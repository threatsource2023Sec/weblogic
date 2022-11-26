package org.antlr.tool;

import org.antlr.analysis.DecisionProbe;
import org.stringtemplate.v4.ST;

public class GrammarAnalysisAbortedMessage extends Message {
   public DecisionProbe probe;

   public GrammarAnalysisAbortedMessage(DecisionProbe probe) {
      super(205);
      this.probe = probe;
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
      st.add("enclosingRule", this.probe.dfa.getNFADecisionStartState().enclosingRule.name);
      return super.toString(st);
   }
}
