package org.stringtemplate.v4.compiler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.EarlyExitException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeNodeStream;
import org.antlr.runtime.tree.TreeParser;
import org.antlr.runtime.tree.TreeRuleReturnScope;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.ErrorManager;
import org.stringtemplate.v4.misc.ErrorType;
import org.stringtemplate.v4.misc.Misc;

public class CodeGenerator extends TreeParser {
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
   String outermostTemplateName;
   CompiledST outermostImpl;
   Token templateToken;
   String template;
   ErrorManager errMgr;
   protected Stack template_stack;
   public static final BitSet FOLLOW_template_in_templateAndEOF50 = new BitSet(new long[]{0L});
   public static final BitSet FOLLOW_EOF_in_templateAndEOF53 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_chunk_in_template77 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_element_in_chunk92 = new BitSet(new long[]{18157339320254482L});
   public static final BitSet FOLLOW_INDENTED_EXPR_in_element105 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_INDENT_in_element107 = new BitSet(new long[]{18014398509482000L});
   public static final BitSet FOLLOW_compoundElement_in_element109 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_compoundElement_in_element117 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_INDENTED_EXPR_in_element124 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_INDENT_in_element126 = new BitSet(new long[]{2203322417152L});
   public static final BitSet FOLLOW_singleElement_in_element130 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_singleElement_in_element138 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_exprElement_in_singleElement149 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TEXT_in_singleElement154 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NEWLINE_in_singleElement164 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ifstat_in_compoundElement178 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_region_in_compoundElement184 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_EXPR_in_exprElement203 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_expr_in_exprElement205 = new BitSet(new long[]{2251799813685256L});
   public static final BitSet FOLLOW_exprOptions_in_exprElement208 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_REGION_in_region246 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_region248 = new BitSet(new long[]{18157339320254480L});
   public static final BitSet FOLLOW_template_in_region258 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_SUBTEMPLATE_in_subtemplate291 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ARGS_in_subtemplate298 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_subtemplate301 = new BitSet(new long[]{33554440L});
   public static final BitSet FOLLOW_template_in_subtemplate318 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_SUBTEMPLATE_in_subtemplate334 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_IF_in_ifstat366 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_conditional_in_ifstat368 = new BitSet(new long[]{18157339320254584L});
   public static final BitSet FOLLOW_chunk_in_ifstat378 = new BitSet(new long[]{104L});
   public static final BitSet FOLLOW_ELSEIF_in_ifstat388 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_conditional_in_ifstat402 = new BitSet(new long[]{18157339320254488L});
   public static final BitSet FOLLOW_chunk_in_ifstat414 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ELSE_in_ifstat437 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_chunk_in_ifstat451 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_OR_in_conditional485 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_conditional_in_conditional487 = new BitSet(new long[]{266694346688955392L});
   public static final BitSet FOLLOW_conditional_in_conditional489 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_AND_in_conditional499 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_conditional_in_conditional501 = new BitSet(new long[]{266694346688955392L});
   public static final BitSet FOLLOW_conditional_in_conditional503 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_BANG_in_conditional513 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_conditional_in_conditional515 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_expr_in_conditional527 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_OPTIONS_in_exprOptions541 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_option_in_exprOptions543 = new BitSet(new long[]{4104L});
   public static final BitSet FOLLOW_EQUALS_in_option555 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_option557 = new BitSet(new long[]{266694345078341632L});
   public static final BitSet FOLLOW_expr_in_option559 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ZIP_in_expr578 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ELEMENTS_in_expr581 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_expr_in_expr584 = new BitSet(new long[]{266694345078341640L});
   public static final BitSet FOLLOW_mapTemplateRef_in_expr591 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_MAP_in_expr603 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_expr_in_expr605 = new BitSet(new long[]{36041991158497280L});
   public static final BitSet FOLLOW_mapTemplateRef_in_expr608 = new BitSet(new long[]{36041991158497288L});
   public static final BitSet FOLLOW_prop_in_expr623 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_includeExpr_in_expr628 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_PROP_in_prop638 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_expr_in_prop640 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_ID_in_prop642 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_PROP_IND_in_prop656 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_expr_in_prop658 = new BitSet(new long[]{266694345078341632L});
   public static final BitSet FOLLOW_expr_in_prop660 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_INCLUDE_in_mapTemplateRef680 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_mapTemplateRef682 = new BitSet(new long[]{266694345078347784L});
   public static final BitSet FOLLOW_args_in_mapTemplateRef692 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_subtemplate_in_mapTemplateRef705 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_INCLUDE_IND_in_mapTemplateRef717 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_expr_in_mapTemplateRef719 = new BitSet(new long[]{266694345078347784L});
   public static final BitSet FOLLOW_args_in_mapTemplateRef729 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_EXEC_FUNC_in_includeExpr751 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_includeExpr753 = new BitSet(new long[]{266694345078341640L});
   public static final BitSet FOLLOW_expr_in_includeExpr755 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_INCLUDE_in_includeExpr766 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_includeExpr768 = new BitSet(new long[]{266694345078347784L});
   public static final BitSet FOLLOW_args_in_includeExpr770 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_INCLUDE_SUPER_in_includeExpr781 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_includeExpr783 = new BitSet(new long[]{266694345078347784L});
   public static final BitSet FOLLOW_args_in_includeExpr785 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_INCLUDE_REGION_in_includeExpr796 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_includeExpr798 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_INCLUDE_SUPER_REGION_in_includeExpr808 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_includeExpr810 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_primary_in_includeExpr818 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ID_in_primary829 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_in_primary839 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TRUE_in_primary848 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FALSE_in_primary857 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_subtemplate_in_primary866 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_list_in_primary893 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_INCLUDE_IND_in_primary900 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_expr_in_primary905 = new BitSet(new long[]{266694345078347784L});
   public static final BitSet FOLLOW_args_in_primary914 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_TO_STR_in_primary934 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_expr_in_primary936 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_expr_in_arg949 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_arg_in_args965 = new BitSet(new long[]{266694345078341634L});
   public static final BitSet FOLLOW_EQUALS_in_args984 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_ID_in_args986 = new BitSet(new long[]{266694345078341632L});
   public static final BitSet FOLLOW_expr_in_args988 = new BitSet(new long[]{8L});
   public static final BitSet FOLLOW_ELLIPSIS_in_args1005 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ELLIPSIS_in_args1020 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LIST_in_list1040 = new BitSet(new long[]{4L});
   public static final BitSet FOLLOW_listElement_in_list1043 = new BitSet(new long[]{267820244985184264L});
   public static final BitSet FOLLOW_expr_in_listElement1059 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_NULL_in_listElement1063 = new BitSet(new long[]{2L});

   public TreeParser[] getDelegates() {
      return new TreeParser[0];
   }

   public CodeGenerator(TreeNodeStream input) {
      this(input, new RecognizerSharedState());
   }

