import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RestaurantService extends Remote {
    List<Plat> consulterMenu() throws RemoteException;
    void passerCommande(Client client, List<Plat> commande) throws RemoteException;
    double calculerFacture(Client client) throws RemoteException;
   
}


