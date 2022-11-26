package weblogic.jms.backend;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.util.HashMap;
import java.util.Iterator;
import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.ServerSession;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.naming.Context;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.client.ConnectionInternal;
import weblogic.jms.client.JMSConnectionFactory;
import weblogic.jms.client.JMSServerSessionPool;
import weblogic.jms.common.ConfigurationException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSDiagnosticImageSource;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.ServerSessionPoolHelper;
import weblogic.jms.extensions.ServerSessionPoolListener;
import weblogic.management.ManagementException;
import weblogic.management.configuration.JMSConnectionConsumerMBean;
import weblogic.management.configuration.JMSSessionPoolMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.JMSConsumerRuntimeMBean;
import weblogic.management.runtime.JMSServerRuntimeMBean;
import weblogic.management.runtime.JMSSessionPoolRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.utils.GenericBeanListener;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.store.common.PartitionNameUtils;

public final class BEServerSessionPool extends RuntimeMBeanDelegate implements BEServerSessionPoolRemote, JMSSessionPoolRuntimeMBean {
   static final long serialVersionUID = -6384515684489802143L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private JMSServerSessionPool remoteWrapper;
   private JMSID serverSessionPoolId;
   private BackEnd backEnd;
   private long serverSessionsMaximum;
   private int acknowledgeMode;
   private boolean transacted;
   private boolean createdFromMBean;
   private String listenerName;
   private Class listenerClass;
   private Constructor listenerConstructor;
   private Object[] listenerParameters;
   private Serializable clientData;
   private int waiters;
   private Connection connection;
   private ConnectionFactory connectionFactory;
   private BEServerSession firstServerSession;
   private long serverSessionsCurrent;
   private final HashMap connectionConsumers = new HashMap();
   private long connectionConsumersCurrentCount;
   private long connectionConsumersHighCount;
   private long connectionConsumersTotalCount;
   private String decoratedName;
   private JMSSessionPoolMBean mbean;
   private int state = 0;
   private boolean validConnectionFactory;
   private boolean validListener;
   private Throwable savedCreationThrowable;
   private static final HashMap sessionPoolSignatures = new HashMap();
   private static final HashMap sessionPoolAdditions = new HashMap();
   private GenericBeanListener sessionPoolListener;

