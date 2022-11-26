package weblogic.management.j2ee.statistics;

import javax.management.j2ee.statistics.BoundaryStatistic;

public class BoundaryStatisticImpl extends StatisticImpl implements BoundaryStatistic {
   private long lowerBound = 0L;
   private long upperBound = 0L;

   public BoundaryStatisticImpl(String description, String name, String unit, String id) throws StatException {
      super(description, name, unit, id);
   }

   public BoundaryStatisticImpl(String description, String name, String id) throws StatException {
      super(description, name, id);
   }

   public long getLowerBound() {
      return this.lowerBound;
   }

   public long getUpperBound() {
      return this.upperBound;
   }

   public void setUpperBound(long upperBound) throws StatException {
      this.upperBound = upperBound;
   }

   public void setLowerBound(long lowerBound) throws StatException {
      this.lowerBound = lowerBound;
   }
}
