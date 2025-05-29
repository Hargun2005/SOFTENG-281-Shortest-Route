package nz.ac.auckland.se281;

/**
 * Custom exception thrown when a requested country cannot be found. This exception is used to
 * handle invalid country names entered by the user.
 */
public class CountryNotFoundException extends Exception {

  private String countryName;

  /**
   * Constructs a new exception with a message indicating that the country was not found.
   *
   * @param countryName The name of the country that could not be found
   */
  public CountryNotFoundException(String countryName) {
    super(MessageCli.INVALID_COUNTRY.getMessage(countryName));
    this.countryName = countryName;
  }

  /**
   * Gets the name of the country that was not found.
   *
   * @return The country name
   */
  public String getCountryName() {
    return countryName;
  }
}
