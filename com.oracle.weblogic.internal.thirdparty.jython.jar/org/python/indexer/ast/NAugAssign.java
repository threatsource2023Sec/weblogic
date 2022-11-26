package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NAugAssign extends NNode {
   static final long serialVersionUID = -6479618862099506199L;
   public NNode target;
   public NNode value;
   public String op;

   public NAugAssign(NNode target, NNode value, String op) {
      this(target, value, op, 0, 1);
   }

   public NAugAssign(NNode target, NNode value, String op, int start, int end) {
      super(start, end);
      this.target = target;
      this.value = value;
      this.op = op;
      this.addChildren(new NNode[]{target, value});
   }

   public NType resolve(Scope s) throws Exception {
      resolveExpr(this.target, s);
      return this.setType(resolveExpr(this.value, s));
   }

   public String toString() {
      return "<AugAssign:" + this.target + " " + this.op + "= " + this.value + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.target, v);
         this.visitNode(this.value, v);
      }

   }
}
