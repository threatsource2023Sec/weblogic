package antlr;

class ZeroOrMoreBlock extends BlockWithImpliedExitPath {
   public ZeroOrMoreBlock(Grammar var1) {
      super(var1);
   }

   public ZeroOrMoreBlock(Grammar var1, Token var2) {
      super(var1, var2);
   }

   public void generate() {
      this.grammar.generator.gen(this);
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public String toString() {
      return super.toString() + "*";
   }
}
