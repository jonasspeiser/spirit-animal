package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.services.KaufService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KaufServiceController {

    @Autowired
    private KaufService kaufService;

    @PostMapping("/api/kaufe")
    @ResponseBody
    public String starteKauf(@RequestBody String request) {
        JSONObject json = new JSONObject(request);
        String userID = json.get("username").toString();
        String inseratID = json.get("inseratID").toString();
        String zahlungsdaten = json.get("zahlungsdaten").toString();
        return kaufService.starteKauf(userID, inseratID, zahlungsdaten);
    }
}
