package org.antlr.gunit.swingui.parsers;

import org.antlr.gunit.swingui.model.ITestCaseInput;
import org.antlr.gunit.swingui.model.ITestCaseOutput;
import org.antlr.gunit.swingui.runner.TestSuiteAdapter;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;

public class StGUnitParser extends Parser {
   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTION", "AST", "CHAR_LITERAL", "DOC_COMMENT", "ESC", "EXT", "FAIL", "ML_COMMENT", "ML_STRING", "NESTED_ACTION", "NESTED_AST", "NESTED_RETVAL", "OK", "RETVAL", "RULE_REF", "SL_COMMENT", "STRING", "STRING_LITERAL", "TOKEN_REF", "WS", "XDIGIT", "'->'", "':'", "';'", "'@header'", "'gunit'", "'returns'", "'walks'"};
   public static final int EOF = -1;
   public static final int T__25 = 25;
   public static final int T__26 = 26;
   public static final int T__27 = 27;
   public static final int T__28 = 28;
   public static final int T__29 = 29;
   public static final int T__30 = 30;
   public static final int T__31 = 31;
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
   public static final int RETVAL = 17;
   public static final int RULE_REF = 18;
   public static final int SL_COMMENT = 19;
   public static final int STRING = 20;
   public static final int STRING_LITERAL = 21;
   public static final int TOKEN_REF = 22;
   public static final int WS = 23;
   public static final int XDIGIT = 24;
   public TestSuiteAdapter adapter;
   public static final BitSet FOLLOW_29_in_gUnitDef68 = new BitSet(new long[]{4456448L});
   public static final BitSet FOLLOW_id_in_gUnitDef72 = new BitSet(new long[]{2281701376L});
   public static final BitSet FOLLOW_31_in_gUnitDef82 = new BitSet(new long[]{4456448L});
   public static final BitSet FOLLOW_id_in_gUnitDef84 = new BitSet(new long[]{134217728L});
   public static final BitSet FOLLOW_27_in_gUnitDef88 = new BitSet(new long[]{272891906L});
   public static final BitSet FOLLOW_header_in_gUnitDef93 = new BitSet(new long[]{4456450L});
   public static final BitSet FOLLOW_suite_in_gUnitDef96 = new BitSet(new long[]{4456450L});
   public static final BitSet FOLLOW_28_in_header108 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_header110 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_RULE_REF_in_suite127 = new BitSet(new long[]{2214592512L});
   public static final BitSet FOLLOW_31_in_suite130 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_RULE_REF_in_suite132 = new BitSet(new long[]{67108864L});
   public static final BitSet FOLLOW_TOKEN_REF_in_suite154 = new BitSet(new long[]{67108864L});
   public static final BitSet FOLLOW_26_in_suite168 = new BitSet(new long[]{5509120L});
   public static final BitSet FOLLOW_test_in_suite172 = new BitSet(new long[]{5509122L});
   public static final BitSet FOLLOW_input_in_test188 = new BitSet(new long[]{1107362816L});
   public static final BitSet FOLLOW_expect_in_test190 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OK_in_expect210 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FAIL_in_expect219 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_30_in_expect227 = new BitSet(new long[]{131072L});
   public static final BitSet FOLLOW_RETVAL_in_expect229 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_25_in_expect236 = new BitSet(new long[]{1052688L});
   public static final BitSet FOLLOW_output_in_expect238 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_25_in_expect245 = new BitSet(new long[]{32L});
   public static final BitSet FOLLOW_AST_in_expect247 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_in_input264 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ML_STRING_in_input273 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_fileInput_in_input280 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_fileInput319 = new BitSet(new long[]{514L});
   public static final BitSet FOLLOW_EXT_in_fileInput324 = new BitSet(new long[]{2L});

   public Parser[] getDelegates() {
      return new Parser[0];
   }

   public StGUnitParser(TokenStream input) {
      this(input, new RecognizerSharedState());
   }

   public StGUnitParser(TokenStream input, RecognizerSharedState state) {
      super(input, state);
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\antlr\\gunit\\swingui\\parsers\\StGUnit.g";
   }

