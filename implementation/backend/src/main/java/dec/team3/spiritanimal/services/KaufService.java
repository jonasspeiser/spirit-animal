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
            return "Zahlung bestätigt";
        } else {
            // Response durchreichen und Fehlermeldung ausgeben
            return "Der Vorgang konnte nicht abgeschlossen werden: PaymentProvider returned " + response;
        }
    }
    public String bestätigungKauf(Kauf kauf) {
        // sende Geld an Inserent
        String zahlungsdaten = kauf.getInserat().getInserent().getZahlungsdaten();
        String response = geldSenden(zahlungsdaten);

        if (response.equals("success")) {
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
            String empfänger = kauf.getKäufer().getEmail();
            String betreff = "Der Vorgang konnte nicht abgeschlossen werden";
            String inhalt = "Etwas ist schiefgelaufen, bitte bestätigen Sie nochmals den Kauf";
            sendeMail(empfänger, betreff, inhalt);
            return "Der Vorgang konnte nicht abgeschlossen werden: PaymentProvider returned " + response;
        }
    }

    public String ablehnungKauf(Kauf kauf) {
        // sende Geld zurück an Käufer
        // aktualisiere Kaufstatus "abgebrochen"
        // benachrichtige Käufer per E-Mail
        return "Kauf abgebrochen";
    }
}
