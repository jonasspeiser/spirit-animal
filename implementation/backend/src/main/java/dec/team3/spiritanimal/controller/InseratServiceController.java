package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.services.AuthService;
import dec.team3.spiritanimal.services.InseratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@Controller
public class InseratServiceController {

    @Autowired
    private InseratService inseratService;
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

    @PostMapping("/api/inserate")
    @ResponseBody
    public Inserat createInserat(@RequestBody Inserat inserat, @RequestHeader("Authorization") String token) {
        // Authorisation: User kann nur für sich selbst Inserate erstellen
        String username = authenticateAndGetUsername(token);

        return inseratService.createInserat(inserat, username);
    }

    @DeleteMapping("/api/inserate/{inseratID}")
    @ResponseBody
    public String deleteInserat(@PathVariable String inseratID, @RequestHeader("Authorization") String token) {
        // Authorisation: User kann nur eigene Inserate löschen
        String username = authenticateAndGetUsername(token);
        String result = inseratService.deleteInserat(inseratID, username);
        if (result.equals("Unauthorized")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Sie sind nicht authorisiert, die Inserate anderer User zu löschen.");
        }
        return result;
    }

    @PutMapping("/api/inserate/{inseratID}")
    @ResponseBody
    public Inserat updateInserat(@RequestBody Inserat changes, @PathVariable String inseratID, @RequestHeader("Authorization") String token) {
        // Authorisation: User kann nur eigene Inserate bearbeiten
        String username = authenticateAndGetUsername(token);
        try {
            return inseratService.updateInserat(changes, inseratID, username);
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Sie sind nicht authorisiert, die Inserate anderer User zu bearbeiten.");
        }
    }

    @PatchMapping("/api/inserate/{inseratID}")
    @ResponseBody
    public String updatePremium(@PathVariable String inseratID, @RequestHeader("Authorization") String token) {
        // Authorisation: User kann nur für eigene Inserate Premiumstatus beantragen
        String username = authenticateAndGetUsername(token);
        String result = inseratService.updatePremium(inseratID, username);
        if (result.equals("Unauthorized")) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Sie sind nicht authorisiert, die Inserate anderer User zu bearbeiten.");
        }
        return result;
    }

    @GetMapping("/api/inserate")
    @ResponseBody
    public List<Inserat> getAllInserate() {
        return inseratService.getAllInserate();
    }

    @GetMapping("/api/inserate/{user}")
    @ResponseBody
    public List<Inserat> getInserateProUser(@PathVariable String user) {
        return inseratService.getInserateProUser(user);
    }

    //test API
    @GetMapping("/api/test")
    @ResponseBody
    public String string() {
        return "success";
    }
}
