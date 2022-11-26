package org.antlr.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.analysis.DFA;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.RuleClosureTransition;
import org.antlr.analysis.SemanticContext;
import org.antlr.analysis.State;
import org.antlr.analysis.Transition;
import org.antlr.misc.Utils;

public class FASerializer {
   protected Set markedStates;
   protected int stateCounter = 0;
   protected Map stateNumberTranslator;
   protected Grammar grammar;

   public FASerializer(Grammar grammar) {
      this.grammar = grammar;
   }

   public String serialize(State s) {
      return s == null ? "<no automaton>" : this.serialize(s, true);
   }

   public String serialize(State s, boolean renumber) {
      this.markedStates = new HashSet();
      this.stateCounter = 0;
      if (renumber) {
         this.stateNumberTranslator = new HashMap();
         this.walkFANormalizingStateNumbers(s);
      }

      List lines = new ArrayList();
      if (s.getNumberOfTransitions() > 0) {
         this.walkSerializingFA(lines, s);
      } else {
         String s0 = this.getStateString(0, s);
         lines.add(s0 + "\n");
      }

      StringBuilder buf = new StringBuilder(0);
      Collections.sort(lines);

      for(int i = 0; i < lines.size(); ++i) {
         String line = (String)lines.get(i);
         buf.append(line);
      }

      return buf.toString();
   }

   protected void walkFANormalizingStateNumbers(State s) {
      if (s == null) {
         ErrorManager.internalError("null state s");
      } else if (this.stateNumberTranslator.get(s) == null) {
         this.stateNumberTranslator.put(s, Utils.integer(this.stateCounter));
         ++this.stateCounter;

         for(int i = 0; i < s.getNumberOfTransitions(); ++i) {
            Transition edge = s.transition(i);
            this.walkFANormalizingStateNumbers(edge.target);
            if (edge instanceof RuleClosureTransition) {
               this.walkFANormalizingStateNumbers(((RuleClosureTransition)edge).followState);
            }
         }

      }
   }

   protected void walkSerializingFA(List lines, State s) {
      if (!this.markedStates.contains(s)) {
         this.markedStates.add(s);
         int normalizedStateNumber = s.stateNumber;
         if (this.stateNumberTranslator != null) {
            Integer normalizedStateNumberI = (Integer)this.stateNumberTranslator.get(s);
            normalizedStateNumber = normalizedStateNumberI;
         }

         String stateStr = this.getStateString(normalizedStateNumber, s);

         for(int i = 0; i < s.getNumberOfTransitions(); ++i) {
            Transition edge = s.transition(i);
            StringBuilder buf = new StringBuilder();
            buf.append(stateStr);
            if (edge.isAction()) {
               buf.append("-{}->");
            } else if (edge.isEpsilon()) {
               buf.append("->");
            } else if (edge.isSemanticPredicate()) {
               buf.append("-{").append(edge.label.getSemanticContext()).append("}?->");
            } else {
               String predsStr = "";
               if (edge.target instanceof DFAState) {
                  SemanticContext preds = ((DFAState)edge.target).getGatedPredicatesInNFAConfigurations();
                  if (preds != null) {
                     predsStr = "&&{" + preds.genExpr(this.grammar.generator, this.grammar.generator.getTemplates(), (DFA)null).render() + "}?";
                  }
               }

               buf.append("-").append(edge.label.toString(this.grammar)).append(predsStr).append("->");
            }

            int normalizedTargetStateNumber = edge.target.stateNumber;
            if (this.stateNumberTranslator != null) {
               Integer normalizedTargetStateNumberI = (Integer)this.stateNumberTranslator.get(edge.target);
               normalizedTargetStateNumber = normalizedTargetStateNumberI;
            }

            buf.append(this.getStateString(normalizedTargetStateNumber, edge.target));
            buf.append("\n");
            lines.add(buf.toString());
            this.walkSerializingFA(lines, edge.target);
            if (edge instanceof RuleClosureTransition) {
               this.walkSerializingFA(lines, ((RuleClosureTransition)edge).followState);
            }
         }

      }
   }

   private String getStateString(int n, State s) {
      String stateStr = ".s" + n;
      if (s.isAcceptState()) {
         if (s instanceof DFAState) {
            stateStr = ":s" + n + "=>" + ((DFAState)s).getUniquelyPredictedAlt();
         } else {
            stateStr = ":s" + n;
         }
      }

      return stateStr;
   }
}
