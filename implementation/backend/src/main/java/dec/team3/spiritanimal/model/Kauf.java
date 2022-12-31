package dec.team3.spiritanimal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class Kauf {

    @Id
    private String kaufID;
    private Inserat inserat;
    private User käufer;
    private Date kaufdatum;
    private String status;

    public String getKaufID() {
        return kaufID;
    }

    public Inserat getInserat() {
        return inserat;
    }

    public void setInserat(Inserat inserat) {
        this.inserat = inserat;
    }

    public User getKäufer() {
        return käufer;
    }

    public void setKäufer(User käufer) {
        this.käufer = käufer;
    }

    public Date getKaufdatum() {
        return kaufdatum;
    }

    public void setKaufdatum(Date kaufdatum) {
        this.kaufdatum = kaufdatum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
