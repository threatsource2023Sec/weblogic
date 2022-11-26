package antlr;

class TokenRefElement extends GrammarAtom {
   public TokenRefElement(Grammar var1, Token var2, boolean var3, int var4) {
      super(var1, var2, var4);
      this.not = var3;
      TokenSymbol var5 = this.grammar.tokenManager.getTokenSymbol(this.atomText);
      if (var5 == null) {
         var1.antlrTool.error("Undefined token symbol: " + this.atomText, this.grammar.getFilename(), var2.getLine(), var2.getColumn());
      } else {
         this.tokenType = var5.getTokenType();
         this.setASTNodeType(var5.getASTNodeType());
      }

      this.line = var2.getLine();
   }

   public void generate() {
      this.grammar.generator.gen(this);
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, (GrammarAtom)this);
   }
}
