package org.antlr.analysis;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.antlr.misc.IntervalSet;
import org.antlr.misc.MultiMap;

public class LL1DFA extends DFA {
   public LL1DFA(int decisionNumber, NFAState decisionStartState, LookaheadSet[] altLook) {
      DFAState s0 = this.newState();
      this.startState = s0;
      this.nfa = decisionStartState.nfa;
      this.nAlts = this.nfa.grammar.getNumberOfAltsForDecisionNFA(decisionStartState);
      this.decisionNumber = decisionNumber;
      this.decisionNFAStartState = decisionStartState;
      this.initAltRelatedInfo();
      this.unreachableAlts = null;

      for(int alt = 1; alt < altLook.length; ++alt) {
         DFAState acceptAltState = this.newState();
         acceptAltState.acceptState = true;
         this.setAcceptState(alt, acceptAltState);
         acceptAltState.k = 1;
         acceptAltState.cachedUniquelyPredicatedAlt = alt;
         Label e = this.getLabelForSet(altLook[alt].tokenTypeSet);
         s0.addTransition(acceptAltState, e);
      }

   }

   public LL1DFA(int decisionNumber, NFAState decisionStartState, MultiMap edgeMap) {
      DFAState s0 = this.newState();
      this.startState = s0;
      this.nfa = decisionStartState.nfa;
      this.nAlts = this.nfa.grammar.getNumberOfAltsForDecisionNFA(decisionStartState);
      this.decisionNumber = decisionNumber;
      this.decisionNFAStartState = decisionStartState;
      this.initAltRelatedInfo();
      this.unreachableAlts = null;
      Iterator i$ = edgeMap.entrySet().iterator();

      while(true) {
         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            IntervalSet edge = (IntervalSet)entry.getKey();
            List alts = (List)entry.getValue();
            Collections.sort(alts);
            DFAState s = this.newState();
            s.k = 1;
            Label e = this.getLabelForSet(edge);
            s0.addTransition(s, e);
            int i;
            if (alts.size() == 1) {
               s.acceptState = true;
               i = (Integer)alts.get(0);
               this.setAcceptState(i, s);
               s.cachedUniquelyPredicatedAlt = i;
            } else {
               s.resolvedWithPredicates = true;

               for(i = 0; i < alts.size(); ++i) {
                  int alt = (Integer)alts.get(i);
                  s.cachedUniquelyPredicatedAlt = -1;
                  DFAState predDFATarget = this.getAcceptState(alt);
                  if (predDFATarget == null) {
                     predDFATarget = this.newState();
                     predDFATarget.acceptState = true;
                     predDFATarget.cachedUniquelyPredicatedAlt = alt;
                     this.setAcceptState(alt, predDFATarget);
                  }

                  SemanticContext.Predicate synpred = this.getSynPredForAlt(decisionStartState, alt);
                  if (synpred == null) {
                     synpred = new SemanticContext.TruePredicate();
                  }

                  s.addTransition(predDFATarget, new PredicateLabel((SemanticContext)synpred));
               }
            }
         }

         return;
      }
   }

   protected Label getLabelForSet(IntervalSet edgeSet) {
      int atom = edgeSet.getSingleElement();
      Label e;
      if (atom != -7) {
         e = new Label(atom);
      } else {
         e = new Label(edgeSet);
      }

      return e;
   }

   protected SemanticContext.Predicate getSynPredForAlt(NFAState decisionStartState, int alt) {
      int walkAlt = decisionStartState.translateDisplayAltToWalkAlt(alt);
      NFAState altLeftEdge = this.nfa.grammar.getNFAStateForAltOfDecision(decisionStartState, walkAlt);
      NFAState altStartState = (NFAState)altLeftEdge.transition[0].target;
      if (altStartState.transition[0].isSemanticPredicate()) {
         SemanticContext ctx = altStartState.transition[0].label.getSemanticContext();
         if (ctx.isSyntacticPredicate()) {
            SemanticContext.Predicate p = (SemanticContext.Predicate)ctx;
            if (p.predicateAST.getType() == 14) {
               if (ctx.isSyntacticPredicate()) {
                  this.nfa.grammar.synPredUsedInDFA(this, ctx);
               }

               return (SemanticContext.Predicate)altStartState.transition[0].label.getSemanticContext();
            }
         }
      }

      return null;
   }
}
