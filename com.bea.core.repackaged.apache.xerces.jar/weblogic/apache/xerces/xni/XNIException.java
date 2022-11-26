package weblogic.apache.xerces.xni;

public class XNIException extends RuntimeException {
   static final long serialVersionUID = 9019819772686063775L;
   private Exception fException = this;

   public XNIException(String var1) {
      super(var1);
   }

   public XNIException(Exception var1) {
      super(var1.getMessage());
      this.fException = var1;
   }

   public XNIException(String var1, Exception var2) {
      super(var1);
      this.fException = var2;
   }

   public Exception getException() {
      return this.fException != this ? this.fException : null;
   }

   public synchronized Throwable initCause(Throwable var1) {
      if (this.fException != this) {
         throw new IllegalStateException();
      } else if (var1 == this) {
         throw new IllegalArgumentException();
      } else {
         this.fException = (Exception)var1;
         return this;
      }
   }

   public Throwable getCause() {
      return this.getException();
   }
}
