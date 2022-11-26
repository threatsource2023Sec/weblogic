package weblogic.diagnostics.query;

import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.CommonAST;
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
import java.util.Hashtable;

public class QueryExpressionParser extends LLkParser implements QueryExpressionParserTokenTypes {
   private VariableIndexResolver variableIndexResolver;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "CONSTANT_BOOLEAN", "CONSTANT_NUMBER", "STRING_LITERAL", "VARIABLE_NAME", "SET_NODE", "NESTED_LOGICAL", "NESTED_ARITHMETIC", "\"AND\"", "\"OR\"", "\"NOT\"", "\"LIKE\"", "\"MATCHES\"", "\"IN\"", "END_OF_QUERY", "LT", "GT", "LE", "GE", "EQ", "NE", "LPAREN", "RPAREN", "BITWISE_OR", "BITWISE_AND", "COMMA", "WS", "SPACE", "PLUS", "MINUS", "DIGIT", "ASCII_VARNAME_START", "UNICODE_CHAR", "ALIAS_DELIMITER_START", "DOT", "NUMBER_SUFFIXES", "DOUBLE_SUFFIX", "FLOAT_SUFFIX", "LONG_SUFFIX", "EXPONENT"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

   public void setVariableIndexResolver(VariableIndexResolver vir) {
      this.variableIndexResolver = vir;
   }

