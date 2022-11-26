package org.antlr.analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.antlr.misc.IntSet;
import org.antlr.misc.IntervalSet;
import org.antlr.tool.Grammar;
import org.antlr.tool.Rule;

public class LL1Analyzer {
   public static final int DETECT_PRED_EOR = 0;
   public static final int DETECT_PRED_FOUND = 1;
   public static final int DETECT_PRED_NOT_FOUND = 2;
   public Grammar grammar;
   protected Set lookBusy = new HashSet();
   public Map FIRSTCache = new HashMap();
   public Map FOLLOWCache = new HashMap();

   public LL1Analyzer(Grammar grammar) {
      this.grammar = grammar;
   }

   public LookaheadSet FIRST(NFAState s) {
      this.lookBusy.clear();
      LookaheadSet look = this._FIRST(s, false);
      return look;
   }

   public LookaheadSet FOLLOW(Rule r) {
      LookaheadSet f = (LookaheadSet)this.FOLLOWCache.get(r);
      if (f != null) {
         return f;
      } else {
         f = this._FIRST(r.stopState, true);
         this.FOLLOWCache.put(r, f);
         return f;
      }
   }

   public LookaheadSet LOOK(NFAState s) {
      if (NFAToDFAConverter.debug) {
         System.out.println("> LOOK(" + s + ")");
      }

      this.lookBusy.clear();
      LookaheadSet look = this._FIRST(s, true);
      if (this.grammar.type != 1 && look.member(1)) {
         LookaheadSet f = this.FOLLOW(s.enclosingRule);
         f.orInPlace(look);
         f.remove(1);
         look = f;
      } else if (this.grammar.type == 1 && look.member(-2)) {
         look = new LookaheadSet(IntervalSet.COMPLETE_SET);
      }

      if (NFAToDFAConverter.debug) {
         System.out.println("< LOOK(" + s + ")=" + look.toString(this.grammar));
      }

      return look;
   }

   protected LookaheadSet _FIRST(NFAState s, boolean chaseFollowTransitions) {
      if (!chaseFollowTransitions && s.isAcceptState()) {
         return this.grammar.type == 1 ? new LookaheadSet(IntervalSet.COMPLETE_SET) : new LookaheadSet(1);
      } else if (this.lookBusy.contains(s)) {
         return new LookaheadSet();
      } else {
         this.lookBusy.add(s);
         Transition transition0 = s.transition[0];
         if (transition0 == null) {
            return null;
         } else if (transition0.label.isAtom()) {
            int atom = transition0.label.getAtom();
            return new LookaheadSet(atom);
         } else if (transition0.label.isSet()) {
            IntSet sl = transition0.label.getSet();
            return new LookaheadSet(sl);
         } else {
            LookaheadSet tset = null;
            if (!chaseFollowTransitions && transition0 instanceof RuleClosureTransition) {
               tset = (LookaheadSet)this.FIRSTCache.get((NFAState)transition0.target);
            }

            if (tset == null) {
               tset = this._FIRST((NFAState)transition0.target, chaseFollowTransitions);
               if (!chaseFollowTransitions && transition0 instanceof RuleClosureTransition) {
                  this.FIRSTCache.put((NFAState)transition0.target, tset);
               }
            }

            LookaheadSet tsetCached = tset;
            if (this.grammar.type != 1 && tset.member(1) && transition0 instanceof RuleClosureTransition) {
               RuleClosureTransition ruleInvocationTrans = (RuleClosureTransition)transition0;
               NFAState following = ruleInvocationTrans.followState;
               LookaheadSet fset = this._FIRST(following, chaseFollowTransitions);
               fset.orInPlace(tset);
               fset.remove(1);
               tset = fset;
            }

            Transition transition1 = s.transition[1];
            if (transition1 != null) {
               LookaheadSet tset1 = this._FIRST((NFAState)transition1.target, chaseFollowTransitions);
               tset1.orInPlace(tset);
               tset = tset1;
            }

            return tset == tsetCached ? new LookaheadSet(tset) : tset;
         }
      }
   }

   public boolean detectConfoundingPredicates(NFAState s) {
      this.lookBusy.clear();
      Rule r = s.enclosingRule;
      return this._detectConfoundingPredicates(s, r, false) == 1;
   }

   protected int _detectConfoundingPredicates(NFAState s, Rule enclosingRule, boolean chaseFollowTransitions) {
      if (!chaseFollowTransitions && s.isAcceptState()) {
         return this.grammar.type == 1 ? 2 : 0;
      } else if (this.lookBusy.contains(s)) {
         return 2;
      } else {
         this.lookBusy.add(s);
         Transition transition0 = s.transition[0];
         if (transition0 == null) {
            return 2;
         } else if (!transition0.label.isSemanticPredicate() && !transition0.label.isEpsilon()) {
            return 2;
         } else {
            if (transition0.label.isSemanticPredicate()) {
               SemanticContext ctx = transition0.label.getSemanticContext();
               SemanticContext.Predicate p = (SemanticContext.Predicate)ctx;
               if (p.predicateAST.getType() != 14) {
                  return 1;
               }
            }

            int result = this._detectConfoundingPredicates((NFAState)transition0.target, enclosingRule, chaseFollowTransitions);
            if (result == 1) {
               return 1;
            } else {
               if (result == 0 && transition0 instanceof RuleClosureTransition) {
                  RuleClosureTransition ruleInvocationTrans = (RuleClosureTransition)transition0;
                  NFAState following = ruleInvocationTrans.followState;
                  int afterRuleResult = this._detectConfoundingPredicates(following, enclosingRule, chaseFollowTransitions);
                  if (afterRuleResult == 1) {
                     return 1;
                  }
               }

               Transition transition1 = s.transition[1];
               if (transition1 != null) {
                  int t1Result = this._detectConfoundingPredicates((NFAState)transition1.target, enclosingRule, chaseFollowTransitions);
                  if (t1Result == 1) {
                     return 1;
                  }
               }

               return 2;
            }
         }
      }
   }

   public SemanticContext getPredicates(NFAState altStartState) {
      this.lookBusy.clear();
      return this._getPredicates(altStartState, altStartState);
   }

   protected SemanticContext _getPredicates(NFAState s, NFAState altStartState) {
      if (s.isAcceptState()) {
         return null;
      } else if (this.lookBusy.contains(s)) {
         return null;
      } else {
         this.lookBusy.add(s);
         Transition transition0 = s.transition[0];
         if (transition0 == null) {
            return null;
         } else if (!transition0.label.isSemanticPredicate() && !transition0.label.isEpsilon()) {
            return null;
         } else {
            SemanticContext p = null;
            SemanticContext p1 = null;
            if (transition0.label.isSemanticPredicate()) {
               p = transition0.label.getSemanticContext();
               if (((SemanticContext.Predicate)p).predicateAST.getType() == 14 && s == altStartState.transition[0].target) {
                  p = null;
               }
            }

            SemanticContext p0 = this._getPredicates((NFAState)transition0.target, altStartState);
            Transition transition1 = s.transition[1];
            if (transition1 != null) {
               p1 = this._getPredicates((NFAState)transition1.target, altStartState);
            }

            return SemanticContext.and(p, SemanticContext.or(p0, p1));
         }
      }
   }
}
