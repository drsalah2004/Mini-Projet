import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    private String    idCommande;
    private double    montant;
    private String    statut;        // EN_ATTENTE, VALIDE, REFUSE, REMBOURSE
    private String    modePaiement;  // CARTE, PAYPAL, VIREMENT, FICTIF
    private ArrayList<String> historique;

    // ================= CONSTRUCTEURS =================

    public Paiement(String idCommande, double montant) {
        if (idCommande == null || idCommande.isEmpty())
            throw new IllegalArgumentException("❌ ID commande invalide !");
        if (montant <= 0)
            throw new IllegalArgumentException("❌ Montant doit être > 0 !");
        this.idCommande  = idCommande;
        this.montant     = montant;
        this.statut      = "EN_ATTENTE";
        this.modePaiement = "CARTE";
        this.historique  = new ArrayList<>();
    }

    // ================= CORE =================

    /** Initier le paiement */
    public void initierPaiement() {
        System.out.println("💳 Paiement initié pour commande : " + idCommande);
        System.out.printf("   Montant : %.2f DH | Mode : %s%n", montant, modePaiement);
        statut = "EN_ATTENTE";
        historique.add("Paiement initié le " + new Date());
    }

    /** Valider le paiement */
    public boolean validerPaiement() {
        boolean securise = verifierSecurite();
        boolean accepte  = false;

        if (securise) {
            accepte = switch (modePaiement.toUpperCase()) {
                case "CARTE"    -> simulerCarte();
                case "PAYPAL"   -> simulerPaypal();
                case "VIREMENT" -> true;  // virement toujours accepté (simulé)
                default         -> true;  // mode fictif
            };
        }

        if (accepte) {
            statut = "VALIDE";
            historique.add("✅ Paiement validé le " + new Date());
            System.out.println("✅ Paiement validé !");
            genererRecu();
        } else {
            statut = "REFUSE";
            historique.add("❌ Paiement refusé le " + new Date());
            System.out.println("❌ Paiement refusé !");
        }
        return accepte;
    }

    /** Simulation carte bancaire */
    public boolean simulerCarte() {
        boolean accepte = Math.random() > 0.2;  // 80% de succès
        System.out.println("🏦 Simulation carte : " + (accepte ? "ACCEPTÉ" : "REFUSÉ"));
        return accepte;
    }

    /** Simulation PayPal */
    public boolean simulerPaypal() {
        boolean accepte = Math.random() > 0.1;  // 90% de succès
        System.out.println("🅿️ Simulation PayPal : " + (accepte ? "ACCEPTÉ" : "REFUSÉ"));
        return accepte;
    }

    /** Générer le reçu */
    public void genererRecu() {
        System.out.println("\n===== REÇU DE PAIEMENT =====");
        System.out.println("Commande     : " + idCommande);
        System.out.printf("Montant      : %.2f DH%n", montant);
        System.out.println("Mode         : " + modePaiement);
        System.out.println("Date         : " + new Date());
        System.out.println("Statut       : " + statut);
        System.out.println("============================\n");
    }

    // ================= OPTIONNEL =================

    /** Rembourser le paiement */
    public void rembourserPaiement() {
        if ("VALIDE".equals(statut)) {
            statut = "REMBOURSE";
            historique.add("♻️ Remboursement effectué le " + new Date());
            System.out.printf("♻️ Paiement de %.2f DH remboursé !%n", montant);
        } else {
            System.out.println("❌ Impossible de rembourser un paiement non validé !");
        }
    }

    /** Afficher l'historique */
    public void historiquePaiements() {
        System.out.println("=== Historique des paiements ===");
        if (historique.isEmpty()) {
            System.out.println("  (Aucun historique)");
        } else {
            for (String h : historique) System.out.println("  " + h);
        }
        System.out.println("================================");
    }

    /** Vérification de sécurité (simulée) */
    public boolean verifierSecurite() {
        System.out.println("🔐 Vérification de sécurité...");
        return montant > 0 && idCommande != null;
    }

    /** Choisir le mode de paiement */
    public void choisirModePaiement(String mode) {
        if (mode == null || mode.isEmpty()) {
            System.out.println("❌ Mode invalide !");
            return;
        }
        this.modePaiement = mode.toUpperCase();
        System.out.println("✅ Mode choisi : " + modePaiement);
    }

    // ================= GETTERS / SETTERS =================

    public String getIdCommande()              { return idCommande; }
    public double getMontant()                 { return montant; }
    public String getStatut()                  { return statut; }
    public String getModePaiement()            { return modePaiement; }
    public void   setModePaiement(String mode) { this.modePaiement = mode; }
    public ArrayList<String> getHistorique()   { return historique; }
}
