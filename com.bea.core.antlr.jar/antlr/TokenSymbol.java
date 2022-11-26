package antlr;

class TokenSymbol extends GrammarSymbol {
   protected int ttype = 0;
   protected String paraphrase = null;
   protected String ASTNodeType;

   public TokenSymbol(String var1) {
      super(var1);
   }

   public String getASTNodeType() {
      return this.ASTNodeType;
   }

   public void setASTNodeType(String var1) {
      this.ASTNodeType = var1;
   }

   public String getParaphrase() {
      return this.paraphrase;
   }

   public int getTokenType() {
      return this.ttype;
   }

   public void setParaphrase(String var1) {
      this.paraphrase = var1;
   }

   public void setTokenType(int var1) {
      this.ttype = var1;
   }
}
