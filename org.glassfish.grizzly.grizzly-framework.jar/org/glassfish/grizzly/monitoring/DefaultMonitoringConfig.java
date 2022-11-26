package org.glassfish.grizzly.monitoring;

import org.glassfish.grizzly.utils.ArraySet;

public class DefaultMonitoringConfig implements MonitoringConfig {
   private final ArraySet monitoringProbes;

   public DefaultMonitoringConfig(Class clazz) {
      this.monitoringProbes = new ArraySet(clazz);
   }

   public final void addProbes(Object... probes) {
      this.monitoringProbes.addAll(probes);
   }

   public final boolean removeProbes(Object... probes) {
      return this.monitoringProbes.removeAll(probes);
   }

   public final Object[] getProbes() {
      return this.monitoringProbes.obtainArrayCopy();
   }

   public final Object[] getProbesUnsafe() {
      return this.monitoringProbes.getArray();
   }

   public boolean hasProbes() {
      return !this.monitoringProbes.isEmpty();
   }

   public final void clearProbes() {
      this.monitoringProbes.clear();
   }

   public Object createManagementObject() {
      return null;
   }
}
