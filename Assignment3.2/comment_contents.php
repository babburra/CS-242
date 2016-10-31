<?php
	//connect to database
	require('connect.php');

	//query comments about an assignment
	function query_comments($assignment){
		global $project;
		$assignment = $_COOKIE['assignment'];
		//make sure we are passing string to database
		$project = mysql_real_escape_string($assignment);

		//query all comments about the assignment 
		$result=mysql_query("SELECT * FROM comment WHERE project = '" . $assignment . "' ORDER BY id DESC") or die(mysql_error());
		//form html code
		while($rows=mysql_fetch_assoc($result)){
			$id = $rows['id'];
			$name = $rows['name'];
			$comment = (string)$rows['comment'];
			$ret .= '<b>' . $name . '</b>' . '<br />' . '<br />' . htmlspecialchars($comment, ENT_QUOTES, 'UTF-8') . '<br />' . '<br />' . '<hr width = 500px/>';
		}
		return $ret;
	}

	echo query_comments($_POST['queryComment']);
?>