package weblogic.transaction;

public class AppSetRollbackOnlyException extends Exception {
   public AppSetRollbackOnlyException() {
   }

   public AppSetRollbackOnlyException(String s) {
      super(s);
   }
}
