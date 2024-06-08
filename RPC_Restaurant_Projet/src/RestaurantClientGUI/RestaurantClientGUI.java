import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;

public class RestaurantClientGUI extends JFrame {
    private RestaurantService restaurantService;
    private Client client;
    private List<Plat> commandes;
    private JTextArea menuTextArea;
    private JTextArea commandesTextArea;
    private JTextField clientNameField;
    private JTextField platField;
    private JLabel factureLabel;

    public RestaurantClientGUI(String serverAddress) {
        try {
            restaurantService = (RestaurantService) Naming.lookup("rmi://" + serverAddress + "/RestaurantServic>
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTitle("Restaurant Client");
        setSize(800, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(210, 210, 210)); // Blanc cassé
        add(mainPanel);


        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(210, 210, 210)); // Blanc cassé
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        JLabel clientLabel = new JLabel("Nom du Client :");
        clientLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        topPanel.add(clientLabel);

        clientNameField = new JTextField(15);
        topPanel.add(clientNameField);

        JButton connectButton = new JButton("Se connecter");
        customizeButton(connectButton);
        topPanel.add(connectButton);


        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1));
        centerPanel.setBackground(new Color(210, 210, 210)); // Blanc cassé
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setBorder(BorderFactory.createTitledBorder("Menu de notre Restaurant"));
        centerPanel.add(menuPanel);

        menuTextArea = new JTextArea();
        menuTextArea.setEditable(false);
        menuTextArea.setBackground(new Color(220, 220, 220));
        menuTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane menuScrollPane = new JScrollPane(menuTextArea);
        menuPanel.add(menuScrollPane, BorderLayout.CENTER);

        JPanel commandesPanel = new JPanel();
        commandesPanel.setLayout(new BorderLayout());
        commandesPanel.setBorder(BorderFactory.createTitledBorder("liste des Commandes"));
        centerPanel.add(commandesPanel);

        commandesTextArea = new JTextArea();
        commandesTextArea.setEditable(false);
        commandesTextArea.setBackground(new Color(220, 220, 220));
        commandesTextArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane commandesScrollPane = new JScrollPane(commandesTextArea);
        commandesPanel.add(commandesScrollPane, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 1));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(17, 17, 25, 25));
        bottomPanel.setBackground(new Color(210, 210, 210)); // Blanc cassé
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        JPanel addPlatPanel = new JPanel();
        addPlatPanel.setBackground(new Color(210, 210, 210)); // Blanc cassé
        bottomPanel.add(addPlatPanel);

        JLabel platLabel = new JLabel("Nom du Plat :");
        platLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        addPlatPanel.add(platLabel);

        platField = new JTextField(20);
        addPlatPanel.add(platField);

        JButton addButton = new JButton("Ajouter Plat");
        customizeButton(addButton);
        addPlatPanel.add(addButton);

        JPanel facturePanel = new JPanel();
        facturePanel.setBackground(new Color(210, 210, 210)); // Blanc cassé
        bottomPanel.add(facturePanel);

        factureLabel = new JLabel("Facture : 0.0 $");
        factureLabel.setFont(new Font("Arial", Font.BOLD, 14));
        facturePanel.add(factureLabel);

        JButton calculerButton = new JButton("Calculer Facture");
        customizeButton(calculerButton);
        facturePanel.add(calculerButton);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientName = clientNameField.getText();
                client = new Client(clientName);
                commandes = new ArrayList<>();
                afficherMenu();
            }
        });

        calculerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculerFacture();
            }
        });
    }

    private void afficherMenu() {
        try {
            List<Plat> menu = restaurantService.consulterMenu();
            menuTextArea.setText("");
            for (Plat plat : menu) {
                menuTextArea.append(plat + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ajouterCommande() {
        try {
            String nomPlat = platField.getText();
            List<Plat> menu = restaurantService.consulterMenu();
            boolean found = false;
            for (Plat plat : menu) {
                if (plat.getNom().equalsIgnoreCase(nomPlat)) {
                    commandes.add(plat);
                    commandesTextArea.append(plat + "\n");
                    found = true;
                    break;
                }
            }
            if (!found) {
                JOptionPane.showMessageDialog(this, "Plat non trouvé dans le menu, merci de commander un autre >
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculerFacture() {
        try {
            restaurantService.passerCommande(client, commandes);
            double facture = restaurantService.calculerFacture(client);
            factureLabel.setText("Votre Facture est : " + facture + " $");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void customizeButton(JButton button) {
        button.setBackground(new Color(100, 100, 100)); // Gris foncé
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
    }

    public static void main(String[] args) {
        String serverAddress = args[0];
        SwingUtilities.invokeLater(() -> {
            RestaurantClientGUI clientGUI = new RestaurantClientGUI(serverAddress);
            clientGUI.setVisible(true);
        });
    }
}
