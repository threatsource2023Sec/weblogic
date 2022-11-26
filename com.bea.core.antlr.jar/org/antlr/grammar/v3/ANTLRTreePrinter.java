package org.antlr.grammar.v3;

import java.util.StringTokenizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
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

public class ANTLRTreePrinter extends TreeParser {
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
   protected Grammar grammar;
   protected boolean showActions;
   protected StringBuilder buf;
   public static final BitSet FOLLOW_grammar__in_toString73 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rule_in_toString79 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_alternative_in_toString85 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_element_in_toString91 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_single_rewrite_in_toString97 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_in_toString103 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_EOR_in_toString109 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LEXER_GRAMMAR_in_grammar_133 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_135 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PARSER_GRAMMAR_in_grammar_145 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_147 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_GRAMMAR_in_grammar_157 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_159 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_COMBINED_GRAMMAR_in_grammar_169 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_171 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_SCOPE_in_attrScope187 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_attrScope189 = new BitSet(new long[]{528L});
   public static final BitSet FOLLOW_ruleAction_in_attrScope191 = new BitSet(new long[]{528L});
   public static final BitSet FOLLOW_ACTION_in_attrScope194 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_grammarSpec210 = new BitSet(new long[]{288265560658018816L, 537034754L});
   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarSpec219 = new BitSet(new long[]{288265560523801088L, 537034754L});
   public static final BitSet FOLLOW_optionsSpec_in_grammarSpec229 = new BitSet(new long[]{35184372089344L, 537034754L});
   public static final BitSet FOLLOW_delegateGrammars_in_grammarSpec238 = new BitSet(new long[]{512L, 537034754L});
   public static final BitSet FOLLOW_tokensSpec_in_grammarSpec245 = new BitSet(new long[]{512L, 163842L});
   public static final BitSet FOLLOW_attrScope_in_grammarSpec252 = new BitSet(new long[]{512L, 163842L});
   public static final BitSet FOLLOW_actions_in_grammarSpec259 = new BitSet(new long[]{0L, 32770L});
   public static final BitSet FOLLOW_rules_in_grammarSpec265 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_action_in_actions278 = new BitSet(new long[]{514L});
   public static final BitSet FOLLOW_AMPERSAND_in_action299 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_action303 = new BitSet(new long[]{8796093022224L});
   public static final BitSet FOLLOW_ID_in_action312 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_action316 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ACTION_in_action331 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec363 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_option_in_optionsSpec371 = new BitSet(new long[]{8200L});
   public static final BitSet FOLLOW_ASSIGN_in_option397 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_option401 = new BitSet(new long[]{149533581639680L, 16777216L});
   public static final BitSet FOLLOW_optionValue_in_option405 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_optionValue420 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_optionValue440 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_optionValue449 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_INT_in_optionValue460 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_IMPORT_in_delegateGrammars490 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ASSIGN_in_delegateGrammars495 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_delegateGrammars497 = new BitSet(new long[]{8796093022208L});
   public static final BitSet FOLLOW_ID_in_delegateGrammars499 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_delegateGrammars504 = new BitSet(new long[]{8796093030408L});
   public static final BitSet FOLLOW_TOKENS_in_tokensSpec521 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_tokenSpec_in_tokensSpec523 = new BitSet(new long[]{8200L, 1073741824L});
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec536 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ASSIGN_in_tokenSpec543 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec545 = new BitSet(new long[]{262144L, 16777216L});
   public static final BitSet FOLLOW_set_in_tokenSpec547 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_rule_in_rules566 = new BitSet(new long[]{2L, 32770L});
   public static final BitSet FOLLOW_precRule_in_rules570 = new BitSet(new long[]{2L, 32770L});
   public static final BitSet FOLLOW_RULE_in_rule586 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rule590 = new BitSet(new long[]{1099511628800L, 28L});
   public static final BitSet FOLLOW_modifier_in_rule596 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_ARG_in_rule609 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rule614 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RET_in_rule627 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rule632 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_throwsSpec_in_rule645 = new BitSet(new long[]{288230376151777792L, 131072L});
   public static final BitSet FOLLOW_optionsSpec_in_rule653 = new BitSet(new long[]{66048L, 131072L});
   public static final BitSet FOLLOW_ruleScopeSpec_in_rule661 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_ruleAction_in_rule669 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_block_in_rule688 = new BitSet(new long[]{292057907200L});
   public static final BitSet FOLLOW_exceptionGroup_in_rule695 = new BitSet(new long[]{17179869184L});
   public static final BitSet FOLLOW_EOR_in_rule702 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PREC_RULE_in_precRule721 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_precRule725 = new BitSet(new long[]{1099511628800L, 28L});
   public static final BitSet FOLLOW_modifier_in_precRule731 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_ARG_in_precRule744 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_precRule749 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RET_in_precRule762 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_precRule767 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_throwsSpec_in_precRule780 = new BitSet(new long[]{288230376151777792L, 131072L});
   public static final BitSet FOLLOW_optionsSpec_in_precRule788 = new BitSet(new long[]{66048L, 131072L});
   public static final BitSet FOLLOW_ruleScopeSpec_in_precRule796 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_ruleAction_in_precRule804 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_block_in_precRule823 = new BitSet(new long[]{292057907200L});
   public static final BitSet FOLLOW_exceptionGroup_in_precRule830 = new BitSet(new long[]{17179869184L});
   public static final BitSet FOLLOW_EOR_in_precRule837 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_AMPERSAND_in_ruleAction855 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_ruleAction859 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_ruleAction863 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_THROWS_in_throwsSpec912 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_throwsSpec914 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec929 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ruleAction_in_ruleScopeSpec931 = new BitSet(new long[]{8796093022744L});
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec935 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_ID_in_ruleScopeSpec941 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_BLOCK_in_block965 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_optionsSpec_in_block976 = new BitSet(new long[]{256L});
   public static final BitSet FOLLOW_alternative_in_block986 = new BitSet(new long[]{8589934848L, 4096L});
   public static final BitSet FOLLOW_rewrite_in_block988 = new BitSet(new long[]{8589934848L});
   public static final BitSet FOLLOW_alternative_in_block994 = new BitSet(new long[]{8589934848L, 4096L});
   public static final BitSet FOLLOW_rewrite_in_block996 = new BitSet(new long[]{8589934848L});
   public static final BitSet FOLLOW_EOB_in_block1004 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_alternative1026 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_alternative1028 = new BitSet(new long[]{-9042943788809592816L, 22666616897L});
   public static final BitSet FOLLOW_EOA_in_alternative1031 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup1046 = new BitSet(new long[]{274878038018L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1052 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1059 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CATCH_in_exceptionHandler1071 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler1073 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_exceptionHandler1075 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_FINALLY_in_finallyClause1088 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_finallyClause1090 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_REWRITES_in_rewrite1103 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_single_rewrite_in_rewrite1105 = new BitSet(new long[]{8L, 2048L});
   public static final BitSet FOLLOW_REWRITES_in_rewrite1112 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_REWRITE_in_single_rewrite1128 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_SEMPRED_in_single_rewrite1137 = new BitSet(new long[]{137438953744L, 134217728L});
   public static final BitSet FOLLOW_alternative_in_single_rewrite1152 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_rewrite_template_in_single_rewrite1159 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ETC_in_single_rewrite1166 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ACTION_in_single_rewrite1175 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TEMPLATE_in_rewrite_template1199 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rewrite_template1208 = new BitSet(new long[]{2048L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_template1219 = new BitSet(new long[]{2048L});
   public static final BitSet FOLLOW_ARGLIST_in_rewrite_template1233 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_in_rewrite_template1249 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rewrite_template1253 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_template1265 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template1301 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template1310 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ROOT_in_element1334 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element1336 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BANG_in_element1345 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element1347 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_atom_in_element1355 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NOT_in_element1361 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element1365 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RANGE_in_element1372 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_atom_in_element1374 = new BitSet(new long[]{281475513843712L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_element1378 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_RANGE_in_element1385 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_atom_in_element1387 = new BitSet(new long[]{281475513843712L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_element1391 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ASSIGN_in_element1398 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element1402 = new BitSet(new long[]{-9042943793104560112L, 22666616897L});
   public static final BitSet FOLLOW_element_in_element1406 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PLUS_ASSIGN_in_element1413 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element1417 = new BitSet(new long[]{-9042943793104560112L, 22666616897L});
   public static final BitSet FOLLOW_element_in_element1421 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ebnf_in_element1427 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_tree__in_element1432 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYNPRED_in_element1439 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_element1441 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ACTION_in_element1453 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FORCED_ACTION_in_element1463 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SEMPRED_in_element1473 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYN_SEMPRED_in_element1484 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_element1494 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_GATED_SEMPRED_in_element1506 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_EPSILON_in_element1515 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_block_in_ebnf1526 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPTIONAL_in_ebnf1536 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1538 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CLOSURE_in_ebnf1550 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1552 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_ebnf1565 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1567 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_BEGIN_in_tree_1584 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_tree_1588 = new BitSet(new long[]{-9042943793104560104L, 22666616897L});
   public static final BitSet FOLLOW_element_in_tree_1591 = new BitSet(new long[]{-9042943793104560104L, 22666616897L});
   public static final BitSet FOLLOW_RULE_REF_in_atom1617 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1629 = new BitSet(new long[]{32776L, 8192L});
   public static final BitSet FOLLOW_ast_suffix_in_atom1640 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TOKEN_REF_in_atom1655 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1667 = new BitSet(new long[]{32776L, 8192L});
   public static final BitSet FOLLOW_ast_suffix_in_atom1679 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom1694 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ast_suffix_in_atom1703 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_atom1718 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ast_suffix_in_atom1727 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_WILDCARD_in_atom1742 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ast_suffix_in_atom1752 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_LABEL_in_atom1772 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_DOT_in_atom1781 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_atom1783 = new BitSet(new long[]{281475513843712L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_atom1787 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ROOT_in_ast_suffix1800 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BANG_in_ast_suffix1807 = new BitSet(new long[]{2L});

   public TreeParser[] getDelegates() {
      return new TreeParser[0];
   }

   public ANTLRTreePrinter(TreeNodeStream input) {
      this(input, new RecognizerSharedState());
   }

   public ANTLRTreePrinter(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
      this.buf = new StringBuilder(300);
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\antlr\\grammar\\v3\\ANTLRTreePrinter.g";
   }

   private block_return block(GrammarAST t, boolean forceParens) throws RecognitionException {
      ANTLRTreePrinter other = new ANTLRTreePrinter(new CommonTreeNodeStream(t));
      other.buf = this.buf;
      return other.block(forceParens);
   }

   public final int countAltsForBlock(GrammarAST t) {
      int n = 0;

      for(int i = 0; i < t.getChildCount(); ++i) {
         if (t.getChild(i).getType() == 8) {
            ++n;
         }
      }

      return n;
   }

   public void out(String s) {
      this.buf.append(s);
   }

   public void reportError(RecognitionException ex) {
      Token token = null;
      if (ex instanceof MismatchedTokenException) {
         token = ((MismatchedTokenException)ex).token;
      } else if (ex instanceof NoViableAltException) {
         token = ((NoViableAltException)ex).token;
      }

      ErrorManager.syntaxError(100, this.grammar, token, "antlr.print: " + ex.toString(), ex);
   }

   public static String normalize(String g) {
      StringTokenizer st = new StringTokenizer(g, " ", false);
      StringBuffer buf = new StringBuffer();

      while(st.hasMoreTokens()) {
         String w = st.nextToken();
         buf.append(w);
         buf.append(" ");
      }

      return buf.toString().trim();
   }

   public final String toString(Grammar g, boolean showActions) throws RecognitionException {
      String s = null;
      this.grammar = g;
      this.showActions = showActions;

      try {
         int alt1 = true;
         byte alt1;
         switch (this.input.LA(1)) {
            case -1:
            case 76:
               alt1 = 6;
               break;
            case 0:
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 11:
            case 12:
            case 17:
            case 20:
            case 22:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 30:
            case 31:
            case 32:
            case 33:
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
            case 49:
            case 51:
            case 52:
            case 53:
            case 54:
            case 56:
            case 58:
            case 59:
            case 60:
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
            case 78:
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
            default:
               NoViableAltException nvae = new NoViableAltException("", 1, 0, this.input);
               throw nvae;
            case 4:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 19:
            case 21:
            case 29:
            case 35:
            case 39:
            case 41:
            case 48:
            case 55:
            case 57:
            case 63:
            case 64:
            case 70:
            case 77:
            case 80:
            case 83:
            case 88:
            case 89:
            case 90:
            case 94:
            case 96:
            case 98:
               alt1 = 4;
               break;
            case 8:
               alt1 = 3;
               break;
            case 23:
            case 50:
            case 61:
            case 97:
               alt1 = 1;
               break;
            case 34:
               alt1 = 7;
               break;
            case 75:
               alt1 = 5;
               break;
            case 79:
               alt1 = 2;
         }

         switch (alt1) {
            case 1:
               this.pushFollow(FOLLOW_grammar__in_toString73);
               this.grammar_();
               --this.state._fsp;
               break;
            case 2:
               this.pushFollow(FOLLOW_rule_in_toString79);
               this.rule();
               --this.state._fsp;
               break;
            case 3:
               this.pushFollow(FOLLOW_alternative_in_toString85);
               this.alternative();
               --this.state._fsp;
               break;
            case 4:
               this.pushFollow(FOLLOW_element_in_toString91);
               this.element();
               --this.state._fsp;
               break;
            case 5:
               this.pushFollow(FOLLOW_single_rewrite_in_toString97);
               this.single_rewrite();
               --this.state._fsp;
               break;
            case 6:
               this.pushFollow(FOLLOW_rewrite_in_toString103);
               this.rewrite();
               --this.state._fsp;
               break;
            case 7:
               this.match(this.input, 34, FOLLOW_EOR_in_toString109);
         }

         String var5 = normalize(this.buf.toString());
         return var5;
      } catch (RecognitionException var9) {
         this.reportError(var9);
         this.recover(this.input, var9);
         return (String)s;
      } finally {
         ;
      }
   }

   public final void grammar_() throws RecognitionException {
      try {
         try {
            int alt2 = true;
            byte alt2;
            switch (this.input.LA(1)) {
               case 23:
                  alt2 = 4;
                  break;
               case 50:
                  alt2 = 1;
                  break;
               case 61:
                  alt2 = 2;
                  break;
               case 97:
                  alt2 = 3;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 2, 0, this.input);
                  throw nvae;
            }

            switch (alt2) {
               case 1:
                  this.match(this.input, 50, FOLLOW_LEXER_GRAMMAR_in_grammar_133);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_135);
                  this.grammarSpec("lexer ");
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 61, FOLLOW_PARSER_GRAMMAR_in_grammar_145);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_147);
                  this.grammarSpec("parser ");
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 3:
                  this.match(this.input, 97, FOLLOW_TREE_GRAMMAR_in_grammar_157);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_159);
                  this.grammarSpec("tree ");
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 4:
                  this.match(this.input, 23, FOLLOW_COMBINED_GRAMMAR_in_grammar_169);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_171);
                  this.grammarSpec("");
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
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
         this.match(this.input, 81, FOLLOW_SCOPE_in_attrScope187);
         this.match(this.input, 2, (BitSet)null);
         this.match(this.input, 43, FOLLOW_ID_in_attrScope189);

         while(true) {
            int alt3 = 2;
            int LA3_0 = this.input.LA(1);
            if (LA3_0 == 9) {
               alt3 = 1;
            }

            switch (alt3) {
               case 1:
                  this.pushFollow(FOLLOW_ruleAction_in_attrScope191);
                  this.ruleAction();
                  --this.state._fsp;
                  break;
               default:
                  this.match(this.input, 4, FOLLOW_ACTION_in_attrScope194);
                  this.match(this.input, 3, (BitSet)null);
                  return;
            }
         }
      } catch (RecognitionException var6) {
         this.reportError(var6);
         this.recover(this.input, var6);
      } finally {
         ;
      }
   }

   public final void grammarSpec(String gtype) throws RecognitionException {
      GrammarAST id = null;
      GrammarAST cmt = null;

      try {
         id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_grammarSpec210);
         this.out(gtype + "grammar " + (id != null ? id.getText() : null));
         int alt4 = 2;
         int LA4_0 = this.input.LA(1);
         if (LA4_0 == 27) {
            alt4 = 1;
         }

         switch (alt4) {
            case 1:
               cmt = (GrammarAST)this.match(this.input, 27, FOLLOW_DOC_COMMENT_in_grammarSpec219);
               this.out((cmt != null ? cmt.getText() : null) + "\n");
            default:
               int alt5 = 2;
               int LA5_0 = this.input.LA(1);
               if (LA5_0 == 58) {
                  alt5 = 1;
               }

               switch (alt5) {
                  case 1:
                     this.pushFollow(FOLLOW_optionsSpec_in_grammarSpec229);
                     this.optionsSpec();
                     --this.state._fsp;
                  default:
                     this.out(";\n");
                     int alt6 = 2;
                     int LA6_0 = this.input.LA(1);
                     if (LA6_0 == 45) {
                        alt6 = 1;
                     }

                     switch (alt6) {
                        case 1:
                           this.pushFollow(FOLLOW_delegateGrammars_in_grammarSpec238);
                           this.delegateGrammars();
                           --this.state._fsp;
                        default:
                           int alt7 = 2;
                           int LA7_0 = this.input.LA(1);
                           if (LA7_0 == 93) {
                              alt7 = 1;
                           }

                           switch (alt7) {
                              case 1:
                                 this.pushFollow(FOLLOW_tokensSpec_in_grammarSpec245);
                                 this.tokensSpec();
                                 --this.state._fsp;
                           }
                     }
               }
         }

         while(true) {
            int alt9 = 2;
            int LA9_0 = this.input.LA(1);
            if (LA9_0 == 81) {
               alt9 = 1;
            }

            switch (alt9) {
               case 1:
                  this.pushFollow(FOLLOW_attrScope_in_grammarSpec252);
                  this.attrScope();
                  --this.state._fsp;
                  break;
               default:
                  alt9 = 2;
                  LA9_0 = this.input.LA(1);
                  if (LA9_0 == 9) {
                     alt9 = 1;
                  }

                  switch (alt9) {
                     case 1:
                        this.pushFollow(FOLLOW_actions_in_grammarSpec259);
                        this.actions();
                        --this.state._fsp;
                     default:
                        this.pushFollow(FOLLOW_rules_in_grammarSpec265);
                        this.rules();
                        --this.state._fsp;
                        return;
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

   public final void actions() throws RecognitionException {
      try {
         int cnt10 = 0;

         while(true) {
            int alt10 = 2;
            int LA10_0 = this.input.LA(1);
            if (LA10_0 == 9) {
               alt10 = 1;
            }

            switch (alt10) {
               case 1:
                  this.pushFollow(FOLLOW_action_in_actions278);
                  this.action();
                  --this.state._fsp;
                  ++cnt10;
                  break;
               default:
                  if (cnt10 < 1) {
                     EarlyExitException eee = new EarlyExitException(10, this.input);
                     throw eee;
                  }

                  return;
            }
         }
      } catch (RecognitionException var8) {
         this.reportError(var8);
         this.recover(this.input, var8);
      } finally {
         ;
      }
   }

   public final void action() throws RecognitionException {
      GrammarAST id1 = null;
      GrammarAST id2 = null;
      GrammarAST a1 = null;
      GrammarAST a2 = null;
      String scope = null;
      String name = null;
      String action = null;

      try {
         try {
            this.match(this.input, 9, FOLLOW_AMPERSAND_in_action299);
            this.match(this.input, 2, (BitSet)null);
            id1 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_action303);
            int alt11 = true;
            int LA11_0 = this.input.LA(1);
            byte alt11;
            if (LA11_0 == 43) {
               alt11 = 1;
            } else {
               if (LA11_0 != 4) {
                  NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
                  throw nvae;
               }

               alt11 = 2;
            }

            switch (alt11) {
               case 1:
                  id2 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_action312);
                  a1 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_action316);
                  scope = id1 != null ? id1.getText() : null;
                  name = a1 != null ? a1.getText() : null;
                  action = a1 != null ? a1.getText() : null;
                  break;
               case 2:
                  a2 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_action331);
                  scope = null;
                  name = id1 != null ? id1.getText() : null;
                  action = a2 != null ? a2.getText() : null;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.showActions) {
               this.out("@" + (scope != null ? scope + "::" : "") + name + action);
            }
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.recover(this.input, var14);
         }

      } finally {
         ;
      }
   }

   public final void optionsSpec() throws RecognitionException {
      try {
         this.match(this.input, 58, FOLLOW_OPTIONS_in_optionsSpec363);
         this.out(" options {");
         this.match(this.input, 2, (BitSet)null);
         int cnt12 = 0;

         while(true) {
            int alt12 = 2;
            int LA12_0 = this.input.LA(1);
            if (LA12_0 == 13) {
               alt12 = 1;
            }

            switch (alt12) {
               case 1:
                  this.pushFollow(FOLLOW_option_in_optionsSpec371);
                  this.option();
                  --this.state._fsp;
                  this.out("; ");
                  ++cnt12;
                  break;
               default:
                  if (cnt12 < 1) {
                     EarlyExitException eee = new EarlyExitException(12, this.input);
                     throw eee;
                  }

                  this.out("} ");
                  this.match(this.input, 3, (BitSet)null);
                  return;
            }
         }
      } catch (RecognitionException var8) {
         this.reportError(var8);
         this.recover(this.input, var8);
      } finally {
         ;
      }
   }

   public final void option() throws RecognitionException {
      GrammarAST id = null;

      try {
         try {
            this.match(this.input, 13, FOLLOW_ASSIGN_in_option397);
            this.match(this.input, 2, (BitSet)null);
            id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_option401);
            this.out((id != null ? id.getText() : null) + "=");
            this.pushFollow(FOLLOW_optionValue_in_option405);
            this.optionValue();
            --this.state._fsp;
            this.match(this.input, 3, (BitSet)null);
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void optionValue() throws RecognitionException {
      GrammarAST id = null;
      GrammarAST s = null;
      GrammarAST c = null;
      GrammarAST i = null;

      try {
         try {
            int alt13 = true;
            byte alt13;
            switch (this.input.LA(1)) {
               case 18:
                  alt13 = 3;
                  break;
               case 43:
                  alt13 = 1;
                  break;
               case 47:
                  alt13 = 4;
                  break;
               case 88:
                  alt13 = 2;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);
                  throw nvae;
            }

            switch (alt13) {
               case 1:
                  id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_optionValue420);
                  this.out(id != null ? id.getText() : null);
                  break;
               case 2:
                  s = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_optionValue440);
                  this.out(s != null ? s.getText() : null);
                  break;
               case 3:
                  c = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_optionValue449);
                  this.out(c != null ? c.getText() : null);
                  break;
               case 4:
                  i = (GrammarAST)this.match(this.input, 47, FOLLOW_INT_in_optionValue460);
                  this.out(i != null ? i.getText() : null);
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

      } finally {
         ;
      }
   }

   public final void delegateGrammars() throws RecognitionException {
      try {
         this.match(this.input, 45, FOLLOW_IMPORT_in_delegateGrammars490);
         this.match(this.input, 2, (BitSet)null);
         int cnt14 = 0;

         while(true) {
            int alt14 = 3;
            int LA14_0 = this.input.LA(1);
            if (LA14_0 == 13) {
               alt14 = 1;
            } else if (LA14_0 == 43) {
               alt14 = 2;
            }

            switch (alt14) {
               case 1:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_delegateGrammars495);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 43, FOLLOW_ID_in_delegateGrammars497);
                  this.match(this.input, 43, FOLLOW_ID_in_delegateGrammars499);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 43, FOLLOW_ID_in_delegateGrammars504);
                  break;
               default:
                  if (cnt14 < 1) {
                     EarlyExitException eee = new EarlyExitException(14, this.input);
                     throw eee;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  return;
            }

            ++cnt14;
         }
      } catch (RecognitionException var8) {
         this.reportError(var8);
         this.recover(this.input, var8);
      } finally {
         ;
      }
   }

   public final void tokensSpec() throws RecognitionException {
      try {
         try {
            this.match(this.input, 93, FOLLOW_TOKENS_in_tokensSpec521);
            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);

               while(true) {
                  int alt15 = 2;
                  int LA15_0 = this.input.LA(1);
                  if (LA15_0 == 13 || LA15_0 == 94) {
                     alt15 = 1;
                  }

                  switch (alt15) {
                     case 1:
                        this.pushFollow(FOLLOW_tokenSpec_in_tokensSpec523);
                        this.tokenSpec();
                        --this.state._fsp;
                        break;
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        return;
                  }
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

   public final void tokenSpec() throws RecognitionException {
      try {
         try {
            int alt16 = true;
            int LA16_0 = this.input.LA(1);
            byte alt16;
            if (LA16_0 == 94) {
               alt16 = 1;
            } else {
               if (LA16_0 != 13) {
                  NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
                  throw nvae;
               }

               alt16 = 2;
            }

            switch (alt16) {
               case 1:
                  this.match(this.input, 94, FOLLOW_TOKEN_REF_in_tokenSpec536);
                  break;
               case 2:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_tokenSpec543);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 94, FOLLOW_TOKEN_REF_in_tokenSpec545);
                  if (this.input.LA(1) != 18 && this.input.LA(1) != 88) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }

                  this.input.consume();
                  this.state.errorRecovery = false;
                  this.match(this.input, 3, (BitSet)null);
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void rules() throws RecognitionException {
      try {
         int cnt17 = 0;

         while(true) {
            int alt17 = 3;
            int LA17_0 = this.input.LA(1);
            if (LA17_0 == 79) {
               alt17 = 1;
            } else if (LA17_0 == 65) {
               alt17 = 2;
            }

            switch (alt17) {
               case 1:
                  this.pushFollow(FOLLOW_rule_in_rules566);
                  this.rule();
                  --this.state._fsp;
                  break;
               case 2:
                  this.pushFollow(FOLLOW_precRule_in_rules570);
                  this.precRule();
                  --this.state._fsp;
                  break;
               default:
                  if (cnt17 < 1) {
                     EarlyExitException eee = new EarlyExitException(17, this.input);
                     throw eee;
                  }

                  return;
            }

            ++cnt17;
         }
      } catch (RecognitionException var8) {
         this.reportError(var8);
         this.recover(this.input, var8);
      } finally {
         ;
      }
   }

   public final void rule() throws RecognitionException {
      GrammarAST id = null;
      GrammarAST arg = null;
      GrammarAST ret = null;
      TreeRuleReturnScope b = null;

      try {
         this.match(this.input, 79, FOLLOW_RULE_in_rule586);
         this.match(this.input, 2, (BitSet)null);
         id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rule590);
         int alt18 = 2;
         int LA18_0 = this.input.LA(1);
         if (LA18_0 == 40 || LA18_0 >= 66 && LA18_0 <= 68) {
            alt18 = 1;
         }

         switch (alt18) {
            case 1:
               this.pushFollow(FOLLOW_modifier_in_rule596);
               this.modifier();
               --this.state._fsp;
            default:
               this.out(id != null ? id.getText() : null);
               this.match(this.input, 10, FOLLOW_ARG_in_rule609);
               byte alt21;
               int LA21_0;
               if (this.input.LA(1) == 2) {
                  this.match(this.input, 2, (BitSet)null);
                  alt21 = 2;
                  LA21_0 = this.input.LA(1);
                  if (LA21_0 == 12) {
                     alt21 = 1;
                  }

                  switch (alt21) {
                     case 1:
                        arg = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rule614);
                        this.out("[" + (arg != null ? arg.getText() : null) + "]");
                     default:
                        this.match(this.input, 3, (BitSet)null);
                  }
               }

               this.match(this.input, 73, FOLLOW_RET_in_rule627);
               if (this.input.LA(1) == 2) {
                  this.match(this.input, 2, (BitSet)null);
                  alt21 = 2;
                  LA21_0 = this.input.LA(1);
                  if (LA21_0 == 12) {
                     alt21 = 1;
                  }

                  switch (alt21) {
                     case 1:
                        ret = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rule632);
                        this.out(" returns [" + (ret != null ? ret.getText() : null) + "]");
                     default:
                        this.match(this.input, 3, (BitSet)null);
                  }
               }

               alt21 = 2;
               LA21_0 = this.input.LA(1);
               if (LA21_0 == 92) {
                  alt21 = 1;
               }

               switch (alt21) {
                  case 1:
                     this.pushFollow(FOLLOW_throwsSpec_in_rule645);
                     this.throwsSpec();
                     --this.state._fsp;
                  default:
                     int alt22 = 2;
                     int LA22_0 = this.input.LA(1);
                     if (LA22_0 == 58) {
                        alt22 = 1;
                     }

                     switch (alt22) {
                        case 1:
                           this.pushFollow(FOLLOW_optionsSpec_in_rule653);
                           this.optionsSpec();
                           --this.state._fsp;
                        default:
                           int alt23 = 2;
                           int LA23_0 = this.input.LA(1);
                           if (LA23_0 == 81) {
                              alt23 = 1;
                           }

                           switch (alt23) {
                              case 1:
                                 this.pushFollow(FOLLOW_ruleScopeSpec_in_rule661);
                                 this.ruleScopeSpec();
                                 --this.state._fsp;
                           }
                     }
               }
         }

         while(true) {
            int alt25 = 2;
            int LA25_0 = this.input.LA(1);
            if (LA25_0 == 9) {
               alt25 = 1;
            }

            switch (alt25) {
               case 1:
                  this.pushFollow(FOLLOW_ruleAction_in_rule669);
                  this.ruleAction();
                  --this.state._fsp;
                  break;
               default:
                  this.out(" :");
                  if (this.input.LA(5) == 55 || this.input.LA(5) == 13) {
                     this.out(" ");
                  }

                  this.pushFollow(FOLLOW_block_in_rule688);
                  b = this.block(false);
                  --this.state._fsp;
                  alt25 = 2;
                  LA25_0 = this.input.LA(1);
                  if (LA25_0 == 17 || LA25_0 == 38) {
                     alt25 = 1;
                  }

                  switch (alt25) {
                     case 1:
                        this.pushFollow(FOLLOW_exceptionGroup_in_rule695);
                        this.exceptionGroup();
                        --this.state._fsp;
                     default:
                        this.match(this.input, 34, FOLLOW_EOR_in_rule702);
                        this.out(";\n");
                        this.match(this.input, 3, (BitSet)null);
                        return;
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

   public final void precRule() throws RecognitionException {
      GrammarAST id = null;
      GrammarAST arg = null;
      GrammarAST ret = null;
      TreeRuleReturnScope b = null;

      try {
         this.match(this.input, 65, FOLLOW_PREC_RULE_in_precRule721);
         this.match(this.input, 2, (BitSet)null);
         id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_precRule725);
         int alt26 = 2;
         int LA26_0 = this.input.LA(1);
         if (LA26_0 == 40 || LA26_0 >= 66 && LA26_0 <= 68) {
            alt26 = 1;
         }

         switch (alt26) {
            case 1:
               this.pushFollow(FOLLOW_modifier_in_precRule731);
               this.modifier();
               --this.state._fsp;
            default:
               this.out(id != null ? id.getText() : null);
               this.match(this.input, 10, FOLLOW_ARG_in_precRule744);
               byte alt29;
               int LA29_0;
               if (this.input.LA(1) == 2) {
                  this.match(this.input, 2, (BitSet)null);
                  alt29 = 2;
                  LA29_0 = this.input.LA(1);
                  if (LA29_0 == 12) {
                     alt29 = 1;
                  }

                  switch (alt29) {
                     case 1:
                        arg = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_precRule749);
                        this.out("[" + (arg != null ? arg.getText() : null) + "]");
                     default:
                        this.match(this.input, 3, (BitSet)null);
                  }
               }

               this.match(this.input, 73, FOLLOW_RET_in_precRule762);
               if (this.input.LA(1) == 2) {
                  this.match(this.input, 2, (BitSet)null);
                  alt29 = 2;
                  LA29_0 = this.input.LA(1);
                  if (LA29_0 == 12) {
                     alt29 = 1;
                  }

                  switch (alt29) {
                     case 1:
                        ret = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_precRule767);
                        this.out(" returns [" + (ret != null ? ret.getText() : null) + "]");
                     default:
                        this.match(this.input, 3, (BitSet)null);
                  }
               }

               alt29 = 2;
               LA29_0 = this.input.LA(1);
               if (LA29_0 == 92) {
                  alt29 = 1;
               }

               switch (alt29) {
                  case 1:
                     this.pushFollow(FOLLOW_throwsSpec_in_precRule780);
                     this.throwsSpec();
                     --this.state._fsp;
                  default:
                     int alt30 = 2;
                     int LA30_0 = this.input.LA(1);
                     if (LA30_0 == 58) {
                        alt30 = 1;
                     }

                     switch (alt30) {
                        case 1:
                           this.pushFollow(FOLLOW_optionsSpec_in_precRule788);
                           this.optionsSpec();
                           --this.state._fsp;
                        default:
                           int alt31 = 2;
                           int LA31_0 = this.input.LA(1);
                           if (LA31_0 == 81) {
                              alt31 = 1;
                           }

                           switch (alt31) {
                              case 1:
                                 this.pushFollow(FOLLOW_ruleScopeSpec_in_precRule796);
                                 this.ruleScopeSpec();
                                 --this.state._fsp;
                           }
                     }
               }
         }

         while(true) {
            int alt33 = 2;
            int LA33_0 = this.input.LA(1);
            if (LA33_0 == 9) {
               alt33 = 1;
            }

            switch (alt33) {
               case 1:
                  this.pushFollow(FOLLOW_ruleAction_in_precRule804);
                  this.ruleAction();
                  --this.state._fsp;
                  break;
               default:
                  this.out(" :");
                  if (this.input.LA(5) == 55 || this.input.LA(5) == 13) {
                     this.out(" ");
                  }

                  this.pushFollow(FOLLOW_block_in_precRule823);
                  b = this.block(false);
                  --this.state._fsp;
                  alt33 = 2;
                  LA33_0 = this.input.LA(1);
                  if (LA33_0 == 17 || LA33_0 == 38) {
                     alt33 = 1;
                  }

                  switch (alt33) {
                     case 1:
                        this.pushFollow(FOLLOW_exceptionGroup_in_precRule830);
                        this.exceptionGroup();
                        --this.state._fsp;
                     default:
                        this.match(this.input, 34, FOLLOW_EOR_in_precRule837);
                        this.out(";\n");
                        this.match(this.input, 3, (BitSet)null);
                        return;
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

   public final void ruleAction() throws RecognitionException {
      GrammarAST id = null;
      GrammarAST a = null;

      try {
         try {
            this.match(this.input, 9, FOLLOW_AMPERSAND_in_ruleAction855);
            this.match(this.input, 2, (BitSet)null);
            id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_ruleAction859);
            a = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_ruleAction863);
            this.match(this.input, 3, (BitSet)null);
            if (this.showActions) {
               this.out("@" + (id != null ? id.getText() : null) + "{" + (a != null ? a.getText() : null) + "}");
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final modifier_return modifier() throws RecognitionException {
      modifier_return retval = new modifier_return();
      retval.start = this.input.LT(1);
      this.out(((GrammarAST)retval.start).getText());
      this.out(" ");

      try {
         try {
            if (this.input.LA(1) != 40 && (this.input.LA(1) < 66 || this.input.LA(1) > 68)) {
               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               throw mse;
            }

            this.input.consume();
            this.state.errorRecovery = false;
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
         this.match(this.input, 92, FOLLOW_THROWS_in_throwsSpec912);
         this.match(this.input, 2, (BitSet)null);
         int cnt34 = 0;

         while(true) {
            int alt34 = 2;
            int LA34_0 = this.input.LA(1);
            if (LA34_0 == 43) {
               alt34 = 1;
            }

            switch (alt34) {
               case 1:
                  this.match(this.input, 43, FOLLOW_ID_in_throwsSpec914);
                  ++cnt34;
                  break;
               default:
                  if (cnt34 < 1) {
                     EarlyExitException eee = new EarlyExitException(34, this.input);
                     throw eee;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  return;
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
            this.match(this.input, 81, FOLLOW_SCOPE_in_ruleScopeSpec929);
            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);

               while(true) {
                  int alt36 = 2;
                  int LA36_0 = this.input.LA(1);
                  if (LA36_0 == 9) {
                     alt36 = 1;
                  }

                  switch (alt36) {
                     case 1:
                        this.pushFollow(FOLLOW_ruleAction_in_ruleScopeSpec931);
                        this.ruleAction();
                        --this.state._fsp;
                        break;
                     default:
                        alt36 = 2;
                        LA36_0 = this.input.LA(1);
                        if (LA36_0 == 4) {
                           alt36 = 1;
                        }

                        switch (alt36) {
                           case 1:
                              this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec935);
                        }

                        while(true) {
                           int alt37 = 2;
                           int LA37_0 = this.input.LA(1);
                           if (LA37_0 == 43) {
                              alt37 = 1;
                           }

                           switch (alt37) {
                              case 1:
                                 this.match(this.input, 43, FOLLOW_ID_in_ruleScopeSpec941);
                                 break;
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                                 return;
                           }
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

   public final block_return block(boolean forceParens) throws RecognitionException {
      block_return retval = new block_return();
      retval.start = this.input.LT(1);
      int numAlts = this.countAltsForBlock((GrammarAST)retval.start);

      try {
         this.match(this.input, 16, FOLLOW_BLOCK_in_block965);
         if (forceParens || numAlts > 1) {
            this.out(" (");
         }

         this.match(this.input, 2, (BitSet)null);
         int alt38 = 2;
         int LA38_0 = this.input.LA(1);
         if (LA38_0 == 58) {
            alt38 = 1;
         }

         switch (alt38) {
            case 1:
               this.pushFollow(FOLLOW_optionsSpec_in_block976);
               this.optionsSpec();
               --this.state._fsp;
               this.out(" :");
            default:
               this.pushFollow(FOLLOW_alternative_in_block986);
               this.alternative();
               --this.state._fsp;
               this.pushFollow(FOLLOW_rewrite_in_block988);
               this.rewrite();
               --this.state._fsp;

               while(true) {
                  int alt39 = 2;
                  int LA39_0 = this.input.LA(1);
                  if (LA39_0 == 8) {
                     alt39 = 1;
                  }

                  switch (alt39) {
                     case 1:
                        this.out("|");
                        this.pushFollow(FOLLOW_alternative_in_block994);
                        this.alternative();
                        --this.state._fsp;
                        this.pushFollow(FOLLOW_rewrite_in_block996);
                        this.rewrite();
                        --this.state._fsp;
                        break;
                     default:
                        this.match(this.input, 33, FOLLOW_EOB_in_block1004);
                        if (forceParens || numAlts > 1) {
                           this.out(")");
                        }

                        this.match(this.input, 3, (BitSet)null);
                        return retval;
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

   public final void alternative() throws RecognitionException {
      try {
         this.match(this.input, 8, FOLLOW_ALT_in_alternative1026);
         this.match(this.input, 2, (BitSet)null);

         while(true) {
            int alt40 = 2;
            int LA40_0 = this.input.LA(1);
            if (LA40_0 == 4 || LA40_0 >= 13 && LA40_0 <= 16 || LA40_0 >= 18 && LA40_0 <= 19 || LA40_0 == 21 || LA40_0 == 29 || LA40_0 == 35 || LA40_0 == 39 || LA40_0 == 41 || LA40_0 == 48 || LA40_0 == 55 || LA40_0 == 57 || LA40_0 >= 63 && LA40_0 <= 64 || LA40_0 == 70 || LA40_0 == 77 || LA40_0 == 80 || LA40_0 == 83 || LA40_0 >= 88 && LA40_0 <= 90 || LA40_0 == 94 || LA40_0 == 96 || LA40_0 == 98) {
               alt40 = 1;
            }

            switch (alt40) {
               case 1:
                  this.pushFollow(FOLLOW_element_in_alternative1028);
                  this.element();
                  --this.state._fsp;
                  break;
               default:
                  this.match(this.input, 32, FOLLOW_EOA_in_alternative1031);
                  this.match(this.input, 3, (BitSet)null);
                  return;
            }
         }
      } catch (RecognitionException var6) {
         this.reportError(var6);
         this.recover(this.input, var6);
      } finally {
         ;
      }
   }

   public final void exceptionGroup() throws RecognitionException {
      try {
         try {
            int alt43 = true;
            int LA43_0 = this.input.LA(1);
            byte alt43;
            if (LA43_0 == 17) {
               alt43 = 1;
            } else {
               if (LA43_0 != 38) {
                  NoViableAltException nvae = new NoViableAltException("", 43, 0, this.input);
                  throw nvae;
               }

               alt43 = 2;
            }

            switch (alt43) {
               case 1:
                  int cnt41 = 0;

                  while(true) {
                     int alt42 = 2;
                     int LA42_0 = this.input.LA(1);
                     if (LA42_0 == 17) {
                        alt42 = 1;
                     }

                     switch (alt42) {
                        case 1:
                           this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup1046);
                           this.exceptionHandler();
                           --this.state._fsp;
                           ++cnt41;
                           break;
                        default:
                           if (cnt41 < 1) {
                              EarlyExitException eee = new EarlyExitException(41, this.input);
                              throw eee;
                           }

                           alt42 = 2;
                           LA42_0 = this.input.LA(1);
                           if (LA42_0 == 38) {
                              alt42 = 1;
                           }

                           switch (alt42) {
                              case 1:
                                 this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1052);
                                 this.finallyClause();
                                 --this.state._fsp;
                                 return;
                              default:
                                 return;
                           }
                     }
                  }
               case 2:
                  this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1059);
                  this.finallyClause();
                  --this.state._fsp;
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
            this.match(this.input, 17, FOLLOW_CATCH_in_exceptionHandler1071);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 12, FOLLOW_ARG_ACTION_in_exceptionHandler1073);
            this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler1075);
            this.match(this.input, 3, (BitSet)null);
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
            this.match(this.input, 38, FOLLOW_FINALLY_in_finallyClause1088);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause1090);
            this.match(this.input, 3, (BitSet)null);
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void rewrite() throws RecognitionException {
      try {
         try {
            int alt45 = true;
            int LA45_0 = this.input.LA(1);
            byte alt45;
            int cnt44;
            if (LA45_0 == 76) {
               cnt44 = this.input.LA(2);
               if (cnt44 == 2) {
                  alt45 = 1;
               } else {
                  if (cnt44 != -1 && cnt44 != 8 && cnt44 != 33) {
                     int nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 45, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt45 = 2;
               }
            } else {
               if (LA45_0 != -1 && LA45_0 != 8 && LA45_0 != 33) {
                  NoViableAltException nvae = new NoViableAltException("", 45, 0, this.input);
                  throw nvae;
               }

               alt45 = 3;
            }

            switch (alt45) {
               case 1:
                  this.match(this.input, 76, FOLLOW_REWRITES_in_rewrite1103);
                  this.match(this.input, 2, (BitSet)null);
                  cnt44 = 0;

                  while(true) {
                     int alt44 = 2;
                     int LA44_0 = this.input.LA(1);
                     if (LA44_0 == 75) {
                        alt44 = 1;
                     }

                     switch (alt44) {
                        case 1:
                           this.pushFollow(FOLLOW_single_rewrite_in_rewrite1105);
                           this.single_rewrite();
                           --this.state._fsp;
                           ++cnt44;
                           break;
                        default:
                           if (cnt44 < 1) {
                              EarlyExitException eee = new EarlyExitException(44, this.input);
                              throw eee;
                           }

                           this.match(this.input, 3, (BitSet)null);
                           return;
                     }
                  }
               case 2:
                  this.match(this.input, 76, FOLLOW_REWRITES_in_rewrite1112);
               case 3:
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
         }

      } finally {
         ;
      }
   }

   public final void single_rewrite() throws RecognitionException {
      GrammarAST SEMPRED1 = null;
      GrammarAST ACTION2 = null;

      try {
         try {
            this.match(this.input, 75, FOLLOW_REWRITE_in_single_rewrite1128);
            this.out(" ->");
            this.match(this.input, 2, (BitSet)null);
            int alt46 = 2;
            int LA46_0 = this.input.LA(1);
            if (LA46_0 == 83) {
               alt46 = 1;
            }

            switch (alt46) {
               case 1:
                  SEMPRED1 = (GrammarAST)this.match(this.input, 83, FOLLOW_SEMPRED_in_single_rewrite1137);
                  this.out(" {" + (SEMPRED1 != null ? SEMPRED1.getText() : null) + "}?");
               default:
                  int alt47 = true;
                  byte alt47;
                  switch (this.input.LA(1)) {
                     case 4:
                        alt47 = 4;
                        break;
                     case 8:
                        alt47 = 1;
                        break;
                     case 37:
                        alt47 = 3;
                        break;
                     case 91:
                        alt47 = 2;
                        break;
                     default:
                        NoViableAltException nvae = new NoViableAltException("", 47, 0, this.input);
                        throw nvae;
                  }

                  switch (alt47) {
                     case 1:
                        this.pushFollow(FOLLOW_alternative_in_single_rewrite1152);
                        this.alternative();
                        --this.state._fsp;
                        break;
                     case 2:
                        this.pushFollow(FOLLOW_rewrite_template_in_single_rewrite1159);
                        this.rewrite_template();
                        --this.state._fsp;
                        break;
                     case 3:
                        this.match(this.input, 37, FOLLOW_ETC_in_single_rewrite1166);
                        this.out("...");
                        break;
                     case 4:
                        ACTION2 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_single_rewrite1175);
                        this.out(" {" + (ACTION2 != null ? ACTION2.getText() : null) + "}");
                  }

                  this.match(this.input, 3, (BitSet)null);
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

      } finally {
         ;
      }
   }

   public final void rewrite_template() throws RecognitionException {
      GrammarAST id = null;
      GrammarAST ind = null;
      GrammarAST arg = null;
      GrammarAST a = null;
      GrammarAST DOUBLE_QUOTE_STRING_LITERAL3 = null;
      GrammarAST DOUBLE_ANGLE_STRING_LITERAL4 = null;

      try {
         try {
            this.match(this.input, 91, FOLLOW_TEMPLATE_in_rewrite_template1199);
            this.match(this.input, 2, (BitSet)null);
            int alt48 = true;
            int LA48_0 = this.input.LA(1);
            byte alt48;
            if (LA48_0 == 43) {
               alt48 = 1;
            } else {
               if (LA48_0 != 4) {
                  NoViableAltException nvae = new NoViableAltException("", 48, 0, this.input);
                  throw nvae;
               }

               alt48 = 2;
            }

            switch (alt48) {
               case 1:
                  id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rewrite_template1208);
                  this.out(" " + (id != null ? id.getText() : null));
                  break;
               case 2:
                  ind = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template1219);
                  this.out(" ({" + (ind != null ? ind.getText() : null) + "})");
            }

            this.match(this.input, 11, FOLLOW_ARGLIST_in_rewrite_template1233);
            this.out("(");
            byte alt50;
            int LA50_0;
            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);

               label179:
               while(true) {
                  alt50 = 2;
                  LA50_0 = this.input.LA(1);
                  if (LA50_0 == 10) {
                     alt50 = 1;
                  }

                  switch (alt50) {
                     case 1:
                        this.match(this.input, 10, FOLLOW_ARG_in_rewrite_template1249);
                        this.match(this.input, 2, (BitSet)null);
                        arg = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rewrite_template1253);
                        this.out((arg != null ? arg.getText() : null) + "=");
                        a = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template1265);
                        this.out(a != null ? a.getText() : null);
                        this.match(this.input, 3, (BitSet)null);
                        break;
                     default:
                        this.out(")");
                        this.match(this.input, 3, (BitSet)null);
                        break label179;
                  }
               }
            }

            alt50 = 3;
            LA50_0 = this.input.LA(1);
            if (LA50_0 == 31) {
               alt50 = 1;
            } else if (LA50_0 == 30) {
               alt50 = 2;
            }

            switch (alt50) {
               case 1:
                  DOUBLE_QUOTE_STRING_LITERAL3 = (GrammarAST)this.match(this.input, 31, FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template1301);
                  this.out(" " + (DOUBLE_QUOTE_STRING_LITERAL3 != null ? DOUBLE_QUOTE_STRING_LITERAL3.getText() : null));
                  break;
               case 2:
                  DOUBLE_ANGLE_STRING_LITERAL4 = (GrammarAST)this.match(this.input, 30, FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template1310);
                  this.out(" " + (DOUBLE_ANGLE_STRING_LITERAL4 != null ? DOUBLE_ANGLE_STRING_LITERAL4.getText() : null));
            }

            this.match(this.input, 3, (BitSet)null);
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.recover(this.input, var14);
         }

      } finally {
         ;
      }
   }

   public final void element() throws RecognitionException {
      GrammarAST id = null;
      GrammarAST id2 = null;
      GrammarAST a = null;
      GrammarAST a2 = null;
      GrammarAST pred = null;
      GrammarAST spred = null;
      GrammarAST gpred = null;

      try {
         try {
            int alt52 = true;
            byte alt52;
            switch (this.input.LA(1)) {
               case 4:
                  alt52 = 12;
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
                  NoViableAltException nvae = new NoViableAltException("", 52, 0, this.input);
                  throw nvae;
               case 13:
                  alt52 = 7;
                  break;
               case 14:
                  alt52 = 16;
                  break;
               case 15:
                  alt52 = 2;
                  break;
               case 16:
               case 21:
               case 57:
               case 64:
                  alt52 = 9;
                  break;
               case 18:
               case 29:
               case 48:
               case 80:
               case 88:
               case 94:
               case 98:
                  alt52 = 3;
                  break;
               case 19:
                  alt52 = 6;
                  break;
               case 35:
                  alt52 = 18;
                  break;
               case 39:
                  alt52 = 13;
                  break;
               case 41:
                  alt52 = 17;
                  break;
               case 55:
                  alt52 = 4;
                  break;
               case 63:
                  alt52 = 8;
                  break;
               case 70:
                  alt52 = 5;
                  break;
               case 77:
                  alt52 = 1;
                  break;
               case 83:
                  alt52 = 14;
                  break;
               case 89:
                  alt52 = 11;
                  break;
               case 90:
                  alt52 = 15;
                  break;
               case 96:
                  alt52 = 10;
            }

            switch (alt52) {
               case 1:
                  this.match(this.input, 77, FOLLOW_ROOT_in_element1334);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_element_in_element1336);
                  this.element();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.out("^");
                  break;
               case 2:
                  this.match(this.input, 15, FOLLOW_BANG_in_element1345);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_element_in_element1347);
                  this.element();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.out("!");
                  break;
               case 3:
                  this.pushFollow(FOLLOW_atom_in_element1355);
                  this.atom();
                  --this.state._fsp;
                  break;
               case 4:
                  this.match(this.input, 55, FOLLOW_NOT_in_element1361);
                  this.out("~");
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_element_in_element1365);
                  this.element();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 5:
                  this.match(this.input, 70, FOLLOW_RANGE_in_element1372);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_atom_in_element1374);
                  this.atom();
                  --this.state._fsp;
                  this.out("..");
                  this.pushFollow(FOLLOW_atom_in_element1378);
                  this.atom();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 6:
                  this.match(this.input, 19, FOLLOW_CHAR_RANGE_in_element1385);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_atom_in_element1387);
                  this.atom();
                  --this.state._fsp;
                  this.out("..");
                  this.pushFollow(FOLLOW_atom_in_element1391);
                  this.atom();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 7:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_element1398);
                  this.match(this.input, 2, (BitSet)null);
                  id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_element1402);
                  this.out((id != null ? id.getText() : null) + "=");
                  this.pushFollow(FOLLOW_element_in_element1406);
                  this.element();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 8:
                  this.match(this.input, 63, FOLLOW_PLUS_ASSIGN_in_element1413);
                  this.match(this.input, 2, (BitSet)null);
                  id2 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_element1417);
                  this.out((id2 != null ? id2.getText() : null) + "+=");
                  this.pushFollow(FOLLOW_element_in_element1421);
                  this.element();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 9:
                  this.pushFollow(FOLLOW_ebnf_in_element1427);
                  this.ebnf();
                  --this.state._fsp;
                  break;
               case 10:
                  this.pushFollow(FOLLOW_tree__in_element1432);
                  this.tree_();
                  --this.state._fsp;
                  break;
               case 11:
                  this.match(this.input, 89, FOLLOW_SYNPRED_in_element1439);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_element1441);
                  this.block(true);
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.out("=>");
                  break;
               case 12:
                  a = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_element1453);
                  if (this.showActions) {
                     this.out("{");
                     this.out(a != null ? a.getText() : null);
                     this.out("}");
                  }
                  break;
               case 13:
                  a2 = (GrammarAST)this.match(this.input, 39, FOLLOW_FORCED_ACTION_in_element1463);
                  if (this.showActions) {
                     this.out("{{");
                     this.out(a2 != null ? a2.getText() : null);
                     this.out("}}");
                  }
                  break;
               case 14:
                  pred = (GrammarAST)this.match(this.input, 83, FOLLOW_SEMPRED_in_element1473);
                  if (this.showActions) {
                     this.out("{");
                     this.out(pred != null ? pred.getText() : null);
                     this.out("}?");
                  } else {
                     this.out("{...}?");
                  }
                  break;
               case 15:
                  spred = (GrammarAST)this.match(this.input, 90, FOLLOW_SYN_SEMPRED_in_element1484);
                  String name = spred != null ? spred.getText() : null;
                  GrammarAST predAST = this.grammar.getSyntacticPredicate(name);
                  this.block(predAST, true);
                  this.out("=>");
                  break;
               case 16:
                  this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_element1494);
                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);

                     while(true) {
                        int alt51 = 2;
                        int LA51_0 = this.input.LA(1);
                        if (LA51_0 >= 4 && LA51_0 <= 102) {
                           alt51 = 1;
                        } else if (LA51_0 == 3) {
                           alt51 = 2;
                        }

                        switch (alt51) {
                           case 1:
                              this.matchAny(this.input);
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              return;
                        }
                     }
                  }
                  break;
               case 17:
                  gpred = (GrammarAST)this.match(this.input, 41, FOLLOW_GATED_SEMPRED_in_element1506);
                  if (this.showActions) {
                     this.out("{");
                     this.out(gpred != null ? gpred.getText() : null);
                     this.out("}? =>");
                  } else {
                     this.out("{...}? =>");
                  }
                  break;
               case 18:
                  this.match(this.input, 35, FOLLOW_EPSILON_in_element1515);
            }
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.recover(this.input, var14);
         }

      } finally {
         ;
      }
   }

   public final void ebnf() throws RecognitionException {
      try {
         try {
            int alt53 = true;
            byte alt53;
            switch (this.input.LA(1)) {
               case 16:
                  alt53 = 1;
                  break;
               case 21:
                  alt53 = 3;
                  break;
               case 57:
                  alt53 = 2;
                  break;
               case 64:
                  alt53 = 4;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 53, 0, this.input);
                  throw nvae;
            }

            switch (alt53) {
               case 1:
                  this.pushFollow(FOLLOW_block_in_ebnf1526);
                  this.block(true);
                  --this.state._fsp;
                  this.out(" ");
                  break;
               case 2:
                  this.match(this.input, 57, FOLLOW_OPTIONAL_in_ebnf1536);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_ebnf1538);
                  this.block(true);
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.out("? ");
                  break;
               case 3:
                  this.match(this.input, 21, FOLLOW_CLOSURE_in_ebnf1550);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_ebnf1552);
                  this.block(true);
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.out("* ");
                  break;
               case 4:
                  this.match(this.input, 64, FOLLOW_POSITIVE_CLOSURE_in_ebnf1565);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_ebnf1567);
                  this.block(true);
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.out("+ ");
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void tree_() throws RecognitionException {
      try {
         this.match(this.input, 96, FOLLOW_TREE_BEGIN_in_tree_1584);
         this.out(" ^(");
         this.match(this.input, 2, (BitSet)null);
         this.pushFollow(FOLLOW_element_in_tree_1588);
         this.element();
         --this.state._fsp;

         while(true) {
            int alt54 = 2;
            int LA54_0 = this.input.LA(1);
            if (LA54_0 == 4 || LA54_0 >= 13 && LA54_0 <= 16 || LA54_0 >= 18 && LA54_0 <= 19 || LA54_0 == 21 || LA54_0 == 29 || LA54_0 == 35 || LA54_0 == 39 || LA54_0 == 41 || LA54_0 == 48 || LA54_0 == 55 || LA54_0 == 57 || LA54_0 >= 63 && LA54_0 <= 64 || LA54_0 == 70 || LA54_0 == 77 || LA54_0 == 80 || LA54_0 == 83 || LA54_0 >= 88 && LA54_0 <= 90 || LA54_0 == 94 || LA54_0 == 96 || LA54_0 == 98) {
               alt54 = 1;
            }

            switch (alt54) {
               case 1:
                  this.pushFollow(FOLLOW_element_in_tree_1591);
                  this.element();
                  --this.state._fsp;
                  break;
               default:
                  this.out(") ");
                  this.match(this.input, 3, (BitSet)null);
                  return;
            }
         }
      } catch (RecognitionException var6) {
         this.reportError(var6);
         this.recover(this.input, var6);
      } finally {
         ;
      }
   }

   public final atom_return atom() throws RecognitionException {
      atom_return retval = new atom_return();
      retval.start = this.input.LT(1);
      GrammarAST rarg = null;
      GrammarAST targ = null;
      GrammarAST LABEL5 = null;
      GrammarAST ID6 = null;
      this.out(" ");

      try {
         try {
            int alt63 = true;
            byte alt63;
            switch (this.input.LA(1)) {
               case 18:
               case 80:
               case 88:
               case 94:
               case 98:
                  alt63 = 1;
                  break;
               case 29:
                  alt63 = 3;
                  break;
               case 48:
                  alt63 = 2;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 63, 0, this.input);
                  throw nvae;
            }

            switch (alt63) {
               case 1:
                  int alt62 = true;
                  byte alt62;
                  switch (this.input.LA(1)) {
                     case 18:
                        alt62 = 3;
                        break;
                     case 80:
                        alt62 = 1;
                        break;
                     case 88:
                        alt62 = 4;
                        break;
                     case 94:
                        alt62 = 2;
                        break;
                     case 98:
                        alt62 = 5;
                        break;
                     default:
                        NoViableAltException nvae = new NoViableAltException("", 62, 0, this.input);
                        throw nvae;
                  }

                  byte alt61;
                  int LA61_0;
                  byte alt58;
                  int LA58_0;
                  label291:
                  switch (alt62) {
                     case 1:
                        this.match(this.input, 80, FOLLOW_RULE_REF_in_atom1617);
                        this.out(((GrammarAST)retval.start).toString());
                        if (this.input.LA(1) != 2) {
                           break;
                        }

                        this.match(this.input, 2, (BitSet)null);
                        alt61 = 2;
                        LA61_0 = this.input.LA(1);
                        if (LA61_0 == 12) {
                           alt61 = 1;
                        }

                        switch (alt61) {
                           case 1:
                              rarg = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1629);
                              this.out("[" + rarg.toString() + "]");
                        }

                        alt58 = 2;
                        LA58_0 = this.input.LA(1);
                        if (LA58_0 == 15 || LA58_0 == 77) {
                           alt58 = 1;
                        }

                        switch (alt58) {
                           case 1:
                              this.pushFollow(FOLLOW_ast_suffix_in_atom1640);
                              this.ast_suffix();
                              --this.state._fsp;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              break label291;
                        }
                     case 2:
                        this.match(this.input, 94, FOLLOW_TOKEN_REF_in_atom1655);
                        this.out(((GrammarAST)retval.start).toString());
                        if (this.input.LA(1) != 2) {
                           break;
                        }

                        this.match(this.input, 2, (BitSet)null);
                        alt61 = 2;
                        LA61_0 = this.input.LA(1);
                        if (LA61_0 == 12) {
                           alt61 = 1;
                        }

                        switch (alt61) {
                           case 1:
                              targ = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1667);
                              this.out("[" + targ.toString() + "]");
                        }

                        alt58 = 2;
                        LA58_0 = this.input.LA(1);
                        if (LA58_0 == 15 || LA58_0 == 77) {
                           alt58 = 1;
                        }

                        switch (alt58) {
                           case 1:
                              this.pushFollow(FOLLOW_ast_suffix_in_atom1679);
                              this.ast_suffix();
                              --this.state._fsp;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              break label291;
                        }
                     case 3:
                        this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_atom1694);
                        this.out(((GrammarAST)retval.start).toString());
                        if (this.input.LA(1) != 2) {
                           break;
                        }

                        this.match(this.input, 2, (BitSet)null);
                        alt61 = 2;
                        LA61_0 = this.input.LA(1);
                        if (LA61_0 == 15 || LA61_0 == 77) {
                           alt61 = 1;
                        }

                        switch (alt61) {
                           case 1:
                              this.pushFollow(FOLLOW_ast_suffix_in_atom1703);
                              this.ast_suffix();
                              --this.state._fsp;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              break label291;
                        }
                     case 4:
                        this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_atom1718);
                        this.out(((GrammarAST)retval.start).toString());
                        if (this.input.LA(1) != 2) {
                           break;
                        }

                        this.match(this.input, 2, (BitSet)null);
                        alt61 = 2;
                        LA61_0 = this.input.LA(1);
                        if (LA61_0 == 15 || LA61_0 == 77) {
                           alt61 = 1;
                        }

                        switch (alt61) {
                           case 1:
                              this.pushFollow(FOLLOW_ast_suffix_in_atom1727);
                              this.ast_suffix();
                              --this.state._fsp;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              break label291;
                        }
                     case 5:
                        this.match(this.input, 98, FOLLOW_WILDCARD_in_atom1742);
                        this.out(((GrammarAST)retval.start).toString());
                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           alt61 = 2;
                           LA61_0 = this.input.LA(1);
                           if (LA61_0 == 15 || LA61_0 == 77) {
                              alt61 = 1;
                           }

                           switch (alt61) {
                              case 1:
                                 this.pushFollow(FOLLOW_ast_suffix_in_atom1752);
                                 this.ast_suffix();
                                 --this.state._fsp;
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                           }
                        }
                  }

                  this.out(" ");
                  break;
               case 2:
                  LABEL5 = (GrammarAST)this.match(this.input, 48, FOLLOW_LABEL_in_atom1772);
                  this.out(" $" + (LABEL5 != null ? LABEL5.getText() : null));
                  break;
               case 3:
                  this.match(this.input, 29, FOLLOW_DOT_in_atom1781);
                  this.match(this.input, 2, (BitSet)null);
                  ID6 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_atom1783);
                  this.out((ID6 != null ? ID6.getText() : null) + ".");
                  this.pushFollow(FOLLOW_atom_in_atom1787);
                  this.atom();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void ast_suffix() throws RecognitionException {
      try {
         try {
            int alt64 = true;
            int LA64_0 = this.input.LA(1);
            byte alt64;
            if (LA64_0 == 77) {
               alt64 = 1;
            } else {
               if (LA64_0 != 15) {
                  NoViableAltException nvae = new NoViableAltException("", 64, 0, this.input);
                  throw nvae;
               }

               alt64 = 2;
            }

            switch (alt64) {
               case 1:
                  this.match(this.input, 77, FOLLOW_ROOT_in_ast_suffix1800);
                  this.out("^");
                  break;
               case 2:
                  this.match(this.input, 15, FOLLOW_BANG_in_ast_suffix1807);
                  this.out("!");
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public static class atom_return extends TreeRuleReturnScope {
   }

   public static class block_return extends TreeRuleReturnScope {
   }

   public static class modifier_return extends TreeRuleReturnScope {
   }
}
