package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.model.Kauf;
import dec.team3.spiritanimal.model.User;
import dec.team3.spiritanimal.repositories.KaufRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KaufService {

    @Autowired
    KaufRepository kaufRepository;

    // TODO

    public String starteKauf(User käufer, Inserat inserat, String zahlungsdaten) {
        // erstelle neuen Kauf in DB
        // ziehe Geld von käufer ein
        // setze kaufstatus auf "bezahlt"
        return "Zahlung bestätigt";
    }

    public String bestätigungKauf(Kauf kauf) {
        // sende Geld an Inserent
        // deaktiviere Inserat
        // aktualisiere Kaufstatus "abgeschlossen"
        // benachrichtige Käufer per E-Mail
        return "Kauf abgeschlossen";
    }

    public String ablehnungKauf(Kauf kauf) {
        // sende Geld zurück an Käufer
        // aktualisiere Kaufstatus "abgebrochen"
        // benachrichtige Käufer per E-Mail
        return "Kauf abgebrochen";
    }
}
