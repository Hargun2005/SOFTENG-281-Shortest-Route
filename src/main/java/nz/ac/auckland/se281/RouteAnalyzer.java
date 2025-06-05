package nz.ac.auckland.se281;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Handles analysis of routes for the Risk game. */
public class RouteAnalyzer {
  private Map<String, Country> countries;

  public RouteAnalyzer(Map<String, Country> countries) {
    this.countries = countries;
  }

  /**
   * Calculates the total fuel consumption for the journey. Excludes the source and destination
   * countries.
   */
  public int calculateFuelConsumption(List<String> route) {
    int totalFuel = 0;

    // Skip the first and last country
    for (int i = 1; i < route.size() - 1; i++) {
      totalFuel += countries.get(route.get(i)).getFuelCost();
    }

    return totalFuel;
  }

  /**
   * Gets the fuel consumption per continent. Includes the continent of source and destination but
   * excludes their fuel.
   */
  public Map<String, Integer> getContinentFuelConsumption(List<String> route) {
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

  /**
   * Finds the continent with the highest fuel consumption.
   *
   * @param continentFuel A map of continents to their fuel consumption values
   * @return The name of the continent with the highest fuel consumption. If multiple continents
   *     have the same highest value, returns the first one encountered in the map (which preserves
   *     insertion order since we use LinkedHashMap).
   */
  public String findHighestFuelContinent(Map<String, Integer> continentFuel) {
    String highestContinent = null;
    int highestFuel = -1;

    // Iterate through the continents and their fuel values
    // We use strict inequality (>) rather than (>=) to ensure we select
    // the first continent with the highest value in case of ties

    for (Map.Entry<String, Integer> entry : continentFuel.entrySet()) {
      if (entry.getValue() > highestFuel) {
        highestFuel = entry.getValue();
        highestContinent = entry.getKey();
      }
    }

    return highestContinent;
  }

  /** Formats the continent fuel map into a string representation. */
  public String formatContinentFuel(Map<String, Integer> continentFuel) {
    StringBuilder result = new StringBuilder("[");
    boolean first = true;

    for (Map.Entry<String, Integer> entry : continentFuel.entrySet()) {
      if (!first) {
        result.append(", ");
      } else {
        first = false;
      }
      result.append(entry.getKey()).append(" (").append(entry.getValue()).append(")");
    }

    result.append("]");
    return result.toString();
  }
}
