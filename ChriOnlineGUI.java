import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

/**
 * ╔══════════════════════════════════════════╗
 *  ChriOnline — Interface Graphique Swing
 *  Architecture : MVC léger + Panels Cards
 * ╚══════════════════════════════════════════╝
 */
public class ChriOnlineGUI extends JFrame {

    // ── Palette ─────────────────────────────────────────────
    static final Color BG_DARK    = new Color(10, 12, 20);
    static final Color BG_CARD    = new Color(18, 22, 36);
    static final Color BG_HOVER   = new Color(28, 34, 54);
    static final Color ACCENT     = new Color(99, 179, 237);
    static final Color ACCENT2    = new Color(72, 209, 150);
    static final Color TEXT_WHITE = new Color(230, 235, 245);
    static final Color TEXT_GREY  = new Color(130, 140, 165);
    static final Color DANGER     = new Color(220, 80, 80);
    static final Color GOLD       = new Color(255, 196, 57);

    // ── Navigation CardLayout ────────────────────────────────
    private CardLayout cardLayout;
    private JPanel     mainPanel;

    // ── Panels ──────────────────────────────────────────────
    private LoginPanel     loginPanel;
    private RegisterPanel  registerPanel;
    private HomePanel      homePanel;
    private ProductsPanel  productsPanel;
    private CartPanel      cartPanel;
    private CheckoutPanel  checkoutPanel;

    // ── Session state ────────────────────────────────────────
    private Utilisateur    currentUser  = null;
    private Panier         currentCart  = null;
    private List<Produit>  catalog      = buildCatalog();

    // ════════════════════════════════════════════════════════
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new ChriOnlineGUI().setVisible(true));
    }

    public ChriOnlineGUI() {
        super("ChriOnline  ·  U Khali Dima 9rib");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);

        cardLayout = new CardLayout();
        mainPanel  = new JPanel(cardLayout);
        mainPanel.setBackground(BG_DARK);

        loginPanel    = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        homePanel     = new HomePanel(this);
        productsPanel = new ProductsPanel(this);
        cartPanel     = new CartPanel(this);
        checkoutPanel = new CheckoutPanel(this);

        mainPanel.add(loginPanel,    "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(homePanel,     "HOME");
        mainPanel.add(productsPanel, "PRODUCTS");
        mainPanel.add(cartPanel,     "CART");
        mainPanel.add(checkoutPanel, "CHECKOUT");

        add(mainPanel);
        navigate("LOGIN");
    }

    // ── Navigation ───────────────────────────────────────────
    public void navigate(String panel) {
        cardLayout.show(mainPanel, panel);
        if ("PRODUCTS".equals(panel)) productsPanel.refresh();
        if ("CART".equals(panel))     cartPanel.refresh();
        if ("HOME".equals(panel))     homePanel.refresh();
    }

    // ── Session helpers ──────────────────────────────────────
    public void login(String login, String password) {
        // Simulate DB lookup — in real app use DatabaseManager
        currentUser = new Utilisateur(login, password, login, login + "@gmail.com", "Sidi Bennour, MA");
        currentCart = new Panier(currentUser);
        navigate("HOME");
    }

    public void logout() {
        currentUser = null;
        currentCart = null;
        navigate("LOGIN");
    }

    public Utilisateur getCurrentUser()   { return currentUser; }
    public Panier      getCurrentCart()   { return currentCart; }
    public List<Produit> getCatalog()     { return catalog; }

    // ── Static demo catalog ──────────────────────────────────
    private static List<Produit> buildCatalog() {
        List<Produit> list = new ArrayList<>();
        list.add(new Produit(1, "Laptop Pro 15",  "Intel i7, 16GB RAM, 512GB SSD",  8500, 10, "Informatique"));
        list.add(new Produit(2, "Smartphone X12",  "6.7\", 128GB, 5G",              3200, 25, "Mobile"));
        list.add(new Produit(3, "Casque Bluetooth","Son Hi-Fi, 30h batterie",         650, 40, "Audio"));
        list.add(new Produit(4, "Tablette Tab S",  "10.4\", 6GB RAM, WiFi + 4G",    2200, 15, "Tablette"));
        list.add(new Produit(5, "Montre Connect",  "GPS, cardiaque, waterproof",      980, 20, "Wearable"));
        list.add(new Produit(6, "Clavier Mécanique","RGB, switches Cherry MX",        450,  8, "Périphériques"));
        list.add(new Produit(7, "SSD 1TB NVMe",    "Lecture 3500MB/s",               750, 30, "Stockage"));
        list.add(new Produit(8, "Webcam 4K",        "Auto-focus, micro intégré",      380, 18, "Périphériques"));
        return list;
    }
}


// ════════════════════════════════════════════════════════════════
//  SHARED UI HELPERS
// ════════════════════════════════════════════════════════════════
class UI {
    static JLabel title(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Serif", Font.BOLD, 28));
        l.setForeground(ChriOnlineGUI.TEXT_WHITE);
        return l;
    }

