package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NWith extends NNode {
   static final long serialVersionUID = 560128079414064421L;
   public NNode optional_vars;
   public NNode context_expr;
   public NBlock body;

   public NWith(NNode optional_vars, NNode context_expr, NBlock body) {
      this(optional_vars, context_expr, body, 0, 1);
   }

   public NWith(NNode optional_vars, NNode context_expr, NBlock body, int start, int end) {
      super(start, end);
      this.optional_vars = optional_vars;
      this.context_expr = context_expr;
      this.body = body;
      this.addChildren(new NNode[]{optional_vars, context_expr, body});
   }

   public NType resolve(Scope s) throws Exception {
      NType val = resolveExpr(this.context_expr, s);
      NameBinder.make().bind(s, this.optional_vars, val);
      return this.setType(resolveExpr(this.body, s));
   }

   public String toString() {
      return "<With:" + this.context_expr + ":" + this.optional_vars + ":" + this.body + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.optional_vars, v);
         this.visitNode(this.context_expr, v);
         this.visitNode(this.body, v);
      }

   }
}
