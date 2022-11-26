package org.python.antlr.ast;

import org.python.antlr.PythonTree;
import org.python.antlr.base.expr;

public class ErrorExpr extends expr {
   public ErrorExpr(PythonTree tree) {
      super(tree);
   }

   public String toString() {
      return "ErrorExpr";
   }

   public String toStringTree() {
      return "ErrorExpr";
   }

   public int getLineno() {
      return this.getLine();
   }

   public int getCol_offset() {
      return this.getCharPositionInLine();
   }

   public Object accept(VisitorIF visitor) {
      return null;
   }

   public void traverse(VisitorIF visitor) throws Exception {
   }
}
