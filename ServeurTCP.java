import java.io.*;
import java.net.*;

public class ServeurTCP {

    static final int PORT = 10000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            System.out.println("🚀 Serveur démarré...");

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connecté : " + client.getInetAddress());

                new Thread(new ClientHandler(client)).start();
            }

        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {

        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
            ) {

                String msg;

                while ((msg = in.readLine()) != null) {

                    System.out.println("Reçu : " + msg);

                    String rep = traiter(msg);

                    out.println(rep);
                }

            } catch (IOException e) {
                System.out.println("Client déconnecté");
            }
        }

        private String traiter(String msg) {

            String[] parts = msg.split("\\|");
            String cmd = parts[0];

            switch (cmd) {

                case "CONNEXION":
                    return "OK|Connexion réussie";

                case "INSCRIPTION":
                    return "OK|Inscription réussie";

                case "PRODUITS":
                    return "PRODUITS|1,Laptop,5000,10,Info|2,Phone,2000,5,Mobile";

                case "AJOUTER_PANIER":
                    return "OK|Produit ajouté";

                case "VOIR_PANIER":
                    return "PANIER|Laptop,2,10000|TOTAL=10000";

                case "PAIEMENT":
                    return "OK|Paiement effectué";

                case "DECONNEXION":
                    return "OK|Déconnecté";

                default:
                    return "ERR|Commande inconnue";
            }
        }
    }
}