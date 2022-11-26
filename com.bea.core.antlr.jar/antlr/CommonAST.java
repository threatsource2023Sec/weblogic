package antlr;

import antlr.collections.AST;

public class CommonAST extends BaseAST {
   int ttype = 0;
   String text;

   public String getText() {
      return this.text;
   }

   public int getType() {
      return this.ttype;
   }

   public void initialize(int var1, String var2) {
      this.setType(var1);
      this.setText(var2);
   }

   public void initialize(AST var1) {
      this.setText(var1.getText());
      this.setType(var1.getType());
   }

   public CommonAST() {
   }

   public CommonAST(Token var1) {
      this.initialize(var1);
   }

   public void initialize(Token var1) {
      this.setText(var1.getText());
      this.setType(var1.getType());
   }

   public void setText(String var1) {
      this.text = var1;
   }

   public void setType(int var1) {
      this.ttype = var1;
   }
}
