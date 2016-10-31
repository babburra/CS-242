import os
import os.path
import lxml.etree as le


def parse_list(in_filename):
    if os.path.isfile(in_filename):
        list_dic = {}
        for ent in le.parse(in_filename).iter('entry'):
            get = ent.xpath
            filename = get('string(name)')
            file_type = get('string(@kind)')
            revision = get('string(commit/@revision)')
            date = get('string(commit/date)').partition('.')[0]
            date = str(date.partition('T')[0]) + " " + str(date.partition('T')[2])
            author = get('string(commit/author)')
            if ent.get('kind') == 'file':
                file_size = get('string(size)')
                info = [str(filename),str(file_type),str(revision),date,str(author),str(file_size + "byte(s)")]
            else:
                info = [str(filename), str(file_type), str(revision), date, str(author)]
            if filename not in list_dic:
                list_dic[str(filename)] = info
            else:
                list_dic[str(filename)].append(info)
        return list_dic