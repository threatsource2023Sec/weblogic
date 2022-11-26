package org.antlr.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.antlr.misc.IntSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.antlr.tool.Grammar;

public class MachineProbe {
   DFA dfa;

   public MachineProbe(DFA dfa) {
      this.dfa = dfa;
   }

   List getAnyDFAPathToTarget(DFAState targetState) {
      Set visited = new HashSet();
      return this.getAnyDFAPathToTarget(this.dfa.startState, targetState, visited);
   }

   public List getAnyDFAPathToTarget(DFAState startState, DFAState targetState, Set visited) {
      List dfaStates = new ArrayList();
      visited.add(startState);
      if (startState.equals(targetState)) {
         dfaStates.add(targetState);
         return dfaStates;
      } else {
         for(int i = 0; i < startState.getNumberOfTransitions(); ++i) {
            Transition e = startState.getTransition(i);
            if (!visited.contains(e.target)) {
               List path = this.getAnyDFAPathToTarget((DFAState)e.target, targetState, visited);
               if (path != null) {
                  dfaStates.add(startState);
                  dfaStates.addAll(path);
                  return dfaStates;
               }
            }
         }

         return null;
      }
   }

   public List getEdgeLabels(DFAState targetState) {
      List dfaStates = this.getAnyDFAPathToTarget(targetState);
      List labels = new ArrayList();

      for(int i = 0; i < dfaStates.size() - 1; ++i) {
         DFAState d = (DFAState)dfaStates.get(i);
         DFAState nextState = (DFAState)dfaStates.get(i + 1);

         for(int j = 0; j < d.getNumberOfTransitions(); ++j) {
            Transition e = d.getTransition(j);
            if (e.target.stateNumber == nextState.stateNumber) {
               labels.add(e.label.getSet());
            }
         }
      }

      return labels;
   }

   public String getInputSequenceDisplay(Grammar g, List labels) {
      List tokens = new ArrayList();
      Iterator i$ = labels.iterator();

      while(i$.hasNext()) {
         IntSet label = (IntSet)i$.next();
         tokens.add(label.toString(g));
      }

      return tokens.toString();
   }

   public List getGrammarLocationsForInputSequence(List nfaStates, List labels) {
      List tokens = new ArrayList();

      label37:
      for(int i = 0; i < nfaStates.size() - 1; ++i) {
         Set cur = (Set)nfaStates.get(i);
         Set next = (Set)nfaStates.get(i + 1);
         IntSet label = (IntSet)labels.get(i);
         Iterator i$ = cur.iterator();

         while(i$.hasNext()) {
            NFAState p = (NFAState)i$.next();

            for(int j = 0; j < p.getNumberOfTransitions(); ++j) {
               Transition t = p.transition(j);
               if (!t.isEpsilon() && !t.label.getSet().and(label).isNil() && next.contains(t.target) && p.associatedASTNode != null) {
                  Token oldtoken = p.associatedASTNode.token;
                  CommonToken token = new CommonToken(oldtoken.getType(), oldtoken.getText());
                  token.setLine(oldtoken.getLine());
                  token.setCharPositionInLine(oldtoken.getCharPositionInLine());
                  tokens.add(token);
                  continue label37;
               }
            }
         }
      }

      return tokens;
   }
}
