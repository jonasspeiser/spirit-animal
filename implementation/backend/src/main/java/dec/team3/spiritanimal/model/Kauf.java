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
    private User käufer;
    private Inserat inserat;
    private Date kaufdatum;
    private KaufStatus status;

    public Kauf(User käufer, Inserat inserat, Date kaufdatum, KaufStatus status) {
        this.käufer = käufer;
        this.inserat = inserat;
        this.kaufdatum = kaufdatum;
        this.status = status;
    }

    public String getKaufID() {
        return kaufID;
    }
    public User getKäufer() {
        return käufer;
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
