package org.antlr.analysis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.antlr.misc.IntSet;
import org.antlr.misc.MultiMap;
import org.antlr.misc.OrderedHashSet;
import org.antlr.misc.Utils;

public class DFAState extends State {
   public static final int INITIAL_NUM_TRANSITIONS = 4;
   public static final int PREDICTED_ALT_UNSET = -2;
   public DFA dfa;
   protected List transitions = new ArrayList(4);
   protected int k;
   protected int acceptStateReachable = -2;
   protected boolean resolvedWithPredicates = false;
   public boolean abortedDueToRecursionOverflow = false;
   protected boolean abortedDueToMultipleRecursiveAlts = false;
   protected int cachedHashCode;
   protected int cachedUniquelyPredicatedAlt = -2;
   public int minAltInConfigurations = Integer.MAX_VALUE;
   public boolean atLeastOneConfigurationHasAPredicate = false;
   public OrderedHashSet nfaConfigurations = new OrderedHashSet();
   public List configurationsWithLabeledEdges = new ArrayList();
   protected Set closureBusy = new HashSet();
   protected OrderedHashSet reachableLabels;

   public DFAState(DFA dfa) {
      this.dfa = dfa;
   }

   public void reset() {
      this.configurationsWithLabeledEdges = null;
      this.closureBusy = null;
      this.reachableLabels = null;
   }

   public Transition transition(int i) {
      return (Transition)this.transitions.get(i);
   }

   public int getNumberOfTransitions() {
      return this.transitions.size();
   }

   public void addTransition(Transition t) {
      this.transitions.add(t);
   }

   public int addTransition(DFAState target, Label label) {
      this.transitions.add(new Transition(label, target));
      return this.transitions.size() - 1;
   }

   public Transition getTransition(int trans) {
      return (Transition)this.transitions.get(trans);
   }

   public void removeTransition(int trans) {
      this.transitions.remove(trans);
   }

   public void addNFAConfiguration(NFAState state, NFAConfiguration c) {
      if (!this.nfaConfigurations.contains(c)) {
         this.nfaConfigurations.add(c);
         if (c.alt < this.minAltInConfigurations) {
            this.minAltInConfigurations = c.alt;
         }

         if (c.semanticContext != SemanticContext.EMPTY_SEMANTIC_CONTEXT) {
            this.atLeastOneConfigurationHasAPredicate = true;
         }

         this.cachedHashCode += c.state + c.alt;
         if (state.transition[0] != null) {
            Label label = state.transition[0].label;
            if (!label.isEpsilon() && !label.isSemanticPredicate()) {
               this.configurationsWithLabeledEdges.add(c);
               if (state.transition[1] == null) {
                  c.singleAtomTransitionEmanating = true;
               }

               this.addReachableLabel(label);
            }
         }

      }
   }

   public NFAConfiguration addNFAConfiguration(NFAState state, int alt, NFAContext context, SemanticContext semanticContext) {
      NFAConfiguration c = new NFAConfiguration(state.stateNumber, alt, context, semanticContext);
      this.addNFAConfiguration(state, c);
      return c;
   }

   protected void addReachableLabel(Label label) {
      if (this.reachableLabels == null) {
         this.reachableLabels = new OrderedHashSet();
      }

      if (!this.reachableLabels.contains(label)) {
         IntSet t = label.getSet();
         IntSet remainder = t;
         int n = this.reachableLabels.size();

         for(int i = 0; i < n; ++i) {
            Label rl = (Label)this.reachableLabels.get(i);
            if (Label.intersect(label, rl)) {
               IntSet s_i = rl.getSet();
               IntSet intersection = s_i.and(t);
               this.reachableLabels.set(i, new Label(intersection));
               IntSet existingMinusNewElements = s_i.subtract(t);
               if (!existingMinusNewElements.isNil()) {
                  Label newLabel = new Label(existingMinusNewElements);
                  this.reachableLabels.add(newLabel);
               }

               remainder = t.subtract(s_i);
               if (remainder.isNil()) {
                  break;
               }

               t = remainder;
            }
         }

         if (!remainder.isNil()) {
            Label newLabel = new Label(remainder);
            this.reachableLabels.add(newLabel);
         }

      }
   }

   public OrderedHashSet getReachableLabels() {
      return this.reachableLabels;
   }

   public void setNFAConfigurations(OrderedHashSet configs) {
      this.nfaConfigurations = configs;
   }

   public int hashCode() {
      return this.cachedHashCode == 0 ? super.hashCode() : this.cachedHashCode;
   }

   public boolean equals(Object o) {
      DFAState other = (DFAState)o;
      return this.nfaConfigurations.equals(other.nfaConfigurations);
   }

   public int getUniquelyPredictedAlt() {
      if (this.cachedUniquelyPredicatedAlt != -2) {
         return this.cachedUniquelyPredicatedAlt;
      } else {
         int alt = -1;
         int numConfigs = this.nfaConfigurations.size();

         for(int i = 0; i < numConfigs; ++i) {
            NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
            if (!configuration.resolved) {
               if (alt == -1) {
                  alt = configuration.alt;
               } else if (configuration.alt != alt) {
                  return -1;
               }
            }
         }

         this.cachedUniquelyPredicatedAlt = alt;
         return alt;
      }
   }

   public int getUniqueAlt() {
      int alt = -1;
      int numConfigs = this.nfaConfigurations.size();

      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
         if (alt == -1) {
            alt = configuration.alt;
         } else if (configuration.alt != alt) {
            return -1;
         }
      }

