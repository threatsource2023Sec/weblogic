package org.glassfish.grizzly.memory;

public interface DefaultMemoryManagerFactory {
   String DMMF_PROP_NAME = "org.glassfish.grizzly.MEMORY_MANAGER_FACTORY";

   MemoryManager createMemoryManager();
}
