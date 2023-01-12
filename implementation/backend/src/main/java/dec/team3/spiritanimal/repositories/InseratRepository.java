package dec.team3.spiritanimal.repositories;

import dec.team3.spiritanimal.model.Inserat;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InseratRepository extends MongoRepository<Inserat, String> {
    // TODO

    Inserat findInseratByInseratID(String inseratID);

    List<Inserat> findInseratsByInserentUsername(String userName);
}
