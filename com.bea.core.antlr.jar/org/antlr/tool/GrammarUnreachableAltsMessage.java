package org.antlr.tool;

import java.util.List;
import org.antlr.analysis.DecisionProbe;
import org.antlr.analysis.NFAState;
import org.stringtemplate.v4.ST;

public class GrammarUnreachableAltsMessage extends Message {
   public DecisionProbe probe;
   public List alts;

   public GrammarUnreachableAltsMessage(DecisionProbe probe, List alts) {
      super(201);
      this.probe = probe;
      this.alts = alts;
      if (probe.dfa.isTokensRuleDecision()) {
         this.setMessageID(208);
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
      if (this.probe.dfa.isTokensRuleDecision()) {
         for(int i = 0; i < this.alts.size(); ++i) {
            Integer altI = (Integer)this.alts.get(i);
            String tokenName = this.probe.getTokenNameForTokensRuleAlt(altI);
            NFAState ruleStart = this.probe.dfa.nfa.grammar.getRuleStartState(tokenName);
            this.line = ruleStart.associatedASTNode.getLine();
            this.column = ruleStart.associatedASTNode.getCharPositionInLine();
            st.add("tokens", tokenName);
         }
      } else {
         st.add("alts", this.alts);
      }

      return super.toString(st);
   }
}
