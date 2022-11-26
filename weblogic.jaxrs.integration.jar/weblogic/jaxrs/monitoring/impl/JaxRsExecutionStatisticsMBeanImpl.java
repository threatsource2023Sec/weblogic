package weblogic.jaxrs.monitoring.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.glassfish.jersey.internal.util.collection.Value;
import org.glassfish.jersey.server.monitoring.ExecutionStatistics;
import org.glassfish.jersey.server.monitoring.TimeWindowStatistics;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsExecutionStatisticsRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

final class JaxRsExecutionStatisticsMBeanImpl extends RuntimeMBeanDelegate implements JaxRsExecutionStatisticsRuntimeMBean {
   private volatile ExecutionStatistics stats;
   private final Map values = new HashMap();

   public JaxRsExecutionStatisticsMBeanImpl(String name, RuntimeMBean parent, ExecutionStatistics initStats) throws ManagementException {
      super(name, parent);
      this.init(initStats);
   }

   private void init(ExecutionStatistics initStats) {
      this.stats = initStats;
      Iterator var2 = initStats.getTimeWindowStatistics().values().iterator();

      while(var2.hasNext()) {
         TimeWindowStatistics timeStats = (TimeWindowStatistics)var2.next();
         final long interval = timeStats.getTimeWindow();
         String postfix = this.convertIntervalToString((int)interval);
         this.values.put("MinTime_" + postfix, new Value() {
            public Number get() {
               return ((TimeWindowStatistics)JaxRsExecutionStatisticsMBeanImpl.this.stats.getTimeWindowStatistics().get(interval)).getMinimumDuration();
            }
         });
         this.values.put("MaxTime_" + postfix, new Value() {
            public Number get() {
               return ((TimeWindowStatistics)JaxRsExecutionStatisticsMBeanImpl.this.stats.getTimeWindowStatistics().get(interval)).getMaximumDuration();
            }
         });
         this.values.put("AverageTime_" + postfix, new Value() {
            public Number get() {
               return ((TimeWindowStatistics)JaxRsExecutionStatisticsMBeanImpl.this.stats.getTimeWindowStatistics().get(interval)).getAverageDuration();
            }
         });
         this.values.put("RequestRate_" + postfix, new Value() {
            public Number get() {
               return ((TimeWindowStatistics)JaxRsExecutionStatisticsMBeanImpl.this.stats.getTimeWindowStatistics().get(interval)).getRequestsPerSecond();
            }
         });
         this.values.put("RequestCount_" + postfix, new Value() {
            public Number get() {
               return ((TimeWindowStatistics)JaxRsExecutionStatisticsMBeanImpl.this.stats.getTimeWindowStatistics().get(interval)).getRequestCount();
            }
         });
      }

   }

   private String convertIntervalToString(int interval) {
      int hours = interval / 3600000;
      interval -= hours * 3600000;
      int minutes = interval / '\uea60';
      interval -= minutes * '\uea60';
      int seconds = interval / 1000;
      StringBuilder sb = new StringBuilder();
      if (hours > 0) {
         sb.append(hours).append("h_");
      }

      if (minutes > 0) {
         sb.append(minutes).append("m_");
      }

      if (seconds > 0) {
         sb.append(seconds).append("s_");
      }

      if (sb.length() == 0) {
         sb.append("total");
      } else {
         sb.setLength(sb.length() - 1);
      }

      return sb.toString();
   }

   public void update(ExecutionStatistics stats) {
      this.stats = stats;
   }

   public long getMinTimeTotal() {
      return ((Number)((Value)this.values.get("MinTime_total")).get()).longValue();
   }

   public long getMinTimeLast1s() {
      return ((Number)((Value)this.values.get("MinTime_1s")).get()).longValue();
   }

   public long getMinTimeLast1m() {
      return ((Number)((Value)this.values.get("MinTime_1m")).get()).longValue();
   }

   public long getMinTimeLast15s() {
      return ((Number)((Value)this.values.get("MinTime_15s")).get()).longValue();
   }

   public long getMinTimeLast15m() {
      return ((Number)((Value)this.values.get("MinTime_15m")).get()).longValue();
   }

   public long getMinTimeLast1h() {
      return ((Number)((Value)this.values.get("MinTime_1h")).get()).longValue();
   }

   public long getMaxTimeTotal() {
      return ((Number)((Value)this.values.get("MaxTime_total")).get()).longValue();
   }

   public long getMaxTimeLast1s() {
      return ((Number)((Value)this.values.get("MaxTime_1s")).get()).longValue();
   }

   public long getMaxTimeLast1m() {
      return ((Number)((Value)this.values.get("MaxTime_1m")).get()).longValue();
   }

   public long getMaxTimeLast15s() {
      return ((Number)((Value)this.values.get("MaxTime_15s")).get()).longValue();
   }

   public long getMaxTimeLast15m() {
      return ((Number)((Value)this.values.get("MaxTime_15m")).get()).longValue();
   }

   public long getMaxTimeLast1h() {
      return ((Number)((Value)this.values.get("MaxTime_1h")).get()).longValue();
   }

   public long getAvgTimeTotal() {
      return ((Number)((Value)this.values.get("AverageTime_total")).get()).longValue();
   }

   public long getAvgTimeLast1s() {
      return ((Number)((Value)this.values.get("AverageTime_1s")).get()).longValue();
   }

   public long getAvgTimeLast1m() {
      return ((Number)((Value)this.values.get("AverageTime_1m")).get()).longValue();
   }

   public long getAvgTimeLast15s() {
      return ((Number)((Value)this.values.get("AverageTime_15s")).get()).longValue();
   }

   public long getAvgTimeLast15m() {
      return ((Number)((Value)this.values.get("AverageTime_15m")).get()).longValue();
   }

   public long getAvgTimeLast1h() {
      return ((Number)((Value)this.values.get("AverageTime_1h")).get()).longValue();
   }

   public double getRequestRateTotal() {
      return ((Number)((Value)this.values.get("RequestRate_total")).get()).doubleValue();
   }

   public double getRequestRateLast1s() {
      return ((Number)((Value)this.values.get("RequestRate_1s")).get()).doubleValue();
   }

   public double getRequestRateLast1m() {
      return ((Number)((Value)this.values.get("RequestRate_1m")).get()).doubleValue();
   }

   public double getRequestRateLast15s() {
      return ((Number)((Value)this.values.get("RequestRate_15s")).get()).doubleValue();
   }

   public double getRequestRateLast15m() {
      return ((Number)((Value)this.values.get("RequestRate_15m")).get()).doubleValue();
   }

   public double getRequestRateLast1h() {
      return ((Number)((Value)this.values.get("RequestRate_1h")).get()).doubleValue();
   }

   public long getRequestCountTotal() {
      return ((Number)((Value)this.values.get("RequestCount_total")).get()).longValue();
   }

   public long getRequestCountLast1s() {
      return ((Number)((Value)this.values.get("RequestCount_1s")).get()).longValue();
   }

   public long getRequestCountLast1m() {
      return ((Number)((Value)this.values.get("RequestCount_1m")).get()).longValue();
   }

   public long getRequestCountLast15s() {
      return ((Number)((Value)this.values.get("RequestCount_15s")).get()).longValue();
   }

   public long getRequestCountLast15m() {
      return ((Number)((Value)this.values.get("RequestCount_15m")).get()).longValue();
   }

   public long getRequestCountLast1h() {
      return ((Number)((Value)this.values.get("RequestCount_1h")).get()).longValue();
   }
}
