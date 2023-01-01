package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.model.InseratStatus;
import dec.team3.spiritanimal.repositories.InseratRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InseratService {

    @Autowired
    InseratRepository inseratRepository;

    // TODO

    public String deaktiviereInserat(Inserat inserat) {
        inserat.setStatus(InseratStatus.DEAKTIVIERT);
        inseratRepository.save(inserat);
        return "Inserat deaktiviert";
    }
}
