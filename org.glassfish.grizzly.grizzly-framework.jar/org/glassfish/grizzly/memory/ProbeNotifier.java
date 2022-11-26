package org.glassfish.grizzly.memory;

import org.glassfish.grizzly.monitoring.DefaultMonitoringConfig;

final class ProbeNotifier {
   static void notifyBufferAllocated(DefaultMonitoringConfig config, int size) {
      MemoryProbe[] probes = (MemoryProbe[])config.getProbesUnsafe();
      if (probes != null) {
         MemoryProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MemoryProbe probe = var3[var5];
            probe.onBufferAllocateEvent(size);
         }
      }

   }

   static void notifyBufferAllocatedFromPool(DefaultMonitoringConfig config, int size) {
      MemoryProbe[] probes = (MemoryProbe[])config.getProbesUnsafe();
      if (probes != null) {
         MemoryProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MemoryProbe probe = var3[var5];
            probe.onBufferAllocateFromPoolEvent(size);
         }
      }

   }

   static void notifyBufferReleasedToPool(DefaultMonitoringConfig config, int size) {
      MemoryProbe[] probes = (MemoryProbe[])config.getProbesUnsafe();
      if (probes != null) {
         MemoryProbe[] var3 = probes;
         int var4 = probes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MemoryProbe probe = var3[var5];
            probe.onBufferReleaseToPoolEvent(size);
         }
      }

   }
}
