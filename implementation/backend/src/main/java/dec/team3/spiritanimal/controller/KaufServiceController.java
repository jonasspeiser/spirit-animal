package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.services.KaufService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KaufServiceController {

    @Autowired
    private KaufService kaufService;

    @PostMapping("/api/kaeufe")
    @ResponseBody
    public String starteKauf(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String userID = json.get("username").toString();
        String inseratID = json.get("inseratID").toString();
        String zahlungsdaten = json.get("zahlungsdaten").toString();
        return kaufService.starteKauf(userID, inseratID, zahlungsdaten);
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