    static JLabel subtitle(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 14));
        l.setForeground(ChriOnlineGUI.TEXT_GREY);
        return l;
    }

    static JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        l.setForeground(ChriOnlineGUI.TEXT_GREY);
        return l;
    }

    static JTextField field(String placeholder) {
        JTextField f = new JTextField(20) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !isFocusOwner()) {
                    g.setColor(ChriOnlineGUI.TEXT_GREY);
                    g.setFont(new Font("SansSerif", Font.ITALIC, 12));
                    g.drawString(placeholder, 10, getHeight() / 2 + 5);
                }
            }
        };
        f.setBackground(new Color(25, 30, 48));
        f.setForeground(ChriOnlineGUI.TEXT_WHITE);
        f.setCaretColor(ChriOnlineGUI.ACCENT);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 60, 90), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        f.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return f;
    }

    static JPasswordField passField(String placeholder) {
        JPasswordField f = new JPasswordField(20);
        f.setBackground(new Color(25, 30, 48));
        f.setForeground(ChriOnlineGUI.TEXT_WHITE);
        f.setCaretColor(ChriOnlineGUI.ACCENT);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 60, 90), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        return f;
    }

    static JButton primaryBtn(String text) {
        JButton b = new JButton(text) {
            private boolean hovered = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = hovered ? ChriOnlineGUI.ACCENT.brighter() : ChriOnlineGUI.ACCENT;
                g2.setPaint(new GradientPaint(0, 0, c1, getWidth(), getHeight(),
                        new Color(72, 150, 220)));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 13));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()  - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        b.setPreferredSize(new Dimension(160, 38));
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    static JButton dangerBtn(String text) {
        JButton b = primaryBtn(text);
        b.setName("danger");
        return b;
    }

    static JButton ghostBtn(String text) {
        JButton b = new JButton(text) {
            private boolean hovered = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hovered) {
                    g2.setColor(ChriOnlineGUI.BG_HOVER);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }
                g2.setColor(ChriOnlineGUI.ACCENT);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 10, 10);
                g2.setColor(ChriOnlineGUI.ACCENT);
                g2.setFont(new Font("SansSerif", Font.BOLD, 13));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()  - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        b.setPreferredSize(new Dimension(140, 36));
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    static JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(ChriOnlineGUI.BG_CARD);
        p.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(40, 50, 75), 1),
            BorderFactory.createEmptyBorder(20, 24, 20, 24)));
        return p;
    }

    static JPanel dark() {
        JPanel p = new JPanel();
        p.setBackground(ChriOnlineGUI.BG_DARK);
        return p;
    }

    static JSeparator sep() {
        JSeparator s = new JSeparator();
        s.setForeground(new Color(40, 50, 75));
        s.setBackground(ChriOnlineGUI.BG_DARK);
        return s;
    }
}


// ════════════════════════════════════════════════════════════════
//  TOP NAV BAR (shared across authenticated panels)
// ════════════════════════════════════════════════════════════════
class NavBar extends JPanel {
    private ChriOnlineGUI app;
    private JLabel cartBadge;

    NavBar(ChriOnlineGUI app) {
        this.app = app;
        setBackground(ChriOnlineGUI.BG_CARD);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 50, 75)),
            BorderFactory.createEmptyBorder(10, 24, 10, 24)));
        setLayout(new BorderLayout(20, 0));

        // Logo
        JLabel logo = new JLabel("⚡ ChriOnline Khalik 9rib");
        logo.setFont(new Font("Serif", Font.BOLD, 20));
        logo.setForeground(ChriOnlineGUI.ACCENT);
        add(logo, BorderLayout.WEST);

        // Nav links
        JPanel nav = UI.dark();
        nav.setBackground(ChriOnlineGUI.BG_CARD);
        nav.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 0));

        String[] pages = {"🏠 Accueil", "📦 Produits", "🛒 Panier"};
        String[] keys  = {"HOME",       "PRODUCTS",    "CART"};
        for (int i = 0; i < pages.length; i++) {
            final String key = keys[i];
            JButton btn = navLink(pages[i]);
            btn.addActionListener(e -> app.navigate(key));
            nav.add(btn);
        }
        add(nav, BorderLayout.CENTER);

        // Right actions
        JPanel right = UI.dark();
        right.setBackground(ChriOnlineGUI.BG_CARD);
        right.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 0));

        cartBadge = new JLabel("0");
        cartBadge.setFont(new Font("SansSerif", Font.BOLD, 10));
        cartBadge.setForeground(Color.WHITE);
        cartBadge.setOpaque(true);
        cartBadge.setBackground(ChriOnlineGUI.DANGER);
        cartBadge.setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        right.add(cartBadge);

        JButton logout = UI.ghostBtn("Déconnexion");
        logout.addActionListener(e -> app.logout());
        right.add(logout);
        add(right, BorderLayout.EAST);
    }

    void updateCartCount(int n) {
        cartBadge.setText(String.valueOf(n));
        cartBadge.setBackground(n > 0 ? ChriOnlineGUI.DANGER : ChriOnlineGUI.TEXT_GREY);
    }

    private JButton navLink(String text) {
        JButton b = new JButton(text) {
            private boolean hovered = false;
            { addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                public void mouseExited(MouseEvent e)  { hovered = false; repaint(); }
            }); }
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (hovered) {
                    g2.setColor(ChriOnlineGUI.BG_HOVER);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                }
                g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
                g2.setColor(hovered ? ChriOnlineGUI.TEXT_WHITE : ChriOnlineGUI.TEXT_GREY);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()  - fm.stringWidth(getText())) / 2,
                    (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        b.setPreferredSize(new Dimension(110, 34));
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}


