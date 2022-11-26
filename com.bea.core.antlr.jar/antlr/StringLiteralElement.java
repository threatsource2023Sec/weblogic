package antlr;

class StringLiteralElement extends GrammarAtom {
   protected String processedAtomText;

   public StringLiteralElement(Grammar var1, Token var2, int var3) {
      super(var1, var2, var3);
      if (!(var1 instanceof LexerGrammar)) {
         TokenSymbol var4 = this.grammar.tokenManager.getTokenSymbol(this.atomText);
         if (var4 == null) {
            var1.antlrTool.error("Undefined literal: " + this.atomText, this.grammar.getFilename(), var2.getLine(), var2.getColumn());
         } else {
            this.tokenType = var4.getTokenType();
         }
      }

      this.line = var2.getLine();
      this.processedAtomText = new String();

      for(int var6 = 1; var6 < this.atomText.length() - 1; ++var6) {
         char var5 = this.atomText.charAt(var6);
         if (var5 == '\\' && var6 + 1 < this.atomText.length() - 1) {
            ++var6;
            var5 = this.atomText.charAt(var6);
            switch (var5) {
               case 'n':
                  var5 = '\n';
                  break;
               case 'r':
                  var5 = '\r';
                  break;
               case 't':
                  var5 = '\t';
            }
         }

         if (var1 instanceof LexerGrammar) {
            ((LexerGrammar)var1).charVocabulary.add(var5);
         }

         this.processedAtomText = this.processedAtomText + var5;
      }

   }

   public void generate() {
      this.grammar.generator.gen(this);
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }
}
