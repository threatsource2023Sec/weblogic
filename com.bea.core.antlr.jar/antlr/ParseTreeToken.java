package antlr;

public class ParseTreeToken extends ParseTree {
   protected Token token;

   public ParseTreeToken(Token var1) {
      this.token = var1;
   }

   protected int getLeftmostDerivation(StringBuffer var1, int var2) {
      var1.append(' ');
      var1.append(this.toString());
      return var2;
   }

   public String toString() {
      return this.token != null ? this.token.getText() : "<missing token>";
   }
}
