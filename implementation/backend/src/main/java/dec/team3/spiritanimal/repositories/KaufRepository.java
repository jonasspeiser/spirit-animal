package dec.team3.spiritanimal.repositories;

import dec.team3.spiritanimal.model.Kauf;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface KaufRepository extends MongoRepository<Kauf, String> {
    Kauf findKaufByKaufID(String kaufID);

    List<Kauf> findKaufsByKäufer_Username(String käuferUsername);

    List<Kauf> findKaufsByInserat_Inserent_Username(String anbieterUsername);

    List<Kauf> findKaufsByInserat_InseratID(String inseratID);
}
