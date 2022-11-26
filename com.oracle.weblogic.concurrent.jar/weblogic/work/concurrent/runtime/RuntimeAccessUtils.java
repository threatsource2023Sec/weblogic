package weblogic.work.concurrent.runtime;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ClusterRuntimeMBean;
import weblogic.management.runtime.ConcurrentManagedObjectsRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.concurrent.ConcurrencyLogger;
import weblogic.work.concurrent.utils.ConcurrentUtils;

public class RuntimeAccessUtils {
   private static final RuntimeAccess runtimeAccess;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private static final Map concurrentRuntimeMBeans;

   public static String getServerName() {
      return runtimeAccess.getServerName();
   }

   public static String getClusterName() {
      ClusterRuntimeMBean clusterRuntime = runtimeAccess.getServerRuntime().getClusterRuntime();
      return clusterRuntime != null ? clusterRuntime.getName() : null;
   }

   public static ServerRuntimeMBean getServerRuntime() {
      return runtimeAccess.getServerRuntime();
   }

   public static WorkManagerRuntimeMBean getPartitionWorkManagerRuntimeMBean(String partitionName, String wmName) {
      WorkManagerRuntimeMBean[] workManagerRuntimes = null;
      if (ConcurrentUtils.isDomainPartitionName(partitionName)) {
         workManagerRuntimes = runtimeAccess.getServerRuntime().getWorkManagerRuntimes();
      } else {
         PartitionRuntimeMBean partitionRuntimeMBean = runtimeAccess.getServerRuntime().lookupPartitionRuntime(partitionName);
         workManagerRuntimes = partitionRuntimeMBean.getWorkManagerRuntimes();
      }

      WorkManagerRuntimeMBean[] var7 = workManagerRuntimes;
      int var4 = workManagerRuntimes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         WorkManagerRuntimeMBean wm = var7[var5];
         if (wm.getName().equals(wmName)) {
            return wm;
         }
      }

