package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.model.Kauf;
import dec.team3.spiritanimal.model.KaufStatus;
import dec.team3.spiritanimal.model.User;
import dec.team3.spiritanimal.repositories.KaufRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class KaufService {

    @Autowired
    KaufRepository kaufRepository;
    @Autowired
    InseratService inseratService;

    public String starteKauf(User käufer, Inserat inserat, String zahlungsdaten) {
        // erstelle neuen Kauf
        Date kaufdatum = new Date();
        Kauf kauf = new Kauf(käufer, inserat, kaufdatum, KaufStatus.INITIIERT);

        // ziehe Geld von käufer ein
        // TODO REST-Call an gemockten Payment Provider schicken. Auf Antwort reagieren.
        // -> Hier werden die zahlungsdaten an den Provider übermittelt
        String response = "success";
        if (response == "success") {
            // setze kaufstatus auf "bezahlt"
            kauf.setStatus(KaufStatus.BEZAHLT);
            // persistiere Kauf in DB
            kaufRepository.save(kauf);
            return "Zahlung bestätigt";
        } else {
            // Response durchreichen und Fehlermeldung ausgeben
            return "Der Vorgang konnte nicht abgeschlossen werden";
        }
    }

    public String bestätigungKauf(Kauf kauf) {
        // sende Geld an Inserent
        // TODO REST-Call an gemockten Payment Provider schicken. Auf Antwort reagieren.
        // -> Hier werden die zahlungsdaten an den Provider übermittelt
        String zahlungsdaten = kauf.getInserat().getInserent().getZahlungsdaten();
        String response = "success";
        if (response == "success") {
            // deaktiviere Inserat
            inseratService.deaktiviereInserat(kauf.getInserat());
            // aktualisiere Kaufstatus "abgeschlossen"
            kauf.setStatus(KaufStatus.ABGESCHLOSSEN);
            kaufRepository.save(kauf);
            // benachrichtige Käufer per E-Mail
            // TODO: Möglichkeit E-Mails zu senden einrichten!
            return "Kauf abgeschlossen";
        } else {
            // sende mail an Inserent: "Etwas ist schiefgelaufen, bitte bestätigen Sie nochmals den Kauf"
            // TODO
            return "Der Vorgang konnte nicht abgeschlossen werden";
        }
    }

    public String ablehnungKauf(Kauf kauf) {
        // sende Geld zurück an Käufer
        // aktualisiere Kaufstatus "abgebrochen"
        // benachrichtige Käufer per E-Mail
        return "Kauf abgebrochen";
    }
}
