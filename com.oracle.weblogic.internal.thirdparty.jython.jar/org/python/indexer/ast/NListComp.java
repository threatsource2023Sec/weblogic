package org.python.indexer.ast;

import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NListType;
import org.python.indexer.types.NType;

public class NListComp extends NNode {
   static final long serialVersionUID = -150205687457446323L;
   public NNode elt;
   public List generators;

   public NListComp(NNode elt, List generators) {
      this(elt, generators, 0, 1);
   }

   public NListComp(NNode elt, List generators, int start, int end) {
      super(start, end);
      this.elt = elt;
      this.generators = generators;
      this.addChildren(new NNode[]{elt});
      this.addChildren(generators);
   }

   public NType resolve(Scope s) throws Exception {
      NameBinder binder = NameBinder.make();
      this.resolveList(this.generators, s);
      return this.setType(new NListType(resolveExpr(this.elt, s)));
   }

   public String toString() {
      return "<NListComp:" + this.start() + ":" + this.elt + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.elt, v);
         this.visitNodeList(this.generators, v);
      }

   }
}
