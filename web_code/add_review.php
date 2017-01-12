<?php

//load and connect to MySQL database stuff
require("config.inc.php");

if (!empty($_POST)) {
	//initial query
	$query = "INSERT INTO user_review ( rating, title, comment,username,bus_name,root_name ) VALUES ( :rating, :title, :review, :user, :bus_name, :root_name ) ";

    //Update query
    $query_params = array(
        ':user' => $_POST['username'],
        ':rating' => $_POST['rating'],
        ':title' => $_POST['title'],
		':review' => $_POST['comment'],
		':bus_name'=>$_POST['bus_name'],
		':root_name'=>$_POST['root_name']
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
		<h1>Add Comment</h1> 
		<form action="add_review.php" method="post"> 
		    Username:<br /> 
		    <input type="text" name="username" placeholder="username" /> 
		    <br /><br />
		    Rating:<br />
		    <input type="text" name="rating" placeholder="Rating" /> 
		    <br /><br />
		    Title:<br /> 
		    <input type="text" name="title" placeholder="post title" /> 
		    <br /><br />
		    comment:<br /> 
		    <input type="text" name="comment" placeholder="comment" /> 
		    <br /><br />
		    Bus Name:<br /> 
		    <input type="text" name="bus_name" placeholder="comment" /> 
		    <br /><br />
		    Root Name:<br /> 
		    <input type="text" name="root_name" placeholder="comment" /> 
		    <br /><br />

		    <input type="submit" value="Add Comment" /> 
		</form> 
	<?php
}

?> 