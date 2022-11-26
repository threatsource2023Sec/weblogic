package org.antlr.grammar.v3;

import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;

public class LeftRecursiveRuleWalker extends TreeParser {
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
   private String ruleName;
   private int outerAlt;
   public int numAlts;
   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec57 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_option_in_optionsSpec59 = new BitSet(new long[]{8200L});
   public static final BitSet FOLLOW_ASSIGN_in_option73 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_option75 = new BitSet(new long[]{149533581639680L, 16777216L});
   public static final BitSet FOLLOW_optionValue_in_option77 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_charSetElement115 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OR_in_charSetElement121 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_charSetElement123 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_charSetElement125 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RANGE_in_charSetElement132 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_charSetElement134 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_charSetElement136 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RULE_in_rec_rule164 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_rec_rule168 = new BitSet(new long[]{1099511628800L, 28L});
   public static final BitSet FOLLOW_modifier_in_rec_rule175 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_ARG_in_rec_rule182 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rec_rule184 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RET_in_rec_rule192 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_rec_rule194 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_optionsSpec_in_rec_rule201 = new BitSet(new long[]{66048L, 131072L});
   public static final BitSet FOLLOW_ruleScopeSpec_in_rec_rule207 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_AMPERSAND_in_rec_rule215 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ruleBlock_in_rec_rule226 = new BitSet(new long[]{292057907200L});
   public static final BitSet FOLLOW_exceptionGroup_in_rec_rule233 = new BitSet(new long[]{17179869184L});
   public static final BitSet FOLLOW_EOR_in_rec_rule239 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec286 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec288 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_ID_in_ruleScopeSpec291 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_BLOCK_in_ruleBlock315 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_optionsSpec_in_ruleBlock320 = new BitSet(new long[]{256L});
   public static final BitSet FOLLOW_outerAlternative_in_ruleBlock328 = new BitSet(new long[]{8589934848L, 4096L});
   public static final BitSet FOLLOW_rewrite_in_ruleBlock340 = new BitSet(new long[]{8589934848L});
   public static final BitSet FOLLOW_EOB_in_ruleBlock358 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BLOCK_in_block381 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_optionsSpec_in_block395 = new BitSet(new long[]{256L});
   public static final BitSet FOLLOW_ALT_in_block413 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_block415 = new BitSet(new long[]{-9043225263786827760L, 22666616897L});
   public static final BitSet FOLLOW_EOA_in_block418 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_rewrite_in_block421 = new BitSet(new long[]{8589934848L});
   public static final BitSet FOLLOW_EOB_in_block439 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_binaryMultipleOp_in_outerAlternative488 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_binary_in_outerAlternative544 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ternary_in_outerAlternative606 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_prefix_in_outerAlternative662 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_suffix_in_outerAlternative718 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ALT_in_outerAlternative760 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_outerAlternative762 = new BitSet(new long[]{-9043225263786827760L, 22666616897L});
   public static final BitSet FOLLOW_EOA_in_outerAlternative765 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_binary814 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_binary818 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_recurseNoLabel_in_binary826 = new BitSet(new long[]{-9223372036854472704L, 1090527232L});
   public static final BitSet FOLLOW_token_in_binary830 = new BitSet(new long[]{-9223372036854767616L, 65536L});
   public static final BitSet FOLLOW_recurse_in_binary832 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_binary834 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_binaryMultipleOp851 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_binaryMultipleOp855 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_recurseNoLabel_in_binaryMultipleOp863 = new BitSet(new long[]{65536L});
   public static final BitSet FOLLOW_BLOCK_in_binaryMultipleOp867 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ALT_in_binaryMultipleOp873 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_token_in_binaryMultipleOp877 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_binaryMultipleOp879 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_EOB_in_binaryMultipleOp888 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_recurse_in_binaryMultipleOp892 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_binaryMultipleOp894 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_ternary909 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_ternary913 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_recurseNoLabel_in_ternary921 = new BitSet(new long[]{-9223372036854472704L, 1090527232L});
   public static final BitSet FOLLOW_token_in_ternary925 = new BitSet(new long[]{-9223372036854767616L, 65536L});
   public static final BitSet FOLLOW_recurse_in_ternary927 = new BitSet(new long[]{-9223372036854472704L, 1090527232L});
   public static final BitSet FOLLOW_token_in_ternary929 = new BitSet(new long[]{-9223372036854767616L, 65536L});
   public static final BitSet FOLLOW_recurse_in_ternary931 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_ternary933 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_prefix949 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_prefix953 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_prefix966 = new BitSet(new long[]{-9043225268081795056L, 22666616897L});
   public static final BitSet FOLLOW_recurse_in_prefix970 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_EOA_in_prefix972 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_suffix985 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_suffix989 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_recurseNoLabel_in_suffix997 = new BitSet(new long[]{-9043225268081795056L, 22666616897L});
   public static final BitSet FOLLOW_element_in_suffix1001 = new BitSet(new long[]{-9043225263786827760L, 22666616897L});
   public static final BitSet FOLLOW_EOA_in_suffix1005 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ASSIGN_in_recurse1018 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_recurse1020 = new BitSet(new long[]{0L, 65536L});
   public static final BitSet FOLLOW_recurseNoLabel_in_recurse1022 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PLUS_ASSIGN_in_recurse1029 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_recurse1031 = new BitSet(new long[]{0L, 65536L});
   public static final BitSet FOLLOW_recurseNoLabel_in_recurse1033 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_recurseNoLabel_in_recurse1039 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_RULE_REF_in_recurseNoLabel1051 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ASSIGN_in_token1068 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_token1070 = new BitSet(new long[]{-9223372036854472704L, 1090527232L});
   public static final BitSet FOLLOW_token_in_token1074 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PLUS_ASSIGN_in_token1083 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_token1085 = new BitSet(new long[]{-9223372036854472704L, 1090527232L});
   public static final BitSet FOLLOW_token_in_token1089 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ROOT_in_token1098 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_token_in_token1102 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BANG_in_token1111 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_token_in_token1115 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_token1125 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_token1139 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TOKEN_REF_in_token1151 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup1172 = new BitSet(new long[]{274878038018L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1175 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1181 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CATCH_in_exceptionHandler1196 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler1198 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_exceptionHandler1200 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_FINALLY_in_finallyClause1213 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_finallyClause1215 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_REWRITES_in_rewrite1228 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_REWRITE_in_rewrite1234 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_SEMPRED_in_rewrite1236 = new BitSet(new long[]{137438953744L, 134217728L});
   public static final BitSet FOLLOW_ALT_in_rewrite1241 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_TEMPLATE_in_rewrite1248 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_rewrite1254 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ETC_in_rewrite1256 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ROOT_in_element1276 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element1278 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BANG_in_element1285 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element1287 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_atom_in_element1293 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NOT_in_element1299 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element1301 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RANGE_in_element1308 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_atom_in_element1310 = new BitSet(new long[]{537133056L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_element1312 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ASSIGN_in_element1319 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element1321 = new BitSet(new long[]{-9043225268081795056L, 22666616897L});
   public static final BitSet FOLLOW_element_in_element1323 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PLUS_ASSIGN_in_element1330 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element1332 = new BitSet(new long[]{-9043225268081795056L, 22666616897L});
   public static final BitSet FOLLOW_element_in_element1334 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ebnf_in_element1340 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_tree__in_element1345 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYNPRED_in_element1351 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_element1353 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_FORCED_ACTION_in_element1360 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ACTION_in_element1365 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SEMPRED_in_element1370 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYN_SEMPRED_in_element1375 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_element1380 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_GATED_SEMPRED_in_element1385 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_EPSILON_in_element1390 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_block_in_ebnf1402 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPTIONAL_in_ebnf1414 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1416 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CLOSURE_in_ebnf1431 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1433 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_ebnf1449 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1451 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_BEGIN_in_tree_1469 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_tree_1471 = new BitSet(new long[]{-9043225268081795048L, 22666616897L});
   public static final BitSet FOLLOW_RULE_REF_in_atom1485 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1487 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TOKEN_REF_in_atom1495 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1497 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom1504 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_atom1509 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_WILDCARD_in_atom1514 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_DOT_in_atom1520 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_atom1522 = new BitSet(new long[]{537133056L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_atom1524 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_binaryMultipleOp_in_synpred1_LeftRecursiveRuleWalker484 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_binary_in_synpred2_LeftRecursiveRuleWalker530 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ternary_in_synpred3_LeftRecursiveRuleWalker593 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_prefix_in_synpred4_LeftRecursiveRuleWalker648 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_suffix_in_synpred5_LeftRecursiveRuleWalker704 = new BitSet(new long[]{2L});

   public TreeParser[] getDelegates() {
      return new TreeParser[0];
   }

   public LeftRecursiveRuleWalker(TreeNodeStream input) {
      this(input, new RecognizerSharedState());
   }

   public LeftRecursiveRuleWalker(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\antlr\\grammar\\v3\\LeftRecursiveRuleWalker.g";
   }

   public void reportError(RecognitionException ex) {
      Token token = null;
      if (ex instanceof MismatchedTokenException) {
         token = ((MismatchedTokenException)ex).token;
      } else if (ex instanceof NoViableAltException) {
         token = ((NoViableAltException)ex).token;
      }

      ErrorManager.syntaxError(100, this.grammar, token, "assign.types: " + ex.toString(), ex);
   }

   public void setTokenPrec(GrammarAST t, int alt) {
   }

   public void binaryAlt(GrammarAST altTree, GrammarAST rewriteTree, int alt) {
   }

   public void ternaryAlt(GrammarAST altTree, GrammarAST rewriteTree, int alt) {
   }

   public void prefixAlt(GrammarAST altTree, GrammarAST rewriteTree, int alt) {
   }

   public void suffixAlt(GrammarAST altTree, GrammarAST rewriteTree, int alt) {
   }

   public void otherAlt(GrammarAST altTree, GrammarAST rewriteTree, int alt) {
   }

   public void setReturnValues(GrammarAST t) {
   }

   public final void optionsSpec() throws RecognitionException {
      try {
         this.match(this.input, 58, FOLLOW_OPTIONS_in_optionsSpec57);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int cnt1 = 0;

               while(true) {
                  int alt1 = 2;
                  int LA1_0 = this.input.LA(1);
                  if (LA1_0 == 13) {
                     alt1 = 1;
                  }

                  switch (alt1) {
                     case 1:
                        this.pushFollow(FOLLOW_option_in_optionsSpec59);
                        this.option();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }

                        ++cnt1;
                        break;
                     default:
                        if (cnt1 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(1, this.input);
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

   public final void option() throws RecognitionException {
      try {
         try {
            this.match(this.input, 13, FOLLOW_ASSIGN_in_option73);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 43, FOLLOW_ID_in_option75);
            if (this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_optionValue_in_option77);
            this.optionValue();
            --this.state._fsp;
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

   public final void optionValue() throws RecognitionException {
      try {
         try {
            if (this.input.LA(1) != 18 && this.input.LA(1) != 43 && this.input.LA(1) != 47 && this.input.LA(1) != 88) {
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

   public final void charSetElement() throws RecognitionException {
      try {
         try {
            int alt2 = true;
            byte alt2;
            switch (this.input.LA(1)) {
               case 18:
                  alt2 = 1;
                  break;
               case 59:
                  alt2 = 2;
                  break;
               case 70:
                  alt2 = 3;
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
                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_charSetElement115);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.match(this.input, 59, FOLLOW_OR_in_charSetElement121);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_charSetElement123);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_charSetElement125);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 3:
                  this.match(this.input, 70, FOLLOW_RANGE_in_charSetElement132);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_charSetElement134);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_charSetElement136);
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

   public final boolean rec_rule(Grammar g) throws RecognitionException {
      boolean isLeftRec = false;
      GrammarAST r = null;
      GrammarAST id = null;
      TreeRuleReturnScope ruleBlock1 = null;
      this.grammar = g;
      this.outerAlt = 1;

      try {
         r = (GrammarAST)this.match(this.input, 79, FOLLOW_RULE_in_rec_rule164);
         if (this.state.failed) {
            return isLeftRec;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return isLeftRec;
            } else {
               id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_rec_rule168);
               if (this.state.failed) {
                  return isLeftRec;
               } else {
                  if (this.state.backtracking == 0) {
                     this.ruleName = id.getText();
                  }

                  int alt3 = 2;
                  int LA3_0 = this.input.LA(1);
                  if (LA3_0 == 40 || LA3_0 >= 66 && LA3_0 <= 68) {
                     alt3 = 1;
                  }

                  switch (alt3) {
                     case 1:
                        this.pushFollow(FOLLOW_modifier_in_rec_rule175);
                        this.modifier();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return isLeftRec;
                        }
                     default:
                        this.match(this.input, 10, FOLLOW_ARG_in_rec_rule182);
                        if (this.state.failed) {
                           return isLeftRec;
                        }

                        byte alt5;
                        int LA5_0;
                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           if (this.state.failed) {
                              return isLeftRec;
                           }

                           alt5 = 2;
                           LA5_0 = this.input.LA(1);
                           if (LA5_0 == 12) {
                              alt5 = 1;
                           }

                           switch (alt5) {
                              case 1:
                                 this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rec_rule184);
                                 if (this.state.failed) {
                                    return isLeftRec;
                                 }
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                                 if (this.state.failed) {
                                    return isLeftRec;
                                 }
                           }
                        }

                        this.match(this.input, 73, FOLLOW_RET_in_rec_rule192);
                        if (this.state.failed) {
                           return isLeftRec;
                        }

                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           if (this.state.failed) {
                              return isLeftRec;
                           }

                           alt5 = 2;
                           LA5_0 = this.input.LA(1);
                           if (LA5_0 == 12) {
                              alt5 = 1;
                           }

                           switch (alt5) {
                              case 1:
                                 this.match(this.input, 12, FOLLOW_ARG_ACTION_in_rec_rule194);
                                 if (this.state.failed) {
                                    return isLeftRec;
                                 }
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                                 if (this.state.failed) {
                                    return isLeftRec;
                                 }
                           }
                        }

                        alt5 = 2;
                        LA5_0 = this.input.LA(1);
                        if (LA5_0 == 58) {
                           alt5 = 1;
                        }

                        switch (alt5) {
                           case 1:
                              this.pushFollow(FOLLOW_optionsSpec_in_rec_rule201);
                              this.optionsSpec();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return isLeftRec;
                              }
                           default:
                              int alt7 = 2;
                              int LA7_0 = this.input.LA(1);
                              if (LA7_0 == 81) {
                                 alt7 = 1;
                              }

                              switch (alt7) {
                                 case 1:
                                    this.pushFollow(FOLLOW_ruleScopeSpec_in_rec_rule207);
                                    this.ruleScopeSpec();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return isLeftRec;
                                    }
                              }
                        }
                  }

                  label471:
                  while(true) {
                     do {
                        int alt10 = 2;
                        int LA10_0 = this.input.LA(1);
                        if (LA10_0 == 9) {
                           alt10 = 1;
                        }

                        switch (alt10) {
                           case 1:
                              this.match(this.input, 9, FOLLOW_AMPERSAND_in_rec_rule215);
                              if (this.state.failed) {
                                 return isLeftRec;
                              }
                              break;
                           default:
                              this.pushFollow(FOLLOW_ruleBlock_in_rec_rule226);
                              ruleBlock1 = this.ruleBlock();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return isLeftRec;
                              }

                              if (this.state.backtracking == 0) {
                                 isLeftRec = ruleBlock1 != null ? ((ruleBlock_return)ruleBlock1).isLeftRec : false;
                              }

                              alt10 = 2;
                              LA10_0 = this.input.LA(1);
                              if (LA10_0 == 17 || LA10_0 == 38) {
                                 alt10 = 1;
                              }

                              switch (alt10) {
                                 case 1:
                                    this.pushFollow(FOLLOW_exceptionGroup_in_rec_rule233);
                                    this.exceptionGroup();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return isLeftRec;
                                    }
                                 default:
                                    this.match(this.input, 34, FOLLOW_EOR_in_rec_rule239);
                                    if (this.state.failed) {
                                       return isLeftRec;
                                    }

                                    this.match(this.input, 3, (BitSet)null);
                                    if (this.state.failed) {
                                       return isLeftRec;
                                    }

                                    if (this.state.backtracking == 0 && ruleBlock1 != null && ((ruleBlock_return)ruleBlock1).isLeftRec) {
                                       r.setType(65);
                                    }

                                    return isLeftRec;
                              }
                        }
                     } while(this.input.LA(1) != 2);

                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return isLeftRec;
                     }

                     while(true) {
                        int alt8 = 2;
                        int LA8_0 = this.input.LA(1);
                        if (LA8_0 >= 4 && LA8_0 <= 102) {
                           alt8 = 1;
                        } else if (LA8_0 == 3) {
                           alt8 = 2;
                        }

                        switch (alt8) {
                           case 1:
                              this.matchAny(this.input);
                              if (this.state.failed) {
                                 return isLeftRec;
                              }
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return isLeftRec;
                              }
                              continue label471;
                        }
                     }
                  }
               }
            }
         }
      } catch (RecognitionException var20) {
         this.reportError(var20);
         this.recover(this.input, var20);
         return isLeftRec;
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

   public final void ruleScopeSpec() throws RecognitionException {
      try {
         try {
            this.match(this.input, 81, FOLLOW_SCOPE_in_ruleScopeSpec286);
            if (this.state.failed) {
               return;
            }

            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);
               if (this.state.failed) {
                  return;
               }

               int alt11 = 2;
               int LA11_0 = this.input.LA(1);
               if (LA11_0 == 4) {
                  alt11 = 1;
               }

               switch (alt11) {
                  case 1:
                     this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec288);
                     if (this.state.failed) {
                        return;
                     }
               }

               while(true) {
                  int alt12 = 2;
                  int LA12_0 = this.input.LA(1);
                  if (LA12_0 == 43) {
                     alt12 = 1;
                  }

                  switch (alt12) {
                     case 1:
                        this.match(this.input, 43, FOLLOW_ID_in_ruleScopeSpec291);
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
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final ruleBlock_return ruleBlock() throws RecognitionException {
      ruleBlock_return retval = new ruleBlock_return();
      retval.start = this.input.LT(1);
      TreeRuleReturnScope outerAlternative2 = null;
      boolean lr = false;
      this.numAlts = ((GrammarAST)retval.start).getChildCount();

      try {
         this.match(this.input, 16, FOLLOW_BLOCK_in_ruleBlock315);
         if (this.state.failed) {
            return retval;
         } else {
            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return retval;
            } else {
               int alt13 = 2;
               int LA13_0 = this.input.LA(1);
               if (LA13_0 == 58) {
                  alt13 = 1;
               }

               switch (alt13) {
                  case 1:
                     this.pushFollow(FOLLOW_optionsSpec_in_ruleBlock320);
                     this.optionsSpec();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }
                  default:
                     int cnt15 = 0;

                     while(true) {
                        int alt15 = 2;
                        int LA15_0 = this.input.LA(1);
                        if (LA15_0 == 8) {
                           alt15 = 1;
                        }

                        switch (alt15) {
                           case 1:
                              this.pushFollow(FOLLOW_outerAlternative_in_ruleBlock328);
                              outerAlternative2 = this.outerAlternative();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0 && outerAlternative2 != null && ((outerAlternative_return)outerAlternative2).isLeftRec) {
                                 retval.isLeftRec = true;
                              }

                              int alt14 = 2;
                              int LA14_0 = this.input.LA(1);
                              if (LA14_0 == 76) {
                                 alt14 = 1;
                              }

                              switch (alt14) {
                                 case 1:
                                    this.pushFollow(FOLLOW_rewrite_in_ruleBlock340);
                                    this.rewrite();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }
                                 default:
                                    if (this.state.backtracking == 0) {
                                       ++this.outerAlt;
                                    }

                                    ++cnt15;
                                    continue;
                              }
                           default:
                              if (cnt15 < 1) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return retval;
                                 }

                                 EarlyExitException eee = new EarlyExitException(15, this.input);
                                 throw eee;
                              }

                              this.match(this.input, 33, FOLLOW_EOB_in_ruleBlock358);
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
         }
      } catch (RecognitionException var15) {
         this.reportError(var15);
         this.recover(this.input, var15);
         return retval;
      } finally {
         ;
      }
   }

   public final void block() throws RecognitionException {
      try {
         this.match(this.input, 16, FOLLOW_BLOCK_in_block381);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int alt16 = 2;
               int LA16_0 = this.input.LA(1);
               if (LA16_0 == 58) {
                  alt16 = 1;
               }

               switch (alt16) {
                  case 1:
                     this.pushFollow(FOLLOW_optionsSpec_in_block395);
                     this.optionsSpec();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return;
                     }
                  default:
                     int cnt19 = 0;

                     label341:
                     while(true) {
                        int alt19 = 2;
                        int LA19_0 = this.input.LA(1);
                        if (LA19_0 == 8) {
                           alt19 = 1;
                        }

                        switch (alt19) {
                           case 1:
                              this.match(this.input, 8, FOLLOW_ALT_in_block413);
                              if (this.state.failed) {
                                 return;
                              }

                              this.match(this.input, 2, (BitSet)null);
                              if (this.state.failed) {
                                 return;
                              }

                              int cnt17 = 0;

                              while(true) {
                                 int alt18 = 2;
                                 int LA18_0 = this.input.LA(1);
                                 if (LA18_0 == 4 || LA18_0 >= 13 && LA18_0 <= 16 || LA18_0 == 18 || LA18_0 == 21 || LA18_0 == 29 || LA18_0 == 35 || LA18_0 == 39 || LA18_0 == 41 || LA18_0 == 55 || LA18_0 == 57 || LA18_0 >= 63 && LA18_0 <= 64 || LA18_0 == 70 || LA18_0 == 77 || LA18_0 == 80 || LA18_0 == 83 || LA18_0 >= 88 && LA18_0 <= 90 || LA18_0 == 94 || LA18_0 == 96 || LA18_0 == 98) {
                                    alt18 = 1;
                                 }

                                 switch (alt18) {
                                    case 1:
                                       this.pushFollow(FOLLOW_element_in_block415);
                                       this.element();
                                       --this.state._fsp;
                                       if (this.state.failed) {
                                          return;
                                       }

                                       ++cnt17;
                                       break;
                                    default:
                                       if (cnt17 < 1) {
                                          if (this.state.backtracking > 0) {
                                             this.state.failed = true;
                                             return;
                                          }

                                          EarlyExitException eee = new EarlyExitException(17, this.input);
                                          throw eee;
                                       }

                                       this.match(this.input, 32, FOLLOW_EOA_in_block418);
                                       if (this.state.failed) {
                                          return;
                                       }

                                       this.match(this.input, 3, (BitSet)null);
                                       if (this.state.failed) {
                                          return;
                                       }

                                       alt18 = 2;
                                       LA18_0 = this.input.LA(1);
                                       if (LA18_0 == 76) {
                                          alt18 = 1;
                                       }

                                       switch (alt18) {
                                          case 1:
                                             this.pushFollow(FOLLOW_rewrite_in_block421);
                                             this.rewrite();
                                             --this.state._fsp;
                                             if (this.state.failed) {
                                                return;
                                             }
                                          default:
                                             ++cnt19;
                                             continue label341;
                                       }
                                 }
                              }
                           default:
                              if (cnt19 < 1) {
                                 if (this.state.backtracking > 0) {
                                    this.state.failed = true;
                                    return;
                                 }

                                 EarlyExitException eee = new EarlyExitException(19, this.input);
                                 throw eee;
                              }

                              this.match(this.input, 33, FOLLOW_EOB_in_block439);
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
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         this.recover(this.input, var13);
      } finally {
         ;
      }
   }

   public final outerAlternative_return outerAlternative() throws RecognitionException {
      outerAlternative_return retval = new outerAlternative_return();
      retval.start = this.input.LT(1);
      GrammarAST rew = ((GrammarAST)retval.start).getNextSibling();
      if (rew.getType() != 76) {
         rew = null;
      }

      try {
         try {
            int alt21 = true;
            int LA21_0 = this.input.LA(1);
            if (LA21_0 != 8) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return retval;
               }

               NoViableAltException nvae = new NoViableAltException("", 21, 0, this.input);
               throw nvae;
            }

            int cnt20 = this.input.LA(2);
            byte alt21;
            if (this.synpred1_LeftRecursiveRuleWalker()) {
               alt21 = 1;
            } else if (this.synpred2_LeftRecursiveRuleWalker()) {
               alt21 = 2;
            } else if (this.synpred3_LeftRecursiveRuleWalker()) {
               alt21 = 3;
            } else if (this.synpred4_LeftRecursiveRuleWalker()) {
               alt21 = 4;
            } else if (this.synpred5_LeftRecursiveRuleWalker()) {
               alt21 = 5;
            } else {
               alt21 = 6;
            }

            switch (alt21) {
               case 1:
                  this.pushFollow(FOLLOW_binaryMultipleOp_in_outerAlternative488);
                  this.binaryMultipleOp();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.binaryAlt((GrammarAST)retval.start, rew, this.outerAlt);
                     retval.isLeftRec = true;
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_binary_in_outerAlternative544);
                  this.binary();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.binaryAlt((GrammarAST)retval.start, rew, this.outerAlt);
                     retval.isLeftRec = true;
                  }
                  break;
               case 3:
                  this.pushFollow(FOLLOW_ternary_in_outerAlternative606);
                  this.ternary();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.ternaryAlt((GrammarAST)retval.start, rew, this.outerAlt);
                     retval.isLeftRec = true;
                  }
                  break;
               case 4:
                  this.pushFollow(FOLLOW_prefix_in_outerAlternative662);
                  this.prefix();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.prefixAlt((GrammarAST)retval.start, rew, this.outerAlt);
                  }
                  break;
               case 5:
                  this.pushFollow(FOLLOW_suffix_in_outerAlternative718);
                  this.suffix();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.suffixAlt((GrammarAST)retval.start, rew, this.outerAlt);
                     retval.isLeftRec = true;
                  }
                  break;
               case 6:
                  this.match(this.input, 8, FOLLOW_ALT_in_outerAlternative760);
                  if (this.state.failed) {
                     return retval;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return retval;
                  }

                  cnt20 = 0;

                  while(true) {
                     int alt20 = 2;
                     int LA20_0 = this.input.LA(1);
                     if (LA20_0 == 4 || LA20_0 >= 13 && LA20_0 <= 16 || LA20_0 == 18 || LA20_0 == 21 || LA20_0 == 29 || LA20_0 == 35 || LA20_0 == 39 || LA20_0 == 41 || LA20_0 == 55 || LA20_0 == 57 || LA20_0 >= 63 && LA20_0 <= 64 || LA20_0 == 70 || LA20_0 == 77 || LA20_0 == 80 || LA20_0 == 83 || LA20_0 >= 88 && LA20_0 <= 90 || LA20_0 == 94 || LA20_0 == 96 || LA20_0 == 98) {
                        alt20 = 1;
                     }

                     switch (alt20) {
                        case 1:
                           this.pushFollow(FOLLOW_element_in_outerAlternative762);
                           this.element();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           ++cnt20;
                           break;
                        default:
                           if (cnt20 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(20, this.input);
                              throw eee;
                           }

                           this.match(this.input, 32, FOLLOW_EOA_in_outerAlternative765);
                           if (this.state.failed) {
                              return retval;
                           }

                           this.match(this.input, 3, (BitSet)null);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.otherAlt((GrammarAST)retval.start, rew, this.outerAlt);
                           }

                           return retval;
                     }
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

   public final void binary() throws RecognitionException {
      GrammarAST op = null;

      try {
         try {
            this.match(this.input, 8, FOLLOW_ALT_in_binary814);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            int alt23 = 2;
            int LA23_0 = this.input.LA(1);
            if (LA23_0 == 14) {
               alt23 = 1;
            }

            switch (alt23) {
               case 1:
                  this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_binary818);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     label189:
                     while(true) {
                        int alt22 = 2;
                        int LA22_0 = this.input.LA(1);
                        if (LA22_0 >= 4 && LA22_0 <= 102) {
                           alt22 = 1;
                        } else if (LA22_0 == 3) {
                           alt22 = 2;
                        }

                        switch (alt22) {
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
                              break label189;
                        }
                     }
                  }
            }

            this.pushFollow(FOLLOW_recurseNoLabel_in_binary826);
            this.recurseNoLabel();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_token_in_binary830);
            op = this.token();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_recurse_in_binary832);
            this.recurse();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 32, FOLLOW_EOA_in_binary834);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            if (this.state.backtracking == 0) {
               this.setTokenPrec(op, this.outerAlt);
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.recover(this.input, var9);
         }

      } finally {
         ;
      }
   }

   public final void binaryMultipleOp() throws RecognitionException {
      GrammarAST op = null;

      try {
         this.match(this.input, 8, FOLLOW_ALT_in_binaryMultipleOp851);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int alt25 = 2;
               int LA25_0 = this.input.LA(1);
               if (LA25_0 == 14) {
                  alt25 = 1;
               }

               int cnt26;
               switch (alt25) {
                  case 1:
                     this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_binaryMultipleOp855);
                     if (this.state.failed) {
                        return;
                     }

                     if (this.input.LA(1) == 2) {
                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        label316:
                        while(true) {
                           cnt26 = 2;
                           int LA24_0 = this.input.LA(1);
                           if (LA24_0 >= 4 && LA24_0 <= 102) {
                              cnt26 = 1;
                           } else if (LA24_0 == 3) {
                              cnt26 = 2;
                           }

                           switch (cnt26) {
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
                                 break label316;
                           }
                        }
                     }
               }

               this.pushFollow(FOLLOW_recurseNoLabel_in_binaryMultipleOp863);
               this.recurseNoLabel();
               --this.state._fsp;
               if (!this.state.failed) {
                  this.match(this.input, 16, FOLLOW_BLOCK_in_binaryMultipleOp867);
                  if (!this.state.failed) {
                     this.match(this.input, 2, (BitSet)null);
                     if (!this.state.failed) {
                        cnt26 = 0;

                        while(true) {
                           int alt26 = 2;
                           int LA26_0 = this.input.LA(1);
                           if (LA26_0 == 8) {
                              alt26 = 1;
                           }

                           switch (alt26) {
                              case 1:
                                 this.match(this.input, 8, FOLLOW_ALT_in_binaryMultipleOp873);
                                 if (this.state.failed) {
                                    return;
                                 }

                                 this.match(this.input, 2, (BitSet)null);
                                 if (this.state.failed) {
                                    return;
                                 }

                                 this.pushFollow(FOLLOW_token_in_binaryMultipleOp877);
                                 op = this.token();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return;
                                 }

                                 this.match(this.input, 32, FOLLOW_EOA_in_binaryMultipleOp879);
                                 if (this.state.failed) {
                                    return;
                                 }

                                 if (this.state.backtracking == 0) {
                                    this.setTokenPrec(op, this.outerAlt);
                                 }

                                 this.match(this.input, 3, (BitSet)null);
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

                                 this.match(this.input, 33, FOLLOW_EOB_in_binaryMultipleOp888);
                                 if (this.state.failed) {
                                    return;
                                 }

                                 this.match(this.input, 3, (BitSet)null);
                                 if (this.state.failed) {
                                    return;
                                 }

                                 this.pushFollow(FOLLOW_recurse_in_binaryMultipleOp892);
                                 this.recurse();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return;
                                 }

                                 this.match(this.input, 32, FOLLOW_EOA_in_binaryMultipleOp894);
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
               }
            }
         }
      } catch (RecognitionException var11) {
         this.reportError(var11);
         this.recover(this.input, var11);
      } finally {
         ;
      }
   }

