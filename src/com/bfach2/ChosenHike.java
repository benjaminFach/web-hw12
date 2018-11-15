/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bfach2;

import java.time.LocalDate;

/**
 * A hike chosen by a user.
 *
 * @author Benjamin Fach
 * @since 2018-11-01
 */
public class ChosenHike {


    // class attributes
    private Routes hikingRoute;
    private int duration;
    private LocalDate startDate;

    // for use as a bean
    public ChosenHike() {
    }

    // class constructor
    public ChosenHike(Routes hikingRoute, int duration, LocalDate startDate) {
        this.hikingRoute = hikingRoute;
        this.duration = duration;
        this.startDate = startDate;
    }

    /**
     * @return the hikingRoute
     */
    public Routes getHikingRoute() {
        return hikingRoute;
    }

    /**
     * @param hikingRoute the hikingRoute to set
     */
    public void setHikingRoute(Routes hikingRoute) {
        this.hikingRoute = hikingRoute;
    }

    /**
     * @return the duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return the startDate
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return String.format("%d:%d:%d:%d:%d", getHikingRoute().getHikeID(), getStartDate().getYear(),
                getStartDate().getMonth().getValue(), getStartDate().getDayOfMonth(), getDuration());
    }

}
