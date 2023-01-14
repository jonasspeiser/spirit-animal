package dec.team3.spiritanimal.repositories;

import dec.team3.spiritanimal.model.Kauf;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface KaufRepository extends MongoRepository<Kauf, String> {
    Kauf findKaufByKaufID(String kaufID);

    List<Kauf> findKaufsByKäuferUsername(String käuferUsername);

    List<Kauf> findKaufsByAnbieterUsername(String anbieterUsername);

    List<Kauf> findKaufsByInserat_InseratID(String inseratID);
}
