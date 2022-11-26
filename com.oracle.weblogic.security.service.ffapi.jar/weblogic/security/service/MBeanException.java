package weblogic.security.service;

public class MBeanException extends SecurityServiceRuntimeException {
   public MBeanException() {
   }

   public MBeanException(String msg) {
      super(msg);
   }

   public MBeanException(Throwable nested) {
      super(nested);
   }

   public MBeanException(String msg, Throwable nested) {
      super(msg, nested);
   }
}
