package org.antlr.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import org.antlr.codegen.CodeGenerator;
import org.antlr.misc.IntSet;
import org.antlr.misc.IntervalSet;
import org.antlr.misc.Utils;
import org.antlr.runtime.IntStream;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.FASerializer;
import org.antlr.tool.GrammarAST;
import org.antlr.tool.Interpreter;
import org.antlr.tool.Rule;
import org.stringtemplate.v4.ST;

public class DFA {
   public static final int REACHABLE_UNKNOWN = -2;
   public static final int REACHABLE_BUSY = -1;
   public static final int REACHABLE_NO = 0;
   public static final int REACHABLE_YES = 1;
   public static final int CYCLIC_UNKNOWN = -2;
   public static final int CYCLIC_BUSY = -1;
   public static final int CYCLIC_DONE = 0;
   public static int MAX_TIME_PER_DFA_CREATION = 1000;
   public static int MAX_STATE_TRANSITIONS_FOR_TABLE = 65534;
   public DFAState startState;
   public int decisionNumber = 0;
   public NFAState decisionNFAStartState;
   public String description;
   protected Map uniqueStates = new HashMap();
   protected Vector states = new Vector();
   protected int stateCounter = 0;
   protected int numberOfStates = 0;
   protected int user_k = -1;
   protected int max_k = -1;
   protected boolean reduced = true;
   protected boolean cyclic = false;
   public boolean predicateVisible = false;
   public boolean hasPredicateBlockedByAction = false;
   protected List unreachableAlts;
   protected int nAlts = 0;
   protected DFAState[] altToAcceptState;
   public IntSet recursiveAltSet = new IntervalSet();
   public NFA nfa;
   protected NFAToDFAConverter nfaConverter;
   public DecisionProbe probe = new DecisionProbe(this);
   public Map edgeTransitionClassMap = new LinkedHashMap();
   protected int edgeTransitionClass = 0;
   public List specialStates;
   public List specialStateSTs;
   public Vector accept;
   public Vector eot;
   public Vector eof;
   public Vector min;
   public Vector max;
   public Vector special;
   public Vector transition;
   public Vector transitionEdgeTables;
   protected int uniqueCompressedSpecialStateNum = 0;
   protected CodeGenerator generator = null;

   protected DFA() {
   }

   public DFA(int decisionNumber, NFAState decisionStartState) {
      this.decisionNumber = decisionNumber;
      this.decisionNFAStartState = decisionStartState;
      this.nfa = decisionStartState.nfa;
      this.nAlts = this.nfa.grammar.getNumberOfAltsForDecisionNFA(decisionStartState);
      this.initAltRelatedInfo();
      this.nfaConverter = new NFAToDFAConverter(this);

      try {
         this.nfaConverter.convert();
         this.verify();
         if (!this.probe.isDeterministic() || this.probe.analysisOverflowed()) {
            this.probe.issueWarnings();
         }

         this.resetStateNumbersToBeContiguous();
      } catch (NonLLStarDecisionException var4) {
         this.probe.reportNonLLStarDecision(this);
         if (!this.okToRetryDFAWithK1()) {
            this.probe.issueWarnings();
         }
      }

   }

   public void resetStateNumbersToBeContiguous() {
      if (this.getUserMaxLookahead() <= 0) {
         int snum = 0;

         for(int i = 0; i <= this.getMaxStateNumber(); ++i) {
            DFAState s = this.getState(i);
            if (s != null) {
               boolean alreadyRenumbered = s.stateNumber < i;
               if (!alreadyRenumbered) {
                  s.stateNumber = snum++;
               }
            }
         }

         if (snum != this.getNumberOfStates()) {
            ErrorManager.internalError("DFA " + this.decisionNumber + ": " + this.decisionNFAStartState.getDescription() + " num unique states " + this.getNumberOfStates() + "!= num renumbered states " + snum);
         }

      }
   }

   public List getJavaCompressedAccept() {
      return this.getRunLengthEncoding(this.accept);
   }

   public List getJavaCompressedEOT() {
      return this.getRunLengthEncoding(this.eot);
   }

   public List getJavaCompressedEOF() {
      return this.getRunLengthEncoding(this.eof);
   }

   public List getJavaCompressedMin() {
      return this.getRunLengthEncoding(this.min);
   }

   public List getJavaCompressedMax() {
      return this.getRunLengthEncoding(this.max);
   }

   public List getJavaCompressedSpecial() {
      return this.getRunLengthEncoding(this.special);
   }