// ════════════════════════════════════════════════════════════════
//  LOGIN PANEL
// ════════════════════════════════════════════════════════════════
class LoginPanel extends JPanel {
    private ChriOnlineGUI app;
    private JTextField     loginField;
    private JPasswordField passField;
    private JLabel         errorLabel;

    LoginPanel(ChriOnlineGUI app) {
        this.app = app;
        setBackground(ChriOnlineGUI.BG_DARK);
        setLayout(new GridBagLayout());
        add(buildForm());
    }

    private JPanel buildForm() {
        JPanel form = UI.card();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setPreferredSize(new Dimension(380, 460));

        // Header
        JLabel icon = new JLabel("⚡", SwingConstants.CENTER);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 48));
        icon.setAlignmentX(CENTER_ALIGNMENT);

        JLabel title = UI.title("ChriOnline-Khalik 9rib");
        title.setAlignmentX(CENTER_ALIGNMENT);
        title.setFont(new Font("Serif", Font.BOLD, 32));
        title.setForeground(ChriOnlineGUI.ACCENT);

        JLabel sub = UI.subtitle("Connectez-vous à votre compte");
        sub.setAlignmentX(CENTER_ALIGNMENT);

        form.add(Box.createVerticalStrut(16));
        form.add(icon);
        form.add(Box.createVerticalStrut(8));
        form.add(title);
        form.add(Box.createVerticalStrut(4));
        form.add(sub);
        form.add(Box.createVerticalStrut(24));
        form.add(UI.sep());
        form.add(Box.createVerticalStrut(20));

        // Fields
        loginField = UI.field("Votre login");
        loginField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        passField  = UI.passField("Mot de passe");
        passField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        form.add(UI.label("Login"));
        form.add(Box.createVerticalStrut(6));
        form.add(loginField);
        form.add(Box.createVerticalStrut(14));
        form.add(UI.label("Mot de passe"));
        form.add(Box.createVerticalStrut(6));
        form.add(passField);
        form.add(Box.createVerticalStrut(8));

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(ChriOnlineGUI.DANGER);
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        errorLabel.setAlignmentX(CENTER_ALIGNMENT);
        form.add(errorLabel);
        form.add(Box.createVerticalStrut(10));

        // Buttons
        JButton loginBtn = UI.primaryBtn("Se connecter");
        loginBtn.setAlignmentX(CENTER_ALIGNMENT);
        loginBtn.addActionListener(e -> doLogin());

        JButton regBtn = UI.ghostBtn("Créer un compte");
        regBtn.setAlignmentX(CENTER_ALIGNMENT);
        regBtn.addActionListener(e -> app.navigate("REGISTER"));

        form.add(loginBtn);
        form.add(Box.createVerticalStrut(10));
        form.add(regBtn);
        form.add(Box.createVerticalStrut(16));

        // Demo hint
        JLabel hint = UI.subtitle("Démo : n'importe quel login/mot de passe");
        hint.setAlignmentX(CENTER_ALIGNMENT);
        form.add(hint);

        // Enter key
        passField.addActionListener(e -> doLogin());
        return form;
    }

    private void doLogin() {
        String login = loginField.getText().trim();
        String pass  = new String(passField.getPassword());
        if (login.isEmpty() || pass.isEmpty()) {
            errorLabel.setText("⚠ Veuillez remplir tous les champs !");
            return;
        }
        app.login(login, pass);
    }
}


// ════════════════════════════════════════════════════════════════
//  REGISTER PANEL
// ════════════════════════════════════════════════════════════════
class RegisterPanel extends JPanel {
    private ChriOnlineGUI app;
    private JTextField loginF, nomF, emailF, adresseF;
    private JPasswordField passF;
    private JLabel errorLabel;

    RegisterPanel(ChriOnlineGUI app) {
        this.app = app;
        setBackground(ChriOnlineGUI.BG_DARK);
        setLayout(new GridBagLayout());
        add(buildForm());
    }

