package weblogic.application;

import weblogic.management.configuration.TargetMBean;

public class SubModuleListenerCtxImpl extends ModuleListenerCtxImpl implements SubModuleListenerCtx {
   private final String subModuleName;
   private final TargetMBean[] subModuleTargets;

   public SubModuleListenerCtxImpl(String appId, String moduleUri, String type, TargetMBean target, String subModuleName, TargetMBean[] subModuleTargets) {
      super(appId, moduleUri, target, type);
      this.subModuleName = subModuleName;
      this.subModuleTargets = subModuleTargets;
   }

   public String getSubModuleName() {
      return this.subModuleName;
   }

   public TargetMBean[] getSubModuleTargets() {
      return this.subModuleTargets;
   }

   public String toString() {
      StringBuffer t = new StringBuffer("Targets=");
      if (this.subModuleTargets != null) {
         for(int i = 0; i < this.subModuleTargets.length; ++i) {
            TargetMBean target = this.subModuleTargets[i];
            t.append(target.getType()).append("/").append(target.getName() + " ");
         }
      } else {
         t.append("null");
      }

      return (new StringBuffer("SubModuleCtx[appId=")).append(this.getApplicationId()).append(", modId=").append(this.subModuleName + "[" + this.getModuleUri() + "]").append(", type=").append(this.getType()).append(", ").append(t).append("]").toString();
   }
}
