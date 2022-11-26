package org.python.indexer.ast;

public class NPlaceHolder extends NNode {
   static final long serialVersionUID = -8732894605739403419L;

   public NPlaceHolder() {
   }

   public NPlaceHolder(int start, int end) {
      super(start, end);
   }

   public String toString() {
      return "<PlaceHolder:" + this.start() + ":" + this.end() + ">";
   }

   public void visit(NNodeVisitor v) {
      v.visit(this);
   }
}
