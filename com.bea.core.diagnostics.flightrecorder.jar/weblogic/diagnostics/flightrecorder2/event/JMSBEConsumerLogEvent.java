package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder.event.JMSBEConsumerLogEventInfo;
import weblogic.diagnostics.flightrecorder.event.JMSBEConsumerLogEventInfoHelper;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("JMS BE Consumer Log")
@Name("com.oracle.weblogic.jms.JMSBEConsumerLogEvent")
@Description("This shows BEConsumer lifecycle events CREATE/DESTROY")
@Category({"WebLogic Server", "JMS"})
public class JMSBEConsumerLogEvent extends BaseEvent implements JMSBEConsumerLogEventInfo {
   @Label("Subsystem")
   @Description("The subsystem ID")
   @RelationKey("http://www.oracle.com/wls/JMS")
   protected String subsystem = "JMS";
   @Label("Consumer Lifecycle")
   @Description("The consumer lifecycle event")
   @RelationKey("http://www.oracle.com/wls/JMS/consumerLifecycle")
   protected String consumerLifecycle = null;
   @Label("Consumer")
   @Description("The consumer name")
   @RelationKey("http://www.oracle.com/wls/JMS/consumer")
   protected String consumer = null;
   @Label("Subscription")
   @Description("The subscription name")
   @RelationKey("http://www.oracle.com/wls/JMS/subscription")
   protected String subscription = null;
   @Label("Destination")
   @Description("The destination name")
   @RelationKey("http://www.oracle.com/wls/JMS/destination")
   protected String destination = null;

   public String getSubsystem() {
      return this.subsystem;
   }

   public void setSubsystem(String subsystem) {
      this.subsystem = subsystem;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      JMSBEConsumerLogEventInfoHelper.populateExtensions(retVal, args, this);
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
}
