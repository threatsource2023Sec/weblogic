package weblogic.application;

import weblogic.management.configuration.TargetMBean;

public class ModuleListenerCtxImpl implements ModuleListenerCtx {
   private final String appId;
   private final String moduleUri;
   private final TargetMBean target;
   private final String type;

   public ModuleListenerCtxImpl(String appId, String moduleUri, TargetMBean target, String type) {
      this.appId = appId;
      this.moduleUri = moduleUri;
      this.target = target;
      this.type = type;
   }

   public String getApplicationId() {
      return this.appId;
   }

   public String getModuleUri() {
      return this.moduleUri;
   }

   public TargetMBean getTarget() {
      return this.target;
   }

   public String getType() {
      return this.type;
   }

   public String toString() {
      StringBuffer t = new StringBuffer("Target=");
      if (this.target != null) {
         t.append(this.target.getType()).append("/").append(this.target.getName());
      } else {
         t.append("null");
      }

      return (new StringBuffer("ModuleCtx[appId=")).append(this.appId).append(", modId=").append(this.moduleUri).append(", type=").append(this.type).append(", ").append(t).append("]").toString();
   }
}
