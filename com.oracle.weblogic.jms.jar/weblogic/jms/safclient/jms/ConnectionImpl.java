package weblogic.jms.safclient.jms;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.IllegalStateException;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import weblogic.jms.client.ProviderInfo;
import weblogic.jms.safclient.ClientSAFDelegate;

public final class ConnectionImpl implements QueueConnection, TopicConnection {
   private ConnectionFactoryImpl connectionFactory;
   private HashMap sessions = new HashMap();
   private int currentSessionId;
   private int id;
   private boolean closed = false;
   private boolean started = false;
   private ExceptionListener listener;
   private String clientID;

   ConnectionImpl(ConnectionFactoryImpl paramConnectionFactory, int paramID) {
      this.connectionFactory = paramConnectionFactory;
      this.id = paramID;
   }

   public synchronized QueueSession createQueueSession(boolean b, int i) throws JMSException {
      return (QueueSession)this.createSession(b, i);
   }

   public ConnectionConsumer createConnectionConsumer(Queue queue, String s, ServerSessionPool serverSessionPool, int i) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   public TopicSession createTopicSession(boolean b, int i) throws JMSException {
      return (TopicSession)this.createSession(b, i);
   }

   public ConnectionConsumer createConnectionConsumer(Topic topic, String s, ServerSessionPool serverSessionPool, int i) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   public ConnectionConsumer createDurableConnectionConsumer(Topic topic, String s, String s1, ServerSessionPool serverSessionPool, int i) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   public Session createSession(boolean b, int i) throws JMSException {
      this.checkClosed();
      SessionImpl s = new SessionImpl(this, this.currentSessionId, b, i);
      synchronized(this.sessions) {
         this.sessions.put(new Integer(this.currentSessionId), s);
      }

      ++this.currentSessionId;
      return s;
   }

   public String getClientID() throws JMSException {
      this.checkClosed();
      return this.clientID;
   }

   public void setClientID(String s) throws JMSException {
      this.checkClosed();
      if (this.started) {
         throw new JMSException("ClientID cannot be set once the connection is started");
      } else {
         this.clientID = s;
      }
   }

   public ConnectionMetaData getMetaData() {
      return new MetaData();
   }

   public ExceptionListener getExceptionListener() throws JMSException {
      this.checkClosed();
      return this.listener;
   }

   public void setExceptionListener(ExceptionListener exceptionListener) throws JMSException {
      this.checkClosed();
      this.listener = exceptionListener;
   }

   public void start() throws JMSException {
      this.checkClosed();
      this.started = true;
   }

   public void stop() throws JMSException {
      this.checkClosed();
      this.started = false;
   }

   public synchronized void close() throws JMSException {
      if (!this.closed) {
         this.closed = true;
         synchronized(this.sessions) {
            Collection collection = this.sessions.values();
            Iterator it = collection.iterator();

            while(true) {
               if (!it.hasNext()) {
                  this.sessions.clear();
                  break;
               }

               Session session = (Session)it.next();
               session.close();
            }
         }

         this.connectionFactory.connectionClosed(this.id);
      }
   }

   void preClose(JMSException reason) {
      HashMap sessionsClone;
      synchronized(this.sessions) {
         sessionsClone = (HashMap)this.sessions.clone();
      }

      Iterator it = sessionsClone.values().iterator();

      while(it.hasNext()) {
         SessionImpl session = (SessionImpl)it.next();
         session.preClose(reason);
      }

      if (this.listener != null) {
         try {
            this.listener.onException(reason);
         } catch (Throwable var8) {
            Throwable cause = var8;

            for(int level = 0; cause != null; cause = cause.getCause()) {
               System.out.println("User onException listener threw an exception.  Level " + level++);
               cause.printStackTrace();
            }
         }
      }

   }

   public ConnectionConsumer createConnectionConsumer(Destination destination, String s, ServerSessionPool serverSessionPool, int i) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   void sessionClosed(int id) {
      synchronized(this.sessions) {
         this.sessions.remove(new Integer(id));
      }
   }

   private void checkClosed() throws JMSException {
      if (this.closed) {
         throw new IllegalStateException("The connection has been closed");
      }
   }

   String getDefaultTimeToDeliver() {
      return this.connectionFactory.getDefaultTimeToDeliver();
   }

   long getSendTimeout() {
      return this.connectionFactory.getSendTimeout();
   }

   String getDefaultUnitOfOrder() {
      return this.connectionFactory.getDefaultUnitOfOrder();
   }

   int getDefaultCompressionThreshold() {
      return this.connectionFactory.getDefaultCompressionThreshold();
   }

   String getDefaultDeliveryMode() {
      return this.connectionFactory.getDefaultDeliveryMode();
   }

   int getDefaultPriority() {
      return this.connectionFactory.getDefaultPriority();
   }

   long getDefaultTimeToLive() {
      return this.connectionFactory.getDefaultTimeToLive();
   }

   boolean getAttachJMSXUserId() {
      return this.connectionFactory.getAttachJMSXUserId();
   }

   ClientSAFDelegate getRoot() {
      return this.connectionFactory.getRoot();
   }

   public String toString() {
      return "ConnectionImpl(" + this.id + ")";
   }

   public Session createSession() throws JMSException {
      return this.createSession(1);
   }

   public Session createSession(int sessionMode) throws JMSException {
      return sessionMode == 0 ? this.createSession(true, 0) : this.createSession(false, sessionMode);
   }

   public ConnectionConsumer createSharedConnectionConsumer(Topic arg0, String arg1, String arg2, ServerSessionPool arg3, int arg4) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   public ConnectionConsumer createSharedDurableConnectionConsumer(Topic arg0, String arg1, String arg2, ServerSessionPool arg3, int arg4) throws JMSException {
      throw new JMSException("No consumer allowed in client SAF implementation");
   }

   private static class MetaData implements ConnectionMetaData {
      private MetaData() {
      }

      public final String getJMSVersion() {
         return ProviderInfo.getJMSVersion();
      }

      public final int getJMSMajorVersion() {
         return ProviderInfo.getJMSMajorVersion();
      }

      public final int getJMSMinorVersion() {
         return ProviderInfo.getJMSMinorVersion();
      }

      public final String getJMSProviderName() {
         return ProviderInfo.getJMSProviderName();
      }

      public final String getProviderVersion() {
         return ProviderInfo.getProviderVersion();
      }

      public final int getProviderMajorVersion() {
         return ProviderInfo.getProviderMajorVersion();
      }

      public final int getProviderMinorVersion() {
         return ProviderInfo.getProviderMinorVersion();
      }

      public Enumeration getJMSXPropertyNames() throws JMSException {
         return ProviderInfo.getJMSXPropertyNames();
      }

      // $FF: synthetic method
      MetaData(Object x0) {
         this();
      }
   }
}
