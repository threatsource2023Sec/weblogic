package weblogic.jms.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSPushExceptionRequest;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.messaging.dispatcher.InvocableMonitor;

public final class FrontEnd {
   private final String mbeanName;
   private final HashMap connectionFactories = new HashMap();
   private long connectionFactoriesCurrentCount;
   private long connectionFactoriesHighCount;
   private long connectionFactoriesTotalCount;
   private final HashMap beDestinationTables = new HashMap();
   public static final String JMS_TEMP_DESTINATION_FTY_JNDI = "weblogic.jms.TempDestinationFactory";
   private JMSService jmsService;
   private final JMSServerId frontEndId;
   private final InvocableMonitor invocableMonitor;
   private final SAFReplyHandler safReplyHandler;
   private int state = 0;

   public FrontEnd(JMSService jmsService) {
      this.jmsService = jmsService;
      this.mbeanName = jmsService.getMbeanName();
      this.frontEndId = jmsService.getNextServerId();
      this.invocableMonitor = new InvocableMonitor(jmsService.getInvocableMonitor());
      this.safReplyHandler = SAFReplyHandler.getSAFReplyHandler();
      this.state = 1;
   }

   public String getMbeanName() {
      return this.mbeanName;
   }

   public JMSService getService() {
      return this.jmsService;
   }

