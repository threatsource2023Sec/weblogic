package com.solarmetric.manage;

public class AggregatingStatistic extends SimpleStatistic {
   private double minValue = Double.NaN;
   private double maxValue = Double.NaN;
   private double cumValue = 0.0;
   private long startTime = System.currentTimeMillis();
   private long lastSetTime = System.currentTimeMillis();
   private int numSets = 0;

   public AggregatingStatistic(String name, String description, String ordinateDescription) {
      super(name, description, ordinateDescription);
   }

   public AggregatingStatistic(String name, String description, String ordinateDescription, int drawStyle, boolean ignoreDuplicates) {
      super(name, description, ordinateDescription, drawStyle, ignoreDuplicates);
   }

   public double getMinValue() {
      return this.minValue;
   }

   public double getMaxValue() {
      return this.maxValue;
   }

   void setStartTime(long time) {
      this.startTime = time;
      this.lastSetTime = time;
   }

   public double getAveValue(long time) {
      switch (super.getDrawStyle()) {
         case 0:
         case 2:
            double timediff = (double)(time - this.startTime);
            if (timediff == 0.0) {
               return 0.0;
            }

            double aveVal = (this.cumValue + (double)(time - this.lastSetTime) * super.getValue()) / timediff;
            return aveVal;
         case 1:
            return this.cumValue / (double)this.numSets;
         default:
            return 0.0;
      }
   }

   public double getAveValue() {
      return this.getAveValue(System.currentTimeMillis());
   }

   public void setValue(long time, double value) {
      if (this.numSets == 0) {
         this.minValue = value;
         this.maxValue = value;
         switch (super.getDrawStyle()) {
            case 0:
            case 2:
               this.startTime = System.currentTimeMillis();
               break;
            case 1:
               this.cumValue = value;
         }
      } else {
         if (value < this.minValue) {
            this.minValue = value;
         }

         if (value > this.maxValue) {
            this.maxValue = value;
         }

         switch (super.getDrawStyle()) {
            case 0:
            case 2:
               if (Double.isNaN(super.getValue())) {
                  this.startTime += time - this.lastSetTime;
               } else {
                  this.cumValue += (double)(time - this.lastSetTime) * super.getValue();
               }
               break;
            case 1:
               this.cumValue += value;
         }
      }

      this.lastSetTime = time;
      ++this.numSets;
      super.setValue(time, value);
   }

   public void setValue(double value) {
      long curtime = System.currentTimeMillis();
      this.setValue(curtime, value);
   }
}
