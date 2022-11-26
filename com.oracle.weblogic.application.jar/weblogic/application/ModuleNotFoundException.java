package weblogic.application;

import weblogic.j2ee.J2EELogger;
import weblogic.utils.StringUtils;

public class ModuleNotFoundException extends NonFatalDeploymentException {
   static final long serialVersionUID = -9003975495054439803L;

   public ModuleNotFoundException(String s) {
      super(s);
   }

   public ModuleNotFoundException(String[] moduleIds) {
      super(J2EELogger.logUrisDidntMatchModulesLoggable(StringUtils.join(moduleIds, ",")).getMessage());
   }
}
