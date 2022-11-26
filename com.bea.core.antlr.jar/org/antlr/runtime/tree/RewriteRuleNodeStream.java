package org.antlr.runtime.tree;

import java.util.List;

public class RewriteRuleNodeStream extends RewriteRuleElementStream {
   public RewriteRuleNodeStream(TreeAdaptor adaptor, String elementDescription) {
      super(adaptor, elementDescription);
   }

   public RewriteRuleNodeStream(TreeAdaptor adaptor, String elementDescription, Object oneElement) {
      super(adaptor, elementDescription, oneElement);
   }

   public RewriteRuleNodeStream(TreeAdaptor adaptor, String elementDescription, List elements) {
      super(adaptor, elementDescription, elements);
   }

   public Object nextNode() {
      return this._next();
   }

   protected Object toTree(Object el) {
      return this.adaptor.dupNode(el);
   }

   protected Object dup(Object el) {
      throw new UnsupportedOperationException("dup can't be called for a node stream.");
   }
}
