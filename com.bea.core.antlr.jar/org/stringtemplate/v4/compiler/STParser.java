package org.stringtemplate.v4.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.IntStream;
import org.antlr.runtime.MismatchedTokenException;
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
import org.stringtemplate.v4.misc.ErrorManager;
import org.stringtemplate.v4.misc.ErrorType;

public class STParser extends Parser {
   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "IF", "ELSE", "ELSEIF", "ENDIF", "SUPER", "SEMI", "BANG", "ELLIPSIS", "EQUALS", "COLON", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "COMMA", "DOT", "LCURLY", "RCURLY", "TEXT", "LDELIM", "RDELIM", "ID", "STRING", "WS", "PIPE", "OR", "AND", "INDENT", "NEWLINE", "AT", "END", "TRUE", "FALSE", "COMMENT", "ARGS", "ELEMENTS", "EXEC_FUNC", "EXPR", "INCLUDE", "INCLUDE_IND", "INCLUDE_REGION", "INCLUDE_SUPER", "INCLUDE_SUPER_REGION", "INDENTED_EXPR", "LIST", "MAP", "NULL", "OPTIONS", "PROP", "PROP_IND", "REGION", "SUBTEMPLATE", "TO_STR", "ZIP"};
   public static final int EOF = -1;
   public static final int RBRACK = 17;
   public static final int LBRACK = 16;
   public static final int ELSE = 5;
   public static final int ELLIPSIS = 11;
   public static final int LCURLY = 20;
   public static final int BANG = 10;
   public static final int EQUALS = 12;
   public static final int TEXT = 22;
   public static final int ID = 25;
   public static final int SEMI = 9;
   public static final int LPAREN = 14;
   public static final int IF = 4;
   public static final int ELSEIF = 6;
   public static final int COLON = 13;
   public static final int RPAREN = 15;
   public static final int WS = 27;
   public static final int COMMA = 18;
   public static final int RCURLY = 21;
   public static final int ENDIF = 7;
   public static final int RDELIM = 24;
   public static final int SUPER = 8;
   public static final int DOT = 19;
   public static final int LDELIM = 23;
   public static final int STRING = 26;
   public static final int PIPE = 28;
   public static final int OR = 29;
   public static final int AND = 30;
   public static final int INDENT = 31;
   public static final int NEWLINE = 32;
   public static final int AT = 33;
   public static final int END = 34;
   public static final int TRUE = 35;
   public static final int FALSE = 36;
   public static final int COMMENT = 37;
   public static final int ARGS = 38;
   public static final int ELEMENTS = 39;
   public static final int EXEC_FUNC = 40;
   public static final int EXPR = 41;
   public static final int INCLUDE = 42;
   public static final int INCLUDE_IND = 43;
   public static final int INCLUDE_REGION = 44;
   public static final int INCLUDE_SUPER = 45;
   public static final int INCLUDE_SUPER_REGION = 46;
   public static final int INDENTED_EXPR = 47;
   public static final int LIST = 48;
   public static final int MAP = 49;
   public static final int NULL = 50;
   public static final int OPTIONS = 51;
   public static final int PROP = 52;
   public static final int PROP_IND = 53;
   public static final int REGION = 54;
   public static final int SUBTEMPLATE = 55;
   public static final int TO_STR = 56;
   public static final int ZIP = 57;
   protected TreeAdaptor adaptor;
   ErrorManager errMgr;
   Token templateToken;
   protected Stack conditional_stack;
   public static final BitSet FOLLOW_template_in_templateAndEOF139 = new BitSet(new long[]{0L});
   public static final BitSet FOLLOW_EOF_in_templateAndEOF141 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_element_in_template155 = new BitSet(new long[]{143893987330L});
   public static final BitSet FOLLOW_INDENT_in_element168 = new BitSet(new long[]{137438953472L});
   public static final BitSet FOLLOW_COMMENT_in_element171 = new BitSet(new long[]{4294967296L});
   public static final BitSet FOLLOW_NEWLINE_in_element173 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_INDENT_in_element181 = new BitSet(new long[]{141746503680L});
   public static final BitSet FOLLOW_singleElement_in_element183 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_singleElement_in_element200 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_compoundElement_in_element205 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_exprTag_in_singleElement216 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TEXT_in_singleElement221 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NEWLINE_in_singleElement226 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_COMMENT_in_singleElement231 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ifstat_in_compoundElement244 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_region_in_compoundElement249 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LDELIM_in_exprTag260 = new BitSet(new long[]{111770943744L});
   public static final BitSet FOLLOW_expr_in_exprTag262 = new BitSet(new long[]{16777728L});
   public static final BitSet FOLLOW_SEMI_in_exprTag266 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_exprOptions_in_exprTag268 = new BitSet(new long[]{16777216L});
   public static final BitSet FOLLOW_RDELIM_in_exprTag273 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_INDENT_in_region305 = new BitSet(new long[]{8388608L});
   public static final BitSet FOLLOW_LDELIM_in_region310 = new BitSet(new long[]{8589934592L});
   public static final BitSet FOLLOW_AT_in_region312 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_ID_in_region314 = new BitSet(new long[]{16777216L});
   public static final BitSet FOLLOW_RDELIM_in_region316 = new BitSet(new long[]{143893987328L});
   public static final BitSet FOLLOW_template_in_region322 = new BitSet(new long[]{2155872256L});
   public static final BitSet FOLLOW_INDENT_in_region326 = new BitSet(new long[]{8388608L});
   public static final BitSet FOLLOW_LDELIM_in_region329 = new BitSet(new long[]{17179869184L});
   public static final BitSet FOLLOW_END_in_region331 = new BitSet(new long[]{16777216L});
   public static final BitSet FOLLOW_RDELIM_in_region333 = new BitSet(new long[]{4294967298L});
   public static final BitSet FOLLOW_NEWLINE_in_region344 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LCURLY_in_subtemplate420 = new BitSet(new long[]{143929638912L});
   public static final BitSet FOLLOW_ID_in_subtemplate426 = new BitSet(new long[]{268697600L});
   public static final BitSet FOLLOW_COMMA_in_subtemplate430 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_ID_in_subtemplate435 = new BitSet(new long[]{268697600L});
   public static final BitSet FOLLOW_PIPE_in_subtemplate440 = new BitSet(new long[]{143896084480L});
   public static final BitSet FOLLOW_template_in_subtemplate445 = new BitSet(new long[]{2149580800L});
   public static final BitSet FOLLOW_INDENT_in_subtemplate447 = new BitSet(new long[]{2097152L});
   public static final BitSet FOLLOW_RCURLY_in_subtemplate450 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_INDENT_in_ifstat491 = new BitSet(new long[]{8388608L});
   public static final BitSet FOLLOW_LDELIM_in_ifstat494 = new BitSet(new long[]{16L});
   public static final BitSet FOLLOW_IF_in_ifstat496 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_ifstat498 = new BitSet(new long[]{111770944768L});
   public static final BitSet FOLLOW_conditional_in_ifstat502 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_ifstat504 = new BitSet(new long[]{16777216L});
   public static final BitSet FOLLOW_RDELIM_in_ifstat506 = new BitSet(new long[]{143893987328L});
   public static final BitSet FOLLOW_template_in_ifstat515 = new BitSet(new long[]{2155872256L});
   public static final BitSet FOLLOW_INDENT_in_ifstat522 = new BitSet(new long[]{8388608L});
   public static final BitSet FOLLOW_LDELIM_in_ifstat525 = new BitSet(new long[]{64L});
   public static final BitSet FOLLOW_ELSEIF_in_ifstat527 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_ifstat529 = new BitSet(new long[]{111770944768L});
   public static final BitSet FOLLOW_conditional_in_ifstat533 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_ifstat535 = new BitSet(new long[]{16777216L});
   public static final BitSet FOLLOW_RDELIM_in_ifstat537 = new BitSet(new long[]{143893987328L});
   public static final BitSet FOLLOW_template_in_ifstat541 = new BitSet(new long[]{2155872256L});
   public static final BitSet FOLLOW_INDENT_in_ifstat551 = new BitSet(new long[]{8388608L});
   public static final BitSet FOLLOW_LDELIM_in_ifstat554 = new BitSet(new long[]{32L});
   public static final BitSet FOLLOW_ELSE_in_ifstat556 = new BitSet(new long[]{16777216L});
   public static final BitSet FOLLOW_RDELIM_in_ifstat558 = new BitSet(new long[]{143893987328L});
   public static final BitSet FOLLOW_template_in_ifstat562 = new BitSet(new long[]{2155872256L});
   public static final BitSet FOLLOW_INDENT_in_ifstat570 = new BitSet(new long[]{8388608L});
   public static final BitSet FOLLOW_LDELIM_in_ifstat576 = new BitSet(new long[]{128L});
   public static final BitSet FOLLOW_ENDIF_in_ifstat578 = new BitSet(new long[]{16777216L});
   public static final BitSet FOLLOW_RDELIM_in_ifstat582 = new BitSet(new long[]{4294967298L});
   public static final BitSet FOLLOW_NEWLINE_in_ifstat593 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_andConditional_in_conditional713 = new BitSet(new long[]{536870914L});
   public static final BitSet FOLLOW_OR_in_conditional717 = new BitSet(new long[]{111770944768L});
   public static final BitSet FOLLOW_andConditional_in_conditional720 = new BitSet(new long[]{536870914L});
   public static final BitSet FOLLOW_notConditional_in_andConditional733 = new BitSet(new long[]{1073741826L});
   public static final BitSet FOLLOW_AND_in_andConditional737 = new BitSet(new long[]{111770944768L});
   public static final BitSet FOLLOW_notConditional_in_andConditional740 = new BitSet(new long[]{1073741826L});
   public static final BitSet FOLLOW_BANG_in_notConditional753 = new BitSet(new long[]{111770944768L});
   public static final BitSet FOLLOW_notConditional_in_notConditional756 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_memberExpr_in_notConditional761 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ID_in_notConditionalExpr773 = new BitSet(new long[]{524290L});
   public static final BitSet FOLLOW_DOT_in_notConditionalExpr784 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_ID_in_notConditionalExpr788 = new BitSet(new long[]{524290L});
   public static final BitSet FOLLOW_DOT_in_notConditionalExpr814 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_notConditionalExpr816 = new BitSet(new long[]{111770943744L});
   public static final BitSet FOLLOW_mapExpr_in_notConditionalExpr818 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_notConditionalExpr820 = new BitSet(new long[]{524290L});
   public static final BitSet FOLLOW_option_in_exprOptions850 = new BitSet(new long[]{262146L});
   public static final BitSet FOLLOW_COMMA_in_exprOptions854 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_option_in_exprOptions856 = new BitSet(new long[]{262146L});
   public static final BitSet FOLLOW_ID_in_option883 = new BitSet(new long[]{4098L});
   public static final BitSet FOLLOW_EQUALS_in_option893 = new BitSet(new long[]{111770943744L});
   public static final BitSet FOLLOW_exprNoComma_in_option895 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_memberExpr_in_exprNoComma1002 = new BitSet(new long[]{8194L});
   public static final BitSet FOLLOW_COLON_in_exprNoComma1008 = new BitSet(new long[]{34619392L});
   public static final BitSet FOLLOW_mapTemplateRef_in_exprNoComma1010 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_mapExpr_in_expr1055 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_memberExpr_in_mapExpr1067 = new BitSet(new long[]{270338L});
   public static final BitSet FOLLOW_COMMA_in_mapExpr1076 = new BitSet(new long[]{111770943744L});
   public static final BitSet FOLLOW_memberExpr_in_mapExpr1078 = new BitSet(new long[]{270336L});
   public static final BitSet FOLLOW_COLON_in_mapExpr1084 = new BitSet(new long[]{34619392L});
   public static final BitSet FOLLOW_mapTemplateRef_in_mapExpr1086 = new BitSet(new long[]{8194L});
   public static final BitSet FOLLOW_COLON_in_mapExpr1149 = new BitSet(new long[]{34619392L});
   public static final BitSet FOLLOW_mapTemplateRef_in_mapExpr1153 = new BitSet(new long[]{270338L});
   public static final BitSet FOLLOW_COMMA_in_mapExpr1159 = new BitSet(new long[]{34619392L});
   public static final BitSet FOLLOW_mapTemplateRef_in_mapExpr1163 = new BitSet(new long[]{270338L});
   public static final BitSet FOLLOW_ID_in_mapTemplateRef1210 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_mapTemplateRef1212 = new BitSet(new long[]{111770978560L});
   public static final BitSet FOLLOW_args_in_mapTemplateRef1214 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_mapTemplateRef1216 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_subtemplate_in_mapTemplateRef1238 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LPAREN_in_mapTemplateRef1245 = new BitSet(new long[]{111770943744L});
   public static final BitSet FOLLOW_mapExpr_in_mapTemplateRef1247 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_mapTemplateRef1251 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_mapTemplateRef1253 = new BitSet(new long[]{111770976512L});
   public static final BitSet FOLLOW_argExprList_in_mapTemplateRef1255 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_mapTemplateRef1258 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_includeExpr_in_memberExpr1281 = new BitSet(new long[]{524290L});
   public static final BitSet FOLLOW_DOT_in_memberExpr1292 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_ID_in_memberExpr1294 = new BitSet(new long[]{524290L});
   public static final BitSet FOLLOW_DOT_in_memberExpr1320 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_memberExpr1322 = new BitSet(new long[]{111770943744L});
   public static final BitSet FOLLOW_mapExpr_in_memberExpr1324 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_memberExpr1326 = new BitSet(new long[]{524290L});
   public static final BitSet FOLLOW_ID_in_includeExpr1370 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_includeExpr1372 = new BitSet(new long[]{111770976512L});
   public static final BitSet FOLLOW_expr_in_includeExpr1374 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_includeExpr1377 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_SUPER_in_includeExpr1398 = new BitSet(new long[]{524288L});
   public static final BitSet FOLLOW_DOT_in_includeExpr1400 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_ID_in_includeExpr1402 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_includeExpr1404 = new BitSet(new long[]{111770978560L});
   public static final BitSet FOLLOW_args_in_includeExpr1406 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_includeExpr1408 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ID_in_includeExpr1427 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_includeExpr1429 = new BitSet(new long[]{111770978560L});
   public static final BitSet FOLLOW_args_in_includeExpr1431 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_includeExpr1433 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_AT_in_includeExpr1455 = new BitSet(new long[]{256L});
   public static final BitSet FOLLOW_SUPER_in_includeExpr1457 = new BitSet(new long[]{524288L});
   public static final BitSet FOLLOW_DOT_in_includeExpr1459 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_ID_in_includeExpr1461 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_includeExpr1463 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_includeExpr1467 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_AT_in_includeExpr1482 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_ID_in_includeExpr1484 = new BitSet(new long[]{16384L});
   public static final BitSet FOLLOW_LPAREN_in_includeExpr1486 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_includeExpr1490 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_primary_in_includeExpr1508 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ID_in_primary1519 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_in_primary1524 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TRUE_in_primary1529 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FALSE_in_primary1534 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_subtemplate_in_primary1539 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_list_in_primary1544 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LPAREN_in_primary1553 = new BitSet(new long[]{111770944768L});
   public static final BitSet FOLLOW_conditional_in_primary1556 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_primary1558 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LPAREN_in_primary1569 = new BitSet(new long[]{111770943744L});
   public static final BitSet FOLLOW_expr_in_primary1571 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_primary1573 = new BitSet(new long[]{16386L});
   public static final BitSet FOLLOW_LPAREN_in_primary1579 = new BitSet(new long[]{111770976512L});
   public static final BitSet FOLLOW_argExprList_in_primary1581 = new BitSet(new long[]{32768L});
   public static final BitSet FOLLOW_RPAREN_in_primary1584 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_argExprList_in_args1640 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_namedArg_in_args1645 = new BitSet(new long[]{262146L});
   public static final BitSet FOLLOW_COMMA_in_args1649 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_namedArg_in_args1651 = new BitSet(new long[]{262146L});
   public static final BitSet FOLLOW_COMMA_in_args1657 = new BitSet(new long[]{2048L});
   public static final BitSet FOLLOW_ELLIPSIS_in_args1659 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ELLIPSIS_in_args1679 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_arg_in_argExprList1692 = new BitSet(new long[]{262146L});
   public static final BitSet FOLLOW_COMMA_in_argExprList1696 = new BitSet(new long[]{111770943744L});
   public static final BitSet FOLLOW_arg_in_argExprList1698 = new BitSet(new long[]{262146L});
   public static final BitSet FOLLOW_exprNoComma_in_arg1715 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ID_in_namedArg1724 = new BitSet(new long[]{4096L});
   public static final BitSet FOLLOW_EQUALS_in_namedArg1726 = new BitSet(new long[]{111770943744L});
   public static final BitSet FOLLOW_arg_in_namedArg1728 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LBRACK_in_list1753 = new BitSet(new long[]{131072L});
   public static final BitSet FOLLOW_RBRACK_in_list1755 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LBRACK_in_list1767 = new BitSet(new long[]{111771336960L});
   public static final BitSet FOLLOW_listElement_in_list1769 = new BitSet(new long[]{393216L});
   public static final BitSet FOLLOW_COMMA_in_list1773 = new BitSet(new long[]{111771336960L});
   public static final BitSet FOLLOW_listElement_in_list1775 = new BitSet(new long[]{393216L});
   public static final BitSet FOLLOW_RBRACK_in_list1780 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_exprNoComma_in_listElement1800 = new BitSet(new long[]{2L});

   public Parser[] getDelegates() {
      return new Parser[0];
   }

   public STParser(TokenStream input) {
      this(input, new RecognizerSharedState());
   }

   public STParser(TokenStream input, RecognizerSharedState state) {
      super(input, state);
      this.adaptor = new CommonTreeAdaptor();
      this.conditional_stack = new Stack();
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
      return "org\\stringtemplate\\v4\\compiler\\STParser.g";
   }

   public STParser(TokenStream input, ErrorManager errMgr, Token templateToken) {
      this(input);
      this.errMgr = errMgr;
      this.templateToken = templateToken;
   }

   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException {
      throw new MismatchedTokenException(ttype, input);
   }

   public final templateAndEOF_return templateAndEOF() throws RecognitionException {
      templateAndEOF_return retval = new templateAndEOF_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken EOF2 = null;
      ParserRuleReturnScope template1 = null;
      CommonTree EOF2_tree = null;
      RewriteRuleTokenStream stream_EOF = new RewriteRuleTokenStream(this.adaptor, "token EOF");
      RewriteRuleSubtreeStream stream_template = new RewriteRuleSubtreeStream(this.adaptor, "rule template");

      try {
         this.pushFollow(FOLLOW_template_in_templateAndEOF139);
         template1 = this.template();
         --this.state._fsp;
         stream_template.add(template1.getTree());
         EOF2 = (CommonToken)this.match(this.input, -1, FOLLOW_EOF_in_templateAndEOF141);
         stream_EOF.add(EOF2);
         retval.tree = root_0;
         new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
         root_0 = (CommonTree)this.adaptor.nil();
         if (stream_template.hasNext()) {
            this.adaptor.addChild(root_0, stream_template.nextTree());
         }

         stream_template.reset();
         retval.tree = root_0;
         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var12) {
         throw var12;
      } finally {
         ;
      }
   }

   public final template_return template() throws RecognitionException {
      template_return retval = new template_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope element3 = null;

      try {
         root_0 = (CommonTree)this.adaptor.nil();

         while(true) {
            int alt1 = 2;
            int LA1_2;
            switch (this.input.LA(1)) {
               case 22:
               case 32:
               case 37:
                  alt1 = 1;
                  break;
               case 23:
                  LA1_2 = this.input.LA(2);
                  if (LA1_2 == 4 || LA1_2 == 8 || LA1_2 == 14 || LA1_2 == 16 || LA1_2 == 20 || LA1_2 >= 25 && LA1_2 <= 26 || LA1_2 == 33 || LA1_2 >= 35 && LA1_2 <= 36) {
                     alt1 = 1;
                  }
                  break;
               case 31:
                  LA1_2 = this.input.LA(2);
                  if (LA1_2 != 23) {
                     if (LA1_2 == 22 || LA1_2 == 32 || LA1_2 == 37) {
                        alt1 = 1;
                     }
                  } else {
                     int LA1_5 = this.input.LA(3);
                     if (LA1_5 == 4 || LA1_5 == 8 || LA1_5 == 14 || LA1_5 == 16 || LA1_5 == 20 || LA1_5 >= 25 && LA1_5 <= 26 || LA1_5 == 33 || LA1_5 >= 35 && LA1_5 <= 36) {
                        alt1 = 1;
                     }
                  }
            }

            switch (alt1) {
               case 1:
                  this.pushFollow(FOLLOW_element_in_template155);
                  element3 = this.element();
                  --this.state._fsp;
                  this.adaptor.addChild(root_0, element3.getTree());
                  break;
               default:
                  retval.stop = this.input.LT(-1);
                  retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                  this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  return retval;
            }
         }
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public final element_return element() throws RecognitionException {
      element_return retval = new element_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken INDENT4 = null;
      CommonToken COMMENT5 = null;
      CommonToken NEWLINE6 = null;
      CommonToken INDENT7 = null;
      ParserRuleReturnScope singleElement8 = null;
      ParserRuleReturnScope singleElement9 = null;
      ParserRuleReturnScope compoundElement10 = null;
      CommonTree INDENT4_tree = null;
      CommonTree COMMENT5_tree = null;
      CommonTree NEWLINE6_tree = null;
      CommonTree INDENT7_tree = null;
      RewriteRuleTokenStream stream_INDENT = new RewriteRuleTokenStream(this.adaptor, "token INDENT");
      RewriteRuleTokenStream stream_NEWLINE = new RewriteRuleTokenStream(this.adaptor, "token NEWLINE");
      RewriteRuleTokenStream stream_COMMENT = new RewriteRuleTokenStream(this.adaptor, "token COMMENT");
      RewriteRuleSubtreeStream stream_singleElement = new RewriteRuleSubtreeStream(this.adaptor, "rule singleElement");

      try {
         int nvaeMark;
         byte alt3;
         int alt3 = true;
         int LA3_12;
         NoViableAltException nvae;
         int nvaeConsume;
         int nvaeMark;
         NoViableAltException nvae;
         NoViableAltException nvae;
         label2639:
         switch (this.input.LA(1)) {
            case 22:
            case 32:
               alt3 = 3;
               break;
            case 23:
               switch (this.input.LA(2)) {
                  case 4:
                     alt3 = 4;
                     break label2639;
                  case 5:
                  case 6:
                  case 7:
                  case 9:
                  case 10:
                  case 11:
                  case 12:
                  case 13:
                  case 15:
                  case 17:
                  case 18:
                  case 19:
                  case 21:
                  case 22:
                  case 23:
                  case 24:
                  case 27:
                  case 28:
                  case 29:
                  case 30:
                  case 31:
                  case 32:
                  case 34:
                  default:
                     LA3_12 = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 3, 3, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA3_12);
                     }
                  case 8:
                  case 14:
                  case 16:
                  case 20:
                  case 25:
                  case 26:
                  case 35:
                  case 36:
                     alt3 = 3;
                     break label2639;
                  case 33:
                     LA3_12 = this.input.LA(3);
                     if (LA3_12 == 25) {
                        nvaeMark = this.input.LA(4);
                        if (nvaeMark == 24) {
                           alt3 = 4;
                        } else {
                           if (nvaeMark != 14) {
                              nvaeMark = this.input.mark();

                              try {
                                 for(nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                                    this.input.consume();
                                 }

                                 nvae = new NoViableAltException("", 3, 14, this.input);
                                 throw nvae;
                              } finally {
                                 this.input.rewind(nvaeMark);
                              }
                           }

                           alt3 = 3;
                        }
                     } else {
                        if (LA3_12 != 8) {
                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 3, 10, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                        }

                        alt3 = 3;
                     }
                     break label2639;
               }
            case 31:
               switch (this.input.LA(2)) {
                  case 22:
                  case 32:
                     alt3 = 2;
                     break label2639;
                  case 23:
                     switch (this.input.LA(3)) {
                        case 4:
                           alt3 = 4;
                           break label2639;
                        case 5:
                        case 6:
                        case 7:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 15:
                        case 17:
                        case 18:
                        case 19:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 27:
                        case 28:
                        case 29:
                        case 30:
                        case 31:
                        case 32:
                        case 34:
                        default:
                           LA3_12 = this.input.mark();

                           try {
                              for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 3, 6, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(LA3_12);
                           }
                        case 8:
                        case 14:
                        case 16:
                        case 20:
                        case 25:
                        case 26:
                        case 35:
                        case 36:
                           alt3 = 2;
                           break label2639;
                        case 33:
                           LA3_12 = this.input.LA(4);
                           if (LA3_12 == 25) {
                              nvaeMark = this.input.LA(5);
                              if (nvaeMark == 24) {
                                 alt3 = 4;
                              } else {
                                 if (nvaeMark != 14) {
                                    nvaeMark = this.input.mark();

                                    try {
                                       for(nvaeConsume = 0; nvaeConsume < 4; ++nvaeConsume) {
                                          this.input.consume();
                                       }

                                       nvae = new NoViableAltException("", 3, 15, this.input);
                                       throw nvae;
                                    } finally {
                                       this.input.rewind(nvaeMark);
                                    }
                                 }

                                 alt3 = 2;
                              }
                           } else {
                              if (LA3_12 != 8) {
                                 nvaeMark = this.input.mark();

                                 try {
                                    for(nvaeMark = 0; nvaeMark < 3; ++nvaeMark) {
                                       this.input.consume();
                                    }

                                    nvae = new NoViableAltException("", 3, 12, this.input);
                                    throw nvae;
                                 } finally {
                                    this.input.rewind(nvaeMark);
                                 }
                              }

                              alt3 = 2;
                           }
                           break label2639;
                     }
                  case 37:
                     LA3_12 = this.input.LA(3);
                     if (LA3_12 == 32) {
                        nvaeMark = this.input.LA(4);
                        if (this.input.LT(1).getCharPositionInLine() == 0) {
                           alt3 = 1;
                        } else {
                           alt3 = 2;
                        }
                     } else {
                        if (LA3_12 != -1 && (LA3_12 < 21 || LA3_12 > 23) && LA3_12 != 31 && LA3_12 != 37) {
                           nvaeMark = this.input.mark();

                           try {
                              for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                                 this.input.consume();
                              }

                              nvae = new NoViableAltException("", 3, 5, this.input);
                              throw nvae;
                           } finally {
                              this.input.rewind(nvaeMark);
                           }
                        }

                        alt3 = 2;
                     }
                     break label2639;
                  default:
                     LA3_12 = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 3, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA3_12);
                     }
               }
            case 37:
               LA3_12 = this.input.LA(2);
               if (LA3_12 == 32) {
                  nvaeMark = this.input.LA(3);
                  if (this.input.LT(1).getCharPositionInLine() == 0) {
                     alt3 = 1;
                  } else {
                     alt3 = 3;
                  }
               } else {
                  if (LA3_12 != -1 && (LA3_12 < 21 || LA3_12 > 23) && LA3_12 != 31 && LA3_12 != 37) {
                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 3, 2, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt3 = 3;
               }
               break;
            default:
               NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);
               throw nvae;
         }

         label2550:
         switch (alt3) {
            case 1:
               if (this.input.LT(1).getCharPositionInLine() != 0) {
                  throw new FailedPredicateException(this.input, "element", "input.LT(1).getCharPositionInLine()==0");
               }

               int alt2 = 2;
               nvaeMark = this.input.LA(1);
               if (nvaeMark == 31) {
                  alt2 = 1;
               }

               switch (alt2) {
                  case 1:
                     INDENT4 = (CommonToken)this.match(this.input, 31, FOLLOW_INDENT_in_element168);
                     stream_INDENT.add(INDENT4);
                  default:
                     COMMENT5 = (CommonToken)this.match(this.input, 37, FOLLOW_COMMENT_in_element171);
                     stream_COMMENT.add(COMMENT5);
                     NEWLINE6 = (CommonToken)this.match(this.input, 32, FOLLOW_NEWLINE_in_element173);
                     stream_NEWLINE.add(NEWLINE6);
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     root_0 = null;
                     retval.tree = root_0;
                     break label2550;
               }
            case 2:
               INDENT7 = (CommonToken)this.match(this.input, 31, FOLLOW_INDENT_in_element181);
               stream_INDENT.add(INDENT7);
               this.pushFollow(FOLLOW_singleElement_in_element183);
               singleElement8 = this.singleElement();
               --this.state._fsp;
               stream_singleElement.add(singleElement8.getTree());
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(47, (String)"INDENTED_EXPR")), root_1);
               this.adaptor.addChild(root_1, stream_INDENT.nextNode());
               if (stream_singleElement.hasNext()) {
                  this.adaptor.addChild(root_1, stream_singleElement.nextTree());
               }

               stream_singleElement.reset();
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
               break;
            case 3:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_singleElement_in_element200);
               singleElement9 = this.singleElement();
               --this.state._fsp;
               this.adaptor.addChild(root_0, singleElement9.getTree());
               break;
            case 4:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_compoundElement_in_element205);
               compoundElement10 = this.compoundElement();
               --this.state._fsp;
               this.adaptor.addChild(root_0, compoundElement10.getTree());
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var151) {
         throw var151;
      } finally {
         ;
      }
   }

   public final singleElement_return singleElement() throws RecognitionException {
      singleElement_return retval = new singleElement_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken TEXT12 = null;
      CommonToken NEWLINE13 = null;
      CommonToken COMMENT14 = null;
      ParserRuleReturnScope exprTag11 = null;
      CommonTree TEXT12_tree = null;
      CommonTree NEWLINE13_tree = null;
      CommonTree COMMENT14_tree = null;

      try {
         int alt4 = true;
         byte alt4;
         switch (this.input.LA(1)) {
            case 22:
               alt4 = 2;
               break;
            case 23:
               alt4 = 1;
               break;
            case 32:
               alt4 = 3;
               break;
            case 37:
               alt4 = 4;
               break;
            default:
               NoViableAltException nvae = new NoViableAltException("", 4, 0, this.input);
               throw nvae;
         }

         switch (alt4) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_exprTag_in_singleElement216);
               exprTag11 = this.exprTag();
               --this.state._fsp;
               this.adaptor.addChild(root_0, exprTag11.getTree());
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               TEXT12 = (CommonToken)this.match(this.input, 22, FOLLOW_TEXT_in_singleElement221);
               TEXT12_tree = (CommonTree)this.adaptor.create(TEXT12);
               this.adaptor.addChild(root_0, TEXT12_tree);
               break;
            case 3:
               root_0 = (CommonTree)this.adaptor.nil();
               NEWLINE13 = (CommonToken)this.match(this.input, 32, FOLLOW_NEWLINE_in_singleElement226);
               NEWLINE13_tree = (CommonTree)this.adaptor.create(NEWLINE13);
               this.adaptor.addChild(root_0, NEWLINE13_tree);
               break;
            case 4:
               root_0 = (CommonTree)this.adaptor.nil();
               COMMENT14 = (CommonToken)this.match(this.input, 37, FOLLOW_COMMENT_in_singleElement231);
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var15) {
         throw var15;
      } finally {
         ;
      }
   }

   public final compoundElement_return compoundElement() throws RecognitionException {
      compoundElement_return retval = new compoundElement_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope ifstat15 = null;
      ParserRuleReturnScope region16 = null;

      try {
         int alt5 = true;
         int LA5_0 = this.input.LA(1);
         int LA5_2;
         int nvaeMark;
         byte alt5;
         NoViableAltException nvae;
         if (LA5_0 == 31) {
            LA5_2 = this.input.LA(2);
            if (LA5_2 != 23) {
               nvaeMark = this.input.mark();

               try {
                  this.input.consume();
                  nvae = new NoViableAltException("", 5, 1, this.input);
                  throw nvae;
               } finally {
                  this.input.rewind(nvaeMark);
               }
            }

            nvaeMark = this.input.LA(3);
            if (nvaeMark == 4) {
               alt5 = 1;
            } else {
               if (nvaeMark != 33) {
                  int nvaeMark = this.input.mark();

                  try {
                     for(int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                        this.input.consume();
                     }

                     NoViableAltException nvae = new NoViableAltException("", 5, 2, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               alt5 = 2;
            }
         } else {
            if (LA5_0 != 23) {
               NoViableAltException nvae = new NoViableAltException("", 5, 0, this.input);
               throw nvae;
            }

            LA5_2 = this.input.LA(2);
            if (LA5_2 == 4) {
               alt5 = 1;
            } else {
               if (LA5_2 != 33) {
                  nvaeMark = this.input.mark();

                  try {
                     this.input.consume();
                     nvae = new NoViableAltException("", 5, 2, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               alt5 = 2;
            }
         }

         switch (alt5) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_ifstat_in_compoundElement244);
               ifstat15 = this.ifstat();
               --this.state._fsp;
               this.adaptor.addChild(root_0, ifstat15.getTree());
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_region_in_compoundElement249);
               region16 = this.region();
               --this.state._fsp;
               this.adaptor.addChild(root_0, region16.getTree());
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var38) {
         throw var38;
      } finally {
         ;
      }
   }

   public final exprTag_return exprTag() throws RecognitionException {
      exprTag_return retval = new exprTag_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken LDELIM17 = null;
      CommonToken char_literal19 = null;
      CommonToken RDELIM21 = null;
      ParserRuleReturnScope expr18 = null;
      ParserRuleReturnScope exprOptions20 = null;
      CommonTree LDELIM17_tree = null;
      CommonTree char_literal19_tree = null;
      CommonTree RDELIM21_tree = null;
      RewriteRuleTokenStream stream_RDELIM = new RewriteRuleTokenStream(this.adaptor, "token RDELIM");
      RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
      RewriteRuleTokenStream stream_LDELIM = new RewriteRuleTokenStream(this.adaptor, "token LDELIM");
      RewriteRuleSubtreeStream stream_exprOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule exprOptions");
      RewriteRuleSubtreeStream stream_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule expr");

      try {
         LDELIM17 = (CommonToken)this.match(this.input, 23, FOLLOW_LDELIM_in_exprTag260);
         stream_LDELIM.add(LDELIM17);
         this.pushFollow(FOLLOW_expr_in_exprTag262);
         expr18 = this.expr();
         --this.state._fsp;
         stream_expr.add(expr18.getTree());
         int alt6 = 2;
         int LA6_0 = this.input.LA(1);
         if (LA6_0 == 9) {
            alt6 = 1;
         }

         switch (alt6) {
            case 1:
               char_literal19 = (CommonToken)this.match(this.input, 9, FOLLOW_SEMI_in_exprTag266);
               stream_SEMI.add(char_literal19);
               this.pushFollow(FOLLOW_exprOptions_in_exprTag268);
               exprOptions20 = this.exprOptions();
               --this.state._fsp;
               stream_exprOptions.add(exprOptions20.getTree());
            default:
               RDELIM21 = (CommonToken)this.match(this.input, 24, FOLLOW_RDELIM_in_exprTag273);
               stream_RDELIM.add(RDELIM21);
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(41, LDELIM17, "EXPR")), root_1);
               this.adaptor.addChild(root_1, stream_expr.nextTree());
               if (stream_exprOptions.hasNext()) {
                  this.adaptor.addChild(root_1, stream_exprOptions.nextTree());
               }

               stream_exprOptions.reset();
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
               retval.stop = this.input.LT(-1);
               retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
               this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
               return retval;
         }
      } catch (RecognitionException var23) {
         throw var23;
      } finally {
         ;
      }
   }

   public final region_return region() throws RecognitionException {
      region_return retval = new region_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken i = null;
      CommonToken x = null;
      CommonToken char_literal22 = null;
      CommonToken ID23 = null;
      CommonToken RDELIM24 = null;
      CommonToken INDENT26 = null;
      CommonToken LDELIM27 = null;
      CommonToken string_literal28 = null;
      CommonToken RDELIM29 = null;
      CommonToken NEWLINE30 = null;
      ParserRuleReturnScope template25 = null;
      CommonTree i_tree = null;
      CommonTree x_tree = null;
      CommonTree char_literal22_tree = null;
      CommonTree ID23_tree = null;
      CommonTree RDELIM24_tree = null;
      CommonTree INDENT26_tree = null;
      CommonTree LDELIM27_tree = null;
      CommonTree string_literal28_tree = null;
      CommonTree RDELIM29_tree = null;
      CommonTree NEWLINE30_tree = null;
      RewriteRuleTokenStream stream_INDENT = new RewriteRuleTokenStream(this.adaptor, "token INDENT");
      RewriteRuleTokenStream stream_RDELIM = new RewriteRuleTokenStream(this.adaptor, "token RDELIM");
      RewriteRuleTokenStream stream_AT = new RewriteRuleTokenStream(this.adaptor, "token AT");
      RewriteRuleTokenStream stream_NEWLINE = new RewriteRuleTokenStream(this.adaptor, "token NEWLINE");
      RewriteRuleTokenStream stream_END = new RewriteRuleTokenStream(this.adaptor, "token END");
      RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
      RewriteRuleTokenStream stream_LDELIM = new RewriteRuleTokenStream(this.adaptor, "token LDELIM");
      RewriteRuleSubtreeStream stream_template = new RewriteRuleSubtreeStream(this.adaptor, "rule template");
      Token indent = null;

      try {
         int alt7 = 2;
         int LA7_0 = this.input.LA(1);
         if (LA7_0 == 31) {
            alt7 = 1;
         }

         switch (alt7) {
            case 1:
               i = (CommonToken)this.match(this.input, 31, FOLLOW_INDENT_in_region305);
               stream_INDENT.add(i);
            default:
               x = (CommonToken)this.match(this.input, 23, FOLLOW_LDELIM_in_region310);
               stream_LDELIM.add(x);
               char_literal22 = (CommonToken)this.match(this.input, 33, FOLLOW_AT_in_region312);
               stream_AT.add(char_literal22);
               ID23 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_region314);
               stream_ID.add(ID23);
               RDELIM24 = (CommonToken)this.match(this.input, 24, FOLLOW_RDELIM_in_region316);
               stream_RDELIM.add(RDELIM24);
               if (this.input.LA(1) != 32) {
                  indent = i;
               }

               this.pushFollow(FOLLOW_template_in_region322);
               template25 = this.template();
               --this.state._fsp;
               stream_template.add(template25.getTree());
               int alt8 = 2;
               int LA8_0 = this.input.LA(1);
               if (LA8_0 == 31) {
                  alt8 = 1;
               }

               switch (alt8) {
                  case 1:
                     INDENT26 = (CommonToken)this.match(this.input, 31, FOLLOW_INDENT_in_region326);
                     stream_INDENT.add(INDENT26);
                  default:
                     LDELIM27 = (CommonToken)this.match(this.input, 23, FOLLOW_LDELIM_in_region329);
                     stream_LDELIM.add(LDELIM27);
                     string_literal28 = (CommonToken)this.match(this.input, 34, FOLLOW_END_in_region331);
                     stream_END.add(string_literal28);
                     RDELIM29 = (CommonToken)this.match(this.input, 24, FOLLOW_RDELIM_in_region333);
                     stream_RDELIM.add(RDELIM29);
                     int alt9 = 2;
                     int LA9_0 = this.input.LA(1);
                     if (LA9_0 == 32) {
                        int LA9_1 = this.input.LA(2);
                        if (((CommonToken)retval.start).getLine() != this.input.LT(1).getLine()) {
                           alt9 = 1;
                        }
                     }

                     switch (alt9) {
                        case 1:
                           if (((CommonToken)retval.start).getLine() == this.input.LT(1).getLine()) {
                              throw new FailedPredicateException(this.input, "region", "$region.start.getLine()!=input.LT(1).getLine()");
                           } else {
                              NEWLINE30 = (CommonToken)this.match(this.input, 32, FOLLOW_NEWLINE_in_region344);
                              stream_NEWLINE.add(NEWLINE30);
                           }
                        default:
                           retval.tree = root_0;
                           RewriteRuleTokenStream stream_i = new RewriteRuleTokenStream(this.adaptor, "token i", i);
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1;
                           if (indent != null) {
                              root_1 = (CommonTree)this.adaptor.nil();
                              root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(47, (String)"INDENTED_EXPR")), root_1);
                              this.adaptor.addChild(root_1, stream_i.nextNode());
                              CommonTree root_2 = (CommonTree)this.adaptor.nil();
                              root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(54, (Token)x)), root_2);
                              this.adaptor.addChild(root_2, stream_ID.nextNode());
                              if (stream_template.hasNext()) {
                                 this.adaptor.addChild(root_2, stream_template.nextTree());
                              }

                              stream_template.reset();
                              this.adaptor.addChild(root_1, root_2);
                              this.adaptor.addChild(root_0, root_1);
                           } else {
                              root_1 = (CommonTree)this.adaptor.nil();
                              root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(54, (Token)x)), root_1);
                              this.adaptor.addChild(root_1, stream_ID.nextNode());
                              if (stream_template.hasNext()) {
                                 this.adaptor.addChild(root_1, stream_template.nextTree());
                              }

                              stream_template.reset();
                              this.adaptor.addChild(root_0, root_1);
                           }

                           retval.tree = root_0;
                           retval.stop = this.input.LT(-1);
                           retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                           this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                           return retval;
                     }
               }
         }
      } catch (RecognitionException var46) {
         throw var46;
      } finally {
         ;
      }
   }

   public final subtemplate_return subtemplate() throws RecognitionException {
      subtemplate_return retval = new subtemplate_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken lc = null;
      CommonToken char_literal31 = null;
      CommonToken char_literal32 = null;
      CommonToken INDENT34 = null;
      CommonToken char_literal35 = null;
      CommonToken ids = null;
      List list_ids = null;
      ParserRuleReturnScope template33 = null;
      CommonTree lc_tree = null;
      CommonTree char_literal31_tree = null;
      CommonTree char_literal32_tree = null;
      CommonTree INDENT34_tree = null;
      CommonTree char_literal35_tree = null;
      CommonTree ids_tree = null;
      RewriteRuleTokenStream stream_INDENT = new RewriteRuleTokenStream(this.adaptor, "token INDENT");
      RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
      RewriteRuleTokenStream stream_LCURLY = new RewriteRuleTokenStream(this.adaptor, "token LCURLY");
      RewriteRuleTokenStream stream_PIPE = new RewriteRuleTokenStream(this.adaptor, "token PIPE");
      RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
      RewriteRuleTokenStream stream_RCURLY = new RewriteRuleTokenStream(this.adaptor, "token RCURLY");
      RewriteRuleSubtreeStream stream_template = new RewriteRuleSubtreeStream(this.adaptor, "rule template");

      try {
         lc = (CommonToken)this.match(this.input, 20, FOLLOW_LCURLY_in_subtemplate420);
         stream_LCURLY.add(lc);
         int alt11 = 2;
         int LA11_0 = this.input.LA(1);
         if (LA11_0 == 25) {
            alt11 = 1;
         }

         byte alt10;
         int LA10_0;
         switch (alt11) {
            case 1:
               ids = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_subtemplate426);
               stream_ID.add(ids);
               if (list_ids == null) {
                  list_ids = new ArrayList();
               }

               list_ids.add(ids);

               label132:
               while(true) {
                  alt10 = 2;
                  LA10_0 = this.input.LA(1);
                  if (LA10_0 == 18) {
                     alt10 = 1;
                  }

                  switch (alt10) {
                     case 1:
                        char_literal31 = (CommonToken)this.match(this.input, 18, FOLLOW_COMMA_in_subtemplate430);
                        stream_COMMA.add(char_literal31);
                        ids = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_subtemplate435);
                        stream_ID.add(ids);
                        if (list_ids == null) {
                           list_ids = new ArrayList();
                        }

                        list_ids.add(ids);
                        break;
                     default:
                        char_literal32 = (CommonToken)this.match(this.input, 28, FOLLOW_PIPE_in_subtemplate440);
                        stream_PIPE.add(char_literal32);
                        break label132;
                  }
               }
            default:
               this.pushFollow(FOLLOW_template_in_subtemplate445);
               template33 = this.template();
               --this.state._fsp;
               stream_template.add(template33.getTree());
               alt10 = 2;
               LA10_0 = this.input.LA(1);
               if (LA10_0 == 31) {
                  alt10 = 1;
               }

               switch (alt10) {
                  case 1:
                     INDENT34 = (CommonToken)this.match(this.input, 31, FOLLOW_INDENT_in_subtemplate447);
                     stream_INDENT.add(INDENT34);
                  default:
                     char_literal35 = (CommonToken)this.match(this.input, 21, FOLLOW_RCURLY_in_subtemplate450);
                     stream_RCURLY.add(char_literal35);
                     retval.tree = root_0;
                     RewriteRuleTokenStream stream_ids = new RewriteRuleTokenStream(this.adaptor, "token ids", list_ids);
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(55, lc, "SUBTEMPLATE")), root_1);

                     while(stream_ids.hasNext()) {
                        CommonTree root_2 = (CommonTree)this.adaptor.nil();
                        root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(38, (String)"ARGS")), root_2);
                        this.adaptor.addChild(root_2, stream_ids.nextNode());
                        this.adaptor.addChild(root_1, root_2);
                     }

                     stream_ids.reset();
                     if (stream_template.hasNext()) {
                        this.adaptor.addChild(root_1, stream_template.nextTree());
                     }

                     stream_template.reset();
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                     retval.stop = this.input.LT(-1);
                     retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                     this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                     return retval;
               }
         }
      } catch (RecognitionException var35) {
         throw var35;
      } finally {
         ;
      }
   }

   public final ifstat_return ifstat() throws RecognitionException {
      ifstat_return retval = new ifstat_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken i = null;
      CommonToken endif = null;
      CommonToken LDELIM36 = null;
      CommonToken string_literal37 = null;
      CommonToken char_literal38 = null;
      CommonToken char_literal39 = null;
      CommonToken RDELIM40 = null;
      CommonToken INDENT41 = null;
      CommonToken LDELIM42 = null;
      CommonToken string_literal43 = null;
      CommonToken char_literal44 = null;
      CommonToken char_literal45 = null;
      CommonToken RDELIM46 = null;
      CommonToken INDENT47 = null;
      CommonToken LDELIM48 = null;
      CommonToken string_literal49 = null;
      CommonToken RDELIM50 = null;
      CommonToken INDENT51 = null;
      CommonToken string_literal52 = null;
      CommonToken RDELIM53 = null;
      CommonToken NEWLINE54 = null;
      List list_c2 = null;
      List list_t2 = null;
      ParserRuleReturnScope c1 = null;
      ParserRuleReturnScope t1 = null;
      ParserRuleReturnScope t3 = null;
      RuleReturnScope c2 = null;
      RuleReturnScope t2 = null;
      CommonTree i_tree = null;
      CommonTree endif_tree = null;
      CommonTree LDELIM36_tree = null;
      CommonTree string_literal37_tree = null;
      CommonTree char_literal38_tree = null;
      CommonTree char_literal39_tree = null;
      CommonTree RDELIM40_tree = null;
      CommonTree INDENT41_tree = null;
      CommonTree LDELIM42_tree = null;
      CommonTree string_literal43_tree = null;
      CommonTree char_literal44_tree = null;
      CommonTree char_literal45_tree = null;
      CommonTree RDELIM46_tree = null;
      CommonTree INDENT47_tree = null;
      CommonTree LDELIM48_tree = null;
      CommonTree string_literal49_tree = null;
      CommonTree RDELIM50_tree = null;
      CommonTree INDENT51_tree = null;
      CommonTree string_literal52_tree = null;
      CommonTree RDELIM53_tree = null;
      CommonTree NEWLINE54_tree = null;
      RewriteRuleTokenStream stream_INDENT = new RewriteRuleTokenStream(this.adaptor, "token INDENT");
      RewriteRuleTokenStream stream_RDELIM = new RewriteRuleTokenStream(this.adaptor, "token RDELIM");
      RewriteRuleTokenStream stream_ELSEIF = new RewriteRuleTokenStream(this.adaptor, "token ELSEIF");
      RewriteRuleTokenStream stream_NEWLINE = new RewriteRuleTokenStream(this.adaptor, "token NEWLINE");
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleTokenStream stream_ENDIF = new RewriteRuleTokenStream(this.adaptor, "token ENDIF");
      RewriteRuleTokenStream stream_ELSE = new RewriteRuleTokenStream(this.adaptor, "token ELSE");
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleTokenStream stream_LDELIM = new RewriteRuleTokenStream(this.adaptor, "token LDELIM");
      RewriteRuleTokenStream stream_IF = new RewriteRuleTokenStream(this.adaptor, "token IF");
      RewriteRuleSubtreeStream stream_template = new RewriteRuleSubtreeStream(this.adaptor, "rule template");
      RewriteRuleSubtreeStream stream_conditional = new RewriteRuleSubtreeStream(this.adaptor, "rule conditional");
      Token indent = null;

      try {
         int alt13 = 2;
         int LA13_0 = this.input.LA(1);
         if (LA13_0 == 31) {
            alt13 = 1;
         }

         switch (alt13) {
            case 1:
               i = (CommonToken)this.match(this.input, 31, FOLLOW_INDENT_in_ifstat491);
               stream_INDENT.add(i);
            default:
               LDELIM36 = (CommonToken)this.match(this.input, 23, FOLLOW_LDELIM_in_ifstat494);
               stream_LDELIM.add(LDELIM36);
               string_literal37 = (CommonToken)this.match(this.input, 4, FOLLOW_IF_in_ifstat496);
               stream_IF.add(string_literal37);
               char_literal38 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_ifstat498);
               stream_LPAREN.add(char_literal38);
               this.pushFollow(FOLLOW_conditional_in_ifstat502);
               c1 = this.conditional();
               --this.state._fsp;
               stream_conditional.add(c1.getTree());
               char_literal39 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_ifstat504);
               stream_RPAREN.add(char_literal39);
               RDELIM40 = (CommonToken)this.match(this.input, 24, FOLLOW_RDELIM_in_ifstat506);
               stream_RDELIM.add(RDELIM40);
               if (this.input.LA(1) != 32) {
                  indent = i;
               }

               this.pushFollow(FOLLOW_template_in_ifstat515);
               t1 = this.template();
               --this.state._fsp;
               stream_template.add(t1.getTree());

               while(true) {
                  int alt17 = 2;
                  int LA17_0 = this.input.LA(1);
                  int LA17_2;
                  int LA16_0;
                  if (LA17_0 == 31) {
                     LA17_2 = this.input.LA(2);
                     if (LA17_2 == 23) {
                        LA16_0 = this.input.LA(3);
                        if (LA16_0 == 6) {
                           alt17 = 1;
                        }
                     }
                  } else if (LA17_0 == 23) {
                     LA17_2 = this.input.LA(2);
                     if (LA17_2 == 6) {
                        alt17 = 1;
                     }
                  }

                  byte alt16;
                  switch (alt17) {
                     case 1:
                        alt16 = 2;
                        LA16_0 = this.input.LA(1);
                        if (LA16_0 == 31) {
                           alt16 = 1;
                        }

                        switch (alt16) {
                           case 1:
                              INDENT41 = (CommonToken)this.match(this.input, 31, FOLLOW_INDENT_in_ifstat522);
                              stream_INDENT.add(INDENT41);
                           default:
                              LDELIM42 = (CommonToken)this.match(this.input, 23, FOLLOW_LDELIM_in_ifstat525);
                              stream_LDELIM.add(LDELIM42);
                              string_literal43 = (CommonToken)this.match(this.input, 6, FOLLOW_ELSEIF_in_ifstat527);
                              stream_ELSEIF.add(string_literal43);
                              char_literal44 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_ifstat529);
                              stream_LPAREN.add(char_literal44);
                              this.pushFollow(FOLLOW_conditional_in_ifstat533);
                              c2 = this.conditional();
                              --this.state._fsp;
                              stream_conditional.add(c2.getTree());
                              if (list_c2 == null) {
                                 list_c2 = new ArrayList();
                              }

                              list_c2.add(c2.getTree());
                              char_literal45 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_ifstat535);
                              stream_RPAREN.add(char_literal45);
                              RDELIM46 = (CommonToken)this.match(this.input, 24, FOLLOW_RDELIM_in_ifstat537);
                              stream_RDELIM.add(RDELIM46);
                              this.pushFollow(FOLLOW_template_in_ifstat541);
                              t2 = this.template();
                              --this.state._fsp;
                              stream_template.add(t2.getTree());
                              if (list_t2 == null) {
                                 list_t2 = new ArrayList();
                              }

                              list_t2.add(t2.getTree());
                              continue;
                        }
                     default:
                        alt17 = 2;
                        LA17_0 = this.input.LA(1);
                        if (LA17_0 == 31) {
                           LA17_2 = this.input.LA(2);
                           if (LA17_2 == 23) {
                              LA16_0 = this.input.LA(3);
                              if (LA16_0 == 5) {
                                 alt17 = 1;
                              }
                           }
                        } else if (LA17_0 == 23) {
                           LA17_2 = this.input.LA(2);
                           if (LA17_2 == 5) {
                              alt17 = 1;
                           }
                        }

                        switch (alt17) {
                           case 1:
                              alt16 = 2;
                              LA16_0 = this.input.LA(1);
                              if (LA16_0 == 31) {
                                 alt16 = 1;
                              }

                              switch (alt16) {
                                 case 1:
                                    INDENT47 = (CommonToken)this.match(this.input, 31, FOLLOW_INDENT_in_ifstat551);
                                    stream_INDENT.add(INDENT47);
                                 default:
                                    LDELIM48 = (CommonToken)this.match(this.input, 23, FOLLOW_LDELIM_in_ifstat554);
                                    stream_LDELIM.add(LDELIM48);
                                    string_literal49 = (CommonToken)this.match(this.input, 5, FOLLOW_ELSE_in_ifstat556);
                                    stream_ELSE.add(string_literal49);
                                    RDELIM50 = (CommonToken)this.match(this.input, 24, FOLLOW_RDELIM_in_ifstat558);
                                    stream_RDELIM.add(RDELIM50);
                                    this.pushFollow(FOLLOW_template_in_ifstat562);
                                    t3 = this.template();
                                    --this.state._fsp;
                                    stream_template.add(t3.getTree());
                              }
                           default:
                              alt16 = 2;
                              LA16_0 = this.input.LA(1);
                              if (LA16_0 == 31) {
                                 alt16 = 1;
                              }

                              switch (alt16) {
                                 case 1:
                                    INDENT51 = (CommonToken)this.match(this.input, 31, FOLLOW_INDENT_in_ifstat570);
                                    stream_INDENT.add(INDENT51);
                                 default:
                                    endif = (CommonToken)this.match(this.input, 23, FOLLOW_LDELIM_in_ifstat576);
                                    stream_LDELIM.add(endif);
                                    string_literal52 = (CommonToken)this.match(this.input, 7, FOLLOW_ENDIF_in_ifstat578);
                                    stream_ENDIF.add(string_literal52);
                                    RDELIM53 = (CommonToken)this.match(this.input, 24, FOLLOW_RDELIM_in_ifstat582);
                                    stream_RDELIM.add(RDELIM53);
                                    int alt19 = 2;
                                    int LA19_0 = this.input.LA(1);
                                    if (LA19_0 == 32) {
                                       int LA19_1 = this.input.LA(2);
                                       if (((CommonToken)retval.start).getLine() != this.input.LT(1).getLine()) {
                                          alt19 = 1;
                                       }
                                    }

                                    switch (alt19) {
                                       case 1:
                                          if (((CommonToken)retval.start).getLine() == this.input.LT(1).getLine()) {
                                             throw new FailedPredicateException(this.input, "ifstat", "$ifstat.start.getLine()!=input.LT(1).getLine()");
                                          }

                                          NEWLINE54 = (CommonToken)this.match(this.input, 32, FOLLOW_NEWLINE_in_ifstat593);
                                          stream_NEWLINE.add(NEWLINE54);
                                       default:
                                          retval.tree = root_0;
                                          RewriteRuleTokenStream stream_i = new RewriteRuleTokenStream(this.adaptor, "token i", i);
                                          RewriteRuleSubtreeStream stream_t1 = new RewriteRuleSubtreeStream(this.adaptor, "rule t1", t1 != null ? t1.getTree() : null);
                                          RewriteRuleSubtreeStream stream_c1 = new RewriteRuleSubtreeStream(this.adaptor, "rule c1", c1 != null ? c1.getTree() : null);
                                          new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                                          RewriteRuleSubtreeStream stream_t3 = new RewriteRuleSubtreeStream(this.adaptor, "rule t3", t3 != null ? t3.getTree() : null);
                                          RewriteRuleSubtreeStream stream_t2 = new RewriteRuleSubtreeStream(this.adaptor, "token t2", list_t2);
                                          RewriteRuleSubtreeStream stream_c2 = new RewriteRuleSubtreeStream(this.adaptor, "token c2", list_c2);
                                          root_0 = (CommonTree)this.adaptor.nil();
                                          CommonTree root_1;
                                          CommonTree root_2;
                                          if (indent != null) {
                                             root_1 = (CommonTree)this.adaptor.nil();
                                             root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(47, (String)"INDENTED_EXPR")), root_1);
                                             this.adaptor.addChild(root_1, stream_i.nextNode());
                                             root_2 = (CommonTree)this.adaptor.nil();
                                             root_2 = (CommonTree)this.adaptor.becomeRoot((Object)stream_IF.nextNode(), root_2);
                                             this.adaptor.addChild(root_2, stream_c1.nextTree());
                                             if (stream_t1.hasNext()) {
                                                this.adaptor.addChild(root_2, stream_t1.nextTree());
                                             }

                                             stream_t1.reset();

                                             CommonTree root_3;
                                             while(stream_ELSEIF.hasNext() || stream_c2.hasNext() || stream_t2.hasNext()) {
                                                root_3 = (CommonTree)this.adaptor.nil();
                                                root_3 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ELSEIF.nextNode(), root_3);
                                                this.adaptor.addChild(root_3, stream_c2.nextTree());
                                                this.adaptor.addChild(root_3, stream_t2.nextTree());
                                                this.adaptor.addChild(root_2, root_3);
                                             }

                                             stream_ELSEIF.reset();
                                             stream_c2.reset();
                                             stream_t2.reset();
                                             if (stream_t3.hasNext() || stream_ELSE.hasNext()) {
                                                root_3 = (CommonTree)this.adaptor.nil();
                                                root_3 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ELSE.nextNode(), root_3);
                                                if (stream_t3.hasNext()) {
                                                   this.adaptor.addChild(root_3, stream_t3.nextTree());
                                                }

                                                stream_t3.reset();
                                                this.adaptor.addChild(root_2, root_3);
                                             }

                                             stream_t3.reset();
                                             stream_ELSE.reset();
                                             this.adaptor.addChild(root_1, root_2);
                                             this.adaptor.addChild(root_0, root_1);
                                          } else {
                                             root_1 = (CommonTree)this.adaptor.nil();
                                             root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_IF.nextNode(), root_1);
                                             this.adaptor.addChild(root_1, stream_c1.nextTree());
                                             if (stream_t1.hasNext()) {
                                                this.adaptor.addChild(root_1, stream_t1.nextTree());
                                             }

                                             stream_t1.reset();

                                             while(stream_t2.hasNext() || stream_ELSEIF.hasNext() || stream_c2.hasNext()) {
                                                root_2 = (CommonTree)this.adaptor.nil();
                                                root_2 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ELSEIF.nextNode(), root_2);
                                                this.adaptor.addChild(root_2, stream_c2.nextTree());
                                                this.adaptor.addChild(root_2, stream_t2.nextTree());
                                                this.adaptor.addChild(root_1, root_2);
                                             }

                                             stream_t2.reset();
                                             stream_ELSEIF.reset();
                                             stream_c2.reset();
                                             if (stream_ELSE.hasNext() || stream_t3.hasNext()) {
                                                root_2 = (CommonTree)this.adaptor.nil();
                                                root_2 = (CommonTree)this.adaptor.becomeRoot((Object)stream_ELSE.nextNode(), root_2);
                                                if (stream_t3.hasNext()) {
                                                   this.adaptor.addChild(root_2, stream_t3.nextTree());
                                                }

                                                stream_t3.reset();
                                                this.adaptor.addChild(root_1, root_2);
                                             }

                                             stream_ELSE.reset();
                                             stream_t3.reset();
                                             this.adaptor.addChild(root_0, root_1);
                                          }

                                          retval.tree = root_0;
                                          retval.stop = this.input.LT(-1);
                                          retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                                          this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                                          return retval;
                                    }
                              }
                        }
                  }
               }
         }
      } catch (RecognitionException var86) {
         throw var86;
      } finally {
         ;
      }
   }

   public final conditional_return conditional() throws RecognitionException {
      this.conditional_stack.push(new conditional_scope());
      conditional_return retval = new conditional_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken string_literal56 = null;
      ParserRuleReturnScope andConditional55 = null;
      ParserRuleReturnScope andConditional57 = null;
      CommonTree string_literal56_tree = null;

      try {
         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_andConditional_in_conditional713);
         andConditional55 = this.andConditional();
         --this.state._fsp;
         this.adaptor.addChild(root_0, andConditional55.getTree());

         while(true) {
            int alt20 = 2;
            int LA20_0 = this.input.LA(1);
            if (LA20_0 == 29) {
               alt20 = 1;
            }

            switch (alt20) {
               case 1:
                  string_literal56 = (CommonToken)this.match(this.input, 29, FOLLOW_OR_in_conditional717);
                  string_literal56_tree = (CommonTree)this.adaptor.create(string_literal56);
                  root_0 = (CommonTree)this.adaptor.becomeRoot((Object)string_literal56_tree, root_0);
                  this.pushFollow(FOLLOW_andConditional_in_conditional720);
                  andConditional57 = this.andConditional();
                  --this.state._fsp;
                  this.adaptor.addChild(root_0, andConditional57.getTree());
                  break;
               default:
                  retval.stop = this.input.LT(-1);
                  retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                  this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  return retval;
            }
         }
      } catch (RecognitionException var12) {
         throw var12;
      } finally {
         this.conditional_stack.pop();
      }
   }

   public final andConditional_return andConditional() throws RecognitionException {
      andConditional_return retval = new andConditional_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken string_literal59 = null;
      ParserRuleReturnScope notConditional58 = null;
      ParserRuleReturnScope notConditional60 = null;
      CommonTree string_literal59_tree = null;

      try {
         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_notConditional_in_andConditional733);
         notConditional58 = this.notConditional();
         --this.state._fsp;
         this.adaptor.addChild(root_0, notConditional58.getTree());

         while(true) {
            int alt21 = 2;
            int LA21_0 = this.input.LA(1);
            if (LA21_0 == 30) {
               alt21 = 1;
            }

            switch (alt21) {
               case 1:
                  string_literal59 = (CommonToken)this.match(this.input, 30, FOLLOW_AND_in_andConditional737);
                  string_literal59_tree = (CommonTree)this.adaptor.create(string_literal59);
                  root_0 = (CommonTree)this.adaptor.becomeRoot((Object)string_literal59_tree, root_0);
                  this.pushFollow(FOLLOW_notConditional_in_andConditional740);
                  notConditional60 = this.notConditional();
                  --this.state._fsp;
                  this.adaptor.addChild(root_0, notConditional60.getTree());
                  break;
               default:
                  retval.stop = this.input.LT(-1);
                  retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                  this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  return retval;
            }
         }
      } catch (RecognitionException var12) {
         throw var12;
      } finally {
         ;
      }
   }

   public final notConditional_return notConditional() throws RecognitionException {
      notConditional_return retval = new notConditional_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken char_literal61 = null;
      ParserRuleReturnScope notConditional62 = null;
      ParserRuleReturnScope memberExpr63 = null;
      CommonTree char_literal61_tree = null;

      try {
         int alt22 = 2;
         int LA22_0 = this.input.LA(1);
         if (LA22_0 == 10) {
            alt22 = 1;
         } else if (LA22_0 == 8 || LA22_0 == 16 || LA22_0 == 20 || LA22_0 >= 25 && LA22_0 <= 26 || LA22_0 == 33 || LA22_0 >= 35 && LA22_0 <= 36) {
            alt22 = 2;
         } else if (LA22_0 == 14 && (this.conditional_stack.size() > 0 || this.conditional_stack.size() == 0)) {
            alt22 = 2;
         }

         switch (alt22) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               char_literal61 = (CommonToken)this.match(this.input, 10, FOLLOW_BANG_in_notConditional753);
               char_literal61_tree = (CommonTree)this.adaptor.create(char_literal61);
               root_0 = (CommonTree)this.adaptor.becomeRoot((Object)char_literal61_tree, root_0);
               this.pushFollow(FOLLOW_notConditional_in_notConditional756);
               notConditional62 = this.notConditional();
               --this.state._fsp;
               this.adaptor.addChild(root_0, notConditional62.getTree());
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_memberExpr_in_notConditional761);
               memberExpr63 = this.memberExpr();
               --this.state._fsp;
               this.adaptor.addChild(root_0, memberExpr63.getTree());
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var12) {
         throw var12;
      } finally {
         ;
      }
   }

   public final notConditionalExpr_return notConditionalExpr() throws RecognitionException {
      notConditionalExpr_return retval = new notConditionalExpr_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken p = null;
      CommonToken prop = null;
      CommonToken ID64 = null;
      CommonToken char_literal65 = null;
      CommonToken char_literal67 = null;
      ParserRuleReturnScope mapExpr66 = null;
      CommonTree p_tree = null;
      CommonTree prop_tree = null;
      CommonTree ID64_tree = null;
      CommonTree char_literal65_tree = null;
      CommonTree char_literal67_tree = null;
      RewriteRuleTokenStream stream_DOT = new RewriteRuleTokenStream(this.adaptor, "token DOT");
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
      RewriteRuleSubtreeStream stream_mapExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule mapExpr");

      try {
         ID64 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_notConditionalExpr773);
         stream_ID.add(ID64);
         retval.tree = root_0;
         new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
         root_0 = (CommonTree)this.adaptor.nil();
         this.adaptor.addChild(root_0, stream_ID.nextNode());
         retval.tree = root_0;

         while(true) {
            int alt23 = 3;
            int LA23_0 = this.input.LA(1);
            if (LA23_0 == 19) {
               int LA23_2 = this.input.LA(2);
               if (LA23_2 == 25) {
                  alt23 = 1;
               } else if (LA23_2 == 14) {
                  alt23 = 2;
               }
            }

            switch (alt23) {
               case 1:
                  p = (CommonToken)this.match(this.input, 19, FOLLOW_DOT_in_notConditionalExpr784);
                  stream_DOT.add(p);
                  prop = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_notConditionalExpr788);
                  stream_ID.add(prop);
                  retval.tree = root_0;
                  RewriteRuleTokenStream stream_prop = new RewriteRuleTokenStream(this.adaptor, "token prop", prop);
                  RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  CommonTree root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(52, p, "PROP")), root_1);
                  this.adaptor.addChild(root_1, stream_retval.nextTree());
                  this.adaptor.addChild(root_1, stream_prop.nextNode());
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
                  break;
               case 2:
                  p = (CommonToken)this.match(this.input, 19, FOLLOW_DOT_in_notConditionalExpr814);
                  stream_DOT.add(p);
                  char_literal65 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_notConditionalExpr816);
                  stream_LPAREN.add(char_literal65);
                  this.pushFollow(FOLLOW_mapExpr_in_notConditionalExpr818);
                  mapExpr66 = this.mapExpr();
                  --this.state._fsp;
                  stream_mapExpr.add(mapExpr66.getTree());
                  char_literal67 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_notConditionalExpr820);
                  stream_RPAREN.add(char_literal67);
                  retval.tree = root_0;
                  RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  CommonTree root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(53, p, "PROP_IND")), root_1);
                  this.adaptor.addChild(root_1, stream_retval.nextTree());
                  this.adaptor.addChild(root_1, stream_mapExpr.nextTree());
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
                  break;
               default:
                  retval.stop = this.input.LT(-1);
                  retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                  this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  return retval;
            }
         }
      } catch (RecognitionException var27) {
         throw var27;
      } finally {
         ;
      }
   }

   public final exprOptions_return exprOptions() throws RecognitionException {
      exprOptions_return retval = new exprOptions_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken char_literal69 = null;
      ParserRuleReturnScope option68 = null;
      ParserRuleReturnScope option70 = null;
      CommonTree char_literal69_tree = null;
      RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
      RewriteRuleSubtreeStream stream_option = new RewriteRuleSubtreeStream(this.adaptor, "rule option");

      try {
         this.pushFollow(FOLLOW_option_in_exprOptions850);
         option68 = this.option();
         --this.state._fsp;
         stream_option.add(option68.getTree());

         while(true) {
            int alt24 = 2;
            int LA24_0 = this.input.LA(1);
            if (LA24_0 == 18) {
               alt24 = 1;
            }

            switch (alt24) {
               case 1:
                  char_literal69 = (CommonToken)this.match(this.input, 18, FOLLOW_COMMA_in_exprOptions854);
                  stream_COMMA.add(char_literal69);
                  this.pushFollow(FOLLOW_option_in_exprOptions856);
                  option70 = this.option();
                  --this.state._fsp;
                  stream_option.add(option70.getTree());
                  break;
               default:
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  CommonTree root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(51, (String)"OPTIONS")), root_1);

                  while(stream_option.hasNext()) {
                     this.adaptor.addChild(root_1, stream_option.nextTree());
                  }

                  stream_option.reset();
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
                  retval.stop = this.input.LT(-1);
                  retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                  this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  return retval;
            }
         }
      } catch (RecognitionException var14) {
         throw var14;
      } finally {
         ;
      }
   }

   public final option_return option() throws RecognitionException {
      option_return retval = new option_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken ID71 = null;
      CommonToken char_literal72 = null;
      ParserRuleReturnScope exprNoComma73 = null;
      CommonTree ID71_tree = null;
      CommonTree char_literal72_tree = null;
      RewriteRuleTokenStream stream_EQUALS = new RewriteRuleTokenStream(this.adaptor, "token EQUALS");
      RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
      RewriteRuleSubtreeStream stream_exprNoComma = new RewriteRuleSubtreeStream(this.adaptor, "rule exprNoComma");
      String id = this.input.LT(1).getText();
      String defVal = (String)Compiler.defaultOptionValues.get(id);
      boolean validOption = Compiler.supportedOptions.get(id) != null;

      try {
         ID71 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_option883);
         stream_ID.add(ID71);
         if (!validOption) {
            this.errMgr.compileTimeError(ErrorType.NO_SUCH_OPTION, this.templateToken, ID71, ID71 != null ? ID71.getText() : null);
         }

         int alt25 = true;
         int LA25_0 = this.input.LA(1);
         byte alt25;
         if (LA25_0 == 12) {
            alt25 = 1;
         } else {
            if (LA25_0 != 18 && LA25_0 != 24) {
               NoViableAltException nvae = new NoViableAltException("", 25, 0, this.input);
               throw nvae;
            }

            alt25 = 2;
         }

         CommonTree root_1;
         switch (alt25) {
            case 1:
               char_literal72 = (CommonToken)this.match(this.input, 12, FOLLOW_EQUALS_in_option893);
               stream_EQUALS.add(char_literal72);
               this.pushFollow(FOLLOW_exprNoComma_in_option895);
               exprNoComma73 = this.exprNoComma();
               --this.state._fsp;
               stream_exprNoComma.add(exprNoComma73.getTree());
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               if (validOption) {
                  root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_EQUALS.nextNode(), root_1);
                  this.adaptor.addChild(root_1, stream_ID.nextNode());
                  this.adaptor.addChild(root_1, stream_exprNoComma.nextTree());
                  this.adaptor.addChild(root_0, root_1);
               } else {
                  root_0 = null;
               }

               retval.tree = root_0;
               break;
            case 2:
               if (defVal == null) {
                  this.errMgr.compileTimeError(ErrorType.NO_DEFAULT_VALUE, this.templateToken, ID71);
               }

               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               if (validOption && defVal != null) {
                  root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(12, (String)"=")), root_1);
                  this.adaptor.addChild(root_1, stream_ID.nextNode());
                  this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(26, ID71, '"' + defVal + '"'));
                  this.adaptor.addChild(root_0, root_1);
               } else {
                  root_0 = null;
               }

               retval.tree = root_0;
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var21) {
         throw var21;
      } finally {
         ;
      }
   }

   public final exprNoComma_return exprNoComma() throws RecognitionException {
      exprNoComma_return retval = new exprNoComma_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken char_literal75 = null;
      ParserRuleReturnScope memberExpr74 = null;
      ParserRuleReturnScope mapTemplateRef76 = null;
      CommonTree char_literal75_tree = null;
      RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
      RewriteRuleSubtreeStream stream_memberExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule memberExpr");
      RewriteRuleSubtreeStream stream_mapTemplateRef = new RewriteRuleSubtreeStream(this.adaptor, "rule mapTemplateRef");

      try {
         this.pushFollow(FOLLOW_memberExpr_in_exprNoComma1002);
         memberExpr74 = this.memberExpr();
         --this.state._fsp;
         stream_memberExpr.add(memberExpr74.getTree());
         int alt26 = true;
         int LA26_0 = this.input.LA(1);
         byte alt26;
         if (LA26_0 == 13) {
            alt26 = 1;
         } else {
            if (LA26_0 != 15 && (LA26_0 < 17 || LA26_0 > 18) && LA26_0 != 24) {
               NoViableAltException nvae = new NoViableAltException("", 26, 0, this.input);
               throw nvae;
            }

            alt26 = 2;
         }

         switch (alt26) {
            case 1:
               char_literal75 = (CommonToken)this.match(this.input, 13, FOLLOW_COLON_in_exprNoComma1008);
               stream_COLON.add(char_literal75);
               this.pushFollow(FOLLOW_mapTemplateRef_in_exprNoComma1010);
               mapTemplateRef76 = this.mapTemplateRef();
               --this.state._fsp;
               stream_mapTemplateRef.add(mapTemplateRef76.getTree());
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(49, (String)"MAP")), root_1);
               this.adaptor.addChild(root_1, stream_memberExpr.nextTree());
               this.adaptor.addChild(root_1, stream_mapTemplateRef.nextTree());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
               break;
            case 2:
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               this.adaptor.addChild(root_0, stream_memberExpr.nextTree());
               retval.tree = root_0;
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var17) {
         throw var17;
      } finally {
         ;
      }
   }

   public final expr_return expr() throws RecognitionException {
      expr_return retval = new expr_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope mapExpr77 = null;

      try {
         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_mapExpr_in_expr1055);
         mapExpr77 = this.mapExpr();
         --this.state._fsp;
         this.adaptor.addChild(root_0, mapExpr77.getTree());
         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var8) {
         throw var8;
      } finally {
         ;
      }
   }

   public final mapExpr_return mapExpr() throws RecognitionException {
      mapExpr_return retval = new mapExpr_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken c = null;
      CommonToken col = null;
      CommonToken char_literal81 = null;
      List list_x = null;
      ParserRuleReturnScope memberExpr78 = null;
      ParserRuleReturnScope memberExpr79 = null;
      ParserRuleReturnScope mapTemplateRef80 = null;
      RuleReturnScope x = null;
      CommonTree c_tree = null;
      CommonTree col_tree = null;
      CommonTree char_literal81_tree = null;
      RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
      RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
      RewriteRuleSubtreeStream stream_memberExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule memberExpr");
      RewriteRuleSubtreeStream stream_mapTemplateRef = new RewriteRuleSubtreeStream(this.adaptor, "rule mapTemplateRef");

      try {
         this.pushFollow(FOLLOW_memberExpr_in_mapExpr1067);
         memberExpr78 = this.memberExpr();
         --this.state._fsp;
         stream_memberExpr.add(memberExpr78.getTree());
         int alt28 = true;
         int LA28_0 = this.input.LA(1);
         byte alt28;
         if (LA28_0 == 18) {
            alt28 = 1;
         } else {
            if (LA28_0 != 9 && LA28_0 != 13 && LA28_0 != 15 && LA28_0 != 24) {
               NoViableAltException nvae = new NoViableAltException("", 28, 0, this.input);
               throw nvae;
            }

            alt28 = 2;
         }

         int alt27;
         label270:
         switch (alt28) {
            case 1:
               int cnt27 = 0;

               while(true) {
                  alt27 = 2;
                  int LA27_0 = this.input.LA(1);
                  if (LA27_0 == 18) {
                     alt27 = 1;
                  }

                  switch (alt27) {
                     case 1:
                        c = (CommonToken)this.match(this.input, 18, FOLLOW_COMMA_in_mapExpr1076);
                        stream_COMMA.add(c);
                        this.pushFollow(FOLLOW_memberExpr_in_mapExpr1078);
                        memberExpr79 = this.memberExpr();
                        --this.state._fsp;
                        stream_memberExpr.add(memberExpr79.getTree());
                        ++cnt27;
                        break;
                     default:
                        if (cnt27 < 1) {
                           EarlyExitException eee = new EarlyExitException(27, this.input);
                           throw eee;
                        }

                        col = (CommonToken)this.match(this.input, 13, FOLLOW_COLON_in_mapExpr1084);
                        stream_COLON.add(col);
                        this.pushFollow(FOLLOW_mapTemplateRef_in_mapExpr1086);
                        mapTemplateRef80 = this.mapTemplateRef();
                        --this.state._fsp;
                        stream_mapTemplateRef.add(mapTemplateRef80.getTree());
                        retval.tree = root_0;
                        new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                        root_0 = (CommonTree)this.adaptor.nil();
                        CommonTree root_1 = (CommonTree)this.adaptor.nil();
                        root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(57, (Token)col)), root_1);
                        CommonTree root_2 = (CommonTree)this.adaptor.nil();
                        root_2 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(39, (String)"ELEMENTS")), root_2);
                        if (!stream_memberExpr.hasNext()) {
                           throw new RewriteEarlyExitException();
                        }

                        while(stream_memberExpr.hasNext()) {
                           this.adaptor.addChild(root_2, stream_memberExpr.nextTree());
                        }

                        stream_memberExpr.reset();
                        this.adaptor.addChild(root_1, root_2);
                        this.adaptor.addChild(root_1, stream_mapTemplateRef.nextTree());
                        this.adaptor.addChild(root_0, root_1);
                        retval.tree = root_0;
                        break label270;
                  }
               }
            case 2:
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               this.adaptor.addChild(root_0, stream_memberExpr.nextTree());
               retval.tree = root_0;
         }

         label255:
         while(true) {
            int alt30 = 2;
            alt27 = this.input.LA(1);
            if (alt27 == 13) {
               alt30 = 1;
            }

            switch (alt30) {
               case 1:
                  if (list_x != null) {
                     list_x.clear();
                  }

                  col = (CommonToken)this.match(this.input, 13, FOLLOW_COLON_in_mapExpr1149);
                  stream_COLON.add(col);
                  this.pushFollow(FOLLOW_mapTemplateRef_in_mapExpr1153);
                  x = this.mapTemplateRef();
                  --this.state._fsp;
                  stream_mapTemplateRef.add(x.getTree());
                  if (list_x == null) {
                     list_x = new ArrayList();
                  }

                  list_x.add(x.getTree());

                  while(true) {
                     int alt29 = 2;
                     int LA29_0 = this.input.LA(1);
                     if (LA29_0 == 18 && c == null) {
                        alt29 = 1;
                     }

                     switch (alt29) {
                        case 1:
                           if (c != null) {
                              throw new FailedPredicateException(this.input, "mapExpr", "$c==null");
                           }

                           char_literal81 = (CommonToken)this.match(this.input, 18, FOLLOW_COMMA_in_mapExpr1159);
                           stream_COMMA.add(char_literal81);
                           this.pushFollow(FOLLOW_mapTemplateRef_in_mapExpr1163);
                           x = this.mapTemplateRef();
                           --this.state._fsp;
                           stream_mapTemplateRef.add(x.getTree());
                           if (list_x == null) {
                              list_x = new ArrayList();
                           }

                           list_x.add(x.getTree());
                           break;
                        default:
                           retval.tree = root_0;
                           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           RewriteRuleSubtreeStream stream_x = new RewriteRuleSubtreeStream(this.adaptor, "token x", list_x);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(49, (Token)col)), root_1);
                           this.adaptor.addChild(root_1, stream_retval.nextTree());
                           if (!stream_x.hasNext()) {
                              throw new RewriteEarlyExitException();
                           }

                           while(stream_x.hasNext()) {
                              this.adaptor.addChild(root_1, stream_x.nextTree());
                           }

                           stream_x.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                           continue label255;
                     }
                  }
               default:
                  retval.stop = this.input.LT(-1);
                  retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                  this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  return retval;
            }
         }
      } catch (RecognitionException var28) {
         throw var28;
      } finally {
         ;
      }
   }

   public final mapTemplateRef_return mapTemplateRef() throws RecognitionException {
      mapTemplateRef_return retval = new mapTemplateRef_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken lp = null;
      CommonToken rp = null;
      CommonToken ID82 = null;
      CommonToken char_literal83 = null;
      CommonToken char_literal85 = null;
      CommonToken char_literal88 = null;
      CommonToken char_literal90 = null;
      ParserRuleReturnScope args84 = null;
      ParserRuleReturnScope subtemplate86 = null;
      ParserRuleReturnScope mapExpr87 = null;
      ParserRuleReturnScope argExprList89 = null;
      CommonTree lp_tree = null;
      CommonTree rp_tree = null;
      CommonTree ID82_tree = null;
      CommonTree char_literal83_tree = null;
      CommonTree char_literal85_tree = null;
      CommonTree char_literal88_tree = null;
      CommonTree char_literal90_tree = null;
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
      RewriteRuleSubtreeStream stream_args = new RewriteRuleSubtreeStream(this.adaptor, "rule args");
      RewriteRuleSubtreeStream stream_argExprList = new RewriteRuleSubtreeStream(this.adaptor, "rule argExprList");
      RewriteRuleSubtreeStream stream_mapExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule mapExpr");

      try {
         int alt32 = true;
         byte alt32;
         switch (this.input.LA(1)) {
            case 14:
               alt32 = 3;
               break;
            case 20:
               alt32 = 2;
               break;
            case 25:
               alt32 = 1;
               break;
            default:
               NoViableAltException nvae = new NoViableAltException("", 32, 0, this.input);
               throw nvae;
         }

         switch (alt32) {
            case 1:
               ID82 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_mapTemplateRef1210);
               stream_ID.add(ID82);
               char_literal83 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_mapTemplateRef1212);
               stream_LPAREN.add(char_literal83);
               this.pushFollow(FOLLOW_args_in_mapTemplateRef1214);
               args84 = this.args();
               --this.state._fsp;
               stream_args.add(args84.getTree());
               char_literal85 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_mapTemplateRef1216);
               stream_RPAREN.add(char_literal85);
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               CommonTree root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(42, (String)"INCLUDE")), root_1);
               this.adaptor.addChild(root_1, stream_ID.nextNode());
               if (stream_args.hasNext()) {
                  this.adaptor.addChild(root_1, stream_args.nextTree());
               }

               stream_args.reset();
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_subtemplate_in_mapTemplateRef1238);
               subtemplate86 = this.subtemplate();
               --this.state._fsp;
               this.adaptor.addChild(root_0, subtemplate86.getTree());
               break;
            case 3:
               lp = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_mapTemplateRef1245);
               stream_LPAREN.add(lp);
               this.pushFollow(FOLLOW_mapExpr_in_mapTemplateRef1247);
               mapExpr87 = this.mapExpr();
               --this.state._fsp;
               stream_mapExpr.add(mapExpr87.getTree());
               rp = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_mapTemplateRef1251);
               stream_RPAREN.add(rp);
               char_literal88 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_mapTemplateRef1253);
               stream_LPAREN.add(char_literal88);
               int alt31 = 2;
               int LA31_0 = this.input.LA(1);
               if (LA31_0 != 8 && LA31_0 != 16 && LA31_0 != 20 && (LA31_0 < 25 || LA31_0 > 26) && LA31_0 != 33 && (LA31_0 < 35 || LA31_0 > 36)) {
                  if (LA31_0 == 14 && (this.conditional_stack.size() > 0 || this.conditional_stack.size() == 0)) {
                     alt31 = 1;
                  }
               } else {
                  alt31 = 1;
               }

               switch (alt31) {
                  case 1:
                     this.pushFollow(FOLLOW_argExprList_in_mapTemplateRef1255);
                     argExprList89 = this.argExprList();
                     --this.state._fsp;
                     stream_argExprList.add(argExprList89.getTree());
                  default:
                     char_literal90 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_mapTemplateRef1258);
                     stream_RPAREN.add(char_literal90);
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(43, (String)"INCLUDE_IND")), root_1);
                     this.adaptor.addChild(root_1, stream_mapExpr.nextTree());
                     if (stream_argExprList.hasNext()) {
                        this.adaptor.addChild(root_1, stream_argExprList.nextTree());
                     }

                     stream_argExprList.reset();
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
               }
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var35) {
         throw var35;
      } finally {
         ;
      }
   }

   public final memberExpr_return memberExpr() throws RecognitionException {
      memberExpr_return retval = new memberExpr_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken p = null;
      CommonToken ID92 = null;
      CommonToken char_literal93 = null;
      CommonToken char_literal95 = null;
      ParserRuleReturnScope includeExpr91 = null;
      ParserRuleReturnScope mapExpr94 = null;
      CommonTree p_tree = null;
      CommonTree ID92_tree = null;
      CommonTree char_literal93_tree = null;
      CommonTree char_literal95_tree = null;
      RewriteRuleTokenStream stream_DOT = new RewriteRuleTokenStream(this.adaptor, "token DOT");
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
      RewriteRuleSubtreeStream stream_includeExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule includeExpr");
      RewriteRuleSubtreeStream stream_mapExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule mapExpr");

      try {
         this.pushFollow(FOLLOW_includeExpr_in_memberExpr1281);
         includeExpr91 = this.includeExpr();
         --this.state._fsp;
         stream_includeExpr.add(includeExpr91.getTree());
         retval.tree = root_0;
         new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
         root_0 = (CommonTree)this.adaptor.nil();
         this.adaptor.addChild(root_0, stream_includeExpr.nextTree());
         retval.tree = root_0;

         while(true) {
            int alt33 = 3;
            int LA33_0 = this.input.LA(1);
            if (LA33_0 == 19) {
               int LA33_2 = this.input.LA(2);
               if (LA33_2 == 25) {
                  alt33 = 1;
               } else if (LA33_2 == 14) {
                  alt33 = 2;
               }
            }

            CommonTree root_1;
            RewriteRuleSubtreeStream stream_retval;
            switch (alt33) {
               case 1:
                  p = (CommonToken)this.match(this.input, 19, FOLLOW_DOT_in_memberExpr1292);
                  stream_DOT.add(p);
                  ID92 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_memberExpr1294);
                  stream_ID.add(ID92);
                  retval.tree = root_0;
                  stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(52, p, "PROP")), root_1);
                  this.adaptor.addChild(root_1, stream_retval.nextTree());
                  this.adaptor.addChild(root_1, stream_ID.nextNode());
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
                  break;
               case 2:
                  p = (CommonToken)this.match(this.input, 19, FOLLOW_DOT_in_memberExpr1320);
                  stream_DOT.add(p);
                  char_literal93 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_memberExpr1322);
                  stream_LPAREN.add(char_literal93);
                  this.pushFollow(FOLLOW_mapExpr_in_memberExpr1324);
                  mapExpr94 = this.mapExpr();
                  --this.state._fsp;
                  stream_mapExpr.add(mapExpr94.getTree());
                  char_literal95 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_memberExpr1326);
                  stream_RPAREN.add(char_literal95);
                  retval.tree = root_0;
                  stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.nil();
                  root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(53, p, "PROP_IND")), root_1);
                  this.adaptor.addChild(root_1, stream_retval.nextTree());
                  this.adaptor.addChild(root_1, stream_mapExpr.nextTree());
                  this.adaptor.addChild(root_0, root_1);
                  retval.tree = root_0;
                  break;
               default:
                  retval.stop = this.input.LT(-1);
                  retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                  this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  return retval;
            }
         }
      } catch (RecognitionException var26) {
         throw var26;
      } finally {
         ;
      }
   }

   public final includeExpr_return includeExpr() throws RecognitionException {
      includeExpr_return retval = new includeExpr_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken rp = null;
      CommonToken ID96 = null;
      CommonToken char_literal97 = null;
      CommonToken char_literal99 = null;
      CommonToken string_literal100 = null;
      CommonToken char_literal101 = null;
      CommonToken ID102 = null;
      CommonToken char_literal103 = null;
      CommonToken char_literal105 = null;
      CommonToken ID106 = null;
      CommonToken char_literal107 = null;
      CommonToken char_literal109 = null;
      CommonToken char_literal110 = null;
      CommonToken string_literal111 = null;
      CommonToken char_literal112 = null;
      CommonToken ID113 = null;
      CommonToken char_literal114 = null;
      CommonToken char_literal115 = null;
      CommonToken ID116 = null;
      CommonToken char_literal117 = null;
      ParserRuleReturnScope expr98 = null;
      ParserRuleReturnScope args104 = null;
      ParserRuleReturnScope args108 = null;
      ParserRuleReturnScope primary118 = null;
      CommonTree rp_tree = null;
      CommonTree ID96_tree = null;
      CommonTree char_literal97_tree = null;
      CommonTree char_literal99_tree = null;
      CommonTree string_literal100_tree = null;
      CommonTree char_literal101_tree = null;
      CommonTree ID102_tree = null;
      CommonTree char_literal103_tree = null;
      CommonTree char_literal105_tree = null;
      CommonTree ID106_tree = null;
      CommonTree char_literal107_tree = null;
      CommonTree char_literal109_tree = null;
      CommonTree char_literal110_tree = null;
      CommonTree string_literal111_tree = null;
      CommonTree char_literal112_tree = null;
      CommonTree ID113_tree = null;
      CommonTree char_literal114_tree = null;
      CommonTree char_literal115_tree = null;
      CommonTree ID116_tree = null;
      CommonTree char_literal117_tree = null;
      RewriteRuleTokenStream stream_SUPER = new RewriteRuleTokenStream(this.adaptor, "token SUPER");
      RewriteRuleTokenStream stream_AT = new RewriteRuleTokenStream(this.adaptor, "token AT");
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleTokenStream stream_DOT = new RewriteRuleTokenStream(this.adaptor, "token DOT");
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
      RewriteRuleSubtreeStream stream_args = new RewriteRuleSubtreeStream(this.adaptor, "rule args");
      RewriteRuleSubtreeStream stream_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule expr");

      try {
         int alt35 = 6;
         int LA35_0 = this.input.LA(1);
         int LA35_3;
         int LA34_0;
         NoViableAltException nvae;
         if (LA35_0 == 25) {
            LA35_3 = this.input.LA(2);
            if (LA35_3 == 14) {
               LA34_0 = this.input.LA(3);
               if (Compiler.funcs.containsKey(this.input.LT(1).getText())) {
                  alt35 = 1;
               } else {
                  alt35 = 3;
               }
            } else {
               if (LA35_3 != 9 && LA35_3 != 13 && LA35_3 != 15 && (LA35_3 < 17 || LA35_3 > 19) && LA35_3 != 24 && (LA35_3 < 29 || LA35_3 > 30)) {
                  LA34_0 = this.input.mark();

                  try {
                     this.input.consume();
                     nvae = new NoViableAltException("", 35, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(LA34_0);
                  }
               }

               alt35 = 6;
            }
         } else if (LA35_0 == 8) {
            alt35 = 2;
         } else if (LA35_0 == 33) {
            LA35_3 = this.input.LA(2);
            if (LA35_3 == 8) {
               alt35 = 4;
            } else {
               if (LA35_3 != 25) {
                  LA34_0 = this.input.mark();

                  try {
                     this.input.consume();
                     nvae = new NoViableAltException("", 35, 3, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(LA34_0);
                  }
               }

               alt35 = 5;
            }
         } else if (LA35_0 == 16 || LA35_0 == 20 || LA35_0 == 26 || LA35_0 >= 35 && LA35_0 <= 36) {
            alt35 = 6;
         } else if (LA35_0 == 14 && (this.conditional_stack.size() > 0 || this.conditional_stack.size() == 0)) {
            alt35 = 6;
         }

         CommonTree root_1;
         label733:
         switch (alt35) {
            case 1:
               if (!Compiler.funcs.containsKey(this.input.LT(1).getText())) {
                  throw new FailedPredicateException(this.input, "includeExpr", "Compiler.funcs.containsKey(input.LT(1).getText())");
               }

               ID96 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_includeExpr1370);
               stream_ID.add(ID96);
               char_literal97 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_includeExpr1372);
               stream_LPAREN.add(char_literal97);
               int alt34 = 2;
               LA34_0 = this.input.LA(1);
               if (LA34_0 != 8 && LA34_0 != 16 && LA34_0 != 20 && (LA34_0 < 25 || LA34_0 > 26) && LA34_0 != 33 && (LA34_0 < 35 || LA34_0 > 36)) {
                  if (LA34_0 == 14 && (this.conditional_stack.size() > 0 || this.conditional_stack.size() == 0)) {
                     alt34 = 1;
                  }
               } else {
                  alt34 = 1;
               }

               switch (alt34) {
                  case 1:
                     this.pushFollow(FOLLOW_expr_in_includeExpr1374);
                     expr98 = this.expr();
                     --this.state._fsp;
                     stream_expr.add(expr98.getTree());
                  default:
                     char_literal99 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_includeExpr1377);
                     stream_RPAREN.add(char_literal99);
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(40, (String)"EXEC_FUNC")), root_1);
                     this.adaptor.addChild(root_1, stream_ID.nextNode());
                     if (stream_expr.hasNext()) {
                        this.adaptor.addChild(root_1, stream_expr.nextTree());
                     }

                     stream_expr.reset();
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
                     break label733;
               }
            case 2:
               string_literal100 = (CommonToken)this.match(this.input, 8, FOLLOW_SUPER_in_includeExpr1398);
               stream_SUPER.add(string_literal100);
               char_literal101 = (CommonToken)this.match(this.input, 19, FOLLOW_DOT_in_includeExpr1400);
               stream_DOT.add(char_literal101);
               ID102 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_includeExpr1402);
               stream_ID.add(ID102);
               char_literal103 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_includeExpr1404);
               stream_LPAREN.add(char_literal103);
               this.pushFollow(FOLLOW_args_in_includeExpr1406);
               args104 = this.args();
               --this.state._fsp;
               stream_args.add(args104.getTree());
               char_literal105 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_includeExpr1408);
               stream_RPAREN.add(char_literal105);
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(45, (String)"INCLUDE_SUPER")), root_1);
               this.adaptor.addChild(root_1, stream_ID.nextNode());
               if (stream_args.hasNext()) {
                  this.adaptor.addChild(root_1, stream_args.nextTree());
               }

               stream_args.reset();
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
               break;
            case 3:
               ID106 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_includeExpr1427);
               stream_ID.add(ID106);
               char_literal107 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_includeExpr1429);
               stream_LPAREN.add(char_literal107);
               this.pushFollow(FOLLOW_args_in_includeExpr1431);
               args108 = this.args();
               --this.state._fsp;
               stream_args.add(args108.getTree());
               char_literal109 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_includeExpr1433);
               stream_RPAREN.add(char_literal109);
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(42, (String)"INCLUDE")), root_1);
               this.adaptor.addChild(root_1, stream_ID.nextNode());
               if (stream_args.hasNext()) {
                  this.adaptor.addChild(root_1, stream_args.nextTree());
               }

               stream_args.reset();
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
               break;
            case 4:
               char_literal110 = (CommonToken)this.match(this.input, 33, FOLLOW_AT_in_includeExpr1455);
               stream_AT.add(char_literal110);
               string_literal111 = (CommonToken)this.match(this.input, 8, FOLLOW_SUPER_in_includeExpr1457);
               stream_SUPER.add(string_literal111);
               char_literal112 = (CommonToken)this.match(this.input, 19, FOLLOW_DOT_in_includeExpr1459);
               stream_DOT.add(char_literal112);
               ID113 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_includeExpr1461);
               stream_ID.add(ID113);
               char_literal114 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_includeExpr1463);
               stream_LPAREN.add(char_literal114);
               rp = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_includeExpr1467);
               stream_RPAREN.add(rp);
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(46, (String)"INCLUDE_SUPER_REGION")), root_1);
               this.adaptor.addChild(root_1, stream_ID.nextNode());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
               break;
            case 5:
               char_literal115 = (CommonToken)this.match(this.input, 33, FOLLOW_AT_in_includeExpr1482);
               stream_AT.add(char_literal115);
               ID116 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_includeExpr1484);
               stream_ID.add(ID116);
               char_literal117 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_includeExpr1486);
               stream_LPAREN.add(char_literal117);
               rp = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_includeExpr1490);
               stream_RPAREN.add(rp);
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.nil();
               root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(44, (String)"INCLUDE_REGION")), root_1);
               this.adaptor.addChild(root_1, stream_ID.nextNode());
               this.adaptor.addChild(root_0, root_1);
               retval.tree = root_0;
               break;
            case 6:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_primary_in_includeExpr1508);
               primary118 = this.primary();
               --this.state._fsp;
               this.adaptor.addChild(root_0, primary118.getTree());
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var77) {
         throw var77;
      } finally {
         ;
      }
   }

   public final primary_return primary() throws RecognitionException {
      primary_return retval = new primary_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken lp = null;
      CommonToken ID119 = null;
      CommonToken STRING120 = null;
      CommonToken TRUE121 = null;
      CommonToken FALSE122 = null;
      CommonToken char_literal125 = null;
      CommonToken char_literal127 = null;
      CommonToken char_literal129 = null;
      CommonToken char_literal130 = null;
      CommonToken char_literal132 = null;
      ParserRuleReturnScope subtemplate123 = null;
      ParserRuleReturnScope list124 = null;
      ParserRuleReturnScope conditional126 = null;
      ParserRuleReturnScope expr128 = null;
      ParserRuleReturnScope argExprList131 = null;
      CommonTree lp_tree = null;
      CommonTree ID119_tree = null;
      CommonTree STRING120_tree = null;
      CommonTree TRUE121_tree = null;
      CommonTree FALSE122_tree = null;
      CommonTree char_literal125_tree = null;
      CommonTree char_literal127_tree = null;
      CommonTree char_literal129_tree = null;
      CommonTree char_literal130_tree = null;
      CommonTree char_literal132_tree = null;
      RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
      RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
      RewriteRuleSubtreeStream stream_argExprList = new RewriteRuleSubtreeStream(this.adaptor, "rule argExprList");
      RewriteRuleSubtreeStream stream_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule expr");

      try {
         int alt38 = 8;
         int LA38_0 = this.input.LA(1);
         int nvaeMark;
         NoViableAltException nvae;
         if (LA38_0 == 25) {
            alt38 = 1;
         } else if (LA38_0 == 26) {
            alt38 = 2;
         } else if (LA38_0 == 35) {
            alt38 = 3;
         } else if (LA38_0 == 36) {
            alt38 = 4;
         } else if (LA38_0 == 20) {
            alt38 = 5;
         } else if (LA38_0 == 16) {
            alt38 = 6;
         } else if (LA38_0 == 14 && (this.conditional_stack.size() > 0 || this.conditional_stack.size() == 0)) {
            int LA38_7 = this.input.LA(2);
            if (this.conditional_stack.size() > 0) {
               alt38 = 7;
            } else {
               if (this.conditional_stack.size() != 0) {
                  nvaeMark = this.input.mark();

                  try {
                     this.input.consume();
                     nvae = new NoViableAltException("", 38, 7, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(nvaeMark);
                  }
               }

               alt38 = 8;
            }
         }

         switch (alt38) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               ID119 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_primary1519);
               ID119_tree = (CommonTree)this.adaptor.create(ID119);
               this.adaptor.addChild(root_0, ID119_tree);
               break;
            case 2:
               root_0 = (CommonTree)this.adaptor.nil();
               STRING120 = (CommonToken)this.match(this.input, 26, FOLLOW_STRING_in_primary1524);
               STRING120_tree = (CommonTree)this.adaptor.create(STRING120);
               this.adaptor.addChild(root_0, STRING120_tree);
               break;
            case 3:
               root_0 = (CommonTree)this.adaptor.nil();
               TRUE121 = (CommonToken)this.match(this.input, 35, FOLLOW_TRUE_in_primary1529);
               TRUE121_tree = (CommonTree)this.adaptor.create(TRUE121);
               this.adaptor.addChild(root_0, TRUE121_tree);
               break;
            case 4:
               root_0 = (CommonTree)this.adaptor.nil();
               FALSE122 = (CommonToken)this.match(this.input, 36, FOLLOW_FALSE_in_primary1534);
               FALSE122_tree = (CommonTree)this.adaptor.create(FALSE122);
               this.adaptor.addChild(root_0, FALSE122_tree);
               break;
            case 5:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_subtemplate_in_primary1539);
               subtemplate123 = this.subtemplate();
               --this.state._fsp;
               this.adaptor.addChild(root_0, subtemplate123.getTree());
               break;
            case 6:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_list_in_primary1544);
               list124 = this.list();
               --this.state._fsp;
               this.adaptor.addChild(root_0, list124.getTree());
               break;
            case 7:
               root_0 = (CommonTree)this.adaptor.nil();
               if (this.conditional_stack.size() <= 0) {
                  throw new FailedPredicateException(this.input, "primary", "$conditional.size()>0");
               }

               char_literal125 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_primary1553);
               this.pushFollow(FOLLOW_conditional_in_primary1556);
               conditional126 = this.conditional();
               --this.state._fsp;
               this.adaptor.addChild(root_0, conditional126.getTree());
               char_literal127 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_primary1558);
               break;
            case 8:
               if (this.conditional_stack.size() != 0) {
                  throw new FailedPredicateException(this.input, "primary", "$conditional.size()==0");
               }

               lp = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_primary1569);
               stream_LPAREN.add(lp);
               this.pushFollow(FOLLOW_expr_in_primary1571);
               expr128 = this.expr();
               --this.state._fsp;
               stream_expr.add(expr128.getTree());
               char_literal129 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_primary1573);
               stream_RPAREN.add(char_literal129);
               int alt37 = true;
               nvaeMark = this.input.LA(1);
               byte alt37;
               if (nvaeMark == 14) {
                  alt37 = 1;
               } else {
                  if (nvaeMark != 9 && nvaeMark != 13 && nvaeMark != 15 && (nvaeMark < 17 || nvaeMark > 19) && nvaeMark != 24 && (nvaeMark < 29 || nvaeMark > 30)) {
                     nvae = new NoViableAltException("", 37, 0, this.input);
                     throw nvae;
                  }

                  alt37 = 2;
               }

               label463:
               switch (alt37) {
                  case 1:
                     char_literal130 = (CommonToken)this.match(this.input, 14, FOLLOW_LPAREN_in_primary1579);
                     stream_LPAREN.add(char_literal130);
                     int alt36 = 2;
                     int LA36_0 = this.input.LA(1);
                     if (LA36_0 != 8 && LA36_0 != 16 && LA36_0 != 20 && (LA36_0 < 25 || LA36_0 > 26) && LA36_0 != 33 && (LA36_0 < 35 || LA36_0 > 36)) {
                        if (LA36_0 == 14 && (this.conditional_stack.size() > 0 || this.conditional_stack.size() == 0)) {
                           alt36 = 1;
                        }
                     } else {
                        alt36 = 1;
                     }

                     switch (alt36) {
                        case 1:
                           this.pushFollow(FOLLOW_argExprList_in_primary1581);
                           argExprList131 = this.argExprList();
                           --this.state._fsp;
                           stream_argExprList.add(argExprList131.getTree());
                        default:
                           char_literal132 = (CommonToken)this.match(this.input, 15, FOLLOW_RPAREN_in_primary1584);
                           stream_RPAREN.add(char_literal132);
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(43, (Token)lp)), root_1);
                           this.adaptor.addChild(root_1, stream_expr.nextTree());
                           if (stream_argExprList.hasNext()) {
                              this.adaptor.addChild(root_1, stream_argExprList.nextTree());
                           }

                           stream_argExprList.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                           break label463;
                     }
                  case 2:
                     retval.tree = root_0;
                     new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                     root_0 = (CommonTree)this.adaptor.nil();
                     CommonTree root_1 = (CommonTree)this.adaptor.nil();
                     root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(56, (Token)lp)), root_1);
                     this.adaptor.addChild(root_1, stream_expr.nextTree());
                     this.adaptor.addChild(root_0, root_1);
                     retval.tree = root_0;
               }
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var48) {
         throw var48;
      } finally {
         ;
      }
   }

   public final args_return args() throws RecognitionException {
      args_return retval = new args_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken char_literal135 = null;
      CommonToken char_literal137 = null;
      CommonToken string_literal138 = null;
      CommonToken string_literal139 = null;
      ParserRuleReturnScope argExprList133 = null;
      ParserRuleReturnScope namedArg134 = null;
      ParserRuleReturnScope namedArg136 = null;
      CommonTree char_literal135_tree = null;
      CommonTree char_literal137_tree = null;
      CommonTree string_literal138_tree = null;
      CommonTree string_literal139_tree = null;
      RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
      RewriteRuleTokenStream stream_ELLIPSIS = new RewriteRuleTokenStream(this.adaptor, "token ELLIPSIS");
      RewriteRuleSubtreeStream stream_namedArg = new RewriteRuleSubtreeStream(this.adaptor, "rule namedArg");

      try {
         int alt41 = true;
         int LA41_0 = this.input.LA(1);
         int LA40_0;
         byte alt41;
         if (LA41_0 == 25) {
            int LA41_1 = this.input.LA(2);
            if (LA41_1 >= 13 && LA41_1 <= 15 || LA41_1 >= 18 && LA41_1 <= 19) {
               alt41 = 1;
            } else {
               if (LA41_1 != 12) {
                  LA40_0 = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 41, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(LA40_0);
                  }
               }

               alt41 = 2;
            }
         } else if (LA41_0 != 8 && LA41_0 != 16 && LA41_0 != 20 && LA41_0 != 26 && LA41_0 != 33 && (LA41_0 < 35 || LA41_0 > 36)) {
            if (LA41_0 == 14 && (this.conditional_stack.size() > 0 || this.conditional_stack.size() == 0)) {
               alt41 = 1;
            } else if (LA41_0 == 11) {
               alt41 = 3;
            } else {
               if (LA41_0 != 15) {
                  NoViableAltException nvae = new NoViableAltException("", 41, 0, this.input);
                  throw nvae;
               }

               alt41 = 4;
            }
         } else {
            alt41 = 1;
         }

         label372:
         switch (alt41) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_argExprList_in_args1640);
               argExprList133 = this.argExprList();
               --this.state._fsp;
               this.adaptor.addChild(root_0, argExprList133.getTree());
               break;
            case 2:
               this.pushFollow(FOLLOW_namedArg_in_args1645);
               namedArg134 = this.namedArg();
               --this.state._fsp;
               stream_namedArg.add(namedArg134.getTree());

               while(true) {
                  int alt40 = 2;
                  LA40_0 = this.input.LA(1);
                  if (LA40_0 == 18) {
                     int LA39_1 = this.input.LA(2);
                     if (LA39_1 == 25) {
                        alt40 = 1;
                     }
                  }

                  switch (alt40) {
                     case 1:
                        char_literal135 = (CommonToken)this.match(this.input, 18, FOLLOW_COMMA_in_args1649);
                        stream_COMMA.add(char_literal135);
                        this.pushFollow(FOLLOW_namedArg_in_args1651);
                        namedArg136 = this.namedArg();
                        --this.state._fsp;
                        stream_namedArg.add(namedArg136.getTree());
                        break;
                     default:
                        alt40 = 2;
                        LA40_0 = this.input.LA(1);
                        if (LA40_0 == 18) {
                           alt40 = 1;
                        }

                        switch (alt40) {
                           case 1:
                              char_literal137 = (CommonToken)this.match(this.input, 18, FOLLOW_COMMA_in_args1657);
                              stream_COMMA.add(char_literal137);
                              string_literal138 = (CommonToken)this.match(this.input, 11, FOLLOW_ELLIPSIS_in_args1659);
                              stream_ELLIPSIS.add(string_literal138);
                           default:
                              retval.tree = root_0;
                              new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                              root_0 = (CommonTree)this.adaptor.nil();
                              if (!stream_namedArg.hasNext()) {
                                 throw new RewriteEarlyExitException();
                              }
                        }

                        while(stream_namedArg.hasNext()) {
                           this.adaptor.addChild(root_0, stream_namedArg.nextTree());
                        }

                        stream_namedArg.reset();
                        if (stream_ELLIPSIS.hasNext()) {
                           this.adaptor.addChild(root_0, stream_ELLIPSIS.nextNode());
                        }

                        stream_ELLIPSIS.reset();
                        retval.tree = root_0;
                        break label372;
                  }
               }
            case 3:
               root_0 = (CommonTree)this.adaptor.nil();
               string_literal139 = (CommonToken)this.match(this.input, 11, FOLLOW_ELLIPSIS_in_args1679);
               string_literal139_tree = (CommonTree)this.adaptor.create(string_literal139);
               this.adaptor.addChild(root_0, string_literal139_tree);
               break;
            case 4:
               root_0 = (CommonTree)this.adaptor.nil();
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var31) {
         throw var31;
      } finally {
         ;
      }
   }

   public final argExprList_return argExprList() throws RecognitionException {
      argExprList_return retval = new argExprList_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken char_literal141 = null;
      ParserRuleReturnScope arg140 = null;
      ParserRuleReturnScope arg142 = null;
      CommonTree char_literal141_tree = null;
      RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
      RewriteRuleSubtreeStream stream_arg = new RewriteRuleSubtreeStream(this.adaptor, "rule arg");

      try {
         this.pushFollow(FOLLOW_arg_in_argExprList1692);
         arg140 = this.arg();
         --this.state._fsp;
         stream_arg.add(arg140.getTree());

         while(true) {
            int alt42 = 2;
            int LA42_0 = this.input.LA(1);
            if (LA42_0 == 18) {
               alt42 = 1;
            }

            switch (alt42) {
               case 1:
                  char_literal141 = (CommonToken)this.match(this.input, 18, FOLLOW_COMMA_in_argExprList1696);
                  stream_COMMA.add(char_literal141);
                  this.pushFollow(FOLLOW_arg_in_argExprList1698);
                  arg142 = this.arg();
                  --this.state._fsp;
                  stream_arg.add(arg142.getTree());
                  break;
               default:
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  if (!stream_arg.hasNext()) {
                     throw new RewriteEarlyExitException();
                  }

                  while(stream_arg.hasNext()) {
                     this.adaptor.addChild(root_0, stream_arg.nextTree());
                  }

                  stream_arg.reset();
                  retval.tree = root_0;
                  retval.stop = this.input.LT(-1);
                  retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
                  this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
                  return retval;
            }
         }
      } catch (RecognitionException var14) {
         throw var14;
      } finally {
         ;
      }
   }

   public final arg_return arg() throws RecognitionException {
      arg_return retval = new arg_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope exprNoComma143 = null;

      try {
         root_0 = (CommonTree)this.adaptor.nil();
         this.pushFollow(FOLLOW_exprNoComma_in_arg1715);
         exprNoComma143 = this.exprNoComma();
         --this.state._fsp;
         this.adaptor.addChild(root_0, exprNoComma143.getTree());
         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var8) {
         throw var8;
      } finally {
         ;
      }
   }

   public final namedArg_return namedArg() throws RecognitionException {
      namedArg_return retval = new namedArg_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken ID144 = null;
      CommonToken char_literal145 = null;
      ParserRuleReturnScope arg146 = null;
      CommonTree ID144_tree = null;
      CommonTree char_literal145_tree = null;
      RewriteRuleTokenStream stream_EQUALS = new RewriteRuleTokenStream(this.adaptor, "token EQUALS");
      RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
      RewriteRuleSubtreeStream stream_arg = new RewriteRuleSubtreeStream(this.adaptor, "rule arg");

      try {
         ID144 = (CommonToken)this.match(this.input, 25, FOLLOW_ID_in_namedArg1724);
         stream_ID.add(ID144);
         char_literal145 = (CommonToken)this.match(this.input, 12, FOLLOW_EQUALS_in_namedArg1726);
         stream_EQUALS.add(char_literal145);
         this.pushFollow(FOLLOW_arg_in_namedArg1728);
         arg146 = this.arg();
         --this.state._fsp;
         stream_arg.add(arg146.getTree());
         retval.tree = root_0;
         new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
         root_0 = (CommonTree)this.adaptor.nil();
         CommonTree root_1 = (CommonTree)this.adaptor.nil();
         root_1 = (CommonTree)this.adaptor.becomeRoot((Object)stream_EQUALS.nextNode(), root_1);
         this.adaptor.addChild(root_1, stream_ID.nextNode());
         this.adaptor.addChild(root_1, stream_arg.nextTree());
         this.adaptor.addChild(root_0, root_1);
         retval.tree = root_0;
         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var16) {
         throw var16;
      } finally {
         ;
      }
   }

   public final list_return list() throws RecognitionException {
      list_return retval = new list_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      CommonToken lb = null;
      CommonToken char_literal147 = null;
      CommonToken char_literal149 = null;
      CommonToken char_literal151 = null;
      ParserRuleReturnScope listElement148 = null;
      ParserRuleReturnScope listElement150 = null;
      CommonTree lb_tree = null;
      CommonTree char_literal147_tree = null;
      CommonTree char_literal149_tree = null;
      CommonTree char_literal151_tree = null;
      RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
      RewriteRuleTokenStream stream_RBRACK = new RewriteRuleTokenStream(this.adaptor, "token RBRACK");
      RewriteRuleTokenStream stream_LBRACK = new RewriteRuleTokenStream(this.adaptor, "token LBRACK");
      RewriteRuleSubtreeStream stream_listElement = new RewriteRuleSubtreeStream(this.adaptor, "rule listElement");

      try {
         int alt44 = true;
         int LA44_0 = this.input.LA(1);
         if (LA44_0 != 16) {
            NoViableAltException nvae = new NoViableAltException("", 44, 0, this.input);
            throw nvae;
         } else {
            int LA44_1 = this.input.LA(2);
            int LA43_0;
            byte alt44;
            if (LA44_1 == 17) {
               LA43_0 = this.input.LA(3);
               if (this.input.LA(2) == 17) {
                  alt44 = 1;
               } else {
                  alt44 = 2;
               }
            } else {
               if (LA44_1 != 8 && LA44_1 != 14 && LA44_1 != 16 && LA44_1 != 18 && LA44_1 != 20 && (LA44_1 < 25 || LA44_1 > 26) && LA44_1 != 33 && (LA44_1 < 35 || LA44_1 > 36)) {
                  LA43_0 = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 44, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(LA43_0);
                  }
               }

               alt44 = 2;
            }

            switch (alt44) {
               case 1:
                  if (this.input.LA(2) != 17) {
                     throw new FailedPredicateException(this.input, "list", "input.LA(2)==RBRACK");
                  }

                  lb = (CommonToken)this.match(this.input, 16, FOLLOW_LBRACK_in_list1753);
                  stream_LBRACK.add(lb);
                  char_literal147 = (CommonToken)this.match(this.input, 17, FOLLOW_RBRACK_in_list1755);
                  stream_RBRACK.add(char_literal147);
                  retval.tree = root_0;
                  new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                  root_0 = (CommonTree)this.adaptor.nil();
                  this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(48, (Token)lb));
                  retval.tree = root_0;
                  break;
               case 2:
                  lb = (CommonToken)this.match(this.input, 16, FOLLOW_LBRACK_in_list1767);
                  stream_LBRACK.add(lb);
                  this.pushFollow(FOLLOW_listElement_in_list1769);
                  listElement148 = this.listElement();
                  --this.state._fsp;
                  stream_listElement.add(listElement148.getTree());

                  label302:
                  while(true) {
                     int alt43 = 2;
                     LA43_0 = this.input.LA(1);
                     if (LA43_0 == 18) {
                        alt43 = 1;
                     }

                     switch (alt43) {
                        case 1:
                           char_literal149 = (CommonToken)this.match(this.input, 18, FOLLOW_COMMA_in_list1773);
                           stream_COMMA.add(char_literal149);
                           this.pushFollow(FOLLOW_listElement_in_list1775);
                           listElement150 = this.listElement();
                           --this.state._fsp;
                           stream_listElement.add(listElement150.getTree());
                           break;
                        default:
                           char_literal151 = (CommonToken)this.match(this.input, 17, FOLLOW_RBRACK_in_list1780);
                           stream_RBRACK.add(char_literal151);
                           retval.tree = root_0;
                           new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
                           root_0 = (CommonTree)this.adaptor.nil();
                           CommonTree root_1 = (CommonTree)this.adaptor.nil();
                           root_1 = (CommonTree)this.adaptor.becomeRoot((Object)((CommonTree)this.adaptor.create(48, (Token)lb)), root_1);

                           while(stream_listElement.hasNext()) {
                              this.adaptor.addChild(root_1, stream_listElement.nextTree());
                           }

                           stream_listElement.reset();
                           this.adaptor.addChild(root_0, root_1);
                           retval.tree = root_0;
                           break label302;
                     }
                  }
            }

            retval.stop = this.input.LT(-1);
            retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
            this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            return retval;
         }
      } catch (RecognitionException var31) {
         throw var31;
      } finally {
         ;
      }
   }

   public final listElement_return listElement() throws RecognitionException {
      listElement_return retval = new listElement_return();
      retval.start = this.input.LT(1);
      CommonTree root_0 = null;
      ParserRuleReturnScope exprNoComma152 = null;

      try {
         int alt45 = true;
         int LA45_0 = this.input.LA(1);
         byte alt45;
         if (LA45_0 == 8 || LA45_0 == 16 || LA45_0 == 20 || LA45_0 >= 25 && LA45_0 <= 26 || LA45_0 == 33 || LA45_0 >= 35 && LA45_0 <= 36) {
            alt45 = 1;
         } else if (LA45_0 == 14 && (this.conditional_stack.size() > 0 || this.conditional_stack.size() == 0)) {
            alt45 = 1;
         } else {
            if (LA45_0 < 17 || LA45_0 > 18) {
               NoViableAltException nvae = new NoViableAltException("", 45, 0, this.input);
               throw nvae;
            }

            alt45 = 2;
         }

         switch (alt45) {
            case 1:
               root_0 = (CommonTree)this.adaptor.nil();
               this.pushFollow(FOLLOW_exprNoComma_in_listElement1800);
               exprNoComma152 = this.exprNoComma();
               --this.state._fsp;
               this.adaptor.addChild(root_0, exprNoComma152.getTree());
               break;
            case 2:
               retval.tree = root_0;
               new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.getTree() : null);
               root_0 = (CommonTree)this.adaptor.nil();
               this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(50, (String)"NULL"));
               retval.tree = root_0;
         }

         retval.stop = this.input.LT(-1);
         retval.tree = (CommonTree)this.adaptor.rulePostProcessing(root_0);
         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
         return retval;
      } catch (RecognitionException var10) {
         throw var10;
      } finally {
         ;
      }
   }

   public static class listElement_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class list_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class namedArg_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class arg_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class argExprList_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class args_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class primary_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class includeExpr_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class memberExpr_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class mapTemplateRef_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class mapExpr_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class expr_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class exprNoComma_return extends ParserRuleReturnScope {
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

   public static class exprOptions_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class notConditionalExpr_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class notConditional_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class andConditional_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class conditional_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   protected static class conditional_scope {
      boolean inside;
   }

   public static class ifstat_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class subtemplate_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class region_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class exprTag_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class compoundElement_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class singleElement_return extends ParserRuleReturnScope {
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

   public static class template_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }

   public static class templateAndEOF_return extends ParserRuleReturnScope {
      CommonTree tree;

      public CommonTree getTree() {
         return this.tree;
      }
   }
}
