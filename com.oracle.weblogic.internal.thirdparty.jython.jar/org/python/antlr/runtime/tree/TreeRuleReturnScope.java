package org.python.antlr.runtime.tree;

import org.python.antlr.runtime.RuleReturnScope;

public class TreeRuleReturnScope extends RuleReturnScope {
   public Object start;

   public Object getStart() {
      return this.start;
   }
}
