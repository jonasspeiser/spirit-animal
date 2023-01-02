package dec.team3.spiritanimal.repositories;

import dec.team3.spiritanimal.model.Inserat;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InseratRepository extends MongoRepository<Inserat, String> {
    // TODO

    Inserat findInseratByInseratID(String inseratID);
}
