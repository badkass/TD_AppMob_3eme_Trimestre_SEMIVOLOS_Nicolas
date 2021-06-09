<?php

include_once(dirname(__FILE__).'\includes\DbOperation.php');
$db = new DbOperation();

//Requête HTTP Post
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    //Création d'un utilisateur si les champs sont remplis
    if (isset($_POST['USERNAME']) AND !empty($_POST['USERNAME'])) {
        $result = $db->createUser($_POST['USERNAME'],$_POST['Password'],$_POST['Email']);

        if ($result == 0) {
            $response['status'] = false;
            $response['error'] = true;
            $response['message'] = 'Utilisateur existe deja';
        } else if ($result == 1) {
            $response['status'] = true;
            $response['error'] = false;
            $response['message'] = 'Nouvel utilisateur cree';

        } else if ($result == 2) {
            $response['status'] = false;
            $response['error'] = true;
            $response['message'] = 'Probleme dans la saisie';
        }
    }
}


//Login
if (isset($_GET['login_username']) AND !empty($_GET['login_username'])) {
    if ($db->userLogin($_GET['login_username'],$_GET['login_password'])) {
        echo "Login réussie";
    } else {
        echo "Identifiants incorrects";
    }
}

//Trouver utilisateur
if (isset($_GET['find_username']) AND !empty($_GET['find_username'])) {
    $result = $db->getUserByUsername($_GET['find_username']);
    if ($result) {
        foreach($result as $key => $value) {
            echo $key." : ".$value."<br/>";
        }
    } else {
        echo "Aucun utilisateur trouvé";
    }
}

?>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Android</title>
    <style>
        body {
                }
    </style>
</head>
<body>
    <h1>Gestion utilisateurs</h1>
    <form id="form_create" method='post' action='#'>
        <fieldset>
            <legend>Création utilisateur</legend>
        <div>
            <label for="username">Username</label>
            <input type="text" id="USERNAME" name="USERNAME" required/>
        </div>
        <div>
            <label for="Password">Password</label>
            <input type="password" id="Password" name="Password" required/>
        </div>
        <div>
            <label for="Email">Email</label>
            <input type="email" id="Email" name="Email" required/>
        </div>
        <input type="submit" id="submit" name="submit" value="Créer" />
    </fieldset>
    </form>

    <form id="form_login" method='get' action='#'>
    <fieldset>
    <legend>Login</legend>
        <div>
            <label for="USERNAME">Username</label>
            <input type="text" id="USERNAME" name="login_username" required/>
        </div>
        <div>
            <label for="Password">Password</label>
            <input type="password" id="Password" name="login_password" required/>
        </div>
        <input type="submit" id="submit" name="submit" value="Login" />
    </fieldset>
    </form>

    <form id="form_user" method='get' action='#'>
    <fieldset>
    <legend>Trouver un utilisateur</legend>
        <div>
            <label for="user">Username</label>
            <input type="text" id="user" name="find_username" required/>

        <input type="submit" id="submit" name="submit" value="Trouver" />
    </fieldset>
    </form>
    <?php 

    //Affichage
    if ($response) {
        echo json_encode($response);
    }
    

    ?>
</body>
</html>