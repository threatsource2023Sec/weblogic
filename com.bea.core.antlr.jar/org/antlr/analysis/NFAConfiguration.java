package org.antlr.analysis;

import org.antlr.misc.Utils;

public class NFAConfiguration {
   public int state;
   public int alt;
   public NFAContext context;
   public SemanticContext semanticContext;
   protected boolean resolved;
   protected boolean resolveWithPredicate;
   protected int numberEpsilonTransitionsEmanatingFromState;
   protected boolean singleAtomTransitionEmanating;

   public NFAConfiguration(int state, int alt, NFAContext context, SemanticContext semanticContext) {
      this.semanticContext = SemanticContext.EMPTY_SEMANTIC_CONTEXT;
      this.state = state;
      this.alt = alt;
      this.context = context;
      this.semanticContext = semanticContext;
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else {
         NFAConfiguration other = (NFAConfiguration)o;
         return this.state == other.state && this.alt == other.alt && this.context.equals(other.context) && this.semanticContext.equals(other.semanticContext);
      }
   }

   public int hashCode() {
      int h = this.state + this.alt + this.context.hashCode();
      return h;
   }

   public String toString() {
      return this.toString(true);
   }

   public String toString(boolean showAlt) {
      StringBuilder buf = new StringBuilder();
      buf.append(this.state);
      if (showAlt) {
         buf.append("|");
         buf.append(this.alt);
      }

      if (this.context.parent != null) {
         buf.append("|");
         buf.append(this.context);
      }

      if (this.semanticContext != null && this.semanticContext != SemanticContext.EMPTY_SEMANTIC_CONTEXT) {
         buf.append("|");
         String escQuote = Utils.replace(this.semanticContext.toString(), "\"", "\\\"");
         buf.append(escQuote);
      }

      if (this.resolved) {
         buf.append("|resolved");
      }

      if (this.resolveWithPredicate) {
         buf.append("|resolveWithPredicate");
      }

      return buf.toString();
   }
}
