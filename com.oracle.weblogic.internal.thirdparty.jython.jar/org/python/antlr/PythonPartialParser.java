package org.python.antlr;

import org.python.antlr.runtime.BaseRecognizer;
import org.python.antlr.runtime.BitSet;
import org.python.antlr.runtime.DFA;
import org.python.antlr.runtime.EarlyExitException;
import org.python.antlr.runtime.IntStream;
import org.python.antlr.runtime.MismatchedSetException;
import org.python.antlr.runtime.NoViableAltException;
import org.python.antlr.runtime.Parser;
import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.RecognizerSharedState;
import org.python.antlr.runtime.TokenStream;

public class PythonPartialParser extends Parser {
   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "INDENT", "DEDENT", "TRAILBACKSLASH", "NEWLINE", "LEADING_WS", "NAME", "DOT", "PRINT", "AND", "AS", "ASSERT", "BREAK", "CLASS", "CONTINUE", "DEF", "DELETE", "ELIF", "EXCEPT", "EXEC", "FINALLY", "FROM", "FOR", "GLOBAL", "IF", "IMPORT", "IN", "IS", "LAMBDA", "NOT", "OR", "ORELSE", "PASS", "RAISE", "RETURN", "TRY", "WHILE", "WITH", "YIELD", "AT", "LPAREN", "RPAREN", "COLON", "ASSIGN", "COMMA", "STAR", "DOUBLESTAR", "SEMI", "PLUSEQUAL", "MINUSEQUAL", "STAREQUAL", "SLASHEQUAL", "PERCENTEQUAL", "AMPEREQUAL", "VBAREQUAL", "CIRCUMFLEXEQUAL", "LEFTSHIFTEQUAL", "RIGHTSHIFTEQUAL", "DOUBLESTAREQUAL", "DOUBLESLASHEQUAL", "RIGHTSHIFT", "LESS", "GREATER", "EQUAL", "GREATEREQUAL", "LESSEQUAL", "ALT_NOTEQUAL", "NOTEQUAL", "VBAR", "CIRCUMFLEX", "AMPER", "LEFTSHIFT", "PLUS", "MINUS", "SLASH", "PERCENT", "DOUBLESLASH", "TILDE", "LBRACK", "RBRACK", "LCURLY", "RCURLY", "BACKQUOTE", "INT", "LONGINT", "FLOAT", "COMPLEX", "STRING", "DIGITS", "Exponent", "TRIAPOS", "TRIQUOTE", "ESC", "COMMENT", "CONTINUED_LINE", "WS", "TRISTRINGPART", "STRINGPART"};
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
   private ErrorHandler errorHandler;
   protected DFA27 dfa27;
   protected DFA32 dfa32;
   protected DFA36 dfa36;
   protected DFA34 dfa34;
   protected DFA45 dfa45;
   protected DFA57 dfa57;
   protected DFA75 dfa75;
   protected DFA84 dfa84;
   protected DFA108 dfa108;
   protected DFA121 dfa121;
   protected DFA125 dfa125;
   protected DFA123 dfa123;
   protected DFA126 dfa126;
   protected DFA130 dfa130;
   protected DFA128 dfa128;
   protected DFA131 dfa131;
   static final String DFA27_eotS = "\n\uffff";
   static final String DFA27_eofS = "\n\uffff";
   static final String DFA27_minS = "\u0001\u0006\t\uffff";
   static final String DFA27_maxS = "\u0001d\t\uffff";
   static final String DFA27_acceptS = "\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t";
   static final String DFA27_specialS = "\n\uffff}>";
   static final String[] DFA27_transitionS = new String[]{"\u0001\u0001\u0002\uffff\u0001\u0001\u0001\uffff\u0001\u0002\u0002\uffff\u0001\t\u0001\u0005\u0001\uffff\u0001\u0005\u0001\uffff\u0001\u0003\u0002\uffff\u0001\b\u0001\uffff\u0001\u0006\u0001\uffff\u0001\u0007\u0001\uffff\u0001\u0006\u0002\uffff\u0002\u0001\u0002\uffff\u0001\u0004\u0002\u0005\u0003\uffff\u0001\u0005\u0001\uffff\u0001\u0001\u001f\uffff\u0002\u0001\u0003\uffff\u0002\u0001\u0001\uffff\u0001\u0001\u0001\uffff\u0006\u0001\b\uffff\u0002\u0001", "", "", "", "", "", "", "", "", ""};
   static final short[] DFA27_eot = DFA.unpackEncodedString("\n\uffff");
   static final short[] DFA27_eof = DFA.unpackEncodedString("\n\uffff");
   static final char[] DFA27_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0006\t\uffff");
   static final char[] DFA27_max = DFA.unpackEncodedStringToUnsignedChars("\u0001d\t\uffff");
   static final short[] DFA27_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t");
   static final short[] DFA27_special = DFA.unpackEncodedString("\n\uffff}>");
   static final short[][] DFA27_transition;
   static final String DFA32_eotS = "\u0016\uffff";
   static final String DFA32_eofS = "\u0016\uffff";
   static final String DFA32_minS = "\u0001\u0006\u0012\u0000\u0003\uffff";
   static final String DFA32_maxS = "\u0001d\u0012\u0000\u0003\uffff";
   static final String DFA32_acceptS = "\u0013\uffff\u0001\u0001\u0001\u0002\u0001\u0003";
   static final String DFA32_specialS = "\u0001\uffff\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0003\uffff}>";
   static final String[] DFA32_transitionS;
   static final short[] DFA32_eot;
   static final short[] DFA32_eof;
   static final char[] DFA32_min;
   static final char[] DFA32_max;
   static final short[] DFA32_accept;
   static final short[] DFA32_special;
   static final short[][] DFA32_transition;
   static final String DFA36_eotS = "\u0015\uffff";
   static final String DFA36_eofS = "\u0015\uffff";
   static final String DFA36_minS = "\u0001\u0006\u0012\u0000\u0002\uffff";
   static final String DFA36_maxS = "\u0001d\u0012\u0000\u0002\uffff";
   static final String DFA36_acceptS = "\u0013\uffff\u0001\u0001\u0001\u0002";
   static final String DFA36_specialS = "\u0001\uffff\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0002\uffff}>";
   static final String[] DFA36_transitionS;
   static final short[] DFA36_eot;
   static final short[] DFA36_eof;
   static final char[] DFA36_min;
   static final char[] DFA36_max;
   static final short[] DFA36_accept;
   static final short[] DFA36_special;
   static final short[][] DFA36_transition;
   static final String DFA34_eotS = "\u0018\uffff";
   static final String DFA34_eofS = "\u0002\u0002\u0016\uffff";
   static final String DFA34_minS = "\u0001\u0007\u0001\u0006\u0016\uffff";
   static final String DFA34_maxS = "\u00012\u0001d\u0016\uffff";
   static final String DFA34_acceptS = "\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u0013\uffff";
   static final String DFA34_specialS = "\u0018\uffff}>";
   static final String[] DFA34_transitionS;
   static final short[] DFA34_eot;
   static final short[] DFA34_eof;
   static final char[] DFA34_min;
   static final char[] DFA34_max;
   static final short[] DFA34_accept;
   static final short[] DFA34_special;
   static final short[][] DFA34_transition;
   static final String DFA45_eotS = "\u0004\uffff";
   static final String DFA45_eofS = "\u0004\uffff";
   static final String DFA45_minS = "\u0002\t\u0002\uffff";
   static final String DFA45_maxS = "\u0001\n\u0001\u001c\u0002\uffff";
   static final String DFA45_acceptS = "\u0002\uffff\u0001\u0001\u0001\u0002";
   static final String DFA45_specialS = "\u0004\uffff}>";
   static final String[] DFA45_transitionS;
   static final short[] DFA45_eot;
   static final short[] DFA45_eof;
   static final char[] DFA45_min;
   static final char[] DFA45_max;
   static final short[] DFA45_accept;
   static final short[] DFA45_special;
   static final short[][] DFA45_transition;
   static final String DFA57_eotS = "\n\uffff";
   static final String DFA57_eofS = "\n\uffff";
   static final String DFA57_minS = "\u0001\u0010\u0005\uffff\u0001\u0000\u0003\uffff";
   static final String DFA57_maxS = "\u0001*\u0005\uffff\u0001\u0000\u0003\uffff";
   static final String DFA57_acceptS = "\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\uffff\u0001\u0006\u0001\u0007\u0001\b";
   static final String DFA57_specialS = "\u0001\u0000\u0005\uffff\u0001\u0001\u0003\uffff}>";
   static final String[] DFA57_transitionS;
   static final short[] DFA57_eot;
   static final short[] DFA57_eof;
   static final char[] DFA57_min;
   static final char[] DFA57_max;
   static final short[] DFA57_accept;
   static final short[] DFA57_special;
   static final short[][] DFA57_transition;
   static final String DFA75_eotS = "\u0010\uffff";
   static final String DFA75_eofS = "\u0001\u0002\u000f\uffff";
   static final String DFA75_minS = "\u0001\u0007\u0001\u0000\u000e\uffff";
   static final String DFA75_maxS = "\u0001U\u0001\u0000\u000e\uffff";
   static final String DFA75_acceptS = "\u0002\uffff\u0001\u0002\f\uffff\u0001\u0001";
   static final String DFA75_specialS = "\u0001\uffff\u0001\u0000\u000e\uffff}>";
   static final String[] DFA75_transitionS;
   static final short[] DFA75_eot;
   static final short[] DFA75_eof;
   static final char[] DFA75_min;
   static final char[] DFA75_max;
   static final short[] DFA75_accept;
   static final short[] DFA75_special;
   static final short[][] DFA75_transition;
   static final String DFA84_eotS = "\r\uffff";
   static final String DFA84_eofS = "\r\uffff";
   static final String DFA84_minS = "\u0001\u001d\t\uffff\u0001\u0006\u0002\uffff";
   static final String DFA84_maxS = "\u0001F\t\uffff\u0001d\u0002\uffff";
   static final String DFA84_acceptS = "\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\uffff\u0001\u000b\u0001\n";
   static final String DFA84_specialS = "\r\uffff}>";
   static final String[] DFA84_transitionS;
   static final short[] DFA84_eot;
   static final short[] DFA84_eof;
   static final char[] DFA84_min;
   static final char[] DFA84_max;
   static final short[] DFA84_accept;
   static final short[] DFA84_special;
   static final short[][] DFA84_transition;
   static final String DFA108_eotS = "\u0016\uffff";
   static final String DFA108_eofS = "\u0016\uffff";
   static final String DFA108_minS = "\u0001,\u0001\u0006\u0014\uffff";
   static final String DFA108_maxS = "\u0001/\u0001d\u0014\uffff";
   static final String DFA108_acceptS = "\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u0011\uffff";
   static final String DFA108_specialS = "\u0016\uffff}>";
   static final String[] DFA108_transitionS;
   static final short[] DFA108_eot;
   static final short[] DFA108_eof;
   static final char[] DFA108_min;
   static final char[] DFA108_max;
   static final short[] DFA108_accept;
   static final short[] DFA108_special;
   static final short[][] DFA108_transition;
   static final String DFA121_eotS = "\u0017\uffff";
   static final String DFA121_eofS = "\u0017\uffff";
   static final String DFA121_minS = "\u0001\u0006\u0001\uffff\u0012\u0000\u0003\uffff";
   static final String DFA121_maxS = "\u0001d\u0001\uffff\u0012\u0000\u0003\uffff";
   static final String DFA121_acceptS = "\u0001\uffff\u0001\u0001\u0012\uffff\u0001\u0003\u0001\u0002\u0001\u0004";
   static final String DFA121_specialS = "\u0001\u0000\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0001\u0012\u0003\uffff}>";
   static final String[] DFA121_transitionS;
   static final short[] DFA121_eot;
   static final short[] DFA121_eof;
   static final char[] DFA121_min;
   static final char[] DFA121_max;
   static final short[] DFA121_accept;
   static final short[] DFA121_special;
   static final short[][] DFA121_transition;
   static final String DFA125_eotS = "\u0013\uffff";
   static final String DFA125_eofS = "\u0013\uffff";
   static final String DFA125_minS = "\u0001\u0006\u0010\u0000\u0002\uffff";
   static final String DFA125_maxS = "\u0001d\u0010\u0000\u0002\uffff";
   static final String DFA125_acceptS = "\u0011\uffff\u0001\u0001\u0001\u0002";
   static final String DFA125_specialS = "\u0001\uffff\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0002\uffff}>";
   static final String[] DFA125_transitionS;
   static final short[] DFA125_eot;
   static final short[] DFA125_eof;
   static final char[] DFA125_min;
   static final char[] DFA125_max;
   static final short[] DFA125_accept;
   static final short[] DFA125_special;
   static final short[][] DFA125_transition;
   static final String DFA123_eotS = "\u0018\uffff";
   static final String DFA123_eofS = "\u0002\u0002\u0016\uffff";
   static final String DFA123_minS = "\u0001\u0007\u0001\u0006\u0016\uffff";
   static final String DFA123_maxS = "\u00012\u0001d\u0016\uffff";
   static final String DFA123_acceptS = "\u0002\uffff\u0001\u0002\u0005\uffff\u0001\u0001\u000f\uffff";
   static final String DFA123_specialS = "\u0018\uffff}>";
   static final String[] DFA123_transitionS;
   static final short[] DFA123_eot;
   static final short[] DFA123_eof;
   static final char[] DFA123_min;
   static final char[] DFA123_max;
   static final short[] DFA123_accept;
   static final short[] DFA123_special;
   static final short[][] DFA123_transition;
   static final String DFA126_eotS = "\u0014\uffff";
   static final String DFA126_eofS = "\u0002\u0002\u0012\uffff";
   static final String DFA126_minS = "\u0001/\u0001\u0006\u0012\uffff";
   static final String DFA126_maxS = "\u0001/\u0001d\u0012\uffff";
   static final String DFA126_acceptS = "\u0002\uffff\u0001\u0002\u0001\u0001\u0010\uffff";
   static final String DFA126_specialS = "\u0014\uffff}>";
   static final String[] DFA126_transitionS;
   static final short[] DFA126_eot;
   static final short[] DFA126_eof;
   static final char[] DFA126_min;
   static final char[] DFA126_max;
   static final short[] DFA126_accept;
   static final short[] DFA126_special;
   static final short[][] DFA126_transition;
   static final String DFA130_eotS = "\u0015\uffff";
   static final String DFA130_eofS = "\u0015\uffff";
   static final String DFA130_minS = "\u0001\u0006\u0012\u0000\u0002\uffff";
   static final String DFA130_maxS = "\u0001d\u0012\u0000\u0002\uffff";
   static final String DFA130_acceptS = "\u0013\uffff\u0001\u0001\u0001\u0002";
   static final String DFA130_specialS = "\u0001\uffff\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0002\uffff}>";
   static final String[] DFA130_transitionS;
   static final short[] DFA130_eot;
   static final short[] DFA130_eof;
   static final char[] DFA130_min;
   static final char[] DFA130_max;
   static final short[] DFA130_accept;
   static final short[] DFA130_special;
   static final short[][] DFA130_transition;
   static final String DFA128_eotS = "*\uffff";
   static final String DFA128_eofS = "\u0002\u0002(\uffff";
   static final String DFA128_minS = "\u0001\u0007\u0001\u0006(\uffff";
   static final String DFA128_maxS = "\u0001U\u0001d(\uffff";
   static final String DFA128_acceptS = "\u0002\uffff\u0001\u0002\n\uffff\u0001\u0001\u0006\uffff\u0001\u0001\u0015\uffff";
   static final String DFA128_specialS = "*\uffff}>";
   static final String[] DFA128_transitionS;
   static final short[] DFA128_eot;
   static final short[] DFA128_eof;
   static final char[] DFA128_min;
   static final char[] DFA128_max;
   static final short[] DFA128_accept;
   static final short[] DFA128_special;
   static final short[][] DFA128_transition;
   static final String DFA131_eotS = "\u0016\uffff";
   static final String DFA131_eofS = "\u0016\uffff";
   static final String DFA131_minS = "\u0001/\u0001\u0006\u0014\uffff";
   static final String DFA131_maxS = "\u0001T\u0001d\u0014\uffff";
   static final String DFA131_acceptS = "\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u0011\uffff";
   static final String DFA131_specialS = "\u0016\uffff}>";
   static final String[] DFA131_transitionS;
   static final short[] DFA131_eot;
   static final short[] DFA131_eof;
   static final char[] DFA131_min;
   static final char[] DFA131_max;
   static final short[] DFA131_accept;
   static final short[] DFA131_special;
   static final short[][] DFA131_transition;
   public static final BitSet FOLLOW_NEWLINE_in_single_input72;
   public static final BitSet FOLLOW_simple_stmt_in_single_input80;
   public static final BitSet FOLLOW_compound_stmt_in_single_input88;
   public static final BitSet FOLLOW_NEWLINE_in_single_input90;
   public static final BitSet FOLLOW_EOF_in_single_input93;
   public static final BitSet FOLLOW_LEADING_WS_in_eval_input111;
   public static final BitSet FOLLOW_NEWLINE_in_eval_input115;
   public static final BitSet FOLLOW_testlist_in_eval_input119;
   public static final BitSet FOLLOW_NEWLINE_in_eval_input123;
   public static final BitSet FOLLOW_EOF_in_eval_input127;
   public static final BitSet FOLLOW_NAME_in_dotted_attr145;
   public static final BitSet FOLLOW_DOT_in_dotted_attr156;
   public static final BitSet FOLLOW_NAME_in_dotted_attr158;
   public static final BitSet FOLLOW_set_in_attr0;
   public static final BitSet FOLLOW_AT_in_decorator464;
   public static final BitSet FOLLOW_dotted_attr_in_decorator466;
   public static final BitSet FOLLOW_LPAREN_in_decorator474;
   public static final BitSet FOLLOW_arglist_in_decorator484;
   public static final BitSet FOLLOW_RPAREN_in_decorator508;
   public static final BitSet FOLLOW_NEWLINE_in_decorator522;
   public static final BitSet FOLLOW_decorator_in_decorators540;
   public static final BitSet FOLLOW_decorators_in_funcdef559;
   public static final BitSet FOLLOW_DEF_in_funcdef562;
   public static final BitSet FOLLOW_NAME_in_funcdef564;
   public static final BitSet FOLLOW_parameters_in_funcdef566;
   public static final BitSet FOLLOW_COLON_in_funcdef568;
   public static final BitSet FOLLOW_suite_in_funcdef570;
   public static final BitSet FOLLOW_LPAREN_in_parameters588;
   public static final BitSet FOLLOW_varargslist_in_parameters597;
   public static final BitSet FOLLOW_RPAREN_in_parameters621;
   public static final BitSet FOLLOW_fpdef_in_defparameter639;
   public static final BitSet FOLLOW_ASSIGN_in_defparameter642;
   public static final BitSet FOLLOW_test_in_defparameter644;
   public static final BitSet FOLLOW_defparameter_in_varargslist666;
   public static final BitSet FOLLOW_COMMA_in_varargslist676;
   public static final BitSet FOLLOW_defparameter_in_varargslist678;
   public static final BitSet FOLLOW_COMMA_in_varargslist689;
   public static final BitSet FOLLOW_STAR_in_varargslist702;
   public static final BitSet FOLLOW_NAME_in_varargslist704;
   public static final BitSet FOLLOW_COMMA_in_varargslist707;
   public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist709;
   public static final BitSet FOLLOW_NAME_in_varargslist711;
   public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist727;
   public static final BitSet FOLLOW_NAME_in_varargslist729;
   public static final BitSet FOLLOW_STAR_in_varargslist759;
   public static final BitSet FOLLOW_NAME_in_varargslist761;
   public static final BitSet FOLLOW_COMMA_in_varargslist764;
   public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist766;
   public static final BitSet FOLLOW_NAME_in_varargslist768;
   public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist778;
   public static final BitSet FOLLOW_NAME_in_varargslist780;
   public static final BitSet FOLLOW_NAME_in_fpdef798;
   public static final BitSet FOLLOW_LPAREN_in_fpdef816;
   public static final BitSet FOLLOW_fplist_in_fpdef818;
   public static final BitSet FOLLOW_RPAREN_in_fpdef820;
   public static final BitSet FOLLOW_LPAREN_in_fpdef828;
   public static final BitSet FOLLOW_fplist_in_fpdef830;
   public static final BitSet FOLLOW_RPAREN_in_fpdef832;
   public static final BitSet FOLLOW_fpdef_in_fplist850;
   public static final BitSet FOLLOW_COMMA_in_fplist866;
   public static final BitSet FOLLOW_fpdef_in_fplist868;
   public static final BitSet FOLLOW_COMMA_in_fplist873;
   public static final BitSet FOLLOW_simple_stmt_in_stmt893;
   public static final BitSet FOLLOW_compound_stmt_in_stmt901;
   public static final BitSet FOLLOW_small_stmt_in_simple_stmt919;
   public static final BitSet FOLLOW_SEMI_in_simple_stmt929;
   public static final BitSet FOLLOW_small_stmt_in_simple_stmt931;
   public static final BitSet FOLLOW_SEMI_in_simple_stmt936;
   public static final BitSet FOLLOW_set_in_simple_stmt940;
   public static final BitSet FOLLOW_expr_stmt_in_small_stmt959;
   public static final BitSet FOLLOW_print_stmt_in_small_stmt974;
   public static final BitSet FOLLOW_del_stmt_in_small_stmt989;
   public static final BitSet FOLLOW_pass_stmt_in_small_stmt1004;
   public static final BitSet FOLLOW_flow_stmt_in_small_stmt1019;
   public static final BitSet FOLLOW_import_stmt_in_small_stmt1034;
   public static final BitSet FOLLOW_global_stmt_in_small_stmt1049;
   public static final BitSet FOLLOW_exec_stmt_in_small_stmt1064;
   public static final BitSet FOLLOW_assert_stmt_in_small_stmt1079;
   public static final BitSet FOLLOW_testlist_in_expr_stmt1114;
   public static final BitSet FOLLOW_augassign_in_expr_stmt1127;
   public static final BitSet FOLLOW_yield_expr_in_expr_stmt1129;
   public static final BitSet FOLLOW_augassign_in_expr_stmt1154;
   public static final BitSet FOLLOW_testlist_in_expr_stmt1156;
   public static final BitSet FOLLOW_testlist_in_expr_stmt1194;
   public static final BitSet FOLLOW_ASSIGN_in_expr_stmt1218;
   public static final BitSet FOLLOW_testlist_in_expr_stmt1220;
   public static final BitSet FOLLOW_ASSIGN_in_expr_stmt1248;
   public static final BitSet FOLLOW_yield_expr_in_expr_stmt1250;
   public static final BitSet FOLLOW_testlist_in_expr_stmt1282;
   public static final BitSet FOLLOW_set_in_augassign0;
   public static final BitSet FOLLOW_PRINT_in_print_stmt1414;
   public static final BitSet FOLLOW_printlist_in_print_stmt1423;
   public static final BitSet FOLLOW_RIGHTSHIFT_in_print_stmt1433;
   public static final BitSet FOLLOW_printlist_in_print_stmt1435;
   public static final BitSet FOLLOW_test_in_printlist1486;
   public static final BitSet FOLLOW_COMMA_in_printlist1497;
   public static final BitSet FOLLOW_test_in_printlist1499;
   public static final BitSet FOLLOW_COMMA_in_printlist1513;
   public static final BitSet FOLLOW_test_in_printlist1523;
   public static final BitSet FOLLOW_DELETE_in_del_stmt1541;
   public static final BitSet FOLLOW_exprlist_in_del_stmt1543;
   public static final BitSet FOLLOW_PASS_in_pass_stmt1561;
   public static final BitSet FOLLOW_break_stmt_in_flow_stmt1579;
   public static final BitSet FOLLOW_continue_stmt_in_flow_stmt1587;
   public static final BitSet FOLLOW_return_stmt_in_flow_stmt1595;
   public static final BitSet FOLLOW_raise_stmt_in_flow_stmt1603;
   public static final BitSet FOLLOW_yield_stmt_in_flow_stmt1611;
   public static final BitSet FOLLOW_BREAK_in_break_stmt1629;
   public static final BitSet FOLLOW_CONTINUE_in_continue_stmt1647;
   public static final BitSet FOLLOW_RETURN_in_return_stmt1665;
   public static final BitSet FOLLOW_testlist_in_return_stmt1674;
   public static final BitSet FOLLOW_yield_expr_in_yield_stmt1708;
   public static final BitSet FOLLOW_RAISE_in_raise_stmt1726;
   public static final BitSet FOLLOW_test_in_raise_stmt1729;
   public static final BitSet FOLLOW_COMMA_in_raise_stmt1732;
   public static final BitSet FOLLOW_test_in_raise_stmt1734;
   public static final BitSet FOLLOW_COMMA_in_raise_stmt1745;
   public static final BitSet FOLLOW_test_in_raise_stmt1747;
   public static final BitSet FOLLOW_import_name_in_import_stmt1771;
   public static final BitSet FOLLOW_import_from_in_import_stmt1779;
   public static final BitSet FOLLOW_IMPORT_in_import_name1797;
   public static final BitSet FOLLOW_dotted_as_names_in_import_name1799;
   public static final BitSet FOLLOW_FROM_in_import_from1818;
   public static final BitSet FOLLOW_DOT_in_import_from1821;
   public static final BitSet FOLLOW_dotted_name_in_import_from1824;
   public static final BitSet FOLLOW_DOT_in_import_from1828;
   public static final BitSet FOLLOW_IMPORT_in_import_from1832;
   public static final BitSet FOLLOW_STAR_in_import_from1843;
   public static final BitSet FOLLOW_import_as_names_in_import_from1855;
   public static final BitSet FOLLOW_LPAREN_in_import_from1867;
   public static final BitSet FOLLOW_import_as_names_in_import_from1869;
   public static final BitSet FOLLOW_COMMA_in_import_from1871;
   public static final BitSet FOLLOW_RPAREN_in_import_from1874;
   public static final BitSet FOLLOW_import_as_name_in_import_as_names1902;
   public static final BitSet FOLLOW_COMMA_in_import_as_names1905;
   public static final BitSet FOLLOW_import_as_name_in_import_as_names1907;
   public static final BitSet FOLLOW_NAME_in_import_as_name1927;
   public static final BitSet FOLLOW_AS_in_import_as_name1930;
   public static final BitSet FOLLOW_NAME_in_import_as_name1932;
   public static final BitSet FOLLOW_dotted_name_in_dotted_as_name1953;
   public static final BitSet FOLLOW_AS_in_dotted_as_name1956;
   public static final BitSet FOLLOW_NAME_in_dotted_as_name1958;
   public static final BitSet FOLLOW_dotted_as_name_in_dotted_as_names1978;
   public static final BitSet FOLLOW_COMMA_in_dotted_as_names1981;
   public static final BitSet FOLLOW_dotted_as_name_in_dotted_as_names1983;
   public static final BitSet FOLLOW_NAME_in_dotted_name2003;
   public static final BitSet FOLLOW_DOT_in_dotted_name2006;
   public static final BitSet FOLLOW_attr_in_dotted_name2008;
   public static final BitSet FOLLOW_GLOBAL_in_global_stmt2028;
   public static final BitSet FOLLOW_NAME_in_global_stmt2030;
   public static final BitSet FOLLOW_COMMA_in_global_stmt2033;
   public static final BitSet FOLLOW_NAME_in_global_stmt2035;
   public static final BitSet FOLLOW_EXEC_in_exec_stmt2055;
   public static final BitSet FOLLOW_expr_in_exec_stmt2057;
   public static final BitSet FOLLOW_IN_in_exec_stmt2060;
   public static final BitSet FOLLOW_test_in_exec_stmt2062;
   public static final BitSet FOLLOW_COMMA_in_exec_stmt2065;
   public static final BitSet FOLLOW_test_in_exec_stmt2067;
   public static final BitSet FOLLOW_ASSERT_in_assert_stmt2089;
   public static final BitSet FOLLOW_test_in_assert_stmt2091;
   public static final BitSet FOLLOW_COMMA_in_assert_stmt2094;
   public static final BitSet FOLLOW_test_in_assert_stmt2096;
   public static final BitSet FOLLOW_if_stmt_in_compound_stmt2116;
   public static final BitSet FOLLOW_while_stmt_in_compound_stmt2124;
   public static final BitSet FOLLOW_for_stmt_in_compound_stmt2132;
   public static final BitSet FOLLOW_try_stmt_in_compound_stmt2140;
   public static final BitSet FOLLOW_with_stmt_in_compound_stmt2148;
   public static final BitSet FOLLOW_funcdef_in_compound_stmt2165;
   public static final BitSet FOLLOW_classdef_in_compound_stmt2182;
   public static final BitSet FOLLOW_decorators_in_compound_stmt2190;
   public static final BitSet FOLLOW_IF_in_if_stmt2208;
   public static final BitSet FOLLOW_test_in_if_stmt2210;
   public static final BitSet FOLLOW_COLON_in_if_stmt2212;
   public static final BitSet FOLLOW_suite_in_if_stmt2214;
   public static final BitSet FOLLOW_elif_clause_in_if_stmt2216;
   public static final BitSet FOLLOW_else_clause_in_elif_clause2235;
   public static final BitSet FOLLOW_ELIF_in_elif_clause2243;
   public static final BitSet FOLLOW_test_in_elif_clause2245;
   public static final BitSet FOLLOW_COLON_in_elif_clause2247;
   public static final BitSet FOLLOW_suite_in_elif_clause2249;
   public static final BitSet FOLLOW_elif_clause_in_elif_clause2260;
   public static final BitSet FOLLOW_ORELSE_in_else_clause2298;
   public static final BitSet FOLLOW_COLON_in_else_clause2300;
   public static final BitSet FOLLOW_suite_in_else_clause2302;
   public static final BitSet FOLLOW_WHILE_in_while_stmt2320;
   public static final BitSet FOLLOW_test_in_while_stmt2322;
   public static final BitSet FOLLOW_COLON_in_while_stmt2324;
   public static final BitSet FOLLOW_suite_in_while_stmt2326;
   public static final BitSet FOLLOW_ORELSE_in_while_stmt2329;
   public static final BitSet FOLLOW_COLON_in_while_stmt2331;
   public static final BitSet FOLLOW_suite_in_while_stmt2333;
   public static final BitSet FOLLOW_FOR_in_for_stmt2353;
   public static final BitSet FOLLOW_exprlist_in_for_stmt2355;
   public static final BitSet FOLLOW_IN_in_for_stmt2357;
   public static final BitSet FOLLOW_testlist_in_for_stmt2359;
   public static final BitSet FOLLOW_COLON_in_for_stmt2361;
   public static final BitSet FOLLOW_suite_in_for_stmt2363;
   public static final BitSet FOLLOW_ORELSE_in_for_stmt2374;
   public static final BitSet FOLLOW_COLON_in_for_stmt2376;
   public static final BitSet FOLLOW_suite_in_for_stmt2378;
   public static final BitSet FOLLOW_TRY_in_try_stmt2402;
   public static final BitSet FOLLOW_COLON_in_try_stmt2404;
   public static final BitSet FOLLOW_suite_in_try_stmt2406;
   public static final BitSet FOLLOW_except_clause_in_try_stmt2416;
   public static final BitSet FOLLOW_ORELSE_in_try_stmt2420;
   public static final BitSet FOLLOW_COLON_in_try_stmt2422;
   public static final BitSet FOLLOW_suite_in_try_stmt2424;
   public static final BitSet FOLLOW_FINALLY_in_try_stmt2429;
   public static final BitSet FOLLOW_COLON_in_try_stmt2431;
   public static final BitSet FOLLOW_suite_in_try_stmt2433;
   public static final BitSet FOLLOW_FINALLY_in_try_stmt2445;
   public static final BitSet FOLLOW_COLON_in_try_stmt2447;
   public static final BitSet FOLLOW_suite_in_try_stmt2449;
   public static final BitSet FOLLOW_WITH_in_with_stmt2478;
   public static final BitSet FOLLOW_with_item_in_with_stmt2480;
   public static final BitSet FOLLOW_COMMA_in_with_stmt2490;
   public static final BitSet FOLLOW_with_item_in_with_stmt2492;
   public static final BitSet FOLLOW_COLON_in_with_stmt2496;
   public static final BitSet FOLLOW_suite_in_with_stmt2498;
   public static final BitSet FOLLOW_test_in_with_item2516;
   public static final BitSet FOLLOW_AS_in_with_item2519;
   public static final BitSet FOLLOW_expr_in_with_item2521;
   public static final BitSet FOLLOW_EXCEPT_in_except_clause2541;
   public static final BitSet FOLLOW_test_in_except_clause2544;
   public static final BitSet FOLLOW_set_in_except_clause2547;
   public static final BitSet FOLLOW_test_in_except_clause2555;
   public static final BitSet FOLLOW_COLON_in_except_clause2561;
   public static final BitSet FOLLOW_suite_in_except_clause2563;
   public static final BitSet FOLLOW_simple_stmt_in_suite2581;
   public static final BitSet FOLLOW_NEWLINE_in_suite2589;
   public static final BitSet FOLLOW_EOF_in_suite2592;
   public static final BitSet FOLLOW_DEDENT_in_suite2611;
   public static final BitSet FOLLOW_EOF_in_suite2615;
   public static final BitSet FOLLOW_INDENT_in_suite2633;
   public static final BitSet FOLLOW_stmt_in_suite2636;
   public static final BitSet FOLLOW_set_in_suite2640;
   public static final BitSet FOLLOW_or_test_in_test2741;
   public static final BitSet FOLLOW_IF_in_test2761;
   public static final BitSet FOLLOW_or_test_in_test2763;
   public static final BitSet FOLLOW_ORELSE_in_test2765;
   public static final BitSet FOLLOW_test_in_test2767;
   public static final BitSet FOLLOW_lambdef_in_test2791;
   public static final BitSet FOLLOW_and_test_in_or_test2809;
   public static final BitSet FOLLOW_OR_in_or_test2822;
   public static final BitSet FOLLOW_and_test_in_or_test2824;
   public static final BitSet FOLLOW_not_test_in_and_test2875;
   public static final BitSet FOLLOW_AND_in_and_test2888;
   public static final BitSet FOLLOW_not_test_in_and_test2890;
   public static final BitSet FOLLOW_NOT_in_not_test2941;
   public static final BitSet FOLLOW_not_test_in_not_test2943;
   public static final BitSet FOLLOW_comparison_in_not_test2951;
   public static final BitSet FOLLOW_expr_in_comparison2969;
   public static final BitSet FOLLOW_comp_op_in_comparison2982;
   public static final BitSet FOLLOW_expr_in_comparison2984;
   public static final BitSet FOLLOW_LESS_in_comp_op3032;
   public static final BitSet FOLLOW_GREATER_in_comp_op3040;
   public static final BitSet FOLLOW_EQUAL_in_comp_op3048;
   public static final BitSet FOLLOW_GREATEREQUAL_in_comp_op3056;
   public static final BitSet FOLLOW_LESSEQUAL_in_comp_op3064;
   public static final BitSet FOLLOW_ALT_NOTEQUAL_in_comp_op3072;
   public static final BitSet FOLLOW_NOTEQUAL_in_comp_op3080;
   public static final BitSet FOLLOW_IN_in_comp_op3088;
   public static final BitSet FOLLOW_NOT_in_comp_op3096;
   public static final BitSet FOLLOW_IN_in_comp_op3098;
   public static final BitSet FOLLOW_IS_in_comp_op3106;
   public static final BitSet FOLLOW_IS_in_comp_op3114;
   public static final BitSet FOLLOW_NOT_in_comp_op3116;
   public static final BitSet FOLLOW_xor_expr_in_expr3134;
   public static final BitSet FOLLOW_VBAR_in_expr3147;
   public static final BitSet FOLLOW_xor_expr_in_expr3149;
   public static final BitSet FOLLOW_and_expr_in_xor_expr3200;
   public static final BitSet FOLLOW_CIRCUMFLEX_in_xor_expr3213;
   public static final BitSet FOLLOW_and_expr_in_xor_expr3215;
   public static final BitSet FOLLOW_shift_expr_in_and_expr3266;
   public static final BitSet FOLLOW_AMPER_in_and_expr3279;
   public static final BitSet FOLLOW_shift_expr_in_and_expr3281;
   public static final BitSet FOLLOW_arith_expr_in_shift_expr3332;
   public static final BitSet FOLLOW_shift_op_in_shift_expr3346;
   public static final BitSet FOLLOW_arith_expr_in_shift_expr3348;
   public static final BitSet FOLLOW_set_in_shift_op0;
   public static final BitSet FOLLOW_term_in_arith_expr3424;
   public static final BitSet FOLLOW_arith_op_in_arith_expr3437;
   public static final BitSet FOLLOW_term_in_arith_expr3439;
   public static final BitSet FOLLOW_set_in_arith_op0;
   public static final BitSet FOLLOW_factor_in_term3515;
   public static final BitSet FOLLOW_term_op_in_term3528;
   public static final BitSet FOLLOW_factor_in_term3530;
   public static final BitSet FOLLOW_set_in_term_op0;
   public static final BitSet FOLLOW_PLUS_in_factor3618;
   public static final BitSet FOLLOW_factor_in_factor3620;
   public static final BitSet FOLLOW_MINUS_in_factor3628;
   public static final BitSet FOLLOW_factor_in_factor3630;
   public static final BitSet FOLLOW_TILDE_in_factor3638;
   public static final BitSet FOLLOW_factor_in_factor3640;
   public static final BitSet FOLLOW_power_in_factor3648;
   public static final BitSet FOLLOW_TRAILBACKSLASH_in_factor3656;
   public static final BitSet FOLLOW_atom_in_power3674;
   public static final BitSet FOLLOW_trailer_in_power3677;
   public static final BitSet FOLLOW_DOUBLESTAR_in_power3689;
   public static final BitSet FOLLOW_factor_in_power3691;
   public static final BitSet FOLLOW_LPAREN_in_atom3715;
   public static final BitSet FOLLOW_yield_expr_in_atom3725;
   public static final BitSet FOLLOW_testlist_gexp_in_atom3735;
   public static final BitSet FOLLOW_RPAREN_in_atom3759;
   public static final BitSet FOLLOW_LBRACK_in_atom3767;
   public static final BitSet FOLLOW_listmaker_in_atom3776;
   public static final BitSet FOLLOW_RBRACK_in_atom3800;
   public static final BitSet FOLLOW_LCURLY_in_atom3808;
   public static final BitSet FOLLOW_dictorsetmaker_in_atom3818;
   public static final BitSet FOLLOW_RCURLY_in_atom3845;
   public static final BitSet FOLLOW_BACKQUOTE_in_atom3854;
   public static final BitSet FOLLOW_testlist_in_atom3856;
   public static final BitSet FOLLOW_BACKQUOTE_in_atom3858;
   public static final BitSet FOLLOW_NAME_in_atom3867;
   public static final BitSet FOLLOW_INT_in_atom3876;
   public static final BitSet FOLLOW_LONGINT_in_atom3885;
   public static final BitSet FOLLOW_FLOAT_in_atom3894;
   public static final BitSet FOLLOW_COMPLEX_in_atom3903;
   public static final BitSet FOLLOW_STRING_in_atom3913;
   public static final BitSet FOLLOW_TRISTRINGPART_in_atom3924;
   public static final BitSet FOLLOW_STRINGPART_in_atom3933;
   public static final BitSet FOLLOW_TRAILBACKSLASH_in_atom3935;
   public static final BitSet FOLLOW_test_in_listmaker3954;
   public static final BitSet FOLLOW_list_for_in_listmaker3965;
   public static final BitSet FOLLOW_COMMA_in_listmaker3985;
   public static final BitSet FOLLOW_test_in_listmaker3987;
   public static final BitSet FOLLOW_COMMA_in_listmaker4002;
   public static final BitSet FOLLOW_test_in_testlist_gexp4022;
   public static final BitSet FOLLOW_COMMA_in_testlist_gexp4044;
   public static final BitSet FOLLOW_test_in_testlist_gexp4046;
   public static final BitSet FOLLOW_COMMA_in_testlist_gexp4051;
   public static final BitSet FOLLOW_comp_for_in_testlist_gexp4078;
   public static final BitSet FOLLOW_LAMBDA_in_lambdef4118;
   public static final BitSet FOLLOW_varargslist_in_lambdef4121;
   public static final BitSet FOLLOW_COLON_in_lambdef4125;
   public static final BitSet FOLLOW_test_in_lambdef4127;
   public static final BitSet FOLLOW_LPAREN_in_trailer4145;
   public static final BitSet FOLLOW_arglist_in_trailer4156;
   public static final BitSet FOLLOW_RPAREN_in_trailer4184;
   public static final BitSet FOLLOW_LBRACK_in_trailer4192;
   public static final BitSet FOLLOW_subscriptlist_in_trailer4194;
   public static final BitSet FOLLOW_RBRACK_in_trailer4196;
   public static final BitSet FOLLOW_DOT_in_trailer4204;
   public static final BitSet FOLLOW_attr_in_trailer4206;
   public static final BitSet FOLLOW_subscript_in_subscriptlist4224;
   public static final BitSet FOLLOW_COMMA_in_subscriptlist4234;
   public static final BitSet FOLLOW_subscript_in_subscriptlist4236;
   public static final BitSet FOLLOW_COMMA_in_subscriptlist4241;
   public static final BitSet FOLLOW_DOT_in_subscript4261;
   public static final BitSet FOLLOW_DOT_in_subscript4263;
   public static final BitSet FOLLOW_DOT_in_subscript4265;
   public static final BitSet FOLLOW_test_in_subscript4284;
   public static final BitSet FOLLOW_COLON_in_subscript4287;
   public static final BitSet FOLLOW_test_in_subscript4290;
   public static final BitSet FOLLOW_sliceop_in_subscript4295;
   public static final BitSet FOLLOW_COLON_in_subscript4316;
   public static final BitSet FOLLOW_test_in_subscript4319;
   public static final BitSet FOLLOW_sliceop_in_subscript4324;
   public static final BitSet FOLLOW_test_in_subscript4334;
   public static final BitSet FOLLOW_COLON_in_sliceop4352;
   public static final BitSet FOLLOW_test_in_sliceop4360;
   public static final BitSet FOLLOW_expr_in_exprlist4400;
   public static final BitSet FOLLOW_COMMA_in_exprlist4411;
   public static final BitSet FOLLOW_expr_in_exprlist4413;
   public static final BitSet FOLLOW_COMMA_in_exprlist4418;
   public static final BitSet FOLLOW_expr_in_exprlist4428;
   public static final BitSet FOLLOW_expr_in_del_list4447;
   public static final BitSet FOLLOW_COMMA_in_del_list4458;
   public static final BitSet FOLLOW_expr_in_del_list4460;
   public static final BitSet FOLLOW_COMMA_in_del_list4465;
   public static final BitSet FOLLOW_test_in_testlist4496;
   public static final BitSet FOLLOW_COMMA_in_testlist4507;
   public static final BitSet FOLLOW_test_in_testlist4509;
   public static final BitSet FOLLOW_COMMA_in_testlist4514;
   public static final BitSet FOLLOW_test_in_testlist4524;
   public static final BitSet FOLLOW_test_in_dictorsetmaker4543;
   public static final BitSet FOLLOW_COLON_in_dictorsetmaker4570;
   public static final BitSet FOLLOW_test_in_dictorsetmaker4572;
   public static final BitSet FOLLOW_comp_for_in_dictorsetmaker4591;
   public static final BitSet FOLLOW_COMMA_in_dictorsetmaker4618;
   public static final BitSet FOLLOW_test_in_dictorsetmaker4620;
   public static final BitSet FOLLOW_COLON_in_dictorsetmaker4622;
   public static final BitSet FOLLOW_test_in_dictorsetmaker4624;
   public static final BitSet FOLLOW_COMMA_in_dictorsetmaker4660;
   public static final BitSet FOLLOW_test_in_dictorsetmaker4662;
   public static final BitSet FOLLOW_COMMA_in_dictorsetmaker4695;
   public static final BitSet FOLLOW_comp_for_in_dictorsetmaker4710;
   public static final BitSet FOLLOW_decorators_in_classdef4739;
   public static final BitSet FOLLOW_CLASS_in_classdef4742;
   public static final BitSet FOLLOW_NAME_in_classdef4744;
   public static final BitSet FOLLOW_LPAREN_in_classdef4747;
   public static final BitSet FOLLOW_testlist_in_classdef4749;
   public static final BitSet FOLLOW_RPAREN_in_classdef4752;
   public static final BitSet FOLLOW_COLON_in_classdef4756;
   public static final BitSet FOLLOW_suite_in_classdef4758;
   public static final BitSet FOLLOW_argument_in_arglist4778;
   public static final BitSet FOLLOW_COMMA_in_arglist4781;
   public static final BitSet FOLLOW_argument_in_arglist4783;
   public static final BitSet FOLLOW_COMMA_in_arglist4798;
   public static final BitSet FOLLOW_STAR_in_arglist4816;
   public static final BitSet FOLLOW_test_in_arglist4818;
   public static final BitSet FOLLOW_COMMA_in_arglist4821;
   public static final BitSet FOLLOW_argument_in_arglist4823;
   public static final BitSet FOLLOW_COMMA_in_arglist4828;
   public static final BitSet FOLLOW_DOUBLESTAR_in_arglist4830;
   public static final BitSet FOLLOW_test_in_arglist4832;
   public static final BitSet FOLLOW_DOUBLESTAR_in_arglist4852;
   public static final BitSet FOLLOW_test_in_arglist4854;
   public static final BitSet FOLLOW_STAR_in_arglist4892;
   public static final BitSet FOLLOW_test_in_arglist4894;
   public static final BitSet FOLLOW_COMMA_in_arglist4897;
   public static final BitSet FOLLOW_argument_in_arglist4899;
   public static final BitSet FOLLOW_COMMA_in_arglist4904;
   public static final BitSet FOLLOW_DOUBLESTAR_in_arglist4906;
   public static final BitSet FOLLOW_test_in_arglist4908;
   public static final BitSet FOLLOW_DOUBLESTAR_in_arglist4918;
   public static final BitSet FOLLOW_test_in_arglist4920;
   public static final BitSet FOLLOW_test_in_argument4938;
   public static final BitSet FOLLOW_ASSIGN_in_argument4950;
   public static final BitSet FOLLOW_test_in_argument4952;
   public static final BitSet FOLLOW_comp_for_in_argument4965;
   public static final BitSet FOLLOW_list_for_in_list_iter5003;
   public static final BitSet FOLLOW_list_if_in_list_iter5011;
   public static final BitSet FOLLOW_FOR_in_list_for5029;
   public static final BitSet FOLLOW_exprlist_in_list_for5031;
   public static final BitSet FOLLOW_IN_in_list_for5033;
   public static final BitSet FOLLOW_testlist_in_list_for5035;
   public static final BitSet FOLLOW_list_iter_in_list_for5038;
   public static final BitSet FOLLOW_IF_in_list_if5058;
   public static final BitSet FOLLOW_test_in_list_if5060;
   public static final BitSet FOLLOW_list_iter_in_list_if5063;
   public static final BitSet FOLLOW_comp_for_in_comp_iter5083;
   public static final BitSet FOLLOW_comp_if_in_comp_iter5091;
   public static final BitSet FOLLOW_FOR_in_comp_for5109;
   public static final BitSet FOLLOW_exprlist_in_comp_for5111;
   public static final BitSet FOLLOW_IN_in_comp_for5113;
   public static final BitSet FOLLOW_or_test_in_comp_for5115;
   public static final BitSet FOLLOW_comp_iter_in_comp_for5117;
   public static final BitSet FOLLOW_IF_in_comp_if5136;
   public static final BitSet FOLLOW_test_in_comp_if5138;
   public static final BitSet FOLLOW_comp_iter_in_comp_if5140;
   public static final BitSet FOLLOW_YIELD_in_yield_expr5159;
   public static final BitSet FOLLOW_testlist_in_yield_expr5161;
   public static final BitSet FOLLOW_LPAREN_in_synpred1_PythonPartial807;
   public static final BitSet FOLLOW_fpdef_in_synpred1_PythonPartial809;
   public static final BitSet FOLLOW_COMMA_in_synpred1_PythonPartial811;
   public static final BitSet FOLLOW_testlist_in_synpred2_PythonPartial1107;
   public static final BitSet FOLLOW_augassign_in_synpred2_PythonPartial1109;
   public static final BitSet FOLLOW_testlist_in_synpred3_PythonPartial1187;
   public static final BitSet FOLLOW_ASSIGN_in_synpred3_PythonPartial1189;
   public static final BitSet FOLLOW_test_in_synpred4_PythonPartial1472;
   public static final BitSet FOLLOW_COMMA_in_synpred4_PythonPartial1474;
   public static final BitSet FOLLOW_decorators_in_synpred5_PythonPartial2157;
   public static final BitSet FOLLOW_DEF_in_synpred5_PythonPartial2160;
   public static final BitSet FOLLOW_decorators_in_synpred6_PythonPartial2174;
   public static final BitSet FOLLOW_CLASS_in_synpred6_PythonPartial2177;
   public static final BitSet FOLLOW_IF_in_synpred7_PythonPartial2752;
   public static final BitSet FOLLOW_or_test_in_synpred7_PythonPartial2754;
   public static final BitSet FOLLOW_ORELSE_in_synpred7_PythonPartial2756;
   public static final BitSet FOLLOW_test_in_synpred8_PythonPartial4274;
   public static final BitSet FOLLOW_COLON_in_synpred8_PythonPartial4276;
   public static final BitSet FOLLOW_COLON_in_synpred9_PythonPartial4308;
   public static final BitSet FOLLOW_expr_in_synpred10_PythonPartial4393;
   public static final BitSet FOLLOW_COMMA_in_synpred10_PythonPartial4395;
   public static final BitSet FOLLOW_test_in_synpred11_PythonPartial4486;
   public static final BitSet FOLLOW_COMMA_in_synpred11_PythonPartial4488;

   public PythonPartialParser(TokenStream input) {
      this(input, new RecognizerSharedState());
   }

   public PythonPartialParser(TokenStream input, RecognizerSharedState state) {
      super(input, state);
      this.errorHandler = new FailFastHandler();
      this.dfa27 = new DFA27(this);
      this.dfa32 = new DFA32(this);
      this.dfa36 = new DFA36(this);
      this.dfa34 = new DFA34(this);
      this.dfa45 = new DFA45(this);
      this.dfa57 = new DFA57(this);
      this.dfa75 = new DFA75(this);
      this.dfa84 = new DFA84(this);
      this.dfa108 = new DFA108(this);
      this.dfa121 = new DFA121(this);
      this.dfa125 = new DFA125(this);
      this.dfa123 = new DFA123(this);
      this.dfa126 = new DFA126(this);
      this.dfa130 = new DFA130(this);
      this.dfa128 = new DFA128(this);
      this.dfa131 = new DFA131(this);
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "/Users/fwierzbicki/hg/jython/jython/grammar/PythonPartial.g";
   }

   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
      Object o = this.errorHandler.recoverFromMismatchedToken(this, input, ttype, follow);
      return o != null ? o : super.recoverFromMismatchedToken(input, ttype, follow);
   }

   public final void single_input() throws RecognitionException {
      try {
         int alt2 = true;
         byte alt2;
         switch (this.input.LA(1)) {
            case 6:
            case 9:
            case 11:
            case 14:
            case 15:
            case 17:
            case 19:
            case 22:
            case 24:
            case 26:
            case 28:
            case 31:
            case 32:
            case 35:
            case 36:
            case 37:
            case 41:
            case 43:
            case 75:
            case 76:
            case 80:
            case 81:
            case 83:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 99:
            case 100:
               alt2 = 2;
               break;
            case 7:
               alt2 = 1;
               break;
            case 8:
            case 10:
            case 12:
            case 13:
            case 20:
            case 21:
            case 23:
            case 29:
            case 30:
            case 33:
            case 34:
            case 44:
            case 45:
            case 46:
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
            case 77:
            case 78:
            case 79:
            case 82:
            case 84:
            case 91:
            case 92:
            case 93:
            case 94:
            case 95:
            case 96:
            case 97:
            case 98:
            default:
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 2, 0, this.input);
               throw nvae;
            case 16:
            case 18:
            case 25:
            case 27:
            case 38:
            case 39:
            case 40:
            case 42:
               alt2 = 3;
         }

         switch (alt2) {
            case 1:
               this.match(this.input, 7, FOLLOW_NEWLINE_in_single_input72);
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_simple_stmt_in_single_input80);
               this.simple_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.pushFollow(FOLLOW_compound_stmt_in_single_input88);
               this.compound_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               int alt1 = 2;
               int LA1_0 = this.input.LA(1);
               if (LA1_0 == 7) {
                  alt1 = 1;
               }

               switch (alt1) {
                  case 1:
                     this.match(this.input, 7, FOLLOW_NEWLINE_in_single_input90);
                     if (this.state.failed) {
                        return;
                     }
                  default:
                     this.match(this.input, -1, FOLLOW_EOF_in_single_input93);
                     if (this.state.failed) {
                        return;
                     }
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void eval_input() throws RecognitionException {
      try {
         int alt3 = 2;
         int LA3_0 = this.input.LA(1);
         if (LA3_0 == 8) {
            alt3 = 1;
         }

         switch (alt3) {
            case 1:
               this.match(this.input, 8, FOLLOW_LEADING_WS_in_eval_input111);
               if (this.state.failed) {
                  return;
               }
         }

         while(true) {
            int alt5 = 2;
            int LA5_0 = this.input.LA(1);
            if (LA5_0 == 7) {
               alt5 = 1;
            }

            switch (alt5) {
               case 1:
                  this.match(this.input, 7, FOLLOW_NEWLINE_in_eval_input115);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               default:
                  alt5 = 2;
                  LA5_0 = this.input.LA(1);
                  if (LA5_0 == 6 || LA5_0 == 9 || LA5_0 >= 31 && LA5_0 <= 32 || LA5_0 == 43 || LA5_0 >= 75 && LA5_0 <= 76 || LA5_0 >= 80 && LA5_0 <= 81 || LA5_0 == 83 || LA5_0 >= 85 && LA5_0 <= 90 || LA5_0 >= 99 && LA5_0 <= 100) {
                     alt5 = 1;
                  }

                  switch (alt5) {
                     case 1:
                        this.pushFollow(FOLLOW_testlist_in_eval_input119);
                        this.testlist();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }
                  }

                  do {
                     int alt6 = 2;
                     int LA6_0 = this.input.LA(1);
                     if (LA6_0 == 7) {
                        alt6 = 1;
                     }

                     switch (alt6) {
                        case 1:
                           this.match(this.input, 7, FOLLOW_NEWLINE_in_eval_input123);
                           break;
                        default:
                           this.match(this.input, -1, FOLLOW_EOF_in_eval_input127);
                           if (this.state.failed) {
                              return;
                           }

                           return;
                     }
                  } while(!this.state.failed);

                  return;
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void dotted_attr() throws RecognitionException {
      try {
         this.match(this.input, 9, FOLLOW_NAME_in_dotted_attr145);
         if (!this.state.failed) {
            int alt8 = true;
            int LA8_0 = this.input.LA(1);
            byte alt8;
            if (LA8_0 == 10) {
               alt8 = 1;
            } else {
               if (LA8_0 != 7 && LA8_0 != 43) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
                  throw nvae;
               }

               alt8 = 2;
            }

            switch (alt8) {
               case 1:
                  int cnt7 = 0;

                  while(true) {
                     int alt7 = 2;
                     int LA7_0 = this.input.LA(1);
                     if (LA7_0 == 10) {
                        alt7 = 1;
                     }

                     switch (alt7) {
                        case 1:
                           this.match(this.input, 10, FOLLOW_DOT_in_dotted_attr156);
                           if (this.state.failed) {
                              return;
                           }

                           this.match(this.input, 9, FOLLOW_NAME_in_dotted_attr158);
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt7;
                           break;
                        default:
                           if (cnt7 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(7, this.input);
                              throw eee;
                           }

                           return;
                     }
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void attr() throws RecognitionException {
      try {
         if (this.input.LA(1) == 9 || this.input.LA(1) >= 11 && this.input.LA(1) <= 41) {
            this.input.consume();
            this.state.errorRecovery = false;
            this.state.failed = false;
         } else if (this.state.backtracking > 0) {
            this.state.failed = true;
         } else {
            MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
            throw mse;
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void decorator() throws RecognitionException {
      try {
         this.match(this.input, 42, FOLLOW_AT_in_decorator464);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_dotted_attr_in_decorator466);
            this.dotted_attr();
            --this.state._fsp;
            if (!this.state.failed) {
               int alt10 = true;
               int LA10_0 = this.input.LA(1);
               byte alt10;
               if (LA10_0 == 43) {
                  alt10 = 1;
               } else {
                  if (LA10_0 != 7) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
                     throw nvae;
                  }

                  alt10 = 2;
               }

               switch (alt10) {
                  case 1:
                     this.match(this.input, 43, FOLLOW_LPAREN_in_decorator474);
                     if (this.state.failed) {
                        return;
                     } else {
                        int alt9 = true;
                        int LA9_0 = this.input.LA(1);
                        byte alt9;
                        if (LA9_0 != 6 && LA9_0 != 9 && (LA9_0 < 31 || LA9_0 > 32) && LA9_0 != 43 && (LA9_0 < 48 || LA9_0 > 49) && (LA9_0 < 75 || LA9_0 > 76) && (LA9_0 < 80 || LA9_0 > 81) && LA9_0 != 83 && (LA9_0 < 85 || LA9_0 > 90) && (LA9_0 < 99 || LA9_0 > 100)) {
                           if (LA9_0 != 44) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              NoViableAltException nvae = new NoViableAltException("", 9, 0, this.input);
                              throw nvae;
                           }

                           alt9 = 2;
                        } else {
                           alt9 = 1;
                        }

                        switch (alt9) {
                           case 1:
                              this.pushFollow(FOLLOW_arglist_in_decorator484);
                              this.arglist();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return;
                              }
                           case 2:
                           default:
                              this.match(this.input, 44, FOLLOW_RPAREN_in_decorator508);
                              if (this.state.failed) {
                                 return;
                              }
                        }
                     }
                  case 2:
                  default:
                     this.match(this.input, 7, FOLLOW_NEWLINE_in_decorator522);
                     if (!this.state.failed) {
                        ;
                     }
               }
            }
         }
      } catch (RecognitionException var9) {
         throw var9;
      } finally {
         ;
      }
   }

   public final void decorators() throws RecognitionException {
      try {
         int cnt11 = 0;

         while(true) {
            int alt11 = 2;
            int LA11_0 = this.input.LA(1);
            if (LA11_0 == 42) {
               alt11 = 1;
            }

            switch (alt11) {
               case 1:
                  this.pushFollow(FOLLOW_decorator_in_decorators540);
                  this.decorator();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  ++cnt11;
                  break;
               default:
                  if (cnt11 >= 1) {
                     return;
                  }

                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  EarlyExitException eee = new EarlyExitException(11, this.input);
                  throw eee;
            }
         }
      } catch (RecognitionException var8) {
         throw var8;
      } finally {
         ;
      }
   }

   public final void funcdef() throws RecognitionException {
      try {
         int alt12 = 2;
         int LA12_0 = this.input.LA(1);
         if (LA12_0 == 42) {
            alt12 = 1;
         }

         switch (alt12) {
            case 1:
               this.pushFollow(FOLLOW_decorators_in_funcdef559);
               this.decorators();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
            default:
               this.match(this.input, 18, FOLLOW_DEF_in_funcdef562);
               if (!this.state.failed) {
                  this.match(this.input, 9, FOLLOW_NAME_in_funcdef564);
                  if (!this.state.failed) {
                     this.pushFollow(FOLLOW_parameters_in_funcdef566);
                     this.parameters();
                     --this.state._fsp;
                     if (!this.state.failed) {
                        this.match(this.input, 45, FOLLOW_COLON_in_funcdef568);
                        if (!this.state.failed) {
                           this.pushFollow(FOLLOW_suite_in_funcdef570);
                           this.suite();
                           --this.state._fsp;
                           if (!this.state.failed) {
                              ;
                           }
                        }
                     }
                  }
               }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void parameters() throws RecognitionException {
      try {
         this.match(this.input, 43, FOLLOW_LPAREN_in_parameters588);
         if (!this.state.failed) {
            int alt13 = true;
            int LA13_0 = this.input.LA(1);
            byte alt13;
            if (LA13_0 != 9 && LA13_0 != 43 && (LA13_0 < 48 || LA13_0 > 49)) {
               if (LA13_0 != 44) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);
                  throw nvae;
               }

               alt13 = 2;
            } else {
               alt13 = 1;
            }

            switch (alt13) {
               case 1:
                  this.pushFollow(FOLLOW_varargslist_in_parameters597);
                  this.varargslist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
               case 2:
               default:
                  this.match(this.input, 44, FOLLOW_RPAREN_in_parameters621);
                  if (!this.state.failed) {
                     ;
                  }
            }
         }
      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void defparameter() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_fpdef_in_defparameter639);
         this.fpdef();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt14 = 2;
            int LA14_0 = this.input.LA(1);
            if (LA14_0 == 46) {
               alt14 = 1;
            }

            switch (alt14) {
               case 1:
                  this.match(this.input, 46, FOLLOW_ASSIGN_in_defparameter642);
                  if (this.state.failed) {
                     return;
                  } else {
                     this.pushFollow(FOLLOW_test_in_defparameter644);
                     this.test();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  }
               default:
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void varargslist() throws RecognitionException {
      try {
         int alt20 = true;
         byte alt20;
         switch (this.input.LA(1)) {
            case 9:
            case 43:
               alt20 = 1;
               break;
            case 48:
               alt20 = 2;
               break;
            case 49:
               alt20 = 3;
               break;
            default:
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 20, 0, this.input);
               throw nvae;
         }

         byte alt18;
         int LA18_0;
         switch (alt20) {
            case 1:
               this.pushFollow(FOLLOW_defparameter_in_varargslist666);
               this.defparameter();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               while(true) {
                  alt18 = 2;
                  LA18_0 = this.input.LA(1);
                  if (LA18_0 == 47) {
                     int LA15_1 = this.input.LA(2);
                     if (LA15_1 == 9 || LA15_1 == 43) {
                        alt18 = 1;
                     }
                  }

                  switch (alt18) {
                     case 1:
                        this.match(this.input, 47, FOLLOW_COMMA_in_varargslist676);
                        if (this.state.failed) {
                           return;
                        }

                        this.pushFollow(FOLLOW_defparameter_in_varargslist678);
                        this.defparameter();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     default:
                        alt18 = 2;
                        LA18_0 = this.input.LA(1);
                        if (LA18_0 == 47) {
                           alt18 = 1;
                        }

                        switch (alt18) {
                           case 1:
                              this.match(this.input, 47, FOLLOW_COMMA_in_varargslist689);
                              if (this.state.failed) {
                                 return;
                              }

                              int alt17 = 3;
                              int LA17_0 = this.input.LA(1);
                              if (LA17_0 == 48) {
                                 alt17 = 1;
                              } else if (LA17_0 == 49) {
                                 alt17 = 2;
                              }

                              switch (alt17) {
                                 case 1:
                                    this.match(this.input, 48, FOLLOW_STAR_in_varargslist702);
                                    if (this.state.failed) {
                                       return;
                                    }

                                    this.match(this.input, 9, FOLLOW_NAME_in_varargslist704);
                                    if (this.state.failed) {
                                       return;
                                    }

                                    int alt16 = 2;
                                    int LA16_0 = this.input.LA(1);
                                    if (LA16_0 == 47) {
                                       alt16 = 1;
                                    }

                                    switch (alt16) {
                                       case 1:
                                          this.match(this.input, 47, FOLLOW_COMMA_in_varargslist707);
                                          if (this.state.failed) {
                                             return;
                                          }

                                          this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_varargslist709);
                                          if (this.state.failed) {
                                             return;
                                          }

                                          this.match(this.input, 9, FOLLOW_NAME_in_varargslist711);
                                          if (this.state.failed) {
                                             return;
                                          }

                                          return;
                                       default:
                                          return;
                                    }
                                 case 2:
                                    this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_varargslist727);
                                    if (this.state.failed) {
                                       return;
                                    }

                                    this.match(this.input, 9, FOLLOW_NAME_in_varargslist729);
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
               this.match(this.input, 48, FOLLOW_STAR_in_varargslist759);
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 9, FOLLOW_NAME_in_varargslist761);
               if (this.state.failed) {
                  return;
               }

               alt18 = 2;
               LA18_0 = this.input.LA(1);
               if (LA18_0 == 47) {
                  alt18 = 1;
               }

               switch (alt18) {
                  case 1:
                     this.match(this.input, 47, FOLLOW_COMMA_in_varargslist764);
                     if (this.state.failed) {
                        return;
                     }

                     this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_varargslist766);
                     if (this.state.failed) {
                        return;
                     }

                     this.match(this.input, 9, FOLLOW_NAME_in_varargslist768);
                     if (this.state.failed) {
                        return;
                     }

                     return;
                  default:
                     return;
               }
            case 3:
               this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_varargslist778);
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 9, FOLLOW_NAME_in_varargslist780);
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var11) {
         throw var11;
      } finally {
         ;
      }
   }

   public final void fpdef() throws RecognitionException {
      try {
         int alt21 = true;
         int LA21_0 = this.input.LA(1);
         byte alt21;
         if (LA21_0 == 9) {
            alt21 = 1;
         } else {
            if (LA21_0 != 43) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 21, 0, this.input);
               throw nvae;
            }

            int LA21_2 = this.input.LA(2);
            if (this.synpred1_PythonPartial()) {
               alt21 = 2;
            } else {
               alt21 = 3;
            }
         }

         switch (alt21) {
            case 1:
               this.match(this.input, 9, FOLLOW_NAME_in_fpdef798);
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.match(this.input, 43, FOLLOW_LPAREN_in_fpdef816);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_fplist_in_fpdef818);
               this.fplist();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 44, FOLLOW_RPAREN_in_fpdef820);
               if (this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.match(this.input, 43, FOLLOW_LPAREN_in_fpdef828);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_fplist_in_fpdef830);
               this.fplist();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 44, FOLLOW_RPAREN_in_fpdef832);
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void fplist() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_fpdef_in_fplist850);
         this.fpdef();
         --this.state._fsp;
         if (!this.state.failed) {
            do {
               int alt23 = 2;
               int LA23_0 = this.input.LA(1);
               if (LA23_0 == 47) {
                  int LA22_1 = this.input.LA(2);
                  if (LA22_1 == 9 || LA22_1 == 43) {
                     alt23 = 1;
                  }
               }

               switch (alt23) {
                  case 1:
                     this.match(this.input, 47, FOLLOW_COMMA_in_fplist866);
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_fpdef_in_fplist868);
                     this.fpdef();
                     --this.state._fsp;
                     break;
                  default:
                     alt23 = 2;
                     LA23_0 = this.input.LA(1);
                     if (LA23_0 == 47) {
                        alt23 = 1;
                     }

                     switch (alt23) {
                        case 1:
                           this.match(this.input, 47, FOLLOW_COMMA_in_fplist873);
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           return;
                     }
               }
            } while(!this.state.failed);

         }
      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void stmt() throws RecognitionException {
      try {
         int alt24 = true;
         int LA24_0 = this.input.LA(1);
         byte alt24;
         if (LA24_0 == 6 || LA24_0 == 9 || LA24_0 == 11 || LA24_0 >= 14 && LA24_0 <= 15 || LA24_0 == 17 || LA24_0 == 19 || LA24_0 == 22 || LA24_0 == 24 || LA24_0 == 26 || LA24_0 == 28 || LA24_0 >= 31 && LA24_0 <= 32 || LA24_0 >= 35 && LA24_0 <= 37 || LA24_0 == 41 || LA24_0 == 43 || LA24_0 >= 75 && LA24_0 <= 76 || LA24_0 >= 80 && LA24_0 <= 81 || LA24_0 == 83 || LA24_0 >= 85 && LA24_0 <= 90 || LA24_0 >= 99 && LA24_0 <= 100) {
            alt24 = 1;
         } else {
            if (LA24_0 != 16 && LA24_0 != 18 && LA24_0 != 25 && LA24_0 != 27 && (LA24_0 < 38 || LA24_0 > 40) && LA24_0 != 42) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 24, 0, this.input);
               throw nvae;
            }

            alt24 = 2;
         }

         switch (alt24) {
            case 1:
               this.pushFollow(FOLLOW_simple_stmt_in_stmt893);
               this.simple_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_compound_stmt_in_stmt901);
               this.compound_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void simple_stmt() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_small_stmt_in_simple_stmt919);
         this.small_stmt();
         --this.state._fsp;
         if (!this.state.failed) {
            do {
               int alt26 = 2;
               int LA26_0 = this.input.LA(1);
               if (LA26_0 == 50) {
                  int LA25_1 = this.input.LA(2);
                  if (LA25_1 == 6 || LA25_1 == 9 || LA25_1 == 11 || LA25_1 >= 14 && LA25_1 <= 15 || LA25_1 == 17 || LA25_1 == 19 || LA25_1 == 22 || LA25_1 == 24 || LA25_1 == 26 || LA25_1 == 28 || LA25_1 >= 31 && LA25_1 <= 32 || LA25_1 >= 35 && LA25_1 <= 37 || LA25_1 == 41 || LA25_1 == 43 || LA25_1 >= 75 && LA25_1 <= 76 || LA25_1 >= 80 && LA25_1 <= 81 || LA25_1 == 83 || LA25_1 >= 85 && LA25_1 <= 90 || LA25_1 >= 99 && LA25_1 <= 100) {
                     alt26 = 1;
                  }
               }

               switch (alt26) {
                  case 1:
                     this.match(this.input, 50, FOLLOW_SEMI_in_simple_stmt929);
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_small_stmt_in_simple_stmt931);
                     this.small_stmt();
                     --this.state._fsp;
                     break;
                  default:
                     alt26 = 2;
                     LA26_0 = this.input.LA(1);
                     if (LA26_0 == 50) {
                        alt26 = 1;
                     }

                     switch (alt26) {
                        case 1:
                           this.match(this.input, 50, FOLLOW_SEMI_in_simple_stmt936);
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           if (this.input.LA(1) != -1 && this.input.LA(1) != 7) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                              throw mse;
                           }

                           this.input.consume();
                           this.state.errorRecovery = false;
                           this.state.failed = false;
                           return;
                     }
               }
            } while(!this.state.failed);

         }
      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void small_stmt() throws RecognitionException {
      try {
         int alt27 = true;
         int alt27 = this.dfa27.predict(this.input);
         switch (alt27) {
            case 1:
               this.pushFollow(FOLLOW_expr_stmt_in_small_stmt959);
               this.expr_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_print_stmt_in_small_stmt974);
               this.print_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.pushFollow(FOLLOW_del_stmt_in_small_stmt989);
               this.del_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 4:
               this.pushFollow(FOLLOW_pass_stmt_in_small_stmt1004);
               this.pass_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 5:
               this.pushFollow(FOLLOW_flow_stmt_in_small_stmt1019);
               this.flow_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 6:
               this.pushFollow(FOLLOW_import_stmt_in_small_stmt1034);
               this.import_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 7:
               this.pushFollow(FOLLOW_global_stmt_in_small_stmt1049);
               this.global_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 8:
               this.pushFollow(FOLLOW_exec_stmt_in_small_stmt1064);
               this.exec_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 9:
               this.pushFollow(FOLLOW_assert_stmt_in_small_stmt1079);
               this.assert_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void expr_stmt() throws RecognitionException {
      try {
         int alt32 = true;
         int alt32 = this.dfa32.predict(this.input);
         boolean alt31;
         int LA31_0;
         int cnt30;
         NoViableAltException nvae;
         byte alt31;
         NoViableAltException nvae;
         switch (alt32) {
            case 1:
               this.pushFollow(FOLLOW_testlist_in_expr_stmt1114);
               this.testlist();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               alt31 = true;
               LA31_0 = this.input.LA(1);
               if (LA31_0 < 51 || LA31_0 > 62) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     nvae = new NoViableAltException("", 28, 0, this.input);
                     throw nvae;
                  }
               }

               cnt30 = this.input.LA(2);
               if (cnt30 == 41) {
                  alt31 = 1;
               } else {
                  if (cnt30 != 6 && cnt30 != 9 && (cnt30 < 31 || cnt30 > 32) && cnt30 != 43 && (cnt30 < 75 || cnt30 > 76) && (cnt30 < 80 || cnt30 > 81) && cnt30 != 83 && (cnt30 < 85 || cnt30 > 90) && (cnt30 < 99 || cnt30 > 100)) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvae = new NoViableAltException("", 28, 1, this.input);
                     throw nvae;
                  }

                  alt31 = 2;
               }

               switch (alt31) {
                  case 1:
                     this.pushFollow(FOLLOW_augassign_in_expr_stmt1127);
                     this.augassign();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_yield_expr_in_expr_stmt1129);
                     this.yield_expr();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }

                     return;
                  case 2:
                     this.pushFollow(FOLLOW_augassign_in_expr_stmt1154);
                     this.augassign();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_testlist_in_expr_stmt1156);
                     this.testlist();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }

                     return;
                  default:
                     return;
               }
            case 2:
               this.pushFollow(FOLLOW_testlist_in_expr_stmt1194);
               this.testlist();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               alt31 = true;
               LA31_0 = this.input.LA(1);
               if (LA31_0 != -1 && LA31_0 != 7 && LA31_0 != 50) {
                  if (LA31_0 != 46) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvae = new NoViableAltException("", 31, 0, this.input);
                     throw nvae;
                  }

                  cnt30 = this.input.LA(2);
                  if (cnt30 == 41) {
                     alt31 = 3;
                  } else {
                     if (cnt30 != 6 && cnt30 != 9 && (cnt30 < 31 || cnt30 > 32) && cnt30 != 43 && (cnt30 < 75 || cnt30 > 76) && (cnt30 < 80 || cnt30 > 81) && cnt30 != 83 && (cnt30 < 85 || cnt30 > 90) && (cnt30 < 99 || cnt30 > 100)) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        nvae = new NoViableAltException("", 31, 2, this.input);
                        throw nvae;
                     }

                     alt31 = 2;
                  }
               } else {
                  alt31 = 1;
               }

               int LA30_0;
               EarlyExitException eee;
               byte alt30;
               switch (alt31) {
                  case 1:
                  default:
                     return;
                  case 2:
                     cnt30 = 0;

                     while(true) {
                        alt30 = 2;
                        LA30_0 = this.input.LA(1);
                        if (LA30_0 == 46) {
                           alt30 = 1;
                        }

                        switch (alt30) {
                           case 1:
                              this.match(this.input, 46, FOLLOW_ASSIGN_in_expr_stmt1218);
                              if (this.state.failed) {
                                 return;
                              }

                              this.pushFollow(FOLLOW_testlist_in_expr_stmt1220);
                              this.testlist();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return;
                              }

                              ++cnt30;
                              break;
                           default:
                              if (cnt30 < 1) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 eee = new EarlyExitException(29, this.input);
                                 throw eee;
                              }

                              return;
                        }
                     }
                  case 3:
                     cnt30 = 0;

                     while(true) {
                        alt30 = 2;
                        LA30_0 = this.input.LA(1);
                        if (LA30_0 == 46) {
                           alt30 = 1;
                        }

                        switch (alt30) {
                           case 1:
                              this.match(this.input, 46, FOLLOW_ASSIGN_in_expr_stmt1248);
                              if (this.state.failed) {
                                 return;
                              }

                              this.pushFollow(FOLLOW_yield_expr_in_expr_stmt1250);
                              this.yield_expr();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return;
                              }

                              ++cnt30;
                              break;
                           default:
                              if (cnt30 < 1) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 eee = new EarlyExitException(30, this.input);
                                 throw eee;
                              }

                              return;
                        }
                     }
               }
            case 3:
               this.pushFollow(FOLLOW_testlist_in_expr_stmt1282);
               this.testlist();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var11) {
         throw var11;
      } finally {
         ;
      }
   }

   public final void augassign() throws RecognitionException {
      try {
         if (this.input.LA(1) >= 51 && this.input.LA(1) <= 62) {
            this.input.consume();
            this.state.errorRecovery = false;
            this.state.failed = false;
         } else if (this.state.backtracking > 0) {
            this.state.failed = true;
         } else {
            MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
            throw mse;
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void print_stmt() throws RecognitionException {
      try {
         this.match(this.input, 11, FOLLOW_PRINT_in_print_stmt1414);
         if (!this.state.failed) {
            int alt33 = true;
            byte alt33;
            switch (this.input.LA(1)) {
               case -1:
               case 7:
               case 50:
                  alt33 = 3;
                  break;
               case 6:
               case 9:
               case 31:
               case 32:
               case 43:
               case 75:
               case 76:
               case 80:
               case 81:
               case 83:
               case 85:
               case 86:
               case 87:
               case 88:
               case 89:
               case 90:
               case 99:
               case 100:
                  alt33 = 1;
                  break;
               case 63:
                  alt33 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 33, 0, this.input);
                  throw nvae;
            }

            switch (alt33) {
               case 1:
                  this.pushFollow(FOLLOW_printlist_in_print_stmt1423);
                  this.printlist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.match(this.input, 63, FOLLOW_RIGHTSHIFT_in_print_stmt1433);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_printlist_in_print_stmt1435);
                  this.printlist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
               case 3:
            }

         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void printlist() throws RecognitionException {
      try {
         int alt36 = true;
         int alt36 = this.dfa36.predict(this.input);
         switch (alt36) {
            case 1:
               this.pushFollow(FOLLOW_test_in_printlist1486);
               this.test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               while(true) {
                  int alt34 = true;
                  int alt34 = this.dfa34.predict(this.input);
                  switch (alt34) {
                     case 1:
                        this.match(this.input, 47, FOLLOW_COMMA_in_printlist1497);
                        if (this.state.failed) {
                           return;
                        }

                        this.pushFollow(FOLLOW_test_in_printlist1499);
                        this.test();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     default:
                        int alt35 = 2;
                        int LA35_0 = this.input.LA(1);
                        if (LA35_0 == 47) {
                           alt35 = 1;
                        }

                        switch (alt35) {
                           case 1:
                              this.match(this.input, 47, FOLLOW_COMMA_in_printlist1513);
                              if (this.state.failed) {
                                 return;
                              }

                              return;
                           default:
                              return;
                        }
                  }
               }
            case 2:
               this.pushFollow(FOLLOW_test_in_printlist1523);
               this.test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void del_stmt() throws RecognitionException {
      try {
         this.match(this.input, 19, FOLLOW_DELETE_in_del_stmt1541);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_exprlist_in_del_stmt1543);
            this.exprlist();
            --this.state._fsp;
            if (!this.state.failed) {
               ;
            }
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void pass_stmt() throws RecognitionException {
      try {
         this.match(this.input, 35, FOLLOW_PASS_in_pass_stmt1561);
         if (!this.state.failed) {
            ;
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void flow_stmt() throws RecognitionException {
      try {
         int alt37 = true;
         byte alt37;
         switch (this.input.LA(1)) {
            case 15:
               alt37 = 1;
               break;
            case 17:
               alt37 = 2;
               break;
            case 36:
               alt37 = 4;
               break;
            case 37:
               alt37 = 3;
               break;
            case 41:
               alt37 = 5;
               break;
            default:
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 37, 0, this.input);
               throw nvae;
         }

         switch (alt37) {
            case 1:
               this.pushFollow(FOLLOW_break_stmt_in_flow_stmt1579);
               this.break_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_continue_stmt_in_flow_stmt1587);
               this.continue_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.pushFollow(FOLLOW_return_stmt_in_flow_stmt1595);
               this.return_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 4:
               this.pushFollow(FOLLOW_raise_stmt_in_flow_stmt1603);
               this.raise_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 5:
               this.pushFollow(FOLLOW_yield_stmt_in_flow_stmt1611);
               this.yield_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void break_stmt() throws RecognitionException {
      try {
         this.match(this.input, 15, FOLLOW_BREAK_in_break_stmt1629);
         if (!this.state.failed) {
            ;
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void continue_stmt() throws RecognitionException {
      try {
         this.match(this.input, 17, FOLLOW_CONTINUE_in_continue_stmt1647);
         if (!this.state.failed) {
            ;
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void return_stmt() throws RecognitionException {
      try {
         this.match(this.input, 37, FOLLOW_RETURN_in_return_stmt1665);
         if (!this.state.failed) {
            int alt38 = true;
            int LA38_0 = this.input.LA(1);
            byte alt38;
            if (LA38_0 != 6 && LA38_0 != 9 && (LA38_0 < 31 || LA38_0 > 32) && LA38_0 != 43 && (LA38_0 < 75 || LA38_0 > 76) && (LA38_0 < 80 || LA38_0 > 81) && LA38_0 != 83 && (LA38_0 < 85 || LA38_0 > 90) && (LA38_0 < 99 || LA38_0 > 100)) {
               if (LA38_0 != -1 && LA38_0 != 7 && LA38_0 != 50) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 38, 0, this.input);
                  throw nvae;
               }

               alt38 = 2;
            } else {
               alt38 = 1;
            }

            switch (alt38) {
               case 1:
                  this.pushFollow(FOLLOW_testlist_in_return_stmt1674);
                  this.testlist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void yield_stmt() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_yield_expr_in_yield_stmt1708);
         this.yield_expr();
         --this.state._fsp;
         if (!this.state.failed) {
            ;
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void raise_stmt() throws RecognitionException {
      try {
         this.match(this.input, 36, FOLLOW_RAISE_in_raise_stmt1726);
         if (!this.state.failed) {
            int alt41 = 2;
            int LA41_0 = this.input.LA(1);
            if (LA41_0 == 6 || LA41_0 == 9 || LA41_0 >= 31 && LA41_0 <= 32 || LA41_0 == 43 || LA41_0 >= 75 && LA41_0 <= 76 || LA41_0 >= 80 && LA41_0 <= 81 || LA41_0 == 83 || LA41_0 >= 85 && LA41_0 <= 90 || LA41_0 >= 99 && LA41_0 <= 100) {
               alt41 = 1;
            }

            switch (alt41) {
               case 1:
                  this.pushFollow(FOLLOW_test_in_raise_stmt1729);
                  this.test();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  } else {
                     int alt40 = 2;
                     int LA40_0 = this.input.LA(1);
                     if (LA40_0 == 47) {
                        alt40 = 1;
                     }

                     switch (alt40) {
                        case 1:
                           this.match(this.input, 47, FOLLOW_COMMA_in_raise_stmt1732);
                           if (this.state.failed) {
                              return;
                           } else {
                              this.pushFollow(FOLLOW_test_in_raise_stmt1734);
                              this.test();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return;
                              } else {
                                 int alt39 = 2;
                                 int LA39_0 = this.input.LA(1);
                                 if (LA39_0 == 47) {
                                    alt39 = 1;
                                 }

                                 switch (alt39) {
                                    case 1:
                                       this.match(this.input, 47, FOLLOW_COMMA_in_raise_stmt1745);
                                       if (this.state.failed) {
                                          return;
                                       } else {
                                          this.pushFollow(FOLLOW_test_in_raise_stmt1747);
                                          this.test();
                                          --this.state._fsp;
                                          if (this.state.failed) {
                                             return;
                                          }
                                       }
                                 }
                              }
                           }
                     }
                  }
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void import_stmt() throws RecognitionException {
      try {
         int alt42 = true;
         int LA42_0 = this.input.LA(1);
         byte alt42;
         if (LA42_0 == 28) {
            alt42 = 1;
         } else {
            if (LA42_0 != 24) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 42, 0, this.input);
               throw nvae;
            }

            alt42 = 2;
         }

         switch (alt42) {
            case 1:
               this.pushFollow(FOLLOW_import_name_in_import_stmt1771);
               this.import_name();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_import_from_in_import_stmt1779);
               this.import_from();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void import_name() throws RecognitionException {
      try {
         this.match(this.input, 28, FOLLOW_IMPORT_in_import_name1797);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_dotted_as_names_in_import_name1799);
            this.dotted_as_names();
            --this.state._fsp;
            if (!this.state.failed) {
               ;
            }
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void import_from() throws RecognitionException {
      try {
         this.match(this.input, 24, FOLLOW_FROM_in_import_from1818);
         if (!this.state.failed) {
            int LA46_0;
            byte alt47;
            int alt45 = true;
            int alt45 = this.dfa45.predict(this.input);
            int alt44;
            label236:
            switch (alt45) {
               case 1:
                  while(true) {
                     alt47 = 2;
                     alt44 = this.input.LA(1);
                     if (alt44 == 10) {
                        alt47 = 1;
                     }

                     switch (alt47) {
                        case 1:
                           this.match(this.input, 10, FOLLOW_DOT_in_import_from1821);
                           if (this.state.failed) {
                              return;
                           }
                           break;
                        default:
                           this.pushFollow(FOLLOW_dotted_name_in_import_from1824);
                           this.dotted_name();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }
                           break label236;
                     }
                  }
               case 2:
                  int cnt44 = 0;

                  label229:
                  while(true) {
                     alt44 = 2;
                     LA46_0 = this.input.LA(1);
                     if (LA46_0 == 10) {
                        alt44 = 1;
                     }

                     switch (alt44) {
                        case 1:
                           this.match(this.input, 10, FOLLOW_DOT_in_import_from1828);
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt44;
                           break;
                        default:
                           if (cnt44 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(44, this.input);
                              throw eee;
                           }
                           break label229;
                     }
                  }
            }

            this.match(this.input, 28, FOLLOW_IMPORT_in_import_from1832);
            if (!this.state.failed) {
               int alt47 = true;
               switch (this.input.LA(1)) {
                  case 9:
                     alt47 = 2;
                     break;
                  case 43:
                     alt47 = 3;
                     break;
                  case 48:
                     alt47 = 1;
                     break;
                  default:
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 47, 0, this.input);
                     throw nvae;
               }

               switch (alt47) {
                  case 1:
                     this.match(this.input, 48, FOLLOW_STAR_in_import_from1843);
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 2:
                     this.pushFollow(FOLLOW_import_as_names_in_import_from1855);
                     this.import_as_names();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 3:
                     this.match(this.input, 43, FOLLOW_LPAREN_in_import_from1867);
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_import_as_names_in_import_from1869);
                     this.import_as_names();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }

                     int alt46 = 2;
                     LA46_0 = this.input.LA(1);
                     if (LA46_0 == 47) {
                        alt46 = 1;
                     }

                     switch (alt46) {
                        case 1:
                           this.match(this.input, 47, FOLLOW_COMMA_in_import_from1871);
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           this.match(this.input, 44, FOLLOW_RPAREN_in_import_from1874);
                           if (this.state.failed) {
                              return;
                           }
                     }
               }

            }
         }
      } catch (RecognitionException var9) {
         throw var9;
      } finally {
         ;
      }
   }

   public final void import_as_names() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_import_as_name_in_import_as_names1902);
         this.import_as_name();
         --this.state._fsp;
         if (!this.state.failed) {
            do {
               int alt48 = 2;
               int LA48_0 = this.input.LA(1);
               if (LA48_0 == 47) {
                  int LA48_2 = this.input.LA(2);
                  if (LA48_2 == 9) {
                     alt48 = 1;
                  }
               }

               switch (alt48) {
                  case 1:
                     this.match(this.input, 47, FOLLOW_COMMA_in_import_as_names1905);
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_import_as_name_in_import_as_names1907);
                     this.import_as_name();
                     --this.state._fsp;
                     break;
                  default:
                     return;
               }
            } while(!this.state.failed);

         }
      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void import_as_name() throws RecognitionException {
      try {
         this.match(this.input, 9, FOLLOW_NAME_in_import_as_name1927);
         if (!this.state.failed) {
            int alt49 = 2;
            int LA49_0 = this.input.LA(1);
            if (LA49_0 == 13) {
               alt49 = 1;
            }

            switch (alt49) {
               case 1:
                  this.match(this.input, 13, FOLLOW_AS_in_import_as_name1930);
                  if (this.state.failed) {
                     return;
                  } else {
                     this.match(this.input, 9, FOLLOW_NAME_in_import_as_name1932);
                     if (this.state.failed) {
                        return;
                     }
                  }
               default:
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void dotted_as_name() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_dotted_name_in_dotted_as_name1953);
         this.dotted_name();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt50 = 2;
            int LA50_0 = this.input.LA(1);
            if (LA50_0 == 13) {
               alt50 = 1;
            }

            switch (alt50) {
               case 1:
                  this.match(this.input, 13, FOLLOW_AS_in_dotted_as_name1956);
                  if (this.state.failed) {
                     return;
                  } else {
                     this.match(this.input, 9, FOLLOW_NAME_in_dotted_as_name1958);
                     if (this.state.failed) {
                        return;
                     }
                  }
               default:
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void dotted_as_names() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_dotted_as_name_in_dotted_as_names1978);
         this.dotted_as_name();
         --this.state._fsp;
         if (!this.state.failed) {
            do {
               int alt51 = 2;
               int LA51_0 = this.input.LA(1);
               if (LA51_0 == 47) {
                  alt51 = 1;
               }

               switch (alt51) {
                  case 1:
                     this.match(this.input, 47, FOLLOW_COMMA_in_dotted_as_names1981);
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_dotted_as_name_in_dotted_as_names1983);
                     this.dotted_as_name();
                     --this.state._fsp;
                     break;
                  default:
                     return;
               }
            } while(!this.state.failed);

         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void dotted_name() throws RecognitionException {
      try {
         this.match(this.input, 9, FOLLOW_NAME_in_dotted_name2003);
         if (!this.state.failed) {
            do {
               int alt52 = 2;
               int LA52_0 = this.input.LA(1);
               if (LA52_0 == 10) {
                  alt52 = 1;
               }

               switch (alt52) {
                  case 1:
                     this.match(this.input, 10, FOLLOW_DOT_in_dotted_name2006);
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_attr_in_dotted_name2008);
                     this.attr();
                     --this.state._fsp;
                     break;
                  default:
                     return;
               }
            } while(!this.state.failed);

         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void global_stmt() throws RecognitionException {
      try {
         this.match(this.input, 26, FOLLOW_GLOBAL_in_global_stmt2028);
         if (!this.state.failed) {
            this.match(this.input, 9, FOLLOW_NAME_in_global_stmt2030);
            if (!this.state.failed) {
               do {
                  int alt53 = 2;
                  int LA53_0 = this.input.LA(1);
                  if (LA53_0 == 47) {
                     alt53 = 1;
                  }

                  switch (alt53) {
                     case 1:
                        this.match(this.input, 47, FOLLOW_COMMA_in_global_stmt2033);
                        if (this.state.failed) {
                           return;
                        }

                        this.match(this.input, 9, FOLLOW_NAME_in_global_stmt2035);
                        break;
                     default:
                        return;
                  }
               } while(!this.state.failed);

            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void exec_stmt() throws RecognitionException {
      try {
         this.match(this.input, 22, FOLLOW_EXEC_in_exec_stmt2055);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_expr_in_exec_stmt2057);
            this.expr();
            --this.state._fsp;
            if (!this.state.failed) {
               int alt55 = 2;
               int LA55_0 = this.input.LA(1);
               if (LA55_0 == 29) {
                  alt55 = 1;
               }

               switch (alt55) {
                  case 1:
                     this.match(this.input, 29, FOLLOW_IN_in_exec_stmt2060);
                     if (this.state.failed) {
                        return;
                     } else {
                        this.pushFollow(FOLLOW_test_in_exec_stmt2062);
                        this.test();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        } else {
                           int alt54 = 2;
                           int LA54_0 = this.input.LA(1);
                           if (LA54_0 == 47) {
                              alt54 = 1;
                           }

                           switch (alt54) {
                              case 1:
                                 this.match(this.input, 47, FOLLOW_COMMA_in_exec_stmt2065);
                                 if (this.state.failed) {
                                    return;
                                 } else {
                                    this.pushFollow(FOLLOW_test_in_exec_stmt2067);
                                    this.test();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return;
                                    }
                                 }
                           }
                        }
                     }
                  default:
               }
            }
         }
      } catch (RecognitionException var8) {
         throw var8;
      } finally {
         ;
      }
   }

   public final void assert_stmt() throws RecognitionException {
      try {
         this.match(this.input, 14, FOLLOW_ASSERT_in_assert_stmt2089);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_test_in_assert_stmt2091);
            this.test();
            --this.state._fsp;
            if (!this.state.failed) {
               int alt56 = 2;
               int LA56_0 = this.input.LA(1);
               if (LA56_0 == 47) {
                  alt56 = 1;
               }

               switch (alt56) {
                  case 1:
                     this.match(this.input, 47, FOLLOW_COMMA_in_assert_stmt2094);
                     if (this.state.failed) {
                        return;
                     } else {
                        this.pushFollow(FOLLOW_test_in_assert_stmt2096);
                        this.test();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }
                     }
                  default:
               }
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void compound_stmt() throws RecognitionException {
      try {
         int alt57 = true;
         int alt57 = this.dfa57.predict(this.input);
         switch (alt57) {
            case 1:
               this.pushFollow(FOLLOW_if_stmt_in_compound_stmt2116);
               this.if_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_while_stmt_in_compound_stmt2124);
               this.while_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.pushFollow(FOLLOW_for_stmt_in_compound_stmt2132);
               this.for_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 4:
               this.pushFollow(FOLLOW_try_stmt_in_compound_stmt2140);
               this.try_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 5:
               this.pushFollow(FOLLOW_with_stmt_in_compound_stmt2148);
               this.with_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 6:
               this.pushFollow(FOLLOW_funcdef_in_compound_stmt2165);
               this.funcdef();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 7:
               this.pushFollow(FOLLOW_classdef_in_compound_stmt2182);
               this.classdef();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 8:
               this.pushFollow(FOLLOW_decorators_in_compound_stmt2190);
               this.decorators();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void if_stmt() throws RecognitionException {
      try {
         this.match(this.input, 27, FOLLOW_IF_in_if_stmt2208);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_test_in_if_stmt2210);
            this.test();
            --this.state._fsp;
            if (!this.state.failed) {
               this.match(this.input, 45, FOLLOW_COLON_in_if_stmt2212);
               if (!this.state.failed) {
                  this.pushFollow(FOLLOW_suite_in_if_stmt2214);
                  this.suite();
                  --this.state._fsp;
                  if (!this.state.failed) {
                     int alt58 = 2;
                     int LA58_0 = this.input.LA(1);
                     if (LA58_0 == 20 || LA58_0 == 34) {
                        alt58 = 1;
                     }

                     switch (alt58) {
                        case 1:
                           this.pushFollow(FOLLOW_elif_clause_in_if_stmt2216);
                           this.elif_clause();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }
                        default:
                     }
                  }
               }
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void elif_clause() throws RecognitionException {
      try {
         int alt60 = true;
         int LA60_0 = this.input.LA(1);
         byte alt60;
         if (LA60_0 == 34) {
            alt60 = 1;
         } else {
            if (LA60_0 != 20) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 60, 0, this.input);
               throw nvae;
            }

            alt60 = 2;
         }

         switch (alt60) {
            case 1:
               this.pushFollow(FOLLOW_else_clause_in_elif_clause2235);
               this.else_clause();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.match(this.input, 20, FOLLOW_ELIF_in_elif_clause2243);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_test_in_elif_clause2245);
               this.test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 45, FOLLOW_COLON_in_elif_clause2247);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_suite_in_elif_clause2249);
               this.suite();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               int alt59 = true;
               int LA59_0 = this.input.LA(1);
               byte alt59;
               if (LA59_0 != 20 && LA59_0 != 34) {
                  if (LA59_0 != -1 && (LA59_0 < 5 || LA59_0 > 7) && LA59_0 != 9 && LA59_0 != 11 && (LA59_0 < 14 || LA59_0 > 19) && LA59_0 != 22 && (LA59_0 < 24 || LA59_0 > 28) && (LA59_0 < 31 || LA59_0 > 32) && (LA59_0 < 35 || LA59_0 > 43) && (LA59_0 < 75 || LA59_0 > 76) && (LA59_0 < 80 || LA59_0 > 81) && LA59_0 != 83 && (LA59_0 < 85 || LA59_0 > 90) && (LA59_0 < 99 || LA59_0 > 100)) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 59, 0, this.input);
                     throw nvae;
                  }

                  alt59 = 2;
               } else {
                  alt59 = 1;
               }

               switch (alt59) {
                  case 1:
                     this.pushFollow(FOLLOW_elif_clause_in_elif_clause2260);
                     this.elif_clause();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  case 2:
               }
         }

      } catch (RecognitionException var9) {
         throw var9;
      } finally {
         ;
      }
   }

   public final void else_clause() throws RecognitionException {
      try {
         this.match(this.input, 34, FOLLOW_ORELSE_in_else_clause2298);
         if (!this.state.failed) {
            this.match(this.input, 45, FOLLOW_COLON_in_else_clause2300);
            if (!this.state.failed) {
               this.pushFollow(FOLLOW_suite_in_else_clause2302);
               this.suite();
               --this.state._fsp;
               if (!this.state.failed) {
                  ;
               }
            }
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void while_stmt() throws RecognitionException {
      try {
         this.match(this.input, 39, FOLLOW_WHILE_in_while_stmt2320);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_test_in_while_stmt2322);
            this.test();
            --this.state._fsp;
            if (!this.state.failed) {
               this.match(this.input, 45, FOLLOW_COLON_in_while_stmt2324);
               if (!this.state.failed) {
                  this.pushFollow(FOLLOW_suite_in_while_stmt2326);
                  this.suite();
                  --this.state._fsp;
                  if (!this.state.failed) {
                     int alt61 = 2;
                     int LA61_0 = this.input.LA(1);
                     if (LA61_0 == 34) {
                        alt61 = 1;
                     }

                     switch (alt61) {
                        case 1:
                           this.match(this.input, 34, FOLLOW_ORELSE_in_while_stmt2329);
                           if (this.state.failed) {
                              return;
                           } else {
                              this.match(this.input, 45, FOLLOW_COLON_in_while_stmt2331);
                              if (this.state.failed) {
                                 return;
                              } else {
                                 this.pushFollow(FOLLOW_suite_in_while_stmt2333);
                                 this.suite();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return;
                                 }
                              }
                           }
                        default:
                     }
                  }
               }
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void for_stmt() throws RecognitionException {
      try {
         this.match(this.input, 25, FOLLOW_FOR_in_for_stmt2353);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_exprlist_in_for_stmt2355);
            this.exprlist();
            --this.state._fsp;
            if (!this.state.failed) {
               this.match(this.input, 29, FOLLOW_IN_in_for_stmt2357);
               if (!this.state.failed) {
                  this.pushFollow(FOLLOW_testlist_in_for_stmt2359);
                  this.testlist();
                  --this.state._fsp;
                  if (!this.state.failed) {
                     this.match(this.input, 45, FOLLOW_COLON_in_for_stmt2361);
                     if (!this.state.failed) {
                        this.pushFollow(FOLLOW_suite_in_for_stmt2363);
                        this.suite();
                        --this.state._fsp;
                        if (!this.state.failed) {
                           int alt62 = 2;
                           int LA62_0 = this.input.LA(1);
                           if (LA62_0 == 34) {
                              alt62 = 1;
                           }

                           switch (alt62) {
                              case 1:
                                 this.match(this.input, 34, FOLLOW_ORELSE_in_for_stmt2374);
                                 if (this.state.failed) {
                                    return;
                                 } else {
                                    this.match(this.input, 45, FOLLOW_COLON_in_for_stmt2376);
                                    if (this.state.failed) {
                                       return;
                                    } else {
                                       this.pushFollow(FOLLOW_suite_in_for_stmt2378);
                                       this.suite();
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          return;
                                       }
                                    }
                                 }
                              default:
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void try_stmt() throws RecognitionException {
      try {
         this.match(this.input, 38, FOLLOW_TRY_in_try_stmt2402);
         if (!this.state.failed) {
            this.match(this.input, 45, FOLLOW_COLON_in_try_stmt2404);
            if (!this.state.failed) {
               this.pushFollow(FOLLOW_suite_in_try_stmt2406);
               this.suite();
               --this.state._fsp;
               if (!this.state.failed) {
                  int alt66 = 3;
                  int LA66_0 = this.input.LA(1);
                  if (LA66_0 == 21) {
                     alt66 = 1;
                  } else if (LA66_0 == 23) {
                     alt66 = 2;
                  }

                  switch (alt66) {
                     case 1:
                        int cnt63 = 0;

                        while(true) {
                           int alt64 = 2;
                           int LA64_0 = this.input.LA(1);
                           if (LA64_0 == 21) {
                              alt64 = 1;
                           }

                           switch (alt64) {
                              case 1:
                                 this.pushFollow(FOLLOW_except_clause_in_try_stmt2416);
                                 this.except_clause();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return;
                                 }

                                 ++cnt63;
                                 break;
                              default:
                                 if (cnt63 < 1) {
                                    if (this.state.backtracking > 0) {
                                       this.state.failed = true;
                                       return;
                                    }

                                    EarlyExitException eee = new EarlyExitException(63, this.input);
                                    throw eee;
                                 }

                                 alt64 = 2;
                                 LA64_0 = this.input.LA(1);
                                 if (LA64_0 == 34) {
                                    alt64 = 1;
                                 }

                                 switch (alt64) {
                                    case 1:
                                       this.match(this.input, 34, FOLLOW_ORELSE_in_try_stmt2420);
                                       if (this.state.failed) {
                                          return;
                                       }

                                       this.match(this.input, 45, FOLLOW_COLON_in_try_stmt2422);
                                       if (this.state.failed) {
                                          return;
                                       }

                                       this.pushFollow(FOLLOW_suite_in_try_stmt2424);
                                       this.suite();
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          return;
                                       }
                                    default:
                                       int alt65 = 2;
                                       int LA65_0 = this.input.LA(1);
                                       if (LA65_0 == 23) {
                                          alt65 = 1;
                                       }

                                       switch (alt65) {
                                          case 1:
                                             this.match(this.input, 23, FOLLOW_FINALLY_in_try_stmt2429);
                                             if (this.state.failed) {
                                                return;
                                             }

                                             this.match(this.input, 45, FOLLOW_COLON_in_try_stmt2431);
                                             if (this.state.failed) {
                                                return;
                                             }

                                             this.pushFollow(FOLLOW_suite_in_try_stmt2433);
                                             this.suite();
                                             --this.state._fsp;
                                             if (this.state.failed) {
                                                return;
                                             }

                                             return;
                                          default:
                                             return;
                                       }
                                 }
                           }
                        }
                     case 2:
                        this.match(this.input, 23, FOLLOW_FINALLY_in_try_stmt2445);
                        if (this.state.failed) {
                           return;
                        }

                        this.match(this.input, 45, FOLLOW_COLON_in_try_stmt2447);
                        if (this.state.failed) {
                           return;
                        }

                        this.pushFollow(FOLLOW_suite_in_try_stmt2449);
                        this.suite();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }
                  }

               }
            }
         }
      } catch (RecognitionException var11) {
         throw var11;
      } finally {
         ;
      }
   }

   public final void with_stmt() throws RecognitionException {
      try {
         this.match(this.input, 40, FOLLOW_WITH_in_with_stmt2478);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_with_item_in_with_stmt2480);
            this.with_item();
            --this.state._fsp;
            if (!this.state.failed) {
               do {
                  int alt67 = 2;
                  int LA67_0 = this.input.LA(1);
                  if (LA67_0 == 47) {
                     alt67 = 1;
                  }

                  switch (alt67) {
                     case 1:
                        this.match(this.input, 47, FOLLOW_COMMA_in_with_stmt2490);
                        if (this.state.failed) {
                           return;
                        }

                        this.pushFollow(FOLLOW_with_item_in_with_stmt2492);
                        this.with_item();
                        --this.state._fsp;
                        break;
                     default:
                        this.match(this.input, 45, FOLLOW_COLON_in_with_stmt2496);
                        if (this.state.failed) {
                           return;
                        }

                        this.pushFollow(FOLLOW_suite_in_with_stmt2498);
                        this.suite();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }

                        return;
                  }
               } while(!this.state.failed);

            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void with_item() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_test_in_with_item2516);
         this.test();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt68 = 2;
            int LA68_0 = this.input.LA(1);
            if (LA68_0 == 13) {
               alt68 = 1;
            }

            switch (alt68) {
               case 1:
                  this.match(this.input, 13, FOLLOW_AS_in_with_item2519);
                  if (this.state.failed) {
                     return;
                  } else {
                     this.pushFollow(FOLLOW_expr_in_with_item2521);
                     this.expr();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  }
               default:
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void except_clause() throws RecognitionException {
      try {
         this.match(this.input, 21, FOLLOW_EXCEPT_in_except_clause2541);
         if (!this.state.failed) {
            int alt70 = 2;
            int LA70_0 = this.input.LA(1);
            if (LA70_0 == 6 || LA70_0 == 9 || LA70_0 >= 31 && LA70_0 <= 32 || LA70_0 == 43 || LA70_0 >= 75 && LA70_0 <= 76 || LA70_0 >= 80 && LA70_0 <= 81 || LA70_0 == 83 || LA70_0 >= 85 && LA70_0 <= 90 || LA70_0 >= 99 && LA70_0 <= 100) {
               alt70 = 1;
            }

            switch (alt70) {
               case 1:
                  this.pushFollow(FOLLOW_test_in_except_clause2544);
                  this.test();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  } else {
                     int alt69 = 2;
                     int LA69_0 = this.input.LA(1);
                     if (LA69_0 == 13 || LA69_0 == 47) {
                        alt69 = 1;
                     }

                     switch (alt69) {
                        case 1:
                           if (this.input.LA(1) != 13 && this.input.LA(1) != 47) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                              throw mse;
                           } else {
                              this.input.consume();
                              this.state.errorRecovery = false;
                              this.state.failed = false;
                              this.pushFollow(FOLLOW_test_in_except_clause2555);
                              this.test();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return;
                              }
                           }
                     }
                  }
               default:
                  this.match(this.input, 45, FOLLOW_COLON_in_except_clause2561);
                  if (!this.state.failed) {
                     this.pushFollow(FOLLOW_suite_in_except_clause2563);
                     this.suite();
                     --this.state._fsp;
                     if (!this.state.failed) {
                        ;
                     }
                  }
            }
         }
      } catch (RecognitionException var9) {
         throw var9;
      } finally {
         ;
      }
   }

   public final void suite() throws RecognitionException {
      try {
         int alt74 = true;
         int LA74_0 = this.input.LA(1);
         byte alt74;
         if (LA74_0 != 6 && LA74_0 != 9 && LA74_0 != 11 && (LA74_0 < 14 || LA74_0 > 15) && LA74_0 != 17 && LA74_0 != 19 && LA74_0 != 22 && LA74_0 != 24 && LA74_0 != 26 && LA74_0 != 28 && (LA74_0 < 31 || LA74_0 > 32) && (LA74_0 < 35 || LA74_0 > 37) && LA74_0 != 41 && LA74_0 != 43 && (LA74_0 < 75 || LA74_0 > 76) && (LA74_0 < 80 || LA74_0 > 81) && LA74_0 != 83 && (LA74_0 < 85 || LA74_0 > 90) && (LA74_0 < 99 || LA74_0 > 100)) {
            if (LA74_0 != 7) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 74, 0, this.input);
               throw nvae;
            }

            alt74 = 2;
         } else {
            alt74 = 1;
         }

         switch (alt74) {
            case 1:
               this.pushFollow(FOLLOW_simple_stmt_in_suite2581);
               this.simple_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.match(this.input, 7, FOLLOW_NEWLINE_in_suite2589);
               if (this.state.failed) {
                  return;
               }

               int alt73 = true;
               byte alt73;
               switch (this.input.LA(1)) {
                  case -1:
                     alt73 = 1;
                     break;
                  case 4:
                     alt73 = 3;
                     break;
                  case 5:
                     alt73 = 2;
                     break;
                  default:
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 73, 0, this.input);
                     throw nvae;
               }

               int cnt72;
               byte alt72;
               int LA72_0;
               EarlyExitException eee;
               switch (alt73) {
                  case 1:
                     this.match(this.input, -1, FOLLOW_EOF_in_suite2592);
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 2:
                     cnt72 = 0;

                     while(true) {
                        alt72 = 2;
                        LA72_0 = this.input.LA(1);
                        if (LA72_0 == 5) {
                           alt72 = 1;
                        }

                        switch (alt72) {
                           case 1:
                              this.match(this.input, 5, FOLLOW_DEDENT_in_suite2611);
                              if (this.state.failed) {
                                 return;
                              }

                              ++cnt72;
                              break;
                           default:
                              if (cnt72 < 1) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 eee = new EarlyExitException(71, this.input);
                                 throw eee;
                              }

                              this.match(this.input, -1, FOLLOW_EOF_in_suite2615);
                              if (this.state.failed) {
                                 return;
                              }

                              return;
                        }
                     }
                  case 3:
                     this.match(this.input, 4, FOLLOW_INDENT_in_suite2633);
                     if (this.state.failed) {
                        return;
                     }

                     cnt72 = 0;

                     while(true) {
                        alt72 = 2;
                        LA72_0 = this.input.LA(1);
                        if (LA72_0 == 6 || LA72_0 == 9 || LA72_0 == 11 || LA72_0 >= 14 && LA72_0 <= 19 || LA72_0 == 22 || LA72_0 >= 24 && LA72_0 <= 28 || LA72_0 >= 31 && LA72_0 <= 32 || LA72_0 >= 35 && LA72_0 <= 43 || LA72_0 >= 75 && LA72_0 <= 76 || LA72_0 >= 80 && LA72_0 <= 81 || LA72_0 == 83 || LA72_0 >= 85 && LA72_0 <= 90 || LA72_0 >= 99 && LA72_0 <= 100) {
                           alt72 = 1;
                        }

                        switch (alt72) {
                           case 1:
                              this.pushFollow(FOLLOW_stmt_in_suite2636);
                              this.stmt();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return;
                              }

                              ++cnt72;
                              break;
                           default:
                              if (cnt72 < 1) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 eee = new EarlyExitException(72, this.input);
                                 throw eee;
                              }

                              if (this.input.LA(1) != -1 && this.input.LA(1) != 5) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                                 throw mse;
                              }

                              this.input.consume();
                              this.state.errorRecovery = false;
                              this.state.failed = false;
                              return;
                        }
                     }
               }
         }

      } catch (RecognitionException var11) {
         throw var11;
      } finally {
         ;
      }
   }

   public final void test() throws RecognitionException {
      try {
         int alt76 = true;
         int LA76_0 = this.input.LA(1);
         byte alt76;
         if (LA76_0 != 6 && LA76_0 != 9 && LA76_0 != 32 && LA76_0 != 43 && (LA76_0 < 75 || LA76_0 > 76) && (LA76_0 < 80 || LA76_0 > 81) && LA76_0 != 83 && (LA76_0 < 85 || LA76_0 > 90) && (LA76_0 < 99 || LA76_0 > 100)) {
            if (LA76_0 != 31) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 76, 0, this.input);
               throw nvae;
            }

            alt76 = 2;
         } else {
            alt76 = 1;
         }

         switch (alt76) {
            case 1:
               this.pushFollow(FOLLOW_or_test_in_test2741);
               this.or_test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               int alt75 = true;
               int alt75 = this.dfa75.predict(this.input);
               switch (alt75) {
                  case 1:
                     this.match(this.input, 27, FOLLOW_IF_in_test2761);
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_or_test_in_test2763);
                     this.or_test();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }

                     this.match(this.input, 34, FOLLOW_ORELSE_in_test2765);
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_test_in_test2767);
                     this.test();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }

                     return;
                  case 2:
                  default:
                     return;
               }
            case 2:
               this.pushFollow(FOLLOW_lambdef_in_test2791);
               this.lambdef();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void or_test() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_and_test_in_or_test2809);
         this.and_test();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt78 = true;
            int LA78_0 = this.input.LA(1);
            byte alt78;
            if (LA78_0 == 33) {
               alt78 = 1;
            } else {
               if (LA78_0 != -1 && LA78_0 != 7 && LA78_0 != 13 && LA78_0 != 25 && LA78_0 != 27 && LA78_0 != 34 && (LA78_0 < 44 || LA78_0 > 47) && (LA78_0 < 50 || LA78_0 > 62) && LA78_0 != 82 && (LA78_0 < 84 || LA78_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     NoViableAltException nvae = new NoViableAltException("", 78, 0, this.input);
                     throw nvae;
                  }
               }

               alt78 = 2;
            }

            switch (alt78) {
               case 1:
                  int cnt77 = 0;

                  while(true) {
                     int alt77 = 2;
                     int LA77_0 = this.input.LA(1);
                     if (LA77_0 == 33) {
                        alt77 = 1;
                     }

                     switch (alt77) {
                        case 1:
                           this.match(this.input, 33, FOLLOW_OR_in_or_test2822);
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_and_test_in_or_test2824);
                           this.and_test();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt77;
                           break;
                        default:
                           if (cnt77 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(77, this.input);
                              throw eee;
                           }

                           return;
                     }
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void and_test() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_not_test_in_and_test2875);
         this.not_test();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt80 = true;
            int LA80_0 = this.input.LA(1);
            byte alt80;
            if (LA80_0 == 12) {
               alt80 = 1;
            } else {
               if (LA80_0 != -1 && LA80_0 != 7 && LA80_0 != 13 && LA80_0 != 25 && LA80_0 != 27 && (LA80_0 < 33 || LA80_0 > 34) && (LA80_0 < 44 || LA80_0 > 47) && (LA80_0 < 50 || LA80_0 > 62) && LA80_0 != 82 && (LA80_0 < 84 || LA80_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     NoViableAltException nvae = new NoViableAltException("", 80, 0, this.input);
                     throw nvae;
                  }
               }

               alt80 = 2;
            }

            switch (alt80) {
               case 1:
                  int cnt79 = 0;

                  while(true) {
                     int alt79 = 2;
                     int LA79_0 = this.input.LA(1);
                     if (LA79_0 == 12) {
                        alt79 = 1;
                     }

                     switch (alt79) {
                        case 1:
                           this.match(this.input, 12, FOLLOW_AND_in_and_test2888);
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_not_test_in_and_test2890);
                           this.not_test();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt79;
                           break;
                        default:
                           if (cnt79 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(79, this.input);
                              throw eee;
                           }

                           return;
                     }
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void not_test() throws RecognitionException {
      try {
         int alt81 = true;
         int LA81_0 = this.input.LA(1);
         byte alt81;
         if (LA81_0 == 32) {
            alt81 = 1;
         } else {
            if (LA81_0 != 6 && LA81_0 != 9 && LA81_0 != 43 && (LA81_0 < 75 || LA81_0 > 76) && (LA81_0 < 80 || LA81_0 > 81) && LA81_0 != 83 && (LA81_0 < 85 || LA81_0 > 90) && (LA81_0 < 99 || LA81_0 > 100)) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 81, 0, this.input);
               throw nvae;
            }

            alt81 = 2;
         }

         switch (alt81) {
            case 1:
               this.match(this.input, 32, FOLLOW_NOT_in_not_test2941);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_not_test_in_not_test2943);
               this.not_test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_comparison_in_not_test2951);
               this.comparison();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void comparison() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_expr_in_comparison2969);
         this.expr();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt83 = true;
            int LA83_0 = this.input.LA(1);
            byte alt83;
            if (LA83_0 >= 29 && LA83_0 <= 30 || LA83_0 == 32 || LA83_0 >= 64 && LA83_0 <= 70) {
               alt83 = 1;
            } else {
               if (LA83_0 != -1 && LA83_0 != 7 && (LA83_0 < 12 || LA83_0 > 13) && LA83_0 != 25 && LA83_0 != 27 && (LA83_0 < 33 || LA83_0 > 34) && (LA83_0 < 44 || LA83_0 > 47) && (LA83_0 < 50 || LA83_0 > 62) && LA83_0 != 82 && (LA83_0 < 84 || LA83_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 83, 0, this.input);
                  throw nvae;
               }

               alt83 = 2;
            }

            switch (alt83) {
               case 1:
                  int cnt82 = 0;

                  while(true) {
                     int alt82 = 2;
                     int LA82_0 = this.input.LA(1);
                     if (LA82_0 >= 29 && LA82_0 <= 30 || LA82_0 == 32 || LA82_0 >= 64 && LA82_0 <= 70) {
                        alt82 = 1;
                     }

                     switch (alt82) {
                        case 1:
                           this.pushFollow(FOLLOW_comp_op_in_comparison2982);
                           this.comp_op();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_expr_in_comparison2984);
                           this.expr();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt82;
                           break;
                        default:
                           if (cnt82 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(82, this.input);
                              throw eee;
                           }

                           return;
                     }
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void comp_op() throws RecognitionException {
      try {
         int alt84 = true;
         int alt84 = this.dfa84.predict(this.input);
         switch (alt84) {
            case 1:
               this.match(this.input, 64, FOLLOW_LESS_in_comp_op3032);
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.match(this.input, 65, FOLLOW_GREATER_in_comp_op3040);
               if (this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.match(this.input, 66, FOLLOW_EQUAL_in_comp_op3048);
               if (this.state.failed) {
                  return;
               }
               break;
            case 4:
               this.match(this.input, 67, FOLLOW_GREATEREQUAL_in_comp_op3056);
               if (this.state.failed) {
                  return;
               }
               break;
            case 5:
               this.match(this.input, 68, FOLLOW_LESSEQUAL_in_comp_op3064);
               if (this.state.failed) {
                  return;
               }
               break;
            case 6:
               this.match(this.input, 69, FOLLOW_ALT_NOTEQUAL_in_comp_op3072);
               if (this.state.failed) {
                  return;
               }
               break;
            case 7:
               this.match(this.input, 70, FOLLOW_NOTEQUAL_in_comp_op3080);
               if (this.state.failed) {
                  return;
               }
               break;
            case 8:
               this.match(this.input, 29, FOLLOW_IN_in_comp_op3088);
               if (this.state.failed) {
                  return;
               }
               break;
            case 9:
               this.match(this.input, 32, FOLLOW_NOT_in_comp_op3096);
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 29, FOLLOW_IN_in_comp_op3098);
               if (this.state.failed) {
                  return;
               }
               break;
            case 10:
               this.match(this.input, 30, FOLLOW_IS_in_comp_op3106);
               if (this.state.failed) {
                  return;
               }
               break;
            case 11:
               this.match(this.input, 30, FOLLOW_IS_in_comp_op3114);
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 32, FOLLOW_NOT_in_comp_op3116);
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void expr() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_xor_expr_in_expr3134);
         this.xor_expr();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt86 = true;
            int LA86_0 = this.input.LA(1);
            byte alt86;
            if (LA86_0 == 71) {
               alt86 = 1;
            } else {
               if (LA86_0 != -1 && LA86_0 != 7 && (LA86_0 < 12 || LA86_0 > 13) && LA86_0 != 25 && LA86_0 != 27 && (LA86_0 < 29 || LA86_0 > 30) && (LA86_0 < 32 || LA86_0 > 34) && (LA86_0 < 44 || LA86_0 > 47) && (LA86_0 < 50 || LA86_0 > 62) && (LA86_0 < 64 || LA86_0 > 70) && LA86_0 != 82 && (LA86_0 < 84 || LA86_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     NoViableAltException nvae = new NoViableAltException("", 86, 0, this.input);
                     throw nvae;
                  }
               }

               alt86 = 2;
            }

            switch (alt86) {
               case 1:
                  int cnt85 = 0;

                  while(true) {
                     int alt85 = 2;
                     int LA85_0 = this.input.LA(1);
                     if (LA85_0 == 71) {
                        alt85 = 1;
                     }

                     switch (alt85) {
                        case 1:
                           this.match(this.input, 71, FOLLOW_VBAR_in_expr3147);
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_xor_expr_in_expr3149);
                           this.xor_expr();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt85;
                           break;
                        default:
                           if (cnt85 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(85, this.input);
                              throw eee;
                           }

                           return;
                     }
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void xor_expr() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_and_expr_in_xor_expr3200);
         this.and_expr();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt88 = true;
            int LA88_0 = this.input.LA(1);
            byte alt88;
            if (LA88_0 == 72) {
               alt88 = 1;
            } else {
               if (LA88_0 != -1 && LA88_0 != 7 && (LA88_0 < 12 || LA88_0 > 13) && LA88_0 != 25 && LA88_0 != 27 && (LA88_0 < 29 || LA88_0 > 30) && (LA88_0 < 32 || LA88_0 > 34) && (LA88_0 < 44 || LA88_0 > 47) && (LA88_0 < 50 || LA88_0 > 62) && (LA88_0 < 64 || LA88_0 > 71) && LA88_0 != 82 && (LA88_0 < 84 || LA88_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     NoViableAltException nvae = new NoViableAltException("", 88, 0, this.input);
                     throw nvae;
                  }
               }

               alt88 = 2;
            }

            switch (alt88) {
               case 1:
                  int cnt87 = 0;

                  while(true) {
                     int alt87 = 2;
                     int LA87_0 = this.input.LA(1);
                     if (LA87_0 == 72) {
                        alt87 = 1;
                     }

                     switch (alt87) {
                        case 1:
                           this.match(this.input, 72, FOLLOW_CIRCUMFLEX_in_xor_expr3213);
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_and_expr_in_xor_expr3215);
                           this.and_expr();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt87;
                           break;
                        default:
                           if (cnt87 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(87, this.input);
                              throw eee;
                           }

                           return;
                     }
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void and_expr() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_shift_expr_in_and_expr3266);
         this.shift_expr();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt90 = true;
            int LA90_0 = this.input.LA(1);
            byte alt90;
            if (LA90_0 == 73) {
               alt90 = 1;
            } else {
               if (LA90_0 != -1 && LA90_0 != 7 && (LA90_0 < 12 || LA90_0 > 13) && LA90_0 != 25 && LA90_0 != 27 && (LA90_0 < 29 || LA90_0 > 30) && (LA90_0 < 32 || LA90_0 > 34) && (LA90_0 < 44 || LA90_0 > 47) && (LA90_0 < 50 || LA90_0 > 62) && (LA90_0 < 64 || LA90_0 > 72) && LA90_0 != 82 && (LA90_0 < 84 || LA90_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     NoViableAltException nvae = new NoViableAltException("", 90, 0, this.input);
                     throw nvae;
                  }
               }

               alt90 = 2;
            }

            switch (alt90) {
               case 1:
                  int cnt89 = 0;

                  while(true) {
                     int alt89 = 2;
                     int LA89_0 = this.input.LA(1);
                     if (LA89_0 == 73) {
                        alt89 = 1;
                     }

                     switch (alt89) {
                        case 1:
                           this.match(this.input, 73, FOLLOW_AMPER_in_and_expr3279);
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_shift_expr_in_and_expr3281);
                           this.shift_expr();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt89;
                           break;
                        default:
                           if (cnt89 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(89, this.input);
                              throw eee;
                           }

                           return;
                     }
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void shift_expr() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_arith_expr_in_shift_expr3332);
         this.arith_expr();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt92 = true;
            int LA92_0 = this.input.LA(1);
            byte alt92;
            if (LA92_0 != 63 && LA92_0 != 74) {
               if (LA92_0 != -1 && LA92_0 != 7 && (LA92_0 < 12 || LA92_0 > 13) && LA92_0 != 25 && LA92_0 != 27 && (LA92_0 < 29 || LA92_0 > 30) && (LA92_0 < 32 || LA92_0 > 34) && (LA92_0 < 44 || LA92_0 > 47) && (LA92_0 < 50 || LA92_0 > 62) && (LA92_0 < 64 || LA92_0 > 73) && LA92_0 != 82 && (LA92_0 < 84 || LA92_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     NoViableAltException nvae = new NoViableAltException("", 92, 0, this.input);
                     throw nvae;
                  }
               }

               alt92 = 2;
            } else {
               alt92 = 1;
            }

            switch (alt92) {
               case 1:
                  int cnt91 = 0;

                  while(true) {
                     int alt91 = 2;
                     int LA91_0 = this.input.LA(1);
                     if (LA91_0 == 63 || LA91_0 == 74) {
                        alt91 = 1;
                     }

                     switch (alt91) {
                        case 1:
                           this.pushFollow(FOLLOW_shift_op_in_shift_expr3346);
                           this.shift_op();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_arith_expr_in_shift_expr3348);
                           this.arith_expr();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt91;
                           break;
                        default:
                           if (cnt91 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(91, this.input);
                              throw eee;
                           }

                           return;
                     }
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void shift_op() throws RecognitionException {
      try {
         if (this.input.LA(1) != 63 && this.input.LA(1) != 74) {
            if (this.state.backtracking > 0) {
               this.state.failed = true;
            } else {
               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               throw mse;
            }
         } else {
            this.input.consume();
            this.state.errorRecovery = false;
            this.state.failed = false;
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void arith_expr() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_term_in_arith_expr3424);
         this.term();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt94 = true;
            int LA94_0 = this.input.LA(1);
            byte alt94;
            if (LA94_0 >= 75 && LA94_0 <= 76) {
               alt94 = 1;
            } else {
               if (LA94_0 != -1 && LA94_0 != 7 && (LA94_0 < 12 || LA94_0 > 13) && LA94_0 != 25 && LA94_0 != 27 && (LA94_0 < 29 || LA94_0 > 30) && (LA94_0 < 32 || LA94_0 > 34) && (LA94_0 < 44 || LA94_0 > 47) && (LA94_0 < 50 || LA94_0 > 74) && LA94_0 != 82 && (LA94_0 < 84 || LA94_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     NoViableAltException nvae = new NoViableAltException("", 94, 0, this.input);
                     throw nvae;
                  }
               }

               alt94 = 2;
            }

            switch (alt94) {
               case 1:
                  int cnt93 = 0;

                  while(true) {
                     int alt93 = 2;
                     int LA93_0 = this.input.LA(1);
                     if (LA93_0 >= 75 && LA93_0 <= 76) {
                        alt93 = 1;
                     }

                     switch (alt93) {
                        case 1:
                           this.pushFollow(FOLLOW_arith_op_in_arith_expr3437);
                           this.arith_op();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_term_in_arith_expr3439);
                           this.term();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt93;
                           break;
                        default:
                           if (cnt93 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(93, this.input);
                              throw eee;
                           }

                           return;
                     }
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void arith_op() throws RecognitionException {
      try {
         if (this.input.LA(1) >= 75 && this.input.LA(1) <= 76) {
            this.input.consume();
            this.state.errorRecovery = false;
            this.state.failed = false;
         } else if (this.state.backtracking > 0) {
            this.state.failed = true;
         } else {
            MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
            throw mse;
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void term() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_factor_in_term3515);
         this.factor();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt96 = true;
            int LA96_0 = this.input.LA(1);
            byte alt96;
            if (LA96_0 != 48 && (LA96_0 < 77 || LA96_0 > 79)) {
               if (LA96_0 != -1 && LA96_0 != 7 && (LA96_0 < 12 || LA96_0 > 13) && LA96_0 != 25 && LA96_0 != 27 && (LA96_0 < 29 || LA96_0 > 30) && (LA96_0 < 32 || LA96_0 > 34) && (LA96_0 < 44 || LA96_0 > 47) && (LA96_0 < 50 || LA96_0 > 76) && LA96_0 != 82 && (LA96_0 < 84 || LA96_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  } else {
                     NoViableAltException nvae = new NoViableAltException("", 96, 0, this.input);
                     throw nvae;
                  }
               }

               alt96 = 2;
            } else {
               alt96 = 1;
            }

            switch (alt96) {
               case 1:
                  int cnt95 = 0;

                  while(true) {
                     int alt95 = 2;
                     int LA95_0 = this.input.LA(1);
                     if (LA95_0 == 48 || LA95_0 >= 77 && LA95_0 <= 79) {
                        alt95 = 1;
                     }

                     switch (alt95) {
                        case 1:
                           this.pushFollow(FOLLOW_term_op_in_term3528);
                           this.term_op();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_factor_in_term3530);
                           this.factor();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt95;
                           break;
                        default:
                           if (cnt95 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(95, this.input);
                              throw eee;
                           }

                           return;
                     }
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void term_op() throws RecognitionException {
      try {
         if (this.input.LA(1) == 48 || this.input.LA(1) >= 77 && this.input.LA(1) <= 79) {
            this.input.consume();
            this.state.errorRecovery = false;
            this.state.failed = false;
         } else if (this.state.backtracking > 0) {
            this.state.failed = true;
         } else {
            MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
            throw mse;
         }
      } catch (RecognitionException var5) {
         throw var5;
      } finally {
         ;
      }
   }

   public final void factor() throws RecognitionException {
      try {
         int alt97 = true;
         byte alt97;
         switch (this.input.LA(1)) {
            case 6:
               alt97 = 5;
               break;
            case 9:
            case 43:
            case 81:
            case 83:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 99:
            case 100:
               alt97 = 4;
               break;
            case 75:
               alt97 = 1;
               break;
            case 76:
               alt97 = 2;
               break;
            case 80:
               alt97 = 3;
               break;
            default:
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 97, 0, this.input);
               throw nvae;
         }

         switch (alt97) {
            case 1:
               this.match(this.input, 75, FOLLOW_PLUS_in_factor3618);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_factor_in_factor3620);
               this.factor();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.match(this.input, 76, FOLLOW_MINUS_in_factor3628);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_factor_in_factor3630);
               this.factor();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.match(this.input, 80, FOLLOW_TILDE_in_factor3638);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_factor_in_factor3640);
               this.factor();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 4:
               this.pushFollow(FOLLOW_power_in_factor3648);
               this.power();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 5:
               this.match(this.input, 6, FOLLOW_TRAILBACKSLASH_in_factor3656);
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void power() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_atom_in_power3674);
         this.atom();
         --this.state._fsp;
         if (!this.state.failed) {
            do {
               int alt99 = 2;
               int LA99_0 = this.input.LA(1);
               if (LA99_0 == 10 || LA99_0 == 43 || LA99_0 == 81) {
                  alt99 = 1;
               }

               switch (alt99) {
                  case 1:
                     this.pushFollow(FOLLOW_trailer_in_power3677);
                     this.trailer();
                     --this.state._fsp;
                     break;
                  default:
                     alt99 = 2;
                     LA99_0 = this.input.LA(1);
                     if (LA99_0 == 49) {
                        alt99 = 1;
                     }

                     switch (alt99) {
                        case 1:
                           this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_power3689);
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_factor_in_power3691);
                           this.factor();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           return;
                     }
               }
            } while(!this.state.failed);

         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void atom() throws RecognitionException {
      try {
         int alt104 = true;
         byte alt104;
         switch (this.input.LA(1)) {
            case 9:
               alt104 = 5;
               break;
            case 43:
               alt104 = 1;
               break;
            case 81:
               alt104 = 2;
               break;
            case 83:
               alt104 = 3;
               break;
            case 85:
               alt104 = 4;
               break;
            case 86:
               alt104 = 6;
               break;
            case 87:
               alt104 = 7;
               break;
            case 88:
               alt104 = 8;
               break;
            case 89:
               alt104 = 9;
               break;
            case 90:
               alt104 = 10;
               break;
            case 99:
               alt104 = 11;
               break;
            case 100:
               alt104 = 12;
               break;
            default:
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 104, 0, this.input);
               throw nvae;
         }

         int LA102_0;
         boolean alt102;
         byte alt102;
         NoViableAltException nvae;
         switch (alt104) {
            case 1:
               this.match(this.input, 43, FOLLOW_LPAREN_in_atom3715);
               if (this.state.failed) {
                  return;
               }

               alt102 = true;
               switch (this.input.LA(1)) {
                  case 6:
                  case 9:
                  case 31:
                  case 32:
                  case 43:
                  case 75:
                  case 76:
                  case 80:
                  case 81:
                  case 83:
                  case 85:
                  case 86:
                  case 87:
                  case 88:
                  case 89:
                  case 90:
                  case 99:
                  case 100:
                     alt102 = 2;
                     break;
                  case 41:
                     alt102 = 1;
                     break;
                  case 44:
                     alt102 = 3;
                     break;
                  default:
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 100, 0, this.input);
                     throw nvae;
               }

               switch (alt102) {
                  case 1:
                     this.pushFollow(FOLLOW_yield_expr_in_atom3725);
                     this.yield_expr();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 2:
                     this.pushFollow(FOLLOW_testlist_gexp_in_atom3735);
                     this.testlist_gexp();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  case 3:
               }

               this.match(this.input, 44, FOLLOW_RPAREN_in_atom3759);
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.match(this.input, 81, FOLLOW_LBRACK_in_atom3767);
               if (this.state.failed) {
                  return;
               }

               alt102 = true;
               LA102_0 = this.input.LA(1);
               if (LA102_0 != 6 && LA102_0 != 9 && (LA102_0 < 31 || LA102_0 > 32) && LA102_0 != 43 && (LA102_0 < 75 || LA102_0 > 76) && (LA102_0 < 80 || LA102_0 > 81) && LA102_0 != 83 && (LA102_0 < 85 || LA102_0 > 90) && (LA102_0 < 99 || LA102_0 > 100)) {
                  if (LA102_0 != 82) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvae = new NoViableAltException("", 101, 0, this.input);
                     throw nvae;
                  }

                  alt102 = 2;
               } else {
                  alt102 = 1;
               }

               switch (alt102) {
                  case 1:
                     this.pushFollow(FOLLOW_listmaker_in_atom3776);
                     this.listmaker();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  case 2:
                  default:
                     this.match(this.input, 82, FOLLOW_RBRACK_in_atom3800);
                     if (this.state.failed) {
                        return;
                     }

                     return;
               }
            case 3:
               this.match(this.input, 83, FOLLOW_LCURLY_in_atom3808);
               if (this.state.failed) {
                  return;
               }

               alt102 = true;
               LA102_0 = this.input.LA(1);
               if (LA102_0 != 6 && LA102_0 != 9 && (LA102_0 < 31 || LA102_0 > 32) && LA102_0 != 43 && (LA102_0 < 75 || LA102_0 > 76) && (LA102_0 < 80 || LA102_0 > 81) && LA102_0 != 83 && (LA102_0 < 85 || LA102_0 > 90) && (LA102_0 < 99 || LA102_0 > 100)) {
                  if (LA102_0 != 84) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvae = new NoViableAltException("", 102, 0, this.input);
                     throw nvae;
                  }

                  alt102 = 2;
               } else {
                  alt102 = 1;
               }

               switch (alt102) {
                  case 1:
                     this.pushFollow(FOLLOW_dictorsetmaker_in_atom3818);
                     this.dictorsetmaker();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  case 2:
                  default:
                     this.match(this.input, 84, FOLLOW_RCURLY_in_atom3845);
                     if (this.state.failed) {
                        return;
                     }

                     return;
               }
            case 4:
               this.match(this.input, 85, FOLLOW_BACKQUOTE_in_atom3854);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_testlist_in_atom3856);
               this.testlist();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 85, FOLLOW_BACKQUOTE_in_atom3858);
               if (this.state.failed) {
                  return;
               }
               break;
            case 5:
               this.match(this.input, 9, FOLLOW_NAME_in_atom3867);
               if (this.state.failed) {
                  return;
               }
               break;
            case 6:
               this.match(this.input, 86, FOLLOW_INT_in_atom3876);
               if (this.state.failed) {
                  return;
               }
               break;
            case 7:
               this.match(this.input, 87, FOLLOW_LONGINT_in_atom3885);
               if (this.state.failed) {
                  return;
               }
               break;
            case 8:
               this.match(this.input, 88, FOLLOW_FLOAT_in_atom3894);
               if (this.state.failed) {
                  return;
               }
               break;
            case 9:
               this.match(this.input, 89, FOLLOW_COMPLEX_in_atom3903);
               if (this.state.failed) {
                  return;
               }
               break;
            case 10:
               int cnt103 = 0;

               while(true) {
                  LA102_0 = 2;
                  int LA103_0 = this.input.LA(1);
                  if (LA103_0 == 90) {
                     LA102_0 = 1;
                  }

                  switch (LA102_0) {
                     case 1:
                        this.match(this.input, 90, FOLLOW_STRING_in_atom3913);
                        if (this.state.failed) {
                           return;
                        }

                        ++cnt103;
                        break;
                     default:
                        if (cnt103 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(103, this.input);
                           throw eee;
                        }

                        return;
                  }
               }
            case 11:
               this.match(this.input, 99, FOLLOW_TRISTRINGPART_in_atom3924);
               if (this.state.failed) {
                  return;
               }
               break;
            case 12:
               this.match(this.input, 100, FOLLOW_STRINGPART_in_atom3933);
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 6, FOLLOW_TRAILBACKSLASH_in_atom3935);
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var9) {
         throw var9;
      } finally {
         ;
      }
   }

   public final void listmaker() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_test_in_listmaker3954);
         this.test();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt106 = true;
            int LA106_0 = this.input.LA(1);
            byte alt106;
            if (LA106_0 == 25) {
               alt106 = 1;
            } else {
               if (LA106_0 != 47 && LA106_0 != 82) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 106, 0, this.input);
                  throw nvae;
               }

               alt106 = 2;
            }

            byte alt105;
            int LA107_0;
            switch (alt106) {
               case 1:
                  this.pushFollow(FOLLOW_list_for_in_listmaker3965);
                  this.list_for();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  label224:
                  while(true) {
                     alt105 = 2;
                     LA107_0 = this.input.LA(1);
                     if (LA107_0 == 47) {
                        int LA105_1 = this.input.LA(2);
                        if (LA105_1 == 6 || LA105_1 == 9 || LA105_1 >= 31 && LA105_1 <= 32 || LA105_1 == 43 || LA105_1 >= 75 && LA105_1 <= 76 || LA105_1 >= 80 && LA105_1 <= 81 || LA105_1 == 83 || LA105_1 >= 85 && LA105_1 <= 90 || LA105_1 >= 99 && LA105_1 <= 100) {
                           alt105 = 1;
                        }
                     }

                     switch (alt105) {
                        case 1:
                           this.match(this.input, 47, FOLLOW_COMMA_in_listmaker3985);
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_test_in_listmaker3987);
                           this.test();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }
                           break;
                        default:
                           break label224;
                     }
                  }
            }

            alt105 = 2;
            LA107_0 = this.input.LA(1);
            if (LA107_0 == 47) {
               alt105 = 1;
            }

            switch (alt105) {
               case 1:
                  this.match(this.input, 47, FOLLOW_COMMA_in_listmaker4002);
                  if (this.state.failed) {
                     return;
                  }
               default:
            }
         }
      } catch (RecognitionException var9) {
         throw var9;
      } finally {
         ;
      }
   }

   public final void testlist_gexp() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_test_in_testlist_gexp4022);
         this.test();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt110 = true;
            int LA110_0 = this.input.LA(1);
            byte alt110;
            if (LA110_0 != 44 && LA110_0 != 47) {
               if (LA110_0 != 25) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 110, 0, this.input);
                  throw nvae;
               }

               alt110 = 2;
            } else {
               alt110 = 1;
            }

            switch (alt110) {
               case 1:
                  while(true) {
                     int alt108 = true;
                     int alt108 = this.dfa108.predict(this.input);
                     switch (alt108) {
                        case 1:
                           this.match(this.input, 47, FOLLOW_COMMA_in_testlist_gexp4044);
                           if (this.state.failed) {
                              return;
                           }

                           this.pushFollow(FOLLOW_test_in_testlist_gexp4046);
                           this.test();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }
                           break;
                        default:
                           int alt109 = 2;
                           int LA109_0 = this.input.LA(1);
                           if (LA109_0 == 47) {
                              alt109 = 1;
                           }

                           switch (alt109) {
                              case 1:
                                 this.match(this.input, 47, FOLLOW_COMMA_in_testlist_gexp4051);
                                 if (this.state.failed) {
                                    return;
                                 }

                                 return;
                           }
                     }
                  }
               case 2:
                  this.pushFollow(FOLLOW_comp_for_in_testlist_gexp4078);
                  this.comp_for();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
            }

         }
      } catch (RecognitionException var8) {
         throw var8;
      } finally {
         ;
      }
   }

   public final void lambdef() throws RecognitionException {
      try {
         this.match(this.input, 31, FOLLOW_LAMBDA_in_lambdef4118);
         if (!this.state.failed) {
            int alt111 = 2;
            int LA111_0 = this.input.LA(1);
            if (LA111_0 == 9 || LA111_0 == 43 || LA111_0 >= 48 && LA111_0 <= 49) {
               alt111 = 1;
            }

            switch (alt111) {
               case 1:
                  this.pushFollow(FOLLOW_varargslist_in_lambdef4121);
                  this.varargslist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
               default:
                  this.match(this.input, 45, FOLLOW_COLON_in_lambdef4125);
                  if (!this.state.failed) {
                     this.pushFollow(FOLLOW_test_in_lambdef4127);
                     this.test();
                     --this.state._fsp;
                     if (!this.state.failed) {
                        ;
                     }
                  }
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void trailer() throws RecognitionException {
      try {
         int alt113 = true;
         byte alt113;
         switch (this.input.LA(1)) {
            case 10:
               alt113 = 3;
               break;
            case 43:
               alt113 = 1;
               break;
            case 81:
               alt113 = 2;
               break;
            default:
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 113, 0, this.input);
               throw nvae;
         }

         switch (alt113) {
            case 1:
               this.match(this.input, 43, FOLLOW_LPAREN_in_trailer4145);
               if (this.state.failed) {
                  return;
               }

               int alt112 = true;
               int LA112_0 = this.input.LA(1);
               byte alt112;
               if (LA112_0 == 6 || LA112_0 == 9 || LA112_0 >= 31 && LA112_0 <= 32 || LA112_0 == 43 || LA112_0 >= 48 && LA112_0 <= 49 || LA112_0 >= 75 && LA112_0 <= 76 || LA112_0 >= 80 && LA112_0 <= 81 || LA112_0 == 83 || LA112_0 >= 85 && LA112_0 <= 90 || LA112_0 >= 99 && LA112_0 <= 100) {
                  alt112 = 1;
               } else {
                  if (LA112_0 != 44) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 112, 0, this.input);
                     throw nvae;
                  }

                  alt112 = 2;
               }

               switch (alt112) {
                  case 1:
                     this.pushFollow(FOLLOW_arglist_in_trailer4156);
                     this.arglist();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  case 2:
                  default:
                     this.match(this.input, 44, FOLLOW_RPAREN_in_trailer4184);
                     if (this.state.failed) {
                        return;
                     }

                     return;
               }
            case 2:
               this.match(this.input, 81, FOLLOW_LBRACK_in_trailer4192);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_subscriptlist_in_trailer4194);
               this.subscriptlist();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 82, FOLLOW_RBRACK_in_trailer4196);
               if (this.state.failed) {
                  return;
               }
               break;
            case 3:
               this.match(this.input, 10, FOLLOW_DOT_in_trailer4204);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_attr_in_trailer4206);
               this.attr();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var8) {
         throw var8;
      } finally {
         ;
      }
   }

   public final void subscriptlist() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_subscript_in_subscriptlist4224);
         this.subscript();
         --this.state._fsp;
         if (!this.state.failed) {
            do {
               int alt115 = 2;
               int LA115_0 = this.input.LA(1);
               if (LA115_0 == 47) {
                  int LA114_1 = this.input.LA(2);
                  if (LA114_1 == 6 || LA114_1 >= 9 && LA114_1 <= 10 || LA114_1 >= 31 && LA114_1 <= 32 || LA114_1 == 43 || LA114_1 == 45 || LA114_1 >= 75 && LA114_1 <= 76 || LA114_1 >= 80 && LA114_1 <= 81 || LA114_1 == 83 || LA114_1 >= 85 && LA114_1 <= 90 || LA114_1 >= 99 && LA114_1 <= 100) {
                     alt115 = 1;
                  }
               }

               switch (alt115) {
                  case 1:
                     this.match(this.input, 47, FOLLOW_COMMA_in_subscriptlist4234);
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_subscript_in_subscriptlist4236);
                     this.subscript();
                     --this.state._fsp;
                     break;
                  default:
                     alt115 = 2;
                     LA115_0 = this.input.LA(1);
                     if (LA115_0 == 47) {
                        alt115 = 1;
                     }

                     switch (alt115) {
                        case 1:
                           this.match(this.input, 47, FOLLOW_COMMA_in_subscriptlist4241);
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           return;
                     }
               }
            } while(!this.state.failed);

         }
      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void subscript() throws RecognitionException {
      try {
         int alt121 = true;
         int alt121 = this.dfa121.predict(this.input);
         byte alt119;
         int LA119_0;
         byte alt116;
         int LA116_0;
         switch (alt121) {
            case 1:
               this.match(this.input, 10, FOLLOW_DOT_in_subscript4261);
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 10, FOLLOW_DOT_in_subscript4263);
               if (this.state.failed) {
                  return;
               }

               this.match(this.input, 10, FOLLOW_DOT_in_subscript4265);
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_test_in_subscript4284);
               this.test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               alt119 = 2;
               LA119_0 = this.input.LA(1);
               if (LA119_0 == 45) {
                  alt119 = 1;
               }

               switch (alt119) {
                  case 1:
                     this.match(this.input, 45, FOLLOW_COLON_in_subscript4287);
                     if (this.state.failed) {
                        return;
                     }

                     alt116 = 2;
                     LA116_0 = this.input.LA(1);
                     if (LA116_0 == 6 || LA116_0 == 9 || LA116_0 >= 31 && LA116_0 <= 32 || LA116_0 == 43 || LA116_0 >= 75 && LA116_0 <= 76 || LA116_0 >= 80 && LA116_0 <= 81 || LA116_0 == 83 || LA116_0 >= 85 && LA116_0 <= 90 || LA116_0 >= 99 && LA116_0 <= 100) {
                        alt116 = 1;
                     }

                     switch (alt116) {
                        case 1:
                           this.pushFollow(FOLLOW_test_in_subscript4290);
                           this.test();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           int alt117 = 2;
                           int LA117_0 = this.input.LA(1);
                           if (LA117_0 == 45) {
                              alt117 = 1;
                           }

                           switch (alt117) {
                              case 1:
                                 this.pushFollow(FOLLOW_sliceop_in_subscript4295);
                                 this.sliceop();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return;
                                 }

                                 return;
                           }
                     }
                  default:
                     return;
               }
            case 3:
               this.match(this.input, 45, FOLLOW_COLON_in_subscript4316);
               if (this.state.failed) {
                  return;
               }

               alt119 = 2;
               LA119_0 = this.input.LA(1);
               if (LA119_0 == 6 || LA119_0 == 9 || LA119_0 >= 31 && LA119_0 <= 32 || LA119_0 == 43 || LA119_0 >= 75 && LA119_0 <= 76 || LA119_0 >= 80 && LA119_0 <= 81 || LA119_0 == 83 || LA119_0 >= 85 && LA119_0 <= 90 || LA119_0 >= 99 && LA119_0 <= 100) {
                  alt119 = 1;
               }

               switch (alt119) {
                  case 1:
                     this.pushFollow(FOLLOW_test_in_subscript4319);
                     this.test();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  default:
                     alt116 = 2;
                     LA116_0 = this.input.LA(1);
                     if (LA116_0 == 45) {
                        alt116 = 1;
                     }

                     switch (alt116) {
                        case 1:
                           this.pushFollow(FOLLOW_sliceop_in_subscript4324);
                           this.sliceop();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           return;
                        default:
                           return;
                     }
               }
            case 4:
               this.pushFollow(FOLLOW_test_in_subscript4334);
               this.test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var11) {
         throw var11;
      } finally {
         ;
      }
   }

   public final void sliceop() throws RecognitionException {
      try {
         this.match(this.input, 45, FOLLOW_COLON_in_sliceop4352);
         if (!this.state.failed) {
            int alt122 = true;
            int LA122_0 = this.input.LA(1);
            byte alt122;
            if (LA122_0 != 6 && LA122_0 != 9 && (LA122_0 < 31 || LA122_0 > 32) && LA122_0 != 43 && (LA122_0 < 75 || LA122_0 > 76) && (LA122_0 < 80 || LA122_0 > 81) && LA122_0 != 83 && (LA122_0 < 85 || LA122_0 > 90) && (LA122_0 < 99 || LA122_0 > 100)) {
               if (LA122_0 != 47 && LA122_0 != 82) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 122, 0, this.input);
                  throw nvae;
               }

               alt122 = 2;
            } else {
               alt122 = 1;
            }

            switch (alt122) {
               case 1:
                  this.pushFollow(FOLLOW_test_in_sliceop4360);
                  this.test();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
               case 2:
               default:
            }
         }
      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void exprlist() throws RecognitionException {
      try {
         int alt125 = true;
         int alt125 = this.dfa125.predict(this.input);
         switch (alt125) {
            case 1:
               this.pushFollow(FOLLOW_expr_in_exprlist4400);
               this.expr();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               while(true) {
                  int alt123 = true;
                  int alt123 = this.dfa123.predict(this.input);
                  switch (alt123) {
                     case 1:
                        this.match(this.input, 47, FOLLOW_COMMA_in_exprlist4411);
                        if (this.state.failed) {
                           return;
                        }

                        this.pushFollow(FOLLOW_expr_in_exprlist4413);
                        this.expr();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     default:
                        int alt124 = 2;
                        int LA124_0 = this.input.LA(1);
                        if (LA124_0 == 47) {
                           alt124 = 1;
                        }

                        switch (alt124) {
                           case 1:
                              this.match(this.input, 47, FOLLOW_COMMA_in_exprlist4418);
                              if (this.state.failed) {
                                 return;
                              }

                              return;
                           default:
                              return;
                        }
                  }
               }
            case 2:
               this.pushFollow(FOLLOW_expr_in_exprlist4428);
               this.expr();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void del_list() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_expr_in_del_list4447);
         this.expr();
         --this.state._fsp;
         if (!this.state.failed) {
            do {
               int alt126 = true;
               int alt126 = this.dfa126.predict(this.input);
               switch (alt126) {
                  case 1:
                     this.match(this.input, 47, FOLLOW_COMMA_in_del_list4458);
                     if (this.state.failed) {
                        return;
                     }

                     this.pushFollow(FOLLOW_expr_in_del_list4460);
                     this.expr();
                     --this.state._fsp;
                     break;
                  default:
                     int alt127 = 2;
                     int LA127_0 = this.input.LA(1);
                     if (LA127_0 == 47) {
                        alt127 = 1;
                     }

                     switch (alt127) {
                        case 1:
                           this.match(this.input, 47, FOLLOW_COMMA_in_del_list4465);
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           return;
                     }
               }
            } while(!this.state.failed);

         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void testlist() throws RecognitionException {
      try {
         int alt130 = true;
         int alt130 = this.dfa130.predict(this.input);
         switch (alt130) {
            case 1:
               this.pushFollow(FOLLOW_test_in_testlist4496);
               this.test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               while(true) {
                  int alt128 = true;
                  int alt128 = this.dfa128.predict(this.input);
                  switch (alt128) {
                     case 1:
                        this.match(this.input, 47, FOLLOW_COMMA_in_testlist4507);
                        if (this.state.failed) {
                           return;
                        }

                        this.pushFollow(FOLLOW_test_in_testlist4509);
                        this.test();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     default:
                        int alt129 = 2;
                        int LA129_0 = this.input.LA(1);
                        if (LA129_0 == 47) {
                           alt129 = 1;
                        }

                        switch (alt129) {
                           case 1:
                              this.match(this.input, 47, FOLLOW_COMMA_in_testlist4514);
                              if (this.state.failed) {
                                 return;
                              }

                              return;
                           default:
                              return;
                        }
                  }
               }
            case 2:
               this.pushFollow(FOLLOW_test_in_testlist4524);
               this.test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void dictorsetmaker() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_test_in_dictorsetmaker4543);
         this.test();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt136 = true;
            int LA136_0 = this.input.LA(1);
            byte alt136;
            if (LA136_0 != 45 && LA136_0 != 47 && LA136_0 != 84) {
               if (LA136_0 != 25) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 136, 0, this.input);
                  throw nvae;
               }

               alt136 = 2;
            } else {
               alt136 = 1;
            }

            switch (alt136) {
               case 1:
                  int alt134 = true;
                  int LA134_0 = this.input.LA(1);
                  byte alt134;
                  if (LA134_0 == 45) {
                     alt134 = 1;
                  } else {
                     if (LA134_0 != 47 && LA134_0 != 84) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 134, 0, this.input);
                        throw nvae;
                     }

                     alt134 = 2;
                  }

                  byte alt132;
                  int LA132_0;
                  int alt131;
                  label378:
                  switch (alt134) {
                     case 1:
                        this.match(this.input, 45, FOLLOW_COLON_in_dictorsetmaker4570);
                        if (this.state.failed) {
                           return;
                        }

                        this.pushFollow(FOLLOW_test_in_dictorsetmaker4572);
                        this.test();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }

                        int alt132 = true;
                        LA132_0 = this.input.LA(1);
                        if (LA132_0 == 25) {
                           alt132 = 1;
                        } else {
                           if (LA132_0 != 47 && LA132_0 != 84) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              NoViableAltException nvae = new NoViableAltException("", 132, 0, this.input);
                              throw nvae;
                           }

                           alt132 = 2;
                        }

                        switch (alt132) {
                           case 1:
                              this.pushFollow(FOLLOW_comp_for_in_dictorsetmaker4591);
                              this.comp_for();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return;
                              }
                              break label378;
                           case 2:
                              while(true) {
                                 int alt131 = true;
                                 alt131 = this.dfa131.predict(this.input);
                                 switch (alt131) {
                                    case 1:
                                       this.match(this.input, 47, FOLLOW_COMMA_in_dictorsetmaker4618);
                                       if (this.state.failed) {
                                          return;
                                       }

                                       this.pushFollow(FOLLOW_test_in_dictorsetmaker4620);
                                       this.test();
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          return;
                                       }

                                       this.match(this.input, 45, FOLLOW_COLON_in_dictorsetmaker4622);
                                       if (this.state.failed) {
                                          return;
                                       }

                                       this.pushFollow(FOLLOW_test_in_dictorsetmaker4624);
                                       this.test();
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          return;
                                       }
                                       break;
                                    default:
                                       break label378;
                                 }
                              }
                           default:
                              break label378;
                        }
                     case 2:
                        label412:
                        while(true) {
                           alt132 = 2;
                           LA132_0 = this.input.LA(1);
                           if (LA132_0 == 47) {
                              alt131 = this.input.LA(2);
                              if (alt131 == 6 || alt131 == 9 || alt131 >= 31 && alt131 <= 32 || alt131 == 43 || alt131 >= 75 && alt131 <= 76 || alt131 >= 80 && alt131 <= 81 || alt131 == 83 || alt131 >= 85 && alt131 <= 90 || alt131 >= 99 && alt131 <= 100) {
                                 alt132 = 1;
                              }
                           }

                           switch (alt132) {
                              case 1:
                                 this.match(this.input, 47, FOLLOW_COMMA_in_dictorsetmaker4660);
                                 if (this.state.failed) {
                                    return;
                                 }

                                 this.pushFollow(FOLLOW_test_in_dictorsetmaker4662);
                                 this.test();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return;
                                 }
                                 break;
                              default:
                                 break label412;
                           }
                        }
                  }

                  alt132 = 2;
                  LA132_0 = this.input.LA(1);
                  if (LA132_0 == 47) {
                     alt132 = 1;
                  }

                  switch (alt132) {
                     case 1:
                        this.match(this.input, 47, FOLLOW_COMMA_in_dictorsetmaker4695);
                        if (this.state.failed) {
                           return;
                        }

                        return;
                     default:
                        return;
                  }
               case 2:
                  this.pushFollow(FOLLOW_comp_for_in_dictorsetmaker4710);
                  this.comp_for();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
            }

         }
      } catch (RecognitionException var11) {
         throw var11;
      } finally {
         ;
      }
   }

   public final void classdef() throws RecognitionException {
      try {
         int alt137 = 2;
         int LA137_0 = this.input.LA(1);
         if (LA137_0 == 42) {
            alt137 = 1;
         }

         switch (alt137) {
            case 1:
               this.pushFollow(FOLLOW_decorators_in_classdef4739);
               this.decorators();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
            default:
               this.match(this.input, 16, FOLLOW_CLASS_in_classdef4742);
               if (!this.state.failed) {
                  this.match(this.input, 9, FOLLOW_NAME_in_classdef4744);
                  if (!this.state.failed) {
                     int alt139 = 2;
                     int LA139_0 = this.input.LA(1);
                     if (LA139_0 == 43) {
                        alt139 = 1;
                     }

                     switch (alt139) {
                        case 1:
                           this.match(this.input, 43, FOLLOW_LPAREN_in_classdef4747);
                           if (this.state.failed) {
                              return;
                           } else {
                              int alt138 = 2;
                              int LA138_0 = this.input.LA(1);
                              if (LA138_0 == 6 || LA138_0 == 9 || LA138_0 >= 31 && LA138_0 <= 32 || LA138_0 == 43 || LA138_0 >= 75 && LA138_0 <= 76 || LA138_0 >= 80 && LA138_0 <= 81 || LA138_0 == 83 || LA138_0 >= 85 && LA138_0 <= 90 || LA138_0 >= 99 && LA138_0 <= 100) {
                                 alt138 = 1;
                              }

                              switch (alt138) {
                                 case 1:
                                    this.pushFollow(FOLLOW_testlist_in_classdef4749);
                                    this.testlist();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return;
                                    }
                                 default:
                                    this.match(this.input, 44, FOLLOW_RPAREN_in_classdef4752);
                                    if (this.state.failed) {
                                       return;
                                    }
                              }
                           }
                        default:
                           this.match(this.input, 45, FOLLOW_COLON_in_classdef4756);
                           if (!this.state.failed) {
                              this.pushFollow(FOLLOW_suite_in_classdef4758);
                              this.suite();
                              --this.state._fsp;
                              if (!this.state.failed) {
                                 ;
                              }
                           }
                     }
                  }
               }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final void arglist() throws RecognitionException {
      try {
         int alt147 = true;
         byte alt147;
         switch (this.input.LA(1)) {
            case 6:
            case 9:
            case 31:
            case 32:
            case 43:
            case 75:
            case 76:
            case 80:
            case 81:
            case 83:
            case 85:
            case 86:
            case 87:
            case 88:
            case 89:
            case 90:
            case 99:
            case 100:
               alt147 = 1;
               break;
            case 48:
               alt147 = 2;
               break;
            case 49:
               alt147 = 3;
               break;
            default:
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 147, 0, this.input);
               throw nvae;
         }

         byte alt146;
         int LA146_0;
         int LA145_1;
         switch (alt147) {
            case 1:
               this.pushFollow(FOLLOW_argument_in_arglist4778);
               this.argument();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               while(true) {
                  alt146 = 2;
                  LA146_0 = this.input.LA(1);
                  if (LA146_0 == 47) {
                     LA145_1 = this.input.LA(2);
                     if (LA145_1 == 6 || LA145_1 == 9 || LA145_1 >= 31 && LA145_1 <= 32 || LA145_1 == 43 || LA145_1 >= 75 && LA145_1 <= 76 || LA145_1 >= 80 && LA145_1 <= 81 || LA145_1 == 83 || LA145_1 >= 85 && LA145_1 <= 90 || LA145_1 >= 99 && LA145_1 <= 100) {
                        alt146 = 1;
                     }
                  }

                  switch (alt146) {
                     case 1:
                        this.match(this.input, 47, FOLLOW_COMMA_in_arglist4781);
                        if (this.state.failed) {
                           return;
                        }

                        this.pushFollow(FOLLOW_argument_in_arglist4783);
                        this.argument();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     default:
                        alt146 = 2;
                        LA146_0 = this.input.LA(1);
                        if (LA146_0 == 47) {
                           alt146 = 1;
                        }

                        switch (alt146) {
                           case 1:
                              this.match(this.input, 47, FOLLOW_COMMA_in_arglist4798);
                              if (this.state.failed) {
                                 return;
                              }

                              int alt143 = 3;
                              int LA143_0 = this.input.LA(1);
                              if (LA143_0 == 48) {
                                 alt143 = 1;
                              } else if (LA143_0 == 49) {
                                 alt143 = 2;
                              }

                              switch (alt143) {
                                 case 1:
                                    this.match(this.input, 48, FOLLOW_STAR_in_arglist4816);
                                    if (this.state.failed) {
                                       return;
                                    }

                                    this.pushFollow(FOLLOW_test_in_arglist4818);
                                    this.test();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return;
                                    }

                                    while(true) {
                                       int alt142 = 2;
                                       int LA142_0 = this.input.LA(1);
                                       if (LA142_0 == 47) {
                                          int LA141_1 = this.input.LA(2);
                                          if (LA141_1 == 6 || LA141_1 == 9 || LA141_1 >= 31 && LA141_1 <= 32 || LA141_1 == 43 || LA141_1 >= 75 && LA141_1 <= 76 || LA141_1 >= 80 && LA141_1 <= 81 || LA141_1 == 83 || LA141_1 >= 85 && LA141_1 <= 90 || LA141_1 >= 99 && LA141_1 <= 100) {
                                             alt142 = 1;
                                          }
                                       }

                                       switch (alt142) {
                                          case 1:
                                             this.match(this.input, 47, FOLLOW_COMMA_in_arglist4821);
                                             if (this.state.failed) {
                                                return;
                                             }

                                             this.pushFollow(FOLLOW_argument_in_arglist4823);
                                             this.argument();
                                             --this.state._fsp;
                                             if (this.state.failed) {
                                                return;
                                             }
                                             break;
                                          default:
                                             alt142 = 2;
                                             LA142_0 = this.input.LA(1);
                                             if (LA142_0 == 47) {
                                                alt142 = 1;
                                             }

                                             switch (alt142) {
                                                case 1:
                                                   this.match(this.input, 47, FOLLOW_COMMA_in_arglist4828);
                                                   if (this.state.failed) {
                                                      return;
                                                   }

                                                   this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_arglist4830);
                                                   if (this.state.failed) {
                                                      return;
                                                   }

                                                   this.pushFollow(FOLLOW_test_in_arglist4832);
                                                   this.test();
                                                   --this.state._fsp;
                                                   if (this.state.failed) {
                                                      return;
                                                   }

                                                   return;
                                                default:
                                                   return;
                                             }
                                       }
                                    }
                                 case 2:
                                    this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_arglist4852);
                                    if (this.state.failed) {
                                       return;
                                    }

                                    this.pushFollow(FOLLOW_test_in_arglist4854);
                                    this.test();
                                    --this.state._fsp;
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
               this.match(this.input, 48, FOLLOW_STAR_in_arglist4892);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_test_in_arglist4894);
               this.test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }

               while(true) {
                  alt146 = 2;
                  LA146_0 = this.input.LA(1);
                  if (LA146_0 == 47) {
                     LA145_1 = this.input.LA(2);
                     if (LA145_1 == 6 || LA145_1 == 9 || LA145_1 >= 31 && LA145_1 <= 32 || LA145_1 == 43 || LA145_1 >= 75 && LA145_1 <= 76 || LA145_1 >= 80 && LA145_1 <= 81 || LA145_1 == 83 || LA145_1 >= 85 && LA145_1 <= 90 || LA145_1 >= 99 && LA145_1 <= 100) {
                        alt146 = 1;
                     }
                  }

                  switch (alt146) {
                     case 1:
                        this.match(this.input, 47, FOLLOW_COMMA_in_arglist4897);
                        if (this.state.failed) {
                           return;
                        }

                        this.pushFollow(FOLLOW_argument_in_arglist4899);
                        this.argument();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     default:
                        alt146 = 2;
                        LA146_0 = this.input.LA(1);
                        if (LA146_0 == 47) {
                           alt146 = 1;
                        }

                        switch (alt146) {
                           case 1:
                              this.match(this.input, 47, FOLLOW_COMMA_in_arglist4904);
                              if (this.state.failed) {
                                 return;
                              }

                              this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_arglist4906);
                              if (this.state.failed) {
                                 return;
                              }

                              this.pushFollow(FOLLOW_test_in_arglist4908);
                              this.test();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return;
                              }

                              return;
                           default:
                              return;
                        }
                  }
               }
            case 3:
               this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_arglist4918);
               if (this.state.failed) {
                  return;
               }

               this.pushFollow(FOLLOW_test_in_arglist4920);
               this.test();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var12) {
         throw var12;
      } finally {
         ;
      }
   }

   public final void argument() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_test_in_argument4938);
         this.test();
         --this.state._fsp;
         if (!this.state.failed) {
            int alt148 = true;
            byte alt148;
            switch (this.input.LA(1)) {
               case 25:
                  alt148 = 2;
                  break;
               case 44:
               case 47:
                  alt148 = 3;
                  break;
               case 46:
                  alt148 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 148, 0, this.input);
                  throw nvae;
            }

            switch (alt148) {
               case 1:
                  this.match(this.input, 46, FOLLOW_ASSIGN_in_argument4950);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_test_in_argument4952);
                  this.test();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_comp_for_in_argument4965);
                  this.comp_for();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
               case 3:
            }

         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void list_iter() throws RecognitionException {
      try {
         int alt149 = true;
         int LA149_0 = this.input.LA(1);
         byte alt149;
         if (LA149_0 == 25) {
            alt149 = 1;
         } else {
            if (LA149_0 != 27) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 149, 0, this.input);
               throw nvae;
            }

            alt149 = 2;
         }

         switch (alt149) {
            case 1:
               this.pushFollow(FOLLOW_list_for_in_list_iter5003);
               this.list_for();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_list_if_in_list_iter5011);
               this.list_if();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void list_for() throws RecognitionException {
      try {
         this.match(this.input, 25, FOLLOW_FOR_in_list_for5029);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_exprlist_in_list_for5031);
            this.exprlist();
            --this.state._fsp;
            if (!this.state.failed) {
               this.match(this.input, 29, FOLLOW_IN_in_list_for5033);
               if (!this.state.failed) {
                  this.pushFollow(FOLLOW_testlist_in_list_for5035);
                  this.testlist();
                  --this.state._fsp;
                  if (!this.state.failed) {
                     int alt150 = 2;
                     int LA150_0 = this.input.LA(1);
                     if (LA150_0 == 25 || LA150_0 == 27) {
                        alt150 = 1;
                     }

                     switch (alt150) {
                        case 1:
                           this.pushFollow(FOLLOW_list_iter_in_list_for5038);
                           this.list_iter();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }
                        default:
                     }
                  }
               }
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void list_if() throws RecognitionException {
      try {
         this.match(this.input, 27, FOLLOW_IF_in_list_if5058);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_test_in_list_if5060);
            this.test();
            --this.state._fsp;
            if (!this.state.failed) {
               int alt151 = 2;
               int LA151_0 = this.input.LA(1);
               if (LA151_0 == 25 || LA151_0 == 27) {
                  alt151 = 1;
               }

               switch (alt151) {
                  case 1:
                     this.pushFollow(FOLLOW_list_iter_in_list_if5063);
                     this.list_iter();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  default:
               }
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void comp_iter() throws RecognitionException {
      try {
         int alt152 = true;
         int LA152_0 = this.input.LA(1);
         byte alt152;
         if (LA152_0 == 25) {
            alt152 = 1;
         } else {
            if (LA152_0 != 27) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               NoViableAltException nvae = new NoViableAltException("", 152, 0, this.input);
               throw nvae;
            }

            alt152 = 2;
         }

         switch (alt152) {
            case 1:
               this.pushFollow(FOLLOW_comp_for_in_comp_iter5083);
               this.comp_for();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
               break;
            case 2:
               this.pushFollow(FOLLOW_comp_if_in_comp_iter5091);
               this.comp_if();
               --this.state._fsp;
               if (this.state.failed) {
                  return;
               }
         }

      } catch (RecognitionException var7) {
         throw var7;
      } finally {
         ;
      }
   }

   public final void comp_for() throws RecognitionException {
      try {
         this.match(this.input, 25, FOLLOW_FOR_in_comp_for5109);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_exprlist_in_comp_for5111);
            this.exprlist();
            --this.state._fsp;
            if (!this.state.failed) {
               this.match(this.input, 29, FOLLOW_IN_in_comp_for5113);
               if (!this.state.failed) {
                  this.pushFollow(FOLLOW_or_test_in_comp_for5115);
                  this.or_test();
                  --this.state._fsp;
                  if (!this.state.failed) {
                     int alt153 = 2;
                     int LA153_0 = this.input.LA(1);
                     if (LA153_0 == 25 || LA153_0 == 27) {
                        alt153 = 1;
                     }

                     switch (alt153) {
                        case 1:
                           this.pushFollow(FOLLOW_comp_iter_in_comp_for5117);
                           this.comp_iter();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }
                        default:
                     }
                  }
               }
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void comp_if() throws RecognitionException {
      try {
         this.match(this.input, 27, FOLLOW_IF_in_comp_if5136);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_test_in_comp_if5138);
            this.test();
            --this.state._fsp;
            if (!this.state.failed) {
               int alt154 = 2;
               int LA154_0 = this.input.LA(1);
               if (LA154_0 == 25 || LA154_0 == 27) {
                  alt154 = 1;
               }

               switch (alt154) {
                  case 1:
                     this.pushFollow(FOLLOW_comp_iter_in_comp_if5140);
                     this.comp_iter();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  default:
               }
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void yield_expr() throws RecognitionException {
      try {
         this.match(this.input, 41, FOLLOW_YIELD_in_yield_expr5159);
         if (!this.state.failed) {
            int alt155 = 2;
            int LA155_0 = this.input.LA(1);
            if (LA155_0 == 6 || LA155_0 == 9 || LA155_0 >= 31 && LA155_0 <= 32 || LA155_0 == 43 || LA155_0 >= 75 && LA155_0 <= 76 || LA155_0 >= 80 && LA155_0 <= 81 || LA155_0 == 83 || LA155_0 >= 85 && LA155_0 <= 90 || LA155_0 >= 99 && LA155_0 <= 100) {
               alt155 = 1;
            }

            switch (alt155) {
               case 1:
                  this.pushFollow(FOLLOW_testlist_in_yield_expr5161);
                  this.testlist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
               default:
            }
         }
      } catch (RecognitionException var6) {
         throw var6;
      } finally {
         ;
      }
   }

   public final void synpred1_PythonPartial_fragment() throws RecognitionException {
      this.match(this.input, 43, FOLLOW_LPAREN_in_synpred1_PythonPartial807);
      if (!this.state.failed) {
         this.pushFollow(FOLLOW_fpdef_in_synpred1_PythonPartial809);
         this.fpdef();
         --this.state._fsp;
         if (!this.state.failed) {
            this.match(this.input, 47, FOLLOW_COMMA_in_synpred1_PythonPartial811);
            if (!this.state.failed) {
               ;
            }
         }
      }
   }

   public final void synpred2_PythonPartial_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_testlist_in_synpred2_PythonPartial1107);
      this.testlist();
      --this.state._fsp;
      if (!this.state.failed) {
         this.pushFollow(FOLLOW_augassign_in_synpred2_PythonPartial1109);
         this.augassign();
         --this.state._fsp;
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred3_PythonPartial_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_testlist_in_synpred3_PythonPartial1187);
      this.testlist();
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 46, FOLLOW_ASSIGN_in_synpred3_PythonPartial1189);
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred4_PythonPartial_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_test_in_synpred4_PythonPartial1472);
      this.test();
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 47, FOLLOW_COMMA_in_synpred4_PythonPartial1474);
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred5_PythonPartial_fragment() throws RecognitionException {
      int alt156 = 2;
      int LA156_0 = this.input.LA(1);
      if (LA156_0 == 42) {
         alt156 = 1;
      }

      switch (alt156) {
         case 1:
            this.pushFollow(FOLLOW_decorators_in_synpred5_PythonPartial2157);
            this.decorators();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }
         default:
            this.match(this.input, 18, FOLLOW_DEF_in_synpred5_PythonPartial2160);
            if (!this.state.failed) {
               ;
            }
      }
   }

   public final void synpred6_PythonPartial_fragment() throws RecognitionException {
      int alt157 = 2;
      int LA157_0 = this.input.LA(1);
      if (LA157_0 == 42) {
         alt157 = 1;
      }

      switch (alt157) {
         case 1:
            this.pushFollow(FOLLOW_decorators_in_synpred6_PythonPartial2174);
            this.decorators();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }
         default:
            this.match(this.input, 16, FOLLOW_CLASS_in_synpred6_PythonPartial2177);
            if (!this.state.failed) {
               ;
            }
      }
   }

   public final void synpred7_PythonPartial_fragment() throws RecognitionException {
      this.match(this.input, 27, FOLLOW_IF_in_synpred7_PythonPartial2752);
      if (!this.state.failed) {
         this.pushFollow(FOLLOW_or_test_in_synpred7_PythonPartial2754);
         this.or_test();
         --this.state._fsp;
         if (!this.state.failed) {
            this.match(this.input, 34, FOLLOW_ORELSE_in_synpred7_PythonPartial2756);
            if (!this.state.failed) {
               ;
            }
         }
      }
   }

   public final void synpred8_PythonPartial_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_test_in_synpred8_PythonPartial4274);
      this.test();
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 45, FOLLOW_COLON_in_synpred8_PythonPartial4276);
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred9_PythonPartial_fragment() throws RecognitionException {
      this.match(this.input, 45, FOLLOW_COLON_in_synpred9_PythonPartial4308);
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred10_PythonPartial_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_expr_in_synpred10_PythonPartial4393);
      this.expr();
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 47, FOLLOW_COMMA_in_synpred10_PythonPartial4395);
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred11_PythonPartial_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_test_in_synpred11_PythonPartial4486);
      this.test();
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 47, FOLLOW_COMMA_in_synpred11_PythonPartial4488);
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final boolean synpred3_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred3_PythonPartial_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred6_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred6_PythonPartial_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred10_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred10_PythonPartial_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred9_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred9_PythonPartial_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred5_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred5_PythonPartial_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred1_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred1_PythonPartial_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred11_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred11_PythonPartial_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred2_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred2_PythonPartial_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred7_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred7_PythonPartial_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred8_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred8_PythonPartial_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred4_PythonPartial() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred4_PythonPartial_fragment();
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
      int numStates = DFA27_transitionS.length;
      DFA27_transition = new short[numStates][];

      int i;
      for(i = 0; i < numStates; ++i) {
         DFA27_transition[i] = DFA.unpackEncodedString(DFA27_transitionS[i]);
      }

      DFA32_transitionS = new String[]{"\u0001\u0011\u0002\uffff\u0001\t\u0015\uffff\u0001\u0012\u0001\u0001\n\uffff\u0001\u0005\u001f\uffff\u0001\u0002\u0001\u0003\u0003\uffff\u0001\u0004\u0001\u0006\u0001\uffff\u0001\u0007\u0001\uffff\u0001\b\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\b\uffff\u0001\u000f\u0001\u0010", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", "", ""};
      DFA32_eot = DFA.unpackEncodedString("\u0016\uffff");
      DFA32_eof = DFA.unpackEncodedString("\u0016\uffff");
      DFA32_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0006\u0012\u0000\u0003\uffff");
      DFA32_max = DFA.unpackEncodedStringToUnsignedChars("\u0001d\u0012\u0000\u0003\uffff");
      DFA32_accept = DFA.unpackEncodedString("\u0013\uffff\u0001\u0001\u0001\u0002\u0001\u0003");
      DFA32_special = DFA.unpackEncodedString("\u0001\uffff\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0003\uffff}>");
      numStates = DFA32_transitionS.length;
      DFA32_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA32_transition[i] = DFA.unpackEncodedString(DFA32_transitionS[i]);
      }

      DFA36_transitionS = new String[]{"\u0001\u0011\u0002\uffff\u0001\t\u0015\uffff\u0001\u0012\u0001\u0001\n\uffff\u0001\u0005\u001f\uffff\u0001\u0002\u0001\u0003\u0003\uffff\u0001\u0004\u0001\u0006\u0001\uffff\u0001\u0007\u0001\uffff\u0001\b\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\b\uffff\u0001\u000f\u0001\u0010", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", ""};
      DFA36_eot = DFA.unpackEncodedString("\u0015\uffff");
      DFA36_eof = DFA.unpackEncodedString("\u0015\uffff");
      DFA36_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0006\u0012\u0000\u0002\uffff");
      DFA36_max = DFA.unpackEncodedStringToUnsignedChars("\u0001d\u0012\u0000\u0002\uffff");
      DFA36_accept = DFA.unpackEncodedString("\u0013\uffff\u0001\u0001\u0001\u0002");
      DFA36_special = DFA.unpackEncodedString("\u0001\uffff\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0002\uffff}>");
      numStates = DFA36_transitionS.length;
      DFA36_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA36_transition[i] = DFA.unpackEncodedString(DFA36_transitionS[i]);
      }

      DFA34_transitionS = new String[]{"\u0001\u0002'\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "\u0001\u0004\u0001\u0002\u0001\uffff\u0001\u0004\u0015\uffff\u0002\u0004\n\uffff\u0001\u0004\u0006\uffff\u0001\u0002\u0018\uffff\u0002\u0004\u0003\uffff\u0002\u0004\u0001\uffff\u0001\u0004\u0001\uffff\u0006\u0004\b\uffff\u0002\u0004", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA34_eot = DFA.unpackEncodedString("\u0018\uffff");
      DFA34_eof = DFA.unpackEncodedString("\u0002\u0002\u0016\uffff");
      DFA34_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0007\u0001\u0006\u0016\uffff");
      DFA34_max = DFA.unpackEncodedStringToUnsignedChars("\u00012\u0001d\u0016\uffff");
      DFA34_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u0013\uffff");
      DFA34_special = DFA.unpackEncodedString("\u0018\uffff}>");
      numStates = DFA34_transitionS.length;
      DFA34_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA34_transition[i] = DFA.unpackEncodedString(DFA34_transitionS[i]);
      }

      DFA45_transitionS = new String[]{"\u0001\u0002\u0001\u0001", "\u0001\u0002\u0001\u0001\u0011\uffff\u0001\u0003", "", ""};
      DFA45_eot = DFA.unpackEncodedString("\u0004\uffff");
      DFA45_eof = DFA.unpackEncodedString("\u0004\uffff");
      DFA45_min = DFA.unpackEncodedStringToUnsignedChars("\u0002\t\u0002\uffff");
      DFA45_max = DFA.unpackEncodedStringToUnsignedChars("\u0001\n\u0001\u001c\u0002\uffff");
      DFA45_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0001\u0001\u0002");
      DFA45_special = DFA.unpackEncodedString("\u0004\uffff}>");
      numStates = DFA45_transitionS.length;
      DFA45_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA45_transition[i] = DFA.unpackEncodedString(DFA45_transitionS[i]);
      }

      DFA57_transitionS = new String[]{"\u0001\b\u0001\uffff\u0001\u0007\u0006\uffff\u0001\u0003\u0001\uffff\u0001\u0001\n\uffff\u0001\u0004\u0001\u0002\u0001\u0005\u0001\uffff\u0001\u0006", "", "", "", "", "", "\u0001\uffff", "", "", ""};
      DFA57_eot = DFA.unpackEncodedString("\n\uffff");
      DFA57_eof = DFA.unpackEncodedString("\n\uffff");
      DFA57_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0010\u0005\uffff\u0001\u0000\u0003\uffff");
      DFA57_max = DFA.unpackEncodedStringToUnsignedChars("\u0001*\u0005\uffff\u0001\u0000\u0003\uffff");
      DFA57_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\uffff\u0001\u0006\u0001\u0007\u0001\b");
      DFA57_special = DFA.unpackEncodedString("\u0001\u0000\u0005\uffff\u0001\u0001\u0003\uffff}>");
      numStates = DFA57_transitionS.length;
      DFA57_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA57_transition[i] = DFA.unpackEncodedString(DFA57_transitionS[i]);
      }

      DFA75_transitionS = new String[]{"\u0001\u0002\u0005\uffff\u0001\u0002\u000b\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u0010\uffff\u0004\u0002\u0002\uffff\r\u0002\u0013\uffff\u0001\u0002\u0001\uffff\u0002\u0002", "\u0001\uffff", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA75_eot = DFA.unpackEncodedString("\u0010\uffff");
      DFA75_eof = DFA.unpackEncodedString("\u0001\u0002\u000f\uffff");
      DFA75_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0007\u0001\u0000\u000e\uffff");
      DFA75_max = DFA.unpackEncodedStringToUnsignedChars("\u0001U\u0001\u0000\u000e\uffff");
      DFA75_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\f\uffff\u0001\u0001");
      DFA75_special = DFA.unpackEncodedString("\u0001\uffff\u0001\u0000\u000e\uffff}>");
      numStates = DFA75_transitionS.length;
      DFA75_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA75_transition[i] = DFA.unpackEncodedString(DFA75_transitionS[i]);
      }

      DFA84_transitionS = new String[]{"\u0001\b\u0001\n\u0001\uffff\u0001\t\u001f\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007", "", "", "", "", "", "", "", "", "", "\u0001\f\u0002\uffff\u0001\f\u0016\uffff\u0001\u000b\n\uffff\u0001\f\u001f\uffff\u0002\f\u0003\uffff\u0002\f\u0001\uffff\u0001\f\u0001\uffff\u0006\f\b\uffff\u0002\f", "", ""};
      DFA84_eot = DFA.unpackEncodedString("\r\uffff");
      DFA84_eof = DFA.unpackEncodedString("\r\uffff");
      DFA84_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u001d\t\uffff\u0001\u0006\u0002\uffff");
      DFA84_max = DFA.unpackEncodedStringToUnsignedChars("\u0001F\t\uffff\u0001d\u0002\uffff");
      DFA84_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\uffff\u0001\u000b\u0001\n");
      DFA84_special = DFA.unpackEncodedString("\r\uffff}>");
      numStates = DFA84_transitionS.length;
      DFA84_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA84_transition[i] = DFA.unpackEncodedString(DFA84_transitionS[i]);
      }

      DFA108_transitionS = new String[]{"\u0001\u0002\u0002\uffff\u0001\u0001", "\u0001\u0004\u0002\uffff\u0001\u0004\u0015\uffff\u0002\u0004\n\uffff\u0001\u0004\u0001\u0002\u001e\uffff\u0002\u0004\u0003\uffff\u0002\u0004\u0001\uffff\u0001\u0004\u0001\uffff\u0006\u0004\b\uffff\u0002\u0004", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA108_eot = DFA.unpackEncodedString("\u0016\uffff");
      DFA108_eof = DFA.unpackEncodedString("\u0016\uffff");
      DFA108_min = DFA.unpackEncodedStringToUnsignedChars("\u0001,\u0001\u0006\u0014\uffff");
      DFA108_max = DFA.unpackEncodedStringToUnsignedChars("\u0001/\u0001d\u0014\uffff");
      DFA108_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u0011\uffff");
      DFA108_special = DFA.unpackEncodedString("\u0016\uffff}>");
      numStates = DFA108_transitionS.length;
      DFA108_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA108_transition[i] = DFA.unpackEncodedString(DFA108_transitionS[i]);
      }

      DFA121_transitionS = new String[]{"\u0001\u0012\u0002\uffff\u0001\n\u0001\u0001\u0014\uffff\u0001\u0013\u0001\u0002\n\uffff\u0001\u0006\u0001\uffff\u0001\u0014\u001d\uffff\u0001\u0003\u0001\u0004\u0003\uffff\u0001\u0005\u0001\u0007\u0001\uffff\u0001\b\u0001\uffff\u0001\t\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\b\uffff\u0001\u0010\u0001\u0011", "", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", "", ""};
      DFA121_eot = DFA.unpackEncodedString("\u0017\uffff");
      DFA121_eof = DFA.unpackEncodedString("\u0017\uffff");
      DFA121_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0006\u0001\uffff\u0012\u0000\u0003\uffff");
      DFA121_max = DFA.unpackEncodedStringToUnsignedChars("\u0001d\u0001\uffff\u0012\u0000\u0003\uffff");
      DFA121_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0012\uffff\u0001\u0003\u0001\u0002\u0001\u0004");
      DFA121_special = DFA.unpackEncodedString("\u0001\u0000\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0001\u0012\u0003\uffff}>");
      numStates = DFA121_transitionS.length;
      DFA121_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA121_transition[i] = DFA.unpackEncodedString(DFA121_transitionS[i]);
      }

      DFA125_transitionS = new String[]{"\u0001\u0010\u0002\uffff\u0001\b!\uffff\u0001\u0004\u001f\uffff\u0001\u0001\u0001\u0002\u0003\uffff\u0001\u0003\u0001\u0005\u0001\uffff\u0001\u0006\u0001\uffff\u0001\u0007\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\b\uffff\u0001\u000e\u0001\u000f", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", ""};
      DFA125_eot = DFA.unpackEncodedString("\u0013\uffff");
      DFA125_eof = DFA.unpackEncodedString("\u0013\uffff");
      DFA125_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0006\u0010\u0000\u0002\uffff");
      DFA125_max = DFA.unpackEncodedStringToUnsignedChars("\u0001d\u0010\u0000\u0002\uffff");
      DFA125_accept = DFA.unpackEncodedString("\u0011\uffff\u0001\u0001\u0001\u0002");
      DFA125_special = DFA.unpackEncodedString("\u0001\uffff\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0002\uffff}>");
      numStates = DFA125_transitionS.length;
      DFA125_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA125_transition[i] = DFA.unpackEncodedString(DFA125_transitionS[i]);
      }

      DFA123_transitionS = new String[]{"\u0001\u0002\u0015\uffff\u0001\u0002\u0011\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "\u0001\b\u0001\u0002\u0001\uffff\u0001\b\u0013\uffff\u0001\u0002\r\uffff\u0001\b\u0006\uffff\u0001\u0002\u0018\uffff\u0002\b\u0003\uffff\u0002\b\u0001\uffff\u0001\b\u0001\uffff\u0006\b\b\uffff\u0002\b", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA123_eot = DFA.unpackEncodedString("\u0018\uffff");
      DFA123_eof = DFA.unpackEncodedString("\u0002\u0002\u0016\uffff");
      DFA123_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0007\u0001\u0006\u0016\uffff");
      DFA123_max = DFA.unpackEncodedStringToUnsignedChars("\u00012\u0001d\u0016\uffff");
      DFA123_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0005\uffff\u0001\u0001\u000f\uffff");
      DFA123_special = DFA.unpackEncodedString("\u0018\uffff}>");
      numStates = DFA123_transitionS.length;
      DFA123_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA123_transition[i] = DFA.unpackEncodedString(DFA123_transitionS[i]);
      }

      DFA126_transitionS = new String[]{"\u0001\u0001", "\u0001\u0003\u0002\uffff\u0001\u0003!\uffff\u0001\u0003\u001f\uffff\u0002\u0003\u0003\uffff\u0002\u0003\u0001\uffff\u0001\u0003\u0001\uffff\u0006\u0003\b\uffff\u0002\u0003", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA126_eot = DFA.unpackEncodedString("\u0014\uffff");
      DFA126_eof = DFA.unpackEncodedString("\u0002\u0002\u0012\uffff");
      DFA126_min = DFA.unpackEncodedStringToUnsignedChars("\u0001/\u0001\u0006\u0012\uffff");
      DFA126_max = DFA.unpackEncodedStringToUnsignedChars("\u0001/\u0001d\u0012\uffff");
      DFA126_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0001\u0001\u0010\uffff");
      DFA126_special = DFA.unpackEncodedString("\u0014\uffff}>");
      numStates = DFA126_transitionS.length;
      DFA126_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA126_transition[i] = DFA.unpackEncodedString(DFA126_transitionS[i]);
      }

      DFA130_transitionS = new String[]{"\u0001\u0011\u0002\uffff\u0001\t\u0015\uffff\u0001\u0012\u0001\u0001\n\uffff\u0001\u0005\u001f\uffff\u0001\u0002\u0001\u0003\u0003\uffff\u0001\u0004\u0001\u0006\u0001\uffff\u0001\u0007\u0001\uffff\u0001\b\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\b\uffff\u0001\u000f\u0001\u0010", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", ""};
      DFA130_eot = DFA.unpackEncodedString("\u0015\uffff");
      DFA130_eof = DFA.unpackEncodedString("\u0015\uffff");
      DFA130_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0006\u0012\u0000\u0002\uffff");
      DFA130_max = DFA.unpackEncodedStringToUnsignedChars("\u0001d\u0012\u0000\u0002\uffff");
      DFA130_accept = DFA.unpackEncodedString("\u0013\uffff\u0001\u0001\u0001\u0002");
      DFA130_special = DFA.unpackEncodedString("\u0001\uffff\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0002\uffff}>");
      numStates = DFA130_transitionS.length;
      DFA130_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA130_transition[i] = DFA.unpackEncodedString(DFA130_transitionS[i]);
      }

      DFA128_transitionS = new String[]{"\u0001\u0002\u0011\uffff\u0001\u0002\u0001\uffff\u0001\u0002\u0010\uffff\u0003\u0002\u0001\u0001\u0002\uffff\r\u0002\u0013\uffff\u0001\u0002\u0002\uffff\u0001\u0002", "\u0001\r\u0001\u0002\u0001\uffff\u0001\r\u000f\uffff\u0001\u0002\u0001\uffff\u0001\u0002\u0003\uffff\u0002\r\n\uffff\u0001\r\u0004\u0002\u0002\uffff\r\u0002\f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\u0002\u0001\r\u0001\uffff\u0001\u0014\u0005\r\b\uffff\u0002\r", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA128_eot = DFA.unpackEncodedString("*\uffff");
      DFA128_eof = DFA.unpackEncodedString("\u0002\u0002(\uffff");
      DFA128_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0007\u0001\u0006(\uffff");
      DFA128_max = DFA.unpackEncodedStringToUnsignedChars("\u0001U\u0001d(\uffff");
      DFA128_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\n\uffff\u0001\u0001\u0006\uffff\u0001\u0001\u0015\uffff");
      DFA128_special = DFA.unpackEncodedString("*\uffff}>");
      numStates = DFA128_transitionS.length;
      DFA128_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA128_transition[i] = DFA.unpackEncodedString(DFA128_transitionS[i]);
      }

      DFA131_transitionS = new String[]{"\u0001\u0001$\uffff\u0001\u0002", "\u0001\u0004\u0002\uffff\u0001\u0004\u0015\uffff\u0002\u0004\n\uffff\u0001\u0004\u001f\uffff\u0002\u0004\u0003\uffff\u0002\u0004\u0001\uffff\u0001\u0004\u0001\u0002\u0006\u0004\b\uffff\u0002\u0004", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA131_eot = DFA.unpackEncodedString("\u0016\uffff");
      DFA131_eof = DFA.unpackEncodedString("\u0016\uffff");
      DFA131_min = DFA.unpackEncodedStringToUnsignedChars("\u0001/\u0001\u0006\u0014\uffff");
      DFA131_max = DFA.unpackEncodedStringToUnsignedChars("\u0001T\u0001d\u0014\uffff");
      DFA131_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u0011\uffff");
      DFA131_special = DFA.unpackEncodedString("\u0016\uffff}>");
      numStates = DFA131_transitionS.length;
      DFA131_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA131_transition[i] = DFA.unpackEncodedString(DFA131_transitionS[i]);
      }

      FOLLOW_NEWLINE_in_single_input72 = new BitSet(new long[]{2L});
      FOLLOW_simple_stmt_in_single_input80 = new BitSet(new long[]{2L});
      FOLLOW_compound_stmt_in_single_input88 = new BitSet(new long[]{128L});
      FOLLOW_NEWLINE_in_single_input90 = new BitSet(new long[]{0L});
      FOLLOW_EOF_in_single_input93 = new BitSet(new long[]{2L});
      FOLLOW_LEADING_WS_in_eval_input111 = new BitSet(new long[]{8802535473856L, 103212062720L});
      FOLLOW_NEWLINE_in_eval_input115 = new BitSet(new long[]{8802535473856L, 103212062720L});
      FOLLOW_testlist_in_eval_input119 = new BitSet(new long[]{128L});
      FOLLOW_NEWLINE_in_eval_input123 = new BitSet(new long[]{128L});
      FOLLOW_EOF_in_eval_input127 = new BitSet(new long[]{2L});
      FOLLOW_NAME_in_dotted_attr145 = new BitSet(new long[]{1026L});
      FOLLOW_DOT_in_dotted_attr156 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_dotted_attr158 = new BitSet(new long[]{1026L});
      FOLLOW_set_in_attr0 = new BitSet(new long[]{2L});
      FOLLOW_AT_in_decorator464 = new BitSet(new long[]{512L});
      FOLLOW_dotted_attr_in_decorator466 = new BitSet(new long[]{8796093022336L});
      FOLLOW_LPAREN_in_decorator474 = new BitSet(new long[]{870819651650112L, 103212062720L});
      FOLLOW_arglist_in_decorator484 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_decorator508 = new BitSet(new long[]{128L});
      FOLLOW_NEWLINE_in_decorator522 = new BitSet(new long[]{2L});
      FOLLOW_decorator_in_decorators540 = new BitSet(new long[]{4398046511106L});
      FOLLOW_decorators_in_funcdef559 = new BitSet(new long[]{262144L});
      FOLLOW_DEF_in_funcdef562 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_funcdef564 = new BitSet(new long[]{8796093022208L});
      FOLLOW_parameters_in_funcdef566 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_funcdef568 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_funcdef570 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_parameters588 = new BitSet(new long[]{870813209199104L});
      FOLLOW_varargslist_in_parameters597 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_parameters621 = new BitSet(new long[]{2L});
      FOLLOW_fpdef_in_defparameter639 = new BitSet(new long[]{70368744177666L});
      FOLLOW_ASSIGN_in_defparameter642 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_defparameter644 = new BitSet(new long[]{2L});
      FOLLOW_defparameter_in_varargslist666 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_varargslist676 = new BitSet(new long[]{8796093022720L});
      FOLLOW_defparameter_in_varargslist678 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_varargslist689 = new BitSet(new long[]{844424930131970L});
      FOLLOW_STAR_in_varargslist702 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist704 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_varargslist707 = new BitSet(new long[]{562949953421312L});
      FOLLOW_DOUBLESTAR_in_varargslist709 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist711 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESTAR_in_varargslist727 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist729 = new BitSet(new long[]{2L});
      FOLLOW_STAR_in_varargslist759 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist761 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_varargslist764 = new BitSet(new long[]{562949953421312L});
      FOLLOW_DOUBLESTAR_in_varargslist766 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist768 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESTAR_in_varargslist778 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist780 = new BitSet(new long[]{2L});
      FOLLOW_NAME_in_fpdef798 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_fpdef816 = new BitSet(new long[]{8796093022720L});
      FOLLOW_fplist_in_fpdef818 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_fpdef820 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_fpdef828 = new BitSet(new long[]{8796093022720L});
      FOLLOW_fplist_in_fpdef830 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_fpdef832 = new BitSet(new long[]{2L});
      FOLLOW_fpdef_in_fplist850 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_fplist866 = new BitSet(new long[]{8796093022720L});
      FOLLOW_fpdef_in_fplist868 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_fplist873 = new BitSet(new long[]{2L});
      FOLLOW_simple_stmt_in_stmt893 = new BitSet(new long[]{2L});
      FOLLOW_compound_stmt_in_stmt901 = new BitSet(new long[]{2L});
      FOLLOW_small_stmt_in_simple_stmt919 = new BitSet(new long[]{1125899906842752L});
      FOLLOW_SEMI_in_simple_stmt929 = new BitSet(new long[]{11242434120256L, 103212062720L});
      FOLLOW_small_stmt_in_simple_stmt931 = new BitSet(new long[]{1125899906842752L});
      FOLLOW_SEMI_in_simple_stmt936 = new BitSet(new long[]{128L});
      FOLLOW_set_in_simple_stmt940 = new BitSet(new long[]{2L});
      FOLLOW_expr_stmt_in_small_stmt959 = new BitSet(new long[]{2L});
      FOLLOW_print_stmt_in_small_stmt974 = new BitSet(new long[]{2L});
      FOLLOW_del_stmt_in_small_stmt989 = new BitSet(new long[]{2L});
      FOLLOW_pass_stmt_in_small_stmt1004 = new BitSet(new long[]{2L});
      FOLLOW_flow_stmt_in_small_stmt1019 = new BitSet(new long[]{2L});
      FOLLOW_import_stmt_in_small_stmt1034 = new BitSet(new long[]{2L});
      FOLLOW_global_stmt_in_small_stmt1049 = new BitSet(new long[]{2L});
      FOLLOW_exec_stmt_in_small_stmt1064 = new BitSet(new long[]{2L});
      FOLLOW_assert_stmt_in_small_stmt1079 = new BitSet(new long[]{2L});
      FOLLOW_testlist_in_expr_stmt1114 = new BitSet(new long[]{9221120237041090560L});
      FOLLOW_augassign_in_expr_stmt1127 = new BitSet(new long[]{2405181849600L});
      FOLLOW_yield_expr_in_expr_stmt1129 = new BitSet(new long[]{2L});
      FOLLOW_augassign_in_expr_stmt1154 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_testlist_in_expr_stmt1156 = new BitSet(new long[]{2L});
      FOLLOW_testlist_in_expr_stmt1194 = new BitSet(new long[]{70368744177666L});
      FOLLOW_ASSIGN_in_expr_stmt1218 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_testlist_in_expr_stmt1220 = new BitSet(new long[]{70368744177666L});
      FOLLOW_ASSIGN_in_expr_stmt1248 = new BitSet(new long[]{2405181849600L});
      FOLLOW_yield_expr_in_expr_stmt1250 = new BitSet(new long[]{70368744177666L});
      FOLLOW_testlist_in_expr_stmt1282 = new BitSet(new long[]{2L});
      FOLLOW_set_in_augassign0 = new BitSet(new long[]{2L});
      FOLLOW_PRINT_in_print_stmt1414 = new BitSet(new long[]{-9223363234319302078L, 103212062720L});
      FOLLOW_printlist_in_print_stmt1423 = new BitSet(new long[]{2L});
      FOLLOW_RIGHTSHIFT_in_print_stmt1433 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_printlist_in_print_stmt1435 = new BitSet(new long[]{2L});
      FOLLOW_test_in_printlist1486 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_printlist1497 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_printlist1499 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_printlist1513 = new BitSet(new long[]{2L});
      FOLLOW_test_in_printlist1523 = new BitSet(new long[]{2L});
      FOLLOW_DELETE_in_del_stmt1541 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_exprlist_in_del_stmt1543 = new BitSet(new long[]{2L});
      FOLLOW_PASS_in_pass_stmt1561 = new BitSet(new long[]{2L});
      FOLLOW_break_stmt_in_flow_stmt1579 = new BitSet(new long[]{2L});
      FOLLOW_continue_stmt_in_flow_stmt1587 = new BitSet(new long[]{2L});
      FOLLOW_return_stmt_in_flow_stmt1595 = new BitSet(new long[]{2L});
      FOLLOW_raise_stmt_in_flow_stmt1603 = new BitSet(new long[]{2L});
      FOLLOW_yield_stmt_in_flow_stmt1611 = new BitSet(new long[]{2L});
      FOLLOW_BREAK_in_break_stmt1629 = new BitSet(new long[]{2L});
      FOLLOW_CONTINUE_in_continue_stmt1647 = new BitSet(new long[]{2L});
      FOLLOW_RETURN_in_return_stmt1665 = new BitSet(new long[]{8802535473730L, 103212062720L});
      FOLLOW_testlist_in_return_stmt1674 = new BitSet(new long[]{2L});
      FOLLOW_yield_expr_in_yield_stmt1708 = new BitSet(new long[]{2L});
      FOLLOW_RAISE_in_raise_stmt1726 = new BitSet(new long[]{8802535473730L, 103212062720L});
      FOLLOW_test_in_raise_stmt1729 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_raise_stmt1732 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_raise_stmt1734 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_raise_stmt1745 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_raise_stmt1747 = new BitSet(new long[]{2L});
      FOLLOW_import_name_in_import_stmt1771 = new BitSet(new long[]{2L});
      FOLLOW_import_from_in_import_stmt1779 = new BitSet(new long[]{2L});
      FOLLOW_IMPORT_in_import_name1797 = new BitSet(new long[]{512L});
      FOLLOW_dotted_as_names_in_import_name1799 = new BitSet(new long[]{2L});
      FOLLOW_FROM_in_import_from1818 = new BitSet(new long[]{1536L});
      FOLLOW_DOT_in_import_from1821 = new BitSet(new long[]{1536L});
      FOLLOW_dotted_name_in_import_from1824 = new BitSet(new long[]{268435456L});
      FOLLOW_DOT_in_import_from1828 = new BitSet(new long[]{268436480L});
      FOLLOW_IMPORT_in_import_from1832 = new BitSet(new long[]{290271069733376L});
      FOLLOW_STAR_in_import_from1843 = new BitSet(new long[]{2L});
      FOLLOW_import_as_names_in_import_from1855 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_import_from1867 = new BitSet(new long[]{512L});
      FOLLOW_import_as_names_in_import_from1869 = new BitSet(new long[]{158329674399744L});
      FOLLOW_COMMA_in_import_from1871 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_import_from1874 = new BitSet(new long[]{2L});
      FOLLOW_import_as_name_in_import_as_names1902 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_import_as_names1905 = new BitSet(new long[]{512L});
      FOLLOW_import_as_name_in_import_as_names1907 = new BitSet(new long[]{140737488355330L});
      FOLLOW_NAME_in_import_as_name1927 = new BitSet(new long[]{8194L});
      FOLLOW_AS_in_import_as_name1930 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_import_as_name1932 = new BitSet(new long[]{2L});
      FOLLOW_dotted_name_in_dotted_as_name1953 = new BitSet(new long[]{8194L});
      FOLLOW_AS_in_dotted_as_name1956 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_dotted_as_name1958 = new BitSet(new long[]{2L});
      FOLLOW_dotted_as_name_in_dotted_as_names1978 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_dotted_as_names1981 = new BitSet(new long[]{512L});
      FOLLOW_dotted_as_name_in_dotted_as_names1983 = new BitSet(new long[]{140737488355330L});
      FOLLOW_NAME_in_dotted_name2003 = new BitSet(new long[]{1026L});
      FOLLOW_DOT_in_dotted_name2006 = new BitSet(new long[]{4398046509568L});
      FOLLOW_attr_in_dotted_name2008 = new BitSet(new long[]{1026L});
      FOLLOW_GLOBAL_in_global_stmt2028 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_global_stmt2030 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_global_stmt2033 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_global_stmt2035 = new BitSet(new long[]{140737488355330L});
      FOLLOW_EXEC_in_exec_stmt2055 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_expr_in_exec_stmt2057 = new BitSet(new long[]{536870914L});
      FOLLOW_IN_in_exec_stmt2060 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_exec_stmt2062 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_exec_stmt2065 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_exec_stmt2067 = new BitSet(new long[]{2L});
      FOLLOW_ASSERT_in_assert_stmt2089 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_assert_stmt2091 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_assert_stmt2094 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_assert_stmt2096 = new BitSet(new long[]{2L});
      FOLLOW_if_stmt_in_compound_stmt2116 = new BitSet(new long[]{2L});
      FOLLOW_while_stmt_in_compound_stmt2124 = new BitSet(new long[]{2L});
      FOLLOW_for_stmt_in_compound_stmt2132 = new BitSet(new long[]{2L});
      FOLLOW_try_stmt_in_compound_stmt2140 = new BitSet(new long[]{2L});
      FOLLOW_with_stmt_in_compound_stmt2148 = new BitSet(new long[]{2L});
      FOLLOW_funcdef_in_compound_stmt2165 = new BitSet(new long[]{2L});
      FOLLOW_classdef_in_compound_stmt2182 = new BitSet(new long[]{2L});
      FOLLOW_decorators_in_compound_stmt2190 = new BitSet(new long[]{2L});
      FOLLOW_IF_in_if_stmt2208 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_if_stmt2210 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_if_stmt2212 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_if_stmt2214 = new BitSet(new long[]{17180917762L});
      FOLLOW_elif_clause_in_if_stmt2216 = new BitSet(new long[]{2L});
      FOLLOW_else_clause_in_elif_clause2235 = new BitSet(new long[]{2L});
      FOLLOW_ELIF_in_elif_clause2243 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_elif_clause2245 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_elif_clause2247 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_elif_clause2249 = new BitSet(new long[]{17180917762L});
      FOLLOW_elif_clause_in_elif_clause2260 = new BitSet(new long[]{2L});
      FOLLOW_ORELSE_in_else_clause2298 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_else_clause2300 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_else_clause2302 = new BitSet(new long[]{2L});
      FOLLOW_WHILE_in_while_stmt2320 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_while_stmt2322 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_while_stmt2324 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_while_stmt2326 = new BitSet(new long[]{17179869186L});
      FOLLOW_ORELSE_in_while_stmt2329 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_while_stmt2331 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_while_stmt2333 = new BitSet(new long[]{2L});
      FOLLOW_FOR_in_for_stmt2353 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_exprlist_in_for_stmt2355 = new BitSet(new long[]{536870912L});
      FOLLOW_IN_in_for_stmt2357 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_testlist_in_for_stmt2359 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_for_stmt2361 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_for_stmt2363 = new BitSet(new long[]{17179869186L});
      FOLLOW_ORELSE_in_for_stmt2374 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_for_stmt2376 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_for_stmt2378 = new BitSet(new long[]{2L});
      FOLLOW_TRY_in_try_stmt2402 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_try_stmt2404 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_try_stmt2406 = new BitSet(new long[]{10485762L});
      FOLLOW_except_clause_in_try_stmt2416 = new BitSet(new long[]{17190354946L});
      FOLLOW_ORELSE_in_try_stmt2420 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_try_stmt2422 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_try_stmt2424 = new BitSet(new long[]{8388610L});
      FOLLOW_FINALLY_in_try_stmt2429 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_try_stmt2431 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_try_stmt2433 = new BitSet(new long[]{2L});
      FOLLOW_FINALLY_in_try_stmt2445 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_try_stmt2447 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_try_stmt2449 = new BitSet(new long[]{2L});
      FOLLOW_WITH_in_with_stmt2478 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_with_item_in_with_stmt2480 = new BitSet(new long[]{175921860444160L});
      FOLLOW_COMMA_in_with_stmt2490 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_with_item_in_with_stmt2492 = new BitSet(new long[]{175921860444160L});
      FOLLOW_COLON_in_with_stmt2496 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_with_stmt2498 = new BitSet(new long[]{2L});
      FOLLOW_test_in_with_item2516 = new BitSet(new long[]{8194L});
      FOLLOW_AS_in_with_item2519 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_expr_in_with_item2521 = new BitSet(new long[]{2L});
      FOLLOW_EXCEPT_in_except_clause2541 = new BitSet(new long[]{43986907562560L, 103212062720L});
      FOLLOW_test_in_except_clause2544 = new BitSet(new long[]{175921860452352L});
      FOLLOW_set_in_except_clause2547 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_except_clause2555 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_except_clause2561 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_except_clause2563 = new BitSet(new long[]{2L});
      FOLLOW_simple_stmt_in_suite2581 = new BitSet(new long[]{2L});
      FOLLOW_NEWLINE_in_suite2589 = new BitSet(new long[]{48L});
      FOLLOW_EOF_in_suite2592 = new BitSet(new long[]{2L});
      FOLLOW_DEDENT_in_suite2611 = new BitSet(new long[]{32L});
      FOLLOW_EOF_in_suite2615 = new BitSet(new long[]{2L});
      FOLLOW_INDENT_in_suite2633 = new BitSet(new long[]{17564794079808L, 103212062720L});
      FOLLOW_stmt_in_suite2636 = new BitSet(new long[]{17564794079840L, 103212062720L});
      FOLLOW_set_in_suite2640 = new BitSet(new long[]{2L});
      FOLLOW_or_test_in_test2741 = new BitSet(new long[]{134217730L});
      FOLLOW_IF_in_test2761 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_or_test_in_test2763 = new BitSet(new long[]{17179869184L});
      FOLLOW_ORELSE_in_test2765 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_test2767 = new BitSet(new long[]{2L});
      FOLLOW_lambdef_in_test2791 = new BitSet(new long[]{2L});
      FOLLOW_and_test_in_or_test2809 = new BitSet(new long[]{8589934594L});
      FOLLOW_OR_in_or_test2822 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_and_test_in_or_test2824 = new BitSet(new long[]{8589934594L});
      FOLLOW_not_test_in_and_test2875 = new BitSet(new long[]{4098L});
      FOLLOW_AND_in_and_test2888 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_not_test_in_and_test2890 = new BitSet(new long[]{4098L});
      FOLLOW_NOT_in_not_test2941 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_not_test_in_not_test2943 = new BitSet(new long[]{2L});
      FOLLOW_comparison_in_not_test2951 = new BitSet(new long[]{2L});
      FOLLOW_expr_in_comparison2969 = new BitSet(new long[]{5905580034L, 127L});
      FOLLOW_comp_op_in_comparison2982 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_expr_in_comparison2984 = new BitSet(new long[]{5905580034L, 127L});
      FOLLOW_LESS_in_comp_op3032 = new BitSet(new long[]{2L});
      FOLLOW_GREATER_in_comp_op3040 = new BitSet(new long[]{2L});
      FOLLOW_EQUAL_in_comp_op3048 = new BitSet(new long[]{2L});
      FOLLOW_GREATEREQUAL_in_comp_op3056 = new BitSet(new long[]{2L});
      FOLLOW_LESSEQUAL_in_comp_op3064 = new BitSet(new long[]{2L});
      FOLLOW_ALT_NOTEQUAL_in_comp_op3072 = new BitSet(new long[]{2L});
      FOLLOW_NOTEQUAL_in_comp_op3080 = new BitSet(new long[]{2L});
      FOLLOW_IN_in_comp_op3088 = new BitSet(new long[]{2L});
      FOLLOW_NOT_in_comp_op3096 = new BitSet(new long[]{536870912L});
      FOLLOW_IN_in_comp_op3098 = new BitSet(new long[]{2L});
      FOLLOW_IS_in_comp_op3106 = new BitSet(new long[]{2L});
      FOLLOW_IS_in_comp_op3114 = new BitSet(new long[]{4294967296L});
      FOLLOW_NOT_in_comp_op3116 = new BitSet(new long[]{2L});
      FOLLOW_xor_expr_in_expr3134 = new BitSet(new long[]{2L, 128L});
      FOLLOW_VBAR_in_expr3147 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_xor_expr_in_expr3149 = new BitSet(new long[]{2L, 128L});
      FOLLOW_and_expr_in_xor_expr3200 = new BitSet(new long[]{2L, 256L});
      FOLLOW_CIRCUMFLEX_in_xor_expr3213 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_and_expr_in_xor_expr3215 = new BitSet(new long[]{2L, 256L});
      FOLLOW_shift_expr_in_and_expr3266 = new BitSet(new long[]{2L, 512L});
      FOLLOW_AMPER_in_and_expr3279 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_shift_expr_in_and_expr3281 = new BitSet(new long[]{2L, 512L});
      FOLLOW_arith_expr_in_shift_expr3332 = new BitSet(new long[]{-9223372036854775806L, 1024L});
      FOLLOW_shift_op_in_shift_expr3346 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_arith_expr_in_shift_expr3348 = new BitSet(new long[]{-9223372036854775806L, 1024L});
      FOLLOW_set_in_shift_op0 = new BitSet(new long[]{2L});
      FOLLOW_term_in_arith_expr3424 = new BitSet(new long[]{2L, 6144L});
      FOLLOW_arith_op_in_arith_expr3437 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_term_in_arith_expr3439 = new BitSet(new long[]{2L, 6144L});
      FOLLOW_set_in_arith_op0 = new BitSet(new long[]{2L});
      FOLLOW_factor_in_term3515 = new BitSet(new long[]{281474976710658L, 57344L});
      FOLLOW_term_op_in_term3528 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_factor_in_term3530 = new BitSet(new long[]{281474976710658L, 57344L});
      FOLLOW_set_in_term_op0 = new BitSet(new long[]{2L});
      FOLLOW_PLUS_in_factor3618 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_factor_in_factor3620 = new BitSet(new long[]{2L});
      FOLLOW_MINUS_in_factor3628 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_factor_in_factor3630 = new BitSet(new long[]{2L});
      FOLLOW_TILDE_in_factor3638 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_factor_in_factor3640 = new BitSet(new long[]{2L});
      FOLLOW_power_in_factor3648 = new BitSet(new long[]{2L});
      FOLLOW_TRAILBACKSLASH_in_factor3656 = new BitSet(new long[]{2L});
      FOLLOW_atom_in_power3674 = new BitSet(new long[]{571746046444546L, 131072L});
      FOLLOW_trailer_in_power3677 = new BitSet(new long[]{571746046444546L, 131072L});
      FOLLOW_DOUBLESTAR_in_power3689 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_factor_in_power3691 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_atom3715 = new BitSet(new long[]{28799903367744L, 103212062720L});
      FOLLOW_yield_expr_in_atom3725 = new BitSet(new long[]{17592186044416L});
      FOLLOW_testlist_gexp_in_atom3735 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_atom3759 = new BitSet(new long[]{2L});
      FOLLOW_LBRACK_in_atom3767 = new BitSet(new long[]{8802535473728L, 103212324864L});
      FOLLOW_listmaker_in_atom3776 = new BitSet(new long[]{0L, 262144L});
      FOLLOW_RBRACK_in_atom3800 = new BitSet(new long[]{2L});
      FOLLOW_LCURLY_in_atom3808 = new BitSet(new long[]{8802535473728L, 103213111296L});
      FOLLOW_dictorsetmaker_in_atom3818 = new BitSet(new long[]{0L, 1048576L});
      FOLLOW_RCURLY_in_atom3845 = new BitSet(new long[]{2L});
      FOLLOW_BACKQUOTE_in_atom3854 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_testlist_in_atom3856 = new BitSet(new long[]{0L, 2097152L});
      FOLLOW_BACKQUOTE_in_atom3858 = new BitSet(new long[]{2L});
      FOLLOW_NAME_in_atom3867 = new BitSet(new long[]{2L});
      FOLLOW_INT_in_atom3876 = new BitSet(new long[]{2L});
      FOLLOW_LONGINT_in_atom3885 = new BitSet(new long[]{2L});
      FOLLOW_FLOAT_in_atom3894 = new BitSet(new long[]{2L});
      FOLLOW_COMPLEX_in_atom3903 = new BitSet(new long[]{2L});
      FOLLOW_STRING_in_atom3913 = new BitSet(new long[]{2L, 67108864L});
      FOLLOW_TRISTRINGPART_in_atom3924 = new BitSet(new long[]{2L});
      FOLLOW_STRINGPART_in_atom3933 = new BitSet(new long[]{64L});
      FOLLOW_TRAILBACKSLASH_in_atom3935 = new BitSet(new long[]{2L});
      FOLLOW_test_in_listmaker3954 = new BitSet(new long[]{140737521909762L});
      FOLLOW_list_for_in_listmaker3965 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_listmaker3985 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_listmaker3987 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_listmaker4002 = new BitSet(new long[]{2L});
      FOLLOW_test_in_testlist_gexp4022 = new BitSet(new long[]{140737521909762L});
      FOLLOW_COMMA_in_testlist_gexp4044 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_testlist_gexp4046 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_testlist_gexp4051 = new BitSet(new long[]{2L});
      FOLLOW_comp_for_in_testlist_gexp4078 = new BitSet(new long[]{2L});
      FOLLOW_LAMBDA_in_lambdef4118 = new BitSet(new long[]{888405395243520L});
      FOLLOW_varargslist_in_lambdef4121 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_lambdef4125 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_lambdef4127 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_trailer4145 = new BitSet(new long[]{870819651650112L, 103212062720L});
      FOLLOW_arglist_in_trailer4156 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_trailer4184 = new BitSet(new long[]{2L});
      FOLLOW_LBRACK_in_trailer4192 = new BitSet(new long[]{43986907563584L, 103212062720L});
      FOLLOW_subscriptlist_in_trailer4194 = new BitSet(new long[]{0L, 262144L});
      FOLLOW_RBRACK_in_trailer4196 = new BitSet(new long[]{2L});
      FOLLOW_DOT_in_trailer4204 = new BitSet(new long[]{4398046509568L});
      FOLLOW_attr_in_trailer4206 = new BitSet(new long[]{2L});
      FOLLOW_subscript_in_subscriptlist4224 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_subscriptlist4234 = new BitSet(new long[]{43986907563584L, 103212062720L});
      FOLLOW_subscript_in_subscriptlist4236 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_subscriptlist4241 = new BitSet(new long[]{2L});
      FOLLOW_DOT_in_subscript4261 = new BitSet(new long[]{1024L});
      FOLLOW_DOT_in_subscript4263 = new BitSet(new long[]{1024L});
      FOLLOW_DOT_in_subscript4265 = new BitSet(new long[]{2L});
      FOLLOW_test_in_subscript4284 = new BitSet(new long[]{35184372088834L});
      FOLLOW_COLON_in_subscript4287 = new BitSet(new long[]{43986907562562L, 103212062720L});
      FOLLOW_test_in_subscript4290 = new BitSet(new long[]{35184372088834L});
      FOLLOW_sliceop_in_subscript4295 = new BitSet(new long[]{2L});
      FOLLOW_COLON_in_subscript4316 = new BitSet(new long[]{43986907562562L, 103212062720L});
      FOLLOW_test_in_subscript4319 = new BitSet(new long[]{35184372088834L});
      FOLLOW_sliceop_in_subscript4324 = new BitSet(new long[]{2L});
      FOLLOW_test_in_subscript4334 = new BitSet(new long[]{2L});
      FOLLOW_COLON_in_sliceop4352 = new BitSet(new long[]{8802535473730L, 103212062720L});
      FOLLOW_test_in_sliceop4360 = new BitSet(new long[]{2L});
      FOLLOW_expr_in_exprlist4400 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_exprlist4411 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_expr_in_exprlist4413 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_exprlist4418 = new BitSet(new long[]{2L});
      FOLLOW_expr_in_exprlist4428 = new BitSet(new long[]{2L});
      FOLLOW_expr_in_del_list4447 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_del_list4458 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_expr_in_del_list4460 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_del_list4465 = new BitSet(new long[]{2L});
      FOLLOW_test_in_testlist4496 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_testlist4507 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_testlist4509 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_testlist4514 = new BitSet(new long[]{2L});
      FOLLOW_test_in_testlist4524 = new BitSet(new long[]{2L});
      FOLLOW_test_in_dictorsetmaker4543 = new BitSet(new long[]{175921893998594L});
      FOLLOW_COLON_in_dictorsetmaker4570 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_dictorsetmaker4572 = new BitSet(new long[]{140737521909760L});
      FOLLOW_comp_for_in_dictorsetmaker4591 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_dictorsetmaker4618 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_dictorsetmaker4620 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_dictorsetmaker4622 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_dictorsetmaker4624 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_dictorsetmaker4660 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_dictorsetmaker4662 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_dictorsetmaker4695 = new BitSet(new long[]{2L});
      FOLLOW_comp_for_in_dictorsetmaker4710 = new BitSet(new long[]{2L});
      FOLLOW_decorators_in_classdef4739 = new BitSet(new long[]{65536L});
      FOLLOW_CLASS_in_classdef4742 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_classdef4744 = new BitSet(new long[]{43980465111040L});
      FOLLOW_LPAREN_in_classdef4747 = new BitSet(new long[]{26394721518144L, 103212062720L});
      FOLLOW_testlist_in_classdef4749 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_classdef4752 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_classdef4756 = new BitSet(new long[]{11242434120384L, 103212062720L});
      FOLLOW_suite_in_classdef4758 = new BitSet(new long[]{2L});
      FOLLOW_argument_in_arglist4778 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist4781 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_argument_in_arglist4783 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist4798 = new BitSet(new long[]{844424930131970L});
      FOLLOW_STAR_in_arglist4816 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_arglist4818 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist4821 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_argument_in_arglist4823 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist4828 = new BitSet(new long[]{562949953421312L});
      FOLLOW_DOUBLESTAR_in_arglist4830 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_arglist4832 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESTAR_in_arglist4852 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_arglist4854 = new BitSet(new long[]{2L});
      FOLLOW_STAR_in_arglist4892 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_arglist4894 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist4897 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_argument_in_arglist4899 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist4904 = new BitSet(new long[]{562949953421312L});
      FOLLOW_DOUBLESTAR_in_arglist4906 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_arglist4908 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESTAR_in_arglist4918 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_arglist4920 = new BitSet(new long[]{2L});
      FOLLOW_test_in_argument4938 = new BitSet(new long[]{211106266087424L});
      FOLLOW_ASSIGN_in_argument4950 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_argument4952 = new BitSet(new long[]{2L});
      FOLLOW_comp_for_in_argument4965 = new BitSet(new long[]{2L});
      FOLLOW_list_for_in_list_iter5003 = new BitSet(new long[]{2L});
      FOLLOW_list_if_in_list_iter5011 = new BitSet(new long[]{2L});
      FOLLOW_FOR_in_list_for5029 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_exprlist_in_list_for5031 = new BitSet(new long[]{536870912L});
      FOLLOW_IN_in_list_for5033 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_testlist_in_list_for5035 = new BitSet(new long[]{167772162L});
      FOLLOW_list_iter_in_list_for5038 = new BitSet(new long[]{2L});
      FOLLOW_IF_in_list_if5058 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_list_if5060 = new BitSet(new long[]{167772162L});
      FOLLOW_list_iter_in_list_if5063 = new BitSet(new long[]{2L});
      FOLLOW_comp_for_in_comp_iter5083 = new BitSet(new long[]{2L});
      FOLLOW_comp_if_in_comp_iter5091 = new BitSet(new long[]{2L});
      FOLLOW_FOR_in_comp_for5109 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_exprlist_in_comp_for5111 = new BitSet(new long[]{536870912L});
      FOLLOW_IN_in_comp_for5113 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_or_test_in_comp_for5115 = new BitSet(new long[]{140737656127490L});
      FOLLOW_comp_iter_in_comp_for5117 = new BitSet(new long[]{2L});
      FOLLOW_IF_in_comp_if5136 = new BitSet(new long[]{8802535473728L, 103212062720L});
      FOLLOW_test_in_comp_if5138 = new BitSet(new long[]{140737656127490L});
      FOLLOW_comp_iter_in_comp_if5140 = new BitSet(new long[]{2L});
      FOLLOW_YIELD_in_yield_expr5159 = new BitSet(new long[]{8802535473730L, 103212062720L});
      FOLLOW_testlist_in_yield_expr5161 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_synpred1_PythonPartial807 = new BitSet(new long[]{8796093022720L});
      FOLLOW_fpdef_in_synpred1_PythonPartial809 = new BitSet(new long[]{140737488355328L});
      FOLLOW_COMMA_in_synpred1_PythonPartial811 = new BitSet(new long[]{2L});
      FOLLOW_testlist_in_synpred2_PythonPartial1107 = new BitSet(new long[]{9221120237041090560L});
      FOLLOW_augassign_in_synpred2_PythonPartial1109 = new BitSet(new long[]{2L});
      FOLLOW_testlist_in_synpred3_PythonPartial1187 = new BitSet(new long[]{70368744177664L});
      FOLLOW_ASSIGN_in_synpred3_PythonPartial1189 = new BitSet(new long[]{2L});
      FOLLOW_test_in_synpred4_PythonPartial1472 = new BitSet(new long[]{140737488355328L});
      FOLLOW_COMMA_in_synpred4_PythonPartial1474 = new BitSet(new long[]{2L});
      FOLLOW_decorators_in_synpred5_PythonPartial2157 = new BitSet(new long[]{262144L});
      FOLLOW_DEF_in_synpred5_PythonPartial2160 = new BitSet(new long[]{2L});
      FOLLOW_decorators_in_synpred6_PythonPartial2174 = new BitSet(new long[]{65536L});
      FOLLOW_CLASS_in_synpred6_PythonPartial2177 = new BitSet(new long[]{2L});
      FOLLOW_IF_in_synpred7_PythonPartial2752 = new BitSet(new long[]{8800387990080L, 103212062720L});
      FOLLOW_or_test_in_synpred7_PythonPartial2754 = new BitSet(new long[]{17179869184L});
      FOLLOW_ORELSE_in_synpred7_PythonPartial2756 = new BitSet(new long[]{2L});
      FOLLOW_test_in_synpred8_PythonPartial4274 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_synpred8_PythonPartial4276 = new BitSet(new long[]{2L});
      FOLLOW_COLON_in_synpred9_PythonPartial4308 = new BitSet(new long[]{2L});
      FOLLOW_expr_in_synpred10_PythonPartial4393 = new BitSet(new long[]{140737488355328L});
      FOLLOW_COMMA_in_synpred10_PythonPartial4395 = new BitSet(new long[]{2L});
      FOLLOW_test_in_synpred11_PythonPartial4486 = new BitSet(new long[]{140737488355328L});
      FOLLOW_COMMA_in_synpred11_PythonPartial4488 = new BitSet(new long[]{2L});
   }

   class DFA131 extends DFA {
      public DFA131(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 131;
         this.eot = PythonPartialParser.DFA131_eot;
         this.eof = PythonPartialParser.DFA131_eof;
         this.min = PythonPartialParser.DFA131_min;
         this.max = PythonPartialParser.DFA131_max;
         this.accept = PythonPartialParser.DFA131_accept;
         this.special = PythonPartialParser.DFA131_special;
         this.transition = PythonPartialParser.DFA131_transition;
      }

      public String getDescription() {
         return "()* loopback of 826:18: ( options {k=2; } : COMMA test COLON test )*";
      }
   }

   class DFA128 extends DFA {
      public DFA128(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 128;
         this.eot = PythonPartialParser.DFA128_eot;
         this.eof = PythonPartialParser.DFA128_eof;
         this.min = PythonPartialParser.DFA128_min;
         this.max = PythonPartialParser.DFA128_max;
         this.accept = PythonPartialParser.DFA128_accept;
         this.special = PythonPartialParser.DFA128_special;
         this.transition = PythonPartialParser.DFA128_transition;
      }

      public String getDescription() {
         return "()* loopback of 815:12: ( options {k=2; } : COMMA test )*";
      }
   }

   class DFA130 extends DFA {
      public DFA130(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 130;
         this.eot = PythonPartialParser.DFA130_eot;
         this.eof = PythonPartialParser.DFA130_eof;
         this.min = PythonPartialParser.DFA130_min;
         this.max = PythonPartialParser.DFA130_max;
         this.accept = PythonPartialParser.DFA130_accept;
         this.special = PythonPartialParser.DFA130_special;
         this.transition = PythonPartialParser.DFA130_transition;
      }

      public String getDescription() {
         return "813:1: testlist : ( ( test COMMA )=> test ( options {k=2; } : COMMA test )* ( COMMA )? | test );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean sx;
         switch (s) {
            case 0:
               int LA130_1 = input.LA(1);
               int index130_1 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_1);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA130_2 = input.LA(1);
               int index130_2 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA130_3 = input.LA(1);
               int index130_3 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_3);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA130_4 = input.LA(1);
               int index130_4 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_4);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA130_5 = input.LA(1);
               int index130_5 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_5);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA130_6 = input.LA(1);
               int index130_6 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_6);
               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA130_7 = input.LA(1);
               int index130_7 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_7);
               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA130_8 = input.LA(1);
               int index130_8 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_8);
               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA130_9 = input.LA(1);
               int index130_9 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_9);
               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA130_10 = input.LA(1);
               int index130_10 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_10);
               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA130_11 = input.LA(1);
               int index130_11 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_11);
               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA130_12 = input.LA(1);
               int index130_12 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_12);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA130_13 = input.LA(1);
               int index130_13 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_13);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA130_14 = input.LA(1);
               int index130_14 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_14);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA130_15 = input.LA(1);
               int index130_15 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_15);
               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA130_16 = input.LA(1);
               int index130_16 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_16);
               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA130_17 = input.LA(1);
               int index130_17 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_17);
               if (s >= 0) {
                  return s;
               }
               break;
            case 17:
               int LA130_18 = input.LA(1);
               int index130_18 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred11_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index130_18);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonPartialParser.this.state.backtracking > 0) {
            PythonPartialParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 130, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA126 extends DFA {
      public DFA126(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 126;
         this.eot = PythonPartialParser.DFA126_eot;
         this.eof = PythonPartialParser.DFA126_eof;
         this.min = PythonPartialParser.DFA126_min;
         this.max = PythonPartialParser.DFA126_max;
         this.accept = PythonPartialParser.DFA126_accept;
         this.special = PythonPartialParser.DFA126_special;
         this.transition = PythonPartialParser.DFA126_transition;
      }

      public String getDescription() {
         return "()* loopback of 809:12: ( options {k=2; } : COMMA expr )*";
      }
   }

   class DFA123 extends DFA {
      public DFA123(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 123;
         this.eot = PythonPartialParser.DFA123_eot;
         this.eof = PythonPartialParser.DFA123_eof;
         this.min = PythonPartialParser.DFA123_min;
         this.max = PythonPartialParser.DFA123_max;
         this.accept = PythonPartialParser.DFA123_accept;
         this.special = PythonPartialParser.DFA123_special;
         this.transition = PythonPartialParser.DFA123_transition;
      }

      public String getDescription() {
         return "()* loopback of 802:28: ( options {k=2; } : COMMA expr )*";
      }
   }

   class DFA125 extends DFA {
      public DFA125(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 125;
         this.eot = PythonPartialParser.DFA125_eot;
         this.eof = PythonPartialParser.DFA125_eof;
         this.min = PythonPartialParser.DFA125_min;
         this.max = PythonPartialParser.DFA125_max;
         this.accept = PythonPartialParser.DFA125_accept;
         this.special = PythonPartialParser.DFA125_special;
         this.transition = PythonPartialParser.DFA125_transition;
      }

      public String getDescription() {
         return "801:1: exprlist : ( ( expr COMMA )=> expr ( options {k=2; } : COMMA expr )* ( COMMA )? | expr );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean sx;
         switch (s) {
            case 0:
               int LA125_1 = input.LA(1);
               int index125_1 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_1);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA125_2 = input.LA(1);
               int index125_2 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA125_3 = input.LA(1);
               int index125_3 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_3);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA125_4 = input.LA(1);
               int index125_4 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_4);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA125_5 = input.LA(1);
               int index125_5 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_5);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA125_6 = input.LA(1);
               int index125_6 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_6);
               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA125_7 = input.LA(1);
               int index125_7 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_7);
               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA125_8 = input.LA(1);
               int index125_8 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_8);
               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA125_9 = input.LA(1);
               int index125_9 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_9);
               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA125_10 = input.LA(1);
               int index125_10 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_10);
               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA125_11 = input.LA(1);
               int index125_11 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_11);
               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA125_12 = input.LA(1);
               int index125_12 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_12);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA125_13 = input.LA(1);
               int index125_13 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_13);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA125_14 = input.LA(1);
               int index125_14 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_14);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA125_15 = input.LA(1);
               int index125_15 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_15);
               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA125_16 = input.LA(1);
               int index125_16 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred10_PythonPartial()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index125_16);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonPartialParser.this.state.backtracking > 0) {
            PythonPartialParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 125, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA121 extends DFA {
      public DFA121(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 121;
         this.eot = PythonPartialParser.DFA121_eot;
         this.eof = PythonPartialParser.DFA121_eof;
         this.min = PythonPartialParser.DFA121_min;
         this.max = PythonPartialParser.DFA121_max;
         this.accept = PythonPartialParser.DFA121_accept;
         this.special = PythonPartialParser.DFA121_special;
         this.transition = PythonPartialParser.DFA121_transition;
      }

      public String getDescription() {
         return "783:1: subscript : ( DOT DOT DOT | ( test COLON )=> test ( COLON ( test )? ( sliceop )? )? | ( COLON )=> COLON ( test )? ( sliceop )? | test );";
      }

      public int specialStateTransition(int sx, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean s;
         switch (sx) {
            case 0:
               int LA121_0 = input.LA(1);
               int index121_0 = input.index();
               input.rewind();
               sx = -1;
               if (LA121_0 == 10) {
                  sx = 1;
               } else if (LA121_0 == 32) {
                  sx = 2;
               } else if (LA121_0 == 75) {
                  sx = 3;
               } else if (LA121_0 == 76) {
                  sx = 4;
               } else if (LA121_0 == 80) {
                  sx = 5;
               } else if (LA121_0 == 43) {
                  sx = 6;
               } else if (LA121_0 == 81) {
                  sx = 7;
               } else if (LA121_0 == 83) {
                  sx = 8;
               } else if (LA121_0 == 85) {
                  sx = 9;
               } else if (LA121_0 == 9) {
                  sx = 10;
               } else if (LA121_0 == 86) {
                  sx = 11;
               } else if (LA121_0 == 87) {
                  sx = 12;
               } else if (LA121_0 == 88) {
                  sx = 13;
               } else if (LA121_0 == 89) {
                  sx = 14;
               } else if (LA121_0 == 90) {
                  sx = 15;
               } else if (LA121_0 == 99) {
                  sx = 16;
               } else if (LA121_0 == 100) {
                  sx = 17;
               } else if (LA121_0 == 6) {
                  sx = 18;
               } else if (LA121_0 == 31) {
                  sx = 19;
               } else if (LA121_0 == 45 && PythonPartialParser.this.synpred9_PythonPartial()) {
                  sx = 20;
               }

               input.seek(index121_0);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 1:
               int LA121_2 = input.LA(1);
               int index121_2 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_2);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 2:
               int LA121_3 = input.LA(1);
               int index121_3 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_3);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 3:
               int LA121_4 = input.LA(1);
               int index121_4 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_4);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 4:
               int LA121_5 = input.LA(1);
               int index121_5 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_5);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 5:
               int LA121_6 = input.LA(1);
               int index121_6 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_6);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 6:
               int LA121_7 = input.LA(1);
               int index121_7 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_7);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 7:
               int LA121_8 = input.LA(1);
               int index121_8 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_8);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 8:
               int LA121_9 = input.LA(1);
               int index121_9 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_9);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 9:
               int LA121_10 = input.LA(1);
               int index121_10 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_10);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 10:
               int LA121_11 = input.LA(1);
               int index121_11 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_11);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 11:
               int LA121_12 = input.LA(1);
               int index121_12 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_12);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 12:
               int LA121_13 = input.LA(1);
               int index121_13 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_13);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 13:
               int LA121_14 = input.LA(1);
               int index121_14 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_14);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 14:
               int LA121_15 = input.LA(1);
               int index121_15 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_15);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 15:
               int LA121_16 = input.LA(1);
               int index121_16 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_16);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 16:
               int LA121_17 = input.LA(1);
               int index121_17 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_17);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 17:
               int LA121_18 = input.LA(1);
               int index121_18 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_18);
               if (sx >= 0) {
                  return sx;
               }
               break;
            case 18:
               int LA121_19 = input.LA(1);
               int index121_19 = input.index();
               input.rewind();
               s = true;
               if (PythonPartialParser.this.synpred8_PythonPartial()) {
                  sx = 21;
               } else {
                  sx = 22;
               }

               input.seek(index121_19);
               if (sx >= 0) {
                  return sx;
               }
         }

         if (PythonPartialParser.this.state.backtracking > 0) {
            PythonPartialParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 121, sx, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA108 extends DFA {
      public DFA108(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 108;
         this.eot = PythonPartialParser.DFA108_eot;
         this.eof = PythonPartialParser.DFA108_eof;
         this.min = PythonPartialParser.DFA108_min;
         this.max = PythonPartialParser.DFA108_max;
         this.accept = PythonPartialParser.DFA108_accept;
         this.special = PythonPartialParser.DFA108_special;
         this.transition = PythonPartialParser.DFA108_transition;
      }

      public String getDescription() {
         return "()* loopback of 754:12: ( options {k=2; } : COMMA test )*";
      }
   }

   class DFA84 extends DFA {
      public DFA84(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 84;
         this.eot = PythonPartialParser.DFA84_eot;
         this.eof = PythonPartialParser.DFA84_eof;
         this.min = PythonPartialParser.DFA84_min;
         this.max = PythonPartialParser.DFA84_max;
         this.accept = PythonPartialParser.DFA84_accept;
         this.special = PythonPartialParser.DFA84_special;
         this.transition = PythonPartialParser.DFA84_transition;
      }

      public String getDescription() {
         return "611:1: comp_op : ( LESS | GREATER | EQUAL | GREATEREQUAL | LESSEQUAL | ALT_NOTEQUAL | NOTEQUAL | IN | NOT IN | IS | IS NOT );";
      }
   }

   class DFA75 extends DFA {
      public DFA75(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 75;
         this.eot = PythonPartialParser.DFA75_eot;
         this.eof = PythonPartialParser.DFA75_eof;
         this.min = PythonPartialParser.DFA75_min;
         this.max = PythonPartialParser.DFA75_max;
         this.accept = PythonPartialParser.DFA75_accept;
         this.special = PythonPartialParser.DFA75_special;
         this.transition = PythonPartialParser.DFA75_transition;
      }

      public String getDescription() {
         return "571:7: ( ( IF or_test ORELSE )=> IF or_test ORELSE test | )";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         switch (s) {
            case 0:
               int LA75_1 = input.LA(1);
               int index75_1 = input.index();
               input.rewind();
               int sx = true;
               if (PythonPartialParser.this.synpred7_PythonPartial()) {
                  s = 15;
               } else {
                  s = 2;
               }

               input.seek(index75_1);
               if (s >= 0) {
                  return s;
               }
            default:
               if (PythonPartialParser.this.state.backtracking > 0) {
                  PythonPartialParser.this.state.failed = true;
                  return -1;
               } else {
                  NoViableAltException nvae = new NoViableAltException(this.getDescription(), 75, s, input);
                  this.error(nvae);
                  throw nvae;
               }
         }
      }
   }

   class DFA57 extends DFA {
      public DFA57(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 57;
         this.eot = PythonPartialParser.DFA57_eot;
         this.eof = PythonPartialParser.DFA57_eof;
         this.min = PythonPartialParser.DFA57_min;
         this.max = PythonPartialParser.DFA57_max;
         this.accept = PythonPartialParser.DFA57_accept;
         this.special = PythonPartialParser.DFA57_special;
         this.transition = PythonPartialParser.DFA57_transition;
      }

      public String getDescription() {
         return "489:1: compound_stmt : ( if_stmt | while_stmt | for_stmt | try_stmt | with_stmt | ( ( decorators )? DEF )=> funcdef | ( ( decorators )? CLASS )=> classdef | decorators );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         switch (s) {
            case 0:
               int LA57_0 = input.LA(1);
               int index57_0 = input.index();
               input.rewind();
               s = -1;
               if (LA57_0 == 27) {
                  s = 1;
               } else if (LA57_0 == 39) {
                  s = 2;
               } else if (LA57_0 == 25) {
                  s = 3;
               } else if (LA57_0 == 38) {
                  s = 4;
               } else if (LA57_0 == 40) {
                  s = 5;
               } else if (LA57_0 == 42) {
                  s = 6;
               } else if (LA57_0 == 18 && PythonPartialParser.this.synpred5_PythonPartial()) {
                  s = 7;
               } else if (LA57_0 == 16 && PythonPartialParser.this.synpred6_PythonPartial()) {
                  s = 8;
               }

               input.seek(index57_0);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA57_6 = input.LA(1);
               int index57_6 = input.index();
               input.rewind();
               int sx = true;
               if (PythonPartialParser.this.synpred5_PythonPartial()) {
                  s = 7;
               } else if (PythonPartialParser.this.synpred6_PythonPartial()) {
                  s = 8;
               } else {
                  s = 9;
               }

               input.seek(index57_6);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonPartialParser.this.state.backtracking > 0) {
            PythonPartialParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 57, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA45 extends DFA {
      public DFA45(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 45;
         this.eot = PythonPartialParser.DFA45_eot;
         this.eof = PythonPartialParser.DFA45_eof;
         this.min = PythonPartialParser.DFA45_min;
         this.max = PythonPartialParser.DFA45_max;
         this.accept = PythonPartialParser.DFA45_accept;
         this.special = PythonPartialParser.DFA45_special;
         this.transition = PythonPartialParser.DFA45_transition;
      }

      public String getDescription() {
         return "440:12: ( ( DOT )* dotted_name | ( DOT )+ )";
      }
   }

   class DFA34 extends DFA {
      public DFA34(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 34;
         this.eot = PythonPartialParser.DFA34_eot;
         this.eof = PythonPartialParser.DFA34_eof;
         this.min = PythonPartialParser.DFA34_min;
         this.max = PythonPartialParser.DFA34_max;
         this.accept = PythonPartialParser.DFA34_accept;
         this.special = PythonPartialParser.DFA34_special;
         this.transition = PythonPartialParser.DFA34_transition;
      }

      public String getDescription() {
         return "()* loopback of 373:13: ( options {k=2; } : COMMA test )*";
      }
   }

   class DFA36 extends DFA {
      public DFA36(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 36;
         this.eot = PythonPartialParser.DFA36_eot;
         this.eof = PythonPartialParser.DFA36_eof;
         this.min = PythonPartialParser.DFA36_min;
         this.max = PythonPartialParser.DFA36_max;
         this.accept = PythonPartialParser.DFA36_accept;
         this.special = PythonPartialParser.DFA36_special;
         this.transition = PythonPartialParser.DFA36_transition;
      }

      public String getDescription() {
         return "371:1: printlist : ( ( test COMMA )=> test ( options {k=2; } : COMMA test )* ( COMMA )? | test );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean sx;
         switch (s) {
            case 0:
               int LA36_1 = input.LA(1);
               int index36_1 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_1);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA36_2 = input.LA(1);
               int index36_2 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA36_3 = input.LA(1);
               int index36_3 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_3);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA36_4 = input.LA(1);
               int index36_4 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_4);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA36_5 = input.LA(1);
               int index36_5 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_5);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA36_6 = input.LA(1);
               int index36_6 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_6);
               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA36_7 = input.LA(1);
               int index36_7 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_7);
               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA36_8 = input.LA(1);
               int index36_8 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_8);
               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA36_9 = input.LA(1);
               int index36_9 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_9);
               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA36_10 = input.LA(1);
               int index36_10 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_10);
               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA36_11 = input.LA(1);
               int index36_11 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_11);
               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA36_12 = input.LA(1);
               int index36_12 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_12);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA36_13 = input.LA(1);
               int index36_13 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_13);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA36_14 = input.LA(1);
               int index36_14 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_14);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA36_15 = input.LA(1);
               int index36_15 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_15);
               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA36_16 = input.LA(1);
               int index36_16 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_16);
               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA36_17 = input.LA(1);
               int index36_17 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_17);
               if (s >= 0) {
                  return s;
               }
               break;
            case 17:
               int LA36_18 = input.LA(1);
               int index36_18 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred4_PythonPartial()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index36_18);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonPartialParser.this.state.backtracking > 0) {
            PythonPartialParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 36, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA32 extends DFA {
      public DFA32(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 32;
         this.eot = PythonPartialParser.DFA32_eot;
         this.eof = PythonPartialParser.DFA32_eof;
         this.min = PythonPartialParser.DFA32_min;
         this.max = PythonPartialParser.DFA32_max;
         this.accept = PythonPartialParser.DFA32_accept;
         this.special = PythonPartialParser.DFA32_special;
         this.transition = PythonPartialParser.DFA32_transition;
      }

      public String getDescription() {
         return "326:7: ( ( testlist augassign )=> testlist ( ( augassign yield_expr ) | ( augassign testlist ) ) | ( testlist ASSIGN )=> testlist ( | ( ( ASSIGN testlist )+ ) | ( ( ASSIGN yield_expr )+ ) ) | testlist )";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean sx;
         switch (s) {
            case 0:
               int LA32_1 = input.LA(1);
               int index32_1 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_1);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA32_2 = input.LA(1);
               int index32_2 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA32_3 = input.LA(1);
               int index32_3 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_3);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA32_4 = input.LA(1);
               int index32_4 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_4);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA32_5 = input.LA(1);
               int index32_5 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_5);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA32_6 = input.LA(1);
               int index32_6 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_6);
               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA32_7 = input.LA(1);
               int index32_7 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_7);
               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA32_8 = input.LA(1);
               int index32_8 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_8);
               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA32_9 = input.LA(1);
               int index32_9 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_9);
               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA32_10 = input.LA(1);
               int index32_10 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_10);
               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA32_11 = input.LA(1);
               int index32_11 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_11);
               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA32_12 = input.LA(1);
               int index32_12 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_12);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA32_13 = input.LA(1);
               int index32_13 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_13);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA32_14 = input.LA(1);
               int index32_14 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_14);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA32_15 = input.LA(1);
               int index32_15 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_15);
               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA32_16 = input.LA(1);
               int index32_16 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_16);
               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA32_17 = input.LA(1);
               int index32_17 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_17);
               if (s >= 0) {
                  return s;
               }
               break;
            case 17:
               int LA32_18 = input.LA(1);
               int index32_18 = input.index();
               input.rewind();
               sx = true;
               if (PythonPartialParser.this.synpred2_PythonPartial()) {
                  s = 19;
               } else if (PythonPartialParser.this.synpred3_PythonPartial()) {
                  s = 20;
               } else {
                  s = 21;
               }

               input.seek(index32_18);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonPartialParser.this.state.backtracking > 0) {
            PythonPartialParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 32, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA27 extends DFA {
      public DFA27(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 27;
         this.eot = PythonPartialParser.DFA27_eot;
         this.eof = PythonPartialParser.DFA27_eof;
         this.min = PythonPartialParser.DFA27_min;
         this.max = PythonPartialParser.DFA27_max;
         this.accept = PythonPartialParser.DFA27_accept;
         this.special = PythonPartialParser.DFA27_special;
         this.transition = PythonPartialParser.DFA27_transition;
      }

      public String getDescription() {
         return "312:1: small_stmt : ( expr_stmt | print_stmt | del_stmt | pass_stmt | flow_stmt | import_stmt | global_stmt | exec_stmt | assert_stmt );";
      }
   }
}
