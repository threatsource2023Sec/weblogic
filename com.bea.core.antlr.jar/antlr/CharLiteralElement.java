package antlr;

class CharLiteralElement extends GrammarAtom {
   public CharLiteralElement(LexerGrammar var1, Token var2, boolean var3, int var4) {
      super(var1, var2, 1);
      this.tokenType = ANTLRLexer.tokenTypeForCharLiteral(var2.getText());
      var1.charVocabulary.add(this.tokenType);
      this.line = var2.getLine();
      this.not = var3;
      this.autoGenType = var4;
   }

   public void generate() {
      this.grammar.generator.gen(this);
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }
}
