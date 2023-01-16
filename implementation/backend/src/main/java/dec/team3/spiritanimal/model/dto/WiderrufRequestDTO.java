package dec.team3.spiritanimal.model.dto;

import jakarta.validation.constraints.NotEmpty;

public class WiderrufRequestDTO {
    @NotEmpty
    String kaufID;
    @NotEmpty
    boolean tierBeiKäufer;

    public WiderrufRequestDTO(String kaufID, boolean tierBeiKäufer) {
        this.kaufID = kaufID;
        this.tierBeiKäufer = tierBeiKäufer;
    }

    public WiderrufRequestDTO() {
    }

    public String getKaufID() {
        return kaufID;
    }

    public void setKaufID(String kaufID) {
        this.kaufID = kaufID;
    }

    public boolean isTierBeiKäufer() {
        return tierBeiKäufer;
    }

    public void setTierBeiKäufer(boolean tierBeiKäufer) {
        this.tierBeiKäufer = tierBeiKäufer;
    }
}
