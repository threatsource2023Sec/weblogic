package org.antlr.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.misc.MultiMap;
import org.antlr.misc.Utils;
import org.antlr.runtime.Token;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;

public class DecisionProbe {
   public DFA dfa;
   protected Set statesWithSyntacticallyAmbiguousAltsSet = new HashSet();
   protected Map stateToSyntacticallyAmbiguousTokensRuleAltsMap = new HashMap();
   protected Set statesResolvedWithSemanticPredicatesSet = new HashSet();
   protected Map stateToAltSetWithSemanticPredicatesMap = new HashMap();
   protected Map stateToIncompletelyCoveredAltsMap = new HashMap();
   protected Set danglingStates = new HashSet();
   protected Set altsWithProblem = new HashSet();
   public boolean nonLLStarDecision = false;
   protected MultiMap stateToRecursionOverflowConfigurationsMap = new MultiMap();
   protected boolean timedOut = false;
   protected Map stateReachable;
   public static final Integer REACHABLE_BUSY = Utils.integer(-1);
   public static final Integer REACHABLE_NO = Utils.integer(0);
   public static final Integer REACHABLE_YES = Utils.integer(1);
   protected Set statesVisitedAtInputDepth;
   protected Set statesVisitedDuringSampleSequence;
   public static boolean verbose = false;

   public DecisionProbe(DFA dfa) {
      this.dfa = dfa;
   }

   public String getDescription() {
      return this.dfa.getNFADecisionStartState().getDescription();
   }

   public boolean isReduced() {
      return this.dfa.isReduced();
   }

   public boolean isCyclic() {
      return this.dfa.isCyclic();
   }

   public boolean isDeterministic() {
      if (this.danglingStates.isEmpty() && this.statesWithSyntacticallyAmbiguousAltsSet.isEmpty() && this.dfa.getUnreachableAlts().isEmpty()) {
         return true;
      } else if (this.statesWithSyntacticallyAmbiguousAltsSet.size() > 0) {
         Iterator i$ = this.statesWithSyntacticallyAmbiguousAltsSet.iterator();

         DFAState d;
         do {
            if (!i$.hasNext()) {
               return true;
            }

            d = (DFAState)i$.next();
         } while(this.statesResolvedWithSemanticPredicatesSet.contains(d));

         return false;
      } else {
         return false;
      }
   }

   public boolean analysisOverflowed() {
      return this.stateToRecursionOverflowConfigurationsMap.size() > 0;
   }

   public boolean isNonLLStarDecision() {
      return this.nonLLStarDecision;
   }

   public int getNumberOfStates() {
      return this.dfa.getNumberOfStates();
   }

   public List getUnreachableAlts() {
      return this.dfa.getUnreachableAlts();
   }

   public Set getDanglingStates() {
      return this.danglingStates;
   }

   public Set getNonDeterministicAlts() {
      return this.altsWithProblem;
   }

   public List getNonDeterministicAltsForState(DFAState targetState) {
      Set nondetAlts = targetState.getNonDeterministicAlts();
      if (nondetAlts == null) {
         return null;
      } else {
         List sorted = new LinkedList();
         sorted.addAll(nondetAlts);
         Collections.sort(sorted);
         return sorted;
      }
   }

   public Set getDFAStatesWithSyntacticallyAmbiguousAlts() {
      return this.statesWithSyntacticallyAmbiguousAltsSet;
   }

   public Set getDisabledAlternatives(DFAState d) {
      return d.getDisabledAlternatives();
   }

   public void removeRecursiveOverflowState(DFAState d) {
      Integer stateI = Utils.integer(d.stateNumber);
      this.stateToRecursionOverflowConfigurationsMap.remove(stateI);
   }

   public List getSampleNonDeterministicInputSequence(DFAState targetState) {
      Set dfaStates = this.getDFAPathStatesToTarget(targetState);
      this.statesVisitedDuringSampleSequence = new HashSet();
      List labels = new ArrayList();
      if (this.dfa != null && this.dfa.startState != null) {
         this.getSampleInputSequenceUsingStateSet(this.dfa.startState, targetState, dfaStates, labels);
         return labels;
      } else {
         return labels;
      }
   }

