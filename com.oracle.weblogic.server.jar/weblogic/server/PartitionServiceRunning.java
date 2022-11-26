package weblogic.server;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.Rank;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.PartitionRuntimeStateManager;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.partition.admin.PartitionManagerService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.t3.srvr.PartitionLifecycleLogger;

@Service
@Named
@RunLevel(20)
@Rank(100)
public final class PartitionServiceRunning extends AbstractServerService {
   @Inject
   @Named("DeploymentPostAdminServerService")
   private ServerService dependsOnDeploymentPostAdminService;
   @Inject
   private PartitionRuntimeStateManager stateManager;
   @Inject
   private PartitionManagerService manager;
   @Inject
   private RuntimeAccess runtimeAccess;
   final boolean isDeploymentStartingAll = false;

   public synchronized void start() throws ServiceFailureException {
      RuntimeAccess config = this.runtimeAccess;
      DomainMBean domainBean = config.getDomain();
      ServerRuntimeMBean serverRuntimeBean = this.runtimeAccess.getServerRuntime();
      String thisServerName = serverRuntimeBean.getName();
      PartitionRuntimeMBean[] partitions = serverRuntimeBean.getPartitionRuntimes();

      try {
         PartitionRuntimeMBean[] var6 = partitions;
         int var7 = partitions.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            PartitionRuntimeMBean partition = var6[var8];
            String state = this.stateManager.getPartitionState(partition.getName(), thisServerName);
            if (!State.isShutdown(state) && state.equals(State.RUNNING.name())) {
               try {
                  partition.resume();
               } catch (Exception var12) {
                  PartitionLifecycleLogger.logPartitionStartFailedDuringServerStartup(partition.getName(), thisServerName, var12);
               }
            }
         }

      } catch (Exception var13) {
         throw new ServiceFailureException(var13);
      }
   }

   public synchronized void halt() throws ServiceFailureException {
      ServerRuntimeMBean serverRuntimeBean = this.runtimeAccess.getServerRuntime();
      DomainMBean domainBean = this.runtimeAccess.getDomain();

      try {
         PartitionRuntimeMBean[] var3 = serverRuntimeBean.getPartitionRuntimes();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PartitionRuntimeMBean partR = var3[var5];
            if (State.isRunning(partR.getInternalState())) {
               try {
                  partR.forceSuspend();
               } catch (Exception var8) {
                  PartitionLifecycleLogger.logPartitionShutdownFailedDuringServerShutdown(partR.getName(), serverRuntimeBean.getName(), var8);
               }
            }
         }

      } catch (Exception var9) {
         throw new ServiceFailureException(var9);
      }
   }

   public void stop() throws ServiceFailureException {
      RuntimeAccess ra = this.runtimeAccess;
      ServerRuntimeMBean server = ra.getServerRuntime();
      DomainMBean domainBean = ra.getDomain();

      try {
         PartitionRuntimeMBean[] var4 = server.getPartitionRuntimes();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            PartitionRuntimeMBean partR = var4[var6];
            if (State.isRunning(partR.getInternalState())) {
               try {
                  partR.suspend(60, false);
               } catch (Exception var9) {
                  PartitionLifecycleLogger.logPartitionShutdownFailedDuringServerShutdown(partR.getName(), server.getName(), var9);
               }
            }
         }

      } catch (Exception var10) {
         throw new ServiceFailureException(var10);
      }
   }
}
