package com.codahale.metrics;

public interface Reservoir {
   int size();

   void update(long var1);

   Snapshot getSnapshot();
}
