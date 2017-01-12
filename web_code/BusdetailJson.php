<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");

if (!empty($_POST)) {

    $ss=$_POST['root_name'];

//initial query
$query = "Select * FROM bus where root_name='$ss'";

//Update query
    $query_params = array(
        ':root_name'=>$_POST['root_name']
    );

//execute query
try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute();
}
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}

// Finally, we can retrieve all of the found rows into an array using fetchAll 
$rows = $stmt->fetchAll();

if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Post Available!";
    $response["posts"]   = array();
    
     foreach ($rows as $row) {
        $post             = array();
    
        //this line is new:
        $post["bus_name"]  = $row["bus_name"];
        $post["bus_code"] = $row["bus_code"];
        $post["bus_category"]    = $row["bus_category"];
        $post["city_from"]  = $row["city_from"];
        $post["city_to"]  = $row["city_to"];
        $post["root_name"]  = $row["root_name"];
        //update our repsonse JSON data
        array_push($response["posts"], $post);
    }

    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));
}

} else
{
?>
<body>
        
     <form method="POST" action="BusdetailJson.php" enctype="multipart/form-data">
        <br>
        Busname:<br /> 
           <input type="text" name="root_name" placeholder="busname" /> 
            <br /><br />
            <input type="submit" name="submit_image" value="Upload">
    </form>
    <?php 
}
    ?>