
mport javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;                               // Importer le package de javaSwing 
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;     // Importer les packages necessaire  pour la comminucation RMI
import java.util.ArrayList;  
import java.util.HashMap;
import java.util.List;   // importer les packages pour implementer les listes et les map fonction dans java
import java.util.Map;

public class RestaurantServer extends UnicastRemoteObject implements RestaurantService {
    private List<Plat> menu;
    private Map<String, List<Plat>> commandes; //cette fonction cree une map qui  associe des cles les noms des clients a 
    private JTextArea commandesTextArea;       // a des valeurs  list des plats
    private JLabel statusLabel;

    protected RestaurantServer() throws RemoteException {
        super();
        menu = new ArrayList<>();  // cree a new array list
        menu.add(new Plat("Pizza", 10.99));    
        menu.add(new Plat("Pâtes", 8.50));
        menu.add(new Plat("Salade", 6.99));
        menu.add(new Plat("BURGERS", 15.0));
        menu.add(new Plat("Sandwich", 12.5));
        menu.add(new Plat("Mini burger avec frites", 15.5));
        menu.add(new Plat("Glace au chocolat ou à la vanille", 12.0));
        menu.add(new Plat("Salade César", 10.5));
        menu.add(new Plat("Espresso", 3));
        menu.add(new Plat("Americano", 3.25));
        menu.add(new Plat("Café au lait", 3.5));
        menu.add(new Plat("Cappuccino", 4.5));
        menu.add(new Plat("Macchiato", 3.5));
        menu.add(new Plat("Café glacé", 4.25));
        menu.add(new Plat("Tiramisu", 7.00));
        menu.add(new Plat("Les crevettes", 30.00));
        commandes = new HashMap<>();

        createGUI(); // pour crerer notre interface graphique
    }

    private void createGUI() {
        JFrame frame = new JFrame("Restaurant Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);       // definir la taille de notre interface


        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));   //l'utilisation de d'autre fonctionnalite pour ajuster notre 
        panel.setBackground(Color.DARK_GRAY);               // interface graphique


        statusLabel = new JLabel("Status: Initializing...");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(statusLabel, BorderLayout.NORTH);


        commandesTextArea = new JTextArea();
        commandesTextArea.setBackground(Color.BLACK);
        commandesTextArea.setForeground(Color.GREEN);
        commandesTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        commandesTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(commandesTextArea);
        scrollPane.setBorder(new LineBorder(Color.GRAY, 1, true));
        panel.add(scrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setBorder(new LineBorder(Color.GRAY, 1, true));

        JLabel bottomLabel = new JLabel("Serveur de Restaurant - En attente de commandes", JLabel.CENTER);
        bottomLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        bottomLabel.setForeground(Color.DARK_GRAY);
        bottomPanel.add(bottomLabel, BorderLayout.CENTER);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void updateCommandesTextArea() {
        commandesTextArea.setText("");
        for (Map.Entry<String, List<Plat>> entry : commandes.entrySet()) { // pour iterer sur les entrees 
            commandesTextArea.append("Client: " + entry.getKey() + "\n"); //ajouter le nom du client  a notre commande textArea
            for (Plat plat : entry.getValue()) {
                commandesTextArea.append("\t" + plat + "\n");
            }
            commandesTextArea.append("\n");
        }
    }

    @Override
    public synchronized List<Plat> consulterMenu() throws RemoteException {
        return menu;
    }

    @Override
    public synchronized void passerCommande(Client client, List<Plat> commande) throws RemoteException {
        String nomClient = client.getNom();
        if (commandes.containsKey(nomClient)) {
            commandes.get(nomClient).addAll(commande);
        } else {
            commandes.put(nomClient, new ArrayList<>(commande));
        }
        updateCommandesTextArea();
        statusLabel.setText("Commande de " + client.getNom() + " est bien reçue : " + commande);
        System.out.println("Commande de " + client.getNom() + " est bien reçue : " + commande);
    }

    @Override
    public synchronized double calculerFacture(Client client) throws RemoteException {
        double totalFacture = 0.0;
        String nomClient = client.getNom();
        List<Plat> commande = commandes.get(nomClient);
        if (commande != null) {
            for (Plat plat : commande) {
                totalFacture += plat.getPrix();
            }
        } else {
            System.out.println("Aucune commande trouvée pour le client : " + client.getNom());
        }
        return totalFacture;
    }

    public static void main(String[] args) {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            RestaurantServer restaurantServer = new RestaurantServer();
            Naming.rebind("RestaurantService", restaurantServer);
            restaurantServer.statusLabel.setText("Status: Notre Serveur est prêt à recevoir des commandes.");
            System.out.println("Notre Serveur est prêt à recevoir des commandes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
