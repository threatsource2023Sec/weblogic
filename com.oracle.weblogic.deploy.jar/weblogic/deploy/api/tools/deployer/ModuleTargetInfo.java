package weblogic.deploy.api.tools.deployer;

import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.api.spi.WebLogicTargetModuleID;
import weblogic.deploy.internal.DeployerTextFormatter;

public class ModuleTargetInfo {
   private String target = null;
   private String module = null;
   protected static final DeployerTextFormatter cat = new DeployerTextFormatter();

   protected ModuleTargetInfo() {
   }

   public ModuleTargetInfo(String s) {
      this.init(s);
   }

   public ModuleTargetInfo(String module, String target) {
      this.setModule(module);
      this.setTarget(target);
   }

   protected void init(String s) {
      int ndx = s.indexOf("@");
      if (ndx != -1) {
         if (s.length() == ndx + 1) {
            throw new IllegalArgumentException(cat.invalidTargetSyntax(s));
         }

         this.setModule(s.substring(0, ndx));
         this.setTarget(s.substring(ndx + 1));
      } else {
         this.setTarget(s);
      }

   }

   public String getTarget() {
      return this.target;
   }

   private void setTarget(String target) {
      this.target = target;
   }

   public String getModule() {
      return this.module;
   }

   private void setModule(String module) {
      this.module = module;
   }

   public TargetModuleID createTmid(String name, Target target, WebLogicDeploymentManager dm) {
      ModuleType mt = WebLogicModuleType.UNKNOWN;
      if (this.getModule() != null) {
         mt = ModuleType.EAR;
      }

      WebLogicTargetModuleID tmid = dm.createTargetModuleID((String)name, (ModuleType)mt, (Target)target);
      if (this.getModule() != null) {
         tmid = dm.createTargetModuleID((TargetModuleID)tmid, (String)this.getModule(), (ModuleType)WebLogicModuleType.UNKNOWN);
      }

      return tmid;
   }
}
