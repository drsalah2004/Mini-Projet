import java.io.Serializable;

public class ItemCommande implements Serializable {

    private static final long serialVersionUID = 1L;

    private Produit produit;
    private int     quantite;
    private double  prixUnitaire; // prix figé au moment de la commande

    // ================= CONSTRUCTEUR =================

    public ItemCommande(Produit produit, int quantite) {
        if (produit == null)
            throw new IllegalArgumentException("❌ Produit invalide !");
        if (quantite <= 0)
            throw new IllegalArgumentException("❌ Quantité doit être > 0 !");

        this.produit      = produit;
        this.quantite     = quantite;
        this.prixUnitaire = produit.getPrix(); // sauvegarde du prix au moment de l'achat
    }

    // ================= CORE =================

    /** Calculer le sous-total (prix figé × quantité) */
    public double calculerSousTotal() {
        return prixUnitaire * quantite;
    }

    // ================= OPTIONNEL =================

    /** Afficher la ligne de facture */
    public void afficherItem() {
        System.out.println("🧾 Produit           : " + produit.getNom());
        System.out.println("   Quantité          : " + quantite);
        System.out.println("   Prix unitaire (figé) : " + prixUnitaire + " DH");
        System.out.printf( "   Sous-total        : %.2f DH%n", calculerSousTotal());
        System.out.println("-----------------------------------");
    }

    // ================= GETTERS =================

    public Produit getProduit()      { return produit; }
    public int     getQuantite()     { return quantite; }
    public double  getPrixUnitaire() { return prixUnitaire; }
}
