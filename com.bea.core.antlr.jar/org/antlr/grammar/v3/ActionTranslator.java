package org.antlr.grammar.v3;

import java.util.ArrayList;
import java.util.List;
import org.antlr.codegen.CodeGenerator;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.tool.Attribute;
import org.antlr.tool.AttributeScope;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;
import org.antlr.tool.Rule;
import org.stringtemplate.v4.ST;

public class ActionTranslator extends Lexer {
   public static final int EOF = -1;
   public static final int ACTION = 4;
   public static final int ARG = 5;
   public static final int ATTR_VALUE_EXPR = 6;
   public static final int DYNAMIC_ABSOLUTE_INDEXED_SCOPE_ATTR = 7;
   public static final int DYNAMIC_NEGATIVE_INDEXED_SCOPE_ATTR = 8;
   public static final int DYNAMIC_SCOPE_ATTR = 9;
   public static final int ENCLOSING_RULE_SCOPE_ATTR = 10;
   public static final int ERROR_SCOPED_XY = 11;
   public static final int ERROR_X = 12;
   public static final int ERROR_XY = 13;
   public static final int ESC = 14;
   public static final int ID = 15;
   public static final int INDIRECT_TEMPLATE_INSTANCE = 16;
   public static final int INT = 17;
   public static final int ISOLATED_DYNAMIC_SCOPE = 18;
   public static final int ISOLATED_LEXER_RULE_REF = 19;
   public static final int ISOLATED_TOKEN_REF = 20;
   public static final int LABEL_REF = 21;
   public static final int LOCAL_ATTR = 22;
   public static final int RULE_SCOPE_ATTR = 23;
   public static final int SCOPE_INDEX_EXPR = 24;
   public static final int SET_ATTRIBUTE = 25;
   public static final int SET_DYNAMIC_SCOPE_ATTR = 26;
   public static final int SET_ENCLOSING_RULE_SCOPE_ATTR = 27;
   public static final int SET_EXPR_ATTRIBUTE = 28;
   public static final int SET_LOCAL_ATTR = 29;
   public static final int SET_RULE_SCOPE_ATTR = 30;
   public static final int SET_TOKEN_SCOPE_ATTR = 31;
   public static final int TEMPLATE_EXPR = 32;
   public static final int TEMPLATE_INSTANCE = 33;
   public static final int TEXT = 34;
   public static final int TOKEN_SCOPE_ATTR = 35;
   public static final int UNKNOWN_SYNTAX = 36;
   public static final int WS = 37;
   public List chunks;
   Rule enclosingRule;
   int outerAltNum;
   Grammar grammar;
   CodeGenerator generator;
   Token actionToken;

   public ActionTranslator(CodeGenerator generator, String ruleName, GrammarAST actionAST) {
      this(new ANTLRStringStream(actionAST.token.getText()));
      this.generator = generator;
      this.grammar = generator.grammar;
      this.enclosingRule = this.grammar.getLocallyDefinedRule(ruleName);
      this.actionToken = actionAST.token;
      this.outerAltNum = actionAST.outerAltNum;
   }

   public ActionTranslator(CodeGenerator generator, String ruleName, Token actionToken, int outerAltNum) {
      this(new ANTLRStringStream(actionToken.getText()));
      this.generator = generator;
      this.grammar = generator.grammar;
      this.enclosingRule = this.grammar.getRule(ruleName);
      this.actionToken = actionToken;
      this.outerAltNum = outerAltNum;
   }

   public List translateToChunks() {
      Token t;
      do {
         t = this.nextToken();
      } while(t.getType() != -1);

      return this.chunks;
   }

   public String translate() {
      List theChunks = this.translateToChunks();
      StringBuilder buf = new StringBuilder();

      for(int i = 0; i < theChunks.size(); ++i) {
         Object o = theChunks.get(i);
         if (o instanceof ST) {
            buf.append(((ST)o).render());
         } else {
            buf.append(o);
         }
      }

      return buf.toString();
   }

   public List translateAction(String action) {
      String rname = null;
      if (this.enclosingRule != null) {
         rname = this.enclosingRule.name;
      }

      ActionTranslator translator = new ActionTranslator(this.generator, rname, new CommonToken(4, action), this.outerAltNum);
      return translator.translateToChunks();
   }

   public boolean isTokenRefInAlt(String id) {
      return this.enclosingRule.getTokenRefsInAlt(id, this.outerAltNum) != null;
   }

   public boolean isRuleRefInAlt(String id) {
      return this.enclosingRule.getRuleRefsInAlt(id, this.outerAltNum) != null;
   }

   public Grammar.LabelElementPair getElementLabel(String id) {
      return this.enclosingRule.getLabel(id);
   }

   public void checkElementRefUniqueness(String ref, boolean isToken) {
      List refs = null;
      if (isToken) {
         refs = this.enclosingRule.getTokenRefsInAlt(ref, this.outerAltNum);
      } else {
         refs = this.enclosingRule.getRuleRefsInAlt(ref, this.outerAltNum);
      }

      if (refs != null && refs.size() > 1) {
         ErrorManager.grammarError(127, this.grammar, this.actionToken, ref);
      }

   }

   public Attribute getRuleLabelAttribute(String ruleName, String attrName) {
      Rule r = this.grammar.getRule(ruleName);
      AttributeScope scope = r.getLocalAttributeScope(attrName);
      return scope != null && !scope.isParameterScope ? scope.getAttribute(attrName) : null;
   }

   AttributeScope resolveDynamicScope(String scopeName) {
      if (this.grammar.getGlobalScope(scopeName) != null) {
         return this.grammar.getGlobalScope(scopeName);
      } else {
         Rule scopeRule = this.grammar.getRule(scopeName);
         return scopeRule != null ? scopeRule.ruleScope : null;
      }
   }

   protected ST template(String name) {
      ST st = this.generator.getTemplates().getInstanceOf(name);
      this.chunks.add(st);
      return st;
   }

   public Lexer[] getDelegates() {
      return new Lexer[0];
   }

   public ActionTranslator() {
      this.chunks = new ArrayList();
   }

   public ActionTranslator(CharStream input) {
      this(input, new RecognizerSharedState());
   }

   public ActionTranslator(CharStream input, RecognizerSharedState state) {
      super(input, state);
      this.chunks = new ArrayList();
   }