   public final void gUnitDef() throws RecognitionException {
      ParserRuleReturnScope name = null;

      try {
         this.match(this.input, 29, FOLLOW_29_in_gUnitDef68);
         this.pushFollow(FOLLOW_id_in_gUnitDef72);
         name = this.id();
         --this.state._fsp;
         this.adapter.setGrammarName(name != null ? this.input.toString(name.start, name.stop) : null);
         int alt1 = 2;
         int LA1_0 = this.input.LA(1);
         if (LA1_0 == 31) {
            alt1 = 1;
         }

         switch (alt1) {
            case 1:
               this.match(this.input, 31, FOLLOW_31_in_gUnitDef82);
               this.pushFollow(FOLLOW_id_in_gUnitDef84);
               this.id();
               --this.state._fsp;
            default:
               this.match(this.input, 27, FOLLOW_27_in_gUnitDef88);
               int alt2 = 2;
               int LA2_0 = this.input.LA(1);
               if (LA2_0 == 28) {
                  alt2 = 1;
               }

               switch (alt2) {
                  case 1:
                     this.pushFollow(FOLLOW_header_in_gUnitDef93);
                     this.header();
                     --this.state._fsp;
               }
         }

         while(true) {
            int alt3 = 2;
            int LA3_0 = this.input.LA(1);
            if (LA3_0 == 18 || LA3_0 == 22) {
               alt3 = 1;
            }

            switch (alt3) {
               case 1:
                  this.pushFollow(FOLLOW_suite_in_gUnitDef96);
                  this.suite();
                  --this.state._fsp;
                  break;
               default:
                  return;
            }
         }
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(this.input, var11);
      } finally {
         ;
      }
   }

