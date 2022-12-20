# Feedback zu den Abgaben

## A1
Beschreibung: sehr umfangreich beschrieben (1.5/1.5)

Einordnung: Einordnung nicht ganz nachvollziehbar: oben steht, dass über die Platform Tiere verkauft werden - das dafür auch Informationen benötigt werden ist klar, für den Kunden aber eher sekundär (0.5/0.5)

Geschäftsmodell: klar und begründet (2/2)

Charakteristiken: alles abgedeckt und auf den UC bezogen (2/2)

## A2

A:

Sehr detailliert beschrieben, mit Sequenzdiagrammen visualisiert

Vorbedingungen,... teilweise implizit beschrieben (siehe Infoblatt)

  (3/3)

B:

Beschreibung und Diagramm vorhanden; Beschreibung ausreichend und sound

 (2/2)

C: 

Schema und Beispiele sind gegeben; Auch die Endpunkte sind beschreiben, welche auf die Schemabeschreibung verweisen;

Auch eine Protokollsequenz ist gegeben; auch HTTP Response Codes sind (im Text) beschrieben 

Graphql ist vorhanden

(13/13)

Sehr gut!



Allgemeine Anmerkungen:

Administrator!=B2B UC

B2B Use Cases müssen mehr als einen simplen Request/Response umfassen

### E-Mail Nachfrage zu A2 Feedback

Sehr geehrter Herr Pittl, 

vielen Dank für das schnelle Feedback! Ich wollte mich nochmal eben vergewissern, dass ich Ihre Inputs richtig verstehe.

"Administrator!=B2B UC"
Ich habe in der Abgabe keine Adiministratorrolle definiert. Ich sollte mir daher nochmal explizit Gedanken über die Administration des Systems machen und ganz klar abstecken, was ein B2B-User darf und was nur ein Administrator darf.

-> Pittl: Das ist richtig: viele haben das vermischt, weshalb ich das als generelle Anmerkung jeden geschrieben habe. Für die Implementierung muss auch eine Administratoren Schnittstelle existieren (siehe Angabe). Diese Rolle kann (muss aber nicht) komplett separat sein.

"B2B Use Cases müssen mehr als einen simplen Request/Response umfassen"
Ich glaube, diesen Punkt habe ich im Vorhinein falsch aufgefasst. Ich habe ihn versucht dadurch zu erfüllen, dass für jeden Use case zwei aufeinanderfolgende Requests nötig sind. Haben Sie hier vielleicht nochmal einen Hinweis oder ein Best Practice Beispiel?

-> Pittl: Beim Kickoff bzw. in der Fragestunde habe ich immer das Beispiel einer Cryptobörse gebracht: vereinfacht: man frägt z.B. den akutellen Kurs ab, anschließend setzt man eine Transaktion ab, wo man den tatsächlichen Kaufkurs (gültig für X Sekunden) zurück bekommt: anschließend muss man die Transaktion bestätigen

