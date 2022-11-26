package antlr;

public class TokenWithIndex extends CommonToken {
   int index;

   public TokenWithIndex() {
   }

   public TokenWithIndex(int var1, String var2) {
      super(var1, var2);
   }

   public void setIndex(int var1) {
      this.index = var1;
   }

   public int getIndex() {
      return this.index;
   }

   public String toString() {
      return "[" + this.index + ":\"" + this.getText() + "\",<" + this.getType() + ">,line=" + this.line + ",col=" + this.col + "]\n";
   }
}
