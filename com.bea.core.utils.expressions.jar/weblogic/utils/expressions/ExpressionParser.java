package weblogic.utils.expressions;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import java.io.StringReader;
import java.util.ArrayList;

public class ExpressionParser extends LLkParser implements ExpressionParserTokenTypes {
   private VariableBinder variableBinder;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"or\"", "\"and\"", "\"not\"", "NTEQ", "EQ", "LT", "GT", "LTEQ", "GTEQ", "\"is\"", "\"null\"", "\"between\"", "\"like\"", "\"escape\"", "\"in\"", "LPAREN", "RPAREN", "COMMA", "PLUS", "MINUS", "TIMES", "DIV", "\"jms_bea_select\"", "ID", "STRING", "\"true\"", "\"false\"", "INTEGER", "FLOAT", "WS", "DIGIT", "HEX_DIGIT", "LETTER", "UNICODEJAVAIDSTART", "UNICODEJAVAIDPART", "SPECIAL", "EXPONENT", "SUFFIX"};

   public ExpressionParser() {
      super(2);
   }

   public Expression parse(String s) throws ExpressionParserException {
      return this.parse(s, (VariableBinder)null);
   }

   public Expression parse(String s, VariableBinder variableBinder) throws ExpressionParserException {
      this.variableBinder = variableBinder;

      try {
         StringReader stringReader = new StringReader(s);
         ExpressionLexer lexer = new ExpressionLexer(stringReader);
         TokenBuffer tokBuf = new TokenBuffer(lexer);
         this.setTokenBuffer(tokBuf);
         return this.rootExpression();
      } catch (NumberFormatException var7) {
         throw new ExpressionParserException("Number format exception : " + var7.getMessage());
      } catch (Exception var8) {
         Exception e = var8;
         String m = null;

         try {
            m = e.getMessage();
         } catch (Exception var6) {
         }

         throw new ExpressionParserException(m, var8);
      }
   }

   protected ExpressionParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.tokenNames = _tokenNames;
   }

   public ExpressionParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 2);
   }

   protected ExpressionParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.tokenNames = _tokenNames;
   }

   public ExpressionParser(TokenStream lexer) {
      this((TokenStream)lexer, 2);
   }

   public ExpressionParser(ParserSharedInputState state) {
      super(state, 2);
      this.tokenNames = _tokenNames;
   }

   public final Expression rootExpression() throws RecognitionException, TokenStreamException {
      Expression expr = this.conditionalExpression();
      this.match(1);
      return expr;
   }

   public final Expression conditionalExpression() throws RecognitionException, TokenStreamException {
      Expression expr = null;
      expr = this.logicalOrExpression();
      return expr;
   }

   public final Expression logicalOrExpression() throws RecognitionException, TokenStreamException {
      Expression rhs = null;

      Expression lhs;
      for(lhs = this.logicalAndExpression(); this.LA(1) == 4; lhs = new Expression(1, lhs, rhs)) {
         this.match(4);
         rhs = this.logicalAndExpression();
      }

      return lhs;
   }

   public final Expression logicalAndExpression() throws RecognitionException, TokenStreamException {
      Expression rhs = null;

      Expression lhs;
      for(lhs = this.logicalNotExpression(); this.LA(1) == 5; lhs = new Expression(0, lhs, rhs)) {
         this.match(5);
         rhs = this.logicalNotExpression();
      }

      return lhs;
   }

   public final Expression logicalNotExpression() throws RecognitionException, TokenStreamException {
      Token not = null;
      switch (this.LA(1)) {
         case 6:
            not = this.LT(1);
            this.match(6);
         case 19:
         case 22:
         case 23:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
            Expression expr = this.relationalExpression();
            if (not != null) {
               expr = new Expression(2, expr);
            }

            return expr;
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
         case 18:
         case 20:
         case 21:
         case 24:
         case 25:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   public final Expression relationalExpression() throws RecognitionException, TokenStreamException {
      Token n1 = null;
      Token n2 = null;
      Expression e1 = null;
      String o = null;
      Expression expr = this.additiveExpression();

      while(true) {
         switch (this.LA(1)) {
            case 6:
            case 15:
            case 16:
            case 18:
               switch (this.LA(1)) {
                  case 6:
                     n2 = this.LT(1);
                     this.match(6);
                  case 15:
                  case 16:
                  case 18:
                     switch (this.LA(1)) {
                        case 15:
                           expr = this.betweenExpression(expr, n2 != null);
                           continue;
                        case 16:
                           expr = this.likeExpression(expr, n2 != null);
                           continue;
                        case 17:
                        default:
                           throw new NoViableAltException(this.LT(1), this.getFilename());
                        case 18:
                           expr = this.inExpression(expr, n2 != null);
                           continue;
                     }
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
               byte op;
               switch (this.LA(1)) {
                  case 7:
                     this.match(7);
                     op = 10;
                     o = "<>";
                     break;
                  case 8:
                     this.match(8);
                     op = 5;
                     o = "=";
                     break;
                  case 9:
                     this.match(9);
                     op = 6;
                     o = "<";
                     break;
                  case 10:
                     this.match(10);
                     op = 7;
                     o = ">";
                     break;
                  case 11:
                     this.match(11);
                     op = 8;
                     o = "<=";
                     break;
                  case 12:
                     this.match(12);
                     op = 9;
                     o = ">=";
                     break;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }

               e1 = this.additiveExpression();
               if (op != 5 && op != 10 && (!expr.isNumeric() || !e1.isNumeric())) {
                  throw new RecognitionException("Operands of " + o + " must be numeric");
               }

               if (!expr.isIdentifier() && !e1.isIdentifier() && (!expr.isNumeric() || !e1.isNumeric()) && (!expr.isString() || !e1.isString()) && (!expr.isBoolean() || !e1.isBoolean())) {
                  throw new RecognitionException("Operands of " + o + " must be of compatible types");
               }

               expr = new Expression(op, expr, e1);
               break;
            case 13:
               this.match(13);
               switch (this.LA(1)) {
                  case 6:
                     n1 = this.LT(1);
                     this.match(6);
                  case 14:
                     this.match(14);
                     if (!expr.isIdentifier()) {
                        throw new RecognitionException("Expected an indentifier before IS " + (n1 != null ? "NOT NULL" : "NULL"));
                     }

                     int op = n1 != null ? 4 : 3;
                     expr = new Expression(op, expr);
                     continue;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            case 14:
            case 17:
            default:
               return expr;
         }
      }
   }

   public final Expression additiveExpression() throws RecognitionException, TokenStreamException {
      Expression e1 = null;

      Expression expr;
      byte op;
      for(expr = this.multiplicativeExpression(); this.LA(1) == 22 || this.LA(1) == 23; expr = new Expression(op, expr, e1)) {
         switch (this.LA(1)) {
            case 22:
               this.match(22);
               op = 22;
               break;
            case 23:
               this.match(23);
               op = 21;
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         e1 = this.multiplicativeExpression();
      }

      return expr;
   }

   public final Expression betweenExpression(Expression lhs, boolean negate) throws RecognitionException, TokenStreamException {
      Expression e1 = null;
      Expression e2 = null;
      this.match(15);
      e1 = this.additiveExpression();
      this.match(5);
      e2 = this.additiveExpression();
      if (lhs.isNumeric() && e1.isNumeric() && e2.isNumeric()) {
         Expression expr;
         if (negate) {
            expr = new Expression(1, new Expression(6, lhs, e1), new Expression(7, lhs, e2));
         } else {
            expr = new Expression(0, new Expression(9, lhs, e1), new Expression(8, lhs, e2));
         }

         return expr;
      } else {
         throw new RecognitionException("Operands of BETWEEN must be numeric");
      }
   }

   public final Expression likeExpression(Expression lhs, boolean negate) throws RecognitionException, TokenStreamException {
      Expression rhs = null;
      Expression esc = null;
      this.match(16);
      rhs = this.string();
      esc = this.escapeExpression();
      if (!lhs.isIdentifier()) {
         throw new RecognitionException("Expected identifier before " + (negate ? "NOT LIKE" : "LIKE"));
      } else {
         Expression expr = new Expression(11, lhs, rhs, esc);
         if (negate) {
            expr = new Expression(2, expr);
         }

         return expr;
      }
   }

   public final Expression inExpression(Expression lhs, boolean negate) throws RecognitionException, TokenStreamException {
      ArrayList list = null;
      this.match(18);
      this.match(19);
      list = this.ident_list();
      this.match(20);
      if (!lhs.isIdentifier()) {
         throw new RecognitionException("Expected identifier before " + (negate ? "NOT IN" : "IN"));
      } else {
         Expression expr = new Expression(13, lhs, list);
         if (negate) {
            expr = new Expression(2, expr);
         }

         return expr;
      }
   }

   public final Expression string() throws RecognitionException, TokenStreamException {
      Token s = null;
      s = this.LT(1);
      this.match(28);
      Expression expr = new Expression(18, s.getText());
      return expr;
   }

   public final Expression escapeExpression() throws RecognitionException, TokenStreamException {
      Expression esc = null;
      switch (this.LA(1)) {
         case 1:
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
         case 15:
         case 16:
         case 18:
         case 20:
            esc = null;
            break;
         case 2:
         case 3:
         case 14:
         case 19:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 17:
            this.match(17);
            esc = this.string();
      }

      return esc;
   }

   public final ArrayList ident_list() throws RecognitionException, TokenStreamException {
      ArrayList l = new ArrayList();
      Expression e = null;
      e = this.string();
      l.add(e);

      while(this.LA(1) == 21) {
         this.match(21);
         e = this.string();
         l.add(e);
      }

      return l;
   }

   public final Expression multiplicativeExpression() throws RecognitionException, TokenStreamException {
      Expression expr = null;
      Expression e1 = null;
      expr = this.unaryExpression();
      if (expr.type() == 26) {
         expr = new Expression(19, expr.getSval());
      } else if (expr.type() == 27) {
         expr = new Expression(20, expr.getSval());
      }

      byte op;
      for(; this.LA(1) == 24 || this.LA(1) == 25; expr = new Expression(op, expr, e1)) {
         switch (this.LA(1)) {
            case 24:
               this.match(24);
               op = 23;
               break;
            case 25:
               this.match(25);
               op = 24;
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         e1 = this.unaryExpression();
         if (e1.type() == 26) {
            e1 = new Expression(19, e1.getSval());
         } else if (e1.type() == 27) {
            e1 = new Expression(20, e1.getSval());
         }
      }

      return expr;
   }

   public final Expression unaryExpression() throws RecognitionException, TokenStreamException {
      Expression expr;
      switch (this.LA(1)) {
         case 19:
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
            expr = this.primaryExpression();
            break;
         case 20:
         case 21:
         case 24:
         case 25:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 22:
            this.match(22);
            expr = this.unaryExpression();
            if (!expr.isNumeric()) {
               throw new RecognitionException("Operand of unary '+' must be numeric");
            }
            break;
         case 23:
            this.match(23);
            expr = this.unaryExpression();
            if (!expr.isNumeric()) {
               throw new RecognitionException("Operand of unary '-' must be numeric");
            }

            if (expr.type() != 26 && expr.type() != 27) {
               Expression sign = new Expression(19, "-1");
               expr = new Expression(23, sign, expr);
            } else if (expr.getSval().charAt(0) == '-') {
               expr.setSval(expr.getSval().substring(1));
            } else {
               expr.setSval("-" + expr.getSval());
            }
      }

      return expr;
   }

   public final Expression primaryExpression() throws RecognitionException, TokenStreamException {
      Expression expr;
      switch (this.LA(1)) {
         case 19:
            this.match(19);
            expr = this.conditionalExpression();
            this.match(20);
            break;
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 26:
         case 27:
         case 28:
         case 29:
         case 30:
         case 31:
         case 32:
            expr = this.value();
      }

      return expr;
   }

   public final Expression value() throws RecognitionException, TokenStreamException {
      Expression expr;
      switch (this.LA(1)) {
         case 26:
            this.match(26);
            expr = this.select_function();
            break;
         case 27:
            expr = this.id();
            break;
         case 28:
            expr = this.string();
            break;
         case 29:
         case 30:
            expr = this.bool();
            break;
         case 31:
         case 32:
            expr = this.number();
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return expr;
   }

   public final Expression select_function() throws RecognitionException, TokenStreamException {
      this.match(19);
      Expression expr = this.select_parameters();
      this.match(20);
      return expr;
   }

   public final Expression number() throws RecognitionException, TokenStreamException {
      Token i = null;
      Token f = null;
      Expression expr;
      switch (this.LA(1)) {
         case 31:
            i = this.LT(1);
            this.match(31);
            expr = new Expression(26, i.getText());
            break;
         case 32:
            f = this.LT(1);
            this.match(32);
            expr = new Expression(27, f.getText());
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return expr;
   }

   public final Expression id() throws RecognitionException, TokenStreamException {
      Token i = null;
      i = this.LT(1);
      this.match(27);
      Expression expr = new Expression(17, this.variableBinder, i.getText());
      return expr;
   }

   public final Expression bool() throws RecognitionException, TokenStreamException {
      Expression expr;
      switch (this.LA(1)) {
         case 29:
            this.match(29);
            expr = new Expression(14);
            break;
         case 30:
            this.match(30);
            expr = new Expression(15);
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return expr;
   }

   public final Expression select_parameters() throws RecognitionException, TokenStreamException {
      Token format = null;
      Token selector = null;
      format = this.LT(1);
      this.match(28);
      this.match(21);
      selector = this.LT(1);
      this.match(28);
      Expression expr = new Expression(25, format.getText(), selector.getText());
      return expr;
   }
}
