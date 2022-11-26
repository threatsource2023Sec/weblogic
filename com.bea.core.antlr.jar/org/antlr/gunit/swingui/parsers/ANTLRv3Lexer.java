package org.antlr.gunit.swingui.parsers;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;

public class ANTLRv3Lexer extends Lexer {
   public static final int EOF = -1;
   public static final int T__65 = 65;
   public static final int T__66 = 66;
   public static final int T__67 = 67;
   public static final int T__68 = 68;
   public static final int T__69 = 69;
   public static final int T__70 = 70;
   public static final int T__71 = 71;
   public static final int T__72 = 72;
   public static final int T__73 = 73;
   public static final int T__74 = 74;
   public static final int T__75 = 75;
   public static final int T__76 = 76;
   public static final int T__77 = 77;
   public static final int T__78 = 78;
   public static final int T__79 = 79;
   public static final int T__80 = 80;
   public static final int T__81 = 81;
   public static final int T__82 = 82;
   public static final int T__83 = 83;
   public static final int T__84 = 84;
   public static final int T__85 = 85;
   public static final int T__86 = 86;
   public static final int T__87 = 87;
   public static final int T__88 = 88;
   public static final int T__89 = 89;
   public static final int T__90 = 90;
   public static final int T__91 = 91;
   public static final int T__92 = 92;
   public static final int T__93 = 93;
   public static final int ACTION = 4;
   public static final int ACTION_CHAR_LITERAL = 5;
   public static final int ACTION_ESC = 6;
   public static final int ACTION_STRING_LITERAL = 7;
   public static final int ALT = 8;
   public static final int ARG = 9;
   public static final int ARGLIST = 10;
   public static final int ARG_ACTION = 11;
   public static final int BACKTRACK_SEMPRED = 12;
   public static final int BANG = 13;
   public static final int BLOCK = 14;
   public static final int CHAR_LITERAL = 15;
   public static final int CHAR_RANGE = 16;
   public static final int CLOSURE = 17;
   public static final int COMBINED_GRAMMAR = 18;
   public static final int DOC_COMMENT = 19;
   public static final int DOUBLE_ANGLE_STRING_LITERAL = 20;
   public static final int DOUBLE_QUOTE_STRING_LITERAL = 21;
   public static final int EOA = 22;
   public static final int EOB = 23;
   public static final int EOR = 24;
   public static final int EPSILON = 25;
   public static final int ESC = 26;
   public static final int FRAGMENT = 27;
   public static final int GATED_SEMPRED = 28;
   public static final int ID = 29;
   public static final int INITACTION = 30;
   public static final int INT = 31;
   public static final int LABEL = 32;
   public static final int LEXER = 33;
   public static final int LEXER_GRAMMAR = 34;
   public static final int LITERAL_CHAR = 35;
   public static final int ML_COMMENT = 36;
   public static final int NESTED_ACTION = 37;
   public static final int NESTED_ARG_ACTION = 38;
   public static final int OPTIONAL = 39;
   public static final int OPTIONS = 40;
   public static final int PARSER = 41;
   public static final int PARSER_GRAMMAR = 42;
   public static final int POSITIVE_CLOSURE = 43;
   public static final int RANGE = 44;
   public static final int RET = 45;
   public static final int REWRITE = 46;
   public static final int ROOT = 47;
   public static final int RULE = 48;
   public static final int RULE_REF = 49;
   public static final int SCOPE = 50;
   public static final int SEMPRED = 51;
   public static final int SL_COMMENT = 52;
   public static final int SRC = 53;
   public static final int STRING_LITERAL = 54;
   public static final int SYNPRED = 55;
   public static final int SYN_SEMPRED = 56;
   public static final int TEMPLATE = 57;
   public static final int TOKENS = 58;
   public static final int TOKEN_REF = 59;
   public static final int TREE_BEGIN = 60;
   public static final int TREE_GRAMMAR = 61;
   public static final int WS = 62;
   public static final int WS_LOOP = 63;
   public static final int XDIGIT = 64;
   protected DFA2 dfa2;
   protected DFA22 dfa22;
   static final String DFA2_eotS = "\u0012\uffff\u0001\u0002\u0004\uffff\u0001\u0002\u0004\uffff";
   static final String DFA2_eofS = "\u001c\uffff";
   static final String DFA2_minS = "\u0002\u0000\u0001\uffff\u0016\u0000\u0001\uffff\u0001\u0000\u0001\uffff";
   static final String DFA2_maxS = "\u0002\uffff\u0001\uffff\u0016\uffff\u0001\uffff\u0001\uffff\u0001\uffff";
   static final String DFA2_acceptS = "\u0002\uffff\u0001\u0002\u0016\uffff\u0001\u0001\u0001\uffff\u0001\u0001";
   static final String DFA2_specialS = "\u0001\u0011\u0001\n\u0001\uffff\u0001\t\u0001\f\u0001\u000b\u0001\u0000\u0001\u0002\u0001\u0003\u0001\u0014\u0001\u0015\u0001\u0017\u0001\u0012\u0001\u0005\u0001\u0001\u0001\u0016\u0001\u0007\u0001\u0006\u0001\u0010\u0001\b\u0001\u000f\u0001\u000e\u0001\u0013\u0001\u0004\u0001\u0018\u0001\uffff\u0001\r\u0001\uffff}>";
   static final String[] DFA2_transitionS = new String[]{" \u0002\u0001\u0001\uffdf\u0002", "$\u0002\u0001\u0003ￛ\u0002", "", "A\u0002\u0001\u0004ﾾ\u0002", "N\u0002\u0001\u0005ﾱ\u0002", "T\u0002\u0001\u0006ﾫ\u0002", "L\u0002\u0001\u0007ﾳ\u0002", "R\u0002\u0001\bﾭ\u0002", " \u0002\u0001\t\uffdf\u0002", "s\u0002\u0001\nﾌ\u0002", "r\u0002\u0001\u000bﾍ\u0002", "c\u0002\u0001\fﾜ\u0002", " \u0002\u0001\r\uffdf\u0002", "\"\u0002\u0001\u000e\uffdd\u0002", "\n\u0013\u0001\u0012\u0002\u0013\u0001\u0010\u0014\u0013\u0001\u00119\u0013\u0001\u000fﾣ\u0013", "\n\u0018\u0001\u0017\u0002\u0018\u0001\u0016\u0014\u0018\u0001\u0015\u0004\u0018\u0001\u0014\uffd8\u0018", "\n\u0019\u0001\u0012\ufff5\u0019", " \u0002\u0001\u001a\uffdf\u0002", "\u0000\u0019", "\n\u0013\u0001\u0012\u0002\u0013\u0001\u0010\u0014\u0013\u0001\u00119\u0013\u0001\u000fﾣ\u0013", "\n\u0013\u0001\u0012\u0002\u0013\u0001\u0010\u0014\u0013\u0001\u00119\u0013\u0001\u000fﾣ\u0013", "\n\u0013\u0001\u0012\u0002\u0013\u0001\u0010\u0014\u0013\u0001\u00119\u0013\u0001\u000fﾣ\u0013", "\n\u0019\u0001\u0012\ufff5\u0019", "\u0000\u0019", "\n\u0013\u0001\u0012\u0002\u0013\u0001\u0010\u0014\u0013\u0001\u00119\u0013\u0001\u000fﾣ\u0013", "", "0\u0002\n\u001bￆ\u0002", ""};
   static final short[] DFA2_eot = DFA.unpackEncodedString("\u0012\uffff\u0001\u0002\u0004\uffff\u0001\u0002\u0004\uffff");
   static final short[] DFA2_eof = DFA.unpackEncodedString("\u001c\uffff");
   static final char[] DFA2_min = DFA.unpackEncodedStringToUnsignedChars("\u0002\u0000\u0001\uffff\u0016\u0000\u0001\uffff\u0001\u0000\u0001\uffff");
   static final char[] DFA2_max = DFA.unpackEncodedStringToUnsignedChars("\u0002\uffff\u0001\uffff\u0016\uffff\u0001\uffff\u0001\uffff\u0001\uffff");
   static final short[] DFA2_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0016\uffff\u0001\u0001\u0001\uffff\u0001\u0001");
   static final short[] DFA2_special = DFA.unpackEncodedString("\u0001\u0011\u0001\n\u0001\uffff\u0001\t\u0001\f\u0001\u000b\u0001\u0000\u0001\u0002\u0001\u0003\u0001\u0014\u0001\u0015\u0001\u0017\u0001\u0012\u0001\u0005\u0001\u0001\u0001\u0016\u0001\u0007\u0001\u0006\u0001\u0010\u0001\b\u0001\u000f\u0001\u000e\u0001\u0013\u0001\u0004\u0001\u0018\u0001\uffff\u0001\r\u0001\uffff}>");
   static final short[][] DFA2_transition;
   static final String DFA22_eotS = "\u0002\uffff\u0001$\u0001)\u0001\uffff\u0001+\u0001$\u0004\uffff\u0001.\u0001\uffff\u00010\u0001\uffff\u00012\u0002\uffff\u0006$\u000b\uffff\u0001$\u0002\uffff\u0002$\u0004\uffff\u0001$\u0006\uffff\n$\u0004\uffff\u000f$\r\uffff\r$\u0001{\u0001$\u0002\uffff\u0003$\u0001\u0081\u0001\u0082\u0001$\u0001\u0084\u0006$\u0001\uffff\u0001$\u0001\uffff\u0003$\u0002\uffff\u0001$\u0001\uffff\u0001\u0091\u0002$\u0001\u0094\u0001$\u0001\u0096\u0001$\u0001\uffff\u0002$\u0001\u009b\u0001\u009c\u0001\uffff\u0001\u009d\u0001$\u0001\uffff\u0001\u009f\u0003\uffff\u0001$\u0001¡\u0003\uffff\u0001$\u0003\uffff\u0001£\u0001\uffff";
   static final String DFA22_eofS = "¤\uffff";
   static final String DFA22_minS = "\u0001\t\u0001\uffff\u0001i\u0001.\u0001\uffff\u0001(\u0001c\u0004\uffff\u0001=\u0001\uffff\u0001:\u0001\uffff\u0001>\u0002\uffff\u0001a\u0001r\u0001e\u0001a\u0001e\u0001h\u0003\uffff\u0001*\u0001\u0000\u0006\uffff\u0001p\u0002\uffff\u0001a\u0001n\u0004\uffff\u0001o\u0006\uffff\u0001t\u0001a\u0001x\u0001r\u0001i\u0001b\u0001t\u0001r\u0001e\u0001k\u0002\uffff\u0002\u0000\u0001t\u0001g\u0001a\u0001p\u0001c\u0001m\u0001e\u0001s\u0001v\u0001t\u0001l\u0001u\u0001o\u0002e\u000b\u0000\u0002\uffff\u0001i\u0001m\u0001l\u0001e\u0001h\u0001m\u0001r\u0001e\u0001a\u0001e\u0001i\u0001r\u0001w\u00010\u0001n\u0001\u0000\u0001\uffff\u0001o\u0001e\u0001l\u00020\u0001a\u00010\u0001r\u0001t\u0002c\u0001n\u0001s\u0001\uffff\u0001s\u0001\u0000\u0002n\u0001y\u0002\uffff\u0001r\u0001\uffff\u00010\u0001e\u0001t\u00010\u0001s\u00010\u0001\t\u0001\u0000\u0001s\u0001t\u00020\u0001\uffff\u00010\u0001e\u0001\uffff\u00010\u0002\uffff\u0001\u0000\u0001\t\u00010\u0003\uffff\u0001d\u0003\uffff\u00010\u0001\uffff";
   static final String DFA22_maxS = "\u0001~\u0001\uffff\u0001r\u0001.\u0001\uffff\u0001(\u0001c\u0004\uffff\u0001=\u0001\uffff\u0001:\u0001\uffff\u0001>\u0002\uffff\u0001a\u0001r\u0001e\u0001u\u0001e\u0001r\u0003\uffff\u0001/\u0001\uffff\u0006\uffff\u0001p\u0002\uffff\u0001a\u0001n\u0004\uffff\u0001o\u0006\uffff\u0001t\u0001a\u0001x\u0001r\u0001o\u0001b\u0001t\u0001r\u0001e\u0001k\u0002\uffff\u0002\uffff\u0001t\u0001g\u0001a\u0001p\u0001c\u0001m\u0001e\u0001s\u0001v\u0001t\u0001l\u0001u\u0001o\u0002e\u000b\uffff\u0002\uffff\u0001i\u0001m\u0001l\u0001e\u0001h\u0001m\u0001r\u0001e\u0001a\u0001e\u0001i\u0001r\u0001w\u0001z\u0001n\u0001\uffff\u0001\uffff\u0001o\u0001e\u0001l\u0002z\u0001a\u0001z\u0001r\u0001t\u0002c\u0001n\u0001s\u0001\uffff\u0001s\u0001\uffff\u0002n\u0001y\u0002\uffff\u0001r\u0001\uffff\u0001z\u0001e\u0001t\u0001z\u0001s\u0001z\u0001{\u0001\uffff\u0001s\u0001t\u0002z\u0001\uffff\u0001z\u0001e\u0001\uffff\u0001z\u0002\uffff\u0001\uffff\u0001{\u0001z\u0003\uffff\u0001d\u0003\uffff\u0001z\u0001\uffff";
   static final String DFA22_acceptS = "\u0001\uffff\u0001\u0001\u0002\uffff\u0001\u0004\u0002\uffff\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\uffff\u0001\u000e\u0001\uffff\u0001\u0012\u0001\uffff\u0001\u0015\u0001\u0016\u0006\uffff\u0001\"\u0001#\u0001$\u0002\uffff\u0001)\u0001*\u0001+\u0001,\u0001-\u0001.\u0001\uffff\u0001/\u00012\u0002\uffff\u0001\u0003\u0001\u000f\u0001\u0007\u0001\u0005\u0001\uffff\u0001\r\u0001\f\u0001\u0011\u0001\u0010\u0001\u0014\u0001\u0013\n\uffff\u0001%\u0001&\u001c\uffff\u0001'\u0001(\u0010\uffff\u0001'\r\uffff\u0001!\u0005\uffff\u0001\u0006\u0001\u0017\u0001\uffff\u0001\u001a\f\uffff\u0001\u001b\u0002\uffff\u0001\u001e\u0001\uffff\u0001 \u00011\u0003\uffff\u0001\u0018\u0001\u0019\u0001\u001c\u0001\uffff\u0001\u001f\u00010\u0001\u0002\u0001\uffff\u0001\u001d";
   static final String DFA22_specialS = "\u001c\uffff\u0001\u0004\"\uffff\u0001\u0000\u0001\u0005\u000f\uffff\u0001\u0003\u0001\u0002\u0001\u0001\u0001\u000e\u0001\f\u0001\u0011\u0001\u0010\u0001\n\u0001\t\u0001\u0007\u0001\u0006\u0011\uffff\u0001\u000b\u0010\uffff\u0001\r\u000e\uffff\u0001\u000f\u000b\uffff\u0001\b\u000b\uffff}>";
   static final String[] DFA22_transitionS;
   static final short[] DFA22_eot;
   static final short[] DFA22_eof;
   static final char[] DFA22_min;
   static final char[] DFA22_max;
   static final short[] DFA22_accept;
   static final short[] DFA22_special;
   static final short[][] DFA22_transition;

