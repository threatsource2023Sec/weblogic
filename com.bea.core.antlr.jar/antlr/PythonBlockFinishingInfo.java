package antlr;

class PythonBlockFinishingInfo {
   String postscript;
   boolean generatedSwitch;
   boolean generatedAnIf;
   boolean needAnErrorClause;

   public PythonBlockFinishingInfo() {
      this.postscript = null;
      this.generatedSwitch = this.generatedSwitch = false;
      this.needAnErrorClause = true;
   }

   public PythonBlockFinishingInfo(String var1, boolean var2, boolean var3, boolean var4) {
      this.postscript = var1;
      this.generatedSwitch = var2;
      this.generatedAnIf = var3;
      this.needAnErrorClause = var4;
   }
}
