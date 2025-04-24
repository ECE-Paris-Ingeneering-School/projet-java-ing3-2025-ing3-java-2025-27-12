import javax.swing.*;
import java.awt.*;
/**
 * Fenêtre de connexion principale.
 * Cette classe fournit une interface utilisateur simple pour se connecter avec un email et un mot de passe.
 * Redirige vers la fenêtre principale si la connexion réussit.
 *
 * @author Arthur et Mathis
 * @version 1.0
 */
public class LoginFrame extends JFrame {
    /**
     * Constructeur qui initialise la fenêtre de connexion.
     */
    public LoginFrame() {
        setTitle("Connexion");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());


        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints constraints = new GridBagConstraints();
            constraints.insets = new Insets(10, 10, 10, 10);
            constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel       titleLabel = new JLabel("Bienvenue");
        titleLabel. setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.     setHorizontalAlignment(SwingConstants.CENTER);



        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel passwordLabel = new JLabel("Mot de passe:");
        JPasswordField passwordField = new JPasswordField();


            JButton loginButton = new JButton("Connexion");
            loginButton.setBackground(new Color(76, 175, 80));
            loginButton.setForeground(Color.WHITE);

        constraints.gridx = 0;
        constraints.gridy = 0;

        constraints.gridwidth = 2;
        panel.add(titleLabel, constraints);

        constraints.gridwidth = 1;
        constraints.gridy++;
        panel.add(emailLabel, constraints);

        constraints.gridx = 1;
        panel.add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy++;
        panel.add(passwordLabel, constraints);



        constraints.gridx = 1;
        panel.add(passwordField, constraints);

        constraints.gridx = 0;
            constraints.gridy++;
        constraints.gridwidth = 2;
        panel.  add(loginButton, constraints);

        add(panel);
    }
    /**
     * Point d'entrée pour lancer la fenêtre de connexion.
     *
     * @param args les arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
