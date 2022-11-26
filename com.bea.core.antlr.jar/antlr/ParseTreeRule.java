package antlr;

import antlr.collections.AST;

public class ParseTreeRule extends ParseTree {
   public static final int INVALID_ALT = -1;
   protected String ruleName;
   protected int altNumber;

   public ParseTreeRule(String var1) {
      this(var1, -1);
   }

   public ParseTreeRule(String var1, int var2) {
      this.ruleName = var1;
      this.altNumber = var2;
   }

   public String getRuleName() {
      return this.ruleName;
   }

   protected int getLeftmostDerivation(StringBuffer var1, int var2) {
      int var3 = 0;
      if (var2 <= 0) {
         var1.append(' ');
         var1.append(this.toString());
         return var3;
      } else {
         AST var4 = this.getFirstChild();

         for(var3 = 1; var4 != null; var4 = var4.getNextSibling()) {
            if (var3 < var2 && !(var4 instanceof ParseTreeToken)) {
               int var5 = var2 - var3;
               int var6 = ((ParseTree)var4).getLeftmostDerivation(var1, var5);
               var3 += var6;
            } else {
               var1.append(' ');
               var1.append(var4.toString());
            }
         }

         return var3;
      }
   }

   public String toString() {
      return this.altNumber == -1 ? '<' + this.ruleName + '>' : '<' + this.ruleName + "[" + this.altNumber + "]>";
   }
}
