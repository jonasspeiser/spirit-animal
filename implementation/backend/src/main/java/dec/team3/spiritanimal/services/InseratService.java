package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.model.InseratStatus;
import dec.team3.spiritanimal.repositories.InseratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InseratService {

    @Autowired
    InseratRepository inseratRepository;
    @Autowired
    UserService userService;

    // TODO

    public Inserat getInserat(String inseratID) {
        return inseratRepository.findInseratByInseratID(inseratID);
    }

    public String deaktiviereInserat(Inserat inserat) {
        inserat.setStatus(InseratStatus.DEAKTIVIERT);
        inseratRepository.save(inserat);
        return "Inserat deaktiviert";
    }

    // Methode zur Erstellung eines neuen Inserats

    public Inserat createInserat(Inserat inserat, String inserentUsername) {

        inserat.setInserentUsername(inserentUsername);
        inserat.setStatus(InseratStatus.ONLINE);
        inseratRepository.save(inserat);
        return inseratRepository.findInseratByInseratID(inserat.getInseratID());
    }

    // Methode zum Löschen eines Inserats

    public String deleteInserat(String inseratID, String username) {
        Inserat inserat = inseratRepository.findInseratByInseratID(inseratID);
        if (inserat == null) {
            return "Inserat existiert nicht.";
        }
        // lösche nur, wenn inserent und übergebener Username übereinstimmen
        if (username.equals(inserat.getInserentUsername())) {
            inseratRepository.deleteById(inseratID);
            return inseratID + " wurde gelöscht!";
        }
        return "Unauthorized";
    }

    // Methode zum Updaten eines Inserats

    public Inserat updateInserat(Inserat changes, String inseratID, String username) throws IllegalAccessException {
        Inserat inseratToBeChanged = inseratRepository.findInseratByInseratID(inseratID);

        if (username.equals(inseratToBeChanged.getInserentUsername())) {
            inseratToBeChanged.setAlter(changes.getAlter());
            inseratToBeChanged.setBeschreibung(changes.getBeschreibung());
            inseratToBeChanged.setFoto(changes.getFoto());
            inseratToBeChanged.setKategorie(changes.getKategorie());
            inseratToBeChanged.setPreis(changes.getPreis());
            inseratToBeChanged.setTiername(changes.getTiername());

            inseratRepository.save(inseratToBeChanged);
            return inseratRepository.findInseratByInseratID(inseratID);
        } else {
            throw new IllegalAccessException();
        }


    }

    // Methode zur Premium-Schaltung eines Inserats
    public String updatePremium(String inseratID, String username) {
        Inserat inseratToBeChanged = inseratRepository.findInseratByInseratID(inseratID);

        if (inseratToBeChanged == null) {
            return "Inserat existiert nicht.";
        }
        // setze nur auf premium, wenn inserent und übergebener Username übereinstimmen
        if (username.equals(inseratToBeChanged.getInserentUsername())) {
            inseratToBeChanged.setPremium(true);
            inseratRepository.save(inseratToBeChanged);
            return inseratID + " ist jetzt Premium";
        }
        return "Unauthorized";
    }

    // TODO: Batch-Funktionalität implementieren

    // Methode zur Rückgabe aller Inserate

    public List<Inserat> getAllInserate() {
        return inseratRepository.findAll();
    }

    // Methode zur Rückgabe aller Inserate/User

    public List<Inserat> getInserateProUser (String user) {
        return inseratRepository.findInseratsByInserentUsername(user);
    }

}