   public BEServerSessionPool(String name, JMSID serverSessionPoolId, BackEnd backEnd, ConnectionFactory connectionFactory, int serverSessionsMaximum, int acknowledgeMode, boolean transacted, String listenerName, Serializable clientData) throws JMSException, ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionName(name), backEnd.getRuntimeMBean());
      this.decoratedName = name;
      this.serverSessionPoolId = serverSessionPoolId;
      this.backEnd = backEnd;
      this.connectionFactory = connectionFactory;
      this.serverSessionsMaximum = (long)serverSessionsMaximum;
      this.acknowledgeMode = acknowledgeMode;
      this.transacted = transacted;
      this.listenerName = listenerName;
      this.clientData = clientData;
      this.initialize();
   }

   public BEServerSessionPool(String name, JMSID serverSessionPoolId, BackEnd backEnd, JMSSessionPoolMBean serverSessionPoolMBean) throws JMSException, ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionName(name), backEnd.getRuntimeMBean());
      this.decoratedName = name;

      try {
         this.createdFromMBean = true;
         this.serverSessionPoolId = serverSessionPoolId;
         this.backEnd = backEnd;
         this.mbean = serverSessionPoolMBean;
         String connectionFactoryName;
         if ((connectionFactoryName = serverSessionPoolMBean.getConnectionFactory()) == null) {
            connectionFactoryName = "weblogic.jms.ConnectionFactory";
         }

         try {
            this.setConnectionFactory(backEnd.getJmsService().getCtx(), connectionFactoryName);
         } catch (ConfigurationException var10) {
            throw var10;
         }

         this.setAcknowledgeMode(serverSessionPoolMBean.getAcknowledgeMode());
         this.serverSessionsMaximum = (long)serverSessionPoolMBean.getSessionsMaximum();
         if (this.serverSessionsMaximum == -1L) {
            this.serverSessionsMaximum = 10L;
         }

         this.transacted = serverSessionPoolMBean.isTransacted();
         this.listenerName = serverSessionPoolMBean.getListenerClass();
         this.initialize();
         JMSConnectionConsumerMBean[] connectionConsumerMBeans = serverSessionPoolMBean.getJMSConnectionConsumers();

         for(int i = 0; i < connectionConsumerMBeans.length; ++i) {
            try {
               ConnectionConsumer connectionConsumer = this.connectionConsumerCreate(connectionConsumerMBeans[i]);
               this.connectionConsumerAdd(connectionConsumerMBeans[i].getName(), connectionConsumer);
            } catch (ConfigurationException var9) {
               JMSLogger.logErrorCreateCC(backEnd.getName(), connectionConsumerMBeans[i].getName(), var9);
               throw var9;
            }
         }

         this.state = 1;
      } catch (Throwable var11) {
         this.cleanup();
         this.savedCreationThrowable = var11;
         JMSLogger.logAddedSessionPoolToBeRemoved(backEnd.getName(), name, var11);
         if (JMSDebug.JMSConfig.isDebugEnabled()) {
            JMSDebug.JMSConfig.debug("Failed to start the new server session pool");
         }
      }

   }

   private void initialize() throws JMSException, ConfigurationException {
      this.setListener(this.listenerName);
      if (this.connectionFactory instanceof QueueConnectionFactory) {
         this.connection = ((QueueConnectionFactory)this.connectionFactory).createQueueConnection();
      } else if (this.connectionFactory instanceof TopicConnectionFactory) {
         this.connection = ((TopicConnectionFactory)this.connectionFactory).createTopicConnection();
      }

      this.remoteWrapper = new JMSServerSessionPool(this.backEnd.getJMSServerId(), this.serverSessionPoolId);
   }

   private void setListener(String listenerName) throws JMSException, ConfigurationException {
      try {
         label47: {
            if (listenerName != null && listenerName.length() != 0) {
               this.listenerClass = Class.forName(listenerName);
               Constructor[] constructors = this.listenerClass.getConstructors();

               for(int i = 0; i < constructors.length; ++i) {
                  Class[] parameters = constructors[i].getParameterTypes();
                  if (parameters.length == 0) {
                     this.listenerConstructor = constructors[i];
                     this.listenerParameters = new Object[0];
                  } else if (parameters.length == 1 && parameters[0].isAssignableFrom(Session.class)) {
                     this.listenerConstructor = constructors[i];
                     this.listenerParameters = new Object[1];
                     break;
                  }
               }

               if (this.listenerConstructor != null) {
                  break label47;
               }

               throw new weblogic.jms.common.JMSException("No constructor for MessageListener", new NoSuchMethodException("expected one of\n\t" + listenerName + ".<init>(), or\n\t" + listenerName + ".<init>(javax.jms.Session)"));
            }

            throw new ConfigurationException("Listener class for ServerSessionPool " + this.name + " is null");
         }
      } catch (ClassNotFoundException var5) {
         throw new ConfigurationException("Listener class, " + listenerName + ", not found");
      }

      this.validListener = true;
   }

   private void setConnectionFactory(Context context, String connectionFactoryName) throws ConfigurationException {
      if (!ServerSessionPoolHelper.isDomainCF(connectionFactoryName, ManagementService.getRuntimeAccess(kernelId).getDomain())) {
         throw new ConfigurationException("Server session pool has to use a connection factory that is configured in the same scope");
      } else {
         try {
            this.connectionFactory = (ConnectionFactory)context.lookup(connectionFactoryName);
         } catch (Throwable var4) {
            throw new ConfigurationException("ConnectionFactory " + connectionFactoryName + " for ServerSessionPool " + this.name + " not found", var4);
         }

         if (this.connectionFactory instanceof JMSConnectionFactory) {
         }

         this.validConnectionFactory = true;
      }
   }

   private void setAcknowledgeMode(String acknowledgeModeString) throws ConfigurationException {
      if (acknowledgeModeString == null) {
         this.acknowledgeMode = 1;
      } else if (acknowledgeModeString.equalsIgnoreCase("Auto")) {
         this.acknowledgeMode = 1;
      } else if (acknowledgeModeString.equalsIgnoreCase("Client")) {
         this.acknowledgeMode = 2;
      } else if (acknowledgeModeString.equalsIgnoreCase("Dups-Ok")) {
         this.acknowledgeMode = 3;
      } else {
         if (!acknowledgeModeString.equalsIgnoreCase("None")) {
            throw new ConfigurationException("Invalid acknowledgeMode for ServerSessionPool " + this.name + ", " + acknowledgeModeString);
         }

         this.acknowledgeMode = 4;
      }

   }

   void start() throws JMSException {
      this.checkShutdown();
      synchronized(this) {
         if ((this.state & 4) == 0) {
            if ((this.state & 1) != 0) {
               this.sessionPoolListener = new GenericBeanListener(this.mbean, this, sessionPoolSignatures, sessionPoolAdditions);
            }

            Iterator iter = this.connectionConsumers.values().iterator();

            while(iter.hasNext()) {
               BEConnectionConsumerCommon connectionConsumer = (BEConnectionConsumerCommon)iter.next();
               connectionConsumer.start();
            }

            this.state = 4;
         }
      }
   }

   void cleanup() {
      try {
         if (this.connection != null) {
            this.connection.close();
         }
      } catch (JMSException var2) {
      }

   }

   void shutdown() {
      Iterator iter = null;
      BEServerSession serverSession = null;
      synchronized(this) {
         if ((this.state & 16) != 0) {
            return;
         }

         if ((this.state & 8) == 0) {
            this.markShuttingDown();
         }

         iter = ((HashMap)this.connectionConsumers.clone()).values().iterator();
         serverSession = this.firstServerSession;
         this.firstServerSession = null;
      }

      if (this.sessionPoolListener != null) {
         this.sessionPoolListener.close();
      }

      try {
         PrivilegedActionUtilities.unregister(this, kernelId);
         if (this.savedCreationThrowable != null) {
            this.state = 16;
            return;
         }
      } catch (ManagementException var10) {
      }

      while(iter.hasNext()) {
         BEConnectionConsumerCommon connectionConsumer = (BEConnectionConsumerCommon)iter.next();

         try {
            connectionConsumer.close();
         } catch (JMSException var9) {
         }
      }

      BEServerSession nextSession;
      for(; serverSession != null; serverSession = nextSession) {
         nextSession = serverSession.getNext();

         try {
            serverSession.close();
         } catch (JMSException var8) {
         }
      }

      try {
         this.connection.close();
      } catch (JMSException var7) {
      }

      synchronized(this) {
         this.state = 16;
         this.connectionConsumers.clear();
         this.connectionConsumersCurrentCount = 0L;
         this.connectionConsumersHighCount = 0L;
         this.connectionConsumersTotalCount = 0L;
      }
   }

   private boolean isShutdown() {
      return (this.state & 24) != 0;
   }

   private synchronized void checkShutdown() throws JMSException {
      if (this.isShutdown()) {
         throw new weblogic.jms.common.JMSException("JMS server session pool is shutdown");
      }
   }

   synchronized void markShuttingDown() {
      if ((this.state & 16) == 0) {
         this.state = 8;
      }
   }

   public JMSID getId() {
      return this.serverSessionPoolId;
   }

   public JMSServerId getBackEndId() {
      return this.backEnd.getJMSServerId();
   }

   public BackEnd getBackEnd() {
      return this.backEnd;
   }

   public Object getRemoteWrapper() {
      return this.remoteWrapper;
   }

   public int getAcknowledgeMode() {
      return this.acknowledgeMode;
   }

   public boolean isTransacted() {
      return this.transacted;
   }

   public long getServerSessionsMaximum() {
      return this.serverSessionsMaximum;
   }

   public String getListenerName() {
      return this.listenerName;
   }

   boolean isCreatedFromMBean() {
      return this.createdFromMBean;
   }

   private ConnectionConsumer connectionConsumerCreate(JMSConnectionConsumerMBean connectionConsumerMBean) throws JMSException, ConfigurationException {
      String name = connectionConsumerMBean.getName();
      String destinationName = connectionConsumerMBean.getDestination();
      if (destinationName == null) {
         throw new ConfigurationException("Null destination for ConnectionConsumer " + name);
      } else if (!ServerSessionPoolHelper.isDomainDestination(destinationName, ManagementService.getRuntimeAccess(kernelId).getDomain())) {
         throw new ConfigurationException("Destination " + destinationName + " for ConnectionConsumer " + name + " not found at the domain level");
      } else {
         Destination destination;
         try {
            destination = (Destination)this.backEnd.getJmsService().getCtx().lookup(destinationName);
         } catch (Exception var8) {
            throw new ConfigurationException("Error finding destination " + destinationName + " for ConnectionConsumer " + name, var8);
         }

         if (destination == null) {
            throw new ConfigurationException("Destination " + destinationName + " for ConnectionConsumer " + name + " not found");
         } else {
            String messageSelector = connectionConsumerMBean.getSelector();
            int messagesMaximum = connectionConsumerMBean.getMessagesMaximum();
            if (messagesMaximum <= 0) {
               messagesMaximum = 10;
            }

            ConnectionConsumer cc;
            if (destination instanceof Queue) {
               cc = ((ConnectionInternal)this.connection).createConnectionConsumer((Queue)destination, messageSelector, (ServerSessionPool)this.getRemoteWrapper(), messagesMaximum);
               if (cc instanceof BEConnectionConsumerCommon) {
                  ((BEConnectionConsumerCommon)cc).initialize(connectionConsumerMBean);
               }

               return cc;
            } else if (destination instanceof Topic) {
               cc = ((ConnectionInternal)this.connection).createConnectionConsumer((Topic)destination, messageSelector, (ServerSessionPool)this.getRemoteWrapper(), messagesMaximum);
               if (cc instanceof BEConnectionConsumerCommon) {
                  ((BEConnectionConsumerCommon)cc).initialize(connectionConsumerMBean);
               }

               return cc;
            } else {
               throw new ConfigurationException("Invalid destination type for ConnectionConsumer " + name);
            }
         }
      }
   }

   private synchronized void connectionConsumerAdd(String name, ConnectionConsumer connectionConsumer) throws JMSException {
      this.checkShutdown();
      if (this.connectionConsumers.put(name, connectionConsumer) == null) {
         if (++this.connectionConsumersCurrentCount > this.connectionConsumersHighCount) {
            this.connectionConsumersHighCount = this.connectionConsumersCurrentCount;
         }

         ++this.connectionConsumersTotalCount;
      }

   }

   private synchronized ConnectionConsumer connectionConsumerRemove(String name) throws JMSException {
      ConnectionConsumer connectionConsumer = (ConnectionConsumer)this.connectionConsumers.remove(name);
      if (connectionConsumer == null) {
         throw new weblogic.jms.common.JMSException("ConnectionConsumer not found");
      } else {
         --this.connectionConsumersCurrentCount;
         return connectionConsumer;
      }
   }

   private synchronized BEServerSession serverSessionCreate() throws JMSException {
      Object session;
      if (this.connection instanceof QueueConnection) {
         session = ((QueueConnection)this.connection).createQueueSession(this.transacted, this.acknowledgeMode);
      } else {
         session = ((TopicConnection)this.connection).createTopicSession(this.transacted, this.acknowledgeMode);
      }

      if (this.listenerParameters.length > 0) {
         this.listenerParameters[0] = session;
      }

      try {
         MessageListener messageListener = (MessageListener)this.listenerConstructor.newInstance(this.listenerParameters);
         if (messageListener instanceof ServerSessionPoolListener) {
            ((ServerSessionPoolListener)messageListener).initialize(this.clientData);
         }

         ((Session)session).setMessageListener(messageListener);
      } catch (Exception var3) {
         throw new weblogic.jms.common.JMSException("Error instantiating message listener", var3);
      }

      return new BEServerSession(this.connection, (Session)session, this);
   }

   synchronized void serverSessionPut(BEServerSession serverSession) {
      if (this.isShutdown()) {
         try {
            serverSession.close();
         } catch (JMSException var3) {
         }
      } else {
         serverSession.setNext(this.firstServerSession);
         this.firstServerSession = serverSession;
      }

      if (this.waiters > 0) {
         this.notify();
      }

   }

   public synchronized ServerSession getServerSession(DispatcherId dispatcherId) throws JMSException {
      if (!this.backEnd.getJmsService().getLocalId().equals(dispatcherId)) {
         throw new weblogic.jms.common.JMSException("Cannot invoke getServerSession() remotely");
      } else {
         BEServerSession serverSession;
         while((serverSession = this.firstServerSession) == null) {
            this.checkShutdown();
            if (this.serverSessionsCurrent >= this.serverSessionsMaximum) {
               try {
                  ++this.waiters;
                  this.wait();
               } catch (InterruptedException var7) {
               } finally {
                  --this.waiters;
               }
            } else {
               this.firstServerSession = this.serverSessionCreate();
               ++this.serverSessionsCurrent;
            }
         }

         this.firstServerSession = serverSession.getNext();
         return serverSession;
      }
   }

   public JMSServerRuntimeMBean getJMSServer() {
      return this.backEnd.getRuntimeMBean();
   }

   public synchronized JMSConsumerRuntimeMBean[] getConnectionConsumers() {
      return null;
   }

   public synchronized long getConnectionConsumersCurrentCount() {
      return this.connectionConsumersCurrentCount;
   }

   public synchronized long getConnectionConsumersHighCount() {
      return this.connectionConsumersHighCount;
   }

   public synchronized long getConnectionConsumersTotalCount() {
      return this.connectionConsumersTotalCount;
   }

   public void setSessionsMaximum(int newMax) {
      this.serverSessionsMaximum = (long)newMax;
   }

   public void startAddConnectionConsumers(JMSConnectionConsumerMBean addedValue) throws BeanUpdateRejectedException {
      try {
         ConnectionConsumer connectionConsumer = this.connectionConsumerCreate(addedValue);
         this.connectionConsumerAdd(addedValue.getName(), connectionConsumer);
      } catch (JMSException var3) {
         throw new BeanUpdateRejectedException(var3.getMessage(), var3);
      }
   }

   public void finishAddConnectionConsumers(JMSConnectionConsumerMBean addedValue, boolean isActivate) {
      if (!isActivate) {
         try {
            ConnectionConsumer consumer = this.connectionConsumerRemove(addedValue.getName());
            consumer.close();
         } catch (JMSException var5) {
            JMSLogger.logErrorRollingBackConnectionConsumer(addedValue.getName(), var5.toString());
         }

      }
   }

   public void startRemoveConnectionConsumers(JMSConnectionConsumerMBean removedValue) throws BeanUpdateRejectedException {
   }

   public void finishRemoveConnectionConsumers(JMSConnectionConsumerMBean removedValue, boolean isActivate) {
      if (isActivate) {
         try {
            ConnectionConsumer consumer = this.connectionConsumerRemove(removedValue.getName());
            consumer.close();
         } catch (JMSException var5) {
            JMSLogger.logErrorRemovingConnectionConsumer(removedValue.getName(), var5.toString());
         }

      }
   }

   public String getDecoratedName() {
      return this.decoratedName;
   }

   public void close() throws JMSException {
   }

   public void dump(JMSDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("ServerSessionPool");
      xsw.writeAttribute("id", this.serverSessionPoolId != null ? this.serverSessionPoolId.toString() : "");
      xsw.writeAttribute("serverSessionsMaximum", String.valueOf(this.serverSessionsMaximum));
      xsw.writeAttribute("acknowledgeMode", String.valueOf(this.acknowledgeMode));
      xsw.writeAttribute("transacted", String.valueOf(this.transacted));
      xsw.writeAttribute("listenerName", this.listenerName != null ? this.listenerName : "");
      xsw.writeAttribute("listenerClassName", this.listenerClass != null ? this.listenerClass.getName() : "");
      xsw.writeAttribute("state", JMSService.getStateName(this.state));
      xsw.writeAttribute("serverSessionsCurrentCount", String.valueOf(this.serverSessionsCurrent));
      xsw.writeAttribute("connectionConsumersCurrentCount", String.valueOf(this.connectionConsumersCurrentCount));
      xsw.writeAttribute("connectionConsumersHighCount", String.valueOf(this.connectionConsumersHighCount));
      xsw.writeAttribute("connectionConsumersTotalCount", String.valueOf(this.connectionConsumersTotalCount));
      xsw.writeStartElement("ConnectionConsumers");
      HashMap tempConsumers = (HashMap)this.connectionConsumers.clone();
      Iterator it = tempConsumers.values().iterator();

      while(it.hasNext()) {
         BEConnectionConsumerImpl consumer = (BEConnectionConsumerImpl)it.next();
         consumer.dumpRef(imageSource, xsw);
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }

   static {
      sessionPoolSignatures.put("SessionsMaximum", Integer.TYPE);
      sessionPoolAdditions.put("ConnectionConsumers", JMSConnectionConsumerMBean.class);
   }
}
