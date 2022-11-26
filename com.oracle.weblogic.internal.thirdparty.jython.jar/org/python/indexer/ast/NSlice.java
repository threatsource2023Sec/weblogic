package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NListType;
import org.python.indexer.types.NType;

public class NSlice extends NNode {
   static final long serialVersionUID = 8685364390631331543L;
   public NNode lower;
   public NNode step;
   public NNode upper;

   public NSlice(NNode lower, NNode step, NNode upper) {
      this(lower, step, upper, 0, 1);
   }

   public NSlice(NNode lower, NNode step, NNode upper, int start, int end) {
      super(start, end);
      this.lower = lower;
      this.step = step;
      this.upper = upper;
      this.addChildren(new NNode[]{lower, step, upper});
   }

   public NType resolve(Scope s) throws Exception {
      resolveExpr(this.lower, s);
      resolveExpr(this.step, s);
      resolveExpr(this.upper, s);
      return this.setType(new NListType());
   }

   public String toString() {
      return "<Slice:" + this.lower + ":" + this.step + ":" + this.upper + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.lower, v);
         this.visitNode(this.step, v);
         this.visitNode(this.upper, v);
      }

   }
}
