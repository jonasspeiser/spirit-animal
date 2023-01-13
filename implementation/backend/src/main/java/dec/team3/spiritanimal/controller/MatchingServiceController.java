package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.services.AuthService;
import dec.team3.spiritanimal.services.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping("/api/soulsearch")
@Controller
public class MatchingServiceController {

    @Autowired
    MatchingService matchingService;

    @Autowired
    AuthService authService;

    private String authenticateAndGetUsername(String token) {
        // Authentication
        if (!authService.isTokenValid(token)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(401), "Invalid Authorization");
        }
        // Authorisation by name
        return authService.getUsername(token);
    }

    @GetMapping("/start")
    @ResponseBody
    public List<Inserat> startSoulSearch(@RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);
        return matchingService.getSwipeStack(username);
    }

    @GetMapping("/mydarlings")
    @ResponseBody
    public List<Inserat> getFavoriten(@RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);
        return matchingService.getFavoritenFürUser(username);
    }

    @DeleteMapping("/mydarlings/{inseratID}")
    @ResponseBody
    public String löscheFavorit(@PathVariable String inseratID, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);
        return matchingService.löscheFavorit(inseratID, username);
    }

    @PostMapping("/like")
    @ResponseBody
    public String likeInserat(@RequestBody String inseratID, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);
        return matchingService.likeInserat(inseratID, username);
    }

    @PostMapping("/dislike")
    @ResponseBody
    public String dislikeInserat(@RequestBody String inseratID, @RequestHeader("Authorization") String token) {
        String username = authenticateAndGetUsername(token);
        return matchingService.dislikeInserat(inseratID, username);
    }
}
