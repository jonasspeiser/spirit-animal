package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.*;
import dec.team3.spiritanimal.repositories.KaufRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
public class KaufService {

    @Autowired
    KaufRepository kaufRepository;
    @Autowired
    InseratService inseratService;
    @Autowired
    UserService userService;

    RestTemplate restTemplate = new RestTemplate();
    String paymentProviderUrl = "http://localhost:8080/mock";
    String geldEinziehenUrl = paymentProviderUrl + "/collectMoney";
    String geldSendenUrl = paymentProviderUrl + "/sendMoney";

    private String geldEinziehen(String zahlungsdaten) {
        // REST-Call mit zahlungsdaten an gemockten Payment Provider schicken und Antwort ausgeben
        String response = restTemplate.postForObject(geldEinziehenUrl, zahlungsdaten, String.class);
        return response;
    }

    private String geldSenden(String zahlungsdaten) {
        // REST-Call mit zahlungsdaten an gemockten Payment Provider schicken und Antwort ausgeben
        String response = restTemplate.postForObject(geldSendenUrl, zahlungsdaten, String.class);
        return response;
    }

    private void sendeMail(String empfänger, String betreff, String inhalt) {
        // TODO: Möglichkeit E-Mails zu senden einrichten!
        // TODO: Ist diese Funktion evtl besser im UserService aufgehoben? Der hat schließlich immer die aktuelle email Adresse des Users, nicht nur eine historische Abschrift
        // -> Auch Datenschutztechnisch besser. So fliegen die Userdaten nicht überall im System rum.
    }

    public String starteKauf(String käuferUsername, String inseratID) {
        User käufer = userService.getUser(käuferUsername);
        Inserat inserat = inseratService.getInserat(inseratID);
        if (käufer == null || inserat == null) {
            return "Username \"" + käuferUsername + "\" oder Inserat \"" + inseratID + "\" konnte nicht gefunden werden";
        }
        if (!inserat.getStatus().equals(InseratStatus.ONLINE)) {
            return "Vorgang abgebrochen. Nur Inserate mit Status \"online\" können gekauft werden.";
        }

        // erstelle neuen Kauf
        String anbieterUsername = inserat.getInserentUsername();
        String zahlungsdaten = käufer.getZahlungsdaten();
        Date kaufdatum = new Date();
        Kauf kauf = new Kauf(käuferUsername, anbieterUsername, inserat, kaufdatum, KaufStatus.INITIIERT);

        // ziehe Geld von käufer ein
        String response = geldEinziehen(zahlungsdaten);
        if (response.equals("success")) {
            // setze kaufstatus auf "bezahlt"
            kauf.setStatus(KaufStatus.BEZAHLT);
            // persistiere Kauf in DB
            kaufRepository.save(kauf);
            return "Kauf vorgemerkt, Zahlung bestätigt, Bestätigung des Anbieters steht noch aus";
        } else {
            // Response durchreichen und Fehlermeldung ausgeben
            return "Der Vorgang konnte nicht abgeschlossen werden: PaymentProvider returned " + response;
        }
    }


    public String bestätigungKauf(String kaufID) {
        Kauf kauf = kaufRepository.findKaufByKaufID(kaufID);
        if (kauf != null) {
            return bestätigungKauf(kauf);
        } else {
            return "Kauf \"" + kaufID + "\" konnte nicht gefunden werden";
        }
    }

    public String ablehnungKauf(String kaufID) {
        Kauf kauf = kaufRepository.findKaufByKaufID(kaufID);
        if (kauf != null) {
            return ablehnungKauf(kauf);
        } else {
            return "Kauf \"" + kaufID + "\" konnte nicht gefunden werden";
        }
    }

    public String bestätigungKauf(Kauf kauf) {
        // Überprüfe, ob das Inserat tatsächlich noch ONLINE ist
        String inseratID = kauf.getInserat().getInseratID();
        Inserat inserat = inseratService.getInserat(inseratID);
        if (!inserat.getStatus().equals(InseratStatus.ONLINE)) {
            return "Vorgang abgebrochen. Nur Inserate mit Status \"online\" können gekauft werden.";
        }

        // sende Geld an Inserent
        String inserentUsername = inserat.getInserentUsername();
        User inserent = userService.getUser(inserentUsername);
        String zahlungsdaten = inserent.getZahlungsdaten();
        String paymentResponse = geldSenden(zahlungsdaten);

        if (paymentResponse.equals("success")) {
            // deaktiviere Inserat
            inseratService.deaktiviereInserat(inserat);
            // aktualisiere Kaufstatus "abgeschlossen"
            kauf.setStatus(KaufStatus.ABGESCHLOSSEN);
            kaufRepository.save(kauf);
            // lehne alle weiteren Käufe für dieses Inserat ab
            List<Kauf> weitereKäufe = getKäufeFürInseratID(inserat.getInseratID());
            for (Kauf weitererKauf : weitereKäufe) {
                ablehnungKauf(weitererKauf);
            }
            // benachrichtige Käufer per E-Mail
            String käuferUsername = kauf.getKäuferUsername();
            User käufer = userService.getUser(käuferUsername);
            String empfänger = käufer.getEmail();
            String betreff = "Kauf abgeschlossen";
            String inhalt = "Kauf abgeschlossen";
            sendeMail(empfänger, betreff, inhalt);
            return "Kauf abgeschlossen";
        } else {
            // sende mail an Inserent: "Etwas ist schiefgelaufen, bitte bestätigen Sie nochmals den Kauf"
            String empfänger = inserent.getEmail();
            String betreff = "Der Vorgang konnte nicht abgeschlossen werden";
            String inhalt = "Etwas ist schiefgelaufen, bitte bestätigen Sie nochmals den Kauf";
            sendeMail(empfänger, betreff, inhalt);
            return "Der Vorgang konnte nicht abgeschlossen werden: PaymentProvider returned " + paymentResponse;
        }
    }

