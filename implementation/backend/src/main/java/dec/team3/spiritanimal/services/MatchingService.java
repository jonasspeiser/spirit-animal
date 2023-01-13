package dec.team3.spiritanimal.services;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchingService {

    @Autowired
    UserService userService;

    @Autowired
    InseratService inseratService;
    public List<Inserat> getSwipeStack(String username) {
        List<Inserat> alleInserate = inseratService.getAllInserate();
        List<Inserat> eigeneInserate = inseratService.getInserateProUser(username);
        List<String> geseheneInserateIDs = userService.getUser(username).getGeseheneInserateIDs();
        List<Inserat> geseheneInserate = new ArrayList<>();
        for (String inseratID : geseheneInserateIDs) {
            geseheneInserate.add(inseratService.getInserat(inseratID));
        }

        List<Inserat> swipeStack = alleInserate;
        swipeStack.removeAll(eigeneInserate);
        swipeStack.removeAll(geseheneInserate);

        return swipeStack;
    }

    public List<Inserat> getFavoritenFürUser(String username) {
        List<String> favorisierteInserateIDs = userService.getUser(username).getFavorisierteInserateIDs();
        List<Inserat> favorisierteInserate = new ArrayList<>();
        for (String inseratID : favorisierteInserateIDs) {
            favorisierteInserate.add(inseratService.getInserat(inseratID));
        }
        return favorisierteInserate;
    }

    public String löscheFavorit(String inseratID, String username) {
        userService.removeFavorisiertesInserat(inseratID, username);
        return "Löschung erfolgreich";
    }

    public String likeInserat(String inseratID, String username) {
        userService.addGesehenesInserat(inseratID, username);
        userService.addFavorisiertesInserat(inseratID, username);
        return "Inserat zu Favoriten hinzugefügt.";
    }

    public String dislikeInserat(String inseratID, String username) {
        userService.addGesehenesInserat(inseratID, username);
        return "Inserat zu bereits gesehenen hinzugefügt.";
    }
}
