package org.python.indexer.ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.python.indexer.Indexer;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NBlock extends NNode {
   static final long serialVersionUID = -9096405259154069107L;
   public List seq;

   public NBlock(List seq) {
      this(seq, 0, 1);
   }

   public NBlock(List seq, int start, int end) {
      super(start, end);
      if (seq == null) {
         seq = new ArrayList();
      }

      this.seq = (List)seq;
      this.addChildren((List)seq);
   }

   public NType resolve(Scope scope) throws Exception {
      Iterator var2 = this.seq.iterator();

      while(var2.hasNext()) {
         NNode n = (NNode)var2.next();
         NType returnType = resolveExpr(n, scope);
         if (returnType != Indexer.idx.builtins.None) {
            this.setType(returnType);
         }
      }

      return this.getType();
   }

   public String toString() {
      return "<Block:" + this.seq + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.seq, v);
      }

   }
}
