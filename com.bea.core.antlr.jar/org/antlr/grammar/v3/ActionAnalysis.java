package org.antlr.grammar.v3;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.tool.AttributeScope;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;
import org.antlr.tool.Rule;

public class ActionAnalysis extends Lexer {
   public static final int EOF = -1;
   public static final int ID = 4;
   public static final int X = 5;
   public static final int X_Y = 6;
   public static final int Y = 7;
   Rule enclosingRule;
   Grammar grammar;
   Token actionToken;
   int outerAltNum;

   public ActionAnalysis(Grammar grammar, String ruleName, GrammarAST actionAST) {
      this(new ANTLRStringStream(actionAST.token.getText()));
      this.grammar = grammar;
      this.enclosingRule = grammar.getLocallyDefinedRule(ruleName);
      this.actionToken = actionAST.token;
      this.outerAltNum = actionAST.outerAltNum;
   }

   public void analyze() {
      Token t;
      do {
         t = this.nextToken();
      } while(t.getType() != -1);

   }

   public Lexer[] getDelegates() {
      return new Lexer[0];
   }

   public ActionAnalysis() {
      this.outerAltNum = 0;
   }

   public ActionAnalysis(CharStream input) {
      this(input, new RecognizerSharedState());
   }

   public ActionAnalysis(CharStream input, RecognizerSharedState state) {
      super(input, state);
      this.outerAltNum = 0;
   }

   public String getGrammarFileName() {
      return "org\\antlr\\grammar\\v3\\ActionAnalysis.g";
   }

   public Token nextToken() {
      while(this.input.LA(1) != -1) {
         this.state.token = null;
         this.state.channel = 0;
         this.state.tokenStartCharIndex = this.input.index();
         this.state.tokenStartCharPositionInLine = this.input.getCharPositionInLine();
         this.state.tokenStartLine = this.input.getLine();
         this.state.text = null;

         try {
            int m = this.input.mark();
            this.state.backtracking = 1;
            this.state.failed = false;
            this.mTokens();
            this.state.backtracking = 0;
            if (!this.state.failed) {
               this.emit();
               return this.state.token;
            }

            this.input.rewind(m);
            this.input.consume();
         } catch (RecognitionException var2) {
            this.reportError(var2);
            this.recover(var2);
         }
      }

      Token eof = new CommonToken(this.input, -1, 0, this.input.index(), this.input.index());
      eof.setLine(this.getLine());
      eof.setCharPositionInLine(this.getCharPositionInLine());
      return eof;
   }

   public void memoize(IntStream input, int ruleIndex, int ruleStartIndex) {
      if (this.state.backtracking > 1) {
         super.memoize(input, ruleIndex, ruleStartIndex);
      }

   }

   public boolean alreadyParsedRule(IntStream input, int ruleIndex) {
      return this.state.backtracking > 1 ? super.alreadyParsedRule(input, ruleIndex) : false;
   }

