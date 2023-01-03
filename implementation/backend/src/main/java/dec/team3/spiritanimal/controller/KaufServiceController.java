package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Kauf;
import dec.team3.spiritanimal.services.KaufService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/kaeufe")
@Controller
public class KaufServiceController {

    @Autowired
    private KaufService kaufService;

    // TODO: DTOs statt Strings als RequestBodies erwarten (sprich, DTOs entsprechend der EDI-Sprache aus A2 definieren)
    // -> Alternativ Request-payload anhand des json-Schemas aus A2 überprüfen
    // TODO: Fehlercode 400 retournieren falls verlangtes Zeug nicht enthalten ist (ergibt sich wahrscheinlich aus obigem Todo)
    @GetMapping(value = "", params = "kaeufer")
    @ResponseBody
    public List<Kauf> getKäufeFürKäufer(@RequestParam String kaeuferUsername) {
        // TODO: Absichern, dass ein User wirklich nur eigene Käufe einsehen kann (Authorisierung)
        return kaufService.getKäufeFürKäufer(kaeuferUsername);
    }

    @GetMapping(value = "", params = "anbieter")
    @ResponseBody
    public List<Kauf> getKäufeFürAnbieter(@RequestParam String anbieterUsername) {
        // TODO: Absichern, dass ein User wirklich nur eigene Käufe einsehen kann (Authorisierung)
        return kaufService.getKäufeFürAnbieter(anbieterUsername);
    }


    // TODO: Batchfähigkeit wie in A2 beschrieben (Übergabe eines Arrays mit mehreren IDs)
    @PostMapping("")
    @ResponseBody
    public String starteKauf(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String username = json.get("username").toString();
        String inseratID = json.get("inseratID").toString();
        String zahlungsdaten = json.get("zahlungsdaten").toString();
        return kaufService.starteKauf(username, inseratID, zahlungsdaten);
    }

    // TODO: User kann nur eigene Verkäufe akzeptieren oder ablehnen (Authorisierung)
    @PostMapping("/akzeptieren")
    @ResponseBody
    public String bestätigungKauf(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String kaufID = json.get("kaufID").toString();
        return kaufService.bestätigungKauf(kaufID);
    }

    // TODO: User kann nur eigene Verkäufe akzeptieren oder ablehnen (Authorisierung)
    @PostMapping("/ablehnen")
    @ResponseBody
    public String ablehnungKauf(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String kaufID = json.get("kaufID").toString();
        return kaufService.ablehnungKauf(kaufID);
    }

    // TODO: User kann nur eigene Käufe widerrufen (Authorisierung)
    @PostMapping("/widerruf")
    @ResponseBody
    public String widerrufeKauf(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String kaufID = json.getString("kaufID");
        boolean tierBeiKäufer = json.getBoolean("tierBeiKäufer");
        return kaufService.starteWiderruf(kaufID, tierBeiKäufer);
    }

    // TODO: User kann nur eigene Käufe widerrufen (Authorisierung)
    @PostMapping("/widerruf/schliessen")
    @ResponseBody
    public String schließeWiderruf(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String kaufID = json.getString("kaufID");
        return kaufService.schließeWiderruf(kaufID);
    }
}
