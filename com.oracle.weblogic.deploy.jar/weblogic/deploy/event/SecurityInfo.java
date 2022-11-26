package weblogic.deploy.event;

import weblogic.deploy.utils.ApplicationUtils;
import weblogic.management.configuration.AppDeploymentMBean;

public final class SecurityInfo {
   private final BaseDeploymentEvent evt;
   private AppDeploymentMBean srcMBean;

   SecurityInfo(BaseDeploymentEvent evt) {
      this.evt = evt;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(this.getClass().getName()).append("{").append("SourceMBean=").append(this.getSource()).append("}");
      return sb.toString();
   }

   public AppDeploymentMBean getSource() {
      if (this.srcMBean == null) {
         AppDeploymentMBean deployMBean = this.evt.getAppDeployment();
         if (deployMBean == null) {
            return null;
         }

         this.srcMBean = ApplicationUtils.getActiveAppDeployment(deployMBean.getApplicationName());
      }

      return this.srcMBean;
   }

   public String getRealm() {
      return "weblogicDEFAULT";
   }
}
