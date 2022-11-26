package org.python.antlr;

import org.python.antlr.runtime.BaseRecognizer;
import org.python.antlr.runtime.BitSet;
import org.python.antlr.runtime.CharStream;
import org.python.antlr.runtime.CommonToken;
import org.python.antlr.runtime.DFA;
import org.python.antlr.runtime.EarlyExitException;
import org.python.antlr.runtime.FailedPredicateException;
import org.python.antlr.runtime.IntStream;
import org.python.antlr.runtime.Lexer;
import org.python.antlr.runtime.MismatchedSetException;
import org.python.antlr.runtime.NoViableAltException;
import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.RecognizerSharedState;
import org.python.antlr.runtime.Token;

public class PythonLexer extends Lexer {
   public static final int PRINT = 11;
   public static final int VBAREQUAL = 57;
   public static final int MINUS = 76;
   public static final int TRAILBACKSLASH = 6;
   public static final int SLASHEQUAL = 54;
   public static final int BREAK = 15;
   public static final int IF = 27;
   public static final int LESSEQUAL = 68;
   public static final int ELIF = 20;
   public static final int IN = 29;
   public static final int DOT = 10;
   public static final int LPAREN = 43;
   public static final int IS = 30;
   public static final int AS = 13;
   public static final int AT = 42;
   public static final int PASS = 35;
   public static final int LBRACK = 81;
   public static final int LEADING_WS = 8;
   public static final int LONGINT = 87;
   public static final int SEMI = 50;
   public static final int ASSIGN = 46;
   public static final int CIRCUMFLEXEQUAL = 58;
   public static final int DOUBLESTAREQUAL = 61;
   public static final int COMMENT = 96;
   public static final int INDENT = 4;
   public static final int IMPORT = 28;
   public static final int DELETE = 19;
   public static final int ESC = 95;
   public static final int ALT_NOTEQUAL = 69;
   public static final int RCURLY = 84;
   public static final int COMMA = 47;
   public static final int TRIQUOTE = 94;
   public static final int YIELD = 41;
   public static final int STAREQUAL = 53;
   public static final int GREATER = 65;
   public static final int LCURLY = 83;
   public static final int DOUBLESLASHEQUAL = 62;
   public static final int RAISE = 36;
   public static final int CONTINUE = 17;
   public static final int LEFTSHIFTEQUAL = 59;
   public static final int STAR = 48;
   public static final int PERCENT = 78;
   public static final int STRING = 90;
   public static final int BACKQUOTE = 85;
   public static final int CLASS = 16;
   public static final int FROM = 24;
   public static final int FINALLY = 23;
   public static final int RIGHTSHIFTEQUAL = 60;
   public static final int TRY = 38;
   public static final int NEWLINE = 7;
   public static final int FOR = 25;
   public static final int RPAREN = 44;
   public static final int EXCEPT = 21;
   public static final int RIGHTSHIFT = 63;
   public static final int NAME = 9;
   public static final int LAMBDA = 31;
   public static final int NOTEQUAL = 70;
   public static final int EXEC = 22;
   public static final int NOT = 32;
   public static final int RBRACK = 82;
   public static final int AND = 12;
   public static final int PERCENTEQUAL = 55;
   public static final int LESS = 64;
   public static final int LEFTSHIFT = 74;
   public static final int PLUS = 75;
   public static final int DOUBLESTAR = 49;
   public static final int FLOAT = 88;
   public static final int TRIAPOS = 93;
   public static final int Exponent = 92;
   public static final int DIGITS = 91;
   public static final int INT = 86;
   public static final int DOUBLESLASH = 79;
   public static final int RETURN = 37;
   public static final int GLOBAL = 26;
   public static final int CONTINUED_LINE = 97;
   public static final int WS = 98;
   public static final int EOF = -1;
   public static final int CIRCUMFLEX = 72;
   public static final int COMPLEX = 89;
   public static final int OR = 33;
   public static final int DEF = 18;
   public static final int ASSERT = 14;
   public static final int AMPEREQUAL = 56;
   public static final int EQUAL = 66;
   public static final int SLASH = 77;
   public static final int AMPER = 73;
   public static final int COLON = 45;
   public static final int ORELSE = 34;
   public static final int WITH = 40;
   public static final int VBAR = 71;
   public static final int PLUSEQUAL = 51;
   public static final int MINUSEQUAL = 52;
   public static final int GREATEREQUAL = 67;
   public static final int WHILE = 39;
   public static final int TILDE = 80;
   public static final int DEDENT = 5;
   int implicitLineJoiningLevel;
   int startPos;
   public boolean eofWhileNested;
   public boolean partial;
   public boolean single;
   private ErrorHandler errorHandler;
   protected DFA5 dfa5;
   protected DFA14 dfa14;
   protected DFA17 dfa17;
   protected DFA45 dfa45;
   protected DFA46 dfa46;
   static final String DFA5_eotS = "\u0003\uffff\u0001\u0004\u0002\uffff";
   static final String DFA5_eofS = "\u0006\uffff";
   static final String DFA5_minS = "\u0001.\u0001\uffff\u0001.\u0001E\u0002\uffff";
   static final String DFA5_maxS = "\u00019\u0001\uffff\u0002e\u0002\uffff";
   static final String DFA5_acceptS = "\u0001\uffff\u0001\u0001\u0002\uffff\u0001\u0003\u0001\u0002";
   static final String DFA5_specialS = "\u0006\uffff}>";
   static final String[] DFA5_transitionS = new String[]{"\u0001\u0001\u0001\uffff\n\u0002", "", "\u0001\u0003\u0001\uffff\n\u0002\u000b\uffff\u0001\u0004\u001f\uffff\u0001\u0004", "\u0001\u0005\u001f\uffff\u0001\u0005", "", ""};
   static final short[] DFA5_eot = DFA.unpackEncodedString("\u0003\uffff\u0001\u0004\u0002\uffff");
   static final short[] DFA5_eof = DFA.unpackEncodedString("\u0006\uffff");
   static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars("\u0001.\u0001\uffff\u0001.\u0001E\u0002\uffff");
   static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars("\u00019\u0001\uffff\u0002e\u0002\uffff");
   static final short[] DFA5_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0002\uffff\u0001\u0003\u0001\u0002");
   static final short[] DFA5_special = DFA.unpackEncodedString("\u0006\uffff}>");
   static final short[][] DFA5_transition;
   static final String DFA14_eotS = "\u0004\uffff";
   static final String DFA14_eofS = "\u0004\uffff";
   static final String DFA14_minS = "\u0002.\u0002\uffff";
   static final String DFA14_maxS = "\u00019\u0001j\u0002\uffff";
   static final String DFA14_acceptS = "\u0002\uffff\u0001\u0002\u0001\u0001";
   static final String DFA14_specialS = "\u0004\uffff}>";
   static final String[] DFA14_transitionS;
   static final short[] DFA14_eot;
   static final short[] DFA14_eof;
   static final char[] DFA14_min;
   static final char[] DFA14_max;
   static final short[] DFA14_accept;
   static final short[] DFA14_special;
   static final short[][] DFA14_transition;
   static final String DFA17_eotS = "\u0014\uffff";
   static final String DFA17_eofS = "\u0014\uffff";
   static final String DFA17_minS = "\u0001\"\u0001\uffff\u0002\"\u0001\uffff\u0002\"\r\uffff";
   static final String DFA17_maxS = "\u0001u\u0001\uffff\u0002r\u0001\uffff\u0002r\r\uffff";
   static final String DFA17_acceptS = "\u0001\uffff\u0001\u0001\u0002\uffff\u0001\u0006\u0002\uffff\u0001\u000f\u0001\u0004\u0001\u000b\u0001\u0002\u0001\u0005\u0001\u000e\u0001\u0003\u0001\t\u0001\f\u0001\u0007\u0001\n\u0001\r\u0001\b";
   static final String DFA17_specialS = "\u0014\uffff}>";
   static final String[] DFA17_transitionS;
   static final short[] DFA17_eot;
   static final short[] DFA17_eof;
   static final char[] DFA17_min;
   static final char[] DFA17_max;
   static final short[] DFA17_accept;
   static final short[] DFA17_special;
   static final short[][] DFA17_transition;
   static final String DFA45_eotS = "\u0002\uffff\u0002\u0004\u0001\uffff";
   static final String DFA45_eofS = "\u0005\uffff";
   static final String DFA45_minS = "\u0001\t\u0001\uffff\u0002\u0000\u0001\uffff";
   static final String DFA45_maxS = "\u0001#\u0001\uffff\u0002\uffff\u0001\uffff";
   static final String DFA45_acceptS = "\u0001\uffff\u0001\u0001\u0002\uffff\u0001\u0002";
   static final String DFA45_specialS = "\u0001\u0000\u0001\uffff\u0001\u0002\u0001\u0001\u0001\uffff}>";
   static final String[] DFA45_transitionS;
   static final short[] DFA45_eot;
   static final short[] DFA45_eof;
   static final char[] DFA45_min;
   static final char[] DFA45_max;
   static final short[] DFA45_accept;
   static final short[] DFA45_special;
   static final short[][] DFA45_transition;
   static final String DFA46_eotS = "\u0001\uffff\u000e0\u0007\uffff\u0001S\u0001U\u0001X\u0001[\u0001]\u0001_\u0001c\u0001f\u0001h\u0001j\u0003\uffff\u0001l\u0002\uffff\u0001n\u0001\uffff\u00020\u0002u\u00040\u0003\uffff\u0001\u0082\u0001\uffff\u0001\u0084\u0001\u0086\u0001\uffff\u0001\u0088\f0\u0001\u0097\u00010\u0001\u0099\u0001\u009a\t0\u0004\uffff\u0001¥\u0002\uffff\u0001§\b\uffff\u0001©\u0002\uffff\u0001«\u0007\uffff\u0001¬\u0001\uffff\u0001®\u00010\u0001\uffff\u0003u\u0002\uffff\u0001¬\u0003\uffff\u0001u\u00060\u0005\uffff\u00010\u0001\uffff\u0001¸\u00030\u0001¼\u0001½\u00060\u0001Ä\u00010\u0001\uffff\u00010\u0002\uffff\u00050\u0001Ì\u00030\u000b\uffff\u0001Ò\u0003u\u0001¬\u0002\uffff\u0001¬\u00010\u0001\uffff\u00030\u0002\uffff\u0001Ú\u0001Û\u00010\u0001Ý\u00010\u0001ß\u0001\uffff\u00030\u0001ã\u00030\u0001\uffff\u00010\u0001è\u00010\u0001\uffff\u0001¬\u0003\uffff\u0001¬\u00010\u0001í\u0001î\u00010\u0002\uffff\u00010\u0001\uffff\u00010\u0001\uffff\u00030\u0001\uffff\u0001õ\u0001ö\u00010\u0001ø\u0001\uffff\u0001ù\u0001\uffff\u0001¬\u0001ú\u0002\uffff\u00010\u0001ü\u00010\u0001þ\u0001ÿ\u0001Ā\u0002\uffff\u0001ā\u0003\uffff\u00010\u0001\uffff\u0001ă\u0004\uffff\u0001Ą\u0002\uffff";
   static final String DFA46_eofS = "ą\uffff";
   static final String DFA46_minS = "\u0001\t\u0001n\u0001\"\u0001l\u0001e\u0001l\u0001i\u0001l\u0001f\u0002a\u0001\"\u0001r\u0001h\u0001i\u0007\uffff\u0002=\u0001*\u0001/\u0002=\u0001<\u0003=\u0003\uffff\u0001=\u0002\uffff\u00010\u0001\uffff\u0001r\u0001o\u0002.\u0004\"\u0003\uffff\u0001\n\u0001\uffff\u0002\t\u0001\uffff\u00010\u0001d\u0002\"\u0001a\u0001n\u0001f\u0001i\u0001c\u0001n\u0001o\u0001r\u0001o\u00010\u0001p\u00020\u0001m\u0001s\u0002i\u0001t\u0001y\u0001i\u0001t\u0001e\u0004\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u0001=\u0002\uffff\u0001=\u0007\uffff\u00010\u0001\uffff\u00010\u0001t\u00030\u0001.\u0002\uffff\u00010\u0001.\u0001+\u0001\uffff\u0001.\u0006\"\u0002\uffff\u0001\u0000\u0001\uffff\u0001\u0000\u0001e\u0001\uffff\u00010\u0001a\u0001s\u0001t\u00020\u0001f\u0002e\u0001c\u0001a\u0001m\u00010\u0001b\u0001\uffff\u0001o\u0002\uffff\u0001b\u0001s\u0001n\u0001s\u0001u\u00010\u0001l\u0001h\u0001l\t\uffff\u0001+\u0001\uffff\u00050\u0001+\u00020\u0001r\u0001\uffff\u0001k\u0001s\u0001i\u0002\uffff\u00020\u0001p\u00010\u0001l\u00010\u0001\uffff\u0001a\u0001r\u0001d\u00010\u0001t\u0001e\u0001r\u0001\uffff\u0001e\u00010\u0001d\u00020\u0001\uffff\u0001+\u00020\u0001t\u00020\u0001n\u0002\uffff\u0001t\u0001\uffff\u0001l\u0001\uffff\u0001l\u0001t\u0001a\u0001\uffff\u00020\u0001n\u00010\u0001\uffff\u00040\u0002\uffff\u0001u\u00010\u0001y\u00030\u0002\uffff\u00010\u0003\uffff\u0001e\u0001\uffff\u00010\u0004\uffff\u00010\u0002\uffff";
   static final String DFA46_maxS = "\u0001~\u0001s\u0001r\u0001o\u0001e\u0001x\u0001r\u0001l\u0001s\u0001a\u0001r\u0001e\u0001r\u0002i\u0007\uffff\u0006=\u0002>\u0002=\u0003\uffff\u0001=\u0002\uffff\u00019\u0001\uffff\u0001r\u0001o\u0001x\u0001l\u0001r\u0001'\u0002r\u0003\uffff\u0001\r\u0001\uffff\u0002#\u0001\uffff\u0001z\u0001d\u0001e\u0001'\u0001a\u0001n\u0001l\u0001s\u0001e\u0001n\u0001o\u0001r\u0001o\u0001z\u0001p\u0002z\u0001m\u0001s\u0002i\u0001t\u0001y\u0001i\u0001t\u0001e\u0004\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u0001=\u0002\uffff\u0001=\u0007\uffff\u0001j\u0001\uffff\u0001z\u0001t\u0001f\u0003l\u0002\uffff\u0002j\u00019\u0001\uffff\u0001l\u0006'\u0002\uffff\u0001\u0000\u0001\uffff\u0001\u0000\u0001e\u0001\uffff\u0001z\u0001a\u0001s\u0001t\u0002z\u0001f\u0002e\u0001c\u0001a\u0001m\u0001z\u0001b\u0001\uffff\u0001o\u0002\uffff\u0001b\u0001s\u0001n\u0001s\u0001u\u0001z\u0001l\u0001h\u0001l\t\uffff\u00019\u0001\uffff\u0001z\u0003l\u0001j\u00029\u0001j\u0001r\u0001\uffff\u0001k\u0001s\u0001i\u0002\uffff\u0002z\u0001p\u0001z\u0001l\u0001z\u0001\uffff\u0001a\u0001r\u0001d\u0001z\u0001t\u0001e\u0001r\u0001\uffff\u0001e\u0001z\u0001d\u00019\u0001j\u0001\uffff\u00029\u0001j\u0001t\u0002z\u0001n\u0002\uffff\u0001t\u0001\uffff\u0001l\u0001\uffff\u0001l\u0001t\u0001a\u0001\uffff\u0002z\u0001n\u0001z\u0001\uffff\u0001z\u00019\u0001j\u0001z\u0002\uffff\u0001u\u0001z\u0001y\u0003z\u0002\uffff\u0001z\u0003\uffff\u0001e\u0001\uffff\u0001z\u0004\uffff\u0001z\u0002\uffff";
   static final String DFA46_acceptS = "\u000f\uffff\u0001\u001d\u0001\u001e\u0001\u001f\u0001 \u0001!\u0001\"\u0001#\n\uffff\u0001.\u0001/\u00010\u0001\uffff\u00012\u00014\u0001\uffff\u0001I\b\uffff\u0001Q\u0001R\u0001S\u0001\uffff\u0001T\u0002\uffff\u0001W\u001a\uffff\u0001:\u0001$\u0001;\u0001%\u0001\uffff\u0001=\u0001&\u0001\uffff\u0001?\u0001'\u0001@\u0001(\u0001B\u0001)\u00015\u00016\u0001\uffff\u0001*\u00018\u0001\uffff\u0001+\u00013\u0001,\u0001A\u0001-\u0001C\u00011\u0001\uffff\u0001H\u0006\uffff\u0001O\u0001N\u0003\uffff\u0001P\u0007\uffff\u0001U\u0001V\u0001\uffff\u0001W\u0002\uffff\u0001\u0001\u000e\uffff\u0001\u000f\u0001\uffff\u0001\u0011\u0001\u0012\t\uffff\u0001F\u0001<\u0001G\u0001>\u0001D\u00017\u0001E\u00019\u0001M\u0001\uffff\u0001K\t\uffff\u0001J\u0003\uffff\u0001\u0006\u0001\u0007\u0006\uffff\u0001\r\u0007\uffff\u0001\u0019\u0005\uffff\u0001L\u0007\uffff\u0001\b\u0001\u0014\u0001\uffff\u0001\n\u0001\uffff\u0001\f\u0003\uffff\u0001\u0015\u0004\uffff\u0001\u001b\u0004\uffff\u0001\u0003\u0001\u0004\u0006\uffff\u0001\u0016\u0001\u0017\u0001\uffff\u0001\u001a\u0001\u001c\u0001\u0002\u0001\uffff\u0001\t\u0001\uffff\u0001\u000e\u0001\u0010\u0001\u0013\u0001\u0018\u0001\uffff\u0001\u000b\u0001\u0005";
   static final String DFA46_specialS = "\u0001\u00012\uffff\u0001\u0000\u0001\uffff\u0001\u0004\u0001\u0005M\uffff\u0001\u0002\u0001\uffff\u0001\u0003~\uffff}>";
   static final String[] DFA46_transitionS;
   static final short[] DFA46_eot;
   static final short[] DFA46_eof;
   static final char[] DFA46_min;
   static final char[] DFA46_max;
   static final short[] DFA46_accept;
   static final short[] DFA46_special;
   static final short[][] DFA46_transition;