   public String getInputSequenceDisplay(List labels) {
      Grammar g = this.dfa.nfa.grammar;
      StringBuilder buf = new StringBuilder();
      Iterator it = labels.iterator();

      while(it.hasNext()) {
         Label label = (Label)it.next();
         buf.append(label.toString(g));
         if (it.hasNext() && g.type != 1) {
            buf.append(' ');
         }
      }

      return buf.toString();
   }

   public List getNFAPathStatesForAlt(int firstAlt, int alt, List labels) {
      NFAState nfaStart = this.dfa.getNFADecisionStartState();
      List path = new LinkedList();

      NFAState isolatedAltStart;
      for(int a = firstAlt; a <= alt; ++a) {
         isolatedAltStart = this.dfa.nfa.grammar.getNFAStateForAltOfDecision(nfaStart, a);
         path.add(isolatedAltStart);
      }

      NFAState altStart = this.dfa.nfa.grammar.getNFAStateForAltOfDecision(nfaStart, alt);
      isolatedAltStart = (NFAState)altStart.transition[0].target;
      path.add(isolatedAltStart);
      this.statesVisitedAtInputDepth = new HashSet();
      this.getNFAPath(isolatedAltStart, 0, labels, path);
      return path;
   }

   public SemanticContext getSemanticContextForAlt(DFAState d, int alt) {
      Map altToPredMap = (Map)this.stateToAltSetWithSemanticPredicatesMap.get(d);
      return altToPredMap == null ? null : (SemanticContext)altToPredMap.get(Utils.integer(alt));
   }

   public boolean hasPredicate() {
      return this.stateToAltSetWithSemanticPredicatesMap.size() > 0;
   }

   public Set getNondeterministicStatesResolvedWithSemanticPredicate() {
      return this.statesResolvedWithSemanticPredicatesSet;
   }

   public Map getIncompletelyCoveredAlts(DFAState d) {
      return (Map)this.stateToIncompletelyCoveredAltsMap.get(d);
   }

   public void issueWarnings() {
      if (this.nonLLStarDecision && !this.dfa.getAutoBacktrackMode()) {
         ErrorManager.nonLLStarDecision(this);
      }

      this.issueRecursionWarnings();
      Set resolvedStates = this.getNondeterministicStatesResolvedWithSemanticPredicate();
      Set problemStates = this.getDFAStatesWithSyntacticallyAmbiguousAlts();
      GrammarAST decAST;
      if (problemStates.size() > 0) {
         Iterator it = problemStates.iterator();

         label84:
         while(true) {
            DFAState d;
            do {
               if (!it.hasNext() || this.dfa.nfa.grammar.NFAToDFAConversionExternallyAborted()) {
                  break label84;
               }

               d = (DFAState)it.next();
               Map insufficientAltToLocations = this.getIncompletelyCoveredAlts(d);
               if (insufficientAltToLocations != null && insufficientAltToLocations.size() > 0) {
                  ErrorManager.insufficientPredicates(this, d, insufficientAltToLocations);
               }
            } while(resolvedStates != null && resolvedStates.contains(d));

            Set disabledAlts = this.getDisabledAlternatives(d);
            this.stripWildCardAlts(disabledAlts);
            if (disabledAlts.size() > 0) {
               boolean explicitlyGreedy = false;
               decAST = d.dfa.nfa.grammar.getDecisionBlockAST(d.dfa.decisionNumber);
               if (decAST != null) {
                  String greedyS = (String)decAST.getBlockOption("greedy");
                  if (greedyS != null && greedyS.equals("true")) {
                     explicitlyGreedy = true;
                  }
               }

               if (!explicitlyGreedy) {
                  ErrorManager.nondeterminism(this, d);
               }
            }
         }
      }

      Set danglingStates = this.getDanglingStates();
      if (danglingStates.size() > 0) {
         Iterator i$ = danglingStates.iterator();

         while(i$.hasNext()) {
            DFAState d = (DFAState)i$.next();
            ErrorManager.danglingState(this, d);
         }
      }

      if (!this.nonLLStarDecision) {
         List unreachableAlts = this.dfa.getUnreachableAlts();
         if (unreachableAlts != null && unreachableAlts.size() > 0) {
            boolean isInheritedTokensRule = false;
            if (this.dfa.isTokensRuleDecision()) {
               Iterator i$ = unreachableAlts.iterator();

               while(i$.hasNext()) {
                  Integer altI = (Integer)i$.next();
                  decAST = this.dfa.getDecisionASTNode();
                  GrammarAST altAST = (GrammarAST)decAST.getChild(altI - 1);
                  GrammarAST delegatedTokensAlt = (GrammarAST)altAST.getFirstChildWithType(29);
                  if (delegatedTokensAlt != null) {
                     isInheritedTokensRule = true;
                     ErrorManager.grammarWarning(162, this.dfa.nfa.grammar, (Token)null, this.dfa.nfa.grammar.name, delegatedTokensAlt.getChild(0).getText());
                  }
               }
            }

            if (!isInheritedTokensRule) {
               ErrorManager.unreachableAlts(this, unreachableAlts);
            }
         }
      }

   }

