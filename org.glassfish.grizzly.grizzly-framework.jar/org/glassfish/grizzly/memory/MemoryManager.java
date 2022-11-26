package org.glassfish.grizzly.memory;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.monitoring.MonitoringAware;

public interface MemoryManager extends MonitoringAware {
   MemoryManager DEFAULT_MEMORY_MANAGER = MemoryManagerInitializer.initManager();

   Buffer allocate(int var1);

   Buffer allocateAtLeast(int var1);

   Buffer reallocate(Buffer var1, int var2);

   void release(Buffer var1);

   boolean willAllocateDirect(int var1);
}
