package org.antlr.tool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.antlr.analysis.ActionLabel;
import org.antlr.analysis.Label;
import org.antlr.analysis.NFA;
import org.antlr.analysis.NFAState;
import org.antlr.analysis.PredicateLabel;
import org.antlr.analysis.RuleClosureTransition;
import org.antlr.analysis.State;
import org.antlr.analysis.StateCluster;
import org.antlr.analysis.Transition;
import org.antlr.misc.IntSet;
import org.antlr.misc.IntervalSet;

public class NFAFactory {
   NFA nfa = null;
   Rule currentRule = null;

   public Rule getCurrentRule() {
      return this.currentRule;
   }

   public void setCurrentRule(Rule currentRule) {
      this.currentRule = currentRule;
   }

   public NFAFactory(NFA nfa) {
      nfa.setFactory(this);
      this.nfa = nfa;
   }

   public NFAState newState() {
      NFAState n = new NFAState(this.nfa);
      int state = this.nfa.getNewNFAStateNumber();
      n.stateNumber = state;
      this.nfa.addState(n);
      n.enclosingRule = this.currentRule;
      return n;
   }

   public void optimizeAlternative(StateCluster alt) {
      NFAState s = alt.left;

      while(s != alt.right) {
         if (s.endOfBlockStateNumber != -1) {
            s = this.nfa.getState(s.endOfBlockStateNumber);
         } else {
            Transition t = s.transition[0];
            if (t instanceof RuleClosureTransition) {
               s = ((RuleClosureTransition)t).followState;
            } else {
               if (t.label.isEpsilon() && !t.label.isAction() && s.getNumberOfTransitions() == 1) {
                  NFAState epsilonTarget = (NFAState)t.target;
                  if (epsilonTarget.endOfBlockStateNumber == -1 && epsilonTarget.transition[0] != null) {
                     s.setTransition0(epsilonTarget.transition[0]);
                  }
               }

               s = (NFAState)t.target;
            }
         }
      }

   }

   public StateCluster build_Atom(int label, GrammarAST associatedAST) {
      NFAState left = this.newState();
      NFAState right = this.newState();
      left.associatedASTNode = associatedAST;
      right.associatedASTNode = associatedAST;
      this.transitionBetweenStates(left, right, label);
      StateCluster g = new StateCluster(left, right);
      return g;
   }

   public StateCluster build_Atom(GrammarAST atomAST) {
      int tokenType = this.nfa.grammar.getTokenType(atomAST.getText());
      return this.build_Atom(tokenType, atomAST);
   }

   public StateCluster build_Set(IntSet set, GrammarAST associatedAST) {
      NFAState left = this.newState();
      NFAState right = this.newState();
      left.associatedASTNode = associatedAST;
      right.associatedASTNode = associatedAST;
      Label label = new Label(set);
      Transition e = new Transition(label, right);
      left.addTransition(e);
      StateCluster g = new StateCluster(left, right);
      return g;
   }

   public StateCluster build_Range(int a, int b) {
      NFAState left = this.newState();
      NFAState right = this.newState();
      Label label = new Label(IntervalSet.of(a, b));
      Transition e = new Transition(label, right);
      left.addTransition(e);
      StateCluster g = new StateCluster(left, right);
      return g;
   }

   public StateCluster build_CharLiteralAtom(GrammarAST charLiteralAST) {
      int c = Grammar.getCharValueFromGrammarCharLiteral(charLiteralAST.getText());
      return this.build_Atom(c, charLiteralAST);
   }

   public StateCluster build_CharRange(String a, String b) {
      int from = Grammar.getCharValueFromGrammarCharLiteral(a);
      int to = Grammar.getCharValueFromGrammarCharLiteral(b);
      return this.build_Range(from, to);
   }

   public StateCluster build_StringLiteralAtom(GrammarAST stringLiteralAST) {
      if (this.nfa.grammar.type != 1) {
         int tokenType = this.nfa.grammar.getTokenType(stringLiteralAST.getText());
         return this.build_Atom(tokenType, stringLiteralAST);
      } else {
         StringBuffer chars = Grammar.getUnescapedStringFromGrammarStringLiteral(stringLiteralAST.getText());
         NFAState first = this.newState();
         NFAState last = null;
         NFAState prev = first;

         for(int i = 0; i < chars.length(); ++i) {
            int c = chars.charAt(i);
            NFAState next = this.newState();
            this.transitionBetweenStates(prev, next, c);
            last = next;
            prev = next;
         }

         return new StateCluster(first, last);
      }
   }

   public StateCluster build_RuleRef(Rule refDef, NFAState ruleStart) {
      NFAState left = this.newState();
      NFAState right = this.newState();
      Transition e = new RuleClosureTransition(refDef, ruleStart, right);
      left.addTransition(e);
      StateCluster g = new StateCluster(left, right);
      return g;
   }