   public List getJavaCompressedTransition() {
      if (this.transition != null && !this.transition.isEmpty()) {
         List encoded = new ArrayList(this.transition.size());

         for(int i = 0; i < this.transition.size(); ++i) {
            Vector transitionsForState = (Vector)this.transition.elementAt(i);
            encoded.add(this.getRunLengthEncoding(transitionsForState));
         }

         return encoded;
      } else {
         return null;
      }
   }

   public List getRunLengthEncoding(List data) {
      if (data != null && !data.isEmpty()) {
         int size = Math.max(2, data.size() / 2);
         List encoded = new ArrayList(size);
         int i = 0;

         int n;
         for(Integer emptyValue = Utils.integer(-1); i < data.size(); i += n) {
            Integer I = (Integer)data.get(i);
            if (I == null) {
               I = emptyValue;
            }

            n = 0;

            for(int j = i; j < data.size(); ++j) {
               Integer v = (Integer)data.get(j);
               if (v == null) {
                  v = emptyValue;
               }

               if (!I.equals(v)) {
                  break;
               }

               ++n;
            }

            encoded.add(this.generator.target.encodeIntAsCharEscape((char)n));
            encoded.add(this.generator.target.encodeIntAsCharEscape((char)I));
         }

         return encoded;
      } else {
         List empty = new ArrayList();
         empty.add("");
         return empty;
      }
   }

   public void createStateTables(CodeGenerator generator) {
      this.generator = generator;
      this.description = this.getNFADecisionStartState().getDescription();
      this.description = generator.target.getTargetStringLiteralFromString(this.description);
      this.special = new Vector(this.getNumberOfStates());
      this.special.setSize(this.getNumberOfStates());
      this.specialStates = new ArrayList();
      this.specialStateSTs = new ArrayList();
      this.accept = new Vector(this.getNumberOfStates());
      this.accept.setSize(this.getNumberOfStates());
      this.eot = new Vector(this.getNumberOfStates());
      this.eot.setSize(this.getNumberOfStates());
      this.eof = new Vector(this.getNumberOfStates());
      this.eof.setSize(this.getNumberOfStates());
      this.min = new Vector(this.getNumberOfStates());
      this.min.setSize(this.getNumberOfStates());
      this.max = new Vector(this.getNumberOfStates());
      this.max.setSize(this.getNumberOfStates());
      this.transition = new Vector(this.getNumberOfStates());
      this.transition.setSize(this.getNumberOfStates());
      this.transitionEdgeTables = new Vector(this.getNumberOfStates());
      this.transitionEdgeTables.setSize(this.getNumberOfStates());
      Iterator it;
      if (this.getUserMaxLookahead() > 0) {
         it = this.states.iterator();
      } else {
         it = this.getUniqueStates().values().iterator();
      }

      while(it.hasNext()) {
         DFAState s = (DFAState)it.next();
         if (s != null) {
            if (s.isAcceptState()) {
               this.accept.set(s.stateNumber, Utils.integer(s.getUniquelyPredictedAlt()));
            } else {
               this.createMinMaxTables(s);
               this.createTransitionTableEntryForState(s);
               this.createSpecialTable(s);
               this.createEOTAndEOFTables(s);
            }
         }
      }

      for(int i = 0; i < this.specialStates.size(); ++i) {
         DFAState ss = (DFAState)this.specialStates.get(i);
         ST stateST = generator.generateSpecialState(ss);
         this.specialStateSTs.add(stateST);
      }

   }

   protected void createMinMaxTables(DFAState s) {
      int smin = 65536;
      int smax = -3;

      for(int j = 0; j < s.getNumberOfTransitions(); ++j) {
         Transition edge = s.transition(j);
         Label label = edge.label;
         if (label.isAtom()) {
            if (label.getAtom() >= 0) {
               if (label.getAtom() < smin) {
                  smin = label.getAtom();
               }

               if (label.getAtom() > smax) {
                  smax = label.getAtom();
               }
            }
         } else if (label.isSet()) {
            IntervalSet labels = (IntervalSet)label.getSet();
            int lmin = labels.getMinElement();
            if (lmin < smin && lmin >= 0) {
               smin = labels.getMinElement();
            }

            if (labels.getMaxElement() > smax) {
               smax = labels.getMaxElement();
            }
         }
      }

      if (smax < 0) {
         smin = 0;
         smax = 0;
      }

      this.min.set(s.stateNumber, Utils.integer((char)smin));
      this.max.set(s.stateNumber, Utils.integer((char)smax));
      if (smax < 0 || smin > 65535 || smin < 0) {
         ErrorManager.internalError("messed up: min=" + this.min + ", max=" + this.max);
      }

   }

