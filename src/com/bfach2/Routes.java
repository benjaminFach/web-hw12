package com.bfach2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import com.bfach2.utils.Rates.HIKE;

public enum Routes {

  BEATEN(2, "Beaten", Stream.of(5, 7).collect(Collectors.toList()), HIKE.BEATEN),
  GARDINER(0, "Gardiner", Stream.of(3, 5).collect(Collectors.toList()), HIKE.GARDINER),
  HELLROARING(1, "Hellroaring", Stream.of(2, 3, 4).collect(Collectors.toList()), HIKE.HELLROARING);

  private final int hikeID;
  private final String name;
  private final List<Integer> durations;
  private final HIKE hike;
  private final HashMap<String, List<Integer>> namesToDurations = new HashMap<>();

  private Routes(final int hikeID, final String name, List<Integer> durations, HIKE hike) {
    this.hikeID = hikeID;
    this.name = name;
    this.durations = durations;
    this.hike = hike;
    namesToDurations.put(name, durations);
  }

  public int getHikeID() {
    return hikeID;
  }

  public String getName() {
    return name;
  }

  public ArrayList<Integer> getDurations() {
    return new ArrayList<Integer>(durations);
  }

  public HIKE asHike() {
    return this.hike;
  }

  public List<Integer> getDurationFromName(String queriedName) {
    return namesToDurations.get(queriedName);
  }

}
