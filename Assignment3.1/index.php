<!doctype html>
<html>
<head>
    <title>Portfolio</title>
    <link rel="stylesheet" href="./static/bootstrap.min.css">
    <link rel="stylesheet" href="./static/styles.css">
</head>
<body>

<div id="content">

<div id="comment">
<a href="comment.php"> Comments </a>
</div>

<?php

require_once __DIR__ . '/parse.php';
require_once __DIR__ . '/output.php';

$projects = Parse::parser(__DIR__ . '/static/svn_list.xml', __DIR__ . '/static/svn_log.xml');
foreach ($projects as $project) {
    Output::print_project($project);
}
?>

</div>

<div id="sidebar">
    <iframe id="preview"></iframe>
</div>

<script src="./static/jquery-3.1.1.min.js"></script>
<script src="./static/script.js"></script>

</body>
</html>