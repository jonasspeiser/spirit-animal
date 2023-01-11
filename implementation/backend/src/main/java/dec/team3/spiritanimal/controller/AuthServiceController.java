package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Role;
import dec.team3.spiritanimal.services.AuthService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/auth")
@Controller
public class AuthServiceController {

    @Autowired
    AuthService authService;

    @PostMapping("/createToken")
    @ResponseBody
    public String createToken(@RequestBody String request) {
        JSONObject userdata = new JSONObject(request);
        String username = userdata.getString("username");
        Role role = Role.valueOf(userdata.getString("role"));
        return authService.createToken(username, role);
    }

    @PostMapping("/isValid")
    @ResponseBody
    public boolean isTokenValid(@RequestBody String request) {
        JSONObject userdata = new JSONObject(request);
        String token = userdata.getString("token");
        return authService.isTokenValid(token);
    }

    @PostMapping("/getUsername")
    @ResponseBody
    public String getUsername(@RequestBody String request) {
        JSONObject userdata = new JSONObject(request);
        String token = userdata.getString("token");
        return authService.getUsername(token);
    }

    @PostMapping("/authorizeRole")
    @ResponseBody
    public boolean authorizeUserRole(@RequestBody String request) {
        JSONObject userdata = new JSONObject(request);
        String token = userdata.getString("token");
        Role role = Role.valueOf(userdata.getString("role"));
        return authService.authorizeUserRole(role, token);
    }


}
