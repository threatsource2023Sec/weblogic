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

public class PythonPartialLexer extends Lexer {
   public static final int PRINT = 11;
   public static final int TRISTRINGPART = 99;
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
   public static final int STRINGPART = 100;
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
   public boolean eofWhileNested;
   public boolean partial;
   int implicitLineJoiningLevel;
   int startPos;
   private ErrorHandler errorHandler;
   protected DFA5 dfa5;
   protected DFA12 dfa12;
   protected DFA15 dfa15;
   protected DFA21 dfa21;
   protected DFA25 dfa25;
   protected DFA26 dfa26;
   protected DFA27 dfa27;
   protected DFA51 dfa51;
   protected DFA52 dfa52;
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
   static final String DFA12_eotS = "\u0004\uffff";
   static final String DFA12_eofS = "\u0004\uffff";
   static final String DFA12_minS = "\u0002.\u0002\uffff";
   static final String DFA12_maxS = "\u00019\u0001j\u0002\uffff";
   static final String DFA12_acceptS = "\u0002\uffff\u0001\u0002\u0001\u0001";
   static final String DFA12_specialS = "\u0004\uffff}>";
   static final String[] DFA12_transitionS;
   static final short[] DFA12_eot;
   static final short[] DFA12_eof;
   static final char[] DFA12_min;
   static final char[] DFA12_max;
   static final short[] DFA12_accept;
   static final short[] DFA12_special;
   static final short[][] DFA12_transition;
   static final String DFA15_eotS = "\f\uffff";
   static final String DFA15_eofS = "\f\uffff";
   static final String DFA15_minS = "\u0001\"\u0001\uffff\u0001\"\u0001\uffff\u0001\"\u0007\uffff";
   static final String DFA15_maxS = "\u0001u\u0001\uffff\u0001r\u0001\uffff\u0001r\u0007\uffff";
   static final String DFA15_acceptS = "\u0001\uffff\u0001\u0001\u0001\uffff\u0001\u0004\u0001\uffff\u0001\t\u0001\u0003\u0001\u0007\u0001\u0002\u0001\u0006\u0001\b\u0001\u0005";
   static final String DFA15_specialS = "\f\uffff}>";
   static final String[] DFA15_transitionS;
   static final short[] DFA15_eot;
   static final short[] DFA15_eof;
   static final char[] DFA15_min;
   static final char[] DFA15_max;
   static final short[] DFA15_accept;
   static final short[] DFA15_special;
   static final short[][] DFA15_transition;
   static final String DFA21_eotS = "\f\uffff";
   static final String DFA21_eofS = "\f\uffff";
   static final String DFA21_minS = "\u0001\"\u0001\uffff\u0001\"\u0001\uffff\u0001\"\u0007\uffff";
   static final String DFA21_maxS = "\u0001u\u0001\uffff\u0001r\u0001\uffff\u0001r\u0007\uffff";
   static final String DFA21_acceptS = "\u0001\uffff\u0001\u0001\u0001\uffff\u0001\u0004\u0001\uffff\u0001\t\u0001\u0003\u0001\u0007\u0001\u0002\u0001\u0006\u0001\b\u0001\u0005";
   static final String DFA21_specialS = "\f\uffff}>";
   static final String[] DFA21_transitionS;
   static final short[] DFA21_eot;
   static final short[] DFA21_eof;
   static final char[] DFA21_min;
   static final char[] DFA21_max;
   static final short[] DFA21_accept;
   static final short[] DFA21_special;
   static final short[][] DFA21_transition;
   static final String DFA25_eotS = "\f\uffff";
   static final String DFA25_eofS = "\f\uffff";
   static final String DFA25_minS = "\u0001\"\u0001\uffff\u0001\"\u0001\uffff\u0001\"\u0007\uffff";
   static final String DFA25_maxS = "\u0001u\u0001\uffff\u0001r\u0001\uffff\u0001r\u0007\uffff";
   static final String DFA25_acceptS = "\u0001\uffff\u0001\u0001\u0001\uffff\u0001\u0004\u0001\uffff\u0001\t\u0001\u0003\u0001\u0007\u0001\u0002\u0001\u0006\u0001\b\u0001\u0005";
   static final String DFA25_specialS = "\f\uffff}>";
   static final String[] DFA25_transitionS;
   static final short[] DFA25_eot;
   static final short[] DFA25_eof;
   static final char[] DFA25_min;
   static final char[] DFA25_max;
   static final short[] DFA25_accept;
   static final short[] DFA25_special;
   static final short[][] DFA25_transition;
   static final String DFA26_eotS = "\u0004\uffff\u0001\u0006\u0002\uffff\u0002\u0006\u0002\uffff\u0003\u0006\u0001\uffff\u0001\u0006\u0001\uffff";
   static final String DFA26_eofS = "\u0011\uffff";
   static final String DFA26_minS = "\u0002\u0000\u0001\uffff\u0002\u0000\u0002\uffff\u0007\u0000\u0001\uffff\u0001\u0000\u0001\uffff";
   static final String DFA26_maxS = "\u0002\uffff\u0001\uffff\u0002\uffff\u0002\uffff\u0007\uffff\u0001\uffff\u0001\uffff\u0001\uffff";
   static final String DFA26_acceptS = "\u0002\uffff\u0001\u0002\u0002\uffff\u0001\u0001\u0001\u0003\u0007\uffff\u0001\u0001\u0001\uffff\u0001\u0001";
   static final String DFA26_specialS = "\u0001\u0006\u0001\u0000\u0001\uffff\u0001\u0001\u0001\u0003\u0002\uffff\u0001\n\u0001\b\u0001\u000b\u0001\u0007\u0001\u0002\u0001\t\u0001\u0005\u0001\uffff\u0001\u0004\u0001\uffff}>";
   static final String[] DFA26_transitionS;
   static final short[] DFA26_eot;
   static final short[] DFA26_eof;
   static final char[] DFA26_min;
   static final char[] DFA26_max;
   static final short[] DFA26_accept;
   static final short[] DFA26_special;
   static final short[][] DFA26_transition;
   static final String DFA27_eotS = "\u0004\uffff\u0001\u0006\u0002\uffff\u0002\u0006\u0002\uffff\u0003\u0006\u0001\uffff\u0001\u0006\u0001\uffff";
   static final String DFA27_eofS = "\u0011\uffff";
   static final String DFA27_minS = "\u0002\u0000\u0001\uffff\u0002\u0000\u0002\uffff\u0007\u0000\u0001\uffff\u0001\u0000\u0001\uffff";
   static final String DFA27_maxS = "\u0002\uffff\u0001\uffff\u0002\uffff\u0002\uffff\u0007\uffff\u0001\uffff\u0001\uffff\u0001\uffff";
   static final String DFA27_acceptS = "\u0002\uffff\u0001\u0002\u0002\uffff\u0001\u0001\u0001\u0003\u0007\uffff\u0001\u0001\u0001\uffff\u0001\u0001";
   static final String DFA27_specialS = "\u0001\u0000\u0001\u0001\u0001\uffff\u0001\u0007\u0001\u0003\u0002\uffff\u0001\u000b\u0001\b\u0001\u0004\u0001\u0002\u0001\n\u0001\t\u0001\u0006\u0001\uffff\u0001\u0005\u0001\uffff}>";
   static final String[] DFA27_transitionS;
   static final short[] DFA27_eot;
   static final short[] DFA27_eof;
   static final char[] DFA27_min;
   static final char[] DFA27_max;
   static final short[] DFA27_accept;
   static final short[] DFA27_special;
   static final short[][] DFA27_transition;
   static final String DFA51_eotS = "\u0002\uffff\u0002\u0004\u0001\uffff";
   static final String DFA51_eofS = "\u0005\uffff";
   static final String DFA51_minS = "\u0001\t\u0001\uffff\u0002\u0000\u0001\uffff";
   static final String DFA51_maxS = "\u0001#\u0001\uffff\u0002\uffff\u0001\uffff";
   static final String DFA51_acceptS = "\u0001\uffff\u0001\u0001\u0002\uffff\u0001\u0002";
   static final String DFA51_specialS = "\u0001\u0002\u0001\uffff\u0001\u0000\u0001\u0001\u0001\uffff}>";
   static final String[] DFA51_transitionS;
   static final short[] DFA51_eot;
   static final short[] DFA51_eof;
   static final char[] DFA51_min;
   static final char[] DFA51_max;
   static final short[] DFA51_accept;
   static final short[] DFA51_special;
   static final short[][] DFA51_transition;
   static final String DFA52_eotS = "\u0001\uffff\u000e/\u0007\uffff\u0001R\u0001T\u0001W\u0001Z\u0001\\\u0001^\u0001b\u0001e\u0001g\u0001i\u0003\uffff\u0001k\u0002\uffff\u0001l\u0001\uffff\u0002/\u0002q\u0003/\u0004\uffff\u0001\u0083\u0001\uffff\u0001\u0085\u0001\u0087\u0001\uffff\u0001\u0089\u000b/\u0001\u0098\u0001/\u0001\u009a\u0001\u009b\t/\u0004\uffff\u0001¦\u0002\uffff\u0001¨\b\uffff\u0001ª\u0002\uffff\u0001¬\b\uffff\u0001\u00ad\u0001¯\u0001/\u0002\uffff\u0001\u00ad\u0001\uffff\u0001q\u0003\uffff\u0001q\u0004/\u0001·\u0002\uffff\u0001·\u0007\uffff\u0001/\u0001\uffff\u0001À\u0003/\u0001Ä\u0001Å\u0006/\u0001Ì\u0001/\u0001\uffff\u0001/\u0002\uffff\u0005/\u0001Ô\u0003/\u000b\uffff\u0001Ú\u0001q\u0001\uffff\u0001\u00ad\u0001\uffff\u0001\u00ad\u0001á\u0002\uffff\u0001â\u0001\uffff\u0001á\u0001\uffff\u0001â\u0001\uffff\u0001/\u0001\uffff\u0003/\u0002\uffff\u0001ò\u0001ó\u0001/\u0001õ\u0001/\u0001÷\u0001\uffff\u0003/\u0001û\u0003/\u0001\uffff\u0001/\u0001Ā\u0001/\u0001\uffff\u0001\u00ad\u0002\uffff\u0001\u00ad\u0001\uffff\u0003á\u0002\uffff\u0002â\u0002\uffff\u0003á\u0002â\u0002\uffff\u0001/\u0001ď\u0001Đ\u0001/\u0002\uffff\u0001/\u0001\uffff\u0001/\u0001\uffff\u0003/\u0001\uffff\u0001ė\u0001Ę\u0001/\u0001Ě\u0001\uffff\u0001ě\u0001\uffff\u0001\u00ad\u0002á\u0001·\u0002â\u0002á\u0001·\u0002â\u0001Ĥ\u0002\uffff\u0001/\u0001Ħ\u0001/\u0001Ĩ\u0001ĩ\u0001Ī\u0002\uffff\u0001ī\u0002\uffff\u0001·\u0003â\u0001·\u0003â\u0001\uffff\u0001/\u0001\uffff\u0001ĭ\u0004\uffff\u0001Į\u0002\uffff";
   static final String DFA52_eofS = "į\uffff";
   static final String DFA52_minS = "\u0001\t\u0001n\u0001r\u0001l\u0001e\u0001l\u0001i\u0001l\u0001f\u0002a\u0001\"\u0001r\u0001h\u0001i\u0007\uffff\u0002=\u0001*\u0001/\u0002=\u0001<\u0003=\u0003\uffff\u0001=\u0002\uffff\u00010\u0001\uffff\u0001r\u0001o\u0002.\u0003\"\u0001\uffff\u0002\u0000\u0001\uffff\u0001\n\u0001\uffff\u0002\t\u0001\uffff\u00010\u0001d\u0001e\u0001a\u0001n\u0001f\u0001i\u0001c\u0001n\u0001o\u0001r\u0001o\u00010\u0001p\u00020\u0001m\u0001s\u0002i\u0001t\u0001y\u0001i\u0001t\u0001e\u0004\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u00020\u0001t\u00010\u0001\uffff\u00010\u0001+\u0002.\u0002\uffff\u0001.\u0004\"\u0001'\u0002\u0000\u0001\"\u0002\u0000\u0002\uffff\u0001\u0000\u0001\uffff\u0001\u0000\u0001e\u0001\uffff\u00010\u0001a\u0001s\u0001t\u00020\u0001f\u0002e\u0001c\u0001a\u0001m\u00010\u0001b\u0001\uffff\u0001o\u0002\uffff\u0001b\u0001s\u0001n\u0001s\u0001u\u00010\u0001l\u0001h\u0001l\t\uffff\u0001+\u0001\uffff\u00020\u0001+\u00030\u0001\u0000\u0001\uffff\u0007\u0000\u0001r\u0001\uffff\u0001k\u0001s\u0001i\u0002\uffff\u00020\u0001p\u00010\u0001l\u00010\u0001\uffff\u0001a\u0001r\u0001d\u00010\u0001t\u0001e\u0001r\u0001\uffff\u0001e\u00010\u0001d\u00020\u0001\uffff\u00020\u0001+\u0003\u0000\u0002\uffff\u000b\u0000\u0001t\u00020\u0001n\u0002\uffff\u0001t\u0001\uffff\u0001l\u0001\uffff\u0001l\u0001t\u0001a\u0001\uffff\u00020\u0001n\u00010\u0001\uffff\u00030\n\u0000\u00010\u0002\uffff\u0001u\u00010\u0001y\u00030\u0002\uffff\u00010\u0002\uffff\b\u0000\u0001\uffff\u0001e\u0001\uffff\u00010\u0004\uffff\u00010\u0002\uffff";
   static final String DFA52_maxS = "\u0001~\u0001s\u0001r\u0001o\u0001e\u0001x\u0001r\u0001l\u0001s\u0001a\u0001r\u0001e\u0001r\u0002i\u0007\uffff\u0006=\u0002>\u0002=\u0003\uffff\u0001=\u0002\uffff\u00019\u0001\uffff\u0001r\u0001o\u0001x\u0001l\u0001r\u0001'\u0001r\u0001\uffff\u0002\uffff\u0001\uffff\u0001\r\u0001\uffff\u0002#\u0001\uffff\u0001z\u0001d\u0001e\u0001a\u0001n\u0001l\u0001s\u0001e\u0001n\u0001o\u0001r\u0001o\u0001z\u0001p\u0002z\u0001m\u0001s\u0002i\u0001t\u0001y\u0001i\u0001t\u0001e\u0004\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u0001j\u0001z\u0001t\u0001f\u0001\uffff\u0001j\u00019\u0001l\u0001j\u0002\uffff\u0001l\u0005'\u0002\uffff\u0001\"\u0002\uffff\u0002\uffff\u0001\u0000\u0001\uffff\u0001\u0000\u0001e\u0001\uffff\u0001z\u0001a\u0001s\u0001t\u0002z\u0001f\u0002e\u0001c\u0001a\u0001m\u0001z\u0001b\u0001\uffff\u0001o\u0002\uffff\u0001b\u0001s\u0001n\u0001s\u0001u\u0001z\u0001l\u0001h\u0001l\t\uffff\u00019\u0001\uffff\u0001z\u0001l\u00019\u0001j\u00019\u0001j\u0001\uffff\u0001\uffff\u0007\uffff\u0001r\u0001\uffff\u0001k\u0001s\u0001i\u0002\uffff\u0002z\u0001p\u0001z\u0001l\u0001z\u0001\uffff\u0001a\u0001r\u0001d\u0001z\u0001t\u0001e\u0001r\u0001\uffff\u0001e\u0001z\u0001d\u00019\u0001j\u0001\uffff\u00019\u0001j\u00019\u0003\uffff\u0002\uffff\u000b\uffff\u0001t\u0002z\u0001n\u0002\uffff\u0001t\u0001\uffff\u0001l\u0001\uffff\u0001l\u0001t\u0001a\u0001\uffff\u0002z\u0001n\u0001z\u0001\uffff\u0001z\u00019\u0001j\n\uffff\u0001z\u0002\uffff\u0001u\u0001z\u0001y\u0003z\u0002\uffff\u0001z\u0002\uffff\b\uffff\u0001\uffff\u0001e\u0001\uffff\u0001z\u0004\uffff\u0001z\u0002\uffff";
   static final String DFA52_acceptS = "\u000f\uffff\u0001\u001d\u0001\u001e\u0001\u001f\u0001 \u0001!\u0001\"\u0001#\n\uffff\u0001.\u0001/\u00010\u0001\uffff\u00012\u00014\u0001\uffff\u0001I\u0007\uffff\u0001Q\u0002\uffff\u0001U\u0001\uffff\u0001V\u0002\uffff\u0001Y\u0019\uffff\u0001:\u0001$\u0001;\u0001%\u0001\uffff\u0001=\u0001&\u0001\uffff\u0001?\u0001'\u0001@\u0001(\u0001B\u0001)\u00015\u00016\u0001\uffff\u0001*\u00018\u0001\uffff\u0001+\u00013\u0001,\u0001A\u0001-\u0001C\u00011\u0001H\u0004\uffff\u0001O\u0004\uffff\u0001N\u0001P\u000b\uffff\u0001W\u0001Y\u0001\uffff\u0001X\u0002\uffff\u0001\u0001\u000e\uffff\u0001\u000f\u0001\uffff\u0001\u0011\u0001\u0012\t\uffff\u0001F\u0001<\u0001G\u0001>\u0001D\u00017\u0001E\u00019\u0001M\u0001\uffff\u0001K\u0007\uffff\u0001R\b\uffff\u0001J\u0003\uffff\u0001\u0006\u0001\u0007\u0006\uffff\u0001\r\u0007\uffff\u0001\u0019\u0005\uffff\u0001L\u0006\uffff\u0001S\u0001T\u000f\uffff\u0001\b\u0001\u0014\u0001\uffff\u0001\n\u0001\uffff\u0001\f\u0003\uffff\u0001\u0015\u0004\uffff\u0001\u001b\u000e\uffff\u0001\u0003\u0001\u0004\u0006\uffff\u0001\u0016\u0001\u0017\u0001\uffff\u0001\u001a\u0001\u001c\b\uffff\u0001\u0002\u0001\uffff\u0001\t\u0001\uffff\u0001\u000e\u0001\u0010\u0001\u0013\u0001\u0018\u0001\uffff\u0001\u000b\u0001\u0005";
   static final String DFA52_specialS = "\u0001-/\uffff\u0001$\u0001\u000f\u0001\uffff\u0001\u000b\u0001\uffff\u0001\f\u0001\rG\uffff\u0001\u0000\u0001\u0006\u0001\uffff\u0001\u0001\u0001\u001c\u0002\uffff\u0001.\u0001\uffff\u0001/.\uffff\u0001\u0002\u0001\uffff\u0001\u0005\u0001\t\u0001\u0003\u0001 \u0001\u001b\u0001\u001e\u0001\u001a\u001f\uffff\u0001\u0016\u0001)\u0001,\u0002\uffff\u0001\u0011\u0001#\u0001\u0018\u0001\n\u0001\b\u00013\u0001\u0015\u0001*\u0001\u000e\u0001\u0004\u0001!\u0016\uffff\u0001\u001d\u0001\u0014\u00010\u0001\"\u0001'\u0001\u0019\u0001+\u0001(\u0001\u001f\u0001\u0013\u000e\uffff\u00011\u0001&\u00012\u0001%\u0001\u0007\u0001\u0012\u0001\u0017\u0001\u0010\u000b\uffff}>";
   static final String[] DFA52_transitionS;
   static final short[] DFA52_eot;
   static final short[] DFA52_eof;
   static final char[] DFA52_min;
   static final char[] DFA52_max;
   static final short[] DFA52_accept;
   static final short[] DFA52_special;
   static final short[][] DFA52_transition;

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
            this.errorHandler.reportError(this, var2);
            this.errorHandler.recover(this, var2);
         } catch (FailedPredicateException var3) {
            this.errorHandler.reportError(this, var3);
            this.errorHandler.recover(this, var3);
         } catch (RecognitionException var4) {
            this.errorHandler.reportError(this, var4);
         }
      }
   }

   public PythonPartialLexer() {
      this.eofWhileNested = false;
      this.partial = false;
      this.implicitLineJoiningLevel = 0;
      this.startPos = -1;
      this.dfa5 = new DFA5(this);
      this.dfa12 = new DFA12(this);
      this.dfa15 = new DFA15(this);
      this.dfa21 = new DFA21(this);
      this.dfa25 = new DFA25(this);
      this.dfa26 = new DFA26(this);
      this.dfa27 = new DFA27(this);
      this.dfa51 = new DFA51(this);
      this.dfa52 = new DFA52(this);
   }

   public PythonPartialLexer(CharStream input) {
      this(input, new RecognizerSharedState());
   }

   public PythonPartialLexer(CharStream input, RecognizerSharedState state) {
      super(input, state);
      this.eofWhileNested = false;
      this.partial = false;
      this.implicitLineJoiningLevel = 0;
      this.startPos = -1;
      this.dfa5 = new DFA5(this);
      this.dfa12 = new DFA12(this);
      this.dfa15 = new DFA15(this);
      this.dfa21 = new DFA21(this);
      this.dfa25 = new DFA25(this);
      this.dfa26 = new DFA26(this);
      this.dfa27 = new DFA27(this);
      this.dfa51 = new DFA51(this);
      this.dfa52 = new DFA52(this);
   }

   public String getGrammarFileName() {
      return "/Users/fwierzbicki/hg/jython/jython/grammar/PythonPartial.g";
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
      int alt10 = true;
      int LA10_0 = this.input.LA(1);
      byte alt10;
      int cnt7;
      if (LA10_0 == 48) {
         cnt7 = this.input.LA(2);
         if (cnt7 != 88 && cnt7 != 120) {
            alt10 = 2;
         } else {
            alt10 = 1;
         }
      } else {
         if (LA10_0 < 49 || LA10_0 > 57) {
            NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
            throw nvae;
         }

         alt10 = 3;
      }

      int LA9_0;
      byte alt9;
      label89:
      switch (alt10) {
         case 1:
            this.match(48);
            if (this.input.LA(1) != 88 && this.input.LA(1) != 120) {
               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
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
                     break label89;
               }
            }
         case 2:
            this.match(48);

            while(true) {
               alt9 = 2;
               LA9_0 = this.input.LA(1);
               if (LA9_0 >= 48 && LA9_0 <= 55) {
                  alt9 = 1;
               }

               switch (alt9) {
                  case 1:
                     this.matchRange(48, 55);
                     break;
                  default:
                     break label89;
               }
            }
         case 3:
            this.matchRange(49, 57);

            label71:
            while(true) {
               alt9 = 2;
               LA9_0 = this.input.LA(1);
               if (LA9_0 >= 48 && LA9_0 <= 57) {
                  alt9 = 1;
               }

               switch (alt9) {
                  case 1:
                     this.mDIGITS();
                     break;
                  default:
                     break label71;
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
      int alt12 = true;
      int alt12 = this.dfa12.predict(this.input);
      label43:
      switch (alt12) {
         case 1:
            int cnt11 = 0;

            while(true) {
               int alt11 = 2;
               int LA11_0 = this.input.LA(1);
               if (LA11_0 >= 48 && LA11_0 <= 57) {
                  alt11 = 1;
               }

               switch (alt11) {
                  case 1:
                     this.mDIGITS();
                     ++cnt11;
                     break;
                  default:
                     if (cnt11 < 1) {
                        EarlyExitException eee = new EarlyExitException(11, this.input);
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
      int cnt13 = 0;

      while(true) {
         int alt13 = 2;
         int LA13_0 = this.input.LA(1);
         if (LA13_0 >= 48 && LA13_0 <= 57) {
            alt13 = 1;
         }

         switch (alt13) {
            case 1:
               this.matchRange(48, 57);
               ++cnt13;
               break;
            default:
               if (cnt13 >= 1) {
                  return;
               }

               EarlyExitException eee = new EarlyExitException(13, this.input);
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
            int alt14 = 2;
            int LA14_0 = this.input.LA(1);
            if (LA14_0 >= 48 && LA14_0 <= 57 || LA14_0 >= 65 && LA14_0 <= 90 || LA14_0 == 95 || LA14_0 >= 97 && LA14_0 <= 122) {
               alt14 = 1;
            }

            switch (alt14) {
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
      int alt15 = true;
      int alt15 = this.dfa15.predict(this.input);
      switch (alt15) {
         case 1:
            this.match(114);
            break;
         case 2:
            this.match(117);
            break;
         case 3:
            this.match("ur");
            break;
         case 4:
            this.match(82);
            break;
         case 5:
            this.match(85);
            break;
         case 6:
            this.match("UR");
            break;
         case 7:
            this.match("uR");
            break;
         case 8:
            this.match("Ur");
      }

      int alt20 = true;
      int LA20_0 = this.input.LA(1);
      int LA20_2;
      NoViableAltException nvae;
      byte alt20;
      int LA19_0;
      if (LA20_0 == 39) {
         LA20_2 = this.input.LA(2);
         if (LA20_2 == 39) {
            LA19_0 = this.input.LA(3);
            if (LA19_0 == 39) {
               alt20 = 1;
            } else {
               alt20 = 4;
            }
         } else {
            if ((LA20_2 < 0 || LA20_2 > 9) && (LA20_2 < 11 || LA20_2 > 38) && (LA20_2 < 40 || LA20_2 > 65535)) {
               nvae = new NoViableAltException("", 20, 1, this.input);
               throw nvae;
            }

            alt20 = 4;
         }
      } else {
         if (LA20_0 != 34) {
            NoViableAltException nvae = new NoViableAltException("", 20, 0, this.input);
            throw nvae;
         }

         LA20_2 = this.input.LA(2);
         if (LA20_2 == 34) {
            LA19_0 = this.input.LA(3);
            if (LA19_0 == 34) {
               alt20 = 2;
            } else {
               alt20 = 3;
            }
         } else {
            if ((LA20_2 < 0 || LA20_2 > 9) && (LA20_2 < 11 || LA20_2 > 33) && (LA20_2 < 35 || LA20_2 > 65535)) {
               nvae = new NoViableAltException("", 20, 2, this.input);
               throw nvae;
            }

            alt20 = 3;
         }
      }

      MismatchedSetException mse;
      int LA17_3;
      byte alt17;
      int LA17_1;
      label291:
      switch (alt20) {
         case 1:
            this.match("'''");

            while(true) {
               alt17 = 2;
               LA19_0 = this.input.LA(1);
               if (LA19_0 == 39) {
                  LA17_1 = this.input.LA(2);
                  if (LA17_1 == 39) {
                     LA17_3 = this.input.LA(3);
                     if (LA17_3 == 39) {
                        alt17 = 2;
                     } else if (LA17_3 >= 0 && LA17_3 <= 38 || LA17_3 >= 40 && LA17_3 <= 65535) {
                        alt17 = 1;
                     }
                  } else if (LA17_1 >= 0 && LA17_1 <= 38 || LA17_1 >= 40 && LA17_1 <= 65535) {
                     alt17 = 1;
                  }
               } else if (LA19_0 >= 0 && LA19_0 <= 38 || LA19_0 >= 40 && LA19_0 <= 65535) {
                  alt17 = 1;
               }

               switch (alt17) {
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
               alt17 = 2;
               LA19_0 = this.input.LA(1);
               if (LA19_0 == 34) {
                  LA17_1 = this.input.LA(2);
                  if (LA17_1 == 34) {
                     LA17_3 = this.input.LA(3);
                     if (LA17_3 == 34) {
                        alt17 = 2;
                     } else if (LA17_3 >= 0 && LA17_3 <= 33 || LA17_3 >= 35 && LA17_3 <= 65535) {
                        alt17 = 1;
                     }
                  } else if (LA17_1 >= 0 && LA17_1 <= 33 || LA17_1 >= 35 && LA17_1 <= 65535) {
                     alt17 = 1;
                  }
               } else if (LA19_0 >= 0 && LA19_0 <= 33 || LA19_0 >= 35 && LA19_0 <= 65535) {
                  alt17 = 1;
               }

               switch (alt17) {
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
               alt17 = 3;
               LA19_0 = this.input.LA(1);
               if (LA19_0 == 92) {
                  alt17 = 1;
               } else if (LA19_0 >= 0 && LA19_0 <= 9 || LA19_0 >= 11 && LA19_0 <= 33 || LA19_0 >= 35 && LA19_0 <= 91 || LA19_0 >= 93 && LA19_0 <= 65535) {
                  alt17 = 2;
               }

               switch (alt17) {
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
               alt17 = 3;
               LA19_0 = this.input.LA(1);
               if (LA19_0 == 92) {
                  alt17 = 1;
               } else if (LA19_0 >= 0 && LA19_0 <= 9 || LA19_0 >= 11 && LA19_0 <= 38 || LA19_0 >= 40 && LA19_0 <= 91 || LA19_0 >= 93 && LA19_0 <= 65535) {
                  alt17 = 2;
               }

               switch (alt17) {
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

   public final void mTRISTRINGPART() throws RecognitionException {
      int _type = 99;
      int _channel = 0;
      int alt21 = true;
      int alt21 = this.dfa21.predict(this.input);
      switch (alt21) {
         case 1:
            this.match(114);
            break;
         case 2:
            this.match(117);
            break;
         case 3:
            this.match("ur");
            break;
         case 4:
            this.match(82);
            break;
         case 5:
            this.match(85);
            break;
         case 6:
            this.match("UR");
            break;
         case 7:
            this.match("uR");
            break;
         case 8:
            this.match("Ur");
      }

      int alt24 = true;
      int LA24_0 = this.input.LA(1);
      byte alt24;
      if (LA24_0 == 39) {
         alt24 = 1;
      } else {
         if (LA24_0 != 34) {
            NoViableAltException nvae = new NoViableAltException("", 24, 0, this.input);
            throw nvae;
         }

         alt24 = 2;
      }

      byte alt23;
      int LA23_0;
      MismatchedSetException mse;
      label72:
      switch (alt24) {
         case 1:
            this.match("'''");

            while(true) {
               alt23 = 2;
               LA23_0 = this.input.LA(1);
               if (LA23_0 >= 0 && LA23_0 <= 65535) {
                  alt23 = 1;
               }

               switch (alt23) {
                  case 1:
                     if (this.input.LA(1) < 0 || this.input.LA(1) > 65535) {
                        mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     break;
                  default:
                     break label72;
               }
            }
         case 2:
            this.match("\"\"\"");

            label64:
            while(true) {
               alt23 = 2;
               LA23_0 = this.input.LA(1);
               if (LA23_0 >= 0 && LA23_0 <= 65535) {
                  alt23 = 1;
               }

               switch (alt23) {
                  case 1:
                     if (this.input.LA(1) < 0 || this.input.LA(1) > 65535) {
                        mse = new MismatchedSetException((BitSet)null, this.input);
                        this.recover(mse);
                        throw mse;
                     }

                     this.input.consume();
                     break;
                  default:
                     break label64;
               }
            }
      }

      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mSTRINGPART() throws RecognitionException {
      int _type = 100;
      int _channel = 0;
      int alt25 = true;
      int alt25 = this.dfa25.predict(this.input);
      switch (alt25) {
         case 1:
            this.match(114);
            break;
         case 2:
            this.match(117);
            break;
         case 3:
            this.match("ur");
            break;
         case 4:
            this.match(82);
            break;
         case 5:
            this.match(85);
            break;
         case 6:
            this.match("UR");
            break;
         case 7:
            this.match("uR");
            break;
         case 8:
            this.match("Ur");
      }

      int alt28 = true;
      int LA28_0 = this.input.LA(1);
      byte alt28;
      if (LA28_0 == 34) {
         alt28 = 1;
      } else {
         if (LA28_0 != 39) {
            NoViableAltException nvae = new NoViableAltException("", 28, 0, this.input);
            throw nvae;
         }

         alt28 = 2;
      }

      boolean alt27;
      MismatchedSetException mse;
      int alt27;
      label83:
      switch (alt28) {
         case 1:
            this.match(34);

            while(true) {
               alt27 = true;
               alt27 = this.dfa26.predict(this.input);
               switch (alt27) {
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
                     this.mCONTINUED_LINE();
                     break label83;
               }
            }
         case 2:
            this.match(39);

            label81:
            while(true) {
               alt27 = true;
               alt27 = this.dfa27.predict(this.input);
               switch (alt27) {
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
                     this.mCONTINUED_LINE();
                     break label81;
               }
            }
      }

      this.state.type = _type;
      this.state.channel = _channel;
   }

   public final void mTRIQUOTE() throws RecognitionException {
      int alt29 = 2;
      int LA29_0 = this.input.LA(1);
      if (LA29_0 == 34) {
         alt29 = 1;
      }

      switch (alt29) {
         case 1:
            this.match(34);
         default:
            int alt30 = 2;
            int LA30_0 = this.input.LA(1);
            if (LA30_0 == 34) {
               alt30 = 1;
            }

            switch (alt30) {
               case 1:
                  this.match(34);
               default:
                  int cnt31 = 0;

                  while(true) {
                     int alt31 = 3;
                     int LA31_0 = this.input.LA(1);
                     if (LA31_0 == 92) {
                        alt31 = 1;
                     } else if (LA31_0 >= 0 && LA31_0 <= 33 || LA31_0 >= 35 && LA31_0 <= 91 || LA31_0 >= 93 && LA31_0 <= 65535) {
                        alt31 = 2;
                     }

                     switch (alt31) {
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
                           if (cnt31 >= 1) {
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(31, this.input);
                           throw eee;
                     }

                     ++cnt31;
                  }
            }
      }
   }

   public final void mTRIAPOS() throws RecognitionException {
      int alt32 = 2;
      int LA32_0 = this.input.LA(1);
      if (LA32_0 == 39) {
         alt32 = 1;
      }

      switch (alt32) {
         case 1:
            this.match(39);
         default:
            int alt33 = 2;
            int LA33_0 = this.input.LA(1);
            if (LA33_0 == 39) {
               alt33 = 1;
            }

            switch (alt33) {
               case 1:
                  this.match(39);
               default:
                  int cnt34 = 0;

                  while(true) {
                     int alt34 = 3;
                     int LA34_0 = this.input.LA(1);
                     if (LA34_0 == 92) {
                        alt34 = 1;
                     } else if (LA34_0 >= 0 && LA34_0 <= 38 || LA34_0 >= 40 && LA34_0 <= 91 || LA34_0 >= 93 && LA34_0 <= 65535) {
                        alt34 = 2;
                     }

                     switch (alt34) {
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
                           if (cnt34 >= 1) {
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(34, this.input);
                           throw eee;
                     }

                     ++cnt34;
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
      boolean extraNewlines = false;
      this.match(92);
      int alt35 = 2;
      int LA35_0 = this.input.LA(1);
      if (LA35_0 == 13) {
         alt35 = 1;
      }

      switch (alt35) {
         case 1:
            this.match(13);
         default:
            this.match(10);

            while(true) {
               int alt37 = 2;
               int LA37_0 = this.input.LA(1);
               if (LA37_0 == 9 || LA37_0 == 32) {
                  alt37 = 1;
               }

               switch (alt37) {
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
                     int alt37 = true;
                     LA37_0 = this.input.LA(1);
                     if ((LA37_0 == 9 || LA37_0 == 32) && this.startPos == 0) {
                        alt37 = 1;
                     } else if (LA37_0 == 35) {
                        alt37 = 1;
                     } else if (LA37_0 != 10 && (LA37_0 < 12 || LA37_0 > 13)) {
                        alt37 = 3;
                     } else {
                        alt37 = 2;
                     }

                     switch (alt37) {
                        case 1:
                           this.mCOMMENT();
                           break;
                        case 2:
                           int nlStart1929 = this.getCharIndex();
                           this.mNEWLINE();
                           new CommonToken(this.input, 0, 0, nlStart1929, this.getCharIndex() - 1);
                           extraNewlines = true;
                        case 3:
                     }

                     if (this.input.LA(1) == -1) {
                        if (extraNewlines) {
                           throw new ParseException("invalid syntax");
                        }

                        this.emit(new CommonToken(6, "\\"));
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
      int cnt40 = 0;

      while(true) {
         int alt40 = 2;
         int LA40_0 = this.input.LA(1);
         if (LA40_0 == 10 || LA40_0 >= 12 && LA40_0 <= 13) {
            alt40 = 1;
         }

         switch (alt40) {
            case 1:
               int alt38 = 2;
               int LA38_0 = this.input.LA(1);
               if (LA38_0 == 12) {
                  alt38 = 1;
               }

               switch (alt38) {
                  case 1:
                     this.match(12);
                  default:
                     int alt39 = 2;
                     int LA39_0 = this.input.LA(1);
                     if (LA39_0 == 13) {
                        alt39 = 1;
                     }

                     switch (alt39) {
                        case 1:
                           this.match(13);
                        default:
                           this.match(10);
                           ++cnt40;
                           continue;
                     }
               }
            default:
               if (cnt40 < 1) {
                  EarlyExitException eee = new EarlyExitException(40, this.input);
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
         int cnt41 = 0;

         while(true) {
            int alt41 = 2;
            int LA41_0 = this.input.LA(1);
            if (LA41_0 == 9 || LA41_0 == 12 || LA41_0 == 32) {
               alt41 = 1;
            }

            switch (alt41) {
               case 1:
                  if (this.input.LA(1) != 9 && this.input.LA(1) != 12 && this.input.LA(1) != 32) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     this.recover(mse);
                     throw mse;
                  }

                  this.input.consume();
                  ++cnt41;
                  break;
               default:
                  if (cnt41 >= 1) {
                     int _channel = 99;
                     this.state.type = _type;
                     this.state.channel = _channel;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(41, this.input);
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
         int alt46 = true;
         int LA46_0 = this.input.LA(1);
         int cnt42;
         byte alt46;
         if (LA46_0 == 32) {
            cnt42 = this.input.LA(2);
            if (this.implicitLineJoiningLevel > 0) {
               alt46 = 1;
            } else {
               alt46 = 2;
            }
         } else {
            if (LA46_0 != 9) {
               NoViableAltException nvae = new NoViableAltException("", 46, 0, this.input);
               throw nvae;
            }

            cnt42 = this.input.LA(2);
            if (this.implicitLineJoiningLevel > 0) {
               alt46 = 1;
            } else {
               alt46 = 2;
            }
         }

         byte alt45;
         int i;
         EarlyExitException eee;
         label141:
         switch (alt46) {
            case 1:
               if (this.implicitLineJoiningLevel <= 0) {
                  throw new FailedPredicateException(this.input, "LEADING_WS", "implicitLineJoiningLevel>0");
               }

               cnt42 = 0;

               while(true) {
                  alt45 = 2;
                  i = this.input.LA(1);
                  if (i == 9 || i == 32) {
                     alt45 = 1;
                  }

                  switch (alt45) {
                     case 1:
                        if (this.input.LA(1) != 9 && this.input.LA(1) != 32) {
                           MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                           this.recover(mse);
                           throw mse;
                        }

                        this.input.consume();
                        ++cnt42;
                        break;
                     default:
                        if (cnt42 < 1) {
                           eee = new EarlyExitException(42, this.input);
                           throw eee;
                        }

                        _channel = 99;
                        break label141;
                  }
               }
            case 2:
               cnt42 = 0;

               label138:
               while(true) {
                  alt45 = 3;
                  i = this.input.LA(1);
                  if (i == 32) {
                     alt45 = 1;
                  } else if (i == 9) {
                     alt45 = 2;
                  }

                  switch (alt45) {
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
                        if (cnt42 < 1) {
                           eee = new EarlyExitException(43, this.input);
                           throw eee;
                        }

                        while(true) {
                           alt45 = 2;
                           i = this.input.LA(1);
                           if (i == 10 || i == 13) {
                              alt45 = 1;
                           }

                           switch (alt45) {
                              case 1:
                                 int alt44 = 2;
                                 int LA44_0 = this.input.LA(1);
                                 if (LA44_0 == 13) {
                                    alt44 = 1;
                                 }

                                 switch (alt44) {
                                    case 1:
                                       this.match(13);
                                    default:
                                       this.match(10);
                                       ++newlines;
                                       continue;
                                 }
                              default:
                                 char[] indentation;
                                 CommonToken c;
                                 if (this.input.LA(1) == -1 && newlines != 0) {
                                    indentation = new char[newlines];

                                    for(i = 0; i < newlines; ++i) {
                                       indentation[i] = '\n';
                                    }

                                    c = new CommonToken(7, new String(indentation));
                                    c.setLine(this.input.getLine());
                                    c.setCharPositionInLine(this.input.getCharPositionInLine());
                                    c.setStartIndex(this.input.index() - 1);
                                    c.setStopIndex(this.input.index() - 1);
                                    this.emit(c);
                                 } else {
                                    indentation = new char[spaces];

                                    for(i = 0; i < spaces; ++i) {
                                       indentation[i] = ' ';
                                    }

                                    c = new CommonToken(8, new String(indentation));
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
                                 break label138;
                           }
                        }
                  }

                  ++cnt42;
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
      int alt51 = true;
      int alt51 = this.dfa51.predict(this.input);
      int cnt49;
      int LA48_0;
      MismatchedSetException mse;
      label104:
      switch (alt51) {
         case 1:
            if (this.startPos != 0) {
               throw new FailedPredicateException(this.input, "COMMENT", "startPos==0");
            }

            while(true) {
               cnt49 = 2;
               LA48_0 = this.input.LA(1);
               if (LA48_0 == 9 || LA48_0 == 32) {
                  cnt49 = 1;
               }

               switch (cnt49) {
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
                        cnt49 = 2;
                        LA48_0 = this.input.LA(1);
                        if (LA48_0 >= 0 && LA48_0 <= 9 || LA48_0 >= 11 && LA48_0 <= 65535) {
                           cnt49 = 1;
                        }

                        switch (cnt49) {
                           case 1:
                              if ((this.input.LA(1) < 0 || this.input.LA(1) > 9) && (this.input.LA(1) < 11 || this.input.LA(1) > 65535)) {
                                 mse = new MismatchedSetException((BitSet)null, this.input);
                                 this.recover(mse);
                                 throw mse;
                              }

                              this.input.consume();
                              break;
                           default:
                              cnt49 = 0;

                              while(true) {
                                 int alt49 = 2;
                                 int LA49_0 = this.input.LA(1);
                                 if (LA49_0 == 10) {
                                    alt49 = 1;
                                 }

                                 switch (alt49) {
                                    case 1:
                                       this.match(10);
                                       ++cnt49;
                                       break;
                                    default:
                                       if (cnt49 < 1) {
                                          EarlyExitException eee = new EarlyExitException(49, this.input);
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
               cnt49 = 2;
               LA48_0 = this.input.LA(1);
               if (LA48_0 >= 0 && LA48_0 <= 9 || LA48_0 >= 11 && LA48_0 <= 65535) {
                  cnt49 = 1;
               }

               switch (cnt49) {
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
      int alt52 = true;
      int alt52 = this.dfa52.predict(this.input);
      switch (alt52) {
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
            this.mTRISTRINGPART();
            break;
         case 84:
            this.mSTRINGPART();
            break;
         case 85:
            this.mCONTINUED_LINE();
            break;
         case 86:
            this.mNEWLINE();
            break;
         case 87:
            this.mWS();
            break;
         case 88:
            this.mLEADING_WS();
            break;
         case 89:
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

      DFA12_transitionS = new String[]{"\u0001\u0002\u0001\uffff\n\u0001", "\u0001\u0002\u0001\uffff\n\u0001\u000b\uffff\u0001\u0002\u0004\uffff\u0001\u0003\u001a\uffff\u0001\u0002\u0004\uffff\u0001\u0003", "", ""};
      DFA12_eot = DFA.unpackEncodedString("\u0004\uffff");
      DFA12_eof = DFA.unpackEncodedString("\u0004\uffff");
      DFA12_min = DFA.unpackEncodedStringToUnsignedChars("\u0002.\u0002\uffff");
      DFA12_max = DFA.unpackEncodedStringToUnsignedChars("\u00019\u0001j\u0002\uffff");
      DFA12_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0001\u0001");
      DFA12_special = DFA.unpackEncodedString("\u0004\uffff}>");
      numStates = DFA12_transitionS.length;
      DFA12_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
      }

      DFA15_transitionS = new String[]{"\u0001\u0005\u0004\uffff\u0001\u0005*\uffff\u0001\u0003\u0002\uffff\u0001\u0004\u001c\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "", "\u0001\b\u0004\uffff\u0001\b*\uffff\u0001\u0007\u001f\uffff\u0001\u0006", "", "\u0001\u000b\u0004\uffff\u0001\u000b*\uffff\u0001\t\u001f\uffff\u0001\n", "", "", "", "", "", "", ""};
      DFA15_eot = DFA.unpackEncodedString("\f\uffff");
      DFA15_eof = DFA.unpackEncodedString("\f\uffff");
      DFA15_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\"\u0001\uffff\u0001\"\u0001\uffff\u0001\"\u0007\uffff");
      DFA15_max = DFA.unpackEncodedStringToUnsignedChars("\u0001u\u0001\uffff\u0001r\u0001\uffff\u0001r\u0007\uffff");
      DFA15_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0001\uffff\u0001\u0004\u0001\uffff\u0001\t\u0001\u0003\u0001\u0007\u0001\u0002\u0001\u0006\u0001\b\u0001\u0005");
      DFA15_special = DFA.unpackEncodedString("\f\uffff}>");
      numStates = DFA15_transitionS.length;
      DFA15_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
      }

      DFA21_transitionS = new String[]{"\u0001\u0005\u0004\uffff\u0001\u0005*\uffff\u0001\u0003\u0002\uffff\u0001\u0004\u001c\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "", "\u0001\b\u0004\uffff\u0001\b*\uffff\u0001\u0007\u001f\uffff\u0001\u0006", "", "\u0001\u000b\u0004\uffff\u0001\u000b*\uffff\u0001\t\u001f\uffff\u0001\n", "", "", "", "", "", "", ""};
      DFA21_eot = DFA.unpackEncodedString("\f\uffff");
      DFA21_eof = DFA.unpackEncodedString("\f\uffff");
      DFA21_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\"\u0001\uffff\u0001\"\u0001\uffff\u0001\"\u0007\uffff");
      DFA21_max = DFA.unpackEncodedStringToUnsignedChars("\u0001u\u0001\uffff\u0001r\u0001\uffff\u0001r\u0007\uffff");
      DFA21_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0001\uffff\u0001\u0004\u0001\uffff\u0001\t\u0001\u0003\u0001\u0007\u0001\u0002\u0001\u0006\u0001\b\u0001\u0005");
      DFA21_special = DFA.unpackEncodedString("\f\uffff}>");
      numStates = DFA21_transitionS.length;
      DFA21_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
      }

      DFA25_transitionS = new String[]{"\u0001\u0005\u0004\uffff\u0001\u0005*\uffff\u0001\u0003\u0002\uffff\u0001\u0004\u001c\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "", "\u0001\b\u0004\uffff\u0001\b*\uffff\u0001\u0007\u001f\uffff\u0001\u0006", "", "\u0001\u000b\u0004\uffff\u0001\u000b*\uffff\u0001\t\u001f\uffff\u0001\n", "", "", "", "", "", "", ""};
      DFA25_eot = DFA.unpackEncodedString("\f\uffff");
      DFA25_eof = DFA.unpackEncodedString("\f\uffff");
      DFA25_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\"\u0001\uffff\u0001\"\u0001\uffff\u0001\"\u0007\uffff");
      DFA25_max = DFA.unpackEncodedStringToUnsignedChars("\u0001u\u0001\uffff\u0001r\u0001\uffff\u0001r\u0007\uffff");
      DFA25_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0001\uffff\u0001\u0004\u0001\uffff\u0001\t\u0001\u0003\u0001\u0007\u0001\u0002\u0001\u0006\u0001\b\u0001\u0005");
      DFA25_special = DFA.unpackEncodedString("\f\uffff}>");
      numStates = DFA25_transitionS.length;
      DFA25_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA25_transition[i] = DFA.unpackEncodedString(DFA25_transitionS[i]);
      }

      DFA26_transitionS = new String[]{"\n\u0002\u0001\uffff\u0017\u0002\u0001\uffff9\u0002\u0001\u0001ﾣ\u0002", "\n\u0005\u0001\u0004\u0002\u0005\u0001\u0003\ufff2\u0005", "", "\n\u0005\u0001\u0006\u0017\u0005\u0001\uffff\uffdd\u0005", "\t\u0005\u0001\u0007\u0001\uffff\u0001\u0005\u0001\t\u0001\n\u0012\u0005\u0001\u0007\u0001\u0005\u0001\uffff\u0001\bￜ\u0005", "", "", "\t\u0005\u0001\u0007\u0001\uffff\u0001\u0005\u0001\t\u0001\n\u0012\u0005\u0001\u0007\u0001\u0005\u0001\uffff\u0001\bￜ\u0005", "\n\f\u0001\uffff\u0017\f\u0001\uffff9\f\u0001\u000bﾣ\f", "\n\u0005\u0001\u0006\u0002\u0005\u0001\n\u0014\u0005\u0001\uffff\uffdd\u0005", "\n\u0005\u0001\u0006\u0017\u0005\u0001\uffff\uffdd\u0005", "\n\u000f\u0001\u000e\u0002\u000f\u0001\r\ufff2\u000f", "\n\f\u0001\uffff\u0017\f\u0001\uffff9\f\u0001\u000bﾣ\f", "\n\f\u0001\u0010\u0017\f\u0001\uffff9\f\u0001\u000bﾣ\f", "", "\n\f\u0001\uffff\u0017\f\u0001\uffff9\f\u0001\u000bﾣ\f", ""};
      DFA26_eot = DFA.unpackEncodedString("\u0004\uffff\u0001\u0006\u0002\uffff\u0002\u0006\u0002\uffff\u0003\u0006\u0001\uffff\u0001\u0006\u0001\uffff");
      DFA26_eof = DFA.unpackEncodedString("\u0011\uffff");
      DFA26_min = DFA.unpackEncodedStringToUnsignedChars("\u0002\u0000\u0001\uffff\u0002\u0000\u0002\uffff\u0007\u0000\u0001\uffff\u0001\u0000\u0001\uffff");
      DFA26_max = DFA.unpackEncodedStringToUnsignedChars("\u0002\uffff\u0001\uffff\u0002\uffff\u0002\uffff\u0007\uffff\u0001\uffff\u0001\uffff\u0001\uffff");
      DFA26_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0002\uffff\u0001\u0001\u0001\u0003\u0007\uffff\u0001\u0001\u0001\uffff\u0001\u0001");
      DFA26_special = DFA.unpackEncodedString("\u0001\u0006\u0001\u0000\u0001\uffff\u0001\u0001\u0001\u0003\u0002\uffff\u0001\n\u0001\b\u0001\u000b\u0001\u0007\u0001\u0002\u0001\t\u0001\u0005\u0001\uffff\u0001\u0004\u0001\uffff}>");
      numStates = DFA26_transitionS.length;
      DFA26_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA26_transition[i] = DFA.unpackEncodedString(DFA26_transitionS[i]);
      }

      DFA27_transitionS = new String[]{"\n\u0002\u0001\uffff\u001c\u0002\u0001\uffff4\u0002\u0001\u0001ﾣ\u0002", "\n\u0005\u0001\u0004\u0002\u0005\u0001\u0003\ufff2\u0005", "", "\n\u0005\u0001\u0006\u001c\u0005\u0001\uffff\uffd8\u0005", "\t\u0005\u0001\u0007\u0001\uffff\u0001\u0005\u0001\t\u0001\n\u0012\u0005\u0001\u0007\u0002\u0005\u0001\b\u0003\u0005\u0001\uffff\uffd8\u0005", "", "", "\t\u0005\u0001\u0007\u0001\uffff\u0001\u0005\u0001\t\u0001\n\u0012\u0005\u0001\u0007\u0002\u0005\u0001\b\u0003\u0005\u0001\uffff\uffd8\u0005", "\n\f\u0001\uffff\u001c\f\u0001\uffff4\f\u0001\u000bﾣ\f", "\n\u0005\u0001\u0006\u0002\u0005\u0001\n\u0019\u0005\u0001\uffff\uffd8\u0005", "\n\u0005\u0001\u0006\u001c\u0005\u0001\uffff\uffd8\u0005", "\n\u000f\u0001\u000e\u0002\u000f\u0001\r\ufff2\u000f", "\n\f\u0001\uffff\u001c\f\u0001\uffff4\f\u0001\u000bﾣ\f", "\n\f\u0001\u0010\u001c\f\u0001\uffff4\f\u0001\u000bﾣ\f", "", "\n\f\u0001\uffff\u001c\f\u0001\uffff4\f\u0001\u000bﾣ\f", ""};
      DFA27_eot = DFA.unpackEncodedString("\u0004\uffff\u0001\u0006\u0002\uffff\u0002\u0006\u0002\uffff\u0003\u0006\u0001\uffff\u0001\u0006\u0001\uffff");
      DFA27_eof = DFA.unpackEncodedString("\u0011\uffff");
      DFA27_min = DFA.unpackEncodedStringToUnsignedChars("\u0002\u0000\u0001\uffff\u0002\u0000\u0002\uffff\u0007\u0000\u0001\uffff\u0001\u0000\u0001\uffff");
      DFA27_max = DFA.unpackEncodedStringToUnsignedChars("\u0002\uffff\u0001\uffff\u0002\uffff\u0002\uffff\u0007\uffff\u0001\uffff\u0001\uffff\u0001\uffff");
      DFA27_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0002\uffff\u0001\u0001\u0001\u0003\u0007\uffff\u0001\u0001\u0001\uffff\u0001\u0001");
      DFA27_special = DFA.unpackEncodedString("\u0001\u0000\u0001\u0001\u0001\uffff\u0001\u0007\u0001\u0003\u0002\uffff\u0001\u000b\u0001\b\u0001\u0004\u0001\u0002\u0001\n\u0001\t\u0001\u0006\u0001\uffff\u0001\u0005\u0001\uffff}>");
      numStates = DFA27_transitionS.length;
      DFA27_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA27_transition[i] = DFA.unpackEncodedString(DFA27_transitionS[i]);
      }

      DFA51_transitionS = new String[]{"\u0001\u0001\u0016\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "", "\n\u0003\u0001\u0001\ufff5\u0003", "\n\u0003\u0001\u0001\ufff5\u0003", ""};
      DFA51_eot = DFA.unpackEncodedString("\u0002\uffff\u0002\u0004\u0001\uffff");
      DFA51_eof = DFA.unpackEncodedString("\u0005\uffff");
      DFA51_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0001\uffff\u0002\u0000\u0001\uffff");
      DFA51_max = DFA.unpackEncodedStringToUnsignedChars("\u0001#\u0001\uffff\u0002\uffff\u0001\uffff");
      DFA51_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0002\uffff\u0001\u0002");
      DFA51_special = DFA.unpackEncodedString("\u0001\u0002\u0001\uffff\u0001\u0000\u0001\u0001\u0001\uffff}>");
      numStates = DFA51_transitionS.length;
      DFA51_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA51_transition[i] = DFA.unpackEncodedString(DFA51_transitionS[i]);
      }

      DFA52_transitionS = new String[]{"\u00016\u00014\u0001\uffff\u00013\u00014\u0012\uffff\u00015\u0001%\u00011\u00017\u0001\uffff\u0001\u001f\u0001\u001b\u00010\u0001\u000f\u0001\u0010\u0001\u0018\u0001\u0016\u0001\u0014\u0001\u0017\u0001&\u0001\u0019\u0001*\t+\u0001\u0013\u0001\u0015\u0001\u001c\u0001\u001e\u0001\u001d\u0001\uffff\u0001'\u0011/\u0001-\u0002/\u0001.\u0005/\u0001\u0011\u00012\u0001\u0012\u0001#\u0001/\u0001 \u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001/\u0001\b\u0002/\u0001\t\u0001/\u0001)\u0001(\u0001\n\u0001/\u0001\u000b\u0001/\u0001\f\u0001,\u0001/\u0001\r\u0001/\u0001\u000e\u0001/\u0001!\u0001\u001a\u0001\"\u0001$", "\u00019\u0004\uffff\u00018", "\u0001:", "\u0001;\u0002\uffff\u0001<", "\u0001=", "\u0001>\u000b\uffff\u0001?", "\u0001@\u0005\uffff\u0001B\u0002\uffff\u0001A", "\u0001C", "\u0001D\u0006\uffff\u0001E\u0001F\u0004\uffff\u0001G", "\u0001H", "\u0001I\u0010\uffff\u0001J", "\u00011\u0004\uffff\u000109\uffff\u0001K\u0003\uffff\u0001L", "\u0001M", "\u0001N\u0001O", "\u0001P", "", "", "", "", "", "", "", "\u0001Q", "\u0001S", "\u0001U\u0012\uffff\u0001V", "\u0001X\r\uffff\u0001Y", "\u0001[", "\u0001]", "\u0001a\u0001`\u0001_", "\u0001c\u0001d", "\u0001f", "\u0001h", "", "", "", "\u0001j", "", "", "\nm", "", "\u0001n", "\u0001o", "\u0001r\u0001\uffff\bt\u0002u\u000b\uffff\u0001s\u0004\uffff\u0001w\u0001\uffff\u0001v\u000b\uffff\u0001p\f\uffff\u0001s\u0004\uffff\u0001w\u0001\uffff\u0001v\u000b\uffff\u0001p", "\u0001r\u0001\uffff\nx\u000b\uffff\u0001s\u0004\uffff\u0001w\u0001\uffff\u0001v\u0018\uffff\u0001s\u0004\uffff\u0001w\u0001\uffff\u0001v", "\u00011\u0004\uffff\u00010*\uffff\u0001z\u001f\uffff\u0001y", "\u00011\u0004\uffff\u00010", "\u00011\u0004\uffff\u00010*\uffff\u0001{\u001f\uffff\u0001|", "", "\n\u007f\u0001\uffff\u001c\u007f\u0001}4\u007f\u0001~ﾣ\u007f", "\n\u0082\u0001\uffff\u0017\u0082\u0001\u00809\u0082\u0001\u0081ﾣ\u0082", "", "\u00014\u0002\uffff\u00014", "", "\u00016\u0001\u0086\u0001\uffff\u0001\u0083\u0001\u0086\u0012\uffff\u00015\u0002\uffff\u0001\u0084", "\u00016\u0001\u0086\u0001\uffff\u0001\u0083\u0001\u0086\u0012\uffff\u00015\u0002\uffff\u0001\u0084", "", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u0012/\u0001\u0088\u0007/", "\u0001\u008a", "\u0001\u008b", "\u0001\u008c", "\u0001\u008d", "\u0001\u008e\u0005\uffff\u0001\u008f", "\u0001\u0090\t\uffff\u0001\u0091", "\u0001\u0092\u0001\uffff\u0001\u0093", "\u0001\u0094", "\u0001\u0095", "\u0001\u0096", "\u0001\u0097", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001\u0099", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001\u009c", "\u0001\u009d", "\u0001\u009e", "\u0001\u009f", "\u0001 ", "\u0001¡", "\u0001¢", "\u0001£", "\u0001¤", "", "", "", "", "\u0001¥", "", "", "\u0001§", "", "", "", "", "", "", "", "", "\u0001©", "", "", "\u0001«", "", "", "", "", "", "", "", "", "\nm\u000b\uffff\u0001®\u0004\uffff\u0001w\u001a\uffff\u0001®\u0004\uffff\u0001w", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001°", "\n±\u0007\uffff\u0006±\u001a\uffff\u0006±", "", "\n³\u000b\uffff\u0001²\u0004\uffff\u0001w\u001a\uffff\u0001²\u0004\uffff\u0001w", "\u0001´\u0001\uffff\u0001´\u0002\uffff\nµ", "\u0001r\u0001\uffff\bt\u0002u\u000b\uffff\u0001s\u0004\uffff\u0001w\u0001\uffff\u0001v\u0018\uffff\u0001s\u0004\uffff\u0001w\u0001\uffff\u0001v", "\u0001r\u0001\uffff\nu\u000b\uffff\u0001s\u0004\uffff\u0001w\u001a\uffff\u0001s\u0004\uffff\u0001w", "", "", "\u0001r\u0001\uffff\nx\u000b\uffff\u0001s\u0004\uffff\u0001w\u0001\uffff\u0001v\u0018\uffff\u0001s\u0004\uffff\u0001w\u0001\uffff\u0001v", "\u00011\u0004\uffff\u00010", "\u00011\u0004\uffff\u00010", "\u00011\u0004\uffff\u00010", "\u00011\u0004\uffff\u00010", "\u0001¶", "\nº\u0001¹\u0002º\u0001¸\ufff2º", "\n\u007f\u0001\uffff\u001c\u007f\u0001·4\u007f\u0001~ﾣ\u007f", "\u0001»", "\n¾\u0001½\u0002¾\u0001¼\ufff2¾", "\n\u0082\u0001\uffff\u0017\u0082\u0001·9\u0082\u0001\u0081ﾣ\u0082", "", "", "\u0001\uffff", "", "\u0001\uffff", "\u0001¿", "", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001Á", "\u0001Â", "\u0001Ã", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001Æ", "\u0001Ç", "\u0001È", "\u0001É", "\u0001Ê", "\u0001Ë", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001Í", "", "\u0001Î", "", "", "\u0001Ï", "\u0001Ð", "\u0001Ñ", "\u0001Ò", "\u0001Ó", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001Õ", "\u0001Ö", "\u0001×", "", "", "", "", "", "", "", "", "", "\u0001Ø\u0001\uffff\u0001Ø\u0002\uffff\nÙ", "", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\n±\u0007\uffff\u0006±\u0005\uffff\u0001v\u0014\uffff\u0006±\u0005\uffff\u0001v", "\u0001Û\u0001\uffff\u0001Û\u0002\uffff\nÜ", "\n³\u000b\uffff\u0001Ý\u0004\uffff\u0001w\u001a\uffff\u0001Ý\u0004\uffff\u0001w", "\nµ", "\nµ\u0010\uffff\u0001w\u001f\uffff\u0001w", "'à\u0001Þ4à\u0001ßﾣà", "", "\n\u007f\u0001â\u001c\u007f\u0001·4\u007f\u0001~ﾣ\u007f", "\t\u007f\u0001ã\u0001\uffff\u0001\u007f\u0001å\u0001æ\u0012\u007f\u0001ã\u0002\u007f\u0001ä\u0003\u007f\u0001·4\u007f\u0001~ﾣ\u007f", "\n\u007f\u0001\uffff\u001c\u007f\u0001·4\u007f\u0001~ﾣ\u007f", "\"é\u0001ç9é\u0001èﾣé", "\n\u0082\u0001â\u0017\u0082\u0001·9\u0082\u0001\u0081ﾣ\u0082", "\t\u0082\u0001ê\u0001\uffff\u0001\u0082\u0001ì\u0001í\u0012\u0082\u0001ê\u0001\u0082\u0001·\u0001ë8\u0082\u0001\u0081ﾣ\u0082", "\n\u0082\u0001\uffff\u0017\u0082\u0001·9\u0082\u0001\u0081ﾣ\u0082", "\u0001î", "", "\u0001ï", "\u0001ð", "\u0001ñ", "", "", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001ô", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001ö", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "", "\u0001ø", "\u0001ù", "\u0001ú", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001ü", "\u0001ý", "\u0001þ", "", "\u0001ÿ", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001ā", "\nÙ", "\nÙ\u0010\uffff\u0001w\u001f\uffff\u0001w", "", "\nÜ", "\nÜ\u0010\uffff\u0001w\u001f\uffff\u0001w", "\u0001Ă\u0001\uffff\u0001Ă\u0002\uffff\nă", "'à\u0001Ą4à\u0001ßﾣà", "\u0000ą", "'à\u0001Þ4à\u0001ßﾣà", "", "", "\t\u007f\u0001ã\u0001\uffff\u0001\u007f\u0001å\u0001æ\u0012\u007f\u0001ã\u0002\u007f\u0001ä\u0003\u007f\u0001·4\u007f\u0001~ﾣ\u007f", "\nĈ\u0001\uffff\u001cĈ\u0001Ć4Ĉ\u0001ćﾣĈ", "\n\u007f\u0001â\u0002\u007f\u0001æ\u0019\u007f\u0001·4\u007f\u0001~ﾣ\u007f", "\n\u007f\u0001â\u001c\u007f\u0001·4\u007f\u0001~ﾣ\u007f", "\"é\u0001ĉ9é\u0001èﾣé", "\u0000Ċ", "\"é\u0001ç9é\u0001èﾣé", "\t\u0082\u0001ê\u0001\uffff\u0001\u0082\u0001ì\u0001í\u0012\u0082\u0001ê\u0001\u0082\u0001·\u0001ë8\u0082\u0001\u0081ﾣ\u0082", "\nč\u0001\uffff\u0017č\u0001ċ9č\u0001Čﾣč", "\n\u0082\u0001â\u0002\u0082\u0001í\u0014\u0082\u0001·9\u0082\u0001\u0081ﾣ\u0082", "\n\u0082\u0001â\u0017\u0082\u0001·9\u0082\u0001\u0081ﾣ\u0082", "\u0001Ď", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001đ", "", "", "\u0001Ē", "", "\u0001ē", "", "\u0001Ĕ", "\u0001ĕ", "\u0001Ė", "", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001ę", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\nă", "\nă\u0010\uffff\u0001w\u001f\uffff\u0001w", "'à\u0001Ĝ4à\u0001ßﾣà", "'à\u0001Þ4à\u0001ßﾣà", "\u0000â", "\nğ\u0001Ğ\u0002ğ\u0001ĝ\ufff2ğ", "\nĈ\u0001\uffff\u001cĈ\u0001Ć4Ĉ\u0001ćﾣĈ", "\"é\u0001Ġ9é\u0001èﾣé", "\"é\u0001ç9é\u0001èﾣé", "\u0000â", "\nģ\u0001Ģ\u0002ģ\u0001ġ\ufff2ģ", "\nč\u0001\uffff\u0017č\u0001ċ9č\u0001Čﾣč", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "", "", "\u0001ĥ", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\u0001ħ", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "", "", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "", "", "\u0000á", "\nĈ\u0001\uffff\u001cĈ\u0001Ć4Ĉ\u0001ćﾣĈ", "\t\u007f\u0001ã\u0001\uffff\u0001\u007f\u0001å\u0001æ\u0012\u007f\u0001ã\u0002\u007f\u0001ä\u0003\u007f\u0001·4\u007f\u0001~ﾣ\u007f", "\nĈ\u0001\uffff\u001cĈ\u0001Ć4Ĉ\u0001ćﾣĈ", "\u0000á", "\nč\u0001\uffff\u0017č\u0001ċ9č\u0001Čﾣč", "\t\u0082\u0001ê\u0001\uffff\u0001\u0082\u0001ì\u0001í\u0012\u0082\u0001ê\u0001\u0082\u0001·\u0001ë8\u0082\u0001\u0081ﾣ\u0082", "\nč\u0001\uffff\u0017č\u0001ċ9č\u0001Čﾣč", "", "\u0001Ĭ", "", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "", "", "", "", "\n/\u0007\uffff\u001a/\u0004\uffff\u0001/\u0001\uffff\u001a/", "", ""};
      DFA52_eot = DFA.unpackEncodedString("\u0001\uffff\u000e/\u0007\uffff\u0001R\u0001T\u0001W\u0001Z\u0001\\\u0001^\u0001b\u0001e\u0001g\u0001i\u0003\uffff\u0001k\u0002\uffff\u0001l\u0001\uffff\u0002/\u0002q\u0003/\u0004\uffff\u0001\u0083\u0001\uffff\u0001\u0085\u0001\u0087\u0001\uffff\u0001\u0089\u000b/\u0001\u0098\u0001/\u0001\u009a\u0001\u009b\t/\u0004\uffff\u0001¦\u0002\uffff\u0001¨\b\uffff\u0001ª\u0002\uffff\u0001¬\b\uffff\u0001\u00ad\u0001¯\u0001/\u0002\uffff\u0001\u00ad\u0001\uffff\u0001q\u0003\uffff\u0001q\u0004/\u0001·\u0002\uffff\u0001·\u0007\uffff\u0001/\u0001\uffff\u0001À\u0003/\u0001Ä\u0001Å\u0006/\u0001Ì\u0001/\u0001\uffff\u0001/\u0002\uffff\u0005/\u0001Ô\u0003/\u000b\uffff\u0001Ú\u0001q\u0001\uffff\u0001\u00ad\u0001\uffff\u0001\u00ad\u0001á\u0002\uffff\u0001â\u0001\uffff\u0001á\u0001\uffff\u0001â\u0001\uffff\u0001/\u0001\uffff\u0003/\u0002\uffff\u0001ò\u0001ó\u0001/\u0001õ\u0001/\u0001÷\u0001\uffff\u0003/\u0001û\u0003/\u0001\uffff\u0001/\u0001Ā\u0001/\u0001\uffff\u0001\u00ad\u0002\uffff\u0001\u00ad\u0001\uffff\u0003á\u0002\uffff\u0002â\u0002\uffff\u0003á\u0002â\u0002\uffff\u0001/\u0001ď\u0001Đ\u0001/\u0002\uffff\u0001/\u0001\uffff\u0001/\u0001\uffff\u0003/\u0001\uffff\u0001ė\u0001Ę\u0001/\u0001Ě\u0001\uffff\u0001ě\u0001\uffff\u0001\u00ad\u0002á\u0001·\u0002â\u0002á\u0001·\u0002â\u0001Ĥ\u0002\uffff\u0001/\u0001Ħ\u0001/\u0001Ĩ\u0001ĩ\u0001Ī\u0002\uffff\u0001ī\u0002\uffff\u0001·\u0003â\u0001·\u0003â\u0001\uffff\u0001/\u0001\uffff\u0001ĭ\u0004\uffff\u0001Į\u0002\uffff");
      DFA52_eof = DFA.unpackEncodedString("į\uffff");
      DFA52_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0001n\u0001r\u0001l\u0001e\u0001l\u0001i\u0001l\u0001f\u0002a\u0001\"\u0001r\u0001h\u0001i\u0007\uffff\u0002=\u0001*\u0001/\u0002=\u0001<\u0003=\u0003\uffff\u0001=\u0002\uffff\u00010\u0001\uffff\u0001r\u0001o\u0002.\u0003\"\u0001\uffff\u0002\u0000\u0001\uffff\u0001\n\u0001\uffff\u0002\t\u0001\uffff\u00010\u0001d\u0001e\u0001a\u0001n\u0001f\u0001i\u0001c\u0001n\u0001o\u0001r\u0001o\u00010\u0001p\u00020\u0001m\u0001s\u0002i\u0001t\u0001y\u0001i\u0001t\u0001e\u0004\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u00020\u0001t\u00010\u0001\uffff\u00010\u0001+\u0002.\u0002\uffff\u0001.\u0004\"\u0001'\u0002\u0000\u0001\"\u0002\u0000\u0002\uffff\u0001\u0000\u0001\uffff\u0001\u0000\u0001e\u0001\uffff\u00010\u0001a\u0001s\u0001t\u00020\u0001f\u0002e\u0001c\u0001a\u0001m\u00010\u0001b\u0001\uffff\u0001o\u0002\uffff\u0001b\u0001s\u0001n\u0001s\u0001u\u00010\u0001l\u0001h\u0001l\t\uffff\u0001+\u0001\uffff\u00020\u0001+\u00030\u0001\u0000\u0001\uffff\u0007\u0000\u0001r\u0001\uffff\u0001k\u0001s\u0001i\u0002\uffff\u00020\u0001p\u00010\u0001l\u00010\u0001\uffff\u0001a\u0001r\u0001d\u00010\u0001t\u0001e\u0001r\u0001\uffff\u0001e\u00010\u0001d\u00020\u0001\uffff\u00020\u0001+\u0003\u0000\u0002\uffff\u000b\u0000\u0001t\u00020\u0001n\u0002\uffff\u0001t\u0001\uffff\u0001l\u0001\uffff\u0001l\u0001t\u0001a\u0001\uffff\u00020\u0001n\u00010\u0001\uffff\u00030\n\u0000\u00010\u0002\uffff\u0001u\u00010\u0001y\u00030\u0002\uffff\u00010\u0002\uffff\b\u0000\u0001\uffff\u0001e\u0001\uffff\u00010\u0004\uffff\u00010\u0002\uffff");
      DFA52_max = DFA.unpackEncodedStringToUnsignedChars("\u0001~\u0001s\u0001r\u0001o\u0001e\u0001x\u0001r\u0001l\u0001s\u0001a\u0001r\u0001e\u0001r\u0002i\u0007\uffff\u0006=\u0002>\u0002=\u0003\uffff\u0001=\u0002\uffff\u00019\u0001\uffff\u0001r\u0001o\u0001x\u0001l\u0001r\u0001'\u0001r\u0001\uffff\u0002\uffff\u0001\uffff\u0001\r\u0001\uffff\u0002#\u0001\uffff\u0001z\u0001d\u0001e\u0001a\u0001n\u0001l\u0001s\u0001e\u0001n\u0001o\u0001r\u0001o\u0001z\u0001p\u0002z\u0001m\u0001s\u0002i\u0001t\u0001y\u0001i\u0001t\u0001e\u0004\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u0001=\u0002\uffff\u0001=\b\uffff\u0001j\u0001z\u0001t\u0001f\u0001\uffff\u0001j\u00019\u0001l\u0001j\u0002\uffff\u0001l\u0005'\u0002\uffff\u0001\"\u0002\uffff\u0002\uffff\u0001\u0000\u0001\uffff\u0001\u0000\u0001e\u0001\uffff\u0001z\u0001a\u0001s\u0001t\u0002z\u0001f\u0002e\u0001c\u0001a\u0001m\u0001z\u0001b\u0001\uffff\u0001o\u0002\uffff\u0001b\u0001s\u0001n\u0001s\u0001u\u0001z\u0001l\u0001h\u0001l\t\uffff\u00019\u0001\uffff\u0001z\u0001l\u00019\u0001j\u00019\u0001j\u0001\uffff\u0001\uffff\u0007\uffff\u0001r\u0001\uffff\u0001k\u0001s\u0001i\u0002\uffff\u0002z\u0001p\u0001z\u0001l\u0001z\u0001\uffff\u0001a\u0001r\u0001d\u0001z\u0001t\u0001e\u0001r\u0001\uffff\u0001e\u0001z\u0001d\u00019\u0001j\u0001\uffff\u00019\u0001j\u00019\u0003\uffff\u0002\uffff\u000b\uffff\u0001t\u0002z\u0001n\u0002\uffff\u0001t\u0001\uffff\u0001l\u0001\uffff\u0001l\u0001t\u0001a\u0001\uffff\u0002z\u0001n\u0001z\u0001\uffff\u0001z\u00019\u0001j\n\uffff\u0001z\u0002\uffff\u0001u\u0001z\u0001y\u0003z\u0002\uffff\u0001z\u0002\uffff\b\uffff\u0001\uffff\u0001e\u0001\uffff\u0001z\u0004\uffff\u0001z\u0002\uffff");
      DFA52_accept = DFA.unpackEncodedString("\u000f\uffff\u0001\u001d\u0001\u001e\u0001\u001f\u0001 \u0001!\u0001\"\u0001#\n\uffff\u0001.\u0001/\u00010\u0001\uffff\u00012\u00014\u0001\uffff\u0001I\u0007\uffff\u0001Q\u0002\uffff\u0001U\u0001\uffff\u0001V\u0002\uffff\u0001Y\u0019\uffff\u0001:\u0001$\u0001;\u0001%\u0001\uffff\u0001=\u0001&\u0001\uffff\u0001?\u0001'\u0001@\u0001(\u0001B\u0001)\u00015\u00016\u0001\uffff\u0001*\u00018\u0001\uffff\u0001+\u00013\u0001,\u0001A\u0001-\u0001C\u00011\u0001H\u0004\uffff\u0001O\u0004\uffff\u0001N\u0001P\u000b\uffff\u0001W\u0001Y\u0001\uffff\u0001X\u0002\uffff\u0001\u0001\u000e\uffff\u0001\u000f\u0001\uffff\u0001\u0011\u0001\u0012\t\uffff\u0001F\u0001<\u0001G\u0001>\u0001D\u00017\u0001E\u00019\u0001M\u0001\uffff\u0001K\u0007\uffff\u0001R\b\uffff\u0001J\u0003\uffff\u0001\u0006\u0001\u0007\u0006\uffff\u0001\r\u0007\uffff\u0001\u0019\u0005\uffff\u0001L\u0006\uffff\u0001S\u0001T\u000f\uffff\u0001\b\u0001\u0014\u0001\uffff\u0001\n\u0001\uffff\u0001\f\u0003\uffff\u0001\u0015\u0004\uffff\u0001\u001b\u000e\uffff\u0001\u0003\u0001\u0004\u0006\uffff\u0001\u0016\u0001\u0017\u0001\uffff\u0001\u001a\u0001\u001c\b\uffff\u0001\u0002\u0001\uffff\u0001\t\u0001\uffff\u0001\u000e\u0001\u0010\u0001\u0013\u0001\u0018\u0001\uffff\u0001\u000b\u0001\u0005");
      DFA52_special = DFA.unpackEncodedString("\u0001-/\uffff\u0001$\u0001\u000f\u0001\uffff\u0001\u000b\u0001\uffff\u0001\f\u0001\rG\uffff\u0001\u0000\u0001\u0006\u0001\uffff\u0001\u0001\u0001\u001c\u0002\uffff\u0001.\u0001\uffff\u0001/.\uffff\u0001\u0002\u0001\uffff\u0001\u0005\u0001\t\u0001\u0003\u0001 \u0001\u001b\u0001\u001e\u0001\u001a\u001f\uffff\u0001\u0016\u0001)\u0001,\u0002\uffff\u0001\u0011\u0001#\u0001\u0018\u0001\n\u0001\b\u00013\u0001\u0015\u0001*\u0001\u000e\u0001\u0004\u0001!\u0016\uffff\u0001\u001d\u0001\u0014\u00010\u0001\"\u0001'\u0001\u0019\u0001+\u0001(\u0001\u001f\u0001\u0013\u000e\uffff\u00011\u0001&\u00012\u0001%\u0001\u0007\u0001\u0012\u0001\u0017\u0001\u0010\u000b\uffff}>");
      numStates = DFA52_transitionS.length;
      DFA52_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA52_transition[i] = DFA.unpackEncodedString(DFA52_transitionS[i]);
      }

   }

   class DFA52 extends DFA {
      public DFA52(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 52;
         this.eot = PythonPartialLexer.DFA52_eot;
         this.eof = PythonPartialLexer.DFA52_eof;
         this.min = PythonPartialLexer.DFA52_min;
         this.max = PythonPartialLexer.DFA52_max;
         this.accept = PythonPartialLexer.DFA52_accept;
         this.special = PythonPartialLexer.DFA52_special;
         this.transition = PythonPartialLexer.DFA52_transition;
      }

      public String getDescription() {
         return "1:1: Tokens : ( AS | ASSERT | BREAK | CLASS | CONTINUE | DEF | DELETE | ELIF | EXCEPT | EXEC | FINALLY | FROM | FOR | GLOBAL | IF | IMPORT | IN | IS | LAMBDA | ORELSE | PASS | PRINT | RAISE | RETURN | TRY | WHILE | WITH | YIELD | LPAREN | RPAREN | LBRACK | RBRACK | COLON | COMMA | SEMI | PLUS | MINUS | STAR | SLASH | VBAR | AMPER | LESS | GREATER | ASSIGN | PERCENT | BACKQUOTE | LCURLY | RCURLY | CIRCUMFLEX | TILDE | EQUAL | NOTEQUAL | ALT_NOTEQUAL | LESSEQUAL | LEFTSHIFT | GREATEREQUAL | RIGHTSHIFT | PLUSEQUAL | MINUSEQUAL | DOUBLESTAR | STAREQUAL | DOUBLESLASH | SLASHEQUAL | VBAREQUAL | PERCENTEQUAL | AMPEREQUAL | CIRCUMFLEXEQUAL | LEFTSHIFTEQUAL | RIGHTSHIFTEQUAL | DOUBLESTAREQUAL | DOUBLESLASHEQUAL | DOT | AT | AND | OR | NOT | FLOAT | LONGINT | INT | COMPLEX | NAME | STRING | TRISTRINGPART | STRINGPART | CONTINUED_LINE | NEWLINE | WS | LEADING_WS | COMMENT );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         boolean sx;
         switch (s) {
            case 0:
               int LA52_126 = _input.LA(1);
               s = -1;
               if (LA52_126 == 13) {
                  s = 184;
               } else if (LA52_126 == 10) {
                  s = 185;
               } else if (LA52_126 >= 0 && LA52_126 <= 9 || LA52_126 >= 11 && LA52_126 <= 12 || LA52_126 >= 14 && LA52_126 <= 65535) {
                  s = 186;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA52_129 = _input.LA(1);
               s = -1;
               if (LA52_129 == 13) {
                  s = 188;
               } else if (LA52_129 == 10) {
                  s = 189;
               } else if (LA52_129 >= 0 && LA52_129 <= 9 || LA52_129 >= 11 && LA52_129 <= 12 || LA52_129 >= 14 && LA52_129 <= 65535) {
                  s = 190;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA52_182 = _input.LA(1);
               sx = true;
               if (LA52_182 == 39) {
                  s = 222;
               } else if (LA52_182 == 92) {
                  s = 223;
               } else if ((LA52_182 < 0 || LA52_182 > 38) && (LA52_182 < 40 || LA52_182 > 91) && (LA52_182 < 93 || LA52_182 > 65535)) {
                  s = 225;
               } else {
                  s = 224;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA52_186 = _input.LA(1);
               s = -1;
               if (LA52_186 == 39) {
                  s = 183;
               } else if (LA52_186 == 92) {
                  s = 126;
               } else if (LA52_186 >= 0 && LA52_186 <= 9 || LA52_186 >= 11 && LA52_186 <= 38 || LA52_186 >= 40 && LA52_186 <= 91 || LA52_186 >= 93 && LA52_186 <= 65535) {
                  s = 127;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA52_236 = _input.LA(1);
               s = -1;
               if (LA52_236 == 34) {
                  s = 183;
               } else if (LA52_236 == 92) {
                  s = 129;
               } else if (LA52_236 == 13) {
                  s = 237;
               } else if ((LA52_236 < 0 || LA52_236 > 9) && (LA52_236 < 11 || LA52_236 > 12) && (LA52_236 < 14 || LA52_236 > 33) && (LA52_236 < 35 || LA52_236 > 91) && (LA52_236 < 93 || LA52_236 > 65535)) {
                  if (LA52_236 == 10) {
                     s = 226;
                  }
               } else {
                  s = 130;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA52_184 = _input.LA(1);
               s = -1;
               if (LA52_184 == 39) {
                  s = 183;
               } else if (LA52_184 == 92) {
                  s = 126;
               } else if (LA52_184 >= 0 && LA52_184 <= 9 || LA52_184 >= 11 && LA52_184 <= 38 || LA52_184 >= 40 && LA52_184 <= 91 || LA52_184 >= 93 && LA52_184 <= 65535) {
                  s = 127;
               } else if (LA52_184 == 10) {
                  s = 226;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA52_127 = _input.LA(1);
               s = -1;
               if (LA52_127 == 39) {
                  s = 183;
               } else if (LA52_127 == 92) {
                  s = 126;
               } else if (LA52_127 >= 0 && LA52_127 <= 9 || LA52_127 >= 11 && LA52_127 <= 38 || LA52_127 >= 40 && LA52_127 <= 91 || LA52_127 >= 93 && LA52_127 <= 65535) {
                  s = 127;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA52_288 = _input.LA(1);
               sx = true;
               if (LA52_288 >= 0 && LA52_288 <= 65535) {
                  s = 225;
               } else {
                  s = 183;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA52_231 = _input.LA(1);
               sx = true;
               if (LA52_231 == 34) {
                  s = 265;
               } else if (LA52_231 == 92) {
                  s = 232;
               } else if ((LA52_231 < 0 || LA52_231 > 33) && (LA52_231 < 35 || LA52_231 > 91) && (LA52_231 < 93 || LA52_231 > 65535)) {
                  s = 225;
               } else {
                  s = 233;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA52_185 = _input.LA(1);
               sx = true;
               if (LA52_185 != 9 && LA52_185 != 32) {
                  if (LA52_185 == 35) {
                     s = 228;
                  } else if (LA52_185 == 12) {
                     s = 229;
                  } else if (LA52_185 == 13) {
                     s = 230;
                  } else if (LA52_185 == 39) {
                     s = 183;
                  } else if (LA52_185 == 92) {
                     s = 126;
                  } else if ((LA52_185 < 0 || LA52_185 > 8) && LA52_185 != 11 && (LA52_185 < 14 || LA52_185 > 31) && (LA52_185 < 33 || LA52_185 > 34) && (LA52_185 < 36 || LA52_185 > 38) && (LA52_185 < 40 || LA52_185 > 91) && (LA52_185 < 93 || LA52_185 > 65535)) {
                     s = 226;
                  } else {
                     s = 127;
                  }
               } else {
                  s = 227;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA52_230 = _input.LA(1);
               s = -1;
               if (LA52_230 == 39) {
                  s = 183;
               } else if (LA52_230 == 92) {
                  s = 126;
               } else if (LA52_230 >= 0 && LA52_230 <= 9 || LA52_230 >= 11 && LA52_230 <= 38 || LA52_230 >= 40 && LA52_230 <= 91 || LA52_230 >= 93 && LA52_230 <= 65535) {
                  s = 127;
               } else if (LA52_230 == 10) {
                  s = 226;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA52_51 = _input.LA(1);
               int index52_51 = _input.index();
               _input.rewind();
               sx = true;
               if (LA52_51 != 10 && LA52_51 != 13) {
                  s = 131;
               } else {
                  s = 52;
               }

               _input.seek(index52_51);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA52_53 = _input.LA(1);
               int index52_53 = _input.index();
               _input.rewind();
               sx = true;
               if (LA52_53 == 35 && PythonPartialLexer.this.startPos == 0) {
                  s = 132;
               } else if (LA52_53 != 32 || PythonPartialLexer.this.startPos != 0 && PythonPartialLexer.this.startPos <= 0) {
                  if (LA52_53 == 12 && PythonPartialLexer.this.startPos > 0) {
                     s = 131;
                  } else if ((LA52_53 == 10 || LA52_53 == 13) && PythonPartialLexer.this.startPos == 0) {
                     s = 134;
                  } else if (LA52_53 != 9 || PythonPartialLexer.this.startPos != 0 && PythonPartialLexer.this.startPos <= 0) {
                     s = 133;
                  } else {
                     s = 54;
                  }
               } else {
                  s = 53;
               }

               _input.seek(index52_53);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA52_54 = _input.LA(1);
               int index52_54 = _input.index();
               _input.rewind();
               sx = true;
               if (LA52_54 == 35 && PythonPartialLexer.this.startPos == 0) {
                  s = 132;
               } else if (LA52_54 != 32 || PythonPartialLexer.this.startPos != 0 && PythonPartialLexer.this.startPos <= 0) {
                  if (LA52_54 == 12 && PythonPartialLexer.this.startPos > 0) {
                     s = 131;
                  } else if ((LA52_54 == 10 || LA52_54 == 13) && PythonPartialLexer.this.startPos == 0) {
                     s = 134;
                  } else if (LA52_54 != 9 || PythonPartialLexer.this.startPos != 0 && PythonPartialLexer.this.startPos <= 0) {
                     s = 135;
                  } else {
                     s = 54;
                  }
               } else {
                  s = 53;
               }

               _input.seek(index52_54);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA52_235 = _input.LA(1);
               sx = true;
               if (LA52_235 == 34) {
                  s = 267;
               } else if (LA52_235 == 92) {
                  s = 268;
               } else if ((LA52_235 < 0 || LA52_235 > 9) && (LA52_235 < 11 || LA52_235 > 33) && (LA52_235 < 35 || LA52_235 > 91) && (LA52_235 < 93 || LA52_235 > 65535)) {
                  s = 226;
               } else {
                  s = 269;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA52_49 = _input.LA(1);
               s = -1;
               if (LA52_49 == 34) {
                  s = 128;
               } else if (LA52_49 == 92) {
                  s = 129;
               } else if (LA52_49 >= 0 && LA52_49 <= 9 || LA52_49 >= 11 && LA52_49 <= 33 || LA52_49 >= 35 && LA52_49 <= 91 || LA52_49 >= 93 && LA52_49 <= 65535) {
                  s = 130;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA52_291 = _input.LA(1);
               sx = true;
               if (LA52_291 == 34) {
                  s = 267;
               } else if (LA52_291 == 92) {
                  s = 268;
               } else if ((LA52_291 < 0 || LA52_291 > 9) && (LA52_291 < 11 || LA52_291 > 33) && (LA52_291 < 35 || LA52_291 > 91) && (LA52_291 < 93 || LA52_291 > 65535)) {
                  s = 226;
               } else {
                  s = 269;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 17:
               int LA52_227 = _input.LA(1);
               sx = true;
               if (LA52_227 == 35) {
                  s = 228;
               } else if (LA52_227 != 9 && LA52_227 != 32) {
                  if (LA52_227 == 39) {
                     s = 183;
                  } else if (LA52_227 == 92) {
                     s = 126;
                  } else if (LA52_227 == 12) {
                     s = 229;
                  } else if (LA52_227 == 13) {
                     s = 230;
                  } else if ((LA52_227 < 0 || LA52_227 > 8) && LA52_227 != 11 && (LA52_227 < 14 || LA52_227 > 31) && (LA52_227 < 33 || LA52_227 > 34) && (LA52_227 < 36 || LA52_227 > 38) && (LA52_227 < 40 || LA52_227 > 91) && (LA52_227 < 93 || LA52_227 > 65535)) {
                     s = 226;
                  } else {
                     s = 127;
                  }
               } else {
                  s = 227;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 18:
               int LA52_289 = _input.LA(1);
               sx = true;
               if (LA52_289 == 34) {
                  s = 267;
               } else if (LA52_289 == 92) {
                  s = 268;
               } else if ((LA52_289 < 0 || LA52_289 > 9) && (LA52_289 < 11 || LA52_289 > 33) && (LA52_289 < 35 || LA52_289 > 91) && (LA52_289 < 93 || LA52_289 > 65535)) {
                  s = 226;
               } else {
                  s = 269;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 19:
               int LA52_269 = _input.LA(1);
               sx = true;
               if (LA52_269 == 34) {
                  s = 267;
               } else if (LA52_269 == 92) {
                  s = 268;
               } else if ((LA52_269 < 0 || LA52_269 > 9) && (LA52_269 < 11 || LA52_269 > 33) && (LA52_269 < 35 || LA52_269 > 91) && (LA52_269 < 93 || LA52_269 > 65535)) {
                  s = 226;
               } else {
                  s = 269;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 20:
               int LA52_261 = _input.LA(1);
               sx = true;
               if (LA52_261 == 39) {
                  s = 222;
               } else if (LA52_261 == 92) {
                  s = 223;
               } else if ((LA52_261 < 0 || LA52_261 > 38) && (LA52_261 < 40 || LA52_261 > 91) && (LA52_261 < 93 || LA52_261 > 65535)) {
                  s = 225;
               } else {
                  s = 224;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 21:
               int LA52_233 = _input.LA(1);
               sx = true;
               if (LA52_233 == 34) {
                  s = 231;
               } else if (LA52_233 == 92) {
                  s = 232;
               } else if ((LA52_233 < 0 || LA52_233 > 33) && (LA52_233 < 35 || LA52_233 > 91) && (LA52_233 < 93 || LA52_233 > 65535)) {
                  s = 225;
               } else {
                  s = 233;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 22:
               int LA52_222 = _input.LA(1);
               sx = true;
               if (LA52_222 == 39) {
                  s = 260;
               } else if (LA52_222 == 92) {
                  s = 223;
               } else if ((LA52_222 < 0 || LA52_222 > 38) && (LA52_222 < 40 || LA52_222 > 91) && (LA52_222 < 93 || LA52_222 > 65535)) {
                  s = 225;
               } else {
                  s = 224;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 23:
               int LA52_290 = _input.LA(1);
               sx = true;
               if (LA52_290 != 9 && LA52_290 != 32) {
                  if (LA52_290 == 35) {
                     s = 235;
                  } else if (LA52_290 == 12) {
                     s = 236;
                  } else if (LA52_290 == 13) {
                     s = 237;
                  } else if (LA52_290 == 34) {
                     s = 183;
                  } else if (LA52_290 == 92) {
                     s = 129;
                  } else if (LA52_290 >= 0 && LA52_290 <= 8 || LA52_290 == 11 || LA52_290 >= 14 && LA52_290 <= 31 || LA52_290 == 33 || LA52_290 >= 36 && LA52_290 <= 91 || LA52_290 >= 93 && LA52_290 <= 65535) {
                     s = 130;
                  } else {
                     s = 226;
                  }
               } else {
                  s = 234;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 24:
               int LA52_229 = _input.LA(1);
               s = -1;
               if (LA52_229 == 39) {
                  s = 183;
               } else if (LA52_229 == 92) {
                  s = 126;
               } else if (LA52_229 == 13) {
                  s = 230;
               } else if ((LA52_229 < 0 || LA52_229 > 9) && (LA52_229 < 11 || LA52_229 > 12) && (LA52_229 < 14 || LA52_229 > 38) && (LA52_229 < 40 || LA52_229 > 91) && (LA52_229 < 93 || LA52_229 > 65535)) {
                  if (LA52_229 == 10) {
                     s = 226;
                  }
               } else {
                  s = 127;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 25:
               int LA52_265 = _input.LA(1);
               sx = true;
               if (LA52_265 == 34) {
                  s = 288;
               } else if (LA52_265 == 92) {
                  s = 232;
               } else if ((LA52_265 < 0 || LA52_265 > 33) && (LA52_265 < 35 || LA52_265 > 91) && (LA52_265 < 93 || LA52_265 > 65535)) {
                  s = 225;
               } else {
                  s = 233;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 26:
               int LA52_190 = _input.LA(1);
               s = -1;
               if (LA52_190 == 34) {
                  s = 183;
               } else if (LA52_190 == 92) {
                  s = 129;
               } else if (LA52_190 >= 0 && LA52_190 <= 9 || LA52_190 >= 11 && LA52_190 <= 33 || LA52_190 >= 35 && LA52_190 <= 91 || LA52_190 >= 93 && LA52_190 <= 65535) {
                  s = 130;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 27:
               int LA52_188 = _input.LA(1);
               s = -1;
               if (LA52_188 == 34) {
                  s = 183;
               } else if (LA52_188 == 92) {
                  s = 129;
               } else if ((LA52_188 < 0 || LA52_188 > 9) && (LA52_188 < 11 || LA52_188 > 33) && (LA52_188 < 35 || LA52_188 > 91) && (LA52_188 < 93 || LA52_188 > 65535)) {
                  if (LA52_188 == 10) {
                     s = 226;
                  }
               } else {
                  s = 130;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 28:
               int LA52_130 = _input.LA(1);
               s = -1;
               if (LA52_130 == 34) {
                  s = 183;
               } else if (LA52_130 == 92) {
                  s = 129;
               } else if (LA52_130 >= 0 && LA52_130 <= 9 || LA52_130 >= 11 && LA52_130 <= 33 || LA52_130 >= 35 && LA52_130 <= 91 || LA52_130 >= 93 && LA52_130 <= 65535) {
                  s = 130;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 29:
               int LA52_260 = _input.LA(1);
               sx = true;
               if (LA52_260 == 39) {
                  s = 284;
               } else if (LA52_260 == 92) {
                  s = 223;
               } else if (LA52_260 >= 0 && LA52_260 <= 38 || LA52_260 >= 40 && LA52_260 <= 91 || LA52_260 >= 93 && LA52_260 <= 65535) {
                  s = 224;
               } else {
                  s = 225;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 30:
               int LA52_189 = _input.LA(1);
               sx = true;
               if (LA52_189 != 9 && LA52_189 != 32) {
                  if (LA52_189 == 35) {
                     s = 235;
                  } else if (LA52_189 == 12) {
                     s = 236;
                  } else if (LA52_189 == 13) {
                     s = 237;
                  } else if (LA52_189 == 34) {
                     s = 183;
                  } else if (LA52_189 == 92) {
                     s = 129;
                  } else if ((LA52_189 < 0 || LA52_189 > 8) && LA52_189 != 11 && (LA52_189 < 14 || LA52_189 > 31) && LA52_189 != 33 && (LA52_189 < 36 || LA52_189 > 91) && (LA52_189 < 93 || LA52_189 > 65535)) {
                     s = 226;
                  } else {
                     s = 130;
                  }
               } else {
                  s = 234;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 31:
               int LA52_268 = _input.LA(1);
               sx = true;
               if (LA52_268 == 13) {
                  s = 289;
               } else if (LA52_268 == 10) {
                  s = 290;
               } else if ((LA52_268 < 0 || LA52_268 > 9) && (LA52_268 < 11 || LA52_268 > 12) && (LA52_268 < 14 || LA52_268 > 65535)) {
                  s = 226;
               } else {
                  s = 291;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 32:
               int LA52_187 = _input.LA(1);
               sx = true;
               if (LA52_187 == 34) {
                  s = 231;
               } else if (LA52_187 == 92) {
                  s = 232;
               } else if ((LA52_187 < 0 || LA52_187 > 33) && (LA52_187 < 35 || LA52_187 > 91) && (LA52_187 < 93 || LA52_187 > 65535)) {
                  s = 225;
               } else {
                  s = 233;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 33:
               int LA52_237 = _input.LA(1);
               s = -1;
               if (LA52_237 == 34) {
                  s = 183;
               } else if (LA52_237 == 92) {
                  s = 129;
               } else if ((LA52_237 < 0 || LA52_237 > 9) && (LA52_237 < 11 || LA52_237 > 33) && (LA52_237 < 35 || LA52_237 > 91) && (LA52_237 < 93 || LA52_237 > 65535)) {
                  if (LA52_237 == 10) {
                     s = 226;
                  }
               } else {
                  s = 130;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 34:
               int LA52_263 = _input.LA(1);
               sx = true;
               if (LA52_263 == 13) {
                  s = 285;
               } else if (LA52_263 == 10) {
                  s = 286;
               } else if ((LA52_263 < 0 || LA52_263 > 9) && (LA52_263 < 11 || LA52_263 > 12) && (LA52_263 < 14 || LA52_263 > 65535)) {
                  s = 226;
               } else {
                  s = 287;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 35:
               int LA52_228 = _input.LA(1);
               sx = true;
               if (LA52_228 == 39) {
                  s = 262;
               } else if (LA52_228 == 92) {
                  s = 263;
               } else if ((LA52_228 < 0 || LA52_228 > 9) && (LA52_228 < 11 || LA52_228 > 38) && (LA52_228 < 40 || LA52_228 > 91) && (LA52_228 < 93 || LA52_228 > 65535)) {
                  s = 226;
               } else {
                  s = 264;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 36:
               int LA52_48 = _input.LA(1);
               s = -1;
               if (LA52_48 == 39) {
                  s = 125;
               } else if (LA52_48 == 92) {
                  s = 126;
               } else if (LA52_48 >= 0 && LA52_48 <= 9 || LA52_48 >= 11 && LA52_48 <= 38 || LA52_48 >= 40 && LA52_48 <= 91 || LA52_48 >= 93 && LA52_48 <= 65535) {
                  s = 127;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 37:
               int LA52_287 = _input.LA(1);
               sx = true;
               if (LA52_287 == 39) {
                  s = 262;
               } else if (LA52_287 == 92) {
                  s = 263;
               } else if ((LA52_287 < 0 || LA52_287 > 9) && (LA52_287 < 11 || LA52_287 > 38) && (LA52_287 < 40 || LA52_287 > 91) && (LA52_287 < 93 || LA52_287 > 65535)) {
                  s = 226;
               } else {
                  s = 264;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 38:
               int LA52_285 = _input.LA(1);
               sx = true;
               if (LA52_285 == 39) {
                  s = 262;
               } else if (LA52_285 == 92) {
                  s = 263;
               } else if ((LA52_285 < 0 || LA52_285 > 9) && (LA52_285 < 11 || LA52_285 > 38) && (LA52_285 < 40 || LA52_285 > 91) && (LA52_285 < 93 || LA52_285 > 65535)) {
                  s = 226;
               } else {
                  s = 264;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 39:
               int LA52_264 = _input.LA(1);
               sx = true;
               if (LA52_264 == 39) {
                  s = 262;
               } else if (LA52_264 == 92) {
                  s = 263;
               } else if ((LA52_264 < 0 || LA52_264 > 9) && (LA52_264 < 11 || LA52_264 > 38) && (LA52_264 < 40 || LA52_264 > 91) && (LA52_264 < 93 || LA52_264 > 65535)) {
                  s = 226;
               } else {
                  s = 264;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 40:
               int LA52_267 = _input.LA(1);
               sx = true;
               if (LA52_267 >= 0 && LA52_267 <= 65535) {
                  s = 226;
               } else {
                  s = 183;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 41:
               int LA52_223 = _input.LA(1);
               sx = true;
               if (LA52_223 >= 0 && LA52_223 <= 65535) {
                  s = 261;
               } else {
                  s = 225;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 42:
               int LA52_234 = _input.LA(1);
               sx = true;
               if (LA52_234 == 35) {
                  s = 235;
               } else if (LA52_234 != 9 && LA52_234 != 32) {
                  if (LA52_234 == 34) {
                     s = 183;
                  } else if (LA52_234 == 92) {
                     s = 129;
                  } else if (LA52_234 == 12) {
                     s = 236;
                  } else if (LA52_234 == 13) {
                     s = 237;
                  } else if ((LA52_234 < 0 || LA52_234 > 8) && LA52_234 != 11 && (LA52_234 < 14 || LA52_234 > 31) && LA52_234 != 33 && (LA52_234 < 36 || LA52_234 > 91) && (LA52_234 < 93 || LA52_234 > 65535)) {
                     s = 226;
                  } else {
                     s = 130;
                  }
               } else {
                  s = 234;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 43:
               int LA52_266 = _input.LA(1);
               sx = true;
               if (LA52_266 == 34) {
                  s = 231;
               } else if (LA52_266 == 92) {
                  s = 232;
               } else if ((LA52_266 < 0 || LA52_266 > 33) && (LA52_266 < 35 || LA52_266 > 91) && (LA52_266 < 93 || LA52_266 > 65535)) {
                  s = 225;
               } else {
                  s = 233;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 44:
               int LA52_224 = _input.LA(1);
               sx = true;
               if (LA52_224 == 39) {
                  s = 222;
               } else if (LA52_224 == 92) {
                  s = 223;
               } else if ((LA52_224 < 0 || LA52_224 > 38) && (LA52_224 < 40 || LA52_224 > 91) && (LA52_224 < 93 || LA52_224 > 65535)) {
                  s = 225;
               } else {
                  s = 224;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 45:
               int LA52_0 = _input.LA(1);
               int index52_0 = _input.index();
               _input.rewind();
               s = -1;
               if (LA52_0 == 97) {
                  s = 1;
               } else if (LA52_0 == 98) {
                  s = 2;
               } else if (LA52_0 == 99) {
                  s = 3;
               } else if (LA52_0 == 100) {
                  s = 4;
               } else if (LA52_0 == 101) {
                  s = 5;
               } else if (LA52_0 == 102) {
                  s = 6;
               } else if (LA52_0 == 103) {
                  s = 7;
               } else if (LA52_0 == 105) {
                  s = 8;
               } else if (LA52_0 == 108) {
                  s = 9;
               } else if (LA52_0 == 112) {
                  s = 10;
               } else if (LA52_0 == 114) {
                  s = 11;
               } else if (LA52_0 == 116) {
                  s = 12;
               } else if (LA52_0 == 119) {
                  s = 13;
               } else if (LA52_0 == 121) {
                  s = 14;
               } else if (LA52_0 == 40) {
                  s = 15;
               } else if (LA52_0 == 41) {
                  s = 16;
               } else if (LA52_0 == 91) {
                  s = 17;
               } else if (LA52_0 == 93) {
                  s = 18;
               } else if (LA52_0 == 58) {
                  s = 19;
               } else if (LA52_0 == 44) {
                  s = 20;
               } else if (LA52_0 == 59) {
                  s = 21;
               } else if (LA52_0 == 43) {
                  s = 22;
               } else if (LA52_0 == 45) {
                  s = 23;
               } else if (LA52_0 == 42) {
                  s = 24;
               } else if (LA52_0 == 47) {
                  s = 25;
               } else if (LA52_0 == 124) {
                  s = 26;
               } else if (LA52_0 == 38) {
                  s = 27;
               } else if (LA52_0 == 60) {
                  s = 28;
               } else if (LA52_0 == 62) {
                  s = 29;
               } else if (LA52_0 == 61) {
                  s = 30;
               } else if (LA52_0 == 37) {
                  s = 31;
               } else if (LA52_0 == 96) {
                  s = 32;
               } else if (LA52_0 == 123) {
                  s = 33;
               } else if (LA52_0 == 125) {
                  s = 34;
               } else if (LA52_0 == 94) {
                  s = 35;
               } else if (LA52_0 == 126) {
                  s = 36;
               } else if (LA52_0 == 33) {
                  s = 37;
               } else if (LA52_0 == 46) {
                  s = 38;
               } else if (LA52_0 == 64) {
                  s = 39;
               } else if (LA52_0 == 111) {
                  s = 40;
               } else if (LA52_0 == 110) {
                  s = 41;
               } else if (LA52_0 == 48) {
                  s = 42;
               } else if (LA52_0 >= 49 && LA52_0 <= 57) {
                  s = 43;
               } else if (LA52_0 == 117) {
                  s = 44;
               } else if (LA52_0 == 82) {
                  s = 45;
               } else if (LA52_0 == 85) {
                  s = 46;
               } else if ((LA52_0 < 65 || LA52_0 > 81) && (LA52_0 < 83 || LA52_0 > 84) && (LA52_0 < 86 || LA52_0 > 90) && LA52_0 != 95 && LA52_0 != 104 && (LA52_0 < 106 || LA52_0 > 107) && LA52_0 != 109 && LA52_0 != 113 && LA52_0 != 115 && LA52_0 != 118 && LA52_0 != 120 && LA52_0 != 122) {
                  if (LA52_0 == 39) {
                     s = 48;
                  } else if (LA52_0 == 34) {
                     s = 49;
                  } else if (LA52_0 == 92) {
                     s = 50;
                  } else if (LA52_0 == 12) {
                     s = 51;
                  } else if (LA52_0 != 10 && LA52_0 != 13) {
                     if (LA52_0 != 32 || PythonPartialLexer.this.startPos != 0 && PythonPartialLexer.this.startPos <= 0) {
                        if (LA52_0 == 9 && (PythonPartialLexer.this.startPos == 0 || PythonPartialLexer.this.startPos > 0)) {
                           s = 54;
                        } else if (LA52_0 == 35) {
                           s = 55;
                        }
                     } else {
                        s = 53;
                     }
                  } else {
                     s = 52;
                  }
               } else {
                  s = 47;
               }

               _input.seek(index52_0);
               if (s >= 0) {
                  return s;
               }
               break;
            case 46:
               int LA52_133 = _input.LA(1);
               int index52_133 = _input.index();
               _input.rewind();
               s = -1;
               if (PythonPartialLexer.this.startPos > 0) {
                  s = 131;
               } else if (PythonPartialLexer.this.startPos == 0 || PythonPartialLexer.this.startPos == 0 && PythonPartialLexer.this.implicitLineJoiningLevel > 0) {
                  s = 134;
               }

               _input.seek(index52_133);
               if (s >= 0) {
                  return s;
               }
               break;
            case 47:
               int LA52_135 = _input.LA(1);
               int index52_135 = _input.index();
               _input.rewind();
               s = -1;
               if (PythonPartialLexer.this.startPos > 0) {
                  s = 131;
               } else if (PythonPartialLexer.this.startPos == 0 || PythonPartialLexer.this.startPos == 0 && PythonPartialLexer.this.implicitLineJoiningLevel > 0) {
                  s = 134;
               }

               _input.seek(index52_135);
               if (s >= 0) {
                  return s;
               }
               break;
            case 48:
               int LA52_262 = _input.LA(1);
               sx = true;
               if (LA52_262 >= 0 && LA52_262 <= 65535) {
                  s = 226;
               } else {
                  s = 183;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 49:
               int LA52_284 = _input.LA(1);
               sx = true;
               if (LA52_284 >= 0 && LA52_284 <= 65535) {
                  s = 225;
               } else {
                  s = 183;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 50:
               int LA52_286 = _input.LA(1);
               sx = true;
               if (LA52_286 != 9 && LA52_286 != 32) {
                  if (LA52_286 == 35) {
                     s = 228;
                  } else if (LA52_286 == 12) {
                     s = 229;
                  } else if (LA52_286 == 13) {
                     s = 230;
                  } else if (LA52_286 == 39) {
                     s = 183;
                  } else if (LA52_286 == 92) {
                     s = 126;
                  } else if ((LA52_286 < 0 || LA52_286 > 8) && LA52_286 != 11 && (LA52_286 < 14 || LA52_286 > 31) && (LA52_286 < 33 || LA52_286 > 34) && (LA52_286 < 36 || LA52_286 > 38) && (LA52_286 < 40 || LA52_286 > 91) && (LA52_286 < 93 || LA52_286 > 65535)) {
                     s = 226;
                  } else {
                     s = 127;
                  }
               } else {
                  s = 227;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 51:
               int LA52_232 = _input.LA(1);
               sx = true;
               if (LA52_232 >= 0 && LA52_232 <= 65535) {
                  s = 266;
               } else {
                  s = 225;
               }

               if (s >= 0) {
                  return s;
               }
         }

         NoViableAltException nvae = new NoViableAltException(this.getDescription(), 52, s, _input);
         this.error(nvae);
         throw nvae;
      }
   }

   class DFA51 extends DFA {
      public DFA51(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 51;
         this.eot = PythonPartialLexer.DFA51_eot;
         this.eof = PythonPartialLexer.DFA51_eof;
         this.min = PythonPartialLexer.DFA51_min;
         this.max = PythonPartialLexer.DFA51_max;
         this.accept = PythonPartialLexer.DFA51_accept;
         this.special = PythonPartialLexer.DFA51_special;
         this.transition = PythonPartialLexer.DFA51_transition;
      }

      public String getDescription() {
         return "1204:1: COMMENT : ({...}? => ( ' ' | '\\t' )* '#' (~ '\\n' )* ( '\\n' )+ | '#' (~ '\\n' )* );";
      }

      public int specialStateTransition(int sx, IntStream _input) throws NoViableAltException {
         boolean s;
         switch (sx) {
            case 0:
               int LA51_2 = _input.LA(1);
               int index51_2 = _input.index();
               _input.rewind();
               s = true;
               if ((LA51_2 < 0 || LA51_2 > 9) && (LA51_2 < 11 || LA51_2 > 65535)) {
                  if (LA51_2 == 10 && PythonPartialLexer.this.startPos == 0) {
                     sx = 1;
                  } else {
                     sx = 4;
                  }
               } else {
                  sx = 3;
               }

               _input.seek(index51_2);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 1:
               int LA51_3 = _input.LA(1);
               int index51_3 = _input.index();
               _input.rewind();
               s = true;
               if ((LA51_3 < 0 || LA51_3 > 9) && (LA51_3 < 11 || LA51_3 > 65535)) {
                  if (LA51_3 == 10 && PythonPartialLexer.this.startPos == 0) {
                     sx = 1;
                  } else {
                     sx = 4;
                  }
               } else {
                  sx = 3;
               }

               _input.seek(index51_3);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 2:
               int LA51_0 = _input.LA(1);
               int index51_0 = _input.index();
               _input.rewind();
               sx = -1;
               if ((LA51_0 == 9 || LA51_0 == 32) && PythonPartialLexer.this.startPos == 0) {
                  sx = 1;
               } else if (LA51_0 == 35) {
                  sx = 2;
               }

               _input.seek(index51_0);
               if (sx >= 0) {
                  return sx;
               }
         }

         NoViableAltException nvae = new NoViableAltException(this.getDescription(), 51, sx, _input);
         this.error(nvae);
         throw nvae;
      }
   }

   class DFA27 extends DFA {
      public DFA27(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 27;
         this.eot = PythonPartialLexer.DFA27_eot;
         this.eof = PythonPartialLexer.DFA27_eof;
         this.min = PythonPartialLexer.DFA27_min;
         this.max = PythonPartialLexer.DFA27_max;
         this.accept = PythonPartialLexer.DFA27_accept;
         this.special = PythonPartialLexer.DFA27_special;
         this.transition = PythonPartialLexer.DFA27_transition;
      }

      public String getDescription() {
         return "()* loopback of 1086:18: ( ESC | ~ ( '\\\\' | '\\n' | '\\'' ) )*";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         boolean sx;
         switch (s) {
            case 0:
               int LA27_0 = _input.LA(1);
               s = -1;
               if (LA27_0 == 92) {
                  s = 1;
               } else if (LA27_0 >= 0 && LA27_0 <= 9 || LA27_0 >= 11 && LA27_0 <= 38 || LA27_0 >= 40 && LA27_0 <= 91 || LA27_0 >= 93 && LA27_0 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA27_1 = _input.LA(1);
               s = -1;
               if (LA27_1 == 13) {
                  s = 3;
               } else if (LA27_1 == 10) {
                  s = 4;
               } else if (LA27_1 >= 0 && LA27_1 <= 9 || LA27_1 >= 11 && LA27_1 <= 12 || LA27_1 >= 14 && LA27_1 <= 65535) {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA27_10 = _input.LA(1);
               s = -1;
               if (LA27_10 == 10) {
                  s = 6;
               } else if (LA27_10 >= 0 && LA27_10 <= 9 || LA27_10 >= 11 && LA27_10 <= 38 || LA27_10 >= 40 && LA27_10 <= 65535) {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA27_4 = _input.LA(1);
               sx = true;
               if ((LA27_4 < 0 || LA27_4 > 8) && LA27_4 != 11 && (LA27_4 < 14 || LA27_4 > 31) && (LA27_4 < 33 || LA27_4 > 34) && (LA27_4 < 36 || LA27_4 > 38) && (LA27_4 < 40 || LA27_4 > 65535)) {
                  if (LA27_4 != 9 && LA27_4 != 32) {
                     if (LA27_4 == 35) {
                        s = 8;
                     } else if (LA27_4 == 12) {
                        s = 9;
                     } else if (LA27_4 == 13) {
                        s = 10;
                     } else {
                        s = 6;
                     }
                  } else {
                     s = 7;
                  }
               } else {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA27_9 = _input.LA(1);
               s = -1;
               if (LA27_9 == 13) {
                  s = 10;
               } else if (LA27_9 == 10) {
                  s = 6;
               } else if (LA27_9 >= 0 && LA27_9 <= 9 || LA27_9 >= 11 && LA27_9 <= 12 || LA27_9 >= 14 && LA27_9 <= 38 || LA27_9 >= 40 && LA27_9 <= 65535) {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA27_15 = _input.LA(1);
               sx = true;
               if (LA27_15 == 92) {
                  s = 11;
               } else if ((LA27_15 < 0 || LA27_15 > 9) && (LA27_15 < 11 || LA27_15 > 38) && (LA27_15 < 40 || LA27_15 > 91) && (LA27_15 < 93 || LA27_15 > 65535)) {
                  s = 6;
               } else {
                  s = 12;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA27_13 = _input.LA(1);
               sx = true;
               if (LA27_13 == 92) {
                  s = 11;
               } else if (LA27_13 >= 0 && LA27_13 <= 9 || LA27_13 >= 11 && LA27_13 <= 38 || LA27_13 >= 40 && LA27_13 <= 91 || LA27_13 >= 93 && LA27_13 <= 65535) {
                  s = 12;
               } else if (LA27_13 == 10) {
                  s = 16;
               } else {
                  s = 6;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA27_3 = _input.LA(1);
               s = -1;
               if (LA27_3 >= 0 && LA27_3 <= 9 || LA27_3 >= 11 && LA27_3 <= 38 || LA27_3 >= 40 && LA27_3 <= 65535) {
                  s = 5;
               } else if (LA27_3 == 10) {
                  s = 6;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA27_8 = _input.LA(1);
               sx = true;
               if (LA27_8 == 92) {
                  s = 11;
               } else if ((LA27_8 < 0 || LA27_8 > 9) && (LA27_8 < 11 || LA27_8 > 38) && (LA27_8 < 40 || LA27_8 > 91) && (LA27_8 < 93 || LA27_8 > 65535)) {
                  s = 6;
               } else {
                  s = 12;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA27_12 = _input.LA(1);
               sx = true;
               if (LA27_12 == 92) {
                  s = 11;
               } else if (LA27_12 >= 0 && LA27_12 <= 9 || LA27_12 >= 11 && LA27_12 <= 38 || LA27_12 >= 40 && LA27_12 <= 91 || LA27_12 >= 93 && LA27_12 <= 65535) {
                  s = 12;
               } else {
                  s = 6;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA27_11 = _input.LA(1);
               sx = true;
               if (LA27_11 == 13) {
                  s = 13;
               } else if (LA27_11 == 10) {
                  s = 14;
               } else if ((LA27_11 < 0 || LA27_11 > 9) && (LA27_11 < 11 || LA27_11 > 12) && (LA27_11 < 14 || LA27_11 > 65535)) {
                  s = 6;
               } else {
                  s = 15;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA27_7 = _input.LA(1);
               sx = true;
               if (LA27_7 == 35) {
                  s = 8;
               } else if (LA27_7 != 9 && LA27_7 != 32) {
                  if (LA27_7 == 12) {
                     s = 9;
                  } else if (LA27_7 == 13) {
                     s = 10;
                  } else if ((LA27_7 < 0 || LA27_7 > 8) && LA27_7 != 11 && (LA27_7 < 14 || LA27_7 > 31) && (LA27_7 < 33 || LA27_7 > 34) && (LA27_7 < 36 || LA27_7 > 38) && (LA27_7 < 40 || LA27_7 > 65535)) {
                     s = 6;
                  } else {
                     s = 5;
                  }
               } else {
                  s = 7;
               }

               if (s >= 0) {
                  return s;
               }
         }

         NoViableAltException nvae = new NoViableAltException(this.getDescription(), 27, s, _input);
         this.error(nvae);
         throw nvae;
      }
   }

   class DFA26 extends DFA {
      public DFA26(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 26;
         this.eot = PythonPartialLexer.DFA26_eot;
         this.eof = PythonPartialLexer.DFA26_eof;
         this.min = PythonPartialLexer.DFA26_min;
         this.max = PythonPartialLexer.DFA26_max;
         this.accept = PythonPartialLexer.DFA26_accept;
         this.special = PythonPartialLexer.DFA26_special;
         this.transition = PythonPartialLexer.DFA26_transition;
      }

      public String getDescription() {
         return "()* loopback of 1085:17: ( ESC | ~ ( '\\\\' | '\\n' | '\"' ) )*";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         boolean sx;
         switch (s) {
            case 0:
               int LA26_1 = _input.LA(1);
               s = -1;
               if (LA26_1 == 13) {
                  s = 3;
               } else if (LA26_1 == 10) {
                  s = 4;
               } else if (LA26_1 >= 0 && LA26_1 <= 9 || LA26_1 >= 11 && LA26_1 <= 12 || LA26_1 >= 14 && LA26_1 <= 65535) {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA26_3 = _input.LA(1);
               s = -1;
               if ((LA26_3 < 0 || LA26_3 > 9) && (LA26_3 < 11 || LA26_3 > 33) && (LA26_3 < 35 || LA26_3 > 65535)) {
                  if (LA26_3 == 10) {
                     s = 6;
                  }
               } else {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA26_11 = _input.LA(1);
               sx = true;
               if (LA26_11 == 13) {
                  s = 13;
               } else if (LA26_11 == 10) {
                  s = 14;
               } else if ((LA26_11 < 0 || LA26_11 > 9) && (LA26_11 < 11 || LA26_11 > 12) && (LA26_11 < 14 || LA26_11 > 65535)) {
                  s = 6;
               } else {
                  s = 15;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA26_4 = _input.LA(1);
               sx = true;
               if ((LA26_4 < 0 || LA26_4 > 8) && LA26_4 != 11 && (LA26_4 < 14 || LA26_4 > 31) && LA26_4 != 33 && (LA26_4 < 36 || LA26_4 > 65535)) {
                  if (LA26_4 != 9 && LA26_4 != 32) {
                     if (LA26_4 == 35) {
                        s = 8;
                     } else if (LA26_4 == 12) {
                        s = 9;
                     } else if (LA26_4 == 13) {
                        s = 10;
                     } else {
                        s = 6;
                     }
                  } else {
                     s = 7;
                  }
               } else {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA26_15 = _input.LA(1);
               sx = true;
               if (LA26_15 == 92) {
                  s = 11;
               } else if ((LA26_15 < 0 || LA26_15 > 9) && (LA26_15 < 11 || LA26_15 > 33) && (LA26_15 < 35 || LA26_15 > 91) && (LA26_15 < 93 || LA26_15 > 65535)) {
                  s = 6;
               } else {
                  s = 12;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA26_13 = _input.LA(1);
               sx = true;
               if (LA26_13 == 92) {
                  s = 11;
               } else if ((LA26_13 < 0 || LA26_13 > 9) && (LA26_13 < 11 || LA26_13 > 33) && (LA26_13 < 35 || LA26_13 > 91) && (LA26_13 < 93 || LA26_13 > 65535)) {
                  if (LA26_13 == 10) {
                     s = 16;
                  } else {
                     s = 6;
                  }
               } else {
                  s = 12;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA26_0 = _input.LA(1);
               s = -1;
               if (LA26_0 == 92) {
                  s = 1;
               } else if (LA26_0 >= 0 && LA26_0 <= 9 || LA26_0 >= 11 && LA26_0 <= 33 || LA26_0 >= 35 && LA26_0 <= 91 || LA26_0 >= 93 && LA26_0 <= 65535) {
                  s = 2;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA26_10 = _input.LA(1);
               s = -1;
               if (LA26_10 == 10) {
                  s = 6;
               } else if (LA26_10 >= 0 && LA26_10 <= 9 || LA26_10 >= 11 && LA26_10 <= 33 || LA26_10 >= 35 && LA26_10 <= 65535) {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA26_8 = _input.LA(1);
               sx = true;
               if (LA26_8 == 92) {
                  s = 11;
               } else if ((LA26_8 < 0 || LA26_8 > 9) && (LA26_8 < 11 || LA26_8 > 33) && (LA26_8 < 35 || LA26_8 > 91) && (LA26_8 < 93 || LA26_8 > 65535)) {
                  s = 6;
               } else {
                  s = 12;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA26_12 = _input.LA(1);
               sx = true;
               if (LA26_12 == 92) {
                  s = 11;
               } else if ((LA26_12 < 0 || LA26_12 > 9) && (LA26_12 < 11 || LA26_12 > 33) && (LA26_12 < 35 || LA26_12 > 91) && (LA26_12 < 93 || LA26_12 > 65535)) {
                  s = 6;
               } else {
                  s = 12;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA26_7 = _input.LA(1);
               sx = true;
               if (LA26_7 == 35) {
                  s = 8;
               } else if (LA26_7 != 9 && LA26_7 != 32) {
                  if (LA26_7 == 12) {
                     s = 9;
                  } else if (LA26_7 == 13) {
                     s = 10;
                  } else if ((LA26_7 < 0 || LA26_7 > 8) && LA26_7 != 11 && (LA26_7 < 14 || LA26_7 > 31) && LA26_7 != 33 && (LA26_7 < 36 || LA26_7 > 65535)) {
                     s = 6;
                  } else {
                     s = 5;
                  }
               } else {
                  s = 7;
               }

               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA26_9 = _input.LA(1);
               s = -1;
               if (LA26_9 == 13) {
                  s = 10;
               } else if (LA26_9 == 10) {
                  s = 6;
               } else if (LA26_9 >= 0 && LA26_9 <= 9 || LA26_9 >= 11 && LA26_9 <= 12 || LA26_9 >= 14 && LA26_9 <= 33 || LA26_9 >= 35 && LA26_9 <= 65535) {
                  s = 5;
               }

               if (s >= 0) {
                  return s;
               }
         }

         NoViableAltException nvae = new NoViableAltException(this.getDescription(), 26, s, _input);
         this.error(nvae);
         throw nvae;
      }
   }

   class DFA25 extends DFA {
      public DFA25(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 25;
         this.eot = PythonPartialLexer.DFA25_eot;
         this.eof = PythonPartialLexer.DFA25_eof;
         this.min = PythonPartialLexer.DFA25_min;
         this.max = PythonPartialLexer.DFA25_max;
         this.accept = PythonPartialLexer.DFA25_accept;
         this.special = PythonPartialLexer.DFA25_special;
         this.transition = PythonPartialLexer.DFA25_transition;
      }

      public String getDescription() {
         return "1084:7: ( 'r' | 'u' | 'ur' | 'R' | 'U' | 'UR' | 'uR' | 'Ur' )?";
      }
   }

   class DFA21 extends DFA {
      public DFA21(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 21;
         this.eot = PythonPartialLexer.DFA21_eot;
         this.eof = PythonPartialLexer.DFA21_eof;
         this.min = PythonPartialLexer.DFA21_min;
         this.max = PythonPartialLexer.DFA21_max;
         this.accept = PythonPartialLexer.DFA21_accept;
         this.special = PythonPartialLexer.DFA21_special;
         this.transition = PythonPartialLexer.DFA21_transition;
      }

      public String getDescription() {
         return "1077:7: ( 'r' | 'u' | 'ur' | 'R' | 'U' | 'UR' | 'uR' | 'Ur' )?";
      }
   }

   class DFA15 extends DFA {
      public DFA15(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 15;
         this.eot = PythonPartialLexer.DFA15_eot;
         this.eof = PythonPartialLexer.DFA15_eof;
         this.min = PythonPartialLexer.DFA15_min;
         this.max = PythonPartialLexer.DFA15_max;
         this.accept = PythonPartialLexer.DFA15_accept;
         this.special = PythonPartialLexer.DFA15_special;
         this.transition = PythonPartialLexer.DFA15_transition;
      }

      public String getDescription() {
         return "1063:9: ( 'r' | 'u' | 'ur' | 'R' | 'U' | 'UR' | 'uR' | 'Ur' )?";
      }
   }

   class DFA12 extends DFA {
      public DFA12(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 12;
         this.eot = PythonPartialLexer.DFA12_eot;
         this.eof = PythonPartialLexer.DFA12_eof;
         this.min = PythonPartialLexer.DFA12_min;
         this.max = PythonPartialLexer.DFA12_max;
         this.accept = PythonPartialLexer.DFA12_accept;
         this.special = PythonPartialLexer.DFA12_special;
         this.transition = PythonPartialLexer.DFA12_transition;
      }

      public String getDescription() {
         return "1047:1: COMPLEX : ( ( DIGITS )+ ( 'j' | 'J' ) | FLOAT ( 'j' | 'J' ) );";
      }
   }

   class DFA5 extends DFA {
      public DFA5(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 5;
         this.eot = PythonPartialLexer.DFA5_eot;
         this.eof = PythonPartialLexer.DFA5_eof;
         this.min = PythonPartialLexer.DFA5_min;
         this.max = PythonPartialLexer.DFA5_max;
         this.accept = PythonPartialLexer.DFA5_accept;
         this.special = PythonPartialLexer.DFA5_special;
         this.transition = PythonPartialLexer.DFA5_transition;
      }

      public String getDescription() {
         return "1025:1: FLOAT : ( '.' DIGITS ( Exponent )? | DIGITS '.' Exponent | DIGITS ( '.' ( DIGITS ( Exponent )? )? | Exponent ) );";
      }
   }
}
