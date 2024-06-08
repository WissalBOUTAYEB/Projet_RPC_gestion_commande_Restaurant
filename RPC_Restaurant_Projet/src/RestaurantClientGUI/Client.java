import java.io.Serializable;

public class Client implements Serializable {
    private String nom;

    public Client(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "Client{" + "nom='" + nom + '\'' + '}';
    }
}
