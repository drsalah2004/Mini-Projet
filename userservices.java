package services;

import java.util.ArrayList;
import model.user;

public class userservices {

    private ArrayList<user> users = new ArrayList<>();

    // INSCRIPTION
    public void register(String username, String email, String password) {
        user user = new user(username, email, password);
        users.add(user);
        System.out.println("Inscription réussie !");
    }

    // CONNEXION
    public boolean login(String email, String password) {
        for (user user : users) {
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