   public String getGrammarFileName() {
      return "org\\antlr\\grammar\\v3\\ActionTranslator.g";
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

   public final void mSET_ENCLOSING_RULE_SCOPE_ATTR() throws RecognitionException {
      try {
         int _type = 27;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         CommonToken expr = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart56 = this.getCharIndex();
            int xStartLine56 = this.getLine();
            int xStartCharPos56 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart56, this.getCharIndex() - 1);
               x.setLine(xStartLine56);
               x.setCharPositionInLine(xStartCharPos56);
               this.match(46);
               if (!this.state.failed) {
                  int yStart62 = this.getCharIndex();
                  int yStartLine62 = this.getLine();
                  int yStartCharPos62 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart62, this.getCharIndex() - 1);
                     y.setLine(yStartLine62);
                     y.setCharPositionInLine(yStartCharPos62);
                     int alt1 = 2;
                     int LA1_0 = this.input.LA(1);
                     if (LA1_0 >= 9 && LA1_0 <= 10 || LA1_0 == 13 || LA1_0 == 32) {
                        alt1 = 1;
                     }

                     switch (alt1) {
                        case 1:
                           this.mWS();
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           this.match(61);
                           if (!this.state.failed) {
                              int exprStart71 = this.getCharIndex();
                              int exprStartLine71 = this.getLine();
                              int exprStartCharPos71 = this.getCharPositionInLine();
                              this.mATTR_VALUE_EXPR();
                              if (!this.state.failed) {
                                 expr = new CommonToken(this.input, 0, 0, exprStart71, this.getCharIndex() - 1);
                                 expr.setLine(exprStartLine71);
                                 expr.setCharPositionInLine(exprStartCharPos71);
                                 this.match(59);
                                 if (!this.state.failed) {
                                    if (this.enclosingRule != null && (x != null ? x.getText() : null).equals(this.enclosingRule.name) && this.enclosingRule.getLocalAttributeScope(y != null ? y.getText() : null) != null) {
                                       if (this.state.backtracking == 1) {
                                          ST st = null;
                                          AttributeScope scope = this.enclosingRule.getLocalAttributeScope(y != null ? y.getText() : null);
                                          if (scope.isPredefinedRuleScope) {
                                             if (!(y != null ? y.getText() : null).equals("st") && !(y != null ? y.getText() : null).equals("tree")) {
                                                ErrorManager.grammarError(151, this.grammar, this.actionToken, x != null ? x.getText() : null, y != null ? y.getText() : null);
                                             } else {
                                                st = this.template("ruleSetPropertyRef_" + (y != null ? y.getText() : null));
                                                this.grammar.referenceRuleLabelPredefinedAttribute(x != null ? x.getText() : null);
                                                st.add("scope", x != null ? x.getText() : null);
                                                st.add("attr", y != null ? y.getText() : null);
                                                st.add("expr", this.translateAction(expr != null ? expr.getText() : null));
                                             }
                                          } else if (scope.isPredefinedLexerRuleScope) {
                                             ErrorManager.grammarError(151, this.grammar, this.actionToken, x != null ? x.getText() : null, y != null ? y.getText() : null);
                                          } else if (scope.isParameterScope) {
                                             st = this.template("parameterSetAttributeRef");
                                             st.add("attr", scope.getAttribute(y != null ? y.getText() : null));
                                             st.add("expr", this.translateAction(expr != null ? expr.getText() : null));
                                          } else {
                                             st = this.template("returnSetAttributeRef");
                                             st.add("ruleDescriptor", this.enclosingRule);
                                             st.add("attr", scope.getAttribute(y != null ? y.getText() : null));
                                             st.add("expr", this.translateAction(expr != null ? expr.getText() : null));
                                          }
                                       }

                                       this.state.type = _type;
                                       this.state.channel = _channel;
                                    } else if (this.state.backtracking > 0) {
                                       this.state.failed = true;
                                    } else {
                                       throw new FailedPredicateException(this.input, "SET_ENCLOSING_RULE_SCOPE_ATTR", "enclosingRule!=null &&\r\n\t                         $x.text.equals(enclosingRule.name) &&\r\n\t                         enclosingRule.getLocalAttributeScope($y.text)!=null");
                                    }
                                 }
                              }
                           }
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mENCLOSING_RULE_SCOPE_ATTR() throws RecognitionException {
      try {
         int _type = 10;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart103 = this.getCharIndex();
            int xStartLine103 = this.getLine();
            int xStartCharPos103 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart103, this.getCharIndex() - 1);
               x.setLine(xStartLine103);
               x.setCharPositionInLine(xStartCharPos103);
               this.match(46);
               if (!this.state.failed) {
                  int yStart109 = this.getCharIndex();
                  int yStartLine109 = this.getLine();
                  int yStartCharPos109 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart109, this.getCharIndex() - 1);
                     y.setLine(yStartLine109);
                     y.setCharPositionInLine(yStartCharPos109);
                     if (this.enclosingRule != null && (x != null ? x.getText() : null).equals(this.enclosingRule.name) && this.enclosingRule.getLocalAttributeScope(y != null ? y.getText() : null) != null) {
                        if (this.state.backtracking == 1) {
                           if (this.isRuleRefInAlt(x != null ? x.getText() : null)) {
                              ErrorManager.grammarError(132, this.grammar, this.actionToken, x != null ? x.getText() : null);
                           }

                           ST st = null;
                           AttributeScope scope = this.enclosingRule.getLocalAttributeScope(y != null ? y.getText() : null);
                           if (scope.isPredefinedRuleScope) {
                              st = this.template("rulePropertyRef_" + (y != null ? y.getText() : null));
                              this.grammar.referenceRuleLabelPredefinedAttribute(x != null ? x.getText() : null);
                              st.add("scope", x != null ? x.getText() : null);
                              st.add("attr", y != null ? y.getText() : null);
                           } else if (scope.isPredefinedLexerRuleScope) {
                              ErrorManager.grammarError(130, this.grammar, this.actionToken, x != null ? x.getText() : null);
                           } else if (scope.isParameterScope) {
                              st = this.template("parameterAttributeRef");
                              st.add("attr", scope.getAttribute(y != null ? y.getText() : null));
                           } else {
                              st = this.template("returnAttributeRef");
                              st.add("ruleDescriptor", this.enclosingRule);
                              st.add("attr", scope.getAttribute(y != null ? y.getText() : null));
                           }
                        }

                        this.state.type = _type;
                        this.state.channel = _channel;
                     } else if (this.state.backtracking > 0) {
                        this.state.failed = true;
                     } else {
                        throw new FailedPredicateException(this.input, "ENCLOSING_RULE_SCOPE_ATTR", "enclosingRule!=null &&\r\n\t                         $x.text.equals(enclosingRule.name) &&\r\n\t                         enclosingRule.getLocalAttributeScope($y.text)!=null");
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mSET_TOKEN_SCOPE_ATTR() throws RecognitionException {
      try {
         int _type = 31;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart135 = this.getCharIndex();
            int xStartLine135 = this.getLine();
            int xStartCharPos135 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart135, this.getCharIndex() - 1);
               x.setLine(xStartLine135);
               x.setCharPositionInLine(xStartCharPos135);
               this.match(46);
               if (!this.state.failed) {
                  int yStart141 = this.getCharIndex();
                  int yStartLine141 = this.getLine();
                  int yStartCharPos141 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart141, this.getCharIndex() - 1);
                     y.setLine(yStartLine141);
                     y.setCharPositionInLine(yStartCharPos141);
                     int alt2 = 2;
                     int LA2_0 = this.input.LA(1);
                     if (LA2_0 >= 9 && LA2_0 <= 10 || LA2_0 == 13 || LA2_0 == 32) {
                        alt2 = 1;
                     }

                     switch (alt2) {
                        case 1:
                           this.mWS();
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           this.match(61);
                           if (!this.state.failed) {
                              if (this.enclosingRule != null && this.input.LA(1) != 61 && (this.enclosingRule.getTokenLabel(x != null ? x.getText() : null) != null || this.isTokenRefInAlt(x != null ? x.getText() : null)) && AttributeScope.tokenScope.getAttribute(y != null ? y.getText() : null) != null) {
                                 if (this.state.backtracking == 1) {
                                    ErrorManager.grammarError(151, this.grammar, this.actionToken, x != null ? x.getText() : null, y != null ? y.getText() : null);
                                 }

                                 this.state.type = _type;
                                 this.state.channel = _channel;
                              } else if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                              } else {
                                 throw new FailedPredicateException(this.input, "SET_TOKEN_SCOPE_ATTR", "enclosingRule!=null && input.LA(1)!='=' &&\r\n\t                         (enclosingRule.getTokenLabel($x.text)!=null||\r\n\t                          isTokenRefInAlt($x.text)) &&\r\n\t                         AttributeScope.tokenScope.getAttribute($y.text)!=null");
                              }
                           }
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mTOKEN_SCOPE_ATTR() throws RecognitionException {
      try {
         int _type = 35;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart180 = this.getCharIndex();
            int xStartLine180 = this.getLine();
            int xStartCharPos180 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart180, this.getCharIndex() - 1);
               x.setLine(xStartLine180);
               x.setCharPositionInLine(xStartCharPos180);
               this.match(46);
               if (!this.state.failed) {
                  int yStart186 = this.getCharIndex();
                  int yStartLine186 = this.getLine();
                  int yStartCharPos186 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart186, this.getCharIndex() - 1);
                     y.setLine(yStartLine186);
                     y.setCharPositionInLine(yStartCharPos186);
                     if (this.enclosingRule == null || this.enclosingRule.getTokenLabel(x != null ? x.getText() : null) == null && !this.isTokenRefInAlt(x != null ? x.getText() : null) || AttributeScope.tokenScope.getAttribute(y != null ? y.getText() : null) == null || this.grammar.type == 1 && this.getElementLabel(x != null ? x.getText() : null).elementRef.token.getType() != 94 && this.getElementLabel(x != null ? x.getText() : null).elementRef.token.getType() != 88) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                        } else {
                           throw new FailedPredicateException(this.input, "TOKEN_SCOPE_ATTR", "enclosingRule!=null &&\r\n\t                         (enclosingRule.getTokenLabel($x.text)!=null||\r\n\t                          isTokenRefInAlt($x.text)) &&\r\n\t                         AttributeScope.tokenScope.getAttribute($y.text)!=null &&\r\n\t                         (grammar.type!=Grammar.LEXER ||\r\n\t                         getElementLabel($x.text).elementRef.token.getType()==ANTLRParser.TOKEN_REF ||\r\n\t                         getElementLabel($x.text).elementRef.token.getType()==ANTLRParser.STRING_LITERAL)");
                        }
                     } else {
                        if (this.state.backtracking == 1) {
                           String label = x != null ? x.getText() : null;
                           if (this.enclosingRule.getTokenLabel(x != null ? x.getText() : null) == null) {
                              this.checkElementRefUniqueness(x != null ? x.getText() : null, true);
                              label = this.enclosingRule.getElementLabel(x != null ? x.getText() : null, this.outerAltNum, this.generator);
                              if (label == null) {
                                 ErrorManager.grammarError(128, this.grammar, this.actionToken, "$" + (x != null ? x.getText() : null) + "." + (y != null ? y.getText() : null));
                                 label = x != null ? x.getText() : null;
                              }
                           }

                           ST st = this.template("tokenLabelPropertyRef_" + (y != null ? y.getText() : null));
                           st.add("scope", label);
                           st.add("attr", AttributeScope.tokenScope.getAttribute(y != null ? y.getText() : null));
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

   public final void mSET_RULE_SCOPE_ATTR() throws RecognitionException {
      try {
         int _type = 30;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         Grammar.LabelElementPair pair = null;
         String refdRuleName = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart217 = this.getCharIndex();
            int xStartLine217 = this.getLine();
            int xStartCharPos217 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart217, this.getCharIndex() - 1);
               x.setLine(xStartLine217);
               x.setCharPositionInLine(xStartCharPos217);
               this.match(46);
               if (!this.state.failed) {
                  int yStart223 = this.getCharIndex();
                  int yStartLine223 = this.getLine();
                  int yStartCharPos223 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart223, this.getCharIndex() - 1);
                     y.setLine(yStartLine223);
                     y.setCharPositionInLine(yStartCharPos223);
                     int alt3 = 2;
                     int LA3_0 = this.input.LA(1);
                     if (LA3_0 >= 9 && LA3_0 <= 10 || LA3_0 == 13 || LA3_0 == 32) {
                        alt3 = 1;
                     }

                     switch (alt3) {
                        case 1:
                           this.mWS();
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           this.match(61);
                           if (!this.state.failed) {
                              if (this.enclosingRule != null && this.input.LA(1) != 61) {
                                 if (this.state.backtracking == 1) {
                                    pair = this.enclosingRule.getRuleLabel(x != null ? x.getText() : null);
                                    if (x != null) {
                                       x.getText();
                                    } else {
                                       Object var10000 = null;
                                    }

                                    if (pair != null) {
                                       refdRuleName = pair.referencedRuleName;
                                    }
                                 }

                                 if ((this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) != null || this.isRuleRefInAlt(x != null ? x.getText() : null)) && this.getRuleLabelAttribute(this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) != null ? this.enclosingRule.getRuleLabel(x != null ? x.getText() : null).referencedRuleName : (x != null ? x.getText() : null), y != null ? y.getText() : null) != null) {
                                    if (this.state.backtracking == 1) {
                                       ErrorManager.grammarError(151, this.grammar, this.actionToken, x != null ? x.getText() : null, y != null ? y.getText() : null);
                                    }

                                    this.state.type = _type;
                                    this.state.channel = _channel;
                                 } else if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                 } else {
                                    throw new FailedPredicateException(this.input, "SET_RULE_SCOPE_ATTR", "(enclosingRule.getRuleLabel($x.text)!=null || isRuleRefInAlt($x.text)) &&\r\n\t      getRuleLabelAttribute(enclosingRule.getRuleLabel($x.text)!=null?enclosingRule.getRuleLabel($x.text).referencedRuleName:$x.text,$y.text)!=null");
                                 }
                              } else if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                              } else {
                                 throw new FailedPredicateException(this.input, "SET_RULE_SCOPE_ATTR", "enclosingRule!=null && input.LA(1)!='='");
                              }
                           }
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mRULE_SCOPE_ATTR() throws RecognitionException {
      try {
         int _type = 23;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         Grammar.LabelElementPair pair = null;
         String refdRuleName = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart276 = this.getCharIndex();
            int xStartLine276 = this.getLine();
            int xStartCharPos276 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart276, this.getCharIndex() - 1);
               x.setLine(xStartLine276);
               x.setCharPositionInLine(xStartCharPos276);
               this.match(46);
               if (!this.state.failed) {
                  int yStart282 = this.getCharIndex();
                  int yStartLine282 = this.getLine();
                  int yStartCharPos282 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart282, this.getCharIndex() - 1);
                     y.setLine(yStartLine282);
                     y.setCharPositionInLine(yStartCharPos282);
                     if (this.enclosingRule == null) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                        } else {
                           throw new FailedPredicateException(this.input, "RULE_SCOPE_ATTR", "enclosingRule!=null");
                        }
                     } else {
                        if (this.state.backtracking == 1) {
                           pair = this.enclosingRule.getRuleLabel(x != null ? x.getText() : null);
                           refdRuleName = x != null ? x.getText() : null;
                           if (pair != null) {
                              refdRuleName = pair.referencedRuleName;
                           }
                        }

                        if ((this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) != null || this.isRuleRefInAlt(x != null ? x.getText() : null)) && this.getRuleLabelAttribute(this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) != null ? this.enclosingRule.getRuleLabel(x != null ? x.getText() : null).referencedRuleName : (x != null ? x.getText() : null), y != null ? y.getText() : null) != null) {
                           if (this.state.backtracking == 1) {
                              String label = x != null ? x.getText() : null;
                              if (pair == null) {
                                 this.checkElementRefUniqueness(x != null ? x.getText() : null, false);
                                 label = this.enclosingRule.getElementLabel(x != null ? x.getText() : null, this.outerAltNum, this.generator);
                                 if (label == null) {
                                    ErrorManager.grammarError(128, this.grammar, this.actionToken, "$" + (x != null ? x.getText() : null) + "." + (y != null ? y.getText() : null));
                                    label = x != null ? x.getText() : null;
                                 }
                              }

                              Rule refdRule = this.grammar.getRule(refdRuleName);
                              AttributeScope scope = refdRule.getLocalAttributeScope(y != null ? y.getText() : null);
                              ST st;
                              if (scope.isPredefinedRuleScope) {
                                 st = this.template("ruleLabelPropertyRef_" + (y != null ? y.getText() : null));
                                 this.grammar.referenceRuleLabelPredefinedAttribute(refdRuleName);
                                 st.add("scope", label);
                                 st.add("attr", y != null ? y.getText() : null);
                              } else if (scope.isPredefinedLexerRuleScope) {
                                 st = this.template("lexerRuleLabelPropertyRef_" + (y != null ? y.getText() : null));
                                 this.grammar.referenceRuleLabelPredefinedAttribute(refdRuleName);
                                 st.add("scope", label);
                                 st.add("attr", y != null ? y.getText() : null);
                              } else if (!scope.isParameterScope) {
                                 st = this.template("ruleLabelRef");
                                 st.add("referencedRule", refdRule);
                                 st.add("scope", label);
                                 st.add("attr", scope.getAttribute(y != null ? y.getText() : null));
                              }
                           }

                           this.state.type = _type;
                           this.state.channel = _channel;
                        } else if (this.state.backtracking > 0) {
                           this.state.failed = true;
                        } else {
                           throw new FailedPredicateException(this.input, "RULE_SCOPE_ATTR", "(enclosingRule.getRuleLabel($x.text)!=null || isRuleRefInAlt($x.text)) &&\r\n\t      getRuleLabelAttribute(enclosingRule.getRuleLabel($x.text)!=null?enclosingRule.getRuleLabel($x.text).referencedRuleName:$x.text,$y.text)!=null");
                        }
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mLABEL_REF() throws RecognitionException {
      try {
         int _type = 21;
         int _channel = 0;
         CommonToken ID1 = null;
         this.match(36);
         if (!this.state.failed) {
            int ID1Start324 = this.getCharIndex();
            int ID1StartLine324 = this.getLine();
            int ID1StartCharPos324 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               ID1 = new CommonToken(this.input, 0, 0, ID1Start324, this.getCharIndex() - 1);
               ID1.setLine(ID1StartLine324);
               ID1.setCharPositionInLine(ID1StartCharPos324);
               if (this.enclosingRule != null && this.getElementLabel(ID1 != null ? ID1.getText() : null) != null && this.enclosingRule.getRuleLabel(ID1 != null ? ID1.getText() : null) == null) {
                  if (this.state.backtracking == 1) {
                     Grammar.LabelElementPair pair = this.getElementLabel(ID1 != null ? ID1.getText() : null);
                     ST st;
                     if (pair.type != 3 && pair.type != 4 && pair.type != 7) {
                        st = this.template("tokenLabelRef");
                     } else {
                        st = this.template("listLabelRef");
                     }

                     st.add("label", ID1 != null ? ID1.getText() : null);
                  }

                  this.state.type = _type;
                  this.state.channel = _channel;
               } else if (this.state.backtracking > 0) {
                  this.state.failed = true;
               } else {
                  throw new FailedPredicateException(this.input, "LABEL_REF", "enclosingRule!=null &&\r\n\t            getElementLabel($ID.text)!=null &&\r\n\t\t        enclosingRule.getRuleLabel($ID.text)==null");
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mISOLATED_TOKEN_REF() throws RecognitionException {
      try {
         int _type = 20;
         int _channel = 0;
         CommonToken ID2 = null;
         this.match(36);
         if (!this.state.failed) {
            int ID2Start348 = this.getCharIndex();
            int ID2StartLine348 = this.getLine();
            int ID2StartCharPos348 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               ID2 = new CommonToken(this.input, 0, 0, ID2Start348, this.getCharIndex() - 1);
               ID2.setLine(ID2StartLine348);
               ID2.setCharPositionInLine(ID2StartCharPos348);
               if (this.grammar.type != 1 && this.enclosingRule != null && this.isTokenRefInAlt(ID2 != null ? ID2.getText() : null)) {
                  if (this.state.backtracking == 1) {
                     String label = this.enclosingRule.getElementLabel(ID2 != null ? ID2.getText() : null, this.outerAltNum, this.generator);
                     this.checkElementRefUniqueness(ID2 != null ? ID2.getText() : null, true);
                     if (label == null) {
                        ErrorManager.grammarError(128, this.grammar, this.actionToken, ID2 != null ? ID2.getText() : null);
                     } else {
                        ST st = this.template("tokenLabelRef");
                        st.add("label", label);
                     }
                  }

                  this.state.type = _type;
                  this.state.channel = _channel;
               } else if (this.state.backtracking > 0) {
                  this.state.failed = true;
               } else {
                  throw new FailedPredicateException(this.input, "ISOLATED_TOKEN_REF", "grammar.type!=Grammar.LEXER && enclosingRule!=null && isTokenRefInAlt($ID.text)");
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mISOLATED_LEXER_RULE_REF() throws RecognitionException {
      try {
         int _type = 19;
         int _channel = 0;
         CommonToken ID3 = null;
         this.match(36);
         if (!this.state.failed) {
            int ID3Start372 = this.getCharIndex();
            int ID3StartLine372 = this.getLine();
            int ID3StartCharPos372 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               ID3 = new CommonToken(this.input, 0, 0, ID3Start372, this.getCharIndex() - 1);
               ID3.setLine(ID3StartLine372);
               ID3.setCharPositionInLine(ID3StartCharPos372);
               if (this.grammar.type == 1 && this.enclosingRule != null && this.isRuleRefInAlt(ID3 != null ? ID3.getText() : null)) {
                  if (this.state.backtracking == 1) {
                     String label = this.enclosingRule.getElementLabel(ID3 != null ? ID3.getText() : null, this.outerAltNum, this.generator);
                     this.checkElementRefUniqueness(ID3 != null ? ID3.getText() : null, false);
                     if (label == null) {
                        ErrorManager.grammarError(128, this.grammar, this.actionToken, ID3 != null ? ID3.getText() : null);
                     } else {
                        ST st = this.template("lexerRuleLabel");
                        st.add("label", label);
                     }
                  }

                  this.state.type = _type;
                  this.state.channel = _channel;
               } else if (this.state.backtracking > 0) {
                  this.state.failed = true;
               } else {
                  throw new FailedPredicateException(this.input, "ISOLATED_LEXER_RULE_REF", "grammar.type==Grammar.LEXER &&\r\n\t             enclosingRule!=null &&\r\n\t             isRuleRefInAlt($ID.text)");
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mSET_LOCAL_ATTR() throws RecognitionException {
      try {
         int _type = 29;
         int _channel = 0;
         CommonToken expr = null;
         CommonToken ID4 = null;
         this.match(36);
         if (!this.state.failed) {
            int ID4Start396 = this.getCharIndex();
            int ID4StartLine396 = this.getLine();
            int ID4StartCharPos396 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               ID4 = new CommonToken(this.input, 0, 0, ID4Start396, this.getCharIndex() - 1);
               ID4.setLine(ID4StartLine396);
               ID4.setCharPositionInLine(ID4StartCharPos396);
               int alt4 = 2;
               int LA4_0 = this.input.LA(1);
               if (LA4_0 >= 9 && LA4_0 <= 10 || LA4_0 == 13 || LA4_0 == 32) {
                  alt4 = 1;
               }

               switch (alt4) {
                  case 1:
                     this.mWS();
                     if (this.state.failed) {
                        return;
                     }
                  default:
                     this.match(61);
                     if (!this.state.failed) {
                        int exprStart405 = this.getCharIndex();
                        int exprStartLine405 = this.getLine();
                        int exprStartCharPos405 = this.getCharPositionInLine();
                        this.mATTR_VALUE_EXPR();
                        if (!this.state.failed) {
                           expr = new CommonToken(this.input, 0, 0, exprStart405, this.getCharIndex() - 1);
                           expr.setLine(exprStartLine405);
                           expr.setCharPositionInLine(exprStartCharPos405);
                           this.match(59);
                           if (!this.state.failed) {
                              if (this.enclosingRule != null && this.enclosingRule.getLocalAttributeScope(ID4 != null ? ID4.getText() : null) != null && !this.enclosingRule.getLocalAttributeScope(ID4 != null ? ID4.getText() : null).isPredefinedLexerRuleScope) {
                                 if (this.state.backtracking == 1) {
                                    AttributeScope scope = this.enclosingRule.getLocalAttributeScope(ID4 != null ? ID4.getText() : null);
                                    ST st;
                                    if (scope.isPredefinedRuleScope) {
                                       if (!(ID4 != null ? ID4.getText() : null).equals("tree") && !(ID4 != null ? ID4.getText() : null).equals("st")) {
                                          ErrorManager.grammarError(151, this.grammar, this.actionToken, ID4 != null ? ID4.getText() : null, "");
                                       } else {
                                          st = this.template("ruleSetPropertyRef_" + (ID4 != null ? ID4.getText() : null));
                                          this.grammar.referenceRuleLabelPredefinedAttribute(this.enclosingRule.name);
                                          st.add("scope", this.enclosingRule.name);
                                          st.add("attr", ID4 != null ? ID4.getText() : null);
                                          st.add("expr", this.translateAction(expr != null ? expr.getText() : null));
                                       }
                                    } else if (scope.isParameterScope) {
                                       st = this.template("parameterSetAttributeRef");
                                       st.add("attr", scope.getAttribute(ID4 != null ? ID4.getText() : null));
                                       st.add("expr", this.translateAction(expr != null ? expr.getText() : null));
                                    } else {
                                       st = this.template("returnSetAttributeRef");
                                       st.add("ruleDescriptor", this.enclosingRule);
                                       st.add("attr", scope.getAttribute(ID4 != null ? ID4.getText() : null));
                                       st.add("expr", this.translateAction(expr != null ? expr.getText() : null));
                                    }
                                 }

                                 this.state.type = _type;
                                 this.state.channel = _channel;
                              } else if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                              } else {
                                 throw new FailedPredicateException(this.input, "SET_LOCAL_ATTR", "enclosingRule!=null\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t&& enclosingRule.getLocalAttributeScope($ID.text)!=null\r\n\t\t\t\t\t\t\t\t\t\t\t\t\t&& !enclosingRule.getLocalAttributeScope($ID.text).isPredefinedLexerRuleScope");
                              }
                           }
                        }
                     }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mLOCAL_ATTR() throws RecognitionException {
      try {
         int _type = 22;
         int _channel = 0;
         CommonToken ID5 = null;
         this.match(36);
         if (!this.state.failed) {
            int ID5Start428 = this.getCharIndex();
            int ID5StartLine428 = this.getLine();
            int ID5StartCharPos428 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               ID5 = new CommonToken(this.input, 0, 0, ID5Start428, this.getCharIndex() - 1);
               ID5.setLine(ID5StartLine428);
               ID5.setCharPositionInLine(ID5StartCharPos428);
               if (this.enclosingRule != null && this.enclosingRule.getLocalAttributeScope(ID5 != null ? ID5.getText() : null) != null) {
                  if (this.state.backtracking == 1) {
                     AttributeScope scope = this.enclosingRule.getLocalAttributeScope(ID5 != null ? ID5.getText() : null);
                     ST st;
                     if (scope.isPredefinedRuleScope) {
                        st = this.template("rulePropertyRef_" + (ID5 != null ? ID5.getText() : null));
                        this.grammar.referenceRuleLabelPredefinedAttribute(this.enclosingRule.name);
                        st.add("scope", this.enclosingRule.name);
                        st.add("attr", ID5 != null ? ID5.getText() : null);
                     } else if (scope.isPredefinedLexerRuleScope) {
                        st = this.template("lexerRulePropertyRef_" + (ID5 != null ? ID5.getText() : null));
                        st.add("scope", this.enclosingRule.name);
                        st.add("attr", ID5 != null ? ID5.getText() : null);
                     } else if (scope.isParameterScope) {
                        st = this.template("parameterAttributeRef");
                        st.add("attr", scope.getAttribute(ID5 != null ? ID5.getText() : null));
                     } else {
                        st = this.template("returnAttributeRef");
                        st.add("ruleDescriptor", this.enclosingRule);
                        st.add("attr", scope.getAttribute(ID5 != null ? ID5.getText() : null));
                     }
                  }

                  this.state.type = _type;
                  this.state.channel = _channel;
               } else if (this.state.backtracking > 0) {
                  this.state.failed = true;
               } else {
                  throw new FailedPredicateException(this.input, "LOCAL_ATTR", "enclosingRule!=null && enclosingRule.getLocalAttributeScope($ID.text)!=null");
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mSET_DYNAMIC_SCOPE_ATTR() throws RecognitionException {
      try {
         int _type = 26;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         CommonToken expr = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart454 = this.getCharIndex();
            int xStartLine454 = this.getLine();
            int xStartCharPos454 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart454, this.getCharIndex() - 1);
               x.setLine(xStartLine454);
               x.setCharPositionInLine(xStartCharPos454);
               this.match("::");
               if (!this.state.failed) {
                  int yStart460 = this.getCharIndex();
                  int yStartLine460 = this.getLine();
                  int yStartCharPos460 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart460, this.getCharIndex() - 1);
                     y.setLine(yStartLine460);
                     y.setCharPositionInLine(yStartCharPos460);
                     int alt5 = 2;
                     int LA5_0 = this.input.LA(1);
                     if (LA5_0 >= 9 && LA5_0 <= 10 || LA5_0 == 13 || LA5_0 == 32) {
                        alt5 = 1;
                     }

                     switch (alt5) {
                        case 1:
                           this.mWS();
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           this.match(61);
                           if (!this.state.failed) {
                              int exprStart469 = this.getCharIndex();
                              int exprStartLine469 = this.getLine();
                              int exprStartCharPos469 = this.getCharPositionInLine();
                              this.mATTR_VALUE_EXPR();
                              if (!this.state.failed) {
                                 expr = new CommonToken(this.input, 0, 0, exprStart469, this.getCharIndex() - 1);
                                 expr.setLine(exprStartLine469);
                                 expr.setCharPositionInLine(exprStartCharPos469);
                                 this.match(59);
                                 if (!this.state.failed) {
                                    if (this.resolveDynamicScope(x != null ? x.getText() : null) != null && this.resolveDynamicScope(x != null ? x.getText() : null).getAttribute(y != null ? y.getText() : null) != null) {
                                       if (this.state.backtracking == 1) {
                                          AttributeScope scope = this.resolveDynamicScope(x != null ? x.getText() : null);
                                          if (scope != null) {
                                             ST st = this.template("scopeSetAttributeRef");
                                             st.add("scope", x != null ? x.getText() : null);
                                             st.add("attr", scope.getAttribute(y != null ? y.getText() : null));
                                             st.add("expr", this.translateAction(expr != null ? expr.getText() : null));
                                          }
                                       }

                                       this.state.type = _type;
                                       this.state.channel = _channel;
                                    } else if (this.state.backtracking > 0) {
                                       this.state.failed = true;
                                    } else {
                                       throw new FailedPredicateException(this.input, "SET_DYNAMIC_SCOPE_ATTR", "resolveDynamicScope($x.text)!=null &&\r\n\t\t\t\t\t\t     resolveDynamicScope($x.text).getAttribute($y.text)!=null");
                                    }
                                 }
                              }
                           }
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mDYNAMIC_SCOPE_ATTR() throws RecognitionException {
      try {
         int _type = 9;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart504 = this.getCharIndex();
            int xStartLine504 = this.getLine();
            int xStartCharPos504 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart504, this.getCharIndex() - 1);
               x.setLine(xStartLine504);
               x.setCharPositionInLine(xStartCharPos504);
               this.match("::");
               if (!this.state.failed) {
                  int yStart510 = this.getCharIndex();
                  int yStartLine510 = this.getLine();
                  int yStartCharPos510 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart510, this.getCharIndex() - 1);
                     y.setLine(yStartLine510);
                     y.setCharPositionInLine(yStartCharPos510);
                     if (this.resolveDynamicScope(x != null ? x.getText() : null) != null && this.resolveDynamicScope(x != null ? x.getText() : null).getAttribute(y != null ? y.getText() : null) != null) {
                        if (this.state.backtracking == 1) {
                           AttributeScope scope = this.resolveDynamicScope(x != null ? x.getText() : null);
                           if (scope != null) {
                              ST st = this.template("scopeAttributeRef");
                              st.add("scope", x != null ? x.getText() : null);
                              st.add("attr", scope.getAttribute(y != null ? y.getText() : null));
                           }
                        }

                        this.state.type = _type;
                        this.state.channel = _channel;
                     } else if (this.state.backtracking > 0) {
                        this.state.failed = true;
                     } else {
                        throw new FailedPredicateException(this.input, "DYNAMIC_SCOPE_ATTR", "resolveDynamicScope($x.text)!=null &&\r\n\t\t\t\t\t\t     resolveDynamicScope($x.text).getAttribute($y.text)!=null");
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mERROR_SCOPED_XY() throws RecognitionException {
      try {
         int _type = 11;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart544 = this.getCharIndex();
            int xStartLine544 = this.getLine();
            int xStartCharPos544 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart544, this.getCharIndex() - 1);
               x.setLine(xStartLine544);
               x.setCharPositionInLine(xStartCharPos544);
               this.match("::");
               if (!this.state.failed) {
                  int yStart550 = this.getCharIndex();
                  int yStartLine550 = this.getLine();
                  int yStartCharPos550 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart550, this.getCharIndex() - 1);
                     y.setLine(yStartLine550);
                     y.setCharPositionInLine(yStartCharPos550);
                     if (this.state.backtracking == 1) {
                        this.chunks.add(this.getText());
                        this.generator.issueInvalidScopeError(x != null ? x.getText() : null, y != null ? y.getText() : null, this.enclosingRule, this.actionToken, this.outerAltNum);
                     }

                     this.state.type = _type;
                     this.state.channel = _channel;
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mDYNAMIC_NEGATIVE_INDEXED_SCOPE_ATTR() throws RecognitionException {
      try {
         int _type = 8;
         int _channel = 0;
         CommonToken x = null;
         CommonToken expr = null;
         CommonToken y = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart572 = this.getCharIndex();
            int xStartLine572 = this.getLine();
            int xStartCharPos572 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart572, this.getCharIndex() - 1);
               x.setLine(xStartLine572);
               x.setCharPositionInLine(xStartCharPos572);
               this.match(91);
               if (!this.state.failed) {
                  this.match(45);
                  if (!this.state.failed) {
                     int exprStart580 = this.getCharIndex();
                     int exprStartLine580 = this.getLine();
                     int exprStartCharPos580 = this.getCharPositionInLine();
                     this.mSCOPE_INDEX_EXPR();
                     if (!this.state.failed) {
                        expr = new CommonToken(this.input, 0, 0, exprStart580, this.getCharIndex() - 1);
                        expr.setLine(exprStartLine580);
                        expr.setCharPositionInLine(exprStartCharPos580);
                        this.match(93);
                        if (!this.state.failed) {
                           this.match("::");
                           if (!this.state.failed) {
                              int yStart588 = this.getCharIndex();
                              int yStartLine588 = this.getLine();
                              int yStartCharPos588 = this.getCharPositionInLine();
                              this.mID();
                              if (!this.state.failed) {
                                 y = new CommonToken(this.input, 0, 0, yStart588, this.getCharIndex() - 1);
                                 y.setLine(yStartLine588);
                                 y.setCharPositionInLine(yStartCharPos588);
                                 if (this.state.backtracking == 1) {
                                    ST st = this.template("scopeAttributeRef");
                                    st.add("scope", x != null ? x.getText() : null);
                                    st.add("attr", this.resolveDynamicScope(x != null ? x.getText() : null).getAttribute(y != null ? y.getText() : null));
                                    st.add("negIndex", expr != null ? expr.getText() : null);
                                 }

                                 this.state.type = _type;
                                 this.state.channel = _channel;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mDYNAMIC_ABSOLUTE_INDEXED_SCOPE_ATTR() throws RecognitionException {
      try {
         int _type = 7;
         int _channel = 0;
         CommonToken x = null;
         CommonToken expr = null;
         CommonToken y = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart612 = this.getCharIndex();
            int xStartLine612 = this.getLine();
            int xStartCharPos612 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart612, this.getCharIndex() - 1);
               x.setLine(xStartLine612);
               x.setCharPositionInLine(xStartCharPos612);
               this.match(91);
               if (!this.state.failed) {
                  int exprStart618 = this.getCharIndex();
                  int exprStartLine618 = this.getLine();
                  int exprStartCharPos618 = this.getCharPositionInLine();
                  this.mSCOPE_INDEX_EXPR();
                  if (!this.state.failed) {
                     expr = new CommonToken(this.input, 0, 0, exprStart618, this.getCharIndex() - 1);
                     expr.setLine(exprStartLine618);
                     expr.setCharPositionInLine(exprStartCharPos618);
                     this.match(93);
                     if (!this.state.failed) {
                        this.match("::");
                        if (!this.state.failed) {
                           int yStart626 = this.getCharIndex();
                           int yStartLine626 = this.getLine();
                           int yStartCharPos626 = this.getCharPositionInLine();
                           this.mID();
                           if (!this.state.failed) {
                              y = new CommonToken(this.input, 0, 0, yStart626, this.getCharIndex() - 1);
                              y.setLine(yStartLine626);
                              y.setCharPositionInLine(yStartCharPos626);
                              if (this.state.backtracking == 1) {
                                 ST st = this.template("scopeAttributeRef");
                                 st.add("scope", x != null ? x.getText() : null);
                                 st.add("attr", this.resolveDynamicScope(x != null ? x.getText() : null).getAttribute(y != null ? y.getText() : null));
                                 st.add("index", expr != null ? expr.getText() : null);
                              }

                              this.state.type = _type;
                              this.state.channel = _channel;
                           }
                        }
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mSCOPE_INDEX_EXPR() throws RecognitionException {
      try {
         int cnt6 = 0;

         while(true) {
            int alt6 = 2;
            int LA6_0 = this.input.LA(1);
            if (LA6_0 >= 0 && LA6_0 <= 92 || LA6_0 >= 94 && LA6_0 <= 65535) {
               alt6 = 1;
            }

            switch (alt6) {
               case 1:
                  if ((this.input.LA(1) < 0 || this.input.LA(1) > 92) && (this.input.LA(1) < 94 || this.input.LA(1) > 65535)) {
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
                  ++cnt6;
                  break;
               default:
                  if (cnt6 >= 1) {
                     return;
                  }

                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(6, this.input);
                  throw eee;
            }
         }
      } finally {
         ;
      }
   }

   public final void mISOLATED_DYNAMIC_SCOPE() throws RecognitionException {
      try {
         int _type = 18;
         int _channel = 0;
         CommonToken ID6 = null;
         this.match(36);
         if (!this.state.failed) {
            int ID6Start669 = this.getCharIndex();
            int ID6StartLine669 = this.getLine();
            int ID6StartCharPos669 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               ID6 = new CommonToken(this.input, 0, 0, ID6Start669, this.getCharIndex() - 1);
               ID6.setLine(ID6StartLine669);
               ID6.setCharPositionInLine(ID6StartCharPos669);
               if (this.resolveDynamicScope(ID6 != null ? ID6.getText() : null) == null) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                  } else {
                     throw new FailedPredicateException(this.input, "ISOLATED_DYNAMIC_SCOPE", "resolveDynamicScope($ID.text)!=null");
                  }
               } else {
                  if (this.state.backtracking == 1) {
                     ST st = this.template("isolatedDynamicScopeRef");
                     st.add("scope", ID6 != null ? ID6.getText() : null);
                  }

                  this.state.type = _type;
                  this.state.channel = _channel;
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mTEMPLATE_INSTANCE() throws RecognitionException {
      try {
         int _type = 33;
         int _channel = 0;
         this.match(37);
         if (!this.state.failed) {
            this.mID();
            if (!this.state.failed) {
               this.match(40);
               if (!this.state.failed) {
                  int alt11 = 2;
                  int LA11_0 = this.input.LA(1);
                  if (LA11_0 >= 9 && LA11_0 <= 10 || LA11_0 == 13 || LA11_0 == 32 || LA11_0 >= 65 && LA11_0 <= 90 || LA11_0 == 95 || LA11_0 >= 97 && LA11_0 <= 122) {
                     alt11 = 1;
                  }

                  switch (alt11) {
                     case 1:
                        int alt7 = 2;
                        int LA7_0 = this.input.LA(1);
                        if (LA7_0 >= 9 && LA7_0 <= 10 || LA7_0 == 13 || LA7_0 == 32) {
                           alt7 = 1;
                        }

                        switch (alt7) {
                           case 1:
                              this.mWS();
                              if (this.state.failed) {
                                 return;
                              }
                           default:
                              this.mARG();
                              if (this.state.failed) {
                                 return;
                              }
                        }

                        label280:
                        while(true) {
                           int alt10 = 2;
                           int LA10_0 = this.input.LA(1);
                           if (LA10_0 == 44) {
                              alt10 = 1;
                           }

                           switch (alt10) {
                              case 1:
                                 this.match(44);
                                 if (this.state.failed) {
                                    return;
                                 }

                                 int alt8 = 2;
                                 int LA8_0 = this.input.LA(1);
                                 if (LA8_0 >= 9 && LA8_0 <= 10 || LA8_0 == 13 || LA8_0 == 32) {
                                    alt8 = 1;
                                 }

                                 switch (alt8) {
                                    case 1:
                                       this.mWS();
                                       if (this.state.failed) {
                                          return;
                                       }
                                    default:
                                       this.mARG();
                                       if (this.state.failed) {
                                          return;
                                       }
                                       continue;
                                 }
                              default:
                                 alt10 = 2;
                                 LA10_0 = this.input.LA(1);
                                 if (LA10_0 >= 9 && LA10_0 <= 10 || LA10_0 == 13 || LA10_0 == 32) {
                                    alt10 = 1;
                                 }

                                 switch (alt10) {
                                    case 1:
                                       this.mWS();
                                       if (this.state.failed) {
                                          return;
                                       }
                                       break label280;
                                 }
                           }
                        }
                     default:
                        this.match(41);
                        if (!this.state.failed) {
                           if (this.state.backtracking == 1) {
                              String action = this.getText().substring(1, this.getText().length());
                              String ruleName = "<outside-of-rule>";
                              if (this.enclosingRule != null) {
                                 ruleName = this.enclosingRule.name;
                              }

                              ST st = this.generator.translateTemplateConstructor(ruleName, this.outerAltNum, this.actionToken, action);
                              if (st != null) {
                                 this.chunks.add(st);
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

   public final void mINDIRECT_TEMPLATE_INSTANCE() throws RecognitionException {
      try {
         int _type = 16;
         int _channel = 0;
         this.match(37);
         if (!this.state.failed) {
            this.match(40);
            if (!this.state.failed) {
               this.mACTION();
               if (!this.state.failed) {
                  this.match(41);
                  if (!this.state.failed) {
                     this.match(40);
                     if (!this.state.failed) {
                        int alt16 = 2;
                        int LA16_0 = this.input.LA(1);
                        if (LA16_0 >= 9 && LA16_0 <= 10 || LA16_0 == 13 || LA16_0 == 32 || LA16_0 >= 65 && LA16_0 <= 90 || LA16_0 == 95 || LA16_0 >= 97 && LA16_0 <= 122) {
                           alt16 = 1;
                        }

                        switch (alt16) {
                           case 1:
                              int alt12 = 2;
                              int LA12_0 = this.input.LA(1);
                              if (LA12_0 >= 9 && LA12_0 <= 10 || LA12_0 == 13 || LA12_0 == 32) {
                                 alt12 = 1;
                              }

                              switch (alt12) {
                                 case 1:
                                    this.mWS();
                                    if (this.state.failed) {
                                       return;
                                    }
                                 default:
                                    this.mARG();
                                    if (this.state.failed) {
                                       return;
                                    }
                              }

                              label283:
                              while(true) {
                                 int alt15 = 2;
                                 int LA15_0 = this.input.LA(1);
                                 if (LA15_0 == 44) {
                                    alt15 = 1;
                                 }

                                 switch (alt15) {
                                    case 1:
                                       this.match(44);
                                       if (this.state.failed) {
                                          return;
                                       }

                                       int alt13 = 2;
                                       int LA13_0 = this.input.LA(1);
                                       if (LA13_0 >= 9 && LA13_0 <= 10 || LA13_0 == 13 || LA13_0 == 32) {
                                          alt13 = 1;
                                       }

                                       switch (alt13) {
                                          case 1:
                                             this.mWS();
                                             if (this.state.failed) {
                                                return;
                                             }
                                          default:
                                             this.mARG();
                                             if (this.state.failed) {
                                                return;
                                             }
                                             continue;
                                       }
                                    default:
                                       alt15 = 2;
                                       LA15_0 = this.input.LA(1);
                                       if (LA15_0 >= 9 && LA15_0 <= 10 || LA15_0 == 13 || LA15_0 == 32) {
                                          alt15 = 1;
                                       }

                                       switch (alt15) {
                                          case 1:
                                             this.mWS();
                                             if (this.state.failed) {
                                                return;
                                             }
                                             break label283;
                                       }
                                 }
                              }
                           default:
                              this.match(41);
                              if (!this.state.failed) {
                                 if (this.state.backtracking == 1) {
                                    String action = this.getText().substring(1, this.getText().length());
                                    ST st = this.generator.translateTemplateConstructor(this.enclosingRule.name, this.outerAltNum, this.actionToken, action);
                                    this.chunks.add(st);
                                 }

                                 this.state.type = _type;
                                 this.state.channel = _channel;
                              }
                        }
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mARG() throws RecognitionException {
      try {
         this.mID();
         if (!this.state.failed) {
            this.match(61);
            if (!this.state.failed) {
               this.mACTION();
               if (!this.state.failed) {
                  ;
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mSET_EXPR_ATTRIBUTE() throws RecognitionException {
      try {
         int _type = 28;
         int _channel = 0;
         CommonToken a = null;
         CommonToken expr = null;
         CommonToken ID7 = null;
         this.match(37);
         if (!this.state.failed) {
            int aStart819 = this.getCharIndex();
            int aStartLine819 = this.getLine();
            int aStartCharPos819 = this.getCharPositionInLine();
            this.mACTION();
            if (!this.state.failed) {
               a = new CommonToken(this.input, 0, 0, aStart819, this.getCharIndex() - 1);
               a.setLine(aStartLine819);
               a.setCharPositionInLine(aStartCharPos819);
               this.match(46);
               if (!this.state.failed) {
                  int ID7Start823 = this.getCharIndex();
                  int ID7StartLine823 = this.getLine();
                  int ID7StartCharPos823 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     ID7 = new CommonToken(this.input, 0, 0, ID7Start823, this.getCharIndex() - 1);
                     ID7.setLine(ID7StartLine823);
                     ID7.setCharPositionInLine(ID7StartCharPos823);
                     int alt17 = 2;
                     int LA17_0 = this.input.LA(1);
                     if (LA17_0 >= 9 && LA17_0 <= 10 || LA17_0 == 13 || LA17_0 == 32) {
                        alt17 = 1;
                     }

                     switch (alt17) {
                        case 1:
                           this.mWS();
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           this.match(61);
                           if (!this.state.failed) {
                              int exprStart832 = this.getCharIndex();
                              int exprStartLine832 = this.getLine();
                              int exprStartCharPos832 = this.getCharPositionInLine();
                              this.mATTR_VALUE_EXPR();
                              if (!this.state.failed) {
                                 expr = new CommonToken(this.input, 0, 0, exprStart832, this.getCharIndex() - 1);
                                 expr.setLine(exprStartLine832);
                                 expr.setCharPositionInLine(exprStartCharPos832);
                                 this.match(59);
                                 if (!this.state.failed) {
                                    if (this.state.backtracking == 1) {
                                       ST st = this.template("actionSetAttribute");
                                       String action = a != null ? a.getText() : null;
                                       action = action.substring(1, action.length() - 1);
                                       st.add("st", this.translateAction(action));
                                       st.add("attrName", ID7 != null ? ID7.getText() : null);
                                       st.add("expr", this.translateAction(expr != null ? expr.getText() : null));
                                    }

                                    this.state.type = _type;
                                    this.state.channel = _channel;
                                 }
                              }
                           }
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mSET_ATTRIBUTE() throws RecognitionException {
      try {
         int _type = 25;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         CommonToken expr = null;
         this.match(37);
         if (!this.state.failed) {
            int xStart859 = this.getCharIndex();
            int xStartLine859 = this.getLine();
            int xStartCharPos859 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart859, this.getCharIndex() - 1);
               x.setLine(xStartLine859);
               x.setCharPositionInLine(xStartCharPos859);
               this.match(46);
               if (!this.state.failed) {
                  int yStart865 = this.getCharIndex();
                  int yStartLine865 = this.getLine();
                  int yStartCharPos865 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart865, this.getCharIndex() - 1);
                     y.setLine(yStartLine865);
                     y.setCharPositionInLine(yStartCharPos865);
                     int alt18 = 2;
                     int LA18_0 = this.input.LA(1);
                     if (LA18_0 >= 9 && LA18_0 <= 10 || LA18_0 == 13 || LA18_0 == 32) {
                        alt18 = 1;
                     }

                     switch (alt18) {
                        case 1:
                           this.mWS();
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           this.match(61);
                           if (!this.state.failed) {
                              int exprStart874 = this.getCharIndex();
                              int exprStartLine874 = this.getLine();
                              int exprStartCharPos874 = this.getCharPositionInLine();
                              this.mATTR_VALUE_EXPR();
                              if (!this.state.failed) {
                                 expr = new CommonToken(this.input, 0, 0, exprStart874, this.getCharIndex() - 1);
                                 expr.setLine(exprStartLine874);
                                 expr.setCharPositionInLine(exprStartCharPos874);
                                 this.match(59);
                                 if (!this.state.failed) {
                                    if (this.state.backtracking == 1) {
                                       ST st = this.template("actionSetAttribute");
                                       st.add("st", x != null ? x.getText() : null);
                                       st.add("attrName", y != null ? y.getText() : null);
                                       st.add("expr", this.translateAction(expr != null ? expr.getText() : null));
                                    }

                                    this.state.type = _type;
                                    this.state.channel = _channel;
                                 }
                              }
                           }
                     }
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mATTR_VALUE_EXPR() throws RecognitionException {
      try {
         if (this.input.LA(1) >= 0 && this.input.LA(1) <= 60 || this.input.LA(1) >= 62 && this.input.LA(1) <= 65535) {
            this.input.consume();
            this.state.failed = false;

            while(true) {
               int alt19 = 2;
               int LA19_0 = this.input.LA(1);
               if (LA19_0 >= 0 && LA19_0 <= 58 || LA19_0 >= 60 && LA19_0 <= 65535) {
                  alt19 = 1;
               }

               switch (alt19) {
                  case 1:
                     if ((this.input.LA(1) < 0 || this.input.LA(1) > 58) && (this.input.LA(1) < 60 || this.input.LA(1) > 65535)) {
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

   public final void mTEMPLATE_EXPR() throws RecognitionException {
      try {
         int _type = 32;
         int _channel = 0;
         CommonToken a = null;
         this.match(37);
         if (!this.state.failed) {
            int aStart923 = this.getCharIndex();
            int aStartLine923 = this.getLine();
            int aStartCharPos923 = this.getCharPositionInLine();
            this.mACTION();
            if (!this.state.failed) {
               a = new CommonToken(this.input, 0, 0, aStart923, this.getCharIndex() - 1);
               a.setLine(aStartLine923);
               a.setCharPositionInLine(aStartCharPos923);
               if (this.state.backtracking == 1) {
                  ST st = this.template("actionStringConstructor");
                  String action = a != null ? a.getText() : null;
                  action = action.substring(1, action.length() - 1);
                  st.add("stringExpr", this.translateAction(action));
               }

               this.state.type = _type;
               this.state.channel = _channel;
            }
         }
      } finally {
         ;
      }
   }

   public final void mACTION() throws RecognitionException {
      try {
         this.match(123);
         if (!this.state.failed) {
            do {
               int alt20 = 2;
               int LA20_0 = this.input.LA(1);
               if (LA20_0 == 125) {
                  alt20 = 2;
               } else if (LA20_0 >= 0 && LA20_0 <= 124 || LA20_0 >= 126 && LA20_0 <= 65535) {
                  alt20 = 1;
               }

               switch (alt20) {
                  case 1:
                     this.matchAny();
                     break;
                  default:
                     this.match(125);
                     if (this.state.failed) {
                        return;
                     }

                     return;
               }
            } while(!this.state.failed);

         }
      } finally {
         ;
      }
   }

   public final void mESC() throws RecognitionException {
      try {
         int _type = 14;
         int _channel = 0;
         int alt21 = true;
         int LA21_0 = this.input.LA(1);
         if (LA21_0 != 92) {
            if (this.state.backtracking > 0) {
               this.state.failed = true;
            } else {
               NoViableAltException nvae = new NoViableAltException("", 21, 0, this.input);
               throw nvae;
            }
         } else {
            int LA21_1 = this.input.LA(2);
            byte alt21;
            if (LA21_1 == 36) {
               alt21 = 1;
            } else if (LA21_1 == 37) {
               alt21 = 2;
            } else {
               if ((LA21_1 < 0 || LA21_1 > 35) && (LA21_1 < 38 || LA21_1 > 65535)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  int nvaeMark = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 21, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               alt21 = 3;
            }

            switch (alt21) {
               case 1:
                  this.match(92);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(36);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 1) {
                     this.chunks.add("$");
                  }
                  break;
               case 2:
                  this.match(92);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(37);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 1) {
                     this.chunks.add("%");
                  }
                  break;
               case 3:
                  this.match(92);
                  if (this.state.failed) {
                     return;
                  }

                  if ((this.input.LA(1) < 0 || this.input.LA(1) > 35) && (this.input.LA(1) < 38 || this.input.LA(1) > 65535)) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  this.state.failed = false;
                  if (this.state.backtracking == 1) {
                     this.chunks.add(this.getText());
                  }
            }

            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mERROR_XY() throws RecognitionException {
      try {
         int _type = 13;
         int _channel = 0;
         CommonToken x = null;
         CommonToken y = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart1023 = this.getCharIndex();
            int xStartLine1023 = this.getLine();
            int xStartCharPos1023 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart1023, this.getCharIndex() - 1);
               x.setLine(xStartLine1023);
               x.setCharPositionInLine(xStartCharPos1023);
               this.match(46);
               if (!this.state.failed) {
                  int yStart1029 = this.getCharIndex();
                  int yStartLine1029 = this.getLine();
                  int yStartCharPos1029 = this.getCharPositionInLine();
                  this.mID();
                  if (!this.state.failed) {
                     y = new CommonToken(this.input, 0, 0, yStart1029, this.getCharIndex() - 1);
                     y.setLine(yStartLine1029);
                     y.setCharPositionInLine(yStartCharPos1029);
                     if (this.state.backtracking == 1) {
                        this.chunks.add(this.getText());
                        this.generator.issueInvalidAttributeError(x != null ? x.getText() : null, y != null ? y.getText() : null, this.enclosingRule, this.actionToken, this.outerAltNum);
                     }

                     this.state.type = _type;
                     this.state.channel = _channel;
                  }
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mERROR_X() throws RecognitionException {
      try {
         int _type = 12;
         int _channel = 0;
         CommonToken x = null;
         this.match(36);
         if (!this.state.failed) {
            int xStart1049 = this.getCharIndex();
            int xStartLine1049 = this.getLine();
            int xStartCharPos1049 = this.getCharPositionInLine();
            this.mID();
            if (!this.state.failed) {
               x = new CommonToken(this.input, 0, 0, xStart1049, this.getCharIndex() - 1);
               x.setLine(xStartLine1049);
               x.setCharPositionInLine(xStartCharPos1049);
               if (this.state.backtracking == 1) {
                  this.chunks.add(this.getText());
                  this.generator.issueInvalidAttributeError(x != null ? x.getText() : null, this.enclosingRule, this.actionToken, this.outerAltNum);
               }

               this.state.type = _type;
               this.state.channel = _channel;
            }
         }
      } finally {
         ;
      }
   }

   public final void mUNKNOWN_SYNTAX() throws RecognitionException {
      try {
         int _type = 36;
         int _channel = 0;
         int alt23 = true;
         int LA23_0 = this.input.LA(1);
         byte alt23;
         if (LA23_0 == 36) {
            alt23 = 1;
         } else {
            if (LA23_0 != 37) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 23, 0, this.input);
               throw nvae;
            }

            alt23 = 2;
         }

         switch (alt23) {
            case 1:
               this.match(36);
               if (this.state.failed) {
                  return;
               }

               if (this.state.backtracking == 1) {
                  this.chunks.add(this.getText());
               }
               break;
            case 2:
               this.match(37);
               if (this.state.failed) {
                  return;
               }

               label177:
               while(true) {
                  int alt22 = 9;
                  switch (this.input.LA(1)) {
                     case 34:
                        alt22 = 8;
                     case 35:
                     case 36:
                     case 37:
                     case 38:
                     case 39:
                     case 42:
                     case 43:
                     case 45:
                     case 47:
                     case 48:
                     case 49:
                     case 50:
                     case 51:
                     case 52:
                     case 53:
                     case 54:
                     case 55:
                     case 56:
                     case 57:
                     case 58:
                     case 59:
                     case 60:
                     case 61:
                     case 62:
                     case 63:
                     case 64:
                     case 91:
                     case 92:
                     case 93:
                     case 94:
                     case 96:
                     case 124:
                     default:
                        break;
                     case 40:
                        alt22 = 3;
                        break;
                     case 41:
                        alt22 = 4;
                        break;
                     case 44:
                        alt22 = 5;
                        break;
                     case 46:
                        alt22 = 2;
                        break;
                     case 65:
                     case 66:
                     case 67:
                     case 68:
                     case 69:
                     case 70:
                     case 71:
                     case 72:
                     case 73:
                     case 74:
                     case 75:
                     case 76:
                     case 77:
                     case 78:
                     case 79:
                     case 80:
                     case 81:
                     case 82:
                     case 83:
                     case 84:
                     case 85:
                     case 86:
                     case 87:
                     case 88:
                     case 89:
                     case 90:
                     case 95:
                     case 97:
                     case 98:
                     case 99:
                     case 100:
                     case 101:
                     case 102:
                     case 103:
                     case 104:
                     case 105:
                     case 106:
                     case 107:
                     case 108:
                     case 109:
                     case 110:
                     case 111:
                     case 112:
                     case 113:
                     case 114:
                     case 115:
                     case 116:
                     case 117:
                     case 118:
                     case 119:
                     case 120:
                     case 121:
                     case 122:
                        alt22 = 1;
                        break;
                     case 123:
                        alt22 = 6;
                        break;
                     case 125:
                        alt22 = 7;
                  }

                  switch (alt22) {
                     case 1:
                        this.mID();
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     case 2:
                        this.match(46);
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     case 3:
                        this.match(40);
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     case 4:
                        this.match(41);
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     case 5:
                        this.match(44);
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     case 6:
                        this.match(123);
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     case 7:
                        this.match(125);
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     case 8:
                        this.match(34);
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     default:
                        if (this.state.backtracking == 1) {
                           this.chunks.add(this.getText());
                           ErrorManager.grammarError(146, this.grammar, this.actionToken, this.getText());
                        }
                        break label177;
                  }
               }
         }

         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mTEXT() throws RecognitionException {
      try {
         int _type = 34;
         int _channel = 0;
         int cnt24 = 0;

         while(true) {
            int alt24 = 2;
            int LA24_0 = this.input.LA(1);
            if (LA24_0 >= 0 && LA24_0 <= 35 || LA24_0 >= 38 && LA24_0 <= 91 || LA24_0 >= 93 && LA24_0 <= 65535) {
               alt24 = 1;
            }

            switch (alt24) {
               case 1:
                  if ((this.input.LA(1) < 0 || this.input.LA(1) > 35) && (this.input.LA(1) < 38 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 65535)) {
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
                  ++cnt24;
                  break;
               default:
                  if (cnt24 >= 1) {
                     if (this.state.backtracking == 1) {
                        this.chunks.add(this.getText());
                     }

                     this.state.type = _type;
                     this.state.channel = _channel;
                     return;
                  }

                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(24, this.input);
                  throw eee;
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
               int alt25 = 2;
               int LA25_0 = this.input.LA(1);
               if (LA25_0 >= 48 && LA25_0 <= 57 || LA25_0 >= 65 && LA25_0 <= 90 || LA25_0 == 95 || LA25_0 >= 97 && LA25_0 <= 122) {
                  alt25 = 1;
               }

               switch (alt25) {
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

   public final void mINT() throws RecognitionException {
      try {
         int cnt26 = 0;

         while(true) {
            int alt26 = 2;
            int LA26_0 = this.input.LA(1);
            if (LA26_0 >= 48 && LA26_0 <= 57) {
               alt26 = 1;
            }

            switch (alt26) {
               case 1:
                  if (this.input.LA(1) < 48 || this.input.LA(1) > 57) {
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
                  ++cnt26;
                  break;
               default:
                  if (cnt26 >= 1) {
                     return;
                  }

                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(26, this.input);
                  throw eee;
            }
         }
      } finally {
         ;
      }
   }

   public final void mWS() throws RecognitionException {
      try {
         int cnt27 = 0;

         while(true) {
            int alt27 = 2;
            int LA27_0 = this.input.LA(1);
            if (LA27_0 >= 9 && LA27_0 <= 10 || LA27_0 == 13 || LA27_0 == 32) {
               alt27 = 1;
            }

            switch (alt27) {
               case 1:
                  if ((this.input.LA(1) < 9 || this.input.LA(1) > 10) && this.input.LA(1) != 13 && this.input.LA(1) != 32) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  this.state.failed = false;
                  ++cnt27;
                  break;
               default:
                  if (cnt27 >= 1) {
                     return;
                  }

                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(27, this.input);
                  throw eee;
            }
         }
      } finally {
         ;
      }
   }

   public void mTokens() throws RecognitionException {
      int alt28 = true;
      int LA28_0 = this.input.LA(1);
      int nvaeMark;
      NoViableAltException nvae;
      byte alt28;
      int LA28_22;
      if (LA28_0 == 36) {
         LA28_22 = this.input.LA(2);
         if (this.synpred1_ActionTranslator()) {
            alt28 = 1;
         } else if (this.synpred2_ActionTranslator()) {
            alt28 = 2;
         } else if (this.synpred3_ActionTranslator()) {
            alt28 = 3;
         } else if (this.synpred4_ActionTranslator()) {
            alt28 = 4;
         } else if (this.synpred5_ActionTranslator()) {
            alt28 = 5;
         } else if (this.synpred6_ActionTranslator()) {
            alt28 = 6;
         } else if (this.synpred7_ActionTranslator()) {
            alt28 = 7;
         } else if (this.synpred8_ActionTranslator()) {
            alt28 = 8;
         } else if (this.synpred9_ActionTranslator()) {
            alt28 = 9;
         } else if (this.synpred10_ActionTranslator()) {
            alt28 = 10;
         } else if (this.synpred11_ActionTranslator()) {
            alt28 = 11;
         } else if (this.synpred12_ActionTranslator()) {
            alt28 = 12;
         } else if (this.synpred13_ActionTranslator()) {
            alt28 = 13;
         } else if (this.synpred14_ActionTranslator()) {
            alt28 = 14;
         } else if (this.synpred15_ActionTranslator()) {
            alt28 = 15;
         } else if (this.synpred16_ActionTranslator()) {
            alt28 = 16;
         } else if (this.synpred17_ActionTranslator()) {
            alt28 = 17;
         } else if (this.synpred24_ActionTranslator()) {
            alt28 = 24;
         } else if (this.synpred25_ActionTranslator()) {
            alt28 = 25;
         } else {
            if (!this.synpred26_ActionTranslator()) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               nvaeMark = this.input.mark();

               try {
                  this.input.consume();
                  nvae = new NoViableAltException("", 28, 1, this.input);
                  throw nvae;
               } finally {
                  this.input.rewind(nvaeMark);
               }
            }

            alt28 = 26;
         }
      } else if (LA28_0 == 37) {
         LA28_22 = this.input.LA(2);
         if (this.synpred18_ActionTranslator()) {
            alt28 = 18;
         } else if (this.synpred19_ActionTranslator()) {
            alt28 = 19;
         } else if (this.synpred20_ActionTranslator()) {
            alt28 = 20;
         } else if (this.synpred21_ActionTranslator()) {
            alt28 = 21;
         } else if (this.synpred22_ActionTranslator()) {
            alt28 = 22;
         } else {
            if (!this.synpred26_ActionTranslator()) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               nvaeMark = this.input.mark();

               try {
                  this.input.consume();
                  nvae = new NoViableAltException("", 28, 22, this.input);
                  throw nvae;
               } finally {
                  this.input.rewind(nvaeMark);
               }
            }

            alt28 = 26;
         }
      } else if (LA28_0 == 92) {
         alt28 = 23;
      } else {
         if ((LA28_0 < 0 || LA28_0 > 35) && (LA28_0 < 38 || LA28_0 > 91) && (LA28_0 < 93 || LA28_0 > 65535)) {
            if (this.state.backtracking > 0) {
               this.state.failed = true;
               return;
            } else {
               NoViableAltException nvae = new NoViableAltException("", 28, 0, this.input);
               throw nvae;
            }
         }

         alt28 = 27;
      }

      switch (alt28) {
         case 1:
            this.mSET_ENCLOSING_RULE_SCOPE_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 2:
            this.mENCLOSING_RULE_SCOPE_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 3:
            this.mSET_TOKEN_SCOPE_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 4:
            this.mTOKEN_SCOPE_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 5:
            this.mSET_RULE_SCOPE_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 6:
            this.mRULE_SCOPE_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 7:
            this.mLABEL_REF();
            if (this.state.failed) {
               return;
            }
            break;
         case 8:
            this.mISOLATED_TOKEN_REF();
            if (this.state.failed) {
               return;
            }
            break;
         case 9:
            this.mISOLATED_LEXER_RULE_REF();
            if (this.state.failed) {
               return;
            }
            break;
         case 10:
            this.mSET_LOCAL_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 11:
            this.mLOCAL_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 12:
            this.mSET_DYNAMIC_SCOPE_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 13:
            this.mDYNAMIC_SCOPE_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 14:
            this.mERROR_SCOPED_XY();
            if (this.state.failed) {
               return;
            }
            break;
         case 15:
            this.mDYNAMIC_NEGATIVE_INDEXED_SCOPE_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 16:
            this.mDYNAMIC_ABSOLUTE_INDEXED_SCOPE_ATTR();
            if (this.state.failed) {
               return;
            }
            break;
         case 17:
            this.mISOLATED_DYNAMIC_SCOPE();
            if (this.state.failed) {
               return;
            }
            break;
         case 18:
            this.mTEMPLATE_INSTANCE();
            if (this.state.failed) {
               return;
            }
            break;
         case 19:
            this.mINDIRECT_TEMPLATE_INSTANCE();
            if (this.state.failed) {
               return;
            }
            break;
         case 20:
            this.mSET_EXPR_ATTRIBUTE();
            if (this.state.failed) {
               return;
            }
            break;
         case 21:
            this.mSET_ATTRIBUTE();
            if (this.state.failed) {
               return;
            }
            break;
         case 22:
            this.mTEMPLATE_EXPR();
            if (this.state.failed) {
               return;
            }
            break;
         case 23:
            this.mESC();
            if (this.state.failed) {
               return;
            }
            break;
         case 24:
            this.mERROR_XY();
            if (this.state.failed) {
               return;
            }
            break;
         case 25:
            this.mERROR_X();
            if (this.state.failed) {
               return;
            }
            break;
         case 26:
            this.mUNKNOWN_SYNTAX();
            if (this.state.failed) {
               return;
            }
            break;
         case 27:
            this.mTEXT();
            if (this.state.failed) {
               return;
            }
      }

   }

   public final void synpred1_ActionTranslator_fragment() throws RecognitionException {
      this.mSET_ENCLOSING_RULE_SCOPE_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred2_ActionTranslator_fragment() throws RecognitionException {
      this.mENCLOSING_RULE_SCOPE_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred3_ActionTranslator_fragment() throws RecognitionException {
      this.mSET_TOKEN_SCOPE_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred4_ActionTranslator_fragment() throws RecognitionException {
      this.mTOKEN_SCOPE_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred5_ActionTranslator_fragment() throws RecognitionException {
      this.mSET_RULE_SCOPE_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred6_ActionTranslator_fragment() throws RecognitionException {
      this.mRULE_SCOPE_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred7_ActionTranslator_fragment() throws RecognitionException {
      this.mLABEL_REF();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred8_ActionTranslator_fragment() throws RecognitionException {
      this.mISOLATED_TOKEN_REF();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred9_ActionTranslator_fragment() throws RecognitionException {
      this.mISOLATED_LEXER_RULE_REF();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred10_ActionTranslator_fragment() throws RecognitionException {
      this.mSET_LOCAL_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred11_ActionTranslator_fragment() throws RecognitionException {
      this.mLOCAL_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred12_ActionTranslator_fragment() throws RecognitionException {
      this.mSET_DYNAMIC_SCOPE_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred13_ActionTranslator_fragment() throws RecognitionException {
      this.mDYNAMIC_SCOPE_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred14_ActionTranslator_fragment() throws RecognitionException {
      this.mERROR_SCOPED_XY();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred15_ActionTranslator_fragment() throws RecognitionException {
      this.mDYNAMIC_NEGATIVE_INDEXED_SCOPE_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred16_ActionTranslator_fragment() throws RecognitionException {
      this.mDYNAMIC_ABSOLUTE_INDEXED_SCOPE_ATTR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred17_ActionTranslator_fragment() throws RecognitionException {
      this.mISOLATED_DYNAMIC_SCOPE();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred18_ActionTranslator_fragment() throws RecognitionException {
      this.mTEMPLATE_INSTANCE();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred19_ActionTranslator_fragment() throws RecognitionException {
      this.mINDIRECT_TEMPLATE_INSTANCE();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred20_ActionTranslator_fragment() throws RecognitionException {
      this.mSET_EXPR_ATTRIBUTE();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred21_ActionTranslator_fragment() throws RecognitionException {
      this.mSET_ATTRIBUTE();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred22_ActionTranslator_fragment() throws RecognitionException {
      this.mTEMPLATE_EXPR();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred24_ActionTranslator_fragment() throws RecognitionException {
      this.mERROR_XY();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred25_ActionTranslator_fragment() throws RecognitionException {
      this.mERROR_X();
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred26_ActionTranslator_fragment() throws RecognitionException {
      this.mUNKNOWN_SYNTAX();
      if (!this.state.failed) {
         ;
      }
   }

   public final boolean synpred18_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred18_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred19_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred19_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred16_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred16_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred11_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred11_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred24_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred24_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred12_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred12_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred9_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred9_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred17_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred17_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred4_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred4_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred13_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred13_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred20_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred20_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred21_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred21_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred6_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred6_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred2_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred2_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred3_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred3_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred10_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred10_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred5_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred5_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred14_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred14_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred25_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred25_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred26_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred26_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred22_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred22_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred7_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred7_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred1_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred1_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred8_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred8_ActionTranslator_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred15_ActionTranslator() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred15_ActionTranslator_fragment();
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
