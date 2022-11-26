package weblogic.management.j2ee.statistics;

import javax.management.j2ee.statistics.TimeStatistic;

public class TimeStatisticImpl extends StatisticImpl implements TimeStatistic {
   private long count = 0L;
   private long maxTime = 0L;
   private long minTime = 0L;
   private long totalTime = 0L;

   public TimeStatisticImpl(String description, String name, String unit, String id) throws StatException {
      super(description, name, unit, id);
   }

   public TimeStatisticImpl(String description, String name, String id) throws StatException {
      super(description, name, id);
   }

   public long getCount() {
      return this.count;
   }

   public long getMinTime() {
      return this.minTime;
   }

   public long getMaxTime() {
      return this.maxTime;
   }

   public long getTotalTime() {
      return this.totalTime;
   }

   public void setCount(long count) throws StatException {
      this.count = count;
   }

   public void setMinTime(long minTime) throws StatException {
      this.minTime = minTime;
   }

   public void setMaxTime(long maxTime) throws StatException {
      this.maxTime = maxTime;
   }

   public void setTotalTime(long totalTime) throws StatException {
      this.totalTime = totalTime;
   }
}
