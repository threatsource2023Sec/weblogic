package org.antlr.tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import org.antlr.Tool;
import org.antlr.analysis.Label;
import org.antlr.analysis.NFAState;
import org.antlr.analysis.RuleClosureTransition;
import org.antlr.analysis.Transition;
import org.antlr.misc.IntervalSet;
import org.antlr.misc.Utils;

public class RandomPhrase {
   public static final boolean debug = false;
   protected static Random random;

   protected static void randomPhrase(Grammar g, List tokenTypes, String startRule) {
      NFAState state = g.getRuleStartState(startRule);
      NFAState stopState = g.getRuleStopState(startRule);
      Stack ruleInvocationStack = new Stack();

      while(true) {
         while(state != stopState || !ruleInvocationStack.isEmpty()) {
            if (state.getNumberOfTransitions() == 0) {
               return;
            }

            if (state.isAcceptState()) {
               NFAState invokingState = (NFAState)ruleInvocationStack.pop();
               RuleClosureTransition invokingTransition = (RuleClosureTransition)invokingState.transition[0];
               state = invokingTransition.followState;
            } else if (state.getNumberOfTransitions() != 1) {
               int decisionNumber = state.getDecisionNumber();
               if (decisionNumber == 0) {
                  System.out.println("weird: no decision number but a choice node");
               } else {
                  int n = g.getNumberOfAltsForDecisionNFA(state);
                  int randomAlt = random.nextInt(n) + 1;
                  NFAState altStartState = g.getNFAStateForAltOfDecision(state, randomAlt);
                  Transition t = altStartState.transition[0];
                  state = (NFAState)t.target;
               }
            } else {
               Transition t0 = state.transition[0];
               if (t0 instanceof RuleClosureTransition) {
                  ruleInvocationStack.push(state);
               } else if (t0.label.isSet() || t0.label.isAtom()) {
                  tokenTypes.add(getTokenType(t0.label));
               }

               state = (NFAState)t0.target;
            }
         }

         return;
      }
   }

   protected static Integer getTokenType(Label label) {
      if (label.isSet()) {
         IntervalSet typeSet = (IntervalSet)label.getSet();
         int randomIndex = random.nextInt(typeSet.size());
         return typeSet.get(randomIndex);
      } else {
         return Utils.integer(label.getAtom());
      }
   }

   public static void main(String[] args) {
      if (args.length < 2) {
         System.err.println("usage: java org.antlr.tool.RandomPhrase grammarfile startrule");
      } else {
         String grammarFileName = args[0];
         String startRule = args[1];
         long seed = System.currentTimeMillis();
         if (args.length == 3) {
            String seedStr = args[2];
            seed = Long.parseLong(seedStr);
         }

         try {
            random = new Random(seed);
            CompositeGrammar composite = new CompositeGrammar();
            Tool tool = new Tool();
            Grammar parser = new Grammar(tool, grammarFileName, composite);
            composite.setDelegationRoot(parser);
            FileReader fr = new FileReader(grammarFileName);
            BufferedReader br = new BufferedReader(fr);
            parser.parseAndBuildAST(br);
            br.close();
            parser.composite.assignTokenTypes();
            parser.composite.defineGrammarSymbols();
            parser.composite.createNFAs();
            List leftRecursiveRules = parser.checkAllRulesForLeftRecursion();
            if (leftRecursiveRules.size() > 0) {
               return;
            }

            if (parser.getRule(startRule) == null) {
               System.out.println("undefined start rule " + startRule);
               return;
            }

            String lexerGrammarText = parser.getLexerGrammar();
            Grammar lexer = new Grammar(tool);
            lexer.importTokenVocabulary(parser);
            lexer.fileName = grammarFileName;
            if (lexerGrammarText != null) {
               lexer.setGrammarContent(lexerGrammarText);
            } else {
               System.err.println("no lexer grammar found in " + grammarFileName);
            }

            lexer.buildNFA();
            leftRecursiveRules = lexer.checkAllRulesForLeftRecursion();
            if (leftRecursiveRules.size() > 0) {
               return;
            }

            List tokenTypes = new ArrayList(100);
            randomPhrase(parser, tokenTypes, startRule);
            System.out.println("token types=" + tokenTypes);

            for(int i = 0; i < tokenTypes.size(); ++i) {
               Integer ttypeI = (Integer)tokenTypes.get(i);
               int ttype = ttypeI;
               String ttypeDisplayName = parser.getTokenDisplayName(ttype);
               if (Character.isUpperCase(ttypeDisplayName.charAt(0))) {
                  List charsInToken = new ArrayList(10);
                  randomPhrase(lexer, charsInToken, ttypeDisplayName);
                  System.out.print(" ");

                  for(int j = 0; j < charsInToken.size(); ++j) {
                     Integer cI = (Integer)charsInToken.get(j);
                     System.out.print((char)cI);
                  }
               } else {
                  String literal = ttypeDisplayName.substring(1, ttypeDisplayName.length() - 1);
                  System.out.print(" " + literal);
               }
            }

            System.out.println();
         } catch (Error var21) {
            System.err.println("Error walking " + grammarFileName + " rule " + startRule + " seed " + seed);
            var21.printStackTrace(System.err);
         } catch (Exception var22) {
            System.err.println("Exception walking " + grammarFileName + " rule " + startRule + " seed " + seed);
            var22.printStackTrace(System.err);
         }

      }
   }
}
