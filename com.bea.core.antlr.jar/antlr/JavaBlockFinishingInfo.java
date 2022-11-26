package antlr;

class JavaBlockFinishingInfo {
   String postscript;
   boolean generatedSwitch;
   boolean generatedAnIf;
   boolean needAnErrorClause;

   public JavaBlockFinishingInfo() {
      this.postscript = null;
      this.generatedSwitch = this.generatedSwitch = false;
      this.needAnErrorClause = true;
   }

   public JavaBlockFinishingInfo(String var1, boolean var2, boolean var3, boolean var4) {
      this.postscript = var1;
      this.generatedSwitch = var2;
      this.generatedAnIf = var3;
      this.needAnErrorClause = var4;
   }
}
