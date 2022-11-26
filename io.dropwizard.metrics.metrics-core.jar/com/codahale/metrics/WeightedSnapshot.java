package com.codahale.metrics;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class WeightedSnapshot extends Snapshot {
   private final long[] values;
   private final double[] normWeights;
   private final double[] quantiles;

   public WeightedSnapshot(Collection values) {
      WeightedSample[] copy = (WeightedSample[])values.toArray(new WeightedSample[0]);
      Arrays.sort(copy, Comparator.comparingLong((w) -> {
         return w.value;
      }));
      this.values = new long[copy.length];
      this.normWeights = new double[copy.length];
      this.quantiles = new double[copy.length];
      double sumWeight = 0.0;
      WeightedSample[] var5 = copy;
      int var6 = copy.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         WeightedSample sample = var5[var7];
         sumWeight += sample.weight;
      }

      int i;
      for(i = 0; i < copy.length; ++i) {
         this.values[i] = copy[i].value;
         this.normWeights[i] = sumWeight != 0.0 ? copy[i].weight / sumWeight : 0.0;
      }

      for(i = 1; i < copy.length; ++i) {
         this.quantiles[i] = this.quantiles[i - 1] + this.normWeights[i - 1];
      }

   }

   public double getValue(double quantile) {
      if (!(quantile < 0.0) && !(quantile > 1.0) && !Double.isNaN(quantile)) {
         if (this.values.length == 0) {
            return 0.0;
         } else {
            int posx = Arrays.binarySearch(this.quantiles, quantile);
            if (posx < 0) {
               posx = -posx - 1 - 1;
            }

            if (posx < 1) {
               return (double)this.values[0];
            } else {
               return posx >= this.values.length ? (double)this.values[this.values.length - 1] : (double)this.values[posx];
            }
         }
      } else {
         throw new IllegalArgumentException(quantile + " is not in [0..1]");
      }
   }

   public int size() {
      return this.values.length;
   }

   public long[] getValues() {
      return Arrays.copyOf(this.values, this.values.length);
   }

   public long getMax() {
      return this.values.length == 0 ? 0L : this.values[this.values.length - 1];
   }

   public long getMin() {
      return this.values.length == 0 ? 0L : this.values[0];
   }

   public double getMean() {
      if (this.values.length == 0) {
         return 0.0;
      } else {
         double sum = 0.0;

         for(int i = 0; i < this.values.length; ++i) {
            sum += (double)this.values[i] * this.normWeights[i];
         }

         return sum;
      }
   }

   public double getStdDev() {
      if (this.values.length <= 1) {
         return 0.0;
      } else {
         double mean = this.getMean();
         double variance = 0.0;

         for(int i = 0; i < this.values.length; ++i) {
            double diff = (double)this.values[i] - mean;
            variance += this.normWeights[i] * diff * diff;
         }

         return Math.sqrt(variance);
      }
   }

   public void dump(OutputStream output) {
      PrintWriter out = new PrintWriter(new OutputStreamWriter(output, StandardCharsets.UTF_8));
      Throwable var3 = null;

      try {
         long[] var4 = this.values;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            long value = var4[var6];
            out.printf("%d%n", value);
         }
      } catch (Throwable var16) {
         var3 = var16;
         throw var16;
      } finally {
         if (var3 != null) {
            try {
               out.close();
            } catch (Throwable var15) {
               var3.addSuppressed(var15);
            }
         } else {
            out.close();
         }

      }

   }

   public static class WeightedSample {
      public final long value;
      public final double weight;

      public WeightedSample(long value, double weight) {
         this.value = value;
         this.weight = weight;
      }
   }
}
