import unittest

from CSAir.airline import Airline

CSAirline = Airline('test_data.json')


class airline_tests(unittest.TestCase):
    def test_longest_edge(self):
        self.assertEqual(CSAirline.graph.longest_edge()['distance'], 12051)

    def test_shortest_edge(self):
        self.assertEqual(CSAirline.graph.shortest_edge()['distance'], 334)

    def test_average_distance(self):
        self.assertEqual(round(CSAirline.graph.average_distance(), 2), 2300.28)

    def test_biggest_city(self):
        self.assertEqual(CSAirline.graph.biggest_city()['code'], 'TYO')

    def test_smallest_city(self):
        self.assertEqual(CSAirline.graph.smallest_city()['code'], 'ESS')

    def test_average_citysize(self):
        self.assertEqual(CSAirline.graph.average_citysize(), 11796143.75)

    def test_hub_cities(self):
        self.assertEqual(CSAirline.graph.find_hub_city(3), [('IST', 12), ('HKG', 12), ('PAR', 10)])

#    def test_merge_file(self):
#        airline = Airline('test_data1.json')
#        new_airline = Airline('test_data2.json')
#        final_airline = Airline('test_data_final.json')
#        merged_airline = airline.graph.merge_airline(new_airline)
#        self.assertDictEqual(merged_airline.metros, final_airline.graph.metros)

    def test_shortest_route(self):
        self.assertEqual(CSAirline.graph.shortest_route('SCL', 'LIM'), ['SCL', 'LIM'])
        self.assertEqual(CSAirline.graph.shortest_route('SCL', 'MEX'), ['SCL', 'LIM', 'MEX'])
        self.assertEqual(CSAirline.graph.shortest_route('SAO', 'KRT'), ['SAO', 'LOS', 'KRT'])

# def test_add_city(self, city):
#        CSAirline.graph.add_city(city)
#        self.assertEqual(CSAirline.graph.metros[city.code], city)

if __name__ == '__main__':
    unittest.main()
