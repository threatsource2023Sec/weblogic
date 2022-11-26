package antlr;

class RuleRefElement extends AlternativeElement {
   protected String targetRule;
   protected String args = null;
   protected String idAssign = null;
   protected String label;

   public RuleRefElement(Grammar var1, Token var2, int var3) {
      super(var1, var2, var3);
      this.targetRule = var2.getText();
      if (var2.type == 24) {
         this.targetRule = CodeGenerator.encodeLexerRuleName(this.targetRule);
      }

   }

   public void generate() {
      this.grammar.generator.gen(this);
   }

   public String getArgs() {
      return this.args;
   }

   public String getIdAssign() {
      return this.idAssign;
   }

   public String getLabel() {
      return this.label;
   }

   public Lookahead look(int var1) {
      return this.grammar.theLLkAnalyzer.look(var1, this);
   }

   public void setArgs(String var1) {
      this.args = var1;
   }

   public void setIdAssign(String var1) {
      this.idAssign = var1;
   }

   public void setLabel(String var1) {
      this.label = var1;
   }

   public String toString() {
      return this.args != null ? " " + this.targetRule + this.args : " " + this.targetRule;
   }
}
