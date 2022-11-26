package org.glassfish.grizzly.monitoring;

public interface MonitoringConfig {
   void addProbes(Object... var1);

   boolean removeProbes(Object... var1);

   Object[] getProbes();

   boolean hasProbes();

   void clearProbes();

   Object createManagementObject();
}
