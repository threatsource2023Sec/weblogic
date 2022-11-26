package antlr;

abstract class BlockWithImpliedExitPath extends AlternativeBlock {
   protected int exitLookaheadDepth;
   protected Lookahead[] exitCache;

   public BlockWithImpliedExitPath(Grammar var1) {
      super(var1);
      this.exitCache = new Lookahead[this.grammar.maxk + 1];
   }

   public BlockWithImpliedExitPath(Grammar var1, Token var2) {
      super(var1, var2, false);
      this.exitCache = new Lookahead[this.grammar.maxk + 1];
   }
}
