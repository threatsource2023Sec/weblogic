package com.bea.adaptive.harvester.jmx;

public interface MetricCacheListener {
   void instanceDeleted(String var1, String var2);

   void newInstance(String var1, String var2);
}
