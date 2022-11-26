package antlr;

public class Token implements Cloneable {
   public static final int MIN_USER_TYPE = 4;
   public static final int NULL_TREE_LOOKAHEAD = 3;
   public static final int INVALID_TYPE = 0;
   public static final int EOF_TYPE = 1;
   public static final int SKIP = -1;
   protected int type = 0;
   public static Token badToken = new Token(0, "<no text>");

   public Token() {
   }

   public Token(int var1) {
      this.type = var1;
   }

   public Token(int var1, String var2) {
      this.type = var1;
      this.setText(var2);
   }

   public int getColumn() {
      return 0;
   }

   public int getLine() {
      return 0;
   }

   public String getFilename() {
      return null;
   }

   public void setFilename(String var1) {
   }

   public String getText() {
      return "<no text>";
   }

   public void setText(String var1) {
   }

   public void setColumn(int var1) {
   }

   public void setLine(int var1) {
   }

   public int getType() {
      return this.type;
   }

   public void setType(int var1) {
      this.type = var1;
   }

   public String toString() {
      return "[\"" + this.getText() + "\",<" + this.getType() + ">]";
   }
}
