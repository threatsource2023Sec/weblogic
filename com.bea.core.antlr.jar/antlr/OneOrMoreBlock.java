package antlr;

class OneOrMoreBlock extends BlockWithImpliedExitPath {
   public OneOrMoreBlock(Grammar var1) {
      super(var1);
   }

   public OneOrMoreBlock(Grammar var1, Token var2) {
      super(var1, var2);
   }

   public void generate() {
      this.grammar.generator.gen(this);
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public String toString() {
      return super.toString() + "+";
   }
}
