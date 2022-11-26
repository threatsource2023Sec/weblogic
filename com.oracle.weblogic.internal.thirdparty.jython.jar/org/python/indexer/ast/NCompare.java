package org.python.indexer.ast;

import java.util.List;
import org.python.indexer.Indexer;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NCompare extends NNode {
   static final long serialVersionUID = 1013460919393985064L;
   public NNode left;
   public List ops;
   public List comparators;

   public NCompare(NNode left, List ops, List comparators) {
      this(left, ops, comparators, 0, 1);
   }

   public NCompare(NNode left, List ops, List comparators, int start, int end) {
      super(start, end);
      this.left = left;
      this.ops = ops;
      this.comparators = comparators;
      this.addChildren(new NNode[]{left});
      this.addChildren(ops);
      this.addChildren(comparators);
   }

   public NType resolve(Scope s) throws Exception {
      this.setType(Indexer.idx.builtins.BaseNum);
      resolveExpr(this.left, s);
      this.resolveList(this.comparators, s);
      return this.getType();
   }

   public String toString() {
      return "<Compare:" + this.left + ":" + this.ops + ":" + this.comparators + ">";
   }

   public void visit(NNodeVisitor v) {
      if (v.visit(this)) {
         this.visitNode(this.left, v);
         this.visitNodeList(this.ops, v);
         this.visitNodeList(this.comparators, v);
      }

   }
}
