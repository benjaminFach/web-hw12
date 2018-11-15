package com.bfach2;

import com.bfach2.utils.BookingDay;
import com.bfach2.utils.Rates;

import java.time.LocalDate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Ben Fach
 */
public class QuoteController {

  // Constants
  private static final String[] HIKES = {"BEATEN", "GARDINER", "HELLROARING"};
  private static final int START_YEAR = 2018;
  private static final int END_YEAR = 2025;

  // minimum number of people required for a hike
  private static final int MIN_HIKERS = 1;

  // maximum number of people allowed on a hike
  private static final int MAX_HIKERS = 10;

  // map durations to hikes
  private static final HashMap<String, List<Integer>> HIKES_TO_DURATIONS;

  static {
    HIKES_TO_DURATIONS = new HashMap<String, List<Integer>>();
    HIKES_TO_DURATIONS.put("BEATEN", Stream.of(5, 7).collect(Collectors.toList()));
    HIKES_TO_DURATIONS.put("GARDINER", Stream.of(3, 5).collect(Collectors.toList()));
    HIKES_TO_DURATIONS.put("HELLROARING", Stream.of(2, 3, 4).collect(Collectors.toList()));
  }

  // Class attributes
  private Quote quote;

  // Constructor
  public QuoteController() {
    this.quote = new Quote();
  }

  protected String processRequest(String trail, String month, String day, String year,
      String duration, String numPeople) {
    if (trail != null) {
      trail = trail.toUpperCase();
    }

    // attempt to create a hike from the provided values
    // if request is invalid, display message to user
    // otherwise, make the calculation and display
    if (!isValidRequest(trail, month, day, year, duration, numPeople)) {
      return quote.getErrorMessage();
    } else {
      // create the booking day
      BookingDay bookingDay = new BookingDay(Integer.parseInt(year), Integer.parseInt(month),
          Integer.parseInt(day));

      // create the HIKE enum, need for the rate calculation
      Routes route;
      if (trail.toUpperCase().equals("BEATEN")) {
        route = Routes.BEATEN;
      } else if (trail.toUpperCase().equals("GARDINER")) {
        route = Routes.GARDINER;
      } else {
        route = Routes.HELLROARING;
      }

      // create object for calculating rate, calibrated by hike chosen
      int durationInt = Integer.parseInt(duration);
      int yearInt = Integer.parseInt(year);
      int monthInt = Integer.parseInt(month);
      int dayInt = Integer.parseInt(day);
      int numPeopleInt = Integer.parseInt(numPeople);

      Rates rater = new Rates(route.asHike());
      rater.setBeginDate(bookingDay);
      rater.setDuration(durationInt);
      if (rater.isValidDates()) {
        // may not be creating start date correctly
        double unitCost = rater.getCost();
        ChosenHike hike = new ChosenHike(route, durationInt,
            LocalDate.of(yearInt, monthInt, dayInt));

        // build quote object
        quote = new Quote(hike, unitCost, numPeopleInt);
        return quote.toString();
      } else {
        return
            "Sorry, the dates you chose were out of season. Please choose between June 1 and Sept 30.";
      }
    }
  }

  /**
   * Exhaustive validation check for
   *
   * @param trail the hiking trail
   * @param month the month of the hike (numeric)
   * @param day the day of the hike
   * @param year the year of the hike
   * @param duration the number of days the hike will take
   * @param numPeople the number of people participating in the hike
   * @return true if can provide a quote, false otherwise
   */
  private boolean isValidRequest(String trail, String month, String day, String year,
      String duration, String numPeople) {
    // make sure the trail is a valid trail
    if (!isValidTrail(trail)) {
      return false;
    } else if (!isValidMonth(month)) {
      return false;
    } else if (!isValidDay(day)) {
      return false;
    } else if (!isValidYear(year)) {
      return false;
    } else if (!isValidDuration(trail, duration)) {
      return false;
    } else if (!isValidNumPeople(numPeople)) {
      return false;
    }
    return true;
  }

