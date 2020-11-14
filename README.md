# HappyPlant
Web based Dashboard for Plant Status and Health 

## Requirements HappyPlant

Eine vordefinierte (generische) Pflanze soll vorhanden sein. 
Daten zu dieser Pflanze liegen in einer Datenbank und werden über eine Website dargestellt.
Zu erfassende Daten: Wasserbedarf, Sonneneinstrahlung/Standort (Enum für Verdunstung), Wasserversorgung. Zusätzlich wünschenswert: Nährstoffbedarf und -Versorgung.
Dargestellt werden sollen die Informationen über ein übersichtliches Dashboard, sodass alles auf einen Blick ersichtlich ist. Sollten weitere Pflanzen hinzukommen, sollen Kategorien/Gruppen von Pflanzen zusammengezogen werden. Es soll ein allgemeiner Gesundheitsstatus (Enum) gezeigt werden, sowie die voraussichtliche Zeit, bis die Pflanze wieder gewässert werden muss.

## Stakeholder und deren Anforderungen: 

### Nutzer:
- Immer zugreifen können
-> muss Ausfallssicher sein, wenn Webiste ausfällt, dann können Pflanzen sterben 
- Parameter der Pflanzen müssen korrekt sein 
-> Wenn Parameter nicht Korrekt sind, dann sterben die PFlanzen
- Wollen Benachrichirgungen haben
-> Ohne Benachrichtigungen können die Pflanzen sterben, weil der Nutzer es nicht mitbekommt
- Website muss Intuitiv sein
- Schöne Ansicht, dem Auge muss es gefallen
### Admin: 
- Leichte wartbarkeit
-> Parameter für Pflanzen leicht anpassen können oder neue Pflanzen einfügen 
### Inhaber: 
- Möglichst viel Gewinn erzielen 
- Zufriedenheit der Kunden
- Selbst Nutzen von der App haben 

## Branch Flow
![alt text](https://github.com/Maffotter/HappyPlant/blob/main/Documentation/BranchFlow.png "Branch FLow")
