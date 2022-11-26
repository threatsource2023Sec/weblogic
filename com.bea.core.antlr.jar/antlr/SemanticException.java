package antlr;

public class SemanticException extends RecognitionException {
   public SemanticException(String var1) {
      super(var1);
   }

   /** @deprecated */
   public SemanticException(String var1, String var2, int var3) {
      this(var1, var2, var3, -1);
   }

   public SemanticException(String var1, String var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }
}
