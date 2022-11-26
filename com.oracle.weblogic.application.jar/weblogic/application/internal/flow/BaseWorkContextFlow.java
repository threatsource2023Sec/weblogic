package weblogic.application.internal.flow;

import weblogic.application.ApplicationContextInternal;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.configuration.AppDeploymentMBean;

public class BaseWorkContextFlow extends BaseFlow {
   public BaseWorkContextFlow(ApplicationContextInternal appCtx) {
      super(appCtx);
   }

   protected void setBindApplicationIdCtx(String operation) {
      AppDeploymentMBean dmb = this.appCtx.getAppDeploymentMBean();
      if (this.isDebugEnabled()) {
         this.debug("*** " + operation + " " + (dmb == null ? "" : dmb.getApplicationIdentifier()));
      }

      if (dmb != null && dmb.getVersionIdentifier() != null) {
         ApplicationVersionUtils.setBindApplicationId(dmb.getApplicationIdentifier());
         ApplicationVersionUtils.setCurrentVersionId(dmb.getApplicationName(), dmb.getVersionIdentifier());
      }

   }

   protected void unsetBindApplicationIdCtx(String operation) {
      AppDeploymentMBean dmb = this.appCtx.getAppDeploymentMBean();
      if (this.isDebugEnabled()) {
         this.debug("*** " + operation + " " + (dmb == null ? "" : dmb.getApplicationIdentifier()));
      }

      if (dmb != null && dmb.getVersionIdentifier() != null) {
         ApplicationVersionUtils.unsetBindApplicationId();
         ApplicationVersionUtils.unsetCurrentVersionId(dmb.getApplicationName());
      }

   }
}
