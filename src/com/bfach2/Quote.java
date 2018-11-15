/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bfach2;

/**
 * @author Ben
 */
public class Quote {

  private ChosenHike hike = null;
  private int numPeople = 1;
  private double value = 0.0;
  private String errorMessage = "";
  private boolean isInvalid = false;

  public Quote() {

  }

  public Quote(ChosenHike hike, double value, int numPeople) {
    this.hike = hike;
    this.value = value;
    this.numPeople = numPeople;
  }

  // Getters

  /**
   * Returns the chosen hike for this quote
   *
   * @return the hike
   */
  public ChosenHike getHike() {
    return this.hike;
  }

  /**
   * Returns the value of the quote
   *
   * @return the cost associated with the quote
   */
  public double getValue() {
    return this.value;
  }

  /**
   * Returns the error message
   *
   * @return the error message
   */
  public String getErrorMessage() {
    return this.errorMessage;
  }

  // Setters

  /**
   * Sets the hike for this quote
   *
   * @param hike the hike chosen for this quote
   */
  public void setHike(ChosenHike hike) {
    this.hike = hike;
  }

  /**
   * Sets the value of the quote
   *
   * @param value the cost associated with the quote
   */
  public void setValue(double value) {
    this.value = value;
  }

  /**
   * Sets the error message
   * @param errorMessage a sentence describing the error
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * Adds on to the error message
   * @param errorMessage a message to append to the error message
   */
  public void updateErrorMessage(String errorMessage) {
    this.errorMessage = this.errorMessage + "\n" + errorMessage;
  }

  @Override
  public String toString() {
    if (isInvalid) {
      return this.errorMessage;
    } else {
      return String
          .format("This hike will cost you $%.2f per person ($%.2f total)", value,
              value * numPeople);
    }
  }

}
