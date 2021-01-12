# HappyPlant
HappyPlant ist ein Prototyp für ein web-basiertes Überwachungs-Dashboard für vom Nutzer angelegte Pflanzen. 
Es sollen Verbrauchstatistiken und der aktuelle Feuchtigkeits-Status der Pflanzen angezeigt werden. 

Zur Website: [HappyPlant Dashboard](https://happyplant1.herokuapp.com/)

## Prototyp
### Zielstellung
Klärung der Fragen zur Nutzerakzeptanz: Wie nützlich ist dieses System? 
Wie ist es ästhetisch und intuitiv darstellbar und welche Herausforderungen entstehen dabei?
Dazu soll die Darstellung der Daten für den Nutzer als Dashboard erprobt werden.

Des Weiteren gilt es zu klären, wie sich die Datenbeschaffung der Pflanzen abspielen soll und wie dabei auf bestehende Systeme aufgebaut werden kann:
Der Nutzer könnte jedes Detail manuell eintragen, jedoch wäre eine automatische Datenerfassung zugunsten der Bedienbarkeit eine enorme Entlastung.
In Anlehnung an Ideale wie IoT und SmartHome soll diese Aufgabe von entsprechender Sensorik übernommen werden.

Außerdem soll eine mögliche Architektur für das System entworfen werden, um nötige Systeme und deren Zusammenspiel zu skizzieren.

### Ausgangssituation
Woher kommen die Pflanzendaten?

Als Fundament soll ein automatisierter Pflanzentopf mit Sensoren zur Feuchtigkeitsmessung genutzt werden. 
Dazu besprachen wir uns mit einem Entwickler, der anhand seines laufenden Projektes, den Prozess der Automatisierung und seine Ergebnisse beschrieb:

In primitivster Form beginnt solch eine Automatisierung durch Sensorik mit der Abfrage des aktuellen Feuchtigkeitswertes und der 
Bestimmung, anhand eines vorher festgelegten Grenzwertes, ob zusätzliche Feuchtigkeit hinzugefügt werden muss oder nicht
(Boolean: Gießen ja oder nein?). 
Fortgeschrittener werden die Daten (ID des Topfes, Zeitstempel, Feuchtigkeitswert, Grenzwert) dann in einem vordefinierten 
Takt in eine Sensordatenbank (NoSQL - zB. MongoDB - reicht, weil es sich um dumme Daten handelt) gespeichert. Bei der Abfrage wird die Historie dann per Tool (zB. Grafana)
als Graph inklusive des Grenzwertes visualisiert.
Die Feuchtigkeit sinkt nur schwach und nach dem Gießen ist ein Sprung nach oben zu erkennen.
Der Feuchtigkeitswert bewegt sich dabei zwischen den Grenzen 0% (total trocken) und 100% (fließend feucht),
meist in einem verhältnismäßig kleinen Bereich. Zum Beispiel bei der getesteten Basilikumpflanze von Rewe zwischen ~32%, was gleichzeitig dem Grenzwert entspricht, 
und maximal 50% Feuchtigkeit. Dabei fällt die Feuchtigkeit bei den zugrundeliegenden Testbedingungen um etwa 10% pro Tag. 
Die Pflanze wird so also etwa alle zwei Tage gegossen.

Hardware der Sensorik:

![Sensorik des Pflanzentopfes](/Documentation/images/SensorikPflanzentopf.PNG "Sensorik des Pflanzentopfes")

Beispiel eines kurzen Ausschnitts des Feuchtigkeitsgraphen der Basilikumpflanze:

![Kurzer Basilikumgraph](/Documentation/images/DatenmoniGraph1.PNG "Kurzer Basilikumgraph")

Beispiel eines realen Feuchtigkeitsgraphen über ein längeres Zeitfenster (Dabei handelt es sich nicht mehr um die Basiliumpflanze):

![Realer Feuchtigkeitsgraph](/Documentation/images/RealerFeuchtigkeitsgraph.jpeg "Realer Feuchtigkeitsgraph")

Durch die Sensorik werden also die Feuchtigkeitswerte bestimmt und für die zeitliche Zuordnung mit einem Zeitstempel versehen. Zusätzlich erhält der Datensatz den Identifier der Pflanze bzw. des Topfes.
Diese Datensätze können dann in eine Sensordatenbank geschrieben werden, aus der unser System liest und mithilfe eines Visualisierungstools
in dem Dashboard für den Nutzer dargestellt werden. Dabei würde auch automatisch erkannt werden, wann der Nutzer zuletzt gegossen hat.

Auf diesem System zur Datenbeschaffung soll unser System aufbauen, wobei die Festlegung des Grenzwertes auf der Website geschehen soll.

### Architektur
![HappyPlant Deployment Diagramm](/Documentation/images/DeploymentDiagramm.png "HappyPlant Deployment Diagramm")

Den Kern des Systems stellt der Heroku Server dar, auf dem die Java-Anwendung und die Domaindatenbank laufen sowie die Website gehostet wird.
Die Schnittstelle zwischen der Hardware des Pflanzentopfes und unserem System bildet die Sensordatenbank, die auf einem Cloud-Server 
läuft und mit den Daten des Feuchtigkeitssensors angereichert wird. Bei der Nutzerabfrage zum Status einer Pflanze lädt 
die Java Anwendung - im Sinne von Polyglot Persistence - die Daten zu dem Pflanzenobjekt (Grenzwert, Gruppierung, o.Ä.) aus
der Domaindatenbank und verknüpft diese mit den Sensordaten aus der Sensordatenbank. Diese Informationen werden dann mithilfe eines
Grafiktools, das als IFrame in das generierte HTML eingebunden wird, visualisiert. Ein Grafiktool wie Grafana bietet sich für das tatsächliche System an,
bedarf allerdings eines Servers und einer Lizenz. Für den Prototypen und auch erste Versionen des Systems reicht es, die Daten mit zum Beispiel Chart.js zu visualisieren.

### Design

Der folgende Screenshot zeigt die ersten Designideen für die Website HappyPlant an, hierbei sollten alle Pflanzen angezeigt werden: 

![HappyPlant Website Mockup](/Documentation/images/FirstMockup.PNG "HappyPlant Website Mockup")

Jedoch wurde diese Idee verworfen, weil auf dem Dashboard zu wenig Informationen über die Pflanzen zu sehen war. Daraus entstand die Idee, 
dass die Daten zu den Pflanzen im Vordergrund stehen und an der Seite von dem Browserfenster eine Liste von den Pflanzen angezeigt wird. 
Mithilfe dieser Liste kann der Nutzer sich seine Pflanzen anzeigen lassen.  

![HappyPlant Website Mockup](/Documentation/images/HappyPlantMockup.PNG "HappyPlant Website Mockup")

Der Prototyp wurde anhand dieser Designidee entwickelt. Die Farbe des Headers wurde geändert, damit ein lebhafterer und freundlicher 
Eindruck geschaffen wird. Außerdem wurde die Liste von den Pflanzen auf die Linke Seite verschoben, weil Menüführungen häufig auf der linken Seite ist
und es intuitiver für den Nutzer ist. Weterhin wurde der Button zum Einfügen einer neuen Pflanze nach oben verschoben damit der Nutzer nicht runter scrollen muss, 
wenn zu viele Pflanzen angelegt wurden. 

![HappyPlant Website](/Documentation/images/Prototype.PNG "HappyPlant Website")

Momentan ist auf der Website ein Dummybild für die Sonnenaktivität. Das Bild ist nicht passend für Pflanzen. 
Bei dem Entwickeln der Website sind Herausforderungen, bezüglich des Moisture Diagrammes, entstanden. Das Moisture Diagramm 
hat sich komisch verhalten, unter anderem wurden die Größen unterschiedlich verändert. Dadurch entstand das Problem, dass 
das Moisture Diagramm bei einigen Größen von dem Browserfenster nicht gut aussieht. Es kann passieren, dass entweder das Moisture Diagramm 
aus der Box herausragt oder es zu klein für die Box wird. 

### Nutzerakzeptanz
HappyPlant wird höchstwahrscheinlich anfangs sehr skeptisch betrachtet werden. Einige Nutzer könnten das Dashboard als 
nutzlos einschätzen und es nicht nutzen. Viele altmodische und traditionelle Menschen werden es somit
nicht Nutzen. Jedoch gibt es inzwischen modernere Generationen, die an Pflanzen interessiert sind und deutlich 
vertrauter mit der modernen Technik sind. Das Projekt wird wahrschienlich von Freunden des SmartHomes zuspruch erhalten. 
Besonders der Aspekt, dass Leute heutzutage deutlich weniger Zeit haben, garantiert beinahe eine hohe Nutzerakzeptanz. 
Die Nutzer des Dashboards müssen nicht mehr vor die Tür gehen um zu wissen ob ihre Pflanzen genug Wasser haben. 

### Herausforderungen
Durch den Einsatz von Sensorik fallen bereits einige Herausforderungen weg, wie zum Beispiel:
- Wie wird der Wasserverbrauch berechnet? Welche Faktoren können/sollen einbezogen werden?
- Der enorme Aufwand der manuellen Datenerfassung durch den Nutzer und dessen Fehleranfälligkeit (Pflanzendaten, Wann wurde wieviel gegossen uvm.)


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
Alternativ könnten entsprechende Datenstrukturen (In der Pflanzenklasse für die Domaindatenbank) erzeugt und vom System verwaltet werden.

- Zur Gruppierung der Pflanzen zu Beete könnte im Formular zum Anlegen einer neuen Pflanze eine Option existieren, die beschreibt, dass es sich um 
einen bereits verwendeten Topf handelt, wodurch eine Liste der IDs aller Töpfe angezeigt wird. Die unterschiedlichen Grenzwerte 
könnten bei der Darstellung im Graphen mit den Namen der Pflanzen versehen werden.

- Wie die unterschiedlichen Bedürfnisse der Pflanzen berücksichtigt werden können, müsste weiter erforscht werden. Da jedes 
Pflanzenobjekt über einen individuellen Feuchtigkeits-Grenzwert verfügt, ab dem die Pflanze gegossen werden soll, können für den 
aktuellen Feuchtigkeitswert des Topfes unterschiedliche Einschätzungen getroffen werden (und damit individuelle Statusmeldungen geschaltet werden), 
weil eine Pflanze eventuell bereits durstig ist, während eine andere noch genug Wasser hat. Wie kann dabei eine sinnvolle 
Balance gefunden werden? Es müssten schon bei der Wahl der Pflanzenarten, die miteinander getopft werden, deren Bedürfnisse 
berücksichtigt und aufeinander abgestimmt werden. Die schlussendliche Entscheidung der Handlung obliegt stets dem Nutzer.


Die Erstellung weiterer Pflanzen/Töpfe bedarf eines getrennten Tools wie zB. NodeRed, wobei Töpfe mit Sensoren 
ausgestattet und die erzeugten Daten als neues Objekt mit ID in die Sensordatenbank geschrieben werden. 
Es gilt zu erforschen, wie das auf der Website von HappyPlant zentralisiert werden könnte...

### Umfang der Umsetzung des Prototyps
Für die Umsetzung des Protoyps werden keine realen Sensordaten des MongoDB Servers verwendet, die uns von dem Entwickler des 
automatisierten Pflanzentopfes zur verfügung gestellt werden, weil durch die Verknüpfung unserer Anwendung mit einer MongoDB, bei den Serveranbietern Heroku 
oder auch Cloud-Server, für uns untragbare Serverkosten entstehen würden. Daher werden zur Veranschaulichung der Dashboard-Funktionalitäten Testdaten zur Laufzeit generiert, 
mit denen per Chart.js der Feuchtigkeitsgraph simuliert wird.
Zur Speicherung des Pflanzenobjektes und des zugehörigen Grenzwertes wird keine Domaindatenbank erstellt, sondern ein fester vorgegebener Wert verwendet.
Es können erstmal keine weiteren Pflanzen hinzugefügt werden, weil dazu auch sinngemäß neue Töpfe mit Sensoren erstellt werden müssten.
Das Design und mögliche Funktionalitäten der Website werden angedeutet.

### Ausblick
Der Umfang des Prototyps könnte folgendermaßen erweitert werden:
- Einstellungen der Graphen: Andere Zeitfenster anzeigen, Grenzwert anpassen (Verlauf des Grenzwertes darstellen, anstatt nur die Konstante zu heben?) o.ä.
- Anbindung einer Domaindatenbank zur Speicherung des Grenzwertes jeder Pflanze und der möglichen Gruppierung (Beete verschiedener Pflanzen in einem Topf)
- Tool zur Erstellung neuer Pflanzen/Töpfe einbinden, sodass der Nutzer nur noch ein Portal bedienen muss
- Automatisierte Bewässerung
- Weitere Sensoren einbinden: Sonnenlicht, Temperatur, Nährstoffe / Minerale (Paprikapflanzen benötigen zB. extra Kalzium, sonst bilden sich braune Stellen an den Früchten)
- Funktionen der einzelnen Buttons
- Extra Seite für die Erstellung einer neuen Pflanze
- Verwendung der Datenbank
- Designveränderungen
 

## Umsetzung
### Generierung der Testdaten
Ziel war es hierbei, Daten zu erzeugen, die im Sinn und Format den Echten aus der Sensordatenbank entsprechen:

![Ausschnitt findAll statement](/Documentation/images/DatenFormatSensor.jpeg "Ausschnitt findAll statement")

Um die Funktionalität der Erzeugung vom eigentlichen System zu trennen, wurde dafür die Helper Klasse DataFaker erstellt.
Für den Prototypen hätte ein klarer Testfall genügt, jedoch wurde durch den Einsatz von Random-Bereichen die Möglichkeit geschaffen das Verhalten unterschiedlicher
Pflanzen zu simulieren (Jedes Neuladen entspricht einem einzigartigen Graphen!). Dabei wird ein Grenzwert, ab dem gegossen werden muss, übergeben und standardmäßig eine Historie über ein Zeitfenster von 7 Tagen konstruiert.
Der durchschnittliche Wasserverbrauch orientiert sich so, dass die Pflanze ihre Reserven zwischen einem oder sechs Tagen aufbraucht und gegossen werden muss. Allerdings verläuft
dieser Verbrauch nicht geradlinig, denn je nach Gegebenheit des Umfelds (Sonnenlicht, Temperatur) verbraucht die Pflanze mal mehr oder mal weniger als den Normalwert.
Der Feuchtigkeitsbereich in dem die Pflanze lebt reicht von definierten 10% bis 25% über dem Grenzwert. Genau diese Ausgangsfeuchtigkeit wird beim Gießen wieder hergestellt.
Es ist unrealistisch, dass ein menschlicher Gärtner jedes mal exakt die selbe Menge gießt, aber in Anbetracht des Ziels der vollständigen Automatisierung des Pflanzentopfes,
wird davon ausgegangen, dass die Wasserversorgung automatisiert stattfindet, wobei diese Regelmäßigkeit gegeben ist. 
Deshalb wird auch sofort beim Unterschreiten des Grenzwertes gegossen.
So sähe jedoch ein Graph aus, bei dem die Gießmenge variabel ist:

![Feuchtigkeitsgraph mit variabler Gießmenge](/Documentation/images/IrregularWateringGraph.PNG "Feuchtigkeitsgraph mit variabler Gießmenge")

Ein Gärtner wird auf normalem Wege den Status seiner Pflanzen etwa 1-6 mal pro Tag prüfen. Der Feuchtigkeitssensor im Pflanzentopf sendet seine Ergebnisse alle paar Sekunden.
Für die Anzahl der Messwerte, die zu generieren sind, wird sich eher an der zugrundeliegenden Sensorik orientiert, jedoch ist ein so genaues Messen nicht notwendig.
Daher fiel die Entscheidung auf stündliche Messergebnisse. Bei einem Zeitfenster von 7 Tagen entspricht dies 168 zu erzeugenden Messwerten.
Dadurch ergibt sich bei einfachen mathematischen Operationen kaum Rechenaufwand (3ms inklusive Rendering des Graphen): Das Programm leidet in diesem Rahmen also kaum unter Performanceproblemen.
Bei dieser Geschwindigkeit stellt es auch kein Problem dar, den kompletten Prozess bei jedem Neuladen zu wiederholen.

Der DataFaker erzeugte ehemals einen Vector von SensorData (Messwert und Zeitstempel), allerdings musste dieser Ansatz für eine bessere Repräsentation
der Realobjekte (Erweiterbarkeit und Wartbarkeit) und für den einfacheren Einsatz von Chart.js umgestellt werden. Da Chart.js die Daten als String-Arrays übergeben werden, bietet es sich an,
statt Vector als Datentyp ArrayList zu verwenden. Die Umstellung lief problemlos, da beide Datenkonstrukte ähnlich arbeiten. Zusätzlich wird ein Label für die Datensätze der Sensoren
benötigt (Feuchtigkeit, Sonnenlicht, ...). Deshalb wurde SensorData zu SensorDataEntry - einem Sensordateneintrag - umbenannt und eine neue Klasse SensorData erstellt,
die eine ArrayList von Sensordateneinträgen sowie ein Label hält. Damit wurde die Erweiterbarkeit verbessert. Die Klasse SensorData erhielt ebenfalls die Methoden zur
umformatierung der Sensordateneinträge zu getrennten String Arrays für den Gebrauch in Chart.js, sodass diese Funktionalität im *Model* und nicht etwa im *Controller* liegt.

### Visualisierung der Sensordaten mit Chart.js
Die Nutzung mächtiger Grafiktools wie Grafana ist anzustreben, übersteigt jedoch den Rahmen dieses Prototyps.
Für kleinere Anwendungen, mit weniger Datenpunkten und geringeren Anforderungen, reicht das Javascript basierte Rendering zur Laufzeit aus.
Chart.js ist dabei der wohl namhafteste Vertreter. Dabei handelt es sich um eine starke Bibliothek, durch die mit relativ einfachem Aufwand
bereits ansehnliche Ergebnisse erzeugt werden können: Grundlegende Animationen, Legenden und eine sinnvolle Gruppierung der Achsen-Labels sind sofort aktiv.

Per CDN Link eingebunden, kann dadurch ein HTML Canvas Element befüllt werden. Das Canvas Element selbst ist nicht *responsive* doch lässt es sich durch das Containerelement (Div)
formatieren und an dessen Eigenschaften anpassen, sodass es *responsive* wird. Dem Script zur Erstellung des Graphen wird dann ein Typ (Line), die Labels der Achsen
und die Datensätze **als String-Arrays** sowie styling Optionen übergeben. Zur Übergabe der Variablen kann im Javascript auch Thymeleaf dereferenziert werden.

Als Besonderheit stellte sich heraus, dass zur Erzeugung der horizontalen Linie des Grenzwertes ein genauso großes Array wie das der Messwerte übergeben werden musste,
in dem immer derselbe Wert steht. Damit die Linie glatt ist, wurden die Punkte unsichtbar gemacht. Dadurch, dass die Grenzwerte pro Messwert dargestellt werden, 
ergibt sich die Möglichkeit eine Anpassung des Grenzwertes darzustellen, sollte er sich im Verlauf der Zeit ändern müssen.

### Anlegen einer neuen Pflanze
Was könnte beim Anlegen einer neuen Pflanze interessant sein?
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

## Projektrahmen
HappyPlant entsteht im Rahmen des Bachelor-IT-Projekts von Anton Bespalov und Michael Hopp.
Abgegeben wird es bis zum 28.02.2021 beim Prüfer Prof. Avemarg an der Fachhochschule Erfurt.
Der geplante Zeitaufwand für die Prüfungsleistung beträgt pro Prüfling 75 Stunden (3CP).

Der Projektauftrag teilt sich zum einen in die Klärung der Forschungsfragen: Entwurf einer Architektur, Aufbau auf- und Umgang mit Sensordaten (Testdatenerzeugung)
sowie das Aufzeigen von Möglichkeiten und Herausforderungen des Systems. Zum anderen soll das System als Website greifbar gemacht werden, um die Funktionalitäten
und die Nutzerakzeptanz zu erproben.

### Entwicklungsmanifest
#### Allgemein
- Sprache des Projekts: Code und Code-Kommentare sind englisch, Projektdokumentation und -Management sind deutsch.
- Kommunikation: Webex, Discord, Whatsapp, GitHub
- GitHub-Projects Kanban wird zur Aufgabenübersicht und -Verteilung genutzt

#### Software
- GitHub als Versionskontrollsystem und für das Projektmanagement
- Heroku Server für das Deployment (App, DB, Website)
- Java Spring MVC (Javaversion: 11.0.9; IDE: IntelliJ IDEA)
- Wix.com für Design-Mockups
- LucidChart für Diagramme
- (PostgreSQL für die Domaindatenbank)

#### Dokumentation
- README
- Code-Kommentare

#### Branch Flow
Auf dem *main*-Branch soll stets eine releasefähige Version laufen. Für Features und Fixes werden Branches abgespaltet, die nach
erfolgreicher Implementierung wieder auf *main* zurückgeführt werden. Dabei ist darauf zu achten, dass die Differenz der Versionen
durch regelmäßige Pulls nicht zu groß wird.

![Branch FLow](/Documentation/images/BranchFlow.png "Branch FLow")

##### HowTo-Branch-Mergen:
1. pull project
2. merge Dev-Branch (remote) in your branch
3. run the tests and check if the app has any problems
4. push your branch
5. checkout Dev-Branch (Remote)
6. merge your branch in the Dev-Branch
7. push Dev-Branch
