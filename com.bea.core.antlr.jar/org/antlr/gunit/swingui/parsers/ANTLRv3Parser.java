package org.antlr.gunit.swingui.parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.RuleReturnScope;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.RewriteEarlyExitException;
import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
import org.antlr.runtime.tree.RewriteRuleTokenStream;
import org.antlr.runtime.tree.TreeAdaptor;

public class ANTLRv3Parser extends Parser {
   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTION", "ACTION_CHAR_LITERAL", "ACTION_ESC", "ACTION_STRING_LITERAL", "ALT", "ARG", "ARGLIST", "ARG_ACTION", "BACKTRACK_SEMPRED", "BANG", "BLOCK", "CHAR_LITERAL", "CHAR_RANGE", "CLOSURE", "COMBINED_GRAMMAR", "DOC_COMMENT", "DOUBLE_ANGLE_STRING_LITERAL", "DOUBLE_QUOTE_STRING_LITERAL", "EOA", "EOB", "EOR", "EPSILON", "ESC", "FRAGMENT", "GATED_SEMPRED", "ID", "INITACTION", "INT", "LABEL", "LEXER", "LEXER_GRAMMAR", "LITERAL_CHAR", "ML_COMMENT", "NESTED_ACTION", "NESTED_ARG_ACTION", "OPTIONAL", "OPTIONS", "PARSER", "PARSER_GRAMMAR", "POSITIVE_CLOSURE", "RANGE", "RET", "REWRITE", "ROOT", "RULE", "RULE_REF", "SCOPE", "SEMPRED", "SL_COMMENT", "SRC", "STRING_LITERAL", "SYNPRED", "SYN_SEMPRED", "TEMPLATE", "TOKENS", "TOKEN_REF", "TREE_BEGIN", "TREE_GRAMMAR", "WS", "WS_LOOP", "XDIGIT", "'$'", "'('", "')'", "'*'", "'+'", "'+='", "','", "'.'", "':'", "'::'", "';'", "'='", "'=>'", "'?'", "'@'", "'catch'", "'finally'", "'grammar'", "'lexer'", "'parser'", "'private'", "'protected'", "'public'", "'returns'", "'throws'", "'tree'", "'|'", "'}'", "'~'"};
   public static final int EOF = -1;
   public static final int T__65 = 65;
   public static final int T__66 = 66;
   public static final int T__67 = 67;
   public static final int T__68 = 68;
   public static final int T__69 = 69;
   public static final int T__70 = 70;
   public static final int T__71 = 71;
   public static final int T__72 = 72;
   public static final int T__73 = 73;
   public static final int T__74 = 74;
   public static final int T__75 = 75;
   public static final int T__76 = 76;
   public static final int T__77 = 77;
   public static final int T__78 = 78;
   public static final int T__79 = 79;
   public static final int T__80 = 80;
   public static final int T__81 = 81;
   public static final int T__82 = 82;
   public static final int T__83 = 83;
   public static final int T__84 = 84;
   public static final int T__85 = 85;
   public static final int T__86 = 86;
   public static final int T__87 = 87;
   public static final int T__88 = 88;
   public static final int T__89 = 89;
   public static final int T__90 = 90;
   public static final int T__91 = 91;
   public static final int T__92 = 92;
   public static final int T__93 = 93;
   public static final int ACTION = 4;
   public static final int ACTION_CHAR_LITERAL = 5;
   public static final int ACTION_ESC = 6;
   public static final int ACTION_STRING_LITERAL = 7;
   public static final int ALT = 8;
   public static final int ARG = 9;
   public static final int ARGLIST = 10;
   public static final int ARG_ACTION = 11;
   public static final int BACKTRACK_SEMPRED = 12;
   public static final int BANG = 13;
   public static final int BLOCK = 14;
   public static final int CHAR_LITERAL = 15;
   public static final int CHAR_RANGE = 16;
   public static final int CLOSURE = 17;
   public static final int COMBINED_GRAMMAR = 18;
   public static final int DOC_COMMENT = 19;
   public static final int DOUBLE_ANGLE_STRING_LITERAL = 20;
   public static final int DOUBLE_QUOTE_STRING_LITERAL = 21;
   public static final int EOA = 22;
   public static final int EOB = 23;
   public static final int EOR = 24;
   public static final int EPSILON = 25;
   public static final int ESC = 26;
   public static final int FRAGMENT = 27;
   public static final int GATED_SEMPRED = 28;
   public static final int ID = 29;
   public static final int INITACTION = 30;
   public static final int INT = 31;
   public static final int LABEL = 32;
   public static final int LEXER = 33;
   public static final int LEXER_GRAMMAR = 34;
   public static final int LITERAL_CHAR = 35;
   public static final int ML_COMMENT = 36;
   public static final int NESTED_ACTION = 37;
   public static final int NESTED_ARG_ACTION = 38;
   public static final int OPTIONAL = 39;
   public static final int OPTIONS = 40;
   public static final int PARSER = 41;
   public static final int PARSER_GRAMMAR = 42;
   public static final int POSITIVE_CLOSURE = 43;
   public static final int RANGE = 44;
   public static final int RET = 45;
   public static final int REWRITE = 46;
   public static final int ROOT = 47;
   public static final int RULE = 48;
   public static final int RULE_REF = 49;
   public static final int SCOPE = 50;
   public static final int SEMPRED = 51;
   public static final int SL_COMMENT = 52;
   public static final int SRC = 53;
   public static final int STRING_LITERAL = 54;
   public static final int SYNPRED = 55;
   public static final int SYN_SEMPRED = 56;
   public static final int TEMPLATE = 57;
   public static final int TOKENS = 58;
   public static final int TOKEN_REF = 59;
   public static final int TREE_BEGIN = 60;
   public static final int TREE_GRAMMAR = 61;
   public static final int WS = 62;
   public static final int WS_LOOP = 63;
   public static final int XDIGIT = 64;
   protected TreeAdaptor adaptor;
   int gtype;
   public List rules;
   protected Stack rule_stack;
   protected DFA72 dfa72;
   static final String DFA72_eotS = "\u0012\uffff";
   static final String DFA72_eofS = "\b\uffff\u0001\u000b\t\uffff";
   static final String DFA72_minS = "\u0001\u0004\u0002B\u0002\uffff\u00011\u0002L\u0001\u0014\u0001\u0004\u0002\uffff\u0001C\u00011\u0002L\u0001\u0004\u0001C";
   static final String DFA72_maxS = "\u0003B\u0002\uffff\u0001C\u0002L\u0001[\u0001\u0004\u0002\uffff\u0001G\u0001;\u0002L\u0001\u0004\u0001G";
   static final String DFA72_acceptS = "\u0003\uffff\u0001\u0003\u0001\u0004\u0005\uffff\u0001\u0001\u0001\u0002\u0006\uffff";
   static final String DFA72_specialS = "\u0012\uffff}>";
   static final String[] DFA72_transitionS = new String[]{"\u0001\u0004,\uffff\u0001\u0002\t\uffff\u0001\u0001\u0006\uffff\u0001\u0003", "\u0001\u0005", "\u0001\u0005", "", "", "\u0001\u0007\t\uffff\u0001\u0006\u0007\uffff\u0001\b", "\u0001\t", "\u0001\t", "\u0002\n\u0018\uffff\u0001\u000b\u0014\uffff\u0001\u000b\u0007\uffff\u0001\u000b\u000f\uffff\u0001\u000b", "\u0001\f", "", "", "\u0001\b\u0003\uffff\u0001\r", "\u0001\u000f\t\uffff\u0001\u000e", "\u0001\u0010", "\u0001\u0010", "\u0001\u0011", "\u0001\b\u0003\uffff\u0001\r"};
   static final short[] DFA72_eot = DFA.unpackEncodedString("\u0012\uffff");
   static final short[] DFA72_eof = DFA.unpackEncodedString("\b\uffff\u0001\u000b\t\uffff");
   static final char[] DFA72_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0004\u0002B\u0002\uffff\u00011\u0002L\u0001\u0014\u0001\u0004\u0002\uffff\u0001C\u00011\u0002L\u0001\u0004\u0001C");
   static final char[] DFA72_max = DFA.unpackEncodedStringToUnsignedChars("\u0003B\u0002\uffff\u0001C\u0002L\u0001[\u0001\u0004\u0002\uffff\u0001G\u0001;\u0002L\u0001\u0004\u0001G");
   static final short[] DFA72_accept = DFA.unpackEncodedString("\u0003\uffff\u0001\u0003\u0001\u0004\u0005\uffff\u0001\u0001\u0001\u0002\u0006\uffff");
   static final short[] DFA72_special = DFA.unpackEncodedString("\u0012\uffff}>");
   static final short[][] DFA72_transition;
   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarDef347;
   public static final BitSet FOLLOW_83_in_grammarDef357;
   public static final BitSet FOLLOW_84_in_grammarDef375;
   public static final BitSet FOLLOW_90_in_grammarDef391;
   public static final BitSet FOLLOW_82_in_grammarDef432;
   public static final BitSet FOLLOW_id_in_grammarDef434;
   public static final BitSet FOLLOW_75_in_grammarDef436;
   public static final BitSet FOLLOW_optionsSpec_in_grammarDef438;
   public static final BitSet FOLLOW_tokensSpec_in_grammarDef441;
   public static final BitSet FOLLOW_attrScope_in_grammarDef444;
   public static final BitSet FOLLOW_action_in_grammarDef447;
   public static final BitSet FOLLOW_rule_in_grammarDef455;
   public static final BitSet FOLLOW_EOF_in_grammarDef463;
   public static final BitSet FOLLOW_TOKENS_in_tokensSpec524;
   public static final BitSet FOLLOW_tokenSpec_in_tokensSpec526;
   public static final BitSet FOLLOW_92_in_tokensSpec529;
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec549;
   public static final BitSet FOLLOW_76_in_tokenSpec555;
   public static final BitSet FOLLOW_STRING_LITERAL_in_tokenSpec560;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_tokenSpec564;
   public static final BitSet FOLLOW_75_in_tokenSpec603;
   public static final BitSet FOLLOW_SCOPE_in_attrScope614;
   public static final BitSet FOLLOW_id_in_attrScope616;
   public static final BitSet FOLLOW_ACTION_in_attrScope618;
   public static final BitSet FOLLOW_79_in_action641;
   public static final BitSet FOLLOW_actionScopeName_in_action644;
   public static final BitSet FOLLOW_74_in_action646;
   public static final BitSet FOLLOW_id_in_action650;
   public static final BitSet FOLLOW_ACTION_in_action652;
   public static final BitSet FOLLOW_id_in_actionScopeName678;
   public static final BitSet FOLLOW_83_in_actionScopeName685;
   public static final BitSet FOLLOW_84_in_actionScopeName702;
   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec718;
   public static final BitSet FOLLOW_option_in_optionsSpec721;
   public static final BitSet FOLLOW_75_in_optionsSpec723;
   public static final BitSet FOLLOW_92_in_optionsSpec727;
   public static final BitSet FOLLOW_id_in_option752;
   public static final BitSet FOLLOW_76_in_option754;
   public static final BitSet FOLLOW_optionValue_in_option756;
   public static final BitSet FOLLOW_id_in_optionValue785;
   public static final BitSet FOLLOW_STRING_LITERAL_in_optionValue795;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_optionValue805;
   public static final BitSet FOLLOW_INT_in_optionValue815;
   public static final BitSet FOLLOW_68_in_optionValue825;
   public static final BitSet FOLLOW_DOC_COMMENT_in_rule854;
   public static final BitSet FOLLOW_86_in_rule864;
   public static final BitSet FOLLOW_87_in_rule866;
   public static final BitSet FOLLOW_85_in_rule868;
   public static final BitSet FOLLOW_FRAGMENT_in_rule870;
   public static final BitSet FOLLOW_id_in_rule878;
   public static final BitSet FOLLOW_BANG_in_rule884;
   public static final BitSet FOLLOW_ARG_ACTION_in_rule893;
   public static final BitSet FOLLOW_88_in_rule902;
   public static final BitSet FOLLOW_ARG_ACTION_in_rule906;
   public static final BitSet FOLLOW_throwsSpec_in_rule914;
   public static final BitSet FOLLOW_optionsSpec_in_rule917;
   public static final BitSet FOLLOW_ruleScopeSpec_in_rule920;
   public static final BitSet FOLLOW_ruleAction_in_rule923;
   public static final BitSet FOLLOW_73_in_rule928;
   public static final BitSet FOLLOW_altList_in_rule930;
   public static final BitSet FOLLOW_75_in_rule932;
   public static final BitSet FOLLOW_exceptionGroup_in_rule936;
   public static final BitSet FOLLOW_79_in_ruleAction1038;
   public static final BitSet FOLLOW_id_in_ruleAction1040;
   public static final BitSet FOLLOW_ACTION_in_ruleAction1042;
   public static final BitSet FOLLOW_89_in_throwsSpec1063;
   public static final BitSet FOLLOW_id_in_throwsSpec1065;
   public static final BitSet FOLLOW_71_in_throwsSpec1069;
   public static final BitSet FOLLOW_id_in_throwsSpec1071;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1094;
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec1096;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1109;
   public static final BitSet FOLLOW_id_in_ruleScopeSpec1111;
   public static final BitSet FOLLOW_71_in_ruleScopeSpec1114;
   public static final BitSet FOLLOW_id_in_ruleScopeSpec1116;
   public static final BitSet FOLLOW_75_in_ruleScopeSpec1120;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1134;
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec1136;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1140;
   public static final BitSet FOLLOW_id_in_ruleScopeSpec1142;
   public static final BitSet FOLLOW_71_in_ruleScopeSpec1145;
   public static final BitSet FOLLOW_id_in_ruleScopeSpec1147;
   public static final BitSet FOLLOW_75_in_ruleScopeSpec1151;
   public static final BitSet FOLLOW_66_in_block1183;
   public static final BitSet FOLLOW_optionsSpec_in_block1192;
   public static final BitSet FOLLOW_73_in_block1196;
   public static final BitSet FOLLOW_alternative_in_block1205;
   public static final BitSet FOLLOW_rewrite_in_block1207;
   public static final BitSet FOLLOW_91_in_block1211;
   public static final BitSet FOLLOW_alternative_in_block1215;
   public static final BitSet FOLLOW_rewrite_in_block1217;
   public static final BitSet FOLLOW_67_in_block1232;
   public static final BitSet FOLLOW_alternative_in_altList1289;
   public static final BitSet FOLLOW_rewrite_in_altList1291;
   public static final BitSet FOLLOW_91_in_altList1295;
   public static final BitSet FOLLOW_alternative_in_altList1299;
   public static final BitSet FOLLOW_rewrite_in_altList1301;
   public static final BitSet FOLLOW_element_in_alternative1349;
   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup1400;
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1407;
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1415;
   public static final BitSet FOLLOW_80_in_exceptionHandler1435;
   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler1437;
   public static final BitSet FOLLOW_ACTION_in_exceptionHandler1439;
   public static final BitSet FOLLOW_81_in_finallyClause1469;
   public static final BitSet FOLLOW_ACTION_in_finallyClause1471;
   public static final BitSet FOLLOW_elementNoOptionSpec_in_element1493;
   public static final BitSet FOLLOW_id_in_elementNoOptionSpec1504;
   public static final BitSet FOLLOW_76_in_elementNoOptionSpec1509;
   public static final BitSet FOLLOW_70_in_elementNoOptionSpec1513;
   public static final BitSet FOLLOW_atom_in_elementNoOptionSpec1516;
   public static final BitSet FOLLOW_ebnfSuffix_in_elementNoOptionSpec1522;
   public static final BitSet FOLLOW_id_in_elementNoOptionSpec1581;
   public static final BitSet FOLLOW_76_in_elementNoOptionSpec1586;
   public static final BitSet FOLLOW_70_in_elementNoOptionSpec1590;
   public static final BitSet FOLLOW_block_in_elementNoOptionSpec1593;
   public static final BitSet FOLLOW_ebnfSuffix_in_elementNoOptionSpec1599;
   public static final BitSet FOLLOW_atom_in_elementNoOptionSpec1658;
   public static final BitSet FOLLOW_ebnfSuffix_in_elementNoOptionSpec1664;
   public static final BitSet FOLLOW_ebnf_in_elementNoOptionSpec1710;
   public static final BitSet FOLLOW_ACTION_in_elementNoOptionSpec1717;
   public static final BitSet FOLLOW_SEMPRED_in_elementNoOptionSpec1724;
   public static final BitSet FOLLOW_77_in_elementNoOptionSpec1728;
   public static final BitSet FOLLOW_treeSpec_in_elementNoOptionSpec1747;
   public static final BitSet FOLLOW_ebnfSuffix_in_elementNoOptionSpec1753;
   public static final BitSet FOLLOW_range_in_atom1805;
   public static final BitSet FOLLOW_ROOT_in_atom1812;
   public static final BitSet FOLLOW_BANG_in_atom1816;
   public static final BitSet FOLLOW_terminal_in_atom1844;
   public static final BitSet FOLLOW_notSet_in_atom1852;
   public static final BitSet FOLLOW_ROOT_in_atom1859;
   public static final BitSet FOLLOW_BANG_in_atom1863;
   public static final BitSet FOLLOW_RULE_REF_in_atom1891;
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1897;
   public static final BitSet FOLLOW_ROOT_in_atom1907;
   public static final BitSet FOLLOW_BANG_in_atom1911;
   public static final BitSet FOLLOW_93_in_notSet1994;
   public static final BitSet FOLLOW_notTerminal_in_notSet2000;
   public static final BitSet FOLLOW_block_in_notSet2014;
   public static final BitSet FOLLOW_TREE_BEGIN_in_treeSpec2038;
   public static final BitSet FOLLOW_element_in_treeSpec2040;
   public static final BitSet FOLLOW_element_in_treeSpec2044;
   public static final BitSet FOLLOW_67_in_treeSpec2049;
   public static final BitSet FOLLOW_block_in_ebnf2081;
   public static final BitSet FOLLOW_78_in_ebnf2089;
   public static final BitSet FOLLOW_68_in_ebnf2106;
   public static final BitSet FOLLOW_69_in_ebnf2123;
   public static final BitSet FOLLOW_77_in_ebnf2140;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2223;
   public static final BitSet FOLLOW_RANGE_in_range2225;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2229;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_terminal2260;
   public static final BitSet FOLLOW_TOKEN_REF_in_terminal2282;
   public static final BitSet FOLLOW_ARG_ACTION_in_terminal2289;
   public static final BitSet FOLLOW_STRING_LITERAL_in_terminal2328;
   public static final BitSet FOLLOW_72_in_terminal2343;
   public static final BitSet FOLLOW_ROOT_in_terminal2364;
   public static final BitSet FOLLOW_BANG_in_terminal2385;
   public static final BitSet FOLLOW_78_in_ebnfSuffix2445;
   public static final BitSet FOLLOW_68_in_ebnfSuffix2457;
   public static final BitSet FOLLOW_69_in_ebnfSuffix2470;
   public static final BitSet FOLLOW_REWRITE_in_rewrite2499;
   public static final BitSet FOLLOW_SEMPRED_in_rewrite2503;
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite2507;
   public static final BitSet FOLLOW_REWRITE_in_rewrite2515;
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite2519;
   public static final BitSet FOLLOW_rewrite_template_in_rewrite_alternative2570;
   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_alternative2575;
   public static final BitSet FOLLOW_66_in_rewrite_tree_block2617;
   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block2619;
   public static final BitSet FOLLOW_67_in_rewrite_tree_block2621;
   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative2655;
   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2683;
   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2688;
   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_element2690;
   public static final BitSet FOLLOW_rewrite_tree_in_rewrite_tree_element2724;
   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_element2730;
   public static final BitSet FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element2776;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom2792;
   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_tree_atom2799;
   public static final BitSet FOLLOW_ARG_ACTION_in_rewrite_tree_atom2801;
   public static final BitSet FOLLOW_RULE_REF_in_rewrite_tree_atom2822;
   public static final BitSet FOLLOW_STRING_LITERAL_in_rewrite_tree_atom2829;
   public static final BitSet FOLLOW_65_in_rewrite_tree_atom2838;
   public static final BitSet FOLLOW_id_in_rewrite_tree_atom2840;
   public static final BitSet FOLLOW_ACTION_in_rewrite_tree_atom2851;
   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf2872;
   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_ebnf2874;
   public static final BitSet FOLLOW_TREE_BEGIN_in_rewrite_tree2894;
   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree2896;
   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree2898;
   public static final BitSet FOLLOW_67_in_rewrite_tree2901;
   public static final BitSet FOLLOW_id_in_rewrite_template2933;
   public static final BitSet FOLLOW_66_in_rewrite_template2937;
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template2939;
   public static final BitSet FOLLOW_67_in_rewrite_template2941;
   public static final BitSet FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template2949;
   public static final BitSet FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template2955;
   public static final BitSet FOLLOW_rewrite_template_ref_in_rewrite_template2982;
   public static final BitSet FOLLOW_rewrite_indirect_template_head_in_rewrite_template2991;
   public static final BitSet FOLLOW_ACTION_in_rewrite_template3000;
   public static final BitSet FOLLOW_id_in_rewrite_template_ref3013;
   public static final BitSet FOLLOW_66_in_rewrite_template_ref3017;
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template_ref3019;
   public static final BitSet FOLLOW_67_in_rewrite_template_ref3021;
   public static final BitSet FOLLOW_66_in_rewrite_indirect_template_head3049;
   public static final BitSet FOLLOW_ACTION_in_rewrite_indirect_template_head3051;
   public static final BitSet FOLLOW_67_in_rewrite_indirect_template_head3053;
   public static final BitSet FOLLOW_66_in_rewrite_indirect_template_head3055;
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3057;
   public static final BitSet FOLLOW_67_in_rewrite_indirect_template_head3059;
   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args3083;
   public static final BitSet FOLLOW_71_in_rewrite_template_args3086;
   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args3088;
   public static final BitSet FOLLOW_id_in_rewrite_template_arg3121;
   public static final BitSet FOLLOW_76_in_rewrite_template_arg3123;
   public static final BitSet FOLLOW_ACTION_in_rewrite_template_arg3125;
   public static final BitSet FOLLOW_TOKEN_REF_in_id3146;
   public static final BitSet FOLLOW_RULE_REF_in_id3156;
   public static final BitSet FOLLOW_rewrite_template_in_synpred1_ANTLRv32570;
   public static final BitSet FOLLOW_rewrite_tree_alternative_in_synpred2_ANTLRv32575;

   public Parser[] getDelegates() {
      return new Parser[0];
   }

   public ANTLRv3Parser(TokenStream input) {
      this(input, new RecognizerSharedState());
   }

   public ANTLRv3Parser(TokenStream input, RecognizerSharedState state) {
      super(input, state);
      this.adaptor = new CommonTreeAdaptor();
      this.rule_stack = new Stack();
      this.dfa72 = new DFA72(this);
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
      return "org\\antlr\\gunit\\swingui\\parsers\\ANTLRv3.g";
   }

