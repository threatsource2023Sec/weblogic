package weblogic.elasticity;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.hk2.api.ServiceLocator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.elasticity.i18n.ElasticityLogger;
import weblogic.elasticity.i18n.ElasticityTextTextFormatter;
import weblogic.management.runtime.ServerLifeCycleTaskRuntimeMBean;
import weblogic.management.workflow.command.AbstractCommand;
import weblogic.server.GlobalServiceLocator;

public abstract class AbstractScalingWork extends AbstractCommand implements Cancellable {
   protected static final transient DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugElasticServices");
   protected static final ElasticityTextTextFormatter txtFmt = ElasticityTextTextFormatter.getInstance();

   protected WLSDynamicClusterScalingService getWlsDynamicClusterScalingService() {
      return (WLSDynamicClusterScalingService)GlobalServiceLocator.getServiceLocator().getService(WLSDynamicClusterScalingService.class, new Annotation[0]);
   }

   protected int performScaling(String clusterName, int scalingSize, boolean isScaleUp, List candidateServerNames, List scaledInstanceNames) {
      WLSDynamicClusterScalingService wlsDynamicClusterScalingService = this.getWlsDynamicClusterScalingService();
      int startIndex = 0;
      int remainingScalingSize = scalingSize;
      String workflowId = this.getContext().getWorkflowId();
      ServerStateInspector serverStateInspector = (ServerStateInspector)GlobalServiceLocator.getServiceLocator().getService(ServerStateInspector.class, new Annotation[0]);
      int successCount = 0;
      int failureCount = 0;

      ArrayList failedInstances;
      for(failedInstances = new ArrayList(); remainingScalingSize > 0 && startIndex < candidateServerNames.size() && !this.isCancel(); remainingScalingSize -= successCount) {
         LinkedList scalingTasks = new LinkedList();

         String serverName;
         for(int count = 0; count < remainingScalingSize && !this.isCancel(); ++count) {
            serverName = (String)candidateServerNames.get(startIndex + count);
            if (!serverStateInspector.isNodemanagerForServerReachable(serverName)) {
               ElasticityLogger.logNodemanagerForServerNotAvailable(workflowId, serverName);
            } else {
               ServerLifeCycleTaskRuntimeMBean taskRuntimeMBean = wlsDynamicClusterScalingService.initiateScaling(clusterName, serverName, isScaleUp, this);
               if (taskRuntimeMBean != null) {
                  scalingTasks.add(taskRuntimeMBean);
               }
            }
         }

         while(scalingTasks.size() > 0 && !this.isCancel()) {
            ServerLifeCycleTaskRuntimeMBean task = (ServerLifeCycleTaskRuntimeMBean)scalingTasks.remove();
            if (task.isRunning()) {
               scalingTasks.offerLast(task);

               try {
                  Thread.sleep(1000L);
               } catch (InterruptedException var18) {
               }
            } else {
               serverName = task.getServerName();
               boolean success = "TASK COMPLETED".equals(task.getStatus());
               if (!success) {
                  success = this.handleFailedScalingOperation(clusterName, serverName);
               }

               if (success) {
                  scaledInstanceNames.add(serverName);
                  ++successCount;
                  this.logOnSuccessfulScaling(clusterName, serverName, isScaleUp);
               } else {
                  ++failureCount;
                  failedInstances.add(serverName);
                  this.logOnFailedScaling(clusterName, serverName, isScaleUp);
               }
            }
         }

         if (this.isCancel()) {
            Iterator var21 = scalingTasks.iterator();

            label78:
            while(true) {
               ServerLifeCycleTaskRuntimeMBean task;
               do {
                  if (!var21.hasNext()) {
                     break label78;
                  }

                  task = (ServerLifeCycleTaskRuntimeMBean)var21.next();
               } while(!task.isRunning());

               try {
                  task.cancel();
               } catch (Exception var19) {
                  if (isScaleUp) {
                     ElasticityLogger.logErrorDuringCancellationOfScaleUpOperation(workflowId, clusterName, task.getServerName(), var19);
                  } else {
                     ElasticityLogger.logErrorDuringCancellationOfScaleDownOperation(workflowId, clusterName, task.getServerName(), var19);
                  }
               }
            }
         }

         startIndex += remainingScalingSize;
      }

      if (failureCount > 0 && debugLogger.isDebugEnabled()) {
         debugLogger.debug("Successes:" + successCount + ", Failures: " + failureCount + ", failed instances: " + failedInstances.toString());
      }

      return scalingSize - remainingScalingSize;
   }

   protected void logOnSuccessfulScaling(String clusterName, String serverName, boolean isScaleUp) {
      String workflowId = this.getContext().getWorkflowId();
      if (isScaleUp) {
         ElasticityLogger.logScaleUpServerStarted(workflowId, clusterName, serverName);
      } else {
         ElasticityLogger.logScaleDownTaskServerShutdownComplete(workflowId, clusterName, serverName);
      }

   }

   protected void logOnFailedScaling(String clusterName, String serverName, boolean isScaleUp) {
      String workflowId = this.getContext().getWorkflowId();
      if (isScaleUp) {
         ElasticityLogger.logScaleUpServerStartFailed(workflowId, clusterName, serverName);
      } else {
         ElasticityLogger.logScaleDownServerShutdownFailed(workflowId, clusterName, serverName);
      }

   }

   protected boolean handleFailedScalingOperation(String clusterName, String serverName) {
      this.logOnFailedScaling(clusterName, serverName, false);
      return false;
   }

   public boolean isCancel() {
      return this.getContext().isCancel();
   }

   protected ClusterScalingStatus getClusterScalingStatus(String clusterName) {
      ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
      ElasticServiceManager esm = (ElasticServiceManager)serviceLocator.getService(ElasticServiceManager.class, new Annotation[0]);
      return esm.getClusterScalingStatus(clusterName);
   }
}
