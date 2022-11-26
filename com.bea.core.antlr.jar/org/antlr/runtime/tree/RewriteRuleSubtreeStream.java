package org.antlr.runtime.tree;

import java.util.List;

public class RewriteRuleSubtreeStream extends RewriteRuleElementStream {
   public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription) {
      super(adaptor, elementDescription);
   }

   public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription, Object oneElement) {
      super(adaptor, elementDescription, oneElement);
   }

   public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription, List elements) {
      super(adaptor, elementDescription, elements);
   }

   public Object nextNode() {
      int n = this.size();
      Object tree;
      if (!this.dirty && (this.cursor < n || n != 1)) {
         for(tree = this._next(); this.adaptor.isNil(tree) && this.adaptor.getChildCount(tree) == 1; tree = this.adaptor.getChild(tree, 0)) {
         }

         Object el = this.adaptor.dupNode(tree);
         return el;
      } else {
         tree = this._next();
         return this.adaptor.dupNode(tree);
      }
   }

   protected Object dup(Object el) {
      return this.adaptor.dupTree(el);
   }
}
