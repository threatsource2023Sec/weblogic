package org.antlr.tool;

import java.util.Collection;
import java.util.List;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.DecisionProbe;
import org.stringtemplate.v4.ST;

public class RecursionOverflowMessage extends Message {
   public DecisionProbe probe;
   public DFAState sampleBadState;
   public int alt;
   public Collection targetRules;
   public Collection callSiteStates;

   public RecursionOverflowMessage(DecisionProbe probe, DFAState sampleBadState, int alt, Collection targetRules, Collection callSiteStates) {
      super(206);
      this.probe = probe;
      this.sampleBadState = sampleBadState;
      this.alt = alt;
      this.targetRules = targetRules;
      this.callSiteStates = callSiteStates;
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
      st.add("targetRules", this.targetRules);
      st.add("alt", this.alt);
      st.add("callSiteStates", this.callSiteStates);
      List labels = this.probe.getSampleNonDeterministicInputSequence(this.sampleBadState);
      String input = this.probe.getInputSequenceDisplay(labels);
      st.add("input", input);
      return super.toString(st);
   }
}
