package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Kauf;
import dec.team3.spiritanimal.model.Role;
import dec.team3.spiritanimal.model.dto.InseratIDRequestDTO;
import dec.team3.spiritanimal.model.dto.WiderrufRequestDTO;
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


    @PostMapping("")
    @ResponseBody
    public String starteKauf(@RequestBody String request, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);

        JSONObject json = new JSONObject(request);
        String inseratID = json.get("inseratID").toString();
        return kaufService.starteKauf(username, inseratID);
    }

    @PostMapping("/akzeptieren")
    @ResponseBody
    public String bestätigungKauf(@RequestBody String request, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);

        JSONObject json = new JSONObject(request);
        String kaufID = json.get("kaufID").toString();
        return kaufService.bestätigungKauf(kaufID);
    }

    @PostMapping("/ablehnen")
    @ResponseBody
    public String ablehnungKauf(@RequestBody String request, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);

        JSONObject json = new JSONObject(request);
        String kaufID = json.get("kaufID").toString();
        return kaufService.ablehnungKauf(kaufID);
    }

    @PostMapping("/widerruf")
    @ResponseBody
    public String widerrufeKauf(@RequestBody String request, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);

        JSONObject json = new JSONObject(request);
        String kaufID = json.getString("kaufID");
        boolean tierBeiKäufer = json.getBoolean("tierBeiKäufer");
        return kaufService.starteWiderruf(kaufID, tierBeiKäufer);
    }

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

//    Batchfähige B2B Endpoints
    @PostMapping("/batch")
    @ResponseBody
    public String batchStarteKauf(@RequestBody List<InseratIDRequestDTO> inseratIDRequestDTOS, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);
        for (InseratIDRequestDTO inseratIDRequestDTO: inseratIDRequestDTOS) {
            String inseratID = inseratIDRequestDTO.getInseratID();
            kaufService.starteKauf(username, inseratID);
        }
        return "Kaufanfragen erfolgreich gesendet. Warte auf akzeptieren des Anbieters.";
    }

    @PostMapping("/widerruf/batch")
    @ResponseBody
    public String batchStarteWiderruf(@RequestBody List<WiderrufRequestDTO> widerrufe, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);
        for (WiderrufRequestDTO widerruf: widerrufe) {
            kaufService.starteWiderruf(username, widerruf.isTierBeiKäufer());
        }
        return "Widerrufe erfolgreich gestartet";
    }
}
