package org.antlr.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.DecisionProbe;
import org.stringtemplate.v4.ST;

public class GrammarInsufficientPredicatesMessage extends Message {
   public DecisionProbe probe;
   public Map altToLocations;
   public DFAState problemState;

   public GrammarInsufficientPredicatesMessage(DecisionProbe probe, DFAState problemState, Map altToLocations) {
      super(203);
      this.probe = probe;
      this.problemState = problemState;
      this.altToLocations = altToLocations;
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
      Map altToLocationsWithStringKey = new LinkedHashMap();
      List alts = new ArrayList();
      alts.addAll(this.altToLocations.keySet());
      Collections.sort(alts);
      Iterator i$ = alts.iterator();

      while(i$.hasNext()) {
         Integer altI = (Integer)i$.next();
         altToLocationsWithStringKey.put(altI.toString(), this.altToLocations.get(altI));
      }

      st.add("altToLocations", altToLocationsWithStringKey);
      List sampleInputLabels = this.problemState.dfa.probe.getSampleNonDeterministicInputSequence(this.problemState);
      String input = this.problemState.dfa.probe.getInputSequenceDisplay(sampleInputLabels);
      st.add("upon", input);
      st.add("hasPredicateBlockedByAction", this.problemState.dfa.hasPredicateBlockedByAction);
      return super.toString(st);
   }
}