   protected void stripWildCardAlts(Set disabledAlts) {
      List sortedDisableAlts = new ArrayList(disabledAlts);
      Collections.sort(sortedDisableAlts);
      Integer lastAlt = (Integer)sortedDisableAlts.get(sortedDisableAlts.size() - 1);
      GrammarAST blockAST = this.dfa.nfa.grammar.getDecisionBlockAST(this.dfa.decisionNumber);
      GrammarAST lastAltAST;
      if (blockAST.getChild(0).getType() == 58) {
         lastAltAST = (GrammarAST)blockAST.getChild(lastAlt);
      } else {
         lastAltAST = (GrammarAST)blockAST.getChild(lastAlt - 1);
      }

      if (lastAltAST.getType() != 33 && lastAltAST.getChild(0).getType() == 98 && lastAltAST.getChild(1).getType() == 32) {
         disabledAlts.remove(lastAlt);
      }

   }

   protected void issueRecursionWarnings() {
      Set dfaStatesWithRecursionProblems = this.stateToRecursionOverflowConfigurationsMap.keySet();
      Map altToTargetToCallSitesMap = new HashMap();
      Map altToDFAState = new HashMap();
      this.computeAltToProblemMaps(dfaStatesWithRecursionProblems, this.stateToRecursionOverflowConfigurationsMap, altToTargetToCallSitesMap, altToDFAState);
      Set alts = altToTargetToCallSitesMap.keySet();
      List sortedAlts = new ArrayList(alts);
      Collections.sort(sortedAlts);
      Iterator i$ = sortedAlts.iterator();

      while(i$.hasNext()) {
         Integer altI = (Integer)i$.next();
         Map targetToCallSiteMap = (Map)altToTargetToCallSitesMap.get(altI);
         Set targetRules = targetToCallSiteMap.keySet();
         Collection callSiteStates = targetToCallSiteMap.values();
         DFAState sampleBadState = (DFAState)altToDFAState.get(altI);
         ErrorManager.recursionOverflow(this, sampleBadState, altI, targetRules, callSiteStates);
      }

   }

   private void computeAltToProblemMaps(Set dfaStatesUnaliased, Map configurationsMap, Map altToTargetToCallSitesMap, Map altToDFAState) {
      Iterator i$ = dfaStatesUnaliased.iterator();

      while(i$.hasNext()) {
         Integer stateI = (Integer)i$.next();
         List configs = (List)configurationsMap.get(stateI);

         for(int i = 0; i < configs.size(); ++i) {
            NFAConfiguration c = (NFAConfiguration)configs.get(i);
            NFAState ruleInvocationState = this.dfa.nfa.getState(c.state);
            Transition transition0 = ruleInvocationState.transition[0];
            RuleClosureTransition ref = (RuleClosureTransition)transition0;
            String targetRule = ((NFAState)ref.target).enclosingRule.name;
            Integer altI = Utils.integer(c.alt);
            Map targetToCallSiteMap = (Map)altToTargetToCallSitesMap.get(altI);
            if (targetToCallSiteMap == null) {
               targetToCallSiteMap = new HashMap();
               altToTargetToCallSitesMap.put(altI, targetToCallSiteMap);
            }

            Set callSites = (Set)((Map)targetToCallSiteMap).get(targetRule);
            if (callSites == null) {
               callSites = new HashSet();
               ((Map)targetToCallSiteMap).put(targetRule, callSites);
            }

            ((Set)callSites).add(ruleInvocationState);
            if (altToDFAState.get(altI) == null) {
               DFAState sampleBadState = this.dfa.getState(stateI);
               altToDFAState.put(altI, sampleBadState);
            }
         }
      }

   }

