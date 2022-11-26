package weblogic.management.j2ee.statistics;

import javax.management.j2ee.statistics.RangeStatistic;

public class RangeStatisticImpl extends StatisticImpl implements RangeStatistic {
   private long current = 0L;
   private long highWaterMark = 0L;
   private long lowWaterMark = 0L;

   public RangeStatisticImpl(String description, String name, String unit, String id) throws StatException {
      super(description, name, unit, id);
   }

   public RangeStatisticImpl(String description, String name, String id) throws StatException {
      super(description, name, id);
   }

   public long getCurrent() {
      return this.current;
   }

   public long getLowWaterMark() {
      return this.lowWaterMark;
   }

   public long getHighWaterMark() {
      return this.highWaterMark;
   }

   public void setCurrent(long current) throws StatException {
      this.current = current;
   }

   public void setLowWaterMark(long lowWaterMark) throws StatException {
      this.lowWaterMark = lowWaterMark;
   }

   public void setHighWaterMark(long highWaterMark) throws StatException {
      this.highWaterMark = highWaterMark;
   }
}
