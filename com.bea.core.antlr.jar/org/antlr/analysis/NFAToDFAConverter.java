package org.antlr.analysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.antlr.misc.BitSet;
import org.antlr.misc.OrderedHashSet;
import org.antlr.misc.Utils;
import org.antlr.tool.ErrorManager;

public class NFAToDFAConverter {
   protected List work = new LinkedList();
   protected NFAContext[] contextTrees;
   protected DFA dfa;
   public static boolean debug = false;
   public static boolean SINGLE_THREADED_NFA_CONVERSION = true;
   protected boolean computingStartState = false;

   public NFAToDFAConverter(DFA dfa) {
      this.dfa = dfa;
      int nAlts = dfa.getNumberOfAlts();
      this.initContextTrees(nAlts);
   }

   public void convert() {
      for(this.dfa.startState = this.computeStartState(); this.work.size() > 0 && !this.dfa.nfa.grammar.NFAToDFAConversionExternallyAborted(); this.work.remove(0)) {
         DFAState d = (DFAState)this.work.get(0);
         if (this.dfa.nfa.grammar.composite.watchNFAConversion) {
            System.out.println("convert DFA state " + d.stateNumber + " (" + d.nfaConfigurations.size() + " nfa states)");
         }

         int k = this.dfa.getUserMaxLookahead();
         if (k > 0 && k == d.getLookaheadDepth()) {
            this.resolveNonDeterminisms(d);
            if (d.isResolvedWithPredicates()) {
               this.addPredicateTransitions(d);
            } else {
               d.setAcceptState(true);
            }
         } else {
            this.findNewDFAStatesAndAddDFATransitions(d);
         }
      }

      this.dfa.findAllGatedSynPredsUsedInDFAAcceptStates();
   }

   protected DFAState computeStartState() {
      NFAState alt = this.dfa.decisionNFAStartState;
      DFAState startState = this.dfa.newState();
      this.computingStartState = true;
      int i = 0;

      for(int altNum = 1; alt != null; alt = (NFAState)alt.transition[1].target) {
         NFAContext initialContext = this.contextTrees[i];
         if (i == 0 && this.dfa.getNFADecisionStartState().decisionStateType == 1) {
            int numAltsIncludingExitBranch = this.dfa.nfa.grammar.getNumberOfAltsForDecisionNFA(this.dfa.decisionNFAStartState);
            this.closure((NFAState)alt.transition[0].target, numAltsIncludingExitBranch, initialContext, SemanticContext.EMPTY_SEMANTIC_CONTEXT, startState, true);
            altNum = 1;
         } else {
            this.closure((NFAState)alt.transition[0].target, altNum, initialContext, SemanticContext.EMPTY_SEMANTIC_CONTEXT, startState, true);
            ++altNum;
         }

         ++i;
         if (alt.transition[1] == null) {
            break;
         }
      }

      this.dfa.addState(startState);
      this.work.add(startState);
      this.computingStartState = false;
      return startState;
   }

   protected void findNewDFAStatesAndAddDFATransitions(DFAState d) {
      OrderedHashSet labels = d.getReachableLabels();
      Label EOTLabel = new Label(-2);
      boolean containsEOT = labels != null && labels.contains(EOTLabel);
      if (!this.dfa.isGreedy() && containsEOT) {
         this.convertToEOTAcceptState(d);
      } else {
         int numberOfEdgesEmanating = 0;
         Map targetToLabelMap = new HashMap();
         int numLabels = 0;
         if (labels != null) {
            numLabels = labels.size();
         }

         int minAlt;
         for(minAlt = 0; minAlt < numLabels; ++minAlt) {
            Label label = (Label)labels.get(minAlt);
            DFAState t = this.reach(d, label);
            if (debug) {
               System.out.println("DFA state after reach " + label + " " + d + "-" + label.toString(this.dfa.nfa.grammar) + "->" + t);
            }

            if (t != null) {
               if (t.getUniqueAlt() == -1) {
                  this.closure(t);
               }

               DFAState targetState = this.addDFAStateToWorkList(t);
               numberOfEdgesEmanating += addTransition(d, label, targetState, targetToLabelMap);
               targetState.setLookaheadDepth(d.getLookaheadDepth() + 1);
            }
         }

         if (!d.isResolvedWithPredicates() && numberOfEdgesEmanating == 0) {
            this.dfa.probe.reportDanglingState(d);
            minAlt = this.resolveByPickingMinAlt(d, (Set)null);
            d.setAcceptState(true);
            this.dfa.setAcceptState(minAlt, d);
         }

         if (d.isResolvedWithPredicates()) {
            this.addPredicateTransitions(d);
         }

      }
   }

