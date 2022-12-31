package dec.team3.spiritanimal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Kategorie {

    @Id
    private String kategorieID;
    private String tierartname;
    private String beschreibung;

    public String getKategorieID() {
        return kategorieID;
    }

    public String getTierartname() {
        return tierartname;
    }

    public void setTierartname(String tierartname) {
        this.tierartname = tierartname;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}
