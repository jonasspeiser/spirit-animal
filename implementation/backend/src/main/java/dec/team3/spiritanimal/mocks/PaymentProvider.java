package dec.team3.spiritanimal.mocks;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

// This is a simple simulation of a PaymentProvider's API
@RequestMapping("/mock")
@Controller
public class PaymentProvider {

    @PostMapping("/collectMoney")
    @ResponseBody
    public String collectMoney(@RequestBody String zahlungsdaten) {
        return "success";
    }

    @PostMapping("/sendMoney")
    @ResponseBody
    public String sendMoney(@RequestBody String zahlungsdaten) {
        return "success";
    }

}
