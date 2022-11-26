package org.python.indexer.ast;

import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NPrint extends NNode {
   static final long serialVersionUID = 689872588518071148L;
   public NNode dest;
   public List values;

   public NPrint(NNode dest, List elts) {
      this(dest, elts, 0, 1);
   }

   public NPrint(NNode dest, List elts, int start, int end) {
      super(start, end);
      this.dest = dest;
      this.values = elts;
      this.addChildren(new NNode[]{dest});
      this.addChildren(elts);
   }

   public NType resolve(Scope s) throws Exception {
      resolveExpr(this.dest, s);
      this.resolveList(this.values, s);
      return this.getType();
   }

   public String toString() {
      return "<Print:" + this.values + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.dest, v);
         this.visitNodeList(this.values, v);
      }

   }
}
