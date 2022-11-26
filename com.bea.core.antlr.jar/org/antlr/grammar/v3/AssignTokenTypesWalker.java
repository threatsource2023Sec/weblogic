package org.antlr.grammar.v3;

import java.util.HashMap;
import java.util.Map;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.TreeAdaptor;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.antlr.tool.ErrorManager;
import org.antlr.tool.Grammar;
import org.antlr.tool.GrammarAST;

public class AssignTokenTypesWalker extends TreeParser {
   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTION", "ACTION_CHAR_LITERAL", "ACTION_ESC", "ACTION_STRING_LITERAL", "ALT", "AMPERSAND", "ARG", "ARGLIST", "ARG_ACTION", "ASSIGN", "BACKTRACK_SEMPRED", "BANG", "BLOCK", "CATCH", "CHAR_LITERAL", "CHAR_RANGE", "CLOSE_ELEMENT_OPTION", "CLOSURE", "COLON", "COMBINED_GRAMMAR", "COMMA", "COMMENT", "DIGIT", "DOC_COMMENT", "DOLLAR", "DOT", "DOUBLE_ANGLE_STRING_LITERAL", "DOUBLE_QUOTE_STRING_LITERAL", "EOA", "EOB", "EOR", "EPSILON", "ESC", "ETC", "FINALLY", "FORCED_ACTION", "FRAGMENT", "GATED_SEMPRED", "GRAMMAR", "ID", "IMPLIES", "IMPORT", "INITACTION", "INT", "LABEL", "LEXER", "LEXER_GRAMMAR", "LPAREN", "ML_COMMENT", "NESTED_ACTION", "NESTED_ARG_ACTION", "NOT", "OPEN_ELEMENT_OPTION", "OPTIONAL", "OPTIONS", "OR", "PARSER", "PARSER_GRAMMAR", "PLUS", "PLUS_ASSIGN", "POSITIVE_CLOSURE", "PREC_RULE", "PRIVATE", "PROTECTED", "PUBLIC", "QUESTION", "RANGE", "RCURLY", "RECURSIVE_RULE_REF", "RET", "RETURNS", "REWRITE", "REWRITES", "ROOT", "RPAREN", "RULE", "RULE_REF", "SCOPE", "SEMI", "SEMPRED", "SL_COMMENT", "SRC", "STAR", "STRAY_BRACKET", "STRING_LITERAL", "SYNPRED", "SYN_SEMPRED", "TEMPLATE", "THROWS", "TOKENS", "TOKEN_REF", "TREE", "TREE_BEGIN", "TREE_GRAMMAR", "WILDCARD", "WS", "WS_LOOP", "WS_OPT", "XDIGIT", "CHARSET"};
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
   public static final int CHARSET = 103;
   protected Grammar grammar;
   protected String currentRuleName;
   protected static GrammarAST stringAlias;
   protected static GrammarAST charAlias;
   protected static GrammarAST stringAlias2;
   protected static GrammarAST charAlias2;
   public static final BitSet FOLLOW_LEXER_GRAMMAR_in_grammar_69 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_74 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PARSER_GRAMMAR_in_grammar_84 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_88 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_GRAMMAR_in_grammar_98 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_104 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_COMBINED_GRAMMAR_in_grammar_114 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_grammarSpec_in_grammar_116 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_grammarSpec135 = new BitSet(new long[]{288265560658018816L, 537034754L});
   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarSpec142 = new BitSet(new long[]{288265560523801088L, 537034754L});
   public static final BitSet FOLLOW_optionsSpec_in_grammarSpec149 = new BitSet(new long[]{35184372089344L, 537034754L});
   public static final BitSet FOLLOW_delegateGrammars_in_grammarSpec156 = new BitSet(new long[]{512L, 537034754L});
   public static final BitSet FOLLOW_tokensSpec_in_grammarSpec163 = new BitSet(new long[]{512L, 163842L});
   public static final BitSet FOLLOW_attrScope_in_grammarSpec170 = new BitSet(new long[]{512L, 163842L});
   public static final BitSet FOLLOW_AMPERSAND_in_grammarSpec179 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_rules_in_grammarSpec191 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SCOPE_in_attrScope204 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_attrScope206 = new BitSet(new long[]{528L});
   public static final BitSet FOLLOW_AMPERSAND_in_attrScope211 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_attrScope220 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec239 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_option_in_optionsSpec242 = new BitSet(new long[]{8200L});
   public static final BitSet FOLLOW_ASSIGN_in_option261 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_option263 = new BitSet(new long[]{149533581639680L, 16777216L});
   public static final BitSet FOLLOW_optionValue_in_option265 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_optionValue291 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_optionValue296 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_optionValue301 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_INT_in_optionValue306 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CHARSET_in_charSet324 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_charSetElement_in_charSet326 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_charSetElement339 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OR_in_charSetElement346 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_charSetElement348 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_charSetElement350 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RANGE_in_charSetElement359 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_charSetElement361 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_charSetElement363 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_IMPORT_in_delegateGrammars378 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ASSIGN_in_delegateGrammars386 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_delegateGrammars388 = new BitSet(new long[]{8796093022208L});
   public static final BitSet FOLLOW_ID_in_delegateGrammars390 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_delegateGrammars398 = new BitSet(new long[]{8796093030408L});
   public static final BitSet FOLLOW_TOKENS_in_tokensSpec420 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_tokenSpec_in_tokensSpec422 = new BitSet(new long[]{8200L, 1073741824L});
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec437 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ASSIGN_in_tokenSpec457 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec464 = new BitSet(new long[]{262144L, 16777216L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_tokenSpec481 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_tokenSpec492 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_rule_in_rules516 = new BitSet(new long[]{2L, 32770L});
   public static final BitSet FOLLOW_RULE_in_rule529 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ruleBody_in_rule531 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PREC_RULE_in_rule538 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ruleBody_in_rule540 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ID_in_ruleBody554 = new BitSet(new long[]{1099511628800L, 28L});
   public static final BitSet FOLLOW_modifier_in_ruleBody563 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_ARG_in_ruleBody570 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_ruleBody573 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RET_in_ruleBody581 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_ruleBody584 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_throwsSpec_in_ruleBody592 = new BitSet(new long[]{288230376151777792L, 131072L});
   public static final BitSet FOLLOW_optionsSpec_in_ruleBody599 = new BitSet(new long[]{66048L, 131072L});
   public static final BitSet FOLLOW_ruleScopeSpec_in_ruleBody606 = new BitSet(new long[]{66048L});
   public static final BitSet FOLLOW_AMPERSAND_in_ruleBody615 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ruleBody628 = new BitSet(new long[]{292057907200L});
   public static final BitSet FOLLOW_exceptionGroup_in_ruleBody633 = new BitSet(new long[]{17179869184L});
   public static final BitSet FOLLOW_EOR_in_ruleBody639 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_THROWS_in_throwsSpec681 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_throwsSpec683 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec698 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_AMPERSAND_in_ruleScopeSpec703 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec713 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_ID_in_ruleScopeSpec719 = new BitSet(new long[]{8796093022216L});
   public static final BitSet FOLLOW_BLOCK_in_block737 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_optionsSpec_in_block743 = new BitSet(new long[]{256L});
   public static final BitSet FOLLOW_alternative_in_block752 = new BitSet(new long[]{8589934848L, 4096L});
   public static final BitSet FOLLOW_rewrite_in_block754 = new BitSet(new long[]{8589934848L});
   public static final BitSet FOLLOW_EOB_in_block762 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ALT_in_alternative779 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_alternative782 = new BitSet(new long[]{-9043225263786303472L, 22666616897L});
   public static final BitSet FOLLOW_EOA_in_alternative786 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup801 = new BitSet(new long[]{274878038018L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup807 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup814 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_CATCH_in_exceptionHandler826 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler828 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_ACTION_in_exceptionHandler830 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_FINALLY_in_finallyClause843 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ACTION_in_finallyClause845 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_REWRITES_in_rewrite858 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_REWRITE_in_rewrite863 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ROOT_in_element887 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element889 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BANG_in_element896 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element898 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_atom_in_element904 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NOT_in_element910 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_element912 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_RANGE_in_element919 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_atom_in_element921 = new BitSet(new long[]{537133056L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_element923 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_RANGE_in_element930 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_atom_in_element932 = new BitSet(new long[]{537133056L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_element934 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ASSIGN_in_element941 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element943 = new BitSet(new long[]{-9043225268081270768L, 22666616897L});
   public static final BitSet FOLLOW_element_in_element945 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PLUS_ASSIGN_in_element952 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_element954 = new BitSet(new long[]{-9043225268081270768L, 22666616897L});
   public static final BitSet FOLLOW_element_in_element956 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ebnf_in_element962 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_tree__in_element967 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYNPRED_in_element974 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_element976 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_FORCED_ACTION_in_element983 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ACTION_in_element988 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SEMPRED_in_element993 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SYN_SEMPRED_in_element998 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BACKTRACK_SEMPRED_in_element1004 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_GATED_SEMPRED_in_element1013 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_EPSILON_in_element1018 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_block_in_ebnf1029 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPTIONAL_in_ebnf1036 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1038 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CLOSURE_in_ebnf1047 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1049 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_ebnf1058 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_block_in_ebnf1060 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TREE_BEGIN_in_tree_1074 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_element_in_tree_1076 = new BitSet(new long[]{-9043225268081270760L, 22666616897L});
   public static final BitSet FOLLOW_RULE_REF_in_atom1091 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1094 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TOKEN_REF_in_atom1107 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1110 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom1124 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_LITERAL_in_atom1135 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_WILDCARD_in_atom1142 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_DOT_in_atom1148 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_atom1150 = new BitSet(new long[]{537133056L, 18270453760L});
   public static final BitSet FOLLOW_atom_in_atom1152 = new BitSet(new long[]{8L});

   public TreeParser[] getDelegates() {
      return new TreeParser[0];
   }

   public AssignTokenTypesWalker(TreeNodeStream input) {
      this(input, new RecognizerSharedState());
   }

   public AssignTokenTypesWalker(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\antlr\\grammar\\v3\\AssignTokenTypesWalker.g";
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

   protected void initASTPatterns() {
      TreeAdaptor adaptor = new ANTLRParser.grammar_Adaptor((ANTLRParser)null);
      stringAlias = (GrammarAST)adaptor.create(16, (String)"BLOCK");
      GrammarAST alt = (GrammarAST)adaptor.create(8, (String)"ALT");
      adaptor.addChild(alt, adaptor.create(88, (String)"STRING_LITERAL"));
      adaptor.addChild(alt, adaptor.create(32, (String)"EOA"));
      adaptor.addChild(stringAlias, alt);
      adaptor.addChild(stringAlias, adaptor.create(33, (String)"EOB"));
      charAlias = (GrammarAST)adaptor.create(16, (String)"BLOCK");
      alt = (GrammarAST)adaptor.create(8, (String)"ALT");
      adaptor.addChild(alt, adaptor.create(18, (String)"CHAR_LITERAL"));
      adaptor.addChild(alt, adaptor.create(32, (String)"EOA"));
      adaptor.addChild(charAlias, alt);
      adaptor.addChild(charAlias, adaptor.create(33, (String)"EOB"));
      stringAlias2 = (GrammarAST)adaptor.create(16, (String)"BLOCK");
      alt = (GrammarAST)adaptor.create(8, (String)"ALT");
      adaptor.addChild(alt, adaptor.create(88, (String)"STRING_LITERAL"));
      adaptor.addChild(alt, adaptor.create(4, (String)"ACTION"));
      adaptor.addChild(alt, adaptor.create(32, (String)"EOA"));
      adaptor.addChild(stringAlias2, alt);
      adaptor.addChild(stringAlias2, adaptor.create(33, (String)"EOB"));
      charAlias2 = (GrammarAST)adaptor.create(16, (String)"BLOCK");
      alt = (GrammarAST)adaptor.create(8, (String)"ALT");
      adaptor.addChild(alt, adaptor.create(18, (String)"CHAR_LITERAL"));
      adaptor.addChild(alt, adaptor.create(4, (String)"ACTION"));
      adaptor.addChild(alt, adaptor.create(32, (String)"EOA"));
      adaptor.addChild(charAlias2, alt);
      adaptor.addChild(charAlias2, adaptor.create(33, (String)"EOB"));
   }

   protected void trackString(GrammarAST t) {
   }

   protected void trackToken(GrammarAST t) {
   }

   protected void trackTokenRule(GrammarAST t, GrammarAST modifier, GrammarAST block) {
   }

   protected void alias(GrammarAST t, GrammarAST s) {
   }

   public void defineTokens(Grammar root) {
   }

   protected void defineStringLiteralsFromDelegates() {
   }

   protected void assignStringTypes(Grammar root) {
   }

   protected void aliasTokenIDsAndLiterals(Grammar root) {
   }

   protected void assignTokenIDTypes(Grammar root) {
   }

   protected void defineTokenNamesAndLiteralsInGrammar(Grammar root) {
   }

   protected void init(Grammar root) {
   }

   public final void grammar_(Grammar g) throws RecognitionException {
      if (this.state.backtracking == 0) {
         this.init(g);
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
                  NoViableAltException nvae = new NoViableAltException("", 1, 0, this.input);
                  throw nvae;
            }

            switch (alt1) {
               case 1:
                  this.match(this.input, 50, FOLLOW_LEXER_GRAMMAR_in_grammar_69);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_74);
                  this.grammarSpec();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 61, FOLLOW_PARSER_GRAMMAR_in_grammar_84);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_88);
                  this.grammarSpec();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 3:
                  this.match(this.input, 97, FOLLOW_TREE_GRAMMAR_in_grammar_98);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_104);
                  this.grammarSpec();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 4:
                  this.match(this.input, 23, FOLLOW_COMBINED_GRAMMAR_in_grammar_114);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_grammarSpec_in_grammar_116);
                  this.grammarSpec();
                  --this.state._fsp;
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

   public final void grammarSpec() throws RecognitionException {
      GrammarAST id = null;
      GrammarAST cmt = null;

      try {
         id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_grammarSpec135);
         int alt2 = 2;
         int LA2_0 = this.input.LA(1);
         if (LA2_0 == 27) {
            alt2 = 1;
         }

         switch (alt2) {
            case 1:
               cmt = (GrammarAST)this.match(this.input, 27, FOLLOW_DOC_COMMENT_in_grammarSpec142);
            default:
               int alt3 = 2;
               int LA3_0 = this.input.LA(1);
               if (LA3_0 == 58) {
                  alt3 = 1;
               }

               switch (alt3) {
                  case 1:
                     this.pushFollow(FOLLOW_optionsSpec_in_grammarSpec149);
                     this.optionsSpec();
                     --this.state._fsp;
                  default:
                     int alt4 = 2;
                     int LA4_0 = this.input.LA(1);
                     if (LA4_0 == 45) {
                        alt4 = 1;
                     }

                     switch (alt4) {
                        case 1:
                           this.pushFollow(FOLLOW_delegateGrammars_in_grammarSpec156);
                           this.delegateGrammars();
                           --this.state._fsp;
                        default:
                           int alt5 = 2;
                           int LA5_0 = this.input.LA(1);
                           if (LA5_0 == 93) {
                              alt5 = 1;
                           }

                           switch (alt5) {
                              case 1:
                                 this.pushFollow(FOLLOW_tokensSpec_in_grammarSpec163);
                                 this.tokensSpec();
                                 --this.state._fsp;
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
                  this.pushFollow(FOLLOW_attrScope_in_grammarSpec170);
                  this.attrScope();
                  --this.state._fsp;
                  break;
               default:
                  label202:
                  while(true) {
                     do {
                        alt8 = 2;
                        LA8_0 = this.input.LA(1);
                        if (LA8_0 == 9) {
                           alt8 = 1;
                        }

                        switch (alt8) {
                           case 1:
                              this.match(this.input, 9, FOLLOW_AMPERSAND_in_grammarSpec179);
                              break;
                           default:
                              this.pushFollow(FOLLOW_rules_in_grammarSpec191);
                              this.rules();
                              --this.state._fsp;
                              return;
                        }
                     } while(this.input.LA(1) != 2);

                     this.match(this.input, 2, (BitSet)null);

                     while(true) {
                        int alt7 = 2;
                        int LA7_0 = this.input.LA(1);
                        if (LA7_0 >= 4 && LA7_0 <= 103) {
                           alt7 = 1;
                        } else if (LA7_0 == 3) {
                           alt7 = 2;
                        }

                        switch (alt7) {
                           case 1:
                              this.matchAny(this.input);
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              continue label202;
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

   public final void attrScope() throws RecognitionException {
      try {
         this.match(this.input, 81, FOLLOW_SCOPE_in_attrScope204);
         this.match(this.input, 2, (BitSet)null);
         this.match(this.input, 43, FOLLOW_ID_in_attrScope206);

         label102:
         while(true) {
            int alt10 = 2;
            int LA10_0 = this.input.LA(1);
            if (LA10_0 == 9) {
               alt10 = 1;
            }

            switch (alt10) {
               case 1:
                  this.match(this.input, 9, FOLLOW_AMPERSAND_in_attrScope211);
                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);

                     while(true) {
                        int alt9 = 2;
                        int LA9_0 = this.input.LA(1);
                        if (LA9_0 >= 4 && LA9_0 <= 103) {
                           alt9 = 1;
                        } else if (LA9_0 == 3) {
                           alt9 = 2;
                        }

                        switch (alt9) {
                           case 1:
                              this.matchAny(this.input);
                              break;
                           default:
                              this.match(this.input, 3, (BitSet)null);
                              continue label102;
                        }
                     }
                  }
                  break;
               default:
                  this.match(this.input, 4, FOLLOW_ACTION_in_attrScope220);
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

   public final Map optionsSpec() throws RecognitionException {
      Map opts = new HashMap();

      try {
         this.match(this.input, 58, FOLLOW_OPTIONS_in_optionsSpec239);
         this.match(this.input, 2, (BitSet)null);
         int cnt11 = 0;

         while(true) {
            int alt11 = 2;
            int LA11_0 = this.input.LA(1);
            if (LA11_0 == 13) {
               alt11 = 1;
            }

            switch (alt11) {
               case 1:
                  this.pushFollow(FOLLOW_option_in_optionsSpec242);
                  this.option(opts);
                  --this.state._fsp;
                  ++cnt11;
                  break;
               default:
                  if (cnt11 < 1) {
                     EarlyExitException eee = new EarlyExitException(11, this.input);
                     throw eee;
                  }

                  this.match(this.input, 3, (BitSet)null);
                  return opts;
            }
         }
      } catch (RecognitionException var9) {
         this.reportError(var9);
         this.recover(this.input, var9);
         return opts;
      } finally {
         ;
      }
   }

   public final void option(Map opts) throws RecognitionException {
      GrammarAST ID1 = null;
      TreeRuleReturnScope optionValue2 = null;

      try {
         try {
            this.match(this.input, 13, FOLLOW_ASSIGN_in_option261);
            this.match(this.input, 2, (BitSet)null);
            ID1 = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_option263);
            this.pushFollow(FOLLOW_optionValue_in_option265);
            optionValue2 = this.optionValue();
            --this.state._fsp;
            this.match(this.input, 3, (BitSet)null);
            String key = ID1 != null ? ID1.getText() : null;
            opts.put(key, optionValue2 != null ? ((optionValue_return)optionValue2).value : null);
            if (this.currentRuleName == null && key.equals("tokenVocab")) {
               this.grammar.importTokenVocabulary(ID1, (String)((String)(optionValue2 != null ? ((optionValue_return)optionValue2).value : null)));
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final optionValue_return optionValue() throws RecognitionException {
      optionValue_return retval = new optionValue_return();
      retval.start = this.input.LT(1);
      GrammarAST INT3 = null;
      if (this.state.backtracking == 0) {
         retval.value = ((GrammarAST)retval.start).getText();
      }

      try {
         try {
            int alt12 = true;
            byte alt12;
            switch (this.input.LA(1)) {
               case 18:
                  alt12 = 3;
                  break;
               case 43:
                  alt12 = 1;
                  break;
               case 47:
                  alt12 = 4;
                  break;
               case 88:
                  alt12 = 2;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
                  throw nvae;
            }

            switch (alt12) {
               case 1:
                  this.match(this.input, 43, FOLLOW_ID_in_optionValue291);
                  break;
               case 2:
                  this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_optionValue296);
                  break;
               case 3:
                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_optionValue301);
                  break;
               case 4:
                  INT3 = (GrammarAST)this.match(this.input, 47, FOLLOW_INT_in_optionValue306);
                  retval.value = Integer.parseInt(INT3 != null ? INT3.getText() : null);
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

   public final void charSet() throws RecognitionException {
      try {
         try {
            this.match(this.input, 103, FOLLOW_CHARSET_in_charSet324);
            this.match(this.input, 2, (BitSet)null);
            this.pushFollow(FOLLOW_charSetElement_in_charSet326);
            this.charSetElement();
            --this.state._fsp;
            this.match(this.input, 3, (BitSet)null);
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
            int alt13 = true;
            byte alt13;
            switch (this.input.LA(1)) {
               case 18:
                  alt13 = 1;
                  break;
               case 59:
                  alt13 = 2;
                  break;
               case 70:
                  alt13 = 3;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);
                  throw nvae;
            }

            switch (alt13) {
               case 1:
                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_charSetElement339);
                  break;
               case 2:
                  this.match(this.input, 59, FOLLOW_OR_in_charSetElement346);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_charSetElement348);
                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_charSetElement350);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 3:
                  this.match(this.input, 70, FOLLOW_RANGE_in_charSetElement359);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_charSetElement361);
                  this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_charSetElement363);
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

   public final void delegateGrammars() throws RecognitionException {
      try {
         this.match(this.input, 45, FOLLOW_IMPORT_in_delegateGrammars378);
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
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_delegateGrammars386);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 43, FOLLOW_ID_in_delegateGrammars388);
                  this.match(this.input, 43, FOLLOW_ID_in_delegateGrammars390);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 43, FOLLOW_ID_in_delegateGrammars398);
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
            this.match(this.input, 93, FOLLOW_TOKENS_in_tokensSpec420);
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
                        this.pushFollow(FOLLOW_tokenSpec_in_tokensSpec422);
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
      GrammarAST t = null;
      GrammarAST t2 = null;
      GrammarAST s = null;
      GrammarAST c = null;

      try {
         try {
            int alt17 = true;
            int LA17_0 = this.input.LA(1);
            byte alt17;
            if (LA17_0 == 94) {
               alt17 = 1;
            } else {
               if (LA17_0 != 13) {
                  NoViableAltException nvae = new NoViableAltException("", 17, 0, this.input);
                  throw nvae;
               }

               alt17 = 2;
            }

            switch (alt17) {
               case 1:
                  t = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_tokenSpec437);
                  this.trackToken(t);
                  break;
               case 2:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_tokenSpec457);
                  this.match(this.input, 2, (BitSet)null);
                  t2 = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_tokenSpec464);
                  this.trackToken(t2);
                  int alt16 = true;
                  int LA16_0 = this.input.LA(1);
                  byte alt16;
                  if (LA16_0 == 88) {
                     alt16 = 1;
                  } else {
                     if (LA16_0 != 18) {
                        NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
                        throw nvae;
                     }

                     alt16 = 2;
                  }

                  switch (alt16) {
                     case 1:
                        s = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_tokenSpec481);
                        this.trackString(s);
                        this.alias(t2, s);
                        break;
                     case 2:
                        c = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_tokenSpec492);
                        this.trackString(c);
                        this.alias(t2, c);
                  }

                  this.match(this.input, 3, (BitSet)null);
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.recover(this.input, var13);
         }

      } finally {
         ;
      }
   }

   public final void rules() throws RecognitionException {
      try {
         int cnt18 = 0;

         while(true) {
            int alt18 = 2;
            int LA18_0 = this.input.LA(1);
            if (LA18_0 == 65 || LA18_0 == 79) {
               alt18 = 1;
            }

            switch (alt18) {
               case 1:
                  this.pushFollow(FOLLOW_rule_in_rules516);
                  this.rule();
                  --this.state._fsp;
                  ++cnt18;
                  break;
               default:
                  if (cnt18 < 1) {
                     EarlyExitException eee = new EarlyExitException(18, this.input);
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

   public final void rule() throws RecognitionException {
      try {
         try {
            int alt19 = true;
            int LA19_0 = this.input.LA(1);
            byte alt19;
            if (LA19_0 == 79) {
               alt19 = 1;
            } else {
               if (LA19_0 != 65) {
                  NoViableAltException nvae = new NoViableAltException("", 19, 0, this.input);
                  throw nvae;
               }

               alt19 = 2;
            }

            switch (alt19) {
               case 1:
                  this.match(this.input, 79, FOLLOW_RULE_in_rule529);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_ruleBody_in_rule531);
                  this.ruleBody();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 65, FOLLOW_PREC_RULE_in_rule538);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_ruleBody_in_rule540);
                  this.ruleBody();
                  --this.state._fsp;
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

   public final void ruleBody() throws RecognitionException {
      GrammarAST id = null;
      TreeRuleReturnScope m = null;
      TreeRuleReturnScope b = null;

      try {
         id = (GrammarAST)this.match(this.input, 43, FOLLOW_ID_in_ruleBody554);
         this.currentRuleName = id != null ? id.getText() : null;
         int alt20 = 2;
         int LA20_0 = this.input.LA(1);
         if (LA20_0 == 40 || LA20_0 >= 66 && LA20_0 <= 68) {
            alt20 = 1;
         }

         switch (alt20) {
            case 1:
               this.pushFollow(FOLLOW_modifier_in_ruleBody563);
               m = this.modifier();
               --this.state._fsp;
            default:
               this.match(this.input, 10, FOLLOW_ARG_in_ruleBody570);
               byte alt23;
               int LA23_0;
               if (this.input.LA(1) == 2) {
                  this.match(this.input, 2, (BitSet)null);
                  alt23 = 2;
                  LA23_0 = this.input.LA(1);
                  if (LA23_0 == 12) {
                     alt23 = 1;
                  }

                  switch (alt23) {
                     case 1:
                        this.match(this.input, 12, FOLLOW_ARG_ACTION_in_ruleBody573);
                     default:
                        this.match(this.input, 3, (BitSet)null);
                  }
               }

               this.match(this.input, 73, FOLLOW_RET_in_ruleBody581);
               if (this.input.LA(1) == 2) {
                  this.match(this.input, 2, (BitSet)null);
                  alt23 = 2;
                  LA23_0 = this.input.LA(1);
                  if (LA23_0 == 12) {
                     alt23 = 1;
                  }

                  switch (alt23) {
                     case 1:
                        this.match(this.input, 12, FOLLOW_ARG_ACTION_in_ruleBody584);
                     default:
                        this.match(this.input, 3, (BitSet)null);
                  }
               }

               alt23 = 2;
               LA23_0 = this.input.LA(1);
               if (LA23_0 == 92) {
                  alt23 = 1;
               }

               switch (alt23) {
                  case 1:
                     this.pushFollow(FOLLOW_throwsSpec_in_ruleBody592);
                     this.throwsSpec();
                     --this.state._fsp;
                  default:
                     int alt24 = 2;
                     int LA24_0 = this.input.LA(1);
                     if (LA24_0 == 58) {
                        alt24 = 1;
                     }

                     switch (alt24) {
                        case 1:
                           this.pushFollow(FOLLOW_optionsSpec_in_ruleBody599);
                           this.optionsSpec();
                           --this.state._fsp;
                        default:
                           int alt25 = 2;
                           int LA25_0 = this.input.LA(1);
                           if (LA25_0 == 81) {
                              alt25 = 1;
                           }

                           switch (alt25) {
                              case 1:
                                 this.pushFollow(FOLLOW_ruleScopeSpec_in_ruleBody606);
                                 this.ruleScopeSpec();
                                 --this.state._fsp;
                           }
                     }
               }
         }

         label280:
         while(true) {
            do {
               int alt28 = 2;
               int LA28_0 = this.input.LA(1);
               if (LA28_0 == 9) {
                  alt28 = 1;
               }

               switch (alt28) {
                  case 1:
                     this.match(this.input, 9, FOLLOW_AMPERSAND_in_ruleBody615);
                     break;
                  default:
                     this.pushFollow(FOLLOW_block_in_ruleBody628);
                     b = this.block();
                     --this.state._fsp;
                     alt28 = 2;
                     LA28_0 = this.input.LA(1);
                     if (LA28_0 == 17 || LA28_0 == 38) {
                        alt28 = 1;
                     }

                     switch (alt28) {
                        case 1:
                           this.pushFollow(FOLLOW_exceptionGroup_in_ruleBody633);
                           this.exceptionGroup();
                           --this.state._fsp;
                        default:
                           this.match(this.input, 34, FOLLOW_EOR_in_ruleBody639);
                           this.trackTokenRule(id, m != null ? (GrammarAST)m.start : null, b != null ? (GrammarAST)b.start : null);
                           return;
                     }
               }
            } while(this.input.LA(1) != 2);

            this.match(this.input, 2, (BitSet)null);

            while(true) {
               int alt26 = 2;
               int LA26_0 = this.input.LA(1);
               if (LA26_0 >= 4 && LA26_0 <= 103) {
                  alt26 = 1;
               } else if (LA26_0 == 3) {
                  alt26 = 2;
               }

               switch (alt26) {
                  case 1:
                     this.matchAny(this.input);
                     break;
                  default:
                     this.match(this.input, 3, (BitSet)null);
                     continue label280;
               }
            }
         }
      } catch (RecognitionException var19) {
         this.reportError(var19);
         this.recover(this.input, var19);
      } finally {
         ;
      }
   }

   public final modifier_return modifier() throws RecognitionException {
      modifier_return retval = new modifier_return();
      retval.start = this.input.LT(1);

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
         this.match(this.input, 92, FOLLOW_THROWS_in_throwsSpec681);
         this.match(this.input, 2, (BitSet)null);
         int cnt29 = 0;

         while(true) {
            int alt29 = 2;
            int LA29_0 = this.input.LA(1);
            if (LA29_0 == 43) {
               alt29 = 1;
            }

            switch (alt29) {
               case 1:
                  this.match(this.input, 43, FOLLOW_ID_in_throwsSpec683);
                  ++cnt29;
                  break;
               default:
                  if (cnt29 < 1) {
                     EarlyExitException eee = new EarlyExitException(29, this.input);
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
            this.match(this.input, 81, FOLLOW_SCOPE_in_ruleScopeSpec698);
            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);

               label164:
               while(true) {
                  byte alt33;
                  int LA33_0;
                  do {
                     int alt32 = 2;
                     int LA32_0 = this.input.LA(1);
                     if (LA32_0 == 9) {
                        alt32 = 1;
                     }

                     switch (alt32) {
                        case 1:
                           this.match(this.input, 9, FOLLOW_AMPERSAND_in_ruleScopeSpec703);
                           break;
                        default:
                           alt32 = 2;
                           LA32_0 = this.input.LA(1);
                           if (LA32_0 == 4) {
                              alt32 = 1;
                           }

                           switch (alt32) {
                              case 1:
                                 this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec713);
                           }

                           while(true) {
                              alt33 = 2;
                              LA33_0 = this.input.LA(1);
                              if (LA33_0 == 43) {
                                 alt33 = 1;
                              }

                              switch (alt33) {
                                 case 1:
                                    this.match(this.input, 43, FOLLOW_ID_in_ruleScopeSpec719);
                                    break;
                                 default:
                                    this.match(this.input, 3, (BitSet)null);
                                    return;
                              }
                           }
                     }
                  } while(this.input.LA(1) != 2);

                  this.match(this.input, 2, (BitSet)null);

                  while(true) {
                     alt33 = 2;
                     LA33_0 = this.input.LA(1);
                     if (LA33_0 >= 4 && LA33_0 <= 103) {
                        alt33 = 1;
                     } else if (LA33_0 == 3) {
                        alt33 = 2;
                     }

                     switch (alt33) {
                        case 1:
                           this.matchAny(this.input);
                           break;
                        default:
                           this.match(this.input, 3, (BitSet)null);
                           continue label164;
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

      try {
         this.match(this.input, 16, FOLLOW_BLOCK_in_block737);
         this.match(this.input, 2, (BitSet)null);
         int alt34 = 2;
         int LA34_0 = this.input.LA(1);
         if (LA34_0 == 58) {
            alt34 = 1;
         }

         switch (alt34) {
            case 1:
               this.pushFollow(FOLLOW_optionsSpec_in_block743);
               this.optionsSpec();
               --this.state._fsp;
            default:
               int cnt35 = 0;

               while(true) {
                  int alt35 = 2;
                  int LA35_0 = this.input.LA(1);
                  if (LA35_0 == 8) {
                     alt35 = 1;
                  }

                  switch (alt35) {
                     case 1:
                        this.pushFollow(FOLLOW_alternative_in_block752);
                        this.alternative();
                        --this.state._fsp;
                        this.pushFollow(FOLLOW_rewrite_in_block754);
                        this.rewrite();
                        --this.state._fsp;
                        ++cnt35;
                        break;
                     default:
                        if (cnt35 < 1) {
                           EarlyExitException eee = new EarlyExitException(35, this.input);
                           throw eee;
                        }

                        this.match(this.input, 33, FOLLOW_EOB_in_block762);
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
         this.match(this.input, 8, FOLLOW_ALT_in_alternative779);
         this.match(this.input, 2, (BitSet)null);
         int cnt36 = 0;

         while(true) {
            int alt36 = 2;
            int LA36_0 = this.input.LA(1);
            if (LA36_0 == 4 || LA36_0 >= 13 && LA36_0 <= 16 || LA36_0 >= 18 && LA36_0 <= 19 || LA36_0 == 21 || LA36_0 == 29 || LA36_0 == 35 || LA36_0 == 39 || LA36_0 == 41 || LA36_0 == 55 || LA36_0 == 57 || LA36_0 >= 63 && LA36_0 <= 64 || LA36_0 == 70 || LA36_0 == 77 || LA36_0 == 80 || LA36_0 == 83 || LA36_0 >= 88 && LA36_0 <= 90 || LA36_0 == 94 || LA36_0 == 96 || LA36_0 == 98) {
               alt36 = 1;
            }

            switch (alt36) {
               case 1:
                  this.pushFollow(FOLLOW_element_in_alternative782);
                  this.element();
                  --this.state._fsp;
                  ++cnt36;
                  break;
               default:
                  if (cnt36 < 1) {
                     EarlyExitException eee = new EarlyExitException(36, this.input);
                     throw eee;
                  }

                  this.match(this.input, 32, FOLLOW_EOA_in_alternative786);
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
                           this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup801);
                           this.exceptionHandler();
                           --this.state._fsp;
                           ++cnt37;
                           break;
                        default:
                           if (cnt37 < 1) {
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
                                 this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup807);
                                 this.finallyClause();
                                 --this.state._fsp;
                                 return;
                              default:
                                 return;
                           }
                     }
                  }
               case 2:
                  this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup814);
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
            this.match(this.input, 17, FOLLOW_CATCH_in_exceptionHandler826);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 12, FOLLOW_ARG_ACTION_in_exceptionHandler828);
            this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler830);
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
            this.match(this.input, 38, FOLLOW_FINALLY_in_finallyClause843);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause845);
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
            int alt42 = true;
            int LA42_0 = this.input.LA(1);
            byte alt42;
            if (LA42_0 == 76) {
               alt42 = 1;
            } else {
               if (LA42_0 != 8 && LA42_0 != 33) {
                  NoViableAltException nvae = new NoViableAltException("", 42, 0, this.input);
                  throw nvae;
               }

               alt42 = 2;
            }

            switch (alt42) {
               case 1:
                  this.match(this.input, 76, FOLLOW_REWRITES_in_rewrite858);
                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);

                     label144:
                     while(true) {
                        do {
                           int alt41 = 2;
                           int LA41_0 = this.input.LA(1);
                           if (LA41_0 == 75) {
                              alt41 = 1;
                           }

                           switch (alt41) {
                              case 1:
                                 this.match(this.input, 75, FOLLOW_REWRITE_in_rewrite863);
                                 break;
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                                 return;
                           }
                        } while(this.input.LA(1) != 2);

                        this.match(this.input, 2, (BitSet)null);

                        while(true) {
                           int alt40 = 2;
                           int LA40_0 = this.input.LA(1);
                           if (LA40_0 >= 4 && LA40_0 <= 103) {
                              alt40 = 1;
                           } else if (LA40_0 == 3) {
                              alt40 = 2;
                           }

                           switch (alt40) {
                              case 1:
                                 this.matchAny(this.input);
                                 break;
                              default:
                                 this.match(this.input, 3, (BitSet)null);
                                 continue label144;
                           }
                        }
                     }
                  }
               case 2:
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

      } finally {
         ;
      }
   }

   public final void element() throws RecognitionException {
      try {
         try {
            int alt44 = true;
            byte alt44;
            switch (this.input.LA(1)) {
               case 4:
                  alt44 = 13;
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
                  NoViableAltException nvae = new NoViableAltException("", 44, 0, this.input);
                  throw nvae;
               case 13:
                  alt44 = 7;
                  break;
               case 14:
                  alt44 = 16;
                  break;
               case 15:
                  alt44 = 2;
                  break;
               case 16:
               case 21:
               case 57:
               case 64:
                  alt44 = 9;
                  break;
               case 18:
               case 29:
               case 80:
               case 88:
               case 94:
               case 98:
                  alt44 = 3;
                  break;
               case 19:
                  alt44 = 6;
                  break;
               case 35:
                  alt44 = 18;
                  break;
               case 39:
                  alt44 = 12;
                  break;
               case 41:
                  alt44 = 17;
                  break;
               case 55:
                  alt44 = 4;
                  break;
               case 63:
                  alt44 = 8;
                  break;
               case 70:
                  alt44 = 5;
                  break;
               case 77:
                  alt44 = 1;
                  break;
               case 83:
                  alt44 = 14;
                  break;
               case 89:
                  alt44 = 11;
                  break;
               case 90:
                  alt44 = 15;
                  break;
               case 96:
                  alt44 = 10;
            }

            switch (alt44) {
               case 1:
                  this.match(this.input, 77, FOLLOW_ROOT_in_element887);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_element_in_element889);
                  this.element();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 15, FOLLOW_BANG_in_element896);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_element_in_element898);
                  this.element();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 3:
                  this.pushFollow(FOLLOW_atom_in_element904);
                  this.atom();
                  --this.state._fsp;
                  break;
               case 4:
                  this.match(this.input, 55, FOLLOW_NOT_in_element910);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_element_in_element912);
                  this.element();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 5:
                  this.match(this.input, 70, FOLLOW_RANGE_in_element919);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_atom_in_element921);
                  this.atom();
                  --this.state._fsp;
                  this.pushFollow(FOLLOW_atom_in_element923);
                  this.atom();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 6:
                  this.match(this.input, 19, FOLLOW_CHAR_RANGE_in_element930);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_atom_in_element932);
                  this.atom();
                  --this.state._fsp;
                  this.pushFollow(FOLLOW_atom_in_element934);
                  this.atom();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 7:
                  this.match(this.input, 13, FOLLOW_ASSIGN_in_element941);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 43, FOLLOW_ID_in_element943);
                  this.pushFollow(FOLLOW_element_in_element945);
                  this.element();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 8:
                  this.match(this.input, 63, FOLLOW_PLUS_ASSIGN_in_element952);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 43, FOLLOW_ID_in_element954);
                  this.pushFollow(FOLLOW_element_in_element956);
                  this.element();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 9:
                  this.pushFollow(FOLLOW_ebnf_in_element962);
                  this.ebnf();
                  --this.state._fsp;
                  break;
               case 10:
                  this.pushFollow(FOLLOW_tree__in_element967);
                  this.tree_();
                  --this.state._fsp;
                  break;
               case 11:
                  this.match(this.input, 89, FOLLOW_SYNPRED_in_element974);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_element976);
                  this.block();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 12:
                  this.match(this.input, 39, FOLLOW_FORCED_ACTION_in_element983);
                  break;
               case 13:
                  this.match(this.input, 4, FOLLOW_ACTION_in_element988);
                  break;
               case 14:
                  this.match(this.input, 83, FOLLOW_SEMPRED_in_element993);
                  break;
               case 15:
                  this.match(this.input, 90, FOLLOW_SYN_SEMPRED_in_element998);
                  break;
               case 16:
                  this.match(this.input, 14, FOLLOW_BACKTRACK_SEMPRED_in_element1004);
                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);

                     while(true) {
                        int alt43 = 2;
                        int LA43_0 = this.input.LA(1);
                        if (LA43_0 >= 4 && LA43_0 <= 103) {
                           alt43 = 1;
                        } else if (LA43_0 == 3) {
                           alt43 = 2;
                        }

                        switch (alt43) {
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
                  this.match(this.input, 41, FOLLOW_GATED_SEMPRED_in_element1013);
                  break;
               case 18:
                  this.match(this.input, 35, FOLLOW_EPSILON_in_element1018);
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void ebnf() throws RecognitionException {
      try {
         try {
            int alt45 = true;
            byte alt45;
            switch (this.input.LA(1)) {
               case 16:
                  alt45 = 1;
                  break;
               case 21:
                  alt45 = 3;
                  break;
               case 57:
                  alt45 = 2;
                  break;
               case 64:
                  alt45 = 4;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 45, 0, this.input);
                  throw nvae;
            }

            switch (alt45) {
               case 1:
                  this.pushFollow(FOLLOW_block_in_ebnf1029);
                  this.block();
                  --this.state._fsp;
                  break;
               case 2:
                  this.match(this.input, 57, FOLLOW_OPTIONAL_in_ebnf1036);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_ebnf1038);
                  this.block();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 3:
                  this.match(this.input, 21, FOLLOW_CLOSURE_in_ebnf1047);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_ebnf1049);
                  this.block();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 4:
                  this.match(this.input, 64, FOLLOW_POSITIVE_CLOSURE_in_ebnf1058);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_ebnf1060);
                  this.block();
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

   public final void tree_() throws RecognitionException {
      try {
         this.match(this.input, 96, FOLLOW_TREE_BEGIN_in_tree_1074);
         this.match(this.input, 2, (BitSet)null);
         int cnt46 = 0;

         while(true) {
            int alt46 = 2;
            int LA46_0 = this.input.LA(1);
            if (LA46_0 == 4 || LA46_0 >= 13 && LA46_0 <= 16 || LA46_0 >= 18 && LA46_0 <= 19 || LA46_0 == 21 || LA46_0 == 29 || LA46_0 == 35 || LA46_0 == 39 || LA46_0 == 41 || LA46_0 == 55 || LA46_0 == 57 || LA46_0 >= 63 && LA46_0 <= 64 || LA46_0 == 70 || LA46_0 == 77 || LA46_0 == 80 || LA46_0 == 83 || LA46_0 >= 88 && LA46_0 <= 90 || LA46_0 == 94 || LA46_0 == 96 || LA46_0 == 98) {
               alt46 = 1;
            }

            switch (alt46) {
               case 1:
                  this.pushFollow(FOLLOW_element_in_tree_1076);
                  this.element();
                  --this.state._fsp;
                  ++cnt46;
                  break;
               default:
                  if (cnt46 < 1) {
                     EarlyExitException eee = new EarlyExitException(46, this.input);
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

   public final void atom() throws RecognitionException {
      GrammarAST t = null;
      GrammarAST c = null;
      GrammarAST s = null;

      try {
         try {
            int alt49 = true;
            byte alt49;
            switch (this.input.LA(1)) {
               case 18:
                  alt49 = 3;
                  break;
               case 29:
                  alt49 = 6;
                  break;
               case 80:
                  alt49 = 1;
                  break;
               case 88:
                  alt49 = 4;
                  break;
               case 94:
                  alt49 = 2;
                  break;
               case 98:
                  alt49 = 5;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 49, 0, this.input);
                  throw nvae;
            }

            byte alt48;
            int LA48_0;
            switch (alt49) {
               case 1:
                  this.match(this.input, 80, FOLLOW_RULE_REF_in_atom1091);
                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     alt48 = 2;
                     LA48_0 = this.input.LA(1);
                     if (LA48_0 == 12) {
                        alt48 = 1;
                     }

                     switch (alt48) {
                        case 1:
                           this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1094);
                        default:
                           this.match(this.input, 3, (BitSet)null);
                     }
                  }
                  break;
               case 2:
                  t = (GrammarAST)this.match(this.input, 94, FOLLOW_TOKEN_REF_in_atom1107);
                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     alt48 = 2;
                     LA48_0 = this.input.LA(1);
                     if (LA48_0 == 12) {
                        alt48 = 1;
                     }

                     switch (alt48) {
                        case 1:
                           this.match(this.input, 12, FOLLOW_ARG_ACTION_in_atom1110);
                        default:
                           this.match(this.input, 3, (BitSet)null);
                     }
                  }

                  this.trackToken(t);
                  break;
               case 3:
                  c = (GrammarAST)this.match(this.input, 18, FOLLOW_CHAR_LITERAL_in_atom1124);
                  this.trackString(c);
                  break;
               case 4:
                  s = (GrammarAST)this.match(this.input, 88, FOLLOW_STRING_LITERAL_in_atom1135);
                  this.trackString(s);
                  break;
               case 5:
                  this.match(this.input, 98, FOLLOW_WILDCARD_in_atom1142);
                  break;
               case 6:
                  this.match(this.input, 29, FOLLOW_DOT_in_atom1148);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 43, FOLLOW_ID_in_atom1150);
                  this.pushFollow(FOLLOW_atom_in_atom1152);
                  this.atom();
                  --this.state._fsp;
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

   public final void ast_suffix() throws RecognitionException {
      try {
         try {
            if (this.input.LA(1) != 15 && this.input.LA(1) != 77) {
               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               throw mse;
            }

            this.input.consume();
            this.state.errorRecovery = false;
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public static class block_return extends TreeRuleReturnScope {
   }

   public static class modifier_return extends TreeRuleReturnScope {
   }

   public static class optionValue_return extends TreeRuleReturnScope {
      public Object value = null;
   }
}
