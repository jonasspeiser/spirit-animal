package dec.team3.spiritanimal.repositories;

import dec.team3.spiritanimal.model.Kauf;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KaufRepository extends MongoRepository<Kauf, String> {
    Kauf findKaufByKaufID(String kaufID);

    Kauf[] getKaufsByKäufer_Username(String käuferUsername);

    Kauf[] getKaufsByInserat_Inserent_Username(String anbieterUsername);
}
