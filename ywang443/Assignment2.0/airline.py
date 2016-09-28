from metros import Metros
from routes import Routes
from graph import Graph
import json
class Airline:
    def __init__(self, filename):
        self.graph = Graph()
        json_data = open(filename)
        data = json.load(json_data)
        for item in data['metros']:
            #print item['code']
            new_city = Metros(item['code'], item['name'], item['country'], item['continent'], item['timezone'],
                                   item['coordinates'], item['population'], item['region'])
            self.graph.add_city(new_city)
        for item in data['routes']:
            new_route = Routes(item['ports'][0], item['ports'][1], item['distance'])
            self.graph.add_route(new_route)
