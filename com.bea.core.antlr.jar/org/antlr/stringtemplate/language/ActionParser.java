package org.antlr.stringtemplate.language;

import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.AST;
import antlr.collections.impl.ASTArray;
import antlr.collections.impl.BitSet;
import java.util.HashMap;
import java.util.Map;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;

public class ActionParser extends LLkParser implements ActionParserTokenTypes {
   protected StringTemplate self;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "APPLY", "MULTI_APPLY", "ARGS", "INCLUDE", "\"if\"", "VALUE", "TEMPLATE", "FUNCTION", "SINGLEVALUEARG", "LIST", "NOTHING", "SEMI", "LPAREN", "RPAREN", "\"elseif\"", "COMMA", "ID", "ASSIGN", "COLON", "NOT", "PLUS", "DOT", "\"first\"", "\"rest\"", "\"last\"", "\"length\"", "\"strip\"", "\"trunc\"", "\"super\"", "ANONYMOUS_TEMPLATE", "STRING", "INT", "LBRACK", "RBRACK", "DOTDOTDOT", "TEMPLATE_ARGS", "NESTED_ANONYMOUS_TEMPLATE", "ESC_CHAR", "WS", "WS_CHAR"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());

   public ActionParser(TokenStream lexer, StringTemplate self) {
      this((TokenStream)lexer, 2);
      this.self = self;
   }

   public void reportError(RecognitionException e) {
      StringTemplateGroup group = this.self.getGroup();
      if (group == StringTemplate.defaultGroup) {
         this.self.error("action parse error; template context is " + this.self.getEnclosingInstanceStackString(), e);
      } else {
         this.self.error("action parse error in group " + this.self.getGroup().getName() + " line " + this.self.getGroupFileLine() + "; template context is " + this.self.getEnclosingInstanceStackString(), e);
      }

   }

   protected ActionParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.self = null;
      this.tokenNames = _tokenNames;
      this.buildTokenTypeASTClassMap();
      this.astFactory = new ASTFactory(this.getTokenTypeToASTClassMap());
   }

   public ActionParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 2);
   }

   protected ActionParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.self = null;
      this.tokenNames = _tokenNames;
      this.buildTokenTypeASTClassMap();
      this.astFactory = new ASTFactory(this.getTokenTypeToASTClassMap());
   }

   public ActionParser(TokenStream lexer) {
      this((TokenStream)lexer, 2);
   }

   public ActionParser(ParserSharedInputState state) {
      super((ParserSharedInputState)state, 2);
      this.self = null;
      this.tokenNames = _tokenNames;
      this.buildTokenTypeASTClassMap();
      this.astFactory = new ASTFactory(this.getTokenTypeToASTClassMap());
   }

   public final Map action() throws RecognitionException, TokenStreamException {
      Map opts = null;
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST action_AST = null;

      try {
         label22:
         switch (this.LA(1)) {
            case 8:
               StringTemplateAST tmp2_AST = null;
               tmp2_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.makeASTRoot(currentAST, tmp2_AST);
               this.match(8);
               this.match(16);
               this.ifCondition();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               this.match(17);
               action_AST = (StringTemplateAST)currentAST.root;
               break;
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 16:
            case 20:
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
               this.templatesExpr();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               switch (this.LA(1)) {
                  case 15:
                     this.match(15);
                     opts = this.optionList();
                     this.astFactory.addASTChild(currentAST, this.returnAST);
                  case 1:
                     action_AST = (StringTemplateAST)currentAST.root;
                     break label22;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            case 18:
               this.match(18);
               this.match(16);
               this.ifCondition();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               this.match(17);
               action_AST = (StringTemplateAST)currentAST.root;
         }
      } catch (RecognitionException var5) {
         if (this.inputState.guessing != 0) {
            throw var5;
         }

         this.reportError(var5);
         this.recover(var5, _tokenSet_0);
      }

      this.returnAST = action_AST;
      return opts;
   }

   public final void templatesExpr() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST templatesExpr_AST = null;
      Token c = null;
      StringTemplateAST c_AST = null;

      try {
         boolean synPredMatched10 = false;
         if (_tokenSet_1.member(this.LA(1)) && _tokenSet_2.member(this.LA(2))) {
            int _m10 = this.mark();
            synPredMatched10 = true;
            ++this.inputState.guessing;

            try {
               this.parallelArrayTemplateApplication();
            } catch (RecognitionException var8) {
               synPredMatched10 = false;
            }

            this.rewind(_m10);
            --this.inputState.guessing;
         }

         if (synPredMatched10) {
            this.parallelArrayTemplateApplication();
            this.astFactory.addASTChild(currentAST, this.returnAST);
            templatesExpr_AST = (StringTemplateAST)currentAST.root;
         } else {
            if (!_tokenSet_1.member(this.LA(1)) || !_tokenSet_3.member(this.LA(2))) {
               throw new NoViableAltException(this.LT(1), this.getFilename());
            }

            this.expr();
            this.astFactory.addASTChild(currentAST, this.returnAST);

            while(this.LA(1) == 22) {
               c = this.LT(1);
               c_AST = (StringTemplateAST)this.astFactory.create(c);
               this.astFactory.makeASTRoot(currentAST, c_AST);
               this.match(22);
               if (this.inputState.guessing == 0) {
                  c_AST.setType(4);
               }

               this.template();
               this.astFactory.addASTChild(currentAST, this.returnAST);

               while(this.LA(1) == 19) {
                  this.match(19);
                  this.template();
                  this.astFactory.addASTChild(currentAST, this.returnAST);
               }
            }

            templatesExpr_AST = (StringTemplateAST)currentAST.root;
         }
      } catch (RecognitionException var9) {
         if (this.inputState.guessing != 0) {
            throw var9;
         }

         this.reportError(var9);
         this.recover(var9, _tokenSet_4);
      }

      this.returnAST = templatesExpr_AST;
   }

   public final Map optionList() throws RecognitionException, TokenStreamException {
      Map opts = new HashMap();
      this.returnAST = null;
      new ASTPair();
      StringTemplateAST optionList_AST = null;

      try {
         this.option(opts);

         while(this.LA(1) == 19) {
            StringTemplateAST tmp9_AST = null;
            StringTemplateAST var10000 = (StringTemplateAST)this.astFactory.create(this.LT(1));
            this.match(19);
            this.option(opts);
         }
      } catch (RecognitionException var5) {
         if (this.inputState.guessing != 0) {
            throw var5;
         }

         this.reportError(var5);
         this.recover(var5, _tokenSet_0);
      }

      this.returnAST = (AST)optionList_AST;
      return opts;
   }

   public final void ifCondition() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST ifCondition_AST = null;

      try {
         switch (this.LA(1)) {
            case 16:
            case 20:
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
               this.ifAtom();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               ifCondition_AST = (StringTemplateAST)currentAST.root;
               break;
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 24:
            case 25:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 23:
               StringTemplateAST tmp10_AST = null;
               tmp10_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.makeASTRoot(currentAST, tmp10_AST);
               this.match(23);
               this.ifAtom();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               ifCondition_AST = (StringTemplateAST)currentAST.root;
         }
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_5);
      }

      this.returnAST = ifCondition_AST;
   }

   public final void option(Map opts) throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST option_AST = null;
      Token i = null;
      StringTemplateAST i_AST = null;
      StringTemplateAST e_AST = null;
      Object v = null;

      try {
         i = this.LT(1);
         i_AST = (StringTemplateAST)this.astFactory.create(i);
         this.astFactory.addASTChild(currentAST, i_AST);
         this.match(20);
         switch (this.LA(1)) {
            case 1:
            case 19:
               if (this.inputState.guessing == 0) {
                  v = "empty expr option";
               }
               break;
            case 21:
               StringTemplateAST tmp11_AST = null;
               tmp11_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp11_AST);
               this.match(21);
               this.nonAlternatingTemplateExpr();
               e_AST = (StringTemplateAST)this.returnAST;
               this.astFactory.addASTChild(currentAST, this.returnAST);
               if (this.inputState.guessing == 0) {
                  v = e_AST;
               }
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         if (this.inputState.guessing == 0) {
            opts.put(i_AST.getText(), v);
         }

         option_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var9) {
         if (this.inputState.guessing != 0) {
            throw var9;
         }

         this.reportError(var9);
         this.recover(var9, _tokenSet_6);
      }

      this.returnAST = option_AST;
   }

   public final void nonAlternatingTemplateExpr() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST nonAlternatingTemplateExpr_AST = null;
      Token c = null;
      StringTemplateAST c_AST = null;

      try {
         this.expr();
         this.astFactory.addASTChild(currentAST, this.returnAST);

         while(this.LA(1) == 22) {
            c = this.LT(1);
            c_AST = (StringTemplateAST)this.astFactory.create(c);
            this.astFactory.makeASTRoot(currentAST, c_AST);
            this.match(22);
            if (this.inputState.guessing == 0) {
               c_AST.setType(4);
            }

            this.template();
            this.astFactory.addASTChild(currentAST, this.returnAST);
         }

         nonAlternatingTemplateExpr_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var6) {
         if (this.inputState.guessing != 0) {
            throw var6;
         }

         this.reportError(var6);
         this.recover(var6, _tokenSet_7);
      }

      this.returnAST = nonAlternatingTemplateExpr_AST;
   }

   public final void parallelArrayTemplateApplication() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST parallelArrayTemplateApplication_AST = null;
      Token c = null;
      StringTemplateAST c_AST = null;

      try {
         this.expr();
         this.astFactory.addASTChild(currentAST, this.returnAST);

         int _cnt17;
         for(_cnt17 = 0; this.LA(1) == 19; ++_cnt17) {
            this.match(19);
            this.expr();
            this.astFactory.addASTChild(currentAST, this.returnAST);
         }

         if (_cnt17 < 1) {
            throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         c = this.LT(1);
         c_AST = (StringTemplateAST)this.astFactory.create(c);
         this.astFactory.addASTChild(currentAST, c_AST);
         this.match(22);
         this.anonymousTemplate();
         this.astFactory.addASTChild(currentAST, this.returnAST);
         if (this.inputState.guessing == 0) {
            parallelArrayTemplateApplication_AST = (StringTemplateAST)currentAST.root;
            parallelArrayTemplateApplication_AST = (StringTemplateAST)this.astFactory.make((new ASTArray(2)).add((StringTemplateAST)this.astFactory.create(5, "MULTI_APPLY")).add(parallelArrayTemplateApplication_AST));
            currentAST.root = parallelArrayTemplateApplication_AST;
            currentAST.child = (AST)(parallelArrayTemplateApplication_AST != null && parallelArrayTemplateApplication_AST.getFirstChild() != null ? parallelArrayTemplateApplication_AST.getFirstChild() : parallelArrayTemplateApplication_AST);
            currentAST.advanceChildToEnd();
         }

         parallelArrayTemplateApplication_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var6) {
         if (this.inputState.guessing != 0) {
            throw var6;
         }

         this.reportError(var6);
         this.recover(var6, _tokenSet_4);
      }

      this.returnAST = parallelArrayTemplateApplication_AST;
   }

   public final void expr() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST expr_AST = null;

      try {
         this.primaryExpr();
         this.astFactory.addASTChild(currentAST, this.returnAST);

         while(this.LA(1) == 24) {
            StringTemplateAST tmp13_AST = null;
            tmp13_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
            this.astFactory.makeASTRoot(currentAST, tmp13_AST);
            this.match(24);
            this.primaryExpr();
            this.astFactory.addASTChild(currentAST, this.returnAST);
         }

         expr_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_8);
      }

      this.returnAST = expr_AST;
   }

   public final void template() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST template_AST = null;

      try {
         switch (this.LA(1)) {
            case 16:
            case 20:
            case 32:
               this.namedTemplate();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               break;
            case 33:
               this.anonymousTemplate();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         if (this.inputState.guessing == 0) {
            template_AST = (StringTemplateAST)currentAST.root;
            template_AST = (StringTemplateAST)this.astFactory.make((new ASTArray(2)).add((StringTemplateAST)this.astFactory.create(10)).add(template_AST));
            currentAST.root = template_AST;
            currentAST.child = (AST)(template_AST != null && template_AST.getFirstChild() != null ? template_AST.getFirstChild() : template_AST);
            currentAST.advanceChildToEnd();
         }

         template_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_8);
      }

      this.returnAST = template_AST;
   }

   public final void anonymousTemplate() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST anonymousTemplate_AST = null;
      Token t = null;
      StringTemplateAST t_AST = null;

      try {
         t = this.LT(1);
         t_AST = (StringTemplateAST)this.astFactory.create(t);
         this.astFactory.addASTChild(currentAST, t_AST);
         this.match(33);
         if (this.inputState.guessing == 0) {
            StringTemplate anonymous = new StringTemplate();
            anonymous.setGroup(this.self.getGroup());
            anonymous.setEnclosingInstance(this.self);
            anonymous.setTemplate(t.getText());
            anonymous.defineFormalArguments(((StringTemplateToken)t).args);
            t_AST.setStringTemplate(anonymous);
         }

         anonymousTemplate_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var6) {
         if (this.inputState.guessing != 0) {
            throw var6;
         }

         this.reportError(var6);
         this.recover(var6, _tokenSet_8);
      }

      this.returnAST = anonymousTemplate_AST;
   }

   public final void ifAtom() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST ifAtom_AST = null;

      try {
         this.templatesExpr();
         this.astFactory.addASTChild(currentAST, this.returnAST);
         ifAtom_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_5);
      }

      this.returnAST = ifAtom_AST;
   }

   public final void primaryExpr() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST primaryExpr_AST = null;

      try {
         StringTemplateAST tmp16_AST;
         switch (this.LA(1)) {
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
               this.function();
               this.astFactory.addASTChild(currentAST, this.returnAST);

               while(this.LA(1) == 25) {
                  StringTemplateAST tmp14_AST = null;
                  tmp14_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
                  this.astFactory.makeASTRoot(currentAST, tmp14_AST);
                  this.match(25);
                  switch (this.LA(1)) {
                     case 16:
                        this.valueExpr();
                        this.astFactory.addASTChild(currentAST, this.returnAST);
                        break;
                     case 20:
                        tmp16_AST = null;
                        tmp16_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
                        this.astFactory.addASTChild(currentAST, tmp16_AST);
                        this.match(20);
                        break;
                     default:
                        throw new NoViableAltException(this.LT(1), this.getFilename());
                  }
               }

               primaryExpr_AST = (StringTemplateAST)currentAST.root;
               break;
            case 32:
            case 33:
            case 34:
            case 35:
            default:
               boolean synPredMatched25 = false;
               if ((this.LA(1) == 16 || this.LA(1) == 20 || this.LA(1) == 32) && _tokenSet_9.member(this.LA(2))) {
                  int _m25 = this.mark();
                  synPredMatched25 = true;
                  ++this.inputState.guessing;

                  try {
                     this.templateInclude();
                  } catch (RecognitionException var6) {
                     synPredMatched25 = false;
                  }

                  this.rewind(_m25);
                  --this.inputState.guessing;
               }

               if (synPredMatched25) {
                  this.templateInclude();
                  this.astFactory.addASTChild(currentAST, this.returnAST);
                  primaryExpr_AST = (StringTemplateAST)currentAST.root;
               } else if (_tokenSet_10.member(this.LA(1)) && _tokenSet_11.member(this.LA(2))) {
                  this.atom();
                  this.astFactory.addASTChild(currentAST, this.returnAST);

                  while(this.LA(1) == 25) {
                     tmp16_AST = null;
                     tmp16_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
                     this.astFactory.makeASTRoot(currentAST, tmp16_AST);
                     this.match(25);
                     switch (this.LA(1)) {
                        case 16:
                           this.valueExpr();
                           this.astFactory.addASTChild(currentAST, this.returnAST);
                           break;
                        case 20:
                           StringTemplateAST tmp17_AST = null;
                           tmp17_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
                           this.astFactory.addASTChild(currentAST, tmp17_AST);
                           this.match(20);
                           break;
                        default:
                           throw new NoViableAltException(this.LT(1), this.getFilename());
                     }
                  }

                  primaryExpr_AST = (StringTemplateAST)currentAST.root;
               } else {
                  if (this.LA(1) != 16 || !_tokenSet_1.member(this.LA(2))) {
                     throw new NoViableAltException(this.LT(1), this.getFilename());
                  }

                  this.valueExpr();
                  this.astFactory.addASTChild(currentAST, this.returnAST);
                  primaryExpr_AST = (StringTemplateAST)currentAST.root;
               }
               break;
            case 36:
               this.list();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               primaryExpr_AST = (StringTemplateAST)currentAST.root;
         }
      } catch (RecognitionException var7) {
         if (this.inputState.guessing != 0) {
            throw var7;
         }

         this.reportError(var7);
         this.recover(var7, _tokenSet_12);
      }

      this.returnAST = primaryExpr_AST;
   }

   public final void templateInclude() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST templateInclude_AST = null;
      Token id = null;
      StringTemplateAST id_AST = null;
      Token qid = null;
      StringTemplateAST qid_AST = null;

      try {
         switch (this.LA(1)) {
            case 16:
               this.indirectTemplate();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               break;
            case 20:
               id = this.LT(1);
               id_AST = (StringTemplateAST)this.astFactory.create(id);
               this.astFactory.addASTChild(currentAST, id_AST);
               this.match(20);
               this.argList();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               break;
            case 32:
               this.match(32);
               this.match(25);
               qid = this.LT(1);
               qid_AST = (StringTemplateAST)this.astFactory.create(qid);
               this.astFactory.addASTChild(currentAST, qid_AST);
               this.match(20);
               if (this.inputState.guessing == 0) {
                  qid_AST.setText("super." + qid_AST.getText());
               }

               this.argList();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         if (this.inputState.guessing == 0) {
            templateInclude_AST = (StringTemplateAST)currentAST.root;
            templateInclude_AST = (StringTemplateAST)this.astFactory.make((new ASTArray(2)).add((StringTemplateAST)this.astFactory.create(7, "include")).add(templateInclude_AST));
            currentAST.root = templateInclude_AST;
            currentAST.child = (AST)(templateInclude_AST != null && templateInclude_AST.getFirstChild() != null ? templateInclude_AST.getFirstChild() : templateInclude_AST);
            currentAST.advanceChildToEnd();
         }

         templateInclude_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var8) {
         if (this.inputState.guessing != 0) {
            throw var8;
         }

         this.reportError(var8);
         this.recover(var8, _tokenSet_12);
      }

      this.returnAST = templateInclude_AST;
   }

   public final void atom() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST atom_AST = null;

      try {
         StringTemplateAST tmp22_AST;
         switch (this.LA(1)) {
            case 20:
               tmp22_AST = null;
               tmp22_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp22_AST);
               this.match(20);
               atom_AST = (StringTemplateAST)currentAST.root;
               break;
            case 33:
               tmp22_AST = null;
               tmp22_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp22_AST);
               this.match(33);
               atom_AST = (StringTemplateAST)currentAST.root;
               break;
            case 34:
               tmp22_AST = null;
               tmp22_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp22_AST);
               this.match(34);
               atom_AST = (StringTemplateAST)currentAST.root;
               break;
            case 35:
               tmp22_AST = null;
               tmp22_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp22_AST);
               this.match(35);
               atom_AST = (StringTemplateAST)currentAST.root;
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_11);
      }

      this.returnAST = atom_AST;
   }

   public final void valueExpr() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST valueExpr_AST = null;
      Token eval = null;
      StringTemplateAST eval_AST = null;

      try {
         eval = this.LT(1);
         eval_AST = (StringTemplateAST)this.astFactory.create(eval);
         this.astFactory.makeASTRoot(currentAST, eval_AST);
         this.match(16);
         this.templatesExpr();
         this.astFactory.addASTChild(currentAST, this.returnAST);
         this.match(17);
         if (this.inputState.guessing == 0) {
            eval_AST.setType(9);
            eval_AST.setText("value");
         }

         valueExpr_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var6) {
         if (this.inputState.guessing != 0) {
            throw var6;
         }

         this.reportError(var6);
         this.recover(var6, _tokenSet_11);
      }

      this.returnAST = valueExpr_AST;
   }

   public final void function() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST function_AST = null;

      try {
         StringTemplateAST tmp30_AST;
         switch (this.LA(1)) {
            case 26:
               tmp30_AST = null;
               tmp30_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp30_AST);
               this.match(26);
               break;
            case 27:
               tmp30_AST = null;
               tmp30_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp30_AST);
               this.match(27);
               break;
            case 28:
               tmp30_AST = null;
               tmp30_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp30_AST);
               this.match(28);
               break;
            case 29:
               tmp30_AST = null;
               tmp30_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp30_AST);
               this.match(29);
               break;
            case 30:
               tmp30_AST = null;
               tmp30_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp30_AST);
               this.match(30);
               break;
            case 31:
               tmp30_AST = null;
               tmp30_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp30_AST);
               this.match(31);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         this.singleArg();
         this.astFactory.addASTChild(currentAST, this.returnAST);
         if (this.inputState.guessing == 0) {
            function_AST = (StringTemplateAST)currentAST.root;
            function_AST = (StringTemplateAST)this.astFactory.make((new ASTArray(2)).add((StringTemplateAST)this.astFactory.create(11)).add(function_AST));
            currentAST.root = function_AST;
            currentAST.child = (AST)(function_AST != null && function_AST.getFirstChild() != null ? function_AST.getFirstChild() : function_AST);
            currentAST.advanceChildToEnd();
         }

         function_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_11);
      }

      this.returnAST = function_AST;
   }

   public final void list() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST list_AST = null;
      Token lb = null;
      StringTemplateAST lb_AST = null;

      try {
         lb = this.LT(1);
         lb_AST = (StringTemplateAST)this.astFactory.create(lb);
         this.astFactory.makeASTRoot(currentAST, lb_AST);
         this.match(36);
         if (this.inputState.guessing == 0) {
            lb_AST.setType(13);
            lb_AST.setText("value");
         }

         this.listElement();
         this.astFactory.addASTChild(currentAST, this.returnAST);

         while(this.LA(1) == 19) {
            this.match(19);
            this.listElement();
            this.astFactory.addASTChild(currentAST, this.returnAST);
         }

         this.match(37);
         list_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var6) {
         if (this.inputState.guessing != 0) {
            throw var6;
         }

         this.reportError(var6);
         this.recover(var6, _tokenSet_12);
      }

      this.returnAST = list_AST;
   }

   public final void singleArg() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST singleArg_AST = null;

      try {
         this.match(16);
         this.nonAlternatingTemplateExpr();
         this.astFactory.addASTChild(currentAST, this.returnAST);
         this.match(17);
         if (this.inputState.guessing == 0) {
            singleArg_AST = (StringTemplateAST)currentAST.root;
            singleArg_AST = (StringTemplateAST)this.astFactory.make((new ASTArray(2)).add((StringTemplateAST)this.astFactory.create(12, "SINGLEVALUEARG")).add(singleArg_AST));
            currentAST.root = singleArg_AST;
            currentAST.child = (AST)(singleArg_AST != null && singleArg_AST.getFirstChild() != null ? singleArg_AST.getFirstChild() : singleArg_AST);
            currentAST.advanceChildToEnd();
         }

         singleArg_AST = (StringTemplateAST)currentAST.root;
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_11);
      }

      this.returnAST = singleArg_AST;
   }

   public final void namedTemplate() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST namedTemplate_AST = null;
      Token qid = null;
      StringTemplateAST qid_AST = null;

      try {
         switch (this.LA(1)) {
            case 16:
               this.indirectTemplate();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               namedTemplate_AST = (StringTemplateAST)currentAST.root;
               break;
            case 20:
               StringTemplateAST tmp35_AST = null;
               tmp35_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp35_AST);
               this.match(20);
               this.argList();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               namedTemplate_AST = (StringTemplateAST)currentAST.root;
               break;
            case 32:
               this.match(32);
               this.match(25);
               qid = this.LT(1);
               qid_AST = (StringTemplateAST)this.astFactory.create(qid);
               this.astFactory.addASTChild(currentAST, qid_AST);
               this.match(20);
               if (this.inputState.guessing == 0) {
                  qid_AST.setText("super." + qid_AST.getText());
               }

               this.argList();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               namedTemplate_AST = (StringTemplateAST)currentAST.root;
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var6) {
         if (this.inputState.guessing != 0) {
            throw var6;
         }

         this.reportError(var6);
         this.recover(var6, _tokenSet_8);
      }

      this.returnAST = namedTemplate_AST;
   }

   public final void argList() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST argList_AST = null;

      try {
         if (this.LA(1) == 16 && this.LA(2) == 17) {
            this.match(16);
            this.match(17);
            if (this.inputState.guessing == 0) {
               argList_AST = (StringTemplateAST)currentAST.root;
               argList_AST = (StringTemplateAST)this.astFactory.create(6, "ARGS");
               currentAST.root = argList_AST;
               currentAST.child = (AST)(argList_AST != null && argList_AST.getFirstChild() != null ? argList_AST.getFirstChild() : argList_AST);
               currentAST.advanceChildToEnd();
            }
         } else {
            boolean synPredMatched52 = false;
            if (this.LA(1) == 16 && _tokenSet_1.member(this.LA(2))) {
               int _m52 = this.mark();
               synPredMatched52 = true;
               ++this.inputState.guessing;

               try {
                  this.singleArg();
               } catch (RecognitionException var6) {
                  synPredMatched52 = false;
               }

               this.rewind(_m52);
               --this.inputState.guessing;
            }

            if (synPredMatched52) {
               this.singleArg();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               argList_AST = (StringTemplateAST)currentAST.root;
            } else {
               if (this.LA(1) != 16 || this.LA(2) != 20 && this.LA(2) != 38) {
                  throw new NoViableAltException(this.LT(1), this.getFilename());
               }

               this.match(16);
               this.argumentAssignment();
               this.astFactory.addASTChild(currentAST, this.returnAST);

               while(this.LA(1) == 19) {
                  this.match(19);
                  this.argumentAssignment();
                  this.astFactory.addASTChild(currentAST, this.returnAST);
               }

               this.match(17);
               if (this.inputState.guessing == 0) {
                  argList_AST = (StringTemplateAST)currentAST.root;
                  argList_AST = (StringTemplateAST)this.astFactory.make((new ASTArray(2)).add((StringTemplateAST)this.astFactory.create(6, "ARGS")).add(argList_AST));
                  currentAST.root = argList_AST;
                  currentAST.child = (AST)(argList_AST != null && argList_AST.getFirstChild() != null ? argList_AST.getFirstChild() : argList_AST);
                  currentAST.advanceChildToEnd();
               }

               argList_AST = (StringTemplateAST)currentAST.root;
            }
         }
      } catch (RecognitionException var7) {
         if (this.inputState.guessing != 0) {
            throw var7;
         }

         this.reportError(var7);
         this.recover(var7, _tokenSet_12);
      }

      this.returnAST = argList_AST;
   }

   public final void indirectTemplate() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST indirectTemplate_AST = null;
      StringTemplateAST e_AST = null;
      StringTemplateAST args_AST = null;

      try {
         StringTemplateAST tmp43_AST = null;
         StringTemplateAST var10000 = (StringTemplateAST)this.astFactory.create(this.LT(1));
         this.match(16);
         this.templatesExpr();
         e_AST = (StringTemplateAST)this.returnAST;
         StringTemplateAST tmp44_AST = null;
         var10000 = (StringTemplateAST)this.astFactory.create(this.LT(1));
         this.match(17);
         this.argList();
         args_AST = (StringTemplateAST)this.returnAST;
         if (this.inputState.guessing == 0) {
            indirectTemplate_AST = (StringTemplateAST)currentAST.root;
            indirectTemplate_AST = (StringTemplateAST)this.astFactory.make((new ASTArray(3)).add((StringTemplateAST)this.astFactory.create(9, "value")).add(e_AST).add(args_AST));
            currentAST.root = indirectTemplate_AST;
            currentAST.child = (AST)(indirectTemplate_AST != null && indirectTemplate_AST.getFirstChild() != null ? indirectTemplate_AST.getFirstChild() : indirectTemplate_AST);
            currentAST.advanceChildToEnd();
         }
      } catch (RecognitionException var7) {
         if (this.inputState.guessing != 0) {
            throw var7;
         }

         this.reportError(var7);
         this.recover(var7, _tokenSet_12);
      }

      this.returnAST = indirectTemplate_AST;
   }

   public final void listElement() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST listElement_AST = null;

      try {
         label44: {
            switch (this.LA(1)) {
               case 16:
               case 20:
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
                  this.nonAlternatingTemplateExpr();
                  this.astFactory.addASTChild(currentAST, this.returnAST);
                  listElement_AST = (StringTemplateAST)currentAST.root;
                  break label44;
               case 17:
               case 18:
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
               case 19:
               case 37:
            }

            if (this.inputState.guessing == 0) {
               listElement_AST = (StringTemplateAST)currentAST.root;
               listElement_AST = (StringTemplateAST)this.astFactory.create(14, "NOTHING");
               currentAST.root = listElement_AST;
               currentAST.child = (AST)(listElement_AST != null && listElement_AST.getFirstChild() != null ? listElement_AST.getFirstChild() : listElement_AST);
               currentAST.advanceChildToEnd();
            }

            listElement_AST = (StringTemplateAST)currentAST.root;
         }
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_13);
      }

      this.returnAST = listElement_AST;
   }

   public final void argumentAssignment() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      StringTemplateAST argumentAssignment_AST = null;

      try {
         StringTemplateAST tmp47_AST;
         switch (this.LA(1)) {
            case 20:
               tmp47_AST = null;
               tmp47_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp47_AST);
               this.match(20);
               StringTemplateAST tmp46_AST = null;
               tmp46_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.makeASTRoot(currentAST, tmp46_AST);
               this.match(21);
               this.nonAlternatingTemplateExpr();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               argumentAssignment_AST = (StringTemplateAST)currentAST.root;
               break;
            case 38:
               tmp47_AST = null;
               tmp47_AST = (StringTemplateAST)this.astFactory.create(this.LT(1));
               this.astFactory.addASTChild(currentAST, tmp47_AST);
               this.match(38);
               argumentAssignment_AST = (StringTemplateAST)currentAST.root;
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var5) {
         if (this.inputState.guessing != 0) {
            throw var5;
         }

         this.reportError(var5);
         this.recover(var5, _tokenSet_14);
      }

      this.returnAST = argumentAssignment_AST;
   }

   protected void buildTokenTypeASTClassMap() {
      this.tokenTypeToASTClassMap = null;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{2L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[]{137372958720L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[]{274862768128L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[]{274867126274L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_4() {
      long[] data = new long[]{163842L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_5() {
      long[] data = new long[]{131072L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_6() {
      long[] data = new long[]{524290L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_7() {
      long[] data = new long[]{137439608834L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_8() {
      long[] data = new long[]{137443835906L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_9() {
      long[] data = new long[]{137406513152L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_10() {
      long[] data = new long[]{60130590720L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_11() {
      long[] data = new long[]{137494167554L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_12() {
      long[] data = new long[]{137460613122L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_13() {
      long[] data = new long[]{137439477760L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_14() {
      long[] data = new long[]{655360L, 0L};
      return data;
   }
}
