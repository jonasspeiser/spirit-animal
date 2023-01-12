package dec.team3.spiritanimal.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Kauf {

    @Id
    private String kaufID;
    @NotEmpty
    private String käuferUsername;
    @NotEmpty
    private Inserat inserat;
    private Date kaufdatum;
    @NotEmpty
    private KaufStatus status;

    public Kauf(String käuferUsername, Inserat inserat, Date kaufdatum, KaufStatus status) {
        this.käuferUsername = käuferUsername;
        this.inserat = inserat;
        this.kaufdatum = kaufdatum;
        this.status = status;
    }

    public String getKaufID() {
        return kaufID;
    }
    public String getKäuferUsername() {
        return käuferUsername;
    }

    public Inserat getInserat() {
        return inserat;
    }

    public Date getKaufdatum() {
        return kaufdatum;
    }

    public KaufStatus getStatus() {
        return status;
    }

    public void setStatus(KaufStatus status) {
        this.status = status;
    }
}
