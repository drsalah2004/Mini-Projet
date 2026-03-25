import java.util.ArrayList;
import java.util.List;

/**
 * Classe Utilisateur
 * Gère l'inscription, la connexion, la session et le profil des utilisateurs.
 */
public class Utilisateur {

    private String login;
    private String motDePasseHashe;
    private String nom;
    private String email;
    private String adresse;
    private boolean connecte;
    private List<Commande> historiqueCmd;

    public Utilisateur() {
        this.connecte = false;
        this.historiqueCmd = new ArrayList<>();
    }

    public Utilisateur(String login, String motDePasse, String nom, String email, String adresse) {
        this();
        inscription(login, motDePasse, nom, email, adresse);
    }

    // =========================================================
    //  MÉTHODES CORE
    // =========================================================

    /**
     * [CORE] Inscription
     */
    public void inscription(String login, String motDePasse, String nom,
                            String email, String adresse) {
        if (login == null || login.isEmpty()) {
            System.out.println("❌ Login invalide !");
            return;
        }
        if (motDePasse == null || motDePasse.length() < 4) {
            System.out.println("❌ Mot de passe trop court (min 4 caractères) !");
            return;
        }
        if (email == null || !email.contains("@")) {
            System.out.println("❌ Email invalide !");
            return;
        }
        this.login            = login;
        this.motDePasseHashe  = hasherMotDePasse(motDePasse);
        this.nom              = nom;
        this.email            = email;
        this.adresse          = adresse;
        System.out.println("✅ Inscription réussie pour : " + nom);
    }

    /**
     * [CORE] Connexion
     */
    public boolean connexion(String login, String motDePasse) {
        if (this.login == null) {
            System.out.println("❌ Utilisateur non inscrit !");
            return false;
        }
        if (this.login.equals(login) &&
                this.motDePasseHashe.equals(hasherMotDePasse(motDePasse))) {
            this.connecte = true;
            System.out.println("✅ Connexion réussie : " + nom);
            return true;
        }
        System.out.println("❌ Login ou mot de passe incorrect !");
        return false;
    }

    /**
     * [CORE] Déconnexion
     */
    public void deconnexion() {
        this.connecte = false;
        System.out.println("👋 Déconnexion de : " + nom);
    }

    /**
     * [CORE] Modification du profil
     */
    public void modification(String nouveauNom, String nouvelEmail,
                             String nouvelleAdresse) {
        if (nouveauNom != null && !nouveauNom.isEmpty())   this.nom     = nouveauNom;
        if (nouvelEmail != null && nouvelEmail.contains("@")) this.email = nouvelEmail;
        if (nouvelleAdresse != null)                        this.adresse = nouvelleAdresse;
        System.out.println("✅ Profil mis à jour !");
    }

    /**
     * [CORE] Suppression du compte
     */
    public void suppression() {
        this.login           = null;
        this.motDePasseHashe = null;
        this.nom             = null;
        this.email           = null;
        this.adresse         = null;
        this.connecte        = false;
        System.out.println("🗑️ Compte supprimé !");
    }

    // =========================================================
    //  MÉTHODES OPTIONNELLES
    // =========================================================

    /** [OPTIONNEL] Afficher profil */
    public void afficherProfil() {
        System.out.println("======= PROFIL =======");
        System.out.println("Login   : " + login);
        System.out.println("Nom     : " + nom);
        System.out.println("Email   : " + email);
        System.out.println("Adresse : " + adresse);
        System.out.println("Statut  : " + (connecte ? "Connecté" : "Déconnecté"));
        System.out.println("======================");
    }

    /** [OPTIONNEL] Historique des commandes */
    public List<Commande> historiqueCommandes() {
        return historiqueCmd;
    }

    public void ajouterCommande(Commande c) {
        historiqueCmd.add(c);
    }

    /** [OPTIONNEL] Gérer session */
    public boolean isConnecte() {
        return connecte;
    }

    /** [OPTIONNEL] Réinitialiser mot de passe */
    public void reinitialiserMotDePasse(String verification, String nouveauMotDePasse) {
        if (verification == null || !verification.equals(this.email)) {
            System.out.println("❌ Vérification échouée !");
            return;
        }
        if (nouveauMotDePasse == null || nouveauMotDePasse.length() < 4) {
            System.out.println("❌ Nouveau mot de passe trop court !");
            return;
        }
        this.motDePasseHashe = hasherMotDePasse(nouveauMotDePasse);
        System.out.println("✅ Mot de passe réinitialisé !");
    }

    // =========================================================
    //  MÉTHODE UTILITAIRE PRIVÉE
    // =========================================================

    private String hasherMotDePasse(String motDePasse) {
        // Simple hash SHA-256 simulé (en prod, utiliser BCrypt)
        return Integer.toHexString(motDePasse.hashCode());
    }

    // Getters / Setters
    public String getLogin()          { return login; }
    public String getNom()            { return nom; }
    public String getEmail()          { return email; }
    public String getAdresse()        { return adresse; }
    public String getMotDePasseHashe(){ return motDePasseHashe; }
    public void   setMotDePasseHashe(String h) { this.motDePasseHashe = h; }
}
