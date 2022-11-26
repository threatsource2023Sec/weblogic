package antlr;

class CppBlockFinishingInfo {
   String postscript;
   boolean generatedSwitch;
   boolean generatedAnIf;
   boolean needAnErrorClause;

   public CppBlockFinishingInfo() {
      this.postscript = null;
      this.generatedSwitch = false;
      this.needAnErrorClause = true;
   }

   public CppBlockFinishingInfo(String var1, boolean var2, boolean var3, boolean var4) {
      this.postscript = var1;
      this.generatedSwitch = var2;
      this.generatedAnIf = var3;
      this.needAnErrorClause = var4;
   }
}
