package antlr;

import antlr.collections.AST;

public class ASTPair {
   public AST root;
   public AST child;

   public final void advanceChildToEnd() {
      if (this.child != null) {
         while(this.child.getNextSibling() != null) {
            this.child = this.child.getNextSibling();
         }
      }

   }

   public ASTPair copy() {
      ASTPair var1 = new ASTPair();
      var1.root = this.root;
      var1.child = this.child;
      return var1;
   }

   public String toString() {
      String var1 = this.root == null ? "null" : this.root.getText();
      String var2 = this.child == null ? "null" : this.child.getText();
      return "[" + var1 + "," + var2 + "]";
   }
}
