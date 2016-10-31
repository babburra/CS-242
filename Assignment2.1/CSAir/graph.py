import ast
import json
import operator
import webbrowser
from metros import Metros
from routes import Routes


def print_json(dic):
    for key in dic:
        print str(key) + ": " + str(dic[key])


class Graph:
    def __init__(self):
        self.metros = {}
        self.routes = {}

    #
    # @param city target city that about to be added
    # add a city to the graph
    def add_city(self, city):
        if city.population <= 0:
            print "City population cannot be negative or zero."
            return
        if city not in self.metros:
            self.metros[city.code] = city
            # print city.info()

    def edit_city(self, city, new_city):
        if city in self.metros:
            del self.metros[city.code]
            self.add_city(new_city)

    #
    # @param route target city that about to be added
    # add a route to the graph
    def add_route(self, route):
        if route.dist <= 0:
            print "Route distance can not be negative or zero."
            return
        if route.head not in self.routes:
            self.routes.setdefault(route.head, [])
        self.routes[route.head].append(route)

    #
    # @param city_code city code of target city that about to be removed
    # Given city code, remove the city and corresponding routes
    def remove_city(self, city_code):
        if city_code not in self.metros:
            print "No such city."
            return
        self.remove_both_route(city_code)
        del self.metros[city_code]

    #
    # @param route_depa depature city of the target route that about to be removed
    # Given depature city, remove corresponding routes
    def remove_both_route(self, city_code):
        for city in self.routes:
            for routes in self.routes[city]:
                if routes.head == city_code or routes.tail == city_code:
                    self.routes[city].remove(routes)
        return

    def remove_single_route(self, route_depa, route_dest):
        no_route = True
        if route_depa in self.routes:
            for routes in self.routes[route_depa]:
                if routes.head == route_depa and routes.tail == route_dest:
                    no_route = False
                    self.routes[route_depa].remove(routes)
            if no_route:
                print "There's no such route."
        else:
            print "There's no such route."
        return

    #
    # list all cities in the graph
    def list_all_cities(self):
        for city in self.metros:
            print self.metros[city].name + "(" + self.metros[city].code + ")"

    #
    # print all cities in the graph (this function is for testing purpose)
    def print_cities(self):
        for city in self.metros:
            print self.metros[city].info()

    def get_route(self, route_depa, route_dest):
        if route_depa in self.routes:
            for routes in self.routes[route_depa]:
                if routes.head == route_depa and routes.tail == route_dest:
                    return routes
        else:
            return None

    #
    # @param city city code of a city
    # get the routes of a give city
    def get_routes(self, city):
        if city not in self.routes:
            pass
        for routes in self.routes[city]:
            print_json(routes.info())

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
    # len
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
                if route.head not in connections:
                    connections[route.head] = 0
                if route.tail not in connections:
                    connections[route.tail] = 0
                connections[route.head] += 1
                connections[route.tail] += 1
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
                    ast.literal_eval(json.dumps(routes.head)) + "-" + ast.literal_eval(json.dumps(routes.tail)))
        for item in _list[:-1]:
            ret += str(item) + "%2C"
        ret += str(_list[-1]) + "&MS=wls&DU=mi"
        webbrowser.open_new(ret)
        return

    def merge_airline(self, new_airline):
        """
        merge two airlines
        :param new_airline: Airline
        :return: merged airline
        """
        for city in new_airline.graph.metros:
            self.add_city(new_airline.graph.metros[city])
        for city in new_airline.graph.routes:
            for route in new_airline.graph.routes[city]:
                self.add_route(route)
        return self

    def shortest_route(self, depa, dest):
        """
        dijkstra's alog
        :param depa: String that represents departure city
        :param dest: String that represents destination city
        :return: an array of cities that represents the final route
        """
        # vertext set
        set = []
        ret = []
        min_dist = {}
        prev = {}
        for v in self.metros:
            min_dist[v] = 9999999
            set.append(v)
        min_dist[depa] = 0
        while len(set):
            cur = None
            for u in set:
                if cur is None:
                    cur = u
                if min_dist[u] < min_dist[cur]:
                    cur = u
            set.remove(u)
            for neighbor in self.routes[u]:
                v = neighbor.tail
                alt = min_dist[u] + self.get_dist(u, v)
                if alt < min_dist[v]:
                    min_dist[v] = alt
                    prev[v] = u
        last = dest
        while last in prev:
            ret.insert(0, prev[last])
            last = prev[last]
        ret = ast.literal_eval(json.dumps(ret))
        if not ret:
            print "Sorry, we don't have a route for your search right now.\n"
            return None
        ret.append(dest)
        return ret

    def get_dist(self, depa, dest):
        """
        get distance of an edge
        :param depa: String that represents departure city
        :param dest: String that represents destination city
        :return: distance of the edge
        """
        if depa in self.routes:
            for route in self.routes[depa]:
                if route.tail == dest:
                    return route.dist
        return 0
