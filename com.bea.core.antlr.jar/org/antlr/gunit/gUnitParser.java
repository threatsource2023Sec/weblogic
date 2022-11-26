package org.antlr.gunit;

import java.util.Stack;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

public class gUnitParser extends Parser {
   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTION", "AST", "CHAR_LITERAL", "DOC_COMMENT", "ESC", "EXT", "FAIL", "ML_COMMENT", "ML_STRING", "NESTED_ACTION", "NESTED_AST", "NESTED_RETVAL", "OK", "OPTIONS", "RETVAL", "RULE_REF", "SL_COMMENT", "STRING", "STRING_LITERAL", "TOKEN_REF", "WS", "XDIGIT", "'->'", "':'", "';'", "'='", "'@header'", "'gunit'", "'returns'", "'walks'", "'}'"};
   public static final int EOF = -1;
   public static final int T__26 = 26;
   public static final int T__27 = 27;
   public static final int T__28 = 28;
   public static final int T__29 = 29;
   public static final int T__30 = 30;
   public static final int T__31 = 31;
   public static final int T__32 = 32;
   public static final int T__33 = 33;
   public static final int T__34 = 34;
   public static final int ACTION = 4;
   public static final int AST = 5;
   public static final int CHAR_LITERAL = 6;
   public static final int DOC_COMMENT = 7;
   public static final int ESC = 8;
   public static final int EXT = 9;
   public static final int FAIL = 10;
   public static final int ML_COMMENT = 11;
   public static final int ML_STRING = 12;
   public static final int NESTED_ACTION = 13;
   public static final int NESTED_AST = 14;
   public static final int NESTED_RETVAL = 15;
   public static final int OK = 16;
   public static final int OPTIONS = 17;
   public static final int RETVAL = 18;
   public static final int RULE_REF = 19;
   public static final int SL_COMMENT = 20;
   public static final int STRING = 21;
   public static final int STRING_LITERAL = 22;
   public static final int TOKEN_REF = 23;
   public static final int WS = 24;
   public static final int XDIGIT = 25;
   public GrammarInfo grammarInfo;
   protected Stack testsuite_stack;
   public static final BitSet FOLLOW_31_in_gUnitDef60 = new BitSet(new long[]{8912896L});
   public static final BitSet FOLLOW_id_in_gUnitDef64 = new BitSet(new long[]{8858370048L});
   public static final BitSet FOLLOW_33_in_gUnitDef67 = new BitSet(new long[]{8912896L});
   public static final BitSet FOLLOW_id_in_gUnitDef71 = new BitSet(new long[]{268435456L});
   public static final BitSet FOLLOW_28_in_gUnitDef75 = new BitSet(new long[]{1082785794L});
   public static final BitSet FOLLOW_optionsSpec_in_gUnitDef84 = new BitSet(new long[]{1082654722L});
   public static final BitSet FOLLOW_header_in_gUnitDef87 = new BitSet(new long[]{8912898L});
   public static final BitSet FOLLOW_testsuite_in_gUnitDef90 = new BitSet(new long[]{8912898L});
   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec104 = new BitSet(new long[]{8912896L});
   public static final BitSet FOLLOW_option_in_optionsSpec107 = new BitSet(new long[]{268435456L});
   public static final BitSet FOLLOW_28_in_optionsSpec109 = new BitSet(new long[]{17188782080L});
   public static final BitSet FOLLOW_34_in_optionsSpec113 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_option124 = new BitSet(new long[]{536870912L});
   public static final BitSet FOLLOW_29_in_option126 = new BitSet(new long[]{8912896L});
   public static final BitSet FOLLOW_treeAdaptor_in_option128 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_treeAdaptor144 = new BitSet(new long[]{514L});
   public static final BitSet FOLLOW_EXT_in_treeAdaptor146 = new BitSet(new long[]{514L});
   public static final BitSet FOLLOW_30_in_header157 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_header159 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_RULE_REF_in_testsuite190 = new BitSet(new long[]{8724152320L});
   public static final BitSet FOLLOW_33_in_testsuite193 = new BitSet(new long[]{524288L});
   public static final BitSet FOLLOW_RULE_REF_in_testsuite197 = new BitSet(new long[]{134217728L});
   public static final BitSet FOLLOW_TOKEN_REF_in_testsuite213 = new BitSet(new long[]{134217728L});
   public static final BitSet FOLLOW_27_in_testsuite227 = new BitSet(new long[]{11014144L});
   public static final BitSet FOLLOW_testcase_in_testsuite231 = new BitSet(new long[]{11014146L});
   public static final BitSet FOLLOW_input_in_testcase249 = new BitSet(new long[]{4362142720L});
   public static final BitSet FOLLOW_expect_in_testcase251 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_in_input278 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ML_STRING_in_input288 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_file_in_input297 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OK_in_expect317 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FAIL_in_expect324 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_32_in_expect331 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_RETVAL_in_expect333 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_26_in_expect340 = new BitSet(new long[]{2101296L});
   public static final BitSet FOLLOW_output_in_expect342 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_in_output359 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ML_STRING_in_output369 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_AST_in_output376 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ACTION_in_output383 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_file401 = new BitSet(new long[]{514L});
   public static final BitSet FOLLOW_EXT_in_file403 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_id422 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_RULE_REF_in_id429 = new BitSet(new long[]{2L});

