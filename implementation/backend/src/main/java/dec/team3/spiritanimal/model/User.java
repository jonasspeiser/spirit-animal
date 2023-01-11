package dec.team3.spiritanimal.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {

    @Id
    private String username;
    @NotEmpty
    private String password;
    @Email
    private String email;
    private String addresse;
    @NotEmpty
    private String zahlungsdaten;

    // für gewerbliche User:
    private String firma;
    private String token;

    // für das Auswählen von Lieblingstieren
    private Präferenz präferenz;
    private Inserat[] geseheneInserate;
    private Inserat[] favorisierteInserate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getZahlungsdaten() {
        return zahlungsdaten;
    }

    public void setZahlungsdaten(String zahlungsdaten) {
        this.zahlungsdaten = zahlungsdaten;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Präferenz getPräferenz() {
        return präferenz;
    }

    public void setPräferenz(Präferenz präferenz) {
        this.präferenz = präferenz;
    }

    public Inserat[] getGeseheneInserate() {
        return geseheneInserate;
    }

    public void setGeseheneInserate(Inserat[] geseheneInserate) {
        this.geseheneInserate = geseheneInserate;
    }

    // TODO: addGesehenesInserat, removeGesehenesInserat

    public Inserat[] getFavorisierteInserate() {
        return favorisierteInserate;
    }

    public void setFavorisierteInserate(Inserat[] favorisierteInserate) {
        this.favorisierteInserate = favorisierteInserate;
    }

    // TODO: addFavorisiertesInserat, removeFavorisiertesInserat
}
