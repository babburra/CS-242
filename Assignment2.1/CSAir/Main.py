import sys
import os.path
import json
from metros import Metros
from routes import Routes
from airline import Airline


def query(al):
    """
    main menu for user to query data
    :param al: airline
    :return: void
    """
    user_input = raw_input('1. List all cities\n'
                           '2. Get information about a city\n'
                           '3. Get information about a route\n'
                           '4. Statistical information\n'
                           '5. Edit the route network\n'
                           '6. Get shortest route\n'
                           '7. Get map\n'
                           '8. Import another file.\n'
                           '9. Quit or press \'q\'\n')
    if user_input == 'q' or user_input == '9':
        save_n_quit(al)
    elif user_input == '1':
        al.graph.list_all_cities()
        query(al)
    elif user_input == '2':
        get_city_info(al)
        query(al)
    elif user_input == '3':
        get_route_info(al)
        query(al)
    elif user_input == '4':
        get_stats(al)
        query(al)
    elif user_input == '5':
        edit_network(al)
        query(al)
    elif user_input == '6':
        print get_shortest_route(al)
        query(al)
    elif user_input == '7':
        al.graph.launch_map()
        query(al)
    elif user_input == '8':
        new_airline = import_file()
        al.graph.merge_airline(new_airline)
        query(al)
    else:
        print("Invalid input, please try again.")
        query(al)


def get_city_info(al):
    """
    print information of a city
    :param al: airline
    :return: void
    """
    city_input = raw_input("Please specify the code of the city.\n "
                           "Press 'q' to quit.\n "
                           "Press 'r' to return to previous menu\n")
    if city_input == 'q':
        save_n_quit(al)
    if city_input == 'r':
        return
    if city_input not in al.graph.metros:
        print "Invalid City. Please try again:\n"
        get_city_info(al)
        return
    print_json(al.graph.metros[city_input].info())
    if city_input in al.graph.routes:
        al.graph.get_routes(city_input)
    get_city_info(al)
    return


def get_stats(al):
    """
    menu for user to get statistics about an airline
    :param al: airline
    :return: void
    """
    user_input = raw_input("1. Longest single flight\n"
                           "2. Shortest single flight\n"
                           "3. Average distance\n"
                           "4. Biggest city\n"
                           "5. Smallest city\n"
                           "6. Average city size\n"
                           "7. List continents\n"
                           "8. Hub cities\n"
                           "9. Quit or press 'q'\n"
                           "Press 'r' to return to previous menu\n")
    if user_input == 'r':
        return
    elif user_input == 'q' or user_input == '9':
        save_n_quit(al)
    elif user_input == '1':
        print_json(al.graph.longest_edge())
        get_stats(al)
    elif user_input == '2':
        print_json(al.graph.shortest_edge())
        get_stats(al)
    elif user_input == '3':
        print al.graph.average_distance()
        get_stats(al)
    elif user_input == '4':
        print_json(al.graph.biggest_city())
        get_stats(al)
    elif user_input == '5':
        print_json(al.graph.smallest_city())
        get_stats(al)
    elif user_input == '6':
        print al.graph.average_citysize()
        get_stats(al)
    elif user_input == '7':
        print_json(al.graph.list_continents())
        get_stats(al)
    elif user_input == '8':
        get_hub_input(al)
        get_stats(al)
    else:
        print("Invalid input, please try again.")
        get_stats(al)


def get_hub_input(al):
    """
    get and print the top n hub cities in a network
    :param al: airline
    :return: void
    """
    hub_input = raw_input("Top _ cities? (input must be an integer)")
    try:
        hub_input = int(hub_input)
        hub_cities = al.graph.find_hub_city(hub_input)
        if hub_cities is not None:
            print hub_cities
        return
    except ValueError:
        print hub_input, "is not an integer!"
        get_hub_input(al)
        return


def edit_network(al):
    """
    menu for user to edit network
    :param al: airline
    :return: void
    """
    user_input = raw_input("1. Remove a city\n"
                           "2. Remove a route\n"
                           "3. Add a city\n"
                           "4. Add a route\n"
                           "5. Edit an existing city\n"
                           "Press 'r' to return to previous menu\n"
                           "Press 'q' to quit")
    if user_input == 'r':
        return
    elif user_input == 'q':
        save_n_quit(al)
    elif user_input == '1':
        input_city = raw_input("City code?\n")
        al.graph.remove_city(input_city)
        edit_network(al)
    elif user_input == '2':
        route_depa = raw_input("Departure city code:\n")
        route_dest = raw_input("Destination city code:\n")
        if route_depa == route_dest:
            print "Departure city and destination city cannot be the same!"
        else:
            al.graph.remove_single_route(route_depa, route_dest)
        edit_network(al)
    elif user_input == '3':
        add_city(al)
        edit_network(al)
    elif user_input == '4':
        route_depa = raw_input("Departure city code:\n")
        route_dest = raw_input("Destination city code:\n")
        while route_depa == route_dest:
            print "Departure city and destination city cannot be the same!"
            route_depa = raw_input("Departure city code:\n")
            route_dest = raw_input("Destination city code:\n")
        # if the route already exist
        if route_depa in al.graph.routes and route_dest in al.graph.routes[route_depa]:
            print "Route already exist!\n"
            edit_network(al)
            return
        route_dist = raw_input("Route distance:\n")
        while route_dist <= 0:
            print "Route cannot be negative or zero. Please try again.\n"
            route_dist = raw_input("Route distance:\n")
        new_route = Routes(route_depa, route_dest, route_dist)
        al.graph.add_route(new_route)
        edit_network(al)
    elif user_input == '5':
        input_city = raw_input("City code of the city you want to edit:\n")
        while input_city not in al.graph.metros:
            print "Given city doesn't exist! Please try again:\n"
            input_city = raw_input("City code of the city you want to edit:\n")
        input_property = raw_input(
            "Which property you want to change? (code/name/country/continent/timezone/coordinates/population)")
        if input_property in al.graph.metros[input_city]:
            new_data = raw_input("Change to:\n")
            if input_property == 'population' and new_data <= 0:
                print "Population cannot be negative or zero!\n"
            else:
                al.graph.metros[input_city][input_property] = new_data
                edit_network(al)


