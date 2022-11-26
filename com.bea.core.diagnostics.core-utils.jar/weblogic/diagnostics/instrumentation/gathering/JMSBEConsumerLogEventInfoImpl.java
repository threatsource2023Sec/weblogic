package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.flightrecorder.event.JMSBEConsumerLogEventInfo;

public class JMSBEConsumerLogEventInfoImpl implements JMSBEConsumerLogEventInfo {
   private String consumerLifecycle = null;
   private String consumer = null;
   private String subscription = null;
   private String destination = null;

   public JMSBEConsumerLogEventInfoImpl(String consumer, String subscription, String destination) {
      this.consumer = consumer;
      this.subscription = subscription;
      this.destination = destination;
   }

   public String getConsumerLifecycle() {
      return this.consumerLifecycle;
   }

   public void setConsumerLifecycle(String consumerLifecycle) {
      this.consumerLifecycle = consumerLifecycle;
   }

   public String getConsumer() {
      return this.consumer;
   }

   public void setConsumer(String consumer) {
      this.consumer = consumer;
   }

   public String getSubscription() {
      return this.subscription;
   }

   public void setSubscription(String subscription) {
      this.subscription = subscription;
   }

   public String getDestination() {
      return this.destination;
   }

   public void setDestination(String destination) {
      this.destination = destination;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if (this.consumerLifecycle != null) {
         sb.append("consumerLifecycle=");
         sb.append(this.consumerLifecycle);
         sb.append(",");
      }

      sb.append("consumer=");
      sb.append(this.consumer);
      sb.append("subscription=");
      sb.append(this.subscription);
      sb.append("destination=");
      sb.append(this.destination);
      return sb.toString();
   }
}
