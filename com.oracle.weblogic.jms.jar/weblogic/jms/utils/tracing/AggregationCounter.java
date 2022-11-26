package weblogic.jms.utils.tracing;

import java.util.concurrent.atomic.AtomicInteger;

public class AggregationCounter {
   SubBuffer dataArea;
   private AtomicInteger[] values;

   public AggregationCounter(String id, int nvalues) {
      this.dataArea = DataLog.newDataArea("AGGREGATION-" + id, nvalues * 4);
      this.values = new AtomicInteger[nvalues];

      for(int i = 0; i < nvalues; ++i) {
         this.values[i] = new AtomicInteger();
      }

   }

   public void increment(int value) {
      if (value > this.values.length) {
         throw new AssertionError("I'm FUCKED");
      } else {
         int newCount = this.values[value].addAndGet(1);
         this.dataArea.putInt(value * 4, newCount);
      }
   }
}
