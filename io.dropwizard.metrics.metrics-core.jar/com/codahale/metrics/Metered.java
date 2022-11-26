package com.codahale.metrics;

public interface Metered extends Metric, Counting {
   long getCount();

   double getFifteenMinuteRate();

   double getFiveMinuteRate();

   double getMeanRate();

   double getOneMinuteRate();
}
