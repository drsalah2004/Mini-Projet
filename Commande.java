import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    private String         id;
    private Utilisateur    utilisateur;
    private List<ItemPanier> items;
    private double         total;
    private String         statut;   // EN_ATTENTE, VALIDEE, EXPEDIEE, LIVREE, ANNULEE
    private Date           dateCreation;

    // ================= CONSTRUCTEUR =================
    public Commande(Utilisateur utilisateur) {
        if (utilisateur == null) throw new IllegalArgumentException("❌ Utilisateur invalide !");
        this.id           = genererID();
        this.utilisateur  = utilisateur;
        this.items        = new ArrayList<>();
        this.statut       = "EN_ATTENTE";
        this.dateCreation = new Date();
    }

    /** Construire une commande à partir d'un panier */
    public Commande(Utilisateur utilisateur, Panier panier) {
        this(utilisateur);
        if (panier == null || panier.getItems().isEmpty()) {
            throw new IllegalArgumentException("❌ Panier vide ou invalide !");
        }
        this.items.addAll(panier.getItems());
        calculerTotal();
    }

    // ================= CORE =================

    /** 1. Ajouter un item */
    public void ajouterItem(ItemPanier item) {
        if (item == null) {
            System.out.println("❌ Item invalide !");
            return;
        }
        items.add(item);
        calculerTotal();
    }

    /** 2. Valider la commande */
    public void validerCommande() {
        if (items.isEmpty()) {
            System.out.println("❌ Impossible de valider : commande vide !");
            return;
        }
        if (!"EN_ATTENTE".equals(statut)) {
            System.out.println("❌ La commande ne peut pas être validée (statut : " + statut + ")");
            return;
        }
        this.statut = "VALIDEE";
        envoyerConfirmation();
        System.out.println("✅ Commande " + id + " validée !");
    }

    /** 3. Annuler la commande */
    public void annulerCommande() {
        if ("VALIDEE".equals(statut) || "EXPEDIEE".equals(statut) || "LIVREE".equals(statut)) {
            System.out.println("❌ Impossible d'annuler une commande déjà " + statut + " !");
            return;
        }
        this.statut = "ANNULEE";
        System.out.println("⚠️ Commande " + id + " annulée !");
    }

    /** 4. Générer un identifiant unique */
    public String genererID() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // ================= LOGIQUE =================

    public double calculerTotal() {
        total = 0;
        for (ItemPanier item : items) {
            total += item.calculerSousTotal();
        }
        return total;
    }

    // ================= OPTIONNEL =================

    /** 5. Suivre le statut */
    public String suivreStatut() {
        return statut;
    }

    /** 6. Modifier le statut (ex: EXPEDIEE, LIVREE) */
    public void modifierStatut(String nouveauStatut) {
        if (nouveauStatut == null || nouveauStatut.isEmpty()) {
            System.out.println("❌ Statut invalide !");
            return;
        }
        List<String> statutsValides = List.of("EN_ATTENTE", "VALIDEE", "EXPEDIEE", "LIVREE", "ANNULEE");
        if (!statutsValides.contains(nouveauStatut)) {
            System.out.println("❌ Statut inconnu : " + nouveauStatut);
            return;
        }
        this.statut = nouveauStatut;
        System.out.println("🔄 Statut modifié en : " + statut);
    }

    /** 7. Envoyer confirmation par email (simulé) */
    public void envoyerConfirmation() {
        System.out.println("📧 Confirmation envoyée à : " + utilisateur.getEmail()
                + " (Client : " + utilisateur.getNom() + ")");
    }

    /** 8. Générer facture */
    public void genererFacture() {
        System.out.println("\n======= FACTURE =======");
        System.out.println("ID Commande  : " + id);
        System.out.println("Date         : " + dateCreation);
        System.out.println("Client       : " + utilisateur.getNom());
        System.out.println("Email        : " + utilisateur.getEmail());
        System.out.println("-----------------------");
        for (ItemPanier item : items) {
            item.afficherItem();
        }
        System.out.println("----------------------");
        System.out.printf("TOTAL        : %.2f DH%n", total);
        System.out.println("Statut       : " + statut);
        System.out.println("======================\n");
    }

    // ================= GETTERS =================

    public String          getId()           { return id; }
    public double          getTotal()        { return total; }
    public String          getStatut()       { return statut; }
    public Utilisateur     getUtilisateur()  { return utilisateur; }
    public List<ItemPanier> getItems()       { return items; }
    public Date            getDateCreation() { return dateCreation; }

	public Commande getPanier() {
		// TODO Auto-generated method stub
		return null;
	}
}
