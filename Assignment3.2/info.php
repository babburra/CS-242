<?php

class Info {
    public $revision;
    public $date;
    public $author;
    public $msg;
    public $actions = array();

    function __construct($revision, $date, $author, $msg) {
        $this->revision = $revision;
        $this->date = $date;
        $this->author = $author;
        $this->msg = $msg;
    }
}