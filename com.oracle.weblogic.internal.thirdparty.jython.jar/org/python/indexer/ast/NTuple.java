package org.python.indexer.ast;

import java.util.Iterator;
import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NTupleType;
import org.python.indexer.types.NType;

public class NTuple extends NSequence {
   static final long serialVersionUID = -7647425038559142921L;

   public NTuple(List elts) {
      this(elts, 0, 1);
   }

   public NTuple(List elts, int start, int end) {
      super(elts, start, end);
   }

   public NType resolve(Scope s) throws Exception {
      NTupleType thisType = new NTupleType();
      Iterator var3 = this.elts.iterator();

      while(var3.hasNext()) {
         NNode e = (NNode)var3.next();
         thisType.add(resolveExpr(e, s));
      }

      return this.setType(thisType);
   }

   public String toString() {
      return "<Tuple:" + this.start() + ":" + this.elts + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.elts, v);
      }

   }
}