   private Set getUnaliasedDFAStateSet(Set dfaStatesWithRecursionProblems) {
      Set dfaStatesUnaliased = new HashSet();
      Iterator i$ = dfaStatesWithRecursionProblems.iterator();

      while(i$.hasNext()) {
         Integer stateI = (Integer)i$.next();
         DFAState d = this.dfa.getState(stateI);
         dfaStatesUnaliased.add(Utils.integer(d.stateNumber));
      }

      return dfaStatesUnaliased;
   }

   public void reportDanglingState(DFAState d) {
      this.danglingStates.add(d);
   }

   public void reportNonLLStarDecision(DFA dfa) {
      this.nonLLStarDecision = true;
      ++dfa.nfa.grammar.numNonLLStar;
      this.altsWithProblem.addAll(dfa.recursiveAltSet.toList());
   }

   public void reportRecursionOverflow(DFAState d, NFAConfiguration recursionNFAConfiguration) {
      if (d.stateNumber > 0) {
         Integer stateI = Utils.integer(d.stateNumber);
         this.stateToRecursionOverflowConfigurationsMap.map(stateI, recursionNFAConfiguration);
      }

   }

   public void reportNondeterminism(DFAState d, Set nondeterministicAlts) {
      this.altsWithProblem.addAll(nondeterministicAlts);
      this.statesWithSyntacticallyAmbiguousAltsSet.add(d);
      this.dfa.nfa.grammar.setOfNondeterministicDecisionNumbers.add(Utils.integer(this.dfa.getDecisionNumber()));
   }

   public void reportLexerRuleNondeterminism(DFAState d, Set nondeterministicAlts) {
      this.stateToSyntacticallyAmbiguousTokensRuleAltsMap.put(d, nondeterministicAlts);
   }

   public void reportNondeterminismResolvedWithSemanticPredicate(DFAState d) {
      if (d.abortedDueToRecursionOverflow) {
         d.dfa.probe.removeRecursiveOverflowState(d);
      }

      this.statesResolvedWithSemanticPredicatesSet.add(d);
      this.dfa.nfa.grammar.setOfNondeterministicDecisionNumbersResolvedWithPredicates.add(Utils.integer(this.dfa.getDecisionNumber()));
   }

   public void reportAltPredicateContext(DFAState d, Map altPredicateContext) {
      Map copy = new HashMap();
      copy.putAll(altPredicateContext);
      this.stateToAltSetWithSemanticPredicatesMap.put(d, copy);
   }

   public void reportIncompletelyCoveredAlts(DFAState d, Map altToLocationsReachableWithoutPredicate) {
      this.stateToIncompletelyCoveredAltsMap.put(d, altToLocationsReachableWithoutPredicate);
   }

   protected boolean reachesState(DFAState startState, DFAState targetState, Set states) {
      if (startState == targetState) {
         states.add(targetState);
         this.stateReachable.put(startState.stateNumber, REACHABLE_YES);
         return true;
      } else {
         DFAState s = startState;
         this.stateReachable.put(startState.stateNumber, REACHABLE_BUSY);

         for(int i = 0; i < s.getNumberOfTransitions(); ++i) {
            Transition t = s.transition(i);
            DFAState edgeTarget = (DFAState)t.target;
            Integer targetStatus = (Integer)this.stateReachable.get(edgeTarget.stateNumber);
            if (targetStatus != REACHABLE_BUSY) {
               if (targetStatus == REACHABLE_YES) {
                  this.stateReachable.put(s.stateNumber, REACHABLE_YES);
                  return true;
               }

               if (targetStatus != REACHABLE_NO && this.reachesState(edgeTarget, targetState, states)) {
                  states.add(s);
                  this.stateReachable.put(s.stateNumber, REACHABLE_YES);
                  return true;
               }
            }
         }

         this.stateReachable.put(s.stateNumber, REACHABLE_NO);
         return false;
      }
   }

