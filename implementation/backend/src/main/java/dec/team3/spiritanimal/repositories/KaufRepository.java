package dec.team3.spiritanimal.repositories;

import dec.team3.spiritanimal.model.Kauf;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KaufRepository extends MongoRepository<Kauf, String> {
    Kauf findKaufByKaufID(String kaufID);

    Kauf[] findKaufsByKäufer_Username(String käuferUsername);

    Kauf[] findKaufsByInserat_Inserent_Username(String anbieterUsername);
}
