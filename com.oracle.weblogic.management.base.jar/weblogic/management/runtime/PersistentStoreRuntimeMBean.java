package weblogic.management.runtime;

import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;

public interface PersistentStoreRuntimeMBean extends RuntimeMBean, HealthFeedback {
   HealthState getHealthState();

   long getCreateCount();

   long getReadCount();

   long getUpdateCount();

   long getDeleteCount();

   long getObjectCount();

   long getPhysicalWriteCount();

   PersistentStoreConnectionRuntimeMBean[] getConnections();

   long getAllocatedWindowBufferBytes();

   long getAllocatedIoBufferBytes();
}
