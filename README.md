# HappyPlant
TODO: "Machbarkeitsstudie", Findungsphase und Entscheidung für Sensordaten beschreiben -> Forschungsauftrag/-Frage

Happy Plant ist ein Prototyp für ein web-basiertes Überwachungs-Dashboard für vom Nutzer angelegte Pflanzen. 
Es sollen Verbrauchstatistiken und der aktuelle Feuchtigkeits-Status der Pflanzen angezeigt werden.

TODO: Inhaltsverzeichnis?

## Prototyp
### Zielstellung
Im Fokus steht - nach der Machbarkeitsstudie - die Klärung der Fragen zur Nutzerakzeptanz: Wie nützlich ist dieses System? 
Wie ist es ästhetisch und intuitiv darstellbar und welche Herausforderungen entstehen dabei?

Deshalb liegt der Schwerpunkt vorerst in der Darstellung der Daten für den Nutzer: Dem Dashboard.

Des Weiteren gilt es zu klären, wie sich die Datenbeschaffung der Pflanzen abspielen soll und wie dabei auf bestehende Systeme aufgebaut werden kann:
Der Nutzer könnte jedes Detail manuell eintragen, jedoch wäre eine automatische Datenerfassung zugunsten der Bedienbarkeit eine enorme Entlastung.
In Anlehnung an Idealen wie IoT und SmartHome soll diese Aufgabe von entsprechender Sensorik übernommen werden.

Außerdem soll eine mögliche Architektur für das System entworfen werden.

### Ausgangssituation
Woher kommen die Pflanzendaten?
Als Fundament soll ein automatisierter Pflanzentopf mit Sensoren zur Feuchtigkeitsmessung genutzt werden. 
Dazu besprachen wir uns mit einem Entwickler, der anhand seines laufenden Projektes, den Prozess der Automatisierung und seine Ergebnisse beschrieb:

In primitivster Form beginnt solch eine Automatisierung durch Sensorik mit der Abfrage des aktuellen Feuchtigkeitswertes und der 
Bestimmung, anhand eines vorher festgelegten Grenzwertes, ob zusätzliche Feuchtigkeit hinzugefügt werden muss oder nicht
(Boolean: Gießen ja oder nein?). 
Fortgeschrittener werden die Daten (ID des Topfes, Zeitstempel, Feuchtigkeitswert, Grenzwert) dann in einem vordefinierten 
Takt in eine Sensordatenbank (NoSQL (zB. MongoDB) reicht, weil es sich um dumme Daten handelt) gespeichert. Bei der Abfrage wird die Historie dann per Tool (zB. Grafana)
als Graph inklusive des Grenzwertes visualisiert.
Die Feuchtigkeit sinkt nur schwach und nach dem Gießen ist ein Sprung nach oben zu erkennen.
Der Feuchtigkeitswert bewegt sich dabei zwischen den Grenzen 0% (total trocken) und 100% (fließend feucht),
meist in einem verhältnismäßig kleinen Bereich. Zum Beispiel bei der getesteten Basilikumpflanze von Rewe zwischen ~32%, was gleichzeitig dem Grenzwert entspricht, 
und maximal 50% Feuchtigkeit. Dabei fällt die Feuchtigkeit bei den zugrundeliegenden Testbedingungen um etwa 10% pro Tag. 
Die Pflanze wird so also etwa alle zwei Tage gegossen.

![Sensorik des Pflanzentopfes](/Documentation/images/SensorikPflanzentopf.PNG "Sensorik des Pflanzentopfes")
TODO: Bild vom Graphen einfügen und Text entsprechend anpassen!

Durch die Sensorik werden also die Feuchtigkeitswerte bestimmt und für die zeitliche Zuordnung mit einem Zeitstempel versehen. Zusätzlich erhält der Datensatz den Identifier der Pflanze bzw. des Topfes.
Diese Datensätze können dann in eine Sensordatenbank geschrieben werden, aus der unser System liest und mithilfe eines Visualisierungstools
in dem Dashboard für den Nutzer dargestellt werden. Dabei würde auch automatisch erkannt werden, wann der Nutzer zuletzt gegossen hat.
Auf diesem System zur Datenbeschaffung soll unser System aufbauen, wobei die Festlegung des Grenzwertes auf der Website geschehen soll.