   protected static int addTransition(DFAState d, Label label, DFAState targetState, Map targetToLabelMap) {
      int n = 0;
      if (DFAOptimizer.COLLAPSE_ALL_PARALLEL_EDGES) {
         Integer tI = Utils.integer(targetState.stateNumber);
         Transition oldTransition = (Transition)targetToLabelMap.get(tI);
         if (oldTransition != null) {
            if (label.getAtom() == -2) {
               oldTransition.label = new Label(-2);
            } else if (oldTransition.label.getAtom() != -2) {
               oldTransition.label.add(label);
            }
         } else {
            n = 1;
            label = (Label)label.clone();
            int transitionIndex = d.addTransition(targetState, label);
            Transition trans = d.getTransition(transitionIndex);
            targetToLabelMap.put(tI, trans);
         }
      } else {
         n = 1;
         d.addTransition(targetState, label);
      }

      return n;
   }

   public void closure(DFAState d) {
      if (debug) {
         System.out.println("closure(" + d + ")");
      }

      List configs = new ArrayList();
      configs.addAll(d.nfaConfigurations);
      int numConfigs = configs.size();

      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration c = (NFAConfiguration)configs.get(i);
         if (!c.singleAtomTransitionEmanating) {
            this.closure(this.dfa.nfa.getState(c.state), c.alt, c.context, c.semanticContext, d, false);
         }
      }

