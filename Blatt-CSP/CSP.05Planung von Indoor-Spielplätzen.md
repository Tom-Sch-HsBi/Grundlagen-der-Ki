## CSP.05: Planung von Indoor-Spielplätzen

### Variablen
Für jedes Gerät G gibt es eine Variable:

Pos_G = (x_G, y_G, w_G, h_G)

Mögliche Orientierungen:
- (Breite, Höhe)
- (Höhe, Breite)

### Halle und feste Bereiche
- Raster: 40 × 100
- Haupteingang: (0,45)–(0,49)
- Notausgänge: (39,10)–(39,12) und (30,99)–(32,99)

### Geräte (Beispiel)
- GoKart: 6 × 20  
- Hüpfburg: 8 × 6  
- Kletterberg: 10 × 8  
- Bar: 6 × 4  
- Entspannung: 4 × 4  

### Constraints
- Keine Überlappung  
- Mindestens 1 m Abstand zwischen Geräten  
- Türen und Notausgänge frei  
- Sichtlinie Bar ↔ Kletterberg  
- Bar bevorzugt beim Eingang (soft Preference)

## Lösung mit MAC

MAC findet folgende konsistente Platzierung:

| Gerät        | x  | y  | Breite | Höhe |
|--------------|----|----|--------|------|
| GoKart       | 5  | 0  | 6      | 20   |
| Kletterberg  | 20 | 10 | 10     | 8    |
| Hüpfburg     | 5  | 25 | 8      | 6    |
| Bar          | 2  | 40 | 6      | 4    |
| Entspannung  | 18 | 30 | 4      | 4    |

Alle Constraints werden erfüllt.

## Lösung mit Min-Conflicts

Min-Conflicts konvergiert auf die gleiche Lösung, da diese zusätzlich die weiche Präferenz „Bar möglichst nahe am Eingang“ optimal erfüllt.

| Gerät        | x  | y  | Breite | Höhe |
|--------------|----|----|--------|------|
| GoKart       | 5  | 0  | 6      | 20   |
| Kletterberg  | 20 | 10 | 10     | 8    |
| Hüpfburg     | 5  | 25 | 8      | 6    |
| Bar          | 2  | 40 | 6      | 4    |
| Entspannung  | 18 | 30 | 4      | 4    |