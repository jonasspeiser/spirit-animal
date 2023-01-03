package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.services.InseratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class InseratServiceController {

    @Autowired
    private InseratService inseratService;

    @PostMapping("/api/inserate")
    @ResponseBody
    public Inserat createInserat(@RequestBody Inserat inserat) {
        return inseratService.createInserat(inserat);
    }

    @DeleteMapping("/api/inserate/{inseratID}")
    @ResponseBody
    public String deleteInserat(@PathVariable String inseratID) {
        return inseratService.deleteInserat(inseratID);
    }

    @PutMapping("/api/inserate/{inseratID}")
    @ResponseBody
    public Inserat updateInserat(@RequestBody Inserat changes, @PathVariable String inseratID) {
        return inseratService.updateInserat(changes, inseratID);
    }
}
