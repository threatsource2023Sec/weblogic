package weblogic.deploy.api.internal.utils;

import javax.enterprise.deploy.shared.ModuleType;
import weblogic.deploy.api.shared.WebLogicModuleType;

public final class AppMergerFactory {
   public static AppMerger getAppMerger(ModuleType mt) {
      int type = mt.getValue();
      return type != ModuleType.EAR.getValue() && type != ModuleType.WAR.getValue() && type != ModuleType.EJB.getValue() && type != WebLogicModuleType.SCA_COMPOSITE.getValue() ? null : new EarMerger();
   }
}