    private JPanel buildForm() {
        JPanel form = UI.card();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setPreferredSize(new Dimension(400, 560));

        JLabel title = UI.title("Inscription");
        title.setAlignmentX(CENTER_ALIGNMENT);
        JLabel sub = UI.subtitle("Créez votre compte ChriOnline");
        sub.setAlignmentX(CENTER_ALIGNMENT);

        form.add(Box.createVerticalStrut(20));
        form.add(title);
        form.add(Box.createVerticalStrut(4));
        form.add(sub);
        form.add(Box.createVerticalStrut(20));
        form.add(UI.sep());
        form.add(Box.createVerticalStrut(16));

        loginF   = UI.field("ex: sek200");
        nomF     = UI.field("Nom complet");
        emailF   = UI.field("email@gmail.com");
        adresseF = UI.field("Ville, Pays");
        passF    = UI.passField("Min. 4 caractères");

        String[][] fields = {
            {"Login *", null}, {"Nom *", null}, {"Email *", null},
            {"Adresse", null}, {"Mot de passe *", null}
        };
        JComponent[] inputs = { loginF, nomF, emailF, adresseF, passF };

        for (int i = 0; i < inputs.length; i++) {
            form.add(UI.label(fields[i][0]));
            form.add(Box.createVerticalStrut(5));
            inputs[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
            form.add(inputs[i]);
            form.add(Box.createVerticalStrut(10));
        }

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(ChriOnlineGUI.DANGER);
        errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        errorLabel.setAlignmentX(CENTER_ALIGNMENT);
        form.add(errorLabel);

        JButton regBtn = UI.primaryBtn("S'inscrire");
        regBtn.setAlignmentX(CENTER_ALIGNMENT);
        regBtn.addActionListener(e -> doRegister());

        JButton back = UI.ghostBtn("← Connexion");
        back.setAlignmentX(CENTER_ALIGNMENT);
        back.addActionListener(e -> app.navigate("LOGIN"));

        form.add(Box.createVerticalStrut(8));
        form.add(regBtn);
        form.add(Box.createVerticalStrut(8));
        form.add(back);
        form.add(Box.createVerticalStrut(16));
        return form;
    }

    private void doRegister() {
        String login  = loginF.getText().trim();
        String nom    = nomF.getText().trim();
        String email  = emailF.getText().trim();
        String adr    = adresseF.getText().trim();
        String pass   = new String(passF.getPassword());

        if (login.isEmpty() || nom.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            errorLabel.setText("⚠ Champs obligatoires manquants !");
            return;
        }
        if (!email.contains("@")) {
            errorLabel.setText("⚠ Email invalide !");
            return;
        }
        if (pass.length() < 4) {
            errorLabel.setText("⚠ Mot de passe trop court (min 4) !");
            return;
        }
        JOptionPane.showMessageDialog(this,
            "✅ Compte créé pour " + nom + " !\nVous pouvez vous connecter.",
            "Inscription réussie", JOptionPane.INFORMATION_MESSAGE);
        app.navigate("LOGIN");
    }
}


// ════════════════════════════════════════════════════════════════
//  HOME PANEL  (dashboard)
// ════════════════════════════════════════════════════════════════
class HomePanel extends JPanel {
    private ChriOnlineGUI app;
    private JLabel welcomeLabel;
    private JLabel cartCountLabel;
    private JLabel totalLabel;
    private NavBar nav;

    HomePanel(ChriOnlineGUI app) {
        this.app = app;
        setBackground(ChriOnlineGUI.BG_DARK);
        setLayout(new BorderLayout());

        nav = new NavBar(app);
        add(nav, BorderLayout.NORTH);

        JPanel content = UI.dark();
        content.setLayout(new BorderLayout(0, 20));
        content.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Hero
        JPanel hero = UI.card();
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBorder(BorderFactory.createEmptyBorder(32, 40, 32, 40));

        welcomeLabel = new JLabel("Bonjour u Marhba bik fi hanoutna");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 36));
        welcomeLabel.setForeground(ChriOnlineGUI.TEXT_WHITE);
        welcomeLabel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel tagline = UI.subtitle("Découvrez nos produits et commandez en toute simplicité.");
        tagline.setAlignmentX(LEFT_ALIGNMENT);

        JButton shopBtn = UI.primaryBtn("🛍  Voir les produits");
        shopBtn.setAlignmentX(LEFT_ALIGNMENT);
        shopBtn.setPreferredSize(new Dimension(200, 42));
        shopBtn.addActionListener(e -> app.navigate("PRODUCTS"));

        hero.add(welcomeLabel);
        hero.add(Box.createVerticalStrut(8));
        hero.add(tagline);
        hero.add(Box.createVerticalStrut(24));
        hero.add(shopBtn);

        content.add(hero, BorderLayout.NORTH);

        // Stats row
        JPanel stats = UI.dark();
        stats.setLayout(new GridLayout(1, 3, 16, 0));

        cartCountLabel = new JLabel("0");
        totalLabel     = new JLabel("0.00 DH");

        stats.add(statCard("🛒 Articles panier", cartCountLabel, ChriOnlineGUI.ACCENT));
        stats.add(statCard("💰 Total panier",    totalLabel,     ChriOnlineGUI.ACCENT2));
        stats.add(statCard("📦 Catalogue",
            styledLabel(String.valueOf(app.getCatalog().size())), ChriOnlineGUI.GOLD));

        content.add(stats, BorderLayout.CENTER);

        // Quick actions
        JPanel actions = UI.dark();
        actions.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
        JLabel actTitle = UI.subtitle("Actions rapides : ");
        actions.add(actTitle);

        JButton cartBtn = UI.ghostBtn("🛒 Mon panier");
        cartBtn.addActionListener(e -> app.navigate("CART"));
        actions.add(cartBtn);

        JButton payBtn = UI.primaryBtn("💳 Payer");
        payBtn.addActionListener(e -> app.navigate("CHECKOUT"));
        actions.add(payBtn);

        content.add(actions, BorderLayout.SOUTH);

