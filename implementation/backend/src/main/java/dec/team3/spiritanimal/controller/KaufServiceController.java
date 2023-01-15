package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Kauf;
import dec.team3.spiritanimal.model.Role;
import dec.team3.spiritanimal.services.AuthService;
import dec.team3.spiritanimal.services.KaufService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RequestMapping("/api/kaeufe")
@Controller
public class KaufServiceController {

    @Autowired
    private KaufService kaufService;

    @Autowired
    private AuthService authService;

    // TODO: DTOs statt Strings als RequestBodies erwarten (sprich, DTOs entsprechend der EDI-Sprache aus A2 definieren)
    // -> Alternativ Request-payload anhand des json-Schemas aus A2 überprüfen
    // TODO: Fehlercode 400 retournieren falls verlangtes Zeug nicht enthalten ist (ergibt sich wahrscheinlich aus obigem Todo)

    private String authenticateAndGetUsername(String token) {
        // Authentication
        if (!authService.isTokenValid(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid Authorization");
        }
        // Authorisation by name
        return authService.getUsername(token);
    }

    @GetMapping(value = "", params = "kaeufer")
    @ResponseBody
    public List<Kauf> getKäufeFürKäufer(@RequestHeader("Authorization") String token) {
        // Authorisation: User kann nur eigene Käufe sehen
        String kaeuferUsername = authenticateAndGetUsername(token);
        return kaufService.getKäufeFürKäufer(kaeuferUsername);
    }

    @GetMapping(value = "", params = "anbieter")
    @ResponseBody
    public List<Kauf> getKäufeFürAnbieter(@RequestHeader("Authorization") String token) {
        // Authorisation: User kann nur eigene Käufe sehen
        String anbieterUsername = authenticateAndGetUsername(token);
        return kaufService.getKäufeFürAnbieter(anbieterUsername);
    }


    // TODO: Batchfähigkeit wie in A2 beschrieben (Übergabe eines Arrays mit mehreren IDs)
    @PostMapping("")
    @ResponseBody
    public String starteKauf(@RequestBody String request, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);

        JSONObject json = new JSONObject(request);
        String inseratID = json.get("inseratID").toString();
        return kaufService.starteKauf(username, inseratID);
    }

    // TODO: User kann nur eigene Verkäufe akzeptieren oder ablehnen (Authorisierung)
    @PostMapping("/akzeptieren")
    @ResponseBody
    public String bestätigungKauf(@RequestBody String request, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);

        JSONObject json = new JSONObject(request);
        String kaufID = json.get("kaufID").toString();
        return kaufService.bestätigungKauf(kaufID);
    }

    // TODO: User kann nur eigene Verkäufe akzeptieren oder ablehnen (Authorisierung)
    @PostMapping("/ablehnen")
    @ResponseBody
    public String ablehnungKauf(@RequestBody String request, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);

        JSONObject json = new JSONObject(request);
        String kaufID = json.get("kaufID").toString();
        return kaufService.ablehnungKauf(kaufID);
    }

    // TODO: User kann nur eigene Käufe widerrufen (Authorisierung)
    @PostMapping("/widerruf")
    @ResponseBody
    public String widerrufeKauf(@RequestBody String request, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);

        JSONObject json = new JSONObject(request);
        String kaufID = json.getString("kaufID");
        boolean tierBeiKäufer = json.getBoolean("tierBeiKäufer");
        return kaufService.starteWiderruf(kaufID, tierBeiKäufer);
    }

//     TODO: User kann nur eigene Käufe widerrufen (Authorisierung)
    @PostMapping("/widerruf/schliessen")
    @ResponseBody
    public String schließeWiderruf(@RequestBody String request, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);

        JSONObject json = new JSONObject(request);
        String kaufID = json.getString("kaufID");
        return kaufService.schließeWiderruf(kaufID);
    }

//    Admin Usecase
    @CrossOrigin
    @GetMapping("")
    @ResponseBody
    public List<Kauf> getAlleKäufe(@RequestHeader("Authorization") String token) {
        //        require Admin Authorization
        if (!authService.authorizeUserRole(Role.ADMIN, token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Only Admins allowed!");
        }
        return kaufService.getAlleKäufe();
    }

    @DeleteMapping("/{kaufID}")
    @ResponseBody
    public String löscheKauf(@PathVariable String kaufID, @RequestHeader("Authorization") String token) {
        //        require Admin Authorization
        if (!authService.authorizeUserRole(Role.ADMIN, token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Only Admins allowed!");
        }
        return kaufService.löscheKauf(kaufID);
    }
}
