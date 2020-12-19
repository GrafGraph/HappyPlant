# HappyPlant
Happy Plant ist ein Prototyp für ein web-basiertes Überwachungs-Dashboard für vom Nutzer angelegte Pflanzen. 
Es sollen Verbrauchstatistiken und der aktuelle Feuchtigkeits-Status der Pflanzen angezeigt werden.

## Prototyp
### Zielstellung
Im Fokus steht - nach der Machbarkeitsstudie - die Klärung der Fragen zur Nutzerakzeptanz: Wie nützlich ist dieses System? 
Wie ist es ästhetisch und intuitiv darstellbar und welche Herausforderungen entstehen dabei?

Deshalb liegt der Schwerpunkt vorerst in der Darstellung der Daten für den Nutzer: Dem Dashboard.

Des Weiteren gilt es zu klären, wie sich die Datenbeschaffung der Pflanzen abspielen soll und wie dabei auf bestehende Systeme aufgebaut werden kann:
Der Nutzer könnte jedes Detail manuell eintragen, jedoch wäre eine automatische Datenerfassung eine enorme Entlastung.
In Anlehnung an IoT und SmartHome soll diese Aufgabe von entsprechender Sensorik übernommen werden.

### Ausgangssituation
Woher kommen die Pflanzendaten?
Als Fundament soll ein automatisierter Pflanzentopf mit Sensoren zur Feuchtigkeitsmessung genutzt werden. 
Dazu besprachen wir uns mit einem Entwickler, der anhand seines laufenden Projektes, den Prozess der Automatisierung und seine Ergebnisse beschrieb:

In primitivster Form beginnt solch eine Automatisierung mit der Abfrage des aktuellen Feuchtigkeitswertes und der 
Bestimmung, anhand eines vorher festgelegten Grenzwertes, ob zusätzliche Feuchtigkeit hinzugefügt werden muss oder nicht
(Boolean Gießen ja oder nein?). 
Fortgeschrittener werden die Daten (ID des Topfes, Zeitstempel, Feuchtigkeitswert, Grenzwert) dann in einem vordefinierten 
Takt in eine Sensordatenbank (NoSQL (zB. MongoDB) reicht, weil die Daten dumm sind) gespeichert. Bei der Abfrage wird die Historie dann per Tool (zB. Grafana)
als Graph inklusive des Grenzwertes visualisiert. 
Die Feuchtigkeit sinkt nur schwach und nach dem Gießen ist ein Sprung nach oben zu erkennen.
Der Feuchtigkeitswert bewegt sich dabei zwischen den Grenzen 0% (total trocken) und 100% (fließend feucht),
meist in einem verhältnismäßig kleinen Bereich. Zum Beispiel bei der getesteten Basilikumpflanze von Rewe zwischen ~32%, was gleichzeitig dem Grenzwert entspricht, 
und maximal 50% Feuchtigkeit. Dabei fällt die Feuchtigkeit bei den zugrundeliegenden Testbedingungen um etwa 10% pro Tag. 
Die Pflanze wird so also etwa alle zwei Tage gegossen.

Durch die Sensorik werden also die Feuchtigkeitswerte bestimmt und für die zeitliche Zuordnung mit einem Zeitstempel versehen. Zusätzlich erhält der Datensatz den Identifier der Pflanze bzw. des Topfes.
Diese Datensätze können dann in eine Sensordatenbank geschrieben werden, aus der unser System liest und mithilfe eines Visualisierungstools
in dem Dashboard für den Nutzer dargestellt werden. Dabei würde auch automatisch erkannt werden, wann der Nutzer zuletzt gegossen hat.

TODO: Herausforderung neue Pflanzen erstellen... Kann nur genutzt werden, was auch da ist.
TODO: Angeben, dass wir die Daten dieses Projekts nutzen

### Umfang der Umsetzung des Prototyps
Für die Umsetzung des Protoyps werden über MongoDB reale Sensordaten **einer** Pflanze verwendet. 
Zur Speicherung des Pflanzenobjektes und des zugehörigen Grenzwertes wird keine Domaindatenbank erstellt, sondern ein fester vorgegebener Wert verwendet.
Es können erstmal keine weiteren Pflanzen hinzugefügt werden, weil dazu auch neue Töpfe mit Sensoren erstellt werden müssten.

Zuerst soll der "primitve" Ansatz mit einem einfachen Status Boolean verfolgt werden, bevor die grafische Darstellung folgt.

### Ausblick / Ergebnis?
Weiterentwicklungsmöglichkeiten...
- Visualisierung der Historie
- Anbindung einer Domaindatenbank zur Speicherung des Grenzwertes jeder Pflanze und der möglichen Gruppierung (Beete verschiedener Pflanzen in einem Topf)
- Tool zu Erstellung neuer Pflanzen/Töpfe einbinden
- Automatisierte Bewässerung

## Umsetzung

### Architektur
Zwei Datenbanken: Einmal Sensordaten mit IDs und einmal Domain mit Pflanzenobjekten, in denen der Schwellwert und weiteres gespeichert wird. Bei Abfrage 

Die Sensordatenbank fungiert als Schnittstelle zwischen der Hardware des Topfes mit der Bereitstellung der Daten und unserem System.
Die Sensordatenbank wird seitens der Mikrocontroller beim Topf mit den Senordaten angereichert, die seitens der Website dann abgerufen, mit den Objekten aus der Domaindatenbank verknüpft und visualisiert werden.

Die Erstellung weiterer Pflanzen/Töpfe bedarf daher eines getrennten Tools wie zB. NodeRed, wobei Töpfe mit Sensoren ausgestattet und die erzeugten Daten als neues Objekt mit ID in die Datenbank geschrieben werden.
In unserem System werden dann Pflanzenobjekte angelegt und mit einem bestehenden Topf der Sensordatenbank verknüpft. Dazu werden sie mit manuell mit dem gewünschten Grenzwert versehen.

### Anlegen einer neuen Pflanze
#### Nutzereingaben
Notwendig:
- ID/Name des Topfes
- Feuchtigkeit (Sensor)

Optional:
- Alias Name zur Identifikation (Flexibel: "Dritter Kaktus Wohnzimmer" oder "Freddy")

Zukünftig interessant:
- Volumen des Topfes
- Lichtverhältnisse (Sonneneinstrahlung) (Sensor)
- Nährstoffwerte (Sensor)
- Wasserbedarf für Prognosen (Berechnet sich aus Historie und ggfs Wetterbericht(Standort) - Sensor)


## Grobanforderungen HappyPlant
# TODO: Überarbeiten
Eine vordefinierte (generische) Pflanze soll vorhanden sein. 
Daten zu dieser Pflanze liegen in einer Datenbank und werden über eine Website dargestellt.
Zu erfassende Daten: Wasserbedarf, Sonneneinstrahlung/Standort (Enum für Verdunstung), Wasserversorgung. Zusätzlich wünschenswert: Nährstoffbedarf und -Versorgung.
Dargestellt werden sollen die Informationen über ein übersichtliches Dashboard, sodass alles auf einen Blick ersichtlich ist. Sollten weitere Pflanzen hinzukommen, sollen Kategorien/Gruppen von Pflanzen zusammengezogen werden. Es soll ein allgemeiner Gesundheitsstatus (Enum) gezeigt werden, sowie die voraussichtliche Zeit, bis die Pflanze wieder gewässert werden muss.

Der Nutzer kann dem System mitteilen, wenn er seine Pflanzen
gegossen hat.

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
