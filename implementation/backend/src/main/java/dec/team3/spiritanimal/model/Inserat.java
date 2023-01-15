package dec.team3.spiritanimal.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Inserat {

    @Id
    private String inseratID;
    @NotEmpty
    private String inserentUsername;
    @NotEmpty
    private Kategorie kategorie;
    @NotEmpty
    private InseratStatus status;
    private boolean premium;
    private String tiername;
    private String beschreibung;
    private String alter;
    private double preis;
    private String foto;

    public Inserat(String inseratID, String inserentUsername, Kategorie kategorie, InseratStatus status, boolean premium, String tiername, String beschreibung, String alter, double preis, String foto) {
        this.inseratID = inseratID;
        this.inserentUsername = inserentUsername;
        this.kategorie = kategorie;
        this.status = status;
        this.premium = premium;
        this.tiername = tiername;
        this.beschreibung = beschreibung;
        this.alter = alter;
        this.preis = preis;
        this.foto = foto;
    }

    public String getInseratID() {
        return inseratID;
    }

    public String getInserentUsername() {
        return inserentUsername;
    }

    public Kategorie getKategorie() {
        return kategorie;
    }

    public void setKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public InseratStatus getStatus() {
        return status;
    }

    public void setStatus(InseratStatus status) {
        this.status = status;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getTiername() {
        return tiername;
    }

    public void setTiername(String tiername) {
        this.tiername = tiername;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public String getAlter() {
        return alter;
    }

    public void setAlter(String alter) {
        this.alter = alter;
    }

    public double getPreis() {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
