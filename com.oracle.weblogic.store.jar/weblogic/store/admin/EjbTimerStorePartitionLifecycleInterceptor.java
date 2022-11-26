package weblogic.store.admin;

import java.security.AccessController;
import java.util.HashMap;
import java.util.Map;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.glassfish.hk2.extras.interception.Interceptor;
import org.jvnet.hk2.annotations.ContractsProvided;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.util.PartitionManagerInterceptorAdapter;
import weblogic.management.configuration.util.ServerServiceInterceptor;
import weblogic.management.provider.ManagementService;
import weblogic.management.utils.GenericManagedDeployment;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServiceFailureException;
import weblogic.store.PersistentStore;
import weblogic.store.PersistentStoreManager;
import weblogic.store.common.StoreDebug;

@Service
@Interceptor
@ContractsProvided({EjbTimerStorePartitionLifecycleInterceptor.class, MethodInterceptor.class})
@ServerServiceInterceptor(EjbTimerStoreService.class)
public class EjbTimerStorePartitionLifecycleInterceptor extends PartitionManagerInterceptorAdapter {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static ComponentInvocationContextManager CICM;
   private static final String GLOBAL_NAME = "DOMAIN";
   private static final String EJBTIMER_NAME_PREFIX = "_WLS_EJBTIMER_";
   static final String IMAGE_NAME = "EJBTIMER_STORE";
   private Map handlerMap = new HashMap();

   public void startPartitionInAdmin(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("EjbTimerStorePartitionLifecycleInterceptor.startPartitionInAdmin(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.startPartition(methodInvocation, partitionName);
   }

   public void resumePartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("EjbTimerStorePartitionLifecycleInterceptor.resumePartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "resumePartition");
      methodInvocation.proceed();
   }

   public void startPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("EjbTimerStorePartitionLifecycleInterceptor.startPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      this.setupEjbTimerStore(methodInvocation, partitionName);
      methodInvocation.proceed();
   }

   public void suspendPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions) throws Throwable {
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("ENTER EjbTimerStorePartitionLifecycleInterceptor.suspendPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "suspendPartition");
      methodInvocation.proceed();
   }

   public void forceSuspendPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("ENTER EjbTimerStorePartitionLifecycleInterceptor.forceSuspendPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceSuspendPartition");
      methodInvocation.proceed();
   }

   public void shutdownPartition(MethodInvocation methodInvocation, String partitionName, int timeout, boolean ignoreSessions, boolean waitForAllSessions) throws Throwable {
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("ENTER EjbTimerStorePartitionLifecycleInterceptor.shutdownPartition(" + partitionName + ", timeout=" + timeout + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownPartition");
      methodInvocation.proceed();
      this.shutdownEjbTimerStore(methodInvocation, partitionName, false);
   }

   public void forceShutdownPartition(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("ENTER EjbTimerStorePartitionLifecycleInterceptor.forceShutdownPartition(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "forceShutdownPartition");
      methodInvocation.proceed();
      this.shutdownEjbTimerStore(methodInvocation, partitionName, true);
   }

   private void setupEjbTimerStore(MethodInvocation methodInvocation, String partitionName) throws Throwable {
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("SETUP_EjbTimerStorePartitionLifecycleInterceptor.setupEjbTimerStore(" + partitionName + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "setupEjbTimerStore");
      PartitionMBean partition = this.getPartition(methodInvocation);
      this.createEjbTimerStore(partition.getName(), partition.getPartitionID());
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("EjbTimeStore created for partition " + partitionName);
      }

   }

   private void shutdownEjbTimerStore(MethodInvocation methodInvocation, String partitionName, boolean force) throws Throwable {
      if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("TEARDOWN_EjbTimerStorePartitionLifecycleInterceptor.shutdownEjbTimerStore(" + partitionName + ", force=" + force + "), current cic=" + CICM.getCurrentComponentInvocationContext());
      }

      checkCIC(partitionName, "shutdownEjbTimerStore");
      PartitionMBean partition = this.getPartition(methodInvocation);
      this.removeEjbTimerStore(partition.getName(), partition.getPartitionID());
   }

   private synchronized void createEjbTimerStore(String partitionName, String partitionId) throws Throwable {
      PersistentStoreManager pmgr = PersistentStoreManager.getManager();
      PersistentStore ejbTimerStore = pmgr.getEjbTimerStore();
      if (ejbTimerStore == null) {
         if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
            StoreDebug.ejbTimerStore.debug("Creating EJB Timer store for partition " + partitionName);
         }

         ServerMBean serverBean = ManagementService.getRuntimeAccess(kernelId).getServer();
         String serverName = serverBean.getName();
         String name = "_WLS_EJBTIMER_" + serverName;
         FileAdminHandler handler = new FileAdminHandler();
         handler.prepareEjbTimerStore(serverBean, name);
         handler.activate((GenericManagedDeployment)null);
         this.handlerMap.put(this.getLongName(name, partitionName), handler);
         pmgr.addEjbTimerStore(partitionId, serverName, handler.getStore());
         if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
            StoreDebug.ejbTimerStore.debug("EJB Timer store created for parition " + partitionName);
         }
      } else if (StoreDebug.ejbTimerStore.isDebugEnabled()) {
         StoreDebug.ejbTimerStore.debug("EJB Timer store already created for parition " + partitionName);
      }

   }

   private synchronized void removeEjbTimerStore(String partitionName, String partitionId) throws Throwable {
      PersistentStoreManager pmgr = PersistentStoreManager.getManager();
      ServerMBean serverBean = ManagementService.getRuntimeAccess(kernelId).getServer();
      String serverName = serverBean.getName();
      String name = "_WLS_EJBTIMER_" + serverName;
      String lname = this.getLongName(name, partitionName);
      FileAdminHandler handler = (FileAdminHandler)this.handlerMap.get(lname);
      if (handler != null) {
         handler.deactivate((GenericManagedDeployment)null);
         handler.unprepareEjbTimerStore(serverBean);
      }

      pmgr.removeEjbTimerStore(partitionId, serverName);
      this.handlerMap.remove(lname);
   }

   private String getLongName(String ejbTimerStoreName, String partitionName) {
      StringBuilder sb = new StringBuilder();
      sb.append(ejbTimerStoreName);
      if (!"DOMAIN".equals(partitionName)) {
         sb.append("$").append(partitionName);
      }

      return sb.toString();
   }

   private static void checkCIC(String partitionName, String method) throws ServiceFailureException {
      ComponentInvocationContext cic = CICM.getCurrentComponentInvocationContext();
      if (!cic.getPartitionName().equals(partitionName)) {
         throw new ServiceFailureException("Mismatched current invocation partition [" + cic + "], expected partition name " + partitionName + " on " + method + "() in EjbTimerStorePartitionLifecycleInterceptor");
      }
   }

   static {
      CICM = ComponentInvocationContextManager.getInstance(kernelId);
   }
}
