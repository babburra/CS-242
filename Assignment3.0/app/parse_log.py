import os
import os.path
import lxml.etree as le


def parse_log(in_filename):
    if os.path.isfile(in_filename):
        log = {}
        for ent in le.parse(in_filename).iter('logentry'):
            get = ent.xpath
            log_files = ent.findall('paths/path')
            for log_file in log_files:
                if log_file.attrib['kind'] == 'file':
                    log_filename = log_file.text[10:]
                    log_title = log_filename.partition('/')[0]
                    log_date = get('string(date)').partition('.')[0]
                    log_trim_date = str(log_date.partition('T')[0]) + " " + str(log_date.partition('T')[2])
                    log_revision = get('string(@revision)')
                    log_msg = get('string(msg)')
                    if log_filename not in log:
                        log[str(log_filename)] = [str(log_filename), str(log_title), log_trim_date, str(log_revision), str(log_msg)]
                    else:
                        log[str(log_filename)].append([str(log_filename), str(log_title), log_trim_date, str(log_revision), str(log_msg)])
        return log