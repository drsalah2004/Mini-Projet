 Application Chrionline en Java
==============================

-Description
ChriOnline est une application e-commerce développée en Java dans le cadre d’un mini-projet académique ChriOnline,Khalik 9rib.
Elle permet aux utilisateurs de consulter des produits, gérer un panier et effectuer des commandes via une architecture client-serveur basée sur les sockets TCP/UDP.

--------------------------------------------------

-Objectifs
- Implémenter une architecture client-serveur en Java
- Utiliser les sockets TCP pour la communication
- Gérer les opérations d’un système e-commerce
- Travailler en équipe avec une répartition claire des tâches

--------------------------------------------------

-Technologies utilisées
- Java
- Sockets TCP/UDP
- Multi-threading
- JDBC (MySQL)
- Git & GitHub

--------------------------------------------------

— Fonctionnalités

.. Utilisateur
- Inscription
- Connexion / Déconnexion
- Gestion de session

.. Produits
- Lister les produits
- Afficher les détails
- Ajouter / modifier / supprimer (admin)

.. Panier
- Ajouter produit
- Supprimer produit
- Modifier quantité
- Calculer le total

.. Commande
- Créer une commande
- Valider commande
- Historique

.. Paiement
- Simulation de paiement
- Validation des données

--------------------------------------------------

-Communication Réseau
- TCP : opérations principales (login, panier, commande)
- UDP : notifications (optionnel)

--------------------------------------------------

-Exécution du projet

1. Lancer le serveur
cd server
javac ServerMain.java
java ServerMain

2. Lancer le client
cd client
javac ClientMain.java
java ClientMain

--------------------------------------------------

-Exemple de fonctionnement

1. L'utilisateur se connecte
2. Consulte les produits
3. Ajoute au panier
4. Valide la commande
5. Effectue un paiement simulé

--------------------------------------------------
