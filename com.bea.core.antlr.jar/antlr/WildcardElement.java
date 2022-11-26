package antlr;

class WildcardElement extends GrammarAtom {
   protected String label;

   public WildcardElement(Grammar var1, Token var2, int var3) {
      super(var1, var2, var3);
      this.line = var2.getLine();
   }

   public void generate() {
      this.grammar.generator.gen(this);
   }

   public String getLabel() {
      return this.label;
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public void setLabel(String var1) {
      this.label = var1;
   }

   public String toString() {
      String var1 = " ";
      if (this.label != null) {
         var1 = var1 + this.label + ":";
      }

      return var1 + ".";
   }
}
