package weblogic.management;

import java.io.Serializable;
import javax.management.Notification;
import javax.management.NotificationFilter;

public class DeploymentNotificationFilter implements NotificationFilter, Serializable {
   private static final long serialVersionUID = 1L;
   private String target = null;
   private String module = null;
   private boolean appsOK = true;
   private boolean modulesOK = true;

   public DeploymentNotificationFilter() {
   }

   public DeploymentNotificationFilter(String targetName, String moduleName, boolean appFilter, boolean moduleFilter) {
      this.target = targetName;
      this.module = moduleName;
      this.appsOK = appFilter;
      this.modulesOK = moduleFilter;
   }

   public boolean isNotificationEnabled(Notification n) {
      boolean enabled = false;
      if (n instanceof DeploymentNotification) {
         enabled = true;
         DeploymentNotification dn = (DeploymentNotification)n;
         String currTarget = dn.getServerName();
         if (this.target != null && !this.target.equals(currTarget)) {
            enabled = false;
         }

         String currModule = dn.getModuleName();
         if (enabled && this.module != null && dn.isModuleNotification() && !this.module.equals(currModule)) {
            enabled = false;
         }

         enabled = enabled && (this.appsOK && dn.isAppNotification() || this.modulesOK && dn.isModuleNotification());
      }

      return enabled;
   }
}
