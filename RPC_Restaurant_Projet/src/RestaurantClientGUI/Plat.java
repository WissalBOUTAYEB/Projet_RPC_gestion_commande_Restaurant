import java.io.Serializable;

public class Plat implements Serializable {
    private String nom;
    private double prix;

    public Plat(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    @Override
    public String toString() {
        return nom + " - " + prix + " $";
    }
}


