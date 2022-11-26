package org.python.indexer.ast;

import org.python.indexer.Indexer;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NRepr extends NNode {
   static final long serialVersionUID = -7920982714296311413L;
   public NNode value;

   public NRepr(NNode n) {
      this(n, 0, 1);
   }

   public NRepr(NNode n, int start, int end) {
      super(start, end);
      this.value = n;
      this.addChildren(new NNode[]{n});
   }

   public NType resolve(Scope s) throws Exception {
      resolveExpr(this.value, s);
      return this.setType(Indexer.idx.builtins.BaseStr);
   }

   public String toString() {
      return "<Repr:" + this.value + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.value, v);
      }

   }
}
