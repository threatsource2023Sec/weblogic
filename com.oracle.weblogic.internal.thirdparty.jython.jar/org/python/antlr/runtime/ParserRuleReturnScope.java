package org.python.antlr.runtime;

public class ParserRuleReturnScope extends RuleReturnScope {
   public Token start;
   public Token stop;

   public Object getStart() {
      return this.start;
   }

   public Object getStop() {
      return this.stop;
   }
}
