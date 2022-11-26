package org.python.antlr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.python.antlr.ast.Assert;
import org.python.antlr.ast.Assign;
import org.python.antlr.ast.Attribute;
import org.python.antlr.ast.AugAssign;
import org.python.antlr.ast.Break;
import org.python.antlr.ast.Call;
import org.python.antlr.ast.ClassDef;
import org.python.antlr.ast.Compare;
import org.python.antlr.ast.Context;
import org.python.antlr.ast.Continue;
import org.python.antlr.ast.Delete;
import org.python.antlr.ast.Dict;
import org.python.antlr.ast.DictComp;
import org.python.antlr.ast.Ellipsis;
import org.python.antlr.ast.ErrorMod;
import org.python.antlr.ast.ExceptHandler;
import org.python.antlr.ast.Exec;
import org.python.antlr.ast.Expr;
import org.python.antlr.ast.Expression;
import org.python.antlr.ast.GeneratorExp;
import org.python.antlr.ast.Global;
import org.python.antlr.ast.If;
import org.python.antlr.ast.IfExp;
import org.python.antlr.ast.Import;
import org.python.antlr.ast.ImportFrom;
import org.python.antlr.ast.Index;
import org.python.antlr.ast.Interactive;
import org.python.antlr.ast.Lambda;
import org.python.antlr.ast.ListComp;
import org.python.antlr.ast.Module;
import org.python.antlr.ast.Name;
import org.python.antlr.ast.Num;
import org.python.antlr.ast.Pass;
import org.python.antlr.ast.Print;
import org.python.antlr.ast.Raise;
import org.python.antlr.ast.Repr;
import org.python.antlr.ast.Return;
import org.python.antlr.ast.Set;
import org.python.antlr.ast.SetComp;
import org.python.antlr.ast.Str;
import org.python.antlr.ast.Subscript;
import org.python.antlr.ast.Tuple;
import org.python.antlr.ast.UnaryOp;
import org.python.antlr.ast.With;
import org.python.antlr.ast.Yield;
import org.python.antlr.ast.alias;
import org.python.antlr.ast.arguments;
import org.python.antlr.ast.boolopType;
import org.python.antlr.ast.cmpopType;
import org.python.antlr.ast.comprehension;
import org.python.antlr.ast.expr_contextType;
import org.python.antlr.ast.operatorType;
import org.python.antlr.ast.unaryopType;
import org.python.antlr.base.excepthandler;
import org.python.antlr.base.expr;
import org.python.antlr.base.mod;
import org.python.antlr.base.slice;
import org.python.antlr.base.stmt;
import org.python.antlr.runtime.BaseRecognizer;
import org.python.antlr.runtime.BitSet;
import org.python.antlr.runtime.DFA;
import org.python.antlr.runtime.EarlyExitException;
import org.python.antlr.runtime.FailedPredicateException;
import org.python.antlr.runtime.IntStream;
import org.python.antlr.runtime.MismatchedSetException;
import org.python.antlr.runtime.NoViableAltException;
import org.python.antlr.runtime.Parser;
import org.python.antlr.runtime.ParserRuleReturnScope;
import org.python.antlr.runtime.RecognitionException;
import org.python.antlr.runtime.RecognizerSharedState;
import org.python.antlr.runtime.Token;
import org.python.antlr.runtime.TokenStream;
import org.python.antlr.runtime.tree.CommonTreeAdaptor;
import org.python.antlr.runtime.tree.RewriteCardinalityException;
import org.python.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.python.antlr.runtime.tree.RewriteRuleTokenStream;
import org.python.antlr.runtime.tree.TreeAdaptor;

public class PythonParser extends Parser {
   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "INDENT", "DEDENT", "TRAILBACKSLASH", "NEWLINE", "LEADING_WS", "NAME", "DOT", "PRINT", "AND", "AS", "ASSERT", "BREAK", "CLASS", "CONTINUE", "DEF", "DELETE", "ELIF", "EXCEPT", "EXEC", "FINALLY", "FROM", "FOR", "GLOBAL", "IF", "IMPORT", "IN", "IS", "LAMBDA", "NOT", "OR", "ORELSE", "PASS", "RAISE", "RETURN", "TRY", "WHILE", "WITH", "YIELD", "AT", "LPAREN", "RPAREN", "COLON", "ASSIGN", "COMMA", "STAR", "DOUBLESTAR", "SEMI", "PLUSEQUAL", "MINUSEQUAL", "STAREQUAL", "SLASHEQUAL", "PERCENTEQUAL", "AMPEREQUAL", "VBAREQUAL", "CIRCUMFLEXEQUAL", "LEFTSHIFTEQUAL", "RIGHTSHIFTEQUAL", "DOUBLESTAREQUAL", "DOUBLESLASHEQUAL", "RIGHTSHIFT", "LESS", "GREATER", "EQUAL", "GREATEREQUAL", "LESSEQUAL", "ALT_NOTEQUAL", "NOTEQUAL", "VBAR", "CIRCUMFLEX", "AMPER", "LEFTSHIFT", "PLUS", "MINUS", "SLASH", "PERCENT", "DOUBLESLASH", "TILDE", "LBRACK", "RBRACK", "LCURLY", "RCURLY", "BACKQUOTE", "INT", "LONGINT", "FLOAT", "COMPLEX", "STRING", "DIGITS", "Exponent", "TRIAPOS", "TRIQUOTE", "ESC", "COMMENT", "CONTINUED_LINE", "WS"};
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
   protected TreeAdaptor adaptor;
   private ErrorHandler errorHandler;
   private GrammarActions actions;
   private String encoding;
   private boolean printFunction;
   private boolean unicodeLiterals;
   protected Stack suite_stack;
   protected Stack expr_stack;
   protected DFA30 dfa30;
   protected DFA35 dfa35;
   protected DFA31 dfa31;
   protected DFA40 dfa40;
   protected DFA38 dfa38;
   protected DFA43 dfa43;
   protected DFA41 dfa41;
   protected DFA52 dfa52;
   protected DFA80 dfa80;
   protected DFA89 dfa89;
   protected DFA112 dfa112;
   protected DFA116 dfa116;
   protected DFA129 dfa129;
   protected DFA133 dfa133;
   protected DFA131 dfa131;
   protected DFA134 dfa134;
   protected DFA138 dfa138;
   protected DFA136 dfa136;
   protected DFA139 dfa139;
   static final String DFA30_eotS = "\u000b\uffff";
   static final String DFA30_eofS = "\u000b\uffff";
   static final String DFA30_minS = "\u0001\t\u0001\uffff\u0001\u0000\b\uffff";
   static final String DFA30_maxS = "\u0001Z\u0001\uffff\u0001\u0000\b\uffff";
   static final String DFA30_acceptS = "\u0001\uffff\u0001\u0001\u0001\uffff\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t";
   static final String DFA30_specialS = "\u0001\u0001\u0001\uffff\u0001\u0000\b\uffff}>";
   static final String[] DFA30_transitionS = new String[]{"\u0001\u0001\u0001\uffff\u0001\u0002\u0002\uffff\u0001\t\u0001\u0005\u0001\uffff\u0001\u0005\u0001\uffff\u0001\u0003\u0002\uffff\u0001\b\u0001\uffff\u0001\u0006\u0001\uffff\u0001\u0007\u0001\uffff\u0001\u0006\u0002\uffff\u0002\u0001\u0002\uffff\u0001\u0004\u0002\u0005\u0003\uffff\u0001\u0005\u0001\uffff\u0001\u0001\u001f\uffff\u0002\u0001\u0003\uffff\u0002\u0001\u0001\uffff\u0001\u0001\u0001\uffff\u0006\u0001", "", "\u0001\uffff", "", "", "", "", "", "", "", ""};
   static final short[] DFA30_eot = DFA.unpackEncodedString("\u000b\uffff");
   static final short[] DFA30_eof = DFA.unpackEncodedString("\u000b\uffff");
   static final char[] DFA30_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0001\uffff\u0001\u0000\b\uffff");
   static final char[] DFA30_max = DFA.unpackEncodedStringToUnsignedChars("\u0001Z\u0001\uffff\u0001\u0000\b\uffff");
   static final short[] DFA30_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0001\uffff\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t");
   static final short[] DFA30_special = DFA.unpackEncodedString("\u0001\u0001\u0001\uffff\u0001\u0000\b\uffff}>");
   static final short[][] DFA30_transition;
   static final String DFA35_eotS = "\u0014\uffff";
   static final String DFA35_eofS = "\u0014\uffff";
   static final String DFA35_minS = "\u0001\t\u0010\u0000\u0003\uffff";
   static final String DFA35_maxS = "\u0001Z\u0010\u0000\u0003\uffff";
   static final String DFA35_acceptS = "\u0011\uffff\u0001\u0001\u0001\u0002\u0001\u0003";
   static final String DFA35_specialS = "\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0003\uffff}>";
   static final String[] DFA35_transitionS;
   static final short[] DFA35_eot;
   static final short[] DFA35_eof;
   static final char[] DFA35_min;
   static final char[] DFA35_max;
   static final short[] DFA35_accept;
   static final short[] DFA35_special;
   static final short[][] DFA35_transition;
   static final String DFA31_eotS = "\u000f\uffff";
   static final String DFA31_eofS = "\u000f\uffff";
   static final String DFA31_minS = "\u00013\f\t\u0002\uffff";
   static final String DFA31_maxS = "\u0001>\fZ\u0002\uffff";
   static final String DFA31_acceptS = "\r\uffff\u0001\u0002\u0001\u0001";
   static final String DFA31_specialS = "\u000f\uffff}>";
   static final String[] DFA31_transitionS;
   static final short[] DFA31_eot;
   static final short[] DFA31_eof;
   static final char[] DFA31_min;
   static final char[] DFA31_max;
   static final short[] DFA31_accept;
   static final short[] DFA31_special;
   static final short[][] DFA31_transition;
   static final String DFA40_eotS = "\u0013\uffff";
   static final String DFA40_eofS = "\u0013\uffff";
   static final String DFA40_minS = "\u0001\t\u0010\u0000\u0002\uffff";
   static final String DFA40_maxS = "\u0001Z\u0010\u0000\u0002\uffff";
   static final String DFA40_acceptS = "\u0011\uffff\u0001\u0001\u0001\u0002";
   static final String DFA40_specialS = "\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0002\uffff}>";
   static final String[] DFA40_transitionS;
   static final short[] DFA40_eot;
   static final short[] DFA40_eof;
   static final char[] DFA40_min;
   static final char[] DFA40_max;
   static final short[] DFA40_accept;
   static final short[] DFA40_special;
   static final short[][] DFA40_transition;
   static final String DFA38_eotS = "\u0016\uffff";
   static final String DFA38_eofS = "\u0016\uffff";
   static final String DFA38_minS = "\u0002\u0007\u0014\uffff";
   static final String DFA38_maxS = "\u00012\u0001Z\u0014\uffff";
   static final String DFA38_acceptS = "\u0002\uffff\u0001\u0002\u0003\uffff\u0001\u0001\u000f\uffff";
   static final String DFA38_specialS = "\u0016\uffff}>";
   static final String[] DFA38_transitionS;
   static final short[] DFA38_eot;
   static final short[] DFA38_eof;
   static final char[] DFA38_min;
   static final char[] DFA38_max;
   static final short[] DFA38_accept;
   static final short[] DFA38_special;
   static final short[][] DFA38_transition;
   static final String DFA43_eotS = "\u0013\uffff";
   static final String DFA43_eofS = "\u0013\uffff";
   static final String DFA43_minS = "\u0001\t\u0010\u0000\u0002\uffff";
   static final String DFA43_maxS = "\u0001Z\u0010\u0000\u0002\uffff";
   static final String DFA43_acceptS = "\u0011\uffff\u0001\u0001\u0001\u0002";
   static final String DFA43_specialS = "\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0002\uffff}>";
   static final String[] DFA43_transitionS;
   static final short[] DFA43_eot;
   static final short[] DFA43_eof;
   static final char[] DFA43_min;
   static final char[] DFA43_max;
   static final short[] DFA43_accept;
   static final short[] DFA43_special;
   static final short[][] DFA43_transition;
   static final String DFA41_eotS = "\u0016\uffff";
   static final String DFA41_eofS = "\u0016\uffff";
   static final String DFA41_minS = "\u0002\u0007\u0014\uffff";
   static final String DFA41_maxS = "\u00012\u0001Z\u0014\uffff";
   static final String DFA41_acceptS = "\u0002\uffff\u0001\u0002\u0003\uffff\u0001\u0001\u000f\uffff";
   static final String DFA41_specialS = "\u0016\uffff}>";
   static final String[] DFA41_transitionS;
   static final short[] DFA41_eot;
   static final short[] DFA41_eof;
   static final char[] DFA41_min;
   static final char[] DFA41_max;
   static final short[] DFA41_accept;
   static final short[] DFA41_special;
   static final short[][] DFA41_transition;
   static final String DFA52_eotS = "\u0004\uffff";
   static final String DFA52_eofS = "\u0004\uffff";
   static final String DFA52_minS = "\u0002\t\u0002\uffff";
   static final String DFA52_maxS = "\u0001\n\u0001\u001c\u0002\uffff";
   static final String DFA52_acceptS = "\u0002\uffff\u0001\u0001\u0001\u0002";
   static final String DFA52_specialS = "\u0004\uffff}>";
   static final String[] DFA52_transitionS;
   static final short[] DFA52_eot;
   static final short[] DFA52_eof;
   static final char[] DFA52_min;
   static final char[] DFA52_max;
   static final short[] DFA52_accept;
   static final short[] DFA52_special;
   static final short[][] DFA52_transition;
   static final String DFA80_eotS = "\u001b\uffff";
   static final String DFA80_eofS = "\u0001\u0002\u001a\uffff";
   static final String DFA80_minS = "\u0001\u0007\u0001\u0000\u0019\uffff";
   static final String DFA80_maxS = "\u0001U\u0001\u0000\u0019\uffff";
   static final String DFA80_acceptS = "\u0002\uffff\u0001\u0002\u0017\uffff\u0001\u0001";
   static final String DFA80_specialS = "\u0001\uffff\u0001\u0000\u0019\uffff}>";
   static final String[] DFA80_transitionS;
   static final short[] DFA80_eot;
   static final short[] DFA80_eof;
   static final char[] DFA80_min;
   static final char[] DFA80_max;
   static final short[] DFA80_accept;
   static final short[] DFA80_special;
   static final short[][] DFA80_transition;
   static final String DFA89_eotS = "\r\uffff";
   static final String DFA89_eofS = "\r\uffff";
   static final String DFA89_minS = "\u0001\u001d\t\uffff\u0001\t\u0002\uffff";
   static final String DFA89_maxS = "\u0001F\t\uffff\u0001Z\u0002\uffff";
   static final String DFA89_acceptS = "\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\uffff\u0001\u000b\u0001\n";
   static final String DFA89_specialS = "\r\uffff}>";
   static final String[] DFA89_transitionS;
   static final short[] DFA89_eot;
   static final short[] DFA89_eof;
   static final char[] DFA89_min;
   static final char[] DFA89_max;
   static final short[] DFA89_accept;
   static final short[] DFA89_special;
   static final short[][] DFA89_transition;
   static final String DFA112_eotS = "\f\uffff";
   static final String DFA112_eofS = "\f\uffff";
   static final String DFA112_minS = "\u0001\t\u000b\uffff";
   static final String DFA112_maxS = "\u0001Z\u000b\uffff";
   static final String DFA112_acceptS = "\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0002\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n";
   static final String DFA112_specialS = "\u0001\u0000\u000b\uffff}>";
   static final String[] DFA112_transitionS;
   static final short[] DFA112_eot;
   static final short[] DFA112_eof;
   static final char[] DFA112_min;
   static final char[] DFA112_max;
   static final short[] DFA112_accept;
   static final short[] DFA112_special;
   static final short[][] DFA112_transition;
   static final String DFA116_eotS = "\u0014\uffff";
   static final String DFA116_eofS = "\u0014\uffff";
   static final String DFA116_minS = "\u0001,\u0001\t\u0012\uffff";
   static final String DFA116_maxS = "\u0001/\u0001Z\u0012\uffff";
   static final String DFA116_acceptS = "\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u000f\uffff";
   static final String DFA116_specialS = "\u0014\uffff}>";
   static final String[] DFA116_transitionS;
   static final short[] DFA116_eot;
   static final short[] DFA116_eof;
   static final char[] DFA116_min;
   static final char[] DFA116_max;
   static final short[] DFA116_accept;
   static final short[] DFA116_special;
   static final short[][] DFA116_transition;
   static final String DFA129_eotS = "\u0015\uffff";
   static final String DFA129_eofS = "\u0015\uffff";
   static final String DFA129_minS = "\u0001\t\u0001\uffff\u0010\u0000\u0003\uffff";
   static final String DFA129_maxS = "\u0001Z\u0001\uffff\u0010\u0000\u0003\uffff";
   static final String DFA129_acceptS = "\u0001\uffff\u0001\u0001\u0010\uffff\u0001\u0003\u0001\u0002\u0001\u0004";
   static final String DFA129_specialS = "\u0001\u0000\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0003\uffff}>";
   static final String[] DFA129_transitionS;
   static final short[] DFA129_eot;
   static final short[] DFA129_eof;
   static final char[] DFA129_min;
   static final char[] DFA129_max;
   static final short[] DFA129_accept;
   static final short[] DFA129_special;
   static final short[][] DFA129_transition;
   static final String DFA133_eotS = "\u0011\uffff";
   static final String DFA133_eofS = "\u0011\uffff";
   static final String DFA133_minS = "\u0001\t\u000e\u0000\u0002\uffff";
   static final String DFA133_maxS = "\u0001Z\u000e\u0000\u0002\uffff";
   static final String DFA133_acceptS = "\u000f\uffff\u0001\u0001\u0001\u0002";
   static final String DFA133_specialS = "\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0002\uffff}>";
   static final String[] DFA133_transitionS;
   static final short[] DFA133_eot;
   static final short[] DFA133_eof;
   static final char[] DFA133_min;
   static final char[] DFA133_max;
   static final short[] DFA133_accept;
   static final short[] DFA133_special;
   static final short[][] DFA133_transition;
   static final String DFA131_eotS = "\u0012\uffff";
   static final String DFA131_eofS = "\u0012\uffff";
   static final String DFA131_minS = "\u0001\u001d\u0001\t\u0010\uffff";
   static final String DFA131_maxS = "\u0001/\u0001Z\u0010\uffff";
   static final String DFA131_acceptS = "\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\r\uffff";
   static final String DFA131_specialS = "\u0012\uffff}>";
   static final String[] DFA131_transitionS;
   static final short[] DFA131_eot;
   static final short[] DFA131_eof;
   static final char[] DFA131_min;
   static final char[] DFA131_max;
   static final short[] DFA131_accept;
   static final short[] DFA131_special;
   static final short[][] DFA131_transition;
   static final String DFA134_eotS = "\u0014\uffff";
   static final String DFA134_eofS = "\u0014\uffff";
   static final String DFA134_minS = "\u0002\u0007\u0012\uffff";
   static final String DFA134_maxS = "\u00012\u0001Z\u0012\uffff";
   static final String DFA134_acceptS = "\u0002\uffff\u0001\u0002\u0003\uffff\u0001\u0001\r\uffff";
   static final String DFA134_specialS = "\u0014\uffff}>";
   static final String[] DFA134_transitionS;
   static final short[] DFA134_eot;
   static final short[] DFA134_eof;
   static final char[] DFA134_min;
   static final char[] DFA134_max;
   static final short[] DFA134_accept;
   static final short[] DFA134_special;
   static final short[][] DFA134_transition;
   static final String DFA138_eotS = "\u0013\uffff";
   static final String DFA138_eofS = "\u0013\uffff";
   static final String DFA138_minS = "\u0001\t\u0010\u0000\u0002\uffff";
   static final String DFA138_maxS = "\u0001Z\u0010\u0000\u0002\uffff";
   static final String DFA138_acceptS = "\u0011\uffff\u0001\u0001\u0001\u0002";
   static final String DFA138_specialS = "\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0002\uffff}>";
   static final String[] DFA138_transitionS;
   static final short[] DFA138_eot;
   static final short[] DFA138_eof;
   static final char[] DFA138_min;
   static final char[] DFA138_max;
   static final short[] DFA138_accept;
   static final short[] DFA138_special;
   static final short[][] DFA138_transition;
   static final String DFA136_eotS = ">\uffff";
   static final String DFA136_eofS = "\u0002\u0002<\uffff";
   static final String DFA136_minS = "\u0002\u0007<\uffff";
   static final String DFA136_maxS = "\u0001U\u0001Z<\uffff";
   static final String DFA136_acceptS = "\u0002\uffff\u0001\u0002&\uffff\u0001\u0001\u0005\uffff\u0001\u0001\u000e\uffff";
   static final String DFA136_specialS = ">\uffff}>";
   static final String[] DFA136_transitionS;
   static final short[] DFA136_eot;
   static final short[] DFA136_eof;
   static final char[] DFA136_min;
   static final char[] DFA136_max;
   static final short[] DFA136_accept;
   static final short[] DFA136_special;
   static final short[][] DFA136_transition;
   static final String DFA139_eotS = "\u0014\uffff";
   static final String DFA139_eofS = "\u0014\uffff";
   static final String DFA139_minS = "\u0001/\u0001\t\u0012\uffff";
   static final String DFA139_maxS = "\u0001T\u0001Z\u0012\uffff";
   static final String DFA139_acceptS = "\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u000f\uffff";
   static final String DFA139_specialS = "\u0014\uffff}>";
   static final String[] DFA139_transitionS;
   static final short[] DFA139_eot;
   static final short[] DFA139_eof;
   static final char[] DFA139_min;
   static final char[] DFA139_max;
   static final short[] DFA139_accept;
   static final short[] DFA139_special;
   static final short[][] DFA139_transition;
   public static final BitSet FOLLOW_NEWLINE_in_single_input118;
   public static final BitSet FOLLOW_EOF_in_single_input121;
   public static final BitSet FOLLOW_simple_stmt_in_single_input137;
   public static final BitSet FOLLOW_NEWLINE_in_single_input139;
   public static final BitSet FOLLOW_EOF_in_single_input142;
   public static final BitSet FOLLOW_compound_stmt_in_single_input158;
   public static final BitSet FOLLOW_NEWLINE_in_single_input160;
   public static final BitSet FOLLOW_EOF_in_single_input163;
   public static final BitSet FOLLOW_NEWLINE_in_file_input215;
   public static final BitSet FOLLOW_stmt_in_file_input225;
   public static final BitSet FOLLOW_EOF_in_file_input244;
   public static final BitSet FOLLOW_LEADING_WS_in_eval_input298;
   public static final BitSet FOLLOW_NEWLINE_in_eval_input302;
   public static final BitSet FOLLOW_testlist_in_eval_input306;
   public static final BitSet FOLLOW_NEWLINE_in_eval_input310;
   public static final BitSet FOLLOW_EOF_in_eval_input314;
   public static final BitSet FOLLOW_NAME_in_dotted_attr366;
   public static final BitSet FOLLOW_DOT_in_dotted_attr377;
   public static final BitSet FOLLOW_NAME_in_dotted_attr381;
   public static final BitSet FOLLOW_NAME_in_name_or_print446;
   public static final BitSet FOLLOW_PRINT_in_name_or_print460;
   public static final BitSet FOLLOW_set_in_attr0;
   public static final BitSet FOLLOW_AT_in_decorator762;
   public static final BitSet FOLLOW_dotted_attr_in_decorator764;
   public static final BitSet FOLLOW_LPAREN_in_decorator772;
   public static final BitSet FOLLOW_arglist_in_decorator782;
   public static final BitSet FOLLOW_RPAREN_in_decorator826;
   public static final BitSet FOLLOW_NEWLINE_in_decorator848;
   public static final BitSet FOLLOW_decorator_in_decorators876;
   public static final BitSet FOLLOW_decorators_in_funcdef914;
   public static final BitSet FOLLOW_DEF_in_funcdef917;
   public static final BitSet FOLLOW_name_or_print_in_funcdef919;
   public static final BitSet FOLLOW_parameters_in_funcdef921;
   public static final BitSet FOLLOW_COLON_in_funcdef923;
   public static final BitSet FOLLOW_suite_in_funcdef925;
   public static final BitSet FOLLOW_LPAREN_in_parameters958;
   public static final BitSet FOLLOW_varargslist_in_parameters967;
   public static final BitSet FOLLOW_RPAREN_in_parameters1011;
   public static final BitSet FOLLOW_fpdef_in_defparameter1044;
   public static final BitSet FOLLOW_ASSIGN_in_defparameter1048;
   public static final BitSet FOLLOW_test_in_defparameter1050;
   public static final BitSet FOLLOW_defparameter_in_varargslist1096;
   public static final BitSet FOLLOW_COMMA_in_varargslist1107;
   public static final BitSet FOLLOW_defparameter_in_varargslist1111;
   public static final BitSet FOLLOW_COMMA_in_varargslist1123;
   public static final BitSet FOLLOW_STAR_in_varargslist1136;
   public static final BitSet FOLLOW_NAME_in_varargslist1140;
   public static final BitSet FOLLOW_COMMA_in_varargslist1143;
   public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist1145;
   public static final BitSet FOLLOW_NAME_in_varargslist1149;
   public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist1165;
   public static final BitSet FOLLOW_NAME_in_varargslist1169;
   public static final BitSet FOLLOW_STAR_in_varargslist1207;
   public static final BitSet FOLLOW_NAME_in_varargslist1211;
   public static final BitSet FOLLOW_COMMA_in_varargslist1214;
   public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist1216;
   public static final BitSet FOLLOW_NAME_in_varargslist1220;
   public static final BitSet FOLLOW_DOUBLESTAR_in_varargslist1238;
   public static final BitSet FOLLOW_NAME_in_varargslist1242;
   public static final BitSet FOLLOW_NAME_in_fpdef1279;
   public static final BitSet FOLLOW_LPAREN_in_fpdef1306;
   public static final BitSet FOLLOW_fplist_in_fpdef1308;
   public static final BitSet FOLLOW_RPAREN_in_fpdef1310;
   public static final BitSet FOLLOW_LPAREN_in_fpdef1326;
   public static final BitSet FOLLOW_fplist_in_fpdef1329;
   public static final BitSet FOLLOW_RPAREN_in_fpdef1331;
   public static final BitSet FOLLOW_fpdef_in_fplist1360;
   public static final BitSet FOLLOW_COMMA_in_fplist1377;
   public static final BitSet FOLLOW_fpdef_in_fplist1381;
   public static final BitSet FOLLOW_COMMA_in_fplist1387;
   public static final BitSet FOLLOW_simple_stmt_in_stmt1423;
   public static final BitSet FOLLOW_compound_stmt_in_stmt1439;
   public static final BitSet FOLLOW_small_stmt_in_simple_stmt1475;
   public static final BitSet FOLLOW_SEMI_in_simple_stmt1485;
   public static final BitSet FOLLOW_small_stmt_in_simple_stmt1489;
   public static final BitSet FOLLOW_SEMI_in_simple_stmt1494;
   public static final BitSet FOLLOW_NEWLINE_in_simple_stmt1498;
   public static final BitSet FOLLOW_expr_stmt_in_small_stmt1521;
   public static final BitSet FOLLOW_del_stmt_in_small_stmt1536;
   public static final BitSet FOLLOW_pass_stmt_in_small_stmt1551;
   public static final BitSet FOLLOW_flow_stmt_in_small_stmt1566;
   public static final BitSet FOLLOW_import_stmt_in_small_stmt1581;
   public static final BitSet FOLLOW_global_stmt_in_small_stmt1596;
   public static final BitSet FOLLOW_exec_stmt_in_small_stmt1611;
   public static final BitSet FOLLOW_assert_stmt_in_small_stmt1626;
   public static final BitSet FOLLOW_print_stmt_in_small_stmt1645;
   public static final BitSet FOLLOW_testlist_in_expr_stmt1693;
   public static final BitSet FOLLOW_augassign_in_expr_stmt1709;
   public static final BitSet FOLLOW_yield_expr_in_expr_stmt1713;
   public static final BitSet FOLLOW_augassign_in_expr_stmt1753;
   public static final BitSet FOLLOW_testlist_in_expr_stmt1757;
   public static final BitSet FOLLOW_testlist_in_expr_stmt1812;
   public static final BitSet FOLLOW_ASSIGN_in_expr_stmt1839;
   public static final BitSet FOLLOW_testlist_in_expr_stmt1843;
   public static final BitSet FOLLOW_ASSIGN_in_expr_stmt1888;
   public static final BitSet FOLLOW_yield_expr_in_expr_stmt1892;
   public static final BitSet FOLLOW_testlist_in_expr_stmt1940;
   public static final BitSet FOLLOW_PLUSEQUAL_in_augassign1982;
   public static final BitSet FOLLOW_MINUSEQUAL_in_augassign2000;
   public static final BitSet FOLLOW_STAREQUAL_in_augassign2018;
   public static final BitSet FOLLOW_SLASHEQUAL_in_augassign2036;
   public static final BitSet FOLLOW_PERCENTEQUAL_in_augassign2054;
   public static final BitSet FOLLOW_AMPEREQUAL_in_augassign2072;
   public static final BitSet FOLLOW_VBAREQUAL_in_augassign2090;
   public static final BitSet FOLLOW_CIRCUMFLEXEQUAL_in_augassign2108;
   public static final BitSet FOLLOW_LEFTSHIFTEQUAL_in_augassign2126;
   public static final BitSet FOLLOW_RIGHTSHIFTEQUAL_in_augassign2144;
   public static final BitSet FOLLOW_DOUBLESTAREQUAL_in_augassign2162;
   public static final BitSet FOLLOW_DOUBLESLASHEQUAL_in_augassign2180;
   public static final BitSet FOLLOW_PRINT_in_print_stmt2220;
   public static final BitSet FOLLOW_printlist_in_print_stmt2231;
   public static final BitSet FOLLOW_RIGHTSHIFT_in_print_stmt2250;
   public static final BitSet FOLLOW_printlist2_in_print_stmt2254;
   public static final BitSet FOLLOW_test_in_printlist2334;
   public static final BitSet FOLLOW_COMMA_in_printlist2346;
   public static final BitSet FOLLOW_test_in_printlist2350;
   public static final BitSet FOLLOW_COMMA_in_printlist2358;
   public static final BitSet FOLLOW_test_in_printlist2379;
   public static final BitSet FOLLOW_test_in_printlist22436;
   public static final BitSet FOLLOW_COMMA_in_printlist22448;
   public static final BitSet FOLLOW_test_in_printlist22452;
   public static final BitSet FOLLOW_COMMA_in_printlist22460;
   public static final BitSet FOLLOW_test_in_printlist22481;
   public static final BitSet FOLLOW_DELETE_in_del_stmt2518;
   public static final BitSet FOLLOW_del_list_in_del_stmt2520;
   public static final BitSet FOLLOW_PASS_in_pass_stmt2556;
   public static final BitSet FOLLOW_break_stmt_in_flow_stmt2582;
   public static final BitSet FOLLOW_continue_stmt_in_flow_stmt2590;
   public static final BitSet FOLLOW_return_stmt_in_flow_stmt2598;
   public static final BitSet FOLLOW_raise_stmt_in_flow_stmt2606;
   public static final BitSet FOLLOW_yield_stmt_in_flow_stmt2614;
   public static final BitSet FOLLOW_BREAK_in_break_stmt2642;
   public static final BitSet FOLLOW_CONTINUE_in_continue_stmt2678;
   public static final BitSet FOLLOW_RETURN_in_return_stmt2714;
   public static final BitSet FOLLOW_testlist_in_return_stmt2723;
   public static final BitSet FOLLOW_yield_expr_in_yield_stmt2788;
   public static final BitSet FOLLOW_RAISE_in_raise_stmt2824;
   public static final BitSet FOLLOW_test_in_raise_stmt2829;
   public static final BitSet FOLLOW_COMMA_in_raise_stmt2833;
   public static final BitSet FOLLOW_test_in_raise_stmt2837;
   public static final BitSet FOLLOW_COMMA_in_raise_stmt2849;
   public static final BitSet FOLLOW_test_in_raise_stmt2853;
   public static final BitSet FOLLOW_import_name_in_import_stmt2886;
   public static final BitSet FOLLOW_import_from_in_import_stmt2894;
   public static final BitSet FOLLOW_IMPORT_in_import_name2922;
   public static final BitSet FOLLOW_dotted_as_names_in_import_name2924;
   public static final BitSet FOLLOW_FROM_in_import_from2961;
   public static final BitSet FOLLOW_DOT_in_import_from2966;
   public static final BitSet FOLLOW_dotted_name_in_import_from2969;
   public static final BitSet FOLLOW_DOT_in_import_from2975;
   public static final BitSet FOLLOW_IMPORT_in_import_from2979;
   public static final BitSet FOLLOW_STAR_in_import_from2990;
   public static final BitSet FOLLOW_import_as_names_in_import_from3015;
   public static final BitSet FOLLOW_LPAREN_in_import_from3038;
   public static final BitSet FOLLOW_import_as_names_in_import_from3042;
   public static final BitSet FOLLOW_COMMA_in_import_from3044;
   public static final BitSet FOLLOW_RPAREN_in_import_from3047;
   public static final BitSet FOLLOW_import_as_name_in_import_as_names3096;
   public static final BitSet FOLLOW_COMMA_in_import_as_names3099;
   public static final BitSet FOLLOW_import_as_name_in_import_as_names3104;
   public static final BitSet FOLLOW_NAME_in_import_as_name3145;
   public static final BitSet FOLLOW_AS_in_import_as_name3148;
   public static final BitSet FOLLOW_NAME_in_import_as_name3152;
   public static final BitSet FOLLOW_dotted_name_in_dotted_as_name3192;
   public static final BitSet FOLLOW_AS_in_dotted_as_name3195;
   public static final BitSet FOLLOW_NAME_in_dotted_as_name3199;
   public static final BitSet FOLLOW_dotted_as_name_in_dotted_as_names3235;
   public static final BitSet FOLLOW_COMMA_in_dotted_as_names3238;
   public static final BitSet FOLLOW_dotted_as_name_in_dotted_as_names3243;
   public static final BitSet FOLLOW_NAME_in_dotted_name3277;
   public static final BitSet FOLLOW_DOT_in_dotted_name3280;
   public static final BitSet FOLLOW_attr_in_dotted_name3284;
   public static final BitSet FOLLOW_GLOBAL_in_global_stmt3320;
   public static final BitSet FOLLOW_NAME_in_global_stmt3324;
   public static final BitSet FOLLOW_COMMA_in_global_stmt3327;
   public static final BitSet FOLLOW_NAME_in_global_stmt3331;
   public static final BitSet FOLLOW_EXEC_in_exec_stmt3369;
   public static final BitSet FOLLOW_expr_in_exec_stmt3371;
   public static final BitSet FOLLOW_IN_in_exec_stmt3375;
   public static final BitSet FOLLOW_test_in_exec_stmt3379;
   public static final BitSet FOLLOW_COMMA_in_exec_stmt3383;
   public static final BitSet FOLLOW_test_in_exec_stmt3387;
   public static final BitSet FOLLOW_ASSERT_in_assert_stmt3428;
   public static final BitSet FOLLOW_test_in_assert_stmt3432;
   public static final BitSet FOLLOW_COMMA_in_assert_stmt3436;
   public static final BitSet FOLLOW_test_in_assert_stmt3440;
   public static final BitSet FOLLOW_if_stmt_in_compound_stmt3469;
   public static final BitSet FOLLOW_while_stmt_in_compound_stmt3477;
   public static final BitSet FOLLOW_for_stmt_in_compound_stmt3485;
   public static final BitSet FOLLOW_try_stmt_in_compound_stmt3493;
   public static final BitSet FOLLOW_with_stmt_in_compound_stmt3501;
   public static final BitSet FOLLOW_funcdef_in_compound_stmt3518;
   public static final BitSet FOLLOW_classdef_in_compound_stmt3526;
   public static final BitSet FOLLOW_IF_in_if_stmt3554;
   public static final BitSet FOLLOW_test_in_if_stmt3556;
   public static final BitSet FOLLOW_COLON_in_if_stmt3559;
   public static final BitSet FOLLOW_suite_in_if_stmt3563;
   public static final BitSet FOLLOW_elif_clause_in_if_stmt3566;
   public static final BitSet FOLLOW_else_clause_in_elif_clause3611;
   public static final BitSet FOLLOW_ELIF_in_elif_clause3627;
   public static final BitSet FOLLOW_test_in_elif_clause3629;
   public static final BitSet FOLLOW_COLON_in_elif_clause3632;
   public static final BitSet FOLLOW_suite_in_elif_clause3634;
   public static final BitSet FOLLOW_elif_clause_in_elif_clause3646;
   public static final BitSet FOLLOW_ORELSE_in_else_clause3706;
   public static final BitSet FOLLOW_COLON_in_else_clause3708;
   public static final BitSet FOLLOW_suite_in_else_clause3712;
   public static final BitSet FOLLOW_WHILE_in_while_stmt3749;
   public static final BitSet FOLLOW_test_in_while_stmt3751;
   public static final BitSet FOLLOW_COLON_in_while_stmt3754;
   public static final BitSet FOLLOW_suite_in_while_stmt3758;
   public static final BitSet FOLLOW_ORELSE_in_while_stmt3762;
   public static final BitSet FOLLOW_COLON_in_while_stmt3764;
   public static final BitSet FOLLOW_suite_in_while_stmt3768;
   public static final BitSet FOLLOW_FOR_in_for_stmt3807;
   public static final BitSet FOLLOW_exprlist_in_for_stmt3809;
   public static final BitSet FOLLOW_IN_in_for_stmt3812;
   public static final BitSet FOLLOW_testlist_in_for_stmt3814;
   public static final BitSet FOLLOW_COLON_in_for_stmt3817;
   public static final BitSet FOLLOW_suite_in_for_stmt3821;
   public static final BitSet FOLLOW_ORELSE_in_for_stmt3833;
   public static final BitSet FOLLOW_COLON_in_for_stmt3835;
   public static final BitSet FOLLOW_suite_in_for_stmt3839;
   public static final BitSet FOLLOW_TRY_in_try_stmt3882;
   public static final BitSet FOLLOW_COLON_in_try_stmt3884;
   public static final BitSet FOLLOW_suite_in_try_stmt3888;
   public static final BitSet FOLLOW_except_clause_in_try_stmt3901;
   public static final BitSet FOLLOW_ORELSE_in_try_stmt3905;
   public static final BitSet FOLLOW_COLON_in_try_stmt3907;
   public static final BitSet FOLLOW_suite_in_try_stmt3911;
   public static final BitSet FOLLOW_FINALLY_in_try_stmt3917;
   public static final BitSet FOLLOW_COLON_in_try_stmt3919;
   public static final BitSet FOLLOW_suite_in_try_stmt3923;
   public static final BitSet FOLLOW_FINALLY_in_try_stmt3946;
   public static final BitSet FOLLOW_COLON_in_try_stmt3948;
   public static final BitSet FOLLOW_suite_in_try_stmt3952;
   public static final BitSet FOLLOW_WITH_in_with_stmt4001;
   public static final BitSet FOLLOW_with_item_in_with_stmt4005;
   public static final BitSet FOLLOW_COMMA_in_with_stmt4015;
   public static final BitSet FOLLOW_with_item_in_with_stmt4019;
   public static final BitSet FOLLOW_COLON_in_with_stmt4023;
   public static final BitSet FOLLOW_suite_in_with_stmt4025;
   public static final BitSet FOLLOW_test_in_with_item4062;
   public static final BitSet FOLLOW_AS_in_with_item4066;
   public static final BitSet FOLLOW_expr_in_with_item4068;
   public static final BitSet FOLLOW_EXCEPT_in_except_clause4107;
   public static final BitSet FOLLOW_test_in_except_clause4112;
   public static final BitSet FOLLOW_set_in_except_clause4116;
   public static final BitSet FOLLOW_test_in_except_clause4126;
   public static final BitSet FOLLOW_COLON_in_except_clause4133;
   public static final BitSet FOLLOW_suite_in_except_clause4135;
   public static final BitSet FOLLOW_simple_stmt_in_suite4181;
   public static final BitSet FOLLOW_NEWLINE_in_suite4197;
   public static final BitSet FOLLOW_INDENT_in_suite4199;
   public static final BitSet FOLLOW_stmt_in_suite4208;
   public static final BitSet FOLLOW_DEDENT_in_suite4228;
   public static final BitSet FOLLOW_or_test_in_test4258;
   public static final BitSet FOLLOW_IF_in_test4280;
   public static final BitSet FOLLOW_or_test_in_test4284;
   public static final BitSet FOLLOW_ORELSE_in_test4287;
   public static final BitSet FOLLOW_test_in_test4291;
   public static final BitSet FOLLOW_lambdef_in_test4336;
   public static final BitSet FOLLOW_and_test_in_or_test4371;
   public static final BitSet FOLLOW_OR_in_or_test4387;
   public static final BitSet FOLLOW_and_test_in_or_test4391;
   public static final BitSet FOLLOW_not_test_in_and_test4472;
   public static final BitSet FOLLOW_AND_in_and_test4488;
   public static final BitSet FOLLOW_not_test_in_and_test4492;
   public static final BitSet FOLLOW_NOT_in_not_test4576;
   public static final BitSet FOLLOW_not_test_in_not_test4580;
   public static final BitSet FOLLOW_comparison_in_not_test4597;
   public static final BitSet FOLLOW_expr_in_comparison4646;
   public static final BitSet FOLLOW_comp_op_in_comparison4660;
   public static final BitSet FOLLOW_expr_in_comparison4664;
   public static final BitSet FOLLOW_LESS_in_comp_op4745;
   public static final BitSet FOLLOW_GREATER_in_comp_op4761;
   public static final BitSet FOLLOW_EQUAL_in_comp_op4777;
   public static final BitSet FOLLOW_GREATEREQUAL_in_comp_op4793;
   public static final BitSet FOLLOW_LESSEQUAL_in_comp_op4809;
   public static final BitSet FOLLOW_ALT_NOTEQUAL_in_comp_op4825;
   public static final BitSet FOLLOW_NOTEQUAL_in_comp_op4841;
   public static final BitSet FOLLOW_IN_in_comp_op4857;
   public static final BitSet FOLLOW_NOT_in_comp_op4873;
   public static final BitSet FOLLOW_IN_in_comp_op4875;
   public static final BitSet FOLLOW_IS_in_comp_op4891;
   public static final BitSet FOLLOW_IS_in_comp_op4907;
   public static final BitSet FOLLOW_NOT_in_comp_op4909;
   public static final BitSet FOLLOW_xor_expr_in_expr4961;
   public static final BitSet FOLLOW_VBAR_in_expr4976;
   public static final BitSet FOLLOW_xor_expr_in_expr4980;
   public static final BitSet FOLLOW_and_expr_in_xor_expr5059;
   public static final BitSet FOLLOW_CIRCUMFLEX_in_xor_expr5074;
   public static final BitSet FOLLOW_and_expr_in_xor_expr5078;
   public static final BitSet FOLLOW_shift_expr_in_and_expr5156;
   public static final BitSet FOLLOW_AMPER_in_and_expr5171;
   public static final BitSet FOLLOW_shift_expr_in_and_expr5175;
   public static final BitSet FOLLOW_arith_expr_in_shift_expr5258;
   public static final BitSet FOLLOW_shift_op_in_shift_expr5272;
   public static final BitSet FOLLOW_arith_expr_in_shift_expr5276;
   public static final BitSet FOLLOW_LEFTSHIFT_in_shift_op5360;
   public static final BitSet FOLLOW_RIGHTSHIFT_in_shift_op5376;
   public static final BitSet FOLLOW_term_in_arith_expr5422;
   public static final BitSet FOLLOW_arith_op_in_arith_expr5435;
   public static final BitSet FOLLOW_term_in_arith_expr5439;
   public static final BitSet FOLLOW_PLUS_in_arith_op5547;
   public static final BitSet FOLLOW_MINUS_in_arith_op5563;
   public static final BitSet FOLLOW_factor_in_term5609;
   public static final BitSet FOLLOW_term_op_in_term5622;
   public static final BitSet FOLLOW_factor_in_term5626;
   public static final BitSet FOLLOW_STAR_in_term_op5708;
   public static final BitSet FOLLOW_SLASH_in_term_op5724;
   public static final BitSet FOLLOW_PERCENT_in_term_op5740;
   public static final BitSet FOLLOW_DOUBLESLASH_in_term_op5756;
   public static final BitSet FOLLOW_PLUS_in_factor5795;
   public static final BitSet FOLLOW_factor_in_factor5799;
   public static final BitSet FOLLOW_MINUS_in_factor5815;
   public static final BitSet FOLLOW_factor_in_factor5819;
   public static final BitSet FOLLOW_TILDE_in_factor5835;
   public static final BitSet FOLLOW_factor_in_factor5839;
   public static final BitSet FOLLOW_power_in_factor5855;
   public static final BitSet FOLLOW_atom_in_power5894;
   public static final BitSet FOLLOW_trailer_in_power5899;
   public static final BitSet FOLLOW_DOUBLESTAR_in_power5914;
   public static final BitSet FOLLOW_factor_in_power5916;
   public static final BitSet FOLLOW_LPAREN_in_atom5966;
   public static final BitSet FOLLOW_yield_expr_in_atom5984;
   public static final BitSet FOLLOW_testlist_gexp_in_atom6004;
   public static final BitSet FOLLOW_RPAREN_in_atom6047;
   public static final BitSet FOLLOW_LBRACK_in_atom6055;
   public static final BitSet FOLLOW_listmaker_in_atom6064;
   public static final BitSet FOLLOW_RBRACK_in_atom6107;
   public static final BitSet FOLLOW_LCURLY_in_atom6115;
   public static final BitSet FOLLOW_dictorsetmaker_in_atom6125;
   public static final BitSet FOLLOW_RCURLY_in_atom6173;
   public static final BitSet FOLLOW_BACKQUOTE_in_atom6184;
   public static final BitSet FOLLOW_testlist_in_atom6186;
   public static final BitSet FOLLOW_BACKQUOTE_in_atom6191;
   public static final BitSet FOLLOW_name_or_print_in_atom6209;
   public static final BitSet FOLLOW_INT_in_atom6227;
   public static final BitSet FOLLOW_LONGINT_in_atom6245;
   public static final BitSet FOLLOW_FLOAT_in_atom6263;
   public static final BitSet FOLLOW_COMPLEX_in_atom6281;
   public static final BitSet FOLLOW_STRING_in_atom6302;
   public static final BitSet FOLLOW_test_in_listmaker6345;
   public static final BitSet FOLLOW_list_for_in_listmaker6357;
   public static final BitSet FOLLOW_COMMA_in_listmaker6389;
   public static final BitSet FOLLOW_test_in_listmaker6393;
   public static final BitSet FOLLOW_COMMA_in_listmaker6422;
   public static final BitSet FOLLOW_test_in_testlist_gexp6454;
   public static final BitSet FOLLOW_COMMA_in_testlist_gexp6478;
   public static final BitSet FOLLOW_test_in_testlist_gexp6482;
   public static final BitSet FOLLOW_COMMA_in_testlist_gexp6490;
   public static final BitSet FOLLOW_comp_for_in_testlist_gexp6544;
   public static final BitSet FOLLOW_LAMBDA_in_lambdef6608;
   public static final BitSet FOLLOW_varargslist_in_lambdef6611;
   public static final BitSet FOLLOW_COLON_in_lambdef6615;
   public static final BitSet FOLLOW_test_in_lambdef6617;
   public static final BitSet FOLLOW_LPAREN_in_trailer6656;
   public static final BitSet FOLLOW_arglist_in_trailer6665;
   public static final BitSet FOLLOW_RPAREN_in_trailer6707;
   public static final BitSet FOLLOW_LBRACK_in_trailer6715;
   public static final BitSet FOLLOW_subscriptlist_in_trailer6717;
   public static final BitSet FOLLOW_RBRACK_in_trailer6720;
   public static final BitSet FOLLOW_DOT_in_trailer6736;
   public static final BitSet FOLLOW_attr_in_trailer6738;
   public static final BitSet FOLLOW_subscript_in_subscriptlist6777;
   public static final BitSet FOLLOW_COMMA_in_subscriptlist6789;
   public static final BitSet FOLLOW_subscript_in_subscriptlist6793;
   public static final BitSet FOLLOW_COMMA_in_subscriptlist6800;
   public static final BitSet FOLLOW_DOT_in_subscript6843;
   public static final BitSet FOLLOW_DOT_in_subscript6845;
   public static final BitSet FOLLOW_DOT_in_subscript6847;
   public static final BitSet FOLLOW_test_in_subscript6877;
   public static final BitSet FOLLOW_COLON_in_subscript6883;
   public static final BitSet FOLLOW_test_in_subscript6888;
   public static final BitSet FOLLOW_sliceop_in_subscript6894;
   public static final BitSet FOLLOW_COLON_in_subscript6925;
   public static final BitSet FOLLOW_test_in_subscript6930;
   public static final BitSet FOLLOW_sliceop_in_subscript6936;
   public static final BitSet FOLLOW_test_in_subscript6954;
   public static final BitSet FOLLOW_COLON_in_sliceop6991;
   public static final BitSet FOLLOW_test_in_sliceop6999;
   public static final BitSet FOLLOW_expr_in_exprlist7070;
   public static final BitSet FOLLOW_COMMA_in_exprlist7082;
   public static final BitSet FOLLOW_expr_in_exprlist7086;
   public static final BitSet FOLLOW_COMMA_in_exprlist7092;
   public static final BitSet FOLLOW_expr_in_exprlist7111;
   public static final BitSet FOLLOW_expr_in_del_list7149;
   public static final BitSet FOLLOW_COMMA_in_del_list7161;
   public static final BitSet FOLLOW_expr_in_del_list7165;
   public static final BitSet FOLLOW_COMMA_in_del_list7171;
   public static final BitSet FOLLOW_test_in_testlist7224;
   public static final BitSet FOLLOW_COMMA_in_testlist7236;
   public static final BitSet FOLLOW_test_in_testlist7240;
   public static final BitSet FOLLOW_COMMA_in_testlist7246;
   public static final BitSet FOLLOW_test_in_testlist7264;
   public static final BitSet FOLLOW_test_in_dictorsetmaker7297;
   public static final BitSet FOLLOW_COLON_in_dictorsetmaker7325;
   public static final BitSet FOLLOW_test_in_dictorsetmaker7329;
   public static final BitSet FOLLOW_comp_for_in_dictorsetmaker7349;
   public static final BitSet FOLLOW_COMMA_in_dictorsetmaker7396;
   public static final BitSet FOLLOW_test_in_dictorsetmaker7400;
   public static final BitSet FOLLOW_COLON_in_dictorsetmaker7403;
   public static final BitSet FOLLOW_test_in_dictorsetmaker7407;
   public static final BitSet FOLLOW_COMMA_in_dictorsetmaker7463;
   public static final BitSet FOLLOW_test_in_dictorsetmaker7467;
   public static final BitSet FOLLOW_COMMA_in_dictorsetmaker7517;
   public static final BitSet FOLLOW_comp_for_in_dictorsetmaker7532;
   public static final BitSet FOLLOW_decorators_in_classdef7585;
   public static final BitSet FOLLOW_CLASS_in_classdef7588;
   public static final BitSet FOLLOW_NAME_in_classdef7590;
   public static final BitSet FOLLOW_LPAREN_in_classdef7593;
   public static final BitSet FOLLOW_testlist_in_classdef7595;
   public static final BitSet FOLLOW_RPAREN_in_classdef7599;
   public static final BitSet FOLLOW_COLON_in_classdef7603;
   public static final BitSet FOLLOW_suite_in_classdef7605;
   public static final BitSet FOLLOW_argument_in_arglist7647;
   public static final BitSet FOLLOW_COMMA_in_arglist7651;
   public static final BitSet FOLLOW_argument_in_arglist7653;
   public static final BitSet FOLLOW_COMMA_in_arglist7669;
   public static final BitSet FOLLOW_STAR_in_arglist7687;
   public static final BitSet FOLLOW_test_in_arglist7691;
   public static final BitSet FOLLOW_COMMA_in_arglist7695;
   public static final BitSet FOLLOW_argument_in_arglist7697;
   public static final BitSet FOLLOW_COMMA_in_arglist7703;
   public static final BitSet FOLLOW_DOUBLESTAR_in_arglist7705;
   public static final BitSet FOLLOW_test_in_arglist7709;
   public static final BitSet FOLLOW_DOUBLESTAR_in_arglist7730;
   public static final BitSet FOLLOW_test_in_arglist7734;
   public static final BitSet FOLLOW_STAR_in_arglist7781;
   public static final BitSet FOLLOW_test_in_arglist7785;
   public static final BitSet FOLLOW_COMMA_in_arglist7789;
   public static final BitSet FOLLOW_argument_in_arglist7791;
   public static final BitSet FOLLOW_COMMA_in_arglist7797;
   public static final BitSet FOLLOW_DOUBLESTAR_in_arglist7799;
   public static final BitSet FOLLOW_test_in_arglist7803;
   public static final BitSet FOLLOW_DOUBLESTAR_in_arglist7822;
   public static final BitSet FOLLOW_test_in_arglist7826;
   public static final BitSet FOLLOW_test_in_argument7865;
   public static final BitSet FOLLOW_ASSIGN_in_argument7878;
   public static final BitSet FOLLOW_test_in_argument7882;
   public static final BitSet FOLLOW_comp_for_in_argument7908;
   public static final BitSet FOLLOW_list_for_in_list_iter7973;
   public static final BitSet FOLLOW_list_if_in_list_iter7982;
   public static final BitSet FOLLOW_FOR_in_list_for8008;
   public static final BitSet FOLLOW_exprlist_in_list_for8010;
   public static final BitSet FOLLOW_IN_in_list_for8013;
   public static final BitSet FOLLOW_testlist_in_list_for8015;
   public static final BitSet FOLLOW_list_iter_in_list_for8019;
   public static final BitSet FOLLOW_IF_in_list_if8049;
   public static final BitSet FOLLOW_test_in_list_if8051;
   public static final BitSet FOLLOW_list_iter_in_list_if8055;
   public static final BitSet FOLLOW_comp_for_in_comp_iter8086;
   public static final BitSet FOLLOW_comp_if_in_comp_iter8095;
   public static final BitSet FOLLOW_FOR_in_comp_for8121;
   public static final BitSet FOLLOW_exprlist_in_comp_for8123;
   public static final BitSet FOLLOW_IN_in_comp_for8126;
   public static final BitSet FOLLOW_or_test_in_comp_for8128;
   public static final BitSet FOLLOW_comp_iter_in_comp_for8131;
   public static final BitSet FOLLOW_IF_in_comp_if8160;
   public static final BitSet FOLLOW_test_in_comp_if8162;
   public static final BitSet FOLLOW_comp_iter_in_comp_if8165;
   public static final BitSet FOLLOW_YIELD_in_yield_expr8206;
   public static final BitSet FOLLOW_testlist_in_yield_expr8208;
   public static final BitSet FOLLOW_LPAREN_in_synpred1_Python1296;
   public static final BitSet FOLLOW_fpdef_in_synpred1_Python1298;
   public static final BitSet FOLLOW_COMMA_in_synpred1_Python1301;
   public static final BitSet FOLLOW_testlist_in_synpred2_Python1683;
   public static final BitSet FOLLOW_augassign_in_synpred2_Python1686;
   public static final BitSet FOLLOW_testlist_in_synpred3_Python1802;
   public static final BitSet FOLLOW_ASSIGN_in_synpred3_Python1805;
   public static final BitSet FOLLOW_test_in_synpred4_Python2317;
   public static final BitSet FOLLOW_COMMA_in_synpred4_Python2320;
   public static final BitSet FOLLOW_test_in_synpred5_Python2416;
   public static final BitSet FOLLOW_COMMA_in_synpred5_Python2419;
   public static final BitSet FOLLOW_test_in_synpred5_Python2421;
   public static final BitSet FOLLOW_decorators_in_synpred6_Python3510;
   public static final BitSet FOLLOW_DEF_in_synpred6_Python3513;
   public static final BitSet FOLLOW_IF_in_synpred7_Python4270;
   public static final BitSet FOLLOW_or_test_in_synpred7_Python4272;
   public static final BitSet FOLLOW_ORELSE_in_synpred7_Python4275;
   public static final BitSet FOLLOW_test_in_synpred8_Python6864;
   public static final BitSet FOLLOW_COLON_in_synpred8_Python6867;
   public static final BitSet FOLLOW_COLON_in_synpred9_Python6915;
   public static final BitSet FOLLOW_expr_in_synpred10_Python7060;
   public static final BitSet FOLLOW_COMMA_in_synpred10_Python7063;
   public static final BitSet FOLLOW_test_in_synpred11_Python7211;
   public static final BitSet FOLLOW_COMMA_in_synpred11_Python7214;

   public PythonParser(TokenStream input) {
      this(input, new RecognizerSharedState());
   }

   public PythonParser(TokenStream input, RecognizerSharedState state) {
      super(input, state);
      this.adaptor = new CommonTreeAdaptor();
      this.actions = new GrammarActions();
      this.printFunction = false;
      this.unicodeLiterals = false;
      this.suite_stack = new Stack();
      this.expr_stack = new Stack();
      this.dfa30 = new DFA30(this);
      this.dfa35 = new DFA35(this);
      this.dfa31 = new DFA31(this);
      this.dfa40 = new DFA40(this);
      this.dfa38 = new DFA38(this);
      this.dfa43 = new DFA43(this);
      this.dfa41 = new DFA41(this);
      this.dfa52 = new DFA52(this);
      this.dfa80 = new DFA80(this);
      this.dfa89 = new DFA89(this);
      this.dfa112 = new DFA112(this);
      this.dfa116 = new DFA116(this);
      this.dfa129 = new DFA129(this);
      this.dfa133 = new DFA133(this);
      this.dfa131 = new DFA131(this);
      this.dfa134 = new DFA134(this);
      this.dfa138 = new DFA138(this);
      this.dfa136 = new DFA136(this);
      this.dfa139 = new DFA139(this);
   }

   public void setTreeAdaptor(TreeAdaptor adaptor) {
      this.adaptor = adaptor;
   }

   public TreeAdaptor getTreeAdaptor() {
      return this.adaptor;
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "/Users/fwierzbicki/hg/jython/jython/grammar/Python.g";
   }

   public void setErrorHandler(ErrorHandler eh) {
      this.errorHandler = eh;
      this.actions.setErrorHandler(eh);
   }

   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
      Object o = this.errorHandler.recoverFromMismatchedToken(this, input, ttype, follow);
      return o != null ? o : super.recoverFromMismatchedToken(input, ttype, follow);
   }

   public PythonParser(TokenStream input, String encoding) {
      this(input);
      this.encoding = encoding;
   }

   public void reportError(RecognitionException e) {
      super.reportError(e);
      this.errorHandler.reportError(this, e);
   }

   public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
   }

   public final single_input_return single_input() throws RecognitionException {
      single_input_return retval = new single_input_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token NEWLINE1 = null;
      Token EOF2 = null;
      Token NEWLINE4 = null;
      Token EOF5 = null;
      Token NEWLINE7 = null;
      Token EOF8 = null;
      simple_stmt_return simple_stmt3 = null;
      compound_stmt_return compound_stmt6 = null;
      PythonTree NEWLINE1_tree = null;
      PythonTree EOF2_tree = null;
      PythonTree NEWLINE4_tree = null;
      PythonTree EOF5_tree = null;
      PythonTree NEWLINE7_tree = null;
      PythonTree EOF8_tree = null;
      mod mtype = null;

      try {
         try {
            int alt4 = true;
            int LA4_0 = this.input.LA(1);
            byte alt4;
            if (LA4_0 != -1 && LA4_0 != 7) {
               if (LA4_0 != 9 && LA4_0 != 32 && LA4_0 != 43 && (LA4_0 < 75 || LA4_0 > 76) && (LA4_0 < 80 || LA4_0 > 81) && LA4_0 != 83 && LA4_0 != 85) {
                  if (LA4_0 != 11 || !this.printFunction && this.printFunction) {
                     if ((LA4_0 < 14 || LA4_0 > 15) && LA4_0 != 17 && LA4_0 != 19 && LA4_0 != 22 && LA4_0 != 24 && LA4_0 != 26 && LA4_0 != 28 && LA4_0 != 31 && (LA4_0 < 35 || LA4_0 > 37) && LA4_0 != 41 && (LA4_0 < 86 || LA4_0 > 90)) {
                        if (LA4_0 != 16 && LA4_0 != 18 && LA4_0 != 25 && LA4_0 != 27 && (LA4_0 < 38 || LA4_0 > 40) && LA4_0 != 42) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           NoViableAltException nvae = new NoViableAltException("", 4, 0, this.input);
                           throw nvae;
                        }

                        alt4 = 3;
                     } else {
                        alt4 = 2;
                     }
                  } else {
                     alt4 = 2;
                  }
               } else {
                  alt4 = 2;
               }
            } else {
               alt4 = 1;
            }

            int alt3;
            byte alt2;
            label479:
            switch (alt4) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();

                  while(true) {
                     alt2 = 2;
                     alt3 = this.input.LA(1);
                     if (alt3 == 7) {
                        alt2 = 1;
                     }

                     switch (alt2) {
                        case 1:
                           NEWLINE1 = (Token)this.match(this.input, 7, FOLLOW_NEWLINE_in_single_input118);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              NEWLINE1_tree = (PythonTree)this.adaptor.create(NEWLINE1);
                              this.adaptor.addChild(root_0, NEWLINE1_tree);
                           }
                           break;
                        default:
                           EOF2 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_single_input121);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              EOF2_tree = (PythonTree)this.adaptor.create(EOF2);
                              this.adaptor.addChild(root_0, EOF2_tree);
                           }

                           if (this.state.backtracking == 0) {
                              mtype = new Interactive(retval.start, new ArrayList());
                           }
                           break label479;
                     }
                  }
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_simple_stmt_in_single_input137);
                  simple_stmt3 = this.simple_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, simple_stmt3.getTree());
                  }

                  while(true) {
                     alt2 = 2;
                     alt3 = this.input.LA(1);
                     if (alt3 == 7) {
                        alt2 = 1;
                     }

                     switch (alt2) {
                        case 1:
                           NEWLINE4 = (Token)this.match(this.input, 7, FOLLOW_NEWLINE_in_single_input139);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              NEWLINE4_tree = (PythonTree)this.adaptor.create(NEWLINE4);
                              this.adaptor.addChild(root_0, NEWLINE4_tree);
                           }
                           break;
                        default:
                           EOF5 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_single_input142);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              EOF5_tree = (PythonTree)this.adaptor.create(EOF5);
                              this.adaptor.addChild(root_0, EOF5_tree);
                           }

                           if (this.state.backtracking == 0) {
                              mtype = new Interactive(retval.start, this.actions.castStmts(simple_stmt3 != null ? simple_stmt3.stypes : null));
                           }
                           break label479;
                     }
                  }
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_compound_stmt_in_single_input158);
                  compound_stmt6 = this.compound_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, compound_stmt6.getTree());
                  }

                  int cnt3 = 0;

                  label469:
                  while(true) {
                     alt3 = 2;
                     int LA3_0 = this.input.LA(1);
                     if (LA3_0 == 7) {
                        alt3 = 1;
                     }

                     switch (alt3) {
                        case 1:
                           NEWLINE7 = (Token)this.match(this.input, 7, FOLLOW_NEWLINE_in_single_input160);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              NEWLINE7_tree = (PythonTree)this.adaptor.create(NEWLINE7);
                              this.adaptor.addChild(root_0, NEWLINE7_tree);
                           }

                           ++cnt3;
                           break;
                        default:
                           if (cnt3 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(3, this.input);
                              throw eee;
                           }

                           EOF8 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_single_input163);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              EOF8_tree = (PythonTree)this.adaptor.create(EOF8);
                              this.adaptor.addChild(root_0, EOF8_tree);
                           }

                           if (this.state.backtracking == 0) {
                              mtype = new Interactive(retval.start, this.actions.castStmts(compound_stmt6 != null ? compound_stmt6.tree : null));
                           }
                           break label469;
                     }
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = mtype;
            }
         } catch (RecognitionException var27) {
            this.reportError(var27);
            this.errorHandler.recover(this, this.input, var27);
            PythonTree badNode = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var27);
            retval.tree = new ErrorMod(badNode);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final file_input_return file_input() throws RecognitionException {
      file_input_return retval = new file_input_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token NEWLINE9 = null;
      Token EOF11 = null;
      stmt_return stmt10 = null;
      PythonTree NEWLINE9_tree = null;
      PythonTree EOF11_tree = null;
      mod mtype = null;
      List stypes = new ArrayList();

      try {
         root_0 = (PythonTree)this.adaptor.nil();

         while(true) {
            int alt5 = 3;
            int LA5_0 = this.input.LA(1);
            if (LA5_0 == 7) {
               alt5 = 1;
            } else if (LA5_0 != 9 && LA5_0 != 32 && LA5_0 != 43 && (LA5_0 < 75 || LA5_0 > 76) && (LA5_0 < 80 || LA5_0 > 81) && LA5_0 != 83 && LA5_0 != 85) {
               if (LA5_0 != 11 || !this.printFunction && this.printFunction) {
                  if (LA5_0 >= 14 && LA5_0 <= 19 || LA5_0 == 22 || LA5_0 >= 24 && LA5_0 <= 28 || LA5_0 == 31 || LA5_0 >= 35 && LA5_0 <= 42 || LA5_0 >= 86 && LA5_0 <= 90) {
                     alt5 = 2;
                  }
               } else {
                  alt5 = 2;
               }
            } else {
               alt5 = 2;
            }

            switch (alt5) {
               case 1:
                  NEWLINE9 = (Token)this.match(this.input, 7, FOLLOW_NEWLINE_in_file_input215);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     NEWLINE9_tree = (PythonTree)this.adaptor.create(NEWLINE9);
                     this.adaptor.addChild(root_0, NEWLINE9_tree);
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_stmt_in_file_input225);
                  stmt10 = this.stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, stmt10.getTree());
                  }

                  if (this.state.backtracking == 0 && (stmt10 != null ? stmt10.stypes : null) != null) {
                     stypes.addAll(stmt10 != null ? stmt10.stypes : null);
                  }
                  break;
               default:
                  EOF11 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_file_input244);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     EOF11_tree = (PythonTree)this.adaptor.create(EOF11);
                     this.adaptor.addChild(root_0, EOF11_tree);
                  }

                  if (this.state.backtracking == 0) {
                     mtype = new Module(retval.start, this.actions.castStmts((List)stypes));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     if (!stypes.isEmpty()) {
                        PythonTree stop = (PythonTree)stypes.get(stypes.size() - 1);
                        mtype.setCharStopIndex(stop.getCharStopIndex());
                        mtype.setTokenStopIndex(stop.getTokenStopIndex());
                     }

                     retval.tree = mtype;
                  }

                  return retval;
            }
         }
      } catch (RecognitionException var16) {
         this.reportError(var16);
         this.errorHandler.recover(this, this.input, var16);
         PythonTree badNode = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         retval.tree = new ErrorMod(badNode);
         return retval;
      } finally {
         ;
      }
   }

   public final eval_input_return eval_input() throws RecognitionException {
      eval_input_return retval = new eval_input_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token LEADING_WS12 = null;
      Token NEWLINE13 = null;
      Token NEWLINE15 = null;
      Token EOF16 = null;
      testlist_return testlist14 = null;
      PythonTree LEADING_WS12_tree = null;
      PythonTree NEWLINE13_tree = null;
      PythonTree NEWLINE15_tree = null;
      PythonTree EOF16_tree = null;
      mod mtype = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         int alt6 = 2;
         int LA6_0 = this.input.LA(1);
         if (LA6_0 == 8) {
            alt6 = 1;
         }

         switch (alt6) {
            case 1:
               LEADING_WS12 = (Token)this.match(this.input, 8, FOLLOW_LEADING_WS_in_eval_input298);
               if (this.state.failed) {
                  return retval;
               }

               if (this.state.backtracking == 0) {
                  LEADING_WS12_tree = (PythonTree)this.adaptor.create(LEADING_WS12);
                  this.adaptor.addChild(root_0, LEADING_WS12_tree);
               }
         }

         while(true) {
            int alt8 = 2;
            int LA8_0 = this.input.LA(1);
            if (LA8_0 == 7) {
               alt8 = 1;
            }

            switch (alt8) {
               case 1:
                  NEWLINE13 = (Token)this.match(this.input, 7, FOLLOW_NEWLINE_in_eval_input302);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     NEWLINE13_tree = (PythonTree)this.adaptor.create(NEWLINE13);
                     this.adaptor.addChild(root_0, NEWLINE13_tree);
                  }
                  break;
               default:
                  this.pushFollow(FOLLOW_testlist_in_eval_input306);
                  testlist14 = this.testlist(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, testlist14.getTree());
                  }

                  while(true) {
                     alt8 = 2;
                     LA8_0 = this.input.LA(1);
                     if (LA8_0 == 7) {
                        alt8 = 1;
                     }

                     switch (alt8) {
                        case 1:
                           NEWLINE15 = (Token)this.match(this.input, 7, FOLLOW_NEWLINE_in_eval_input310);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              NEWLINE15_tree = (PythonTree)this.adaptor.create(NEWLINE15);
                              this.adaptor.addChild(root_0, NEWLINE15_tree);
                           }
                           break;
                        default:
                           EOF16 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_eval_input314);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              EOF16_tree = (PythonTree)this.adaptor.create(EOF16);
                              this.adaptor.addChild(root_0, EOF16_tree);
                           }

                           if (this.state.backtracking == 0) {
                              mtype = new Expression(retval.start, this.actions.castExpr(testlist14 != null ? testlist14.tree : null));
                           }

                           retval.stop = this.input.LT(-1);
                           if (this.state.backtracking == 0) {
                              retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                              this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                           }

                           if (this.state.backtracking == 0) {
                              retval.tree = mtype;
                           }

                           return retval;
                     }
                  }
            }
         }
      } catch (RecognitionException var21) {
         this.reportError(var21);
         this.errorHandler.recover(this, this.input, var21);
         PythonTree badNode = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var21);
         retval.tree = new ErrorMod(badNode);
         return retval;
      } finally {
         ;
      }
   }

   public final dotted_attr_return dotted_attr() throws RecognitionException {
      dotted_attr_return retval = new dotted_attr_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token n1 = null;
      Token DOT17 = null;
      Token n2 = null;
      List list_n2 = null;
      PythonTree n1_tree = null;
      PythonTree DOT17_tree = null;
      PythonTree n2_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            n1 = (Token)this.match(this.input, 9, FOLLOW_NAME_in_dotted_attr366);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               n1_tree = (PythonTree)this.adaptor.create(n1);
               this.adaptor.addChild(root_0, n1_tree);
            }

            int alt10 = true;
            int LA10_0 = this.input.LA(1);
            byte alt10;
            if (LA10_0 == 10) {
               alt10 = 1;
            } else {
               if (LA10_0 != 7 && LA10_0 != 43) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
                  throw nvae;
               }

               alt10 = 2;
            }

            label193:
            switch (alt10) {
               case 1:
                  int cnt9 = 0;

                  while(true) {
                     int alt9 = 2;
                     int LA9_0 = this.input.LA(1);
                     if (LA9_0 == 10) {
                        alt9 = 1;
                     }

                     switch (alt9) {
                        case 1:
                           DOT17 = (Token)this.match(this.input, 10, FOLLOW_DOT_in_dotted_attr377);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              DOT17_tree = (PythonTree)this.adaptor.create(DOT17);
                              this.adaptor.addChild(root_0, DOT17_tree);
                           }

                           n2 = (Token)this.match(this.input, 9, FOLLOW_NAME_in_dotted_attr381);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              n2_tree = (PythonTree)this.adaptor.create(n2);
                              this.adaptor.addChild(root_0, n2_tree);
                           }

                           if (list_n2 == null) {
                              list_n2 = new ArrayList();
                           }

                           list_n2.add(n2);
                           ++cnt9;
                           break;
                        default:
                           if (cnt9 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(9, this.input);
                              throw eee;
                           }

                           if (this.state.backtracking == 0) {
                              retval.etype = this.actions.makeDottedAttr(n1, list_n2);
                           }
                           break label193;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.etype = this.actions.makeNameNode(n1);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var19) {
            this.reportError(var19);
            this.errorHandler.recover(this, this.input, var19);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var19);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final name_or_print_return name_or_print() throws RecognitionException {
      name_or_print_return retval = new name_or_print_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token NAME18 = null;
      Token PRINT19 = null;
      PythonTree NAME18_tree = null;
      PythonTree PRINT19_tree = null;

      try {
         try {
            int alt11 = true;
            int LA11_0 = this.input.LA(1);
            byte alt11;
            if (LA11_0 == 9) {
               alt11 = 1;
            } else {
               if (LA11_0 != 11 || !this.printFunction) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
                  throw nvae;
               }

               alt11 = 2;
            }

            switch (alt11) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  NAME18 = (Token)this.match(this.input, 9, FOLLOW_NAME_in_name_or_print446);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     NAME18_tree = (PythonTree)this.adaptor.create(NAME18);
                     this.adaptor.addChild(root_0, NAME18_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tok = retval.start;
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  if (!this.printFunction) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     throw new FailedPredicateException(this.input, "name_or_print", "printFunction");
                  }

                  PRINT19 = (Token)this.match(this.input, 11, FOLLOW_PRINT_in_name_or_print460);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     PRINT19_tree = (PythonTree)this.adaptor.create(PRINT19);
                     this.adaptor.addChild(root_0, PRINT19_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tok = retval.start;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.errorHandler.recover(this, this.input, var13);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final attr_return attr() throws RecognitionException {
      attr_return retval = new attr_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token set20 = null;
      PythonTree set20_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            set20 = this.input.LT(1);
            if (this.input.LA(1) != 9 && (this.input.LA(1) < 11 || this.input.LA(1) > 41)) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return retval;
               }

               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               throw mse;
            }

            this.input.consume();
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, (PythonTree)this.adaptor.create(set20));
            }

            this.state.errorRecovery = false;
            this.state.failed = false;
            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.errorHandler.recover(this, this.input, var9);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var9);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final decorator_return decorator() throws RecognitionException {
      decorator_return retval = new decorator_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token AT21 = null;
      Token LPAREN23 = null;
      Token RPAREN25 = null;
      Token NEWLINE26 = null;
      dotted_attr_return dotted_attr22 = null;
      arglist_return arglist24 = null;
      PythonTree AT21_tree = null;
      PythonTree LPAREN23_tree = null;
      PythonTree RPAREN25_tree = null;
      PythonTree NEWLINE26_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            AT21 = (Token)this.match(this.input, 42, FOLLOW_AT_in_decorator762);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               AT21_tree = (PythonTree)this.adaptor.create(AT21);
               this.adaptor.addChild(root_0, AT21_tree);
            }

            this.pushFollow(FOLLOW_dotted_attr_in_decorator764);
            dotted_attr22 = this.dotted_attr();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, dotted_attr22.getTree());
            }

            int alt13 = true;
            int LA13_0 = this.input.LA(1);
            byte alt13;
            if (LA13_0 == 43) {
               alt13 = 1;
            } else {
               if (LA13_0 != 7) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);
                  throw nvae;
               }

               alt13 = 2;
            }

            switch (alt13) {
               case 1:
                  LPAREN23 = (Token)this.match(this.input, 43, FOLLOW_LPAREN_in_decorator772);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LPAREN23_tree = (PythonTree)this.adaptor.create(LPAREN23);
                     this.adaptor.addChild(root_0, LPAREN23_tree);
                  }

                  int alt12 = true;
                  int LA12_0 = this.input.LA(1);
                  byte alt12;
                  if (LA12_0 != 9 && LA12_0 != 32 && LA12_0 != 43 && (LA12_0 < 75 || LA12_0 > 76) && (LA12_0 < 80 || LA12_0 > 81) && LA12_0 != 83 && LA12_0 != 85) {
                     if (LA12_0 == 11 && this.printFunction) {
                        alt12 = 1;
                     } else if (LA12_0 == 31 || LA12_0 >= 48 && LA12_0 <= 49 || LA12_0 >= 86 && LA12_0 <= 90) {
                        alt12 = 1;
                     } else {
                        if (LA12_0 != 44) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
                           throw nvae;
                        }

                        alt12 = 2;
                     }
                  } else {
                     alt12 = 1;
                  }

                  switch (alt12) {
                     case 1:
                        this.pushFollow(FOLLOW_arglist_in_decorator782);
                        arglist24 = this.arglist();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, arglist24.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.etype = this.actions.makeCall(LPAREN23, dotted_attr22 != null ? dotted_attr22.etype : null, arglist24 != null ? arglist24.args : null, arglist24 != null ? arglist24.keywords : null, arglist24 != null ? arglist24.starargs : null, arglist24 != null ? arglist24.kwargs : null);
                        }
                        break;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.etype = this.actions.makeCall(LPAREN23, dotted_attr22 != null ? dotted_attr22.etype : null);
                        }
                  }

                  RPAREN25 = (Token)this.match(this.input, 44, FOLLOW_RPAREN_in_decorator826);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RPAREN25_tree = (PythonTree)this.adaptor.create(RPAREN25);
                     this.adaptor.addChild(root_0, RPAREN25_tree);
                  }
                  break;
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.etype = dotted_attr22 != null ? dotted_attr22.etype : null;
                  }
            }

            NEWLINE26 = (Token)this.match(this.input, 7, FOLLOW_NEWLINE_in_decorator848);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               NEWLINE26_tree = (PythonTree)this.adaptor.create(NEWLINE26);
               this.adaptor.addChild(root_0, NEWLINE26_tree);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = retval.etype;
            }
         } catch (RecognitionException var21) {
            this.reportError(var21);
            this.errorHandler.recover(this, this.input, var21);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var21);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final decorators_return decorators() throws RecognitionException {
      decorators_return retval = new decorators_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      List list_d = null;
      decorator_return d = null;
      d = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         int cnt14 = 0;

         while(true) {
            int alt14 = 2;
            int LA14_0 = this.input.LA(1);
            if (LA14_0 == 42) {
               alt14 = 1;
            }

            switch (alt14) {
               case 1:
                  this.pushFollow(FOLLOW_decorator_in_decorators876);
                  d = this.decorator();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, d.getTree());
                  }

                  if (list_d == null) {
                     list_d = new ArrayList();
                  }

                  list_d.add(d.getTree());
                  ++cnt14;
                  break;
               default:
                  if (cnt14 < 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     EarlyExitException eee = new EarlyExitException(14, this.input);
                     throw eee;
                  }

                  if (this.state.backtracking == 0) {
                     retval.etypes = list_d;
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  return retval;
            }
         }
      } catch (RecognitionException var12) {
         this.reportError(var12);
         this.errorHandler.recover(this, this.input, var12);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var12);
         return retval;
      } finally {
         ;
      }
   }

   public final funcdef_return funcdef() throws RecognitionException {
      funcdef_return retval = new funcdef_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token DEF28 = null;
      Token COLON31 = null;
      decorators_return decorators27 = null;
      name_or_print_return name_or_print29 = null;
      parameters_return parameters30 = null;
      suite_return suite32 = null;
      PythonTree DEF28_tree = null;
      PythonTree COLON31_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            int alt15 = 2;
            int LA15_0 = this.input.LA(1);
            if (LA15_0 == 42) {
               alt15 = 1;
            }

            switch (alt15) {
               case 1:
                  this.pushFollow(FOLLOW_decorators_in_funcdef914);
                  decorators27 = this.decorators();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, decorators27.getTree());
                  }
               default:
                  DEF28 = (Token)this.match(this.input, 18, FOLLOW_DEF_in_funcdef917);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     DEF28_tree = (PythonTree)this.adaptor.create(DEF28);
                     this.adaptor.addChild(root_0, DEF28_tree);
                  }

                  this.pushFollow(FOLLOW_name_or_print_in_funcdef919);
                  name_or_print29 = this.name_or_print();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, name_or_print29.getTree());
                  }

                  this.pushFollow(FOLLOW_parameters_in_funcdef921);
                  parameters30 = this.parameters();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, parameters30.getTree());
                  }

                  COLON31 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_funcdef923);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     COLON31_tree = (PythonTree)this.adaptor.create(COLON31);
                     this.adaptor.addChild(root_0, COLON31_tree);
                  }

                  this.pushFollow(FOLLOW_suite_in_funcdef925);
                  suite32 = this.suite(false);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, suite32.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     Token t = DEF28;
                     if ((decorators27 != null ? decorators27.start : null) != null) {
                        t = decorators27 != null ? decorators27.start : null;
                     }

                     stype = this.actions.makeFuncdef(t, name_or_print29 != null ? name_or_print29.start : null, parameters30 != null ? parameters30.args : null, suite32 != null ? suite32.stypes : null, decorators27 != null ? decorators27.etypes : null);
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = stype;
                  }
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.errorHandler.recover(this, this.input, var18);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final parameters_return parameters() throws RecognitionException {
      parameters_return retval = new parameters_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token LPAREN33 = null;
      Token RPAREN35 = null;
      varargslist_return varargslist34 = null;
      PythonTree LPAREN33_tree = null;
      PythonTree RPAREN35_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            LPAREN33 = (Token)this.match(this.input, 43, FOLLOW_LPAREN_in_parameters958);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               LPAREN33_tree = (PythonTree)this.adaptor.create(LPAREN33);
               this.adaptor.addChild(root_0, LPAREN33_tree);
            }

            int alt16 = true;
            int LA16_0 = this.input.LA(1);
            byte alt16;
            if (LA16_0 == 9 || LA16_0 == 43 || LA16_0 >= 48 && LA16_0 <= 49) {
               alt16 = 1;
            } else {
               if (LA16_0 != 44) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
                  throw nvae;
               }

               alt16 = 2;
            }

            switch (alt16) {
               case 1:
                  this.pushFollow(FOLLOW_varargslist_in_parameters967);
                  varargslist34 = this.varargslist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, varargslist34.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.args = varargslist34 != null ? varargslist34.args : null;
                  }
                  break;
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.args = new arguments(retval.start, new ArrayList(), (Name)null, (Name)null, new ArrayList());
                  }
            }

            RPAREN35 = (Token)this.match(this.input, 44, FOLLOW_RPAREN_in_parameters1011);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               RPAREN35_tree = (PythonTree)this.adaptor.create(RPAREN35);
               this.adaptor.addChild(root_0, RPAREN35_tree);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.errorHandler.recover(this, this.input, var14);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var14);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final defparameter_return defparameter(List defaults) throws RecognitionException {
      defparameter_return retval = new defparameter_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token ASSIGN37 = null;
      fpdef_return fpdef36 = null;
      test_return test38 = null;
      PythonTree ASSIGN37_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_fpdef_in_defparameter1044);
            fpdef36 = this.fpdef(expr_contextType.Param);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, fpdef36.getTree());
            }

            int alt17 = 2;
            int LA17_0 = this.input.LA(1);
            if (LA17_0 == 46) {
               alt17 = 1;
            }

            switch (alt17) {
               case 1:
                  ASSIGN37 = (Token)this.match(this.input, 46, FOLLOW_ASSIGN_in_defparameter1048);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ASSIGN37_tree = (PythonTree)this.adaptor.create(ASSIGN37);
                     this.adaptor.addChild(root_0, ASSIGN37_tree);
                  }

                  this.pushFollow(FOLLOW_test_in_defparameter1050);
                  test38 = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, test38.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     retval.etype = this.actions.castExpr(fpdef36 != null ? fpdef36.tree : null);
                     if (ASSIGN37 != null) {
                        defaults.add(test38 != null ? test38.tree : null);
                     } else if (!defaults.isEmpty()) {
                        throw new ParseException("non-default argument follows default argument", fpdef36 != null ? fpdef36.tree : null);
                     }
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = retval.etype;
                  }
            }
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.errorHandler.recover(this, this.input, var14);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var14);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final varargslist_return varargslist() throws RecognitionException {
      varargslist_return retval = new varargslist_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token starargs = null;
      Token kwargs = null;
      Token COMMA39 = null;
      Token COMMA40 = null;
      Token STAR41 = null;
      Token COMMA42 = null;
      Token DOUBLESTAR43 = null;
      Token DOUBLESTAR44 = null;
      Token STAR45 = null;
      Token COMMA46 = null;
      Token DOUBLESTAR47 = null;
      Token DOUBLESTAR48 = null;
      List list_d = null;
      defparameter_return d = null;
      d = null;
      PythonTree starargs_tree = null;
      PythonTree kwargs_tree = null;
      PythonTree COMMA39_tree = null;
      PythonTree COMMA40_tree = null;
      PythonTree STAR41_tree = null;
      PythonTree COMMA42_tree = null;
      PythonTree DOUBLESTAR43_tree = null;
      PythonTree DOUBLESTAR44_tree = null;
      PythonTree STAR45_tree = null;
      PythonTree COMMA46_tree = null;
      PythonTree DOUBLESTAR47_tree = null;
      PythonTree DOUBLESTAR48_tree = null;
      List defaults = new ArrayList();

      try {
         try {
            int alt23 = true;
            byte alt23;
            switch (this.input.LA(1)) {
               case 9:
               case 43:
                  alt23 = 1;
                  break;
               case 48:
                  alt23 = 2;
                  break;
               case 49:
                  alt23 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 23, 0, this.input);
                  throw nvae;
            }

            byte alt21;
            int LA21_0;
            label537:
            switch (alt23) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_defparameter_in_varargslist1096);
                  d = this.defparameter(defaults);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, d.getTree());
                  }

                  if (list_d == null) {
                     list_d = new ArrayList();
                  }

                  list_d.add(d.getTree());

                  while(true) {
                     alt21 = 2;
                     LA21_0 = this.input.LA(1);
                     if (LA21_0 == 47) {
                        int LA18_1 = this.input.LA(2);
                        if (LA18_1 == 9 || LA18_1 == 43) {
                           alt21 = 1;
                        }
                     }

                     switch (alt21) {
                        case 1:
                           COMMA39 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_varargslist1107);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              COMMA39_tree = (PythonTree)this.adaptor.create(COMMA39);
                              this.adaptor.addChild(root_0, COMMA39_tree);
                           }

                           this.pushFollow(FOLLOW_defparameter_in_varargslist1111);
                           d = this.defparameter(defaults);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, d.getTree());
                           }

                           if (list_d == null) {
                              list_d = new ArrayList();
                           }

                           list_d.add(d.getTree());
                           break;
                        default:
                           alt21 = 2;
                           LA21_0 = this.input.LA(1);
                           if (LA21_0 == 47) {
                              alt21 = 1;
                           }

                           switch (alt21) {
                              case 1:
                                 COMMA40 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_varargslist1123);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    COMMA40_tree = (PythonTree)this.adaptor.create(COMMA40);
                                    this.adaptor.addChild(root_0, COMMA40_tree);
                                 }

                                 int alt20 = 3;
                                 int LA20_0 = this.input.LA(1);
                                 if (LA20_0 == 48) {
                                    alt20 = 1;
                                 } else if (LA20_0 == 49) {
                                    alt20 = 2;
                                 }

                                 label503:
                                 switch (alt20) {
                                    case 1:
                                       STAR41 = (Token)this.match(this.input, 48, FOLLOW_STAR_in_varargslist1136);
                                       if (this.state.failed) {
                                          return retval;
                                       }

                                       if (this.state.backtracking == 0) {
                                          STAR41_tree = (PythonTree)this.adaptor.create(STAR41);
                                          this.adaptor.addChild(root_0, STAR41_tree);
                                       }

                                       starargs = (Token)this.match(this.input, 9, FOLLOW_NAME_in_varargslist1140);
                                       if (this.state.failed) {
                                          return retval;
                                       }

                                       if (this.state.backtracking == 0) {
                                          starargs_tree = (PythonTree)this.adaptor.create(starargs);
                                          this.adaptor.addChild(root_0, starargs_tree);
                                       }

                                       int alt19 = 2;
                                       int LA19_0 = this.input.LA(1);
                                       if (LA19_0 == 47) {
                                          alt19 = 1;
                                       }

                                       switch (alt19) {
                                          case 1:
                                             COMMA42 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_varargslist1143);
                                             if (this.state.failed) {
                                                return retval;
                                             }

                                             if (this.state.backtracking == 0) {
                                                COMMA42_tree = (PythonTree)this.adaptor.create(COMMA42);
                                                this.adaptor.addChild(root_0, COMMA42_tree);
                                             }

                                             DOUBLESTAR43 = (Token)this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_varargslist1145);
                                             if (this.state.failed) {
                                                return retval;
                                             }

                                             if (this.state.backtracking == 0) {
                                                DOUBLESTAR43_tree = (PythonTree)this.adaptor.create(DOUBLESTAR43);
                                                this.adaptor.addChild(root_0, DOUBLESTAR43_tree);
                                             }

                                             kwargs = (Token)this.match(this.input, 9, FOLLOW_NAME_in_varargslist1149);
                                             if (this.state.failed) {
                                                return retval;
                                             }

                                             if (this.state.backtracking == 0) {
                                                kwargs_tree = (PythonTree)this.adaptor.create(kwargs);
                                                this.adaptor.addChild(root_0, kwargs_tree);
                                             }
                                          default:
                                             break label503;
                                       }
                                    case 2:
                                       DOUBLESTAR44 = (Token)this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_varargslist1165);
                                       if (this.state.failed) {
                                          return retval;
                                       }

                                       if (this.state.backtracking == 0) {
                                          DOUBLESTAR44_tree = (PythonTree)this.adaptor.create(DOUBLESTAR44);
                                          this.adaptor.addChild(root_0, DOUBLESTAR44_tree);
                                       }

                                       kwargs = (Token)this.match(this.input, 9, FOLLOW_NAME_in_varargslist1169);
                                       if (this.state.failed) {
                                          return retval;
                                       }

                                       if (this.state.backtracking == 0) {
                                          kwargs_tree = (PythonTree)this.adaptor.create(kwargs);
                                          this.adaptor.addChild(root_0, kwargs_tree);
                                       }
                                 }
                              default:
                                 if (this.state.backtracking == 0) {
                                    retval.args = this.actions.makeArgumentsType(retval.start, list_d, starargs, kwargs, defaults);
                                 }
                                 break label537;
                           }
                     }
                  }
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  STAR45 = (Token)this.match(this.input, 48, FOLLOW_STAR_in_varargslist1207);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     STAR45_tree = (PythonTree)this.adaptor.create(STAR45);
                     this.adaptor.addChild(root_0, STAR45_tree);
                  }

                  starargs = (Token)this.match(this.input, 9, FOLLOW_NAME_in_varargslist1211);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     starargs_tree = (PythonTree)this.adaptor.create(starargs);
                     this.adaptor.addChild(root_0, starargs_tree);
                  }

                  alt21 = 2;
                  LA21_0 = this.input.LA(1);
                  if (LA21_0 == 47) {
                     alt21 = 1;
                  }

                  switch (alt21) {
                     case 1:
                        COMMA46 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_varargslist1214);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           COMMA46_tree = (PythonTree)this.adaptor.create(COMMA46);
                           this.adaptor.addChild(root_0, COMMA46_tree);
                        }

                        DOUBLESTAR47 = (Token)this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_varargslist1216);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           DOUBLESTAR47_tree = (PythonTree)this.adaptor.create(DOUBLESTAR47);
                           this.adaptor.addChild(root_0, DOUBLESTAR47_tree);
                        }

                        kwargs = (Token)this.match(this.input, 9, FOLLOW_NAME_in_varargslist1220);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           kwargs_tree = (PythonTree)this.adaptor.create(kwargs);
                           this.adaptor.addChild(root_0, kwargs_tree);
                        }
                     default:
                        if (this.state.backtracking == 0) {
                           retval.args = this.actions.makeArgumentsType(retval.start, list_d, starargs, kwargs, defaults);
                        }
                        break label537;
                  }
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  DOUBLESTAR48 = (Token)this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_varargslist1238);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     DOUBLESTAR48_tree = (PythonTree)this.adaptor.create(DOUBLESTAR48);
                     this.adaptor.addChild(root_0, DOUBLESTAR48_tree);
                  }

                  kwargs = (Token)this.match(this.input, 9, FOLLOW_NAME_in_varargslist1242);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     kwargs_tree = (PythonTree)this.adaptor.create(kwargs);
                     this.adaptor.addChild(root_0, kwargs_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.args = this.actions.makeArgumentsType(retval.start, list_d, (Token)null, kwargs, defaults);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var41) {
            this.reportError(var41);
            this.errorHandler.recover(this, this.input, var41);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var41);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final fpdef_return fpdef(expr_contextType ctype) throws RecognitionException {
      fpdef_return retval = new fpdef_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token NAME49 = null;
      Token LPAREN50 = null;
      Token RPAREN52 = null;
      Token LPAREN53 = null;
      Token RPAREN55 = null;
      fplist_return fplist51 = null;
      fplist_return fplist54 = null;
      PythonTree NAME49_tree = null;
      PythonTree LPAREN50_tree = null;
      PythonTree RPAREN52_tree = null;
      PythonTree LPAREN53_tree = null;
      PythonTree RPAREN55_tree = null;
      expr etype = null;

      try {
         try {
            int alt24 = true;
            int LA24_0 = this.input.LA(1);
            byte alt24;
            if (LA24_0 == 9) {
               alt24 = 1;
            } else {
               if (LA24_0 != 43) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 24, 0, this.input);
                  throw nvae;
               }

               int LA24_2 = this.input.LA(2);
               if (this.synpred1_Python()) {
                  alt24 = 2;
               } else {
                  alt24 = 3;
               }
            }

            switch (alt24) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  NAME49 = (Token)this.match(this.input, 9, FOLLOW_NAME_in_fpdef1279);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     NAME49_tree = (PythonTree)this.adaptor.create(NAME49);
                     this.adaptor.addChild(root_0, NAME49_tree);
                  }

                  if (this.state.backtracking == 0) {
                     etype = new Name(NAME49, NAME49 != null ? NAME49.getText() : null, ctype);
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  LPAREN50 = (Token)this.match(this.input, 43, FOLLOW_LPAREN_in_fpdef1306);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LPAREN50_tree = (PythonTree)this.adaptor.create(LPAREN50);
                     this.adaptor.addChild(root_0, LPAREN50_tree);
                  }

                  this.pushFollow(FOLLOW_fplist_in_fpdef1308);
                  fplist51 = this.fplist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, fplist51.getTree());
                  }

                  RPAREN52 = (Token)this.match(this.input, 44, FOLLOW_RPAREN_in_fpdef1310);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RPAREN52_tree = (PythonTree)this.adaptor.create(RPAREN52);
                     this.adaptor.addChild(root_0, RPAREN52_tree);
                  }

                  if (this.state.backtracking == 0) {
                     etype = new Tuple(fplist51 != null ? fplist51.start : null, this.actions.castExprs(fplist51 != null ? fplist51.etypes : null), expr_contextType.Store);
                  }
                  break;
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  LPAREN53 = (Token)this.match(this.input, 43, FOLLOW_LPAREN_in_fpdef1326);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_fplist_in_fpdef1329);
                  fplist54 = this.fplist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, fplist54.getTree());
                  }

                  RPAREN55 = (Token)this.match(this.input, 44, FOLLOW_RPAREN_in_fpdef1331);
                  if (this.state.failed) {
                     return retval;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               if (etype != null) {
                  retval.tree = (PythonTree)etype;
               }

               this.actions.checkAssign(this.actions.castExpr(retval.tree));
            }
         } catch (RecognitionException var23) {
            this.reportError(var23);
            this.errorHandler.recover(this, this.input, var23);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var23);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final fplist_return fplist() throws RecognitionException {
      fplist_return retval = new fplist_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token COMMA56 = null;
      Token COMMA57 = null;
      List list_f = null;
      fpdef_return f = null;
      f = null;
      PythonTree COMMA56_tree = null;
      PythonTree COMMA57_tree = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_fpdef_in_fplist1360);
         f = this.fpdef(expr_contextType.Store);
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, f.getTree());
            }

            if (list_f == null) {
               list_f = new ArrayList();
            }

            list_f.add(f.getTree());

            while(true) {
               int alt26 = 2;
               int LA26_0 = this.input.LA(1);
               if (LA26_0 == 47) {
                  int LA25_1 = this.input.LA(2);
                  if (LA25_1 == 9 || LA25_1 == 43) {
                     alt26 = 1;
                  }
               }

               switch (alt26) {
                  case 1:
                     COMMA56 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_fplist1377);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        COMMA56_tree = (PythonTree)this.adaptor.create(COMMA56);
                        this.adaptor.addChild(root_0, COMMA56_tree);
                     }

                     this.pushFollow(FOLLOW_fpdef_in_fplist1381);
                     f = this.fpdef(expr_contextType.Store);
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, f.getTree());
                     }

                     if (list_f == null) {
                        list_f = new ArrayList();
                     }

                     list_f.add(f.getTree());
                     break;
                  default:
                     alt26 = 2;
                     LA26_0 = this.input.LA(1);
                     if (LA26_0 == 47) {
                        alt26 = 1;
                     }

                     switch (alt26) {
                        case 1:
                           COMMA57 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_fplist1387);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              COMMA57_tree = (PythonTree)this.adaptor.create(COMMA57);
                              this.adaptor.addChild(root_0, COMMA57_tree);
                           }
                        default:
                           if (this.state.backtracking == 0) {
                              retval.etypes = list_f;
                           }

                           retval.stop = this.input.LT(-1);
                           if (this.state.backtracking == 0) {
                              retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                              this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                           }

                           return retval;
                     }
               }
            }
         }
      } catch (RecognitionException var15) {
         this.reportError(var15);
         this.errorHandler.recover(this, this.input, var15);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         return retval;
      } finally {
         ;
      }
   }

   public final stmt_return stmt() throws RecognitionException {
      stmt_return retval = new stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      simple_stmt_return simple_stmt58 = null;
      compound_stmt_return compound_stmt59 = null;

      try {
         try {
            int alt27 = true;
            int LA27_0 = this.input.LA(1);
            byte alt27;
            if (LA27_0 != 9 && LA27_0 != 32 && LA27_0 != 43 && (LA27_0 < 75 || LA27_0 > 76) && (LA27_0 < 80 || LA27_0 > 81) && LA27_0 != 83 && LA27_0 != 85) {
               if (LA27_0 == 11 && (this.printFunction || !this.printFunction)) {
                  alt27 = 1;
               } else if (LA27_0 >= 14 && LA27_0 <= 15 || LA27_0 == 17 || LA27_0 == 19 || LA27_0 == 22 || LA27_0 == 24 || LA27_0 == 26 || LA27_0 == 28 || LA27_0 == 31 || LA27_0 >= 35 && LA27_0 <= 37 || LA27_0 == 41 || LA27_0 >= 86 && LA27_0 <= 90) {
                  alt27 = 1;
               } else {
                  if (LA27_0 != 16 && LA27_0 != 18 && LA27_0 != 25 && LA27_0 != 27 && (LA27_0 < 38 || LA27_0 > 40) && LA27_0 != 42) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 27, 0, this.input);
                     throw nvae;
                  }

                  alt27 = 2;
               }
            } else {
               alt27 = 1;
            }

            switch (alt27) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_simple_stmt_in_stmt1423);
                  simple_stmt58 = this.simple_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, simple_stmt58.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.stypes = simple_stmt58 != null ? simple_stmt58.stypes : null;
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_compound_stmt_in_stmt1439);
                  compound_stmt59 = this.compound_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, compound_stmt59.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.stypes = new ArrayList();
                     retval.stypes.add(compound_stmt59 != null ? compound_stmt59.tree : null);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var11) {
            this.reportError(var11);
            this.errorHandler.recover(this, this.input, var11);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var11);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final simple_stmt_return simple_stmt() throws RecognitionException {
      simple_stmt_return retval = new simple_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token SEMI60 = null;
      Token SEMI61 = null;
      Token NEWLINE62 = null;
      List list_s = null;
      small_stmt_return s = null;
      s = null;
      PythonTree SEMI60_tree = null;
      PythonTree SEMI61_tree = null;
      PythonTree NEWLINE62_tree = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_small_stmt_in_simple_stmt1475);
         s = this.small_stmt();
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, s.getTree());
            }

            if (list_s == null) {
               list_s = new ArrayList();
            }

            list_s.add(s.getTree());

            while(true) {
               int alt29 = 2;
               int LA29_0 = this.input.LA(1);
               if (LA29_0 == 50) {
                  int LA28_1 = this.input.LA(2);
                  if (LA28_1 == 9 || LA28_1 == 11 || LA28_1 >= 14 && LA28_1 <= 15 || LA28_1 == 17 || LA28_1 == 19 || LA28_1 == 22 || LA28_1 == 24 || LA28_1 == 26 || LA28_1 == 28 || LA28_1 >= 31 && LA28_1 <= 32 || LA28_1 >= 35 && LA28_1 <= 37 || LA28_1 == 41 || LA28_1 == 43 || LA28_1 >= 75 && LA28_1 <= 76 || LA28_1 >= 80 && LA28_1 <= 81 || LA28_1 == 83 || LA28_1 >= 85 && LA28_1 <= 90) {
                     alt29 = 1;
                  }
               }

               switch (alt29) {
                  case 1:
                     SEMI60 = (Token)this.match(this.input, 50, FOLLOW_SEMI_in_simple_stmt1485);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        SEMI60_tree = (PythonTree)this.adaptor.create(SEMI60);
                        this.adaptor.addChild(root_0, SEMI60_tree);
                     }

                     this.pushFollow(FOLLOW_small_stmt_in_simple_stmt1489);
                     s = this.small_stmt();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, s.getTree());
                     }

                     if (list_s == null) {
                        list_s = new ArrayList();
                     }

                     list_s.add(s.getTree());
                     break;
                  default:
                     alt29 = 2;
                     LA29_0 = this.input.LA(1);
                     if (LA29_0 == 50) {
                        alt29 = 1;
                     }

                     switch (alt29) {
                        case 1:
                           SEMI61 = (Token)this.match(this.input, 50, FOLLOW_SEMI_in_simple_stmt1494);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              SEMI61_tree = (PythonTree)this.adaptor.create(SEMI61);
                              this.adaptor.addChild(root_0, SEMI61_tree);
                           }
                        default:
                           NEWLINE62 = (Token)this.match(this.input, 7, FOLLOW_NEWLINE_in_simple_stmt1498);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              NEWLINE62_tree = (PythonTree)this.adaptor.create(NEWLINE62);
                              this.adaptor.addChild(root_0, NEWLINE62_tree);
                           }

                           if (this.state.backtracking == 0) {
                              retval.stypes = list_s;
                           }

                           retval.stop = this.input.LT(-1);
                           if (this.state.backtracking == 0) {
                              retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                              this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                           }

                           return retval;
                     }
               }
            }
         }
      } catch (RecognitionException var17) {
         this.reportError(var17);
         this.errorHandler.recover(this, this.input, var17);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         return retval;
      } finally {
         ;
      }
   }

   public final small_stmt_return small_stmt() throws RecognitionException {
      small_stmt_return retval = new small_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      expr_stmt_return expr_stmt63 = null;
      del_stmt_return del_stmt64 = null;
      pass_stmt_return pass_stmt65 = null;
      flow_stmt_return flow_stmt66 = null;
      import_stmt_return import_stmt67 = null;
      global_stmt_return global_stmt68 = null;
      exec_stmt_return exec_stmt69 = null;
      assert_stmt_return assert_stmt70 = null;
      print_stmt_return print_stmt71 = null;

      try {
         try {
            int alt30 = true;
            int alt30 = this.dfa30.predict(this.input);
            switch (alt30) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_expr_stmt_in_small_stmt1521);
                  expr_stmt63 = this.expr_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, expr_stmt63.getTree());
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_del_stmt_in_small_stmt1536);
                  del_stmt64 = this.del_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, del_stmt64.getTree());
                  }
                  break;
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_pass_stmt_in_small_stmt1551);
                  pass_stmt65 = this.pass_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, pass_stmt65.getTree());
                  }
                  break;
               case 4:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_flow_stmt_in_small_stmt1566);
                  flow_stmt66 = this.flow_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, flow_stmt66.getTree());
                  }
                  break;
               case 5:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_import_stmt_in_small_stmt1581);
                  import_stmt67 = this.import_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, import_stmt67.getTree());
                  }
                  break;
               case 6:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_global_stmt_in_small_stmt1596);
                  global_stmt68 = this.global_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, global_stmt68.getTree());
                  }
                  break;
               case 7:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_exec_stmt_in_small_stmt1611);
                  exec_stmt69 = this.exec_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, exec_stmt69.getTree());
                  }
                  break;
               case 8:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_assert_stmt_in_small_stmt1626);
                  assert_stmt70 = this.assert_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, assert_stmt70.getTree());
                  }
                  break;
               case 9:
                  root_0 = (PythonTree)this.adaptor.nil();
                  if (this.printFunction) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     throw new FailedPredicateException(this.input, "small_stmt", "!printFunction");
                  }

                  this.pushFollow(FOLLOW_print_stmt_in_small_stmt1645);
                  print_stmt71 = this.print_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, print_stmt71.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var17) {
            this.reportError(var17);
            this.errorHandler.recover(this, this.input, var17);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final expr_stmt_return expr_stmt() throws RecognitionException {
      expr_stmt_return retval = new expr_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token at = null;
      Token ay = null;
      List list_t = null;
      List list_y2 = null;
      testlist_return lhs = null;
      augassign_return aay = null;
      yield_expr_return y1 = null;
      augassign_return aat = null;
      testlist_return rhs = null;
      testlist_return t = null;
      t = null;
      yield_expr_return y2 = null;
      y2 = null;
      PythonTree at_tree = null;
      PythonTree ay_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            int alt35 = true;
            int alt35 = this.dfa35.predict(this.input);
            boolean alt34;
            int alt34;
            label690:
            switch (alt35) {
               case 1:
                  this.pushFollow(FOLLOW_testlist_in_expr_stmt1693);
                  lhs = this.testlist(expr_contextType.AugStore);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, lhs.getTree());
                  }

                  alt34 = true;
                  alt34 = this.dfa31.predict(this.input);
                  switch (alt34) {
                     case 1:
                        this.pushFollow(FOLLOW_augassign_in_expr_stmt1709);
                        aay = this.augassign();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, aay.getTree());
                        }

                        this.pushFollow(FOLLOW_yield_expr_in_expr_stmt1713);
                        y1 = this.yield_expr();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, y1.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           this.actions.checkAugAssign(this.actions.castExpr(lhs != null ? lhs.tree : null));
                           stype = new AugAssign(lhs != null ? lhs.tree : null, this.actions.castExpr(lhs != null ? lhs.tree : null), aay != null ? aay.op : null, this.actions.castExpr(y1 != null ? y1.etype : null));
                        }
                        break label690;
                     case 2:
                        this.pushFollow(FOLLOW_augassign_in_expr_stmt1753);
                        aat = this.augassign();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, aat.getTree());
                        }

                        this.pushFollow(FOLLOW_testlist_in_expr_stmt1757);
                        rhs = this.testlist(expr_contextType.Load);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, rhs.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           this.actions.checkAugAssign(this.actions.castExpr(lhs != null ? lhs.tree : null));
                           stype = new AugAssign(lhs != null ? lhs.tree : null, this.actions.castExpr(lhs != null ? lhs.tree : null), aat != null ? aat.op : null, this.actions.castExpr(rhs != null ? rhs.tree : null));
                        }
                     default:
                        break label690;
                  }
               case 2:
                  this.pushFollow(FOLLOW_testlist_in_expr_stmt1812);
                  lhs = this.testlist(expr_contextType.Store);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, lhs.getTree());
                  }

                  alt34 = true;
                  int LA34_0 = this.input.LA(1);
                  int cnt33;
                  if (LA34_0 != 7 && LA34_0 != 50) {
                     if (LA34_0 != 46) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 34, 0, this.input);
                        throw nvae;
                     }

                     cnt33 = this.input.LA(2);
                     if (cnt33 == 41) {
                        alt34 = 3;
                     } else {
                        if (cnt33 != 9 && cnt33 != 11 && (cnt33 < 31 || cnt33 > 32) && cnt33 != 43 && (cnt33 < 75 || cnt33 > 76) && (cnt33 < 80 || cnt33 > 81) && cnt33 != 83 && (cnt33 < 85 || cnt33 > 90)) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           NoViableAltException nvae = new NoViableAltException("", 34, 2, this.input);
                           throw nvae;
                        }

                        alt34 = 2;
                     }
                  } else {
                     alt34 = 1;
                  }

                  int LA33_0;
                  EarlyExitException eee;
                  byte alt33;
                  switch (alt34) {
                     case 1:
                     default:
                        break label690;
                     case 2:
                        cnt33 = 0;

                        while(true) {
                           alt33 = 2;
                           LA33_0 = this.input.LA(1);
                           if (LA33_0 == 46) {
                              alt33 = 1;
                           }

                           switch (alt33) {
                              case 1:
                                 at = (Token)this.match(this.input, 46, FOLLOW_ASSIGN_in_expr_stmt1839);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    at_tree = (PythonTree)this.adaptor.create(at);
                                    this.adaptor.addChild(root_0, at_tree);
                                 }

                                 this.pushFollow(FOLLOW_testlist_in_expr_stmt1843);
                                 t = this.testlist(expr_contextType.Store);
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    this.adaptor.addChild(root_0, t.getTree());
                                 }

                                 if (list_t == null) {
                                    list_t = new ArrayList();
                                 }

                                 list_t.add(t.getTree());
                                 ++cnt33;
                                 break;
                              default:
                                 if (cnt33 < 1) {
                                    if (this.state.backtracking > 0) {
                                       this.state.failed = true;
                                       return retval;
                                    }

                                    eee = new EarlyExitException(32, this.input);
                                    throw eee;
                                 }

                                 if (this.state.backtracking == 0) {
                                    stype = new Assign(lhs != null ? lhs.tree : null, this.actions.makeAssignTargets(this.actions.castExpr(lhs != null ? lhs.tree : null), list_t), this.actions.makeAssignValue(list_t));
                                 }
                                 break label690;
                           }
                        }
                     case 3:
                        cnt33 = 0;

                        while(true) {
                           alt33 = 2;
                           LA33_0 = this.input.LA(1);
                           if (LA33_0 == 46) {
                              alt33 = 1;
                           }

                           switch (alt33) {
                              case 1:
                                 ay = (Token)this.match(this.input, 46, FOLLOW_ASSIGN_in_expr_stmt1888);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    ay_tree = (PythonTree)this.adaptor.create(ay);
                                    this.adaptor.addChild(root_0, ay_tree);
                                 }

                                 this.pushFollow(FOLLOW_yield_expr_in_expr_stmt1892);
                                 y2 = this.yield_expr();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    this.adaptor.addChild(root_0, y2.getTree());
                                 }

                                 if (list_y2 == null) {
                                    list_y2 = new ArrayList();
                                 }

                                 list_y2.add(y2.getTree());
                                 ++cnt33;
                                 break;
                              default:
                                 if (cnt33 < 1) {
                                    if (this.state.backtracking > 0) {
                                       this.state.failed = true;
                                       return retval;
                                    }

                                    eee = new EarlyExitException(33, this.input);
                                    throw eee;
                                 }

                                 if (this.state.backtracking == 0) {
                                    stype = new Assign(lhs != null ? lhs.start : null, this.actions.makeAssignTargets(this.actions.castExpr(lhs != null ? lhs.tree : null), list_y2), this.actions.makeAssignValue(list_y2));
                                 }
                                 break label690;
                           }
                        }
                  }
               case 3:
                  this.pushFollow(FOLLOW_testlist_in_expr_stmt1940);
                  lhs = this.testlist(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, lhs.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     stype = new Expr(lhs != null ? lhs.start : null, this.actions.castExpr(lhs != null ? lhs.tree : null));
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && stype != null) {
               retval.tree = (PythonTree)stype;
            }
         } catch (RecognitionException var27) {
            this.reportError(var27);
            this.errorHandler.recover(this, this.input, var27);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var27);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final augassign_return augassign() throws RecognitionException {
      augassign_return retval = new augassign_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token PLUSEQUAL72 = null;
      Token MINUSEQUAL73 = null;
      Token STAREQUAL74 = null;
      Token SLASHEQUAL75 = null;
      Token PERCENTEQUAL76 = null;
      Token AMPEREQUAL77 = null;
      Token VBAREQUAL78 = null;
      Token CIRCUMFLEXEQUAL79 = null;
      Token LEFTSHIFTEQUAL80 = null;
      Token RIGHTSHIFTEQUAL81 = null;
      Token DOUBLESTAREQUAL82 = null;
      Token DOUBLESLASHEQUAL83 = null;
      PythonTree PLUSEQUAL72_tree = null;
      PythonTree MINUSEQUAL73_tree = null;
      PythonTree STAREQUAL74_tree = null;
      PythonTree SLASHEQUAL75_tree = null;
      PythonTree PERCENTEQUAL76_tree = null;
      PythonTree AMPEREQUAL77_tree = null;
      PythonTree VBAREQUAL78_tree = null;
      PythonTree CIRCUMFLEXEQUAL79_tree = null;
      PythonTree LEFTSHIFTEQUAL80_tree = null;
      PythonTree RIGHTSHIFTEQUAL81_tree = null;
      PythonTree DOUBLESTAREQUAL82_tree = null;
      PythonTree DOUBLESLASHEQUAL83_tree = null;

      try {
         try {
            int alt36 = true;
            byte alt36;
            switch (this.input.LA(1)) {
               case 51:
                  alt36 = 1;
                  break;
               case 52:
                  alt36 = 2;
                  break;
               case 53:
                  alt36 = 3;
                  break;
               case 54:
                  alt36 = 4;
                  break;
               case 55:
                  alt36 = 5;
                  break;
               case 56:
                  alt36 = 6;
                  break;
               case 57:
                  alt36 = 7;
                  break;
               case 58:
                  alt36 = 8;
                  break;
               case 59:
                  alt36 = 9;
                  break;
               case 60:
                  alt36 = 10;
                  break;
               case 61:
                  alt36 = 11;
                  break;
               case 62:
                  alt36 = 12;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 36, 0, this.input);
                  throw nvae;
            }

            switch (alt36) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  PLUSEQUAL72 = (Token)this.match(this.input, 51, FOLLOW_PLUSEQUAL_in_augassign1982);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     PLUSEQUAL72_tree = (PythonTree)this.adaptor.create(PLUSEQUAL72);
                     this.adaptor.addChild(root_0, PLUSEQUAL72_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Add;
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  MINUSEQUAL73 = (Token)this.match(this.input, 52, FOLLOW_MINUSEQUAL_in_augassign2000);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     MINUSEQUAL73_tree = (PythonTree)this.adaptor.create(MINUSEQUAL73);
                     this.adaptor.addChild(root_0, MINUSEQUAL73_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Sub;
                  }
                  break;
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  STAREQUAL74 = (Token)this.match(this.input, 53, FOLLOW_STAREQUAL_in_augassign2018);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     STAREQUAL74_tree = (PythonTree)this.adaptor.create(STAREQUAL74);
                     this.adaptor.addChild(root_0, STAREQUAL74_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Mult;
                  }
                  break;
               case 4:
                  root_0 = (PythonTree)this.adaptor.nil();
                  SLASHEQUAL75 = (Token)this.match(this.input, 54, FOLLOW_SLASHEQUAL_in_augassign2036);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     SLASHEQUAL75_tree = (PythonTree)this.adaptor.create(SLASHEQUAL75);
                     this.adaptor.addChild(root_0, SLASHEQUAL75_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Div;
                  }
                  break;
               case 5:
                  root_0 = (PythonTree)this.adaptor.nil();
                  PERCENTEQUAL76 = (Token)this.match(this.input, 55, FOLLOW_PERCENTEQUAL_in_augassign2054);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     PERCENTEQUAL76_tree = (PythonTree)this.adaptor.create(PERCENTEQUAL76);
                     this.adaptor.addChild(root_0, PERCENTEQUAL76_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Mod;
                  }
                  break;
               case 6:
                  root_0 = (PythonTree)this.adaptor.nil();
                  AMPEREQUAL77 = (Token)this.match(this.input, 56, FOLLOW_AMPEREQUAL_in_augassign2072);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     AMPEREQUAL77_tree = (PythonTree)this.adaptor.create(AMPEREQUAL77);
                     this.adaptor.addChild(root_0, AMPEREQUAL77_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.BitAnd;
                  }
                  break;
               case 7:
                  root_0 = (PythonTree)this.adaptor.nil();
                  VBAREQUAL78 = (Token)this.match(this.input, 57, FOLLOW_VBAREQUAL_in_augassign2090);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     VBAREQUAL78_tree = (PythonTree)this.adaptor.create(VBAREQUAL78);
                     this.adaptor.addChild(root_0, VBAREQUAL78_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.BitOr;
                  }
                  break;
               case 8:
                  root_0 = (PythonTree)this.adaptor.nil();
                  CIRCUMFLEXEQUAL79 = (Token)this.match(this.input, 58, FOLLOW_CIRCUMFLEXEQUAL_in_augassign2108);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     CIRCUMFLEXEQUAL79_tree = (PythonTree)this.adaptor.create(CIRCUMFLEXEQUAL79);
                     this.adaptor.addChild(root_0, CIRCUMFLEXEQUAL79_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.BitXor;
                  }
                  break;
               case 9:
                  root_0 = (PythonTree)this.adaptor.nil();
                  LEFTSHIFTEQUAL80 = (Token)this.match(this.input, 59, FOLLOW_LEFTSHIFTEQUAL_in_augassign2126);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LEFTSHIFTEQUAL80_tree = (PythonTree)this.adaptor.create(LEFTSHIFTEQUAL80);
                     this.adaptor.addChild(root_0, LEFTSHIFTEQUAL80_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.LShift;
                  }
                  break;
               case 10:
                  root_0 = (PythonTree)this.adaptor.nil();
                  RIGHTSHIFTEQUAL81 = (Token)this.match(this.input, 60, FOLLOW_RIGHTSHIFTEQUAL_in_augassign2144);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RIGHTSHIFTEQUAL81_tree = (PythonTree)this.adaptor.create(RIGHTSHIFTEQUAL81);
                     this.adaptor.addChild(root_0, RIGHTSHIFTEQUAL81_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.RShift;
                  }
                  break;
               case 11:
                  root_0 = (PythonTree)this.adaptor.nil();
                  DOUBLESTAREQUAL82 = (Token)this.match(this.input, 61, FOLLOW_DOUBLESTAREQUAL_in_augassign2162);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     DOUBLESTAREQUAL82_tree = (PythonTree)this.adaptor.create(DOUBLESTAREQUAL82);
                     this.adaptor.addChild(root_0, DOUBLESTAREQUAL82_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Pow;
                  }
                  break;
               case 12:
                  root_0 = (PythonTree)this.adaptor.nil();
                  DOUBLESLASHEQUAL83 = (Token)this.match(this.input, 62, FOLLOW_DOUBLESLASHEQUAL_in_augassign2180);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     DOUBLESLASHEQUAL83_tree = (PythonTree)this.adaptor.create(DOUBLESLASHEQUAL83);
                     this.adaptor.addChild(root_0, DOUBLESLASHEQUAL83_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.FloorDiv;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var32) {
            this.reportError(var32);
            this.errorHandler.recover(this, this.input, var32);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var32);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final print_stmt_return print_stmt() throws RecognitionException {
      print_stmt_return retval = new print_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token PRINT84 = null;
      Token RIGHTSHIFT85 = null;
      printlist_return t1 = null;
      printlist2_return t2 = null;
      PythonTree PRINT84_tree = null;
      PythonTree RIGHTSHIFT85_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            PRINT84 = (Token)this.match(this.input, 11, FOLLOW_PRINT_in_print_stmt2220);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               PRINT84_tree = (PythonTree)this.adaptor.create(PRINT84);
               this.adaptor.addChild(root_0, PRINT84_tree);
            }

            int alt37 = true;
            int LA37_0 = this.input.LA(1);
            byte alt37;
            if (LA37_0 != 9 && LA37_0 != 32 && LA37_0 != 43 && (LA37_0 < 75 || LA37_0 > 76) && (LA37_0 < 80 || LA37_0 > 81) && LA37_0 != 83 && LA37_0 != 85) {
               if (LA37_0 == 11 && this.printFunction) {
                  alt37 = 1;
               } else if (LA37_0 == 31 || LA37_0 >= 86 && LA37_0 <= 90) {
                  alt37 = 1;
               } else if (LA37_0 == 63) {
                  alt37 = 2;
               } else {
                  if (LA37_0 != 7 && LA37_0 != 50) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 37, 0, this.input);
                     throw nvae;
                  }

                  alt37 = 3;
               }
            } else {
               alt37 = 1;
            }

            switch (alt37) {
               case 1:
                  this.pushFollow(FOLLOW_printlist_in_print_stmt2231);
                  t1 = this.printlist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t1.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     stype = new Print(PRINT84, (expr)null, this.actions.castExprs(t1 != null ? t1.elts : null), t1 != null ? t1.newline : false);
                  }
                  break;
               case 2:
                  RIGHTSHIFT85 = (Token)this.match(this.input, 63, FOLLOW_RIGHTSHIFT_in_print_stmt2250);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RIGHTSHIFT85_tree = (PythonTree)this.adaptor.create(RIGHTSHIFT85);
                     this.adaptor.addChild(root_0, RIGHTSHIFT85_tree);
                  }

                  this.pushFollow(FOLLOW_printlist2_in_print_stmt2254);
                  t2 = this.printlist2();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t2.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     stype = new Print(PRINT84, this.actions.castExpr((t2 != null ? t2.elts : null).get(0)), this.actions.castExprs(t2 != null ? t2.elts : null, 1), t2 != null ? t2.newline : false);
                  }
                  break;
               case 3:
                  if (this.state.backtracking == 0) {
                     stype = new Print(PRINT84, (expr)null, new ArrayList(), true);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = stype;
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.errorHandler.recover(this, this.input, var16);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final printlist_return printlist() throws RecognitionException {
      printlist_return retval = new printlist_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token trailcomma = null;
      Token COMMA86 = null;
      List list_t = null;
      test_return t = null;
      t = null;
      PythonTree trailcomma_tree = null;
      PythonTree COMMA86_tree = null;

      try {
         try {
            int alt40 = true;
            int alt40 = this.dfa40.predict(this.input);
            label207:
            switch (alt40) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_test_in_printlist2334);
                  t = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t.getTree());
                  }

                  if (list_t == null) {
                     list_t = new ArrayList();
                  }

                  list_t.add(t.getTree());

                  while(true) {
                     int alt38 = true;
                     int alt38 = this.dfa38.predict(this.input);
                     switch (alt38) {
                        case 1:
                           COMMA86 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_printlist2346);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              COMMA86_tree = (PythonTree)this.adaptor.create(COMMA86);
                              this.adaptor.addChild(root_0, COMMA86_tree);
                           }

                           this.pushFollow(FOLLOW_test_in_printlist2350);
                           t = this.test(expr_contextType.Load);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, t.getTree());
                           }

                           if (list_t == null) {
                              list_t = new ArrayList();
                           }

                           list_t.add(t.getTree());
                           break;
                        default:
                           int alt39 = 2;
                           int LA39_0 = this.input.LA(1);
                           if (LA39_0 == 47) {
                              alt39 = 1;
                           }

                           switch (alt39) {
                              case 1:
                                 trailcomma = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_printlist2358);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    trailcomma_tree = (PythonTree)this.adaptor.create(trailcomma);
                                    this.adaptor.addChild(root_0, trailcomma_tree);
                                 }
                              default:
                                 if (this.state.backtracking == 0) {
                                    retval.elts = list_t;
                                    if (trailcomma == null) {
                                       retval.newline = true;
                                    } else {
                                       retval.newline = false;
                                    }
                                 }
                                 break label207;
                           }
                     }
                  }
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_test_in_printlist2379);
                  t = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t.getTree());
                  }

                  if (list_t == null) {
                     list_t = new ArrayList();
                  }

                  list_t.add(t.getTree());
                  if (this.state.backtracking == 0) {
                     retval.elts = list_t;
                     retval.newline = true;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.errorHandler.recover(this, this.input, var16);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final printlist2_return printlist2() throws RecognitionException {
      printlist2_return retval = new printlist2_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token trailcomma = null;
      Token COMMA87 = null;
      List list_t = null;
      test_return t = null;
      t = null;
      PythonTree trailcomma_tree = null;
      PythonTree COMMA87_tree = null;

      try {
         try {
            int alt43 = true;
            int alt43 = this.dfa43.predict(this.input);
            label207:
            switch (alt43) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_test_in_printlist22436);
                  t = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t.getTree());
                  }

                  if (list_t == null) {
                     list_t = new ArrayList();
                  }

                  list_t.add(t.getTree());

                  while(true) {
                     int alt41 = true;
                     int alt41 = this.dfa41.predict(this.input);
                     switch (alt41) {
                        case 1:
                           COMMA87 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_printlist22448);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              COMMA87_tree = (PythonTree)this.adaptor.create(COMMA87);
                              this.adaptor.addChild(root_0, COMMA87_tree);
                           }

                           this.pushFollow(FOLLOW_test_in_printlist22452);
                           t = this.test(expr_contextType.Load);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, t.getTree());
                           }

                           if (list_t == null) {
                              list_t = new ArrayList();
                           }

                           list_t.add(t.getTree());
                           break;
                        default:
                           int alt42 = 2;
                           int LA42_0 = this.input.LA(1);
                           if (LA42_0 == 47) {
                              alt42 = 1;
                           }

                           switch (alt42) {
                              case 1:
                                 trailcomma = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_printlist22460);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    trailcomma_tree = (PythonTree)this.adaptor.create(trailcomma);
                                    this.adaptor.addChild(root_0, trailcomma_tree);
                                 }
                              default:
                                 if (this.state.backtracking == 0) {
                                    retval.elts = list_t;
                                    if (trailcomma == null) {
                                       retval.newline = true;
                                    } else {
                                       retval.newline = false;
                                    }
                                 }
                                 break label207;
                           }
                     }
                  }
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_test_in_printlist22481);
                  t = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t.getTree());
                  }

                  if (list_t == null) {
                     list_t = new ArrayList();
                  }

                  list_t.add(t.getTree());
                  if (this.state.backtracking == 0) {
                     retval.elts = list_t;
                     retval.newline = true;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.errorHandler.recover(this, this.input, var16);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final del_stmt_return del_stmt() throws RecognitionException {
      del_stmt_return retval = new del_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token DELETE88 = null;
      del_list_return del_list89 = null;
      PythonTree DELETE88_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            DELETE88 = (Token)this.match(this.input, 19, FOLLOW_DELETE_in_del_stmt2518);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               DELETE88_tree = (PythonTree)this.adaptor.create(DELETE88);
               this.adaptor.addChild(root_0, DELETE88_tree);
            }

            this.pushFollow(FOLLOW_del_list_in_del_stmt2520);
            del_list89 = this.del_list();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, del_list89.getTree());
            }

            if (this.state.backtracking == 0) {
               stype = new Delete(DELETE88, del_list89 != null ? del_list89.etypes : null);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = stype;
            }
         } catch (RecognitionException var11) {
            this.reportError(var11);
            this.errorHandler.recover(this, this.input, var11);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var11);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final pass_stmt_return pass_stmt() throws RecognitionException {
      pass_stmt_return retval = new pass_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token PASS90 = null;
      PythonTree PASS90_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            PASS90 = (Token)this.match(this.input, 35, FOLLOW_PASS_in_pass_stmt2556);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               PASS90_tree = (PythonTree)this.adaptor.create(PASS90);
               this.adaptor.addChild(root_0, PASS90_tree);
            }

            if (this.state.backtracking == 0) {
               stype = new Pass(PASS90);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = stype;
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.errorHandler.recover(this, this.input, var10);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var10);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final flow_stmt_return flow_stmt() throws RecognitionException {
      flow_stmt_return retval = new flow_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      break_stmt_return break_stmt91 = null;
      continue_stmt_return continue_stmt92 = null;
      return_stmt_return return_stmt93 = null;
      raise_stmt_return raise_stmt94 = null;
      yield_stmt_return yield_stmt95 = null;

      try {
         try {
            int alt44 = true;
            byte alt44;
            switch (this.input.LA(1)) {
               case 15:
                  alt44 = 1;
                  break;
               case 17:
                  alt44 = 2;
                  break;
               case 36:
                  alt44 = 4;
                  break;
               case 37:
                  alt44 = 3;
                  break;
               case 41:
                  alt44 = 5;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 44, 0, this.input);
                  throw nvae;
            }

            switch (alt44) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_break_stmt_in_flow_stmt2582);
                  break_stmt91 = this.break_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, break_stmt91.getTree());
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_continue_stmt_in_flow_stmt2590);
                  continue_stmt92 = this.continue_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, continue_stmt92.getTree());
                  }
                  break;
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_return_stmt_in_flow_stmt2598);
                  return_stmt93 = this.return_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, return_stmt93.getTree());
                  }
                  break;
               case 4:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_raise_stmt_in_flow_stmt2606);
                  raise_stmt94 = this.raise_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, raise_stmt94.getTree());
                  }
                  break;
               case 5:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_yield_stmt_in_flow_stmt2614);
                  yield_stmt95 = this.yield_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, yield_stmt95.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.errorHandler.recover(this, this.input, var13);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final break_stmt_return break_stmt() throws RecognitionException {
      break_stmt_return retval = new break_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token BREAK96 = null;
      PythonTree BREAK96_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            BREAK96 = (Token)this.match(this.input, 15, FOLLOW_BREAK_in_break_stmt2642);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               BREAK96_tree = (PythonTree)this.adaptor.create(BREAK96);
               this.adaptor.addChild(root_0, BREAK96_tree);
            }

            if (this.state.backtracking == 0) {
               stype = new Break(BREAK96);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = stype;
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.errorHandler.recover(this, this.input, var10);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var10);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final continue_stmt_return continue_stmt() throws RecognitionException {
      continue_stmt_return retval = new continue_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token CONTINUE97 = null;
      PythonTree CONTINUE97_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            CONTINUE97 = (Token)this.match(this.input, 17, FOLLOW_CONTINUE_in_continue_stmt2678);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               CONTINUE97_tree = (PythonTree)this.adaptor.create(CONTINUE97);
               this.adaptor.addChild(root_0, CONTINUE97_tree);
            }

            if (this.state.backtracking == 0) {
               if (!this.suite_stack.isEmpty() && ((suite_scope)this.suite_stack.peek()).continueIllegal) {
                  this.errorHandler.error("'continue' not supported inside 'finally' clause", new PythonTree(retval.start));
               }

               stype = new Continue(CONTINUE97);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = stype;
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.errorHandler.recover(this, this.input, var10);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var10);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final return_stmt_return return_stmt() throws RecognitionException {
      return_stmt_return retval = new return_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token RETURN98 = null;
      testlist_return testlist99 = null;
      PythonTree RETURN98_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            RETURN98 = (Token)this.match(this.input, 37, FOLLOW_RETURN_in_return_stmt2714);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               RETURN98_tree = (PythonTree)this.adaptor.create(RETURN98);
               this.adaptor.addChild(root_0, RETURN98_tree);
            }

            int alt45 = true;
            int LA45_0 = this.input.LA(1);
            byte alt45;
            if (LA45_0 != 9 && LA45_0 != 32 && LA45_0 != 43 && (LA45_0 < 75 || LA45_0 > 76) && (LA45_0 < 80 || LA45_0 > 81) && LA45_0 != 83 && LA45_0 != 85) {
               if (LA45_0 == 11 && this.printFunction) {
                  alt45 = 1;
               } else if (LA45_0 == 31 || LA45_0 >= 86 && LA45_0 <= 90) {
                  alt45 = 1;
               } else {
                  if (LA45_0 != 7 && LA45_0 != 50) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 45, 0, this.input);
                     throw nvae;
                  }

                  alt45 = 2;
               }
            } else {
               alt45 = 1;
            }

            switch (alt45) {
               case 1:
                  this.pushFollow(FOLLOW_testlist_in_return_stmt2723);
                  testlist99 = this.testlist(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, testlist99.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     stype = new Return(RETURN98, this.actions.castExpr(testlist99 != null ? testlist99.tree : null));
                  }
                  break;
               case 2:
                  if (this.state.backtracking == 0) {
                     stype = new Return(RETURN98, (expr)null);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = stype;
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.errorHandler.recover(this, this.input, var13);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final yield_stmt_return yield_stmt() throws RecognitionException {
      yield_stmt_return retval = new yield_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      yield_expr_return yield_expr100 = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_yield_expr_in_yield_stmt2788);
            yield_expr100 = this.yield_expr();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, yield_expr100.getTree());
            }

            if (this.state.backtracking == 0) {
               stype = new Expr(yield_expr100 != null ? yield_expr100.start : null, this.actions.castExpr(yield_expr100 != null ? yield_expr100.etype : null));
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = stype;
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.errorHandler.recover(this, this.input, var9);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var9);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final raise_stmt_return raise_stmt() throws RecognitionException {
      raise_stmt_return retval = new raise_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token RAISE101 = null;
      Token COMMA102 = null;
      Token COMMA103 = null;
      test_return t1 = null;
      test_return t2 = null;
      test_return t3 = null;
      PythonTree RAISE101_tree = null;
      PythonTree COMMA102_tree = null;
      PythonTree COMMA103_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            RAISE101 = (Token)this.match(this.input, 36, FOLLOW_RAISE_in_raise_stmt2824);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               RAISE101_tree = (PythonTree)this.adaptor.create(RAISE101);
               this.adaptor.addChild(root_0, RAISE101_tree);
            }

            int alt48 = 2;
            int LA48_0 = this.input.LA(1);
            if (LA48_0 != 9 && LA48_0 != 32 && LA48_0 != 43 && (LA48_0 < 75 || LA48_0 > 76) && (LA48_0 < 80 || LA48_0 > 81) && LA48_0 != 83 && LA48_0 != 85) {
               if (LA48_0 == 11 && this.printFunction) {
                  alt48 = 1;
               } else if (LA48_0 == 31 || LA48_0 >= 86 && LA48_0 <= 90) {
                  alt48 = 1;
               }
            } else {
               alt48 = 1;
            }

            switch (alt48) {
               case 1:
                  this.pushFollow(FOLLOW_test_in_raise_stmt2829);
                  t1 = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t1.getTree());
                  }

                  int alt47 = 2;
                  int LA47_0 = this.input.LA(1);
                  if (LA47_0 == 47) {
                     alt47 = 1;
                  }

                  switch (alt47) {
                     case 1:
                        COMMA102 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_raise_stmt2833);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           COMMA102_tree = (PythonTree)this.adaptor.create(COMMA102);
                           this.adaptor.addChild(root_0, COMMA102_tree);
                        }

                        this.pushFollow(FOLLOW_test_in_raise_stmt2837);
                        t2 = this.test(expr_contextType.Load);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, t2.getTree());
                        }

                        int alt46 = 2;
                        int LA46_0 = this.input.LA(1);
                        if (LA46_0 == 47) {
                           alt46 = 1;
                        }

                        switch (alt46) {
                           case 1:
                              COMMA103 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_raise_stmt2849);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 COMMA103_tree = (PythonTree)this.adaptor.create(COMMA103);
                                 this.adaptor.addChild(root_0, COMMA103_tree);
                              }

                              this.pushFollow(FOLLOW_test_in_raise_stmt2853);
                              t3 = this.test(expr_contextType.Load);
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 this.adaptor.addChild(root_0, t3.getTree());
                              }
                        }
                  }
               default:
                  if (this.state.backtracking == 0) {
                     stype = new Raise(RAISE101, this.actions.castExpr(t1 != null ? t1.tree : null), this.actions.castExpr(t2 != null ? t2.tree : null), this.actions.castExpr(t3 != null ? t3.tree : null));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = stype;
                  }
            }
         } catch (RecognitionException var23) {
            this.reportError(var23);
            this.errorHandler.recover(this, this.input, var23);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var23);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final import_stmt_return import_stmt() throws RecognitionException {
      import_stmt_return retval = new import_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      import_name_return import_name104 = null;
      import_from_return import_from105 = null;

      try {
         try {
            int alt49 = true;
            int LA49_0 = this.input.LA(1);
            byte alt49;
            if (LA49_0 == 28) {
               alt49 = 1;
            } else {
               if (LA49_0 != 24) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 49, 0, this.input);
                  throw nvae;
               }

               alt49 = 2;
            }

            switch (alt49) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_import_name_in_import_stmt2886);
                  import_name104 = this.import_name();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, import_name104.getTree());
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_import_from_in_import_stmt2894);
                  import_from105 = this.import_from();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, import_from105.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var11) {
            this.reportError(var11);
            this.errorHandler.recover(this, this.input, var11);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var11);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final import_name_return import_name() throws RecognitionException {
      import_name_return retval = new import_name_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token IMPORT106 = null;
      dotted_as_names_return dotted_as_names107 = null;
      PythonTree IMPORT106_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            IMPORT106 = (Token)this.match(this.input, 28, FOLLOW_IMPORT_in_import_name2922);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               IMPORT106_tree = (PythonTree)this.adaptor.create(IMPORT106);
               this.adaptor.addChild(root_0, IMPORT106_tree);
            }

            this.pushFollow(FOLLOW_dotted_as_names_in_import_name2924);
            dotted_as_names107 = this.dotted_as_names();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, dotted_as_names107.getTree());
            }

            if (this.state.backtracking == 0) {
               stype = new Import(IMPORT106, dotted_as_names107 != null ? dotted_as_names107.atypes : null);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = stype;
            }
         } catch (RecognitionException var11) {
            this.reportError(var11);
            this.errorHandler.recover(this, this.input, var11);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var11);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final import_from_return import_from() throws RecognitionException {
      import_from_return retval = new import_from_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token FROM108 = null;
      Token IMPORT110 = null;
      Token STAR111 = null;
      Token LPAREN112 = null;
      Token COMMA113 = null;
      Token RPAREN114 = null;
      Token d = null;
      List list_d = null;
      import_as_names_return i1 = null;
      import_as_names_return i2 = null;
      dotted_name_return dotted_name109 = null;
      PythonTree FROM108_tree = null;
      PythonTree IMPORT110_tree = null;
      PythonTree STAR111_tree = null;
      PythonTree LPAREN112_tree = null;
      PythonTree COMMA113_tree = null;
      PythonTree RPAREN114_tree = null;
      PythonTree d_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            FROM108 = (Token)this.match(this.input, 24, FOLLOW_FROM_in_import_from2961);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               FROM108_tree = (PythonTree)this.adaptor.create(FROM108);
               this.adaptor.addChild(root_0, FROM108_tree);
            }

            int LA53_0;
            byte alt54;
            int alt52 = true;
            int alt52 = this.dfa52.predict(this.input);
            int alt51;
            label633:
            switch (alt52) {
               case 1:
                  while(true) {
                     alt54 = 2;
                     alt51 = this.input.LA(1);
                     if (alt51 == 10) {
                        alt54 = 1;
                     }

                     switch (alt54) {
                        case 1:
                           d = (Token)this.match(this.input, 10, FOLLOW_DOT_in_import_from2966);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              d_tree = (PythonTree)this.adaptor.create(d);
                              this.adaptor.addChild(root_0, d_tree);
                           }

                           if (list_d == null) {
                              list_d = new ArrayList();
                           }

                           list_d.add(d);
                           break;
                        default:
                           this.pushFollow(FOLLOW_dotted_name_in_import_from2969);
                           dotted_name109 = this.dotted_name();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, dotted_name109.getTree());
                           }
                           break label633;
                     }
                  }
               case 2:
                  int cnt51 = 0;

                  label626:
                  while(true) {
                     alt51 = 2;
                     LA53_0 = this.input.LA(1);
                     if (LA53_0 == 10) {
                        alt51 = 1;
                     }

                     switch (alt51) {
                        case 1:
                           d = (Token)this.match(this.input, 10, FOLLOW_DOT_in_import_from2975);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              d_tree = (PythonTree)this.adaptor.create(d);
                              this.adaptor.addChild(root_0, d_tree);
                           }

                           if (list_d == null) {
                              list_d = new ArrayList();
                           }

                           list_d.add(d);
                           ++cnt51;
                           break;
                        default:
                           if (cnt51 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(51, this.input);
                              throw eee;
                           }
                           break label626;
                     }
                  }
            }

            IMPORT110 = (Token)this.match(this.input, 28, FOLLOW_IMPORT_in_import_from2979);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               IMPORT110_tree = (PythonTree)this.adaptor.create(IMPORT110);
               this.adaptor.addChild(root_0, IMPORT110_tree);
            }

            int alt54 = true;
            switch (this.input.LA(1)) {
               case 9:
                  alt54 = 2;
                  break;
               case 43:
                  alt54 = 3;
                  break;
               case 48:
                  alt54 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 54, 0, this.input);
                  throw nvae;
            }

            switch (alt54) {
               case 1:
                  STAR111 = (Token)this.match(this.input, 48, FOLLOW_STAR_in_import_from2990);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     STAR111_tree = (PythonTree)this.adaptor.create(STAR111);
                     this.adaptor.addChild(root_0, STAR111_tree);
                  }

                  if (this.state.backtracking == 0) {
                     stype = new ImportFrom(FROM108, this.actions.makeFromText(list_d, dotted_name109 != null ? dotted_name109.names : null), this.actions.makeModuleNameNode(list_d, dotted_name109 != null ? dotted_name109.names : null), this.actions.makeStarAlias(STAR111), this.actions.makeLevel(list_d));
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_import_as_names_in_import_from3015);
                  i1 = this.import_as_names();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, i1.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     String dottedText = dotted_name109 != null ? this.input.toString(dotted_name109.start, dotted_name109.stop) : null;
                     if (dottedText != null && dottedText.equals("__future__")) {
                        List aliases = i1 != null ? i1.atypes : null;
                        Iterator var43 = aliases.iterator();

                        while(var43.hasNext()) {
                           alias a = (alias)var43.next();
                           if (a != null) {
                              if (a.getInternalName().equals("print_function")) {
                                 this.printFunction = true;
                              } else if (a.getInternalName().equals("unicode_literals")) {
                                 this.unicodeLiterals = true;
                              }
                           }
                        }
                     }

                     stype = new ImportFrom(FROM108, this.actions.makeFromText(list_d, dotted_name109 != null ? dotted_name109.names : null), this.actions.makeModuleNameNode(list_d, dotted_name109 != null ? dotted_name109.names : null), this.actions.makeAliases(i1 != null ? i1.atypes : null), this.actions.makeLevel(list_d));
                  }
                  break;
               case 3:
                  LPAREN112 = (Token)this.match(this.input, 43, FOLLOW_LPAREN_in_import_from3038);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LPAREN112_tree = (PythonTree)this.adaptor.create(LPAREN112);
                     this.adaptor.addChild(root_0, LPAREN112_tree);
                  }

                  this.pushFollow(FOLLOW_import_as_names_in_import_from3042);
                  i2 = this.import_as_names();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, i2.getTree());
                  }

                  int alt53 = 2;
                  LA53_0 = this.input.LA(1);
                  if (LA53_0 == 47) {
                     alt53 = 1;
                  }

                  switch (alt53) {
                     case 1:
                        COMMA113 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_import_from3044);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           COMMA113_tree = (PythonTree)this.adaptor.create(COMMA113);
                           this.adaptor.addChild(root_0, COMMA113_tree);
                        }
                  }

                  RPAREN114 = (Token)this.match(this.input, 44, FOLLOW_RPAREN_in_import_from3047);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RPAREN114_tree = (PythonTree)this.adaptor.create(RPAREN114);
                     this.adaptor.addChild(root_0, RPAREN114_tree);
                  }

                  if (this.state.backtracking == 0) {
                     String dottedText = dotted_name109 != null ? this.input.toString(dotted_name109.start, dotted_name109.stop) : null;
                     if (dottedText != null && dottedText.equals("__future__")) {
                        List aliases = i2 != null ? i2.atypes : null;
                        Iterator var28 = aliases.iterator();

                        while(var28.hasNext()) {
                           alias a = (alias)var28.next();
                           if (a != null) {
                              if (a.getInternalName().equals("print_function")) {
                                 this.printFunction = true;
                              } else if (a.getInternalName().equals("unicode_literals")) {
                                 this.unicodeLiterals = true;
                              }
                           }
                        }
                     }

                     stype = new ImportFrom(FROM108, this.actions.makeFromText(list_d, dotted_name109 != null ? dotted_name109.names : null), this.actions.makeModuleNameNode(list_d, dotted_name109 != null ? dotted_name109.names : null), this.actions.makeAliases(i2 != null ? i2.atypes : null), this.actions.makeLevel(list_d));
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = stype;
            }
         } catch (RecognitionException var33) {
            this.reportError(var33);
            this.errorHandler.recover(this, this.input, var33);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var33);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final import_as_names_return import_as_names() throws RecognitionException {
      import_as_names_return retval = new import_as_names_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token COMMA115 = null;
      List list_n = null;
      import_as_name_return n = null;
      n = null;
      PythonTree COMMA115_tree = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_import_as_name_in_import_as_names3096);
         n = this.import_as_name();
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, n.getTree());
            }

            if (list_n == null) {
               list_n = new ArrayList();
            }

            list_n.add(n.getTree());

            while(true) {
               int alt55 = 2;
               int LA55_0 = this.input.LA(1);
               if (LA55_0 == 47) {
                  int LA55_2 = this.input.LA(2);
                  if (LA55_2 == 9) {
                     alt55 = 1;
                  }
               }

               switch (alt55) {
                  case 1:
                     COMMA115 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_import_as_names3099);
                     if (this.state.failed) {
                        return retval;
                     }

                     this.pushFollow(FOLLOW_import_as_name_in_import_as_names3104);
                     n = this.import_as_name();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, n.getTree());
                     }

                     if (list_n == null) {
                        list_n = new ArrayList();
                     }

                     list_n.add(n.getTree());
                     break;
                  default:
                     if (this.state.backtracking == 0) {
                        retval.atypes = list_n;
                     }

                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         this.errorHandler.recover(this, this.input, var13);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         return retval;
      } finally {
         ;
      }
   }

   public final import_as_name_return import_as_name() throws RecognitionException {
      import_as_name_return retval = new import_as_name_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token name = null;
      Token asname = null;
      Token AS116 = null;
      PythonTree name_tree = null;
      PythonTree asname_tree = null;
      PythonTree AS116_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            name = (Token)this.match(this.input, 9, FOLLOW_NAME_in_import_as_name3145);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               name_tree = (PythonTree)this.adaptor.create(name);
               this.adaptor.addChild(root_0, name_tree);
            }

            int alt56 = 2;
            int LA56_0 = this.input.LA(1);
            if (LA56_0 == 13) {
               alt56 = 1;
            }

            switch (alt56) {
               case 1:
                  AS116 = (Token)this.match(this.input, 13, FOLLOW_AS_in_import_as_name3148);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     AS116_tree = (PythonTree)this.adaptor.create(AS116);
                     this.adaptor.addChild(root_0, AS116_tree);
                  }

                  asname = (Token)this.match(this.input, 9, FOLLOW_NAME_in_import_as_name3152);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     asname_tree = (PythonTree)this.adaptor.create(asname);
                     this.adaptor.addChild(root_0, asname_tree);
                  }
               default:
                  if (this.state.backtracking == 0) {
                     retval.atype = new alias(this.actions.makeNameNode(name), this.actions.makeNameNode(asname));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = retval.atype;
                  }
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.errorHandler.recover(this, this.input, var15);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final dotted_as_name_return dotted_as_name() throws RecognitionException {
      dotted_as_name_return retval = new dotted_as_name_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token asname = null;
      Token AS118 = null;
      dotted_name_return dotted_name117 = null;
      PythonTree asname_tree = null;
      PythonTree AS118_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_dotted_name_in_dotted_as_name3192);
            dotted_name117 = this.dotted_name();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, dotted_name117.getTree());
            }

            int alt57 = 2;
            int LA57_0 = this.input.LA(1);
            if (LA57_0 == 13) {
               alt57 = 1;
            }

            switch (alt57) {
               case 1:
                  AS118 = (Token)this.match(this.input, 13, FOLLOW_AS_in_dotted_as_name3195);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     AS118_tree = (PythonTree)this.adaptor.create(AS118);
                     this.adaptor.addChild(root_0, AS118_tree);
                  }

                  asname = (Token)this.match(this.input, 9, FOLLOW_NAME_in_dotted_as_name3199);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     asname_tree = (PythonTree)this.adaptor.create(asname);
                     this.adaptor.addChild(root_0, asname_tree);
                  }
               default:
                  if (this.state.backtracking == 0) {
                     retval.atype = new alias(dotted_name117 != null ? dotted_name117.names : null, this.actions.makeNameNode(asname));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = retval.atype;
                  }
            }
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.errorHandler.recover(this, this.input, var14);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var14);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final dotted_as_names_return dotted_as_names() throws RecognitionException {
      dotted_as_names_return retval = new dotted_as_names_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token COMMA119 = null;
      List list_d = null;
      dotted_as_name_return d = null;
      d = null;
      PythonTree COMMA119_tree = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_dotted_as_name_in_dotted_as_names3235);
         d = this.dotted_as_name();
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, d.getTree());
            }

            if (list_d == null) {
               list_d = new ArrayList();
            }

            list_d.add(d.getTree());

            while(true) {
               int alt58 = 2;
               int LA58_0 = this.input.LA(1);
               if (LA58_0 == 47) {
                  alt58 = 1;
               }

               switch (alt58) {
                  case 1:
                     COMMA119 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_dotted_as_names3238);
                     if (this.state.failed) {
                        return retval;
                     }

                     this.pushFollow(FOLLOW_dotted_as_name_in_dotted_as_names3243);
                     d = this.dotted_as_name();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, d.getTree());
                     }

                     if (list_d == null) {
                        list_d = new ArrayList();
                     }

                     list_d.add(d.getTree());
                     break;
                  default:
                     if (this.state.backtracking == 0) {
                        retval.atypes = list_d;
                     }

                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         this.errorHandler.recover(this, this.input, var13);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         return retval;
      } finally {
         ;
      }
   }

   public final dotted_name_return dotted_name() throws RecognitionException {
      dotted_name_return retval = new dotted_name_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token NAME120 = null;
      Token DOT121 = null;
      List list_dn = null;
      attr_return dn = null;
      dn = null;
      PythonTree NAME120_tree = null;
      PythonTree DOT121_tree = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         NAME120 = (Token)this.match(this.input, 9, FOLLOW_NAME_in_dotted_name3277);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               NAME120_tree = (PythonTree)this.adaptor.create(NAME120);
               this.adaptor.addChild(root_0, NAME120_tree);
            }

            while(true) {
               int alt59 = 2;
               int LA59_0 = this.input.LA(1);
               if (LA59_0 == 10) {
                  alt59 = 1;
               }

               switch (alt59) {
                  case 1:
                     DOT121 = (Token)this.match(this.input, 10, FOLLOW_DOT_in_dotted_name3280);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        DOT121_tree = (PythonTree)this.adaptor.create(DOT121);
                        this.adaptor.addChild(root_0, DOT121_tree);
                     }

                     this.pushFollow(FOLLOW_attr_in_dotted_name3284);
                     dn = this.attr();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, dn.getTree());
                     }

                     if (list_dn == null) {
                        list_dn = new ArrayList();
                     }

                     list_dn.add(dn.getTree());
                     break;
                  default:
                     if (this.state.backtracking == 0) {
                        retval.names = this.actions.makeDottedName(NAME120, list_dn);
                     }

                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var15) {
         this.reportError(var15);
         this.errorHandler.recover(this, this.input, var15);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         return retval;
      } finally {
         ;
      }
   }

   public final global_stmt_return global_stmt() throws RecognitionException {
      global_stmt_return retval = new global_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token GLOBAL122 = null;
      Token COMMA123 = null;
      Token n = null;
      List list_n = null;
      PythonTree GLOBAL122_tree = null;
      PythonTree COMMA123_tree = null;
      PythonTree n_tree = null;
      stmt stype = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         GLOBAL122 = (Token)this.match(this.input, 26, FOLLOW_GLOBAL_in_global_stmt3320);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               GLOBAL122_tree = (PythonTree)this.adaptor.create(GLOBAL122);
               this.adaptor.addChild(root_0, GLOBAL122_tree);
            }

            n = (Token)this.match(this.input, 9, FOLLOW_NAME_in_global_stmt3324);
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  n_tree = (PythonTree)this.adaptor.create(n);
                  this.adaptor.addChild(root_0, n_tree);
               }

               if (list_n == null) {
                  list_n = new ArrayList();
               }

               list_n.add(n);

               while(true) {
                  int alt60 = 2;
                  int LA60_0 = this.input.LA(1);
                  if (LA60_0 == 47) {
                     alt60 = 1;
                  }

                  switch (alt60) {
                     case 1:
                        COMMA123 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_global_stmt3327);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           COMMA123_tree = (PythonTree)this.adaptor.create(COMMA123);
                           this.adaptor.addChild(root_0, COMMA123_tree);
                        }

                        n = (Token)this.match(this.input, 9, FOLLOW_NAME_in_global_stmt3331);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           n_tree = (PythonTree)this.adaptor.create(n);
                           this.adaptor.addChild(root_0, n_tree);
                        }

                        if (list_n == null) {
                           list_n = new ArrayList();
                        }

                        list_n.add(n);
                        break;
                     default:
                        if (this.state.backtracking == 0) {
                           stype = new Global(GLOBAL122, this.actions.makeNames(list_n), this.actions.makeNameNodes(list_n));
                        }

                        retval.stop = this.input.LT(-1);
                        if (this.state.backtracking == 0) {
                           retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = stype;
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var17) {
         this.reportError(var17);
         this.errorHandler.recover(this, this.input, var17);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         return retval;
      } finally {
         ;
      }
   }

   public final exec_stmt_return exec_stmt() throws RecognitionException {
      exec_stmt_return retval = new exec_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token EXEC124 = null;
      Token IN126 = null;
      Token COMMA127 = null;
      test_return t1 = null;
      test_return t2 = null;
      expr_return expr125 = null;
      PythonTree EXEC124_tree = null;
      PythonTree IN126_tree = null;
      PythonTree COMMA127_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            EXEC124 = (Token)this.match(this.input, 22, FOLLOW_EXEC_in_exec_stmt3369);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               EXEC124_tree = (PythonTree)this.adaptor.create(EXEC124);
               this.adaptor.addChild(root_0, EXEC124_tree);
            }

            this.pushFollow(FOLLOW_expr_in_exec_stmt3371);
            expr125 = this.expr(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, expr125.getTree());
            }

            int alt62 = 2;
            int LA62_0 = this.input.LA(1);
            if (LA62_0 == 29) {
               alt62 = 1;
            }

            switch (alt62) {
               case 1:
                  IN126 = (Token)this.match(this.input, 29, FOLLOW_IN_in_exec_stmt3375);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     IN126_tree = (PythonTree)this.adaptor.create(IN126);
                     this.adaptor.addChild(root_0, IN126_tree);
                  }

                  this.pushFollow(FOLLOW_test_in_exec_stmt3379);
                  t1 = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t1.getTree());
                  }

                  int alt61 = 2;
                  int LA61_0 = this.input.LA(1);
                  if (LA61_0 == 47) {
                     alt61 = 1;
                  }

                  switch (alt61) {
                     case 1:
                        COMMA127 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_exec_stmt3383);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           COMMA127_tree = (PythonTree)this.adaptor.create(COMMA127);
                           this.adaptor.addChild(root_0, COMMA127_tree);
                        }

                        this.pushFollow(FOLLOW_test_in_exec_stmt3387);
                        t2 = this.test(expr_contextType.Load);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, t2.getTree());
                        }
                  }
               default:
                  if (this.state.backtracking == 0) {
                     stype = new Exec(EXEC124, this.actions.castExpr(expr125 != null ? expr125.tree : null), this.actions.castExpr(t1 != null ? t1.tree : null), this.actions.castExpr(t2 != null ? t2.tree : null));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = stype;
                  }
            }
         } catch (RecognitionException var21) {
            this.reportError(var21);
            this.errorHandler.recover(this, this.input, var21);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var21);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final assert_stmt_return assert_stmt() throws RecognitionException {
      assert_stmt_return retval = new assert_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token ASSERT128 = null;
      Token COMMA129 = null;
      test_return t1 = null;
      test_return t2 = null;
      PythonTree ASSERT128_tree = null;
      PythonTree COMMA129_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            ASSERT128 = (Token)this.match(this.input, 14, FOLLOW_ASSERT_in_assert_stmt3428);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               ASSERT128_tree = (PythonTree)this.adaptor.create(ASSERT128);
               this.adaptor.addChild(root_0, ASSERT128_tree);
            }

            this.pushFollow(FOLLOW_test_in_assert_stmt3432);
            t1 = this.test(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, t1.getTree());
            }

            int alt63 = 2;
            int LA63_0 = this.input.LA(1);
            if (LA63_0 == 47) {
               alt63 = 1;
            }

            switch (alt63) {
               case 1:
                  COMMA129 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_assert_stmt3436);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     COMMA129_tree = (PythonTree)this.adaptor.create(COMMA129);
                     this.adaptor.addChild(root_0, COMMA129_tree);
                  }

                  this.pushFollow(FOLLOW_test_in_assert_stmt3440);
                  t2 = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t2.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     stype = new Assert(ASSERT128, this.actions.castExpr(t1 != null ? t1.tree : null), this.actions.castExpr(t2 != null ? t2.tree : null));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = stype;
                  }
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.errorHandler.recover(this, this.input, var16);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final compound_stmt_return compound_stmt() throws RecognitionException {
      compound_stmt_return retval = new compound_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      if_stmt_return if_stmt130 = null;
      while_stmt_return while_stmt131 = null;
      for_stmt_return for_stmt132 = null;
      try_stmt_return try_stmt133 = null;
      with_stmt_return with_stmt134 = null;
      funcdef_return funcdef135 = null;
      classdef_return classdef136 = null;

      try {
         try {
            int alt64 = true;
            int LA64_0 = this.input.LA(1);
            byte alt64;
            if (LA64_0 == 27) {
               alt64 = 1;
            } else if (LA64_0 == 39) {
               alt64 = 2;
            } else if (LA64_0 == 25) {
               alt64 = 3;
            } else if (LA64_0 == 38) {
               alt64 = 4;
            } else if (LA64_0 == 40) {
               alt64 = 5;
            } else if (LA64_0 == 42) {
               int LA64_6 = this.input.LA(2);
               if (this.synpred6_Python()) {
                  alt64 = 6;
               } else {
                  alt64 = 7;
               }
            } else if (LA64_0 == 18 && this.synpred6_Python()) {
               alt64 = 6;
            } else {
               if (LA64_0 != 16) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 64, 0, this.input);
                  throw nvae;
               }

               alt64 = 7;
            }

            switch (alt64) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_if_stmt_in_compound_stmt3469);
                  if_stmt130 = this.if_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, if_stmt130.getTree());
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_while_stmt_in_compound_stmt3477);
                  while_stmt131 = this.while_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, while_stmt131.getTree());
                  }
                  break;
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_for_stmt_in_compound_stmt3485);
                  for_stmt132 = this.for_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, for_stmt132.getTree());
                  }
                  break;
               case 4:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_try_stmt_in_compound_stmt3493);
                  try_stmt133 = this.try_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, try_stmt133.getTree());
                  }
                  break;
               case 5:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_with_stmt_in_compound_stmt3501);
                  with_stmt134 = this.with_stmt();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, with_stmt134.getTree());
                  }
                  break;
               case 6:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_funcdef_in_compound_stmt3518);
                  funcdef135 = this.funcdef();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, funcdef135.getTree());
                  }
                  break;
               case 7:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_classdef_in_compound_stmt3526);
                  classdef136 = this.classdef();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, classdef136.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.errorHandler.recover(this, this.input, var16);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final if_stmt_return if_stmt() throws RecognitionException {
      if_stmt_return retval = new if_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token IF137 = null;
      Token COLON139 = null;
      suite_return ifsuite = null;
      test_return test138 = null;
      elif_clause_return elif_clause140 = null;
      PythonTree IF137_tree = null;
      PythonTree COLON139_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            IF137 = (Token)this.match(this.input, 27, FOLLOW_IF_in_if_stmt3554);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               IF137_tree = (PythonTree)this.adaptor.create(IF137);
               this.adaptor.addChild(root_0, IF137_tree);
            }

            this.pushFollow(FOLLOW_test_in_if_stmt3556);
            test138 = this.test(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, test138.getTree());
            }

            COLON139 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_if_stmt3559);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               COLON139_tree = (PythonTree)this.adaptor.create(COLON139);
               this.adaptor.addChild(root_0, COLON139_tree);
            }

            this.pushFollow(FOLLOW_suite_in_if_stmt3563);
            ifsuite = this.suite(false);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, ifsuite.getTree());
            }

            int alt65 = 2;
            int LA65_0 = this.input.LA(1);
            if (LA65_0 == 20 || LA65_0 == 34) {
               alt65 = 1;
            }

            switch (alt65) {
               case 1:
                  this.pushFollow(FOLLOW_elif_clause_in_if_stmt3566);
                  elif_clause140 = this.elif_clause();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, elif_clause140.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     stype = new If(IF137, this.actions.castExpr(test138 != null ? test138.tree : null), this.actions.castStmts(ifsuite != null ? ifsuite.stypes : null), this.actions.makeElse(elif_clause140 != null ? elif_clause140.stypes : null, elif_clause140 != null ? elif_clause140.tree : null));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = stype;
                  }
            }
         } catch (RecognitionException var17) {
            this.reportError(var17);
            this.errorHandler.recover(this, this.input, var17);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final elif_clause_return elif_clause() throws RecognitionException {
      elif_clause_return retval = new elif_clause_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token ELIF142 = null;
      Token COLON144 = null;
      elif_clause_return e2 = null;
      else_clause_return else_clause141 = null;
      test_return test143 = null;
      suite_return suite145 = null;
      PythonTree ELIF142_tree = null;
      PythonTree COLON144_tree = null;
      stmt stype = null;

      try {
         try {
            int alt67 = true;
            int LA67_0 = this.input.LA(1);
            byte alt67;
            if (LA67_0 == 34) {
               alt67 = 1;
            } else {
               if (LA67_0 != 20) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 67, 0, this.input);
                  throw nvae;
               }

               alt67 = 2;
            }

            switch (alt67) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_else_clause_in_elif_clause3611);
                  else_clause141 = this.else_clause();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, else_clause141.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.stypes = else_clause141 != null ? else_clause141.stypes : null;
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  ELIF142 = (Token)this.match(this.input, 20, FOLLOW_ELIF_in_elif_clause3627);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ELIF142_tree = (PythonTree)this.adaptor.create(ELIF142);
                     this.adaptor.addChild(root_0, ELIF142_tree);
                  }

                  this.pushFollow(FOLLOW_test_in_elif_clause3629);
                  test143 = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, test143.getTree());
                  }

                  COLON144 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_elif_clause3632);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     COLON144_tree = (PythonTree)this.adaptor.create(COLON144);
                     this.adaptor.addChild(root_0, COLON144_tree);
                  }

                  this.pushFollow(FOLLOW_suite_in_elif_clause3634);
                  suite145 = this.suite(false);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, suite145.getTree());
                  }

                  int alt66 = true;
                  int LA66_0 = this.input.LA(1);
                  byte alt66;
                  if (LA66_0 != 20 && LA66_0 != 34) {
                     if (LA66_0 != -1 && LA66_0 != 5 && LA66_0 != 7 && LA66_0 != 9 && LA66_0 != 11 && (LA66_0 < 14 || LA66_0 > 19) && LA66_0 != 22 && (LA66_0 < 24 || LA66_0 > 28) && (LA66_0 < 31 || LA66_0 > 32) && (LA66_0 < 35 || LA66_0 > 43) && (LA66_0 < 75 || LA66_0 > 76) && (LA66_0 < 80 || LA66_0 > 81) && LA66_0 != 83 && (LA66_0 < 85 || LA66_0 > 90)) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 66, 0, this.input);
                        throw nvae;
                     }

                     alt66 = 2;
                  } else {
                     alt66 = 1;
                  }

                  switch (alt66) {
                     case 1:
                        this.pushFollow(FOLLOW_elif_clause_in_elif_clause3646);
                        e2 = this.elif_clause();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, e2.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           stype = new If(test143 != null ? test143.start : null, this.actions.castExpr(test143 != null ? test143.tree : null), this.actions.castStmts(suite145 != null ? suite145.stypes : null), this.actions.makeElse(e2 != null ? e2.stypes : null, e2 != null ? e2.tree : null));
                        }
                        break;
                     case 2:
                        if (this.state.backtracking == 0) {
                           stype = new If(test143 != null ? test143.start : null, this.actions.castExpr(test143 != null ? test143.tree : null), this.actions.castStmts(suite145 != null ? suite145.stypes : null), new ArrayList());
                        }
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && stype != null) {
               retval.tree = stype;
            }
         } catch (RecognitionException var20) {
            this.reportError(var20);
            this.errorHandler.recover(this, this.input, var20);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var20);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final else_clause_return else_clause() throws RecognitionException {
      else_clause_return retval = new else_clause_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token ORELSE146 = null;
      Token COLON147 = null;
      suite_return elsesuite = null;
      PythonTree ORELSE146_tree = null;
      PythonTree COLON147_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            ORELSE146 = (Token)this.match(this.input, 34, FOLLOW_ORELSE_in_else_clause3706);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               ORELSE146_tree = (PythonTree)this.adaptor.create(ORELSE146);
               this.adaptor.addChild(root_0, ORELSE146_tree);
            }

            COLON147 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_else_clause3708);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               COLON147_tree = (PythonTree)this.adaptor.create(COLON147);
               this.adaptor.addChild(root_0, COLON147_tree);
            }

            this.pushFollow(FOLLOW_suite_in_else_clause3712);
            elsesuite = this.suite(false);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, elsesuite.getTree());
            }

            if (this.state.backtracking == 0) {
               retval.stypes = elsesuite != null ? elsesuite.stypes : null;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var12) {
            this.reportError(var12);
            this.errorHandler.recover(this, this.input, var12);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var12);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final while_stmt_return while_stmt() throws RecognitionException {
      while_stmt_return retval = new while_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token WHILE148 = null;
      Token COLON150 = null;
      Token ORELSE151 = null;
      Token COLON152 = null;
      suite_return s1 = null;
      suite_return s2 = null;
      test_return test149 = null;
      PythonTree WHILE148_tree = null;
      PythonTree COLON150_tree = null;
      PythonTree ORELSE151_tree = null;
      PythonTree COLON152_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            WHILE148 = (Token)this.match(this.input, 39, FOLLOW_WHILE_in_while_stmt3749);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               WHILE148_tree = (PythonTree)this.adaptor.create(WHILE148);
               this.adaptor.addChild(root_0, WHILE148_tree);
            }

            this.pushFollow(FOLLOW_test_in_while_stmt3751);
            test149 = this.test(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, test149.getTree());
            }

            COLON150 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_while_stmt3754);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               COLON150_tree = (PythonTree)this.adaptor.create(COLON150);
               this.adaptor.addChild(root_0, COLON150_tree);
            }

            this.pushFollow(FOLLOW_suite_in_while_stmt3758);
            s1 = this.suite(false);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, s1.getTree());
            }

            int alt68 = 2;
            int LA68_0 = this.input.LA(1);
            if (LA68_0 == 34) {
               alt68 = 1;
            }

            switch (alt68) {
               case 1:
                  ORELSE151 = (Token)this.match(this.input, 34, FOLLOW_ORELSE_in_while_stmt3762);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ORELSE151_tree = (PythonTree)this.adaptor.create(ORELSE151);
                     this.adaptor.addChild(root_0, ORELSE151_tree);
                  }

                  COLON152 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_while_stmt3764);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     COLON152_tree = (PythonTree)this.adaptor.create(COLON152);
                     this.adaptor.addChild(root_0, COLON152_tree);
                  }

                  this.pushFollow(FOLLOW_suite_in_while_stmt3768);
                  s2 = this.suite(false);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, s2.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     stype = this.actions.makeWhile(WHILE148, this.actions.castExpr(test149 != null ? test149.tree : null), s1 != null ? s1.stypes : null, s2 != null ? s2.stypes : null);
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = stype;
                  }
            }
         } catch (RecognitionException var21) {
            this.reportError(var21);
            this.errorHandler.recover(this, this.input, var21);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var21);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final for_stmt_return for_stmt() throws RecognitionException {
      for_stmt_return retval = new for_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token FOR153 = null;
      Token IN155 = null;
      Token COLON157 = null;
      Token ORELSE158 = null;
      Token COLON159 = null;
      suite_return s1 = null;
      suite_return s2 = null;
      exprlist_return exprlist154 = null;
      testlist_return testlist156 = null;
      PythonTree FOR153_tree = null;
      PythonTree IN155_tree = null;
      PythonTree COLON157_tree = null;
      PythonTree ORELSE158_tree = null;
      PythonTree COLON159_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            FOR153 = (Token)this.match(this.input, 25, FOLLOW_FOR_in_for_stmt3807);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               FOR153_tree = (PythonTree)this.adaptor.create(FOR153);
               this.adaptor.addChild(root_0, FOR153_tree);
            }

            this.pushFollow(FOLLOW_exprlist_in_for_stmt3809);
            exprlist154 = this.exprlist(expr_contextType.Store);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, exprlist154.getTree());
            }

            IN155 = (Token)this.match(this.input, 29, FOLLOW_IN_in_for_stmt3812);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               IN155_tree = (PythonTree)this.adaptor.create(IN155);
               this.adaptor.addChild(root_0, IN155_tree);
            }

            this.pushFollow(FOLLOW_testlist_in_for_stmt3814);
            testlist156 = this.testlist(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, testlist156.getTree());
            }

            COLON157 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_for_stmt3817);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               COLON157_tree = (PythonTree)this.adaptor.create(COLON157);
               this.adaptor.addChild(root_0, COLON157_tree);
            }

            this.pushFollow(FOLLOW_suite_in_for_stmt3821);
            s1 = this.suite(false);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, s1.getTree());
            }

            int alt69 = 2;
            int LA69_0 = this.input.LA(1);
            if (LA69_0 == 34) {
               alt69 = 1;
            }

            switch (alt69) {
               case 1:
                  ORELSE158 = (Token)this.match(this.input, 34, FOLLOW_ORELSE_in_for_stmt3833);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ORELSE158_tree = (PythonTree)this.adaptor.create(ORELSE158);
                     this.adaptor.addChild(root_0, ORELSE158_tree);
                  }

                  COLON159 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_for_stmt3835);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     COLON159_tree = (PythonTree)this.adaptor.create(COLON159);
                     this.adaptor.addChild(root_0, COLON159_tree);
                  }

                  this.pushFollow(FOLLOW_suite_in_for_stmt3839);
                  s2 = this.suite(false);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, s2.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     stype = this.actions.makeFor(FOR153, exprlist154 != null ? exprlist154.etype : null, this.actions.castExpr(testlist156 != null ? testlist156.tree : null), s1 != null ? s1.stypes : null, s2 != null ? s2.stypes : null);
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = stype;
                  }
            }
         } catch (RecognitionException var24) {
            this.reportError(var24);
            this.errorHandler.recover(this, this.input, var24);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var24);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final try_stmt_return try_stmt() throws RecognitionException {
      try_stmt_return retval = new try_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token TRY160 = null;
      Token COLON161 = null;
      Token ORELSE162 = null;
      Token COLON163 = null;
      Token FINALLY164 = null;
      Token COLON165 = null;
      Token FINALLY166 = null;
      Token COLON167 = null;
      List list_e = null;
      suite_return trysuite = null;
      suite_return elsesuite = null;
      suite_return finalsuite = null;
      except_clause_return e = null;
      e = null;
      PythonTree TRY160_tree = null;
      PythonTree COLON161_tree = null;
      PythonTree ORELSE162_tree = null;
      PythonTree COLON163_tree = null;
      PythonTree FINALLY164_tree = null;
      PythonTree COLON165_tree = null;
      PythonTree FINALLY166_tree = null;
      PythonTree COLON167_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            TRY160 = (Token)this.match(this.input, 38, FOLLOW_TRY_in_try_stmt3882);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               TRY160_tree = (PythonTree)this.adaptor.create(TRY160);
               this.adaptor.addChild(root_0, TRY160_tree);
            }

            COLON161 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_try_stmt3884);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               COLON161_tree = (PythonTree)this.adaptor.create(COLON161);
               this.adaptor.addChild(root_0, COLON161_tree);
            }

            this.pushFollow(FOLLOW_suite_in_try_stmt3888);
            trysuite = this.suite(!this.suite_stack.isEmpty() && ((suite_scope)this.suite_stack.peek()).continueIllegal);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, trysuite.getTree());
            }

            int alt73 = true;
            int LA73_0 = this.input.LA(1);
            byte alt73;
            if (LA73_0 == 21) {
               alt73 = 1;
            } else {
               if (LA73_0 != 23) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 73, 0, this.input);
                  throw nvae;
               }

               alt73 = 2;
            }

            label482:
            switch (alt73) {
               case 1:
                  int cnt70 = 0;

                  while(true) {
                     int alt71 = 2;
                     int LA71_0 = this.input.LA(1);
                     if (LA71_0 == 21) {
                        alt71 = 1;
                     }

                     switch (alt71) {
                        case 1:
                           this.pushFollow(FOLLOW_except_clause_in_try_stmt3901);
                           e = this.except_clause();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, e.getTree());
                           }

                           if (list_e == null) {
                              list_e = new ArrayList();
                           }

                           list_e.add(e.getTree());
                           ++cnt70;
                           break;
                        default:
                           if (cnt70 >= 1) {
                              alt71 = 2;
                              LA71_0 = this.input.LA(1);
                              if (LA71_0 == 34) {
                                 alt71 = 1;
                              }

                              switch (alt71) {
                                 case 1:
                                    ORELSE162 = (Token)this.match(this.input, 34, FOLLOW_ORELSE_in_try_stmt3905);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       ORELSE162_tree = (PythonTree)this.adaptor.create(ORELSE162);
                                       this.adaptor.addChild(root_0, ORELSE162_tree);
                                    }

                                    COLON163 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_try_stmt3907);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       COLON163_tree = (PythonTree)this.adaptor.create(COLON163);
                                       this.adaptor.addChild(root_0, COLON163_tree);
                                    }

                                    this.pushFollow(FOLLOW_suite_in_try_stmt3911);
                                    elsesuite = this.suite(!this.suite_stack.isEmpty() && ((suite_scope)this.suite_stack.peek()).continueIllegal);
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       this.adaptor.addChild(root_0, elsesuite.getTree());
                                    }
                                 default:
                                    int alt72 = 2;
                                    int LA72_0 = this.input.LA(1);
                                    if (LA72_0 == 23) {
                                       alt72 = 1;
                                    }

                                    switch (alt72) {
                                       case 1:
                                          FINALLY164 = (Token)this.match(this.input, 23, FOLLOW_FINALLY_in_try_stmt3917);
                                          if (this.state.failed) {
                                             return retval;
                                          }

                                          if (this.state.backtracking == 0) {
                                             FINALLY164_tree = (PythonTree)this.adaptor.create(FINALLY164);
                                             this.adaptor.addChild(root_0, FINALLY164_tree);
                                          }

                                          COLON165 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_try_stmt3919);
                                          if (this.state.failed) {
                                             return retval;
                                          }

                                          if (this.state.backtracking == 0) {
                                             COLON165_tree = (PythonTree)this.adaptor.create(COLON165);
                                             this.adaptor.addChild(root_0, COLON165_tree);
                                          }

                                          this.pushFollow(FOLLOW_suite_in_try_stmt3923);
                                          finalsuite = this.suite(true);
                                          --this.state._fsp;
                                          if (this.state.failed) {
                                             return retval;
                                          }

                                          if (this.state.backtracking == 0) {
                                             this.adaptor.addChild(root_0, finalsuite.getTree());
                                          }
                                       default:
                                          if (this.state.backtracking == 0) {
                                             stype = this.actions.makeTryExcept(TRY160, trysuite != null ? trysuite.stypes : null, list_e, elsesuite != null ? elsesuite.stypes : null, finalsuite != null ? finalsuite.stypes : null);
                                          }
                                          break label482;
                                    }
                              }
                           }

                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           EarlyExitException eee = new EarlyExitException(70, this.input);
                           throw eee;
                     }
                  }
               case 2:
                  FINALLY166 = (Token)this.match(this.input, 23, FOLLOW_FINALLY_in_try_stmt3946);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     FINALLY166_tree = (PythonTree)this.adaptor.create(FINALLY166);
                     this.adaptor.addChild(root_0, FINALLY166_tree);
                  }

                  COLON167 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_try_stmt3948);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     COLON167_tree = (PythonTree)this.adaptor.create(COLON167);
                     this.adaptor.addChild(root_0, COLON167_tree);
                  }

                  this.pushFollow(FOLLOW_suite_in_try_stmt3952);
                  finalsuite = this.suite(true);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, finalsuite.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     stype = this.actions.makeTryFinally(TRY160, trysuite != null ? trysuite.stypes : null, finalsuite != null ? finalsuite.stypes : null);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)stype;
            }
         } catch (RecognitionException var36) {
            this.reportError(var36);
            this.errorHandler.recover(this, this.input, var36);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var36);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final with_stmt_return with_stmt() throws RecognitionException {
      with_stmt_return retval = new with_stmt_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token WITH168 = null;
      Token COMMA169 = null;
      Token COLON170 = null;
      List list_w = null;
      suite_return suite171 = null;
      with_item_return w = null;
      w = null;
      PythonTree WITH168_tree = null;
      PythonTree COMMA169_tree = null;
      PythonTree COLON170_tree = null;
      stmt stype = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         WITH168 = (Token)this.match(this.input, 40, FOLLOW_WITH_in_with_stmt4001);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               WITH168_tree = (PythonTree)this.adaptor.create(WITH168);
               this.adaptor.addChild(root_0, WITH168_tree);
            }

            this.pushFollow(FOLLOW_with_item_in_with_stmt4005);
            w = this.with_item();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, w.getTree());
               }

               if (list_w == null) {
                  list_w = new ArrayList();
               }

               list_w.add(w.getTree());

               while(true) {
                  int alt74 = 2;
                  int LA74_0 = this.input.LA(1);
                  if (LA74_0 == 47) {
                     alt74 = 1;
                  }

                  switch (alt74) {
                     case 1:
                        COMMA169 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_with_stmt4015);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           COMMA169_tree = (PythonTree)this.adaptor.create(COMMA169);
                           this.adaptor.addChild(root_0, COMMA169_tree);
                        }

                        this.pushFollow(FOLLOW_with_item_in_with_stmt4019);
                        w = this.with_item();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, w.getTree());
                        }

                        if (list_w == null) {
                           list_w = new ArrayList();
                        }

                        list_w.add(w.getTree());
                        break;
                     default:
                        COLON170 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_with_stmt4023);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           COLON170_tree = (PythonTree)this.adaptor.create(COLON170);
                           this.adaptor.addChild(root_0, COLON170_tree);
                        }

                        this.pushFollow(FOLLOW_suite_in_with_stmt4025);
                        suite171 = this.suite(false);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, suite171.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           stype = this.actions.makeWith(WITH168, list_w, suite171 != null ? suite171.stypes : null);
                        }

                        retval.stop = this.input.LT(-1);
                        if (this.state.backtracking == 0) {
                           retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = stype;
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var19) {
         this.reportError(var19);
         this.errorHandler.recover(this, this.input, var19);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var19);
         return retval;
      } finally {
         ;
      }
   }

   public final with_item_return with_item() throws RecognitionException {
      with_item_return retval = new with_item_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token AS173 = null;
      test_return test172 = null;
      expr_return expr174 = null;
      PythonTree AS173_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_test_in_with_item4062);
            test172 = this.test(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, test172.getTree());
            }

            int alt75 = 2;
            int LA75_0 = this.input.LA(1);
            if (LA75_0 == 13) {
               alt75 = 1;
            }

            switch (alt75) {
               case 1:
                  AS173 = (Token)this.match(this.input, 13, FOLLOW_AS_in_with_item4066);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     AS173_tree = (PythonTree)this.adaptor.create(AS173);
                     this.adaptor.addChild(root_0, AS173_tree);
                  }

                  this.pushFollow(FOLLOW_expr_in_with_item4068);
                  expr174 = this.expr(expr_contextType.Store);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, expr174.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     expr item = this.actions.castExpr(test172 != null ? test172.tree : null);
                     expr var = null;
                     if ((expr174 != null ? expr174.start : null) != null) {
                        var = this.actions.castExpr(expr174 != null ? expr174.tree : null);
                        this.actions.checkAssign(var);
                     }

                     stype = new With(test172 != null ? test172.start : null, item, var, (List)null);
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = stype;
                  }
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.errorHandler.recover(this, this.input, var15);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final except_clause_return except_clause() throws RecognitionException {
      except_clause_return retval = new except_clause_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token EXCEPT175 = null;
      Token set176 = null;
      Token COLON177 = null;
      test_return t1 = null;
      test_return t2 = null;
      suite_return suite178 = null;
      PythonTree EXCEPT175_tree = null;
      PythonTree set176_tree = null;
      PythonTree COLON177_tree = null;
      excepthandler extype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            EXCEPT175 = (Token)this.match(this.input, 21, FOLLOW_EXCEPT_in_except_clause4107);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               EXCEPT175_tree = (PythonTree)this.adaptor.create(EXCEPT175);
               this.adaptor.addChild(root_0, EXCEPT175_tree);
            }

            int alt77 = 2;
            int LA77_0 = this.input.LA(1);
            if (LA77_0 != 9 && LA77_0 != 32 && LA77_0 != 43 && (LA77_0 < 75 || LA77_0 > 76) && (LA77_0 < 80 || LA77_0 > 81) && LA77_0 != 83 && LA77_0 != 85) {
               if (LA77_0 == 11 && this.printFunction) {
                  alt77 = 1;
               } else if (LA77_0 == 31 || LA77_0 >= 86 && LA77_0 <= 90) {
                  alt77 = 1;
               }
            } else {
               alt77 = 1;
            }

            switch (alt77) {
               case 1:
                  this.pushFollow(FOLLOW_test_in_except_clause4112);
                  t1 = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t1.getTree());
                  }

                  int alt76 = 2;
                  int LA76_0 = this.input.LA(1);
                  if (LA76_0 == 13 || LA76_0 == 47) {
                     alt76 = 1;
                  }

                  switch (alt76) {
                     case 1:
                        set176 = this.input.LT(1);
                        if (this.input.LA(1) != 13 && this.input.LA(1) != 47) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                           throw mse;
                        }

                        this.input.consume();
                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, (PythonTree)this.adaptor.create(set176));
                        }

                        this.state.errorRecovery = false;
                        this.state.failed = false;
                        this.pushFollow(FOLLOW_test_in_except_clause4126);
                        t2 = this.test(expr_contextType.Store);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, t2.getTree());
                        }
                  }
               default:
                  COLON177 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_except_clause4133);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     COLON177_tree = (PythonTree)this.adaptor.create(COLON177);
                     this.adaptor.addChild(root_0, COLON177_tree);
                  }

                  this.pushFollow(FOLLOW_suite_in_except_clause4135);
                  suite178 = this.suite(!this.suite_stack.isEmpty() && ((suite_scope)this.suite_stack.peek()).continueIllegal);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, suite178.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     extype = new ExceptHandler(EXCEPT175, this.actions.castExpr(t1 != null ? t1.tree : null), this.actions.castExpr(t2 != null ? t2.tree : null), this.actions.castStmts(suite178 != null ? suite178.stypes : null));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = extype;
                  }
            }
         } catch (RecognitionException var21) {
            this.reportError(var21);
            this.errorHandler.recover(this, this.input, var21);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var21);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final suite_return suite(boolean fromFinally) throws RecognitionException {
      this.suite_stack.push(new suite_scope());
      suite_return retval = new suite_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token NEWLINE180 = null;
      Token INDENT181 = null;
      Token DEDENT183 = null;
      simple_stmt_return simple_stmt179 = null;
      stmt_return stmt182 = null;
      PythonTree NEWLINE180_tree = null;
      PythonTree INDENT181_tree = null;
      PythonTree DEDENT183_tree = null;
      if (!((suite_scope)this.suite_stack.peek()).continueIllegal && !fromFinally) {
         ((suite_scope)this.suite_stack.peek()).continueIllegal = false;
      } else {
         ((suite_scope)this.suite_stack.peek()).continueIllegal = true;
      }

      retval.stypes = new ArrayList();

      try {
         int alt79 = true;
         int LA79_0 = this.input.LA(1);
         byte alt79;
         suite_return var24;
         if (LA79_0 != 9 && LA79_0 != 32 && LA79_0 != 43 && (LA79_0 < 75 || LA79_0 > 76) && (LA79_0 < 80 || LA79_0 > 81) && LA79_0 != 83 && LA79_0 != 85) {
            if (LA79_0 != 11 || !this.printFunction && this.printFunction) {
               if (LA79_0 >= 14 && LA79_0 <= 15 || LA79_0 == 17 || LA79_0 == 19 || LA79_0 == 22 || LA79_0 == 24 || LA79_0 == 26 || LA79_0 == 28 || LA79_0 == 31 || LA79_0 >= 35 && LA79_0 <= 37 || LA79_0 == 41 || LA79_0 >= 86 && LA79_0 <= 90) {
                  alt79 = 1;
               } else {
                  if (LA79_0 != 7) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        var24 = retval;
                        return var24;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 79, 0, this.input);
                     throw nvae;
                  }

                  alt79 = 2;
               }
            } else {
               alt79 = 1;
            }
         } else {
            alt79 = 1;
         }

         switch (alt79) {
            case 1:
               root_0 = (PythonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_simple_stmt_in_suite4181);
               simple_stmt179 = this.simple_stmt();
               --this.state._fsp;
               if (this.state.failed) {
                  var24 = retval;
                  return var24;
               }

               if (this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, simple_stmt179.getTree());
               }

               if (this.state.backtracking == 0) {
                  retval.stypes = simple_stmt179 != null ? simple_stmt179.stypes : null;
               }
               break;
            case 2:
               root_0 = (PythonTree)this.adaptor.nil();
               NEWLINE180 = (Token)this.match(this.input, 7, FOLLOW_NEWLINE_in_suite4197);
               if (this.state.failed) {
                  var24 = retval;
                  return var24;
               }

               if (this.state.backtracking == 0) {
                  NEWLINE180_tree = (PythonTree)this.adaptor.create(NEWLINE180);
                  this.adaptor.addChild(root_0, NEWLINE180_tree);
               }

               INDENT181 = (Token)this.match(this.input, 4, FOLLOW_INDENT_in_suite4199);
               if (this.state.failed) {
                  var24 = retval;
                  return var24;
               }

               if (this.state.backtracking == 0) {
                  INDENT181_tree = (PythonTree)this.adaptor.create(INDENT181);
                  this.adaptor.addChild(root_0, INDENT181_tree);
               }

               int cnt78 = 0;

               label596:
               while(true) {
                  int alt78 = 2;
                  int LA78_0 = this.input.LA(1);
                  if (LA78_0 != 9 && LA78_0 != 32 && LA78_0 != 43 && (LA78_0 < 75 || LA78_0 > 76) && (LA78_0 < 80 || LA78_0 > 81) && LA78_0 != 83 && LA78_0 != 85) {
                     if (LA78_0 != 11 || !this.printFunction && this.printFunction) {
                        if (LA78_0 >= 14 && LA78_0 <= 19 || LA78_0 == 22 || LA78_0 >= 24 && LA78_0 <= 28 || LA78_0 == 31 || LA78_0 >= 35 && LA78_0 <= 42 || LA78_0 >= 86 && LA78_0 <= 90) {
                           alt78 = 1;
                        }
                     } else {
                        alt78 = 1;
                     }
                  } else {
                     alt78 = 1;
                  }

                  suite_return var17;
                  switch (alt78) {
                     case 1:
                        this.pushFollow(FOLLOW_stmt_in_suite4208);
                        stmt182 = this.stmt();
                        --this.state._fsp;
                        if (this.state.failed) {
                           var17 = retval;
                           return var17;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, stmt182.getTree());
                        }

                        if (this.state.backtracking == 0 && (stmt182 != null ? stmt182.stypes : null) != null) {
                           retval.stypes.addAll(stmt182 != null ? stmt182.stypes : null);
                        }

                        ++cnt78;
                        break;
                     default:
                        if (cnt78 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              var17 = retval;
                              return var17;
                           }

                           EarlyExitException eee = new EarlyExitException(78, this.input);
                           throw eee;
                        }

                        DEDENT183 = (Token)this.match(this.input, 5, FOLLOW_DEDENT_in_suite4228);
                        if (this.state.failed) {
                           suite_return var26 = retval;
                           return var26;
                        }

                        if (this.state.backtracking == 0) {
                           DEDENT183_tree = (PythonTree)this.adaptor.create(DEDENT183);
                           this.adaptor.addChild(root_0, DEDENT183_tree);
                        }
                        break label596;
                  }
               }
         }

         retval.stop = this.input.LT(-1);
         if (this.state.backtracking == 0) {
            retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         }
      } catch (RecognitionException var21) {
         this.reportError(var21);
         this.errorHandler.recover(this, this.input, var21);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var21);
      } finally {
         this.suite_stack.pop();
      }

      return retval;
   }

   public final test_return test(expr_contextType ctype) throws RecognitionException {
      test_return retval = new test_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token IF184 = null;
      Token ORELSE185 = null;
      or_test_return o1 = null;
      or_test_return o2 = null;
      test_return e = null;
      lambdef_return lambdef186 = null;
      PythonTree IF184_tree = null;
      PythonTree ORELSE185_tree = null;
      RewriteRuleTokenStream stream_IF = new RewriteRuleTokenStream(this.adaptor, "token IF");
      RewriteRuleTokenStream stream_ORELSE = new RewriteRuleTokenStream(this.adaptor, "token ORELSE");
      RewriteRuleSubtreeStream stream_or_test = new RewriteRuleSubtreeStream(this.adaptor, "rule or_test");
      RewriteRuleSubtreeStream stream_test = new RewriteRuleSubtreeStream(this.adaptor, "rule test");
      expr etype = null;

      try {
         try {
            int alt81 = true;
            int LA81_0 = this.input.LA(1);
            byte alt81;
            if (LA81_0 != 9 && LA81_0 != 32 && LA81_0 != 43 && (LA81_0 < 75 || LA81_0 > 76) && (LA81_0 < 80 || LA81_0 > 81) && LA81_0 != 83 && LA81_0 != 85) {
               if (LA81_0 == 11 && this.printFunction) {
                  alt81 = 1;
               } else if (LA81_0 >= 86 && LA81_0 <= 90) {
                  alt81 = 1;
               } else {
                  if (LA81_0 != 31) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 81, 0, this.input);
                     throw nvae;
                  }

                  alt81 = 2;
               }
            } else {
               alt81 = 1;
            }

            label312:
            switch (alt81) {
               case 1:
                  this.pushFollow(FOLLOW_or_test_in_test4258);
                  o1 = this.or_test(ctype);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_or_test.add(o1.getTree());
                  }

                  int alt80 = true;
                  int alt80 = this.dfa80.predict(this.input);
                  switch (alt80) {
                     case 1:
                        IF184 = (Token)this.match(this.input, 27, FOLLOW_IF_in_test4280);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_IF.add(IF184);
                        }

                        this.pushFollow(FOLLOW_or_test_in_test4284);
                        o2 = this.or_test(ctype);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_or_test.add(o2.getTree());
                        }

                        ORELSE185 = (Token)this.match(this.input, 34, FOLLOW_ORELSE_in_test4287);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ORELSE.add(ORELSE185);
                        }

                        this.pushFollow(FOLLOW_test_in_test4291);
                        e = this.test(expr_contextType.Load);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_test.add(e.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           etype = new IfExp(o1 != null ? o1.start : null, this.actions.castExpr(o2 != null ? o2.tree : null), this.actions.castExpr(o1 != null ? o1.tree : null), this.actions.castExpr(e != null ? e.tree : null));
                        }
                        break label312;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                           root_0 = (PythonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_or_test.nextTree());
                           retval.tree = root_0;
                        }
                     default:
                        break label312;
                  }
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_lambdef_in_test4336);
                  lambdef186 = this.lambdef();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, lambdef186.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && etype != null) {
               retval.tree = etype;
            }
         } catch (RecognitionException var24) {
            this.reportError(var24);
            this.errorHandler.recover(this, this.input, var24);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var24);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final or_test_return or_test(expr_contextType ctype) throws RecognitionException {
      or_test_return retval = new or_test_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token or = null;
      List list_right = null;
      and_test_return left = null;
      and_test_return right = null;
      right = null;
      PythonTree or_tree = null;
      RewriteRuleTokenStream stream_OR = new RewriteRuleTokenStream(this.adaptor, "token OR");
      RewriteRuleSubtreeStream stream_and_test = new RewriteRuleSubtreeStream(this.adaptor, "rule and_test");

      try {
         try {
            this.pushFollow(FOLLOW_and_test_in_or_test4371);
            left = this.and_test(ctype);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_and_test.add(left.getTree());
            }

            int alt83 = true;
            int LA83_0 = this.input.LA(1);
            byte alt83;
            if (LA83_0 == 33) {
               alt83 = 1;
            } else {
               if (LA83_0 != -1 && LA83_0 != 7 && LA83_0 != 13 && LA83_0 != 25 && LA83_0 != 27 && LA83_0 != 34 && (LA83_0 < 44 || LA83_0 > 47) && (LA83_0 < 50 || LA83_0 > 62) && LA83_0 != 82 && (LA83_0 < 84 || LA83_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 83, 0, this.input);
                  throw nvae;
               }

               alt83 = 2;
            }

            label313:
            switch (alt83) {
               case 1:
                  int cnt82 = 0;

                  while(true) {
                     int alt82 = 2;
                     int LA82_0 = this.input.LA(1);
                     if (LA82_0 == 33) {
                        alt82 = 1;
                     }

                     switch (alt82) {
                        case 1:
                           or = (Token)this.match(this.input, 33, FOLLOW_OR_in_or_test4387);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_OR.add(or);
                           }

                           this.pushFollow(FOLLOW_and_test_in_or_test4391);
                           right = this.and_test(ctype);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_and_test.add(right.getTree());
                           }

                           if (list_right == null) {
                              list_right = new ArrayList();
                           }

                           list_right.add(right.getTree());
                           ++cnt82;
                           break;
                        default:
                           if (cnt82 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(82, this.input);
                              throw eee;
                           }
                           break label313;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleSubtreeStream stream_left = new RewriteRuleSubtreeStream(this.adaptor, "rule left", left != null ? left.tree : null);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_left.nextTree());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && or != null) {
               Token tok = left != null ? left.start : null;
               if ((left != null ? left.leftTok : null) != null) {
                  tok = left != null ? left.leftTok : null;
               }

               retval.tree = this.actions.makeBoolOp(tok, left != null ? left.tree : null, boolopType.Or, list_right);
            }
         } catch (RecognitionException var20) {
            this.reportError(var20);
            this.errorHandler.recover(this, this.input, var20);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var20);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final and_test_return and_test(expr_contextType ctype) throws RecognitionException {
      and_test_return retval = new and_test_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token and = null;
      List list_right = null;
      not_test_return left = null;
      not_test_return right = null;
      right = null;
      PythonTree and_tree = null;
      RewriteRuleTokenStream stream_AND = new RewriteRuleTokenStream(this.adaptor, "token AND");
      RewriteRuleSubtreeStream stream_not_test = new RewriteRuleSubtreeStream(this.adaptor, "rule not_test");

      try {
         try {
            this.pushFollow(FOLLOW_not_test_in_and_test4472);
            left = this.not_test(ctype);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_not_test.add(left.getTree());
            }

            int alt85 = true;
            int LA85_0 = this.input.LA(1);
            byte alt85;
            if (LA85_0 == 12) {
               alt85 = 1;
            } else {
               if (LA85_0 != -1 && LA85_0 != 7 && LA85_0 != 13 && LA85_0 != 25 && LA85_0 != 27 && (LA85_0 < 33 || LA85_0 > 34) && (LA85_0 < 44 || LA85_0 > 47) && (LA85_0 < 50 || LA85_0 > 62) && LA85_0 != 82 && (LA85_0 < 84 || LA85_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 85, 0, this.input);
                  throw nvae;
               }

               alt85 = 2;
            }

            label316:
            switch (alt85) {
               case 1:
                  int cnt84 = 0;

                  while(true) {
                     int alt84 = 2;
                     int LA84_0 = this.input.LA(1);
                     if (LA84_0 == 12) {
                        alt84 = 1;
                     }

                     switch (alt84) {
                        case 1:
                           and = (Token)this.match(this.input, 12, FOLLOW_AND_in_and_test4488);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_AND.add(and);
                           }

                           this.pushFollow(FOLLOW_not_test_in_and_test4492);
                           right = this.not_test(ctype);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_not_test.add(right.getTree());
                           }

                           if (list_right == null) {
                              list_right = new ArrayList();
                           }

                           list_right.add(right.getTree());
                           ++cnt84;
                           break;
                        default:
                           if (cnt84 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(84, this.input);
                              throw eee;
                           }
                           break label316;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleSubtreeStream stream_left = new RewriteRuleSubtreeStream(this.adaptor, "rule left", left != null ? left.tree : null);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_left.nextTree());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && and != null) {
               Token tok = left != null ? left.start : null;
               if ((left != null ? left.leftTok : null) != null) {
                  tok = left != null ? left.leftTok : null;
               }

               retval.tree = this.actions.makeBoolOp(tok, left != null ? left.tree : null, boolopType.And, list_right);
            }
         } catch (RecognitionException var20) {
            this.reportError(var20);
            this.errorHandler.recover(this, this.input, var20);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var20);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final not_test_return not_test(expr_contextType ctype) throws RecognitionException {
      not_test_return retval = new not_test_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token NOT187 = null;
      not_test_return nt = null;
      comparison_return comparison188 = null;
      PythonTree NOT187_tree = null;
      expr etype = null;

      try {
         try {
            int alt86 = true;
            int LA86_0 = this.input.LA(1);
            byte alt86;
            if (LA86_0 == 32) {
               alt86 = 1;
            } else if (LA86_0 != 9 && LA86_0 != 43 && (LA86_0 < 75 || LA86_0 > 76) && (LA86_0 < 80 || LA86_0 > 81) && LA86_0 != 83 && LA86_0 != 85) {
               if (LA86_0 == 11 && this.printFunction) {
                  alt86 = 2;
               } else {
                  if (LA86_0 < 86 || LA86_0 > 90) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 86, 0, this.input);
                     throw nvae;
                  }

                  alt86 = 2;
               }
            } else {
               alt86 = 2;
            }

            switch (alt86) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  NOT187 = (Token)this.match(this.input, 32, FOLLOW_NOT_in_not_test4576);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     NOT187_tree = (PythonTree)this.adaptor.create(NOT187);
                     this.adaptor.addChild(root_0, NOT187_tree);
                  }

                  this.pushFollow(FOLLOW_not_test_in_not_test4580);
                  nt = this.not_test(ctype);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, nt.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     etype = new UnaryOp(NOT187, unaryopType.Not, this.actions.castExpr(nt != null ? nt.tree : null));
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_comparison_in_not_test4597);
                  comparison188 = this.comparison(ctype);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, comparison188.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.leftTok = comparison188 != null ? comparison188.leftTok : null;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && etype != null) {
               retval.tree = etype;
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.errorHandler.recover(this, this.input, var15);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final comparison_return comparison(expr_contextType ctype) throws RecognitionException {
      comparison_return retval = new comparison_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      List list_right = null;
      expr_return left = null;
      comp_op_return comp_op189 = null;
      expr_return right = null;
      right = null;
      RewriteRuleSubtreeStream stream_comp_op = new RewriteRuleSubtreeStream(this.adaptor, "rule comp_op");
      RewriteRuleSubtreeStream stream_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule expr");
      List cmps = new ArrayList();

      try {
         try {
            this.pushFollow(FOLLOW_expr_in_comparison4646);
            left = this.expr(ctype);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_expr.add(left.getTree());
            }

            int alt88 = true;
            int LA88_0 = this.input.LA(1);
            byte alt88;
            if ((LA88_0 < 29 || LA88_0 > 30) && LA88_0 != 32 && (LA88_0 < 64 || LA88_0 > 70)) {
               if (LA88_0 != -1 && LA88_0 != 7 && (LA88_0 < 12 || LA88_0 > 13) && LA88_0 != 25 && LA88_0 != 27 && (LA88_0 < 33 || LA88_0 > 34) && (LA88_0 < 44 || LA88_0 > 47) && (LA88_0 < 50 || LA88_0 > 62) && LA88_0 != 82 && (LA88_0 < 84 || LA88_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 88, 0, this.input);
                  throw nvae;
               }

               alt88 = 2;
            } else {
               alt88 = 1;
            }

            label349:
            switch (alt88) {
               case 1:
                  int cnt87 = 0;

                  while(true) {
                     int alt87 = 2;
                     int LA87_0 = this.input.LA(1);
                     if (LA87_0 >= 29 && LA87_0 <= 30 || LA87_0 == 32 || LA87_0 >= 64 && LA87_0 <= 70) {
                        alt87 = 1;
                     }

                     switch (alt87) {
                        case 1:
                           this.pushFollow(FOLLOW_comp_op_in_comparison4660);
                           comp_op189 = this.comp_op();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_comp_op.add(comp_op189.getTree());
                           }

                           this.pushFollow(FOLLOW_expr_in_comparison4664);
                           right = this.expr(ctype);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_expr.add(right.getTree());
                           }

                           if (list_right == null) {
                              list_right = new ArrayList();
                           }

                           list_right.add(right.getTree());
                           if (this.state.backtracking == 0) {
                              cmps.add(comp_op189 != null ? comp_op189.op : null);
                           }

                           ++cnt87;
                           break;
                        default:
                           if (cnt87 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(87, this.input);
                              throw eee;
                           }
                           break label349;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleSubtreeStream stream_left = new RewriteRuleSubtreeStream(this.adaptor, "rule left", left != null ? left.tree : null);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_left.nextTree());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.leftTok = left != null ? left.leftTok : null;
               if (!cmps.isEmpty()) {
                  retval.tree = new Compare(left != null ? left.start : null, this.actions.castExpr(left != null ? left.tree : null), this.actions.makeCmpOps(cmps), this.actions.castExprs(list_right));
               }
            }
         } catch (RecognitionException var20) {
            this.reportError(var20);
            this.errorHandler.recover(this, this.input, var20);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var20);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final comp_op_return comp_op() throws RecognitionException {
      comp_op_return retval = new comp_op_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token LESS190 = null;
      Token GREATER191 = null;
      Token EQUAL192 = null;
      Token GREATEREQUAL193 = null;
      Token LESSEQUAL194 = null;
      Token ALT_NOTEQUAL195 = null;
      Token NOTEQUAL196 = null;
      Token IN197 = null;
      Token NOT198 = null;
      Token IN199 = null;
      Token IS200 = null;
      Token IS201 = null;
      Token NOT202 = null;
      PythonTree LESS190_tree = null;
      PythonTree GREATER191_tree = null;
      PythonTree EQUAL192_tree = null;
      PythonTree GREATEREQUAL193_tree = null;
      PythonTree LESSEQUAL194_tree = null;
      PythonTree ALT_NOTEQUAL195_tree = null;
      PythonTree NOTEQUAL196_tree = null;
      PythonTree IN197_tree = null;
      PythonTree NOT198_tree = null;
      PythonTree IN199_tree = null;
      PythonTree IS200_tree = null;
      PythonTree IS201_tree = null;
      PythonTree NOT202_tree = null;

      try {
         try {
            int alt89 = true;
            int alt89 = this.dfa89.predict(this.input);
            switch (alt89) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  LESS190 = (Token)this.match(this.input, 64, FOLLOW_LESS_in_comp_op4745);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LESS190_tree = (PythonTree)this.adaptor.create(LESS190);
                     this.adaptor.addChild(root_0, LESS190_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.Lt;
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  GREATER191 = (Token)this.match(this.input, 65, FOLLOW_GREATER_in_comp_op4761);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     GREATER191_tree = (PythonTree)this.adaptor.create(GREATER191);
                     this.adaptor.addChild(root_0, GREATER191_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.Gt;
                  }
                  break;
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  EQUAL192 = (Token)this.match(this.input, 66, FOLLOW_EQUAL_in_comp_op4777);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     EQUAL192_tree = (PythonTree)this.adaptor.create(EQUAL192);
                     this.adaptor.addChild(root_0, EQUAL192_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.Eq;
                  }
                  break;
               case 4:
                  root_0 = (PythonTree)this.adaptor.nil();
                  GREATEREQUAL193 = (Token)this.match(this.input, 67, FOLLOW_GREATEREQUAL_in_comp_op4793);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     GREATEREQUAL193_tree = (PythonTree)this.adaptor.create(GREATEREQUAL193);
                     this.adaptor.addChild(root_0, GREATEREQUAL193_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.GtE;
                  }
                  break;
               case 5:
                  root_0 = (PythonTree)this.adaptor.nil();
                  LESSEQUAL194 = (Token)this.match(this.input, 68, FOLLOW_LESSEQUAL_in_comp_op4809);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LESSEQUAL194_tree = (PythonTree)this.adaptor.create(LESSEQUAL194);
                     this.adaptor.addChild(root_0, LESSEQUAL194_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.LtE;
                  }
                  break;
               case 6:
                  root_0 = (PythonTree)this.adaptor.nil();
                  ALT_NOTEQUAL195 = (Token)this.match(this.input, 69, FOLLOW_ALT_NOTEQUAL_in_comp_op4825);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ALT_NOTEQUAL195_tree = (PythonTree)this.adaptor.create(ALT_NOTEQUAL195);
                     this.adaptor.addChild(root_0, ALT_NOTEQUAL195_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.NotEq;
                  }
                  break;
               case 7:
                  root_0 = (PythonTree)this.adaptor.nil();
                  NOTEQUAL196 = (Token)this.match(this.input, 70, FOLLOW_NOTEQUAL_in_comp_op4841);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     NOTEQUAL196_tree = (PythonTree)this.adaptor.create(NOTEQUAL196);
                     this.adaptor.addChild(root_0, NOTEQUAL196_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.NotEq;
                  }
                  break;
               case 8:
                  root_0 = (PythonTree)this.adaptor.nil();
                  IN197 = (Token)this.match(this.input, 29, FOLLOW_IN_in_comp_op4857);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     IN197_tree = (PythonTree)this.adaptor.create(IN197);
                     this.adaptor.addChild(root_0, IN197_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.In;
                  }
                  break;
               case 9:
                  root_0 = (PythonTree)this.adaptor.nil();
                  NOT198 = (Token)this.match(this.input, 32, FOLLOW_NOT_in_comp_op4873);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     NOT198_tree = (PythonTree)this.adaptor.create(NOT198);
                     this.adaptor.addChild(root_0, NOT198_tree);
                  }

                  IN199 = (Token)this.match(this.input, 29, FOLLOW_IN_in_comp_op4875);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     IN199_tree = (PythonTree)this.adaptor.create(IN199);
                     this.adaptor.addChild(root_0, IN199_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.NotIn;
                  }
                  break;
               case 10:
                  root_0 = (PythonTree)this.adaptor.nil();
                  IS200 = (Token)this.match(this.input, 30, FOLLOW_IS_in_comp_op4891);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     IS200_tree = (PythonTree)this.adaptor.create(IS200);
                     this.adaptor.addChild(root_0, IS200_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.Is;
                  }
                  break;
               case 11:
                  root_0 = (PythonTree)this.adaptor.nil();
                  IS201 = (Token)this.match(this.input, 30, FOLLOW_IS_in_comp_op4907);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     IS201_tree = (PythonTree)this.adaptor.create(IS201);
                     this.adaptor.addChild(root_0, IS201_tree);
                  }

                  NOT202 = (Token)this.match(this.input, 32, FOLLOW_NOT_in_comp_op4909);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     NOT202_tree = (PythonTree)this.adaptor.create(NOT202);
                     this.adaptor.addChild(root_0, NOT202_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = cmpopType.IsNot;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var34) {
            this.reportError(var34);
            this.errorHandler.recover(this, this.input, var34);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var34);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final expr_return expr(expr_contextType ect) throws RecognitionException {
      this.expr_stack.push(new expr_scope());
      expr_return retval = new expr_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token op = null;
      List list_right = null;
      xor_expr_return left = null;
      xor_expr_return right = null;
      right = null;
      PythonTree op_tree = null;
      RewriteRuleTokenStream stream_VBAR = new RewriteRuleTokenStream(this.adaptor, "token VBAR");
      RewriteRuleSubtreeStream stream_xor_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule xor_expr");
      ((expr_scope)this.expr_stack.peek()).ctype = ect;

      try {
         this.pushFollow(FOLLOW_xor_expr_in_expr4961);
         left = this.xor_expr();
         --this.state._fsp;
         if (this.state.failed) {
            expr_return var24 = retval;
            return var24;
         }

         if (this.state.backtracking == 0) {
            stream_xor_expr.add(left.getTree());
         }

         int alt91 = true;
         int LA91_0 = this.input.LA(1);
         byte alt91;
         if (LA91_0 == 71) {
            alt91 = 1;
         } else {
            if (LA91_0 != -1 && LA91_0 != 7 && (LA91_0 < 12 || LA91_0 > 13) && LA91_0 != 25 && LA91_0 != 27 && (LA91_0 < 29 || LA91_0 > 30) && (LA91_0 < 32 || LA91_0 > 34) && (LA91_0 < 44 || LA91_0 > 47) && (LA91_0 < 50 || LA91_0 > 62) && (LA91_0 < 64 || LA91_0 > 70) && LA91_0 != 82 && (LA91_0 < 84 || LA91_0 > 85)) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  expr_return var25 = retval;
                  return var25;
               }

               NoViableAltException nvae = new NoViableAltException("", 91, 0, this.input);
               throw nvae;
            }

            alt91 = 2;
         }

         label422:
         switch (alt91) {
            case 1:
               int cnt90 = 0;

               while(true) {
                  int alt90 = 2;
                  int LA90_0 = this.input.LA(1);
                  if (LA90_0 == 71) {
                     alt90 = 1;
                  }

                  expr_return var16;
                  switch (alt90) {
                     case 1:
                        op = (Token)this.match(this.input, 71, FOLLOW_VBAR_in_expr4976);
                        if (this.state.failed) {
                           var16 = retval;
                           return var16;
                        }

                        if (this.state.backtracking == 0) {
                           stream_VBAR.add(op);
                        }

                        this.pushFollow(FOLLOW_xor_expr_in_expr4980);
                        right = this.xor_expr();
                        --this.state._fsp;
                        if (this.state.failed) {
                           var16 = retval;
                           return var16;
                        }

                        if (this.state.backtracking == 0) {
                           stream_xor_expr.add(right.getTree());
                        }

                        if (list_right == null) {
                           list_right = new ArrayList();
                        }

                        list_right.add(right.getTree());
                        ++cnt90;
                        break;
                     default:
                        if (cnt90 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              var16 = retval;
                              return var16;
                           }

                           EarlyExitException eee = new EarlyExitException(90, this.input);
                           throw eee;
                        }
                        break label422;
                  }
               }
            case 2:
               if (this.state.backtracking == 0) {
                  retval.tree = root_0;
                  RewriteRuleSubtreeStream stream_left = new RewriteRuleSubtreeStream(this.adaptor, "rule left", left != null ? left.tree : null);
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.adaptor.addChild(root_0, stream_left.nextTree());
                  retval.tree = root_0;
               }
         }

         retval.stop = this.input.LT(-1);
         if (this.state.backtracking == 0) {
            retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         }

         if (this.state.backtracking == 0) {
            retval.leftTok = left != null ? left.lparen : null;
            if (op != null) {
               Token tok = left != null ? left.start : null;
               if ((left != null ? left.lparen : null) != null) {
                  tok = left != null ? left.lparen : null;
               }

               retval.tree = this.actions.makeBinOp(tok, left != null ? left.tree : null, operatorType.BitOr, list_right);
            }
         }
      } catch (RecognitionException var20) {
         this.reportError(var20);
         this.errorHandler.recover(this, this.input, var20);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var20);
      } finally {
         this.expr_stack.pop();
      }

      return retval;
   }

   public final xor_expr_return xor_expr() throws RecognitionException {
      xor_expr_return retval = new xor_expr_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token op = null;
      List list_right = null;
      and_expr_return left = null;
      and_expr_return right = null;
      right = null;
      PythonTree op_tree = null;
      RewriteRuleTokenStream stream_CIRCUMFLEX = new RewriteRuleTokenStream(this.adaptor, "token CIRCUMFLEX");
      RewriteRuleSubtreeStream stream_and_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule and_expr");

      try {
         try {
            this.pushFollow(FOLLOW_and_expr_in_xor_expr5059);
            left = this.and_expr();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_and_expr.add(left.getTree());
            }

            int alt93 = true;
            int LA93_0 = this.input.LA(1);
            byte alt93;
            if (LA93_0 == 72) {
               alt93 = 1;
            } else {
               if (LA93_0 != -1 && LA93_0 != 7 && (LA93_0 < 12 || LA93_0 > 13) && LA93_0 != 25 && LA93_0 != 27 && (LA93_0 < 29 || LA93_0 > 30) && (LA93_0 < 32 || LA93_0 > 34) && (LA93_0 < 44 || LA93_0 > 47) && (LA93_0 < 50 || LA93_0 > 62) && (LA93_0 < 64 || LA93_0 > 71) && LA93_0 != 82 && (LA93_0 < 84 || LA93_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 93, 0, this.input);
                  throw nvae;
               }

               alt93 = 2;
            }

            label349:
            switch (alt93) {
               case 1:
                  int cnt92 = 0;

                  while(true) {
                     int alt92 = 2;
                     int LA92_0 = this.input.LA(1);
                     if (LA92_0 == 72) {
                        alt92 = 1;
                     }

                     switch (alt92) {
                        case 1:
                           op = (Token)this.match(this.input, 72, FOLLOW_CIRCUMFLEX_in_xor_expr5074);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_CIRCUMFLEX.add(op);
                           }

                           this.pushFollow(FOLLOW_and_expr_in_xor_expr5078);
                           right = this.and_expr();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_and_expr.add(right.getTree());
                           }

                           if (list_right == null) {
                              list_right = new ArrayList();
                           }

                           list_right.add(right.getTree());
                           ++cnt92;
                           break;
                        default:
                           if (cnt92 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(92, this.input);
                              throw eee;
                           }
                           break label349;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleSubtreeStream stream_left = new RewriteRuleSubtreeStream(this.adaptor, "rule left", left != null ? left.tree : null);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_left.nextTree());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               if (op != null) {
                  Token tok = left != null ? left.start : null;
                  if ((left != null ? left.lparen : null) != null) {
                     tok = left != null ? left.lparen : null;
                  }

                  retval.tree = this.actions.makeBinOp(tok, left != null ? left.tree : null, operatorType.BitXor, list_right);
               }

               retval.lparen = left != null ? left.lparen : null;
            }
         } catch (RecognitionException var19) {
            this.reportError(var19);
            this.errorHandler.recover(this, this.input, var19);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var19);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final and_expr_return and_expr() throws RecognitionException {
      and_expr_return retval = new and_expr_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token op = null;
      List list_right = null;
      shift_expr_return left = null;
      shift_expr_return right = null;
      right = null;
      PythonTree op_tree = null;
      RewriteRuleTokenStream stream_AMPER = new RewriteRuleTokenStream(this.adaptor, "token AMPER");
      RewriteRuleSubtreeStream stream_shift_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule shift_expr");

      try {
         try {
            this.pushFollow(FOLLOW_shift_expr_in_and_expr5156);
            left = this.shift_expr();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_shift_expr.add(left.getTree());
            }

            int alt95 = true;
            int LA95_0 = this.input.LA(1);
            byte alt95;
            if (LA95_0 == 73) {
               alt95 = 1;
            } else {
               if (LA95_0 != -1 && LA95_0 != 7 && (LA95_0 < 12 || LA95_0 > 13) && LA95_0 != 25 && LA95_0 != 27 && (LA95_0 < 29 || LA95_0 > 30) && (LA95_0 < 32 || LA95_0 > 34) && (LA95_0 < 44 || LA95_0 > 47) && (LA95_0 < 50 || LA95_0 > 62) && (LA95_0 < 64 || LA95_0 > 72) && LA95_0 != 82 && (LA95_0 < 84 || LA95_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 95, 0, this.input);
                  throw nvae;
               }

               alt95 = 2;
            }

            label349:
            switch (alt95) {
               case 1:
                  int cnt94 = 0;

                  while(true) {
                     int alt94 = 2;
                     int LA94_0 = this.input.LA(1);
                     if (LA94_0 == 73) {
                        alt94 = 1;
                     }

                     switch (alt94) {
                        case 1:
                           op = (Token)this.match(this.input, 73, FOLLOW_AMPER_in_and_expr5171);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_AMPER.add(op);
                           }

                           this.pushFollow(FOLLOW_shift_expr_in_and_expr5175);
                           right = this.shift_expr();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_shift_expr.add(right.getTree());
                           }

                           if (list_right == null) {
                              list_right = new ArrayList();
                           }

                           list_right.add(right.getTree());
                           ++cnt94;
                           break;
                        default:
                           if (cnt94 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(94, this.input);
                              throw eee;
                           }
                           break label349;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleSubtreeStream stream_left = new RewriteRuleSubtreeStream(this.adaptor, "rule left", left != null ? left.tree : null);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_left.nextTree());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               if (op != null) {
                  Token tok = left != null ? left.start : null;
                  if ((left != null ? left.lparen : null) != null) {
                     tok = left != null ? left.lparen : null;
                  }

                  retval.tree = this.actions.makeBinOp(tok, left != null ? left.tree : null, operatorType.BitAnd, list_right);
               }

               retval.lparen = left != null ? left.lparen : null;
            }
         } catch (RecognitionException var19) {
            this.reportError(var19);
            this.errorHandler.recover(this, this.input, var19);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var19);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final shift_expr_return shift_expr() throws RecognitionException {
      shift_expr_return retval = new shift_expr_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      List list_right = null;
      arith_expr_return left = null;
      shift_op_return shift_op203 = null;
      arith_expr_return right = null;
      right = null;
      RewriteRuleSubtreeStream stream_shift_op = new RewriteRuleSubtreeStream(this.adaptor, "rule shift_op");
      RewriteRuleSubtreeStream stream_arith_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule arith_expr");
      List ops = new ArrayList();
      List toks = new ArrayList();

      try {
         try {
            this.pushFollow(FOLLOW_arith_expr_in_shift_expr5258);
            left = this.arith_expr();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_arith_expr.add(left.getTree());
            }

            int alt97 = true;
            int LA97_0 = this.input.LA(1);
            byte alt97;
            if (LA97_0 != 63 && LA97_0 != 74) {
               if (LA97_0 != -1 && LA97_0 != 7 && (LA97_0 < 12 || LA97_0 > 13) && LA97_0 != 25 && LA97_0 != 27 && (LA97_0 < 29 || LA97_0 > 30) && (LA97_0 < 32 || LA97_0 > 34) && (LA97_0 < 44 || LA97_0 > 47) && (LA97_0 < 50 || LA97_0 > 62) && (LA97_0 < 64 || LA97_0 > 73) && LA97_0 != 82 && (LA97_0 < 84 || LA97_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 97, 0, this.input);
                  throw nvae;
               }

               alt97 = 2;
            } else {
               alt97 = 1;
            }

            label376:
            switch (alt97) {
               case 1:
                  int cnt96 = 0;

                  while(true) {
                     int alt96 = 2;
                     int LA96_0 = this.input.LA(1);
                     if (LA96_0 == 63 || LA96_0 == 74) {
                        alt96 = 1;
                     }

                     switch (alt96) {
                        case 1:
                           this.pushFollow(FOLLOW_shift_op_in_shift_expr5272);
                           shift_op203 = this.shift_op();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_shift_op.add(shift_op203.getTree());
                           }

                           this.pushFollow(FOLLOW_arith_expr_in_shift_expr5276);
                           right = this.arith_expr();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_arith_expr.add(right.getTree());
                           }

                           if (list_right == null) {
                              list_right = new ArrayList();
                           }

                           list_right.add(right.getTree());
                           if (this.state.backtracking == 0) {
                              ops.add(shift_op203 != null ? shift_op203.op : null);
                              toks.add(shift_op203 != null ? shift_op203.start : null);
                           }

                           ++cnt96;
                           break;
                        default:
                           if (cnt96 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(96, this.input);
                              throw eee;
                           }
                           break label376;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleSubtreeStream stream_left = new RewriteRuleSubtreeStream(this.adaptor, "rule left", left != null ? left.tree : null);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_left.nextTree());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               if (!ops.isEmpty()) {
                  Token tok = left != null ? left.start : null;
                  if ((left != null ? left.lparen : null) != null) {
                     tok = left != null ? left.lparen : null;
                  }

                  retval.tree = this.actions.makeBinOp(tok, left != null ? left.tree : null, ops, list_right, toks);
               }

               retval.lparen = left != null ? left.lparen : null;
            }
         } catch (RecognitionException var20) {
            this.reportError(var20);
            this.errorHandler.recover(this, this.input, var20);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var20);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final shift_op_return shift_op() throws RecognitionException {
      shift_op_return retval = new shift_op_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token LEFTSHIFT204 = null;
      Token RIGHTSHIFT205 = null;
      PythonTree LEFTSHIFT204_tree = null;
      PythonTree RIGHTSHIFT205_tree = null;

      try {
         try {
            int alt98 = true;
            int LA98_0 = this.input.LA(1);
            byte alt98;
            if (LA98_0 == 74) {
               alt98 = 1;
            } else {
               if (LA98_0 != 63) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 98, 0, this.input);
                  throw nvae;
               }

               alt98 = 2;
            }

            switch (alt98) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  LEFTSHIFT204 = (Token)this.match(this.input, 74, FOLLOW_LEFTSHIFT_in_shift_op5360);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LEFTSHIFT204_tree = (PythonTree)this.adaptor.create(LEFTSHIFT204);
                     this.adaptor.addChild(root_0, LEFTSHIFT204_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.LShift;
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  RIGHTSHIFT205 = (Token)this.match(this.input, 63, FOLLOW_RIGHTSHIFT_in_shift_op5376);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RIGHTSHIFT205_tree = (PythonTree)this.adaptor.create(RIGHTSHIFT205);
                     this.adaptor.addChild(root_0, RIGHTSHIFT205_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.RShift;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.errorHandler.recover(this, this.input, var13);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final arith_expr_return arith_expr() throws RecognitionException {
      arith_expr_return retval = new arith_expr_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      List list_right = null;
      term_return left = null;
      arith_op_return arith_op206 = null;
      term_return right = null;
      right = null;
      RewriteRuleSubtreeStream stream_term = new RewriteRuleSubtreeStream(this.adaptor, "rule term");
      RewriteRuleSubtreeStream stream_arith_op = new RewriteRuleSubtreeStream(this.adaptor, "rule arith_op");
      List ops = new ArrayList();
      List toks = new ArrayList();

      try {
         try {
            this.pushFollow(FOLLOW_term_in_arith_expr5422);
            left = this.term();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_term.add(left.getTree());
            }

            int alt100 = true;
            int LA100_0 = this.input.LA(1);
            byte alt100;
            if (LA100_0 >= 75 && LA100_0 <= 76) {
               alt100 = 1;
            } else {
               if (LA100_0 != -1 && LA100_0 != 7 && (LA100_0 < 12 || LA100_0 > 13) && LA100_0 != 25 && LA100_0 != 27 && (LA100_0 < 29 || LA100_0 > 30) && (LA100_0 < 32 || LA100_0 > 34) && (LA100_0 < 44 || LA100_0 > 47) && (LA100_0 < 50 || LA100_0 > 74) && LA100_0 != 82 && (LA100_0 < 84 || LA100_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 100, 0, this.input);
                  throw nvae;
               }

               alt100 = 2;
            }

            label379:
            switch (alt100) {
               case 1:
                  int cnt99 = 0;

                  while(true) {
                     int alt99 = 2;
                     int LA99_0 = this.input.LA(1);
                     if (LA99_0 >= 75 && LA99_0 <= 76) {
                        alt99 = 1;
                     }

                     switch (alt99) {
                        case 1:
                           this.pushFollow(FOLLOW_arith_op_in_arith_expr5435);
                           arith_op206 = this.arith_op();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_arith_op.add(arith_op206.getTree());
                           }

                           this.pushFollow(FOLLOW_term_in_arith_expr5439);
                           right = this.term();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_term.add(right.getTree());
                           }

                           if (list_right == null) {
                              list_right = new ArrayList();
                           }

                           list_right.add(right.getTree());
                           if (this.state.backtracking == 0) {
                              ops.add(arith_op206 != null ? arith_op206.op : null);
                              toks.add(arith_op206 != null ? arith_op206.start : null);
                           }

                           ++cnt99;
                           break;
                        default:
                           if (cnt99 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(99, this.input);
                              throw eee;
                           }
                           break label379;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleSubtreeStream stream_left = new RewriteRuleSubtreeStream(this.adaptor, "rule left", left != null ? left.tree : null);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_left.nextTree());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               if (!ops.isEmpty()) {
                  Token tok = left != null ? left.start : null;
                  if ((left != null ? left.lparen : null) != null) {
                     tok = left != null ? left.lparen : null;
                  }

                  retval.tree = this.actions.makeBinOp(tok, left != null ? left.tree : null, ops, list_right, toks);
               }

               retval.lparen = left != null ? left.lparen : null;
            }
         } catch (RewriteCardinalityException var20) {
            PythonTree badNode = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), (RecognitionException)null);
            retval.tree = badNode;
            this.errorHandler.error("Internal Parser Error", badNode);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final arith_op_return arith_op() throws RecognitionException {
      arith_op_return retval = new arith_op_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token PLUS207 = null;
      Token MINUS208 = null;
      PythonTree PLUS207_tree = null;
      PythonTree MINUS208_tree = null;

      try {
         try {
            int alt101 = true;
            int LA101_0 = this.input.LA(1);
            byte alt101;
            if (LA101_0 == 75) {
               alt101 = 1;
            } else {
               if (LA101_0 != 76) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 101, 0, this.input);
                  throw nvae;
               }

               alt101 = 2;
            }

            switch (alt101) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  PLUS207 = (Token)this.match(this.input, 75, FOLLOW_PLUS_in_arith_op5547);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     PLUS207_tree = (PythonTree)this.adaptor.create(PLUS207);
                     this.adaptor.addChild(root_0, PLUS207_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Add;
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  MINUS208 = (Token)this.match(this.input, 76, FOLLOW_MINUS_in_arith_op5563);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     MINUS208_tree = (PythonTree)this.adaptor.create(MINUS208);
                     this.adaptor.addChild(root_0, MINUS208_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Sub;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.errorHandler.recover(this, this.input, var13);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final term_return term() throws RecognitionException {
      term_return retval = new term_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      List list_right = null;
      factor_return left = null;
      term_op_return term_op209 = null;
      factor_return right = null;
      right = null;
      RewriteRuleSubtreeStream stream_term_op = new RewriteRuleSubtreeStream(this.adaptor, "rule term_op");
      RewriteRuleSubtreeStream stream_factor = new RewriteRuleSubtreeStream(this.adaptor, "rule factor");
      List ops = new ArrayList();
      List toks = new ArrayList();

      try {
         try {
            this.pushFollow(FOLLOW_factor_in_term5609);
            left = this.factor();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_factor.add(left.getTree());
            }

            int alt103 = true;
            int LA103_0 = this.input.LA(1);
            byte alt103;
            if (LA103_0 == 48 || LA103_0 >= 77 && LA103_0 <= 79) {
               alt103 = 1;
            } else {
               if (LA103_0 != -1 && LA103_0 != 7 && (LA103_0 < 12 || LA103_0 > 13) && LA103_0 != 25 && LA103_0 != 27 && (LA103_0 < 29 || LA103_0 > 30) && (LA103_0 < 32 || LA103_0 > 34) && (LA103_0 < 44 || LA103_0 > 47) && (LA103_0 < 50 || LA103_0 > 76) && LA103_0 != 82 && (LA103_0 < 84 || LA103_0 > 85)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 103, 0, this.input);
                  throw nvae;
               }

               alt103 = 2;
            }

            label377:
            switch (alt103) {
               case 1:
                  int cnt102 = 0;

                  while(true) {
                     int alt102 = 2;
                     int LA102_0 = this.input.LA(1);
                     if (LA102_0 == 48 || LA102_0 >= 77 && LA102_0 <= 79) {
                        alt102 = 1;
                     }

                     switch (alt102) {
                        case 1:
                           this.pushFollow(FOLLOW_term_op_in_term5622);
                           term_op209 = this.term_op();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_term_op.add(term_op209.getTree());
                           }

                           this.pushFollow(FOLLOW_factor_in_term5626);
                           right = this.factor();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_factor.add(right.getTree());
                           }

                           if (list_right == null) {
                              list_right = new ArrayList();
                           }

                           list_right.add(right.getTree());
                           if (this.state.backtracking == 0) {
                              ops.add(term_op209 != null ? term_op209.op : null);
                              toks.add(term_op209 != null ? term_op209.start : null);
                           }

                           ++cnt102;
                           break;
                        default:
                           if (cnt102 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(102, this.input);
                              throw eee;
                           }
                           break label377;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleSubtreeStream stream_left = new RewriteRuleSubtreeStream(this.adaptor, "rule left", left != null ? left.tree : null);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_left.nextTree());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.lparen = left != null ? left.lparen : null;
               if (!ops.isEmpty()) {
                  Token tok = left != null ? left.start : null;
                  if ((left != null ? left.lparen : null) != null) {
                     tok = left != null ? left.lparen : null;
                  }

                  retval.tree = this.actions.makeBinOp(tok, left != null ? left.tree : null, ops, list_right, toks);
               }
            }
         } catch (RecognitionException var20) {
            this.reportError(var20);
            this.errorHandler.recover(this, this.input, var20);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var20);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final term_op_return term_op() throws RecognitionException {
      term_op_return retval = new term_op_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token STAR210 = null;
      Token SLASH211 = null;
      Token PERCENT212 = null;
      Token DOUBLESLASH213 = null;
      PythonTree STAR210_tree = null;
      PythonTree SLASH211_tree = null;
      PythonTree PERCENT212_tree = null;
      PythonTree DOUBLESLASH213_tree = null;

      try {
         try {
            int alt104 = true;
            byte alt104;
            switch (this.input.LA(1)) {
               case 48:
                  alt104 = 1;
                  break;
               case 77:
                  alt104 = 2;
                  break;
               case 78:
                  alt104 = 3;
                  break;
               case 79:
                  alt104 = 4;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 104, 0, this.input);
                  throw nvae;
            }

            switch (alt104) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  STAR210 = (Token)this.match(this.input, 48, FOLLOW_STAR_in_term_op5708);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     STAR210_tree = (PythonTree)this.adaptor.create(STAR210);
                     this.adaptor.addChild(root_0, STAR210_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Mult;
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  SLASH211 = (Token)this.match(this.input, 77, FOLLOW_SLASH_in_term_op5724);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     SLASH211_tree = (PythonTree)this.adaptor.create(SLASH211);
                     this.adaptor.addChild(root_0, SLASH211_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Div;
                  }
                  break;
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  PERCENT212 = (Token)this.match(this.input, 78, FOLLOW_PERCENT_in_term_op5740);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     PERCENT212_tree = (PythonTree)this.adaptor.create(PERCENT212);
                     this.adaptor.addChild(root_0, PERCENT212_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.Mod;
                  }
                  break;
               case 4:
                  root_0 = (PythonTree)this.adaptor.nil();
                  DOUBLESLASH213 = (Token)this.match(this.input, 79, FOLLOW_DOUBLESLASH_in_term_op5756);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     DOUBLESLASH213_tree = (PythonTree)this.adaptor.create(DOUBLESLASH213);
                     this.adaptor.addChild(root_0, DOUBLESLASH213_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.op = operatorType.FloorDiv;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.errorHandler.recover(this, this.input, var16);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final factor_return factor() throws RecognitionException {
      factor_return retval = new factor_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token PLUS214 = null;
      Token MINUS215 = null;
      Token TILDE216 = null;
      factor_return p = null;
      factor_return m = null;
      factor_return t = null;
      power_return power217 = null;
      PythonTree PLUS214_tree = null;
      PythonTree MINUS215_tree = null;
      PythonTree TILDE216_tree = null;

      try {
         try {
            int alt105 = true;
            int LA105_0 = this.input.LA(1);
            byte alt105;
            if (LA105_0 == 75) {
               alt105 = 1;
            } else if (LA105_0 == 76) {
               alt105 = 2;
            } else if (LA105_0 == 80) {
               alt105 = 3;
            } else if (LA105_0 != 9 && LA105_0 != 43 && LA105_0 != 81 && LA105_0 != 83 && LA105_0 != 85) {
               if (LA105_0 == 11 && this.printFunction) {
                  alt105 = 4;
               } else {
                  if (LA105_0 < 86 || LA105_0 > 90) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 105, 0, this.input);
                     throw nvae;
                  }

                  alt105 = 4;
               }
            } else {
               alt105 = 4;
            }

            switch (alt105) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  PLUS214 = (Token)this.match(this.input, 75, FOLLOW_PLUS_in_factor5795);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     PLUS214_tree = (PythonTree)this.adaptor.create(PLUS214);
                     this.adaptor.addChild(root_0, PLUS214_tree);
                  }

                  this.pushFollow(FOLLOW_factor_in_factor5799);
                  p = this.factor();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, p.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.etype = new UnaryOp(PLUS214, unaryopType.UAdd, p != null ? p.etype : null);
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  MINUS215 = (Token)this.match(this.input, 76, FOLLOW_MINUS_in_factor5815);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     MINUS215_tree = (PythonTree)this.adaptor.create(MINUS215);
                     this.adaptor.addChild(root_0, MINUS215_tree);
                  }

                  this.pushFollow(FOLLOW_factor_in_factor5819);
                  m = this.factor();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, m.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.etype = this.actions.negate(MINUS215, m != null ? m.etype : null);
                  }
                  break;
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  TILDE216 = (Token)this.match(this.input, 80, FOLLOW_TILDE_in_factor5835);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     TILDE216_tree = (PythonTree)this.adaptor.create(TILDE216);
                     this.adaptor.addChild(root_0, TILDE216_tree);
                  }

                  this.pushFollow(FOLLOW_factor_in_factor5839);
                  t = this.factor();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.etype = new UnaryOp(TILDE216, unaryopType.Invert, t != null ? t.etype : null);
                  }
                  break;
               case 4:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_power_in_factor5855);
                  power217 = this.power();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, power217.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.etype = this.actions.castExpr(power217 != null ? power217.tree : null);
                     retval.lparen = power217 != null ? power217.lparen : null;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = retval.etype;
            }
         } catch (RecognitionException var19) {
            this.reportError(var19);
            this.errorHandler.recover(this, this.input, var19);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var19);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final power_return power() throws RecognitionException {
      power_return retval = new power_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token d = null;
      List list_t = null;
      atom_return atom218 = null;
      factor_return factor219 = null;
      trailer_return t = null;
      t = null;
      PythonTree d_tree = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_atom_in_power5894);
         atom218 = this.atom();
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, atom218.getTree());
            }

            while(true) {
               int alt107 = 2;
               int LA107_0 = this.input.LA(1);
               if (LA107_0 == 10 || LA107_0 == 43 || LA107_0 == 81) {
                  alt107 = 1;
               }

               switch (alt107) {
                  case 1:
                     this.pushFollow(FOLLOW_trailer_in_power5899);
                     t = this.trailer(atom218 != null ? atom218.start : null, atom218 != null ? atom218.tree : null);
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, t.getTree());
                     }

                     if (list_t == null) {
                        list_t = new ArrayList();
                     }

                     list_t.add(t.getTree());
                     break;
                  default:
                     alt107 = 2;
                     LA107_0 = this.input.LA(1);
                     if (LA107_0 == 49) {
                        alt107 = 1;
                     }

                     switch (alt107) {
                        case 1:
                           d = (Token)this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_power5914);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              d_tree = (PythonTree)this.adaptor.create(d);
                              this.adaptor.addChild(root_0, d_tree);
                           }

                           this.pushFollow(FOLLOW_factor_in_power5916);
                           factor219 = this.factor();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, factor219.getTree());
                           }
                     }

                     if (this.state.backtracking == 0) {
                        retval.lparen = atom218 != null ? atom218.lparen : null;
                        retval.etype = this.actions.castExpr(atom218 != null ? atom218.tree : null);
                        if (list_t != null) {
                           Iterator var11 = list_t.iterator();

                           while(var11.hasNext()) {
                              Object o = var11.next();
                              this.actions.recurseSetContext(retval.etype, expr_contextType.Load);
                              if (o instanceof Call) {
                                 Call c = (Call)o;
                                 c.setFunc(retval.etype);
                                 retval.etype = c;
                              } else if (o instanceof Subscript) {
                                 Subscript c = (Subscript)o;
                                 c.setValue(retval.etype);
                                 retval.etype = c;
                              } else if (o instanceof Attribute) {
                                 Attribute c = (Attribute)o;
                                 c.setCharStartIndex(retval.etype.getCharStartIndex());
                                 c.setValue(retval.etype);
                                 retval.etype = c;
                              }
                           }
                        }

                        if (d != null) {
                           List right = new ArrayList();
                           right.add(factor219 != null ? factor219.tree : null);
                           retval.etype = this.actions.makeBinOp(atom218 != null ? atom218.start : null, retval.etype, operatorType.Pow, right);
                        }
                     }

                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     if (this.state.backtracking == 0) {
                        retval.tree = retval.etype;
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var17) {
         this.reportError(var17);
         this.errorHandler.recover(this, this.input, var17);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         return retval;
      } finally {
         ;
      }
   }

   public final atom_return atom() throws RecognitionException {
      atom_return retval = new atom_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token lb = null;
      Token rb = null;
      Token LPAREN220 = null;
      Token RPAREN223 = null;
      Token LBRACK224 = null;
      Token RBRACK226 = null;
      Token LCURLY227 = null;
      Token RCURLY229 = null;
      Token INT232 = null;
      Token LONGINT233 = null;
      Token FLOAT234 = null;
      Token COMPLEX235 = null;
      Token S = null;
      List list_S = null;
      yield_expr_return yield_expr221 = null;
      testlist_gexp_return testlist_gexp222 = null;
      listmaker_return listmaker225 = null;
      dictorsetmaker_return dictorsetmaker228 = null;
      testlist_return testlist230 = null;
      name_or_print_return name_or_print231 = null;
      PythonTree lb_tree = null;
      PythonTree rb_tree = null;
      PythonTree LPAREN220_tree = null;
      PythonTree RPAREN223_tree = null;
      PythonTree LBRACK224_tree = null;
      PythonTree RBRACK226_tree = null;
      PythonTree LCURLY227_tree = null;
      PythonTree RCURLY229_tree = null;
      PythonTree INT232_tree = null;
      PythonTree LONGINT233_tree = null;
      PythonTree FLOAT234_tree = null;
      PythonTree COMPLEX235_tree = null;
      PythonTree S_tree = null;
      RewriteRuleTokenStream stream_RBRACK = new RewriteRuleTokenStream(this.adaptor, "token RBRACK");
      RewriteRuleTokenStream stream_LBRACK = new RewriteRuleTokenStream(this.adaptor, "token LBRACK");
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleTokenStream stream_LCURLY = new RewriteRuleTokenStream(this.adaptor, "token LCURLY");
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleTokenStream stream_RCURLY = new RewriteRuleTokenStream(this.adaptor, "token RCURLY");
      RewriteRuleSubtreeStream stream_listmaker = new RewriteRuleSubtreeStream(this.adaptor, "rule listmaker");
      RewriteRuleSubtreeStream stream_yield_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule yield_expr");
      RewriteRuleSubtreeStream stream_testlist_gexp = new RewriteRuleSubtreeStream(this.adaptor, "rule testlist_gexp");
      RewriteRuleSubtreeStream stream_dictorsetmaker = new RewriteRuleSubtreeStream(this.adaptor, "rule dictorsetmaker");
      expr etype = null;

      try {
         try {
            int alt112 = true;
            int alt112 = this.dfa112.predict(this.input);
            int LA108_0;
            boolean alt108;
            byte alt108;
            NoViableAltException nvae;
            switch (alt112) {
               case 1:
                  LPAREN220 = (Token)this.match(this.input, 43, FOLLOW_LPAREN_in_atom5966);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_LPAREN.add(LPAREN220);
                  }

                  if (this.state.backtracking == 0) {
                     retval.lparen = LPAREN220;
                  }

                  alt108 = true;
                  LA108_0 = this.input.LA(1);
                  if (LA108_0 == 41) {
                     alt108 = 1;
                  } else if (LA108_0 != 9 && LA108_0 != 32 && LA108_0 != 43 && (LA108_0 < 75 || LA108_0 > 76) && (LA108_0 < 80 || LA108_0 > 81) && LA108_0 != 83 && LA108_0 != 85) {
                     if (LA108_0 == 11 && this.printFunction) {
                        alt108 = 2;
                     } else if (LA108_0 == 31 || LA108_0 >= 86 && LA108_0 <= 90) {
                        alt108 = 2;
                     } else {
                        if (LA108_0 != 44) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvae = new NoViableAltException("", 108, 0, this.input);
                           throw nvae;
                        }

                        alt108 = 3;
                     }
                  } else {
                     alt108 = 2;
                  }

                  switch (alt108) {
                     case 1:
                        this.pushFollow(FOLLOW_yield_expr_in_atom5984);
                        yield_expr221 = this.yield_expr();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_yield_expr.add(yield_expr221.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           etype = yield_expr221 != null ? yield_expr221.etype : null;
                        }
                        break;
                     case 2:
                        this.pushFollow(FOLLOW_testlist_gexp_in_atom6004);
                        testlist_gexp222 = this.testlist_gexp();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_testlist_gexp.add(testlist_gexp222.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                           root_0 = (PythonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_testlist_gexp.nextTree());
                           retval.tree = root_0;
                        }
                        break;
                     case 3:
                        if (this.state.backtracking == 0) {
                           etype = new Tuple(LPAREN220, new ArrayList(), ((expr_scope)this.expr_stack.peek()).ctype);
                        }
                  }

                  RPAREN223 = (Token)this.match(this.input, 44, FOLLOW_RPAREN_in_atom6047);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_RPAREN.add(RPAREN223);
                  }
                  break;
               case 2:
                  LBRACK224 = (Token)this.match(this.input, 81, FOLLOW_LBRACK_in_atom6055);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_LBRACK.add(LBRACK224);
                  }

                  alt108 = true;
                  LA108_0 = this.input.LA(1);
                  if (LA108_0 != 9 && LA108_0 != 32 && LA108_0 != 43 && (LA108_0 < 75 || LA108_0 > 76) && (LA108_0 < 80 || LA108_0 > 81) && LA108_0 != 83 && LA108_0 != 85) {
                     if (LA108_0 == 11 && this.printFunction) {
                        alt108 = 1;
                     } else if (LA108_0 == 31 || LA108_0 >= 86 && LA108_0 <= 90) {
                        alt108 = 1;
                     } else {
                        if (LA108_0 != 82) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvae = new NoViableAltException("", 109, 0, this.input);
                           throw nvae;
                        }

                        alt108 = 2;
                     }
                  } else {
                     alt108 = 1;
                  }

                  switch (alt108) {
                     case 1:
                        this.pushFollow(FOLLOW_listmaker_in_atom6064);
                        listmaker225 = this.listmaker(LBRACK224);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_listmaker.add(listmaker225.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                           root_0 = (PythonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_listmaker.nextTree());
                           retval.tree = root_0;
                        }
                        break;
                     case 2:
                        if (this.state.backtracking == 0) {
                           etype = new org.python.antlr.ast.List(LBRACK224, new ArrayList(), ((expr_scope)this.expr_stack.peek()).ctype);
                        }
                  }

                  RBRACK226 = (Token)this.match(this.input, 82, FOLLOW_RBRACK_in_atom6107);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_RBRACK.add(RBRACK226);
                  }
                  break;
               case 3:
                  LCURLY227 = (Token)this.match(this.input, 83, FOLLOW_LCURLY_in_atom6115);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_LCURLY.add(LCURLY227);
                  }

                  alt108 = true;
                  LA108_0 = this.input.LA(1);
                  if (LA108_0 != 9 && LA108_0 != 32 && LA108_0 != 43 && (LA108_0 < 75 || LA108_0 > 76) && (LA108_0 < 80 || LA108_0 > 81) && LA108_0 != 83 && LA108_0 != 85) {
                     if (LA108_0 == 11 && this.printFunction) {
                        alt108 = 1;
                     } else if (LA108_0 != 31 && (LA108_0 < 86 || LA108_0 > 90)) {
                        if (LA108_0 != 84) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvae = new NoViableAltException("", 110, 0, this.input);
                           throw nvae;
                        }

                        alt108 = 2;
                     } else {
                        alt108 = 1;
                     }
                  } else {
                     alt108 = 1;
                  }

                  switch (alt108) {
                     case 1:
                        this.pushFollow(FOLLOW_dictorsetmaker_in_atom6125);
                        dictorsetmaker228 = this.dictorsetmaker(LCURLY227);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_dictorsetmaker.add(dictorsetmaker228.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                           root_0 = (PythonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_dictorsetmaker.nextTree());
                           retval.tree = root_0;
                        }
                        break;
                     case 2:
                        if (this.state.backtracking == 0) {
                           etype = new Dict(LCURLY227, new ArrayList(), new ArrayList());
                        }
                  }

                  RCURLY229 = (Token)this.match(this.input, 84, FOLLOW_RCURLY_in_atom6173);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_RCURLY.add(RCURLY229);
                  }
                  break;
               case 4:
                  root_0 = (PythonTree)this.adaptor.nil();
                  lb = (Token)this.match(this.input, 85, FOLLOW_BACKQUOTE_in_atom6184);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     lb_tree = (PythonTree)this.adaptor.create(lb);
                     this.adaptor.addChild(root_0, lb_tree);
                  }

                  this.pushFollow(FOLLOW_testlist_in_atom6186);
                  testlist230 = this.testlist(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, testlist230.getTree());
                  }

                  rb = (Token)this.match(this.input, 85, FOLLOW_BACKQUOTE_in_atom6191);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     rb_tree = (PythonTree)this.adaptor.create(rb);
                     this.adaptor.addChild(root_0, rb_tree);
                  }

                  if (this.state.backtracking == 0) {
                     etype = new Repr(lb, this.actions.castExpr(testlist230 != null ? testlist230.tree : null));
                  }
                  break;
               case 5:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_name_or_print_in_atom6209);
                  name_or_print231 = this.name_or_print();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, name_or_print231.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     etype = new Name(name_or_print231 != null ? name_or_print231.start : null, name_or_print231 != null ? this.input.toString(name_or_print231.start, name_or_print231.stop) : null, ((expr_scope)this.expr_stack.peek()).ctype);
                  }
                  break;
               case 6:
                  root_0 = (PythonTree)this.adaptor.nil();
                  INT232 = (Token)this.match(this.input, 86, FOLLOW_INT_in_atom6227);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     INT232_tree = (PythonTree)this.adaptor.create(INT232);
                     this.adaptor.addChild(root_0, INT232_tree);
                  }

                  if (this.state.backtracking == 0) {
                     etype = new Num(INT232, this.actions.makeInt(INT232));
                  }
                  break;
               case 7:
                  root_0 = (PythonTree)this.adaptor.nil();
                  LONGINT233 = (Token)this.match(this.input, 87, FOLLOW_LONGINT_in_atom6245);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LONGINT233_tree = (PythonTree)this.adaptor.create(LONGINT233);
                     this.adaptor.addChild(root_0, LONGINT233_tree);
                  }

                  if (this.state.backtracking == 0) {
                     etype = new Num(LONGINT233, this.actions.makeInt(LONGINT233));
                  }
                  break;
               case 8:
                  root_0 = (PythonTree)this.adaptor.nil();
                  FLOAT234 = (Token)this.match(this.input, 88, FOLLOW_FLOAT_in_atom6263);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     FLOAT234_tree = (PythonTree)this.adaptor.create(FLOAT234);
                     this.adaptor.addChild(root_0, FLOAT234_tree);
                  }

                  if (this.state.backtracking == 0) {
                     etype = new Num(FLOAT234, this.actions.makeFloat(FLOAT234));
                  }
                  break;
               case 9:
                  root_0 = (PythonTree)this.adaptor.nil();
                  COMPLEX235 = (Token)this.match(this.input, 89, FOLLOW_COMPLEX_in_atom6281);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     COMPLEX235_tree = (PythonTree)this.adaptor.create(COMPLEX235);
                     this.adaptor.addChild(root_0, COMPLEX235_tree);
                  }

                  if (this.state.backtracking == 0) {
                     etype = new Num(COMPLEX235, this.actions.makeComplex(COMPLEX235));
                  }
                  break;
               case 10:
                  root_0 = (PythonTree)this.adaptor.nil();
                  int cnt111 = 0;

                  label928:
                  while(true) {
                     LA108_0 = 2;
                     int LA111_0 = this.input.LA(1);
                     if (LA111_0 == 90) {
                        LA108_0 = 1;
                     }

                     switch (LA108_0) {
                        case 1:
                           S = (Token)this.match(this.input, 90, FOLLOW_STRING_in_atom6302);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              S_tree = (PythonTree)this.adaptor.create(S);
                              this.adaptor.addChild(root_0, S_tree);
                           }

                           if (list_S == null) {
                              list_S = new ArrayList();
                           }

                           list_S.add(S);
                           ++cnt111;
                           break;
                        default:
                           if (cnt111 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(111, this.input);
                              throw eee;
                           }

                           if (this.state.backtracking == 0) {
                              etype = new Str(this.actions.extractStringToken(list_S), this.actions.extractStrings(list_S, this.encoding, this.unicodeLiterals));
                           }
                           break label928;
                     }
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && etype != null) {
               retval.tree = (PythonTree)etype;
            }
         } catch (RecognitionException var55) {
            this.reportError(var55);
            this.errorHandler.recover(this, this.input, var55);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var55);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final listmaker_return listmaker(Token lbrack) throws RecognitionException {
      listmaker_return retval = new listmaker_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token COMMA237 = null;
      Token COMMA238 = null;
      List list_t = null;
      list_for_return list_for236 = null;
      test_return t = null;
      t = null;
      PythonTree COMMA237_tree = null;
      PythonTree COMMA238_tree = null;
      List gens = new ArrayList();
      expr etype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_test_in_listmaker6345);
            t = this.test(((expr_scope)this.expr_stack.peek()).ctype);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, t.getTree());
            }

            if (list_t == null) {
               list_t = new ArrayList();
            }

            list_t.add(t.getTree());
            int alt114 = true;
            int LA114_0 = this.input.LA(1);
            byte alt114;
            if (LA114_0 == 25) {
               alt114 = 1;
            } else {
               if (LA114_0 != 47 && LA114_0 != 82) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 114, 0, this.input);
                  throw nvae;
               }

               alt114 = 2;
            }

            byte alt113;
            int LA115_0;
            switch (alt114) {
               case 1:
                  this.pushFollow(FOLLOW_list_for_in_listmaker6357);
                  list_for236 = this.list_for(gens);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, list_for236.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     Collections.reverse(gens);
                     etype = new ListComp(retval.start, this.actions.castExpr(list_t.get(0)), gens);
                  }
                  break;
               case 2:
                  label306:
                  while(true) {
                     alt113 = 2;
                     LA115_0 = this.input.LA(1);
                     if (LA115_0 == 47) {
                        int LA113_1 = this.input.LA(2);
                        if (LA113_1 == 9 || LA113_1 == 11 || LA113_1 >= 31 && LA113_1 <= 32 || LA113_1 == 43 || LA113_1 >= 75 && LA113_1 <= 76 || LA113_1 >= 80 && LA113_1 <= 81 || LA113_1 == 83 || LA113_1 >= 85 && LA113_1 <= 90) {
                           alt113 = 1;
                        }
                     }

                     switch (alt113) {
                        case 1:
                           COMMA237 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_listmaker6389);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              COMMA237_tree = (PythonTree)this.adaptor.create(COMMA237);
                              this.adaptor.addChild(root_0, COMMA237_tree);
                           }

                           this.pushFollow(FOLLOW_test_in_listmaker6393);
                           t = this.test(((expr_scope)this.expr_stack.peek()).ctype);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, t.getTree());
                           }

                           if (list_t == null) {
                              list_t = new ArrayList();
                           }

                           list_t.add(t.getTree());
                           break;
                        default:
                           if (this.state.backtracking == 0) {
                              etype = new org.python.antlr.ast.List(lbrack, this.actions.castExprs(list_t), ((expr_scope)this.expr_stack.peek()).ctype);
                           }
                           break label306;
                     }
                  }
            }

            alt113 = 2;
            LA115_0 = this.input.LA(1);
            if (LA115_0 == 47) {
               alt113 = 1;
            }

            switch (alt113) {
               case 1:
                  COMMA238 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_listmaker6422);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     COMMA238_tree = (PythonTree)this.adaptor.create(COMMA238);
                     this.adaptor.addChild(root_0, COMMA238_tree);
                  }
               default:
                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)etype;
                  }
            }
         } catch (RecognitionException var21) {
            this.reportError(var21);
            this.errorHandler.recover(this, this.input, var21);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var21);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final testlist_gexp_return testlist_gexp() throws RecognitionException {
      testlist_gexp_return retval = new testlist_gexp_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token c1 = null;
      Token c2 = null;
      List list_t = null;
      comp_for_return comp_for239 = null;
      test_return t = null;
      t = null;
      PythonTree c1_tree = null;
      PythonTree c2_tree = null;
      RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
      RewriteRuleSubtreeStream stream_test = new RewriteRuleSubtreeStream(this.adaptor, "rule test");
      RewriteRuleSubtreeStream stream_comp_for = new RewriteRuleSubtreeStream(this.adaptor, "rule comp_for");
      expr etype = null;
      List gens = new ArrayList();

      try {
         try {
            this.pushFollow(FOLLOW_test_in_testlist_gexp6454);
            t = this.test(((expr_scope)this.expr_stack.peek()).ctype);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_test.add(t.getTree());
            }

            if (list_t == null) {
               list_t = new ArrayList();
            }

            list_t.add(t.getTree());
            int alt118 = true;
            int alt116;
            byte alt118;
            switch (this.input.LA(1)) {
               case 25:
                  alt118 = 3;
                  break;
               case 44:
                  alt116 = this.input.LA(2);
                  if (c1 == null && c2 == null) {
                     alt118 = 2;
                     break;
                  }

                  alt118 = 1;
                  break;
               case 47:
                  alt118 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 118, 0, this.input);
                  throw nvae;
            }

            label285:
            switch (alt118) {
               case 1:
                  while(true) {
                     int alt116 = true;
                     alt116 = this.dfa116.predict(this.input);
                     switch (alt116) {
                        case 1:
                           c1 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_testlist_gexp6478);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_COMMA.add(c1);
                           }

                           this.pushFollow(FOLLOW_test_in_testlist_gexp6482);
                           t = this.test(((expr_scope)this.expr_stack.peek()).ctype);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_test.add(t.getTree());
                           }

                           if (list_t == null) {
                              list_t = new ArrayList();
                           }

                           list_t.add(t.getTree());
                           break;
                        default:
                           int alt117 = 2;
                           int LA117_0 = this.input.LA(1);
                           if (LA117_0 == 47) {
                              alt117 = 1;
                           }

                           switch (alt117) {
                              case 1:
                                 c2 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_testlist_gexp6490);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    stream_COMMA.add(c2);
                                 }
                              default:
                                 if (c1 == null && c2 == null) {
                                    if (this.state.backtracking > 0) {
                                       this.state.failed = true;
                                       return retval;
                                    }

                                    throw new FailedPredicateException(this.input, "testlist_gexp", " $c1 != null || $c2 != null ");
                                 }

                                 if (this.state.backtracking == 0) {
                                    etype = new Tuple(retval.start, this.actions.castExprs(list_t), ((expr_scope)this.expr_stack.peek()).ctype);
                                 }
                                 break label285;
                           }
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_test.nextTree());
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  this.pushFollow(FOLLOW_comp_for_in_testlist_gexp6544);
                  comp_for239 = this.comp_for(gens);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_comp_for.add(comp_for239.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     Collections.reverse(gens);
                     expr e = this.actions.castExpr(list_t.get(0));
                     if (e instanceof Context) {
                        ((Context)e).setContext(expr_contextType.Load);
                     }

                     etype = new GeneratorExp(retval.start, this.actions.castExpr(list_t.get(0)), gens);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && etype != null) {
               retval.tree = (PythonTree)etype;
            }
         } catch (RecognitionException var22) {
            this.reportError(var22);
            this.errorHandler.recover(this, this.input, var22);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var22);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final lambdef_return lambdef() throws RecognitionException {
      lambdef_return retval = new lambdef_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token LAMBDA240 = null;
      Token COLON242 = null;
      varargslist_return varargslist241 = null;
      test_return test243 = null;
      PythonTree LAMBDA240_tree = null;
      PythonTree COLON242_tree = null;
      expr etype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            LAMBDA240 = (Token)this.match(this.input, 31, FOLLOW_LAMBDA_in_lambdef6608);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               LAMBDA240_tree = (PythonTree)this.adaptor.create(LAMBDA240);
               this.adaptor.addChild(root_0, LAMBDA240_tree);
            }

            int alt119 = 2;
            int LA119_0 = this.input.LA(1);
            if (LA119_0 == 9 || LA119_0 == 43 || LA119_0 >= 48 && LA119_0 <= 49) {
               alt119 = 1;
            }

            switch (alt119) {
               case 1:
                  this.pushFollow(FOLLOW_varargslist_in_lambdef6611);
                  varargslist241 = this.varargslist();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, varargslist241.getTree());
                  }
               default:
                  COLON242 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_lambdef6615);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     COLON242_tree = (PythonTree)this.adaptor.create(COLON242);
                     this.adaptor.addChild(root_0, COLON242_tree);
                  }

                  this.pushFollow(FOLLOW_test_in_lambdef6617);
                  test243 = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, test243.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     arguments a = varargslist241 != null ? varargslist241.args : null;
                     if (a == null) {
                        a = new arguments(LAMBDA240, new ArrayList(), (Name)null, (Name)null, new ArrayList());
                     }

                     etype = new Lambda(LAMBDA240, a, this.actions.castExpr(test243 != null ? test243.tree : null));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = etype;
                  }
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.errorHandler.recover(this, this.input, var16);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final trailer_return trailer(Token begin, PythonTree ptree) throws RecognitionException {
      trailer_return retval = new trailer_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token LPAREN244 = null;
      Token RPAREN246 = null;
      Token LBRACK247 = null;
      Token RBRACK249 = null;
      Token DOT250 = null;
      arglist_return arglist245 = null;
      subscriptlist_return subscriptlist248 = null;
      attr_return attr251 = null;
      PythonTree LPAREN244_tree = null;
      PythonTree RPAREN246_tree = null;
      PythonTree LBRACK247_tree = null;
      PythonTree RBRACK249_tree = null;
      PythonTree DOT250_tree = null;
      expr etype = null;

      try {
         try {
            int alt121 = true;
            byte alt121;
            switch (this.input.LA(1)) {
               case 10:
                  alt121 = 3;
                  break;
               case 43:
                  alt121 = 1;
                  break;
               case 81:
                  alt121 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 121, 0, this.input);
                  throw nvae;
            }

            switch (alt121) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  LPAREN244 = (Token)this.match(this.input, 43, FOLLOW_LPAREN_in_trailer6656);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LPAREN244_tree = (PythonTree)this.adaptor.create(LPAREN244);
                     this.adaptor.addChild(root_0, LPAREN244_tree);
                  }

                  int alt120 = true;
                  int LA120_0 = this.input.LA(1);
                  byte alt120;
                  if (LA120_0 != 9 && LA120_0 != 32 && LA120_0 != 43 && (LA120_0 < 75 || LA120_0 > 76) && (LA120_0 < 80 || LA120_0 > 81) && LA120_0 != 83 && LA120_0 != 85) {
                     if (LA120_0 == 11 && this.printFunction) {
                        alt120 = 1;
                     } else if (LA120_0 != 31 && (LA120_0 < 48 || LA120_0 > 49) && (LA120_0 < 86 || LA120_0 > 90)) {
                        if (LA120_0 != 44) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           NoViableAltException nvae = new NoViableAltException("", 120, 0, this.input);
                           throw nvae;
                        }

                        alt120 = 2;
                     } else {
                        alt120 = 1;
                     }
                  } else {
                     alt120 = 1;
                  }

                  switch (alt120) {
                     case 1:
                        this.pushFollow(FOLLOW_arglist_in_trailer6665);
                        arglist245 = this.arglist();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, arglist245.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           etype = new Call(begin, this.actions.castExpr(ptree), this.actions.castExprs(arglist245 != null ? arglist245.args : null), this.actions.makeKeywords(arglist245 != null ? arglist245.keywords : null), arglist245 != null ? arglist245.starargs : null, arglist245 != null ? arglist245.kwargs : null);
                        }
                        break;
                     case 2:
                        if (this.state.backtracking == 0) {
                           etype = new Call(begin, this.actions.castExpr(ptree), new ArrayList(), new ArrayList(), (expr)null, (expr)null);
                        }
                  }

                  RPAREN246 = (Token)this.match(this.input, 44, FOLLOW_RPAREN_in_trailer6707);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RPAREN246_tree = (PythonTree)this.adaptor.create(RPAREN246);
                     this.adaptor.addChild(root_0, RPAREN246_tree);
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  LBRACK247 = (Token)this.match(this.input, 81, FOLLOW_LBRACK_in_trailer6715);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LBRACK247_tree = (PythonTree)this.adaptor.create(LBRACK247);
                     this.adaptor.addChild(root_0, LBRACK247_tree);
                  }

                  this.pushFollow(FOLLOW_subscriptlist_in_trailer6717);
                  subscriptlist248 = this.subscriptlist(begin);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, subscriptlist248.getTree());
                  }

                  RBRACK249 = (Token)this.match(this.input, 82, FOLLOW_RBRACK_in_trailer6720);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RBRACK249_tree = (PythonTree)this.adaptor.create(RBRACK249);
                     this.adaptor.addChild(root_0, RBRACK249_tree);
                  }

                  if (this.state.backtracking == 0) {
                     etype = new Subscript(begin, this.actions.castExpr(ptree), this.actions.castSlice(subscriptlist248 != null ? subscriptlist248.tree : null), ((expr_scope)this.expr_stack.peek()).ctype);
                  }
                  break;
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  DOT250 = (Token)this.match(this.input, 10, FOLLOW_DOT_in_trailer6736);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     DOT250_tree = (PythonTree)this.adaptor.create(DOT250);
                     this.adaptor.addChild(root_0, DOT250_tree);
                  }

                  this.pushFollow(FOLLOW_attr_in_trailer6738);
                  attr251 = this.attr();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, attr251.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     etype = new Attribute(begin, this.actions.castExpr(ptree), new Name(attr251 != null ? attr251.tree : null, attr251 != null ? this.input.toString(attr251.start, attr251.stop) : null, expr_contextType.Load), ((expr_scope)this.expr_stack.peek()).ctype);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && etype != null) {
               retval.tree = (PythonTree)etype;
            }
         } catch (RecognitionException var26) {
            this.reportError(var26);
            this.errorHandler.recover(this, this.input, var26);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var26);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final subscriptlist_return subscriptlist(Token begin) throws RecognitionException {
      subscriptlist_return retval = new subscriptlist_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token c1 = null;
      Token c2 = null;
      List list_sub = null;
      subscript_return sub = null;
      sub = null;
      PythonTree c1_tree = null;
      PythonTree c2_tree = null;
      slice sltype = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_subscript_in_subscriptlist6777);
         sub = this.subscript();
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, sub.getTree());
            }

            if (list_sub == null) {
               list_sub = new ArrayList();
            }

            list_sub.add(sub.getTree());

            while(true) {
               int alt123 = 2;
               int LA123_0 = this.input.LA(1);
               if (LA123_0 == 47) {
                  int LA122_1 = this.input.LA(2);
                  if (LA122_1 >= 9 && LA122_1 <= 11 || LA122_1 >= 31 && LA122_1 <= 32 || LA122_1 == 43 || LA122_1 == 45 || LA122_1 >= 75 && LA122_1 <= 76 || LA122_1 >= 80 && LA122_1 <= 81 || LA122_1 == 83 || LA122_1 >= 85 && LA122_1 <= 90) {
                     alt123 = 1;
                  }
               }

               switch (alt123) {
                  case 1:
                     c1 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_subscriptlist6789);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        c1_tree = (PythonTree)this.adaptor.create(c1);
                        this.adaptor.addChild(root_0, c1_tree);
                     }

                     this.pushFollow(FOLLOW_subscript_in_subscriptlist6793);
                     sub = this.subscript();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, sub.getTree());
                     }

                     if (list_sub == null) {
                        list_sub = new ArrayList();
                     }

                     list_sub.add(sub.getTree());
                     break;
                  default:
                     alt123 = 2;
                     LA123_0 = this.input.LA(1);
                     if (LA123_0 == 47) {
                        alt123 = 1;
                     }

                     switch (alt123) {
                        case 1:
                           c2 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_subscriptlist6800);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              c2_tree = (PythonTree)this.adaptor.create(c2);
                              this.adaptor.addChild(root_0, c2_tree);
                           }
                        default:
                           if (this.state.backtracking == 0) {
                              sltype = this.actions.makeSliceType(begin, c1, c2, list_sub);
                           }

                           retval.stop = this.input.LT(-1);
                           if (this.state.backtracking == 0) {
                              retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                              this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                           }

                           if (this.state.backtracking == 0) {
                              retval.tree = sltype;
                           }

                           return retval;
                     }
               }
            }
         }
      } catch (RecognitionException var17) {
         this.reportError(var17);
         this.errorHandler.recover(this, this.input, var17);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         return retval;
      } finally {
         ;
      }
   }

   public final subscript_return subscript() throws RecognitionException {
      subscript_return retval = new subscript_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token d1 = null;
      Token c1 = null;
      Token c2 = null;
      Token DOT252 = null;
      Token DOT253 = null;
      test_return lower = null;
      test_return upper1 = null;
      test_return upper2 = null;
      sliceop_return sliceop254 = null;
      sliceop_return sliceop255 = null;
      test_return test256 = null;
      PythonTree d1_tree = null;
      PythonTree c1_tree = null;
      PythonTree c2_tree = null;
      PythonTree DOT252_tree = null;
      PythonTree DOT253_tree = null;

      try {
         try {
            int alt129 = true;
            int alt129 = this.dfa129.predict(this.input);
            byte alt127;
            int LA127_0;
            byte alt124;
            int LA124_0;
            label583:
            switch (alt129) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  d1 = (Token)this.match(this.input, 10, FOLLOW_DOT_in_subscript6843);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     d1_tree = (PythonTree)this.adaptor.create(d1);
                     this.adaptor.addChild(root_0, d1_tree);
                  }

                  DOT252 = (Token)this.match(this.input, 10, FOLLOW_DOT_in_subscript6845);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     DOT252_tree = (PythonTree)this.adaptor.create(DOT252);
                     this.adaptor.addChild(root_0, DOT252_tree);
                  }

                  DOT253 = (Token)this.match(this.input, 10, FOLLOW_DOT_in_subscript6847);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     DOT253_tree = (PythonTree)this.adaptor.create(DOT253);
                     this.adaptor.addChild(root_0, DOT253_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.sltype = new Ellipsis(d1);
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_test_in_subscript6877);
                  lower = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, lower.getTree());
                  }

                  alt127 = 2;
                  LA127_0 = this.input.LA(1);
                  if (LA127_0 == 45) {
                     alt127 = 1;
                  }

                  switch (alt127) {
                     case 1:
                        c1 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_subscript6883);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           c1_tree = (PythonTree)this.adaptor.create(c1);
                           this.adaptor.addChild(root_0, c1_tree);
                        }

                        alt124 = 2;
                        LA124_0 = this.input.LA(1);
                        if (LA124_0 != 9 && LA124_0 != 32 && LA124_0 != 43 && (LA124_0 < 75 || LA124_0 > 76) && (LA124_0 < 80 || LA124_0 > 81) && LA124_0 != 83 && LA124_0 != 85) {
                           if (LA124_0 == 11 && this.printFunction) {
                              alt124 = 1;
                           } else if (LA124_0 == 31 || LA124_0 >= 86 && LA124_0 <= 90) {
                              alt124 = 1;
                           }
                        } else {
                           alt124 = 1;
                        }

                        switch (alt124) {
                           case 1:
                              this.pushFollow(FOLLOW_test_in_subscript6888);
                              upper1 = this.test(expr_contextType.Load);
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 this.adaptor.addChild(root_0, upper1.getTree());
                              }
                           default:
                              int alt125 = 2;
                              int LA125_0 = this.input.LA(1);
                              if (LA125_0 == 45) {
                                 alt125 = 1;
                              }

                              switch (alt125) {
                                 case 1:
                                    this.pushFollow(FOLLOW_sliceop_in_subscript6894);
                                    sliceop254 = this.sliceop();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       this.adaptor.addChild(root_0, sliceop254.getTree());
                                    }
                              }
                        }
                     default:
                        if (this.state.backtracking == 0) {
                           retval.sltype = this.actions.makeSubscript(lower != null ? lower.tree : null, c1, upper1 != null ? upper1.tree : null, sliceop254 != null ? sliceop254.tree : null);
                        }
                        break label583;
                  }
               case 3:
                  root_0 = (PythonTree)this.adaptor.nil();
                  c2 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_subscript6925);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     c2_tree = (PythonTree)this.adaptor.create(c2);
                     this.adaptor.addChild(root_0, c2_tree);
                  }

                  alt127 = 2;
                  LA127_0 = this.input.LA(1);
                  if (LA127_0 != 9 && LA127_0 != 32 && LA127_0 != 43 && (LA127_0 < 75 || LA127_0 > 76) && (LA127_0 < 80 || LA127_0 > 81) && LA127_0 != 83 && LA127_0 != 85) {
                     if (LA127_0 == 11 && this.printFunction) {
                        alt127 = 1;
                     } else if (LA127_0 == 31 || LA127_0 >= 86 && LA127_0 <= 90) {
                        alt127 = 1;
                     }
                  } else {
                     alt127 = 1;
                  }

                  switch (alt127) {
                     case 1:
                        this.pushFollow(FOLLOW_test_in_subscript6930);
                        upper2 = this.test(expr_contextType.Load);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, upper2.getTree());
                        }
                     default:
                        alt124 = 2;
                        LA124_0 = this.input.LA(1);
                        if (LA124_0 == 45) {
                           alt124 = 1;
                        }

                        switch (alt124) {
                           case 1:
                              this.pushFollow(FOLLOW_sliceop_in_subscript6936);
                              sliceop255 = this.sliceop();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 this.adaptor.addChild(root_0, sliceop255.getTree());
                              }
                           default:
                              if (this.state.backtracking == 0) {
                                 retval.sltype = this.actions.makeSubscript((PythonTree)null, c2, upper2 != null ? upper2.tree : null, sliceop255 != null ? sliceop255.tree : null);
                              }
                              break label583;
                        }
                  }
               case 4:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_test_in_subscript6954);
                  test256 = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, test256.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.sltype = new Index(test256 != null ? test256.start : null, this.actions.castExpr(test256 != null ? test256.tree : null));
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = retval.sltype;
            }
         } catch (RecognitionException var30) {
            this.reportError(var30);
            this.errorHandler.recover(this, this.input, var30);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var30);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final sliceop_return sliceop() throws RecognitionException {
      sliceop_return retval = new sliceop_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token COLON257 = null;
      test_return test258 = null;
      PythonTree COLON257_tree = null;
      RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
      RewriteRuleSubtreeStream stream_test = new RewriteRuleSubtreeStream(this.adaptor, "rule test");
      expr etype = null;

      try {
         try {
            COLON257 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_sliceop6991);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_COLON.add(COLON257);
            }

            int alt130 = true;
            int LA130_0 = this.input.LA(1);
            byte alt130;
            if (LA130_0 != 9 && LA130_0 != 32 && LA130_0 != 43 && (LA130_0 < 75 || LA130_0 > 76) && (LA130_0 < 80 || LA130_0 > 81) && LA130_0 != 83 && LA130_0 != 85) {
               if (LA130_0 == 11 && this.printFunction) {
                  alt130 = 1;
               } else if (LA130_0 != 31 && (LA130_0 < 86 || LA130_0 > 90)) {
                  if (LA130_0 != 47 && LA130_0 != 82) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 130, 0, this.input);
                     throw nvae;
                  }

                  alt130 = 2;
               } else {
                  alt130 = 1;
               }
            } else {
               alt130 = 1;
            }

            switch (alt130) {
               case 1:
                  this.pushFollow(FOLLOW_test_in_sliceop6999);
                  test258 = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_test.add(test258.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_test.nextTree());
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  if (this.state.backtracking == 0) {
                     etype = new Name(COLON257, "None", expr_contextType.Load);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && etype != null) {
               retval.tree = etype;
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.errorHandler.recover(this, this.input, var15);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final exprlist_return exprlist(expr_contextType ctype) throws RecognitionException {
      exprlist_return retval = new exprlist_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token COMMA259 = null;
      Token COMMA260 = null;
      List list_e = null;
      expr_return expr261 = null;
      expr_return e = null;
      e = null;
      PythonTree COMMA259_tree = null;
      PythonTree COMMA260_tree = null;

      try {
         try {
            int alt133 = true;
            int alt133 = this.dfa133.predict(this.input);
            label203:
            switch (alt133) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_expr_in_exprlist7070);
                  e = this.expr(ctype);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, e.getTree());
                  }

                  if (list_e == null) {
                     list_e = new ArrayList();
                  }

                  list_e.add(e.getTree());

                  while(true) {
                     int alt131 = true;
                     int alt131 = this.dfa131.predict(this.input);
                     switch (alt131) {
                        case 1:
                           COMMA259 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_exprlist7082);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              COMMA259_tree = (PythonTree)this.adaptor.create(COMMA259);
                              this.adaptor.addChild(root_0, COMMA259_tree);
                           }

                           this.pushFollow(FOLLOW_expr_in_exprlist7086);
                           e = this.expr(ctype);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, e.getTree());
                           }

                           if (list_e == null) {
                              list_e = new ArrayList();
                           }

                           list_e.add(e.getTree());
                           break;
                        default:
                           int alt132 = 2;
                           int LA132_0 = this.input.LA(1);
                           if (LA132_0 == 47) {
                              alt132 = 1;
                           }

                           switch (alt132) {
                              case 1:
                                 COMMA260 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_exprlist7092);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    COMMA260_tree = (PythonTree)this.adaptor.create(COMMA260);
                                    this.adaptor.addChild(root_0, COMMA260_tree);
                                 }
                              default:
                                 if (this.state.backtracking == 0) {
                                    retval.etype = new Tuple(retval.start, this.actions.castExprs(list_e), ctype);
                                 }
                                 break label203;
                           }
                     }
                  }
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_expr_in_exprlist7111);
                  expr261 = this.expr(ctype);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, expr261.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.etype = this.actions.castExpr(expr261 != null ? expr261.tree : null);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.errorHandler.recover(this, this.input, var18);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final del_list_return del_list() throws RecognitionException {
      del_list_return retval = new del_list_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token COMMA262 = null;
      Token COMMA263 = null;
      List list_e = null;
      expr_return e = null;
      e = null;
      PythonTree COMMA262_tree = null;
      PythonTree COMMA263_tree = null;

      try {
         root_0 = (PythonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_expr_in_del_list7149);
         e = this.expr(expr_contextType.Del);
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, e.getTree());
            }

            if (list_e == null) {
               list_e = new ArrayList();
            }

            list_e.add(e.getTree());

            while(true) {
               int alt134 = true;
               int alt134 = this.dfa134.predict(this.input);
               switch (alt134) {
                  case 1:
                     COMMA262 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_del_list7161);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        COMMA262_tree = (PythonTree)this.adaptor.create(COMMA262);
                        this.adaptor.addChild(root_0, COMMA262_tree);
                     }

                     this.pushFollow(FOLLOW_expr_in_del_list7165);
                     e = this.expr(expr_contextType.Del);
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, e.getTree());
                     }

                     if (list_e == null) {
                        list_e = new ArrayList();
                     }

                     list_e.add(e.getTree());
                     break;
                  default:
                     int alt135 = 2;
                     int LA135_0 = this.input.LA(1);
                     if (LA135_0 == 47) {
                        alt135 = 1;
                     }

                     switch (alt135) {
                        case 1:
                           COMMA263 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_del_list7171);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              COMMA263_tree = (PythonTree)this.adaptor.create(COMMA263);
                              this.adaptor.addChild(root_0, COMMA263_tree);
                           }
                        default:
                           if (this.state.backtracking == 0) {
                              retval.etypes = this.actions.makeDeleteList(list_e);
                           }

                           retval.stop = this.input.LT(-1);
                           if (this.state.backtracking == 0) {
                              retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                              this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                           }

                           return retval;
                     }
               }
            }
         }
      } catch (RecognitionException var15) {
         this.reportError(var15);
         this.errorHandler.recover(this, this.input, var15);
         retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         return retval;
      } finally {
         ;
      }
   }

   public final testlist_return testlist(expr_contextType ctype) throws RecognitionException {
      testlist_return retval = new testlist_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token COMMA264 = null;
      Token COMMA265 = null;
      List list_t = null;
      test_return test266 = null;
      test_return t = null;
      t = null;
      PythonTree COMMA264_tree = null;
      PythonTree COMMA265_tree = null;
      expr etype = null;

      try {
         try {
            int alt138 = true;
            int alt138 = this.dfa138.predict(this.input);
            label197:
            switch (alt138) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_test_in_testlist7224);
                  t = this.test(ctype);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t.getTree());
                  }

                  if (list_t == null) {
                     list_t = new ArrayList();
                  }

                  list_t.add(t.getTree());

                  while(true) {
                     int alt136 = true;
                     int alt136 = this.dfa136.predict(this.input);
                     switch (alt136) {
                        case 1:
                           COMMA264 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_testlist7236);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              COMMA264_tree = (PythonTree)this.adaptor.create(COMMA264);
                              this.adaptor.addChild(root_0, COMMA264_tree);
                           }

                           this.pushFollow(FOLLOW_test_in_testlist7240);
                           t = this.test(ctype);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, t.getTree());
                           }

                           if (list_t == null) {
                              list_t = new ArrayList();
                           }

                           list_t.add(t.getTree());
                           break;
                        default:
                           int alt137 = 2;
                           int LA137_0 = this.input.LA(1);
                           if (LA137_0 == 47) {
                              alt137 = 1;
                           }

                           switch (alt137) {
                              case 1:
                                 COMMA265 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_testlist7246);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    COMMA265_tree = (PythonTree)this.adaptor.create(COMMA265);
                                    this.adaptor.addChild(root_0, COMMA265_tree);
                                 }
                              default:
                                 if (this.state.backtracking == 0) {
                                    etype = new Tuple(retval.start, this.actions.castExprs(list_t), ctype);
                                 }
                                 break label197;
                           }
                     }
                  }
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_test_in_testlist7264);
                  test266 = this.test(ctype);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, test266.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && etype != null) {
               retval.tree = etype;
            }
         } catch (RecognitionException var19) {
            this.reportError(var19);
            this.errorHandler.recover(this, this.input, var19);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var19);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final dictorsetmaker_return dictorsetmaker(Token lcurly) throws RecognitionException {
      dictorsetmaker_return retval = new dictorsetmaker_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token COLON267 = null;
      Token COMMA269 = null;
      Token COLON270 = null;
      Token COMMA271 = null;
      Token COMMA272 = null;
      List list_k = null;
      List list_v = null;
      comp_for_return comp_for268 = null;
      comp_for_return comp_for273 = null;
      test_return k = null;
      k = null;
      test_return v = null;
      v = null;
      PythonTree COLON267_tree = null;
      PythonTree COMMA269_tree = null;
      PythonTree COLON270_tree = null;
      PythonTree COMMA271_tree = null;
      PythonTree COMMA272_tree = null;
      List gens = new ArrayList();
      expr etype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_test_in_dictorsetmaker7297);
            k = this.test(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, k.getTree());
            }

            if (list_k == null) {
               list_k = new ArrayList();
            }

            list_k.add(k.getTree());
            int alt144 = true;
            int LA144_0 = this.input.LA(1);
            byte alt144;
            if (LA144_0 != 45 && LA144_0 != 47 && LA144_0 != 84) {
               if (LA144_0 != 25) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 144, 0, this.input);
                  throw nvae;
               }

               alt144 = 2;
            } else {
               alt144 = 1;
            }

            label634:
            switch (alt144) {
               case 1:
                  int alt142 = true;
                  int LA142_0 = this.input.LA(1);
                  byte alt142;
                  if (LA142_0 == 45) {
                     alt142 = 1;
                  } else {
                     if (LA142_0 != 47 && LA142_0 != 84) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 142, 0, this.input);
                        throw nvae;
                     }

                     alt142 = 2;
                  }

                  byte alt140;
                  int LA140_0;
                  int alt139;
                  label600:
                  switch (alt142) {
                     case 1:
                        COLON267 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_dictorsetmaker7325);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           COLON267_tree = (PythonTree)this.adaptor.create(COLON267);
                           this.adaptor.addChild(root_0, COLON267_tree);
                        }

                        this.pushFollow(FOLLOW_test_in_dictorsetmaker7329);
                        v = this.test(expr_contextType.Load);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, v.getTree());
                        }

                        if (list_v == null) {
                           list_v = new ArrayList();
                        }

                        list_v.add(v.getTree());
                        int alt140 = true;
                        LA140_0 = this.input.LA(1);
                        if (LA140_0 == 25) {
                           alt140 = 1;
                        } else {
                           if (LA140_0 != 47 && LA140_0 != 84) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              NoViableAltException nvae = new NoViableAltException("", 140, 0, this.input);
                              throw nvae;
                           }

                           alt140 = 2;
                        }

                        switch (alt140) {
                           case 1:
                              this.pushFollow(FOLLOW_comp_for_in_dictorsetmaker7349);
                              comp_for268 = this.comp_for(gens);
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 this.adaptor.addChild(root_0, comp_for268.getTree());
                              }

                              if (this.state.backtracking == 0) {
                                 Collections.reverse(gens);
                                 etype = new DictComp(retval.start, this.actions.castExpr(list_k.get(0)), this.actions.castExpr(list_v.get(0)), gens);
                              }
                              break label600;
                           case 2:
                              while(true) {
                                 int alt139 = true;
                                 alt139 = this.dfa139.predict(this.input);
                                 switch (alt139) {
                                    case 1:
                                       COMMA269 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_dictorsetmaker7396);
                                       if (this.state.failed) {
                                          return retval;
                                       }

                                       if (this.state.backtracking == 0) {
                                          COMMA269_tree = (PythonTree)this.adaptor.create(COMMA269);
                                          this.adaptor.addChild(root_0, COMMA269_tree);
                                       }

                                       this.pushFollow(FOLLOW_test_in_dictorsetmaker7400);
                                       k = this.test(expr_contextType.Load);
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          return retval;
                                       }

                                       if (this.state.backtracking == 0) {
                                          this.adaptor.addChild(root_0, k.getTree());
                                       }

                                       if (list_k == null) {
                                          list_k = new ArrayList();
                                       }

                                       list_k.add(k.getTree());
                                       COLON270 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_dictorsetmaker7403);
                                       if (this.state.failed) {
                                          return retval;
                                       }

                                       if (this.state.backtracking == 0) {
                                          COLON270_tree = (PythonTree)this.adaptor.create(COLON270);
                                          this.adaptor.addChild(root_0, COLON270_tree);
                                       }

                                       this.pushFollow(FOLLOW_test_in_dictorsetmaker7407);
                                       v = this.test(expr_contextType.Load);
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          return retval;
                                       }

                                       if (this.state.backtracking == 0) {
                                          this.adaptor.addChild(root_0, v.getTree());
                                       }

                                       if (list_v == null) {
                                          list_v = new ArrayList();
                                       }

                                       list_v.add(v.getTree());
                                       break;
                                    default:
                                       if (this.state.backtracking == 0) {
                                          etype = new Dict(lcurly, this.actions.castExprs(list_k), this.actions.castExprs(list_v));
                                       }
                                       break label600;
                                 }
                              }
                           default:
                              break label600;
                        }
                     case 2:
                        label630:
                        while(true) {
                           alt140 = 2;
                           LA140_0 = this.input.LA(1);
                           if (LA140_0 == 47) {
                              alt139 = this.input.LA(2);
                              if (alt139 == 9 || alt139 == 11 || alt139 >= 31 && alt139 <= 32 || alt139 == 43 || alt139 >= 75 && alt139 <= 76 || alt139 >= 80 && alt139 <= 81 || alt139 == 83 || alt139 >= 85 && alt139 <= 90) {
                                 alt140 = 1;
                              }
                           }

                           switch (alt140) {
                              case 1:
                                 COMMA271 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_dictorsetmaker7463);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    COMMA271_tree = (PythonTree)this.adaptor.create(COMMA271);
                                    this.adaptor.addChild(root_0, COMMA271_tree);
                                 }

                                 this.pushFollow(FOLLOW_test_in_dictorsetmaker7467);
                                 k = this.test(expr_contextType.Load);
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    this.adaptor.addChild(root_0, k.getTree());
                                 }

                                 if (list_k == null) {
                                    list_k = new ArrayList();
                                 }

                                 list_k.add(k.getTree());
                                 break;
                              default:
                                 if (this.state.backtracking == 0) {
                                    etype = new Set(lcurly, this.actions.castExprs(list_k));
                                 }
                                 break label630;
                           }
                        }
                  }

                  alt140 = 2;
                  LA140_0 = this.input.LA(1);
                  if (LA140_0 == 47) {
                     alt140 = 1;
                  }

                  switch (alt140) {
                     case 1:
                        COMMA272 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_dictorsetmaker7517);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           COMMA272_tree = (PythonTree)this.adaptor.create(COMMA272);
                           this.adaptor.addChild(root_0, COMMA272_tree);
                        }
                     default:
                        break label634;
                  }
               case 2:
                  this.pushFollow(FOLLOW_comp_for_in_dictorsetmaker7532);
                  comp_for273 = this.comp_for(gens);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, comp_for273.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     Collections.reverse(gens);
                     expr e = this.actions.castExpr(list_k.get(0));
                     if (e instanceof Context) {
                        ((Context)e).setContext(expr_contextType.Load);
                     }

                     etype = new SetComp(lcurly, this.actions.castExpr(list_k.get(0)), gens);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0 && etype != null) {
               retval.tree = (PythonTree)etype;
            }
         } catch (RecognitionException var33) {
            this.reportError(var33);
            this.errorHandler.recover(this, this.input, var33);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var33);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final classdef_return classdef() throws RecognitionException {
      classdef_return retval = new classdef_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token CLASS275 = null;
      Token NAME276 = null;
      Token LPAREN277 = null;
      Token RPAREN279 = null;
      Token COLON280 = null;
      decorators_return decorators274 = null;
      testlist_return testlist278 = null;
      suite_return suite281 = null;
      PythonTree CLASS275_tree = null;
      PythonTree NAME276_tree = null;
      PythonTree LPAREN277_tree = null;
      PythonTree RPAREN279_tree = null;
      PythonTree COLON280_tree = null;
      stmt stype = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            int alt145 = 2;
            int LA145_0 = this.input.LA(1);
            if (LA145_0 == 42) {
               alt145 = 1;
            }

            switch (alt145) {
               case 1:
                  this.pushFollow(FOLLOW_decorators_in_classdef7585);
                  decorators274 = this.decorators();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, decorators274.getTree());
                  }
            }

            CLASS275 = (Token)this.match(this.input, 16, FOLLOW_CLASS_in_classdef7588);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               CLASS275_tree = (PythonTree)this.adaptor.create(CLASS275);
               this.adaptor.addChild(root_0, CLASS275_tree);
            }

            NAME276 = (Token)this.match(this.input, 9, FOLLOW_NAME_in_classdef7590);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               NAME276_tree = (PythonTree)this.adaptor.create(NAME276);
               this.adaptor.addChild(root_0, NAME276_tree);
            }

            int alt147 = 2;
            int LA147_0 = this.input.LA(1);
            if (LA147_0 == 43) {
               alt147 = 1;
            }

            switch (alt147) {
               case 1:
                  LPAREN277 = (Token)this.match(this.input, 43, FOLLOW_LPAREN_in_classdef7593);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     LPAREN277_tree = (PythonTree)this.adaptor.create(LPAREN277);
                     this.adaptor.addChild(root_0, LPAREN277_tree);
                  }

                  int alt146 = 2;
                  int LA146_0 = this.input.LA(1);
                  if (LA146_0 != 9 && LA146_0 != 32 && LA146_0 != 43 && (LA146_0 < 75 || LA146_0 > 76) && (LA146_0 < 80 || LA146_0 > 81) && LA146_0 != 83 && LA146_0 != 85) {
                     if (LA146_0 == 11 && this.printFunction) {
                        alt146 = 1;
                     } else if (LA146_0 == 31 || LA146_0 >= 86 && LA146_0 <= 90) {
                        alt146 = 1;
                     }
                  } else {
                     alt146 = 1;
                  }

                  switch (alt146) {
                     case 1:
                        this.pushFollow(FOLLOW_testlist_in_classdef7595);
                        testlist278 = this.testlist(expr_contextType.Load);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, testlist278.getTree());
                        }
                     default:
                        RPAREN279 = (Token)this.match(this.input, 44, FOLLOW_RPAREN_in_classdef7599);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           RPAREN279_tree = (PythonTree)this.adaptor.create(RPAREN279);
                           this.adaptor.addChild(root_0, RPAREN279_tree);
                        }
                  }
            }

            COLON280 = (Token)this.match(this.input, 45, FOLLOW_COLON_in_classdef7603);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               COLON280_tree = (PythonTree)this.adaptor.create(COLON280);
               this.adaptor.addChild(root_0, COLON280_tree);
            }

            this.pushFollow(FOLLOW_suite_in_classdef7605);
            suite281 = this.suite(false);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, suite281.getTree());
            }

            if (this.state.backtracking == 0) {
               Token t = CLASS275;
               if ((decorators274 != null ? decorators274.start : null) != null) {
                  t = decorators274 != null ? decorators274.start : null;
               }

               stype = new ClassDef(t, this.actions.cantBeNoneName(NAME276), this.actions.makeBases(this.actions.castExpr(testlist278 != null ? testlist278.tree : null)), this.actions.castStmts(suite281 != null ? suite281.stypes : null), this.actions.castExprs(decorators274 != null ? decorators274.etypes : null));
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree = stype;
            }
         } catch (RecognitionException var27) {
            this.reportError(var27);
            this.errorHandler.recover(this, this.input, var27);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var27);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final arglist_return arglist() throws RecognitionException {
      arglist_return retval = new arglist_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token COMMA283 = null;
      Token COMMA285 = null;
      Token STAR286 = null;
      Token COMMA287 = null;
      Token COMMA289 = null;
      Token DOUBLESTAR290 = null;
      Token DOUBLESTAR291 = null;
      Token STAR292 = null;
      Token COMMA293 = null;
      Token COMMA295 = null;
      Token DOUBLESTAR296 = null;
      Token DOUBLESTAR297 = null;
      test_return s = null;
      test_return k = null;
      argument_return argument282 = null;
      argument_return argument284 = null;
      argument_return argument288 = null;
      argument_return argument294 = null;
      PythonTree COMMA283_tree = null;
      PythonTree COMMA285_tree = null;
      PythonTree STAR286_tree = null;
      PythonTree COMMA287_tree = null;
      PythonTree COMMA289_tree = null;
      PythonTree DOUBLESTAR290_tree = null;
      PythonTree DOUBLESTAR291_tree = null;
      PythonTree STAR292_tree = null;
      PythonTree COMMA293_tree = null;
      PythonTree COMMA295_tree = null;
      PythonTree DOUBLESTAR296_tree = null;
      PythonTree DOUBLESTAR297_tree = null;
      List arguments = new ArrayList();
      List kws = new ArrayList();
      List gens = new ArrayList();

      try {
         try {
            int alt155 = true;
            int LA155_0 = this.input.LA(1);
            byte alt155;
            if (LA155_0 != 9 && LA155_0 != 32 && LA155_0 != 43 && (LA155_0 < 75 || LA155_0 > 76) && (LA155_0 < 80 || LA155_0 > 81) && LA155_0 != 83 && LA155_0 != 85) {
               if (LA155_0 == 11 && this.printFunction) {
                  alt155 = 1;
               } else if (LA155_0 == 31 || LA155_0 >= 86 && LA155_0 <= 90) {
                  alt155 = 1;
               } else if (LA155_0 == 48) {
                  alt155 = 2;
               } else {
                  if (LA155_0 != 49) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 155, 0, this.input);
                     throw nvae;
                  }

                  alt155 = 3;
               }
            } else {
               alt155 = 1;
            }

            label963: {
               int LA154_0;
               int LA153_1;
               byte alt154;
               switch (alt155) {
                  case 1:
                     root_0 = (PythonTree)this.adaptor.nil();
                     this.pushFollow(FOLLOW_argument_in_arglist7647);
                     argument282 = this.argument(arguments, kws, gens, true, false);
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, argument282.getTree());
                     }
                     break;
                  case 2:
                     root_0 = (PythonTree)this.adaptor.nil();
                     STAR292 = (Token)this.match(this.input, 48, FOLLOW_STAR_in_arglist7781);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        STAR292_tree = (PythonTree)this.adaptor.create(STAR292);
                        this.adaptor.addChild(root_0, STAR292_tree);
                     }

                     this.pushFollow(FOLLOW_test_in_arglist7785);
                     s = this.test(expr_contextType.Load);
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, s.getTree());
                     }

                     while(true) {
                        alt154 = 2;
                        LA154_0 = this.input.LA(1);
                        if (LA154_0 == 47) {
                           LA153_1 = this.input.LA(2);
                           if (LA153_1 == 9 || LA153_1 == 11 || LA153_1 >= 31 && LA153_1 <= 32 || LA153_1 == 43 || LA153_1 >= 75 && LA153_1 <= 76 || LA153_1 >= 80 && LA153_1 <= 81 || LA153_1 == 83 || LA153_1 >= 85 && LA153_1 <= 90) {
                              alt154 = 1;
                           }
                        }

                        switch (alt154) {
                           case 1:
                              COMMA293 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_arglist7789);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 COMMA293_tree = (PythonTree)this.adaptor.create(COMMA293);
                                 this.adaptor.addChild(root_0, COMMA293_tree);
                              }

                              this.pushFollow(FOLLOW_argument_in_arglist7791);
                              argument294 = this.argument(arguments, kws, gens, false, true);
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 this.adaptor.addChild(root_0, argument294.getTree());
                              }
                              break;
                           default:
                              alt154 = 2;
                              LA154_0 = this.input.LA(1);
                              if (LA154_0 == 47) {
                                 alt154 = 1;
                              }

                              switch (alt154) {
                                 case 1:
                                    COMMA295 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_arglist7797);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       COMMA295_tree = (PythonTree)this.adaptor.create(COMMA295);
                                       this.adaptor.addChild(root_0, COMMA295_tree);
                                    }

                                    DOUBLESTAR296 = (Token)this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_arglist7799);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       DOUBLESTAR296_tree = (PythonTree)this.adaptor.create(DOUBLESTAR296);
                                       this.adaptor.addChild(root_0, DOUBLESTAR296_tree);
                                    }

                                    this.pushFollow(FOLLOW_test_in_arglist7803);
                                    k = this.test(expr_contextType.Load);
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       this.adaptor.addChild(root_0, k.getTree());
                                    }
                                 default:
                                    if (this.state.backtracking == 0) {
                                       retval.starargs = this.actions.castExpr(s != null ? s.tree : null);
                                       retval.keywords = kws;
                                       retval.kwargs = this.actions.castExpr(k != null ? k.tree : null);
                                    }
                                    break label963;
                              }
                        }
                     }
                  case 3:
                     root_0 = (PythonTree)this.adaptor.nil();
                     DOUBLESTAR297 = (Token)this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_arglist7822);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        DOUBLESTAR297_tree = (PythonTree)this.adaptor.create(DOUBLESTAR297);
                        this.adaptor.addChild(root_0, DOUBLESTAR297_tree);
                     }

                     this.pushFollow(FOLLOW_test_in_arglist7826);
                     k = this.test(expr_contextType.Load);
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, k.getTree());
                     }

                     if (this.state.backtracking == 0) {
                        retval.kwargs = this.actions.castExpr(k != null ? k.tree : null);
                     }
                  default:
                     break label963;
               }

               label962:
               while(true) {
                  alt154 = 2;
                  LA154_0 = this.input.LA(1);
                  if (LA154_0 == 47) {
                     LA153_1 = this.input.LA(2);
                     if (LA153_1 == 9 || LA153_1 == 11 || LA153_1 >= 31 && LA153_1 <= 32 || LA153_1 == 43 || LA153_1 >= 75 && LA153_1 <= 76 || LA153_1 >= 80 && LA153_1 <= 81 || LA153_1 == 83 || LA153_1 >= 85 && LA153_1 <= 90) {
                        alt154 = 1;
                     }
                  }

                  switch (alt154) {
                     case 1:
                        COMMA283 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_arglist7651);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           COMMA283_tree = (PythonTree)this.adaptor.create(COMMA283);
                           this.adaptor.addChild(root_0, COMMA283_tree);
                        }

                        this.pushFollow(FOLLOW_argument_in_arglist7653);
                        argument284 = this.argument(arguments, kws, gens, false, false);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, argument284.getTree());
                        }
                        break;
                     default:
                        alt154 = 2;
                        LA154_0 = this.input.LA(1);
                        if (LA154_0 == 47) {
                           alt154 = 1;
                        }

                        switch (alt154) {
                           case 1:
                              COMMA285 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_arglist7669);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 COMMA285_tree = (PythonTree)this.adaptor.create(COMMA285);
                                 this.adaptor.addChild(root_0, COMMA285_tree);
                              }

                              int alt151 = 3;
                              int LA151_0 = this.input.LA(1);
                              if (LA151_0 == 48) {
                                 alt151 = 1;
                              } else if (LA151_0 == 49) {
                                 alt151 = 2;
                              }

                              label928:
                              switch (alt151) {
                                 case 1:
                                    STAR286 = (Token)this.match(this.input, 48, FOLLOW_STAR_in_arglist7687);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       STAR286_tree = (PythonTree)this.adaptor.create(STAR286);
                                       this.adaptor.addChild(root_0, STAR286_tree);
                                    }

                                    this.pushFollow(FOLLOW_test_in_arglist7691);
                                    s = this.test(expr_contextType.Load);
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       this.adaptor.addChild(root_0, s.getTree());
                                    }

                                    while(true) {
                                       int alt150 = 2;
                                       int LA150_0 = this.input.LA(1);
                                       if (LA150_0 == 47) {
                                          int LA149_1 = this.input.LA(2);
                                          if (LA149_1 == 9 || LA149_1 == 11 || LA149_1 >= 31 && LA149_1 <= 32 || LA149_1 == 43 || LA149_1 >= 75 && LA149_1 <= 76 || LA149_1 >= 80 && LA149_1 <= 81 || LA149_1 == 83 || LA149_1 >= 85 && LA149_1 <= 90) {
                                             alt150 = 1;
                                          }
                                       }

                                       switch (alt150) {
                                          case 1:
                                             COMMA287 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_arglist7695);
                                             if (this.state.failed) {
                                                return retval;
                                             }

                                             if (this.state.backtracking == 0) {
                                                COMMA287_tree = (PythonTree)this.adaptor.create(COMMA287);
                                                this.adaptor.addChild(root_0, COMMA287_tree);
                                             }

                                             this.pushFollow(FOLLOW_argument_in_arglist7697);
                                             argument288 = this.argument(arguments, kws, gens, false, true);
                                             --this.state._fsp;
                                             if (this.state.failed) {
                                                return retval;
                                             }

                                             if (this.state.backtracking == 0) {
                                                this.adaptor.addChild(root_0, argument288.getTree());
                                             }
                                             break;
                                          default:
                                             alt150 = 2;
                                             LA150_0 = this.input.LA(1);
                                             if (LA150_0 == 47) {
                                                alt150 = 1;
                                             }

                                             switch (alt150) {
                                                case 1:
                                                   COMMA289 = (Token)this.match(this.input, 47, FOLLOW_COMMA_in_arglist7703);
                                                   if (this.state.failed) {
                                                      return retval;
                                                   }

                                                   if (this.state.backtracking == 0) {
                                                      COMMA289_tree = (PythonTree)this.adaptor.create(COMMA289);
                                                      this.adaptor.addChild(root_0, COMMA289_tree);
                                                   }

                                                   DOUBLESTAR290 = (Token)this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_arglist7705);
                                                   if (this.state.failed) {
                                                      return retval;
                                                   }

                                                   if (this.state.backtracking == 0) {
                                                      DOUBLESTAR290_tree = (PythonTree)this.adaptor.create(DOUBLESTAR290);
                                                      this.adaptor.addChild(root_0, DOUBLESTAR290_tree);
                                                   }

                                                   this.pushFollow(FOLLOW_test_in_arglist7709);
                                                   k = this.test(expr_contextType.Load);
                                                   --this.state._fsp;
                                                   if (this.state.failed) {
                                                      return retval;
                                                   }

                                                   if (this.state.backtracking == 0) {
                                                      this.adaptor.addChild(root_0, k.getTree());
                                                   }
                                                default:
                                                   break label928;
                                             }
                                       }
                                    }
                                 case 2:
                                    DOUBLESTAR291 = (Token)this.match(this.input, 49, FOLLOW_DOUBLESTAR_in_arglist7730);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       DOUBLESTAR291_tree = (PythonTree)this.adaptor.create(DOUBLESTAR291);
                                       this.adaptor.addChild(root_0, DOUBLESTAR291_tree);
                                    }

                                    this.pushFollow(FOLLOW_test_in_arglist7734);
                                    k = this.test(expr_contextType.Load);
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       this.adaptor.addChild(root_0, k.getTree());
                                    }
                              }
                           default:
                              if (this.state.backtracking == 0) {
                                 if (arguments.size() > 1 && gens.size() > 0) {
                                    this.actions.errorGenExpNotSoleArg(new PythonTree(retval.start));
                                 }

                                 retval.args = arguments;
                                 retval.keywords = kws;
                                 retval.starargs = this.actions.castExpr(s != null ? s.tree : null);
                                 retval.kwargs = this.actions.castExpr(k != null ? k.tree : null);
                              }
                              break label962;
                        }
                  }
               }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var48) {
            this.reportError(var48);
            this.errorHandler.recover(this, this.input, var48);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var48);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final argument_return argument(List arguments, List kws, List gens, boolean first, boolean afterStar) throws RecognitionException {
      argument_return retval = new argument_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token ASSIGN298 = null;
      test_return t1 = null;
      test_return t2 = null;
      comp_for_return comp_for299 = null;
      PythonTree ASSIGN298_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_test_in_argument7865);
            t1 = this.test(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, t1.getTree());
            }

            int alt156 = true;
            byte alt156;
            switch (this.input.LA(1)) {
               case 25:
                  alt156 = 2;
                  break;
               case 44:
               case 47:
                  alt156 = 3;
                  break;
               case 46:
                  alt156 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 156, 0, this.input);
                  throw nvae;
            }

            switch (alt156) {
               case 1:
                  ASSIGN298 = (Token)this.match(this.input, 46, FOLLOW_ASSIGN_in_argument7878);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ASSIGN298_tree = (PythonTree)this.adaptor.create(ASSIGN298);
                     this.adaptor.addChild(root_0, ASSIGN298_tree);
                  }

                  this.pushFollow(FOLLOW_test_in_argument7882);
                  t2 = this.test(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t2.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     expr newkey = this.actions.castExpr(t1 != null ? t1.tree : null);
                     Iterator var15 = kws.iterator();

                     while(var15.hasNext()) {
                        Object o = var15.next();
                        List list = (List)o;
                        Object oldkey = list.get(0);
                        if (oldkey instanceof Name && newkey instanceof Name && ((Name)oldkey).getId().equals(((Name)newkey).getId())) {
                           this.errorHandler.error("keyword arguments repeated", t1 != null ? t1.tree : null);
                        }
                     }

                     List exprs = new ArrayList();
                     exprs.add(newkey);
                     exprs.add(this.actions.castExpr(t2 != null ? t2.tree : null));
                     kws.add(exprs);
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_comp_for_in_argument7908);
                  comp_for299 = this.comp_for(gens);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, comp_for299.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     if (!first) {
                        this.actions.errorGenExpNotSoleArg(comp_for299 != null ? comp_for299.tree : null);
                     }

                     retval.genarg = true;
                     Collections.reverse(gens);
                     arguments.add(new GeneratorExp(t1 != null ? t1.start : null, this.actions.castExpr(t1 != null ? t1.tree : null), gens));
                  }
                  break;
               case 3:
                  if (this.state.backtracking == 0) {
                     if (kws.size() > 0) {
                        this.errorHandler.error("non-keyword arg after keyword arg", t1 != null ? t1.tree : null);
                     } else if (afterStar) {
                        this.errorHandler.error("only named arguments may follow *expression", t1 != null ? t1.tree : null);
                     }

                     arguments.add(t1 != null ? t1.tree : null);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var22) {
            this.reportError(var22);
            this.errorHandler.recover(this, this.input, var22);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var22);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final list_iter_return list_iter(List gens, List ifs) throws RecognitionException {
      list_iter_return retval = new list_iter_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      list_for_return list_for300 = null;
      list_if_return list_if301 = null;

      try {
         try {
            int alt157 = true;
            int LA157_0 = this.input.LA(1);
            byte alt157;
            if (LA157_0 == 25) {
               alt157 = 1;
            } else {
               if (LA157_0 != 27) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 157, 0, this.input);
                  throw nvae;
               }

               alt157 = 2;
            }

            switch (alt157) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_list_for_in_list_iter7973);
                  list_for300 = this.list_for(gens);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, list_for300.getTree());
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_list_if_in_list_iter7982);
                  list_if301 = this.list_if(gens, ifs);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, list_if301.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.errorHandler.recover(this, this.input, var13);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final list_for_return list_for(List gens) throws RecognitionException {
      list_for_return retval = new list_for_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token FOR302 = null;
      Token IN304 = null;
      exprlist_return exprlist303 = null;
      testlist_return testlist305 = null;
      list_iter_return list_iter306 = null;
      PythonTree FOR302_tree = null;
      PythonTree IN304_tree = null;
      List ifs = new ArrayList();

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            FOR302 = (Token)this.match(this.input, 25, FOLLOW_FOR_in_list_for8008);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               FOR302_tree = (PythonTree)this.adaptor.create(FOR302);
               this.adaptor.addChild(root_0, FOR302_tree);
            }

            this.pushFollow(FOLLOW_exprlist_in_list_for8010);
            exprlist303 = this.exprlist(expr_contextType.Store);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, exprlist303.getTree());
            }

            IN304 = (Token)this.match(this.input, 29, FOLLOW_IN_in_list_for8013);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               IN304_tree = (PythonTree)this.adaptor.create(IN304);
               this.adaptor.addChild(root_0, IN304_tree);
            }

            this.pushFollow(FOLLOW_testlist_in_list_for8015);
            testlist305 = this.testlist(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, testlist305.getTree());
            }

            int alt158 = 2;
            int LA158_0 = this.input.LA(1);
            if (LA158_0 == 25 || LA158_0 == 27) {
               alt158 = 1;
            }

            switch (alt158) {
               case 1:
                  this.pushFollow(FOLLOW_list_iter_in_list_for8019);
                  list_iter306 = this.list_iter(gens, ifs);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, list_iter306.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     Collections.reverse(ifs);
                     gens.add(new comprehension(FOR302, exprlist303 != null ? exprlist303.etype : null, this.actions.castExpr(testlist305 != null ? testlist305.tree : null), ifs));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.errorHandler.recover(this, this.input, var18);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final list_if_return list_if(List gens, List ifs) throws RecognitionException {
      list_if_return retval = new list_if_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token IF307 = null;
      test_return test308 = null;
      list_iter_return list_iter309 = null;
      PythonTree IF307_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            IF307 = (Token)this.match(this.input, 27, FOLLOW_IF_in_list_if8049);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               IF307_tree = (PythonTree)this.adaptor.create(IF307);
               this.adaptor.addChild(root_0, IF307_tree);
            }

            this.pushFollow(FOLLOW_test_in_list_if8051);
            test308 = this.test(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, test308.getTree());
            }

            int alt159 = 2;
            int LA159_0 = this.input.LA(1);
            if (LA159_0 == 25 || LA159_0 == 27) {
               alt159 = 1;
            }

            switch (alt159) {
               case 1:
                  this.pushFollow(FOLLOW_list_iter_in_list_if8055);
                  list_iter309 = this.list_iter(gens, ifs);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, list_iter309.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     ifs.add(this.actions.castExpr(test308 != null ? test308.tree : null));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.errorHandler.recover(this, this.input, var15);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final comp_iter_return comp_iter(List gens, List ifs) throws RecognitionException {
      comp_iter_return retval = new comp_iter_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      comp_for_return comp_for310 = null;
      comp_if_return comp_if311 = null;

      try {
         try {
            int alt160 = true;
            int LA160_0 = this.input.LA(1);
            byte alt160;
            if (LA160_0 == 25) {
               alt160 = 1;
            } else {
               if (LA160_0 != 27) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 160, 0, this.input);
                  throw nvae;
               }

               alt160 = 2;
            }

            switch (alt160) {
               case 1:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_comp_for_in_comp_iter8086);
                  comp_for310 = this.comp_for(gens);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, comp_for310.getTree());
                  }
                  break;
               case 2:
                  root_0 = (PythonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_comp_if_in_comp_iter8095);
                  comp_if311 = this.comp_if(gens, ifs);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, comp_if311.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.errorHandler.recover(this, this.input, var13);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final comp_for_return comp_for(List gens) throws RecognitionException {
      comp_for_return retval = new comp_for_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token FOR312 = null;
      Token IN314 = null;
      exprlist_return exprlist313 = null;
      or_test_return or_test315 = null;
      comp_iter_return comp_iter316 = null;
      PythonTree FOR312_tree = null;
      PythonTree IN314_tree = null;
      List ifs = new ArrayList();

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            FOR312 = (Token)this.match(this.input, 25, FOLLOW_FOR_in_comp_for8121);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               FOR312_tree = (PythonTree)this.adaptor.create(FOR312);
               this.adaptor.addChild(root_0, FOR312_tree);
            }

            this.pushFollow(FOLLOW_exprlist_in_comp_for8123);
            exprlist313 = this.exprlist(expr_contextType.Store);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, exprlist313.getTree());
            }

            IN314 = (Token)this.match(this.input, 29, FOLLOW_IN_in_comp_for8126);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               IN314_tree = (PythonTree)this.adaptor.create(IN314);
               this.adaptor.addChild(root_0, IN314_tree);
            }

            this.pushFollow(FOLLOW_or_test_in_comp_for8128);
            or_test315 = this.or_test(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, or_test315.getTree());
            }

            int alt161 = 2;
            int LA161_0 = this.input.LA(1);
            if (LA161_0 == 25 || LA161_0 == 27) {
               alt161 = 1;
            }

            switch (alt161) {
               case 1:
                  this.pushFollow(FOLLOW_comp_iter_in_comp_for8131);
                  comp_iter316 = this.comp_iter(gens, ifs);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, comp_iter316.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     Collections.reverse(ifs);
                     gens.add(new comprehension(FOR312, exprlist313 != null ? exprlist313.etype : null, this.actions.castExpr(or_test315 != null ? or_test315.tree : null), ifs));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.errorHandler.recover(this, this.input, var18);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final comp_if_return comp_if(List gens, List ifs) throws RecognitionException {
      comp_if_return retval = new comp_if_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token IF317 = null;
      test_return test318 = null;
      comp_iter_return comp_iter319 = null;
      PythonTree IF317_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            IF317 = (Token)this.match(this.input, 27, FOLLOW_IF_in_comp_if8160);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               IF317_tree = (PythonTree)this.adaptor.create(IF317);
               this.adaptor.addChild(root_0, IF317_tree);
            }

            this.pushFollow(FOLLOW_test_in_comp_if8162);
            test318 = this.test(expr_contextType.Load);
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, test318.getTree());
            }

            int alt162 = 2;
            int LA162_0 = this.input.LA(1);
            if (LA162_0 == 25 || LA162_0 == 27) {
               alt162 = 1;
            }

            switch (alt162) {
               case 1:
                  this.pushFollow(FOLLOW_comp_iter_in_comp_if8165);
                  comp_iter319 = this.comp_iter(gens, ifs);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, comp_iter319.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     ifs.add(this.actions.castExpr(test318 != null ? test318.tree : null));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.errorHandler.recover(this, this.input, var15);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final yield_expr_return yield_expr() throws RecognitionException {
      yield_expr_return retval = new yield_expr_return();
      retval.start = this.input.LT(1);
      PythonTree root_0 = null;
      Token YIELD320 = null;
      testlist_return testlist321 = null;
      PythonTree YIELD320_tree = null;

      try {
         try {
            root_0 = (PythonTree)this.adaptor.nil();
            YIELD320 = (Token)this.match(this.input, 41, FOLLOW_YIELD_in_yield_expr8206);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               YIELD320_tree = (PythonTree)this.adaptor.create(YIELD320);
               this.adaptor.addChild(root_0, YIELD320_tree);
            }

            int alt163 = 2;
            int LA163_0 = this.input.LA(1);
            if (LA163_0 != 9 && LA163_0 != 32 && LA163_0 != 43 && (LA163_0 < 75 || LA163_0 > 76) && (LA163_0 < 80 || LA163_0 > 81) && LA163_0 != 83 && LA163_0 != 85) {
               if (LA163_0 == 11 && this.printFunction) {
                  alt163 = 1;
               } else if (LA163_0 == 31 || LA163_0 >= 86 && LA163_0 <= 90) {
                  alt163 = 1;
               }
            } else {
               alt163 = 1;
            }

            switch (alt163) {
               case 1:
                  this.pushFollow(FOLLOW_testlist_in_yield_expr8208);
                  testlist321 = this.testlist(expr_contextType.Load);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, testlist321.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     retval.etype = new Yield(YIELD320, this.actions.castExpr(testlist321 != null ? testlist321.tree : null));
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (PythonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = retval.etype;
                  }
            }
         } catch (RecognitionException var12) {
            this.reportError(var12);
            this.errorHandler.recover(this, this.input, var12);
            retval.tree = (PythonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var12);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void synpred1_Python_fragment() throws RecognitionException {
      this.match(this.input, 43, FOLLOW_LPAREN_in_synpred1_Python1296);
      if (!this.state.failed) {
         this.pushFollow(FOLLOW_fpdef_in_synpred1_Python1298);
         this.fpdef((expr_contextType)null);
         --this.state._fsp;
         if (!this.state.failed) {
            this.match(this.input, 47, FOLLOW_COMMA_in_synpred1_Python1301);
            if (!this.state.failed) {
               ;
            }
         }
      }
   }

   public final void synpred2_Python_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_testlist_in_synpred2_Python1683);
      this.testlist((expr_contextType)null);
      --this.state._fsp;
      if (!this.state.failed) {
         this.pushFollow(FOLLOW_augassign_in_synpred2_Python1686);
         this.augassign();
         --this.state._fsp;
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred3_Python_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_testlist_in_synpred3_Python1802);
      this.testlist((expr_contextType)null);
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 46, FOLLOW_ASSIGN_in_synpred3_Python1805);
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred4_Python_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_test_in_synpred4_Python2317);
      this.test((expr_contextType)null);
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 47, FOLLOW_COMMA_in_synpred4_Python2320);
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred5_Python_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_test_in_synpred5_Python2416);
      this.test((expr_contextType)null);
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 47, FOLLOW_COMMA_in_synpred5_Python2419);
         if (!this.state.failed) {
            this.pushFollow(FOLLOW_test_in_synpred5_Python2421);
            this.test((expr_contextType)null);
            --this.state._fsp;
            if (!this.state.failed) {
               ;
            }
         }
      }
   }

   public final void synpred6_Python_fragment() throws RecognitionException {
      int alt164 = 2;
      int LA164_0 = this.input.LA(1);
      if (LA164_0 == 42) {
         alt164 = 1;
      }

      switch (alt164) {
         case 1:
            this.pushFollow(FOLLOW_decorators_in_synpred6_Python3510);
            this.decorators();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }
         default:
            this.match(this.input, 18, FOLLOW_DEF_in_synpred6_Python3513);
            if (!this.state.failed) {
               ;
            }
      }
   }

   public final void synpred7_Python_fragment() throws RecognitionException {
      this.match(this.input, 27, FOLLOW_IF_in_synpred7_Python4270);
      if (!this.state.failed) {
         this.pushFollow(FOLLOW_or_test_in_synpred7_Python4272);
         this.or_test((expr_contextType)null);
         --this.state._fsp;
         if (!this.state.failed) {
            this.match(this.input, 34, FOLLOW_ORELSE_in_synpred7_Python4275);
            if (!this.state.failed) {
               ;
            }
         }
      }
   }

   public final void synpred8_Python_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_test_in_synpred8_Python6864);
      this.test((expr_contextType)null);
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 45, FOLLOW_COLON_in_synpred8_Python6867);
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred9_Python_fragment() throws RecognitionException {
      this.match(this.input, 45, FOLLOW_COLON_in_synpred9_Python6915);
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred10_Python_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_expr_in_synpred10_Python7060);
      this.expr((expr_contextType)null);
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 47, FOLLOW_COMMA_in_synpred10_Python7063);
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final void synpred11_Python_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_test_in_synpred11_Python7211);
      this.test((expr_contextType)null);
      --this.state._fsp;
      if (!this.state.failed) {
         this.match(this.input, 47, FOLLOW_COMMA_in_synpred11_Python7214);
         if (!this.state.failed) {
            ;
         }
      }
   }

   public final boolean synpred1_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred1_Python_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred3_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred3_Python_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred10_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred10_Python_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred11_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred11_Python_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred2_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred2_Python_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred4_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred4_Python_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred5_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred5_Python_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred6_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred6_Python_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred7_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred7_Python_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred8_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred8_Python_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred9_Python() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred9_Python_fragment();
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
      int numStates = DFA30_transitionS.length;
      DFA30_transition = new short[numStates][];

      int i;
      for(i = 0; i < numStates; ++i) {
         DFA30_transition[i] = DFA.unpackEncodedString(DFA30_transitionS[i]);
      }

      DFA35_transitionS = new String[]{"\u0001\t\u0001\uffff\u0001\n\u0013\uffff\u0001\u0010\u0001\u0001\n\uffff\u0001\u0005\u001f\uffff\u0001\u0002\u0001\u0003\u0003\uffff\u0001\u0004\u0001\u0006\u0001\uffff\u0001\u0007\u0001\uffff\u0001\b\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", "", ""};
      DFA35_eot = DFA.unpackEncodedString("\u0014\uffff");
      DFA35_eof = DFA.unpackEncodedString("\u0014\uffff");
      DFA35_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0010\u0000\u0003\uffff");
      DFA35_max = DFA.unpackEncodedStringToUnsignedChars("\u0001Z\u0010\u0000\u0003\uffff");
      DFA35_accept = DFA.unpackEncodedString("\u0011\uffff\u0001\u0001\u0001\u0002\u0001\u0003");
      DFA35_special = DFA.unpackEncodedString("\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0003\uffff}>");
      numStates = DFA35_transitionS.length;
      DFA35_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA35_transition[i] = DFA.unpackEncodedString(DFA35_transitionS[i]);
      }

      DFA31_transitionS = new String[]{"\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "\u0001\r\u0001\uffff\u0001\r\u0013\uffff\u0002\r\b\uffff\u0001\u000e\u0001\uffff\u0001\r\u001f\uffff\u0002\r\u0003\uffff\u0002\r\u0001\uffff\u0001\r\u0001\uffff\u0006\r", "", ""};
      DFA31_eot = DFA.unpackEncodedString("\u000f\uffff");
      DFA31_eof = DFA.unpackEncodedString("\u000f\uffff");
      DFA31_min = DFA.unpackEncodedStringToUnsignedChars("\u00013\f\t\u0002\uffff");
      DFA31_max = DFA.unpackEncodedStringToUnsignedChars("\u0001>\fZ\u0002\uffff");
      DFA31_accept = DFA.unpackEncodedString("\r\uffff\u0001\u0002\u0001\u0001");
      DFA31_special = DFA.unpackEncodedString("\u000f\uffff}>");
      numStates = DFA31_transitionS.length;
      DFA31_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA31_transition[i] = DFA.unpackEncodedString(DFA31_transitionS[i]);
      }

      DFA40_transitionS = new String[]{"\u0001\t\u0001\uffff\u0001\n\u0013\uffff\u0001\u0010\u0001\u0001\n\uffff\u0001\u0005\u001f\uffff\u0001\u0002\u0001\u0003\u0003\uffff\u0001\u0004\u0001\u0006\u0001\uffff\u0001\u0007\u0001\uffff\u0001\b\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", ""};
      DFA40_eot = DFA.unpackEncodedString("\u0013\uffff");
      DFA40_eof = DFA.unpackEncodedString("\u0013\uffff");
      DFA40_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0010\u0000\u0002\uffff");
      DFA40_max = DFA.unpackEncodedStringToUnsignedChars("\u0001Z\u0010\u0000\u0002\uffff");
      DFA40_accept = DFA.unpackEncodedString("\u0011\uffff\u0001\u0001\u0001\u0002");
      DFA40_special = DFA.unpackEncodedString("\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0002\uffff}>");
      numStates = DFA40_transitionS.length;
      DFA40_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA40_transition[i] = DFA.unpackEncodedString(DFA40_transitionS[i]);
      }

      DFA38_transitionS = new String[]{"\u0001\u0002'\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "\u0001\u0002\u0001\uffff\u0001\u0006\u0001\uffff\u0001\u0006\u0013\uffff\u0002\u0006\n\uffff\u0001\u0006\u0006\uffff\u0001\u0002\u0018\uffff\u0002\u0006\u0003\uffff\u0002\u0006\u0001\uffff\u0001\u0006\u0001\uffff\u0006\u0006", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA38_eot = DFA.unpackEncodedString("\u0016\uffff");
      DFA38_eof = DFA.unpackEncodedString("\u0016\uffff");
      DFA38_min = DFA.unpackEncodedStringToUnsignedChars("\u0002\u0007\u0014\uffff");
      DFA38_max = DFA.unpackEncodedStringToUnsignedChars("\u00012\u0001Z\u0014\uffff");
      DFA38_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0003\uffff\u0001\u0001\u000f\uffff");
      DFA38_special = DFA.unpackEncodedString("\u0016\uffff}>");
      numStates = DFA38_transitionS.length;
      DFA38_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA38_transition[i] = DFA.unpackEncodedString(DFA38_transitionS[i]);
      }

      DFA43_transitionS = new String[]{"\u0001\t\u0001\uffff\u0001\n\u0013\uffff\u0001\u0010\u0001\u0001\n\uffff\u0001\u0005\u001f\uffff\u0001\u0002\u0001\u0003\u0003\uffff\u0001\u0004\u0001\u0006\u0001\uffff\u0001\u0007\u0001\uffff\u0001\b\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", ""};
      DFA43_eot = DFA.unpackEncodedString("\u0013\uffff");
      DFA43_eof = DFA.unpackEncodedString("\u0013\uffff");
      DFA43_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0010\u0000\u0002\uffff");
      DFA43_max = DFA.unpackEncodedStringToUnsignedChars("\u0001Z\u0010\u0000\u0002\uffff");
      DFA43_accept = DFA.unpackEncodedString("\u0011\uffff\u0001\u0001\u0001\u0002");
      DFA43_special = DFA.unpackEncodedString("\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0002\uffff}>");
      numStates = DFA43_transitionS.length;
      DFA43_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA43_transition[i] = DFA.unpackEncodedString(DFA43_transitionS[i]);
      }

      DFA41_transitionS = new String[]{"\u0001\u0002'\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "\u0001\u0002\u0001\uffff\u0001\u0006\u0001\uffff\u0001\u0006\u0013\uffff\u0002\u0006\n\uffff\u0001\u0006\u0006\uffff\u0001\u0002\u0018\uffff\u0002\u0006\u0003\uffff\u0002\u0006\u0001\uffff\u0001\u0006\u0001\uffff\u0006\u0006", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA41_eot = DFA.unpackEncodedString("\u0016\uffff");
      DFA41_eof = DFA.unpackEncodedString("\u0016\uffff");
      DFA41_min = DFA.unpackEncodedStringToUnsignedChars("\u0002\u0007\u0014\uffff");
      DFA41_max = DFA.unpackEncodedStringToUnsignedChars("\u00012\u0001Z\u0014\uffff");
      DFA41_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0003\uffff\u0001\u0001\u000f\uffff");
      DFA41_special = DFA.unpackEncodedString("\u0016\uffff}>");
      numStates = DFA41_transitionS.length;
      DFA41_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA41_transition[i] = DFA.unpackEncodedString(DFA41_transitionS[i]);
      }

      DFA52_transitionS = new String[]{"\u0001\u0002\u0001\u0001", "\u0001\u0002\u0001\u0001\u0011\uffff\u0001\u0003", "", ""};
      DFA52_eot = DFA.unpackEncodedString("\u0004\uffff");
      DFA52_eof = DFA.unpackEncodedString("\u0004\uffff");
      DFA52_min = DFA.unpackEncodedStringToUnsignedChars("\u0002\t\u0002\uffff");
      DFA52_max = DFA.unpackEncodedStringToUnsignedChars("\u0001\n\u0001\u001c\u0002\uffff");
      DFA52_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0001\u0001\u0002");
      DFA52_special = DFA.unpackEncodedString("\u0004\uffff}>");
      numStates = DFA52_transitionS.length;
      DFA52_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA52_transition[i] = DFA.unpackEncodedString(DFA52_transitionS[i]);
      }

      DFA80_transitionS = new String[]{"\u0001\u0002\u0005\uffff\u0001\u0002\u000b\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u0010\uffff\u0004\u0002\u0002\uffff\r\u0002\u0013\uffff\u0001\u0002\u0001\uffff\u0002\u0002", "\u0001\uffff", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA80_eot = DFA.unpackEncodedString("\u001b\uffff");
      DFA80_eof = DFA.unpackEncodedString("\u0001\u0002\u001a\uffff");
      DFA80_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0007\u0001\u0000\u0019\uffff");
      DFA80_max = DFA.unpackEncodedStringToUnsignedChars("\u0001U\u0001\u0000\u0019\uffff");
      DFA80_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0017\uffff\u0001\u0001");
      DFA80_special = DFA.unpackEncodedString("\u0001\uffff\u0001\u0000\u0019\uffff}>");
      numStates = DFA80_transitionS.length;
      DFA80_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA80_transition[i] = DFA.unpackEncodedString(DFA80_transitionS[i]);
      }

      DFA89_transitionS = new String[]{"\u0001\b\u0001\n\u0001\uffff\u0001\t\u001f\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007", "", "", "", "", "", "", "", "", "", "\u0001\f\u0001\uffff\u0001\f\u0014\uffff\u0001\u000b\n\uffff\u0001\f\u001f\uffff\u0002\f\u0003\uffff\u0002\f\u0001\uffff\u0001\f\u0001\uffff\u0006\f", "", ""};
      DFA89_eot = DFA.unpackEncodedString("\r\uffff");
      DFA89_eof = DFA.unpackEncodedString("\r\uffff");
      DFA89_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u001d\t\uffff\u0001\t\u0002\uffff");
      DFA89_max = DFA.unpackEncodedStringToUnsignedChars("\u0001F\t\uffff\u0001Z\u0002\uffff");
      DFA89_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\uffff\u0001\u000b\u0001\n");
      DFA89_special = DFA.unpackEncodedString("\r\uffff}>");
      numStates = DFA89_transitionS.length;
      DFA89_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA89_transition[i] = DFA.unpackEncodedString(DFA89_transitionS[i]);
      }

      DFA112_transitionS = new String[]{"\u0001\u0005\u0001\uffff\u0001\u0006\u001f\uffff\u0001\u0001%\uffff\u0001\u0002\u0001\uffff\u0001\u0003\u0001\uffff\u0001\u0004\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b", "", "", "", "", "", "", "", "", "", "", ""};
      DFA112_eot = DFA.unpackEncodedString("\f\uffff");
      DFA112_eof = DFA.unpackEncodedString("\f\uffff");
      DFA112_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u000b\uffff");
      DFA112_max = DFA.unpackEncodedStringToUnsignedChars("\u0001Z\u000b\uffff");
      DFA112_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0002\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n");
      DFA112_special = DFA.unpackEncodedString("\u0001\u0000\u000b\uffff}>");
      numStates = DFA112_transitionS.length;
      DFA112_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA112_transition[i] = DFA.unpackEncodedString(DFA112_transitionS[i]);
      }

      DFA116_transitionS = new String[]{"\u0001\u0002\u0002\uffff\u0001\u0001", "\u0001\u0004\u0001\uffff\u0001\u0004\u0013\uffff\u0002\u0004\n\uffff\u0001\u0004\u0001\u0002\u001e\uffff\u0002\u0004\u0003\uffff\u0002\u0004\u0001\uffff\u0001\u0004\u0001\uffff\u0006\u0004", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA116_eot = DFA.unpackEncodedString("\u0014\uffff");
      DFA116_eof = DFA.unpackEncodedString("\u0014\uffff");
      DFA116_min = DFA.unpackEncodedStringToUnsignedChars("\u0001,\u0001\t\u0012\uffff");
      DFA116_max = DFA.unpackEncodedStringToUnsignedChars("\u0001/\u0001Z\u0012\uffff");
      DFA116_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u000f\uffff");
      DFA116_special = DFA.unpackEncodedString("\u0014\uffff}>");
      numStates = DFA116_transitionS.length;
      DFA116_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA116_transition[i] = DFA.unpackEncodedString(DFA116_transitionS[i]);
      }

      DFA129_transitionS = new String[]{"\u0001\n\u0001\u0001\u0001\u000b\u0013\uffff\u0001\u0011\u0001\u0002\n\uffff\u0001\u0006\u0001\uffff\u0001\u0012\u001d\uffff\u0001\u0003\u0001\u0004\u0003\uffff\u0001\u0005\u0001\u0007\u0001\uffff\u0001\b\u0001\uffff\u0001\t\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010", "", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", "", ""};
      DFA129_eot = DFA.unpackEncodedString("\u0015\uffff");
      DFA129_eof = DFA.unpackEncodedString("\u0015\uffff");
      DFA129_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0001\uffff\u0010\u0000\u0003\uffff");
      DFA129_max = DFA.unpackEncodedStringToUnsignedChars("\u0001Z\u0001\uffff\u0010\u0000\u0003\uffff");
      DFA129_accept = DFA.unpackEncodedString("\u0001\uffff\u0001\u0001\u0010\uffff\u0001\u0003\u0001\u0002\u0001\u0004");
      DFA129_special = DFA.unpackEncodedString("\u0001\u0000\u0001\uffff\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0003\uffff}>");
      numStates = DFA129_transitionS.length;
      DFA129_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA129_transition[i] = DFA.unpackEncodedString(DFA129_transitionS[i]);
      }

      DFA133_transitionS = new String[]{"\u0001\b\u0001\uffff\u0001\t\u001f\uffff\u0001\u0004\u001f\uffff\u0001\u0001\u0001\u0002\u0003\uffff\u0001\u0003\u0001\u0005\u0001\uffff\u0001\u0006\u0001\uffff\u0001\u0007\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", ""};
      DFA133_eot = DFA.unpackEncodedString("\u0011\uffff");
      DFA133_eof = DFA.unpackEncodedString("\u0011\uffff");
      DFA133_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u000e\u0000\u0002\uffff");
      DFA133_max = DFA.unpackEncodedStringToUnsignedChars("\u0001Z\u000e\u0000\u0002\uffff");
      DFA133_accept = DFA.unpackEncodedString("\u000f\uffff\u0001\u0001\u0001\u0002");
      DFA133_special = DFA.unpackEncodedString("\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0002\uffff}>");
      numStates = DFA133_transitionS.length;
      DFA133_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA133_transition[i] = DFA.unpackEncodedString(DFA133_transitionS[i]);
      }

      DFA131_transitionS = new String[]{"\u0001\u0002\u0011\uffff\u0001\u0001", "\u0001\u0004\u0001\uffff\u0001\u0004\u0011\uffff\u0001\u0002\r\uffff\u0001\u0004\u001f\uffff\u0002\u0004\u0003\uffff\u0002\u0004\u0001\uffff\u0001\u0004\u0001\uffff\u0006\u0004", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA131_eot = DFA.unpackEncodedString("\u0012\uffff");
      DFA131_eof = DFA.unpackEncodedString("\u0012\uffff");
      DFA131_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u001d\u0001\t\u0010\uffff");
      DFA131_max = DFA.unpackEncodedStringToUnsignedChars("\u0001/\u0001Z\u0010\uffff");
      DFA131_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\r\uffff");
      DFA131_special = DFA.unpackEncodedString("\u0012\uffff}>");
      numStates = DFA131_transitionS.length;
      DFA131_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA131_transition[i] = DFA.unpackEncodedString(DFA131_transitionS[i]);
      }

      DFA134_transitionS = new String[]{"\u0001\u0002'\uffff\u0001\u0001\u0002\uffff\u0001\u0002", "\u0001\u0002\u0001\uffff\u0001\u0006\u0001\uffff\u0001\u0006\u001f\uffff\u0001\u0006\u0006\uffff\u0001\u0002\u0018\uffff\u0002\u0006\u0003\uffff\u0002\u0006\u0001\uffff\u0001\u0006\u0001\uffff\u0006\u0006", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA134_eot = DFA.unpackEncodedString("\u0014\uffff");
      DFA134_eof = DFA.unpackEncodedString("\u0014\uffff");
      DFA134_min = DFA.unpackEncodedStringToUnsignedChars("\u0002\u0007\u0012\uffff");
      DFA134_max = DFA.unpackEncodedStringToUnsignedChars("\u00012\u0001Z\u0012\uffff");
      DFA134_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0003\uffff\u0001\u0001\r\uffff");
      DFA134_special = DFA.unpackEncodedString("\u0014\uffff}>");
      numStates = DFA134_transitionS.length;
      DFA134_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA134_transition[i] = DFA.unpackEncodedString(DFA134_transitionS[i]);
      }

      DFA138_transitionS = new String[]{"\u0001\t\u0001\uffff\u0001\n\u0013\uffff\u0001\u0010\u0001\u0001\n\uffff\u0001\u0005\u001f\uffff\u0001\u0002\u0001\u0003\u0003\uffff\u0001\u0004\u0001\u0006\u0001\uffff\u0001\u0007\u0001\uffff\u0001\b\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "\u0001\uffff", "", ""};
      DFA138_eot = DFA.unpackEncodedString("\u0013\uffff");
      DFA138_eof = DFA.unpackEncodedString("\u0013\uffff");
      DFA138_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\t\u0010\u0000\u0002\uffff");
      DFA138_max = DFA.unpackEncodedStringToUnsignedChars("\u0001Z\u0010\u0000\u0002\uffff");
      DFA138_accept = DFA.unpackEncodedString("\u0011\uffff\u0001\u0001\u0001\u0002");
      DFA138_special = DFA.unpackEncodedString("\u0001\u0000\u0001\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0001\u000e\u0001\u000f\u0001\u0010\u0002\uffff}>");
      numStates = DFA138_transitionS.length;
      DFA138_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA138_transition[i] = DFA.unpackEncodedString(DFA138_transitionS[i]);
      }

      DFA136_transitionS = new String[]{"\u0001\u0002\u0011\uffff\u0001\u0002\u0001\uffff\u0001\u0002\u0010\uffff\u0003\u0002\u0001\u0001\u0002\uffff\r\u0002\u0013\uffff\u0001\u0002\u0002\uffff\u0001\u0002", "\u0001\u0002\u0001\uffff\u0001/\u0001\uffff\u0001/\r\uffff\u0001\u0002\u0001\uffff\u0001\u0002\u0003\uffff\u0002/\n\uffff\u0001/\u0004\u0002\u0002\uffff\r\u0002\f\uffff\u0002/\u0003\uffff\u0002/\u0001\u0002\u0001/\u0001\uffff\u0001)\u0005/", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA136_eot = DFA.unpackEncodedString(">\uffff");
      DFA136_eof = DFA.unpackEncodedString("\u0002\u0002<\uffff");
      DFA136_min = DFA.unpackEncodedStringToUnsignedChars("\u0002\u0007<\uffff");
      DFA136_max = DFA.unpackEncodedStringToUnsignedChars("\u0001U\u0001Z<\uffff");
      DFA136_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002&\uffff\u0001\u0001\u0005\uffff\u0001\u0001\u000e\uffff");
      DFA136_special = DFA.unpackEncodedString(">\uffff}>");
      numStates = DFA136_transitionS.length;
      DFA136_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA136_transition[i] = DFA.unpackEncodedString(DFA136_transitionS[i]);
      }

      DFA139_transitionS = new String[]{"\u0001\u0001$\uffff\u0001\u0002", "\u0001\u0004\u0001\uffff\u0001\u0004\u0013\uffff\u0002\u0004\n\uffff\u0001\u0004\u001f\uffff\u0002\u0004\u0003\uffff\u0002\u0004\u0001\uffff\u0001\u0004\u0001\u0002\u0006\u0004", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
      DFA139_eot = DFA.unpackEncodedString("\u0014\uffff");
      DFA139_eof = DFA.unpackEncodedString("\u0014\uffff");
      DFA139_min = DFA.unpackEncodedStringToUnsignedChars("\u0001/\u0001\t\u0012\uffff");
      DFA139_max = DFA.unpackEncodedStringToUnsignedChars("\u0001T\u0001Z\u0012\uffff");
      DFA139_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0002\u0001\uffff\u0001\u0001\u000f\uffff");
      DFA139_special = DFA.unpackEncodedString("\u0014\uffff}>");
      numStates = DFA139_transitionS.length;
      DFA139_transition = new short[numStates][];

      for(i = 0; i < numStates; ++i) {
         DFA139_transition[i] = DFA.unpackEncodedString(DFA139_transitionS[i]);
      }

      FOLLOW_NEWLINE_in_single_input118 = new BitSet(new long[]{128L});
      FOLLOW_EOF_in_single_input121 = new BitSet(new long[]{2L});
      FOLLOW_simple_stmt_in_single_input137 = new BitSet(new long[]{128L});
      FOLLOW_NEWLINE_in_single_input139 = new BitSet(new long[]{128L});
      FOLLOW_EOF_in_single_input142 = new BitSet(new long[]{2L});
      FOLLOW_compound_stmt_in_single_input158 = new BitSet(new long[]{128L});
      FOLLOW_NEWLINE_in_single_input160 = new BitSet(new long[]{128L});
      FOLLOW_EOF_in_single_input163 = new BitSet(new long[]{2L});
      FOLLOW_NEWLINE_in_file_input215 = new BitSet(new long[]{17564794079872L, 132847616L});
      FOLLOW_stmt_in_file_input225 = new BitSet(new long[]{17564794079872L, 132847616L});
      FOLLOW_EOF_in_file_input244 = new BitSet(new long[]{2L});
      FOLLOW_LEADING_WS_in_eval_input298 = new BitSet(new long[]{8802535475840L, 132847616L});
      FOLLOW_NEWLINE_in_eval_input302 = new BitSet(new long[]{8802535475840L, 132847616L});
      FOLLOW_testlist_in_eval_input306 = new BitSet(new long[]{128L});
      FOLLOW_NEWLINE_in_eval_input310 = new BitSet(new long[]{128L});
      FOLLOW_EOF_in_eval_input314 = new BitSet(new long[]{2L});
      FOLLOW_NAME_in_dotted_attr366 = new BitSet(new long[]{1026L});
      FOLLOW_DOT_in_dotted_attr377 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_dotted_attr381 = new BitSet(new long[]{1026L});
      FOLLOW_NAME_in_name_or_print446 = new BitSet(new long[]{2L});
      FOLLOW_PRINT_in_name_or_print460 = new BitSet(new long[]{2L});
      FOLLOW_set_in_attr0 = new BitSet(new long[]{2L});
      FOLLOW_AT_in_decorator762 = new BitSet(new long[]{512L});
      FOLLOW_dotted_attr_in_decorator764 = new BitSet(new long[]{8796093022336L});
      FOLLOW_LPAREN_in_decorator772 = new BitSet(new long[]{870819651652096L, 132847616L});
      FOLLOW_arglist_in_decorator782 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_decorator826 = new BitSet(new long[]{128L});
      FOLLOW_NEWLINE_in_decorator848 = new BitSet(new long[]{2L});
      FOLLOW_decorator_in_decorators876 = new BitSet(new long[]{4398046511106L});
      FOLLOW_decorators_in_funcdef914 = new BitSet(new long[]{262144L});
      FOLLOW_DEF_in_funcdef917 = new BitSet(new long[]{2560L});
      FOLLOW_name_or_print_in_funcdef919 = new BitSet(new long[]{8796093022208L});
      FOLLOW_parameters_in_funcdef921 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_funcdef923 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_funcdef925 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_parameters958 = new BitSet(new long[]{870813209199104L});
      FOLLOW_varargslist_in_parameters967 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_parameters1011 = new BitSet(new long[]{2L});
      FOLLOW_fpdef_in_defparameter1044 = new BitSet(new long[]{70368744177666L});
      FOLLOW_ASSIGN_in_defparameter1048 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_defparameter1050 = new BitSet(new long[]{2L});
      FOLLOW_defparameter_in_varargslist1096 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_varargslist1107 = new BitSet(new long[]{8796093022720L});
      FOLLOW_defparameter_in_varargslist1111 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_varargslist1123 = new BitSet(new long[]{844424930131970L});
      FOLLOW_STAR_in_varargslist1136 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist1140 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_varargslist1143 = new BitSet(new long[]{562949953421312L});
      FOLLOW_DOUBLESTAR_in_varargslist1145 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist1149 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESTAR_in_varargslist1165 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist1169 = new BitSet(new long[]{2L});
      FOLLOW_STAR_in_varargslist1207 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist1211 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_varargslist1214 = new BitSet(new long[]{562949953421312L});
      FOLLOW_DOUBLESTAR_in_varargslist1216 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist1220 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESTAR_in_varargslist1238 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_varargslist1242 = new BitSet(new long[]{2L});
      FOLLOW_NAME_in_fpdef1279 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_fpdef1306 = new BitSet(new long[]{8796093022720L});
      FOLLOW_fplist_in_fpdef1308 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_fpdef1310 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_fpdef1326 = new BitSet(new long[]{8796093022720L});
      FOLLOW_fplist_in_fpdef1329 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_fpdef1331 = new BitSet(new long[]{2L});
      FOLLOW_fpdef_in_fplist1360 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_fplist1377 = new BitSet(new long[]{8796093022720L});
      FOLLOW_fpdef_in_fplist1381 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_fplist1387 = new BitSet(new long[]{2L});
      FOLLOW_simple_stmt_in_stmt1423 = new BitSet(new long[]{2L});
      FOLLOW_compound_stmt_in_stmt1439 = new BitSet(new long[]{2L});
      FOLLOW_small_stmt_in_simple_stmt1475 = new BitSet(new long[]{1125899906842752L});
      FOLLOW_SEMI_in_simple_stmt1485 = new BitSet(new long[]{11242434120192L, 132847616L});
      FOLLOW_small_stmt_in_simple_stmt1489 = new BitSet(new long[]{1125899906842752L});
      FOLLOW_SEMI_in_simple_stmt1494 = new BitSet(new long[]{128L});
      FOLLOW_NEWLINE_in_simple_stmt1498 = new BitSet(new long[]{2L});
      FOLLOW_expr_stmt_in_small_stmt1521 = new BitSet(new long[]{2L});
      FOLLOW_del_stmt_in_small_stmt1536 = new BitSet(new long[]{2L});
      FOLLOW_pass_stmt_in_small_stmt1551 = new BitSet(new long[]{2L});
      FOLLOW_flow_stmt_in_small_stmt1566 = new BitSet(new long[]{2L});
      FOLLOW_import_stmt_in_small_stmt1581 = new BitSet(new long[]{2L});
      FOLLOW_global_stmt_in_small_stmt1596 = new BitSet(new long[]{2L});
      FOLLOW_exec_stmt_in_small_stmt1611 = new BitSet(new long[]{2L});
      FOLLOW_assert_stmt_in_small_stmt1626 = new BitSet(new long[]{2L});
      FOLLOW_print_stmt_in_small_stmt1645 = new BitSet(new long[]{2L});
      FOLLOW_testlist_in_expr_stmt1693 = new BitSet(new long[]{9221120237041090560L});
      FOLLOW_augassign_in_expr_stmt1709 = new BitSet(new long[]{2405181849600L});
      FOLLOW_yield_expr_in_expr_stmt1713 = new BitSet(new long[]{2L});
      FOLLOW_augassign_in_expr_stmt1753 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_testlist_in_expr_stmt1757 = new BitSet(new long[]{2L});
      FOLLOW_testlist_in_expr_stmt1812 = new BitSet(new long[]{70368744177666L});
      FOLLOW_ASSIGN_in_expr_stmt1839 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_testlist_in_expr_stmt1843 = new BitSet(new long[]{70368744177666L});
      FOLLOW_ASSIGN_in_expr_stmt1888 = new BitSet(new long[]{2405181849600L});
      FOLLOW_yield_expr_in_expr_stmt1892 = new BitSet(new long[]{70368744177666L});
      FOLLOW_testlist_in_expr_stmt1940 = new BitSet(new long[]{2L});
      FOLLOW_PLUSEQUAL_in_augassign1982 = new BitSet(new long[]{2L});
      FOLLOW_MINUSEQUAL_in_augassign2000 = new BitSet(new long[]{2L});
      FOLLOW_STAREQUAL_in_augassign2018 = new BitSet(new long[]{2L});
      FOLLOW_SLASHEQUAL_in_augassign2036 = new BitSet(new long[]{2L});
      FOLLOW_PERCENTEQUAL_in_augassign2054 = new BitSet(new long[]{2L});
      FOLLOW_AMPEREQUAL_in_augassign2072 = new BitSet(new long[]{2L});
      FOLLOW_VBAREQUAL_in_augassign2090 = new BitSet(new long[]{2L});
      FOLLOW_CIRCUMFLEXEQUAL_in_augassign2108 = new BitSet(new long[]{2L});
      FOLLOW_LEFTSHIFTEQUAL_in_augassign2126 = new BitSet(new long[]{2L});
      FOLLOW_RIGHTSHIFTEQUAL_in_augassign2144 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESTAREQUAL_in_augassign2162 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESLASHEQUAL_in_augassign2180 = new BitSet(new long[]{2L});
      FOLLOW_PRINT_in_print_stmt2220 = new BitSet(new long[]{-9223363234319300094L, 132847616L});
      FOLLOW_printlist_in_print_stmt2231 = new BitSet(new long[]{2L});
      FOLLOW_RIGHTSHIFT_in_print_stmt2250 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_printlist2_in_print_stmt2254 = new BitSet(new long[]{2L});
      FOLLOW_test_in_printlist2334 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_printlist2346 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_printlist2350 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_printlist2358 = new BitSet(new long[]{2L});
      FOLLOW_test_in_printlist2379 = new BitSet(new long[]{2L});
      FOLLOW_test_in_printlist22436 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_printlist22448 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_printlist22452 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_printlist22460 = new BitSet(new long[]{2L});
      FOLLOW_test_in_printlist22481 = new BitSet(new long[]{2L});
      FOLLOW_DELETE_in_del_stmt2518 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_del_list_in_del_stmt2520 = new BitSet(new long[]{2L});
      FOLLOW_PASS_in_pass_stmt2556 = new BitSet(new long[]{2L});
      FOLLOW_break_stmt_in_flow_stmt2582 = new BitSet(new long[]{2L});
      FOLLOW_continue_stmt_in_flow_stmt2590 = new BitSet(new long[]{2L});
      FOLLOW_return_stmt_in_flow_stmt2598 = new BitSet(new long[]{2L});
      FOLLOW_raise_stmt_in_flow_stmt2606 = new BitSet(new long[]{2L});
      FOLLOW_yield_stmt_in_flow_stmt2614 = new BitSet(new long[]{2L});
      FOLLOW_BREAK_in_break_stmt2642 = new BitSet(new long[]{2L});
      FOLLOW_CONTINUE_in_continue_stmt2678 = new BitSet(new long[]{2L});
      FOLLOW_RETURN_in_return_stmt2714 = new BitSet(new long[]{8802535475714L, 132847616L});
      FOLLOW_testlist_in_return_stmt2723 = new BitSet(new long[]{2L});
      FOLLOW_yield_expr_in_yield_stmt2788 = new BitSet(new long[]{2L});
      FOLLOW_RAISE_in_raise_stmt2824 = new BitSet(new long[]{8802535475714L, 132847616L});
      FOLLOW_test_in_raise_stmt2829 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_raise_stmt2833 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_raise_stmt2837 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_raise_stmt2849 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_raise_stmt2853 = new BitSet(new long[]{2L});
      FOLLOW_import_name_in_import_stmt2886 = new BitSet(new long[]{2L});
      FOLLOW_import_from_in_import_stmt2894 = new BitSet(new long[]{2L});
      FOLLOW_IMPORT_in_import_name2922 = new BitSet(new long[]{512L});
      FOLLOW_dotted_as_names_in_import_name2924 = new BitSet(new long[]{2L});
      FOLLOW_FROM_in_import_from2961 = new BitSet(new long[]{1536L});
      FOLLOW_DOT_in_import_from2966 = new BitSet(new long[]{1536L});
      FOLLOW_dotted_name_in_import_from2969 = new BitSet(new long[]{268435456L});
      FOLLOW_DOT_in_import_from2975 = new BitSet(new long[]{268436480L});
      FOLLOW_IMPORT_in_import_from2979 = new BitSet(new long[]{290271069733376L});
      FOLLOW_STAR_in_import_from2990 = new BitSet(new long[]{2L});
      FOLLOW_import_as_names_in_import_from3015 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_import_from3038 = new BitSet(new long[]{512L});
      FOLLOW_import_as_names_in_import_from3042 = new BitSet(new long[]{158329674399744L});
      FOLLOW_COMMA_in_import_from3044 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_import_from3047 = new BitSet(new long[]{2L});
      FOLLOW_import_as_name_in_import_as_names3096 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_import_as_names3099 = new BitSet(new long[]{512L});
      FOLLOW_import_as_name_in_import_as_names3104 = new BitSet(new long[]{140737488355330L});
      FOLLOW_NAME_in_import_as_name3145 = new BitSet(new long[]{8194L});
      FOLLOW_AS_in_import_as_name3148 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_import_as_name3152 = new BitSet(new long[]{2L});
      FOLLOW_dotted_name_in_dotted_as_name3192 = new BitSet(new long[]{8194L});
      FOLLOW_AS_in_dotted_as_name3195 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_dotted_as_name3199 = new BitSet(new long[]{2L});
      FOLLOW_dotted_as_name_in_dotted_as_names3235 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_dotted_as_names3238 = new BitSet(new long[]{512L});
      FOLLOW_dotted_as_name_in_dotted_as_names3243 = new BitSet(new long[]{140737488355330L});
      FOLLOW_NAME_in_dotted_name3277 = new BitSet(new long[]{1026L});
      FOLLOW_DOT_in_dotted_name3280 = new BitSet(new long[]{4398046509568L});
      FOLLOW_attr_in_dotted_name3284 = new BitSet(new long[]{1026L});
      FOLLOW_GLOBAL_in_global_stmt3320 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_global_stmt3324 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_global_stmt3327 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_global_stmt3331 = new BitSet(new long[]{140737488355330L});
      FOLLOW_EXEC_in_exec_stmt3369 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_expr_in_exec_stmt3371 = new BitSet(new long[]{536870914L});
      FOLLOW_IN_in_exec_stmt3375 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_exec_stmt3379 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_exec_stmt3383 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_exec_stmt3387 = new BitSet(new long[]{2L});
      FOLLOW_ASSERT_in_assert_stmt3428 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_assert_stmt3432 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_assert_stmt3436 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_assert_stmt3440 = new BitSet(new long[]{2L});
      FOLLOW_if_stmt_in_compound_stmt3469 = new BitSet(new long[]{2L});
      FOLLOW_while_stmt_in_compound_stmt3477 = new BitSet(new long[]{2L});
      FOLLOW_for_stmt_in_compound_stmt3485 = new BitSet(new long[]{2L});
      FOLLOW_try_stmt_in_compound_stmt3493 = new BitSet(new long[]{2L});
      FOLLOW_with_stmt_in_compound_stmt3501 = new BitSet(new long[]{2L});
      FOLLOW_funcdef_in_compound_stmt3518 = new BitSet(new long[]{2L});
      FOLLOW_classdef_in_compound_stmt3526 = new BitSet(new long[]{2L});
      FOLLOW_IF_in_if_stmt3554 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_if_stmt3556 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_if_stmt3559 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_if_stmt3563 = new BitSet(new long[]{17180917762L});
      FOLLOW_elif_clause_in_if_stmt3566 = new BitSet(new long[]{2L});
      FOLLOW_else_clause_in_elif_clause3611 = new BitSet(new long[]{2L});
      FOLLOW_ELIF_in_elif_clause3627 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_elif_clause3629 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_elif_clause3632 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_elif_clause3634 = new BitSet(new long[]{17180917762L});
      FOLLOW_elif_clause_in_elif_clause3646 = new BitSet(new long[]{2L});
      FOLLOW_ORELSE_in_else_clause3706 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_else_clause3708 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_else_clause3712 = new BitSet(new long[]{2L});
      FOLLOW_WHILE_in_while_stmt3749 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_while_stmt3751 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_while_stmt3754 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_while_stmt3758 = new BitSet(new long[]{17179869186L});
      FOLLOW_ORELSE_in_while_stmt3762 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_while_stmt3764 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_while_stmt3768 = new BitSet(new long[]{2L});
      FOLLOW_FOR_in_for_stmt3807 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_exprlist_in_for_stmt3809 = new BitSet(new long[]{536870912L});
      FOLLOW_IN_in_for_stmt3812 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_testlist_in_for_stmt3814 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_for_stmt3817 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_for_stmt3821 = new BitSet(new long[]{17179869186L});
      FOLLOW_ORELSE_in_for_stmt3833 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_for_stmt3835 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_for_stmt3839 = new BitSet(new long[]{2L});
      FOLLOW_TRY_in_try_stmt3882 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_try_stmt3884 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_try_stmt3888 = new BitSet(new long[]{10485760L});
      FOLLOW_except_clause_in_try_stmt3901 = new BitSet(new long[]{17190354946L});
      FOLLOW_ORELSE_in_try_stmt3905 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_try_stmt3907 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_try_stmt3911 = new BitSet(new long[]{8388610L});
      FOLLOW_FINALLY_in_try_stmt3917 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_try_stmt3919 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_try_stmt3923 = new BitSet(new long[]{2L});
      FOLLOW_FINALLY_in_try_stmt3946 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_try_stmt3948 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_try_stmt3952 = new BitSet(new long[]{2L});
      FOLLOW_WITH_in_with_stmt4001 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_with_item_in_with_stmt4005 = new BitSet(new long[]{175921860444160L});
      FOLLOW_COMMA_in_with_stmt4015 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_with_item_in_with_stmt4019 = new BitSet(new long[]{175921860444160L});
      FOLLOW_COLON_in_with_stmt4023 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_with_stmt4025 = new BitSet(new long[]{2L});
      FOLLOW_test_in_with_item4062 = new BitSet(new long[]{8194L});
      FOLLOW_AS_in_with_item4066 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_expr_in_with_item4068 = new BitSet(new long[]{2L});
      FOLLOW_EXCEPT_in_except_clause4107 = new BitSet(new long[]{43986907564544L, 132847616L});
      FOLLOW_test_in_except_clause4112 = new BitSet(new long[]{175921860452352L});
      FOLLOW_set_in_except_clause4116 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_except_clause4126 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_except_clause4133 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_except_clause4135 = new BitSet(new long[]{2L});
      FOLLOW_simple_stmt_in_suite4181 = new BitSet(new long[]{2L});
      FOLLOW_NEWLINE_in_suite4197 = new BitSet(new long[]{16L});
      FOLLOW_INDENT_in_suite4199 = new BitSet(new long[]{17564794079872L, 132847616L});
      FOLLOW_stmt_in_suite4208 = new BitSet(new long[]{17564794079904L, 132847616L});
      FOLLOW_DEDENT_in_suite4228 = new BitSet(new long[]{2L});
      FOLLOW_or_test_in_test4258 = new BitSet(new long[]{134217730L});
      FOLLOW_IF_in_test4280 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_or_test_in_test4284 = new BitSet(new long[]{17179869184L});
      FOLLOW_ORELSE_in_test4287 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_test4291 = new BitSet(new long[]{2L});
      FOLLOW_lambdef_in_test4336 = new BitSet(new long[]{2L});
      FOLLOW_and_test_in_or_test4371 = new BitSet(new long[]{8589934594L});
      FOLLOW_OR_in_or_test4387 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_and_test_in_or_test4391 = new BitSet(new long[]{8589934594L});
      FOLLOW_not_test_in_and_test4472 = new BitSet(new long[]{4098L});
      FOLLOW_AND_in_and_test4488 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_not_test_in_and_test4492 = new BitSet(new long[]{4098L});
      FOLLOW_NOT_in_not_test4576 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_not_test_in_not_test4580 = new BitSet(new long[]{2L});
      FOLLOW_comparison_in_not_test4597 = new BitSet(new long[]{2L});
      FOLLOW_expr_in_comparison4646 = new BitSet(new long[]{5905580034L, 127L});
      FOLLOW_comp_op_in_comparison4660 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_expr_in_comparison4664 = new BitSet(new long[]{5905580034L, 127L});
      FOLLOW_LESS_in_comp_op4745 = new BitSet(new long[]{2L});
      FOLLOW_GREATER_in_comp_op4761 = new BitSet(new long[]{2L});
      FOLLOW_EQUAL_in_comp_op4777 = new BitSet(new long[]{2L});
      FOLLOW_GREATEREQUAL_in_comp_op4793 = new BitSet(new long[]{2L});
      FOLLOW_LESSEQUAL_in_comp_op4809 = new BitSet(new long[]{2L});
      FOLLOW_ALT_NOTEQUAL_in_comp_op4825 = new BitSet(new long[]{2L});
      FOLLOW_NOTEQUAL_in_comp_op4841 = new BitSet(new long[]{2L});
      FOLLOW_IN_in_comp_op4857 = new BitSet(new long[]{2L});
      FOLLOW_NOT_in_comp_op4873 = new BitSet(new long[]{536870912L});
      FOLLOW_IN_in_comp_op4875 = new BitSet(new long[]{2L});
      FOLLOW_IS_in_comp_op4891 = new BitSet(new long[]{2L});
      FOLLOW_IS_in_comp_op4907 = new BitSet(new long[]{4294967296L});
      FOLLOW_NOT_in_comp_op4909 = new BitSet(new long[]{2L});
      FOLLOW_xor_expr_in_expr4961 = new BitSet(new long[]{2L, 128L});
      FOLLOW_VBAR_in_expr4976 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_xor_expr_in_expr4980 = new BitSet(new long[]{2L, 128L});
      FOLLOW_and_expr_in_xor_expr5059 = new BitSet(new long[]{2L, 256L});
      FOLLOW_CIRCUMFLEX_in_xor_expr5074 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_and_expr_in_xor_expr5078 = new BitSet(new long[]{2L, 256L});
      FOLLOW_shift_expr_in_and_expr5156 = new BitSet(new long[]{2L, 512L});
      FOLLOW_AMPER_in_and_expr5171 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_shift_expr_in_and_expr5175 = new BitSet(new long[]{2L, 512L});
      FOLLOW_arith_expr_in_shift_expr5258 = new BitSet(new long[]{-9223372036854775806L, 1024L});
      FOLLOW_shift_op_in_shift_expr5272 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_arith_expr_in_shift_expr5276 = new BitSet(new long[]{-9223372036854775806L, 1024L});
      FOLLOW_LEFTSHIFT_in_shift_op5360 = new BitSet(new long[]{2L});
      FOLLOW_RIGHTSHIFT_in_shift_op5376 = new BitSet(new long[]{2L});
      FOLLOW_term_in_arith_expr5422 = new BitSet(new long[]{2L, 6144L});
      FOLLOW_arith_op_in_arith_expr5435 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_term_in_arith_expr5439 = new BitSet(new long[]{2L, 6144L});
      FOLLOW_PLUS_in_arith_op5547 = new BitSet(new long[]{2L});
      FOLLOW_MINUS_in_arith_op5563 = new BitSet(new long[]{2L});
      FOLLOW_factor_in_term5609 = new BitSet(new long[]{281474976710658L, 57344L});
      FOLLOW_term_op_in_term5622 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_factor_in_term5626 = new BitSet(new long[]{281474976710658L, 57344L});
      FOLLOW_STAR_in_term_op5708 = new BitSet(new long[]{2L});
      FOLLOW_SLASH_in_term_op5724 = new BitSet(new long[]{2L});
      FOLLOW_PERCENT_in_term_op5740 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESLASH_in_term_op5756 = new BitSet(new long[]{2L});
      FOLLOW_PLUS_in_factor5795 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_factor_in_factor5799 = new BitSet(new long[]{2L});
      FOLLOW_MINUS_in_factor5815 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_factor_in_factor5819 = new BitSet(new long[]{2L});
      FOLLOW_TILDE_in_factor5835 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_factor_in_factor5839 = new BitSet(new long[]{2L});
      FOLLOW_power_in_factor5855 = new BitSet(new long[]{2L});
      FOLLOW_atom_in_power5894 = new BitSet(new long[]{571746046444546L, 131072L});
      FOLLOW_trailer_in_power5899 = new BitSet(new long[]{571746046444546L, 131072L});
      FOLLOW_DOUBLESTAR_in_power5914 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_factor_in_power5916 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_atom5966 = new BitSet(new long[]{28799903369728L, 132847616L});
      FOLLOW_yield_expr_in_atom5984 = new BitSet(new long[]{17592186044416L});
      FOLLOW_testlist_gexp_in_atom6004 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_atom6047 = new BitSet(new long[]{2L});
      FOLLOW_LBRACK_in_atom6055 = new BitSet(new long[]{8802535475712L, 133109760L});
      FOLLOW_listmaker_in_atom6064 = new BitSet(new long[]{0L, 262144L});
      FOLLOW_RBRACK_in_atom6107 = new BitSet(new long[]{2L});
      FOLLOW_LCURLY_in_atom6115 = new BitSet(new long[]{8802535475712L, 133896192L});
      FOLLOW_dictorsetmaker_in_atom6125 = new BitSet(new long[]{0L, 1048576L});
      FOLLOW_RCURLY_in_atom6173 = new BitSet(new long[]{2L});
      FOLLOW_BACKQUOTE_in_atom6184 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_testlist_in_atom6186 = new BitSet(new long[]{0L, 2097152L});
      FOLLOW_BACKQUOTE_in_atom6191 = new BitSet(new long[]{2L});
      FOLLOW_name_or_print_in_atom6209 = new BitSet(new long[]{2L});
      FOLLOW_INT_in_atom6227 = new BitSet(new long[]{2L});
      FOLLOW_LONGINT_in_atom6245 = new BitSet(new long[]{2L});
      FOLLOW_FLOAT_in_atom6263 = new BitSet(new long[]{2L});
      FOLLOW_COMPLEX_in_atom6281 = new BitSet(new long[]{2L});
      FOLLOW_STRING_in_atom6302 = new BitSet(new long[]{2L, 67108864L});
      FOLLOW_test_in_listmaker6345 = new BitSet(new long[]{140737521909762L});
      FOLLOW_list_for_in_listmaker6357 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_listmaker6389 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_listmaker6393 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_listmaker6422 = new BitSet(new long[]{2L});
      FOLLOW_test_in_testlist_gexp6454 = new BitSet(new long[]{140737521909762L});
      FOLLOW_COMMA_in_testlist_gexp6478 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_testlist_gexp6482 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_testlist_gexp6490 = new BitSet(new long[]{2L});
      FOLLOW_comp_for_in_testlist_gexp6544 = new BitSet(new long[]{2L});
      FOLLOW_LAMBDA_in_lambdef6608 = new BitSet(new long[]{888405395243520L});
      FOLLOW_varargslist_in_lambdef6611 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_lambdef6615 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_lambdef6617 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_trailer6656 = new BitSet(new long[]{870819651652096L, 132847616L});
      FOLLOW_arglist_in_trailer6665 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_trailer6707 = new BitSet(new long[]{2L});
      FOLLOW_LBRACK_in_trailer6715 = new BitSet(new long[]{43986907565568L, 132847616L});
      FOLLOW_subscriptlist_in_trailer6717 = new BitSet(new long[]{0L, 262144L});
      FOLLOW_RBRACK_in_trailer6720 = new BitSet(new long[]{2L});
      FOLLOW_DOT_in_trailer6736 = new BitSet(new long[]{4398046509568L});
      FOLLOW_attr_in_trailer6738 = new BitSet(new long[]{2L});
      FOLLOW_subscript_in_subscriptlist6777 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_subscriptlist6789 = new BitSet(new long[]{43986907565568L, 132847616L});
      FOLLOW_subscript_in_subscriptlist6793 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_subscriptlist6800 = new BitSet(new long[]{2L});
      FOLLOW_DOT_in_subscript6843 = new BitSet(new long[]{1024L});
      FOLLOW_DOT_in_subscript6845 = new BitSet(new long[]{1024L});
      FOLLOW_DOT_in_subscript6847 = new BitSet(new long[]{2L});
      FOLLOW_test_in_subscript6877 = new BitSet(new long[]{35184372088834L});
      FOLLOW_COLON_in_subscript6883 = new BitSet(new long[]{43986907564546L, 132847616L});
      FOLLOW_test_in_subscript6888 = new BitSet(new long[]{35184372088834L});
      FOLLOW_sliceop_in_subscript6894 = new BitSet(new long[]{2L});
      FOLLOW_COLON_in_subscript6925 = new BitSet(new long[]{43986907564546L, 132847616L});
      FOLLOW_test_in_subscript6930 = new BitSet(new long[]{35184372088834L});
      FOLLOW_sliceop_in_subscript6936 = new BitSet(new long[]{2L});
      FOLLOW_test_in_subscript6954 = new BitSet(new long[]{2L});
      FOLLOW_COLON_in_sliceop6991 = new BitSet(new long[]{8802535475714L, 132847616L});
      FOLLOW_test_in_sliceop6999 = new BitSet(new long[]{2L});
      FOLLOW_expr_in_exprlist7070 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_exprlist7082 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_expr_in_exprlist7086 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_exprlist7092 = new BitSet(new long[]{2L});
      FOLLOW_expr_in_exprlist7111 = new BitSet(new long[]{2L});
      FOLLOW_expr_in_del_list7149 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_del_list7161 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_expr_in_del_list7165 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_del_list7171 = new BitSet(new long[]{2L});
      FOLLOW_test_in_testlist7224 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_testlist7236 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_testlist7240 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_testlist7246 = new BitSet(new long[]{2L});
      FOLLOW_test_in_testlist7264 = new BitSet(new long[]{2L});
      FOLLOW_test_in_dictorsetmaker7297 = new BitSet(new long[]{175921893998594L});
      FOLLOW_COLON_in_dictorsetmaker7325 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_dictorsetmaker7329 = new BitSet(new long[]{140737521909760L});
      FOLLOW_comp_for_in_dictorsetmaker7349 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_dictorsetmaker7396 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_dictorsetmaker7400 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_dictorsetmaker7403 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_dictorsetmaker7407 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_dictorsetmaker7463 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_dictorsetmaker7467 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_dictorsetmaker7517 = new BitSet(new long[]{2L});
      FOLLOW_comp_for_in_dictorsetmaker7532 = new BitSet(new long[]{2L});
      FOLLOW_decorators_in_classdef7585 = new BitSet(new long[]{65536L});
      FOLLOW_CLASS_in_classdef7588 = new BitSet(new long[]{512L});
      FOLLOW_NAME_in_classdef7590 = new BitSet(new long[]{43980465111040L});
      FOLLOW_LPAREN_in_classdef7593 = new BitSet(new long[]{26394721520128L, 132847616L});
      FOLLOW_testlist_in_classdef7595 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RPAREN_in_classdef7599 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_classdef7603 = new BitSet(new long[]{11242434120320L, 132847616L});
      FOLLOW_suite_in_classdef7605 = new BitSet(new long[]{2L});
      FOLLOW_argument_in_arglist7647 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist7651 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_argument_in_arglist7653 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist7669 = new BitSet(new long[]{844424930131970L});
      FOLLOW_STAR_in_arglist7687 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_arglist7691 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist7695 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_argument_in_arglist7697 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist7703 = new BitSet(new long[]{562949953421312L});
      FOLLOW_DOUBLESTAR_in_arglist7705 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_arglist7709 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESTAR_in_arglist7730 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_arglist7734 = new BitSet(new long[]{2L});
      FOLLOW_STAR_in_arglist7781 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_arglist7785 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist7789 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_argument_in_arglist7791 = new BitSet(new long[]{140737488355330L});
      FOLLOW_COMMA_in_arglist7797 = new BitSet(new long[]{562949953421312L});
      FOLLOW_DOUBLESTAR_in_arglist7799 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_arglist7803 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLESTAR_in_arglist7822 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_arglist7826 = new BitSet(new long[]{2L});
      FOLLOW_test_in_argument7865 = new BitSet(new long[]{211106266087424L});
      FOLLOW_ASSIGN_in_argument7878 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_argument7882 = new BitSet(new long[]{2L});
      FOLLOW_comp_for_in_argument7908 = new BitSet(new long[]{2L});
      FOLLOW_list_for_in_list_iter7973 = new BitSet(new long[]{2L});
      FOLLOW_list_if_in_list_iter7982 = new BitSet(new long[]{2L});
      FOLLOW_FOR_in_list_for8008 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_exprlist_in_list_for8010 = new BitSet(new long[]{536870912L});
      FOLLOW_IN_in_list_for8013 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_testlist_in_list_for8015 = new BitSet(new long[]{167772162L});
      FOLLOW_list_iter_in_list_for8019 = new BitSet(new long[]{2L});
      FOLLOW_IF_in_list_if8049 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_list_if8051 = new BitSet(new long[]{167772162L});
      FOLLOW_list_iter_in_list_if8055 = new BitSet(new long[]{2L});
      FOLLOW_comp_for_in_comp_iter8086 = new BitSet(new long[]{2L});
      FOLLOW_comp_if_in_comp_iter8095 = new BitSet(new long[]{2L});
      FOLLOW_FOR_in_comp_for8121 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_exprlist_in_comp_for8123 = new BitSet(new long[]{536870912L});
      FOLLOW_IN_in_comp_for8126 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_or_test_in_comp_for8128 = new BitSet(new long[]{140737656127490L});
      FOLLOW_comp_iter_in_comp_for8131 = new BitSet(new long[]{2L});
      FOLLOW_IF_in_comp_if8160 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_comp_if8162 = new BitSet(new long[]{140737656127490L});
      FOLLOW_comp_iter_in_comp_if8165 = new BitSet(new long[]{2L});
      FOLLOW_YIELD_in_yield_expr8206 = new BitSet(new long[]{8802535475714L, 132847616L});
      FOLLOW_testlist_in_yield_expr8208 = new BitSet(new long[]{2L});
      FOLLOW_LPAREN_in_synpred1_Python1296 = new BitSet(new long[]{8796093022720L});
      FOLLOW_fpdef_in_synpred1_Python1298 = new BitSet(new long[]{140737488355328L});
      FOLLOW_COMMA_in_synpred1_Python1301 = new BitSet(new long[]{2L});
      FOLLOW_testlist_in_synpred2_Python1683 = new BitSet(new long[]{9221120237041090560L});
      FOLLOW_augassign_in_synpred2_Python1686 = new BitSet(new long[]{2L});
      FOLLOW_testlist_in_synpred3_Python1802 = new BitSet(new long[]{70368744177664L});
      FOLLOW_ASSIGN_in_synpred3_Python1805 = new BitSet(new long[]{2L});
      FOLLOW_test_in_synpred4_Python2317 = new BitSet(new long[]{140737488355328L});
      FOLLOW_COMMA_in_synpred4_Python2320 = new BitSet(new long[]{2L});
      FOLLOW_test_in_synpred5_Python2416 = new BitSet(new long[]{140737488355328L});
      FOLLOW_COMMA_in_synpred5_Python2419 = new BitSet(new long[]{8802535475712L, 132847616L});
      FOLLOW_test_in_synpred5_Python2421 = new BitSet(new long[]{2L});
      FOLLOW_decorators_in_synpred6_Python3510 = new BitSet(new long[]{262144L});
      FOLLOW_DEF_in_synpred6_Python3513 = new BitSet(new long[]{2L});
      FOLLOW_IF_in_synpred7_Python4270 = new BitSet(new long[]{8800387992064L, 132847616L});
      FOLLOW_or_test_in_synpred7_Python4272 = new BitSet(new long[]{17179869184L});
      FOLLOW_ORELSE_in_synpred7_Python4275 = new BitSet(new long[]{2L});
      FOLLOW_test_in_synpred8_Python6864 = new BitSet(new long[]{35184372088832L});
      FOLLOW_COLON_in_synpred8_Python6867 = new BitSet(new long[]{2L});
      FOLLOW_COLON_in_synpred9_Python6915 = new BitSet(new long[]{2L});
      FOLLOW_expr_in_synpred10_Python7060 = new BitSet(new long[]{140737488355328L});
      FOLLOW_COMMA_in_synpred10_Python7063 = new BitSet(new long[]{2L});
      FOLLOW_test_in_synpred11_Python7211 = new BitSet(new long[]{140737488355328L});
      FOLLOW_COMMA_in_synpred11_Python7214 = new BitSet(new long[]{2L});
   }

   class DFA139 extends DFA {
      public DFA139(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 139;
         this.eot = PythonParser.DFA139_eot;
         this.eof = PythonParser.DFA139_eof;
         this.min = PythonParser.DFA139_min;
         this.max = PythonParser.DFA139_max;
         this.accept = PythonParser.DFA139_accept;
         this.special = PythonParser.DFA139_special;
         this.transition = PythonParser.DFA139_transition;
      }

      public String getDescription() {
         return "()* loopback of 2014:18: ( options {k=2; } : COMMA k+= test[expr_contextType.Load] COLON v+= test[expr_contextType.Load] )*";
      }
   }

   class DFA136 extends DFA {
      public DFA136(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 136;
         this.eot = PythonParser.DFA136_eot;
         this.eof = PythonParser.DFA136_eof;
         this.min = PythonParser.DFA136_min;
         this.max = PythonParser.DFA136_max;
         this.accept = PythonParser.DFA136_accept;
         this.special = PythonParser.DFA136_special;
         this.transition = PythonParser.DFA136_transition;
      }

      public String getDescription() {
         return "()* loopback of 1986:22: ( options {k=2; } : COMMA t+= test[ctype] )*";
      }
   }

   class DFA138 extends DFA {
      public DFA138(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 138;
         this.eot = PythonParser.DFA138_eot;
         this.eof = PythonParser.DFA138_eof;
         this.min = PythonParser.DFA138_min;
         this.max = PythonParser.DFA138_max;
         this.accept = PythonParser.DFA138_accept;
         this.special = PythonParser.DFA138_special;
         this.transition = PythonParser.DFA138_transition;
      }

      public String getDescription() {
         return "1976:1: testlist[expr_contextType ctype] : ( ( test[null] COMMA )=>t+= test[ctype] ( options {k=2; } : COMMA t+= test[ctype] )* ( COMMA )? | test[ctype] );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean sx;
         switch (s) {
            case 0:
               int LA138_0 = input.LA(1);
               int index138_0 = input.index();
               input.rewind();
               s = -1;
               if (LA138_0 == 32) {
                  s = 1;
               } else if (LA138_0 == 75) {
                  s = 2;
               } else if (LA138_0 == 76) {
                  s = 3;
               } else if (LA138_0 == 80) {
                  s = 4;
               } else if (LA138_0 == 43) {
                  s = 5;
               } else if (LA138_0 == 81) {
                  s = 6;
               } else if (LA138_0 == 83) {
                  s = 7;
               } else if (LA138_0 == 85) {
                  s = 8;
               } else if (LA138_0 == 9) {
                  s = 9;
               } else if (LA138_0 == 11 && PythonParser.this.printFunction) {
                  s = 10;
               } else if (LA138_0 == 86) {
                  s = 11;
               } else if (LA138_0 == 87) {
                  s = 12;
               } else if (LA138_0 == 88) {
                  s = 13;
               } else if (LA138_0 == 89) {
                  s = 14;
               } else if (LA138_0 == 90) {
                  s = 15;
               } else if (LA138_0 == 31) {
                  s = 16;
               }

               input.seek(index138_0);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA138_1 = input.LA(1);
               int index138_1 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_1);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA138_2 = input.LA(1);
               int index138_2 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA138_3 = input.LA(1);
               int index138_3 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_3);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA138_4 = input.LA(1);
               int index138_4 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_4);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA138_5 = input.LA(1);
               int index138_5 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_5);
               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA138_6 = input.LA(1);
               int index138_6 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_6);
               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA138_7 = input.LA(1);
               int index138_7 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_7);
               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA138_8 = input.LA(1);
               int index138_8 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_8);
               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA138_9 = input.LA(1);
               int index138_9 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_9);
               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA138_10 = input.LA(1);
               int index138_10 = input.index();
               input.rewind();
               s = -1;
               if (PythonParser.this.synpred11_Python() && PythonParser.this.printFunction) {
                  s = 17;
               } else if (PythonParser.this.printFunction) {
                  s = 18;
               }

               input.seek(index138_10);
               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA138_11 = input.LA(1);
               int index138_11 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_11);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA138_12 = input.LA(1);
               int index138_12 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_12);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA138_13 = input.LA(1);
               int index138_13 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_13);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA138_14 = input.LA(1);
               int index138_14 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_14);
               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA138_15 = input.LA(1);
               int index138_15 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_15);
               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA138_16 = input.LA(1);
               int index138_16 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred11_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index138_16);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonParser.this.state.backtracking > 0) {
            PythonParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 138, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA134 extends DFA {
      public DFA134(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 134;
         this.eot = PythonParser.DFA134_eot;
         this.eof = PythonParser.DFA134_eof;
         this.min = PythonParser.DFA134_min;
         this.max = PythonParser.DFA134_max;
         this.accept = PythonParser.DFA134_accept;
         this.special = PythonParser.DFA134_special;
         this.transition = PythonParser.DFA134_transition;
      }

      public String getDescription() {
         return "()* loopback of 1969:37: ( options {k=2; } : COMMA e+= expr[expr_contextType.Del] )*";
      }
   }

   class DFA131 extends DFA {
      public DFA131(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 131;
         this.eot = PythonParser.DFA131_eot;
         this.eof = PythonParser.DFA131_eof;
         this.min = PythonParser.DFA131_min;
         this.max = PythonParser.DFA131_max;
         this.accept = PythonParser.DFA131_accept;
         this.special = PythonParser.DFA131_special;
         this.transition = PythonParser.DFA131_transition;
      }

      public String getDescription() {
         return "()* loopback of 1955:44: ( options {k=2; } : COMMA e+= expr[ctype] )*";
      }
   }

   class DFA133 extends DFA {
      public DFA133(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 133;
         this.eot = PythonParser.DFA133_eot;
         this.eof = PythonParser.DFA133_eof;
         this.min = PythonParser.DFA133_min;
         this.max = PythonParser.DFA133_max;
         this.accept = PythonParser.DFA133_accept;
         this.special = PythonParser.DFA133_special;
         this.transition = PythonParser.DFA133_transition;
      }

      public String getDescription() {
         return "1953:1: exprlist[expr_contextType ctype] returns [expr etype] : ( ( expr[null] COMMA )=>e+= expr[ctype] ( options {k=2; } : COMMA e+= expr[ctype] )* ( COMMA )? | expr[ctype] );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean sx;
         switch (s) {
            case 0:
               int LA133_0 = input.LA(1);
               int index133_0 = input.index();
               input.rewind();
               s = -1;
               if (LA133_0 == 75) {
                  s = 1;
               } else if (LA133_0 == 76) {
                  s = 2;
               } else if (LA133_0 == 80) {
                  s = 3;
               } else if (LA133_0 == 43) {
                  s = 4;
               } else if (LA133_0 == 81) {
                  s = 5;
               } else if (LA133_0 == 83) {
                  s = 6;
               } else if (LA133_0 == 85) {
                  s = 7;
               } else if (LA133_0 == 9) {
                  s = 8;
               } else if (LA133_0 == 11 && PythonParser.this.printFunction) {
                  s = 9;
               } else if (LA133_0 == 86) {
                  s = 10;
               } else if (LA133_0 == 87) {
                  s = 11;
               } else if (LA133_0 == 88) {
                  s = 12;
               } else if (LA133_0 == 89) {
                  s = 13;
               } else if (LA133_0 == 90) {
                  s = 14;
               }

               input.seek(index133_0);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA133_1 = input.LA(1);
               int index133_1 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_1);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA133_2 = input.LA(1);
               int index133_2 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA133_3 = input.LA(1);
               int index133_3 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_3);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA133_4 = input.LA(1);
               int index133_4 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_4);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA133_5 = input.LA(1);
               int index133_5 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_5);
               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA133_6 = input.LA(1);
               int index133_6 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_6);
               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA133_7 = input.LA(1);
               int index133_7 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_7);
               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA133_8 = input.LA(1);
               int index133_8 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_8);
               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA133_9 = input.LA(1);
               int index133_9 = input.index();
               input.rewind();
               s = -1;
               if (PythonParser.this.synpred10_Python() && PythonParser.this.printFunction) {
                  s = 15;
               } else if (PythonParser.this.printFunction) {
                  s = 16;
               }

               input.seek(index133_9);
               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA133_10 = input.LA(1);
               int index133_10 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_10);
               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA133_11 = input.LA(1);
               int index133_11 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_11);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA133_12 = input.LA(1);
               int index133_12 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_12);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA133_13 = input.LA(1);
               int index133_13 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_13);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA133_14 = input.LA(1);
               int index133_14 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred10_Python()) {
                  s = 15;
               } else {
                  s = 16;
               }

               input.seek(index133_14);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonParser.this.state.backtracking > 0) {
            PythonParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 133, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA129 extends DFA {
      public DFA129(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 129;
         this.eot = PythonParser.DFA129_eot;
         this.eof = PythonParser.DFA129_eof;
         this.min = PythonParser.DFA129_min;
         this.max = PythonParser.DFA129_max;
         this.accept = PythonParser.DFA129_accept;
         this.special = PythonParser.DFA129_special;
         this.transition = PythonParser.DFA129_transition;
      }

      public String getDescription() {
         return "1907:1: subscript returns [slice sltype] : (d1= DOT DOT DOT | ( test[null] COLON )=>lower= test[expr_contextType.Load] (c1= COLON (upper1= test[expr_contextType.Load] )? ( sliceop )? )? | ( COLON )=>c2= COLON (upper2= test[expr_contextType.Load] )? ( sliceop )? | test[expr_contextType.Load] );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean sx;
         switch (s) {
            case 0:
               int LA129_0 = input.LA(1);
               int index129_0 = input.index();
               input.rewind();
               s = -1;
               if (LA129_0 == 10) {
                  s = 1;
               } else if (LA129_0 == 32) {
                  s = 2;
               } else if (LA129_0 == 75) {
                  s = 3;
               } else if (LA129_0 == 76) {
                  s = 4;
               } else if (LA129_0 == 80) {
                  s = 5;
               } else if (LA129_0 == 43) {
                  s = 6;
               } else if (LA129_0 == 81) {
                  s = 7;
               } else if (LA129_0 == 83) {
                  s = 8;
               } else if (LA129_0 == 85) {
                  s = 9;
               } else if (LA129_0 == 9) {
                  s = 10;
               } else if (LA129_0 == 11 && PythonParser.this.printFunction) {
                  s = 11;
               } else if (LA129_0 == 86) {
                  s = 12;
               } else if (LA129_0 == 87) {
                  s = 13;
               } else if (LA129_0 == 88) {
                  s = 14;
               } else if (LA129_0 == 89) {
                  s = 15;
               } else if (LA129_0 == 90) {
                  s = 16;
               } else if (LA129_0 == 31) {
                  s = 17;
               } else if (LA129_0 == 45 && PythonParser.this.synpred9_Python()) {
                  s = 18;
               }

               input.seek(index129_0);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA129_2 = input.LA(1);
               int index129_2 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA129_3 = input.LA(1);
               int index129_3 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_3);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA129_4 = input.LA(1);
               int index129_4 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_4);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA129_5 = input.LA(1);
               int index129_5 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_5);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA129_6 = input.LA(1);
               int index129_6 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_6);
               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA129_7 = input.LA(1);
               int index129_7 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_7);
               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA129_8 = input.LA(1);
               int index129_8 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_8);
               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA129_9 = input.LA(1);
               int index129_9 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_9);
               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA129_10 = input.LA(1);
               int index129_10 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_10);
               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA129_11 = input.LA(1);
               int index129_11 = input.index();
               input.rewind();
               s = -1;
               if (PythonParser.this.synpred8_Python() && PythonParser.this.printFunction) {
                  s = 19;
               } else if (PythonParser.this.printFunction) {
                  s = 20;
               }

               input.seek(index129_11);
               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA129_12 = input.LA(1);
               int index129_12 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_12);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA129_13 = input.LA(1);
               int index129_13 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_13);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA129_14 = input.LA(1);
               int index129_14 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_14);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA129_15 = input.LA(1);
               int index129_15 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_15);
               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA129_16 = input.LA(1);
               int index129_16 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_16);
               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA129_17 = input.LA(1);
               int index129_17 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred8_Python()) {
                  s = 19;
               } else {
                  s = 20;
               }

               input.seek(index129_17);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonParser.this.state.backtracking > 0) {
            PythonParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 129, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA116 extends DFA {
      public DFA116(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 116;
         this.eot = PythonParser.DFA116_eot;
         this.eof = PythonParser.DFA116_eof;
         this.min = PythonParser.DFA116_min;
         this.max = PythonParser.DFA116_max;
         this.accept = PythonParser.DFA116_accept;
         this.special = PythonParser.DFA116_special;
         this.transition = PythonParser.DFA116_transition;
      }

      public String getDescription() {
         return "()* loopback of 1822:11: ( options {k=2; } : c1= COMMA t+= test[$expr::ctype] )*";
      }
   }

   class DFA112 extends DFA {
      public DFA112(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 112;
         this.eot = PythonParser.DFA112_eot;
         this.eof = PythonParser.DFA112_eof;
         this.min = PythonParser.DFA112_min;
         this.max = PythonParser.DFA112_max;
         this.accept = PythonParser.DFA112_accept;
         this.special = PythonParser.DFA112_special;
         this.transition = PythonParser.DFA112_transition;
      }

      public String getDescription() {
         return "1713:1: atom returns [Token lparen = null] : ( LPAREN ( yield_expr | testlist_gexp -> testlist_gexp | ) RPAREN | LBRACK ( listmaker[$LBRACK] -> listmaker | ) RBRACK | LCURLY ( dictorsetmaker[$LCURLY] -> dictorsetmaker | ) RCURLY | lb= BACKQUOTE testlist[expr_contextType.Load] rb= BACKQUOTE | name_or_print | INT | LONGINT | FLOAT | COMPLEX | (S+= STRING )+ );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         switch (s) {
            case 0:
               int LA112_0 = input.LA(1);
               int index112_0 = input.index();
               input.rewind();
               s = -1;
               if (LA112_0 == 43) {
                  s = 1;
               } else if (LA112_0 == 81) {
                  s = 2;
               } else if (LA112_0 == 83) {
                  s = 3;
               } else if (LA112_0 == 85) {
                  s = 4;
               } else if (LA112_0 == 9) {
                  s = 5;
               } else if (LA112_0 == 11 && PythonParser.this.printFunction) {
                  s = 6;
               } else if (LA112_0 == 86) {
                  s = 7;
               } else if (LA112_0 == 87) {
                  s = 8;
               } else if (LA112_0 == 88) {
                  s = 9;
               } else if (LA112_0 == 89) {
                  s = 10;
               } else if (LA112_0 == 90) {
                  s = 11;
               }

               input.seek(index112_0);
               if (s >= 0) {
                  return s;
               }
            default:
               if (PythonParser.this.state.backtracking > 0) {
                  PythonParser.this.state.failed = true;
                  return -1;
               } else {
                  NoViableAltException nvae = new NoViableAltException(this.getDescription(), 112, s, input);
                  this.error(nvae);
                  throw nvae;
               }
         }
      }
   }

   class DFA89 extends DFA {
      public DFA89(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 89;
         this.eot = PythonParser.DFA89_eot;
         this.eof = PythonParser.DFA89_eof;
         this.min = PythonParser.DFA89_min;
         this.max = PythonParser.DFA89_max;
         this.accept = PythonParser.DFA89_accept;
         this.special = PythonParser.DFA89_special;
         this.transition = PythonParser.DFA89_transition;
      }

      public String getDescription() {
         return "1388:1: comp_op returns [cmpopType op] : ( LESS | GREATER | EQUAL | GREATEREQUAL | LESSEQUAL | ALT_NOTEQUAL | NOTEQUAL | IN | NOT IN | IS | IS NOT );";
      }
   }

   class DFA80 extends DFA {
      public DFA80(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 80;
         this.eot = PythonParser.DFA80_eot;
         this.eof = PythonParser.DFA80_eof;
         this.min = PythonParser.DFA80_min;
         this.max = PythonParser.DFA80_max;
         this.accept = PythonParser.DFA80_accept;
         this.special = PythonParser.DFA80_special;
         this.transition = PythonParser.DFA80_transition;
      }

      public String getDescription() {
         return "1292:7: ( ( IF or_test[null] ORELSE )=> IF o2= or_test[ctype] ORELSE e= test[expr_contextType.Load] | -> or_test )";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         switch (s) {
            case 0:
               int LA80_1 = input.LA(1);
               int index80_1 = input.index();
               input.rewind();
               int sx = true;
               if (PythonParser.this.synpred7_Python()) {
                  s = 26;
               } else {
                  s = 2;
               }

               input.seek(index80_1);
               if (s >= 0) {
                  return s;
               }
            default:
               if (PythonParser.this.state.backtracking > 0) {
                  PythonParser.this.state.failed = true;
                  return -1;
               } else {
                  NoViableAltException nvae = new NoViableAltException(this.getDescription(), 80, s, input);
                  this.error(nvae);
                  throw nvae;
               }
         }
      }
   }

   class DFA52 extends DFA {
      public DFA52(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 52;
         this.eot = PythonParser.DFA52_eot;
         this.eof = PythonParser.DFA52_eof;
         this.min = PythonParser.DFA52_min;
         this.max = PythonParser.DFA52_max;
         this.accept = PythonParser.DFA52_accept;
         this.special = PythonParser.DFA52_special;
         this.transition = PythonParser.DFA52_transition;
      }

      public String getDescription() {
         return "945:12: ( (d+= DOT )* dotted_name | (d+= DOT )+ )";
      }
   }

   class DFA41 extends DFA {
      public DFA41(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 41;
         this.eot = PythonParser.DFA41_eot;
         this.eof = PythonParser.DFA41_eof;
         this.min = PythonParser.DFA41_min;
         this.max = PythonParser.DFA41_max;
         this.accept = PythonParser.DFA41_accept;
         this.special = PythonParser.DFA41_special;
         this.transition = PythonParser.DFA41_transition;
      }

      public String getDescription() {
         return "()* loopback of 784:39: ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )*";
      }
   }

   class DFA43 extends DFA {
      public DFA43(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 43;
         this.eot = PythonParser.DFA43_eot;
         this.eof = PythonParser.DFA43_eof;
         this.min = PythonParser.DFA43_min;
         this.max = PythonParser.DFA43_max;
         this.accept = PythonParser.DFA43_accept;
         this.special = PythonParser.DFA43_special;
         this.transition = PythonParser.DFA43_transition;
      }

      public String getDescription() {
         return "781:1: printlist2 returns [boolean newline, List elts] : ( ( test[null] COMMA test[null] )=>t+= test[expr_contextType.Load] ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )* (trailcomma= COMMA )? | t+= test[expr_contextType.Load] );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean sx;
         switch (s) {
            case 0:
               int LA43_0 = input.LA(1);
               int index43_0 = input.index();
               input.rewind();
               s = -1;
               if (LA43_0 == 32) {
                  s = 1;
               } else if (LA43_0 == 75) {
                  s = 2;
               } else if (LA43_0 == 76) {
                  s = 3;
               } else if (LA43_0 == 80) {
                  s = 4;
               } else if (LA43_0 == 43) {
                  s = 5;
               } else if (LA43_0 == 81) {
                  s = 6;
               } else if (LA43_0 == 83) {
                  s = 7;
               } else if (LA43_0 == 85) {
                  s = 8;
               } else if (LA43_0 == 9) {
                  s = 9;
               } else if (LA43_0 == 11 && PythonParser.this.printFunction) {
                  s = 10;
               } else if (LA43_0 == 86) {
                  s = 11;
               } else if (LA43_0 == 87) {
                  s = 12;
               } else if (LA43_0 == 88) {
                  s = 13;
               } else if (LA43_0 == 89) {
                  s = 14;
               } else if (LA43_0 == 90) {
                  s = 15;
               } else if (LA43_0 == 31) {
                  s = 16;
               }

               input.seek(index43_0);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA43_1 = input.LA(1);
               int index43_1 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_1);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA43_2 = input.LA(1);
               int index43_2 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA43_3 = input.LA(1);
               int index43_3 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_3);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA43_4 = input.LA(1);
               int index43_4 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_4);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA43_5 = input.LA(1);
               int index43_5 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_5);
               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA43_6 = input.LA(1);
               int index43_6 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_6);
               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA43_7 = input.LA(1);
               int index43_7 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_7);
               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA43_8 = input.LA(1);
               int index43_8 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_8);
               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA43_9 = input.LA(1);
               int index43_9 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_9);
               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA43_10 = input.LA(1);
               int index43_10 = input.index();
               input.rewind();
               s = -1;
               if (PythonParser.this.synpred5_Python() && PythonParser.this.printFunction) {
                  s = 17;
               } else if (PythonParser.this.printFunction) {
                  s = 18;
               }

               input.seek(index43_10);
               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA43_11 = input.LA(1);
               int index43_11 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_11);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA43_12 = input.LA(1);
               int index43_12 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_12);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA43_13 = input.LA(1);
               int index43_13 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_13);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA43_14 = input.LA(1);
               int index43_14 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_14);
               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA43_15 = input.LA(1);
               int index43_15 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_15);
               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA43_16 = input.LA(1);
               int index43_16 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred5_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index43_16);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonParser.this.state.backtracking > 0) {
            PythonParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 43, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA38 extends DFA {
      public DFA38(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 38;
         this.eot = PythonParser.DFA38_eot;
         this.eof = PythonParser.DFA38_eof;
         this.min = PythonParser.DFA38_min;
         this.max = PythonParser.DFA38_max;
         this.accept = PythonParser.DFA38_accept;
         this.special = PythonParser.DFA38_special;
         this.transition = PythonParser.DFA38_transition;
      }

      public String getDescription() {
         return "()* loopback of 763:39: ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )*";
      }
   }

   class DFA40 extends DFA {
      public DFA40(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 40;
         this.eot = PythonParser.DFA40_eot;
         this.eof = PythonParser.DFA40_eof;
         this.min = PythonParser.DFA40_min;
         this.max = PythonParser.DFA40_max;
         this.accept = PythonParser.DFA40_accept;
         this.special = PythonParser.DFA40_special;
         this.transition = PythonParser.DFA40_transition;
      }

      public String getDescription() {
         return "760:1: printlist returns [boolean newline, List elts] : ( ( test[null] COMMA )=>t+= test[expr_contextType.Load] ( options {k=2; } : COMMA t+= test[expr_contextType.Load] )* (trailcomma= COMMA )? | t+= test[expr_contextType.Load] );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean sx;
         switch (s) {
            case 0:
               int LA40_0 = input.LA(1);
               int index40_0 = input.index();
               input.rewind();
               s = -1;
               if (LA40_0 == 32) {
                  s = 1;
               } else if (LA40_0 == 75) {
                  s = 2;
               } else if (LA40_0 == 76) {
                  s = 3;
               } else if (LA40_0 == 80) {
                  s = 4;
               } else if (LA40_0 == 43) {
                  s = 5;
               } else if (LA40_0 == 81) {
                  s = 6;
               } else if (LA40_0 == 83) {
                  s = 7;
               } else if (LA40_0 == 85) {
                  s = 8;
               } else if (LA40_0 == 9) {
                  s = 9;
               } else if (LA40_0 == 11 && PythonParser.this.printFunction) {
                  s = 10;
               } else if (LA40_0 == 86) {
                  s = 11;
               } else if (LA40_0 == 87) {
                  s = 12;
               } else if (LA40_0 == 88) {
                  s = 13;
               } else if (LA40_0 == 89) {
                  s = 14;
               } else if (LA40_0 == 90) {
                  s = 15;
               } else if (LA40_0 == 31) {
                  s = 16;
               }

               input.seek(index40_0);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA40_1 = input.LA(1);
               int index40_1 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_1);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA40_2 = input.LA(1);
               int index40_2 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA40_3 = input.LA(1);
               int index40_3 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_3);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA40_4 = input.LA(1);
               int index40_4 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_4);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA40_5 = input.LA(1);
               int index40_5 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_5);
               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA40_6 = input.LA(1);
               int index40_6 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_6);
               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA40_7 = input.LA(1);
               int index40_7 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_7);
               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA40_8 = input.LA(1);
               int index40_8 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_8);
               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA40_9 = input.LA(1);
               int index40_9 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_9);
               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA40_10 = input.LA(1);
               int index40_10 = input.index();
               input.rewind();
               s = -1;
               if (PythonParser.this.synpred4_Python() && PythonParser.this.printFunction) {
                  s = 17;
               } else if (PythonParser.this.printFunction) {
                  s = 18;
               }

               input.seek(index40_10);
               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA40_11 = input.LA(1);
               int index40_11 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_11);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA40_12 = input.LA(1);
               int index40_12 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_12);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA40_13 = input.LA(1);
               int index40_13 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_13);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA40_14 = input.LA(1);
               int index40_14 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_14);
               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA40_15 = input.LA(1);
               int index40_15 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_15);
               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA40_16 = input.LA(1);
               int index40_16 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred4_Python()) {
                  s = 17;
               } else {
                  s = 18;
               }

               input.seek(index40_16);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonParser.this.state.backtracking > 0) {
            PythonParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 40, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA31 extends DFA {
      public DFA31(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 31;
         this.eot = PythonParser.DFA31_eot;
         this.eof = PythonParser.DFA31_eof;
         this.min = PythonParser.DFA31_min;
         this.max = PythonParser.DFA31_max;
         this.accept = PythonParser.DFA31_accept;
         this.special = PythonParser.DFA31_special;
         this.transition = PythonParser.DFA31_transition;
      }

      public String getDescription() {
         return "644:9: ( (aay= augassign y1= yield_expr ) | (aat= augassign rhs= testlist[expr_contextType.Load] ) )";
      }
   }

   class DFA35 extends DFA {
      public DFA35(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 35;
         this.eot = PythonParser.DFA35_eot;
         this.eof = PythonParser.DFA35_eof;
         this.min = PythonParser.DFA35_min;
         this.max = PythonParser.DFA35_max;
         this.accept = PythonParser.DFA35_accept;
         this.special = PythonParser.DFA35_special;
         this.transition = PythonParser.DFA35_transition;
      }

      public String getDescription() {
         return "643:7: ( ( testlist[null] augassign )=>lhs= testlist[expr_contextType.AugStore] ( (aay= augassign y1= yield_expr ) | (aat= augassign rhs= testlist[expr_contextType.Load] ) ) | ( testlist[null] ASSIGN )=>lhs= testlist[expr_contextType.Store] ( | ( (at= ASSIGN t+= testlist[expr_contextType.Store] )+ ) | ( (ay= ASSIGN y2+= yield_expr )+ ) ) | lhs= testlist[expr_contextType.Load] )";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         boolean sx;
         switch (s) {
            case 0:
               int LA35_0 = input.LA(1);
               int index35_0 = input.index();
               input.rewind();
               s = -1;
               if (LA35_0 == 32) {
                  s = 1;
               } else if (LA35_0 == 75) {
                  s = 2;
               } else if (LA35_0 == 76) {
                  s = 3;
               } else if (LA35_0 == 80) {
                  s = 4;
               } else if (LA35_0 == 43) {
                  s = 5;
               } else if (LA35_0 == 81) {
                  s = 6;
               } else if (LA35_0 == 83) {
                  s = 7;
               } else if (LA35_0 == 85) {
                  s = 8;
               } else if (LA35_0 == 9) {
                  s = 9;
               } else if (LA35_0 == 11 && PythonParser.this.printFunction) {
                  s = 10;
               } else if (LA35_0 == 86) {
                  s = 11;
               } else if (LA35_0 == 87) {
                  s = 12;
               } else if (LA35_0 == 88) {
                  s = 13;
               } else if (LA35_0 == 89) {
                  s = 14;
               } else if (LA35_0 == 90) {
                  s = 15;
               } else if (LA35_0 == 31) {
                  s = 16;
               }

               input.seek(index35_0);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA35_1 = input.LA(1);
               int index35_1 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_1);
               if (s >= 0) {
                  return s;
               }
               break;
            case 2:
               int LA35_2 = input.LA(1);
               int index35_2 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 3:
               int LA35_3 = input.LA(1);
               int index35_3 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_3);
               if (s >= 0) {
                  return s;
               }
               break;
            case 4:
               int LA35_4 = input.LA(1);
               int index35_4 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_4);
               if (s >= 0) {
                  return s;
               }
               break;
            case 5:
               int LA35_5 = input.LA(1);
               int index35_5 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_5);
               if (s >= 0) {
                  return s;
               }
               break;
            case 6:
               int LA35_6 = input.LA(1);
               int index35_6 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_6);
               if (s >= 0) {
                  return s;
               }
               break;
            case 7:
               int LA35_7 = input.LA(1);
               int index35_7 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_7);
               if (s >= 0) {
                  return s;
               }
               break;
            case 8:
               int LA35_8 = input.LA(1);
               int index35_8 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_8);
               if (s >= 0) {
                  return s;
               }
               break;
            case 9:
               int LA35_9 = input.LA(1);
               int index35_9 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_9);
               if (s >= 0) {
                  return s;
               }
               break;
            case 10:
               int LA35_10 = input.LA(1);
               int index35_10 = input.index();
               input.rewind();
               s = -1;
               if ((!PythonParser.this.synpred2_Python() || !PythonParser.this.printFunction) && (!PythonParser.this.synpred2_Python() || !PythonParser.this.printFunction)) {
                  if (PythonParser.this.synpred3_Python() && PythonParser.this.printFunction || PythonParser.this.synpred3_Python() && PythonParser.this.printFunction) {
                     s = 18;
                  } else if (PythonParser.this.printFunction) {
                     s = 19;
                  }
               } else {
                  s = 17;
               }

               input.seek(index35_10);
               if (s >= 0) {
                  return s;
               }
               break;
            case 11:
               int LA35_11 = input.LA(1);
               int index35_11 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_11);
               if (s >= 0) {
                  return s;
               }
               break;
            case 12:
               int LA35_12 = input.LA(1);
               int index35_12 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_12);
               if (s >= 0) {
                  return s;
               }
               break;
            case 13:
               int LA35_13 = input.LA(1);
               int index35_13 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_13);
               if (s >= 0) {
                  return s;
               }
               break;
            case 14:
               int LA35_14 = input.LA(1);
               int index35_14 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_14);
               if (s >= 0) {
                  return s;
               }
               break;
            case 15:
               int LA35_15 = input.LA(1);
               int index35_15 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_15);
               if (s >= 0) {
                  return s;
               }
               break;
            case 16:
               int LA35_16 = input.LA(1);
               int index35_16 = input.index();
               input.rewind();
               sx = true;
               if (PythonParser.this.synpred2_Python()) {
                  s = 17;
               } else if (PythonParser.this.synpred3_Python()) {
                  s = 18;
               } else {
                  s = 19;
               }

               input.seek(index35_16);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonParser.this.state.backtracking > 0) {
            PythonParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 35, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   class DFA30 extends DFA {
      public DFA30(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 30;
         this.eot = PythonParser.DFA30_eot;
         this.eof = PythonParser.DFA30_eof;
         this.min = PythonParser.DFA30_min;
         this.max = PythonParser.DFA30_max;
         this.accept = PythonParser.DFA30_accept;
         this.special = PythonParser.DFA30_special;
         this.transition = PythonParser.DFA30_transition;
      }

      public String getDescription() {
         return "621:1: small_stmt : ( expr_stmt | del_stmt | pass_stmt | flow_stmt | import_stmt | global_stmt | exec_stmt | assert_stmt | {...}? => print_stmt );";
      }

      public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
         TokenStream input = (TokenStream)_input;
         switch (s) {
            case 0:
               int LA30_2 = input.LA(1);
               int index30_2 = input.index();
               input.rewind();
               s = -1;
               if (PythonParser.this.printFunction) {
                  s = 1;
               } else if (!PythonParser.this.printFunction) {
                  s = 10;
               }

               input.seek(index30_2);
               if (s >= 0) {
                  return s;
               }
               break;
            case 1:
               int LA30_0 = input.LA(1);
               int index30_0 = input.index();
               input.rewind();
               s = -1;
               if (LA30_0 != 9 && (LA30_0 < 31 || LA30_0 > 32) && LA30_0 != 43 && (LA30_0 < 75 || LA30_0 > 76) && (LA30_0 < 80 || LA30_0 > 81) && LA30_0 != 83 && (LA30_0 < 85 || LA30_0 > 90)) {
                  if (LA30_0 != 11 || !PythonParser.this.printFunction && PythonParser.this.printFunction) {
                     if (LA30_0 == 19) {
                        s = 3;
                     } else if (LA30_0 == 35) {
                        s = 4;
                     } else if (LA30_0 != 15 && LA30_0 != 17 && (LA30_0 < 36 || LA30_0 > 37) && LA30_0 != 41) {
                        if (LA30_0 != 24 && LA30_0 != 28) {
                           if (LA30_0 == 26) {
                              s = 7;
                           } else if (LA30_0 == 22) {
                              s = 8;
                           } else if (LA30_0 == 14) {
                              s = 9;
                           }
                        } else {
                           s = 6;
                        }
                     } else {
                        s = 5;
                     }
                  } else {
                     s = 2;
                  }
               } else {
                  s = 1;
               }

               input.seek(index30_0);
               if (s >= 0) {
                  return s;
               }
         }

         if (PythonParser.this.state.backtracking > 0) {
            PythonParser.this.state.failed = true;
            return -1;
         } else {
            NoViableAltException nvae = new NoViableAltException(this.getDescription(), 30, s, input);
            this.error(nvae);
            throw nvae;
         }
      }
   }

   public static class yield_expr_return extends ParserRuleReturnScope {
      public expr etype;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class comp_if_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class comp_for_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class comp_iter_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class list_if_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class list_for_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class list_iter_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class argument_return extends ParserRuleReturnScope {
      public boolean genarg;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class arglist_return extends ParserRuleReturnScope {
      public List args;
      public List keywords;
      public expr starargs;
      public expr kwargs;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class classdef_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class dictorsetmaker_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class testlist_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class del_list_return extends ParserRuleReturnScope {
      public List etypes;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class exprlist_return extends ParserRuleReturnScope {
      public expr etype;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class sliceop_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class subscript_return extends ParserRuleReturnScope {
      public slice sltype;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class subscriptlist_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class trailer_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class lambdef_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class testlist_gexp_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class listmaker_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class atom_return extends ParserRuleReturnScope {
      public Token lparen = null;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class power_return extends ParserRuleReturnScope {
      public expr etype;
      public Token lparen = null;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class factor_return extends ParserRuleReturnScope {
      public expr etype;
      public Token lparen = null;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class term_op_return extends ParserRuleReturnScope {
      public operatorType op;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class term_return extends ParserRuleReturnScope {
      public Token lparen = null;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class arith_op_return extends ParserRuleReturnScope {
      public operatorType op;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class arith_expr_return extends ParserRuleReturnScope {
      public Token lparen = null;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class shift_op_return extends ParserRuleReturnScope {
      public operatorType op;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class shift_expr_return extends ParserRuleReturnScope {
      public Token lparen = null;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class and_expr_return extends ParserRuleReturnScope {
      public Token lparen = null;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class xor_expr_return extends ParserRuleReturnScope {
      public Token lparen = null;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class expr_return extends ParserRuleReturnScope {
      public Token leftTok;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   protected static class expr_scope {
      expr_contextType ctype;
   }

   public static class comp_op_return extends ParserRuleReturnScope {
      public cmpopType op;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class comparison_return extends ParserRuleReturnScope {
      public Token leftTok;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class not_test_return extends ParserRuleReturnScope {
      public Token leftTok;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class and_test_return extends ParserRuleReturnScope {
      public Token leftTok;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class or_test_return extends ParserRuleReturnScope {
      public Token leftTok;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class test_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class suite_return extends ParserRuleReturnScope {
      public List stypes;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   protected static class suite_scope {
      boolean continueIllegal;
   }

   public static class except_clause_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class with_item_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class with_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class try_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class for_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class while_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class else_clause_return extends ParserRuleReturnScope {
      public List stypes;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class elif_clause_return extends ParserRuleReturnScope {
      public List stypes;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class if_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class compound_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class assert_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class exec_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class global_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class dotted_name_return extends ParserRuleReturnScope {
      public List names;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class dotted_as_names_return extends ParserRuleReturnScope {
      public List atypes;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class dotted_as_name_return extends ParserRuleReturnScope {
      public alias atype;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class import_as_name_return extends ParserRuleReturnScope {
      public alias atype;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class import_as_names_return extends ParserRuleReturnScope {
      public List atypes;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class import_from_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class import_name_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class import_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class raise_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class yield_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class return_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class continue_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class break_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class flow_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class pass_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class del_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class printlist2_return extends ParserRuleReturnScope {
      public boolean newline;
      public List elts;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class printlist_return extends ParserRuleReturnScope {
      public boolean newline;
      public List elts;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class print_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class augassign_return extends ParserRuleReturnScope {
      public operatorType op;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class expr_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class small_stmt_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class simple_stmt_return extends ParserRuleReturnScope {
      public List stypes;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class stmt_return extends ParserRuleReturnScope {
      public List stypes;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class fplist_return extends ParserRuleReturnScope {
      public List etypes;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class fpdef_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class varargslist_return extends ParserRuleReturnScope {
      public arguments args;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class defparameter_return extends ParserRuleReturnScope {
      public expr etype;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class parameters_return extends ParserRuleReturnScope {
      public arguments args;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class funcdef_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class decorators_return extends ParserRuleReturnScope {
      public List etypes;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class decorator_return extends ParserRuleReturnScope {
      public expr etype;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class attr_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class name_or_print_return extends ParserRuleReturnScope {
      public Token tok;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class dotted_attr_return extends ParserRuleReturnScope {
      public expr etype;
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class eval_input_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class file_input_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }

   public static class single_input_return extends ParserRuleReturnScope {
      PythonTree tree;

      public Object getTree() {
         return this.tree;
      }
   }
}
