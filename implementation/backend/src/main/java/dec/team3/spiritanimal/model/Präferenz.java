package dec.team3.spiritanimal.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Präferenz {

    private Kategorie[] kategorien;
    private int alterMin;
    private int alterMax;
    private int preisMin;
    private int preisMax;

    public Kategorie[] getKategorien() {
        return kategorien;
    }

    public void setKategorien(Kategorie[] kategorien) {
        this.kategorien = kategorien;
    }

    // TODO: addKategorie, removeKategorie

    public int getAlterMin() {
        return alterMin;
    }

    public void setAlterMin(int alterMin) {
        this.alterMin = alterMin;
    }

    public int getAlterMax() {
        return alterMax;
    }

    public void setAlterMax(int alterMax) {
        this.alterMax = alterMax;
    }

    public int getPreisMin() {
        return preisMin;
    }

    public void setPreisMin(int preisMin) {
        this.preisMin = preisMin;
    }

    public int getPreisMax() {
        return preisMax;
    }

    public void setPreisMax(int preisMax) {
        this.preisMax = preisMax;
    }
}
