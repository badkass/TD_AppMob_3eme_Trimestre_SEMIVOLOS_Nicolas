<?php
/*

get User

Affiche l'utilisateur recherché
*/
include_once(dirname(__FILE__).'\includes\DbOperation.php');
$db = new DbOperation();

//Requête HTTP GET
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['USERNAME']) && isset($_GET['Password'])) {
        $result = $db->userLogin($_GET['USERNAME'],$_GET['Password']);
        if ($result) {
            $user = $db->getUserByUsername($_GET['USERNAME']);
            $response = array(
                'status' => true,
                'error' => false,
                'message' => 'Connexion reussie',
                'username' => $user['USERNAME'],
                'email' =>  $user['Email']
            );
        } else {
            $response = array(
                'status'=>false,
                'error'=>true,
                'message'=>'Mauvaise authentification'
            );
        }
    } else {
        $response = array(
            'status'=> false,
            'error'=>true,
            'message' => 'Donnees requises manquantes'
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