   public StateCluster build_Epsilon() {
      NFAState left = this.newState();
      NFAState right = this.newState();
      this.transitionBetweenStates(left, right, -5);
      StateCluster g = new StateCluster(left, right);
      return g;
   }

   public StateCluster build_SemanticPredicate(GrammarAST pred) {
      if (!pred.getText().toUpperCase().startsWith("synpred".toUpperCase())) {
         ++this.nfa.grammar.numberOfSemanticPredicates;
      }

      NFAState left = this.newState();
      NFAState right = this.newState();
      Transition e = new Transition(new PredicateLabel(pred), right);
      left.addTransition(e);
      StateCluster g = new StateCluster(left, right);
      return g;
   }

   public StateCluster build_Action(GrammarAST action) {
      NFAState left = this.newState();
      NFAState right = this.newState();
      Transition e = new Transition(new ActionLabel(action), right);
      left.addTransition(e);
      return new StateCluster(left, right);
   }

   public int build_EOFStates(Collection rules) {
      int numberUnInvokedRules = 0;
      Iterator i$ = rules.iterator();

      while(i$.hasNext()) {
         Rule r = (Rule)i$.next();
         NFAState endNFAState = r.stopState;
         if (endNFAState.transition[0] == null) {
            this.build_EOFState(endNFAState);
            ++numberUnInvokedRules;
         }
      }

      return numberUnInvokedRules;
   }

   private void build_EOFState(NFAState endNFAState) {
      NFAState end = this.newState();
      int label = -1;
      if (this.nfa.grammar.type == 1) {
         label = -2;
         end.setEOTTargetState(true);
      }

      Transition toEnd = new Transition(label, end);
      endNFAState.addTransition(toEnd);
   }

   public StateCluster build_AB(StateCluster A, StateCluster B) {
      if (A == null) {
         return B;
      } else if (B == null) {
         return A;
      } else {
         this.transitionBetweenStates(A.right, B.left, -5);
         StateCluster g = new StateCluster(A.left, B.right);
         return g;
      }
   }

   public StateCluster build_AlternativeBlockFromSet(StateCluster set) {
      if (set == null) {
         return null;
      } else {
         NFAState startOfAlt = this.newState();
         this.transitionBetweenStates(startOfAlt, set.left, -5);
         return new StateCluster(startOfAlt, set.right);
      }
   }

   public StateCluster build_AlternativeBlock(List alternativeStateClusters) {
      if (alternativeStateClusters != null && !alternativeStateClusters.isEmpty()) {
         NFAState firstAlt;
         if (alternativeStateClusters.size() == 1) {
            StateCluster g = (StateCluster)alternativeStateClusters.get(0);
            firstAlt = this.newState();
            this.transitionBetweenStates(firstAlt, g.left, -5);
            return new StateCluster(firstAlt, g.right);
         } else {
            NFAState prevAlternative = null;
            firstAlt = null;
            NFAState blockEndNFAState = this.newState();
            blockEndNFAState.setDescription("end block");
            int altNum = 1;

            for(Iterator i$ = alternativeStateClusters.iterator(); i$.hasNext(); ++altNum) {
               StateCluster g = (StateCluster)i$.next();
               NFAState left = this.newState();
               left.setDescription("alt " + altNum + " of ()");
               this.transitionBetweenStates(left, g.left, -5);
               this.transitionBetweenStates(g.right, blockEndNFAState, -5);
               if (firstAlt == null) {
                  firstAlt = left;
               } else {
                  this.transitionBetweenStates(prevAlternative, left, -5);
               }

               prevAlternative = left;
            }

            StateCluster result = new StateCluster(firstAlt, blockEndNFAState);
            firstAlt.decisionStateType = 2;
            firstAlt.endOfBlockStateNumber = blockEndNFAState.stateNumber;
            return result;
         }
      } else {
         return null;
      }
   }

   public StateCluster build_Aoptional(StateCluster A) {
      int n = this.nfa.grammar.getNumberOfAltsForDecisionNFA(A.left);
      StateCluster g;
      NFAState decisionState;
      NFAState emptyAlt;
      if (n == 1) {
         decisionState = A.left;
         decisionState.setDescription("only alt of ()? block");
         emptyAlt = this.newState();
         emptyAlt.setDescription("epsilon path of ()? block");
         NFAState blockEndNFAState = this.newState();
         this.transitionBetweenStates(A.right, blockEndNFAState, -5);
         blockEndNFAState.setDescription("end ()? block");
         this.transitionBetweenStates(decisionState, emptyAlt, -5);
         this.transitionBetweenStates(emptyAlt, blockEndNFAState, -5);
         decisionState.endOfBlockStateNumber = blockEndNFAState.stateNumber;
         blockEndNFAState.decisionStateType = 5;
         g = new StateCluster(decisionState, blockEndNFAState);
      } else {
         decisionState = this.nfa.grammar.getNFAStateForAltOfDecision(A.left, n);
         emptyAlt = this.newState();
         emptyAlt.setDescription("epsilon path of ()? block");
         this.transitionBetweenStates(decisionState, emptyAlt, -5);
         this.transitionBetweenStates(emptyAlt, A.right, -5);
         A.left.endOfBlockStateNumber = A.right.stateNumber;
         A.right.decisionStateType = 5;
         g = A;
      }

      g.left.decisionStateType = 3;
      return g;
   }

