package weblogic.application;

public class FatalModuleException extends ModuleException {
   static final long serialVersionUID = 1815268793210759069L;

   public FatalModuleException(String msg) {
      super(msg);
   }

   public FatalModuleException(Throwable th) {
      super(th);
   }

   public FatalModuleException(String msg, Throwable th) {
      super(msg, th);
   }
}
