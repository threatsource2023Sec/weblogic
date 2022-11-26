package weblogic.management.runtime;

public interface AsyncReplicationRuntimeMBean extends ReplicationRuntimeMBean {
   int getSessionsWaitingForFlushCount();

   long getLastSessionsFlushTime();
}
