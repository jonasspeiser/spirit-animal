package dec.team3.spiritanimal.repositories;

import dec.team3.spiritanimal.model.Kategorie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KategorieRepository extends MongoRepository<Kategorie, String> {
    // TODO
}
