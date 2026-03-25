import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientTCP {

    static final String HOST = "localhost";
    static final int PORT = 10000;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private Scanner scanner;

    private boolean connecte = false;
    private String nomUtilisateur = "sek";

    public ClientTCP() {
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        ClientTCP client = new ClientTCP();
        client.start();
    }

    public void start() {
        try {
            connecter();
            menu();
        } catch (Exception e) {
            System.out.println("Erreur : " + e.getMessage());
        } finally {
            fermer();
        }
    }

    private void connecter() throws IOException {
        socket = new Socket(HOST, PORT);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("✅ Connecté au serveur");
    }

    private void menu() throws IOException {
        boolean running = true;

        while (running) {
            if (!connecte) {
                System.out.println("\n1.Connexion  2.Inscription  0.Quitter");
            } else {
                System.out.println("\n1.Produits  2.Ajouter  3.Panier  4.Payer  5.Déconnexion  0.Quitter");
            }

            String choix = scanner.nextLine();

            switch (choix) {
                case "1":
                    if (!connecte) connexion();
                    else envoyer("PRODUITS");
                    break;

                case "2":
                    if (!connecte) inscription();
                    else ajouterPanier();
                    break;

                case "3":
                    envoyer("VOIR_PANIER");
                    break;

                case "4":
                    envoyer("PAIEMENT|CARTE");
                    break;

                case "5":
                    deconnexion();
                    break;

                case "0":
                    running = false;
                    break;

                default:
                    System.out.println("Choix invalide");
            }
        }
    }

    private void connexion() throws IOException {
        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Mot de passe: ");
        String mdp = scanner.nextLine();

        String rep = envoyer("CONNEXION|" + login + "|" + mdp);

        if (rep.startsWith("OK")) {
            connecte = true;
            nomUtilisateur = login;
        }
    }

    private void inscription() throws IOException {
        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Mot de passe: ");
        String mdp = scanner.nextLine();

        System.out.print("Nom: ");
        String nom = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Adresse: ");
        String adr = scanner.nextLine();

        envoyer("INSCRIPTION|" + login + "|" + mdp + "|" + nom + "|" + email + "|" + adr);
    }

    private void ajouterPanier() throws IOException {
        System.out.print("ID produit: ");
        String id = scanner.nextLine();

        System.out.print("Quantité: ");
        String qte = scanner.nextLine();

        envoyer("AJOUTER_PANIER|" + id + "|" + qte);
    }

    private void deconnexion() throws IOException {
        envoyer("DECONNEXION");
        connecte = false;
    }

    private String envoyer(String msg) throws IOException {
        out.println(msg);
        String rep = in.readLine();
        System.out.println("➡️ " + rep);
        return rep;
    }

    private void fermer() {
        try {
            if (socket != null) socket.close();
            System.out.println("Connexion fermée");
        } catch (IOException ignored) {}
    }
}