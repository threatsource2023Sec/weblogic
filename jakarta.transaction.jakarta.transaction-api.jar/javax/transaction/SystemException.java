package javax.transaction;

public class SystemException extends Exception {
   private static final long serialVersionUID = 839699079412719325L;
   public int errorCode;

   public SystemException() {
   }

   public SystemException(String s) {
      super(s);
   }

   public SystemException(int errcode) {
      this.errorCode = errcode;
   }
}
