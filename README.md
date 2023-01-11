# DEC SpiritAnimal

## Local Deployment
**MongoDB** Container starten per `docker compose up`  
*MongoExpress Datenbankfrontend erreichbar über `localhost:8081`*

**Backend** mit Maven builden lassen und über IDE starten

**Frontend**: Nach `\implementation\frontend\spiritanimal-frontend` navigieren.  
`npm i`  
`npm run serve`

## Tech-Stack
Datenbank: MongoDB  
Backend: Java/Spring Boot  
Frontend: Vue.js

## Authentication/Authorisation
**B2C-User** loggen sich einfach über das Frontend nach erfolgreicher Registrierung mit Username und Passwort ein.  
**B2B-User** registrieren sich auch zunächst über das Frontend. Anschließend können sie mit einem Klick auf die Schaltfläche "Mein Profil" ihren API-Token kopieren.
Dieser Token ist bei allen API-Calls im Header mitzugeben.  
Für den **Administrator** des Systems gibt es einen bereits angelegten Account mit bereits hinterlegtes Passwort:  

**[TODO: Hier Credentials angeben]**

Der Administrator erhält seinen API-Token genau wie der B2B User, indem er ihn unter "Mein Profil" kopiert.  
Ist ein Token abgelaufen, muss der User sich wieder übers Frontend einloggen um wiederum seinen neuen Token zu kopieren.
