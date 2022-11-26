package weblogic.diagnostics.flightrecorder.event;

public interface JMSBEConsumerLogEventInfo {
   String getConsumerLifecycle();

   void setConsumerLifecycle(String var1);

   String getConsumer();

   void setConsumer(String var1);

   String getSubscription();

   void setSubscription(String var1);

   String getDestination();

   void setDestination(String var1);
}
