package org.python.antlr.ast;

import org.python.antlr.PythonTree;
import org.python.antlr.base.mod;

public class ErrorMod extends mod {
   public ErrorMod(PythonTree tree) {
      super(tree);
   }

   public String toString() {
      return "ErrorMod";
   }

   public String toStringTree() {
      return "ErrorMod";
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
