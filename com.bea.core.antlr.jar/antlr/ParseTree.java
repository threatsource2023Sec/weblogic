package antlr;

import antlr.collections.AST;

public abstract class ParseTree extends BaseAST {
   public String getLeftmostDerivationStep(int var1) {
      if (var1 <= 0) {
         return this.toString();
      } else {
         StringBuffer var2 = new StringBuffer(2000);
         this.getLeftmostDerivation(var2, var1);
         return var2.toString();
      }
   }

   public String getLeftmostDerivation(int var1) {
      StringBuffer var2 = new StringBuffer(2000);
      var2.append("    " + this.toString());
      var2.append("\n");

      for(int var3 = 1; var3 < var1; ++var3) {
         var2.append(" =>");
         var2.append(this.getLeftmostDerivationStep(var3));
         var2.append("\n");
      }

      return var2.toString();
   }

   protected abstract int getLeftmostDerivation(StringBuffer var1, int var2);

   public void initialize(int var1, String var2) {
   }

   public void initialize(AST var1) {
   }

   public void initialize(Token var1) {
   }
}
