package antlr;

class CharRangeElement extends AlternativeElement {
   String label;
   protected char begin = 0;
   protected char end = 0;
   protected String beginText;
   protected String endText;

   public CharRangeElement(LexerGrammar var1, Token var2, Token var3, int var4) {
      super(var1);
      this.begin = (char)ANTLRLexer.tokenTypeForCharLiteral(var2.getText());
      this.beginText = var2.getText();
      this.end = (char)ANTLRLexer.tokenTypeForCharLiteral(var3.getText());
      this.endText = var3.getText();
      this.line = var2.getLine();

      for(int var5 = this.begin; var5 <= this.end; ++var5) {
         var1.charVocabulary.add(var5);
      }

      this.autoGenType = var4;
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
      return this.label != null ? " " + this.label + ":" + this.beginText + ".." + this.endText : " " + this.beginText + ".." + this.endText;
   }
}
