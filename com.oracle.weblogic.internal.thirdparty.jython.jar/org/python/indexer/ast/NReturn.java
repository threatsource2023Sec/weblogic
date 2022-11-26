package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NReturn extends NNode {
   static final long serialVersionUID = 5795610129307339141L;
   public NNode value;

   public NReturn(NNode n) {
      this(n, 0, 1);
   }

   public NReturn(NNode n, int start, int end) {
      super(start, end);
      this.value = n;
      this.addChildren(new NNode[]{n});
   }

   public NType resolve(Scope s) throws Exception {
      return this.setType(resolveExpr(this.value, s));
   }

   public String toString() {
      return "<Return:" + this.value + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.value, v);
      }

   }
}
