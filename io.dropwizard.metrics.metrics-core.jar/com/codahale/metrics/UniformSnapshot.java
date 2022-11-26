package com.codahale.metrics;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

public class UniformSnapshot extends Snapshot {
   private final long[] values;

   public UniformSnapshot(Collection values) {
      Object[] copy = values.toArray();
      this.values = new long[copy.length];

      for(int i = 0; i < copy.length; ++i) {
         this.values[i] = (Long)copy[i];
      }

      Arrays.sort(this.values);
   }

   public UniformSnapshot(long[] values) {
      this.values = Arrays.copyOf(values, values.length);
      Arrays.sort(this.values);
   }

   public double getValue(double quantile) {
      if (!(quantile < 0.0) && !(quantile > 1.0) && !Double.isNaN(quantile)) {
         if (this.values.length == 0) {
            return 0.0;
         } else {
            double pos = quantile * (double)(this.values.length + 1);
            int index = (int)pos;
            if (index < 1) {
               return (double)this.values[0];
            } else if (index >= this.values.length) {
               return (double)this.values[this.values.length - 1];
            } else {
               double lower = (double)this.values[index - 1];
               double upper = (double)this.values[index];
               return lower + (pos - Math.floor(pos)) * (upper - lower);
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
         long[] var3 = this.values;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            long value = var3[var5];
            sum += (double)value;
         }

         return sum / (double)this.values.length;
      }
   }

   public double getStdDev() {
      if (this.values.length <= 1) {
         return 0.0;
      } else {
         double mean = this.getMean();
         double sum = 0.0;
         long[] var5 = this.values;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            long value = var5[var7];
            double diff = (double)value - mean;
            sum += diff * diff;
         }

         double variance = sum / (double)(this.values.length - 1);
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
}
