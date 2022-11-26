package org.python.indexer.ast;

import org.python.indexer.Indexer;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NNum extends NNode {
   static final long serialVersionUID = -425866329526788376L;
   public Object n;

   public NNum(int n) {
      this.n = n;
   }

   public NNum(Object n, int start, int end) {
      super(start, end);
      this.n = n;
   }

   public NType resolve(Scope s) throws Exception {
      return this.setType(Indexer.idx.builtins.BaseNum);
   }

   public String toString() {
      return "<Num:" + this.n + ">";
   }

   public void visit(NNodeVisitor v) {
      v.visit(this);
   }
}
