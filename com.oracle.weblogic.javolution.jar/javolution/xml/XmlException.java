package javolution.xml;

public class XmlException extends RuntimeException {
   private Throwable _cause;
   private static final long serialVersionUID = 1L;

   public XmlException() {
   }

   public XmlException(String var1) {
      super(var1);
   }

   public XmlException(String var1, Throwable var2) {
      super(var1 + " caused by " + var2);
      this._cause = var2;
   }

   public XmlException(Throwable var1) {
      super("XML exception caused by " + var1);
      this._cause = var1;
   }

   public Throwable getCause() {
      return this._cause;
   }
}
