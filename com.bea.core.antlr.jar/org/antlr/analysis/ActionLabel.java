package org.antlr.analysis;

import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;

public class ActionLabel extends Label {
   public GrammarAST actionAST;

   public ActionLabel(GrammarAST actionAST) {
      super(-6);
      this.actionAST = actionAST;
   }

   public boolean isEpsilon() {
      return true;
   }

   public boolean isAction() {
      return true;
   }

   public String toString() {
      return "{" + this.actionAST + "}";
   }

   public String toString(Grammar g) {
      return this.toString();
   }
}
