package antlr;

class TokenRangeElement extends AlternativeElement {
   String label;
   protected int begin = 0;
   protected int end = 0;
   protected String beginText;
   protected String endText;

   public TokenRangeElement(Grammar var1, Token var2, Token var3, int var4) {
      super(var1, var2, var4);
      this.begin = this.grammar.tokenManager.getTokenSymbol(var2.getText()).getTokenType();
      this.beginText = var2.getText();
      this.end = this.grammar.tokenManager.getTokenSymbol(var3.getText()).getTokenType();
      this.endText = var3.getText();
      this.line = var2.getLine();
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
