package dec.team3.spiritanimal.controller;

import dec.team3.spiritanimal.model.Inserat;
import dec.team3.spiritanimal.services.InseratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

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

    @MutationMapping
    public Inserat createInserat(@Argument Inserat inserat) {
        String inserentUsername = inserat.getInserentUsername();
        return inseratService.createInserat(inserat, inserentUsername);
    }

}