   public final void header() throws RecognitionException {
      try {
         try {
            this.match(this.input, 28, FOLLOW_28_in_header108);
            this.match(this.input, 4, FOLLOW_ACTION_in_header110);
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void suite() throws RecognitionException {
      Token parserRule = null;
      Token lexerRule = null;

      try {
         int alt5 = true;
         int LA5_0 = this.input.LA(1);
         byte alt5;
         if (LA5_0 == 18) {
            alt5 = 1;
         } else {
            if (LA5_0 != 22) {
               NoViableAltException nvae = new NoViableAltException("", 5, 0, this.input);
               throw nvae;
            }

            alt5 = 2;
         }

         int cnt6;
         label165:
         switch (alt5) {
            case 1:
               parserRule = (Token)this.match(this.input, 18, FOLLOW_RULE_REF_in_suite127);
               cnt6 = 2;
               int LA4_0 = this.input.LA(1);
               if (LA4_0 == 31) {
                  cnt6 = 1;
               }

               switch (cnt6) {
                  case 1:
                     this.match(this.input, 31, FOLLOW_31_in_suite130);
                     this.match(this.input, 18, FOLLOW_RULE_REF_in_suite132);
                  default:
                     this.adapter.startRule(parserRule != null ? parserRule.getText() : null);
                     break label165;
               }
            case 2:
               lexerRule = (Token)this.match(this.input, 22, FOLLOW_TOKEN_REF_in_suite154);
               this.adapter.startRule(lexerRule != null ? lexerRule.getText() : null);
         }

         this.match(this.input, 26, FOLLOW_26_in_suite168);
         cnt6 = 0;

         while(true) {
            int alt6 = 2;
            int LA6_3;
            switch (this.input.LA(1)) {
               case 12:
               case 20:
                  alt6 = 1;
                  break;
               case 18:
                  LA6_3 = this.input.LA(2);
                  if (LA6_3 >= 9 && LA6_3 <= 10 || LA6_3 == 16 || LA6_3 == 25 || LA6_3 == 30) {
                     alt6 = 1;
                  }
                  break;
               case 22:
                  LA6_3 = this.input.LA(2);
                  if (LA6_3 >= 9 && LA6_3 <= 10 || LA6_3 == 16 || LA6_3 == 25 || LA6_3 == 30) {
                     alt6 = 1;
                  }
            }

            switch (alt6) {
               case 1:
                  this.pushFollow(FOLLOW_test_in_suite172);
                  this.test();
                  --this.state._fsp;
                  ++cnt6;
                  break;
               default:
                  if (cnt6 < 1) {
                     EarlyExitException eee = new EarlyExitException(6, this.input);
                     throw eee;
                  }

                  this.adapter.endRule();
                  return;
            }
         }
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(this.input, var11);
      } finally {
         ;
      }
   }

   public final void test() throws RecognitionException {
      ITestCaseInput input1 = null;
      ITestCaseOutput expect2 = null;

      try {
         try {
            this.pushFollow(FOLLOW_input_in_test188);
            input1 = this.input();
            --this.state._fsp;
            this.pushFollow(FOLLOW_expect_in_test190);
            expect2 = this.expect();
            --this.state._fsp;
            this.adapter.addTestCase(input1, expect2);
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final ITestCaseOutput expect() throws RecognitionException {
      ITestCaseOutput out = null;
      Token RETVAL3 = null;
      Token AST5 = null;
      ParserRuleReturnScope output4 = null;

      try {
         try {
            int alt7 = true;
            byte alt7;
            switch (this.input.LA(1)) {
               case 10:
                  alt7 = 2;
                  break;
               case 16:
                  alt7 = 1;
                  break;
               case 25:
                  int LA7_4 = this.input.LA(2);
                  if (LA7_4 == 5) {
                     alt7 = 5;
                  } else {
                     if (LA7_4 != 4 && LA7_4 != 12 && LA7_4 != 20) {
                        int nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           NoViableAltException nvae = new NoViableAltException("", 7, 4, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt7 = 4;
                  }
                  break;
               case 30:
                  alt7 = 3;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 7, 0, this.input);
                  throw nvae;
            }

            switch (alt7) {
               case 1:
                  this.match(this.input, 16, FOLLOW_OK_in_expect210);
                  out = TestSuiteAdapter.createBoolOutput(true);
                  break;
               case 2:
                  this.match(this.input, 10, FOLLOW_FAIL_in_expect219);
                  out = TestSuiteAdapter.createBoolOutput(false);
                  break;
               case 3:
                  this.match(this.input, 30, FOLLOW_30_in_expect227);
                  RETVAL3 = (Token)this.match(this.input, 17, FOLLOW_RETVAL_in_expect229);
                  out = TestSuiteAdapter.createReturnOutput(RETVAL3 != null ? RETVAL3.getText() : null);
                  break;
               case 4:
                  this.match(this.input, 25, FOLLOW_25_in_expect236);
                  this.pushFollow(FOLLOW_output_in_expect238);
                  output4 = this.output();
                  --this.state._fsp;
                  out = TestSuiteAdapter.createStdOutput(output4 != null ? this.input.toString(output4.start, output4.stop) : null);
                  break;
               case 5:
                  this.match(this.input, 25, FOLLOW_25_in_expect245);
                  AST5 = (Token)this.match(this.input, 5, FOLLOW_AST_in_expect247);
                  out = TestSuiteAdapter.createAstOutput(AST5 != null ? AST5.getText() : null);
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
         }

         return out;
      } finally {
         ;
      }
   }

   public final ITestCaseInput input() throws RecognitionException {
      ITestCaseInput in = null;
      Token STRING6 = null;
      Token ML_STRING7 = null;
      String fileInput8 = null;

      try {
         try {
            int alt8 = true;
            byte alt8;
            switch (this.input.LA(1)) {
               case 12:
                  alt8 = 2;
                  break;
               case 18:
               case 22:
                  alt8 = 3;
                  break;
               case 20:
                  alt8 = 1;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
                  throw nvae;
            }

            switch (alt8) {
               case 1:
                  STRING6 = (Token)this.match(this.input, 20, FOLLOW_STRING_in_input264);
                  in = TestSuiteAdapter.createStringInput(STRING6 != null ? STRING6.getText() : null);
                  break;
               case 2:
                  ML_STRING7 = (Token)this.match(this.input, 12, FOLLOW_ML_STRING_in_input273);
                  in = TestSuiteAdapter.createMultiInput(ML_STRING7 != null ? ML_STRING7.getText() : null);
                  break;
               case 3:
                  this.pushFollow(FOLLOW_fileInput_in_input280);
                  fileInput8 = this.fileInput();
                  --this.state._fsp;
                  in = TestSuiteAdapter.createFileInput(fileInput8);
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

         return in;
      } finally {
         ;
      }
   }

   public final output_return output() throws RecognitionException {
      output_return retval = new output_return();
      retval.start = this.input.LT(1);

      try {
         try {
            if (this.input.LA(1) != 4 && this.input.LA(1) != 12 && this.input.LA(1) != 20) {
               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               throw mse;
            }

            this.input.consume();
            this.state.errorRecovery = false;
            retval.stop = this.input.LT(-1);
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final String fileInput() throws RecognitionException {
      String path = null;
      Token EXT10 = null;
      ParserRuleReturnScope id9 = null;

      try {
         try {
            this.pushFollow(FOLLOW_id_in_fileInput319);
            id9 = this.id();
            --this.state._fsp;
            path = id9 != null ? this.input.toString(id9.start, id9.stop) : null;
            int alt9 = 2;
            int LA9_0 = this.input.LA(1);
            if (LA9_0 == 9) {
               alt9 = 1;
            }

            switch (alt9) {
               case 1:
                  EXT10 = (Token)this.match(this.input, 9, FOLLOW_EXT_in_fileInput324);
                  path = path + (EXT10 != null ? EXT10.getText() : null);
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.recover(this.input, var9);
         }

         return path;
      } finally {
         ;
      }
   }

   public final id_return id() throws RecognitionException {
      id_return retval = new id_return();
      retval.start = this.input.LT(1);

      try {
         try {
            if (this.input.LA(1) != 18 && this.input.LA(1) != 22) {
               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               throw mse;
            }

            this.input.consume();
            this.state.errorRecovery = false;
            retval.stop = this.input.LT(-1);
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

         return retval;
      } finally {
         ;
      }
   }

   public static class id_return extends ParserRuleReturnScope {
   }

   public static class output_return extends ParserRuleReturnScope {
   }
}
