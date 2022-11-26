package org.antlr.analysis;

import org.antlr.tool.Rule;

public class RuleClosureTransition extends Transition {
   public Rule rule;
   public NFAState followState;

   public RuleClosureTransition(Rule rule, NFAState ruleStart, NFAState followState) {
      super(-5, ruleStart);
      this.rule = rule;
      this.followState = followState;
   }
}