   public Parser[] getDelegates() {
      return new Parser[0];
   }

   public gUnitParser(TokenStream input) {
      this(input, new RecognizerSharedState());
   }

   public gUnitParser(TokenStream input, RecognizerSharedState state) {
      super(input, state);
      this.testsuite_stack = new Stack();
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\antlr\\gunit\\gUnit.g";
   }

   public gUnitParser(TokenStream input, GrammarInfo grammarInfo) {
      super(input);
      this.testsuite_stack = new Stack();
      this.grammarInfo = grammarInfo;
   }

   public final void gUnitDef() throws RecognitionException {
      ParserRuleReturnScope g1 = null;
      ParserRuleReturnScope g2 = null;

      try {
         this.match(this.input, 31, FOLLOW_31_in_gUnitDef60);
         this.pushFollow(FOLLOW_id_in_gUnitDef64);
         g1 = this.id();
         --this.state._fsp;
         int alt1 = 2;
         int LA1_0 = this.input.LA(1);
         if (LA1_0 == 33) {
            alt1 = 1;
         }

         switch (alt1) {
            case 1:
               this.match(this.input, 33, FOLLOW_33_in_gUnitDef67);
               this.pushFollow(FOLLOW_id_in_gUnitDef71);
               g2 = this.id();
               --this.state._fsp;
            default:
               this.match(this.input, 28, FOLLOW_28_in_gUnitDef75);
               if ((g2 != null ? this.input.toString(g2.start, g2.stop) : null) != null) {
                  this.grammarInfo.setGrammarName(g2 != null ? this.input.toString(g2.start, g2.stop) : null);
                  this.grammarInfo.setTreeGrammarName(g1 != null ? this.input.toString(g1.start, g1.stop) : null);
               } else {
                  this.grammarInfo.setGrammarName(g1 != null ? this.input.toString(g1.start, g1.stop) : null);
               }

               int alt2 = 2;
               int LA2_0 = this.input.LA(1);
               if (LA2_0 == 17) {
                  alt2 = 1;
               }

               switch (alt2) {
                  case 1:
                     this.pushFollow(FOLLOW_optionsSpec_in_gUnitDef84);
                     this.optionsSpec();
                     --this.state._fsp;
                  default:
                     int alt3 = 2;
                     int LA3_0 = this.input.LA(1);
                     if (LA3_0 == 30) {
                        alt3 = 1;
                     }

                     switch (alt3) {
                        case 1:
                           this.pushFollow(FOLLOW_header_in_gUnitDef87);
                           this.header();
                           --this.state._fsp;
                     }
               }
         }

         while(true) {
            int alt4 = 2;
            int LA4_0 = this.input.LA(1);
            if (LA4_0 == 19 || LA4_0 == 23) {
               alt4 = 1;
            }

            switch (alt4) {
               case 1:
                  this.pushFollow(FOLLOW_testsuite_in_gUnitDef90);
                  this.testsuite();
                  --this.state._fsp;
                  break;
               default:
                  return;
            }
         }
      } catch (RecognitionException var14) {
         this.reportError(var14);
         this.recover(this.input, var14);
      } finally {
         ;
      }
   }

   public final void optionsSpec() throws RecognitionException {
      try {
         this.match(this.input, 17, FOLLOW_OPTIONS_in_optionsSpec104);
         int cnt5 = 0;

         while(true) {
            int alt5 = 2;
            int LA5_0 = this.input.LA(1);
            if (LA5_0 == 19 || LA5_0 == 23) {
               alt5 = 1;
            }

            switch (alt5) {
               case 1:
                  this.pushFollow(FOLLOW_option_in_optionsSpec107);
                  this.option();
                  --this.state._fsp;
                  this.match(this.input, 28, FOLLOW_28_in_optionsSpec109);
                  ++cnt5;
                  break;
               default:
                  if (cnt5 < 1) {
                     EarlyExitException eee = new EarlyExitException(5, this.input);
                     throw eee;
                  }

                  this.match(this.input, 34, FOLLOW_34_in_optionsSpec113);
                  return;
            }
         }
      } catch (RecognitionException var8) {
         this.reportError(var8);
         this.recover(this.input, var8);
      } finally {
         ;
      }
   }

