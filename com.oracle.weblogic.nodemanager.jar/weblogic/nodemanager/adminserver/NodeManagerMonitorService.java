package weblogic.nodemanager.adminserver;

import java.io.IOException;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.admin.plugin.NMMachineChangeList;
import weblogic.application.utils.ManagementUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.internal.CommonAdminConfigurationManager;
import weblogic.nodemanager.mbean.NodeManagerRuntime;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.ServerWorkManagerFactory;
import weblogic.work.WorkManager;

@Service
@Named
@RunLevel(10)
public class NodeManagerMonitorService extends AbstractServerService {
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   NodeManagerMonitorImpl monitor = null;
   private static final long DEFAULT_TIMER_PERIOD_MILLIS = 60000L;
   private static final String WORK_MANAGER_NAME = "weblogic.nodemanager.ConfigPoler";
   private static final int WORK_MANAGER_MIN_THREADS = 1;
   private static final int WORK_MANAGER_MAX_THREADS = 1;

   public void start() {
      if (this.isAdminServer()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("NodeManagerMonitorService starting");
         }

         this.monitor = this.createNodeManagerMonitor();
         CommonAdminConfigurationManager.getInstance().setNodeManagerMonitor(this.monitor);
         NodeManagerRuntime.setNodeManagerMonitor(this.monitor);
      }

   }

   public void stop() {
      if (this.monitor != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("NodeManagerMonitorService stopping");
         }

         this.monitor.shutdown();
         this.monitor = null;
      }

   }

   public void halt() {
      if (this.monitor != null) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(" NodeManagerMonitorService halting");
         }

         this.monitor.shutdown();
         this.monitor = null;
      }

   }

   private NodeManagerMonitorImpl createNodeManagerMonitor() {
      WorkManager workManager = this.getWorkManager();
      DomainMBean domainMBean = this.getDomainMBean();
      MachineChangeListProvider machineChangeListProvider = new MachineChangeListProviderImpl();
      TimerManager timerManager = this.getTimerManager();
      long timerPeriodMillis = 60000L;
      return new NodeManagerMonitorImpl(workManager, domainMBean, machineChangeListProvider, timerManager, timerPeriodMillis);
   }

   private TimerManager getTimerManager() {
      return TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
   }

   private boolean isAdminServer() {
      return ManagementService.getPropertyService(kernelId).isAdminServer();
   }

   private DomainMBean getDomainMBean() {
      return ManagementUtils.getDomainMBean();
   }

   private WorkManager getWorkManager() {
      return ServerWorkManagerFactory.getInstance().findOrCreate("weblogic.nodemanager.ConfigPoler", -1, 1, 1);
   }

   private static class MachineChangeListProviderImpl implements MachineChangeListProvider {
      public MachineChangeListProviderImpl() {
      }

      public void syncChangeList(MachineMBean machineMBean) throws IOException {
         NMMachineChangeList changeList = CommonAdminConfigurationManager.getInstance().getMachineConfiguration(machineMBean.getName());
         if (NodeManagerMonitorService.debugLogger.isDebugEnabled()) {
            NodeManagerMonitorService.debugLogger.debug("NodeManagerMonitorService sync changelist: machine=" + machineMBean + " changeList=" + changeList);
         }

         if (changeList != null) {
            if (NodeManagerMonitorService.debugLogger.isDebugEnabled()) {
               NodeManagerMonitorService.debugLogger.debug("NodeManagerMonitorService sync changelist 1 calling getInstance");
            }

            NodeManagerRuntime nmr = NodeManagerRuntime.getInstance(machineMBean);
            if (NodeManagerMonitorService.debugLogger.isDebugEnabled()) {
               NodeManagerMonitorService.debugLogger.debug("NodeManagerMonitorService sync changelist 2 calling syncChangeList");
            }

            nmr.syncChangeList(changeList);
            if (NodeManagerMonitorService.debugLogger.isDebugEnabled()) {
               NodeManagerMonitorService.debugLogger.debug("NodeManagerMonitorService sync changelist 3 done");
            }
         }

      }
   }
}
