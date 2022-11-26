package weblogic.application;

import weblogic.management.configuration.TargetMBean;

public interface ModuleListenerCtx {
   String getApplicationId();

   String getModuleUri();

   TargetMBean getTarget();

   String getType();
}
