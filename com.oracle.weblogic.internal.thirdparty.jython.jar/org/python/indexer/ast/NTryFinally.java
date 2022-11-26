package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NTryFinally extends NNode {
   static final long serialVersionUID = 136428581711609107L;
   public NBlock body;
   public NBlock finalbody;

   public NTryFinally(NBlock body, NBlock orelse) {
      this(body, orelse, 0, 1);
   }

   public NTryFinally(NBlock body, NBlock orelse, int start, int end) {
      super(start, end);
      this.body = body;
      this.finalbody = orelse;
      this.addChildren(new NNode[]{body, orelse});
   }

   public NType resolve(Scope s) throws Exception {
      if (this.body != null) {
         this.setType(resolveExpr(this.body, s));
      }

      if (this.finalbody != null) {
         this.addType(resolveExpr(this.finalbody, s));
      }

      return this.getType();
   }

   public String toString() {
      return "<TryFinally:" + this.body + ":" + this.finalbody + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.body, v);
         this.visitNode(this.finalbody, v);
      }

   }
}
