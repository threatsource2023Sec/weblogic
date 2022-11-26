package weblogic.deploy.api.tools.deployer;

import java.util.HashSet;
import java.util.Iterator;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.utils.MBeanHomeTool;
import weblogic.management.runtime.AppRuntimeStateRuntimeMBean;

public class ListappsOperation extends Jsr88Operation {
   private HashSet apps;
   private AppRuntimeStateRuntimeMBean appRT;

   public ListappsOperation(MBeanHomeTool tool, Options options) {
      super(tool, options);
   }

   public void setAllowedOptions() {
   }

   public void validate() throws DeployerException {
      super.validate();
   }

   protected void prepareTmids() {
   }

   public void execute() throws Exception {
      this.apps = new HashSet();
      TargetModuleID[] tmids = null;
      Target[] targets = this.dm.getTargets(this.dOpts);

      for(int i = 0; i < WebLogicModuleType.getModuleTypes(); ++i) {
         ModuleType mt = WebLogicModuleType.getModuleType(i);
         if (mt != null) {
            tmids = this.dm.getAvailableModules(mt, targets, this.dOpts);
            if (tmids != null) {
               for(int j = 0; j < tmids.length; ++j) {
                  if (tmids[j].getParentTargetModuleID() == null) {
                     this.apps.add(tmids[j].getModuleID());
                  }
               }
            }
         }
      }

   }

   public int report() {
      if (this.apps.isEmpty()) {
         System.out.println(cat.noAppToList());
      } else {
         Iterator var1 = this.apps.iterator();

         while(var1.hasNext()) {
            String tmid = (String)var1.next();
            String str = " " + ApplicationVersionUtils.getDisplayName(tmid) + this.getActiveState(tmid);
            System.out.println(str);
         }

         System.out.println(cat.appsFound() + " " + this.apps.size());
      }

      return 0;
   }

   public String getOperation() {
      return "listapps";
   }

   private String getActiveState(String appId) {
      if (ApplicationVersionUtils.getVersionId(appId) == null) {
         return "";
      } else {
         AppRuntimeStateRuntimeMBean appRT = this.getAppRuntimeStateMBean();
         if (appRT == null) {
            return "";
         } else {
            Target[] targets = this.dm.getTargets();
            if (targets != null && targets.length != 0 && targets[0] != null) {
               for(int i = 0; i < targets.length; ++i) {
                  String appState = appRT.getCurrentState(appId, targets[i].getName());
                  if ("STATE_ACTIVE".equals(appState)) {
                     return " <ACTIVE VERSION>";
                  }
               }

               return "";
            } else {
               return "";
            }
         }
      }
   }

   private AppRuntimeStateRuntimeMBean getAppRuntimeStateMBean() {
      if (this.appRT == null) {
         try {
            this.appRT = this.dm.getHelper().getAppRuntimeStateMBean();
         } catch (Exception var2) {
         }
      }

      return this.appRT;
   }
}
