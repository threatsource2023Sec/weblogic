package weblogic.application.ddconvert;

public class DDConvertException extends Exception {
   public DDConvertException() {
   }

   public DDConvertException(String msg) {
      super(msg);
   }

   public DDConvertException(Throwable th) {
      super(th);
   }

   public DDConvertException(String msg, Throwable th) {
      super(msg, th);
   }
}
