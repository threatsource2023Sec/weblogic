package weblogic.jms.backend;

import java.security.AccessController;
import javax.jms.Destination;
import javax.jms.JMSException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public final class BackEndTempDestinationFactory {
   private final BackEnd backEnd;
   private boolean onDynamicNonUPS = false;

   BackEndTempDestinationFactory(BackEnd backEnd) {
      this.backEnd = backEnd;
      if (backEnd.isClusterTargeted()) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
         int distributionPolicy = -1;
         if (distributionPolicy == 0 && !backEnd.getShortName().endsWith("@" + server.getName())) {
            this.onDynamicNonUPS = true;
         }
      }

   }

   public boolean isOnDynamicNonUPS() {
      return this.onDynamicNonUPS;
   }

   public Destination createTempDestination(DispatcherId feDispatcherId, JMSID connectionId, boolean connectionIsStopped, int destType, long startStopSequenceNumber, String connectionAddress) throws JMSException {
      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("creating back-end Temp Destination with type" + destType);
      }

      BEDestinationImpl backEndDestination;
      if (destType == 4) {
         backEndDestination = this.backEnd.createTemporaryDestination(feDispatcherId, "Queue", connectionId, connectionIsStopped, startStopSequenceNumber, connectionAddress);
      } else {
         backEndDestination = this.backEnd.createTemporaryDestination(feDispatcherId, "Topic", connectionId, connectionIsStopped, startStopSequenceNumber, connectionAddress);
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("done calling createTemporaryDestinaition");
      }

      return backEndDestination.getDestinationImpl();
   }

   public String getJMSServerName() {
      return this.backEnd.getConfigName();
   }
}
