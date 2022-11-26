package org.python.indexer.ast;

public class NEllipsis extends NNode {
   static final long serialVersionUID = 4148534089952252511L;

   public NEllipsis() {
   }

   public NEllipsis(int start, int end) {
      super(start, end);
   }

   public String toString() {
      return "<Ellipsis>";
   }

   public void visit(NNodeVisitor v) {
      v.visit(this);
   }
}