   public StateCluster build_Aplus(StateCluster A) {
      NFAState left = this.newState();
      NFAState blockEndNFAState = this.newState();
      blockEndNFAState.decisionStateType = 5;
      if (A.right.decisionStateType == 5) {
         NFAState extraRightEdge = this.newState();
         this.transitionBetweenStates(A.right, extraRightEdge, -5);
         A.right = extraRightEdge;
      }

      this.transitionBetweenStates(A.right, blockEndNFAState, -5);
      this.transitionBetweenStates(A.right, A.left, -5);
      this.transitionBetweenStates(left, A.left, -5);
      A.right.decisionStateType = 1;
      A.left.decisionStateType = 2;
      A.left.endOfBlockStateNumber = A.right.stateNumber;
      StateCluster g = new StateCluster(left, blockEndNFAState);
      return g;
   }

   public StateCluster build_Astar(StateCluster A) {
      NFAState bypassDecisionState = this.newState();
      bypassDecisionState.setDescription("enter loop path of ()* block");
      NFAState optionalAlt = this.newState();
      optionalAlt.setDescription("epsilon path of ()* block");
      NFAState blockEndNFAState = this.newState();
      blockEndNFAState.decisionStateType = 5;
      if (A.right.decisionStateType == 5) {
         NFAState extraRightEdge = this.newState();
         this.transitionBetweenStates(A.right, extraRightEdge, -5);
         A.right = extraRightEdge;
      }

      A.right.setDescription("()* loopback");
      this.transitionBetweenStates(bypassDecisionState, A.left, -5);
      this.transitionBetweenStates(bypassDecisionState, optionalAlt, -5);
      this.transitionBetweenStates(optionalAlt, blockEndNFAState, -5);
      this.transitionBetweenStates(A.right, blockEndNFAState, -5);
      this.transitionBetweenStates(A.right, A.left, -5);
      bypassDecisionState.decisionStateType = 4;
      A.left.decisionStateType = 2;
      A.right.decisionStateType = 1;
      A.left.endOfBlockStateNumber = A.right.stateNumber;
      bypassDecisionState.endOfBlockStateNumber = blockEndNFAState.stateNumber;
      StateCluster g = new StateCluster(bypassDecisionState, blockEndNFAState);
      return g;
   }

   public StateCluster build_Wildcard(GrammarAST associatedAST) {
      NFAState left = this.newState();
      NFAState right = this.newState();
      left.associatedASTNode = associatedAST;
      right.associatedASTNode = associatedAST;
      Label label = new Label(this.nfa.grammar.getTokenTypes());
      Transition e = new Transition(label, right);
      left.addTransition(e);
      StateCluster g = new StateCluster(left, right);
      return g;
   }

   public StateCluster build_WildcardTree(GrammarAST associatedAST) {
      StateCluster wildRoot = this.build_Wildcard(associatedAST);
      StateCluster down = this.build_Atom(2, associatedAST);
      wildRoot = this.build_AB(wildRoot, down);
      StateCluster wildChildren = this.build_Wildcard(associatedAST);
      wildChildren = this.build_Aplus(wildChildren);
      wildRoot = this.build_AB(wildRoot, wildChildren);
      StateCluster up = this.build_Atom(3, associatedAST);
      wildRoot = this.build_AB(wildRoot, up);
      StateCluster optionalNodeAlt = this.build_Wildcard(associatedAST);
      List alts = new ArrayList();
      alts.add(wildRoot);
      alts.add(optionalNodeAlt);
      StateCluster blk = this.build_AlternativeBlock(alts);
      return blk;
   }

   protected IntSet getCollapsedBlockAsSet(State blk) {
      if (blk != null && blk.transition(0) != null) {
         State s1 = blk.transition(0).target;
         if (s1 != null && s1.transition(0) != null) {
            Label label = s1.transition(0).label;
            if (label.isSet()) {
               return label.getSet();
            }
         }
      }

      return null;
   }

   private void transitionBetweenStates(NFAState a, NFAState b, int label) {
      Transition e = new Transition(label, b);
      a.addTransition(e);
   }
}
