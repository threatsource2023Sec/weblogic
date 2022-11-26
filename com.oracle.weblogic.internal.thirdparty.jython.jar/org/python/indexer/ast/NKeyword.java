package org.python.indexer.ast;

import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NKeyword extends NNode {
   static final long serialVersionUID = 9031782645918578266L;
   public String arg;
   public NNode value;

   public NKeyword(String arg, NNode value) {
      this(arg, value, 0, 1);
   }

   public NKeyword(String arg, NNode value, int start, int end) {
      super(start, end);
      this.arg = arg;
      this.value = value;
      this.addChildren(new NNode[]{value});
   }

   public NType resolve(Scope s) throws Exception {
      return this.setType(resolveExpr(this.value, s));
   }

   public String toString() {
      return "<Keyword:" + this.arg + ":" + this.value + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.value, v);
      }

   }
}
