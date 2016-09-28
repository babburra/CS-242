import json, ast
class Metros:
    def __init__(self, code, name, country, continent, timezone, coordinates, population, region):
        self.code = code
        self.name = name
        self.country = country
        self.continent = continent
        self.timezone = timezone
        self.coordinates = coordinates
        self.population = population
        self.region = region

    def info(self):
        ret = {}
        ret['code'] = self.code
        ret['name'] = self.name
        ret['country'] = self.country
        ret['continent'] = self.continent
        ret['timezone'] = self.timezone
        ret['coordinates'] = self.coordinates
        ret['population'] = self.population
        ret['region'] = self.region
        return ast.literal_eval(json.dumps(ret))