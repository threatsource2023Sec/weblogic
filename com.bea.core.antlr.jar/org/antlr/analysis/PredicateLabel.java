package org.antlr.analysis;

import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;

public class PredicateLabel extends Label {
   protected SemanticContext semanticContext;

   public PredicateLabel(GrammarAST predicateASTNode) {
      super(-4);
      this.semanticContext = new SemanticContext.Predicate(predicateASTNode);
   }

   public PredicateLabel(SemanticContext semCtx) {
      super(-4);
      this.semanticContext = semCtx;
   }

   public int hashCode() {
      return this.semanticContext.hashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (this == o) {
         return true;
      } else {
         return !(o instanceof PredicateLabel) ? false : this.semanticContext.equals(((PredicateLabel)o).semanticContext);
      }
   }

   public boolean isSemanticPredicate() {
      return true;
   }

   public SemanticContext getSemanticContext() {
      return this.semanticContext;
   }

   public String toString() {
      return "{" + this.semanticContext + "}?";
   }

   public String toString(Grammar g) {
      return this.toString();
   }
}
