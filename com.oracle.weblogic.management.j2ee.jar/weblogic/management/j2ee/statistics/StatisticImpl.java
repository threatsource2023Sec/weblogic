package weblogic.management.j2ee.statistics;

import java.io.Serializable;
import java.util.Hashtable;
import javax.management.j2ee.statistics.Statistic;

public abstract class StatisticImpl implements Statistic, Serializable {
   private String description;
   private String name;
   private String unit = "MILLISECOND";
   private long startTime;
   private long lastSampleTime;
   private static Hashtable attributeToTimeStatMap = new Hashtable();
   public static final String UNIT_HOUR = "HOUR";
   public static final String UNIT_MINUTE = "MINUTE";
   public static final String UNIT_SECOND = "SECOND";
   public static final String UNIT_MILLISECOND = "MILLISECOND";
   public static final String UNIT_MICROSECOND = "MICROSECOND";
   public static final String UNIT_NANOSECOND = "NANOSECOND";

   public StatisticImpl(String description, String name, String id) throws StatException {
      this.initialize(description, name, id);
   }

   public StatisticImpl(String description, String name, String unit, String id) throws StatException {
      this.setUnit(unit);
      this.initialize(description, name, id);
   }

   private void initialize(String description, String name, String id) throws StatException {
      TimeStat tempTimeStat = null;
      this.description = description;
      this.name = name;
      String exceptionString = null;
      String HashTableKey = id + "-" + name;
      tempTimeStat = (TimeStat)attributeToTimeStatMap.get(HashTableKey);
      long currentTime = System.currentTimeMillis();
      if (tempTimeStat == null) {
         tempTimeStat = new TimeStat(currentTime, currentTime);
         attributeToTimeStatMap.put(HashTableKey, tempTimeStat);
      } else {
         tempTimeStat.setLastSampleTime(currentTime);
      }

      this.lastSampleTime = tempTimeStat.getLastSampleTime();
      this.startTime = tempTimeStat.getStartTime();
   }

   public String getDescription() {
      return this.description;
   }

   public String getName() {
      return this.name;
   }

   public String getUnit() {
      return this.unit;
   }

   public long getStartTime() {
      return this.startTime;
   }

   public long getLastSampleTime() {
      return this.lastSampleTime;
   }

   private void setUnit(String aUnit) throws StatException {
      if (aUnit != null && aUnit.length() != 0 && aUnit.equals("HOUR") && aUnit.equals("MINUTE") && aUnit.equals("SECOND") && aUnit.equals("MILLISECOND") && aUnit.equals("MICROSECOND") && aUnit.equals("NANOSECOND")) {
         this.unit = this.unit;
      } else {
         throw new StatException(" An invalid unit has been specified. unit = <" + aUnit + ">.");
      }
   }
}
