package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {
  private Map<String, Set<String>> countryGraph;
  private Map<String, Country> countries;

  public MapEngine() {
    // using LinkedHashMap to maintain insertion order
    countryGraph = new LinkedHashMap<>();
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

      // Initialize  the adjacency set for each country using LinkedHashSet to preserve order
      countryGraph.put(countryName, new LinkedHashSet<>());
    }
    // Parse adjacencies data
    for (String adjacencyLine : adjacencies) {
      String[] parts = adjacencyLine.split(",");
      String countryName = parts[0];

      // Skip if country doesn't exist (shouldn't happen with valid data)
      if (!countryGraph.containsKey(countryName)) {
        continue;
      }
      // Add each neighbour
      for (int i = 1; i < parts.length; i++) {
        String neighbour = parts[i];
        // Only add if neighbour exists
        if (countryGraph.containsKey(neighbour)) {
          countryGraph.get(countryName).add(neighbour);
        }
      }
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    MessageCli.INSERT_COUNTRY.printMessage();
    while (true) {
      String formattedCountry = null;
      try {
        String inputCountry = Utils.scanner.nextLine();
        formattedCountry = Utils.capitalizeFirstLetterOfEachWord(inputCountry);

        Country country = getCountry(formattedCountry);

        // Get neighbors - LinkedHashSet already preserves insertion order
        List<String> neighbours = new ArrayList<>(countryGraph.get(formattedCountry));

        MessageCli.COUNTRY_INFO.printMessage(
            country.getName(),
            country.getContinent(),
            String.valueOf(country.getFuelCost()),
            neighbours.toString());

        break;
      } catch (CountryNotFoundException e) {
        MessageCli.INVALID_COUNTRY.printMessage(formattedCountry);
      }
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
  public void showRoute() {}
}
