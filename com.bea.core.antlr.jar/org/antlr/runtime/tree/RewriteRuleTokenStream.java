package org.antlr.runtime.tree;

import java.util.List;
import org.antlr.runtime.Token;

public class RewriteRuleTokenStream extends RewriteRuleElementStream {
   public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription) {
      super(adaptor, elementDescription);
   }

   public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription, Object oneElement) {
      super(adaptor, elementDescription, oneElement);
   }

   public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription, List elements) {
      super(adaptor, elementDescription, elements);
   }

   public Object nextNode() {
      Token t = (Token)this._next();
      return this.adaptor.create(t);
   }

   public Token nextToken() {
      return (Token)this._next();
   }

   protected Object toTree(Object el) {
      return el;
   }

   protected Object dup(Object el) {
      throw new UnsupportedOperationException("dup can't be called for a token stream.");
   }
}
