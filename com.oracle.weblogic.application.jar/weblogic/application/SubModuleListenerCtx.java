package weblogic.application;

import weblogic.management.configuration.TargetMBean;

public interface SubModuleListenerCtx {
   String getSubModuleName();

   TargetMBean[] getSubModuleTargets();
}