   public final option_return option() throws RecognitionException {
      option_return retval = new option_return();
      retval.start = this.input.LT(1);
      ParserRuleReturnScope id1 = null;
      ParserRuleReturnScope treeAdaptor2 = null;

      try {
         try {
            this.pushFollow(FOLLOW_id_in_option124);
            id1 = this.id();
            --this.state._fsp;
            this.match(this.input, 29, FOLLOW_29_in_option126);
            this.pushFollow(FOLLOW_treeAdaptor_in_option128);
            treeAdaptor2 = this.treeAdaptor();
            --this.state._fsp;
            if ((id1 != null ? this.input.toString(id1.start, id1.stop) : null).equals("TreeAdaptor")) {
               this.grammarInfo.setAdaptor(treeAdaptor2 != null ? this.input.toString(treeAdaptor2.start, treeAdaptor2.stop) : null);
            } else {
               System.err.println("Invalid option detected: " + this.input.toString(retval.start, this.input.LT(-1)));
            }

            retval.stop = this.input.LT(-1);
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final treeAdaptor_return treeAdaptor() throws RecognitionException {
      treeAdaptor_return retval = new treeAdaptor_return();
      retval.start = this.input.LT(1);

      try {
         this.pushFollow(FOLLOW_id_in_treeAdaptor144);
         this.id();
         --this.state._fsp;

         while(true) {
            int alt6 = 2;
            int LA6_0 = this.input.LA(1);
            if (LA6_0 == 9) {
               alt6 = 1;
            }

            switch (alt6) {
               case 1:
                  this.match(this.input, 9, FOLLOW_EXT_in_treeAdaptor146);
                  break;
               default:
                  retval.stop = this.input.LT(-1);
                  return retval;
            }
         }
      } catch (RecognitionException var7) {
         this.reportError(var7);
         this.recover(this.input, var7);
         return retval;
      } finally {
         ;
      }
   }

   public final void header() throws RecognitionException {
      Token ACTION3 = null;

      try {
         try {
            this.match(this.input, 30, FOLLOW_30_in_header157);
            ACTION3 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_header159);
            int pos1;
            int pos2;
            if ((pos1 = (ACTION3 != null ? ACTION3.getText() : null).indexOf("package")) != -1 && (pos2 = (ACTION3 != null ? ACTION3.getText() : null).indexOf(59)) != -1) {
               this.grammarInfo.setGrammarPackage((ACTION3 != null ? ACTION3.getText() : null).substring(pos1 + 8, pos2).trim());
            } else {
               System.err.println("error(line " + ACTION3.getLine() + "): invalid header");
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void testsuite() throws RecognitionException {
      this.testsuite_stack.push(new testsuite_scope());
      Token r1 = null;
      Token r2 = null;
      Token t = null;
      gUnitTestSuite ts = null;
      ((testsuite_scope)this.testsuite_stack.peek()).isLexicalRule = false;

      try {
         int alt8 = true;
         int LA8_0 = this.input.LA(1);
         byte alt8;
         if (LA8_0 == 19) {
            alt8 = 1;
         } else {
            if (LA8_0 != 23) {
               NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
               throw nvae;
            }

            alt8 = 2;
         }

         int cnt9;
         label201:
         switch (alt8) {
            case 1:
               r1 = (Token)this.match(this.input, 19, FOLLOW_RULE_REF_in_testsuite190);
               cnt9 = 2;
               int LA7_0 = this.input.LA(1);
               if (LA7_0 == 33) {
                  cnt9 = 1;
               }

               switch (cnt9) {
                  case 1:
                     this.match(this.input, 33, FOLLOW_33_in_testsuite193);
                     r2 = (Token)this.match(this.input, 19, FOLLOW_RULE_REF_in_testsuite197);
                  default:
                     if (r2 == null) {
                        ts = new gUnitTestSuite(r1 != null ? r1.getText() : null);
                     } else {
                        ts = new gUnitTestSuite(r1 != null ? r1.getText() : null, r2 != null ? r2.getText() : null);
                     }
                     break label201;
               }
            case 2:
               t = (Token)this.match(this.input, 23, FOLLOW_TOKEN_REF_in_testsuite213);
               ts = new gUnitTestSuite();
               ts.setLexicalRuleName(t != null ? t.getText() : null);
               ((testsuite_scope)this.testsuite_stack.peek()).isLexicalRule = true;
         }

         this.match(this.input, 27, FOLLOW_27_in_testsuite227);
         cnt9 = 0;

         while(true) {
            int alt9 = 2;
            int LA9_3;
            switch (this.input.LA(1)) {
               case 12:
               case 21:
                  alt9 = 1;
                  break;
               case 19:
                  LA9_3 = this.input.LA(2);
                  if (LA9_3 >= 9 && LA9_3 <= 10 || LA9_3 == 16 || LA9_3 == 26 || LA9_3 == 32) {
                     alt9 = 1;
                  }
                  break;
               case 23:
                  LA9_3 = this.input.LA(2);
                  if (LA9_3 >= 9 && LA9_3 <= 10 || LA9_3 == 16 || LA9_3 == 26 || LA9_3 == 32) {
                     alt9 = 1;
                  }
            }

            switch (alt9) {
               case 1:
                  this.pushFollow(FOLLOW_testcase_in_testsuite231);
                  this.testcase(ts);
                  --this.state._fsp;
                  ++cnt9;
                  break;
               default:
                  if (cnt9 < 1) {
                     EarlyExitException eee = new EarlyExitException(9, this.input);
                     throw eee;
                  }

                  this.grammarInfo.addRuleTestSuite(ts);
                  return;
            }
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         this.recover(this.input, var13);
      } finally {
         this.testsuite_stack.pop();
      }

   }

   public final void testcase(gUnitTestSuite ts) throws RecognitionException {
      gUnitTestInput input4 = null;
      AbstractTest expect5 = null;

      try {
         try {
            this.pushFollow(FOLLOW_input_in_testcase249);
            input4 = this.input();
            --this.state._fsp;
            this.pushFollow(FOLLOW_expect_in_testcase251);
            expect5 = this.expect();
            --this.state._fsp;
            ts.addTestCase(input4, expect5);
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final gUnitTestInput input() throws RecognitionException {
      gUnitTestInput in = null;
      Token STRING6 = null;
      Token ML_STRING7 = null;
      ParserRuleReturnScope file8 = null;
      String testInput = null;
      boolean inputIsFile = false;
      int line = -1;

      try {
         try {
            int alt10 = true;
            byte alt10;
            switch (this.input.LA(1)) {
               case 12:
                  alt10 = 2;
                  break;
               case 19:
               case 23:
                  alt10 = 3;
                  break;
               case 21:
                  alt10 = 1;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
                  throw nvae;
            }

            switch (alt10) {
               case 1:
                  STRING6 = (Token)this.match(this.input, 21, FOLLOW_STRING_in_input278);
                  testInput = (STRING6 != null ? STRING6.getText() : null).replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t").replace("\\b", "\b").replace("\\f", "\f").replace("\\\"", "\"").replace("\\'", "'").replace("\\\\", "\\");
                  line = STRING6 != null ? STRING6.getLine() : 0;
                  break;
               case 2:
                  ML_STRING7 = (Token)this.match(this.input, 12, FOLLOW_ML_STRING_in_input288);
                  testInput = ML_STRING7 != null ? ML_STRING7.getText() : null;
                  line = ML_STRING7 != null ? ML_STRING7.getLine() : 0;
                  break;
               case 3:
                  this.pushFollow(FOLLOW_file_in_input297);
                  file8 = this.file();
                  --this.state._fsp;
                  testInput = file8 != null ? this.input.toString(file8.start, file8.stop) : null;
                  inputIsFile = true;
                  line = file8 != null ? ((file_return)file8).line : 0;
            }

            in = new gUnitTestInput(testInput, inputIsFile, line);
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.recover(this.input, var13);
         }

         return in;
      } finally {
         ;
      }
   }

   public final AbstractTest expect() throws RecognitionException {
      AbstractTest out = null;
      Token RETVAL9 = null;
      Token output10 = null;

      try {
         try {
            int alt11 = true;
            byte alt11;
            switch (this.input.LA(1)) {
               case 10:
                  alt11 = 2;
                  break;
               case 16:
                  alt11 = 1;
                  break;
               case 26:
                  alt11 = 4;
                  break;
               case 32:
                  alt11 = 3;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
                  throw nvae;
            }

            switch (alt11) {
               case 1:
                  this.match(this.input, 16, FOLLOW_OK_in_expect317);
                  out = new BooleanTest(true);
                  break;
               case 2:
                  this.match(this.input, 10, FOLLOW_FAIL_in_expect324);
                  out = new BooleanTest(false);
                  break;
               case 3:
                  this.match(this.input, 32, FOLLOW_32_in_expect331);
                  RETVAL9 = (Token)this.match(this.input, 18, FOLLOW_RETVAL_in_expect333);
                  if (!((testsuite_scope)this.testsuite_stack.peek()).isLexicalRule) {
                     out = new ReturnTest(RETVAL9);
                  }
                  break;
               case 4:
                  this.match(this.input, 26, FOLLOW_26_in_expect340);
                  this.pushFollow(FOLLOW_output_in_expect342);
                  output10 = this.output();
                  --this.state._fsp;
                  if (!((testsuite_scope)this.testsuite_stack.peek()).isLexicalRule) {
                     out = new OutputTest(output10);
                  }
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.recover(this.input, var9);
         }

         return (AbstractTest)out;
      } finally {
         ;
      }
   }

   public final Token output() throws RecognitionException {
      Token token = null;
      Token STRING11 = null;
      Token ML_STRING12 = null;
      Token AST13 = null;
      Token ACTION14 = null;

      try {
         try {
            int alt12 = true;
            byte alt12;
            switch (this.input.LA(1)) {
               case 4:
                  alt12 = 4;
                  break;
               case 5:
                  alt12 = 3;
                  break;
               case 12:
                  alt12 = 2;
                  break;
               case 21:
                  alt12 = 1;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
                  throw nvae;
            }

            switch (alt12) {
               case 1:
                  STRING11 = (Token)this.match(this.input, 21, FOLLOW_STRING_in_output359);
                  STRING11.setText((STRING11 != null ? STRING11.getText() : null).replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t").replace("\\b", "\b").replace("\\f", "\f").replace("\\\"", "\"").replace("\\'", "'").replace("\\\\", "\\"));
                  token = STRING11;
                  break;
               case 2:
                  ML_STRING12 = (Token)this.match(this.input, 12, FOLLOW_ML_STRING_in_output369);
                  token = ML_STRING12;
                  break;
               case 3:
                  AST13 = (Token)this.match(this.input, 5, FOLLOW_AST_in_output376);
                  token = AST13;
                  break;
               case 4:
                  ACTION14 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_output383);
                  token = ACTION14;
            }
         } catch (RecognitionException var11) {
            this.reportError(var11);
            this.recover(this.input, var11);
         }

         return token;
      } finally {
         ;
      }
   }

   public final file_return file() throws RecognitionException {
      file_return retval = new file_return();
      retval.start = this.input.LT(1);
      ParserRuleReturnScope id15 = null;

      try {
         try {
            this.pushFollow(FOLLOW_id_in_file401);
            id15 = this.id();
            --this.state._fsp;
            int alt13 = 2;
            int LA13_0 = this.input.LA(1);
            if (LA13_0 == 9) {
               alt13 = 1;
            }

            switch (alt13) {
               case 1:
                  this.match(this.input, 9, FOLLOW_EXT_in_file403);
               default:
                  retval.line = id15 != null ? ((id_return)id15).line : 0;
                  retval.stop = this.input.LT(-1);
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final id_return id() throws RecognitionException {
      id_return retval = new id_return();
      retval.start = this.input.LT(1);
      Token TOKEN_REF16 = null;
      Token RULE_REF17 = null;

      try {
         try {
            int alt14 = true;
            int LA14_0 = this.input.LA(1);
            byte alt14;
            if (LA14_0 == 23) {
               alt14 = 1;
            } else {
               if (LA14_0 != 19) {
                  NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
                  throw nvae;
               }

               alt14 = 2;
            }

            switch (alt14) {
               case 1:
                  TOKEN_REF16 = (Token)this.match(this.input, 23, FOLLOW_TOKEN_REF_in_id422);
                  retval.line = TOKEN_REF16 != null ? TOKEN_REF16.getLine() : 0;
                  break;
               case 2:
                  RULE_REF17 = (Token)this.match(this.input, 19, FOLLOW_RULE_REF_in_id429);
                  retval.line = RULE_REF17 != null ? RULE_REF17.getLine() : 0;
            }

            retval.stop = this.input.LT(-1);
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

         return retval;
      } finally {
         ;
      }
   }

   public static class id_return extends ParserRuleReturnScope {
      public int line;
   }

   public static class file_return extends ParserRuleReturnScope {
      public int line;
   }

   protected static class testsuite_scope {
      boolean isLexicalRule;
   }

   public static class treeAdaptor_return extends ParserRuleReturnScope {
   }

   public static class option_return extends ParserRuleReturnScope {
   }
}
