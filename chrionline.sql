-- =========================================================
-- Base de données : chrionline
-- =========================================================

DROP DATABASE IF EXISTS chrionline;
CREATE DATABASE chrionline
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE chrionline;

-- =========================================================
-- TABLE : utilisateurs
-- =========================================================
CREATE TABLE utilisateurs (
    login VARCHAR(50) PRIMARY KEY,
    mot_de_passe VARCHAR(255) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    adresse VARCHAR(255),
    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =========================================================
-- TABLE : produits
-- =========================================================
CREATE TABLE produits (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT,
    prix DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    categorie VARCHAR(50),
    date_ajout DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =========================================================
-- TABLE : commandes
-- =========================================================
CREATE TABLE commandes (
    id VARCHAR(20) PRIMARY KEY,
    utilisateur_login VARCHAR(50) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    statut ENUM('EN_ATTENTE','VALIDEE','EXPEDIEE','LIVREE','ANNULEE') DEFAULT 'EN_ATTENTE',
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_commandes_utilisateur
        FOREIGN KEY (utilisateur_login) REFERENCES utilisateurs(login)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- =========================================================
-- TABLE : items_commande
-- =========================================================
CREATE TABLE items_commande (
    id INT AUTO_INCREMENT PRIMARY KEY,
    commande_id VARCHAR(20) NOT NULL,
    produit_id INT NOT NULL,
    quantite INT NOT NULL,
    prix_unitaire DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_items_commande
        FOREIGN KEY (commande_id) REFERENCES commandes(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_items_produit
        FOREIGN KEY (produit_id) REFERENCES produits(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- =========================================================
-- TABLE : paiements
-- =========================================================
CREATE TABLE paiements (
    id INT AUTO_INCREMENT PRIMARY KEY,
    commande_id VARCHAR(20) NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    mode ENUM('CARTE','PAYPAL','VIREMENT','FICTIF') DEFAULT 'CARTE',
    statut ENUM('EN_ATTENTE','VALIDE','REFUSE','REMBOURSE') DEFAULT 'EN_ATTENTE',
    date_paiement DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_paiement_commande
        FOREIGN KEY (commande_id) REFERENCES commandes(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- =========================================================
-- DONNEES DE TEST
-- =========================================================
INSERT INTO utilisateurs (login, mot_de_passe, nom, email, adresse) VALUES
('admin', 'admin123', 'Administrateur', 'admin@chrionline.com', 'Casablanca'),
('client1', 'client123', 'Client Test', 'client1@chrionline.com', 'Rabat');

INSERT INTO produits (nom, description, prix, stock, categorie) VALUES
('Laptop Pro 15', 'Intel i7, 16GB RAM, 512GB SSD', 8999.99, 10, 'Informatique'),
('Smartphone X12', 'Ecran 6.5, 128GB, Double SIM', 3499.00, 25, 'Mobile'),
('Casque Bluetooth', 'Son HD, ANC, 30h batterie', 799.00, 50, 'Audio'),
('Clavier Mecanique', 'RGB, Switch Cherry MX Red', 599.00, 30, 'Peripheriques'),
('Souris Gamer', 'DPI reglable, 6 boutons', 349.00, 40, 'Peripheriques');

INSERT INTO commandes (id, utilisateur_login, total, statut) VALUES
('CMD001', 'client1', 4298.00, 'VALIDEE');

INSERT INTO items_commande (commande_id, produit_id, quantite, prix_unitaire) VALUES
('CMD001', 2, 1, 3499.00),
('CMD001', 3, 1, 799.00);

INSERT INTO paiements (commande_id, montant, mode, statut) VALUES
('CMD001', 4298.00, 'CARTE', 'VALIDE');

-- =========================================================
-- VERIFICATION
-- =========================================================
SHOW TABLES;
SELECT * FROM utilisateurs;
SELECT * FROM produits;
SELECT * FROM commandes;
SELECT * FROM items_commande;
SELECT * FROM paiements;