   public final grammarDef_return grammarDef() throws RecognitionException {
      grammarDef_return retval = new grammarDef_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token g = null;
      Token DOC_COMMENT1 = null;
      Token string_literal2 = null;
      Token string_literal3 = null;
      Token string_literal4 = null;
      Token char_literal6 = null;
      Token EOF12 = null;
      ParserRuleReturnScope id5 = null;
      ParserRuleReturnScope optionsSpec7 = null;
      ParserRuleReturnScope tokensSpec8 = null;
      ParserRuleReturnScope attrScope9 = null;
      ParserRuleReturnScope action10 = null;
      ParserRuleReturnScope rule11 = null;
      CommonTree g_tree = null;
      CommonTree DOC_COMMENT1_tree = null;
      CommonTree string_literal2_tree = null;
      CommonTree string_literal3_tree = null;
      CommonTree string_literal4_tree = null;
      CommonTree char_literal6_tree = null;
      CommonTree EOF12_tree = null;
      RewriteRuleTokenStream stream_DOC_COMMENT = new RewriteRuleTokenStream(this.adaptor, "token DOC_COMMENT");
      RewriteRuleTokenStream stream_90 = new RewriteRuleTokenStream(this.adaptor, "token 90");
      RewriteRuleTokenStream stream_82 = new RewriteRuleTokenStream(this.adaptor, "token 82");
      RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
      RewriteRuleTokenStream stream_EOF = new RewriteRuleTokenStream(this.adaptor, "token EOF");
      RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
      RewriteRuleTokenStream stream_75 = new RewriteRuleTokenStream(this.adaptor, "token 75");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_tokensSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule tokensSpec");
      RewriteRuleSubtreeStream stream_attrScope = new RewriteRuleSubtreeStream(this.adaptor, "rule attrScope");
      RewriteRuleSubtreeStream stream_rule = new RewriteRuleSubtreeStream(this.adaptor, "rule rule");
      RewriteRuleSubtreeStream stream_action = new RewriteRuleSubtreeStream(this.adaptor, "rule action");
      RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");

      try {
         int alt1 = 2;
         int LA1_0 = this.input.LA(1);
         if (LA1_0 == 19) {
            alt1 = 1;
         }

         switch (alt1) {
            case 1:
               DOC_COMMENT1 = (Token)this.match(this.input, 19, FOLLOW_DOC_COMMENT_in_grammarDef347);
               if (this.state.failed) {
                  return retval;
               }

               if (this.state.backtracking == 0) {
                  stream_DOC_COMMENT.add(DOC_COMMENT1);
               }
            default:
               int alt2 = true;
               byte alt2;
               switch (this.input.LA(1)) {
                  case 82:
                     alt2 = 4;
                     break;
                  case 83:
                     alt2 = 1;
                     break;
                  case 84:
                     alt2 = 2;
                     break;
                  case 85:
                  case 86:
                  case 87:
                  case 88:
                  case 89:
                  default:
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 2, 0, this.input);
                     throw nvae;
                  case 90:
                     alt2 = 3;
               }

               switch (alt2) {
                  case 1:
                     string_literal2 = (Token)this.match(this.input, 83, FOLLOW_83_in_grammarDef357);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_83.add(string_literal2);
                     }

                     if (this.state.backtracking == 0) {
                        this.gtype = 34;
                     }
                     break;
                  case 2:
                     string_literal3 = (Token)this.match(this.input, 84, FOLLOW_84_in_grammarDef375);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_84.add(string_literal3);
                     }

                     if (this.state.backtracking == 0) {
                        this.gtype = 42;
                     }
                     break;
                  case 3:
                     string_literal4 = (Token)this.match(this.input, 90, FOLLOW_90_in_grammarDef391);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_90.add(string_literal4);
                     }

