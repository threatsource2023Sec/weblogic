package org.python.google.common.math;

import java.util.Iterator;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.common.primitives.Doubles;

@Beta
@GwtIncompatible
public final class StatsAccumulator {
   private long count = 0L;
   private double mean = 0.0;
   private double sumOfSquaresOfDeltas = 0.0;
   private double min = Double.NaN;
   private double max = Double.NaN;

   public void add(double value) {
      if (this.count == 0L) {
         this.count = 1L;
         this.mean = value;
         this.min = value;
         this.max = value;
         if (!Doubles.isFinite(value)) {
            this.sumOfSquaresOfDeltas = Double.NaN;
         }
      } else {
         ++this.count;
         if (Doubles.isFinite(value) && Doubles.isFinite(this.mean)) {
            double delta = value - this.mean;
            this.mean += delta / (double)this.count;
            this.sumOfSquaresOfDeltas += delta * (value - this.mean);
         } else {
            this.mean = calculateNewMeanNonFinite(this.mean, value);
            this.sumOfSquaresOfDeltas = Double.NaN;
         }

         this.min = Math.min(this.min, value);
         this.max = Math.max(this.max, value);
      }

   }

   public void addAll(Iterable values) {
      Iterator var2 = values.iterator();

      while(var2.hasNext()) {
         Number value = (Number)var2.next();
         this.add(value.doubleValue());
      }

   }

   public void addAll(Iterator values) {
      while(values.hasNext()) {
         this.add(((Number)values.next()).doubleValue());
      }

   }

   public void addAll(double... values) {
      double[] var2 = values;
      int var3 = values.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         double value = var2[var4];
         this.add(value);
      }

   }

   public void addAll(int... values) {
      int[] var2 = values;
      int var3 = values.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int value = var2[var4];
         this.add((double)value);
      }

   }

   public void addAll(long... values) {
      long[] var2 = values;
      int var3 = values.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         long value = var2[var4];
         this.add((double)value);
      }

   }

   public void addAll(Stats values) {
      if (values.count() != 0L) {
         if (this.count == 0L) {
            this.count = values.count();
            this.mean = values.mean();
            this.sumOfSquaresOfDeltas = values.sumOfSquaresOfDeltas();
            this.min = values.min();
            this.max = values.max();
         } else {
            this.count += values.count();
            if (Doubles.isFinite(this.mean) && Doubles.isFinite(values.mean())) {
               double delta = values.mean() - this.mean;
               this.mean += delta * (double)values.count() / (double)this.count;
               this.sumOfSquaresOfDeltas += values.sumOfSquaresOfDeltas() + delta * (values.mean() - this.mean) * (double)values.count();
            } else {
               this.mean = calculateNewMeanNonFinite(this.mean, values.mean());
               this.sumOfSquaresOfDeltas = Double.NaN;
            }

            this.min = Math.min(this.min, values.min());
            this.max = Math.max(this.max, values.max());
         }

      }
   }

   public Stats snapshot() {
      return new Stats(this.count, this.mean, this.sumOfSquaresOfDeltas, this.min, this.max);
   }

   public long count() {
      return this.count;
   }

   public double mean() {
      Preconditions.checkState(this.count != 0L);
      return this.mean;
   }

   public final double sum() {
      return this.mean * (double)this.count;
   }

   public final double populationVariance() {
      Preconditions.checkState(this.count != 0L);
      if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
         return Double.NaN;
      } else {
         return this.count == 1L ? 0.0 : DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)this.count;
      }
   }

   public final double populationStandardDeviation() {
      return Math.sqrt(this.populationVariance());
   }

   public final double sampleVariance() {
      Preconditions.checkState(this.count > 1L);
      return Double.isNaN(this.sumOfSquaresOfDeltas) ? Double.NaN : DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)(this.count - 1L);
   }

   public final double sampleStandardDeviation() {
      return Math.sqrt(this.sampleVariance());
   }

   public double min() {
      Preconditions.checkState(this.count != 0L);
      return this.min;
   }

   public double max() {
      Preconditions.checkState(this.count != 0L);
      return this.max;
   }

   double sumOfSquaresOfDeltas() {
      return this.sumOfSquaresOfDeltas;
   }

   static double calculateNewMeanNonFinite(double previousMean, double value) {
      if (Doubles.isFinite(previousMean)) {
         return value;
      } else {
         return !Doubles.isFinite(value) && previousMean != value ? Double.NaN : previousMean;
      }
   }
}
