📌 Projet RPC : Gestion des Commandes d'un Restaurant

📖 Description
Ce projet est une application distribuée de gestion des commandes pour un restaurant, développée dans le cadre du module Application Répartie. Le système utilise Java RMI (Remote Method Invocation) pour la communication entre le serveur et les clients, et Swing pour l'interface graphique.

🎯 Objectifs :
Gestion des commandes en temps réel : Consultation du menu, prise de commandes, et calcul de factures.
Interface utilisateur intuitive : Utilisation de Swing pour une interaction fluide.
Fiabilité et sécurité : Transactions sécurisées et enregistrements précis des commandes.

⚙️ Fonctionnalités
🔍 Consultation du Menu : Affichage des plats disponibles avec leurs descriptions et prix.
🛒 Prise de Commandes : Sélection de plats à partir du menu et envoi des commandes au serveur.
💸 Calcul de Factures : Calcul automatique des factures en fonction des plats commandés.
👥 Gestion des Clients : Enregistrement des commandes de chaque client de manière distincte.

🏗️ Architecture et Conception
L'application est basée sur une architecture client-serveur en utilisant Java RMI :

Client : Interface graphique avec Java Swing permettant :
Connexion au serveur
Consultation du menu
Passage de commandes
Calcul de facture
Serveur :
Fournit les services RMI pour le menu, les commandes et les factures
Gère les données des plats et les commandes en temps réel
Interface RMI :
Définit les méthodes distantes utilisées par le client pour interagir avec le serveur.
