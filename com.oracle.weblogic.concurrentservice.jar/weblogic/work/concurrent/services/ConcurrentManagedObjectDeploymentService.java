package weblogic.work.concurrent.services;

import java.util.Iterator;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.PartitionTable;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ManagedExecutorServiceMBean;
import weblogic.management.configuration.ManagedScheduledExecutorServiceMBean;
import weblogic.management.configuration.ManagedThreadFactoryMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.internal.DeploymentHandlerHome;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.work.concurrent.context.DelegateAppContextHelper;
import weblogic.work.concurrent.partition.ConcurrentManagedObjectDeploymentHandler;
import weblogic.work.concurrent.partition.PartitionObjectAndRuntimeManager;
import weblogic.work.concurrent.runtime.AppContextHelperImpl;
import weblogic.work.concurrent.runtime.ConcurrentAppDeploymentExtensionFactory;
import weblogic.work.concurrent.runtime.GlobalConstraints;
import weblogic.work.concurrent.runtime.RuntimeAccessUtils;
import weblogic.work.concurrent.spi.ThreadNumberConstraints;
import weblogic.work.concurrent.utils.ConcurrentUtils;
import weblogic.work.concurrent.utils.DefaultConcurrentObjectInfo;

@Service
@Named
@RunLevel(10)
public class ConcurrentManagedObjectDeploymentService extends AbstractServerService {
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnNamingService;
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConcurrent");
   private static final PartitionObjectAndRuntimeManager concurrentObjectAndRuntimeManager = PartitionObjectAndRuntimeManager.getInstance();
   private final BeanUpdateListener constraintsUpdateListener = new BeanUpdateListener() {
      public void rollbackUpdate(BeanUpdateEvent event) {
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         ServerMBean serverBean = (ServerMBean)event.getSourceBean();
         BeanUpdateEvent.PropertyUpdate[] list = event.getUpdateList();
         BeanUpdateEvent.PropertyUpdate[] var4 = list;
         int var5 = list.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BeanUpdateEvent.PropertyUpdate prop = var4[var6];
            if (prop.getUpdateType() == 1) {
               String propertyName = prop.getPropertyName();
               ThreadNumberConstraints mtfLimit;
               if (propertyName.equals("MaxConcurrentLongRunningRequests")) {
                  mtfLimit = GlobalConstraints.getExecutorConstraints().getServerLimit();
                  mtfLimit.adjustMax(serverBean.getMaxConcurrentLongRunningRequests());
               } else if (propertyName.equals("MaxConcurrentNewThreads")) {
                  mtfLimit = GlobalConstraints.getMTFConstraints().getServerLimit();
                  mtfLimit.adjustMax(serverBean.getMaxConcurrentNewThreads());
               }
            }
         }

      }
   };
   private ConcurrentManagedObjectDeploymentHandler concurrentManagedObjectDeployer;

   public void start() throws ServiceFailureException {
      DelegateAppContextHelper.setDelegate(AppContextHelperImpl.instance);

      try {
         RuntimeAccessUtils.getOrCreateGlobalConcurrentRuntime();
      } catch (ManagementException var5) {
         throw new ServiceFailureException(var5);
      }

      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      RuntimeAccessUtils.getServerMBean().addBeanUpdateListener(this.constraintsUpdateListener);
      ConcurrentAppDeploymentExtensionFactory cade = new ConcurrentAppDeploymentExtensionFactory();
      afm.addAppDeploymentExtensionFactory(cade);
      this.concurrentManagedObjectDeployer = new ConcurrentManagedObjectDeploymentHandler();
      DeploymentHandlerHome.addDeploymentHandler(this.concurrentManagedObjectDeployer);

      try {
         createDefaultObjectsForCurrentPartition();
      } catch (DeploymentException var4) {
         throw new ServiceFailureException(var4);
      }
   }

   public void stop() throws ServiceFailureException {
      this.halt();
   }

   public void halt() throws ServiceFailureException {
      RuntimeAccessUtils.getServerMBean().removeBeanUpdateListener(this.constraintsUpdateListener);
      cleanupDefaultObjects(PartitionTable.getInstance().getGlobalPartitionName());
   }

   static void cleanupDefaultObjects(String partitionName) {
      Iterator var1 = ConcurrentUtils.getAllDefaultConcurrentObjectInfo().iterator();

      while(var1.hasNext()) {
         DefaultConcurrentObjectInfo info = (DefaultConcurrentObjectInfo)var1.next();
         concurrentObjectAndRuntimeManager.removeObject(info.getClassName(), info.getName(), partitionName);
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("cleanupDefaultObjects: remove default CMOs for " + partitionName);
      }

   }

   static void createDefaultObjectsForCurrentPartition() throws DeploymentException {
      concurrentObjectAndRuntimeManager.createManagedExecutorServiceReference((ManagedExecutorServiceMBean)null);
      concurrentObjectAndRuntimeManager.createManagedScheduledExecutorServiceReference((ManagedScheduledExecutorServiceMBean)null);
      concurrentObjectAndRuntimeManager.createManagedThreadFactoryReference((ManagedThreadFactoryMBean)null);
      concurrentObjectAndRuntimeManager.createContextServiceReference();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("createDefaultObjectsForCurrentPartition: created default CMOs for " + RuntimeAccessUtils.getCurrentPartitionName());
      }

   }
}
