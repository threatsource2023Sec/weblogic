package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NExprStmt extends NNode {
   static final long serialVersionUID = 7366113211576923188L;
   public NNode value;

   public NExprStmt(NNode n) {
      this(n, 0, 1);
   }

   public NExprStmt(NNode n, int start, int end) {
      super(start, end);
      this.value = n;
      this.addChildren(new NNode[]{n});
   }

   public NType resolve(Scope s) throws Exception {
      resolveExpr(this.value, s);
      return this.getType();
   }

   public String toString() {
      return "<ExprStmt:" + this.value + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.value, v);
      }

   }
}
