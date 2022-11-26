package antlr.preprocessor;

class Option {
   protected String name;
   protected String rhs;
   protected Grammar enclosingGrammar;

   public Option(String var1, String var2, Grammar var3) {
      this.name = var1;
      this.rhs = var2;
      this.setEnclosingGrammar(var3);
   }

   public Grammar getEnclosingGrammar() {
      return this.enclosingGrammar;
   }

   public String getName() {
      return this.name;
   }

   public String getRHS() {
      return this.rhs;
   }

   public void setEnclosingGrammar(Grammar var1) {
      this.enclosingGrammar = var1;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setRHS(String var1) {
      this.rhs = var1;
   }

   public String toString() {
      return "\t" + this.name + "=" + this.rhs;
   }
}
