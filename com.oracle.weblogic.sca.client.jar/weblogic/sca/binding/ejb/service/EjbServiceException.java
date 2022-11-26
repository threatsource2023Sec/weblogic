package weblogic.sca.binding.ejb.service;

public class EjbServiceException extends Exception {
   public EjbServiceException() {
   }

   public EjbServiceException(String arg0, Throwable arg1) {
      super(arg0, arg1);
   }

   public EjbServiceException(String arg0) {
      super(arg0);
   }

   public EjbServiceException(Throwable arg0) {
      super(arg0);
   }
}
