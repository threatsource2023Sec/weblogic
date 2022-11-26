package org.python.indexer.ast;

public class NBreak extends NNode {
   static final long serialVersionUID = 2114759731430768793L;

   public NBreak() {
   }

   public NBreak(int start, int end) {
      super(start, end);
   }

   public String toString() {
      return "<Break>";
   }

   public void visit(NNodeVisitor v) {
      v.visit(this);
   }
}
