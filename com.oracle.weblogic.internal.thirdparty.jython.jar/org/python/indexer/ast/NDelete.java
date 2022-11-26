package org.python.indexer.ast;

import java.util.Iterator;
import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NDelete extends NNode {
   static final long serialVersionUID = -2223255555054110766L;
   public List targets;

   public NDelete(List elts) {
      this(elts, 0, 1);
   }

   public NDelete(List elts, int start, int end) {
      super(start, end);
      this.targets = elts;
      this.addChildren(elts);
   }

   public NType resolve(Scope s) throws Exception {
      Iterator var2 = this.targets.iterator();

      while(var2.hasNext()) {
         NNode n = (NNode)var2.next();
         resolveExpr(n, s);
         if (n instanceof NName) {
            s.remove(((NName)n).id);
         }
      }

      return this.getType();
   }

   public String toString() {
      return "<Delete:" + this.targets + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.targets, v);
      }

   }
}
