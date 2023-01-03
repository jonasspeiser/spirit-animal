package dec.team3.spiritanimal.repositories;

import dec.team3.spiritanimal.model.Präferenz;
import dec.team3.spiritanimal.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface UserRepository extends MongoRepository<User, String> {

    User findUserByUsername(String username);

    void deleteUserByUsername(String username);

    List<Präferenz> findPreferenceByUsername(String username);
}
