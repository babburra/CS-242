<?php
require('connect.php');
$deleteid = $_GET['id'];
mysql_query("DELETE FROM comment WHERE id='$deleteid'");
header("location: success.php");
?>