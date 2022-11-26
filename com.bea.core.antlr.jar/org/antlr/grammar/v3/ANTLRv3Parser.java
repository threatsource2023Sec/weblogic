package org.antlr.grammar.v3;

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
   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ACTION", "ACTION_CHAR_LITERAL", "ACTION_ESC", "ACTION_STRING_LITERAL", "ALT", "ARG", "ARGLIST", "ARG_ACTION", "AT", "BACKTRACK_SEMPRED", "BANG", "BLOCK", "CHAR_LITERAL", "CHAR_RANGE", "CLOSURE", "COMBINED_GRAMMAR", "DOC_COMMENT", "DOUBLE_ANGLE_STRING_LITERAL", "DOUBLE_QUOTE_STRING_LITERAL", "EOA", "EOB", "EOR", "EPSILON", "ESC", "FRAGMENT", "GATED_SEMPRED", "ID", "INT", "LABEL", "LABEL_ASSIGN", "LEXER", "LEXER_GRAMMAR", "LIST_LABEL_ASSIGN", "LITERAL_CHAR", "ML_COMMENT", "NESTED_ACTION", "NESTED_ARG_ACTION", "OPTIONAL", "OPTIONS", "PARSER", "PARSER_GRAMMAR", "POSITIVE_CLOSURE", "RANGE", "RET", "REWRITE", "ROOT", "RULE", "RULE_REF", "SCOPE", "SEMPRED", "SL_COMMENT", "SRC", "STRING_LITERAL", "SYNPRED", "SYN_SEMPRED", "TEMPLATE", "TOKENS", "TOKEN_REF", "TREE_BEGIN", "TREE_GRAMMAR", "WS", "WS_LOOP", "XDIGIT", "'$'", "'('", "')'", "'*'", "'+'", "','", "'.'", "':'", "'::'", "';'", "'<'", "'=>'", "'>'", "'?'", "'catch'", "'finally'", "'grammar'", "'lexer'", "'parser'", "'private'", "'protected'", "'public'", "'throws'", "'tree'", "'|'", "'}'", "'~'"};
   public static final int EOF = -1;
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
   public static final int AT = 12;
   public static final int BACKTRACK_SEMPRED = 13;
   public static final int BANG = 14;
   public static final int BLOCK = 15;
   public static final int CHAR_LITERAL = 16;
   public static final int CHAR_RANGE = 17;
   public static final int CLOSURE = 18;
   public static final int COMBINED_GRAMMAR = 19;
   public static final int DOC_COMMENT = 20;
   public static final int DOUBLE_ANGLE_STRING_LITERAL = 21;
   public static final int DOUBLE_QUOTE_STRING_LITERAL = 22;
   public static final int EOA = 23;
   public static final int EOB = 24;
   public static final int EOR = 25;
   public static final int EPSILON = 26;
   public static final int ESC = 27;
   public static final int FRAGMENT = 28;
   public static final int GATED_SEMPRED = 29;
   public static final int ID = 30;
   public static final int INT = 31;
   public static final int LABEL = 32;
   public static final int LABEL_ASSIGN = 33;
   public static final int LEXER = 34;
   public static final int LEXER_GRAMMAR = 35;
   public static final int LIST_LABEL_ASSIGN = 36;
   public static final int LITERAL_CHAR = 37;
   public static final int ML_COMMENT = 38;
   public static final int NESTED_ACTION = 39;
   public static final int NESTED_ARG_ACTION = 40;
   public static final int OPTIONAL = 41;
   public static final int OPTIONS = 42;
   public static final int PARSER = 43;
   public static final int PARSER_GRAMMAR = 44;
   public static final int POSITIVE_CLOSURE = 45;
   public static final int RANGE = 46;
   public static final int RET = 47;
   public static final int REWRITE = 48;
   public static final int ROOT = 49;
   public static final int RULE = 50;
   public static final int RULE_REF = 51;
   public static final int SCOPE = 52;
   public static final int SEMPRED = 53;
   public static final int SL_COMMENT = 54;
   public static final int SRC = 55;
   public static final int STRING_LITERAL = 56;
   public static final int SYNPRED = 57;
   public static final int SYN_SEMPRED = 58;
   public static final int TEMPLATE = 59;
   public static final int TOKENS = 60;
   public static final int TOKEN_REF = 61;
   public static final int TREE_BEGIN = 62;
   public static final int TREE_GRAMMAR = 63;
   public static final int WS = 64;
   public static final int WS_LOOP = 65;
   public static final int XDIGIT = 66;
   protected TreeAdaptor adaptor;
   int gtype;
   protected Stack rule_stack;
   protected DFA81 dfa81;
   static final String DFA81_eotS = "\u0012\uffff";
   static final String DFA81_eofS = "\b\uffff\u0001\u000b\t\uffff";
   static final String DFA81_minS = "\u0001\u0004\u0002D\u0002\uffff\u00013\u0002!\u0001\u0015\u0001\u0004\u0002\uffff\u0001E\u00013\u0002!\u0001\u0004\u0001E";
   static final String DFA81_maxS = "\u0003D\u0002\uffff\u0001E\u0002!\u0001[\u0001\u0004\u0002\uffff\u0001H\u0001=\u0002!\u0001\u0004\u0001H";
   static final String DFA81_acceptS = "\u0003\uffff\u0001\u0003\u0001\u0004\u0005\uffff\u0001\u0001\u0001\u0002\u0006\uffff";
   static final String DFA81_specialS = "\u0012\uffff}>";
   static final String[] DFA81_transitionS = new String[]{"\u0001\u0004.\uffff\u0001\u0002\t\uffff\u0001\u0001\u0006\uffff\u0001\u0003", "\u0001\u0005", "\u0001\u0005", "", "", "\u0001\u0007\t\uffff\u0001\u0006\u0007\uffff\u0001\b", "\u0001\t", "\u0001\t", "\u0002\n\u0019\uffff\u0001\u000b\u0014\uffff\u0001\u000b\u0006\uffff\u0001\u000b\u000e\uffff\u0001\u000b", "\u0001\f", "", "", "\u0001\b\u0002\uffff\u0001\r", "\u0001\u000f\t\uffff\u0001\u000e", "\u0001\u0010", "\u0001\u0010", "\u0001\u0011", "\u0001\b\u0002\uffff\u0001\r"};
   static final short[] DFA81_eot = DFA.unpackEncodedString("\u0012\uffff");
   static final short[] DFA81_eof = DFA.unpackEncodedString("\b\uffff\u0001\u000b\t\uffff");
   static final char[] DFA81_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0004\u0002D\u0002\uffff\u00013\u0002!\u0001\u0015\u0001\u0004\u0002\uffff\u0001E\u00013\u0002!\u0001\u0004\u0001E");
   static final char[] DFA81_max = DFA.unpackEncodedStringToUnsignedChars("\u0003D\u0002\uffff\u0001E\u0002!\u0001[\u0001\u0004\u0002\uffff\u0001H\u0001=\u0002!\u0001\u0004\u0001H");
   static final short[] DFA81_accept = DFA.unpackEncodedString("\u0003\uffff\u0001\u0003\u0001\u0004\u0005\uffff\u0001\u0001\u0001\u0002\u0006\uffff");
   static final short[] DFA81_special = DFA.unpackEncodedString("\u0012\uffff}>");
   static final short[][] DFA81_transition;
   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarDef373;
   public static final BitSet FOLLOW_84_in_grammarDef383;
   public static final BitSet FOLLOW_85_in_grammarDef401;
   public static final BitSet FOLLOW_90_in_grammarDef417;
   public static final BitSet FOLLOW_83_in_grammarDef458;
   public static final BitSet FOLLOW_id_in_grammarDef460;
   public static final BitSet FOLLOW_76_in_grammarDef462;
   public static final BitSet FOLLOW_optionsSpec_in_grammarDef464;
   public static final BitSet FOLLOW_tokensSpec_in_grammarDef467;
   public static final BitSet FOLLOW_attrScope_in_grammarDef470;
   public static final BitSet FOLLOW_action_in_grammarDef473;
   public static final BitSet FOLLOW_rule_in_grammarDef481;
   public static final BitSet FOLLOW_EOF_in_grammarDef489;
   public static final BitSet FOLLOW_TOKENS_in_tokensSpec550;
   public static final BitSet FOLLOW_tokenSpec_in_tokensSpec552;
   public static final BitSet FOLLOW_92_in_tokensSpec555;
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec575;
   public static final BitSet FOLLOW_LABEL_ASSIGN_in_tokenSpec581;
   public static final BitSet FOLLOW_STRING_LITERAL_in_tokenSpec586;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_tokenSpec590;
   public static final BitSet FOLLOW_76_in_tokenSpec629;
   public static final BitSet FOLLOW_SCOPE_in_attrScope640;
   public static final BitSet FOLLOW_id_in_attrScope642;
   public static final BitSet FOLLOW_ACTION_in_attrScope644;
   public static final BitSet FOLLOW_AT_in_action667;
   public static final BitSet FOLLOW_actionScopeName_in_action670;
   public static final BitSet FOLLOW_75_in_action672;
   public static final BitSet FOLLOW_id_in_action676;
   public static final BitSet FOLLOW_ACTION_in_action678;
   public static final BitSet FOLLOW_id_in_actionScopeName704;
   public static final BitSet FOLLOW_84_in_actionScopeName711;
   public static final BitSet FOLLOW_85_in_actionScopeName728;
   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec744;
   public static final BitSet FOLLOW_option_in_optionsSpec747;
   public static final BitSet FOLLOW_76_in_optionsSpec749;
   public static final BitSet FOLLOW_92_in_optionsSpec753;
   public static final BitSet FOLLOW_id_in_option778;
   public static final BitSet FOLLOW_LABEL_ASSIGN_in_option780;
   public static final BitSet FOLLOW_optionValue_in_option782;
   public static final BitSet FOLLOW_qid_in_optionValue811;
   public static final BitSet FOLLOW_STRING_LITERAL_in_optionValue821;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_optionValue831;
   public static final BitSet FOLLOW_INT_in_optionValue841;
   public static final BitSet FOLLOW_70_in_optionValue851;
   public static final BitSet FOLLOW_DOC_COMMENT_in_rule876;
   public static final BitSet FOLLOW_87_in_rule886;
   public static final BitSet FOLLOW_88_in_rule888;
   public static final BitSet FOLLOW_86_in_rule890;
   public static final BitSet FOLLOW_FRAGMENT_in_rule892;
   public static final BitSet FOLLOW_id_in_rule900;
   public static final BitSet FOLLOW_BANG_in_rule906;
   public static final BitSet FOLLOW_ARG_ACTION_in_rule915;
   public static final BitSet FOLLOW_RET_in_rule924;
   public static final BitSet FOLLOW_ARG_ACTION_in_rule928;
   public static final BitSet FOLLOW_throwsSpec_in_rule936;
   public static final BitSet FOLLOW_optionsSpec_in_rule939;
   public static final BitSet FOLLOW_ruleScopeSpec_in_rule942;
   public static final BitSet FOLLOW_ruleAction_in_rule945;
   public static final BitSet FOLLOW_74_in_rule950;
   public static final BitSet FOLLOW_altList_in_rule952;
   public static final BitSet FOLLOW_76_in_rule954;
   public static final BitSet FOLLOW_exceptionGroup_in_rule958;
   public static final BitSet FOLLOW_AT_in_ruleAction1064;
   public static final BitSet FOLLOW_id_in_ruleAction1066;
   public static final BitSet FOLLOW_ACTION_in_ruleAction1068;
   public static final BitSet FOLLOW_89_in_throwsSpec1089;
   public static final BitSet FOLLOW_id_in_throwsSpec1091;
   public static final BitSet FOLLOW_72_in_throwsSpec1095;
   public static final BitSet FOLLOW_id_in_throwsSpec1097;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1120;
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec1122;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1135;
   public static final BitSet FOLLOW_id_in_ruleScopeSpec1137;
   public static final BitSet FOLLOW_72_in_ruleScopeSpec1140;
   public static final BitSet FOLLOW_id_in_ruleScopeSpec1142;
   public static final BitSet FOLLOW_76_in_ruleScopeSpec1146;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1160;
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec1162;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1166;
   public static final BitSet FOLLOW_id_in_ruleScopeSpec1168;
   public static final BitSet FOLLOW_72_in_ruleScopeSpec1171;
   public static final BitSet FOLLOW_id_in_ruleScopeSpec1173;
   public static final BitSet FOLLOW_76_in_ruleScopeSpec1177;
   public static final BitSet FOLLOW_68_in_block1209;
   public static final BitSet FOLLOW_optionsSpec_in_block1218;
   public static final BitSet FOLLOW_74_in_block1222;
   public static final BitSet FOLLOW_altpair_in_block1229;
   public static final BitSet FOLLOW_91_in_block1233;
   public static final BitSet FOLLOW_altpair_in_block1235;
   public static final BitSet FOLLOW_69_in_block1250;
   public static final BitSet FOLLOW_alternative_in_altpair1289;
   public static final BitSet FOLLOW_rewrite_in_altpair1291;
   public static final BitSet FOLLOW_altpair_in_altList1311;
   public static final BitSet FOLLOW_91_in_altList1315;
   public static final BitSet FOLLOW_altpair_in_altList1317;
   public static final BitSet FOLLOW_element_in_alternative1358;
   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup1409;
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1416;
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1424;
   public static final BitSet FOLLOW_81_in_exceptionHandler1444;
   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler1446;
   public static final BitSet FOLLOW_ACTION_in_exceptionHandler1448;
   public static final BitSet FOLLOW_82_in_finallyClause1478;
   public static final BitSet FOLLOW_ACTION_in_finallyClause1480;
   public static final BitSet FOLLOW_id_in_element1502;
   public static final BitSet FOLLOW_LABEL_ASSIGN_in_element1507;
   public static final BitSet FOLLOW_LIST_LABEL_ASSIGN_in_element1511;
   public static final BitSet FOLLOW_atom_in_element1514;
   public static final BitSet FOLLOW_ebnfSuffix_in_element1520;
   public static final BitSet FOLLOW_id_in_element1579;
   public static final BitSet FOLLOW_LABEL_ASSIGN_in_element1584;
   public static final BitSet FOLLOW_LIST_LABEL_ASSIGN_in_element1588;
   public static final BitSet FOLLOW_block_in_element1591;
   public static final BitSet FOLLOW_ebnfSuffix_in_element1597;
   public static final BitSet FOLLOW_atom_in_element1656;
   public static final BitSet FOLLOW_ebnfSuffix_in_element1662;
   public static final BitSet FOLLOW_ebnf_in_element1708;
   public static final BitSet FOLLOW_ACTION_in_element1715;
   public static final BitSet FOLLOW_SEMPRED_in_element1722;
   public static final BitSet FOLLOW_78_in_element1728;
   public static final BitSet FOLLOW_treeSpec_in_element1748;
   public static final BitSet FOLLOW_ebnfSuffix_in_element1754;
   public static final BitSet FOLLOW_terminal_in_atom1806;
   public static final BitSet FOLLOW_range_in_atom1811;
   public static final BitSet FOLLOW_ROOT_in_atom1821;
   public static final BitSet FOLLOW_BANG_in_atom1825;
   public static final BitSet FOLLOW_notSet_in_atom1859;
   public static final BitSet FOLLOW_ROOT_in_atom1868;
   public static final BitSet FOLLOW_BANG_in_atom1872;
   public static final BitSet FOLLOW_RULE_REF_in_atom1908;
   public static final BitSet FOLLOW_ARG_ACTION_in_atom1910;
   public static final BitSet FOLLOW_ROOT_in_atom1920;
   public static final BitSet FOLLOW_BANG_in_atom1924;
   public static final BitSet FOLLOW_93_in_notSet1972;
   public static final BitSet FOLLOW_notTerminal_in_notSet1978;
   public static final BitSet FOLLOW_elementOptions_in_notSet1980;
   public static final BitSet FOLLOW_block_in_notSet1998;
   public static final BitSet FOLLOW_elementOptions_in_notSet2000;
   public static final BitSet FOLLOW_77_in_elementOptions2052;
   public static final BitSet FOLLOW_qid_in_elementOptions2054;
   public static final BitSet FOLLOW_79_in_elementOptions2056;
   public static final BitSet FOLLOW_77_in_elementOptions2074;
   public static final BitSet FOLLOW_option_in_elementOptions2076;
   public static final BitSet FOLLOW_76_in_elementOptions2079;
   public static final BitSet FOLLOW_option_in_elementOptions2081;
   public static final BitSet FOLLOW_79_in_elementOptions2085;
   public static final BitSet FOLLOW_id_in_elementOption2105;
   public static final BitSet FOLLOW_LABEL_ASSIGN_in_elementOption2107;
   public static final BitSet FOLLOW_optionValue_in_elementOption2109;
   public static final BitSet FOLLOW_TREE_BEGIN_in_treeSpec2131;
   public static final BitSet FOLLOW_element_in_treeSpec2133;
   public static final BitSet FOLLOW_element_in_treeSpec2137;
   public static final BitSet FOLLOW_69_in_treeSpec2142;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2165;
   public static final BitSet FOLLOW_RANGE_in_range2167;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2171;
   public static final BitSet FOLLOW_elementOptions_in_range2173;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_terminal2210;
   public static final BitSet FOLLOW_elementOptions_in_terminal2212;
   public static final BitSet FOLLOW_TOKEN_REF_in_terminal2243;
   public static final BitSet FOLLOW_ARG_ACTION_in_terminal2245;
   public static final BitSet FOLLOW_elementOptions_in_terminal2248;
   public static final BitSet FOLLOW_STRING_LITERAL_in_terminal2269;
   public static final BitSet FOLLOW_elementOptions_in_terminal2271;
   public static final BitSet FOLLOW_73_in_terminal2292;
   public static final BitSet FOLLOW_elementOptions_in_terminal2294;
   public static final BitSet FOLLOW_ROOT_in_terminal2321;
   public static final BitSet FOLLOW_BANG_in_terminal2342;
   public static final BitSet FOLLOW_block_in_ebnf2385;
   public static final BitSet FOLLOW_80_in_ebnf2393;
   public static final BitSet FOLLOW_70_in_ebnf2410;
   public static final BitSet FOLLOW_71_in_ebnf2427;
   public static final BitSet FOLLOW_78_in_ebnf2444;
   public static final BitSet FOLLOW_80_in_ebnfSuffix2529;
   public static final BitSet FOLLOW_70_in_ebnfSuffix2541;
   public static final BitSet FOLLOW_71_in_ebnfSuffix2554;
   public static final BitSet FOLLOW_REWRITE_in_rewrite2583;
   public static final BitSet FOLLOW_SEMPRED_in_rewrite2587;
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite2591;
   public static final BitSet FOLLOW_REWRITE_in_rewrite2599;
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite2603;
   public static final BitSet FOLLOW_rewrite_template_in_rewrite_alternative2654;
   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_alternative2659;
   public static final BitSet FOLLOW_68_in_rewrite_tree_block2701;
   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block2703;
   public static final BitSet FOLLOW_69_in_rewrite_tree_block2705;
   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative2739;
   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2767;
   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2772;
   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_element2774;
   public static final BitSet FOLLOW_rewrite_tree_in_rewrite_tree_element2808;
   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_element2814;
   public static final BitSet FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element2860;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom2876;
   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_tree_atom2883;
   public static final BitSet FOLLOW_ARG_ACTION_in_rewrite_tree_atom2885;
   public static final BitSet FOLLOW_RULE_REF_in_rewrite_tree_atom2906;
   public static final BitSet FOLLOW_STRING_LITERAL_in_rewrite_tree_atom2913;
   public static final BitSet FOLLOW_67_in_rewrite_tree_atom2922;
   public static final BitSet FOLLOW_id_in_rewrite_tree_atom2924;
   public static final BitSet FOLLOW_ACTION_in_rewrite_tree_atom2935;
   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf2956;
   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_ebnf2958;
   public static final BitSet FOLLOW_TREE_BEGIN_in_rewrite_tree2978;
   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree2980;
   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree2982;
   public static final BitSet FOLLOW_69_in_rewrite_tree2985;
   public static final BitSet FOLLOW_id_in_rewrite_template3017;
   public static final BitSet FOLLOW_68_in_rewrite_template3021;
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template3023;
   public static final BitSet FOLLOW_69_in_rewrite_template3025;
   public static final BitSet FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template3033;
   public static final BitSet FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template3039;
   public static final BitSet FOLLOW_rewrite_template_ref_in_rewrite_template3066;
   public static final BitSet FOLLOW_rewrite_indirect_template_head_in_rewrite_template3075;
   public static final BitSet FOLLOW_ACTION_in_rewrite_template3084;
   public static final BitSet FOLLOW_id_in_rewrite_template_ref3097;
   public static final BitSet FOLLOW_68_in_rewrite_template_ref3101;
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template_ref3103;
   public static final BitSet FOLLOW_69_in_rewrite_template_ref3105;
   public static final BitSet FOLLOW_68_in_rewrite_indirect_template_head3133;
   public static final BitSet FOLLOW_ACTION_in_rewrite_indirect_template_head3135;
   public static final BitSet FOLLOW_69_in_rewrite_indirect_template_head3137;
   public static final BitSet FOLLOW_68_in_rewrite_indirect_template_head3139;
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3141;
   public static final BitSet FOLLOW_69_in_rewrite_indirect_template_head3143;
   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args3167;
   public static final BitSet FOLLOW_72_in_rewrite_template_args3170;
   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args3172;
   public static final BitSet FOLLOW_id_in_rewrite_template_arg3205;
   public static final BitSet FOLLOW_LABEL_ASSIGN_in_rewrite_template_arg3207;
   public static final BitSet FOLLOW_ACTION_in_rewrite_template_arg3209;
   public static final BitSet FOLLOW_id_in_qid3230;
   public static final BitSet FOLLOW_73_in_qid3233;
   public static final BitSet FOLLOW_id_in_qid3235;
   public static final BitSet FOLLOW_TOKEN_REF_in_id3247;
   public static final BitSet FOLLOW_RULE_REF_in_id3257;
   public static final BitSet FOLLOW_rewrite_template_in_synpred1_ANTLRv32654;
   public static final BitSet FOLLOW_rewrite_tree_alternative_in_synpred2_ANTLRv32659;

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
      this.dfa81 = new DFA81(this);
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
      return "org\\antlr\\grammar\\v3\\ANTLRv3.g";
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
      RewriteRuleTokenStream stream_EOF = new RewriteRuleTokenStream(this.adaptor, "token EOF");
      RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
      RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
      RewriteRuleTokenStream stream_85 = new RewriteRuleTokenStream(this.adaptor, "token 85");
      RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_tokensSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule tokensSpec");
      RewriteRuleSubtreeStream stream_attrScope = new RewriteRuleSubtreeStream(this.adaptor, "rule attrScope");
      RewriteRuleSubtreeStream stream_rule = new RewriteRuleSubtreeStream(this.adaptor, "rule rule");
      RewriteRuleSubtreeStream stream_action = new RewriteRuleSubtreeStream(this.adaptor, "rule action");
      RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");

      try {
         int alt1 = 2;
         int LA1_0 = this.input.LA(1);
         if (LA1_0 == 20) {
            alt1 = 1;
         }

         switch (alt1) {
            case 1:
               DOC_COMMENT1 = (Token)this.match(this.input, 20, FOLLOW_DOC_COMMENT_in_grammarDef373);
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
                  case 83:
                     alt2 = 4;
                     break;
                  case 84:
                     alt2 = 1;
                     break;
                  case 85:
                     alt2 = 2;
                     break;
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
                     string_literal2 = (Token)this.match(this.input, 84, FOLLOW_84_in_grammarDef383);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_84.add(string_literal2);
                     }

                     if (this.state.backtracking == 0) {
                        this.gtype = 35;
                     }
                     break;
                  case 2:
                     string_literal3 = (Token)this.match(this.input, 85, FOLLOW_85_in_grammarDef401);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_85.add(string_literal3);
                     }

                     if (this.state.backtracking == 0) {
                        this.gtype = 44;
                     }
                     break;
                  case 3:
                     string_literal4 = (Token)this.match(this.input, 90, FOLLOW_90_in_grammarDef417);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_90.add(string_literal4);
                     }

                     if (this.state.backtracking == 0) {
                        this.gtype = 63;
                     }
                     break;
                  case 4:
                     if (this.state.backtracking == 0) {
                        this.gtype = 19;
                     }
               }

               g = (Token)this.match(this.input, 83, FOLLOW_83_in_grammarDef458);
               if (this.state.failed) {
                  return retval;
               }

               if (this.state.backtracking == 0) {
                  stream_83.add(g);
               }

               this.pushFollow(FOLLOW_id_in_grammarDef460);
               id5 = this.id();
               --this.state._fsp;
               if (this.state.failed) {
                  return retval;
               }

               if (this.state.backtracking == 0) {
                  stream_id.add(id5.getTree());
               }

               char_literal6 = (Token)this.match(this.input, 76, FOLLOW_76_in_grammarDef462);
               if (this.state.failed) {
                  return retval;
               }

               if (this.state.backtracking == 0) {
                  stream_76.add(char_literal6);
               }

               int alt3 = 2;
               int LA3_0 = this.input.LA(1);
               if (LA3_0 == 42) {
                  alt3 = 1;
               }

               switch (alt3) {
                  case 1:
                     this.pushFollow(FOLLOW_optionsSpec_in_grammarDef464);
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
                     if (LA4_0 == 60) {
                        alt4 = 1;
                     }

                     switch (alt4) {
                        case 1:
                           this.pushFollow(FOLLOW_tokensSpec_in_grammarDef467);
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
            if (LA6_0 == 52) {
               cnt7 = 1;
            }

            switch (cnt7) {
               case 1:
                  this.pushFollow(FOLLOW_attrScope_in_grammarDef470);
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
                     if (LA6_0 == 12) {
                        cnt7 = 1;
                     }

                     switch (cnt7) {
                        case 1:
                           this.pushFollow(FOLLOW_action_in_grammarDef473);
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
                              if (LA7_0 == 20 || LA7_0 == 28 || LA7_0 == 51 || LA7_0 == 61 || LA7_0 >= 86 && LA7_0 <= 88) {
                                 alt7 = 1;
                              }

                              switch (alt7) {
                                 case 1:
                                    this.pushFollow(FOLLOW_rule_in_grammarDef481);
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

                                    EOF12 = (Token)this.match(this.input, -1, FOLLOW_EOF_in_grammarDef489);
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
         TOKENS13 = (Token)this.match(this.input, 60, FOLLOW_TOKENS_in_tokensSpec550);
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
               if (LA8_0 == 61) {
                  alt8 = 1;
               }

               switch (alt8) {
                  case 1:
                     this.pushFollow(FOLLOW_tokenSpec_in_tokensSpec552);
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

                     char_literal15 = (Token)this.match(this.input, 92, FOLLOW_92_in_tokensSpec555);
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
      RewriteRuleTokenStream stream_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LABEL_ASSIGN");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
      RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");

      try {
         try {
            TOKEN_REF16 = (Token)this.match(this.input, 61, FOLLOW_TOKEN_REF_in_tokenSpec575);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_TOKEN_REF.add(TOKEN_REF16);
            }

            int alt10 = true;
            int LA10_0 = this.input.LA(1);
            byte alt10;
            if (LA10_0 == 33) {
               alt10 = 1;
            } else {
               if (LA10_0 != 76) {
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
                  char_literal17 = (Token)this.match(this.input, 33, FOLLOW_LABEL_ASSIGN_in_tokenSpec581);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_LABEL_ASSIGN.add(char_literal17);
                  }

                  int alt9 = true;
                  int LA9_0 = this.input.LA(1);
                  byte alt9;
                  if (LA9_0 == 56) {
                     alt9 = 1;
                  } else {
                     if (LA9_0 != 16) {
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
                        lit = (Token)this.match(this.input, 56, FOLLOW_STRING_LITERAL_in_tokenSpec586);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_STRING_LITERAL.add(lit);
                        }
                        break;
                     case 2:
                        lit = (Token)this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_tokenSpec590);
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
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_LABEL_ASSIGN.nextNode(), root_1);
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

            char_literal18 = (Token)this.match(this.input, 76, FOLLOW_76_in_tokenSpec629);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_76.add(char_literal18);
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
            string_literal19 = (Token)this.match(this.input, 52, FOLLOW_SCOPE_in_attrScope640);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_SCOPE.add(string_literal19);
            }

            this.pushFollow(FOLLOW_id_in_attrScope642);
            id20 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id20.getTree());
            }

            ACTION21 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_attrScope644);
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
      RewriteRuleTokenStream stream_AT = new RewriteRuleTokenStream(this.adaptor, "token AT");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleTokenStream stream_75 = new RewriteRuleTokenStream(this.adaptor, "token 75");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_actionScopeName = new RewriteRuleSubtreeStream(this.adaptor, "rule actionScopeName");

      try {
         try {
            char_literal22 = (Token)this.match(this.input, 12, FOLLOW_AT_in_action667);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_AT.add(char_literal22);
            }

            int alt11 = 2;
            int LA11_1;
            switch (this.input.LA(1)) {
               case 51:
                  LA11_1 = this.input.LA(2);
                  if (LA11_1 == 75) {
                     alt11 = 1;
                  }
                  break;
               case 61:
                  LA11_1 = this.input.LA(2);
                  if (LA11_1 == 75) {
                     alt11 = 1;
                  }
                  break;
               case 84:
               case 85:
                  alt11 = 1;
            }

            switch (alt11) {
               case 1:
                  this.pushFollow(FOLLOW_actionScopeName_in_action670);
                  actionScopeName23 = this.actionScopeName();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_actionScopeName.add(actionScopeName23.getTree());
                  }

                  string_literal24 = (Token)this.match(this.input, 75, FOLLOW_75_in_action672);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_75.add(string_literal24);
                  }
               default:
                  this.pushFollow(FOLLOW_id_in_action676);
                  id25 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(id25.getTree());
                  }

                  ACTION26 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_action678);
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
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_AT.nextNode(), root_1);
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
      RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
      RewriteRuleTokenStream stream_85 = new RewriteRuleTokenStream(this.adaptor, "token 85");

      try {
         try {
            int alt12 = true;
            byte alt12;
            switch (this.input.LA(1)) {
               case 51:
               case 61:
                  alt12 = 1;
                  break;
               case 84:
                  alt12 = 2;
                  break;
               case 85:
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
                  this.pushFollow(FOLLOW_id_in_actionScopeName704);
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
                  l = (Token)this.match(this.input, 84, FOLLOW_84_in_actionScopeName711);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_84.add(l);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(30, (Token)l));
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  p = (Token)this.match(this.input, 85, FOLLOW_85_in_actionScopeName728);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_85.add(p);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(30, (Token)p));
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
      RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");
      RewriteRuleSubtreeStream stream_option = new RewriteRuleSubtreeStream(this.adaptor, "rule option");

      try {
         OPTIONS28 = (Token)this.match(this.input, 42, FOLLOW_OPTIONS_in_optionsSpec744);
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
               if (LA13_0 == 51 || LA13_0 == 61) {
                  alt13 = 1;
               }

               switch (alt13) {
                  case 1:
                     this.pushFollow(FOLLOW_option_in_optionsSpec747);
                     option29 = this.option();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_option.add(option29.getTree());
                     }

                     char_literal30 = (Token)this.match(this.input, 76, FOLLOW_76_in_optionsSpec749);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_76.add(char_literal30);
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

                     char_literal31 = (Token)this.match(this.input, 92, FOLLOW_92_in_optionsSpec753);
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
      RewriteRuleTokenStream stream_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LABEL_ASSIGN");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_optionValue = new RewriteRuleSubtreeStream(this.adaptor, "rule optionValue");

      try {
         try {
            this.pushFollow(FOLLOW_id_in_option778);
            id32 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id32.getTree());
            }

            char_literal33 = (Token)this.match(this.input, 33, FOLLOW_LABEL_ASSIGN_in_option780);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_LABEL_ASSIGN.add(char_literal33);
            }

            this.pushFollow(FOLLOW_optionValue_in_option782);
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
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_LABEL_ASSIGN.nextNode(), root_1);
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
      ParserRuleReturnScope qid35 = null;
      CommonTree s_tree = null;
      CommonTree STRING_LITERAL36_tree = null;
      CommonTree CHAR_LITERAL37_tree = null;
      CommonTree INT38_tree = null;
      RewriteRuleTokenStream stream_70 = new RewriteRuleTokenStream(this.adaptor, "token 70");

      try {
         try {
            int alt14 = true;
            byte alt14;
            switch (this.input.LA(1)) {
               case 16:
                  alt14 = 3;
                  break;
               case 31:
                  alt14 = 4;
                  break;
               case 51:
               case 61:
                  alt14 = 1;
                  break;
               case 56:
                  alt14 = 2;
                  break;
               case 70:
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
                  this.pushFollow(FOLLOW_qid_in_optionValue811);
                  qid35 = this.qid();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, qid35.getTree());
                  }
                  break;
               case 2:
                  root_0 = (CommonTree)this.adaptor.nil();
                  STRING_LITERAL36 = (Token)this.match(this.input, 56, FOLLOW_STRING_LITERAL_in_optionValue821);
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
                  CHAR_LITERAL37 = (Token)this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_optionValue831);
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
                  INT38 = (Token)this.match(this.input, 31, FOLLOW_INT_in_optionValue841);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     INT38_tree = (CommonTree)this.adaptor.create(INT38);
                     this.adaptor.addChild(root_0, INT38_tree);
                  }
                  break;
               case 5:
                  s = (Token)this.match(this.input, 70, FOLLOW_70_in_optionValue851);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_70.add(s);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(56, (Token)s));
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
      RewriteRuleTokenStream stream_RET = new RewriteRuleTokenStream(this.adaptor, "token RET");
      RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
      RewriteRuleTokenStream stream_FRAGMENT = new RewriteRuleTokenStream(this.adaptor, "token FRAGMENT");
      RewriteRuleTokenStream stream_86 = new RewriteRuleTokenStream(this.adaptor, "token 86");
      RewriteRuleTokenStream stream_87 = new RewriteRuleTokenStream(this.adaptor, "token 87");
      RewriteRuleTokenStream stream_74 = new RewriteRuleTokenStream(this.adaptor, "token 74");
      RewriteRuleTokenStream stream_88 = new RewriteRuleTokenStream(this.adaptor, "token 88");
      RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
      RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");
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
         if (LA15_0 == 20) {
            alt15 = 1;
         }

         switch (alt15) {
            case 1:
               DOC_COMMENT39 = (Token)this.match(this.input, 20, FOLLOW_DOC_COMMENT_in_rule876);
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
         if (LA17_0 == 28 || LA17_0 >= 86 && LA17_0 <= 88) {
            alt17 = 1;
         }

         byte alt18;
         switch (alt17) {
            case 1:
               int alt16 = true;
               rule_return var48;
               switch (this.input.LA(1)) {
                  case 28:
                     alt18 = 4;
                     break;
                  case 86:
                     alt18 = 3;
                     break;
                  case 87:
                     alt18 = 1;
                     break;
                  case 88:
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
                     modifier = (Token)this.match(this.input, 87, FOLLOW_87_in_rule886);
                     if (this.state.failed) {
                        var48 = retval;
                        return var48;
                     }

                     if (this.state.backtracking == 0) {
                        stream_87.add(modifier);
                     }
                     break;
                  case 2:
                     modifier = (Token)this.match(this.input, 88, FOLLOW_88_in_rule888);
                     if (this.state.failed) {
                        var48 = retval;
                        return var48;
                     }

                     if (this.state.backtracking == 0) {
                        stream_88.add(modifier);
                     }
                     break;
                  case 3:
                     modifier = (Token)this.match(this.input, 86, FOLLOW_86_in_rule890);
                     if (this.state.failed) {
                        var48 = retval;
                        return var48;
                     }

                     if (this.state.backtracking == 0) {
                        stream_86.add(modifier);
                     }
                     break;
                  case 4:
                     modifier = (Token)this.match(this.input, 28, FOLLOW_FRAGMENT_in_rule892);
                     if (this.state.failed) {
                        var48 = retval;
                        return var48;
                     } else if (this.state.backtracking == 0) {
                        stream_FRAGMENT.add(modifier);
                     }
               }
            default:
               this.pushFollow(FOLLOW_id_in_rule900);
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
                  if (LA18_0 == 14) {
                     alt18 = 1;
                  }

                  switch (alt18) {
                     case 1:
                        char_literal41 = (Token)this.match(this.input, 14, FOLLOW_BANG_in_rule906);
                        if (this.state.failed) {
                           rule_return var49 = retval;
                           return var49;
                        } else if (this.state.backtracking == 0) {
                           stream_BANG.add(char_literal41);
                        }
                     default:
                        int alt19 = 2;
                        int LA19_0 = this.input.LA(1);
                        if (LA19_0 == 11) {
                           alt19 = 1;
                        }

                        switch (alt19) {
                           case 1:
                              arg = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_rule915);
                              if (this.state.failed) {
                                 rule_return var51 = retval;
                                 return var51;
                              } else if (this.state.backtracking == 0) {
                                 stream_ARG_ACTION.add(arg);
                              }
                           default:
                              int alt20 = 2;
                              int LA20_0 = this.input.LA(1);
                              if (LA20_0 == 47) {
                                 alt20 = 1;
                              }

                              switch (alt20) {
                                 case 1:
                                    string_literal42 = (Token)this.match(this.input, 47, FOLLOW_RET_in_rule924);
                                    rule_return var53;
                                    if (this.state.failed) {
                                       var53 = retval;
                                       return var53;
                                    } else {
                                       if (this.state.backtracking == 0) {
                                          stream_RET.add(string_literal42);
                                       }

                                       rt = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_rule928);
                                       if (this.state.failed) {
                                          var53 = retval;
                                          return var53;
                                       } else if (this.state.backtracking == 0) {
                                          stream_ARG_ACTION.add(rt);
                                       }
                                    }
                                 default:
                                    int alt21 = 2;
                                    int LA21_0 = this.input.LA(1);
                                    if (LA21_0 == 89) {
                                       alt21 = 1;
                                    }

                                    switch (alt21) {
                                       case 1:
                                          this.pushFollow(FOLLOW_throwsSpec_in_rule936);
                                          throwsSpec43 = this.throwsSpec();
                                          --this.state._fsp;
                                          if (this.state.failed) {
                                             rule_return var55 = retval;
                                             return var55;
                                          } else if (this.state.backtracking == 0) {
                                             stream_throwsSpec.add(throwsSpec43.getTree());
                                          }
                                       default:
                                          int alt22 = 2;
                                          int LA22_0 = this.input.LA(1);
                                          if (LA22_0 == 42) {
                                             alt22 = 1;
                                          }

                                          switch (alt22) {
                                             case 1:
                                                this.pushFollow(FOLLOW_optionsSpec_in_rule939);
                                                optionsSpec44 = this.optionsSpec();
                                                --this.state._fsp;
                                                if (this.state.failed) {
                                                   rule_return var57 = retval;
                                                   return var57;
                                                } else if (this.state.backtracking == 0) {
                                                   stream_optionsSpec.add(optionsSpec44.getTree());
                                                }
                                             default:
                                                int alt23 = 2;
                                                int LA23_0 = this.input.LA(1);
                                                if (LA23_0 == 52) {
                                                   alt23 = 1;
                                                }

                                                rule_return var59;
                                                switch (alt23) {
                                                   case 1:
                                                      this.pushFollow(FOLLOW_ruleScopeSpec_in_rule942);
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

                                                while(true) {
                                                   int alt25 = 2;
                                                   int LA25_0 = this.input.LA(1);
                                                   if (LA25_0 == 12) {
                                                      alt25 = 1;
                                                   }

                                                   rule_return var61;
                                                   switch (alt25) {
                                                      case 1:
                                                         this.pushFollow(FOLLOW_ruleAction_in_rule945);
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
                                                         char_literal47 = (Token)this.match(this.input, 74, FOLLOW_74_in_rule950);
                                                         if (this.state.failed) {
                                                            var59 = retval;
                                                            return var59;
                                                         }

                                                         if (this.state.backtracking == 0) {
                                                            stream_74.add(char_literal47);
                                                         }

                                                         this.pushFollow(FOLLOW_altList_in_rule952);
                                                         altList48 = this.altList();
                                                         --this.state._fsp;
                                                         if (this.state.failed) {
                                                            var59 = retval;
                                                            return var59;
                                                         }

                                                         if (this.state.backtracking == 0) {
                                                            stream_altList.add(altList48.getTree());
                                                         }

                                                         char_literal49 = (Token)this.match(this.input, 76, FOLLOW_76_in_rule954);
                                                         if (this.state.failed) {
                                                            var59 = retval;
                                                            return var59;
                                                         }

                                                         if (this.state.backtracking == 0) {
                                                            stream_76.add(char_literal49);
                                                         }

                                                         alt25 = 2;
                                                         LA25_0 = this.input.LA(1);
                                                         if (LA25_0 >= 81 && LA25_0 <= 82) {
                                                            alt25 = 1;
                                                         }

                                                         switch (alt25) {
                                                            case 1:
                                                               this.pushFollow(FOLLOW_exceptionGroup_in_rule958);
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
                                                                  root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(50, (String)"RULE")), root_1);
                                                                  this.adaptor.addChild(root_1, stream_id.nextTree());
                                                                  this.adaptor.addChild(root_1, modifier != null ? this.adaptor.create(modifier) : null);
                                                                  CommonTree root_2;
                                                                  if (stream_arg.hasNext()) {
                                                                     root_2 = (CommonTree)this.adaptor.nil();
                                                                     root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(9, (Token)arg)), root_2);
                                                                     this.adaptor.addChild(root_2, stream_arg.nextNode());
                                                                     this.adaptor.addChild(root_1, root_2);
                                                                  }

                                                                  stream_arg.reset();
                                                                  if (stream_RET.hasNext() || stream_rt.hasNext()) {
                                                                     root_2 = (CommonTree)this.adaptor.nil();
                                                                     root_2 = (CommonTree)this.adaptor.becomeRoot((Object)stream_RET.nextNode(), root_2);
                                                                     this.adaptor.addChild(root_2, stream_rt.nextNode());
                                                                     this.adaptor.addChild(root_1, root_2);
                                                                  }

                                                                  stream_RET.reset();
                                                                  stream_rt.reset();
                                                                  if (stream_throwsSpec.hasNext()) {
                                                                     this.adaptor.addChild(root_1, stream_throwsSpec.nextTree());
                                                                  }

                                                                  stream_throwsSpec.reset();
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
                                                                  this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(25, (String)"EOR"));
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
                        }
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
      RewriteRuleTokenStream stream_AT = new RewriteRuleTokenStream(this.adaptor, "token AT");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         try {
            char_literal51 = (Token)this.match(this.input, 12, FOLLOW_AT_in_ruleAction1064);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_AT.add(char_literal51);
            }

            this.pushFollow(FOLLOW_id_in_ruleAction1066);
            id52 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id52.getTree());
            }

            ACTION53 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_ruleAction1068);
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
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_AT.nextNode(), root_1);
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
      RewriteRuleTokenStream stream_72 = new RewriteRuleTokenStream(this.adaptor, "token 72");
      RewriteRuleTokenStream stream_89 = new RewriteRuleTokenStream(this.adaptor, "token 89");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         string_literal54 = (Token)this.match(this.input, 89, FOLLOW_89_in_throwsSpec1089);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_89.add(string_literal54);
            }

            this.pushFollow(FOLLOW_id_in_throwsSpec1091);
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
                  if (LA26_0 == 72) {
                     alt26 = 1;
                  }

                  switch (alt26) {
                     case 1:
                        char_literal56 = (Token)this.match(this.input, 72, FOLLOW_72_in_throwsSpec1095);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_72.add(char_literal56);
                        }

                        this.pushFollow(FOLLOW_id_in_throwsSpec1097);
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
      RewriteRuleTokenStream stream_72 = new RewriteRuleTokenStream(this.adaptor, "token 72");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         try {
            label1082: {
               int alt29 = true;
               int LA29_0 = this.input.LA(1);
               if (LA29_0 != 52) {
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
                  if (LA28_0 == 52) {
                     alt29 = 3;
                  } else {
                     if (LA28_0 != 12 && LA28_0 != 74) {
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
                  if (LA29_1 != 51 && LA29_1 != 61) {
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
                     string_literal58 = (Token)this.match(this.input, 52, FOLLOW_SCOPE_in_ruleScopeSpec1120);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_SCOPE.add(string_literal58);
                     }

                     ACTION59 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec1122);
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
                     string_literal60 = (Token)this.match(this.input, 52, FOLLOW_SCOPE_in_ruleScopeSpec1135);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_SCOPE.add(string_literal60);
                     }

                     this.pushFollow(FOLLOW_id_in_ruleScopeSpec1137);
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
                     string_literal65 = (Token)this.match(this.input, 52, FOLLOW_SCOPE_in_ruleScopeSpec1160);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_SCOPE.add(string_literal65);
                     }

                     ACTION66 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec1162);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_ACTION.add(ACTION66);
                     }

                     string_literal67 = (Token)this.match(this.input, 52, FOLLOW_SCOPE_in_ruleScopeSpec1166);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_SCOPE.add(string_literal67);
                     }

                     this.pushFollow(FOLLOW_id_in_ruleScopeSpec1168);
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
                        if (LA28_0 == 72) {
                           alt28 = 1;
                        }

                        switch (alt28) {
                           case 1:
                              char_literal69 = (Token)this.match(this.input, 72, FOLLOW_72_in_ruleScopeSpec1171);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_72.add(char_literal69);
                              }

                              this.pushFollow(FOLLOW_id_in_ruleScopeSpec1173);
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
                              char_literal71 = (Token)this.match(this.input, 76, FOLLOW_76_in_ruleScopeSpec1177);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_76.add(char_literal71);
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
                  if (LA28_0 == 72) {
                     alt28 = 1;
                  }

                  switch (alt28) {
                     case 1:
                        char_literal62 = (Token)this.match(this.input, 72, FOLLOW_72_in_ruleScopeSpec1140);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_72.add(char_literal62);
                        }

                        this.pushFollow(FOLLOW_id_in_ruleScopeSpec1142);
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
                        char_literal64 = (Token)this.match(this.input, 76, FOLLOW_76_in_ruleScopeSpec1146);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_76.add(char_literal64);
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
      ParserRuleReturnScope altpair73 = null;
      ParserRuleReturnScope altpair75 = null;
      CommonTree lp_tree = null;
      CommonTree rp_tree = null;
      CommonTree char_literal72_tree = null;
      CommonTree char_literal74_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
      RewriteRuleTokenStream stream_91 = new RewriteRuleTokenStream(this.adaptor, "token 91");
      RewriteRuleTokenStream stream_74 = new RewriteRuleTokenStream(this.adaptor, "token 74");
      RewriteRuleSubtreeStream stream_altpair = new RewriteRuleSubtreeStream(this.adaptor, "rule altpair");
      RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");

      try {
         lp = (Token)this.match(this.input, 68, FOLLOW_68_in_block1209);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_68.add(lp);
            }

            int alt31 = 2;
            int LA31_0 = this.input.LA(1);
            if (LA31_0 == 42 || LA31_0 == 74) {
               alt31 = 1;
            }

            byte alt32;
            int LA32_0;
            switch (alt31) {
               case 1:
                  alt32 = 2;
                  LA32_0 = this.input.LA(1);
                  if (LA32_0 == 42) {
                     alt32 = 1;
                  }

                  switch (alt32) {
                     case 1:
                        this.pushFollow(FOLLOW_optionsSpec_in_block1218);
                        opts = this.optionsSpec();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_optionsSpec.add(opts.getTree());
                        }
                     default:
                        char_literal72 = (Token)this.match(this.input, 74, FOLLOW_74_in_block1222);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_74.add(char_literal72);
                        }
                  }
               default:
                  this.pushFollow(FOLLOW_altpair_in_block1229);
                  altpair73 = this.altpair();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_altpair.add(altpair73.getTree());
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
                     char_literal74 = (Token)this.match(this.input, 91, FOLLOW_91_in_block1233);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_91.add(char_literal74);
                     }

                     this.pushFollow(FOLLOW_altpair_in_block1235);
                     altpair75 = this.altpair();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_altpair.add(altpair75.getTree());
                     }
                     break;
                  default:
                     rp = (Token)this.match(this.input, 69, FOLLOW_69_in_block1250);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_69.add(rp);
                     }

                     if (this.state.backtracking == 0) {
                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        CommonTree root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(15, lp, "BLOCK")), root_1);
                        if (stream_optionsSpec.hasNext()) {
                           this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
                        }

                        stream_optionsSpec.reset();
                        if (!stream_altpair.hasNext()) {
                           throw new RewriteEarlyExitException();
                        }

                        while(stream_altpair.hasNext()) {
                           this.adaptor.addChild(root_1, stream_altpair.nextTree());
                        }

                        stream_altpair.reset();
                        this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(24, rp, "EOB"));
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
      } catch (RecognitionException var28) {
         this.reportError(var28);
         this.recover(this.input, var28);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var28);
         return retval;
      } finally {
         ;
      }
   }

   public final altpair_return altpair() throws RecognitionException {
      altpair_return retval = new altpair_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope alternative76 = null;
      ParserRuleReturnScope rewrite77 = null;

      try {
         try {
            root_0 = (CommonTree)this.adaptor.nil();
            this.pushFollow(FOLLOW_alternative_in_altpair1289);
            alternative76 = this.alternative();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, alternative76.getTree());
            }

            this.pushFollow(FOLLOW_rewrite_in_altpair1291);
            rewrite77 = this.rewrite();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, rewrite77.getTree());
            }

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

   public final altList_return altList() throws RecognitionException {
      altList_return retval = new altList_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal79 = null;
      ParserRuleReturnScope altpair78 = null;
      ParserRuleReturnScope altpair80 = null;
      CommonTree char_literal79_tree = null;
      RewriteRuleTokenStream stream_91 = new RewriteRuleTokenStream(this.adaptor, "token 91");
      RewriteRuleSubtreeStream stream_altpair = new RewriteRuleSubtreeStream(this.adaptor, "rule altpair");
      CommonTree blkRoot = (CommonTree)this.adaptor.create(15, this.input.LT(-1), "BLOCK");

      try {
         this.pushFollow(FOLLOW_altpair_in_altList1311);
         altpair78 = this.altpair();
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_altpair.add(altpair78.getTree());
            }

            while(true) {
               int alt33 = 2;
               int LA33_0 = this.input.LA(1);
               if (LA33_0 == 91) {
                  alt33 = 1;
               }

               switch (alt33) {
                  case 1:
                     char_literal79 = (Token)this.match(this.input, 91, FOLLOW_91_in_altList1315);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_91.add(char_literal79);
                     }

                     this.pushFollow(FOLLOW_altpair_in_altList1317);
                     altpair80 = this.altpair();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_altpair.add(altpair80.getTree());
                     }
                     break;
                  default:
                     if (this.state.backtracking == 0) {
                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        CommonTree root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)blkRoot, root_1);
                        if (!stream_altpair.hasNext()) {
                           throw new RewriteEarlyExitException();
                        }

                        while(stream_altpair.hasNext()) {
                           this.adaptor.addChild(root_1, stream_altpair.nextTree());
                        }

                        stream_altpair.reset();
                        this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(24, (String)"EOB"));
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
      } catch (RecognitionException var16) {
         this.reportError(var16);
         this.recover(this.input, var16);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var16);
         return retval;
      } finally {
         ;
      }
   }

   public final alternative_return alternative() throws RecognitionException {
      alternative_return retval = new alternative_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope element81 = null;
      RewriteRuleSubtreeStream stream_element = new RewriteRuleSubtreeStream(this.adaptor, "rule element");
      Token firstToken = this.input.LT(1);
      Token prevToken = this.input.LT(-1);

      try {
         try {
            int alt35 = true;
            int LA35_0 = this.input.LA(1);
            byte alt35;
            if (LA35_0 != 4 && LA35_0 != 16 && LA35_0 != 51 && LA35_0 != 53 && LA35_0 != 56 && (LA35_0 < 61 || LA35_0 > 62) && LA35_0 != 68 && LA35_0 != 73 && LA35_0 != 93) {
               if (LA35_0 != 48 && LA35_0 != 69 && LA35_0 != 76 && LA35_0 != 91) {
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
                     if (LA34_0 == 4 || LA34_0 == 16 || LA34_0 == 51 || LA34_0 == 53 || LA34_0 == 56 || LA34_0 >= 61 && LA34_0 <= 62 || LA34_0 == 68 || LA34_0 == 73 || LA34_0 == 93) {
                        alt34 = 1;
                     }

                     switch (alt34) {
                        case 1:
                           this.pushFollow(FOLLOW_element_in_alternative1358);
                           element81 = this.element();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_element.add(element81.getTree());
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
                           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(23, (String)"EOA"));
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
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(26, prevToken, "EPSILON"));
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(23, (String)"EOA"));
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
      ParserRuleReturnScope exceptionHandler82 = null;
      ParserRuleReturnScope finallyClause83 = null;
      ParserRuleReturnScope finallyClause84 = null;

      try {
         try {
            int alt38 = true;
            int LA38_0 = this.input.LA(1);
            byte alt38;
            if (LA38_0 == 81) {
               alt38 = 1;
            } else {
               if (LA38_0 != 82) {
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
                     if (LA37_0 == 81) {
                        alt37 = 1;
                     }

                     switch (alt37) {
                        case 1:
                           this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup1409);
                           exceptionHandler82 = this.exceptionHandler();
                           --this.state._fsp;
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              this.adaptor.addChild(root_0, exceptionHandler82.getTree());
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
                           if (LA37_0 == 82) {
                              alt37 = 1;
                           }

                           switch (alt37) {
                              case 1:
                                 this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1416);
                                 finallyClause83 = this.finallyClause();
                                 --this.state._fsp;
                                 if (this.state.failed) {
                                    return retval;
                                 }

                                 if (this.state.backtracking == 0) {
                                    this.adaptor.addChild(root_0, finallyClause83.getTree());
                                 }
                              default:
                                 break label181;
                           }
                     }
                  }
               case 2:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup1424);
                  finallyClause84 = this.finallyClause();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, finallyClause84.getTree());
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
      Token string_literal85 = null;
      Token ARG_ACTION86 = null;
      Token ACTION87 = null;
      CommonTree string_literal85_tree = null;
      CommonTree ARG_ACTION86_tree = null;
      CommonTree ACTION87_tree = null;
      RewriteRuleTokenStream stream_81 = new RewriteRuleTokenStream(this.adaptor, "token 81");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");

      try {
         try {
            string_literal85 = (Token)this.match(this.input, 81, FOLLOW_81_in_exceptionHandler1444);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_81.add(string_literal85);
            }

            ARG_ACTION86 = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_exceptionHandler1446);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ARG_ACTION.add(ARG_ACTION86);
            }

            ACTION87 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler1448);
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
      Token string_literal88 = null;
      Token ACTION89 = null;
      CommonTree string_literal88_tree = null;
      CommonTree ACTION89_tree = null;
      RewriteRuleTokenStream stream_82 = new RewriteRuleTokenStream(this.adaptor, "token 82");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");

      try {
         try {
            string_literal88 = (Token)this.match(this.input, 82, FOLLOW_82_in_finallyClause1478);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_82.add(string_literal88);
            }

            ACTION89 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause1480);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION89);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_82.nextNode(), root_1);
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
      Token labelOp = null;
      Token g = null;
      Token ACTION99 = null;
      Token SEMPRED100 = null;
      ParserRuleReturnScope id90 = null;
      ParserRuleReturnScope atom91 = null;
      ParserRuleReturnScope ebnfSuffix92 = null;
      ParserRuleReturnScope id93 = null;
      ParserRuleReturnScope block94 = null;
      ParserRuleReturnScope ebnfSuffix95 = null;
      ParserRuleReturnScope atom96 = null;
      ParserRuleReturnScope ebnfSuffix97 = null;
      ParserRuleReturnScope ebnf98 = null;
      ParserRuleReturnScope treeSpec101 = null;
      ParserRuleReturnScope ebnfSuffix102 = null;
      CommonTree labelOp_tree = null;
      CommonTree g_tree = null;
      CommonTree ACTION99_tree = null;
      CommonTree SEMPRED100_tree = null;
      RewriteRuleTokenStream stream_78 = new RewriteRuleTokenStream(this.adaptor, "token 78");
      RewriteRuleTokenStream stream_LIST_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LIST_LABEL_ASSIGN");
      RewriteRuleTokenStream stream_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LABEL_ASSIGN");
      RewriteRuleTokenStream stream_SEMPRED = new RewriteRuleTokenStream(this.adaptor, "token SEMPRED");
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
               case 16:
               case 56:
               case 73:
               case 93:
                  alt46 = 3;
                  break;
               case 51:
                  switch (this.input.LA(2)) {
                     case 4:
                     case 11:
                     case 14:
                     case 16:
                     case 48:
                     case 49:
                     case 51:
                     case 53:
                     case 56:
                     case 61:
                     case 62:
                     case 68:
                     case 69:
                     case 70:
                     case 71:
                     case 73:
                     case 76:
                     case 80:
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
                     case 13:
                     case 15:
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
                     case 34:
                     case 35:
                     case 37:
                     case 38:
                     case 39:
                     case 40:
                     case 41:
                     case 42:
                     case 43:
                     case 44:
                     case 45:
                     case 46:
                     case 47:
                     case 50:
                     case 52:
                     case 54:
                     case 55:
                     case 57:
                     case 58:
                     case 59:
                     case 60:
                     case 63:
                     case 64:
                     case 65:
                     case 66:
                     case 67:
                     case 72:
                     case 74:
                     case 75:
                     case 77:
                     case 78:
                     case 79:
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
                     case 33:
                        nvaeMark = this.input.LA(3);
                        if (nvaeMark != 16 && nvaeMark != 51 && nvaeMark != 56 && nvaeMark != 61 && nvaeMark != 73 && nvaeMark != 93) {
                           if (nvaeMark != 68) {
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
                     case 36:
                        nvaeMark = this.input.LA(3);
                        if (nvaeMark != 16 && nvaeMark != 51 && nvaeMark != 56 && nvaeMark != 61 && nvaeMark != 73 && nvaeMark != 93) {
                           if (nvaeMark != 68) {
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
                  }
               case 53:
                  alt46 = 6;
                  break;
               case 61:
                  switch (this.input.LA(2)) {
                     case 4:
                     case 11:
                     case 14:
                     case 16:
                     case 48:
                     case 49:
                     case 51:
                     case 53:
                     case 56:
                     case 61:
                     case 62:
                     case 68:
                     case 69:
                     case 70:
                     case 71:
                     case 73:
                     case 76:
                     case 77:
                     case 80:
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
                     case 13:
                     case 15:
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
                     case 34:
                     case 35:
                     case 37:
                     case 38:
                     case 39:
                     case 40:
                     case 41:
                     case 42:
                     case 43:
                     case 44:
                     case 45:
                     case 46:
                     case 47:
                     case 50:
                     case 52:
                     case 54:
                     case 55:
                     case 57:
                     case 58:
                     case 59:
                     case 60:
                     case 63:
                     case 64:
                     case 65:
                     case 66:
                     case 67:
                     case 72:
                     case 74:
                     case 75:
                     case 78:
                     case 79:
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
                     case 33:
                        nvaeMark = this.input.LA(3);
                        if (nvaeMark != 16 && nvaeMark != 51 && nvaeMark != 56 && nvaeMark != 61 && nvaeMark != 73 && nvaeMark != 93) {
                           if (nvaeMark != 68) {
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
                     case 36:
                        nvaeMark = this.input.LA(3);
                        if (nvaeMark != 16 && nvaeMark != 51 && nvaeMark != 56 && nvaeMark != 61 && nvaeMark != 73 && nvaeMark != 93) {
                           if (nvaeMark != 68) {
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
                  }
               case 62:
                  alt46 = 7;
                  break;
               case 68:
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
                  this.pushFollow(FOLLOW_id_in_element1502);
                  id90 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(id90.getTree());
                  }

                  alt45 = true;
                  nvaeMark = this.input.LA(1);
                  if (nvaeMark == 33) {
                     alt45 = 1;
                  } else {
                     if (nvaeMark != 36) {
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
                        labelOp = (Token)this.match(this.input, 33, FOLLOW_LABEL_ASSIGN_in_element1507);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_LABEL_ASSIGN.add(labelOp);
                        }
                        break;
                     case 2:
                        labelOp = (Token)this.match(this.input, 36, FOLLOW_LIST_LABEL_ASSIGN_in_element1511);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_LIST_LABEL_ASSIGN.add(labelOp);
                        }
                  }

                  this.pushFollow(FOLLOW_atom_in_element1514);
                  atom91 = this.atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_atom.add(atom91.getTree());
                  }

                  alt42 = true;
                  LA42_0 = this.input.LA(1);
                  if ((LA42_0 < 70 || LA42_0 > 71) && LA42_0 != 80) {
                     if (LA42_0 != 4 && LA42_0 != 16 && LA42_0 != 48 && LA42_0 != 51 && LA42_0 != 53 && LA42_0 != 56 && (LA42_0 < 61 || LA42_0 > 62) && (LA42_0 < 68 || LA42_0 > 69) && LA42_0 != 73 && LA42_0 != 76 && LA42_0 != 91 && LA42_0 != 93) {
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
                        this.pushFollow(FOLLOW_ebnfSuffix_in_element1520);
                        ebnfSuffix92 = this.ebnfSuffix();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(ebnfSuffix92.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                           root_2 = (CommonTree)this.adaptor.nil();
                           root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(15, (String)"BLOCK")), root_2);
                           root_3 = (CommonTree)this.adaptor.nil();
                           root_3 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_3);
                           root_4 = (CommonTree)this.adaptor.nil();
                           root_4 = (CommonTree)this.adaptor.becomeRoot((Object)stream_labelOp.nextNode(), root_4);
                           this.adaptor.addChild(root_4, stream_id.nextTree());
                           this.adaptor.addChild(root_4, stream_atom.nextTree());
                           this.adaptor.addChild(root_3, root_4);
                           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(23, (String)"EOA"));
                           this.adaptor.addChild(root_2, root_3);
                           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(24, (String)"EOB"));
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
                  this.pushFollow(FOLLOW_id_in_element1579);
                  id93 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(id93.getTree());
                  }

                  alt45 = true;
                  nvaeMark = this.input.LA(1);
                  if (nvaeMark == 33) {
                     alt45 = 1;
                  } else {
                     if (nvaeMark != 36) {
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
                        labelOp = (Token)this.match(this.input, 33, FOLLOW_LABEL_ASSIGN_in_element1584);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_LABEL_ASSIGN.add(labelOp);
                        }
                        break;
                     case 2:
                        labelOp = (Token)this.match(this.input, 36, FOLLOW_LIST_LABEL_ASSIGN_in_element1588);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_LIST_LABEL_ASSIGN.add(labelOp);
                        }
                  }

                  this.pushFollow(FOLLOW_block_in_element1591);
                  block94 = this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_block.add(block94.getTree());
                  }

                  alt42 = true;
                  LA42_0 = this.input.LA(1);
                  if ((LA42_0 < 70 || LA42_0 > 71) && LA42_0 != 80) {
                     if (LA42_0 != 4 && LA42_0 != 16 && LA42_0 != 48 && LA42_0 != 51 && LA42_0 != 53 && LA42_0 != 56 && (LA42_0 < 61 || LA42_0 > 62) && (LA42_0 < 68 || LA42_0 > 69) && LA42_0 != 73 && LA42_0 != 76 && LA42_0 != 91 && LA42_0 != 93) {
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
                        this.pushFollow(FOLLOW_ebnfSuffix_in_element1597);
                        ebnfSuffix95 = this.ebnfSuffix();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(ebnfSuffix95.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                           root_2 = (CommonTree)this.adaptor.nil();
                           root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(15, (String)"BLOCK")), root_2);
                           root_3 = (CommonTree)this.adaptor.nil();
                           root_3 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_3);
                           root_4 = (CommonTree)this.adaptor.nil();
                           root_4 = (CommonTree)this.adaptor.becomeRoot((Object)stream_labelOp.nextNode(), root_4);
                           this.adaptor.addChild(root_4, stream_id.nextTree());
                           this.adaptor.addChild(root_4, stream_block.nextTree());
                           this.adaptor.addChild(root_3, root_4);
                           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(23, (String)"EOA"));
                           this.adaptor.addChild(root_2, root_3);
                           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(24, (String)"EOB"));
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
                  this.pushFollow(FOLLOW_atom_in_element1656);
                  atom96 = this.atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_atom.add(atom96.getTree());
                  }

                  alt45 = true;
                  nvaeMark = this.input.LA(1);
                  if ((nvaeMark < 70 || nvaeMark > 71) && nvaeMark != 80) {
                     if (nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 48 && nvaeMark != 51 && nvaeMark != 53 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 73 && nvaeMark != 76 && nvaeMark != 91 && nvaeMark != 93) {
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
                        this.pushFollow(FOLLOW_ebnfSuffix_in_element1662);
                        ebnfSuffix97 = this.ebnfSuffix();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(ebnfSuffix97.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                           root_2 = (CommonTree)this.adaptor.nil();
                           root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(15, (String)"BLOCK")), root_2);
                           root_3 = (CommonTree)this.adaptor.nil();
                           root_3 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_3);
                           this.adaptor.addChild(root_3, stream_atom.nextTree());
                           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(23, (String)"EOA"));
                           this.adaptor.addChild(root_2, root_3);
                           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(24, (String)"EOB"));
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
                  this.pushFollow(FOLLOW_ebnf_in_element1708);
                  ebnf98 = this.ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, ebnf98.getTree());
                  }
                  break;
               case 5:
                  root_0 = (CommonTree)this.adaptor.nil();
                  ACTION99 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_element1715);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION99_tree = (CommonTree)this.adaptor.create(ACTION99);
                     this.adaptor.addChild(root_0, ACTION99_tree);
                  }
                  break;
               case 6:
                  SEMPRED100 = (Token)this.match(this.input, 53, FOLLOW_SEMPRED_in_element1722);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_SEMPRED.add(SEMPRED100);
                  }

                  alt45 = true;
                  nvaeMark = this.input.LA(1);
                  if (nvaeMark == 78) {
                     alt45 = 1;
                  } else {
                     if (nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 48 && nvaeMark != 51 && nvaeMark != 53 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 73 && nvaeMark != 76 && nvaeMark != 91 && nvaeMark != 93) {
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
                        g = (Token)this.match(this.input, 78, FOLLOW_78_in_element1728);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_78.add(g);
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(29, (Token)g));
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
                  this.pushFollow(FOLLOW_treeSpec_in_element1748);
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
                  if ((nvaeMark < 70 || nvaeMark > 71) && nvaeMark != 80) {
                     if (nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 48 && nvaeMark != 51 && nvaeMark != 53 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 68 || nvaeMark > 69) && nvaeMark != 73 && nvaeMark != 76 && nvaeMark != 91 && nvaeMark != 93) {
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
                        this.pushFollow(FOLLOW_ebnfSuffix_in_element1754);
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
                           root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(15, (String)"BLOCK")), root_2);
                           root_3 = (CommonTree)this.adaptor.nil();
                           root_3 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_3);
                           this.adaptor.addChild(root_3, stream_treeSpec.nextTree());
                           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(23, (String)"EOA"));
                           this.adaptor.addChild(root_2, root_3);
                           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(24, (String)"EOB"));
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
      Token RULE_REF106 = null;
      Token ARG_ACTION107 = null;
      ParserRuleReturnScope terminal103 = null;
      ParserRuleReturnScope range104 = null;
      ParserRuleReturnScope notSet105 = null;
      CommonTree op_tree = null;
      CommonTree RULE_REF106_tree = null;
      CommonTree ARG_ACTION107_tree = null;
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
               case 16:
                  int LA54_1 = this.input.LA(2);
                  if (LA54_1 == 46) {
                     alt54 = 2;
                  } else {
                     if (LA54_1 != 4 && LA54_1 != 14 && LA54_1 != 16 && (LA54_1 < 48 || LA54_1 > 49) && LA54_1 != 51 && LA54_1 != 53 && LA54_1 != 56 && (LA54_1 < 61 || LA54_1 > 62) && (LA54_1 < 68 || LA54_1 > 71) && LA54_1 != 73 && (LA54_1 < 76 || LA54_1 > 77) && LA54_1 != 80 && LA54_1 != 91 && LA54_1 != 93) {
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

                     alt54 = 1;
                  }
                  break;
               case 51:
                  alt54 = 4;
                  break;
               case 56:
               case 61:
               case 73:
                  alt54 = 1;
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

            boolean alt53;
            int LA53_0;
            byte alt50;
            boolean alt50;
            byte alt53;
            NoViableAltException nvae;
            RewriteRuleTokenStream stream_op;
            CommonTree root_1;
            label1439:
            switch (alt54) {
               case 1:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_terminal_in_atom1806);
                  terminal103 = this.terminal();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, terminal103.getTree());
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_range_in_atom1811);
                  range104 = this.range();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_range.add(range104.getTree());
                  }

                  alt50 = true;
                  LA50_0 = this.input.LA(1);
                  if (LA50_0 != 14 && LA50_0 != 49) {
                     if (LA50_0 != 4 && LA50_0 != 16 && LA50_0 != 48 && LA50_0 != 51 && LA50_0 != 53 && LA50_0 != 56 && (LA50_0 < 61 || LA50_0 > 62) && (LA50_0 < 68 || LA50_0 > 71) && LA50_0 != 73 && LA50_0 != 76 && LA50_0 != 80 && LA50_0 != 91 && LA50_0 != 93) {
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
                        alt53 = true;
                        LA53_0 = this.input.LA(1);
                        if (LA53_0 == 49) {
                           alt53 = 1;
                        } else {
                           if (LA53_0 != 14) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvae = new NoViableAltException("", 47, 0, this.input);
                              throw nvae;
                           }

                           alt53 = 2;
                        }

                        switch (alt53) {
                           case 1:
                              op = (Token)this.match(this.input, 49, FOLLOW_ROOT_in_atom1821);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_ROOT.add(op);
                              }
                              break;
                           case 2:
                              op = (Token)this.match(this.input, 14, FOLLOW_BANG_in_atom1825);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_BANG.add(op);
                              }
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           stream_op = new RewriteRuleTokenStream(this.adaptor, "token op", op);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_op.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_range.nextTree());
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label1439;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_range.nextTree());
                           retval.tree = root_0;
                        }
                     default:
                        break label1439;
                  }
               case 3:
                  this.pushFollow(FOLLOW_notSet_in_atom1859);
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
                  if (LA50_0 != 14 && LA50_0 != 49) {
                     if (LA50_0 != 4 && LA50_0 != 16 && LA50_0 != 48 && LA50_0 != 51 && LA50_0 != 53 && LA50_0 != 56 && (LA50_0 < 61 || LA50_0 > 62) && (LA50_0 < 68 || LA50_0 > 71) && LA50_0 != 73 && LA50_0 != 76 && LA50_0 != 80 && LA50_0 != 91 && LA50_0 != 93) {
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
                        alt53 = true;
                        LA53_0 = this.input.LA(1);
                        if (LA53_0 == 49) {
                           alt53 = 1;
                        } else {
                           if (LA53_0 != 14) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvae = new NoViableAltException("", 49, 0, this.input);
                              throw nvae;
                           }

                           alt53 = 2;
                        }

                        switch (alt53) {
                           case 1:
                              op = (Token)this.match(this.input, 49, FOLLOW_ROOT_in_atom1868);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_ROOT.add(op);
                              }
                              break;
                           case 2:
                              op = (Token)this.match(this.input, 14, FOLLOW_BANG_in_atom1872);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_BANG.add(op);
                              }
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           stream_op = new RewriteRuleTokenStream(this.adaptor, "token op", op);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_op.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_notSet.nextTree());
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label1439;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           this.adaptor.addChild(root_0, stream_notSet.nextTree());
                           retval.tree = root_0;
                        }
                     default:
                        break label1439;
                  }
               case 4:
                  RULE_REF106 = (Token)this.match(this.input, 51, FOLLOW_RULE_REF_in_atom1908);
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
                        ARG_ACTION107 = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_atom1910);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ARG_ACTION.add(ARG_ACTION107);
                        }
                  }

                  alt53 = true;
                  LA53_0 = this.input.LA(1);
                  if (LA53_0 != 14 && LA53_0 != 49) {
                     if (LA53_0 != 4 && LA53_0 != 16 && LA53_0 != 48 && LA53_0 != 51 && LA53_0 != 53 && LA53_0 != 56 && (LA53_0 < 61 || LA53_0 > 62) && (LA53_0 < 68 || LA53_0 > 71) && LA53_0 != 73 && LA53_0 != 76 && LA53_0 != 80 && LA53_0 != 91 && LA53_0 != 93) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 53, 0, this.input);
                        throw nvae;
                     }

                     alt53 = 2;
                  } else {
                     alt53 = 1;
                  }

                  switch (alt53) {
                     case 1:
                        int alt52 = true;
                        int LA52_0 = this.input.LA(1);
                        byte alt52;
                        if (LA52_0 == 49) {
                           alt52 = 1;
                        } else {
                           if (LA52_0 != 14) {
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
                              op = (Token)this.match(this.input, 49, FOLLOW_ROOT_in_atom1920);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_ROOT.add(op);
                              }
                              break;
                           case 2:
                              op = (Token)this.match(this.input, 14, FOLLOW_BANG_in_atom1924);
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_BANG.add(op);
                              }
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           RewriteRuleTokenStream stream_op = new RewriteRuleTokenStream(this.adaptor, "token op", op);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_op.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_RULE_REF.nextNode());
                           if (stream_ARG_ACTION.hasNext()) {
                              this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
                           }

                           stream_ARG_ACTION.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break;
                     case 2:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_RULE_REF.nextNode(), root_1);
                           if (stream_ARG_ACTION.hasNext()) {
                              this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
                           }

                           stream_ARG_ACTION.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var36) {
            this.reportError(var36);
            this.recover(this.input, var36);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var36);
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
      Token char_literal108 = null;
      ParserRuleReturnScope notTerminal109 = null;
      ParserRuleReturnScope elementOptions110 = null;
      ParserRuleReturnScope block111 = null;
      ParserRuleReturnScope elementOptions112 = null;
      CommonTree char_literal108_tree = null;
      RewriteRuleTokenStream stream_93 = new RewriteRuleTokenStream(this.adaptor, "token 93");
      RewriteRuleSubtreeStream stream_notTerminal = new RewriteRuleSubtreeStream(this.adaptor, "rule notTerminal");
      RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");
      RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");

      try {
         try {
            char_literal108 = (Token)this.match(this.input, 93, FOLLOW_93_in_notSet1972);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_93.add(char_literal108);
            }

            int alt57 = true;
            int LA57_0 = this.input.LA(1);
            byte alt57;
            if (LA57_0 != 16 && LA57_0 != 56 && LA57_0 != 61) {
               if (LA57_0 != 68) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 57, 0, this.input);
                  throw nvae;
               }

               alt57 = 2;
            } else {
               alt57 = 1;
            }

            byte alt56;
            int LA56_0;
            CommonTree root_1;
            label236:
            switch (alt57) {
               case 1:
                  this.pushFollow(FOLLOW_notTerminal_in_notSet1978);
                  notTerminal109 = this.notTerminal();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_notTerminal.add(notTerminal109.getTree());
                  }

                  alt56 = 2;
                  LA56_0 = this.input.LA(1);
                  if (LA56_0 == 77) {
                     alt56 = 1;
                  }

                  switch (alt56) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_notSet1980);
                        elementOptions110 = this.elementOptions();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_elementOptions.add(elementOptions110.getTree());
                        }
                     default:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_93.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_notTerminal.nextTree());
                           if (stream_elementOptions.hasNext()) {
                              this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                           }

                           stream_elementOptions.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label236;
                  }
               case 2:
                  this.pushFollow(FOLLOW_block_in_notSet1998);
                  block111 = this.block();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_block.add(block111.getTree());
                  }

                  alt56 = 2;
                  LA56_0 = this.input.LA(1);
                  if (LA56_0 == 77) {
                     alt56 = 1;
                  }

                  switch (alt56) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_notSet2000);
                        elementOptions112 = this.elementOptions();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_elementOptions.add(elementOptions112.getTree());
                        }
                     default:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_93.nextNode(), root_1);
                           this.adaptor.addChild(root_1, stream_block.nextTree());
                           if (stream_elementOptions.hasNext()) {
                              this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                           }

                           stream_elementOptions.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                  }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
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

   public final notTerminal_return notTerminal() throws RecognitionException {
      notTerminal_return retval = new notTerminal_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token set113 = null;
      CommonTree set113_tree = null;

      try {
         try {
            root_0 = (CommonTree)this.adaptor.nil();
            set113 = this.input.LT(1);
            if (this.input.LA(1) != 16 && this.input.LA(1) != 56 && this.input.LA(1) != 61) {
               if (this.state.backtracking > 0) {
                  this.state.failed = true;
                  return retval;
               }

               MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
               throw mse;
            }

            this.input.consume();
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set113));
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

   public final elementOptions_return elementOptions() throws RecognitionException {
      elementOptions_return retval = new elementOptions_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal114 = null;
      Token char_literal116 = null;
      Token char_literal117 = null;
      Token char_literal119 = null;
      Token char_literal121 = null;
      ParserRuleReturnScope qid115 = null;
      ParserRuleReturnScope option118 = null;
      ParserRuleReturnScope option120 = null;
      CommonTree char_literal114_tree = null;
      CommonTree char_literal116_tree = null;
      CommonTree char_literal117_tree = null;
      CommonTree char_literal119_tree = null;
      CommonTree char_literal121_tree = null;
      RewriteRuleTokenStream stream_79 = new RewriteRuleTokenStream(this.adaptor, "token 79");
      RewriteRuleTokenStream stream_77 = new RewriteRuleTokenStream(this.adaptor, "token 77");
      RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");
      RewriteRuleSubtreeStream stream_qid = new RewriteRuleSubtreeStream(this.adaptor, "rule qid");
      RewriteRuleSubtreeStream stream_option = new RewriteRuleSubtreeStream(this.adaptor, "rule option");

      try {
         try {
            label1126: {
               int alt59 = true;
               int LA59_0 = this.input.LA(1);
               if (LA59_0 != 77) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 59, 0, this.input);
                  throw nvae;
               }

               int LA59_1 = this.input.LA(2);
               int LA58_0;
               int nvaeMark;
               int nvaeConsume;
               byte alt59;
               NoViableAltException nvae;
               if (LA59_1 == 61) {
                  LA58_0 = this.input.LA(3);
                  if (LA58_0 != 73 && LA58_0 != 79) {
                     if (LA58_0 != 33) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                              this.input.consume();
                           }

                           nvae = new NoViableAltException("", 59, 2, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt59 = 2;
                  } else {
                     alt59 = 1;
                  }
               } else {
                  if (LA59_1 != 51) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     LA58_0 = this.input.mark();

                     try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 59, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA58_0);
                     }
                  }

                  LA58_0 = this.input.LA(3);
                  if (LA58_0 != 73 && LA58_0 != 79) {
                     if (LA58_0 != 33) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                              this.input.consume();
                           }

                           nvae = new NoViableAltException("", 59, 3, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt59 = 2;
                  } else {
                     alt59 = 1;
                  }
               }

               CommonTree root_1;
               switch (alt59) {
                  case 1:
                     char_literal114 = (Token)this.match(this.input, 77, FOLLOW_77_in_elementOptions2052);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_77.add(char_literal114);
                     }

                     this.pushFollow(FOLLOW_qid_in_elementOptions2054);
                     qid115 = this.qid();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_qid.add(qid115.getTree());
                     }

                     char_literal116 = (Token)this.match(this.input, 79, FOLLOW_79_in_elementOptions2056);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_79.add(char_literal116);
                     }

                     if (this.state.backtracking == 0) {
                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(42, (String)"OPTIONS")), root_1);
                        this.adaptor.addChild(root_1, stream_qid.nextTree());
                        this.adaptor.addChild(root_0, root_1);
                        retval.tree = root_0;
                     }
                     break label1126;
                  case 2:
                     char_literal117 = (Token)this.match(this.input, 77, FOLLOW_77_in_elementOptions2074);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_77.add(char_literal117);
                     }

                     this.pushFollow(FOLLOW_option_in_elementOptions2076);
                     option118 = this.option();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_option.add(option118.getTree());
                     }
                     break;
                  default:
                     break label1126;
               }

               label1107:
               while(true) {
                  int alt58 = 2;
                  LA58_0 = this.input.LA(1);
                  if (LA58_0 == 76) {
                     alt58 = 1;
                  }

                  switch (alt58) {
                     case 1:
                        char_literal119 = (Token)this.match(this.input, 76, FOLLOW_76_in_elementOptions2079);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_76.add(char_literal119);
                        }

                        this.pushFollow(FOLLOW_option_in_elementOptions2081);
                        option120 = this.option();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_option.add(option120.getTree());
                        }
                        break;
                     default:
                        char_literal121 = (Token)this.match(this.input, 79, FOLLOW_79_in_elementOptions2085);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_79.add(char_literal121);
                        }

                        if (this.state.backtracking != 0) {
                           break label1107;
                        }

                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(42, (String)"OPTIONS")), root_1);
                        if (!stream_option.hasNext()) {
                           throw new RewriteEarlyExitException();
                        }

                        while(stream_option.hasNext()) {
                           this.adaptor.addChild(root_1, stream_option.nextTree());
                        }

                        stream_option.reset();
                        this.adaptor.addChild(root_0, root_1);
                        retval.tree = root_0;
                        break label1107;
                  }
               }
            }

            retval.stop = this.input.LT(-1);
            if (this.state.backtracking == 0) {
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
         } catch (RecognitionException var54) {
            this.reportError(var54);
            this.recover(this.input, var54);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var54);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final elementOption_return elementOption() throws RecognitionException {
      elementOption_return retval = new elementOption_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal123 = null;
      ParserRuleReturnScope id122 = null;
      ParserRuleReturnScope optionValue124 = null;
      CommonTree char_literal123_tree = null;
      RewriteRuleTokenStream stream_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LABEL_ASSIGN");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_optionValue = new RewriteRuleSubtreeStream(this.adaptor, "rule optionValue");

      try {
         try {
            this.pushFollow(FOLLOW_id_in_elementOption2105);
            id122 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id122.getTree());
            }

            char_literal123 = (Token)this.match(this.input, 33, FOLLOW_LABEL_ASSIGN_in_elementOption2107);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_LABEL_ASSIGN.add(char_literal123);
            }

            this.pushFollow(FOLLOW_optionValue_in_elementOption2109);
            optionValue124 = this.optionValue();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_optionValue.add(optionValue124.getTree());
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_LABEL_ASSIGN.nextNode(), root_1);
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

   public final treeSpec_return treeSpec() throws RecognitionException {
      treeSpec_return retval = new treeSpec_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token string_literal125 = null;
      Token char_literal128 = null;
      ParserRuleReturnScope element126 = null;
      ParserRuleReturnScope element127 = null;
      CommonTree string_literal125_tree = null;
      CommonTree char_literal128_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleTokenStream stream_TREE_BEGIN = new RewriteRuleTokenStream(this.adaptor, "token TREE_BEGIN");
      RewriteRuleSubtreeStream stream_element = new RewriteRuleSubtreeStream(this.adaptor, "rule element");

      try {
         string_literal125 = (Token)this.match(this.input, 62, FOLLOW_TREE_BEGIN_in_treeSpec2131);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_TREE_BEGIN.add(string_literal125);
            }

            this.pushFollow(FOLLOW_element_in_treeSpec2133);
            element126 = this.element();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  stream_element.add(element126.getTree());
               }

               int cnt60 = 0;

               while(true) {
                  int alt60 = 2;
                  int LA60_0 = this.input.LA(1);
                  if (LA60_0 == 4 || LA60_0 == 16 || LA60_0 == 51 || LA60_0 == 53 || LA60_0 == 56 || LA60_0 >= 61 && LA60_0 <= 62 || LA60_0 == 68 || LA60_0 == 73 || LA60_0 == 93) {
                     alt60 = 1;
                  }

                  switch (alt60) {
                     case 1:
                        this.pushFollow(FOLLOW_element_in_treeSpec2137);
                        element127 = this.element();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_element.add(element127.getTree());
                        }

                        ++cnt60;
                        break;
                     default:
                        if (cnt60 < 1) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           EarlyExitException eee = new EarlyExitException(60, this.input);
                           throw eee;
                        }

                        char_literal128 = (Token)this.match(this.input, 69, FOLLOW_69_in_treeSpec2142);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_69.add(char_literal128);
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(62, (String)"TREE_BEGIN")), root_1);
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

   public final range_return range() throws RecognitionException {
      range_return retval = new range_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token c1 = null;
      Token c2 = null;
      Token RANGE129 = null;
      ParserRuleReturnScope elementOptions130 = null;
      CommonTree c1_tree = null;
      CommonTree c2_tree = null;
      CommonTree RANGE129_tree = null;
      RewriteRuleTokenStream stream_RANGE = new RewriteRuleTokenStream(this.adaptor, "token RANGE");
      RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
      RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");

      try {
         try {
            c1 = (Token)this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_range2165);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_CHAR_LITERAL.add(c1);
            }

            RANGE129 = (Token)this.match(this.input, 46, FOLLOW_RANGE_in_range2167);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_RANGE.add(RANGE129);
            }

            c2 = (Token)this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_range2171);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_CHAR_LITERAL.add(c2);
            }

            int alt61 = 2;
            int LA61_0 = this.input.LA(1);
            if (LA61_0 == 77) {
               alt61 = 1;
            }

            switch (alt61) {
               case 1:
                  this.pushFollow(FOLLOW_elementOptions_in_range2173);
                  elementOptions130 = this.elementOptions();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_elementOptions.add(elementOptions130.getTree());
                  }
               default:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     RewriteRuleTokenStream stream_c1 = new RewriteRuleTokenStream(this.adaptor, "token c1", c1);
                     RewriteRuleTokenStream stream_c2 = new RewriteRuleTokenStream(this.adaptor, "token c2", c2);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(17, c1, "..")), root_1);
                     this.adaptor.addChild(root_1, stream_c1.nextNode());
                     this.adaptor.addChild(root_1, stream_c2.nextNode());
                     if (stream_elementOptions.hasNext()) {
                        this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                     }

                     stream_elementOptions.reset();
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

   public final terminal_return terminal() throws RecognitionException {
      terminal_return retval = new terminal_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token CHAR_LITERAL131 = null;
      Token TOKEN_REF133 = null;
      Token ARG_ACTION134 = null;
      Token STRING_LITERAL136 = null;
      Token char_literal138 = null;
      Token char_literal140 = null;
      Token char_literal141 = null;
      ParserRuleReturnScope elementOptions132 = null;
      ParserRuleReturnScope elementOptions135 = null;
      ParserRuleReturnScope elementOptions137 = null;
      ParserRuleReturnScope elementOptions139 = null;
      CommonTree CHAR_LITERAL131_tree = null;
      CommonTree TOKEN_REF133_tree = null;
      CommonTree ARG_ACTION134_tree = null;
      CommonTree STRING_LITERAL136_tree = null;
      CommonTree char_literal138_tree = null;
      CommonTree char_literal140_tree = null;
      CommonTree char_literal141_tree = null;
      RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
      RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
      RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
      RewriteRuleTokenStream stream_ROOT = new RewriteRuleTokenStream(this.adaptor, "token ROOT");
      RewriteRuleTokenStream stream_73 = new RewriteRuleTokenStream(this.adaptor, "token 73");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
      RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
      RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");

      try {
         try {
            int alt67 = true;
            byte alt67;
            switch (this.input.LA(1)) {
               case 16:
                  alt67 = 1;
                  break;
               case 56:
                  alt67 = 3;
                  break;
               case 61:
                  alt67 = 2;
                  break;
               case 73:
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

            byte alt66;
            int LA66_0;
            CommonTree root_1;
            label494:
            switch (alt67) {
               case 1:
                  CHAR_LITERAL131 = (Token)this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_terminal2210);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_CHAR_LITERAL.add(CHAR_LITERAL131);
                  }

                  alt66 = 2;
                  LA66_0 = this.input.LA(1);
                  if (LA66_0 == 77) {
                     alt66 = 1;
                  }

                  switch (alt66) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_terminal2212);
                        elementOptions132 = this.elementOptions();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_elementOptions.add(elementOptions132.getTree());
                        }
                     default:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_CHAR_LITERAL.nextNode(), root_1);
                           if (stream_elementOptions.hasNext()) {
                              this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                           }

                           stream_elementOptions.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label494;
                  }
               case 2:
                  TOKEN_REF133 = (Token)this.match(this.input, 61, FOLLOW_TOKEN_REF_in_terminal2243);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_TOKEN_REF.add(TOKEN_REF133);
                  }

                  alt66 = 2;
                  LA66_0 = this.input.LA(1);
                  if (LA66_0 == 11) {
                     alt66 = 1;
                  }

                  switch (alt66) {
                     case 1:
                        ARG_ACTION134 = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_terminal2245);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ARG_ACTION.add(ARG_ACTION134);
                        }
                     default:
                        int alt64 = 2;
                        int LA64_0 = this.input.LA(1);
                        if (LA64_0 == 77) {
                           alt64 = 1;
                        }

                        switch (alt64) {
                           case 1:
                              this.pushFollow(FOLLOW_elementOptions_in_terminal2248);
                              elementOptions135 = this.elementOptions();
                              --this.state._fsp;
                              if (this.state.failed) {
                                 return retval;
                              }

                              if (this.state.backtracking == 0) {
                                 stream_elementOptions.add(elementOptions135.getTree());
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
                                 if (stream_elementOptions.hasNext()) {
                                    this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                                 }

                                 stream_elementOptions.reset();
                                 this.adaptor.addChild(root_0, root_1);
                                 retval.tree = root_0;
                              }
                              break label494;
                        }
                  }
               case 3:
                  STRING_LITERAL136 = (Token)this.match(this.input, 56, FOLLOW_STRING_LITERAL_in_terminal2269);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_STRING_LITERAL.add(STRING_LITERAL136);
                  }

                  alt66 = 2;
                  LA66_0 = this.input.LA(1);
                  if (LA66_0 == 77) {
                     alt66 = 1;
                  }

                  switch (alt66) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_terminal2271);
                        elementOptions137 = this.elementOptions();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_elementOptions.add(elementOptions137.getTree());
                        }
                     default:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_STRING_LITERAL.nextNode(), root_1);
                           if (stream_elementOptions.hasNext()) {
                              this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                           }

                           stream_elementOptions.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                        break label494;
                  }
               case 4:
                  char_literal138 = (Token)this.match(this.input, 73, FOLLOW_73_in_terminal2292);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_73.add(char_literal138);
                  }

                  alt66 = 2;
                  LA66_0 = this.input.LA(1);
                  if (LA66_0 == 77) {
                     alt66 = 1;
                  }

                  switch (alt66) {
                     case 1:
                        this.pushFollow(FOLLOW_elementOptions_in_terminal2294);
                        elementOptions139 = this.elementOptions();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_elementOptions.add(elementOptions139.getTree());
                        }
                     default:
                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_73.nextNode(), root_1);
                           if (stream_elementOptions.hasNext()) {
                              this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
                           }

                           stream_elementOptions.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                        }
                  }
            }

            alt66 = 3;
            LA66_0 = this.input.LA(1);
            if (LA66_0 == 49) {
               alt66 = 1;
            } else if (LA66_0 == 14) {
               alt66 = 2;
            }

            RewriteRuleSubtreeStream stream_retval;
            switch (alt66) {
               case 1:
                  char_literal140 = (Token)this.match(this.input, 49, FOLLOW_ROOT_in_terminal2321);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_ROOT.add(char_literal140);
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
                  char_literal141 = (Token)this.match(this.input, 14, FOLLOW_BANG_in_terminal2342);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_BANG.add(char_literal141);
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
         } catch (RecognitionException var39) {
            this.reportError(var39);
            this.recover(this.input, var39);
            retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var39);
         }

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
      Token string_literal143 = null;
      ParserRuleReturnScope block142 = null;
      CommonTree op_tree = null;
      CommonTree string_literal143_tree = null;
      RewriteRuleTokenStream stream_78 = new RewriteRuleTokenStream(this.adaptor, "token 78");
      RewriteRuleTokenStream stream_70 = new RewriteRuleTokenStream(this.adaptor, "token 70");
      RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
      RewriteRuleTokenStream stream_80 = new RewriteRuleTokenStream(this.adaptor, "token 80");
      RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
      Token firstToken = this.input.LT(1);

      try {
         try {
            this.pushFollow(FOLLOW_block_in_ebnf2385);
            block142 = this.block();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_block.add(block142.getTree());
            }

            int alt69 = true;
            byte alt69;
            switch (this.input.LA(1)) {
               case 4:
               case 16:
               case 48:
               case 51:
               case 53:
               case 56:
               case 61:
               case 62:
               case 68:
               case 69:
               case 73:
               case 76:
               case 91:
               case 93:
                  alt69 = 5;
                  break;
               case 70:
                  alt69 = 2;
                  break;
               case 71:
                  alt69 = 3;
                  break;
               case 78:
                  alt69 = 4;
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

            CommonTree root_1;
            switch (alt69) {
               case 1:
                  op = (Token)this.match(this.input, 80, FOLLOW_80_in_ebnf2393);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_80.add(op);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(41, (Token)op)), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  op = (Token)this.match(this.input, 70, FOLLOW_70_in_ebnf2410);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_70.add(op);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(18, (Token)op)), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  op = (Token)this.match(this.input, 71, FOLLOW_71_in_ebnf2427);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_71.add(op);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(45, (Token)op)), root_1);
                     this.adaptor.addChild(root_1, stream_block.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 4:
                  string_literal143 = (Token)this.match(this.input, 78, FOLLOW_78_in_ebnf2444);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_78.add(string_literal143);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     if (this.gtype == 19 && Character.isUpperCase(((rule_scope)this.rule_stack.peek()).name.charAt(0))) {
                        root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(57, (String)"=>")), root_1);
                        this.adaptor.addChild(root_1, stream_block.nextTree());
                        this.adaptor.addChild(root_0, root_1);
                     } else {
                        this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(58, (String)"SYN_SEMPRED"));
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

   public final ebnfSuffix_return ebnfSuffix() throws RecognitionException {
      ebnfSuffix_return retval = new ebnfSuffix_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal144 = null;
      Token char_literal145 = null;
      Token char_literal146 = null;
      CommonTree char_literal144_tree = null;
      CommonTree char_literal145_tree = null;
      CommonTree char_literal146_tree = null;
      RewriteRuleTokenStream stream_70 = new RewriteRuleTokenStream(this.adaptor, "token 70");
      RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
      RewriteRuleTokenStream stream_80 = new RewriteRuleTokenStream(this.adaptor, "token 80");
      Token op = this.input.LT(1);

      try {
         try {
            int alt70 = true;
            byte alt70;
            switch (this.input.LA(1)) {
               case 70:
                  alt70 = 2;
                  break;
               case 71:
                  alt70 = 3;
                  break;
               case 80:
                  alt70 = 1;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 70, 0, this.input);
                  throw nvae;
            }

            switch (alt70) {
               case 1:
                  char_literal144 = (Token)this.match(this.input, 80, FOLLOW_80_in_ebnfSuffix2529);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_80.add(char_literal144);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(41, (Token)op));
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  char_literal145 = (Token)this.match(this.input, 70, FOLLOW_70_in_ebnfSuffix2541);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_70.add(char_literal145);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(18, (Token)op));
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  char_literal146 = (Token)this.match(this.input, 71, FOLLOW_71_in_ebnfSuffix2554);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_71.add(char_literal146);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(45, (Token)op));
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
            int alt72 = true;
            int LA72_0 = this.input.LA(1);
            byte alt72;
            if (LA72_0 == 48) {
               alt72 = 1;
            } else {
               if (LA72_0 != 69 && LA72_0 != 76 && LA72_0 != 91) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 72, 0, this.input);
                  throw nvae;
               }

               alt72 = 2;
            }

            label273:
            switch (alt72) {
               case 1:
                  while(true) {
                     int alt71 = 2;
                     int LA71_0 = this.input.LA(1);
                     if (LA71_0 == 48) {
                        int LA71_1 = this.input.LA(2);
                        if (LA71_1 == 53) {
                           alt71 = 1;
                        }
                     }

                     switch (alt71) {
                        case 1:
                           rew = (Token)this.match(this.input, 48, FOLLOW_REWRITE_in_rewrite2583);
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
                           preds = (Token)this.match(this.input, 53, FOLLOW_SEMPRED_in_rewrite2587);
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
                           this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite2591);
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
                           rew2 = (Token)this.match(this.input, 48, FOLLOW_REWRITE_in_rewrite2599);
                           if (this.state.failed) {
                              return retval;
                           }

                           if (this.state.backtracking == 0) {
                              stream_REWRITE.add(rew2);
                           }

                           this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite2603);
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
                           while(stream_predicated.hasNext() || stream_preds.hasNext() || stream_rew.hasNext()) {
                              root_1 = (CommonTree)this.adaptor.nil();
                              root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_rew.nextNode(), root_1);
                              this.adaptor.addChild(root_1, stream_preds.nextNode());
                              this.adaptor.addChild(root_1, stream_predicated.nextTree());
                              this.adaptor.addChild(root_0, root_1);
                           }

                           stream_predicated.reset();
                           stream_preds.reset();
                           stream_rew.reset();
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
      ParserRuleReturnScope rewrite_template147 = null;
      ParserRuleReturnScope rewrite_tree_alternative148 = null;

      try {
         try {
            byte alt73;
            int alt73 = true;
            int LA73_3;
            int nvaeMark;
            NoViableAltException nvae;
            int nvaeConsume;
            int nvaeMark;
            NoViableAltException nvae;
            label7692:
            switch (this.input.LA(1)) {
               case 4:
                  LA73_3 = this.input.LA(2);
                  if (this.synpred1_ANTLRv3()) {
                     alt73 = 1;
                  } else {
                     if (!this.synpred2_ANTLRv3()) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 73, 4, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt73 = 2;
                  }
                  break;
               case 16:
               case 56:
               case 62:
               case 67:
                  alt73 = 2;
                  break;
               case 48:
               case 69:
               case 76:
               case 91:
                  alt73 = 3;
                  break;
               case 51:
                  LA73_3 = this.input.LA(2);
                  if (LA73_3 == 68) {
                     switch (this.input.LA(3)) {
                        case 4:
                        case 16:
                        case 56:
                        case 62:
                        case 67:
                        case 68:
                           alt73 = 2;
                           break label7692;
                        case 51:
                           nvaeMark = this.input.LA(4);
                           if (nvaeMark == 33) {
                              alt73 = 1;
                              break label7692;
                           }

                           if (nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 51 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 67 || nvaeMark > 71) && nvaeMark != 80) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 73, 11, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt73 = 2;
                           break label7692;
                        case 61:
                           nvaeMark = this.input.LA(4);
                           if (nvaeMark == 33) {
                              alt73 = 1;
                              break label7692;
                           }

                           if (nvaeMark != 4 && nvaeMark != 11 && nvaeMark != 16 && nvaeMark != 51 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 67 || nvaeMark > 71) && nvaeMark != 80) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 73, 10, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt73 = 2;
                           break label7692;
                        case 69:
                           alt73 = 1;
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

                              nvae = new NoViableAltException("", 73, 7, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                     }
                  } else {
                     if (LA73_3 != 4 && LA73_3 != 16 && LA73_3 != 48 && LA73_3 != 51 && LA73_3 != 56 && (LA73_3 < 61 || LA73_3 > 62) && LA73_3 != 67 && (LA73_3 < 69 || LA73_3 > 71) && LA73_3 != 76 && LA73_3 != 80 && LA73_3 != 91) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 73, 2, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt73 = 2;
                     break;
                  }
               case 61:
                  LA73_3 = this.input.LA(2);
                  if (LA73_3 == 68) {
                     switch (this.input.LA(3)) {
                        case 4:
                        case 16:
                        case 56:
                        case 62:
                        case 67:
                        case 68:
                           alt73 = 2;
                           break label7692;
                        case 51:
                           nvaeMark = this.input.LA(4);
                           if (nvaeMark == 33) {
                              alt73 = 1;
                              break label7692;
                           }

                           if (nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 51 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 67 || nvaeMark > 71) && nvaeMark != 80) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 73, 11, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt73 = 2;
                           break label7692;
                        case 61:
                           nvaeMark = this.input.LA(4);
                           if (nvaeMark == 33) {
                              alt73 = 1;
                              break label7692;
                           }

                           if (nvaeMark != 4 && nvaeMark != 11 && nvaeMark != 16 && nvaeMark != 51 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 67 || nvaeMark > 71) && nvaeMark != 80) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 73, 10, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt73 = 2;
                           break label7692;
                        case 69:
                           alt73 = 1;
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

                              nvae = new NoViableAltException("", 73, 7, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                     }
                  }

                  if (LA73_3 != 4 && LA73_3 != 11 && LA73_3 != 16 && LA73_3 != 48 && LA73_3 != 51 && LA73_3 != 56 && (LA73_3 < 61 || LA73_3 > 62) && LA73_3 != 67 && (LA73_3 < 69 || LA73_3 > 71) && LA73_3 != 76 && LA73_3 != 80 && LA73_3 != 91) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 73, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt73 = 2;
                  break;
               case 68:
                  LA73_3 = this.input.LA(2);
                  if (LA73_3 == 4) {
                     nvaeMark = this.input.LA(3);
                     if (nvaeMark == 69) {
                        nvaeMark = this.input.LA(4);
                        if (nvaeMark == 68) {
                           alt73 = 1;
                           break;
                        }

                        if ((nvaeMark < 70 || nvaeMark > 71) && nvaeMark != 80) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvaeConsume = this.input.mark();

                           try {
                              for(int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                 this.input.consume();
                              }

                              NoViableAltException nvae = new NoViableAltException("", 73, 12, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeConsume);
                           }
                        }

                        alt73 = 2;
                        break;
                     }

                     if (nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 51 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 67 || nvaeMark > 68) && (nvaeMark < 70 || nvaeMark > 71) && nvaeMark != 80) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                              this.input.consume();
                           }

                           nvae = new NoViableAltException("", 73, 8, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt73 = 2;
                     break;
                  }

                  if (LA73_3 != 16 && LA73_3 != 51 && LA73_3 != 56 && (LA73_3 < 61 || LA73_3 > 62) && (LA73_3 < 67 || LA73_3 > 68)) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 73, 3, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt73 = 2;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 73, 0, this.input);
                  throw nvae;
            }

            switch (alt73) {
               case 1:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_template_in_rewrite_alternative2654);
                  rewrite_template147 = this.rewrite_template();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_template147.getTree());
                  }
                  break;
               case 2:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_alternative2659);
                  rewrite_tree_alternative148 = this.rewrite_tree_alternative();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_tree_alternative148.getTree());
                  }
                  break;
               case 3:
                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_1);
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(26, (String)"EPSILON"));
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(23, (String)"EOA"));
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
      Token char_literal150 = null;
      ParserRuleReturnScope rewrite_tree_alternative149 = null;
      CommonTree lp_tree = null;
      CommonTree char_literal150_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
      RewriteRuleSubtreeStream stream_rewrite_tree_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_alternative");

      try {
         try {
            lp = (Token)this.match(this.input, 68, FOLLOW_68_in_rewrite_tree_block2701);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_68.add(lp);
            }

            this.pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block2703);
            rewrite_tree_alternative149 = this.rewrite_tree_alternative();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_tree_alternative.add(rewrite_tree_alternative149.getTree());
            }

            char_literal150 = (Token)this.match(this.input, 69, FOLLOW_69_in_rewrite_tree_block2705);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_69.add(char_literal150);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(15, lp, "BLOCK")), root_1);
               this.adaptor.addChild(root_1, stream_rewrite_tree_alternative.nextTree());
               this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(24, lp, "EOB"));
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
      ParserRuleReturnScope rewrite_tree_element151 = null;
      RewriteRuleSubtreeStream stream_rewrite_tree_element = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_element");

      try {
         int cnt74 = 0;

         while(true) {
            int alt74 = 2;
            int LA74_0 = this.input.LA(1);
            if (LA74_0 == 4 || LA74_0 == 16 || LA74_0 == 51 || LA74_0 == 56 || LA74_0 >= 61 && LA74_0 <= 62 || LA74_0 >= 67 && LA74_0 <= 68) {
               alt74 = 1;
            }

            switch (alt74) {
               case 1:
                  this.pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative2739);
                  rewrite_tree_element151 = this.rewrite_tree_element();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_tree_element.add(rewrite_tree_element151.getTree());
                  }

                  ++cnt74;
                  break;
               default:
                  if (cnt74 < 1) {
                     if (this.state.backtracking > 0) {
                        this.state.failed = true;
                        return retval;
                     }

                     EarlyExitException eee = new EarlyExitException(74, this.input);
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
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(23, (String)"EOA"));
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
      ParserRuleReturnScope rewrite_tree_atom152 = null;
      ParserRuleReturnScope rewrite_tree_atom153 = null;
      ParserRuleReturnScope ebnfSuffix154 = null;
      ParserRuleReturnScope rewrite_tree155 = null;
      ParserRuleReturnScope ebnfSuffix156 = null;
      ParserRuleReturnScope rewrite_tree_ebnf157 = null;
      RewriteRuleSubtreeStream stream_rewrite_tree = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree");
      RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
      RewriteRuleSubtreeStream stream_rewrite_tree_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_atom");

      try {
         try {
            int LA75_0;
            byte alt76;
            NoViableAltException nvae;
            int alt76 = true;
            int nvaeMark;
            int nvaeMark;
            label6474:
            switch (this.input.LA(1)) {
               case 4:
                  nvaeMark = this.input.LA(2);
                  if (nvaeMark != -1 && nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 48 && nvaeMark != 51 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 67 || nvaeMark > 69) && nvaeMark != 76 && nvaeMark != 91) {
                     if ((nvaeMark < 70 || nvaeMark > 71) && nvaeMark != 80) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA75_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 76, 6, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA75_0);
                        }
                     }

                     alt76 = 2;
                  } else {
                     alt76 = 1;
                  }
                  break;
               case 16:
                  nvaeMark = this.input.LA(2);
                  if (nvaeMark != -1 && nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 48 && nvaeMark != 51 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 67 || nvaeMark > 69) && nvaeMark != 76 && nvaeMark != 91) {
                     if ((nvaeMark < 70 || nvaeMark > 71) && nvaeMark != 80) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA75_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 76, 1, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA75_0);
                        }
                     }

                     alt76 = 2;
                  } else {
                     alt76 = 1;
                  }
                  break;
               case 51:
                  nvaeMark = this.input.LA(2);
                  if (nvaeMark != -1 && nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 48 && nvaeMark != 51 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 67 || nvaeMark > 69) && nvaeMark != 76 && nvaeMark != 91) {
                     if ((nvaeMark < 70 || nvaeMark > 71) && nvaeMark != 80) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA75_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 76, 3, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA75_0);
                        }
                     }

                     alt76 = 2;
                  } else {
                     alt76 = 1;
                  }
                  break;
               case 56:
                  nvaeMark = this.input.LA(2);
                  if (nvaeMark != -1 && nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 48 && nvaeMark != 51 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 67 || nvaeMark > 69) && nvaeMark != 76 && nvaeMark != 91) {
                     if ((nvaeMark < 70 || nvaeMark > 71) && nvaeMark != 80) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA75_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 76, 4, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA75_0);
                        }
                     }

                     alt76 = 2;
                  } else {
                     alt76 = 1;
                  }
                  break;
               case 61:
                  switch (this.input.LA(2)) {
                     case -1:
                     case 4:
                     case 16:
                     case 48:
                     case 51:
                     case 56:
                     case 61:
                     case 62:
                     case 67:
                     case 68:
                     case 69:
                     case 76:
                     case 91:
                        alt76 = 1;
                        break label6474;
                     case 11:
                        nvaeMark = this.input.LA(3);
                        if (nvaeMark != -1 && nvaeMark != 4 && nvaeMark != 16 && nvaeMark != 48 && nvaeMark != 51 && nvaeMark != 56 && (nvaeMark < 61 || nvaeMark > 62) && (nvaeMark < 67 || nvaeMark > 69) && nvaeMark != 76 && nvaeMark != 91) {
                           if ((nvaeMark < 70 || nvaeMark > 71) && nvaeMark != 80) {
                              if (this.state.backtracking > 0) {
                                 this.state.failed = true;
                                 return retval;
                              }

                              LA75_0 = this.input.mark();

                              try {
                                 for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 76, 11, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(LA75_0);
                              }
                           }

                           alt76 = 2;
                        } else {
                           alt76 = 1;
                        }
                        break label6474;
                     case 70:
                     case 71:
                     case 80:
                        alt76 = 2;
                        break label6474;
                     default:
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvaeMark = this.input.mark();

                        try {
                           this.input.consume();
                           NoViableAltException nvae = new NoViableAltException("", 76, 2, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                  }
               case 62:
                  alt76 = 3;
                  break;
               case 67:
                  nvaeMark = this.input.LA(2);
                  int nvaeConsume;
                  NoViableAltException nvae;
                  if (nvaeMark == 61) {
                     LA75_0 = this.input.LA(3);
                     if (LA75_0 != -1 && LA75_0 != 4 && LA75_0 != 16 && LA75_0 != 48 && LA75_0 != 51 && LA75_0 != 56 && (LA75_0 < 61 || LA75_0 > 62) && (LA75_0 < 67 || LA75_0 > 69) && LA75_0 != 76 && LA75_0 != 91) {
                        if ((LA75_0 < 70 || LA75_0 > 71) && LA75_0 != 80) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 76, 12, this.input);
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
                     if (nvaeMark != 51) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        LA75_0 = this.input.mark();

                        try {
                           this.input.consume();
                           nvae = new NoViableAltException("", 76, 5, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(LA75_0);
                        }
                     }

                     LA75_0 = this.input.LA(3);
                     if (LA75_0 != -1 && LA75_0 != 4 && LA75_0 != 16 && LA75_0 != 48 && LA75_0 != 51 && LA75_0 != 56 && (LA75_0 < 61 || LA75_0 > 62) && (LA75_0 < 67 || LA75_0 > 69) && LA75_0 != 76 && LA75_0 != 91) {
                        if ((LA75_0 < 70 || LA75_0 > 71) && LA75_0 != 80) {
                           if (this.state.backtracking > 0) {
                              this.state.failed = true;
                              return retval;
                           }

                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 76, 13, this.input);
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
                  break;
               case 68:
                  alt76 = 4;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 76, 0, this.input);
                  throw nvae;
            }

            CommonTree root_1;
            label6306:
            switch (alt76) {
               case 1:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2767);
                  rewrite_tree_atom152 = this.rewrite_tree_atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_tree_atom152.getTree());
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2772);
                  rewrite_tree_atom153 = this.rewrite_tree_atom();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_tree_atom.add(rewrite_tree_atom153.getTree());
                  }

                  this.pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_element2774);
                  ebnfSuffix154 = this.ebnfSuffix();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_ebnfSuffix.add(ebnfSuffix154.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                     CommonTree root_2 = (CommonTree)this.adaptor.nil();
                     root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(15, (String)"BLOCK")), root_2);
                     root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_1);
                     this.adaptor.addChild(root_1, stream_rewrite_tree_atom.nextTree());
                     this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(23, (String)"EOA"));
                     this.adaptor.addChild(root_2, root_1);
                     this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(24, (String)"EOB"));
                     this.adaptor.addChild(root_1, root_2);
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 3:
                  this.pushFollow(FOLLOW_rewrite_tree_in_rewrite_tree_element2808);
                  rewrite_tree155 = this.rewrite_tree();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_tree.add(rewrite_tree155.getTree());
                  }

                  int alt75 = true;
                  LA75_0 = this.input.LA(1);
                  byte alt75;
                  if ((LA75_0 < 70 || LA75_0 > 71) && LA75_0 != 80) {
                     if (LA75_0 != -1 && LA75_0 != 4 && LA75_0 != 16 && LA75_0 != 48 && LA75_0 != 51 && LA75_0 != 56 && (LA75_0 < 61 || LA75_0 > 62) && (LA75_0 < 67 || LA75_0 > 69) && LA75_0 != 76 && LA75_0 != 91) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        nvae = new NoViableAltException("", 75, 0, this.input);
                        throw nvae;
                     }

                     alt75 = 2;
                  } else {
                     alt75 = 1;
                  }

                  switch (alt75) {
                     case 1:
                        this.pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_element2814);
                        ebnfSuffix156 = this.ebnfSuffix();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ebnfSuffix.add(ebnfSuffix156.getTree());
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ebnfSuffix.nextNode(), root_1);
                           CommonTree root_2 = (CommonTree)this.adaptor.nil();
                           root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(15, (String)"BLOCK")), root_2);
                           CommonTree root_3 = (CommonTree)this.adaptor.nil();
                           root_3 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(8, (String)"ALT")), root_3);
                           this.adaptor.addChild(root_3, stream_rewrite_tree.nextTree());
                           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(23, (String)"EOA"));
                           this.adaptor.addChild(root_2, root_3);
                           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(24, (String)"EOB"));
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
                  this.pushFollow(FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element2860);
                  rewrite_tree_ebnf157 = this.rewrite_tree_ebnf();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_tree_ebnf157.getTree());
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
      Token CHAR_LITERAL158 = null;
      Token TOKEN_REF159 = null;
      Token ARG_ACTION160 = null;
      Token RULE_REF161 = null;
      Token STRING_LITERAL162 = null;
      Token ACTION164 = null;
      ParserRuleReturnScope id163 = null;
      CommonTree d_tree = null;
      CommonTree CHAR_LITERAL158_tree = null;
      CommonTree TOKEN_REF159_tree = null;
      CommonTree ARG_ACTION160_tree = null;
      CommonTree RULE_REF161_tree = null;
      CommonTree STRING_LITERAL162_tree = null;
      CommonTree ACTION164_tree = null;
      RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
      RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         try {
            int alt78 = true;
            byte alt78;
            switch (this.input.LA(1)) {
               case 4:
                  alt78 = 6;
                  break;
               case 16:
                  alt78 = 1;
                  break;
               case 51:
                  alt78 = 3;
                  break;
               case 56:
                  alt78 = 4;
                  break;
               case 61:
                  alt78 = 2;
                  break;
               case 67:
                  alt78 = 5;
                  break;
               default:
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 78, 0, this.input);
                  throw nvae;
            }

            label275:
            switch (alt78) {
               case 1:
                  root_0 = (CommonTree)this.adaptor.nil();
                  CHAR_LITERAL158 = (Token)this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom2876);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     CHAR_LITERAL158_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL158);
                     this.adaptor.addChild(root_0, CHAR_LITERAL158_tree);
                  }
                  break;
               case 2:
                  TOKEN_REF159 = (Token)this.match(this.input, 61, FOLLOW_TOKEN_REF_in_rewrite_tree_atom2883);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_TOKEN_REF.add(TOKEN_REF159);
                  }

                  int alt77 = 2;
                  int LA77_0 = this.input.LA(1);
                  if (LA77_0 == 11) {
                     alt77 = 1;
                  }

                  switch (alt77) {
                     case 1:
                        ARG_ACTION160 = (Token)this.match(this.input, 11, FOLLOW_ARG_ACTION_in_rewrite_tree_atom2885);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_ARG_ACTION.add(ARG_ACTION160);
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
                  RULE_REF161 = (Token)this.match(this.input, 51, FOLLOW_RULE_REF_in_rewrite_tree_atom2906);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     RULE_REF161_tree = (CommonTree)this.adaptor.create(RULE_REF161);
                     this.adaptor.addChild(root_0, RULE_REF161_tree);
                  }
                  break;
               case 4:
                  root_0 = (CommonTree)this.adaptor.nil();
                  STRING_LITERAL162 = (Token)this.match(this.input, 56, FOLLOW_STRING_LITERAL_in_rewrite_tree_atom2913);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     STRING_LITERAL162_tree = (CommonTree)this.adaptor.create(STRING_LITERAL162);
                     this.adaptor.addChild(root_0, STRING_LITERAL162_tree);
                  }
                  break;
               case 5:
                  d = (Token)this.match(this.input, 67, FOLLOW_67_in_rewrite_tree_atom2922);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_67.add(d);
                  }

                  this.pushFollow(FOLLOW_id_in_rewrite_tree_atom2924);
                  id163 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(id163.getTree());
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(32, d, id163 != null ? this.input.toString(id163.start, id163.stop) : null));
                     retval.tree = root_0;
                  }
                  break;
               case 6:
                  root_0 = (CommonTree)this.adaptor.nil();
                  ACTION164 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_tree_atom2935);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION164_tree = (CommonTree)this.adaptor.create(ACTION164);
                     this.adaptor.addChild(root_0, ACTION164_tree);
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
      ParserRuleReturnScope rewrite_tree_block165 = null;
      ParserRuleReturnScope ebnfSuffix166 = null;
      RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
      RewriteRuleSubtreeStream stream_rewrite_tree_block = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_block");
      Token firstToken = this.input.LT(1);

      try {
         try {
            this.pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf2956);
            rewrite_tree_block165 = this.rewrite_tree_block();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_tree_block.add(rewrite_tree_block165.getTree());
            }

            this.pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_ebnf2958);
            ebnfSuffix166 = this.ebnfSuffix();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ebnfSuffix.add(ebnfSuffix166.getTree());
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
      Token string_literal167 = null;
      Token char_literal170 = null;
      ParserRuleReturnScope rewrite_tree_atom168 = null;
      ParserRuleReturnScope rewrite_tree_element169 = null;
      CommonTree string_literal167_tree = null;
      CommonTree char_literal170_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleTokenStream stream_TREE_BEGIN = new RewriteRuleTokenStream(this.adaptor, "token TREE_BEGIN");
      RewriteRuleSubtreeStream stream_rewrite_tree_element = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_element");
      RewriteRuleSubtreeStream stream_rewrite_tree_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_atom");

      try {
         string_literal167 = (Token)this.match(this.input, 62, FOLLOW_TREE_BEGIN_in_rewrite_tree2978);
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               stream_TREE_BEGIN.add(string_literal167);
            }

            this.pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree2980);
            rewrite_tree_atom168 = this.rewrite_tree_atom();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            } else {
               if (this.state.backtracking == 0) {
                  stream_rewrite_tree_atom.add(rewrite_tree_atom168.getTree());
               }

               while(true) {
                  int alt79 = 2;
                  int LA79_0 = this.input.LA(1);
                  if (LA79_0 == 4 || LA79_0 == 16 || LA79_0 == 51 || LA79_0 == 56 || LA79_0 >= 61 && LA79_0 <= 62 || LA79_0 >= 67 && LA79_0 <= 68) {
                     alt79 = 1;
                  }

                  switch (alt79) {
                     case 1:
                        this.pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree2982);
                        rewrite_tree_element169 = this.rewrite_tree_element();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_rewrite_tree_element.add(rewrite_tree_element169.getTree());
                        }
                        break;
                     default:
                        char_literal170 = (Token)this.match(this.input, 69, FOLLOW_69_in_rewrite_tree2985);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_69.add(char_literal170);
                        }

                        if (this.state.backtracking == 0) {
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(62, (String)"TREE_BEGIN")), root_1);
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
      Token char_literal173 = null;
      Token ACTION176 = null;
      ParserRuleReturnScope id171 = null;
      ParserRuleReturnScope rewrite_template_args172 = null;
      ParserRuleReturnScope rewrite_template_ref174 = null;
      ParserRuleReturnScope rewrite_indirect_template_head175 = null;
      CommonTree lp_tree = null;
      CommonTree str_tree = null;
      CommonTree char_literal173_tree = null;
      CommonTree ACTION176_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
      RewriteRuleTokenStream stream_DOUBLE_QUOTE_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_QUOTE_STRING_LITERAL");
      RewriteRuleTokenStream stream_DOUBLE_ANGLE_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_ANGLE_STRING_LITERAL");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");

      try {
         try {
            int alt81 = true;
            int alt81 = this.dfa81.predict(this.input);
            switch (alt81) {
               case 1:
                  this.pushFollow(FOLLOW_id_in_rewrite_template3017);
                  id171 = this.id();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_id.add(id171.getTree());
                  }

                  lp = (Token)this.match(this.input, 68, FOLLOW_68_in_rewrite_template3021);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_68.add(lp);
                  }

                  this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template3023);
                  rewrite_template_args172 = this.rewrite_template_args();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_rewrite_template_args.add(rewrite_template_args172.getTree());
                  }

                  char_literal173 = (Token)this.match(this.input, 69, FOLLOW_69_in_rewrite_template3025);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_69.add(char_literal173);
                  }

                  int alt80 = true;
                  int LA80_0 = this.input.LA(1);
                  byte alt80;
                  if (LA80_0 == 22) {
                     alt80 = 1;
                  } else {
                     if (LA80_0 != 21) {
                        if (this.state.backtracking > 0) {
                           this.state.failed = true;
                           return retval;
                        }

                        NoViableAltException nvae = new NoViableAltException("", 80, 0, this.input);
                        throw nvae;
                     }

                     alt80 = 2;
                  }

                  switch (alt80) {
                     case 1:
                        str = (Token)this.match(this.input, 22, FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template3033);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_DOUBLE_QUOTE_STRING_LITERAL.add(str);
                        }
                        break;
                     case 2:
                        str = (Token)this.match(this.input, 21, FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template3039);
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
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(59, lp, "TEMPLATE")), root_1);
                     this.adaptor.addChild(root_1, stream_id.nextTree());
                     this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
                     this.adaptor.addChild(root_1, stream_str.nextNode());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_template_ref_in_rewrite_template3066);
                  rewrite_template_ref174 = this.rewrite_template_ref();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_template_ref174.getTree());
                  }
                  break;
               case 3:
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.pushFollow(FOLLOW_rewrite_indirect_template_head_in_rewrite_template3075);
                  rewrite_indirect_template_head175 = this.rewrite_indirect_template_head();
                  --this.state._fsp;
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     this.adaptor.addChild(root_0, rewrite_indirect_template_head175.getTree());
                  }
                  break;
               case 4:
                  root_0 = (CommonTree)this.adaptor.nil();
                  ACTION176 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template3084);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     ACTION176_tree = (CommonTree)this.adaptor.create(ACTION176);
                     this.adaptor.addChild(root_0, ACTION176_tree);
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
      Token char_literal179 = null;
      ParserRuleReturnScope id177 = null;
      ParserRuleReturnScope rewrite_template_args178 = null;
      CommonTree lp_tree = null;
      CommonTree char_literal179_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
      RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");

      try {
         try {
            this.pushFollow(FOLLOW_id_in_rewrite_template_ref3097);
            id177 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id177.getTree());
            }

            lp = (Token)this.match(this.input, 68, FOLLOW_68_in_rewrite_template_ref3101);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_68.add(lp);
            }

            this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template_ref3103);
            rewrite_template_args178 = this.rewrite_template_args();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_template_args.add(rewrite_template_args178.getTree());
            }

            char_literal179 = (Token)this.match(this.input, 69, FOLLOW_69_in_rewrite_template_ref3105);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_69.add(char_literal179);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(59, lp, "TEMPLATE")), root_1);
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
      Token ACTION180 = null;
      Token char_literal181 = null;
      Token char_literal182 = null;
      Token char_literal184 = null;
      ParserRuleReturnScope rewrite_template_args183 = null;
      CommonTree lp_tree = null;
      CommonTree ACTION180_tree = null;
      CommonTree char_literal181_tree = null;
      CommonTree char_literal182_tree = null;
      CommonTree char_literal184_tree = null;
      RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
      RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");

      try {
         try {
            lp = (Token)this.match(this.input, 68, FOLLOW_68_in_rewrite_indirect_template_head3133);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_68.add(lp);
            }

            ACTION180 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_indirect_template_head3135);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION180);
            }

            char_literal181 = (Token)this.match(this.input, 69, FOLLOW_69_in_rewrite_indirect_template_head3137);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_69.add(char_literal181);
            }

            char_literal182 = (Token)this.match(this.input, 68, FOLLOW_68_in_rewrite_indirect_template_head3139);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_68.add(char_literal182);
            }

            this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3141);
            rewrite_template_args183 = this.rewrite_template_args();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_rewrite_template_args.add(rewrite_template_args183.getTree());
            }

            char_literal184 = (Token)this.match(this.input, 69, FOLLOW_69_in_rewrite_indirect_template_head3143);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_69.add(char_literal184);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(59, lp, "TEMPLATE")), root_1);
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
      Token char_literal186 = null;
      ParserRuleReturnScope rewrite_template_arg185 = null;
      ParserRuleReturnScope rewrite_template_arg187 = null;
      CommonTree char_literal186_tree = null;
      RewriteRuleTokenStream stream_72 = new RewriteRuleTokenStream(this.adaptor, "token 72");
      RewriteRuleSubtreeStream stream_rewrite_template_arg = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_arg");

      try {
         try {
            int alt83 = true;
            int LA83_0 = this.input.LA(1);
            byte alt83;
            if (LA83_0 != 51 && LA83_0 != 61) {
               if (LA83_0 != 69) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 83, 0, this.input);
                  throw nvae;
               }

               alt83 = 2;
            } else {
               alt83 = 1;
            }

            label204: {
               switch (alt83) {
                  case 1:
                     this.pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args3167);
                     rewrite_template_arg185 = this.rewrite_template_arg();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        stream_rewrite_template_arg.add(rewrite_template_arg185.getTree());
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
                  int alt82 = 2;
                  int LA82_0 = this.input.LA(1);
                  if (LA82_0 == 72) {
                     alt82 = 1;
                  }

                  switch (alt82) {
                     case 1:
                        char_literal186 = (Token)this.match(this.input, 72, FOLLOW_72_in_rewrite_template_args3170);
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_72.add(char_literal186);
                        }

                        this.pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args3172);
                        rewrite_template_arg187 = this.rewrite_template_arg();
                        --this.state._fsp;
                        if (this.state.failed) {
                           return retval;
                        }

                        if (this.state.backtracking == 0) {
                           stream_rewrite_template_arg.add(rewrite_template_arg187.getTree());
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
      Token char_literal189 = null;
      Token ACTION190 = null;
      ParserRuleReturnScope id188 = null;
      CommonTree char_literal189_tree = null;
      CommonTree ACTION190_tree = null;
      RewriteRuleTokenStream stream_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LABEL_ASSIGN");
      RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
      RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");

      try {
         try {
            this.pushFollow(FOLLOW_id_in_rewrite_template_arg3205);
            id188 = this.id();
            --this.state._fsp;
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_id.add(id188.getTree());
            }

            char_literal189 = (Token)this.match(this.input, 33, FOLLOW_LABEL_ASSIGN_in_rewrite_template_arg3207);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_LABEL_ASSIGN.add(char_literal189);
            }

            ACTION190 = (Token)this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template_arg3209);
            if (this.state.failed) {
               return retval;
            }

            if (this.state.backtracking == 0) {
               stream_ACTION.add(ACTION190);
            }

            if (this.state.backtracking == 0) {
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(9, (Token)(id188 != null ? id188.start : null))), root_1);
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

   public final qid_return qid() throws RecognitionException {
      qid_return retval = new qid_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token char_literal192 = null;
      ParserRuleReturnScope id191 = null;
      ParserRuleReturnScope id193 = null;
      CommonTree char_literal192_tree = null;

      try {
         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_id_in_qid3230);
         id191 = this.id();
         --this.state._fsp;
         if (this.state.failed) {
            return retval;
         } else {
            if (this.state.backtracking == 0) {
               this.adaptor.addChild(root_0, id191.getTree());
            }

            while(true) {
               int alt84 = 2;
               int LA84_0 = this.input.LA(1);
               if (LA84_0 == 73) {
                  alt84 = 1;
               }

               switch (alt84) {
                  case 1:
                     char_literal192 = (Token)this.match(this.input, 73, FOLLOW_73_in_qid3233);
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        char_literal192_tree = (CommonTree)this.adaptor.create(char_literal192);
                        this.adaptor.addChild(root_0, char_literal192_tree);
                     }

                     this.pushFollow(FOLLOW_id_in_qid3235);
                     id193 = this.id();
                     --this.state._fsp;
                     if (this.state.failed) {
                        return retval;
                     }

                     if (this.state.backtracking == 0) {
                        this.adaptor.addChild(root_0, id193.getTree());
                     }
                     break;
                  default:
                     retval.stop = this.input.LT(-1);
                     if (this.state.backtracking == 0) {
                        retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                        this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     }

                     return retval;
               }
            }
         }
      } catch (RecognitionException var13) {
         this.reportError(var13);
         this.recover(this.input, var13);
         retval.tree = (CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), var13);
         return retval;
      } finally {
         ;
      }
   }

   public final id_return id() throws RecognitionException {
      id_return retval = new id_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      Token TOKEN_REF194 = null;
      Token RULE_REF195 = null;
      CommonTree TOKEN_REF194_tree = null;
      CommonTree RULE_REF195_tree = null;
      RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
      RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");

      try {
         try {
            int alt85 = true;
            int LA85_0 = this.input.LA(1);
            byte alt85;
            if (LA85_0 == 61) {
               alt85 = 1;
            } else {
               if (LA85_0 != 51) {
                  if (this.state.backtracking > 0) {
                     this.state.failed = true;
                     return retval;
                  }

                  NoViableAltException nvae = new NoViableAltException("", 85, 0, this.input);
                  throw nvae;
               }

               alt85 = 2;
            }

            switch (alt85) {
               case 1:
                  TOKEN_REF194 = (Token)this.match(this.input, 61, FOLLOW_TOKEN_REF_in_id3247);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_TOKEN_REF.add(TOKEN_REF194);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(30, (Token)TOKEN_REF194));
                     retval.tree = root_0;
                  }
                  break;
               case 2:
                  RULE_REF195 = (Token)this.match(this.input, 51, FOLLOW_RULE_REF_in_id3257);
                  if (this.state.failed) {
                     return retval;
                  }

                  if (this.state.backtracking == 0) {
                     stream_RULE_REF.add(RULE_REF195);
                  }

                  if (this.state.backtracking == 0) {
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(30, (Token)RULE_REF195));
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
      this.pushFollow(FOLLOW_rewrite_template_in_synpred1_ANTLRv32654);
      this.rewrite_template();
      --this.state._fsp;
      if (!this.state.failed) {
         ;
      }
   }

   public final void synpred2_ANTLRv3_fragment() throws RecognitionException {
      this.pushFollow(FOLLOW_rewrite_tree_alternative_in_synpred2_ANTLRv32659);
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
      int numStates = DFA81_transitionS.length;
      DFA81_transition = new short[numStates][];

      for(int i = 0; i < numStates; ++i) {
         DFA81_transition[i] = DFA.unpackEncodedString(DFA81_transitionS[i]);
      }

      FOLLOW_DOC_COMMENT_in_grammarDef373 = new BitSet(new long[]{0L, 70778880L});
      FOLLOW_84_in_grammarDef383 = new BitSet(new long[]{0L, 524288L});
      FOLLOW_85_in_grammarDef401 = new BitSet(new long[]{0L, 524288L});
      FOLLOW_90_in_grammarDef417 = new BitSet(new long[]{0L, 524288L});
      FOLLOW_83_in_grammarDef458 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_grammarDef460 = new BitSet(new long[]{0L, 4096L});
      FOLLOW_76_in_grammarDef462 = new BitSet(new long[]{3465524311577595904L, 29360128L});
      FOLLOW_optionsSpec_in_grammarDef464 = new BitSet(new long[]{3465519913531084800L, 29360128L});
      FOLLOW_tokensSpec_in_grammarDef467 = new BitSet(new long[]{2312598408924237824L, 29360128L});
      FOLLOW_attrScope_in_grammarDef470 = new BitSet(new long[]{2312598408924237824L, 29360128L});
      FOLLOW_action_in_grammarDef473 = new BitSet(new long[]{2308094809296867328L, 29360128L});
      FOLLOW_rule_in_grammarDef481 = new BitSet(new long[]{2308094809296863232L, 29360128L});
      FOLLOW_EOF_in_grammarDef489 = new BitSet(new long[]{2L});
      FOLLOW_TOKENS_in_tokensSpec550 = new BitSet(new long[]{2305843009213693952L});
      FOLLOW_tokenSpec_in_tokensSpec552 = new BitSet(new long[]{2305843009213693952L, 268435456L});
      FOLLOW_92_in_tokensSpec555 = new BitSet(new long[]{2L});
      FOLLOW_TOKEN_REF_in_tokenSpec575 = new BitSet(new long[]{8589934592L, 4096L});
      FOLLOW_LABEL_ASSIGN_in_tokenSpec581 = new BitSet(new long[]{72057594037993472L});
      FOLLOW_STRING_LITERAL_in_tokenSpec586 = new BitSet(new long[]{0L, 4096L});
      FOLLOW_CHAR_LITERAL_in_tokenSpec590 = new BitSet(new long[]{0L, 4096L});
      FOLLOW_76_in_tokenSpec629 = new BitSet(new long[]{2L});
      FOLLOW_SCOPE_in_attrScope640 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_attrScope642 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_attrScope644 = new BitSet(new long[]{2L});
      FOLLOW_AT_in_action667 = new BitSet(new long[]{2308094809027379200L, 3145728L});
      FOLLOW_actionScopeName_in_action670 = new BitSet(new long[]{0L, 2048L});
      FOLLOW_75_in_action672 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_action676 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_action678 = new BitSet(new long[]{2L});
      FOLLOW_id_in_actionScopeName704 = new BitSet(new long[]{2L});
      FOLLOW_84_in_actionScopeName711 = new BitSet(new long[]{2L});
      FOLLOW_85_in_actionScopeName728 = new BitSet(new long[]{2L});
      FOLLOW_OPTIONS_in_optionsSpec744 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_option_in_optionsSpec747 = new BitSet(new long[]{0L, 4096L});
      FOLLOW_76_in_optionsSpec749 = new BitSet(new long[]{2308094809027379200L, 268435456L});
      FOLLOW_92_in_optionsSpec753 = new BitSet(new long[]{2L});
      FOLLOW_id_in_option778 = new BitSet(new long[]{8589934592L});
      FOLLOW_LABEL_ASSIGN_in_option780 = new BitSet(new long[]{2380152405212856320L, 64L});
      FOLLOW_optionValue_in_option782 = new BitSet(new long[]{2L});
      FOLLOW_qid_in_optionValue811 = new BitSet(new long[]{2L});
      FOLLOW_STRING_LITERAL_in_optionValue821 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_optionValue831 = new BitSet(new long[]{2L});
      FOLLOW_INT_in_optionValue841 = new BitSet(new long[]{2L});
      FOLLOW_70_in_optionValue851 = new BitSet(new long[]{2L});
      FOLLOW_DOC_COMMENT_in_rule876 = new BitSet(new long[]{2308094809295814656L, 29360128L});
      FOLLOW_87_in_rule886 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_88_in_rule888 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_86_in_rule890 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_FRAGMENT_in_rule892 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_rule900 = new BitSet(new long[]{4648735162259456L, 33555456L});
      FOLLOW_BANG_in_rule906 = new BitSet(new long[]{4648735162243072L, 33555456L});
      FOLLOW_ARG_ACTION_in_rule915 = new BitSet(new long[]{4648735162241024L, 33555456L});
      FOLLOW_RET_in_rule924 = new BitSet(new long[]{2048L});
      FOLLOW_ARG_ACTION_in_rule928 = new BitSet(new long[]{4507997673885696L, 33555456L});
      FOLLOW_throwsSpec_in_rule936 = new BitSet(new long[]{4507997673885696L, 1024L});
      FOLLOW_optionsSpec_in_rule939 = new BitSet(new long[]{4503599627374592L, 1024L});
      FOLLOW_ruleScopeSpec_in_rule942 = new BitSet(new long[]{4096L, 1024L});
      FOLLOW_ruleAction_in_rule945 = new BitSet(new long[]{4096L, 1024L});
      FOLLOW_74_in_rule950 = new BitSet(new long[]{7001127095724212240L, 536871440L});
      FOLLOW_altList_in_rule952 = new BitSet(new long[]{0L, 4096L});
      FOLLOW_76_in_rule954 = new BitSet(new long[]{2L, 393216L});
      FOLLOW_exceptionGroup_in_rule958 = new BitSet(new long[]{2L});
      FOLLOW_AT_in_ruleAction1064 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_ruleAction1066 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_ruleAction1068 = new BitSet(new long[]{2L});
      FOLLOW_89_in_throwsSpec1089 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_throwsSpec1091 = new BitSet(new long[]{2L, 256L});
      FOLLOW_72_in_throwsSpec1095 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_throwsSpec1097 = new BitSet(new long[]{2L, 256L});
      FOLLOW_SCOPE_in_ruleScopeSpec1120 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_ruleScopeSpec1122 = new BitSet(new long[]{2L});
      FOLLOW_SCOPE_in_ruleScopeSpec1135 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_ruleScopeSpec1137 = new BitSet(new long[]{0L, 4352L});
      FOLLOW_72_in_ruleScopeSpec1140 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_ruleScopeSpec1142 = new BitSet(new long[]{0L, 4352L});
      FOLLOW_76_in_ruleScopeSpec1146 = new BitSet(new long[]{2L});
      FOLLOW_SCOPE_in_ruleScopeSpec1160 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_ruleScopeSpec1162 = new BitSet(new long[]{4503599627370496L});
      FOLLOW_SCOPE_in_ruleScopeSpec1166 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_ruleScopeSpec1168 = new BitSet(new long[]{0L, 4352L});
      FOLLOW_72_in_ruleScopeSpec1171 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_ruleScopeSpec1173 = new BitSet(new long[]{0L, 4352L});
      FOLLOW_76_in_ruleScopeSpec1177 = new BitSet(new long[]{2L});
      FOLLOW_68_in_block1209 = new BitSet(new long[]{7001131493770723344L, 536872464L});
      FOLLOW_optionsSpec_in_block1218 = new BitSet(new long[]{0L, 1024L});
      FOLLOW_74_in_block1222 = new BitSet(new long[]{7001127095724212240L, 536871440L});
      FOLLOW_altpair_in_block1229 = new BitSet(new long[]{0L, 134217760L});
      FOLLOW_91_in_block1233 = new BitSet(new long[]{7001127095724212240L, 536871440L});
      FOLLOW_altpair_in_block1235 = new BitSet(new long[]{0L, 134217760L});
      FOLLOW_69_in_block1250 = new BitSet(new long[]{2L});
      FOLLOW_alternative_in_altpair1289 = new BitSet(new long[]{281474976710656L});
      FOLLOW_rewrite_in_altpair1291 = new BitSet(new long[]{2L});
      FOLLOW_altpair_in_altList1311 = new BitSet(new long[]{2L, 134217728L});
      FOLLOW_91_in_altList1315 = new BitSet(new long[]{7001127095724212240L, 536871440L});
      FOLLOW_altpair_in_altList1317 = new BitSet(new long[]{2L, 134217728L});
      FOLLOW_element_in_alternative1358 = new BitSet(new long[]{7000845620747501586L, 536871440L});
      FOLLOW_exceptionHandler_in_exceptionGroup1409 = new BitSet(new long[]{2L, 393216L});
      FOLLOW_finallyClause_in_exceptionGroup1416 = new BitSet(new long[]{2L});
      FOLLOW_finallyClause_in_exceptionGroup1424 = new BitSet(new long[]{2L});
      FOLLOW_81_in_exceptionHandler1444 = new BitSet(new long[]{2048L});
      FOLLOW_ARG_ACTION_in_exceptionHandler1446 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_exceptionHandler1448 = new BitSet(new long[]{2L});
      FOLLOW_82_in_finallyClause1478 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_finallyClause1480 = new BitSet(new long[]{2L});
      FOLLOW_id_in_element1502 = new BitSet(new long[]{77309411328L});
      FOLLOW_LABEL_ASSIGN_in_element1507 = new BitSet(new long[]{2380152403065372672L, 536871424L});
      FOLLOW_LIST_LABEL_ASSIGN_in_element1511 = new BitSet(new long[]{2380152403065372672L, 536871424L});
      FOLLOW_atom_in_element1514 = new BitSet(new long[]{2L, 65728L});
      FOLLOW_ebnfSuffix_in_element1520 = new BitSet(new long[]{2L});
      FOLLOW_id_in_element1579 = new BitSet(new long[]{77309411328L});
      FOLLOW_LABEL_ASSIGN_in_element1584 = new BitSet(new long[]{0L, 16L});
      FOLLOW_LIST_LABEL_ASSIGN_in_element1588 = new BitSet(new long[]{0L, 16L});
      FOLLOW_block_in_element1591 = new BitSet(new long[]{2L, 65728L});
      FOLLOW_ebnfSuffix_in_element1597 = new BitSet(new long[]{2L});
      FOLLOW_atom_in_element1656 = new BitSet(new long[]{2L, 65728L});
      FOLLOW_ebnfSuffix_in_element1662 = new BitSet(new long[]{2L});
      FOLLOW_ebnf_in_element1708 = new BitSet(new long[]{2L});
      FOLLOW_ACTION_in_element1715 = new BitSet(new long[]{2L});
      FOLLOW_SEMPRED_in_element1722 = new BitSet(new long[]{2L, 16384L});
      FOLLOW_78_in_element1728 = new BitSet(new long[]{2L});
      FOLLOW_treeSpec_in_element1748 = new BitSet(new long[]{2L, 65728L});
      FOLLOW_ebnfSuffix_in_element1754 = new BitSet(new long[]{2L});
      FOLLOW_terminal_in_atom1806 = new BitSet(new long[]{2L});
      FOLLOW_range_in_atom1811 = new BitSet(new long[]{562949953437698L});
      FOLLOW_ROOT_in_atom1821 = new BitSet(new long[]{2L});
      FOLLOW_BANG_in_atom1825 = new BitSet(new long[]{2L});
      FOLLOW_notSet_in_atom1859 = new BitSet(new long[]{562949953437698L});
      FOLLOW_ROOT_in_atom1868 = new BitSet(new long[]{2L});
      FOLLOW_BANG_in_atom1872 = new BitSet(new long[]{2L});
      FOLLOW_RULE_REF_in_atom1908 = new BitSet(new long[]{562949953439746L});
      FOLLOW_ARG_ACTION_in_atom1910 = new BitSet(new long[]{562949953437698L});
      FOLLOW_ROOT_in_atom1920 = new BitSet(new long[]{2L});
      FOLLOW_BANG_in_atom1924 = new BitSet(new long[]{2L});
      FOLLOW_93_in_notSet1972 = new BitSet(new long[]{2377900603251687424L, 16L});
      FOLLOW_notTerminal_in_notSet1978 = new BitSet(new long[]{2L, 8192L});
      FOLLOW_elementOptions_in_notSet1980 = new BitSet(new long[]{2L});
      FOLLOW_block_in_notSet1998 = new BitSet(new long[]{2L, 8192L});
      FOLLOW_elementOptions_in_notSet2000 = new BitSet(new long[]{2L});
      FOLLOW_77_in_elementOptions2052 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_qid_in_elementOptions2054 = new BitSet(new long[]{0L, 32768L});
      FOLLOW_79_in_elementOptions2056 = new BitSet(new long[]{2L});
      FOLLOW_77_in_elementOptions2074 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_option_in_elementOptions2076 = new BitSet(new long[]{0L, 36864L});
      FOLLOW_76_in_elementOptions2079 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_option_in_elementOptions2081 = new BitSet(new long[]{0L, 36864L});
      FOLLOW_79_in_elementOptions2085 = new BitSet(new long[]{2L});
      FOLLOW_id_in_elementOption2105 = new BitSet(new long[]{8589934592L});
      FOLLOW_LABEL_ASSIGN_in_elementOption2107 = new BitSet(new long[]{2380152405212856320L, 64L});
      FOLLOW_optionValue_in_elementOption2109 = new BitSet(new long[]{2L});
      FOLLOW_TREE_BEGIN_in_treeSpec2131 = new BitSet(new long[]{7000845620747501584L, 536871440L});
      FOLLOW_element_in_treeSpec2133 = new BitSet(new long[]{7000845620747501584L, 536871440L});
      FOLLOW_element_in_treeSpec2137 = new BitSet(new long[]{7000845620747501584L, 536871472L});
      FOLLOW_69_in_treeSpec2142 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_range2165 = new BitSet(new long[]{70368744177664L});
      FOLLOW_RANGE_in_range2167 = new BitSet(new long[]{65536L});
      FOLLOW_CHAR_LITERAL_in_range2171 = new BitSet(new long[]{2L, 8192L});
      FOLLOW_elementOptions_in_range2173 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_terminal2210 = new BitSet(new long[]{562949953437698L, 8192L});
      FOLLOW_elementOptions_in_terminal2212 = new BitSet(new long[]{562949953437698L});
      FOLLOW_TOKEN_REF_in_terminal2243 = new BitSet(new long[]{562949953439746L, 8192L});
      FOLLOW_ARG_ACTION_in_terminal2245 = new BitSet(new long[]{562949953437698L, 8192L});
      FOLLOW_elementOptions_in_terminal2248 = new BitSet(new long[]{562949953437698L});
      FOLLOW_STRING_LITERAL_in_terminal2269 = new BitSet(new long[]{562949953437698L, 8192L});
      FOLLOW_elementOptions_in_terminal2271 = new BitSet(new long[]{562949953437698L});
      FOLLOW_73_in_terminal2292 = new BitSet(new long[]{562949953437698L, 8192L});
      FOLLOW_elementOptions_in_terminal2294 = new BitSet(new long[]{562949953437698L});
      FOLLOW_ROOT_in_terminal2321 = new BitSet(new long[]{2L});
      FOLLOW_BANG_in_terminal2342 = new BitSet(new long[]{2L});
      FOLLOW_block_in_ebnf2385 = new BitSet(new long[]{2L, 82112L});
      FOLLOW_80_in_ebnf2393 = new BitSet(new long[]{2L});
      FOLLOW_70_in_ebnf2410 = new BitSet(new long[]{2L});
      FOLLOW_71_in_ebnf2427 = new BitSet(new long[]{2L});
      FOLLOW_78_in_ebnf2444 = new BitSet(new long[]{2L});
      FOLLOW_80_in_ebnfSuffix2529 = new BitSet(new long[]{2L});
      FOLLOW_70_in_ebnfSuffix2541 = new BitSet(new long[]{2L});
      FOLLOW_71_in_ebnfSuffix2554 = new BitSet(new long[]{2L});
      FOLLOW_REWRITE_in_rewrite2583 = new BitSet(new long[]{9007199254740992L});
      FOLLOW_SEMPRED_in_rewrite2587 = new BitSet(new long[]{6992119896469471248L, 24L});
      FOLLOW_rewrite_alternative_in_rewrite2591 = new BitSet(new long[]{281474976710656L});
      FOLLOW_REWRITE_in_rewrite2599 = new BitSet(new long[]{6991838421492760592L, 24L});
      FOLLOW_rewrite_alternative_in_rewrite2603 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_template_in_rewrite_alternative2654 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_alternative_in_rewrite_alternative2659 = new BitSet(new long[]{2L});
      FOLLOW_68_in_rewrite_tree_block2701 = new BitSet(new long[]{6991838421492760592L, 24L});
      FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block2703 = new BitSet(new long[]{0L, 32L});
      FOLLOW_69_in_rewrite_tree_block2705 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative2739 = new BitSet(new long[]{6991838421492760594L, 24L});
      FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2767 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2772 = new BitSet(new long[]{0L, 65728L});
      FOLLOW_ebnfSuffix_in_rewrite_tree_element2774 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_in_rewrite_tree_element2808 = new BitSet(new long[]{2L, 65728L});
      FOLLOW_ebnfSuffix_in_rewrite_tree_element2814 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element2860 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom2876 = new BitSet(new long[]{2L});
      FOLLOW_TOKEN_REF_in_rewrite_tree_atom2883 = new BitSet(new long[]{2050L});
      FOLLOW_ARG_ACTION_in_rewrite_tree_atom2885 = new BitSet(new long[]{2L});
      FOLLOW_RULE_REF_in_rewrite_tree_atom2906 = new BitSet(new long[]{2L});
      FOLLOW_STRING_LITERAL_in_rewrite_tree_atom2913 = new BitSet(new long[]{2L});
      FOLLOW_67_in_rewrite_tree_atom2922 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_rewrite_tree_atom2924 = new BitSet(new long[]{2L});
      FOLLOW_ACTION_in_rewrite_tree_atom2935 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf2956 = new BitSet(new long[]{0L, 65728L});
      FOLLOW_ebnfSuffix_in_rewrite_tree_ebnf2958 = new BitSet(new long[]{2L});
      FOLLOW_TREE_BEGIN_in_rewrite_tree2978 = new BitSet(new long[]{2380152403065372688L, 8L});
      FOLLOW_rewrite_tree_atom_in_rewrite_tree2980 = new BitSet(new long[]{6991838421492760592L, 56L});
      FOLLOW_rewrite_tree_element_in_rewrite_tree2982 = new BitSet(new long[]{6991838421492760592L, 56L});
      FOLLOW_69_in_rewrite_tree2985 = new BitSet(new long[]{2L});
      FOLLOW_id_in_rewrite_template3017 = new BitSet(new long[]{0L, 16L});
      FOLLOW_68_in_rewrite_template3021 = new BitSet(new long[]{2308094809027379200L, 32L});
      FOLLOW_rewrite_template_args_in_rewrite_template3023 = new BitSet(new long[]{0L, 32L});
      FOLLOW_69_in_rewrite_template3025 = new BitSet(new long[]{6291456L});
      FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template3033 = new BitSet(new long[]{2L});
      FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template3039 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_template_ref_in_rewrite_template3066 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_indirect_template_head_in_rewrite_template3075 = new BitSet(new long[]{2L});
      FOLLOW_ACTION_in_rewrite_template3084 = new BitSet(new long[]{2L});
      FOLLOW_id_in_rewrite_template_ref3097 = new BitSet(new long[]{0L, 16L});
      FOLLOW_68_in_rewrite_template_ref3101 = new BitSet(new long[]{2308094809027379200L, 32L});
      FOLLOW_rewrite_template_args_in_rewrite_template_ref3103 = new BitSet(new long[]{0L, 32L});
      FOLLOW_69_in_rewrite_template_ref3105 = new BitSet(new long[]{2L});
      FOLLOW_68_in_rewrite_indirect_template_head3133 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_rewrite_indirect_template_head3135 = new BitSet(new long[]{0L, 32L});
      FOLLOW_69_in_rewrite_indirect_template_head3137 = new BitSet(new long[]{0L, 16L});
      FOLLOW_68_in_rewrite_indirect_template_head3139 = new BitSet(new long[]{2308094809027379200L, 32L});
      FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3141 = new BitSet(new long[]{0L, 32L});
      FOLLOW_69_in_rewrite_indirect_template_head3143 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_template_arg_in_rewrite_template_args3167 = new BitSet(new long[]{2L, 256L});
      FOLLOW_72_in_rewrite_template_args3170 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_rewrite_template_arg_in_rewrite_template_args3172 = new BitSet(new long[]{2L, 256L});
      FOLLOW_id_in_rewrite_template_arg3205 = new BitSet(new long[]{8589934592L});
      FOLLOW_LABEL_ASSIGN_in_rewrite_template_arg3207 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_rewrite_template_arg3209 = new BitSet(new long[]{2L});
      FOLLOW_id_in_qid3230 = new BitSet(new long[]{2L, 512L});
      FOLLOW_73_in_qid3233 = new BitSet(new long[]{2308094809027379200L});
      FOLLOW_id_in_qid3235 = new BitSet(new long[]{2L, 512L});
      FOLLOW_TOKEN_REF_in_id3247 = new BitSet(new long[]{2L});
      FOLLOW_RULE_REF_in_id3257 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_template_in_synpred1_ANTLRv32654 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_alternative_in_synpred2_ANTLRv32659 = new BitSet(new long[]{2L});
   }

   protected class DFA81 extends DFA {
      public DFA81(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 81;
         this.eot = ANTLRv3Parser.DFA81_eot;
         this.eof = ANTLRv3Parser.DFA81_eof;
         this.min = ANTLRv3Parser.DFA81_min;
         this.max = ANTLRv3Parser.DFA81_max;
         this.accept = ANTLRv3Parser.DFA81_accept;
         this.special = ANTLRv3Parser.DFA81_special;
         this.transition = ANTLRv3Parser.DFA81_transition;
      }

      public String getDescription() {
         return "425:1: rewrite_template : ( id lp= '(' rewrite_template_args ')' (str= DOUBLE_QUOTE_STRING_LITERAL |str= DOUBLE_ANGLE_STRING_LITERAL ) -> ^( TEMPLATE[$lp,\"TEMPLATE\"] id rewrite_template_args $str) | rewrite_template_ref | rewrite_indirect_template_head | ACTION );";
      }
   }

   public static class id_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class qid_return extends ParserRuleReturnScope {
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

   public static class ebnf_return extends ParserRuleReturnScope {
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

   public static class treeSpec_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class elementOption_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class elementOptions_return extends ParserRuleReturnScope {
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

   public static class altpair_return extends ParserRuleReturnScope {
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
