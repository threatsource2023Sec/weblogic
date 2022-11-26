package antlr;

import antlr.collections.AST;

public class CommonASTWithHiddenTokens extends CommonAST {
   protected CommonHiddenStreamToken hiddenBefore;
   protected CommonHiddenStreamToken hiddenAfter;

   public CommonASTWithHiddenTokens() {
   }

   public CommonASTWithHiddenTokens(Token var1) {
      super(var1);
   }

   public CommonHiddenStreamToken getHiddenAfter() {
      return this.hiddenAfter;
   }

   public CommonHiddenStreamToken getHiddenBefore() {
      return this.hiddenBefore;
   }

   public void initialize(AST var1) {
      this.hiddenBefore = ((CommonASTWithHiddenTokens)var1).getHiddenBefore();
      this.hiddenAfter = ((CommonASTWithHiddenTokens)var1).getHiddenAfter();
      super.initialize(var1);
   }

   public void initialize(Token var1) {
      CommonHiddenStreamToken var2 = (CommonHiddenStreamToken)var1;
      super.initialize((Token)var2);
      this.hiddenBefore = var2.getHiddenBefore();
      this.hiddenAfter = var2.getHiddenAfter();
   }
}
