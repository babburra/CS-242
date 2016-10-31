<?php

class File {
    public $path;
    public $link;
    public $size;
    public $info = array();

    function __construct($path, $link, $size) {
        $this->path = $path;
        $this->link = $link;
        $this->size = $size;
    }
}