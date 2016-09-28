import ast
import json
import operator
import webbrowser

from routes import Routes


class Graph:
    def __init__(self):
        self.metros = {}
        self.routes = {}

    #
    # @param city target city that about to be added
    # add a city to the graph
    def add_city(self, city):
        if city not in self.metros:
            self.metros[city.code] = city
            # print city.info()

    #
    # @param route target city that about to be added
    # add a route to the graph
    def add_route(self, route):
        if route.depa not in self.routes:
            self.routes.setdefault(route.depa, [])
        self.routes[route.depa].append(route)

    #
    # @param city_code city code of target city that about to be removed
    # Given city code, remove the city and corresponding routes
    def remove_city(self, city_code):
        self.metros.pop(city_code, None)
        self.remove_route(city_code)

    #
    # @param route_depa depature city of the target route that about to be removed
    # Given depature city, remove corresponding routes
    def remove_route(self, route_depa):
        self.routes.pop(route_depa, None)

    #
    # list all cities in the graph
    def list_all_cities(self):
        for city in self.metros:
            print self.metros[city].name

    #
    # print all cities in the graph (this function is for testing purpose)
    def print_cities(self):
        for city in self.metros:
            print self.metros[city].info()

    #
    # @param city city code of a city
    # get the routes of a give city
    def get_routes(self, city):
        if city not in self.routes:
            pass
        for routes in self.routes[city]:
            print routes.info()

    #
    # @return return the longest flight in the network
    def longest_edge(self):
        ret = Routes("", "", 0)
        for city in self.routes:
            for route in self.routes[city]:
                if route.dist > ret.dist:
                    ret = route
        return ret.info()

    #
    # @return return the shortest flight in the network
    def shortest_edge(self):
        ret = Routes("", "", 100000)
        for city in self.routes:
            for route in self.routes[city]:
                if route.dist < ret.dist:
                    ret = route
        return ret.info()

    #
    # @return return the average distance in the network
    def average_distance(self):
        sum_dist = 0.0
        count = 0
        for city in self.routes:
            for route in self.routes[city]:
                sum_dist += route.dist
                count += 1
        return sum_dist / count

    #
    # @return return biggest city (by population) served by CSAir
    def biggest_city(self):
        max_population = 0
        ret = ""
        for city in self.metros:
            if self.metros[city].population > max_population:
                max_population = self.metros[city].population
                ret = city
        return self.metros[ret].info()

    #
    # @return return the smallest city (by population) served by CSAir
    def smallest_city(self):
        min_population = 0
        ret = ""
        for city in self.metros:
            if min_population == 0:
                min_population = self.metros[city].population
            elif self.metros[city].population < min_population:
                min_population = self.metros[city].population
                ret = city
        return self.metros[ret].info()

    #
    # @return return the average size (by population) of all the cities served by CSAir
    def average_citysize(self):
        sum_population = 0.0
        count = 0
        for city in self.metros:
            sum_population += self.metros[city].population
            count += 1
        return sum_population / count

    #
    # @return return a list of the continents served by CSAir and which cities are in them
    def list_continents(self):
        ret = {}
        for city in self.metros:
            if self.metros[city].continent not in ret:
                ret.setdefault(self.metros[city].continent, [])
            ret[self.metros[city].continent].append(city)
        return ast.literal_eval(json.dumps(ret))

    #
    # @param n integer from 0 to len(city)
    # @return top n hub cities
    def find_hub_city(self, n):
        connections = {}
        for city in self.routes:
            for route in self.routes[city]:
                if route.depa not in connections:
                    connections[route.depa] = 0
                if route.dest not in connections:
                    connections[route.dest] = 0
                connections[route.depa] += 1
                connections[route.dest] += 1
        connections = ast.literal_eval(json.dumps(connections))
        ordered = sorted(connections.items(), key=operator.itemgetter(1), reverse=True)
        if n <= 0 or n > len(ordered):
            print "Invalid input."
            return
        hub_cities = ordered[:n]
        return hub_cities

    #
    # launch the map of the network
    def launch_map(self):
        ret = "http://www.gcmap.com/mapui?P="
        _list = []
        for city in self.routes:
            for routes in self.routes[city]:
                _list.append(
                    ast.literal_eval(json.dumps(routes.depa)) + "-" + ast.literal_eval(json.dumps(routes.dest)))
        for item in _list[:-1]:
            ret += str(item) + "%2C"
        ret += str(_list[-1]) + "&MS=wls&DU=mi"
        webbrowser.open_new(ret)
        return
