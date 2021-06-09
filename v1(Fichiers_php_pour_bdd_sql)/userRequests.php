<?php

include_once(dirname(__FILE__).'\includes\DbOperation.php');
$db = new DbOperation();

//Requête HTTP GET
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    if (isset($_GET['USERNAME']) || isset($_GET['Email'])) {
        $params;

        if (isset($_GET['USERNAME']) && !empty($_GET['USERNAME'])) {
            $params['USERNAME'] = strtolower($_GET['USERNAME']);
        } else {
            $params['USERNAME'] = "%";
        }
        if (isset($_GET['Email'])&& !empty($_GET['Email'])) {
            $params['Email'] = strtolower($_GET['Email']);
        } else {
            $params['Email'] = "%";
        }
        
        $result = $db->getUsersByParams($params);

        if ($result) {
            $response = array(
                'status' => true,
                'error' => false,
                'message' => 'Utilisateur trouve',
                'users' => $result
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