   public void setErrorHandler(ErrorHandler eh) {
      this.errorHandler = eh;
   }

   public Token nextToken() {
      this.startPos = this.getCharPositionInLine();

      while(true) {
         this.state.token = null;
         this.state.channel = 0;
         this.state.tokenStartCharIndex = this.input.index();
         this.state.tokenStartCharPositionInLine = this.input.getCharPositionInLine();
         this.state.tokenStartLine = this.input.getLine();
         this.state.text = null;
         if (this.input.LA(1) == -1) {
            if (this.implicitLineJoiningLevel > 0) {
               this.eofWhileNested = true;
            }

            return Token.EOF_TOKEN;
         }

         try {
            this.mTokens();
            if (this.state.token == null) {
               this.emit();
            } else if (this.state.token == Token.SKIP_TOKEN) {
               continue;
            }

            return this.state.token;
         } catch (NoViableAltException var2) {
            this.reportError(var2);
            this.errorHandler.recover(this, var2);
         } catch (FailedPredicateException var3) {
            this.reportError(var3);
            this.errorHandler.recover(this, var3);
         } catch (RecognitionException var4) {
            this.reportError(var4);
         }
      }
   }

   public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
   }

   public PythonLexer() {
      this.implicitLineJoiningLevel = 0;
      this.startPos = -1;
      this.eofWhileNested = false;
      this.partial = false;
      this.single = false;
      this.dfa5 = new DFA5(this);
      this.dfa14 = new DFA14(this);
      this.dfa17 = new DFA17(this);
      this.dfa45 = new DFA45(this);
      this.dfa46 = new DFA46(this);
   }

   public PythonLexer(CharStream input) {
      this(input, new RecognizerSharedState());
   }

   public PythonLexer(CharStream input, RecognizerSharedState state) {
      super(input, state);
      this.implicitLineJoiningLevel = 0;
      this.startPos = -1;
      this.eofWhileNested = false;
      this.partial = false;
      this.single = false;
      this.dfa5 = new DFA5(this);
      this.dfa14 = new DFA14(this);
      this.dfa17 = new DFA17(this);
      this.dfa45 = new DFA45(this);
      this.dfa46 = new DFA46(this);
   }

   public String getGrammarFileName() {
      return "/Users/fwierzbicki/hg/jython/jython/grammar/Python.g";
   }

   public final void mAS() throws RecognitionException {
      int _type = 13;
      int _channel = 0;
      this.match("as");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mASSERT() throws RecognitionException {
      int _type = 14;
      int _channel = 0;
      this.match("assert");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mBREAK() throws RecognitionException {
      int _type = 15;
      int _channel = 0;
      this.match("break");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mCLASS() throws RecognitionException {
      int _type = 16;
      int _channel = 0;
      this.match("class");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mCONTINUE() throws RecognitionException {
      int _type = 17;
      int _channel = 0;
      this.match("continue");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mDEF() throws RecognitionException {
      int _type = 18;
      int _channel = 0;
      this.match("def");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mDELETE() throws RecognitionException {
      int _type = 19;
      int _channel = 0;
      this.match("del");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mELIF() throws RecognitionException {
      int _type = 20;
      int _channel = 0;
      this.match("elif");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mEXCEPT() throws RecognitionException {
      int _type = 21;
      int _channel = 0;
      this.match("except");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mEXEC() throws RecognitionException {
      int _type = 22;
      int _channel = 0;
      this.match("exec");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mFINALLY() throws RecognitionException {
      int _type = 23;
      int _channel = 0;
      this.match("finally");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mFROM() throws RecognitionException {
      int _type = 24;
      int _channel = 0;
      this.match("from");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mFOR() throws RecognitionException {
      int _type = 25;
      int _channel = 0;
      this.match("for");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mGLOBAL() throws RecognitionException {
      int _type = 26;
      int _channel = 0;
      this.match("global");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mIF() throws RecognitionException {
      int _type = 27;
      int _channel = 0;
      this.match("if");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mIMPORT() throws RecognitionException {
      int _type = 28;
      int _channel = 0;
      this.match("import");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mIN() throws RecognitionException {
      int _type = 29;
      int _channel = 0;
      this.match("in");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mIS() throws RecognitionException {
      int _type = 30;
      int _channel = 0;
      this.match("is");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mLAMBDA() throws RecognitionException {
      int _type = 31;
      int _channel = 0;
      this.match("lambda");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mORELSE() throws RecognitionException {
      int _type = 34;
      int _channel = 0;
      this.match("else");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mPASS() throws RecognitionException {
      int _type = 35;
      int _channel = 0;
      this.match("pass");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mPRINT() throws RecognitionException {
      int _type = 11;
      int _channel = 0;
      this.match("print");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mRAISE() throws RecognitionException {
      int _type = 36;
      int _channel = 0;
      this.match("raise");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mRETURN() throws RecognitionException {
      int _type = 37;
      int _channel = 0;
      this.match("return");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mTRY() throws RecognitionException {
      int _type = 38;
      int _channel = 0;
      this.match("try");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mWHILE() throws RecognitionException {
      int _type = 39;
      int _channel = 0;
      this.match("while");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mWITH() throws RecognitionException {
      int _type = 40;
      int _channel = 0;
      this.match("with");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mYIELD() throws RecognitionException {
      int _type = 41;
      int _channel = 0;
      this.match("yield");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mLPAREN() throws RecognitionException {
      int _type = 43;
      int _channel = 0;
      this.match(40);
      ++this.implicitLineJoiningLevel;
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mRPAREN() throws RecognitionException {
      int _type = 44;
      int _channel = 0;
      this.match(41);
      --this.implicitLineJoiningLevel;
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mLBRACK() throws RecognitionException {
      int _type = 81;
      int _channel = 0;
      this.match(91);
      ++this.implicitLineJoiningLevel;
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mRBRACK() throws RecognitionException {
      int _type = 82;
      int _channel = 0;
      this.match(93);
      --this.implicitLineJoiningLevel;
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mCOLON() throws RecognitionException {
      int _type = 45;
      int _channel = 0;
      this.match(58);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mCOMMA() throws RecognitionException {
      int _type = 47;
      int _channel = 0;
      this.match(44);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mSEMI() throws RecognitionException {
      int _type = 50;
      int _channel = 0;
      this.match(59);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mPLUS() throws RecognitionException {
      int _type = 75;
      int _channel = 0;
      this.match(43);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mMINUS() throws RecognitionException {
      int _type = 76;
      int _channel = 0;
      this.match(45);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mSTAR() throws RecognitionException {
      int _type = 48;
      int _channel = 0;
      this.match(42);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mSLASH() throws RecognitionException {
      int _type = 77;
      int _channel = 0;
      this.match(47);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mVBAR() throws RecognitionException {
      int _type = 71;
      int _channel = 0;
      this.match(124);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mAMPER() throws RecognitionException {
      int _type = 73;
      int _channel = 0;
      this.match(38);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mLESS() throws RecognitionException {
      int _type = 64;
      int _channel = 0;
      this.match(60);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mGREATER() throws RecognitionException {
      int _type = 65;
      int _channel = 0;
      this.match(62);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mASSIGN() throws RecognitionException {
      int _type = 46;
      int _channel = 0;
      this.match(61);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mPERCENT() throws RecognitionException {
      int _type = 78;
      int _channel = 0;
      this.match(37);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mBACKQUOTE() throws RecognitionException {
      int _type = 85;
      int _channel = 0;
      this.match(96);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mLCURLY() throws RecognitionException {
      int _type = 83;
      int _channel = 0;
      this.match(123);
      ++this.implicitLineJoiningLevel;
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mRCURLY() throws RecognitionException {
      int _type = 84;
      int _channel = 0;
      this.match(125);
      --this.implicitLineJoiningLevel;
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mCIRCUMFLEX() throws RecognitionException {
      int _type = 72;
      int _channel = 0;
      this.match(94);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mTILDE() throws RecognitionException {
      int _type = 80;
      int _channel = 0;
      this.match(126);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mEQUAL() throws RecognitionException {
      int _type = 66;
      int _channel = 0;
      this.match("==");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mNOTEQUAL() throws RecognitionException {
      int _type = 70;
      int _channel = 0;
      this.match("!=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mALT_NOTEQUAL() throws RecognitionException {
      int _type = 69;
      int _channel = 0;
      this.match("<>");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mLESSEQUAL() throws RecognitionException {
      int _type = 68;
      int _channel = 0;
      this.match("<=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mLEFTSHIFT() throws RecognitionException {
      int _type = 74;
      int _channel = 0;
      this.match("<<");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mGREATEREQUAL() throws RecognitionException {
      int _type = 67;
      int _channel = 0;
      this.match(">=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mRIGHTSHIFT() throws RecognitionException {
      int _type = 63;
      int _channel = 0;
      this.match(">>");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mPLUSEQUAL() throws RecognitionException {
      int _type = 51;
      int _channel = 0;
      this.match("+=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mMINUSEQUAL() throws RecognitionException {
      int _type = 52;
      int _channel = 0;
      this.match("-=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mDOUBLESTAR() throws RecognitionException {
      int _type = 49;
      int _channel = 0;
      this.match("**");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mSTAREQUAL() throws RecognitionException {
      int _type = 53;
      int _channel = 0;
      this.match("*=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mDOUBLESLASH() throws RecognitionException {
      int _type = 79;
      int _channel = 0;
      this.match("//");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mSLASHEQUAL() throws RecognitionException {
      int _type = 54;
      int _channel = 0;
      this.match("/=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mVBAREQUAL() throws RecognitionException {
      int _type = 57;
      int _channel = 0;
      this.match("|=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mPERCENTEQUAL() throws RecognitionException {
      int _type = 55;
      int _channel = 0;
      this.match("%=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mAMPEREQUAL() throws RecognitionException {
      int _type = 56;
      int _channel = 0;
      this.match("&=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mCIRCUMFLEXEQUAL() throws RecognitionException {
      int _type = 58;
      int _channel = 0;
      this.match("^=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mLEFTSHIFTEQUAL() throws RecognitionException {
      int _type = 59;
      int _channel = 0;
      this.match("<<=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mRIGHTSHIFTEQUAL() throws RecognitionException {
      int _type = 60;
      int _channel = 0;
      this.match(">>=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mDOUBLESTAREQUAL() throws RecognitionException {
      int _type = 61;
      int _channel = 0;
      this.match("**=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mDOUBLESLASHEQUAL() throws RecognitionException {
      int _type = 62;
      int _channel = 0;
      this.match("//=");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mDOT() throws RecognitionException {
      int _type = 10;
      int _channel = 0;
      this.match(46);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mAT() throws RecognitionException {
      int _type = 42;
      int _channel = 0;
      this.match(64);
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mAND() throws RecognitionException {
      int _type = 12;
      int _channel = 0;
      this.match("and");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mOR() throws RecognitionException {
      int _type = 33;
      int _channel = 0;
      this.match("or");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mNOT() throws RecognitionException {
      int _type = 32;
      int _channel = 0;
      this.match("not");
      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mFLOAT() throws RecognitionException {
      byte _type;
      byte _channel;
      _type = 88;
      _channel = 0;
      int alt5 = true;
      int alt5 = this.dfa5.predict(this.input);
      int LA4_0;
      byte alt4;
      label61:
      switch (alt5) {
         case 1:
            this.match(46);
            this.mDIGITS();
            alt4 = 2;
            LA4_0 = this.input.LA(1);
            if (LA4_0 == 69 || LA4_0 == 101) {
               alt4 = 1;
            }

            switch (alt4) {
               case 1:
                  this.mExponent();
               default:
                  break label61;
            }
         case 2:
            this.mDIGITS();
            this.match(46);
            this.mExponent();
            break;
         case 3:
            this.mDIGITS();
            int alt4 = true;
            LA4_0 = this.input.LA(1);
            if (LA4_0 == 46) {
               alt4 = 1;
            } else {
               if (LA4_0 != 69 && LA4_0 != 101) {
                  NoViableAltException nvae = new NoViableAltException("", 4, 0, this.input);
                  throw nvae;
               }

               alt4 = 2;
            }

            label57:
            switch (alt4) {
               case 1:
                  this.match(46);
                  int alt3 = 2;
                  int LA3_0 = this.input.LA(1);
                  if (LA3_0 >= 48 && LA3_0 <= 57) {
                     alt3 = 1;
                  }

                  switch (alt3) {
                     case 1:
                        this.mDIGITS();
                        int alt2 = 2;
                        int LA2_0 = this.input.LA(1);
                        if (LA2_0 == 69 || LA2_0 == 101) {
                           alt2 = 1;
                        }

                        switch (alt2) {
                           case 1:
                              this.mExponent();
                        }
                     default:
                        break label57;
                  }
               case 2:
                  this.mExponent();
            }
      }

      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mLONGINT() throws RecognitionException {
      int _type = 87;
      int _channel = 0;
      this.mINT();
      if (this.input.LA(1) != 76 && this.input.LA(1) != 108) {
         MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
         this.recover(mse);
         throw mse;
      } else {
         this.input.consume();
         this.state.type = _type;
         this.state.channel = _channel;
      }
   }

   public final void mExponent() throws RecognitionException {
      if (this.input.LA(1) != 69 && this.input.LA(1) != 101) {
         MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
         this.recover(mse);
         throw mse;
      } else {
         this.input.consume();
         byte alt6 = 2;
         int LA6_0 = this.input.LA(1);
         if (LA6_0 == 43 || LA6_0 == 45) {
            alt6 = 1;
         }

         switch (alt6) {
            case 1:
               if (this.input.LA(1) != 43 && this.input.LA(1) != 45) {
                  MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                  this.recover(mse);
                  throw mse;
               } else {
                  this.input.consume();
               }
            default:
               this.mDIGITS();
         }
      }
   }

   public final void mINT() throws RecognitionException {
      int _type = 86;
      int _channel = 0;
      int alt12 = true;
      int LA12_0 = this.input.LA(1);
      byte alt12;
      if (LA12_0 == 48) {
         switch (this.input.LA(2)) {
            case 66:
            case 98:
               alt12 = 4;
               break;
            case 79:
            case 111:
               alt12 = 2;
               break;
            case 88:
            case 120:
               alt12 = 1;
               break;
            default:
               alt12 = 3;
         }
      } else {
         if (LA12_0 < 49 || LA12_0 > 57) {
            NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
            throw nvae;
         }

         alt12 = 5;
      }

      int LA10_0;
      int cnt7;
      MismatchedSetException mse;
      label136:
      switch (alt12) {
         case 1:
            this.match(48);
            if (this.input.LA(1) != 88 && this.input.LA(1) != 120) {
               mse = new MismatchedSetException((BitSet)null, this.input);
               this.recover(mse);
               throw mse;
            }

            this.input.consume();
            cnt7 = 0;

            while(true) {
               int alt7 = 2;
               int LA7_0 = this.input.LA(1);
               if (LA7_0 >= 48 && LA7_0 <= 57 || LA7_0 >= 65 && LA7_0 <= 70 || LA7_0 >= 97 && LA7_0 <= 102) {
                  alt7 = 1;
               }

               switch (alt7) {
                  case 1:
                     if ((this.input.LA(1) < 48 || this.input.LA(1) > 57) && (this.input.LA(1) < 65 || this.input.LA(1) > 70) && (this.input.LA(1) < 97 || this.input.LA(1) > 102)) {
                        MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     ++cnt7;
                     break;
                  default:
                     if (cnt7 < 1) {
                        EarlyExitException eee = new EarlyExitException(7, this.input);
                        throw eee;
                     }
                     break label136;
               }
            }
         case 2:
            this.match(48);
            if (this.input.LA(1) != 79 && this.input.LA(1) != 111) {
               mse = new MismatchedSetException((BitSet)null, this.input);
               this.recover(mse);
               throw mse;
            }

            this.input.consume();

            while(true) {
               cnt7 = 2;
               LA10_0 = this.input.LA(1);
               if (LA10_0 >= 48 && LA10_0 <= 55) {
                  cnt7 = 1;
               }

               switch (cnt7) {
                  case 1:
                     this.matchRange(48, 55);
                     break;
                  default:
                     break label136;
               }
            }
         case 3:
            this.match(48);

            while(true) {
               cnt7 = 2;
               LA10_0 = this.input.LA(1);
               if (LA10_0 >= 48 && LA10_0 <= 55) {
                  cnt7 = 1;
               }

               switch (cnt7) {
                  case 1:
                     this.matchRange(48, 55);
                     break;
                  default:
                     break label136;
               }
            }
         case 4:
            this.match(48);
            if (this.input.LA(1) != 66 && this.input.LA(1) != 98) {
               mse = new MismatchedSetException((BitSet)null, this.input);
               this.recover(mse);
               throw mse;
            }

            this.input.consume();

            while(true) {
               cnt7 = 2;
               LA10_0 = this.input.LA(1);
               if (LA10_0 >= 48 && LA10_0 <= 49) {
                  cnt7 = 1;
               }

               switch (cnt7) {
                  case 1:
                     this.matchRange(48, 49);
                     break;
                  default:
                     break label136;
               }
            }
         case 5:
            this.matchRange(49, 57);

            label104:
            while(true) {
               cnt7 = 2;
               LA10_0 = this.input.LA(1);
               if (LA10_0 >= 48 && LA10_0 <= 57) {
                  cnt7 = 1;
               }

               switch (cnt7) {
                  case 1:
                     this.mDIGITS();
                     break;
                  default:
                     break label104;
               }
            }
      }

      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mCOMPLEX() throws RecognitionException {
      byte _type;
      byte _channel;
      _type = 89;
      _channel = 0;
      int alt14 = true;
      int alt14 = this.dfa14.predict(this.input);
      label43:
      switch (alt14) {
         case 1:
            int cnt13 = 0;

            while(true) {
               int alt13 = 2;
               int LA13_0 = this.input.LA(1);
               if (LA13_0 >= 48 && LA13_0 <= 57) {
                  alt13 = 1;
               }

               switch (alt13) {
                  case 1:
                     this.mDIGITS();
                     ++cnt13;
                     break;
                  default:
                     if (cnt13 < 1) {
                        EarlyExitException eee = new EarlyExitException(13, this.input);
                        throw eee;
                     }

                     if (this.input.LA(1) != 74 && this.input.LA(1) != 106) {
                        MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     break label43;
               }
            }
         case 2:
            this.mFLOAT();
            if (this.input.LA(1) != 74 && this.input.LA(1) != 106) {
               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               this.recover(mse);
               throw mse;
            }

            this.input.consume();
      }

      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mDIGITS() throws RecognitionException {
      int cnt15 = 0;

      while(true) {
         int alt15 = 2;
         int LA15_0 = this.input.LA(1);
         if (LA15_0 >= 48 && LA15_0 <= 57) {
            alt15 = 1;
         }

         switch (alt15) {
            case 1:
               this.matchRange(48, 57);
               ++cnt15;
               break;
            default:
               if (cnt15 >= 1) {
                  return;
               }

               EarlyExitException eee = new EarlyExitException(15, this.input);
               throw eee;
         }
      }
   }

   public final void mNAME() throws RecognitionException {
      int _type = 9;
      int _channel = 0;
      if ((this.input.LA(1) < 65 || this.input.LA(1) > 90) && this.input.LA(1) != 95 && (this.input.LA(1) < 97 || this.input.LA(1) > 122)) {
         MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
         this.recover(mse);
         throw mse;
      } else {
         this.input.consume();

         while(true) {
            int alt16 = 2;
            int LA16_0 = this.input.LA(1);
            if (LA16_0 >= 48 && LA16_0 <= 57 || LA16_0 >= 65 && LA16_0 <= 90 || LA16_0 == 95 || LA16_0 >= 97 && LA16_0 <= 122) {
               alt16 = 1;
            }

            switch (alt16) {
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
      }
   }

   public final void mSTRING() throws RecognitionException {
      int _type = 90;
      int _channel = 0;
      int alt17 = true;
      int alt17 = this.dfa17.predict(this.input);
      switch (alt17) {
         case 1:
            this.match(114);
            break;
         case 2:
            this.match(117);
            break;
         case 3:
            this.match(98);
            break;
         case 4:
            this.match("ur");
            break;
         case 5:
            this.match("br");
            break;
         case 6:
            this.match(82);
            break;
         case 7:
            this.match(85);
            break;
         case 8:
            this.match(66);
            break;
         case 9:
            this.match("UR");
            break;
         case 10:
            this.match("BR");
            break;
         case 11:
            this.match("uR");
            break;
         case 12:
            this.match("Ur");
            break;
         case 13:
            this.match("Br");
            break;
         case 14:
            this.match("bR");
      }

      int alt22 = true;
      int LA22_0 = this.input.LA(1);
      int LA22_2;
      NoViableAltException nvae;
      byte alt22;
      int LA21_0;
      if (LA22_0 == 39) {
         LA22_2 = this.input.LA(2);
         if (LA22_2 == 39) {
            LA21_0 = this.input.LA(3);
            if (LA21_0 == 39) {
               alt22 = 1;
            } else {
               alt22 = 4;
            }
         } else {
            if ((LA22_2 < 0 || LA22_2 > 9) && (LA22_2 < 11 || LA22_2 > 38) && (LA22_2 < 40 || LA22_2 > 65535)) {
               nvae = new NoViableAltException("", 22, 1, this.input);
               throw nvae;
            }

            alt22 = 4;
         }
      } else {
         if (LA22_0 != 34) {
            NoViableAltException nvae = new NoViableAltException("", 22, 0, this.input);
            throw nvae;
         }

         LA22_2 = this.input.LA(2);
         if (LA22_2 == 34) {
            LA21_0 = this.input.LA(3);
            if (LA21_0 == 34) {
               alt22 = 2;
            } else {
               alt22 = 3;
            }
         } else {
            if ((LA22_2 < 0 || LA22_2 > 9) && (LA22_2 < 11 || LA22_2 > 33) && (LA22_2 < 35 || LA22_2 > 65535)) {
               nvae = new NoViableAltException("", 22, 2, this.input);
               throw nvae;
            }

            alt22 = 3;
         }
      }

      MismatchedSetException mse;
      int LA19_3;
      byte alt19;
      int LA19_1;
      label291:
      switch (alt22) {
         case 1:
            this.match("'''");

            while(true) {
               alt19 = 2;
               LA21_0 = this.input.LA(1);
               if (LA21_0 == 39) {
                  LA19_1 = this.input.LA(2);
                  if (LA19_1 == 39) {
                     LA19_3 = this.input.LA(3);
                     if (LA19_3 == 39) {
                        alt19 = 2;
                     } else if (LA19_3 >= 0 && LA19_3 <= 38 || LA19_3 >= 40 && LA19_3 <= 65535) {
                        alt19 = 1;
                     }
                  } else if (LA19_1 >= 0 && LA19_1 <= 38 || LA19_1 >= 40 && LA19_1 <= 65535) {
                     alt19 = 1;
                  }
               } else if (LA21_0 >= 0 && LA21_0 <= 38 || LA21_0 >= 40 && LA21_0 <= 65535) {
                  alt19 = 1;
               }

               switch (alt19) {
                  case 1:
                     this.mTRIAPOS();
                     break;
                  default:
                     this.match("'''");
                     break label291;
               }
            }
         case 2:
            this.match("\"\"\"");

            while(true) {
               alt19 = 2;
               LA21_0 = this.input.LA(1);
               if (LA21_0 == 34) {
                  LA19_1 = this.input.LA(2);
                  if (LA19_1 == 34) {
                     LA19_3 = this.input.LA(3);
                     if (LA19_3 == 34) {
                        alt19 = 2;
                     } else if (LA19_3 >= 0 && LA19_3 <= 33 || LA19_3 >= 35 && LA19_3 <= 65535) {
                        alt19 = 1;
                     }
                  } else if (LA19_1 >= 0 && LA19_1 <= 33 || LA19_1 >= 35 && LA19_1 <= 65535) {
                     alt19 = 1;
                  }
               } else if (LA21_0 >= 0 && LA21_0 <= 33 || LA21_0 >= 35 && LA21_0 <= 65535) {
                  alt19 = 1;
               }

               switch (alt19) {
                  case 1:
                     this.mTRIQUOTE();
                     break;
                  default:
                     this.match("\"\"\"");
                     break label291;
               }
            }
         case 3:
            this.match(34);

            while(true) {
               alt19 = 3;
               LA21_0 = this.input.LA(1);
               if (LA21_0 == 92) {
                  alt19 = 1;
               } else if (LA21_0 >= 0 && LA21_0 <= 9 || LA21_0 >= 11 && LA21_0 <= 33 || LA21_0 >= 35 && LA21_0 <= 91 || LA21_0 >= 93 && LA21_0 <= 65535) {
                  alt19 = 2;
               }

               switch (alt19) {
                  case 1:
                     this.mESC();
                     break;
                  case 2:
                     if ((this.input.LA(1) < 0 || this.input.LA(1) > 9) && (this.input.LA(1) < 11 || this.input.LA(1) > 33) && (this.input.LA(1) < 35 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 65535)) {
                        mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     break;
                  default:
                     this.match(34);
                     break label291;
               }
            }
         case 4:
            this.match(39);

            label289:
            while(true) {
               alt19 = 3;
               LA21_0 = this.input.LA(1);
               if (LA21_0 == 92) {
                  alt19 = 1;
               } else if (LA21_0 >= 0 && LA21_0 <= 9 || LA21_0 >= 11 && LA21_0 <= 38 || LA21_0 >= 40 && LA21_0 <= 91 || LA21_0 >= 93 && LA21_0 <= 65535) {
                  alt19 = 2;
               }

               switch (alt19) {
                  case 1:
                     this.mESC();
                     break;
                  case 2:
                     if ((this.input.LA(1) < 0 || this.input.LA(1) > 9) && (this.input.LA(1) < 11 || this.input.LA(1) > 38) && (this.input.LA(1) < 40 || this.input.LA(1) > 91) && (this.input.LA(1) < 93 || this.input.LA(1) > 65535)) {
                        mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     break;
                  default:
                     this.match(39);
                     break label289;
               }
            }
      }

      if (this.state.tokenStartLine != this.input.getLine()) {
         this.state.tokenStartLine = this.input.getLine();
         this.state.tokenStartCharPositionInLine = -2;
      }

      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mTRIQUOTE() throws RecognitionException {
      int alt23 = 2;
      int LA23_0 = this.input.LA(1);
      if (LA23_0 == 34) {
         alt23 = 1;
      }

      switch (alt23) {
         case 1:
            this.match(34);
         default:
            int alt24 = 2;
            int LA24_0 = this.input.LA(1);
            if (LA24_0 == 34) {
               alt24 = 1;
            }

            switch (alt24) {
               case 1:
                  this.match(34);
               default:
                  int cnt25 = 0;

                  while(true) {
                     int alt25 = 3;
                     int LA25_0 = this.input.LA(1);
                     if (LA25_0 == 92) {
                        alt25 = 1;
                     } else if (LA25_0 >= 0 && LA25_0 <= 33 || LA25_0 >= 35 && LA25_0 <= 91 || LA25_0 >= 93 && LA25_0 <= 65535) {
                        alt25 = 2;
                     }

                     switch (alt25) {
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
                           if (cnt25 >= 1) {
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(25, this.input);
                           throw eee;
                     }

                     ++cnt25;
                  }
            }
      }
   }

   public final void mTRIAPOS() throws RecognitionException {
      int alt26 = 2;
      int LA26_0 = this.input.LA(1);
      if (LA26_0 == 39) {
         alt26 = 1;
      }

      switch (alt26) {
         case 1:
            this.match(39);
         default:
            int alt27 = 2;
            int LA27_0 = this.input.LA(1);
            if (LA27_0 == 39) {
               alt27 = 1;
            }

            switch (alt27) {
               case 1:
                  this.match(39);
               default:
                  int cnt28 = 0;

                  while(true) {
                     int alt28 = 3;
                     int LA28_0 = this.input.LA(1);
                     if (LA28_0 == 92) {
                        alt28 = 1;
                     } else if (LA28_0 >= 0 && LA28_0 <= 38 || LA28_0 >= 40 && LA28_0 <= 91 || LA28_0 >= 93 && LA28_0 <= 65535) {
                        alt28 = 2;
                     }

                     switch (alt28) {
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
                           break;
                        default:
                           if (cnt28 >= 1) {
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(28, this.input);
                           throw eee;
                     }

                     ++cnt28;
                  }
            }
      }
   }

   public final void mESC() throws RecognitionException {
      this.match(92);
      this.matchAny();
   }

   public final void mCONTINUED_LINE() throws RecognitionException {
      int _type = 97;
      int _channel = false;
      Token nl = null;
      this.match(92);
      int alt29 = 2;
      int LA29_0 = this.input.LA(1);
      if (LA29_0 == 13) {
         alt29 = 1;
      }

      switch (alt29) {
         case 1:
            this.match(13);
         default:
            this.match(10);

            while(true) {
               int alt31 = 2;
               int LA31_0 = this.input.LA(1);
               if (LA31_0 == 9 || LA31_0 == 32) {
                  alt31 = 1;
               }

               switch (alt31) {
                  case 1:
                     if (this.input.LA(1) != 9 && this.input.LA(1) != 32) {
                        MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     break;
                  default:
                     int _channel = 99;
                     int alt31 = true;
                     LA31_0 = this.input.LA(1);
                     if ((LA31_0 == 9 || LA31_0 == 32) && this.startPos == 0) {
                        alt31 = 1;
                     } else if (LA31_0 == 35) {
                        alt31 = 1;
                     } else if (LA31_0 != 10 && (LA31_0 < 12 || LA31_0 > 13)) {
                        alt31 = 3;
                     } else {
                        alt31 = 2;
                     }

                     switch (alt31) {
                        case 1:
                           this.mCOMMENT();
                           break;
                        case 2:
                           int nlStart1805 = this.getCharIndex();
                           this.mNEWLINE();
                           nl = new CommonToken(this.input, 0, 0, nlStart1805, this.getCharIndex() - 1);
                           this.emit(new CommonToken(7, nl.getText()));
                        case 3:
                     }

                     if (this.input.LA(1) == -1) {
                        throw new ParseException("unexpected character after line continuation character");
                     }

                     this.state.type = _type;
                     this.state.channel = _channel;
                     return;
               }
            }
      }
   }

   public final void mNEWLINE() throws RecognitionException {
      int _type = 7;
      int _channel = 0;
      int newlines = 0;
      int cnt34 = 0;

      while(true) {
         int alt34 = 2;
         int LA34_0 = this.input.LA(1);
         if (LA34_0 == 10 || LA34_0 >= 12 && LA34_0 <= 13) {
            alt34 = 1;
         }

         switch (alt34) {
            case 1:
               int alt32 = 2;
               int LA32_0 = this.input.LA(1);
               if (LA32_0 == 12) {
                  alt32 = 1;
               }

               switch (alt32) {
                  case 1:
                     this.match(12);
                  default:
                     int alt33 = 2;
                     int LA33_0 = this.input.LA(1);
                     if (LA33_0 == 13) {
                        alt33 = 1;
                     }

                     switch (alt33) {
                        case 1:
                           this.match(13);
                        default:
                           this.match(10);
                           ++newlines;
                           ++cnt34;
                           continue;
                     }
               }
            default:
               if (cnt34 < 1) {
                  EarlyExitException eee = new EarlyExitException(34, this.input);
                  throw eee;
               }

               if (this.startPos == 0 || this.implicitLineJoiningLevel > 0) {
                  _channel = 99;
               }

               this.state.type = _type;
               this.state.channel = _channel;
               return;
         }
      }
   }

   public final void mWS() throws RecognitionException {
      int _type = 98;
      int _channel = false;
      if (this.startPos <= 0) {
         throw new FailedPredicateException(this.input, "WS", "startPos>0");
      } else {
         int cnt35 = 0;

         while(true) {
            int alt35 = 2;
            int LA35_0 = this.input.LA(1);
            if (LA35_0 == 9 || LA35_0 == 12 || LA35_0 == 32) {
               alt35 = 1;
            }

            switch (alt35) {
               case 1:
                  if (this.input.LA(1) != 9 && this.input.LA(1) != 12 && this.input.LA(1) != 32) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  ++cnt35;
                  break;
               default:
                  if (cnt35 >= 1) {
                     int _channel = 99;
                     this.state.type = _type;
                     this.state.channel = _channel;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(35, this.input);
                  throw eee;
            }
         }
      }
   }

   public final void mLEADING_WS() throws RecognitionException {
      int _type = 8;
      int _channel = 0;
      int spaces = 0;
      int newlines = 0;
      if (this.startPos != 0) {
         throw new FailedPredicateException(this.input, "LEADING_WS", "startPos==0");
      } else {
         int alt40 = true;
         int LA40_0 = this.input.LA(1);
         int cnt36;
         byte alt40;
         if (LA40_0 == 32) {
            cnt36 = this.input.LA(2);
            if (this.implicitLineJoiningLevel > 0) {
               alt40 = 1;
            } else {
               alt40 = 2;
            }
         } else {
            if (LA40_0 != 9) {
               NoViableAltException nvae = new NoViableAltException("", 40, 0, this.input);
               throw nvae;
            }

            cnt36 = this.input.LA(2);
            if (this.implicitLineJoiningLevel > 0) {
               alt40 = 1;
            } else {
               alt40 = 2;
            }
         }

         byte alt39;
         int i;
         EarlyExitException eee;
         label147:
         switch (alt40) {
            case 1:
               if (this.implicitLineJoiningLevel <= 0) {
                  throw new FailedPredicateException(this.input, "LEADING_WS", "implicitLineJoiningLevel>0");
               }

               cnt36 = 0;

               while(true) {
                  alt39 = 2;
                  i = this.input.LA(1);
                  if (i == 9 || i == 32) {
                     alt39 = 1;
                  }

                  switch (alt39) {
                     case 1:
                        if (this.input.LA(1) != 9 && this.input.LA(1) != 32) {
                           MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                           this.recover(mse);
                           throw mse;
                        }

                        this.input.consume();
                        ++cnt36;
                        break;
                     default:
                        if (cnt36 < 1) {
                           eee = new EarlyExitException(36, this.input);
                           throw eee;
                        }

                        _channel = 99;
                        break label147;
                  }
               }
            case 2:
               cnt36 = 0;

               label144:
               while(true) {
                  alt39 = 3;
                  i = this.input.LA(1);
                  if (i == 32) {
                     alt39 = 1;
                  } else if (i == 9) {
                     alt39 = 2;
                  }

                  switch (alt39) {
                     case 1:
                        this.match(32);
                        ++spaces;
                        break;
                     case 2:
                        this.match(9);
                        spaces += 8;
                        spaces -= spaces % 8;
                        break;
                     default:
                        if (cnt36 < 1) {
                           eee = new EarlyExitException(37, this.input);
                           throw eee;
                        }

                        while(true) {
                           alt39 = 2;
                           i = this.input.LA(1);
                           if (i == 10 || i == 13) {
                              alt39 = 1;
                           }

                           switch (alt39) {
                              case 1:
                                 int alt38 = 2;
                                 int LA38_0 = this.input.LA(1);
                                 if (LA38_0 == 13) {
                                    alt38 = 1;
                                 }

                                 switch (alt38) {
                                    case 1:
                                       this.match(13);
                                    default:
                                       this.match(10);
                                       ++newlines;
                                       continue;
                                 }
                              default:
                                 char[] nls;
                                 CommonToken c;
                                 if (this.input.LA(1) == -1 && newlines != 0) {
                                    if (this.single && newlines == 1) {
                                       throw new ParseException("Trailing space in single mode.");
                                    }

                                    nls = new char[newlines];

                                    for(i = 0; i < newlines; ++i) {
                                       nls[i] = '\n';
                                    }

                                    c = new CommonToken(7, new String(nls));
                                    c.setLine(this.input.getLine());
                                    c.setCharPositionInLine(this.input.getCharPositionInLine());
                                    c.setStartIndex(this.input.index() - 1);
                                    c.setStopIndex(this.input.index() - 1);
                                    this.emit(c);
                                 } else {
                                    nls = new char[spaces];

                                    for(i = 0; i < spaces; ++i) {
                                       nls[i] = ' ';
                                    }

                                    c = new CommonToken(8, new String(nls));
                                    c.setLine(this.input.getLine());
                                    c.setCharPositionInLine(this.input.getCharPositionInLine());
                                    c.setStartIndex(this.input.index() - 1);
                                    c.setStopIndex(this.input.index() - 1);
                                    this.emit(c);
                                    if (newlines != 0) {
                                       if (this.state.token != null) {
                                          this.state.token.setChannel(99);
                                       } else {
                                          _channel = 99;
                                       }
                                    }
                                 }
                                 break label144;
                           }
                        }
                  }

                  ++cnt36;
               }
         }

         this.state.type = _type;
         this.state.channel = _channel;
      }
   }

   public final void mCOMMENT() throws RecognitionException {
      byte _type;
      byte _channel;
      _type = 96;
      int _channel = false;
      _channel = 99;
      int alt45 = true;
      int alt45 = this.dfa45.predict(this.input);
      int cnt43;
      int LA42_0;
      MismatchedSetException mse;
      label104:
      switch (alt45) {
         case 1:
            if (this.startPos != 0) {
               throw new FailedPredicateException(this.input, "COMMENT", "startPos==0");
            }

            while(true) {
               cnt43 = 2;
               LA42_0 = this.input.LA(1);
               if (LA42_0 == 9 || LA42_0 == 32) {
                  cnt43 = 1;
               }

               switch (cnt43) {
                  case 1:
                     if (this.input.LA(1) != 9 && this.input.LA(1) != 32) {
                        mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     break;
                  default:
                     this.match(35);

                     while(true) {
                        cnt43 = 2;
                        LA42_0 = this.input.LA(1);
                        if (LA42_0 >= 0 && LA42_0 <= 9 || LA42_0 >= 11 && LA42_0 <= 65535) {
                           cnt43 = 1;
                        }

                        switch (cnt43) {
                           case 1:
                              if ((this.input.LA(1) < 0 || this.input.LA(1) > 9) && (this.input.LA(1) < 11 || this.input.LA(1) > 65535)) {
                                 mse = new MismatchedSetException((BitSet)null, this.input);
                                 this.recover(mse);
                                 throw mse;
                              }

                              this.input.consume();
                              break;
                           default:
                              cnt43 = 0;

                              while(true) {
                                 int alt43 = 2;
                                 int LA43_0 = this.input.LA(1);
                                 if (LA43_0 == 10) {
                                    alt43 = 1;
                                 }

                                 switch (alt43) {
                                    case 1:
                                       this.match(10);
                                       ++cnt43;
                                       break;
                                    default:
                                       if (cnt43 < 1) {
                                          EarlyExitException eee = new EarlyExitException(43, this.input);
                                          throw eee;
                                       }
                                       break label104;
                                 }
                              }
                        }
                     }
               }
            }
         case 2:
            this.match(35);

            label102:
            while(true) {
               cnt43 = 2;
               LA42_0 = this.input.LA(1);
               if (LA42_0 >= 0 && LA42_0 <= 9 || LA42_0 >= 11 && LA42_0 <= 65535) {
                  cnt43 = 1;
               }

               switch (cnt43) {
                  case 1:
                     if ((this.input.LA(1) < 0 || this.input.LA(1) > 9) && (this.input.LA(1) < 11 || this.input.LA(1) > 65535)) {
                        mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     break;
                  default:
                     break label102;
               }
            }
      }

      this.state.type = _type;
      this.state.channel = _channel;
   }

   public void mTokens() throws RecognitionException {
      int alt46 = true;
      int alt46 = this.dfa46.predict(this.input);
      switch (alt46) {
         case 1:
            this.mAS();
            break;
         case 2:
            this.mASSERT();
            break;
         case 3:
            this.mBREAK();
            break;
         case 4:
            this.mCLASS();
            break;
         case 5:
            this.mCONTINUE();
            break;
         case 6:
            this.mDEF();
            break;
         case 7:
            this.mDELETE();
            break;
         case 8:
            this.mELIF();
            break;
         case 9:
            this.mEXCEPT();
            break;
         case 10:
            this.mEXEC();
            break;
         case 11:
            this.mFINALLY();
            break;
         case 12:
            this.mFROM();
            break;
         case 13:
            this.mFOR();
            break;
         case 14:
            this.mGLOBAL();
            break;
         case 15:
            this.mIF();
            break;
         case 16:
            this.mIMPORT();
            break;
         case 17:
            this.mIN();
            break;
         case 18:
            this.mIS();
            break;
         case 19:
            this.mLAMBDA();
            break;
         case 20:
            this.mORELSE();
            break;
         case 21:
            this.mPASS();
            break;
         case 22:
            this.mPRINT();
            break;
         case 23:
            this.mRAISE();
            break;
         case 24:
            this.mRETURN();
            break;
         case 25:
            this.mTRY();
            break;
         case 26:
            this.mWHILE();
            break;
         case 27:
            this.mWITH();
            break;
         case 28:
            this.mYIELD();
            break;
         case 29:
            this.mLPAREN();
            break;
         case 30:
            this.mRPAREN();
            break;
         case 31:
            this.mLBRACK();
            break;
         case 32:
            this.mRBRACK();
            break;
         case 33:
            this.mCOLON();
            break;
         case 34:
            this.mCOMMA();
            break;
         case 35:
            this.mSEMI();
            break;
         case 36:
            this.mPLUS();
            break;
         case 37:
            this.mMINUS();
            break;
         case 38:
            this.mSTAR();
            break;
         case 39:
            this.mSLASH();
            break;
         case 40:
            this.mVBAR();
            break;
         case 41:
            this.mAMPER();
            break;
         case 42:
            this.mLESS();
            break;
         case 43:
            this.mGREATER();
            break;
         case 44:
            this.mASSIGN();
            break;
         case 45:
            this.mPERCENT();
            break;
         case 46:
            this.mBACKQUOTE();
            break;
         case 47:
            this.mLCURLY();
            break;
         case 48:
            this.mRCURLY();
            break;
         case 49:
            this.mCIRCUMFLEX();
            break;
         case 50:
            this.mTILDE();
            break;
         case 51:
            this.mEQUAL();
            break;
         case 52:
            this.mNOTEQUAL();
            break;
         case 53:
            this.mALT_NOTEQUAL();
            break;
         case 54:
            this.mLESSEQUAL();
            break;
         case 55:
            this.mLEFTSHIFT();
            break;
         case 56:
            this.mGREATEREQUAL();
            break;
         case 57:
            this.mRIGHTSHIFT();
            break;
         case 58:
            this.mPLUSEQUAL();
            break;
         case 59:
            this.mMINUSEQUAL();
            break;
         case 60:
            this.mDOUBLESTAR();
            break;
         case 61:
            this.mSTAREQUAL();
            break;
         case 62:
            this.mDOUBLESLASH();
            break;
         case 63:
            this.mSLASHEQUAL();
            break;
         case 64:
            this.mVBAREQUAL();
            break;
         case 65:
            this.mPERCENTEQUAL();
            break;
         case 66:
            this.mAMPEREQUAL();
            break;
         case 67:
            this.mCIRCUMFLEXEQUAL();
            break;
         case 68:
            this.mLEFTSHIFTEQUAL();
            break;
         case 69:
            this.mRIGHTSHIFTEQUAL();
            break;
         case 70:
            this.mDOUBLESTAREQUAL();
            break;
         case 71:
            this.mDOUBLESLASHEQUAL();
            break;
         case 72:
            this.mDOT();
            break;
         case 73:
            this.mAT();
            break;
         case 74:
            this.mAND();
            break;
         case 75:
            this.mOR();
            break;
         case 76:
            this.mNOT();
            break;
         case 77:
            this.mFLOAT();
            break;
         case 78:
            this.mLONGINT();
            break;
         case 79:
            this.mINT();
            break;
         case 80:
            this.mCOMPLEX();
            break;
         case 81:
            this.mNAME();
            break;
         case 82:
            this.mSTRING();
            break;
         case 83:
            this.mCONTINUED_LINE();
            break;
         case 84:
            this.mNEWLINE();
            break;
         case 85:
            this.mWS();
            break;
         case 86:
            this.mLEADING_WS();
            break;
         case 87:
            this.mCOMMENT();
      }

   }

   static {
      int numStates = DFA5_transitionS.length;
      DFA5_transition = new short[numStates][];

      int i;
      for(i = 0; i < numStates; ++i) {
         DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
      }

      DFA14_transitionS = new String[]{"\u0001\u0002\u0001\uffff\n\u0001", "\u0001\u0002\u0001\uffff\n\u0001\u000b\uffff\u0001\u0002\u0004\uffff\u0001\u0003\u001a\uffff\u0001\u0002\u0004\uffff\u0001\u0003", "", ""};
      DFA14_eot = DFA.unpackEncodedString("\u0004\uffff");
      DFA14_eof = DFA.unpackEncodedString("\u0004\uffff");
      DFA14_min = DFA.unpackEncodedStringToUnsignedChars("\u0002.\u0002\uffff");
      DFA14_max = DFA.unpackEncodedStringToUnsignedChars("\u00019\u0001j\u0002\uffff");
      DFA14_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0001\u0001");
      DFA14_special = DFA.unpackEncodedString("\u0004\uffff}>");
      numStates = DFA14_transitionS.length;
      DFA14_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
      }

      DFA17_transitionS = new String[]{"\u0001\u0007\u0004\uffff\u0001\u0007\u001a\uffff\u0001\u0006\u000f\uffff\u0001\u0004\u0002\uffff\u0001\u0005\f\uffff\u0001\u0003\u000f\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "", "\u0001\n\u0004\uffff\u0001\n*\uffff\u0001\t\u001f\uffff\u0001\b", "\u0001\r\u0004\uffff\u0001\r*\uffff\u0001\f\u001f\uffff\u0001\u000b", "", "\u0001\u0010\u0004\uffff\u0001\u0010*\uffff\u0001\u000e\u001f\uffff\u0001\u000f", "\u0001\u0013\u0004\uffff\u0001\u0013*\uffff\u0001\u0011\u001f\uffff\u0001\u0012", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA17_eot = DFA.unpackEncodedString("\u0014\uffff");
      DFA17_eof = DFA.unpackEncodedString("\u0014\uffff");
      DFA17_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\"\u0001\uffff\u0002\"\u0001\uffff\u0002\"\r\uffff");
      DFA17_max = DFA.unpackEncodedStringToUnsignedChars("\u0001u\u0001\uffff\u0002r\u0001\uffff\u0002r\r\uffff");
      DFA17_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0002\uffff\u0001\u0006\u0002\uffff\u0001\u000f\u0001\u0004\u0001\u000b\u0001\u0002\u0001\u0005\u0001\u000e\u0001\u0003\u0001\t\u0001\f\u0001\u0007\u0001\n\u0001\r\u0001\b");
      DFA17_special = DFA.unpackEncodedString("\u0014\uffff}>");
      numStates = DFA17_transitionS.length;
      DFA17_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
      }

      DFA45_transitionS = new String[]{"\u0001\u0001\u0016\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "", "\n\u0003\u0001\u0001\ufff5\u0003", "\n\u0003\u0001\u0001\ufff5\u0003", ""};
      DFA45_eot = DFA.unpackEncodedString("\u0002\uffff\u0002\u0004\u0001\uffff");
      DFA45_eof = DFA.unpackEncodedString("\u0005\uffff");
      DFA45_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0001\uffff\u0002\u0000\u0001\uffff");
      DFA45_max = DFA.unpackEncodedStringToUnsignedChars("\u0001#\u0001\uffff\u0002\uffff\u0001\uffff");
      DFA45_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0002\uffff\u0001\u0002");
      DFA45_special = DFA.unpackEncodedString("\u0001\u0000\u0001\uffff\u0001\u0002\u0001\u0001\u0001\uffff}>");
      numStates = DFA45_transitionS.length;
      DFA45_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA45_transition[i] = DFA.unpackEncodedString(DFA45_transitionS[i]);
      }

      DFA46_transitionS = new String[]{"\u00016\u00014\u0001\uffff\u00013\u00014\u0012\uffff\u00015\u0001%\u00011\u00017\u0001\uffff\u0001\u001f\u0001\u001b\u00011\u0001\u000f\u0001\u0010\u0001\u0018\u0001\u0016\u0001\u0014\u0001\u0017\u0001&\u0001\u0019\u0001*\t+\u0001\u0013\u0001\u0015\u0001\u001c\u0001\u001e\u0001\u001d\u0001\uffff\u0001'\u00010\u0001/\u000f0\u0001-\u00020\u0001.\u00050\u0001\u0011\u00012\u0001\u0012\u0001#\u00010\u0001 \u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u00010\u0001\b\u00020\u0001\t\u00010\u0001)\u0001(\u0001\n\u00010\u0001\u000b\u00010\u0001\f\u0001,\u00010\u0001\r\u00010\u0001\u000e\u00010\u0001!\u0001\u001a\u0001\"\u0001$", "\u00019\u0004\uffff\u00018", "\u00011\u0004\uffff\u00011*\uffff\u0001;\u001f\uffff\u0001:", "\u0001<\u0002\uffff\u0001=", "\u0001>", "\u0001?\u000b\uffff\u0001@", "\u0001A\u0005\uffff\u0001C\u0002\uffff\u0001B", "\u0001D", "\u0001E\u0006\uffff\u0001F\u0001G\u0004\uffff\u0001H", "\u0001I", "\u0001J\u0010\uffff\u0001K", "\u00011\u0004\uffff\u000119\uffff\u0001L\u0003\uffff\u0001M", "\u0001N", "\u0001O\u0001P", "\u0001Q", "", "", "", "", "", "", "", "\u0001R", "\u0001T", "\u0001V\u0012\uffff\u0001W", "\u0001Y\r\uffff\u0001Z", "\u0001\\", "\u0001^", "\u0001b\u0001a\u0001`", "\u0001d\u0001e", "\u0001g", "\u0001i", "", "", "", "\u0001k", "", "", "\nm", "", "\u0001o", "\u0001p", "\u0001w\u0001\uffff\bt\u0002x\b\uffff\u0001s\u0002\uffff\u0001y\u0004\uffff\u0001z\u0001\uffff\u0001v\u0002\uffff\u0001r\b\uffff\u0001q\t\uffff\u0001s\u0002\uffff\u0001y\u0004\uffff\u0001z\u0001\uffff\u0001v\u0002\uffff\u0001r\b\uffff\u0001q", "\u0001w\u0001\uffff\n{\u000b\uffff\u0001y\u0004\uffff\u0001z\u0001\uffff\u0001v\u0018\uffff\u0001y\u0004\uffff\u0001z\u0001\uffff\u0001v", "\u00011\u0004\uffff\u00011*\uffff\u0001}\u001f\uffff\u0001|", "\u00011\u0004\uffff\u00011", "\u00011\u0004\uffff\u00011*\uffff\u0001~\u001f\uffff\u0001\u007f", "\u00011\u0004\uffff\u00011*\uffff\u0001\u0080\u001f\uffff\u0001\u0081", "", "", "", "\u00014\u0002\uffff\u00014", "", "\u00016\u0001\u0083\u0001\uffff\u0001\u0082\u0001\u0083\u0012\uffff\u00015\u0002\uffff\u0001\u0085", "\u00016\u0001\u0083\u0001\uffff\u0001\u0082\u0001\u0083\u0012\uffff\u00015\u0002\uffff\u0001\u0085", "", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u00120\u0001\u0087\u00070", "\u0001\u0089", "\u00011\u0004\uffff\u00011=\uffff\u0001\u008a", "\u00011\u0004\uffff\u00011", "\u0001\u008b", "\u0001\u008c", "\u0001\u008d\u0005\uffff\u0001\u008e", "\u0001\u008f\t\uffff\u0001\u0090", "\u0001\u0091\u0001\uffff\u0001\u0092", "\u0001\u0093", "\u0001\u0094", "\u0001\u0095", "\u0001\u0096", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001\u0098", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001\u009b", "\u0001\u009c", "\u0001\u009d", "\u0001\u009e", "\u0001\u009f", "\u0001 ", "\u0001¡", "\u0001¢", "\u0001£", "", "", "", "", "\u0001¤", "", "", "\u0001¦", "", "", "", "", "", "", "", "", "\u0001¨", "", "", "\u0001ª", "", "", "", "", "", "", "", "\nm\u000b\uffff\u0001\u00ad\u0004\uffff\u0001z\u001a\uffff\u0001\u00ad\u0004\uffff\u0001z", "", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001¯", "\n°\u0007\uffff\u0006°\u001a\uffff\u0006°", "\b±\u0014\uffff\u0001v\u001f\uffff\u0001v", "\u0002²\u001a\uffff\u0001v\u001f\uffff\u0001v", "\u0001w\u0001\uffff\bt\u0002x\u000b\uffff\u0001y\u0004\uffff\u0001z\u0001\uffff\u0001v\u0018\uffff\u0001y\u0004\uffff\u0001z\u0001\uffff\u0001v", "", "", "\n³\u000b\uffff\u0001´\u0004\uffff\u0001z\u001a\uffff\u0001´\u0004\uffff\u0001z", "\u0001w\u0001\uffff\nx\u000b\uffff\u0001y\u0004\uffff\u0001z\u001a\uffff\u0001y\u0004\uffff\u0001z", "\u0001µ\u0001\uffff\u0001µ\u0002\uffff\n¶", "", "\u0001w\u0001\uffff\n{\u000b\uffff\u0001y\u0004\uffff\u0001z\u0001\uffff\u0001v\u0018\uffff\u0001y\u0004\uffff\u0001z\u0001\uffff\u0001v", "\u00011\u0004\uffff\u00011", "\u00011\u0004\uffff\u00011", "\u00011\u0004\uffff\u00011", "\u00011\u0004\uffff\u00011", "\u00011\u0004\uffff\u00011", "\u00011\u0004\uffff\u00011", "", "", "\u0001\uffff", "", "\u0001\uffff", "\u0001·", "", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001¹", "\u0001º", "\u0001»", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001¾", "\u0001¿", "\u0001À", "\u0001Á", "\u0001Â", "\u0001Ã", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001Å", "", "\u0001Æ", "", "", "\u0001Ç", "\u0001È", "\u0001É", "\u0001Ê", "\u0001Ë", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001Í", "\u0001Î", "\u0001Ï", "", "", "", "", "", "", "", "", "", "\u0001Ð\u0001\uffff\u0001Ð\u0002\uffff\nÑ", "", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\n°\u0007\uffff\u0006°\u0005\uffff\u0001v\u0014\uffff\u0006°\u0005\uffff\u0001v", "\b±\u0014\uffff\u0001v\u001f\uffff\u0001v", "\u0002²\u001a\uffff\u0001v\u001f\uffff\u0001v", "\n³\u000b\uffff\u0001Ó\u0004\uffff\u0001z\u001a\uffff\u0001Ó\u0004\uffff\u0001z", "\u0001Ô\u0001\uffff\u0001Ô\u0002\uffff\nÕ", "\n¶", "\n¶\u0010\uffff\u0001z\u001f\uffff\u0001z", "\u0001Ö", "", "\u0001×", "\u0001Ø", "\u0001Ù", "", "", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001Ü", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001Þ", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "", "\u0001à", "\u0001á", "\u0001â", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001ä", "\u0001å", "\u0001æ", "", "\u0001ç", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001é", "\nÑ", "\nÑ\u0010\uffff\u0001z\u001f\uffff\u0001z", "", "\u0001ê\u0001\uffff\u0001ê\u0002\uffff\në", "\nÕ", "\nÕ\u0010\uffff\u0001z\u001f\uffff\u0001z", "\u0001ì", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001ï", "", "", "\u0001ð", "", "\u0001ñ", "", "\u0001ò", "\u0001ó", "\u0001ô", "", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001÷", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\në", "\në\u0010\uffff\u0001z\u001f\uffff\u0001z", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "", "", "\u0001û", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\u0001ý", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "", "", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "", "", "", "\u0001Ă", "", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "", "", "", "", "\n0\u0007\uffff\u001a0\u0004\uffff\u00010\u0001\uffff\u001a0", "", ""};
      DFA46_eot = DFA.unpackEncodedString("\u0001\uffff\u000e0\u0007\uffff\u0001S\u0001U\u0001X\u0001[\u0001]\u0001_\u0001c\u0001f\u0001h\u0001j\u0003\uffff\u0001l\u0002\uffff\u0001n\u0001\uffff\u00020\u0002u\u00040\u0003\uffff\u0001\u0082\u0001\uffff\u0001\u0084\u0001\u0086\u0001\uffff\u0001\u0088\f0\u0001\u0097\u00010\u0001\u0099\u0001\u009a\t0\u0004\uffff\u0001¥\u0002\uffff\u0001§\b\uffff\u0001©\u0002\uffff\u0001«\u0007\uffff\u0001¬\u0001\uffff\u0001®\u00010\u0001\uffff\u0003u\u0002\uffff\u0001¬\u0003\uffff\u0001u\u00060\u0005\uffff\u00010\u0001\uffff\u0001¸\u00030\u0001¼\u0001½\u00060\u0001Ä\u00010\u0001\uffff\u00010\u0002\uffff\u00050\u0001Ì\u00030\u000b\uffff\u0001Ò\u0003u\u0001¬\u0002\uffff\u0001¬\u00010\u0001\uffff\u00030\u0002\uffff\u0001Ú\u0001Û\u00010\u0001Ý\u00010\u0001ß\u0001\uffff\u00030\u0001ã\u00030\u0001\uffff\u00010\u0001è\u00010\u0001\uffff\u0001¬\u0003\uffff\u0001¬\u00010\u0001í\u0001î\u00010\u0002\uffff\u00010\u0001\uffff\u00010\u0001\uffff\u00030\u0001\uffff\u0001õ\u0001ö\u00010\u0001ø\u0001\uffff\u0001ù\u0001\uffff\u0001¬\u0001ú\u0002\uffff\u00010\u0001ü\u00010\u0001þ\u0001ÿ\u0001Ā\u0002\uffff\u0001ā\u0003\uffff\u00010\u0001\uffff\u0001ă\u0004\uffff\u0001Ą\u0002\uffff");
      DFA46_eof = DFA.unpackEncodedString("ą\uffff");
      DFA46_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0001n\u0001\"\u0001l\u0001e\u0001l\u0001i\u0001l\u0001f\u0002a\u0001\"\u0001r\u0001h\u0001i\u0007\uffff\u0002=\u0001*\u0001/\u0002=\u0001<\u0003=\u0003\uffff\u0001=\u0002\uffff\u00010\u0001\uffff\u0001r\u0001o\u0002.\u0004\"\u0003\uffff\u0001\n\u0001\uffff\u0002\t\u0001\uffff\u00010\u0001d\u0002\"\u0001a\u0001n\u0001f\u0001i\u0001c\u0001n\u0001o\u0001r\u0001o\u00010\u0001p\u00020\u0001m\u0001s\u0002i\u0001t\u0001y\u0001i\u0001t\u0001e\u0004\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u0001=\u0002\uffff\u0001=\u0007\uffff\u00010\u0001\uffff\u00010\u0001t\u00030\u0001.\u0002\uffff\u00010\u0001.\u0001+\u0001\uffff\u0001.\u0006\"\u0002\uffff\u0001\u0000\u0001\uffff\u0001\u0000\u0001e\u0001\uffff\u00010\u0001a\u0001s\u0001t\u00020\u0001f\u0002e\u0001c\u0001a\u0001m\u00010\u0001b\u0001\uffff\u0001o\u0002\uffff\u0001b\u0001s\u0001n\u0001s\u0001u\u00010\u0001l\u0001h\u0001l\t\uffff\u0001+\u0001\uffff\u00050\u0001+\u00020\u0001r\u0001\uffff\u0001k\u0001s\u0001i\u0002\uffff\u00020\u0001p\u00010\u0001l\u00010\u0001\uffff\u0001a\u0001r\u0001d\u00010\u0001t\u0001e\u0001r\u0001\uffff\u0001e\u00010\u0001d\u00020\u0001\uffff\u0001+\u00020\u0001t\u00020\u0001n\u0002\uffff\u0001t\u0001\uffff\u0001l\u0001\uffff\u0001l\u0001t\u0001a\u0001\uffff\u00020\u0001n\u00010\u0001\uffff\u00040\u0002\uffff\u0001u\u00010\u0001y\u00030\u0002\uffff\u00010\u0003\uffff\u0001e\u0001\uffff\u00010\u0004\uffff\u00010\u0002\uffff");
      DFA46_max = DFA.unpackEncodedStringToUnsignedChars("\u0001~\u0001s\u0001r\u0001o\u0001e\u0001x\u0001r\u0001l\u0001s\u0001a\u0001r\u0001e\u0001r\u0002i\u0007\uffff\u0006=\u0002>\u0002=\u0003\uffff\u0001=\u0002\uffff\u00019\u0001\uffff\u0001r\u0001o\u0001x\u0001l\u0001r\u0001'\u0002r\u0003\uffff\u0001\r\u0001\uffff\u0002#\u0001\uffff\u0001z\u0001d\u0001e\u0001'\u0001a\u0001n\u0001l\u0001s\u0001e\u0001n\u0001o\u0001r\u0001o\u0001z\u0001p\u0002z\u0001m\u0001s\u0002i\u0001t\u0001y\u0001i\u0001t\u0001e\u0004\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u0001=\u0002\uffff\u0001=\u0007\uffff\u0001j\u0001\uffff\u0001z\u0001t\u0001f\u0003l\u0002\uffff\u0002j\u00019\u0001\uffff\u0001l\u0006'\u0002\uffff\u0001\u0000\u0001\uffff\u0001\u0000\u0001e\u0001\uffff\u0001z\u0001a\u0001s\u0001t\u0002z\u0001f\u0002e\u0001c\u0001a\u0001m\u0001z\u0001b\u0001\uffff\u0001o\u0002\uffff\u0001b\u0001s\u0001n\u0001s\u0001u\u0001z\u0001l\u0001h\u0001l\t\uffff\u00019\u0001\uffff\u0001z\u0003l\u0001j\u00029\u0001j\u0001r\u0001\uffff\u0001k\u0001s\u0001i\u0002\uffff\u0002z\u0001p\u0001z\u0001l\u0001z\u0001\uffff\u0001a\u0001r\u0001d\u0001z\u0001t\u0001e\u0001r\u0001\uffff\u0001e\u0001z\u0001d\u00019\u0001j\u0001\uffff\u00029\u0001j\u0001t\u0002z\u0001n\u0002\uffff\u0001t\u0001\uffff\u0001l\u0001\uffff\u0001l\u0001t\u0001a\u0001\uffff\u0002z\u0001n\u0001z\u0001\uffff\u0001z\u00019\u0001j\u0001z\u0002\uffff\u0001u\u0001z\u0001y\u0003z\u0002\uffff\u0001z\u0003\uffff\u0001e\u0001\uffff\u0001z\u0004\uffff\u0001z\u0002\uffff");
      DFA46_accept = DFA.unpackEncodedString("\u000f\uffff\u0001\u001d\u0001\u001e\u0001\u001f\u0001 \u0001!\u0001\"\u0001#\n\uffff\u0001.\u0001/\u00010\u0001\uffff\u00012\u00014\u0001\uffff\u0001I\b\uffff\u0001Q\u0001R\u0001S\u0001\uffff\u0001T\u0002\uffff\u0001W\u001a\uffff\u0001:\u0001$\u0001;\u0001%\u0001\uffff\u0001=\u0001&\u0001\uffff\u0001?\u0001'\u0001@\u0001(\u0001B\u0001)\u00015\u00016\u0001\uffff\u0001*\u00018\u0001\uffff\u0001+\u00013\u0001,\u0001A\u0001-\u0001C\u00011\u0001\uffff\u0001H\u0006\uffff\u0001O\u0001N\u0003\uffff\u0001P\u0007\uffff\u0001U\u0001V\u0001\uffff\u0001W\u0002\uffff\u0001\u0001\u000e\uffff\u0001\u000f\u0001\uffff\u0001\u0011\u0001\u0012\t\uffff\u0001F\u0001<\u0001G\u0001>\u0001D\u00017\u0001E\u00019\u0001M\u0001\uffff\u0001K\t\uffff\u0001J\u0003\uffff\u0001\u0006\u0001\u0007\u0006\uffff\u0001\r\u0007\uffff\u0001\u0019\u0005\uffff\u0001L\u0007\uffff\u0001\b\u0001\u0014\u0001\uffff\u0001\n\u0001\uffff\u0001\f\u0003\uffff\u0001\u0015\u0004\uffff\u0001\u001b\u0004\uffff\u0001\u0003\u0001\u0004\u0006\uffff\u0001\u0016\u0001\u0017\u0001\uffff\u0001\u001a\u0001\u001c\u0001\u0002\u0001\uffff\u0001\t\u0001\uffff\u0001\u000e\u0001\u0010\u0001\u0013\u0001\u0018\u0001\uffff\u0001\u000b\u0001\u0005");
      DFA46_special = DFA.unpackEncodedString("\u0001\u00012\uffff\u0001\u0000\u0001\uffff\u0001\u0004\u0001\u0005M\uffff\u0001\u0002\u0001\uffff\u0001\u0003~\uffff}>");
      numStates = DFA46_transitionS.length;
      DFA46_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA46_transition[i] = DFA.unpackEncodedString(DFA46_transitionS[i]);
      }

   }

   class DFA46 extends DFA {
      public DFA46(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 46;
         this.eot = PythonLexer.DFA46_eot;
         this.eof = PythonLexer.DFA46_eof;
         this.min = PythonLexer.DFA46_min;
         this.max = PythonLexer.DFA46_max;
         this.accept = PythonLexer.DFA46_accept;
         this.special = PythonLexer.DFA46_special;
         this.transition = PythonLexer.DFA46_transition;
      }

      public String getDescription() {
         return "1:1: Tokens : ( AS | ASSERT | BREAK | CLASS | CONTINUE | DEF | DELETE | ELIF | EXCEPT | EXEC | FINALLY | FROM | FOR | GLOBAL | IF | IMPORT | IN | IS | LAMBDA | ORELSE | PASS | PRINT | RAISE | RETURN | TRY | WHILE | WITH | YIELD | LPAREN | RPAREN | LBRACK | RBRACK | COLON | COMMA | SEMI | PLUS | MINUS | STAR | SLASH | VBAR | AMPER | LESS | GREATER | ASSIGN | PERCENT | BACKQUOTE | LCURLY | RCURLY | CIRCUMFLEX | TILDE | EQUAL | NOTEQUAL | ALT_NOTEQUAL | LESSEQUAL | LEFTSHIFT | GREATEREQUAL | RIGHTSHIFT | PLUSEQUAL | MINUSEQUAL | DOUBLESTAR | STAREQUAL | DOUBLESLASH | SLASHEQUAL | VBAREQUAL | PERCENTEQUAL | AMPEREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL | DOT | AT | AND | OR | NOT | FLOAT | LONGINT | INT | COMPLEX | NAME | STRING | CONTINUED_LINE | NEWLINE | WS | LEADING_WS | COMMENT );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         boolean sx;
         switch (s) {
            case 0:
               int LA46_51 = _input.LA(1);
               int index46_51 = _input.index();
               _input.rewind();
               sx = true;
               if (LA46_51 != 10 && LA46_51 != 13) {
                  s = 130;
               } else {
                  s = 52;
               }

               _input.seek(index46_51);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA46_0 = _input.LA(1);
               int index46_0 = _input.index();
               _input.rewind();
               s = -1;
               if (LA46_0 == 97) {
                  s = 1;
               } else if (LA46_0 == 98) {
                  s = 2;
               } else if (LA46_0 == 99) {
                  s = 3;
               } else if (LA46_0 == 100) {
                  s = 4;
               } else if (LA46_0 == 101) {
                  s = 5;
               } else if (LA46_0 == 102) {
                  s = 6;
               } else if (LA46_0 == 103) {
                  s = 7;
               } else if (LA46_0 == 105) {
                  s = 8;
               } else if (LA46_0 == 108) {
                  s = 9;
               } else if (LA46_0 == 112) {
                  s = 10;
               } else if (LA46_0 == 114) {
                  s = 11;
               } else if (LA46_0 == 116) {
                  s = 12;
               } else if (LA46_0 == 119) {
                  s = 13;
               } else if (LA46_0 == 121) {
                  s = 14;
               } else if (LA46_0 == 40) {
                  s = 15;
               } else if (LA46_0 == 41) {
                  s = 16;
               } else if (LA46_0 == 91) {
                  s = 17;
               } else if (LA46_0 == 93) {
                  s = 18;
               } else if (LA46_0 == 58) {
                  s = 19;
               } else if (LA46_0 == 44) {
                  s = 20;
               } else if (LA46_0 == 59) {
                  s = 21;
               } else if (LA46_0 == 43) {
                  s = 22;
               } else if (LA46_0 == 45) {
                  s = 23;
               } else if (LA46_0 == 42) {
                  s = 24;
               } else if (LA46_0 == 47) {
                  s = 25;
               } else if (LA46_0 == 124) {
                  s = 26;
               } else if (LA46_0 == 38) {
                  s = 27;
               } else if (LA46_0 == 60) {
                  s = 28;
               } else if (LA46_0 == 62) {
                  s = 29;
               } else if (LA46_0 == 61) {
                  s = 30;
               } else if (LA46_0 == 37) {
                  s = 31;
               } else if (LA46_0 == 96) {
                  s = 32;
               } else if (LA46_0 == 123) {
                  s = 33;
               } else if (LA46_0 == 125) {
                  s = 34;
               } else if (LA46_0 == 94) {
                  s = 35;
               } else if (LA46_0 == 126) {
                  s = 36;
               } else if (LA46_0 == 33) {
                  s = 37;
               } else if (LA46_0 == 46) {
                  s = 38;
               } else if (LA46_0 == 64) {
                  s = 39;
               } else if (LA46_0 == 111) {
                  s = 40;
               } else if (LA46_0 == 110) {
                  s = 41;
               } else if (LA46_0 == 48) {
                  s = 42;
               } else if (LA46_0 >= 49 && LA46_0 <= 57) {
                  s = 43;
               } else if (LA46_0 == 117) {
                  s = 44;
               } else if (LA46_0 == 82) {
                  s = 45;
               } else if (LA46_0 == 85) {
                  s = 46;
               } else if (LA46_0 == 66) {
                  s = 47;
               } else if (LA46_0 != 65 && (LA46_0 < 67 || LA46_0 > 81) && (LA46_0 < 83 || LA46_0 > 84) && (LA46_0 < 86 || LA46_0 > 90) && LA46_0 != 95 && LA46_0 != 104 && (LA46_0 < 106 || LA46_0 > 107) && LA46_0 != 109 && LA46_0 != 113 && LA46_0 != 115 && LA46_0 != 118 && LA46_0 != 120 && LA46_0 != 122) {
                  if (LA46_0 != 34 && LA46_0 != 39) {
                     if (LA46_0 == 92) {
                        s = 50;
                     } else if (LA46_0 == 12) {
                        s = 51;
                     } else if (LA46_0 != 10 && LA46_0 != 13) {
                        if (LA46_0 != 32 || PythonLexer.this.startPos != 0 && PythonLexer.this.startPos <= 0) {
                           if (LA46_0 == 9 && (PythonLexer.this.startPos == 0 || PythonLexer.this.startPos > 0)) {
                              s = 54;
                           } else if (LA46_0 == 35) {
                              s = 55;
                           }
                        } else {
                           s = 53;
                        }
                     } else {
                        s = 52;
                     }
                  } else {
                     s = 49;
                  }
               } else {
                  s = 48;
               }

               _input.seek(index46_0);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA46_132 = _input.LA(1);
               int index46_132 = _input.index();
               _input.rewind();
               s = -1;
               if (PythonLexer.this.startPos > 0) {
                  s = 130;
               } else if (PythonLexer.this.startPos == 0 || PythonLexer.this.startPos == 0 && PythonLexer.this.implicitLineJoiningLevel > 0) {
                  s = 131;
               }

               _input.seek(index46_132);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA46_134 = _input.LA(1);
               int index46_134 = _input.index();
               _input.rewind();
               s = -1;
               if (PythonLexer.this.startPos > 0) {
                  s = 130;
               } else if (PythonLexer.this.startPos == 0 || PythonLexer.this.startPos == 0 && PythonLexer.this.implicitLineJoiningLevel > 0) {
                  s = 131;
               }

               _input.seek(index46_134);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA46_53 = _input.LA(1);
               int index46_53 = _input.index();
               _input.rewind();
               sx = true;
               if ((LA46_53 == 10 || LA46_53 == 13) && PythonLexer.this.startPos == 0) {
                  s = 131;
               } else if (LA46_53 != 32 || PythonLexer.this.startPos != 0 && PythonLexer.this.startPos <= 0) {
                  if (LA46_53 == 9 && (PythonLexer.this.startPos == 0 || PythonLexer.this.startPos > 0)) {
                     s = 54;
                  } else if (LA46_53 == 35 && PythonLexer.this.startPos == 0) {
                     s = 133;
                  } else if (LA46_53 == 12 && PythonLexer.this.startPos > 0) {
                     s = 130;
                  } else {
                     s = 132;
                  }
               } else {
                  s = 53;
               }

               _input.seek(index46_53);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA46_54 = _input.LA(1);
               int index46_54 = _input.index();
               _input.rewind();
               sx = true;
               if ((LA46_54 == 10 || LA46_54 == 13) && PythonLexer.this.startPos == 0) {
                  s = 131;
               } else if (LA46_54 != 32 || PythonLexer.this.startPos != 0 && PythonLexer.this.startPos <= 0) {
                  if (LA46_54 == 9 && (PythonLexer.this.startPos == 0 || PythonLexer.this.startPos > 0)) {
                     s = 54;
                  } else if (LA46_54 == 35 && PythonLexer.this.startPos == 0) {
                     s = 133;
                  } else if (LA46_54 == 12 && PythonLexer.this.startPos > 0) {
                     s = 130;
                  } else {
                     s = 134;
                  }
               } else {
                  s = 53;
               }

               _input.seek(index46_54);
               if (s >= 0) {
                  return s;
               }
         }

         NoViableAltException nvae = new NoViableAltException(this.getDescription(), 46, s, _input);
         this.error(nvae);
         throw nvae;
      }
   }

   class DFA45 extends DFA {
      public DFA45(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 45;
         this.eot = PythonLexer.DFA45_eot;
         this.eof = PythonLexer.DFA45_eof;
         this.min = PythonLexer.DFA45_min;
         this.max = PythonLexer.DFA45_max;
         this.accept = PythonLexer.DFA45_accept;
         this.special = PythonLexer.DFA45_special;
         this.transition = PythonLexer.DFA45_transition;
      }

      public String getDescription() {
         return "2507:1: COMMENT : ({...}? => ( ' ' | '\\t' )* '#' (~ '\\n' )* ( '\\n' )+ | '#' (~ '\\n' )* );";
      }

      public int specialStateTransition(int sx, IntStream _input) throws NoViableAltException {
         boolean s;
         switch (sx) {
            case 0:
               int LA45_0 = _input.LA(1);
               int index45_0 = _input.index();
               _input.rewind();
               sx = -1;
               if ((LA45_0 == 9 || LA45_0 == 32) && PythonLexer.this.startPos == 0) {
                  sx = 1;
               } else if (LA45_0 == 35) {
                  sx = 2;
               }

               _input.seek(index45_0);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 1:
               int LA45_3 = _input.LA(1);
               int index45_3 = _input.index();
               _input.rewind();
               s = true;
               if (LA45_3 >= 0 && LA45_3 <= 9 || LA45_3 >= 11 && LA45_3 <= 65535) {
                  sx = 3;
               } else if (LA45_3 == 10 && PythonLexer.this.startPos == 0) {
                  sx = 1;
               } else {
                  sx = 4;
               }

               _input.seek(index45_3);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 2:
               int LA45_2 = _input.LA(1);
               int index45_2 = _input.index();
               _input.rewind();
               s = true;
               if (LA45_2 >= 0 && LA45_2 <= 9 || LA45_2 >= 11 && LA45_2 <= 65535) {
                  sx = 3;
               } else if (LA45_2 == 10 && PythonLexer.this.startPos == 0) {
                  sx = 1;
               } else {
                  sx = 4;
               }

               _input.seek(index45_2);
               if (sx >= 0) {
                  return sx;
               }
         }

         NoViableAltException nvae = new NoViableAltException(this.getDescription(), 45, sx, _input);
         this.error(nvae);
         throw nvae;
      }
   }

   class DFA17 extends DFA {
      public DFA17(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 17;
         this.eot = PythonLexer.DFA17_eot;
         this.eof = PythonLexer.DFA17_eof;
         this.min = PythonLexer.DFA17_min;
         this.max = PythonLexer.DFA17_max;
         this.accept = PythonLexer.DFA17_accept;
         this.special = PythonLexer.DFA17_special;
         this.transition = PythonLexer.DFA17_transition;
      }

      public String getDescription() {
         return "2373:9: ( 'r' | 'u' | 'b' | 'ur' | 'br' | 'R' | 'U' | 'B' | 'UR' | 'BR' | 'uR' | 'Ur' | 'Br' | 'bR' )?";
      }
   }

   class DFA14 extends DFA {
      public DFA14(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 14;
         this.eot = PythonLexer.DFA14_eot;
         this.eof = PythonLexer.DFA14_eof;
         this.min = PythonLexer.DFA14_min;
         this.max = PythonLexer.DFA14_max;
         this.accept = PythonLexer.DFA14_accept;
         this.special = PythonLexer.DFA14_special;
         this.transition = PythonLexer.DFA14_transition;
      }

      public String getDescription() {
         return "2357:1: COMPLEX : ( ( DIGITS )+ ( 'j' | 'J' ) | FLOAT ( 'j' | 'J' ) );";
      }
   }

   class DFA5 extends DFA {
      public DFA5(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 5;
         this.eot = PythonLexer.DFA5_eot;
         this.eof = PythonLexer.DFA5_eof;
         this.min = PythonLexer.DFA5_min;
         this.max = PythonLexer.DFA5_max;
         this.accept = PythonLexer.DFA5_accept;
         this.special = PythonLexer.DFA5_special;
         this.transition = PythonLexer.DFA5_transition;
      }

      public String getDescription() {
         return "2331:1: FLOAT : ( '.' DIGITS ( Exponent )? | DIGITS '.' Exponent | DIGITS ( '.' ( DIGITS ( Exponent )? )? | Exponent ) );";
      }
   }
}
