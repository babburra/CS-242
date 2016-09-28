import sys
from airline import Airline


#
# main menu for user to query data
def query(airline):
    user_input = raw_input("1. List all cities\n"
                           "2. Get information about a city\n"
                           "3. Statistical information\n"
                           "4. Get map\n"
                           "5. Quit or press 'q'\n")
    if user_input == 'q' or user_input == '5':
        sys.exit(0)
    elif user_input == '1':
        airline.graph.list_all_cities()
        query(airline)
    elif user_input == '2':
        get_city_info()
        query(airline)
    elif user_input == '3':
        get_stats(airline)
        query(airline)
    elif user_input == '4':
        airline.graph.launch_map()
        query(airline)
    else:
        print("Invalid input, please try again.")
        query(airline)


#
# print the information of a city
def get_city_info():
    city_input = raw_input("Please specify the code of the city.\n "
                           "Press 'q' to quit.\n "
                           "Press 'r' to return to previous menu\n")
    if city_input == 'q':
        sys.exit(0)
    if city_input == 'r':
        return
    if city_input not in CSAirline.graph.metros:
        print "Invalid City. Please try again:\n"
        get_city_info()
        return
    print CSAirline.graph.metros[city_input].info()
    CSAirline.graph.get_routes(city_input)
    get_city_info()
    return


#
# @param airline
# menu for user to get statistics about an airline
def get_stats(airline):
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
        sys.exit(0)
    elif user_input == '1':
        print airline.graph.longest_edge()
        get_stats(airline)
    elif user_input == '2':
        print airline.graph.shortest_edge()
        get_stats(airline)
    elif user_input == '3':
        print airline.graph.average_distance()
        get_stats(airline)
    elif user_input == '4':
        print airline.graph.biggest_city()
        get_stats(airline)
    elif user_input == '5':
        print airline.graph.smallest_city()
        get_stats(airline)
    elif user_input == '6':
        print airline.graph.average_citysize()
        get_stats(airline)
    elif user_input == '7':
        print airline.graph.list_continents()
        get_stats(airline)
    elif user_input == '8':
        get_hub_input(airline)
        get_stats(airline)
    else:
        print("Invalid input, please try again.")
        get_stats(airline)


#
# @param airline
# get and print the top n hub cities in a network
def get_hub_input(airline):
    hub_input = raw_input("Top _ cities? (input must be an integer)")
    try:
        hub_input = int(hub_input)
        hub_cities = airline.graph.find_hub_city(hub_input)
        if hub_cities is not None:
            print hub_cities
        return
    except ValueError:
        print hub_input, "is not an integer!"
        get_hub_input(airline)
        return


if __name__ == "__main__":
    filename = "test_data.json"
    CSAirline = Airline(filename)
    query(CSAirline)