   protected Set getDFAPathStatesToTarget(DFAState targetState) {
      Set dfaStates = new HashSet();
      this.stateReachable = new HashMap();
      if (this.dfa != null && this.dfa.startState != null) {
         this.reachesState(this.dfa.startState, targetState, dfaStates);
         return dfaStates;
      } else {
         return dfaStates;
      }
   }

   protected void getSampleInputSequenceUsingStateSet(State startState, State targetState, Set states, List labels) {
      this.statesVisitedDuringSampleSequence.add(startState.stateNumber);

      for(int i = 0; i < startState.getNumberOfTransitions(); ++i) {
         Transition t = startState.transition(i);
         DFAState edgeTarget = (DFAState)t.target;
         if (states.contains(edgeTarget) && !this.statesVisitedDuringSampleSequence.contains(edgeTarget.stateNumber)) {
            labels.add(t.label);
            if (edgeTarget != targetState) {
               this.getSampleInputSequenceUsingStateSet(edgeTarget, targetState, states, labels);
            }

            return;
         }
      }

      labels.add(new Label(-5));
   }

   protected boolean getNFAPath(NFAState s, int labelIndex, List labels, List path) {
      String thisStateKey = this.getStateLabelIndexKey(s.stateNumber, labelIndex);
      if (this.statesVisitedAtInputDepth.contains(thisStateKey)) {
         return false;
      } else {
         this.statesVisitedAtInputDepth.add(thisStateKey);

         for(int i = 0; i < s.getNumberOfTransitions(); ++i) {
            Transition t = s.transition[i];
            NFAState edgeTarget = (NFAState)t.target;
            Label label = (Label)labels.get(labelIndex);
            boolean found;
            if (!t.label.isEpsilon() && !t.label.isSemanticPredicate()) {
               if (t.label.matches(label)) {
                  path.add(edgeTarget);
                  if (labelIndex == labels.size() - 1) {
                     this.statesVisitedAtInputDepth.remove(thisStateKey);
                     return true;
                  }

                  found = this.getNFAPath(edgeTarget, labelIndex + 1, labels, path);
                  if (found) {
                     this.statesVisitedAtInputDepth.remove(thisStateKey);
                     return true;
                  }

                  path.remove(path.size() - 1);
               }
            } else {
               path.add(edgeTarget);
               found = this.getNFAPath(edgeTarget, labelIndex, labels, path);
               if (found) {
                  this.statesVisitedAtInputDepth.remove(thisStateKey);
                  return true;
               }

               path.remove(path.size() - 1);
            }
         }

         this.statesVisitedAtInputDepth.remove(thisStateKey);
         return false;
      }
   }

   protected String getStateLabelIndexKey(int s, int i) {
      StringBuilder buf = new StringBuilder();
      buf.append(s);
      buf.append('_');
      buf.append(i);
      return buf.toString();
   }

   public String getTokenNameForTokensRuleAlt(int alt) {
      NFAState decisionState = this.dfa.getNFADecisionStartState();
      NFAState altState = this.dfa.nfa.grammar.getNFAStateForAltOfDecision(decisionState, alt);
      NFAState decisionLeft = (NFAState)altState.transition[0].target;
      RuleClosureTransition ruleCallEdge = (RuleClosureTransition)decisionLeft.transition[0];
      NFAState ruleStartState = (NFAState)ruleCallEdge.target;
      return ruleStartState.enclosingRule.name;
   }

   public void reset() {
      this.stateToRecursionOverflowConfigurationsMap.clear();
   }
}
