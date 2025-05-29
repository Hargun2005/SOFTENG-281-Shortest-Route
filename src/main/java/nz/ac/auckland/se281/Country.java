package nz.ac.auckland.se281;

/**
 * Represents a country in the Risk game with its properties. Each country belongs to a continent
 * and has an associated fuel cost for traveling through it.
 */
public class Country {
  private String name;
  private String continent;
  private int fuelCost;

  /**
   * Creates a new Country with the specified properties.
   *
   * @param name The name of the country
   * @param continent The continent where the country is located
   * @param fuelCost The fuel cost for traveling through this country
   */
  public Country(String name, String continent, int fuelCost) {
    this.name = name;
    this.continent = continent;
    this.fuelCost = fuelCost;
  }

  /**
   * Gets the name of the country.
   *
   * @return The country name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the continent where this country is located.
   *
   * @return The continent name
   */
  public String getContinent() {
    return continent;
  }

  /**
   * Gets the fuel cost for traveling through this country.
   *
   * @return The fuel cost as an integer
   */
  public int getFuelCost() {
    return fuelCost;
  }
}
