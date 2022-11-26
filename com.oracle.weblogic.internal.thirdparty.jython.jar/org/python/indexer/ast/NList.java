package org.python.indexer.ast;

import java.util.Iterator;
import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NListType;
import org.python.indexer.types.NType;

public class NList extends NSequence {
   static final long serialVersionUID = 6623743056841822992L;

   public NList(List elts) {
      this(elts, 0, 1);
   }

   public NList(List elts, int start, int end) {
      super(elts, start, end);
   }

   public NType resolve(Scope s) throws Exception {
      if (this.elts.size() == 0) {
         return this.setType(new NListType());
      } else {
         NListType listType = null;
         Iterator var3 = this.elts.iterator();

         while(var3.hasNext()) {
            NNode elt = (NNode)var3.next();
            if (listType == null) {
               listType = new NListType(resolveExpr(elt, s));
            } else {
               listType.add(resolveExpr(elt, s));
            }
         }

         if (listType != null) {
            this.setType(listType);
         }

         return this.getType();
      }
   }

   public String toString() {
      return "<List:" + this.start() + ":" + this.elts + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNodeList(this.elts, v);
      }

   }
}
