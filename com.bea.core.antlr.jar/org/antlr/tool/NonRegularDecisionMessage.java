package org.antlr.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.antlr.analysis.DecisionProbe;
import org.stringtemplate.v4.ST;

public class NonRegularDecisionMessage extends Message {
   public DecisionProbe probe;
   public Set altsWithRecursion;

   public NonRegularDecisionMessage(DecisionProbe probe, Set altsWithRecursion) {
      super(211);
      this.probe = probe;
      this.altsWithRecursion = altsWithRecursion;
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
      String ruleName = this.probe.dfa.getNFADecisionStartState().enclosingRule.name;
      st.add("ruleName", ruleName);
      List sortedAlts = new ArrayList();
      sortedAlts.addAll(this.altsWithRecursion);
      Collections.sort(sortedAlts);
      st.add("alts", sortedAlts);
      return super.toString(st);
   }
}
