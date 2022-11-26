package antlr;

abstract class AlternativeElement extends GrammarElement {
   AlternativeElement next;
   protected int autoGenType = 1;
   protected String enclosingRuleName;

   public AlternativeElement(Grammar var1) {
      super(var1);
   }

   public AlternativeElement(Grammar var1, Token var2) {
      super(var1, var2);
   }

   public AlternativeElement(Grammar var1, Token var2, int var3) {
      super(var1, var2);
      this.autoGenType = var3;
   }

   public int getAutoGenType() {
      return this.autoGenType;
   }

   public void setAutoGenType(int var1) {
      this.autoGenType = var1;
   }

   public String getLabel() {
      return null;
   }

   public void setLabel(String var1) {
   }
}
