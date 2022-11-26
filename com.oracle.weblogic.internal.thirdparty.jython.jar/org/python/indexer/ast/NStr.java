package org.python.indexer.ast;

import org.python.indexer.Indexer;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NStr extends NNode {
   static final long serialVersionUID = -6092297133232624953L;
   public Object n;

   public NStr() {
      this("");
   }

   public NStr(Object n) {
      this.n = n;
   }

   public NStr(Object n, int start, int end) {
      super(start, end);
      this.n = n;
   }

   public NType resolve(Scope s) throws Exception {
      return this.setType(Indexer.idx.builtins.BaseStr);
   }

   public String toString() {
      return "<Str>";
   }

   public void visit(NNodeVisitor v) {
      v.visit(this);
   }
}
