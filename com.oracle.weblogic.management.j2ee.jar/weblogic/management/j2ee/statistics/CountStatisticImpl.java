package weblogic.management.j2ee.statistics;

import javax.management.j2ee.statistics.CountStatistic;

public class CountStatisticImpl extends StatisticImpl implements CountStatistic {
   private long count = 0L;

   public CountStatisticImpl(String description, String name, String unit, String id) throws StatException {
      super(description, name, unit, id);
   }

   public CountStatisticImpl(String description, String name, String id) throws StatException {
      super(description, name, id);
   }

   public long getCount() {
      return this.count;
   }

   public void setCount(long count) throws StatException {
      this.count = count;
   }
}
