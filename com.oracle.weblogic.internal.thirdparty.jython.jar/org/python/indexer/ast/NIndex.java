package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NIndex extends NNode {
   static final long serialVersionUID = -8920941673115420849L;
   public NNode value;

   public NIndex(NNode n) {
      this(n, 0, 1);
   }

   public NIndex(NNode n, int start, int end) {
      super(start, end);
      this.value = n;
      this.addChildren(new NNode[]{n});
   }

   public NType resolve(Scope s) throws Exception {
      return this.setType(resolveExpr(this.value, s));
   }

   public String toString() {
      return "<Index:" + this.value + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.value, v);
      }

   }
}