   public CodeGenerator(TreeNodeStream input, RecognizerSharedState state) {
      super(input, state);
      this.template_stack = new Stack();
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\stringtemplate\\v4\\compiler\\CodeGenerator.g";
   }

   public CodeGenerator(TreeNodeStream input, ErrorManager errMgr, String name, String template, Token templateToken) {
      this(input, new RecognizerSharedState());
      this.errMgr = errMgr;
      this.outermostTemplateName = name;
      this.template = template;
      this.templateToken = templateToken;
   }

   public void emit1(CommonTree opAST, short opcode, int arg) {
      ((template_scope)this.template_stack.peek()).state.emit1(opAST, opcode, arg);
   }

   public void emit1(CommonTree opAST, short opcode, String arg) {
      ((template_scope)this.template_stack.peek()).state.emit1(opAST, opcode, arg);
   }

   public void emit2(CommonTree opAST, short opcode, int arg, int arg2) {
      ((template_scope)this.template_stack.peek()).state.emit2(opAST, opcode, arg, arg2);
   }

   public void emit2(CommonTree opAST, short opcode, String s, int arg2) {
      ((template_scope)this.template_stack.peek()).state.emit2(opAST, opcode, s, arg2);
   }

   public void emit(CommonTree opAST, short opcode) {
      ((template_scope)this.template_stack.peek()).state.emit(opAST, opcode);
   }

   public void insert(int addr, short opcode, String s) {
      ((template_scope)this.template_stack.peek()).state.insert(addr, opcode, s);
   }

   public void setOption(CommonTree id) {
      ((template_scope)this.template_stack.peek()).state.setOption(id);
   }

   public void write(int addr, short value) {
      ((template_scope)this.template_stack.peek()).state.write(addr, value);
   }

   public int address() {
      return ((template_scope)this.template_stack.peek()).state.ip;
   }

   public void func(CommonTree id) {
      ((template_scope)this.template_stack.peek()).state.func(this.templateToken, id);
   }

   public void refAttr(CommonTree id) {
      ((template_scope)this.template_stack.peek()).state.refAttr(this.templateToken, id);
   }

   public int defineString(String s) {
      return ((template_scope)this.template_stack.peek()).state.defineString(s);
   }

