package com.codahale.metrics;

import java.io.OutputStream;

public abstract class Snapshot {
   public abstract double getValue(double var1);

   public abstract long[] getValues();

   public abstract int size();

   public double getMedian() {
      return this.getValue(0.5);
   }

   public double get75thPercentile() {
      return this.getValue(0.75);
   }

   public double get95thPercentile() {
      return this.getValue(0.95);
   }

   public double get98thPercentile() {
      return this.getValue(0.98);
   }

   public double get99thPercentile() {
      return this.getValue(0.99);
   }

   public double get999thPercentile() {
      return this.getValue(0.999);
   }

   public abstract long getMax();

   public abstract double getMean();

   public abstract long getMin();

   public abstract double getStdDev();

   public abstract void dump(OutputStream var1);
}
