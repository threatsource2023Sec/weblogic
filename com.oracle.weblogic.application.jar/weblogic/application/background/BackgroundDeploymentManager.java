package weblogic.application.background;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ApplicationContext;
import weblogic.application.DeploymentContext;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.AppDeploymentMBean;

public enum BackgroundDeploymentManager {
   instance;

   public final DebugLogger logger = DebugLogger.getDebugLogger("DebugAppContainer");
   private final List backgroundApps = new ArrayList();
   private final List backgroundOnDemandApps = new ArrayList();

   public List getBackgroundApps() {
      return this.backgroundApps;
   }

   public List getBackgroundOnDemandApps() {
      return this.backgroundOnDemandApps;
   }

   void addBackgroundDeployment(BackgroundDeployment d, DeploymentContext ctx) {
      ApplicationContext appCtx = d.getApplicationContext();
      AppDeploymentMBean appMBean = appCtx != null ? appCtx.getAppDeploymentMBean() : null;
      if (appMBean != null && appMBean.getOnDemandContextPaths() != null && appMBean.getOnDemandContextPaths().length > 0) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Add background deployment: " + appMBean.getName());
         }

         synchronized(this.backgroundOnDemandApps) {
            this.backgroundOnDemandApps.add(new BackgroundApplication(d, ctx));
         }
      } else {
         synchronized(this.backgroundApps) {
            this.backgroundApps.add(new BackgroundApplication(d, ctx));
         }
      }

   }

   void removeBackgroundDeployment(BackgroundDeployment d, DeploymentContext ctx) {
      ApplicationContext appCtx = d.getApplicationContext();
      AppDeploymentMBean appMBean = appCtx != null ? appCtx.getAppDeploymentMBean() : null;
      if (appMBean != null && appMBean.getOnDemandContextPaths() != null && appMBean.getOnDemandContextPaths().length > 0) {
         synchronized(this.backgroundOnDemandApps) {
            Iterator it = this.backgroundOnDemandApps.iterator();

            while(it.hasNext()) {
               BackgroundApplication app = (BackgroundApplication)it.next();
               if (app.getDeployment().equals(d)) {
                  it.remove();
                  return;
               }
            }
         }
      } else {
         Iterator it = this.backgroundApps.iterator();

         while(it.hasNext()) {
            BackgroundApplication app = (BackgroundApplication)it.next();
            if (app.getDeployment().equals(d)) {
               it.remove();
               return;
            }
         }
      }

   }

   public boolean isDebugEnabled() {
      return this.logger.isDebugEnabled();
   }

   public void debug(String msg) {
      this.logger.debug(msg);
   }

   public void debug(String msg, Throwable t) {
      this.logger.debug(msg, t);
   }
}
