package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class NExceptHandler extends NNode {
   static final long serialVersionUID = 6215262228266158119L;
   public NNode name;
   public NNode exceptionType;
   public NBlock body;

   public NExceptHandler(NNode name, NNode exceptionType, NBlock body) {
      this(name, exceptionType, body, 0, 1);
   }

   public NExceptHandler(NNode name, NNode exceptionType, NBlock body, int start, int end) {
      super(start, end);
      this.name = name;
      this.exceptionType = exceptionType;
      this.body = body;
      this.addChildren(new NNode[]{name, exceptionType, body});
   }

   public boolean bindsName() {
      return true;
   }

   protected void bindNames(Scope s) throws Exception {
      if (this.name != null) {
         NameBinder.make().bind(s, (NNode)this.name, new NUnknownType());
      }

   }

   public NType resolve(Scope s) throws Exception {
      NType typeval = new NUnknownType();
      if (this.exceptionType != null) {
         typeval = resolveExpr(this.exceptionType, s);
      }

      if (this.name != null) {
         NameBinder.make().bind(s, (NNode)this.name, (NType)typeval);
      }

      return this.body != null ? this.setType(resolveExpr(this.body, s)) : this.setType(new NUnknownType());
   }

   public String toString() {
      return "<ExceptHandler:" + this.start() + ":" + this.name + ":" + this.exceptionType + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.name, v);
         this.visitNode(this.exceptionType, v);
         this.visitNode(this.body, v);
      }

   }
}
