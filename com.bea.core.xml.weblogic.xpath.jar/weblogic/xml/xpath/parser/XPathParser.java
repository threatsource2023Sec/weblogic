package weblogic.xml.xpath.parser;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class XPathParser extends LLkParser implements XPathParserTokenTypes {
   private ModelFactory mFactory;
   private Collection mErrors;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "SLASH", "DOUBLE_SLASH", "DOUBLE_DOT", "DOT", "ATSIGN", "NCNAME", "DOUBLE_COLON", "ASTERISK", "\"processing-instruction\"", "LPAREN", "LITERAL", "RPAREN", "\"comment\"", "\"text\"", "\"node\"", "COLON", "NUMBER", "DOLLAR", "COMMA", "PIPE", "LBRACKET", "RBRACKET", "\"or\"", "\"and\"", "EQUALS", "NOT_EQUAL", "LESS_THAN", "LESS_EQUAL", "GREATER_THAN", "GREATER_EQUAL", "ADDITION", "SUBTRACTION", "\"div\"", "\"mod\"", "DASH", "WS", "DIGIT", "LETTER", "SINGLE_QUOTE_LITERAL", "DOUBLE_QUOTE_LITERAL"};
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
   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());

   public void setModelFactory(ModelFactory f) {
      this.mFactory = f;
   }

   private int getAxisNumber(String name) {
      int num = ModelFactory.AXIS_NAMES.indexOf(name);
      if (num == -1) {
         throw new IllegalArgumentException("Unknown axis '" + name + "'");
      } else {
         return num;
      }
   }

   public Collection getErrors() {
      return this.mErrors;
   }

   public void reportError(RecognitionException re) {
      if (this.mErrors == null) {
         this.mErrors = new ArrayList();
      }

      this.mErrors.add(re);
   }

   public void reportError(Exception re) {
      if (this.mErrors == null) {
         this.mErrors = new ArrayList();
      }

      this.mErrors.add(re);
   }

   public void reportError(String msg) {
      if (this.mErrors == null) {
         this.mErrors = new ArrayList();
      }

      this.mErrors.add(msg);
   }

   protected XPathParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.mErrors = null;
      this.tokenNames = _tokenNames;
   }

   public XPathParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 2);
   }

   protected XPathParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.mErrors = null;
      this.tokenNames = _tokenNames;
   }

   public XPathParser(TokenStream lexer) {
      this((TokenStream)lexer, 2);
   }

   public XPathParser(ParserSharedInputState state) {
      super(state, 2);
      this.mErrors = null;
      this.tokenNames = _tokenNames;
   }

   public final ExpressionModel start() throws RecognitionException, TokenStreamException {
      ExpressionModel foo = null;

      try {
         foo = this.expr();
         if (this.inputState.guessing == 0) {
         }
      } catch (RecognitionException var3) {
         if (this.inputState.guessing != 0) {
            throw var3;
         }

         this.reportError(var3);
         this.recover(var3, _tokenSet_0);
      }

      return foo;
   }

   public final ExpressionModel expr() throws RecognitionException, TokenStreamException {
      ExpressionModel out = null;

      try {
         out = this.orExpr();
      } catch (RecognitionException var3) {
         if (this.inputState.guessing != 0) {
            throw var3;
         }

         this.reportError(var3);
         this.recover(var3, _tokenSet_1);
      }

      return out;
   }

   public final LocationPathModel locationPath() throws RecognitionException, TokenStreamException {
      Collection steps = new ArrayList();
      StepModel s = null;
      LocationPathModel out = null;

      try {
         label48:
         switch (this.LA(1)) {
            case 4:
            case 5:
               switch (this.LA(1)) {
                  case 4:
                     this.match(4);
                     if (this.inputState.guessing == 0) {
                        steps.add(this.mFactory.getDocumentRootStep());
                     }
                     break label48;
                  case 5:
                     this.match(5);
                     if (this.inputState.guessing == 0) {
                        steps.add(this.mFactory.getDoubleSlashStep());
                     }
                     break label48;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 16:
            case 17:
            case 18:
               out = this.relativeLocationPath((StepModel)null);
               return out;
            case 10:
            case 13:
            case 14:
            case 15:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         while(true) {
            if (!_tokenSet_2.member(this.LA(1)) || !_tokenSet_3.member(this.LA(2))) {
               if (this.inputState.guessing == 0) {
                  out = this.mFactory.createLocationPath(steps);
               }
               break;
            }

            s = this.step();
            if (this.inputState.guessing == 0) {
               steps.add(s);
            }

            switch (this.LA(1)) {
               case 1:
               case 6:
               case 7:
               case 8:
               case 9:
               case 11:
               case 12:
               case 15:
               case 16:
               case 17:
               case 18:
               case 22:
               case 23:
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
                  break;
               case 2:
               case 3:
               case 10:
               case 13:
               case 14:
               case 19:
               case 20:
               case 21:
               case 24:
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
               case 4:
                  this.match(4);
                  break;
               case 5:
                  this.match(5);
                  if (this.inputState.guessing == 0) {
                     steps.add(this.mFactory.getDoubleSlashStep());
                  }
            }
         }
      } catch (RecognitionException var5) {
         if (this.inputState.guessing != 0) {
            throw var5;
         }

         this.reportError(var5);
         this.recover(var5, _tokenSet_4);
      }

      return out;
   }

   public final StepModel step() throws RecognitionException, TokenStreamException {
      Token a = null;
      Token lit = null;
      Token pfx = null;
      Token nme = null;
      StepModel step = null;
      int axis = -1;
      NodeTestModel test = null;
      Collection predicates = null;
      ExpressionModel predex = null;

      try {
         switch (this.LA(1)) {
            case 6:
               this.match(6);
               if (this.inputState.guessing == 0) {
                  axis = 10;
                  test = this.mFactory.getNodeNodeTest();
               }
               break;
            case 7:
               this.match(7);
               if (this.inputState.guessing == 0) {
                  axis = 0;
                  test = this.mFactory.getNodeNodeTest();
               }
               break;
            case 8:
            case 9:
            case 11:
            case 12:
            case 16:
            case 17:
            case 18:
               if (this.LA(1) == 8) {
                  this.match(8);
                  if (this.inputState.guessing == 0) {
                     axis = 3;
                  }
               } else if (this.LA(1) == 9 && this.LA(2) == 10) {
                  a = this.LT(1);
                  this.match(9);
                  this.match(10);
                  if (this.inputState.guessing == 0) {
                     axis = this.getAxisNumber(a.getText());
                  }
               } else if (!_tokenSet_5.member(this.LA(1)) || !_tokenSet_6.member(this.LA(2))) {
                  throw new NoViableAltException(this.LT(1), this.getFilename());
               }

               if (this.inputState.guessing == 0 && axis == -1) {
                  axis = 4;
               }

               label112:
               switch (this.LA(1)) {
                  case 9:
                     if (this.LA(1) == 9 && this.LA(2) == 19) {
                        pfx = this.LT(1);
                        this.match(9);
                        this.match(19);
                     } else if (this.LA(1) != 9 || !_tokenSet_7.member(this.LA(2))) {
                        throw new NoViableAltException(this.LT(1), this.getFilename());
                     }

                     nme = this.LT(1);
                     this.match(9);
                     if (this.inputState.guessing == 0) {
                        test = pfx == null ? this.mFactory.createNameNodeTest(nme.getText()) : this.mFactory.createNameNodeTest(pfx.getText(), nme.getText());
                     }
                     break;
                  case 10:
                  case 13:
                  case 14:
                  case 15:
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
                  case 11:
                     this.match(11);
                     if (this.inputState.guessing == 0) {
                        switch (axis) {
                           case 3:
                              test = this.mFactory.getAttributeNodeTest();
                              break;
                           case 9:
                              test = this.mFactory.getNamespaceNodeTest();
                              break;
                           default:
                              test = this.mFactory.getElementNodeTest();
                        }
                     }
                     break;
                  case 12:
                     this.match(12);
                     this.match(13);
                     switch (this.LA(1)) {
                        case 14:
                           lit = this.LT(1);
                           this.match(14);
                           this.match(15);
                           if (this.inputState.guessing == 0) {
                              test = this.mFactory.createPINodeTest(lit.getText());
                           }
                           break label112;
                        case 15:
                           this.match(15);
                           if (this.inputState.guessing == 0) {
                              test = this.mFactory.getEmptyPINodeTest();
                           }
                           break label112;
                        default:
                           throw new NoViableAltException(this.LT(1), this.getFilename());
                     }
                  case 16:
                     this.match(16);
                     this.match(13);
                     this.match(15);
                     if (this.inputState.guessing == 0) {
                        test = this.mFactory.getCommentNodeTest();
                     }
                     break;
                  case 17:
                     this.match(17);
                     this.match(13);
                     this.match(15);
                     if (this.inputState.guessing == 0) {
                        test = this.mFactory.getTextNodeTest();
                     }
                     break;
                  case 18:
                     this.match(18);
                     this.match(13);
                     this.match(15);
                     if (this.inputState.guessing == 0) {
                        test = this.mFactory.getNodeNodeTest();
                     }
               }

               while(this.LA(1) == 24) {
                  predex = this.predicate();
                  if (this.inputState.guessing == 0) {
                     if (predicates == null) {
                        predicates = new ArrayList();
                     }

                     predicates.add(predex);
                  }
               }
               break;
            case 10:
            case 13:
            case 14:
            case 15:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         if (this.inputState.guessing == 0 && step == null) {
            step = this.mFactory.createStep(axis, test, predicates);
         }
      } catch (RecognitionException var11) {
         if (this.inputState.guessing != 0) {
            throw var11;
         }

         this.reportError(var11);
         this.recover(var11, _tokenSet_8);
      }

      return step;
   }

   public final LocationPathModel relativeLocationPath(StepModel s) throws RecognitionException, TokenStreamException {
      Collection steps = new ArrayList();
      if (s != null) {
         steps.add(s);
      }

      LocationPathModel out = null;

      try {
         s = this.step();
         if (this.inputState.guessing == 0) {
            steps.add(s);
         }

         while((this.LA(1) == 4 || this.LA(1) == 5) && _tokenSet_2.member(this.LA(2))) {
            switch (this.LA(1)) {
               case 4:
                  this.match(4);
                  break;
               case 5:
                  this.match(5);
                  if (this.inputState.guessing == 0) {
                     steps.add(this.mFactory.getDoubleSlashStep());
                  }
                  break;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }

            s = this.step();
            if (this.inputState.guessing == 0) {
               steps.add(s);
            }
         }

         switch (this.LA(1)) {
            case 1:
            case 11:
            case 15:
            case 22:
            case 23:
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
               break;
            case 2:
            case 3:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 24:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 4:
               this.match(4);
               break;
            case 5:
               this.match(5);
               if (this.inputState.guessing == 0) {
                  steps.add(this.mFactory.getDoubleSlashStep());
               }
         }

         if (this.inputState.guessing == 0) {
            out = this.mFactory.createLocationPath(steps);
         }
      } catch (RecognitionException var5) {
         if (this.inputState.guessing != 0) {
            throw var5;
         }

         this.reportError(var5);
         this.recover(var5, _tokenSet_4);
      }

      return out;
   }

   public final ExpressionModel predicate() throws RecognitionException, TokenStreamException {
      ExpressionModel predExpr = null;

      try {
         this.match(24);
         predExpr = this.expr();
         this.match(25);
         if (this.inputState.guessing == 0 && predExpr.getType() == 3) {
            predExpr = this.mFactory.createExpression(this.mFactory.createFunctionExpression("position", (Collection)null), 200, predExpr);
         }
      } catch (RecognitionException var3) {
         if (this.inputState.guessing != 0) {
            throw var3;
         }

         this.reportError(var3);
         this.recover(var3, _tokenSet_7);
      }

      return predExpr;
   }

   public final ExpressionModel orExpr() throws RecognitionException, TokenStreamException {
      ExpressionModel left = null;
      ExpressionModel right = null;

      try {
         left = this.andExpr();

         while(this.LA(1) == 26) {
            this.match(26);
            right = this.andExpr();
            if (this.inputState.guessing == 0) {
               ModelFactory var10002 = this.mFactory;
               left = this.mFactory.createExpression(left, 106, right);
            }
         }
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_1);
      }

      return left;
   }

   public final ExpressionModel primaryExpr() throws RecognitionException, TokenStreamException {
      Token lit = null;
      Token num = null;
      ExpressionModel out = null;

      try {
         switch (this.LA(1)) {
            case 9:
               out = this.functionCall();
               break;
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 13:
               this.match(13);
               out = this.expr();
               this.match(15);
               break;
            case 14:
               lit = this.LT(1);
               this.match(14);
               if (this.inputState.guessing == 0) {
                  out = this.mFactory.createLiteralExpression(lit.getText());
               }
               break;
            case 20:
               num = this.LT(1);
               this.match(20);
               if (this.inputState.guessing == 0) {
                  out = this.mFactory.createConstantExpression(num.getText());
               }
               break;
            case 21:
               out = this.variableReference();
         }
      } catch (RecognitionException var5) {
         if (this.inputState.guessing != 0) {
            throw var5;
         }

         this.reportError(var5);
         this.recover(var5, _tokenSet_9);
      }

      return out;
   }

   public final ExpressionModel variableReference() throws RecognitionException, TokenStreamException {
      Token n = null;
      ExpressionModel out = null;

      try {
         this.match(21);
         n = this.LT(1);
         this.match(9);
         if (this.inputState.guessing == 0) {
            out = this.mFactory.createVariableReference(n.getText());
         }
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_9);
      }

      return out;
   }

   public final ExpressionModel functionCall() throws RecognitionException, TokenStreamException {
      Token name = null;
      ExpressionModel out = null;
      List arguments = new ArrayList();
      ExpressionModel arg = null;

      try {
         name = this.LT(1);
         this.match(9);
         this.match(13);
         switch (this.LA(1)) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 35:
            case 38:
               arg = this.expr();
               if (this.inputState.guessing == 0) {
                  arguments.add(arg);
               }

               while(this.LA(1) == 22) {
                  this.match(22);
                  arg = this.expr();
                  if (this.inputState.guessing == 0) {
                     arguments.add(arg);
                  }
               }
            case 15:
               break;
            case 19:
            case 25:
            case 26:
            case 27:
            case 34:
            case 36:
            case 37:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         this.match(15);
         if (this.inputState.guessing == 0) {
            out = this.mFactory.createFunctionExpression(name.getText(), arguments);
         }
      } catch (RecognitionException var6) {
         if (this.inputState.guessing != 0) {
            throw var6;
         }

         this.reportError(var6);
         this.recover(var6, _tokenSet_9);
      }

      return out;
   }

   public final ExpressionModel unionExpr() throws RecognitionException, TokenStreamException {
      ExpressionModel left = null;
      ExpressionModel right = null;

      try {
         left = this.pathExpr();

         while(this.LA(1) == 23) {
            this.match(23);
            right = this.pathExpr();
            if (this.inputState.guessing == 0) {
               ModelFactory var10002 = this.mFactory;
               left = this.mFactory.createExpression(left, 107, right);
            }
         }
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_10);
      }

      return left;
   }

   public final ExpressionModel pathExpr() throws RecognitionException, TokenStreamException {
      ExpressionModel left = null;
      LocationPathModel path = null;

      try {
         boolean synPredMatched55 = false;
         if (_tokenSet_11.member(this.LA(1)) && _tokenSet_12.member(this.LA(2))) {
            int _m55 = this.mark();
            synPredMatched55 = true;
            ++this.inputState.guessing;

            try {
               label47:
               switch (this.LA(1)) {
                  case 9:
                  case 13:
                     switch (this.LA(1)) {
                        case 9:
                           this.match(9);
                        case 13:
                           this.match(13);
                           break label47;
                        default:
                           throw new NoViableAltException(this.LT(1), this.getFilename());
                     }
                  case 10:
                  case 11:
                  case 12:
                  case 15:
                  case 16:
                  case 17:
                  case 18:
                  case 19:
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
                  case 14:
                     this.match(14);
                     break;
                  case 20:
                     this.match(20);
                     break;
                  case 21:
                     this.match(21);
                     this.match(9);
               }
            } catch (RecognitionException var6) {
               synPredMatched55 = false;
            }

            this.rewind(_m55);
            --this.inputState.guessing;
         }

         if (synPredMatched55) {
            left = this.filterExpr();
            switch (this.LA(1)) {
               case 1:
               case 11:
               case 15:
               case 22:
               case 23:
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
                  break;
               case 2:
               case 3:
               case 6:
               case 7:
               case 8:
               case 9:
               case 10:
               case 12:
               case 13:
               case 14:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 24:
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
               case 4:
                  this.match(4);
                  path = this.relativeLocationPath((StepModel)null);
                  if (this.inputState.guessing == 0) {
                     left = this.mFactory.createComposition((ExpressionModel)left, path);
                  }
                  break;
               case 5:
                  this.match(5);
                  path = this.relativeLocationPath(this.mFactory.getDoubleSlashStep());
                  if (this.inputState.guessing == 0) {
                     left = this.mFactory.createComposition((ExpressionModel)left, path);
                  }
            }
         } else {
            if (!_tokenSet_13.member(this.LA(1)) || !_tokenSet_3.member(this.LA(2))) {
               throw new NoViableAltException(this.LT(1), this.getFilename());
            }

            left = this.locationPath();
         }
      } catch (RecognitionException var7) {
         if (this.inputState.guessing != 0) {
            throw var7;
         }

         this.reportError(var7);
         this.recover(var7, _tokenSet_4);
      }

      return (ExpressionModel)left;
   }

   public final ExpressionModel filterExpr() throws RecognitionException, TokenStreamException {
      ExpressionModel out = null;
      ExpressionModel predEx = null;
      List predList = null;

      try {
         out = this.primaryExpr();

         while(this.LA(1) == 24) {
            predEx = this.predicate();
            if (this.inputState.guessing == 0) {
               if (predList == null) {
                  predList = new ArrayList();
               }

               predList.add(predEx);
            }
         }

         if (this.inputState.guessing == 0 && predList != null) {
            out = this.mFactory.createFilterExpression(out, predList);
         }
      } catch (RecognitionException var5) {
         if (this.inputState.guessing != 0) {
            throw var5;
         }

         this.reportError(var5);
         this.recover(var5, _tokenSet_14);
      }

      return out;
   }

   public final ExpressionModel andExpr() throws RecognitionException, TokenStreamException {
      ExpressionModel left = null;
      ExpressionModel right = null;

      try {
         left = this.equalityExpr();

         while(this.LA(1) == 27) {
            this.match(27);
            right = this.equalityExpr();
            if (this.inputState.guessing == 0) {
               ModelFactory var10002 = this.mFactory;
               left = this.mFactory.createExpression(left, 105, right);
            }
         }
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         }

         this.reportError(var4);
         this.recover(var4, _tokenSet_15);
      }

      return left;
   }

   public final ExpressionModel equalityExpr() throws RecognitionException, TokenStreamException {
      ExpressionModel left = null;
      ExpressionModel right = null;

      try {
         left = this.relationalExpr();

         while(true) {
            while(true) {
               ModelFactory var10002;
               switch (this.LA(1)) {
                  case 28:
                     this.match(28);
                     right = this.relationalExpr();
                     if (this.inputState.guessing == 0) {
                        var10002 = this.mFactory;
                        left = this.mFactory.createExpression(left, 200, right);
                     }
                     break;
                  case 29:
                     this.match(29);
                     right = this.relationalExpr();
                     if (this.inputState.guessing == 0) {
                        var10002 = this.mFactory;
                        left = this.mFactory.createExpression(left, 199, right);
                     }
                     break;
                  default:
                     return left;
               }
            }
         }
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         } else {
            this.reportError(var4);
            this.recover(var4, _tokenSet_16);
            return left;
         }
      }
   }

   public final ExpressionModel relationalExpr() throws RecognitionException, TokenStreamException {
      ExpressionModel left = null;
      ExpressionModel right = null;

      try {
         left = this.additiveExpr();

         while(true) {
            while(true) {
               ModelFactory var10002;
               switch (this.LA(1)) {
                  case 30:
                     this.match(30);
                     right = this.additiveExpr();
                     if (this.inputState.guessing == 0) {
                        var10002 = this.mFactory;
                        left = this.mFactory.createExpression(left, 201, right);
                     }
                     break;
                  case 31:
                     this.match(31);
                     right = this.additiveExpr();
                     if (this.inputState.guessing == 0) {
                        var10002 = this.mFactory;
                        left = this.mFactory.createExpression(left, 204, right);
                     }
                     break;
                  case 32:
                     this.match(32);
                     right = this.additiveExpr();
                     if (this.inputState.guessing == 0) {
                        var10002 = this.mFactory;
                        left = this.mFactory.createExpression(left, 202, right);
                     }
                     break;
                  case 33:
                     this.match(33);
                     right = this.additiveExpr();
                     if (this.inputState.guessing == 0) {
                        var10002 = this.mFactory;
                        left = this.mFactory.createExpression(left, 203, right);
                     }
                     break;
                  default:
                     return left;
               }
            }
         }
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         } else {
            this.reportError(var4);
            this.recover(var4, _tokenSet_17);
            return left;
         }
      }
   }

   public final ExpressionModel additiveExpr() throws RecognitionException, TokenStreamException {
      ExpressionModel left = null;
      ExpressionModel right = null;

      try {
         left = this.multiplicativeExpr();

         while(true) {
            while(true) {
               ModelFactory var10002;
               switch (this.LA(1)) {
                  case 34:
                     this.match(34);
                     right = this.multiplicativeExpr();
                     if (this.inputState.guessing == 0) {
                        var10002 = this.mFactory;
                        left = this.mFactory.createExpression(left, 101, right);
                     }
                     break;
                  case 35:
                     this.match(35);
                     right = this.multiplicativeExpr();
                     if (this.inputState.guessing == 0) {
                        var10002 = this.mFactory;
                        left = this.mFactory.createExpression(left, 100, right);
                     }
                     break;
                  default:
                     return left;
               }
            }
         }
      } catch (RecognitionException var4) {
         if (this.inputState.guessing != 0) {
            throw var4;
         } else {
            this.reportError(var4);
            this.recover(var4, _tokenSet_18);
            return left;
         }
      }
   }

   public final ExpressionModel multiplicativeExpr() throws RecognitionException, TokenStreamException {
      ExpressionModel left = null;
      ExpressionModel right = null;

      try {
         boolean synPredMatched98 = false;
         if (_tokenSet_19.member(this.LA(1)) && _tokenSet_20.member(this.LA(2))) {
            int _m98 = this.mark();
            synPredMatched98 = true;
            ++this.inputState.guessing;

            try {
               switch (this.LA(1)) {
                  case 9:
                     this.match(9);
                     break;
                  case 11:
                     this.match(11);
                     break;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            } catch (RecognitionException var6) {
               synPredMatched98 = false;
            }

            this.rewind(_m98);
            --this.inputState.guessing;
         }

         if (synPredMatched98) {
            switch (this.LA(1)) {
               case 4:
                  this.match(4);
                  break;
               case 5:
                  this.match(5);
                  break;
               case 6:
               case 7:
               case 9:
               case 11:
               case 12:
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
               case 25:
               case 26:
               case 27:
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
               case 8:
                  this.match(8);
                  break;
               case 10:
                  this.match(10);
                  break;
               case 13:
                  this.match(13);
                  break;
               case 22:
                  this.match(22);
                  break;
               case 23:
                  this.match(23);
                  break;
               case 24:
                  this.match(24);
                  break;
               case 28:
                  this.match(28);
                  break;
               case 29:
                  this.match(29);
                  break;
               case 30:
                  this.match(30);
                  break;
               case 31:
                  this.match(31);
                  break;
               case 32:
                  this.match(32);
                  break;
               case 33:
                  this.match(33);
            }

            left = this.unaryExpr();
         } else {
            if (_tokenSet_20.member(this.LA(1)) && _tokenSet_21.member(this.LA(2))) {
               left = this.unaryExpr();

               while(true) {
                  while(true) {
                     ModelFactory var10002;
                     switch (this.LA(1)) {
                        case 11:
                           this.match(11);
                           right = this.unaryExpr();
                           if (this.inputState.guessing == 0) {
                              var10002 = this.mFactory;
                              left = this.mFactory.createExpression(left, 102, right);
                           }
                           break;
                        case 36:
                           this.match(36);
                           right = this.unaryExpr();
                           if (this.inputState.guessing == 0) {
                              var10002 = this.mFactory;
                              left = this.mFactory.createExpression(left, 103, right);
                           }
                           break;
                        case 37:
                           this.match(37);
                           right = this.unaryExpr();
                           if (this.inputState.guessing == 0) {
                              var10002 = this.mFactory;
                              left = this.mFactory.createExpression(left, 104, right);
                           }
                           break;
                        default:
                           return left;
                     }
                  }
               }
            }

            throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var7) {
         if (this.inputState.guessing != 0) {
            throw var7;
         }

         this.reportError(var7);
         this.recover(var7, _tokenSet_22);
      }

      return left;
   }

   public final ExpressionModel unaryExpr() throws RecognitionException, TokenStreamException {
      ExpressionModel left = null;

      try {
         switch (this.LA(1)) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
               left = this.unionExpr();
               break;
            case 10:
            case 15:
            case 19:
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
            case 36:
            case 37:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 35:
            case 38:
               switch (this.LA(1)) {
                  case 35:
                     this.match(35);
                     break;
                  case 38:
                     this.match(38);
                     break;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }

               left = this.unionExpr();
               if (this.inputState.guessing == 0) {
                  left = this.mFactory.createNegativeExpression(left);
               }
         }
      } catch (RecognitionException var3) {
         if (this.inputState.guessing != 0) {
            throw var3;
         }

         this.reportError(var3);
         this.recover(var3, _tokenSet_10);
      }

      return left;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{2L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[]{37781506L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[]{465856L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[]{274874744818L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_4() {
      long[] data = new long[]{274856970242L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_5() {
      long[] data = new long[]{465408L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_6() {
      long[] data = new long[]{274874743794L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_7() {
      long[] data = new long[]{274874211314L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_8() {
      long[] data = new long[]{274857434098L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_9() {
      long[] data = new long[]{274873747506L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_10() {
      long[] data = new long[]{274848581634L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_11() {
      long[] data = new long[]{3170816L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_12() {
      long[] data = new long[]{549755289586L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_13() {
      long[] data = new long[]{465904L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_14() {
      long[] data = new long[]{274856970290L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_15() {
      long[] data = new long[]{104890370L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_16() {
      long[] data = new long[]{239108098L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_17() {
      long[] data = new long[]{1044414466L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_18() {
      long[] data = new long[]{17150541826L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_19() {
      long[] data = new long[]{16940803376L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_20() {
      long[] data = new long[]{309241281520L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_21() {
      long[] data = new long[]{549755813874L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_22() {
      long[] data = new long[]{68690149378L, 0L};
      return data;
   }
}
