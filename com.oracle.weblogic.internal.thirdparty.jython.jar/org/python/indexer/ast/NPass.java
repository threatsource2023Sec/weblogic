package org.python.indexer.ast;

public class NPass extends NNode {
   static final long serialVersionUID = 3668786487029793620L;

   public NPass() {
   }

   public NPass(int start, int end) {
      super(start, end);
   }

   public String toString() {
      return "<Pass>";
   }

   public void visit(NNodeVisitor v) {
      v.visit(this);
   }
}
