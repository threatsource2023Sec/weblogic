package org.antlr.tool;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.DecisionProbe;
import org.antlr.analysis.NFAState;
import org.stringtemplate.v4.ST;

public class GrammarNonDeterminismMessage extends Message {
   public DecisionProbe probe;
   public DFAState problemState;

   public GrammarNonDeterminismMessage(DecisionProbe probe, DFAState problemState) {
      super(200);
      this.probe = probe;
      this.problemState = problemState;
      if (probe.dfa.isTokensRuleDecision()) {
         this.setMessageID(209);
      }

   }

   public String toString() {
      GrammarAST decisionASTNode = this.probe.dfa.getDecisionASTNode();
      this.line = decisionASTNode.getLine();
      this.column = decisionASTNode.getCharPositionInLine();
      String fileName = this.probe.dfa.nfa.grammar.getFileName();
      if (fileName != null) {
         this.file = fileName;
      }

      ST st = this.getMessageTemplate();
      List labels = this.probe.getSampleNonDeterministicInputSequence(this.problemState);
      String input = this.probe.getInputSequenceDisplay(labels);
      st.add("input", input);
      if (this.probe.dfa.isTokensRuleDecision()) {
         Set disabledAlts = this.probe.getDisabledAlternatives(this.problemState);
         Iterator i$ = disabledAlts.iterator();

         while(i$.hasNext()) {
            Integer altI = (Integer)i$.next();
            String tokenName = this.probe.getTokenNameForTokensRuleAlt(altI);
            NFAState ruleStart = this.probe.dfa.nfa.grammar.getRuleStartState(tokenName);
            this.line = ruleStart.associatedASTNode.getLine();
            this.column = ruleStart.associatedASTNode.getCharPositionInLine();
            st.add("disabled", tokenName);
         }
      } else {
         st.add("disabled", this.probe.getDisabledAlternatives(this.problemState));
      }

      List nondetAlts = this.probe.getNonDeterministicAltsForState(this.problemState);
      NFAState nfaStart = this.probe.dfa.getNFADecisionStartState();
      int firstAlt = 0;
      if (nondetAlts != null) {
         Iterator i$ = nondetAlts.iterator();

         while(i$.hasNext()) {
            Integer displayAltI = (Integer)i$.next();
            if (DecisionProbe.verbose) {
               int tracePathAlt = nfaStart.translateDisplayAltToWalkAlt(displayAltI);
               if (firstAlt == 0) {
                  firstAlt = tracePathAlt;
               }

               List path = this.probe.getNFAPathStatesForAlt(firstAlt, tracePathAlt, labels);
               st.addAggr("paths.{alt, states}", displayAltI, path);
            } else if (this.probe.dfa.isTokensRuleDecision()) {
               String tokenName = this.probe.getTokenNameForTokensRuleAlt(displayAltI);
               st.add("conflictingTokens", tokenName);
            } else {
               st.add("conflictingAlts", displayAltI);
            }
         }
      }

      st.add("hasPredicateBlockedByAction", this.problemState.dfa.hasPredicateBlockedByAction);
      return super.toString(st);
   }
}
