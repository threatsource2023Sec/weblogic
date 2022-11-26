package antlr;

class ActionElement extends AlternativeElement {
   protected String actionText;
   protected boolean isSemPred = false;

   public ActionElement(Grammar var1, Token var2) {
      super(var1);
      this.actionText = var2.getText();
      this.line = var2.getLine();
      this.column = var2.getColumn();
   }

   public void generate() {
      this.grammar.generator.gen(this);
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public String toString() {
      return " " + this.actionText + (this.isSemPred ? "?" : "");
   }
}
