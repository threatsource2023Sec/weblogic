package antlr;

abstract class GrammarSymbol {
   protected String id;

   public GrammarSymbol() {
   }

   public GrammarSymbol(String var1) {
      this.id = var1;
   }

   public String getId() {
      return this.id;
   }

   public void setId(String var1) {
      this.id = var1;
   }
}
