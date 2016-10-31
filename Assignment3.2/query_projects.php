<?php

require_once __DIR__ . '/parse.php';
require_once __DIR__ . '/output.php';

$projects = Parse::parser(__DIR__ . '/static/svn_list.xml', __DIR__ . '/static/svn_log.xml');
foreach ($projects as $project) {
    Output::print_project($project);
}
?>