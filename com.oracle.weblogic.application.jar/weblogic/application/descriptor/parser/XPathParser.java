package weblogic.application.descriptor.parser;

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

public class XPathParser extends LLkParser implements XPathParserTokenTypes {
   private Collection parseErrors;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "SLASH", "NCNAME", "LBRACKET", "COMMA", "RBRACKET", "EQUALS", "NUMERIC_LITERAL", "STRING_LITERAL", "WS", "DIGIT", "LETTER", "DASH", "SINGLE_QUOTE_LITERAL", "DOUBLE_QUOTE_LITERAL"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());

   public Collection getErrors() {
      return this.parseErrors;
   }

   public void reportError(RecognitionException re) {
      if (this.parseErrors == null) {
         this.parseErrors = new ArrayList();
      }

      this.parseErrors.add(re);
   }

   public void reportError(Exception re) {
      if (this.parseErrors == null) {
         this.parseErrors = new ArrayList();
      }

      this.parseErrors.add(re);
   }

   public void reportError(String msg) {
      if (this.parseErrors == null) {
         this.parseErrors = new ArrayList();
      }

      this.parseErrors.add(msg);
   }

   protected XPathParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.parseErrors = null;
      this.tokenNames = _tokenNames;
   }

   public XPathParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 2);
   }

   protected XPathParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.parseErrors = null;
      this.tokenNames = _tokenNames;
   }

   public XPathParser(TokenStream lexer) {
      this((TokenStream)lexer, 2);
   }

   public XPathParser(ParserSharedInputState state) {
      super(state, 2);
      this.parseErrors = null;
      this.tokenNames = _tokenNames;
   }

   public final XPathParseResults start() throws RecognitionException, TokenStreamException {
      XPathParseResults results = null;

      try {
         results = this.xPathExpr();
      } catch (RecognitionException var3) {
         this.reportError(var3);
         this.recover(var3, _tokenSet_0);
      }

      return results;
   }

   public final XPathParseResults xPathExpr() throws RecognitionException, TokenStreamException {
      XPathParseResults results = new XPathParseResults();
      StepExpression s = null;

      try {
         this.match(4);
         s = this.stepExpr();
         results.addStep(s);

         while(this.LA(1) == 4) {
            this.match(4);
            s = this.stepExpr();
            results.addStep(s);
         }
      } catch (RecognitionException var4) {
         this.reportError(var4);
         this.recover(var4, _tokenSet_0);
      }

      return results;
   }

   public final StepExpression stepExpr() throws RecognitionException, TokenStreamException {
      Token n = null;
      StepExpression step = new StepExpression();
      PredicateInfo p = null;

      try {
         switch (this.LA(1)) {
            case 5:
               n = this.LT(1);
               this.match(5);
               step.setPathName(n.getText());
               switch (this.LA(1)) {
                  case 6:
                     p = this.predicate();
                  case 1:
                  case 4:
                     step.addPredicate(p);
                     return step;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            case 6:
               p = this.predicate();
               step.addPredicate(p);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.recover(var5, _tokenSet_1);
      }

      return step;
   }

   public final PredicateInfo predicate() throws RecognitionException, TokenStreamException {
      PredicateInfo pred = new PredicateInfo();
      PredicateExpression e = null;

      try {
         this.match(6);
         e = this.expr();
         pred.addExpression(e);

         while(this.LA(1) == 7) {
            this.match(7);
            e = this.expr();
            pred.addExpression(e);
         }

         this.match(8);
      } catch (RecognitionException var4) {
         this.reportError(var4);
         this.recover(var4, _tokenSet_1);
      }

      return pred;
   }

   public final PredicateExpression expr() throws RecognitionException, TokenStreamException {
      Token n = null;
      Token nl = null;
      PredicateExpression pex = new PredicateExpression();
      String l = null;
      String idx = null;

      try {
         switch (this.LA(1)) {
            case 5:
               n = this.LT(1);
               this.match(5);
               pex.setKeyName(n.getText());
               this.match(9);
               l = this.literal();
               pex.setLiteralValue(l);
               break;
            case 10:
               nl = this.LT(1);
               this.match(10);
               pex.setIndexValue(nl.getText());
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var7) {
         this.reportError(var7);
         this.recover(var7, _tokenSet_2);
      }

      return pex;
   }

   public final String literal() throws RecognitionException, TokenStreamException {
      Token s = null;
      Token n = null;
      String litvalue = null;

      try {
         switch (this.LA(1)) {
            case 10:
               n = this.LT(1);
               this.match(10);
               litvalue = n.getText();
               break;
            case 11:
               s = this.LT(1);
               this.match(11);
               litvalue = s.getText();
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (RecognitionException var5) {
         this.reportError(var5);
         this.recover(var5, _tokenSet_2);
      }

      return litvalue;
   }

   public final String numericLiteral() throws RecognitionException, TokenStreamException {
      Token n = null;
      String numlit = null;

      try {
         n = this.LT(1);
         this.match(10);
         numlit = n.getText();
      } catch (RecognitionException var4) {
         this.reportError(var4);
         this.recover(var4, _tokenSet_0);
      }

      return numlit;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{2L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[]{18L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[]{384L, 0L};
      return data;
   }
}
