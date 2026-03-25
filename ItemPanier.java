import java.io.Serializable;

public class ItemPanier implements Serializable {

    private static final long serialVersionUID = 1L;

    private Produit produit;
    private int quantite;

    // ================= CONSTRUCTEUR =================
    public ItemPanier(Produit produit, int quantite) {
        if (produit == null) {
            throw new IllegalArgumentException("Produit invalide !");
        }
        if (quantite <= 0) {
            throw new IllegalArgumentException("Quantité doit être > 0 !");
        }
        this.produit = produit;
        this.quantite = quantite;
    }

    // ================= GETTERS / SETTERS =================
    public Produit getProduit() { return produit; }
    public int getQuantite() { return quantite; }

    public void setQuantite(int quantite) {
        if (quantite > 0) this.quantite = quantite;
    }

    // ================= CORE =================

    // 1. Calculer sous-total
    public double calculerSousTotal() {
        return produit.getPrix() * quantite;
    }

    // Alias utilisé par Commande.java
    public double calculerSousTotal1() {
        return calculerSousTotal();
    }

    // ================= OPTIONNEL =================

    // 2. Afficher ligne
    public void afficherItem() {
        System.out.println("🛒 Produit : " + produit.getNom());
        System.out.println("   Quantité : " + quantite);
        System.out.println("   Prix unitaire : " + produit.getPrix() + " DH");
        System.out.println("   Sous-total : " + calculerSousTotal() + " DH");
        System.out.println("-----------------------------------");
    }

    // 3. Mettre à jour quantité
    public void mettreAJourQuantite(int nouvelleQuantite) {
        if (nouvelleQuantite <= 0) {
            System.out.println("❌ Quantité invalide !");
            return;
        }
        this.quantite = nouvelleQuantite;
        System.out.println("🔄 Quantité mise à jour : " + quantite);
    }
}