   public final void mX_Y() throws RecognitionException {
      try {
         int _type = 6;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart55 = this.getCharIndex();
            int xStartLine55 = this.getLine();
            int xStartCharPos55 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart55, this.getCharIndex() - 1);
               x.setLine(xStartLine55);
               x.setCharPositionInLine(xStartCharPos55);
               this.match(46);
               if (!this.state.failed) {
                  int yStart61 = this.getCharIndex();
                  int yStartLine61 = this.getLine();
                  int yStartCharPos61 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart61, this.getCharIndex() - 1);
                     y.setLine(yStartLine61);
                     y.setCharPositionInLine(yStartCharPos61);
                     if (this.enclosingRule == null) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                        } else {
                           throw new FailedPredicateException(this.input, "X_Y", "enclosingRule!=null");
                        }
                     } else {
                        if (this.state.backtracking == 1) {
                           AttributeScope scope = null;
                           String refdRuleName = null;
                           if ((x != null ? x.getText() : null).equals(this.enclosingRule.name)) {
                              refdRuleName = x != null ? x.getText() : null;
                              scope = this.enclosingRule.getLocalAttributeScope(y != null ? y.getText() : null);
                           } else if (this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) != null) {
                              Grammar.LabelElementPair pair = this.enclosingRule.getRuleLabel(x != null ? x.getText() : null);
                              pair.actionReferencesLabel = true;
                              refdRuleName = pair.referencedRuleName;
                              Rule refdRule = this.grammar.getRule(refdRuleName);
                              if (refdRule != null) {
                                 scope = refdRule.getLocalAttributeScope(y != null ? y.getText() : null);
                              }
                           } else if (this.enclosingRule.getRuleRefsInAlt(x.getText(), this.outerAltNum) != null) {
                              refdRuleName = x != null ? x.getText() : null;
                              Rule refdRule = this.grammar.getRule(refdRuleName);
                              if (refdRule != null) {
                                 scope = refdRule.getLocalAttributeScope(y != null ? y.getText() : null);
                              }
                           }

                           if (scope != null && (scope.isPredefinedRuleScope || scope.isPredefinedLexerRuleScope)) {
                              this.grammar.referenceRuleLabelPredefinedAttribute(refdRuleName);
                           }
                        }

                        this.state.type = _type;
                        this.state.channel = _channel;
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mX() throws RecognitionException {
      try {
         int _type = 5;
         int _channel = 0;
         CommonToken x = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart83 = this.getCharIndex();
            int xStartLine83 = this.getLine();
            int xStartCharPos83 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart83, this.getCharIndex() - 1);
               x.setLine(xStartLine83);
               x.setCharPositionInLine(xStartCharPos83);
               if (this.enclosingRule != null && this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) != null) {
                  if (this.state.backtracking == 1) {
                     Grammar.LabelElementPair pair = this.enclosingRule.getRuleLabel(x != null ? x.getText() : null);
                     pair.actionReferencesLabel = true;
                  }

                  this.state.type = _type;
                  this.state.channel = _channel;
               } else if (this.state.backtracking > 0) {
                  this.state.failed = true;
               } else {
                  throw new FailedPredicateException(this.input, "X", "enclosingRule!=null && enclosingRule.getRuleLabel($x.text)!=null");
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mY() throws RecognitionException {
      try {
         int _type = 7;
         int _channel = 0;
         CommonToken ID1 = null;
         this.match(36);
         if (!this.state.failed) {
            int ID1Start104 = this.getCharIndex();
            int ID1StartLine104 = this.getLine();
            int ID1StartCharPos104 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               ID1 = new CommonToken(this.input, 0, 0, ID1Start104, this.getCharIndex() - 1);
               ID1.setLine(ID1StartLine104);
               ID1.setCharPositionInLine(ID1StartCharPos104);
               if (this.enclosingRule != null && this.enclosingRule.getLocalAttributeScope(ID1 != null ? ID1.getText() : null) != null) {
                  if (this.state.backtracking == 1) {
                     AttributeScope scope = this.enclosingRule.getLocalAttributeScope(ID1 != null ? ID1.getText() : null);
                     if (scope != null && (scope.isPredefinedRuleScope || scope.isPredefinedLexerRuleScope)) {
                        this.grammar.referenceRuleLabelPredefinedAttribute(this.enclosingRule.name);
                     }
                  }

                  this.state.type = _type;
                  this.state.channel = _channel;
               } else if (this.state.backtracking > 0) {
                  this.state.failed = true;
               } else {
                  throw new FailedPredicateException(this.input, "Y", "enclosingRule!=null && enclosingRule.getLocalAttributeScope($ID.text)!=null");
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mID() throws RecognitionException {
      try {
         if (this.input.LA(1) >= 65 && this.input.LA(1) <= 90 || this.input.LA(1) == 95 || this.input.LA(1) >= 97 && this.input.LA(1) <= 122) {
            this.input.consume();
            this.state.failed = false;

            while(true) {
               int alt1 = 2;
               int LA1_0 = this.input.LA(1);
               if (LA1_0 >= 48 && LA1_0 <= 57 || LA1_0 >= 65 && LA1_0 <= 90 || LA1_0 == 95 || LA1_0 >= 97 && LA1_0 <= 122) {
                  alt1 = 1;
               }

               switch (alt1) {
                  case 1:
                     if ((this.input.LA(1) < 48 || this.input.LA(1) > 57) && (this.input.LA(1) < 65 || this.input.LA(1) > 90) && this.input.LA(1) != 95 && (this.input.LA(1) < 97 || this.input.LA(1) > 122)) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        } else {
                           MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                           this.recover(mse);
                           throw mse;
                        }
                     }

                     this.input.consume();
                     this.state.failed = false;
                     break;
                  default:
                     return;
               }
            }
         } else if (this.state.backtracking > 0) {
            this.state.failed = true;
         } else {
            MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
            this.recover(mse);
            throw mse;
         }
      } finally {
         ;
      }
   }

   public void mTokens() throws RecognitionException {
      int alt2 = true;
      int LA2_0 = this.input.LA(1);
      if (LA2_0 == 36) {
         int LA2_1 = this.input.LA(2);
         byte alt2;
         if (this.synpred1_ActionAnalysis()) {
            alt2 = 1;
         } else if (this.synpred2_ActionAnalysis()) {
            alt2 = 2;
         } else {
            alt2 = 3;
         }

         switch (alt2) {
            case 1:
               this.mX_Y();
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.mX();
               if (this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.mY();
               if (this.state.failed) {
                  return;
               }
         }

      } else if (this.state.backtracking > 0) {
         this.state.failed = true;
      } else {
         NoViableAltException nvae = new NoViableAltException("", 2, 0, this.input);
         throw nvae;
      }
   }

   public final void synpred1_ActionAnalysis_fragment() throws RecognitionException {
      this.mX_Y();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred2_ActionAnalysis_fragment() throws RecognitionException {
      this.mX();
      if (!this.state.failed) {
         ;
      }
   }

   public final boolean synpred2_ActionAnalysis() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred2_ActionAnalysis_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred1_ActionAnalysis() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred1_ActionAnalysis_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }
}
