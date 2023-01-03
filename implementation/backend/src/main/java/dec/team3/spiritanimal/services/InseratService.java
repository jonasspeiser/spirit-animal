package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.model.InseratStatus;
import dec.team3.spiritanimal.repositories.InseratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InseratService {

    @Autowired
    InseratRepository inseratRepository;

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

    public Inserat createInserat(Inserat inserat) {
        inseratRepository.save(inserat);
        return inseratRepository.findInseratByInseratID(inserat.getInseratID());
    }

    // Methode zum Löschen eines Inserats

    public String deleteInserat(String inseratID) {
        inseratRepository.deleteById(inseratID);
        return inseratID + " wurde gelöscht!";
    }

    // Methode zum Updaten eines Inserats

    public Inserat updateInserat(Inserat changes, String inseratID) {
        Inserat inseratToBeChanged = inseratRepository.findInseratByInseratID(inseratID);

        inseratToBeChanged.setAlter(changes.getAlter());
        inseratToBeChanged.setBeschreibung(changes.getBeschreibung());
        inseratToBeChanged.setFoto(changes.getFoto());
        inseratToBeChanged.setKategorie(changes.getKategorie());
        inseratToBeChanged.setPreis(changes.getPreis());
        inseratToBeChanged.setTiername(changes.getTiername());

        inseratRepository.save(inseratToBeChanged);
        return inseratRepository.findInseratByInseratID(inseratID);
    }

}
