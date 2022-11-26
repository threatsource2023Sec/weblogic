package weblogic.jms.backend;

import javax.jms.JMSException;
import weblogic.jms.dd.DDHandler;

public final class BEUOOQueueState extends BEUOOState {
   public BEUOOQueueState(BEDestinationImpl destination, DDHandler dd) {
      super(destination, dd);

      try {
         BEDestinationImpl.addPropertyFlags(destination.getKernelDestination(), "Logging", 16);
      } catch (JMSException var4) {
         throw new AssertionError(var4);
      }
   }
}