def add_city(al):
    """
    add city to network
    :param al: airline
    :return: void
    """
    new_code = raw_input("City code:\n")
    new_name = raw_input("City name:\n")
    new_country = raw_input("Country:\n")
    new_continent = raw_input("Continent:\n")
    new_timezone = raw_input("Timezone:\n")
    new_coordinates = raw_input("Coordinates:\n")
    new_population = raw_input("Population:\n")
    while new_population <= 0:
        print "Population cannot be negative or zero. Please try again.\n"
        new_population = raw_input("Population:\n")
    new_region = raw_input("Region:\n")
    new_city = Metros(new_code, new_name, new_country, new_continent, new_timezone, new_coordinates, new_population,
                      new_region)
    al.graph.add_city(new_city)


def import_file():
    """
    import another json file if needed
    :return: the new airline after merge
    """
    filename = raw_input("Input filename:\n"
                         "Press 'q' to quit.\n ")
    if filename == 'q':
        sys.exit(0)
    elif not os.path.isfile(filename):
        print "Unable to open file."
        return False
    cs_airline = Airline(filename)
    return cs_airline


def export_file(al):
    """
    export current airline
    :param al: airline
    :return: void
    """
    metros = []
    routes = []
    for city in al.graph.metros:
        metros.append(al.graph.metros[city].info())
    for city in al.graph.routes:
        for route in al.graph.routes[city]:
            routes.append(route.info())
    data = {"metros": metros, "routes": routes}
    json.dump(data, open('data.json', "w"), indent=4)


def save_n_quit(al):
    """
    save and quit the program
    :param al: airline
    :return: void
    """
    export_file(al)
    print "Thank you for choosing CSAir!"
    sys.exit(0)


def get_shortest_route(al):
    """
    get the shortest route
    :param al: airline
    :return: the shortest route or none
    """
    route_depa = raw_input("Departure city code:\n")
    route_dest = raw_input("Destination city code:\n")
    if route_depa not in al.graph.metros or route_dest not in al.graph.metros:
        print "No such route.\n"
    route = al.graph.shortest_route(route_depa, route_dest)
    return route


def get_route_info(al):
    """
    get route information
    :param al: airline
    :return: void
    """
    route = get_shortest_route(al)
    if route:
        print "Total distance: " + str(get_total_dist(al, route)) + "km"
        print "Total cost: $" + str(get_cost(al, route))
        print "Total time: " + str(travel_time(al, route)) + "hr(s)"


def get_total_dist(al, route):
    """
    helper function, find total distance of a route
    :param al: airline
    :param route: target route
    :return: distance of the route
    """
    distance = 0
    for i in range(0, len(route) - 1):
        distance += al.graph.get_dist(route[i], route[i + 1])
    return distance


def get_cost(al, route):
    """
    helper function, find total cost of a route
    :param al: airline
    :param route: target route
    :return: the cost of a route
    """
    total_cost = 0
    for i in range(0, len(route) - 1):
        cost = 0.35 - 0.05 * i
        if cost <= 0:
            cost = 0
        total_cost += cost * al.graph.get_dist(route[i], route[i + 1])
    return total_cost


def travel_time(al, route):
    """
    helper function, find the travel time for a route
    :param al: Airline
    :param route: Route
    :return: travel time for a route
    """
    layover_time = 0.0
    time = 0.0
    if len(route) <= 2:
        layover_time = 0
    else:
        for i in range(1, len(route) - 1):
            edges = al.graph.routes[route[i]]
            num_out_bound = len(edges)
            layover = (2 - num_out_bound / 6)
            if layover < 0:
                layover = 0
            layover_time += layover
    for i in range(0, len(route) - 1):
        distance = al.graph.get_dist(route[i], route[i + 1])
        if distance < 400:
            time = distance / 375
        else:
            time = 2 * 200 / 375 + (distance - 400) / 750
    return time + layover_time


def print_json(dic):
    """
    helper function, print dictionary with c: and \n
    :param dic: Dictionary
    :return: void
    """
    for key in dic:
        print str(key) + ": " + str(dic[key])


if __name__ == "__main__":
    airline = import_file()
    while airline is False:
        airline = import_file()
    query(airline)
