package org.antlr.tool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.antlr.analysis.NFAState;
import org.antlr.analysis.RuleClosureTransition;
import org.antlr.analysis.Transition;
import org.antlr.runtime.tree.Tree;

public class GrammarSanity {
   protected Set visitedDuringRecursionCheck = null;
   protected Grammar grammar;

   public GrammarSanity(Grammar grammar) {
      this.grammar = grammar;
   }

   public List checkAllRulesForLeftRecursion() {
      this.grammar.buildNFA();
      this.grammar.leftRecursiveRules = new HashSet();
      List listOfRecursiveCycles = new ArrayList();

      for(int i = 0; i < this.grammar.composite.ruleIndexToRuleList.size(); ++i) {
         Rule r = (Rule)this.grammar.composite.ruleIndexToRuleList.elementAt(i);
         if (r != null) {
            this.visitedDuringRecursionCheck = new HashSet();
            this.visitedDuringRecursionCheck.add(r);
            Set visitedStates = new HashSet();
            this.traceStatesLookingForLeftRecursion(r.startState, visitedStates, listOfRecursiveCycles);
         }
      }

      if (listOfRecursiveCycles.size() > 0) {
         ErrorManager.leftRecursionCycles(listOfRecursiveCycles);
      }

      return listOfRecursiveCycles;
   }

   protected boolean traceStatesLookingForLeftRecursion(NFAState s, Set visitedStates, List listOfRecursiveCycles) {
      if (s.isAcceptState()) {
         return true;
      } else if (visitedStates.contains(s)) {
         return false;
      } else {
         visitedStates.add(s);
         boolean stateReachesAcceptState = false;
         Transition t0 = s.transition[0];
         if (t0 instanceof RuleClosureTransition) {
            RuleClosureTransition refTrans = (RuleClosureTransition)t0;
            Rule refRuleDef = refTrans.rule;
            if (this.visitedDuringRecursionCheck.contains(refRuleDef)) {
               this.grammar.leftRecursiveRules.add(refRuleDef);
               this.addRulesToCycle(refRuleDef, s.enclosingRule, listOfRecursiveCycles);
            } else {
               this.visitedDuringRecursionCheck.add(refRuleDef);
               boolean callReachedAcceptState = this.traceStatesLookingForLeftRecursion((NFAState)t0.target, new HashSet(), listOfRecursiveCycles);
               this.visitedDuringRecursionCheck.remove(refRuleDef);
               if (callReachedAcceptState) {
                  NFAState followingState = ((RuleClosureTransition)t0).followState;
                  stateReachesAcceptState |= this.traceStatesLookingForLeftRecursion(followingState, visitedStates, listOfRecursiveCycles);
               }
            }
         } else if (t0.label.isEpsilon() || t0.label.isSemanticPredicate()) {
            stateReachesAcceptState |= this.traceStatesLookingForLeftRecursion((NFAState)t0.target, visitedStates, listOfRecursiveCycles);
         }

         Transition t1 = s.transition[1];
         if (t1 != null) {
            stateReachesAcceptState |= this.traceStatesLookingForLeftRecursion((NFAState)t1.target, visitedStates, listOfRecursiveCycles);
         }

         return stateReachesAcceptState;
      }
   }

   protected void addRulesToCycle(Rule targetRule, Rule enclosingRule, List listOfRecursiveCycles) {
      boolean foundCycle = false;

      for(int i = 0; i < listOfRecursiveCycles.size(); ++i) {
         Set rulesInCycle = (Set)listOfRecursiveCycles.get(i);
         if (rulesInCycle.contains(targetRule)) {
            rulesInCycle.add(enclosingRule);
            foundCycle = true;
         }

         if (rulesInCycle.contains(enclosingRule)) {
            rulesInCycle.add(targetRule);
            foundCycle = true;
         }
      }

      if (!foundCycle) {
         Set cycle = new HashSet();
         cycle.add(targetRule);
         cycle.add(enclosingRule);
         listOfRecursiveCycles.add(cycle);
      }

   }

   public void checkRuleReference(GrammarAST scopeAST, GrammarAST refAST, GrammarAST argsAST, String currentRuleName) {
      Rule r = this.grammar.getRule(refAST.getText());
      if (refAST.getType() == 80) {
         if (argsAST != null) {
            if (r != null && r.argActionAST == null) {
               ErrorManager.grammarError(130, this.grammar, argsAST.getToken(), r.name);
            }
         } else if (r != null && r.argActionAST != null) {
            ErrorManager.grammarError(129, this.grammar, refAST.getToken(), r.name);
         }
      } else if (refAST.getType() == 94) {
         if (this.grammar.type != 1) {
            if (argsAST != null) {
               ErrorManager.grammarError(131, this.grammar, refAST.getToken(), refAST.getText());
            }

            return;
         }

         if (argsAST != null) {
            if (r != null && r.argActionAST == null) {
               ErrorManager.grammarError(130, this.grammar, argsAST.getToken(), r.name);
            }
         } else if (r != null && r.argActionAST != null) {
            ErrorManager.grammarError(129, this.grammar, refAST.getToken(), r.name);
         }
      }

   }

   public void ensureAltIsSimpleNodeOrTree(GrammarAST altAST, GrammarAST elementAST, int outerAltNum) {
      if (this.isValidSimpleElementNode(elementAST)) {
         GrammarAST next = elementAST.getNextSibling();
         if (!this.isNextNonActionElementEOA(next)) {
            ErrorManager.grammarWarning(153, this.grammar, next.token, outerAltNum);
         }

      } else {
         switch (elementAST.getType()) {
            case 4:
            case 14:
            case 41:
            case 83:
            case 90:
               this.ensureAltIsSimpleNodeOrTree(altAST, elementAST.getNextSibling(), outerAltNum);
               return;
            case 13:
            case 63:
               if (this.isValidSimpleElementNode(elementAST.getChild(1))) {
                  return;
               }
            default:
               ErrorManager.grammarWarning(153, this.grammar, elementAST.token, outerAltNum);
         }
      }
   }

   protected boolean isValidSimpleElementNode(Tree t) {
      switch (t.getType()) {
         case 18:
         case 88:
         case 94:
         case 96:
         case 98:
            return true;
         default:
            return false;
      }
   }

   protected boolean isNextNonActionElementEOA(GrammarAST t) {
      while(t.getType() == 4 || t.getType() == 83) {
         t = t.getNextSibling();
      }

      if (t.getType() == 32) {
         return true;
      } else {
         return false;
      }
   }
}
