package com.codahale.metrics;

public abstract class RatioGauge implements Gauge {
   protected abstract Ratio getRatio();

   public Double getValue() {
      return this.getRatio().getValue();
   }

   public static class Ratio {
      private final double numerator;
      private final double denominator;

      public static Ratio of(double numerator, double denominator) {
         return new Ratio(numerator, denominator);
      }

      private Ratio(double numerator, double denominator) {
         this.numerator = numerator;
         this.denominator = denominator;
      }

      public double getValue() {
         double d = this.denominator;
         return !Double.isNaN(d) && !Double.isInfinite(d) && d != 0.0 ? this.numerator / d : Double.NaN;
      }

      public String toString() {
         return this.numerator + ":" + this.denominator;
      }
   }
}
