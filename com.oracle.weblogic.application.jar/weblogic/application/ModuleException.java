package weblogic.application;

import weblogic.management.DeploymentException;

public class ModuleException extends DeploymentException {
   static final long serialVersionUID = 354496553045662647L;

   public ModuleException() {
      super("");
   }

   public ModuleException(String msg) {
      super(msg);
   }

   public ModuleException(Throwable th) {
      super(th);
   }

   public ModuleException(String msg, Throwable th) {
      super(msg, th);
   }
}
