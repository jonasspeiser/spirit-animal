package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.User;
import dec.team3.spiritanimal.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;


@Controller
public class UserServiceController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/users")
    @ResponseBody
    public String storeUser(@RequestBody User user) throws Exception {
        if(user.getUsername()!=null){
            User newUser = userService.storeUser(user);
            return "User " + newUser.getUsername() + " created successfully.";
        }else{
            throw new Exception("Please add a Username.");
        }
    }
}
