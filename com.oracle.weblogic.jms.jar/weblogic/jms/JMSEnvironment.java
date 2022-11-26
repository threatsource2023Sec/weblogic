package weblogic.jms;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.UnmarshalException;
import java.security.SecureRandom;
import javax.jms.ConnectionConsumer;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.naming.Context;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.jms.common.JMSID;
import weblogic.jms.dispatcher.AccessDispatcherManager;
import weblogic.jms.dispatcher.ClientDispatcherManager;
import weblogic.jms.dispatcher.DispatcherPartitionContext;
import weblogic.jms.dispatcher.InvocableManagerDelegate;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.JMSDispatcherManager;
import weblogic.jndi.internal.NamingNode;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherOneWay;
import weblogic.messaging.dispatcher.DispatcherRemote;
import weblogic.messaging.dispatcher.Request;

public abstract class JMSEnvironment implements AccessDispatcherManager {
   private static final String PREFIX_CLIENT_NAME = "weblogic.jms.C:";
   private static final JMSEnvironment JMS_ENVIRONMENT;
   private static final JMSDispatcherManager JMS_DISPATCHER_MANAGER = JMSDispatcherManager.getRawSingleton();

   public static JMSEnvironment getJMSEnvironment() {
      return JMS_ENVIRONMENT;
   }

   public abstract boolean isThinClient();

   public abstract boolean isServer();

   public abstract ConnectionConsumer createConnectionConsumer(Destination var1, String var2, ServerSessionPool var3, int var4, JMSDispatcher var5, JMSID var6) throws JMSException;

   public abstract Context getLocalJNDIContext();

   public abstract void pushLocalJNDIContext(Context var1);

   public abstract void popLocalJNDIContext();

   public abstract Request createFEConnectionConsumerCloseRequest() throws IOException;

   public abstract Request createFEConnectionConsumerCreateRequest() throws IOException;

   public abstract Request createFEServerSessionPoolCloseRequest() throws IOException;

   public abstract Request createFEServerSessionPoolCreateRequest() throws IOException;

   public abstract String getValueFromWallet(String var1, String var2) throws JMSException;

   public abstract DispatcherId getLocalDispatcherId();

   public abstract void createJmsJavaOid();

   public void cleanupDispatcherRemote(DispatcherRemote dispatcherRemote, DispatcherOneWay dispatcherOneWay) {
   }

   public abstract String getFullJNDINodeName(NamingNode var1);

   public static String generateClientDispatcherName() {
      return "weblogic.jms.C:" + getHostAddress() + ":" + Long.toString(System.currentTimeMillis() & 65535L, 36) + ":" + Long.toString(generateRandomLong(), 36);
   }

   protected static DispatcherId clientDispatcherId() {
      return new DispatcherId(generateClientDispatcherName(), (String)null);
   }

   private static String getHostAddress() {
      try {
         InetAddress ia = InetAddress.getLocalHost();
         return ia.getHostName();
      } catch (UnknownHostException var1) {
         return "UNKNOWN";
      }
   }

   private static long generateRandomLong() {
      SecureRandom rnd = new SecureRandom();
      return rnd.nextLong();
   }

   public JMSDispatcher findDispatcherByPartitionIdUnmarshalException(String partitionId) throws UnmarshalException {
      return JMS_DISPATCHER_MANAGER.findDispatcherByPartitionIdUnmarshalException(partitionId);
   }

   public DispatcherPartitionContext lookupDispatcherPartitionContextById(String partitionId) {
      return JMS_DISPATCHER_MANAGER.lookupDispatcherPartitionContextById(partitionId);
   }

   public DispatcherPartitionContext findDispatcherPartitionContextJMSException() throws JMSException {
      return JMS_DISPATCHER_MANAGER.findDispatcherPartitionContextJMSException();
   }

   public DispatcherPartitionContext findDispatcherPartitionContextJMSException(String partitionId) throws JMSException {
      return JMS_DISPATCHER_MANAGER.findDispatcherPartitionContextJMSException(partitionId);
   }

   public DispatcherPartitionContext findDispatcherPartitionContextDispatcherException(String partitionId) throws DispatcherException {
      return JMS_DISPATCHER_MANAGER.findDispatcherPartitionContextDispatcherException(partitionId);
   }

   public DispatcherPartitionContext lookupDispatcherPartitionContextByName(String partitionName) {
      return JMS_DISPATCHER_MANAGER.lookupDispatcherPartitionContextByName(partitionName);
   }

   static {
      JMSEnvironment singleton = null;

      try {
         singleton = (JMSEnvironment)Class.forName("weblogic.jms.WLSJMSEnvironmentImpl").newInstance();
      } catch (Exception var4) {
         try {
            singleton = (JMSEnvironment)Class.forName("weblogic.jms.ClientJMSEnvironmentImpl").newInstance();
         } catch (Exception var3) {
            throw new IllegalArgumentException(var3.toString());
         }
      }

      JMS_ENVIRONMENT = singleton;
      if (!JMS_ENVIRONMENT.isServer()) {
         ClientDispatcherManager cdm = new ClientDispatcherManager(JMS_DISPATCHER_MANAGER, JMS_ENVIRONMENT);
         JMS_DISPATCHER_MANAGER.createDispatcherPartitionContext("0", "DOMAIN", false, cdm, InvocableManagerDelegate.delegate, (ComponentInvocationContext)null);
         cdm.init();
      }

   }
}
