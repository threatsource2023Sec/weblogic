package org.antlr.grammar.v3;

import java.util.ArrayList;
import java.util.List;
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
import org.antlr.runtime.Token;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;

public class ANTLRLexer extends Lexer {
   public static final int EOF = -1;
   public static final int ACTION = 4;
   public static final int ACTION_CHAR_LITERAL = 5;
   public static final int ACTION_ESC = 6;
   public static final int ACTION_STRING_LITERAL = 7;
   public static final int ALT = 8;
   public static final int AMPERSAND = 9;
   public static final int ARG = 10;
   public static final int ARGLIST = 11;
   public static final int ARG_ACTION = 12;
   public static final int ASSIGN = 13;
   public static final int BACKTRACK_SEMPRED = 14;
   public static final int BANG = 15;
   public static final int BLOCK = 16;
   public static final int CATCH = 17;
   public static final int CHAR_LITERAL = 18;
   public static final int CHAR_RANGE = 19;
   public static final int CLOSE_ELEMENT_OPTION = 20;
   public static final int CLOSURE = 21;
   public static final int COLON = 22;
   public static final int COMBINED_GRAMMAR = 23;
   public static final int COMMA = 24;
   public static final int COMMENT = 25;
   public static final int DIGIT = 26;
   public static final int DOC_COMMENT = 27;
   public static final int DOLLAR = 28;
   public static final int DOT = 29;
   public static final int DOUBLE_ANGLE_STRING_LITERAL = 30;
   public static final int DOUBLE_QUOTE_STRING_LITERAL = 31;
   public static final int EOA = 32;
   public static final int EOB = 33;
   public static final int EOR = 34;
   public static final int EPSILON = 35;
   public static final int ESC = 36;
   public static final int ETC = 37;
   public static final int FINALLY = 38;
   public static final int FORCED_ACTION = 39;
   public static final int FRAGMENT = 40;
   public static final int GATED_SEMPRED = 41;
   public static final int GRAMMAR = 42;
   public static final int ID = 43;
   public static final int IMPLIES = 44;
   public static final int IMPORT = 45;
   public static final int INITACTION = 46;
   public static final int INT = 47;
   public static final int LABEL = 48;
   public static final int LEXER = 49;
   public static final int LEXER_GRAMMAR = 50;
   public static final int LPAREN = 51;
   public static final int ML_COMMENT = 52;
   public static final int NESTED_ACTION = 53;
   public static final int NESTED_ARG_ACTION = 54;
   public static final int NOT = 55;
   public static final int OPEN_ELEMENT_OPTION = 56;
   public static final int OPTIONAL = 57;
   public static final int OPTIONS = 58;
   public static final int OR = 59;
   public static final int PARSER = 60;
   public static final int PARSER_GRAMMAR = 61;
   public static final int PLUS = 62;
   public static final int PLUS_ASSIGN = 63;
   public static final int POSITIVE_CLOSURE = 64;
   public static final int PREC_RULE = 65;
   public static final int PRIVATE = 66;
   public static final int PROTECTED = 67;
   public static final int PUBLIC = 68;
   public static final int QUESTION = 69;
   public static final int RANGE = 70;
   public static final int RCURLY = 71;
   public static final int RECURSIVE_RULE_REF = 72;
   public static final int RET = 73;
   public static final int RETURNS = 74;
   public static final int REWRITE = 75;
   public static final int REWRITES = 76;
   public static final int ROOT = 77;
   public static final int RPAREN = 78;
   public static final int RULE = 79;
   public static final int RULE_REF = 80;
   public static final int SCOPE = 81;
   public static final int SEMI = 82;
   public static final int SEMPRED = 83;
   public static final int SL_COMMENT = 84;
   public static final int SRC = 85;
   public static final int STAR = 86;
   public static final int STRAY_BRACKET = 87;
   public static final int STRING_LITERAL = 88;
   public static final int SYNPRED = 89;
   public static final int SYN_SEMPRED = 90;
   public static final int TEMPLATE = 91;
   public static final int THROWS = 92;
   public static final int TOKENS = 93;
   public static final int TOKEN_REF = 94;
   public static final int TREE = 95;
   public static final int TREE_BEGIN = 96;
   public static final int TREE_GRAMMAR = 97;
   public static final int WILDCARD = 98;
   public static final int WS = 99;
   public static final int WS_LOOP = 100;
   public static final int WS_OPT = 101;
   public static final int XDIGIT = 102;
   public boolean hasASTOperator;
   private String fileName;
   protected DFA9 dfa9;
   protected DFA25 dfa25;
   static final String DFA9_eotS = "\u0002\u0002\u0001\uffff\r\u0002\u0001\uffff\u0005\u0002\u0001\uffff\u0002\u0002\u0003\uffff\u0001\u0002\u0001\uffff";
   static final String DFA9_eofS = "\u001e\uffff";
   static final String DFA9_minS = "\u0001 \u0001$\u0001\uffff\u0001A\u0001N\u0001T\u0001L\u0001R\u0001 \u0001s\u0001r\u0001c\u0001 \u0001\"\u0003\u0000\u0001 \u0007\u0000\u0003\uffff\u00010\u0001\u0000";
   static final String DFA9_maxS = "\u0001 \u0001$\u0001\uffff\u0001A\u0001N\u0001T\u0001L\u0001R\u0001 \u0001s\u0001r\u0001c\u0001 \u0001\"\u0003\uffff\u0001 \u0007\uffff\u0003\uffff\u00019\u0001\u0000";
   static final String DFA9_acceptS = "\u0002\uffff\u0001\u0002\u0016\uffff\u0003\u0001\u0002\uffff";
   static final String DFA9_specialS = "\u000e\uffff\u0001\u0007\u0001\t\u0001\n\u0001\uffff\u0001\u0003\u0001\u0001\u0001\b\u0001\u0006\u0001\u0004\u0001\u0000\u0001\u0005\u0004\uffff\u0001\u0002}>";
   static final String[] DFA9_transitionS = new String[]{"\u0001\u0001", "\u0001\u0003", "", "\u0001\u0004", "\u0001\u0005", "\u0001\u0006", "\u0001\u0007", "\u0001\b", "\u0001\t", "\u0001\n", "\u0001\u000b", "\u0001\f", "\u0001\r", "\u0001\u000e", "\n\u0013\u0001\u0012\u0002\u0013\u0001\u0010\u0014\u0013\u0001\u00119\u0013\u0001\u000fﾣ\u0013", "\n\u0018\u0001\u0017\u0002\u0018\u0001\u0016\u0014\u0018\u0001\u0015\u0004\u0018\u0001\u0014\uffd8\u0018", "\n\u001b\u0001\u0012\u0017\u001b\u0001\u00199\u001b\u0001\u001aﾣ\u001b", "\u0001\u001c", "\"\u001b\u0001\u00199\u001b\u0001\u001aﾣ\u001b", "\n\u0013\u0001\u0012\u0002\u0013\u0001\u0010\u0014\u0013\u0001\u00119\u0013\u0001\u000fﾣ\u0013", "\n\u0013\u0001\u0012\u0002\u0013\u0001\u0010\u0014\u0013\u0001\u00119\u0013\u0001\u000fﾣ\u0013", "\n\u0013\u0001\u0012\u0002\u0013\u0001\u0010\u0014\u0013\u0001\u00119\u0013\u0001\u000fﾣ\u0013", "\n\u001b\u0001\u0012\u0017\u001b\u0001\u00199\u001b\u0001\u001aﾣ\u001b", "\"\u001b\u0001\u00199\u001b\u0001\u001aﾣ\u001b", "\n\u0013\u0001\u0012\u0002\u0013\u0001\u0010\u0014\u0013\u0001\u00119\u0013\u0001\u000fﾣ\u0013", "", "", "", "\n\u001d", "\u0001\uffff"};
   static final short[] DFA9_eot = DFA.unpackEncodedString("\u0002\u0002\u0001\uffff\r\u0002\u0001\uffff\u0005\u0002\u0001\uffff\u0002\u0002\u0003\uffff\u0001\u0002\u0001\uffff");
   static final short[] DFA9_eof = DFA.unpackEncodedString("\u001e\uffff");
   static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars("\u0001 \u0001$\u0001\uffff\u0001A\u0001N\u0001T\u0001L\u0001R\u0001 \u0001s\u0001r\u0001c\u0001 \u0001\"\u0003\u0000\u0001 \u0007\u0000\u0003\uffff\u00010\u0001\u0000");
   static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars("\u0001 \u0001$\u0001\uffff\u0001A\u0001N\u0001T\u0001L\u0001R\u0001 \u0001s\u0001r\u0001c\u0001 \u0001\"\u0003\uffff\u0001 \u0007\uffff\u0003\uffff\u00019\u0001\u0000");
   static final short[] DFA9_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0016\uffff\u0003\u0001\u0002\uffff");
   static final short[] DFA9_special = DFA.unpackEncodedString("\u000e\uffff\u0001\u0007\u0001\t\u0001\n\u0001\uffff\u0001\u0003\u0001\u0001\u0001\b\u0001\u0006\u0001\u0004\u0001\u0000\u0001\u0005\u0004\uffff\u0001\u0002}>");
   static final short[][] DFA9_transition;
   static final String DFA25_eotS = "\u0001\uffff\t(\u0002\uffff\u00018\u0004\uffff\u0001:\u0004\uffff\u0001<\u0001>\u0004\uffff\u0001@\n\uffff\u0001(\u0001\uffff\u000e(\b\uffff\u0001R\u0001\uffff\u0010(\u0002\uffff\u000e(\u0001q\u0002(\u0001t\u0004(\u0001y\u0005(\u0001\u007f\u0001(\u0001\uffff\u0002(\u0001\uffff\u0003(\u0001\u0086\u0001\uffff\u0001\u0087\u0002(\u0001\u008a\u0001(\u0001\uffff\u0001\u008c\u0002(\u0001\u008f\u0001(\u0001\u0091\u0002\uffff\u0001\u0092\u0001(\u0001\uffff\u0001\u0094\u0002\uffff\u0001(\u0001\uffff\u0001\u0096\u0002\uffff\u0001(\u0003\uffff\u0001\u0098\u0001\uffff";
   static final String DFA25_eofS = "\u0099\uffff";
   static final String DFA25_minS = "\u0001\t\u0001a\u0001i\u0001r\u0001m\u0001e\u0001a\u0001e\u0001c\u0001h\u0002\uffff\u0001<\u0004\uffff\u0001(\u0004\uffff\u0001=\u0001>\u0004\uffff\u0001.\n\uffff\u0001p\u0001\uffff\u0001t\u0001n\u0002a\u0001p\u0001x\u0001r\u0001i\u0001b\u0001t\u0001o\u0001r\u0001e\u0001k\b\uffff\u0001.\u0001\uffff\u0001t\u0001c\u0001a\u0001g\u0001m\u0001o\u0001e\u0001s\u0001v\u0001t\u0001l\u0001u\u0001p\u0001o\u0002e\u0002\uffff\u0001i\u0001h\u0001l\u0002m\u0002r\u0001e\u0001a\u0001e\u0001i\u0001r\u0001e\u0001w\u00010\u0001n\u0001o\u00010\u0001l\u0001e\u0001a\u0001t\u00010\u0001r\u0001t\u0002c\u0001n\u00010\u0001s\u0001\uffff\u0001s\u0001n\u0001\uffff\u0001y\u0001n\u0001r\u00010\u0001\uffff\u00010\u0001e\u0001t\u00010\u0001s\u0001\uffff\u00010\u0001\t\u0001s\u00010\u0001t\u00010\u0002\uffff\u00010\u0001e\u0001\uffff\u00010\u0002\uffff\u0001\t\u0001\uffff\u00010\u0002\uffff\u0001d\u0003\uffff\u00010\u0001\uffff";
   static final String DFA25_maxS = "\u0001~\u0001a\u0002r\u0001m\u0001e\u0001u\u0001e\u0001c\u0001r\u0002\uffff\u0001<\u0004\uffff\u0001(\u0004\uffff\u0001=\u0001>\u0004\uffff\u0001.\n\uffff\u0001p\u0001\uffff\u0001t\u0001n\u0002a\u0001p\u0001x\u0001r\u0001o\u0001b\u0001t\u0001o\u0001r\u0001e\u0001k\b\uffff\u0001.\u0001\uffff\u0001t\u0001c\u0001a\u0001g\u0001m\u0001o\u0001e\u0001s\u0001v\u0001t\u0001l\u0001u\u0001p\u0001o\u0002e\u0002\uffff\u0001i\u0001h\u0001l\u0002m\u0002r\u0001e\u0001a\u0001e\u0001i\u0001r\u0001e\u0001w\u0001z\u0001n\u0001o\u0001z\u0001l\u0001e\u0001a\u0001t\u0001z\u0001r\u0001t\u0002c\u0001n\u0001z\u0001s\u0001\uffff\u0001s\u0001n\u0001\uffff\u0001y\u0001n\u0001r\u0001z\u0001\uffff\u0001z\u0001e\u0001t\u0001z\u0001s\u0001\uffff\u0001z\u0001{\u0001s\u0001z\u0001t\u0001z\u0002\uffff\u0001z\u0001e\u0001\uffff\u0001z\u0002\uffff\u0001{\u0001\uffff\u0001z\u0002\uffff\u0001d\u0003\uffff\u0001z\u0001\uffff";
   static final String DFA25_acceptS = "\n\uffff\u0001\u000f\u0001\u0010\u0001\uffff\u0001\u0012\u0001\u0013\u0001\u0014\u0001\u0015\u0001\uffff\u0001\u0017\u0001\u0018\u0001\u0019\u0001\u001a\u0002\uffff\u0001\u001f\u0001 \u0001\"\u0001#\u0001\uffff\u0001'\u0001(\u0001)\u0001*\u0001+\u0001,\u0001.\u0001/\u00010\u00011\u0001\uffff\u00014\u000e\uffff\u0001-\u0001\u0011\u0001\u0016\u0001!\u0001\u001d\u0001\u001b\u0001\u001e\u0001\u001c\u0001\uffff\u0001$\u0010\uffff\u0001%\u0001&\u001e\uffff\u0001\u000e\u0002\uffff\u0001\u0001\u0004\uffff\u0001\u0006\u0005\uffff\u0001\f\u0006\uffff\u0001\u0005\u0001\u0007\u0002\uffff\u0001\n\u0001\uffff\u0001\r\u00012\u0001\uffff\u0001\u0002\u0001\uffff\u0001\u0004\u0001\b\u0001\uffff\u0001\u000b\u00013\u0001\u0003\u0001\uffff\u0001\t";
   static final String DFA25_specialS = "\u0099\uffff}>";
   static final String[] DFA25_transitionS;
   static final short[] DFA25_eot;
   static final short[] DFA25_eof;
   static final char[] DFA25_min;
   static final char[] DFA25_max;
   static final short[] DFA25_accept;
   static final short[] DFA25_special;
   static final short[][] DFA25_transition;

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String value) {
      this.fileName = value;
   }

   public Token nextToken() {
      Token token;
      for(token = super.nextToken(); token.getType() == 87; token = super.nextToken()) {
         ErrorManager.syntaxError(100, (Grammar)null, token, "antlr: dangling ']'? make sure to escape with \\]", (RecognitionException)null);
      }

      return token;
   }

   public Lexer[] getDelegates() {
      return new Lexer[0];
   }

   public ANTLRLexer() {
      this.hasASTOperator = false;
      this.dfa9 = new DFA9(this);
      this.dfa25 = new DFA25(this);
   }

   public ANTLRLexer(CharStream input) {
      this(input, new RecognizerSharedState());
   }

   public ANTLRLexer(CharStream input, RecognizerSharedState state) {
      super(input, state);
      this.hasASTOperator = false;
      this.dfa9 = new DFA9(this);
      this.dfa25 = new DFA25(this);
   }

   public String getGrammarFileName() {
      return "org\\antlr\\grammar\\v3\\ANTLR.g";
   }

   public final void mCATCH() throws RecognitionException {
      try {
         int _type = 17;
         int _channel = 0;
         this.match("catch");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mFINALLY() throws RecognitionException {
      try {
         int _type = 38;
         int _channel = 0;
         this.match("finally");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mFRAGMENT() throws RecognitionException {
      try {
         int _type = 40;
         int _channel = 0;
         this.match("fragment");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mGRAMMAR() throws RecognitionException {
      try {
         int _type = 42;
         int _channel = 0;
         this.match("grammar");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mIMPORT() throws RecognitionException {
      try {
         int _type = 45;
         int _channel = 0;
         this.match("import");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mLEXER() throws RecognitionException {
      try {
         int _type = 49;
         int _channel = 0;
         this.match("lexer");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mPARSER() throws RecognitionException {
      try {
         int _type = 60;
         int _channel = 0;
         this.match("parser");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mPRIVATE() throws RecognitionException {
      try {
         int _type = 66;
         int _channel = 0;
         this.match("private");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mPROTECTED() throws RecognitionException {
      try {
         int _type = 67;
         int _channel = 0;
         this.match("protected");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mPUBLIC() throws RecognitionException {
      try {
         int _type = 68;
         int _channel = 0;
         this.match("public");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mRETURNS() throws RecognitionException {
      try {
         int _type = 74;
         int _channel = 0;
         this.match("returns");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mSCOPE() throws RecognitionException {
      try {
         int _type = 81;
         int _channel = 0;
         this.match("scope");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mTHROWS() throws RecognitionException {
      try {
         int _type = 92;
         int _channel = 0;
         this.match("throws");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mTREE() throws RecognitionException {
      try {
         int _type = 95;
         int _channel = 0;
         this.match("tree");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mSTRING_LITERAL() throws RecognitionException {
   }

   public final void mFORCED_ACTION() throws RecognitionException {
   }

   public final void mDOC_COMMENT() throws RecognitionException {
   }

   public final void mSEMPRED() throws RecognitionException {
   }

   public final void mWS() throws RecognitionException {
      try {
         int _type = 99;
         int _channel = 0;
         int alt2 = true;
         byte alt2;
         switch (this.input.LA(1)) {
            case 9:
               alt2 = 2;
               break;
            case 10:
            case 13:
               alt2 = 3;
               break;
            case 32:
               alt2 = 1;
               break;
            default:
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 2, 0, this.input);
               throw nvae;
         }

         switch (alt2) {
            case 1:
               this.match(32);
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.match(9);
               if (this.state.failed) {
                  return;
               }
               break;
            case 3:
               int alt1 = 2;
               int LA1_0 = this.input.LA(1);
               if (LA1_0 == 13) {
                  alt1 = 1;
               }

               switch (alt1) {
                  case 1:
                     this.match(13);
                     if (this.state.failed) {
                        return;
                     }
                  default:
                     this.match(10);
                     if (this.state.failed) {
                        return;
                     }
               }
         }

         if (this.state.backtracking == 0) {
            _channel = 99;
         }

         this.state.type = _type;
         this.state.channel = _channel;
      } finally {
         ;
      }
   }

   public final void mCOMMENT() throws RecognitionException {
      try {
         int _type = 25;
         int _channel = 0;
         List type = new ArrayList() {
            {
               this.add(0);
            }
         };
         int alt3 = true;
         int LA3_0 = this.input.LA(1);
         if (LA3_0 == 47) {
            int LA3_1 = this.input.LA(2);
            byte alt3;
            if (LA3_1 == 47) {
               alt3 = 1;
            } else {
               if (LA3_1 != 42) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  int nvaeMark = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 3, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               alt3 = 2;
            }

            switch (alt3) {
               case 1:
                  this.mSL_COMMENT();
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.mML_COMMENT(type);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     _type = (Integer)type.get(0);
                  }
            }

            if (this.state.backtracking == 0 && _type != 27) {
               _channel = 99;
            }

            this.state.type = _type;
            this.state.channel = _channel;
         } else if (this.state.backtracking > 0) {
            this.state.failed = true;
         } else {
            NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);
            throw nvae;
         }
      } finally {
         ;
      }
   }

   public final void mSL_COMMENT() throws RecognitionException {
      try {
         this.match("//");
         if (!this.state.failed) {
            int alt9 = true;
            int alt9 = this.dfa9.predict(this.input);
            byte alt8;
            int LA8_0;
            int LA7_0;
            byte alt7;
            switch (alt9) {
               case 1:
                  this.match(" $ANTLR ");
                  if (this.state.failed) {
                     return;
                  } else {
                     this.mSRC();
                     if (this.state.failed) {
                        return;
                     } else {
                        alt8 = 2;
                        LA8_0 = this.input.LA(1);
                        if (LA8_0 == 10 || LA8_0 == 13) {
                           alt8 = 1;
                        }

                        switch (alt8) {
                           case 1:
                              alt7 = 2;
                              LA7_0 = this.input.LA(1);
                              if (LA7_0 == 13) {
                                 alt7 = 1;
                              }

                              switch (alt7) {
                                 case 1:
                                    this.match(13);
                                    if (this.state.failed) {
                                       return;
                                    }
                                 default:
                                    this.match(10);
                                    if (this.state.failed) {
                                       return;
                                    }

                                    return;
                              }
                           default:
                              return;
                        }
                     }
                  }
               case 2:
                  while(true) {
                     alt8 = 2;
                     LA8_0 = this.input.LA(1);
                     if (LA8_0 >= 0 && LA8_0 <= 9 || LA8_0 >= 11 && LA8_0 <= 12 || LA8_0 >= 14 && LA8_0 <= 65535) {
                        alt8 = 1;
                     }

                     switch (alt8) {
                        case 1:
                           if ((this.input.LA(1) < 0 || this.input.LA(1) > 9) && (this.input.LA(1) < 11 || this.input.LA(1) > 12) && (this.input.LA(1) < 14 || this.input.LA(1) > 65535)) {
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
                           break;
                        default:
                           alt8 = 2;
                           LA8_0 = this.input.LA(1);
                           if (LA8_0 == 10 || LA8_0 == 13) {
                              alt8 = 1;
                           }

                           switch (alt8) {
                              case 1:
                                 alt7 = 2;
                                 LA7_0 = this.input.LA(1);
                                 if (LA7_0 == 13) {
                                    alt7 = 1;
                                 }

                                 switch (alt7) {
                                    case 1:
                                       this.match(13);
                                       if (this.state.failed) {
                                          return;
                                       }
                                    default:
                                       this.match(10);
                                       if (this.state.failed) {
                                          return;
                                       }

                                       return;
                                 }
                              default:
                                 return;
                           }
                     }
                  }
            }
         }
      } finally {
         ;
      }
   }

   public final void mML_COMMENT(List type) throws RecognitionException {
      try {
         this.match("/*");
         if (!this.state.failed) {
            if (this.state.backtracking == 0) {
               type.set(0, this.input.LA(1) == 42 && this.input.LA(2) != 47 ? 27 : 52);
            }

            do {
               int alt10 = 2;
               int LA10_0 = this.input.LA(1);
               if (LA10_0 == 42) {
                  int LA10_1 = this.input.LA(2);
                  if (LA10_1 == 47) {
                     alt10 = 2;
                  } else if (LA10_1 >= 0 && LA10_1 <= 46 || LA10_1 >= 48 && LA10_1 <= 65535) {
                     alt10 = 1;
                  }
               } else if (LA10_0 >= 0 && LA10_0 <= 41 || LA10_0 >= 43 && LA10_0 <= 65535) {
                  alt10 = 1;
               }

               switch (alt10) {
                  case 1:
                     this.matchAny();
                     break;
                  default:
                     this.match("*/");
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

   public final void mOPEN_ELEMENT_OPTION() throws RecognitionException {
      try {
         int _type = 56;
         int _channel = 0;
         this.match(60);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mCLOSE_ELEMENT_OPTION() throws RecognitionException {
      try {
         int _type = 20;
         int _channel = 0;
         this.match(62);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mAMPERSAND() throws RecognitionException {
      try {
         int _type = 9;
         int _channel = 0;
         this.match(64);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mCOMMA() throws RecognitionException {
      try {
         int _type = 24;
         int _channel = 0;
         this.match(44);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mQUESTION() throws RecognitionException {
      try {
         int _type = 69;
         int _channel = 0;
         this.match(63);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mTREE_BEGIN() throws RecognitionException {
      try {
         int _type = 96;
         int _channel = 0;
         this.match("^(");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mLPAREN() throws RecognitionException {
      try {
         int _type = 51;
         int _channel = 0;
         this.match(40);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mRPAREN() throws RecognitionException {
      try {
         int _type = 78;
         int _channel = 0;
         this.match(41);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mCOLON() throws RecognitionException {
      try {
         int _type = 22;
         int _channel = 0;
         this.match(58);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mSTAR() throws RecognitionException {
      try {
         int _type = 86;
         int _channel = 0;
         this.match(42);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mPLUS() throws RecognitionException {
      try {
         int _type = 62;
         int _channel = 0;
         this.match(43);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mASSIGN() throws RecognitionException {
      try {
         int _type = 13;
         int _channel = 0;
         this.match(61);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mPLUS_ASSIGN() throws RecognitionException {
      try {
         int _type = 63;
         int _channel = 0;
         this.match("+=");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mIMPLIES() throws RecognitionException {
      try {
         int _type = 44;
         int _channel = 0;
         this.match("=>");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mREWRITE() throws RecognitionException {
      try {
         int _type = 75;
         int _channel = 0;
         this.match("->");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mSEMI() throws RecognitionException {
      try {
         int _type = 82;
         int _channel = 0;
         this.match(59);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mROOT() throws RecognitionException {
      try {
         int _type = 77;
         int _channel = 0;
         this.match(94);
         if (!this.state.failed) {
            if (this.state.backtracking == 0) {
               this.hasASTOperator = true;
            }

            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mBANG() throws RecognitionException {
      try {
         int _type = 15;
         int _channel = 0;
         this.match(33);
         if (!this.state.failed) {
            if (this.state.backtracking == 0) {
               this.hasASTOperator = true;
            }

            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mOR() throws RecognitionException {
      try {
         int _type = 59;
         int _channel = 0;
         this.match(124);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mWILDCARD() throws RecognitionException {
      try {
         int _type = 98;
         int _channel = 0;
         this.match(46);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mETC() throws RecognitionException {
      try {
         int _type = 37;
         int _channel = 0;
         this.match("...");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mRANGE() throws RecognitionException {
      try {
         int _type = 70;
         int _channel = 0;
         this.match("..");
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mNOT() throws RecognitionException {
      try {
         int _type = 55;
         int _channel = 0;
         this.match(126);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mRCURLY() throws RecognitionException {
      try {
         int _type = 71;
         int _channel = 0;
         this.match(125);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mDOLLAR() throws RecognitionException {
      try {
         int _type = 28;
         int _channel = 0;
         this.match(36);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mSTRAY_BRACKET() throws RecognitionException {
      try {
         int _type = 87;
         int _channel = 0;
         this.match(93);
         if (!this.state.failed) {
            this.state.type = _type;
            this.state.channel = _channel;
         }
      } finally {
         ;
      }
   }

   public final void mCHAR_LITERAL() throws RecognitionException {
      try {
         int _type = 18;
         int _channel = 0;
         this.match(39);
         if (!this.state.failed) {
            while(true) {
               int alt11 = 3;
               int LA11_0 = this.input.LA(1);
               if (LA11_0 == 92) {
                  alt11 = 1;
               } else if (LA11_0 >= 0 && LA11_0 <= 38 || LA11_0 >= 40 && LA11_0 <= 91 || LA11_0 >= 93 && LA11_0 <= 65535) {
                  alt11 = 2;
               }

               switch (alt11) {
                  case 1:
                     this.mESC();
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 2:
                     if ((this.input.LA(1) < 0 || this.input.LA(1) > 38) && (this.input.LA(1) < 40 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 65535)) {
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
                     break;
                  default:
                     this.match(39);
                     if (this.state.failed) {
                        return;
                     }

                     if (this.state.backtracking == 0) {
                        StringBuffer s = Grammar.getUnescapedStringFromGrammarStringLiteral(this.getText());
                        if (s.length() > 1) {
                           _type = 88;
                        }
                     }

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

   public final void mDOUBLE_QUOTE_STRING_LITERAL() throws RecognitionException {
      try {
         int _type = 31;
         int _channel = 0;
         StringBuilder builder = new StringBuilder();
         this.match(34);
         if (!this.state.failed) {
            if (this.state.backtracking == 0) {
               builder.append('"');
            }

            while(true) {
               int alt12 = 4;
               int LA12_0 = this.input.LA(1);
               if (LA12_0 == 92) {
                  int LA12_2 = this.input.LA(2);
                  if (LA12_2 == 34 && this.synpred2_ANTLR()) {
                     alt12 = 1;
                  } else if (LA12_2 >= 0 && LA12_2 <= 33 || LA12_2 >= 35 && LA12_2 <= 65535) {
                     alt12 = 2;
                  }
               } else if (LA12_0 >= 0 && LA12_0 <= 33 || LA12_0 >= 35 && LA12_0 <= 91 || LA12_0 >= 93 && LA12_0 <= 65535) {
                  alt12 = 3;
               }

               int c;
               MismatchedSetException mse;
               switch (alt12) {
                  case 1:
                     this.match(92);
                     if (this.state.failed) {
                        return;
                     }

                     this.match(34);
                     if (this.state.failed) {
                        return;
                     }

                     if (this.state.backtracking == 0) {
                        builder.append('"');
                     }
                     break;
                  case 2:
                     this.match(92);
                     if (this.state.failed) {
                        return;
                     }

                     c = this.input.LA(1);
                     if ((this.input.LA(1) < 0 || this.input.LA(1) > 33) && (this.input.LA(1) < 35 || this.input.LA(1) > 65535)) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     this.state.failed = false;
                     if (this.state.backtracking == 0) {
                        builder.append("\\" + (char)c);
                     }
                     break;
                  case 3:
                     c = this.input.LA(1);
                     if ((this.input.LA(1) < 0 || this.input.LA(1) > 33) && (this.input.LA(1) < 35 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 65535)) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     this.state.failed = false;
                     if (this.state.backtracking == 0) {
                        builder.append((char)c);
                     }
                     break;
                  default:
                     this.match(34);
                     if (this.state.failed) {
                        return;
                     }

                     if (this.state.backtracking == 0) {
                        builder.append('"');
                     }

                     if (this.state.backtracking == 0) {
                        this.setText(builder.toString());
                     }

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

   public final void mDOUBLE_ANGLE_STRING_LITERAL() throws RecognitionException {
      try {
         int _type = 30;
         int _channel = 0;
         this.match("<<");
         if (!this.state.failed) {
            do {
               int alt13 = 2;
               int LA13_0 = this.input.LA(1);
               if (LA13_0 == 62) {
                  int LA13_1 = this.input.LA(2);
                  if (LA13_1 == 62) {
                     alt13 = 2;
                  } else if (LA13_1 >= 0 && LA13_1 <= 61 || LA13_1 >= 63 && LA13_1 <= 65535) {
                     alt13 = 1;
                  }
               } else if (LA13_0 >= 0 && LA13_0 <= 61 || LA13_0 >= 63 && LA13_0 <= 65535) {
                  alt13 = 1;
               }

               switch (alt13) {
                  case 1:
                     this.matchAny();
                     break;
                  default:
                     this.match(">>");
                     if (this.state.failed) {
                        return;
                     }

                     this.state.type = _type;
                     this.state.channel = _channel;
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
         this.match(92);
         if (!this.state.failed) {
            this.matchAny();
            if (!this.state.failed) {
               ;
            }
         }
      } finally {
         ;
      }
   }

   public final void mDIGIT() throws RecognitionException {
      try {
         if (this.input.LA(1) >= 48 && this.input.LA(1) <= 57) {
            this.input.consume();
            this.state.failed = false;
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

   public final void mXDIGIT() throws RecognitionException {
      try {
         if (this.input.LA(1) >= 48 && this.input.LA(1) <= 57 || this.input.LA(1) >= 65 && this.input.LA(1) <= 70 || this.input.LA(1) >= 97 && this.input.LA(1) <= 102) {
            this.input.consume();
            this.state.failed = false;
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
         int _type = 47;
         int _channel = 0;
         int cnt14 = 0;

         while(true) {
            int alt14 = 2;
            int LA14_0 = this.input.LA(1);
            if (LA14_0 >= 48 && LA14_0 <= 57) {
               alt14 = 1;
            }

            switch (alt14) {
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
                  ++cnt14;
                  break;
               default:
                  if (cnt14 >= 1) {
                     this.state.type = _type;
                     this.state.channel = _channel;
                     return;
                  }

                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(14, this.input);
                  throw eee;
            }
         }
      } finally {
         ;
      }
   }

   public final void mARG_ACTION() throws RecognitionException {
      try {
         int _type = 12;
         int _channel = 0;
         List text = new ArrayList() {
            {
               this.add((Object)null);
            }
         };
         this.match(91);
         if (!this.state.failed) {
            this.mNESTED_ARG_ACTION(text);
            if (!this.state.failed) {
               this.match(93);
               if (!this.state.failed) {
                  if (this.state.backtracking == 0) {
                     this.setText((String)text.get(0));
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

   public final void mNESTED_ARG_ACTION(List text) throws RecognitionException {
      try {
         CommonToken ACTION_STRING_LITERAL1 = null;
         CommonToken ACTION_CHAR_LITERAL2 = null;
         text.set(0, "");
         StringBuilder builder = new StringBuilder();

         while(true) {
            int alt15 = 6;
            int LA15_0 = this.input.LA(1);
            int ACTION_CHAR_LITERAL2Start867;
            if (LA15_0 == 92) {
               ACTION_CHAR_LITERAL2Start867 = this.input.LA(2);
               if (ACTION_CHAR_LITERAL2Start867 == 93 && this.synpred3_ANTLR()) {
                  alt15 = 1;
               } else if (ACTION_CHAR_LITERAL2Start867 >= 0 && ACTION_CHAR_LITERAL2Start867 <= 92 || ACTION_CHAR_LITERAL2Start867 >= 94 && ACTION_CHAR_LITERAL2Start867 <= 65535) {
                  alt15 = 2;
               }
            } else if (LA15_0 == 34) {
               alt15 = 3;
            } else if (LA15_0 == 39) {
               alt15 = 4;
            } else if (LA15_0 >= 0 && LA15_0 <= 33 || LA15_0 >= 35 && LA15_0 <= 38 || LA15_0 >= 40 && LA15_0 <= 91 || LA15_0 >= 94 && LA15_0 <= 65535) {
               alt15 = 5;
            }

            int c;
            int ACTION_CHAR_LITERAL2StartLine867;
            int ACTION_CHAR_LITERAL2StartCharPos867;
            MismatchedSetException mse;
            switch (alt15) {
               case 1:
                  this.match(92);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(93);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     builder.append("]");
                  }
                  break;
               case 2:
                  this.match(92);
                  if (this.state.failed) {
                     return;
                  }

                  c = this.input.LA(1);
                  if (this.input.LA(1) >= 0 && this.input.LA(1) <= 92 || this.input.LA(1) >= 94 && this.input.LA(1) <= 65535) {
                     this.input.consume();
                     this.state.failed = false;
                     if (this.state.backtracking == 0) {
                        builder.append("\\" + (char)c);
                     }
                     break;
                  }

                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  mse = new MismatchedSetException((BitSet)null, this.input);
                  this.recover(mse);
                  throw mse;
               case 3:
                  ACTION_CHAR_LITERAL2Start867 = this.getCharIndex();
                  ACTION_CHAR_LITERAL2StartLine867 = this.getLine();
                  ACTION_CHAR_LITERAL2StartCharPos867 = this.getCharPositionInLine();
                  this.mACTION_STRING_LITERAL();
                  if (this.state.failed) {
                     return;
                  }

                  ACTION_STRING_LITERAL1 = new CommonToken(this.input, 0, 0, ACTION_CHAR_LITERAL2Start867, this.getCharIndex() - 1);
                  ACTION_STRING_LITERAL1.setLine(ACTION_CHAR_LITERAL2StartLine867);
                  ACTION_STRING_LITERAL1.setCharPositionInLine(ACTION_CHAR_LITERAL2StartCharPos867);
                  if (this.state.backtracking == 0) {
                     builder.append(ACTION_STRING_LITERAL1 != null ? ACTION_STRING_LITERAL1.getText() : null);
                  }
                  break;
               case 4:
                  ACTION_CHAR_LITERAL2Start867 = this.getCharIndex();
                  ACTION_CHAR_LITERAL2StartLine867 = this.getLine();
                  ACTION_CHAR_LITERAL2StartCharPos867 = this.getCharPositionInLine();
                  this.mACTION_CHAR_LITERAL();
                  if (this.state.failed) {
                     return;
                  }

                  ACTION_CHAR_LITERAL2 = new CommonToken(this.input, 0, 0, ACTION_CHAR_LITERAL2Start867, this.getCharIndex() - 1);
                  ACTION_CHAR_LITERAL2.setLine(ACTION_CHAR_LITERAL2StartLine867);
                  ACTION_CHAR_LITERAL2.setCharPositionInLine(ACTION_CHAR_LITERAL2StartCharPos867);
                  if (this.state.backtracking == 0) {
                     builder.append(ACTION_CHAR_LITERAL2 != null ? ACTION_CHAR_LITERAL2.getText() : null);
                  }
                  break;
               case 5:
                  c = this.input.LA(1);
                  if (this.input.LA(1) >= 0 && this.input.LA(1) <= 33 || this.input.LA(1) >= 35 && this.input.LA(1) <= 38 || this.input.LA(1) >= 40 && this.input.LA(1) <= 91 || this.input.LA(1) >= 94 && this.input.LA(1) <= 65535) {
                     this.input.consume();
                     this.state.failed = false;
                     if (this.state.backtracking == 0) {
                        builder.append((char)c);
                     }
                     break;
                  }

                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  mse = new MismatchedSetException((BitSet)null, this.input);
                  this.recover(mse);
                  throw mse;
               default:
                  if (this.state.backtracking == 0) {
                     text.set(0, builder.toString());
                  }

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
         int actionLine = this.getLine();
         int actionColumn = this.getCharPositionInLine();
         this.mNESTED_ACTION();
         if (!this.state.failed) {
            int alt16 = 2;
            int LA16_0 = this.input.LA(1);
            if (LA16_0 == 63) {
               alt16 = 1;
            }

            switch (alt16) {
               case 1:
                  this.match(63);
                  if (this.state.failed) {
                     return;
                  } else if (this.state.backtracking == 0) {
                     _type = 83;
                  }
               default:
                  if (this.state.backtracking == 0) {
                     String action = this.getText();
                     int n = 1;
                     if (action.startsWith("{{") && action.endsWith("}}")) {
                        _type = 39;
                        n = 2;
                     }

                     action = action.substring(n, action.length() - n - (_type == 83 ? 1 : 0));
                     this.setText(action);
                  }

                  this.state.type = _type;
                  this.state.channel = _channel;
            }
         }
      } finally {
         ;
      }
   }

   public final void mNESTED_ACTION() throws RecognitionException {
      try {
         this.match(123);
         if (!this.state.failed) {
            while(true) {
               int alt17 = 7;
               int LA17_0 = this.input.LA(1);
               if (LA17_0 == 123) {
                  alt17 = 1;
               } else if (LA17_0 == 39) {
                  alt17 = 2;
               } else if (LA17_0 == 47) {
                  int LA17_4 = this.input.LA(2);
                  if (this.synpred4_ANTLR()) {
                     alt17 = 3;
                  } else {
                     alt17 = 6;
                  }
               } else if (LA17_0 == 34) {
                  alt17 = 4;
               } else if (LA17_0 == 92) {
                  alt17 = 5;
               } else if (LA17_0 >= 0 && LA17_0 <= 33 || LA17_0 >= 35 && LA17_0 <= 38 || LA17_0 >= 40 && LA17_0 <= 46 || LA17_0 >= 48 && LA17_0 <= 91 || LA17_0 >= 93 && LA17_0 <= 122 || LA17_0 == 124 || LA17_0 >= 126 && LA17_0 <= 65535) {
                  alt17 = 6;
               }

               switch (alt17) {
                  case 1:
                     this.mNESTED_ACTION();
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 2:
                     this.mACTION_CHAR_LITERAL();
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 3:
                     this.mCOMMENT();
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 4:
                     this.mACTION_STRING_LITERAL();
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 5:
                     this.mACTION_ESC();
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 6:
                     if ((this.input.LA(1) < 0 || this.input.LA(1) > 33) && (this.input.LA(1) < 35 || this.input.LA(1) > 38) && (this.input.LA(1) < 40 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 122) && this.input.LA(1) != 124 && (this.input.LA(1) < 126 || this.input.LA(1) > 65535)) {
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
                     break;
                  default:
                     this.match(125);
                     if (this.state.failed) {
                        return;
                     }

                     return;
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mACTION_CHAR_LITERAL() throws RecognitionException {
      try {
         this.match(39);
         if (!this.state.failed) {
            while(true) {
               int alt18 = 3;
               int LA18_0 = this.input.LA(1);
               if (LA18_0 == 92) {
                  alt18 = 1;
               } else if (LA18_0 >= 0 && LA18_0 <= 38 || LA18_0 >= 40 && LA18_0 <= 91 || LA18_0 >= 93 && LA18_0 <= 65535) {
                  alt18 = 2;
               }

               switch (alt18) {
                  case 1:
                     this.mACTION_ESC();
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 2:
                     if ((this.input.LA(1) < 0 || this.input.LA(1) > 38) && (this.input.LA(1) < 40 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 65535)) {
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
                     break;
                  default:
                     this.match(39);
                     if (this.state.failed) {
                        return;
                     }

                     return;
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mACTION_STRING_LITERAL() throws RecognitionException {
      try {
         this.match(34);
         if (!this.state.failed) {
            while(true) {
               int alt19 = 3;
               int LA19_0 = this.input.LA(1);
               if (LA19_0 == 92) {
                  alt19 = 1;
               } else if (LA19_0 >= 0 && LA19_0 <= 33 || LA19_0 >= 35 && LA19_0 <= 91 || LA19_0 >= 93 && LA19_0 <= 65535) {
                  alt19 = 2;
               }

               switch (alt19) {
                  case 1:
                     this.mACTION_ESC();
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 2:
                     if ((this.input.LA(1) < 0 || this.input.LA(1) > 33) && (this.input.LA(1) < 35 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 65535)) {
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
                     break;
                  default:
                     this.match(34);
                     if (this.state.failed) {
                        return;
                     }

                     return;
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mACTION_ESC() throws RecognitionException {
      try {
         int alt20 = true;
         int LA20_0 = this.input.LA(1);
         if (LA20_0 != 92) {
            if (this.state.backtracking > 0) {
               this.state.failed = true;
            } else {
               NoViableAltException nvae = new NoViableAltException("", 20, 0, this.input);
               throw nvae;
            }
         } else {
            int LA20_1 = this.input.LA(2);
            byte alt20;
            if (LA20_1 == 39) {
               alt20 = 1;
            } else if (LA20_1 == 34) {
               alt20 = 2;
            } else {
               if ((LA20_1 < 0 || LA20_1 > 33) && (LA20_1 < 35 || LA20_1 > 38) && (LA20_1 < 40 || LA20_1 > 65535)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     int nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 20, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }
               }

               alt20 = 3;
            }

            switch (alt20) {
               case 1:
                  this.match("\\'");
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.match("\\\"");
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 3:
                  this.match(92);
                  if (this.state.failed) {
                     return;
                  }

                  if ((this.input.LA(1) < 0 || this.input.LA(1) > 33) && (this.input.LA(1) < 35 || this.input.LA(1) > 38) && (this.input.LA(1) < 40 || this.input.LA(1) > 65535)) {
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
            }

         }
      } finally {
         ;
      }
   }

   public final void mTOKEN_REF() throws RecognitionException {
      try {
         int _type = 94;
         int _channel = 0;
         this.matchRange(65, 90);
         if (!this.state.failed) {
            while(true) {
               int alt21 = 2;
               int LA21_0 = this.input.LA(1);
               if (LA21_0 >= 48 && LA21_0 <= 57 || LA21_0 >= 65 && LA21_0 <= 90 || LA21_0 == 95 || LA21_0 >= 97 && LA21_0 <= 122) {
                  alt21 = 1;
               }

               switch (alt21) {
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

   public final void mTOKENS() throws RecognitionException {
      try {
         int _type = 93;
         int _channel = 0;
         this.match("tokens");
         if (!this.state.failed) {
            this.mWS_LOOP();
            if (!this.state.failed) {
               this.match(123);
               if (!this.state.failed) {
                  this.state.type = _type;
                  this.state.channel = _channel;
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mOPTIONS() throws RecognitionException {
      try {
         int _type = 58;
         int _channel = 0;
         this.match("options");
         if (!this.state.failed) {
            this.mWS_LOOP();
            if (!this.state.failed) {
               this.match(123);
               if (!this.state.failed) {
                  this.state.type = _type;
                  this.state.channel = _channel;
               }
            }
         }
      } finally {
         ;
      }
   }

   public final void mRULE_REF() throws RecognitionException {
      try {
         int _type = 80;
         int _channel = 0;
         int t = false;
         this.matchRange(97, 122);
         if (!this.state.failed) {
            while(true) {
               int alt22 = 2;
               int LA22_0 = this.input.LA(1);
               if (LA22_0 >= 48 && LA22_0 <= 57 || LA22_0 >= 65 && LA22_0 <= 90 || LA22_0 == 95 || LA22_0 >= 97 && LA22_0 <= 122) {
                  alt22 = 1;
               }

               switch (alt22) {
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

   public final void mWS_LOOP() throws RecognitionException {
      try {
         while(true) {
            int alt23 = 3;
            int LA23_0 = this.input.LA(1);
            if ((LA23_0 < 9 || LA23_0 > 10) && LA23_0 != 13 && LA23_0 != 32) {
               if (LA23_0 == 47) {
                  alt23 = 2;
               }
            } else {
               alt23 = 1;
            }

            switch (alt23) {
               case 1:
                  this.mWS();
                  if (!this.state.failed) {
                     break;
                  }

                  return;
               case 2:
                  this.mCOMMENT();
                  if (!this.state.failed) {
                     break;
                  }

                  return;
               default:
                  return;
            }
         }
      } finally {
         ;
      }
   }

   public final void mWS_OPT() throws RecognitionException {
      try {
         int alt24 = 2;
         int LA24_0 = this.input.LA(1);
         if (LA24_0 >= 9 && LA24_0 <= 10 || LA24_0 == 13 || LA24_0 == 32) {
            alt24 = 1;
         }

         switch (alt24) {
            case 1:
               this.mWS();
               if (this.state.failed) {
                  return;
               }
            default:
         }
      } finally {
         ;
      }
   }

   public final void mSRC() throws RecognitionException {
      try {
         CommonToken file = null;
         CommonToken line = null;
         this.match("src");
         if (!this.state.failed) {
            this.match(32);
            if (!this.state.failed) {
               int fileStart1272 = this.getCharIndex();
               int fileStartLine1272 = this.getLine();
               int fileStartCharPos1272 = this.getCharPositionInLine();
               this.mACTION_STRING_LITERAL();
               if (!this.state.failed) {
                  file = new CommonToken(this.input, 0, 0, fileStart1272, this.getCharIndex() - 1);
                  file.setLine(fileStartLine1272);
                  file.setCharPositionInLine(fileStartCharPos1272);
                  this.match(32);
                  if (!this.state.failed) {
                     int lineStart1278 = this.getCharIndex();
                     int lineStartLine1278 = this.getLine();
                     int lineStartCharPos1278 = this.getCharPositionInLine();
                     this.mINT();
                     if (!this.state.failed) {
                        line = new CommonToken(this.input, 0, 0, lineStart1278, this.getCharIndex() - 1);
                        line.setLine(lineStartLine1278);
                        line.setCharPositionInLine(lineStartCharPos1278);
                        if (this.state.backtracking == 0) {
                           this.setFileName((file != null ? file.getText() : null).substring(1, (file != null ? file.getText() : null).length() - 1));
                           this.input.setLine(Integer.parseInt(line != null ? line.getText() : null) - 1);
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

   public void mTokens() throws RecognitionException {
      int alt25 = true;
      int alt25 = this.dfa25.predict(this.input);
      switch (alt25) {
         case 1:
            this.mCATCH();
            if (this.state.failed) {
               return;
            }
            break;
         case 2:
            this.mFINALLY();
            if (this.state.failed) {
               return;
            }
            break;
         case 3:
            this.mFRAGMENT();
            if (this.state.failed) {
               return;
            }
            break;
         case 4:
            this.mGRAMMAR();
            if (this.state.failed) {
               return;
            }
            break;
         case 5:
            this.mIMPORT();
            if (this.state.failed) {
               return;
            }
            break;
         case 6:
            this.mLEXER();
            if (this.state.failed) {
               return;
            }
            break;
         case 7:
            this.mPARSER();
            if (this.state.failed) {
               return;
            }
            break;
         case 8:
            this.mPRIVATE();
            if (this.state.failed) {
               return;
            }
            break;
         case 9:
            this.mPROTECTED();
            if (this.state.failed) {
               return;
            }
            break;
         case 10:
            this.mPUBLIC();
            if (this.state.failed) {
               return;
            }
            break;
         case 11:
            this.mRETURNS();
            if (this.state.failed) {
               return;
            }
            break;
         case 12:
            this.mSCOPE();
            if (this.state.failed) {
               return;
            }
            break;
         case 13:
            this.mTHROWS();
            if (this.state.failed) {
               return;
            }
            break;
         case 14:
            this.mTREE();
            if (this.state.failed) {
               return;
            }
            break;
         case 15:
            this.mWS();
            if (this.state.failed) {
               return;
            }
            break;
         case 16:
            this.mCOMMENT();
            if (this.state.failed) {
               return;
            }
            break;
         case 17:
            this.mOPEN_ELEMENT_OPTION();
            if (this.state.failed) {
               return;
            }
            break;
         case 18:
            this.mCLOSE_ELEMENT_OPTION();
            if (this.state.failed) {
               return;
            }
            break;
         case 19:
            this.mAMPERSAND();
            if (this.state.failed) {
               return;
            }
            break;
         case 20:
            this.mCOMMA();
            if (this.state.failed) {
               return;
            }
            break;
         case 21:
            this.mQUESTION();
            if (this.state.failed) {
               return;
            }
            break;
         case 22:
            this.mTREE_BEGIN();
            if (this.state.failed) {
               return;
            }
            break;
         case 23:
            this.mLPAREN();
            if (this.state.failed) {
               return;
            }
            break;
         case 24:
            this.mRPAREN();
            if (this.state.failed) {
               return;
            }
            break;
         case 25:
            this.mCOLON();
            if (this.state.failed) {
               return;
            }
            break;
         case 26:
            this.mSTAR();
            if (this.state.failed) {
               return;
            }
            break;
         case 27:
            this.mPLUS();
            if (this.state.failed) {
               return;
            }
            break;
         case 28:
            this.mASSIGN();
            if (this.state.failed) {
               return;
            }
            break;
         case 29:
            this.mPLUS_ASSIGN();
            if (this.state.failed) {
               return;
            }
            break;
         case 30:
            this.mIMPLIES();
            if (this.state.failed) {
               return;
            }
            break;
         case 31:
            this.mREWRITE();
            if (this.state.failed) {
               return;
            }
            break;
         case 32:
            this.mSEMI();
            if (this.state.failed) {
               return;
            }
            break;
         case 33:
            this.mROOT();
            if (this.state.failed) {
               return;
            }
            break;
         case 34:
            this.mBANG();
            if (this.state.failed) {
               return;
            }
            break;
         case 35:
            this.mOR();
            if (this.state.failed) {
               return;
            }
            break;
         case 36:
            this.mWILDCARD();
            if (this.state.failed) {
               return;
            }
            break;
         case 37:
            this.mETC();
            if (this.state.failed) {
               return;
            }
            break;
         case 38:
            this.mRANGE();
            if (this.state.failed) {
               return;
            }
            break;
         case 39:
            this.mNOT();
            if (this.state.failed) {
               return;
            }
            break;
         case 40:
            this.mRCURLY();
            if (this.state.failed) {
               return;
            }
            break;
         case 41:
            this.mDOLLAR();
            if (this.state.failed) {
               return;
            }
            break;
         case 42:
            this.mSTRAY_BRACKET();
            if (this.state.failed) {
               return;
            }
            break;
         case 43:
            this.mCHAR_LITERAL();
            if (this.state.failed) {
               return;
            }
            break;
         case 44:
            this.mDOUBLE_QUOTE_STRING_LITERAL();
            if (this.state.failed) {
               return;
            }
            break;
         case 45:
            this.mDOUBLE_ANGLE_STRING_LITERAL();
            if (this.state.failed) {
               return;
            }
            break;
         case 46:
            this.mINT();
            if (this.state.failed) {
               return;
            }
            break;
         case 47:
            this.mARG_ACTION();
            if (this.state.failed) {
               return;
            }
            break;
         case 48:
            this.mACTION();
            if (this.state.failed) {
               return;
            }
            break;
         case 49:
            this.mTOKEN_REF();
            if (this.state.failed) {
               return;
            }
            break;
         case 50:
            this.mTOKENS();
            if (this.state.failed) {
               return;
            }
            break;
         case 51:
            this.mOPTIONS();
            if (this.state.failed) {
               return;
            }
            break;
         case 52:
            this.mRULE_REF();
            if (this.state.failed) {
               return;
            }
      }

   }

   public final void synpred1_ANTLR_fragment() throws RecognitionException {
      this.match(" $ANTLR");
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred2_ANTLR_fragment() throws RecognitionException {
      this.match("\\\"");
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred3_ANTLR_fragment() throws RecognitionException {
      this.match("\\]");
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred4_ANTLR_fragment() throws RecognitionException {
      int alt26 = true;
      int LA26_0 = this.input.LA(1);
      if (LA26_0 == 47) {
         int LA26_1 = this.input.LA(2);
         byte alt26;
         if (LA26_1 == 47) {
            alt26 = 1;
         } else {
            if (LA26_1 != 42) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               int nvaeMark = this.input.mark();

               try {
                  this.input.consume();
                  NoViableAltException nvae = new NoViableAltException("", 26, 1, this.input);
                  throw nvae;
               } finally {
                  this.input.rewind(nvaeMark);
               }
            }

            alt26 = 2;
         }

         switch (alt26) {
            case 1:
               this.match("//");
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.match("/*");
               if (this.state.failed) {
                  return;
               }
         }

      } else if (this.state.backtracking > 0) {
         this.state.failed = true;
      } else {
         NoViableAltException nvae = new NoViableAltException("", 26, 0, this.input);
         throw nvae;
      }
   }

   public final boolean synpred4_ANTLR() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred4_ANTLR_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred1_ANTLR() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred1_ANTLR_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred2_ANTLR() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred2_ANTLR_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred3_ANTLR() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred3_ANTLR_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   static {
      int numStates = DFA9_transitionS.length;
      DFA9_transition = new short[numStates][];

      int i;
      for(i = 0; i < numStates; ++i) {
         DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
      }

      DFA25_transitionS = new String[]{"\u0002\n\u0002\uffff\u0001\n\u0012\uffff\u0001\n\u0001\u001a\u0001\"\u0001\uffff\u0001\u001f\u0002\uffff\u0001!\u0001\u0012\u0001\u0013\u0001\u0015\u0001\u0016\u0001\u000f\u0001\u0018\u0001\u001c\u0001\u000b\n#\u0001\u0014\u0001\u0019\u0001\f\u0001\u0017\u0001\r\u0001\u0010\u0001\u000e\u001a&\u0001$\u0001\uffff\u0001 \u0001\u0011\u0002\uffff\u0002(\u0001\u0001\u0002(\u0001\u0002\u0001\u0003\u0001(\u0001\u0004\u0002(\u0001\u0005\u0002(\u0001'\u0001\u0006\u0001(\u0001\u0007\u0001\b\u0001\t\u0006(\u0001%\u0001\u001b\u0001\u001e\u0001\u001d", "\u0001)", "\u0001*\b\uffff\u0001+", "\u0001,", "\u0001-", "\u0001.", "\u0001/\u0010\uffff\u00010\u0002\uffff\u00011", "\u00012", "\u00013", "\u00014\u0006\uffff\u00016\u0002\uffff\u00015", "", "", "\u00017", "", "", "", "", "\u00019", "", "", "", "", "\u0001;", "\u0001=", "", "", "", "", "\u0001?", "", "", "", "", "", "", "", "", "", "", "\u0001A", "", "\u0001B", "\u0001C", "\u0001D", "\u0001E", "\u0001F", "\u0001G", "\u0001H", "\u0001I\u0005\uffff\u0001J", "\u0001K", "\u0001L", "\u0001M", "\u0001N", "\u0001O", "\u0001P", "", "", "", "", "", "", "", "", "\u0001Q", "", "\u0001S", "\u0001T", "\u0001U", "\u0001V", "\u0001W", "\u0001X", "\u0001Y", "\u0001Z", "\u0001[", "\u0001\\", "\u0001]", "\u0001^", "\u0001_", "\u0001`", "\u0001a", "\u0001b", "", "", "\u0001c", "\u0001d", "\u0001e", "\u0001f", "\u0001g", "\u0001h", "\u0001i", "\u0001j", "\u0001k", "\u0001l", "\u0001m", "\u0001n", "\u0001o", "\u0001p", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "\u0001r", "\u0001s", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "\u0001u", "\u0001v", "\u0001w", "\u0001x", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "\u0001z", "\u0001{", "\u0001|", "\u0001}", "\u0001~", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "\u0001\u0080", "", "\u0001\u0081", "\u0001\u0082", "", "\u0001\u0083", "\u0001\u0084", "\u0001\u0085", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "\u0001\u0088", "\u0001\u0089", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "\u0001\u008b", "", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "\u0002\u008d\u0002\uffff\u0001\u008d\u0012\uffff\u0001\u008d\u000e\uffff\u0001\u008dK\uffff\u0001\u008d", "\u0001\u008e", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "\u0001\u0090", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "", "", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "\u0001\u0093", "", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "", "", "\u0002\u0095\u0002\uffff\u0001\u0095\u0012\uffff\u0001\u0095\u000e\uffff\u0001\u0095K\uffff\u0001\u0095", "", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", "", "", "\u0001\u0097", "", "", "", "\n(\u0007\uffff\u001a(\u0004\uffff\u0001(\u0001\uffff\u001a(", ""};
      DFA25_eot = DFA.unpackEncodedString("\u0001\uffff\t(\u0002\uffff\u00018\u0004\uffff\u0001:\u0004\uffff\u0001<\u0001>\u0004\uffff\u0001@\n\uffff\u0001(\u0001\uffff\u000e(\b\uffff\u0001R\u0001\uffff\u0010(\u0002\uffff\u000e(\u0001q\u0002(\u0001t\u0004(\u0001y\u0005(\u0001\u007f\u0001(\u0001\uffff\u0002(\u0001\uffff\u0003(\u0001\u0086\u0001\uffff\u0001\u0087\u0002(\u0001\u008a\u0001(\u0001\uffff\u0001\u008c\u0002(\u0001\u008f\u0001(\u0001\u0091\u0002\uffff\u0001\u0092\u0001(\u0001\uffff\u0001\u0094\u0002\uffff\u0001(\u0001\uffff\u0001\u0096\u0002\uffff\u0001(\u0003\uffff\u0001\u0098\u0001\uffff");
      DFA25_eof = DFA.unpackEncodedString("\u0099\uffff");
      DFA25_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0001a\u0001i\u0001r\u0001m\u0001e\u0001a\u0001e\u0001c\u0001h\u0002\uffff\u0001<\u0004\uffff\u0001(\u0004\uffff\u0001=\u0001>\u0004\uffff\u0001.\n\uffff\u0001p\u0001\uffff\u0001t\u0001n\u0002a\u0001p\u0001x\u0001r\u0001i\u0001b\u0001t\u0001o\u0001r\u0001e\u0001k\b\uffff\u0001.\u0001\uffff\u0001t\u0001c\u0001a\u0001g\u0001m\u0001o\u0001e\u0001s\u0001v\u0001t\u0001l\u0001u\u0001p\u0001o\u0002e\u0002\uffff\u0001i\u0001h\u0001l\u0002m\u0002r\u0001e\u0001a\u0001e\u0001i\u0001r\u0001e\u0001w\u00010\u0001n\u0001o\u00010\u0001l\u0001e\u0001a\u0001t\u00010\u0001r\u0001t\u0002c\u0001n\u00010\u0001s\u0001\uffff\u0001s\u0001n\u0001\uffff\u0001y\u0001n\u0001r\u00010\u0001\uffff\u00010\u0001e\u0001t\u00010\u0001s\u0001\uffff\u00010\u0001\t\u0001s\u00010\u0001t\u00010\u0002\uffff\u00010\u0001e\u0001\uffff\u00010\u0002\uffff\u0001\t\u0001\uffff\u00010\u0002\uffff\u0001d\u0003\uffff\u00010\u0001\uffff");
      DFA25_max = DFA.unpackEncodedStringToUnsignedChars("\u0001~\u0001a\u0002r\u0001m\u0001e\u0001u\u0001e\u0001c\u0001r\u0002\uffff\u0001<\u0004\uffff\u0001(\u0004\uffff\u0001=\u0001>\u0004\uffff\u0001.\n\uffff\u0001p\u0001\uffff\u0001t\u0001n\u0002a\u0001p\u0001x\u0001r\u0001o\u0001b\u0001t\u0001o\u0001r\u0001e\u0001k\b\uffff\u0001.\u0001\uffff\u0001t\u0001c\u0001a\u0001g\u0001m\u0001o\u0001e\u0001s\u0001v\u0001t\u0001l\u0001u\u0001p\u0001o\u0002e\u0002\uffff\u0001i\u0001h\u0001l\u0002m\u0002r\u0001e\u0001a\u0001e\u0001i\u0001r\u0001e\u0001w\u0001z\u0001n\u0001o\u0001z\u0001l\u0001e\u0001a\u0001t\u0001z\u0001r\u0001t\u0002c\u0001n\u0001z\u0001s\u0001\uffff\u0001s\u0001n\u0001\uffff\u0001y\u0001n\u0001r\u0001z\u0001\uffff\u0001z\u0001e\u0001t\u0001z\u0001s\u0001\uffff\u0001z\u0001{\u0001s\u0001z\u0001t\u0001z\u0002\uffff\u0001z\u0001e\u0001\uffff\u0001z\u0002\uffff\u0001{\u0001\uffff\u0001z\u0002\uffff\u0001d\u0003\uffff\u0001z\u0001\uffff");
      DFA25_accept = DFA.unpackEncodedString("\n\uffff\u0001\u000f\u0001\u0010\u0001\uffff\u0001\u0012\u0001\u0013\u0001\u0014\u0001\u0015\u0001\uffff\u0001\u0017\u0001\u0018\u0001\u0019\u0001\u001a\u0002\uffff\u0001\u001f\u0001 \u0001\"\u0001#\u0001\uffff\u0001'\u0001(\u0001)\u0001*\u0001+\u0001,\u0001.\u0001/\u00010\u00011\u0001\uffff\u00014\u000e\uffff\u0001-\u0001\u0011\u0001\u0016\u0001!\u0001\u001d\u0001\u001b\u0001\u001e\u0001\u001c\u0001\uffff\u0001$\u0010\uffff\u0001%\u0001&\u001e\uffff\u0001\u000e\u0002\uffff\u0001\u0001\u0004\uffff\u0001\u0006\u0005\uffff\u0001\f\u0006\uffff\u0001\u0005\u0001\u0007\u0002\uffff\u0001\n\u0001\uffff\u0001\r\u00012\u0001\uffff\u0001\u0002\u0001\uffff\u0001\u0004\u0001\b\u0001\uffff\u0001\u000b\u00013\u0001\u0003\u0001\uffff\u0001\t");
      DFA25_special = DFA.unpackEncodedString("\u0099\uffff}>");
      numStates = DFA25_transitionS.length;
      DFA25_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA25_transition[i] = DFA.unpackEncodedString(DFA25_transitionS[i]);
      }

   }

   protected class DFA25 extends DFA {
      public DFA25(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 25;
         this.eot = ANTLRLexer.DFA25_eot;
         this.eof = ANTLRLexer.DFA25_eof;
         this.min = ANTLRLexer.DFA25_min;
         this.max = ANTLRLexer.DFA25_max;
         this.accept = ANTLRLexer.DFA25_accept;
         this.special = ANTLRLexer.DFA25_special;
         this.transition = ANTLRLexer.DFA25_transition;
      }

      public String getDescription() {
         return "1:1: Tokens : ( CATCH | FINALLY | FRAGMENT | GRAMMAR | IMPORT | LEXER | PARSER | PRIVATE | PROTECTED | PUBLIC | RETURNS | SCOPE | THROWS | TREE | WS | COMMENT | OPEN_ELEMENT_OPTION | CLOSE_ELEMENT_OPTION | AMPERSAND | COMMA | QUESTION | TREE_BEGIN | LPAREN | RPAREN | COLON | STAR | PLUS | ASSIGN | PLUS_ASSIGN | IMPLIES | REWRITE | SEMI | ROOT | BANG | OR | WILDCARD | ETC | RANGE | NOT | RCURLY | DOLLAR | STRAY_BRACKET | CHAR_LITERAL | DOUBLE_QUOTE_STRING_LITERAL | DOUBLE_ANGLE_STRING_LITERAL | INT | ARG_ACTION | ACTION | TOKEN_REF | TOKENS | OPTIONS | RULE_REF );";
      }
   }

   protected class DFA9 extends DFA {
      public DFA9(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 9;
         this.eot = ANTLRLexer.DFA9_eot;
         this.eof = ANTLRLexer.DFA9_eof;
         this.min = ANTLRLexer.DFA9_min;
         this.max = ANTLRLexer.DFA9_max;
         this.accept = ANTLRLexer.DFA9_accept;
         this.special = ANTLRLexer.DFA9_special;
         this.transition = ANTLRLexer.DFA9_transition;
      }

      public String getDescription() {
         return "1101:3: ( ( ' $ANTLR' )=> ' $ANTLR ' SRC ( ( '\\r' )? '\\n' )? | (~ ( '\\r' | '\\n' ) )* ( ( '\\r' )? '\\n' )? )";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         boolean sx;
         switch (s) {
            case 0:
               int LA9_23 = _input.LA(1);
               int index9_23 = _input.index();
               _input.rewind();
               sx = true;
               if (LA9_23 == 34 && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 25;
               } else if (LA9_23 == 92 && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 26;
               } else if ((LA9_23 >= 0 && LA9_23 <= 33 || LA9_23 >= 35 && LA9_23 <= 91 || LA9_23 >= 93 && LA9_23 <= 65535) && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 27;
               } else {
                  s = 2;
               }

               _input.seek(index9_23);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA9_19 = _input.LA(1);
               sx = true;
               if (LA9_19 == 34) {
                  s = 17;
               } else if (LA9_19 == 92) {
                  s = 15;
               } else if (LA9_19 == 13) {
                  s = 16;
               } else if (LA9_19 == 10) {
                  s = 18;
               } else if ((LA9_19 < 0 || LA9_19 > 9) && (LA9_19 < 11 || LA9_19 > 12) && (LA9_19 < 14 || LA9_19 > 33) && (LA9_19 < 35 || LA9_19 > 91) && (LA9_19 < 93 || LA9_19 > 65535)) {
                  s = 2;
               } else {
                  s = 19;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA9_29 = _input.LA(1);
               int index9_29 = _input.index();
               _input.rewind();
               sx = true;
               if (ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 27;
               } else {
                  s = 2;
               }

               _input.seek(index9_29);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA9_18 = _input.LA(1);
               int index9_18 = _input.index();
               _input.rewind();
               sx = true;
               if (LA9_18 == 34 && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 25;
               } else if (LA9_18 == 92 && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 26;
               } else if ((LA9_18 >= 0 && LA9_18 <= 33 || LA9_18 >= 35 && LA9_18 <= 91 || LA9_18 >= 93 && LA9_18 <= 65535) && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 27;
               } else {
                  s = 2;
               }

               _input.seek(index9_18);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA9_22 = _input.LA(1);
               int index9_22 = _input.index();
               _input.rewind();
               s = -1;
               if (LA9_22 == 34 && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 25;
               } else if (LA9_22 == 92 && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 26;
               } else if (LA9_22 == 10) {
                  s = 18;
               } else if ((LA9_22 >= 0 && LA9_22 <= 9 || LA9_22 >= 11 && LA9_22 <= 33 || LA9_22 >= 35 && LA9_22 <= 91 || LA9_22 >= 93 && LA9_22 <= 65535) && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 27;
               }

               _input.seek(index9_22);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA9_24 = _input.LA(1);
               sx = true;
               if (LA9_24 == 34) {
                  s = 17;
               } else if (LA9_24 == 92) {
                  s = 15;
               } else if (LA9_24 == 13) {
                  s = 16;
               } else if (LA9_24 == 10) {
                  s = 18;
               } else if ((LA9_24 < 0 || LA9_24 > 9) && (LA9_24 < 11 || LA9_24 > 12) && (LA9_24 < 14 || LA9_24 > 33) && (LA9_24 < 35 || LA9_24 > 91) && (LA9_24 < 93 || LA9_24 > 65535)) {
                  s = 2;
               } else {
                  s = 19;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA9_21 = _input.LA(1);
               sx = true;
               if (LA9_21 == 34) {
                  s = 17;
               } else if (LA9_21 == 92) {
                  s = 15;
               } else if (LA9_21 == 13) {
                  s = 16;
               } else if (LA9_21 == 10) {
                  s = 18;
               } else if ((LA9_21 < 0 || LA9_21 > 9) && (LA9_21 < 11 || LA9_21 > 12) && (LA9_21 < 14 || LA9_21 > 33) && (LA9_21 < 35 || LA9_21 > 91) && (LA9_21 < 93 || LA9_21 > 65535)) {
                  s = 2;
               } else {
                  s = 19;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA9_14 = _input.LA(1);
               sx = true;
               if (LA9_14 == 92) {
                  s = 15;
               } else if (LA9_14 == 13) {
                  s = 16;
               } else if (LA9_14 == 34) {
                  s = 17;
               } else if (LA9_14 == 10) {
                  s = 18;
               } else if ((LA9_14 < 0 || LA9_14 > 9) && (LA9_14 < 11 || LA9_14 > 12) && (LA9_14 < 14 || LA9_14 > 33) && (LA9_14 < 35 || LA9_14 > 91) && (LA9_14 < 93 || LA9_14 > 65535)) {
                  s = 2;
               } else {
                  s = 19;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA9_20 = _input.LA(1);
               sx = true;
               if (LA9_20 == 34) {
                  s = 17;
               } else if (LA9_20 == 92) {
                  s = 15;
               } else if (LA9_20 == 13) {
                  s = 16;
               } else if (LA9_20 == 10) {
                  s = 18;
               } else if ((LA9_20 < 0 || LA9_20 > 9) && (LA9_20 < 11 || LA9_20 > 12) && (LA9_20 < 14 || LA9_20 > 33) && (LA9_20 < 35 || LA9_20 > 91) && (LA9_20 < 93 || LA9_20 > 65535)) {
                  s = 2;
               } else {
                  s = 19;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA9_15 = _input.LA(1);
               sx = true;
               if (LA9_15 == 39) {
                  s = 20;
               } else if (LA9_15 == 34) {
                  s = 21;
               } else if (LA9_15 == 13) {
                  s = 22;
               } else if (LA9_15 == 10) {
                  s = 23;
               } else if ((LA9_15 < 0 || LA9_15 > 9) && (LA9_15 < 11 || LA9_15 > 12) && (LA9_15 < 14 || LA9_15 > 33) && (LA9_15 < 35 || LA9_15 > 38) && (LA9_15 < 40 || LA9_15 > 65535)) {
                  s = 2;
               } else {
                  s = 24;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA9_16 = _input.LA(1);
               int index9_16 = _input.index();
               _input.rewind();
               s = -1;
               if (LA9_16 == 34 && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 25;
               } else if (LA9_16 == 92 && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 26;
               } else if (LA9_16 == 10) {
                  s = 18;
               } else if ((LA9_16 >= 0 && LA9_16 <= 9 || LA9_16 >= 11 && LA9_16 <= 33 || LA9_16 >= 35 && LA9_16 <= 91 || LA9_16 >= 93 && LA9_16 <= 65535) && ANTLRLexer.this.synpred1_ANTLR()) {
                  s = 27;
               }

               _input.seek(index9_16);
               if (s >= 0) {
                  return s;
               }
         }

         if (ANTLRLexer.this.state.backtracking > 0) {
            ANTLRLexer.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 9, s, _input);
            this.error(nvae);
            throw nvae;
         }
      }
   }
}
