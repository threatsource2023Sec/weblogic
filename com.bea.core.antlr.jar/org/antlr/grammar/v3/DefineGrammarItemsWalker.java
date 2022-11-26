package org.antlr.grammar.v3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.antlr.tool.AttributeScope;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;
import org.antlr.tool.Rule;

public class DefineGrammarItemsWalker extends TreeParser {
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
   protected Stack AttributeScopeActions_stack;
   protected Grammar grammar;
   protected GrammarAST root;
   protected String currentRuleName;
   protected GrammarAST currentRewriteBlock;
   protected GrammarAST currentRewriteRule;
   protected int outerAltNum;
   protected int blockLevel;
   public static final BitSet FOLLOW_LEXER_GRAMMAR_in_grammar_76 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_82 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PARSER_GRAMMAR_in_grammar_91 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_96 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_GRAMMAR_in_grammar_105 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_110 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_COMBINED_GRAMMAR_in_grammar_119 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_124 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_SCOPE_in_attrScope149 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_attrScope153 = new BitSet(new long[]{528L});
   public static final BitSet FOLLOW_attrScopeAction_in_attrScope155 = new BitSet(new long[]{528L});
   public static final BitSet FOLLOW_ACTION_in_attrScope160 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_AMPERSAND_in_attrScopeAction178 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_attrScopeAction180 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_attrScopeAction182 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_grammarSpec200 = new BitSet(new long[]{288265560658018816L, 537034754L});
   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarSpec207 = new BitSet(new long[]{288265560523801088L, 537034754L});
   public static final BitSet FOLLOW_optionsSpec_in_grammarSpec215 = new BitSet(new long[]{35184372089344L, 537034754L});
   public static final BitSet FOLLOW_delegateGrammars_in_grammarSpec223 = new BitSet(new long[]{512L, 537034754L});
   public static final BitSet FOLLOW_tokensSpec_in_grammarSpec230 = new BitSet(new long[]{512L, 163842L});
   public static final BitSet FOLLOW_attrScope_in_grammarSpec237 = new BitSet(new long[]{512L, 163842L});
   public static final BitSet FOLLOW_actions_in_grammarSpec244 = new BitSet(new long[]{0L, 32770L});
   public static final BitSet FOLLOW_rules_in_grammarSpec250 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_action_in_actions263 = new BitSet(new long[]{514L});
   public static final BitSet FOLLOW_AMPERSAND_in_action285 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_action289 = new BitSet(new long[]{8796093022224L});
   public static final BitSet FOLLOW_ID_in_action298 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_action302 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ACTION_in_action318 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec352 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_IMPORT_in_delegateGrammars369 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ASSIGN_in_delegateGrammars374 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_delegateGrammars376 = new BitSet(new long[]{8796093022208L});
   public static final BitSet FOLLOW_ID_in_delegateGrammars378 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_delegateGrammars383 = new BitSet(new long[]{8796093030408L});
   public static final BitSet FOLLOW_TOKENS_in_tokensSpec400 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_tokenSpec_in_tokensSpec402 = new BitSet(new long[]{8200L, 1073741824L});
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec417 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ASSIGN_in_tokenSpec424 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec429 = new BitSet(new long[]{262144L, 16777216L});
   public static final BitSet FOLLOW_set_in_tokenSpec434 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_rule_in_rules465 = new BitSet(new long[]{2L, 32770L});
   public static final BitSet FOLLOW_PREC_RULE_in_rules470 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_RULE_in_rule495 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rule499 = new BitSet(new long[]{1099511628800L, 28L});
   public static final BitSet FOLLOW_modifier_in_rule507 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_ARG_in_rule516 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rule521 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RET_in_rule532 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rule537 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_throwsSpec_in_rule547 = new BitSet(new long[]{288230376151777792L, 131072L});
   public static final BitSet FOLLOW_optionsSpec_in_rule555 = new BitSet(new long[]{66048L, 131072L});
   public static final BitSet FOLLOW_ruleScopeSpec_in_rule568 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_ruleAction_in_rule577 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_block_in_rule592 = new BitSet(new long[]{292057907200L});
   public static final BitSet FOLLOW_exceptionGroup_in_rule598 = new BitSet(new long[]{17179869184L});
   public static final BitSet FOLLOW_EOR_in_rule605 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_AMPERSAND_in_ruleAction629 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_ruleAction633 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_ruleAction637 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_THROWS_in_throwsSpec697 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_throwsSpec700 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec730 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_attrScopeAction_in_ruleScopeSpec737 = new BitSet(new long[]{528L});
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec742 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_ID_in_ruleScopeSpec763 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_BLOCK_in_block797 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_optionsSpec_in_block803 = new BitSet(new long[]{768L});
   public static final BitSet FOLLOW_blockAction_in_block811 = new BitSet(new long[]{768L});
   public static final BitSet FOLLOW_alternative_in_block820 = new BitSet(new long[]{8589934848L, 4096L});
   public static final BitSet FOLLOW_rewrite_in_block822 = new BitSet(new long[]{8589934848L});
   public static final BitSet FOLLOW_EOB_in_block839 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_AMPERSAND_in_blockAction863 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_blockAction867 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_blockAction871 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_alternative909 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_alternative912 = new BitSet(new long[]{-9043225263786303472L, 22666616897L});
   public static final BitSet FOLLOW_EOA_in_alternative916 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup931 = new BitSet(new long[]{274878038018L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup937 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup944 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CATCH_in_exceptionHandler958 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler960 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_exceptionHandler962 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_FINALLY_in_finallyClause980 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_finallyClause982 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ROOT_in_element999 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element1001 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BANG_in_element1010 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element1012 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_atom_in_element1020 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NOT_in_element1029 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element1031 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RANGE_in_element1040 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_atom_in_element1042 = new BitSet(new long[]{537133056L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_element1045 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_RANGE_in_element1055 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_atom_in_element1057 = new BitSet(new long[]{537133056L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_element1060 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ASSIGN_in_element1069 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element1073 = new BitSet(new long[]{-9043225268081270768L, 22666616897L});
   public static final BitSet FOLLOW_element_in_element1077 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PLUS_ASSIGN_in_element1090 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element1094 = new BitSet(new long[]{-9043225268081270768L, 22666616897L});
   public static final BitSet FOLLOW_element_in_element1098 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ebnf_in_element1115 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_tree__in_element1122 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYNPRED_in_element1131 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_element1133 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ACTION_in_element1144 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FORCED_ACTION_in_element1157 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SEMPRED_in_element1168 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYN_SEMPRED_in_element1179 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_element1187 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_GATED_SEMPRED_in_element1198 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_EPSILON_in_element1209 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_dotLoop_in_ebnf1227 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_block_in_ebnf1233 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPTIONAL_in_ebnf1240 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1242 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CLOSURE_in_ebnf1251 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1253 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_ebnf1262 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1264 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CLOSURE_in_dotLoop1283 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_dotBlock_in_dotLoop1285 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_dotLoop1295 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_dotBlock_in_dotLoop1297 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BLOCK_in_dotBlock1320 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ALT_in_dotBlock1324 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_WILDCARD_in_dotBlock1326 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_dotBlock1328 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_EOB_in_dotBlock1332 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_BEGIN_in_tree_1346 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_tree_1348 = new BitSet(new long[]{-9043225268081270760L, 22666616897L});
   public static final BitSet FOLLOW_RULE_REF_in_atom1366 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1371 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TOKEN_REF_in_atom1388 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1393 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom1409 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_atom1420 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_WILDCARD_in_atom1430 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_DOT_in_atom1436 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_atom1438 = new BitSet(new long[]{537133056L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_atom1440 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_REWRITES_in_rewrite1477 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_REWRITE_in_rewrite1486 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_SEMPRED_in_rewrite1491 = new BitSet(new long[]{137438953744L, 134217728L});
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite1495 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BLOCK_in_rewrite_block1539 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite_block1541 = new BitSet(new long[]{8589934592L});
   public static final BitSet FOLLOW_EOB_in_rewrite_block1543 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_rewrite_alternative1575 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_element_in_rewrite_alternative1581 = new BitSet(new long[]{144396667349893136L, 5385551873L});
   public static final BitSet FOLLOW_EPSILON_in_rewrite_alternative1588 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_rewrite_alternative1592 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_rewrite_template_in_rewrite_alternative1603 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ETC_in_rewrite_alternative1608 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_atom_in_rewrite_element1622 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_ebnf_in_rewrite_element1627 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_rewrite_tree_in_rewrite_element1632 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPTIONAL_in_rewrite_ebnf1645 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_block_in_rewrite_ebnf1647 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CLOSURE_in_rewrite_ebnf1656 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_block_in_rewrite_ebnf1658 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_rewrite_ebnf1667 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_block_in_rewrite_ebnf1669 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_BEGIN_in_rewrite_tree1686 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rewrite_atom_in_rewrite_tree1688 = new BitSet(new long[]{144396663054925848L, 5385551873L});
   public static final BitSet FOLLOW_rewrite_element_in_rewrite_tree1692 = new BitSet(new long[]{144396663054925848L, 5385551873L});
   public static final BitSet FOLLOW_RULE_REF_in_rewrite_atom1713 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_atom1723 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rewrite_atom1731 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_rewrite_atom1756 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_rewrite_atom1762 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LABEL_in_rewrite_atom1771 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_atom1776 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ALT_in_rewrite_template1793 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_EPSILON_in_rewrite_template1795 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_rewrite_template1797 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TEMPLATE_in_rewrite_template1806 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rewrite_template1811 = new BitSet(new long[]{2048L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_template1815 = new BitSet(new long[]{2048L});
   public static final BitSet FOLLOW_ARGLIST_in_rewrite_template1823 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_in_rewrite_template1833 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rewrite_template1837 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_template1841 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ACTION_in_rewrite_template1898 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_dotLoop_in_synpred1_DefineGrammarItemsWalker1222 = new BitSet(new long[]{2L});

   public TreeParser[] getDelegates() {
      return new TreeParser[0];
   }

   public DefineGrammarItemsWalker(TreeNodeStream input) {
      this(input, new RecognizerSharedState());
   }

   public DefineGrammarItemsWalker(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
      this.AttributeScopeActions_stack = new Stack();
      this.outerAltNum = 0;
      this.blockLevel = 0;
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\antlr\\grammar\\v3\\DefineGrammarItemsWalker.g";
   }

   public final int countAltsForRule(CommonTree t) {
      CommonTree block = (CommonTree)t.getFirstChildWithType(16);
      int altCount = 0;

      for(int i = 0; i < block.getChildCount(); ++i) {
         if (block.getChild(i).getType() == 8) {
            ++altCount;
         }
      }

      return altCount;
   }

   protected final void finish() {
      this.trimGrammar();
   }

   protected final void trimGrammar() {
      if (this.grammar.type == 4) {
         GrammarAST p;
         for(p = this.root; !p.getText().equals("grammar"); p = p.getNextSibling()) {
         }

         for(int i = 0; i < p.getChildCount(); ++i) {
            if (p.getChild(i).getType() == 79) {
               String ruleName = p.getChild(i).getChild(0).getText();
               if (Rule.getRuleType(ruleName) == 1) {
                  p.deleteChild(i);
                  --i;
               }
            }
         }

      }
   }

   protected final void trackInlineAction(GrammarAST actionAST) {
      Rule r = this.grammar.getRule(this.currentRuleName);
      if (r != null) {
         r.trackInlineAction(actionAST);
      }

   }

   public final grammar__return grammar_(Grammar g) throws RecognitionException {
      grammar__return retval = new grammar__return();
      retval.start = this.input.LT(1);
      this.grammar = g;
      this.root = (GrammarAST)retval.start;

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
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 1, 0, this.input);
                  throw nvae;
            }

            switch (alt1) {
               case 1:
                  this.match(this.input, 50, FOLLOW_LEXER_GRAMMAR_in_grammar_76);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.grammar.type = 1;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_82);
                  this.grammarSpec();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 2:
                  this.match(this.input, 61, FOLLOW_PARSER_GRAMMAR_in_grammar_91);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.grammar.type = 2;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_96);
                  this.grammarSpec();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 3:
                  this.match(this.input, 97, FOLLOW_TREE_GRAMMAR_in_grammar_105);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.grammar.type = 3;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_110);
                  this.grammarSpec();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 4:
                  this.match(this.input, 23, FOLLOW_COMBINED_GRAMMAR_in_grammar_119);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.grammar.type = 4;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_124);
                  this.grammarSpec();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
            }

            if (this.state.backtracking == 0) {
               this.finish();
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void attrScope() throws RecognitionException {
      this.AttributeScopeActions_stack.push(new AttributeScopeActions_scope());
      GrammarAST name = null;
      GrammarAST attrs = null;
      ((AttributeScopeActions_scope)this.AttributeScopeActions_stack.peek()).actions = new HashMap();

      try {
         this.match(this.input, 81, FOLLOW_SCOPE_in_attrScope149);
         if (this.state.failed) {
            return;
         }

         this.match(this.input, 2, (BitSet)null);
         if (this.state.failed) {
            return;
         }

         name = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_attrScope153);
         if (!this.state.failed) {
            while(true) {
               int alt2 = 2;
               int LA2_0 = this.input.LA(1);
               if (LA2_0 == 9) {
                  alt2 = 1;
               }

               switch (alt2) {
                  case 1:
                     this.pushFollow(FOLLOW_attrScopeAction_in_attrScope155);
                     this.attrScopeAction();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  default:
                     attrs = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_attrScope160);
                     if (this.state.failed) {
                        return;
                     }

                     this.match(this.input, 3, (BitSet)null);
                     if (!this.state.failed) {
                        if (this.state.backtracking == 0) {
                           AttributeScope scope = this.grammar.defineGlobalScope(name != null ? name.getText() : null, attrs.getToken());
                           scope.isDynamicGlobalScope = true;
                           scope.addAttributes(attrs != null ? attrs.getText() : null, 59);
                           Iterator i$ = ((AttributeScopeActions_scope)this.AttributeScopeActions_stack.peek()).actions.keySet().iterator();

                           while(i$.hasNext()) {
                              GrammarAST action = (GrammarAST)i$.next();
                              scope.defineNamedAction(action, (GrammarAST)((AttributeScopeActions_scope)this.AttributeScopeActions_stack.peek()).actions.get(action));
                           }
                        }

                        return;
                     }

                     return;
               }
            }
         }
      } catch (RecognitionException var9) {
         this.reportError(var9);
         this.recover(this.input, var9);
         return;
      } finally {
         this.AttributeScopeActions_stack.pop();
      }

   }

   public final void attrScopeAction() throws RecognitionException {
      GrammarAST ID1 = null;
      GrammarAST ACTION2 = null;

      try {
         try {
            this.match(this.input, 9, FOLLOW_AMPERSAND_in_attrScopeAction178);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            ID1 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_attrScopeAction180);
            if (this.state.failed) {
               return;
            }

            ACTION2 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_attrScopeAction182);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            if (this.state.backtracking == 0) {
               ((AttributeScopeActions_scope)this.AttributeScopeActions_stack.peek()).actions.put(ID1, ACTION2);
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void grammarSpec() throws RecognitionException {
      GrammarAST id = null;
      GrammarAST cmt = null;

      try {
         id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_grammarSpec200);
         if (!this.state.failed) {
            int alt3 = 2;
            int LA3_0 = this.input.LA(1);
            if (LA3_0 == 27) {
               alt3 = 1;
            }

            switch (alt3) {
               case 1:
                  cmt = (GrammarAST)this.match(this.input, 27, FOLLOW_DOC_COMMENT_in_grammarSpec207);
                  if (this.state.failed) {
                     return;
                  }
               default:
                  int alt4 = 2;
                  int LA4_0 = this.input.LA(1);
                  if (LA4_0 == 58) {
                     alt4 = 1;
                  }

                  switch (alt4) {
                     case 1:
                        this.pushFollow(FOLLOW_optionsSpec_in_grammarSpec215);
                        this.optionsSpec();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }
                     default:
                        int alt5 = 2;
                        int LA5_0 = this.input.LA(1);
                        if (LA5_0 == 45) {
                           alt5 = 1;
                        }

                        switch (alt5) {
                           case 1:
                              this.pushFollow(FOLLOW_delegateGrammars_in_grammarSpec223);
                              this.delegateGrammars();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return;
                              }
                           default:
                              int alt6 = 2;
                              int LA6_0 = this.input.LA(1);
                              if (LA6_0 == 93) {
                                 alt6 = 1;
                              }

                              switch (alt6) {
                                 case 1:
                                    this.pushFollow(FOLLOW_tokensSpec_in_grammarSpec230);
                                    this.tokensSpec();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return;
                                    }
                              }
                        }
                  }
            }

            while(true) {
               int alt8 = 2;
               int LA8_0 = this.input.LA(1);
               if (LA8_0 == 81) {
                  alt8 = 1;
               }

               switch (alt8) {
                  case 1:
                     this.pushFollow(FOLLOW_attrScope_in_grammarSpec237);
                     this.attrScope();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                     break;
                  default:
                     alt8 = 2;
                     LA8_0 = this.input.LA(1);
                     if (LA8_0 == 9) {
                        alt8 = 1;
                     }

                     switch (alt8) {
                        case 1:
                           this.pushFollow(FOLLOW_actions_in_grammarSpec244);
                           this.actions();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           this.pushFollow(FOLLOW_rules_in_grammarSpec250);
                           this.rules();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           return;
                     }
               }
            }
         }
      } catch (RecognitionException var16) {
         this.reportError(var16);
         this.recover(this.input, var16);
      } finally {
         ;
      }
   }

   public final void actions() throws RecognitionException {
      try {
         int cnt9 = 0;

         while(true) {
            int alt9 = 2;
            int LA9_0 = this.input.LA(1);
            if (LA9_0 == 9) {
               alt9 = 1;
            }

            switch (alt9) {
               case 1:
                  this.pushFollow(FOLLOW_action_in_actions263);
                  this.action();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  ++cnt9;
                  break;
               default:
                  if (cnt9 < 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     EarlyExitException eee = new EarlyExitException(9, this.input);
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
      GrammarAST amp = null;
      GrammarAST id1 = null;
      GrammarAST id2 = null;
      GrammarAST a1 = null;
      GrammarAST a2 = null;
      String scope = null;
      GrammarAST nameAST = null;
      GrammarAST actionAST = null;

      try {
         try {
            amp = (GrammarAST)this.match(this.input, 9, FOLLOW_AMPERSAND_in_action285);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            id1 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_action289);
            if (this.state.failed) {
               return;
            }

            int alt10 = true;
            int LA10_0 = this.input.LA(1);
            byte alt10;
            if (LA10_0 == 43) {
               alt10 = 1;
            } else {
               if (LA10_0 != 4) {
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
                  id2 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_action298);
                  if (this.state.failed) {
                     return;
                  }

                  a1 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_action302);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     scope = id1 != null ? id1.getText() : null;
                     nameAST = id2;
                     actionAST = a1;
                  }
                  break;
               case 2:
                  a2 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_action318);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     scope = null;
                     nameAST = id1;
                     actionAST = a2;
                  }
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            if (this.state.backtracking == 0) {
               this.grammar.defineNamedAction(amp, scope, nameAST, actionAST);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
         }

      } finally {
         ;
      }
   }

   public final void optionsSpec() throws RecognitionException {
      try {
         try {
            this.match(this.input, 58, FOLLOW_OPTIONS_in_optionsSpec352);
            if (this.state.failed) {
               return;
            }

            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);
               if (this.state.failed) {
                  return;
               }

               while(true) {
                  int alt11 = 2;
                  int LA11_0 = this.input.LA(1);
                  if (LA11_0 >= 4 && LA11_0 <= 102) {
                     alt11 = 1;
                  } else if (LA11_0 == 3) {
                     alt11 = 2;
                  }

                  switch (alt11) {
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

   public final void delegateGrammars() throws RecognitionException {
      try {
         this.match(this.input, 45, FOLLOW_IMPORT_in_delegateGrammars369);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int cnt12 = 0;

               while(true) {
                  int alt12 = 3;
                  int LA12_0 = this.input.LA(1);
                  if (LA12_0 == 13) {
                     alt12 = 1;
                  } else if (LA12_0 == 43) {
                     alt12 = 2;
                  }

                  switch (alt12) {
                     case 1:
                        this.match(this.input, 13, FOLLOW_ASSIGN_in_delegateGrammars374);
                        if (this.state.failed) {
                           return;
                        }

                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        this.match(this.input, 43, FOLLOW_ID_in_delegateGrammars376);
                        if (this.state.failed) {
                           return;
                        }

                        this.match(this.input, 43, FOLLOW_ID_in_delegateGrammars378);
                        if (this.state.failed) {
                           return;
                        }

                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     case 2:
                        this.match(this.input, 43, FOLLOW_ID_in_delegateGrammars383);
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     default:
                        if (cnt12 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(12, this.input);
                           throw eee;
                        }

                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        return;
                  }

                  ++cnt12;
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

   public final void tokensSpec() throws RecognitionException {
      try {
         try {
            this.match(this.input, 93, FOLLOW_TOKENS_in_tokensSpec400);
            if (this.state.failed) {
               return;
            }

            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);
               if (this.state.failed) {
                  return;
               }

               while(true) {
                  int alt13 = 2;
                  int LA13_0 = this.input.LA(1);
                  if (LA13_0 == 13 || LA13_0 == 94) {
                     alt13 = 1;
                  }

                  switch (alt13) {
                     case 1:
                        this.pushFollow(FOLLOW_tokenSpec_in_tokensSpec402);
                        this.tokenSpec();
                        --this.state._fsp;
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
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void tokenSpec() throws RecognitionException {
      GrammarAST t = null;

      try {
         try {
            int alt14 = true;
            int LA14_0 = this.input.LA(1);
            byte alt14;
            if (LA14_0 == 94) {
               alt14 = 1;
            } else {
               if (LA14_0 != 13) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
                  throw nvae;
               }

               alt14 = 2;
            }

            switch (alt14) {
               case 1:
                  t = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_tokenSpec417);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_tokenSpec424);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 94, FOLLOW_TOKEN_REF_in_tokenSpec429);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) != 18 && this.input.LA(1) != 88) {
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
                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
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

   public final void rules() throws RecognitionException {
      try {
         int cnt16 = 0;

         while(true) {
            int alt16 = 3;
            int LA16_0 = this.input.LA(1);
            if (LA16_0 == 79) {
               alt16 = 1;
            } else if (LA16_0 == 65) {
               alt16 = 2;
            }

            label147:
            switch (alt16) {
               case 1:
                  this.pushFollow(FOLLOW_rule_in_rules465);
                  this.rule();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.match(this.input, 65, FOLLOW_PREC_RULE_in_rules470);
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
                     int alt15 = 2;
                     int LA15_0 = this.input.LA(1);
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
                           break label147;
                     }
                  }
               default:
                  if (cnt16 < 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     EarlyExitException eee = new EarlyExitException(16, this.input);
                     throw eee;
                  }

                  return;
            }

            ++cnt16;
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
      GrammarAST args = null;
      GrammarAST ret = null;
      GrammarAST RULE3 = null;
      TreeRuleReturnScope b = null;
      TreeRuleReturnScope modifier4 = null;
      HashSet throwsSpec5 = null;
      String name = null;
      Map opts = null;
      Rule r = null;

      try {
         RULE3 = (GrammarAST)this.match(this.input, 79, FOLLOW_RULE_in_rule495);
         if (this.state.failed) {
            return retval;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return retval;
            } else {
               id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rule499);
               if (this.state.failed) {
                  return retval;
               } else {
                  if (this.state.backtracking == 0) {
                     opts = RULE3.getBlockOptions();
                  }

                  int alt17 = 2;
                  int LA17_0 = this.input.LA(1);
                  if (LA17_0 == 40 || LA17_0 >= 66 && LA17_0 <= 68) {
                     alt17 = 1;
                  }

                  switch (alt17) {
                     case 1:
                        this.pushFollow(FOLLOW_modifier_in_rule507);
                        modifier4 = this.modifier();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }
                  }

                  this.match(this.input, 10, FOLLOW_ARG_in_rule516);
                  if (this.state.failed) {
                     return retval;
                  } else {
                     byte alt19;
                     int LA19_0;
                     if (this.input.LA(1) == 2) {
                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           return retval;
                        }

                        alt19 = 2;
                        LA19_0 = this.input.LA(1);
                        if (LA19_0 == 12) {
                           alt19 = 1;
                        }

                        switch (alt19) {
                           case 1:
                              args = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rule521);
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

                     this.match(this.input, 73, FOLLOW_RET_in_rule532);
                     if (this.state.failed) {
                        return retval;
                     } else {
                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           if (this.state.failed) {
                              return retval;
                           }

                           alt19 = 2;
                           LA19_0 = this.input.LA(1);
                           if (LA19_0 == 12) {
                              alt19 = 1;
                           }

                           switch (alt19) {
                              case 1:
                                 ret = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rule537);
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

                        alt19 = 2;
                        LA19_0 = this.input.LA(1);
                        if (LA19_0 == 92) {
                           alt19 = 1;
                        }

                        switch (alt19) {
                           case 1:
                              this.pushFollow(FOLLOW_throwsSpec_in_rule547);
                              throwsSpec5 = this.throwsSpec();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }
                        }

                        int alt21 = 2;
                        int LA21_0 = this.input.LA(1);
                        if (LA21_0 == 58) {
                           alt21 = 1;
                        }

                        switch (alt21) {
                           case 1:
                              this.pushFollow(FOLLOW_optionsSpec_in_rule555);
                              this.optionsSpec();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }
                        }

                        if (this.state.backtracking == 0) {
                           name = id != null ? id.getText() : null;
                           this.currentRuleName = name;
                           if (Rule.getRuleType(name) == 1 && this.grammar.type == 4) {
                              this.grammar.defineLexerRuleFoundInParser(id.getToken(), (GrammarAST)retval.start);
                           } else {
                              int numAlts = this.countAltsForRule((GrammarAST)retval.start);
                              this.grammar.defineRule(id.getToken(), modifier4 != null ? ((modifier_return)modifier4).mod : null, opts, (GrammarAST)retval.start, args, numAlts);
                              r = this.grammar.getRule(name);
                              if (args != null) {
                                 r.parameterScope = this.grammar.createParameterScope(name, args.getToken());
                                 r.parameterScope.addAttributes(args != null ? args.getText() : null, 44);
                              }

                              if (ret != null) {
                                 r.returnScope = this.grammar.createReturnScope(name, ret.getToken());
                                 r.returnScope.addAttributes(ret != null ? ret.getText() : null, 44);
                              }

                              if (throwsSpec5 != null) {
                                 Iterator i$ = throwsSpec5.iterator();

                                 while(i$.hasNext()) {
                                    String exception = (String)i$.next();
                                    r.throwsSpec.add(exception);
                                 }
                              }
                           }
                        }

                        int alt22 = 2;
                        int LA22_0 = this.input.LA(1);
                        if (LA22_0 == 81) {
                           alt22 = 1;
                        }

                        switch (alt22) {
                           case 1:
                              this.pushFollow(FOLLOW_ruleScopeSpec_in_rule568);
                              this.ruleScopeSpec(r);
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }
                        }

                        while(true) {
                           int alt24 = 2;
                           int LA24_0 = this.input.LA(1);
                           if (LA24_0 == 9) {
                              alt24 = 1;
                           }

                           switch (alt24) {
                              case 1:
                                 this.pushFollow(FOLLOW_ruleAction_in_rule577);
                                 this.ruleAction(r);
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return retval;
                                 }
                                 break;
                              default:
                                 if (this.state.backtracking == 0) {
                                    this.blockLevel = 0;
                                 }

                                 this.pushFollow(FOLLOW_block_in_rule592);
                                 b = this.block();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 alt24 = 2;
                                 LA24_0 = this.input.LA(1);
                                 if (LA24_0 == 17 || LA24_0 == 38) {
                                    alt24 = 1;
                                 }

                                 switch (alt24) {
                                    case 1:
                                       this.pushFollow(FOLLOW_exceptionGroup_in_rule598);
                                       this.exceptionGroup();
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          return retval;
                                       }
                                    default:
                                       this.match(this.input, 34, FOLLOW_EOR_in_rule605);
                                       if (this.state.failed) {
                                          return retval;
                                       }

                                       if (this.state.backtracking == 0) {
                                          (b != null ? (GrammarAST)b.start : null).setBlockOptions(opts);
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
               }
            }
         }
      } catch (RecognitionException var26) {
         this.reportError(var26);
         this.recover(this.input, var26);
         return retval;
      } finally {
         ;
      }
   }

   public final void ruleAction(Rule r) throws RecognitionException {
      GrammarAST amp = null;
      GrammarAST id = null;
      GrammarAST a = null;

      try {
         try {
            amp = (GrammarAST)this.match(this.input, 9, FOLLOW_AMPERSAND_in_ruleAction629);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_ruleAction633);
            if (this.state.failed) {
               return;
            }

            a = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_ruleAction637);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            if (this.state.backtracking == 0 && r != null) {
               r.defineNamedAction(amp, id, a);
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.recover(this.input, var9);
         }

      } finally {
         ;
      }
   }

   public final modifier_return modifier() throws RecognitionException {
      modifier_return retval = new modifier_return();
      retval.start = this.input.LT(1);
      retval.mod = ((GrammarAST)retval.start).getToken().getText();

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

   public final HashSet throwsSpec() throws RecognitionException {
      HashSet exceptions = null;
      GrammarAST ID6 = null;
      exceptions = new HashSet();

      try {
         this.match(this.input, 92, FOLLOW_THROWS_in_throwsSpec697);
         if (this.state.failed) {
            return exceptions;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return exceptions;
            } else {
               int cnt25 = 0;

               while(true) {
                  int alt25 = 2;
                  int LA25_0 = this.input.LA(1);
                  if (LA25_0 == 43) {
                     alt25 = 1;
                  }

                  switch (alt25) {
                     case 1:
                        ID6 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_throwsSpec700);
                        if (this.state.failed) {
                           return exceptions;
                        }

                        if (this.state.backtracking == 0) {
                           exceptions.add(ID6 != null ? ID6.getText() : null);
                        }

                        ++cnt25;
                        break;
                     default:
                        if (cnt25 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return exceptions;
                           }

                           EarlyExitException eee = new EarlyExitException(25, this.input);
                           throw eee;
                        }

                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return exceptions;
                        }

                        return exceptions;
                  }
               }
            }
         }
      } catch (RecognitionException var10) {
         this.reportError(var10);
         this.recover(this.input, var10);
         return exceptions;
      } finally {
         ;
      }
   }

   public final void ruleScopeSpec(Rule r) throws RecognitionException {
      this.AttributeScopeActions_stack.push(new AttributeScopeActions_scope());
      GrammarAST attrs = null;
      GrammarAST uses = null;
      ((AttributeScopeActions_scope)this.AttributeScopeActions_stack.peek()).actions = new HashMap();

      try {
         this.match(this.input, 81, FOLLOW_SCOPE_in_ruleScopeSpec730);
         if (!this.state.failed) {
            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);
               if (!this.state.failed) {
                  int alt27 = 2;
                  int LA27_0 = this.input.LA(1);
                  if (LA27_0 == 4 || LA27_0 == 9) {
                     alt27 = 1;
                  }

                  byte alt28;
                  int LA28_0;
                  switch (alt27) {
                     case 1:
                        label259:
                        while(true) {
                           alt28 = 2;
                           LA28_0 = this.input.LA(1);
                           if (LA28_0 == 9) {
                              alt28 = 1;
                           }

                           switch (alt28) {
                              case 1:
                                 this.pushFollow(FOLLOW_attrScopeAction_in_ruleScopeSpec737);
                                 this.attrScopeAction();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return;
                                 }
                                 break;
                              default:
                                 attrs = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec742);
                                 if (this.state.failed) {
                                    return;
                                 }

                                 if (this.state.backtracking == 0) {
                                    r.ruleScope = this.grammar.createRuleScope(r.name, attrs.getToken());
                                    r.ruleScope.isDynamicRuleScope = true;
                                    r.ruleScope.addAttributes(attrs != null ? attrs.getText() : null, 59);
                                    Iterator i$ = ((AttributeScopeActions_scope)this.AttributeScopeActions_stack.peek()).actions.keySet().iterator();

                                    while(i$.hasNext()) {
                                       GrammarAST action = (GrammarAST)i$.next();
                                       r.ruleScope.defineNamedAction(action, (GrammarAST)((AttributeScopeActions_scope)this.AttributeScopeActions_stack.peek()).actions.get(action));
                                    }
                                 }
                                 break label259;
                           }
                        }
                  }

                  while(true) {
                     alt28 = 2;
                     LA28_0 = this.input.LA(1);
                     if (LA28_0 == 43) {
                        alt28 = 1;
                     }

                     switch (alt28) {
                        case 1:
                           uses = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_ruleScopeSpec763);
                           if (this.state.failed) {
                              return;
                           }

                           if (this.state.backtracking == 0) {
                              if (this.grammar.getGlobalScope(uses != null ? uses.getText() : null) == null) {
                                 ErrorManager.grammarError(140, this.grammar, uses.getToken(), uses != null ? uses.getText() : null);
                              } else {
                                 if (r.useScopes == null) {
                                    r.useScopes = new ArrayList();
                                 }

                                 r.useScopes.add(uses != null ? uses.getText() : null);
                              }
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
            }
         }
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(this.input, var11);
      } finally {
         this.AttributeScopeActions_stack.pop();
      }
   }

   public final block_return block() throws RecognitionException {
      block_return retval = new block_return();
      retval.start = this.input.LT(1);
      ++this.blockLevel;
      if (this.blockLevel == 1) {
         this.outerAltNum = 1;
      }

      try {
         this.match(this.input, 16, FOLLOW_BLOCK_in_block797);
         block_return var13;
         if (this.state.failed) {
            var13 = retval;
            return var13;
         }

         this.match(this.input, 2, (BitSet)null);
         if (this.state.failed) {
            var13 = retval;
            return var13;
         }

         int alt29 = 2;
         int LA29_0 = this.input.LA(1);
         if (LA29_0 == 58) {
            alt29 = 1;
         }

         switch (alt29) {
            case 1:
               this.pushFollow(FOLLOW_optionsSpec_in_block803);
               this.optionsSpec();
               --this.state._fsp;
               if (this.state.failed) {
                  block_return var4 = retval;
                  return var4;
               }
         }

         while(true) {
            int cnt31 = 2;
            int LA30_0 = this.input.LA(1);
            if (LA30_0 == 9) {
               cnt31 = 1;
            }

            switch (cnt31) {
               case 1:
                  this.pushFollow(FOLLOW_blockAction_in_block811);
                  this.blockAction();
                  --this.state._fsp;
                  if (this.state.failed) {
                     block_return var6 = retval;
                     return var6;
                  }
                  break;
               default:
                  cnt31 = 0;

                  while(true) {
                     int alt31 = 2;
                     int LA31_0 = this.input.LA(1);
                     if (LA31_0 == 8) {
                        alt31 = 1;
                     }

                     block_return var7;
                     switch (alt31) {
                        case 1:
                           this.pushFollow(FOLLOW_alternative_in_block820);
                           this.alternative();
                           --this.state._fsp;
                           if (this.state.failed) {
                              var7 = retval;
                              return var7;
                           }

                           this.pushFollow(FOLLOW_rewrite_in_block822);
                           this.rewrite();
                           --this.state._fsp;
                           if (this.state.failed) {
                              var7 = retval;
                              return var7;
                           }

                           if (this.blockLevel == 1) {
                              ++this.outerAltNum;
                           }

                           ++cnt31;
                           break;
                        default:
                           if (cnt31 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 var7 = retval;
                                 return var7;
                              }

                              EarlyExitException eee = new EarlyExitException(31, this.input);
                              throw eee;
                           }

                           this.match(this.input, 33, FOLLOW_EOB_in_block839);
                           block_return var16;
                           if (this.state.failed) {
                              var16 = retval;
                              return var16;
                           }

                           this.match(this.input, 3, (BitSet)null);
                           if (this.state.failed) {
                              var16 = retval;
                              return var16;
                           }

                           return retval;
                     }
                  }
            }
         }
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(this.input, var11);
      } finally {
         --this.blockLevel;
      }

      return retval;
   }

   public final void blockAction() throws RecognitionException {
      GrammarAST amp = null;
      GrammarAST id = null;
      GrammarAST a = null;

      try {
         try {
            amp = (GrammarAST)this.match(this.input, 9, FOLLOW_AMPERSAND_in_blockAction863);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_blockAction867);
            if (this.state.failed) {
               return;
            }

            a = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_blockAction871);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final void alternative() throws RecognitionException {
      try {
         this.match(this.input, 8, FOLLOW_ALT_in_alternative909);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int cnt32 = 0;

               while(true) {
                  int alt32 = 2;
                  int LA32_0 = this.input.LA(1);
                  if (LA32_0 == 4 || LA32_0 >= 13 && LA32_0 <= 16 || LA32_0 >= 18 && LA32_0 <= 19 || LA32_0 == 21 || LA32_0 == 29 || LA32_0 == 35 || LA32_0 == 39 || LA32_0 == 41 || LA32_0 == 55 || LA32_0 == 57 || LA32_0 >= 63 && LA32_0 <= 64 || LA32_0 == 70 || LA32_0 == 77 || LA32_0 == 80 || LA32_0 == 83 || LA32_0 >= 88 && LA32_0 <= 90 || LA32_0 == 94 || LA32_0 == 96 || LA32_0 == 98) {
                     alt32 = 1;
                  }

                  switch (alt32) {
                     case 1:
                        this.pushFollow(FOLLOW_element_in_alternative912);
                        this.element();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }

                        ++cnt32;
                        break;
                     default:
                        if (cnt32 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(32, this.input);
                           throw eee;
                        }

                        this.match(this.input, 32, FOLLOW_EOA_in_alternative916);
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

   public final void exceptionGroup() throws RecognitionException {
      try {
         try {
            int alt35 = true;
            int LA35_0 = this.input.LA(1);
            byte alt35;
            if (LA35_0 == 17) {
               alt35 = 1;
            } else {
               if (LA35_0 != 38) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
                  throw nvae;
               }

               alt35 = 2;
            }

            switch (alt35) {
               case 1:
                  int cnt33 = 0;

                  while(true) {
                     int alt34 = 2;
                     int LA34_0 = this.input.LA(1);
                     if (LA34_0 == 17) {
                        alt34 = 1;
                     }

                     switch (alt34) {
                        case 1:
                           this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup931);
                           this.exceptionHandler();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt33;
                           break;
                        default:
                           if (cnt33 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(33, this.input);
                              throw eee;
                           }

                           alt34 = 2;
                           LA34_0 = this.input.LA(1);
                           if (LA34_0 == 38) {
                              alt34 = 1;
                           }

                           switch (alt34) {
                              case 1:
                                 this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup937);
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
                  this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup944);
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
      GrammarAST ACTION7 = null;

      try {
         try {
            this.match(this.input, 17, FOLLOW_CATCH_in_exceptionHandler958);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 12, FOLLOW_ARG_ACTION_in_exceptionHandler960);
            if (this.state.failed) {
               return;
            }

            ACTION7 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler962);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            if (this.state.backtracking == 0) {
               this.trackInlineAction(ACTION7);
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void finallyClause() throws RecognitionException {
      GrammarAST ACTION8 = null;

      try {
         try {
            this.match(this.input, 38, FOLLOW_FINALLY_in_finallyClause980);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            ACTION8 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause982);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            if (this.state.backtracking == 0) {
               this.trackInlineAction(ACTION8);
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final element_return element() throws RecognitionException {
      element_return retval = new element_return();
      retval.start = this.input.LT(1);
      GrammarAST id = null;
      GrammarAST id2 = null;
      GrammarAST act = null;
      GrammarAST act2 = null;
      GrammarAST SEMPRED9 = null;
      GrammarAST GATED_SEMPRED10 = null;
      TreeRuleReturnScope el = null;
      TreeRuleReturnScope a2 = null;

      try {
         try {
            int alt37 = true;
            byte alt37;
            switch (this.input.LA(1)) {
               case 4:
                  alt37 = 12;
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

                  NoViableAltException nvae = new NoViableAltException("", 37, 0, this.input);
                  throw nvae;
               case 13:
                  alt37 = 7;
                  break;
               case 14:
                  alt37 = 16;
                  break;
               case 15:
                  alt37 = 2;
                  break;
               case 16:
               case 21:
               case 57:
               case 64:
                  alt37 = 9;
                  break;
               case 18:
               case 29:
               case 80:
               case 88:
               case 94:
               case 98:
                  alt37 = 3;
                  break;
               case 19:
                  alt37 = 6;
                  break;
               case 35:
                  alt37 = 18;
                  break;
               case 39:
                  alt37 = 13;
                  break;
               case 41:
                  alt37 = 17;
                  break;
               case 55:
                  alt37 = 4;
                  break;
               case 63:
                  alt37 = 8;
                  break;
               case 70:
                  alt37 = 5;
                  break;
               case 77:
                  alt37 = 1;
                  break;
               case 83:
                  alt37 = 14;
                  break;
               case 89:
                  alt37 = 11;
                  break;
               case 90:
                  alt37 = 15;
                  break;
               case 96:
                  alt37 = 10;
            }

            GrammarAST e;
            switch (alt37) {
               case 1:
                  this.match(this.input, 77, FOLLOW_ROOT_in_element999);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element1001);
                  this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 2:
                  this.match(this.input, 15, FOLLOW_BANG_in_element1010);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element1012);
                  this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 3:
                  this.pushFollow(FOLLOW_atom_in_element1020);
                  this.atom((GrammarAST)null);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 4:
                  this.match(this.input, 55, FOLLOW_NOT_in_element1029);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element1031);
                  this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 5:
                  this.match(this.input, 70, FOLLOW_RANGE_in_element1040);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_atom_in_element1042);
                  this.atom((GrammarAST)null);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_atom_in_element1045);
                  this.atom((GrammarAST)null);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 6:
                  this.match(this.input, 19, FOLLOW_CHAR_RANGE_in_element1055);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_atom_in_element1057);
                  this.atom((GrammarAST)null);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_atom_in_element1060);
                  this.atom((GrammarAST)null);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 7:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_element1069);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_element1073);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element1077);
                  el = this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     e = el != null ? (GrammarAST)el.start : null;
                     if (e.getType() == 77 || e.getType() == 15) {
                        e = (GrammarAST)e.getChild(0);
                     }

                     if (e.getType() == 80) {
                        this.grammar.defineRuleRefLabel(this.currentRuleName, id.getToken(), e);
                     } else if (e.getType() == 98 && this.grammar.type == 3) {
                        this.grammar.defineWildcardTreeLabel(this.currentRuleName, id.getToken(), e);
                     } else {
                        this.grammar.defineTokenRefLabel(this.currentRuleName, id.getToken(), e);
                     }
                  }
                  break;
               case 8:
                  this.match(this.input, 63, FOLLOW_PLUS_ASSIGN_in_element1090);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  id2 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_element1094);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_element_in_element1098);
                  a2 = this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     e = a2 != null ? (GrammarAST)a2.start : null;
                     if (e.getType() == 77 || e.getType() == 15) {
                        e = (GrammarAST)e.getChild(0);
                     }

                     if (e.getType() == 80) {
                        this.grammar.defineRuleListLabel(this.currentRuleName, id2.getToken(), e);
                     } else if (e.getType() == 98 && this.grammar.type == 3) {
                        this.grammar.defineWildcardTreeListLabel(this.currentRuleName, id2.getToken(), e);
                     } else {
                        this.grammar.defineTokenListLabel(this.currentRuleName, id2.getToken(), e);
                     }
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 9:
                  this.pushFollow(FOLLOW_ebnf_in_element1115);
                  this.ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 10:
                  this.pushFollow(FOLLOW_tree__in_element1122);
                  this.tree_();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 11:
                  this.match(this.input, 89, FOLLOW_SYNPRED_in_element1131);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_block_in_element1133);
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
               case 12:
                  act = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_element1144);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     act.outerAltNum = this.outerAltNum;
                     this.trackInlineAction(act);
                  }
                  break;
               case 13:
                  act2 = (GrammarAST)this.match(this.input, 39, FOLLOW_FORCED_ACTION_in_element1157);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     act2.outerAltNum = this.outerAltNum;
                     this.trackInlineAction(act2);
                  }
                  break;
               case 14:
                  SEMPRED9 = (GrammarAST)this.match(this.input, 83, FOLLOW_SEMPRED_in_element1168);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     SEMPRED9.outerAltNum = this.outerAltNum;
                     this.trackInlineAction(SEMPRED9);
                  }
                  break;
               case 15:
                  this.match(this.input, 90, FOLLOW_SYN_SEMPRED_in_element1179);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 16:
                  this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_element1187);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     while(true) {
                        int alt36 = 2;
                        int LA36_0 = this.input.LA(1);
                        if (LA36_0 >= 4 && LA36_0 <= 102) {
                           alt36 = 1;
                        } else if (LA36_0 == 3) {
                           alt36 = 2;
                        }

                        switch (alt36) {
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
               case 17:
                  GATED_SEMPRED10 = (GrammarAST)this.match(this.input, 41, FOLLOW_GATED_SEMPRED_in_element1198);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     GATED_SEMPRED10.outerAltNum = this.outerAltNum;
                     this.trackInlineAction(GATED_SEMPRED10);
                  }
                  break;
               case 18:
                  this.match(this.input, 35, FOLLOW_EPSILON_in_element1209);
                  if (this.state.failed) {
                     return retval;
                  }
            }
         } catch (RecognitionException var17) {
            this.reportError(var17);
            this.recover(this.input, var17);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void ebnf() throws RecognitionException {
      try {
         try {
            int alt38 = true;
            int LA38_2;
            int nvaeMark;
            int nvaeMark;
            int nvaeMark;
            int nvaeMark;
            int nvaeConsume;
            int nvaeMark;
            int nvaeMark;
            int nvaeConsume;
            int nvaeMark;
            int nvaeConsume;
            int nvaeMark;
            int nvaeConsume;
            byte alt38;
            NoViableAltException nvae;
            NoViableAltException nvae;
            NoViableAltException nvae;
            NoViableAltException nvae;
            NoViableAltException nvae;
            NoViableAltException nvae;
            NoViableAltException nvae;
            NoViableAltException nvae;
            NoViableAltException nvae;
            NoViableAltException nvae;
            NoViableAltException nvae;
            switch (this.input.LA(1)) {
               case 16:
                  alt38 = 2;
                  break;
               case 21:
                  LA38_2 = this.input.LA(2);
                  if (LA38_2 != 2) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 38, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  nvaeMark = this.input.LA(3);
                  if (nvaeMark != 16) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                           this.input.consume();
                        }

                        nvae = new NoViableAltException("", 38, 5, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  nvaeMark = this.input.LA(4);
                  if (nvaeMark != 2) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        for(nvaeMark = 0; nvaeMark < 3; ++nvaeMark) {
                           this.input.consume();
                        }

                        nvae = new NoViableAltException("", 38, 7, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  nvaeMark = this.input.LA(5);
                  if (nvaeMark == 8) {
                     nvaeMark = this.input.LA(6);
                     if (nvaeMark != 2) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        nvaeConsume = this.input.mark();

                        try {
                           for(nvaeMark = 0; nvaeMark < 5; ++nvaeMark) {
                              this.input.consume();
                           }

                           nvae = new NoViableAltException("", 38, 11, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeConsume);
                        }
                     }

                     nvaeConsume = this.input.LA(7);
                     if (nvaeConsume == 98) {
                        nvaeMark = this.input.LA(8);
                        if (nvaeMark == 32) {
                           nvaeMark = this.input.LA(9);
                           if (nvaeMark != 3) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              nvaeConsume = this.input.mark();

                              try {
                                 for(nvaeMark = 0; nvaeMark < 8; ++nvaeMark) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 38, 19, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeConsume);
                              }
                           }

                           nvaeConsume = this.input.LA(10);
                           if (nvaeConsume == 33) {
                              nvaeMark = this.input.LA(11);
                              if (nvaeMark != 3) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 nvaeConsume = this.input.mark();

                                 try {
                                    for(nvaeMark = 0; nvaeMark < 10; ++nvaeMark) {
                                       this.input.consume();
                                    }

                                    nvae = new NoViableAltException("", 38, 23, this.input);
                                    throw nvae;
                                 } finally {
                                    this.input.rewind(nvaeConsume);
                                 }
                              }

                              nvaeConsume = this.input.LA(12);
                              if (nvaeConsume != 3) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 nvaeMark = this.input.mark();

                                 try {
                                    for(nvaeConsume = 0; nvaeConsume < 11; ++nvaeConsume) {
                                       this.input.consume();
                                    }

                                    nvae = new NoViableAltException("", 38, 25, this.input);
                                    throw nvae;
                                 } finally {
                                    this.input.rewind(nvaeMark);
                                 }
                              }

                              nvaeMark = this.input.LA(13);
                              if (this.synpred1_DefineGrammarItemsWalker()) {
                                 alt38 = 1;
                              } else {
                                 alt38 = 4;
                              }
                           } else {
                              if (nvaeConsume != 8 && nvaeConsume != 76) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 nvaeMark = this.input.mark();

                                 try {
                                    for(nvaeConsume = 0; nvaeConsume < 9; ++nvaeConsume) {
                                       this.input.consume();
                                    }

                                    nvae = new NoViableAltException("", 38, 21, this.input);
                                    throw nvae;
                                 } finally {
                                    this.input.rewind(nvaeMark);
                                 }
                              }

                              alt38 = 4;
                           }
                           break;
                        }

                        if (nvaeMark != 4 && (nvaeMark < 13 || nvaeMark > 16) && (nvaeMark < 18 || nvaeMark > 19) && nvaeMark != 21 && nvaeMark != 29 && nvaeMark != 35 && nvaeMark != 39 && nvaeMark != 41 && nvaeMark != 55 && nvaeMark != 57 && (nvaeMark < 63 || nvaeMark > 64) && nvaeMark != 70 && nvaeMark != 77 && nvaeMark != 80 && nvaeMark != 83 && (nvaeMark < 88 || nvaeMark > 90) && nvaeMark != 94 && nvaeMark != 96 && nvaeMark != 98) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeConsume = 0; nvaeConsume < 7; ++nvaeConsume) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 38, 17, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                        }

