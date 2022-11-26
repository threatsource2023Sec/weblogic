package org.antlr.grammar.v3;

import org.antlr.runtime.BaseRecognizer;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.DFA;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;

public class ANTLRv3Tree extends TreeParser {
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
   protected DFA48 dfa48;
   static final String DFA48_eotS = "\u0010\uffff";
   static final String DFA48_eofS = "\u0010\uffff";
   static final String DFA48_minS = "\u0001\u0004\u0001\u0002\u0001\uffff\u0001\u0004\u0001\n\u0001\uffff\u0001\u0002\u0001\t\u0002\uffff\u0001\u0002\u0001\u001e\u0001\u0004\u0003\u0003";
   static final String DFA48_maxS = "\u0001;\u0001\u0002\u0001\uffff\u0001\u001e\u0001\n\u0001\uffff\u0001\u0016\u0001\t\u0002\uffff\u0001\u0002\u0001\u001e\u0001\u0004\u0001\u0003\u0001\t\u0001\u0016";
   static final String DFA48_acceptS = "\u0002\uffff\u0001\u0004\u0002\uffff\u0001\u0003\u0002\uffff\u0001\u0001\u0001\u0002\u0006\uffff";
   static final String DFA48_specialS = "\u0010\uffff}>";
   static final String[] DFA48_transitionS = new String[]{"\u0001\u00026\uffff\u0001\u0001", "\u0001\u0003", "", "\u0001\u0005\u0019\uffff\u0001\u0004", "\u0001\u0006", "", "\u0001\u0007\u0001\t\u0011\uffff\u0002\b", "\u0001\n", "", "", "\u0001\u000b", "\u0001\f", "\u0001\r", "\u0001\u000e", "\u0001\u000f\u0005\uffff\u0001\n", "\u0001\t\u0011\uffff\u0002\b"};
   static final short[] DFA48_eot = DFA.unpackEncodedString("\u0010\uffff");
   static final short[] DFA48_eof = DFA.unpackEncodedString("\u0010\uffff");
   static final char[] DFA48_min = DFA.unpackEncodedStringToUnsignedChars("\u0001\u0004\u0001\u0002\u0001\uffff\u0001\u0004\u0001\n\u0001\uffff\u0001\u0002\u0001\t\u0002\uffff\u0001\u0002\u0001\u001e\u0001\u0004\u0003\u0003");
   static final char[] DFA48_max = DFA.unpackEncodedStringToUnsignedChars("\u0001;\u0001\u0002\u0001\uffff\u0001\u001e\u0001\n\u0001\uffff\u0001\u0016\u0001\t\u0002\uffff\u0001\u0002\u0001\u001e\u0001\u0004\u0001\u0003\u0001\t\u0001\u0016");
   static final short[] DFA48_accept = DFA.unpackEncodedString("\u0002\uffff\u0001\u0004\u0002\uffff\u0001\u0003\u0002\uffff\u0001\u0001\u0001\u0002\u0006\uffff");
   static final short[] DFA48_special = DFA.unpackEncodedString("\u0010\uffff}>");
   static final short[][] DFA48_transition;
   public static final BitSet FOLLOW_grammarType_in_grammarDef58;
   public static final BitSet FOLLOW_ID_in_grammarDef60;
   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarDef62;
   public static final BitSet FOLLOW_optionsSpec_in_grammarDef65;
   public static final BitSet FOLLOW_tokensSpec_in_grammarDef68;
   public static final BitSet FOLLOW_attrScope_in_grammarDef71;
   public static final BitSet FOLLOW_action_in_grammarDef74;
   public static final BitSet FOLLOW_rule_in_grammarDef77;
   public static final BitSet FOLLOW_TOKENS_in_tokensSpec133;
   public static final BitSet FOLLOW_tokenSpec_in_tokensSpec135;
   public static final BitSet FOLLOW_LABEL_ASSIGN_in_tokenSpec149;
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec151;
   public static final BitSet FOLLOW_STRING_LITERAL_in_tokenSpec153;
   public static final BitSet FOLLOW_LABEL_ASSIGN_in_tokenSpec160;
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec162;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_tokenSpec164;
   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec170;
   public static final BitSet FOLLOW_SCOPE_in_attrScope182;
   public static final BitSet FOLLOW_ID_in_attrScope184;
   public static final BitSet FOLLOW_ACTION_in_attrScope186;
   public static final BitSet FOLLOW_AT_in_action199;
   public static final BitSet FOLLOW_ID_in_action201;
   public static final BitSet FOLLOW_ID_in_action203;
   public static final BitSet FOLLOW_ACTION_in_action205;
   public static final BitSet FOLLOW_AT_in_action212;
   public static final BitSet FOLLOW_ID_in_action214;
   public static final BitSet FOLLOW_ACTION_in_action216;
   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec229;
   public static final BitSet FOLLOW_option_in_optionsSpec231;
   public static final BitSet FOLLOW_qid_in_option249;
   public static final BitSet FOLLOW_LABEL_ASSIGN_in_option259;
   public static final BitSet FOLLOW_ID_in_option261;
   public static final BitSet FOLLOW_optionValue_in_option263;
   public static final BitSet FOLLOW_RULE_in_rule329;
   public static final BitSet FOLLOW_ID_in_rule331;
   public static final BitSet FOLLOW_modifier_in_rule333;
   public static final BitSet FOLLOW_ARG_in_rule338;
   public static final BitSet FOLLOW_ARG_ACTION_in_rule340;
   public static final BitSet FOLLOW_RET_in_rule347;
   public static final BitSet FOLLOW_ARG_ACTION_in_rule349;
   public static final BitSet FOLLOW_throwsSpec_in_rule362;
   public static final BitSet FOLLOW_optionsSpec_in_rule365;
   public static final BitSet FOLLOW_ruleScopeSpec_in_rule368;
   public static final BitSet FOLLOW_ruleAction_in_rule371;
   public static final BitSet FOLLOW_altList_in_rule382;
   public static final BitSet FOLLOW_exceptionGroup_in_rule392;
   public static final BitSet FOLLOW_EOR_in_rule395;
   public static final BitSet FOLLOW_AT_in_ruleAction434;
   public static final BitSet FOLLOW_ID_in_ruleAction436;
   public static final BitSet FOLLOW_ACTION_in_ruleAction438;
   public static final BitSet FOLLOW_89_in_throwsSpec451;
   public static final BitSet FOLLOW_ID_in_throwsSpec453;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec467;
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec469;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec476;
   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec478;
   public static final BitSet FOLLOW_ID_in_ruleScopeSpec480;
   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec488;
   public static final BitSet FOLLOW_ID_in_ruleScopeSpec490;
   public static final BitSet FOLLOW_BLOCK_in_block510;
   public static final BitSet FOLLOW_optionsSpec_in_block512;
   public static final BitSet FOLLOW_alternative_in_block516;
   public static final BitSet FOLLOW_rewrite_in_block518;
   public static final BitSet FOLLOW_EOB_in_block522;
   public static final BitSet FOLLOW_BLOCK_in_altList545;
   public static final BitSet FOLLOW_alternative_in_altList548;
   public static final BitSet FOLLOW_rewrite_in_altList550;
   public static final BitSet FOLLOW_EOB_in_altList554;
   public static final BitSet FOLLOW_ALT_in_alternative576;
   public static final BitSet FOLLOW_element_in_alternative578;
   public static final BitSet FOLLOW_EOA_in_alternative581;
   public static final BitSet FOLLOW_ALT_in_alternative593;
   public static final BitSet FOLLOW_EPSILON_in_alternative595;
   public static final BitSet FOLLOW_EOA_in_alternative597;
   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup612;
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup615;
   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup621;
   public static final BitSet FOLLOW_81_in_exceptionHandler642;
   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler644;
   public static final BitSet FOLLOW_ACTION_in_exceptionHandler646;
   public static final BitSet FOLLOW_82_in_finallyClause668;
   public static final BitSet FOLLOW_ACTION_in_finallyClause670;
   public static final BitSet FOLLOW_set_in_element686;
   public static final BitSet FOLLOW_ID_in_element692;
   public static final BitSet FOLLOW_block_in_element694;
   public static final BitSet FOLLOW_set_in_element701;
   public static final BitSet FOLLOW_ID_in_element707;
   public static final BitSet FOLLOW_atom_in_element709;
   public static final BitSet FOLLOW_atom_in_element715;
   public static final BitSet FOLLOW_ebnf_in_element720;
   public static final BitSet FOLLOW_ACTION_in_element727;
   public static final BitSet FOLLOW_SEMPRED_in_element734;
   public static final BitSet FOLLOW_GATED_SEMPRED_in_element739;
   public static final BitSet FOLLOW_TREE_BEGIN_in_element747;
   public static final BitSet FOLLOW_element_in_element749;
   public static final BitSet FOLLOW_set_in_atom763;
   public static final BitSet FOLLOW_atom_in_atom769;
   public static final BitSet FOLLOW_CHAR_RANGE_in_atom776;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom778;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom780;
   public static final BitSet FOLLOW_optionsSpec_in_atom782;
   public static final BitSet FOLLOW_93_in_atom790;
   public static final BitSet FOLLOW_notTerminal_in_atom792;
   public static final BitSet FOLLOW_optionsSpec_in_atom794;
   public static final BitSet FOLLOW_93_in_atom802;
   public static final BitSet FOLLOW_block_in_atom804;
   public static final BitSet FOLLOW_optionsSpec_in_atom806;
   public static final BitSet FOLLOW_RULE_REF_in_atom817;
   public static final BitSet FOLLOW_ARG_ACTION_in_atom819;
   public static final BitSet FOLLOW_RULE_REF_in_atom828;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom838;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom849;
   public static final BitSet FOLLOW_optionsSpec_in_atom851;
   public static final BitSet FOLLOW_TOKEN_REF_in_atom860;
   public static final BitSet FOLLOW_TOKEN_REF_in_atom869;
   public static final BitSet FOLLOW_optionsSpec_in_atom871;
   public static final BitSet FOLLOW_TOKEN_REF_in_atom881;
   public static final BitSet FOLLOW_ARG_ACTION_in_atom883;
   public static final BitSet FOLLOW_optionsSpec_in_atom885;
   public static final BitSet FOLLOW_TOKEN_REF_in_atom895;
   public static final BitSet FOLLOW_ARG_ACTION_in_atom897;
   public static final BitSet FOLLOW_STRING_LITERAL_in_atom906;
   public static final BitSet FOLLOW_STRING_LITERAL_in_atom915;
   public static final BitSet FOLLOW_optionsSpec_in_atom917;
   public static final BitSet FOLLOW_73_in_atom926;
   public static final BitSet FOLLOW_73_in_atom935;
   public static final BitSet FOLLOW_optionsSpec_in_atom937;
   public static final BitSet FOLLOW_SYNPRED_in_ebnf956;
   public static final BitSet FOLLOW_block_in_ebnf958;
   public static final BitSet FOLLOW_OPTIONAL_in_ebnf965;
   public static final BitSet FOLLOW_block_in_ebnf967;
   public static final BitSet FOLLOW_CLOSURE_in_ebnf976;
   public static final BitSet FOLLOW_block_in_ebnf978;
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_ebnf988;
   public static final BitSet FOLLOW_block_in_ebnf990;
   public static final BitSet FOLLOW_SYN_SEMPRED_in_ebnf996;
   public static final BitSet FOLLOW_block_in_ebnf1001;
   public static final BitSet FOLLOW_REWRITE_in_rewrite1041;
   public static final BitSet FOLLOW_SEMPRED_in_rewrite1043;
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite1045;
   public static final BitSet FOLLOW_REWRITE_in_rewrite1051;
   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite1053;
   public static final BitSet FOLLOW_rewrite_template_in_rewrite_alternative1068;
   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_alternative1073;
   public static final BitSet FOLLOW_ALT_in_rewrite_alternative1084;
   public static final BitSet FOLLOW_EPSILON_in_rewrite_alternative1086;
   public static final BitSet FOLLOW_EOA_in_rewrite_alternative1088;
   public static final BitSet FOLLOW_BLOCK_in_rewrite_tree_block1107;
   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block1109;
   public static final BitSet FOLLOW_EOB_in_rewrite_tree_block1111;
   public static final BitSet FOLLOW_ALT_in_rewrite_tree_alternative1130;
   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative1132;
   public static final BitSet FOLLOW_EOA_in_rewrite_tree_alternative1135;
   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree_element1150;
   public static final BitSet FOLLOW_rewrite_tree_in_rewrite_tree_element1155;
   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_element1162;
   public static final BitSet FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element1169;
   public static final BitSet FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom1185;
   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_tree_atom1192;
   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_tree_atom1200;
   public static final BitSet FOLLOW_ARG_ACTION_in_rewrite_tree_atom1202;
   public static final BitSet FOLLOW_RULE_REF_in_rewrite_tree_atom1214;
   public static final BitSet FOLLOW_STRING_LITERAL_in_rewrite_tree_atom1221;
   public static final BitSet FOLLOW_LABEL_in_rewrite_tree_atom1228;
   public static final BitSet FOLLOW_ACTION_in_rewrite_tree_atom1233;
   public static final BitSet FOLLOW_OPTIONAL_in_rewrite_tree_ebnf1245;
   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1247;
   public static final BitSet FOLLOW_CLOSURE_in_rewrite_tree_ebnf1256;
   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1258;
   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_rewrite_tree_ebnf1268;
   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1270;
   public static final BitSet FOLLOW_TREE_BEGIN_in_rewrite_tree1284;
   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree1286;
   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree1288;
   public static final BitSet FOLLOW_TEMPLATE_in_rewrite_template1306;
   public static final BitSet FOLLOW_ID_in_rewrite_template1308;
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template1310;
   public static final BitSet FOLLOW_set_in_rewrite_template1317;
   public static final BitSet FOLLOW_rewrite_template_ref_in_rewrite_template1333;
   public static final BitSet FOLLOW_rewrite_indirect_template_head_in_rewrite_template1338;
   public static final BitSet FOLLOW_ACTION_in_rewrite_template1343;
   public static final BitSet FOLLOW_TEMPLATE_in_rewrite_template_ref1357;
   public static final BitSet FOLLOW_ID_in_rewrite_template_ref1359;
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template_ref1361;
   public static final BitSet FOLLOW_TEMPLATE_in_rewrite_indirect_template_head1376;
   public static final BitSet FOLLOW_ACTION_in_rewrite_indirect_template_head1378;
   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head1380;
   public static final BitSet FOLLOW_ARGLIST_in_rewrite_template_args1393;
   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args1395;
   public static final BitSet FOLLOW_ARGLIST_in_rewrite_template_args1402;
   public static final BitSet FOLLOW_ARG_in_rewrite_template_arg1416;
   public static final BitSet FOLLOW_ID_in_rewrite_template_arg1418;
   public static final BitSet FOLLOW_ACTION_in_rewrite_template_arg1420;
   public static final BitSet FOLLOW_ID_in_qid1431;
   public static final BitSet FOLLOW_73_in_qid1434;
   public static final BitSet FOLLOW_ID_in_qid1436;

