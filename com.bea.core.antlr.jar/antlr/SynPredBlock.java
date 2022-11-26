package antlr;

class SynPredBlock extends AlternativeBlock {
   public SynPredBlock(Grammar var1) {
      super(var1);
   }

   public SynPredBlock(Grammar var1, Token var2) {
      super(var1, var2, false);
   }

   public void generate() {
      this.grammar.generator.gen((AlternativeBlock)this);
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public String toString() {
      return super.toString() + "=>";
   }
}
