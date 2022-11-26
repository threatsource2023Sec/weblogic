package antlr;

import antlr.collections.AST;
import antlr.collections.impl.BitSet;

public class MismatchedTokenException extends RecognitionException {
   String[] tokenNames;
   public Token token;
   public AST node;
   String tokenText = null;
   public static final int TOKEN = 1;
   public static final int NOT_TOKEN = 2;
   public static final int RANGE = 3;
   public static final int NOT_RANGE = 4;
   public static final int SET = 5;
   public static final int NOT_SET = 6;
   public int mismatchType;
   public int expecting;
   public int upper;
   public BitSet set;

   public MismatchedTokenException() {
      super("Mismatched Token: expecting any AST node", "<AST>", -1, -1);
   }

   public MismatchedTokenException(String[] var1, AST var2, int var3, int var4, boolean var5) {
      super("Mismatched Token", "<AST>", var2 == null ? -1 : var2.getLine(), var2 == null ? -1 : var2.getColumn());
      this.tokenNames = var1;
      this.node = var2;
      if (var2 == null) {
         this.tokenText = "<empty tree>";
      } else {
         this.tokenText = var2.toString();
      }

      this.mismatchType = var5 ? 4 : 3;
      this.expecting = var3;
      this.upper = var4;
   }

   public MismatchedTokenException(String[] var1, AST var2, int var3, boolean var4) {
      super("Mismatched Token", "<AST>", var2 == null ? -1 : var2.getLine(), var2 == null ? -1 : var2.getColumn());
      this.tokenNames = var1;
      this.node = var2;
      if (var2 == null) {
         this.tokenText = "<empty tree>";
      } else {
         this.tokenText = var2.toString();
      }

      this.mismatchType = var4 ? 2 : 1;
      this.expecting = var3;
   }

   public MismatchedTokenException(String[] var1, AST var2, BitSet var3, boolean var4) {
      super("Mismatched Token", "<AST>", var2 == null ? -1 : var2.getLine(), var2 == null ? -1 : var2.getColumn());
      this.tokenNames = var1;
      this.node = var2;
      if (var2 == null) {
         this.tokenText = "<empty tree>";
      } else {
         this.tokenText = var2.toString();
      }

      this.mismatchType = var4 ? 6 : 5;
      this.set = var3;
   }

   public MismatchedTokenException(String[] var1, Token var2, int var3, int var4, boolean var5, String var6) {
      super("Mismatched Token", var6, var2.getLine(), var2.getColumn());
      this.tokenNames = var1;
      this.token = var2;
      this.tokenText = var2.getText();
      this.mismatchType = var5 ? 4 : 3;
      this.expecting = var3;
      this.upper = var4;
   }

   public MismatchedTokenException(String[] var1, Token var2, int var3, boolean var4, String var5) {
      super("Mismatched Token", var5, var2.getLine(), var2.getColumn());
      this.tokenNames = var1;
      this.token = var2;
      this.tokenText = var2.getText();
      this.mismatchType = var4 ? 2 : 1;
      this.expecting = var3;
   }

   public MismatchedTokenException(String[] var1, Token var2, BitSet var3, boolean var4, String var5) {
      super("Mismatched Token", var5, var2.getLine(), var2.getColumn());
      this.tokenNames = var1;
      this.token = var2;
      this.tokenText = var2.getText();
      this.mismatchType = var4 ? 6 : 5;
      this.set = var3;
   }

   public String getMessage() {
      StringBuffer var1 = new StringBuffer();
      switch (this.mismatchType) {
         case 1:
            var1.append("expecting " + this.tokenName(this.expecting) + ", found '" + this.tokenText + "'");
            break;
         case 2:
            var1.append("expecting anything but " + this.tokenName(this.expecting) + "; got it anyway");
            break;
         case 3:
            var1.append("expecting token in range: " + this.tokenName(this.expecting) + ".." + this.tokenName(this.upper) + ", found '" + this.tokenText + "'");
            break;
         case 4:
            var1.append("expecting token NOT in range: " + this.tokenName(this.expecting) + ".." + this.tokenName(this.upper) + ", found '" + this.tokenText + "'");
            break;
         case 5:
         case 6:
            var1.append("expecting " + (this.mismatchType == 6 ? "NOT " : "") + "one of (");
            int[] var2 = this.set.toArray();

            for(int var3 = 0; var3 < var2.length; ++var3) {
               var1.append(" ");
               var1.append(this.tokenName(var2[var3]));
            }

            var1.append("), found '" + this.tokenText + "'");
            break;
         default:
            var1.append(super.getMessage());
      }

      return var1.toString();
   }

   private String tokenName(int var1) {
      if (var1 == 0) {
         return "<Set of tokens>";
      } else {
         return var1 >= 0 && var1 < this.tokenNames.length ? this.tokenNames[var1] : "<" + String.valueOf(var1) + ">";
      }
   }
}
