package org.python.indexer.ast;

public class NContinue extends NNode {
   static final long serialVersionUID = 1646681898280823606L;

   public NContinue() {
   }

   public NContinue(int start, int end) {
      super(start, end);
   }

   public String toString() {
      return "<Continue>";
   }

   public void visit(NNodeVisitor v) {
      v.visit(this);
   }
}
