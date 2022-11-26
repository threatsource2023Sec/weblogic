package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NAssert extends NNode {
   static final long serialVersionUID = 7574732756076428388L;
   public NNode test;
   public NNode msg;

   public NAssert(NNode test, NNode msg) {
      this(test, msg, 0, 1);
   }

   public NAssert(NNode test, NNode msg, int start, int end) {
      super(start, end);
      this.test = test;
      this.msg = msg;
      this.addChildren(new NNode[]{test, msg});
   }

   public NType resolve(Scope s) throws Exception {
      resolveExpr(this.test, s);
      resolveExpr(this.msg, s);
      return this.getType();
   }

   public String toString() {
      return "<Assert:" + this.test + ":" + this.msg + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.test, v);
         this.visitNode(this.msg, v);
      }

   }
}
