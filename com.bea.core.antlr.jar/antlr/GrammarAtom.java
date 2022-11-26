package antlr;

abstract class GrammarAtom extends AlternativeElement {
   protected String label;
   protected String atomText;
   protected int tokenType = 0;
   protected boolean not = false;
   protected String ASTNodeType = null;

   public GrammarAtom(Grammar var1, Token var2, int var3) {
      super(var1, var2, var3);
      this.atomText = var2.getText();
   }

   public String getLabel() {
      return this.label;
   }

   public String getText() {
      return this.atomText;
   }

   public int getType() {
      return this.tokenType;
   }

   public void setLabel(String var1) {
      this.label = var1;
   }

   public String getASTNodeType() {
      return this.ASTNodeType;
   }

   public void setASTNodeType(String var1) {
      this.ASTNodeType = var1;
   }

   public void setOption(Token var1, Token var2) {
      if (var1.getText().equals("AST")) {
         this.setASTNodeType(var2.getText());
      } else {
         this.grammar.antlrTool.error("Invalid element option:" + var1.getText(), this.grammar.getFilename(), var1.getLine(), var1.getColumn());
      }

   }

   public String toString() {
      String var1 = " ";
      if (this.label != null) {
         var1 = var1 + this.label + ":";
      }

      if (this.not) {
         var1 = var1 + "~";
      }

      return var1 + this.atomText;
   }
}