        add(content, BorderLayout.CENTER);
    }

    void refresh() {
        Utilisateur u = app.getCurrentUser();
        if (u != null) welcomeLabel.setText("Bonjour u marhba bik, " + u.getNom() + " 👋");
        Panier c = app.getCurrentCart();
        if (c != null) {
            cartCountLabel.setText(String.valueOf(c.getNombreArticles()));
            totalLabel.setText(String.format("%.2f DH", c.calculerTotal()));
            nav.updateCartCount(c.getNombreArticles());
        }
    }

    private JLabel styledLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(ChriOnlineGUI.TEXT_WHITE);
        l.setFont(new Font("SansSerif", Font.BOLD, 28));
        return l;
    }

    private JPanel statCard(String title, JLabel valLabel, Color accent) {
        JPanel p = UI.card();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        valLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        valLabel.setForeground(accent);
        valLabel.setAlignmentX(LEFT_ALIGNMENT);
        JLabel t = UI.label(title);
        t.setAlignmentX(LEFT_ALIGNMENT);
        p.add(valLabel);
        p.add(Box.createVerticalStrut(6));
        p.add(t);
        return p;
    }
}


// ════════════════════════════════════════════════════════════════
//  PRODUCTS PANEL
// ════════════════════════════════════════════════════════════════
class ProductsPanel extends JPanel {
    private ChriOnlineGUI app;
    private JPanel        grid;
    private JTextField    searchField;
    private JComboBox<String> catFilter;
    private NavBar        nav;

    ProductsPanel(ChriOnlineGUI app) {
        this.app = app;
        setBackground(ChriOnlineGUI.BG_DARK);
        setLayout(new BorderLayout());

        nav = new NavBar(app);
        add(nav, BorderLayout.NORTH);

        JPanel content = UI.dark();
        content.setLayout(new BorderLayout(0, 16));
        content.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        // Toolbar
        JPanel toolbar = UI.dark();
        toolbar.setLayout(new BorderLayout(16, 0));

        JLabel title = UI.title("📦 Catalogue Produits");
        toolbar.add(title, BorderLayout.WEST);

        JPanel filters = UI.dark();
        filters.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        searchField = UI.field("Rechercher...");
        searchField.setPreferredSize(new Dimension(200, 36));
        searchField.addActionListener(e -> refresh());

        catFilter = new JComboBox<>(new String[]{"Toutes catégories","Informatique","Mobile","Audio","Tablette","Wearable","Périphériques","Stockage"});
        catFilter.setBackground(new Color(25, 30, 48));
        catFilter.setForeground(ChriOnlineGUI.TEXT_WHITE);
        catFilter.setFont(new Font("SansSerif", Font.PLAIN, 12));
        catFilter.addActionListener(e -> refresh());

        JButton searchBtn = UI.primaryBtn("Filtrer");
        searchBtn.addActionListener(e -> refresh());

        filters.add(searchField);
        filters.add(catFilter);
        filters.add(searchBtn);
        toolbar.add(filters, BorderLayout.EAST);

        content.add(toolbar, BorderLayout.NORTH);

        // Grid (wrapped in scroll)
        grid = new JPanel(new GridLayout(0, 4, 16, 16));
        grid.setBackground(ChriOnlineGUI.BG_DARK);

        JScrollPane scroll = new JScrollPane(grid);
        scroll.setBorder(null);
        scroll.setBackground(ChriOnlineGUI.BG_DARK);
        scroll.getViewport().setBackground(ChriOnlineGUI.BG_DARK);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        content.add(scroll, BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);
    }

    void refresh() {
        String kw  = searchField.getText().trim().toLowerCase();
        String cat = (String) catFilter.getSelectedItem();

        List<Produit> filtered = new ArrayList<>();
        for (Produit p : app.getCatalog()) {
            boolean matchKw  = kw.isEmpty() || p.getNom().toLowerCase().contains(kw)
                                             || p.getDescription().toLowerCase().contains(kw);
            boolean matchCat = "Toutes catégories".equals(cat) || p.getCategorie().equals(cat);
            if (matchKw && matchCat) filtered.add(p);
        }

        grid.removeAll();
        grid.setLayout(new GridLayout(0, Math.max(1, Math.min(4, filtered.size())), 16, 16));

        for (Produit p : filtered) grid.add(productCard(p));

        if (filtered.isEmpty()) {
            JLabel empty = UI.subtitle("Aucun produit trouvé.");
            empty.setHorizontalAlignment(SwingConstants.CENTER);
            grid.add(empty);
        }

        grid.revalidate();
        grid.repaint();

        Panier c = app.getCurrentCart();
        if (c != null) nav.updateCartCount(c.getNombreArticles());
    }

    private JPanel productCard(Produit p) {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ChriOnlineGUI.BG_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                g2.setColor(new Color(40, 50, 75));
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 14, 14);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // Emoji icon based on category
        String emoji = switch (p.getCategorie()) {
            case "Informatique" -> "💻";
            case "Mobile"       -> "📱";
            case "Audio"        -> "🎧";
            case "Tablette"     -> "📟";
            case "Wearable"     -> "⌚";
            case "Stockage"     -> "💾";
            default             -> "🖱";
        };

        JLabel icon = new JLabel(emoji, SwingConstants.CENTER);
        icon.setFont(new Font("SansSerif", Font.PLAIN, 36));
        icon.setAlignmentX(CENTER_ALIGNMENT);

        JLabel nom = new JLabel(p.getNom(), SwingConstants.CENTER);
        nom.setFont(new Font("SansSerif", Font.BOLD, 13));
        nom.setForeground(ChriOnlineGUI.TEXT_WHITE);
        nom.setAlignmentX(CENTER_ALIGNMENT);

