package weblogic.diagnostics.flightrecorder;

public interface FlightRecorderDebugEvent extends FlightRecorderEvent {
   void setECID(String var1);

   void setRID(String var1);

   void setPartitionName(String var1);

   void setPartitionId(String var1);

   void setComponent(String var1);

   void setMessage(String var1);

   void setThrowableMessage(String var1);
}
