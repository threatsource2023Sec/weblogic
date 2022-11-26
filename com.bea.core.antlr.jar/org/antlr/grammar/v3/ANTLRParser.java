package org.antlr.grammar.v3;

import java.util.HashMap;
import java.util.Map;
import org.antlr.misc.IntSet;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.MissingTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.UnwantedTokenException;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteEarlyExitException;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;
import org.antlr.tool.Rule;

public class ANTLRParser extends Parser {
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
   protected TreeAdaptor adaptor;
   protected String currentRuleName;
   protected GrammarAST currentBlockAST;
   protected boolean atTreeRoot;
   private Grammar grammar;
   private int grammarType;
   private String fileName;
   public static final BitSet FOLLOW_ACTION_in_grammar_324 = new BitSet(new long[]{1153488852740997120L, 2147483648L});
   public static final BitSet FOLLOW_DOC_COMMENT_in_grammar_335 = new BitSet(new long[]{1153488852606779392L, 2147483648L});
   public static final BitSet FOLLOW_grammarType_in_grammar_345 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_id_in_grammar_349 = new BitSet(new long[]{0L, 262144L});
   public static final BitSet FOLLOW_SEMI_in_grammar_353 = new BitSet(new long[]{288266660169646592L, 1610809372L});
   public static final BitSet FOLLOW_optionsSpec_in_grammar_359 = new BitSet(new long[]{36284017934848L, 1610809372L});
   public static final BitSet FOLLOW_delegateGrammars_in_grammar_373 = new BitSet(new long[]{1099645846016L, 1610809372L});
   public static final BitSet FOLLOW_tokensSpec_in_grammar_382 = new BitSet(new long[]{1099645846016L, 1073938460L});
   public static final BitSet FOLLOW_attrScopes_in_grammar_390 = new BitSet(new long[]{1099645846016L, 1073807388L});
   public static final BitSet FOLLOW_actions_in_grammar_397 = new BitSet(new long[]{1099645845504L, 1073807388L});
   public static final BitSet FOLLOW_rules_in_grammar_405 = new BitSet(new long[]{0L});
   public static final BitSet FOLLOW_EOF_in_grammar_409 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LEXER_in_grammarType460 = new BitSet(new long[]{4398046511104L});
   public static final BitSet FOLLOW_GRAMMAR_in_grammarType465 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_PARSER_in_grammarType488 = new BitSet(new long[]{4398046511104L});
   public static final BitSet FOLLOW_GRAMMAR_in_grammarType492 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TREE_in_grammarType513 = new BitSet(new long[]{4398046511104L});
   public static final BitSet FOLLOW_GRAMMAR_in_grammarType519 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_GRAMMAR_in_grammarType542 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_action_in_actions569 = new BitSet(new long[]{514L});
   public static final BitSet FOLLOW_AMPERSAND_in_action584 = new BitSet(new long[]{1153484454560268288L, 1073807360L});
   public static final BitSet FOLLOW_actionScopeName_in_action588 = new BitSet(new long[]{4194304L});
   public static final BitSet FOLLOW_COLON_in_action590 = new BitSet(new long[]{4194304L});
   public static final BitSet FOLLOW_COLON_in_action593 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_id_in_action598 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_action600 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_actionScopeName613 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LEXER_in_actionScopeName620 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_PARSER_in_actionScopeName634 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec656 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_option_in_optionsSpec660 = new BitSet(new long[]{0L, 262144L});
   public static final BitSet FOLLOW_SEMI_in_optionsSpec663 = new BitSet(new long[]{0L, 1073807488L});
   public static final BitSet FOLLOW_RCURLY_in_optionsSpec668 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_option681 = new BitSet(new long[]{8192L});
   public static final BitSet FOLLOW_ASSIGN_in_option683 = new BitSet(new long[]{140737488617472L, 1094778880L});
   public static final BitSet FOLLOW_optionValue_in_option686 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_optionValue707 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_optionValue719 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_optionValue728 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_INT_in_optionValue739 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STAR_in_optionValue759 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_IMPORT_in_delegateGrammars784 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_delegateGrammar_in_delegateGrammars787 = new BitSet(new long[]{16777216L, 262144L});
   public static final BitSet FOLLOW_COMMA_in_delegateGrammars790 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_delegateGrammar_in_delegateGrammars793 = new BitSet(new long[]{16777216L, 262144L});
   public static final BitSet FOLLOW_SEMI_in_delegateGrammars797 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_delegateGrammar811 = new BitSet(new long[]{8192L});
   public static final BitSet FOLLOW_ASSIGN_in_delegateGrammar813 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_id_in_delegateGrammar818 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_delegateGrammar827 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKENS_in_tokensSpec854 = new BitSet(new long[]{0L, 1073741952L});
   public static final BitSet FOLLOW_tokenSpec_in_tokensSpec860 = new BitSet(new long[]{0L, 1073741952L});
   public static final BitSet FOLLOW_RCURLY_in_tokensSpec865 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec877 = new BitSet(new long[]{8192L, 262144L});
   public static final BitSet FOLLOW_ASSIGN_in_tokenSpec881 = new BitSet(new long[]{262144L, 16777216L});
   public static final BitSet FOLLOW_set_in_tokenSpec884 = new BitSet(new long[]{0L, 262144L});
   public static final BitSet FOLLOW_SEMI_in_tokenSpec893 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_attrScope_in_attrScopes906 = new BitSet(new long[]{2L, 131072L});
   public static final BitSet FOLLOW_SCOPE_in_attrScope919 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_id_in_attrScope922 = new BitSet(new long[]{528L});
   public static final BitSet FOLLOW_ruleActions_in_attrScope924 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_attrScope927 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rule_in_rules940 = new BitSet(new long[]{1099645845506L, 1073807388L});
   public static final BitSet FOLLOW_DOC_COMMENT_in_rule970 = new BitSet(new long[]{1099511627776L, 1073807388L});
   public static final BitSet FOLLOW_PROTECTED_in_rule983 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_PUBLIC_in_rule992 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_PRIVATE_in_rule1002 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_FRAGMENT_in_rule1011 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_id_in_rule1023 = new BitSet(new long[]{288230376155943424L, 268567552L});
   public static final BitSet FOLLOW_BANG_in_rule1033 = new BitSet(new long[]{288230376155910656L, 268567552L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rule1044 = new BitSet(new long[]{288230376155906560L, 268567552L});
   public static final BitSet FOLLOW_RETURNS_in_rule1053 = new BitSet(new long[]{4096L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rule1057 = new BitSet(new long[]{288230376155906560L, 268566528L});
   public static final BitSet FOLLOW_throwsSpec_in_rule1067 = new BitSet(new long[]{288230376155906560L, 131072L});
   public static final BitSet FOLLOW_optionsSpec_in_rule1076 = new BitSet(new long[]{4194816L, 131072L});
   public static final BitSet FOLLOW_ruleScopeSpec_in_rule1085 = new BitSet(new long[]{4194816L});
   public static final BitSet FOLLOW_ruleActions_in_rule1090 = new BitSet(new long[]{4194304L});
   public static final BitSet FOLLOW_COLON_in_rule1096 = new BitSet(new long[]{614741898892148752L, 22565947392L});
   public static final BitSet FOLLOW_ruleAltList_in_rule1100 = new BitSet(new long[]{0L, 262144L});
   public static final BitSet FOLLOW_SEMI_in_rule1105 = new BitSet(new long[]{274878038018L});
   public static final BitSet FOLLOW_exceptionGroup_in_rule1113 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ruleAction_in_ruleActions1251 = new BitSet(new long[]{514L});
   public static final BitSet FOLLOW_AMPERSAND_in_ruleAction1266 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_id_in_ruleAction1269 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_ruleAction1271 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_THROWS_in_throwsSpec1282 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_id_in_throwsSpec1285 = new BitSet(new long[]{16777218L});
   public static final BitSet FOLLOW_COMMA_in_throwsSpec1289 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_id_in_throwsSpec1292 = new BitSet(new long[]{16777218L});
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1308 = new BitSet(new long[]{528L});
   public static final BitSet FOLLOW_ruleActions_in_ruleScopeSpec1310 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec1313 = new BitSet(new long[]{2L, 131072L});
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1322 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_idList_in_ruleScopeSpec1324 = new BitSet(new long[]{0L, 262144L});
   public static final BitSet FOLLOW_SEMI_in_ruleScopeSpec1326 = new BitSet(new long[]{2L, 131072L});
   public static final BitSet FOLLOW_alternative_in_ruleAltList1383 = new BitSet(new long[]{576460752303423488L, 2048L});
   public static final BitSet FOLLOW_rewrite_in_ruleAltList1387 = new BitSet(new long[]{576460752303423490L});
   public static final BitSet FOLLOW_OR_in_ruleAltList1416 = new BitSet(new long[]{614741898892148752L, 22565947392L});
   public static final BitSet FOLLOW_alternative_in_ruleAltList1420 = new BitSet(new long[]{576460752303423488L, 2048L});
   public static final BitSet FOLLOW_rewrite_in_ruleAltList1424 = new BitSet(new long[]{576460752303423490L});
   public static final BitSet FOLLOW_LPAREN_in_block1500 = new BitSet(new long[]{902972275048055312L, 22565963776L});
   public static final BitSet FOLLOW_optionsSpec_in_block1538 = new BitSet(new long[]{4194816L});
   public static final BitSet FOLLOW_ruleActions_in_block1549 = new BitSet(new long[]{4194304L});
   public static final BitSet FOLLOW_COLON_in_block1557 = new BitSet(new long[]{614741898892148752L, 22565963776L});
   public static final BitSet FOLLOW_ACTION_in_block1563 = new BitSet(new long[]{4194304L});
   public static final BitSet FOLLOW_COLON_in_block1565 = new BitSet(new long[]{614741898892148752L, 22565963776L});
   public static final BitSet FOLLOW_alternative_in_block1577 = new BitSet(new long[]{576460752303423488L, 18432L});
   public static final BitSet FOLLOW_rewrite_in_block1581 = new BitSet(new long[]{576460752303423488L, 16384L});
   public static final BitSet FOLLOW_OR_in_block1591 = new BitSet(new long[]{614741898892148752L, 22565963776L});
   public static final BitSet FOLLOW_alternative_in_block1595 = new BitSet(new long[]{576460752303423488L, 18432L});
   public static final BitSet FOLLOW_rewrite_in_block1599 = new BitSet(new long[]{576460752303423488L, 16384L});
   public static final BitSet FOLLOW_RPAREN_in_block1616 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_element_in_alternative1656 = new BitSet(new long[]{38281146588725266L, 22565945344L});
   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup1702 = new BitSet(new long[]{274878038018L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1705 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1711 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CATCH_in_exceptionHandler1722 = new BitSet(new long[]{4096L});
   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler1725 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_exceptionHandler1727 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FINALLY_in_finallyClause1738 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_finallyClause1741 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_elementNoOptionSpec_in_element1752 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_elementNoOptionSpec1770 = new BitSet(new long[]{-9223372036854767616L});
   public static final BitSet FOLLOW_ASSIGN_in_elementNoOptionSpec1773 = new BitSet(new long[]{38280596832911360L, 18270453760L});
   public static final BitSet FOLLOW_PLUS_ASSIGN_in_elementNoOptionSpec1776 = new BitSet(new long[]{38280596832911360L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_elementNoOptionSpec1785 = new BitSet(new long[]{4611686018427387906L, 4194336L});
   public static final BitSet FOLLOW_ebnfSuffix_in_elementNoOptionSpec1790 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ebnf_in_elementNoOptionSpec1803 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_atom_in_elementNoOptionSpec1816 = new BitSet(new long[]{4611686018427387906L, 4194336L});
   public static final BitSet FOLLOW_ebnfSuffix_in_elementNoOptionSpec1825 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ebnf_in_elementNoOptionSpec1841 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FORCED_ACTION_in_elementNoOptionSpec1847 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ACTION_in_elementNoOptionSpec1853 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SEMPRED_in_elementNoOptionSpec1861 = new BitSet(new long[]{17592186044418L});
   public static final BitSet FOLLOW_IMPLIES_in_elementNoOptionSpec1865 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_tree__in_elementNoOptionSpec1884 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_range_in_atom1899 = new BitSet(new long[]{32770L, 8192L});
   public static final BitSet FOLLOW_ROOT_in_atom1902 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BANG_in_atom1905 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_atom1945 = new BitSet(new long[]{0L, 17179869184L});
   public static final BitSet FOLLOW_WILDCARD_in_atom1949 = new BitSet(new long[]{262144L, 18270453760L});
   public static final BitSet FOLLOW_terminal_in_atom1953 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ruleref_in_atom1955 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_terminal_in_atom1964 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ruleref_in_atom1970 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_notSet_in_atom1979 = new BitSet(new long[]{32770L, 8192L});
   public static final BitSet FOLLOW_ROOT_in_atom1982 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BANG_in_atom1985 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_RULE_REF_in_ruleref1999 = new BitSet(new long[]{36866L, 8192L});
   public static final BitSet FOLLOW_ARG_ACTION_in_ruleref2002 = new BitSet(new long[]{32770L, 8192L});
   public static final BitSet FOLLOW_ROOT_in_ruleref2006 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BANG_in_ruleref2009 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NOT_in_notSet2023 = new BitSet(new long[]{2251799813947392L, 1090519040L});
   public static final BitSet FOLLOW_notTerminal_in_notSet2030 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_block_in_notSet2036 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_treeRoot2059 = new BitSet(new long[]{-9223372036854767616L});
   public static final BitSet FOLLOW_ASSIGN_in_treeRoot2062 = new BitSet(new long[]{38280596832911360L, 18270453760L});
   public static final BitSet FOLLOW_PLUS_ASSIGN_in_treeRoot2065 = new BitSet(new long[]{38280596832911360L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_treeRoot2070 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_block_in_treeRoot2072 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_atom_in_treeRoot2078 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_block_in_treeRoot2083 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TREE_BEGIN_in_tree_2094 = new BitSet(new long[]{38280596832911360L, 18270453760L});
   public static final BitSet FOLLOW_treeRoot_in_tree_2099 = new BitSet(new long[]{38281146588725264L, 22565945344L});
   public static final BitSet FOLLOW_element_in_tree_2101 = new BitSet(new long[]{38281146588725264L, 22565961728L});
   public static final BitSet FOLLOW_RPAREN_in_tree_2106 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_block_in_ebnf2120 = new BitSet(new long[]{4611703610613465090L, 4202528L});
   public static final BitSet FOLLOW_QUESTION_in_ebnf2126 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STAR_in_ebnf2144 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_PLUS_in_ebnf2162 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_IMPLIES_in_ebnf2180 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ROOT_in_ebnf2216 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BANG_in_ebnf2233 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2280 = new BitSet(new long[]{0L, 64L});
   public static final BitSet FOLLOW_RANGE_in_range2282 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2286 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_range2313 = new BitSet(new long[]{0L, 64L});
   public static final BitSet FOLLOW_RANGE_in_range2317 = new BitSet(new long[]{0L, 1073741824L});
   public static final BitSet FOLLOW_TOKEN_REF_in_range2319 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_range2327 = new BitSet(new long[]{0L, 64L});
   public static final BitSet FOLLOW_RANGE_in_range2331 = new BitSet(new long[]{0L, 16777216L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_range2333 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2341 = new BitSet(new long[]{0L, 64L});
   public static final BitSet FOLLOW_RANGE_in_range2345 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2347 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_terminal2376 = new BitSet(new long[]{72057594037960706L, 8192L});
   public static final BitSet FOLLOW_elementOptions_in_terminal2381 = new BitSet(new long[]{32770L, 8192L});
   public static final BitSet FOLLOW_ROOT_in_terminal2389 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BANG_in_terminal2392 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_terminal2403 = new BitSet(new long[]{72057594037964802L, 8192L});
   public static final BitSet FOLLOW_elementOptions_in_terminal2410 = new BitSet(new long[]{36866L, 8192L});
   public static final BitSet FOLLOW_ARG_ACTION_in_terminal2421 = new BitSet(new long[]{32770L, 8192L});
   public static final BitSet FOLLOW_ROOT_in_terminal2430 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BANG_in_terminal2433 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_terminal2444 = new BitSet(new long[]{72057594037960706L, 8192L});
   public static final BitSet FOLLOW_elementOptions_in_terminal2449 = new BitSet(new long[]{32770L, 8192L});
   public static final BitSet FOLLOW_ROOT_in_terminal2457 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BANG_in_terminal2460 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_WILDCARD_in_terminal2471 = new BitSet(new long[]{32770L, 8192L});
   public static final BitSet FOLLOW_ROOT_in_terminal2474 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BANG_in_terminal2477 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPEN_ELEMENT_OPTION_in_elementOptions2496 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_defaultNodeOption_in_elementOptions2499 = new BitSet(new long[]{1048576L});
   public static final BitSet FOLLOW_CLOSE_ELEMENT_OPTION_in_elementOptions2502 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPEN_ELEMENT_OPTION_in_elementOptions2508 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_elementOption_in_elementOptions2511 = new BitSet(new long[]{1048576L, 262144L});
   public static final BitSet FOLLOW_SEMI_in_elementOptions2515 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_elementOption_in_elementOptions2518 = new BitSet(new long[]{1048576L, 262144L});
   public static final BitSet FOLLOW_CLOSE_ELEMENT_OPTION_in_elementOptions2523 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_elementOptionId_in_defaultNodeOption2536 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_elementOption2552 = new BitSet(new long[]{8192L});
   public static final BitSet FOLLOW_ASSIGN_in_elementOption2554 = new BitSet(new long[]{3221225472L, 1090584576L});
   public static final BitSet FOLLOW_elementOptionId_in_elementOption2561 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_elementOption2575 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_elementOption2579 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_elementOption2583 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_elementOptionId2614 = new BitSet(new long[]{2L, 17179869184L});
   public static final BitSet FOLLOW_WILDCARD_in_elementOptionId2619 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_id_in_elementOptionId2623 = new BitSet(new long[]{2L, 17179869184L});
   public static final BitSet FOLLOW_QUESTION_in_ebnfSuffix2700 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STAR_in_ebnfSuffix2714 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_PLUS_in_ebnfSuffix2728 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_idList2790 = new BitSet(new long[]{16777218L});
   public static final BitSet FOLLOW_COMMA_in_idList2793 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_id_in_idList2796 = new BitSet(new long[]{16777218L});
   public static final BitSet FOLLOW_TOKEN_REF_in_id2809 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_RULE_REF_in_id2821 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_with_sempred_in_rewrite2841 = new BitSet(new long[]{0L, 2048L});
   public static final BitSet FOLLOW_REWRITE_in_rewrite2846 = new BitSet(new long[]{2251937521336336L, 5385551872L});
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite2848 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_REWRITE_in_rewrite_with_sempred2879 = new BitSet(new long[]{0L, 524288L});
   public static final BitSet FOLLOW_SEMPRED_in_rewrite_with_sempred2882 = new BitSet(new long[]{2251937521336336L, 5385551872L});
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite_with_sempred2884 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LPAREN_in_rewrite_block2895 = new BitSet(new long[]{2251937521336336L, 5385568256L});
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite_block2899 = new BitSet(new long[]{0L, 16384L});
   public static final BitSet FOLLOW_RPAREN_in_rewrite_block2903 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_template_in_rewrite_alternative2939 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_element_in_rewrite_alternative2951 = new BitSet(new long[]{2251800082382866L, 5385551872L});
   public static final BitSet FOLLOW_ETC_in_rewrite_alternative3012 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_atom_in_rewrite_element3027 = new BitSet(new long[]{4611686018427387906L, 4194336L});
   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_element3047 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_ebnf_in_rewrite_element3066 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_tree_in_rewrite_element3075 = new BitSet(new long[]{4611686018427387906L, 4194336L});
   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_element3095 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_atom3122 = new BitSet(new long[]{72057594037932034L});
   public static final BitSet FOLLOW_elementOptions_in_rewrite_atom3125 = new BitSet(new long[]{4098L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rewrite_atom3130 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_RULE_REF_in_rewrite_atom3137 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_rewrite_atom3144 = new BitSet(new long[]{72057594037927938L});
   public static final BitSet FOLLOW_elementOptions_in_rewrite_atom3146 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_rewrite_atom3156 = new BitSet(new long[]{72057594037927938L});
   public static final BitSet FOLLOW_elementOptions_in_rewrite_atom3158 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_DOLLAR_in_rewrite_atom3166 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_label_in_rewrite_atom3169 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_atom3175 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_label3186 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_RULE_REF_in_label3196 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_block_in_rewrite_ebnf3214 = new BitSet(new long[]{4611686018427387904L, 4194336L});
   public static final BitSet FOLLOW_QUESTION_in_rewrite_ebnf3220 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STAR_in_rewrite_ebnf3239 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_PLUS_in_rewrite_ebnf3258 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TREE_BEGIN_in_rewrite_tree3286 = new BitSet(new long[]{268697616L, 1090584576L});
   public static final BitSet FOLLOW_rewrite_atom_in_rewrite_tree3292 = new BitSet(new long[]{2251800082382864L, 5385568256L});
   public static final BitSet FOLLOW_rewrite_element_in_rewrite_tree3294 = new BitSet(new long[]{2251800082382864L, 5385568256L});
   public static final BitSet FOLLOW_RPAREN_in_rewrite_tree3299 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_template_head_in_rewrite_template3334 = new BitSet(new long[]{3221225472L});
   public static final BitSet FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template3353 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template3359 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_template_head_in_rewrite_template3374 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_indirect_template_head_in_rewrite_template3383 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_template3392 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_rewrite_template_head3405 = new BitSet(new long[]{2251799813685248L});
   public static final BitSet FOLLOW_LPAREN_in_rewrite_template_head3409 = new BitSet(new long[]{0L, 1073823744L});
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template_head3413 = new BitSet(new long[]{0L, 16384L});
   public static final BitSet FOLLOW_RPAREN_in_rewrite_template_head3417 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LPAREN_in_rewrite_indirect_template_head3445 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_indirect_template_head3449 = new BitSet(new long[]{0L, 16384L});
   public static final BitSet FOLLOW_RPAREN_in_rewrite_indirect_template_head3453 = new BitSet(new long[]{2251799813685248L});
   public static final BitSet FOLLOW_LPAREN_in_rewrite_indirect_template_head3457 = new BitSet(new long[]{0L, 1073823744L});
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3459 = new BitSet(new long[]{0L, 16384L});
   public static final BitSet FOLLOW_RPAREN_in_rewrite_indirect_template_head3461 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args3485 = new BitSet(new long[]{16777218L});
   public static final BitSet FOLLOW_COMMA_in_rewrite_template_args3488 = new BitSet(new long[]{0L, 1073807360L});
   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args3490 = new BitSet(new long[]{16777218L});
   public static final BitSet FOLLOW_id_in_rewrite_template_arg3525 = new BitSet(new long[]{8192L});
   public static final BitSet FOLLOW_ASSIGN_in_rewrite_template_arg3529 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_template_arg3531 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_id_in_synpred1_ANTLR1929 = new BitSet(new long[]{0L, 17179869184L});
   public static final BitSet FOLLOW_WILDCARD_in_synpred1_ANTLR1931 = new BitSet(new long[]{262144L, 18270453760L});
   public static final BitSet FOLLOW_terminal_in_synpred1_ANTLR1934 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ruleref_in_synpred1_ANTLR1936 = new BitSet(new long[]{2L});

   public Parser[] getDelegates() {
      return new Parser[0];
   }

   public ANTLRParser(TokenStream input) {
      this(input, new RecognizerSharedState());
   }

   public ANTLRParser(TokenStream input, RecognizerSharedState state) {
      super(input, state);
      this.adaptor = new CommonTreeAdaptor();
      this.currentRuleName = null;
      this.currentBlockAST = null;
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
      return "org\\antlr\\grammar\\v3\\ANTLR.g";
   }

   public static ANTLRParser createParser(TokenStream input) {
      ANTLRParser parser = new ANTLRParser(input);
      parser.adaptor = new grammar_Adaptor(parser);
      return parser;
   }

   public Grammar getGrammar() {
      return this.grammar;
   }

   public void setGrammar(Grammar value) {
      this.grammar = value;
   }

   public int getGrammarType() {
      return this.grammarType;
   }

   public void setGrammarType(int value) {
      this.grammarType = value;
   }

   public String getFileName() {
      return this.fileName;
   }

   public void setFileName(String value) {
      this.fileName = value;
   }

   private final int LA(int i) {
      return this.input.LA(i);
   }

   private final Token LT(int k) {
      return this.input.LT(k);
   }

   protected GrammarAST setToBlockWithSet(GrammarAST b) {
      GrammarAST alt = (GrammarAST)this.adaptor.create(8, (String)"ALT");
      this.adaptor.addChild(alt, b);
      this.adaptor.addChild(alt, this.adaptor.create(32, (String)"<end-of-alt>"));
      this.prefixWithSynPred(alt);
      GrammarAST block = (GrammarAST)this.adaptor.create(16, b.getToken(), "BLOCK");
      this.adaptor.addChild(block, alt);
      this.adaptor.addChild(alt, this.adaptor.create(33, (String)"<end-of-block>"));
      return block;
   }

   protected GrammarAST createBlockFromDupAlt(GrammarAST alt) {
      GrammarAST nalt = GrammarAST.dupTreeNoActions(alt, (GrammarAST)null);
      GrammarAST block = (GrammarAST)this.adaptor.create(16, alt.getToken(), "BLOCK");
      this.adaptor.addChild(block, nalt);
      this.adaptor.addChild(block, this.adaptor.create(33, (String)"<end-of-block>"));
      return block;
   }

   protected void prefixWithSynPred(GrammarAST alt) {
      String autoBacktrack = (String)this.grammar.getBlockOption(this.currentBlockAST, "backtrack");
      if (autoBacktrack == null) {
         autoBacktrack = (String)this.grammar.getOption("backtrack");
      }

      if (autoBacktrack != null && autoBacktrack.equals("true") && (this.grammarType != 4 || Rule.getRuleType(this.currentRuleName) != 1) && alt.getChild(0).getType() != 90) {
         GrammarAST synpredBlockAST = this.createBlockFromDupAlt(alt);
         GrammarAST synpredAST = this.createSynSemPredFromBlock(synpredBlockAST, 14);
         GrammarAST[] children = alt.getChildrenAsArray();
         this.adaptor.setChild(alt, 0, synpredAST);

         for(int i = 0; i < children.length; ++i) {
            if (i < children.length - 1) {
               this.adaptor.setChild(alt, i + 1, children[i]);
            } else {
               this.adaptor.addChild(alt, children[i]);
            }
         }
      }

   }

   protected GrammarAST createSynSemPredFromBlock(GrammarAST synpredBlockAST, int synpredTokenType) {
      String predName = this.grammar.defineSyntacticPredicate(synpredBlockAST, this.currentRuleName);
      GrammarAST p = (GrammarAST)this.adaptor.create(synpredTokenType, predName);
      this.grammar.blocksWithSynPreds.add(this.currentBlockAST);
      return p;
   }

   public static GrammarAST createSimpleRuleAST(String name, GrammarAST block, boolean fragment) {
      TreeAdaptor adaptor = new grammar_Adaptor((ANTLRParser)null);
      GrammarAST modifier = null;
      if (fragment) {
         modifier = (GrammarAST)adaptor.create(40, (String)"fragment");
      }

      GrammarAST rule = (GrammarAST)adaptor.create(79, block.getToken(), "rule");
      adaptor.addChild(rule, adaptor.create(43, (String)name));
      if (modifier != null) {
         adaptor.addChild(rule, modifier);
      }

      adaptor.addChild(rule, adaptor.create(10, (String)"ARG"));
      adaptor.addChild(rule, adaptor.create(73, (String)"RET"));
      adaptor.addChild(rule, adaptor.create(81, (String)"scope"));
      adaptor.addChild(rule, block);
      adaptor.addChild(rule, adaptor.create(34, block.getLastChild().getToken(), "<end-of-rule>"));
      return rule;
   }

   public void reportError(RecognitionException ex) {
      Token token = ex.token;
      ErrorManager.syntaxError(100, this.grammar, token, "antlr: " + ex.toString(), ex);
   }

   public void cleanup(GrammarAST root) {
      if (this.grammarType == 1) {
         String filter = (String)this.grammar.getOption("filter");
         GrammarAST var3 = this.grammar.addArtificialMatchTokensRule(root, this.grammar.lexerRuleNamesInCombined, this.grammar.getDelegateNames(), filter != null && filter.equals("true"));
      }

   }

   public final grammar__return grammar_(Grammar g) throws RecognitionException {
      grammar__return retval = new grammar__return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token cmt = null;
      Token ACTION1 = null;
      Token SEMI2 = null;
      Token EOF4 = null;
      ParserRuleReturnScope gr = null;
      ParserRuleReturnScope gid = null;
      ParserRuleReturnScope ig = null;
      ParserRuleReturnScope ts = null;
      ParserRuleReturnScope scopes = null;
      ParserRuleReturnScope a = null;
      ParserRuleReturnScope r = null;
      ParserRuleReturnScope optionsSpec3 = null;
      GrammarAST cmt_tree = null;
      GrammarAST ACTION1_tree = null;
      GrammarAST SEMI2_tree = null;
      GrammarAST EOF4_tree = null;
      RewriteRuleTokenStream stream_DOC_COMMENT = new RewriteRuleTokenStream(this.adaptor, "token DOC_COMMENT");
      RewriteRuleTokenStream stream_EOF = new RewriteRuleTokenStream(this.adaptor, "token EOF");
      RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_tokensSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule tokensSpec");
      RewriteRuleSubtreeStream stream_attrScopes = new RewriteRuleSubtreeStream(this.adaptor, "rule attrScopes");
      RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
      RewriteRuleSubtreeStream stream_delegateGrammars = new RewriteRuleSubtreeStream(this.adaptor, "rule delegateGrammars");
      RewriteRuleSubtreeStream stream_grammarType = new RewriteRuleSubtreeStream(this.adaptor, "rule grammarType");
      RewriteRuleSubtreeStream stream_actions = new RewriteRuleSubtreeStream(this.adaptor, "rule actions");
      RewriteRuleSubtreeStream stream_rules = new RewriteRuleSubtreeStream(this.adaptor, "rule rules");
      this.grammar = g;

      try {
         try {
            int alt1 = 2;
            int LA1_0 = this.input.LA(1);
            if (LA1_0 == 4) {
               alt1 = 1;
            }

            switch (alt1) {
               case 1:
                  ACTION1 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_grammar_324);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_ACTION.add(ACTION1);
                  }
               default:
                  int alt2 = 2;
                  int LA2_0 = this.input.LA(1);
                  if (LA2_0 == 27) {
                     alt2 = 1;
                  }

                  switch (alt2) {
                     case 1:
                        cmt = (Token)this.match(this.input, 27, FOLLOW_DOC_COMMENT_in_grammar_335);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_DOC_COMMENT.add(cmt);
                        }
                     default:
                        this.pushFollow(FOLLOW_grammarType_in_grammar_345);
                        gr = this.grammarType();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_grammarType.add(gr.getTree());
                        }

                        this.pushFollow(FOLLOW_id_in_grammar_349);
                        gid = this.id();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_id.add(gid.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           this.grammar.setName(gid != null ? this.input.toString(gid.start, gid.stop) : null);
                        }

                        SEMI2 = (Token)this.match(this.input, 82, FOLLOW_SEMI_in_grammar_353);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_SEMI.add(SEMI2);
                        }

                        int alt3 = 2;
                        int LA3_0 = this.input.LA(1);
                        if (LA3_0 == 58) {
                           alt3 = 1;
                        }

                        switch (alt3) {
                           case 1:
                              this.pushFollow(FOLLOW_optionsSpec_in_grammar_359);
                              optionsSpec3 = this.optionsSpec();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_optionsSpec.add(optionsSpec3.getTree());
                              }

                              if (this.state.backtracking == 0) {
                                 Map opts = optionsSpec3 != null ? ((optionsSpec_return)optionsSpec3).opts : null;
                                 this.grammar.setOptions(opts, optionsSpec3 != null ? optionsSpec3.start : null);
                              }
                           default:
                              int alt4 = 2;
                              int LA4_0 = this.input.LA(1);
                              if (LA4_0 == 45) {
                                 alt4 = 1;
                              }

                              switch (alt4) {
                                 case 1:
                                    this.pushFollow(FOLLOW_delegateGrammars_in_grammar_373);
                                    ig = this.delegateGrammars();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       stream_delegateGrammars.add(ig.getTree());
                                    }
                                 default:
                                    int alt5 = 2;
                                    int LA5_0 = this.input.LA(1);
                                    if (LA5_0 == 93) {
                                       alt5 = 1;
                                    }

                                    switch (alt5) {
                                       case 1:
                                          this.pushFollow(FOLLOW_tokensSpec_in_grammar_382);
                                          ts = this.tokensSpec();
                                          --this.state._fsp;
                                          if (this.state.failed) {
                                             return retval;
                                          }

                                          if (this.state.backtracking == 0) {
                                             stream_tokensSpec.add(ts.getTree());
                                          }
                                       default:
                                          this.pushFollow(FOLLOW_attrScopes_in_grammar_390);
                                          scopes = this.attrScopes();
                                          --this.state._fsp;
                                          if (this.state.failed) {
                                             return retval;
                                          }

                                          if (this.state.backtracking == 0) {
                                             stream_attrScopes.add(scopes.getTree());
                                          }

                                          int alt6 = 2;
                                          int LA6_0 = this.input.LA(1);
                                          if (LA6_0 == 9) {
                                             alt6 = 1;
                                          }

                                          switch (alt6) {
                                             case 1:
                                                this.pushFollow(FOLLOW_actions_in_grammar_397);
                                                a = this.actions();
                                                --this.state._fsp;
                                                if (this.state.failed) {
                                                   return retval;
                                                }

                                                if (this.state.backtracking == 0) {
                                                   stream_actions.add(a.getTree());
                                                }
                                             default:
                                                this.pushFollow(FOLLOW_rules_in_grammar_405);
                                                r = this.rules();
                                                --this.state._fsp;
                                                if (this.state.failed) {
                                                   return retval;
                                                }

                                                if (this.state.backtracking == 0) {
                                                   stream_rules.add(r.getTree());
                                                }

                                                EOF4 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_grammar_409);
                                                if (this.state.failed) {
                                                   return retval;
                                                }

                                                if (this.state.backtracking == 0) {
                                                   stream_EOF.add(EOF4);
                                                }

                                                if (this.state.backtracking == 0) {
                                                   retval.tree = root_0;
                                                   RewriteRuleTokenStream stream_cmt = new RewriteRuleTokenStream(this.adaptor, "token cmt", cmt);
                                                   RewriteRuleSubtreeStream stream_scopes = new RewriteRuleSubtreeStream(this.adaptor, "rule scopes", scopes != null ? scopes.getTree() : null);
                                                   new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                                                   RewriteRuleSubtreeStream stream_ts = new RewriteRuleSubtreeStream(this.adaptor, "rule ts", ts != null ? ts.getTree() : null);
                                                   RewriteRuleSubtreeStream stream_r = new RewriteRuleSubtreeStream(this.adaptor, "rule r", r != null ? r.getTree() : null);
                                                   RewriteRuleSubtreeStream stream_ig = new RewriteRuleSubtreeStream(this.adaptor, "rule ig", ig != null ? ig.getTree() : null);
                                                   RewriteRuleSubtreeStream stream_a = new RewriteRuleSubtreeStream(this.adaptor, "rule a", a != null ? a.getTree() : null);
                                                   RewriteRuleSubtreeStream stream_gid = new RewriteRuleSubtreeStream(this.adaptor, "rule gid", gid != null ? gid.getTree() : null);
                                                   RewriteRuleSubtreeStream stream_gr = new RewriteRuleSubtreeStream(this.adaptor, "rule gr", gr != null ? gr.getTree() : null);
                                                   root_0 = (GrammarAST)this.adaptor.nil();
                                                   GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                                                   root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)stream_gr.nextNode(), root_1);
                                                   this.adaptor.addChild(root_1, stream_gid.nextTree());
                                                   if (stream_cmt.hasNext()) {
                                                      this.adaptor.addChild(root_1, stream_cmt.nextNode());
                                                   }

                                                   stream_cmt.reset();
                                                   if (stream_optionsSpec.hasNext()) {
                                                      this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
                                                   }

                                                   stream_optionsSpec.reset();
                                                   if (stream_ig.hasNext()) {
                                                      this.adaptor.addChild(root_1, stream_ig.nextTree());
                                                   }

                                                   stream_ig.reset();
                                                   if (stream_ts.hasNext()) {
                                                      this.adaptor.addChild(root_1, stream_ts.nextTree());
                                                   }

                                                   stream_ts.reset();
                                                   if (stream_scopes.hasNext()) {
                                                      this.adaptor.addChild(root_1, stream_scopes.nextTree());
                                                   }

                                                   stream_scopes.reset();
                                                   if (stream_a.hasNext()) {
                                                      this.adaptor.addChild(root_1, stream_a.nextTree());
                                                   }

                                                   stream_a.reset();
                                                   this.adaptor.addChild(root_1, stream_r.nextTree());
                                                   this.adaptor.addChild(root_0, root_1);
                                                   retval.tree = root_0;
                                                }

                                                retval.stop = this.input.LT(-1);
                                                if (this.state.backtracking == 0) {
                                                   retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                                                   this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                                                }

                                                if (this.state.backtracking == 0) {
                                                   this.cleanup(retval.tree);
                                                }
                                          }
                                    }
                              }
                        }
                  }
            }
         } catch (RecognitionException var58) {
            this.reportError(var58);
            this.recover(this.input, var58);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var58);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final grammarType_return grammarType() throws RecognitionException {
      grammarType_return retval = new grammarType_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token gr = null;
      Token string_literal5 = null;
      Token string_literal6 = null;
      Token string_literal7 = null;
      GrammarAST gr_tree = null;
      GrammarAST string_literal5_tree = null;
      GrammarAST string_literal6_tree = null;
      GrammarAST string_literal7_tree = null;
      RewriteRuleTokenStream stream_TREE = new RewriteRuleTokenStream(this.adaptor, "token TREE");
      RewriteRuleTokenStream stream_PARSER = new RewriteRuleTokenStream(this.adaptor, "token PARSER");
      RewriteRuleTokenStream stream_LEXER = new RewriteRuleTokenStream(this.adaptor, "token LEXER");
      RewriteRuleTokenStream stream_GRAMMAR = new RewriteRuleTokenStream(this.adaptor, "token GRAMMAR");

      try {
         try {
            int alt7 = true;
            byte alt7;
            switch (this.input.LA(1)) {
               case 42:
                  alt7 = 4;
                  break;
               case 49:
                  alt7 = 1;
                  break;
               case 60:
                  alt7 = 2;
                  break;
               case 95:
                  alt7 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 7, 0, this.input);
                  throw nvae;
            }

            switch (alt7) {
               case 1:
                  string_literal5 = (Token)this.match(this.input, 49, FOLLOW_LEXER_in_grammarType460);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_LEXER.add(string_literal5);
                  }

                  gr = (Token)this.match(this.input, 42, FOLLOW_GRAMMAR_in_grammarType465);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_GRAMMAR.add(gr);
                  }

                  if (this.state.backtracking == 0) {
                     this.grammarType = 1;
                     this.grammar.type = 1;
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(50, (Token)gr));
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  string_literal6 = (Token)this.match(this.input, 60, FOLLOW_PARSER_in_grammarType488);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_PARSER.add(string_literal6);
                  }

                  gr = (Token)this.match(this.input, 42, FOLLOW_GRAMMAR_in_grammarType492);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_GRAMMAR.add(gr);
                  }

                  if (this.state.backtracking == 0) {
                     this.grammarType = 2;
                     this.grammar.type = 2;
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(61, (Token)gr));
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  string_literal7 = (Token)this.match(this.input, 95, FOLLOW_TREE_in_grammarType513);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_TREE.add(string_literal7);
                  }

                  gr = (Token)this.match(this.input, 42, FOLLOW_GRAMMAR_in_grammarType519);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_GRAMMAR.add(gr);
                  }

                  if (this.state.backtracking == 0) {
                     this.grammarType = 3;
                     this.grammar.type = 3;
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(97, (Token)gr));
                     retval.tree = root_0;
                  }
                  break;
               case 4:
                  gr = (Token)this.match(this.input, 42, FOLLOW_GRAMMAR_in_grammarType542);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_GRAMMAR.add(gr);
                  }

                  if (this.state.backtracking == 0) {
                     this.grammarType = 4;
                     this.grammar.type = 4;
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(23, (Token)gr));
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var20) {
            this.reportError(var20);
            this.recover(this.input, var20);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var20);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final actions_return actions() throws RecognitionException {
      actions_return retval = new actions_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      ParserRuleReturnScope action8 = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         int cnt8 = 0;

         while(true) {
            int alt8 = 2;
            int LA8_0 = this.input.LA(1);
            if (LA8_0 == 9) {
               alt8 = 1;
            }

            switch (alt8) {
               case 1:
                  this.pushFollow(FOLLOW_action_in_actions569);
                  action8 = this.action();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, action8.getTree());
                  }

                  ++cnt8;
                  break;
               default:
                  if (cnt8 < 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     EarlyExitException eee = new EarlyExitException(8, this.input);
                     throw eee;
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  return retval;
            }
         }
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(this.input, var11);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var11);
         return retval;
      } finally {
         ;
      }
   }

   public final action_return action() throws RecognitionException {
      action_return retval = new action_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token AMPERSAND9 = null;
      Token COLON11 = null;
      Token COLON12 = null;
      Token ACTION14 = null;
      ParserRuleReturnScope actionScopeName10 = null;
      ParserRuleReturnScope id13 = null;
      GrammarAST AMPERSAND9_tree = null;
      GrammarAST COLON11_tree = null;
      GrammarAST COLON12_tree = null;
      GrammarAST ACTION14_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            AMPERSAND9 = (Token)this.match(this.input, 9, FOLLOW_AMPERSAND_in_action584);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               AMPERSAND9_tree = (GrammarAST)this.adaptor.create(AMPERSAND9);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)AMPERSAND9_tree, root_0);
            }

            int alt9 = 2;
            int LA9_1;
            switch (this.input.LA(1)) {
               case 49:
               case 60:
                  alt9 = 1;
                  break;
               case 80:
                  LA9_1 = this.input.LA(2);
                  if (LA9_1 == 22) {
                     alt9 = 1;
                  }
                  break;
               case 94:
                  LA9_1 = this.input.LA(2);
                  if (LA9_1 == 22) {
                     alt9 = 1;
                  }
            }

            switch (alt9) {
               case 1:
                  this.pushFollow(FOLLOW_actionScopeName_in_action588);
                  actionScopeName10 = this.actionScopeName();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, actionScopeName10.getTree());
                  }

                  COLON11 = (Token)this.match(this.input, 22, FOLLOW_COLON_in_action590);
                  if (this.state.failed) {
                     return retval;
                  }

                  COLON12 = (Token)this.match(this.input, 22, FOLLOW_COLON_in_action593);
                  if (this.state.failed) {
                     return retval;
                  }
               default:
                  this.pushFollow(FOLLOW_id_in_action598);
                  id13 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, id13.getTree());
                  }

                  ACTION14 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_action600);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION14_tree = (GrammarAST)this.adaptor.create(ACTION14);
                     this.adaptor.addChild(root_0, ACTION14_tree);
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final actionScopeName_return actionScopeName() throws RecognitionException {
      actionScopeName_return retval = new actionScopeName_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token l = null;
      Token p = null;
      ParserRuleReturnScope id15 = null;
      GrammarAST l_tree = null;
      GrammarAST p_tree = null;
      RewriteRuleTokenStream stream_PARSER = new RewriteRuleTokenStream(this.adaptor, "token PARSER");
      RewriteRuleTokenStream stream_LEXER = new RewriteRuleTokenStream(this.adaptor, "token LEXER");

      try {
         try {
            int alt10 = true;
            byte alt10;
            switch (this.input.LA(1)) {
               case 49:
                  alt10 = 2;
                  break;
               case 60:
                  alt10 = 3;
                  break;
               case 80:
               case 94:
                  alt10 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
                  throw nvae;
            }

            switch (alt10) {
               case 1:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_id_in_actionScopeName613);
                  id15 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, id15.getTree());
                  }
                  break;
               case 2:
                  l = (Token)this.match(this.input, 49, FOLLOW_LEXER_in_actionScopeName620);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_LEXER.add(l);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(43, (Token)l));
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  p = (Token)this.match(this.input, 60, FOLLOW_PARSER_in_actionScopeName634);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_PARSER.add(p);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(43, (Token)p));
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final optionsSpec_return optionsSpec() throws RecognitionException {
      optionsSpec_return retval = new optionsSpec_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token OPTIONS16 = null;
      Token SEMI18 = null;
      Token RCURLY19 = null;
      ParserRuleReturnScope option17 = null;
      GrammarAST OPTIONS16_tree = null;
      GrammarAST SEMI18_tree = null;
      GrammarAST RCURLY19_tree = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         OPTIONS16 = (Token)this.match(this.input, 58, FOLLOW_OPTIONS_in_optionsSpec656);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               OPTIONS16_tree = (GrammarAST)this.adaptor.create(OPTIONS16);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)OPTIONS16_tree, root_0);
            }

            int cnt11 = 0;

            while(true) {
               int alt11 = 2;
               int LA11_0 = this.input.LA(1);
               if (LA11_0 == 80 || LA11_0 == 94) {
                  alt11 = 1;
               }

               switch (alt11) {
                  case 1:
                     this.pushFollow(FOLLOW_option_in_optionsSpec660);
                     option17 = this.option(retval.opts);
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, option17.getTree());
                     }

                     SEMI18 = (Token)this.match(this.input, 82, FOLLOW_SEMI_in_optionsSpec663);
                     if (this.state.failed) {
                        return retval;
                     }

                     ++cnt11;
                     break;
                  default:
                     if (cnt11 < 1) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        EarlyExitException eee = new EarlyExitException(11, this.input);
                        throw eee;
                     }

                     RCURLY19 = (Token)this.match(this.input, 71, FOLLOW_RCURLY_in_optionsSpec668);
                     if (this.state.failed) {
                        return retval;
                     }

                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var17) {
         this.reportError(var17);
         this.recover(this.input, var17);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         return retval;
      } finally {
         ;
      }
   }

   public final option_return option(Map opts) throws RecognitionException {
      option_return retval = new option_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token ASSIGN21 = null;
      ParserRuleReturnScope id20 = null;
      ParserRuleReturnScope optionValue22 = null;
      GrammarAST ASSIGN21_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_id_in_option681);
            id20 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, id20.getTree());
            }

            ASSIGN21 = (Token)this.match(this.input, 13, FOLLOW_ASSIGN_in_option683);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               ASSIGN21_tree = (GrammarAST)this.adaptor.create(ASSIGN21);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ASSIGN21_tree, root_0);
            }

            this.pushFollow(FOLLOW_optionValue_in_option686);
            optionValue22 = this.optionValue();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, optionValue22.getTree());
            }

            if (this.state.backtracking == 0) {
               opts.put(id20 != null ? this.input.toString(id20.start, id20.stop) : null, optionValue22 != null ? ((optionValue_return)optionValue22).value : null);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var12) {
            this.reportError(var12);
            this.recover(this.input, var12);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var12);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final optionValue_return optionValue() throws RecognitionException {
      optionValue_return retval = new optionValue_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token s = null;
      Token c = null;
      Token i = null;
      Token ss = null;
      ParserRuleReturnScope x = null;
      GrammarAST s_tree = null;
      GrammarAST c_tree = null;
      GrammarAST i_tree = null;
      GrammarAST ss_tree = null;
      RewriteRuleTokenStream stream_STAR = new RewriteRuleTokenStream(this.adaptor, "token STAR");

      try {
         try {
            int alt12 = true;
            byte alt12;
            switch (this.input.LA(1)) {
               case 18:
                  alt12 = 3;
                  break;
               case 47:
                  alt12 = 4;
                  break;
               case 80:
               case 94:
                  alt12 = 1;
                  break;
               case 86:
                  alt12 = 5;
                  break;
               case 88:
                  alt12 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
                  throw nvae;
            }

            String vs;
            switch (alt12) {
               case 1:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_id_in_optionValue707);
                  x = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, x.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.value = x != null ? this.input.toString(x.start, x.stop) : null;
                  }
                  break;
               case 2:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  s = (Token)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_optionValue719);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     s_tree = (GrammarAST)this.adaptor.create(s);
                     this.adaptor.addChild(root_0, s_tree);
                  }

                  if (this.state.backtracking == 0) {
                     vs = s != null ? s.getText() : null;
                     retval.value = vs.substring(1, vs.length() - 1);
                  }
                  break;
               case 3:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  c = (Token)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_optionValue728);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     c_tree = (GrammarAST)this.adaptor.create(c);
                     this.adaptor.addChild(root_0, c_tree);
                  }

                  if (this.state.backtracking == 0) {
                     vs = c != null ? c.getText() : null;
                     retval.value = vs.substring(1, vs.length() - 1);
                  }
                  break;
               case 4:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  i = (Token)this.match(this.input, 47, FOLLOW_INT_in_optionValue739);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     i_tree = (GrammarAST)this.adaptor.create(i);
                     this.adaptor.addChild(root_0, i_tree);
                  }

                  if (this.state.backtracking == 0) {
                     retval.value = Integer.parseInt(i != null ? i.getText() : null);
                  }
                  break;
               case 5:
                  ss = (Token)this.match(this.input, 86, FOLLOW_STAR_in_optionValue759);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_STAR.add(ss);
                  }

                  if (this.state.backtracking == 0) {
                     retval.value = "*";
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(88, (Token)ss));
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final delegateGrammars_return delegateGrammars() throws RecognitionException {
      delegateGrammars_return retval = new delegateGrammars_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token string_literal23 = null;
      Token COMMA25 = null;
      Token SEMI27 = null;
      ParserRuleReturnScope delegateGrammar24 = null;
      ParserRuleReturnScope delegateGrammar26 = null;
      GrammarAST string_literal23_tree = null;
      GrammarAST COMMA25_tree = null;
      GrammarAST SEMI27_tree = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         string_literal23 = (Token)this.match(this.input, 45, FOLLOW_IMPORT_in_delegateGrammars784);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               string_literal23_tree = (GrammarAST)this.adaptor.create(string_literal23);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)string_literal23_tree, root_0);
            }

            this.pushFollow(FOLLOW_delegateGrammar_in_delegateGrammars787);
            delegateGrammar24 = this.delegateGrammar();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, delegateGrammar24.getTree());
               }

               while(true) {
                  int alt13 = 2;
                  int LA13_0 = this.input.LA(1);
                  if (LA13_0 == 24) {
                     alt13 = 1;
                  }

                  switch (alt13) {
                     case 1:
                        COMMA25 = (Token)this.match(this.input, 24, FOLLOW_COMMA_in_delegateGrammars790);
                        if (this.state.failed) {
                           return retval;
                        }

                        this.pushFollow(FOLLOW_delegateGrammar_in_delegateGrammars793);
                        delegateGrammar26 = this.delegateGrammar();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, delegateGrammar26.getTree());
                        }
                        break;
                     default:
                        SEMI27 = (Token)this.match(this.input, 82, FOLLOW_SEMI_in_delegateGrammars797);
                        if (this.state.failed) {
                           return retval;
                        }

                        retval.stop = this.input.LT(-1);
                        if (this.state.backtracking == 0) {
                           retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var17) {
         this.reportError(var17);
         this.recover(this.input, var17);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         return retval;
      } finally {
         ;
      }
   }

   public final delegateGrammar_return delegateGrammar() throws RecognitionException {
      delegateGrammar_return retval = new delegateGrammar_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token ASSIGN28 = null;
      ParserRuleReturnScope lab = null;
      ParserRuleReturnScope g = null;
      ParserRuleReturnScope g2 = null;
      GrammarAST ASSIGN28_tree = null;

      try {
         try {
            int alt14 = true;
            int LA14_0 = this.input.LA(1);
            byte alt14;
            int LA14_2;
            int nvaeMark;
            NoViableAltException nvae;
            if (LA14_0 == 94) {
               LA14_2 = this.input.LA(2);
               if (LA14_2 == 13) {
                  alt14 = 1;
               } else {
                  if (LA14_2 != 24 && LA14_2 != 82) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 14, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt14 = 2;
               }
            } else {
               if (LA14_0 != 80) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
                  throw nvae;
               }

               LA14_2 = this.input.LA(2);
               if (LA14_2 == 13) {
                  alt14 = 1;
               } else {
                  if (LA14_2 != 24 && LA14_2 != 82) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 14, 2, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt14 = 2;
               }
            }

            switch (alt14) {
               case 1:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_id_in_delegateGrammar811);
                  lab = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, lab.getTree());
                  }

                  ASSIGN28 = (Token)this.match(this.input, 13, FOLLOW_ASSIGN_in_delegateGrammar813);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ASSIGN28_tree = (GrammarAST)this.adaptor.create(ASSIGN28);
                     root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ASSIGN28_tree, root_0);
                  }

                  this.pushFollow(FOLLOW_id_in_delegateGrammar818);
                  g = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, g.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     this.grammar.importGrammar(g != null ? (GrammarAST)g.getTree() : null, lab != null ? this.input.toString(lab.start, lab.stop) : null);
                  }
                  break;
               case 2:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_id_in_delegateGrammar827);
                  g2 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, g2.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     this.grammar.importGrammar(g2 != null ? (GrammarAST)g2.getTree() : null, (String)null);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var30) {
            this.reportError(var30);
            this.recover(this.input, var30);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var30);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final tokensSpec_return tokensSpec() throws RecognitionException {
      tokensSpec_return retval = new tokensSpec_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token TOKENS29 = null;
      Token RCURLY31 = null;
      ParserRuleReturnScope tokenSpec30 = null;
      GrammarAST TOKENS29_tree = null;
      GrammarAST RCURLY31_tree = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         TOKENS29 = (Token)this.match(this.input, 93, FOLLOW_TOKENS_in_tokensSpec854);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               TOKENS29_tree = (GrammarAST)this.adaptor.create(TOKENS29);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)TOKENS29_tree, root_0);
            }

            while(true) {
               int alt15 = 2;
               int LA15_0 = this.input.LA(1);
               if (LA15_0 == 94) {
                  alt15 = 1;
               }

               switch (alt15) {
                  case 1:
                     this.pushFollow(FOLLOW_tokenSpec_in_tokensSpec860);
                     tokenSpec30 = this.tokenSpec();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, tokenSpec30.getTree());
                     }
                     break;
                  default:
                     RCURLY31 = (Token)this.match(this.input, 71, FOLLOW_RCURLY_in_tokensSpec865);
                     if (this.state.failed) {
                        return retval;
                     }

                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var14) {
         this.reportError(var14);
         this.recover(this.input, var14);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var14);
         return retval;
      } finally {
         ;
      }
   }

   public final tokenSpec_return tokenSpec() throws RecognitionException {
      tokenSpec_return retval = new tokenSpec_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token TOKEN_REF32 = null;
      Token ASSIGN33 = null;
      Token set34 = null;
      Token SEMI35 = null;
      GrammarAST TOKEN_REF32_tree = null;
      GrammarAST ASSIGN33_tree = null;
      GrammarAST set34_tree = null;
      GrammarAST SEMI35_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            TOKEN_REF32 = (Token)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_tokenSpec877);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               TOKEN_REF32_tree = (GrammarAST)this.adaptor.create(TOKEN_REF32);
               this.adaptor.addChild(root_0, TOKEN_REF32_tree);
            }

            int alt16 = 2;
            int LA16_0 = this.input.LA(1);
            if (LA16_0 == 13) {
               alt16 = 1;
            }

            switch (alt16) {
               case 1:
                  ASSIGN33 = (Token)this.match(this.input, 13, FOLLOW_ASSIGN_in_tokenSpec881);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ASSIGN33_tree = (GrammarAST)this.adaptor.create(ASSIGN33);
                     root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ASSIGN33_tree, root_0);
                  }

                  set34 = this.input.LT(1);
                  if (this.input.LA(1) != 18 && this.input.LA(1) != 88) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }

                  this.input.consume();
                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(set34));
                  }

                  this.state.errorRecovery = false;
                  this.state.failed = false;
               default:
                  SEMI35 = (Token)this.match(this.input, 82, FOLLOW_SEMI_in_tokenSpec893);
                  if (this.state.failed) {
                     return retval;
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }
            }
         } catch (RecognitionException var17) {
            this.reportError(var17);
            this.recover(this.input, var17);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final attrScopes_return attrScopes() throws RecognitionException {
      attrScopes_return retval = new attrScopes_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      ParserRuleReturnScope attrScope36 = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();

         while(true) {
            int alt17 = 2;
            int LA17_0 = this.input.LA(1);
            if (LA17_0 == 81) {
               alt17 = 1;
            }

            switch (alt17) {
               case 1:
                  this.pushFollow(FOLLOW_attrScope_in_attrScopes906);
                  attrScope36 = this.attrScope();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, attrScope36.getTree());
                  }
                  break;
               default:
                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  return retval;
            }
         }
      } catch (RecognitionException var10) {
         this.reportError(var10);
         this.recover(this.input, var10);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var10);
         return retval;
      } finally {
         ;
      }
   }

   public final attrScope_return attrScope() throws RecognitionException {
      attrScope_return retval = new attrScope_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token string_literal37 = null;
      Token ACTION40 = null;
      ParserRuleReturnScope id38 = null;
      ParserRuleReturnScope ruleActions39 = null;
      GrammarAST string_literal37_tree = null;
      GrammarAST ACTION40_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            string_literal37 = (Token)this.match(this.input, 81, FOLLOW_SCOPE_in_attrScope919);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               string_literal37_tree = (GrammarAST)this.adaptor.create(string_literal37);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)string_literal37_tree, root_0);
            }

            this.pushFollow(FOLLOW_id_in_attrScope922);
            id38 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, id38.getTree());
            }

            int alt18 = 2;
            int LA18_0 = this.input.LA(1);
            if (LA18_0 == 9) {
               alt18 = 1;
            }

            switch (alt18) {
               case 1:
                  this.pushFollow(FOLLOW_ruleActions_in_attrScope924);
                  ruleActions39 = this.ruleActions();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, ruleActions39.getTree());
                  }
               default:
                  ACTION40 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_attrScope927);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION40_tree = (GrammarAST)this.adaptor.create(ACTION40);
                     this.adaptor.addChild(root_0, ACTION40_tree);
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rules_return rules() throws RecognitionException {
      rules_return retval = new rules_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      ParserRuleReturnScope rule41 = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         int cnt19 = 0;

         while(true) {
            int alt19 = 2;
            int LA19_0 = this.input.LA(1);
            if (LA19_0 == 27 || LA19_0 == 40 || LA19_0 >= 66 && LA19_0 <= 68 || LA19_0 == 80 || LA19_0 == 94) {
               alt19 = 1;
            }

            switch (alt19) {
               case 1:
                  this.pushFollow(FOLLOW_rule_in_rules940);
                  rule41 = this.rule();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rule41.getTree());
                  }

                  ++cnt19;
                  break;
               default:
                  if (cnt19 < 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     EarlyExitException eee = new EarlyExitException(19, this.input);
                     throw eee;
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  return retval;
            }
         }
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(this.input, var11);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var11);
         return retval;
      } finally {
         ;
      }
   }

   public final rule_return rule() throws RecognitionException {
      rule_return retval = new rule_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token d = null;
      Token p1 = null;
      Token p2 = null;
      Token p3 = null;
      Token p4 = null;
      Token aa = null;
      Token rt = null;
      Token BANG42 = null;
      Token string_literal43 = null;
      Token COLON47 = null;
      Token SEMI49 = null;
      ParserRuleReturnScope ruleName = null;
      ParserRuleReturnScope scopes = null;
      ParserRuleReturnScope ex = null;
      ParserRuleReturnScope throwsSpec44 = null;
      ParserRuleReturnScope optionsSpec45 = null;
      ParserRuleReturnScope ruleActions46 = null;
      ParserRuleReturnScope ruleAltList48 = null;
      GrammarAST d_tree = null;
      GrammarAST p1_tree = null;
      GrammarAST p2_tree = null;
      GrammarAST p3_tree = null;
      GrammarAST p4_tree = null;
      GrammarAST aa_tree = null;
      GrammarAST rt_tree = null;
      GrammarAST BANG42_tree = null;
      GrammarAST string_literal43_tree = null;
      GrammarAST COLON47_tree = null;
      GrammarAST SEMI49_tree = null;
      RewriteRuleTokenStream stream_DOC_COMMENT = new RewriteRuleTokenStream(this.adaptor, "token DOC_COMMENT");
      RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
      RewriteRuleTokenStream stream_PROTECTED = new RewriteRuleTokenStream(this.adaptor, "token PROTECTED");
      RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
      RewriteRuleTokenStream stream_PUBLIC = new RewriteRuleTokenStream(this.adaptor, "token PUBLIC");
      RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
      RewriteRuleTokenStream stream_PRIVATE = new RewriteRuleTokenStream(this.adaptor, "token PRIVATE");
      RewriteRuleTokenStream stream_FRAGMENT = new RewriteRuleTokenStream(this.adaptor, "token FRAGMENT");
      RewriteRuleTokenStream stream_RETURNS = new RewriteRuleTokenStream(this.adaptor, "token RETURNS");
      RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_ruleAltList = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleAltList");
      RewriteRuleSubtreeStream stream_exceptionGroup = new RewriteRuleSubtreeStream(this.adaptor, "rule exceptionGroup");
      RewriteRuleSubtreeStream stream_throwsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule throwsSpec");
      RewriteRuleSubtreeStream stream_ruleScopeSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleScopeSpec");
      RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
      RewriteRuleSubtreeStream stream_ruleActions = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleActions");
      GrammarAST eob = null;
      CommonToken start = (CommonToken)this.LT(1);
      int startLine = this.LT(1).getLine();

      try {
         try {
            int alt20 = 2;
            int LA20_0 = this.input.LA(1);
            if (LA20_0 == 27) {
               alt20 = 1;
            }

            switch (alt20) {
               case 1:
                  d = (Token)this.match(this.input, 27, FOLLOW_DOC_COMMENT_in_rule970);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_DOC_COMMENT.add(d);
                  }
               default:
                  int alt21 = 5;
                  switch (this.input.LA(1)) {
                     case 40:
                        alt21 = 4;
                        break;
                     case 66:
                        alt21 = 3;
                        break;
                     case 67:
                        alt21 = 1;
                        break;
                     case 68:
                        alt21 = 2;
                  }

                  switch (alt21) {
                     case 1:
                        p1 = (Token)this.match(this.input, 67, FOLLOW_PROTECTED_in_rule983);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_PROTECTED.add(p1);
                        }
                        break;
                     case 2:
                        p2 = (Token)this.match(this.input, 68, FOLLOW_PUBLIC_in_rule992);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_PUBLIC.add(p2);
                        }
                        break;
                     case 3:
                        p3 = (Token)this.match(this.input, 66, FOLLOW_PRIVATE_in_rule1002);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_PRIVATE.add(p3);
                        }
                        break;
                     case 4:
                        p4 = (Token)this.match(this.input, 40, FOLLOW_FRAGMENT_in_rule1011);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_FRAGMENT.add(p4);
                        }
                  }

                  this.pushFollow(FOLLOW_id_in_rule1023);
                  ruleName = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(ruleName.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     this.currentRuleName = ruleName != null ? this.input.toString(ruleName.start, ruleName.stop) : null;
                     if (this.grammarType == 1 && p4 == null) {
                        this.grammar.lexerRuleNamesInCombined.add(this.currentRuleName);
                     }
                  }

                  int alt22 = 2;
                  int LA22_0 = this.input.LA(1);
                  if (LA22_0 == 15) {
                     alt22 = 1;
                  }

                  switch (alt22) {
                     case 1:
                        BANG42 = (Token)this.match(this.input, 15, FOLLOW_BANG_in_rule1033);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_BANG.add(BANG42);
                        }
                     default:
                        int alt23 = 2;
                        int LA23_0 = this.input.LA(1);
                        if (LA23_0 == 12) {
                           alt23 = 1;
                        }

                        switch (alt23) {
                           case 1:
                              aa = (Token)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rule1044);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_ARG_ACTION.add(aa);
                              }
                           default:
                              int alt24 = 2;
                              int LA24_0 = this.input.LA(1);
                              if (LA24_0 == 74) {
                                 alt24 = 1;
                              }

                              switch (alt24) {
                                 case 1:
                                    string_literal43 = (Token)this.match(this.input, 74, FOLLOW_RETURNS_in_rule1053);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       stream_RETURNS.add(string_literal43);
                                    }

                                    rt = (Token)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rule1057);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       stream_ARG_ACTION.add(rt);
                                    }
                                 default:
                                    int alt25 = 2;
                                    int LA25_0 = this.input.LA(1);
                                    if (LA25_0 == 92) {
                                       alt25 = 1;
                                    }

                                    switch (alt25) {
                                       case 1:
                                          this.pushFollow(FOLLOW_throwsSpec_in_rule1067);
                                          throwsSpec44 = this.throwsSpec();
                                          --this.state._fsp;
                                          if (this.state.failed) {
                                             return retval;
                                          }

                                          if (this.state.backtracking == 0) {
                                             stream_throwsSpec.add(throwsSpec44.getTree());
                                          }
                                       default:
                                          int alt26 = 2;
                                          int LA26_0 = this.input.LA(1);
                                          if (LA26_0 == 58) {
                                             alt26 = 1;
                                          }

                                          switch (alt26) {
                                             case 1:
                                                this.pushFollow(FOLLOW_optionsSpec_in_rule1076);
                                                optionsSpec45 = this.optionsSpec();
                                                --this.state._fsp;
                                                if (this.state.failed) {
                                                   return retval;
                                                }

                                                if (this.state.backtracking == 0) {
                                                   stream_optionsSpec.add(optionsSpec45.getTree());
                                                }
                                             default:
                                                this.pushFollow(FOLLOW_ruleScopeSpec_in_rule1085);
                                                scopes = this.ruleScopeSpec();
                                                --this.state._fsp;
                                                if (this.state.failed) {
                                                   return retval;
                                                }

                                                if (this.state.backtracking == 0) {
                                                   stream_ruleScopeSpec.add(scopes.getTree());
                                                }

                                                int alt27 = 2;
                                                int LA27_0 = this.input.LA(1);
                                                if (LA27_0 == 9) {
                                                   alt27 = 1;
                                                }

                                                switch (alt27) {
                                                   case 1:
                                                      this.pushFollow(FOLLOW_ruleActions_in_rule1090);
                                                      ruleActions46 = this.ruleActions();
                                                      --this.state._fsp;
                                                      if (this.state.failed) {
                                                         return retval;
                                                      }

                                                      if (this.state.backtracking == 0) {
                                                         stream_ruleActions.add(ruleActions46.getTree());
                                                      }
                                                   default:
                                                      COLON47 = (Token)this.match(this.input, 22, FOLLOW_COLON_in_rule1096);
                                                      if (this.state.failed) {
                                                         return retval;
                                                      }

                                                      if (this.state.backtracking == 0) {
                                                         stream_COLON.add(COLON47);
                                                      }

                                                      this.pushFollow(FOLLOW_ruleAltList_in_rule1100);
                                                      ruleAltList48 = this.ruleAltList(optionsSpec45 != null ? ((optionsSpec_return)optionsSpec45).opts : null);
                                                      --this.state._fsp;
                                                      if (this.state.failed) {
                                                         return retval;
                                                      }

                                                      if (this.state.backtracking == 0) {
                                                         stream_ruleAltList.add(ruleAltList48.getTree());
                                                      }

                                                      SEMI49 = (Token)this.match(this.input, 82, FOLLOW_SEMI_in_rule1105);
                                                      if (this.state.failed) {
                                                         return retval;
                                                      }

                                                      if (this.state.backtracking == 0) {
                                                         stream_SEMI.add(SEMI49);
                                                      }

                                                      int alt28 = 2;
                                                      int LA28_0 = this.input.LA(1);
                                                      if (LA28_0 == 17 || LA28_0 == 38) {
                                                         alt28 = 1;
                                                      }

                                                      switch (alt28) {
                                                         case 1:
                                                            this.pushFollow(FOLLOW_exceptionGroup_in_rule1113);
                                                            ex = this.exceptionGroup();
                                                            --this.state._fsp;
                                                            if (this.state.failed) {
                                                               return retval;
                                                            }

                                                            if (this.state.backtracking == 0) {
                                                               stream_exceptionGroup.add(ex.getTree());
                                                            }
                                                         default:
                                                            if (this.state.backtracking == 0) {
                                                               retval.tree = root_0;
                                                               RewriteRuleTokenStream stream_p4 = new RewriteRuleTokenStream(this.adaptor, "token p4", p4);
                                                               RewriteRuleTokenStream stream_p3 = new RewriteRuleTokenStream(this.adaptor, "token p3", p3);
                                                               RewriteRuleTokenStream stream_p2 = new RewriteRuleTokenStream(this.adaptor, "token p2", p2);
                                                               RewriteRuleTokenStream stream_p1 = new RewriteRuleTokenStream(this.adaptor, "token p1", p1);
                                                               RewriteRuleTokenStream stream_rt = new RewriteRuleTokenStream(this.adaptor, "token rt", rt);
                                                               RewriteRuleTokenStream stream_aa = new RewriteRuleTokenStream(this.adaptor, "token aa", aa);
                                                               RewriteRuleSubtreeStream stream_ex = new RewriteRuleSubtreeStream(this.adaptor, "rule ex", ex != null ? ex.getTree() : null);
                                                               RewriteRuleSubtreeStream stream_scopes = new RewriteRuleSubtreeStream(this.adaptor, "rule scopes", scopes != null ? scopes.getTree() : null);
                                                               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                                                               RewriteRuleSubtreeStream stream_ruleName = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleName", ruleName != null ? ruleName.getTree() : null);
                                                               root_0 = (GrammarAST)this.adaptor.nil();
                                                               GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                                                               root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(79, ruleName != null ? ruleName.start : null, "rule")), root_1);
                                                               this.adaptor.addChild(root_1, stream_ruleName.nextTree());
                                                               if (stream_p1.hasNext()) {
                                                                  this.adaptor.addChild(root_1, stream_p1.nextNode());
                                                               }

                                                               stream_p1.reset();
                                                               if (stream_p2.hasNext()) {
                                                                  this.adaptor.addChild(root_1, stream_p2.nextNode());
                                                               }

                                                               stream_p2.reset();
                                                               if (stream_p3.hasNext()) {
                                                                  this.adaptor.addChild(root_1, stream_p3.nextNode());
                                                               }

                                                               stream_p3.reset();
                                                               if (stream_p4.hasNext()) {
                                                                  this.adaptor.addChild(root_1, stream_p4.nextNode());
                                                               }

                                                               stream_p4.reset();
                                                               GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
                                                               root_2 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(10, (String)"ARG")), root_2);
                                                               if (stream_aa.hasNext()) {
                                                                  this.adaptor.addChild(root_2, stream_aa.nextNode());
                                                               }

                                                               stream_aa.reset();
                                                               this.adaptor.addChild(root_1, root_2);
                                                               root_2 = (GrammarAST)this.adaptor.nil();
                                                               root_2 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(73, (String)"RET")), root_2);
                                                               if (stream_rt.hasNext()) {
                                                                  this.adaptor.addChild(root_2, stream_rt.nextNode());
                                                               }

                                                               stream_rt.reset();
                                                               this.adaptor.addChild(root_1, root_2);
                                                               if (stream_throwsSpec.hasNext()) {
                                                                  this.adaptor.addChild(root_1, stream_throwsSpec.nextTree());
                                                               }

                                                               stream_throwsSpec.reset();
                                                               if (stream_optionsSpec.hasNext()) {
                                                                  this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
                                                               }

                                                               stream_optionsSpec.reset();
                                                               this.adaptor.addChild(root_1, stream_scopes.nextTree());
                                                               if (stream_ruleActions.hasNext()) {
                                                                  this.adaptor.addChild(root_1, stream_ruleActions.nextTree());
                                                               }

                                                               stream_ruleActions.reset();
                                                               this.adaptor.addChild(root_1, stream_ruleAltList.nextTree());
                                                               if (stream_ex.hasNext()) {
                                                                  this.adaptor.addChild(root_1, stream_ex.nextTree());
                                                               }

                                                               stream_ex.reset();
                                                               this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(34, SEMI49, "<end-of-rule>"));
                                                               this.adaptor.addChild(root_0, root_1);
                                                               retval.tree = root_0;
                                                            }

                                                            if (this.state.backtracking == 0) {
                                                               retval.tree.setTreeEnclosingRuleNameDeeply(this.currentRuleName);
                                                               ((GrammarAST)retval.tree.getChild(0)).setBlockOptions(optionsSpec45 != null ? ((optionsSpec_return)optionsSpec45).opts : null);
                                                            }

                                                            retval.stop = this.input.LT(-1);
                                                            if (this.state.backtracking == 0) {
                                                               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                                                               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                                                            }
                                                      }
                                                }
                                          }
                                    }
                              }
                        }
                  }
            }
         } catch (RecognitionException var84) {
            this.reportError(var84);
            this.recover(this.input, var84);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var84);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final ruleActions_return ruleActions() throws RecognitionException {
      ruleActions_return retval = new ruleActions_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      ParserRuleReturnScope ruleAction50 = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         int cnt29 = 0;

         while(true) {
            int alt29 = 2;
            int LA29_0 = this.input.LA(1);
            if (LA29_0 == 9) {
               alt29 = 1;
            }

            switch (alt29) {
               case 1:
                  this.pushFollow(FOLLOW_ruleAction_in_ruleActions1251);
                  ruleAction50 = this.ruleAction();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, ruleAction50.getTree());
                  }

                  ++cnt29;
                  break;
               default:
                  if (cnt29 < 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     EarlyExitException eee = new EarlyExitException(29, this.input);
                     throw eee;
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  return retval;
            }
         }
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(this.input, var11);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var11);
         return retval;
      } finally {
         ;
      }
   }

   public final ruleAction_return ruleAction() throws RecognitionException {
      ruleAction_return retval = new ruleAction_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token AMPERSAND51 = null;
      Token ACTION53 = null;
      ParserRuleReturnScope id52 = null;
      GrammarAST AMPERSAND51_tree = null;
      GrammarAST ACTION53_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            AMPERSAND51 = (Token)this.match(this.input, 9, FOLLOW_AMPERSAND_in_ruleAction1266);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               AMPERSAND51_tree = (GrammarAST)this.adaptor.create(AMPERSAND51);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)AMPERSAND51_tree, root_0);
            }

            this.pushFollow(FOLLOW_id_in_ruleAction1269);
            id52 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, id52.getTree());
            }

            ACTION53 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_ruleAction1271);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               ACTION53_tree = (GrammarAST)this.adaptor.create(ACTION53);
               this.adaptor.addChild(root_0, ACTION53_tree);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var12) {
            this.reportError(var12);
            this.recover(this.input, var12);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var12);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final throwsSpec_return throwsSpec() throws RecognitionException {
      throwsSpec_return retval = new throwsSpec_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token string_literal54 = null;
      Token COMMA56 = null;
      ParserRuleReturnScope id55 = null;
      ParserRuleReturnScope id57 = null;
      GrammarAST string_literal54_tree = null;
      GrammarAST COMMA56_tree = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         string_literal54 = (Token)this.match(this.input, 92, FOLLOW_THROWS_in_throwsSpec1282);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               string_literal54_tree = (GrammarAST)this.adaptor.create(string_literal54);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)string_literal54_tree, root_0);
            }

            this.pushFollow(FOLLOW_id_in_throwsSpec1285);
            id55 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, id55.getTree());
               }

               while(true) {
                  int alt30 = 2;
                  int LA30_0 = this.input.LA(1);
                  if (LA30_0 == 24) {
                     alt30 = 1;
                  }

                  switch (alt30) {
                     case 1:
                        COMMA56 = (Token)this.match(this.input, 24, FOLLOW_COMMA_in_throwsSpec1289);
                        if (this.state.failed) {
                           return retval;
                        }

                        this.pushFollow(FOLLOW_id_in_throwsSpec1292);
                        id57 = this.id();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, id57.getTree());
                        }
                        break;
                     default:
                        retval.stop = this.input.LT(-1);
                        if (this.state.backtracking == 0) {
                           retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var15) {
         this.reportError(var15);
         this.recover(this.input, var15);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         return retval;
      } finally {
         ;
      }
   }

   public final ruleScopeSpec_return ruleScopeSpec() throws RecognitionException {
      ruleScopeSpec_return retval = new ruleScopeSpec_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token string_literal58 = null;
      Token ACTION60 = null;
      Token string_literal61 = null;
      Token SEMI63 = null;
      ParserRuleReturnScope ruleActions59 = null;
      ParserRuleReturnScope idList62 = null;
      GrammarAST string_literal58_tree = null;
      GrammarAST ACTION60_tree = null;
      GrammarAST string_literal61_tree = null;
      GrammarAST SEMI63_tree = null;
      RewriteRuleTokenStream stream_SCOPE = new RewriteRuleTokenStream(this.adaptor, "token SCOPE");
      RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleSubtreeStream stream_ruleActions = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleActions");
      RewriteRuleSubtreeStream stream_idList = new RewriteRuleSubtreeStream(this.adaptor, "rule idList");

      try {
         int alt32 = 2;
         int LA32_0 = this.input.LA(1);
         if (LA32_0 == 81) {
            int LA32_1 = this.input.LA(2);
            if (LA32_1 == 4 || LA32_1 == 9) {
               alt32 = 1;
            }
         }

         int LA31_0;
         byte alt31;
         switch (alt32) {
            case 1:
               string_literal58 = (Token)this.match(this.input, 81, FOLLOW_SCOPE_in_ruleScopeSpec1308);
               if (this.state.failed) {
                  return retval;
               }

               if (this.state.backtracking == 0) {
                  stream_SCOPE.add(string_literal58);
               }

               alt31 = 2;
               LA31_0 = this.input.LA(1);
               if (LA31_0 == 9) {
                  alt31 = 1;
               }

               switch (alt31) {
                  case 1:
                     this.pushFollow(FOLLOW_ruleActions_in_ruleScopeSpec1310);
                     ruleActions59 = this.ruleActions();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_ruleActions.add(ruleActions59.getTree());
                     }
                  default:
                     ACTION60 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec1313);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_ACTION.add(ACTION60);
                     }
               }
         }

         while(true) {
            alt31 = 2;
            LA31_0 = this.input.LA(1);
            if (LA31_0 == 81) {
               alt31 = 1;
            }

            switch (alt31) {
               case 1:
                  string_literal61 = (Token)this.match(this.input, 81, FOLLOW_SCOPE_in_ruleScopeSpec1322);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_SCOPE.add(string_literal61);
                  }

                  this.pushFollow(FOLLOW_idList_in_ruleScopeSpec1324);
                  idList62 = this.idList();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_idList.add(idList62.getTree());
                  }

                  SEMI63 = (Token)this.match(this.input, 82, FOLLOW_SEMI_in_ruleScopeSpec1326);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_SEMI.add(SEMI63);
                  }
                  break;
               default:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(81, retval.start, "scope")), root_1);
                     if (stream_ruleActions.hasNext()) {
                        this.adaptor.addChild(root_1, stream_ruleActions.nextTree());
                     }

                     stream_ruleActions.reset();
                     if (stream_ACTION.hasNext()) {
                        this.adaptor.addChild(root_1, stream_ACTION.nextNode());
                     }

                     stream_ACTION.reset();

                     while(stream_idList.hasNext()) {
                        this.adaptor.addChild(root_1, stream_idList.nextTree());
                     }

                     stream_idList.reset();
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  return retval;
            }
         }
      } catch (RecognitionException var26) {
         this.reportError(var26);
         this.recover(this.input, var26);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var26);
         return retval;
      } finally {
         ;
      }
   }

   public final ruleAltList_return ruleAltList(Map opts) throws RecognitionException {
      ruleAltList_return retval = new ruleAltList_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token OR64 = null;
      ParserRuleReturnScope a1 = null;
      ParserRuleReturnScope r1 = null;
      ParserRuleReturnScope a2 = null;
      ParserRuleReturnScope r2 = null;
      GrammarAST OR64_tree = null;
      RewriteRuleTokenStream stream_OR = new RewriteRuleTokenStream(this.adaptor, "token OR");
      RewriteRuleSubtreeStream stream_rewrite = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite");
      RewriteRuleSubtreeStream stream_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule alternative");
      GrammarAST blkRoot = null;
      GrammarAST save = this.currentBlockAST;

      try {
         if (this.state.backtracking == 0) {
            retval.tree = root_0;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(16, this.input.LT(-1), "BLOCK"));
            retval.tree = root_0;
         }

         if (this.state.backtracking == 0) {
            blkRoot = (GrammarAST)retval.tree.getChild(0);
            blkRoot.setBlockOptions(opts);
            this.currentBlockAST = blkRoot;
         }

         this.pushFollow(FOLLOW_alternative_in_ruleAltList1383);
         a1 = this.alternative();
         --this.state._fsp;
         ruleAltList_return var29;
         if (this.state.failed) {
            var29 = retval;
            return var29;
         }

         if (this.state.backtracking == 0) {
            stream_alternative.add(a1.getTree());
         }

         this.pushFollow(FOLLOW_rewrite_in_ruleAltList1387);
         r1 = this.rewrite();
         --this.state._fsp;
         if (this.state.failed) {
            var29 = retval;
            return var29;
         }

         if (this.state.backtracking == 0) {
            stream_rewrite.add(r1.getTree());
         }

         if (this.state.backtracking == 0 && (this.LA(1) == 59 || this.LA(2) == 69 || this.LA(2) == 62 || this.LA(2) == 86)) {
            this.prefixWithSynPred(a1 != null ? (GrammarAST)a1.getTree() : null);
         }

         RewriteRuleSubtreeStream stream_retval;
         if (this.state.backtracking == 0) {
            retval.tree = root_0;
            new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            RewriteRuleSubtreeStream stream_a1 = new RewriteRuleSubtreeStream(this.adaptor, "rule a1", a1 != null ? a1.getTree() : null);
            stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule r1", r1 != null ? r1.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            this.adaptor.addChild(root_0, stream_a1.nextTree());
            if (stream_retval.hasNext()) {
               this.adaptor.addChild(root_0, stream_retval.nextTree());
            }

            stream_retval.reset();
            retval.tree = root_0;
         }

         int alt35 = true;
         int LA35_0 = this.input.LA(1);
         byte alt35;
         if (LA35_0 == 59) {
            alt35 = 1;
         } else {
            if (LA35_0 != 82) {
               if (this.state.backtracking <= 0) {
                  NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
                  throw nvae;
               }

               this.state.failed = true;
               ruleAltList_return var32 = retval;
               return var32;
            }

            alt35 = 2;
         }

         switch (alt35) {
            case 1:
               int cnt34 = 0;

               label484:
               while(true) {
                  int alt34 = 2;
                  int LA34_0 = this.input.LA(1);
                  if (LA34_0 == 59) {
                     alt34 = 1;
                  }

                  ruleAltList_return var35;
                  switch (alt34) {
                     case 1:
                        OR64 = (Token)this.match(this.input, 59, FOLLOW_OR_in_ruleAltList1416);
                        if (this.state.failed) {
                           var35 = retval;
                           return var35;
                        }

                        if (this.state.backtracking == 0) {
                           stream_OR.add(OR64);
                        }

                        this.pushFollow(FOLLOW_alternative_in_ruleAltList1420);
                        a2 = this.alternative();
                        --this.state._fsp;
                        if (this.state.failed) {
                           var35 = retval;
                           return var35;
                        }

                        if (this.state.backtracking == 0) {
                           stream_alternative.add(a2.getTree());
                        }

                        this.pushFollow(FOLLOW_rewrite_in_ruleAltList1424);
                        r2 = this.rewrite();
                        --this.state._fsp;
                        if (this.state.failed) {
                           var35 = retval;
                           return var35;
                        }

                        if (this.state.backtracking == 0) {
                           stream_rewrite.add(r2.getTree());
                        }

                        if (this.state.backtracking == 0 && (this.LA(1) == 59 || this.LA(2) == 69 || this.LA(2) == 62 || this.LA(2) == 86)) {
                           this.prefixWithSynPred(a2 != null ? (GrammarAST)a2.getTree() : null);
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           RewriteRuleSubtreeStream stream_a2 = new RewriteRuleSubtreeStream(this.adaptor, "rule a2", a2 != null ? a2.getTree() : null);
                           RewriteRuleSubtreeStream stream_r2 = new RewriteRuleSubtreeStream(this.adaptor, "rule r2", r2 != null ? r2.getTree() : null);
                           root_0 = (GrammarAST)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_retval.nextTree());
                           this.adaptor.addChild(root_0, stream_a2.nextTree());
                           if (stream_r2.hasNext()) {
                              this.adaptor.addChild(root_0, stream_r2.nextTree());
                           }

                           stream_r2.reset();
                           retval.tree = root_0;
                        }

                        ++cnt34;
                        break;
                     default:
                        if (cnt34 < 1) {
                           if (this.state.backtracking <= 0) {
                              EarlyExitException eee = new EarlyExitException(34, this.input);
                              throw eee;
                           }

                           this.state.failed = true;
                           var35 = retval;
                           return var35;
                        }
                        break label484;
                  }
               }
            case 2:
         }

         if (this.state.backtracking == 0) {
            retval.tree = root_0;
            stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
            root_0 = (GrammarAST)this.adaptor.nil();
            GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
            root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)blkRoot, root_1);
            this.adaptor.addChild(root_1, stream_retval.nextTree());
            this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(33, (String)"<end-of-block>"));
            this.adaptor.addChild(root_0, root_1);
            retval.tree = root_0;
         }

         retval.stop = this.input.LT(-1);
         if (this.state.backtracking == 0) {
            retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         }
      } catch (RecognitionException var26) {
         this.reportError(var26);
         this.recover(this.input, var26);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var26);
      } finally {
         this.currentBlockAST = save;
      }

      return retval;
   }

   public final block_return block() throws RecognitionException {
      block_return retval = new block_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token lp = null;
      Token rp = null;
      Token COLON67 = null;
      Token ACTION68 = null;
      Token COLON69 = null;
      Token OR70 = null;
      ParserRuleReturnScope a = null;
      ParserRuleReturnScope r = null;
      ParserRuleReturnScope optionsSpec65 = null;
      ParserRuleReturnScope ruleActions66 = null;
      GrammarAST lp_tree = null;
      GrammarAST rp_tree = null;
      GrammarAST COLON67_tree = null;
      GrammarAST ACTION68_tree = null;
      GrammarAST COLON69_tree = null;
      GrammarAST OR70_tree = null;
      RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleTokenStream stream_OR = new RewriteRuleTokenStream(this.adaptor, "token OR");
      RewriteRuleSubtreeStream stream_rewrite = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite");
      RewriteRuleSubtreeStream stream_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule alternative");
      RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
      RewriteRuleSubtreeStream stream_ruleActions = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleActions");
      GrammarAST save = this.currentBlockAST;

      try {
         lp = (Token)this.match(this.input, 51, FOLLOW_LPAREN_in_block1500);
         if (this.state.failed) {
            block_return var41 = retval;
            return var41;
         } else {
            if (this.state.backtracking == 0) {
               stream_LPAREN.add(lp);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (GrammarAST)this.adaptor.nil();
               this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(16, lp, "BLOCK"));
               retval.tree = root_0;
            }

            if (this.state.backtracking == 0) {
               this.currentBlockAST = (GrammarAST)retval.tree.getChild(0);
            }

            int alt38 = 3;
            int LA38_0 = this.input.LA(1);
            if (LA38_0 != 9 && LA38_0 != 22 && LA38_0 != 58) {
               if (LA38_0 == 4) {
                  int LA38_2 = this.input.LA(2);
                  if (LA38_2 == 22) {
                     alt38 = 2;
                  }
               }
            } else {
               alt38 = 1;
            }

            int LA39_0;
            block_return var33;
            block_return var42;
            byte alt39;
            label579:
            switch (alt38) {
               case 1:
                  alt39 = 2;
                  LA39_0 = this.input.LA(1);
                  if (LA39_0 == 58) {
                     alt39 = 1;
                  }

                  switch (alt39) {
                     case 1:
                        this.pushFollow(FOLLOW_optionsSpec_in_block1538);
                        optionsSpec65 = this.optionsSpec();
                        --this.state._fsp;
                        if (this.state.failed) {
                           var33 = retval;
                           return var33;
                        }

                        if (this.state.backtracking == 0) {
                           stream_optionsSpec.add(optionsSpec65.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           ((GrammarAST)retval.tree.getChild(0)).setOptions(this.grammar, optionsSpec65 != null ? ((optionsSpec_return)optionsSpec65).opts : null);
                        }
                     default:
                        int alt37 = 2;
                        int LA37_0 = this.input.LA(1);
                        if (LA37_0 == 9) {
                           alt37 = 1;
                        }

                        block_return var35;
                        switch (alt37) {
                           case 1:
                              this.pushFollow(FOLLOW_ruleActions_in_block1549);
                              ruleActions66 = this.ruleActions();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 var35 = retval;
                                 return var35;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_ruleActions.add(ruleActions66.getTree());
                              }
                        }

                        COLON67 = (Token)this.match(this.input, 22, FOLLOW_COLON_in_block1557);
                        if (this.state.failed) {
                           var35 = retval;
                           return var35;
                        }

                        if (this.state.backtracking == 0) {
                           stream_COLON.add(COLON67);
                        }
                        break label579;
                  }
               case 2:
                  ACTION68 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_block1563);
                  if (this.state.failed) {
                     var42 = retval;
                     return var42;
                  }

                  if (this.state.backtracking == 0) {
                     stream_ACTION.add(ACTION68);
                  }

                  COLON69 = (Token)this.match(this.input, 22, FOLLOW_COLON_in_block1565);
                  if (this.state.failed) {
                     var42 = retval;
                     return var42;
                  }

                  if (this.state.backtracking == 0) {
                     stream_COLON.add(COLON69);
                  }
            }

            this.pushFollow(FOLLOW_alternative_in_block1577);
            a = this.alternative();
            --this.state._fsp;
            if (this.state.failed) {
               var42 = retval;
               return var42;
            } else {
               if (this.state.backtracking == 0) {
                  stream_alternative.add(a.getTree());
               }

               this.pushFollow(FOLLOW_rewrite_in_block1581);
               r = this.rewrite();
               --this.state._fsp;
               if (this.state.failed) {
                  var42 = retval;
                  return var42;
               } else {
                  if (this.state.backtracking == 0) {
                     stream_rewrite.add(r.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     stream_alternative.add(r != null ? (GrammarAST)r.getTree() : null);
                     if (this.LA(1) == 59 || this.LA(2) == 69 || this.LA(2) == 62 || this.LA(2) == 86) {
                        this.prefixWithSynPred(a != null ? (GrammarAST)a.getTree() : null);
                     }
                  }

                  while(true) {
                     alt39 = 2;
                     LA39_0 = this.input.LA(1);
                     if (LA39_0 == 59) {
                        alt39 = 1;
                     }

                     switch (alt39) {
                        case 1:
                           OR70 = (Token)this.match(this.input, 59, FOLLOW_OR_in_block1591);
                           if (this.state.failed) {
                              var33 = retval;
                              return var33;
                           }

                           if (this.state.backtracking == 0) {
                              stream_OR.add(OR70);
                           }

                           this.pushFollow(FOLLOW_alternative_in_block1595);
                           a = this.alternative();
                           --this.state._fsp;
                           if (this.state.failed) {
                              var33 = retval;
                              return var33;
                           }

                           if (this.state.backtracking == 0) {
                              stream_alternative.add(a.getTree());
                           }

                           this.pushFollow(FOLLOW_rewrite_in_block1599);
                           r = this.rewrite();
                           --this.state._fsp;
                           if (this.state.failed) {
                              var33 = retval;
                              return var33;
                           }

                           if (this.state.backtracking == 0) {
                              stream_rewrite.add(r.getTree());
                           }

                           if (this.state.backtracking == 0) {
                              stream_alternative.add(r != null ? (GrammarAST)r.getTree() : null);
                              if (this.LA(1) == 59 || this.LA(2) == 69 || this.LA(2) == 62 || this.LA(2) == 86) {
                                 this.prefixWithSynPred(a != null ? (GrammarAST)a.getTree() : null);
                              }
                           }
                           break;
                        default:
                           rp = (Token)this.match(this.input, 78, FOLLOW_RPAREN_in_block1616);
                           if (this.state.failed) {
                              var42 = retval;
                              return var42;
                           }

                           if (this.state.backtracking == 0) {
                              stream_RPAREN.add(rp);
                           }

                           if (this.state.backtracking == 0) {
                              retval.tree = root_0;
                              RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                              root_0 = (GrammarAST)this.adaptor.nil();
                              GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                              root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)stream_retval.nextNode(), root_1);
                              if (stream_optionsSpec.hasNext()) {
                                 this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
                              }

                              stream_optionsSpec.reset();
                              if (stream_ruleActions.hasNext()) {
                                 this.adaptor.addChild(root_1, stream_ruleActions.nextTree());
                              }

                              stream_ruleActions.reset();
                              if (stream_ACTION.hasNext()) {
                                 this.adaptor.addChild(root_1, stream_ACTION.nextNode());
                              }

                              stream_ACTION.reset();
                              if (!stream_alternative.hasNext()) {
                                 throw new RewriteEarlyExitException();
                              }

                              while(stream_alternative.hasNext()) {
                                 this.adaptor.addChild(root_1, stream_alternative.nextTree());
                              }

                              stream_alternative.reset();
                              this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(33, rp, "<end-of-block>"));
                              this.adaptor.addChild(root_0, root_1);
                              retval.tree = root_0;
                           }

                           retval.stop = this.input.LT(-1);
                           if (this.state.backtracking == 0) {
                              retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                              this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                           }

                           return retval;
                     }
                  }
               }
            }
         }
      } catch (RecognitionException var39) {
         this.reportError(var39);
         this.recover(this.input, var39);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var39);
         return retval;
      } finally {
         this.currentBlockAST = save;
      }
   }

   public final alternative_return alternative() throws RecognitionException {
      alternative_return retval = new alternative_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      ParserRuleReturnScope element71 = null;
      RewriteRuleSubtreeStream stream_element = new RewriteRuleSubtreeStream(this.adaptor, "rule element");

      try {
         try {
            int alt41 = true;
            int LA41_0 = this.input.LA(1);
            byte alt41;
            if (LA41_0 != 4 && LA41_0 != 18 && LA41_0 != 39 && LA41_0 != 51 && LA41_0 != 55 && LA41_0 != 80 && LA41_0 != 83 && LA41_0 != 88 && LA41_0 != 94 && LA41_0 != 96 && LA41_0 != 98) {
               if (LA41_0 != 59 && LA41_0 != 75 && LA41_0 != 78 && LA41_0 != 82) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 41, 0, this.input);
                  throw nvae;
               }

               alt41 = 2;
            } else {
               alt41 = 1;
            }

            label251:
            switch (alt41) {
               case 1:
                  int cnt40 = 0;

                  while(true) {
                     int alt40 = 2;
                     int LA40_0 = this.input.LA(1);
                     if (LA40_0 == 4 || LA40_0 == 18 || LA40_0 == 39 || LA40_0 == 51 || LA40_0 == 55 || LA40_0 == 80 || LA40_0 == 83 || LA40_0 == 88 || LA40_0 == 94 || LA40_0 == 96 || LA40_0 == 98) {
                        alt40 = 1;
                     }

                     switch (alt40) {
                        case 1:
                           this.pushFollow(FOLLOW_element_in_alternative1656);
                           element71 = this.element();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_element.add(element71.getTree());
                           }

                           ++cnt40;
                           break;
                        default:
                           if (cnt40 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(40, this.input);
                              throw eee;
                           }

                           if (this.state.backtracking != 0) {
                              break label251;
                           }

                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (GrammarAST)this.adaptor.nil();
                           GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                           root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(8, retval.start, "ALT")), root_1);
                           if (!stream_element.hasNext()) {
                              throw new RewriteEarlyExitException();
                           }

                           while(stream_element.hasNext()) {
                              this.adaptor.addChild(root_1, stream_element.nextTree());
                           }

                           stream_element.reset();
                           this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(32, this.input.LT(-1), "<end-of-alt>"));
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                           break label251;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(8, retval.start, "ALT")), root_1);
                     this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(35, this.input.LT(-1), "epsilon"));
                     this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(32, this.input.LT(-1), "<end-of-alt>"));
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.recover(this.input, var14);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var14);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final exceptionGroup_return exceptionGroup() throws RecognitionException {
      exceptionGroup_return retval = new exceptionGroup_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      ParserRuleReturnScope exceptionHandler72 = null;
      ParserRuleReturnScope finallyClause73 = null;
      ParserRuleReturnScope finallyClause74 = null;

      try {
         try {
            int alt44 = true;
            int LA44_0 = this.input.LA(1);
            byte alt44;
            if (LA44_0 == 17) {
               alt44 = 1;
            } else {
               if (LA44_0 != 38) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 44, 0, this.input);
                  throw nvae;
               }

               alt44 = 2;
            }

            label181:
            switch (alt44) {
               case 1:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  int cnt42 = 0;

                  while(true) {
                     int alt43 = 2;
                     int LA43_0 = this.input.LA(1);
                     if (LA43_0 == 17) {
                        alt43 = 1;
                     }

                     switch (alt43) {
                        case 1:
                           this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup1702);
                           exceptionHandler72 = this.exceptionHandler();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, exceptionHandler72.getTree());
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

                           alt43 = 2;
                           LA43_0 = this.input.LA(1);
                           if (LA43_0 == 38) {
                              alt43 = 1;
                           }

                           switch (alt43) {
                              case 1:
                                 this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1705);
                                 finallyClause73 = this.finallyClause();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    this.adaptor.addChild(root_0, finallyClause73.getTree());
                                 }
                              default:
                                 break label181;
                           }
                     }
                  }
               case 2:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1711);
                  finallyClause74 = this.finallyClause();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, finallyClause74.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final exceptionHandler_return exceptionHandler() throws RecognitionException {
      exceptionHandler_return retval = new exceptionHandler_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token string_literal75 = null;
      Token ARG_ACTION76 = null;
      Token ACTION77 = null;
      GrammarAST string_literal75_tree = null;
      GrammarAST ARG_ACTION76_tree = null;
      GrammarAST ACTION77_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            string_literal75 = (Token)this.match(this.input, 17, FOLLOW_CATCH_in_exceptionHandler1722);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               string_literal75_tree = (GrammarAST)this.adaptor.create(string_literal75);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)string_literal75_tree, root_0);
            }

            ARG_ACTION76 = (Token)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_exceptionHandler1725);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               ARG_ACTION76_tree = (GrammarAST)this.adaptor.create(ARG_ACTION76);
               this.adaptor.addChild(root_0, ARG_ACTION76_tree);
            }

            ACTION77 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler1727);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               ACTION77_tree = (GrammarAST)this.adaptor.create(ACTION77);
               this.adaptor.addChild(root_0, ACTION77_tree);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.recover(this.input, var13);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final finallyClause_return finallyClause() throws RecognitionException {
      finallyClause_return retval = new finallyClause_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token string_literal78 = null;
      Token ACTION79 = null;
      GrammarAST string_literal78_tree = null;
      GrammarAST ACTION79_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            string_literal78 = (Token)this.match(this.input, 38, FOLLOW_FINALLY_in_finallyClause1738);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               string_literal78_tree = (GrammarAST)this.adaptor.create(string_literal78);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)string_literal78_tree, root_0);
            }

            ACTION79 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause1741);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               ACTION79_tree = (GrammarAST)this.adaptor.create(ACTION79);
               this.adaptor.addChild(root_0, ACTION79_tree);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var11) {
            this.reportError(var11);
            this.recover(this.input, var11);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var11);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final element_return element() throws RecognitionException {
      element_return retval = new element_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      ParserRuleReturnScope elementNoOptionSpec80 = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_elementNoOptionSpec_in_element1752);
            elementNoOptionSpec80 = this.elementNoOptionSpec();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, elementNoOptionSpec80.getTree());
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var8);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final elementNoOptionSpec_return elementNoOptionSpec() throws RecognitionException {
      elementNoOptionSpec_return retval = new elementNoOptionSpec_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token p = null;
      Token ASSIGN82 = null;
      Token PLUS_ASSIGN83 = null;
      Token FORCED_ACTION87 = null;
      Token ACTION88 = null;
      Token IMPLIES89 = null;
      ParserRuleReturnScope sub = null;
      ParserRuleReturnScope a = null;
      ParserRuleReturnScope sub2 = null;
      ParserRuleReturnScope t3 = null;
      ParserRuleReturnScope id81 = null;
      ParserRuleReturnScope atom84 = null;
      ParserRuleReturnScope ebnf85 = null;
      ParserRuleReturnScope ebnf86 = null;
      GrammarAST p_tree = null;
      GrammarAST ASSIGN82_tree = null;
      GrammarAST PLUS_ASSIGN83_tree = null;
      GrammarAST FORCED_ACTION87_tree = null;
      GrammarAST ACTION88_tree = null;
      GrammarAST IMPLIES89_tree = null;
      IntSet elements = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            int alt50 = true;
            int LA50_1;
            int LA45_0;
            NoViableAltException nvae;
            byte alt50;
            switch (this.input.LA(1)) {
               case 4:
                  alt50 = 5;
                  break;
               case 18:
               case 55:
               case 88:
               case 98:
                  alt50 = 2;
                  break;
               case 39:
                  alt50 = 4;
                  break;
               case 51:
                  alt50 = 3;
                  break;
               case 80:
                  LA50_1 = this.input.LA(2);
                  if (LA50_1 != 13 && LA50_1 != 63) {
                     if (LA50_1 != 4 && LA50_1 != 12 && LA50_1 != 15 && LA50_1 != 18 && LA50_1 != 39 && LA50_1 != 51 && LA50_1 != 55 && LA50_1 != 59 && LA50_1 != 62 && LA50_1 != 69 && LA50_1 != 75 && (LA50_1 < 77 || LA50_1 > 78) && LA50_1 != 80 && (LA50_1 < 82 || LA50_1 > 83) && LA50_1 != 86 && LA50_1 != 88 && LA50_1 != 94 && LA50_1 != 96 && LA50_1 != 98) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA45_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 50, 2, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA45_0);
                        }
                     }

                     alt50 = 2;
                  } else {
                     alt50 = 1;
                  }
                  break;
               case 83:
                  alt50 = 6;
                  break;
               case 94:
                  LA50_1 = this.input.LA(2);
                  if (LA50_1 != 4 && LA50_1 != 12 && LA50_1 != 15 && LA50_1 != 18 && LA50_1 != 39 && LA50_1 != 51 && (LA50_1 < 55 || LA50_1 > 56) && LA50_1 != 59 && LA50_1 != 62 && (LA50_1 < 69 || LA50_1 > 70) && LA50_1 != 75 && (LA50_1 < 77 || LA50_1 > 78) && LA50_1 != 80 && (LA50_1 < 82 || LA50_1 > 83) && LA50_1 != 86 && LA50_1 != 88 && LA50_1 != 94 && LA50_1 != 96 && LA50_1 != 98) {
                     if (LA50_1 != 13 && LA50_1 != 63) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA45_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 50, 1, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA45_0);
                        }
                     }

                     alt50 = 1;
                     break;
                  }

                  alt50 = 2;
                  break;
               case 96:
                  alt50 = 7;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 50, 0, this.input);
                  throw nvae;
            }

            byte alt45;
            label1607:
            switch (alt50) {
               case 1:
                  this.pushFollow(FOLLOW_id_in_elementNoOptionSpec1770);
                  id81 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, id81.getTree());
                  }

                  int alt45 = true;
                  LA45_0 = this.input.LA(1);
                  if (LA45_0 == 13) {
                     alt45 = 1;
                  } else {
                     if (LA45_0 != 63) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 45, 0, this.input);
                        throw nvae;
                     }

                     alt45 = 2;
                  }

                  switch (alt45) {
                     case 1:
                        ASSIGN82 = (Token)this.match(this.input, 13, FOLLOW_ASSIGN_in_elementNoOptionSpec1773);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           ASSIGN82_tree = (GrammarAST)this.adaptor.create(ASSIGN82);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ASSIGN82_tree, root_0);
                        }
                        break;
                     case 2:
                        PLUS_ASSIGN83 = (Token)this.match(this.input, 63, FOLLOW_PLUS_ASSIGN_in_elementNoOptionSpec1776);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           PLUS_ASSIGN83_tree = (GrammarAST)this.adaptor.create(PLUS_ASSIGN83);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)PLUS_ASSIGN83_tree, root_0);
                        }
                  }

                  int alt47 = true;
                  int LA47_0 = this.input.LA(1);
                  byte alt47;
                  if (LA47_0 != 18 && LA47_0 != 55 && LA47_0 != 80 && LA47_0 != 88 && LA47_0 != 94 && LA47_0 != 98) {
                     if (LA47_0 != 51) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 47, 0, this.input);
                        throw nvae;
                     }

                     alt47 = 2;
                  } else {
                     alt47 = 1;
                  }

                  switch (alt47) {
                     case 1:
                        this.pushFollow(FOLLOW_atom_in_elementNoOptionSpec1785);
                        atom84 = this.atom();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, atom84.getTree());
                        }

                        int alt46 = 2;
                        int LA46_0 = this.input.LA(1);
                        if (LA46_0 == 62 || LA46_0 == 69 || LA46_0 == 86) {
                           alt46 = 1;
                        }

                        switch (alt46) {
                           case 1:
                              this.pushFollow(FOLLOW_ebnfSuffix_in_elementNoOptionSpec1790);
                              sub = this.ebnfSuffix(root_0, false);
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 root_0 = sub != null ? (GrammarAST)sub.getTree() : null;
                              }
                           default:
                              break label1607;
                        }
                     case 2:
                        this.pushFollow(FOLLOW_ebnf_in_elementNoOptionSpec1803);
                        ebnf85 = this.ebnf();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, ebnf85.getTree());
                        }
                     default:
                        break label1607;
                  }
               case 2:
                  this.pushFollow(FOLLOW_atom_in_elementNoOptionSpec1816);
                  a = this.atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, a.getTree());
                  }

                  alt45 = 2;
                  LA45_0 = this.input.LA(1);
                  if (LA45_0 == 62 || LA45_0 == 69 || LA45_0 == 86) {
                     alt45 = 1;
                  }

                  switch (alt45) {
                     case 1:
                        this.pushFollow(FOLLOW_ebnfSuffix_in_elementNoOptionSpec1825);
                        sub2 = this.ebnfSuffix(a != null ? (GrammarAST)a.getTree() : null, false);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           root_0 = sub2 != null ? (GrammarAST)sub2.getTree() : null;
                        }
                     default:
                        break label1607;
                  }
               case 3:
                  this.pushFollow(FOLLOW_ebnf_in_elementNoOptionSpec1841);
                  ebnf86 = this.ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, ebnf86.getTree());
                  }
                  break;
               case 4:
                  FORCED_ACTION87 = (Token)this.match(this.input, 39, FOLLOW_FORCED_ACTION_in_elementNoOptionSpec1847);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     FORCED_ACTION87_tree = (GrammarAST)this.adaptor.create(FORCED_ACTION87);
                     this.adaptor.addChild(root_0, FORCED_ACTION87_tree);
                  }
                  break;
               case 5:
                  ACTION88 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_elementNoOptionSpec1853);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION88_tree = (GrammarAST)this.adaptor.create(ACTION88);
                     this.adaptor.addChild(root_0, ACTION88_tree);
                  }
                  break;
               case 6:
                  p = (Token)this.match(this.input, 83, FOLLOW_SEMPRED_in_elementNoOptionSpec1861);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     p_tree = (GrammarAST)this.adaptor.create(p);
                     this.adaptor.addChild(root_0, p_tree);
                  }

                  alt45 = 2;
                  LA45_0 = this.input.LA(1);
                  if (LA45_0 == 44) {
                     alt45 = 1;
                  }

                  switch (alt45) {
                     case 1:
                        IMPLIES89 = (Token)this.match(this.input, 44, FOLLOW_IMPLIES_in_elementNoOptionSpec1865);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           p.setType(41);
                        }
                     default:
                        if (this.state.backtracking == 0) {
                           this.grammar.blocksWithSemPreds.add(this.currentBlockAST);
                        }
                        break label1607;
                  }
               case 7:
                  this.pushFollow(FOLLOW_tree__in_elementNoOptionSpec1884);
                  t3 = this.tree_();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, t3.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var47) {
            this.reportError(var47);
            this.recover(this.input, var47);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var47);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final atom_return atom() throws RecognitionException {
      atom_return retval = new atom_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token w = null;
      Token ROOT91 = null;
      Token BANG92 = null;
      Token ROOT99 = null;
      Token BANG100 = null;
      ParserRuleReturnScope range90 = null;
      ParserRuleReturnScope id93 = null;
      ParserRuleReturnScope terminal94 = null;
      ParserRuleReturnScope ruleref95 = null;
      ParserRuleReturnScope terminal96 = null;
      ParserRuleReturnScope ruleref97 = null;
      ParserRuleReturnScope notSet98 = null;
      GrammarAST w_tree = null;
      GrammarAST ROOT91_tree = null;
      GrammarAST BANG92_tree = null;
      GrammarAST ROOT99_tree = null;
      GrammarAST BANG100_tree = null;

      try {
         try {
            int alt55 = true;
            int LA55_2;
            int LA54_0;
            NoViableAltException nvae;
            byte alt55;
            switch (this.input.LA(1)) {
               case 18:
                  LA55_2 = this.input.LA(2);
                  if (LA55_2 == 70) {
                     alt55 = 1;
                     break;
                  }

                  if (LA55_2 != 4 && LA55_2 != 15 && LA55_2 != 18 && LA55_2 != 39 && LA55_2 != 51 && (LA55_2 < 55 || LA55_2 > 56) && LA55_2 != 59 && LA55_2 != 62 && LA55_2 != 69 && LA55_2 != 75 && (LA55_2 < 77 || LA55_2 > 78) && LA55_2 != 80 && (LA55_2 < 82 || LA55_2 > 83) && LA55_2 != 86 && LA55_2 != 88 && LA55_2 != 94 && LA55_2 != 96 && LA55_2 != 98) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     LA54_0 = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 55, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA54_0);
                     }
                  }

                  alt55 = 2;
                  break;
               case 55:
                  alt55 = 3;
                  break;
               case 80:
               case 98:
                  alt55 = 2;
                  break;
               case 88:
                  LA55_2 = this.input.LA(2);
                  if (LA55_2 == 70) {
                     alt55 = 1;
                     break;
                  }

                  if (LA55_2 != 4 && LA55_2 != 15 && LA55_2 != 18 && LA55_2 != 39 && LA55_2 != 51 && (LA55_2 < 55 || LA55_2 > 56) && LA55_2 != 59 && LA55_2 != 62 && LA55_2 != 69 && LA55_2 != 75 && (LA55_2 < 77 || LA55_2 > 78) && LA55_2 != 80 && (LA55_2 < 82 || LA55_2 > 83) && LA55_2 != 86 && LA55_2 != 88 && LA55_2 != 94 && LA55_2 != 96 && LA55_2 != 98) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     LA54_0 = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 55, 3, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA54_0);
                     }
                  }

                  alt55 = 2;
                  break;
               case 94:
                  LA55_2 = this.input.LA(2);
                  if (LA55_2 == 70) {
                     alt55 = 1;
                     break;
                  }

                  if (LA55_2 != 4 && LA55_2 != 12 && LA55_2 != 15 && LA55_2 != 18 && LA55_2 != 39 && LA55_2 != 51 && (LA55_2 < 55 || LA55_2 > 56) && LA55_2 != 59 && LA55_2 != 62 && LA55_2 != 69 && LA55_2 != 75 && (LA55_2 < 77 || LA55_2 > 78) && LA55_2 != 80 && (LA55_2 < 82 || LA55_2 > 83) && LA55_2 != 86 && LA55_2 != 88 && LA55_2 != 94 && LA55_2 != 96 && LA55_2 != 98) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     LA54_0 = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 55, 2, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA54_0);
                     }
                  }

                  alt55 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 55, 0, this.input);
                  throw nvae;
            }

            byte alt54;
            label5743:
            switch (alt55) {
               case 1:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_range_in_atom1899);
                  range90 = this.range();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, range90.getTree());
                  }

                  alt54 = 3;
                  LA54_0 = this.input.LA(1);
                  if (LA54_0 == 77) {
                     alt54 = 1;
                  } else if (LA54_0 == 15) {
                     alt54 = 2;
                  }

                  switch (alt54) {
                     case 1:
                        ROOT91 = (Token)this.match(this.input, 77, FOLLOW_ROOT_in_atom1902);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           ROOT91_tree = (GrammarAST)this.adaptor.create(ROOT91);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ROOT91_tree, root_0);
                        }
                        break label5743;
                     case 2:
                        BANG92 = (Token)this.match(this.input, 15, FOLLOW_BANG_in_atom1905);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           BANG92_tree = (GrammarAST)this.adaptor.create(BANG92);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)BANG92_tree, root_0);
                        }
                     default:
                        break label5743;
                  }
               case 2:
                  int LA52_0;
                  NoViableAltException nvae;
                  root_0 = (GrammarAST)this.adaptor.nil();
                  int alt53 = true;
                  int nvaeConsume;
                  label5831:
                  switch (this.input.LA(1)) {
                     case 18:
                     case 88:
                     case 98:
                        alt54 = 2;
                        break;
                     case 80:
                        LA54_0 = this.input.LA(2);
                        if (LA54_0 == 98) {
                           switch (this.input.LA(3)) {
                              case 4:
                              case 15:
                              case 39:
                              case 51:
                              case 55:
                              case 59:
                              case 62:
                              case 69:
                              case 75:
                              case 77:
                              case 78:
                              case 82:
                              case 83:
                              case 86:
                              case 96:
                                 alt54 = 3;
                                 break label5831;
                              case 18:
                                 LA52_0 = this.input.LA(4);
                                 if (this.synpred1_ANTLR()) {
                                    alt54 = 1;
                                 } else {
                                    alt54 = 3;
                                 }
                                 break label5831;
                              case 80:
                                 LA52_0 = this.input.LA(4);
                                 if (this.synpred1_ANTLR()) {
                                    alt54 = 1;
                                 } else {
                                    alt54 = 3;
                                 }
                                 break label5831;
                              case 88:
                                 LA52_0 = this.input.LA(4);
                                 if (this.synpred1_ANTLR()) {
                                    alt54 = 1;
                                 } else {
                                    alt54 = 3;
                                 }
                                 break label5831;
                              case 94:
                                 LA52_0 = this.input.LA(4);
                                 if (this.synpred1_ANTLR()) {
                                    alt54 = 1;
                                 } else {
                                    alt54 = 3;
                                 }
                                 break label5831;
                              case 98:
                                 LA52_0 = this.input.LA(4);
                                 if (this.synpred1_ANTLR()) {
                                    alt54 = 1;
                                 } else {
                                    alt54 = 3;
                                 }
                                 break label5831;
                              default:
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return retval;
                                 }

                                 LA52_0 = this.input.mark();

                                 try {
                                    for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                       this.input.consume();
                                    }

                                    nvae = new NoViableAltException("", 53, 5, this.input);
                                    throw nvae;
                                 } finally {
                                    this.input.rewind(LA52_0);
                                 }
                           }
                        } else {
                           if (LA54_0 != 4 && LA54_0 != 12 && LA54_0 != 15 && LA54_0 != 18 && LA54_0 != 39 && LA54_0 != 51 && LA54_0 != 55 && LA54_0 != 59 && LA54_0 != 62 && LA54_0 != 69 && LA54_0 != 75 && (LA54_0 < 77 || LA54_0 > 78) && LA54_0 != 80 && (LA54_0 < 82 || LA54_0 > 83) && LA54_0 != 86 && LA54_0 != 88 && LA54_0 != 94 && LA54_0 != 96) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              LA52_0 = this.input.mark();

                              try {
                                 this.input.consume();
                                 nvae = new NoViableAltException("", 53, 2, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(LA52_0);
                              }
                           }

                           alt54 = 3;
                           break;
                        }
                     case 94:
                        LA54_0 = this.input.LA(2);
                        if (LA54_0 == 98) {
                           switch (this.input.LA(3)) {
                              case 4:
                              case 15:
                              case 39:
                              case 51:
                              case 55:
                              case 59:
                              case 62:
                              case 69:
                              case 75:
                              case 77:
                              case 78:
                              case 82:
                              case 83:
                              case 86:
                              case 96:
                                 alt54 = 2;
                                 break label5831;
                              case 18:
                                 LA52_0 = this.input.LA(4);
                                 if (this.synpred1_ANTLR()) {
                                    alt54 = 1;
                                 } else {
                                    alt54 = 2;
                                 }
                                 break label5831;
                              case 80:
                                 LA52_0 = this.input.LA(4);
                                 if (this.synpred1_ANTLR()) {
                                    alt54 = 1;
                                 } else {
                                    alt54 = 2;
                                 }
                                 break label5831;
                              case 88:
                                 LA52_0 = this.input.LA(4);
                                 if (this.synpred1_ANTLR()) {
                                    alt54 = 1;
                                 } else {
                                    alt54 = 2;
                                 }
                                 break label5831;
                              case 94:
                                 LA52_0 = this.input.LA(4);
                                 if (this.synpred1_ANTLR()) {
                                    alt54 = 1;
                                 } else {
                                    alt54 = 2;
                                 }
                                 break label5831;
                              case 98:
                                 LA52_0 = this.input.LA(4);
                                 if (this.synpred1_ANTLR()) {
                                    alt54 = 1;
                                 } else {
                                    alt54 = 2;
                                 }
                                 break label5831;
                              default:
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return retval;
                                 }

                                 LA52_0 = this.input.mark();

                                 try {
                                    for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                       this.input.consume();
                                    }

                                    nvae = new NoViableAltException("", 53, 4, this.input);
                                    throw nvae;
                                 } finally {
                                    this.input.rewind(LA52_0);
                                 }
                           }
                        } else {
                           if (LA54_0 != 4 && LA54_0 != 12 && LA54_0 != 15 && LA54_0 != 18 && LA54_0 != 39 && LA54_0 != 51 && (LA54_0 < 55 || LA54_0 > 56) && LA54_0 != 59 && LA54_0 != 62 && LA54_0 != 69 && LA54_0 != 75 && (LA54_0 < 77 || LA54_0 > 78) && LA54_0 != 80 && (LA54_0 < 82 || LA54_0 > 83) && LA54_0 != 86 && LA54_0 != 88 && LA54_0 != 94 && LA54_0 != 96) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              LA52_0 = this.input.mark();

                              try {
                                 this.input.consume();
                                 nvae = new NoViableAltException("", 53, 1, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(LA52_0);
                              }
                           }

                           alt54 = 2;
                           break;
                        }
                     default:
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 53, 0, this.input);
                        throw nvae;
                  }

                  switch (alt54) {
                     case 1:
                        this.pushFollow(FOLLOW_id_in_atom1945);
                        id93 = this.id();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, id93.getTree());
                        }

                        w = (Token)this.match(this.input, 98, FOLLOW_WILDCARD_in_atom1949);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           w_tree = (GrammarAST)this.adaptor.create(w);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)w_tree, root_0);
                        }

                        int alt52 = true;
                        LA52_0 = this.input.LA(1);
                        byte alt52;
                        if (LA52_0 != 18 && LA52_0 != 88 && LA52_0 != 94 && LA52_0 != 98) {
                           if (LA52_0 != 80) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvae = new NoViableAltException("", 52, 0, this.input);
                              throw nvae;
                           }

                           alt52 = 2;
                        } else {
                           alt52 = 1;
                        }

                        switch (alt52) {
                           case 1:
                              this.pushFollow(FOLLOW_terminal_in_atom1953);
                              terminal94 = this.terminal();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 this.adaptor.addChild(root_0, terminal94.getTree());
                              }
                              break;
                           case 2:
                              this.pushFollow(FOLLOW_ruleref_in_atom1955);
                              ruleref95 = this.ruleref();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 this.adaptor.addChild(root_0, ruleref95.getTree());
                              }
                        }

                        if (this.state.backtracking == 0) {
                           w.setType(29);
                        }
                        break label5743;
                     case 2:
                        this.pushFollow(FOLLOW_terminal_in_atom1964);
                        terminal96 = this.terminal();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, terminal96.getTree());
                        }
                        break label5743;
                     case 3:
                        this.pushFollow(FOLLOW_ruleref_in_atom1970);
                        ruleref97 = this.ruleref();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, ruleref97.getTree());
                        }
                     default:
                        break label5743;
                  }
               case 3:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_notSet_in_atom1979);
                  notSet98 = this.notSet();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, notSet98.getTree());
                  }

                  alt54 = 3;
                  LA54_0 = this.input.LA(1);
                  if (LA54_0 == 77) {
                     alt54 = 1;
                  } else if (LA54_0 == 15) {
                     alt54 = 2;
                  }

                  switch (alt54) {
                     case 1:
                        ROOT99 = (Token)this.match(this.input, 77, FOLLOW_ROOT_in_atom1982);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           ROOT99_tree = (GrammarAST)this.adaptor.create(ROOT99);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ROOT99_tree, root_0);
                        }
                        break;
                     case 2:
                        BANG100 = (Token)this.match(this.input, 15, FOLLOW_BANG_in_atom1985);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           BANG100_tree = (GrammarAST)this.adaptor.create(BANG100);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)BANG100_tree, root_0);
                        }
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var111) {
            this.reportError(var111);
            this.recover(this.input, var111);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var111);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final ruleref_return ruleref() throws RecognitionException {
      ruleref_return retval = new ruleref_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token RULE_REF101 = null;
      Token ARG_ACTION102 = null;
      Token ROOT103 = null;
      Token BANG104 = null;
      GrammarAST RULE_REF101_tree = null;
      GrammarAST ARG_ACTION102_tree = null;
      GrammarAST ROOT103_tree = null;
      GrammarAST BANG104_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            RULE_REF101 = (Token)this.match(this.input, 80, FOLLOW_RULE_REF_in_ruleref1999);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               RULE_REF101_tree = (GrammarAST)this.adaptor.create(RULE_REF101);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)RULE_REF101_tree, root_0);
            }

            int alt56 = 2;
            int LA56_0 = this.input.LA(1);
            if (LA56_0 == 12) {
               alt56 = 1;
            }

            switch (alt56) {
               case 1:
                  ARG_ACTION102 = (Token)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_ruleref2002);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ARG_ACTION102_tree = (GrammarAST)this.adaptor.create(ARG_ACTION102);
                     this.adaptor.addChild(root_0, ARG_ACTION102_tree);
                  }
               default:
                  int alt57 = 3;
                  int LA57_0 = this.input.LA(1);
                  if (LA57_0 == 77) {
                     alt57 = 1;
                  } else if (LA57_0 == 15) {
                     alt57 = 2;
                  }

                  switch (alt57) {
                     case 1:
                        ROOT103 = (Token)this.match(this.input, 77, FOLLOW_ROOT_in_ruleref2006);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           ROOT103_tree = (GrammarAST)this.adaptor.create(ROOT103);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ROOT103_tree, root_0);
                        }
                        break;
                     case 2:
                        BANG104 = (Token)this.match(this.input, 15, FOLLOW_BANG_in_ruleref2009);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           BANG104_tree = (GrammarAST)this.adaptor.create(BANG104);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)BANG104_tree, root_0);
                        }
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }
            }
         } catch (RecognitionException var19) {
            this.reportError(var19);
            this.recover(this.input, var19);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var19);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final notSet_return notSet() throws RecognitionException {
      notSet_return retval = new notSet_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token NOT105 = null;
      ParserRuleReturnScope notTerminal106 = null;
      ParserRuleReturnScope block107 = null;
      GrammarAST NOT105_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            NOT105 = (Token)this.match(this.input, 55, FOLLOW_NOT_in_notSet2023);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               NOT105_tree = (GrammarAST)this.adaptor.create(NOT105);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)NOT105_tree, root_0);
            }

            int alt58 = true;
            int LA58_0 = this.input.LA(1);
            byte alt58;
            if (LA58_0 != 18 && LA58_0 != 88 && LA58_0 != 94) {
               if (LA58_0 != 51) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 58, 0, this.input);
                  throw nvae;
               }

               alt58 = 2;
            } else {
               alt58 = 1;
            }

            switch (alt58) {
               case 1:
                  this.pushFollow(FOLLOW_notTerminal_in_notSet2030);
                  notTerminal106 = this.notTerminal();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, notTerminal106.getTree());
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_block_in_notSet2036);
                  block107 = this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, block107.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.recover(this.input, var13);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final treeRoot_return treeRoot() throws RecognitionException {
      treeRoot_return retval = new treeRoot_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token ASSIGN109 = null;
      Token PLUS_ASSIGN110 = null;
      ParserRuleReturnScope id108 = null;
      ParserRuleReturnScope atom111 = null;
      ParserRuleReturnScope block112 = null;
      ParserRuleReturnScope atom113 = null;
      ParserRuleReturnScope block114 = null;
      GrammarAST ASSIGN109_tree = null;
      GrammarAST PLUS_ASSIGN110_tree = null;
      this.atTreeRoot = true;

      try {
         try {
            int alt61 = true;
            int LA61_1;
            int LA59_0;
            NoViableAltException nvae;
            byte alt61;
            switch (this.input.LA(1)) {
               case 18:
               case 55:
               case 88:
               case 98:
                  alt61 = 2;
                  break;
               case 51:
                  alt61 = 3;
                  break;
               case 80:
                  LA61_1 = this.input.LA(2);
                  if (LA61_1 != 13 && LA61_1 != 63) {
                     if (LA61_1 != 4 && LA61_1 != 12 && LA61_1 != 15 && LA61_1 != 18 && LA61_1 != 39 && LA61_1 != 51 && LA61_1 != 55 && LA61_1 != 77 && LA61_1 != 80 && LA61_1 != 83 && LA61_1 != 88 && LA61_1 != 94 && LA61_1 != 96 && LA61_1 != 98) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA59_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 61, 2, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA59_0);
                        }
                     }

                     alt61 = 2;
                     break;
                  }

                  alt61 = 1;
                  break;
               case 94:
                  LA61_1 = this.input.LA(2);
                  if (LA61_1 != 4 && LA61_1 != 12 && LA61_1 != 15 && LA61_1 != 18 && LA61_1 != 39 && LA61_1 != 51 && (LA61_1 < 55 || LA61_1 > 56) && LA61_1 != 70 && LA61_1 != 77 && LA61_1 != 80 && LA61_1 != 83 && LA61_1 != 88 && LA61_1 != 94 && LA61_1 != 96 && LA61_1 != 98) {
                     if (LA61_1 != 13 && LA61_1 != 63) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA59_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 61, 1, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA59_0);
                        }
                     }

                     alt61 = 1;
                  } else {
                     alt61 = 2;
                  }
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 61, 0, this.input);
                  throw nvae;
            }

            label972:
            switch (alt61) {
               case 1:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_id_in_treeRoot2059);
                  id108 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, id108.getTree());
                  }

                  int alt59 = true;
                  LA59_0 = this.input.LA(1);
                  byte alt59;
                  if (LA59_0 == 13) {
                     alt59 = 1;
                  } else {
                     if (LA59_0 != 63) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 59, 0, this.input);
                        throw nvae;
                     }

                     alt59 = 2;
                  }

                  switch (alt59) {
                     case 1:
                        ASSIGN109 = (Token)this.match(this.input, 13, FOLLOW_ASSIGN_in_treeRoot2062);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           ASSIGN109_tree = (GrammarAST)this.adaptor.create(ASSIGN109);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ASSIGN109_tree, root_0);
                        }
                        break;
                     case 2:
                        PLUS_ASSIGN110 = (Token)this.match(this.input, 63, FOLLOW_PLUS_ASSIGN_in_treeRoot2065);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           PLUS_ASSIGN110_tree = (GrammarAST)this.adaptor.create(PLUS_ASSIGN110);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)PLUS_ASSIGN110_tree, root_0);
                        }
                  }

                  int alt60 = true;
                  int LA60_0 = this.input.LA(1);
                  byte alt60;
                  if (LA60_0 != 18 && LA60_0 != 55 && LA60_0 != 80 && LA60_0 != 88 && LA60_0 != 94 && LA60_0 != 98) {
                     if (LA60_0 != 51) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 60, 0, this.input);
                        throw nvae;
                     }

                     alt60 = 2;
                  } else {
                     alt60 = 1;
                  }

                  switch (alt60) {
                     case 1:
                        this.pushFollow(FOLLOW_atom_in_treeRoot2070);
                        atom111 = this.atom();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, atom111.getTree());
                        }
                        break label972;
                     case 2:
                        this.pushFollow(FOLLOW_block_in_treeRoot2072);
                        block112 = this.block();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, block112.getTree());
                        }
                     default:
                        break label972;
                  }
               case 2:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_atom_in_treeRoot2078);
                  atom113 = this.atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, atom113.getTree());
                  }
                  break;
               case 3:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_block_in_treeRoot2083);
                  block114 = this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, block114.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               this.atTreeRoot = false;
            }
         } catch (RecognitionException var33) {
            this.reportError(var33);
            this.recover(this.input, var33);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var33);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final tree__return tree_() throws RecognitionException {
      tree__return retval = new tree__return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token TREE_BEGIN115 = null;
      Token RPAREN118 = null;
      ParserRuleReturnScope treeRoot116 = null;
      ParserRuleReturnScope element117 = null;
      GrammarAST TREE_BEGIN115_tree = null;
      GrammarAST RPAREN118_tree = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         TREE_BEGIN115 = (Token)this.match(this.input, 96, FOLLOW_TREE_BEGIN_in_tree_2094);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               TREE_BEGIN115_tree = (GrammarAST)this.adaptor.create(TREE_BEGIN115);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)TREE_BEGIN115_tree, root_0);
            }

            this.pushFollow(FOLLOW_treeRoot_in_tree_2099);
            treeRoot116 = this.treeRoot();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, treeRoot116.getTree());
               }

               int cnt62 = 0;

               while(true) {
                  int alt62 = 2;
                  int LA62_0 = this.input.LA(1);
                  if (LA62_0 == 4 || LA62_0 == 18 || LA62_0 == 39 || LA62_0 == 51 || LA62_0 == 55 || LA62_0 == 80 || LA62_0 == 83 || LA62_0 == 88 || LA62_0 == 94 || LA62_0 == 96 || LA62_0 == 98) {
                     alt62 = 1;
                  }

                  switch (alt62) {
                     case 1:
                        this.pushFollow(FOLLOW_element_in_tree_2101);
                        element117 = this.element();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, element117.getTree());
                        }

                        ++cnt62;
                        break;
                     default:
                        if (cnt62 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           EarlyExitException eee = new EarlyExitException(62, this.input);
                           throw eee;
                        }

                        RPAREN118 = (Token)this.match(this.input, 78, FOLLOW_RPAREN_in_tree_2106);
                        if (this.state.failed) {
                           return retval;
                        }

                        retval.stop = this.input.LT(-1);
                        if (this.state.backtracking == 0) {
                           retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var16) {
         this.reportError(var16);
         this.recover(this.input, var16);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         return retval;
      } finally {
         ;
      }
   }

   public final ebnf_return ebnf() throws RecognitionException {
      ebnf_return retval = new ebnf_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token QUESTION120 = null;
      Token STAR121 = null;
      Token PLUS122 = null;
      Token IMPLIES123 = null;
      Token ROOT124 = null;
      Token BANG125 = null;
      ParserRuleReturnScope block119 = null;
      GrammarAST QUESTION120_tree = null;
      GrammarAST STAR121_tree = null;
      GrammarAST PLUS122_tree = null;
      GrammarAST IMPLIES123_tree = null;
      GrammarAST ROOT124_tree = null;
      GrammarAST BANG125_tree = null;
      RewriteRuleTokenStream stream_PLUS = new RewriteRuleTokenStream(this.adaptor, "token PLUS");
      RewriteRuleTokenStream stream_STAR = new RewriteRuleTokenStream(this.adaptor, "token STAR");
      RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
      RewriteRuleTokenStream stream_IMPLIES = new RewriteRuleTokenStream(this.adaptor, "token IMPLIES");
      RewriteRuleTokenStream stream_QUESTION = new RewriteRuleTokenStream(this.adaptor, "token QUESTION");
      RewriteRuleTokenStream stream_ROOT = new RewriteRuleTokenStream(this.adaptor, "token ROOT");
      RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");

      try {
         try {
            this.pushFollow(FOLLOW_block_in_ebnf2120);
            block119 = this.block();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_block.add(block119.getTree());
            }

            int alt63 = true;
            byte alt63;
            switch (this.input.LA(1)) {
               case 4:
               case 18:
               case 39:
               case 51:
               case 55:
               case 59:
               case 75:
               case 78:
               case 80:
               case 82:
               case 83:
               case 88:
               case 94:
               case 96:
               case 98:
                  alt63 = 7;
                  break;
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 16:
               case 17:
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
               case 32:
               case 33:
               case 34:
               case 35:
               case 36:
               case 37:
               case 38:
               case 40:
               case 41:
               case 42:
               case 43:
               case 45:
               case 46:
               case 47:
               case 48:
               case 49:
               case 50:
               case 52:
               case 53:
               case 54:
               case 56:
               case 57:
               case 58:
               case 60:
               case 61:
               case 63:
               case 64:
               case 65:
               case 66:
               case 67:
               case 68:
               case 70:
               case 71:
               case 72:
               case 73:
               case 74:
               case 76:
               case 79:
               case 81:
               case 84:
               case 85:
               case 87:
               case 89:
               case 90:
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

                  NoViableAltException nvae = new NoViableAltException("", 63, 0, this.input);
                  throw nvae;
               case 15:
                  alt63 = 6;
                  break;
               case 44:
                  alt63 = 4;
                  break;
               case 62:
                  alt63 = 3;
                  break;
               case 69:
                  alt63 = 1;
                  break;
               case 77:
                  alt63 = 5;
                  break;
               case 86:
                  alt63 = 2;
            }

            GrammarAST root_1;
            switch (alt63) {
               case 1:
                  QUESTION120 = (Token)this.match(this.input, 69, FOLLOW_QUESTION_in_ebnf2126);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_QUESTION.add(QUESTION120);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(57, retval.start, "?")), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  STAR121 = (Token)this.match(this.input, 86, FOLLOW_STAR_in_ebnf2144);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_STAR.add(STAR121);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(21, retval.start, "*")), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  PLUS122 = (Token)this.match(this.input, 62, FOLLOW_PLUS_in_ebnf2162);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_PLUS.add(PLUS122);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(64, retval.start, "+")), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 4:
                  IMPLIES123 = (Token)this.match(this.input, 44, FOLLOW_IMPLIES_in_ebnf2180);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_IMPLIES.add(IMPLIES123);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     if (this.grammarType == 4 && Rule.getRuleType(this.currentRuleName) == 1) {
                        root_1 = (GrammarAST)this.adaptor.nil();
                        root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(89, retval.start, "=>")), root_1);
                        this.adaptor.addChild(root_1, stream_block.nextTree());
                        this.adaptor.addChild(root_0, root_1);
                     } else {
                        this.adaptor.addChild(root_0, this.createSynSemPredFromBlock(block119 != null ? (GrammarAST)block119.getTree() : null, 90));
                     }

                     retval.tree = root_0;
                  }
                  break;
               case 5:
                  ROOT124 = (Token)this.match(this.input, 77, FOLLOW_ROOT_in_ebnf2216);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_ROOT.add(ROOT124);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)stream_ROOT.nextNode(), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 6:
                  BANG125 = (Token)this.match(this.input, 15, FOLLOW_BANG_in_ebnf2233);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_BANG.add(BANG125);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)stream_BANG.nextNode(), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 7:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_block.nextTree());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var29) {
            this.reportError(var29);
            this.recover(this.input, var29);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var29);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final range_return range() throws RecognitionException {
      range_return retval = new range_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token c1 = null;
      Token c2 = null;
      Token t = null;
      Token r = null;
      Token RANGE126 = null;
      Token TOKEN_REF127 = null;
      Token STRING_LITERAL128 = null;
      Token CHAR_LITERAL129 = null;
      GrammarAST c1_tree = null;
      GrammarAST c2_tree = null;
      GrammarAST t_tree = null;
      GrammarAST r_tree = null;
      GrammarAST RANGE126_tree = null;
      GrammarAST TOKEN_REF127_tree = null;
      GrammarAST STRING_LITERAL128_tree = null;
      GrammarAST CHAR_LITERAL129_tree = null;
      RewriteRuleTokenStream stream_RANGE = new RewriteRuleTokenStream(this.adaptor, "token RANGE");
      RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
      RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");

      try {
         try {
            int alt65 = true;
            int LA65_0 = this.input.LA(1);
            byte alt65;
            if (LA65_0 == 18) {
               int LA65_1 = this.input.LA(2);
               int nvaeMark;
               if (LA65_1 != 70) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  nvaeMark = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 65, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               nvaeMark = this.input.LA(3);
               int nvaeMark;
               if (nvaeMark != 18) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  nvaeMark = this.input.mark();

                  try {
                     for(int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                        this.input.consume();
                     }

                     NoViableAltException nvae = new NoViableAltException("", 65, 3, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               nvaeMark = this.input.LA(4);
               if (Rule.getRuleType(this.currentRuleName) == 1) {
                  alt65 = 1;
               } else {
                  alt65 = 2;
               }
            } else {
               if (LA65_0 != 88 && LA65_0 != 94) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 65, 0, this.input);
                  throw nvae;
               }

               alt65 = 2;
            }

            RewriteRuleTokenStream stream_c2;
            switch (alt65) {
               case 1:
                  if (Rule.getRuleType(this.currentRuleName) != 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     throw new FailedPredicateException(this.input, "range", "Rule.getRuleType(currentRuleName) == Grammar.LEXER");
                  }

                  c1 = (Token)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_range2280);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_CHAR_LITERAL.add(c1);
                  }

                  RANGE126 = (Token)this.match(this.input, 70, FOLLOW_RANGE_in_range2282);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_RANGE.add(RANGE126);
                  }

                  c2 = (Token)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_range2286);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_CHAR_LITERAL.add(c2);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleTokenStream stream_c1 = new RewriteRuleTokenStream(this.adaptor, "token c1", c1);
                     stream_c2 = new RewriteRuleTokenStream(this.adaptor, "token c2", c2);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(19, c1, "..")), root_1);
                     this.adaptor.addChild(root_1, stream_c1.nextNode());
                     this.adaptor.addChild(root_1, stream_c2.nextNode());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  int alt64 = true;
                  byte alt64;
                  switch (this.input.LA(1)) {
                     case 18:
                        alt64 = 3;
                        break;
                     case 88:
                        alt64 = 2;
                        break;
                     case 94:
                        alt64 = 1;
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
                        t = (Token)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_range2313);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_TOKEN_REF.add(t);
                        }

                        r = (Token)this.match(this.input, 70, FOLLOW_RANGE_in_range2317);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_RANGE.add(r);
                        }

                        TOKEN_REF127 = (Token)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_range2319);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_TOKEN_REF.add(TOKEN_REF127);
                        }
                        break;
                     case 2:
                        t = (Token)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_range2327);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_STRING_LITERAL.add(t);
                        }

                        r = (Token)this.match(this.input, 70, FOLLOW_RANGE_in_range2331);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_RANGE.add(r);
                        }

                        STRING_LITERAL128 = (Token)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_range2333);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_STRING_LITERAL.add(STRING_LITERAL128);
                        }
                        break;
                     case 3:
                        t = (Token)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_range2341);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_CHAR_LITERAL.add(t);
                        }

                        r = (Token)this.match(this.input, 70, FOLLOW_RANGE_in_range2345);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_RANGE.add(r);
                        }

                        CHAR_LITERAL129 = (Token)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_range2347);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_CHAR_LITERAL.add(CHAR_LITERAL129);
                        }
                  }

                  if (this.state.backtracking == 0) {
                     ErrorManager.syntaxError(170, this.grammar, r, (Object)null, (RecognitionException)null);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     stream_c2 = new RewriteRuleTokenStream(this.adaptor, "token t", t);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_c2.nextNode());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var46) {
            this.reportError(var46);
            this.recover(this.input, var46);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var46);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final terminal_return terminal() throws RecognitionException {
      terminal_return retval = new terminal_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token cl = null;
      Token tr = null;
      Token sl = null;
      Token wi = null;
      Token ROOT131 = null;
      Token BANG132 = null;
      Token ARG_ACTION134 = null;
      Token ROOT135 = null;
      Token BANG136 = null;
      Token ROOT138 = null;
      Token BANG139 = null;
      Token ROOT140 = null;
      Token BANG141 = null;
      ParserRuleReturnScope elementOptions130 = null;
      ParserRuleReturnScope elementOptions133 = null;
      ParserRuleReturnScope elementOptions137 = null;
      GrammarAST cl_tree = null;
      GrammarAST tr_tree = null;
      GrammarAST sl_tree = null;
      GrammarAST wi_tree = null;
      GrammarAST ROOT131_tree = null;
      GrammarAST BANG132_tree = null;
      GrammarAST ARG_ACTION134_tree = null;
      GrammarAST ROOT135_tree = null;
      GrammarAST BANG136_tree = null;
      GrammarAST ROOT138_tree = null;
      GrammarAST BANG139_tree = null;
      GrammarAST ROOT140_tree = null;
      GrammarAST BANG141_tree = null;

      try {
         try {
            int alt74 = true;
            byte alt74;
            switch (this.input.LA(1)) {
               case 18:
                  alt74 = 1;
                  break;
               case 88:
                  alt74 = 3;
                  break;
               case 94:
                  alt74 = 2;
                  break;
               case 98:
                  alt74 = 4;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 74, 0, this.input);
                  throw nvae;
            }

            byte alt73;
            int LA73_0;
            byte alt72;
            int LA72_0;
            label473:
            switch (alt74) {
               case 1:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  cl = (Token)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_terminal2376);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     cl_tree = (GrammarAST)this.adaptor.create(cl);
                     root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)cl_tree, root_0);
                  }

                  alt73 = 2;
                  LA73_0 = this.input.LA(1);
                  if (LA73_0 == 56) {
                     alt73 = 1;
                  }

                  switch (alt73) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_terminal2381);
                        this.elementOptions(cl_tree);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }
                     default:
                        alt72 = 3;
                        LA72_0 = this.input.LA(1);
                        if (LA72_0 == 77) {
                           alt72 = 1;
                        } else if (LA72_0 == 15) {
                           alt72 = 2;
                        }

                        switch (alt72) {
                           case 1:
                              ROOT131 = (Token)this.match(this.input, 77, FOLLOW_ROOT_in_terminal2389);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 ROOT131_tree = (GrammarAST)this.adaptor.create(ROOT131);
                                 root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ROOT131_tree, root_0);
                              }
                              break label473;
                           case 2:
                              BANG132 = (Token)this.match(this.input, 15, FOLLOW_BANG_in_terminal2392);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 BANG132_tree = (GrammarAST)this.adaptor.create(BANG132);
                                 root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)BANG132_tree, root_0);
                              }
                           default:
                              break label473;
                        }
                  }
               case 2:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  tr = (Token)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_terminal2403);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     tr_tree = (GrammarAST)this.adaptor.create(tr);
                     root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)tr_tree, root_0);
                  }

                  alt73 = 2;
                  LA73_0 = this.input.LA(1);
                  if (LA73_0 == 56) {
                     alt73 = 1;
                  }

                  switch (alt73) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_terminal2410);
                        this.elementOptions(tr_tree);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }
                     default:
                        alt72 = 2;
                        LA72_0 = this.input.LA(1);
                        if (LA72_0 == 12) {
                           alt72 = 1;
                        }

                        switch (alt72) {
                           case 1:
                              ARG_ACTION134 = (Token)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_terminal2421);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 ARG_ACTION134_tree = (GrammarAST)this.adaptor.create(ARG_ACTION134);
                                 this.adaptor.addChild(root_0, ARG_ACTION134_tree);
                              }
                           default:
                              int alt70 = 3;
                              int LA70_0 = this.input.LA(1);
                              if (LA70_0 == 77) {
                                 alt70 = 1;
                              } else if (LA70_0 == 15) {
                                 alt70 = 2;
                              }

                              switch (alt70) {
                                 case 1:
                                    ROOT135 = (Token)this.match(this.input, 77, FOLLOW_ROOT_in_terminal2430);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       ROOT135_tree = (GrammarAST)this.adaptor.create(ROOT135);
                                       root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ROOT135_tree, root_0);
                                    }
                                    break label473;
                                 case 2:
                                    BANG136 = (Token)this.match(this.input, 15, FOLLOW_BANG_in_terminal2433);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       BANG136_tree = (GrammarAST)this.adaptor.create(BANG136);
                                       root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)BANG136_tree, root_0);
                                    }
                                 default:
                                    break label473;
                              }
                        }
                  }
               case 3:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  sl = (Token)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_terminal2444);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     sl_tree = (GrammarAST)this.adaptor.create(sl);
                     root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)sl_tree, root_0);
                  }

                  alt73 = 2;
                  LA73_0 = this.input.LA(1);
                  if (LA73_0 == 56) {
                     alt73 = 1;
                  }

                  switch (alt73) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_terminal2449);
                        this.elementOptions(sl_tree);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }
                     default:
                        alt72 = 3;
                        LA72_0 = this.input.LA(1);
                        if (LA72_0 == 77) {
                           alt72 = 1;
                        } else if (LA72_0 == 15) {
                           alt72 = 2;
                        }

                        switch (alt72) {
                           case 1:
                              ROOT138 = (Token)this.match(this.input, 77, FOLLOW_ROOT_in_terminal2457);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 ROOT138_tree = (GrammarAST)this.adaptor.create(ROOT138);
                                 root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ROOT138_tree, root_0);
                              }
                              break label473;
                           case 2:
                              BANG139 = (Token)this.match(this.input, 15, FOLLOW_BANG_in_terminal2460);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 BANG139_tree = (GrammarAST)this.adaptor.create(BANG139);
                                 root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)BANG139_tree, root_0);
                              }
                           default:
                              break label473;
                        }
                  }
               case 4:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  wi = (Token)this.match(this.input, 98, FOLLOW_WILDCARD_in_terminal2471);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     wi_tree = (GrammarAST)this.adaptor.create(wi);
                     this.adaptor.addChild(root_0, wi_tree);
                  }

                  alt73 = 3;
                  LA73_0 = this.input.LA(1);
                  if (LA73_0 == 77) {
                     alt73 = 1;
                  } else if (LA73_0 == 15) {
                     alt73 = 2;
                  }

                  switch (alt73) {
                     case 1:
                        ROOT140 = (Token)this.match(this.input, 77, FOLLOW_ROOT_in_terminal2474);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           ROOT140_tree = (GrammarAST)this.adaptor.create(ROOT140);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ROOT140_tree, root_0);
                        }
                        break;
                     case 2:
                        BANG141 = (Token)this.match(this.input, 15, FOLLOW_BANG_in_terminal2477);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           BANG141_tree = (GrammarAST)this.adaptor.create(BANG141);
                           root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)BANG141_tree, root_0);
                        }
                  }

                  if (this.state.backtracking == 0 && this.atTreeRoot) {
                     ErrorManager.syntaxError(166, this.grammar, wi, (Object)null, (RecognitionException)null);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var43) {
            this.reportError(var43);
            this.recover(this.input, var43);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var43);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final elementOptions_return elementOptions(GrammarAST terminalAST) throws RecognitionException {
      elementOptions_return retval = new elementOptions_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token OPEN_ELEMENT_OPTION142 = null;
      Token CLOSE_ELEMENT_OPTION144 = null;
      Token OPEN_ELEMENT_OPTION145 = null;
      Token SEMI147 = null;
      Token CLOSE_ELEMENT_OPTION149 = null;
      ParserRuleReturnScope defaultNodeOption143 = null;
      ParserRuleReturnScope elementOption146 = null;
      ParserRuleReturnScope elementOption148 = null;
      GrammarAST OPEN_ELEMENT_OPTION142_tree = null;
      GrammarAST CLOSE_ELEMENT_OPTION144_tree = null;
      GrammarAST OPEN_ELEMENT_OPTION145_tree = null;
      GrammarAST SEMI147_tree = null;
      GrammarAST CLOSE_ELEMENT_OPTION149_tree = null;

      try {
         try {
            int alt76 = true;
            int LA76_0 = this.input.LA(1);
            if (LA76_0 != 56) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return retval;
               }

               NoViableAltException nvae = new NoViableAltException("", 76, 0, this.input);
               throw nvae;
            }

            int LA76_1 = this.input.LA(2);
            int LA75_0;
            int nvaeMark;
            int nvaeConsume;
            byte alt76;
            NoViableAltException nvae;
            if (LA76_1 == 94) {
               LA75_0 = this.input.LA(3);
               if (LA75_0 != 20 && LA75_0 != 98) {
                  if (LA75_0 != 13) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                           this.input.consume();
                        }

                        nvae = new NoViableAltException("", 76, 2, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt76 = 2;
               } else {
                  alt76 = 1;
               }
            } else {
               if (LA76_1 != 80) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  LA75_0 = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 76, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(LA75_0);
                  }
               }

               LA75_0 = this.input.LA(3);
               if (LA75_0 != 20 && LA75_0 != 98) {
                  if (LA75_0 != 13) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                           this.input.consume();
                        }

                        nvae = new NoViableAltException("", 76, 3, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt76 = 2;
               } else {
                  alt76 = 1;
               }
            }

            switch (alt76) {
               case 1:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  OPEN_ELEMENT_OPTION142 = (Token)this.match(this.input, 56, FOLLOW_OPEN_ELEMENT_OPTION_in_elementOptions2496);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     OPEN_ELEMENT_OPTION142_tree = (GrammarAST)this.adaptor.create(OPEN_ELEMENT_OPTION142);
                     root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)OPEN_ELEMENT_OPTION142_tree, root_0);
                  }

                  this.pushFollow(FOLLOW_defaultNodeOption_in_elementOptions2499);
                  defaultNodeOption143 = this.defaultNodeOption(terminalAST);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, defaultNodeOption143.getTree());
                  }

                  CLOSE_ELEMENT_OPTION144 = (Token)this.match(this.input, 20, FOLLOW_CLOSE_ELEMENT_OPTION_in_elementOptions2502);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 2:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  OPEN_ELEMENT_OPTION145 = (Token)this.match(this.input, 56, FOLLOW_OPEN_ELEMENT_OPTION_in_elementOptions2508);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     OPEN_ELEMENT_OPTION145_tree = (GrammarAST)this.adaptor.create(OPEN_ELEMENT_OPTION145);
                     root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)OPEN_ELEMENT_OPTION145_tree, root_0);
                  }

                  this.pushFollow(FOLLOW_elementOption_in_elementOptions2511);
                  elementOption146 = this.elementOption(terminalAST);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, elementOption146.getTree());
                  }

                  label834:
                  while(true) {
                     int alt75 = 2;
                     LA75_0 = this.input.LA(1);
                     if (LA75_0 == 82) {
                        alt75 = 1;
                     }

                     switch (alt75) {
                        case 1:
                           SEMI147 = (Token)this.match(this.input, 82, FOLLOW_SEMI_in_elementOptions2515);
                           if (this.state.failed) {
                              return retval;
                           }

                           this.pushFollow(FOLLOW_elementOption_in_elementOptions2518);
                           elementOption148 = this.elementOption(terminalAST);
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, elementOption148.getTree());
                           }
                           break;
                        default:
                           CLOSE_ELEMENT_OPTION149 = (Token)this.match(this.input, 20, FOLLOW_CLOSE_ELEMENT_OPTION_in_elementOptions2523);
                           if (this.state.failed) {
                              return retval;
                           }
                           break label834;
                     }
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var50) {
            this.reportError(var50);
            this.recover(this.input, var50);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var50);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final defaultNodeOption_return defaultNodeOption(GrammarAST terminalAST) throws RecognitionException {
      defaultNodeOption_return retval = new defaultNodeOption_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      ParserRuleReturnScope elementOptionId150 = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_elementOptionId_in_defaultNodeOption2536);
            elementOptionId150 = this.elementOptionId();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, elementOptionId150.getTree());
            }

            if (this.state.backtracking == 0) {
               terminalAST.setTerminalOption(this.grammar, "node", elementOptionId150 != null ? ((elementOptionId_return)elementOptionId150).qid : null);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.recover(this.input, var9);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var9);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final elementOption_return elementOption(GrammarAST terminalAST) throws RecognitionException {
      elementOption_return retval = new elementOption_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token t = null;
      Token ASSIGN152 = null;
      ParserRuleReturnScope id151 = null;
      ParserRuleReturnScope elementOptionId153 = null;
      GrammarAST t_tree = null;
      GrammarAST ASSIGN152_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            this.pushFollow(FOLLOW_id_in_elementOption2552);
            id151 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, id151.getTree());
            }

            ASSIGN152 = (Token)this.match(this.input, 13, FOLLOW_ASSIGN_in_elementOption2554);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               ASSIGN152_tree = (GrammarAST)this.adaptor.create(ASSIGN152);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)ASSIGN152_tree, root_0);
            }

            int alt78 = true;
            int LA78_0 = this.input.LA(1);
            byte alt78;
            if (LA78_0 != 80 && LA78_0 != 94) {
               if ((LA78_0 < 30 || LA78_0 > 31) && LA78_0 != 88) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 78, 0, this.input);
                  throw nvae;
               }

               alt78 = 2;
            } else {
               alt78 = 1;
            }

            switch (alt78) {
               case 1:
                  this.pushFollow(FOLLOW_elementOptionId_in_elementOption2561);
                  elementOptionId153 = this.elementOptionId();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, elementOptionId153.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     terminalAST.setTerminalOption(this.grammar, id151 != null ? this.input.toString(id151.start, id151.stop) : null, elementOptionId153 != null ? ((elementOptionId_return)elementOptionId153).qid : null);
                  }
                  break;
               case 2:
                  int alt77 = true;
                  byte alt77;
                  switch (this.input.LA(1)) {
                     case 30:
                        alt77 = 3;
                        break;
                     case 31:
                        alt77 = 2;
                        break;
                     case 88:
                        alt77 = 1;
                        break;
                     default:
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 77, 0, this.input);
                        throw nvae;
                  }

                  switch (alt77) {
                     case 1:
                        t = (Token)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_elementOption2575);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           t_tree = (GrammarAST)this.adaptor.create(t);
                           this.adaptor.addChild(root_0, t_tree);
                        }
                        break;
                     case 2:
                        t = (Token)this.match(this.input, 31, FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_elementOption2579);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           t_tree = (GrammarAST)this.adaptor.create(t);
                           this.adaptor.addChild(root_0, t_tree);
                        }
                        break;
                     case 3:
                        t = (Token)this.match(this.input, 30, FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_elementOption2583);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           t_tree = (GrammarAST)this.adaptor.create(t);
                           this.adaptor.addChild(root_0, t_tree);
                        }
                  }

                  if (this.state.backtracking == 0) {
                     terminalAST.setTerminalOption(this.grammar, id151 != null ? this.input.toString(id151.start, id151.stop) : null, t != null ? t.getText() : null);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var17) {
            this.reportError(var17);
            this.recover(this.input, var17);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final elementOptionId_return elementOptionId() throws RecognitionException {
      elementOptionId_return retval = new elementOptionId_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token char_literal154 = null;
      ParserRuleReturnScope i = null;
      GrammarAST char_literal154_tree = null;
      StringBuffer buf = new StringBuffer();

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         this.pushFollow(FOLLOW_id_in_elementOptionId2614);
         i = this.id();
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, i.getTree());
            }

            if (this.state.backtracking == 0) {
               buf.append(i != null ? this.input.toString(i.start, i.stop) : null);
            }

            while(true) {
               int alt79 = 2;
               int LA79_0 = this.input.LA(1);
               if (LA79_0 == 98) {
                  alt79 = 1;
               }

               switch (alt79) {
                  case 1:
                     char_literal154 = (Token)this.match(this.input, 98, FOLLOW_WILDCARD_in_elementOptionId2619);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        char_literal154_tree = (GrammarAST)this.adaptor.create(char_literal154);
                        this.adaptor.addChild(root_0, char_literal154_tree);
                     }

                     this.pushFollow(FOLLOW_id_in_elementOptionId2623);
                     i = this.id();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, i.getTree());
                     }

                     if (this.state.backtracking == 0) {
                        buf.append("." + (i != null ? this.input.toString(i.start, i.stop) : null));
                     }
                     break;
                  default:
                     if (this.state.backtracking == 0) {
                        retval.qid = buf.toString();
                     }

                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         this.recover(this.input, var13);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         return retval;
      } finally {
         ;
      }
   }

   public final ebnfSuffix_return ebnfSuffix(GrammarAST elemAST, boolean inRewrite) throws RecognitionException {
      ebnfSuffix_return retval = new ebnfSuffix_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token QUESTION155 = null;
      Token STAR156 = null;
      Token PLUS157 = null;
      GrammarAST QUESTION155_tree = null;
      GrammarAST STAR156_tree = null;
      GrammarAST PLUS157_tree = null;
      RewriteRuleTokenStream stream_PLUS = new RewriteRuleTokenStream(this.adaptor, "token PLUS");
      RewriteRuleTokenStream stream_STAR = new RewriteRuleTokenStream(this.adaptor, "token STAR");
      RewriteRuleTokenStream stream_QUESTION = new RewriteRuleTokenStream(this.adaptor, "token QUESTION");
      GrammarAST blkRoot = null;
      GrammarAST alt = null;
      GrammarAST save = this.currentBlockAST;

      try {
         try {
            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (GrammarAST)this.adaptor.nil();
               this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(16, elemAST.getToken(), "BLOCK"));
               retval.tree = root_0;
            }

            if (this.state.backtracking == 0) {
               blkRoot = (GrammarAST)retval.tree.getChild(0);
               this.currentBlockAST = blkRoot;
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (GrammarAST)this.adaptor.nil();
               GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
               root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(8, elemAST.getToken(), "ALT")), root_1);
               this.adaptor.addChild(root_1, elemAST);
               this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(32, (String)"<end-of-alt>"));
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            if (this.state.backtracking == 0) {
               alt = (GrammarAST)retval.tree.getChild(0);
               if (!inRewrite) {
                  this.prefixWithSynPred(alt);
               }
            }

            int alt80 = true;
            byte alt80;
            switch (this.input.LA(1)) {
               case 62:
                  alt80 = 3;
                  break;
               case 69:
                  alt80 = 1;
                  break;
               case 86:
                  alt80 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 80, 0, this.input);
                  throw nvae;
            }

            switch (alt80) {
               case 1:
                  QUESTION155 = (Token)this.match(this.input, 69, FOLLOW_QUESTION_in_ebnfSuffix2700);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_QUESTION.add(QUESTION155);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(57, elemAST.getToken(), "?"));
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  STAR156 = (Token)this.match(this.input, 86, FOLLOW_STAR_in_ebnfSuffix2714);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_STAR.add(STAR156);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(21, elemAST.getToken(), "*"));
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  PLUS157 = (Token)this.match(this.input, 62, FOLLOW_PLUS_in_ebnfSuffix2728);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_PLUS.add(PLUS157);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(64, elemAST.getToken(), "+"));
                     retval.tree = root_0;
                  }
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (GrammarAST)this.adaptor.nil();
               GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
               root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)stream_retval.nextNode(), root_1);
               GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
               root_2 = (GrammarAST)this.adaptor.becomeRoot((Object)blkRoot, root_2);
               this.adaptor.addChild(root_2, alt);
               this.adaptor.addChild(root_2, (GrammarAST)this.adaptor.create(33, elemAST.getToken(), "<end-of-block>"));
               this.adaptor.addChild(root_1, root_2);
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               this.currentBlockAST = save;
            }
         } catch (RecognitionException var24) {
            this.reportError(var24);
            this.recover(this.input, var24);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var24);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final notTerminal_return notTerminal() throws RecognitionException {
      notTerminal_return retval = new notTerminal_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token set158 = null;
      GrammarAST set158_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            set158 = this.input.LT(1);
            if (this.input.LA(1) != 18 && this.input.LA(1) != 88 && this.input.LA(1) != 94) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return retval;
               }

               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               throw mse;
            }

            this.input.consume();
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(set158));
            }

            this.state.errorRecovery = false;
            this.state.failed = false;
            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.recover(this.input, var9);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var9);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final idList_return idList() throws RecognitionException {
      idList_return retval = new idList_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token COMMA160 = null;
      ParserRuleReturnScope id159 = null;
      ParserRuleReturnScope id161 = null;
      GrammarAST COMMA160_tree = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         this.pushFollow(FOLLOW_id_in_idList2790);
         id159 = this.id();
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, id159.getTree());
            }

            while(true) {
               int alt81 = 2;
               int LA81_0 = this.input.LA(1);
               if (LA81_0 == 24) {
                  alt81 = 1;
               }

               switch (alt81) {
                  case 1:
                     COMMA160 = (Token)this.match(this.input, 24, FOLLOW_COMMA_in_idList2793);
                     if (this.state.failed) {
                        return retval;
                     }

                     this.pushFollow(FOLLOW_id_in_idList2796);
                     id161 = this.id();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, id161.getTree());
                     }
                     break;
                  default:
                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         this.recover(this.input, var13);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         return retval;
      } finally {
         ;
      }
   }

   public final id_return id() throws RecognitionException {
      id_return retval = new id_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token TOKEN_REF162 = null;
      Token RULE_REF163 = null;
      GrammarAST TOKEN_REF162_tree = null;
      GrammarAST RULE_REF163_tree = null;
      RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");

      try {
         try {
            int alt82 = true;
            int LA82_0 = this.input.LA(1);
            byte alt82;
            if (LA82_0 == 94) {
               alt82 = 1;
            } else {
               if (LA82_0 != 80) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 82, 0, this.input);
                  throw nvae;
               }

               alt82 = 2;
            }

            switch (alt82) {
               case 1:
                  TOKEN_REF162 = (Token)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_id2809);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_TOKEN_REF.add(TOKEN_REF162);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(43, (Token)TOKEN_REF162));
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  RULE_REF163 = (Token)this.match(this.input, 80, FOLLOW_RULE_REF_in_id2821);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_RULE_REF.add(RULE_REF163);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(43, (Token)RULE_REF163));
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_return rewrite() throws RecognitionException {
      rewrite_return retval = new rewrite_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token REWRITE165 = null;
      ParserRuleReturnScope rewrite_with_sempred164 = null;
      ParserRuleReturnScope rewrite_alternative166 = null;
      GrammarAST REWRITE165_tree = null;
      RewriteRuleTokenStream stream_REWRITE = new RewriteRuleTokenStream(this.adaptor, "token REWRITE");
      RewriteRuleSubtreeStream stream_rewrite_with_sempred = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_with_sempred");
      RewriteRuleSubtreeStream stream_rewrite_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_alternative");

      try {
         try {
            int alt84 = true;
            int LA84_0 = this.input.LA(1);
            byte alt84;
            if (LA84_0 == 75) {
               alt84 = 1;
            } else {
               if (LA84_0 != 59 && LA84_0 != 78 && LA84_0 != 82) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 84, 0, this.input);
                  throw nvae;
               }

               alt84 = 2;
            }

            label186:
            switch (alt84) {
               case 1:
                  while(true) {
                     int alt83 = 2;
                     int LA83_0 = this.input.LA(1);
                     if (LA83_0 == 75) {
                        int LA83_1 = this.input.LA(2);
                        if (LA83_1 == 83) {
                           alt83 = 1;
                        }
                     }

                     switch (alt83) {
                        case 1:
                           this.pushFollow(FOLLOW_rewrite_with_sempred_in_rewrite2841);
                           rewrite_with_sempred164 = this.rewrite_with_sempred();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_rewrite_with_sempred.add(rewrite_with_sempred164.getTree());
                           }
                           break;
                        default:
                           REWRITE165 = (Token)this.match(this.input, 75, FOLLOW_REWRITE_in_rewrite2846);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_REWRITE.add(REWRITE165);
                           }

                           this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite2848);
                           rewrite_alternative166 = this.rewrite_alternative();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_rewrite_alternative.add(rewrite_alternative166.getTree());
                           }

                           if (this.state.backtracking != 0) {
                              break label186;
                           }

                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (GrammarAST)this.adaptor.nil();
                           GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                           root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(76, (String)"REWRITES")), root_1);

                           while(stream_rewrite_with_sempred.hasNext()) {
                              this.adaptor.addChild(root_1, stream_rewrite_with_sempred.nextTree());
                           }

                           stream_rewrite_with_sempred.reset();
                           GrammarAST root_2 = (GrammarAST)this.adaptor.nil();
                           root_2 = (GrammarAST)this.adaptor.becomeRoot((Object)stream_REWRITE.nextNode(), root_2);
                           this.adaptor.addChild(root_2, stream_rewrite_alternative.nextTree());
                           this.adaptor.addChild(root_1, root_2);
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                           break label186;
                     }
                  }
               case 2:
                  root_0 = (GrammarAST)this.adaptor.nil();
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_with_sempred_return rewrite_with_sempred() throws RecognitionException {
      rewrite_with_sempred_return retval = new rewrite_with_sempred_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token REWRITE167 = null;
      Token SEMPRED168 = null;
      ParserRuleReturnScope rewrite_alternative169 = null;
      GrammarAST REWRITE167_tree = null;
      GrammarAST SEMPRED168_tree = null;

      try {
         try {
            root_0 = (GrammarAST)this.adaptor.nil();
            REWRITE167 = (Token)this.match(this.input, 75, FOLLOW_REWRITE_in_rewrite_with_sempred2879);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               REWRITE167_tree = (GrammarAST)this.adaptor.create(REWRITE167);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)REWRITE167_tree, root_0);
            }

            SEMPRED168 = (Token)this.match(this.input, 83, FOLLOW_SEMPRED_in_rewrite_with_sempred2882);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               SEMPRED168_tree = (GrammarAST)this.adaptor.create(SEMPRED168);
               this.adaptor.addChild(root_0, SEMPRED168_tree);
            }

            this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite_with_sempred2884);
            rewrite_alternative169 = this.rewrite_alternative();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, rewrite_alternative169.getTree());
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var12) {
            this.reportError(var12);
            this.recover(this.input, var12);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var12);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_block_return rewrite_block() throws RecognitionException {
      rewrite_block_return retval = new rewrite_block_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token LPAREN170 = null;
      Token RPAREN172 = null;
      ParserRuleReturnScope rewrite_alternative171 = null;
      GrammarAST LPAREN170_tree = null;
      GrammarAST RPAREN172_tree = null;
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleSubtreeStream stream_rewrite_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_alternative");

      try {
         try {
            LPAREN170 = (Token)this.match(this.input, 51, FOLLOW_LPAREN_in_rewrite_block2895);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_LPAREN.add(LPAREN170);
            }

            this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite_block2899);
            rewrite_alternative171 = this.rewrite_alternative();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_alternative.add(rewrite_alternative171.getTree());
            }

            RPAREN172 = (Token)this.match(this.input, 78, FOLLOW_RPAREN_in_rewrite_block2903);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_RPAREN.add(RPAREN172);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (GrammarAST)this.adaptor.nil();
               GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
               root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(16, LPAREN170, "BLOCK")), root_1);
               this.adaptor.addChild(root_1, stream_rewrite_alternative.nextTree());
               this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(33, RPAREN172, "<end-of-block>"));
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.recover(this.input, var16);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_alternative_return rewrite_alternative() throws RecognitionException {
      rewrite_alternative_return retval = new rewrite_alternative_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token ETC175 = null;
      ParserRuleReturnScope rewrite_template173 = null;
      ParserRuleReturnScope rewrite_element174 = null;
      GrammarAST ETC175_tree = null;
      RewriteRuleSubtreeStream stream_rewrite_element = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_element");

      try {
         try {
            int alt86 = true;
            int LA86_0 = this.input.LA(1);
            int cnt85;
            int nvaeMark;
            NoViableAltException nvae;
            byte alt86;
            if (LA86_0 != 94 || (!this.grammar.buildTemplate() || !this.LT(1).getText().equals("template")) && !this.grammar.buildAST() && !this.grammar.buildTemplate()) {
               if (LA86_0 == 80 && (this.grammar.buildTemplate() && this.LT(1).getText().equals("template") || this.grammar.buildAST() || this.grammar.buildTemplate())) {
                  cnt85 = this.input.LA(2);
                  if (this.grammar.buildTemplate()) {
                     alt86 = 1;
                  } else {
                     if (!this.grammar.buildAST()) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 86, 2, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt86 = 2;
                  }
               } else if (LA86_0 == 51 && (this.grammar.buildAST() || this.grammar.buildTemplate())) {
                  cnt85 = this.input.LA(2);
                  if (this.grammar.buildTemplate()) {
                     alt86 = 1;
                  } else {
                     if (!this.grammar.buildAST()) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 86, 3, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt86 = 2;
                  }
               } else if (LA86_0 == 4 && (this.grammar.buildAST() || this.grammar.buildTemplate())) {
                  cnt85 = this.input.LA(2);
                  if (this.grammar.buildTemplate()) {
                     alt86 = 1;
                  } else {
                     if (!this.grammar.buildAST()) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 86, 4, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt86 = 2;
                  }
               } else if ((LA86_0 == 18 || LA86_0 == 28 || LA86_0 == 88 || LA86_0 == 96) && this.grammar.buildAST()) {
                  alt86 = 2;
               } else if (LA86_0 != 59 && LA86_0 != 75 && LA86_0 != 78 && LA86_0 != 82) {
                  if (LA86_0 != 37) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 86, 0, this.input);
                     throw nvae;
                  }

                  alt86 = 4;
               } else {
                  alt86 = 3;
               }
            } else {
               cnt85 = this.input.LA(2);
               if (this.grammar.buildTemplate()) {
                  alt86 = 1;
               } else {
                  if (!this.grammar.buildAST()) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 86, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt86 = 2;
               }
            }

            label1770:
            switch (alt86) {
               case 1:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  if (!this.grammar.buildTemplate()) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     throw new FailedPredicateException(this.input, "rewrite_alternative", "grammar.buildTemplate()");
                  }

                  this.pushFollow(FOLLOW_rewrite_template_in_rewrite_alternative2939);
                  rewrite_template173 = this.rewrite_template();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_template173.getTree());
                  }
                  break;
               case 2:
                  if (!this.grammar.buildAST()) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     throw new FailedPredicateException(this.input, "rewrite_alternative", "grammar.buildAST()");
                  }

                  cnt85 = 0;

                  while(true) {
                     int alt85 = 2;
                     int LA85_0 = this.input.LA(1);
                     if (LA85_0 == 4 || LA85_0 == 18 || LA85_0 == 28 || LA85_0 == 51 || LA85_0 == 80 || LA85_0 == 88 || LA85_0 == 94 || LA85_0 == 96) {
                        alt85 = 1;
                     }

                     switch (alt85) {
                        case 1:
                           this.pushFollow(FOLLOW_rewrite_element_in_rewrite_alternative2951);
                           rewrite_element174 = this.rewrite_element();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_rewrite_element.add(rewrite_element174.getTree());
                           }

                           ++cnt85;
                           break;
                        default:
                           if (cnt85 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(85, this.input);
                              throw eee;
                           }

                           if (this.state.backtracking != 0) {
                              break label1770;
                           }

                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (GrammarAST)this.adaptor.nil();
                           GrammarAST root_1;
                           if (!stream_rewrite_element.hasNext()) {
                              root_1 = (GrammarAST)this.adaptor.nil();
                              root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(8, this.LT(1), "ALT")), root_1);
                              this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(35, (String)"epsilon"));
                              this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(32, (String)"<end-of-alt>"));
                              this.adaptor.addChild(root_0, root_1);
                           } else {
                              root_1 = (GrammarAST)this.adaptor.nil();
                              root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(8, this.LT(1), "ALT")), root_1);
                              if (!stream_rewrite_element.hasNext()) {
                                 throw new RewriteEarlyExitException();
                              }

                              while(stream_rewrite_element.hasNext()) {
                                 this.adaptor.addChild(root_1, stream_rewrite_element.nextTree());
                              }

                              stream_rewrite_element.reset();
                              this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(32, (String)"<end-of-alt>"));
                              this.adaptor.addChild(root_0, root_1);
                           }

                           retval.tree = root_0;
                           break label1770;
                     }
                  }
               case 3:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(8, this.LT(1), "ALT")), root_1);
                     this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(35, (String)"epsilon"));
                     this.adaptor.addChild(root_1, (GrammarAST)this.adaptor.create(32, (String)"<end-of-alt>"));
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 4:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  if (!this.grammar.buildAST()) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     throw new FailedPredicateException(this.input, "rewrite_alternative", "grammar.buildAST()");
                  }

                  ETC175 = (Token)this.match(this.input, 37, FOLLOW_ETC_in_rewrite_alternative3012);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ETC175_tree = (GrammarAST)this.adaptor.create(ETC175);
                     this.adaptor.addChild(root_0, ETC175_tree);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var52) {
            this.reportError(var52);
            this.recover(this.input, var52);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var52);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_element_return rewrite_element() throws RecognitionException {
      rewrite_element_return retval = new rewrite_element_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      ParserRuleReturnScope t = null;
      ParserRuleReturnScope subrule = null;
      ParserRuleReturnScope tr = null;
      ParserRuleReturnScope rewrite_ebnf176 = null;
      RewriteRuleSubtreeStream stream_rewrite_tree = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree");
      RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
      RewriteRuleSubtreeStream stream_rewrite_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_atom");

      try {
         try {
            int alt89 = true;
            byte alt89;
            switch (this.input.LA(1)) {
               case 4:
               case 18:
               case 28:
               case 80:
               case 88:
               case 94:
                  alt89 = 1;
                  break;
               case 51:
                  alt89 = 2;
                  break;
               case 96:
                  alt89 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 89, 0, this.input);
                  throw nvae;
            }

            byte alt88;
            RewriteRuleSubtreeStream stream_tr;
            RewriteRuleSubtreeStream stream_subrule;
            int LA88_0;
            label351:
            switch (alt89) {
               case 1:
                  this.pushFollow(FOLLOW_rewrite_atom_in_rewrite_element3027);
                  t = this.rewrite_atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_atom.add(t.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     stream_tr = new RewriteRuleSubtreeStream(this.adaptor, "rule t", t != null ? t.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_tr.nextTree());
                     retval.tree = root_0;
                  }

                  alt88 = 2;
                  LA88_0 = this.input.LA(1);
                  if (LA88_0 == 62 || LA88_0 == 69 || LA88_0 == 86) {
                     alt88 = 1;
                  }

                  switch (alt88) {
                     case 1:
                        this.pushFollow(FOLLOW_ebnfSuffix_in_rewrite_element3047);
                        subrule = this.ebnfSuffix(t != null ? (GrammarAST)t.getTree() : null, true);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(subrule.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           stream_subrule = new RewriteRuleSubtreeStream(this.adaptor, "rule subrule", subrule != null ? subrule.getTree() : null);
                           root_0 = (GrammarAST)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_subrule.nextTree());
                           retval.tree = root_0;
                        }
                     default:
                        break label351;
                  }
               case 2:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_ebnf_in_rewrite_element3066);
                  rewrite_ebnf176 = this.rewrite_ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_ebnf176.getTree());
                  }
                  break;
               case 3:
                  this.pushFollow(FOLLOW_rewrite_tree_in_rewrite_element3075);
                  tr = this.rewrite_tree();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_tree.add(tr.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     stream_tr = new RewriteRuleSubtreeStream(this.adaptor, "rule tr", tr != null ? tr.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_tr.nextTree());
                     retval.tree = root_0;
                  }

                  alt88 = 2;
                  LA88_0 = this.input.LA(1);
                  if (LA88_0 == 62 || LA88_0 == 69 || LA88_0 == 86) {
                     alt88 = 1;
                  }

                  switch (alt88) {
                     case 1:
                        this.pushFollow(FOLLOW_ebnfSuffix_in_rewrite_element3095);
                        subrule = this.ebnfSuffix(tr != null ? (GrammarAST)tr.getTree() : null, true);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(subrule.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           stream_subrule = new RewriteRuleSubtreeStream(this.adaptor, "rule subrule", subrule != null ? subrule.getTree() : null);
                           root_0 = (GrammarAST)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_subrule.nextTree());
                           retval.tree = root_0;
                        }
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_atom_return rewrite_atom() throws RecognitionException {
      rewrite_atom_return retval = new rewrite_atom_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token tr = null;
      Token cl = null;
      Token sl = null;
      Token ARG_ACTION178 = null;
      Token RULE_REF179 = null;
      Token DOLLAR182 = null;
      Token ACTION184 = null;
      ParserRuleReturnScope elementOptions177 = null;
      ParserRuleReturnScope elementOptions180 = null;
      ParserRuleReturnScope elementOptions181 = null;
      ParserRuleReturnScope label183 = null;
      GrammarAST tr_tree = null;
      GrammarAST cl_tree = null;
      GrammarAST sl_tree = null;
      GrammarAST ARG_ACTION178_tree = null;
      GrammarAST RULE_REF179_tree = null;
      GrammarAST DOLLAR182_tree = null;
      GrammarAST ACTION184_tree = null;

      try {
         try {
            int alt94 = true;
            byte alt94;
            switch (this.input.LA(1)) {
               case 4:
                  alt94 = 6;
                  break;
               case 18:
                  alt94 = 3;
                  break;
               case 28:
                  alt94 = 5;
                  break;
               case 80:
                  alt94 = 2;
                  break;
               case 88:
                  alt94 = 4;
                  break;
               case 94:
                  alt94 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 94, 0, this.input);
                  throw nvae;
            }

            byte alt93;
            int LA93_0;
            label296:
            switch (alt94) {
               case 1:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  tr = (Token)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_rewrite_atom3122);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     tr_tree = (GrammarAST)this.adaptor.create(tr);
                     root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)tr_tree, root_0);
                  }

                  alt93 = 2;
                  LA93_0 = this.input.LA(1);
                  if (LA93_0 == 56) {
                     alt93 = 1;
                  }

                  switch (alt93) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_rewrite_atom3125);
                        this.elementOptions(tr_tree);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }
                     default:
                        int alt91 = 2;
                        int LA91_0 = this.input.LA(1);
                        if (LA91_0 == 12) {
                           alt91 = 1;
                        }

                        switch (alt91) {
                           case 1:
                              ARG_ACTION178 = (Token)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rewrite_atom3130);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 ARG_ACTION178_tree = (GrammarAST)this.adaptor.create(ARG_ACTION178);
                                 this.adaptor.addChild(root_0, ARG_ACTION178_tree);
                              }
                           default:
                              break label296;
                        }
                  }
               case 2:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  RULE_REF179 = (Token)this.match(this.input, 80, FOLLOW_RULE_REF_in_rewrite_atom3137);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RULE_REF179_tree = (GrammarAST)this.adaptor.create(RULE_REF179);
                     this.adaptor.addChild(root_0, RULE_REF179_tree);
                  }
                  break;
               case 3:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  cl = (Token)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_rewrite_atom3144);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     cl_tree = (GrammarAST)this.adaptor.create(cl);
                     this.adaptor.addChild(root_0, cl_tree);
                  }

                  alt93 = 2;
                  LA93_0 = this.input.LA(1);
                  if (LA93_0 == 56) {
                     alt93 = 1;
                  }

                  switch (alt93) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_rewrite_atom3146);
                        this.elementOptions(cl_tree);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }
                     default:
                        break label296;
                  }
               case 4:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  sl = (Token)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_rewrite_atom3156);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     sl_tree = (GrammarAST)this.adaptor.create(sl);
                     this.adaptor.addChild(root_0, sl_tree);
                  }

                  alt93 = 2;
                  LA93_0 = this.input.LA(1);
                  if (LA93_0 == 56) {
                     alt93 = 1;
                  }

                  switch (alt93) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_rewrite_atom3158);
                        this.elementOptions(sl_tree);
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }
                     default:
                        break label296;
                  }
               case 5:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  DOLLAR182 = (Token)this.match(this.input, 28, FOLLOW_DOLLAR_in_rewrite_atom3166);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_label_in_rewrite_atom3169);
                  label183 = this.label();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, label183.getTree());
                  }
                  break;
               case 6:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  ACTION184 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_atom3175);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION184_tree = (GrammarAST)this.adaptor.create(ACTION184);
                     this.adaptor.addChild(root_0, ACTION184_tree);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var30) {
            this.reportError(var30);
            this.recover(this.input, var30);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var30);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final label_return label() throws RecognitionException {
      label_return retval = new label_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token TOKEN_REF185 = null;
      Token RULE_REF186 = null;
      GrammarAST TOKEN_REF185_tree = null;
      GrammarAST RULE_REF186_tree = null;
      RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");

      try {
         try {
            int alt95 = true;
            int LA95_0 = this.input.LA(1);
            byte alt95;
            if (LA95_0 == 94) {
               alt95 = 1;
            } else {
               if (LA95_0 != 80) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 95, 0, this.input);
                  throw nvae;
               }

               alt95 = 2;
            }

            switch (alt95) {
               case 1:
                  TOKEN_REF185 = (Token)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_label3186);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_TOKEN_REF.add(TOKEN_REF185);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(48, (Token)TOKEN_REF185));
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  RULE_REF186 = (Token)this.match(this.input, 80, FOLLOW_RULE_REF_in_label3196);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_RULE_REF.add(RULE_REF186);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(48, (Token)RULE_REF186));
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_ebnf_return rewrite_ebnf() throws RecognitionException {
      rewrite_ebnf_return retval = new rewrite_ebnf_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token QUESTION187 = null;
      Token STAR188 = null;
      Token PLUS189 = null;
      ParserRuleReturnScope b = null;
      GrammarAST QUESTION187_tree = null;
      GrammarAST STAR188_tree = null;
      GrammarAST PLUS189_tree = null;
      RewriteRuleTokenStream stream_PLUS = new RewriteRuleTokenStream(this.adaptor, "token PLUS");
      RewriteRuleTokenStream stream_STAR = new RewriteRuleTokenStream(this.adaptor, "token STAR");
      RewriteRuleTokenStream stream_QUESTION = new RewriteRuleTokenStream(this.adaptor, "token QUESTION");
      RewriteRuleSubtreeStream stream_rewrite_block = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_block");

      try {
         try {
            this.pushFollow(FOLLOW_rewrite_block_in_rewrite_ebnf3214);
            b = this.rewrite_block();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_block.add(b.getTree());
            }

            int alt96 = true;
            byte alt96;
            switch (this.input.LA(1)) {
               case 62:
                  alt96 = 3;
                  break;
               case 69:
                  alt96 = 1;
                  break;
               case 86:
                  alt96 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 96, 0, this.input);
                  throw nvae;
            }

            RewriteRuleSubtreeStream stream_b;
            GrammarAST root_1;
            switch (alt96) {
               case 1:
                  QUESTION187 = (Token)this.match(this.input, 69, FOLLOW_QUESTION_in_rewrite_ebnf3220);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_QUESTION.add(QUESTION187);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     stream_b = new RewriteRuleSubtreeStream(this.adaptor, "rule b", b != null ? b.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(57, b != null ? b.start : null, "?")), root_1);
                     this.adaptor.addChild(root_1, stream_b.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  STAR188 = (Token)this.match(this.input, 86, FOLLOW_STAR_in_rewrite_ebnf3239);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_STAR.add(STAR188);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     stream_b = new RewriteRuleSubtreeStream(this.adaptor, "rule b", b != null ? b.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(21, b != null ? b.start : null, "*")), root_1);
                     this.adaptor.addChild(root_1, stream_b.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  PLUS189 = (Token)this.match(this.input, 62, FOLLOW_PLUS_in_rewrite_ebnf3258);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_PLUS.add(PLUS189);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     stream_b = new RewriteRuleSubtreeStream(this.adaptor, "rule b", b != null ? b.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.nil();
                     root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(64, b != null ? b.start : null, "+")), root_1);
                     this.adaptor.addChild(root_1, stream_b.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var21) {
            this.reportError(var21);
            this.recover(this.input, var21);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var21);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_tree_return rewrite_tree() throws RecognitionException {
      rewrite_tree_return retval = new rewrite_tree_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token TREE_BEGIN190 = null;
      Token RPAREN193 = null;
      ParserRuleReturnScope rewrite_atom191 = null;
      ParserRuleReturnScope rewrite_element192 = null;
      GrammarAST TREE_BEGIN190_tree = null;
      GrammarAST RPAREN193_tree = null;

      try {
         root_0 = (GrammarAST)this.adaptor.nil();
         TREE_BEGIN190 = (Token)this.match(this.input, 96, FOLLOW_TREE_BEGIN_in_rewrite_tree3286);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               TREE_BEGIN190_tree = (GrammarAST)this.adaptor.create(TREE_BEGIN190);
               root_0 = (GrammarAST)this.adaptor.becomeRoot((Object)TREE_BEGIN190_tree, root_0);
            }

            this.pushFollow(FOLLOW_rewrite_atom_in_rewrite_tree3292);
            rewrite_atom191 = this.rewrite_atom();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  this.adaptor.addChild(root_0, rewrite_atom191.getTree());
               }

               while(true) {
                  int alt97 = 2;
                  int LA97_0 = this.input.LA(1);
                  if (LA97_0 == 4 || LA97_0 == 18 || LA97_0 == 28 || LA97_0 == 51 || LA97_0 == 80 || LA97_0 == 88 || LA97_0 == 94 || LA97_0 == 96) {
                     alt97 = 1;
                  }

                  switch (alt97) {
                     case 1:
                        this.pushFollow(FOLLOW_rewrite_element_in_rewrite_tree3294);
                        rewrite_element192 = this.rewrite_element();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           this.adaptor.addChild(root_0, rewrite_element192.getTree());
                        }
                        break;
                     default:
                        RPAREN193 = (Token)this.match(this.input, 78, FOLLOW_RPAREN_in_rewrite_tree3299);
                        if (this.state.failed) {
                           return retval;
                        }

                        retval.stop = this.input.LT(-1);
                        if (this.state.backtracking == 0) {
                           retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var15) {
         this.reportError(var15);
         this.recover(this.input, var15);
         retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_template_return rewrite_template() throws RecognitionException {
      rewrite_template_return retval = new rewrite_template_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token st = null;
      Token ACTION197 = null;
      ParserRuleReturnScope rewrite_template_head194 = null;
      ParserRuleReturnScope rewrite_template_head195 = null;
      ParserRuleReturnScope rewrite_indirect_template_head196 = null;
      GrammarAST st_tree = null;
      GrammarAST ACTION197_tree = null;
      RewriteRuleTokenStream stream_DOUBLE_QUOTE_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_QUOTE_STRING_LITERAL");
      RewriteRuleTokenStream stream_DOUBLE_ANGLE_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_ANGLE_STRING_LITERAL");
      RewriteRuleSubtreeStream stream_rewrite_template_head = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_head");

      try {
         try {
            int alt99 = true;
            int LA99_1;
            byte alt99;
            switch (this.input.LA(1)) {
               case 4:
                  alt99 = 4;
                  break;
               case 51:
                  alt99 = 3;
                  break;
               case 80:
                  LA99_1 = this.input.LA(2);
                  if (this.LT(1).getText().equals("template")) {
                     alt99 = 1;
                  } else {
                     alt99 = 2;
                  }
                  break;
               case 94:
                  LA99_1 = this.input.LA(2);
                  if (this.LT(1).getText().equals("template")) {
                     alt99 = 1;
                  } else {
                     alt99 = 2;
                  }
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 99, 0, this.input);
                  throw nvae;
            }

            switch (alt99) {
               case 1:
                  if (!this.LT(1).getText().equals("template")) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     throw new FailedPredicateException(this.input, "rewrite_template", "LT(1).getText().equals(\"template\")");
                  }

                  this.pushFollow(FOLLOW_rewrite_template_head_in_rewrite_template3334);
                  rewrite_template_head194 = this.rewrite_template_head();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_template_head.add(rewrite_template_head194.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (GrammarAST)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_rewrite_template_head.nextTree());
                     retval.tree = root_0;
                  }

                  int alt98 = true;
                  int LA98_0 = this.input.LA(1);
                  byte alt98;
                  if (LA98_0 == 31) {
                     alt98 = 1;
                  } else {
                     if (LA98_0 != 30) {
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
                        st = (Token)this.match(this.input, 31, FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template3353);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_DOUBLE_QUOTE_STRING_LITERAL.add(st);
                        }
                        break;
                     case 2:
                        st = (Token)this.match(this.input, 30, FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template3359);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_DOUBLE_ANGLE_STRING_LITERAL.add(st);
                        }
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(retval.tree.getChild(0), this.adaptor.create(st));
                  }
                  break;
               case 2:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_template_head_in_rewrite_template3374);
                  rewrite_template_head195 = this.rewrite_template_head();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_template_head195.getTree());
                  }
                  break;
               case 3:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_indirect_template_head_in_rewrite_template3383);
                  rewrite_indirect_template_head196 = this.rewrite_indirect_template_head();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_indirect_template_head196.getTree());
                  }
                  break;
               case 4:
                  root_0 = (GrammarAST)this.adaptor.nil();
                  ACTION197 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template3392);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION197_tree = (GrammarAST)this.adaptor.create(ACTION197);
                     this.adaptor.addChild(root_0, ACTION197_tree);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var20) {
            this.reportError(var20);
            this.recover(this.input, var20);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var20);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_template_head_return rewrite_template_head() throws RecognitionException {
      rewrite_template_head_return retval = new rewrite_template_head_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token lp = null;
      Token RPAREN200 = null;
      ParserRuleReturnScope id198 = null;
      ParserRuleReturnScope rewrite_template_args199 = null;
      GrammarAST lp_tree = null;
      GrammarAST RPAREN200_tree = null;
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");

      try {
         try {
            this.pushFollow(FOLLOW_id_in_rewrite_template_head3405);
            id198 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id198.getTree());
            }

            lp = (Token)this.match(this.input, 51, FOLLOW_LPAREN_in_rewrite_template_head3409);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_LPAREN.add(lp);
            }

            this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template_head3413);
            rewrite_template_args199 = this.rewrite_template_args();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_template_args.add(rewrite_template_args199.getTree());
            }

            RPAREN200 = (Token)this.match(this.input, 78, FOLLOW_RPAREN_in_rewrite_template_head3417);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_RPAREN.add(RPAREN200);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (GrammarAST)this.adaptor.nil();
               GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
               root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(91, lp, "TEMPLATE")), root_1);
               this.adaptor.addChild(root_1, stream_id.nextTree());
               this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_indirect_template_head_return rewrite_indirect_template_head() throws RecognitionException {
      rewrite_indirect_template_head_return retval = new rewrite_indirect_template_head_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token lp = null;
      Token ACTION201 = null;
      Token RPAREN202 = null;
      Token LPAREN203 = null;
      Token RPAREN205 = null;
      ParserRuleReturnScope rewrite_template_args204 = null;
      GrammarAST lp_tree = null;
      GrammarAST ACTION201_tree = null;
      GrammarAST RPAREN202_tree = null;
      GrammarAST LPAREN203_tree = null;
      GrammarAST RPAREN205_tree = null;
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");

      try {
         try {
            lp = (Token)this.match(this.input, 51, FOLLOW_LPAREN_in_rewrite_indirect_template_head3445);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_LPAREN.add(lp);
            }

            ACTION201 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_indirect_template_head3449);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION201);
            }

            RPAREN202 = (Token)this.match(this.input, 78, FOLLOW_RPAREN_in_rewrite_indirect_template_head3453);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_RPAREN.add(RPAREN202);
            }

            LPAREN203 = (Token)this.match(this.input, 51, FOLLOW_LPAREN_in_rewrite_indirect_template_head3457);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_LPAREN.add(LPAREN203);
            }

            this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3459);
            rewrite_template_args204 = this.rewrite_template_args();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_template_args.add(rewrite_template_args204.getTree());
            }

            RPAREN205 = (Token)this.match(this.input, 78, FOLLOW_RPAREN_in_rewrite_indirect_template_head3461);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_RPAREN.add(RPAREN205);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (GrammarAST)this.adaptor.nil();
               GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
               root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(91, lp, "TEMPLATE")), root_1);
               this.adaptor.addChild(root_1, stream_ACTION.nextNode());
               this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var23) {
            this.reportError(var23);
            this.recover(this.input, var23);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var23);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_template_args_return rewrite_template_args() throws RecognitionException {
      rewrite_template_args_return retval = new rewrite_template_args_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token COMMA207 = null;
      ParserRuleReturnScope rewrite_template_arg206 = null;
      ParserRuleReturnScope rewrite_template_arg208 = null;
      GrammarAST COMMA207_tree = null;
      RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
      RewriteRuleSubtreeStream stream_rewrite_template_arg = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_arg");

      try {
         try {
            int alt101 = true;
            int LA101_0 = this.input.LA(1);
            byte alt101;
            if (LA101_0 != 80 && LA101_0 != 94) {
               if (LA101_0 != 78) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 101, 0, this.input);
                  throw nvae;
               }

               alt101 = 2;
            } else {
               alt101 = 1;
            }

            label204: {
               switch (alt101) {
                  case 1:
                     this.pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args3485);
                     rewrite_template_arg206 = this.rewrite_template_arg();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_rewrite_template_arg.add(rewrite_template_arg206.getTree());
                     }
                     break;
                  case 2:
                     if (this.state.backtracking == 0) {
                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (GrammarAST)this.adaptor.nil();
                        this.adaptor.addChild(root_0, (GrammarAST)this.adaptor.create(11, (String)"ARGLIST"));
                        retval.tree = root_0;
                     }
                  default:
                     break label204;
               }

               label203:
               while(true) {
                  int alt100 = 2;
                  int LA100_0 = this.input.LA(1);
                  if (LA100_0 == 24) {
                     alt100 = 1;
                  }

                  switch (alt100) {
                     case 1:
                        COMMA207 = (Token)this.match(this.input, 24, FOLLOW_COMMA_in_rewrite_template_args3488);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_COMMA.add(COMMA207);
                        }

                        this.pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args3490);
                        rewrite_template_arg208 = this.rewrite_template_arg();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_rewrite_template_arg.add(rewrite_template_arg208.getTree());
                        }
                        break;
                     default:
                        if (this.state.backtracking != 0) {
                           break label203;
                        }

                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (GrammarAST)this.adaptor.nil();
                        GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
                        root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(11, (String)"ARGLIST")), root_1);
                        if (!stream_rewrite_template_arg.hasNext()) {
                           throw new RewriteEarlyExitException();
                        }

                        while(stream_rewrite_template_arg.hasNext()) {
                           this.adaptor.addChild(root_1, stream_rewrite_template_arg.nextTree());
                        }

                        stream_rewrite_template_arg.reset();
                        this.adaptor.addChild(root_0, root_1);
                        retval.tree = root_0;
                        break label203;
                  }
               }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var17) {
            this.reportError(var17);
            this.recover(this.input, var17);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_template_arg_return rewrite_template_arg() throws RecognitionException {
      rewrite_template_arg_return retval = new rewrite_template_arg_return();
      retval.start = this.input.LT(1);
      GrammarAST root_0 = null;
      Token a = null;
      Token ACTION210 = null;
      ParserRuleReturnScope id209 = null;
      GrammarAST a_tree = null;
      GrammarAST ACTION210_tree = null;
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleTokenStream stream_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token ASSIGN");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         try {
            this.pushFollow(FOLLOW_id_in_rewrite_template_arg3525);
            id209 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id209.getTree());
            }

            a = (Token)this.match(this.input, 13, FOLLOW_ASSIGN_in_rewrite_template_arg3529);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ASSIGN.add(a);
            }

            ACTION210 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template_arg3531);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION210);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (GrammarAST)this.adaptor.nil();
               GrammarAST root_1 = (GrammarAST)this.adaptor.nil();
               root_1 = (GrammarAST)this.adaptor.becomeRoot((Object)((GrammarAST)this.adaptor.create(10, a, "ARG")), root_1);
               this.adaptor.addChild(root_1, stream_id.nextTree());
               this.adaptor.addChild(root_1, stream_ACTION.nextNode());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (GrammarAST)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.recover(this.input, var16);
            retval.tree = (GrammarAST)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void synpred1_ANTLR_fragment() throws RecognitionException {
      if (this.LT(1).getCharPositionInLine() + this.LT(1).getText().length() == this.LT(2).getCharPositionInLine() && this.LT(2).getCharPositionInLine() + 1 == this.LT(3).getCharPositionInLine()) {
         this.pushFollow(FOLLOW_id_in_synpred1_ANTLR1929);
         this.id();
         --this.state._fsp;
         if (!this.state.failed) {
            this.match(this.input, 98, FOLLOW_WILDCARD_in_synpred1_ANTLR1931);
            if (!this.state.failed) {
               int alt102 = true;
               int LA102_0 = this.input.LA(1);
               byte alt102;
               if (LA102_0 != 18 && LA102_0 != 88 && LA102_0 != 94 && LA102_0 != 98) {
                  if (LA102_0 != 80) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 102, 0, this.input);
                     throw nvae;
                  }

                  alt102 = 2;
               } else {
                  alt102 = 1;
               }

               switch (alt102) {
                  case 1:
                     this.pushFollow(FOLLOW_terminal_in_synpred1_ANTLR1934);
                     this.terminal();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  case 2:
                     this.pushFollow(FOLLOW_ruleref_in_synpred1_ANTLR1936);
                     this.ruleref();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
               }

            }
         }
      } else if (this.state.backtracking > 0) {
         this.state.failed = true;
      } else {
         throw new FailedPredicateException(this.input, "synpred1_ANTLR", "LT(1).getCharPositionInLine()+LT(1).getText().length()==LT(2).getCharPositionInLine()&&\r\n\t\t\t LT(2).getCharPositionInLine()+1==LT(3).getCharPositionInLine()");
      }
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

   public static class rewrite_template_arg_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_template_args_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_indirect_template_head_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_template_head_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_template_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_tree_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_ebnf_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class label_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_atom_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_element_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_alternative_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_block_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_with_sempred_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rewrite_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class id_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class idList_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class notTerminal_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class ebnfSuffix_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class elementOptionId_return extends ParserRuleReturnScope {
      public String qid;
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class elementOption_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class defaultNodeOption_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class elementOptions_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class terminal_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class range_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class ebnf_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class tree__return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class treeRoot_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class notSet_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class ruleref_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class atom_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class elementNoOptionSpec_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class element_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class finallyClause_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class exceptionHandler_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class exceptionGroup_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class alternative_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class block_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class ruleAltList_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class ruleScopeSpec_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class throwsSpec_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class ruleAction_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class ruleActions_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rule_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class rules_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class attrScope_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class attrScopes_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class tokenSpec_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class tokensSpec_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class delegateGrammar_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class delegateGrammars_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class optionValue_return extends ParserRuleReturnScope {
      public Object value = null;
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class option_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class optionsSpec_return extends ParserRuleReturnScope {
      public Map opts = new HashMap();
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class actionScopeName_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class action_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class actions_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class grammarType_return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   public static class grammar__return extends ParserRuleReturnScope {
      GrammarAST tree;

      public GrammarAST getTree() {
         return this.tree;
      }
   }

   static class grammar_Adaptor extends CommonTreeAdaptor {
      ANTLRParser _outer;

      public grammar_Adaptor(ANTLRParser outer) {
         this._outer = outer;
      }

      public Object create(Token payload) {
         GrammarAST t = new GrammarAST(payload);
         if (this._outer != null) {
            t.enclosingRuleName = this._outer.currentRuleName;
         }

         return t;
      }

      public Object errorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
         GrammarAST t = new GrammarASTErrorNode(input, start, stop, e);
         if (this._outer != null) {
            t.enclosingRuleName = this._outer.currentRuleName;
         }

         return t;
      }
   }

   private static class GrammarASTErrorNode extends GrammarAST {
      public IntStream input;
      public Token start;
      public Token stop;
      public RecognitionException trappedException;

      public GrammarASTErrorNode(TokenStream input, Token start, Token stop, RecognitionException e) {
         super(stop);
         if (stop == null || stop.getTokenIndex() < start.getTokenIndex() && stop.getType() != -1) {
            stop = start;
         }

         this.input = input;
         this.start = start;
         this.stop = stop;
         this.trappedException = e;
      }

      public boolean isNil() {
         return false;
      }

      public String getText() {
         String badText = null;
         if (this.start != null) {
            int i = this.start.getTokenIndex();
            int j = this.stop.getTokenIndex();
            if (this.stop.getType() == -1) {
               j = this.input.size();
            }

            badText = ((TokenStream)this.input).toString(i, j);
         } else {
            badText = "<unknown>";
         }

         return badText;
      }

      public void setText(String value) {
      }

      public int getType() {
         return 0;
      }

      public void setType(int value) {
      }

      public String toString() {
         if (this.trappedException instanceof MissingTokenException) {
            return "<missing type: " + ((MissingTokenException)this.trappedException).getMissingType() + ">";
         } else if (this.trappedException instanceof UnwantedTokenException) {
            return "<extraneous: " + ((UnwantedTokenException)this.trappedException).getUnexpectedToken() + ", resync=" + this.getText() + ">";
         } else if (this.trappedException instanceof MismatchedTokenException) {
            return "<mismatched token: " + this.trappedException.token + ", resync=" + this.getText() + ">";
         } else {
            return this.trappedException instanceof NoViableAltException ? "<unexpected: " + this.trappedException.token + ", resync=" + this.getText() + ">" : "<error: " + this.getText() + ">";
         }
      }
   }
}
