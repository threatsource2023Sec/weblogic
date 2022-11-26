package org.python.indexer.ast;

import java.util.List;
import org.python.indexer.Scope;
import org.python.indexer.types.NType;

public class NBody extends NBlock {
   static final long serialVersionUID = 1518962862898927516L;

   public NBody(NBlock block) {
      this(block == null ? null : block.seq);
   }

   public NBody(List seq) {
      super(seq);
   }

   public NBody(List seq, int start, int end) {
      super(seq, start, end);
   }

   public NType resolve(Scope scope) throws Exception {
      try {
         scope.setNameBindingPhase(true);
         this.visit(new GlobalFinder(scope));
         this.visit(new BindingFinder(scope));
      } finally {
         scope.setNameBindingPhase(false);
      }

      return super.resolve(scope);
   }

   private class GlobalFinder extends DefaultNodeVisitor {
      private Scope scope;

      public GlobalFinder(Scope scope) {
         this.scope = scope;
      }

      public boolean visit(NGlobal n) {
         NNode.resolveExpr(n, this.scope);
         return false;
      }

      public boolean visit(NFunctionDef n) {
         return false;
      }

      public boolean visit(NLambda n) {
         return false;
      }

      public boolean visit(NClassDef n) {
         return false;
      }
   }
}
