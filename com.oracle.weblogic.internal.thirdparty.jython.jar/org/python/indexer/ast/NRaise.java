package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NRaise extends NNode {
   static final long serialVersionUID = 5384576775167988640L;
   public NNode exceptionType;
   public NNode inst;
   public NNode traceback;

   public NRaise(NNode exceptionType, NNode inst, NNode traceback) {
      this(exceptionType, inst, traceback, 0, 1);
   }

   public NRaise(NNode exceptionType, NNode inst, NNode traceback, int start, int end) {
      super(start, end);
      this.exceptionType = exceptionType;
      this.inst = inst;
      this.traceback = traceback;
      this.addChildren(new NNode[]{exceptionType, inst, traceback});
   }

   public NType resolve(Scope s) throws Exception {
      resolveExpr(this.exceptionType, s);
      resolveExpr(this.inst, s);
      resolveExpr(this.traceback, s);
      return this.getType();
   }

   public String toString() {
      return "<Raise:" + this.traceback + ":" + this.exceptionType + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.exceptionType, v);
         this.visitNode(this.inst, v);
         this.visitNode(this.traceback, v);
      }

   }
}
