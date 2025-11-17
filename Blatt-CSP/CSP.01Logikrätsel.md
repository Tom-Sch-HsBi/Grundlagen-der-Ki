# Formulierung des Einstein-Rätsels als Constraint Satisfaction Problem (CSP)

## Variablen
Für jede der fünf Hauspositionen (1–5) wird je eine Variable für jede Eigenschaft definiert:
- **Farbe:** `F1, F2, F3, F4, F5`
- **Nationalität:** `N1, N2, N3, N4, N5`
- **Getränk:** `G1, G2, G3, G4, G5`
- **Zigarette:** `Z1, Z2, Z3, Z4, Z5`
- **Haustier:** `H1, H2, H3, H4, H5`

## Wertebereiche
- Farben: {Rot, Grün, Blau, Gelb, Weiß}
- Nationalitäten: {Brite, Schwede, Däne, Norweger, Deutscher}
- Getränke: {Tee, Kaffee, Milch, Bier, Wasser}
- Zigarettenmarken: {PallMall, Dunhill, Blend, BlueMaster, Prince}
- Haustiere: {Hunde, Vögel, Katzen, Pferde, Fische}

## Constraints
### Unäre Constraints
- `N1 = Norweger`
- `G3 = Milch`

### Binäre Constraints (kurz)

#### 1) Gleichheits-Constraints (je Haus i)
- (N_i = Brite) ⇔ (F_i = Rot)
- (N_i = Schwede) ⇔ (H_i = Hunde)
- (N_i = Däne) ⇔ (G_i = Tee)
- (F_i = Grün) ⇔ (G_i = Kaffee)
- (Z_i = PallMall) ⇔ (H_i = Vögel)
- (F_i = Gelb) ⇔ (Z_i = Dunhill)
- (Z_i = BlueMaster) ⇔ (G_i = Bier)
- (N_i = Deutscher) ⇔ (Z_i = Prince)

#### 2) AllDifferent (für jede Kategorie)
Für alle i ≠ j:
- F_i ≠ F_j
- N_i ≠ N_j
- G_i ≠ G_j
- Z_i ≠ Z_j
- H_i ≠ H_j

#### 3) Neben-Constraints (Nachbarhäuser)
Für benachbarte Positionen (i, i+1):
- (F_i = Grün) → (F_{i+1} = Weiß)
- (Z_i = Blend) ↔ (H_{i+1} = Katzen oder H_{i-1} = Katzen)
- (H_i = Pferde) ↔ (Z_{i+1} = Dunhill oder Z_{i-1} = Dunhill)
- (Z_i = Blend) ↔ (G_{i+1} = Wasser oder G_{i-1} = Wasser)
- (N_i = Norweger) ↔ (F_{i+1} = Blau oder F_{i-1} = Blau)