   protected void createTransitionTableEntryForState(DFAState s) {
      int smax = (Integer)this.max.get(s.stateNumber);
      int smin = (Integer)this.min.get(s.stateNumber);
      Vector stateTransitions = new Vector(smax - smin + 1);
      stateTransitions.setSize(smax - smin + 1);
      this.transition.set(s.stateNumber, stateTransitions);

      for(int j = 0; j < s.getNumberOfTransitions(); ++j) {
         Transition edge = s.transition(j);
         Label label = edge.label;
         if (label.isAtom() && label.getAtom() >= 0) {
            int labelIndex = label.getAtom() - smin;
            stateTransitions.set(labelIndex, Utils.integer(edge.target.stateNumber));
         } else if (label.isSet()) {
            IntervalSet labels = (IntervalSet)label.getSet();
            int[] atoms = labels.toArray();

            for(int a = 0; a < atoms.length; ++a) {
               if (atoms[a] >= 0) {
                  int labelIndex = atoms[a] - smin;
                  stateTransitions.set(labelIndex, Utils.integer(edge.target.stateNumber));
               }
            }
         }
      }

      Integer edgeClass = (Integer)this.edgeTransitionClassMap.get(stateTransitions);
      if (edgeClass != null) {
         this.transitionEdgeTables.set(s.stateNumber, edgeClass);
      } else {
         edgeClass = Utils.integer(this.edgeTransitionClass);
         this.transitionEdgeTables.set(s.stateNumber, edgeClass);
         this.edgeTransitionClassMap.put(stateTransitions, edgeClass);
         ++this.edgeTransitionClass;
      }

   }

   protected void createEOTAndEOFTables(DFAState s) {
      for(int j = 0; j < s.getNumberOfTransitions(); ++j) {
         Transition edge = s.transition(j);
         Label label = edge.label;
         if (label.isAtom()) {
            if (label.getAtom() == -2) {
               this.eot.set(s.stateNumber, Utils.integer(edge.target.stateNumber));
            } else if (label.getAtom() == -1) {
               this.eof.set(s.stateNumber, Utils.integer(edge.target.stateNumber));
            }
         } else if (label.isSet()) {
            IntervalSet labels = (IntervalSet)label.getSet();
            int[] atoms = labels.toArray();

            for(int a = 0; a < atoms.length; ++a) {
               if (atoms[a] == -2) {
                  this.eot.set(s.stateNumber, Utils.integer(edge.target.stateNumber));
               } else if (atoms[a] == -1) {
                  this.eof.set(s.stateNumber, Utils.integer(edge.target.stateNumber));
               }
            }
         }
      }

   }

   protected void createSpecialTable(DFAState s) {
      boolean hasSemPred = false;

      int smax;
      for(smax = 0; smax < s.getNumberOfTransitions(); ++smax) {
         Transition edge = s.transition(smax);
         Label label = edge.label;
         if (label.isSemanticPredicate() || ((DFAState)edge.target).getGatedPredicatesInNFAConfigurations() != null) {
            hasSemPred = true;
            break;
         }
      }

      smax = (Integer)this.max.get(s.stateNumber);
      int smin = (Integer)this.min.get(s.stateNumber);
      if (!hasSemPred && smax - smin <= MAX_STATE_TRANSITIONS_FOR_TABLE) {
         this.special.set(s.stateNumber, Utils.integer(-1));
      } else {
         this.special.set(s.stateNumber, Utils.integer(this.uniqueCompressedSpecialStateNum));
         ++this.uniqueCompressedSpecialStateNum;
         this.specialStates.add(s);
      }

   }

   public int predict(IntStream input) {
      Interpreter interp = new Interpreter(this.nfa.grammar, input);
      return interp.predict(this);
   }

   protected DFAState addState(DFAState d) {
      if (this.getUserMaxLookahead() > 0) {
         return d;
      } else {
         DFAState existing = (DFAState)this.uniqueStates.get(d);
         if (existing != null) {
            return existing;
         } else {
            this.uniqueStates.put(d, d);
            ++this.numberOfStates;
            return d;
         }
      }
   }

   public void removeState(DFAState d) {
      DFAState it = (DFAState)this.uniqueStates.remove(d);
      if (it != null) {
         --this.numberOfStates;
      }

   }

   public Map getUniqueStates() {
      return this.uniqueStates;
   }

   public int getMaxStateNumber() {
      return this.states.size() - 1;
   }

   public DFAState getState(int stateNumber) {
      return (DFAState)this.states.get(stateNumber);
   }

