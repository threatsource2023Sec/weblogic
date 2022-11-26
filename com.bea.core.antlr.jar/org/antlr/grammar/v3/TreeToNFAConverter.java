package org.antlr.grammar.v3;

import java.util.ArrayList;
import java.util.List;
import org.antlr.analysis.NFA;
import org.antlr.analysis.NFAState;
import org.antlr.analysis.RuleClosureTransition;
import org.antlr.analysis.StateCluster;
import org.antlr.analysis.Transition;
import org.antlr.misc.IntSet;
import org.antlr.misc.IntervalSet;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;
import org.antlr.tool.NFAFactory;
import org.antlr.tool.Rule;

public class TreeToNFAConverter extends TreeParser {
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
   protected NFAFactory factory;
   protected NFA nfa;
   protected Grammar grammar;
   protected String currentRuleName;
   protected int outerAltNum;
   protected int blockLevel;
   protected int inTest;
   public static final BitSet FOLLOW_LEXER_GRAMMAR_in_grammar_68 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_70 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PARSER_GRAMMAR_in_grammar_80 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_82 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_GRAMMAR_in_grammar_92 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_94 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_COMBINED_GRAMMAR_in_grammar_104 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_106 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_SCOPE_in_attrScope125 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_attrScope127 = new BitSet(new long[]{528L});
   public static final BitSet FOLLOW_AMPERSAND_in_attrScope132 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_attrScope141 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_grammarSpec154 = new BitSet(new long[]{288265560658018816L, 537034754L});
   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarSpec161 = new BitSet(new long[]{288265560523801088L, 537034754L});
   public static final BitSet FOLLOW_OPTIONS_in_grammarSpec170 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_IMPORT_in_grammarSpec184 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_TOKENS_in_grammarSpec198 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_attrScope_in_grammarSpec210 = new BitSet(new long[]{512L, 163842L});
   public static final BitSet FOLLOW_AMPERSAND_in_grammarSpec219 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rules_in_grammarSpec231 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rule_in_rules243 = new BitSet(new long[]{2L, 32770L});
   public static final BitSet FOLLOW_PREC_RULE_in_rules248 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_RULE_in_rule267 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rule271 = new BitSet(new long[]{1099511628800L, 28L});
   public static final BitSet FOLLOW_modifier_in_rule282 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_ARG_in_rule290 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rule293 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RET_in_rule302 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rule305 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_throwsSpec_in_rule314 = new BitSet(new long[]{288230376151777792L, 131072L});
   public static final BitSet FOLLOW_OPTIONS_in_rule324 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ruleScopeSpec_in_rule338 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_AMPERSAND_in_rule349 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_rule363 = new BitSet(new long[]{292057907200L});
   public static final BitSet FOLLOW_exceptionGroup_in_rule369 = new BitSet(new long[]{17179869184L});
   public static final BitSet FOLLOW_EOR_in_rule376 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_THROWS_in_throwsSpec423 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_throwsSpec425 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec440 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_AMPERSAND_in_ruleScopeSpec445 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec455 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_ID_in_ruleScopeSpec461 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_set_in_block492 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BLOCK_in_block502 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_OPTIONS_in_block507 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_alternative_in_block523 = new BitSet(new long[]{8589934848L, 4096L});
   public static final BitSet FOLLOW_rewrite_in_block525 = new BitSet(new long[]{8589934848L});
   public static final BitSet FOLLOW_EOB_in_block548 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_alternative577 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_alternative582 = new BitSet(new long[]{-9043225263786303472L, 22666616897L});
   public static final BitSet FOLLOW_EOA_in_alternative589 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup608 = new BitSet(new long[]{274878038018L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup614 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup621 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CATCH_in_exceptionHandler636 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler638 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_exceptionHandler640 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_FINALLY_in_finallyClause656 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_finallyClause658 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_REWRITES_in_rewrite672 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_REWRITE_in_rewrite690 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ROOT_in_element725 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element729 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BANG_in_element740 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element744 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ASSIGN_in_element753 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element755 = new BitSet(new long[]{-9043225268081270768L, 22666616897L});
   public static final BitSet FOLLOW_element_in_element759 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PLUS_ASSIGN_in_element768 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element770 = new BitSet(new long[]{-9043225268081270768L, 22666616897L});
   public static final BitSet FOLLOW_element_in_element774 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RANGE_in_element785 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_atom_in_element789 = new BitSet(new long[]{537133056L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_element794 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_RANGE_in_element808 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_element812 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_element816 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_atom_or_notatom_in_element828 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ebnf_in_element837 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_tree__in_element846 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYNPRED_in_element857 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_element859 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ACTION_in_element868 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FORCED_ACTION_in_element877 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SEMPRED_in_element888 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYN_SEMPRED_in_element899 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_element911 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_GATED_SEMPRED_in_element926 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_EPSILON_in_element935 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_set_in_ebnf961 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_block_in_ebnf971 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPTIONAL_in_ebnf982 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf986 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CLOSURE_in_ebnf999 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1003 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_ebnf1016 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1020 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_BEGIN_in_tree_1048 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_tree_1055 = new BitSet(new long[]{-9043225268081270760L, 22666616897L});
   public static final BitSet FOLLOW_element_in_tree_1071 = new BitSet(new long[]{-9043225268081270760L, 22666616897L});
   public static final BitSet FOLLOW_atom_in_atom_or_notatom1100 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NOT_in_atom_or_notatom1112 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom_or_notatom1121 = new BitSet(new long[]{32776L, 8192L});
   public static final BitSet FOLLOW_ast_suffix_in_atom_or_notatom1126 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TOKEN_REF_in_atom_or_notatom1143 = new BitSet(new long[]{32776L, 8192L});
   public static final BitSet FOLLOW_ast_suffix_in_atom_or_notatom1148 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_set_in_atom_or_notatom1163 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RULE_REF_in_atom1205 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1210 = new BitSet(new long[]{32776L, 8192L});
   public static final BitSet FOLLOW_ast_suffix_in_atom1217 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TOKEN_REF_in_atom1235 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1241 = new BitSet(new long[]{32776L, 8192L});
   public static final BitSet FOLLOW_ast_suffix_in_atom1248 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom1266 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ast_suffix_in_atom1272 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_atom1290 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ast_suffix_in_atom1296 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_WILDCARD_in_atom1314 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ast_suffix_in_atom1319 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_DOT_in_atom1336 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_atom1340 = new BitSet(new long[]{537133056L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_atom1344 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BLOCK_in_set1390 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ALT_in_set1399 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_set1404 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_setElement_in_set1413 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_set1416 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_EOB_in_set1426 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RULE_in_setRule1460 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_setRule1464 = new BitSet(new long[]{1099511628800L, 28L});
   public static final BitSet FOLLOW_modifier_in_setRule1467 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_ARG_in_setRule1471 = new BitSet(new long[]{0L, 512L});
   public static final BitSet FOLLOW_RET_in_setRule1473 = new BitSet(new long[]{288230376151777792L, 131072L});
   public static final BitSet FOLLOW_OPTIONS_in_setRule1478 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ruleScopeSpec_in_setRule1489 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_AMPERSAND_in_setRule1500 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BLOCK_in_setRule1514 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_OPTIONS_in_setRule1519 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ALT_in_setRule1537 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_setRule1540 = new BitSet(new long[]{36028797019815936L, 1090519040L});
   public static final BitSet FOLLOW_setElement_in_setRule1544 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_setRule1547 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_EOB_in_setRule1559 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_exceptionGroup_in_setRule1571 = new BitSet(new long[]{17179869184L});
   public static final BitSet FOLLOW_EOR_in_setRule1578 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_setElement1607 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_setElement1618 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_setElement1630 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_RANGE_in_setElement1640 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_setElement1644 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_setElement1648 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_set_in_setElement1661 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NOT_in_setElement1673 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_setElement_in_setElement1680 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BLOCK_in_testBlockAsSet1725 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ALT_in_testBlockAsSet1733 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_testBlockAsSet1736 = new BitSet(new long[]{36028797019815936L, 1090519040L});
   public static final BitSet FOLLOW_testSetElement_in_testBlockAsSet1740 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_testBlockAsSet1744 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_EOB_in_testBlockAsSet1756 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RULE_in_testSetRule1791 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_testSetRule1795 = new BitSet(new long[]{1099511628800L, 28L});
   public static final BitSet FOLLOW_modifier_in_testSetRule1798 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_ARG_in_testSetRule1802 = new BitSet(new long[]{0L, 512L});
   public static final BitSet FOLLOW_RET_in_testSetRule1804 = new BitSet(new long[]{288230376151777792L, 131072L});
   public static final BitSet FOLLOW_OPTIONS_in_testSetRule1809 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ruleScopeSpec_in_testSetRule1820 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_AMPERSAND_in_testSetRule1831 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BLOCK_in_testSetRule1845 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ALT_in_testSetRule1854 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_testSetRule1857 = new BitSet(new long[]{36028797019815936L, 1090519040L});
   public static final BitSet FOLLOW_testSetElement_in_testSetRule1861 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_testSetRule1865 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_EOB_in_testSetRule1879 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_exceptionGroup_in_testSetRule1890 = new BitSet(new long[]{17179869184L});
   public static final BitSet FOLLOW_EOR_in_testSetRule1897 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_testSetElement1929 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_testSetElement1938 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_testSetElement1957 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_RANGE_in_testSetElement1963 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_testSetElement1967 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_testSetElement1971 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_testBlockAsSet_in_testSetElement1983 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NOT_in_testSetElement1996 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_testSetElement_in_testSetElement2000 = new BitSet(new long[]{8L});

   public TreeParser[] getDelegates() {
      return new TreeParser[0];
   }

   public TreeToNFAConverter(TreeNodeStream input) {
      this(input, new RecognizerSharedState());
   }

   public TreeToNFAConverter(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
      this.factory = null;
      this.nfa = null;
      this.grammar = null;
      this.currentRuleName = null;
      this.outerAltNum = 0;
      this.blockLevel = 0;
      this.inTest = 0;
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\antlr\\grammar\\v3\\TreeToNFAConverter.g";
   }

   public TreeToNFAConverter(TreeNodeStream input, Grammar g, NFA nfa, NFAFactory factory) {
      this(input);
      this.grammar = g;
      this.nfa = nfa;
      this.factory = factory;
   }

   public final IntSet setRule(GrammarAST t) throws RecognitionException {
      TreeToNFAConverter other = new TreeToNFAConverter(new CommonTreeNodeStream(t), this.grammar, this.nfa, this.factory);
      other.currentRuleName = this.currentRuleName;
      other.outerAltNum = this.outerAltNum;
      other.blockLevel = this.blockLevel;
      return other.setRule();
   }

   public final int testBlockAsSet(GrammarAST t) throws RecognitionException {
      Rule r = this.grammar.getLocallyDefinedRule(this.currentRuleName);
      if (r.hasRewrite(this.outerAltNum)) {
         return -1;
      } else {
         TreeToNFAConverter other = new TreeToNFAConverter(new CommonTreeNodeStream(t), this.grammar, this.nfa, this.factory);
         ++other.state.backtracking;
         other.currentRuleName = this.currentRuleName;
         other.outerAltNum = this.outerAltNum;
         other.blockLevel = this.blockLevel;
         int result = other.testBlockAsSet();
         return other.state.failed ? -1 : result;
      }
   }

   public final int testSetRule(GrammarAST t) throws RecognitionException {
      TreeToNFAConverter other = new TreeToNFAConverter(new CommonTreeNodeStream(t), this.grammar, this.nfa, this.factory);
      ++other.state.backtracking;
      other.currentRuleName = this.currentRuleName;
      other.outerAltNum = this.outerAltNum;
      other.blockLevel = this.blockLevel;
      int result = other.testSetRule();
      if (other.state.failed) {
         this.state.failed = true;
      }

      return result;
   }

   protected void addFollowTransition(String ruleName, NFAState following) {
      Rule r = this.grammar.getRule(ruleName);

      NFAState end;
      for(end = r.stopState; end.transition(1) != null; end = (NFAState)end.transition(1).target) {
      }

      if (end.transition(0) != null) {
         NFAState n = this.factory.newState();
         Transition e = new Transition(-5, n);
         end.addTransition(e);
         end = n;
      }

      Transition followEdge = new Transition(-5, following);
      end.addTransition(followEdge);
   }

   protected void finish() {
      int numEntryPoints = this.factory.build_EOFStates(this.grammar.getRules());
      if (numEntryPoints == 0) {
         ErrorManager.grammarWarning(138, this.grammar, (Token)null, this.grammar.name);
      }

   }

   public void reportError(RecognitionException ex) {
      if (this.inTest > 0) {
         throw new IllegalStateException(ex);
      } else {
         Token token = null;
         if (ex instanceof MismatchedTokenException) {
            token = ((MismatchedTokenException)ex).token;
         } else if (ex instanceof NoViableAltException) {
            token = ((NoViableAltException)ex).token;
         }

         ErrorManager.syntaxError(100, this.grammar, token, "buildnfa: " + ex.toString(), ex);
      }
   }

   private boolean hasElementOptions(GrammarAST node) {
      if (node == null) {
         throw new NullPointerException("node");
      } else {
         return node.terminalOptions != null && node.terminalOptions.size() > 0;
      }
   }

   public final void grammar_() throws RecognitionException {
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
                  this.match(this.input, 50, FOLLOW_LEXER_GRAMMAR_in_grammar_68);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_70);
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
                  this.match(this.input, 61, FOLLOW_PARSER_GRAMMAR_in_grammar_80);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_82);
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
                  this.match(this.input, 97, FOLLOW_TREE_GRAMMAR_in_grammar_92);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_94);
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
                  this.match(this.input, 23, FOLLOW_COMBINED_GRAMMAR_in_grammar_104);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_106);
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

            if (this.state.backtracking == 0) {
               this.finish();
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void attrScope() throws RecognitionException {
      try {
         this.match(this.input, 81, FOLLOW_SCOPE_in_attrScope125);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               this.match(this.input, 43, FOLLOW_ID_in_attrScope127);
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
                              this.match(this.input, 9, FOLLOW_AMPERSAND_in_attrScope132);
                              if (this.state.failed) {
                                 return;
                              }
                              break;
                           default:
                              this.match(this.input, 4, FOLLOW_ACTION_in_attrScope141);
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
      GrammarAST cmt = null;

      try {
         this.match(this.input, 43, FOLLOW_ID_in_grammarSpec154);
         if (!this.state.failed) {
            int alt4 = 2;
            int LA4_0 = this.input.LA(1);
            if (LA4_0 == 27) {
               alt4 = 1;
            }

            switch (alt4) {
               case 1:
                  cmt = (GrammarAST)this.match(this.input, 27, FOLLOW_DOC_COMMENT_in_grammarSpec161);
                  if (this.state.failed) {
                     return;
                  }
               default:
                  int alt6 = 2;
                  int LA6_0 = this.input.LA(1);
                  if (LA6_0 == 58) {
                     alt6 = 1;
                  }

                  byte alt5;
                  int LA5_0;
                  switch (alt6) {
                     case 1:
                        this.match(this.input, 58, FOLLOW_OPTIONS_in_grammarSpec170);
                        if (this.state.failed) {
                           return;
                        }

                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           if (this.state.failed) {
                              return;
                           }

                           label485:
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
                                    break label485;
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
                        this.match(this.input, 45, FOLLOW_IMPORT_in_grammarSpec184);
                        if (this.state.failed) {
                           return;
                        }

                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           if (this.state.failed) {
                              return;
                           }

                           label468:
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
                                    break label468;
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
                        this.match(this.input, 93, FOLLOW_TOKENS_in_grammarSpec198);
                        if (this.state.failed) {
                           return;
                        }

                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           if (this.state.failed) {
                              return;
                           }

                           label416:
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
                                    break label416;
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
                           this.pushFollow(FOLLOW_attrScope_in_grammarSpec210);
                           this.attrScope();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }
                           break;
                        default:
                           label452:
                           while(true) {
                              do {
                                 alt9 = 2;
                                 LA9_0 = this.input.LA(1);
                                 if (LA9_0 == 9) {
                                    alt9 = 1;
                                 }

                                 switch (alt9) {
                                    case 1:
                                       this.match(this.input, 9, FOLLOW_AMPERSAND_in_grammarSpec219);
                                       if (this.state.failed) {
                                          return;
                                       }
                                       break;
                                    default:
                                       this.pushFollow(FOLLOW_rules_in_grammarSpec231);
                                       this.rules();
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
                                       continue label452;
                                 }
                              }
                           }
                     }
                  }
            }
         }
      } catch (RecognitionException var17) {
         this.reportError(var17);
         this.recover(this.input, var17);
      } finally {
         ;
      }
   }

   public final void rules() throws RecognitionException {
      try {
         int cnt15 = 0;

         while(true) {
            int alt15 = 3;
            int LA15_0 = this.input.LA(1);
            if (LA15_0 == 79) {
               alt15 = 1;
            } else if (LA15_0 == 65) {
               alt15 = 2;
            }

            label147:
            switch (alt15) {
               case 1:
                  this.pushFollow(FOLLOW_rule_in_rules243);
                  this.rule();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.match(this.input, 65, FOLLOW_PREC_RULE_in_rules248);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) != 2) {
                     break;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  while(true) {
                     int alt14 = 2;
                     int LA14_0 = this.input.LA(1);
                     if (LA14_0 >= 4 && LA14_0 <= 102) {
                        alt14 = 1;
                     } else if (LA14_0 == 3) {
                        alt14 = 2;
                     }

                     switch (alt14) {
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
                           break label147;
                     }
                  }
               default:
                  if (cnt15 < 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     EarlyExitException eee = new EarlyExitException(15, this.input);
                     throw eee;
                  }

                  return;
            }

            ++cnt15;
         }
      } catch (RecognitionException var9) {
         this.reportError(var9);
         this.recover(this.input, var9);
      } finally {
         ;
      }
   }

   public final rule_return rule() throws RecognitionException {
      rule_return retval = new rule_return();
      retval.start = this.input.LT(1);
      GrammarAST id = null;
      TreeRuleReturnScope b = null;

      try {
         this.match(this.input, 79, FOLLOW_RULE_in_rule267);
         if (this.state.failed) {
            return retval;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return retval;
            } else {
               id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rule271);
               if (this.state.failed) {
                  return retval;
               } else {
                  if (this.state.backtracking == 0) {
                     this.currentRuleName = id != null ? id.getText() : null;
                     this.factory.setCurrentRule(this.grammar.getLocallyDefinedRule(this.currentRuleName));
                  }

                  int alt16 = 2;
                  int LA16_0 = this.input.LA(1);
                  if (LA16_0 == 40 || LA16_0 >= 66 && LA16_0 <= 68) {
                     alt16 = 1;
                  }

                  switch (alt16) {
                     case 1:
                        this.pushFollow(FOLLOW_modifier_in_rule282);
                        this.modifier();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }
                     default:
                        this.match(this.input, 10, FOLLOW_ARG_in_rule290);
                        if (this.state.failed) {
                           return retval;
                        } else {
                           byte alt18;
                           int LA18_0;
                           if (this.input.LA(1) == 2) {
                              this.match(this.input, 2, (BitSet)null);
                              if (this.state.failed) {
                                 return retval;
                              }

                              alt18 = 2;
                              LA18_0 = this.input.LA(1);
                              if (LA18_0 == 12) {
                                 alt18 = 1;
                              }

                              switch (alt18) {
                                 case 1:
                                    this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rule293);
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

                           this.match(this.input, 73, FOLLOW_RET_in_rule302);
                           if (this.state.failed) {
                              return retval;
                           } else {
                              if (this.input.LA(1) == 2) {
                                 this.match(this.input, 2, (BitSet)null);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 alt18 = 2;
                                 LA18_0 = this.input.LA(1);
                                 if (LA18_0 == 12) {
                                    alt18 = 1;
                                 }

                                 switch (alt18) {
                                    case 1:
                                       this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rule305);
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

                              alt18 = 2;
                              LA18_0 = this.input.LA(1);
                              if (LA18_0 == 92) {
                                 alt18 = 1;
                              }

                              switch (alt18) {
                                 case 1:
                                    this.pushFollow(FOLLOW_throwsSpec_in_rule314);
                                    this.throwsSpec();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }
                                 default:
                                    int alt21 = 2;
                                    int LA21_0 = this.input.LA(1);
                                    if (LA21_0 == 58) {
                                       alt21 = 1;
                                    }

                                    byte alt20;
                                    int LA20_0;
                                    switch (alt21) {
                                       case 1:
                                          this.match(this.input, 58, FOLLOW_OPTIONS_in_rule324);
                                          if (this.state.failed) {
                                             return retval;
                                          }

                                          if (this.input.LA(1) == 2) {
                                             this.match(this.input, 2, (BitSet)null);
                                             if (this.state.failed) {
                                                return retval;
                                             }

                                             label613:
                                             while(true) {
                                                alt20 = 2;
                                                LA20_0 = this.input.LA(1);
                                                if (LA20_0 >= 4 && LA20_0 <= 102) {
                                                   alt20 = 1;
                                                } else if (LA20_0 == 3) {
                                                   alt20 = 2;
                                                }

                                                switch (alt20) {
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
                                                      break label613;
                                                }
                                             }
                                          }
                                    }

                                    alt20 = 2;
                                    LA20_0 = this.input.LA(1);
                                    if (LA20_0 == 81) {
                                       alt20 = 1;
                                    }

                                    switch (alt20) {
                                       case 1:
                                          this.pushFollow(FOLLOW_ruleScopeSpec_in_rule338);
                                          this.ruleScopeSpec();
                                          --this.state._fsp;
                                          if (this.state.failed) {
                                             return retval;
                                          }
                                    }

                                    label597:
                                    while(true) {
                                       do {
                                          int alt25 = 2;
                                          int LA25_0 = this.input.LA(1);
                                          if (LA25_0 == 9) {
                                             alt25 = 1;
                                          }

                                          switch (alt25) {
                                             case 1:
                                                this.match(this.input, 9, FOLLOW_AMPERSAND_in_rule349);
                                                if (this.state.failed) {
                                                   return retval;
                                                }
                                                break;
                                             default:
                                                this.pushFollow(FOLLOW_block_in_rule363);
                                                b = this.block();
                                                --this.state._fsp;
                                                if (this.state.failed) {
                                                   return retval;
                                                }

                                                alt25 = 2;
                                                LA25_0 = this.input.LA(1);
                                                if (LA25_0 == 17 || LA25_0 == 38) {
                                                   alt25 = 1;
                                                }

                                                switch (alt25) {
                                                   case 1:
                                                      this.pushFollow(FOLLOW_exceptionGroup_in_rule369);
                                                      this.exceptionGroup();
                                                      --this.state._fsp;
                                                      if (this.state.failed) {
                                                         return retval;
                                                      }
                                                   default:
                                                      this.match(this.input, 34, FOLLOW_EOR_in_rule376);
                                                      if (this.state.failed) {
                                                         return retval;
                                                      }

                                                      if (this.state.backtracking == 0) {
                                                         StateCluster g = b != null ? ((block_return)b).g : null;
                                                         if ((b != null ? (GrammarAST)b.start : null).getSetValue() != null) {
                                                            g = this.factory.build_AlternativeBlockFromSet(g);
                                                         }

                                                         if (Rule.getRuleType(this.currentRuleName) == 2 || this.grammar.type == 1) {
                                                            Rule thisR = this.grammar.getLocallyDefinedRule(this.currentRuleName);
                                                            NFAState start = thisR.startState;
                                                            start.associatedASTNode = id;
                                                            start.addTransition(new Transition(-5, g.left));
                                                            if (this.grammar.getNumberOfAltsForDecisionNFA(g.left) > 1) {
                                                               g.left.setDescription(this.grammar.grammarTreeToString((GrammarAST)retval.start, false));
                                                               g.left.setDecisionASTNode(b != null ? (GrammarAST)b.start : null);
                                                               int d = this.grammar.assignDecisionNumber(g.left);
                                                               this.grammar.setDecisionNFA(d, g.left);
                                                               this.grammar.setDecisionBlockAST(d, b != null ? (GrammarAST)b.start : null);
                                                            }

                                                            NFAState end = thisR.stopState;
                                                            g.right.addTransition(new Transition(-5, end));
                                                         }
                                                      }

                                                      this.match(this.input, 3, (BitSet)null);
                                                      if (this.state.failed) {
                                                         return retval;
                                                      }

                                                      return retval;
                                                }
                                          }
                                       } while(this.input.LA(1) != 2);

                                       this.match(this.input, 2, (BitSet)null);
                                       if (this.state.failed) {
                                          return retval;
                                       }

                                       while(true) {
                                          int alt23 = 2;
                                          int LA23_0 = this.input.LA(1);
                                          if (LA23_0 >= 4 && LA23_0 <= 102) {
                                             alt23 = 1;
                                          } else if (LA23_0 == 3) {
                                             alt23 = 2;
                                          }

                                          switch (alt23) {
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
                                                continue label597;
                                          }
                                       }
                                    }
                              }
                           }
                        }
                  }
               }
            }
         }
      } catch (RecognitionException var21) {
         this.reportError(var21);
         this.recover(this.input, var21);
         return retval;
      } finally {
         ;
      }
   }

