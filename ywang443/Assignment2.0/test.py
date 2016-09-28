from metros import Metros
from graph import Graph
from routes import Routes
from airline import Airline


# CSAirline.graph.print_cities()
# CSAirline.graph.print_routes()

def test_longest_edge():
    print CSAirline.graph.longest_edge()


def test_shortest_edge():
    print CSAirline.graph.shortest_edge()
    return


def test_average_distance():
    print CSAirline.graph.average_distance()
    return


def test_biggest_city():
    print CSAirline.graph.biggest_city()
    return


def test_smallest_city():
    print CSAirline.graph.smallest_city()
    return


def test_average_citysize():
    print CSAirline.graph.average_citysize()
    return


def test_list_continents():
    print CSAirline.graph.list_continents()
    return


def test_hub_cities():
    print CSAirline.graph.find_hub_city(3)
    return


def test_launch_map():
    CSAirline.graph.launch_map()
    return


if __name__ == "__main__":
    SCL = Metros('SCL', 'Santiago', 'CL', 'South America', -4, {"S": 33, "W": 71}, 6000000, 1)
    LIM = Metros('LIM', 'Lima', 'PE', 'South America', -5, {"S": 12, "W": 77}, 9050000, 1)
    route1 = Routes('SCL', 'LIM', 2453)
    graph = Graph()
    graph.add_city(SCL)
    graph.add_city(LIM)
    graph.add_route(route1)
    print SCL.info()
    graph.print_cities()
    graph.remove_city('SCL')
    print "Removing SCL"
    graph.print_cities()

    graph.add_city(SCL)
    CSAirline = Airline('test_data.json')
    test_longest_edge()
    test_shortest_edge()
    test_average_distance()
    test_biggest_city()
    test_smallest_city()
    test_average_citysize()
    test_list_continents()
    test_hub_cities()
    test_launch_map()
