package org.antlr.tool;

import java.util.List;
import java.util.Stack;
import org.antlr.analysis.DFA;
import org.antlr.analysis.DFAState;
import org.antlr.analysis.Label;
import org.antlr.analysis.NFAState;
import org.antlr.analysis.RuleClosureTransition;
import org.antlr.analysis.Transition;
import org.antlr.misc.IntervalSet;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.debug.BlankDebugEventListener;
import org.antlr.runtime.debug.DebugEventListener;
import org.antlr.runtime.debug.ParseTreeBuilder;
import org.antlr.runtime.tree.ParseTree;

public class Interpreter implements TokenSource {
   protected Grammar grammar;
   protected IntStream input;

   public Interpreter(Grammar grammar, IntStream input) {
      this.grammar = grammar;
      this.input = input;
   }

   public Token nextToken() {
      if (this.grammar.type != 1) {
         return null;
      } else if (this.input.LA(1) == -1) {
         return new CommonToken((CharStream)this.input, -1, 0, this.input.index(), this.input.index());
      } else {
         int start = this.input.index();
         int charPos = ((CharStream)this.input).getCharPositionInLine();
         CommonToken token = null;

         while(this.input.LA(1) != -1) {
            try {
               token = this.scan("Tokens", (List)null);
               break;
            } catch (RecognitionException var5) {
               this.reportScanError(var5);
            }
         }

         int stop = this.input.index() - 1;
         if (token == null) {
            return new CommonToken((CharStream)this.input, -1, 0, start, start);
         } else {
            token.setLine(((CharStream)this.input).getLine());
            token.setStartIndex(start);
            token.setStopIndex(stop);
            token.setCharPositionInLine(charPos);
            return token;
         }
      }
   }

   public void scan(String startRule, DebugEventListener actions, List visitedStates) throws RecognitionException {
      if (this.grammar.type == 1) {
         if (this.grammar.getRuleStartState(startRule) == null) {
            this.grammar.buildNFA();
         }

         if (!this.grammar.allDecisionDFAHaveBeenCreated()) {
            this.grammar.createLookaheadDFAs();
         }

         Stack ruleInvocationStack = new Stack();
         NFAState start = this.grammar.getRuleStartState(startRule);
         NFAState stop = this.grammar.getRuleStopState(startRule);
         this.parseEngine(startRule, start, stop, this.input, ruleInvocationStack, actions, visitedStates);
      }
   }

   public CommonToken scan(String startRule) throws RecognitionException {
      return this.scan(startRule, (List)null);
   }

   public CommonToken scan(String startRule, List visitedStates) throws RecognitionException {
      LexerActionGetTokenType actions = new LexerActionGetTokenType(this.grammar);
      this.scan(startRule, actions, visitedStates);
      return actions.token;
   }

   public void parse(String startRule, DebugEventListener actions, List visitedStates) throws RecognitionException {
      if (this.grammar.getRuleStartState(startRule) == null) {
         this.grammar.buildNFA();
      }

      if (!this.grammar.allDecisionDFAHaveBeenCreated()) {
         this.grammar.createLookaheadDFAs();
      }

      Stack ruleInvocationStack = new Stack();
      NFAState start = this.grammar.getRuleStartState(startRule);
      NFAState stop = this.grammar.getRuleStopState(startRule);
      this.parseEngine(startRule, start, stop, this.input, ruleInvocationStack, actions, visitedStates);
   }

   public ParseTree parse(String startRule) throws RecognitionException {
      return this.parse(startRule, (List)null);
   }

   public ParseTree parse(String startRule, List visitedStates) throws RecognitionException {
      ParseTreeBuilder actions = new ParseTreeBuilder(this.grammar.name);

      try {
         this.parse(startRule, actions, visitedStates);
      } catch (RecognitionException var5) {
      }

      return actions.getTree();
   }

