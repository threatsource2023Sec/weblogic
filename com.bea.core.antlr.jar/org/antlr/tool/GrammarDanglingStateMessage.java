package org.antlr.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.DecisionProbe;
import org.stringtemplate.v4.ST;

public class GrammarDanglingStateMessage extends Message {
   public DecisionProbe probe;
   public DFAState problemState;

   public GrammarDanglingStateMessage(DecisionProbe probe, DFAState problemState) {
      super(202);
      this.probe = probe;
      this.problemState = problemState;
   }

   public String toString() {
      GrammarAST decisionASTNode = this.probe.dfa.getDecisionASTNode();
      this.line = decisionASTNode.getLine();
      this.column = decisionASTNode.getCharPositionInLine();
      String fileName = this.probe.dfa.nfa.grammar.getFileName();
      if (fileName != null) {
         this.file = fileName;
      }

      List labels = this.probe.getSampleNonDeterministicInputSequence(this.problemState);
      String input = this.probe.getInputSequenceDisplay(labels);
      ST st = this.getMessageTemplate();
      List alts = new ArrayList();
      alts.addAll(this.problemState.getAltSet());
      Collections.sort(alts);
      st.add("danglingAlts", alts);
      st.add("input", input);
      return super.toString(st);
   }
}
