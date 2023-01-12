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

    // TODO: Authorization einrichten
    @PostMapping("/api/inserate")
    @ResponseBody
    public Inserat createInserat(@RequestBody Inserat inserat, @RequestHeader("Authorization") String token) {
        // Authorisation: User kann nur eigene Käufe sehen
        String username = authenticateAndGetUsername(token);

        return inseratService.createInserat(inserat, username);
    }

    // TODO: Authorization einrichten
    @DeleteMapping("/api/inserate/{inseratID}")
    @ResponseBody
    public String deleteInserat(@PathVariable String inseratID, @RequestHeader("Authorization") String token) {
        return inseratService.deleteInserat(inseratID);
    }

    // TODO: Authorization einrichten
    @PutMapping("/api/inserate/{inseratID}")
    @ResponseBody
    public Inserat updateInserat(@RequestBody Inserat changes, @PathVariable String inseratID, @RequestHeader("Authorization") String token) {
        return inseratService.updateInserat(changes, inseratID);
    }

    // TODO: Authorization einrichten
    @PatchMapping("/api/inserate/{inseratID}")
    @ResponseBody
    public String updatePremium(@PathVariable String inseratID, @RequestHeader("Authorization") String token) {
        return inseratService.updatePremium(inseratID);
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
        return "sucess";
    }
}