                     if (this.state.backtracking == 0) {
                        this.gtype = 61;
                     }
                     break;
                  case 4:
                     if (this.state.backtracking == 0) {
                        this.gtype = 18;
                     }
               }

               g = (Token)this.match(this.input, 82, FOLLOW_82_in_grammarDef432);
               if (this.state.failed) {
                  return retval;
               }

               if (this.state.backtracking == 0) {
                  stream_82.add(g);
               }

               this.pushFollow(FOLLOW_id_in_grammarDef434);
               id5 = this.id();
               --this.state._fsp;
               if (this.state.failed) {
                  return retval;
               }

               if (this.state.backtracking == 0) {
                  stream_id.add(id5.getTree());
               }

               char_literal6 = (Token)this.match(this.input, 75, FOLLOW_75_in_grammarDef436);
               if (this.state.failed) {
                  return retval;
               }

               if (this.state.backtracking == 0) {
                  stream_75.add(char_literal6);
               }

               int alt3 = 2;
               int LA3_0 = this.input.LA(1);
               if (LA3_0 == 40) {
                  alt3 = 1;
               }

               switch (alt3) {
                  case 1:
                     this.pushFollow(FOLLOW_optionsSpec_in_grammarDef438);
                     optionsSpec7 = this.optionsSpec();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_optionsSpec.add(optionsSpec7.getTree());
                     }
                  default:
                     int alt4 = 2;
                     int LA4_0 = this.input.LA(1);
                     if (LA4_0 == 58) {
                        alt4 = 1;
                     }

                     switch (alt4) {
                        case 1:
                           this.pushFollow(FOLLOW_tokensSpec_in_grammarDef441);
                           tokensSpec8 = this.tokensSpec();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_tokensSpec.add(tokensSpec8.getTree());
                           }
                     }
               }
         }

         while(true) {
            int cnt7 = 2;
            int LA6_0 = this.input.LA(1);
            if (LA6_0 == 50) {
               cnt7 = 1;
            }

            switch (cnt7) {
               case 1:
                  this.pushFollow(FOLLOW_attrScope_in_grammarDef444);
                  attrScope9 = this.attrScope();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_attrScope.add(attrScope9.getTree());
                  }
                  break;
               default:
                  while(true) {
                     cnt7 = 2;
                     LA6_0 = this.input.LA(1);
                     if (LA6_0 == 79) {
                        cnt7 = 1;
                     }

                     switch (cnt7) {
                        case 1:
                           this.pushFollow(FOLLOW_action_in_grammarDef447);
                           action10 = this.action();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_action.add(action10.getTree());
                           }
                           break;
                        default:
                           cnt7 = 0;

                           while(true) {
                              int alt7 = 2;
                              int LA7_0 = this.input.LA(1);
                              if (LA7_0 == 19 || LA7_0 == 27 || LA7_0 == 49 || LA7_0 == 59 || LA7_0 >= 85 && LA7_0 <= 87) {
                                 alt7 = 1;
                              }

                              switch (alt7) {
                                 case 1:
                                    this.pushFollow(FOLLOW_rule_in_grammarDef455);
                                    rule11 = this.rule();
                                    --this.state._fsp;
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       stream_rule.add(rule11.getTree());
                                    }

                                    ++cnt7;
                                    break;
                                 default:
                                    if (cnt7 < 1) {
                                       if (this.state.backtracking > 0) {
                                          this.state.failed = true;
                                          return retval;
                                       }

                                       EarlyExitException eee = new EarlyExitException(7, this.input);
                                       throw eee;
                                    }

                                    EOF12 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_grammarDef463);
                                    if (this.state.failed) {
                                       return retval;
                                    }

                                    if (this.state.backtracking == 0) {
                                       stream_EOF.add(EOF12);
                                    }

                                    if (this.state.backtracking == 0) {
                                       retval.tree = root_0;
                                       new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                                       root_0 = (CommonTree)this.adaptor.nil();
                                       CommonTree root_1 = (CommonTree)this.adaptor.nil();
                                       root_1 = (CommonTree)this.adaptor.becomeRoot((Object)this.adaptor.create(this.gtype, g), root_1);
                                       this.adaptor.addChild(root_1, stream_id.nextTree());
                                       if (stream_DOC_COMMENT.hasNext()) {
                                          this.adaptor.addChild(root_1, stream_DOC_COMMENT.nextNode());
                                       }

                                       stream_DOC_COMMENT.reset();
                                       if (stream_optionsSpec.hasNext()) {
                                          this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
                                       }

                                       stream_optionsSpec.reset();
                                       if (stream_tokensSpec.hasNext()) {
                                          this.adaptor.addChild(root_1, stream_tokensSpec.nextTree());
                                       }

                                       stream_tokensSpec.reset();

                                       while(stream_attrScope.hasNext()) {
                                          this.adaptor.addChild(root_1, stream_attrScope.nextTree());
                                       }

                                       stream_attrScope.reset();

                                       while(stream_action.hasNext()) {
                                          this.adaptor.addChild(root_1, stream_action.nextTree());
                                       }

                                       stream_action.reset();
                                       if (!stream_rule.hasNext()) {
                                          throw new RewriteEarlyExitException();
                                       }

                                       while(stream_rule.hasNext()) {
                                          this.adaptor.addChild(root_1, stream_rule.nextTree());
                                       }

                                       stream_rule.reset();
                                       this.adaptor.addChild(root_0, root_1);
                                       retval.tree = root_0;
                                    }

                                    retval.stop = this.input.LT(-1);
                                    if (this.state.backtracking == 0) {
                                       retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                                       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                                    }

                                    return retval;
                              }
                           }
                     }
                  }
            }
         }
      } catch (RecognitionException var50) {
         this.reportError(var50);
         this.recover(this.input, var50);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var50);
         return retval;
      } finally {
         ;
      }
   }

   public final tokensSpec_return tokensSpec() throws RecognitionException {
      tokensSpec_return retval = new tokensSpec_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token TOKENS13 = null;
      Token char_literal15 = null;
      ParserRuleReturnScope tokenSpec14 = null;
      CommonTree TOKENS13_tree = null;
      CommonTree char_literal15_tree = null;
      RewriteRuleTokenStream stream_TOKENS = new RewriteRuleTokenStream(this.adaptor, "token TOKENS");
      RewriteRuleTokenStream stream_92 = new RewriteRuleTokenStream(this.adaptor, "token 92");
      RewriteRuleSubtreeStream stream_tokenSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule tokenSpec");

      try {
         TOKENS13 = (Token)this.match(this.input, 58, FOLLOW_TOKENS_in_tokensSpec524);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_TOKENS.add(TOKENS13);
            }

            int cnt8 = 0;

            while(true) {
               int alt8 = 2;
               int LA8_0 = this.input.LA(1);
               if (LA8_0 == 59) {
                  alt8 = 1;
               }

               switch (alt8) {
                  case 1:
                     this.pushFollow(FOLLOW_tokenSpec_in_tokensSpec526);
                     tokenSpec14 = this.tokenSpec();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_tokenSpec.add(tokenSpec14.getTree());
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

                     char_literal15 = (Token)this.match(this.input, 92, FOLLOW_92_in_tokensSpec529);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_92.add(char_literal15);
                     }

                     if (this.state.backtracking == 0) {
                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        CommonTree root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_TOKENS.nextNode(), root_1);
                        if (!stream_tokenSpec.hasNext()) {
                           throw new RewriteEarlyExitException();
                        }

                        while(stream_tokenSpec.hasNext()) {
                           this.adaptor.addChild(root_1, stream_tokenSpec.nextTree());
                        }

                        stream_tokenSpec.reset();
                        this.adaptor.addChild(root_0, root_1);
                        retval.tree = root_0;
                     }

                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var18) {
         this.reportError(var18);
         this.recover(this.input, var18);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         return retval;
      } finally {
         ;
      }
   }

   public final tokenSpec_return tokenSpec() throws RecognitionException {
      tokenSpec_return retval = new tokenSpec_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token lit = null;
      Token TOKEN_REF16 = null;
      Token char_literal17 = null;
      Token char_literal18 = null;
      CommonTree lit_tree = null;
      CommonTree TOKEN_REF16_tree = null;
      CommonTree char_literal17_tree = null;
      CommonTree char_literal18_tree = null;
      RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
      RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
      RewriteRuleTokenStream stream_75 = new RewriteRuleTokenStream(this.adaptor, "token 75");
      RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");

      try {
         try {
            TOKEN_REF16 = (Token)this.match(this.input, 59, FOLLOW_TOKEN_REF_in_tokenSpec549);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_TOKEN_REF.add(TOKEN_REF16);
            }

            int alt10 = true;
            int LA10_0 = this.input.LA(1);
            byte alt10;
            if (LA10_0 == 76) {
               alt10 = 1;
            } else {
               if (LA10_0 != 75) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
                  throw nvae;
               }

               alt10 = 2;
            }

            switch (alt10) {
               case 1:
                  char_literal17 = (Token)this.match(this.input, 76, FOLLOW_76_in_tokenSpec555);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_76.add(char_literal17);
                  }

                  int alt9 = true;
                  int LA9_0 = this.input.LA(1);
                  byte alt9;
                  if (LA9_0 == 54) {
                     alt9 = 1;
                  } else {
                     if (LA9_0 != 15) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 9, 0, this.input);
                        throw nvae;
                     }

                     alt9 = 2;
                  }

                  switch (alt9) {
                     case 1:
                        lit = (Token)this.match(this.input, 54, FOLLOW_STRING_LITERAL_in_tokenSpec560);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_STRING_LITERAL.add(lit);
                        }
                        break;
                     case 2:
                        lit = (Token)this.match(this.input, 15, FOLLOW_CHAR_LITERAL_in_tokenSpec564);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_CHAR_LITERAL.add(lit);
                        }
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleTokenStream stream_lit = new RewriteRuleTokenStream(this.adaptor, "token lit", lit);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_76.nextNode(), root_1);
                     this.adaptor.addChild(root_1, stream_TOKEN_REF.nextNode());
                     this.adaptor.addChild(root_1, stream_lit.nextNode());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_TOKEN_REF.nextNode());
                     retval.tree = root_0;
                  }
            }

            char_literal18 = (Token)this.match(this.input, 75, FOLLOW_75_in_tokenSpec603);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_75.add(char_literal18);
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var26) {
            this.reportError(var26);
            this.recover(this.input, var26);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var26);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final attrScope_return attrScope() throws RecognitionException {
      attrScope_return retval = new attrScope_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token string_literal19 = null;
      Token ACTION21 = null;
      ParserRuleReturnScope id20 = null;
      CommonTree string_literal19_tree = null;
      CommonTree ACTION21_tree = null;
      RewriteRuleTokenStream stream_SCOPE = new RewriteRuleTokenStream(this.adaptor, "token SCOPE");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         try {
            string_literal19 = (Token)this.match(this.input, 50, FOLLOW_SCOPE_in_attrScope614);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_SCOPE.add(string_literal19);
            }

            this.pushFollow(FOLLOW_id_in_attrScope616);
            id20 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id20.getTree());
            }

            ACTION21 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_attrScope618);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION21);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_SCOPE.nextNode(), root_1);
               this.adaptor.addChild(root_1, stream_id.nextTree());
               this.adaptor.addChild(root_1, stream_ACTION.nextNode());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.recover(this.input, var16);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final action_return action() throws RecognitionException {
      action_return retval = new action_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal22 = null;
      Token string_literal24 = null;
      Token ACTION26 = null;
      ParserRuleReturnScope actionScopeName23 = null;
      ParserRuleReturnScope id25 = null;
      CommonTree char_literal22_tree = null;
      CommonTree string_literal24_tree = null;
      CommonTree ACTION26_tree = null;
      RewriteRuleTokenStream stream_79 = new RewriteRuleTokenStream(this.adaptor, "token 79");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleTokenStream stream_74 = new RewriteRuleTokenStream(this.adaptor, "token 74");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_actionScopeName = new RewriteRuleSubtreeStream(this.adaptor, "rule actionScopeName");

      try {
         try {
            char_literal22 = (Token)this.match(this.input, 79, FOLLOW_79_in_action641);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_79.add(char_literal22);
            }

            int alt11 = 2;
            int LA11_1;
            switch (this.input.LA(1)) {
               case 49:
                  LA11_1 = this.input.LA(2);
                  if (LA11_1 == 74) {
                     alt11 = 1;
                  }
                  break;
               case 59:
                  LA11_1 = this.input.LA(2);
                  if (LA11_1 == 74) {
                     alt11 = 1;
                  }
                  break;
               case 83:
               case 84:
                  alt11 = 1;
            }

            switch (alt11) {
               case 1:
                  this.pushFollow(FOLLOW_actionScopeName_in_action644);
                  actionScopeName23 = this.actionScopeName();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_actionScopeName.add(actionScopeName23.getTree());
                  }

                  string_literal24 = (Token)this.match(this.input, 74, FOLLOW_74_in_action646);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_74.add(string_literal24);
                  }
               default:
                  this.pushFollow(FOLLOW_id_in_action650);
                  id25 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(id25.getTree());
                  }

                  ACTION26 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_action652);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_ACTION.add(ACTION26);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_79.nextNode(), root_1);
                     if (stream_actionScopeName.hasNext()) {
                        this.adaptor.addChild(root_1, stream_actionScopeName.nextTree());
                     }

                     stream_actionScopeName.reset();
                     this.adaptor.addChild(root_1, stream_id.nextTree());
                     this.adaptor.addChild(root_1, stream_ACTION.nextNode());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }
            }
         } catch (RecognitionException var22) {
            this.reportError(var22);
            this.recover(this.input, var22);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var22);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final actionScopeName_return actionScopeName() throws RecognitionException {
      actionScopeName_return retval = new actionScopeName_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token l = null;
      Token p = null;
      ParserRuleReturnScope id27 = null;
      CommonTree l_tree = null;
      CommonTree p_tree = null;
      RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
      RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");

      try {
         try {
            int alt12 = true;
            byte alt12;
            switch (this.input.LA(1)) {
               case 49:
               case 59:
                  alt12 = 1;
                  break;
               case 83:
                  alt12 = 2;
                  break;
               case 84:
                  alt12 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
                  throw nvae;
            }

            switch (alt12) {
               case 1:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_id_in_actionScopeName678);
                  id27 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, id27.getTree());
                  }
                  break;
               case 2:
                  l = (Token)this.match(this.input, 83, FOLLOW_83_in_actionScopeName685);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_83.add(l);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(29, (Token)l));
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  p = (Token)this.match(this.input, 84, FOLLOW_84_in_actionScopeName702);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_84.add(p);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(29, (Token)p));
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final optionsSpec_return optionsSpec() throws RecognitionException {
      optionsSpec_return retval = new optionsSpec_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token OPTIONS28 = null;
      Token char_literal30 = null;
      Token char_literal31 = null;
      ParserRuleReturnScope option29 = null;
      CommonTree OPTIONS28_tree = null;
      CommonTree char_literal30_tree = null;
      CommonTree char_literal31_tree = null;
      RewriteRuleTokenStream stream_92 = new RewriteRuleTokenStream(this.adaptor, "token 92");
      RewriteRuleTokenStream stream_OPTIONS = new RewriteRuleTokenStream(this.adaptor, "token OPTIONS");
      RewriteRuleTokenStream stream_75 = new RewriteRuleTokenStream(this.adaptor, "token 75");
      RewriteRuleSubtreeStream stream_option = new RewriteRuleSubtreeStream(this.adaptor, "rule option");

      try {
         OPTIONS28 = (Token)this.match(this.input, 40, FOLLOW_OPTIONS_in_optionsSpec718);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_OPTIONS.add(OPTIONS28);
            }

            int cnt13 = 0;

            while(true) {
               int alt13 = 2;
               int LA13_0 = this.input.LA(1);
               if (LA13_0 == 49 || LA13_0 == 59) {
                  alt13 = 1;
               }

               switch (alt13) {
                  case 1:
                     this.pushFollow(FOLLOW_option_in_optionsSpec721);
                     option29 = this.option();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_option.add(option29.getTree());
                     }

                     char_literal30 = (Token)this.match(this.input, 75, FOLLOW_75_in_optionsSpec723);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_75.add(char_literal30);
                     }

                     ++cnt13;
                     break;
                  default:
                     if (cnt13 < 1) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        EarlyExitException eee = new EarlyExitException(13, this.input);
                        throw eee;
                     }

                     char_literal31 = (Token)this.match(this.input, 92, FOLLOW_92_in_optionsSpec727);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_92.add(char_literal31);
                     }

                     if (this.state.backtracking == 0) {
                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        CommonTree root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_OPTIONS.nextNode(), root_1);
                        if (!stream_option.hasNext()) {
                           throw new RewriteEarlyExitException();
                        }

                        while(stream_option.hasNext()) {
                           this.adaptor.addChild(root_1, stream_option.nextTree());
                        }

                        stream_option.reset();
                        this.adaptor.addChild(root_0, root_1);
                        retval.tree = root_0;
                     }

                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var21) {
         this.reportError(var21);
         this.recover(this.input, var21);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var21);
         return retval;
      } finally {
         ;
      }
   }

   public final option_return option() throws RecognitionException {
      option_return retval = new option_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal33 = null;
      ParserRuleReturnScope id32 = null;
      ParserRuleReturnScope optionValue34 = null;
      CommonTree char_literal33_tree = null;
      RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_optionValue = new RewriteRuleSubtreeStream(this.adaptor, "rule optionValue");

      try {
         try {
            this.pushFollow(FOLLOW_id_in_option752);
            id32 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id32.getTree());
            }

            char_literal33 = (Token)this.match(this.input, 76, FOLLOW_76_in_option754);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_76.add(char_literal33);
            }

            this.pushFollow(FOLLOW_optionValue_in_option756);
            optionValue34 = this.optionValue();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_optionValue.add(optionValue34.getTree());
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_76.nextNode(), root_1);
               this.adaptor.addChild(root_1, stream_id.nextTree());
               this.adaptor.addChild(root_1, stream_optionValue.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final optionValue_return optionValue() throws RecognitionException {
      optionValue_return retval = new optionValue_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token s = null;
      Token STRING_LITERAL36 = null;
      Token CHAR_LITERAL37 = null;
      Token INT38 = null;
      ParserRuleReturnScope id35 = null;
      CommonTree s_tree = null;
      CommonTree STRING_LITERAL36_tree = null;
      CommonTree CHAR_LITERAL37_tree = null;
      CommonTree INT38_tree = null;
      RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");

      try {
         try {
            int alt14 = true;
            byte alt14;
            switch (this.input.LA(1)) {
               case 15:
                  alt14 = 3;
                  break;
               case 31:
                  alt14 = 4;
                  break;
               case 49:
               case 59:
                  alt14 = 1;
                  break;
               case 54:
                  alt14 = 2;
                  break;
               case 68:
                  alt14 = 5;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
                  throw nvae;
            }

            switch (alt14) {
               case 1:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_id_in_optionValue785);
                  id35 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, id35.getTree());
                  }
                  break;
               case 2:
                  root_0 = (CommonTree)this.adaptor.nil();
                  STRING_LITERAL36 = (Token)this.match(this.input, 54, FOLLOW_STRING_LITERAL_in_optionValue795);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     STRING_LITERAL36_tree = (CommonTree)this.adaptor.create(STRING_LITERAL36);
                     this.adaptor.addChild(root_0, STRING_LITERAL36_tree);
                  }
                  break;
               case 3:
                  root_0 = (CommonTree)this.adaptor.nil();
                  CHAR_LITERAL37 = (Token)this.match(this.input, 15, FOLLOW_CHAR_LITERAL_in_optionValue805);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     CHAR_LITERAL37_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL37);
                     this.adaptor.addChild(root_0, CHAR_LITERAL37_tree);
                  }
                  break;
               case 4:
                  root_0 = (CommonTree)this.adaptor.nil();
                  INT38 = (Token)this.match(this.input, 31, FOLLOW_INT_in_optionValue815);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     INT38_tree = (CommonTree)this.adaptor.create(INT38);
                     this.adaptor.addChild(root_0, INT38_tree);
                  }
                  break;
               case 5:
                  s = (Token)this.match(this.input, 68, FOLLOW_68_in_optionValue825);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_68.add(s);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(54, (Token)s));
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rule_return rule() throws RecognitionException {
      this.rule_stack.push(new rule_scope());
      rule_return retval = new rule_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token modifier = null;
      Token arg = null;
      Token rt = null;
      Token DOC_COMMENT39 = null;
      Token char_literal41 = null;
      Token string_literal42 = null;
      Token char_literal47 = null;
      Token char_literal49 = null;
      ParserRuleReturnScope id40 = null;
      ParserRuleReturnScope throwsSpec43 = null;
      ParserRuleReturnScope optionsSpec44 = null;
      ParserRuleReturnScope ruleScopeSpec45 = null;
      ParserRuleReturnScope ruleAction46 = null;
      ParserRuleReturnScope altList48 = null;
      ParserRuleReturnScope exceptionGroup50 = null;
      CommonTree modifier_tree = null;
      CommonTree arg_tree = null;
      CommonTree rt_tree = null;
      CommonTree DOC_COMMENT39_tree = null;
      CommonTree char_literal41_tree = null;
      CommonTree string_literal42_tree = null;
      CommonTree char_literal47_tree = null;
      CommonTree char_literal49_tree = null;
      RewriteRuleTokenStream stream_DOC_COMMENT = new RewriteRuleTokenStream(this.adaptor, "token DOC_COMMENT");
      RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
      RewriteRuleTokenStream stream_FRAGMENT = new RewriteRuleTokenStream(this.adaptor, "token FRAGMENT");
      RewriteRuleTokenStream stream_86 = new RewriteRuleTokenStream(this.adaptor, "token 86");
      RewriteRuleTokenStream stream_73 = new RewriteRuleTokenStream(this.adaptor, "token 73");
      RewriteRuleTokenStream stream_87 = new RewriteRuleTokenStream(this.adaptor, "token 87");
      RewriteRuleTokenStream stream_88 = new RewriteRuleTokenStream(this.adaptor, "token 88");
      RewriteRuleTokenStream stream_75 = new RewriteRuleTokenStream(this.adaptor, "token 75");
      RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
      RewriteRuleTokenStream stream_85 = new RewriteRuleTokenStream(this.adaptor, "token 85");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_exceptionGroup = new RewriteRuleSubtreeStream(this.adaptor, "rule exceptionGroup");
      RewriteRuleSubtreeStream stream_throwsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule throwsSpec");
      RewriteRuleSubtreeStream stream_ruleScopeSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleScopeSpec");
      RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
      RewriteRuleSubtreeStream stream_altList = new RewriteRuleSubtreeStream(this.adaptor, "rule altList");
      RewriteRuleSubtreeStream stream_ruleAction = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleAction");

      try {
         int alt15 = 2;
         int LA15_0 = this.input.LA(1);
         if (LA15_0 == 19) {
            alt15 = 1;
         }

         switch (alt15) {
            case 1:
               DOC_COMMENT39 = (Token)this.match(this.input, 19, FOLLOW_DOC_COMMENT_in_rule854);
               if (this.state.failed) {
                  rule_return var45 = retval;
                  return var45;
               }

               if (this.state.backtracking == 0) {
                  stream_DOC_COMMENT.add(DOC_COMMENT39);
               }
         }

         int alt17 = 2;
         int LA17_0 = this.input.LA(1);
         if (LA17_0 == 27 || LA17_0 >= 85 && LA17_0 <= 87) {
            alt17 = 1;
         }

         byte alt18;
         switch (alt17) {
            case 1:
               int alt16 = true;
               rule_return var48;
               switch (this.input.LA(1)) {
                  case 27:
                     alt18 = 4;
                     break;
                  case 85:
                     alt18 = 3;
                     break;
                  case 86:
                     alt18 = 1;
                     break;
                  case 87:
                     alt18 = 2;
                     break;
                  default:
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        var48 = retval;
                        return var48;
                     }

                     NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
                     throw nvae;
               }

               switch (alt18) {
                  case 1:
                     modifier = (Token)this.match(this.input, 86, FOLLOW_86_in_rule864);
                     if (this.state.failed) {
                        var48 = retval;
                        return var48;
                     }

                     if (this.state.backtracking == 0) {
                        stream_86.add(modifier);
                     }
                     break;
                  case 2:
                     modifier = (Token)this.match(this.input, 87, FOLLOW_87_in_rule866);
                     if (this.state.failed) {
                        var48 = retval;
                        return var48;
                     }

                     if (this.state.backtracking == 0) {
                        stream_87.add(modifier);
                     }
                     break;
                  case 3:
                     modifier = (Token)this.match(this.input, 85, FOLLOW_85_in_rule868);
                     if (this.state.failed) {
                        var48 = retval;
                        return var48;
                     }

                     if (this.state.backtracking == 0) {
                        stream_85.add(modifier);
                     }
                     break;
                  case 4:
                     modifier = (Token)this.match(this.input, 27, FOLLOW_FRAGMENT_in_rule870);
                     if (this.state.failed) {
                        var48 = retval;
                        return var48;
                     }

                     if (this.state.backtracking == 0) {
                        stream_FRAGMENT.add(modifier);
                     }
               }
         }

         this.pushFollow(FOLLOW_id_in_rule878);
         id40 = this.id();
         --this.state._fsp;
         if (this.state.failed) {
            rule_return var75 = retval;
            return var75;
         } else {
            if (this.state.backtracking == 0) {
               stream_id.add(id40.getTree());
            }

            if (this.state.backtracking == 0) {
               ((rule_scope)this.rule_stack.peek()).name = id40 != null ? this.input.toString(id40.start, id40.stop) : null;
            }

            alt18 = 2;
            int LA18_0 = this.input.LA(1);
            if (LA18_0 == 13) {
               alt18 = 1;
            }

            byte alt19;
            switch (alt18) {
               case 1:
                  char_literal41 = (Token)this.match(this.input, 13, FOLLOW_BANG_in_rule884);
                  if (this.state.failed) {
                     rule_return var49 = retval;
                     return var49;
                  }

                  if (this.state.backtracking == 0) {
                     stream_BANG.add(char_literal41);
                  }
               default:
                  alt19 = 2;
                  int LA19_0 = this.input.LA(1);
                  if (LA19_0 == 11) {
                     alt19 = 1;
                  }
            }

            switch (alt19) {
               case 1:
                  arg = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_rule893);
                  if (this.state.failed) {
                     rule_return var51 = retval;
                     return var51;
                  }

                  if (this.state.backtracking == 0) {
                     stream_ARG_ACTION.add(arg);
                  }
            }

            int alt20 = 2;
            int LA20_0 = this.input.LA(1);
            if (LA20_0 == 88) {
               alt20 = 1;
            }

            switch (alt20) {
               case 1:
                  string_literal42 = (Token)this.match(this.input, 88, FOLLOW_88_in_rule902);
                  rule_return var53;
                  if (this.state.failed) {
                     var53 = retval;
                     return var53;
                  }

                  if (this.state.backtracking == 0) {
                     stream_88.add(string_literal42);
                  }

                  rt = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_rule906);
                  if (this.state.failed) {
                     var53 = retval;
                     return var53;
                  }

                  if (this.state.backtracking == 0) {
                     stream_ARG_ACTION.add(rt);
                  }
            }

            int alt21 = 2;
            int LA21_0 = this.input.LA(1);
            if (LA21_0 == 89) {
               alt21 = 1;
            }

            rule_return var59;
            switch (alt21) {
               case 1:
                  this.pushFollow(FOLLOW_throwsSpec_in_rule914);
                  throwsSpec43 = this.throwsSpec();
                  --this.state._fsp;
                  if (this.state.failed) {
                     rule_return var55 = retval;
                     return var55;
                  }

                  if (this.state.backtracking == 0) {
                     stream_throwsSpec.add(throwsSpec43.getTree());
                  }
               default:
                  int alt22 = 2;
                  int LA22_0 = this.input.LA(1);
                  if (LA22_0 == 40) {
                     alt22 = 1;
                  }

                  switch (alt22) {
                     case 1:
                        this.pushFollow(FOLLOW_optionsSpec_in_rule917);
                        optionsSpec44 = this.optionsSpec();
                        --this.state._fsp;
                        if (this.state.failed) {
                           rule_return var57 = retval;
                           return var57;
                        }

                        if (this.state.backtracking == 0) {
                           stream_optionsSpec.add(optionsSpec44.getTree());
                        }
                     default:
                        int alt23 = 2;
                        int LA23_0 = this.input.LA(1);
                        if (LA23_0 == 50) {
                           alt23 = 1;
                        }

                        switch (alt23) {
                           case 1:
                              this.pushFollow(FOLLOW_ruleScopeSpec_in_rule920);
                              ruleScopeSpec45 = this.ruleScopeSpec();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 var59 = retval;
                                 return var59;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_ruleScopeSpec.add(ruleScopeSpec45.getTree());
                              }
                        }
                  }
            }

            while(true) {
               int alt25 = 2;
               int LA25_0 = this.input.LA(1);
               if (LA25_0 == 79) {
                  alt25 = 1;
               }

               rule_return var61;
               switch (alt25) {
                  case 1:
                     this.pushFollow(FOLLOW_ruleAction_in_rule923);
                     ruleAction46 = this.ruleAction();
                     --this.state._fsp;
                     if (this.state.failed) {
                        var61 = retval;
                        return var61;
                     }

                     if (this.state.backtracking == 0) {
                        stream_ruleAction.add(ruleAction46.getTree());
                     }
                     break;
                  default:
                     char_literal47 = (Token)this.match(this.input, 73, FOLLOW_73_in_rule928);
                     if (this.state.failed) {
                        var59 = retval;
                        return var59;
                     }

                     if (this.state.backtracking == 0) {
                        stream_73.add(char_literal47);
                     }

                     this.pushFollow(FOLLOW_altList_in_rule930);
                     altList48 = this.altList();
                     --this.state._fsp;
                     if (!this.state.failed) {
                        if (this.state.backtracking == 0) {
                           stream_altList.add(altList48.getTree());
                        }

                        char_literal49 = (Token)this.match(this.input, 75, FOLLOW_75_in_rule932);
                        if (this.state.failed) {
                           var59 = retval;
                           return var59;
                        }

                        if (this.state.backtracking == 0) {
                           stream_75.add(char_literal49);
                        }

                        alt25 = 2;
                        LA25_0 = this.input.LA(1);
                        if (LA25_0 >= 80 && LA25_0 <= 81) {
                           alt25 = 1;
                        }

                        switch (alt25) {
                           case 1:
                              this.pushFollow(FOLLOW_exceptionGroup_in_rule936);
                              exceptionGroup50 = this.exceptionGroup();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 var61 = retval;
                                 return var61;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_exceptionGroup.add(exceptionGroup50.getTree());
                              }
                           default:
                              if (this.state.backtracking == 0) {
                                 retval.tree = root_0;
                                 RewriteRuleTokenStream stream_arg = new RewriteRuleTokenStream(this.adaptor, "token arg", arg);
                                 RewriteRuleTokenStream stream_rt = new RewriteRuleTokenStream(this.adaptor, "token rt", rt);
                                 new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                                 root_0 = (CommonTree)this.adaptor.nil();
                                 CommonTree root_1 = (CommonTree)this.adaptor.nil();
                                 root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(48, (String)"RULE")), root_1);
                                 this.adaptor.addChild(root_1, stream_id.nextTree());
                                 this.adaptor.addChild(root_1, modifier != null ? this.adaptor.create(modifier) : null);
                                 CommonTree root_2;
                                 if (stream_arg.hasNext()) {
                                    root_2 = (CommonTree)this.adaptor.nil();
                                    root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(9, (String)"ARG")), root_2);
                                    this.adaptor.addChild(root_2, stream_arg.nextNode());
                                    this.adaptor.addChild(root_1, root_2);
                                 }

                                 stream_arg.reset();
                                 if (stream_rt.hasNext()) {
                                    root_2 = (CommonTree)this.adaptor.nil();
                                    root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(45, (String)"RET")), root_2);
                                    this.adaptor.addChild(root_2, stream_rt.nextNode());
                                    this.adaptor.addChild(root_1, root_2);
                                 }

                                 stream_rt.reset();
                                 if (stream_optionsSpec.hasNext()) {
                                    this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
                                 }

                                 stream_optionsSpec.reset();
                                 if (stream_ruleScopeSpec.hasNext()) {
                                    this.adaptor.addChild(root_1, stream_ruleScopeSpec.nextTree());
                                 }

                                 stream_ruleScopeSpec.reset();

                                 while(stream_ruleAction.hasNext()) {
                                    this.adaptor.addChild(root_1, stream_ruleAction.nextTree());
                                 }

                                 stream_ruleAction.reset();
                                 this.adaptor.addChild(root_1, stream_altList.nextTree());
                                 if (stream_exceptionGroup.hasNext()) {
                                    this.adaptor.addChild(root_1, stream_exceptionGroup.nextTree());
                                 }

                                 stream_exceptionGroup.reset();
                                 this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(24, (String)"EOR"));
                                 this.adaptor.addChild(root_0, root_1);
                                 retval.tree = root_0;
                              }

                              retval.stop = this.input.LT(-1);
                              if (this.state.backtracking == 0) {
                                 retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                                 this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                              }

                              if (this.state.backtracking == 0) {
                                 this.rules.add(((rule_scope)this.rule_stack.peek()).name);
                              }

                              return retval;
                        }
                     }

                     var59 = retval;
                     return var59;
               }
            }
         }
      } catch (RecognitionException var69) {
         this.reportError(var69);
         this.recover(this.input, var69);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var69);
         return retval;
      } finally {
         this.rule_stack.pop();
      }
   }

   public final ruleAction_return ruleAction() throws RecognitionException {
      ruleAction_return retval = new ruleAction_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal51 = null;
      Token ACTION53 = null;
      ParserRuleReturnScope id52 = null;
      CommonTree char_literal51_tree = null;
      CommonTree ACTION53_tree = null;
      RewriteRuleTokenStream stream_79 = new RewriteRuleTokenStream(this.adaptor, "token 79");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         try {
            char_literal51 = (Token)this.match(this.input, 79, FOLLOW_79_in_ruleAction1038);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_79.add(char_literal51);
            }

            this.pushFollow(FOLLOW_id_in_ruleAction1040);
            id52 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id52.getTree());
            }

            ACTION53 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_ruleAction1042);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION53);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_79.nextNode(), root_1);
               this.adaptor.addChild(root_1, stream_id.nextTree());
               this.adaptor.addChild(root_1, stream_ACTION.nextNode());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.recover(this.input, var16);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final throwsSpec_return throwsSpec() throws RecognitionException {
      throwsSpec_return retval = new throwsSpec_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token string_literal54 = null;
      Token char_literal56 = null;
      ParserRuleReturnScope id55 = null;
      ParserRuleReturnScope id57 = null;
      CommonTree string_literal54_tree = null;
      CommonTree char_literal56_tree = null;
      RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
      RewriteRuleTokenStream stream_89 = new RewriteRuleTokenStream(this.adaptor, "token 89");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         string_literal54 = (Token)this.match(this.input, 89, FOLLOW_89_in_throwsSpec1063);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_89.add(string_literal54);
            }

            this.pushFollow(FOLLOW_id_in_throwsSpec1065);
            id55 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  stream_id.add(id55.getTree());
               }

               while(true) {
                  int alt26 = 2;
                  int LA26_0 = this.input.LA(1);
                  if (LA26_0 == 71) {
                     alt26 = 1;
                  }

                  switch (alt26) {
                     case 1:
                        char_literal56 = (Token)this.match(this.input, 71, FOLLOW_71_in_throwsSpec1069);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_71.add(char_literal56);
                        }

                        this.pushFollow(FOLLOW_id_in_throwsSpec1071);
                        id57 = this.id();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_id.add(id57.getTree());
                        }
                        break;
                     default:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_89.nextNode(), root_1);
                           if (!stream_id.hasNext()) {
                              throw new RewriteEarlyExitException();
                           }

                           while(stream_id.hasNext()) {
                              this.adaptor.addChild(root_1, stream_id.nextTree());
                           }

                           stream_id.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }

                        retval.stop = this.input.LT(-1);
                        if (this.state.backtracking == 0) {
                           retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var18) {
         this.reportError(var18);
         this.recover(this.input, var18);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         return retval;
      } finally {
         ;
      }
   }

   public final ruleScopeSpec_return ruleScopeSpec() throws RecognitionException {
      ruleScopeSpec_return retval = new ruleScopeSpec_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token string_literal58 = null;
      Token ACTION59 = null;
      Token string_literal60 = null;
      Token char_literal62 = null;
      Token char_literal64 = null;
      Token string_literal65 = null;
      Token ACTION66 = null;
      Token string_literal67 = null;
      Token char_literal69 = null;
      Token char_literal71 = null;
      ParserRuleReturnScope id61 = null;
      ParserRuleReturnScope id63 = null;
      ParserRuleReturnScope id68 = null;
      ParserRuleReturnScope id70 = null;
      CommonTree string_literal58_tree = null;
      CommonTree ACTION59_tree = null;
      CommonTree string_literal60_tree = null;
      CommonTree char_literal62_tree = null;
      CommonTree char_literal64_tree = null;
      CommonTree string_literal65_tree = null;
      CommonTree ACTION66_tree = null;
      CommonTree string_literal67_tree = null;
      CommonTree char_literal69_tree = null;
      CommonTree char_literal71_tree = null;
      RewriteRuleTokenStream stream_SCOPE = new RewriteRuleTokenStream(this.adaptor, "token SCOPE");
      RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleTokenStream stream_75 = new RewriteRuleTokenStream(this.adaptor, "token 75");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         try {
            label1082: {
               int alt29 = true;
               int LA29_0 = this.input.LA(1);
               if (LA29_0 != 50) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 29, 0, this.input);
                  throw nvae;
               }

               int LA29_1 = this.input.LA(2);
               int LA28_0;
               byte alt29;
               if (LA29_1 == 4) {
                  LA28_0 = this.input.LA(3);
                  if (LA28_0 == 50) {
                     alt29 = 3;
                  } else {
                     if (LA28_0 != 73 && LA28_0 != 79) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        int nvaeMark = this.input.mark();

                        try {
                           for(int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                              this.input.consume();
                           }

                           NoViableAltException nvae = new NoViableAltException("", 29, 2, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt29 = 1;
                  }
               } else {
                  if (LA29_1 != 49 && LA29_1 != 59) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     LA28_0 = this.input.mark();

                     try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 29, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA28_0);
                     }
                  }

                  alt29 = 2;
               }

               byte alt28;
               CommonTree root_1;
               switch (alt29) {
                  case 1:
                     string_literal58 = (Token)this.match(this.input, 50, FOLLOW_SCOPE_in_ruleScopeSpec1094);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_SCOPE.add(string_literal58);
                     }

                     ACTION59 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec1096);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_ACTION.add(ACTION59);
                     }

                     if (this.state.backtracking == 0) {
                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_SCOPE.nextNode(), root_1);
                        this.adaptor.addChild(root_1, stream_ACTION.nextNode());
                        this.adaptor.addChild(root_0, root_1);
                        retval.tree = root_0;
                     }
                     break label1082;
                  case 2:
                     string_literal60 = (Token)this.match(this.input, 50, FOLLOW_SCOPE_in_ruleScopeSpec1109);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_SCOPE.add(string_literal60);
                     }

                     this.pushFollow(FOLLOW_id_in_ruleScopeSpec1111);
                     id61 = this.id();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_id.add(id61.getTree());
                     }
                     break;
                  case 3:
                     string_literal65 = (Token)this.match(this.input, 50, FOLLOW_SCOPE_in_ruleScopeSpec1134);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_SCOPE.add(string_literal65);
                     }

                     ACTION66 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec1136);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_ACTION.add(ACTION66);
                     }

                     string_literal67 = (Token)this.match(this.input, 50, FOLLOW_SCOPE_in_ruleScopeSpec1140);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_SCOPE.add(string_literal67);
                     }

                     this.pushFollow(FOLLOW_id_in_ruleScopeSpec1142);
                     id68 = this.id();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_id.add(id68.getTree());
                     }

                     while(true) {
                        alt28 = 2;
                        LA28_0 = this.input.LA(1);
                        if (LA28_0 == 71) {
                           alt28 = 1;
                        }

                        switch (alt28) {
                           case 1:
                              char_literal69 = (Token)this.match(this.input, 71, FOLLOW_71_in_ruleScopeSpec1145);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_71.add(char_literal69);
                              }

                              this.pushFollow(FOLLOW_id_in_ruleScopeSpec1147);
                              id70 = this.id();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_id.add(id70.getTree());
                              }
                              break;
                           default:
                              char_literal71 = (Token)this.match(this.input, 75, FOLLOW_75_in_ruleScopeSpec1151);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_75.add(char_literal71);
                              }

                              if (this.state.backtracking != 0) {
                                 break label1082;
                              }

                              retval.tree = root_0;
                              new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                              root_0 = (CommonTree)this.adaptor.nil();
                              root_1 = (CommonTree)this.adaptor.nil();
                              root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_SCOPE.nextNode(), root_1);
                              this.adaptor.addChild(root_1, stream_ACTION.nextNode());
                              if (!stream_id.hasNext()) {
                                 throw new RewriteEarlyExitException();
                              }

                              while(stream_id.hasNext()) {
                                 this.adaptor.addChild(root_1, stream_id.nextTree());
                              }

                              stream_id.reset();
                              this.adaptor.addChild(root_0, root_1);
                              retval.tree = root_0;
                              break label1082;
                        }
                     }
                  default:
                     break label1082;
               }

               label1075:
               while(true) {
                  alt28 = 2;
                  LA28_0 = this.input.LA(1);
                  if (LA28_0 == 71) {
                     alt28 = 1;
                  }

                  switch (alt28) {
                     case 1:
                        char_literal62 = (Token)this.match(this.input, 71, FOLLOW_71_in_ruleScopeSpec1114);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_71.add(char_literal62);
                        }

                        this.pushFollow(FOLLOW_id_in_ruleScopeSpec1116);
                        id63 = this.id();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_id.add(id63.getTree());
                        }
                        break;
                     default:
                        char_literal64 = (Token)this.match(this.input, 75, FOLLOW_75_in_ruleScopeSpec1120);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_75.add(char_literal64);
                        }

                        if (this.state.backtracking != 0) {
                           break label1075;
                        }

                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_SCOPE.nextNode(), root_1);
                        if (!stream_id.hasNext()) {
                           throw new RewriteEarlyExitException();
                        }

                        while(stream_id.hasNext()) {
                           this.adaptor.addChild(root_1, stream_id.nextTree());
                        }

                        stream_id.reset();
                        this.adaptor.addChild(root_0, root_1);
                        retval.tree = root_0;
                        break label1075;
                  }
               }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var55) {
            this.reportError(var55);
            this.recover(this.input, var55);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var55);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final block_return block() throws RecognitionException {
      block_return retval = new block_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token lp = null;
      Token rp = null;
      Token char_literal72 = null;
      Token char_literal74 = null;
      ParserRuleReturnScope opts = null;
      ParserRuleReturnScope a1 = null;
      ParserRuleReturnScope a2 = null;
      ParserRuleReturnScope rewrite73 = null;
      ParserRuleReturnScope rewrite75 = null;
      CommonTree lp_tree = null;
      CommonTree rp_tree = null;
      CommonTree char_literal72_tree = null;
      CommonTree char_literal74_tree = null;
      RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
      RewriteRuleTokenStream stream_66 = new RewriteRuleTokenStream(this.adaptor, "token 66");
      RewriteRuleTokenStream stream_91 = new RewriteRuleTokenStream(this.adaptor, "token 91");
      RewriteRuleTokenStream stream_73 = new RewriteRuleTokenStream(this.adaptor, "token 73");
      RewriteRuleSubtreeStream stream_rewrite = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite");
      RewriteRuleSubtreeStream stream_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule alternative");
      RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");

      try {
         lp = (Token)this.match(this.input, 66, FOLLOW_66_in_block1183);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_66.add(lp);
            }

            int alt31 = 2;
            int LA31_0 = this.input.LA(1);
            if (LA31_0 == 40 || LA31_0 == 73) {
               alt31 = 1;
            }

            byte alt32;
            int LA32_0;
            switch (alt31) {
               case 1:
                  alt32 = 2;
                  LA32_0 = this.input.LA(1);
                  if (LA32_0 == 40) {
                     alt32 = 1;
                  }

                  switch (alt32) {
                     case 1:
                        this.pushFollow(FOLLOW_optionsSpec_in_block1192);
                        opts = this.optionsSpec();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_optionsSpec.add(opts.getTree());
                        }
                     default:
                        char_literal72 = (Token)this.match(this.input, 73, FOLLOW_73_in_block1196);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_73.add(char_literal72);
                        }
                  }
               default:
                  this.pushFollow(FOLLOW_alternative_in_block1205);
                  a1 = this.alternative();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_alternative.add(a1.getTree());
                  }

                  this.pushFollow(FOLLOW_rewrite_in_block1207);
                  rewrite73 = this.rewrite();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite.add(rewrite73.getTree());
                  }
            }

            while(true) {
               alt32 = 2;
               LA32_0 = this.input.LA(1);
               if (LA32_0 == 91) {
                  alt32 = 1;
               }

               switch (alt32) {
                  case 1:
                     char_literal74 = (Token)this.match(this.input, 91, FOLLOW_91_in_block1211);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_91.add(char_literal74);
                     }

                     this.pushFollow(FOLLOW_alternative_in_block1215);
                     a2 = this.alternative();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_alternative.add(a2.getTree());
                     }

                     this.pushFollow(FOLLOW_rewrite_in_block1217);
                     rewrite75 = this.rewrite();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_rewrite.add(rewrite75.getTree());
                     }
                     break;
                  default:
                     rp = (Token)this.match(this.input, 67, FOLLOW_67_in_block1232);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_67.add(rp);
                     }

                     if (this.state.backtracking == 0) {
                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        CommonTree root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(14, lp, "BLOCK")), root_1);
                        if (stream_optionsSpec.hasNext()) {
                           this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
                        }

                        stream_optionsSpec.reset();
                        if (!stream_alternative.hasNext()) {
                           throw new RewriteEarlyExitException();
                        }

                        for(; stream_alternative.hasNext(); stream_rewrite.reset()) {
                           this.adaptor.addChild(root_1, stream_alternative.nextTree());
                           if (stream_rewrite.hasNext()) {
                              this.adaptor.addChild(root_1, stream_rewrite.nextTree());
                           }
                        }

                        stream_alternative.reset();
                        this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(23, rp, "EOB"));
                        this.adaptor.addChild(root_0, root_1);
                        retval.tree = root_0;
                     }

                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var31) {
         this.reportError(var31);
         this.recover(this.input, var31);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var31);
         return retval;
      } finally {
         ;
      }
   }

   public final altList_return altList() throws RecognitionException {
      altList_return retval = new altList_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal77 = null;
      ParserRuleReturnScope a1 = null;
      ParserRuleReturnScope a2 = null;
      ParserRuleReturnScope rewrite76 = null;
      ParserRuleReturnScope rewrite78 = null;
      CommonTree char_literal77_tree = null;
      RewriteRuleTokenStream stream_91 = new RewriteRuleTokenStream(this.adaptor, "token 91");
      RewriteRuleSubtreeStream stream_rewrite = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite");
      RewriteRuleSubtreeStream stream_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule alternative");
      CommonTree blkRoot = (CommonTree)this.adaptor.create(14, this.input.LT(-1), "BLOCK");

      try {
         this.pushFollow(FOLLOW_alternative_in_altList1289);
         a1 = this.alternative();
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_alternative.add(a1.getTree());
            }

            this.pushFollow(FOLLOW_rewrite_in_altList1291);
            rewrite76 = this.rewrite();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  stream_rewrite.add(rewrite76.getTree());
               }

               while(true) {
                  int alt33 = 2;
                  int LA33_0 = this.input.LA(1);
                  if (LA33_0 == 91) {
                     alt33 = 1;
                  }

                  switch (alt33) {
                     case 1:
                        char_literal77 = (Token)this.match(this.input, 91, FOLLOW_91_in_altList1295);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_91.add(char_literal77);
                        }

                        this.pushFollow(FOLLOW_alternative_in_altList1299);
                        a2 = this.alternative();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_alternative.add(a2.getTree());
                        }

                        this.pushFollow(FOLLOW_rewrite_in_altList1301);
                        rewrite78 = this.rewrite();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_rewrite.add(rewrite78.getTree());
                        }
                        break;
                     default:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)blkRoot, root_1);
                           if (!stream_alternative.hasNext()) {
                              throw new RewriteEarlyExitException();
                           }

                           for(; stream_alternative.hasNext(); stream_rewrite.reset()) {
                              this.adaptor.addChild(root_1, stream_alternative.nextTree());
                              if (stream_rewrite.hasNext()) {
                                 this.adaptor.addChild(root_1, stream_rewrite.nextTree());
                              }
                           }

                           stream_alternative.reset();
                           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(23, (String)"EOB"));
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }

                        retval.stop = this.input.LT(-1);
                        if (this.state.backtracking == 0) {
                           retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var19) {
         this.reportError(var19);
         this.recover(this.input, var19);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var19);
         return retval;
      } finally {
         ;
      }
   }

   public final alternative_return alternative() throws RecognitionException {
      alternative_return retval = new alternative_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope element79 = null;
      RewriteRuleSubtreeStream stream_element = new RewriteRuleSubtreeStream(this.adaptor, "rule element");
      Token firstToken = this.input.LT(1);
      Token prevToken = this.input.LT(-1);

      try {
         try {
            int alt35 = true;
            int LA35_0 = this.input.LA(1);
            byte alt35;
            if (LA35_0 != 4 && LA35_0 != 15 && LA35_0 != 49 && LA35_0 != 51 && LA35_0 != 54 && (LA35_0 < 59 || LA35_0 > 60) && LA35_0 != 66 && LA35_0 != 72 && LA35_0 != 93) {
               if (LA35_0 != 46 && LA35_0 != 67 && LA35_0 != 75 && LA35_0 != 91) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
                  throw nvae;
               }

               alt35 = 2;
            } else {
               alt35 = 1;
            }

            label249:
            switch (alt35) {
               case 1:
                  int cnt34 = 0;

                  while(true) {
                     int alt34 = 2;
                     int LA34_0 = this.input.LA(1);
                     if (LA34_0 == 4 || LA34_0 == 15 || LA34_0 == 49 || LA34_0 == 51 || LA34_0 == 54 || LA34_0 >= 59 && LA34_0 <= 60 || LA34_0 == 66 || LA34_0 == 72 || LA34_0 == 93) {
                        alt34 = 1;
                     }

                     switch (alt34) {
                        case 1:
                           this.pushFollow(FOLLOW_element_in_alternative1349);
                           element79 = this.element();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_element.add(element79.getTree());
                           }

                           ++cnt34;
                           break;
                        default:
                           if (cnt34 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(34, this.input);
                              throw eee;
                           }

                           if (this.state.backtracking != 0) {
                              break label249;
                           }

                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, firstToken, "ALT")), root_1);
                           if (!stream_element.hasNext()) {
                              throw new RewriteEarlyExitException();
                           }

                           while(stream_element.hasNext()) {
                              this.adaptor.addChild(root_1, stream_element.nextTree());
                           }

                           stream_element.reset();
                           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(22, (String)"EOA"));
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                           break label249;
                     }
                  }
               case 2:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, prevToken, "ALT")), root_1);
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(25, prevToken, "EPSILON"));
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(22, (String)"EOA"));
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.recover(this.input, var16);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final exceptionGroup_return exceptionGroup() throws RecognitionException {
      exceptionGroup_return retval = new exceptionGroup_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope exceptionHandler80 = null;
      ParserRuleReturnScope finallyClause81 = null;
      ParserRuleReturnScope finallyClause82 = null;

      try {
         try {
            int alt38 = true;
            int LA38_0 = this.input.LA(1);
            byte alt38;
            if (LA38_0 == 80) {
               alt38 = 1;
            } else {
               if (LA38_0 != 81) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 38, 0, this.input);
                  throw nvae;
               }

               alt38 = 2;
            }

            label181:
            switch (alt38) {
               case 1:
                  root_0 = (CommonTree)this.adaptor.nil();
                  int cnt36 = 0;

                  while(true) {
                     int alt37 = 2;
                     int LA37_0 = this.input.LA(1);
                     if (LA37_0 == 80) {
                        alt37 = 1;
                     }

                     switch (alt37) {
                        case 1:
                           this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup1400);
                           exceptionHandler80 = this.exceptionHandler();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, exceptionHandler80.getTree());
                           }

                           ++cnt36;
                           break;
                        default:
                           if (cnt36 < 1) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              EarlyExitException eee = new EarlyExitException(36, this.input);
                              throw eee;
                           }

                           alt37 = 2;
                           LA37_0 = this.input.LA(1);
                           if (LA37_0 == 81) {
                              alt37 = 1;
                           }

                           switch (alt37) {
                              case 1:
                                 this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1407);
                                 finallyClause81 = this.finallyClause();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    this.adaptor.addChild(root_0, finallyClause81.getTree());
                                 }
                              default:
                                 break label181;
                           }
                     }
                  }
               case 2:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1415);
                  finallyClause82 = this.finallyClause();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, finallyClause82.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final exceptionHandler_return exceptionHandler() throws RecognitionException {
      exceptionHandler_return retval = new exceptionHandler_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token string_literal83 = null;
      Token ARG_ACTION84 = null;
      Token ACTION85 = null;
      CommonTree string_literal83_tree = null;
      CommonTree ARG_ACTION84_tree = null;
      CommonTree ACTION85_tree = null;
      RewriteRuleTokenStream stream_80 = new RewriteRuleTokenStream(this.adaptor, "token 80");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");

      try {
         try {
            string_literal83 = (Token)this.match(this.input, 80, FOLLOW_80_in_exceptionHandler1435);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_80.add(string_literal83);
            }

            ARG_ACTION84 = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_exceptionHandler1437);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ARG_ACTION.add(ARG_ACTION84);
            }

            ACTION85 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler1439);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION85);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_80.nextNode(), root_1);
               this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
               this.adaptor.addChild(root_1, stream_ACTION.nextNode());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var17) {
            this.reportError(var17);
            this.recover(this.input, var17);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final finallyClause_return finallyClause() throws RecognitionException {
      finallyClause_return retval = new finallyClause_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token string_literal86 = null;
      Token ACTION87 = null;
      CommonTree string_literal86_tree = null;
      CommonTree ACTION87_tree = null;
      RewriteRuleTokenStream stream_81 = new RewriteRuleTokenStream(this.adaptor, "token 81");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");

      try {
         try {
            string_literal86 = (Token)this.match(this.input, 81, FOLLOW_81_in_finallyClause1469);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_81.add(string_literal86);
            }

            ACTION87 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause1471);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION87);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_81.nextNode(), root_1);
               this.adaptor.addChild(root_1, stream_ACTION.nextNode());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.recover(this.input, var14);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var14);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final element_return element() throws RecognitionException {
      element_return retval = new element_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope elementNoOptionSpec88 = null;

      try {
         try {
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_elementNoOptionSpec_in_element1493);
            elementNoOptionSpec88 = this.elementNoOptionSpec();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, elementNoOptionSpec88.getTree());
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var8);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final elementNoOptionSpec_return elementNoOptionSpec() throws RecognitionException {
      elementNoOptionSpec_return retval = new elementNoOptionSpec_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token labelOp = null;
      Token ACTION98 = null;
      Token SEMPRED99 = null;
      Token string_literal100 = null;
      ParserRuleReturnScope id89 = null;
      ParserRuleReturnScope atom90 = null;
      ParserRuleReturnScope ebnfSuffix91 = null;
      ParserRuleReturnScope id92 = null;
      ParserRuleReturnScope block93 = null;
      ParserRuleReturnScope ebnfSuffix94 = null;
      ParserRuleReturnScope atom95 = null;
      ParserRuleReturnScope ebnfSuffix96 = null;
      ParserRuleReturnScope ebnf97 = null;
      ParserRuleReturnScope treeSpec101 = null;
      ParserRuleReturnScope ebnfSuffix102 = null;
      CommonTree labelOp_tree = null;
      CommonTree ACTION98_tree = null;
      CommonTree SEMPRED99_tree = null;
      CommonTree string_literal100_tree = null;
      RewriteRuleTokenStream stream_77 = new RewriteRuleTokenStream(this.adaptor, "token 77");
      RewriteRuleTokenStream stream_70 = new RewriteRuleTokenStream(this.adaptor, "token 70");
      RewriteRuleTokenStream stream_SEMPRED = new RewriteRuleTokenStream(this.adaptor, "token SEMPRED");
      RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule atom");
      RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
      RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
      RewriteRuleSubtreeStream stream_treeSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule treeSpec");

      try {
         try {
            int nvaeMark;
            byte alt46;
            NoViableAltException nvae;
            int alt46 = true;
            int nvaeMark;
            int nvaeConsume;
            NoViableAltException nvae;
            label5985:
            switch (this.input.LA(1)) {
               case 4:
                  alt46 = 5;
                  break;
               case 15:
               case 54:
               case 72:
               case 93:
                  alt46 = 3;
                  break;
               case 49:
                  switch (this.input.LA(2)) {
                     case 4:
                     case 11:
                     case 13:
                     case 15:
                     case 46:
                     case 47:
                     case 49:
                     case 51:
                     case 54:
                     case 59:
                     case 60:
                     case 66:
                     case 67:
                     case 68:
                     case 69:
                     case 72:
                     case 75:
                     case 78:
                     case 91:
                     case 93:
                        alt46 = 3;
                        break label5985;
                     case 5:
                     case 6:
                     case 7:
                     case 8:
                     case 9:
                     case 10:
                     case 12:
                     case 14:
                     case 16:
                     case 17:
                     case 18:
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
                     case 39:
                     case 40:
                     case 41:
                     case 42:
                     case 43:
                     case 44:
                     case 45:
                     case 48:
                     case 50:
                     case 52:
                     case 53:
                     case 55:
                     case 56:
                     case 57:
                     case 58:
                     case 61:
                     case 62:
                     case 63:
                     case 64:
                     case 65:
                     case 71:
                     case 73:
                     case 74:
                     case 77:
                     case 79:
                     case 80:
                     case 81:
                     case 82:
                     case 83:
                     case 84:
                     case 85:
                     case 86:
                     case 87:
                     case 88:
                     case 89:
                     case 90:
                     case 92:
                     default:
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 46, 2, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     case 70:
                        nvaeMark = this.input.LA(3);
                        if (nvaeMark != 15 && nvaeMark != 49 && nvaeMark != 54 && nvaeMark != 59 && nvaeMark != 72 && nvaeMark != 93) {
                           if (nvaeMark != 66) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 46, 9, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt46 = 2;
                           break label5985;
                        }

                        alt46 = 1;
                        break label5985;
                     case 76:
                        nvaeMark = this.input.LA(3);
                        if (nvaeMark != 15 && nvaeMark != 49 && nvaeMark != 54 && nvaeMark != 59 && nvaeMark != 72 && nvaeMark != 93) {
                           if (nvaeMark != 66) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 46, 8, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt46 = 2;
                           break label5985;
                        }

                        alt46 = 1;
                        break label5985;
                  }
               case 51:
                  alt46 = 6;
                  break;
               case 59:
                  switch (this.input.LA(2)) {
                     case 4:
                     case 11:
                     case 13:
                     case 15:
                     case 46:
                     case 47:
                     case 49:
                     case 51:
                     case 54:
                     case 59:
                     case 60:
                     case 66:
                     case 67:
                     case 68:
                     case 69:
                     case 72:
                     case 75:
                     case 78:
                     case 91:
                     case 93:
                        alt46 = 3;
                        break label5985;
                     case 5:
                     case 6:
                     case 7:
                     case 8:
                     case 9:
                     case 10:
                     case 12:
                     case 14:
                     case 16:
                     case 17:
                     case 18:
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
                     case 39:
                     case 40:
                     case 41:
                     case 42:
                     case 43:
                     case 44:
                     case 45:
                     case 48:
                     case 50:
                     case 52:
                     case 53:
                     case 55:
                     case 56:
                     case 57:
                     case 58:
                     case 61:
                     case 62:
                     case 63:
                     case 64:
                     case 65:
                     case 71:
                     case 73:
                     case 74:
                     case 77:
                     case 79:
                     case 80:
                     case 81:
                     case 82:
                     case 83:
                     case 84:
                     case 85:
                     case 86:
                     case 87:
                     case 88:
                     case 89:
                     case 90:
                     case 92:
                     default:
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 46, 1, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     case 70:
                        nvaeMark = this.input.LA(3);
                        if (nvaeMark != 15 && nvaeMark != 49 && nvaeMark != 54 && nvaeMark != 59 && nvaeMark != 72 && nvaeMark != 93) {
                           if (nvaeMark != 66) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 46, 9, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt46 = 2;
                           break label5985;
                        }

                        alt46 = 1;
                        break label5985;
                     case 76:
                        nvaeMark = this.input.LA(3);
                        if (nvaeMark != 15 && nvaeMark != 49 && nvaeMark != 54 && nvaeMark != 59 && nvaeMark != 72 && nvaeMark != 93) {
                           if (nvaeMark != 66) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 46, 8, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt46 = 2;
                           break label5985;
                        }

                        alt46 = 1;
                        break label5985;
                  }
               case 60:
                  alt46 = 7;
                  break;
               case 66:
                  alt46 = 4;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 46, 0, this.input);
                  throw nvae;
            }

            CommonTree root_1;
            CommonTree root_2;
            CommonTree root_3;
            CommonTree root_1;
            CommonTree root_2;
            CommonTree root_3;
            CommonTree root_4;
            boolean alt45;
            byte alt45;
            int LA42_0;
            RewriteRuleTokenStream stream_labelOp;
            NoViableAltException nvae;
            boolean alt42;
            byte alt42;
            label6122:
            switch (alt46) {
               case 1:
                  this.pushFollow(FOLLOW_id_in_elementNoOptionSpec1504);
                  id89 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(id89.getTree());
                  }

                  alt45 = true;
                  nvaeMark = this.input.LA(1);
                  if (nvaeMark == 76) {
                     alt45 = 1;
                  } else {
                     if (nvaeMark != 70) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 39, 0, this.input);
                        throw nvae;
                     }

                     alt45 = 2;
                  }

                  switch (alt45) {
                     case 1:
                        labelOp = (Token)this.match(this.input, 76, FOLLOW_76_in_elementNoOptionSpec1509);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_76.add(labelOp);
                        }
                        break;
                     case 2:
                        labelOp = (Token)this.match(this.input, 70, FOLLOW_70_in_elementNoOptionSpec1513);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_70.add(labelOp);
                        }
                  }

                  this.pushFollow(FOLLOW_atom_in_elementNoOptionSpec1516);
                  atom90 = this.atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_atom.add(atom90.getTree());
                  }

                  alt42 = true;
                  LA42_0 = this.input.LA(1);
                  if ((LA42_0 < 68 || LA42_0 > 69) && LA42_0 != 78) {
                     if (LA42_0 != 4 && LA42_0 != 15 && LA42_0 != 46 && LA42_0 != 49 && LA42_0 != 51 && LA42_0 != 54 && (LA42_0 < 59 || LA42_0 > 60) && (LA42_0 < 66 || LA42_0 > 67) && LA42_0 != 72 && LA42_0 != 75 && LA42_0 != 91 && LA42_0 != 93) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 40, 0, this.input);
                        throw nvae;
                     }

                     alt42 = 2;
                  } else {
                     alt42 = 1;
                  }

                  switch (alt42) {
                     case 1:
                        this.pushFollow(FOLLOW_ebnfSuffix_in_elementNoOptionSpec1522);
                        ebnfSuffix91 = this.ebnfSuffix();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(ebnfSuffix91.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                           root_2 = (CommonTree)this.adaptor.nil();
                           root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(14, (String)"BLOCK")), root_2);
                           root_3 = (CommonTree)this.adaptor.nil();
                           root_3 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_3);
                           root_4 = (CommonTree)this.adaptor.nil();
                           root_4 = (CommonTree)this.adaptor.becomeRoot((Object)stream_labelOp.nextNode(), root_4);
                           this.adaptor.addChild(root_4, stream_id.nextTree());
                           this.adaptor.addChild(root_4, stream_atom.nextTree());
                           this.adaptor.addChild(root_3, root_4);
                           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(22, (String)"EOA"));
                           this.adaptor.addChild(root_2, root_3);
                           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(23, (String)"EOB"));
                           this.adaptor.addChild(root_1, root_2);
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label6122;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_labelOp.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_id.nextTree());
                           this.adaptor.addChild(root_1, stream_atom.nextTree());
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                     default:
                        break label6122;
                  }
               case 2:
                  this.pushFollow(FOLLOW_id_in_elementNoOptionSpec1581);
                  id92 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(id92.getTree());
                  }

                  alt45 = true;
                  nvaeMark = this.input.LA(1);
                  if (nvaeMark == 76) {
                     alt45 = 1;
                  } else {
                     if (nvaeMark != 70) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 41, 0, this.input);
                        throw nvae;
                     }

                     alt45 = 2;
                  }

                  switch (alt45) {
                     case 1:
                        labelOp = (Token)this.match(this.input, 76, FOLLOW_76_in_elementNoOptionSpec1586);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_76.add(labelOp);
                        }
                        break;
                     case 2:
                        labelOp = (Token)this.match(this.input, 70, FOLLOW_70_in_elementNoOptionSpec1590);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_70.add(labelOp);
                        }
                  }

                  this.pushFollow(FOLLOW_block_in_elementNoOptionSpec1593);
                  block93 = this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_block.add(block93.getTree());
                  }

                  alt42 = true;
                  LA42_0 = this.input.LA(1);
                  if ((LA42_0 < 68 || LA42_0 > 69) && LA42_0 != 78) {
                     if (LA42_0 != 4 && LA42_0 != 15 && LA42_0 != 46 && LA42_0 != 49 && LA42_0 != 51 && LA42_0 != 54 && (LA42_0 < 59 || LA42_0 > 60) && (LA42_0 < 66 || LA42_0 > 67) && LA42_0 != 72 && LA42_0 != 75 && LA42_0 != 91 && LA42_0 != 93) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 42, 0, this.input);
                        throw nvae;
                     }

                     alt42 = 2;
                  } else {
                     alt42 = 1;
                  }

                  switch (alt42) {
                     case 1:
                        this.pushFollow(FOLLOW_ebnfSuffix_in_elementNoOptionSpec1599);
                        ebnfSuffix94 = this.ebnfSuffix();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(ebnfSuffix94.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                           root_2 = (CommonTree)this.adaptor.nil();
                           root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(14, (String)"BLOCK")), root_2);
                           root_3 = (CommonTree)this.adaptor.nil();
                           root_3 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_3);
                           root_4 = (CommonTree)this.adaptor.nil();
                           root_4 = (CommonTree)this.adaptor.becomeRoot((Object)stream_labelOp.nextNode(), root_4);
                           this.adaptor.addChild(root_4, stream_id.nextTree());
                           this.adaptor.addChild(root_4, stream_block.nextTree());
                           this.adaptor.addChild(root_3, root_4);
                           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(22, (String)"EOA"));
                           this.adaptor.addChild(root_2, root_3);
                           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(23, (String)"EOB"));
                           this.adaptor.addChild(root_1, root_2);
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label6122;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_labelOp.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_id.nextTree());
                           this.adaptor.addChild(root_1, stream_block.nextTree());
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                     default:
                        break label6122;
                  }
               case 3:
                  this.pushFollow(FOLLOW_atom_in_elementNoOptionSpec1658);
                  atom95 = this.atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_atom.add(atom95.getTree());
                  }

                  alt45 = true;
                  nvaeMark = this.input.LA(1);
                  if ((nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 78) {
                     if (nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 46 && nvaeMark != 49 && nvaeMark != 51 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 66 || nvaeMark > 67) && nvaeMark != 72 && nvaeMark != 75 && nvaeMark != 91 && nvaeMark != 93) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 43, 0, this.input);
                        throw nvae;
                     }

                     alt45 = 2;
                  } else {
                     alt45 = 1;
                  }

                  switch (alt45) {
                     case 1:
                        this.pushFollow(FOLLOW_ebnfSuffix_in_elementNoOptionSpec1664);
                        ebnfSuffix96 = this.ebnfSuffix();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(ebnfSuffix96.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                           root_2 = (CommonTree)this.adaptor.nil();
                           root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(14, (String)"BLOCK")), root_2);
                           root_3 = (CommonTree)this.adaptor.nil();
                           root_3 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_3);
                           this.adaptor.addChild(root_3, stream_atom.nextTree());
                           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(22, (String)"EOA"));
                           this.adaptor.addChild(root_2, root_3);
                           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(23, (String)"EOB"));
                           this.adaptor.addChild(root_1, root_2);
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label6122;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_atom.nextTree());
                           retval.tree = root_0;
                        }
                     default:
                        break label6122;
                  }
               case 4:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_ebnf_in_elementNoOptionSpec1710);
                  ebnf97 = this.ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, ebnf97.getTree());
                  }
                  break;
               case 5:
                  root_0 = (CommonTree)this.adaptor.nil();
                  ACTION98 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_elementNoOptionSpec1717);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION98_tree = (CommonTree)this.adaptor.create(ACTION98);
                     this.adaptor.addChild(root_0, ACTION98_tree);
                  }
                  break;
               case 6:
                  SEMPRED99 = (Token)this.match(this.input, 51, FOLLOW_SEMPRED_in_elementNoOptionSpec1724);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_SEMPRED.add(SEMPRED99);
                  }

                  alt45 = true;
                  nvaeMark = this.input.LA(1);
                  if (nvaeMark == 77) {
                     alt45 = 1;
                  } else {
                     if (nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 46 && nvaeMark != 49 && nvaeMark != 51 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 66 || nvaeMark > 67) && nvaeMark != 72 && nvaeMark != 75 && nvaeMark != 91 && nvaeMark != 93) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 44, 0, this.input);
                        throw nvae;
                     }

                     alt45 = 2;
                  }

                  switch (alt45) {
                     case 1:
                        string_literal100 = (Token)this.match(this.input, 77, FOLLOW_77_in_elementNoOptionSpec1728);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_77.add(string_literal100);
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(28, (String)"GATED_SEMPRED"));
                           retval.tree = root_0;
                        }
                        break label6122;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_SEMPRED.nextNode());
                           retval.tree = root_0;
                        }
                     default:
                        break label6122;
                  }
               case 7:
                  this.pushFollow(FOLLOW_treeSpec_in_elementNoOptionSpec1747);
                  treeSpec101 = this.treeSpec();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_treeSpec.add(treeSpec101.getTree());
                  }

                  alt45 = true;
                  nvaeMark = this.input.LA(1);
                  if ((nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 78) {
                     if (nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 46 && nvaeMark != 49 && nvaeMark != 51 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 66 || nvaeMark > 67) && nvaeMark != 72 && nvaeMark != 75 && nvaeMark != 91 && nvaeMark != 93) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 45, 0, this.input);
                        throw nvae;
                     }

                     alt45 = 2;
                  } else {
                     alt45 = 1;
                  }

                  switch (alt45) {
                     case 1:
                        this.pushFollow(FOLLOW_ebnfSuffix_in_elementNoOptionSpec1753);
                        ebnfSuffix102 = this.ebnfSuffix();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(ebnfSuffix102.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                           root_2 = (CommonTree)this.adaptor.nil();
                           root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(14, (String)"BLOCK")), root_2);
                           root_3 = (CommonTree)this.adaptor.nil();
                           root_3 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_3);
                           this.adaptor.addChild(root_3, stream_treeSpec.nextTree());
                           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(22, (String)"EOA"));
                           this.adaptor.addChild(root_2, root_3);
                           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(23, (String)"EOB"));
                           this.adaptor.addChild(root_1, root_2);
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_treeSpec.nextTree());
                           retval.tree = root_0;
                        }
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var105) {
            this.reportError(var105);
            this.recover(this.input, var105);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var105);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final atom_return atom() throws RecognitionException {
      atom_return retval = new atom_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token op = null;
      Token arg = null;
      Token RULE_REF106 = null;
      ParserRuleReturnScope range103 = null;
      ParserRuleReturnScope terminal104 = null;
      ParserRuleReturnScope notSet105 = null;
      CommonTree op_tree = null;
      CommonTree arg_tree = null;
      CommonTree RULE_REF106_tree = null;
      RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
      RewriteRuleTokenStream stream_ROOT = new RewriteRuleTokenStream(this.adaptor, "token ROOT");
      RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
      RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
      RewriteRuleSubtreeStream stream_range = new RewriteRuleSubtreeStream(this.adaptor, "rule range");
      RewriteRuleSubtreeStream stream_notSet = new RewriteRuleSubtreeStream(this.adaptor, "rule notSet");

      try {
         try {
            int alt54 = true;
            int LA50_0;
            byte alt54;
            NoViableAltException nvae;
            switch (this.input.LA(1)) {
               case 15:
                  int LA54_1 = this.input.LA(2);
                  if (LA54_1 == 44) {
                     alt54 = 1;
                  } else {
                     if (LA54_1 != 4 && LA54_1 != 13 && LA54_1 != 15 && (LA54_1 < 46 || LA54_1 > 47) && LA54_1 != 49 && LA54_1 != 51 && LA54_1 != 54 && (LA54_1 < 59 || LA54_1 > 60) && (LA54_1 < 66 || LA54_1 > 69) && LA54_1 != 72 && LA54_1 != 75 && LA54_1 != 78 && LA54_1 != 91 && LA54_1 != 93) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA50_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 54, 1, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA50_0);
                        }
                     }

                     alt54 = 2;
                  }
                  break;
               case 49:
                  alt54 = 4;
                  break;
               case 54:
               case 59:
               case 72:
                  alt54 = 2;
                  break;
               case 93:
                  alt54 = 3;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 54, 0, this.input);
                  throw nvae;
            }

            byte alt49;
            int LA49_0;
            byte alt50;
            boolean alt50;
            boolean alt49;
            RewriteRuleTokenStream stream_arg;
            NoViableAltException nvae;
            CommonTree root_1;
            label1286:
            switch (alt54) {
               case 1:
                  this.pushFollow(FOLLOW_range_in_atom1805);
                  range103 = this.range();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_range.add(range103.getTree());
                  }

                  alt50 = true;
                  LA50_0 = this.input.LA(1);
                  if (LA50_0 != 13 && LA50_0 != 47) {
                     if (LA50_0 != 4 && LA50_0 != 15 && LA50_0 != 46 && LA50_0 != 49 && LA50_0 != 51 && LA50_0 != 54 && (LA50_0 < 59 || LA50_0 > 60) && (LA50_0 < 66 || LA50_0 > 69) && LA50_0 != 72 && LA50_0 != 75 && LA50_0 != 78 && LA50_0 != 91 && LA50_0 != 93) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 48, 0, this.input);
                        throw nvae;
                     }

                     alt50 = 2;
                  } else {
                     alt50 = 1;
                  }

                  switch (alt50) {
                     case 1:
                        alt49 = true;
                        LA49_0 = this.input.LA(1);
                        if (LA49_0 == 47) {
                           alt49 = 1;
                        } else {
                           if (LA49_0 != 13) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvae = new NoViableAltException("", 47, 0, this.input);
                              throw nvae;
                           }

                           alt49 = 2;
                        }

                        switch (alt49) {
                           case 1:
                              op = (Token)this.match(this.input, 47, FOLLOW_ROOT_in_atom1812);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_ROOT.add(op);
                              }
                              break;
                           case 2:
                              op = (Token)this.match(this.input, 13, FOLLOW_BANG_in_atom1816);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_BANG.add(op);
                              }
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           stream_arg = new RewriteRuleTokenStream(this.adaptor, "token op", op);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_arg.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_range.nextTree());
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label1286;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_range.nextTree());
                           retval.tree = root_0;
                        }
                        break label1286;
                  }
               case 2:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_terminal_in_atom1844);
                  terminal104 = this.terminal();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, terminal104.getTree());
                  }
                  break;
               case 3:
                  this.pushFollow(FOLLOW_notSet_in_atom1852);
                  notSet105 = this.notSet();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_notSet.add(notSet105.getTree());
                  }

                  alt50 = true;
                  LA50_0 = this.input.LA(1);
                  if (LA50_0 != 13 && LA50_0 != 47) {
                     if (LA50_0 != 4 && LA50_0 != 15 && LA50_0 != 46 && LA50_0 != 49 && LA50_0 != 51 && LA50_0 != 54 && (LA50_0 < 59 || LA50_0 > 60) && (LA50_0 < 66 || LA50_0 > 69) && LA50_0 != 72 && LA50_0 != 75 && LA50_0 != 78 && LA50_0 != 91 && LA50_0 != 93) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 50, 0, this.input);
                        throw nvae;
                     }

                     alt50 = 2;
                  } else {
                     alt50 = 1;
                  }

                  switch (alt50) {
                     case 1:
                        alt49 = true;
                        LA49_0 = this.input.LA(1);
                        if (LA49_0 == 47) {
                           alt49 = 1;
                        } else {
                           if (LA49_0 != 13) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvae = new NoViableAltException("", 49, 0, this.input);
                              throw nvae;
                           }

                           alt49 = 2;
                        }

                        switch (alt49) {
                           case 1:
                              op = (Token)this.match(this.input, 47, FOLLOW_ROOT_in_atom1859);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_ROOT.add(op);
                              }
                              break;
                           case 2:
                              op = (Token)this.match(this.input, 13, FOLLOW_BANG_in_atom1863);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_BANG.add(op);
                              }
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           stream_arg = new RewriteRuleTokenStream(this.adaptor, "token op", op);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_arg.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_notSet.nextTree());
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label1286;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_notSet.nextTree());
                           retval.tree = root_0;
                        }
                     default:
                        break label1286;
                  }
               case 4:
                  RULE_REF106 = (Token)this.match(this.input, 49, FOLLOW_RULE_REF_in_atom1891);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_RULE_REF.add(RULE_REF106);
                  }

                  alt50 = 2;
                  LA50_0 = this.input.LA(1);
                  if (LA50_0 == 11) {
                     alt50 = 1;
                  }

                  switch (alt50) {
                     case 1:
                        arg = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_atom1897);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ARG_ACTION.add(arg);
                        }
                  }

                  alt49 = 2;
                  LA49_0 = this.input.LA(1);
                  if (LA49_0 == 13 || LA49_0 == 47) {
                     alt49 = 1;
                  }

                  switch (alt49) {
                     case 1:
                        int alt52 = true;
                        int LA52_0 = this.input.LA(1);
                        byte alt52;
                        if (LA52_0 == 47) {
                           alt52 = 1;
                        } else {
                           if (LA52_0 != 13) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              NoViableAltException nvae = new NoViableAltException("", 52, 0, this.input);
                              throw nvae;
                           }

                           alt52 = 2;
                        }

                        switch (alt52) {
                           case 1:
                              op = (Token)this.match(this.input, 47, FOLLOW_ROOT_in_atom1907);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_ROOT.add(op);
                              }
                              break;
                           case 2:
                              op = (Token)this.match(this.input, 13, FOLLOW_BANG_in_atom1911);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_BANG.add(op);
                              }
                        }
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     stream_arg = new RewriteRuleTokenStream(this.adaptor, "token arg", arg);
                     RewriteRuleTokenStream stream_op = new RewriteRuleTokenStream(this.adaptor, "token op", op);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1;
                     if (arg != null && op != null) {
                        root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_op.nextNode(), root_1);
                        this.adaptor.addChild(root_1, stream_RULE_REF.nextNode());
                        this.adaptor.addChild(root_1, stream_arg.nextNode());
                        this.adaptor.addChild(root_0, root_1);
                     } else if (arg != null) {
                        root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_RULE_REF.nextNode(), root_1);
                        this.adaptor.addChild(root_1, stream_arg.nextNode());
                        this.adaptor.addChild(root_0, root_1);
                     } else if (op != null) {
                        root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_op.nextNode(), root_1);
                        this.adaptor.addChild(root_1, stream_RULE_REF.nextNode());
                        this.adaptor.addChild(root_0, root_1);
                     } else {
                        this.adaptor.addChild(root_0, stream_RULE_REF.nextNode());
                     }

                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var35) {
            this.reportError(var35);
            this.recover(this.input, var35);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var35);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final notSet_return notSet() throws RecognitionException {
      notSet_return retval = new notSet_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal107 = null;
      ParserRuleReturnScope notTerminal108 = null;
      ParserRuleReturnScope block109 = null;
      CommonTree char_literal107_tree = null;
      RewriteRuleTokenStream stream_93 = new RewriteRuleTokenStream(this.adaptor, "token 93");
      RewriteRuleSubtreeStream stream_notTerminal = new RewriteRuleSubtreeStream(this.adaptor, "rule notTerminal");
      RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");

      try {
         try {
            char_literal107 = (Token)this.match(this.input, 93, FOLLOW_93_in_notSet1994);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_93.add(char_literal107);
            }

            int alt55 = true;
            int LA55_0 = this.input.LA(1);
            byte alt55;
            if (LA55_0 != 15 && LA55_0 != 54 && LA55_0 != 59) {
               if (LA55_0 != 66) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 55, 0, this.input);
                  throw nvae;
               }

               alt55 = 2;
            } else {
               alt55 = 1;
            }

            CommonTree root_1;
            switch (alt55) {
               case 1:
                  this.pushFollow(FOLLOW_notTerminal_in_notSet2000);
                  notTerminal108 = this.notTerminal();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_notTerminal.add(notTerminal108.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_93.nextNode(), root_1);
                     this.adaptor.addChild(root_1, stream_notTerminal.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_block_in_notSet2014);
                  block109 = this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_block.add(block109.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_93.nextNode(), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var17) {
            this.reportError(var17);
            this.recover(this.input, var17);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final treeSpec_return treeSpec() throws RecognitionException {
      treeSpec_return retval = new treeSpec_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token string_literal110 = null;
      Token char_literal113 = null;
      ParserRuleReturnScope element111 = null;
      ParserRuleReturnScope element112 = null;
      CommonTree string_literal110_tree = null;
      CommonTree char_literal113_tree = null;
      RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
      RewriteRuleTokenStream stream_TREE_BEGIN = new RewriteRuleTokenStream(this.adaptor, "token TREE_BEGIN");
      RewriteRuleSubtreeStream stream_element = new RewriteRuleSubtreeStream(this.adaptor, "rule element");

      try {
         string_literal110 = (Token)this.match(this.input, 60, FOLLOW_TREE_BEGIN_in_treeSpec2038);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_TREE_BEGIN.add(string_literal110);
            }

            this.pushFollow(FOLLOW_element_in_treeSpec2040);
            element111 = this.element();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  stream_element.add(element111.getTree());
               }

               int cnt56 = 0;

               while(true) {
                  int alt56 = 2;
                  int LA56_0 = this.input.LA(1);
                  if (LA56_0 == 4 || LA56_0 == 15 || LA56_0 == 49 || LA56_0 == 51 || LA56_0 == 54 || LA56_0 >= 59 && LA56_0 <= 60 || LA56_0 == 66 || LA56_0 == 72 || LA56_0 == 93) {
                     alt56 = 1;
                  }

                  switch (alt56) {
                     case 1:
                        this.pushFollow(FOLLOW_element_in_treeSpec2044);
                        element112 = this.element();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_element.add(element112.getTree());
                        }

                        ++cnt56;
                        break;
                     default:
                        if (cnt56 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           EarlyExitException eee = new EarlyExitException(56, this.input);
                           throw eee;
                        }

                        char_literal113 = (Token)this.match(this.input, 67, FOLLOW_67_in_treeSpec2049);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_67.add(char_literal113);
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(60, (String)"TREE_BEGIN")), root_1);
                           if (!stream_element.hasNext()) {
                              throw new RewriteEarlyExitException();
                           }

                           while(stream_element.hasNext()) {
                              this.adaptor.addChild(root_1, stream_element.nextTree());
                           }

                           stream_element.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }

                        retval.stop = this.input.LT(-1);
                        if (this.state.backtracking == 0) {
                           retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var19) {
         this.reportError(var19);
         this.recover(this.input, var19);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var19);
         return retval;
      } finally {
         ;
      }
   }

   public final ebnf_return ebnf() throws RecognitionException {
      ebnf_return retval = new ebnf_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token op = null;
      Token string_literal115 = null;
      ParserRuleReturnScope block114 = null;
      CommonTree op_tree = null;
      CommonTree string_literal115_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleTokenStream stream_78 = new RewriteRuleTokenStream(this.adaptor, "token 78");
      RewriteRuleTokenStream stream_77 = new RewriteRuleTokenStream(this.adaptor, "token 77");
      RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
      RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
      Token firstToken = this.input.LT(1);

      try {
         try {
            this.pushFollow(FOLLOW_block_in_ebnf2081);
            block114 = this.block();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_block.add(block114.getTree());
            }

            int alt57 = true;
            byte alt57;
            switch (this.input.LA(1)) {
               case 4:
               case 15:
               case 46:
               case 49:
               case 51:
               case 54:
               case 59:
               case 60:
               case 66:
               case 67:
               case 72:
               case 75:
               case 91:
               case 93:
                  alt57 = 5;
                  break;
               case 68:
                  alt57 = 2;
                  break;
               case 69:
                  alt57 = 3;
                  break;
               case 77:
                  alt57 = 4;
                  break;
               case 78:
                  alt57 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 57, 0, this.input);
                  throw nvae;
            }

            CommonTree root_1;
            switch (alt57) {
               case 1:
                  op = (Token)this.match(this.input, 78, FOLLOW_78_in_ebnf2089);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_78.add(op);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(39, (Token)op)), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  op = (Token)this.match(this.input, 68, FOLLOW_68_in_ebnf2106);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_68.add(op);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(17, (Token)op)), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  op = (Token)this.match(this.input, 69, FOLLOW_69_in_ebnf2123);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_69.add(op);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(43, (Token)op)), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 4:
                  string_literal115 = (Token)this.match(this.input, 77, FOLLOW_77_in_ebnf2140);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_77.add(string_literal115);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     if (this.gtype == 18 && Character.isUpperCase(((rule_scope)this.rule_stack.peek()).name.charAt(0))) {
                        root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(55, (String)"=>")), root_1);
                        this.adaptor.addChild(root_1, stream_block.nextTree());
                        this.adaptor.addChild(root_0, root_1);
                     } else {
                        this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(56, (String)"SYN_SEMPRED"));
                     }

                     retval.tree = root_0;
                  }
                  break;
               case 5:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_block.nextTree());
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree.getToken().setLine(firstToken.getLine());
               retval.tree.getToken().setCharPositionInLine(firstToken.getCharPositionInLine());
            }
         } catch (RecognitionException var20) {
            this.reportError(var20);
            this.recover(this.input, var20);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var20);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final range_return range() throws RecognitionException {
      range_return retval = new range_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token c1 = null;
      Token c2 = null;
      Token RANGE116 = null;
      CommonTree c1_tree = null;
      CommonTree c2_tree = null;
      CommonTree RANGE116_tree = null;
      RewriteRuleTokenStream stream_RANGE = new RewriteRuleTokenStream(this.adaptor, "token RANGE");
      RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");

      try {
         try {
            c1 = (Token)this.match(this.input, 15, FOLLOW_CHAR_LITERAL_in_range2223);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_CHAR_LITERAL.add(c1);
            }

            RANGE116 = (Token)this.match(this.input, 44, FOLLOW_RANGE_in_range2225);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_RANGE.add(RANGE116);
            }

            c2 = (Token)this.match(this.input, 15, FOLLOW_CHAR_LITERAL_in_range2229);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_CHAR_LITERAL.add(c2);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               RewriteRuleTokenStream stream_c1 = new RewriteRuleTokenStream(this.adaptor, "token c1", c1);
               RewriteRuleTokenStream stream_c2 = new RewriteRuleTokenStream(this.adaptor, "token c2", c2);
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(16, c1, "..")), root_1);
               this.adaptor.addChild(root_1, stream_c1.nextNode());
               this.adaptor.addChild(root_1, stream_c2.nextNode());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final terminal_return terminal() throws RecognitionException {
      terminal_return retval = new terminal_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token CHAR_LITERAL117 = null;
      Token TOKEN_REF118 = null;
      Token ARG_ACTION119 = null;
      Token STRING_LITERAL120 = null;
      Token char_literal121 = null;
      Token char_literal122 = null;
      Token char_literal123 = null;
      CommonTree CHAR_LITERAL117_tree = null;
      CommonTree TOKEN_REF118_tree = null;
      CommonTree ARG_ACTION119_tree = null;
      CommonTree STRING_LITERAL120_tree = null;
      CommonTree char_literal121_tree = null;
      CommonTree char_literal122_tree = null;
      CommonTree char_literal123_tree = null;
      RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
      RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
      RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
      RewriteRuleTokenStream stream_ROOT = new RewriteRuleTokenStream(this.adaptor, "token ROOT");
      RewriteRuleTokenStream stream_72 = new RewriteRuleTokenStream(this.adaptor, "token 72");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
      RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");

      try {
         try {
            int alt59 = true;
            byte alt59;
            switch (this.input.LA(1)) {
               case 15:
                  alt59 = 1;
                  break;
               case 54:
                  alt59 = 3;
                  break;
               case 59:
                  alt59 = 2;
                  break;
               case 72:
                  alt59 = 4;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 59, 0, this.input);
                  throw nvae;
            }

            int LA58_0;
            CommonTree root_1;
            byte alt58;
            label467:
            switch (alt59) {
               case 1:
                  CHAR_LITERAL117 = (Token)this.match(this.input, 15, FOLLOW_CHAR_LITERAL_in_terminal2260);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_CHAR_LITERAL.add(CHAR_LITERAL117);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_CHAR_LITERAL.nextNode());
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  TOKEN_REF118 = (Token)this.match(this.input, 59, FOLLOW_TOKEN_REF_in_terminal2282);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_TOKEN_REF.add(TOKEN_REF118);
                  }

                  int alt58 = true;
                  LA58_0 = this.input.LA(1);
                  if (LA58_0 == 11) {
                     alt58 = 1;
                  } else {
                     if (LA58_0 != 4 && LA58_0 != 13 && LA58_0 != 15 && (LA58_0 < 46 || LA58_0 > 47) && LA58_0 != 49 && LA58_0 != 51 && LA58_0 != 54 && (LA58_0 < 59 || LA58_0 > 60) && (LA58_0 < 66 || LA58_0 > 69) && LA58_0 != 72 && LA58_0 != 75 && LA58_0 != 78 && LA58_0 != 91 && LA58_0 != 93) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 58, 0, this.input);
                        throw nvae;
                     }

                     alt58 = 2;
                  }

                  switch (alt58) {
                     case 1:
                        ARG_ACTION119 = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_terminal2289);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ARG_ACTION.add(ARG_ACTION119);
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_TOKEN_REF.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label467;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_TOKEN_REF.nextNode());
                           retval.tree = root_0;
                        }
                     default:
                        break label467;
                  }
               case 3:
                  STRING_LITERAL120 = (Token)this.match(this.input, 54, FOLLOW_STRING_LITERAL_in_terminal2328);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_STRING_LITERAL.add(STRING_LITERAL120);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_STRING_LITERAL.nextNode());
                     retval.tree = root_0;
                  }
                  break;
               case 4:
                  char_literal121 = (Token)this.match(this.input, 72, FOLLOW_72_in_terminal2343);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_72.add(char_literal121);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, stream_72.nextNode());
                     retval.tree = root_0;
                  }
            }

            alt58 = 3;
            LA58_0 = this.input.LA(1);
            if (LA58_0 == 47) {
               alt58 = 1;
            } else if (LA58_0 == 13) {
               alt58 = 2;
            }

            RewriteRuleSubtreeStream stream_retval;
            switch (alt58) {
               case 1:
                  char_literal122 = (Token)this.match(this.input, 47, FOLLOW_ROOT_in_terminal2364);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_ROOT.add(char_literal122);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ROOT.nextNode(), root_1);
                     this.adaptor.addChild(root_1, stream_retval.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  char_literal123 = (Token)this.match(this.input, 13, FOLLOW_BANG_in_terminal2385);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_BANG.add(char_literal123);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_BANG.nextNode(), root_1);
                     this.adaptor.addChild(root_1, stream_retval.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var32) {
            this.reportError(var32);
            this.recover(this.input, var32);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var32);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final notTerminal_return notTerminal() throws RecognitionException {
      notTerminal_return retval = new notTerminal_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token set124 = null;
      CommonTree set124_tree = null;

      try {
         try {
            root_0 = (CommonTree)this.adaptor.nil();
            set124 = this.input.LT(1);
            if (this.input.LA(1) != 15 && this.input.LA(1) != 54 && this.input.LA(1) != 59) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return retval;
               }

               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               throw mse;
            }

            this.input.consume();
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set124));
            }

            this.state.errorRecovery = false;
            this.state.failed = false;
            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var9) {
            this.reportError(var9);
            this.recover(this.input, var9);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var9);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final ebnfSuffix_return ebnfSuffix() throws RecognitionException {
      ebnfSuffix_return retval = new ebnfSuffix_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal125 = null;
      Token char_literal126 = null;
      Token char_literal127 = null;
      CommonTree char_literal125_tree = null;
      CommonTree char_literal126_tree = null;
      CommonTree char_literal127_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleTokenStream stream_78 = new RewriteRuleTokenStream(this.adaptor, "token 78");
      RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
      Token op = this.input.LT(1);

      try {
         try {
            int alt61 = true;
            byte alt61;
            switch (this.input.LA(1)) {
               case 68:
                  alt61 = 2;
                  break;
               case 69:
                  alt61 = 3;
                  break;
               case 78:
                  alt61 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 61, 0, this.input);
                  throw nvae;
            }

            switch (alt61) {
               case 1:
                  char_literal125 = (Token)this.match(this.input, 78, FOLLOW_78_in_ebnfSuffix2445);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_78.add(char_literal125);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(39, (Token)op));
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  char_literal126 = (Token)this.match(this.input, 68, FOLLOW_68_in_ebnfSuffix2457);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_68.add(char_literal126);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(17, (Token)op));
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  char_literal127 = (Token)this.match(this.input, 69, FOLLOW_69_in_ebnfSuffix2470);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_69.add(char_literal127);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(43, (Token)op));
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_return rewrite() throws RecognitionException {
      rewrite_return retval = new rewrite_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token rew2 = null;
      Token rew = null;
      Token preds = null;
      List list_rew = null;
      List list_preds = null;
      List list_predicated = null;
      ParserRuleReturnScope last = null;
      RuleReturnScope predicated = null;
      CommonTree rew2_tree = null;
      CommonTree rew_tree = null;
      CommonTree preds_tree = null;
      RewriteRuleTokenStream stream_SEMPRED = new RewriteRuleTokenStream(this.adaptor, "token SEMPRED");
      RewriteRuleTokenStream stream_REWRITE = new RewriteRuleTokenStream(this.adaptor, "token REWRITE");
      RewriteRuleSubtreeStream stream_rewrite_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_alternative");
      Token firstToken = this.input.LT(1);

      try {
         try {
            int alt63 = true;
            int LA63_0 = this.input.LA(1);
            byte alt63;
            if (LA63_0 == 46) {
               alt63 = 1;
            } else {
               if (LA63_0 != 67 && LA63_0 != 75 && LA63_0 != 91) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 63, 0, this.input);
                  throw nvae;
               }

               alt63 = 2;
            }

            label273:
            switch (alt63) {
               case 1:
                  while(true) {
                     int alt62 = 2;
                     int LA62_0 = this.input.LA(1);
                     if (LA62_0 == 46) {
                        int LA62_1 = this.input.LA(2);
                        if (LA62_1 == 51) {
                           alt62 = 1;
                        }
                     }

                     switch (alt62) {
                        case 1:
                           rew = (Token)this.match(this.input, 46, FOLLOW_REWRITE_in_rewrite2499);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_REWRITE.add(rew);
                           }

                           if (list_rew == null) {
                              list_rew = new ArrayList();
                           }

                           list_rew.add(rew);
                           preds = (Token)this.match(this.input, 51, FOLLOW_SEMPRED_in_rewrite2503);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_SEMPRED.add(preds);
                           }

                           if (list_preds == null) {
                              list_preds = new ArrayList();
                           }

                           list_preds.add(preds);
                           this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite2507);
                           predicated = this.rewrite_alternative();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_rewrite_alternative.add(predicated.getTree());
                           }

                           if (list_predicated == null) {
                              list_predicated = new ArrayList();
                           }

                           list_predicated.add(predicated.getTree());
                           break;
                        default:
                           rew2 = (Token)this.match(this.input, 46, FOLLOW_REWRITE_in_rewrite2515);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_REWRITE.add(rew2);
                           }

                           this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite2519);
                           last = this.rewrite_alternative();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_rewrite_alternative.add(last.getTree());
                           }

                           if (this.state.backtracking != 0) {
                              break label273;
                           }

                           retval.tree = root_0;
                           RewriteRuleTokenStream stream_rew2 = new RewriteRuleTokenStream(this.adaptor, "token rew2", rew2);
                           RewriteRuleTokenStream stream_rew = new RewriteRuleTokenStream(this.adaptor, "token rew", list_rew);
                           RewriteRuleTokenStream stream_preds = new RewriteRuleTokenStream(this.adaptor, "token preds", list_preds);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           RewriteRuleSubtreeStream stream_last = new RewriteRuleSubtreeStream(this.adaptor, "rule last", last != null ? last.getTree() : null);
                           RewriteRuleSubtreeStream stream_predicated = new RewriteRuleSubtreeStream(this.adaptor, "token predicated", list_predicated);
                           root_0 = (CommonTree)this.adaptor.nil();

                           CommonTree root_1;
                           while(stream_rew.hasNext() || stream_predicated.hasNext() || stream_preds.hasNext()) {
                              root_1 = (CommonTree)this.adaptor.nil();
                              root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_rew.nextNode(), root_1);
                              this.adaptor.addChild(root_1, stream_preds.nextNode());
                              this.adaptor.addChild(root_1, stream_predicated.nextTree());
                              this.adaptor.addChild(root_0, root_1);
                           }

                           stream_rew.reset();
                           stream_predicated.reset();
                           stream_preds.reset();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_rew2.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_last.nextTree());
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                           break label273;
                     }
                  }
               case 2:
                  root_0 = (CommonTree)this.adaptor.nil();
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var30) {
            this.reportError(var30);
            this.recover(this.input, var30);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var30);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_alternative_return rewrite_alternative() throws RecognitionException {
      rewrite_alternative_return retval = new rewrite_alternative_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope rewrite_template128 = null;
      ParserRuleReturnScope rewrite_tree_alternative129 = null;

      try {
         try {
            byte alt64;
            int alt64 = true;
            int LA64_3;
            int nvaeMark;
            NoViableAltException nvae;
            int nvaeConsume;
            int nvaeMark;
            NoViableAltException nvae;
            label7692:
            switch (this.input.LA(1)) {
               case 4:
                  LA64_3 = this.input.LA(2);
                  if (this.synpred1_ANTLRv3()) {
                     alt64 = 1;
                  } else {
                     if (!this.synpred2_ANTLRv3()) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 64, 4, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt64 = 2;
                  }
                  break;
               case 15:
               case 54:
               case 60:
               case 65:
                  alt64 = 2;
                  break;
               case 46:
               case 67:
               case 75:
               case 91:
                  alt64 = 3;
                  break;
               case 49:
                  LA64_3 = this.input.LA(2);
                  if (LA64_3 == 66) {
                     switch (this.input.LA(3)) {
                        case 4:
                        case 15:
                        case 54:
                        case 60:
                        case 65:
                        case 66:
                           alt64 = 2;
                           break label7692;
                        case 49:
                           nvaeMark = this.input.LA(4);
                           if (nvaeMark == 76) {
                              alt64 = 1;
                              break label7692;
                           }

                           if (nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 49 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 65 || nvaeMark > 69) && nvaeMark != 78) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 64, 11, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt64 = 2;
                           break label7692;
                        case 59:
                           nvaeMark = this.input.LA(4);
                           if (nvaeMark == 76) {
                              alt64 = 1;
                              break label7692;
                           }

                           if (nvaeMark != 4 && nvaeMark != 11 && nvaeMark != 15 && nvaeMark != 49 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 65 || nvaeMark > 69) && nvaeMark != 78) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 64, 10, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt64 = 2;
                           break label7692;
                        case 67:
                           alt64 = 1;
                           break label7692;
                        default:
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 64, 7, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                     }
                  } else {
                     if (LA64_3 != 4 && LA64_3 != 15 && LA64_3 != 46 && LA64_3 != 49 && LA64_3 != 54 && (LA64_3 < 59 || LA64_3 > 60) && LA64_3 != 65 && (LA64_3 < 67 || LA64_3 > 69) && LA64_3 != 75 && LA64_3 != 78 && LA64_3 != 91) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 64, 2, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt64 = 2;
                     break;
                  }
               case 59:
                  LA64_3 = this.input.LA(2);
                  if (LA64_3 == 66) {
                     switch (this.input.LA(3)) {
                        case 4:
                        case 15:
                        case 54:
                        case 60:
                        case 65:
                        case 66:
                           alt64 = 2;
                           break label7692;
                        case 49:
                           nvaeMark = this.input.LA(4);
                           if (nvaeMark == 76) {
                              alt64 = 1;
                              break label7692;
                           }

                           if (nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 49 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 65 || nvaeMark > 69) && nvaeMark != 78) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 64, 11, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt64 = 2;
                           break label7692;
                        case 59:
                           nvaeMark = this.input.LA(4);
                           if (nvaeMark == 76) {
                              alt64 = 1;
                              break label7692;
                           }

                           if (nvaeMark != 4 && nvaeMark != 11 && nvaeMark != 15 && nvaeMark != 49 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 65 || nvaeMark > 69) && nvaeMark != 78) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 64, 10, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt64 = 2;
                           break label7692;
                        case 67:
                           alt64 = 1;
                           break label7692;
                        default:
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 64, 7, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                     }
                  }

                  if (LA64_3 != 4 && LA64_3 != 11 && LA64_3 != 15 && LA64_3 != 46 && LA64_3 != 49 && LA64_3 != 54 && (LA64_3 < 59 || LA64_3 > 60) && LA64_3 != 65 && (LA64_3 < 67 || LA64_3 > 69) && LA64_3 != 75 && LA64_3 != 78 && LA64_3 != 91) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 64, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt64 = 2;
                  break;
               case 66:
                  LA64_3 = this.input.LA(2);
                  if (LA64_3 == 4) {
                     nvaeMark = this.input.LA(3);
                     if (nvaeMark == 67) {
                        nvaeMark = this.input.LA(4);
                        if (nvaeMark == 66) {
                           alt64 = 1;
                           break;
                        }

                        if ((nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 78) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvaeConsume = this.input.mark();

                           try {
                              for(int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                 this.input.consume();
                              }

                              NoViableAltException nvae = new NoViableAltException("", 64, 12, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeConsume);
                           }
                        }

                        alt64 = 2;
                        break;
                     }

                     if (nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 49 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 65 || nvaeMark > 66) && (nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 78) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                              this.input.consume();
                           }

                           nvae = new NoViableAltException("", 64, 8, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt64 = 2;
                     break;
                  }

                  if (LA64_3 != 15 && LA64_3 != 49 && LA64_3 != 54 && (LA64_3 < 59 || LA64_3 > 60) && (LA64_3 < 65 || LA64_3 > 66)) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 64, 3, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt64 = 2;
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
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_template_in_rewrite_alternative2570);
                  rewrite_template128 = this.rewrite_template();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_template128.getTree());
                  }
                  break;
               case 2:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_alternative2575);
                  rewrite_tree_alternative129 = this.rewrite_tree_alternative();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_tree_alternative129.getTree());
                  }
                  break;
               case 3:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_1);
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(25, (String)"EPSILON"));
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(22, (String)"EOA"));
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var217) {
            this.reportError(var217);
            this.recover(this.input, var217);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var217);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_tree_block_return rewrite_tree_block() throws RecognitionException {
      rewrite_tree_block_return retval = new rewrite_tree_block_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token lp = null;
      Token char_literal131 = null;
      ParserRuleReturnScope rewrite_tree_alternative130 = null;
      CommonTree lp_tree = null;
      CommonTree char_literal131_tree = null;
      RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
      RewriteRuleTokenStream stream_66 = new RewriteRuleTokenStream(this.adaptor, "token 66");
      RewriteRuleSubtreeStream stream_rewrite_tree_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_alternative");

      try {
         try {
            lp = (Token)this.match(this.input, 66, FOLLOW_66_in_rewrite_tree_block2617);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_66.add(lp);
            }

            this.pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block2619);
            rewrite_tree_alternative130 = this.rewrite_tree_alternative();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_tree_alternative.add(rewrite_tree_alternative130.getTree());
            }

            char_literal131 = (Token)this.match(this.input, 67, FOLLOW_67_in_rewrite_tree_block2621);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_67.add(char_literal131);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(14, lp, "BLOCK")), root_1);
               this.adaptor.addChild(root_1, stream_rewrite_tree_alternative.nextTree());
               this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(23, lp, "EOB"));
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.recover(this.input, var16);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_tree_alternative_return rewrite_tree_alternative() throws RecognitionException {
      rewrite_tree_alternative_return retval = new rewrite_tree_alternative_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope rewrite_tree_element132 = null;
      RewriteRuleSubtreeStream stream_rewrite_tree_element = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_element");

      try {
         int cnt65 = 0;

         while(true) {
            int alt65 = 2;
            int LA65_0 = this.input.LA(1);
            if (LA65_0 == 4 || LA65_0 == 15 || LA65_0 == 49 || LA65_0 == 54 || LA65_0 >= 59 && LA65_0 <= 60 || LA65_0 >= 65 && LA65_0 <= 66) {
               alt65 = 1;
            }

            switch (alt65) {
               case 1:
                  this.pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative2655);
                  rewrite_tree_element132 = this.rewrite_tree_element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_tree_element.add(rewrite_tree_element132.getTree());
                  }

                  ++cnt65;
                  break;
               default:
                  if (cnt65 < 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     EarlyExitException eee = new EarlyExitException(65, this.input);
                     throw eee;
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_1);
                     if (!stream_rewrite_tree_element.hasNext()) {
                        throw new RewriteEarlyExitException();
                     }

                     while(stream_rewrite_tree_element.hasNext()) {
                        this.adaptor.addChild(root_1, stream_rewrite_tree_element.nextTree());
                     }

                     stream_rewrite_tree_element.reset();
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(22, (String)"EOA"));
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }

                  retval.stop = this.input.LT(-1);
                  if (this.state.backtracking == 0) {
                     retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  }

                  return retval;
            }
         }
      } catch (RecognitionException var12) {
         this.reportError(var12);
         this.recover(this.input, var12);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var12);
         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_tree_element_return rewrite_tree_element() throws RecognitionException {
      rewrite_tree_element_return retval = new rewrite_tree_element_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope rewrite_tree_atom133 = null;
      ParserRuleReturnScope rewrite_tree_atom134 = null;
      ParserRuleReturnScope ebnfSuffix135 = null;
      ParserRuleReturnScope rewrite_tree136 = null;
      ParserRuleReturnScope ebnfSuffix137 = null;
      ParserRuleReturnScope rewrite_tree_ebnf138 = null;
      RewriteRuleSubtreeStream stream_rewrite_tree = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree");
      RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
      RewriteRuleSubtreeStream stream_rewrite_tree_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_atom");

      try {
         try {
            int LA66_0;
            byte alt67;
            NoViableAltException nvae;
            int alt67 = true;
            int nvaeMark;
            int nvaeMark;
            label6474:
            switch (this.input.LA(1)) {
               case 4:
                  nvaeMark = this.input.LA(2);
                  if (nvaeMark != -1 && nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 46 && nvaeMark != 49 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 65 || nvaeMark > 67) && nvaeMark != 75 && nvaeMark != 91) {
                     if ((nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 78) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA66_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 67, 6, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA66_0);
                        }
                     }

                     alt67 = 2;
                  } else {
                     alt67 = 1;
                  }
                  break;
               case 15:
                  nvaeMark = this.input.LA(2);
                  if (nvaeMark != -1 && nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 46 && nvaeMark != 49 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 65 || nvaeMark > 67) && nvaeMark != 75 && nvaeMark != 91) {
                     if ((nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 78) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA66_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 67, 1, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA66_0);
                        }
                     }

                     alt67 = 2;
                  } else {
                     alt67 = 1;
                  }
                  break;
               case 49:
                  nvaeMark = this.input.LA(2);
                  if (nvaeMark != -1 && nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 46 && nvaeMark != 49 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 65 || nvaeMark > 67) && nvaeMark != 75 && nvaeMark != 91) {
                     if ((nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 78) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA66_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 67, 3, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA66_0);
                        }
                     }

                     alt67 = 2;
                  } else {
                     alt67 = 1;
                  }
                  break;
               case 54:
                  nvaeMark = this.input.LA(2);
                  if (nvaeMark != -1 && nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 46 && nvaeMark != 49 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 65 || nvaeMark > 67) && nvaeMark != 75 && nvaeMark != 91) {
                     if ((nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 78) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA66_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 67, 4, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA66_0);
                        }
                     }

                     alt67 = 2;
                  } else {
                     alt67 = 1;
                  }
                  break;
               case 59:
                  switch (this.input.LA(2)) {
                     case -1:
                     case 4:
                     case 15:
                     case 46:
                     case 49:
                     case 54:
                     case 59:
                     case 60:
                     case 65:
                     case 66:
                     case 67:
                     case 75:
                     case 91:
                        alt67 = 1;
                        break label6474;
                     case 11:
                        nvaeMark = this.input.LA(3);
                        if (nvaeMark != -1 && nvaeMark != 4 && nvaeMark != 15 && nvaeMark != 46 && nvaeMark != 49 && nvaeMark != 54 && (nvaeMark < 59 || nvaeMark > 60) && (nvaeMark < 65 || nvaeMark > 67) && nvaeMark != 75 && nvaeMark != 91) {
                           if ((nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 78) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              LA66_0 = this.input.mark();

                              try {
                                 for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 67, 11, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(LA66_0);
                              }
                           }

                           alt67 = 2;
                        } else {
                           alt67 = 1;
                        }
                        break label6474;
                     case 68:
                     case 69:
                     case 78:
                        alt67 = 2;
                        break label6474;
                     default:
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           NoViableAltException nvae = new NoViableAltException("", 67, 2, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                  }
               case 60:
                  alt67 = 3;
                  break;
               case 65:
                  nvaeMark = this.input.LA(2);
                  int nvaeConsume;
                  NoViableAltException nvae;
                  if (nvaeMark == 59) {
                     LA66_0 = this.input.LA(3);
                     if (LA66_0 != -1 && LA66_0 != 4 && LA66_0 != 15 && LA66_0 != 46 && LA66_0 != 49 && LA66_0 != 54 && (LA66_0 < 59 || LA66_0 > 60) && (LA66_0 < 65 || LA66_0 > 67) && LA66_0 != 75 && LA66_0 != 91) {
                        if ((LA66_0 < 68 || LA66_0 > 69) && LA66_0 != 78) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 67, 12, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                        }

                        alt67 = 2;
                     } else {
                        alt67 = 1;
                     }
                  } else {
                     if (nvaeMark != 49) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA66_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 67, 5, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA66_0);
                        }
                     }

                     LA66_0 = this.input.LA(3);
                     if (LA66_0 != -1 && LA66_0 != 4 && LA66_0 != 15 && LA66_0 != 46 && LA66_0 != 49 && LA66_0 != 54 && (LA66_0 < 59 || LA66_0 > 60) && (LA66_0 < 65 || LA66_0 > 67) && LA66_0 != 75 && LA66_0 != 91) {
                        if ((LA66_0 < 68 || LA66_0 > 69) && LA66_0 != 78) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 67, 13, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                        }

                        alt67 = 2;
                     } else {
                        alt67 = 1;
                     }
                  }
                  break;
               case 66:
                  alt67 = 4;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 67, 0, this.input);
                  throw nvae;
            }

            CommonTree root_1;
            label6306:
            switch (alt67) {
               case 1:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2683);
                  rewrite_tree_atom133 = this.rewrite_tree_atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_tree_atom133.getTree());
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2688);
                  rewrite_tree_atom134 = this.rewrite_tree_atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_tree_atom.add(rewrite_tree_atom134.getTree());
                  }

                  this.pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_element2690);
                  ebnfSuffix135 = this.ebnfSuffix();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_ebnfSuffix.add(ebnfSuffix135.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                     CommonTree root_2 = (CommonTree)this.adaptor.nil();
                     root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(14, (String)"BLOCK")), root_2);
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_1);
                     this.adaptor.addChild(root_1, stream_rewrite_tree_atom.nextTree());
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(22, (String)"EOA"));
                     this.adaptor.addChild(root_2, root_1);
                     this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(23, (String)"EOB"));
                     this.adaptor.addChild(root_1, root_2);
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  this.pushFollow(FOLLOW_rewrite_tree_in_rewrite_tree_element2724);
                  rewrite_tree136 = this.rewrite_tree();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_tree.add(rewrite_tree136.getTree());
                  }

                  int alt66 = true;
                  LA66_0 = this.input.LA(1);
                  byte alt66;
                  if ((LA66_0 < 68 || LA66_0 > 69) && LA66_0 != 78) {
                     if (LA66_0 != -1 && LA66_0 != 4 && LA66_0 != 15 && LA66_0 != 46 && LA66_0 != 49 && LA66_0 != 54 && (LA66_0 < 59 || LA66_0 > 60) && (LA66_0 < 65 || LA66_0 > 67) && LA66_0 != 75 && LA66_0 != 91) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 66, 0, this.input);
                        throw nvae;
                     }

                     alt66 = 2;
                  } else {
                     alt66 = 1;
                  }

                  switch (alt66) {
                     case 1:
                        this.pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_element2730);
                        ebnfSuffix137 = this.ebnfSuffix();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(ebnfSuffix137.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                           CommonTree root_2 = (CommonTree)this.adaptor.nil();
                           root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(14, (String)"BLOCK")), root_2);
                           CommonTree root_3 = (CommonTree)this.adaptor.nil();
                           root_3 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_3);
                           this.adaptor.addChild(root_3, stream_rewrite_tree.nextTree());
                           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(22, (String)"EOA"));
                           this.adaptor.addChild(root_2, root_3);
                           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(23, (String)"EOB"));
                           this.adaptor.addChild(root_1, root_2);
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label6306;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_rewrite_tree.nextTree());
                           retval.tree = root_0;
                        }
                     default:
                        break label6306;
                  }
               case 4:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element2776);
                  rewrite_tree_ebnf138 = this.rewrite_tree_ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_tree_ebnf138.getTree());
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var145) {
            this.reportError(var145);
            this.recover(this.input, var145);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var145);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_tree_atom_return rewrite_tree_atom() throws RecognitionException {
      rewrite_tree_atom_return retval = new rewrite_tree_atom_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token d = null;
      Token CHAR_LITERAL139 = null;
      Token TOKEN_REF140 = null;
      Token ARG_ACTION141 = null;
      Token RULE_REF142 = null;
      Token STRING_LITERAL143 = null;
      Token ACTION145 = null;
      ParserRuleReturnScope id144 = null;
      CommonTree d_tree = null;
      CommonTree CHAR_LITERAL139_tree = null;
      CommonTree TOKEN_REF140_tree = null;
      CommonTree ARG_ACTION141_tree = null;
      CommonTree RULE_REF142_tree = null;
      CommonTree STRING_LITERAL143_tree = null;
      CommonTree ACTION145_tree = null;
      RewriteRuleTokenStream stream_65 = new RewriteRuleTokenStream(this.adaptor, "token 65");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
      RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         try {
            int alt69 = true;
            byte alt69;
            switch (this.input.LA(1)) {
               case 4:
                  alt69 = 6;
                  break;
               case 15:
                  alt69 = 1;
                  break;
               case 49:
                  alt69 = 3;
                  break;
               case 54:
                  alt69 = 4;
                  break;
               case 59:
                  alt69 = 2;
                  break;
               case 65:
                  alt69 = 5;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 69, 0, this.input);
                  throw nvae;
            }

            label275:
            switch (alt69) {
               case 1:
                  root_0 = (CommonTree)this.adaptor.nil();
                  CHAR_LITERAL139 = (Token)this.match(this.input, 15, FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom2792);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     CHAR_LITERAL139_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL139);
                     this.adaptor.addChild(root_0, CHAR_LITERAL139_tree);
                  }
                  break;
               case 2:
                  TOKEN_REF140 = (Token)this.match(this.input, 59, FOLLOW_TOKEN_REF_in_rewrite_tree_atom2799);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_TOKEN_REF.add(TOKEN_REF140);
                  }

                  int alt68 = 2;
                  int LA68_0 = this.input.LA(1);
                  if (LA68_0 == 11) {
                     alt68 = 1;
                  }

                  switch (alt68) {
                     case 1:
                        ARG_ACTION141 = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_rewrite_tree_atom2801);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ARG_ACTION.add(ARG_ACTION141);
                        }
                     default:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_TOKEN_REF.nextNode(), root_1);
                           if (stream_ARG_ACTION.hasNext()) {
                              this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
                           }

                           stream_ARG_ACTION.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label275;
                  }
               case 3:
                  root_0 = (CommonTree)this.adaptor.nil();
                  RULE_REF142 = (Token)this.match(this.input, 49, FOLLOW_RULE_REF_in_rewrite_tree_atom2822);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RULE_REF142_tree = (CommonTree)this.adaptor.create(RULE_REF142);
                     this.adaptor.addChild(root_0, RULE_REF142_tree);
                  }
                  break;
               case 4:
                  root_0 = (CommonTree)this.adaptor.nil();
                  STRING_LITERAL143 = (Token)this.match(this.input, 54, FOLLOW_STRING_LITERAL_in_rewrite_tree_atom2829);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     STRING_LITERAL143_tree = (CommonTree)this.adaptor.create(STRING_LITERAL143);
                     this.adaptor.addChild(root_0, STRING_LITERAL143_tree);
                  }
                  break;
               case 5:
                  d = (Token)this.match(this.input, 65, FOLLOW_65_in_rewrite_tree_atom2838);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_65.add(d);
                  }

                  this.pushFollow(FOLLOW_id_in_rewrite_tree_atom2840);
                  id144 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(id144.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(32, d, id144 != null ? this.input.toString(id144.start, id144.stop) : null));
                     retval.tree = root_0;
                  }
                  break;
               case 6:
                  root_0 = (CommonTree)this.adaptor.nil();
                  ACTION145 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_tree_atom2851);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION145_tree = (CommonTree)this.adaptor.create(ACTION145);
                     this.adaptor.addChild(root_0, ACTION145_tree);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var30) {
            this.reportError(var30);
            this.recover(this.input, var30);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var30);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_tree_ebnf_return rewrite_tree_ebnf() throws RecognitionException {
      rewrite_tree_ebnf_return retval = new rewrite_tree_ebnf_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope rewrite_tree_block146 = null;
      ParserRuleReturnScope ebnfSuffix147 = null;
      RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
      RewriteRuleSubtreeStream stream_rewrite_tree_block = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_block");
      Token firstToken = this.input.LT(1);

      try {
         try {
            this.pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf2872);
            rewrite_tree_block146 = this.rewrite_tree_block();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_tree_block.add(rewrite_tree_block146.getTree());
            }

            this.pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_ebnf2874);
            ebnfSuffix147 = this.ebnfSuffix();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ebnfSuffix.add(ebnfSuffix147.getTree());
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
               this.adaptor.addChild(root_1, stream_rewrite_tree_block.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }

            if (this.state.backtracking == 0) {
               retval.tree.getToken().setLine(firstToken.getLine());
               retval.tree.getToken().setCharPositionInLine(firstToken.getCharPositionInLine());
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.recover(this.input, var13);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_tree_return rewrite_tree() throws RecognitionException {
      rewrite_tree_return retval = new rewrite_tree_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token string_literal148 = null;
      Token char_literal151 = null;
      ParserRuleReturnScope rewrite_tree_atom149 = null;
      ParserRuleReturnScope rewrite_tree_element150 = null;
      CommonTree string_literal148_tree = null;
      CommonTree char_literal151_tree = null;
      RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
      RewriteRuleTokenStream stream_TREE_BEGIN = new RewriteRuleTokenStream(this.adaptor, "token TREE_BEGIN");
      RewriteRuleSubtreeStream stream_rewrite_tree_element = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_element");
      RewriteRuleSubtreeStream stream_rewrite_tree_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_atom");

      try {
         string_literal148 = (Token)this.match(this.input, 60, FOLLOW_TREE_BEGIN_in_rewrite_tree2894);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_TREE_BEGIN.add(string_literal148);
            }

            this.pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree2896);
            rewrite_tree_atom149 = this.rewrite_tree_atom();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  stream_rewrite_tree_atom.add(rewrite_tree_atom149.getTree());
               }

               while(true) {
                  int alt70 = 2;
                  int LA70_0 = this.input.LA(1);
                  if (LA70_0 == 4 || LA70_0 == 15 || LA70_0 == 49 || LA70_0 == 54 || LA70_0 >= 59 && LA70_0 <= 60 || LA70_0 >= 65 && LA70_0 <= 66) {
                     alt70 = 1;
                  }

                  switch (alt70) {
                     case 1:
                        this.pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree2898);
                        rewrite_tree_element150 = this.rewrite_tree_element();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_rewrite_tree_element.add(rewrite_tree_element150.getTree());
                        }
                        break;
                     default:
                        char_literal151 = (Token)this.match(this.input, 67, FOLLOW_67_in_rewrite_tree2901);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_67.add(char_literal151);
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(60, (String)"TREE_BEGIN")), root_1);
                           this.adaptor.addChild(root_1, stream_rewrite_tree_atom.nextTree());

                           while(stream_rewrite_tree_element.hasNext()) {
                              this.adaptor.addChild(root_1, stream_rewrite_tree_element.nextTree());
                           }

                           stream_rewrite_tree_element.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }

                        retval.stop = this.input.LT(-1);
                        if (this.state.backtracking == 0) {
                           retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                        }

                        return retval;
                  }
               }
            }
         }
      } catch (RecognitionException var19) {
         this.reportError(var19);
         this.recover(this.input, var19);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var19);
         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_template_return rewrite_template() throws RecognitionException {
      rewrite_template_return retval = new rewrite_template_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token lp = null;
      Token str = null;
      Token char_literal154 = null;
      Token ACTION157 = null;
      ParserRuleReturnScope id152 = null;
      ParserRuleReturnScope rewrite_template_args153 = null;
      ParserRuleReturnScope rewrite_template_ref155 = null;
      ParserRuleReturnScope rewrite_indirect_template_head156 = null;
      CommonTree lp_tree = null;
      CommonTree str_tree = null;
      CommonTree char_literal154_tree = null;
      CommonTree ACTION157_tree = null;
      RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
      RewriteRuleTokenStream stream_66 = new RewriteRuleTokenStream(this.adaptor, "token 66");
      RewriteRuleTokenStream stream_DOUBLE_QUOTE_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_QUOTE_STRING_LITERAL");
      RewriteRuleTokenStream stream_DOUBLE_ANGLE_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_ANGLE_STRING_LITERAL");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");

      try {
         try {
            int alt72 = true;
            int alt72 = this.dfa72.predict(this.input);
            switch (alt72) {
               case 1:
                  this.pushFollow(FOLLOW_id_in_rewrite_template2933);
                  id152 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(id152.getTree());
                  }

                  lp = (Token)this.match(this.input, 66, FOLLOW_66_in_rewrite_template2937);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_66.add(lp);
                  }

                  this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template2939);
                  rewrite_template_args153 = this.rewrite_template_args();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_template_args.add(rewrite_template_args153.getTree());
                  }

                  char_literal154 = (Token)this.match(this.input, 67, FOLLOW_67_in_rewrite_template2941);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_67.add(char_literal154);
                  }

                  int alt71 = true;
                  int LA71_0 = this.input.LA(1);
                  byte alt71;
                  if (LA71_0 == 21) {
                     alt71 = 1;
                  } else {
                     if (LA71_0 != 20) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 71, 0, this.input);
                        throw nvae;
                     }

                     alt71 = 2;
                  }

                  switch (alt71) {
                     case 1:
                        str = (Token)this.match(this.input, 21, FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template2949);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_DOUBLE_QUOTE_STRING_LITERAL.add(str);
                        }
                        break;
                     case 2:
                        str = (Token)this.match(this.input, 20, FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template2955);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_DOUBLE_ANGLE_STRING_LITERAL.add(str);
                        }
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleTokenStream stream_str = new RewriteRuleTokenStream(this.adaptor, "token str", str);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(57, lp, "TEMPLATE")), root_1);
                     this.adaptor.addChild(root_1, stream_id.nextTree());
                     this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
                     this.adaptor.addChild(root_1, stream_str.nextNode());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_template_ref_in_rewrite_template2982);
                  rewrite_template_ref155 = this.rewrite_template_ref();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_template_ref155.getTree());
                  }
                  break;
               case 3:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_indirect_template_head_in_rewrite_template2991);
                  rewrite_indirect_template_head156 = this.rewrite_indirect_template_head();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_indirect_template_head156.getTree());
                  }
                  break;
               case 4:
                  root_0 = (CommonTree)this.adaptor.nil();
                  ACTION157 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template3000);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION157_tree = (CommonTree)this.adaptor.create(ACTION157);
                     this.adaptor.addChild(root_0, ACTION157_tree);
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var30) {
            this.reportError(var30);
            this.recover(this.input, var30);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var30);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_template_ref_return rewrite_template_ref() throws RecognitionException {
      rewrite_template_ref_return retval = new rewrite_template_ref_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token lp = null;
      Token char_literal160 = null;
      ParserRuleReturnScope id158 = null;
      ParserRuleReturnScope rewrite_template_args159 = null;
      CommonTree lp_tree = null;
      CommonTree char_literal160_tree = null;
      RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
      RewriteRuleTokenStream stream_66 = new RewriteRuleTokenStream(this.adaptor, "token 66");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");

      try {
         try {
            this.pushFollow(FOLLOW_id_in_rewrite_template_ref3013);
            id158 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id158.getTree());
            }

            lp = (Token)this.match(this.input, 66, FOLLOW_66_in_rewrite_template_ref3017);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_66.add(lp);
            }

            this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template_ref3019);
            rewrite_template_args159 = this.rewrite_template_args();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_template_args.add(rewrite_template_args159.getTree());
            }

            char_literal160 = (Token)this.match(this.input, 67, FOLLOW_67_in_rewrite_template_ref3021);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_67.add(char_literal160);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(57, lp, "TEMPLATE")), root_1);
               this.adaptor.addChild(root_1, stream_id.nextTree());
               this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var18);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_indirect_template_head_return rewrite_indirect_template_head() throws RecognitionException {
      rewrite_indirect_template_head_return retval = new rewrite_indirect_template_head_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token lp = null;
      Token ACTION161 = null;
      Token char_literal162 = null;
      Token char_literal163 = null;
      Token char_literal165 = null;
      ParserRuleReturnScope rewrite_template_args164 = null;
      CommonTree lp_tree = null;
      CommonTree ACTION161_tree = null;
      CommonTree char_literal162_tree = null;
      CommonTree char_literal163_tree = null;
      CommonTree char_literal165_tree = null;
      RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
      RewriteRuleTokenStream stream_66 = new RewriteRuleTokenStream(this.adaptor, "token 66");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");

      try {
         try {
            lp = (Token)this.match(this.input, 66, FOLLOW_66_in_rewrite_indirect_template_head3049);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_66.add(lp);
            }

            ACTION161 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_indirect_template_head3051);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION161);
            }

            char_literal162 = (Token)this.match(this.input, 67, FOLLOW_67_in_rewrite_indirect_template_head3053);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_67.add(char_literal162);
            }

            char_literal163 = (Token)this.match(this.input, 66, FOLLOW_66_in_rewrite_indirect_template_head3055);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_66.add(char_literal163);
            }

            this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3057);
            rewrite_template_args164 = this.rewrite_template_args();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_template_args.add(rewrite_template_args164.getTree());
            }

            char_literal165 = (Token)this.match(this.input, 67, FOLLOW_67_in_rewrite_indirect_template_head3059);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_67.add(char_literal165);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(57, lp, "TEMPLATE")), root_1);
               this.adaptor.addChild(root_1, stream_ACTION.nextNode());
               this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var23) {
            this.reportError(var23);
            this.recover(this.input, var23);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var23);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_template_args_return rewrite_template_args() throws RecognitionException {
      rewrite_template_args_return retval = new rewrite_template_args_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal167 = null;
      ParserRuleReturnScope rewrite_template_arg166 = null;
      ParserRuleReturnScope rewrite_template_arg168 = null;
      CommonTree char_literal167_tree = null;
      RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
      RewriteRuleSubtreeStream stream_rewrite_template_arg = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_arg");

      try {
         try {
            int alt74 = true;
            int LA74_0 = this.input.LA(1);
            byte alt74;
            if (LA74_0 != 49 && LA74_0 != 59) {
               if (LA74_0 != 67) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 74, 0, this.input);
                  throw nvae;
               }

               alt74 = 2;
            } else {
               alt74 = 1;
            }

            label204: {
               switch (alt74) {
                  case 1:
                     this.pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args3083);
                     rewrite_template_arg166 = this.rewrite_template_arg();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_rewrite_template_arg.add(rewrite_template_arg166.getTree());
                     }
                     break;
                  case 2:
                     if (this.state.backtracking == 0) {
                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(10, (String)"ARGLIST"));
                        retval.tree = root_0;
                     }
                  default:
                     break label204;
               }

               label203:
               while(true) {
                  int alt73 = 2;
                  int LA73_0 = this.input.LA(1);
                  if (LA73_0 == 71) {
                     alt73 = 1;
                  }

                  switch (alt73) {
                     case 1:
                        char_literal167 = (Token)this.match(this.input, 71, FOLLOW_71_in_rewrite_template_args3086);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_71.add(char_literal167);
                        }

                        this.pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args3088);
                        rewrite_template_arg168 = this.rewrite_template_arg();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_rewrite_template_arg.add(rewrite_template_arg168.getTree());
                        }
                        break;
                     default:
                        if (this.state.backtracking != 0) {
                           break label203;
                        }

                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        CommonTree root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(10, (String)"ARGLIST")), root_1);
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
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var17) {
            this.reportError(var17);
            this.recover(this.input, var17);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var17);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final rewrite_template_arg_return rewrite_template_arg() throws RecognitionException {
      rewrite_template_arg_return retval = new rewrite_template_arg_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal170 = null;
      Token ACTION171 = null;
      ParserRuleReturnScope id169 = null;
      CommonTree char_literal170_tree = null;
      CommonTree ACTION171_tree = null;
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         try {
            this.pushFollow(FOLLOW_id_in_rewrite_template_arg3121);
            id169 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id169.getTree());
            }

            char_literal170 = (Token)this.match(this.input, 76, FOLLOW_76_in_rewrite_template_arg3123);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_76.add(char_literal170);
            }

            ACTION171 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template_arg3125);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION171);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(9, (Token)(id169 != null ? id169.start : null))), root_1);
               this.adaptor.addChild(root_1, stream_id.nextTree());
               this.adaptor.addChild(root_1, stream_ACTION.nextNode());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var16) {
            this.reportError(var16);
            this.recover(this.input, var16);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final id_return id() throws RecognitionException {
      id_return retval = new id_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token TOKEN_REF172 = null;
      Token RULE_REF173 = null;
      CommonTree TOKEN_REF172_tree = null;
      CommonTree RULE_REF173_tree = null;
      RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");

      try {
         try {
            int alt75 = true;
            int LA75_0 = this.input.LA(1);
            byte alt75;
            if (LA75_0 == 59) {
               alt75 = 1;
            } else {
               if (LA75_0 != 49) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 75, 0, this.input);
                  throw nvae;
               }

               alt75 = 2;
            }

            switch (alt75) {
               case 1:
                  TOKEN_REF172 = (Token)this.match(this.input, 59, FOLLOW_TOKEN_REF_in_id3146);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_TOKEN_REF.add(TOKEN_REF172);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(29, (Token)TOKEN_REF172));
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  RULE_REF173 = (Token)this.match(this.input, 49, FOLLOW_RULE_REF_in_id3156);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_RULE_REF.add(RULE_REF173);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(29, (Token)RULE_REF173));
                     retval.tree = root_0;
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var15);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void synpred1_ANTLRv3_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_rewrite_template_in_synpred1_ANTLRv32570);
      this.rewrite_template();
      --this.state._fsp;
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred2_ANTLRv3_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_rewrite_tree_alternative_in_synpred2_ANTLRv32575);
      this.rewrite_tree_alternative();
      --this.state._fsp;
      if (!this.state.failed) {
         ;
      }
   }

   public final boolean synpred2_ANTLRv3() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred2_ANTLRv3_fragment();
      } catch (RecognitionException var3) {
         System.err.println("impossible: " + var3);
      }

      boolean success = !this.state.failed;
      this.input.rewind(start);
      --this.state.backtracking;
      this.state.failed = false;
      return success;
   }

   public final boolean synpred1_ANTLRv3() {
      ++this.state.backtracking;
      int start = this.input.mark();

      try {
         this.synpred1_ANTLRv3_fragment();
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
      int numStates = DFA72_transitionS.length;
      DFA72_transition = new short[numStates][];

      for(int i = 0; i < numStates; ++i) {
         DFA72_transition[i] = DFA.unpackEncodedString(DFA72_transitionS[i]);
      }

      FOLLOW_DOC_COMMENT_in_grammarDef347 = new BitSet(new long[]{0L, 68943872L});
      FOLLOW_83_in_grammarDef357 = new BitSet(new long[]{0L, 262144L});
      FOLLOW_84_in_grammarDef375 = new BitSet(new long[]{0L, 262144L});
      FOLLOW_90_in_grammarDef391 = new BitSet(new long[]{0L, 262144L});
      FOLLOW_82_in_grammarDef432 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_grammarDef434 = new BitSet(new long[]{0L, 2048L});
      FOLLOW_75_in_grammarDef436 = new BitSet(new long[]{866381077961768960L, 14712832L});
      FOLLOW_optionsSpec_in_grammarDef438 = new BitSet(new long[]{866379978450141184L, 14712832L});
      FOLLOW_tokensSpec_in_grammarDef441 = new BitSet(new long[]{578149602298429440L, 14712832L});
      FOLLOW_attrScope_in_grammarDef444 = new BitSet(new long[]{578149602298429440L, 14712832L});
      FOLLOW_action_in_grammarDef447 = new BitSet(new long[]{577023702391586816L, 14712832L});
      FOLLOW_rule_in_grammarDef455 = new BitSet(new long[]{577023702391586816L, 14680064L});
      FOLLOW_EOF_in_grammarDef463 = new BitSet(new long[]{2L});
      FOLLOW_TOKENS_in_tokensSpec524 = new BitSet(new long[]{576460752303423488L});
      FOLLOW_tokenSpec_in_tokensSpec526 = new BitSet(new long[]{576460752303423488L, 268435456L});
      FOLLOW_92_in_tokensSpec529 = new BitSet(new long[]{2L});
      FOLLOW_TOKEN_REF_in_tokenSpec549 = new BitSet(new long[]{0L, 6144L});
      FOLLOW_76_in_tokenSpec555 = new BitSet(new long[]{18014398509514752L});
      FOLLOW_STRING_LITERAL_in_tokenSpec560 = new BitSet(new long[]{0L, 2048L});
      FOLLOW_CHAR_LITERAL_in_tokenSpec564 = new BitSet(new long[]{0L, 2048L});
      FOLLOW_75_in_tokenSpec603 = new BitSet(new long[]{2L});
      FOLLOW_SCOPE_in_attrScope614 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_attrScope616 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_attrScope618 = new BitSet(new long[]{2L});
      FOLLOW_79_in_action641 = new BitSet(new long[]{577023702256844800L, 1572864L});
      FOLLOW_actionScopeName_in_action644 = new BitSet(new long[]{0L, 1024L});
      FOLLOW_74_in_action646 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_action650 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_action652 = new BitSet(new long[]{2L});
      FOLLOW_id_in_actionScopeName678 = new BitSet(new long[]{2L});
      FOLLOW_83_in_actionScopeName685 = new BitSet(new long[]{2L});
      FOLLOW_84_in_actionScopeName702 = new BitSet(new long[]{2L});
      FOLLOW_OPTIONS_in_optionsSpec718 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_option_in_optionsSpec721 = new BitSet(new long[]{0L, 2048L});
      FOLLOW_75_in_optionsSpec723 = new BitSet(new long[]{577023702256844800L, 268435456L});
      FOLLOW_92_in_optionsSpec727 = new BitSet(new long[]{2L});
      FOLLOW_id_in_option752 = new BitSet(new long[]{0L, 4096L});
      FOLLOW_76_in_option754 = new BitSet(new long[]{595038102913843200L, 16L});
      FOLLOW_optionValue_in_option756 = new BitSet(new long[]{2L});
      FOLLOW_id_in_optionValue785 = new BitSet(new long[]{2L});
      FOLLOW_STRING_LITERAL_in_optionValue795 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_optionValue805 = new BitSet(new long[]{2L});
      FOLLOW_INT_in_optionValue815 = new BitSet(new long[]{2L});
      FOLLOW_68_in_optionValue825 = new BitSet(new long[]{2L});
      FOLLOW_DOC_COMMENT_in_rule854 = new BitSet(new long[]{577023702391062528L, 14680064L});
      FOLLOW_86_in_rule864 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_87_in_rule866 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_85_in_rule868 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_FRAGMENT_in_rule870 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_rule878 = new BitSet(new long[]{1126999418480640L, 50364928L});
      FOLLOW_BANG_in_rule884 = new BitSet(new long[]{1126999418472448L, 50364928L});
      FOLLOW_ARG_ACTION_in_rule893 = new BitSet(new long[]{1126999418470400L, 50364928L});
      FOLLOW_88_in_rule902 = new BitSet(new long[]{2048L});
      FOLLOW_ARG_ACTION_in_rule906 = new BitSet(new long[]{1126999418470400L, 33587712L});
      FOLLOW_throwsSpec_in_rule914 = new BitSet(new long[]{1126999418470400L, 33280L});
      FOLLOW_optionsSpec_in_rule917 = new BitSet(new long[]{1125899906842624L, 33280L});
      FOLLOW_ruleScopeSpec_in_rule920 = new BitSet(new long[]{0L, 33280L});
      FOLLOW_ruleAction_in_rule923 = new BitSet(new long[]{0L, 33280L});
      FOLLOW_73_in_rule928 = new BitSet(new long[]{1750281773931069456L, 671088900L});
      FOLLOW_altList_in_rule930 = new BitSet(new long[]{0L, 2048L});
      FOLLOW_75_in_rule932 = new BitSet(new long[]{2L, 196608L});
      FOLLOW_exceptionGroup_in_rule936 = new BitSet(new long[]{2L});
      FOLLOW_79_in_ruleAction1038 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_ruleAction1040 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_ruleAction1042 = new BitSet(new long[]{2L});
      FOLLOW_89_in_throwsSpec1063 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_throwsSpec1065 = new BitSet(new long[]{2L, 128L});
      FOLLOW_71_in_throwsSpec1069 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_throwsSpec1071 = new BitSet(new long[]{2L, 128L});
      FOLLOW_SCOPE_in_ruleScopeSpec1094 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_ruleScopeSpec1096 = new BitSet(new long[]{2L});
      FOLLOW_SCOPE_in_ruleScopeSpec1109 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_ruleScopeSpec1111 = new BitSet(new long[]{0L, 2176L});
      FOLLOW_71_in_ruleScopeSpec1114 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_ruleScopeSpec1116 = new BitSet(new long[]{0L, 2176L});
      FOLLOW_75_in_ruleScopeSpec1120 = new BitSet(new long[]{2L});
      FOLLOW_SCOPE_in_ruleScopeSpec1134 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_ruleScopeSpec1136 = new BitSet(new long[]{1125899906842624L});
      FOLLOW_SCOPE_in_ruleScopeSpec1140 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_ruleScopeSpec1142 = new BitSet(new long[]{0L, 2176L});
      FOLLOW_71_in_ruleScopeSpec1145 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_ruleScopeSpec1147 = new BitSet(new long[]{0L, 2176L});
      FOLLOW_75_in_ruleScopeSpec1151 = new BitSet(new long[]{2L});
      FOLLOW_66_in_block1183 = new BitSet(new long[]{1750282873442697232L, 671089420L});
      FOLLOW_optionsSpec_in_block1192 = new BitSet(new long[]{0L, 512L});
      FOLLOW_73_in_block1196 = new BitSet(new long[]{1750281773931069456L, 671088908L});
      FOLLOW_alternative_in_block1205 = new BitSet(new long[]{70368744177664L, 134217736L});
      FOLLOW_rewrite_in_block1207 = new BitSet(new long[]{0L, 134217736L});
      FOLLOW_91_in_block1211 = new BitSet(new long[]{1750281773931069456L, 671088908L});
      FOLLOW_alternative_in_block1215 = new BitSet(new long[]{70368744177664L, 134217736L});
      FOLLOW_rewrite_in_block1217 = new BitSet(new long[]{0L, 134217736L});
      FOLLOW_67_in_block1232 = new BitSet(new long[]{2L});
      FOLLOW_alternative_in_altList1289 = new BitSet(new long[]{70368744177664L, 134217728L});
      FOLLOW_rewrite_in_altList1291 = new BitSet(new long[]{2L, 134217728L});
      FOLLOW_91_in_altList1295 = new BitSet(new long[]{1750281773931069456L, 671088900L});
      FOLLOW_alternative_in_altList1299 = new BitSet(new long[]{70368744177664L, 134217728L});
      FOLLOW_rewrite_in_altList1301 = new BitSet(new long[]{2L, 134217728L});
      FOLLOW_element_in_alternative1349 = new BitSet(new long[]{1750211405186891794L, 536871172L});
      FOLLOW_exceptionHandler_in_exceptionGroup1400 = new BitSet(new long[]{2L, 196608L});
      FOLLOW_finallyClause_in_exceptionGroup1407 = new BitSet(new long[]{2L});
      FOLLOW_finallyClause_in_exceptionGroup1415 = new BitSet(new long[]{2L});
      FOLLOW_80_in_exceptionHandler1435 = new BitSet(new long[]{2048L});
      FOLLOW_ARG_ACTION_in_exceptionHandler1437 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_exceptionHandler1439 = new BitSet(new long[]{2L});
      FOLLOW_81_in_finallyClause1469 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_finallyClause1471 = new BitSet(new long[]{2L});
      FOLLOW_elementNoOptionSpec_in_element1493 = new BitSet(new long[]{2L});
      FOLLOW_id_in_elementNoOptionSpec1504 = new BitSet(new long[]{0L, 4160L});
      FOLLOW_76_in_elementNoOptionSpec1509 = new BitSet(new long[]{595038100766359552L, 536871168L});
      FOLLOW_70_in_elementNoOptionSpec1513 = new BitSet(new long[]{595038100766359552L, 536871168L});
      FOLLOW_atom_in_elementNoOptionSpec1516 = new BitSet(new long[]{2L, 16432L});
      FOLLOW_ebnfSuffix_in_elementNoOptionSpec1522 = new BitSet(new long[]{2L});
      FOLLOW_id_in_elementNoOptionSpec1581 = new BitSet(new long[]{0L, 4160L});
      FOLLOW_76_in_elementNoOptionSpec1586 = new BitSet(new long[]{0L, 4L});
      FOLLOW_70_in_elementNoOptionSpec1590 = new BitSet(new long[]{0L, 4L});
      FOLLOW_block_in_elementNoOptionSpec1593 = new BitSet(new long[]{2L, 16432L});
      FOLLOW_ebnfSuffix_in_elementNoOptionSpec1599 = new BitSet(new long[]{2L});
      FOLLOW_atom_in_elementNoOptionSpec1658 = new BitSet(new long[]{2L, 16432L});
      FOLLOW_ebnfSuffix_in_elementNoOptionSpec1664 = new BitSet(new long[]{2L});
      FOLLOW_ebnf_in_elementNoOptionSpec1710 = new BitSet(new long[]{2L});
      FOLLOW_ACTION_in_elementNoOptionSpec1717 = new BitSet(new long[]{2L});
      FOLLOW_SEMPRED_in_elementNoOptionSpec1724 = new BitSet(new long[]{2L, 8192L});
      FOLLOW_77_in_elementNoOptionSpec1728 = new BitSet(new long[]{2L});
      FOLLOW_treeSpec_in_elementNoOptionSpec1747 = new BitSet(new long[]{2L, 16432L});
      FOLLOW_ebnfSuffix_in_elementNoOptionSpec1753 = new BitSet(new long[]{2L});
      FOLLOW_range_in_atom1805 = new BitSet(new long[]{140737488363522L});
      FOLLOW_ROOT_in_atom1812 = new BitSet(new long[]{2L});
      FOLLOW_BANG_in_atom1816 = new BitSet(new long[]{2L});
      FOLLOW_terminal_in_atom1844 = new BitSet(new long[]{2L});
      FOLLOW_notSet_in_atom1852 = new BitSet(new long[]{140737488363522L});
      FOLLOW_ROOT_in_atom1859 = new BitSet(new long[]{2L});
      FOLLOW_BANG_in_atom1863 = new BitSet(new long[]{2L});
      FOLLOW_RULE_REF_in_atom1891 = new BitSet(new long[]{140737488365570L});
      FOLLOW_ARG_ACTION_in_atom1897 = new BitSet(new long[]{140737488363522L});
      FOLLOW_ROOT_in_atom1907 = new BitSet(new long[]{2L});
      FOLLOW_BANG_in_atom1911 = new BitSet(new long[]{2L});
      FOLLOW_93_in_notSet1994 = new BitSet(new long[]{594475150812938240L, 4L});
      FOLLOW_notTerminal_in_notSet2000 = new BitSet(new long[]{2L});
      FOLLOW_block_in_notSet2014 = new BitSet(new long[]{2L});
      FOLLOW_TREE_BEGIN_in_treeSpec2038 = new BitSet(new long[]{1750211405186891792L, 536871172L});
      FOLLOW_element_in_treeSpec2040 = new BitSet(new long[]{1750211405186891792L, 536871172L});
      FOLLOW_element_in_treeSpec2044 = new BitSet(new long[]{1750211405186891792L, 536871180L});
      FOLLOW_67_in_treeSpec2049 = new BitSet(new long[]{2L});
      FOLLOW_block_in_ebnf2081 = new BitSet(new long[]{2L, 24624L});
      FOLLOW_78_in_ebnf2089 = new BitSet(new long[]{2L});
      FOLLOW_68_in_ebnf2106 = new BitSet(new long[]{2L});
      FOLLOW_69_in_ebnf2123 = new BitSet(new long[]{2L});
      FOLLOW_77_in_ebnf2140 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_range2223 = new BitSet(new long[]{17592186044416L});
      FOLLOW_RANGE_in_range2225 = new BitSet(new long[]{32768L});
      FOLLOW_CHAR_LITERAL_in_range2229 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_terminal2260 = new BitSet(new long[]{140737488363522L});
      FOLLOW_TOKEN_REF_in_terminal2282 = new BitSet(new long[]{140737488365570L});
      FOLLOW_ARG_ACTION_in_terminal2289 = new BitSet(new long[]{140737488363522L});
      FOLLOW_STRING_LITERAL_in_terminal2328 = new BitSet(new long[]{140737488363522L});
      FOLLOW_72_in_terminal2343 = new BitSet(new long[]{140737488363522L});
      FOLLOW_ROOT_in_terminal2364 = new BitSet(new long[]{2L});
      FOLLOW_BANG_in_terminal2385 = new BitSet(new long[]{2L});
      FOLLOW_78_in_ebnfSuffix2445 = new BitSet(new long[]{2L});
      FOLLOW_68_in_ebnfSuffix2457 = new BitSet(new long[]{2L});
      FOLLOW_69_in_ebnfSuffix2470 = new BitSet(new long[]{2L});
      FOLLOW_REWRITE_in_rewrite2499 = new BitSet(new long[]{2251799813685248L});
      FOLLOW_SEMPRED_in_rewrite2503 = new BitSet(new long[]{1748029974117384208L, 6L});
      FOLLOW_rewrite_alternative_in_rewrite2507 = new BitSet(new long[]{70368744177664L});
      FOLLOW_REWRITE_in_rewrite2515 = new BitSet(new long[]{1747959605373206544L, 6L});
      FOLLOW_rewrite_alternative_in_rewrite2519 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_template_in_rewrite_alternative2570 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_alternative_in_rewrite_alternative2575 = new BitSet(new long[]{2L});
      FOLLOW_66_in_rewrite_tree_block2617 = new BitSet(new long[]{1747959605373206544L, 6L});
      FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block2619 = new BitSet(new long[]{0L, 8L});
      FOLLOW_67_in_rewrite_tree_block2621 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative2655 = new BitSet(new long[]{1747959605373206546L, 6L});
      FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2683 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2688 = new BitSet(new long[]{0L, 16432L});
      FOLLOW_ebnfSuffix_in_rewrite_tree_element2690 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_in_rewrite_tree_element2724 = new BitSet(new long[]{2L, 16432L});
      FOLLOW_ebnfSuffix_in_rewrite_tree_element2730 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element2776 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom2792 = new BitSet(new long[]{2L});
      FOLLOW_TOKEN_REF_in_rewrite_tree_atom2799 = new BitSet(new long[]{2050L});
      FOLLOW_ARG_ACTION_in_rewrite_tree_atom2801 = new BitSet(new long[]{2L});
      FOLLOW_RULE_REF_in_rewrite_tree_atom2822 = new BitSet(new long[]{2L});
      FOLLOW_STRING_LITERAL_in_rewrite_tree_atom2829 = new BitSet(new long[]{2L});
      FOLLOW_65_in_rewrite_tree_atom2838 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_id_in_rewrite_tree_atom2840 = new BitSet(new long[]{2L});
      FOLLOW_ACTION_in_rewrite_tree_atom2851 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf2872 = new BitSet(new long[]{0L, 16432L});
      FOLLOW_ebnfSuffix_in_rewrite_tree_ebnf2874 = new BitSet(new long[]{2L});
      FOLLOW_TREE_BEGIN_in_rewrite_tree2894 = new BitSet(new long[]{595038100766359568L, 2L});
      FOLLOW_rewrite_tree_atom_in_rewrite_tree2896 = new BitSet(new long[]{1747959605373206544L, 14L});
      FOLLOW_rewrite_tree_element_in_rewrite_tree2898 = new BitSet(new long[]{1747959605373206544L, 14L});
      FOLLOW_67_in_rewrite_tree2901 = new BitSet(new long[]{2L});
      FOLLOW_id_in_rewrite_template2933 = new BitSet(new long[]{0L, 4L});
      FOLLOW_66_in_rewrite_template2937 = new BitSet(new long[]{577023702256844800L, 8L});
      FOLLOW_rewrite_template_args_in_rewrite_template2939 = new BitSet(new long[]{0L, 8L});
      FOLLOW_67_in_rewrite_template2941 = new BitSet(new long[]{3145728L});
      FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template2949 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template2955 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_template_ref_in_rewrite_template2982 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_indirect_template_head_in_rewrite_template2991 = new BitSet(new long[]{2L});
      FOLLOW_ACTION_in_rewrite_template3000 = new BitSet(new long[]{2L});
      FOLLOW_id_in_rewrite_template_ref3013 = new BitSet(new long[]{0L, 4L});
      FOLLOW_66_in_rewrite_template_ref3017 = new BitSet(new long[]{577023702256844800L, 8L});
      FOLLOW_rewrite_template_args_in_rewrite_template_ref3019 = new BitSet(new long[]{0L, 8L});
      FOLLOW_67_in_rewrite_template_ref3021 = new BitSet(new long[]{2L});
      FOLLOW_66_in_rewrite_indirect_template_head3049 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_rewrite_indirect_template_head3051 = new BitSet(new long[]{0L, 8L});
      FOLLOW_67_in_rewrite_indirect_template_head3053 = new BitSet(new long[]{0L, 4L});
      FOLLOW_66_in_rewrite_indirect_template_head3055 = new BitSet(new long[]{577023702256844800L, 8L});
      FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3057 = new BitSet(new long[]{0L, 8L});
      FOLLOW_67_in_rewrite_indirect_template_head3059 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_template_arg_in_rewrite_template_args3083 = new BitSet(new long[]{2L, 128L});
      FOLLOW_71_in_rewrite_template_args3086 = new BitSet(new long[]{577023702256844800L});
      FOLLOW_rewrite_template_arg_in_rewrite_template_args3088 = new BitSet(new long[]{2L, 128L});
      FOLLOW_id_in_rewrite_template_arg3121 = new BitSet(new long[]{0L, 4096L});
      FOLLOW_76_in_rewrite_template_arg3123 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_rewrite_template_arg3125 = new BitSet(new long[]{2L});
      FOLLOW_TOKEN_REF_in_id3146 = new BitSet(new long[]{2L});
      FOLLOW_RULE_REF_in_id3156 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_template_in_synpred1_ANTLRv32570 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_alternative_in_synpred2_ANTLRv32575 = new BitSet(new long[]{2L});
   }

   protected class DFA72 extends DFA {
      public DFA72(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 72;
         this.eot = ANTLRv3Parser.DFA72_eot;
         this.eof = ANTLRv3Parser.DFA72_eof;
         this.min = ANTLRv3Parser.DFA72_min;
         this.max = ANTLRv3Parser.DFA72_max;
         this.accept = ANTLRv3Parser.DFA72_accept;
         this.special = ANTLRv3Parser.DFA72_special;
         this.transition = ANTLRv3Parser.DFA72_transition;
      }

      public String getDescription() {
         return "420:1: rewrite_template : ( id lp= '(' rewrite_template_args ')' (str= DOUBLE_QUOTE_STRING_LITERAL |str= DOUBLE_ANGLE_STRING_LITERAL ) -> ^( TEMPLATE[$lp,\"TEMPLATE\"] id rewrite_template_args $str) | rewrite_template_ref | rewrite_indirect_template_head | ACTION );";
      }
   }

   public static class id_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_template_arg_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_template_args_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_indirect_template_head_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_template_ref_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_template_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_tree_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_tree_ebnf_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_tree_atom_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_tree_element_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_tree_alternative_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_tree_block_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_alternative_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rewrite_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class ebnfSuffix_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class notTerminal_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class terminal_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class range_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class ebnf_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class treeSpec_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class notSet_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class atom_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class elementNoOptionSpec_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class element_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class finallyClause_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class exceptionHandler_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class exceptionGroup_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class alternative_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class altList_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class block_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class ruleScopeSpec_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class throwsSpec_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class ruleAction_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class rule_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   protected static class rule_scope {
      String name;
   }

   public static class optionValue_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class option_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class optionsSpec_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class actionScopeName_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class action_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class attrScope_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class tokenSpec_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class tokensSpec_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class grammarDef_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }
}
