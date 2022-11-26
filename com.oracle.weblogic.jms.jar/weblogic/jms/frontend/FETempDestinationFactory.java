package weblogic.jms.frontend;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.jms.Destination;
import javax.jms.JMSException;
import weblogic.jms.backend.BETempDestinationFactoryRemote;
import weblogic.jms.common.JMSID;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.rmi.extensions.server.RemoteWrapper;

public final class FETempDestinationFactory implements RemoteWrapper {
   static final long serialVersionUID = 599643165308006354L;
   private final BETempDestinationFactoryRemote factoryRemote;

   public FETempDestinationFactory(BETempDestinationFactoryRemote factoryRemote) {
      this.factoryRemote = factoryRemote;
   }

   public Destination createTempDestination(DispatcherId feDispatcherId, JMSID connectionId, boolean connectionIsStopped, int destType, long startStopSequenceNumber, String connectionAddress, boolean isDefaultConnectionFactory, int moduleType, String moduleName) throws JMSException {
      try {
         return this.factoryRemote.createTempDestination(feDispatcherId, connectionId, connectionIsStopped, destType, startStopSequenceNumber, connectionAddress, isDefaultConnectionFactory, moduleType, moduleName);
      } catch (RemoteException var12) {
         throw new weblogic.jms.common.JMSException("Failed to create temporary destination", var12);
      }
   }

   public Remote getRemoteDelegate() {
      return this.factoryRemote;
   }
}
