package com.solarmetric.manage;

public class BoundedStatistic extends SimpleStatistic {
   private double lowerBound = Double.NaN;
   private double upperBound = Double.NaN;

   public BoundedStatistic(String name, String description, String ordinateDescription) {
      super(name, description, ordinateDescription);
   }

   public BoundedStatistic(String name, String description, String ordinateDescription, int drawStyle, boolean ignoreDuplicates) {
      super(name, description, ordinateDescription, drawStyle, ignoreDuplicates);
   }

   public double getLowerBound() {
      return this.lowerBound;
   }

   public double getUpperBound() {
      return this.upperBound;
   }

   public void setLowerBound(double lowerBound) {
      this.lowerBound = lowerBound;
   }

   public void setUpperBound(double upperBound) {
      this.upperBound = upperBound;
   }
}