   public final void modifier() throws RecognitionException {
      try {
         try {
            if (this.input.LA(1) != 40 && (this.input.LA(1) < 66 || this.input.LA(1) > 68)) {
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

   public final void throwsSpec() throws RecognitionException {
      try {
         this.match(this.input, 92, FOLLOW_THROWS_in_throwsSpec423);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int cnt26 = 0;

               while(true) {
                  int alt26 = 2;
                  int LA26_0 = this.input.LA(1);
                  if (LA26_0 == 43) {
                     alt26 = 1;
                  }

                  switch (alt26) {
                     case 1:
                        this.match(this.input, 43, FOLLOW_ID_in_throwsSpec425);
                        if (this.state.failed) {
                           return;
                        }

                        ++cnt26;
                        break;
                     default:
                        if (cnt26 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(26, this.input);
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
            this.match(this.input, 81, FOLLOW_SCOPE_in_ruleScopeSpec440);
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
                  byte alt30;
                  int LA30_0;
                  do {
                     int alt29 = 2;
                     int LA29_0 = this.input.LA(1);
                     if (LA29_0 == 9) {
                        alt29 = 1;
                     }

                     switch (alt29) {
                        case 1:
                           this.match(this.input, 9, FOLLOW_AMPERSAND_in_ruleScopeSpec445);
                           if (this.state.failed) {
                              return;
                           }
                           break;
                        default:
                           alt29 = 2;
                           LA29_0 = this.input.LA(1);
                           if (LA29_0 == 4) {
                              alt29 = 1;
                           }

                           switch (alt29) {
                              case 1:
                                 this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec455);
                                 if (this.state.failed) {
                                    return;
                                 }
                           }

                           while(true) {
                              alt30 = 2;
                              LA30_0 = this.input.LA(1);
                              if (LA30_0 == 43) {
                                 alt30 = 1;
                              }

                              switch (alt30) {
                                 case 1:
                                    this.match(this.input, 43, FOLLOW_ID_in_ruleScopeSpec461);
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
                     alt30 = 2;
                     LA30_0 = this.input.LA(1);
                     if (LA30_0 >= 4 && LA30_0 <= 102) {
                        alt30 = 1;
                     } else if (LA30_0 == 3) {
                        alt30 = 2;
                     }

                     switch (alt30) {
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

   public final block_return block() throws RecognitionException {
      block_return retval = new block_return();
      retval.start = this.input.LT(1);
      StateCluster a = null;
      TreeRuleReturnScope set1 = null;
      List alts = new ArrayList();
      ++this.blockLevel;
      if (this.blockLevel == 1) {
         this.outerAltNum = 1;
      }

      try {
         int alt34 = true;
         int LA34_0 = this.input.LA(1);
         block_return var20;
         if (LA34_0 != 16) {
            if (this.state.backtracking > 0) {
               this.state.failed = true;
               var20 = retval;
               return var20;
            } else {
               NoViableAltException nvae = new NoViableAltException("", 34, 0, this.input);
               throw nvae;
            }
         } else {
            int LA34_1 = this.input.LA(2);
            byte alt34;
            if (this.grammar.isValidSet(this, (GrammarAST)retval.start) && !this.currentRuleName.equals("Tokens")) {
               alt34 = 1;
            } else {
               alt34 = 2;
            }

            switch (alt34) {
               case 1:
                  if (this.grammar.isValidSet(this, (GrammarAST)retval.start) && !this.currentRuleName.equals("Tokens")) {
                     this.pushFollow(FOLLOW_set_in_block492);
                     set1 = this.set();
                     --this.state._fsp;
                     if (this.state.failed) {
                        var20 = retval;
                        return var20;
                     }

                     if (this.state.backtracking == 0) {
                        retval.g = set1 != null ? ((set_return)set1).g : null;
                     }

                     return retval;
                  } else {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        var20 = retval;
                        return var20;
                     }

                     throw new FailedPredicateException(this.input, "block", "grammar.isValidSet(this,$start) &&\r\n\t\t !currentRuleName.equals(Grammar.ARTIFICIAL_TOKENS_RULENAME)");
                  }
               case 2:
                  this.match(this.input, 16, FOLLOW_BLOCK_in_block502);
                  if (this.state.failed) {
                     var20 = retval;
                     return var20;
                  } else {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        var20 = retval;
                        return var20;
                     } else {
                        int alt32 = 2;
                        int LA32_0 = this.input.LA(1);
                        if (LA32_0 == 58) {
                           alt32 = 1;
                        }

                        int alt31;
                        switch (alt32) {
                           case 1:
                              this.match(this.input, 58, FOLLOW_OPTIONS_in_block507);
                              block_return var22;
                              if (this.state.failed) {
                                 var22 = retval;
                                 return var22;
                              } else if (this.input.LA(1) == 2) {
                                 this.match(this.input, 2, (BitSet)null);
                                 if (this.state.failed) {
                                    var22 = retval;
                                    return var22;
                                 } else {
                                    label412:
                                    while(true) {
                                       alt31 = 2;
                                       int LA31_0 = this.input.LA(1);
                                       if (LA31_0 >= 4 && LA31_0 <= 102) {
                                          alt31 = 1;
                                       } else if (LA31_0 == 3) {
                                          alt31 = 2;
                                       }

                                       switch (alt31) {
                                          case 1:
                                             this.matchAny(this.input);
                                             if (this.state.failed) {
                                                block_return var11 = retval;
                                                return var11;
                                             }
                                             break;
                                          default:
                                             this.match(this.input, 3, (BitSet)null);
                                             if (this.state.failed) {
                                                var22 = retval;
                                                return var22;
                                             }
                                             break label412;
                                       }
                                    }
                                 }
                              }
                           default:
                              alt31 = 0;

                              while(true) {
                                 int alt33 = 2;
                                 int LA33_0 = this.input.LA(1);
                                 if (LA33_0 == 8) {
                                    alt33 = 1;
                                 }

                                 block_return var12;
                                 switch (alt33) {
                                    case 1:
                                       this.pushFollow(FOLLOW_alternative_in_block523);
                                       a = this.alternative();
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          var12 = retval;
                                          return var12;
                                       }

                                       this.pushFollow(FOLLOW_rewrite_in_block525);
                                       this.rewrite();
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          var12 = retval;
                                          return var12;
                                       }

                                       if (this.state.backtracking == 0) {
                                          alts.add(a);
                                       }

                                       if (this.blockLevel == 1) {
                                          ++this.outerAltNum;
                                       }

                                       ++alt31;
                                       break;
                                    default:
                                       if (alt31 < 1) {
                                          if (this.state.backtracking > 0) {
                                             this.state.failed = true;
                                             var12 = retval;
                                             return var12;
                                          }

                                          EarlyExitException eee = new EarlyExitException(33, this.input);
                                          throw eee;
                                       }

                                       this.match(this.input, 33, FOLLOW_EOB_in_block548);
                                       block_return var24;
                                       if (this.state.failed) {
                                          var24 = retval;
                                          return var24;
                                       }

                                       this.match(this.input, 3, (BitSet)null);
                                       if (this.state.failed) {
                                          var24 = retval;
                                          return var24;
                                       } else {
                                          if (this.state.backtracking == 0) {
                                             retval.g = this.factory.build_AlternativeBlock(alts);
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
      } catch (RecognitionException var16) {
         this.reportError(var16);
         this.recover(this.input, var16);
         return retval;
      } finally {
         --this.blockLevel;
      }
   }

   public final StateCluster alternative() throws RecognitionException {
      StateCluster g = null;
      TreeRuleReturnScope e = null;

      try {
         this.match(this.input, 8, FOLLOW_ALT_in_alternative577);
         if (this.state.failed) {
            return g;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return g;
            } else {
               int cnt35 = 0;

               while(true) {
                  int alt35 = 2;
                  int LA35_0 = this.input.LA(1);
                  if (LA35_0 == 4 || LA35_0 >= 13 && LA35_0 <= 16 || LA35_0 >= 18 && LA35_0 <= 19 || LA35_0 == 21 || LA35_0 == 29 || LA35_0 == 35 || LA35_0 == 39 || LA35_0 == 41 || LA35_0 == 55 || LA35_0 == 57 || LA35_0 >= 63 && LA35_0 <= 64 || LA35_0 == 70 || LA35_0 == 77 || LA35_0 == 80 || LA35_0 == 83 || LA35_0 >= 88 && LA35_0 <= 90 || LA35_0 == 94 || LA35_0 == 96 || LA35_0 == 98) {
                     alt35 = 1;
                  }

                  switch (alt35) {
                     case 1:
                        this.pushFollow(FOLLOW_element_in_alternative582);
                        e = this.element();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return g;
                        }

                        if (this.state.backtracking == 0) {
                           g = this.factory.build_AB(g, e != null ? ((element_return)e).g : null);
                        }

                        ++cnt35;
                        break;
                     default:
                        if (cnt35 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return g;
                           }

                           EarlyExitException eee = new EarlyExitException(35, this.input);
                           throw eee;
                        }

                        this.match(this.input, 32, FOLLOW_EOA_in_alternative589);
                        if (this.state.failed) {
                           return g;
                        }

                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return g;
                        }

                        if (this.state.backtracking == 0) {
                           if (g == null) {
                              g = this.factory.build_Epsilon();
                           } else {
                              this.factory.optimizeAlternative(g);
                           }

                           return g;
                        }

                        return g;
                  }
               }
            }
         }
      } catch (RecognitionException var10) {
         this.reportError(var10);
         this.recover(this.input, var10);
         return g;
      } finally {
         ;
      }
   }

   public final void exceptionGroup() throws RecognitionException {
      try {
         try {
            int alt38 = true;
            int LA38_0 = this.input.LA(1);
            byte alt38;
            if (LA38_0 == 17) {
               alt38 = 1;
            } else {
               if (LA38_0 != 38) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 38, 0, this.input);
                  throw nvae;
               }

               alt38 = 2;
            }

            switch (alt38) {
               case 1:
                  int cnt36 = 0;

                  while(true) {
                     int alt37 = 2;
                     int LA37_0 = this.input.LA(1);
                     if (LA37_0 == 17) {
                        alt37 = 1;
                     }

                     switch (alt37) {
                        case 1:
                           this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup608);
                           this.exceptionHandler();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt36;
                           break;
                        default:
                           if (cnt36 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(36, this.input);
                              throw eee;
                           }

                           alt37 = 2;
                           LA37_0 = this.input.LA(1);
                           if (LA37_0 == 38) {
                              alt37 = 1;
                           }

                           switch (alt37) {
                              case 1:
                                 this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup614);
                                 this.finallyClause();
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
                  this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup621);
                  this.finallyClause();
                  --this.state._fsp;
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

   public final void exceptionHandler() throws RecognitionException {
      try {
         try {
            this.match(this.input, 17, FOLLOW_CATCH_in_exceptionHandler636);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 12, FOLLOW_ARG_ACTION_in_exceptionHandler638);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler640);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void finallyClause() throws RecognitionException {
      try {
         try {
            this.match(this.input, 38, FOLLOW_FINALLY_in_finallyClause656);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause658);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final rewrite_return rewrite() throws RecognitionException {
      rewrite_return retval = new rewrite_return();
      retval.start = this.input.LT(1);

      try {
         try {
            int alt41 = true;
            int LA41_0 = this.input.LA(1);
            byte alt41;
            if (LA41_0 == 76) {
               alt41 = 1;
            } else {
               if (LA41_0 != 8 && LA41_0 != 33) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 41, 0, this.input);
                  throw nvae;
               }

               alt41 = 2;
            }

            switch (alt41) {
               case 1:
                  this.match(this.input, 76, FOLLOW_REWRITES_in_rewrite672);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     label228:
                     while(true) {
                        do {
                           int alt40 = 2;
                           int LA40_0 = this.input.LA(1);
                           if (LA40_0 == 75) {
                              alt40 = 1;
                           }

                           switch (alt40) {
                              case 1:
                                 if (this.state.backtracking == 0 && this.grammar.getOption("output") == null) {
                                    ErrorManager.grammarError(149, this.grammar, ((GrammarAST)retval.start).getToken(), this.currentRuleName);
                                 }

                                 this.match(this.input, 75, FOLLOW_REWRITE_in_rewrite690);
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
                        } while(this.input.LA(1) != 2);

                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           return retval;
                        }

                        while(true) {
                           int alt39 = 2;
                           int LA39_0 = this.input.LA(1);
                           if (LA39_0 >= 4 && LA39_0 <= 102) {
                              alt39 = 1;
                           } else if (LA39_0 == 3) {
                              alt39 = 2;
                           }

                           switch (alt39) {
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
                                 continue label228;
                           }
                        }
                     }
                  }
               case 2:
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

   public final element_return element() throws RecognitionException {
      element_return retval = new element_return();
      retval.start = this.input.LT(1);
      GrammarAST c1 = null;
      GrammarAST c2 = null;
      GrammarAST pred = null;
      GrammarAST spred = null;
      GrammarAST bpred = null;
      GrammarAST gpred = null;
      GrammarAST ACTION5 = null;
      GrammarAST FORCED_ACTION6 = null;
      TreeRuleReturnScope e = null;
      TreeRuleReturnScope a = null;
      TreeRuleReturnScope b = null;
      StateCluster atom_or_notatom2 = null;
      TreeRuleReturnScope ebnf3 = null;
      TreeRuleReturnScope tree_4 = null;

      try {
         try {
            int alt43 = true;
            byte alt43;
            switch (this.input.LA(1)) {
               case 4:
                  alt43 = 11;
                  break;
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
               case 12:
               case 17:
               case 20:
               case 22:
               case 23:
               case 24:
               case 25:
               case 26:
               case 27:
               case 28:
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 36:
               case 37:
               case 38:
               case 40:
               case 42:
               case 43:
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
               case 56:
               case 58:
               case 59:
               case 60:
               case 61:
               case 62:
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
               case 78:
               case 79:
               case 81:
               case 82:
               case 84:
               case 85:
               case 86:
               case 87:
               case 91:
               case 92:
               case 93:
               case 95:
               case 97:
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 43, 0, this.input);
                  throw nvae;
               case 13:
                  alt43 = 3;
                  break;
               case 14:
                  alt43 = 15;
                  break;
               case 15:
                  alt43 = 2;
                  break;
               case 16:
               case 21:
               case 57:
               case 64:
                  alt43 = 8;
                  break;
               case 18:
               case 29:
               case 55:
               case 80:
               case 88:
               case 94:
               case 98:
                  alt43 = 7;
                  break;
               case 19:
                  alt43 = 6;
                  break;
               case 35:
                  alt43 = 17;
                  break;
               case 39:
                  alt43 = 12;
                  break;
               case 41:
                  alt43 = 16;
                  break;
               case 63:
                  alt43 = 4;
                  break;
               case 70:
                  alt43 = 5;
                  break;
               case 77:
                  alt43 = 1;
                  break;
               case 83:
                  alt43 = 13;
                  break;
               case 89:
                  alt43 = 10;
                  break;
               case 90:
                  alt43 = 14;
                  break;
               case 96:
                  alt43 = 9;
            }

            switch (alt43) {
               case 1:
                  this.match(this.input, 77, FOLLOW_ROOT_in_element725);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element729);
                  e = this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = e != null ? ((element_return)e).g : null;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 2:
                  this.match(this.input, 15, FOLLOW_BANG_in_element740);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element744);
                  e = this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = e != null ? ((element_return)e).g : null;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 3:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_element753);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 43, FOLLOW_ID_in_element755);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element759);
                  e = this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = e != null ? ((element_return)e).g : null;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 4:
                  this.match(this.input, 63, FOLLOW_PLUS_ASSIGN_in_element768);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 43, FOLLOW_ID_in_element770);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element774);
                  e = this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = e != null ? ((element_return)e).g : null;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 5:
                  this.match(this.input, 70, FOLLOW_RANGE_in_element785);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_atom_in_element789);
                  a = this.atom((String)null);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_atom_in_element794);
                  b = this.atom((String)null);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = this.factory.build_Range(this.grammar.getTokenType(a != null ? this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(a.start), this.input.getTreeAdaptor().getTokenStopIndex(a.start)) : null), this.grammar.getTokenType(b != null ? this.input.getTokenStream().toString(this.input.getTreeAdaptor().getTokenStartIndex(b.start), this.input.getTreeAdaptor().getTokenStopIndex(b.start)) : null));
                  }
                  break;
               case 6:
                  this.match(this.input, 19, FOLLOW_CHAR_RANGE_in_element808);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  c1 = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_element812);
                  if (this.state.failed) {
                     return retval;
                  }

                  c2 = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_element816);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0 && this.grammar.type == 1) {
                     retval.g = this.factory.build_CharRange(c1 != null ? c1.getText() : null, c2 != null ? c2.getText() : null);
                  }
                  break;
               case 7:
                  this.pushFollow(FOLLOW_atom_or_notatom_in_element828);
                  atom_or_notatom2 = this.atom_or_notatom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = atom_or_notatom2;
                  }
                  break;
               case 8:
                  this.pushFollow(FOLLOW_ebnf_in_element837);
                  ebnf3 = this.ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = ebnf3 != null ? ((ebnf_return)ebnf3).g : null;
                  }
                  break;
               case 9:
                  this.pushFollow(FOLLOW_tree__in_element846);
                  tree_4 = this.tree_();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = tree_4 != null ? ((tree__return)tree_4).g : null;
                  }
                  break;
               case 10:
                  this.match(this.input, 89, FOLLOW_SYNPRED_in_element857);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_block_in_element859);
                  this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 11:
                  ACTION5 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_element868);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = this.factory.build_Action(ACTION5);
                  }
                  break;
               case 12:
                  FORCED_ACTION6 = (GrammarAST)this.match(this.input, 39, FOLLOW_FORCED_ACTION_in_element877);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = this.factory.build_Action(FORCED_ACTION6);
                  }
                  break;
               case 13:
                  pred = (GrammarAST)this.match(this.input, 83, FOLLOW_SEMPRED_in_element888);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = this.factory.build_SemanticPredicate(pred);
                  }
                  break;
               case 14:
                  spred = (GrammarAST)this.match(this.input, 90, FOLLOW_SYN_SEMPRED_in_element899);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = this.factory.build_SemanticPredicate(spred);
                  }
                  break;
               case 15:
                  bpred = (GrammarAST)this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_element911);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     label819:
                     while(true) {
                        int alt42 = 2;
                        int LA42_0 = this.input.LA(1);
                        if (LA42_0 >= 4 && LA42_0 <= 102) {
                           alt42 = 1;
                        } else if (LA42_0 == 3) {
                           alt42 = 2;
                        }

                        switch (alt42) {
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
                              break label819;
                        }
                     }
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = this.factory.build_SemanticPredicate(bpred);
                  }
                  break;
               case 16:
                  gpred = (GrammarAST)this.match(this.input, 41, FOLLOW_GATED_SEMPRED_in_element926);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = this.factory.build_SemanticPredicate(gpred);
                  }
                  break;
               case 17:
                  this.match(this.input, 35, FOLLOW_EPSILON_in_element935);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = this.factory.build_Epsilon();
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

   public final ebnf_return ebnf() throws RecognitionException {
      ebnf_return retval = new ebnf_return();
      retval.start = this.input.LT(1);
      TreeRuleReturnScope b = null;
      TreeRuleReturnScope set7 = null;
      GrammarAST blk = (GrammarAST)retval.start;
      if (blk.getType() != 16) {
         blk = (GrammarAST)blk.getChild(0);
      }

      GrammarAST eob = blk.getLastChild();

      try {
         try {
            int alt44 = true;
            int d;
            byte alt44;
            switch (this.input.LA(1)) {
               case 16:
                  d = this.input.LA(2);
                  if (this.grammar.isValidSet(this, (GrammarAST)retval.start)) {
                     alt44 = 1;
                  } else {
                     alt44 = 2;
                  }
                  break;
               case 21:
                  alt44 = 4;
                  break;
               case 57:
                  alt44 = 3;
                  break;
               case 64:
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

            int d;
            NFAState altBlockState;
            StateCluster bg;
            switch (alt44) {
               case 1:
                  if (!this.grammar.isValidSet(this, (GrammarAST)retval.start)) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     throw new FailedPredicateException(this.input, "ebnf", "grammar.isValidSet(this,$start)");
                  }

                  this.pushFollow(FOLLOW_set_in_ebnf961);
                  set7 = this.set();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = set7 != null ? ((set_return)set7).g : null;
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_block_in_ebnf971);
                  b = this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     if (this.grammar.getNumberOfAltsForDecisionNFA((b != null ? ((block_return)b).g : null).left) > 1) {
                        (b != null ? ((block_return)b).g : null).left.setDescription(this.grammar.grammarTreeToString(blk, false));
                        (b != null ? ((block_return)b).g : null).left.setDecisionASTNode(blk);
                        d = this.grammar.assignDecisionNumber((b != null ? ((block_return)b).g : null).left);
                        this.grammar.setDecisionNFA(d, (b != null ? ((block_return)b).g : null).left);
                        this.grammar.setDecisionBlockAST(d, blk);
                     }

                     retval.g = b != null ? ((block_return)b).g : null;
                  }
                  break;
               case 3:
                  this.match(this.input, 57, FOLLOW_OPTIONAL_in_ebnf982);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf986);
                  b = this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     bg = b != null ? ((block_return)b).g : null;
                     if (blk.getSetValue() != null) {
                        bg = this.factory.build_AlternativeBlockFromSet(bg);
                     }

                     retval.g = this.factory.build_Aoptional(bg);
                     retval.g.left.setDescription(this.grammar.grammarTreeToString((GrammarAST)retval.start, false));
                     d = this.grammar.assignDecisionNumber(retval.g.left);
                     this.grammar.setDecisionNFA(d, retval.g.left);
                     this.grammar.setDecisionBlockAST(d, blk);
                     retval.g.left.setDecisionASTNode((GrammarAST)retval.start);
                  }
                  break;
               case 4:
                  this.match(this.input, 21, FOLLOW_CLOSURE_in_ebnf999);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1003);
                  b = this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     bg = b != null ? ((block_return)b).g : null;
                     if (blk.getSetValue() != null) {
                        bg = this.factory.build_AlternativeBlockFromSet(bg);
                     }

                     retval.g = this.factory.build_Astar(bg);
                     bg.right.setDescription("()* loopback of " + this.grammar.grammarTreeToString((GrammarAST)retval.start, false));
                     d = this.grammar.assignDecisionNumber(bg.right);
                     this.grammar.setDecisionNFA(d, bg.right);
                     this.grammar.setDecisionBlockAST(d, blk);
                     bg.right.setDecisionASTNode(eob);
                     altBlockState = (NFAState)retval.g.left.transition(0).target;
                     altBlockState.setDecisionASTNode((GrammarAST)retval.start);
                     altBlockState.setDecisionNumber(d);
                     retval.g.left.setDecisionNumber(d);
                     retval.g.left.setDecisionASTNode((GrammarAST)retval.start);
                  }
                  break;
               case 5:
                  this.match(this.input, 64, FOLLOW_POSITIVE_CLOSURE_in_ebnf1016);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1020);
                  b = this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     bg = b != null ? ((block_return)b).g : null;
                     if (blk.getSetValue() != null) {
                        bg = this.factory.build_AlternativeBlockFromSet(bg);
                     }

                     retval.g = this.factory.build_Aplus(bg);
                     bg.right.setDescription("()+ loopback of " + this.grammar.grammarTreeToString((GrammarAST)retval.start, false));
                     d = this.grammar.assignDecisionNumber(bg.right);
                     this.grammar.setDecisionNFA(d, bg.right);
                     this.grammar.setDecisionBlockAST(d, blk);
                     bg.right.setDecisionASTNode(eob);
                     altBlockState = (NFAState)retval.g.left.transition(0).target;
                     altBlockState.setDecisionASTNode((GrammarAST)retval.start);
                     altBlockState.setDecisionNumber(d);
                  }
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.recover(this.input, var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final tree__return tree_() throws RecognitionException {
      tree__return retval = new tree__return();
      retval.start = this.input.LT(1);
      TreeRuleReturnScope e = null;
      StateCluster down = null;
      StateCluster up = null;

      try {
         this.match(this.input, 96, FOLLOW_TREE_BEGIN_in_tree_1048);
         if (this.state.failed) {
            return retval;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return retval;
            } else {
               this.pushFollow(FOLLOW_element_in_tree_1055);
               e = this.element();
               --this.state._fsp;
               if (this.state.failed) {
                  return retval;
               } else {
                  if (this.state.backtracking == 0) {
                     retval.g = e != null ? ((element_return)e).g : null;
                  }

                  if (this.state.backtracking == 0) {
                     down = this.factory.build_Atom(2, e != null ? (GrammarAST)e.start : null);
                     retval.g = this.factory.build_AB(retval.g, down);
                  }

                  while(true) {
                     int alt45 = 2;
                     int LA45_0 = this.input.LA(1);
                     if (LA45_0 == 4 || LA45_0 >= 13 && LA45_0 <= 16 || LA45_0 >= 18 && LA45_0 <= 19 || LA45_0 == 21 || LA45_0 == 29 || LA45_0 == 35 || LA45_0 == 39 || LA45_0 == 41 || LA45_0 == 55 || LA45_0 == 57 || LA45_0 >= 63 && LA45_0 <= 64 || LA45_0 == 70 || LA45_0 == 77 || LA45_0 == 80 || LA45_0 == 83 || LA45_0 >= 88 && LA45_0 <= 90 || LA45_0 == 94 || LA45_0 == 96 || LA45_0 == 98) {
                        alt45 = 1;
                     }

                     switch (alt45) {
                        case 1:
                           this.pushFollow(FOLLOW_element_in_tree_1071);
                           e = this.element();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              retval.g = this.factory.build_AB(retval.g, e != null ? ((element_return)e).g : null);
                           }
                           break;
                        default:
                           if (this.state.backtracking == 0) {
                              up = this.factory.build_Atom(3, e != null ? (GrammarAST)e.start : null);
                              retval.g = this.factory.build_AB(retval.g, up);
                              ((GrammarAST)retval.start).NFATreeDownState = down.left;
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
         }
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(this.input, var11);
         return retval;
      } finally {
         ;
      }
   }

   public final StateCluster atom_or_notatom() throws RecognitionException {
      StateCluster g = null;
      GrammarAST n = null;
      GrammarAST c = null;
      GrammarAST t = null;
      TreeRuleReturnScope atom8 = null;
      TreeRuleReturnScope set9 = null;

      try {
         try {
            int alt49 = true;
            int LA49_0 = this.input.LA(1);
            byte alt49;
            if (LA49_0 != 18 && LA49_0 != 29 && LA49_0 != 80 && LA49_0 != 88 && LA49_0 != 94 && LA49_0 != 98) {
               if (LA49_0 != 55) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return g;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 49, 0, this.input);
                  throw nvae;
               }

               alt49 = 2;
            } else {
               alt49 = 1;
            }

            switch (alt49) {
               case 1:
                  this.pushFollow(FOLLOW_atom_in_atom_or_notatom1100);
                  atom8 = this.atom((String)null);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return g;
                  }

                  if (this.state.backtracking == 0) {
                     g = atom8 != null ? ((atom_return)atom8).g : null;
                  }
                  break;
               case 2:
                  n = (GrammarAST)this.match(this.input, 55, FOLLOW_NOT_in_atom_or_notatom1112);
                  if (this.state.failed) {
                     return g;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return g;
                  }

                  int alt48 = true;
                  byte alt48;
                  switch (this.input.LA(1)) {
                     case 16:
                        alt48 = 3;
                        break;
                     case 18:
                        alt48 = 1;
                        break;
                     case 94:
                        alt48 = 2;
                        break;
                     default:
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return g;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 48, 0, this.input);
                        throw nvae;
                  }

                  boolean ttype;
                  IntSet notAtom;
                  byte alt47;
                  int LA47_0;
                  int ttype;
                  label439:
                  switch (alt48) {
                     case 1:
                        c = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_atom_or_notatom1121);
                        if (this.state.failed) {
                           return g;
                        }

                        alt47 = 2;
                        LA47_0 = this.input.LA(1);
                        if (LA47_0 == 15 || LA47_0 == 77) {
                           alt47 = 1;
                        }

                        switch (alt47) {
                           case 1:
                              this.pushFollow(FOLLOW_ast_suffix_in_atom_or_notatom1126);
                              this.ast_suffix();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return g;
                              }
                           default:
                              if (this.state.backtracking == 0) {
                                 ttype = false;
                                 if (this.grammar.type == 1) {
                                    ttype = Grammar.getCharValueFromGrammarCharLiteral(c != null ? c.getText() : null);
                                 } else {
                                    ttype = this.grammar.getTokenType(c != null ? c.getText() : null);
                                 }

                                 notAtom = this.grammar.complement(ttype);
                                 if (notAtom.isNil()) {
                                    ErrorManager.grammarError(139, this.grammar, c.getToken(), c != null ? c.getText() : null);
                                 }

                                 g = this.factory.build_Set(notAtom, n);
                              }
                              break label439;
                        }
                     case 2:
                        t = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_atom_or_notatom1143);
                        if (this.state.failed) {
                           return g;
                        }

                        alt47 = 2;
                        LA47_0 = this.input.LA(1);
                        if (LA47_0 == 15 || LA47_0 == 77) {
                           alt47 = 1;
                        }

                        switch (alt47) {
                           case 1:
                              this.pushFollow(FOLLOW_ast_suffix_in_atom_or_notatom1148);
                              this.ast_suffix();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return g;
                              }
                        }

                        if (this.state.backtracking != 0) {
                           break;
                        }

                        ttype = false;
                        notAtom = null;
                        if (this.grammar.type == 1) {
                           notAtom = this.grammar.getSetFromRule(this, t != null ? t.getText() : null);
                           if (notAtom == null) {
                              ErrorManager.grammarError(154, this.grammar, t.getToken(), t != null ? t.getText() : null);
                           } else {
                              notAtom = this.grammar.complement(notAtom);
                           }
                        } else {
                           ttype = this.grammar.getTokenType(t != null ? t.getText() : null);
                           notAtom = this.grammar.complement(ttype);
                        }

                        if (notAtom == null || notAtom.isNil()) {
                           ErrorManager.grammarError(139, this.grammar, t.getToken(), t != null ? t.getText() : null);
                        }

                        g = this.factory.build_Set(notAtom, n);
                        break;
                     case 3:
                        this.pushFollow(FOLLOW_set_in_atom_or_notatom1163);
                        set9 = this.set();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return g;
                        }

                        if (this.state.backtracking == 0) {
                           g = set9 != null ? ((set_return)set9).g : null;
                        }

                        if (this.state.backtracking == 0) {
                           GrammarAST stNode = (GrammarAST)n.getChild(0);
                           IntSet s = stNode.getSetValue();
                           stNode.setSetValue(s);
                           s = this.grammar.complement(s);
                           if (s.isNil()) {
                              ErrorManager.grammarError(139, this.grammar, n.getToken());
                           }

                           g = this.factory.build_Set(s, n);
                        }
                  }

                  if (this.state.backtracking == 0) {
                     n.followingNFAState = g.right;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return g;
                  }
            }
         } catch (RecognitionException var17) {
            this.reportError(var17);
            this.recover(this.input, var17);
         }

         return g;
      } finally {
         ;
      }
   }

   public final atom_return atom(String scopeName) throws RecognitionException {
      atom_return retval = new atom_return();
      retval.start = this.input.LT(1);
      GrammarAST r = null;
      GrammarAST rarg = null;
      GrammarAST t = null;
      GrammarAST targ = null;
      GrammarAST c = null;
      GrammarAST s = null;
      GrammarAST w = null;
      GrammarAST scope_ = null;
      TreeRuleReturnScope a = null;

      try {
         try {
            int alt57 = true;
            byte alt57;
            switch (this.input.LA(1)) {
               case 18:
                  alt57 = 3;
                  break;
               case 29:
                  alt57 = 6;
                  break;
               case 80:
                  alt57 = 1;
                  break;
               case 88:
                  alt57 = 4;
                  break;
               case 94:
                  alt57 = 2;
                  break;
               case 98:
                  alt57 = 5;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 57, 0, this.input);
                  throw nvae;
            }

            byte alt56;
            int LA56_0;
            byte alt53;
            int LA53_0;
            Rule rr;
            NFAState start;
            switch (alt57) {
               case 1:
                  r = (GrammarAST)this.match(this.input, 80, FOLLOW_RULE_REF_in_atom1205);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     alt56 = 2;
                     LA56_0 = this.input.LA(1);
                     if (LA56_0 == 12) {
                        alt56 = 1;
                     }

                     switch (alt56) {
                        case 1:
                           rarg = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1210);
                           if (this.state.failed) {
                              return retval;
                           }
                     }

                     alt53 = 2;
                     LA53_0 = this.input.LA(1);
                     if (LA53_0 == 15 || LA53_0 == 77) {
                        alt53 = 1;
                     }

                     switch (alt53) {
                        case 1:
                           this.pushFollow(FOLLOW_ast_suffix_in_atom1217);
                           this.ast_suffix();
                           --this.state._fsp;
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
                     start = this.grammar.getRuleStartState(scopeName, r != null ? r.getText() : null);
                     if (start != null) {
                        rr = this.grammar.getRule(scopeName, r != null ? r.getText() : null);
                        retval.g = this.factory.build_RuleRef(rr, start);
                        r.followingNFAState = retval.g.right;
                        r.NFAStartState = retval.g.left;
                        if (retval.g.left.transition(0) instanceof RuleClosureTransition && this.grammar.type != 1) {
                           this.addFollowTransition(r != null ? r.getText() : null, retval.g.right);
                        }
                     }
                  }
                  break;
               case 2:
                  t = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_atom1235);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     alt56 = 2;
                     LA56_0 = this.input.LA(1);
                     if (LA56_0 == 12) {
                        alt56 = 1;
                     }

                     switch (alt56) {
                        case 1:
                           targ = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1241);
                           if (this.state.failed) {
                              return retval;
                           }
                     }

                     alt53 = 2;
                     LA53_0 = this.input.LA(1);
                     if (LA53_0 == 15 || LA53_0 == 77) {
                        alt53 = 1;
                     }

                     switch (alt53) {
                        case 1:
                           this.pushFollow(FOLLOW_ast_suffix_in_atom1248);
                           this.ast_suffix();
                           --this.state._fsp;
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
                     if (this.grammar.type == 1) {
                        start = this.grammar.getRuleStartState(scopeName, t != null ? t.getText() : null);
                        if (start != null) {
                           rr = this.grammar.getRule(scopeName, t.getText());
                           retval.g = this.factory.build_RuleRef(rr, start);
                           t.NFAStartState = retval.g.left;
                        }
                     } else {
                        retval.g = this.factory.build_Atom(t);
                        t.followingNFAState = retval.g.right;
                     }
                  }
                  break;
               case 3:
                  c = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_atom1266);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     alt56 = 2;
                     LA56_0 = this.input.LA(1);
                     if (LA56_0 == 15 || LA56_0 == 77) {
                        alt56 = 1;
                     }

                     switch (alt56) {
                        case 1:
                           this.pushFollow(FOLLOW_ast_suffix_in_atom1272);
                           this.ast_suffix();
                           --this.state._fsp;
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
                     if (this.grammar.type == 1) {
                        retval.g = this.factory.build_CharLiteralAtom(c);
                     } else {
                        retval.g = this.factory.build_Atom(c);
                        c.followingNFAState = retval.g.right;
                     }
                  }
                  break;
               case 4:
                  s = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_atom1290);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     alt56 = 2;
                     LA56_0 = this.input.LA(1);
                     if (LA56_0 == 15 || LA56_0 == 77) {
                        alt56 = 1;
                     }

                     switch (alt56) {
                        case 1:
                           this.pushFollow(FOLLOW_ast_suffix_in_atom1296);
                           this.ast_suffix();
                           --this.state._fsp;
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
                     if (this.grammar.type == 1) {
                        retval.g = this.factory.build_StringLiteralAtom(s);
                     } else {
                        retval.g = this.factory.build_Atom(s);
                        s.followingNFAState = retval.g.right;
                     }
                  }
                  break;
               case 5:
                  w = (GrammarAST)this.match(this.input, 98, FOLLOW_WILDCARD_in_atom1314);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     alt56 = 2;
                     LA56_0 = this.input.LA(1);
                     if (LA56_0 == 15 || LA56_0 == 77) {
                        alt56 = 1;
                     }

                     switch (alt56) {
                        case 1:
                           this.pushFollow(FOLLOW_ast_suffix_in_atom1319);
                           this.ast_suffix();
                           --this.state._fsp;
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
                     if (this.nfa.grammar.type != 3 || w.getChildIndex() <= 0 && w.getParent().getChild(1).getType() != 32) {
                        retval.g = this.factory.build_Wildcard(w);
                     } else {
                        retval.g = this.factory.build_WildcardTree(w);
                     }
                  }
                  break;
               case 6:
                  this.match(this.input, 29, FOLLOW_DOT_in_atom1336);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  scope_ = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_atom1340);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_atom_in_atom1344);
                  a = this.atom(scope_ != null ? scope_.getText() : null);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     retval.g = a != null ? ((atom_return)a).g : null;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
            }
         } catch (RecognitionException var21) {
            this.reportError(var21);
            this.recover(this.input, var21);
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

   public final set_return set() throws RecognitionException {
      set_return retval = new set_return();
      retval.start = this.input.LT(1);
      GrammarAST b = null;
      IntSet elements = new IntervalSet();
      if (this.state.backtracking == 0) {
         ((GrammarAST)retval.start).setSetValue(elements);
      }

      try {
         b = (GrammarAST)this.match(this.input, 16, FOLLOW_BLOCK_in_set1390);
         if (this.state.failed) {
            return retval;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return retval;
            } else {
               int cnt60 = 0;

               while(true) {
                  int alt60 = 2;
                  int LA60_0 = this.input.LA(1);
                  if (LA60_0 == 8) {
                     alt60 = 1;
                  }

                  switch (alt60) {
                     case 1:
                        this.match(this.input, 8, FOLLOW_ALT_in_set1399);
                        if (this.state.failed) {
                           return retval;
                        }

                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           return retval;
                        }

                        int alt59 = 2;
                        int LA59_0 = this.input.LA(1);
                        if (LA59_0 == 14) {
                           alt59 = 1;
                        }

                        switch (alt59) {
                           case 1:
                              this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_set1404);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.input.LA(1) == 2) {
                                 this.match(this.input, 2, (BitSet)null);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 label284:
                                 while(true) {
                                    int alt58 = 2;
                                    int LA58_0 = this.input.LA(1);
                                    if (LA58_0 >= 4 && LA58_0 <= 102) {
                                       alt58 = 1;
                                    } else if (LA58_0 == 3) {
                                       alt58 = 2;
                                    }

                                    switch (alt58) {
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
                                          break label284;
                                    }
                                 }
                              }
                           default:
                              this.pushFollow(FOLLOW_setElement_in_set1413);
                              this.setElement(elements);
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              this.match(this.input, 32, FOLLOW_EOA_in_set1416);
                              if (this.state.failed) {
                                 return retval;
                              }

                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return retval;
                              }

                              ++cnt60;
                              continue;
                        }
                     default:
                        if (cnt60 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           EarlyExitException eee = new EarlyExitException(60, this.input);
                           throw eee;
                        }

                        this.match(this.input, 33, FOLLOW_EOB_in_set1426);
                        if (this.state.failed) {
                           return retval;
                        }

                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           retval.g = this.factory.build_Set(elements, b);
                           b.followingNFAState = retval.g.right;
                           b.setSetValue(elements);
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var15) {
         this.reportError(var15);
         this.recover(this.input, var15);
         return retval;
      } finally {
         ;
      }
   }

   public final IntSet setRule() throws RecognitionException {
      IntSet elements = new IntervalSet();
      GrammarAST id = null;
      IntSet s = null;

      try {
         this.match(this.input, 79, FOLLOW_RULE_in_setRule1460);
         if (this.state.failed) {
            return elements;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return elements;
            } else {
               id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_setRule1464);
               if (this.state.failed) {
                  return elements;
               } else {
                  int alt61 = 2;
                  int LA61_0 = this.input.LA(1);
                  if (LA61_0 == 40 || LA61_0 >= 66 && LA61_0 <= 68) {
                     alt61 = 1;
                  }

                  switch (alt61) {
                     case 1:
                        this.pushFollow(FOLLOW_modifier_in_setRule1467);
                        this.modifier();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return elements;
                        }
                     default:
                        this.match(this.input, 10, FOLLOW_ARG_in_setRule1471);
                        if (this.state.failed) {
                           return elements;
                        } else {
                           this.match(this.input, 73, FOLLOW_RET_in_setRule1473);
                           if (this.state.failed) {
                              return elements;
                           } else {
                              int alt63 = 2;
                              int LA63_0 = this.input.LA(1);
                              if (LA63_0 == 58) {
                                 alt63 = 1;
                              }

                              byte alt62;
                              int LA62_0;
                              switch (alt63) {
                                 case 1:
                                    this.match(this.input, 58, FOLLOW_OPTIONS_in_setRule1478);
                                    if (this.state.failed) {
                                       return elements;
                                    } else if (this.input.LA(1) == 2) {
                                       this.match(this.input, 2, (BitSet)null);
                                       if (this.state.failed) {
                                          return elements;
                                       } else {
                                          label628:
                                          while(true) {
                                             alt62 = 2;
                                             LA62_0 = this.input.LA(1);
                                             if (LA62_0 >= 4 && LA62_0 <= 102) {
                                                alt62 = 1;
                                             } else if (LA62_0 == 3) {
                                                alt62 = 2;
                                             }

                                             switch (alt62) {
                                                case 1:
                                                   this.matchAny(this.input);
                                                   if (this.state.failed) {
                                                      return elements;
                                                   }
                                                   break;
                                                default:
                                                   this.match(this.input, 3, (BitSet)null);
                                                   if (this.state.failed) {
                                                      return elements;
                                                   }
                                                   break label628;
                                             }
                                          }
                                       }
                                    }
                                 default:
                                    alt62 = 2;
                                    LA62_0 = this.input.LA(1);
                                    if (LA62_0 == 81) {
                                       alt62 = 1;
                                    }

                                    switch (alt62) {
                                       case 1:
                                          this.pushFollow(FOLLOW_ruleScopeSpec_in_setRule1489);
                                          this.ruleScopeSpec();
                                          --this.state._fsp;
                                          if (this.state.failed) {
                                             return elements;
                                          }
                                    }

                                    label616:
                                    while(true) {
                                       int alt68 = 2;
                                       int LA68_0 = this.input.LA(1);
                                       if (LA68_0 == 9) {
                                          alt68 = 1;
                                       }

                                       int alt67;
                                       int LA67_0;
                                       switch (alt68) {
                                          case 1:
                                             this.match(this.input, 9, FOLLOW_AMPERSAND_in_setRule1500);
                                             if (this.state.failed) {
                                                return elements;
                                             }

                                             if (this.input.LA(1) != 2) {
                                                break;
                                             }

                                             this.match(this.input, 2, (BitSet)null);
                                             if (this.state.failed) {
                                                return elements;
                                             }

                                             while(true) {
                                                alt67 = 2;
                                                LA67_0 = this.input.LA(1);
                                                if (LA67_0 >= 4 && LA67_0 <= 102) {
                                                   alt67 = 1;
                                                } else if (LA67_0 == 3) {
                                                   alt67 = 2;
                                                }

                                                switch (alt67) {
                                                   case 1:
                                                      this.matchAny(this.input);
                                                      if (this.state.failed) {
                                                         return elements;
                                                      }
                                                      break;
                                                   default:
                                                      this.match(this.input, 3, (BitSet)null);
                                                      if (this.state.failed) {
                                                         return elements;
                                                      }
                                                      continue label616;
                                                }
                                             }
                                          default:
                                             this.match(this.input, 16, FOLLOW_BLOCK_in_setRule1514);
                                             if (this.state.failed) {
                                                return elements;
                                             } else {
                                                this.match(this.input, 2, (BitSet)null);
                                                if (this.state.failed) {
                                                   return elements;
                                                } else {
                                                   alt68 = 2;
                                                   LA68_0 = this.input.LA(1);
                                                   if (LA68_0 == 58) {
                                                      alt68 = 1;
                                                   }

                                                   switch (alt68) {
                                                      case 1:
                                                         this.match(this.input, 58, FOLLOW_OPTIONS_in_setRule1519);
                                                         if (this.state.failed) {
                                                            return elements;
                                                         } else if (this.input.LA(1) == 2) {
                                                            this.match(this.input, 2, (BitSet)null);
                                                            if (this.state.failed) {
                                                               return elements;
                                                            } else {
                                                               label590:
                                                               while(true) {
                                                                  alt67 = 2;
                                                                  LA67_0 = this.input.LA(1);
                                                                  if (LA67_0 >= 4 && LA67_0 <= 102) {
                                                                     alt67 = 1;
                                                                  } else if (LA67_0 == 3) {
                                                                     alt67 = 2;
                                                                  }

                                                                  switch (alt67) {
                                                                     case 1:
                                                                        this.matchAny(this.input);
                                                                        if (this.state.failed) {
                                                                           return elements;
                                                                        }
                                                                        break;
                                                                     default:
                                                                        this.match(this.input, 3, (BitSet)null);
                                                                        if (this.state.failed) {
                                                                           return elements;
                                                                        }
                                                                        break label590;
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      default:
                                                         alt67 = 0;

                                                         while(true) {
                                                            int alt71 = 2;
                                                            int LA71_0 = this.input.LA(1);
                                                            if (LA71_0 == 8) {
                                                               alt71 = 1;
                                                            }

                                                            switch (alt71) {
                                                               case 1:
                                                                  this.match(this.input, 8, FOLLOW_ALT_in_setRule1537);
                                                                  if (this.state.failed) {
                                                                     return elements;
                                                                  }

                                                                  this.match(this.input, 2, (BitSet)null);
                                                                  if (this.state.failed) {
                                                                     return elements;
                                                                  }

                                                                  int alt69 = 2;
                                                                  int LA69_0 = this.input.LA(1);
                                                                  if (LA69_0 == 14) {
                                                                     alt69 = 1;
                                                                  }

                                                                  switch (alt69) {
                                                                     case 1:
                                                                        this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_setRule1540);
                                                                        if (this.state.failed) {
                                                                           return elements;
                                                                        }
                                                                     default:
                                                                        this.pushFollow(FOLLOW_setElement_in_setRule1544);
                                                                        this.setElement(elements);
                                                                        --this.state._fsp;
                                                                        if (this.state.failed) {
                                                                           return elements;
                                                                        }

                                                                        this.match(this.input, 32, FOLLOW_EOA_in_setRule1547);
                                                                        if (this.state.failed) {
                                                                           return elements;
                                                                        }

                                                                        this.match(this.input, 3, (BitSet)null);
                                                                        if (this.state.failed) {
                                                                           return elements;
                                                                        }

                                                                        ++alt67;
                                                                        continue;
                                                                  }
                                                               default:
                                                                  if (alt67 < 1) {
                                                                     if (this.state.backtracking > 0) {
                                                                        this.state.failed = true;
                                                                        return elements;
                                                                     }

                                                                     EarlyExitException eee = new EarlyExitException(70, this.input);
                                                                     throw eee;
                                                                  }

                                                                  this.match(this.input, 33, FOLLOW_EOB_in_setRule1559);
                                                                  if (this.state.failed) {
                                                                     return elements;
                                                                  }

                                                                  this.match(this.input, 3, (BitSet)null);
                                                                  if (this.state.failed) {
                                                                     return elements;
                                                                  }

                                                                  alt71 = 2;
                                                                  LA71_0 = this.input.LA(1);
                                                                  if (LA71_0 == 17 || LA71_0 == 38) {
                                                                     alt71 = 1;
                                                                  }

                                                                  switch (alt71) {
                                                                     case 1:
                                                                        this.pushFollow(FOLLOW_exceptionGroup_in_setRule1571);
                                                                        this.exceptionGroup();
                                                                        --this.state._fsp;
                                                                        if (this.state.failed) {
                                                                           return elements;
                                                                        }
                                                                     default:
                                                                        this.match(this.input, 34, FOLLOW_EOR_in_setRule1578);
                                                                        if (this.state.failed) {
                                                                           return elements;
                                                                        }

                                                                        this.match(this.input, 3, (BitSet)null);
                                                                        if (this.state.failed) {
                                                                           return elements;
                                                                        }

                                                                        return elements;
                                                                  }
                                                            }
                                                         }
                                                   }
                                                }
                                             }
                                       }
                                    }
                              }
                           }
                        }
                  }
               }
            }
         }
      } catch (RecognitionException var21) {
         throw var21;
      } finally {
         ;
      }
   }

   public final void setElement(IntSet elements) throws RecognitionException {
      GrammarAST c = null;
      GrammarAST t = null;
      GrammarAST s = null;
      GrammarAST c1 = null;
      GrammarAST c2 = null;
      TreeRuleReturnScope gset = null;
      IntSet ns = null;

      try {
         try {
            int alt72 = true;
            byte alt72;
            switch (this.input.LA(1)) {
               case 16:
                  alt72 = 5;
                  break;
               case 18:
                  alt72 = 1;
                  break;
               case 19:
                  alt72 = 4;
                  break;
               case 55:
                  alt72 = 6;
                  break;
               case 88:
                  alt72 = 3;
                  break;
               case 94:
                  alt72 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 72, 0, this.input);
                  throw nvae;
            }

            int ttype;
            IntSet not;
            switch (alt72) {
               case 1:
                  c = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_setElement1607);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     if (this.grammar.type == 1) {
                        ttype = Grammar.getCharValueFromGrammarCharLiteral(c != null ? c.getText() : null);
                     } else {
                        ttype = this.grammar.getTokenType(c != null ? c.getText() : null);
                     }

                     if (elements.member(ttype)) {
                        ErrorManager.grammarError(204, this.grammar, c.getToken(), c != null ? c.getText() : null);
                     }

                     elements.add(ttype);
                  }
                  break;
               case 2:
                  t = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_setElement1618);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     if (this.grammar.type == 1) {
                        not = this.grammar.getSetFromRule(this, t != null ? t.getText() : null);
                        if (not == null) {
                           ErrorManager.grammarError(154, this.grammar, t.getToken(), t != null ? t.getText() : null);
                        } else {
                           elements.addAll(not);
                        }
                     } else {
                        ttype = this.grammar.getTokenType(t != null ? t.getText() : null);
                        if (elements.member(ttype)) {
                           ErrorManager.grammarError(204, this.grammar, t.getToken(), t != null ? t.getText() : null);
                        }

                        elements.add(ttype);
                     }
                  }
                  break;
               case 3:
                  s = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_setElement1630);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     ttype = this.grammar.getTokenType(s != null ? s.getText() : null);
                     if (elements.member(ttype)) {
                        ErrorManager.grammarError(204, this.grammar, s.getToken(), s != null ? s.getText() : null);
                     }

                     elements.add(ttype);
                  }
                  break;
               case 4:
                  this.match(this.input, 19, FOLLOW_CHAR_RANGE_in_setElement1640);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  c1 = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_setElement1644);
                  if (this.state.failed) {
                     return;
                  }

                  c2 = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_setElement1648);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0 && this.grammar.type == 1) {
                     int a = Grammar.getCharValueFromGrammarCharLiteral(c1 != null ? c1.getText() : null);
                     int b = Grammar.getCharValueFromGrammarCharLiteral(c2 != null ? c2.getText() : null);
                     elements.addAll(IntervalSet.of(a, b));
                  }
                  break;
               case 5:
                  this.pushFollow(FOLLOW_set_in_setElement1661);
                  gset = this.set();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     Transition setTrans = (gset != null ? ((set_return)gset).g : null).left.transition(0);
                     elements.addAll(setTrans.label.getSet());
                  }
                  break;
               case 6:
                  this.match(this.input, 55, FOLLOW_NOT_in_setElement1673);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     ns = new IntervalSet();
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_setElement_in_setElement1680);
                  this.setElement(ns);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     not = this.grammar.complement(ns);
                     elements.addAll(not);
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.recover(this.input, var16);
         }

      } finally {
         ;
      }
   }

   public final int testBlockAsSet() throws RecognitionException {
      int alts = 0;
      int testSetElement10 = false;
      ++this.inTest;

      try {
         this.match(this.input, 16, FOLLOW_BLOCK_in_testBlockAsSet1725);
         int cnt74;
         if (this.state.failed) {
            cnt74 = alts;
            return cnt74;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               cnt74 = alts;
               return cnt74;
            } else {
               cnt74 = 0;

               while(true) {
                  int alt74 = 2;
                  int LA74_0 = this.input.LA(1);
                  if (LA74_0 == 8) {
                     alt74 = 1;
                  }

                  int alt73;
                  switch (alt74) {
                     case 1:
                        this.match(this.input, 8, FOLLOW_ALT_in_testBlockAsSet1733);
                        if (this.state.failed) {
                           alt73 = alts;
                           return alt73;
                        }

                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           alt73 = alts;
                           return alt73;
                        }

                        alt73 = 2;
                        int LA73_0 = this.input.LA(1);
                        if (LA73_0 == 14) {
                           alt73 = 1;
                        }

                        int var8;
                        switch (alt73) {
                           case 1:
                              this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_testBlockAsSet1736);
                              if (this.state.failed) {
                                 var8 = alts;
                                 return var8;
                              }
                        }

                        this.pushFollow(FOLLOW_testSetElement_in_testBlockAsSet1740);
                        int testSetElement10 = this.testSetElement();
                        --this.state._fsp;
                        if (this.state.failed) {
                           var8 = alts;
                           return var8;
                        }

                        alts += testSetElement10;
                        this.match(this.input, 32, FOLLOW_EOA_in_testBlockAsSet1744);
                        if (this.state.failed) {
                           var8 = alts;
                           return var8;
                        }

                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           var8 = alts;
                           return var8;
                        }

                        ++cnt74;
                        break;
                     default:
                        if (cnt74 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              alt73 = alts;
                              return alt73;
                           }

                           EarlyExitException eee = new EarlyExitException(74, this.input);
                           throw eee;
                        }

                        this.match(this.input, 33, FOLLOW_EOB_in_testBlockAsSet1756);
                        if (this.state.failed) {
                           alt74 = alts;
                           return alt74;
                        }

                        this.match(this.input, 3, (BitSet)null);
                        if (!this.state.failed) {
                           return alts;
                        }

                        alt74 = alts;
                        return alt74;
                  }
               }
            }
         }
      } catch (RecognitionException var12) {
         throw var12;
      } finally {
         --this.inTest;
      }
   }

   public final int testSetRule() throws RecognitionException {
      int alts = 0;
      GrammarAST id = null;
      int testSetElement11 = false;
      ++this.inTest;

      try {
         this.match(this.input, 79, FOLLOW_RULE_in_testSetRule1791);
         int alt75;
         if (this.state.failed) {
            alt75 = alts;
            return alt75;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               alt75 = alts;
               return alt75;
            } else {
               id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_testSetRule1795);
               if (this.state.failed) {
                  alt75 = alts;
                  return alt75;
               } else {
                  alt75 = 2;
                  int LA75_0 = this.input.LA(1);
                  if (LA75_0 == 40 || LA75_0 >= 66 && LA75_0 <= 68) {
                     alt75 = 1;
                  }

                  int var6;
                  switch (alt75) {
                     case 1:
                        this.pushFollow(FOLLOW_modifier_in_testSetRule1798);
                        this.modifier();
                        --this.state._fsp;
                        if (this.state.failed) {
                           var6 = alts;
                           return var6;
                        }
                  }

                  this.match(this.input, 10, FOLLOW_ARG_in_testSetRule1802);
                  if (this.state.failed) {
                     var6 = alts;
                     return var6;
                  } else {
                     this.match(this.input, 73, FOLLOW_RET_in_testSetRule1804);
                     if (this.state.failed) {
                        var6 = alts;
                        return var6;
                     } else {
                        int alt77 = 2;
                        int LA77_0 = this.input.LA(1);
                        if (LA77_0 == 58) {
                           alt77 = 1;
                        }

                        int LA76_0;
                        int cnt82;
                        switch (alt77) {
                           case 1:
                              this.match(this.input, 58, FOLLOW_OPTIONS_in_testSetRule1809);
                              int alt76;
                              if (this.state.failed) {
                                 alt76 = alts;
                                 return alt76;
                              }

                              if (this.input.LA(1) == 2) {
                                 this.match(this.input, 2, (BitSet)null);
                                 if (this.state.failed) {
                                    alt76 = alts;
                                    return alt76;
                                 }

                                 label671:
                                 while(true) {
                                    alt76 = 2;
                                    LA76_0 = this.input.LA(1);
                                    if (LA76_0 >= 4 && LA76_0 <= 102) {
                                       alt76 = 1;
                                    } else if (LA76_0 == 3) {
                                       alt76 = 2;
                                    }

                                    switch (alt76) {
                                       case 1:
                                          this.matchAny(this.input);
                                          if (this.state.failed) {
                                             cnt82 = alts;
                                             return cnt82;
                                          }
                                          break;
                                       default:
                                          this.match(this.input, 3, (BitSet)null);
                                          if (this.state.failed) {
                                             alt76 = alts;
                                             return alt76;
                                          }
                                          break label671;
                                    }
                                 }
                              }
                           default:
                              int alt78 = 2;
                              LA76_0 = this.input.LA(1);
                              if (LA76_0 == 81) {
                                 alt78 = 1;
                              }

                              switch (alt78) {
                                 case 1:
                                    this.pushFollow(FOLLOW_ruleScopeSpec_in_testSetRule1820);
                                    this.ruleScopeSpec();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       cnt82 = alts;
                                       return cnt82;
                                    }
                              }
                        }

                        label700:
                        while(true) {
                           int alt80 = 2;
                           int LA80_0 = this.input.LA(1);
                           if (LA80_0 == 9) {
                              alt80 = 1;
                           }

                           int LA83_0;
                           int LA79_0;
                           int LA81_0;
                           switch (alt80) {
                              case 1:
                                 this.match(this.input, 9, FOLLOW_AMPERSAND_in_testSetRule1831);
                                 if (this.state.failed) {
                                    LA83_0 = alts;
                                    return LA83_0;
                                 }

                                 if (this.input.LA(1) != 2) {
                                    break;
                                 }

                                 this.match(this.input, 2, (BitSet)null);
                                 if (this.state.failed) {
                                    LA83_0 = alts;
                                    return LA83_0;
                                 }

                                 while(true) {
                                    LA83_0 = 2;
                                    LA79_0 = this.input.LA(1);
                                    if (LA79_0 >= 4 && LA79_0 <= 102) {
                                       LA83_0 = 1;
                                    } else if (LA79_0 == 3) {
                                       LA83_0 = 2;
                                    }

                                    switch (LA83_0) {
                                       case 1:
                                          this.matchAny(this.input);
                                          if (this.state.failed) {
                                             LA81_0 = alts;
                                             return LA81_0;
                                          }
                                          break;
                                       default:
                                          this.match(this.input, 3, (BitSet)null);
                                          if (this.state.failed) {
                                             LA83_0 = alts;
                                             return LA83_0;
                                          }
                                          continue label700;
                                    }
                                 }
                              default:
                                 this.match(this.input, 16, FOLLOW_BLOCK_in_testSetRule1845);
                                 if (this.state.failed) {
                                    cnt82 = alts;
                                    return cnt82;
                                 }

                                 this.match(this.input, 2, (BitSet)null);
                                 if (this.state.failed) {
                                    cnt82 = alts;
                                    return cnt82;
                                 }

                                 cnt82 = 0;

                                 while(true) {
                                    int alt83 = 2;
                                    LA83_0 = this.input.LA(1);
                                    if (LA83_0 == 8) {
                                       alt83 = 1;
                                    }

                                    switch (alt83) {
                                       case 1:
                                          this.match(this.input, 8, FOLLOW_ALT_in_testSetRule1854);
                                          if (this.state.failed) {
                                             LA79_0 = alts;
                                             return LA79_0;
                                          }

                                          this.match(this.input, 2, (BitSet)null);
                                          if (this.state.failed) {
                                             LA79_0 = alts;
                                             return LA79_0;
                                          }

                                          int alt81 = 2;
                                          LA81_0 = this.input.LA(1);
                                          if (LA81_0 == 14) {
                                             alt81 = 1;
                                          }

                                          int var15;
                                          switch (alt81) {
                                             case 1:
                                                this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_testSetRule1857);
                                                if (this.state.failed) {
                                                   var15 = alts;
                                                   return var15;
                                                }
                                          }

                                          this.pushFollow(FOLLOW_testSetElement_in_testSetRule1861);
                                          int testSetElement11 = this.testSetElement();
                                          --this.state._fsp;
                                          if (this.state.failed) {
                                             var15 = alts;
                                             return var15;
                                          }

                                          alts += testSetElement11;
                                          this.match(this.input, 32, FOLLOW_EOA_in_testSetRule1865);
                                          if (this.state.failed) {
                                             var15 = alts;
                                             return var15;
                                          }

                                          this.match(this.input, 3, (BitSet)null);
                                          if (this.state.failed) {
                                             var15 = alts;
                                             return var15;
                                          }

                                          ++cnt82;
                                          break;
                                       default:
                                          if (cnt82 < 1) {
                                             if (this.state.backtracking > 0) {
                                                this.state.failed = true;
                                                LA79_0 = alts;
                                                return LA79_0;
                                             }

                                             EarlyExitException eee = new EarlyExitException(82, this.input);
                                             throw eee;
                                          }

                                          this.match(this.input, 33, FOLLOW_EOB_in_testSetRule1879);
                                          if (this.state.failed) {
                                             LA80_0 = alts;
                                             return LA80_0;
                                          }

                                          this.match(this.input, 3, (BitSet)null);
                                          if (this.state.failed) {
                                             LA80_0 = alts;
                                             return LA80_0;
                                          }

                                          alt83 = 2;
                                          LA83_0 = this.input.LA(1);
                                          if (LA83_0 == 17 || LA83_0 == 38) {
                                             alt83 = 1;
                                          }

                                          switch (alt83) {
                                             case 1:
                                                this.pushFollow(FOLLOW_exceptionGroup_in_testSetRule1890);
                                                this.exceptionGroup();
                                                --this.state._fsp;
                                                if (this.state.failed) {
                                                   LA79_0 = alts;
                                                   return LA79_0;
                                                }
                                          }

                                          this.match(this.input, 34, FOLLOW_EOR_in_testSetRule1897);
                                          if (this.state.failed) {
                                             LA79_0 = alts;
                                             return LA79_0;
                                          }

                                          this.match(this.input, 3, (BitSet)null);
                                          if (!this.state.failed) {
                                             return alts;
                                          }

                                          LA79_0 = alts;
                                          return LA79_0;
                                    }
                                 }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (RecognitionException var19) {
         throw var19;
      } finally {
         --this.inTest;
      }
   }

   public final int testSetElement() throws RecognitionException {
      int alts = 1;
      GrammarAST c = null;
      GrammarAST t = null;
      GrammarAST s = null;
      GrammarAST c1 = null;
      GrammarAST c2 = null;
      int tse = false;
      int testBlockAsSet12 = false;

      try {
         int alt84 = true;
         int LA84_0 = this.input.LA(1);
         byte alt84;
         if (LA84_0 == 18) {
            alt84 = 1;
         } else if (LA84_0 == 94) {
            alt84 = 2;
         } else if (LA84_0 == 88 && this.grammar.type != 1) {
            alt84 = 3;
         } else if (LA84_0 == 19) {
            alt84 = 4;
         } else if (LA84_0 == 16) {
            alt84 = 5;
         } else {
            if (LA84_0 != 55) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return alts;
               }

               NoViableAltException nvae = new NoViableAltException("", 84, 0, this.input);
               throw nvae;
            }

            alt84 = 6;
         }

         switch (alt84) {
            case 1:
               c = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_testSetElement1929);
               if (this.state.failed) {
                  return alts;
               }

               if (this.hasElementOptions(c)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return alts;
                  }

                  throw new FailedPredicateException(this.input, "testSetElement", "!hasElementOptions($c)");
               }
               break;
            case 2:
               t = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_testSetElement1938);
               if (this.state.failed) {
                  return alts;
               }

               if (this.hasElementOptions(t)) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return alts;
                  }

                  throw new FailedPredicateException(this.input, "testSetElement", "!hasElementOptions($t)");
               }

               if (this.grammar.type == 1) {
                  Rule rule = this.grammar.getRule(t != null ? t.getText() : null);
                  if (rule == null) {
                     throw new RecognitionException();
                  }

                  alts += this.testSetRule(rule.tree);
               }
               break;
            case 3:
               if (this.grammar.type == 1) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return alts;
                  }

                  throw new FailedPredicateException(this.input, "testSetElement", "grammar.type!=Grammar.LEXER");
               }

               s = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_testSetElement1957);
               if (this.state.failed) {
                  return alts;
               }
               break;
            case 4:
               this.match(this.input, 19, FOLLOW_CHAR_RANGE_in_testSetElement1963);
               if (this.state.failed) {
                  return alts;
               }

               this.match(this.input, 2, (BitSet)null);
               if (this.state.failed) {
                  return alts;
               }

               c1 = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_testSetElement1967);
               if (this.state.failed) {
                  return alts;
               }

               c2 = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_testSetElement1971);
               if (this.state.failed) {
                  return alts;
               }

               this.match(this.input, 3, (BitSet)null);
               if (this.state.failed) {
                  return alts;
               }

               alts = IntervalSet.of(Grammar.getCharValueFromGrammarCharLiteral(c1 != null ? c1.getText() : null), Grammar.getCharValueFromGrammarCharLiteral(c2 != null ? c2.getText() : null)).size();
               break;
            case 5:
               this.pushFollow(FOLLOW_testBlockAsSet_in_testSetElement1983);
               int testBlockAsSet12 = this.testBlockAsSet();
               --this.state._fsp;
               if (this.state.failed) {
                  return alts;
               }

               alts = testBlockAsSet12;
               break;
            case 6:
               this.match(this.input, 55, FOLLOW_NOT_in_testSetElement1996);
               if (this.state.failed) {
                  return alts;
               }

               this.match(this.input, 2, (BitSet)null);
               if (this.state.failed) {
                  return alts;
               }

               this.pushFollow(FOLLOW_testSetElement_in_testSetElement2000);
               int tse = this.testSetElement();
               --this.state._fsp;
               if (this.state.failed) {
                  return alts;
               }

               this.match(this.input, 3, (BitSet)null);
               if (this.state.failed) {
                  return alts;
               }

               alts = this.grammar.getTokenTypes().size() - tse;
         }

         return alts;
      } catch (RecognitionException var15) {
         throw var15;
      } finally {
         ;
      }
   }

   public static class set_return extends TreeRuleReturnScope {
      public StateCluster g = null;
   }

   public static class atom_return extends TreeRuleReturnScope {
      public StateCluster g = null;
   }

   public static class tree__return extends TreeRuleReturnScope {
      public StateCluster g = null;
   }

   public static class ebnf_return extends TreeRuleReturnScope {
      public StateCluster g = null;
   }

   public static class element_return extends TreeRuleReturnScope {
      public StateCluster g = null;
   }

   public static class rewrite_return extends TreeRuleReturnScope {
   }

   public static class block_return extends TreeRuleReturnScope {
      public StateCluster g = null;
   }

   public static class rule_return extends TreeRuleReturnScope {
   }
}
