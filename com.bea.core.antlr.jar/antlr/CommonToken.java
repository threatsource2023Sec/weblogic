package antlr;

public class CommonToken extends Token {
   protected int line;
   protected String text = null;
   protected int col;

   public CommonToken() {
   }

   public CommonToken(int var1, String var2) {
      this.type = var1;
      this.setText(var2);
   }

   public CommonToken(String var1) {
      this.text = var1;
   }

   public int getLine() {
      return this.line;
   }

   public String getText() {
      return this.text;
   }

   public void setLine(int var1) {
      this.line = var1;
   }

   public void setText(String var1) {
      this.text = var1;
   }

   public String toString() {
      return "[\"" + this.getText() + "\",<" + this.type + ">,line=" + this.line + ",col=" + this.col + "]";
   }

   public int getColumn() {
      return this.col;
   }

   public void setColumn(int var1) {
      this.col = var1;
   }
}
