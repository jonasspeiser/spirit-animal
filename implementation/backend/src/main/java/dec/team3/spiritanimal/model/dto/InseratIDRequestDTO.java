package dec.team3.spiritanimal.model.dto;

import jakarta.validation.constraints.NotEmpty;

public class InseratIDRequestDTO {
    @NotEmpty
    String inseratID;

    public InseratIDRequestDTO() {
    }

    public InseratIDRequestDTO(String inseratID) {
        this.inseratID = inseratID;
    }

    public String getInseratID() {
        return inseratID;
    }

    public void setInseratID(String inseratID) {
        this.inseratID = inseratID;
    }

}
