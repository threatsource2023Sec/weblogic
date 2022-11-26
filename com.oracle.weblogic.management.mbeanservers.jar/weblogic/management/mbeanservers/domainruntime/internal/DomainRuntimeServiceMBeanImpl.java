package weblogic.management.mbeanservers.domainruntime.internal;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.jmx.JMXLogger;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.jmx.RemoteRuntimeException;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.internal.DomainServiceImpl;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditFailedException;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ManagementServiceRestricted;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.internal.DomainConfiguration;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.MigratableServiceCoordinatorRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.ContextWrap;
import weblogic.work.ExecuteThread;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class DomainRuntimeServiceMBeanImpl extends DomainServiceImpl implements DomainRuntimeServiceMBean {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXDomain");
   private static final Logger LOGGER = Logger.getLogger(DomainRuntimeServiceMBeanImpl.class.getName());
   private final MBeanServerConnectionManager connectionManager;
   private final String serverName;
   private final DomainRuntimeMBean domainRuntime;
   private static final ObjectName RUNTIME_SERVICE;
   private RuntimeServicesManager runtimeServicesManager;
   private DomainAccess domainAccess = null;
   private Set inProgressReconnects = new HashSet();
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final EditAccess edit;

   DomainRuntimeServiceMBeanImpl(MBeanServerConnectionManager connectionManager, DomainRuntimeMBean domainRuntime) {
      super("DomainRuntimeService", DomainRuntimeServiceMBean.class.getName(), (Service)null);
      this.edit = ManagementServiceRestricted.getEditAccess(kernelId);
      this.connectionManager = connectionManager;
      this.domainRuntime = domainRuntime;
      this.runtimeServicesManager = new RuntimeServicesManager();
      this.domainAccess = ManagementService.getDomainAccess(kernelId);
      connectionManager.addCallback(new MBeanServerConnectionManager.MBeanServerConnectionListener() {
         public synchronized void connect(String serverName, MBeanServerConnection connection) {
            DomainRuntimeServiceMBeanImpl.this.runtimeServicesManager.addConnection(serverName, connection);
         }

         public synchronized void disconnect(String serverName) {
            DomainRuntimeServiceMBeanImpl.this.runtimeServicesManager.removeConnection(serverName);
         }
      });
      RuntimeAccess runtime = ManagementService.getRuntimeAccess(kernelId);
      this.serverName = runtime.getServerName();
   }

   public DomainMBean getDomainConfiguration() {
      try {
         return DomainConfiguration.getInstance().getDomainMBean();
      } catch (IOException var2) {
         throw new AssertionError("IO Exception retrieving Domain Configuration", var2);
      }
   }

   public DomainMBean getDomainPending() {
      try {
         String partitionName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
         EditAccess partitionAwareEditAccess;
         if ("DOMAIN".equals(partitionName)) {
            partitionAwareEditAccess = this.edit;
         } else {
            partitionAwareEditAccess = ManagementServiceRestricted.getEditSession("default");
         }

         return partitionAwareEditAccess.getDomainBeanWithoutLock();
      } catch (EditFailedException var3) {
         throw new AssertionError("Edit Failed, GetDomainPending", var3);
      }
   }

   public ServerMBean findServerConfiguration(String serverName) {
      RuntimeServiceMBean runtimeService = this.getRuntimeServiceMBean(serverName);
      if (runtimeService == null) {
         if (debug.isDebugEnabled()) {
            debug.debug("Could not find runtime Configuration for " + serverName);
         }

         return null;
      } else {
         try {
            return runtimeService.getServerConfiguration();
         } catch (RuntimeException var4) {
            if (debug.isDebugEnabled()) {
               debug.debug("Exception getting domain configuration [" + serverName + "], reconnecting: " + var4, var4);
            }

            this.attemptReconnection(runtimeService);
            throw var4;
         }
      }
   }

   public DomainMBean findDomainConfiguration(String serverName) {
      RuntimeServiceMBean runtimeService = this.getRuntimeServiceMBean(serverName);
      if (runtimeService == null) {
         return null;
      } else {
         try {
            return runtimeService.getDomainConfiguration();
         } catch (RuntimeException var4) {
            if (debug.isDebugEnabled()) {
               debug.debug("Exception getting domain configuration[" + serverName + "]: " + var4, var4);
            }

            this.attemptReconnection(runtimeService);
            throw var4;
         }
      }
   }

   public String getServerName() {
      return this.serverName;
   }

   public DomainRuntimeMBean getDomainRuntime() {
      return this.domainRuntime;
   }

   public RuntimeMBean[] findRuntimes(DescriptorBean configurationMBean) {
      List result = new ArrayList();
      RuntimeServiceMBean[] runtimes = this.runtimeServicesManager.getRuntimeServices();

      for(int i = 0; i < runtimes.length; ++i) {
         RuntimeServiceMBean runtimeService = runtimes[i];
         RuntimeMBean runtime = null;

         try {
            runtimeService.findRuntime(configurationMBean);
         } catch (RemoteRuntimeException var8) {
            if (debug.isDebugEnabled()) {
               debug.debug("Exception finding runtimes: ", var8);
            }

            this.attemptReconnection(runtimeService);
         }

         if (runtime != null) {
            result.add(runtime);
         }
      }

      return (RuntimeMBean[])((RuntimeMBean[])result.toArray(new RuntimeMBean[result.size()]));
   }

   public RuntimeMBean findRuntime(DescriptorBean configurationMBean, String serverName) {
      RuntimeServiceMBean runtimeService = this.getRuntimeServiceMBean(serverName);
      if (runtimeService == null) {
         return null;
      } else {
         try {
            return runtimeService.findRuntime(configurationMBean);
         } catch (RemoteRuntimeException var5) {
            if (debug.isDebugEnabled()) {
               debug.debug("Exception finding runtime for config and server:", var5);
            }

            this.attemptReconnection(runtimeService);
            return null;
         }
      }
   }

   public DescriptorBean findConfiguration(RuntimeMBean runtimeMBean) {
      MBeanServerInvocationHandler stub = (MBeanServerInvocationHandler)Proxy.getInvocationHandler(runtimeMBean);
      String remoteServerName = this.connectionManager.lookupServerName(stub._getConnection());
      if (remoteServerName == null) {
         return null;
      } else {
         RuntimeServiceMBean runtimeService = this.getRuntimeServiceMBean(remoteServerName);
         if (runtimeService == null) {
            return null;
         } else {
            try {
               return runtimeService.findConfiguration(runtimeMBean);
            } catch (RemoteRuntimeException var6) {
               if (debug.isDebugEnabled()) {
                  debug.debug("Exception finding configuration: ", var6);
               }

               this.attemptReconnection(runtimeService);
               return null;
            }
         }
      }
   }

   public ServerRuntimeMBean[] getServerRuntimes() {
      List result = new ArrayList();
      RuntimeServiceMBean[] runtimes = this.runtimeServicesManager.getRuntimeServices();
      CountDownLatch latch = new CountDownLatch(runtimes.length);
      WorkManager wm = this.getWorkManager();
      RuntimeServiceMBean[] var5 = runtimes;
      int var6 = runtimes.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         RuntimeServiceMBean runtimeService = var5[var7];
         ServerRuntimeExecutor runtimeExecutor = new ServerRuntimeExecutor(runtimeService, result, latch);
         wm.schedule(new ContextWrap(runtimeExecutor));
      }

      try {
         latch.await();
      } catch (InterruptedException var10) {
      }

      return (ServerRuntimeMBean[])((ServerRuntimeMBean[])result.toArray(new ServerRuntimeMBean[result.size()]));
   }

   public ServerRuntimeMBean lookupServerRuntime(String serverName) {
      ServerRuntimeMBean result = null;
      if (serverName == null) {
         return result;
      } else {
         RuntimeServiceMBean runtimeService = this.runtimeServicesManager.get(serverName);
         if (runtimeService != null) {
            try {
               result = runtimeService.getServerRuntime();
            } catch (RemoteRuntimeException var5) {
               if (debug.isDebugEnabled()) {
                  debug.debug("Exception looking up runtime [" + serverName + "]: " + var5, var5);
               }

               this.attemptReconnection(runtimeService);
            }
         }

         return result;
      }
   }

   public PartitionRuntimeMBean[] findPartitionRuntimes(String partitionName) {
      List result = new ArrayList();
      if (partitionName == null) {
         return null;
      } else {
         ServerRuntimeMBean[] serverRuntimes = this.getServerRuntimes();
         ServerRuntimeMBean[] var4 = serverRuntimes;
         int var5 = serverRuntimes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ServerRuntimeMBean serverRuntime = var4[var6];
            PartitionRuntimeMBean partitionRuntime = serverRuntime.lookupPartitionRuntime(partitionName);
            if (partitionRuntime != null) {
               result.add(partitionRuntime);
            }
         }

         return (PartitionRuntimeMBean[])((PartitionRuntimeMBean[])result.toArray(new PartitionRuntimeMBean[result.size()]));
      }
   }

   public PartitionRuntimeMBean findPartitionRuntime(String partitionName, String serverName) {
      PartitionRuntimeMBean result = null;
      if (partitionName != null && serverName != null) {
         ServerRuntimeMBean serverRuntime = this.lookupServerRuntime(serverName);
         if (serverRuntime != null) {
            result = serverRuntime.lookupPartitionRuntime(partitionName);
         }

         return result;
      } else {
         return result;
      }
   }

   public Service findService(String name, String type, String serverName) {
      if (serverName == null) {
         return (Service)this.domainAccess.findService(name, type);
      } else {
         RuntimeServiceMBean runtime = this.getRuntimeServiceMBean(serverName);
         if (runtime != null) {
            try {
               return runtime.findService(name, type);
            } catch (RemoteRuntimeException var6) {
               if (debug.isDebugEnabled()) {
                  debug.debug("Exception finding service[" + serverName + "]: " + var6, var6);
               }

               this.attemptReconnection(runtime);
            }
         }

         return null;
      }
   }

   public Service[] getServices(String serverName) {
      if (serverName == null) {
         weblogic.management.provider.Service[] localServices = this.domainAccess.getRootServices();
         Service[] mbeanServices = new Service[localServices.length];
         System.arraycopy(localServices, 0, mbeanServices, 0, localServices.length);
         return mbeanServices;
      } else {
         RuntimeServiceMBean runtime = this.getRuntimeServiceMBean(serverName);
         if (runtime != null) {
            try {
               return runtime.getServices();
            } catch (RemoteRuntimeException var4) {
               if (debug.isDebugEnabled()) {
                  debug.debug("Exception getting services: [" + serverName + "] " + var4, var4);
               }

               this.attemptReconnection(runtime);
            }
         }

         return null;
      }
   }

   private RuntimeServiceMBean getRuntimeServiceMBean(String serverName) {
      RuntimeServiceMBean runtime = this.runtimeServicesManager.get(serverName);
      return runtime;
   }

   private void attemptReconnection(RuntimeServiceMBean runtimeService) {
      String serverName = this.runtimeServicesManager.getServerNameForRuntimeService(runtimeService);
      JMXLogger.logJMXResiliencyWarning(serverName, "Attempting to reconnect to the server in DomainRuntime Service");
      if (serverName != null) {
         this.scheduleReconnection(serverName);
      } else {
         JMXLogger.logJMXResiliencyWarning("", "Server name could not be found in runtime service manager");
      }

   }

   private void scheduleReconnection(String serverName) {
      synchronized(this.inProgressReconnects) {
         if (this.inProgressReconnects.contains(serverName)) {
            JMXLogger.logJMXResiliencyWarning(serverName, "Reconnect to the server in DomainRuntime Service is already in progress. Skipping.");
            return;
         }

         this.inProgressReconnects.add(serverName);
      }

      WorkManagerFactory.getInstance().getSystem().schedule(new ReconnectionExecutor(serverName));
   }

   private WorkManager getWorkManager() {
      if (Thread.currentThread() instanceof ExecuteThread) {
         WorkManager wm = ((ExecuteThread)Thread.currentThread()).getWorkManager();
         if (!"consoleWorkManager".equals(wm.getName())) {
            return wm;
         }
      }

      return WorkManagerFactory.getInstance().getDefault();
   }

   public MigratableServiceCoordinatorRuntimeMBean lookupMigratableServiceCoordinatorRuntime() {
      return ManagementService.getDomainAccess(kernelId).getMigratableServiceCoordinatorRuntime();
   }

   public MigratableServiceCoordinatorRuntimeMBean getMigratableServiceCoordinatorRuntime() {
      return ManagementService.getDomainAccess(kernelId).getMigratableServiceCoordinatorRuntime();
   }

   static {
      try {
         RUNTIME_SERVICE = new ObjectName(RuntimeServiceMBean.OBJECT_NAME);
      } catch (MalformedObjectNameException var1) {
         throw new Error(var1);
      }
   }

   private class ServerRuntimeExecutor implements Runnable {
      private RuntimeServiceMBean runtimeService;
      private List result;
      private CountDownLatch latch;

      ServerRuntimeExecutor(RuntimeServiceMBean runtimeService, List result, CountDownLatch latch) {
         this.runtimeService = runtimeService;
         this.result = result;
         this.latch = latch;
      }

      public void run() {
         String partitionName = ComponentInvocationContextManager.getInstance(DomainRuntimeServiceMBeanImpl.kernelId).getCurrentComponentInvocationContext().getPartitionName();
         String serverName = DomainRuntimeServiceMBeanImpl.this.runtimeServicesManager.getServerNameForRuntimeService(this.runtimeService);

         try {
            if (serverName != null) {
               ExecuteThread.updateWorkDescription("[ Getting server runtime for server : " + serverName + " ]");
               ServerRuntimeMBean serverRuntime = this.runtimeService.getServerRuntime();
               if (serverRuntime != null) {
                  synchronized(this.result) {
                     if (partitionName != null && !partitionName.equals("DOMAIN")) {
                        if (serverRuntime.lookupPartitionRuntime(partitionName) != null) {
                           this.result.add(serverRuntime);
                           return;
                        }
                     } else {
                        this.result.add(serverRuntime);
                     }

                     return;
                  }
               }

               return;
            }
         } catch (RemoteRuntimeException var11) {
            if (DomainRuntimeServiceMBeanImpl.debug.isDebugEnabled()) {
               DomainRuntimeServiceMBeanImpl.debug.debug("Exception finding server runtimes: ", var11);
               DomainRuntimeServiceMBeanImpl.debug.debug("Calling attemptReconnection to reconnect");
            }

            DomainRuntimeServiceMBeanImpl.this.attemptReconnection(this.runtimeService);
            return;
         } finally {
            this.latch.countDown();
         }

      }
   }

   private class RuntimeServiceProxyResolver implements Runnable {
      private RuntimeServicesManager manager;
      private String sName;
      private MBeanServerConnection connection;
      private List failedList;
      private CountDownLatch latch;

      RuntimeServiceProxyResolver(RuntimeServicesManager manager, MBeanServerConnection connection, String sName, List failedList, CountDownLatch latch) {
         this.manager = manager;
         this.connection = connection;
         this.sName = sName;
         this.failedList = failedList;
         this.latch = latch;
      }

      public void run() {
         try {
            ExecuteThread.updateWorkDescription("[ Resolving Runtime Proxy for server : " + this.sName + " ]");
            this.manager.resolveRuntimeServiceProxy(this.connection, this.sName, this.failedList);
         } finally {
            this.latch.countDown();
         }

      }
   }

   private class ReconnectionExecutor implements Runnable {
      private String sname;

      ReconnectionExecutor(String sname) {
         this.sname = sname;
      }

      public void run() {
         boolean var9 = false;

         try {
            var9 = true;
            ExecuteThread.updateWorkDescription("[ Attempting reconnection for server : " + DomainRuntimeServiceMBeanImpl.this.serverName + " ]");
            JMXLogger.logJMXResiliencyWarning(this.sname, "Attempting to reconnect to the server in DomainRuntime Service");
            DomainRuntimeServiceMBeanImpl.this.connectionManager.attemptReconnection(this.sname);
            var9 = false;
         } finally {
            if (var9) {
               synchronized(DomainRuntimeServiceMBeanImpl.this.inProgressReconnects) {
                  DomainRuntimeServiceMBeanImpl.this.inProgressReconnects.remove(this.sname);
               }
            }
         }

         synchronized(DomainRuntimeServiceMBeanImpl.this.inProgressReconnects) {
            DomainRuntimeServiceMBeanImpl.this.inProgressReconnects.remove(this.sname);
         }
      }
   }

   private class RuntimeServicesManager {
      private Map runtimeServicesByName = new ConcurrentHashMap();
      private RuntimeServiceMBean[] runtimeServicesArray;
      private volatile boolean runtimeServicesArrayUptodate = true;
      private Map unresolvedConnections = null;

      RuntimeServicesManager() {
         this.unresolvedConnections = new ConcurrentHashMap();
      }

      RuntimeServiceMBean get(String serverName) {
         this.checkUnresolved();
         return (RuntimeServiceMBean)this.runtimeServicesByName.get(serverName);
      }

      String getServerNameForRuntimeService(RuntimeServiceMBean runtimeService) {
         Iterator i = this.runtimeServicesByName.entrySet().iterator();

         Map.Entry entry;
         do {
            if (!i.hasNext()) {
               return null;
            }

            entry = (Map.Entry)i.next();
         } while(entry.getValue() != runtimeService);

         return (String)entry.getKey();
      }

      synchronized void addConnection(String serverName, MBeanServerConnection connection) {
         this.unresolvedConnections.put(serverName, connection);
         this.runtimeServicesArrayUptodate = false;
         JMXLogger.logJMXResiliencyWarning(serverName, "Added MBeanServerConnection in DomainRuntimeServiceMBean ");
      }

      synchronized void removeConnection(String serverName) {
         this.runtimeServicesByName.remove(serverName);
         this.unresolvedConnections.remove(serverName);
         Collection services = this.runtimeServicesByName.values();
         this.runtimeServicesArray = (RuntimeServiceMBean[])((RuntimeServiceMBean[])services.toArray(new RuntimeServiceMBean[services.size()]));
         JMXLogger.logJMXResiliencyWarning(serverName, "Removing MBeanServerConnection in DomainRuntimeServiceMBean");
      }

      RuntimeServiceMBean[] getRuntimeServices() {
         this.checkUnresolved();
         return this.runtimeServicesArray;
      }

      private void checkUnresolved() {
         if (!this.runtimeServicesArrayUptodate) {
            this.resolve();
         }

      }

      private void resolve() {
         JMXLogger.logJMXResiliencyWarning("All Servers", "Resolving connection list DomainRuntimeServiceMBean");
         List attemptReconnection = new ArrayList();
         synchronized(this) {
            if (!this.runtimeServicesArrayUptodate) {
               CountDownLatch latch = new CountDownLatch(this.unresolvedConnections.size());
               WorkManager wm = DomainRuntimeServiceMBeanImpl.this.getWorkManager();
               Iterator iterator = this.unresolvedConnections.entrySet().iterator();

               while(iterator.hasNext()) {
                  Map.Entry entry = (Map.Entry)iterator.next();
                  wm.schedule(DomainRuntimeServiceMBeanImpl.this.new RuntimeServiceProxyResolver(this, (MBeanServerConnection)entry.getValue(), (String)entry.getKey(), attemptReconnection, latch));
               }

               try {
                  latch.await();
               } catch (InterruptedException var8) {
               }

               Collection services = this.runtimeServicesByName.values();
               this.runtimeServicesArray = (RuntimeServiceMBean[])((RuntimeServiceMBean[])services.toArray(new RuntimeServiceMBean[services.size()]));
               if (this.unresolvedConnections.isEmpty()) {
                  this.runtimeServicesArrayUptodate = true;
               }
            }
         }

         if (!attemptReconnection.isEmpty() && DomainRuntimeServiceMBeanImpl.debug.isDebugEnabled()) {
            DomainRuntimeServiceMBeanImpl.debug.debug("Initiating Reconnect to the following Servers : " + attemptReconnection.toString());
         }

         Iterator var2 = attemptReconnection.iterator();

         while(var2.hasNext()) {
            String serverName = (String)var2.next();
            if (this.unresolvedConnections.containsKey(serverName)) {
               JMXLogger.logJMXResiliencyWarning(serverName, "Calling attemptReconnection in DomainRuntimeServiceMBean");
               DomainRuntimeServiceMBeanImpl.this.scheduleReconnection(serverName);
            } else {
               JMXLogger.logJMXResiliencyWarning(serverName, "Not able to reconnect, server " + serverName + " is not in unresolved connections list.");
            }
         }

      }

      void resolveRuntimeServiceProxy(MBeanServerConnection connection, String serverName, List attemptReconnection) {
         RuntimeServiceMBean runtime;
         try {
            runtime = (RuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(connection, DomainRuntimeServiceMBeanImpl.RUNTIME_SERVICE);
         } catch (Throwable var9) {
            if (DomainRuntimeServiceMBeanImpl.debug.isDebugEnabled()) {
               DomainRuntimeServiceMBeanImpl.debug.debug("Exception resolve runtime: ", var9);
            }

            JMXLogger.logJMXResiliencyWarning(serverName, "Exception in runtime resolve proxy adding the server name in attempt reconnection");
            synchronized(attemptReconnection) {
               attemptReconnection.add(serverName);
               return;
            }
         }

         this.runtimeServicesByName.put(serverName, runtime);
         this.unresolvedConnections.remove(serverName);
      }
   }
}
