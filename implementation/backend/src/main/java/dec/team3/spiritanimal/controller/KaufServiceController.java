package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Kauf;
import dec.team3.spiritanimal.services.KaufService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class KaufServiceController {

    @Autowired
    private KaufService kaufService;

    // TODO: Fehlercode 400 retournieren falls verlangtes Zeug nicht enthalten ist
    @GetMapping(value = "/api/kaeufe", params = "kaeufer")
    @ResponseBody
    public Kauf[] getKäufeFürKäufer(@RequestParam String kaeuferUsername) {
        // TODO: Absichern, dass ein User wirklich nur eigene Käufe einsehen kann (Authorisierung)
        return kaufService.getKäufeFürKäufer(kaeuferUsername);
    }

    @GetMapping(value = "/api/kaeufe", params = "anbieter")
    @ResponseBody
    public Kauf[] getKäufeFürAnbieter(@RequestParam String anbieterUsername) {
        // TODO: Absichern, dass ein User wirklich nur eigene Käufe einsehen kann (Authorisierung)
        return kaufService.getKäufeFürAnbieter(anbieterUsername);
    }


    // TODO: Batchfähigkeit wie in A2 beschrieben (Übergabe eines Arrays mit mehreren IDs)
    @PostMapping("/api/kaeufe")
    @ResponseBody
    public String starteKauf(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String username = json.get("username").toString();
        String inseratID = json.get("inseratID").toString();
        String zahlungsdaten = json.get("zahlungsdaten").toString();
        return kaufService.starteKauf(username, inseratID, zahlungsdaten);
    }

    @PatchMapping("api/kaeufe/{kaufID}")
    @ResponseBody
    public String beendeKauf(@RequestBody String request){
        JSONObject json = new JSONObject(request);
        String kaufID = json.get("kaufID").toString();
        String aktion = json.get("aktion").toString();
        if (aktion.equals("ablehnen")) {
            return kaufService.ablehnungKauf(kaufID);
        }
        if (aktion.equals("bestätigen")) {
            return kaufService.bestätigungKauf(kaufID);
        } else {
            //TODO: Return code 400
            return "Etwas ist schiefgelaufen";
        }
    }
}
