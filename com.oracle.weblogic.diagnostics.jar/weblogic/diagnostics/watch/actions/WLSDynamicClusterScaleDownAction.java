package weblogic.diagnostics.watch.actions;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.watch.actions.ActionConfigBean;
import com.oracle.weblogic.diagnostics.watch.actions.ActionContext;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.descriptor.WLDFScaleDownActionBean;
import weblogic.diagnostics.watch.i18n.DiagnosticsWatchLogger;

@Service(
   name = "ScaleDown"
)
@AdminServer
@PerLookup
public class WLSDynamicClusterScaleDownAction extends WLSDynamicClusterScalingActionBase {
   public static final String ACTION_NAME = "ScaleDown";

   public WLSDynamicClusterScaleDownAction() {
      super("ScaleDown");
   }

   public void execute(ActionContext context) {
      ActionConfigBean actionConfig = context.getActionConfig();
      if (actionConfig == null) {
         DiagnosticsWatchLogger.logScaleDownActionNoConfigPresent();
      } else {
         WLDFScaleDownActionBean scaleDownConfig = null;
         if (actionConfig instanceof WLDFActionConfigWrapper && ((WLDFActionConfigWrapper)actionConfig).getBean() instanceof WLDFScaleDownActionBean) {
            scaleDownConfig = (WLDFScaleDownActionBean)((WLDFActionConfigWrapper)actionConfig).getBean();
            String clusterName = scaleDownConfig.getClusterName();
            String actionName = scaleDownConfig.getName();
            if (this.isClusterAtDynamicMin(clusterName)) {
               DiagnosticsWatchLogger.logScaleDownActionClusterAtMinimum(actionName, clusterName, this.getConfiguredElasticMin(clusterName));
            } else {
               if (this.markScalingActionInProgress(clusterName)) {
                  String finalTaskStatus = null;
                  int scalingSize = scaleDownConfig.getScalingSize();

                  try {
                     DiagnosticsWatchLogger.logScaleDownActionStarted(actionName, clusterName, scalingSize, scaleDownConfig.getTimeout());
                     String taskUrl = this.lcmInvoker.scaleUpDown(clusterName, scalingSize, false);
                     if (taskUrl != null) {
                        finalTaskStatus = this.monitorLCMScalingTask(taskUrl);
                     } else {
                        finalTaskStatus = this.invokeESMScalingOperation(scaleDownConfig);
                     }
                  } catch (Exception var12) {
                     throw new RuntimeException(var12);
                  } finally {
                     this.clearScalingActionInProgress(clusterName);
                     DiagnosticsWatchLogger.logScaleDownTaskComplete(actionName, clusterName, finalTaskStatus);
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
