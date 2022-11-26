package org.stringtemplate.v4.compiler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.antlr.runtime.BitSet;
import org.antlr.runtime.FailedPredicateException;
import org.antlr.runtime.MismatchedSetException;
import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.NoViableAltException;
import org.antlr.runtime.Parser;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.RecognizerSharedState;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.misc.ErrorType;
import org.stringtemplate.v4.misc.Misc;

public class GroupParser extends Parser {
   public static final String[] tokenNames = new String[]{"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ANONYMOUS_TEMPLATE", "BIGSTRING", "BIGSTRING_NO_NL", "COMMENT", "FALSE", "ID", "LBRACK", "LINE_COMMENT", "RBRACK", "STRING", "TRUE", "WS", "'('", "')'", "','", "'.'", "':'", "'::='", "';'", "'='", "'@'", "'default'", "'delimiters'", "'group'", "'implements'", "'import'"};
   public static final int EOF = -1;
   public static final int T__16 = 16;
   public static final int T__17 = 17;
   public static final int T__18 = 18;
   public static final int T__19 = 19;
   public static final int T__20 = 20;
   public static final int T__21 = 21;
   public static final int T__22 = 22;
   public static final int T__23 = 23;
   public static final int T__24 = 24;
   public static final int T__25 = 25;
   public static final int T__26 = 26;
   public static final int T__27 = 27;
   public static final int T__28 = 28;
   public static final int T__29 = 29;
   public static final int ANONYMOUS_TEMPLATE = 4;
   public static final int BIGSTRING = 5;
   public static final int BIGSTRING_NO_NL = 6;
   public static final int COMMENT = 7;
   public static final int FALSE = 8;
   public static final int ID = 9;
   public static final int LBRACK = 10;
   public static final int LINE_COMMENT = 11;
   public static final int RBRACK = 12;
   public static final int STRING = 13;
   public static final int TRUE = 14;
   public static final int WS = 15;
   public STGroup group;
   protected Stack formalArgs_stack;
   public static final BitSet FOLLOW_oldStyleHeader_in_group86 = new BitSet(new long[]{620757504L});
   public static final BitSet FOLLOW_delimiters_in_group91 = new BitSet(new long[]{553648640L});
   public static final BitSet FOLLOW_29_in_group101 = new BitSet(new long[]{8192L});
   public static final BitSet FOLLOW_STRING_in_group103 = new BitSet(new long[]{553648640L});
   public static final BitSet FOLLOW_29_in_group111 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_ID_in_group122 = new BitSet(new long[]{554172928L});
   public static final BitSet FOLLOW_19_in_group125 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_ID_in_group127 = new BitSet(new long[]{554172928L});
   public static final BitSet FOLLOW_def_in_group139 = new BitSet(new long[]{16777728L});
   public static final BitSet FOLLOW_EOF_in_group145 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_27_in_oldStyleHeader162 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_ID_in_oldStyleHeader164 = new BitSet(new long[]{273678336L});
   public static final BitSet FOLLOW_20_in_oldStyleHeader168 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_ID_in_oldStyleHeader170 = new BitSet(new long[]{272629760L});
   public static final BitSet FOLLOW_28_in_oldStyleHeader182 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_ID_in_oldStyleHeader184 = new BitSet(new long[]{4456448L});
   public static final BitSet FOLLOW_18_in_oldStyleHeader187 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_ID_in_oldStyleHeader189 = new BitSet(new long[]{4456448L});
   public static final BitSet FOLLOW_22_in_oldStyleHeader201 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ID_in_groupName223 = new BitSet(new long[]{524290L});
   public static final BitSet FOLLOW_19_in_groupName228 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_ID_in_groupName232 = new BitSet(new long[]{524290L});
   public static final BitSet FOLLOW_26_in_delimiters250 = new BitSet(new long[]{8192L});
   public static final BitSet FOLLOW_STRING_in_delimiters254 = new BitSet(new long[]{262144L});
   public static final BitSet FOLLOW_18_in_delimiters256 = new BitSet(new long[]{8192L});
   public static final BitSet FOLLOW_STRING_in_delimiters260 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_templateDef_in_def284 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_dictDef_in_def289 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_24_in_templateDef313 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_ID_in_templateDef317 = new BitSet(new long[]{524288L});
   public static final BitSet FOLLOW_19_in_templateDef319 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_ID_in_templateDef323 = new BitSet(new long[]{65536L});
   public static final BitSet FOLLOW_16_in_templateDef325 = new BitSet(new long[]{131072L});
   public static final BitSet FOLLOW_17_in_templateDef327 = new BitSet(new long[]{2097152L});
   public static final BitSet FOLLOW_ID_in_templateDef335 = new BitSet(new long[]{65536L});
   public static final BitSet FOLLOW_16_in_templateDef337 = new BitSet(new long[]{131584L});
   public static final BitSet FOLLOW_formalArgs_in_templateDef339 = new BitSet(new long[]{131072L});
   public static final BitSet FOLLOW_17_in_templateDef341 = new BitSet(new long[]{2097152L});
   public static final BitSet FOLLOW_21_in_templateDef352 = new BitSet(new long[]{8290L});
   public static final BitSet FOLLOW_STRING_in_templateDef368 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BIGSTRING_in_templateDef383 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BIGSTRING_NO_NL_in_templateDef395 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ID_in_templateDef430 = new BitSet(new long[]{2097152L});
   public static final BitSet FOLLOW_21_in_templateDef432 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_ID_in_templateDef436 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_formalArg_in_formalArgs462 = new BitSet(new long[]{262146L});
   public static final BitSet FOLLOW_18_in_formalArgs466 = new BitSet(new long[]{512L});
   public static final BitSet FOLLOW_formalArg_in_formalArgs468 = new BitSet(new long[]{262146L});
   public static final BitSet FOLLOW_ID_in_formalArg486 = new BitSet(new long[]{8388610L});
   public static final BitSet FOLLOW_23_in_formalArg492 = new BitSet(new long[]{24848L});
   public static final BitSet FOLLOW_set_in_formalArg496 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_23_in_formalArg512 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_LBRACK_in_formalArg516 = new BitSet(new long[]{4096L});
   public static final BitSet FOLLOW_RBRACK_in_formalArg518 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ID_in_dictDef551 = new BitSet(new long[]{2097152L});
   public static final BitSet FOLLOW_21_in_dictDef553 = new BitSet(new long[]{1024L});
   public static final BitSet FOLLOW_dict_in_dictDef555 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LBRACK_in_dict587 = new BitSet(new long[]{33562624L});
   public static final BitSet FOLLOW_dictPairs_in_dict589 = new BitSet(new long[]{4096L});
   public static final BitSet FOLLOW_RBRACK_in_dict592 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_keyValuePair_in_dictPairs607 = new BitSet(new long[]{262146L});
   public static final BitSet FOLLOW_18_in_dictPairs616 = new BitSet(new long[]{8192L});
   public static final BitSet FOLLOW_keyValuePair_in_dictPairs618 = new BitSet(new long[]{262146L});
   public static final BitSet FOLLOW_18_in_dictPairs624 = new BitSet(new long[]{33554432L});
   public static final BitSet FOLLOW_defaultValuePair_in_dictPairs626 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_defaultValuePair_in_dictPairs637 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_25_in_defaultValuePair660 = new BitSet(new long[]{1048576L});
   public static final BitSet FOLLOW_20_in_defaultValuePair662 = new BitSet(new long[]{26480L});
   public static final BitSet FOLLOW_keyValue_in_defaultValuePair664 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_in_keyValuePair678 = new BitSet(new long[]{1048576L});
   public static final BitSet FOLLOW_20_in_keyValuePair680 = new BitSet(new long[]{26480L});
   public static final BitSet FOLLOW_keyValue_in_keyValuePair682 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BIGSTRING_in_keyValue699 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_BIGSTRING_NO_NL_in_keyValue708 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ANONYMOUS_TEMPLATE_in_keyValue716 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_STRING_in_keyValue723 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_TRUE_in_keyValue733 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_FALSE_in_keyValue743 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_LBRACK_in_keyValue753 = new BitSet(new long[]{4096L});
   public static final BitSet FOLLOW_RBRACK_in_keyValue755 = new BitSet(new long[]{2L});
   public static final BitSet FOLLOW_ID_in_keyValue768 = new BitSet(new long[]{2L});