    public String ablehnungKauf(Kauf kauf) {
        // Überprüfe, ob das Inserat tatsächlich noch ONLINE ist
        String inseratID = kauf.getInserat().getInseratID();
        Inserat inserat = inseratService.getInserat(inseratID);
        if (!inserat.getStatus().equals(InseratStatus.ONLINE)) {
            return "Vorgang abgebrochen. Nur Inserate mit Status \"online\" können gekauft werden.";
        }

        // sende Geld zurück an Käufer
        String käuferUsername = kauf.getKäuferUsername();
        User käufer = userService.getUser(käuferUsername);
        String zahlungsdaten = käufer.getZahlungsdaten();
        String paymentResponse = geldSenden(zahlungsdaten);

        if (paymentResponse.equals("success")) {
            // aktualisiere Kaufstatus "abgebrochen"
            kauf.setStatus(KaufStatus.ABGEBROCHEN);
            kaufRepository.save(kauf);
            // benachrichtige Käufer per E-Mail
            String empfänger = käufer.getEmail();
            String betreff = "Kauf abgebrochen";
            String inhalt = "Kauf abgebrochen";
            sendeMail(empfänger, betreff, inhalt);
            return "Kauf abgebrochen";
        } else {
            // sende mail an Inserent: "Etwas ist schiefgelaufen, bitte bestätigen Sie nochmals den Kauf"
            String inserent_username = inserat.getInserentUsername();
            User inserent = userService.getUser(inserent_username);
            String empfänger = inserent.getEmail();
            String betreff = "Der Vorgang konnte nicht abgeschlossen werden";
            String inhalt = "Etwas ist schiefgelaufen, bitte bestätigen Sie nochmals den Kauf";
            sendeMail(empfänger, betreff, inhalt);
            return "Der Vorgang konnte nicht abgeschlossen werden: PaymentProvider returned " + paymentResponse;
        }
    }

    public List<Kauf> getKäufeFürKäufer(String username) {
        return kaufRepository.findKaufsByKäuferUsername(username);
    }

    public List<Kauf> getKäufeFürAnbieter(String username) {
        return kaufRepository.findKaufsByAnbieterUsername(username);
    }

    public List<Kauf> getKäufeFürInseratID(String inseratID) {
        return kaufRepository.findKaufsByInserat_InseratID(inseratID);
    }

    public String starteWiderruf(String kaufID, boolean tierBeiKäufer) {
        Kauf kauf = kaufRepository.findKaufByKaufID(kaufID);

        if (kauf == null) {
            return "Der Vorgang konnte nicht abgeschlossen werden: Ungültige kaufID";
        }

        if (!(kauf.getStatus() == KaufStatus.ABGESCHLOSSEN)) {
            return "Vorgang abgebrochen: Ein Widerruf kann nur für abgeschlossene Käufe eingereicht werden. "
                    + "Kaufstatus ist: " + kauf.getStatus();
        }

        // ziehe Geld von Anbieter ein
        Inserat inserat = kauf.getInserat();
        String username = inserat.getInserentUsername();
        User inserent = userService.getUser(username);
        String zahlungsdaten = inserent.getZahlungsdaten();
        String paymentResponse = geldEinziehen(zahlungsdaten);
        if (!paymentResponse.equals("success")) {
            return "Der Vorgang konnte nicht abgeschlossen werden: PaymentProvider returned " + paymentResponse;
        }
        // aktualisiere Kaufstatus: "Widerruf eingeleitet"
        kauf.setStatus(KaufStatus.WIDERRUF_EINGELEITET);
        kaufRepository.save(kauf);

        if (tierBeiKäufer) {
            return "Widerruf eingeleitet, warte auf Bestätigung der Rückgabe des Tieres an den Anbieter";
        }

        return schließeWiderruf(kaufID);
    }

    public String schließeWiderruf(String kaufID) {
        Kauf kauf = kaufRepository.findKaufByKaufID(kaufID);

        if (kauf == null) {
            return "Der Vorgang konnte nicht abgeschlossen werden: Ungültige kaufID";
        }

        if (!(kauf.getStatus() == KaufStatus.WIDERRUF_EINGELEITET)) {
            return "Vorgang abgebrochen: Für Kauf " + kaufID + " ist aktuell kein Widerruf eingeleitet. "
                    + "Kaufstatus ist: " + kauf.getStatus();
        }

        // sende Geld an Käufer
        String käuferUsername = kauf.getKäuferUsername();
        User käufer = userService.getUser(käuferUsername);
        String zahlungsdaten = käufer.getZahlungsdaten();
        String paymentResponse = geldSenden(zahlungsdaten);
        if (!paymentResponse.equals("success")) {
            return "Der Vorgang konnte nicht abgeschlossen werden: PaymentProvider returned " + paymentResponse;
        }
        // aktualisiere Kaufstatus: "Widerruf abgeschlossen"
        kauf.setStatus(KaufStatus.WIDERRRUF_ABGESCHLOSSEN);
        kaufRepository.save(kauf);

        // TODO: benachrichtige Käufer per E-Mail
        return "Rückzahlung bestätigt, Widerruf abgeschlossen";
    }

    public List<Kauf> getAlleKäufe() {
        return kaufRepository.findAll();
    }

    public String löscheKauf(String kaufID) {
        kaufRepository.deleteById(kaufID);
        return "Löschung erfolgreich";
    }
}