        JLabel desc = new JLabel("<html><center>" + p.getDescription() + "</center></html>", SwingConstants.CENTER);
        desc.setFont(new Font("SansSerif", Font.PLAIN, 11));
        desc.setForeground(ChriOnlineGUI.TEXT_GREY);
        desc.setAlignmentX(CENTER_ALIGNMENT);

        JLabel prix = new JLabel(String.format("%.0f DH", p.getPrix()), SwingConstants.CENTER);
        prix.setFont(new Font("SansSerif", Font.BOLD, 16));
        prix.setForeground(ChriOnlineGUI.ACCENT2);
        prix.setAlignmentX(CENTER_ALIGNMENT);

        JLabel stock = new JLabel("Stock: " + p.getQuantiteStock(), SwingConstants.CENTER);
        stock.setFont(new Font("SansSerif", Font.PLAIN, 11));
        stock.setForeground(p.getQuantiteStock() > 5 ? ChriOnlineGUI.ACCENT2 : ChriOnlineGUI.DANGER);
        stock.setAlignmentX(CENTER_ALIGNMENT);

        JPanel catTag = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        catTag.setOpaque(false);
        JLabel catLabel = new JLabel(p.getCategorie());
        catLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        catLabel.setForeground(ChriOnlineGUI.ACCENT);
        catLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ChriOnlineGUI.ACCENT, 1),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)));
        catTag.add(catLabel);

        JButton addBtn = UI.primaryBtn("+ Panier");
        addBtn.setAlignmentX(CENTER_ALIGNMENT);
        addBtn.addActionListener(e -> {
            String qteStr = JOptionPane.showInputDialog(this,
                "Quantité pour \"" + p.getNom() + "\" :", "1");
            if (qteStr == null) return;
            try {
                int qte = Integer.parseInt(qteStr.trim());
                if (qte <= 0) throw new NumberFormatException();
                app.getCurrentCart().ajouterProduit(p, qte);
                nav.updateCartCount(app.getCurrentCart().getNombreArticles());
                JOptionPane.showMessageDialog(this,
                    "✅ " + p.getNom() + " × " + qte + " ajouté au panier !",
                    "Panier", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "⚠ Quantité invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        card.add(icon);
        card.add(Box.createVerticalStrut(8));
        card.add(nom);
        card.add(Box.createVerticalStrut(4));
        card.add(desc);
        card.add(Box.createVerticalStrut(8));
        card.add(catTag);
        card.add(Box.createVerticalStrut(8));
        card.add(prix);
        card.add(Box.createVerticalStrut(4));
        card.add(stock);
        card.add(Box.createVerticalStrut(12));
        card.add(addBtn);
        return card;
    }
}


// ════════════════════════════════════════════════════════════════
//  CART PANEL
// ════════════════════════════════════════════════════════════════
class CartPanel extends JPanel {
    private ChriOnlineGUI app;
    private JPanel        itemsList;
    private JLabel        totalLabel;
    private NavBar        nav;

    CartPanel(ChriOnlineGUI app) {
        this.app = app;
        setBackground(ChriOnlineGUI.BG_DARK);
        setLayout(new BorderLayout());

        nav = new NavBar(app);
        add(nav, BorderLayout.NORTH);

        JPanel content = UI.dark();
        content.setLayout(new BorderLayout(0, 16));
        content.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JLabel title = UI.title("🛒 Mon Panier");
        content.add(title, BorderLayout.NORTH);

        // Items list
        itemsList = UI.dark();
        itemsList.setLayout(new BoxLayout(itemsList, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(itemsList);
        scroll.setBorder(null);
        scroll.setBackground(ChriOnlineGUI.BG_DARK);
        scroll.getViewport().setBackground(ChriOnlineGUI.BG_DARK);
        content.add(scroll, BorderLayout.CENTER);

        // Bottom bar
        JPanel bottomBar = UI.card();
        bottomBar.setLayout(new BorderLayout(16, 0));
        bottomBar.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));

        JPanel totPanel = UI.card();
        totPanel.setLayout(new BoxLayout(totPanel, BoxLayout.Y_AXIS));
        totPanel.setBorder(null);
        totPanel.setBackground(ChriOnlineGUI.BG_CARD);
        JLabel totTxt = UI.label("Total estimé :");
        totTxt.setAlignmentX(LEFT_ALIGNMENT);
        totalLabel = new JLabel("0.00 DH");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        totalLabel.setForeground(ChriOnlineGUI.ACCENT2);
        totalLabel.setAlignmentX(LEFT_ALIGNMENT);
        totPanel.add(totTxt);
        totPanel.add(totalLabel);
        bottomBar.add(totPanel, BorderLayout.WEST);

        JPanel btns = UI.dark();
        btns.setBackground(ChriOnlineGUI.BG_CARD);
        btns.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton clearBtn = UI.ghostBtn("🗑 Vider");
        clearBtn.addActionListener(e -> {
            app.getCurrentCart().vider();
            refresh();
        });

        JButton continueBtn = UI.ghostBtn("← Produits");
        continueBtn.addActionListener(e -> app.navigate("PRODUCTS"));

        JButton checkoutBtn = UI.primaryBtn("💳 Commander");
        checkoutBtn.addActionListener(e -> {
            if (app.getCurrentCart().getItems().isEmpty()) {
                JOptionPane.showMessageDialog(this, "⚠ Votre panier est vide !", "Panier vide", JOptionPane.WARNING_MESSAGE);
                return;
            }
            app.navigate("CHECKOUT");
        });

        btns.add(clearBtn);
        btns.add(continueBtn);
        btns.add(checkoutBtn);
        bottomBar.add(btns, BorderLayout.EAST);

        content.add(bottomBar, BorderLayout.SOUTH);
        add(content, BorderLayout.CENTER);
    }

    void refresh() {
        itemsList.removeAll();
        Panier cart = app.getCurrentCart();
        if (cart == null) return;

        if (cart.getItems().isEmpty()) {
            JLabel empty = UI.subtitle("Votre panier est vide. Ajoutez des produits !");
            empty.setAlignmentX(CENTER_ALIGNMENT);
            itemsList.add(Box.createVerticalStrut(60));
            itemsList.add(empty);
        } else {
            for (ItemPanier item : new ArrayList<>(cart.getItems())) {
                itemsList.add(cartItemRow(item));
                itemsList.add(Box.createVerticalStrut(10));
            }
        }

        totalLabel.setText(String.format("%.2f DH", cart.calculerTotal()));
        nav.updateCartCount(cart.getNombreArticles());
        itemsList.revalidate();
        itemsList.repaint();
    }

    private JPanel cartItemRow(ItemPanier item) {
        JPanel row = UI.card();
        row.setLayout(new BorderLayout(16, 0));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JLabel name = new JLabel(item.getProduit().getNom());
        name.setFont(new Font("SansSerif", Font.BOLD, 14));
        name.setForeground(ChriOnlineGUI.TEXT_WHITE);

        JLabel price = UI.label(item.getProduit().getPrix() + " DH × " + item.getQuantite());
        JLabel sub   = new JLabel(String.format("%.2f DH", item.calculerSousTotal()));
        sub.setFont(new Font("SansSerif", Font.BOLD, 15));
        sub.setForeground(ChriOnlineGUI.ACCENT2);

        JPanel left = UI.card();
        left.setBackground(ChriOnlineGUI.BG_CARD);
        left.setBorder(null);
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.add(name);
        left.add(Box.createVerticalStrut(4));
        left.add(price);
        row.add(left, BorderLayout.WEST);
        row.add(sub, BorderLayout.CENTER);

        JPanel actions = UI.dark();
        actions.setBackground(ChriOnlineGUI.BG_CARD);
        actions.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 0));

        JButton modBtn = UI.ghostBtn("Quantité");
        modBtn.setPreferredSize(new Dimension(90, 30));
        modBtn.addActionListener(e -> {
            String qStr = JOptionPane.showInputDialog(this, "Nouvelle quantité :", item.getQuantite());
            if (qStr == null) return;
            try {
                int q = Integer.parseInt(qStr.trim());
                app.getCurrentCart().modifierQuantite(item.getProduit(), q);
                refresh();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Quantité invalide !", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton delBtn = new JButton("✕") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ChriOnlineGUI.DANGER);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 12));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                    (getWidth()-fm.stringWidth(getText()))/2,
                    (getHeight()+fm.getAscent()-fm.getDescent())/2);
                g2.dispose();
            }
        };
        delBtn.setPreferredSize(new Dimension(32, 30));
        delBtn.setBorderPainted(false);
        delBtn.setContentAreaFilled(false);
        delBtn.setFocusPainted(false);
        delBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        delBtn.addActionListener(e -> {
            app.getCurrentCart().supprimerProduit(item.getProduit());
            refresh();
        });

        actions.add(modBtn);
        actions.add(delBtn);
        row.add(actions, BorderLayout.EAST);
        return row;
    }
}