   public final void ternary() throws RecognitionException {
      GrammarAST op = null;

      try {
         try {
            this.match(this.input, 8, FOLLOW_ALT_in_ternary909);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            int alt28 = 2;
            int LA28_0 = this.input.LA(1);
            if (LA28_0 == 14) {
               alt28 = 1;
            }

            switch (alt28) {
               case 1:
                  this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_ternary913);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     label209:
                     while(true) {
                        int alt27 = 2;
                        int LA27_0 = this.input.LA(1);
                        if (LA27_0 >= 4 && LA27_0 <= 102) {
                           alt27 = 1;
                        } else if (LA27_0 == 3) {
                           alt27 = 2;
                        }

                        switch (alt27) {
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
                              break label209;
                        }
                     }
                  }
            }

            this.pushFollow(FOLLOW_recurseNoLabel_in_ternary921);
            this.recurseNoLabel();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_token_in_ternary925);
            op = this.token();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_recurse_in_ternary927);
            this.recurse();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_token_in_ternary929);
            this.token();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }

            this.pushFollow(FOLLOW_recurse_in_ternary931);
            this.recurse();
            --this.state._fsp;
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 32, FOLLOW_EOA_in_ternary933);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 3, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            if (this.state.backtracking == 0) {
               this.setTokenPrec(op, this.outerAlt);
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.recover(this.input, var9);
         }

      } finally {
         ;
      }
   }

   public final void prefix() throws RecognitionException {
      try {
         this.match(this.input, 8, FOLLOW_ALT_in_prefix949);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int alt30 = 2;
               int LA30_0 = this.input.LA(1);
               int cnt31;
               if (LA30_0 == 14) {
                  cnt31 = this.input.LA(2);
                  if (cnt31 == 2) {
                     alt30 = 1;
                  }
               }

               switch (alt30) {
                  case 1:
                     this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_prefix953);
                     if (this.state.failed) {
                        return;
                     }

                     if (this.input.LA(1) == 2) {
                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        label513:
                        while(true) {
                           int alt29 = 2;
                           int LA29_0 = this.input.LA(1);
                           if (LA29_0 >= 4 && LA29_0 <= 102) {
                              alt29 = 1;
                           } else if (LA29_0 == 3) {
                              alt29 = 2;
                           }

                           switch (alt29) {
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
                                 break label513;
                           }
                        }
                     }
               }

               if (this.state.backtracking == 0) {
                  this.setTokenPrec((GrammarAST)this.input.LT(1), this.outerAlt);
               }

               cnt31 = 0;

               while(true) {
                  int alt31 = 2;
                  int LA31_3;
                  int LA31_6;
                  int LA31_9;
                  int LA31_11;
                  switch (this.input.LA(1)) {
                     case 4:
                     case 14:
                     case 15:
                     case 16:
                     case 18:
                     case 21:
                     case 29:
                     case 35:
                     case 39:
                     case 41:
                     case 55:
                     case 57:
                     case 64:
                     case 70:
                     case 77:
                     case 83:
                     case 88:
                     case 89:
                     case 90:
                     case 94:
                     case 96:
                     case 98:
                        alt31 = 1;
                     case 5:
                     case 6:
                     case 7:
                     case 8:
                     case 9:
                     case 10:
                     case 11:
                     case 12:
                     case 17:
                     case 19:
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
                        break;
                     case 13:
                        LA31_3 = this.input.LA(2);
                        if (LA31_3 != 2) {
                           break;
                        }

                        LA31_6 = this.input.LA(3);
                        if (LA31_6 != 43) {
                           break;
                        }

                        LA31_9 = this.input.LA(4);
                        if (LA31_9 == 80) {
                           LA31_11 = this.input.LA(5);
                           if (LA31_11 == 2) {
                              alt31 = 1;
                           }
                        } else if (LA31_9 == 4 || LA31_9 >= 13 && LA31_9 <= 16 || LA31_9 == 18 || LA31_9 == 21 || LA31_9 == 29 || LA31_9 == 35 || LA31_9 == 39 || LA31_9 == 41 || LA31_9 == 55 || LA31_9 == 57 || LA31_9 >= 63 && LA31_9 <= 64 || LA31_9 == 70 || LA31_9 == 77 || LA31_9 == 83 || LA31_9 >= 88 && LA31_9 <= 90 || LA31_9 == 94 || LA31_9 == 96 || LA31_9 == 98) {
                           alt31 = 1;
                        }
                        break;
                     case 63:
                        LA31_3 = this.input.LA(2);
                        if (LA31_3 != 2) {
                           break;
                        }

                        LA31_6 = this.input.LA(3);
                        if (LA31_6 != 43) {
                           break;
                        }

                        LA31_9 = this.input.LA(4);
                        if (LA31_9 == 80) {
                           LA31_11 = this.input.LA(5);
                           if (LA31_11 == 2) {
                              alt31 = 1;
                           }
                        } else if (LA31_9 == 4 || LA31_9 >= 13 && LA31_9 <= 16 || LA31_9 == 18 || LA31_9 == 21 || LA31_9 == 29 || LA31_9 == 35 || LA31_9 == 39 || LA31_9 == 41 || LA31_9 == 55 || LA31_9 == 57 || LA31_9 >= 63 && LA31_9 <= 64 || LA31_9 == 70 || LA31_9 == 77 || LA31_9 == 83 || LA31_9 >= 88 && LA31_9 <= 90 || LA31_9 == 94 || LA31_9 == 96 || LA31_9 == 98) {
                           alt31 = 1;
                        }
                        break;
                     case 80:
                        LA31_3 = this.input.LA(2);
                        if (LA31_3 == 2) {
                           alt31 = 1;
                        }
                  }

                  switch (alt31) {
                     case 1:
                        if (((CommonTree)this.input.LT(1)).getText().equals(this.ruleName)) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           throw new FailedPredicateException(this.input, "prefix", "!((CommonTree)input.LT(1)).getText().equals(ruleName)");
                        }

                        this.pushFollow(FOLLOW_element_in_prefix966);
                        this.element();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }

                        ++cnt31;
                        break;
                     default:
                        if (cnt31 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return;
                           }

                           EarlyExitException eee = new EarlyExitException(31, this.input);
                           throw eee;
                        }

                        this.pushFollow(FOLLOW_recurse_in_prefix970);
                        this.recurse();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return;
                        }

                        this.match(this.input, 32, FOLLOW_EOA_in_prefix972);
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
      } catch (RecognitionException var12) {
         this.reportError(var12);
         this.recover(this.input, var12);
      } finally {
         ;
      }
   }

   public final void suffix() throws RecognitionException {
      try {
         this.match(this.input, 8, FOLLOW_ALT_in_suffix985);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int alt33 = 2;
               int LA33_0 = this.input.LA(1);
               if (LA33_0 == 14) {
                  alt33 = 1;
               }

               int cnt34;
               switch (alt33) {
                  case 1:
                     this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_suffix989);
                     if (this.state.failed) {
                        return;
                     }

                     if (this.input.LA(1) == 2) {
                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        label322:
                        while(true) {
                           cnt34 = 2;
                           int LA32_0 = this.input.LA(1);
                           if (LA32_0 >= 4 && LA32_0 <= 102) {
                              cnt34 = 1;
                           } else if (LA32_0 == 3) {
                              cnt34 = 2;
                           }

                           switch (cnt34) {
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
                                 break label322;
                           }
                        }
                     }
               }

               this.pushFollow(FOLLOW_recurseNoLabel_in_suffix997);
               this.recurseNoLabel();
               --this.state._fsp;
               if (!this.state.failed) {
                  if (this.state.backtracking == 0) {
                     this.setTokenPrec((GrammarAST)this.input.LT(1), this.outerAlt);
                  }

                  cnt34 = 0;

                  while(true) {
                     int alt34 = 2;
                     int LA34_0 = this.input.LA(1);
                     if (LA34_0 == 4 || LA34_0 >= 13 && LA34_0 <= 16 || LA34_0 == 18 || LA34_0 == 21 || LA34_0 == 29 || LA34_0 == 35 || LA34_0 == 39 || LA34_0 == 41 || LA34_0 == 55 || LA34_0 == 57 || LA34_0 >= 63 && LA34_0 <= 64 || LA34_0 == 70 || LA34_0 == 77 || LA34_0 == 80 || LA34_0 == 83 || LA34_0 >= 88 && LA34_0 <= 90 || LA34_0 == 94 || LA34_0 == 96 || LA34_0 == 98) {
                        alt34 = 1;
                     }

                     switch (alt34) {
                        case 1:
                           this.pushFollow(FOLLOW_element_in_suffix1001);
                           this.element();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt34;
                           break;
                        default:
                           if (cnt34 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(34, this.input);
                              throw eee;
                           }

                           this.match(this.input, 32, FOLLOW_EOA_in_suffix1005);
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
         }
      } catch (RecognitionException var10) {
         this.reportError(var10);
         this.recover(this.input, var10);
      } finally {
         ;
      }
   }

   public final void recurse() throws RecognitionException {
      try {
         try {
            int alt35 = true;
            byte alt35;
            switch (this.input.LA(1)) {
               case 13:
                  alt35 = 1;
                  break;
               case 63:
                  alt35 = 2;
                  break;
               case 80:
                  alt35 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
                  throw nvae;
            }

            switch (alt35) {
               case 1:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_recurse1018);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 43, FOLLOW_ID_in_recurse1020);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_recurseNoLabel_in_recurse1022);
                  this.recurseNoLabel();
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
                  this.match(this.input, 63, FOLLOW_PLUS_ASSIGN_in_recurse1029);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 43, FOLLOW_ID_in_recurse1031);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_recurseNoLabel_in_recurse1033);
                  this.recurseNoLabel();
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
                  this.pushFollow(FOLLOW_recurseNoLabel_in_recurse1039);
                  this.recurseNoLabel();
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

   public final void recurseNoLabel() throws RecognitionException {
      try {
         try {
            if (!((CommonTree)this.input.LT(1)).getText().equals(this.ruleName)) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return;
               }

               throw new FailedPredicateException(this.input, "recurseNoLabel", "((CommonTree)input.LT(1)).getText().equals(ruleName)");
            }

            this.match(this.input, 80, FOLLOW_RULE_REF_in_recurseNoLabel1051);
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

   public final GrammarAST token() throws RecognitionException {
      GrammarAST t = null;
      GrammarAST a = null;
      GrammarAST b = null;
      GrammarAST c = null;
      GrammarAST s = null;

      try {
         try {
            int alt36 = true;
            byte alt36;
            switch (this.input.LA(1)) {
               case 13:
                  alt36 = 1;
                  break;
               case 15:
                  alt36 = 4;
                  break;
               case 18:
                  alt36 = 5;
                  break;
               case 63:
                  alt36 = 2;
                  break;
               case 77:
                  alt36 = 3;
                  break;
               case 88:
                  alt36 = 6;
                  break;
               case 94:
                  alt36 = 7;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return t;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 36, 0, this.input);
                  throw nvae;
            }

            switch (alt36) {
               case 1:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_token1068);
                  if (this.state.failed) {
                     return t;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return t;
                  }

                  this.match(this.input, 43, FOLLOW_ID_in_token1070);
                  if (this.state.failed) {
                     return t;
                  }

                  this.pushFollow(FOLLOW_token_in_token1074);
                  s = this.token();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return t;
                  }

                  if (this.state.backtracking == 0) {
                     t = s;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return t;
                  }
                  break;
               case 2:
                  this.match(this.input, 63, FOLLOW_PLUS_ASSIGN_in_token1083);
                  if (this.state.failed) {
                     return t;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return t;
                  }

                  this.match(this.input, 43, FOLLOW_ID_in_token1085);
                  if (this.state.failed) {
                     return t;
                  }

                  this.pushFollow(FOLLOW_token_in_token1089);
                  s = this.token();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return t;
                  }

                  if (this.state.backtracking == 0) {
                     t = s;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return t;
                  }
                  break;
               case 3:
                  this.match(this.input, 77, FOLLOW_ROOT_in_token1098);
                  if (this.state.failed) {
                     return t;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return t;
                  }

                  this.pushFollow(FOLLOW_token_in_token1102);
                  s = this.token();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return t;
                  }

                  if (this.state.backtracking == 0) {
                     t = s;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return t;
                  }
                  break;
               case 4:
                  this.match(this.input, 15, FOLLOW_BANG_in_token1111);
                  if (this.state.failed) {
                     return t;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return t;
                  }

                  this.pushFollow(FOLLOW_token_in_token1115);
                  s = this.token();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return t;
                  }

                  if (this.state.backtracking == 0) {
                     t = s;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return t;
                  }
                  break;
               case 5:
                  a = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_token1125);
                  if (this.state.failed) {
                     return t;
                  }

                  if (this.state.backtracking == 0) {
                     t = a;
                  }
                  break;
               case 6:
                  b = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_token1139);
                  if (this.state.failed) {
                     return t;
                  }

                  if (this.state.backtracking == 0) {
                     t = b;
                  }
                  break;
               case 7:
                  c = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_token1151);
                  if (this.state.failed) {
                     return t;
                  }

                  if (this.state.backtracking == 0) {
                     t = c;
                  }
            }
         } catch (RecognitionException var11) {
            this.reportError(var11);
            this.recover(this.input, var11);
         }

         return t;
      } finally {
         ;
      }
   }

   public final void exceptionGroup() throws RecognitionException {
      try {
         try {
            int alt39 = true;
            int LA39_0 = this.input.LA(1);
            byte alt39;
            if (LA39_0 == 17) {
               alt39 = 1;
            } else {
               if (LA39_0 != 38) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 39, 0, this.input);
                  throw nvae;
               }

               alt39 = 2;
            }

            switch (alt39) {
               case 1:
                  int cnt37 = 0;

                  while(true) {
                     int alt38 = 2;
                     int LA38_0 = this.input.LA(1);
                     if (LA38_0 == 17) {
                        alt38 = 1;
                     }

                     switch (alt38) {
                        case 1:
                           this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup1172);
                           this.exceptionHandler();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return;
                           }

                           ++cnt37;
                           break;
                        default:
                           if (cnt37 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return;
                              }

                              EarlyExitException eee = new EarlyExitException(37, this.input);
                              throw eee;
                           }

                           alt38 = 2;
                           LA38_0 = this.input.LA(1);
                           if (LA38_0 == 38) {
                              alt38 = 1;
                           }

                           switch (alt38) {
                              case 1:
                                 this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1175);
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
                  this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1181);
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
            this.match(this.input, 17, FOLLOW_CATCH_in_exceptionHandler1196);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 12, FOLLOW_ARG_ACTION_in_exceptionHandler1198);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler1200);
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
            this.match(this.input, 38, FOLLOW_FINALLY_in_finallyClause1213);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 2, (BitSet)null);
            if (this.state.failed) {
               return;
            }

            this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause1215);
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

   public final void rewrite() throws RecognitionException {
      try {
         try {
            this.match(this.input, 76, FOLLOW_REWRITES_in_rewrite1228);
            if (this.state.failed) {
               return;
            }

            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);
               if (this.state.failed) {
                  return;
               }

               while(true) {
                  int alt44 = 2;
                  int LA44_0 = this.input.LA(1);
                  if (LA44_0 == 75) {
                     alt44 = 1;
                  }

                  switch (alt44) {
                     case 1:
                        this.match(this.input, 75, FOLLOW_REWRITE_in_rewrite1234);
                        if (this.state.failed) {
                           return;
                        }

                        this.match(this.input, 2, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        int alt40 = 2;
                        int LA40_0 = this.input.LA(1);
                        if (LA40_0 == 83) {
                           alt40 = 1;
                        }

                        switch (alt40) {
                           case 1:
                              this.match(this.input, 83, FOLLOW_SEMPRED_in_rewrite1236);
                              if (this.state.failed) {
                                 return;
                              }
                           default:
                              int alt43 = true;
                              byte alt43;
                              switch (this.input.LA(1)) {
                                 case 4:
                                    alt43 = 3;
                                    break;
                                 case 8:
                                    alt43 = 1;
                                    break;
                                 case 37:
                                    alt43 = 4;
                                    break;
                                 case 91:
                                    alt43 = 2;
                                    break;
                                 default:
                                    if (this.state.backtracking > 0) {
                                       this.state.failed = true;
                                       return;
                                    }

                                    NoViableAltException nvae = new NoViableAltException("", 43, 0, this.input);
                                    throw nvae;
                              }

                              byte alt42;
                              int LA42_0;
                              label326:
                              switch (alt43) {
                                 case 1:
                                    this.match(this.input, 8, FOLLOW_ALT_in_rewrite1241);
                                    if (this.state.failed) {
                                       return;
                                    }

                                    if (this.input.LA(1) == 2) {
                                       this.match(this.input, 2, (BitSet)null);
                                       if (this.state.failed) {
                                          return;
                                       }

                                       while(true) {
                                          alt42 = 2;
                                          LA42_0 = this.input.LA(1);
                                          if (LA42_0 >= 4 && LA42_0 <= 102) {
                                             alt42 = 1;
                                          } else if (LA42_0 == 3) {
                                             alt42 = 2;
                                          }

                                          switch (alt42) {
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
                                                break label326;
                                          }
                                       }
                                    }
                                    break;
                                 case 2:
                                    this.match(this.input, 91, FOLLOW_TEMPLATE_in_rewrite1248);
                                    if (this.state.failed) {
                                       return;
                                    }

                                    if (this.input.LA(1) == 2) {
                                       this.match(this.input, 2, (BitSet)null);
                                       if (this.state.failed) {
                                          return;
                                       }

                                       while(true) {
                                          alt42 = 2;
                                          LA42_0 = this.input.LA(1);
                                          if (LA42_0 >= 4 && LA42_0 <= 102) {
                                             alt42 = 1;
                                          } else if (LA42_0 == 3) {
                                             alt42 = 2;
                                          }

                                          switch (alt42) {
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
                                                break label326;
                                          }
                                       }
                                    }
                                    break;
                                 case 3:
                                    this.match(this.input, 4, FOLLOW_ACTION_in_rewrite1254);
                                    if (this.state.failed) {
                                       return;
                                    }
                                    break;
                                 case 4:
                                    this.match(this.input, 37, FOLLOW_ETC_in_rewrite1256);
                                    if (this.state.failed) {
                                       return;
                                    }
                              }

                              this.match(this.input, 3, (BitSet)null);
                              if (this.state.failed) {
                                 return;
                              }
                              continue;
                        }
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        if (this.state.failed) {
                           return;
                        }

                        return;
                  }
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

   public final void element() throws RecognitionException {
      try {
         try {
            int alt45 = true;
            byte alt45;
            switch (this.input.LA(1)) {
               case 4:
                  alt45 = 12;
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
               case 19:
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
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 45, 0, this.input);
                  throw nvae;
               case 13:
                  alt45 = 6;
                  break;
               case 14:
                  alt45 = 15;
                  break;
               case 15:
                  alt45 = 2;
                  break;
               case 16:
               case 21:
               case 57:
               case 64:
                  alt45 = 8;
                  break;
               case 18:
               case 29:
               case 80:
               case 88:
               case 94:
               case 98:
                  alt45 = 3;
                  break;
               case 35:
                  alt45 = 17;
                  break;
               case 39:
                  alt45 = 11;
                  break;
               case 41:
                  alt45 = 16;
                  break;
               case 55:
                  alt45 = 4;
                  break;
               case 63:
                  alt45 = 7;
                  break;
               case 70:
                  alt45 = 5;
                  break;
               case 77:
                  alt45 = 1;
                  break;
               case 83:
                  alt45 = 13;
                  break;
               case 89:
                  alt45 = 10;
                  break;
               case 90:
                  alt45 = 14;
                  break;
               case 96:
                  alt45 = 9;
            }

            switch (alt45) {
               case 1:
                  this.match(this.input, 77, FOLLOW_ROOT_in_element1276);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_element_in_element1278);
                  this.element();
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
                  this.match(this.input, 15, FOLLOW_BANG_in_element1285);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_element_in_element1287);
                  this.element();
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
                  this.pushFollow(FOLLOW_atom_in_element1293);
                  this.atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 4:
                  this.match(this.input, 55, FOLLOW_NOT_in_element1299);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_element_in_element1301);
                  this.element();
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
                  this.match(this.input, 70, FOLLOW_RANGE_in_element1308);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_atom_in_element1310);
                  this.atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_atom_in_element1312);
                  this.atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 6:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_element1319);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 43, FOLLOW_ID_in_element1321);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_element_in_element1323);
                  this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 7:
                  this.match(this.input, 63, FOLLOW_PLUS_ASSIGN_in_element1330);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 43, FOLLOW_ID_in_element1332);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_element_in_element1334);
                  this.element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 8:
                  this.pushFollow(FOLLOW_ebnf_in_element1340);
                  this.ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 9:
                  this.pushFollow(FOLLOW_tree__in_element1345);
                  this.tree_();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 10:
                  this.match(this.input, 89, FOLLOW_SYNPRED_in_element1351);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_block_in_element1353);
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
               case 11:
                  this.match(this.input, 39, FOLLOW_FORCED_ACTION_in_element1360);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 12:
                  this.match(this.input, 4, FOLLOW_ACTION_in_element1365);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 13:
                  this.match(this.input, 83, FOLLOW_SEMPRED_in_element1370);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 14:
                  this.match(this.input, 90, FOLLOW_SYN_SEMPRED_in_element1375);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 15:
                  this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_element1380);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 16:
                  this.match(this.input, 41, FOLLOW_GATED_SEMPRED_in_element1385);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 17:
                  this.match(this.input, 35, FOLLOW_EPSILON_in_element1390);
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

   public final void ebnf() throws RecognitionException {
      try {
         try {
            int alt46 = true;
            byte alt46;
            switch (this.input.LA(1)) {
               case 16:
                  alt46 = 1;
                  break;
               case 21:
                  alt46 = 3;
                  break;
               case 57:
                  alt46 = 2;
                  break;
               case 64:
                  alt46 = 4;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 46, 0, this.input);
                  throw nvae;
            }

            switch (alt46) {
               case 1:
                  this.pushFollow(FOLLOW_block_in_ebnf1402);
                  this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 2:
                  this.match(this.input, 57, FOLLOW_OPTIONAL_in_ebnf1414);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1416);
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
               case 3:
                  this.match(this.input, 21, FOLLOW_CLOSURE_in_ebnf1431);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1433);
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
                  this.match(this.input, 64, FOLLOW_POSITIVE_CLOSURE_in_ebnf1449);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_block_in_ebnf1451);
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
         this.match(this.input, 96, FOLLOW_TREE_BEGIN_in_tree_1469);
         if (!this.state.failed) {
            this.match(this.input, 2, (BitSet)null);
            if (!this.state.failed) {
               int cnt47 = 0;

               while(true) {
                  int alt47 = 2;
                  int LA47_0 = this.input.LA(1);
                  if (LA47_0 == 4 || LA47_0 >= 13 && LA47_0 <= 16 || LA47_0 == 18 || LA47_0 == 21 || LA47_0 == 29 || LA47_0 == 35 || LA47_0 == 39 || LA47_0 == 41 || LA47_0 == 55 || LA47_0 == 57 || LA47_0 >= 63 && LA47_0 <= 64 || LA47_0 == 70 || LA47_0 == 77 || LA47_0 == 80 || LA47_0 == 83 || LA47_0 >= 88 && LA47_0 <= 90 || LA47_0 == 94 || LA47_0 == 96 || LA47_0 == 98) {
                     alt47 = 1;
                  }

                  switch (alt47) {
                     case 1:
                        this.pushFollow(FOLLOW_element_in_tree_1471);
                        this.element();
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

   public final void atom() throws RecognitionException {
      try {
         try {
            int alt50 = true;
            byte alt50;
            switch (this.input.LA(1)) {
               case 18:
                  alt50 = 3;
                  break;
               case 29:
                  alt50 = 6;
                  break;
               case 80:
                  alt50 = 1;
                  break;
               case 88:
                  alt50 = 4;
                  break;
               case 94:
                  alt50 = 2;
                  break;
               case 98:
                  alt50 = 5;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 50, 0, this.input);
                  throw nvae;
            }

            byte alt49;
            int LA49_0;
            switch (alt50) {
               case 1:
                  this.match(this.input, 80, FOLLOW_RULE_REF_in_atom1485);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     alt49 = 2;
                     LA49_0 = this.input.LA(1);
                     if (LA49_0 == 12) {
                        alt49 = 1;
                     }

                     switch (alt49) {
                        case 1:
                           this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1487);
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
                  break;
               case 2:
                  this.match(this.input, 94, FOLLOW_TOKEN_REF_in_atom1495);
                  if (this.state.failed) {
                     return;
                  }

                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     if (this.state.failed) {
                        return;
                     }

                     alt49 = 2;
                     LA49_0 = this.input.LA(1);
                     if (LA49_0 == 12) {
                        alt49 = 1;
                     }

                     switch (alt49) {
                        case 1:
                           this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1497);
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
                  break;
               case 3:
                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_atom1504);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 4:
                  this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_atom1509);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 5:
                  this.match(this.input, 98, FOLLOW_WILDCARD_in_atom1514);
                  if (this.state.failed) {
                     return;
                  }
                  break;
               case 6:
                  this.match(this.input, 29, FOLLOW_DOT_in_atom1520);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 2, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 43, FOLLOW_ID_in_atom1522);
                  if (this.state.failed) {
                     return;
                  }

                  this.pushFollow(FOLLOW_atom_in_atom1524);
                  this.atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  if (this.state.failed) {
                     return;
                  }
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
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

   public final void synpred1_LeftRecursiveRuleWalker_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_binaryMultipleOp_in_synpred1_LeftRecursiveRuleWalker484);
      this.binaryMultipleOp();
      --this.state._fsp;
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred2_LeftRecursiveRuleWalker_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_binary_in_synpred2_LeftRecursiveRuleWalker530);
      this.binary();
      --this.state._fsp;
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred3_LeftRecursiveRuleWalker_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_ternary_in_synpred3_LeftRecursiveRuleWalker593);
      this.ternary();
      --this.state._fsp;
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred4_LeftRecursiveRuleWalker_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_prefix_in_synpred4_LeftRecursiveRuleWalker648);
      this.prefix();
      --this.state._fsp;
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred5_LeftRecursiveRuleWalker_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_suffix_in_synpred5_LeftRecursiveRuleWalker704);
      this.suffix();
      --this.state._fsp;
      if (!this.state.failed) {
         ;
      }
   }

   public final boolean synpred5_LeftRecursiveRuleWalker() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred5_LeftRecursiveRuleWalker_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred4_LeftRecursiveRuleWalker() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred4_LeftRecursiveRuleWalker_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred2_LeftRecursiveRuleWalker() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred2_LeftRecursiveRuleWalker_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred1_LeftRecursiveRuleWalker() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred1_LeftRecursiveRuleWalker_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred3_LeftRecursiveRuleWalker() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred3_LeftRecursiveRuleWalker_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public static class outerAlternative_return extends TreeRuleReturnScope {
      public boolean isLeftRec;
   }

   public static class ruleBlock_return extends TreeRuleReturnScope {
      public boolean isLeftRec;
   }
}
