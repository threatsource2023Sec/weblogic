package weblogic.jms.backend;

import javax.jms.Destination;
import javax.jms.JMSException;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSID;
import weblogic.jms.frontend.FETempDestinationFactory;
import weblogic.messaging.dispatcher.DispatcherId;

public final class BETempDestinationFactory implements BETempDestinationFactoryRemote {
   private final FETempDestinationFactory factoryWrapper = new FETempDestinationFactory(this);
   private JMSService jmsService;

   public BETempDestinationFactory(JMSService svc) {
      this.jmsService = svc;
   }

   public Destination createTempDestination(DispatcherId feDispatcherId, JMSID connectionId, boolean connectionIsStopped, int destType, long startStopSequenceNumber, String connectionAddress, boolean isDefaultConnectionFactory, int moduleType, String moduleName) throws JMSException {
      this.jmsService.checkShutdown();
      BackEndTempDestinationFactory factory = this.jmsService.getBEDeployer().nextFactory(isDefaultConnectionFactory, moduleType, moduleName);
      if (factory == null) {
         throw new weblogic.jms.common.JMSException("No server configured for temporary destinations");
      } else {
         return factory.createTempDestination(feDispatcherId, connectionId, connectionIsStopped, destType, startStopSequenceNumber, connectionAddress);
      }
   }

   public FETempDestinationFactory getFactoryWrapper() {
      return this.factoryWrapper;
   }
}
