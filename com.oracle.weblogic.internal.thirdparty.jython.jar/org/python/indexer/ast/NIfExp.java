package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NIfExp extends NNode {
   static final long serialVersionUID = 8516153579808365723L;
   public NNode test;
   public NNode body;
   public NNode orelse;

   public NIfExp(NNode test, NNode body, NNode orelse) {
      this(test, body, orelse, 0, 1);
   }

   public NIfExp(NNode test, NNode body, NNode orelse, int start, int end) {
      super(start, end);
      this.test = test;
      this.body = body;
      this.orelse = orelse;
      this.addChildren(new NNode[]{test, body, orelse});
   }

   public NType resolve(Scope s) throws Exception {
      resolveExpr(this.test, s);
      if (this.body != null) {
         this.setType(resolveExpr(this.body, s));
      }

      if (this.orelse != null) {
         this.addType(resolveExpr(this.orelse, s));
      }

      return this.getType();
   }

   public String toString() {
      return "<IfExp:" + this.start() + ":" + this.test + ":" + this.body + ":" + this.orelse + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.test, v);
         this.visitNode(this.body, v);
         this.visitNode(this.orelse, v);
      }

   }
}