   public Parser[] getDelegates() {
      return new Parser[0];
   }

   public GroupParser(TokenStream input) {
      this(input, new RecognizerSharedState());
   }

   public GroupParser(TokenStream input, RecognizerSharedState state) {
      super(input, state);
      this.formalArgs_stack = new Stack();
   }

   public String[] getTokenNames() {
      return tokenNames;
   }

   public String getGrammarFileName() {
      return "org\\stringtemplate\\v4\\compiler\\Group.g";
   }

   public void displayRecognitionError(String[] tokenNames, RecognitionException e) {
      String msg = this.getErrorMessage(e, tokenNames);
      this.group.errMgr.groupSyntaxError(ErrorType.SYNTAX_ERROR, this.getSourceName(), e, msg);
   }

   public String getSourceName() {
      String fullFileName = super.getSourceName();
      File f = new File(fullFileName);
      return f.getName();
   }

   public void error(String msg) {
      NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
      this.group.errMgr.groupSyntaxError(ErrorType.SYNTAX_ERROR, this.getSourceName(), e, msg);
      this.recover(this.input, (RecognitionException)null);
   }

   public final void group(STGroup group, String prefix) throws RecognitionException {
      Token STRING1 = null;
      GroupLexer lexer = (GroupLexer)this.input.getTokenSource();
      this.group = lexer.group = group;

      try {
         int alt1 = 2;
         int LA1_0 = this.input.LA(1);
         if (LA1_0 == 27) {
            alt1 = 1;
         }

         switch (alt1) {
            case 1:
               this.pushFollow(FOLLOW_oldStyleHeader_in_group86);
               this.oldStyleHeader();
               --this.state._fsp;
            default:
               int alt2 = 2;
               int LA2_0 = this.input.LA(1);
               if (LA2_0 == 26) {
                  alt2 = 1;
               }

               switch (alt2) {
                  case 1:
                     this.pushFollow(FOLLOW_delimiters_in_group91);
                     this.delimiters();
                     --this.state._fsp;
               }
         }

         while(true) {
            label150:
            while(true) {
               int alt5 = 3;
               int LA5_0 = this.input.LA(1);
               if (LA5_0 == 29) {
                  int LA4_2 = this.input.LA(2);
                  if (LA4_2 == 13) {
                     alt5 = 1;
                  } else if (LA4_2 == 9) {
                     alt5 = 2;
                  }
               }

               switch (alt5) {
                  case 1:
                     this.match(this.input, 29, FOLLOW_29_in_group101);
                     STRING1 = (Token)this.match(this.input, 13, FOLLOW_STRING_in_group103);
                     group.importTemplates(STRING1);
                     break;
                  case 2:
                     this.match(this.input, 29, FOLLOW_29_in_group111);
                     MismatchedTokenException e = new MismatchedTokenException(13, this.input);
                     this.reportError(e);
                     this.match(this.input, 9, FOLLOW_ID_in_group122);

                     while(true) {
                        int alt3 = 2;
                        int LA3_0 = this.input.LA(1);
                        if (LA3_0 == 19) {
                           alt3 = 1;
                        }

                        switch (alt3) {
                           case 1:
                              this.match(this.input, 19, FOLLOW_19_in_group125);
                              this.match(this.input, 9, FOLLOW_ID_in_group127);
                              break;
                           default:
                              continue label150;
                        }
                     }
                  default:
                     while(true) {
                        alt5 = 2;
                        LA5_0 = this.input.LA(1);
                        if (LA5_0 == 9 || LA5_0 == 24) {
                           alt5 = 1;
                        }

                        switch (alt5) {
                           case 1:
                              this.pushFollow(FOLLOW_def_in_group139);
                              this.def(prefix);
                              --this.state._fsp;
                              break;
                           default:
                              this.match(this.input, -1, FOLLOW_EOF_in_group145);
                              return;
                        }
                     }
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

   public final void oldStyleHeader() throws RecognitionException {
      try {
         try {
            this.match(this.input, 27, FOLLOW_27_in_oldStyleHeader162);
            this.match(this.input, 9, FOLLOW_ID_in_oldStyleHeader164);
            int alt6 = 2;
            int LA6_0 = this.input.LA(1);
            if (LA6_0 == 20) {
               alt6 = 1;
            }

            switch (alt6) {
               case 1:
                  this.match(this.input, 20, FOLLOW_20_in_oldStyleHeader168);
                  this.match(this.input, 9, FOLLOW_ID_in_oldStyleHeader170);
               default:
                  int alt8 = 2;
                  int LA8_0 = this.input.LA(1);
                  if (LA8_0 == 28) {
                     alt8 = 1;
                  }

                  switch (alt8) {
                     case 1:
                        this.match(this.input, 28, FOLLOW_28_in_oldStyleHeader182);
                        this.match(this.input, 9, FOLLOW_ID_in_oldStyleHeader184);

                        label77:
                        while(true) {
                           int alt7 = 2;
                           int LA7_0 = this.input.LA(1);
                           if (LA7_0 == 18) {
                              alt7 = 1;
                           }

                           switch (alt7) {
                              case 1:
                                 this.match(this.input, 18, FOLLOW_18_in_oldStyleHeader187);
                                 this.match(this.input, 9, FOLLOW_ID_in_oldStyleHeader189);
                                 break;
                              default:
                                 break label77;
                           }
                        }
                  }

                  this.match(this.input, 22, FOLLOW_22_in_oldStyleHeader201);
            }
         } catch (RecognitionException var10) {
            this.reportError(var10);
            this.recover(this.input, var10);
         }

      } finally {
         ;
      }
   }

   public final String groupName() throws RecognitionException {
      String name = null;
      Token a = null;
      StringBuilder buf = new StringBuilder();

      try {
         a = (Token)this.match(this.input, 9, FOLLOW_ID_in_groupName223);
         buf.append(a != null ? a.getText() : null);

         while(true) {
            int alt9 = 2;
            int LA9_0 = this.input.LA(1);
            if (LA9_0 == 19) {
               alt9 = 1;
            }

            switch (alt9) {
               case 1:
                  this.match(this.input, 19, FOLLOW_19_in_groupName228);
                  a = (Token)this.match(this.input, 9, FOLLOW_ID_in_groupName232);
                  buf.append(a != null ? a.getText() : null);
                  break;
               default:
                  return (String)name;
            }
         }
      } catch (RecognitionException var9) {
         this.reportError(var9);
         this.recover(this.input, var9);
         return (String)name;
      } finally {
         ;
      }
   }

   public final void delimiters() throws RecognitionException {
      Token a = null;
      Token b = null;

      try {
         try {
            this.match(this.input, 26, FOLLOW_26_in_delimiters250);
            a = (Token)this.match(this.input, 13, FOLLOW_STRING_in_delimiters254);
            this.match(this.input, 18, FOLLOW_18_in_delimiters256);
            b = (Token)this.match(this.input, 13, FOLLOW_STRING_in_delimiters260);
            this.group.delimiterStartChar = a.getText().charAt(1);
            this.group.delimiterStopChar = b.getText().charAt(1);
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void def(String prefix) throws RecognitionException {
      try {
         try {
            int alt10 = true;
            int LA10_0 = this.input.LA(1);
            byte alt10;
            if (LA10_0 == 24) {
               alt10 = 1;
            } else {
               if (LA10_0 != 9) {
                  NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
                  throw nvae;
               }

               int LA10_2 = this.input.LA(2);
               if (LA10_2 == 16) {
                  alt10 = 1;
               } else {
                  int LA10_3;
                  if (LA10_2 != 21) {
                     LA10_3 = this.input.mark();

                     try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 10, 2, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(LA10_3);
                     }
                  }

                  LA10_3 = this.input.LA(3);
                  if (LA10_3 == 9) {
                     alt10 = 1;
                  } else {
                     if (LA10_3 != 10) {
                        int nvaeMark = this.input.mark();

                        try {
                           for(int nvaeConsume = 0; nvaeConsume < 2; ++nvaeConsume) {
                              this.input.consume();
                           }

                           NoViableAltException nvae = new NoViableAltException("", 10, 3, this.input);
                           throw nvae;
                        } finally {
                           this.input.rewind(nvaeMark);
                        }
                     }

                     alt10 = 2;
                  }
               }
            }

            switch (alt10) {
               case 1:
                  this.pushFollow(FOLLOW_templateDef_in_def284);
                  this.templateDef(prefix);
                  --this.state._fsp;
                  break;
               case 2:
                  this.pushFollow(FOLLOW_dictDef_in_def289);
                  this.dictDef();
                  --this.state._fsp;
            }
         } catch (RecognitionException var25) {
            this.state.lastErrorIndex = this.input.index();
            this.error("garbled template definition starting at '" + this.input.LT(1).getText() + "'");
         }

      } finally {
         ;
      }
   }

   public final void templateDef(String prefix) throws RecognitionException {
      Token enclosing = null;
      Token name = null;
      Token alias = null;
      Token target = null;
      Token STRING2 = null;
      Token BIGSTRING3 = null;
      Token BIGSTRING_NO_NL4 = null;
      List formalArgs5 = null;
      String template = null;
      int n = 0;

      try {
         try {
            int alt13 = true;
            int LA13_0 = this.input.LA(1);
            int nvaeMark;
            byte alt13;
            NoViableAltException nvae;
            if (LA13_0 == 24) {
               alt13 = 1;
            } else {
               if (LA13_0 != 9) {
                  NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);
                  throw nvae;
               }

               int LA13_2 = this.input.LA(2);
               if (LA13_2 == 16) {
                  alt13 = 1;
               } else {
                  if (LA13_2 != 21) {
                     nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        nvae = new NoViableAltException("", 13, 2, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt13 = 2;
               }
            }

            switch (alt13) {
               case 1:
                  int alt11 = true;
                  nvaeMark = this.input.LA(1);
                  byte alt11;
                  if (nvaeMark == 24) {
                     alt11 = 1;
                  } else {
                     if (nvaeMark != 9) {
                        nvae = new NoViableAltException("", 11, 0, this.input);
                        throw nvae;
                     }

                     alt11 = 2;
                  }

                  switch (alt11) {
                     case 1:
                        this.match(this.input, 24, FOLLOW_24_in_templateDef313);
                        enclosing = (Token)this.match(this.input, 9, FOLLOW_ID_in_templateDef317);
                        this.match(this.input, 19, FOLLOW_19_in_templateDef319);
                        name = (Token)this.match(this.input, 9, FOLLOW_ID_in_templateDef323);
                        this.match(this.input, 16, FOLLOW_16_in_templateDef325);
                        this.match(this.input, 17, FOLLOW_17_in_templateDef327);
                        break;
                     case 2:
                        name = (Token)this.match(this.input, 9, FOLLOW_ID_in_templateDef335);
                        this.match(this.input, 16, FOLLOW_16_in_templateDef337);
                        this.pushFollow(FOLLOW_formalArgs_in_templateDef339);
                        formalArgs5 = this.formalArgs();
                        --this.state._fsp;
                        this.match(this.input, 17, FOLLOW_17_in_templateDef341);
                  }

                  this.match(this.input, 21, FOLLOW_21_in_templateDef352);
                  Token templateToken = this.input.LT(1);
                  int alt12 = true;
                  byte alt12;
                  switch (this.input.LA(1)) {
                     case -1:
                     case 9:
                     case 24:
                        alt12 = 4;
                        break;
                     case 5:
                        alt12 = 2;
                        break;
                     case 6:
                        alt12 = 3;
                        break;
                     case 13:
                        alt12 = 1;
                        break;
                     default:
                        NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
                        throw nvae;
                  }

                  String templateName;
                  switch (alt12) {
                     case 1:
                        STRING2 = (Token)this.match(this.input, 13, FOLLOW_STRING_in_templateDef368);
                        template = STRING2 != null ? STRING2.getText() : null;
                        n = 1;
                        break;
                     case 2:
                        BIGSTRING3 = (Token)this.match(this.input, 5, FOLLOW_BIGSTRING_in_templateDef383);
                        template = BIGSTRING3 != null ? BIGSTRING3.getText() : null;
                        n = 2;
                        break;
                     case 3:
                        BIGSTRING_NO_NL4 = (Token)this.match(this.input, 6, FOLLOW_BIGSTRING_NO_NL_in_templateDef395);
                        template = BIGSTRING_NO_NL4 != null ? BIGSTRING_NO_NL4.getText() : null;
                        n = 2;
                        break;
                     case 4:
                        template = "";
                        templateName = "missing template at '" + this.input.LT(1).getText() + "'";
                        NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
                        this.group.errMgr.groupSyntaxError(ErrorType.SYNTAX_ERROR, this.getSourceName(), e, templateName);
                  }

                  if ((name != null ? name.getTokenIndex() : 0) >= 0) {
                     template = Misc.strip(template, n);
                     templateName = name != null ? name.getText() : null;
                     if (prefix.length() > 0) {
                        templateName = prefix + (name != null ? name.getText() : null);
                     }

                     String enclosingTemplateName = enclosing != null ? enclosing.getText() : null;
                     if (enclosingTemplateName != null && enclosingTemplateName.length() > 0 && prefix.length() > 0) {
                        enclosingTemplateName = prefix + enclosingTemplateName;
                     }

                     this.group.defineTemplateOrRegion(templateName, enclosingTemplateName, templateToken, template, name, formalArgs5);
                  }
                  break;
               case 2:
                  alias = (Token)this.match(this.input, 9, FOLLOW_ID_in_templateDef430);
                  this.match(this.input, 21, FOLLOW_21_in_templateDef432);
                  target = (Token)this.match(this.input, 9, FOLLOW_ID_in_templateDef436);
                  this.group.defineTemplateAlias(alias, target);
            }
         } catch (RecognitionException var28) {
            this.reportError(var28);
            this.recover(this.input, var28);
         }

      } finally {
         ;
      }
   }

   public final List formalArgs() throws RecognitionException {
      this.formalArgs_stack.push(new formalArgs_scope());
      List args = new ArrayList();
      ((formalArgs_scope)this.formalArgs_stack.peek()).hasOptionalParameter = false;

      try {
         int alt15 = true;
         int LA15_0 = this.input.LA(1);
         byte alt15;
         if (LA15_0 == 9) {
            alt15 = 1;
         } else {
            if (LA15_0 != 17) {
               NoViableAltException nvae = new NoViableAltException("", 15, 0, this.input);
               throw nvae;
            }

            alt15 = 2;
         }

         switch (alt15) {
            case 1:
               this.pushFollow(FOLLOW_formalArg_in_formalArgs462);
               this.formalArg(args);
               --this.state._fsp;

               while(true) {
                  int alt14 = 2;
                  int LA14_0 = this.input.LA(1);
                  if (LA14_0 == 18) {
                     alt14 = 1;
                  }

                  switch (alt14) {
                     case 1:
                        this.match(this.input, 18, FOLLOW_18_in_formalArgs466);
                        this.pushFollow(FOLLOW_formalArg_in_formalArgs468);
                        this.formalArg(args);
                        --this.state._fsp;
                        break;
                     default:
                        return args;
                  }
               }
            case 2:
         }
      } catch (RecognitionException var9) {
         this.reportError(var9);
         this.recover(this.input, var9);
      } finally {
         this.formalArgs_stack.pop();
      }

      return args;
   }

   public final void formalArg(List args) throws RecognitionException {
      Token a = null;
      Token ID6 = null;

      try {
         try {
            ID6 = (Token)this.match(this.input, 9, FOLLOW_ID_in_formalArg486);
            int alt16 = true;
            int LA16_0 = this.input.LA(1);
            byte alt16;
            if (LA16_0 != 23) {
               if (LA16_0 < 17 || LA16_0 > 18) {
                  NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
                  throw nvae;
               }

               alt16 = 3;
            } else {
               int LA16_1 = this.input.LA(2);
               if (LA16_1 == 4 || LA16_1 == 8 || LA16_1 >= 13 && LA16_1 <= 14) {
                  alt16 = 1;
               } else {
                  if (LA16_1 != 10) {
                     int nvaeMark = this.input.mark();

                     try {
                        this.input.consume();
                        NoViableAltException nvae = new NoViableAltException("", 16, 1, this.input);
                        throw nvae;
                     } finally {
                        this.input.rewind(nvaeMark);
                     }
                  }

                  alt16 = 2;
               }
            }

            switch (alt16) {
               case 1:
                  this.match(this.input, 23, FOLLOW_23_in_formalArg492);
                  a = this.input.LT(1);
                  if (this.input.LA(1) != 4 && this.input.LA(1) != 8 && (this.input.LA(1) < 13 || this.input.LA(1) > 14)) {
                     MismatchedSetException mse = new MismatchedSetException((BitSet)null, this.input);
                     throw mse;
                  }

                  this.input.consume();
                  this.state.errorRecovery = false;
                  ((formalArgs_scope)this.formalArgs_stack.peek()).hasOptionalParameter = true;
                  break;
               case 2:
                  this.match(this.input, 23, FOLLOW_23_in_formalArg512);
                  a = (Token)this.match(this.input, 10, FOLLOW_LBRACK_in_formalArg516);
                  this.match(this.input, 12, FOLLOW_RBRACK_in_formalArg518);
                  ((formalArgs_scope)this.formalArgs_stack.peek()).hasOptionalParameter = true;
                  break;
               case 3:
                  if (((formalArgs_scope)this.formalArgs_stack.peek()).hasOptionalParameter) {
                     this.group.errMgr.compileTimeError(ErrorType.REQUIRED_PARAMETER_AFTER_OPTIONAL, (Token)null, ID6);
                  }
            }

            args.add(new FormalArgument(ID6 != null ? ID6.getText() : null, a));
         } catch (RecognitionException var18) {
            this.reportError(var18);
            this.recover(this.input, var18);
         }

      } finally {
         ;
      }
   }

   public final void dictDef() throws RecognitionException {
      Token ID7 = null;
      Map dict8 = null;

      try {
         try {
            ID7 = (Token)this.match(this.input, 9, FOLLOW_ID_in_dictDef551);
            this.match(this.input, 21, FOLLOW_21_in_dictDef553);
            this.pushFollow(FOLLOW_dict_in_dictDef555);
            dict8 = this.dict();
            --this.state._fsp;
            if (this.group.rawGetDictionary(ID7 != null ? ID7.getText() : null) != null) {
               this.group.errMgr.compileTimeError(ErrorType.MAP_REDEFINITION, (Token)null, ID7);
            } else if (this.group.rawGetTemplate(ID7 != null ? ID7.getText() : null) != null) {
               this.group.errMgr.compileTimeError(ErrorType.TEMPLATE_REDEFINITION_AS_MAP, (Token)null, ID7);
            } else {
               this.group.defineDictionary(ID7 != null ? ID7.getText() : null, dict8);
            }
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final Map dict() throws RecognitionException {
      Map mapping = null;
      mapping = new HashMap();

      try {
         try {
            this.match(this.input, 10, FOLLOW_LBRACK_in_dict587);
            this.pushFollow(FOLLOW_dictPairs_in_dict589);
            this.dictPairs(mapping);
            --this.state._fsp;
            this.match(this.input, 12, FOLLOW_RBRACK_in_dict592);
         } catch (RecognitionException var6) {
            this.reportError(var6);
            this.recover(this.input, var6);
         }

         return mapping;
      } finally {
         ;
      }
   }

   public final void dictPairs(Map mapping) throws RecognitionException {
      try {
         try {
            int alt19 = true;
            int LA19_0 = this.input.LA(1);
            byte alt19;
            if (LA19_0 == 13) {
               alt19 = 1;
            } else {
               if (LA19_0 != 25) {
                  NoViableAltException nvae = new NoViableAltException("", 19, 0, this.input);
                  throw nvae;
               }

               alt19 = 2;
            }

            switch (alt19) {
               case 1:
                  this.pushFollow(FOLLOW_keyValuePair_in_dictPairs607);
                  this.keyValuePair(mapping);
                  --this.state._fsp;

                  while(true) {
                     int alt18 = 2;
                     int LA18_0 = this.input.LA(1);
                     if (LA18_0 == 18) {
                        int LA17_1 = this.input.LA(2);
                        if (LA17_1 == 13) {
                           alt18 = 1;
                        }
                     }

                     switch (alt18) {
                        case 1:
                           this.match(this.input, 18, FOLLOW_18_in_dictPairs616);
                           this.pushFollow(FOLLOW_keyValuePair_in_dictPairs618);
                           this.keyValuePair(mapping);
                           --this.state._fsp;
                           break;
                        default:
                           alt18 = 2;
                           LA18_0 = this.input.LA(1);
                           if (LA18_0 == 18) {
                              alt18 = 1;
                           }

                           switch (alt18) {
                              case 1:
                                 this.match(this.input, 18, FOLLOW_18_in_dictPairs624);
                                 this.pushFollow(FOLLOW_defaultValuePair_in_dictPairs626);
                                 this.defaultValuePair(mapping);
                                 --this.state._fsp;
                                 return;
                              default:
                                 return;
                           }
                     }
                  }
               case 2:
                  this.pushFollow(FOLLOW_defaultValuePair_in_dictPairs637);
                  this.defaultValuePair(mapping);
                  --this.state._fsp;
            }
         } catch (RecognitionException var10) {
            this.error("missing dictionary entry at '" + this.input.LT(1).getText() + "'");
         }

      } finally {
         ;
      }
   }

   public final void defaultValuePair(Map mapping) throws RecognitionException {
      Object keyValue9 = null;

      try {
         try {
            this.match(this.input, 25, FOLLOW_25_in_defaultValuePair660);
            this.match(this.input, 20, FOLLOW_20_in_defaultValuePair662);
            this.pushFollow(FOLLOW_keyValue_in_defaultValuePair664);
            keyValue9 = this.keyValue();
            --this.state._fsp;
            mapping.put("default", keyValue9);
         } catch (RecognitionException var7) {
            this.reportError(var7);
            this.recover(this.input, var7);
         }

      } finally {
         ;
      }
   }

   public final void keyValuePair(Map mapping) throws RecognitionException {
      Token STRING10 = null;
      Object keyValue11 = null;

      try {
         try {
            STRING10 = (Token)this.match(this.input, 13, FOLLOW_STRING_in_keyValuePair678);
            this.match(this.input, 20, FOLLOW_20_in_keyValuePair680);
            this.pushFollow(FOLLOW_keyValue_in_keyValuePair682);
            keyValue11 = this.keyValue();
            --this.state._fsp;
            mapping.put(Misc.replaceEscapes(Misc.strip(STRING10 != null ? STRING10.getText() : null, 1)), keyValue11);
         } catch (RecognitionException var8) {
            this.reportError(var8);
            this.recover(this.input, var8);
         }

      } finally {
         ;
      }
   }

   public final Object keyValue() throws RecognitionException {
      Object value = null;
      Token BIGSTRING12 = null;
      Token BIGSTRING_NO_NL13 = null;
      Token ANONYMOUS_TEMPLATE14 = null;
      Token STRING15 = null;

      try {
         try {
            int alt20 = 8;
            int LA20_0 = this.input.LA(1);
            if (LA20_0 == 5) {
               alt20 = 1;
            } else if (LA20_0 == 6) {
               alt20 = 2;
            } else if (LA20_0 == 4) {
               alt20 = 3;
            } else if (LA20_0 == 13) {
               alt20 = 4;
            } else if (LA20_0 == 14) {
               alt20 = 5;
            } else if (LA20_0 == 8) {
               alt20 = 6;
            } else if (LA20_0 == 10) {
               alt20 = 7;
            } else if (LA20_0 == 9 && this.input.LT(1).getText().equals("key")) {
               alt20 = 8;
            }

            switch (alt20) {
               case 1:
                  BIGSTRING12 = (Token)this.match(this.input, 5, FOLLOW_BIGSTRING_in_keyValue699);
                  value = this.group.createSingleton(BIGSTRING12);
                  break;
               case 2:
                  BIGSTRING_NO_NL13 = (Token)this.match(this.input, 6, FOLLOW_BIGSTRING_NO_NL_in_keyValue708);
                  value = this.group.createSingleton(BIGSTRING_NO_NL13);
                  break;
               case 3:
                  ANONYMOUS_TEMPLATE14 = (Token)this.match(this.input, 4, FOLLOW_ANONYMOUS_TEMPLATE_in_keyValue716);
                  value = this.group.createSingleton(ANONYMOUS_TEMPLATE14);
                  break;
               case 4:
                  STRING15 = (Token)this.match(this.input, 13, FOLLOW_STRING_in_keyValue723);
                  value = Misc.replaceEscapes(Misc.strip(STRING15 != null ? STRING15.getText() : null, 1));
                  break;
               case 5:
                  this.match(this.input, 14, FOLLOW_TRUE_in_keyValue733);
                  value = true;
                  break;
               case 6:
                  this.match(this.input, 8, FOLLOW_FALSE_in_keyValue743);
                  value = false;
                  break;
               case 7:
                  this.match(this.input, 10, FOLLOW_LBRACK_in_keyValue753);
                  this.match(this.input, 12, FOLLOW_RBRACK_in_keyValue755);
                  value = Collections.emptyList();
                  break;
               case 8:
                  if (!this.input.LT(1).getText().equals("key")) {
                     throw new FailedPredicateException(this.input, "keyValue", "input.LT(1).getText().equals(\"key\")");
                  }

                  this.match(this.input, 9, FOLLOW_ID_in_keyValue768);
                  value = "key";
            }
         } catch (RecognitionException var11) {
            this.error("missing value for key at '" + this.input.LT(1).getText() + "'");
         }

         return value;
      } finally {
         ;
      }
   }

   protected static class formalArgs_scope {
      boolean hasOptionalParameter;
   }
}
