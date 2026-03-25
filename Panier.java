import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Panier implements Serializable {

    private static final long serialVersionUID = 1L;

    private int               id;
    private Utilisateur       utilisateur;  //Utilise ItemPanier (pas Produit directement)
    private double            total;
    private String            coupon;

    private List<ItemPanier> items;
    // =========================================================
    //  CONSTRUCTEUR
    // =========================================================

    public Panier(Utilisateur utilisateur) {
        if (utilisateur == null) throw new IllegalArgumentException("❌ Utilisateur invalide !");
        this.utilisateur = utilisateur;
        this.items       = new ArrayList<>();
        this.total       = 0.0;
        this.coupon      = null;
    }

    // =========================================================
    //  MÉTHODES CORE
    // =========================================================

    /**
     * [CORE] Ajouter un produit au panier
     */
    public void ajouterProduit(Produit produit, int quantite) {
        if (produit == null || quantite <= 0) {
            System.out.println("❌ Produit ou quantité invalide !");
            return;
        }
        if (!produit.estDisponible(quantite)) {
            System.out.println("❌ Stock insuffisant pour : " + produit.getNom());
            return;
        }
        // Vérifier si le produit est déjà dans le panier
        for (ItemPanier item : items) {
            if (item.getProduit().getId() == produit.getId()) {
                item.mettreAJourQuantite(item.getQuantite() + quantite);
                calculerTotal();
                System.out.println("🔄 Quantité mise à jour pour : " + produit.getNom());
                return;
            }
        }
        items.add(new ItemPanier(produit, quantite));
        calculerTotal();
        System.out.println("✅ Ajouté au panier : " + produit.getNom() + " x" + quantite);
    }

    /**
     * [CORE] Supprimer un produit du panier
     */
    public void supprimerProduit(Produit produit) {
        items.removeIf(item -> item.getProduit().getId() == produit.getId());
        calculerTotal();
        System.out.println("🗑️ Produit retiré du panier : " + produit.getNom());
    }

    /**
     * [CORE] Calculer le total
     */
    public double calculerTotal() {
        total = 0.0;
        for (ItemPanier item : items) {
            total += item.calculerSousTotal();
        }
        if (coupon != null) {
            total = appliquerReductionCoupon(total, coupon);
        }
        return total;
    }

    /**
     * [CORE] Vider le panier
     */
    public void vider() {
        items.clear();
        total  = 0.0;
        coupon = null;
        System.out.println("🧹 Panier vidé !");
    }

    // =========================================================
    //  MÉTHODES OPTIONNELLES
    // =========================================================

    /**
     * [OPTIONNEL] Modifier la quantité d'un produit
     */
    public void modifierQuantite(Produit produit, int quantite) {
        for (ItemPanier item : items) {
            if (item.getProduit().getId() == produit.getId()) {
                if (quantite <= 0) {
                    supprimerProduit(produit);
                } else {
                    item.mettreAJourQuantite(quantite);
                    calculerTotal();
                }
                return;
            }
        }
        System.out.println("❌ Produit non trouvé dans le panier !");
    }

    /**
     * [OPTIONNEL] Appliquer un coupon
     */
    public boolean appliquerCoupon(String codeCoupon) {
        if (codeCoupon == null || codeCoupon.isEmpty()) {
            System.out.println("❌ Code coupon invalide !");
            return false;
        }
        // Coupons simulés
        if (codeCoupon.equalsIgnoreCase("PROMO10") ||
                codeCoupon.equalsIgnoreCase("BIENVENUE") ||
                codeCoupon.equalsIgnoreCase("SOLDES20")) {
            this.coupon = codeCoupon;
            calculerTotal();
            System.out.println("✅ Coupon appliqué : " + codeCoupon);
            return true;
        }
        System.out.println("❌ Coupon invalide : " + codeCoupon);
        return false;
    }

    private double appliquerReductionCoupon(double montant, String code) {
        return switch (code.toUpperCase()) {
            case "PROMO10"    -> montant * 0.90;   // -10%
            case "BIENVENUE"  -> montant * 0.85;   // -15%
            case "SOLDES20"   -> montant * 0.80;   // -20%
            default           -> montant;
        };
    }

    /**
     * [OPTIONNEL] Sauvegarder le panier (à connecter à la BD)
     */
    public void sauvegarderPanier() {
        System.out.println("💾 Panier sauvegardé pour : " + utilisateur.getNom());
    }

    /**
     * [OPTIONNEL] Afficher récapitulatif
     */
    public void afficherRecapitulatif() {
        System.out.println("===== Récapitulatif du panier =====");
        if (items.isEmpty()) {
            System.out.println("  (Panier vide)");
        } else {
            for (ItemPanier item : items) {
                item.afficherItem();
            }
        }
        if (coupon != null) System.out.println("🏷️ Coupon appliqué : " + coupon);
        System.out.println("TOTAL : " + String.format("%.2f", calculerTotal()) + " DH");
        System.out.println("===================================");
    }

    /** Nombre d'articles dans le panier */
    public int getNombreArticles() {
        return items.stream().mapToInt(ItemPanier::getQuantite).sum();
    }

    // =========================================================
    //  Getters / Setters
    // =========================================================

    public int              getId()           { return id; }
    public void             setId(int id)     { this.id = id; }

    public Utilisateur      getUtilisateur()  { return utilisateur; }

    public List<ItemPanier> getItems()        { return items; }

    public double           getTotal()        { return total; }

    public String           getCoupon()       { return coupon; }
}
