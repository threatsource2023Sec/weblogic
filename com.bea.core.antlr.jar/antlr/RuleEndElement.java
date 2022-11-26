package antlr;

class RuleEndElement extends BlockEndElement {
   protected Lookahead[] cache;
   protected boolean noFOLLOW;

   public RuleEndElement(Grammar var1) {
      super(var1);
      this.cache = new Lookahead[var1.maxk + 1];
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public String toString() {
      return "";
   }
}
