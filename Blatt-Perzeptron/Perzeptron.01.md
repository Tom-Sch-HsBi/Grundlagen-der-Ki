# Perzeptron.01

## (1) Entscheidungsgrenze
Gegeben: Gewichtsvektor (w0, w1, w2) = (2, 1, 1).

Entscheidungsgrenze (Trennebene):

w0 + w1*x1 + w2*x2 = 0  
2 + x1 + x2 = 0  →  x2 = -x1 - 2

Bereich, der mit +1 klassifiziert wird:

Alle Punkte mit  
x2 > -x1 - 2  
(oberhalb der Geraden).

## (2) Welche der folgenden Perzeptrons haben die selbe Trennebene? Welche weisen exakt die gleiche Klassifikation auf?

- (1, 0.5, 0.5) – gleiche Trennebene, gleiche Klassifikation  
- (200, 100, 100) – gleiche Trennebene, gleiche Klassifikation  
- (sqrt(2), 1, 1) – nicht proportional, andere Trennebene  
- (-2, -1, -1) – gleiche Trennebene, aber invertierte Klassifikation
