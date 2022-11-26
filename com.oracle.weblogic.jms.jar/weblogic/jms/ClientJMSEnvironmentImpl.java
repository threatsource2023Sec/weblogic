package weblogic.jms;

import java.io.IOException;
import javax.jms.ConnectionConsumer;
import javax.jms.Destination;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.naming.Context;
import weblogic.jms.common.JMSID;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jndi.internal.NamingNode;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherOneWay;
import weblogic.messaging.dispatcher.DispatcherProxy;
import weblogic.messaging.dispatcher.DispatcherRemote;
import weblogic.messaging.dispatcher.DispatcherServerRef;
import weblogic.messaging.dispatcher.Request;

public class ClientJMSEnvironmentImpl extends JMSEnvironment {
   private static DispatcherId dispatcherId = clientDispatcherId();

   public boolean isThinClient() {
      return false;
   }

   public boolean isServer() {
      return false;
   }

   public ConnectionConsumer createConnectionConsumer(Destination destination, String messageSelector, ServerSessionPool serverSessionPool, int messagesMaximum, JMSDispatcher dispatcher, JMSID jmsid) throws JMSException {
      throw new IllegalStateException(JMSClientExceptionLogger.logConnectionConsumerOnClientLoggable().getMessage());
   }

   public Context getLocalJNDIContext() {
      throw new UnsupportedOperationException("Not supported on WebLogic thin client");
   }

   public void pushLocalJNDIContext(Context ctx) {
      throw new UnsupportedOperationException("Not supported on WebLogic thin client");
   }

   public void popLocalJNDIContext() {
      throw new UnsupportedOperationException("Not supported on WebLogic thin client");
   }

   public Request createFEConnectionConsumerCloseRequest() throws IOException {
      throw new IOException(JMSClientExceptionLogger.logConnectionConsumerOnClientLoggable().getMessage());
   }

   public Request createFEConnectionConsumerCreateRequest() throws IOException {
      throw new IOException(JMSClientExceptionLogger.logConnectionConsumerOnClientLoggable().getMessage());
   }

   public Request createFEServerSessionPoolCloseRequest() throws IOException {
      throw new IOException(JMSClientExceptionLogger.logConnectionConsumerOnClientLoggable().getMessage());
   }

   public Request createFEServerSessionPoolCreateRequest() throws IOException {
      throw new IOException(JMSClientExceptionLogger.logConnectionConsumerOnClientLoggable().getMessage());
   }

   public String getValueFromWallet(String walletLocation, String alias) throws JMSException {
      throw new UnsupportedOperationException("Not supported on WebLogic thin client");
   }

   public DispatcherId getLocalDispatcherId() {
      return dispatcherId;
   }

   public void createJmsJavaOid() {
      if (!this.isThinClient()) {
         DispatcherServerRef.createJmsJavaOid();
      }

   }

   public void cleanupDispatcherRemote(DispatcherRemote dispatcherRemote, DispatcherOneWay dispatcherOneWay) {
      if (dispatcherOneWay instanceof DispatcherProxy) {
         ((DispatcherProxy)dispatcherOneWay).cleanup();
      }

      if (dispatcherRemote instanceof DispatcherProxy) {
         ((DispatcherProxy)dispatcherOneWay).cleanup();
      }

   }

   public String getFullJNDINodeName(NamingNode store) {
      throw new UnsupportedOperationException("Not supported on WebLogic thin client");
   }
}