                        alt38 = 4;
                        break;
                     }

                     if (nvaeConsume != 4 && (nvaeConsume < 13 || nvaeConsume > 16) && (nvaeConsume < 18 || nvaeConsume > 19) && nvaeConsume != 21 && nvaeConsume != 29 && nvaeConsume != 35 && nvaeConsume != 39 && nvaeConsume != 41 && nvaeConsume != 55 && nvaeConsume != 57 && (nvaeConsume < 63 || nvaeConsume > 64) && nvaeConsume != 70 && nvaeConsume != 77 && nvaeConsume != 80 && nvaeConsume != 83 && (nvaeConsume < 88 || nvaeConsume > 90) && nvaeConsume != 94 && nvaeConsume != 96) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           for(nvaeMark = 0; nvaeMark < 6; ++nvaeMark) {
                              this.input.consume();
                           }

                           nvae = new NoViableAltException("", 38, 15, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt38 = 4;
                     break;
                  }

                  if (nvaeMark != 9 && nvaeMark != 58) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        for(nvaeConsume = 0; nvaeConsume < 4; ++nvaeConsume) {
                           this.input.consume();
                        }

                        nvae = new NoViableAltException("", 38, 9, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt38 = 4;
                  break;
               case 57:
                  alt38 = 3;
                  break;
               case 64:
                  LA38_2 = this.input.LA(2);
                  if (LA38_2 != 2) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 38, 2, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  nvaeMark = this.input.LA(3);
                  if (nvaeMark != 16) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                           this.input.consume();
                        }

                        nvae = new NoViableAltException("", 38, 6, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  nvaeMark = this.input.LA(4);
                  if (nvaeMark != 2) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        for(nvaeMark = 0; nvaeMark < 3; ++nvaeMark) {
                           this.input.consume();
                        }

                        nvae = new NoViableAltException("", 38, 8, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  nvaeMark = this.input.LA(5);
                  if (nvaeMark == 8) {
                     nvaeMark = this.input.LA(6);
                     if (nvaeMark != 2) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        nvaeConsume = this.input.mark();

                        try {
                           for(nvaeMark = 0; nvaeMark < 5; ++nvaeMark) {
                              this.input.consume();
                           }

                           nvae = new NoViableAltException("", 38, 13, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeConsume);
                        }
                     }

                     nvaeConsume = this.input.LA(7);
                     if (nvaeConsume == 98) {
                        nvaeMark = this.input.LA(8);
                        if (nvaeMark == 32) {
                           nvaeMark = this.input.LA(9);
                           if (nvaeMark != 3) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              nvaeConsume = this.input.mark();

                              try {
                                 for(nvaeMark = 0; nvaeMark < 8; ++nvaeMark) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 38, 20, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeConsume);
                              }
                           }

                           nvaeConsume = this.input.LA(10);
                           if (nvaeConsume == 33) {
                              nvaeMark = this.input.LA(11);
                              if (nvaeMark != 3) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 nvaeConsume = this.input.mark();

                                 try {
                                    for(nvaeMark = 0; nvaeMark < 10; ++nvaeMark) {
                                       this.input.consume();
                                    }

                                    nvae = new NoViableAltException("", 38, 24, this.input);
                                    throw nvae;
                                 } finally {
                                    this.input.rewind(nvaeConsume);
                                 }
                              }

                              nvaeConsume = this.input.LA(12);
                              if (nvaeConsume != 3) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 nvaeMark = this.input.mark();

                                 try {
                                    for(nvaeConsume = 0; nvaeConsume < 11; ++nvaeConsume) {
                                       this.input.consume();
                                    }

                                    nvae = new NoViableAltException("", 38, 26, this.input);
                                    throw nvae;
                                 } finally {
                                    this.input.rewind(nvaeMark);
                                 }
                              }

                              nvaeMark = this.input.LA(13);
                              if (this.synpred1_DefineGrammarItemsWalker()) {
                                 alt38 = 1;
                              } else {
                                 alt38 = 5;
                              }
                           } else {
                              if (nvaeConsume != 8 && nvaeConsume != 76) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 nvaeMark = this.input.mark();

                                 try {
                                    for(nvaeConsume = 0; nvaeConsume < 9; ++nvaeConsume) {
                                       this.input.consume();
                                    }

                                    nvae = new NoViableAltException("", 38, 22, this.input);
                                    throw nvae;
                                 } finally {
                                    this.input.rewind(nvaeMark);
                                 }
                              }

                              alt38 = 5;
                           }
                           break;
                        }

                        if (nvaeMark != 4 && (nvaeMark < 13 || nvaeMark > 16) && (nvaeMark < 18 || nvaeMark > 19) && nvaeMark != 21 && nvaeMark != 29 && nvaeMark != 35 && nvaeMark != 39 && nvaeMark != 41 && nvaeMark != 55 && nvaeMark != 57 && (nvaeMark < 63 || nvaeMark > 64) && nvaeMark != 70 && nvaeMark != 77 && nvaeMark != 80 && nvaeMark != 83 && (nvaeMark < 88 || nvaeMark > 90) && nvaeMark != 94 && nvaeMark != 96 && nvaeMark != 98) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeConsume = 0; nvaeConsume < 7; ++nvaeConsume) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 38, 18, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                        }

                        alt38 = 5;
                        break;
                     }

                     if (nvaeConsume != 4 && (nvaeConsume < 13 || nvaeConsume > 16) && (nvaeConsume < 18 || nvaeConsume > 19) && nvaeConsume != 21 && nvaeConsume != 29 && nvaeConsume != 35 && nvaeConsume != 39 && nvaeConsume != 41 && nvaeConsume != 55 && nvaeConsume != 57 && (nvaeConsume < 63 || nvaeConsume > 64) && nvaeConsume != 70 && nvaeConsume != 77 && nvaeConsume != 80 && nvaeConsume != 83 && (nvaeConsume < 88 || nvaeConsume > 90) && nvaeConsume != 94 && nvaeConsume != 96) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           for(nvaeMark = 0; nvaeMark < 6; ++nvaeMark) {
                              this.input.consume();
                           }

                           nvae = new NoViableAltException("", 38, 16, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt38 = 5;
                     break;
                  }

                  if (nvaeMark != 9 && nvaeMark != 58) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        for(nvaeConsume = 0; nvaeConsume < 4; ++nvaeConsume) {
                           this.input.consume();
                        }

                        nvae = new NoViableAltException("", 38, 10, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt38 = 5;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 38, 0, this.input);
                  throw nvae;
            }

            switch (alt38) {
               case 1:
                  this.pushFollow(FOLLOW_dotLoop_in_ebnf1227);
                  this.dotLoop();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_block_in_ebnf1233);
                  this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 3:
                  this.match(this.input, 57, FOLLOW_OPTIONAL_in_ebnf1240);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1242);
                  this.block();
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
                  this.match(this.input, 21, FOLLOW_CLOSURE_in_ebnf1251);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1253);
                  this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 5:
                  this.match(this.input, 64, FOLLOW_POSITIVE_CLOSURE_in_ebnf1262);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1264);
                  this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
            }
         } catch (RecognitionException var612) {
            this.reportError(var612);
            this.recover(this.input, var612);
         }

      } finally {
         ;
      }
   }

   public final dotLoop_return dotLoop() throws RecognitionException {
      dotLoop_return retval = new dotLoop_return();
      retval.start = this.input.LT(1);

      try {
         try {
            int alt39 = true;
            int LA39_0 = this.input.LA(1);
            byte alt39;
            if (LA39_0 == 21) {
               alt39 = 1;
            } else {
               if (LA39_0 != 64) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 39, 0, this.input);
                  throw nvae;
               }

               alt39 = 2;
            }

            switch (alt39) {
               case 1:
                  this.match(this.input, 21, FOLLOW_CLOSURE_in_dotLoop1283);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_dotBlock_in_dotLoop1285);
                  this.dotBlock();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 2:
                  this.match(this.input, 64, FOLLOW_POSITIVE_CLOSURE_in_dotLoop1295);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.pushFollow(FOLLOW_dotBlock_in_dotLoop1297);
                  this.dotBlock();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }
            }

            if (this.state.backtracking == 0) {
               GrammarAST block = (GrammarAST)((GrammarAST)retval.start).getChild(0);
               Map opts = new HashMap();
               opts.put("greedy", "false");
               if (this.grammar.type != 1) {
                  opts.put("k", 1);
               }

               block.setOptions(this.grammar, opts);
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.recover(this.input, var9);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void dotBlock() throws RecognitionException {
      try {
         try {
            this.match(this.input, 16, FOLLOW_BLOCK_in_dotBlock1320);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 8, FOLLOW_ALT_in_dotBlock1324);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 98, FOLLOW_WILDCARD_in_dotBlock1326);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 32, FOLLOW_EOA_in_dotBlock1328);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 33, FOLLOW_EOB_in_dotBlock1332);
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

   public final void tree_() throws RecognitionException {
      try {
         this.match(this.input, 96, FOLLOW_TREE_BEGIN_in_tree_1346);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int cnt40 = 0;

               while(true) {
                  int alt40 = 2;
                  int LA40_0 = this.input.LA(1);
                  if (LA40_0 == 4 || LA40_0 >= 13 && LA40_0 <= 16 || LA40_0 >= 18 && LA40_0 <= 19 || LA40_0 == 21 || LA40_0 == 29 || LA40_0 == 35 || LA40_0 == 39 || LA40_0 == 41 || LA40_0 == 55 || LA40_0 == 57 || LA40_0 >= 63 && LA40_0 <= 64 || LA40_0 == 70 || LA40_0 == 77 || LA40_0 == 80 || LA40_0 == 83 || LA40_0 >= 88 && LA40_0 <= 90 || LA40_0 == 94 || LA40_0 == 96 || LA40_0 == 98) {
                     alt40 = 1;
                  }

                  switch (alt40) {
                     case 1:
                        this.pushFollow(FOLLOW_element_in_tree_1348);
                        this.element();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }

                        ++cnt40;
                        break;
                     default:
                        if (cnt40 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(40, this.input);
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

   public final void atom(GrammarAST scope_) throws RecognitionException {
      GrammarAST rr = null;
      GrammarAST rarg = null;
      GrammarAST t = null;
      GrammarAST targ = null;
      GrammarAST c = null;
      GrammarAST s = null;
      GrammarAST ID11 = null;

      try {
         try {
            int alt43 = true;
            byte alt43;
            switch (this.input.LA(1)) {
               case 18:
                  alt43 = 3;
                  break;
               case 29:
                  alt43 = 6;
                  break;
               case 80:
                  alt43 = 1;
                  break;
               case 88:
                  alt43 = 4;
                  break;
               case 94:
                  alt43 = 2;
                  break;
               case 98:
                  alt43 = 5;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 43, 0, this.input);
                  throw nvae;
            }

            Rule rule;
            int LA42_0;
            byte alt42;
            switch (alt43) {
               case 1:
                  rr = (GrammarAST)this.match(this.input, 80, FOLLOW_RULE_REF_in_atom1366);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     alt42 = 2;
                     LA42_0 = this.input.LA(1);
                     if (LA42_0 == 12) {
                        alt42 = 1;
                     }

                     switch (alt42) {
                        case 1:
                           rarg = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1371);
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           this.match(this.input, 3, (BitSet)null);
                           if (this.state.failed) {
                              return;
                           }
                     }
                  }

                  if (this.state.backtracking == 0) {
                     this.grammar.altReferencesRule(this.currentRuleName, scope_, rr, this.outerAltNum);
                     if (rarg != null) {
                        rarg.outerAltNum = this.outerAltNum;
                        this.trackInlineAction(rarg);
                     }
                  }
                  break;
               case 2:
                  t = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_atom1388);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     alt42 = 2;
                     LA42_0 = this.input.LA(1);
                     if (LA42_0 == 12) {
                        alt42 = 1;
                     }

                     switch (alt42) {
                        case 1:
                           targ = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1393);
                           if (this.state.failed) {
                              return;
                           }
                        default:
                           this.match(this.input, 3, (BitSet)null);
                           if (this.state.failed) {
                              return;
                           }
                     }
                  }

                  if (this.state.backtracking == 0) {
                     if (targ != null) {
                        targ.outerAltNum = this.outerAltNum;
                        this.trackInlineAction(targ);
                     }

                     if (this.grammar.type == 1) {
                        this.grammar.altReferencesRule(this.currentRuleName, scope_, t, this.outerAltNum);
                     } else {
                        this.grammar.altReferencesTokenID(this.currentRuleName, t, this.outerAltNum);
                     }
                  }
                  break;
               case 3:
                  c = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_atom1409);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0 && this.grammar.type != 1) {
                     rule = this.grammar.getRule(this.currentRuleName);
                     if (rule != null) {
                        rule.trackTokenReferenceInAlt(c, this.outerAltNum);
                     }
                  }
                  break;
               case 4:
                  s = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_atom1420);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0 && this.grammar.type != 1) {
                     rule = this.grammar.getRule(this.currentRuleName);
                     if (rule != null) {
                        rule.trackTokenReferenceInAlt(s, this.outerAltNum);
                     }
                  }
                  break;
               case 5:
                  this.match(this.input, 98, FOLLOW_WILDCARD_in_atom1430);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 6:
                  this.match(this.input, 29, FOLLOW_DOT_in_atom1436);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  ID11 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_atom1438);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_atom_in_atom1440);
                  this.atom(ID11);
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
         }

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

   public final rewrite_return rewrite() throws RecognitionException {
      rewrite_return retval = new rewrite_return();
      retval.start = this.input.LT(1);
      GrammarAST pred = null;
      this.currentRewriteRule = (GrammarAST)retval.start;
      if (this.state.backtracking == 0 && this.grammar.buildAST()) {
         this.currentRewriteRule.rewriteRefsDeep = new HashSet();
      }

      try {
         try {
            int alt46 = true;
            int LA46_0 = this.input.LA(1);
            byte alt46;
            if (LA46_0 == 76) {
               alt46 = 1;
            } else {
               if (LA46_0 != 8 && LA46_0 != 33) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 46, 0, this.input);
                  throw nvae;
               }

               alt46 = 2;
            }

            switch (alt46) {
               case 1:
                  this.match(this.input, 76, FOLLOW_REWRITES_in_rewrite1477);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return retval;
                     }

                     while(true) {
                        int alt45 = 2;
                        int LA45_0 = this.input.LA(1);
                        if (LA45_0 == 75) {
                           alt45 = 1;
                        }

                        switch (alt45) {
                           case 1:
                              this.match(this.input, 75, FOLLOW_REWRITE_in_rewrite1486);
                              if (this.state.failed) {
                                 return retval;
                              }

                              this.match(this.input, 2, (BitSet)null);
                              if (this.state.failed) {
                                 return retval;
                              }

                              int alt44 = 2;
                              int LA44_0 = this.input.LA(1);
                              if (LA44_0 == 83) {
                                 alt44 = 1;
                              }

                              switch (alt44) {
                                 case 1:
                                    pred = (GrammarAST)this.match(this.input, 83, FOLLOW_SEMPRED_in_rewrite1491);
                                    if (this.state.failed) {
                                       return retval;
                                    }
                                 default:
                                    this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite1495);
                                    this.rewrite_alternative();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    this.match(this.input, 3, (BitSet)null);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0 && pred != null) {
                                       pred.outerAltNum = this.outerAltNum;
                                       this.trackInlineAction(pred);
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
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.recover(this.input, var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_block_return rewrite_block() throws RecognitionException {
      rewrite_block_return retval = new rewrite_block_return();
      retval.start = this.input.LT(1);
      GrammarAST enclosingBlock = this.currentRewriteBlock;
      if (this.state.backtracking == 0) {
         this.currentRewriteBlock = (GrammarAST)retval.start;
         this.currentRewriteBlock.rewriteRefsShallow = new HashSet();
         this.currentRewriteBlock.rewriteRefsDeep = new HashSet();
      }

      rewrite_block_return var3;
      try {
         this.match(this.input, 16, FOLLOW_BLOCK_in_rewrite_block1539);
         if (this.state.failed) {
            var3 = retval;
            return var3;
         }

         this.match(this.input, 2, (BitSet)null);
         if (this.state.failed) {
            var3 = retval;
            return var3;
         }

         this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite_block1541);
         this.rewrite_alternative();
         --this.state._fsp;
         if (this.state.failed) {
            var3 = retval;
            return var3;
         }

         this.match(this.input, 33, FOLLOW_EOB_in_rewrite_block1543);
         if (!this.state.failed) {
            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               var3 = retval;
               return var3;
            }

            if (this.state.backtracking == 0 && enclosingBlock != null) {
               Iterator i$ = this.currentRewriteBlock.rewriteRefsShallow.iterator();

               while(i$.hasNext()) {
                  GrammarAST item = (GrammarAST)i$.next();
                  enclosingBlock.rewriteRefsDeep.add(item);
               }
            }

            return retval;
         }

         var3 = retval;
      } catch (RecognitionException var8) {
         this.reportError(var8);
         this.recover(this.input, var8);
         return retval;
      } finally {
         this.currentRewriteBlock = enclosingBlock;
      }

      return var3;
   }

   public final void rewrite_alternative() throws RecognitionException {
      GrammarAST a = null;

      try {
         try {
            int alt49 = 3;
            int LA49_0 = this.input.LA(1);
            int LA48_0;
            int cnt47;
            int LA47_0;
            if (LA49_0 != 8 || !this.grammar.buildAST() && !this.grammar.buildTemplate()) {
               if ((LA49_0 == 4 || LA49_0 == 91) && this.grammar.buildTemplate()) {
                  alt49 = 2;
               } else {
                  if (LA49_0 != 37) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 49, 0, this.input);
                     throw nvae;
                  }

                  alt49 = 3;
               }
            } else {
               int LA49_1 = this.input.LA(2);
               if (LA49_1 == 2 && (this.grammar.buildAST() || this.grammar.buildTemplate())) {
                  LA48_0 = this.input.LA(3);
                  if (LA48_0 == 35 && (this.grammar.buildAST() || this.grammar.buildTemplate())) {
                     cnt47 = this.input.LA(4);
                     if (cnt47 == 32 && (this.grammar.buildAST() || this.grammar.buildTemplate())) {
                        int LA49_7 = this.input.LA(5);
                        if (LA49_7 == 3 && (this.grammar.buildAST() || this.grammar.buildTemplate())) {
                           LA47_0 = this.input.LA(6);
                           if (this.grammar.buildAST()) {
                              alt49 = 1;
                           } else {
                              if (!this.grammar.buildTemplate()) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 int nvaeMark = this.input.mark();

                                 try {
                                    for(int nvaeConsume = 0; nvaeConsume < 5; ++nvaeConsume) {
                                       this.input.consume();
                                    }

                                    NoViableAltException nvae = new NoViableAltException("", 49, 8, this.input);
                                    throw nvae;
                                 } finally {
                                    this.input.rewind(nvaeMark);
                                 }
                              }

                              alt49 = 2;
                           }
                        }
                     }
                  } else if ((LA48_0 == 4 || LA48_0 == 18 || LA48_0 == 21 || LA48_0 == 48 || LA48_0 == 57 || LA48_0 == 64 || LA48_0 == 80 || LA48_0 == 88 || LA48_0 == 94 || LA48_0 == 96) && this.grammar.buildAST()) {
                     alt49 = 1;
                  }
               }
            }

            switch (alt49) {
               case 1:
                  if (!this.grammar.buildAST()) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     throw new FailedPredicateException(this.input, "rewrite_alternative", "grammar.buildAST()");
                  }

                  a = (GrammarAST)this.match(this.input, 8, FOLLOW_ALT_in_rewrite_alternative1575);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  int alt48 = true;
                  LA48_0 = this.input.LA(1);
                  byte alt48;
                  if (LA48_0 != 4 && LA48_0 != 18 && LA48_0 != 21 && LA48_0 != 48 && LA48_0 != 57 && LA48_0 != 64 && LA48_0 != 80 && LA48_0 != 88 && LA48_0 != 94 && LA48_0 != 96) {
                     if (LA48_0 != 35) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 48, 0, this.input);
                        throw nvae;
                     }

                     alt48 = 2;
                  } else {
                     alt48 = 1;
                  }

                  label733:
                  switch (alt48) {
                     case 1:
                        cnt47 = 0;

                        while(true) {
                           int alt47 = 2;
                           LA47_0 = this.input.LA(1);
                           if (LA47_0 == 4 || LA47_0 == 18 || LA47_0 == 21 || LA47_0 == 48 || LA47_0 == 57 || LA47_0 == 64 || LA47_0 == 80 || LA47_0 == 88 || LA47_0 == 94 || LA47_0 == 96) {
                              alt47 = 1;
                           }

                           switch (alt47) {
                              case 1:
                                 this.pushFollow(FOLLOW_rewrite_element_in_rewrite_alternative1581);
                                 this.rewrite_element();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return;
                                 }

                                 ++cnt47;
                                 break;
                              default:
                                 if (cnt47 < 1) {
                                    if (this.state.backtracking > 0) {
                                       this.state.failed = true;
                                       return;
                                    }

                                    EarlyExitException eee = new EarlyExitException(47, this.input);
                                    throw eee;
                                 }
                                 break label733;
                           }
                        }
                     case 2:
                        this.match(this.input, 35, FOLLOW_EPSILON_in_rewrite_alternative1588);
                        if (this.state.failed) {
                           return;
                        }
                  }

                  this.match(this.input, 32, FOLLOW_EOA_in_rewrite_alternative1592);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  if (!this.grammar.buildTemplate()) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     throw new FailedPredicateException(this.input, "rewrite_alternative", "grammar.buildTemplate()");
                  }

                  this.pushFollow(FOLLOW_rewrite_template_in_rewrite_alternative1603);
                  this.rewrite_template();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 3:
                  this.match(this.input, 37, FOLLOW_ETC_in_rewrite_alternative1608);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.blockLevel != 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return;
                     }

                     throw new FailedPredicateException(this.input, "rewrite_alternative", "this.blockLevel==1");
                  }
            }
         } catch (RecognitionException var20) {
            this.reportError(var20);
            this.recover(this.input, var20);
         }

      } finally {
         ;
      }
   }

   public final void rewrite_element() throws RecognitionException {
      try {
         try {
            int alt50 = true;
            byte alt50;
            switch (this.input.LA(1)) {
               case 4:
               case 18:
               case 48:
               case 80:
               case 88:
               case 94:
                  alt50 = 1;
                  break;
               case 21:
               case 57:
               case 64:
                  alt50 = 2;
                  break;
               case 96:
                  alt50 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 50, 0, this.input);
                  throw nvae;
            }

            switch (alt50) {
               case 1:
                  this.pushFollow(FOLLOW_rewrite_atom_in_rewrite_element1622);
                  this.rewrite_atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_rewrite_ebnf_in_rewrite_element1627);
                  this.rewrite_ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 3:
                  this.pushFollow(FOLLOW_rewrite_tree_in_rewrite_element1632);
                  this.rewrite_tree();
                  --this.state._fsp;
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

   public final void rewrite_ebnf() throws RecognitionException {
      try {
         try {
            int alt51 = true;
            byte alt51;
            switch (this.input.LA(1)) {
               case 21:
                  alt51 = 2;
                  break;
               case 57:
                  alt51 = 1;
                  break;
               case 64:
                  alt51 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 51, 0, this.input);
                  throw nvae;
            }

            switch (alt51) {
               case 1:
                  this.match(this.input, 57, FOLLOW_OPTIONAL_in_rewrite_ebnf1645);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_rewrite_block_in_rewrite_ebnf1647);
                  this.rewrite_block();
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
                  this.match(this.input, 21, FOLLOW_CLOSURE_in_rewrite_ebnf1656);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_rewrite_block_in_rewrite_ebnf1658);
                  this.rewrite_block();
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
                  this.match(this.input, 64, FOLLOW_POSITIVE_CLOSURE_in_rewrite_ebnf1667);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_rewrite_block_in_rewrite_ebnf1669);
                  this.rewrite_block();
                  --this.state._fsp;
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

   public final void rewrite_tree() throws RecognitionException {
      try {
         this.match(this.input, 96, FOLLOW_TREE_BEGIN_in_rewrite_tree1686);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               this.pushFollow(FOLLOW_rewrite_atom_in_rewrite_tree1688);
               this.rewrite_atom();
               --this.state._fsp;
               if (!this.state.failed) {
                  while(true) {
                     int alt52 = 2;
                     int LA52_0 = this.input.LA(1);
                     if (LA52_0 == 4 || LA52_0 == 18 || LA52_0 == 21 || LA52_0 == 48 || LA52_0 == 57 || LA52_0 == 64 || LA52_0 == 80 || LA52_0 == 88 || LA52_0 == 94 || LA52_0 == 96) {
                        alt52 = 1;
                     }

                     switch (alt52) {
                        case 1:
                           this.pushFollow(FOLLOW_rewrite_element_in_rewrite_tree1692);
                           this.rewrite_element();
                           --this.state._fsp;
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
            }
         }
      } catch (RecognitionException var6) {
         this.reportError(var6);
         this.recover(this.input, var6);
      } finally {
         ;
      }
   }

   public final rewrite_atom_return rewrite_atom() throws RecognitionException {
      rewrite_atom_return retval = new rewrite_atom_return();
      retval.start = this.input.LT(1);
      GrammarAST ARG_ACTION12 = null;
      GrammarAST ACTION13 = null;
      if (this.state.backtracking == 0) {
         Rule r = this.grammar.getRule(this.currentRuleName);
         Set tokenRefsInAlt = r.getTokenRefsInAlt(this.outerAltNum);
         boolean imaginary = ((GrammarAST)retval.start).getType() == 94 && !tokenRefsInAlt.contains(((GrammarAST)retval.start).getText());
         if (!imaginary && this.grammar.buildAST() && (((GrammarAST)retval.start).getType() == 80 || ((GrammarAST)retval.start).getType() == 48 || ((GrammarAST)retval.start).getType() == 94 || ((GrammarAST)retval.start).getType() == 18 || ((GrammarAST)retval.start).getType() == 88)) {
            if (this.currentRewriteBlock != null) {
               this.currentRewriteBlock.rewriteRefsShallow.add((GrammarAST)retval.start);
               this.currentRewriteBlock.rewriteRefsDeep.add((GrammarAST)retval.start);
            }

            this.currentRewriteRule.rewriteRefsDeep.add((GrammarAST)retval.start);
         }
      }

      try {
         try {
            int alt55 = true;
            byte alt55;
            switch (this.input.LA(1)) {
               case 4:
                  alt55 = 4;
                  break;
               case 18:
               case 88:
               case 94:
                  alt55 = 2;
                  break;
               case 48:
                  alt55 = 3;
                  break;
               case 80:
                  alt55 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 55, 0, this.input);
                  throw nvae;
            }

            switch (alt55) {
               case 1:
                  this.match(this.input, 80, FOLLOW_RULE_REF_in_rewrite_atom1713);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 2:
                  int alt54 = true;
                  byte alt54;
                  switch (this.input.LA(1)) {
                     case 18:
                        alt54 = 2;
                        break;
                     case 88:
                        alt54 = 3;
                        break;
                     case 94:
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
                        this.match(this.input, 94, FOLLOW_TOKEN_REF_in_rewrite_atom1723);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           if (this.state.failed) {
                              return retval;
                           }

                           int alt53 = 2;
                           int LA53_0 = this.input.LA(1);
                           if (LA53_0 == 12) {
                              alt53 = 1;
                           }

                           switch (alt53) {
                              case 1:
                                 ARG_ACTION12 = (GrammarAST)this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rewrite_atom1731);
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    ARG_ACTION12.outerAltNum = this.outerAltNum;
                                    this.trackInlineAction(ARG_ACTION12);
                                 }
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                                 if (this.state.failed) {
                                    return retval;
                                 }
                           }
                        }

                        return retval;
                     case 2:
                        this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_rewrite_atom1756);
                        if (this.state.failed) {
                           return retval;
                        }

                        return retval;
                     case 3:
                        this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_rewrite_atom1762);
                        if (this.state.failed) {
                           return retval;
                        }

                        return retval;
                     default:
                        return retval;
                  }
               case 3:
                  this.match(this.input, 48, FOLLOW_LABEL_in_rewrite_atom1771);
                  if (this.state.failed) {
                     return retval;
                  }
                  break;
               case 4:
                  ACTION13 = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_atom1776);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION13.outerAltNum = this.outerAltNum;
                     this.trackInlineAction(ACTION13);
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

   public final void rewrite_template() throws RecognitionException {
      GrammarAST id = null;
      GrammarAST ind = null;
      GrammarAST arg = null;
      GrammarAST a = null;
      GrammarAST act = null;

      try {
         try {
            int alt59 = true;
            byte alt59;
            switch (this.input.LA(1)) {
               case 4:
                  alt59 = 3;
                  break;
               case 8:
                  alt59 = 1;
                  break;
               case 91:
                  alt59 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 59, 0, this.input);
                  throw nvae;
            }

            switch (alt59) {
               case 1:
                  this.match(this.input, 8, FOLLOW_ALT_in_rewrite_template1793);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 35, FOLLOW_EPSILON_in_rewrite_template1795);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 32, FOLLOW_EOA_in_rewrite_template1797);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.match(this.input, 91, FOLLOW_TEMPLATE_in_rewrite_template1806);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  int alt56 = true;
                  int LA56_0 = this.input.LA(1);
                  byte alt56;
                  if (LA56_0 == 43) {
                     alt56 = 1;
                  } else {
                     if (LA56_0 != 4) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 56, 0, this.input);
                        throw nvae;
                     }

                     alt56 = 2;
                  }

                  switch (alt56) {
                     case 1:
                        id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rewrite_template1811);
                        if (this.state.failed) {
                           return;
                        }
                        break;
                     case 2:
                        ind = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template1815);
                        if (this.state.failed) {
                           return;
                        }
                  }

                  this.match(this.input, 11, FOLLOW_ARGLIST_in_rewrite_template1823);
                  if (this.state.failed) {
                     return;
                  }

                  byte alt58;
                  int LA58_0;
                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     label379:
                     while(true) {
                        alt58 = 2;
                        LA58_0 = this.input.LA(1);
                        if (LA58_0 == 10) {
                           alt58 = 1;
                        }

                        switch (alt58) {
                           case 1:
                              this.match(this.input, 10, FOLLOW_ARG_in_rewrite_template1833);
                              if (this.state.failed) {
                                 return;
                              }

                              this.match(this.input, 2, (BitSet)null);
                              if (this.state.failed) {
                                 return;
                              }

                              arg = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rewrite_template1837);
                              if (this.state.failed) {
                                 return;
                              }

                              a = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template1841);
                              if (this.state.failed) {
                                 return;
                              }

                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return;
                              }

                              if (this.state.backtracking == 0) {
                                 a.outerAltNum = this.outerAltNum;
                                 this.trackInlineAction(a);
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return;
                              }
                              break label379;
                        }
                     }
                  }

                  if (this.state.backtracking == 0 && ind != null) {
                     ind.outerAltNum = this.outerAltNum;
                     this.trackInlineAction(ind);
                  }

                  alt58 = 2;
                  LA58_0 = this.input.LA(1);
                  if (LA58_0 >= 30 && LA58_0 <= 31) {
                     alt58 = 1;
                  }

                  switch (alt58) {
                     case 1:
                        if (this.input.LA(1) < 30 || this.input.LA(1) > 31) {
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
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        return;
                  }
               case 3:
                  act = (GrammarAST)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template1898);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.state.backtracking == 0) {
                     act.outerAltNum = this.outerAltNum;
                     this.trackInlineAction(act);
                  }
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
         }

      } finally {
         ;
      }
   }

   public final void synpred1_DefineGrammarItemsWalker_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_dotLoop_in_synpred1_DefineGrammarItemsWalker1222);
      this.dotLoop();
      --this.state._fsp;
      if (!this.state.failed) {
         ;
      }
   }

   public final boolean synpred1_DefineGrammarItemsWalker() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred1_DefineGrammarItemsWalker_fragment();
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
   }

   public static class rewrite_block_return extends TreeRuleReturnScope {
   }

   public static class rewrite_return extends TreeRuleReturnScope {
   }

   public static class dotLoop_return extends TreeRuleReturnScope {
   }

   public static class element_return extends TreeRuleReturnScope {
   }

   public static class block_return extends TreeRuleReturnScope {
   }

   public static class modifier_return extends TreeRuleReturnScope {
      public String mod;
   }

   public static class rule_return extends TreeRuleReturnScope {
   }

   public static class grammar__return extends TreeRuleReturnScope {
   }

   protected static class AttributeScopeActions_scope {
      HashMap actions;
   }
}
