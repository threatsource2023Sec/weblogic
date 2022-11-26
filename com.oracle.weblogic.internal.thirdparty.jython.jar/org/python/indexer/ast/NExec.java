package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NExec extends NNode {
   static final long serialVersionUID = -1840017898177850339L;
   public NNode body;
   public NNode globals;
   public NNode locals;

   public NExec(NNode body, NNode globals, NNode locals) {
      this(body, globals, locals, 0, 1);
   }

   public NExec(NNode body, NNode globals, NNode locals, int start, int end) {
      super(start, end);
      this.body = body;
      this.globals = globals;
      this.locals = locals;
      this.addChildren(new NNode[]{body, globals, locals});
   }

   public NType resolve(Scope s) throws Exception {
      resolveExpr(this.body, s);
      resolveExpr(this.globals, s);
      resolveExpr(this.locals, s);
      return this.getType();
   }

   public String toString() {
      return "<Exec:" + this.start() + ":" + this.end() + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.body, v);
         this.visitNode(this.globals, v);
         this.visitNode(this.locals, v);
      }

   }
}
