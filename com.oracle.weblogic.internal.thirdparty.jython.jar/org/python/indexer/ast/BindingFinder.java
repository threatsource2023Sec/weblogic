package org.python.indexer.ast;

import org.python.indexer.Indexer;
import org.python.indexer.Scope;

class BindingFinder extends GenericNodeVisitor {
   private Scope scope;

   public BindingFinder(Scope scope) {
      this.scope = scope;
   }

   public boolean dispatch(NNode n) {
      if (n.bindsName()) {
         try {
            n.bindNames(this.scope);
         } catch (Exception var3) {
            Indexer.idx.handleException("error binding names for " + n, var3);
         }
      }

      return !n.isFunctionDef() && !n.isClassDef() ? super.dispatch(n) : false;
   }
}
