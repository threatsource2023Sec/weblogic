package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NListType;
import org.python.indexer.types.NType;

public class NYield extends NNode {
   static final long serialVersionUID = 2639481204205358048L;
   public NNode value;

   public NYield(NNode n) {
      this(n, 0, 1);
   }

   public NYield(NNode n, int start, int end) {
      super(start, end);
      this.value = n;
      this.addChildren(new NNode[]{n});
   }

   public NType resolve(Scope s) throws Exception {
      return this.setType(new NListType(resolveExpr(this.value, s)));
   }

   public String toString() {
      return "<Yield:" + this.start() + ":" + this.value + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.value, v);
      }

   }
}