      d.closureBusy = null;
   }

   public void closure(NFAState p, int alt, NFAContext context, SemanticContext semanticContext, DFAState d, boolean collectPredicates) {
      if (debug) {
         System.out.println("closure at " + p.enclosingRule.name + " state " + p.stateNumber + "|" + alt + " filling DFA state " + d.stateNumber + " with context " + context);
      }

      NFAConfiguration proposedNFAConfiguration = new NFAConfiguration(p.stateNumber, alt, context, semanticContext);
      if (closureIsBusy(d, proposedNFAConfiguration)) {
         if (debug) {
            System.out.println("avoid visiting exact closure computation NFA config: " + proposedNFAConfiguration + " in " + p.enclosingRule.name);
            System.out.println("state is " + d.dfa.decisionNumber + "." + d.stateNumber);
         }

      } else {
         d.closureBusy.add(proposedNFAConfiguration);
         d.addNFAConfiguration(p, proposedNFAConfiguration);
         Transition transition0 = p.transition[0];
         RuleClosureTransition ref;
         NFAState altLeftEdge;
         if (transition0 instanceof RuleClosureTransition) {
            int depth = context.recursionDepthEmanatingFromState(p.stateNumber);
            if (depth == 1 && d.dfa.getUserMaxLookahead() == 0) {
               d.dfa.recursiveAltSet.add(alt);
               if (d.dfa.recursiveAltSet.size() > 1) {
                  d.abortedDueToMultipleRecursiveAlts = true;
                  throw new NonLLStarDecisionException(d.dfa);
               }
            }

            if (depth >= NFAContext.MAX_SAME_RULE_INVOCATIONS_PER_NFA_CONFIG_STACK) {
               d.abortedDueToRecursionOverflow = true;
               d.dfa.probe.reportRecursionOverflow(d, proposedNFAConfiguration);
               if (debug) {
                  System.out.println("analysis overflow in closure(" + d.stateNumber + ")");
               }

               return;
            }

            ref = (RuleClosureTransition)transition0;
            NFAContext newContext = new NFAContext(context, p);
            altLeftEdge = (NFAState)ref.target;
            this.closure(altLeftEdge, alt, newContext, semanticContext, d, collectPredicates);
         } else if (p.isAcceptState() && context.parent != null) {
            NFAState whichStateInvokedRule = context.invokingState;
            ref = (RuleClosureTransition)whichStateInvokedRule.transition[0];
            NFAState continueState = ref.followState;
            NFAContext newContext = context.parent;
            this.closure(continueState, alt, newContext, semanticContext, d, collectPredicates);
         } else {
            if (transition0 != null && transition0.isEpsilon()) {
               boolean collectPredicatesAfterAction = collectPredicates;
               if (transition0.isAction() && collectPredicates) {
                  collectPredicatesAfterAction = false;
               }

               this.closure((NFAState)transition0.target, alt, context, semanticContext, d, collectPredicatesAfterAction);
            } else if (transition0 != null && transition0.isSemanticPredicate()) {
               SemanticContext labelContext = transition0.label.getSemanticContext();
               if (this.computingStartState) {
                  if (collectPredicates) {
                     this.dfa.predicateVisible = true;
                  } else {
                     this.dfa.hasPredicateBlockedByAction = true;
                  }
               }

               SemanticContext newSemanticContext = semanticContext;
               if (collectPredicates) {
                  int walkAlt = this.dfa.decisionNFAStartState.translateDisplayAltToWalkAlt(alt);
                  altLeftEdge = this.dfa.nfa.grammar.getNFAStateForAltOfDecision(this.dfa.decisionNFAStartState, walkAlt);
                  if (!labelContext.isSyntacticPredicate() || p == altLeftEdge.transition[0].target) {
                     newSemanticContext = SemanticContext.and(semanticContext, labelContext);
                  }
               }

               this.closure((NFAState)transition0.target, alt, context, newSemanticContext, d, collectPredicates);
            }

            Transition transition1 = p.transition[1];
            if (transition1 != null && transition1.isEpsilon()) {
               this.closure((NFAState)transition1.target, alt, context, semanticContext, d, collectPredicates);
            }
         }

      }
   }

   public static boolean closureIsBusy(DFAState d, NFAConfiguration proposedNFAConfiguration) {
      return d.closureBusy.contains(proposedNFAConfiguration);
   }

   public DFAState reach(DFAState d, Label label) {
      DFAState labelDFATarget = this.dfa.newState();
      List configs = d.configurationsWithLabeledEdges;
      int numConfigs = configs.size();

      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration c = (NFAConfiguration)configs.get(i);
         if (!c.resolved && !c.resolveWithPredicate) {
            NFAState p = this.dfa.nfa.getState(c.state);
            Transition edge = p.transition[0];
            if (edge != null && c.singleAtomTransitionEmanating) {
               Label edgeLabel = edge.label;
               if ((c.context.parent == null || edgeLabel.label != -2) && Label.intersect(label, edgeLabel)) {
                  labelDFATarget.addNFAConfiguration((NFAState)edge.target, c.alt, c.context, c.semanticContext);
               }
            }
         }
      }

      if (labelDFATarget.nfaConfigurations.size() == 0) {
         this.dfa.setState(labelDFATarget.stateNumber, (DFAState)null);
         labelDFATarget = null;
      }

      return labelDFATarget;
   }

   protected void convertToEOTAcceptState(DFAState d) {
      Label eot = new Label(-2);
      int numConfigs = d.nfaConfigurations.size();

      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration c = (NFAConfiguration)d.nfaConfigurations.get(i);
         if (!c.resolved && !c.resolveWithPredicate) {
            NFAState p = this.dfa.nfa.getState(c.state);
            Transition edge = p.transition[0];
            Label edgeLabel = edge.label;
            if (edgeLabel.equals(eot)) {
               d.setAcceptState(true);
               d.nfaConfigurations.clear();
               d.addNFAConfiguration(p, c.alt, c.context, c.semanticContext);
               return;
            }
         }
      }

   }

   protected DFAState addDFAStateToWorkList(DFAState d) {
      DFAState existingState = this.dfa.addState(d);
      if (d != existingState) {
         this.dfa.setState(d.stateNumber, existingState);
         return existingState;
      } else {
         this.resolveNonDeterminisms(d);
         int alt = d.getUniquelyPredictedAlt();
         if (alt != -1) {
            d = this.convertToAcceptState(d, alt);
         } else {
            this.work.add(d);
         }

         return d;
      }
   }

   protected DFAState convertToAcceptState(DFAState d, int alt) {
      if (DFAOptimizer.MERGE_STOP_STATES && d.getNonDeterministicAlts() == null && !d.abortedDueToRecursionOverflow && !d.abortedDueToMultipleRecursiveAlts) {
         DFAState acceptStateForAlt = this.dfa.getAcceptState(alt);
         if (acceptStateForAlt != null) {
            SemanticContext gatedPreds = d.getGatedPredicatesInNFAConfigurations();
            SemanticContext existingStateGatedPreds = acceptStateForAlt.getGatedPredicatesInNFAConfigurations();
            if (gatedPreds == null && existingStateGatedPreds == null || gatedPreds != null && existingStateGatedPreds != null && gatedPreds.equals(existingStateGatedPreds)) {
               this.dfa.setState(d.stateNumber, acceptStateForAlt);
               this.dfa.removeState(d);
               return acceptStateForAlt;
            }
         }
      }

      d.setAcceptState(true);
      this.dfa.setAcceptState(alt, d);
      return d;
   }

   public void resolveNonDeterminisms(DFAState d) {
      if (debug) {
         System.out.println("resolveNonDeterminisms " + d.toString());
      }

      boolean conflictingLexerRules = false;
      Set nondeterministicAlts = d.getNonDeterministicAlts();
      if (debug && nondeterministicAlts != null) {
         System.out.println("nondet alts=" + nondeterministicAlts);
      }

      NFAConfiguration anyConfig = (NFAConfiguration)d.nfaConfigurations.get(0);
      NFAState anyState = this.dfa.nfa.getState(anyConfig.state);
      if (anyState.isEOTTargetState()) {
         Set allAlts = d.getAltSet();
         if (allAlts != null && allAlts.size() > 1) {
            nondeterministicAlts = allAlts;
            if (d.dfa.isTokensRuleDecision()) {
               this.dfa.probe.reportLexerRuleNondeterminism(d, allAlts);
               conflictingLexerRules = true;
            }
         }
      }

      if (d.abortedDueToRecursionOverflow || nondeterministicAlts != null) {
         if (!d.abortedDueToRecursionOverflow && !conflictingLexerRules) {
            this.dfa.probe.reportNondeterminism(d, nondeterministicAlts);
         }

         boolean resolved = this.tryToResolveWithSemanticPredicates(d, nondeterministicAlts);
         if (resolved) {
            if (debug) {
               System.out.println("resolved DFA state " + d.stateNumber + " with pred");
            }

            d.resolvedWithPredicates = true;
            this.dfa.probe.reportNondeterminismResolvedWithSemanticPredicate(d);
         } else {
            this.resolveByChoosingFirstAlt(d, nondeterministicAlts);
         }
      }
   }

   protected int resolveByChoosingFirstAlt(DFAState d, Set nondeterministicAlts) {
      int winningAlt;
      if (this.dfa.isGreedy()) {
         winningAlt = this.resolveByPickingMinAlt(d, nondeterministicAlts);
      } else {
         int exitAlt = this.dfa.getNumberOfAlts();
         if (nondeterministicAlts.contains(Utils.integer(exitAlt))) {
            winningAlt = this.resolveByPickingExitAlt(d, nondeterministicAlts);
         } else {
            winningAlt = this.resolveByPickingMinAlt(d, nondeterministicAlts);
         }
      }

      return winningAlt;
   }

   protected int resolveByPickingMinAlt(DFAState d, Set nondeterministicAlts) {
      int min;
      if (nondeterministicAlts != null) {
         min = getMinAlt(nondeterministicAlts);
      } else {
         min = d.minAltInConfigurations;
      }

      turnOffOtherAlts(d, min, nondeterministicAlts);
      return min;
   }

   protected int resolveByPickingExitAlt(DFAState d, Set nondeterministicAlts) {
      int exitAlt = this.dfa.getNumberOfAlts();
      turnOffOtherAlts(d, exitAlt, nondeterministicAlts);
      return exitAlt;
   }

   protected static void turnOffOtherAlts(DFAState d, int min, Set nondeterministicAlts) {
      int numConfigs = d.nfaConfigurations.size();

      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration configuration = (NFAConfiguration)d.nfaConfigurations.get(i);
         if (configuration.alt != min && (nondeterministicAlts == null || nondeterministicAlts.contains(Utils.integer(configuration.alt)))) {
            configuration.resolved = true;
         }
      }

   }

   protected static int getMinAlt(Set nondeterministicAlts) {
      int min = Integer.MAX_VALUE;
      Iterator i$ = nondeterministicAlts.iterator();

      while(i$.hasNext()) {
         Integer altI = (Integer)i$.next();
         int alt = altI;
         if (alt < min) {
            min = alt;
         }
      }

      return min;
   }

   protected boolean tryToResolveWithSemanticPredicates(DFAState d, Set nondeterministicAlts) {
      Map altToPredMap = this.getPredicatesPerNonDeterministicAlt(d, nondeterministicAlts);
      if (altToPredMap.isEmpty()) {
         return false;
      } else {
         this.dfa.probe.reportAltPredicateContext(d, altToPredMap);
         if (nondeterministicAlts.size() - altToPredMap.size() > 1) {
            return false;
         } else {
            if (altToPredMap.size() == nondeterministicAlts.size() - 1) {
               BitSet ndSet = BitSet.of((Collection)nondeterministicAlts);
               BitSet predSet = BitSet.of(altToPredMap);
               int nakedAlt = ndSet.subtract(predSet).getSingleElement();
               Object nakedAltPred;
               if (nakedAlt == max(nondeterministicAlts)) {
                  nakedAltPred = new SemanticContext.TruePredicate();
               } else {
                  SemanticContext unionOfPredicatesFromAllAlts = getUnionOfPredicates(altToPredMap);
                  if (unionOfPredicatesFromAllAlts.isSyntacticPredicate()) {
                     nakedAltPred = new SemanticContext.TruePredicate();
                  } else {
                     nakedAltPred = SemanticContext.not(unionOfPredicatesFromAllAlts);
                  }
               }

               altToPredMap.put(Utils.integer(nakedAlt), nakedAltPred);
               int numConfigs = d.nfaConfigurations.size();

               for(int i = 0; i < numConfigs; ++i) {
                  NFAConfiguration configuration = (NFAConfiguration)d.nfaConfigurations.get(i);
                  if (configuration.alt == nakedAlt) {
                     configuration.semanticContext = (SemanticContext)nakedAltPred;
                  }
               }
            }

            if (altToPredMap.size() == nondeterministicAlts.size()) {
               if (d.abortedDueToRecursionOverflow) {
                  d.dfa.probe.removeRecursiveOverflowState(d);
               }

               int numConfigs = d.nfaConfigurations.size();

               for(int i = 0; i < numConfigs; ++i) {
                  NFAConfiguration configuration = (NFAConfiguration)d.nfaConfigurations.get(i);
                  SemanticContext semCtx = (SemanticContext)altToPredMap.get(Utils.integer(configuration.alt));
                  if (semCtx != null) {
                     configuration.resolveWithPredicate = true;
                     configuration.semanticContext = semCtx;
                     altToPredMap.remove(Utils.integer(configuration.alt));
                     if (semCtx.isSyntacticPredicate()) {
                        this.dfa.nfa.grammar.synPredUsedInDFA(this.dfa, semCtx);
                     }
                  } else if (nondeterministicAlts.contains(Utils.integer(configuration.alt))) {
                     configuration.resolved = true;
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   protected Map getPredicatesPerNonDeterministicAlt(DFAState d, Set nondeterministicAlts) {
      Map altToPredicateContextMap = new HashMap();
      Map altToSetOfContextsMap = new HashMap();
      Iterator i$ = nondeterministicAlts.iterator();

      while(i$.hasNext()) {
         Integer altI = (Integer)i$.next();
         altToSetOfContextsMap.put(altI, new OrderedHashSet());
      }

      Map altToLocationsReachableWithoutPredicate = new HashMap();
      Set nondetAltsWithUncoveredConfiguration = new HashSet();
      int numConfigs = d.nfaConfigurations.size();

      Integer altI;
      Set contextsForThisAlt;
      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration configuration = (NFAConfiguration)d.nfaConfigurations.get(i);
         altI = Utils.integer(configuration.alt);
         if (nondeterministicAlts.contains(altI)) {
            if (configuration.semanticContext != SemanticContext.EMPTY_SEMANTIC_CONTEXT) {
               contextsForThisAlt = (Set)altToSetOfContextsMap.get(altI);
               contextsForThisAlt.add(configuration.semanticContext);
            } else {
               nondetAltsWithUncoveredConfiguration.add(altI);
            }
         }
      }

      List incompletelyCoveredAlts = new ArrayList();
      Iterator i$ = nondeterministicAlts.iterator();

      while(true) {
         while(i$.hasNext()) {
            altI = (Integer)i$.next();
            contextsForThisAlt = (Set)altToSetOfContextsMap.get(altI);
            if (nondetAltsWithUncoveredConfiguration.contains(altI)) {
               if (contextsForThisAlt.size() > 0) {
                  incompletelyCoveredAlts.add(altI);
               }
            } else {
               SemanticContext combinedContext = null;

               SemanticContext ctx;
               for(Iterator i$ = contextsForThisAlt.iterator(); i$.hasNext(); combinedContext = SemanticContext.or(combinedContext, ctx)) {
                  ctx = (SemanticContext)i$.next();
               }

               altToPredicateContextMap.put(altI, combinedContext);
            }
         }

         if (incompletelyCoveredAlts.size() > 0) {
            for(int i = 0; i < numConfigs; ++i) {
               NFAConfiguration configuration = (NFAConfiguration)d.nfaConfigurations.get(i);
               Integer altI = Utils.integer(configuration.alt);
               if (incompletelyCoveredAlts.contains(altI) && configuration.semanticContext == SemanticContext.EMPTY_SEMANTIC_CONTEXT) {
                  NFAState s = this.dfa.nfa.getState(configuration.state);
                  if (s.incidentEdgeLabel != null && s.incidentEdgeLabel.label != -1) {
                     if (s.associatedASTNode != null && s.associatedASTNode.token != null) {
                        Set locations = (Set)altToLocationsReachableWithoutPredicate.get(altI);
                        if (locations == null) {
                           locations = new HashSet();
                           altToLocationsReachableWithoutPredicate.put(altI, locations);
                        }

                        ((Set)locations).add(s.associatedASTNode.token);
                     } else {
                        ErrorManager.internalError("no AST/token for nonepsilon target w/o predicate");
                     }
                  }
               }
            }

            this.dfa.probe.reportIncompletelyCoveredAlts(d, altToLocationsReachableWithoutPredicate);
         }

         return altToPredicateContextMap;
      }
   }

   protected static SemanticContext getUnionOfPredicates(Map altToPredMap) {
      SemanticContext unionOfPredicatesFromAllAlts = null;
      Iterator iter = altToPredMap.values().iterator();

      while(iter.hasNext()) {
         SemanticContext semCtx = (SemanticContext)iter.next();
         if (unionOfPredicatesFromAllAlts == null) {
            unionOfPredicatesFromAllAlts = semCtx;
         } else {
            unionOfPredicatesFromAllAlts = SemanticContext.or(unionOfPredicatesFromAllAlts, semCtx);
         }
      }

      return unionOfPredicatesFromAllAlts;
   }

   protected void addPredicateTransitions(DFAState d) {
      List configsWithPreds = new ArrayList();
      int numConfigs = d.nfaConfigurations.size();

      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration c = (NFAConfiguration)d.nfaConfigurations.get(i);
         if (c.resolveWithPredicate) {
            configsWithPreds.add(c);
         }
      }

      Collections.sort(configsWithPreds, new Comparator() {
         public int compare(NFAConfiguration a, NFAConfiguration b) {
            if (a.alt < b.alt) {
               return -1;
            } else {
               return a.alt > b.alt ? 1 : 0;
            }
         }
      });
      List predConfigsSortedByAlt = configsWithPreds;

      for(int i = 0; i < predConfigsSortedByAlt.size(); ++i) {
         NFAConfiguration c = (NFAConfiguration)predConfigsSortedByAlt.get(i);
         DFAState predDFATarget = d.dfa.getAcceptState(c.alt);
         if (predDFATarget == null) {
            predDFATarget = this.dfa.newState();
            predDFATarget.addNFAConfiguration(this.dfa.nfa.getState(c.state), c.alt, c.context, c.semanticContext);
            predDFATarget.setAcceptState(true);
            this.dfa.setAcceptState(c.alt, predDFATarget);
            DFAState existingState = this.dfa.addState(predDFATarget);
            if (predDFATarget != existingState) {
               this.dfa.setState(predDFATarget.stateNumber, existingState);
               predDFATarget = existingState;
            }
         }

         d.addTransition(predDFATarget, new PredicateLabel(c.semanticContext));
      }

   }

   protected void initContextTrees(int numberOfAlts) {
      this.contextTrees = new NFAContext[numberOfAlts];

      for(int i = 0; i < this.contextTrees.length; ++i) {
         int alt = i + 1;
         this.contextTrees[i] = new NFAContext((NFAContext)null, (NFAState)null);
      }

   }

   public static int max(Set s) {
      if (s == null) {
         return Integer.MIN_VALUE;
      } else {
         int i = 0;
         int m = 0;
         Iterator i$ = s.iterator();

         while(i$.hasNext()) {
            Integer value = (Integer)i$.next();
            ++i;
            if (i == 1) {
               m = value;
            } else if (value > m) {
               m = value;
            }
         }

         return m;
      }
   }
}