   public void setState(int stateNumber, DFAState d) {
      this.states.set(stateNumber, d);
   }

   public boolean isReduced() {
      return this.reduced;
   }

   public boolean isCyclic() {
      return this.cyclic && this.getUserMaxLookahead() == 0;
   }

   public boolean isClassicDFA() {
      return !this.isCyclic() && !this.nfa.grammar.decisionsWhoseDFAsUsesSemPreds.contains(this) && !this.nfa.grammar.decisionsWhoseDFAsUsesSynPreds.contains(this);
   }

   public boolean canInlineDecision() {
      return !this.isCyclic() && !this.probe.isNonLLStarDecision() && this.getNumberOfStates() < CodeGenerator.MAX_ACYCLIC_DFA_STATES_INLINE;
   }

   public boolean isTokensRuleDecision() {
      if (this.nfa.grammar.type != 1) {
         return false;
      } else {
         NFAState nfaStart = this.getNFADecisionStartState();
         Rule r = this.nfa.grammar.getLocallyDefinedRule("Tokens");
         NFAState TokensRuleStart = r.startState;
         NFAState TokensDecisionStart = (NFAState)TokensRuleStart.transition[0].target;
         return nfaStart == TokensDecisionStart;
      }
   }

   public int getUserMaxLookahead() {
      if (this.user_k >= 0) {
         return this.user_k;
      } else {
         this.user_k = this.nfa.grammar.getUserMaxLookahead(this.decisionNumber);
         return this.user_k;
      }
   }

   public boolean getAutoBacktrackMode() {
      return this.nfa.grammar.getAutoBacktrackMode(this.decisionNumber);
   }

   public void setUserMaxLookahead(int k) {
      this.user_k = k;
   }

   public int getMaxLookaheadDepth() {
      return this.hasCycle() ? Integer.MAX_VALUE : this._getMaxLookaheadDepth(this.startState, 0);
   }

   int _getMaxLookaheadDepth(DFAState d, int depth) {
      int max = depth;

      for(int i = 0; i < d.getNumberOfTransitions(); ++i) {
         Transition t = d.transition(i);
         if (!t.isSemanticPredicate()) {
            DFAState edgeTarget = (DFAState)t.target;
            int m = this._getMaxLookaheadDepth(edgeTarget, depth + 1);
            max = Math.max(max, m);
         }
      }

      return max;
   }

   public boolean hasSynPred() {
      boolean has = this._hasSynPred(this.startState, new HashSet());
      return has;
   }

   public boolean getHasSynPred() {
      return this.hasSynPred();
   }

