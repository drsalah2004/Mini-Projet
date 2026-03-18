package app;

import java.util.ArrayList;

// CLASS PRINCIPALE
public class Utilisateur {

    // ---------------- MODEL ----------------
    static class User {
        private String username;
        private String email;
        private String password;

        public User(String username, String email, String password) {
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getUsername() {
            return username;
        }
    }

    // ---------------- SERVICE ----------------
    static class UserService {

        private ArrayList<User> users = new ArrayList<>();

        // INSCRIPTION
        public void register(String username, String email, String password) {
            User user = new User(username, email, password);
            users.add(user);
            System.out.println("Inscription réussie !");
        }

        // CONNEXION
        public boolean login(String email, String password) {
            for (User user : users) {
                if (user.getEmail().equals(email) &&
                    user.getPassword().equals(password)) {
                    System.out.println("Connexion réussie !");
                    return true;
                }
            }
            System.out.println("Email ou mot de passe incorrect !");
            return false;
        }
    }

    // ---------------- MAIN ----------------
    public static void main(String[] args) {

        UserService service = new UserService();

        // Inscription
        service.register("Salah32", "salah2004@gmail.com", "Lolo");

        // Connexion
        service.login("salah2004@gmail.com", "Lolo");
    }
}
