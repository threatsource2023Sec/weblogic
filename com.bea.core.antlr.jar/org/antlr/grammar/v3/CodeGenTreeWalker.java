package org.antlr.grammar.v3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.antlr.analysis.DFA;
import org.antlr.analysis.LookaheadSet;
import org.antlr.analysis.NFAState;
import org.antlr.codegen.CodeGenerator;
import org.antlr.misc.IntSet;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;
import org.antlr.tool.Rule;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public class CodeGenTreeWalker extends TreeParser {
   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTION", "ACTION_CHAR_LITERAL", "ACTION_ESC", "ACTION_STRING_LITERAL", "ALT", "AMPERSAND", "ARG", "ARGLIST", "ARG_ACTION", "ASSIGN", "BACKTRACK_SEMPRED", "BANG", "BLOCK", "CATCH", "CHAR_LITERAL", "CHAR_RANGE", "CLOSE_ELEMENT_OPTION", "CLOSURE", "COLON", "COMBINED_GRAMMAR", "COMMA", "COMMENT", "DIGIT", "DOC_COMMENT", "DOLLAR", "DOT", "DOUBLE_ANGLE_STRING_LITERAL", "DOUBLE_QUOTE_STRING_LITERAL", "EOA", "EOB", "EOR", "EPSILON", "ESC", "ETC", "FINALLY", "FORCED_ACTION", "FRAGMENT", "GATED_SEMPRED", "GRAMMAR", "ID", "IMPLIES", "IMPORT", "INITACTION", "INT", "LABEL", "LEXER", "LEXER_GRAMMAR", "LPAREN", "ML_COMMENT", "NESTED_ACTION", "NESTED_ARG_ACTION", "NOT", "OPEN_ELEMENT_OPTION", "OPTIONAL", "OPTIONS", "OR", "PARSER", "PARSER_GRAMMAR", "PLUS", "PLUS_ASSIGN", "POSITIVE_CLOSURE", "PREC_RULE", "PRIVATE", "PROTECTED", "PUBLIC", "QUESTION", "RANGE", "RCURLY", "RECURSIVE_RULE_REF", "RET", "RETURNS", "REWRITE", "REWRITES", "ROOT", "RPAREN", "RULE", "RULE_REF", "SCOPE", "SEMI", "SEMPRED", "SL_COMMENT", "SRC", "STAR", "STRAY_BRACKET", "STRING_LITERAL", "SYNPRED", "SYN_SEMPRED", "TEMPLATE", "THROWS", "TOKENS", "TOKEN_REF", "TREE", "TREE_BEGIN", "TREE_GRAMMAR", "WILDCARD", "WS", "WS_LOOP", "WS_OPT", "XDIGIT"};
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
   protected static final int RULE_BLOCK_NESTING_LEVEL = 0;
   protected static final int OUTER_REWRITE_NESTING_LEVEL = 0;
   private String currentRuleName;
   protected int blockNestingLevel;
   protected int rewriteBlockNestingLevel;
   private int outerAltNum;
   protected ST currentBlockST;
   protected boolean currentAltHasASTRewrite;
   protected int rewriteTreeNestingLevel;
   protected HashSet rewriteRuleRefs;
   protected CodeGenerator generator;
   protected Grammar grammar;
   protected STGroup templates;
   protected ST recognizerST;
   protected ST outputFileST;
   protected ST headerFileST;
   protected String outputOption;
   public static final BitSet FOLLOW_LEXER_GRAMMAR_in_grammar_67 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_69 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PARSER_GRAMMAR_in_grammar_79 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_81 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_GRAMMAR_in_grammar_91 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_93 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_COMBINED_GRAMMAR_in_grammar_103 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_105 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_SCOPE_in_attrScope124 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_attrScope126 = new BitSet(new long[]{528L});
   public static final BitSet FOLLOW_AMPERSAND_in_attrScope131 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_attrScope140 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_grammarSpec157 = new BitSet(new long[]{288265560658018816L, 537034754L});
   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarSpec165 = new BitSet(new long[]{288265560523801088L, 537034754L});
   public static final BitSet FOLLOW_OPTIONS_in_grammarSpec186 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_IMPORT_in_grammarSpec200 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_TOKENS_in_grammarSpec214 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_attrScope_in_grammarSpec226 = new BitSet(new long[]{512L, 163842L});
   public static final BitSet FOLLOW_AMPERSAND_in_grammarSpec235 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rules_in_grammarSpec246 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rule_in_rules291 = new BitSet(new long[]{2L, 32770L});
   public static final BitSet FOLLOW_RULE_in_rules305 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_PREC_RULE_in_rules317 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_RULE_in_rule359 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rule363 = new BitSet(new long[]{1099511628800L, 28L});
   public static final BitSet FOLLOW_modifier_in_rule376 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_ARG_in_rule384 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rule387 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RET_in_rule396 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rule399 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_throwsSpec_in_rule408 = new BitSet(new long[]{288230376151777792L, 131072L});
   public static final BitSet FOLLOW_OPTIONS_in_rule418 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ruleScopeSpec_in_rule431 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_AMPERSAND_in_rule441 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_rule455 = new BitSet(new long[]{292057907200L});
   public static final BitSet FOLLOW_exceptionGroup_in_rule468 = new BitSet(new long[]{17179869184L});
   public static final BitSet FOLLOW_EOR_in_rule476 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_THROWS_in_throwsSpec526 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_throwsSpec528 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec543 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_AMPERSAND_in_ruleScopeSpec548 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec558 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_ID_in_ruleScopeSpec564 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_setBlock_in_block605 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BLOCK_in_block618 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_OPTIONS_in_block626 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_alternative_in_block643 = new BitSet(new long[]{8589934848L, 4096L});
   public static final BitSet FOLLOW_rewrite_in_block648 = new BitSet(new long[]{8589934848L});
   public static final BitSet FOLLOW_EOB_in_block665 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BLOCK_in_setBlock697 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ALT_in_setAlternative717 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_setElement_in_setAlternative719 = new BitSet(new long[]{4295753728L, 1090519040L});
   public static final BitSet FOLLOW_EOA_in_setAlternative722 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup737 = new BitSet(new long[]{274878038018L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup744 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup752 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CATCH_in_exceptionHandler766 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler768 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_exceptionHandler770 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_FINALLY_in_finallyClause788 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_finallyClause790 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_alternative820 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_alternative833 = new BitSet(new long[]{-9043225263786303472L, 22666616833L});
   public static final BitSet FOLLOW_EOA_in_alternative851 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ROOT_in_element886 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element890 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BANG_in_element903 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element907 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_NOT_in_element923 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_notElement_in_element927 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ASSIGN_in_element942 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element946 = new BitSet(new long[]{-9043225268081270768L, 22666616833L});
   public static final BitSet FOLLOW_element_in_element950 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PLUS_ASSIGN_in_element965 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element969 = new BitSet(new long[]{-9043225268081270768L, 22666616833L});
   public static final BitSet FOLLOW_element_in_element973 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_RANGE_in_element987 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_element991 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_element995 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ebnf_in_element1024 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_atom_in_element1035 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_tree__in_element1046 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_element_action_in_element1056 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SEMPRED_in_element1071 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_GATED_SEMPRED_in_element1075 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYN_SEMPRED_in_element1086 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYNPRED_in_element1094 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_element1105 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_EPSILON_in_element1117 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ACTION_in_element_action1134 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FORCED_ACTION_in_element_action1145 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_notElement1174 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_notElement1187 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_notElement1200 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BLOCK_in_notElement1214 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1261 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPTIONAL_in_ebnf1280 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1284 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CLOSURE_in_ebnf1305 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1309 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_ebnf1330 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1334 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_BEGIN_in_tree_1372 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_tree_1379 = new BitSet(new long[]{-9043225268081270760L, 22666616833L});
   public static final BitSet FOLLOW_element_action_in_tree_1416 = new BitSet(new long[]{-9043225268081270760L, 22666616833L});
   public static final BitSet FOLLOW_element_in_tree_1438 = new BitSet(new long[]{-9043225268081270760L, 22666616833L});
   public static final BitSet FOLLOW_RULE_REF_in_atom1488 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1493 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TOKEN_REF_in_atom1511 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1516 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom1532 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_atom1544 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_WILDCARD_in_atom1556 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_DOT_in_atom1567 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_atom1569 = new BitSet(new long[]{537198592L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_atom1573 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_set_in_atom1586 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BLOCK_in_set1631 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_setElement1651 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_setElement1656 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_setElement1661 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_RANGE_in_setElement1667 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_setElement1669 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_setElement1671 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_REWRITES_in_rewrite1696 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_REWRITE_in_rewrite1717 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_SEMPRED_in_rewrite1722 = new BitSet(new long[]{137438953744L, 134217728L});
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite1728 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BLOCK_in_rewrite_block1771 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite_block1783 = new BitSet(new long[]{8589934592L});
   public static final BitSet FOLLOW_EOB_in_rewrite_block1788 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_rewrite_alternative1823 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_element_in_rewrite_alternative1841 = new BitSet(new long[]{144396667349893136L, 5385551873L});
   public static final BitSet FOLLOW_EPSILON_in_rewrite_alternative1862 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_rewrite_alternative1878 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_rewrite_template_in_rewrite_alternative1891 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ETC_in_rewrite_alternative1904 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_atom_in_rewrite_element1924 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_ebnf_in_rewrite_element1934 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_tree_in_rewrite_element1943 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPTIONAL_in_rewrite_ebnf1964 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_block_in_rewrite_ebnf1966 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CLOSURE_in_rewrite_ebnf1984 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_block_in_rewrite_ebnf1986 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_rewrite_ebnf2004 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_block_in_rewrite_ebnf2006 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_BEGIN_in_rewrite_tree2039 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_atom_in_rewrite_tree2046 = new BitSet(new long[]{144396663054925848L, 5385551873L});
   public static final BitSet FOLLOW_rewrite_element_in_rewrite_tree2066 = new BitSet(new long[]{144396663054925848L, 5385551873L});
   public static final BitSet FOLLOW_RULE_REF_in_rewrite_atom2111 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_atom2128 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rewrite_atom2133 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_rewrite_atom2144 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_rewrite_atom2152 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LABEL_in_rewrite_atom2166 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_atom2176 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ALT_in_rewrite_template2199 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_EPSILON_in_rewrite_template2201 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_rewrite_template2203 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TEMPLATE_in_rewrite_template2214 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rewrite_template2219 = new BitSet(new long[]{2048L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_template2223 = new BitSet(new long[]{2048L});
   public static final BitSet FOLLOW_ARGLIST_in_rewrite_template2236 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_in_rewrite_template2246 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rewrite_template2250 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_template2254 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template2287 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template2300 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_template2324 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_set_in_synpred1_CodeGenTreeWalker1009 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_element_action_in_synpred2_CodeGenTreeWalker1405 = new BitSet(new long[]{2L});

   public TreeParser[] getDelegates() {
      return new TreeParser[0];
   }

   public CodeGenTreeWalker(TreeNodeStream input) {
      this(input, new RecognizerSharedState());
   }

   public CodeGenTreeWalker(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
      this.currentRuleName = null;
      this.blockNestingLevel = 0;
      this.rewriteBlockNestingLevel = 0;
      this.outerAltNum = 0;
      this.currentBlockST = null;
      this.currentAltHasASTRewrite = false;
      this.rewriteTreeNestingLevel = 0;
      this.rewriteRuleRefs = null;
      this.outputOption = "";
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\antlr\\grammar\\v3\\CodeGenTreeWalker.g";
   }

   public String getCurrentRuleName() {
      return this.currentRuleName;
   }

   public void setCurrentRuleName(String value) {
      this.currentRuleName = value;
   }

   public int getOuterAltNum() {
      return this.outerAltNum;
   }

   public void setOuterAltNum(int value) {
      this.outerAltNum = value;
   }

   public void reportError(RecognitionException ex) {
      Token token = null;
      if (ex instanceof MismatchedTokenException) {
         token = ((MismatchedTokenException)ex).token;
      } else if (ex instanceof NoViableAltException) {
         token = ((NoViableAltException)ex).token;
      }

      ErrorManager.syntaxError(100, this.grammar, token, "codegen: " + ex.toString(), ex);
   }

   public final void reportError(String s) {
      System.out.println("codegen: error: " + s);
   }

   protected final ST getWildcardST(GrammarAST elementAST, GrammarAST ast_suffix, String label) {
      String name = "wildcard";
      if (this.grammar.type == 1) {
         name = "wildcardChar";
      }

      return this.getTokenElementST(name, name, elementAST, ast_suffix, label);
   }

   protected final ST getRuleElementST(String name, String ruleTargetName, GrammarAST elementAST, GrammarAST ast_suffix, String label) {
      Rule r = this.grammar.getRule(this.currentRuleName);
      String suffix = this.getSTSuffix(elementAST, ast_suffix, label);
      if (!r.isSynPred) {
         name = name + suffix;
      }

      if ((this.grammar.buildAST() || suffix.length() > 0) && label == null && (r == null || !r.isSynPred)) {
         label = this.generator.createUniqueLabel(ruleTargetName);
         CommonToken labelTok = new CommonToken(43, label);
         this.grammar.defineRuleRefLabel(this.currentRuleName, labelTok, elementAST);
      }

      ST elementST = this.templates.getInstanceOf(name);
      if (label != null) {
         elementST.add("label", label);
      }

      return elementST;
   }

   protected final ST getTokenElementST(String name, String elementName, GrammarAST elementAST, GrammarAST ast_suffix, String label) {
      boolean tryUnchecked = false;
      if (name == "matchSet" && elementAST.enclosingRuleName != null && elementAST.enclosingRuleName.length() > 0 && Rule.getRuleType(elementAST.enclosingRuleName) == 1 && (elementAST.getParent().getType() != 8 || elementAST.getParent().getParent().getParent().getType() != 79 || elementAST.getParent().getParent().getChildCount() != 2) && (elementAST.getParent().getType() != 55 || elementAST.getParent().getParent().getParent().getParent().getType() != 79 || elementAST.getParent().getParent().getParent().getChildCount() != 2)) {
         tryUnchecked = true;
      }

      String suffix = this.getSTSuffix(elementAST, ast_suffix, label);
      Rule r = this.grammar.getRule(this.currentRuleName);
      if ((this.grammar.buildAST() || suffix.length() > 0) && label == null && (r == null || !r.isSynPred)) {
         label = this.generator.createUniqueLabel(elementName);
         CommonToken labelTok = new CommonToken(43, label);
         this.grammar.defineTokenRefLabel(this.currentRuleName, labelTok, elementAST);
      }

      ST elementST = null;
      if (tryUnchecked && this.templates.isDefined(name + "Unchecked" + suffix)) {
         elementST = this.templates.getInstanceOf(name + "Unchecked" + suffix);
      }

      if (elementST == null) {
         elementST = this.templates.getInstanceOf(name + suffix);
      }

      if (label != null) {
         elementST.add("label", label);
      }

      return elementST;
   }

   public final boolean isListLabel(String label) {
      boolean hasListLabel = false;
      if (label != null) {
         Rule r = this.grammar.getRule(this.currentRuleName);
         if (r != null) {
            Grammar.LabelElementPair pair = r.getLabel(label);
            if (pair != null && (pair.type == 4 || pair.type == 3 || pair.type == 7)) {
               hasListLabel = true;
            }
         }
      }

      return hasListLabel;
   }

   protected final String getSTSuffix(GrammarAST elementAST, GrammarAST ast_suffix, String label) {
      if (this.grammar.type == 1) {
         return "";
      } else {
         String operatorPart = "";
         String rewritePart = "";
         String listLabelPart = "";
         Rule ruleDescr = this.grammar.getRule(this.currentRuleName);
         if (ast_suffix != null && !ruleDescr.isSynPred) {
            if (ast_suffix.getType() == 77) {
               operatorPart = "RuleRoot";
            } else if (ast_suffix.getType() == 15) {
               operatorPart = "Bang";
            }
         }

         if (this.currentAltHasASTRewrite && elementAST.getType() != 98) {
            rewritePart = "Track";
         }

         if (this.isListLabel(label)) {
            listLabelPart = "AndListLabel";
         }

         String STsuffix = operatorPart + rewritePart + listLabelPart;
         return STsuffix;
      }
   }

   protected final List getTokenTypesAsTargetLabels(Collection refs) {
      if (refs != null && refs.size() != 0) {
         List labels = new ArrayList(refs.size());

         String label;
         for(Iterator i$ = refs.iterator(); i$.hasNext(); labels.add(label)) {
            GrammarAST t = (GrammarAST)i$.next();
            if (t.getType() != 80 && t.getType() != 94 && t.getType() != 48) {
               label = this.generator.getTokenTypeAsTargetLabel(this.grammar.getTokenType(t.getText()));
            } else {
               label = t.getText();
            }
         }

         return labels;
      } else {
         return null;
      }
   }

   public final void init(Grammar g) {
      this.grammar = g;
      this.generator = this.grammar.getCodeGenerator();
      this.templates = this.generator.getTemplates();
   }

   public final void grammar_(Grammar g, ST recognizerST, ST outputFileST, ST headerFileST) throws RecognitionException {
      if (this.state.backtracking == 0) {
         this.init(g);
         this.recognizerST = recognizerST;
         this.outputFileST = outputFileST;
         this.headerFileST = headerFileST;
         String superClass = (String)g.getOption("superClass");
         this.outputOption = (String)g.getOption("output");
         if (superClass != null) {
            recognizerST.add("superClass", superClass);
         }

         Object lt;
         if (g.type != 1) {
            lt = g.getOption("ASTLabelType");
            if (lt != null) {
               recognizerST.add("ASTLabelType", lt);
            }
         }

         if (g.type == 3 && g.getOption("ASTLabelType") == null) {
            ErrorManager.grammarWarning(152, g, (Token)null, g.name);
         }

         if (g.type != 3) {
            lt = g.getOption("TokenLabelType");
            if (lt != null) {
               recognizerST.add("labelType", lt);
            }
         }

         recognizerST.add("numRules", this.grammar.getRules().size());
         outputFileST.add("numRules", this.grammar.getRules().size());
         headerFileST.add("numRules", this.grammar.getRules().size());
      }

      try {
         try {
            int alt1 = true;
            byte alt1;
            switch (this.input.LA(1)) {
               case 23:
                  alt1 = 4;
                  break;
               case 50:
                  alt1 = 1;
                  break;
               case 61:
                  alt1 = 2;
                  break;
               case 97:
                  alt1 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 1, 0, this.input);
                  throw nvae;
            }

            switch (alt1) {
               case 1:
                  this.match(this.input, 50, FOLLOW_LEXER_GRAMMAR_in_grammar_67);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_69);
                  this.grammarSpec();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.match(this.input, 61, FOLLOW_PARSER_GRAMMAR_in_grammar_79);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_81);
                  this.grammarSpec();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 3:
                  this.match(this.input, 97, FOLLOW_TREE_GRAMMAR_in_grammar_91);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_93);
                  this.grammarSpec();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 4:
                  this.match(this.input, 23, FOLLOW_COMBINED_GRAMMAR_in_grammar_103);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_105);
                  this.grammarSpec();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

      } finally {
         ;
      }
   }

   public final void attrScope() throws RecognitionException {
      try {
         this.match(this.input, 81, FOLLOW_SCOPE_in_attrScope124);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               this.match(this.input, 43, FOLLOW_ID_in_attrScope126);
               if (!this.state.failed) {
                  label185:
                  while(true) {
                     do {
                        int alt3 = 2;
                        int LA3_0 = this.input.LA(1);
                        if (LA3_0 == 9) {
                           alt3 = 1;
                        }

                        switch (alt3) {
                           case 1:
                              this.match(this.input, 9, FOLLOW_AMPERSAND_in_attrScope131);
                              if (this.state.failed) {
                                 return;
                              }
                              break;
                           default:
                              this.match(this.input, 4, FOLLOW_ACTION_in_attrScope140);
                              if (this.state.failed) {
                                 return;
                              }

                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return;
                              }

                              return;
                        }
                     } while(this.input.LA(1) != 2);

                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     while(true) {
                        int alt2 = 2;
                        int LA2_0 = this.input.LA(1);
                        if (LA2_0 >= 4 && LA2_0 <= 102) {
                           alt2 = 1;
                        } else if (LA2_0 == 3) {
                           alt2 = 2;
                        }

                        switch (alt2) {
                           case 1:
                              this.matchAny(this.input);
                              if (this.state.failed) {
                                 return;
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return;
                              }
                              continue label185;
                        }
                     }
                  }
               }
            }
         }
      } catch (RecognitionException var8) {
         this.reportError(var8);
         this.recover(this.input, var8);
      } finally {
         ;
      }
   }

   public final void grammarSpec() throws RecognitionException {
      GrammarAST name = null;
      GrammarAST cmt = null;

      try {
         name = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_grammarSpec157);
         if (!this.state.failed) {
            int alt4 = 2;
            int LA4_0 = this.input.LA(1);
            if (LA4_0 == 27) {
               alt4 = 1;
            }

            switch (alt4) {
               case 1:
                  cmt = (GrammarAST)this.match(this.input, 27, FOLLOW_DOC_COMMENT_in_grammarSpec165);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     this.outputFileST.add("docComment", cmt != null ? cmt.getText() : null);
                     this.headerFileST.add("docComment", cmt != null ? cmt.getText() : null);
                  }
            }

            if (this.state.backtracking == 0) {
               this.recognizerST.add("name", this.grammar.getRecognizerName());
               this.outputFileST.add("name", this.grammar.getRecognizerName());
               this.headerFileST.add("name", this.grammar.getRecognizerName());
               this.recognizerST.add("scopes", this.grammar.getGlobalScopes());
               this.headerFileST.add("scopes", this.grammar.getGlobalScopes());
            }

            int alt6 = 2;
            int LA6_0 = this.input.LA(1);
            if (LA6_0 == 58) {
               alt6 = 1;
            }

            byte alt5;
            int LA5_0;
            switch (alt6) {
               case 1:
                  this.match(this.input, 58, FOLLOW_OPTIONS_in_grammarSpec186);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     label525:
                     while(true) {
                        alt5 = 2;
                        LA5_0 = this.input.LA(1);
                        if (LA5_0 >= 4 && LA5_0 <= 102) {
                           alt5 = 1;
                        } else if (LA5_0 == 3) {
                           alt5 = 2;
                        }

                        switch (alt5) {
                           case 1:
                              this.matchAny(this.input);
                              if (this.state.failed) {
                                 return;
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return;
                              }
                              break label525;
                        }
                     }
                  }
            }

            alt5 = 2;
            LA5_0 = this.input.LA(1);
            if (LA5_0 == 45) {
               alt5 = 1;
            }

            byte alt7;
            int LA7_0;
            switch (alt5) {
               case 1:
                  this.match(this.input, 45, FOLLOW_IMPORT_in_grammarSpec200);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     label508:
                     while(true) {
                        alt7 = 2;
                        LA7_0 = this.input.LA(1);
                        if (LA7_0 >= 4 && LA7_0 <= 102) {
                           alt7 = 1;
                        } else if (LA7_0 == 3) {
                           alt7 = 2;
                        }

                        switch (alt7) {
                           case 1:
                              this.matchAny(this.input);
                              if (this.state.failed) {
                                 return;
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return;
                              }
                              break label508;
                        }
                     }
                  }
            }

            alt7 = 2;
            LA7_0 = this.input.LA(1);
            if (LA7_0 == 93) {
               alt7 = 1;
            }

            byte alt9;
            int LA9_0;
            switch (alt7) {
               case 1:
                  this.match(this.input, 93, FOLLOW_TOKENS_in_grammarSpec214);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     label456:
                     while(true) {
                        alt9 = 2;
                        LA9_0 = this.input.LA(1);
                        if (LA9_0 >= 4 && LA9_0 <= 102) {
                           alt9 = 1;
                        } else if (LA9_0 == 3) {
                           alt9 = 2;
                        }

                        switch (alt9) {
                           case 1:
                              this.matchAny(this.input);
                              if (this.state.failed) {
                                 return;
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return;
                              }
                              break label456;
                        }
                     }
                  }
            }

            while(true) {
               alt9 = 2;
               LA9_0 = this.input.LA(1);
               if (LA9_0 == 81) {
                  alt9 = 1;
               }

               switch (alt9) {
                  case 1:
                     this.pushFollow(FOLLOW_attrScope_in_grammarSpec226);
                     this.attrScope();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  default:
                     label492:
                     while(true) {
                        do {
                           alt9 = 2;
                           LA9_0 = this.input.LA(1);
                           if (LA9_0 == 9) {
                              alt9 = 1;
                           }

                           switch (alt9) {
                              case 1:
                                 this.match(this.input, 9, FOLLOW_AMPERSAND_in_grammarSpec235);
                                 if (this.state.failed) {
                                    return;
                                 }
                                 break;
                              default:
                                 this.pushFollow(FOLLOW_rules_in_grammarSpec246);
                                 this.rules(this.recognizerST);
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return;
                                 }

                                 return;
                           }
                        } while(this.input.LA(1) != 2);

                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        while(true) {
                           int alt12 = 2;
                           int LA12_0 = this.input.LA(1);
                           if (LA12_0 >= 4 && LA12_0 <= 102) {
                              alt12 = 1;
                           } else if (LA12_0 == 3) {
                              alt12 = 2;
                           }

                           switch (alt12) {
                              case 1:
                                 this.matchAny(this.input);
                                 if (this.state.failed) {
                                    return;
                                 }
                                 break;
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                                 if (this.state.failed) {
                                    return;
                                 }
                                 continue label492;
                           }
                        }
                     }
               }
            }
         }
      } catch (RecognitionException var18) {
         this.reportError(var18);
         this.recover(this.input, var18);
      } finally {
         ;
      }
   }

   public final void rules(ST recognizerST) throws RecognitionException {
      TreeRuleReturnScope rST = null;
      String ruleName = ((GrammarAST)this.input.LT(1)).getChild(0).getText();
      boolean generated = this.grammar.generateMethodForRule(ruleName);

      try {
         int cnt17 = 0;

         while(true) {
            int alt17 = 2;
            int LA17_0 = this.input.LA(1);
            if (LA17_0 == 65 || LA17_0 == 79) {
               alt17 = 1;
            }

            label337:
            switch (alt17) {
               case 1:
                  int alt16 = true;
                  int LA16_0 = this.input.LA(1);
                  byte alt16;
                  if (LA16_0 == 79) {
                     int LA16_1 = this.input.LA(2);
                     if (generated) {
                        alt16 = 1;
                     } else {
                        alt16 = 2;
                     }
                  } else {
                     if (LA16_0 != 65) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
                        throw nvae;
                     }

                     alt16 = 3;
                  }

                  int LA15_0;
                  byte alt15;
                  switch (alt16) {
                     case 1:
                        if (!generated) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           throw new FailedPredicateException(this.input, "rules", "generated");
                        }

                        this.pushFollow(FOLLOW_rule_in_rules291);
                        rST = this.rule();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }

                        if (this.state.backtracking == 0 && (rST != null ? ((rule_return)rST).code : null) != null) {
                           recognizerST.add("rules", rST != null ? ((rule_return)rST).code : null);
                           this.outputFileST.add("rules", rST != null ? ((rule_return)rST).code : null);
                           this.headerFileST.add("rules", rST != null ? ((rule_return)rST).code : null);
                        }
                        break label337;
                     case 2:
                        this.match(this.input, 79, FOLLOW_RULE_in_rules305);
                        if (this.state.failed) {
                           return;
                        }

                        if (this.input.LA(1) != 2) {
                           break label337;
                        }

                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        while(true) {
                           alt15 = 2;
                           LA15_0 = this.input.LA(1);
                           if (LA15_0 >= 4 && LA15_0 <= 102) {
                              alt15 = 1;
                           } else if (LA15_0 == 3) {
                              alt15 = 2;
                           }

                           switch (alt15) {
                              case 1:
                                 this.matchAny(this.input);
                                 if (this.state.failed) {
                                    return;
                                 }
                                 break;
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                                 if (this.state.failed) {
                                    return;
                                 }
                                 break label337;
                           }
                        }
                     case 3:
                        this.match(this.input, 65, FOLLOW_PREC_RULE_in_rules317);
                        if (this.state.failed) {
                           return;
                        }

                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           if (this.state.failed) {
                              return;
                           }

                           while(true) {
                              alt15 = 2;
                              LA15_0 = this.input.LA(1);
                              if (LA15_0 >= 4 && LA15_0 <= 102) {
                                 alt15 = 1;
                              } else if (LA15_0 == 3) {
                                 alt15 = 2;
                              }

                              switch (alt15) {
                                 case 1:
                                    this.matchAny(this.input);
                                    if (this.state.failed) {
                                       return;
                                    }
                                    break;
                                 default:
                                    this.match(this.input, 3, (BitSet)null);
                                    if (this.state.failed) {
                                       return;
                                    }
                                    break label337;
                              }
                           }
                        }
                     default:
                        break label337;
                  }
               default:
                  if (cnt17 < 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     EarlyExitException eee = new EarlyExitException(17, this.input);
                     throw eee;
                  }

                  return;
            }

            if (this.input.LA(1) == 79) {
               ruleName = ((GrammarAST)this.input.LT(1)).getChild(0).getText();
               generated = this.grammar.generateMethodForRule(ruleName);
            }

            ++cnt17;
         }
      } catch (RecognitionException var15) {
         this.reportError(var15);
         this.recover(this.input, var15);
      } finally {
         ;
      }
   }

   public final rule_return rule() throws RecognitionException {
      rule_return retval = new rule_return();
      retval.start = this.input.LT(1);
      GrammarAST id = null;
      TreeRuleReturnScope mod = null;
      TreeRuleReturnScope b = null;
      String initAction = null;
      GrammarAST block2 = (GrammarAST)((GrammarAST)retval.start).getFirstChildWithType(16);
      DFA dfa = block2.getLookaheadDFA();
      this.blockNestingLevel = -1;
      Rule ruleDescr = this.grammar.getRule(((GrammarAST)retval.start).getChild(0).getText());
      this.currentRuleName = ((GrammarAST)retval.start).getChild(0).getText();
      STGroup saveGroup = this.templates;
      if (ruleDescr.isSynPred && this.generator.target.useBaseTemplatesForSynPredFragments()) {
         this.templates = this.generator.getBaseTemplates();
      }

      String description = "";

      try {
         this.match(this.input, 79, FOLLOW_RULE_in_rule359);
         rule_return var29;
         if (this.state.failed) {
            var29 = retval;
            return var29;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               var29 = retval;
               return var29;
            } else {
               id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rule363);
               if (this.state.failed) {
                  var29 = retval;
                  return var29;
               } else {
                  assert this.state.backtracking != 0 || this.currentRuleName == (id != null ? id.getText() : null);

                  int alt18 = 2;
                  int LA18_0 = this.input.LA(1);
                  if (LA18_0 == 40 || LA18_0 >= 66 && LA18_0 <= 68) {
                     alt18 = 1;
                  }

                  rule_return var13;
                  switch (alt18) {
                     case 1:
                        this.pushFollow(FOLLOW_modifier_in_rule376);
                        mod = this.modifier();
                        --this.state._fsp;
                        if (this.state.failed) {
                           var13 = retval;
                           return var13;
                        }
                  }

                  this.match(this.input, 10, FOLLOW_ARG_in_rule384);
                  if (this.state.failed) {
                     var13 = retval;
                     return var13;
                  } else {
                     int LA20_0;
                     rule_return var15;
                     byte alt20;
                     if (this.input.LA(1) == 2) {
                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           var13 = retval;
                           return var13;
                        }

                        alt20 = 2;
                        LA20_0 = this.input.LA(1);
                        if (LA20_0 == 12) {
                           alt20 = 1;
                        }

                        switch (alt20) {
                           case 1:
                              this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rule387);
                              if (this.state.failed) {
                                 var15 = retval;
                                 return var15;
                              }
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 var15 = retval;
                                 return var15;
                              }
                        }
                     }

                     this.match(this.input, 73, FOLLOW_RET_in_rule396);
                     if (this.state.failed) {
                        var13 = retval;
                        return var13;
                     } else {
                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           if (this.state.failed) {
                              var13 = retval;
                              return var13;
                           }

                           alt20 = 2;
                           LA20_0 = this.input.LA(1);
                           if (LA20_0 == 12) {
                              alt20 = 1;
                           }

                           switch (alt20) {
                              case 1:
                                 this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rule399);
                                 if (this.state.failed) {
                                    var15 = retval;
                                    return var15;
                                 }
                           }

                           this.match(this.input, 3, (BitSet)null);
                           if (this.state.failed) {
                              var15 = retval;
                              return var15;
                           }
                        }

                        alt20 = 2;
                        LA20_0 = this.input.LA(1);
                        if (LA20_0 == 92) {
                           alt20 = 1;
                        }

                        switch (alt20) {
                           case 1:
                              this.pushFollow(FOLLOW_throwsSpec_in_rule408);
                              this.throwsSpec();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 var15 = retval;
                                 return var15;
                              }
                        }

                        int alt23 = 2;
                        int LA23_0 = this.input.LA(1);
                        if (LA23_0 == 58) {
                           alt23 = 1;
                        }

                        byte alt22;
                        int LA22_0;
                        rule_return var19;
                        switch (alt23) {
                           case 1:
                              this.match(this.input, 58, FOLLOW_OPTIONS_in_rule418);
                              rule_return var32;
                              if (this.state.failed) {
                                 var32 = retval;
                                 return var32;
                              }

                              if (this.input.LA(1) == 2) {
                                 this.match(this.input, 2, (BitSet)null);
                                 if (this.state.failed) {
                                    var32 = retval;
                                    return var32;
                                 }

                                 label853:
                                 while(true) {
                                    alt22 = 2;
                                    LA22_0 = this.input.LA(1);
                                    if (LA22_0 >= 4 && LA22_0 <= 102) {
                                       alt22 = 1;
                                    } else if (LA22_0 == 3) {
                                       alt22 = 2;
                                    }

                                    switch (alt22) {
                                       case 1:
                                          this.matchAny(this.input);
                                          if (this.state.failed) {
                                             var19 = retval;
                                             return var19;
                                          }
                                          break;
                                       default:
                                          this.match(this.input, 3, (BitSet)null);
                                          if (this.state.failed) {
                                             var32 = retval;
                                             return var32;
                                          }
                                          break label853;
                                    }
                                 }
                              }
                        }

                        alt22 = 2;
                        LA22_0 = this.input.LA(1);
                        if (LA22_0 == 81) {
                           alt22 = 1;
                        }

                        switch (alt22) {
                           case 1:
                              this.pushFollow(FOLLOW_ruleScopeSpec_in_rule431);
                              this.ruleScopeSpec();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 var19 = retval;
                                 return var19;
                              }
                        }

                        label873:
                        while(true) {
                           int alt27 = 2;
                           int LA27_0 = this.input.LA(1);
                           if (LA27_0 == 9) {
                              alt27 = 1;
                           }

                           rule_return var36;
                           switch (alt27) {
                              case 1:
                                 this.match(this.input, 9, FOLLOW_AMPERSAND_in_rule441);
                                 if (this.state.failed) {
                                    var36 = retval;
                                    return var36;
                                 }

                                 if (this.input.LA(1) != 2) {
                                    break;
                                 }

                                 this.match(this.input, 2, (BitSet)null);
                                 if (this.state.failed) {
                                    var36 = retval;
                                    return var36;
                                 }

                                 while(true) {
                                    int alt25 = 2;
                                    int LA25_0 = this.input.LA(1);
                                    if (LA25_0 >= 4 && LA25_0 <= 102) {
                                       alt25 = 1;
                                    } else if (LA25_0 == 3) {
                                       alt25 = 2;
                                    }

                                    switch (alt25) {
                                       case 1:
                                          this.matchAny(this.input);
                                          if (this.state.failed) {
                                             rule_return var23 = retval;
                                             return var23;
                                          }
                                          break;
                                       default:
                                          this.match(this.input, 3, (BitSet)null);
                                          if (this.state.failed) {
                                             var36 = retval;
                                             return var36;
                                          }
                                          continue label873;
                                    }
                                 }
                              default:
                                 this.pushFollow(FOLLOW_block_in_rule455);
                                 b = this.block("ruleBlock", dfa, (GrammarAST)null);
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    var19 = retval;
                                    return var19;
                                 }

                                 if (this.state.backtracking == 0) {
                                    description = this.grammar.grammarTreeToString((GrammarAST)((GrammarAST)retval.start).getFirstChildWithType(16), false);
                                    description = this.generator.target.getTargetStringLiteralFromString(description);
                                    (b != null ? ((block_return)b).code : null).add("description", description);
                                    String stName = null;
                                    if (ruleDescr.isSynPred) {
                                       stName = "synpredRule";
                                    } else if (this.grammar.type == 1) {
                                       if (this.currentRuleName.equals("Tokens")) {
                                          stName = "tokensRule";
                                       } else {
                                          stName = "lexerRule";
                                       }
                                    } else if (this.grammar.type != 4 || Rule.getRuleType(this.currentRuleName) != 1) {
                                       stName = "rule";
                                    }

                                    retval.code = this.templates.getInstanceOf(stName);
                                    if (retval.code.getName().equals("/rule")) {
                                       retval.code.add("emptyRule", this.grammar.isEmptyRule(block2));
                                    }

                                    retval.code.add("ruleDescriptor", ruleDescr);
                                    String memo = (String)this.grammar.getBlockOption((GrammarAST)retval.start, "memoize");
                                    if (memo == null) {
                                       memo = (String)this.grammar.getOption("memoize");
                                    }

                                    if (memo != null && memo.equals("true") && (stName.equals("rule") || stName.equals("lexerRule"))) {
                                       retval.code.add("memoize", memo != null && memo.equals("true"));
                                    }
                                 }

                                 alt27 = 2;
                                 LA27_0 = this.input.LA(1);
                                 if (LA27_0 == 17 || LA27_0 == 38) {
                                    alt27 = 1;
                                 }

                                 switch (alt27) {
                                    case 1:
                                       this.pushFollow(FOLLOW_exceptionGroup_in_rule468);
                                       this.exceptionGroup(retval.code);
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          var36 = retval;
                                          return var36;
                                       }
                                    default:
                                       this.match(this.input, 34, FOLLOW_EOR_in_rule476);
                                       if (this.state.failed) {
                                          var36 = retval;
                                          return var36;
                                       }

                                       this.match(this.input, 3, (BitSet)null);
                                       if (this.state.failed) {
                                          var36 = retval;
                                          return var36;
                                       }

                                       if (this.state.backtracking == 0 && retval.code != null) {
                                          if (this.grammar.type != 1) {
                                             description = this.grammar.grammarTreeToString((GrammarAST)retval.start, false);
                                             description = this.generator.target.getTargetStringLiteralFromString(description);
                                             retval.code.add("description", description);
                                          } else {
                                             boolean naked = this.currentRuleName.equals("Tokens") || (mod != null ? (GrammarAST)mod.start : null) != null && (mod != null ? (GrammarAST)mod.start : null).getText().equals("fragment");
                                             retval.code.add("nakedBlock", naked);
                                          }

                                          Rule theRule = this.grammar.getRule(this.currentRuleName);
                                          this.generator.translateActionAttributeReferencesForSingleScope(theRule, theRule.getActions());
                                          retval.code.add("ruleName", this.currentRuleName);
                                          retval.code.add("block", b != null ? ((block_return)b).code : null);
                                          if (initAction != null) {
                                             retval.code.add("initAction", initAction);
                                             return retval;
                                          }
                                       }

                                       return retval;
                                 }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (RecognitionException var27) {
         this.reportError(var27);
         this.recover(this.input, var27);
         return retval;
      } finally {
         this.templates = saveGroup;
      }
   }

   public final modifier_return modifier() throws RecognitionException {
      modifier_return retval = new modifier_return();
      retval.start = this.input.LT(1);

      try {
         try {
            if (this.input.LA(1) != 40 && (this.input.LA(1) < 66 || this.input.LA(1) > 68)) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return retval;
               }

               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               throw mse;
            }

            this.input.consume();
            this.state.errorRecovery = false;
            this.state.failed = false;
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void throwsSpec() throws RecognitionException {
      try {
         this.match(this.input, 92, FOLLOW_THROWS_in_throwsSpec526);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int cnt28 = 0;

               while(true) {
                  int alt28 = 2;
                  int LA28_0 = this.input.LA(1);
                  if (LA28_0 == 43) {
                     alt28 = 1;
                  }

                  switch (alt28) {
                     case 1:
                        this.match(this.input, 43, FOLLOW_ID_in_throwsSpec528);
                        if (this.state.failed) {
                           return;
                        }

                        ++cnt28;
                        break;
                     default:
                        if (cnt28 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(28, this.input);
                           throw eee;
                        }

                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        return;
                  }
               }
            }
         }
      } catch (RecognitionException var8) {
         this.reportError(var8);
         this.recover(this.input, var8);
      } finally {
         ;
      }
   }

   public final void ruleScopeSpec() throws RecognitionException {
      try {
         try {
            this.match(this.input, 81, FOLLOW_SCOPE_in_ruleScopeSpec543);
            if (this.state.failed) {
               return;
            }

            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);
               if (this.state.failed) {
                  return;
               }

               label225:
               while(true) {
                  byte alt32;
                  int LA32_0;
                  do {
                     int alt31 = 2;
                     int LA31_0 = this.input.LA(1);
                     if (LA31_0 == 9) {
                        alt31 = 1;
                     }

                     switch (alt31) {
                        case 1:
                           this.match(this.input, 9, FOLLOW_AMPERSAND_in_ruleScopeSpec548);
                           if (this.state.failed) {
                              return;
                           }
                           break;
                        default:
                           alt31 = 2;
                           LA31_0 = this.input.LA(1);
                           if (LA31_0 == 4) {
                              alt31 = 1;
                           }

                           switch (alt31) {
                              case 1:
                                 this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec558);
                                 if (this.state.failed) {
                                    return;
                                 }
                           }

                           while(true) {
                              alt32 = 2;
                              LA32_0 = this.input.LA(1);
                              if (LA32_0 == 43) {
                                 alt32 = 1;
                              }

                              switch (alt32) {
                                 case 1:
                                    this.match(this.input, 43, FOLLOW_ID_in_ruleScopeSpec564);
                                    if (this.state.failed) {
                                       return;
                                    }
                                    break;
                                 default:
                                    this.match(this.input, 3, (BitSet)null);
                                    if (this.state.failed) {
                                       return;
                                    }

                                    return;
                              }
                           }
                     }
                  } while(this.input.LA(1) != 2);

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  while(true) {
                     alt32 = 2;
                     LA32_0 = this.input.LA(1);
                     if (LA32_0 >= 4 && LA32_0 <= 102) {
                        alt32 = 1;
                     } else if (LA32_0 == 3) {
                        alt32 = 2;
                     }

                     switch (alt32) {
                        case 1:
                           this.matchAny(this.input);
                           if (this.state.failed) {
                              return;
                           }
                           break;
                        default:
                           this.match(this.input, 3, (BitSet)null);
                           if (this.state.failed) {
                              return;
                           }
                           continue label225;
                     }
                  }
               }
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final block_return block(String blockTemplateName, DFA dfa, GrammarAST label) throws RecognitionException {
      block_return retval = new block_return();
      retval.start = this.input.LT(1);
      TreeRuleReturnScope alt = null;
      TreeRuleReturnScope rew = null;
      TreeRuleReturnScope setBlock1 = null;
      int altNum = 0;
      ++this.blockNestingLevel;
      if (this.state.backtracking == 0) {
         ST decision = null;
         if (dfa != null) {
            retval.code = this.templates.getInstanceOf(blockTemplateName);
            decision = this.generator.genLookaheadDecision(this.recognizerST, dfa);
            retval.code.add("decision", decision);
            retval.code.add("decisionNumber", dfa.getDecisionNumber());
            retval.code.add("maxK", dfa.getMaxLookaheadDepth());
            retval.code.add("maxAlt", dfa.getNumberOfAlts());
         } else {
            retval.code = this.templates.getInstanceOf(blockTemplateName + "SingleAlt");
         }

         retval.code.add("blockLevel", this.blockNestingLevel);
         retval.code.add("enclosingBlockLevel", this.blockNestingLevel - 1);
         altNum = 1;
         if (this.blockNestingLevel == 0) {
            this.outerAltNum = 1;
         }
      }

      try {
         int alt36 = true;
         int LA36_0 = this.input.LA(1);
         block_return var26;
         if (LA36_0 != 16) {
            if (this.state.backtracking > 0) {
               this.state.failed = true;
               var26 = retval;
               return var26;
            }

            NoViableAltException nvae = new NoViableAltException("", 36, 0, this.input);
            throw nvae;
         }

         int LA36_1 = this.input.LA(2);
         byte alt36;
         if (((GrammarAST)retval.start).getSetValue() != null) {
            alt36 = 1;
         } else {
            alt36 = 2;
         }

         switch (alt36) {
            case 1:
               if (((GrammarAST)retval.start).getSetValue() == null) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     var26 = retval;
                     return var26;
                  }

                  throw new FailedPredicateException(this.input, "block", "$start.getSetValue()!=null");
               }

               this.pushFollow(FOLLOW_setBlock_in_block605);
               setBlock1 = this.setBlock();
               --this.state._fsp;
               if (this.state.failed) {
                  var26 = retval;
                  return var26;
               }

               if (this.state.backtracking == 0) {
                  retval.code.add("alts", setBlock1 != null ? ((setBlock_return)setBlock1).code : null);
               }
               break;
            case 2:
               this.match(this.input, 16, FOLLOW_BLOCK_in_block618);
               if (this.state.failed) {
                  var26 = retval;
                  return var26;
               }

               this.match(this.input, 2, (BitSet)null);
               if (this.state.failed) {
                  var26 = retval;
                  return var26;
               }

               int alt34 = 2;
               int LA34_0 = this.input.LA(1);
               if (LA34_0 == 58) {
                  alt34 = 1;
               }

               int alt33;
               switch (alt34) {
                  case 1:
                     this.match(this.input, 58, FOLLOW_OPTIONS_in_block626);
                     block_return var28;
                     if (this.state.failed) {
                        var28 = retval;
                        return var28;
                     }

                     if (this.input.LA(1) == 2) {
                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           var28 = retval;
                           return var28;
                        }

                        label533:
                        while(true) {
                           alt33 = 2;
                           int LA33_0 = this.input.LA(1);
                           if (LA33_0 >= 4 && LA33_0 <= 102) {
                              alt33 = 1;
                           } else if (LA33_0 == 3) {
                              alt33 = 2;
                           }

                           switch (alt33) {
                              case 1:
                                 this.matchAny(this.input);
                                 if (this.state.failed) {
                                    block_return var15 = retval;
                                    return var15;
                                 }
                                 break;
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                                 if (this.state.failed) {
                                    var28 = retval;
                                    return var28;
                                 }
                                 break label533;
                           }
                        }
                     }
                  default:
                     alt33 = 0;

                     while(true) {
                        int alt35 = 2;
                        int LA35_0 = this.input.LA(1);
                        if (LA35_0 == 8) {
                           alt35 = 1;
                        }

                        block_return var32;
                        switch (alt35) {
                           case 1:
                              this.pushFollow(FOLLOW_alternative_in_block643);
                              alt = this.alternative(label);
                              --this.state._fsp;
                              if (this.state.failed) {
                                 var32 = retval;
                                 return var32;
                              }

                              this.pushFollow(FOLLOW_rewrite_in_block648);
                              rew = this.rewrite();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 var32 = retval;
                                 return var32;
                              }

                              if (this.state.backtracking == 0) {
                                 if (this.blockNestingLevel == 0) {
                                    ++this.outerAltNum;
                                 }

                                 GrammarAST firstRewriteAST = (rew != null ? (GrammarAST)rew.start : null).findFirstType(75);
                                 boolean etc = (rew != null ? (GrammarAST)rew.start : null).getType() == 76 && firstRewriteAST.getChild(0) != null && firstRewriteAST.getChild(0).getType() == 37;
                                 if ((rew != null ? ((rewrite_return)rew).code : null) != null && !etc) {
                                    (alt != null ? ((alternative_return)alt).code : null).add("rew", rew != null ? ((rewrite_return)rew).code : null);
                                 }

                                 retval.code.add("alts", alt != null ? ((alternative_return)alt).code : null);
                                 (alt != null ? ((alternative_return)alt).code : null).add("altNum", altNum);
                                 (alt != null ? ((alternative_return)alt).code : null).add("outerAlt", this.blockNestingLevel == 0);
                                 ++altNum;
                              }

                              ++alt33;
                              break;
                           default:
                              if (alt33 < 1) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    var32 = retval;
                                    return var32;
                                 }

                                 EarlyExitException eee = new EarlyExitException(35, this.input);
                                 throw eee;
                              }

                              this.match(this.input, 33, FOLLOW_EOB_in_block665);
                              block_return var30;
                              if (this.state.failed) {
                                 var30 = retval;
                                 return var30;
                              }

                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 var30 = retval;
                                 return var30;
                              }

                              return retval;
                        }
                     }
               }
         }
      } catch (RecognitionException var21) {
         this.reportError(var21);
         this.recover(this.input, var21);
      } finally {
         --this.blockNestingLevel;
      }

      return retval;
   }

   public final setBlock_return setBlock() throws RecognitionException {
      setBlock_return retval = new setBlock_return();
      retval.start = this.input.LT(1);
      GrammarAST s = null;
      ST setcode = null;
      if (this.state.backtracking == 0 && this.blockNestingLevel == 0 && this.grammar.buildAST()) {
         Rule r = this.grammar.getRule(this.currentRuleName);
         this.currentAltHasASTRewrite = r.hasRewrite(this.outerAltNum);
         if (this.currentAltHasASTRewrite) {
            r.trackTokenReferenceInAlt((GrammarAST)retval.start, this.outerAltNum);
         }
      }

      try {
         try {
            s = (GrammarAST)this.match(this.input, 16, FOLLOW_BLOCK_in_setBlock697);
            if (this.state.failed) {
               return retval;
            }

            int alt37;
            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);
               if (this.state.failed) {
                  return retval;
               }

               label158:
               while(true) {
                  alt37 = 2;
                  int LA37_0 = this.input.LA(1);
                  if (LA37_0 >= 4 && LA37_0 <= 102) {
                     alt37 = 1;
                  } else if (LA37_0 == 3) {
                     alt37 = 2;
                  }

                  switch (alt37) {
                     case 1:
                        this.matchAny(this.input);
                        if (this.state.failed) {
                           return retval;
                        }
                        break;
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return retval;
                        }
                        break label158;
                  }
               }
            }

            if (this.state.backtracking == 0) {
               alt37 = ((CommonToken)s.getToken()).getTokenIndex();
               if (this.blockNestingLevel == 0) {
                  setcode = this.getTokenElementST("matchRuleBlockSet", "set", s, (GrammarAST)null, (String)null);
               } else {
                  setcode = this.getTokenElementST("matchSet", "set", s, (GrammarAST)null, (String)null);
               }

               setcode.add("elementIndex", alt37);
               setcode.add("s", this.generator.genSetExpr(this.templates, s.getSetValue(), 1, false));
               ST altcode = this.templates.getInstanceOf("alt");
               altcode.addAggr("elements.{el,line,pos}", setcode, s.getLine(), s.getCharPositionInLine() + 1);
               altcode.add("altNum", 1);
               altcode.add("outerAlt", this.blockNestingLevel == 0);
               if (!this.currentAltHasASTRewrite && this.grammar.buildAST()) {
                  altcode.add("autoAST", true);
               }

               altcode.add("treeLevel", this.rewriteTreeNestingLevel);
               retval.code = altcode;
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void setAlternative() throws RecognitionException {
      try {
         this.match(this.input, 8, FOLLOW_ALT_in_setAlternative717);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int cnt38 = 0;

               while(true) {
                  int alt38 = 2;
                  int LA38_0 = this.input.LA(1);
                  if (LA38_0 >= 18 && LA38_0 <= 19 || LA38_0 == 88 || LA38_0 == 94) {
                     alt38 = 1;
                  }

                  switch (alt38) {
                     case 1:
                        this.pushFollow(FOLLOW_setElement_in_setAlternative719);
                        this.setElement();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }

                        ++cnt38;
                        break;
                     default:
                        if (cnt38 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(38, this.input);
                           throw eee;
                        }

                        this.match(this.input, 32, FOLLOW_EOA_in_setAlternative722);
                        if (this.state.failed) {
                           return;
                        }

                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        return;
                  }
               }
            }
         }
      } catch (RecognitionException var8) {
         this.reportError(var8);
         this.recover(this.input, var8);
      } finally {
         ;
      }
   }

   public final void exceptionGroup(ST ruleST) throws RecognitionException {
      try {
         try {
            int alt41 = true;
            int LA41_0 = this.input.LA(1);
            byte alt41;
            if (LA41_0 == 17) {
               alt41 = 1;
            } else {
               if (LA41_0 != 38) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 41, 0, this.input);
                  throw nvae;
               }

               alt41 = 2;
            }

            switch (alt41) {
               case 1:
                  int cnt39 = 0;

                  while(true) {
                     int alt40 = 2;
                     int LA40_0 = this.input.LA(1);
                     if (LA40_0 == 17) {
                        alt40 = 1;
                     }

                     switch (alt40) {
                        case 1:
                           this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup737);
                           this.exceptionHandler(ruleST);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt39;
                           break;
                        default:
                           if (cnt39 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(39, this.input);
                              throw eee;
                           }

                           alt40 = 2;
                           LA40_0 = this.input.LA(1);
                           if (LA40_0 == 38) {
                              alt40 = 1;
                           }

                           switch (alt40) {
                              case 1:
                                 this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup744);
                                 this.finallyClause(ruleST);
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
                  this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup752);
                  this.finallyClause(ruleST);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
            }
         } catch (RecognitionException var11) {
            this.reportError(var11);
            this.recover(this.input, var11);
         }

      } finally {
         ;
      }
   }

   public final void exceptionHandler(ST ruleST) throws RecognitionException {
      GrammarAST ACTION2 = null;
      GrammarAST ARG_ACTION3 = null;

      try {
         try {
            this.match(this.input, 17, FOLLOW_CATCH_in_exceptionHandler766);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            ARG_ACTION3 = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_exceptionHandler768);
            if (this.state.failed) {
               return;
            }

            ACTION2 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler770);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            if (this.state.backtracking == 0) {
               List chunks = this.generator.translateAction(this.currentRuleName, ACTION2);
               ruleST.addAggr("exceptions.{decl,action}", ARG_ACTION3 != null ? ARG_ACTION3.getText() : null, chunks);
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final void finallyClause(ST ruleST) throws RecognitionException {
      GrammarAST ACTION4 = null;

      try {
         try {
            this.match(this.input, 38, FOLLOW_FINALLY_in_finallyClause788);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            ACTION4 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause790);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            if (this.state.backtracking == 0) {
               List chunks = this.generator.translateAction(this.currentRuleName, ACTION4);
               ruleST.add("finally", chunks);
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final alternative_return alternative(GrammarAST label) throws RecognitionException {
      alternative_return retval = new alternative_return();
      retval.start = this.input.LT(1);
      GrammarAST a = null;
      TreeRuleReturnScope e = null;
      if (this.state.backtracking == 0) {
         retval.code = this.templates.getInstanceOf("alt");
         if (this.blockNestingLevel == 0 && this.grammar.buildAST()) {
            Rule r = this.grammar.getRule(this.currentRuleName);
            this.currentAltHasASTRewrite = r.hasRewrite(this.outerAltNum);
         }

         String description = this.grammar.grammarTreeToString((GrammarAST)retval.start, false);
         description = this.generator.target.getTargetStringLiteralFromString(description);
         retval.code.add("description", description);
         retval.code.add("treeLevel", this.rewriteTreeNestingLevel);
         if (!this.currentAltHasASTRewrite && this.grammar.buildAST()) {
            retval.code.add("autoAST", true);
         }
      }

      try {
         a = (GrammarAST)this.match(this.input, 8, FOLLOW_ALT_in_alternative820);
         if (this.state.failed) {
            return retval;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return retval;
            } else {
               int cnt42 = 0;

               while(true) {
                  int alt42 = 2;
                  int LA42_0 = this.input.LA(1);
                  if (LA42_0 == 4 || LA42_0 >= 13 && LA42_0 <= 16 || LA42_0 >= 18 && LA42_0 <= 19 || LA42_0 == 21 || LA42_0 == 29 || LA42_0 == 35 || LA42_0 == 39 || LA42_0 == 41 || LA42_0 == 55 || LA42_0 == 57 || LA42_0 >= 63 && LA42_0 <= 64 || LA42_0 == 77 || LA42_0 == 80 || LA42_0 == 83 || LA42_0 >= 88 && LA42_0 <= 90 || LA42_0 == 94 || LA42_0 == 96 || LA42_0 == 98) {
                     alt42 = 1;
                  }

                  switch (alt42) {
                     case 1:
                        this.pushFollow(FOLLOW_element_in_alternative833);
                        e = this.element(label, (GrammarAST)null);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0 && (e != null ? ((element_return)e).code : null) != null) {
                           retval.code.addAggr("elements.{el,line,pos}", e != null ? ((element_return)e).code : null, (e != null ? (GrammarAST)e.start : null).getLine(), (e != null ? (GrammarAST)e.start : null).getCharPositionInLine() + 1);
                        }

                        ++cnt42;
                        break;
                     default:
                        if (cnt42 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           EarlyExitException eee = new EarlyExitException(42, this.input);
                           throw eee;
                        }

                        this.match(this.input, 32, FOLLOW_EOA_in_alternative851);
                        if (this.state.failed) {
                           return retval;
                        }

                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return retval;
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var12) {
         this.reportError(var12);
         this.recover(this.input, var12);
         return retval;
      } finally {
         ;
      }
   }

   public final element_return element(GrammarAST label, GrammarAST astSuffix) throws RecognitionException {
      element_return retval = new element_return();
      retval.start = this.input.LT(1);
      GrammarAST n = null;
      GrammarAST alabel = null;
      GrammarAST label2 = null;
      GrammarAST a = null;
      GrammarAST b = null;
      GrammarAST sp = null;
      GrammarAST ROOT5 = null;
      GrammarAST BANG6 = null;
      TreeRuleReturnScope e = null;
      ST ne = null;
      TreeRuleReturnScope ebnf7 = null;
      TreeRuleReturnScope atom8 = null;
      TreeRuleReturnScope tree_9 = null;
      TreeRuleReturnScope element_action10 = null;
      IntSet elements = null;
      GrammarAST ast = null;

      try {
         try {
            int alt46 = true;
            int LA46_0 = this.input.LA(1);
            byte alt46;
            if (LA46_0 == 77) {
               alt46 = 1;
            } else if (LA46_0 == 15) {
               alt46 = 2;
            } else if (LA46_0 == 55) {
               alt46 = 3;
            } else if (LA46_0 == 13) {
               alt46 = 4;
            } else if (LA46_0 == 63) {
               alt46 = 5;
            } else if (LA46_0 == 19) {
               alt46 = 6;
            } else if (LA46_0 == 16) {
               int LA46_7 = this.input.LA(2);
               if (this.synpred1_CodeGenTreeWalker()) {
                  alt46 = 7;
               } else {
                  alt46 = 8;
               }
            } else if (LA46_0 == 57 && this.synpred1_CodeGenTreeWalker()) {
               alt46 = 7;
            } else if (LA46_0 == 21 && this.synpred1_CodeGenTreeWalker()) {
               alt46 = 7;
            } else if (LA46_0 == 64 && this.synpred1_CodeGenTreeWalker()) {
               alt46 = 7;
            } else if (LA46_0 != 18 && LA46_0 != 29 && LA46_0 != 80 && LA46_0 != 88 && LA46_0 != 94 && LA46_0 != 98) {
               if (LA46_0 == 96) {
                  alt46 = 9;
               } else if (LA46_0 != 4 && LA46_0 != 39) {
                  if (LA46_0 != 41 && LA46_0 != 83) {
                     if (LA46_0 == 90) {
                        alt46 = 12;
                     } else if (LA46_0 == 89) {
                        alt46 = 13;
                     } else if (LA46_0 == 14) {
                        alt46 = 14;
                     } else {
                        if (LA46_0 != 35) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           NoViableAltException nvae = new NoViableAltException("", 46, 0, this.input);
                           throw nvae;
                        }

                        alt46 = 15;
                     }
                  } else {
                     alt46 = 11;
                  }
               } else {
                  alt46 = 10;
               }
            } else {
               alt46 = 8;
            }

            int LA45_0;
            byte alt45;
            switch (alt46) {
               case 1:
                  ROOT5 = (GrammarAST)this.match(this.input, 77, FOLLOW_ROOT_in_element886);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element890);
                  e = this.element(label, ROOT5);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = e != null ? ((element_return)e).code : null;
                  }
                  break;
               case 2:
                  BANG6 = (GrammarAST)this.match(this.input, 15, FOLLOW_BANG_in_element903);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element907);
                  e = this.element(label, BANG6);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = e != null ? ((element_return)e).code : null;
                  }
                  break;
               case 3:
                  n = (GrammarAST)this.match(this.input, 55, FOLLOW_NOT_in_element923);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_notElement_in_element927);
                  ne = this.notElement(n, label, astSuffix);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = ne;
                  }
                  break;
               case 4:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_element942);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  alabel = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_element946);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element950);
                  e = this.element(alabel, astSuffix);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = e != null ? ((element_return)e).code : null;
                  }
                  break;
               case 5:
                  this.match(this.input, 63, FOLLOW_PLUS_ASSIGN_in_element965);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  label2 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_element969);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element973);
                  e = this.element(label2, astSuffix);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = e != null ? ((element_return)e).code : null;
                  }
                  break;
               case 6:
                  this.match(this.input, 19, FOLLOW_CHAR_RANGE_in_element987);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  a = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_element991);
                  if (this.state.failed) {
                     return retval;
                  }

                  b = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_element995);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = this.templates.getInstanceOf("charRangeRef");
                     String low = this.generator.target.getTargetCharLiteralFromANTLRCharLiteral(this.generator, a != null ? a.getText() : null);
                     String high = this.generator.target.getTargetCharLiteralFromANTLRCharLiteral(this.generator, b != null ? b.getText() : null);
                     retval.code.add("a", low);
                     retval.code.add("b", high);
                     if (label != null) {
                        retval.code.add("label", label.getText());
                     }
                  }
                  break;
               case 7:
                  this.pushFollow(FOLLOW_ebnf_in_element1024);
                  ebnf7 = this.ebnf(label);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = ebnf7 != null ? ((ebnf_return)ebnf7).code : null;
                  }
                  break;
               case 8:
                  this.pushFollow(FOLLOW_atom_in_element1035);
                  atom8 = this.atom((GrammarAST)null, label, astSuffix);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = atom8 != null ? ((atom_return)atom8).code : null;
                  }
                  break;
               case 9:
                  this.pushFollow(FOLLOW_tree__in_element1046);
                  tree_9 = this.tree_();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = tree_9 != null ? ((tree__return)tree_9).code : null;
                  }
                  break;
               case 10:
                  this.pushFollow(FOLLOW_element_action_in_element1056);
                  element_action10 = this.element_action();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = element_action10 != null ? ((element_action_return)element_action10).code : null;
                  }
                  break;
               case 11:
                  int alt43 = true;
                  LA45_0 = this.input.LA(1);
                  if (LA45_0 == 83) {
                     alt45 = 1;
                  } else {
                     if (LA45_0 != 41) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 43, 0, this.input);
                        throw nvae;
                     }

                     alt45 = 2;
                  }

                  switch (alt45) {
                     case 1:
                        sp = (GrammarAST)this.match(this.input, 83, FOLLOW_SEMPRED_in_element1071);
                        if (this.state.failed) {
                           return retval;
                        }
                        break;
                     case 2:
                        sp = (GrammarAST)this.match(this.input, 41, FOLLOW_GATED_SEMPRED_in_element1075);
                        if (this.state.failed) {
                           return retval;
                        }
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = this.templates.getInstanceOf("validateSemanticPredicate");
                     retval.code.add("pred", this.generator.translateAction(this.currentRuleName, sp));
                     String description = this.generator.target.getTargetStringLiteralFromString(sp != null ? sp.getText() : null);
                     retval.code.add("description", description);
                  }
                  break;
               case 12:
                  this.match(this.input, 90, FOLLOW_SYN_SEMPRED_in_element1086);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 13:
                  this.match(this.input, 89, FOLLOW_SYNPRED_in_element1094);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     while(true) {
                        alt45 = 2;
                        LA45_0 = this.input.LA(1);
                        if (LA45_0 >= 4 && LA45_0 <= 102) {
                           alt45 = 1;
                        } else if (LA45_0 == 3) {
                           alt45 = 2;
                        }

                        switch (alt45) {
                           case 1:
                              this.matchAny(this.input);
                              if (this.state.failed) {
                                 return retval;
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return retval;
                              }

                              return retval;
                        }
                     }
                  }
                  break;
               case 14:
                  this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_element1105);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     while(true) {
                        alt45 = 2;
                        LA45_0 = this.input.LA(1);
                        if (LA45_0 >= 4 && LA45_0 <= 102) {
                           alt45 = 1;
                        } else if (LA45_0 == 3) {
                           alt45 = 2;
                        }

                        switch (alt45) {
                           case 1:
                              this.matchAny(this.input);
                              if (this.state.failed) {
                                 return retval;
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return retval;
                              }

                              return retval;
                        }
                     }
                  }
                  break;
               case 15:
                  this.match(this.input, 35, FOLLOW_EPSILON_in_element1117);
                  if (this.state.failed) {
                     return retval;
                  }
            }
         } catch (RecognitionException var28) {
            this.reportError(var28);
            this.recover(this.input, var28);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final element_action_return element_action() throws RecognitionException {
      element_action_return retval = new element_action_return();
      retval.start = this.input.LT(1);
      GrammarAST act = null;
      GrammarAST act2 = null;

      try {
         try {
            int alt47 = true;
            int LA47_0 = this.input.LA(1);
            byte alt47;
            if (LA47_0 == 4) {
               alt47 = 1;
            } else {
               if (LA47_0 != 39) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 47, 0, this.input);
                  throw nvae;
               }

               alt47 = 2;
            }

            switch (alt47) {
               case 1:
                  act = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_element_action1134);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = this.templates.getInstanceOf("execAction");
                     retval.code.add("action", this.generator.translateAction(this.currentRuleName, act));
                  }
                  break;
               case 2:
                  act2 = (GrammarAST)this.match(this.input, 39, FOLLOW_FORCED_ACTION_in_element_action1145);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = this.templates.getInstanceOf("execForcedAction");
                     retval.code.add("action", this.generator.translateAction(this.currentRuleName, act2));
                  }
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final ST notElement(GrammarAST n, GrammarAST label, GrammarAST astSuffix) throws RecognitionException {
      ST code = null;
      GrammarAST assign_c = null;
      GrammarAST assign_s = null;
      GrammarAST assign_t = null;
      GrammarAST assign_st = null;
      IntSet elements = null;
      String labelText = null;
      if (label != null) {
         labelText = label.getText();
      }

      try {
         try {
            int alt49 = true;
            byte alt49;
            switch (this.input.LA(1)) {
               case 16:
                  alt49 = 4;
                  break;
               case 18:
                  alt49 = 1;
                  break;
               case 88:
                  alt49 = 2;
                  break;
               case 94:
                  alt49 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return code;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 49, 0, this.input);
                  throw nvae;
            }

            int alt48;
            switch (alt49) {
               case 1:
                  assign_c = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_notElement1174);
                  if (this.state.failed) {
                     return code;
                  }

                  if (this.state.backtracking == 0) {
                     int ttype = false;
                     if (this.grammar.type == 1) {
                        alt48 = Grammar.getCharValueFromGrammarCharLiteral(assign_c != null ? assign_c.getText() : null);
                     } else {
                        alt48 = this.grammar.getTokenType(assign_c != null ? assign_c.getText() : null);
                     }

                     elements = this.grammar.complement(alt48);
                  }
                  break;
               case 2:
                  assign_s = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_notElement1187);
                  if (this.state.failed) {
                     return code;
                  }

                  if (this.state.backtracking == 0) {
                     alt48 = 0;
                     if (this.grammar.type != 1) {
                        alt48 = this.grammar.getTokenType(assign_s != null ? assign_s.getText() : null);
                     }

                     elements = this.grammar.complement(alt48);
                  }
                  break;
               case 3:
                  assign_t = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_notElement1200);
                  if (this.state.failed) {
                     return code;
                  }

                  if (this.state.backtracking == 0) {
                     alt48 = this.grammar.getTokenType(assign_t != null ? assign_t.getText() : null);
                     elements = this.grammar.complement(alt48);
                  }
                  break;
               case 4:
                  assign_st = (GrammarAST)this.match(this.input, 16, FOLLOW_BLOCK_in_notElement1214);
                  if (this.state.failed) {
                     return code;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return code;
                     }

                     label270:
                     while(true) {
                        alt48 = 2;
                        int LA48_0 = this.input.LA(1);
                        if (LA48_0 >= 4 && LA48_0 <= 102) {
                           alt48 = 1;
                        } else if (LA48_0 == 3) {
                           alt48 = 2;
                        }

                        switch (alt48) {
                           case 1:
                              this.matchAny(this.input);
                              if (this.state.failed) {
                                 return code;
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return code;
                              }
                              break label270;
                        }
                     }
                  }

                  if (this.state.backtracking == 0) {
                     elements = assign_st.getSetValue();
                     elements = this.grammar.complement(elements);
                  }
            }

            if (this.state.backtracking == 0) {
               code = this.getTokenElementST("matchSet", "set", (GrammarAST)n.getChild(0), astSuffix, labelText);
               code.add("s", this.generator.genSetExpr(this.templates, elements, 1, false));
               alt48 = ((CommonToken)n.getToken()).getTokenIndex();
               code.add("elementIndex", alt48);
               if (this.grammar.type != 1) {
                  this.generator.generateLocalFOLLOW(n, "set", this.currentRuleName, alt48);
               }
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
         }

         return code;
      } finally {
         ;
      }
   }

   public final ebnf_return ebnf(GrammarAST label) throws RecognitionException {
      ebnf_return retval = new ebnf_return();
      retval.start = this.input.LT(1);
      TreeRuleReturnScope blk = null;
      DFA dfa = null;
      GrammarAST b = (GrammarAST)((GrammarAST)retval.start).getChild(0);
      GrammarAST eob = b.getLastChild();

      try {
         try {
            int alt50 = true;
            byte alt50;
            switch (this.input.LA(1)) {
               case 16:
                  alt50 = 1;
                  break;
               case 21:
                  alt50 = 3;
                  break;
               case 57:
                  alt50 = 2;
                  break;
               case 64:
                  alt50 = 4;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 50, 0, this.input);
                  throw nvae;
            }

            switch (alt50) {
               case 1:
                  if (this.state.backtracking == 0) {
                     dfa = ((GrammarAST)retval.start).getLookaheadDFA();
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1261);
                  blk = this.block("block", dfa, label);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = blk != null ? ((block_return)blk).code : null;
                  }
                  break;
               case 2:
                  if (this.state.backtracking == 0) {
                     dfa = ((GrammarAST)retval.start).getLookaheadDFA();
                  }

                  this.match(this.input, 57, FOLLOW_OPTIONAL_in_ebnf1280);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1284);
                  blk = this.block("optionalBlock", dfa, label);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = blk != null ? ((block_return)blk).code : null;
                  }
                  break;
               case 3:
                  if (this.state.backtracking == 0) {
                     dfa = eob.getLookaheadDFA();
                  }

                  this.match(this.input, 21, FOLLOW_CLOSURE_in_ebnf1305);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1309);
                  blk = this.block("closureBlock", dfa, label);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = blk != null ? ((block_return)blk).code : null;
                  }
                  break;
               case 4:
                  if (this.state.backtracking == 0) {
                     dfa = eob.getLookaheadDFA();
                  }

                  this.match(this.input, 64, FOLLOW_POSITIVE_CLOSURE_in_ebnf1330);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1334);
                  blk = this.block("positiveClosureBlock", dfa, label);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = blk != null ? ((block_return)blk).code : null;
                  }
            }

            if (this.state.backtracking == 0) {
               String description = this.grammar.grammarTreeToString((GrammarAST)retval.start, false);
               description = this.generator.target.getTargetStringLiteralFromString(description);
               retval.code.add("description", description);
            }
         } catch (RecognitionException var12) {
            this.reportError(var12);
            this.recover(this.input, var12);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final tree__return tree_() throws RecognitionException {
      tree__return retval = new tree__return();
      retval.start = this.input.LT(1);
      TreeRuleReturnScope el = null;
      TreeRuleReturnScope act = null;
      ++this.rewriteTreeNestingLevel;
      GrammarAST rootSuffix = null;
      if (this.state.backtracking == 0) {
         retval.code = this.templates.getInstanceOf("tree");
         NFAState afterDOWN = (NFAState)((GrammarAST)retval.start).NFATreeDownState.transition(0).target;
         LookaheadSet s = this.grammar.LOOK(afterDOWN);
         if (s.member(3)) {
            retval.code.add("nullableChildList", "true");
         }

         retval.code.add("enclosingTreeLevel", this.rewriteTreeNestingLevel - 1);
         retval.code.add("treeLevel", this.rewriteTreeNestingLevel);
         Rule r = this.grammar.getRule(this.currentRuleName);
         if (this.grammar.buildAST() && !r.hasRewrite(this.outerAltNum)) {
            rootSuffix = new GrammarAST(77, "ROOT");
         }
      }

      tree__return var13;
      try {
         this.match(this.input, 96, FOLLOW_TREE_BEGIN_in_tree_1372);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               var13 = retval;
               return var13;
            }

            this.pushFollow(FOLLOW_element_in_tree_1379);
            el = this.element((GrammarAST)null, rootSuffix);
            --this.state._fsp;
            if (this.state.failed) {
               var13 = retval;
               return var13;
            }

            if (this.state.backtracking == 0) {
               retval.code.addAggr("root.{el,line,pos}", el != null ? ((element_return)el).code : null, (el != null ? (GrammarAST)el.start : null).getLine(), (el != null ? (GrammarAST)el.start : null).getCharPositionInLine() + 1);
            }

            while(true) {
               int alt52 = 2;
               int LA52_0 = this.input.LA(1);
               int LA51_3;
               if (LA52_0 == 4) {
                  LA51_3 = this.input.LA(2);
                  if (this.synpred2_CodeGenTreeWalker()) {
                     alt52 = 1;
                  }
               } else if (LA52_0 == 39) {
                  LA51_3 = this.input.LA(2);
                  if (this.synpred2_CodeGenTreeWalker()) {
                     alt52 = 1;
                  }
               }

               tree__return var17;
               switch (alt52) {
                  case 1:
                     this.pushFollow(FOLLOW_element_action_in_tree_1416);
                     act = this.element_action();
                     --this.state._fsp;
                     if (this.state.failed) {
                        var17 = retval;
                        return var17;
                     }

                     if (this.state.backtracking == 0) {
                        retval.code.addAggr("actionsAfterRoot.{el,line,pos}", act != null ? ((element_action_return)act).code : null, (act != null ? (GrammarAST)act.start : null).getLine(), (act != null ? (GrammarAST)act.start : null).getCharPositionInLine() + 1);
                     }
                     break;
                  default:
                     while(true) {
                        alt52 = 2;
                        LA52_0 = this.input.LA(1);
                        if (LA52_0 == 4 || LA52_0 >= 13 && LA52_0 <= 16 || LA52_0 >= 18 && LA52_0 <= 19 || LA52_0 == 21 || LA52_0 == 29 || LA52_0 == 35 || LA52_0 == 39 || LA52_0 == 41 || LA52_0 == 55 || LA52_0 == 57 || LA52_0 >= 63 && LA52_0 <= 64 || LA52_0 == 77 || LA52_0 == 80 || LA52_0 == 83 || LA52_0 >= 88 && LA52_0 <= 90 || LA52_0 == 94 || LA52_0 == 96 || LA52_0 == 98) {
                           alt52 = 1;
                        }

                        switch (alt52) {
                           case 1:
                              this.pushFollow(FOLLOW_element_in_tree_1438);
                              el = this.element((GrammarAST)null, (GrammarAST)null);
                              --this.state._fsp;
                              if (this.state.failed) {
                                 var17 = retval;
                                 return var17;
                              }

                              if (this.state.backtracking == 0) {
                                 retval.code.addAggr("children.{el,line,pos}", el != null ? ((element_return)el).code : null, (el != null ? (GrammarAST)el.start : null).getLine(), (el != null ? (GrammarAST)el.start : null).getCharPositionInLine() + 1);
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (!this.state.failed) {
                                 return retval;
                              }

                              var13 = retval;
                              return var13;
                        }
                     }
               }
            }
         }

         var13 = retval;
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(this.input, var11);
         return retval;
      } finally {
         --this.rewriteTreeNestingLevel;
      }

      return var13;
   }

   public final atom_return atom(GrammarAST scope, GrammarAST label, GrammarAST astSuffix) throws RecognitionException {
      atom_return retval = new atom_return();
      retval.start = this.input.LT(1);
      GrammarAST r = null;
      GrammarAST rarg = null;
      GrammarAST t = null;
      GrammarAST targ = null;
      GrammarAST c = null;
      GrammarAST s = null;
      GrammarAST w = null;
      GrammarAST ID11 = null;
      TreeRuleReturnScope a = null;
      ST set12 = null;
      String labelText = null;
      if (this.state.backtracking == 0) {
         if (label != null) {
            labelText = label.getText();
         }

         if (this.grammar.type != 1 && (((GrammarAST)retval.start).getType() == 80 || ((GrammarAST)retval.start).getType() == 94 || ((GrammarAST)retval.start).getType() == 18 || ((GrammarAST)retval.start).getType() == 88)) {
            Rule encRule = this.grammar.getRule(((GrammarAST)retval.start).enclosingRuleName);
            if (encRule != null && encRule.hasRewrite(this.outerAltNum) && astSuffix != null) {
               ErrorManager.grammarError(165, this.grammar, ((GrammarAST)retval.start).getToken(), ((GrammarAST)retval.start).enclosingRuleName, this.outerAltNum);
               astSuffix = null;
            }
         }
      }

      try {
         try {
            int alt55 = true;
            byte alt55;
            switch (this.input.LA(1)) {
               case 16:
                  alt55 = 7;
                  break;
               case 18:
                  alt55 = 3;
                  break;
               case 29:
                  alt55 = 6;
                  break;
               case 80:
                  alt55 = 1;
                  break;
               case 88:
                  alt55 = 4;
                  break;
               case 94:
                  alt55 = 2;
                  break;
               case 98:
                  alt55 = 5;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 55, 0, this.input);
                  throw nvae;
            }

            int i;
            Grammar scopeG;
            String scopeName;
            byte alt54;
            int i;
            Rule rdef2;
            List args;
            switch (alt55) {
               case 1:
                  r = (GrammarAST)this.match(this.input, 80, FOLLOW_RULE_REF_in_atom1488);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     alt54 = 2;
                     i = this.input.LA(1);
                     if (i == 12) {
                        alt54 = 1;
                     }

                     switch (alt54) {
                        case 1:
                           rarg = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1493);
                           if (this.state.failed) {
                              return retval;
                           }
                        default:
                           this.match(this.input, 3, (BitSet)null);
                           if (this.state.failed) {
                              return retval;
                           }
                     }
                  }

                  if (this.state.backtracking == 0) {
                     this.grammar.checkRuleReference(scope, r, rarg, this.currentRuleName);
                     scopeName = null;
                     if (scope != null) {
                        scopeName = scope.getText();
                     }

                     rdef2 = this.grammar.getRule(scopeName, r != null ? r.getText() : null);
                     if (!rdef2.getHasReturnValue()) {
                        labelText = null;
                     }

                     retval.code = this.getRuleElementST("ruleRef", r != null ? r.getText() : null, r, astSuffix, labelText);
                     retval.code.add("rule", rdef2);
                     if (scope != null) {
                        scopeG = this.grammar.composite.getGrammar(scope.getText());
                        retval.code.add("scope", scopeG);
                     } else if (rdef2.grammar != this.grammar) {
                        args = rdef2.grammar.getDelegates();
                        if (args.contains(this.grammar)) {
                           retval.code.add("scope", rdef2.grammar);
                        } else if (this.grammar != rdef2.grammar.composite.delegateGrammarTreeRoot.grammar) {
                           retval.code.add("scope", rdef2.grammar.composite.delegateGrammarTreeRoot.grammar);
                        }
                     }

                     if (rarg != null) {
                        args = this.generator.translateAction(this.currentRuleName, rarg);
                        retval.code.add("args", args);
                     }

                     int i = ((CommonToken)r.getToken()).getTokenIndex();
                     retval.code.add("elementIndex", i);
                     this.generator.generateLocalFOLLOW(r, r != null ? r.getText() : null, this.currentRuleName, i);
                     r.code = retval.code;
                  }
                  break;
               case 2:
                  t = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_atom1511);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     alt54 = 2;
                     i = this.input.LA(1);
                     if (i == 12) {
                        alt54 = 1;
                     }

                     switch (alt54) {
                        case 1:
                           targ = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1516);
                           if (this.state.failed) {
                              return retval;
                           }
                        default:
                           this.match(this.input, 3, (BitSet)null);
                           if (this.state.failed) {
                              return retval;
                           }
                     }
                  }

                  if (this.state.backtracking == 0) {
                     if (this.currentAltHasASTRewrite && t.terminalOptions != null && t.terminalOptions.get("node") != null) {
                        ErrorManager.grammarError(155, this.grammar, t.getToken(), t != null ? t.getText() : null);
                     }

                     this.grammar.checkRuleReference(scope, t, targ, this.currentRuleName);
                     if (this.grammar.type == 1) {
                        if (this.grammar.getTokenType(t != null ? t.getText() : null) == -1) {
                           retval.code = this.templates.getInstanceOf("lexerMatchEOF");
                        } else {
                           retval.code = this.templates.getInstanceOf("lexerRuleRef");
                           if (this.isListLabel(labelText)) {
                              retval.code = this.templates.getInstanceOf("lexerRuleRefAndListLabel");
                           }

                           scopeName = null;
                           if (scope != null) {
                              scopeName = scope.getText();
                           }

                           rdef2 = this.grammar.getRule(scopeName, t != null ? t.getText() : null);
                           retval.code.add("rule", rdef2);
                           if (scope != null) {
                              scopeG = this.grammar.composite.getGrammar(scope.getText());
                              retval.code.add("scope", scopeG);
                           } else if (rdef2.grammar != this.grammar) {
                              retval.code.add("scope", rdef2.grammar);
                           }

                           if (targ != null) {
                              args = this.generator.translateAction(this.currentRuleName, targ);
                              retval.code.add("args", args);
                           }
                        }

                        i = ((CommonToken)t.getToken()).getTokenIndex();
                        retval.code.add("elementIndex", i);
                        if (label != null) {
                           retval.code.add("label", labelText);
                        }
                     } else {
                        retval.code = this.getTokenElementST("tokenRef", t != null ? t.getText() : null, t, astSuffix, labelText);
                        scopeName = this.generator.getTokenTypeAsTargetLabel(this.grammar.getTokenType(t.getText()));
                        retval.code.add("token", scopeName);
                        if (!this.currentAltHasASTRewrite && t.terminalOptions != null) {
                           retval.code.add("terminalOptions", t.terminalOptions);
                        }

                        i = ((CommonToken)t.getToken()).getTokenIndex();
                        retval.code.add("elementIndex", i);
                        this.generator.generateLocalFOLLOW(t, scopeName, this.currentRuleName, i);
                     }

                     t.code = retval.code;
                  }
                  break;
               case 3:
                  c = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_atom1532);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     if (this.grammar.type == 1) {
                        retval.code = this.templates.getInstanceOf("charRef");
                        retval.code.add("char", this.generator.target.getTargetCharLiteralFromANTLRCharLiteral(this.generator, c != null ? c.getText() : null));
                        if (label != null) {
                           retval.code.add("label", labelText);
                        }
                     } else {
                        retval.code = this.getTokenElementST("tokenRef", "char_literal", c, astSuffix, labelText);
                        scopeName = this.generator.getTokenTypeAsTargetLabel(this.grammar.getTokenType(c != null ? c.getText() : null));
                        retval.code.add("token", scopeName);
                        if (c.terminalOptions != null) {
                           retval.code.add("terminalOptions", c.terminalOptions);
                        }

                        i = ((CommonToken)c.getToken()).getTokenIndex();
                        retval.code.add("elementIndex", i);
                        this.generator.generateLocalFOLLOW(c, scopeName, this.currentRuleName, i);
                     }
                  }
                  break;
               case 4:
                  s = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_atom1544);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     i = ((CommonToken)s.getToken()).getTokenIndex();
                     if (this.grammar.type == 1) {
                        retval.code = this.templates.getInstanceOf("lexerStringRef");
                        retval.code.add("string", this.generator.target.getTargetStringLiteralFromANTLRStringLiteral(this.generator, s != null ? s.getText() : null));
                        retval.code.add("elementIndex", i);
                        if (label != null) {
                           retval.code.add("label", labelText);
                        }
                     } else {
                        retval.code = this.getTokenElementST("tokenRef", "string_literal", s, astSuffix, labelText);
                        String tokenLabel = this.generator.getTokenTypeAsTargetLabel(this.grammar.getTokenType(s != null ? s.getText() : null));
                        retval.code.add("token", tokenLabel);
                        if (s.terminalOptions != null) {
                           retval.code.add("terminalOptions", s.terminalOptions);
                        }

                        retval.code.add("elementIndex", i);
                        this.generator.generateLocalFOLLOW(s, tokenLabel, this.currentRuleName, i);
                     }
                  }
                  break;
               case 5:
                  w = (GrammarAST)this.match(this.input, 98, FOLLOW_WILDCARD_in_atom1556);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = this.getWildcardST(w, astSuffix, labelText);
                     retval.code.add("elementIndex", ((CommonToken)w.getToken()).getTokenIndex());
                  }
                  break;
               case 6:
                  this.match(this.input, 29, FOLLOW_DOT_in_atom1567);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  ID11 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_atom1569);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_atom_in_atom1573);
                  a = this.atom(ID11, label, astSuffix);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = a != null ? ((atom_return)a).code : null;
                  }
                  break;
               case 7:
                  this.pushFollow(FOLLOW_set_in_atom1586);
                  set12 = this.set(label, astSuffix);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = set12;
                  }
            }
         } catch (RecognitionException var23) {
            this.reportError(var23);
            this.recover(this.input, var23);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void ast_suffix() throws RecognitionException {
      try {
         try {
            if (this.input.LA(1) != 15 && this.input.LA(1) != 77) {
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
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final ST set(GrammarAST label, GrammarAST astSuffix) throws RecognitionException {
      ST code = null;
      GrammarAST s = null;
      String labelText = null;
      if (label != null) {
         labelText = label.getText();
      }

      try {
         try {
            s = (GrammarAST)this.match(this.input, 16, FOLLOW_BLOCK_in_set1631);
            if (this.state.failed) {
               return code;
            }

            int alt56;
            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);
               if (this.state.failed) {
                  return code;
               }

               label121:
               while(true) {
                  alt56 = 2;
                  int LA56_0 = this.input.LA(1);
                  if (LA56_0 >= 4 && LA56_0 <= 102) {
                     alt56 = 1;
                  } else if (LA56_0 == 3) {
                     alt56 = 2;
                  }

                  switch (alt56) {
                     case 1:
                        this.matchAny(this.input);
                        if (this.state.failed) {
                           return code;
                        }
                        break;
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return code;
                        }
                        break label121;
                  }
               }
            }

            if (this.state.backtracking == 0) {
               code = this.getTokenElementST("matchSet", "set", s, astSuffix, labelText);
               alt56 = ((CommonToken)s.getToken()).getTokenIndex();
               code.add("elementIndex", alt56);
               if (this.grammar.type != 1) {
                  this.generator.generateLocalFOLLOW(s, "set", this.currentRuleName, alt56);
               }

               code.add("s", this.generator.genSetExpr(this.templates, s.getSetValue(), 1, false));
            }
         } catch (RecognitionException var12) {
            this.reportError(var12);
            this.recover(this.input, var12);
         }

         return code;
      } finally {
         ;
      }
   }

   public final void setElement() throws RecognitionException {
      try {
         try {
            int alt57 = true;
            byte alt57;
            switch (this.input.LA(1)) {
               case 18:
                  alt57 = 1;
                  break;
               case 19:
                  alt57 = 4;
                  break;
               case 88:
                  alt57 = 3;
                  break;
               case 94:
                  alt57 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 57, 0, this.input);
                  throw nvae;
            }

            switch (alt57) {
               case 1:
                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_setElement1651);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.match(this.input, 94, FOLLOW_TOKEN_REF_in_setElement1656);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 3:
                  this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_setElement1661);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 4:
                  this.match(this.input, 19, FOLLOW_CHAR_RANGE_in_setElement1667);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_setElement1669);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_setElement1671);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final rewrite_return rewrite() throws RecognitionException {
      rewrite_return retval = new rewrite_return();
      retval.start = this.input.LT(1);
      GrammarAST r = null;
      GrammarAST pred = null;
      ST alt = null;
      if (this.state.backtracking == 0) {
         if (((GrammarAST)retval.start).getType() == 76) {
            if (this.generator.grammar.buildTemplate()) {
               retval.code = this.templates.getInstanceOf("rewriteTemplate");
            } else {
               retval.code = this.templates.getInstanceOf("rewriteCode");
               retval.code.add("treeLevel", 0);
               retval.code.add("rewriteBlockLevel", 0);
               retval.code.add("referencedElementsDeep", this.getTokenTypesAsTargetLabels(((GrammarAST)retval.start).rewriteRefsDeep));
               Set tokenLabels = this.grammar.getLabels(((GrammarAST)retval.start).rewriteRefsDeep, 2);
               Set tokenListLabels = this.grammar.getLabels(((GrammarAST)retval.start).rewriteRefsDeep, 4);
               Set ruleLabels = this.grammar.getLabels(((GrammarAST)retval.start).rewriteRefsDeep, 1);
               Set ruleListLabels = this.grammar.getLabels(((GrammarAST)retval.start).rewriteRefsDeep, 3);
               Set wildcardLabels = this.grammar.getLabels(((GrammarAST)retval.start).rewriteRefsDeep, 6);
               Set wildcardListLabels = this.grammar.getLabels(((GrammarAST)retval.start).rewriteRefsDeep, 7);
               ST retvalST = this.templates.getInstanceOf("prevRuleRootRef");
               ruleLabels.add(retvalST.render());
               retval.code.add("referencedTokenLabels", tokenLabels);
               retval.code.add("referencedTokenListLabels", tokenListLabels);
               retval.code.add("referencedRuleLabels", ruleLabels);
               retval.code.add("referencedRuleListLabels", ruleListLabels);
               retval.code.add("referencedWildcardLabels", wildcardLabels);
               retval.code.add("referencedWildcardListLabels", wildcardListLabels);
            }
         } else {
            retval.code = this.templates.getInstanceOf("noRewrite");
            retval.code.add("treeLevel", 0);
            retval.code.add("rewriteBlockLevel", 0);
         }
      }

      try {
         try {
            int alt60 = true;
            int LA60_0 = this.input.LA(1);
            byte alt60;
            if (LA60_0 == 76) {
               alt60 = 1;
            } else {
               if (LA60_0 != 8 && LA60_0 != 33) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 60, 0, this.input);
                  throw nvae;
               }

               alt60 = 2;
            }

            switch (alt60) {
               case 1:
                  this.match(this.input, 76, FOLLOW_REWRITES_in_rewrite1696);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     while(true) {
                        int alt59 = 2;
                        int LA59_0 = this.input.LA(1);
                        if (LA59_0 == 75) {
                           alt59 = 1;
                        }

                        switch (alt59) {
                           case 1:
                              if (this.state.backtracking == 0) {
                                 this.rewriteRuleRefs = new HashSet();
                              }

                              r = (GrammarAST)this.match(this.input, 75, FOLLOW_REWRITE_in_rewrite1717);
                              if (this.state.failed) {
                                 return retval;
                              }

                              this.match(this.input, 2, (BitSet)null);
                              if (this.state.failed) {
                                 return retval;
                              }

                              int alt58 = 2;
                              int LA58_0 = this.input.LA(1);
                              if (LA58_0 == 83) {
                                 alt58 = 1;
                              }

                              switch (alt58) {
                                 case 1:
                                    pred = (GrammarAST)this.match(this.input, 83, FOLLOW_SEMPRED_in_rewrite1722);
                                    if (this.state.failed) {
                                       return retval;
                                    }
                                 default:
                                    this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite1728);
                                    alt = this.rewrite_alternative();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    this.match(this.input, 3, (BitSet)null);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       this.rewriteBlockNestingLevel = 0;
                                       List predChunks = null;
                                       if (pred != null) {
                                          predChunks = this.generator.translateAction(this.currentRuleName, pred);
                                       }

                                       String description = this.grammar.grammarTreeToString(r, false);
                                       description = this.generator.target.getTargetStringLiteralFromString(description);
                                       retval.code.addAggr("alts.{pred,alt,description}", predChunks, alt, description);
                                       pred = null;
                                    }
                                    continue;
                              }
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return retval;
                              }

                              return retval;
                        }
                     }
                  }
               case 2:
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.recover(this.input, var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final ST rewrite_block(String blockTemplateName) throws RecognitionException {
      ST code = null;
      GrammarAST BLOCK13 = null;
      ST alt = null;
      ++this.rewriteBlockNestingLevel;
      ST save_currentBlockST = this.currentBlockST;
      if (this.state.backtracking == 0) {
         code = this.templates.getInstanceOf(blockTemplateName);
         this.currentBlockST = code;
         code.add("rewriteBlockLevel", this.rewriteBlockNestingLevel);
      }

      try {
         BLOCK13 = (GrammarAST)this.match(this.input, 16, FOLLOW_BLOCK_in_rewrite_block1771);
         ST var6;
         if (this.state.failed) {
            var6 = code;
            return var6;
         }

         if (this.state.backtracking == 0) {
            this.currentBlockST.add("referencedElementsDeep", this.getTokenTypesAsTargetLabels(BLOCK13.rewriteRefsDeep));
            this.currentBlockST.add("referencedElements", this.getTokenTypesAsTargetLabels(BLOCK13.rewriteRefsShallow));
         }

         this.match(this.input, 2, (BitSet)null);
         if (this.state.failed) {
            var6 = code;
            return var6;
         }

         this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite_block1783);
         alt = this.rewrite_alternative();
         --this.state._fsp;
         if (this.state.failed) {
            var6 = code;
            return var6;
         }

         this.match(this.input, 33, FOLLOW_EOB_in_rewrite_block1788);
         if (this.state.failed) {
            var6 = code;
            return var6;
         }

         this.match(this.input, 3, (BitSet)null);
         if (this.state.failed) {
            var6 = code;
            return var6;
         }

         if (this.state.backtracking == 0) {
            code.add("alt", alt);
         }
      } catch (RecognitionException var10) {
         this.reportError(var10);
         this.recover(this.input, var10);
      } finally {
         --this.rewriteBlockNestingLevel;
         this.currentBlockST = save_currentBlockST;
      }

      return code;
   }

   public final ST rewrite_alternative() throws RecognitionException {
      ST code = null;
      GrammarAST a = null;
      TreeRuleReturnScope el = null;
      ST rewrite_template14 = null;

      try {
         try {
            int alt63 = true;
            byte alt63;
            int LA62_0;
            NoViableAltException nvae;
            int cnt61;
            int LA61_0;
            switch (this.input.LA(1)) {
               case 4:
               case 91:
                  alt63 = 2;
                  break;
               case 8:
                  int LA63_1 = this.input.LA(2);
                  if (LA63_1 != 2) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return code;
                     }

                     LA62_0 = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 63, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA62_0);
                     }
                  }

                  LA62_0 = this.input.LA(3);
                  int nvaeConsume;
                  if (LA62_0 == 35) {
                     cnt61 = this.input.LA(4);
                     if (cnt61 != 32) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return code;
                        }

                        nvaeConsume = this.input.mark();

                        try {
                           for(LA61_0 = 0; LA61_0 < 3; ++LA61_0) {
                              this.input.consume();
                           }

                           NoViableAltException nvae = new NoViableAltException("", 63, 5, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeConsume);
                        }
                     }

                     nvaeConsume = this.input.LA(5);
                     int nvaeMark;
                     if (nvaeConsume != 3) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return code;
                        }

                        LA61_0 = this.input.mark();

                        try {
                           for(nvaeMark = 0; nvaeMark < 4; ++nvaeMark) {
                              this.input.consume();
                           }

                           NoViableAltException nvae = new NoViableAltException("", 63, 7, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA61_0);
                        }
                     }

                     LA61_0 = this.input.LA(6);
                     if (this.generator.grammar.buildAST()) {
                        alt63 = 1;
                     } else {
                        if (!this.generator.grammar.buildTemplate()) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return code;
                           }

                           nvaeMark = this.input.mark();

                           try {
                              for(int nvaeConsume = 0; nvaeConsume < 5; ++nvaeConsume) {
                                 this.input.consume();
                              }

                              NoViableAltException nvae = new NoViableAltException("", 63, 8, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                        }

                        alt63 = 2;
                     }
                  } else {
                     if (LA62_0 != 4 && LA62_0 != 18 && LA62_0 != 21 && LA62_0 != 48 && LA62_0 != 57 && LA62_0 != 64 && LA62_0 != 80 && LA62_0 != 88 && LA62_0 != 94 && LA62_0 != 96) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return code;
                        }

                        cnt61 = this.input.mark();

                        try {
                           for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                              this.input.consume();
                           }

                           NoViableAltException nvae = new NoViableAltException("", 63, 4, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(cnt61);
                        }
                     }

                     alt63 = 1;
                  }
                  break;
               case 37:
                  alt63 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return code;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 63, 0, this.input);
                  throw nvae;
            }

            switch (alt63) {
               case 1:
                  if (!this.generator.grammar.buildAST()) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return code;
                     }

                     throw new FailedPredicateException(this.input, "rewrite_alternative", "generator.grammar.buildAST()");
                  }

                  a = (GrammarAST)this.match(this.input, 8, FOLLOW_ALT_in_rewrite_alternative1823);
                  if (this.state.failed) {
                     return code;
                  }

                  if (this.state.backtracking == 0) {
                     code = this.templates.getInstanceOf("rewriteElementList");
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return code;
                  }

                  int alt62 = true;
                  LA62_0 = this.input.LA(1);
                  byte alt62;
                  if (LA62_0 != 4 && LA62_0 != 18 && LA62_0 != 21 && LA62_0 != 48 && LA62_0 != 57 && LA62_0 != 64 && LA62_0 != 80 && LA62_0 != 88 && LA62_0 != 94 && LA62_0 != 96) {
                     if (LA62_0 != 35) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return code;
                        }

                        nvae = new NoViableAltException("", 62, 0, this.input);
                        throw nvae;
                     }

                     alt62 = 2;
                  } else {
                     alt62 = 1;
                  }

                  label2134:
                  switch (alt62) {
                     case 1:
                        cnt61 = 0;

                        while(true) {
                           int alt61 = 2;
                           LA61_0 = this.input.LA(1);
                           if (LA61_0 == 4 || LA61_0 == 18 || LA61_0 == 21 || LA61_0 == 48 || LA61_0 == 57 || LA61_0 == 64 || LA61_0 == 80 || LA61_0 == 88 || LA61_0 == 94 || LA61_0 == 96) {
                              alt61 = 1;
                           }

                           switch (alt61) {
                              case 1:
                                 this.pushFollow(FOLLOW_rewrite_element_in_rewrite_alternative1841);
                                 el = this.rewrite_element();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return code;
                                 }

                                 if (this.state.backtracking == 0) {
                                    code.addAggr("elements.{el,line,pos}", el != null ? ((rewrite_element_return)el).code : null, (el != null ? (GrammarAST)el.start : null).getLine(), (el != null ? (GrammarAST)el.start : null).getCharPositionInLine() + 1);
                                 }

                                 ++cnt61;
                                 break;
                              default:
                                 if (cnt61 < 1) {
                                    if (this.state.backtracking > 0) {
                                       this.state.failed = true;
                                       return code;
                                    }

                                    EarlyExitException eee = new EarlyExitException(61, this.input);
                                    throw eee;
                                 }
                                 break label2134;
                           }
                        }
                     case 2:
                        this.match(this.input, 35, FOLLOW_EPSILON_in_rewrite_alternative1862);
                        if (this.state.failed) {
                           return code;
                        }

                        if (this.state.backtracking == 0) {
                           code.addAggr("elements.{el,line,pos}", this.templates.getInstanceOf("rewriteEmptyAlt"), a.getLine(), a.getCharPositionInLine() + 1);
                        }
                  }

                  this.match(this.input, 32, FOLLOW_EOA_in_rewrite_alternative1878);
                  if (this.state.failed) {
                     return code;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return code;
                  }
                  break;
               case 2:
                  if (!this.generator.grammar.buildTemplate()) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return code;
                     }

                     throw new FailedPredicateException(this.input, "rewrite_alternative", "generator.grammar.buildTemplate()");
                  }

                  this.pushFollow(FOLLOW_rewrite_template_in_rewrite_alternative1891);
                  rewrite_template14 = this.rewrite_template();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return code;
                  }

                  if (this.state.backtracking == 0) {
                     code = rewrite_template14;
                  }
                  break;
               case 3:
                  this.match(this.input, 37, FOLLOW_ETC_in_rewrite_alternative1904);
                  if (this.state.failed) {
                     return code;
                  }
            }
         } catch (RecognitionException var66) {
            this.reportError(var66);
            this.recover(this.input, var66);
         }

         return code;
      } finally {
         ;
      }
   }

   public final rewrite_element_return rewrite_element() throws RecognitionException {
      rewrite_element_return retval = new rewrite_element_return();
      retval.start = this.input.LT(1);
      TreeRuleReturnScope rewrite_atom15 = null;
      TreeRuleReturnScope rewrite_ebnf16 = null;
      TreeRuleReturnScope rewrite_tree17 = null;
      IntSet elements = null;
      GrammarAST ast = null;

      try {
         try {
            int alt64 = true;
            byte alt64;
            switch (this.input.LA(1)) {
               case 4:
               case 18:
               case 48:
               case 80:
               case 88:
               case 94:
                  alt64 = 1;
                  break;
               case 21:
               case 57:
               case 64:
                  alt64 = 2;
                  break;
               case 96:
                  alt64 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 64, 0, this.input);
                  throw nvae;
            }

            switch (alt64) {
               case 1:
                  this.pushFollow(FOLLOW_rewrite_atom_in_rewrite_element1924);
                  rewrite_atom15 = this.rewrite_atom(false);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = rewrite_atom15 != null ? ((rewrite_atom_return)rewrite_atom15).code : null;
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_rewrite_ebnf_in_rewrite_element1934);
                  rewrite_ebnf16 = this.rewrite_ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = rewrite_ebnf16 != null ? ((rewrite_ebnf_return)rewrite_ebnf16).code : null;
                  }
                  break;
               case 3:
                  this.pushFollow(FOLLOW_rewrite_tree_in_rewrite_element1943);
                  rewrite_tree17 = this.rewrite_tree();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = rewrite_tree17 != null ? ((rewrite_tree_return)rewrite_tree17).code : null;
                  }
            }
         } catch (RecognitionException var12) {
            this.reportError(var12);
            this.recover(this.input, var12);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_ebnf_return rewrite_ebnf() throws RecognitionException {
      rewrite_ebnf_return retval = new rewrite_ebnf_return();
      retval.start = this.input.LT(1);
      ST rewrite_block18 = null;
      ST rewrite_block19 = null;
      ST rewrite_block20 = null;

      try {
         try {
            int alt65 = true;
            byte alt65;
            switch (this.input.LA(1)) {
               case 21:
                  alt65 = 2;
                  break;
               case 57:
                  alt65 = 1;
                  break;
               case 64:
                  alt65 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 65, 0, this.input);
                  throw nvae;
            }

            String description;
            switch (alt65) {
               case 1:
                  this.match(this.input, 57, FOLLOW_OPTIONAL_in_rewrite_ebnf1964);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_rewrite_block_in_rewrite_ebnf1966);
                  rewrite_block18 = this.rewrite_block("rewriteOptionalBlock");
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = rewrite_block18;
                  }

                  if (this.state.backtracking == 0) {
                     description = this.grammar.grammarTreeToString((GrammarAST)retval.start, false);
                     description = this.generator.target.getTargetStringLiteralFromString(description);
                     retval.code.add("description", description);
                  }
                  break;
               case 2:
                  this.match(this.input, 21, FOLLOW_CLOSURE_in_rewrite_ebnf1984);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_rewrite_block_in_rewrite_ebnf1986);
                  rewrite_block19 = this.rewrite_block("rewriteClosureBlock");
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = rewrite_block19;
                  }

                  if (this.state.backtracking == 0) {
                     description = this.grammar.grammarTreeToString((GrammarAST)retval.start, false);
                     description = this.generator.target.getTargetStringLiteralFromString(description);
                     retval.code.add("description", description);
                  }
                  break;
               case 3:
                  this.match(this.input, 64, FOLLOW_POSITIVE_CLOSURE_in_rewrite_ebnf2004);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_rewrite_block_in_rewrite_ebnf2006);
                  rewrite_block20 = this.rewrite_block("rewritePositiveClosureBlock");
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.code = rewrite_block20;
                  }

                  if (this.state.backtracking == 0) {
                     description = this.grammar.grammarTreeToString((GrammarAST)retval.start, false);
                     description = this.generator.target.getTargetStringLiteralFromString(description);
                     retval.code.add("description", description);
                  }
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_tree_return rewrite_tree() throws RecognitionException {
      rewrite_tree_return retval = new rewrite_tree_return();
      retval.start = this.input.LT(1);
      TreeRuleReturnScope r = null;
      TreeRuleReturnScope el = null;
      ++this.rewriteTreeNestingLevel;
      if (this.state.backtracking == 0) {
         retval.code = this.templates.getInstanceOf("rewriteTree");
         retval.code.add("treeLevel", this.rewriteTreeNestingLevel);
         retval.code.add("enclosingTreeLevel", this.rewriteTreeNestingLevel - 1);
      }

      rewrite_tree_return var4;
      try {
         this.match(this.input, 96, FOLLOW_TREE_BEGIN_in_rewrite_tree2039);
         if (this.state.failed) {
            var4 = retval;
            return var4;
         }

         this.match(this.input, 2, (BitSet)null);
         if (this.state.failed) {
            var4 = retval;
            return var4;
         }

         this.pushFollow(FOLLOW_rewrite_atom_in_rewrite_tree2046);
         r = this.rewrite_atom(true);
         --this.state._fsp;
         if (!this.state.failed) {
            if (this.state.backtracking == 0) {
               retval.code.addAggr("root.{el,line,pos}", r != null ? ((rewrite_atom_return)r).code : null, (r != null ? (GrammarAST)r.start : null).getLine(), (r != null ? (GrammarAST)r.start : null).getCharPositionInLine() + 1);
            }

            while(true) {
               int alt66 = 2;
               int LA66_0 = this.input.LA(1);
               if (LA66_0 == 4 || LA66_0 == 18 || LA66_0 == 21 || LA66_0 == 48 || LA66_0 == 57 || LA66_0 == 64 || LA66_0 == 80 || LA66_0 == 88 || LA66_0 == 94 || LA66_0 == 96) {
                  alt66 = 1;
               }

               switch (alt66) {
                  case 1:
                     this.pushFollow(FOLLOW_rewrite_element_in_rewrite_tree2066);
                     el = this.rewrite_element();
                     --this.state._fsp;
                     if (this.state.failed) {
                        rewrite_tree_return var6 = retval;
                        return var6;
                     }

                     if (this.state.backtracking == 0) {
                        retval.code.addAggr("children.{el,line,pos}", el != null ? ((rewrite_element_return)el).code : null, (el != null ? (GrammarAST)el.start : null).getLine(), (el != null ? (GrammarAST)el.start : null).getCharPositionInLine() + 1);
                     }
                     break;
                  default:
                     this.match(this.input, 3, (BitSet)null);
                     if (this.state.failed) {
                        var4 = retval;
                        return var4;
                     }

                     if (this.state.backtracking == 0) {
                        String description = this.grammar.grammarTreeToString((GrammarAST)retval.start, false);
                        description = this.generator.target.getTargetStringLiteralFromString(description);
                        retval.code.add("description", description);
                     }

                     return retval;
               }
            }
         }

         var4 = retval;
      } catch (RecognitionException var10) {
         this.reportError(var10);
         this.recover(this.input, var10);
         return retval;
      } finally {
         --this.rewriteTreeNestingLevel;
      }

      return var4;
   }

   public final rewrite_atom_return rewrite_atom(boolean isRoot) throws RecognitionException {
      rewrite_atom_return retval = new rewrite_atom_return();
      retval.start = this.input.LT(1);
      GrammarAST r = null;
      GrammarAST tk = null;
      GrammarAST arg = null;
      GrammarAST cl = null;
      GrammarAST sl = null;
      GrammarAST LABEL21 = null;
      GrammarAST ACTION22 = null;

      try {
         try {
            int alt69 = true;
            byte alt69;
            switch (this.input.LA(1)) {
               case 4:
                  alt69 = 4;
                  break;
               case 18:
               case 88:
               case 94:
                  alt69 = 2;
                  break;
               case 48:
                  alt69 = 3;
                  break;
               case 80:
                  alt69 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 69, 0, this.input);
                  throw nvae;
            }

            String labelName;
            String stName;
            switch (alt69) {
               case 1:
                  r = (GrammarAST)this.match(this.input, 80, FOLLOW_RULE_REF_in_rewrite_atom2111);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     labelName = r != null ? r.getText() : null;
                     String stName = "rewriteRuleRef";
                     if (isRoot) {
                        stName = stName + "Root";
                     }

                     retval.code = this.templates.getInstanceOf(stName);
                     retval.code.add("rule", labelName);
                     if (this.grammar.getRule(labelName) == null) {
                        ErrorManager.grammarError(106, this.grammar, r.getToken(), labelName);
                        retval.code = new ST("");
                     } else if (this.grammar.getRule(this.currentRuleName).getRuleRefsInAlt(labelName, this.outerAltNum) == null) {
                        ErrorManager.grammarError(136, this.grammar, r.getToken(), labelName);
                        retval.code = new ST("");
                     } else if (!this.rewriteRuleRefs.contains(labelName)) {
                        this.rewriteRuleRefs.add(labelName);
                     }
                  }
                  break;
               case 2:
                  int alt68 = true;
                  byte alt68;
                  switch (this.input.LA(1)) {
                     case 18:
                        alt68 = 2;
                        break;
                     case 88:
                        alt68 = 3;
                        break;
                     case 94:
                        alt68 = 1;
                        break;
                     default:
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 68, 0, this.input);
                        throw nvae;
                  }

                  switch (alt68) {
                     case 1:
                        tk = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_rewrite_atom2128);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           if (this.state.failed) {
                              return retval;
                           }

                           int alt67 = 2;
                           int LA67_0 = this.input.LA(1);
                           if (LA67_0 == 12) {
                              alt67 = 1;
                           }

                           switch (alt67) {
                              case 1:
                                 arg = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rewrite_atom2133);
                                 if (this.state.failed) {
                                    return retval;
                                 }
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                                 if (this.state.failed) {
                                    return retval;
                                 }
                           }
                        }
                        break;
                     case 2:
                        cl = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_rewrite_atom2144);
                        if (this.state.failed) {
                           return retval;
                        }
                        break;
                     case 3:
                        sl = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_rewrite_atom2152);
                        if (this.state.failed) {
                           return retval;
                        }
                  }

                  if (this.state.backtracking == 0) {
                     GrammarAST term = tk;
                     if (tk == null) {
                        term = cl;
                     }

                     if (term == null) {
                        term = sl;
                     }

                     String tokenName = ((GrammarAST)retval.start).getToken().getText();
                     stName = "rewriteTokenRef";
                     Rule rule = this.grammar.getRule(this.currentRuleName);
                     Collection tokenRefsInAlt = rule.getTokenRefsInAlt(this.outerAltNum);
                     boolean createNewNode = !tokenRefsInAlt.contains(tokenName) || arg != null;
                     if (createNewNode) {
                        stName = "rewriteImaginaryTokenRef";
                     }

                     if (isRoot) {
                        stName = stName + "Root";
                     }

                     retval.code = this.templates.getInstanceOf(stName);
                     if (term.terminalOptions != null) {
                        retval.code.add("terminalOptions", term.terminalOptions);
                     }

                     if (arg != null) {
                        List args = this.generator.translateAction(this.currentRuleName, arg);
                        retval.code.add("args", args);
                     }

                     retval.code.add("elementIndex", ((CommonToken)((GrammarAST)retval.start).getToken()).getTokenIndex());
                     int ttype = this.grammar.getTokenType(tokenName);
                     String tok = this.generator.getTokenTypeAsTargetLabel(ttype);
                     retval.code.add("token", tok);
                     if (this.grammar.getTokenType(tokenName) == -7) {
                        ErrorManager.grammarError(135, this.grammar, ((GrammarAST)retval.start).getToken(), tokenName);
                        retval.code = new ST("");
                     }
                  }
                  break;
               case 3:
                  LABEL21 = (GrammarAST)this.match(this.input, 48, FOLLOW_LABEL_in_rewrite_atom2166);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     labelName = LABEL21 != null ? LABEL21.getText() : null;
                     Rule rule = this.grammar.getRule(this.currentRuleName);
                     Grammar.LabelElementPair pair = rule.getLabel(labelName);
                     if (labelName.equals(this.currentRuleName)) {
                        if (rule.hasRewrite(this.outerAltNum) && rule.getRuleRefsInAlt(this.outerAltNum).contains(labelName)) {
                           ErrorManager.grammarError(132, this.grammar, LABEL21.getToken(), labelName);
                        }

                        ST labelST = this.templates.getInstanceOf("prevRuleRootRef");
                        retval.code = this.templates.getInstanceOf("rewriteRuleLabelRef" + (isRoot ? "Root" : ""));
                        retval.code.add("label", labelST);
                     } else if (pair == null) {
                        ErrorManager.grammarError(137, this.grammar, LABEL21.getToken(), labelName);
                        retval.code = new ST("");
                     } else {
                        stName = null;
                        switch (pair.type) {
                           case 1:
                              stName = "rewriteRuleLabelRef";
                              break;
                           case 2:
                              stName = "rewriteTokenLabelRef";
                              break;
                           case 3:
                              stName = "rewriteRuleListLabelRef";
                              break;
                           case 4:
                              stName = "rewriteTokenListLabelRef";
                           case 5:
                           default:
                              break;
                           case 6:
                              stName = "rewriteWildcardLabelRef";
                              break;
                           case 7:
                              stName = "rewriteRuleListLabelRef";
                        }

                        if (isRoot) {
                           stName = stName + "Root";
                        }

                        retval.code = this.templates.getInstanceOf(stName);
                        retval.code.add("label", labelName);
                     }
                  }
                  break;
               case 4:
                  ACTION22 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_atom2176);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     if (ACTION22 != null) {
                        ACTION22.getText();
                     } else {
                        Object var10000 = null;
                     }

                     List chunks = this.generator.translateAction(this.currentRuleName, ACTION22);
                     retval.code = this.templates.getInstanceOf("rewriteNodeAction" + (isRoot ? "Root" : ""));
                     retval.code.add("action", chunks);
                  }
            }
         } catch (RecognitionException var23) {
            this.reportError(var23);
            this.recover(this.input, var23);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final ST rewrite_template() throws RecognitionException {
      ST code = null;
      GrammarAST id = null;
      GrammarAST ind = null;
      GrammarAST arg = null;
      GrammarAST a = null;
      GrammarAST act = null;
      GrammarAST DOUBLE_QUOTE_STRING_LITERAL23 = null;
      GrammarAST DOUBLE_ANGLE_STRING_LITERAL24 = null;

      try {
         try {
            int alt73 = true;
            byte alt73;
            switch (this.input.LA(1)) {
               case 4:
                  alt73 = 3;
                  break;
               case 8:
                  alt73 = 1;
                  break;
               case 91:
                  alt73 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return code;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 73, 0, this.input);
                  throw nvae;
            }

            switch (alt73) {
               case 1:
                  this.match(this.input, 8, FOLLOW_ALT_in_rewrite_template2199);
                  if (this.state.failed) {
                     return code;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return code;
                  }

                  this.match(this.input, 35, FOLLOW_EPSILON_in_rewrite_template2201);
                  if (this.state.failed) {
                     return code;
                  }

                  this.match(this.input, 32, FOLLOW_EOA_in_rewrite_template2203);
                  if (this.state.failed) {
                     return code;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return code;
                  }

                  if (this.state.backtracking == 0) {
                     code = this.templates.getInstanceOf("rewriteEmptyTemplate");
                  }
                  break;
               case 2:
                  this.match(this.input, 91, FOLLOW_TEMPLATE_in_rewrite_template2214);
                  if (this.state.failed) {
                     return code;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return code;
                  }

                  int alt70 = true;
                  int LA70_0 = this.input.LA(1);
                  byte alt70;
                  if (LA70_0 == 43) {
                     alt70 = 1;
                  } else {
                     if (LA70_0 != 4) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return code;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 70, 0, this.input);
                        throw nvae;
                     }

                     alt70 = 2;
                  }

                  switch (alt70) {
                     case 1:
                        id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rewrite_template2219);
                        if (this.state.failed) {
                           return code;
                        }
                        break;
                     case 2:
                        ind = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template2223);
                        if (this.state.failed) {
                           return code;
                        }
                  }

                  if (this.state.backtracking == 0) {
                     if (id != null && (id != null ? id.getText() : null).equals("template")) {
                        code = this.templates.getInstanceOf("rewriteInlineTemplate");
                     } else if (id != null) {
                        code = this.templates.getInstanceOf("rewriteExternalTemplate");
                        code.add("name", id != null ? id.getText() : null);
                     } else if (ind != null) {
                        code = this.templates.getInstanceOf("rewriteIndirectTemplate");
                        List chunks = this.generator.translateAction(this.currentRuleName, ind);
                        code.add("expr", chunks);
                     }
                  }

                  this.match(this.input, 11, FOLLOW_ARGLIST_in_rewrite_template2236);
                  if (this.state.failed) {
                     return code;
                  }

                  int LA72_0;
                  byte alt71;
                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return code;
                     }

                     label469:
                     while(true) {
                        alt71 = 2;
                        LA72_0 = this.input.LA(1);
                        if (LA72_0 == 10) {
                           alt71 = 1;
                        }

                        switch (alt71) {
                           case 1:
                              this.match(this.input, 10, FOLLOW_ARG_in_rewrite_template2246);
                              if (this.state.failed) {
                                 return code;
                              }

                              this.match(this.input, 2, (BitSet)null);
                              if (this.state.failed) {
                                 return code;
                              }

                              arg = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rewrite_template2250);
                              if (this.state.failed) {
                                 return code;
                              }

                              a = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template2254);
                              if (this.state.failed) {
                                 return code;
                              }

                              if (this.state.backtracking == 0) {
                                 a.outerAltNum = this.outerAltNum;
                                 List chunks = this.generator.translateAction(this.currentRuleName, a);
                                 code.addAggr("args.{name,value}", arg != null ? arg.getText() : null, chunks);
                              }

                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return code;
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return code;
                              }
                              break label469;
                        }
                     }
                  }

                  alt71 = 3;
                  LA72_0 = this.input.LA(1);
                  if (LA72_0 == 31) {
                     alt71 = 1;
                  } else if (LA72_0 == 30) {
                     alt71 = 2;
                  }

                  String t;
                  String sl;
                  switch (alt71) {
                     case 1:
                        DOUBLE_QUOTE_STRING_LITERAL23 = (GrammarAST)this.match(this.input, 31, FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template2287);
                        if (this.state.failed) {
                           return code;
                        }

                        if (this.state.backtracking == 0) {
                           sl = DOUBLE_QUOTE_STRING_LITERAL23 != null ? DOUBLE_QUOTE_STRING_LITERAL23.getText() : null;
                           t = sl.substring(1, sl.length() - 1);
                           t = this.generator.target.getTargetStringLiteralFromString(t);
                           code.add("template", t);
                        }
                        break;
                     case 2:
                        DOUBLE_ANGLE_STRING_LITERAL24 = (GrammarAST)this.match(this.input, 30, FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template2300);
                        if (this.state.failed) {
                           return code;
                        }

                        if (this.state.backtracking == 0) {
                           sl = DOUBLE_ANGLE_STRING_LITERAL24 != null ? DOUBLE_ANGLE_STRING_LITERAL24.getText() : null;
                           t = sl.substring(2, sl.length() - 2);
                           t = this.generator.target.getTargetStringLiteralFromString(t);
                           code.add("template", t);
                        }
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return code;
                  }
                  break;
               case 3:
                  act = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template2324);
                  if (this.state.failed) {
                     return code;
                  }

                  if (this.state.backtracking == 0) {
                     act.outerAltNum = this.outerAltNum;
                     code = this.templates.getInstanceOf("rewriteAction");
                     code.add("action", this.generator.translateAction(this.currentRuleName, act));
                  }
            }
         } catch (RecognitionException var19) {
            this.reportError(var19);
            this.recover(this.input, var19);
         }

         return code;
      } finally {
         ;
      }
   }

   public final void synpred1_CodeGenTreeWalker_fragment() throws RecognitionException {
      if (((GrammarAST)this.input.LT(1)).getSetValue() != null) {
         if (this.state.backtracking > 0) {
            this.state.failed = true;
         } else {
            throw new FailedPredicateException(this.input, "synpred1_CodeGenTreeWalker", "((GrammarAST)input.LT(1)).getSetValue()==null");
         }
      } else if (this.input.LA(1) != 16 && this.input.LA(1) != 21 && this.input.LA(1) != 57 && this.input.LA(1) != 64) {
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
   }

   public final void synpred2_CodeGenTreeWalker_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_element_action_in_synpred2_CodeGenTreeWalker1405);
      this.element_action();
      --this.state._fsp;
      if (!this.state.failed) {
         ;
      }
   }

   public final boolean synpred1_CodeGenTreeWalker() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred1_CodeGenTreeWalker_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred2_CodeGenTreeWalker() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred2_CodeGenTreeWalker_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public static class rewrite_atom_return extends TreeRuleReturnScope {
      public ST code = null;
   }

   public static class rewrite_tree_return extends TreeRuleReturnScope {
      public ST code;
   }

   public static class rewrite_ebnf_return extends TreeRuleReturnScope {
      public ST code = null;
   }

   public static class rewrite_element_return extends TreeRuleReturnScope {
      public ST code = null;
   }

   public static class rewrite_return extends TreeRuleReturnScope {
      public ST code = null;
   }

   public static class atom_return extends TreeRuleReturnScope {
      public ST code = null;
   }

   public static class tree__return extends TreeRuleReturnScope {
      public ST code;
   }

   public static class ebnf_return extends TreeRuleReturnScope {
      public ST code = null;
   }

   public static class element_action_return extends TreeRuleReturnScope {
      public ST code = null;
   }

   public static class element_return extends TreeRuleReturnScope {
      public ST code = null;
   }

   public static class alternative_return extends TreeRuleReturnScope {
      public ST code;
   }

   public static class setBlock_return extends TreeRuleReturnScope {
      public ST code = null;
   }

   public static class block_return extends TreeRuleReturnScope {
      public ST code = null;
   }

   public static class modifier_return extends TreeRuleReturnScope {
   }

   public static class rule_return extends TreeRuleReturnScope {
      public ST code = null;
   }
}
