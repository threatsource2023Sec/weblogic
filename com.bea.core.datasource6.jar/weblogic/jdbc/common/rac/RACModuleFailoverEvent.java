package weblogic.jdbc.common.rac;

public interface RACModuleFailoverEvent {
   String getInstanceName();

   String getServiceName();

   String getDbUniqueName();

   String getHostName();

   String getStatus();

   String getReason();

   String getEventType();

   boolean isPlanned();

   boolean isServiceEvent();

   boolean isNodeEvent();

   boolean isUpEvent();

   boolean isDownEvent();

   int getDrainTimeout();
}
