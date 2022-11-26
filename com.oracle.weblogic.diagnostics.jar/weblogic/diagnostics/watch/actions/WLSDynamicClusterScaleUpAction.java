package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBean;
import com.oracle.weblogic.diagnostics.watch.actions.ActionContext;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.descriptor.WLDFScaleUpActionBean;
import weblogic.diagnostics.watch.i18n.DiagnosticsWatchLogger;

@Service(
   name = "ScaleUp"
)
@AdminServer
@PerLookup
public class WLSDynamicClusterScaleUpAction extends WLSDynamicClusterScalingActionBase {
   public static final String ACTION_NAME = "ScaleUp";

   public WLSDynamicClusterScaleUpAction() {
      super("ScaleUp");
   }

   public void execute(ActionContext context) {
      ActionConfigBean actionConfig = context.getActionConfig();
      if (actionConfig == null) {
         DiagnosticsWatchLogger.logScaleUpActionNoConfigPresent();
      } else {
         WLDFScaleUpActionBean scaleUpConfig = null;
         if (actionConfig instanceof WLDFActionConfigWrapper && ((WLDFActionConfigWrapper)actionConfig).getBean() instanceof WLDFScaleUpActionBean) {
            scaleUpConfig = (WLDFScaleUpActionBean)((WLDFActionConfigWrapper)actionConfig).getBean();
            String actionName = scaleUpConfig.getName();
            String clusterName = scaleUpConfig.getClusterName();
            int scalingSize = scaleUpConfig.getScalingSize();
            if (this.isClusterAtDynamicMax(clusterName)) {
               DiagnosticsWatchLogger.logScaleUpActionClusterAtMaximum(actionName, clusterName, this.getConfiguredElasticMax(clusterName));
            } else {
               if (this.markScalingActionInProgress(clusterName)) {
                  String finalTaskStatus = null;

                  try {
                     DiagnosticsWatchLogger.logScaleUpActionStarted(actionName, clusterName, scalingSize, scaleUpConfig.getTimeout());
                     String taskUrl = this.lcmInvoker.scaleUpDown(clusterName, scalingSize, true);
                     if (taskUrl != null) {
                        finalTaskStatus = this.monitorLCMScalingTask(taskUrl);
                     } else {
                        finalTaskStatus = this.invokeESMScalingOperation(scaleUpConfig);
                     }
                  } catch (Exception var12) {
                     throw new RuntimeException(var12);
                  } finally {
                     this.clearScalingActionInProgress(clusterName);
                     DiagnosticsWatchLogger.logScaleUpTaskComplete(actionName, clusterName, finalTaskStatus);
                  }
               } else if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Scaling action already in progress for cluster " + clusterName);
               }

            }
         } else {
            DiagnosticsWatchLogger.logScaleUpActionInvalidConfigBeanType(actionConfig.getName(), actionConfig.getClass().getName(), WLDFActionConfigWrapper.class.getName());
         }
      }
   }
}
