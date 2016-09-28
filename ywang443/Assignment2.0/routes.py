import json, ast
class Routes:
    def __init__(self, depa, dest, dist):
        self.depa = depa
        self.dest = dest
        self.dist = dist
    def info(self):
        ret = {}
        ret ['ports'] = [self.depa, self.dest]
        ret ['distance'] = self.dist
        return ast.literal_eval(json.dumps(ret))