package antlr;

class CSharpBlockFinishingInfo {
   String postscript;
   boolean generatedSwitch;
   boolean generatedAnIf;
   boolean needAnErrorClause;

   public CSharpBlockFinishingInfo() {
      this.postscript = null;
      this.generatedSwitch = this.generatedSwitch = false;
      this.needAnErrorClause = true;
   }

   public CSharpBlockFinishingInfo(String var1, boolean var2, boolean var3, boolean var4) {
      this.postscript = var1;
      this.generatedSwitch = var2;
      this.generatedAnIf = var3;
      this.needAnErrorClause = var4;
   }
}
