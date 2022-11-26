package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NIf extends NNode {
   static final long serialVersionUID = -3744458754599083332L;
   public NNode test;
   public NBlock body;
   public NBlock orelse;

   public NIf(NNode test, NBlock body, NBlock orelse) {
      this(test, body, orelse, 0, 1);
   }

   public NIf(NNode test, NBlock body, NBlock orelse, int start, int end) {
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
      return "<If:" + this.start() + ":" + this.test + ":" + this.body + ":" + this.orelse + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.test, v);
         this.visitNode(this.body, v);
         this.visitNode(this.orelse, v);
      }

   }
}
