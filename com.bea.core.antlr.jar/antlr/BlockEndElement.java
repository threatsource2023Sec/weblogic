package antlr;

class BlockEndElement extends AlternativeElement {
   protected boolean[] lock;
   protected AlternativeBlock block;

   public BlockEndElement(Grammar var1) {
      super(var1);
      this.lock = new boolean[var1.maxk + 1];
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public String toString() {
      return "";
   }
}
