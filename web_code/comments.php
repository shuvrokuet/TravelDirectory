<?php

/*
Our "config.inc.php" file connects to database every time we include or require
it within a php script.  Since we want this script to add a new user to our db,
we will be talking with our database, and therefore,
let's require the connection to happen:
*/
require("config.inc.php");

if (!empty($_POST)) {

    $bus_code=$_POST['bus_code'];
    $root_name=$_POST['root_name'];

//initial query
$query = "Select * FROM user_review where bus_name='$bus_code' AND root_name='$root_name'";

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
    $sum=0;
    $i=0;
    $response["posts"]   = array();
    
     foreach ($rows as $row) {
        $post             = array();
    
        //this line is new:
        $post["post_id"]  = $row["post_id"];
        $post["username"] = $row["username"];
        $post["title"]    = $row["title"];
        $post["review"]  = $row["comment"];
        $post["user_rating"] = $row["rating"];
        $sum = $sum  + $post["user_rating"];
        $i++;
        //update our repsonse JSON data
        array_push($response["posts"], $post);
    }
    $response["avg"]=$sum/$i;

    // echoing JSON response
    echo json_encode($response);
    echo $sum/$i;
    //echo $i;
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No Post Available!";
    die(json_encode($response));
}
}
else
{
?>
<body>
        
     <form method="POST" action="comments.php" enctype="multipart/form-data">
        <br>
        Busname:<br /> 
           <input type="text" name="bus_code" placeholder="busname" /> 
            <br /><br />
            BusRoot name:<br />
           <input type="text" name="root_name" placeholder="busname" /> 

            <input type="submit" name="submit_image" value="Upload">
    </form>
    <?php 
}
    ?>