### Architektur
![HappyPlant Deployment Diagramm](/Documentation/images/DeploymentDiagramm.png "HappyPlant Deployment Diagramm")

Den Kern des Systems stellt der Heroku Server dar, auf dem die Java-Anwendung und die Domaindatenbank laufen, sowie die Website gehostet wird.
Die Schnittstelle zwischen der Hardware des Pflanzentopfes und unserem System bildet die Sensordatenbank, die auf einem Cloud-Server 
läuft und mit den Daten des Feuchtigkeitssensors angereichert wird. Bei der Nutzerabfrage zum Status einer Pflanze lädt 
die Java Anwendung - im Sinne von Polyglot Persistence - die Daten zu dem Pflanzenobjekt (Grenzwert, Gruppierung, o.Ä.) aus
der Domaindatenbank und verknüpft diese mit den Sensordaten aus der Sensordatenbank. Diese Informationen werden dann mithilfe eines
Grafiktools, das als IFrame in das generierte HTML eingebunden wird, visualisiert.

TODO: Architektur auf Sensorseite genauer beschreiben

### Herausforderungen
Durch den Einsatz von Sensorik fallen bereits einige Herausforderungen weg, wie zum Beispiel:
- Wie wird der Wasserverbrauch berechnet? Welche Faktoren können/sollen einbezogen werden?
- Der enorme Aufwand der manuellen Datenerfassung durch den Nutzer und dessen Fehleranfälligkeit (Pflanzendaten, Wann wurde wieviel gegossen uvm.)
- TODO: Weiter ausführen

Jedoch ergeben sich unter Anderem auch neue Hürden:

Wie sollen den auf der Website erstellten Pflanzenobjekten die realen Pflanzen zugeordnet werden?
- Wenn der Nutzer auf der Website eine neue Pflanze registrieren möchte, muss es zur Auswertung der Daten vorher den entsprechenden Pflanzentopf geben 
(Es kann nur genutzt/ausgewertet werden, was auch exisitert...).
Zur Zuordnung könnte dem Topf beim Anschließen eine ID bzw. ein Name gegeben werden, den der Nutzer beim Registrieren der Pflanze wiedererkennt.
Diese ID wird mit den Feuchtigkeitswerten an die Sensordatenbank geschickt, sodass unser System im Formular zum Anlegen 
einer neuen Pflanze eine (Dropdown-) Liste mit den IDs aller bisher nicht zugeordneten Pflanzentöpfen anzeigt, aus denen der Nutzer den richtigen wählen kann.

Daran schließt sich an: Wie sollen *Beete*, also Töpfe bzw. Kästen behandelt werden, in denen mehrere Pflanzen mit unterschiedlichen 
Bedürfnissen leben? Wie erfolgt solch eine Gruppierung zu Beeten? Oder erhält jede Pflanze einen eigenen Sensor?
- Wie viele Sensoren - auch bei großen Töpfen interessant - für eine effektive Messung der Ereignisse nötig wären, müsste vorerst getestet werden.
Sollten mehrere Sensoren pro Beet genutzt werden, könnten Mechanismen zur Gruppierung dieser in Einsatz treten: Zum Beispiel 
Namenskonventionen bei den IDs der Sensoren, was im System eventuell dazu führt, dass für Analysen und Finden von Zusammenhängen 
(verhältnismäßig aufwändige, unpraktische und fehleranfällige) Syntax-Analysen gebraucht würden. Dem Nutzer könnte das jedoch zur Zuordnung von Sensoren zu Pflanzen ausreichen. 
Alternativ könnten entsprechende Datenstrukturen erzeugt und vom System verwaltet werden.

- Zur Gruppierung der Pflanzen zu Beete könnte im Formular zum Anlegen einer neuen Pflanze eine Option existieren, die beschreibt, dass es sich um 
einen bereits verwendeten Topf handelt, wodurch eine Liste der IDs aller Töpfe angezeigt wird. Die unterschiedlichen Grenzwerte 
könnten bei der Darstellung im Graphen mit den Namen der Pflanzen versehen werden.