   public TreeParser[] getDelegates() {
      return new TreeParser[0];
   }

   public ANTLRv3Tree(TreeNodeStream input) {
      this(input, new RecognizerSharedState());
   }

   public ANTLRv3Tree(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
      this.dfa48 = new DFA48(this);
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\antlr\\grammar\\v3\\ANTLRv3Tree.g";
   }

   public final void grammarDef() throws RecognitionException {
      try {
         this.pushFollow(FOLLOW_grammarType_in_grammarDef58);
         this.grammarType();
         --this.state._fsp;
         this.match(this.input, 2, (BitSet)null);
         this.match(this.input, 30, FOLLOW_ID_in_grammarDef60);
         int alt1 = 2;
         int LA1_0 = this.input.LA(1);
         if (LA1_0 == 20) {
            alt1 = 1;
         }

         switch (alt1) {
            case 1:
               this.match(this.input, 20, FOLLOW_DOC_COMMENT_in_grammarDef62);
            default:
               int alt2 = 2;
               int LA2_0 = this.input.LA(1);
               if (LA2_0 == 42) {
                  alt2 = 1;
               }

               switch (alt2) {
                  case 1:
                     this.pushFollow(FOLLOW_optionsSpec_in_grammarDef65);
                     this.optionsSpec();
                     --this.state._fsp;
                  default:
                     int alt3 = 2;
                     int LA3_0 = this.input.LA(1);
                     if (LA3_0 == 60) {
                        alt3 = 1;
                     }

                     switch (alt3) {
                        case 1:
                           this.pushFollow(FOLLOW_tokensSpec_in_grammarDef68);
                           this.tokensSpec();
                           --this.state._fsp;
                     }
               }
         }

         while(true) {
            int cnt6 = 2;
            int LA5_0 = this.input.LA(1);
            if (LA5_0 == 52) {
               cnt6 = 1;
            }

            switch (cnt6) {
               case 1:
                  this.pushFollow(FOLLOW_attrScope_in_grammarDef71);
                  this.attrScope();
                  --this.state._fsp;
                  break;
               default:
                  while(true) {
                     cnt6 = 2;
                     LA5_0 = this.input.LA(1);
                     if (LA5_0 == 12) {
                        cnt6 = 1;
                     }

                     switch (cnt6) {
                        case 1:
                           this.pushFollow(FOLLOW_action_in_grammarDef74);
                           this.action();
                           --this.state._fsp;
                           break;
                        default:
                           cnt6 = 0;

                           while(true) {
                              int alt6 = 2;
                              int LA6_0 = this.input.LA(1);
                              if (LA6_0 == 50) {
                                 alt6 = 1;
                              }

                              switch (alt6) {
                                 case 1:
                                    this.pushFollow(FOLLOW_rule_in_grammarDef77);
                                    this.rule();
                                    --this.state._fsp;
                                    ++cnt6;
                                    break;
                                 default:
                                    if (cnt6 < 1) {
                                       EarlyExitException eee = new EarlyExitException(6, this.input);
                                       throw eee;
                                    }

                                    this.match(this.input, 3, (BitSet)null);
                                    return;
                              }
                           }
                     }
                  }
            }
         }
      } catch (RecognitionException var14) {
         this.reportError(var14);
         this.recover(this.input, var14);
      } finally {
         ;
      }
   }

