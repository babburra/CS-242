import json, ast
from vertex import Vertex


class Metros(Vertex, object):
    def __init__(self, code, name, country, continent, timezone, coordinates, population, region):
        super(Metros, self).__init__(name)

        self.code = code
        self.name = name
        self.country = country
        self.continent = continent
        self.timezone = timezone
        self.coordinates = coordinates
        self.population = population
        self.region = region

    def info(self):
        ret = {'code': self.code, 'name': self.name, 'country': self.country, 'continent': self.continent,
               'timezone': self.timezone, 'coordinates': self.coordinates, 'population': self.population,
               'region': self.region}
        return ast.literal_eval(json.dumps(ret))
