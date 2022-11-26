package antlr;

public class CommonHiddenStreamToken extends CommonToken {
   protected CommonHiddenStreamToken hiddenBefore;
   protected CommonHiddenStreamToken hiddenAfter;

   public CommonHiddenStreamToken() {
   }

   public CommonHiddenStreamToken(int var1, String var2) {
      super(var1, var2);
   }

   public CommonHiddenStreamToken(String var1) {
      super(var1);
   }

   public CommonHiddenStreamToken getHiddenAfter() {
      return this.hiddenAfter;
   }

   public CommonHiddenStreamToken getHiddenBefore() {
      return this.hiddenBefore;
   }

   protected void setHiddenAfter(CommonHiddenStreamToken var1) {
      this.hiddenAfter = var1;
   }

   protected void setHiddenBefore(CommonHiddenStreamToken var1) {
      this.hiddenBefore = var1;
   }
}
