package antlr;

class StringLiteralSymbol extends TokenSymbol {
   protected String label;

   public StringLiteralSymbol(String var1) {
      super(var1);
   }

   public String getLabel() {
      return this.label;
   }

   public void setLabel(String var1) {
      this.label = var1;
   }
}
