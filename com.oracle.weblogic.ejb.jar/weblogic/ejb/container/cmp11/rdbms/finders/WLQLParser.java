package weblogic.ejb.container.cmp11.rdbms.finders;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import weblogic.ejb.container.ejbc.EJBCException;

public class WLQLParser extends LLkParser implements WLQLParserTokenTypes {
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "LPAREN", "RPAREN", "AND", "OR", "NOT", "EQUALS", "LT", "GT", "LTEQ", "GTEQ", "\"like\"", "\"isNull\"", "\"isNotNull\"", "\"orderBy\"", "VARIABLE", "SPECIAL", "ID", "STRING", "BACKSTRING", "NUMBER", "SLASH", "BACKTICK", "SSTRING", "DSTRING", "DASH", "DOT", "INT", "REAL", "UNICODE_RANGE", "WS", "COMMENT", "DIGIT", "LETTER"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());

   public WLQLParser() {
      super(2);
   }

   public WLQLExpression parse(String s) throws EJBCException {
      try {
         byte[] bytes = s.getBytes();
         ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
         WLQLLexer lexer = new WLQLLexer(bais);
         TokenBuffer tokBuf = new TokenBuffer(lexer);
         this.setTokenBuffer(tokBuf);
         return this.expression();
      } catch (Exception var6) {
         throw new EJBCException("Couldn't parse '" + s + "' into WLQLExpression.", var6);
      }
   }

   protected WLQLParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public WLQLParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 2);
   }

   protected WLQLParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public WLQLParser(TokenStream lexer) {
      this((TokenStream)lexer, 2);
   }

   public WLQLParser(ParserSharedInputState state) {
      super(state, 2);
      this.tokenNames = _tokenNames;
   }

   public final WLQLExpression expression() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      switch (this.LA(1)) {
         case 4:
            this.match(4);
            switch (this.LA(1)) {
               case 6:
                  expr = this.and();
                  break;
               case 7:
                  expr = this.or();
                  break;
               case 8:
                  expr = this.not();
                  break;
               case 9:
                  expr = this.eq();
                  break;
               case 10:
                  expr = this.less_than();
                  break;
               case 11:
                  expr = this.greater_than();
                  break;
               case 12:
                  expr = this.less_than_or_equal();
                  break;
               case 13:
                  expr = this.greater_than_or_equal();
                  break;
               case 14:
                  expr = this.like();
                  break;
               case 15:
                  expr = this.isnull();
                  break;
               case 16:
                  expr = this.isnotnull();
                  break;
               case 17:
                  expr = this.orderby();
                  break;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }

            this.match(5);
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
         case 14:
         case 15:
         case 16:
         case 17:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 18:
            expr = this.variable();
            break;
         case 19:
            expr = this.special();
            break;
         case 20:
            expr = this.id();
            break;
         case 21:
         case 22:
            expr = this.string();
            break;
         case 23:
            expr = this.number();
      }

      return expr;
   }

   public final WLQLExpression and() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      new ArrayList();
      this.match(6);
      List l = this.expression_list();
      expr = new WLQLExpression(0, l);
      return expr;
   }

   public final WLQLExpression or() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      new ArrayList();
      this.match(7);
      List l = this.expression_list();
      expr = new WLQLExpression(1, l);
      return expr;
   }

   public final WLQLExpression not() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      WLQLExpression e = null;
      this.match(8);
      e = this.expression();
      expr = new WLQLExpression(2, e);
      return expr;
   }

   public final WLQLExpression eq() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      new ArrayList();
      this.match(9);
      List l = this.expression_list();
      expr = new WLQLExpression(3, l);
      return expr;
   }

   public final WLQLExpression less_than() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      WLQLExpression e1 = null;
      WLQLExpression e2 = null;
      this.match(10);
      e1 = this.expression();
      e2 = this.expression();
      expr = new WLQLExpression(4, e1, e2);
      return expr;
   }

   public final WLQLExpression greater_than() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      WLQLExpression e1 = null;
      WLQLExpression e2 = null;
      this.match(11);
      e1 = this.expression();
      e2 = this.expression();
      expr = new WLQLExpression(5, e1, e2);
      return expr;
   }

   public final WLQLExpression less_than_or_equal() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      WLQLExpression e1 = null;
      WLQLExpression e2 = null;
      this.match(12);
      e1 = this.expression();
      e2 = this.expression();
      expr = new WLQLExpression(6, e1, e2);
      return expr;
   }

   public final WLQLExpression greater_than_or_equal() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      WLQLExpression e1 = null;
      WLQLExpression e2 = null;
      this.match(13);
      e1 = this.expression();
      e2 = this.expression();
      expr = new WLQLExpression(7, e1, e2);
      return expr;
   }

   public final WLQLExpression like() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      WLQLExpression e1 = null;
      WLQLExpression e2 = null;
      this.match(14);
      e1 = this.expression();
      e2 = this.expression();
      expr = new WLQLExpression(8, e1, e2);
      return expr;
   }

   public final WLQLExpression isnull() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      WLQLExpression e = null;
      this.match(15);
      e = this.expression();
      expr = new WLQLExpression(14, e);
      return expr;
   }

   public final WLQLExpression isnotnull() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      WLQLExpression e = null;
      this.match(16);
      e = this.expression();
      expr = new WLQLExpression(15, e);
      return expr;
   }

   public final WLQLExpression orderby() throws RecognitionException, TokenStreamException {
      WLQLExpression expr = null;
      WLQLExpression clause = null;
      WLQLExpression e = null;
      this.match(17);
      clause = this.string();
      e = this.expression();
      expr = new WLQLExpression(16, clause, e);
      return expr;
   }

   public final WLQLExpression variable() throws RecognitionException, TokenStreamException {
      Token v = null;
      WLQLExpression expr = null;
      v = this.LT(1);
      this.match(18);
      expr = new WLQLExpression(13, v.getText());
      return expr;
   }

   public final WLQLExpression special() throws RecognitionException, TokenStreamException {
      Token id = null;
      WLQLExpression expr = null;
      WLQLExpression lit = null;
      List l = new ArrayList();
      id = this.LT(1);
      this.match(19);
      lit = this.string();
      l.add(lit);
      expr = new WLQLExpression(id.getText(), l);
      return expr;
   }

   public final WLQLExpression id() throws RecognitionException, TokenStreamException {
      Token i = null;
      WLQLExpression expr = null;
      i = this.LT(1);
      this.match(20);
      expr = new WLQLExpression(9, i.getText());
      return expr;
   }

   public final WLQLExpression string() throws RecognitionException, TokenStreamException {
      Token s = null;
      Token bs = null;
      WLQLExpression expr = null;
      switch (this.LA(1)) {
         case 21:
            s = this.LT(1);
            this.match(21);
            expr = new WLQLExpression(10, s.getText());
            break;
         case 22:
            bs = this.LT(1);
            this.match(22);
            expr = new WLQLExpression(10, bs.getText());
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return expr;
   }

   public final WLQLExpression number() throws RecognitionException, TokenStreamException {
      Token n = null;
      WLQLExpression expr = null;
      n = this.LT(1);
      this.match(23);
      expr = new WLQLExpression(11, n.getText());
      return expr;
   }

   public final List expression_list() throws RecognitionException, TokenStreamException {
      List l = new ArrayList();
      WLQLExpression e = null;

      int _cnt23;
      for(_cnt23 = 0; _tokenSet_0.member(this.LA(1)); ++_cnt23) {
         e = this.expression();
         l.add(e);
      }

      if (_cnt23 >= 1) {
         return l;
      } else {
         throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{16515088L, 0L};
      return data;
   }
}