   public final void grammarType() throws RecognitionException {
      try {
         try {
            if (this.input.LA(1) != 19 && this.input.LA(1) != 35 && this.input.LA(1) != 44 && this.input.LA(1) != 63) {
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

   public final void tokensSpec() throws RecognitionException {
      try {
         this.match(this.input, 60, FOLLOW_TOKENS_in_tokensSpec133);
         this.match(this.input, 2, (BitSet)null);
         int cnt7 = 0;

         while(true) {
            int alt7 = 2;
            int LA7_0 = this.input.LA(1);
            if (LA7_0 == 33 || LA7_0 == 61) {
               alt7 = 1;
            }

            switch (alt7) {
               case 1:
                  this.pushFollow(FOLLOW_tokenSpec_in_tokensSpec135);
                  this.tokenSpec();
                  --this.state._fsp;
                  ++cnt7;
                  break;
               default:
                  if (cnt7 < 1) {
                     EarlyExitException eee = new EarlyExitException(7, this.input);
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

   public final void tokenSpec() throws RecognitionException {
      try {
         try {
            int alt8 = true;
            int LA8_0 = this.input.LA(1);
            byte alt8;
            if (LA8_0 == 33) {
               int LA8_1 = this.input.LA(2);
               int nvaeMark;
               if (LA8_1 != 2) {
                  nvaeMark = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 8, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               nvaeMark = this.input.LA(3);
               int LA8_4;
               int nvaeMark;
               if (nvaeMark != 61) {
                  LA8_4 = this.input.mark();

                  try {
                     for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                        this.input.consume();
                     }

                     NoViableAltException nvae = new NoViableAltException("", 8, 3, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(LA8_4);
                  }
               }

               LA8_4 = this.input.LA(4);
               if (LA8_4 == 56) {
                  alt8 = 1;
               } else {
                  if (LA8_4 != 16) {
                     nvaeMark = this.input.mark();

                     try {
                        for(int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                           this.input.consume();
                        }

                        NoViableAltException nvae = new NoViableAltException("", 8, 4, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt8 = 2;
               }
            } else {
               if (LA8_0 != 61) {
                  NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
                  throw nvae;
               }

               alt8 = 3;
            }

            switch (alt8) {
               case 1:
                  this.match(this.input, 33, FOLLOW_LABEL_ASSIGN_in_tokenSpec149);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 61, FOLLOW_TOKEN_REF_in_tokenSpec151);
                  this.match(this.input, 56, FOLLOW_STRING_LITERAL_in_tokenSpec153);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 33, FOLLOW_LABEL_ASSIGN_in_tokenSpec160);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 61, FOLLOW_TOKEN_REF_in_tokenSpec162);
                  this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_tokenSpec164);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 3:
                  this.match(this.input, 61, FOLLOW_TOKEN_REF_in_tokenSpec170);
            }
         } catch (RecognitionException var35) {
            this.reportError(var35);
            this.recover(this.input, var35);
         }

      } finally {
         ;
      }
   }

   public final void attrScope() throws RecognitionException {
      try {
         try {
            this.match(this.input, 52, FOLLOW_SCOPE_in_attrScope182);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 30, FOLLOW_ID_in_attrScope184);
            this.match(this.input, 4, FOLLOW_ACTION_in_attrScope186);
            this.match(this.input, 3, (BitSet)null);
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void action() throws RecognitionException {
      try {
         try {
            int alt9 = true;
            int LA9_0 = this.input.LA(1);
            if (LA9_0 != 12) {
               NoViableAltException nvae = new NoViableAltException("", 9, 0, this.input);
               throw nvae;
            }

            int LA9_1 = this.input.LA(2);
            int nvaeMark;
            if (LA9_1 != 2) {
               nvaeMark = this.input.mark();

               try {
                  this.input.consume();
                  NoViableAltException nvae = new NoViableAltException("", 9, 1, this.input);
                  throw nvae;
               } finally {
                  this.input.rewind(nvaeMark);
               }
            }

            nvaeMark = this.input.LA(3);
            int LA9_3;
            int nvaeMark;
            if (nvaeMark != 30) {
               LA9_3 = this.input.mark();

               try {
                  for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                     this.input.consume();
                  }

                  NoViableAltException nvae = new NoViableAltException("", 9, 2, this.input);
                  throw nvae;
               } finally {
                  this.input.rewind(LA9_3);
               }
            }

            LA9_3 = this.input.LA(4);
            byte alt9;
            if (LA9_3 == 30) {
               alt9 = 1;
            } else {
               if (LA9_3 != 4) {
                  nvaeMark = this.input.mark();

                  try {
                     for(int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                        this.input.consume();
                     }

                     NoViableAltException nvae = new NoViableAltException("", 9, 3, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               alt9 = 2;
            }

            switch (alt9) {
               case 1:
                  this.match(this.input, 12, FOLLOW_AT_in_action199);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 30, FOLLOW_ID_in_action201);
                  this.match(this.input, 30, FOLLOW_ID_in_action203);
                  this.match(this.input, 4, FOLLOW_ACTION_in_action205);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 12, FOLLOW_AT_in_action212);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 30, FOLLOW_ID_in_action214);
                  this.match(this.input, 4, FOLLOW_ACTION_in_action216);
                  this.match(this.input, 3, (BitSet)null);
            }
         } catch (RecognitionException var35) {
            this.reportError(var35);
            this.recover(this.input, var35);
         }

      } finally {
         ;
      }
   }

   public final void optionsSpec() throws RecognitionException {
      try {
         this.match(this.input, 42, FOLLOW_OPTIONS_in_optionsSpec229);
         this.match(this.input, 2, (BitSet)null);
         int cnt10 = 0;

         while(true) {
            int alt10 = 2;
            int LA10_0 = this.input.LA(1);
            if (LA10_0 == 30 || LA10_0 == 33) {
               alt10 = 1;
            }

            switch (alt10) {
               case 1:
                  this.pushFollow(FOLLOW_option_in_optionsSpec231);
                  this.option();
                  --this.state._fsp;
                  ++cnt10;
                  break;
               default:
                  if (cnt10 < 1) {
                     EarlyExitException eee = new EarlyExitException(10, this.input);
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

   public final void option() throws RecognitionException {
      try {
         try {
            int alt11 = true;
            int LA11_0 = this.input.LA(1);
            byte alt11;
            if (LA11_0 == 30) {
               alt11 = 1;
            } else {
               if (LA11_0 != 33) {
                  NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
                  throw nvae;
               }

               alt11 = 2;
            }

            switch (alt11) {
               case 1:
                  this.pushFollow(FOLLOW_qid_in_option249);
                  this.qid();
                  --this.state._fsp;
                  break;
               case 2:
                  this.match(this.input, 33, FOLLOW_LABEL_ASSIGN_in_option259);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 30, FOLLOW_ID_in_option261);
                  this.pushFollow(FOLLOW_optionValue_in_option263);
                  this.optionValue();
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

   public final void optionValue() throws RecognitionException {
      try {
         try {
            if (this.input.LA(1) != 16 && (this.input.LA(1) < 30 || this.input.LA(1) > 31) && this.input.LA(1) != 56) {
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

   public final void rule() throws RecognitionException {
      try {
         this.match(this.input, 50, FOLLOW_RULE_in_rule329);
         this.match(this.input, 2, (BitSet)null);
         this.match(this.input, 30, FOLLOW_ID_in_rule331);
         int alt12 = 2;
         int LA12_0 = this.input.LA(1);
         if (LA12_0 == 28 || LA12_0 >= 86 && LA12_0 <= 88) {
            alt12 = 1;
         }

         switch (alt12) {
            case 1:
               this.pushFollow(FOLLOW_modifier_in_rule333);
               this.modifier();
               --this.state._fsp;
            default:
               int alt13 = 2;
               int LA13_0 = this.input.LA(1);
               if (LA13_0 == 9) {
                  alt13 = 1;
               }

               switch (alt13) {
                  case 1:
                     this.match(this.input, 9, FOLLOW_ARG_in_rule338);
                     this.match(this.input, 2, (BitSet)null);
                     this.match(this.input, 11, FOLLOW_ARG_ACTION_in_rule340);
                     this.match(this.input, 3, (BitSet)null);
                  default:
                     int alt14 = 2;
                     int LA14_0 = this.input.LA(1);
                     if (LA14_0 == 47) {
                        alt14 = 1;
                     }

                     switch (alt14) {
                        case 1:
                           this.match(this.input, 47, FOLLOW_RET_in_rule347);
                           this.match(this.input, 2, (BitSet)null);
                           this.match(this.input, 11, FOLLOW_ARG_ACTION_in_rule349);
                           this.match(this.input, 3, (BitSet)null);
                        default:
                           int alt15 = 2;
                           int LA15_0 = this.input.LA(1);
                           if (LA15_0 == 89) {
                              alt15 = 1;
                           }

                           switch (alt15) {
                              case 1:
                                 this.pushFollow(FOLLOW_throwsSpec_in_rule362);
                                 this.throwsSpec();
                                 --this.state._fsp;
                              default:
                                 int alt16 = 2;
                                 int LA16_0 = this.input.LA(1);
                                 if (LA16_0 == 42) {
                                    alt16 = 1;
                                 }

                                 switch (alt16) {
                                    case 1:
                                       this.pushFollow(FOLLOW_optionsSpec_in_rule365);
                                       this.optionsSpec();
                                       --this.state._fsp;
                                    default:
                                       int alt17 = 2;
                                       int LA17_0 = this.input.LA(1);
                                       if (LA17_0 == 52) {
                                          alt17 = 1;
                                       }

                                       switch (alt17) {
                                          case 1:
                                             this.pushFollow(FOLLOW_ruleScopeSpec_in_rule368);
                                             this.ruleScopeSpec();
                                             --this.state._fsp;
                                       }
                                 }
                           }
                     }
               }
         }

         while(true) {
            int alt19 = 2;
            int LA19_0 = this.input.LA(1);
            if (LA19_0 == 12) {
               alt19 = 1;
            }

            switch (alt19) {
               case 1:
                  this.pushFollow(FOLLOW_ruleAction_in_rule371);
                  this.ruleAction();
                  --this.state._fsp;
                  break;
               default:
                  this.pushFollow(FOLLOW_altList_in_rule382);
                  this.altList();
                  --this.state._fsp;
                  alt19 = 2;
                  LA19_0 = this.input.LA(1);
                  if (LA19_0 >= 81 && LA19_0 <= 82) {
                     alt19 = 1;
                  }

                  switch (alt19) {
                     case 1:
                        this.pushFollow(FOLLOW_exceptionGroup_in_rule392);
                        this.exceptionGroup();
                        --this.state._fsp;
                     default:
                        this.match(this.input, 25, FOLLOW_EOR_in_rule395);
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

   public final void modifier() throws RecognitionException {
      try {
         try {
            if (this.input.LA(1) != 28 && (this.input.LA(1) < 86 || this.input.LA(1) > 88)) {
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

   public final void ruleAction() throws RecognitionException {
      try {
         try {
            this.match(this.input, 12, FOLLOW_AT_in_ruleAction434);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 30, FOLLOW_ID_in_ruleAction436);
            this.match(this.input, 4, FOLLOW_ACTION_in_ruleAction438);
            this.match(this.input, 3, (BitSet)null);
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
         this.match(this.input, 89, FOLLOW_89_in_throwsSpec451);
         this.match(this.input, 2, (BitSet)null);
         int cnt20 = 0;

         while(true) {
            int alt20 = 2;
            int LA20_0 = this.input.LA(1);
            if (LA20_0 == 30) {
               alt20 = 1;
            }

            switch (alt20) {
               case 1:
                  this.match(this.input, 30, FOLLOW_ID_in_throwsSpec453);
                  ++cnt20;
                  break;
               default:
                  if (cnt20 < 1) {
                     EarlyExitException eee = new EarlyExitException(20, this.input);
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
            int alt23 = true;
            int LA23_0 = this.input.LA(1);
            if (LA23_0 != 52) {
               NoViableAltException nvae = new NoViableAltException("", 23, 0, this.input);
               throw nvae;
            }

            int cnt22 = this.input.LA(2);
            int LA23_2;
            if (cnt22 != 2) {
               LA23_2 = this.input.mark();

               try {
                  this.input.consume();
                  NoViableAltException nvae = new NoViableAltException("", 23, 1, this.input);
                  throw nvae;
               } finally {
                  this.input.rewind(LA23_2);
               }
            }

            LA23_2 = this.input.LA(3);
            int LA22_0;
            byte alt23;
            int nvaeMark;
            if (LA23_2 == 4) {
               LA22_0 = this.input.LA(4);
               if (LA22_0 == 3) {
                  alt23 = 1;
               } else {
                  if (LA22_0 != 30) {
                     nvaeMark = this.input.mark();

                     try {
                        for(int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                           this.input.consume();
                        }

                        NoViableAltException nvae = new NoViableAltException("", 23, 3, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt23 = 2;
               }
            } else {
               if (LA23_2 != 30) {
                  LA22_0 = this.input.mark();

                  try {
                     for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                        this.input.consume();
                     }

                     NoViableAltException nvae = new NoViableAltException("", 23, 2, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(LA22_0);
                  }
               }

               alt23 = 3;
            }

            byte alt22;
            EarlyExitException eee;
            switch (alt23) {
               case 1:
                  this.match(this.input, 52, FOLLOW_SCOPE_in_ruleScopeSpec467);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec469);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 52, FOLLOW_SCOPE_in_ruleScopeSpec476);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 4, FOLLOW_ACTION_in_ruleScopeSpec478);
                  cnt22 = 0;

                  while(true) {
                     alt22 = 2;
                     LA22_0 = this.input.LA(1);
                     if (LA22_0 == 30) {
                        alt22 = 1;
                     }

                     switch (alt22) {
                        case 1:
                           this.match(this.input, 30, FOLLOW_ID_in_ruleScopeSpec480);
                           ++cnt22;
                           break;
                        default:
                           if (cnt22 < 1) {
                              eee = new EarlyExitException(21, this.input);
                              throw eee;
                           }

                           this.match(this.input, 3, (BitSet)null);
                           return;
                     }
                  }
               case 3:
                  this.match(this.input, 52, FOLLOW_SCOPE_in_ruleScopeSpec488);
                  this.match(this.input, 2, (BitSet)null);
                  cnt22 = 0;

                  while(true) {
                     alt22 = 2;
                     LA22_0 = this.input.LA(1);
                     if (LA22_0 == 30) {
                        alt22 = 1;
                     }

                     switch (alt22) {
                        case 1:
                           this.match(this.input, 30, FOLLOW_ID_in_ruleScopeSpec490);
                           ++cnt22;
                           break;
                        default:
                           if (cnt22 < 1) {
                              eee = new EarlyExitException(22, this.input);
                              throw eee;
                           }

                           this.match(this.input, 3, (BitSet)null);
                           return;
                     }
                  }
            }
         } catch (RecognitionException var35) {
            this.reportError(var35);
            this.recover(this.input, var35);
         }

      } finally {
         ;
      }
   }

   public final void block() throws RecognitionException {
      try {
         this.match(this.input, 15, FOLLOW_BLOCK_in_block510);
         this.match(this.input, 2, (BitSet)null);
         int alt24 = 2;
         int LA24_0 = this.input.LA(1);
         if (LA24_0 == 42) {
            alt24 = 1;
         }

         switch (alt24) {
            case 1:
               this.pushFollow(FOLLOW_optionsSpec_in_block512);
               this.optionsSpec();
               --this.state._fsp;
            default:
               int cnt25 = 0;

               while(true) {
                  int alt25 = 2;
                  int LA25_0 = this.input.LA(1);
                  if (LA25_0 == 8) {
                     alt25 = 1;
                  }

                  switch (alt25) {
                     case 1:
                        this.pushFollow(FOLLOW_alternative_in_block516);
                        this.alternative();
                        --this.state._fsp;
                        this.pushFollow(FOLLOW_rewrite_in_block518);
                        this.rewrite();
                        --this.state._fsp;
                        ++cnt25;
                        break;
                     default:
                        if (cnt25 < 1) {
                           EarlyExitException eee = new EarlyExitException(25, this.input);
                           throw eee;
                        }

                        this.match(this.input, 24, FOLLOW_EOB_in_block522);
                        this.match(this.input, 3, (BitSet)null);
                        return;
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

   public final void altList() throws RecognitionException {
      try {
         this.match(this.input, 15, FOLLOW_BLOCK_in_altList545);
         this.match(this.input, 2, (BitSet)null);
         int cnt26 = 0;

         while(true) {
            int alt26 = 2;
            int LA26_0 = this.input.LA(1);
            if (LA26_0 == 8) {
               alt26 = 1;
            }

            switch (alt26) {
               case 1:
                  this.pushFollow(FOLLOW_alternative_in_altList548);
                  this.alternative();
                  --this.state._fsp;
                  this.pushFollow(FOLLOW_rewrite_in_altList550);
                  this.rewrite();
                  --this.state._fsp;
                  ++cnt26;
                  break;
               default:
                  if (cnt26 < 1) {
                     EarlyExitException eee = new EarlyExitException(26, this.input);
                     throw eee;
                  }

                  this.match(this.input, 24, FOLLOW_EOB_in_altList554);
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

   public final void alternative() throws RecognitionException {
      try {
         try {
            int alt28 = true;
            int LA28_0 = this.input.LA(1);
            if (LA28_0 != 8) {
               NoViableAltException nvae = new NoViableAltException("", 28, 0, this.input);
               throw nvae;
            }

            int cnt27 = this.input.LA(2);
            int LA28_2;
            if (cnt27 != 2) {
               LA28_2 = this.input.mark();

               try {
                  this.input.consume();
                  NoViableAltException nvae = new NoViableAltException("", 28, 1, this.input);
                  throw nvae;
               } finally {
                  this.input.rewind(LA28_2);
               }
            }

            LA28_2 = this.input.LA(3);
            int LA27_0;
            byte alt28;
            if (LA28_2 == 26) {
               alt28 = 2;
            } else {
               if (LA28_2 != 4 && (LA28_2 < 14 || LA28_2 > 18) && LA28_2 != 29 && LA28_2 != 33 && LA28_2 != 36 && LA28_2 != 41 && LA28_2 != 45 && LA28_2 != 49 && LA28_2 != 51 && LA28_2 != 53 && (LA28_2 < 56 || LA28_2 > 58) && (LA28_2 < 61 || LA28_2 > 62) && LA28_2 != 73 && LA28_2 != 93) {
                  LA27_0 = this.input.mark();

                  try {
                     for(int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                        this.input.consume();
                     }

                     NoViableAltException nvae = new NoViableAltException("", 28, 2, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(LA27_0);
                  }
               }

               alt28 = 1;
            }

            switch (alt28) {
               case 1:
                  this.match(this.input, 8, FOLLOW_ALT_in_alternative576);
                  this.match(this.input, 2, (BitSet)null);
                  cnt27 = 0;

                  while(true) {
                     int alt27 = 2;
                     LA27_0 = this.input.LA(1);
                     if (LA27_0 == 4 || LA27_0 >= 14 && LA27_0 <= 18 || LA27_0 == 29 || LA27_0 == 33 || LA27_0 == 36 || LA27_0 == 41 || LA27_0 == 45 || LA27_0 == 49 || LA27_0 == 51 || LA27_0 == 53 || LA27_0 >= 56 && LA27_0 <= 58 || LA27_0 >= 61 && LA27_0 <= 62 || LA27_0 == 73 || LA27_0 == 93) {
                        alt27 = 1;
                     }

                     switch (alt27) {
                        case 1:
                           this.pushFollow(FOLLOW_element_in_alternative578);
                           this.element();
                           --this.state._fsp;
                           ++cnt27;
                           break;
                        default:
                           if (cnt27 < 1) {
                              EarlyExitException eee = new EarlyExitException(27, this.input);
                              throw eee;
                           }

                           this.match(this.input, 23, FOLLOW_EOA_in_alternative581);
                           this.match(this.input, 3, (BitSet)null);
                           return;
                     }
                  }
               case 2:
                  this.match(this.input, 8, FOLLOW_ALT_in_alternative593);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 26, FOLLOW_EPSILON_in_alternative595);
                  this.match(this.input, 23, FOLLOW_EOA_in_alternative597);
                  this.match(this.input, 3, (BitSet)null);
            }
         } catch (RecognitionException var24) {
            this.reportError(var24);
            this.recover(this.input, var24);
         }

      } finally {
         ;
      }
   }

   public final void exceptionGroup() throws RecognitionException {
      try {
         try {
            int alt31 = true;
            int LA31_0 = this.input.LA(1);
            byte alt31;
            if (LA31_0 == 81) {
               alt31 = 1;
            } else {
               if (LA31_0 != 82) {
                  NoViableAltException nvae = new NoViableAltException("", 31, 0, this.input);
                  throw nvae;
               }

               alt31 = 2;
            }

            switch (alt31) {
               case 1:
                  int cnt29 = 0;

                  while(true) {
                     int alt30 = 2;
                     int LA30_0 = this.input.LA(1);
                     if (LA30_0 == 81) {
                        alt30 = 1;
                     }

                     switch (alt30) {
                        case 1:
                           this.pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup612);
                           this.exceptionHandler();
                           --this.state._fsp;
                           ++cnt29;
                           break;
                        default:
                           if (cnt29 < 1) {
                              EarlyExitException eee = new EarlyExitException(29, this.input);
                              throw eee;
                           }

                           alt30 = 2;
                           LA30_0 = this.input.LA(1);
                           if (LA30_0 == 82) {
                              alt30 = 1;
                           }

                           switch (alt30) {
                              case 1:
                                 this.pushFollow(FOLLOW_finallyClause_in_exceptionGroup615);
                                 this.finallyClause();
                                 --this.state._fsp;
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
            this.match(this.input, 81, FOLLOW_81_in_exceptionHandler642);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 11, FOLLOW_ARG_ACTION_in_exceptionHandler644);
            this.match(this.input, 4, FOLLOW_ACTION_in_exceptionHandler646);
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
            this.match(this.input, 82, FOLLOW_82_in_finallyClause668);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 4, FOLLOW_ACTION_in_finallyClause670);
            this.match(this.input, 3, (BitSet)null);
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void element() throws RecognitionException {
      try {
         try {
            int alt33 = true;
            int cnt32;
            byte alt33;
            int LA33_9;
            switch (this.input.LA(1)) {
               case 4:
                  alt33 = 5;
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
               case 30:
               case 31:
               case 32:
               case 34:
               case 35:
               case 37:
               case 38:
               case 39:
               case 40:
               case 42:
               case 43:
               case 44:
               case 46:
               case 47:
               case 48:
               case 50:
               case 52:
               case 54:
               case 55:
               case 59:
               case 60:
               case 63:
               case 64:
               case 65:
               case 66:
               case 67:
               case 68:
               case 69:
               case 70:
               case 71:
               case 72:
               case 74:
               case 75:
               case 76:
               case 77:
               case 78:
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
               case 91:
               case 92:
               default:
                  NoViableAltException nvae = new NoViableAltException("", 33, 0, this.input);
                  throw nvae;
               case 14:
               case 16:
               case 17:
               case 49:
               case 51:
               case 56:
               case 61:
               case 73:
               case 93:
                  alt33 = 3;
                  break;
               case 15:
               case 18:
               case 41:
               case 45:
               case 57:
               case 58:
                  alt33 = 4;
                  break;
               case 29:
                  alt33 = 7;
                  break;
               case 33:
               case 36:
                  cnt32 = this.input.LA(2);
                  int nvaeMark;
                  if (cnt32 != 2) {
                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 33, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  nvaeMark = this.input.LA(3);
                  int nvaeMark;
                  if (nvaeMark != 30) {
                     LA33_9 = this.input.mark();

                     try {
                        for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                           this.input.consume();
                        }

                        NoViableAltException nvae = new NoViableAltException("", 33, 8, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA33_9);
                     }
                  }

                  LA33_9 = this.input.LA(4);
                  if (LA33_9 == 15) {
                     alt33 = 1;
                     break;
                  }

                  if (LA33_9 != 14 && (LA33_9 < 16 || LA33_9 > 17) && LA33_9 != 49 && LA33_9 != 51 && LA33_9 != 56 && LA33_9 != 61 && LA33_9 != 73 && LA33_9 != 93) {
                     nvaeMark = this.input.mark();

                     try {
                        for(int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                           this.input.consume();
                        }

                        NoViableAltException nvae = new NoViableAltException("", 33, 9, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt33 = 2;
                  break;
               case 53:
                  alt33 = 6;
                  break;
               case 62:
                  alt33 = 8;
            }

            MismatchedSetException mse;
            switch (alt33) {
               case 1:
                  if (this.input.LA(1) != 33 && this.input.LA(1) != 36) {
                     mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }

                  this.input.consume();
                  this.state.errorRecovery = false;
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 30, FOLLOW_ID_in_element692);
                  this.pushFollow(FOLLOW_block_in_element694);
                  this.block();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  if (this.input.LA(1) != 33 && this.input.LA(1) != 36) {
                     mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }

                  this.input.consume();
                  this.state.errorRecovery = false;
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 30, FOLLOW_ID_in_element707);
                  this.pushFollow(FOLLOW_atom_in_element709);
                  this.atom();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 3:
                  this.pushFollow(FOLLOW_atom_in_element715);
                  this.atom();
                  --this.state._fsp;
                  break;
               case 4:
                  this.pushFollow(FOLLOW_ebnf_in_element720);
                  this.ebnf();
                  --this.state._fsp;
                  break;
               case 5:
                  this.match(this.input, 4, FOLLOW_ACTION_in_element727);
                  break;
               case 6:
                  this.match(this.input, 53, FOLLOW_SEMPRED_in_element734);
                  break;
               case 7:
                  this.match(this.input, 29, FOLLOW_GATED_SEMPRED_in_element739);
                  break;
               case 8:
                  this.match(this.input, 62, FOLLOW_TREE_BEGIN_in_element747);
                  this.match(this.input, 2, (BitSet)null);
                  cnt32 = 0;

                  while(true) {
                     int alt32 = 2;
                     LA33_9 = this.input.LA(1);
                     if (LA33_9 == 4 || LA33_9 >= 14 && LA33_9 <= 18 || LA33_9 == 29 || LA33_9 == 33 || LA33_9 == 36 || LA33_9 == 41 || LA33_9 == 45 || LA33_9 == 49 || LA33_9 == 51 || LA33_9 == 53 || LA33_9 >= 56 && LA33_9 <= 58 || LA33_9 >= 61 && LA33_9 <= 62 || LA33_9 == 73 || LA33_9 == 93) {
                        alt32 = 1;
                     }

                     switch (alt32) {
                        case 1:
                           this.pushFollow(FOLLOW_element_in_element749);
                           this.element();
                           --this.state._fsp;
                           ++cnt32;
                           break;
                        default:
                           if (cnt32 < 1) {
                              EarlyExitException eee = new EarlyExitException(32, this.input);
                              throw eee;
                           }

                           this.match(this.input, 3, (BitSet)null);
                           return;
                     }
                  }
            }
         } catch (RecognitionException var34) {
            this.reportError(var34);
            this.recover(this.input, var34);
         }

      } finally {
         ;
      }
   }

   public final void atom() throws RecognitionException {
      try {
         try {
            int alt38 = true;
            int LA38_8;
            int nvaeMark;
            int nvaeMark;
            int nvaeMark;
            byte alt38;
            NoViableAltException nvae;
            NoViableAltException nvae;
            switch (this.input.LA(1)) {
               case 14:
               case 49:
                  alt38 = 1;
                  break;
               case 16:
                  LA38_8 = this.input.LA(2);
                  if (LA38_8 == 2) {
                     alt38 = 8;
                     break;
                  }

                  if ((LA38_8 < 3 || LA38_8 > 4) && (LA38_8 < 14 || LA38_8 > 18) && LA38_8 != 23 && LA38_8 != 29 && LA38_8 != 33 && LA38_8 != 36 && LA38_8 != 41 && LA38_8 != 45 && LA38_8 != 49 && LA38_8 != 51 && LA38_8 != 53 && (LA38_8 < 56 || LA38_8 > 58) && (LA38_8 < 61 || LA38_8 > 62) && LA38_8 != 73 && LA38_8 != 93) {
                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 38, 5, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt38 = 7;
                  break;
               case 17:
                  alt38 = 2;
                  break;
               case 51:
                  LA38_8 = this.input.LA(2);
                  if (LA38_8 == 2) {
                     alt38 = 5;
                     break;
                  }

                  if ((LA38_8 < 3 || LA38_8 > 4) && (LA38_8 < 14 || LA38_8 > 18) && LA38_8 != 23 && LA38_8 != 29 && LA38_8 != 33 && LA38_8 != 36 && LA38_8 != 41 && LA38_8 != 45 && LA38_8 != 49 && LA38_8 != 51 && LA38_8 != 53 && (LA38_8 < 56 || LA38_8 > 58) && (LA38_8 < 61 || LA38_8 > 62) && LA38_8 != 73 && LA38_8 != 93) {
                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 38, 4, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt38 = 6;
                  break;
               case 56:
                  LA38_8 = this.input.LA(2);
                  if (LA38_8 == 2) {
                     alt38 = 14;
                     break;
                  }

                  if ((LA38_8 < 3 || LA38_8 > 4) && (LA38_8 < 14 || LA38_8 > 18) && LA38_8 != 23 && LA38_8 != 29 && LA38_8 != 33 && LA38_8 != 36 && LA38_8 != 41 && LA38_8 != 45 && LA38_8 != 49 && LA38_8 != 51 && LA38_8 != 53 && (LA38_8 < 56 || LA38_8 > 58) && (LA38_8 < 61 || LA38_8 > 62) && LA38_8 != 73 && LA38_8 != 93) {
                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 38, 7, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt38 = 13;
                  break;
               case 61:
                  LA38_8 = this.input.LA(2);
                  if (LA38_8 == 2) {
                     nvaeMark = this.input.LA(3);
                     if (nvaeMark == 11) {
                        nvaeMark = this.input.LA(4);
                        if (nvaeMark == 3) {
                           alt38 = 12;
                        } else {
                           if (nvaeMark != 42) {
                              nvaeMark = this.input.mark();

                              try {
                                 for(int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 NoViableAltException nvae = new NoViableAltException("", 38, 22, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt38 = 11;
                        }
                     } else {
                        if (nvaeMark != 42) {
                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 38, 14, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                        }

                        alt38 = 10;
                     }
                     break;
                  }

                  if ((LA38_8 < 3 || LA38_8 > 4) && (LA38_8 < 14 || LA38_8 > 18) && LA38_8 != 23 && LA38_8 != 29 && LA38_8 != 33 && LA38_8 != 36 && LA38_8 != 41 && LA38_8 != 45 && LA38_8 != 49 && LA38_8 != 51 && LA38_8 != 53 && (LA38_8 < 56 || LA38_8 > 58) && (LA38_8 < 61 || LA38_8 > 62) && LA38_8 != 73 && LA38_8 != 93) {
                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 38, 6, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt38 = 9;
                  break;
               case 73:
                  LA38_8 = this.input.LA(2);
                  if (LA38_8 == 2) {
                     alt38 = 16;
                     break;
                  }

                  if ((LA38_8 < 3 || LA38_8 > 4) && (LA38_8 < 14 || LA38_8 > 18) && LA38_8 != 23 && LA38_8 != 29 && LA38_8 != 33 && LA38_8 != 36 && LA38_8 != 41 && LA38_8 != 45 && LA38_8 != 49 && LA38_8 != 51 && LA38_8 != 53 && (LA38_8 < 56 || LA38_8 > 58) && (LA38_8 < 61 || LA38_8 > 62) && LA38_8 != 73 && LA38_8 != 93) {
                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 38, 8, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt38 = 15;
                  break;
               case 93:
                  LA38_8 = this.input.LA(2);
                  if (LA38_8 != 2) {
                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 38, 3, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  nvaeMark = this.input.LA(3);
                  if (nvaeMark != 16 && nvaeMark != 56 && nvaeMark != 61) {
                     if (nvaeMark != 15) {
                        nvaeMark = this.input.mark();

                        try {
                           for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
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
                  }

                  alt38 = 3;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 38, 0, this.input);
                  throw nvae;
            }

            byte alt37;
            switch (alt38) {
               case 1:
                  if (this.input.LA(1) != 14 && this.input.LA(1) != 49) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }

                  this.input.consume();
                  this.state.errorRecovery = false;
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_atom_in_atom769);
                  this.atom();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 17, FOLLOW_CHAR_RANGE_in_atom776);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_atom778);
                  this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_atom780);
                  alt37 = 2;
                  nvaeMark = this.input.LA(1);
                  if (nvaeMark == 42) {
                     alt37 = 1;
                  }

                  switch (alt37) {
                     case 1:
                        this.pushFollow(FOLLOW_optionsSpec_in_atom782);
                        this.optionsSpec();
                        --this.state._fsp;
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        return;
                  }
               case 3:
                  this.match(this.input, 93, FOLLOW_93_in_atom790);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_notTerminal_in_atom792);
                  this.notTerminal();
                  --this.state._fsp;
                  alt37 = 2;
                  nvaeMark = this.input.LA(1);
                  if (nvaeMark == 42) {
                     alt37 = 1;
                  }

                  switch (alt37) {
                     case 1:
                        this.pushFollow(FOLLOW_optionsSpec_in_atom794);
                        this.optionsSpec();
                        --this.state._fsp;
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        return;
                  }
               case 4:
                  this.match(this.input, 93, FOLLOW_93_in_atom802);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_atom804);
                  this.block();
                  --this.state._fsp;
                  alt37 = 2;
                  nvaeMark = this.input.LA(1);
                  if (nvaeMark == 42) {
                     alt37 = 1;
                  }

                  switch (alt37) {
                     case 1:
                        this.pushFollow(FOLLOW_optionsSpec_in_atom806);
                        this.optionsSpec();
                        --this.state._fsp;
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        return;
                  }
               case 5:
                  this.match(this.input, 51, FOLLOW_RULE_REF_in_atom817);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 11, FOLLOW_ARG_ACTION_in_atom819);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 6:
                  this.match(this.input, 51, FOLLOW_RULE_REF_in_atom828);
                  break;
               case 7:
                  this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_atom838);
                  break;
               case 8:
                  this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_atom849);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_optionsSpec_in_atom851);
                  this.optionsSpec();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 9:
                  this.match(this.input, 61, FOLLOW_TOKEN_REF_in_atom860);
                  break;
               case 10:
                  this.match(this.input, 61, FOLLOW_TOKEN_REF_in_atom869);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_optionsSpec_in_atom871);
                  this.optionsSpec();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 11:
                  this.match(this.input, 61, FOLLOW_TOKEN_REF_in_atom881);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 11, FOLLOW_ARG_ACTION_in_atom883);
                  this.pushFollow(FOLLOW_optionsSpec_in_atom885);
                  this.optionsSpec();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 12:
                  this.match(this.input, 61, FOLLOW_TOKEN_REF_in_atom895);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 11, FOLLOW_ARG_ACTION_in_atom897);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 13:
                  this.match(this.input, 56, FOLLOW_STRING_LITERAL_in_atom906);
                  break;
               case 14:
                  this.match(this.input, 56, FOLLOW_STRING_LITERAL_in_atom915);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_optionsSpec_in_atom917);
                  this.optionsSpec();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 15:
                  this.match(this.input, 73, FOLLOW_73_in_atom926);
                  break;
               case 16:
                  this.match(this.input, 73, FOLLOW_73_in_atom935);
                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);
                     alt37 = 2;
                     nvaeMark = this.input.LA(1);
                     if (nvaeMark == 42) {
                        alt37 = 1;
                     }

                     switch (alt37) {
                        case 1:
                           this.pushFollow(FOLLOW_optionsSpec_in_atom937);
                           this.optionsSpec();
                           --this.state._fsp;
                        default:
                           this.match(this.input, 3, (BitSet)null);
                     }
                  }
            }
         } catch (RecognitionException var135) {
            this.reportError(var135);
            this.recover(this.input, var135);
         }

      } finally {
         ;
      }
   }

   public final void ebnf() throws RecognitionException {
      try {
         try {
            int alt39 = true;
            byte alt39;
            switch (this.input.LA(1)) {
               case 15:
                  alt39 = 6;
                  break;
               case 18:
                  alt39 = 3;
                  break;
               case 41:
                  alt39 = 2;
                  break;
               case 45:
                  alt39 = 4;
                  break;
               case 57:
                  alt39 = 1;
                  break;
               case 58:
                  alt39 = 5;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 39, 0, this.input);
                  throw nvae;
            }

            switch (alt39) {
               case 1:
                  this.match(this.input, 57, FOLLOW_SYNPRED_in_ebnf956);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_ebnf958);
                  this.block();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 41, FOLLOW_OPTIONAL_in_ebnf965);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_ebnf967);
                  this.block();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 3:
                  this.match(this.input, 18, FOLLOW_CLOSURE_in_ebnf976);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_ebnf978);
                  this.block();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 4:
                  this.match(this.input, 45, FOLLOW_POSITIVE_CLOSURE_in_ebnf988);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_block_in_ebnf990);
                  this.block();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 5:
                  this.match(this.input, 58, FOLLOW_SYN_SEMPRED_in_ebnf996);
                  break;
               case 6:
                  this.pushFollow(FOLLOW_block_in_ebnf1001);
                  this.block();
                  --this.state._fsp;
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void notTerminal() throws RecognitionException {
      try {
         try {
            if (this.input.LA(1) != 16 && this.input.LA(1) != 56 && this.input.LA(1) != 61) {
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

   public final void rewrite() throws RecognitionException {
      try {
         try {
            int alt41 = true;
            int LA41_0 = this.input.LA(1);
            byte alt41;
            if (LA41_0 == 48) {
               alt41 = 1;
            } else {
               if (LA41_0 != 8 && LA41_0 != 24) {
                  NoViableAltException nvae = new NoViableAltException("", 41, 0, this.input);
                  throw nvae;
               }

               alt41 = 2;
            }

            switch (alt41) {
               case 1:
                  while(true) {
                     int alt40 = 2;
                     int LA40_0 = this.input.LA(1);
                     if (LA40_0 == 48) {
                        int LA40_1 = this.input.LA(2);
                        if (LA40_1 == 2) {
                           int LA40_2 = this.input.LA(3);
                           if (LA40_2 == 53) {
                              alt40 = 1;
                           }
                        }
                     }

                     switch (alt40) {
                        case 1:
                           this.match(this.input, 48, FOLLOW_REWRITE_in_rewrite1041);
                           this.match(this.input, 2, (BitSet)null);
                           this.match(this.input, 53, FOLLOW_SEMPRED_in_rewrite1043);
                           this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite1045);
                           this.rewrite_alternative();
                           --this.state._fsp;
                           this.match(this.input, 3, (BitSet)null);
                           break;
                        default:
                           this.match(this.input, 48, FOLLOW_REWRITE_in_rewrite1051);
                           this.match(this.input, 2, (BitSet)null);
                           this.pushFollow(FOLLOW_rewrite_alternative_in_rewrite1053);
                           this.rewrite_alternative();
                           --this.state._fsp;
                           this.match(this.input, 3, (BitSet)null);
                           return;
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

   public final void rewrite_alternative() throws RecognitionException {
      try {
         try {
            int alt42 = true;
            int LA42_0 = this.input.LA(1);
            byte alt42;
            if (LA42_0 != 4 && LA42_0 != 59) {
               if (LA42_0 != 8) {
                  NoViableAltException nvae = new NoViableAltException("", 42, 0, this.input);
                  throw nvae;
               }

               int LA42_2 = this.input.LA(2);
               int LA42_3;
               if (LA42_2 != 2) {
                  LA42_3 = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 42, 2, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(LA42_3);
                  }
               }

               LA42_3 = this.input.LA(3);
               if (LA42_3 == 26) {
                  alt42 = 3;
               } else {
                  if (LA42_3 != 4 && (LA42_3 < 15 || LA42_3 > 16) && LA42_3 != 18 && LA42_3 != 32 && LA42_3 != 41 && LA42_3 != 45 && LA42_3 != 51 && LA42_3 != 56 && (LA42_3 < 61 || LA42_3 > 62)) {
                     int nvaeMark = this.input.mark();

                     try {
                        for(int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                           this.input.consume();
                        }

                        NoViableAltException nvae = new NoViableAltException("", 42, 3, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt42 = 2;
               }
            } else {
               alt42 = 1;
            }

            switch (alt42) {
               case 1:
                  this.pushFollow(FOLLOW_rewrite_template_in_rewrite_alternative1068);
                  this.rewrite_template();
                  --this.state._fsp;
                  break;
               case 2:
                  this.pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_alternative1073);
                  this.rewrite_tree_alternative();
                  --this.state._fsp;
                  break;
               case 3:
                  this.match(this.input, 8, FOLLOW_ALT_in_rewrite_alternative1084);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 26, FOLLOW_EPSILON_in_rewrite_alternative1086);
                  this.match(this.input, 23, FOLLOW_EOA_in_rewrite_alternative1088);
                  this.match(this.input, 3, (BitSet)null);
            }
         } catch (RecognitionException var24) {
            this.reportError(var24);
            this.recover(this.input, var24);
         }

      } finally {
         ;
      }
   }

   public final void rewrite_tree_block() throws RecognitionException {
      try {
         try {
            this.match(this.input, 15, FOLLOW_BLOCK_in_rewrite_tree_block1107);
            this.match(this.input, 2, (BitSet)null);
            this.pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block1109);
            this.rewrite_tree_alternative();
            --this.state._fsp;
            this.match(this.input, 24, FOLLOW_EOB_in_rewrite_tree_block1111);
            this.match(this.input, 3, (BitSet)null);
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void rewrite_tree_alternative() throws RecognitionException {
      try {
         this.match(this.input, 8, FOLLOW_ALT_in_rewrite_tree_alternative1130);
         this.match(this.input, 2, (BitSet)null);
         int cnt43 = 0;

         while(true) {
            int alt43 = 2;
            int LA43_0 = this.input.LA(1);
            if (LA43_0 == 4 || LA43_0 >= 15 && LA43_0 <= 16 || LA43_0 == 18 || LA43_0 == 32 || LA43_0 == 41 || LA43_0 == 45 || LA43_0 == 51 || LA43_0 == 56 || LA43_0 >= 61 && LA43_0 <= 62) {
               alt43 = 1;
            }

            switch (alt43) {
               case 1:
                  this.pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative1132);
                  this.rewrite_tree_element();
                  --this.state._fsp;
                  ++cnt43;
                  break;
               default:
                  if (cnt43 < 1) {
                     EarlyExitException eee = new EarlyExitException(43, this.input);
                     throw eee;
                  }

                  this.match(this.input, 23, FOLLOW_EOA_in_rewrite_tree_alternative1135);
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

   public final void rewrite_tree_element() throws RecognitionException {
      try {
         try {
            int alt44 = true;
            byte alt44;
            switch (this.input.LA(1)) {
               case 4:
               case 16:
               case 32:
               case 51:
               case 56:
               case 61:
                  alt44 = 1;
                  break;
               case 15:
                  alt44 = 3;
                  break;
               case 18:
               case 41:
               case 45:
                  alt44 = 4;
                  break;
               case 62:
                  alt44 = 2;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 44, 0, this.input);
                  throw nvae;
            }

            switch (alt44) {
               case 1:
                  this.pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree_element1150);
                  this.rewrite_tree_atom();
                  --this.state._fsp;
                  break;
               case 2:
                  this.pushFollow(FOLLOW_rewrite_tree_in_rewrite_tree_element1155);
                  this.rewrite_tree();
                  --this.state._fsp;
                  break;
               case 3:
                  this.pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_element1162);
                  this.rewrite_tree_block();
                  --this.state._fsp;
                  break;
               case 4:
                  this.pushFollow(FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element1169);
                  this.rewrite_tree_ebnf();
                  --this.state._fsp;
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void rewrite_tree_atom() throws RecognitionException {
      try {
         try {
            int alt45 = true;
            byte alt45;
            switch (this.input.LA(1)) {
               case 4:
                  alt45 = 7;
                  break;
               case 16:
                  alt45 = 1;
                  break;
               case 32:
                  alt45 = 6;
                  break;
               case 51:
                  alt45 = 4;
                  break;
               case 56:
                  alt45 = 5;
                  break;
               case 61:
                  int LA45_2 = this.input.LA(2);
                  if (LA45_2 == 2) {
                     alt45 = 3;
                     break;
                  }

                  if ((LA45_2 < 3 || LA45_2 > 4) && (LA45_2 < 15 || LA45_2 > 16) && LA45_2 != 18 && LA45_2 != 23 && LA45_2 != 32 && LA45_2 != 41 && LA45_2 != 45 && LA45_2 != 51 && LA45_2 != 56 && (LA45_2 < 61 || LA45_2 > 62)) {
                     int nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 45, 2, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt45 = 2;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 45, 0, this.input);
                  throw nvae;
            }

            switch (alt45) {
               case 1:
                  this.match(this.input, 16, FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom1185);
                  break;
               case 2:
                  this.match(this.input, 61, FOLLOW_TOKEN_REF_in_rewrite_tree_atom1192);
                  break;
               case 3:
                  this.match(this.input, 61, FOLLOW_TOKEN_REF_in_rewrite_tree_atom1200);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 11, FOLLOW_ARG_ACTION_in_rewrite_tree_atom1202);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 4:
                  this.match(this.input, 51, FOLLOW_RULE_REF_in_rewrite_tree_atom1214);
                  break;
               case 5:
                  this.match(this.input, 56, FOLLOW_STRING_LITERAL_in_rewrite_tree_atom1221);
                  break;
               case 6:
                  this.match(this.input, 32, FOLLOW_LABEL_in_rewrite_tree_atom1228);
                  break;
               case 7:
                  this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_tree_atom1233);
            }
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.recover(this.input, var14);
         }

      } finally {
         ;
      }
   }

   public final void rewrite_tree_ebnf() throws RecognitionException {
      try {
         try {
            int alt46 = true;
            byte alt46;
            switch (this.input.LA(1)) {
               case 18:
                  alt46 = 2;
                  break;
               case 41:
                  alt46 = 1;
                  break;
               case 45:
                  alt46 = 3;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 46, 0, this.input);
                  throw nvae;
            }

            switch (alt46) {
               case 1:
                  this.match(this.input, 41, FOLLOW_OPTIONAL_in_rewrite_tree_ebnf1245);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1247);
                  this.rewrite_tree_block();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.match(this.input, 18, FOLLOW_CLOSURE_in_rewrite_tree_ebnf1256);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1258);
                  this.rewrite_tree_block();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 3:
                  this.match(this.input, 45, FOLLOW_POSITIVE_CLOSURE_in_rewrite_tree_ebnf1268);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1270);
                  this.rewrite_tree_block();
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

   public final void rewrite_tree() throws RecognitionException {
      try {
         this.match(this.input, 62, FOLLOW_TREE_BEGIN_in_rewrite_tree1284);
         this.match(this.input, 2, (BitSet)null);
         this.pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree1286);
         this.rewrite_tree_atom();
         --this.state._fsp;

         while(true) {
            int alt47 = 2;
            int LA47_0 = this.input.LA(1);
            if (LA47_0 == 4 || LA47_0 >= 15 && LA47_0 <= 16 || LA47_0 == 18 || LA47_0 == 32 || LA47_0 == 41 || LA47_0 == 45 || LA47_0 == 51 || LA47_0 == 56 || LA47_0 >= 61 && LA47_0 <= 62) {
               alt47 = 1;
            }

            switch (alt47) {
               case 1:
                  this.pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree1288);
                  this.rewrite_tree_element();
                  --this.state._fsp;
                  break;
               default:
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

   public final void rewrite_template() throws RecognitionException {
      try {
         try {
            int alt48 = true;
            int alt48 = this.dfa48.predict(this.input);
            switch (alt48) {
               case 1:
                  this.match(this.input, 59, FOLLOW_TEMPLATE_in_rewrite_template1306);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 30, FOLLOW_ID_in_rewrite_template1308);
                  this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template1310);
                  this.rewrite_template_args();
                  --this.state._fsp;
                  if (this.input.LA(1) >= 21 && this.input.LA(1) <= 22) {
                     this.input.consume();
                     this.state.errorRecovery = false;
                     this.match(this.input, 3, (BitSet)null);
                     break;
                  }

                  MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                  throw mse;
               case 2:
                  this.pushFollow(FOLLOW_rewrite_template_ref_in_rewrite_template1333);
                  this.rewrite_template_ref();
                  --this.state._fsp;
                  break;
               case 3:
                  this.pushFollow(FOLLOW_rewrite_indirect_template_head_in_rewrite_template1338);
                  this.rewrite_indirect_template_head();
                  --this.state._fsp;
                  break;
               case 4:
                  this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template1343);
            }
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void rewrite_template_ref() throws RecognitionException {
      try {
         try {
            this.match(this.input, 59, FOLLOW_TEMPLATE_in_rewrite_template_ref1357);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 30, FOLLOW_ID_in_rewrite_template_ref1359);
            this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template_ref1361);
            this.rewrite_template_args();
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

   public final void rewrite_indirect_template_head() throws RecognitionException {
      try {
         try {
            this.match(this.input, 59, FOLLOW_TEMPLATE_in_rewrite_indirect_template_head1376);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_indirect_template_head1378);
            this.pushFollow(FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head1380);
            this.rewrite_template_args();
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

   public final void rewrite_template_args() throws RecognitionException {
      try {
         try {
            int alt50 = true;
            int LA50_0 = this.input.LA(1);
            if (LA50_0 != 10) {
               NoViableAltException nvae = new NoViableAltException("", 50, 0, this.input);
               throw nvae;
            }

            int cnt49 = this.input.LA(2);
            byte alt50;
            if (cnt49 == 2) {
               alt50 = 1;
            } else {
               if (cnt49 != 3 && (cnt49 < 21 || cnt49 > 22)) {
                  int nvaeMark = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 50, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               alt50 = 2;
            }

            switch (alt50) {
               case 1:
                  this.match(this.input, 10, FOLLOW_ARGLIST_in_rewrite_template_args1393);
                  this.match(this.input, 2, (BitSet)null);
                  cnt49 = 0;

                  while(true) {
                     int alt49 = 2;
                     int LA49_0 = this.input.LA(1);
                     if (LA49_0 == 9) {
                        alt49 = 1;
                     }

                     switch (alt49) {
                        case 1:
                           this.pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args1395);
                           this.rewrite_template_arg();
                           --this.state._fsp;
                           ++cnt49;
                           break;
                        default:
                           if (cnt49 < 1) {
                              EarlyExitException eee = new EarlyExitException(49, this.input);
                              throw eee;
                           }

                           this.match(this.input, 3, (BitSet)null);
                           return;
                     }
                  }
               case 2:
                  this.match(this.input, 10, FOLLOW_ARGLIST_in_rewrite_template_args1402);
            }
         } catch (RecognitionException var15) {
            this.reportError(var15);
            this.recover(this.input, var15);
         }

      } finally {
         ;
      }
   }

   public final void rewrite_template_arg() throws RecognitionException {
      try {
         try {
            this.match(this.input, 9, FOLLOW_ARG_in_rewrite_template_arg1416);
            this.match(this.input, 2, (BitSet)null);
            this.match(this.input, 30, FOLLOW_ID_in_rewrite_template_arg1418);
            this.match(this.input, 4, FOLLOW_ACTION_in_rewrite_template_arg1420);
            this.match(this.input, 3, (BitSet)null);
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final void qid() throws RecognitionException {
      try {
         this.match(this.input, 30, FOLLOW_ID_in_qid1431);

         while(true) {
            int alt51 = 2;
            int LA51_0 = this.input.LA(1);
            if (LA51_0 == 73) {
               alt51 = 1;
            }

            switch (alt51) {
               case 1:
                  this.match(this.input, 73, FOLLOW_73_in_qid1434);
                  this.match(this.input, 30, FOLLOW_ID_in_qid1436);
                  break;
               default:
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

   static {
      int numStates = DFA48_transitionS.length;
      DFA48_transition = new short[numStates][];

      for(int i = 0; i < numStates; ++i) {
         DFA48_transition[i] = DFA.unpackEncodedString(DFA48_transitionS[i]);
      }

      FOLLOW_grammarType_in_grammarDef58 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_grammarDef60 = new BitSet(new long[]{1158555402188623872L});
      FOLLOW_DOC_COMMENT_in_grammarDef62 = new BitSet(new long[]{1158555402187575296L});
      FOLLOW_optionsSpec_in_grammarDef65 = new BitSet(new long[]{1158551004141064192L});
      FOLLOW_tokensSpec_in_grammarDef68 = new BitSet(new long[]{5629499534217216L});
      FOLLOW_attrScope_in_grammarDef71 = new BitSet(new long[]{5629499534217216L});
      FOLLOW_action_in_grammarDef74 = new BitSet(new long[]{1125899906846720L});
      FOLLOW_rule_in_grammarDef77 = new BitSet(new long[]{1125899906842632L});
      FOLLOW_TOKENS_in_tokensSpec133 = new BitSet(new long[]{4L});
      FOLLOW_tokenSpec_in_tokensSpec135 = new BitSet(new long[]{2305843017803628552L});
      FOLLOW_LABEL_ASSIGN_in_tokenSpec149 = new BitSet(new long[]{4L});
      FOLLOW_TOKEN_REF_in_tokenSpec151 = new BitSet(new long[]{72057594037927936L});
      FOLLOW_STRING_LITERAL_in_tokenSpec153 = new BitSet(new long[]{8L});
      FOLLOW_LABEL_ASSIGN_in_tokenSpec160 = new BitSet(new long[]{4L});
      FOLLOW_TOKEN_REF_in_tokenSpec162 = new BitSet(new long[]{65536L});
      FOLLOW_CHAR_LITERAL_in_tokenSpec164 = new BitSet(new long[]{8L});
      FOLLOW_TOKEN_REF_in_tokenSpec170 = new BitSet(new long[]{2L});
      FOLLOW_SCOPE_in_attrScope182 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_attrScope184 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_attrScope186 = new BitSet(new long[]{8L});
      FOLLOW_AT_in_action199 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_action201 = new BitSet(new long[]{1073741824L});
      FOLLOW_ID_in_action203 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_action205 = new BitSet(new long[]{8L});
      FOLLOW_AT_in_action212 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_action214 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_action216 = new BitSet(new long[]{8L});
      FOLLOW_OPTIONS_in_optionsSpec229 = new BitSet(new long[]{4L});
      FOLLOW_option_in_optionsSpec231 = new BitSet(new long[]{9663676424L});
      FOLLOW_qid_in_option249 = new BitSet(new long[]{2L});
      FOLLOW_LABEL_ASSIGN_in_option259 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_option261 = new BitSet(new long[]{72057597259218944L});
      FOLLOW_optionValue_in_option263 = new BitSet(new long[]{8L});
      FOLLOW_RULE_in_rule329 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_rule331 = new BitSet(new long[]{4648735430709760L, 62914560L});
      FOLLOW_modifier_in_rule333 = new BitSet(new long[]{4648735162274304L, 33554432L});
      FOLLOW_ARG_in_rule338 = new BitSet(new long[]{4L});
      FOLLOW_ARG_ACTION_in_rule340 = new BitSet(new long[]{8L});
      FOLLOW_RET_in_rule347 = new BitSet(new long[]{4L});
      FOLLOW_ARG_ACTION_in_rule349 = new BitSet(new long[]{8L});
      FOLLOW_throwsSpec_in_rule362 = new BitSet(new long[]{4507997673918464L});
      FOLLOW_optionsSpec_in_rule365 = new BitSet(new long[]{4503599627407360L});
      FOLLOW_ruleScopeSpec_in_rule368 = new BitSet(new long[]{36864L});
      FOLLOW_ruleAction_in_rule371 = new BitSet(new long[]{36864L});
      FOLLOW_altList_in_rule382 = new BitSet(new long[]{33554432L, 393216L});
      FOLLOW_exceptionGroup_in_rule392 = new BitSet(new long[]{33554432L});
      FOLLOW_EOR_in_rule395 = new BitSet(new long[]{8L});
      FOLLOW_AT_in_ruleAction434 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_ruleAction436 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_ruleAction438 = new BitSet(new long[]{8L});
      FOLLOW_89_in_throwsSpec451 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_throwsSpec453 = new BitSet(new long[]{1073741832L});
      FOLLOW_SCOPE_in_ruleScopeSpec467 = new BitSet(new long[]{4L});
      FOLLOW_ACTION_in_ruleScopeSpec469 = new BitSet(new long[]{8L});
      FOLLOW_SCOPE_in_ruleScopeSpec476 = new BitSet(new long[]{4L});
      FOLLOW_ACTION_in_ruleScopeSpec478 = new BitSet(new long[]{1073741824L});
      FOLLOW_ID_in_ruleScopeSpec480 = new BitSet(new long[]{1073741832L});
      FOLLOW_SCOPE_in_ruleScopeSpec488 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_ruleScopeSpec490 = new BitSet(new long[]{1073741832L});
      FOLLOW_BLOCK_in_block510 = new BitSet(new long[]{4L});
      FOLLOW_optionsSpec_in_block512 = new BitSet(new long[]{256L});
      FOLLOW_alternative_in_block516 = new BitSet(new long[]{281474993488128L});
      FOLLOW_rewrite_in_block518 = new BitSet(new long[]{16777472L});
      FOLLOW_EOB_in_block522 = new BitSet(new long[]{8L});
      FOLLOW_BLOCK_in_altList545 = new BitSet(new long[]{4L});
      FOLLOW_alternative_in_altList548 = new BitSet(new long[]{281474993488128L});
      FOLLOW_rewrite_in_altList550 = new BitSet(new long[]{16777472L});
      FOLLOW_EOB_in_altList554 = new BitSet(new long[]{8L});
      FOLLOW_ALT_in_alternative576 = new BitSet(new long[]{4L});
      FOLLOW_element_in_alternative578 = new BitSet(new long[]{7433791596178948112L, 536871424L});
      FOLLOW_EOA_in_alternative581 = new BitSet(new long[]{8L});
      FOLLOW_ALT_in_alternative593 = new BitSet(new long[]{4L});
      FOLLOW_EPSILON_in_alternative595 = new BitSet(new long[]{8388608L});
      FOLLOW_EOA_in_alternative597 = new BitSet(new long[]{8L});
      FOLLOW_exceptionHandler_in_exceptionGroup612 = new BitSet(new long[]{2L, 393216L});
      FOLLOW_finallyClause_in_exceptionGroup615 = new BitSet(new long[]{2L});
      FOLLOW_finallyClause_in_exceptionGroup621 = new BitSet(new long[]{2L});
      FOLLOW_81_in_exceptionHandler642 = new BitSet(new long[]{4L});
      FOLLOW_ARG_ACTION_in_exceptionHandler644 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_exceptionHandler646 = new BitSet(new long[]{8L});
      FOLLOW_82_in_finallyClause668 = new BitSet(new long[]{4L});
      FOLLOW_ACTION_in_finallyClause670 = new BitSet(new long[]{8L});
      FOLLOW_set_in_element686 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_element692 = new BitSet(new long[]{32768L});
      FOLLOW_block_in_element694 = new BitSet(new long[]{8L});
      FOLLOW_set_in_element701 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_element707 = new BitSet(new long[]{2380715353018941440L, 536871424L});
      FOLLOW_atom_in_element709 = new BitSet(new long[]{8L});
      FOLLOW_atom_in_element715 = new BitSet(new long[]{2L});
      FOLLOW_ebnf_in_element720 = new BitSet(new long[]{2L});
      FOLLOW_ACTION_in_element727 = new BitSet(new long[]{2L});
      FOLLOW_SEMPRED_in_element734 = new BitSet(new long[]{2L});
      FOLLOW_GATED_SEMPRED_in_element739 = new BitSet(new long[]{2L});
      FOLLOW_TREE_BEGIN_in_element747 = new BitSet(new long[]{4L});
      FOLLOW_element_in_element749 = new BitSet(new long[]{7433791596170559512L, 536871424L});
      FOLLOW_set_in_atom763 = new BitSet(new long[]{4L});
      FOLLOW_atom_in_atom769 = new BitSet(new long[]{8L});
      FOLLOW_CHAR_RANGE_in_atom776 = new BitSet(new long[]{4L});
      FOLLOW_CHAR_LITERAL_in_atom778 = new BitSet(new long[]{65536L});
      FOLLOW_CHAR_LITERAL_in_atom780 = new BitSet(new long[]{4398046511112L});
      FOLLOW_optionsSpec_in_atom782 = new BitSet(new long[]{8L});
      FOLLOW_93_in_atom790 = new BitSet(new long[]{4L});
      FOLLOW_notTerminal_in_atom792 = new BitSet(new long[]{4398046511112L});
      FOLLOW_optionsSpec_in_atom794 = new BitSet(new long[]{8L});
      FOLLOW_93_in_atom802 = new BitSet(new long[]{4L});
      FOLLOW_block_in_atom804 = new BitSet(new long[]{4398046511112L});
      FOLLOW_optionsSpec_in_atom806 = new BitSet(new long[]{8L});
      FOLLOW_RULE_REF_in_atom817 = new BitSet(new long[]{4L});
      FOLLOW_ARG_ACTION_in_atom819 = new BitSet(new long[]{8L});
      FOLLOW_RULE_REF_in_atom828 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_atom838 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_atom849 = new BitSet(new long[]{4L});
      FOLLOW_optionsSpec_in_atom851 = new BitSet(new long[]{8L});
      FOLLOW_TOKEN_REF_in_atom860 = new BitSet(new long[]{2L});
      FOLLOW_TOKEN_REF_in_atom869 = new BitSet(new long[]{4L});
      FOLLOW_optionsSpec_in_atom871 = new BitSet(new long[]{8L});
      FOLLOW_TOKEN_REF_in_atom881 = new BitSet(new long[]{4L});
      FOLLOW_ARG_ACTION_in_atom883 = new BitSet(new long[]{4398046511104L});
      FOLLOW_optionsSpec_in_atom885 = new BitSet(new long[]{8L});
      FOLLOW_TOKEN_REF_in_atom895 = new BitSet(new long[]{4L});
      FOLLOW_ARG_ACTION_in_atom897 = new BitSet(new long[]{8L});
      FOLLOW_STRING_LITERAL_in_atom906 = new BitSet(new long[]{2L});
      FOLLOW_STRING_LITERAL_in_atom915 = new BitSet(new long[]{4L});
      FOLLOW_optionsSpec_in_atom917 = new BitSet(new long[]{8L});
      FOLLOW_73_in_atom926 = new BitSet(new long[]{2L});
      FOLLOW_73_in_atom935 = new BitSet(new long[]{4L});
      FOLLOW_optionsSpec_in_atom937 = new BitSet(new long[]{8L});
      FOLLOW_SYNPRED_in_ebnf956 = new BitSet(new long[]{4L});
      FOLLOW_block_in_ebnf958 = new BitSet(new long[]{8L});
      FOLLOW_OPTIONAL_in_ebnf965 = new BitSet(new long[]{4L});
      FOLLOW_block_in_ebnf967 = new BitSet(new long[]{8L});
      FOLLOW_CLOSURE_in_ebnf976 = new BitSet(new long[]{4L});
      FOLLOW_block_in_ebnf978 = new BitSet(new long[]{8L});
      FOLLOW_POSITIVE_CLOSURE_in_ebnf988 = new BitSet(new long[]{4L});
      FOLLOW_block_in_ebnf990 = new BitSet(new long[]{8L});
      FOLLOW_SYN_SEMPRED_in_ebnf996 = new BitSet(new long[]{2L});
      FOLLOW_block_in_ebnf1001 = new BitSet(new long[]{2L});
      FOLLOW_REWRITE_in_rewrite1041 = new BitSet(new long[]{4L});
      FOLLOW_SEMPRED_in_rewrite1043 = new BitSet(new long[]{576460752303423760L});
      FOLLOW_rewrite_alternative_in_rewrite1045 = new BitSet(new long[]{8L});
      FOLLOW_REWRITE_in_rewrite1051 = new BitSet(new long[]{4L});
      FOLLOW_rewrite_alternative_in_rewrite1053 = new BitSet(new long[]{8L});
      FOLLOW_rewrite_template_in_rewrite_alternative1068 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_alternative_in_rewrite_alternative1073 = new BitSet(new long[]{2L});
      FOLLOW_ALT_in_rewrite_alternative1084 = new BitSet(new long[]{4L});
      FOLLOW_EPSILON_in_rewrite_alternative1086 = new BitSet(new long[]{8388608L});
      FOLLOW_EOA_in_rewrite_alternative1088 = new BitSet(new long[]{8L});
      FOLLOW_BLOCK_in_rewrite_tree_block1107 = new BitSet(new long[]{4L});
      FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block1109 = new BitSet(new long[]{16777216L});
      FOLLOW_EOB_in_rewrite_tree_block1111 = new BitSet(new long[]{8L});
      FOLLOW_ALT_in_rewrite_tree_alternative1130 = new BitSet(new long[]{4L});
      FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative1132 = new BitSet(new long[]{6991875809191755792L});
      FOLLOW_EOA_in_rewrite_tree_alternative1135 = new BitSet(new long[]{8L});
      FOLLOW_rewrite_tree_atom_in_rewrite_tree_element1150 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_in_rewrite_tree_element1155 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_block_in_rewrite_tree_element1162 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element1169 = new BitSet(new long[]{2L});
      FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom1185 = new BitSet(new long[]{2L});
      FOLLOW_TOKEN_REF_in_rewrite_tree_atom1192 = new BitSet(new long[]{2L});
      FOLLOW_TOKEN_REF_in_rewrite_tree_atom1200 = new BitSet(new long[]{4L});
      FOLLOW_ARG_ACTION_in_rewrite_tree_atom1202 = new BitSet(new long[]{8L});
      FOLLOW_RULE_REF_in_rewrite_tree_atom1214 = new BitSet(new long[]{2L});
      FOLLOW_STRING_LITERAL_in_rewrite_tree_atom1221 = new BitSet(new long[]{2L});
      FOLLOW_LABEL_in_rewrite_tree_atom1228 = new BitSet(new long[]{2L});
      FOLLOW_ACTION_in_rewrite_tree_atom1233 = new BitSet(new long[]{2L});
      FOLLOW_OPTIONAL_in_rewrite_tree_ebnf1245 = new BitSet(new long[]{4L});
      FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1247 = new BitSet(new long[]{8L});
      FOLLOW_CLOSURE_in_rewrite_tree_ebnf1256 = new BitSet(new long[]{4L});
      FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1258 = new BitSet(new long[]{8L});
      FOLLOW_POSITIVE_CLOSURE_in_rewrite_tree_ebnf1268 = new BitSet(new long[]{4L});
      FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1270 = new BitSet(new long[]{8L});
      FOLLOW_TREE_BEGIN_in_rewrite_tree1284 = new BitSet(new long[]{4L});
      FOLLOW_rewrite_tree_atom_in_rewrite_tree1286 = new BitSet(new long[]{6991875809183367192L});
      FOLLOW_rewrite_tree_element_in_rewrite_tree1288 = new BitSet(new long[]{6991875809183367192L});
      FOLLOW_TEMPLATE_in_rewrite_template1306 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_rewrite_template1308 = new BitSet(new long[]{1024L});
      FOLLOW_rewrite_template_args_in_rewrite_template1310 = new BitSet(new long[]{6291456L});
      FOLLOW_set_in_rewrite_template1317 = new BitSet(new long[]{8L});
      FOLLOW_rewrite_template_ref_in_rewrite_template1333 = new BitSet(new long[]{2L});
      FOLLOW_rewrite_indirect_template_head_in_rewrite_template1338 = new BitSet(new long[]{2L});
      FOLLOW_ACTION_in_rewrite_template1343 = new BitSet(new long[]{2L});
      FOLLOW_TEMPLATE_in_rewrite_template_ref1357 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_rewrite_template_ref1359 = new BitSet(new long[]{1024L});
      FOLLOW_rewrite_template_args_in_rewrite_template_ref1361 = new BitSet(new long[]{8L});
      FOLLOW_TEMPLATE_in_rewrite_indirect_template_head1376 = new BitSet(new long[]{4L});
      FOLLOW_ACTION_in_rewrite_indirect_template_head1378 = new BitSet(new long[]{1024L});
      FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head1380 = new BitSet(new long[]{8L});
      FOLLOW_ARGLIST_in_rewrite_template_args1393 = new BitSet(new long[]{4L});
      FOLLOW_rewrite_template_arg_in_rewrite_template_args1395 = new BitSet(new long[]{520L});
      FOLLOW_ARGLIST_in_rewrite_template_args1402 = new BitSet(new long[]{2L});
      FOLLOW_ARG_in_rewrite_template_arg1416 = new BitSet(new long[]{4L});
      FOLLOW_ID_in_rewrite_template_arg1418 = new BitSet(new long[]{16L});
      FOLLOW_ACTION_in_rewrite_template_arg1420 = new BitSet(new long[]{8L});
      FOLLOW_ID_in_qid1431 = new BitSet(new long[]{2L, 512L});
      FOLLOW_73_in_qid1434 = new BitSet(new long[]{1073741824L});
      FOLLOW_ID_in_qid1436 = new BitSet(new long[]{2L, 512L});
   }

   protected class DFA48 extends DFA {
      public DFA48(BaseRecognizer recognizer) {
         this.recognizer = recognizer;
         this.decisionNumber = 48;
         this.eot = ANTLRv3Tree.DFA48_eot;
         this.eof = ANTLRv3Tree.DFA48_eof;
         this.min = ANTLRv3Tree.DFA48_min;
         this.max = ANTLRv3Tree.DFA48_max;
         this.accept = ANTLRv3Tree.DFA48_accept;
         this.special = ANTLRv3Tree.DFA48_special;
         this.transition = ANTLRv3Tree.DFA48_transition;
      }

      public String getDescription() {
         return "234:1: rewrite_template : ( ^( TEMPLATE ID rewrite_template_args ( DOUBLE_QUOTE_STRING_LITERAL | DOUBLE_ANGLE_STRING_LITERAL ) ) | rewrite_template_ref | rewrite_indirect_template_head | ACTION );";
      }
   }
}
