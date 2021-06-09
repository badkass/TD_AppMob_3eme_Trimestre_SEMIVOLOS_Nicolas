<?php
/*

UserRegister est un web service REST

*/
include_once(dirname(__FILE__).'\includes\DbOperation.php');
$db = new DbOperation();

//Requête HTTP Post
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    //Création d'un utilisateur si les champs sont remplis
    if (isset($_POST['USERNAME']) AND !empty($_POST['USERNAME']) AND isset($_POST['Email']) AND isset($_POST['Password'])) {
        $result = $db->createUser($_POST['USERNAME'],$_POST['Password'],$_POST['Email']);

        if ($result == 0) {
            $response = array(
                'status' => false,
                'error' => true,
                'message' => 'Utilisateur existe deja'
            );
        } else if ($result == 1) {
            $response = array(
                'status' => true,
                'error' => false,
                'message' => 'Nouvel utilisateur cree'
            );

        } else if ($result == 2) {
            $response = array(
                'status' => true,
                'error' => false,
                'message' => 'Donnees requises manquantes'
            );
        }
    } else {
        $response = array(
            'status' => true,
            'error' => false,
            'message' => 'Probleme dans la saisie'
        );
    }
    
} else {
    $response = array(
        'status'=> false,
        'error'=>true,
        'message' => 'Requete invalide'
    );
}

echo json_encode($response);

?>

