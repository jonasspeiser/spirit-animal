package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.model.Role;
import dec.team3.spiritanimal.model.dto.InseratIDRequestDTO;
import dec.team3.spiritanimal.services.AuthService;
import dec.team3.spiritanimal.services.InseratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
        Role rolle = Role.USER;
        if (authService.authorizeUserRole(Role.ADMIN, token)) {
            rolle = Role.ADMIN;
        }
        String result = inseratService.deleteInserat(inseratID, username, rolle);
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

    @GetMapping(value = "/api/inserate", params = "user")
    @ResponseBody
    public List<Inserat> getInserateProUser(@RequestParam String user) {
        return inseratService.getInserateProUser(user);
    }

    @GetMapping(value = "/api/inserate", params = "inseratID")
    @ResponseBody
    public Inserat getInserat(@RequestParam String inseratID) {
        return inseratService.getInserat(inseratID);
    }

    //test API
    @GetMapping("/api/test")
    @ResponseBody
    public String string() {
        return "success";
    }

    // Batchfähige B2B Endpoints
    @PostMapping("/api/inserate/batch")
    @ResponseBody
    public List<Inserat> batchCreateInserate(@RequestBody List<Inserat> inserate, @RequestHeader("Authorization") String token) {
        ArrayList<Inserat> created = new ArrayList<>();
        for (Inserat inserat : inserate) {
            created.add(createInserat(inserat, token));
        }
        return created;
    }

    @PutMapping("/api/inserate/batch")
    @ResponseBody
    public List<Inserat> batchUpdateInserate(@RequestBody List<Inserat> inserate, @RequestHeader("Authorization") String token) {
        ArrayList<Inserat> changed = new ArrayList<>();
        for (Inserat inserat : inserate) {
            String inseratID = inserat.getInseratID();
            changed.add(updateInserat(inserat, inseratID, token));
        }
        return changed;
    }

    @PostMapping("/api/inserate/premium/batch")
    @ResponseBody
    public String batchUpdatePremium(@RequestBody List<InseratIDRequestDTO> inseratIDRequestDTOS, @RequestHeader("Authorization") String token) {
        for (InseratIDRequestDTO inseratIDRequestDTO : inseratIDRequestDTOS) {
            String inseratID = inseratIDRequestDTO.getInseratID();
            updatePremium(inseratID, token);
        }
        return "Inserate erfolgreich auf Premium geupdatet";
    }
}