   protected void parseEngine(String startRule, NFAState start, NFAState stop, IntStream input, Stack ruleInvocationStack, DebugEventListener actions, List visitedStates) throws RecognitionException {
      NFAState s = start;
      if (actions != null) {
         actions.enterRule(start.nfa.grammar.getFileName(), start.enclosingRule.name);
      }

      int t = input.LA(1);

      while(s != stop) {
         if (visitedStates != null) {
            visitedStates.add(s);
         }

         if (s.getDecisionNumber() > 0 && s.nfa.grammar.getNumberOfAltsForDecisionNFA(s) > 1) {
            DFA dfa = s.nfa.grammar.getLookaheadDFA(s.getDecisionNumber());
            int m = input.mark();
            int predictedAlt = this.predict(dfa);
            if (predictedAlt == -1) {
               String description = dfa.getNFADecisionStartState().getDescription();
               NoViableAltException nvae = new NoViableAltException(description, dfa.getDecisionNumber(), s.stateNumber, input);
               if (actions != null) {
                  actions.recognitionException(nvae);
               }

               input.consume();
               throw nvae;
            }

            input.rewind(m);
            int parseAlt = s.translateDisplayAltToWalkAlt(predictedAlt);
            NFAState alt;
            if (parseAlt > s.nfa.grammar.getNumberOfAltsForDecisionNFA(s)) {
               alt = s.nfa.grammar.nfa.getState(s.endOfBlockStateNumber);
            } else {
               alt = s.nfa.grammar.getNFAStateForAltOfDecision(s, parseAlt);
            }

            s = (NFAState)alt.transition[0].target;
         } else if (s.isAcceptState()) {
            if (actions != null) {
               actions.exitRule(s.nfa.grammar.getFileName(), s.enclosingRule.name);
            }

            if (ruleInvocationStack.empty()) {
               break;
            }

            NFAState invokingState = (NFAState)ruleInvocationStack.pop();
            RuleClosureTransition invokingTransition = (RuleClosureTransition)invokingState.transition[0];
            s = invokingTransition.followState;
         } else {
            Transition trans = s.transition[0];
            Label label = trans.label;
            FailedPredicateException fpe;
            if (label.isSemanticPredicate()) {
               fpe = new FailedPredicateException(input, s.enclosingRule.name, "can't deal with predicates yet");
               if (actions != null) {
                  actions.recognitionException(fpe);
               }
            }

            if (label.isEpsilon()) {
               if (trans instanceof RuleClosureTransition) {
                  ruleInvocationStack.push(s);
                  s = (NFAState)trans.target;
                  if (actions != null) {
                     actions.enterRule(s.nfa.grammar.getFileName(), s.enclosingRule.name);
                  }

                  if (!s.nfa.grammar.allDecisionDFAHaveBeenCreated()) {
                     s.nfa.grammar.createLookaheadDFAs();
                  }
               } else {
                  s = (NFAState)trans.target;
               }
            } else {
               if (!label.matches(t)) {
                  if (label.isAtom()) {
                     MismatchedTokenException mte = new MismatchedTokenException(label.getAtom(), input);
                     if (actions != null) {
                        actions.recognitionException(mte);
                     }

                     input.consume();
                     throw mte;
                  }

                  if (label.isSet()) {
                     MismatchedSetException mse = new MismatchedSetException(((IntervalSet)label.getSet()).toRuntimeBitSet(), input);
                     if (actions != null) {
                        actions.recognitionException(mse);
                     }

                     input.consume();
                     throw mse;
                  }

                  if (label.isSemanticPredicate()) {
                     fpe = new FailedPredicateException(input, s.enclosingRule.name, label.getSemanticContext().toString());
                     if (actions != null) {
                        actions.recognitionException(fpe);
                     }

                     input.consume();
                     throw fpe;
                  }

                  throw new RecognitionException(input);
               }

               if (actions != null && (s.nfa.grammar.type == 2 || s.nfa.grammar.type == 4)) {
                  actions.consumeToken(((TokenStream)input).LT(1));
               }

               s = (NFAState)s.transition[0].target;
               input.consume();
               t = input.LA(1);
            }
         }
      }

      if (actions != null) {
         actions.exitRule(s.nfa.grammar.getFileName(), stop.enclosingRule.name);
      }

   }

   public int predict(DFA dfa) {
      DFAState s = dfa.startState;
      int c = this.input.LA(1);
      Transition eotTransition = null;

      while(true) {
         label30:
         while(!s.isAcceptState()) {
            for(int i = 0; i < s.getNumberOfTransitions(); ++i) {
               Transition t = s.transition(i);
               if (t.label.matches(c)) {
                  s = (DFAState)t.target;
                  this.input.consume();
                  c = this.input.LA(1);
                  continue label30;
               }

               if (t.label.getAtom() == -2) {
                  eotTransition = t;
               }
            }

            if (eotTransition == null) {
               return -1;
            }

            s = (DFAState)eotTransition.target;
         }

         return s.getUniquelyPredictedAlt();
      }
   }

   public void reportScanError(RecognitionException re) {
      CharStream cs = (CharStream)this.input;
      System.err.println("problem matching token at " + cs.getLine() + ":" + cs.getCharPositionInLine() + " " + re);
   }

   public String getSourceName() {
      return this.input.getSourceName();
   }

   class LexerActionGetTokenType extends BlankDebugEventListener {
      public CommonToken token;
      Grammar g;

      public LexerActionGetTokenType(Grammar g) {
         this.g = g;
      }

      public void exitRule(String grammarFileName, String ruleName) {
         if (!ruleName.equals("Tokens")) {
            int type = this.g.getTokenType(ruleName);
            int channel = 0;
            this.token = new CommonToken((CharStream)Interpreter.this.input, type, channel, 0, 0);
         }

      }
   }
}
