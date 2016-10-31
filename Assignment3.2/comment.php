<?php
require('connect.php');
$name = $_POST['name'];
$comment = $_POST['comment'];

	if($name && $comment){
		$project = $_COOKIE['assignment'];
		//insert name, comments, project to database
		$insert = mysql_query("INSERT INTO comment (name, comment, project) VALUES ('$name', '$comment', '$project')");
		$name = NULL;
		$comment = NULL;
		$project = NULL;
	}
?>