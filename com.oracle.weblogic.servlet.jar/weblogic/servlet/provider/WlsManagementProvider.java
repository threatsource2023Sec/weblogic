package weblogic.servlet.provider;

import java.security.AccessController;
import java.util.Iterator;
import java.util.List;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.ComponentRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.ServletRuntimeMBean;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionAccessRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionRuntimeMBean;
import weblogic.management.runtime.WLDFRuntimeMBean;
import weblogic.management.runtime.WebServerRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.management.runtime.WseeRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.servlet.spi.ManagementProvider;
import weblogic.work.WorkManagerRuntimeMBeanImpl;
import weblogic.work.WorkManagerService;

public class WlsManagementProvider implements ManagementProvider {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final RuntimeAccess runtimeAccess;
   private static final DomainMBean domain;
   private static final ServerMBean server;
   private static final ServerRuntimeMBean serverRuntime;

   public String getServerName() {
      return runtimeAccess.getServerName();
   }

   public DomainMBean getDomainMBean() {
      return domain;
   }

   public ServerMBean getServerMBean() {
      return server;
   }

   public String getDomainRootDir() {
      return DomainDir.getRootDir();
   }

   public String getServerState() {
      return serverRuntime.getState();
   }

   public WLDFAccessRuntimeMBean getWLDFAccessRuntime() {
      WLDFRuntimeMBean wldfRuntime = serverRuntime.getWLDFRuntime();
      return wldfRuntime != null ? wldfRuntime.getWLDFAccessRuntime() : null;
   }

   public WLDFPartitionAccessRuntimeMBean getWLDFPartitionAccessRuntime(String partitionName) {
      PartitionRuntimeMBean partitionRuntime = serverRuntime.lookupPartitionRuntime(partitionName);
      if (partitionRuntime != null) {
         WLDFPartitionRuntimeMBean wldfPartitionRuntime = partitionRuntime.getWLDFPartitionRuntime();
         if (wldfPartitionRuntime != null) {
            return wldfPartitionRuntime.getWLDFPartitionAccessRuntime();
         }
      }

      return null;
   }

   public boolean isServiceAvailable(String name) {
      return serverRuntime.isServiceAvailable(name);
   }

   public boolean isServerInAdminMode() {
      return serverRuntime.getStateVal() == 17;
   }

   public boolean isServerInResumingMode() {
      return serverRuntime.getStateVal() == 6;
   }

   public boolean isServerSuspendingShuttingDown() {
      return serverRuntime.getStateVal() == 4 || serverRuntime.getStateVal() == 7;
   }

   public boolean isServerShuttingDown() {
      return serverRuntime.isShuttingDown();
   }

   public void registerWebServerRuntime(WebServerRuntimeMBean mbean) {
      serverRuntime.addWebServerRuntime(mbean);
   }

   public void unregisterWebServerRuntime(WebServerRuntimeMBean mbean) {
      serverRuntime.removeWebServerRuntime(mbean);
   }

   public int getWebServicesConversationSessionCount(WebAppServletContext context) {
      int count = 0;
      if (!hasWSEEServlets(context)) {
         return count;
      } else {
         String appName = context.getApplicationName();
         ApplicationRuntimeMBean appRT = serverRuntime.lookupApplicationRuntime(appName);
         if (appRT != null) {
            WseeRuntimeMBean[] wseeRTs = appRT.getWseeRuntimes();
            if (wseeRTs != null) {
               for(int indx = 0; indx < wseeRTs.length; ++indx) {
                  count = (int)((long)count + wseeRTs[indx].getConversationInstanceCount());
               }
            }
         }

         return count;
      }
   }

   private static boolean hasWSEEServlets(WebAppServletContext context) {
      ServletRuntimeMBean[] runtimes = context.getServletRuntimeMBeans();

      for(int i = 0; i < runtimes.length; ++i) {
         ServletRuntimeMBean srm = runtimes[i];
         if (srm.getServletClassName().equals("weblogic.wsee.server.servlet.WebappWSServlet") || srm.getServletClassName().equals("weblogic.wsee.server.servlet.EjbWSServlet")) {
            return true;
         }
      }

      return false;
   }

   public boolean isMemoryLow() {
      HealthState health = serverRuntime.getHealthState();
      if (health.getState() == 4 && health.getSymptoms().length >= 1) {
         for(int i = 0; i < health.getSymptoms().length; ++i) {
            if (health.getSymptoms()[i].getInfo() == "server is low on memory") {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public void handleOutOfMemory(Throwable e) {
      if (e instanceof OutOfMemoryError || e.getCause() instanceof OutOfMemoryError) {
         HealthMonitorService.panic(e);
      }

   }

   public void addWorkManagerRuntimes(ComponentRuntimeMBean parent, ApplicationRuntimeMBean appRuntime, List workManagers) throws ManagementException {
      Iterator iter = workManagers.iterator();

      while(iter.hasNext()) {
         WorkManagerRuntimeMBean wmRuntime = WorkManagerRuntimeMBeanImpl.getWorkManagerRuntime(((WorkManagerService)iter.next()).getDelegate(), appRuntime, parent);
         if (wmRuntime != null) {
            parent.addWorkManagerRuntime(wmRuntime);
         }
      }

   }

   static {
      runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
      domain = runtimeAccess.getDomain();
      server = runtimeAccess.getServer();
      serverRuntime = runtimeAccess.getServerRuntime();
   }
}
