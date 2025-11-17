include "globals.mzn";

set of int: H = 1..5;

enum Color    = { red, green, white, yellow, blue };
enum Nation   = { brit, swede, dane, norwegian, german };
enum Drink    = { tea, coffee, milk, beer, water };
enum Smoke    = { pallmall, dunhill, blend, bluemaster, prince };
enum Pet      = { dog, bird, cat, horse, fish };

array[H] of var Color:  color;
array[H] of var Nation: nation;
array[H] of var Drink:  drink;
array[H] of var Smoke:  smoke;
array[H] of var Pet:    pet;

constraint alldifferent(color);
constraint alldifferent(nation);
constraint alldifferent(drink);
constraint alldifferent(smoke);
constraint alldifferent(pet);

% Brit im roten Haus
constraint forall(i in H)(
    nation[i] = brit <-> color[i] = red
);

% Schwede hält einen Hund
constraint forall(i in H)(
    nation[i] = swede <-> pet[i] = dog
);

% Däne trinkt Tee
constraint forall(i in H)(
    nation[i] = dane <-> drink[i] = tea
);

% Grün steht links von weiß
constraint forall(i in 1..4)(
    color[i] = green -> color[i+1] = white
);

% Kaffee im grünen Haus
constraint forall(i in H)(
    color[i] = green -> drink[i] = coffee
);

% Pall Mall -> Vögel
constraint forall(i in H)(
    smoke[i] = pallmall -> pet[i] = bird
);

% Gelbes Haus -> Dunhill
constraint forall(i in H)(
    color[i] = yellow -> smoke[i] = dunhill
);

% In der Mitte wird Milch getrunken
constraint drink[3] = milk;

% Erster ist Norweger
constraint nation[1] = norwegian;

% Blend neben Katze
constraint forall(i in H)(
    smoke[i] = blend ->
    ((i > 1 /\ pet[i-1] = cat) \/ (i < 5 /\ pet[i+1] = cat))
);

% Dunhill neben Pferd
constraint forall(i in H)(
    smoke[i] = dunhill ->
    ((i > 1 /\ pet[i-1] = horse) \/ (i < 5 /\ pet[i+1] = horse))
);

% BlueMaster -> Bier
constraint forall(i in H)(
    smoke[i] = bluemaster -> drink[i] = beer
);

% Deutscher -> Prince
constraint forall(i in H)(
    nation[i] = german -> smoke[i] = prince
);

% Norweger neben Blau
constraint forall(i in H)(
    nation[i] = norwegian ->
    ((i > 1 /\ color[i-1] = blue) \/ (i < 5 /\ color[i+1] = blue))
);

% Wasser neben Blend
constraint forall(i in H)(
    drink[i] = water ->
    ((i > 1 /\ smoke[i-1] = blend) \/ (i < 5 /\ smoke[i+1] = blend))
);

output [
  "Haus | Farbe | Nation | Drink | Smoke | Pet\n"] ++
  [ show(i) ++ " | " ++
    show(color[i]) ++ " | " ++
    show(nation[i]) ++ " | " ++
    show(drink[i]) ++ " | " ++
    show(smoke[i]) ++ " | " ++
    show(pet[i]) ++ "\n"
  | i in H
  ];
solve satisfy;