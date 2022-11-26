package org.antlr.gunit.swingui.parsers;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class StGUnitLexer extends Lexer {
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
   protected DFA13 dfa13;
   static final String DFA13_eotS = "N\uffff";
   static final String DFA13_eofS = "N\uffff";
   static final String DFA13_minS = "\u0001\u0000\u0002\uffff\u0002\u0000\u0001\uffff\u0001\u0000\u0002\uffff\u0004\u0000\u0001\uffff\u0003\u0000=\uffff";
   static final String DFA13_maxS = "\u0001\uffff\u0002\uffff\u0002\uffff\u0001\uffff\u0001\uffff\u0002\uffff\u0004\uffff\u0001\uffff\u0003\uffff=\uffff";
   static final String DFA13_acceptS = "\u0001\uffff\u0001\u0005\u0001\u0001\u0002\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0001\u0002\u0004\uffff\u0001\u0004\u0004\uffff\u001f\u0002\u000e\u0003\u0004\uffff\u0001\u0003\u0005\uffff\u0001\u0003\u0004\uffff";
   static final String DFA13_specialS = "\u0001\u0000\u0002\uffff\u0001\u0001\u0001\u0002\u0001\uffff\u0001\u0003\u0002\uffff\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\uffff\u0001\b\u0001\t\u0001\n=\uffff}>";
   static final String[] DFA13_transitionS = new String[]{"\"\u0005\u0001\u0003\u0004\u0005\u0001\u0004S\u0005\u0001\u0002\u0001\u0005\u0001\u0001ﾂ\u0005", "", "", "\"\u000b\u0001\b\u0004\u000b\u0001\n4\u000b\u0001\u0006\u001e\u000b\u0001\t\u0001\u000b\u0001\u0007ﾂ\u000b", "\"\u0010\u0001\u000f\u0004\u0010\u0001\u00054\u0010\u0001\f\u001e\u0010\u0001\u000e\u0001\u0010\u0001\rﾂ\u0010", "", "\"\u001e\u0001\u0017\u0004\u001e\u0001\u0018\u0016\u001e\u0001\u001a\u001d\u001e\u0001\u0019\u0005\u001e\u0001\u0015\u0003\u001e\u0001\u0016\u0007\u001e\u0001\u0012\u0003\u001e\u0001\u0013\u0001\u001e\u0001\u0014\u0001\u001b\u0005\u001e\u0001\u001d\u0001\u001e\u0001\u001cﾂ\u001e", "", "", "\"$\u0001\u001f\u0004$\u0001\"4$\u0001 \u001e$\u0001!\u0001$\u0001#ﾂ$", "\"*\u0001%\u0004*\u0001(4*\u0001&\u001e*\u0001)\u0001*\u0001'ﾂ*", "\"0\u0001+\u00040\u0001/40\u0001,\u001e0\u0001.\u00010\u0001-ﾂ0", "\"=\u00016\u0004=\u00017\u0016=\u00019\u001d=\u00018\u0005=\u00014\u0003=\u00015\u0007=\u00011\u0003=\u00012\u0001=\u00013\u0001:\u0005=\u0001<\u0001=\u0001;ﾂ=", "", "'\u0005\u0001>\uffd8\u0005", "'\u0005\u0001C\uffd8\u0005", "'\u0005\u0001I\uffd8\u0005", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
   static final short[] DFA13_eot = DFA.unpackEncodedString("N\uffff");
   static final short[] DFA13_eof = DFA.unpackEncodedString("N\uffff");
   static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0000\u0002\uffff\u0002\u0000\u0001\uffff\u0001\u0000\u0002\uffff\u0004\u0000\u0001\uffff\u0003\u0000=\uffff");
   static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars("\u0001\uffff\u0002\uffff\u0002\uffff\u0001\uffff\u0001\uffff\u0002\uffff\u0004\uffff\u0001\uffff\u0003\uffff=\uffff");
   static final short[] DFA13_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0005\u0001\u0001\u0002\uffff\u0001\u0004\u0001\uffff\u0001\u0004\u0001\u0002\u0004\uffff\u0001\u0004\u0004\uffff\u001f\u0002\u000e\u0003\u0004\uffff\u0001\u0003\u0005\uffff\u0001\u0003\u0004\uffff");
   static final short[] DFA13_special = DFA.unpackEncodedString("\u0001\u0000\u0002\uffff\u0001\u0001\u0001\u0002\u0001\uffff\u0001\u0003\u0002\uffff\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\uffff\u0001\b\u0001\t\u0001\n=\uffff}>");
   static final short[][] DFA13_transition;

   public Lexer[] getDelegates() {
      return new Lexer[0];
   }

   public StGUnitLexer() {
      this.dfa13 = new DFA13(this);
   }

   public StGUnitLexer(CharStream input) {
      this(input, new RecognizerSharedState());
   }

   public StGUnitLexer(CharStream input, RecognizerSharedState state) {
      super(input, state);
      this.dfa13 = new DFA13(this);
   }

   public String getGrammarFileName() {
      return "org\\antlr\\gunit\\swingui\\parsers\\StGUnit.g";
   }

   public final void mFAIL() throws RecognitionException {
      try {
         int _type = 10;
         int _channel = 0;
         this.match("FAIL");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mOK() throws RecognitionException {
      try {
         int _type = 16;
         int _channel = 0;
         this.match("OK");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__25() throws RecognitionException {
      try {
         int _type = 25;
         int _channel = 0;
         this.match("->");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__26() throws RecognitionException {
      try {
         int _type = 26;
         int _channel = 0;
         this.match(58);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__27() throws RecognitionException {
      try {
         int _type = 27;
         int _channel = 0;
         this.match(59);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__28() throws RecognitionException {
      try {
         int _type = 28;
         int _channel = 0;
         this.match("@header");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__29() throws RecognitionException {
      try {
         int _type = 29;
         int _channel = 0;
         this.match("gunit");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__30() throws RecognitionException {
      try {
         int _type = 30;
         int _channel = 0;
         this.match("returns");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__31() throws RecognitionException {
      try {
         int _type = 31;
         int _channel = 0;
         this.match("walks");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mSL_COMMENT() throws RecognitionException {
      try {
         int _type = 19;
         int _channel = false;
         this.match("//");

         while(true) {
            int alt2 = 2;
            int LA2_0 = this.input.LA(1);
            if (LA2_0 >= 0 && LA2_0 <= 9 || LA2_0 >= 11 && LA2_0 <= 12 || LA2_0 >= 14 && LA2_0 <= 65535) {
               alt2 = 1;
            }

            switch (alt2) {
               case 1:
                  if ((this.input.LA(1) < 0 || this.input.LA(1) > 9) && (this.input.LA(1) < 11 || this.input.LA(1) > 12) && (this.input.LA(1) < 14 || this.input.LA(1) > 65535)) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  break;
               default:
                  alt2 = 2;
                  LA2_0 = this.input.LA(1);
                  if (LA2_0 == 13) {
                     alt2 = 1;
                  }

                  switch (alt2) {
                     case 1:
                        this.match(13);
                     default:
                        this.match(10);
                        int _channel = 99;
                        this.state.type = _type;
                        this.state.channel = _channel;
                        return;
                  }
            }
         }
      } finally {
         ;
      }
   }

   public final void mML_COMMENT() throws RecognitionException {
      try {
         int _type = 11;
         int _channel = false;
         this.match("/*");
         int _channel = 99;

         while(true) {
            int alt3 = 2;
            int LA3_0 = this.input.LA(1);
            if (LA3_0 == 42) {
               int LA3_1 = this.input.LA(2);
               if (LA3_1 == 47) {
                  alt3 = 2;
               } else if (LA3_1 >= 0 && LA3_1 <= 46 || LA3_1 >= 48 && LA3_1 <= 65535) {
                  alt3 = 1;
               }
            } else if (LA3_0 >= 0 && LA3_0 <= 41 || LA3_0 >= 43 && LA3_0 <= 65535) {
               alt3 = 1;
            }

            switch (alt3) {
               case 1:
                  this.matchAny();
                  break;
               default:
                  this.match("*/");
                  this.state.type = _type;
                  this.state.channel = _channel;
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mSTRING() throws RecognitionException {
      try {
         int _type = 20;
         int _channel = 0;
         this.match(34);

         while(true) {
            int alt4 = 3;
            int LA4_0 = this.input.LA(1);
            if (LA4_0 == 92) {
               alt4 = 1;
            } else if (LA4_0 >= 0 && LA4_0 <= 33 || LA4_0 >= 35 && LA4_0 <= 91 || LA4_0 >= 93 && LA4_0 <= 65535) {
               alt4 = 2;
            }

            switch (alt4) {
               case 1:
                  this.mESC();
                  break;
               case 2:
                  if ((this.input.LA(1) < 0 || this.input.LA(1) > 33) && (this.input.LA(1) < 35 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 65535)) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  break;
               default:
                  this.match(34);
                  this.state.type = _type;
                  this.state.channel = _channel;
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mML_STRING() throws RecognitionException {
      try {
         int _type = 12;
         int _channel = 0;
         this.match("<<");

         while(true) {
            int alt5 = 2;
            int LA5_0 = this.input.LA(1);
            if (LA5_0 == 62) {
               int LA5_1 = this.input.LA(2);
               if (LA5_1 == 62) {
                  alt5 = 2;
               } else if (LA5_1 >= 0 && LA5_1 <= 61 || LA5_1 >= 63 && LA5_1 <= 65535) {
                  alt5 = 1;
               }
            } else if (LA5_0 >= 0 && LA5_0 <= 61 || LA5_0 >= 63 && LA5_0 <= 65535) {
               alt5 = 1;
            }

            switch (alt5) {
               case 1:
                  this.matchAny();
                  break;
               default:
                  this.match(">>");
                  this.state.type = _type;
                  this.state.channel = _channel;
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mTOKEN_REF() throws RecognitionException {
      try {
         int _type = 22;
         int _channel = 0;
         this.matchRange(65, 90);

         while(true) {
            int alt6 = 2;
            int LA6_0 = this.input.LA(1);
            if (LA6_0 >= 48 && LA6_0 <= 57 || LA6_0 >= 65 && LA6_0 <= 90 || LA6_0 == 95 || LA6_0 >= 97 && LA6_0 <= 122) {
               alt6 = 1;
            }

            switch (alt6) {
               case 1:
                  if ((this.input.LA(1) < 48 || this.input.LA(1) > 57) && (this.input.LA(1) < 65 || this.input.LA(1) > 90) && this.input.LA(1) != 95 && (this.input.LA(1) < 97 || this.input.LA(1) > 122)) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  break;
               default:
                  this.state.type = _type;
                  this.state.channel = _channel;
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mRULE_REF() throws RecognitionException {
      try {
         int _type = 18;
         int _channel = 0;
         this.matchRange(97, 122);

         while(true) {
            int alt7 = 2;
            int LA7_0 = this.input.LA(1);
            if (LA7_0 >= 48 && LA7_0 <= 57 || LA7_0 >= 65 && LA7_0 <= 90 || LA7_0 == 95 || LA7_0 >= 97 && LA7_0 <= 122) {
               alt7 = 1;
            }

            switch (alt7) {
               case 1:
                  if ((this.input.LA(1) < 48 || this.input.LA(1) > 57) && (this.input.LA(1) < 65 || this.input.LA(1) > 90) && this.input.LA(1) != 95 && (this.input.LA(1) < 97 || this.input.LA(1) > 122)) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  break;
               default:
                  this.state.type = _type;
                  this.state.channel = _channel;
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mEXT() throws RecognitionException {
      try {
         int _type = 9;
         int _channel = 0;
         this.match(46);
         int cnt8 = 0;

         while(true) {
            int alt8 = 2;
            int LA8_0 = this.input.LA(1);
            if (LA8_0 >= 48 && LA8_0 <= 57 || LA8_0 >= 65 && LA8_0 <= 90 || LA8_0 >= 97 && LA8_0 <= 122) {
               alt8 = 1;
            }

            switch (alt8) {
               case 1:
                  if ((this.input.LA(1) < 48 || this.input.LA(1) > 57) && (this.input.LA(1) < 65 || this.input.LA(1) > 90) && (this.input.LA(1) < 97 || this.input.LA(1) > 122)) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  ++cnt8;
                  break;
               default:
                  if (cnt8 >= 1) {
                     this.state.type = _type;
                     this.state.channel = _channel;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(8, this.input);
                  throw eee;
            }
         }
      } finally {
         ;
      }
   }

   public final void mRETVAL() throws RecognitionException {
      try {
         int _type = 17;
         int _channel = 0;
         this.mNESTED_RETVAL();
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mNESTED_RETVAL() throws RecognitionException {
      try {
         this.match(91);

         while(true) {
            int alt9 = 3;
            int LA9_0 = this.input.LA(1);
            if (LA9_0 == 93) {
               alt9 = 3;
            } else if (LA9_0 == 91) {
               alt9 = 1;
            } else if (LA9_0 >= 0 && LA9_0 <= 90 || LA9_0 == 92 || LA9_0 >= 94 && LA9_0 <= 65535) {
               alt9 = 2;
            }

            switch (alt9) {
               case 1:
                  this.mNESTED_RETVAL();
                  break;
               case 2:
                  this.matchAny();
                  break;
               default:
                  this.match(93);
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mAST() throws RecognitionException {
      try {
         int _type = 5;
         int _channel = 0;
         this.mNESTED_AST();

         while(true) {
            int alt11 = 2;
            int LA11_0 = this.input.LA(1);
            if (LA11_0 == 32 || LA11_0 == 40) {
               alt11 = 1;
            }

            switch (alt11) {
               case 1:
                  int alt10 = 2;
                  int LA10_0 = this.input.LA(1);
                  if (LA10_0 == 32) {
                     alt10 = 1;
                  }

                  switch (alt10) {
                     case 1:
                        this.match(32);
                     default:
                        this.mNESTED_AST();
                        continue;
                  }
               default:
                  this.state.type = _type;
                  this.state.channel = _channel;
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mNESTED_AST() throws RecognitionException {
      try {
         this.match(40);

         while(true) {
            int alt12 = 3;
            int LA12_0 = this.input.LA(1);
            if (LA12_0 == 41) {
               alt12 = 3;
            } else if (LA12_0 == 40) {
               alt12 = 1;
            } else if (LA12_0 >= 0 && LA12_0 <= 39 || LA12_0 >= 42 && LA12_0 <= 65535) {
               alt12 = 2;
            }

            switch (alt12) {
               case 1:
                  this.mNESTED_AST();
                  break;
               case 2:
                  this.matchAny();
                  break;
               default:
                  this.match(41);
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mACTION() throws RecognitionException {
      try {
         int _type = 4;
         int _channel = 0;
         this.mNESTED_ACTION();
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mNESTED_ACTION() throws RecognitionException {
      try {
         this.match(123);

         while(true) {
            int alt13 = true;
            int alt13 = this.dfa13.predict(this.input);
            switch (alt13) {
               case 1:
                  this.mNESTED_ACTION();
                  break;
               case 2:
                  this.mSTRING_LITERAL();
                  break;
               case 3:
                  this.mCHAR_LITERAL();
                  break;
               case 4:
                  this.matchAny();
                  break;
               default:
                  this.match(125);
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mCHAR_LITERAL() throws RecognitionException {
      try {
         this.match(39);
         int alt14 = true;
         int LA14_0 = this.input.LA(1);
         byte alt14;
         if (LA14_0 == 92) {
            alt14 = 1;
         } else {
            if ((LA14_0 < 0 || LA14_0 > 38) && (LA14_0 < 40 || LA14_0 > 91) && (LA14_0 < 93 || LA14_0 > 65535)) {
               NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
               throw nvae;
            }

            alt14 = 2;
         }

         switch (alt14) {
            case 1:
               this.mESC();
               break;
            case 2:
               if ((this.input.LA(1) < 0 || this.input.LA(1) > 38) && (this.input.LA(1) < 40 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 65535)) {
                  MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                  this.recover(mse);
                  throw mse;
               }

               this.input.consume();
         }

         this.match(39);
      } finally {
         ;
      }
   }

   public final void mSTRING_LITERAL() throws RecognitionException {
      try {
         this.match(34);

         while(true) {
            int alt15 = 3;
            int LA15_0 = this.input.LA(1);
            if (LA15_0 == 92) {
               alt15 = 1;
            } else if (LA15_0 >= 0 && LA15_0 <= 33 || LA15_0 >= 35 && LA15_0 <= 91 || LA15_0 >= 93 && LA15_0 <= 65535) {
               alt15 = 2;
            }

            switch (alt15) {
               case 1:
                  this.mESC();
                  break;
               case 2:
                  if ((this.input.LA(1) < 0 || this.input.LA(1) > 33) && (this.input.LA(1) < 35 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 65535)) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  break;
               default:
                  this.match(34);
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mESC() throws RecognitionException {
      try {
         this.match(92);
         int alt16 = true;
         int LA16_0 = this.input.LA(1);
         byte alt16;
         if (LA16_0 == 110) {
            alt16 = 1;
         } else if (LA16_0 == 114) {
            alt16 = 2;
         } else if (LA16_0 == 116) {
            alt16 = 3;
         } else if (LA16_0 == 98) {
            alt16 = 4;
         } else if (LA16_0 == 102) {
            alt16 = 5;
         } else if (LA16_0 == 34) {
            alt16 = 6;
         } else if (LA16_0 == 39) {
            alt16 = 7;
         } else if (LA16_0 == 92) {
            alt16 = 8;
         } else if (LA16_0 == 62) {
            alt16 = 9;
         } else if (LA16_0 == 117) {
            int LA16_10 = this.input.LA(2);
            if ((LA16_10 < 48 || LA16_10 > 57) && (LA16_10 < 65 || LA16_10 > 70) && (LA16_10 < 97 || LA16_10 > 102)) {
               alt16 = 11;
            } else {
               alt16 = 10;
            }
         } else {
            if ((LA16_0 < 0 || LA16_0 > 33) && (LA16_0 < 35 || LA16_0 > 38) && (LA16_0 < 40 || LA16_0 > 61) && (LA16_0 < 63 || LA16_0 > 91) && (LA16_0 < 93 || LA16_0 > 97) && (LA16_0 < 99 || LA16_0 > 101) && (LA16_0 < 103 || LA16_0 > 109) && (LA16_0 < 111 || LA16_0 > 113) && LA16_0 != 115 && (LA16_0 < 118 || LA16_0 > 65535)) {
               NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
               throw nvae;
            }

            alt16 = 11;
         }

         switch (alt16) {
            case 1:
               this.match(110);
               break;
            case 2:
               this.match(114);
               break;
            case 3:
               this.match(116);
               break;
            case 4:
               this.match(98);
               break;
            case 5:
               this.match(102);
               break;
            case 6:
               this.match(34);
               break;
            case 7:
               this.match(39);
               break;
            case 8:
               this.match(92);
               break;
            case 9:
               this.match(62);
               break;
            case 10:
               this.match(117);
               this.mXDIGIT();
               this.mXDIGIT();
               this.mXDIGIT();
               this.mXDIGIT();
               break;
            case 11:
               this.matchAny();
         }

      } finally {
         ;
      }
   }

   public final void mXDIGIT() throws RecognitionException {
      try {
         if ((this.input.LA(1) < 48 || this.input.LA(1) > 57) && (this.input.LA(1) < 65 || this.input.LA(1) > 70) && (this.input.LA(1) < 97 || this.input.LA(1) > 102)) {
            MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
            this.recover(mse);
            throw mse;
         } else {
            this.input.consume();
         }
      } finally {
         ;
      }
   }

   public final void mWS() throws RecognitionException {
      try {
         int _type = 23;
         int _channel = false;
         int cnt18 = 0;

         while(true) {
            int alt18 = 4;
            switch (this.input.LA(1)) {
               case 9:
                  alt18 = 2;
                  break;
               case 10:
               case 13:
                  alt18 = 3;
                  break;
               case 32:
                  alt18 = 1;
            }

            label72:
            switch (alt18) {
               case 1:
                  this.match(32);
                  break;
               case 2:
                  this.match(9);
                  break;
               case 3:
                  int alt17 = 2;
                  int LA17_0 = this.input.LA(1);
                  if (LA17_0 == 13) {
                     alt17 = 1;
                  }

                  switch (alt17) {
                     case 1:
                        this.match(13);
                     default:
                        this.match(10);
                        break label72;
                  }
               default:
                  if (cnt18 >= 1) {
                     int _channel = 99;
                     this.state.type = _type;
                     this.state.channel = _channel;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(18, this.input);
                  throw eee;
            }

            ++cnt18;
         }
      } finally {
         ;
      }
   }

   public void mTokens() throws RecognitionException {
      int alt19 = true;
      int LA19_10;
      int nvaeMark;
      int LA19_31;
      int LA19_35;
      int LA19_39;
      byte alt19;
      switch (this.input.LA(1)) {
         case 9:
         case 10:
         case 13:
         case 32:
            alt19 = 20;
            break;
         case 11:
         case 12:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 33:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 41:
         case 42:
         case 43:
         case 44:
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
         case 61:
         case 62:
         case 63:
         case 92:
         case 93:
         case 94:
         case 95:
         case 96:
         default:
            NoViableAltException nvae = new NoViableAltException("", 19, 0, this.input);
            throw nvae;
         case 34:
            alt19 = 12;
            break;
         case 40:
            alt19 = 18;
            break;
         case 45:
            alt19 = 3;
            break;
         case 46:
            alt19 = 16;
            break;
         case 47:
            LA19_10 = this.input.LA(2);
            if (LA19_10 == 47) {
               alt19 = 10;
            } else {
               if (LA19_10 != 42) {
                  nvaeMark = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 19, 10, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               alt19 = 11;
            }
            break;
         case 58:
            alt19 = 4;
            break;
         case 59:
            alt19 = 5;
            break;
         case 60:
            alt19 = 13;
            break;
         case 64:
            alt19 = 6;
            break;
         case 65:
         case 66:
         case 67:
         case 68:
         case 69:
         case 71:
         case 72:
         case 73:
         case 74:
         case 75:
         case 76:
         case 77:
         case 78:
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
            alt19 = 14;
            break;
         case 70:
            LA19_10 = this.input.LA(2);
            if (LA19_10 == 65) {
               nvaeMark = this.input.LA(3);
               if (nvaeMark == 73) {
                  LA19_31 = this.input.LA(4);
                  if (LA19_31 == 76) {
                     LA19_35 = this.input.LA(5);
                     if ((LA19_35 < 48 || LA19_35 > 57) && (LA19_35 < 65 || LA19_35 > 90) && LA19_35 != 95 && (LA19_35 < 97 || LA19_35 > 122)) {
                        alt19 = 1;
                     } else {
                        alt19 = 14;
                     }
                  } else {
                     alt19 = 14;
                  }
               } else {
                  alt19 = 14;
               }
            } else {
               alt19 = 14;
            }
            break;
         case 79:
            LA19_10 = this.input.LA(2);
            if (LA19_10 == 75) {
               nvaeMark = this.input.LA(3);
               if ((nvaeMark < 48 || nvaeMark > 57) && (nvaeMark < 65 || nvaeMark > 90) && nvaeMark != 95 && (nvaeMark < 97 || nvaeMark > 122)) {
                  alt19 = 2;
               } else {
                  alt19 = 14;
               }
            } else {
               alt19 = 14;
            }
            break;
         case 91:
            alt19 = 17;
            break;
         case 97:
         case 98:
         case 99:
         case 100:
         case 101:
         case 102:
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
         case 115:
         case 116:
         case 117:
         case 118:
         case 120:
         case 121:
         case 122:
            alt19 = 15;
            break;
         case 103:
            LA19_10 = this.input.LA(2);
            if (LA19_10 == 117) {
               nvaeMark = this.input.LA(3);
               if (nvaeMark == 110) {
                  LA19_31 = this.input.LA(4);
                  if (LA19_31 == 105) {
                     LA19_35 = this.input.LA(5);
                     if (LA19_35 == 116) {
                        LA19_39 = this.input.LA(6);
                        if ((LA19_39 < 48 || LA19_39 > 57) && (LA19_39 < 65 || LA19_39 > 90) && LA19_39 != 95 && (LA19_39 < 97 || LA19_39 > 122)) {
                           alt19 = 7;
                        } else {
                           alt19 = 15;
                        }
                     } else {
                        alt19 = 15;
                     }
                  } else {
                     alt19 = 15;
                  }
               } else {
                  alt19 = 15;
               }
            } else {
               alt19 = 15;
            }
            break;
         case 114:
            LA19_10 = this.input.LA(2);
            if (LA19_10 == 101) {
               nvaeMark = this.input.LA(3);
               if (nvaeMark == 116) {
                  LA19_31 = this.input.LA(4);
                  if (LA19_31 == 117) {
                     LA19_35 = this.input.LA(5);
                     if (LA19_35 == 114) {
                        LA19_39 = this.input.LA(6);
                        if (LA19_39 == 110) {
                           int LA19_41 = this.input.LA(7);
                           if (LA19_41 == 115) {
                              int LA19_43 = this.input.LA(8);
                              if ((LA19_43 < 48 || LA19_43 > 57) && (LA19_43 < 65 || LA19_43 > 90) && LA19_43 != 95 && (LA19_43 < 97 || LA19_43 > 122)) {
                                 alt19 = 8;
                              } else {
                                 alt19 = 15;
                              }
                           } else {
                              alt19 = 15;
                           }
                        } else {
                           alt19 = 15;
                        }
                     } else {
                        alt19 = 15;
                     }
                  } else {
                     alt19 = 15;
                  }
               } else {
                  alt19 = 15;
               }
            } else {
               alt19 = 15;
            }
            break;
         case 119:
            LA19_10 = this.input.LA(2);
            if (LA19_10 == 97) {
               nvaeMark = this.input.LA(3);
               if (nvaeMark == 108) {
                  LA19_31 = this.input.LA(4);
                  if (LA19_31 == 107) {
                     LA19_35 = this.input.LA(5);
                     if (LA19_35 == 115) {
                        LA19_39 = this.input.LA(6);
                        if ((LA19_39 < 48 || LA19_39 > 57) && (LA19_39 < 65 || LA19_39 > 90) && LA19_39 != 95 && (LA19_39 < 97 || LA19_39 > 122)) {
                           alt19 = 9;
                        } else {
                           alt19 = 15;
                        }
                     } else {
                        alt19 = 15;
                     }
                  } else {
                     alt19 = 15;
                  }
               } else {
                  alt19 = 15;
               }
            } else {
               alt19 = 15;
            }
            break;
         case 123:
            alt19 = 19;
      }

      switch (alt19) {
         case 1:
            this.mFAIL();
            break;
         case 2:
            this.mOK();
            break;
         case 3:
            this.mT__25();
            break;
         case 4:
            this.mT__26();
            break;
         case 5:
            this.mT__27();
            break;
         case 6:
            this.mT__28();
            break;
         case 7:
            this.mT__29();
            break;
         case 8:
            this.mT__30();
            break;
         case 9:
            this.mT__31();
            break;
         case 10:
            this.mSL_COMMENT();
            break;
         case 11:
            this.mML_COMMENT();
            break;
         case 12:
            this.mSTRING();
            break;
         case 13:
            this.mML_STRING();
            break;
         case 14:
            this.mTOKEN_REF();
            break;
         case 15:
            this.mRULE_REF();
            break;
         case 16:
            this.mEXT();
            break;
         case 17:
            this.mRETVAL();
            break;
         case 18:
            this.mAST();
            break;
         case 19:
            this.mACTION();
            break;
         case 20:
            this.mWS();
      }

   }

   static {
      int numStates = DFA13_transitionS.length;
      DFA13_transition = new short[numStates][];

      for(int i = 0; i < numStates; ++i) {
         DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
      }

   }

   protected class DFA13 extends DFA {
      public DFA13(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 13;
         this.eot = StGUnitLexer.DFA13_eot;
         this.eof = StGUnitLexer.DFA13_eof;
         this.min = StGUnitLexer.DFA13_min;
         this.max = StGUnitLexer.DFA13_max;
         this.accept = StGUnitLexer.DFA13_accept;
         this.special = StGUnitLexer.DFA13_special;
         this.transition = StGUnitLexer.DFA13_transition;
      }

      public String getDescription() {
         return "()* loopback of 167:2: ( options {greedy=false; k=3; } : NESTED_ACTION | STRING_LITERAL | CHAR_LITERAL | . )*";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         switch (s) {
            case 0:
               int LA13_0 = _input.LA(1);
               s = -1;
               if (LA13_0 == 125) {
                  s = 1;
               } else if (LA13_0 == 123) {
                  s = 2;
               } else if (LA13_0 == 34) {
                  s = 3;
               } else if (LA13_0 == 39) {
                  s = 4;
               } else if (LA13_0 >= 0 && LA13_0 <= 33 || LA13_0 >= 35 && LA13_0 <= 38 || LA13_0 >= 40 && LA13_0 <= 122 || LA13_0 == 124 || LA13_0 >= 126 && LA13_0 <= 65535) {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA13_3 = _input.LA(1);
               s = -1;
               if (LA13_3 == 92) {
                  s = 6;
               } else if (LA13_3 == 125) {
                  s = 7;
               } else if (LA13_3 == 34) {
                  s = 8;
               } else if (LA13_3 == 123) {
                  s = 9;
               } else if (LA13_3 == 39) {
                  s = 10;
               } else if (LA13_3 >= 0 && LA13_3 <= 33 || LA13_3 >= 35 && LA13_3 <= 38 || LA13_3 >= 40 && LA13_3 <= 91 || LA13_3 >= 93 && LA13_3 <= 122 || LA13_3 == 124 || LA13_3 >= 126 && LA13_3 <= 65535) {
                  s = 11;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA13_4 = _input.LA(1);
               s = -1;
               if (LA13_4 == 92) {
                  s = 12;
               } else if (LA13_4 == 125) {
                  s = 13;
               } else if (LA13_4 == 123) {
                  s = 14;
               } else if (LA13_4 == 34) {
                  s = 15;
               } else if ((LA13_4 < 0 || LA13_4 > 33) && (LA13_4 < 35 || LA13_4 > 38) && (LA13_4 < 40 || LA13_4 > 91) && (LA13_4 < 93 || LA13_4 > 122) && LA13_4 != 124 && (LA13_4 < 126 || LA13_4 > 65535)) {
                  if (LA13_4 == 39) {
                     s = 5;
                  }
               } else {
                  s = 16;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA13_6 = _input.LA(1);
               s = -1;
               if (LA13_6 == 110) {
                  s = 18;
               } else if (LA13_6 == 114) {
                  s = 19;
               } else if (LA13_6 == 116) {
                  s = 20;
               } else if (LA13_6 == 98) {
                  s = 21;
               } else if (LA13_6 == 102) {
                  s = 22;
               } else if (LA13_6 == 34) {
                  s = 23;
               } else if (LA13_6 == 39) {
                  s = 24;
               } else if (LA13_6 == 92) {
                  s = 25;
               } else if (LA13_6 == 62) {
                  s = 26;
               } else if (LA13_6 == 117) {
                  s = 27;
               } else if (LA13_6 == 125) {
                  s = 28;
               } else if (LA13_6 == 123) {
                  s = 29;
               } else if (LA13_6 >= 0 && LA13_6 <= 33 || LA13_6 >= 35 && LA13_6 <= 38 || LA13_6 >= 40 && LA13_6 <= 61 || LA13_6 >= 63 && LA13_6 <= 91 || LA13_6 >= 93 && LA13_6 <= 97 || LA13_6 >= 99 && LA13_6 <= 101 || LA13_6 >= 103 && LA13_6 <= 109 || LA13_6 >= 111 && LA13_6 <= 113 || LA13_6 == 115 || LA13_6 >= 118 && LA13_6 <= 122 || LA13_6 == 124 || LA13_6 >= 126 && LA13_6 <= 65535) {
                  s = 30;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA13_9 = _input.LA(1);
               s = -1;
               if (LA13_9 == 34) {
                  s = 31;
               } else if (LA13_9 == 92) {
                  s = 32;
               } else if (LA13_9 == 123) {
                  s = 33;
               } else if (LA13_9 == 39) {
                  s = 34;
               } else if (LA13_9 == 125) {
                  s = 35;
               } else if (LA13_9 >= 0 && LA13_9 <= 33 || LA13_9 >= 35 && LA13_9 <= 38 || LA13_9 >= 40 && LA13_9 <= 91 || LA13_9 >= 93 && LA13_9 <= 122 || LA13_9 == 124 || LA13_9 >= 126 && LA13_9 <= 65535) {
                  s = 36;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA13_10 = _input.LA(1);
               s = -1;
               if (LA13_10 == 34) {
                  s = 37;
               } else if (LA13_10 == 92) {
                  s = 38;
               } else if (LA13_10 == 125) {
                  s = 39;
               } else if (LA13_10 == 39) {
                  s = 40;
               } else if (LA13_10 == 123) {
                  s = 41;
               } else if (LA13_10 >= 0 && LA13_10 <= 33 || LA13_10 >= 35 && LA13_10 <= 38 || LA13_10 >= 40 && LA13_10 <= 91 || LA13_10 >= 93 && LA13_10 <= 122 || LA13_10 == 124 || LA13_10 >= 126 && LA13_10 <= 65535) {
                  s = 42;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA13_11 = _input.LA(1);
               s = -1;
               if (LA13_11 == 34) {
                  s = 43;
               } else if (LA13_11 == 92) {
                  s = 44;
               } else if (LA13_11 == 125) {
                  s = 45;
               } else if (LA13_11 == 123) {
                  s = 46;
               } else if (LA13_11 == 39) {
                  s = 47;
               } else if (LA13_11 >= 0 && LA13_11 <= 33 || LA13_11 >= 35 && LA13_11 <= 38 || LA13_11 >= 40 && LA13_11 <= 91 || LA13_11 >= 93 && LA13_11 <= 122 || LA13_11 == 124 || LA13_11 >= 126 && LA13_11 <= 65535) {
                  s = 48;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA13_12 = _input.LA(1);
               s = -1;
               if (LA13_12 == 110) {
                  s = 49;
               } else if (LA13_12 == 114) {
                  s = 50;
               } else if (LA13_12 == 116) {
                  s = 51;
               } else if (LA13_12 == 98) {
                  s = 52;
               } else if (LA13_12 == 102) {
                  s = 53;
               } else if (LA13_12 == 34) {
                  s = 54;
               } else if (LA13_12 == 39) {
                  s = 55;
               } else if (LA13_12 == 92) {
                  s = 56;
               } else if (LA13_12 == 62) {
                  s = 57;
               } else if (LA13_12 == 117) {
                  s = 58;
               } else if (LA13_12 == 125) {
                  s = 59;
               } else if (LA13_12 == 123) {
                  s = 60;
               } else if (LA13_12 >= 0 && LA13_12 <= 33 || LA13_12 >= 35 && LA13_12 <= 38 || LA13_12 >= 40 && LA13_12 <= 61 || LA13_12 >= 63 && LA13_12 <= 91 || LA13_12 >= 93 && LA13_12 <= 97 || LA13_12 >= 99 && LA13_12 <= 101 || LA13_12 >= 103 && LA13_12 <= 109 || LA13_12 >= 111 && LA13_12 <= 113 || LA13_12 == 115 || LA13_12 >= 118 && LA13_12 <= 122 || LA13_12 == 124 || LA13_12 >= 126 && LA13_12 <= 65535) {
                  s = 61;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA13_14 = _input.LA(1);
               s = -1;
               if (LA13_14 == 39) {
                  s = 62;
               } else if (LA13_14 >= 0 && LA13_14 <= 38 || LA13_14 >= 40 && LA13_14 <= 65535) {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA13_15 = _input.LA(1);
               s = -1;
               if (LA13_15 == 39) {
                  s = 67;
               } else if (LA13_15 >= 0 && LA13_15 <= 38 || LA13_15 >= 40 && LA13_15 <= 65535) {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA13_16 = _input.LA(1);
               s = -1;
               if (LA13_16 == 39) {
                  s = 73;
               } else if (LA13_16 >= 0 && LA13_16 <= 38 || LA13_16 >= 40 && LA13_16 <= 65535) {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
         }

         NoViableAltException nvae = new NoViableAltException(this.getDescription(), 13, s, _input);
         this.error(nvae);
         throw nvae;
      }
   }
}
