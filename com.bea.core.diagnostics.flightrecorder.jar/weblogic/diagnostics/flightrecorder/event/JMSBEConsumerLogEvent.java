package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "JMS BE Consumer Log",
   description = "This shows BEConsumer lifecycle events CREATE/DESTROY",
   path = "wls/JMS/JMS_BE_Consumer_Log",
   thread = true
)
public class JMSBEConsumerLogEvent extends BaseInstantEvent implements JMSBEConsumerLogEventInfo {
   @ValueDefinition(
      name = "Subsystem",
      description = "The subsystem ID",
      relationKey = "http://www.oracle.com/wls/JMS"
   )
   protected String subsystem = "JMS";
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Consumer Lifecycle",
      description = "The consumer lifecycle event",
      relationKey = "http://www.oracle.com/wls/JMS/consumerLifecycle"
   )
   protected String consumerLifecycle = null;
   @ValueDefinition(
      name = "Consumer",
      description = "The consumer name",
      relationKey = "http://www.oracle.com/wls/JMS/consumer"
   )
   protected String consumer = null;
   @ValueDefinition(
      name = "Subscription",
      description = "The subscription name",
      relationKey = "http://www.oracle.com/wls/JMS/subscription"
   )
   protected String subscription = null;
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Destination",
      description = "The destination name",
      relationKey = "http://www.oracle.com/wls/JMS/destination"
   )
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