- Wie die unterschiedlichen Bedürfnisse der Pflanzen berücksichtigt werden können, müsste weiter erforscht werden. Da jedes 
Pflanzenobjekt über einen individuellen Feuchtigkeits-Grenzwert verfügt, ab dem die Pflanze gegossen werden soll, können für den 
aktuellen Feuchtigkeitswert des Topfes unterschiedliche Einschätzungen getroffen werden (und damit individuelle Statusmeldungen geschaltet werden), 
weil eine Pflanze eventuell bereits durstig ist, während eine andere noch genug Wasser hat. Wie kann dabei eine sinnvolle 
Balance gefunden werden? Es müssten schon bei der Wahl der Pflanzenarten, die miteinander getopft werden, deren Bedürfnisse 
berücksichtigt und aufeinander abgestimmt werden. Die schlussendliche Entscheidung obliegt stets dem Nutzer.

- TODO: Weitere Herausforderungen dokumentieren (zb Serverkosten)
Die Erstellung weiterer Pflanzen/Töpfe bedarf daher eines getrennten Tools wie zB. NodeRed, wobei Töpfe mit Sensoren 
ausgestattet und die erzeugten Daten als neues Objekt mit ID in die Sensordatenbank geschrieben werden.

### Umfang der Umsetzung des Prototyps
Für die Umsetzung des Protoyps werden keine realen Sensordaten des MongoDB Servers verwendet, die uns von dem Entwickler des 
automatisierten Pflanzentopfes zur verfügung gestellt werden, weil durch die Verknüpfung unserer Anwendung mit einer MongoDB, bei den Serveranbietern Heroku 
oder auch Cloud-Server, für uns untragbare Serverkosten entstehen würden. Daher werden zur Veranschaulichung der Dashboard-Funktionalitäten Testdaten zur Laufzeit generiert.
Zur Speicherung des Pflanzenobjektes und des zugehörigen Grenzwertes wird keine Domaindatenbank erstellt, sondern ein fester vorgegebener Wert verwendet.
Es können erstmal keine weiteren Pflanzen hinzugefügt werden, weil dazu auch neue Töpfe mit Sensoren erstellt werden müssten.
Das Design und mögliche Funktionalitäten der Website werden angedeutet.

Zuerst soll der *primitve* Ansatz mit einem einfachen Status Boolean verfolgt werden, bevor die grafische Darstellung folgt.

### Ausblick
Der Umfang des Prototyps könnte folgendermaßen erweitert werden:
- Visualisierung der Historie
- Anbindung einer Domaindatenbank zur Speicherung des Grenzwertes jeder Pflanze und der möglichen Gruppierung (Beete verschiedener Pflanzen in einem Topf)
- Tool zur Erstellung neuer Pflanzen/Töpfe einbinden, sodass der Nutzer nur noch ein Portal bedienen muss
- Automatisierte Bewässerung

## Umsetzung
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

## Projektrahmen
TODO: Projektrahmen - Bachelor IT-Projekt einfügen
(Prüfer, Thema, Rahmen(Modul), Deadline)

### Entwicklungsmanifest
#### Allgemein
- Sprache des Projekts: Code und Code-Kommentare sind englisch, Projektdokumentation und -Management sind deutsch.
- Kommunikation: Webex, Discord, Whatsapp, GitHub
- GitHub-Projects Kanban wird zur Aufgabenübersicht und -Verteilung genutzt

#### Software
- GitHub als Versionskontrollsystem und für das Projektmanagement
- Heroku Server für das Deployment (App, DB, Website)
- Java Spring MVC (IDE: IntelliJ)
- Wix.com für Design-Mockups
- LucidChart für Diagramme
- PostgreSQL für die Domaindatenbank

#### Dokumentation
- README
- Code-Kommentare

#### Branch Flow
![Branch FLow](/Documentation/images/BranchFlow.png "Branch FLow")
##### Mergen:
1. pull project
2. merge Dev-Branch (remote) in your branch
3. run the tests and check if the app has any problems
4. push your branch
5. checkout Dev-Branch (Remote)
6. merge your branch in the Dev-Branch
7. push Dev-Branch
