package weblogic.jms;

import java.io.IOException;
import java.security.AccessController;
import javax.jms.ConnectionConsumer;
import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.naming.Context;
import oracle.security.pki.OracleSecretStore;
import oracle.security.pki.OracleWallet;
import weblogic.corba.j2ee.naming.ORBHelper;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSConnectionConsumerCreateResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.frontend.FEConnectionConsumerCloseRequest;
import weblogic.jms.frontend.FEConnectionConsumerCreateRequest;
import weblogic.jms.frontend.FEServerSessionPoolCloseRequest;
import weblogic.jms.frontend.FEServerSessionPoolCreateRequest;
import weblogic.jndi.internal.BasicNamingNode;
import weblogic.jndi.internal.NamingNode;
import weblogic.kernel.KernelStatus;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherServerRef;
import weblogic.messaging.dispatcher.Request;
import weblogic.protocol.LocalServerIdentity;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class WLSJMSEnvironmentImpl extends JMSEnvironment {
   private static final boolean isServer = KernelStatus.isServer();
   private static final AuthenticatedSubject kernelIdOrNull;
   private static final DispatcherId dispatcherId;

   public boolean isThinClient() {
      return ORBHelper.isThinClient();
   }

   public boolean isServer() {
      return isServer;
   }

   private static DispatcherId initializeDispatcherId() {
      return isServer ? new DispatcherId(ManagementService.getRuntimeAccess(kernelIdOrNull).getServerName(), LocalServerIdentity.getIdentity().getPersistentIdentity().toString()) : clientDispatcherId();
   }

   public ConnectionConsumer createConnectionConsumer(Destination destination, String messageSelector, ServerSessionPool serverSessionPool, int messagesMaximum, JMSDispatcher dispatcher, JMSID jmsid) throws JMSException {
      if (!isServer) {
         throw new IllegalStateException(JMSClientExceptionLogger.logConnectionConsumerOnClientLoggable().getMessage());
      } else if (messagesMaximum != 0 && messagesMaximum >= -1) {
         if (destination == null) {
            throw new InvalidDestinationException(JMSClientExceptionLogger.logNullDestinationLoggable().getMessage());
         } else if (!(destination instanceof DestinationImpl)) {
            throw new InvalidDestinationException(JMSClientExceptionLogger.logForeignDestinationLoggable().getMessage());
         } else {
            Object response = dispatcher.dispatchSync(new FEConnectionConsumerCreateRequest(jmsid, serverSessionPool, (DestinationImpl)destination, false, messageSelector, messagesMaximum, true));
            return ((JMSConnectionConsumerCreateResponse)response).getConnectionConsumer();
         }
      } else {
         throw new weblogic.jms.common.JMSException(JMSClientExceptionLogger.logInvalidMessagesMaximumLoggable(messagesMaximum));
      }
   }

   public Context getLocalJNDIContext() {
      return JMSServerUtilities.getLocalJNDIContext();
   }

   public void pushLocalJNDIContext(Context ctx) {
      JMSServerUtilities.pushLocalJNDIContext(ctx);
   }

   public void popLocalJNDIContext() {
      JMSServerUtilities.popLocalJNDIContext();
   }

   public Request createFEConnectionConsumerCloseRequest() throws IOException {
      return new FEConnectionConsumerCloseRequest();
   }

   public Request createFEConnectionConsumerCreateRequest() throws IOException {
      return new FEConnectionConsumerCreateRequest();
   }

   public Request createFEServerSessionPoolCloseRequest() throws IOException {
      return new FEServerSessionPoolCloseRequest();
   }

   public Request createFEServerSessionPoolCreateRequest() throws IOException {
      return new FEServerSessionPoolCreateRequest();
   }

   public DispatcherId getLocalDispatcherId() {
      return dispatcherId;
   }

   public void createJmsJavaOid() {
      if (!this.isThinClient()) {
         DispatcherServerRef.createJmsJavaOid();
      }

   }

   public String getValueFromWallet(String walletLocation, String alias) throws JMSException {
      if (alias != null && alias.length() != 0) {
         try {
            OracleWallet wallet = new OracleWallet();
            wallet.open(walletLocation, (char[])null);
            OracleSecretStore store = wallet.getSecretStore();
            if (!store.containsAlias(alias)) {
               throw new JMSException("No alias " + alias + " exists in wallet under " + walletLocation);
            } else {
               return new String(store.getSecret(alias));
            }
         } catch (Exception var5) {
            throw new weblogic.jms.common.JMSException(var5);
         }
      } else {
         return null;
      }
   }

   public String getFullJNDINodeName(NamingNode store) {
      return ((BasicNamingNode)store).getNameInNamespace();
   }

   static {
      kernelIdOrNull = isServer ? (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction()) : null;
      dispatcherId = initializeDispatcherId();
   }
}
