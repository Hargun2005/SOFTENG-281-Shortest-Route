package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** This class is the main entry point. */
public class MapEngine {
  private Graph<String> countryGraph;
  private Map<String, Country> countries;

  public MapEngine() {
    // using LinkedHashMap to maintain insertion order
    countryGraph = new Graph<>();
    countries = new HashMap<>();
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {

    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();

    // Parse countries data
    for (String countryLine : countries) {
      String[] parts = countryLine.split(",");
      String countryName = parts[0];
      String continent = parts[1];
      int fuelCost = Integer.parseInt(parts[2]);

      Country country = new Country(countryName, continent, fuelCost);
      this.countries.put(countryName, country);

      // Add node to graph
      countryGraph.addNode(countryName);
    }
    // Parse adjacencies data
    for (String adjacencyLine : adjacencies) {
      String[] parts = adjacencyLine.split(",");
      String countryName = parts[0];

      // Skip if country doesn't exist (shouldn't happen with valid data)
      if (!countryGraph.containsNode(countryName)) {
        continue;
      }
      // Add each neighbour
      for (int i = 1; i < parts.length; i++) {
        String neighbour = parts[i];
        // Only add if neighbour exists
        if (countryGraph.containsNode(neighbour)) {
          countryGraph.addEdge(countryName, neighbour);
        }
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();

    String formattedCountry = getValidCountryName();

    // This shouldn't throw an exception now because getValidCountryName() ensures a valid country
    try {
      Country country = getCountry(formattedCountry);

      // Get neighbors - LinkedHashSet already preserves insertion order
      List<String> neighbours = new ArrayList<>(countryGraph.getNeighbors(formattedCountry));

      MessageCli.COUNTRY_INFO.printMessage(
          country.getName(),
          country.getContinent(),
          String.valueOf(country.getFuelCost()),
          neighbours.toString());
    } catch (CountryNotFoundException e) {
      // This should never happen, but just in case
      System.err.println("Unexpected error: " + e.getMessage());
    }
  }

  /**
   * Get the country object by name.
   *
   * @throws CountryNotFoundException if the country is not found
   */
  private Country getCountry(String countryName) throws CountryNotFoundException {
    if (!countries.containsKey(countryName)) {
      throw new CountryNotFoundException(countryName);
    }
    return countries.get(countryName);
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {
    // Ask for the starting country
    MessageCli.INSERT_SOURCE.printMessage();
    String startCountry = getValidCountryName();
    // Ask for the destination country
    MessageCli.INSERT_DESTINATION.printMessage();
    String endCountry = getValidCountryName();

    // if source and destination are the same
    if (startCountry.equals(endCountry)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
      return;
    }
    // Find the shortest path using BFS via the Graph class
    List<String> route = countryGraph.getPathBFS(startCountry, endCountry);

    // Calculate the fuel consumption
    int totalFuel = calculateFuelConsumption(route);

    Map<String, Integer> continentFuel = getContinentFuelConsumption(route);

    // Find the continent with the highest fuel consumption
    String highestFuelContinent = findHighestFuelContinent(continentFuel);

    // Display the results
    MessageCli.ROUTE_INFO.printMessage(route.toString());
    MessageCli.FUEL_INFO.printMessage(String.valueOf(totalFuel));
    MessageCli.CONTINENT_INFO.printMessage(formatContinentFuel(continentFuel));

    // Only print if there are intermediate countries (fuel > 0)
    if (totalFuel > 0) {
      MessageCli.FUEL_CONTINENT_INFO.printMessage(
          highestFuelContinent + " (" + continentFuel.get(highestFuelContinent) + ")");
    }
  }

  /**
   * Gets a valid country name from user input, prompting until valid. Reuses code from Task 1 for
   * validation.
   */
  private String getValidCountryName() {
    while (true) {
      String formattedCountry = null;
      try {
        String inputCountry = Utils.scanner.nextLine();
        formattedCountry = Utils.capitalizeFirstLetterOfEachWord(inputCountry);

        // this will throw an exception when the country is not found
        getCountry(formattedCountry);

        return formattedCountry;
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(formattedCountry);
      }
    }
  }

  private Map<String, Integer> getContinentFuelConsumption(List<String> route) {
    Map<String, Integer> continentFuel = new LinkedHashMap<>();

    for (int i = 0; i < route.size(); i++) {
      String country = route.get(i);
      String continent = countries.get(country).getContinent();

      // Add continent if not already in the map
      if (!continentFuel.containsKey(continent)) {
        continentFuel.put(continent, 0);
      }
      // Add fuel for intermediate countries only
      if (i > 0 && i < route.size() - 1) {
        int currentFuel = continentFuel.get(continent);
        continentFuel.put(continent, currentFuel + countries.get(country).getFuelCost());
      }
    }
    return continentFuel;
  }

  private String findHighestFuelContinent(Map<String, Integer> continentFuel) {
    String highestContinent = null;
    int highestFuel = -1;

    for (Map.Entry<String, Integer> entry : continentFuel.entrySet()) {
      if (entry.getValue() > highestFuel) {
        highestFuel = entry.getValue();
        highestContinent = entry.getKey();
      }
    }
    return highestContinent;
  }

  private int calculateFuelConsumption(List<String> route) {
    // Same implementation as before
    int totalFuel = 0;

    // Skip the first and last country
    for (int i = 1; i < route.size() - 1; i++) {
      totalFuel += countries.get(route.get(i)).getFuelCost();
    }

    return totalFuel;
  }

  private String formatContinentFuel(Map<String, Integer> continentFuel) {
    List<String> result = new ArrayList<>();
    for (Map.Entry<String, Integer> entry : continentFuel.entrySet()) {
      result.add(entry.getKey() + " " + "(" + entry.getValue() + ")");
    }
    return result.toString();
  }
}
