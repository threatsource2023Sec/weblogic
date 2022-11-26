package weblogic.jms.safclient.jms;

import java.util.HashMap;
import java.util.Iterator;
import javax.jms.Connection;
import javax.jms.IllegalStateException;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import weblogic.jms.client.ContainerType;
import weblogic.jms.safclient.ClientSAFDelegate;
import weblogic.jms.safclient.jndi.Shutdownable;

public final class ConnectionFactoryImpl implements QueueConnectionFactory, TopicConnectionFactory, Shutdownable {
   private String name;
   private String defaultDeliveryMode = "Persistent";
   private String defaultTimeToDeliver = "0";
   private long defaultTimeToLive = 0L;
   private int defaultPriority = 4;
   private long sendTimeout = 10L;
   private int defaultCompressionThreshold = Integer.MAX_VALUE;
   private String defaultUnitOfOrder = null;
   private String clientID = null;
   private boolean attach = false;
   private ClientSAFDelegate root;
   private HashMap connections = new HashMap();
   private int currentConnectionId;

   public ConnectionFactoryImpl(String paramName, ClientSAFDelegate paramRoot) {
      this.name = paramName;
      this.root = paramRoot;
   }

   public Connection createConnection() throws JMSException {
      this.checkClosed();
      Integer connectionID = new Integer(this.currentConnectionId++);
      ConnectionImpl retVal = new ConnectionImpl(this, connectionID);
      synchronized(this.connections) {
         this.connections.put(connectionID, retVal);
      }

      if (this.clientID != null) {
         retVal.setClientID(this.clientID);
      }

      return retVal;
   }

   public Connection createConnection(String s, String s1) throws JMSException {
      return this.createConnection();
   }

   public TopicConnection createTopicConnection() throws JMSException {
      return (TopicConnection)this.createConnection();
   }

   public TopicConnection createTopicConnection(String s, String s1) throws JMSException {
      return (TopicConnection)this.createConnection();
   }

   public QueueConnection createQueueConnection() throws JMSException {
      return (QueueConnection)this.createConnection();
   }

   public QueueConnection createQueueConnection(String s, String s1) throws JMSException {
      return (QueueConnection)this.createConnection();
   }

   void connectionClosed(int id) {
      synchronized(this.connections) {
         this.connections.remove(new Integer(id));
      }
   }

   private void checkClosed() throws JMSException {
      if (!this.root.isOpened()) {
         throw new IllegalStateException("The client SAF system is closed");
      }
   }

   public void setJNDIName(String paramJNDIName) {
   }

   public void setLocalJNDIName(String paramJNDIName) {
   }

   public void setDefaultDeliveryMode(String paramMode) {
      this.defaultDeliveryMode = paramMode;
   }

   String getDefaultDeliveryMode() {
      return this.defaultDeliveryMode;
   }

   public void setDefaultTimeToDeliver(String paramTTD) {
      this.defaultTimeToDeliver = paramTTD;
   }

   String getDefaultTimeToDeliver() {
      return this.defaultTimeToDeliver;
   }

   public void setDefaultTimeToLive(long paramTTL) {
      this.defaultTimeToLive = paramTTL;
   }

   long getDefaultTimeToLive() {
      return this.defaultTimeToLive;
   }

   public void setDefaultPriority(int paramPriority) {
      this.defaultPriority = paramPriority;
   }

   int getDefaultPriority() {
      return this.defaultPriority;
   }

   public void setDefaultRedeliveryDelay(long paramRD) {
   }

   public void setSendTimeout(long paramST) {
      this.sendTimeout = paramST;
   }

   long getSendTimeout() {
      return this.sendTimeout;
   }

   public void setDefaultCompressionThreshold(int paramDCT) {
      this.defaultCompressionThreshold = paramDCT;
   }

   int getDefaultCompressionThreshold() {
      return this.defaultCompressionThreshold;
   }

   public void setDefaultUnitOfOrder(String paramUOO) {
      this.defaultUnitOfOrder = paramUOO;
   }

   String getDefaultUnitOfOrder() {
      return this.defaultUnitOfOrder;
   }

   public void setClientId(String paramClientID) {
      this.clientID = paramClientID;
   }

   public void setAcknowledgePolicy(String paramAckP) {
   }

   public void setAllowCloseInOnMessage(boolean paramCIOM) {
   }

   public void setMessagesMaximum(int paramMessagesMaximum) {
   }

   public void setMulticastOverrunPolicy(String paramOverrun) {
   }

   public void setSynchronousPrefetchMode(String paramMode) {
   }

   public void setXAConnectionFactoryEnabled(boolean paramXAEnabled) {
   }

   public void setAttachJMSXUserId(boolean paramAttach) {
      this.attach = paramAttach;
   }

   boolean getAttachJMSXUserId() {
      return this.attach;
   }

   ClientSAFDelegate getRoot() {
      return this.root;
   }

   public void shutdown(JMSException reason) {
      HashMap connectionsClone;
      synchronized(this.connections) {
         connectionsClone = (HashMap)this.connections.clone();
      }

      Iterator it = connectionsClone.keySet().iterator();

      while(it.hasNext()) {
         ConnectionImpl connection = (ConnectionImpl)connectionsClone.get(it.next());
         connection.preClose(reason);

         try {
            connection.close();
         } catch (JMSException var6) {
         }
      }

   }

   public String toString() {
      return "ConnectionFactory(" + this.name + "," + System.identityHashCode(this) + ")";
   }

   public JMSContext createContext() {
      return new JMSContextImpl(this, getContainerType());
   }

   public JMSContext createContext(String userName, String password) {
      return new JMSContextImpl(this, getContainerType(), userName, password);
   }

   public JMSContext createContext(String userName, String password, int sessionMode) {
      return new JMSContextImpl(this, getContainerType(), userName, password, sessionMode);
   }

   public JMSContext createContext(int sessionMode) {
      return new JMSContextImpl(this, getContainerType(), sessionMode);
   }

   protected static ContainerType getContainerType() {
      return ContainerType.JavaSE;
   }
}
