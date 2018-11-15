<%-- 
    Document   : beartooh-hiking-homepage
    Created on : Nov 1, 2018, 3:47:46 PM
    Author     : Ben
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
            integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
            crossorigin="anonymous">
    </script>
    <script src="scripts/moment.js"></script>
    <script src="scripts/hikingQuote.js"></script>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
          crossorigin="anonymous">
    <link rel="stylesheet"
          type="text/css"
          href="stylesheets/beartooth.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Beartooth Hiking Company Home Page</title>
</head>
<body>

<main role="main" class="container-fluid">
    <div class="row">
        <div class="col">
            <p class="main-header"><img src="images/beartooth-header.jpg" alt="Mountain peaks in the wilderness" class="main-header-image"></p>
        </div>
    </div>

    <div class="row">
        <div class="col">
            <p class="main-header">Beartooth Hiking Company</p>
            <p class="slogan">Getting you connected to Mother Nature.</p>
        </div>
    </div>
    <div class="row mb-3 mt-5">
        <div class="col">
            <p class="section-header">Tours</p>
        </div>
    </div>

    <div class="row">
        <div class="col-sm-2">
        </div>

        <div class="col-sm-4 quote-form">
            <h4>Generate a quote for our amazing hiking adventures!</h4>

            <form onsubmit="event.preventDefault(); return processQuoteRequest()" id="quoteForm">

                <div>
                    <label for="date">Choose a date:</label>
                    <input type="date" class="form-control" id="date">
                </div>
                <div>
                    <label for="people">Specify the number of hikers:</label>
                    <input type="number" class="form-control" id="people">
                </div>
                <div>
                    <label for="hike">Choose a hike:</label>
                    <div id="hike">
                        <input type="radio" name="hike" value="GARDINER">Gardiner Lake</input> <br>
                        <input type="radio" name="hike" value="HELLROARING">The Hellroaring Plateau</input> <br>
                        <input type="radio" name="hike" value="BEATEN">The Beaten Path</input>
                    </div>
                </div>
                <div>
                    <label for="duration">Choose a duration:</label>
                    <div id="duration">
                        <input type="radio" name="duration" value="2">2 days</input> <br>
                        <input type="radio" name="duration" value="3">3 days</input> <br>
                        <input type="radio" name="duration" value="4">4 days</input> <br>
                        <input type="radio" name="duration" value="5">5 days</input> <br>
                        <input type="radio" name="duration" value="7">7 days</input>
                    </div>
                </div>

                <div class="quote-form-buttons">
                    <button type="reset" class="btn btn-warning">Reset</button>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>

                <div class="results" id="quoteResult">

                </div>
            </form>
        </div>
        <div class="col-sm-3">
            <table class="pricing-table">
                <thead>
                <tr>
                    <th>Destination</th>
                    <th>Duration</th>
                    <th>Difficulty</th>
                    <th>Cost</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th>Gardiner Lake</th>
                    <td>3 or 5 days</td>
                    <td>Intermediate</td>
                    <td>$40/day</td>

                </tr>
                <tr>
                    <th>The Hellroaring Plateau</th>
                    <td>2, 3, or 4 days</td>
                    <td>Easy</td>
                    <td>$35/day</td>
                </tr>
                <tr>
                    <th>The Beaten Path</th>
                    <td>5 or 7 days</td>
                    <td>Difficult</td>
                    <td>$45/day</td>
                </tr>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="4">All hikes have a 50% surcharge for Sat/Sun hikes.</td>
                </tr>
                </tfoot>
            </table>
        </div>

    </div>
</main>

<footer class="footer">
    <div class="container">
    <span class="text-muted">For more information, check out the
        <a href="http://www.wilderness.net/index.cfm?fuse=NWPS&sec=wildView&WID=1">wilderness website</a>
        for more information about the surrounding area.</span>
    </div>
</footer>
</body>
</html>