   protected QueryExpressionParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
      this.buildTokenTypeASTClassMap();
      this.astFactory = new ASTFactory(this.getTokenTypeToASTClassMap());
   }

   public QueryExpressionParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 1);
   }

   protected QueryExpressionParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
      this.buildTokenTypeASTClassMap();
      this.astFactory = new ASTFactory(this.getTokenTypeToASTClassMap());
   }

   public QueryExpressionParser(TokenStream lexer) {
      this((TokenStream)lexer, 1);
   }

   public QueryExpressionParser(ParserSharedInputState state) {
      super(state, 1);
      this.tokenNames = _tokenNames;
      this.buildTokenTypeASTClassMap();
      this.astFactory = new ASTFactory(this.getTokenTypeToASTClassMap());
   }

   public final void booleanExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST booleanExpression_AST = null;
      this.logicalExpression();
      this.astFactory.addASTChild(currentAST, this.returnAST);
      this.match(17);
      booleanExpression_AST = currentAST.root;
      this.returnAST = booleanExpression_AST;
   }

   public final void logicalExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST logicalExpression_AST = null;
      this.orExpression();
      this.astFactory.addASTChild(currentAST, this.returnAST);
      logicalExpression_AST = currentAST.root;
      this.returnAST = logicalExpression_AST;
   }

   public final void orExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST orExpression_AST = null;
      this.andExpression();
      this.astFactory.addASTChild(currentAST, this.returnAST);

      while(this.LA(1) == 12) {
         AST tmp2_AST = null;
         tmp2_AST = this.astFactory.create(this.LT(1));
         this.astFactory.makeASTRoot(currentAST, tmp2_AST);
         this.match(12);
         this.andExpression();
         this.astFactory.addASTChild(currentAST, this.returnAST);
      }

      orExpression_AST = currentAST.root;
      this.returnAST = orExpression_AST;
   }

   public final void andExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST andExpression_AST = null;
      this.relationalExpression();
      this.astFactory.addASTChild(currentAST, this.returnAST);

      while(this.LA(1) == 11) {
         AST tmp3_AST = null;
         tmp3_AST = this.astFactory.create(this.LT(1));
         this.astFactory.makeASTRoot(currentAST, tmp3_AST);
         this.match(11);
         this.relationalExpression();
         this.astFactory.addASTChild(currentAST, this.returnAST);
      }

      andExpression_AST = currentAST.root;
      this.returnAST = andExpression_AST;
   }

   public final void relationalExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST relationalExpression_AST = null;
      boolean synPredMatched11 = false;
      if (_tokenSet_0.member(this.LA(1))) {
         int _m11 = this.mark();
         synPredMatched11 = true;
         ++this.inputState.guessing;

         try {
            this.atom();
            this.match(16);
         } catch (RecognitionException var12) {
            synPredMatched11 = false;
         }

         this.rewind(_m11);
         --this.inputState.guessing;
      }

      if (synPredMatched11) {
         this.atom();
         this.astFactory.addASTChild(currentAST, this.returnAST);
         AST tmp4_AST = null;
         tmp4_AST = this.astFactory.create(this.LT(1));
         this.astFactory.makeASTRoot(currentAST, tmp4_AST);
         this.match(16);
         this.setNode();
         this.astFactory.addASTChild(currentAST, this.returnAST);
         relationalExpression_AST = currentAST.root;
      } else {
         boolean synPredMatched14 = false;
         if (_tokenSet_0.member(this.LA(1))) {
            int _m14 = this.mark();
            synPredMatched14 = true;
            ++this.inputState.guessing;

            try {
               this.atom();
               this.match(14);
            } catch (RecognitionException var11) {
               synPredMatched14 = false;
            }

            this.rewind(_m14);
            --this.inputState.guessing;
         }

         if (synPredMatched14) {
            this.atom();
            this.astFactory.addASTChild(currentAST, this.returnAST);
            AST tmp6_AST = null;
            tmp6_AST = this.astFactory.create(this.LT(1));
            this.astFactory.makeASTRoot(currentAST, tmp6_AST);
            this.match(14);
            tmp6_AST = null;
            ConstantStringNode tmp6_AST = (ConstantStringNode)this.astFactory.create(this.LT(1), "weblogic.diagnostics.query.ConstantStringNode");
            this.astFactory.addASTChild(currentAST, tmp6_AST);
            this.match(6);
            relationalExpression_AST = currentAST.root;
         } else {
            boolean synPredMatched17 = false;
            if (_tokenSet_0.member(this.LA(1))) {
               int _m17 = this.mark();
               synPredMatched17 = true;
               ++this.inputState.guessing;

               try {
                  this.atom();
                  this.match(15);
               } catch (RecognitionException var10) {
                  synPredMatched17 = false;
               }

               this.rewind(_m17);
               --this.inputState.guessing;
            }

            if (synPredMatched17) {
               this.atom();
               this.astFactory.addASTChild(currentAST, this.returnAST);
               AST tmp8_AST = null;
               tmp8_AST = this.astFactory.create(this.LT(1));
               this.astFactory.makeASTRoot(currentAST, tmp8_AST);
               this.match(15);
               tmp8_AST = null;
               ConstantStringNode tmp8_AST = (ConstantStringNode)this.astFactory.create(this.LT(1), "weblogic.diagnostics.query.ConstantStringNode");
               this.astFactory.addASTChild(currentAST, tmp8_AST);
               this.match(6);
               relationalExpression_AST = currentAST.root;
            } else {
               boolean synPredMatched20 = false;
               if (_tokenSet_0.member(this.LA(1))) {
                  int _m20 = this.mark();
                  synPredMatched20 = true;
                  ++this.inputState.guessing;

                  try {
                     this.atom();
                  } catch (RecognitionException var9) {
                     synPredMatched20 = false;
                  }

                  this.rewind(_m20);
                  --this.inputState.guessing;
               }

               if (synPredMatched20) {
                  this.atom();
                  this.astFactory.addASTChild(currentAST, this.returnAST);
                  AST tmp14_AST;
                  switch (this.LA(1)) {
                     case 18:
                        tmp14_AST = null;
                        tmp14_AST = this.astFactory.create(this.LT(1));
                        this.astFactory.makeASTRoot(currentAST, tmp14_AST);
                        this.match(18);
                        break;
                     case 19:
                        tmp14_AST = null;
                        tmp14_AST = this.astFactory.create(this.LT(1));
                        this.astFactory.makeASTRoot(currentAST, tmp14_AST);
                        this.match(19);
                        break;
                     case 20:
                        tmp14_AST = null;
                        tmp14_AST = this.astFactory.create(this.LT(1));
                        this.astFactory.makeASTRoot(currentAST, tmp14_AST);
                        this.match(20);
                        break;
                     case 21:
                        tmp14_AST = null;
                        tmp14_AST = this.astFactory.create(this.LT(1));
                        this.astFactory.makeASTRoot(currentAST, tmp14_AST);
                        this.match(21);
                        break;
                     case 22:
                        tmp14_AST = null;
                        tmp14_AST = this.astFactory.create(this.LT(1));
                        this.astFactory.makeASTRoot(currentAST, tmp14_AST);
                        this.match(22);
                        break;
                     case 23:
                        tmp14_AST = null;
                        tmp14_AST = this.astFactory.create(this.LT(1));
                        this.astFactory.makeASTRoot(currentAST, tmp14_AST);
                        this.match(23);
                        break;
                     default:
                        throw new NoViableAltException(this.LT(1), this.getFilename());
                  }

                  this.atom();
                  this.astFactory.addASTChild(currentAST, this.returnAST);
                  relationalExpression_AST = currentAST.root;
               } else {
                  if (this.LA(1) != 13 && this.LA(1) != 24) {
                     throw new NoViableAltException(this.LT(1), this.getFilename());
                  }

                  this.unaryExpression();
                  this.astFactory.addASTChild(currentAST, this.returnAST);
                  relationalExpression_AST = currentAST.root;
               }
            }
         }
      }

      this.returnAST = relationalExpression_AST;
   }

   public final void atom() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST atom_AST = null;
      ConstantStringNode tmp16_AST;
      switch (this.LA(1)) {
         case 4:
            tmp16_AST = null;
            ConstantBooleanNode tmp15_AST = (ConstantBooleanNode)this.astFactory.create(this.LT(1), "weblogic.diagnostics.query.ConstantBooleanNode");
            this.astFactory.addASTChild(currentAST, tmp15_AST);
            this.match(4);
            atom_AST = currentAST.root;
            break;
         case 5:
         case 7:
         case 24:
            this.arithMeticExpression();
            this.astFactory.addASTChild(currentAST, this.returnAST);
            atom_AST = currentAST.root;
            break;
         case 6:
            tmp16_AST = null;
            tmp16_AST = (ConstantStringNode)this.astFactory.create(this.LT(1), "weblogic.diagnostics.query.ConstantStringNode");
            this.astFactory.addASTChild(currentAST, tmp16_AST);
            this.match(6);
            atom_AST = currentAST.root;
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      this.returnAST = atom_AST;
   }

   public final void setNode() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST setNode_AST = null;
      this.match(24);
      this.constantNumberOrString();
      this.astFactory.addASTChild(currentAST, this.returnAST);

      while(this.LA(1) == 28) {
         this.match(28);
         this.constantNumberOrString();
         this.astFactory.addASTChild(currentAST, this.returnAST);
      }

      this.match(25);
      if (this.inputState.guessing == 0) {
         setNode_AST = currentAST.root;
         setNode_AST = this.astFactory.make((new ASTArray(2)).add((SetNode)this.astFactory.create(8, "SetNode", "weblogic.diagnostics.query.SetNode")).add(setNode_AST));
         currentAST.root = setNode_AST;
         currentAST.child = setNode_AST != null && setNode_AST.getFirstChild() != null ? setNode_AST.getFirstChild() : setNode_AST;
         currentAST.advanceChildToEnd();
      }

      setNode_AST = currentAST.root;
      this.returnAST = setNode_AST;
   }

   public final void unaryExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST unaryExpression_AST = null;
      switch (this.LA(1)) {
         case 13:
            AST tmp20_AST = null;
            tmp20_AST = this.astFactory.create(this.LT(1));
            this.astFactory.makeASTRoot(currentAST, tmp20_AST);
            this.match(13);
            this.nestedLogicalExpression();
            this.astFactory.addASTChild(currentAST, this.returnAST);
            unaryExpression_AST = currentAST.root;
            break;
         case 24:
            this.nestedLogicalExpression();
            this.astFactory.addASTChild(currentAST, this.returnAST);
            unaryExpression_AST = currentAST.root;
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      this.returnAST = unaryExpression_AST;
   }

   public final void nestedLogicalExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST nestedLogicalExpression_AST = null;
      this.match(24);
      this.logicalExpression();
      this.astFactory.addASTChild(currentAST, this.returnAST);
      this.match(25);
      if (this.inputState.guessing == 0) {
         nestedLogicalExpression_AST = currentAST.root;
         nestedLogicalExpression_AST = this.astFactory.make((new ASTArray(2)).add((CommonAST)this.astFactory.create(9, "NestedLogical", "antlr.CommonAST")).add(nestedLogicalExpression_AST));
         currentAST.root = nestedLogicalExpression_AST;
         currentAST.child = nestedLogicalExpression_AST != null && nestedLogicalExpression_AST.getFirstChild() != null ? nestedLogicalExpression_AST.getFirstChild() : nestedLogicalExpression_AST;
         currentAST.advanceChildToEnd();
      }

      nestedLogicalExpression_AST = currentAST.root;
      this.returnAST = nestedLogicalExpression_AST;
   }

   public final void arithMeticExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST arithMeticExpression_AST = null;
      this.bitWiseOrExpression();
      this.astFactory.addASTChild(currentAST, this.returnAST);
      arithMeticExpression_AST = currentAST.root;
      this.returnAST = arithMeticExpression_AST;
   }

   public final void bitWiseOrExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST bitWiseOrExpression_AST = null;
      this.bitWiseAndExpression();
      this.astFactory.addASTChild(currentAST, this.returnAST);

      while(this.LA(1) == 26) {
         AST tmp23_AST = null;
         tmp23_AST = this.astFactory.create(this.LT(1));
         this.astFactory.makeASTRoot(currentAST, tmp23_AST);
         this.match(26);
         this.bitWiseAndExpression();
         this.astFactory.addASTChild(currentAST, this.returnAST);
      }

      bitWiseOrExpression_AST = currentAST.root;
      this.returnAST = bitWiseOrExpression_AST;
   }

   public final void bitWiseAndExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST bitWiseAndExpression_AST = null;
      this.mathExpression();
      this.astFactory.addASTChild(currentAST, this.returnAST);

      while(this.LA(1) == 27) {
         AST tmp24_AST = null;
         tmp24_AST = this.astFactory.create(this.LT(1));
         this.astFactory.makeASTRoot(currentAST, tmp24_AST);
         this.match(27);
         this.mathExpression();
         this.astFactory.addASTChild(currentAST, this.returnAST);
      }

      bitWiseAndExpression_AST = currentAST.root;
      this.returnAST = bitWiseAndExpression_AST;
   }

   public final void mathExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST mathExpression_AST = null;
      switch (this.LA(1)) {
         case 5:
         case 7:
            this.numberOrVariableAtom();
            this.astFactory.addASTChild(currentAST, this.returnAST);
            mathExpression_AST = currentAST.root;
            break;
         case 24:
            this.nestedArithmeticExpression();
            this.astFactory.addASTChild(currentAST, this.returnAST);
            mathExpression_AST = currentAST.root;
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      this.returnAST = mathExpression_AST;
   }

   public final void numberOrVariableAtom() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST numberOrVariableAtom_AST = null;
      switch (this.LA(1)) {
         case 5:
            ConstantNumberNode tmp25_AST = null;
            tmp25_AST = (ConstantNumberNode)this.astFactory.create(this.LT(1), "weblogic.diagnostics.query.ConstantNumberNode");
            this.astFactory.addASTChild(currentAST, tmp25_AST);
            this.match(5);
            numberOrVariableAtom_AST = currentAST.root;
            break;
         case 7:
            this.variableNode();
            this.astFactory.addASTChild(currentAST, this.returnAST);
            numberOrVariableAtom_AST = currentAST.root;
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      this.returnAST = numberOrVariableAtom_AST;
   }

   public final void nestedArithmeticExpression() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST nestedArithmeticExpression_AST = null;
      this.match(24);
      this.arithMeticExpression();
      this.astFactory.addASTChild(currentAST, this.returnAST);
      this.match(25);
      if (this.inputState.guessing == 0) {
         nestedArithmeticExpression_AST = currentAST.root;
         nestedArithmeticExpression_AST = this.astFactory.make((new ASTArray(2)).add((CommonAST)this.astFactory.create(10, "NestedArithmetic", "antlr.CommonAST")).add(nestedArithmeticExpression_AST));
         currentAST.root = nestedArithmeticExpression_AST;
         currentAST.child = nestedArithmeticExpression_AST != null && nestedArithmeticExpression_AST.getFirstChild() != null ? nestedArithmeticExpression_AST.getFirstChild() : nestedArithmeticExpression_AST;
         currentAST.advanceChildToEnd();
      }

      nestedArithmeticExpression_AST = currentAST.root;
      this.returnAST = nestedArithmeticExpression_AST;
   }

   public final void variableNode() throws RecognitionException, TokenStreamException, UnknownVariableException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST variableNode_AST = null;
      Token v = null;
      VariableNode v_AST = null;
      v = this.LT(1);
      v_AST = (VariableNode)this.astFactory.create(v, "weblogic.diagnostics.query.VariableNode");
      this.astFactory.addASTChild(currentAST, v_AST);
      this.match(7);
      if (this.inputState.guessing == 0 && this.variableIndexResolver != null) {
         v_AST.setVariableIndex(this.variableIndexResolver.getVariableIndex(v.getText()));
      }

      variableNode_AST = currentAST.root;
      this.returnAST = variableNode_AST;
   }

   public final void constantNumberOrString() throws RecognitionException, TokenStreamException {
      this.returnAST = null;
      ASTPair currentAST = new ASTPair();
      AST constantNumberOrString_AST = null;
      ConstantStringNode tmp29_AST;
      switch (this.LA(1)) {
         case 5:
            tmp29_AST = null;
            ConstantNumberNode tmp28_AST = (ConstantNumberNode)this.astFactory.create(this.LT(1), "weblogic.diagnostics.query.ConstantNumberNode");
            this.astFactory.addASTChild(currentAST, tmp28_AST);
            this.match(5);
            constantNumberOrString_AST = currentAST.root;
            break;
         case 6:
            tmp29_AST = null;
            tmp29_AST = (ConstantStringNode)this.astFactory.create(this.LT(1), "weblogic.diagnostics.query.ConstantStringNode");
            this.astFactory.addASTChild(currentAST, tmp29_AST);
            this.match(6);
            constantNumberOrString_AST = currentAST.root;
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      this.returnAST = constantNumberOrString_AST;
   }

   protected void buildTokenTypeASTClassMap() {
      this.tokenTypeToASTClassMap = new Hashtable();
      this.tokenTypeToASTClassMap.put(new Integer(4), ConstantBooleanNode.class);
      this.tokenTypeToASTClassMap.put(new Integer(5), ConstantNumberNode.class);
      this.tokenTypeToASTClassMap.put(new Integer(6), ConstantStringNode.class);
      this.tokenTypeToASTClassMap.put(new Integer(7), VariableNode.class);
      this.tokenTypeToASTClassMap.put(new Integer(8), SetNode.class);
      this.tokenTypeToASTClassMap.put(new Integer(9), CommonAST.class);
      this.tokenTypeToASTClassMap.put(new Integer(10), CommonAST.class);
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{16777456L, 0L};
      return data;
   }
}
