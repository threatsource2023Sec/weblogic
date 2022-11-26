package com.solarmetric.manage;

import java.text.DecimalFormat;

public class SampleStatistic extends SimpleStatistic {
   private static final long serialVersionUID = 1L;
   private double minValue = Double.NaN;
   private double maxValue = Double.NaN;
   private double cumValue = 0.0;
   private double firstValue;
   private int numSets = 0;

   public SampleStatistic(String name, String description, String ordinateDescription) {
      super(name, description, ordinateDescription, 1, false);
   }

   public double getMinValue() {
      return this.minValue;
   }

   public double getMaxValue() {
      return this.maxValue;
   }

   public double getFirstValue() {
      return this.firstValue;
   }

   public double getSampleSum() {
      return this.cumValue;
   }

   public int getSampleCount() {
      return this.numSets;
   }

   public double getAveValue() {
      return this.numSets > 0 ? this.cumValue / (double)this.numSets : 0.0;
   }

   public void setValue(long time, double value) {
      if (this.numSets == 0) {
         this.minValue = value;
         this.maxValue = value;
         this.cumValue = value;
         this.firstValue = value;
      } else {
         if (value < this.minValue) {
            this.minValue = value;
         }

         if (value > this.maxValue) {
            this.maxValue = value;
         }

         this.cumValue += value;
      }

      ++this.numSets;
      super.setValue(time, value);
   }

   public void setValue(double value) {
      long curtime = System.currentTimeMillis();
      this.setValue(curtime, value);
   }

   public String toString() {
      return this.toString(0);
   }

   public String toString(int digits) {
      DecimalFormat digitsFormat = new DecimalFormat();
      digitsFormat.setMaximumFractionDigits(digits);
      return "sum = " + digitsFormat.format(this.getSampleSum()) + " " + this.getOrdinateDescription() + ", ave = " + digitsFormat.format(this.getAveValue()) + " " + this.getOrdinateDescription() + ", min = " + digitsFormat.format(this.getMinValue()) + " " + this.getOrdinateDescription() + ", max = " + digitsFormat.format(this.getMaxValue()) + " " + this.getOrdinateDescription() + ", first = " + digitsFormat.format(this.getFirstValue()) + " " + this.getOrdinateDescription() + ", count = " + this.getSampleCount();
   }

   public void reset() {
      this.minValue = Double.NaN;
      this.maxValue = Double.NaN;
      this.cumValue = 0.0;
      this.numSets = 0;
   }
}