      return alt;
   }

   public Set getDisabledAlternatives() {
      Set disabled = new LinkedHashSet();
      int numConfigs = this.nfaConfigurations.size();

      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
         if (configuration.resolved) {
            disabled.add(Utils.integer(configuration.alt));
         }
      }

      return disabled;
   }

   protected Set getNonDeterministicAlts() {
      int user_k = this.dfa.getUserMaxLookahead();
      if (user_k > 0 && user_k == this.k) {
         return this.getAltSet();
      } else {
         return !this.abortedDueToMultipleRecursiveAlts && !this.abortedDueToRecursionOverflow ? this.getConflictingAlts() : this.getAltSet();
      }
   }

   protected Set getConflictingAlts() {
      Set nondeterministicAlts = new HashSet();
      int numConfigs = this.nfaConfigurations.size();
      if (numConfigs <= 1) {
         return null;
      } else {
         MultiMap stateToConfigListMap = new MultiMap();

         for(int i = 0; i < numConfigs; ++i) {
            NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
            Integer stateI = Utils.integer(configuration.state);
            stateToConfigListMap.map(stateI, configuration);
         }

         Set states = stateToConfigListMap.keySet();
         int numPotentialConflicts = 0;
         Iterator i$ = states.iterator();

         Integer stateI;
         int i;
         int j;
         NFAConfiguration t;
         while(i$.hasNext()) {
            stateI = (Integer)i$.next();
            boolean thisStateHasPotentialProblem = false;
            List configsForState = (List)stateToConfigListMap.get(stateI);
            i = 0;
            int numConfigsForState = configsForState.size();

            for(j = 0; j < numConfigsForState && numConfigsForState > 1; ++j) {
               t = (NFAConfiguration)configsForState.get(j);
               if (i == 0) {
                  i = t.alt;
               } else if (t.alt != i && (this.dfa.nfa.grammar.type != 1 || !this.dfa.decisionNFAStartState.enclosingRule.name.equals("Tokens"))) {
                  ++numPotentialConflicts;
                  thisStateHasPotentialProblem = true;
               }
            }

            if (!thisStateHasPotentialProblem) {
               stateToConfigListMap.put(stateI, (Object)null);
            }
         }

         if (numPotentialConflicts == 0) {
            return null;
         } else {
            i$ = states.iterator();

            while(i$.hasNext()) {
               stateI = (Integer)i$.next();
               List configsForState = (List)stateToConfigListMap.get(stateI);
               int numConfigsForState = 0;
               if (configsForState != null) {
                  numConfigsForState = configsForState.size();
               }

               for(i = 0; i < numConfigsForState; ++i) {
                  NFAConfiguration s = (NFAConfiguration)configsForState.get(i);

                  for(j = i + 1; j < numConfigsForState; ++j) {
                     t = (NFAConfiguration)configsForState.get(j);
                     if (s.alt != t.alt && s.context.conflictsWith(t.context)) {
                        nondeterministicAlts.add(Utils.integer(s.alt));
                        nondeterministicAlts.add(Utils.integer(t.alt));
                     }
                  }
               }
            }

            if (nondeterministicAlts.isEmpty()) {
               return null;
            } else {
               return nondeterministicAlts;
            }
         }
      }
   }

   public Set getAltSet() {
      int numConfigs = this.nfaConfigurations.size();
      Set alts = new HashSet();

      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
         alts.add(Utils.integer(configuration.alt));
      }

      return alts.isEmpty() ? null : alts;
   }

   public Set getGatedSyntacticPredicatesInNFAConfigurations() {
      int numConfigs = this.nfaConfigurations.size();
      Set synpreds = new HashSet();

      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
         SemanticContext gatedPredExpr = configuration.semanticContext.getGatedPredicateContext();
         if (gatedPredExpr != null && configuration.semanticContext.isSyntacticPredicate()) {
            synpreds.add(configuration.semanticContext);
         }
      }

      if (synpreds.isEmpty()) {
         return null;
      } else {
         return synpreds;
      }
   }

   public SemanticContext getGatedPredicatesInNFAConfigurations() {
      SemanticContext unionOfPredicatesFromAllAlts = null;
      int numConfigs = this.nfaConfigurations.size();

      for(int i = 0; i < numConfigs; ++i) {
         NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
         SemanticContext gatedPredExpr = configuration.semanticContext.getGatedPredicateContext();
         if (gatedPredExpr == null) {
            return null;
         }

         if (this.acceptState || !configuration.semanticContext.isSyntacticPredicate()) {
            if (unionOfPredicatesFromAllAlts == null) {
               unionOfPredicatesFromAllAlts = gatedPredExpr;
            } else {
               unionOfPredicatesFromAllAlts = SemanticContext.or(unionOfPredicatesFromAllAlts, gatedPredExpr);
            }
         }
      }

      if (unionOfPredicatesFromAllAlts instanceof SemanticContext.TruePredicate) {
         return null;
      } else {
         return unionOfPredicatesFromAllAlts;
      }
   }

   public int getAcceptStateReachable() {
      return this.acceptStateReachable;
   }

   public void setAcceptStateReachable(int acceptStateReachable) {
      this.acceptStateReachable = acceptStateReachable;
   }

   public boolean isResolvedWithPredicates() {
      return this.resolvedWithPredicates;
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append(this.stateNumber).append(":{");

      for(int i = 0; i < this.nfaConfigurations.size(); ++i) {
         NFAConfiguration configuration = (NFAConfiguration)this.nfaConfigurations.get(i);
         if (i > 0) {
            buf.append(", ");
         }

         buf.append(configuration);
      }

      buf.append("}");
      return buf.toString();
   }

   public int getLookaheadDepth() {
      return this.k;
   }

   public void setLookaheadDepth(int k) {
      this.k = k;
      if (k > this.dfa.max_k) {
         this.dfa.max_k = k;
      }

   }
}
