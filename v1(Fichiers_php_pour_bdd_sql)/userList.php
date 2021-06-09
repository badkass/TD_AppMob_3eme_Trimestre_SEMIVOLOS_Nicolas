<?php

include_once(dirname(__FILE__).'\includes\DbOperation.php');
$db = new DbOperation();

//Requête HTTP GET
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['USERNAME']) AND !empty($_GET['USERNAME'])) {
        $result = $db->getUserByUsername($_GET['USERNAME']);
        if ($result) {
            $response = array(
                'status' => true,
                'error' => false,
                'message' => 'Utilisateur trouvé',
                'username' => $result['USERNAME'],
                'email' =>  $result['Email']
            );
        } else {
            $response = array(
                'status'=>false,
                'error'=>true,
                'message'=>'Aucun utilisateur trouvé'
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

