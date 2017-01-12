<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {


    //$q="SELECT AVG(rating) FROM user_review"
   
    //initial query
    $query = "INSERT INTO bus(bus_name,bus_code,bus_category,city_from,city_to,root_name) VALUES (:busname,:bus_code,:buscategory,:cityfrom,:cityto,:root)";

    //Update query
    $query_params = array(
        ':busname'=>$_POST['busname'],
        ':bus_code'=>$_POST['bus_code'],
        ':buscategory'=>$_POST['buscategory'],
        ':cityfrom'=>$_POST['cityfrom'],
        ':cityto'=>$_POST['cityto'],
        ':root'=>$_POST['root']
    );
  
    //execute query
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
        $response["success"] = 0;
        $response["message"] = "Database Error. Couldn't add post!";
        die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Username Successfully Added!";
    echo json_encode($response);
   
} else {
?>
        <body>
        
     <form method="POST" action="Busdetail.php" enctype="multipart/form-data">
        <br>
        Busname:<br /> 
           <input type="text" name="busname" placeholder="busname" /> 
            <br /><br />
        Bus Code:<br />
            <input type="text" name="bus_code" placeholder="bus image" /> 
        <br />
        Bus category:<br />
            <input type="text" name="buscategory" placeholder="bus category" /> 
            <br /><br />
        City From:<br />
            <input type="text" name="cityfrom" placeholder="city from" /> 
        <br />
        City To:<br />
            <input type="text" name="cityto" placeholder="city to" /> 
        <br />
        Root Name:<br />
            <input type="text" name="root" placeholder="root name" /> 
        <br />
        Bus Time:<br />
            <input type="text" name="time" placeholder="Bus Time" /> 
        <br />
        <input type="submit" name="submit_image" value="Upload">
     </form>

     </body>
    <?php
}

?> 