      return null;
   }

   public static ConcurrentManagedObjectsRuntimeMBeanImpl getConcurrentManagedObjectsRuntime() throws ManagementException {
      String partitionName = getCurrentPartitionName();
      return getConcurrentManagedObjectsRuntime(partitionName);
   }

   public static ConcurrentManagedObjectsRuntimeMBeanImpl getConcurrentManagedObjectsRuntime(String partitionName) throws ManagementException {
      if (ConcurrentUtils.isDomainPartitionName(partitionName)) {
         return getOrCreateGlobalConcurrentRuntime();
      } else {
         PartitionRuntimeMBean partitionRuntimeMBean = runtimeAccess.getServerRuntime().lookupPartitionRuntime(partitionName);
         return getOrCreatePartitionConcurrentRuntime(partitionName, partitionRuntimeMBean);
      }
   }

   public static ConcurrentManagedObjectsRuntimeMBeanImpl getOrCreateGlobalConcurrentRuntime() throws ManagementException {
      ServerRuntimeMBean serverRuntime = runtimeAccess.getServerRuntime();
      synchronized(concurrentRuntimeMBeans) {
         ConcurrentManagedObjectsRuntimeMBeanImpl globalRuntimeMbean = (ConcurrentManagedObjectsRuntimeMBeanImpl)serverRuntime.getConcurrentManagedObjectsRuntime();
         if (globalRuntimeMbean == null) {
            try {
               globalRuntimeMbean = new ConcurrentManagedObjectsRuntimeMBeanImpl();
            } catch (ManagementException var5) {
               ConcurrencyLogger.logCMOsRuntimeMBeanCreationError(ConcurrentUtils.getDomainPartitionName(), var5);
               throw var5;
            }

            serverRuntime.setConcurrentManagedObjectsRuntime(globalRuntimeMbean);
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("ConcurrentManagedObjectsRuntimeMBean " + globalRuntimeMbean + " for global runtime is created and installed under " + serverRuntime);
            }
         }

         return globalRuntimeMbean;
      }
   }

   public static ConcurrentManagedObjectsRuntimeMBeanImpl getOrCreatePartitionConcurrentRuntime(String partitionName, PartitionRuntimeMBean partitionRuntimeMBean) throws ManagementException {
      if (partitionName == null) {
         IllegalArgumentException e = new IllegalArgumentException("Can not create ConcurrentManagedObjectsRuntimeMBean without partition name");
         ConcurrencyLogger.logCMOsRuntimeMBeanCreationError(partitionName, e);
         throw e;
      } else {
         synchronized(concurrentRuntimeMBeans) {
            ConcurrentManagedObjectsRuntimeMBeanImpl concurrentRuntimeMBean = (ConcurrentManagedObjectsRuntimeMBeanImpl)concurrentRuntimeMBeans.get(partitionName);
            if (concurrentRuntimeMBean == null) {
               try {
                  concurrentRuntimeMBean = new ConcurrentManagedObjectsRuntimeMBeanImpl();
               } catch (ManagementException var6) {
                  ConcurrencyLogger.logCMOsRuntimeMBeanCreationError(partitionName, var6);
                  throw var6;
               }

               concurrentRuntimeMBeans.put(partitionName, concurrentRuntimeMBean);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("ConcurrentManagedObjectsRuntimeMBean " + concurrentRuntimeMBean + " for partition " + partitionName + " is created");
               }
            }

            if (partitionRuntimeMBean != null && partitionRuntimeMBean.getConcurrentManagedObjectsRuntime() != concurrentRuntimeMBean) {
               partitionRuntimeMBean.setConcurrentManagedObjectsRuntime(concurrentRuntimeMBean);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("ConcurrentManagedObjectsRuntimeMBean " + concurrentRuntimeMBean + " is installed under " + partitionRuntimeMBean);
               }
            }

            return concurrentRuntimeMBean;
         }
      }
   }

   public static void removePartitionConcurrentRuntime(String partitionName, PartitionRuntimeMBean partitionRuntimeMBean) throws ManagementException {
      if (partitionName == null) {
         IllegalArgumentException e = new IllegalArgumentException("Can not remove ConcurrentManagedObjectsRuntimeMBean without partition name");
         ConcurrencyLogger.logCMOsRuntimeMBeanDestructionError(partitionName, e);
         throw e;
      } else {
         ConcurrentManagedObjectsRuntimeMBeanImpl concurrentRuntime;
         synchronized(concurrentRuntimeMBeans) {
            concurrentRuntime = (ConcurrentManagedObjectsRuntimeMBeanImpl)concurrentRuntimeMBeans.remove(partitionName);
         }

         if (concurrentRuntime != null) {
            try {
               concurrentRuntime.unregister();
            } catch (ManagementException var9) {
               ConcurrencyLogger.logCMOsRuntimeMBeanDestructionError(partitionName, var9);
               throw var9;
            } finally {
               if (partitionRuntimeMBean != null) {
                  partitionRuntimeMBean.setConcurrentManagedObjectsRuntime((ConcurrentManagedObjectsRuntimeMBean)null);
               }

            }

         }
      }
   }

   public static String getCurrentPartitionId() {
      ComponentInvocationContext componentInvocationContext = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      return componentInvocationContext.getPartitionId();
   }

   public static String getCurrentPartitionName() {
      ComponentInvocationContext componentInvocationContext = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      return componentInvocationContext.getPartitionId() != null ? componentInvocationContext.getPartitionName() : ConcurrentUtils.getDomainPartitionName();
   }

   public static DomainMBean getDomainMBean() {
      return runtimeAccess.getDomain();
   }

   public static PartitionMBean getPartitionMBean(String partitionName) {
      if (ConcurrentUtils.isDomainPartitionName(partitionName)) {
         return null;
      } else {
         PartitionMBean partition = runtimeAccess.getDomain().lookupPartition(partitionName);
         if (partition == null) {
            throw new IllegalStateException("Could not find PartitionMBean for " + partitionName);
         } else {
            return partition;
         }
      }
   }

   public static ServerMBean getServerMBean() {
      return runtimeAccess.getServer();
   }

   public static ConcurrentConstraintsInfo getServerConstraints() {
      ServerMBean server = runtimeAccess.getServer();
      return new ConcurrentConstraintsInfo(server);
   }

   public static ConcurrentConstraintsInfo getPartitionConstraints(String partitionName) {
      if (ConcurrentUtils.isDomainPartitionName(partitionName)) {
         return new ConcurrentConstraintsInfo(runtimeAccess.getDomain());
      } else {
         PartitionMBean partition = runtimeAccess.getDomain().lookupPartition(partitionName);
         if (partition == null) {
            throw new IllegalStateException("Could not find PartitionMBean for " + partitionName);
         } else {
            return new ConcurrentConstraintsInfo(partition);
         }
      }
   }

   static {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      concurrentRuntimeMBeans = new HashMap();
   }
}
