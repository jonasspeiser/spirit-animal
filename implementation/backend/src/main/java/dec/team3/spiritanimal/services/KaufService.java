package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.model.Kauf;
import dec.team3.spiritanimal.model.KaufStatus;
import dec.team3.spiritanimal.model.User;
import dec.team3.spiritanimal.repositories.KaufRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

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

    public String starteKauf(String käuferUsername, String inseratID, String zahlungsdaten) {
        User käufer = userService.getUser(käuferUsername);
        Inserat inserat = inseratService.getInserat(inseratID);
        if (käufer != null && inserat != null) {
            return starteKauf(käufer, inserat, zahlungsdaten);
        } else {
            return "Username \"" + käuferUsername + "\" oder Inserat \"" + inseratID + "\" konnte nicht gefunden werden";
        }
    }

    public String starteKauf(User käufer, Inserat inserat, String zahlungsdaten) {
        // erstelle neuen Kauf
        Date kaufdatum = new Date();
        Kauf kauf = new Kauf(käufer, inserat, kaufdatum, KaufStatus.INITIIERT);

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
        // sende Geld an Inserent
        String zahlungsdaten = kauf.getInserat().getInserent().getZahlungsdaten();
        String paymentResponse = geldSenden(zahlungsdaten);

        if (paymentResponse.equals("success")) {
            // deaktiviere Inserat
            inseratService.deaktiviereInserat(kauf.getInserat());
            // aktualisiere Kaufstatus "abgeschlossen"
            kauf.setStatus(KaufStatus.ABGESCHLOSSEN);
            kaufRepository.save(kauf);
            // benachrichtige Käufer per E-Mail
            String empfänger = kauf.getKäufer().getEmail();
            String betreff = "Kauf abgeschlossen";
            String inhalt = "Kauf abgeschlossen";
            sendeMail(empfänger, betreff, inhalt);
            return "Kauf abgeschlossen";
        } else {
            // sende mail an Inserent: "Etwas ist schiefgelaufen, bitte bestätigen Sie nochmals den Kauf"
            String empfänger = kauf.getInserat().getInserent().getEmail();
            String betreff = "Der Vorgang konnte nicht abgeschlossen werden";
            String inhalt = "Etwas ist schiefgelaufen, bitte bestätigen Sie nochmals den Kauf";
            sendeMail(empfänger, betreff, inhalt);
            return "Der Vorgang konnte nicht abgeschlossen werden: PaymentProvider returned " + paymentResponse;
        }
    }

    public String ablehnungKauf(Kauf kauf) {
        // sende Geld zurück an Käufer
        String zahlungsdaten = kauf.getKäufer().getZahlungsdaten();
        // TODO: Inkonsistenz auflösen: Käufer gibt bei Kauf Zahlungsdaten an, diese werden aber nicht im Kauf gespeichert
        // -> hier müssten also die Zahlungsdaten aus dem Kauf statt die des Users verwendet werden? Oder verwende ich einfach durchgehend die des User-Accounts? (auch für den Payment provider)
        // -> Testen, ob sich der im Inserat genestete User nicht zufällig magisch mitaktualisiert
        // -> Hinten angestellt, da ich mir eh noch Gedanken darüber machen wollte, ob wir nicht Geldkonten für den User vorhalten um einen komplexeren Handshake beim Kauf implementieren zu können
        String paymentResponse = geldSenden(zahlungsdaten);

        if (paymentResponse.equals("success")) {
            // deaktiviere Inserat
            inseratService.deaktiviereInserat(kauf.getInserat());
            // aktualisiere Kaufstatus "abgebrochen"
            kauf.setStatus(KaufStatus.ABGEBROCHEN);
            kaufRepository.save(kauf);
            // benachrichtige Käufer per E-Mail
            String empfänger = kauf.getKäufer().getEmail();
            String betreff = "Kauf abgebrochen";
            String inhalt = "Kauf abgebrochen";
            sendeMail(empfänger, betreff, inhalt);
            return "Kauf abgebrochen";
        } else {
            // sende mail an Inserent: "Etwas ist schiefgelaufen, bitte bestätigen Sie nochmals den Kauf"
            String empfänger = kauf.getInserat().getInserent().getEmail();
            String betreff = "Der Vorgang konnte nicht abgeschlossen werden";
            String inhalt = "Etwas ist schiefgelaufen, bitte bestätigen Sie nochmals den Kauf";
            sendeMail(empfänger, betreff, inhalt);
            return "Der Vorgang konnte nicht abgeschlossen werden: PaymentProvider returned " + paymentResponse;
        }
    }

    public Kauf[] getKäufeFürKäufer(String username) {
        return kaufRepository.getKaufsByKäufer_Username(username);
    }

    public Kauf[] getKäufeFürAnbieter(String username) {
        return kaufRepository.getKaufsByInserat_Inserent_Username(username);
    }

    public String starteWiderruf(String kaufID, boolean tierBeiKäufer) {
        Kauf kauf = kaufRepository.findKaufByKaufID(kaufID);
        // ziehe Geld von Anbieter ein
        String zahlungsdaten = kauf.getInserat().getInserent().getZahlungsdaten();
        String paymentResponse = geldEinziehen(zahlungsdaten);
        if (!paymentResponse.equals("success")) {
            return "Der Vorgang konnte nicht abgeschlossen werden: PaymentProvider returned " + paymentResponse;
        }
        // aktualisiere Kaufstatus: "Widerruf eingeleitet"
        kauf.setStatus(KaufStatus.WIDERRUF_EINGELEITET);

        if (tierBeiKäufer) {
            kaufRepository.save(kauf);
            return "Widerruf eingeleitet, warte auf Bestätigung der Rückgabe des Tieres an den Anbieter";
        }

        return schließeWiderruf(kaufID);
    }

    public String schließeWiderruf(String kaufID) {
        Kauf kauf = kaufRepository.findKaufByKaufID(kaufID);
        // sende Geld an Käufer
        String zahlungsdaten = kauf.getKäufer().getZahlungsdaten();
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
}
