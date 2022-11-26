package antlr;

public class ANTLRHashString {
   private String s;
   private char[] buf;
   private int len;
   private CharScanner lexer;
   private static final int prime = 151;

   public ANTLRHashString(char[] var1, int var2, CharScanner var3) {
      this.lexer = var3;
      this.setBuffer(var1, var2);
   }

   public ANTLRHashString(CharScanner var1) {
      this.lexer = var1;
   }

   public ANTLRHashString(String var1, CharScanner var2) {
      this.lexer = var2;
      this.setString(var1);
   }

   private final char charAt(int var1) {
      return this.s != null ? this.s.charAt(var1) : this.buf[var1];
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof ANTLRHashString) && !(var1 instanceof String)) {
         return false;
      } else {
         ANTLRHashString var2;
         if (var1 instanceof String) {
            var2 = new ANTLRHashString((String)var1, this.lexer);
         } else {
            var2 = (ANTLRHashString)var1;
         }

         int var3 = this.length();
         if (var2.length() != var3) {
            return false;
         } else {
            int var4;
            if (this.lexer.getCaseSensitiveLiterals()) {
               for(var4 = 0; var4 < var3; ++var4) {
                  if (this.charAt(var4) != var2.charAt(var4)) {
                     return false;
                  }
               }
            } else {
               for(var4 = 0; var4 < var3; ++var4) {
                  if (this.lexer.toLower(this.charAt(var4)) != this.lexer.toLower(var2.charAt(var4))) {
                     return false;
                  }
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      int var1 = 0;
      int var2 = this.length();
      int var3;
      if (this.lexer.getCaseSensitiveLiterals()) {
         for(var3 = 0; var3 < var2; ++var3) {
            var1 = var1 * 151 + this.charAt(var3);
         }
      } else {
         for(var3 = 0; var3 < var2; ++var3) {
            var1 = var1 * 151 + this.lexer.toLower(this.charAt(var3));
         }
      }

      return var1;
   }

   private final int length() {
      return this.s != null ? this.s.length() : this.len;
   }

   public void setBuffer(char[] var1, int var2) {
      this.buf = var1;
      this.len = var2;
      this.s = null;
   }

   public void setString(String var1) {
      this.s = var1;
      this.buf = null;
   }
}