   public Lexer[] getDelegates() {
      return new Lexer[0];
   }

   public ANTLRv3Lexer() {
      this.dfa2 = new DFA2(this);
      this.dfa22 = new DFA22(this);
   }

   public ANTLRv3Lexer(CharStream input) {
      this(input, new RecognizerSharedState());
   }

   public ANTLRv3Lexer(CharStream input, RecognizerSharedState state) {
      super(input, state);
      this.dfa2 = new DFA2(this);
      this.dfa22 = new DFA22(this);
   }

   public String getGrammarFileName() {
      return "org\\antlr\\gunit\\swingui\\parsers\\ANTLRv3.g";
   }

   public final void mBANG() throws RecognitionException {
      try {
         int _type = 13;
         int _channel = 0;
         this.match(33);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mFRAGMENT() throws RecognitionException {
      try {
         int _type = 27;
         int _channel = 0;
         this.match("fragment");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mRANGE() throws RecognitionException {
      try {
         int _type = 44;
         int _channel = 0;
         this.match("..");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mREWRITE() throws RecognitionException {
      try {
         int _type = 46;
         int _channel = 0;
         this.match("->");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mROOT() throws RecognitionException {
      try {
         int _type = 47;
         int _channel = 0;
         this.match(94);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mSCOPE() throws RecognitionException {
      try {
         int _type = 50;
         int _channel = 0;
         this.match("scope");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mTREE_BEGIN() throws RecognitionException {
      try {
         int _type = 60;
         int _channel = 0;
         this.match("^(");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__65() throws RecognitionException {
      try {
         int _type = 65;
         int _channel = 0;
         this.match(36);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__66() throws RecognitionException {
      try {
         int _type = 66;
         int _channel = 0;
         this.match(40);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__67() throws RecognitionException {
      try {
         int _type = 67;
         int _channel = 0;
         this.match(41);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__68() throws RecognitionException {
      try {
         int _type = 68;
         int _channel = 0;
         this.match(42);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__69() throws RecognitionException {
      try {
         int _type = 69;
         int _channel = 0;
         this.match(43);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__70() throws RecognitionException {
      try {
         int _type = 70;
         int _channel = 0;
         this.match("+=");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__71() throws RecognitionException {
      try {
         int _type = 71;
         int _channel = 0;
         this.match(44);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__72() throws RecognitionException {
      try {
         int _type = 72;
         int _channel = 0;
         this.match(46);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__73() throws RecognitionException {
      try {
         int _type = 73;
         int _channel = 0;
         this.match(58);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__74() throws RecognitionException {
      try {
         int _type = 74;
         int _channel = 0;
         this.match("::");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__75() throws RecognitionException {
      try {
         int _type = 75;
         int _channel = 0;
         this.match(59);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__76() throws RecognitionException {
      try {
         int _type = 76;
         int _channel = 0;
         this.match(61);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__77() throws RecognitionException {
      try {
         int _type = 77;
         int _channel = 0;
         this.match("=>");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__78() throws RecognitionException {
      try {
         int _type = 78;
         int _channel = 0;
         this.match(63);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__79() throws RecognitionException {
      try {
         int _type = 79;
         int _channel = 0;
         this.match(64);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__80() throws RecognitionException {
      try {
         int _type = 80;
         int _channel = 0;
         this.match("catch");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__81() throws RecognitionException {
      try {
         int _type = 81;
         int _channel = 0;
         this.match("finally");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__82() throws RecognitionException {
      try {
         int _type = 82;
         int _channel = 0;
         this.match("grammar");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__83() throws RecognitionException {
      try {
         int _type = 83;
         int _channel = 0;
         this.match("lexer");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__84() throws RecognitionException {
      try {
         int _type = 84;
         int _channel = 0;
         this.match("parser");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__85() throws RecognitionException {
      try {
         int _type = 85;
         int _channel = 0;
         this.match("private");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__86() throws RecognitionException {
      try {
         int _type = 86;
         int _channel = 0;
         this.match("protected");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__87() throws RecognitionException {
      try {
         int _type = 87;
         int _channel = 0;
         this.match("public");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__88() throws RecognitionException {
      try {
         int _type = 88;
         int _channel = 0;
         this.match("returns");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__89() throws RecognitionException {
      try {
         int _type = 89;
         int _channel = 0;
         this.match("throws");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__90() throws RecognitionException {
      try {
         int _type = 90;
         int _channel = 0;
         this.match("tree");
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__91() throws RecognitionException {
      try {
         int _type = 91;
         int _channel = 0;
         this.match(124);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__92() throws RecognitionException {
      try {
         int _type = 92;
         int _channel = 0;
         this.match(125);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mT__93() throws RecognitionException {
      try {
         int _type = 93;
         int _channel = 0;
         this.match(126);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mSL_COMMENT() throws RecognitionException {
      try {
         int _type = 52;
         int _channel = false;
         this.match("//");
         int alt2 = true;
         int alt2 = this.dfa2.predict(this.input);
         byte alt3;
         int LA3_0;
         switch (alt2) {
            case 1:
               this.match(" $ANTLR ");
               this.mSRC();
               break;
            case 2:
               label157:
               while(true) {
                  alt3 = 2;
                  LA3_0 = this.input.LA(1);
                  if (LA3_0 >= 0 && LA3_0 <= 9 || LA3_0 >= 11 && LA3_0 <= 12 || LA3_0 >= 14 && LA3_0 <= 65535) {
                     alt3 = 1;
                  }

                  switch (alt3) {
                     case 1:
                        if ((this.input.LA(1) < 0 || this.input.LA(1) > 9) && (this.input.LA(1) < 11 || this.input.LA(1) > 12) && (this.input.LA(1) < 14 || this.input.LA(1) > 65535)) {
                           MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                           this.recover(mse);
                           throw mse;
                        }

                        this.input.consume();
                        break;
                     default:
                        break label157;
                  }
               }
         }

         alt3 = 2;
         LA3_0 = this.input.LA(1);
         if (LA3_0 == 13) {
            alt3 = 1;
         }

         switch (alt3) {
            case 1:
               this.match(13);
            default:
               this.match(10);
               int _channel = 99;
               this.state.type = _type;
               this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mML_COMMENT() throws RecognitionException {
      try {
         int _type = 36;
         int _channel = 0;
         this.match("/*");
         if (this.input.LA(1) == 42) {
            _type = 19;
         } else {
            _channel = 99;
         }

         while(true) {
            int alt4 = 2;
            int LA4_0 = this.input.LA(1);
            if (LA4_0 == 42) {
               int LA4_1 = this.input.LA(2);
               if (LA4_1 == 47) {
                  alt4 = 2;
               } else if (LA4_1 >= 0 && LA4_1 <= 46 || LA4_1 >= 48 && LA4_1 <= 65535) {
                  alt4 = 1;
               }
            } else if (LA4_0 >= 0 && LA4_0 <= 41 || LA4_0 >= 43 && LA4_0 <= 65535) {
               alt4 = 1;
            }

            switch (alt4) {
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

   public final void mCHAR_LITERAL() throws RecognitionException {
      try {
         int _type = 15;
         int _channel = 0;
         this.match(39);
         this.mLITERAL_CHAR();
         this.match(39);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mSTRING_LITERAL() throws RecognitionException {
      try {
         int _type = 54;
         int _channel = 0;
         this.match(39);
         this.mLITERAL_CHAR();

         while(true) {
            int alt5 = 2;
            int LA5_0 = this.input.LA(1);
            if (LA5_0 >= 0 && LA5_0 <= 38 || LA5_0 >= 40 && LA5_0 <= 65535) {
               alt5 = 1;
            }

            switch (alt5) {
               case 1:
                  this.mLITERAL_CHAR();
                  break;
               default:
                  this.match(39);
                  this.state.type = _type;
                  this.state.channel = _channel;
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mLITERAL_CHAR() throws RecognitionException {
      try {
         int alt6 = true;
         int LA6_0 = this.input.LA(1);
         byte alt6;
         if (LA6_0 == 92) {
            alt6 = 1;
         } else {
            if ((LA6_0 < 0 || LA6_0 > 38) && (LA6_0 < 40 || LA6_0 > 91) && (LA6_0 < 93 || LA6_0 > 65535)) {
               NoViableAltException nvae = new NoViableAltException("", 6, 0, this.input);
               throw nvae;
            }

            alt6 = 2;
         }

         switch (alt6) {
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

      } finally {
         ;
      }
   }

   public final void mDOUBLE_QUOTE_STRING_LITERAL() throws RecognitionException {
      try {
         int _type = 21;
         int _channel = 0;
         this.match(34);

         while(true) {
            int alt7 = 3;
            int LA7_0 = this.input.LA(1);
            if (LA7_0 == 92) {
               alt7 = 1;
            } else if (LA7_0 >= 0 && LA7_0 <= 33 || LA7_0 >= 35 && LA7_0 <= 91 || LA7_0 >= 93 && LA7_0 <= 65535) {
               alt7 = 2;
            }

            switch (alt7) {
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

   public final void mDOUBLE_ANGLE_STRING_LITERAL() throws RecognitionException {
      try {
         int _type = 20;
         int _channel = 0;
         this.match("<<");

         while(true) {
            int alt8 = 2;
            int LA8_0 = this.input.LA(1);
            if (LA8_0 == 62) {
               int LA8_1 = this.input.LA(2);
               if (LA8_1 == 62) {
                  alt8 = 2;
               } else if (LA8_1 >= 0 && LA8_1 <= 61 || LA8_1 >= 63 && LA8_1 <= 65535) {
                  alt8 = 1;
               }
            } else if (LA8_0 >= 0 && LA8_0 <= 61 || LA8_0 >= 63 && LA8_0 <= 65535) {
               alt8 = 1;
            }

            switch (alt8) {
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

   public final void mESC() throws RecognitionException {
      try {
         this.match(92);
         int alt9 = true;
         int LA9_0 = this.input.LA(1);
         byte alt9;
         if (LA9_0 == 110) {
            alt9 = 1;
         } else if (LA9_0 == 114) {
            alt9 = 2;
         } else if (LA9_0 == 116) {
            alt9 = 3;
         } else if (LA9_0 == 98) {
            alt9 = 4;
         } else if (LA9_0 == 102) {
            alt9 = 5;
         } else if (LA9_0 == 34) {
            alt9 = 6;
         } else if (LA9_0 == 39) {
            alt9 = 7;
         } else if (LA9_0 == 92) {
            alt9 = 8;
         } else if (LA9_0 == 62) {
            alt9 = 9;
         } else if (LA9_0 == 117) {
            int LA9_10 = this.input.LA(2);
            if ((LA9_10 < 48 || LA9_10 > 57) && (LA9_10 < 65 || LA9_10 > 70) && (LA9_10 < 97 || LA9_10 > 102)) {
               alt9 = 11;
            } else {
               alt9 = 10;
            }
         } else {
            if ((LA9_0 < 0 || LA9_0 > 33) && (LA9_0 < 35 || LA9_0 > 38) && (LA9_0 < 40 || LA9_0 > 61) && (LA9_0 < 63 || LA9_0 > 91) && (LA9_0 < 93 || LA9_0 > 97) && (LA9_0 < 99 || LA9_0 > 101) && (LA9_0 < 103 || LA9_0 > 109) && (LA9_0 < 111 || LA9_0 > 113) && LA9_0 != 115 && (LA9_0 < 118 || LA9_0 > 65535)) {
               NoViableAltException nvae = new NoViableAltException("", 9, 0, this.input);
               throw nvae;
            }

            alt9 = 11;
         }

         switch (alt9) {
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

   public final void mINT() throws RecognitionException {
      try {
         int _type = 31;
         int _channel = 0;
         int cnt10 = 0;

         while(true) {
            int alt10 = 2;
            int LA10_0 = this.input.LA(1);
            if (LA10_0 >= 48 && LA10_0 <= 57) {
               alt10 = 1;
            }

            switch (alt10) {
               case 1:
                  if (this.input.LA(1) < 48 || this.input.LA(1) > 57) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  ++cnt10;
                  break;
               default:
                  if (cnt10 >= 1) {
                     this.state.type = _type;
                     this.state.channel = _channel;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(10, this.input);
                  throw eee;
            }
         }
      } finally {
         ;
      }
   }

   public final void mARG_ACTION() throws RecognitionException {
      try {
         int _type = 11;
         int _channel = 0;
         this.mNESTED_ARG_ACTION();
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mNESTED_ARG_ACTION() throws RecognitionException {
      try {
         this.match(91);

         while(true) {
            int alt11 = 5;
            int LA11_0 = this.input.LA(1);
            if (LA11_0 == 93) {
               alt11 = 5;
            } else if (LA11_0 == 91) {
               alt11 = 1;
            } else if (LA11_0 == 34) {
               alt11 = 2;
            } else if (LA11_0 == 39) {
               alt11 = 3;
            } else if (LA11_0 >= 0 && LA11_0 <= 33 || LA11_0 >= 35 && LA11_0 <= 38 || LA11_0 >= 40 && LA11_0 <= 90 || LA11_0 == 92 || LA11_0 >= 94 && LA11_0 <= 65535) {
               alt11 = 4;
            }

            switch (alt11) {
               case 1:
                  this.mNESTED_ARG_ACTION();
                  break;
               case 2:
                  this.mACTION_STRING_LITERAL();
                  break;
               case 3:
                  this.mACTION_CHAR_LITERAL();
                  break;
               case 4:
                  this.matchAny();
                  break;
               default:
                  this.match(93);
                  this.setText(this.getText().substring(1, this.getText().length() - 1));
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
         int alt12 = 2;
         int LA12_0 = this.input.LA(1);
         if (LA12_0 == 63) {
            alt12 = 1;
         }

         switch (alt12) {
            case 1:
               this.match(63);
               _type = 51;
            default:
               this.state.type = _type;
               this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mNESTED_ACTION() throws RecognitionException {
      try {
         this.match(123);

         while(true) {
            int alt13 = 7;
            int LA13_0 = this.input.LA(1);
            if (LA13_0 == 125) {
               alt13 = 7;
            } else if (LA13_0 == 123) {
               alt13 = 1;
            } else {
               int LA13_5;
               if (LA13_0 == 47) {
                  LA13_5 = this.input.LA(2);
                  if (LA13_5 == 47) {
                     alt13 = 2;
                  } else if (LA13_5 == 42) {
                     alt13 = 3;
                  } else if (LA13_5 >= 0 && LA13_5 <= 41 || LA13_5 >= 43 && LA13_5 <= 46 || LA13_5 >= 48 && LA13_5 <= 65535) {
                     alt13 = 6;
                  }
               } else if (LA13_0 == 34) {
                  LA13_5 = this.input.LA(2);
                  if (LA13_5 == 92) {
                     alt13 = 4;
                  } else if (LA13_5 == 125) {
                     alt13 = 4;
                  } else if (LA13_5 == 34) {
                     alt13 = 4;
                  } else if (LA13_5 == 123) {
                     alt13 = 4;
                  } else if (LA13_5 == 47) {
                     alt13 = 4;
                  } else if (LA13_5 == 39) {
                     alt13 = 4;
                  } else if (LA13_5 >= 0 && LA13_5 <= 33 || LA13_5 >= 35 && LA13_5 <= 38 || LA13_5 >= 40 && LA13_5 <= 46 || LA13_5 >= 48 && LA13_5 <= 91 || LA13_5 >= 93 && LA13_5 <= 122 || LA13_5 == 124 || LA13_5 >= 126 && LA13_5 <= 65535) {
                     alt13 = 4;
                  }
               } else if (LA13_0 == 39) {
                  LA13_5 = this.input.LA(2);
                  if (LA13_5 == 92) {
                     alt13 = 5;
                  } else if (LA13_5 == 125) {
                     alt13 = 5;
                  } else if (LA13_5 == 123) {
                     alt13 = 5;
                  } else if (LA13_5 == 47) {
                     alt13 = 5;
                  } else if (LA13_5 == 34) {
                     alt13 = 5;
                  } else if ((LA13_5 < 0 || LA13_5 > 33) && (LA13_5 < 35 || LA13_5 > 38) && (LA13_5 < 40 || LA13_5 > 46) && (LA13_5 < 48 || LA13_5 > 91) && (LA13_5 < 93 || LA13_5 > 122) && LA13_5 != 124 && (LA13_5 < 126 || LA13_5 > 65535)) {
                     if (LA13_5 == 39) {
                        alt13 = 6;
                     }
                  } else {
                     alt13 = 5;
                  }
               } else if (LA13_0 >= 0 && LA13_0 <= 33 || LA13_0 >= 35 && LA13_0 <= 38 || LA13_0 >= 40 && LA13_0 <= 46 || LA13_0 >= 48 && LA13_0 <= 122 || LA13_0 == 124 || LA13_0 >= 126 && LA13_0 <= 65535) {
                  alt13 = 6;
               }
            }

            switch (alt13) {
               case 1:
                  this.mNESTED_ACTION();
                  break;
               case 2:
                  this.mSL_COMMENT();
                  break;
               case 3:
                  this.mML_COMMENT();
                  break;
               case 4:
                  this.mACTION_STRING_LITERAL();
                  break;
               case 5:
                  this.mACTION_CHAR_LITERAL();
                  break;
               case 6:
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

   public final void mACTION_CHAR_LITERAL() throws RecognitionException {
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
               this.mACTION_ESC();
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

   public final void mACTION_STRING_LITERAL() throws RecognitionException {
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
                  this.mACTION_ESC();
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

   public final void mACTION_ESC() throws RecognitionException {
      try {
         int alt16 = true;
         int LA16_0 = this.input.LA(1);
         if (LA16_0 != 92) {
            NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
            throw nvae;
         } else {
            int LA16_1 = this.input.LA(2);
            byte alt16;
            if (LA16_1 == 39) {
               alt16 = 1;
            } else if (LA16_1 == 34) {
               alt16 = 2;
            } else {
               if ((LA16_1 < 0 || LA16_1 > 33) && (LA16_1 < 35 || LA16_1 > 38) && (LA16_1 < 40 || LA16_1 > 65535)) {
                  int nvaeMark = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 16, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               alt16 = 3;
            }

            switch (alt16) {
               case 1:
                  this.match("\\'");
                  break;
               case 2:
                  this.match(92);
                  this.match(34);
                  break;
               case 3:
                  this.match(92);
                  if ((this.input.LA(1) < 0 || this.input.LA(1) > 33) && (this.input.LA(1) < 35 || this.input.LA(1) > 38) && (this.input.LA(1) < 40 || this.input.LA(1) > 65535)) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
            }

         }
      } finally {
         ;
      }
   }

   public final void mTOKEN_REF() throws RecognitionException {
      try {
         int _type = 59;
         int _channel = 0;
         this.matchRange(65, 90);

         while(true) {
            int alt17 = 2;
            int LA17_0 = this.input.LA(1);
            if (LA17_0 >= 48 && LA17_0 <= 57 || LA17_0 >= 65 && LA17_0 <= 90 || LA17_0 == 95 || LA17_0 >= 97 && LA17_0 <= 122) {
               alt17 = 1;
            }

            switch (alt17) {
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
         int _type = 49;
         int _channel = 0;
         this.matchRange(97, 122);

         while(true) {
            int alt18 = 2;
            int LA18_0 = this.input.LA(1);
            if (LA18_0 >= 48 && LA18_0 <= 57 || LA18_0 >= 65 && LA18_0 <= 90 || LA18_0 == 95 || LA18_0 >= 97 && LA18_0 <= 122) {
               alt18 = 1;
            }

            switch (alt18) {
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

   public final void mOPTIONS() throws RecognitionException {
      try {
         int _type = 40;
         int _channel = 0;
         this.match("options");
         this.mWS_LOOP();
         this.match(123);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mTOKENS() throws RecognitionException {
      try {
         int _type = 58;
         int _channel = 0;
         this.match("tokens");
         this.mWS_LOOP();
         this.match(123);
         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mSRC() throws RecognitionException {
      try {
         CommonToken file = null;
         CommonToken line = null;
         this.match("src");
         this.match(32);
         int fileStart938 = this.getCharIndex();
         int fileStartLine938 = this.getLine();
         int fileStartCharPos938 = this.getCharPositionInLine();
         this.mACTION_STRING_LITERAL();
         file = new CommonToken(this.input, 0, 0, fileStart938, this.getCharIndex() - 1);
         file.setLine(fileStartLine938);
         file.setCharPositionInLine(fileStartCharPos938);
         this.match(32);
         int lineStart944 = this.getCharIndex();
         int lineStartLine944 = this.getLine();
         int lineStartCharPos944 = this.getCharPositionInLine();
         this.mINT();
         line = new CommonToken(this.input, 0, 0, lineStart944, this.getCharIndex() - 1);
         line.setLine(lineStartLine944);
         line.setCharPositionInLine(lineStartCharPos944);
      } finally {
         ;
      }
   }

   public final void mWS() throws RecognitionException {
      try {
         int _type = 62;
         int _channel = false;
         int cnt20 = 0;

         while(true) {
            int alt20 = 4;
            switch (this.input.LA(1)) {
               case 9:
                  alt20 = 2;
                  break;
               case 10:
               case 13:
                  alt20 = 3;
                  break;
               case 32:
                  alt20 = 1;
            }

            label72:
            switch (alt20) {
               case 1:
                  this.match(32);
                  break;
               case 2:
                  this.match(9);
                  break;
               case 3:
                  int alt19 = 2;
                  int LA19_0 = this.input.LA(1);
                  if (LA19_0 == 13) {
                     alt19 = 1;
                  }

                  switch (alt19) {
                     case 1:
                        this.match(13);
                     default:
                        this.match(10);
                        break label72;
                  }
               default:
                  if (cnt20 >= 1) {
                     int _channel = 99;
                     this.state.type = _type;
                     this.state.channel = _channel;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(20, this.input);
                  throw eee;
            }

            ++cnt20;
         }
      } finally {
         ;
      }
   }

   public final void mWS_LOOP() throws RecognitionException {
      try {
         while(true) {
            int alt21 = 4;
            int LA21_0 = this.input.LA(1);
            if ((LA21_0 < 9 || LA21_0 > 10) && LA21_0 != 13 && LA21_0 != 32) {
               if (LA21_0 == 47) {
                  int LA21_3 = this.input.LA(2);
                  if (LA21_3 == 47) {
                     alt21 = 2;
                  } else if (LA21_3 == 42) {
                     alt21 = 3;
                  }
               }
            } else {
               alt21 = 1;
            }

            switch (alt21) {
               case 1:
                  this.mWS();
                  break;
               case 2:
                  this.mSL_COMMENT();
                  break;
               case 3:
                  this.mML_COMMENT();
                  break;
               default:
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public void mTokens() throws RecognitionException {
      int alt22 = true;
      int alt22 = this.dfa22.predict(this.input);
      switch (alt22) {
         case 1:
            this.mBANG();
            break;
         case 2:
            this.mFRAGMENT();
            break;
         case 3:
            this.mRANGE();
            break;
         case 4:
            this.mREWRITE();
            break;
         case 5:
            this.mROOT();
            break;
         case 6:
            this.mSCOPE();
            break;
         case 7:
            this.mTREE_BEGIN();
            break;
         case 8:
            this.mT__65();
            break;
         case 9:
            this.mT__66();
            break;
         case 10:
            this.mT__67();
            break;
         case 11:
            this.mT__68();
            break;
         case 12:
            this.mT__69();
            break;
         case 13:
            this.mT__70();
            break;
         case 14:
            this.mT__71();
            break;
         case 15:
            this.mT__72();
            break;
         case 16:
            this.mT__73();
            break;
         case 17:
            this.mT__74();
            break;
         case 18:
            this.mT__75();
            break;
         case 19:
            this.mT__76();
            break;
         case 20:
            this.mT__77();
            break;
         case 21:
            this.mT__78();
            break;
         case 22:
            this.mT__79();
            break;
         case 23:
            this.mT__80();
            break;
         case 24:
            this.mT__81();
            break;
         case 25:
            this.mT__82();
            break;
         case 26:
            this.mT__83();
            break;
         case 27:
            this.mT__84();
            break;
         case 28:
            this.mT__85();
            break;
         case 29:
            this.mT__86();
            break;
         case 30:
            this.mT__87();
            break;
         case 31:
            this.mT__88();
            break;
         case 32:
            this.mT__89();
            break;
         case 33:
            this.mT__90();
            break;
         case 34:
            this.mT__91();
            break;
         case 35:
            this.mT__92();
            break;
         case 36:
            this.mT__93();
            break;
         case 37:
            this.mSL_COMMENT();
            break;
         case 38:
            this.mML_COMMENT();
            break;
         case 39:
            this.mCHAR_LITERAL();
            break;
         case 40:
            this.mSTRING_LITERAL();
            break;
         case 41:
            this.mDOUBLE_QUOTE_STRING_LITERAL();
            break;
         case 42:
            this.mDOUBLE_ANGLE_STRING_LITERAL();
            break;
         case 43:
            this.mINT();
            break;
         case 44:
            this.mARG_ACTION();
            break;
         case 45:
            this.mACTION();
            break;
         case 46:
            this.mTOKEN_REF();
            break;
         case 47:
            this.mRULE_REF();
            break;
         case 48:
            this.mOPTIONS();
            break;
         case 49:
            this.mTOKENS();
            break;
         case 50:
            this.mWS();
      }

   }

   static {
      int numStates = DFA2_transitionS.length;
      DFA2_transition = new short[numStates][];

      int i;
      for(i = 0; i < numStates; ++i) {
         DFA2_transition[i] = DFA.unpackEncodedString(DFA2_transitionS[i]);
      }

      DFA22_transitionS = new String[]{"\u0002%\u0002\uffff\u0001%\u0012\uffff\u0001%\u0001\u0001\u0001\u001d\u0001\uffff\u0001\u0007\u0002\uffff\u0001\u001c\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\u0004\u0001\u0003\u0001\u001b\n\u001f\u0001\r\u0001\u000e\u0001\u001e\u0001\u000f\u0001\uffff\u0001\u0010\u0001\u0011\u001a\"\u0001 \u0002\uffff\u0001\u0005\u0002\uffff\u0002$\u0001\u0012\u0002$\u0001\u0002\u0001\u0013\u0004$\u0001\u0014\u0002$\u0001#\u0001\u0015\u0001$\u0001\u0016\u0001\u0006\u0001\u0017\u0006$\u0001!\u0001\u0018\u0001\u0019\u0001\u001a", "", "\u0001'\b\uffff\u0001&", "\u0001(", "", "\u0001*", "\u0001,", "", "", "", "", "\u0001-", "", "\u0001/", "", "\u00011", "", "", "\u00013", "\u00014", "\u00015", "\u00016\u0010\uffff\u00017\u0002\uffff\u00018", "\u00019", "\u0001:\u0006\uffff\u0001<\u0002\uffff\u0001;", "", "", "", "\u0001>\u0004\uffff\u0001=", "'@\u0001\uffff4@\u0001?ﾣ@", "", "", "", "", "", "", "\u0001A", "", "", "\u0001B", "\u0001C", "", "", "", "", "\u0001D", "", "", "", "", "", "", "\u0001E", "\u0001F", "\u0001G", "\u0001H", "\u0001I\u0005\uffff\u0001J", "\u0001K", "\u0001L", "\u0001M", "\u0001N", "\u0001O", "", "", "\"Z\u0001U\u0004Z\u0001V\u0016Z\u0001X\u001dZ\u0001W\u0005Z\u0001S\u0003Z\u0001T\u0007Z\u0001P\u0003Z\u0001Q\u0001Z\u0001R\u0001YﾊZ", "'\\\u0001[\uffd8\\", "\u0001]", "\u0001^", "\u0001_", "\u0001`", "\u0001a", "\u0001b", "\u0001c", "\u0001d", "\u0001e", "\u0001f", "\u0001g", "\u0001h", "\u0001i", "\u0001j", "\u0001k", "'\\\u0001[\uffd8\\", "'\\\u0001[\uffd8\\", "'\\\u0001[\uffd8\\", "'\\\u0001[\uffd8\\", "'\\\u0001[\uffd8\\", "'\\\u0001[\uffd8\\", "'\\\u0001[\uffd8\\", "'\\\u0001[\uffd8\\", "'\\\u0001[\uffd8\\", "'\\\u0001[\b\\\nl\u0007\\\u0006l\u001a\\\u0006lﾙ\\", "'\\\u0001[\uffd8\\", "", "", "\u0001n", "\u0001o", "\u0001p", "\u0001q", "\u0001r", "\u0001s", "\u0001t", "\u0001u", "\u0001v", "\u0001w", "\u0001x", "\u0001y", "\u0001z", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "\u0001|", "0\\\n}\u0007\\\u0006}\u001a\\\u0006}ﾙ\\", "", "\u0001~", "\u0001\u007f", "\u0001\u0080", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "\u0001\u0083", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "\u0001\u0085", "\u0001\u0086", "\u0001\u0087", "\u0001\u0088", "\u0001\u0089", "\u0001\u008a", "", "\u0001\u008b", "0\\\n\u008c\u0007\\\u0006\u008c\u001a\\\u0006\u008cﾙ\\", "\u0001\u008d", "\u0001\u008e", "\u0001\u008f", "", "", "\u0001\u0090", "", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "\u0001\u0092", "\u0001\u0093", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "\u0001\u0095", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "\u0002\u0097\u0002\uffff\u0001\u0097\u0012\uffff\u0001\u0097\u000e\uffff\u0001\u0097K\uffff\u0001\u0097", "0\\\n\u0098\u0007\\\u0006\u0098\u001a\\\u0006\u0098ﾙ\\", "\u0001\u0099", "\u0001\u009a", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "\u0001\u009e", "", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "", "", "'\\\u0001[\uffd8\\", "\u0002 \u0002\uffff\u0001 \u0012\uffff\u0001 \u000e\uffff\u0001 K\uffff\u0001 ", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", "", "", "", "\u0001¢", "", "", "", "\n$\u0007\uffff\u001a$\u0004\uffff\u0001$\u0001\uffff\u001a$", ""};
      DFA22_eot = DFA.unpackEncodedString("\u0002\uffff\u0001$\u0001)\u0001\uffff\u0001+\u0001$\u0004\uffff\u0001.\u0001\uffff\u00010\u0001\uffff\u00012\u0002\uffff\u0006$\u000b\uffff\u0001$\u0002\uffff\u0002$\u0004\uffff\u0001$\u0006\uffff\n$\u0004\uffff\u000f$\r\uffff\r$\u0001{\u0001$\u0002\uffff\u0003$\u0001\u0081\u0001\u0082\u0001$\u0001\u0084\u0006$\u0001\uffff\u0001$\u0001\uffff\u0003$\u0002\uffff\u0001$\u0001\uffff\u0001\u0091\u0002$\u0001\u0094\u0001$\u0001\u0096\u0001$\u0001\uffff\u0002$\u0001\u009b\u0001\u009c\u0001\uffff\u0001\u009d\u0001$\u0001\uffff\u0001\u009f\u0003\uffff\u0001$\u0001¡\u0003\uffff\u0001$\u0003\uffff\u0001£\u0001\uffff");
      DFA22_eof = DFA.unpackEncodedString("¤\uffff");
      DFA22_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0001\uffff\u0001i\u0001.\u0001\uffff\u0001(\u0001c\u0004\uffff\u0001=\u0001\uffff\u0001:\u0001\uffff\u0001>\u0002\uffff\u0001a\u0001r\u0001e\u0001a\u0001e\u0001h\u0003\uffff\u0001*\u0001\u0000\u0006\uffff\u0001p\u0002\uffff\u0001a\u0001n\u0004\uffff\u0001o\u0006\uffff\u0001t\u0001a\u0001x\u0001r\u0001i\u0001b\u0001t\u0001r\u0001e\u0001k\u0002\uffff\u0002\u0000\u0001t\u0001g\u0001a\u0001p\u0001c\u0001m\u0001e\u0001s\u0001v\u0001t\u0001l\u0001u\u0001o\u0002e\u000b\u0000\u0002\uffff\u0001i\u0001m\u0001l\u0001e\u0001h\u0001m\u0001r\u0001e\u0001a\u0001e\u0001i\u0001r\u0001w\u00010\u0001n\u0001\u0000\u0001\uffff\u0001o\u0001e\u0001l\u00020\u0001a\u00010\u0001r\u0001t\u0002c\u0001n\u0001s\u0001\uffff\u0001s\u0001\u0000\u0002n\u0001y\u0002\uffff\u0001r\u0001\uffff\u00010\u0001e\u0001t\u00010\u0001s\u00010\u0001\t\u0001\u0000\u0001s\u0001t\u00020\u0001\uffff\u00010\u0001e\u0001\uffff\u00010\u0002\uffff\u0001\u0000\u0001\t\u00010\u0003\uffff\u0001d\u0003\uffff\u00010\u0001\uffff");
      DFA22_max = DFA.unpackEncodedStringToUnsignedChars("\u0001~\u0001\uffff\u0001r\u0001.\u0001\uffff\u0001(\u0001c\u0004\uffff\u0001=\u0001\uffff\u0001:\u0001\uffff\u0001>\u0002\uffff\u0001a\u0001r\u0001e\u0001u\u0001e\u0001r\u0003\uffff\u0001/\u0001\uffff\u0006\uffff\u0001p\u0002\uffff\u0001a\u0001n\u0004\uffff\u0001o\u0006\uffff\u0001t\u0001a\u0001x\u0001r\u0001o\u0001b\u0001t\u0001r\u0001e\u0001k\u0002\uffff\u0002\uffff\u0001t\u0001g\u0001a\u0001p\u0001c\u0001m\u0001e\u0001s\u0001v\u0001t\u0001l\u0001u\u0001o\u0002e\u000b\uffff\u0002\uffff\u0001i\u0001m\u0001l\u0001e\u0001h\u0001m\u0001r\u0001e\u0001a\u0001e\u0001i\u0001r\u0001w\u0001z\u0001n\u0001\uffff\u0001\uffff\u0001o\u0001e\u0001l\u0002z\u0001a\u0001z\u0001r\u0001t\u0002c\u0001n\u0001s\u0001\uffff\u0001s\u0001\uffff\u0002n\u0001y\u0002\uffff\u0001r\u0001\uffff\u0001z\u0001e\u0001t\u0001z\u0001s\u0001z\u0001{\u0001\uffff\u0001s\u0001t\u0002z\u0001\uffff\u0001z\u0001e\u0001\uffff\u0001z\u0002\uffff\u0001\uffff\u0001{\u0001z\u0003\uffff\u0001d\u0003\uffff\u0001z\u0001\uffff");
      DFA22_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0002\uffff\u0001\u0004\u0002\uffff\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\uffff\u0001\u000e\u0001\uffff\u0001\u0012\u0001\uffff\u0001\u0015\u0001\u0016\u0006\uffff\u0001\"\u0001#\u0001$\u0002\uffff\u0001)\u0001*\u0001+\u0001,\u0001-\u0001.\u0001\uffff\u0001/\u00012\u0002\uffff\u0001\u0003\u0001\u000f\u0001\u0007\u0001\u0005\u0001\uffff\u0001\r\u0001\f\u0001\u0011\u0001\u0010\u0001\u0014\u0001\u0013\n\uffff\u0001%\u0001&\u001c\uffff\u0001'\u0001(\u0010\uffff\u0001'\r\uffff\u0001!\u0005\uffff\u0001\u0006\u0001\u0017\u0001\uffff\u0001\u001a\f\uffff\u0001\u001b\u0002\uffff\u0001\u001e\u0001\uffff\u0001 \u00011\u0003\uffff\u0001\u0018\u0001\u0019\u0001\u001c\u0001\uffff\u0001\u001f\u00010\u0001\u0002\u0001\uffff\u0001\u001d");
      DFA22_special = DFA.unpackEncodedString("\u001c\uffff\u0001\u0004\"\uffff\u0001\u0000\u0001\u0005\u000f\uffff\u0001\u0003\u0001\u0002\u0001\u0001\u0001\u000e\u0001\f\u0001\u0011\u0001\u0010\u0001\n\u0001\t\u0001\u0007\u0001\u0006\u0011\uffff\u0001\u000b\u0010\uffff\u0001\r\u000e\uffff\u0001\u000f\u000b\uffff\u0001\b\u000b\uffff}>");
      numStates = DFA22_transitionS.length;
      DFA22_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
      }

   }

   protected class DFA22 extends DFA {
      public DFA22(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 22;
         this.eot = ANTLRv3Lexer.DFA22_eot;
         this.eof = ANTLRv3Lexer.DFA22_eof;
         this.min = ANTLRv3Lexer.DFA22_min;
         this.max = ANTLRv3Lexer.DFA22_max;
         this.accept = ANTLRv3Lexer.DFA22_accept;
         this.special = ANTLRv3Lexer.DFA22_special;
         this.transition = ANTLRv3Lexer.DFA22_transition;
      }

      public String getDescription() {
         return "1:1: Tokens : ( BANG | FRAGMENT | RANGE | REWRITE | ROOT | SCOPE | TREE_BEGIN | T__65 | T__66 | T__67 | T__68 | T__69 | T__70 | T__71 | T__72 | T__73 | T__74 | T__75 | T__76 | T__77 | T__78 | T__79 | T__80 | T__81 | T__82 | T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | SL_COMMENT | ML_COMMENT | CHAR_LITERAL | STRING_LITERAL | DOUBLE_QUOTE_STRING_LITERAL | DOUBLE_ANGLE_STRING_LITERAL | INT | ARG_ACTION | ACTION | TOKEN_REF | RULE_REF | OPTIONS | TOKENS | WS );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         switch (s) {
            case 0:
               int LA22_63 = _input.LA(1);
               s = -1;
               if (LA22_63 == 110) {
                  s = 80;
               } else if (LA22_63 == 114) {
                  s = 81;
               } else if (LA22_63 == 116) {
                  s = 82;
               } else if (LA22_63 == 98) {
                  s = 83;
               } else if (LA22_63 == 102) {
                  s = 84;
               } else if (LA22_63 == 34) {
                  s = 85;
               } else if (LA22_63 == 39) {
                  s = 86;
               } else if (LA22_63 == 92) {
                  s = 87;
               } else if (LA22_63 == 62) {
                  s = 88;
               } else if (LA22_63 == 117) {
                  s = 89;
               } else if (LA22_63 >= 0 && LA22_63 <= 33 || LA22_63 >= 35 && LA22_63 <= 38 || LA22_63 >= 40 && LA22_63 <= 61 || LA22_63 >= 63 && LA22_63 <= 91 || LA22_63 >= 93 && LA22_63 <= 97 || LA22_63 >= 99 && LA22_63 <= 101 || LA22_63 >= 103 && LA22_63 <= 109 || LA22_63 >= 111 && LA22_63 <= 113 || LA22_63 == 115 || LA22_63 >= 118 && LA22_63 <= 65535) {
                  s = 90;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA22_82 = _input.LA(1);
               s = -1;
               if (LA22_82 == 39) {
                  s = 91;
               } else if (LA22_82 >= 0 && LA22_82 <= 38 || LA22_82 >= 40 && LA22_82 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA22_81 = _input.LA(1);
               s = -1;
               if (LA22_81 == 39) {
                  s = 91;
               } else if (LA22_81 >= 0 && LA22_81 <= 38 || LA22_81 >= 40 && LA22_81 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA22_80 = _input.LA(1);
               s = -1;
               if (LA22_80 == 39) {
                  s = 91;
               } else if (LA22_80 >= 0 && LA22_80 <= 38 || LA22_80 >= 40 && LA22_80 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA22_28 = _input.LA(1);
               s = -1;
               if (LA22_28 == 92) {
                  s = 63;
               } else if (LA22_28 >= 0 && LA22_28 <= 38 || LA22_28 >= 40 && LA22_28 <= 91 || LA22_28 >= 93 && LA22_28 <= 65535) {
                  s = 64;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA22_64 = _input.LA(1);
               s = -1;
               if (LA22_64 == 39) {
                  s = 91;
               } else if (LA22_64 >= 0 && LA22_64 <= 38 || LA22_64 >= 40 && LA22_64 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA22_90 = _input.LA(1);
               s = -1;
               if (LA22_90 == 39) {
                  s = 91;
               } else if (LA22_90 >= 0 && LA22_90 <= 38 || LA22_90 >= 40 && LA22_90 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA22_89 = _input.LA(1);
               s = -1;
               if (LA22_89 >= 48 && LA22_89 <= 57 || LA22_89 >= 65 && LA22_89 <= 70 || LA22_89 >= 97 && LA22_89 <= 102) {
                  s = 108;
               } else if (LA22_89 == 39) {
                  s = 91;
               } else if (LA22_89 >= 0 && LA22_89 <= 38 || LA22_89 >= 40 && LA22_89 <= 47 || LA22_89 >= 58 && LA22_89 <= 64 || LA22_89 >= 71 && LA22_89 <= 96 || LA22_89 >= 103 && LA22_89 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA22_152 = _input.LA(1);
               s = -1;
               if (LA22_152 == 39) {
                  s = 91;
               } else if (LA22_152 >= 0 && LA22_152 <= 38 || LA22_152 >= 40 && LA22_152 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA22_88 = _input.LA(1);
               s = -1;
               if (LA22_88 == 39) {
                  s = 91;
               } else if (LA22_88 >= 0 && LA22_88 <= 38 || LA22_88 >= 40 && LA22_88 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA22_87 = _input.LA(1);
               s = -1;
               if (LA22_87 == 39) {
                  s = 91;
               } else if (LA22_87 >= 0 && LA22_87 <= 38 || LA22_87 >= 40 && LA22_87 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA22_108 = _input.LA(1);
               s = -1;
               if (LA22_108 >= 48 && LA22_108 <= 57 || LA22_108 >= 65 && LA22_108 <= 70 || LA22_108 >= 97 && LA22_108 <= 102) {
                  s = 125;
               } else if (LA22_108 >= 0 && LA22_108 <= 47 || LA22_108 >= 58 && LA22_108 <= 64 || LA22_108 >= 71 && LA22_108 <= 96 || LA22_108 >= 103 && LA22_108 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA22_84 = _input.LA(1);
               s = -1;
               if (LA22_84 == 39) {
                  s = 91;
               } else if (LA22_84 >= 0 && LA22_84 <= 38 || LA22_84 >= 40 && LA22_84 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA22_125 = _input.LA(1);
               s = -1;
               if (LA22_125 >= 48 && LA22_125 <= 57 || LA22_125 >= 65 && LA22_125 <= 70 || LA22_125 >= 97 && LA22_125 <= 102) {
                  s = 140;
               } else if (LA22_125 >= 0 && LA22_125 <= 47 || LA22_125 >= 58 && LA22_125 <= 64 || LA22_125 >= 71 && LA22_125 <= 96 || LA22_125 >= 103 && LA22_125 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA22_83 = _input.LA(1);
               s = -1;
               if (LA22_83 == 39) {
                  s = 91;
               } else if (LA22_83 >= 0 && LA22_83 <= 38 || LA22_83 >= 40 && LA22_83 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA22_140 = _input.LA(1);
               s = -1;
               if ((LA22_140 < 48 || LA22_140 > 57) && (LA22_140 < 65 || LA22_140 > 70) && (LA22_140 < 97 || LA22_140 > 102)) {
                  if (LA22_140 >= 0 && LA22_140 <= 47 || LA22_140 >= 58 && LA22_140 <= 64 || LA22_140 >= 71 && LA22_140 <= 96 || LA22_140 >= 103 && LA22_140 <= 65535) {
                     s = 92;
                  }
               } else {
                  s = 152;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA22_86 = _input.LA(1);
               s = -1;
               if (LA22_86 == 39) {
                  s = 91;
               } else if (LA22_86 >= 0 && LA22_86 <= 38 || LA22_86 >= 40 && LA22_86 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 17:
               int LA22_85 = _input.LA(1);
               s = -1;
               if (LA22_85 == 39) {
                  s = 91;
               } else if (LA22_85 >= 0 && LA22_85 <= 38 || LA22_85 >= 40 && LA22_85 <= 65535) {
                  s = 92;
               }

               if (s >= 0) {
                  return s;
               }
         }

         NoViableAltException nvae = new NoViableAltException(this.getDescription(), 22, s, _input);
         this.error(nvae);
         throw nvae;
      }
   }

   protected class DFA2 extends DFA {
      public DFA2(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 2;
         this.eot = ANTLRv3Lexer.DFA2_eot;
         this.eof = ANTLRv3Lexer.DFA2_eof;
         this.min = ANTLRv3Lexer.DFA2_min;
         this.max = ANTLRv3Lexer.DFA2_max;
         this.accept = ANTLRv3Lexer.DFA2_accept;
         this.special = ANTLRv3Lexer.DFA2_special;
         this.transition = ANTLRv3Lexer.DFA2_transition;
      }

      public String getDescription() {
         return "466:5: ( ' $ANTLR ' SRC | (~ ( '\\r' | '\\n' ) )* )";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         boolean sx;
         switch (s) {
            case 0:
               int LA2_6 = _input.LA(1);
               s = -1;
               if (LA2_6 == 76) {
                  s = 7;
               } else if (LA2_6 >= 0 && LA2_6 <= 75 || LA2_6 >= 77 && LA2_6 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA2_14 = _input.LA(1);
               s = -1;
               if (LA2_14 == 92) {
                  s = 15;
               } else if (LA2_14 == 13) {
                  s = 16;
               } else if (LA2_14 == 34) {
                  s = 17;
               } else if (LA2_14 == 10) {
                  s = 18;
               } else if (LA2_14 >= 0 && LA2_14 <= 9 || LA2_14 >= 11 && LA2_14 <= 12 || LA2_14 >= 14 && LA2_14 <= 33 || LA2_14 >= 35 && LA2_14 <= 91 || LA2_14 >= 93 && LA2_14 <= 65535) {
                  s = 19;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA2_7 = _input.LA(1);
               s = -1;
               if (LA2_7 == 82) {
                  s = 8;
               } else if (LA2_7 >= 0 && LA2_7 <= 81 || LA2_7 >= 83 && LA2_7 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA2_8 = _input.LA(1);
               s = -1;
               if (LA2_8 == 32) {
                  s = 9;
               } else if (LA2_8 >= 0 && LA2_8 <= 31 || LA2_8 >= 33 && LA2_8 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA2_23 = _input.LA(1);
               sx = true;
               if (LA2_23 >= 0 && LA2_23 <= 65535) {
                  s = 25;
               } else {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA2_13 = _input.LA(1);
               s = -1;
               if (LA2_13 == 34) {
                  s = 14;
               } else if (LA2_13 >= 0 && LA2_13 <= 33 || LA2_13 >= 35 && LA2_13 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA2_17 = _input.LA(1);
               s = -1;
               if (LA2_17 == 32) {
                  s = 26;
               } else if (LA2_17 >= 0 && LA2_17 <= 31 || LA2_17 >= 33 && LA2_17 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA2_16 = _input.LA(1);
               s = -1;
               if (LA2_16 >= 0 && LA2_16 <= 9 || LA2_16 >= 11 && LA2_16 <= 65535) {
                  s = 25;
               } else if (LA2_16 == 10) {
                  s = 18;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA2_19 = _input.LA(1);
               s = -1;
               if (LA2_19 == 34) {
                  s = 17;
               } else if (LA2_19 == 92) {
                  s = 15;
               } else if (LA2_19 == 13) {
                  s = 16;
               } else if (LA2_19 == 10) {
                  s = 18;
               } else if (LA2_19 >= 0 && LA2_19 <= 9 || LA2_19 >= 11 && LA2_19 <= 12 || LA2_19 >= 14 && LA2_19 <= 33 || LA2_19 >= 35 && LA2_19 <= 91 || LA2_19 >= 93 && LA2_19 <= 65535) {
                  s = 19;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA2_3 = _input.LA(1);
               s = -1;
               if (LA2_3 == 65) {
                  s = 4;
               } else if (LA2_3 >= 0 && LA2_3 <= 64 || LA2_3 >= 66 && LA2_3 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA2_1 = _input.LA(1);
               s = -1;
               if (LA2_1 == 36) {
                  s = 3;
               } else if (LA2_1 >= 0 && LA2_1 <= 35 || LA2_1 >= 37 && LA2_1 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA2_5 = _input.LA(1);
               s = -1;
               if (LA2_5 == 84) {
                  s = 6;
               } else if (LA2_5 >= 0 && LA2_5 <= 83 || LA2_5 >= 85 && LA2_5 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA2_4 = _input.LA(1);
               s = -1;
               if (LA2_4 == 78) {
                  s = 5;
               } else if (LA2_4 >= 0 && LA2_4 <= 77 || LA2_4 >= 79 && LA2_4 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA2_26 = _input.LA(1);
               s = -1;
               if (LA2_26 >= 48 && LA2_26 <= 57) {
                  s = 27;
               } else if (LA2_26 >= 0 && LA2_26 <= 47 || LA2_26 >= 58 && LA2_26 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA2_21 = _input.LA(1);
               s = -1;
               if (LA2_21 == 34) {
                  s = 17;
               } else if (LA2_21 == 92) {
                  s = 15;
               } else if (LA2_21 == 13) {
                  s = 16;
               } else if (LA2_21 == 10) {
                  s = 18;
               } else if (LA2_21 >= 0 && LA2_21 <= 9 || LA2_21 >= 11 && LA2_21 <= 12 || LA2_21 >= 14 && LA2_21 <= 33 || LA2_21 >= 35 && LA2_21 <= 91 || LA2_21 >= 93 && LA2_21 <= 65535) {
                  s = 19;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA2_20 = _input.LA(1);
               s = -1;
               if (LA2_20 == 34) {
                  s = 17;
               } else if (LA2_20 == 92) {
                  s = 15;
               } else if (LA2_20 == 13) {
                  s = 16;
               } else if (LA2_20 == 10) {
                  s = 18;
               } else if (LA2_20 >= 0 && LA2_20 <= 9 || LA2_20 >= 11 && LA2_20 <= 12 || LA2_20 >= 14 && LA2_20 <= 33 || LA2_20 >= 35 && LA2_20 <= 91 || LA2_20 >= 93 && LA2_20 <= 65535) {
                  s = 19;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA2_18 = _input.LA(1);
               sx = true;
               if (LA2_18 >= 0 && LA2_18 <= 65535) {
                  s = 25;
               } else {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 17:
               int LA2_0 = _input.LA(1);
               s = -1;
               if (LA2_0 == 32) {
                  s = 1;
               } else if (LA2_0 >= 0 && LA2_0 <= 31 || LA2_0 >= 33 && LA2_0 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 18:
               int LA2_12 = _input.LA(1);
               s = -1;
               if (LA2_12 == 32) {
                  s = 13;
               } else if (LA2_12 >= 0 && LA2_12 <= 31 || LA2_12 >= 33 && LA2_12 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 19:
               int LA2_22 = _input.LA(1);
               s = -1;
               if (LA2_22 >= 0 && LA2_22 <= 9 || LA2_22 >= 11 && LA2_22 <= 65535) {
                  s = 25;
               } else if (LA2_22 == 10) {
                  s = 18;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 20:
               int LA2_9 = _input.LA(1);
               s = -1;
               if (LA2_9 == 115) {
                  s = 10;
               } else if (LA2_9 >= 0 && LA2_9 <= 114 || LA2_9 >= 116 && LA2_9 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 21:
               int LA2_10 = _input.LA(1);
               s = -1;
               if (LA2_10 == 114) {
                  s = 11;
               } else if (LA2_10 >= 0 && LA2_10 <= 113 || LA2_10 >= 115 && LA2_10 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 22:
               int LA2_15 = _input.LA(1);
               s = -1;
               if (LA2_15 == 39) {
                  s = 20;
               } else if (LA2_15 == 34) {
                  s = 21;
               } else if (LA2_15 == 13) {
                  s = 22;
               } else if (LA2_15 == 10) {
                  s = 23;
               } else if (LA2_15 >= 0 && LA2_15 <= 9 || LA2_15 >= 11 && LA2_15 <= 12 || LA2_15 >= 14 && LA2_15 <= 33 || LA2_15 >= 35 && LA2_15 <= 38 || LA2_15 >= 40 && LA2_15 <= 65535) {
                  s = 24;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 23:
               int LA2_11 = _input.LA(1);
               s = -1;
               if (LA2_11 == 99) {
                  s = 12;
               } else if (LA2_11 >= 0 && LA2_11 <= 98 || LA2_11 >= 100 && LA2_11 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 24:
               int LA2_24 = _input.LA(1);
               s = -1;
               if (LA2_24 == 34) {
                  s = 17;
               } else if (LA2_24 == 92) {
                  s = 15;
               } else if (LA2_24 == 13) {
                  s = 16;
               } else if (LA2_24 == 10) {
                  s = 18;
               } else if (LA2_24 >= 0 && LA2_24 <= 9 || LA2_24 >= 11 && LA2_24 <= 12 || LA2_24 >= 14 && LA2_24 <= 33 || LA2_24 >= 35 && LA2_24 <= 91 || LA2_24 >= 93 && LA2_24 <= 65535) {
                  s = 19;
               }

               if (s >= 0) {
                  return s;
               }
         }

         NoViableAltException nvae = new NoViableAltException(this.getDescription(), 2, s, _input);
         this.error(nvae);
         throw nvae;
      }
   }
}
