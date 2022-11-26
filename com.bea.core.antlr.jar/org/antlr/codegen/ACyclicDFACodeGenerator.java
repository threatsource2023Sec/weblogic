package org.antlr.codegen;

import java.util.ArrayList;
import java.util.List;
import org.antlr.analysis.DFA;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.SemanticContext;
import org.antlr.analysis.Transition;
import org.antlr.misc.Utils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class ACyclicDFACodeGenerator {
   protected CodeGenerator parentGenerator;

   public ACyclicDFACodeGenerator(CodeGenerator parent) {
      this.parentGenerator = parent;
   }

   public ST genFixedLookaheadDecision(STGroup templates, DFA dfa) {
      return this.walkFixedDFAGeneratingStateMachine(templates, dfa, dfa.startState, 1);
   }

   protected ST walkFixedDFAGeneratingStateMachine(STGroup templates, DFA dfa, DFAState s, int k) {
      if (s.isAcceptState()) {
         ST dfaST = templates.getInstanceOf("dfaAcceptState");
         dfaST.add("alt", Utils.integer(s.getUniquelyPredictedAlt()));
         return dfaST;
      } else {
         String dfaStateName = "dfaState";
         String dfaLoopbackStateName = "dfaLoopbackState";
         String dfaOptionalBlockStateName = "dfaOptionalBlockState";
         String dfaEdgeName = "dfaEdge";
         if (this.parentGenerator.canGenerateSwitch(s)) {
            dfaStateName = "dfaStateSwitch";
            dfaLoopbackStateName = "dfaLoopbackStateSwitch";
            dfaOptionalBlockStateName = "dfaOptionalBlockStateSwitch";
            dfaEdgeName = "dfaEdgeSwitch";
         }

         ST dfaST = templates.getInstanceOf(dfaStateName);
         if (dfa.getNFADecisionStartState().decisionStateType == 1) {
            dfaST = templates.getInstanceOf(dfaLoopbackStateName);
         } else if (dfa.getNFADecisionStartState().decisionStateType == 3) {
            dfaST = templates.getInstanceOf(dfaOptionalBlockStateName);
         }

         dfaST.add("k", Utils.integer(k));
         dfaST.add("stateNumber", Utils.integer(s.stateNumber));
         dfaST.add("semPredState", s.isResolvedWithPredicates());
         int EOTPredicts = -1;
         DFAState EOTTarget = null;

         int i;
         Transition edge;
         ST edgeST;
         ST targetST;
         for(i = 0; i < s.getNumberOfTransitions(); ++i) {
            edge = s.transition(i);
            if (edge.label.getAtom() == -2) {
               EOTTarget = (DFAState)edge.target;
               EOTPredicts = EOTTarget.getUniquelyPredictedAlt();
            } else {
               edgeST = templates.getInstanceOf(dfaEdgeName);
               if (edgeST.impl.formalArguments.get("labels") != null) {
                  List labels = edge.label.getSet().toList();
                  List targetLabels = new ArrayList(labels.size());

                  for(int j = 0; j < labels.size(); ++j) {
                     Integer vI = (Integer)labels.get(j);
                     String label = this.parentGenerator.getTokenTypeAsTargetLabel(vI);
                     targetLabels.add(label);
                  }

                  edgeST.add("labels", targetLabels);
               } else {
                  edgeST.add("labelExpr", this.parentGenerator.genLabelExpr(templates, edge, k));
               }

               if (!edge.label.isSemanticPredicate()) {
                  DFAState target = (DFAState)edge.target;
                  SemanticContext preds = target.getGatedPredicatesInNFAConfigurations();
                  if (preds != null) {
                     ST predST = preds.genExpr(this.parentGenerator, this.parentGenerator.getTemplates(), dfa);
                     edgeST.add("predicates", predST);
                  }
               }

               targetST = this.walkFixedDFAGeneratingStateMachine(templates, dfa, (DFAState)edge.target, k + 1);
               edgeST.add("targetState", targetST);
               dfaST.add("edges", edgeST);
            }
         }

         if (EOTPredicts != -1) {
            dfaST.add("eotPredictsAlt", Utils.integer(EOTPredicts));
         } else if (EOTTarget != null && EOTTarget.getNumberOfTransitions() > 0) {
            for(i = 0; i < EOTTarget.getNumberOfTransitions(); ++i) {
               edge = EOTTarget.transition(i);
               edgeST = templates.getInstanceOf(dfaEdgeName);
               edgeST.add("labelExpr", this.parentGenerator.genSemanticPredicateExpr(templates, edge));
               targetST = this.walkFixedDFAGeneratingStateMachine(templates, dfa, (DFAState)edge.target, k + 1);
               edgeST.add("targetState", targetST);
               dfaST.add("edges", edgeST);
            }
         }

         return dfaST;
      }
   }
}
