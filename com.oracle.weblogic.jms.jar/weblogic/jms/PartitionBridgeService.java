package weblogic.jms;

import java.security.AccessController;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationListener;
import javax.naming.NamingException;
import javax.naming.event.EventContext;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingExceptionEvent;
import javax.naming.event.ObjectChangeListener;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.bridge.internal.BridgeDebug;
import weblogic.jms.bridge.internal.MessagingBridge;
import weblogic.jms.bridge.internal.MessagingBridgeException;
import weblogic.jndi.Environment;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.MessagingBridgeMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ConnectorComponentRuntimeMBean;
import weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean;
import weblogic.management.runtime.ConnectorServiceRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public final class PartitionBridgeService {
   private static final int STATE_INITIALIZING = 0;
   private static final int STATE_SUSPENDING = 1;
   private static final int STATE_SUSPENDED = 2;
   private static final int STATE_STARTED = 4;
   private static final int STATE_SHUTTING_DOWN = 8;
   private static final int STATE_CLOSED = 16;
   public static final String ADAPTER_JNDI_OLD_XA = "eis.jms.WLSConnectionFactoryJNDIXA";
   public static final String ADAPTER_JNDI_OLD_NOTX = "eis.jms.WLSConnectionFactoryJNDINoTX";
   public static final String ADAPTER_JNDI_INTERNAL_XA = "eis.jms.internal.WLSConnectionFactoryJNDIXA";
   public static final String ADAPTER_JNDI_INTERNAL_NOTX = "eis.jms.internal.WLSConnectionFactoryJNDINoTX";
   private static final String BRIDGE_WORK_MANAGER_NAME = "weblogic.jms.MessagingBridge";
   private int state = 0;
   private boolean initialized;
   private final HashMap bridges;
   private long bridgesHighCount;
   private long bridgesTotalCount;
   private final HashSet adapterMBeans;
   private ServerMBean serverMBean;
   private String domainName;
   private final HashMap registrations;
   private WorkManager workManager;
   private EventContext src;
   private ComponentInvocationContext cic;
   final BridgeDebug bridgeDebug = new BridgeDebug();

   public PartitionBridgeService(ComponentInvocationContext cic) throws ServiceFailureException {
      this.cic = cic;
      this.adapterMBeans = new HashSet();
      this.bridges = new HashMap();
      this.registrations = new HashMap();
   }

   public String getPartitionName() {
      return JMSService.getSafePartitionKey(this.cic);
   }

   private void initialize() throws ServiceFailureException {
      if ((this.state & 2) == 0) {
         try {
            Environment env = new Environment();
            env.setCreateIntermediateContexts(true);
            this.src = (EventContext)env.getInitialContext();
         } catch (Exception var4) {
            throw new ServiceFailureException(var4);
         }

         synchronized(this) {
            this.state = 2;
         }

         if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
            BridgeDebug.MessagingBridgeStartup.debug("Bridge service is initialized for partition [" + this.getPartitionName() + "]");
         }

      }
   }

   void stop(boolean force) throws ServiceFailureException {
      this.suspend(force);
      this.shutdown();
   }

   void stopWithCIC(boolean force) throws ServiceFailureException {
      ManagedInvocationContext mic = JMSCICHelper.pushJMSCIC(this.cic);
      Throwable var3 = null;

      try {
         this.stop(force);
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   public void start() throws ServiceFailureException {
      this.initialize();
      Iterator itr;
      synchronized(this) {
         if ((this.state & 4) != 0) {
            return;
         }

         if ((this.state & 16) != 0) {
            BridgeLogger.logBridgeFailedInit();
            return;
         }

         this.state = 4;
         itr = ((HashMap)this.bridges.clone()).values().iterator();
      }

      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("Start bridge service[" + this.state + "] for partition [" + this.getPartitionName() + "]");
      }

      while(itr.hasNext()) {
         MessagingBridge bridge = (MessagingBridge)itr.next();

         try {
            bridge.resume();
         } catch (Exception var4) {
            BridgeLogger.logErrorStartBridge(bridge.getName(), var4);
         }
      }

   }

   private void suspend(boolean force) {
      Iterator itr;
      synchronized(this) {
         if ((this.state & 25) != 0) {
            return;
         }

         this.state = 1;
         itr = ((HashMap)this.bridges.clone()).values().iterator();
      }

      while(true) {
         boolean var13 = false;

         try {
            var13 = true;
            if (!itr.hasNext()) {
               var13 = false;
               break;
            }

            MessagingBridge bridge = (MessagingBridge)itr.next();
            bridge.suspend(force);
         } finally {
            if (var13) {
               synchronized(this) {
                  this.state = 2;
               }
            }
         }
      }

      synchronized(this) {
         this.state = 2;
      }
   }

   public synchronized boolean isShutdown() {
      return (this.state & 24) != 0;
   }

   public synchronized void checkShutdown() throws MessagingBridgeException {
      if (this.isShutdown()) {
         throw new MessagingBridgeException("Messaging Bridge Service has been shutdown for partition [" + this.getPartitionName() + "]");
      }
   }

   private void shutdown() {
      boolean var19 = false;

      label190: {
         try {
            var19 = true;
            Iterator unlockedShutdownItr;
            synchronized(this) {
               if ((this.state & 24) != 0) {
                  var19 = false;
                  break label190;
               }

               this.state = 8;
               Iterator markItr = this.bridges.values().iterator();
               unlockedShutdownItr = ((HashMap)this.bridges.clone()).values().iterator();

               while(markItr.hasNext()) {
                  ((MessagingBridge)markItr.next()).markShuttingDown();
               }
            }

            while(unlockedShutdownItr.hasNext()) {
               try {
                  ((MessagingBridge)unlockedShutdownItr.next()).shutdown();
               } catch (Throwable var24) {
               }
            }

            synchronized(this) {
               this.bridges.clear();
               this.adapterMBeans.clear();
            }

            if (this.src != null) {
               try {
                  this.src.close();
                  var19 = false;
               } catch (Exception var25) {
                  if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
                     BridgeDebug.MessagingBridgeStartup.debug("Exception on closing EventContext when shutdown bridge service: " + var25, var25);
                     var19 = false;
                  } else {
                     var19 = false;
                  }
               }
            } else {
               var19 = false;
            }
         } finally {
            if (var19) {
               synchronized(this) {
                  this.state = 16;
               }

               BridgeLogger.logBridgeShutdown();
            }
         }

         synchronized(this) {
            this.state = 16;
         }

         BridgeLogger.logBridgeShutdown();
         return;
      }

      synchronized(this) {
         this.state = 16;
      }

      BridgeLogger.logBridgeShutdown();
   }

   public String getDomainName() {
      return this.domainName;
   }

   public synchronized void addMessagingBridge(String name, MessagingBridge bridge) throws MessagingBridgeException {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("About to add bridge:" + bridge.getName() + " for partition [" + this.getPartitionName() + "]");
      }

      this.checkShutdown();
      if (this.bridges.put(name, bridge) == null) {
         this.bridgesHighCount = Math.max((long)this.bridges.size(), this.bridgesHighCount);
         ++this.bridgesTotalCount;
      }

   }

   public synchronized void removeMessagingBridge(String name) {
      this.bridges.remove(name);
   }

   public MessagingBridge[] getMessagingBridges() {
      MessagingBridge[] retValue;
      Iterator it;
      synchronized(this) {
         retValue = new MessagingBridge[this.bridges.size()];
         it = ((HashMap)this.bridges.clone()).values().iterator();
      }

      for(int i = 0; it.hasNext(); retValue[i++] = (MessagingBridge)it.next()) {
      }

      return retValue;
   }

   public synchronized MessagingBridge findBridge(String name) {
      if (BridgeDebug.MessagingBridgeStartup.isDebugEnabled()) {
         BridgeDebug.MessagingBridgeStartup.debug("About to find bridge:" + name + " for partition [" + this.getPartitionName() + "]");
      }

      return (MessagingBridge)this.bridges.get(name);
   }

   private void addAdapter(String adapterJndiName) {
      synchronized(this.adapterMBeans) {
         this.adapterMBeans.add(adapterJndiName);
         this.logIgnoredAdapter(adapterJndiName);
      }

      HashMap bridgeList;
      synchronized(this.registrations) {
         bridgeList = (HashMap)this.registrations.remove(adapterJndiName);
      }

      if (bridgeList != null) {
         Iterator itr = bridgeList.values().iterator();

         while(itr.hasNext()) {
            MessagingBridge bridge = (MessagingBridge)itr.next();
            synchronized(this) {
               if (this.bridges.get(bridge.getName()) != null && !this.isOldJNDIName(adapterJndiName)) {
                  bridge.resume();
               }
            }
         }

      }
   }

   private void removeAdapter(String adapterJndiName) {
      if (!this.isShutdown()) {
         Iterator itr;
         synchronized(this) {
            itr = ((HashMap)this.bridges.clone()).values().iterator();
         }

         while(true) {
            MessagingBridge bridge;
            MessagingBridgeMBean mbean;
            do {
               if (!itr.hasNext()) {
                  synchronized(this.adapterMBeans) {
                     this.adapterMBeans.remove(adapterJndiName);
                     return;
                  }
               }

               bridge = (MessagingBridge)itr.next();
               mbean = bridge.getMBean();
            } while(!seperatedBySlash(mbean.getSourceDestination().getAdapterJNDIName()).equals(adapterJndiName) && !seperatedBySlash(mbean.getTargetDestination().getAdapterJNDIName()).equals(adapterJndiName));

            if (!this.isOldJNDIName(adapterJndiName)) {
               bridge.suspend(false);
            }

            this.registerForAdapterDeployment(adapterJndiName, bridge);
         }
      }
   }

   public boolean findAdapterAndRegister(String jndiName, MessagingBridge bridge) {
      synchronized(this.adapterMBeans) {
         if (this.adapterMBeans.contains(seperatedBySlash(jndiName))) {
            return true;
         } else {
            boolean var10000;
            try {
               MessagingBridge.getContext().lookup(jndiName);
               this.adapterMBeans.add(jndiName);
               this.logIgnoredAdapter(jndiName);

               try {
                  this.src.addNamingListener(jndiName, 0, new AdapterChangeHandler());
               } catch (NamingException var6) {
                  var6.printStackTrace();
               }

               var10000 = true;
            } catch (NamingException var7) {
               this.registerForAdapterDeployment(jndiName, bridge);
               return false;
            }

            return var10000;
         }
      }
   }

   private void registerForAdapterDeployment(String jndiName, MessagingBridge bridge) {
      String newName = seperatedBySlash(jndiName);
      synchronized(this.registrations) {
         HashMap bridgeList = (HashMap)this.registrations.get(newName);
         if (bridgeList == null) {
            bridgeList = new HashMap();
            this.registrations.put(newName, bridgeList);

            try {
               this.src.addNamingListener(jndiName, 0, new AdapterChangeHandler());
            } catch (NamingException var8) {
               var8.printStackTrace();
            }
         }

         bridgeList.put(bridge.getName(), bridge);
      }
   }

   private static String seperatedBySlash(String name) {
      String newStr = "";

      for(int index = name.indexOf("."); index >= 0; index = name.indexOf(".")) {
         newStr = newStr + name.substring(0, index) + "/";
         name = name.substring(index + 1);
      }

      newStr = newStr + name;
      return newStr;
   }

   public static String seperatedByDot(String name) {
      String newStr = "";

      for(int index = name.indexOf("/"); index >= 0; index = name.indexOf("/")) {
         newStr = newStr + name.substring(0, index) + ".";
         name = name.substring(index + 1);
      }

      newStr = newStr + name;
      return newStr;
   }

   private boolean isOldXAJNDIName(String jndiName) {
      return "eis.jms.WLSConnectionFactoryJNDIXA".equals(seperatedByDot(jndiName));
   }

   private boolean isOldNOXAJNDIName(String jndiName) {
      return "eis.jms.WLSConnectionFactoryJNDINoTX".equals(seperatedByDot(jndiName));
   }

   private boolean isOldJNDIName(String jndiName) {
      return this.isOldXAJNDIName(jndiName) || this.isOldNOXAJNDIName(jndiName);
   }

   private void logIgnoredAdapter(String jndiName) {
      if (this.isOldJNDIName(jndiName)) {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
         ConnectorServiceRuntimeMBean connectorServiceRuntime;
         if (this.cic.isGlobalRuntime()) {
            connectorServiceRuntime = serverRuntime.getConnectorServiceRuntime();
         } else {
            PartitionRuntimeMBean partitionRuntime = serverRuntime.lookupPartitionRuntime(this.cic.getPartitionName());
            connectorServiceRuntime = partitionRuntime.getConnectorServiceRuntime();
         }

         ConnectorComponentRuntimeMBean[] var11 = connectorServiceRuntime.getRAs();
         int var6 = var11.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            ConnectorComponentRuntimeMBean ra = var11[var7];
            ConnectorConnectionPoolRuntimeMBean connectionPool = ra.getConnectionPool(seperatedBySlash(jndiName));
            if (connectionPool != null) {
               AppDeploymentMBean appDeploymentMBean = ra.getAppDeploymentMBean();
               if (appDeploymentMBean.getDeploymentPlan() != null) {
                  BridgeLogger.logWarningAdapterIgnored(appDeploymentMBean.getName(), seperatedByDot(jndiName), this.convertToInternalJNDIName(jndiName));
               }
            }
         }

      }
   }

   private String convertToInternalJNDIName(String jndiName) {
      if (this.isOldXAJNDIName(jndiName)) {
         return "eis.jms.internal.WLSConnectionFactoryJNDIXA";
      } else {
         return this.isOldNOXAJNDIName(jndiName) ? "eis.jms.internal.WLSConnectionFactoryJNDINoTX" : jndiName;
      }
   }

   public static void removeNotificationListener(NotificationBroadcaster mbean, NotificationListener obj) {
      try {
         mbean.removeNotificationListener(obj);
      } catch (Exception var3) {
      }

   }

   public synchronized WorkManager getWorkManager() {
      if (this.workManager == null) {
         int maxThreads = -1;
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getServer();
         if (server.getUse81StyleExecuteQueues()) {
            maxThreads = Kernel.getConfig().getMessagingBridgeThreadPoolSize();
            if (maxThreads <= 0) {
               maxThreads = 10;
            }
         }

         this.workManager = WorkManagerFactory.getInstance().findOrCreate("weblogic.jms.MessagingBridge", 1, maxThreads);
      }

      return this.workManager;
   }

   private final class AdapterChangeHandler implements ObjectChangeListener {
      private AdapterChangeHandler() {
      }

      public void objectChanged(NamingEvent evt) {
         switch (evt.getType()) {
            case 0:
               PartitionBridgeService.this.addAdapter(PartitionBridgeService.seperatedBySlash(evt.getNewBinding().getName()));
               break;
            case 1:
               PartitionBridgeService.this.removeAdapter(PartitionBridgeService.seperatedBySlash(evt.getNewBinding().getName()));
            case 2:
            case 3:
         }

      }

      public void namingExceptionThrown(NamingExceptionEvent evt) {
         System.out.println(evt.getException());
      }

      // $FF: synthetic method
      AdapterChangeHandler(Object x1) {
         this();
      }
   }
}
