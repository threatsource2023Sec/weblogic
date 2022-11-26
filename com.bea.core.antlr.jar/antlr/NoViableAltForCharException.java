package antlr;

public class NoViableAltForCharException extends RecognitionException {
   public char foundChar;

   public NoViableAltForCharException(char var1, CharScanner var2) {
      super("NoViableAlt", var2.getFilename(), var2.getLine(), var2.getColumn());
      this.foundChar = var1;
   }

   /** @deprecated */
   public NoViableAltForCharException(char var1, String var2, int var3) {
      this(var1, var2, var3, -1);
   }

   public NoViableAltForCharException(char var1, String var2, int var3, int var4) {
      super("NoViableAlt", var2, var3, var4);
      this.foundChar = var1;
   }

   public String getMessage() {
      String var1 = "unexpected char: ";
      if (this.foundChar >= ' ' && this.foundChar <= '~') {
         var1 = var1 + '\'';
         var1 = var1 + this.foundChar;
         var1 = var1 + '\'';
      } else {
         var1 = var1 + "0x" + Integer.toHexString(this.foundChar).toUpperCase();
      }

      return var1;
   }
}
