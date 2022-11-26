package weblogic.t3.srvr;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.runlevel.RunLevel;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.jvnet.hk2.annotations.Service;
import weblogic.version;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelLogManager;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.RuntimeAccessSettable;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.server.AbstractServerService;
import weblogic.server.AggregateProgressMBeanImpl;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.server.SubsystemProgressListenerImpl;
import weblogic.time.api.TimerMBeanFactoryService;
import weblogic.utils.io.oif.WebLogicObjectInputFilter;
import weblogic.utils.progress.ProgressTrackerRegistrar;
import weblogic.utils.progress.client.ProgressBean;
import weblogic.work.RequestManager;
import weblogic.work.ServerWorkManagerFactory;
import weblogic.workarea.WorkContextFilter;

@Service
@Named
@RunLevel(5)
public class BootService extends AbstractServerService {
   @Inject
   private ServiceLocator locator;
   @Inject
   private WebLogicExecutor weblogicExecutor;
   @Inject
   @Named("RuntimeAccessService")
   private ServerService dependencyOnRuntimeAccessService;
   @Inject
   @Named("FinalThreadLocalService")
   private ServerService dependencyOnFinalThreadLocalService;
   @Inject
   @Named("RuntimeDomainSelectorService")
   private ServerService dependencyOnRuntimeDomainSelectorService;
   @Inject
   private WebLogicServer t3Srvr;
   @Inject
   private Provider runtimeAccessProvider;
   @Inject
   private ProgressTrackerRegistrar registrar;
   private AggregateProgressMBeanImpl aggregateBean;

   public String getName() {
      return "Kernel";
   }

   public String getVersion() {
      return "Commonj WorkManager v1.1";
   }

   public void start() throws ServiceFailureException {
      try {
         RuntimeAccess runtimeAccess = (RuntimeAccess)this.runtimeAccessProvider.get();
         ServerRuntimeMBean serverRuntime = ServerRuntime.init(runtimeAccess);
         ((RuntimeAccessSettable)runtimeAccess).setServerRuntime(serverRuntime);
         SetUIDRendezvous.initialize();
         ServerMBean server = runtimeAccess.getServer();
         if (!server.getUse81StyleExecuteQueues()) {
            if (server.isUseConcurrentQueueForRequestManager()) {
               RequestManager.enableBufferQueue(true);
            }

            if (server.isUseEnhancedPriorityQueueForRequestManager()) {
               RequestManager.enableEnhancedPriorityQueue(true);
            }

            if (server.isAllowShrinkingPriorityRequestQueue()) {
               RequestManager.enableAllowShrinkingPriorityQueue(true);
            }

            if (server.isUseEnhancedIncrementAdvisor()) {
               RequestManager.useEnhancedIncrementAdvisor(true);
            }

            if (server.isIsolatePartitionThreadLocals()) {
               RequestManager.setIsolatePartitionThreadLocal(true);
            }

            ServerWorkManagerFactory.initialize(runtimeAccess.getServer());
         }

         KernelLogManager.initialize(runtimeAccess.getServer());
         Kernel.initialize(runtimeAccess.getServer());
         this.createTimeMBean();
         this.locator.getService(JVMRuntime.class, new Annotation[0]);
         ClassLoaderRuntime.init(serverRuntime);
         T3SrvrLogger.logStartupBuildName(version.getWLSVersion(), runtimeAccess.getServer().getName());
         new ExecutionContext("systemContext");
         new Scavenger();
         WebLogicObjectInputFilter.initialize();
         WorkContextFilter.initialize();
         this.aggregateBean = new AggregateProgressMBeanImpl(serverRuntime.getName(), serverRuntime, this.registrar);
         serverRuntime.setAggregateProgress(this.aggregateBean);
         Iterator var4 = this.registrar.getAggregateProgress().getAllSubsystems().iterator();

         while(var4.hasNext()) {
            ProgressBean bean = (ProgressBean)var4.next();
            this.aggregateBean.createProgress(bean.getName());
         }

         ServiceLocatorUtilities.addClasses(this.locator, new Class[]{SubsystemProgressListenerImpl.class});
      } catch (ConfigurationException var6) {
         T3SrvrLogger.logConfigFailure(var6.getMessage());
         throw new ServiceFailureException(var6);
      } catch (ManagementException var7) {
         throw new ServiceFailureException(var7);
      }

      if (this.weblogicExecutor != null) {
         this.weblogicExecutor.serverWorkManagerInitialized();
      }

   }

   private void createTimeMBean() throws ManagementException {
      TimerMBeanFactoryService factory = (TimerMBeanFactoryService)this.locator.getService(TimerMBeanFactoryService.class, new Annotation[0]);
      factory.createTimerMBean(this.t3Srvr.getStartupThreadGroup());
   }

   public void stop() {
      this.shutdown();
   }

   public void halt() {
      this.shutdown();
   }

   public void shutdown() {
      T3SrvrLogger.logKernelShutdown();
      Kernel.shutdown();
   }
}
