package weblogic.diagnostics.flightrecorder;

import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

public interface FlightRecorderBaseEvent extends FlightRecorderEvent {
   void populateExtensions(Object var1, Object[] var2, DynamicJoinPoint var3, boolean var4);

   boolean isECIDEnabled();

   void setECID(String var1);

   void setRID(String var1);

   void setPartitionName(String var1);

   void setPartitionId(String var1);

   void setTransactionID(String var1);

   void setClassName(String var1);

   void setMethodName(String var1);

   void setReturnValue(String var1);

   void setUserID(String var1);

   String getUserID();

   void setThrottled(boolean var1);

   boolean getThrottled();

   boolean requiresProcessingArgsAfter();

   void generateInFlight();

   boolean willGenerateInFlight();
}