   public final void templateAndEOF() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_template_in_templateAndEOF50);
            this.template((String)null, (List)null);
            --this.state._fsp;
            this.match(this.input, -1, FOLLOW_EOF_in_templateAndEOF53);
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final CompiledST template(String name, List args) throws RecognitionException {
      this.template_stack.push(new template_scope());
      CompiledST impl = null;
      ((template_scope)this.template_stack.peek()).state = new CompilationState(this.errMgr, name, this.input.getTokenStream());
      impl = ((template_scope)this.template_stack.peek()).state.impl;
      if (this.template_stack.size() == 1) {
         this.outermostImpl = impl;
      }

      impl.defineFormalArgs(args);
      if (name != null && name.startsWith("_sub")) {
         impl.addArg(new FormalArgument("i"));
         impl.addArg(new FormalArgument("i0"));
      }

      impl.template = this.template;

      try {
         this.pushFollow(FOLLOW_chunk_in_template77);
         this.chunk();
         --this.state._fsp;
         if (((template_scope)this.template_stack.peek()).state.stringtable != null) {
            impl.strings = ((template_scope)this.template_stack.peek()).state.stringtable.toArray();
         }

         impl.codeSize = ((template_scope)this.template_stack.peek()).state.ip;
      } catch (RecognitionException var8) {
         this.reportError(var8);
         this.recover(this.input, var8);
      } finally {
         this.template_stack.pop();
      }

      return impl;
   }

   public final void chunk() throws RecognitionException {
      try {
         while(true) {
            try {
               int alt1 = 2;
               int LA1_0 = this.input.LA(1);
               if (LA1_0 == 4 || LA1_0 == 22 || LA1_0 == 32 || LA1_0 == 41 || LA1_0 == 47 || LA1_0 == 54) {
                  alt1 = 1;
               }

               switch (alt1) {
                  case 1:
                     this.pushFollow(FOLLOW_element_in_chunk92);
                     this.element();
                     --this.state._fsp;
                     continue;
               }
            } catch (RecognitionException var6) {
               this.reportError(var6);
               this.recover(this.input, var6);
            }

            return;
         }
      } finally {
         ;
      }
   }

   public final void element() throws RecognitionException {
      CommonTree INDENT1 = null;
      CommonTree INDENT2 = null;

      try {
         try {
            int alt2 = true;
            byte alt2;
            switch (this.input.LA(1)) {
               case 4:
               case 54:
                  alt2 = 2;
                  break;
               case 22:
               case 32:
               case 41:
                  alt2 = 4;
                  break;
               case 47:
                  int LA2_1 = this.input.LA(2);
                  int nvaeMark;
                  if (LA2_1 != 2) {
                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 2, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  nvaeMark = this.input.LA(3);
                  int LA2_5;
                  int nvaeMark;
                  if (nvaeMark != 31) {
                     LA2_5 = this.input.mark();

                     try {
                        for(nvaeMark = 0; nvaeMark < 2; ++nvaeMark) {
                           this.input.consume();
                        }

                        NoViableAltException nvae = new NoViableAltException("", 2, 4, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA2_5);
                     }
                  }

                  LA2_5 = this.input.LA(4);
                  if (LA2_5 != 4 && LA2_5 != 54) {
                     if (LA2_5 != 22 && LA2_5 != 32 && LA2_5 != 41) {
                        nvaeMark = this.input.mark();

                        try {
                           for(int nvaeConsume = 0; nvaeConsume < 3; ++nvaeConsume) {
                              this.input.consume();
                           }

                           NoViableAltException nvae = new NoViableAltException("", 2, 5, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt2 = 3;
                     break;
                  }

                  alt2 = 1;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 2, 0, this.input);
                  throw nvae;
            }

            switch (alt2) {
               case 1:
                  this.match(this.input, 47, FOLLOW_INDENTED_EXPR_in_element105);
                  this.match(this.input, 2, (BitSet)null);
                  INDENT1 = (CommonTree)this.match(this.input, 31, FOLLOW_INDENT_in_element107);
                  this.pushFollow(FOLLOW_compoundElement_in_element109);
                  this.compoundElement(INDENT1);
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 2:
                  this.pushFollow(FOLLOW_compoundElement_in_element117);
                  this.compoundElement((CommonTree)null);
                  --this.state._fsp;
                  break;
               case 3:
                  this.match(this.input, 47, FOLLOW_INDENTED_EXPR_in_element124);
                  this.match(this.input, 2, (BitSet)null);
                  INDENT2 = (CommonTree)this.match(this.input, 31, FOLLOW_INDENT_in_element126);
                  ((template_scope)this.template_stack.peek()).state.indent(INDENT2);
                  this.pushFollow(FOLLOW_singleElement_in_element130);
                  this.singleElement();
                  --this.state._fsp;
                  ((template_scope)this.template_stack.peek()).state.emit((short)40);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 4:
                  this.pushFollow(FOLLOW_singleElement_in_element138);
                  this.singleElement();
                  --this.state._fsp;
            }
         } catch (RecognitionException var36) {
            this.reportError(var36);
            this.recover(this.input, var36);
         }

      } finally {
         ;
      }
   }

   public final void singleElement() throws RecognitionException {
      CommonTree TEXT3 = null;
      CommonTree NEWLINE4 = null;

      try {
         try {
            int alt3 = true;
            byte alt3;
            switch (this.input.LA(1)) {
               case 22:
                  alt3 = 2;
                  break;
               case 32:
                  alt3 = 3;
                  break;
               case 41:
                  alt3 = 1;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);
                  throw nvae;
            }

            switch (alt3) {
               case 1:
                  this.pushFollow(FOLLOW_exprElement_in_singleElement149);
                  this.exprElement();
                  --this.state._fsp;
                  break;
               case 2:
                  TEXT3 = (CommonTree)this.match(this.input, 22, FOLLOW_TEXT_in_singleElement154);
                  if ((TEXT3 != null ? TEXT3.getText() : null).length() > 0) {
                     this.emit1(TEXT3, (short)47, TEXT3 != null ? TEXT3.getText() : null);
                  }
                  break;
               case 3:
                  NEWLINE4 = (CommonTree)this.match(this.input, 32, FOLLOW_NEWLINE_in_singleElement164);
                  this.emit(NEWLINE4, (short)41);
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final void compoundElement(CommonTree indent) throws RecognitionException {
      try {
         try {
            int alt4 = true;
            int LA4_0 = this.input.LA(1);
            byte alt4;
            if (LA4_0 == 4) {
               alt4 = 1;
            } else {
               if (LA4_0 != 54) {
                  NoViableAltException nvae = new NoViableAltException("", 4, 0, this.input);
                  throw nvae;
               }

               alt4 = 2;
            }

            switch (alt4) {
               case 1:
                  this.pushFollow(FOLLOW_ifstat_in_compoundElement178);
                  this.ifstat(indent);
                  --this.state._fsp;
                  break;
               case 2:
                  this.pushFollow(FOLLOW_region_in_compoundElement184);
                  this.region(indent);
                  --this.state._fsp;
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final void exprElement() throws RecognitionException {
      CommonTree EXPR5 = null;
      short op = 13;

      try {
         try {
            EXPR5 = (CommonTree)this.match(this.input, 41, FOLLOW_EXPR_in_exprElement203);
            this.match(this.input, 2, (BitSet)null);
            this.pushFollow(FOLLOW_expr_in_exprElement205);
            this.expr();
            --this.state._fsp;
            int alt5 = 2;
            int LA5_0 = this.input.LA(1);
            if (LA5_0 == 51) {
               alt5 = 1;
            }

            switch (alt5) {
               case 1:
                  this.pushFollow(FOLLOW_exprOptions_in_exprElement208);
                  this.exprOptions();
                  --this.state._fsp;
                  op = 14;
               default:
                  this.match(this.input, 3, (BitSet)null);
                  this.emit(EXPR5, op);
            }
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final region_return region(CommonTree indent) throws RecognitionException {
      region_return retval = new region_return();
      retval.start = this.input.LT(1);
      CommonTree ID6 = null;
      CompiledST template7 = null;
      if (indent != null) {
         ((template_scope)this.template_stack.peek()).state.indent(indent);
      }

      try {
         try {
            this.match(this.input, 54, FOLLOW_REGION_in_region246);
            this.match(this.input, 2, (BitSet)null);
            ID6 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_region248);
            retval.name = STGroup.getMangledRegionName(this.outermostTemplateName, ID6 != null ? ID6.getText() : null);
            this.pushFollow(FOLLOW_template_in_region258);
            template7 = this.template(retval.name, (List)null);
            --this.state._fsp;
            template7.isRegion = true;
            template7.regionDefType = ST.RegionType.EMBEDDED;
            template7.templateDefStartToken = ID6.token;
            this.outermostImpl.addImplicitlyDefinedTemplate(template7);
            this.emit2((CommonTree)retval.start, (short)8, retval.name, 0);
            this.emit((CommonTree)retval.start, (short)13);
            this.match(this.input, 3, (BitSet)null);
            if (indent != null) {
               ((template_scope)this.template_stack.peek()).state.emit((short)40);
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

   public final subtemplate_return subtemplate() throws RecognitionException {
      subtemplate_return retval = new subtemplate_return();
      retval.start = this.input.LT(1);
      CommonTree ID8 = null;
      CommonTree SUBTEMPLATE10 = null;
      CommonTree SUBTEMPLATE11 = null;
      CompiledST template9 = null;
      retval.name = Compiler.getNewSubtemplateName();
      List args = new ArrayList();

      try {
         try {
            int alt8 = true;
            int LA8_0 = this.input.LA(1);
            if (LA8_0 != 55) {
               NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
               throw nvae;
            }

            int LA8_1 = this.input.LA(2);
            int LA7_0;
            byte alt8;
            if (LA8_1 == 2) {
               alt8 = 1;
            } else {
               if ((LA8_1 < 3 || LA8_1 > 6) && (LA8_1 < 10 || LA8_1 > 12) && LA8_1 != 22 && (LA8_1 < 25 || LA8_1 > 26) && (LA8_1 < 29 || LA8_1 > 30) && LA8_1 != 32 && (LA8_1 < 35 || LA8_1 > 36) && (LA8_1 < 40 || LA8_1 > 57)) {
                  LA7_0 = this.input.mark();

                  try {
                     this.input.consume();
                     NoViableAltException nvae = new NoViableAltException("", 8, 1, this.input);
                     throw nvae;
                  } finally {
                     this.input.rewind(LA7_0);
                  }
               }

               alt8 = 2;
            }

            switch (alt8) {
               case 1:
                  SUBTEMPLATE10 = (CommonTree)this.match(this.input, 55, FOLLOW_SUBTEMPLATE_in_subtemplate291);
                  if (this.input.LA(1) == 2) {
                     this.match(this.input, 2, (BitSet)null);

                     label326:
                     while(true) {
                        int alt7 = 2;
                        LA7_0 = this.input.LA(1);
                        if (LA7_0 == 38) {
                           alt7 = 1;
                        }

                        switch (alt7) {
                           case 1:
                              this.match(this.input, 38, FOLLOW_ARGS_in_subtemplate298);
                              this.match(this.input, 2, (BitSet)null);
                              int cnt6 = 0;

                              while(true) {
                                 int alt6 = 2;
                                 int LA6_0 = this.input.LA(1);
                                 if (LA6_0 == 25) {
                                    alt6 = 1;
                                 }

                                 switch (alt6) {
                                    case 1:
                                       ID8 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_subtemplate301);
                                       args.add(new FormalArgument(ID8 != null ? ID8.getText() : null));
                                       ++cnt6;
                                       break;
                                    default:
                                       if (cnt6 < 1) {
                                          EarlyExitException eee = new EarlyExitException(6, this.input);
                                          throw eee;
                                       }

                                       this.match(this.input, 3, (BitSet)null);
                                       continue label326;
                                 }
                              }
                           default:
                              retval.nargs = args.size();
                              this.pushFollow(FOLLOW_template_in_subtemplate318);
                              template9 = this.template(retval.name, args);
                              --this.state._fsp;
                              template9.isAnonSubtemplate = true;
                              template9.templateDefStartToken = SUBTEMPLATE10.token;
                              template9.ast = SUBTEMPLATE10;
                              template9.ast.setUnknownTokenBoundaries();
                              template9.tokens = this.input.getTokenStream();
                              this.outermostImpl.addImplicitlyDefinedTemplate(template9);
                              this.match(this.input, 3, (BitSet)null);
                              return retval;
                        }
                     }
                  }
                  break;
               case 2:
                  SUBTEMPLATE11 = (CommonTree)this.match(this.input, 55, FOLLOW_SUBTEMPLATE_in_subtemplate334);
                  CompiledST sub = new CompiledST();
                  sub.name = retval.name;
                  sub.template = "";
                  sub.addArg(new FormalArgument("i"));
                  sub.addArg(new FormalArgument("i0"));
                  sub.isAnonSubtemplate = true;
                  sub.templateDefStartToken = SUBTEMPLATE11.token;
                  sub.ast = SUBTEMPLATE11;
                  sub.ast.setUnknownTokenBoundaries();
                  sub.tokens = this.input.getTokenStream();
                  this.outermostImpl.addImplicitlyDefinedTemplate(sub);
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

   public final void ifstat(CommonTree indent) throws RecognitionException {
      CommonTree i = null;
      CommonTree eif = null;
      CommonTree el = null;
      TreeRuleReturnScope ec = null;
      int prevBranchOperand = true;
      List endRefs = new ArrayList();
      if (indent != null) {
         ((template_scope)this.template_stack.peek()).state.indent(indent);
      }

      try {
         i = (CommonTree)this.match(this.input, 4, FOLLOW_IF_in_ifstat366);
         this.match(this.input, 2, (BitSet)null);
         this.pushFollow(FOLLOW_conditional_in_ifstat368);
         this.conditional();
         --this.state._fsp;
         int prevBranchOperand = this.address() + 1;
         this.emit1(i, (short)19, -1);
         this.pushFollow(FOLLOW_chunk_in_ifstat378);
         this.chunk();
         --this.state._fsp;

         while(true) {
            int alt10 = 2;
            int LA10_0 = this.input.LA(1);
            if (LA10_0 == 6) {
               alt10 = 1;
            }

            switch (alt10) {
               case 1:
                  eif = (CommonTree)this.match(this.input, 6, FOLLOW_ELSEIF_in_ifstat388);
                  endRefs.add(this.address() + 1);
                  this.emit1(eif, (short)18, -1);
                  this.write(prevBranchOperand, (short)this.address());
                  prevBranchOperand = true;
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_conditional_in_ifstat402);
                  ec = this.conditional();
                  --this.state._fsp;
                  prevBranchOperand = this.address() + 1;
                  this.emit1(ec != null ? (CommonTree)ec.start : null, (short)19, -1);
                  this.pushFollow(FOLLOW_chunk_in_ifstat414);
                  this.chunk();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  break;
               default:
                  alt10 = 2;
                  LA10_0 = this.input.LA(1);
                  if (LA10_0 == 5) {
                     alt10 = 1;
                  }

                  switch (alt10) {
                     case 1:
                        el = (CommonTree)this.match(this.input, 5, FOLLOW_ELSE_in_ifstat437);
                        endRefs.add(this.address() + 1);
                        this.emit1(el, (short)18, -1);
                        this.write(prevBranchOperand, (short)this.address());
                        prevBranchOperand = -1;
                        if (this.input.LA(1) == 2) {
                           this.match(this.input, 2, (BitSet)null);
                           this.pushFollow(FOLLOW_chunk_in_ifstat451);
                           this.chunk();
                           --this.state._fsp;
                           this.match(this.input, 3, (BitSet)null);
                        }
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        if (prevBranchOperand >= 0) {
                           this.write(prevBranchOperand, (short)this.address());
                        }

                        Iterator var10 = endRefs.iterator();

                        while(var10.hasNext()) {
                           int opnd = (Integer)var10.next();
                           this.write(opnd, (short)this.address());
                        }

                        if (indent != null) {
                           ((template_scope)this.template_stack.peek()).state.emit((short)40);
                        }

                        return;
                  }
            }
         }
      } catch (RecognitionException var15) {
         this.reportError(var15);
         this.recover(this.input, var15);
      } finally {
         ;
      }
   }

   public final conditional_return conditional() throws RecognitionException {
      conditional_return retval = new conditional_return();
      retval.start = this.input.LT(1);
      CommonTree OR12 = null;
      CommonTree AND13 = null;
      CommonTree BANG14 = null;

      try {
         try {
            int alt11 = true;
            byte alt11;
            switch (this.input.LA(1)) {
               case 10:
                  alt11 = 3;
                  break;
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 22:
               case 23:
               case 24:
               case 27:
               case 28:
               case 31:
               case 32:
               case 33:
               case 34:
               case 37:
               case 38:
               case 39:
               case 41:
               case 47:
               case 50:
               case 51:
               case 54:
               default:
                  NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
                  throw nvae;
               case 25:
               case 26:
               case 35:
               case 36:
               case 40:
               case 42:
               case 43:
               case 44:
               case 45:
               case 46:
               case 48:
               case 49:
               case 52:
               case 53:
               case 55:
               case 56:
               case 57:
                  alt11 = 4;
                  break;
               case 29:
                  alt11 = 1;
                  break;
               case 30:
                  alt11 = 2;
            }

            switch (alt11) {
               case 1:
                  OR12 = (CommonTree)this.match(this.input, 29, FOLLOW_OR_in_conditional485);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_conditional_in_conditional487);
                  this.conditional();
                  --this.state._fsp;
                  this.pushFollow(FOLLOW_conditional_in_conditional489);
                  this.conditional();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.emit(OR12, (short)37);
                  break;
               case 2:
                  AND13 = (CommonTree)this.match(this.input, 30, FOLLOW_AND_in_conditional499);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_conditional_in_conditional501);
                  this.conditional();
                  --this.state._fsp;
                  this.pushFollow(FOLLOW_conditional_in_conditional503);
                  this.conditional();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.emit(AND13, (short)38);
                  break;
               case 3:
                  BANG14 = (CommonTree)this.match(this.input, 10, FOLLOW_BANG_in_conditional513);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_conditional_in_conditional515);
                  this.conditional();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.emit(BANG14, (short)36);
                  break;
               case 4:
                  this.pushFollow(FOLLOW_expr_in_conditional527);
                  this.expr();
                  --this.state._fsp;
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final exprOptions_return exprOptions() throws RecognitionException {
      exprOptions_return retval = new exprOptions_return();
      retval.start = this.input.LT(1);

      try {
         try {
            this.emit((CommonTree)retval.start, (short)20);
            this.match(this.input, 51, FOLLOW_OPTIONS_in_exprOptions541);
            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);

               while(true) {
                  int alt12 = 2;
                  int LA12_0 = this.input.LA(1);
                  if (LA12_0 == 12) {
                     alt12 = 1;
                  }

                  switch (alt12) {
                     case 1:
                        this.pushFollow(FOLLOW_option_in_exprOptions543);
                        this.option();
                        --this.state._fsp;
                        break;
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        return retval;
                  }
               }
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final void option() throws RecognitionException {
      CommonTree ID15 = null;

      try {
         try {
            this.match(this.input, 12, FOLLOW_EQUALS_in_option555);
            this.match(this.input, 2, (BitSet)null);
            ID15 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_option557);
            this.pushFollow(FOLLOW_expr_in_option559);
            this.expr();
            --this.state._fsp;
            this.match(this.input, 3, (BitSet)null);
            this.setOption(ID15);
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

      } finally {
         ;
      }
   }

   public final void expr() throws RecognitionException {
      CommonTree ZIP16 = null;
      CommonTree MAP17 = null;
      int nt = 0;
      int ne = 0;

      try {
         try {
            int alt15 = true;
            byte alt15;
            switch (this.input.LA(1)) {
               case 25:
               case 26:
               case 35:
               case 36:
               case 40:
               case 42:
               case 43:
               case 44:
               case 45:
               case 46:
               case 48:
               case 55:
               case 56:
                  alt15 = 4;
                  break;
               case 27:
               case 28:
               case 29:
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 37:
               case 38:
               case 39:
               case 41:
               case 47:
               case 50:
               case 51:
               case 54:
               default:
                  NoViableAltException nvae = new NoViableAltException("", 15, 0, this.input);
                  throw nvae;
               case 49:
                  alt15 = 2;
                  break;
               case 52:
               case 53:
                  alt15 = 3;
                  break;
               case 57:
                  alt15 = 1;
            }

            int cnt14;
            byte alt14;
            int LA14_0;
            EarlyExitException eee;
            switch (alt15) {
               case 1:
                  ZIP16 = (CommonTree)this.match(this.input, 57, FOLLOW_ZIP_in_expr578);
                  this.match(this.input, 2, (BitSet)null);
                  this.match(this.input, 39, FOLLOW_ELEMENTS_in_expr581);
                  this.match(this.input, 2, (BitSet)null);
                  cnt14 = 0;

                  while(true) {
                     alt14 = 2;
                     LA14_0 = this.input.LA(1);
                     if (LA14_0 >= 25 && LA14_0 <= 26 || LA14_0 >= 35 && LA14_0 <= 36 || LA14_0 == 40 || LA14_0 >= 42 && LA14_0 <= 46 || LA14_0 >= 48 && LA14_0 <= 49 || LA14_0 >= 52 && LA14_0 <= 53 || LA14_0 >= 55 && LA14_0 <= 57) {
                        alt14 = 1;
                     }

                     switch (alt14) {
                        case 1:
                           this.pushFollow(FOLLOW_expr_in_expr584);
                           this.expr();
                           --this.state._fsp;
                           ++ne;
                           ++cnt14;
                           break;
                        default:
                           if (cnt14 < 1) {
                              eee = new EarlyExitException(13, this.input);
                              throw eee;
                           }

                           this.match(this.input, 3, (BitSet)null);
                           this.pushFollow(FOLLOW_mapTemplateRef_in_expr591);
                           this.mapTemplateRef(ne);
                           --this.state._fsp;
                           this.match(this.input, 3, (BitSet)null);
                           this.emit1(ZIP16, (short)17, ne);
                           return;
                     }
                  }
               case 2:
                  MAP17 = (CommonTree)this.match(this.input, 49, FOLLOW_MAP_in_expr603);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_expr_in_expr605);
                  this.expr();
                  --this.state._fsp;
                  cnt14 = 0;

                  while(true) {
                     alt14 = 2;
                     LA14_0 = this.input.LA(1);
                     if (LA14_0 >= 42 && LA14_0 <= 43 || LA14_0 == 55) {
                        alt14 = 1;
                     }

                     switch (alt14) {
                        case 1:
                           this.pushFollow(FOLLOW_mapTemplateRef_in_expr608);
                           this.mapTemplateRef(1);
                           --this.state._fsp;
                           ++nt;
                           ++cnt14;
                           break;
                        default:
                           if (cnt14 < 1) {
                              eee = new EarlyExitException(14, this.input);
                              throw eee;
                           }

                           this.match(this.input, 3, (BitSet)null);
                           if (nt > 1) {
                              this.emit1(MAP17, (short)(nt > 1 ? 16 : 15), nt);
                           } else {
                              this.emit(MAP17, (short)15);
                           }

                           return;
                     }
                  }
               case 3:
                  this.pushFollow(FOLLOW_prop_in_expr623);
                  this.prop();
                  --this.state._fsp;
                  break;
               case 4:
                  this.pushFollow(FOLLOW_includeExpr_in_expr628);
                  this.includeExpr();
                  --this.state._fsp;
            }
         } catch (RecognitionException var13) {
            this.reportError(var13);
            this.recover(this.input, var13);
         }

      } finally {
         ;
      }
   }

   public final void prop() throws RecognitionException {
      CommonTree PROP18 = null;
      CommonTree ID19 = null;
      CommonTree PROP_IND20 = null;

      try {
         try {
            int alt16 = true;
            int LA16_0 = this.input.LA(1);
            byte alt16;
            if (LA16_0 == 52) {
               alt16 = 1;
            } else {
               if (LA16_0 != 53) {
                  NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
                  throw nvae;
               }

               alt16 = 2;
            }

            switch (alt16) {
               case 1:
                  PROP18 = (CommonTree)this.match(this.input, 52, FOLLOW_PROP_in_prop638);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_expr_in_prop640);
                  this.expr();
                  --this.state._fsp;
                  ID19 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_prop642);
                  this.match(this.input, 3, (BitSet)null);
                  this.emit1(PROP18, (short)4, ID19 != null ? ID19.getText() : null);
                  break;
               case 2:
                  PROP_IND20 = (CommonTree)this.match(this.input, 53, FOLLOW_PROP_IND_in_prop656);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_expr_in_prop658);
                  this.expr();
                  --this.state._fsp;
                  this.pushFollow(FOLLOW_expr_in_prop660);
                  this.expr();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.emit(PROP_IND20, (short)5);
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

      } finally {
         ;
      }
   }

   public final mapTemplateRef_return mapTemplateRef(int num_exprs) throws RecognitionException {
      mapTemplateRef_return retval = new mapTemplateRef_return();
      retval.start = this.input.LT(1);
      CommonTree INCLUDE21 = null;
      CommonTree ID23 = null;
      CommonTree INCLUDE_IND25 = null;
      TreeRuleReturnScope args22 = null;
      TreeRuleReturnScope subtemplate24 = null;
      TreeRuleReturnScope args26 = null;

      try {
         try {
            int alt17 = true;
            byte alt17;
            switch (this.input.LA(1)) {
               case 42:
                  alt17 = 1;
                  break;
               case 43:
                  alt17 = 3;
                  break;
               case 55:
                  alt17 = 2;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 17, 0, this.input);
                  throw nvae;
            }

            int i;
            switch (alt17) {
               case 1:
                  INCLUDE21 = (CommonTree)this.match(this.input, 42, FOLLOW_INCLUDE_in_mapTemplateRef680);
                  this.match(this.input, 2, (BitSet)null);
                  ID23 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_mapTemplateRef682);

                  for(i = 1; i <= num_exprs; ++i) {
                     this.emit(INCLUDE21, (short)44);
                  }

                  this.pushFollow(FOLLOW_args_in_mapTemplateRef692);
                  args22 = this.args();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  if (args22 != null && ((args_return)args22).passThru) {
                     this.emit1((CommonTree)retval.start, (short)22, ID23 != null ? ID23.getText() : null);
                  }

                  if (args22 != null && ((args_return)args22).namedArgs) {
                     this.emit1(INCLUDE21, (short)10, ID23 != null ? ID23.getText() : null);
                  } else {
                     this.emit2(INCLUDE21, (short)8, ID23 != null ? ID23.getText() : null, (args22 != null ? ((args_return)args22).n : 0) + num_exprs);
                  }
                  break;
               case 2:
                  this.pushFollow(FOLLOW_subtemplate_in_mapTemplateRef705);
                  subtemplate24 = this.subtemplate();
                  --this.state._fsp;
                  if ((subtemplate24 != null ? ((subtemplate_return)subtemplate24).nargs : 0) != num_exprs) {
                     this.errMgr.compileTimeError(ErrorType.ANON_ARGUMENT_MISMATCH, this.templateToken, (subtemplate24 != null ? (CommonTree)subtemplate24.start : null).token, subtemplate24 != null ? ((subtemplate_return)subtemplate24).nargs : 0, num_exprs);
                  }

                  for(i = 1; i <= num_exprs; ++i) {
                     this.emit(subtemplate24 != null ? (CommonTree)subtemplate24.start : null, (short)44);
                  }

                  this.emit2(subtemplate24 != null ? (CommonTree)subtemplate24.start : null, (short)8, subtemplate24 != null ? ((subtemplate_return)subtemplate24).name : null, num_exprs);
                  break;
               case 3:
                  INCLUDE_IND25 = (CommonTree)this.match(this.input, 43, FOLLOW_INCLUDE_IND_in_mapTemplateRef717);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_expr_in_mapTemplateRef719);
                  this.expr();
                  --this.state._fsp;
                  this.emit(INCLUDE_IND25, (short)26);

                  for(i = 1; i <= num_exprs; ++i) {
                     this.emit(INCLUDE_IND25, (short)44);
                  }

                  this.pushFollow(FOLLOW_args_in_mapTemplateRef729);
                  args26 = this.args();
                  --this.state._fsp;
                  this.emit1(INCLUDE_IND25, (short)9, (args26 != null ? ((args_return)args26).n : 0) + num_exprs);
                  this.match(this.input, 3, (BitSet)null);
            }
         } catch (RecognitionException var14) {
            this.reportError(var14);
            this.recover(this.input, var14);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final includeExpr_return includeExpr() throws RecognitionException {
      includeExpr_return retval = new includeExpr_return();
      retval.start = this.input.LT(1);
      CommonTree ID27 = null;
      CommonTree ID29 = null;
      CommonTree INCLUDE30 = null;
      CommonTree ID32 = null;
      CommonTree INCLUDE_SUPER33 = null;
      CommonTree ID34 = null;
      CommonTree INCLUDE_REGION35 = null;
      CommonTree ID36 = null;
      CommonTree INCLUDE_SUPER_REGION37 = null;
      TreeRuleReturnScope args28 = null;
      TreeRuleReturnScope args31 = null;

      try {
         try {
            int alt19 = true;
            byte alt19;
            switch (this.input.LA(1)) {
               case 25:
               case 26:
               case 35:
               case 36:
               case 43:
               case 48:
               case 55:
               case 56:
                  alt19 = 6;
                  break;
               case 27:
               case 28:
               case 29:
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 37:
               case 38:
               case 39:
               case 41:
               case 47:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               default:
                  NoViableAltException nvae = new NoViableAltException("", 19, 0, this.input);
                  throw nvae;
               case 40:
                  alt19 = 1;
                  break;
               case 42:
                  alt19 = 2;
                  break;
               case 44:
                  alt19 = 4;
                  break;
               case 45:
                  alt19 = 3;
                  break;
               case 46:
                  alt19 = 5;
            }

            switch (alt19) {
               case 1:
                  this.match(this.input, 40, FOLLOW_EXEC_FUNC_in_includeExpr751);
                  this.match(this.input, 2, (BitSet)null);
                  ID27 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_includeExpr753);
                  int alt18 = 2;
                  int LA18_0 = this.input.LA(1);
                  if (LA18_0 >= 25 && LA18_0 <= 26 || LA18_0 >= 35 && LA18_0 <= 36 || LA18_0 == 40 || LA18_0 >= 42 && LA18_0 <= 46 || LA18_0 >= 48 && LA18_0 <= 49 || LA18_0 >= 52 && LA18_0 <= 53 || LA18_0 >= 55 && LA18_0 <= 57) {
                     alt18 = 1;
                  }

                  switch (alt18) {
                     case 1:
                        this.pushFollow(FOLLOW_expr_in_includeExpr755);
                        this.expr();
                        --this.state._fsp;
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        this.func(ID27);
                        return retval;
                  }
               case 2:
                  INCLUDE30 = (CommonTree)this.match(this.input, 42, FOLLOW_INCLUDE_in_includeExpr766);
                  this.match(this.input, 2, (BitSet)null);
                  ID29 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_includeExpr768);
                  this.pushFollow(FOLLOW_args_in_includeExpr770);
                  args28 = this.args();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  if (args28 != null && ((args_return)args28).passThru) {
                     this.emit1((CommonTree)retval.start, (short)22, ID29 != null ? ID29.getText() : null);
                  }

                  if (args28 != null && ((args_return)args28).namedArgs) {
                     this.emit1(INCLUDE30, (short)10, ID29 != null ? ID29.getText() : null);
                  } else {
                     this.emit2(INCLUDE30, (short)8, ID29 != null ? ID29.getText() : null, args28 != null ? ((args_return)args28).n : 0);
                  }
                  break;
               case 3:
                  INCLUDE_SUPER33 = (CommonTree)this.match(this.input, 45, FOLLOW_INCLUDE_SUPER_in_includeExpr781);
                  this.match(this.input, 2, (BitSet)null);
                  ID32 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_includeExpr783);
                  this.pushFollow(FOLLOW_args_in_includeExpr785);
                  args31 = this.args();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  if (args31 != null && ((args_return)args31).passThru) {
                     this.emit1((CommonTree)retval.start, (short)22, ID32 != null ? ID32.getText() : null);
                  }

                  if (args31 != null && ((args_return)args31).namedArgs) {
                     this.emit1(INCLUDE_SUPER33, (short)12, ID32 != null ? ID32.getText() : null);
                  } else {
                     this.emit2(INCLUDE_SUPER33, (short)11, ID32 != null ? ID32.getText() : null, args31 != null ? ((args_return)args31).n : 0);
                  }
                  break;
               case 4:
                  INCLUDE_REGION35 = (CommonTree)this.match(this.input, 44, FOLLOW_INCLUDE_REGION_in_includeExpr796);
                  this.match(this.input, 2, (BitSet)null);
                  ID34 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_includeExpr798);
                  this.match(this.input, 3, (BitSet)null);
                  CompiledST impl = Compiler.defineBlankRegion(this.outermostImpl, ID34.token);
                  this.emit2(INCLUDE_REGION35, (short)8, impl.name, 0);
                  break;
               case 5:
                  INCLUDE_SUPER_REGION37 = (CommonTree)this.match(this.input, 46, FOLLOW_INCLUDE_SUPER_REGION_in_includeExpr808);
                  this.match(this.input, 2, (BitSet)null);
                  ID36 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_includeExpr810);
                  this.match(this.input, 3, (BitSet)null);
                  String mangled = STGroup.getMangledRegionName(this.outermostImpl.name, ID36 != null ? ID36.getText() : null);
                  this.emit2(INCLUDE_SUPER_REGION37, (short)11, mangled, 0);
                  break;
               case 6:
                  this.pushFollow(FOLLOW_primary_in_includeExpr818);
                  this.primary();
                  --this.state._fsp;
            }
         } catch (RecognitionException var19) {
            this.reportError(var19);
            this.recover(this.input, var19);
         }

         return retval;
      } finally {
         ;
      }
   }

   public final primary_return primary() throws RecognitionException {
      primary_return retval = new primary_return();
      retval.start = this.input.LT(1);
      CommonTree ID38 = null;
      CommonTree STRING39 = null;
      CommonTree TRUE40 = null;
      CommonTree FALSE41 = null;
      CommonTree INCLUDE_IND43 = null;
      CommonTree TO_STR45 = null;
      TreeRuleReturnScope subtemplate42 = null;
      TreeRuleReturnScope args44 = null;

      try {
         try {
            int alt20 = true;
            byte alt20;
            switch (this.input.LA(1)) {
               case 25:
                  alt20 = 1;
                  break;
               case 26:
                  alt20 = 2;
                  break;
               case 35:
                  alt20 = 3;
                  break;
               case 36:
                  alt20 = 4;
                  break;
               case 43:
                  alt20 = 7;
                  break;
               case 48:
                  alt20 = 6;
                  break;
               case 55:
                  alt20 = 5;
                  break;
               case 56:
                  alt20 = 8;
                  break;
               default:
                  NoViableAltException nvae = new NoViableAltException("", 20, 0, this.input);
                  throw nvae;
            }

            switch (alt20) {
               case 1:
                  ID38 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_primary829);
                  this.refAttr(ID38);
                  break;
               case 2:
                  STRING39 = (CommonTree)this.match(this.input, 26, FOLLOW_STRING_in_primary839);
                  this.emit1(STRING39, (short)1, Misc.strip(STRING39 != null ? STRING39.getText() : null, 1));
                  break;
               case 3:
                  TRUE40 = (CommonTree)this.match(this.input, 35, FOLLOW_TRUE_in_primary848);
                  this.emit(TRUE40, (short)45);
                  break;
               case 4:
                  FALSE41 = (CommonTree)this.match(this.input, 36, FOLLOW_FALSE_in_primary857);
                  this.emit(FALSE41, (short)46);
                  break;
               case 5:
                  this.pushFollow(FOLLOW_subtemplate_in_primary866);
                  subtemplate42 = this.subtemplate();
                  --this.state._fsp;
                  this.emit2((CommonTree)retval.start, (short)8, subtemplate42 != null ? ((subtemplate_return)subtemplate42).name : null, 0);
                  break;
               case 6:
                  this.pushFollow(FOLLOW_list_in_primary893);
                  this.list();
                  --this.state._fsp;
                  break;
               case 7:
                  INCLUDE_IND43 = (CommonTree)this.match(this.input, 43, FOLLOW_INCLUDE_IND_in_primary900);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_expr_in_primary905);
                  this.expr();
                  --this.state._fsp;
                  this.emit(INCLUDE_IND43, (short)26);
                  this.pushFollow(FOLLOW_args_in_primary914);
                  args44 = this.args();
                  --this.state._fsp;
                  this.emit1(INCLUDE_IND43, (short)9, args44 != null ? ((args_return)args44).n : 0);
                  this.match(this.input, 3, (BitSet)null);
                  break;
               case 8:
                  TO_STR45 = (CommonTree)this.match(this.input, 56, FOLLOW_TO_STR_in_primary934);
                  this.match(this.input, 2, (BitSet)null);
                  this.pushFollow(FOLLOW_expr_in_primary936);
                  this.expr();
                  --this.state._fsp;
                  this.match(this.input, 3, (BitSet)null);
                  this.emit(TO_STR45, (short)26);
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

   public final void arg() throws RecognitionException {
      try {
         try {
            this.pushFollow(FOLLOW_expr_in_arg949);
            this.expr();
            --this.state._fsp;
         } catch (RecognitionException var5) {
            this.reportError(var5);
            this.recover(this.input, var5);
         }

      } finally {
         ;
      }
   }

   public final args_return args() throws RecognitionException {
      args_return retval = new args_return();
      retval.start = this.input.LT(1);
      CommonTree eq = null;
      CommonTree ID46 = null;

      try {
         try {
            int alt24 = true;
            byte alt24;
            switch (this.input.LA(1)) {
               case 3:
                  alt24 = 4;
                  break;
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 13:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
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
               case 33:
               case 34:
               case 37:
               case 38:
               case 39:
               case 41:
               case 47:
               case 50:
               case 51:
               case 54:
               default:
                  NoViableAltException nvae = new NoViableAltException("", 24, 0, this.input);
                  throw nvae;
               case 11:
                  alt24 = 3;
                  break;
               case 12:
                  alt24 = 2;
                  break;
               case 25:
               case 26:
               case 35:
               case 36:
               case 40:
               case 42:
               case 43:
               case 44:
               case 45:
               case 46:
               case 48:
               case 49:
               case 52:
               case 53:
               case 55:
               case 56:
               case 57:
                  alt24 = 1;
            }

            int cnt22;
            byte alt23;
            int LA23_0;
            EarlyExitException eee;
            switch (alt24) {
               case 1:
                  cnt22 = 0;

                  while(true) {
                     alt23 = 2;
                     LA23_0 = this.input.LA(1);
                     if (LA23_0 >= 25 && LA23_0 <= 26 || LA23_0 >= 35 && LA23_0 <= 36 || LA23_0 == 40 || LA23_0 >= 42 && LA23_0 <= 46 || LA23_0 >= 48 && LA23_0 <= 49 || LA23_0 >= 52 && LA23_0 <= 53 || LA23_0 >= 55 && LA23_0 <= 57) {
                        alt23 = 1;
                     }

                     switch (alt23) {
                        case 1:
                           this.pushFollow(FOLLOW_arg_in_args965);
                           this.arg();
                           --this.state._fsp;
                           ++retval.n;
                           ++cnt22;
                           break;
                        default:
                           if (cnt22 < 1) {
                              eee = new EarlyExitException(21, this.input);
                              throw eee;
                           }

                           return retval;
                     }
                  }
               case 2:
                  this.emit((CommonTree)retval.start, (short)21);
                  retval.namedArgs = true;
                  cnt22 = 0;

                  while(true) {
                     alt23 = 2;
                     LA23_0 = this.input.LA(1);
                     if (LA23_0 == 12) {
                        alt23 = 1;
                     }

                     switch (alt23) {
                        case 1:
                           eq = (CommonTree)this.match(this.input, 12, FOLLOW_EQUALS_in_args984);
                           this.match(this.input, 2, (BitSet)null);
                           ID46 = (CommonTree)this.match(this.input, 25, FOLLOW_ID_in_args986);
                           this.pushFollow(FOLLOW_expr_in_args988);
                           this.expr();
                           --this.state._fsp;
                           this.match(this.input, 3, (BitSet)null);
                           ++retval.n;
                           this.emit1(eq, (short)7, this.defineString(ID46 != null ? ID46.getText() : null));
                           ++cnt22;
                           break;
                        default:
                           if (cnt22 < 1) {
                              eee = new EarlyExitException(22, this.input);
                              throw eee;
                           }

                           alt23 = 2;
                           LA23_0 = this.input.LA(1);
                           if (LA23_0 == 11) {
                              alt23 = 1;
                           }

                           switch (alt23) {
                              case 1:
                                 this.match(this.input, 11, FOLLOW_ELLIPSIS_in_args1005);
                                 retval.passThru = true;
                                 return retval;
                              default:
                                 return retval;
                           }
                     }
                  }
               case 3:
                  this.match(this.input, 11, FOLLOW_ELLIPSIS_in_args1020);
                  retval.passThru = true;
                  this.emit((CommonTree)retval.start, (short)21);
                  retval.namedArgs = true;
               case 4:
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

   public final list_return list() throws RecognitionException {
      list_return retval = new list_return();
      retval.start = this.input.LT(1);
      TreeRuleReturnScope listElement47 = null;

      try {
         try {
            this.emit((CommonTree)retval.start, (short)24);
            this.match(this.input, 48, FOLLOW_LIST_in_list1040);
            if (this.input.LA(1) == 2) {
               this.match(this.input, 2, (BitSet)null);

               while(true) {
                  int alt25 = 2;
                  int LA25_0 = this.input.LA(1);
                  if (LA25_0 >= 25 && LA25_0 <= 26 || LA25_0 >= 35 && LA25_0 <= 36 || LA25_0 == 40 || LA25_0 >= 42 && LA25_0 <= 46 || LA25_0 >= 48 && LA25_0 <= 50 || LA25_0 >= 52 && LA25_0 <= 53 || LA25_0 >= 55 && LA25_0 <= 57) {
                     alt25 = 1;
                  }

                  switch (alt25) {
                     case 1:
                        this.pushFollow(FOLLOW_listElement_in_list1043);
                        listElement47 = this.listElement();
                        --this.state._fsp;
                        this.emit(listElement47 != null ? (CommonTree)listElement47.start : null, (short)25);
                        break;
                     default:
                        this.match(this.input, 3, (BitSet)null);
                        return retval;
                  }
               }
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

   public final listElement_return listElement() throws RecognitionException {
      listElement_return retval = new listElement_return();
      retval.start = this.input.LT(1);
      CommonTree NULL48 = null;

      try {
         try {
            int alt26 = true;
            int LA26_0 = this.input.LA(1);
            byte alt26;
            if (LA26_0 >= 25 && LA26_0 <= 26 || LA26_0 >= 35 && LA26_0 <= 36 || LA26_0 == 40 || LA26_0 >= 42 && LA26_0 <= 46 || LA26_0 >= 48 && LA26_0 <= 49 || LA26_0 >= 52 && LA26_0 <= 53 || LA26_0 >= 55 && LA26_0 <= 57) {
               alt26 = 1;
            } else {
               if (LA26_0 != 50) {
                  NoViableAltException nvae = new NoViableAltException("", 26, 0, this.input);
                  throw nvae;
               }

               alt26 = 2;
            }

            switch (alt26) {
               case 1:
                  this.pushFollow(FOLLOW_expr_in_listElement1059);
                  this.expr();
                  --this.state._fsp;
                  break;
               case 2:
                  NULL48 = (CommonTree)this.match(this.input, 50, FOLLOW_NULL_in_listElement1063);
                  this.emit(NULL48, (short)44);
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

   public static class listElement_return extends TreeRuleReturnScope {
   }

   public static class list_return extends TreeRuleReturnScope {
   }

   public static class args_return extends TreeRuleReturnScope {
      public int n = 0;
      public boolean namedArgs = false;
      public boolean passThru;
   }

   public static class primary_return extends TreeRuleReturnScope {
   }

   public static class includeExpr_return extends TreeRuleReturnScope {
   }

   public static class mapTemplateRef_return extends TreeRuleReturnScope {
   }

   public static class exprOptions_return extends TreeRuleReturnScope {
   }

   public static class conditional_return extends TreeRuleReturnScope {
   }

   public static class subtemplate_return extends TreeRuleReturnScope {
      public String name;
      public int nargs;
   }

   public static class region_return extends TreeRuleReturnScope {
      public String name;
   }

   protected static class template_scope {
      CompilationState state;
   }
}
