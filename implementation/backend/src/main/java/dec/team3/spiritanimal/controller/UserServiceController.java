package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Präferenz;
import dec.team3.spiritanimal.model.Role;
import dec.team3.spiritanimal.model.User;
import dec.team3.spiritanimal.services.UserService;
import jakarta.validation.Valid;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/api")
@Controller
public class UserServiceController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody String request) {
        JSONObject userdata = new JSONObject(request);
        String username = userdata.getString("username");
        String password = userdata.getString("password");
        return userService.login(username, password);
    }

    @PostMapping("/users")
    @ResponseBody
    public String storeUser(@Valid @RequestBody User user) throws Exception {
        if(user.getUsername()!=null){
            User newUser = userService.storeUser(user);
            return "User " + newUser.getUsername() + " created successfully.";
        }else{
            throw new Exception("Please add a Username.");
        }
    }

    @GetMapping("/users/{user}")
    @ResponseBody
    public Präferenz getPreferenceForUser(@PathVariable String user, @RequestHeader("Authorization") String token) {
        return userService.getPreferenceForUser(user);
    }

    @PutMapping("/users/{user}")
    @ResponseBody
    public Präferenz updateUserPreference(@RequestBody Präferenz changes, @PathVariable String user, @RequestHeader("Authorization") String token) {
        return userService.updateUserPreference(changes, user);
    }
}
