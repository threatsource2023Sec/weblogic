package weblogic.deploy.api.tools.deployer;

import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.Target;
import javax.enterprise.deploy.spi.TargetModuleID;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.deploy.api.spi.WebLogicDeploymentManager;
import weblogic.deploy.internal.DeployerTextFormatter;

public class SubModuleTargetInfo extends ModuleTargetInfo {
   private String submodule = null;
   protected static final DeployerTextFormatter cat = new DeployerTextFormatter();

   public SubModuleTargetInfo(String s) {
      int sdx = s.indexOf("@");
      if (sdx == -1) {
         throw new IllegalArgumentException(cat.invalidTargetSyntax(s));
      } else {
         this.setSubmodule(s.substring(0, sdx));
         this.init(s.substring(sdx + 1));
      }
   }

   public String getSubmodule() {
      return this.submodule;
   }

   public void setSubmodule(String submodule) {
      this.submodule = submodule;
   }

   public TargetModuleID createTmid(String name, Target target, WebLogicDeploymentManager dm) {
      TargetModuleID parent = null;
      TargetModuleID tmid = super.createTmid(name, target, dm);
      TargetModuleID[] children = ((TargetModuleID)tmid).getChildTargetModuleID();
      if (children != null) {
         for(int i = 0; i < children.length; ++i) {
            TargetModuleID child = children[i];
            if (child.getModuleID().equals(this.getModule())) {
               parent = child;
            }
         }

         if (parent == null) {
            throw new AssertionError("Failed to build tmids");
         }
      } else {
         parent = tmid;
      }

      if (this.getSubmodule() != null) {
         tmid = dm.createTargetModuleID((TargetModuleID)parent, (String)this.getSubmodule(), (ModuleType)WebLogicModuleType.SUBMODULE);
      }

      return (TargetModuleID)tmid;
   }
}