   public void shutdownConnectionsForResourceGroup(ResourceGroupMBean rgmbean, boolean force) {
      if (rgmbean == null) {
         throw new IllegalArgumentException("ResourceGroupMBean passed in is null");
      } else {
         List cleanupcfs = new ArrayList();
         List cleanupconns = new ArrayList();
         int allcfCount;
         int allconnCount;
         synchronized(this) {
            if ((this.state & 24) != 0) {
               return;
            }

            allcfCount = this.connectionFactories.size();
            if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
               Iterator itr = this.connectionFactories.values().iterator();

               while(itr.hasNext()) {
                  FEConnectionFactory cf = (FEConnectionFactory)itr.next();
                  if (cf.isInResourceGroup(rgmbean)) {
                     JMSDebug.JMSFrontEnd.debug("FrontEnd found ConnectionFactory@" + cf.hashCode() + "[" + cf.getName() + ", " + cf.getJNDIName() + "] in scope of  ResourceGroup " + rgmbean + " that is shutdown");
                  }
               }
            }

            Map connmap = this.getCloneOfConnectionsMap();
            allconnCount = connmap.size();
            Iterator itr = connmap.values().iterator();

            while(itr.hasNext()) {
               FEConnection conn = (FEConnection)itr.next();
               FEConnectionFactory cf = conn.getConnectionFactory();
               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("FrontEnd check scope for Connection@" + conn.hashCode() + "[" + conn.getJMSID() + "]CF@" + cf.hashCode() + "[" + cf.getName() + ", " + cf.getJNDIName() + "] on ResourceGroup " + rgmbean + " shutdown");
               }

               if (cf.isInResourceGroup(rgmbean)) {
                  cf.markShuttingDown();

                  try {
                     cf.suspend();
                  } catch (Throwable var20) {
                  }

                  cleanupcfs.add(cf);
                  conn.markShuttingDown();
                  cleanupconns.add(conn);
               }
            }
         }

         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("FrontEnd cleaning up " + cleanupconns.size() + "(" + allconnCount + ") Connections [" + cleanupconns + "] in " + cleanupcfs.size() + "(" + allcfCount + ") ConnectionFactories [" + cleanupcfs + "] on ResourceGroup " + rgmbean + " shutdown");
         }

         Iterator var7 = cleanupconns.iterator();

         FEConnection conn;
         while(var7.hasNext()) {
            conn = (FEConnection)var7.next();

            try {
               conn.stop();
            } catch (Throwable var19) {
            }
         }

         var7 = cleanupcfs.iterator();

         while(var7.hasNext()) {
            FEConnectionFactory cf = (FEConnectionFactory)var7.next();

            try {
               if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
                  JMSDebug.JMSFrontEnd.debug("FrontEnd unbind ConnectionFactory@" + cf.hashCode() + "[" + cf.getName() + ", " + cf.getJNDIName() + "] on ResourceGroup " + rgmbean + " shutdown");
               }

               cf.unbind();
               synchronized(this) {
                  this.connectionFactoryRemove(cf);
               }
            } catch (Throwable var18) {
            }

            try {
               cf.waitForInvocablesCompletionForResourceGroup(force);
            } catch (Throwable var16) {
            }
         }

         var7 = cleanupconns.iterator();

         while(var7.hasNext()) {
            conn = (FEConnection)var7.next();

            try {
               this.pushExceptionAndConnectionClose(conn, "ResourceGroup " + rgmbean);
            } catch (Throwable var15) {
            }
         }

      }
   }

   public synchronized void markShuttingDown() {
      if ((this.state & 24) == 0) {
         this.state = 8;
         Iterator iterator = this.connectionFactories.values().iterator();

         while(iterator.hasNext()) {
            ((FEConnectionFactory)iterator.next()).markShuttingDown();
         }

         synchronized(this) {
            iterator = this.getCloneOfConnectionsMap().values().iterator();
         }

         while(iterator.hasNext()) {
            ((FEConnection)iterator.next()).markShuttingDown();
         }

      }
   }

   private synchronized void checkShutdown() throws JMSException {
      if ((this.state & 24) != 0) {
         throw new weblogic.jms.common.JMSException("JMS Server is shutdown or suspended");
      }
   }

   synchronized void checkShutdownOrSuspended() throws JMSException {
      if ((this.state & 27) != 0) {
         throw new weblogic.jms.common.JMSException("JMS server is shutdown or suspended");
      }
   }

   public synchronized void markSuspending() {
      if ((this.state & 27) == 0) {
         this.state = 2;
         Iterator iterator = this.connectionFactories.values().iterator();

         while(iterator.hasNext()) {
            ((FEConnectionFactory)iterator.next()).markSuspending();
         }

         synchronized(this) {
            iterator = this.getCloneOfConnectionsMap().values().iterator();
         }

         while(iterator.hasNext()) {
            ((FEConnection)iterator.next()).markSuspending();
         }

      }
   }

   public void prepareForSuspend(boolean force) {
      Iterator iterator;
      synchronized(this) {
         if ((this.state & 24) != 0) {
            return;
         }

         if ((this.state & 2) == 0) {
            this.markSuspending();
         }

         iterator = ((HashMap)this.connectionFactories.clone()).keySet().iterator();
      }

      while(iterator.hasNext()) {
         try {
            String name = (String)iterator.next();
            FEConnectionFactory connectionFactory = (FEConnectionFactory)this.connectionFactories.get(name);
            connectionFactory.suspend();
         } catch (Throwable var8) {
         }
      }

      if (!force) {
         synchronized(this) {
            iterator = this.getCloneOfConnectionsMap().values().iterator();
         }

         while(iterator.hasNext()) {
            try {
               FEConnection connection = (FEConnection)iterator.next();
               connection.stop();
            } catch (Throwable var6) {
            }
         }
      }

   }

   public void suspend(boolean force) throws Exception {
      boolean hasNotBeenSuspending = false;
      Throwable firstException = null;
      synchronized(this) {
         if ((this.state & 24) != 0) {
            return;
         }

         if ((this.state & 2) == 0) {
            hasNotBeenSuspending = true;
            this.markSuspending();
         }

         if (force) {
            this.invocableMonitor.forceInvocablesCompletion();
         }
      }

      boolean var19 = false;

      try {
         var19 = true;
         if (hasNotBeenSuspending) {
            try {
               this.prepareForSuspend(force);
            } catch (Throwable var24) {
               if (firstException == null) {
                  firstException = var24;
               }
            }
         }

         if (!force && hasNotBeenSuspending) {
            this.invocableMonitor.waitForInvocablesCompletion();
         }

         Iterator iterator;
         synchronized(this) {
            iterator = this.getCloneOfConnectionsMap().values().iterator();
         }

         while(true) {
            if (!iterator.hasNext()) {
               var19 = false;
               break;
            }

            try {
               FEConnection connection = (FEConnection)iterator.next();
               connection.normalClose();
            } catch (Throwable var23) {
               if (firstException == null) {
                  firstException = var23;
               }
            }
         }
      } finally {
         if (var19) {
            synchronized(this) {
               this.state = 1;
            }

            if (firstException != null) {
               if (firstException instanceof Exception) {
                  throw (Exception)firstException;
               }

               throw new weblogic.jms.common.JMSException("Error occurred in suspending JMS Service", firstException);
            }

         }
      }

      synchronized(this) {
         this.state = 1;
      }

      if (firstException != null) {
         if (firstException instanceof Exception) {
            throw (Exception)firstException;
         } else {
            throw new weblogic.jms.common.JMSException("Error occurred in suspending JMS Service", firstException);
         }
      }
   }

   public void resume() throws JMSException {
      Iterator iterator;
      synchronized(this) {
         if ((this.state & 4) != 0) {
            return;
         }

         if ((this.state & 1) == 0) {
            throw new JMSException("Failed to start JMS connection factories: wrong state");
         }

         iterator = ((HashMap)this.connectionFactories.clone()).keySet().iterator();
      }

      while(iterator.hasNext()) {
         String name = (String)iterator.next();
         FEConnectionFactory connectionFactory = (FEConnectionFactory)this.connectionFactories.get(name);
         String jndiName = connectionFactory.getJNDIName();
         if (connectionFactory != null) {
            try {
               connectionFactory.bind();
               if (connectionFactory.isDefaultConnectionFactory()) {
                  JMSLogger.logDefaultCFactoryDeployed(name, jndiName);
               } else {
                  JMSLogger.logCFactoryDeployed(name);
               }
            } catch (JMSException var9) {
               if (connectionFactory.isDefaultConnectionFactory()) {
                  JMSLogger.logErrorBindDefaultCF(name, jndiName, var9);
               } else {
                  JMSLogger.logErrorBindCF(name, var9);
               }

               throw var9;
            }
         }
      }

      synchronized(this) {
         this.state = 4;
      }
   }

   public void shutdown() {
      this.shutdown(false);
   }

   public void shutdownPartitionWithNotification() {
      this.shutdown(true);
   }

   private void shutdown(boolean shouldNotifyClientDueToPartitionShutdown) {
      Iterator iterator;
      synchronized(this) {
         if ((this.state & 16) != 0) {
            return;
         }

         if ((this.state & 8) == 0) {
            this.markShuttingDown();
         }

         iterator = ((HashMap)this.connectionFactories.clone()).keySet().iterator();
      }

      while(iterator.hasNext()) {
         try {
            String name = (String)iterator.next();
            FEConnectionFactory connectionFactory = (FEConnectionFactory)this.connectionFactories.get(name);
            connectionFactory.unbind();
            synchronized(this) {
               this.connectionFactoryRemove(connectionFactory);
            }
         } catch (Throwable var13) {
         }
      }

      synchronized(this) {
         iterator = this.getCloneOfConnectionsMap().values().iterator();
      }

      while(iterator.hasNext()) {
         try {
            FEConnection connection = (FEConnection)iterator.next();
            if (!shouldNotifyClientDueToPartitionShutdown) {
               connection.normalClose();
            } else {
               this.pushExceptionAndConnectionClose(connection, "Partition " + this.getService().getPartitionName());
            }
         } catch (Throwable var10) {
         }
      }

      synchronized(this) {
         this.state = 16;
      }
   }

   public synchronized void connectionFactoryAdd(FEConnectionFactory connectionFactory) throws JMSException {
      this.checkShutdown();
      String name = connectionFactory.getName();
      if (this.connectionFactories.put(name, connectionFactory) == null) {
         ++this.connectionFactoriesTotalCount;
         if (++this.connectionFactoriesCurrentCount > this.connectionFactoriesHighCount) {
            this.connectionFactoriesHighCount = this.connectionFactoriesCurrentCount;
         }

      }
   }

   public synchronized void connectionFactoryRemove(FEConnectionFactory connectionFactory) {
      String name = connectionFactory.getName();
      if (this.connectionFactories.remove(name) != null) {
         --this.connectionFactoriesCurrentCount;
      }
   }

   public synchronized FEConnectionFactory connectionFactoryFind(String name) {
      return (FEConnectionFactory)this.connectionFactories.get(name);
   }

   public FEConnectionFactory[] getConnectionFactories() {
      return (FEConnectionFactory[])((FEConnectionFactory[])this.connectionFactories.values().toArray(new FEConnectionFactory[this.connectionFactories.size()]));
   }

   synchronized void addBackEndDestination(JMSServerId backEndId, String name, DestinationImpl destination, FEConnection feConnection) {
   }

   public synchronized void removeBackEndDestination(DestinationImpl destination) {
      HashMap beDestinationTable = (HashMap)this.beDestinationTables.get(destination.getBackEndId());
      if (beDestinationTable != null) {
         synchronized(beDestinationTable) {
            beDestinationTable.remove(destination.getName());
         }
      }
   }

   synchronized DestinationImpl findBackEndDestination(JMSServerId backEndId, String name) {
      HashMap beDestinationTable = (HashMap)this.beDestinationTables.get(backEndId);
      if (beDestinationTable == null) {
         return null;
      } else {
         synchronized(beDestinationTable) {
            return (DestinationImpl)beDestinationTable.get(name);
         }
      }
   }

   InvocableMonitor getInvocableMonitor() {
      return this.invocableMonitor;
   }

   SAFReplyHandler getSAFReplyHandler() {
      return this.safReplyHandler;
   }

   private Map getCloneOfConnectionsMap() {
      Map conns = this.getService().getInvocableManagerDelegate().getInvocableMap(7);
      return (Map)((HashMap)conns).clone();
   }

   public JMSServerId getFrontEndId() {
      return this.frontEndId;
   }

   private void pushExceptionAndConnectionClose(FEConnection conn, String reason) throws JMSException {
      if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
         JMSDebug.JMSFrontEnd.debug("FrontEnd close Connection@" + conn.hashCode() + "[" + conn.getJMSID() + "]" + conn + " on " + reason + " shutdown");
      }

      weblogic.jms.common.JMSException exception = new weblogic.jms.common.JMSException(JMSExceptionLogger.getPushExceptionOnConnectionCloseLoggable(reason));
      JMSDispatcher jmsDispatcher = conn.getClientDispatcher();
      conn.close(false, exception);
      JMSPushExceptionRequest request = new JMSPushExceptionRequest(3, conn.getConnectionId(), exception);

      try {
         JMSServerUtilities.anonDispatchNoReply(request, jmsDispatcher);
      } catch (Exception var7) {
         if (JMSDebug.JMSFrontEnd.isDebugEnabled()) {
            JMSDebug.JMSFrontEnd.debug("Exception in pushing connection [" + conn.getJMSID() + "] exception to client for " + reason, var7);
         }
      }

   }
}
