// constants (and their initialization)

// mapping of hikes to their offered durations
var HIKES_TO_DURATIONS = {};
HIKES_TO_DURATIONS["BEATEN"] = [5, 7];
HIKES_TO_DURATIONS["GARDINER"] = [3, 5];
HIKES_TO_DURATIONS["HELLROARING"] = [2, 3, 4];

// minimum number of people required for a hike
var MIN_HIKERS = 1;

// maximum number of people allowed on a hike
var MAX_HIKERS = 10;

// earliest month/day for a hike
var SEASON_START = moment("9999-06-01", "YYYY-MM-DD");

// latest month/day for a hike
var SEASON_END = moment("9999-10-01", "YYYY-MM-DD");

// furthest year out someone can book
var FURTHEST_YEAR = 2025;

// global variable to build error message, so all errors are presented to user after submission.
var errorMessages = Array();


/*
 * Validates information from user requesting hiking quote.
 * Should be called on form submission before posting to servlet.
 * Displays error message and does not post if invalid input is detected.
 */
function processQuoteRequest() {
    // clear the error message queue
    errorMessages = Array();

    // grab values from form
    var date = $('#date').val();
    var people = $('#people').val();
    var hike = $("input[name=hike]:checked").val();
    var duration = $("input[name=duration]:checked").val();

    // validate each field
    var isValidHike = validateHike(hike);
    validateDuration(duration, hike, isValidHike);
    validateNumPeople(people);
    var isValidDate = validateDate(date);
    if (isValidDate) {
        isInSeason(date, duration);
    }

    // place error messages (if any) into error message display div
    if (errorMessages.length > 0) {
        alert(errorMessages.join("\n"));
        $("#quoteResult").html("");
        return false;
    }

    showResults(hike, date, duration, people);

    return false;
}

/*
 * Checks if a hike was chosen and if so, if it is a hike that is offered.
 * If hike is invalid, add a relevant message to the error message queue and return false.
 * Otherwise return true.
 */
function validateHike(hike) {
    if (typeof hike == 'undefined') {
        errorMessages.push("Please choose a hike.");
        return false;
    }

    if (!(hike in HIKES_TO_DURATIONS)) {
        errorMessages.push("Sorry, " + hike + " isn't a hike that we offer.");
        return false;
    }

    return true;
}

/*
 * Checks if the duration is a valid option for the chosen hike.
 * If the duration is not valid, add a relevant message to the error message queue and return false.
 * Otherwise return true.
 */
function validateDuration(duration, hike, isValidHike) {
    // check if a duration was chosen
    if (typeof duration == 'undefined') {
        errorMessages.push("Please choose a duration.");
        return false;
    }

    // ensure duration is a number
    if (isNaN(duration)) {
        errorMessages.push("Please choose a valid number for the duration of the hike");
        return false;
    }
    duration = parseInt(duration);

    // if there's no hike chosen, can't check duration
    if (!isValidHike) {
        // no error message; will be handled by hike validation
        return false;
    }
    hike = hike.toUpperCase();

    // check if duration is offered for chosen hike
    if (!(HIKES_TO_DURATIONS[hike].includes(duration))) {
        errorMessages.push("Sorry, we don't offer this duration for " + hike + ". Try " + HIKES_TO_DURATIONS[hike].join(" or ") + " days.");
        return false;
    }

    return true;
}

function validateNumPeople(numPeople) {
    // check if a number of people was provided
    if (typeof numPeople == 'undefined') {
        errorMessages.push("Please choose a number of people");
        return false;
    }

    // ensure number of people specified is a number
    if (isNaN(numPeople)) {
        errorMessages.push("The value provided for number of people must be a number");
        return false;
    }

    // ensure number of hikers is above the minimum
    if (numPeople < MIN_HIKERS) {
        errorMessages.push("You must have at least " + MIN_HIKERS + " hiker.");
        return false;
    }

    // ensure number of hikers is below or equal to maximum
    if (numPeople > MAX_HIKERS) {
        errorMessages.push("You can have at most " + MAX_HIKERS + " hikers.");
        return false;
    }

    return true;
}

function validateDate(date) {
    // check if a date was provided
    if (typeof date == 'undefined') {
        errorMessages.push("Please choose a date.");
        return false;
    }

    // ensure the date is a valid day in the Gregorian calendar
    date = moment(date, "YYYY-MM-DD", true);
    if (!(date.isValid())) {
        errorMessages.push("Please choose a valid date");
        return false;
    }
    return true;
}

/*
 * This function assumes the date and duration have been verified.
 */
function isInSeason(date, duration) {
    startDate = moment(date, "YYYY-MM-DD");

    // create copy of season start and set year for easy comparison
    seasonStart = moment(SEASON_START);
    seasonStart.year(startDate.year())

    // create copy of season end and set year for easy comparison
    seasonEnd = moment(SEASON_END);
    seasonEnd.year(startDate.year());

    // check if the start date is before the start of the season
    if (startDate.diff(seasonStart) < 0) {
        errorMessages.push("You must choose a start date that is after the season starts, " + seasonStart.format("YYYY-MM-DD") + ".");
        return false;
    }

    // check if end date is after the season ends
    endDate = moment(startDate).add(duration, 'days');

    if (endDate.diff(seasonEnd) > 0) {
        errorMessages.push("Your hike must finish before the end of the season, " + seasonEnd.format("YYYY-MM-DD") + ".");
        return false;
    }

    // make sure the start date isn't too far out
    if (startDate.year() > FURTHEST_YEAR) {
        errorMessages.push("Sorry, we don't take bookings that far in advance.");
        return false;
    }

    return true;
}

function showResults(hike, date, duration, numPeople) {
    // submit the request to the servlet
    $.ajax({
        type: "post",
        url: generateRequestUrl(hike, date, duration, numPeople),
        data: "",
        success: function (data) {
            console.log("Submission was successful.");
            console.log(data);
            $("#quoteResult").html("<label>" + data + "</label>");
        },
        error: function (data) {
            console.log("Something went wrong.");
            $("#quoteResult").html("");
        },
    });

}

function generateRequestUrl(hike, date, duration, numPeople) {
    date = moment(date, "YYYY-MM-DD");

    var requestUrl = "https://web6.jhuep.com/fach_hw10/QuoteController";
    requestUrl = requestUrl + ("?trail=" + hike);

    // month is zero indexed in moment
    requestUrl = requestUrl + ("&month=" + (date.month() + 1));
    requestUrl = requestUrl + ("&day=" + date.date());
    requestUrl = requestUrl + ("&year=" + date.year());
    requestUrl = requestUrl + ("&duration=" + duration);
    requestUrl = requestUrl + ("&numPeople=" + numPeople);

    return requestUrl;
}