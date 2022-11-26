package weblogic.diagnostics.instrumentation.gathering;

import weblogic.diagnostics.instrumentation.ValueRenderer;
import weblogic.jms.backend.BEConsumerImpl;
import weblogic.jms.backend.BEDestinationImpl;

public class JMSConsumerImplRenderer implements ValueRenderer {
   public Object render(Object inputObject) {
      if (inputObject != null && inputObject instanceof BEConsumerImpl) {
         BEConsumerImpl consumerImpl = (BEConsumerImpl)inputObject;
         BEDestinationImpl destinationImpl = consumerImpl.getDestination();
         return new JMSBEConsumerLogEventInfoImpl(consumerImpl.getName(), consumerImpl.getSubscriptionName(), destinationImpl == null ? null : destinationImpl.getName());
      } else {
         return null;
      }
   }
}
