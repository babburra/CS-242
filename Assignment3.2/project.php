<?php

class Project {
    public $name;
    public $recent_update;
    public $files = array();

    function __construct($name, $recent_update){
    	$this->name = $name;
    	$this->recent_update = $recent_update;
    }
}