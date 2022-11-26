package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NUnaryOp extends NNode {
   static final long serialVersionUID = 4877088513200468108L;
   public NNode op;
   public NNode operand;

   public NUnaryOp(NNode op, NNode n) {
      this(op, n, 0, 1);
   }

   public NUnaryOp(NNode op, NNode n, int start, int end) {
      super(start, end);
      this.op = op;
      this.operand = n;
      this.addChildren(new NNode[]{op, n});
   }

   public NType resolve(Scope s) throws Exception {
      return this.setType(resolveExpr(this.operand, s));
   }

   public String toString() {
      return "<UOp:" + this.op + ":" + this.operand + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.op, v);
         this.visitNode(this.operand, v);
      }

   }
}
