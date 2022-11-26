package weblogic.management.runtime;

public interface PersistentStoreConnectionRuntimeMBean extends RuntimeMBean {
   long getCreateCount();

   long getReadCount();

   long getUpdateCount();

   long getDeleteCount();

   long getObjectCount();
}