   boolean _hasSynPred(DFAState d, Set busy) {
      busy.add(d);

      for(int i = 0; i < d.getNumberOfTransitions(); ++i) {
         Transition t = d.transition(i);
         if (t.isSemanticPredicate()) {
            SemanticContext ctx = t.label.getSemanticContext();
            if (ctx.isSyntacticPredicate()) {
               return true;
            }
         }

         DFAState edgeTarget = (DFAState)t.target;
         if (!busy.contains(edgeTarget) && this._hasSynPred(edgeTarget, busy)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasSemPred() {
      boolean has = this._hasSemPred(this.startState, new HashSet());
      return has;
   }

   boolean _hasSemPred(DFAState d, Set busy) {
      busy.add(d);

      for(int i = 0; i < d.getNumberOfTransitions(); ++i) {
         Transition t = d.transition(i);
         if (t.isSemanticPredicate()) {
            SemanticContext ctx = t.label.getSemanticContext();
            if (ctx.hasUserSemanticPredicate()) {
               return true;
            }
         }

         DFAState edgeTarget = (DFAState)t.target;
         if (!busy.contains(edgeTarget) && this._hasSemPred(edgeTarget, busy)) {
            return true;
         }
      }

      return false;
   }

   public boolean hasCycle() {
      boolean cyclic = this._hasCycle(this.startState, new HashMap());
      return cyclic;
   }

   boolean _hasCycle(DFAState d, Map busy) {
      busy.put(d, -1);

      for(int i = 0; i < d.getNumberOfTransitions(); ++i) {
         Transition t = d.transition(i);
         DFAState target = (DFAState)t.target;
         int cond = -2;
         if (busy.get(target) != null) {
            cond = (Integer)busy.get(target);
         }

         if (cond == -1) {
            return true;
         }

         if (cond != 0 && this._hasCycle(target, busy)) {
            return true;
         }
      }

      busy.put(d, 0);
      return false;
   }

   public List getUnreachableAlts() {
      return this.unreachableAlts;
   }

   public void verify() {
      this.doesStateReachAcceptState(this.startState);
   }

   protected boolean doesStateReachAcceptState(DFAState d) {
      if (d.isAcceptState()) {
         d.setAcceptStateReachable(1);
         int predicts = d.getUniquelyPredictedAlt();
         this.unreachableAlts.remove(Utils.integer(predicts));
         return true;
      } else {
         d.setAcceptStateReachable(-1);
         boolean anEdgeReachesAcceptState = false;

         for(int i = 0; i < d.getNumberOfTransitions(); ++i) {
            Transition t = d.transition(i);
            DFAState edgeTarget = (DFAState)t.target;
            int targetStatus = edgeTarget.getAcceptStateReachable();
            if (targetStatus == -1) {
               this.cyclic = true;
            } else if (targetStatus == 1) {
               anEdgeReachesAcceptState = true;
            } else if (targetStatus != 0 && this.doesStateReachAcceptState(edgeTarget)) {
               anEdgeReachesAcceptState = true;
            }
         }

         if (anEdgeReachesAcceptState) {
            d.setAcceptStateReachable(1);
         } else {
            d.setAcceptStateReachable(0);
            this.reduced = false;
         }

         return anEdgeReachesAcceptState;
      }
   }

   public void findAllGatedSynPredsUsedInDFAAcceptStates() {
      int nAlts = this.getNumberOfAlts();

      for(int i = 1; i <= nAlts; ++i) {
         DFAState a = this.getAcceptState(i);
         if (a != null) {
            Set synpreds = a.getGatedSyntacticPredicatesInNFAConfigurations();
            if (synpreds != null) {
               Iterator i$ = synpreds.iterator();

               while(i$.hasNext()) {
                  SemanticContext semctx = (SemanticContext)i$.next();
                  this.nfa.grammar.synPredUsedInDFA(this, semctx);
               }
            }
         }
      }

   }

   public NFAState getNFADecisionStartState() {
      return this.decisionNFAStartState;
   }

   public DFAState getAcceptState(int alt) {
      return this.altToAcceptState[alt];
   }

   public void setAcceptState(int alt, DFAState acceptState) {
      this.altToAcceptState[alt] = acceptState;
   }

   public String getDescription() {
      return this.description;
   }

   public int getDecisionNumber() {
      return this.decisionNFAStartState.getDecisionNumber();
   }

   public boolean okToRetryDFAWithK1() {
      boolean nonLLStarOrOverflowAndPredicateVisible = (this.probe.isNonLLStarDecision() || this.probe.analysisOverflowed()) && this.predicateVisible;
      return this.getUserMaxLookahead() != 1 && nonLLStarOrOverflowAndPredicateVisible;
   }

   public String getReasonForFailure() {
      StringBuilder buf = new StringBuilder();
      if (this.probe.isNonLLStarDecision()) {
         buf.append("non-LL(*)");
         if (this.predicateVisible) {
            buf.append(" && predicate visible");
         }
      }

      if (this.probe.analysisOverflowed()) {
         buf.append("recursion overflow");
         if (this.predicateVisible) {
            buf.append(" && predicate visible");
         }
      }

      buf.append("\n");
      return buf.toString();
   }

   public GrammarAST getDecisionASTNode() {
      return this.decisionNFAStartState.associatedASTNode;
   }

   public boolean isGreedy() {
      GrammarAST blockAST = this.nfa.grammar.getDecisionBlockAST(this.decisionNumber);
      Object v = this.nfa.grammar.getBlockOption(blockAST, "greedy");
      return v == null || !v.equals("false");
   }

   public DFAState newState() {
      DFAState n = new DFAState(this);
      n.stateNumber = this.stateCounter++;
      this.states.setSize(n.stateNumber + 1);
      this.states.set(n.stateNumber, n);
      return n;
   }

   public int getNumberOfStates() {
      return this.getUserMaxLookahead() > 0 ? this.states.size() : this.numberOfStates;
   }

   public int getNumberOfAlts() {
      return this.nAlts;
   }

   protected void initAltRelatedInfo() {
      this.unreachableAlts = new LinkedList();

      for(int i = 1; i <= this.nAlts; ++i) {
         this.unreachableAlts.add(Utils.integer(i));
      }

      this.altToAcceptState = new DFAState[this.nAlts + 1];
   }

   public String toString() {
      FASerializer serializer = new FASerializer(this.nfa.grammar);
      return this.startState == null ? "" : serializer.serialize(this.startState, false);
   }
}
