import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Produit implements Serializable {

    private static final long serialVersionUID = 1L;

    private int    id;
    private String nom;
    private String description;
    private double prix;
    private int    quantiteStock;
    private String categorie;

    // =========================================================
    //  CONSTRUCTEURS
    // =========================================================

    public Produit() {}

    public Produit(int id, String nom, String description,
                   double prix, int quantiteStock, String categorie) {
        if (prix < 0) throw new IllegalArgumentException("❌ Prix ne peut pas être négatif !");
        if (quantiteStock < 0) throw new IllegalArgumentException("❌ Stock ne peut pas être négatif !");
        this.id            = id;
        this.nom           = nom;
        this.description   = description;
        this.prix          = prix;
        this.quantiteStock = quantiteStock;
        this.categorie     = categorie;
    }

    // =========================================================
    //  MÉTHODES CORE
    // =========================================================

    /** [CORE] Ajouter / initialiser un produit */
    public void ajouter(String nom, String description,
                        double prix, int stock, String categorie) {
        if (prix < 0 || stock < 0) {
            System.out.println("❌ Prix ou stock invalide !");
            return;
        }
        this.nom           = nom;
        this.description   = description;
        this.prix          = prix;
        this.quantiteStock = stock;
        this.categorie     = categorie;
        System.out.println("✅ Produit ajouté : " + nom);
    }

    /** [CORE] Modifier un produit */
    public void modifier(String nouveauNom, String nouvelleDescription,
                         double nouveauPrix, String nouvelleCategorie) {
        if (nouveauPrix < 0) {
            System.out.println("❌ Prix invalide !");
            return;
        }
        this.nom         = nouveauNom;
        this.description = nouvelleDescription;
        this.prix        = nouveauPrix;
        this.categorie   = nouvelleCategorie;
        System.out.println("✅ Produit modifié : " + nom);
    }

    /** [CORE] Supprimer (marquer comme supprimé) */
    public void supprimer() {
        System.out.println("🗑️ Produit supprimé : " + nom);
        this.quantiteStock = 0;
    }

    /** [CORE] Consulter la liste (retourne liste vide — à remplir via BD) */
    public List<Produit> consulter() {
        return new ArrayList<>();
    }

    /** [CORE] Afficher les détails */
    public void afficherDetails() {
        System.out.println("======= PRODUIT =======");
        System.out.println("ID          : " + id);
        System.out.println("Nom         : " + nom);
        System.out.println("Description : " + description);
        System.out.println("Prix        : " + prix + " DH");
        System.out.println("Stock       : " + quantiteStock);
        System.out.println("Catégorie   : " + categorie);
        System.out.println("=======================");
    }

    // =========================================================
    //  MÉTHODES OPTIONNELLES
    // =========================================================

    /** [OPTIONNEL] Rechercher par mot-clé ou catégorie */
    public List<Produit> rechercherProduit(String motCle, String cat) {
        return new ArrayList<>();
    }

    /** [OPTIONNEL] Gérer catégorie */
    public void gererCategorie(String action, String nomCategorie) {
        if ("SET".equalsIgnoreCase(action)) {
            this.categorie = nomCategorie;
            System.out.println("✅ Catégorie définie : " + categorie);
        }
    }

    /** [OPTIONNEL] Mettre à jour le stock */
    public void mettreAJourStock(int quantite) {
        int newStock = this.quantiteStock + quantite;
        if (newStock < 0) {
            System.out.println("❌ Stock insuffisant !");
            return;
        }
        this.quantiteStock = newStock;
        notifierRuptureStock();
        System.out.println("📦 Stock mis à jour : " + quantiteStock);
    }

    /** [OPTIONNEL] Notifier rupture de stock */
    public void notifierRuptureStock() {
        if (quantiteStock <= 0) {
            System.out.println("⚠️ Rupture de stock pour : " + nom);
        }
    }

    /** Vérifier la disponibilité */
    public boolean estDisponible(int quantiteDemandee) {
        return quantiteStock >= quantiteDemandee;
    }

    // =========================================================
    //  Getters / Setters
    // =========================================================

    public int    getId()                         { return id; }
    public void   setId(int id)                   { this.id = id; }

    public String getNom()                        { return nom; }
    public void   setNom(String nom)              { this.nom = nom; }

    public String getDescription()                { return description; }
    public void   setDescription(String desc)     { this.description = desc; }

    public double getPrix()                       { return prix; }
    public void   setPrix(double prix)            { this.prix = prix; }

    public int    getQuantiteStock()              { return quantiteStock; }
    public void   setQuantiteStock(int stock)     { this.quantiteStock = stock; }

    public String getCategorie()                  { return categorie; }
    public void   setCategorie(String categorie)  { this.categorie = categorie; }
}
