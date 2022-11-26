package weblogic.diagnostics.module;

import weblogic.application.ModuleException;

public class WLDFModuleException extends ModuleException {
   public WLDFModuleException(String msg) {
      super(msg);
   }

   public WLDFModuleException(Throwable th) {
      super(th);
   }

   public WLDFModuleException(String msg, Throwable th) {
      super(msg, th);
   }
}
