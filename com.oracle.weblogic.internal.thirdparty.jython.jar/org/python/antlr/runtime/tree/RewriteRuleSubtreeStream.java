package org.python.antlr.runtime.tree;

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
      Object el;
      if (!this.dirty && (this.cursor < n || n != 1)) {
         el = this._next();
         return el;
      } else {
         el = this._next();
         return this.adaptor.dupNode(el);
      }
   }

   protected Object dup(Object el) {
      return this.adaptor.dupTree(el);
   }
}
