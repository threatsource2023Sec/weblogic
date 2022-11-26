package org.antlr.tool;

import java.util.Iterator;

public class GrammarReport2 {
   public static final String newline = System.getProperty("line.separator");
   public Grammar root;

   public GrammarReport2(Grammar rootGrammar) {
      this.root = rootGrammar;
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      this.stats(this.root, buf);
      CompositeGrammar composite = this.root.composite;
      Iterator i$ = composite.getDelegates(this.root).iterator();

      while(i$.hasNext()) {
         Grammar g = (Grammar)i$.next();
         this.stats(g, buf);
      }

      return buf.toString();
   }

   void stats(Grammar g, StringBuilder buf) {
      int numDec = g.getNumberOfDecisions();

      for(int decision = 1; decision <= numDec; ++decision) {
         Grammar.Decision d = g.getDecision(decision);
         if (d.dfa != null) {
            int k = d.dfa.getMaxLookaheadDepth();
            Rule enclosingRule = d.dfa.decisionNFAStartState.enclosingRule;
            if (!enclosingRule.isSynPred) {
               buf.append(g.name).append(".").append(enclosingRule.name).append(":");
               GrammarAST decisionAST = d.dfa.decisionNFAStartState.associatedASTNode;
               buf.append(decisionAST.getLine());
               buf.append(":");
               buf.append(decisionAST.getCharPositionInLine());
               buf.append(" decision ").append(decision).append(":");
               if (d.dfa.isCyclic()) {
                  buf.append(" cyclic");
               }

               if (k != Integer.MAX_VALUE) {
                  buf.append(" k=").append(k);
               }

               if (d.dfa.hasSynPred()) {
                  buf.append(" backtracks");
               }

               if (d.dfa.hasSemPred()) {
                  buf.append(" sempred");
               }

               this.nl(buf);
            }
         }
      }

   }

   void nl(StringBuilder buf) {
      buf.append(newline);
   }
}