  /**
   * Checks if the requested hiking trail is offered
   *
   * @param trail the hiking trail
   * @return if the hiking trail is offered, false otherwise
   */
  private boolean isValidTrail(String trail) {
    if (!Arrays.asList(HIKES).contains(trail)) {
      quote.updateErrorMessage("You chose an invalid hiking route");
      return false;
    }

    return true;
  }

  /**
   * Checks if the month is valid
   *
   * @param month the month (numeric)
   * @return true if number represents a valid month, false otherwise
   */
  private boolean isValidMonth(String month) {
    int monthInt = 0;
    try {
      monthInt = Integer.parseInt(month);
    } catch (NumberFormatException ex) {
      quote.updateErrorMessage("couldn't process month");
      return false;
    }
    // check if value is between 1 and 12
    if (monthInt <= 0 || monthInt > 12) {
      quote.updateErrorMessage("provide a month between 1 and 12");
      return false;
    } else {
      return true;
    }
  }

  /**
   * Checks if the day is valid
   *
   * @param day the day
   * @return true is number represents a valid day, false otherwise
   */
  private boolean isValidDay(String day) {
    int dayInt = 0;
    try {
      dayInt = Integer.parseInt(day);
    } catch (NumberFormatException ex) {
      quote.updateErrorMessage("couldn't process day");
      return false;
    }
    // check if value is less than 1 or greater than 31
    if (dayInt <= 0) {
      quote.updateErrorMessage("provide a positive value greater than 0, less than 31 for the day");
      return false;
    } else {
      return true;
    }
  }

  /**
   * Check if the year is within our booking limits
   *
   * @param year the year
   * @return true if within booking range, false otherwise
   */
  private boolean isValidYear(String year) {
    int yearInt = 0;
    try {
      yearInt = Integer.parseInt(year);
    } catch (NumberFormatException ex) {
      quote.updateErrorMessage("couldn't process year");
      return false;
    }
    // check if value is within our allowed years
    if (yearInt < START_YEAR || yearInt > END_YEAR) {
      quote.updateErrorMessage("choose a year between " + START_YEAR + " and " + END_YEAR);
      return false;
    } else {
      return true;
    }
  }

  /**
   * Verifies that the hike chosen has an offering of the chosen duration
   *
   * @param hike the hike
   * @param duration the duration in days
   * @return true if duration is offered for hike, false otherwise
   */
  private boolean isValidDuration(String hike, String duration) {
    int durationInt = 0;
    try {
      durationInt = Integer.parseInt(duration);
    } catch (NumberFormatException ex) {
      quote.updateErrorMessage("couldn't process duration");
      return false;
    }
    if (!isValidTrail(hike)) {
      quote.updateErrorMessage("we don't offer that hike.");
      return false;
    } else if (!HIKES_TO_DURATIONS.get(hike).contains(durationInt)) {
      StringBuilder errorBuilder = new StringBuilder();
      errorBuilder.append("We don't offer that duration for " + hike);
      errorBuilder.append(". Choose one of the following: ");
      errorBuilder.append(HIKES_TO_DURATIONS.get(hike).stream().map(Object::toString)
          .collect(Collectors.joining(", ")));
      quote.updateErrorMessage(errorBuilder.toString());

      return false;
    }

    return true;
  }

  /**
   * Verifies that the number of people attending the hike is an acceptable amount
   *
   * @param numPeople the number of people attending the hike
   * @return true if number of hikers is allowed, false otherwise
   */
  private boolean isValidNumPeople(String numPeople) {
    int numPeopleInt = 0;

    try {
      numPeopleInt = Integer.parseInt(numPeople);
    } catch (NumberFormatException ex) {
      quote.updateErrorMessage("couldn't process number of people... " + numPeople);
      return false;
    }

    // make sure number is in range
    if (numPeopleInt < MIN_HIKERS || numPeopleInt > MAX_HIKERS) {
      quote.updateErrorMessage("you must choose at least" + MIN_HIKERS + " and at most "
          + MAX_HIKERS + " hikers.");
      return false;
    }

    return true;
  }
}
