package client.locales;

import java.util.ListResourceBundle;

public class gui_fr_FR extends ListResourceBundle {
    public Object[][] getContents(){
        return contents;
    }
    private Object[][] contents = {{"Error", "Erreur"},
            {"Timeout", "Le délai d'attente du serveur est écoulé"},
            {"UnavailableError", "Serveur indisponible"},
            {"ServerDoesntResponse", "Le serveur ne répond pas"},
            {"ServerIOException", "Je ne sais pas comment appeler cette erreur"},
            {"InvalidCredentials", "Vérifiez vos informations"},
            {"AuthTitle", "Autorisation"},
            {"LoginField", "Identifiant"},
            {"PasswordField", "Mot de passe"},
            {"SignUpButton", "S'inscrire"},
            {"UserNotFound", "Utilisateur non trouvé"},
            {"PasswordIncorrect", "Mot de passe incorrect"},
            {"UserAlreadyExists", "L'utilisateur existe déjà"},
            {"RegisterSuccess", "Inscription réussie"},
            {"AuthSuccess", "Autorisation réussie"},
            {"Info", "Information"},
            {"NotValidData", "Données non valides"},
            {"DBError", "Erreur de la base de données"},
            {"ObjectNotFound", "Objet non trouvé"},
            {"Forbidden", "Accès refusé"},
            {"InfoResult", "Type : {0}\nNombre d'éléments : {1}\nDate de la dernière sauvegarde : {2}\nDate de la dernière initialisation : {3}"},
            {"EditTitle", "Édition"},
            {"Cancel", "Annuler"},
            {"NumberFormatException", "Erreur de format de nombre"},
            {"UserLabel", "Utilisateur"},
            {"Exit", "Sortie"},
            {"LogOut", "Déconnexion"},
            {"Help", "Aide"},
            {"Add", "Ajouter"},
            {"Update", "Mettre à jour"},
            {"RemoveByID", "Supprimer par ID"},
            {"Clear", "Effacer"},
            {"ExecuteScript", "Exécuter le script"},
            {"RemoveLower", "Supprimer inférieur"},
            {"SumOfCapacity", "Somme des capacités"},
            {"FilterByCapacity", "Filtrer par capacité"},
            {"FilterLessThanType", "Filtrer par type"},
            {"TableTab", "Tableau"},
            {"VisualTab", "Visualisation"},
            {"Owner", "Propriétaire"},
            {"Name", "Nom"},
            {"CreationDate", "Date de création"},
            {"EnginePower", "Puissance"},
            {"Capacity", "Capacité"},
            {"VehicleType", "Type de véhicule"},
            {"FuelType", "Type de carburant"},
            {"EditTitle", "Édition"},
            {"Cancel", "Annuler"},
            {"CommandNotFound", "Commande non trouvée"},
            {"CheckScriptErr", "Vérifiez votre script"},
            {"ScriptExecutionErr", "Erreur lors de l'exécution du script"},
            {"HelpResult", "add {element} : ajoute un nouvel élément à la collection\n" +
                    "clear : vide la collection\n" +
                    "execute_script filename : exécute le script du fichier spécifié\n" +
                    "exit : termine le programme sans sauvegarde\n" +
                    "filter_by_capacity : affiche les éléments dont le champ capacité est égal à la valeur spécifiée\n" +
                    "filter_less_than_type : affiche les éléments dont la valeur du champ type de véhicule est inférieure à la valeur spécifiée\n" +
                    "help : affiche des informations sur les commandes disponibles\n" +
                    "info : affiche des informations sur la collection\n" +
                    "remove_by_id id : supprime un élément par ID\n" +
                    "remove_last : supprime le dernier élément\n" +
                    "remove_lower {element} : supprime tous les éléments inférieurs à celui spécifié\n" +
                    "show : affiche des informations sur la collection\n" +
                    "sort : trie la collection par noms des éléments\n" +
                    "sum_of_capacity : affiche la somme de tous les champs capacité\n" +
                    "update id {element} : met à jour la valeur de l'élément"},
            {"Success", "Succès"},
            {"UpdateSuc", "Mise à jour réussie"},
            {"ClearSuc", "Effacement réussi"},
            {"RemoveById", "Suppression par ID"},
            {"RemoveByIDSuc", "Succès"},
            {"ScriptExecutionSuc", "Succès"},
            {"Result", "Résultat"},
            {"WrongType", "Type incorrect"},
            {"FilterLessThanTypeResult", "Objets trouvés"},
            {"WrongNumber", "Nombre incorrect"}};
}
