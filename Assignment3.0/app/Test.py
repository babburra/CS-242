from parse_log import parse_log
from parse_list import parse_list

if __name__ == '__main__':
    log_file = "svn_log.xml"
    list_file = "svn_list.xml"
    print parse_log(log_file)
    print parse_list(list_file)