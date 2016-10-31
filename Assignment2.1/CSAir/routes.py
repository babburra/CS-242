import json, ast
from edge import Edge


class Routes(Edge, object):
    def __init__(self, depa, dest, dist):
        super(Routes, self).__init__(depa, dest)
        self.dist = dist

    def info(self):
        ret = {'ports': [self.head, self.tail], 'distance': self.dist}
        return ast.literal_eval(json.dumps(ret))
