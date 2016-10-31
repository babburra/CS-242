<!doctype html>
<html>
<head>
    <title>Portfolio</title>
    <link rel="stylesheet" href="./static/bootstrap.min.css">
    <link rel="stylesheet" href="./static/styles.css">
</head>
<body>

<div id="container">

<div class = "left">
<?php
	require("query_projects.php");
?>
</div>

<div class="content">
	<div class="top">
		<iframe id="preview"></iframe>
	</div>
	<div id="commentdiv" class="bottom" >
		<!-- form for user to comment on specific project!-->
		<form id="comment_form" name = "comment_form" method="POST">
		<input type="text" name="name" placeholder="Name"><br/>
		<textarea name="comment" placeholder="Comment"></textarea><br/>
		<input type="submit" name="submit" value="comment">
		</form>
		<h4>Comments:</h4>
		<div id="show_comments">
		</div>
	</div>

</div>
</div>

<script src="./static/jquery-3.1.1.min.js"></script>
<script src="./static/script.js"></script>

</body>
</html>