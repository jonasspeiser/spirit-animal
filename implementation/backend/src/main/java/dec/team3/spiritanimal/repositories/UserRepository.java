package dec.team3.spiritanimal.repositories;

import dec.team3.spiritanimal.model.Präferenz;
import dec.team3.spiritanimal.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<User, String> {

    User findUserByUsername(String username);

    void deleteUserByUsername(String username);

    Präferenz findPreferenceByUsername(String username);
}
