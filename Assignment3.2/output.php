<?php

class Output {
    # citation: http://stackoverflow.com/questions/15188033/human-readable-file-size
    private static function get_size($bytes, $decimals=2) {
        $size = array('b', 'kb', 'mb', 'gb', 'tb', 'pb', 'eb', 'zb', 'yb');
        $factor = intval(floor((strlen($bytes) - 1) / 3));
        if ($factor == 0) $decimals = 0;
        return sprintf("%.{$decimals}f ", $bytes / pow(1024, $factor)) . $size[$factor];
    }

    private static function get_type($filepath) {
        $path_parts = pathinfo($filepath);
        return $path_parts['extension'];
    }

    private static function get_title($project){
        $recent_update = $project->recent_update;
        echo "<h3>$project->name</h3>\r\n";
        echo "<p><span class='label label-default'>$recent_update->revision</span>";
        echo " $recent_update->author ";
        echo "<span class='text-muted'>$recent_update->msg</span>";
        echo " <span class='pull-right'>$recent_update->date</span></p>\r\n";
    }

    private static function print_version($file, $info) {
        $revision = $info->revision;
        $author = $info->author;
        $msg = $info->msg;
        $date = $info->date;
        $link = "$file->link?p=$revision";

        $action = $info->actions[$file->path];
        $status_colors = array('A'=>'label-success', 'D'=>'label-danger', 'R'=>'label-info', 'C'=>'label-warning');
        $label_color = array_key_exists($action, $status_colors) ? $status_colors[$action] : 'label-default';

        echo "<a href='$link' target='_blank' class='list-group-item'>";
        echo "<span class='label $label_color'>$revision</span> $author ";
        echo "<span class='text-muted'>$msg</span> <span class='pull-right'>$date</span>";
        echo "</a>\r\n";
    }

    private static function get_file($file) {
        $directory = pathinfo($file->path, PATHINFO_DIRNAME);
        $base = pathinfo($file->path, PATHINFO_BASENAME);
        # this type of file we dont want
        if(strpos($base,'.xml') !== false || strpos($base,'.jar') !== false ||
            strpos($base,'.eml') !== false || strpos($base,'.iml') !== false || strpos($base,'.eps') !== false ||
            strpos($base,'.tex') !== false || strpos($base,'.class') !== false){
            return;
        }
        $type = self::get_type($base);
        if (is_null($type))
            $type = (string) "non_file";
        $size = self::get_size($file->size);

        # print the file title
        echo "<div class='list-group'>\r\n";
        echo "<a href='$file->link' target=_blank' class='list-group-item'>";
        echo "<span class='text-muted'>$directory/</span><strong>$base</strong>";
        echo " <span class='pull-right'><span class='text-muted'>$type</span> $size</span></a>\r\n";
        foreach ($file->info as $info) {
            self::print_version($file, $info);
        }
        echo "</div>\r\n";
    }

    public static function print_project($project) {
        self::get_title($project);        
        foreach ($project->files as $file) {
            self::get_file($file);
        }
    }
}