// ════════════════════════════════════════════════════════════════
//  CHECKOUT PANEL
// ════════════════════════════════════════════════════════════════
class CheckoutPanel extends JPanel {
    private ChriOnlineGUI app;
    private JComboBox<String> modePayment;
    private JTextField couponField;
    private JLabel totalLabel;
    private JLabel statusLabel;
    private NavBar nav;

    CheckoutPanel(ChriOnlineGUI app) {
        this.app = app;
        setBackground(ChriOnlineGUI.BG_DARK);
        setLayout(new BorderLayout());

        nav = new NavBar(app);
        add(nav, BorderLayout.NORTH);

        JPanel content = UI.dark();
        content.setLayout(new GridBagLayout());
        content.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));

        JPanel form = UI.card();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setPreferredSize(new Dimension(500, 520));

        // Title
        JLabel title = UI.title("💳 Paiement");
        title.setAlignmentX(LEFT_ALIGNMENT);
        form.add(Box.createVerticalStrut(8));
        form.add(title);
        form.add(Box.createVerticalStrut(4));
        form.add(UI.subtitle("Vérifiez et confirmez votre commande"));
        form.add(Box.createVerticalStrut(20));
        form.add(UI.sep());
        form.add(Box.createVerticalStrut(16));

        // Total
        totalLabel = new JLabel("0.00 DH");
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        totalLabel.setForeground(ChriOnlineGUI.ACCENT2);
        totalLabel.setAlignmentX(LEFT_ALIGNMENT);
        JLabel totTxt = UI.label("Montant total :");
        totTxt.setAlignmentX(LEFT_ALIGNMENT);
        form.add(totTxt);
        form.add(Box.createVerticalStrut(4));
        form.add(totalLabel);
        form.add(Box.createVerticalStrut(20));

        // Mode paiement
        form.add(UI.label("Mode de paiement :"));
        form.add(Box.createVerticalStrut(6));
        modePayment = new JComboBox<>(new String[]{"💳 Carte bancaire", "🅿 PayPal", "🏦 Virement bancaire", "🎭 Paiement fictif"});
        modePayment.setBackground(new Color(25, 30, 48));
        modePayment.setForeground(ChriOnlineGUI.TEXT_WHITE);
        modePayment.setFont(new Font("SansSerif", Font.PLAIN, 13));
        modePayment.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        form.add(modePayment);
        form.add(Box.createVerticalStrut(16));

        // Coupon
        form.add(UI.label("Code coupon (optionnel) :"));
        form.add(Box.createVerticalStrut(6));
        couponField = UI.field("PROMO10 / BIENVENUE / SOLDES20");
        couponField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));

        JPanel couponRow = UI.dark();
        couponRow.setLayout(new BoxLayout(couponRow, BoxLayout.X_AXIS));
        couponRow.add(couponField);
        couponRow.add(Box.createHorizontalStrut(8));
        JButton applyBtn = UI.ghostBtn("Appliquer");
        applyBtn.setPreferredSize(new Dimension(100, 42));
        applyBtn.addActionListener(e -> applyCoupon());
        couponRow.add(applyBtn);
        couponRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        form.add(couponRow);
        form.add(Box.createVerticalStrut(20));
        form.add(UI.sep());
        form.add(Box.createVerticalStrut(16));

        // Status
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        statusLabel.setAlignmentX(LEFT_ALIGNMENT);
        form.add(statusLabel);
        form.add(Box.createVerticalStrut(10));

        // Buttons
        JPanel btnRow = UI.dark();
        btnRow.setBackground(ChriOnlineGUI.BG_CARD);
        btnRow.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

        JButton backBtn = UI.ghostBtn("← Panier");
        backBtn.addActionListener(e -> app.navigate("CART"));

        JButton payBtn = UI.primaryBtn("✅ Confirmer");
        payBtn.setPreferredSize(new Dimension(160, 42));
        payBtn.addActionListener(e -> processPayment());

        btnRow.add(backBtn);
        btnRow.add(payBtn);
        form.add(btnRow);
        form.add(Box.createVerticalStrut(16));

        content.add(form);
        add(content, BorderLayout.CENTER);
    }

    // called when navigating here
    private void refreshTotal() {
        Panier cart = app.getCurrentCart();
        if (cart != null) totalLabel.setText(String.format("%.2f DH", cart.calculerTotal()));
    }

    @Override public void addNotify() {
        super.addNotify();
        refreshTotal();
    }

    private void applyCoupon() {
        Panier cart = app.getCurrentCart();
        if (cart == null) return;
        String code = couponField.getText().trim();
        boolean ok = cart.appliquerCoupon(code);
        if (ok) {
            statusLabel.setForeground(ChriOnlineGUI.ACCENT2);
            statusLabel.setText("✅ Coupon \"" + code + "\" appliqué !");
            totalLabel.setText(String.format("%.2f DH", cart.calculerTotal()));
        } else {
            statusLabel.setForeground(ChriOnlineGUI.DANGER);
            statusLabel.setText("❌ Coupon invalide !");
        }
    }

    private void processPayment() {
        Panier cart = app.getCurrentCart();
        if (cart == null || cart.getItems().isEmpty()) {
            statusLabel.setForeground(ChriOnlineGUI.DANGER);
            statusLabel.setText("❌ Panier vide !");
            return;
        }

        double total = cart.calculerTotal();
        String modeStr = (String) modePayment.getSelectedItem();
        String mode;
        if      (modeStr.contains("Carte"))    mode = "CARTE";
        else if (modeStr.contains("PayPal"))   mode = "PAYPAL";
        else if (modeStr.contains("Virement")) mode = "VIREMENT";
        else                                   mode = "FICTIF";

        // Create order
        Commande cmd = new Commande(app.getCurrentUser(), cart);
        Paiement pmt = new Paiement(cmd.getId(), total);
        pmt.choisirModePaiement(mode);
        pmt.initierPaiement();
        boolean success = pmt.validerPaiement();

        if (success) {
            cmd.validerCommande();
            app.getCurrentUser().ajouterCommande(cmd);

            String msg = String.format(
                "✅ Commande confirmée !\n\nN° : %s\nTotal : %.2f DH\nMode : %s\n\n" +
                "Confirmation envoyée à : %s",
                cmd.getId(), total, mode, app.getCurrentUser().getEmail()
            );
            JOptionPane.showMessageDialog(this, msg, "Paiement accepté", JOptionPane.INFORMATION_MESSAGE);

            // Reset cart
            app.getCurrentCart().vider();
            app.navigate("HOME");
        } else {
            statusLabel.setForeground(ChriOnlineGUI.DANGER);
            statusLabel.setText("❌ Paiement refusé. Réessayez !");
        }
    }
}