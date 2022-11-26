package com.solarmetric.manage;

import java.util.EventObject;

public class StatisticEvent extends EventObject {
   private long time;
   private double value;

   public StatisticEvent(Statistic stat, long time, double value) {
      super(stat);
      this.time = time;
      this.value = value;
   }

   public Statistic getStatistic() {
      return (Statistic)this.getSource();
   }

   public long getTime() {
      return this.time;
   }

   public double getValue() {
      return this.value;
   }
}
