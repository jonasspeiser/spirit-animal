package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.model.Kategorie;
import dec.team3.spiritanimal.services.InseratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQLController {

    final
    InseratService inseratService;

    public GraphQLController(InseratService inseratService) {
        this.inseratService = inseratService;
    }

    @QueryMapping
    public Inserat getInserat(@Argument String inseratID) {
        return inseratService.getInserat(inseratID);
    }

    @QueryMapping
    public List<Inserat> getAlleInserate() {
        return inseratService.getAllInserate();
    }

    @QueryMapping
    public List<Inserat> getInserateFuerUser(@Argument String username){
        return inseratService.getInserateProUser(username);
    }

    @MutationMapping
    public Inserat createInserat(@Argument String inserentUsername, @Argument Kategorie kategorie, @Argument String tiername, @Argument String beschreibung, @Argument String alter, @Argument double preis, @Argument String foto) {
        Inserat inserat = new Inserat(null, inserentUsername, kategorie, null, false, tiername, beschreibung, alter,preis, foto);
        return inseratService.createInserat(inserat, inserentUsername);
    }

}
