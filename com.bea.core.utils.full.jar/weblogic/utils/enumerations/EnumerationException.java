package weblogic.utils.enumerations;

public class EnumerationException extends RuntimeException {
   private Exception realException;

   public EnumerationException() {
   }

   public EnumerationException(String msg) {
      super(msg);
   }

   public EnumerationException(Exception realException) {
      this.realException = realException;
   }

   public EnumerationException(String msg, Exception realException) {
      super(msg);
      this.realException = realException;
   }

   public Exception getRealException() {
      return this.realException;
   }

   public String toString() {
      return super.toString() + ": <" + this.realException.toString() + ">";
   }
}
