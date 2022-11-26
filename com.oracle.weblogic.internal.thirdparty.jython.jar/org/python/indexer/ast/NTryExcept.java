package org.python.indexer.ast;

import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NTryExcept extends NNode {
   static final long serialVersionUID = 7210847998428480831L;
   public List handlers;
   public NBlock body;
   public NBlock orelse;

   public NTryExcept(List handlers, NBlock body, NBlock orelse) {
      this(handlers, body, orelse, 0, 1);
   }

   public NTryExcept(List handlers, NBlock body, NBlock orelse, int start, int end) {
      super(start, end);
      this.handlers = handlers;
      this.body = body;
      this.orelse = orelse;
      this.addChildren(handlers);
      this.addChildren(new NNode[]{body, orelse});
   }

   public NType resolve(Scope s) throws Exception {
      this.resolveList(this.handlers, s);
      if (this.body != null) {
         this.setType(resolveExpr(this.body, s));
      }

      if (this.orelse != null) {
         this.addType(resolveExpr(this.orelse, s));
      }

      return this.getType();
   }

   public String toString() {
      return "<TryExcept:" + this.handlers + ":" + this.body + ":" + this.orelse + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.handlers, v);
         this.visitNode(this.body, v);
         this.visitNode(this.orelse, v);
      }

   }
}
