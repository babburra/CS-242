<?php

require_once __DIR__ . '/info.php';
require_once __DIR__ . '/file.php';
require_once __DIR__ . '/project.php';

class Parse{
	private static function trim_date($date){
		return substr($date, 0, 10);
	}

	private static function trim_path($path){
		$pos = strpos($path, '/', 1);
		return substr($path, $pos + 1);
	}

	private static function create_info($xml) {
        $author = (string) $xml->author;
        $message = (string) $xml->msg;
        $revision = (string) $xml->attributes()->revision;
        $date = self::trim_date($xml->date);
        return new Info($revision, $date, $author, $message);
    }

	private static function create_project($entry, $revision_info) {
        $revision = (string) $entry->commit->attributes()->revision;
        return new Project((string) $entry->name, $revision_info[$revision]);
    }

	private static function find_parent($project, $entry) {
        $file_path = (string) $entry->name;
        if(isset($file_path[strlen($project->name)])){
            return $file_path[strlen($project->name)] == '/';
        }
        return;
    }

    private static function add_file($project, $entry, $base, $path_info) {
        # we want file only
        if ($entry->attributes()->kind != 'file' )
            return;
        $path = (string) $entry->name;
        $link = $base . $path;
        $size = intval((string) $entry->size);

        $file = new File($path, $link, $size);
        $file->info = $path_info[$path];

        $project->files[] = $file;
    }

	public static function parser($list_file, $log_file){
		$svn_list = simplexml_load_file($list_file);
		$svn_log = simplexml_load_file($log_file);
		$path_info = array();
		$revision_info = array();

		foreach ($svn_log->logentry as $logentry) {
			$info = self::create_info($logentry);
			$revision_info[$info->revision] = $info;
			foreach ($logentry->paths->path as $path){
				$file_path = self::trim_path($path);
				$action = (string) $path->attributes()->action;
				$info->actions[$file_path] = $action;
				$path_info[$file_path][] = $info;
			}
		}

		$projects = array();
        $base_path = (string) $svn_list->list->attributes()->path . '/';
        foreach ($svn_list->list->entry as $entry) {
            #if current is null create, else add 
            if (isset($cur_project) && self::find_parent($cur_project, $entry)) {
                self::add_file($cur_project, $entry, $base_path, $path_info);
            } else {
                $cur_project = self::create_project($entry, $revision_info);
                $projects[] = $cur_project;
            }
        }
        return $projects;	
	}	
}