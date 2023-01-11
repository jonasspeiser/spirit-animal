package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.Präferenz;
import dec.team3.spiritanimal.model.Role;
import dec.team3.spiritanimal.model.User;
import dec.team3.spiritanimal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthService authService;

    public User storeUser(User user) {
        // Todo: Aktuell werden user einfach überschrieben. Soll aber Fehler schmeißen, wenn User bereits existiert
        userRepository.save(user);
        return userRepository.findUserByUsername(user.getUsername());
    }

    public String login(String username, String password) {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            return "User " + username + " nicht vorhanden.";
        }

        if (user.getPassword().equals(password)) {
            Role role = Role.USER;
            String authToken = authService.createToken(username, role);
            user.setToken(authToken);
            userRepository.save(user);
            return authToken;
        }
        return "Login fehlgeschlagen. Falsches Passwort.";
    }

    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    public String getTokenForUser(String username) {
        return userRepository.findUserByUsername(username).getToken();
    }

    // Methode zum Anzeigen der Präferenz
    public Präferenz getPreferenceForUser(String user) {
        return userRepository.findPreferenceByUsername(user);
    }

    // Methode zum Ändern der Präferenz
    public Präferenz updateUserPreference(Präferenz changes, String user) {
        User userToBeChanged = userRepository.findUserByUsername(user);
        Präferenz preferenceToBeChanged = userToBeChanged.getPräferenz();

        if (preferenceToBeChanged == null) {
            preferenceToBeChanged = new Präferenz();
        }

        preferenceToBeChanged.setKategorien(changes.getKategorien());
        preferenceToBeChanged.setAlterMax(changes.getAlterMax());
        preferenceToBeChanged.setPreisMax(changes.getPreisMax());
        preferenceToBeChanged.setAlterMin(changes.getAlterMin());
        preferenceToBeChanged.setPreisMin(changes.getPreisMin());

        userToBeChanged.setPräferenz(preferenceToBeChanged);
        userRepository.save(userToBeChanged);
        return preferenceToBeChanged;
    }

}
