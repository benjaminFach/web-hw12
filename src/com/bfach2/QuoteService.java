package com.bfach2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/Quote")
public class QuoteService {

  @GET
  @Produces("text/html")
  public String getQuote(@QueryParam("hike") String hike,
      @QueryParam("month") String month,
      @QueryParam("day") String day,
      @QueryParam("year") String year,
      @QueryParam("duration") String duration,
      @QueryParam("numPeople") String numPeople) {

    QuoteController quoteController = new QuoteController();

    // make sure all params were provided
    if (hasEmptyParam(hike, month, day, year, duration, numPeople)) {
      return "<b>You must provide all parameters</b>";
    }

    // process quote request with controller, return processing results
    return quoteController.processRequest(hike, month, day, year, duration, numPeople);
  }

  private boolean hasEmptyParam(String hike, String month, String day, String year, String duration,
      String numPeople) {
    if (hike == null || hike.length() == 0) return true;
    if (month == null || month.length() == 0) return true;
    if (day == null || day.length() == 0) return true;
    if (year == null || year.length() == 0) return true;
    if (duration == null || duration.length() == 0) return true;
    if (numPeople == null || numPeople.length() == 0) return true;

    return false;
  }
}
