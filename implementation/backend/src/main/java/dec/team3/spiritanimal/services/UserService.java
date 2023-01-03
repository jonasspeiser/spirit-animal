package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.Präferenz;
import dec.team3.spiritanimal.model.User;
import dec.team3.spiritanimal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User storeUser(User user) {
        userRepository.save(user);
        return userRepository.findUserByUsername(user.getUsername());
    }

    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    // Methode zum Anzeigen der Präferenz

    public List<Präferenz> getPreferenceForUser(String user) {
        return userRepository.findPreferenceByUsername(user);
    }

}
