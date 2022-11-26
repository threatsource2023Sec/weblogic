package org.python.antlr;

import org.python.antlr.ast.VisitorBase;

public class Visitor extends VisitorBase {
   public void traverse(PythonTree node) throws Exception {
      node.traverse(this);
   }

   public void visit(PythonTree[] nodes) throws Exception {
      for(int i = 0; i < nodes.length; ++i) {
         this.visit(nodes[i]);
      }

   }

   public Object visit(PythonTree node) throws Exception {
      Object ret = node.accept(this);
      return ret;
   }

   protected Object unhandled_node(PythonTree node) throws Exception {
      return this;
   }
}
