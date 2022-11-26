package weblogic.ejb.container.cmp.rdbms.finders;

import antlr.LLkParser;
import antlr.NoViableAltException;
import antlr.ParserSharedInputState;
import antlr.RecognitionException;
import antlr.Token;
import antlr.TokenBuffer;
import antlr.TokenStream;
import antlr.TokenStreamException;
import antlr.collections.impl.BitSet;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import weblogic.ejb20.cmp.rdbms.finders.EJBQLCompilerException;
import weblogic.ejb20.cmp.rdbms.finders.EJBQLToken;

public class ExprParser extends LLkParser implements ExprParserTokenTypes {
   EjbqlFinder finder;
   Expr exprNOOP;
   int nextSubQuery;
   Vector exprVector;
   static String[] keywords = new String[]{"abs", "all", "and", "any", "as", "asc", "avg", "between", "by", "concat", "count", "desc", "distinct", "empty", "escape", "exists", "false", "for", "from", "group", "having", "in", "is", "length", "like", "locate", "max", "member", "min", "mod", "new", "not", "null", "object", "of", "or", "order", "orderby", "select", "select_hint", "sqrt", "substring", "sum", "true", "where", "upper", "lower"};
   StringBuffer exceptionBuff;
   List ejbqlParserTokenList;
   boolean parseExceptionErrorHandled;
   String lastIdValue;
   public static final String[] _tokenNames = new String[]{"<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"select\"", "\"distinct\"", "COMMA", "\"object\"", "LPAREN", "ID", "RPAREN", "\"min\"", "\"max\"", "\"avg\"", "\"sum\"", "\"count\"", "\"all\"", "TIMES", "\"from\"", "\"in\"", "\"as\"", "\"for\"", "\"where\"", "\"orderby\"", "\"order\"", "\"by\"", "\"group\"", "\"having\"", "\"select_hint\"", "\"or\"", "\"and\"", "\"not\"", "\"exists\"", "NTEQ", "EQ", "LT", "GT", "LTEQ", "GTEQ", "\"is\"", "\"null\"", "\"empty\"", "\"between\"", "\"like\"", "\"escape\"", "\"asc\"", "\"desc\"", "\"member\"", "\"of\"", "PLUS", "MINUS", "DIV", "\"any\"", "VARIABLE", "STRING", "\"true\"", "\"false\"", "NUMBER", "\"concat\"", "\"substring\"", "\"length\"", "\"locate\"", "\"upper\"", "\"lower\"", "\"abs\"", "\"sqrt\"", "\"mod\"", "DIGIT", "LETTER", "DOT", "AT", "DASH", "UNICODE_RANGE", "INT", "REAL", "E", "WS"};
   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());

   public ExprParser(EjbqlFinder f) {
      super(2);
      this.exprNOOP = new ExprNOOP(72);
      this.nextSubQuery = 1;
      this.exprVector = new Vector();
      this.exceptionBuff = new StringBuffer();
      this.parseExceptionErrorHandled = false;
      this.lastIdValue = null;
      this.finder = f;
   }

   public Expr parse(String s) throws EJBQLCompilerException {
      try {
         StringReader sr = new StringReader(s);
         ExprLexer lexer = new ExprLexer(sr);
         TokenBuffer tokBuf = new TokenBuffer(lexer);
         this.setTokenBuffer(tokBuf);
         this.ejbqlParserTokenList = new ArrayList();
         return this.rootExpr();
      } catch (Exception var6) {
         try {
            this.handleParserEjbqlTokensAfterFailure();
         } catch (TokenStreamException var5) {
         }

         String antlrExceptionText = var6.toString();
         if (antlrExceptionText.indexOf("java.lang.NullPointerException") != -1) {
            antlrExceptionText = "";
         } else {
            antlrExceptionText = this.removeANTRLine(antlrExceptionText) + "\n\n";
         }

         Exception e = new Exception("\nEJB QL Parser Error.\n\n" + antlrExceptionText + this.exceptionBuff.toString() + "\n");
         EJBQLCompilerException ex = this.finder.newEJBQLCompilerException(e, this.ejbqlParserTokenList);
         throw ex;
      }
   }

   private String getTokenText() throws TokenStreamException {
      return this.LT(0).getText();
   }

   private EJBQLToken addToParserTokenList() throws TokenStreamException {
      String s = this.getTokenText();
      return this.addToParserTokenList(s, true);
   }

   private EJBQLToken addToParserTokenList(boolean pad) throws TokenStreamException {
      String s = this.getTokenText();
      return this.addToParserTokenList(s, pad);
   }

   private EJBQLToken addToParserTokenList(String s) throws TokenStreamException {
      return this.addToParserTokenList(s, true);
   }

   private EJBQLToken addToParserTokenList(String s, boolean blankpad) throws TokenStreamException {
      if (blankpad) {
         s = s + " ";
      }

      EJBQLToken ejbqlToken = new EJBQLToken(s);
      this.ejbqlParserTokenList.add(ejbqlToken);
      return ejbqlToken;
   }

   private void handleParserEjbqlTokensAfterFailure() throws TokenStreamException {
      if (!this.parseExceptionErrorHandled) {
         this.parseExceptionErrorHandled = true;
         String errorTokenText = "?";
         Token t = this.LT(1);
         if (t != null) {
            errorTokenText = t.getText();
         } else {
            t = this.LT(0);
            if (t != null) {
               errorTokenText = t.getText();
            }
         }

         EJBQLToken ejbqlToken = this.addToParserTokenList(errorTokenText);
         ejbqlToken.setHadException(true);
         int j = 2;

         while(true) {
            try {
               int ttype = this.LA(j);
               if (ttype == 1) {
                  break;
               }

               t = this.LT(j++);
               this.addToParserTokenList(t.getText());
            } catch (Exception var6) {
               break;
            }
         }

      }
   }

   private void appendCurrTokenIsKeywordMaybe() {
      boolean isKeyWord = false;

      String s;
      try {
         s = this.LT(1).getText();
      } catch (Throwable var4) {
         return;
      }

      if (s != null) {
         if (s.length() != 0) {
            for(int i = 0; i < keywords.length; ++i) {
               if (keywords[i].equalsIgnoreCase(s)) {
                  isKeyWord = true;
                  break;
               }
            }

            if (isKeyWord) {
               this.exceptionBuff.append("Note: '" + s + "' is a Reserved Keyword.\n\n");
            }

         }
      }
   }

   private String removeANTRLine(String input) {
      String test = "line 1:";
      int i = input.indexOf(test);
      return i == 0 ? input.substring(i + test.length()) : input;
   }

   protected ExprParser(TokenBuffer tokenBuf, int k) {
      super(tokenBuf, k);
      this.exprNOOP = new ExprNOOP(72);
      this.nextSubQuery = 1;
      this.exprVector = new Vector();
      this.exceptionBuff = new StringBuffer();
      this.parseExceptionErrorHandled = false;
      this.lastIdValue = null;
      this.tokenNames = _tokenNames;
   }

   public ExprParser(TokenBuffer tokenBuf) {
      this((TokenBuffer)tokenBuf, 2);
   }

   protected ExprParser(TokenStream lexer, int k) {
      super(lexer, k);
      this.exprNOOP = new ExprNOOP(72);
      this.nextSubQuery = 1;
      this.exprVector = new Vector();
      this.exceptionBuff = new StringBuffer();
      this.parseExceptionErrorHandled = false;
      this.lastIdValue = null;
      this.tokenNames = _tokenNames;
   }

   public ExprParser(TokenStream lexer) {
      this((TokenStream)lexer, 2);
   }

   public ExprParser(ParserSharedInputState state) {
      super(state, 2);
      this.exprNOOP = new ExprNOOP(72);
      this.nextSubQuery = 1;
      this.exprVector = new Vector();
      this.exceptionBuff = new StringBuffer();
      this.parseExceptionErrorHandled = false;
      this.lastIdValue = null;
      this.tokenNames = _tokenNames;
   }

   public final ExprROOT rootExpr() throws RecognitionException, TokenStreamException {
      ExprROOT expr = null;
      Vector v = null;
      this.getExprs();
      expr = this.getExprFromVector();
      expr.appendMainEJBQL(" ");
      return expr;
   }

   public final void getExprs() throws RecognitionException, TokenStreamException {
      ExprSELECT selectexpr = null;
      Expr e1 = null;
      Expr e2 = null;
      Expr e3 = null;
      Expr e4 = null;
      Expr fromexpr = null;
      selectexpr = this.selectExpr();
      if (selectexpr != null) {
         this.exprVector.addElement(selectexpr);
      }

      fromexpr = this.fromExpr();
      if (fromexpr != null) {
         this.exprVector.addElement(fromexpr);
      }

      e1 = this.whereExpr();
      if (e1 != null) {
         this.exprVector.addElement(e1);
      }

      e2 = this.groupByExpr();
      if (e2 != null) {
         this.exprVector.addElement(e2);
      }

      e3 = this.orderByExpr();
      if (e3 != null) {
         this.exprVector.addElement(e3);
      }

      e4 = this.selectHintExpr();
      if (e4 != null) {
         this.exprVector.addElement(e4);
      }

   }

   public final ExprROOT getExprFromVector() throws RecognitionException, TokenStreamException {
      ExprROOT expr = null;
      int vsize = this.exprVector.size();
      Enumeration e = this.exprVector.elements();
      if (vsize == 5) {
         expr = new ExprROOT(33, (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement());
      } else if (vsize == 4) {
         expr = new ExprROOT(33, (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement());
      } else if (vsize == 3) {
         expr = new ExprROOT(33, (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement());
      } else if (vsize == 2) {
         expr = new ExprROOT(33, (Expr)e.nextElement(), (Expr)e.nextElement());
      } else if (vsize == 1) {
         expr = new ExprROOT(33, (Expr)e.nextElement());
      } else {
         this.reportError("Expected 2-5 EJB-QL Elements: SELECT, FROM, [WHERE, ORDER_BY, SELECT_HINT] instead got: " + vsize);
      }

      return expr;
   }

   public final ExprSELECT selectExpr() throws RecognitionException, TokenStreamException {
      ExprSELECT expr = null;
      EJBQLToken ejbqlToken = null;
      switch (this.LA(1)) {
         case 1:
         case 18:
         case 22:
         case 23:
         case 24:
         case 26:
         case 28:
            expr = null;
            break;
         case 2:
         case 3:
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
         case 19:
         case 20:
         case 21:
         case 25:
         case 27:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 4:
            this.match(4);
            ejbqlToken = this.addToParserTokenList();
            expr = this.selectBody();
            expr.appendMainEJBQL(ejbqlToken);
      }

      return expr;
   }

   public final ExprFROM fromExpr() throws RecognitionException, TokenStreamException {
      ExprFROM expr = null;
      Expr e = null;
      Vector v = new Vector();
      EJBQLToken ejbqlToken = null;
      String fromText = "";

      try {
         switch (this.LA(1)) {
            case 1:
            case 10:
            case 22:
            case 23:
            case 24:
            case 26:
            case 28:
               expr = null;
               break;
            case 2:
            case 3:
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
            case 15:
            case 16:
            case 17:
            case 19:
            case 20:
            case 21:
            case 25:
            case 27:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 18:
               this.match(18);
               ejbqlToken = this.addToParserTokenList();
               fromText = ejbqlToken.getTokenText();
               e = this.fromTarget();
               v.addElement(e);

               while(this.LA(1) == 6) {
                  this.match(6);
                  this.addToParserTokenList();
                  e = this.fromTarget();
                  v.addElement(e);
               }

               expr = new ExprFROM(27, this.exprNOOP, v, fromText);
               expr.appendMainEJBQL(fromText + " ");
         }
      } catch (Exception var8) {
         this.appendCurrTokenIsKeywordMaybe();
         this.exceptionBuff.append(" Error in FROM clause. \n\n Check that the Range Variable Declarations and the Collection Member Declarations are correct \n  and that no EJB QL keywords are being used as: \n\n      Range Variable names,\n\n   or Collection Member names,\n\n   or Abstract Schema names. \n\n");
      }

      return expr;
   }

   public final Expr whereExpr() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr e1 = null;
      boolean where_branch = false;
      EJBQLToken ejbqlToken = null;

      try {
         switch (this.LA(1)) {
            case 1:
            case 10:
            case 23:
            case 24:
            case 26:
            case 28:
               expr = null;
               break;
            case 22:
               this.match(22);
               ejbqlToken = this.addToParserTokenList();
               where_branch = true;
               e1 = this.conditionalExpr((Expr)null, false);
               expr = new ExprWHERE(26, e1);
               expr.appendMainEJBQL(ejbqlToken);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (Exception var6) {
         if (where_branch) {
            this.appendCurrTokenIsKeywordMaybe();
            this.exceptionBuff.append("Error in WHERE clause. \nCheck that no EJB QL keywords are being used as: \n   variable names. \n\n\n");
         }
      }

      return expr;
   }

   public final Expr groupByExpr() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr e1 = null;
      ExprHAVING e2 = null;
      Vector v1 = null;
      EJBQLToken ejbqlToken = null;
      String groupByText = null;
      String havingText = null;
      boolean having_branch = false;

      try {
         if (this.LA(1) == 26 && this.LA(2) == 25) {
            this.match(26);
            ejbqlToken = this.addToParserTokenList(true);
            groupByText = ejbqlToken.getTokenText();
            this.match(25);
            ejbqlToken = this.addToParserTokenList(true);
            groupByText = groupByText + ejbqlToken.getTokenText();
            v1 = this.groupby_ident_list();
            switch (this.LA(1)) {
               case 27:
                  this.match(27);
                  ejbqlToken = this.addToParserTokenList();
                  havingText = ejbqlToken.getTokenText();
                  having_branch = true;
                  e1 = this.logicalOrExpr(true);
                  e2 = new ExprHAVING(69, e1);
                  e2.appendMainEJBQL(havingText);
               case 1:
               case 10:
               case 23:
               case 24:
               case 26:
               case 28:
                  expr = new ExprGROUPBY(68, e2, v1);
                  expr.appendMainEJBQL(groupByText);
                  break;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
         } else {
            if (!_tokenSet_0.member(this.LA(1)) || !_tokenSet_1.member(this.LA(2))) {
               throw new NoViableAltException(this.LT(1), this.getFilename());
            }

            expr = null;
         }
      } catch (Exception var10) {
         if (having_branch) {
            this.appendCurrTokenIsKeywordMaybe();
            this.exceptionBuff.append("Error in the HAVING clause. \n   check that no EJB QL keywords are being used as variable names. \n\n");
         }
      }

      return expr;
   }

   public final Expr orderByExpr() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr e1 = null;
      Vector v1 = null;
      EJBQLToken ejbqlToken = null;
      boolean orderby_branch = false;
      String orderByText = null;

      try {
         switch (this.LA(1)) {
            case 1:
            case 28:
               expr = null;
               break;
            case 23:
               this.match(23);
               ejbqlToken = this.addToParserTokenList();
               orderby_branch = true;
               v1 = this.orderby_ident_list();
               e1 = new ExprSIMPLE_QUALIFIER(36, ejbqlToken.getTokenText());
               expr = new ExprORDERBY(36, e1, v1);
               expr.appendMainEJBQL(ejbqlToken);
               break;
            case 24:
               this.match(24);
               ejbqlToken = this.addToParserTokenList(true);
               orderByText = ejbqlToken.getTokenText();
               this.match(25);
               ejbqlToken = this.addToParserTokenList(true);
               orderByText = orderByText + ejbqlToken.getTokenText();
               v1 = this.orderby_ident_list();
               e1 = new ExprSIMPLE_QUALIFIER(36, ejbqlToken.getTokenText());
               expr = new ExprORDERBY(36, e1, v1);
               expr.appendMainEJBQL(orderByText);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (Exception var8) {
         if (orderby_branch) {
            this.exceptionBuff.append(" Possible Error in ORDER BY clause. \n Check that the ORDER BY arguments consists of:\n   pathExprs terminating in a cmp-field name, \n   numeric literals. \n Check that no EJB QL keywords are being used as: \n   arguments. \n\n");
            this.appendCurrTokenIsKeywordMaybe();
         }
      }

      return expr;
   }

   public final Expr selectHintExpr() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      ExprSTRING exprString = null;
      EJBQLToken ejbqlToken = null;
      ExprSELECT_HINT expr;
      switch (this.LA(1)) {
         case 1:
            expr = null;
            break;
         case 28:
            this.match(28);
            ejbqlToken = this.addToParserTokenList();
            expr = this.string();
            expr = new ExprSELECT_HINT(60, (ExprSTRING)expr);
            expr.appendMainEJBQL(ejbqlToken);
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return expr;
   }

   public final ExprSELECT selectBody() throws RecognitionException, TokenStreamException {
      ExprSELECT expr = null;
      Expr e = null;
      Vector v = new Vector();
      boolean outerDistinct = false;
      EJBQLToken ejbqlToken = null;
      String distinctText = "";

      try {
         switch (this.LA(1)) {
            case 5:
               this.match(5);
               ejbqlToken = this.addToParserTokenList();
               distinctText = ejbqlToken.getTokenText();
               outerDistinct = true;
            case 7:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 62:
            case 63:
               e = this.selectTarget();
               v.addElement(e);

               while(this.LA(1) == 6) {
                  this.match(6);
                  this.addToParserTokenList();
                  e = this.selectTarget();
                  v.addElement(e);
               }

               if (outerDistinct) {
                  Expr distinctExpr = new ExprSIMPLE_QUALIFIER(51, distinctText);
                  distinctExpr.appendMainEJBQL(distinctText);
                  expr = new ExprSELECT(34, distinctExpr, v);
               } else {
                  expr = new ExprSELECT(34, this.exprNOOP, v);
               }
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (Exception var8) {
         this.appendCurrTokenIsKeywordMaybe();
         this.exceptionBuff.append("Error detected in SELECT clause.\n\nCheck that no arguments in SELECT clause, or SELECT clause Operators are keywords.\n");
         this.exceptionBuff.append("\nSELECT Targets must be:\n      AGGREGATE(cmp-field or pathExpr terminating in cmp-field): \n          { MIN(f), MAX(f), AVG(f), SUM(f), COUNT(f) },\n\n or   single valued path expression terminating in cmp-field or cmr-field, \n\n or   OBJECT(Identification Variable declared in the FROM Clause). \n\n");
      }

      return expr;
   }

   public final Expr selectTarget() throws RecognitionException, TokenStreamException {
      Token o2 = null;
      Token x2 = null;
      Expr expr = null;
      ExprID exprID = null;
      EJBQLToken ejbqlToken = null;
      String cast;
      switch (this.LA(1)) {
         case 7:
            this.match(7);
            ejbqlToken = this.addToParserTokenList(false);
            cast = ejbqlToken.getTokenText();
            this.match(8);
            this.addToParserTokenList("(", false);
            o2 = this.LT(1);
            this.match(9);
            this.addToParserTokenList(false);
            this.match(10);
            this.addToParserTokenList(")", true);
            exprID = new ExprID(17, o2.getText());
            this.lastIdValue = o2.getText();
            exprID.appendMainEJBQL(o2.getText() + " ");
            expr = new ExprOBJECT(61, exprID);
            ((Expr)expr).appendMainEJBQL(cast);
            break;
         case 9:
            x2 = this.LT(1);
            this.match(9);
            this.addToParserTokenList();
            int type = BaseExpr.finderStringOrId(x2.getText());
            if (type != 17 && type != 40) {
               this.reportError(" Error SELECT expected an ID or '@@', but instead got: " + x2.getText());
            }

            if (type == 40) {
               cast = BaseExpr.getSelectCast(x2.getText());
               exprID = new ExprID(17, cast);
               this.lastIdValue = cast;
               exprID.appendMainEJBQL(cast + " ");
               expr = new ExprOBJECT(61, exprID);
               ((Expr)expr).appendMainEJBQL("@@");
            } else {
               expr = new ExprID(17, x2.getText());
               this.lastIdValue = x2.getText();
               ((Expr)expr).appendMainEJBQL(x2.getText() + " ");
            }
            break;
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
            expr = this.aggregateTarget();
            break;
         case 62:
         case 63:
            expr = this.case_function(false);
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return (Expr)expr;
   }

   public final ExprAGGREGATE aggregateTarget() throws RecognitionException, TokenStreamException {
      ExprAGGREGATE expr = null;
      switch (this.LA(1)) {
         case 11:
            this.match(11);
            this.addToParserTokenList(false);
            expr = this.aggregateWrap(44, this.getTokenText());
            break;
         case 12:
            this.match(12);
            this.addToParserTokenList(false);
            expr = this.aggregateWrap(45, this.getTokenText());
            break;
         case 13:
            this.match(13);
            this.addToParserTokenList(false);
            expr = this.aggregateWrap(46, this.getTokenText());
            break;
         case 14:
            this.match(14);
            this.addToParserTokenList(false);
            expr = this.aggregateWrap(47, this.getTokenText());
            break;
         case 15:
            this.match(15);
            this.addToParserTokenList(false);
            expr = this.aggregateCountWrap(48, this.getTokenText());
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return expr;
   }

   public final Expr case_function(boolean orderByOrGroupBy) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      String branch = null;
      EJBQLToken ejbqlToken = null;

      try {
         Expr expr;
         switch (this.LA(1)) {
            case 62:
               this.match(62);
               branch = "UPPER";
               ejbqlToken = this.addToParserTokenList();
               this.match(8);
               this.addToParserTokenList();
               if (orderByOrGroupBy) {
                  expr = this.case_value_order_group_by();
               } else {
                  expr = this.case_value();
               }

               this.match(10);
               this.addToParserTokenList();
               expr = new ExprCASE(70, expr);
               expr.appendMainEJBQL(ejbqlToken);
               break;
            case 63:
               this.match(63);
               branch = "LOWER";
               ejbqlToken = this.addToParserTokenList();
               this.match(8);
               this.addToParserTokenList();
               if (orderByOrGroupBy) {
                  expr = this.case_value_order_group_by();
               } else {
                  expr = this.case_value();
               }

               this.match(10);
               this.addToParserTokenList();
               expr = new ExprCASE(71, expr);
               expr.appendMainEJBQL(ejbqlToken);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (Exception var6) {
         if (branch.equals("UPPER")) {
            this.exceptionBuff.append(" Error in UPPER(String) function. \n");
         } else if (branch.equals("LOWER")) {
            this.exceptionBuff.append(" Error in LOWER(String) function. \n");
         }

         this.exceptionBuff.append(" Check for proper syntax. \n Check that none of the arguments are EJB QL keywords. \n");
      }

      return expr;
   }

   public final ExprAGGREGATE aggregateWrap(int type, String ejbqlOp) throws RecognitionException, TokenStreamException {
      ExprAGGREGATE expr = null;
      Expr e1 = null;
      Expr e2 = null;
      ExprID exprID = null;
      boolean distinct = false;
      boolean all = false;
      EJBQLToken ejbqlToken = null;
      String qualifierText = "";
      this.match(8);
      this.addToParserTokenList();
      switch (this.LA(1)) {
         case 5:
            this.match(5);
            ejbqlToken = this.addToParserTokenList();
            qualifierText = ejbqlToken.getTokenText();
            distinct = true;
         case 9:
         case 16:
            switch (this.LA(1)) {
               case 16:
                  this.match(16);
                  ejbqlToken = this.addToParserTokenList();
                  qualifierText = ejbqlToken.getTokenText();
                  all = true;
               case 9:
                  e1 = this.id();
                  this.match(10);
                  this.addToParserTokenList();
                  exprID = (ExprID)e1;
                  if (distinct) {
                     e2 = new ExprSIMPLE_QUALIFIER(51, qualifierText);
                     e2.appendMainEJBQL(qualifierText);
                     expr = new ExprAGGREGATE(type, exprID, e2);
                  } else if (all) {
                     e2 = new ExprSIMPLE_QUALIFIER(49, qualifierText);
                     e2.appendMainEJBQL(qualifierText);
                     expr = new ExprAGGREGATE(type, exprID, e2);
                  } else {
                     expr = new ExprAGGREGATE(type, exprID);
                  }

                  expr.appendMainEJBQL(ejbqlOp);
                  return expr;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   public final ExprAGGREGATE aggregateCountWrap(int type, String ejbqlOp) throws RecognitionException, TokenStreamException {
      ExprAGGREGATE expr = null;
      Expr e1 = null;
      Expr e2 = null;
      Expr e3 = null;
      ExprID exprID = null;
      EJBQLToken ejbqlToken = null;
      boolean distinct = false;
      boolean all = false;
      boolean star = false;
      String qualifierText = "";
      this.match(8);
      this.addToParserTokenList();
      switch (this.LA(1)) {
         case 5:
            this.match(5);
            ejbqlToken = this.addToParserTokenList();
            qualifierText = ejbqlToken.getTokenText();
            distinct = true;
            e2 = new ExprSIMPLE_QUALIFIER(51, qualifierText);
            e2.appendMainEJBQL(qualifierText);
         case 9:
         case 16:
         case 17:
            switch (this.LA(1)) {
               case 16:
                  this.match(16);
                  ejbqlToken = this.addToParserTokenList();
                  qualifierText = ejbqlToken.getTokenText();
                  all = true;
                  e2 = new ExprSIMPLE_QUALIFIER(49, qualifierText);
                  e2.appendMainEJBQL(qualifierText);
               case 9:
               case 17:
                  switch (this.LA(1)) {
                     case 9:
                        e3 = this.id();
                        exprID = (ExprID)e3;
                        break;
                     case 17:
                        this.match(17);
                        this.addToParserTokenList();
                        e1 = new ExprSIMPLE_QUALIFIER(50, "*");
                        e1.appendMainEJBQL("*");
                        star = true;
                        break;
                     default:
                        throw new NoViableAltException(this.LT(1), this.getFilename());
                  }

                  this.match(10);
                  this.addToParserTokenList();
                  if (star) {
                     expr = new ExprAGGREGATE(type, exprID);
                  } else if (distinct) {
                     expr = new ExprAGGREGATE(type, exprID, e2);
                  } else if (all) {
                     expr = new ExprAGGREGATE(type, exprID, e2);
                  } else {
                     expr = new ExprAGGREGATE(type, exprID);
                  }

                  expr.appendMainEJBQL(ejbqlOp);
                  return expr;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   public final Expr id() throws RecognitionException, TokenStreamException {
      Token x = null;
      Expr expr = null;
      EJBQLToken ejbqlToken = null;
      x = this.LT(1);
      this.match(9);
      ejbqlToken = this.addToParserTokenList(false);
      String idWithNoPad = ejbqlToken.getTokenText();
      int type = BaseExpr.finderStringOrId(idWithNoPad);
      if (type != 17) {
         this.reportError(" Error expected an ID, but instead got: " + idWithNoPad);
      }

      expr = new ExprID(17, idWithNoPad);
      this.lastIdValue = idWithNoPad;
      expr.appendMainEJBQL(idWithNoPad + " ");
      return expr;
   }

   public final Expr fromTarget() throws RecognitionException, TokenStreamException {
      Token x2f = null;
      Token x2g = null;
      Token x2a = null;
      Token x2b = null;
      Token x2c = null;
      Token x2d = null;
      Token x2e = null;
      Expr expr = null;
      EJBQLToken ejbqlToken = null;
      String primaryKeyword = "";
      String secondaryKeyword = null;
      ExprID expr1;
      ExprID expr2;
      int type;
      switch (this.LA(1)) {
         case 9:
            x2a = this.LT(1);
            this.match(9);
            ejbqlToken = this.addToParserTokenList();
            expr1 = new ExprID(17, x2a.getText());
            this.lastIdValue = x2a.getText();
            expr1.appendMainEJBQL(ejbqlToken);
            type = BaseExpr.finderStringOrId(x2a.getText());
            if (type != 17) {
               this.reportError(" Error FOR expected an ID, but instead got: " + x2a.getText());
            }

            switch (this.LA(1)) {
               case 9:
                  x2e = this.LT(1);
                  this.match(9);
                  ejbqlToken = this.addToParserTokenList();
                  type = BaseExpr.finderStringOrId(x2e.getText());
                  if (type == 35) {
                     this.reportError(" Error FOR expected an ID, but instead got: " + x2e.getText());
                  }

                  expr2 = new ExprID(17, x2e.getText());
                  this.lastIdValue = x2e.getText();
                  expr2.appendMainEJBQL(ejbqlToken);
                  expr = new ExprRANGE_VARIABLE(74, expr1, expr2);
                  return (Expr)expr;
               case 19:
                  this.match(19);
                  ejbqlToken = this.addToParserTokenList();
                  primaryKeyword = ejbqlToken.getTokenText();
                  x2c = this.LT(1);
                  this.match(9);
                  ejbqlToken = this.addToParserTokenList();
                  type = BaseExpr.finderStringOrId(x2c.getText());
                  if (type != 17) {
                     this.reportError(" Error FOR expected an ID, but instead got: " + x2c.getText());
                  }

                  expr2 = new ExprID(17, x2c.getText());
                  this.lastIdValue = x2c.getText();
                  expr2.appendMainEJBQL(ejbqlToken);
                  expr = new ExprCORR_IN(28, expr1, expr2, primaryKeyword);
                  ((Expr)expr).appendMainEJBQL(primaryKeyword);
                  return (Expr)expr;
               case 20:
                  this.match(20);
                  ejbqlToken = this.addToParserTokenList();
                  primaryKeyword = ejbqlToken.getTokenText();
                  x2d = this.LT(1);
                  this.match(9);
                  ejbqlToken = this.addToParserTokenList();
                  type = BaseExpr.finderStringOrId(x2d.getText());
                  if (type != 17) {
                     this.reportError(" Error FOR expected an ID, but instead got: " + x2d.getText());
                  }

                  expr2 = new ExprID(17, x2d.getText());
                  this.lastIdValue = x2d.getText();
                  expr2.appendMainEJBQL(ejbqlToken);
                  expr = new ExprRANGE_VARIABLE(74, expr1, expr2);
                  ((Expr)expr).appendMainEJBQL(primaryKeyword);
                  return (Expr)expr;
               case 21:
                  this.match(21);
                  ejbqlToken = this.addToParserTokenList();
                  primaryKeyword = ejbqlToken.getTokenText();
                  x2b = this.LT(1);
                  this.match(9);
                  ejbqlToken = this.addToParserTokenList();
                  type = BaseExpr.finderStringOrId(x2b.getText());
                  if (type != 17) {
                     this.reportError(" Error FOR expected an ID, but instead got: " + x2b.getText());
                  }

                  expr2 = new ExprID(17, x2b.getText());
                  this.lastIdValue = x2b.getText();
                  expr2.appendMainEJBQL(ejbqlToken);
                  expr = new ExprCORR_FOR(29, expr1, expr2, primaryKeyword);
                  ((Expr)expr).appendMainEJBQL(primaryKeyword);
                  return (Expr)expr;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
         case 19:
            this.match(19);
            ejbqlToken = this.addToParserTokenList();
            primaryKeyword = ejbqlToken.getTokenText();
            this.match(8);
            this.addToParserTokenList();
            x2f = this.LT(1);
            this.match(9);
            this.addToParserTokenList();
            this.match(10);
            this.addToParserTokenList();
            switch (this.LA(1)) {
               case 20:
                  this.match(20);
                  ejbqlToken = this.addToParserTokenList();
                  secondaryKeyword = ejbqlToken.getTokenText();
               case 9:
                  x2g = this.LT(1);
                  this.match(9);
                  this.addToParserTokenList();
                  type = BaseExpr.finderStringOrId(x2f.getText());
                  if (type != 17) {
                     this.reportError(" Error FOR expected an ID, but instead got: " + x2f.getText());
                  }

                  type = BaseExpr.finderStringOrId(x2g.getText());
                  if (type != 17) {
                     this.reportError(" Error FOR expected an ID, but instead got: " + x2g.getText());
                  }

                  expr1 = new ExprID(17, x2f.getText());
                  this.lastIdValue = x2f.getText();
                  expr1.prependPreEJBQL("(");
                  expr1.appendMainEJBQL(x2f.getText());
                  expr1.appendPostEJBQL(")");
                  expr2 = new ExprID(17, x2g.getText());
                  this.lastIdValue = x2f.getText();
                  if (secondaryKeyword != null) {
                     expr2.prependPreEJBQL(" " + secondaryKeyword + " ");
                  }

                  expr2.appendMainEJBQL(x2g.getText() + " ");
                  expr = new ExprCOLLECTION_MEMBER(73, expr1, expr2, primaryKeyword);
                  ((Expr)expr).appendMainEJBQL(primaryKeyword);
                  return (Expr)expr;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   public final Expr conditionalExpr(Expr subqueryQualifier, boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr selectexpr = null;
      EJBQLToken ejbqlToken = null;
      switch (this.LA(1)) {
         case 1:
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
         case 19:
         case 23:
         case 24:
         case 26:
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
         case 38:
         case 39:
         case 42:
         case 43:
         case 47:
         case 49:
         case 50:
         case 51:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
            expr = this.logicalOrExpr(allowAggregateValue);
            if (subqueryQualifier != null) {
               this.exceptionBuff.append(" The qualifiers: ANY, ALL, EXISTS  may only be used with a SubQuery\n\n");
            }
            break;
         case 2:
         case 3:
         case 5:
         case 6:
         case 7:
         case 18:
         case 20:
         case 21:
         case 22:
         case 25:
         case 27:
         case 40:
         case 41:
         case 44:
         case 45:
         case 46:
         case 48:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 4:
            this.match(4);
            ejbqlToken = this.addToParserTokenList();
            String selectText = ejbqlToken.getTokenText();
            expr = this.subqueryBody(subqueryQualifier, selectText);
      }

      return expr;
   }

   public final Vector orderby_ident_list() throws RecognitionException, TokenStreamException {
      Vector v = new Vector();
      Expr e = null;
      EJBQLToken ejbqlToken = null;
      ExprSIMPLE_QUALIFIER e2 = null;
      switch (this.LA(1)) {
         case 9:
            e = this.id();
            break;
         case 57:
            e = this.number();
            break;
         case 62:
         case 63:
            e = this.case_function(true);
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      String qualifierText;
      switch (this.LA(1)) {
         case 45:
            this.match(45);
            ejbqlToken = this.addToParserTokenList(false);
            qualifierText = ejbqlToken.getTokenText();
            e2 = new ExprSIMPLE_QUALIFIER(66, qualifierText);
            e2.appendMainEJBQL(qualifierText);
         case 1:
         case 6:
         case 28:
         case 46:
            switch (this.LA(1)) {
               case 46:
                  this.match(46);
                  ejbqlToken = this.addToParserTokenList();
                  qualifierText = ejbqlToken.getTokenText();
                  e2 = new ExprSIMPLE_QUALIFIER(67, qualifierText);
                  e2.appendMainEJBQL(qualifierText);
               case 1:
               case 6:
               case 28:
                  Expr e = new ExprORDERBY_ELEMENT(52, e, e2);
                  v.addElement(e);
                  e2 = null;

                  while(this.LA(1) == 6) {
                     this.match(6);
                     this.addToParserTokenList();
                     switch (this.LA(1)) {
                        case 9:
                           e = this.id();
                           break;
                        case 57:
                           e = this.number();
                           break;
                        case 62:
                        case 63:
                           e = this.case_function(true);
                           break;
                        default:
                           throw new NoViableAltException(this.LT(1), this.getFilename());
                     }

                     switch (this.LA(1)) {
                        case 1:
                        case 6:
                        case 28:
                        case 46:
                           break;
                        case 45:
                           this.match(45);
                           ejbqlToken = this.addToParserTokenList();
                           qualifierText = ejbqlToken.getTokenText();
                           e2 = new ExprSIMPLE_QUALIFIER(66, qualifierText);
                           e2.appendMainEJBQL(qualifierText);
                           break;
                        default:
                           throw new NoViableAltException(this.LT(1), this.getFilename());
                     }

                     switch (this.LA(1)) {
                        case 46:
                           this.match(46);
                           ejbqlToken = this.addToParserTokenList();
                           qualifierText = ejbqlToken.getTokenText();
                           e2 = new ExprSIMPLE_QUALIFIER(67, qualifierText);
                           e2.appendMainEJBQL(qualifierText);
                        case 1:
                        case 6:
                        case 28:
                           e = new ExprORDERBY_ELEMENT(52, e, e2);
                           v.addElement(e);
                           e2 = null;
                           break;
                        default:
                           throw new NoViableAltException(this.LT(1), this.getFilename());
                     }
                  }

                  return v;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   public final Vector groupby_ident_list() throws RecognitionException, TokenStreamException {
      Vector v = new Vector();
      Expr e = null;

      try {
         switch (this.LA(1)) {
            case 9:
               e = this.id();
               break;
            case 57:
               e = this.number();
               break;
            case 62:
            case 63:
               e = this.case_function(true);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         v.addElement(e);

         for(; this.LA(1) == 6; v.addElement(e)) {
            this.match(6);
            this.addToParserTokenList();
            switch (this.LA(1)) {
               case 9:
                  e = this.id();
                  break;
               case 57:
                  e = this.number();
                  break;
               case 62:
               case 63:
                  e = this.case_function(true);
                  break;
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
         }
      } catch (Exception var4) {
         this.appendCurrTokenIsKeywordMaybe();
         this.exceptionBuff.append(" Error in GROUP BY expression. \n Check that the GROUP BY arguments are: \n   numeric literals, \n   pathExprs terminating in cmp-fields \n  \n Check that none of the GROUP BY arguments are EJB QL keywords \n\n");
         this.appendCurrTokenIsKeywordMaybe();
      }

      return v;
   }

   public final Expr logicalOrExpr(boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr e1 = null;
      EJBQLToken ejbqlToken = null;
      expr = this.logicalAndExpr(allowAggregateValue);

      while(this.LA(1) == 29) {
         this.match(29);
         ejbqlToken = this.addToParserTokenList();
         e1 = this.logicalAndExpr(allowAggregateValue);
         expr = new ExprOR(1, (Expr)expr, e1);
         ((Expr)expr).appendMainEJBQL(ejbqlToken);
      }

      return (Expr)expr;
   }

   public final Expr string() throws RecognitionException, TokenStreamException {
      Token s = null;
      Expr expr = null;
      EJBQLToken ejbqlToken = null;
      s = this.LT(1);
      this.match(54);
      ejbqlToken = this.addToParserTokenList(false);
      String stringWithNoPad = ejbqlToken.getTokenText();
      expr = new ExprSTRING(18, stringWithNoPad);
      expr.appendMainEJBQL(stringWithNoPad + " ");
      return expr;
   }

   public final Expr subqueryBody(Expr subqueryQualifier, String selectText) throws RecognitionException, TokenStreamException {
      Expr expr = null;

      try {
         expr = this.selectBody();
         ((Expr)expr).appendMainEJBQL(selectText);
         Expr e1 = null;
         Expr e2 = null;
         Expr fromexpr = null;
         EJBQLToken ejbqlToken = null;
         Vector v = new Vector();
         if (subqueryQualifier != null) {
            v.addElement(subqueryQualifier);
         }

         v.addElement(expr);
         fromexpr = this.fromExpr();
         if (fromexpr != null) {
            v.addElement(fromexpr);
         }

         e1 = this.whereExpr();
         if (e1 != null) {
            v.addElement(e1);
         }

         e2 = this.groupByExpr();
         if (e2 != null) {
            v.addElement(e2);
         }

         int vsize = v.size();
         Enumeration e = v.elements();
         if (vsize == 5) {
            expr = new ExprSUBQUERY(43, new ExprINTEGER(19, Integer.toString(this.nextSubQuery++)), (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement());
         } else if (vsize == 4) {
            expr = new ExprSUBQUERY(43, new ExprINTEGER(19, Integer.toString(this.nextSubQuery++)), (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement());
         } else if (vsize == 3) {
            expr = new ExprSUBQUERY(43, new ExprINTEGER(19, Integer.toString(this.nextSubQuery++)), (Expr)e.nextElement(), (Expr)e.nextElement(), (Expr)e.nextElement());
         } else if (vsize == 2) {
            expr = new ExprSUBQUERY(43, new ExprINTEGER(19, Integer.toString(this.nextSubQuery++)), (Expr)e.nextElement(), (Expr)e.nextElement());
         } else if (vsize == 1) {
            expr = new ExprSUBQUERY(43, new ExprINTEGER(19, Integer.toString(this.nextSubQuery++)), (Expr)e.nextElement());
         } else {
            this.reportError("Expected 1-5 EJB QL SubQuery Elements: [{ANY, ALL, EXISTS}] SELECT, FROM, [WHERE] [GROUP BY] instead got: " + vsize);
         }
      } catch (Exception var11) {
         this.exceptionBuff.append(" Error in Subquery expression. \n\n");
         this.appendCurrTokenIsKeywordMaybe();
      }

      return (Expr)expr;
   }

   public final Expr subqueryExpr(Expr subqueryQualifier) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      EJBQLToken ejbqlToken = null;
      this.match(4);
      ejbqlToken = this.addToParserTokenList();
      String selectText = ejbqlToken.getTokenText();
      expr = this.subqueryBody(subqueryQualifier, selectText);
      return expr;
   }

   public final Expr logicalAndExpr(boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr e1 = null;
      EJBQLToken ejbqlToken = null;
      expr = this.logicalNotExpr(allowAggregateValue);

      while(this.LA(1) == 30) {
         this.match(30);
         ejbqlToken = this.addToParserTokenList();
         e1 = this.logicalNotExpr(allowAggregateValue);
         expr = new ExprAND(0, (Expr)expr, e1);
         ((Expr)expr).appendMainEJBQL(ejbqlToken);
      }

      return (Expr)expr;
   }

   public final Expr logicalNotExpr(boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr e1 = null;
      int op = 0;
      EJBQLToken notToken = null;
      EJBQLToken ejbqlToken = null;
      String notText = null;
      String existsText = null;
      if (this.LA(1) == 31 && _tokenSet_2.member(this.LA(2))) {
         this.match(31);
         notToken = this.addToParserTokenList();
         op = 2;
      } else if (!_tokenSet_2.member(this.LA(1)) || !_tokenSet_3.member(this.LA(2))) {
         throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      if (this.LA(1) == 32 && this.LA(2) == 8) {
         this.match(32);
         ejbqlToken = this.addToParserTokenList();
         existsText = ejbqlToken.getTokenText();
         this.match(8);
         this.addToParserTokenList();
         ejbqlToken = this.addToParserTokenList();
         boolean notExists = false;
         if (op == 2) {
            notExists = true;
         }

         e1 = new ExprEXISTS(65, notExists);
         e1.appendMainEJBQL(notToken + " " + existsText);
         expr = this.subqueryExpr(e1);
         this.match(10);
         this.addToParserTokenList();
         ((Expr)expr).prependPreEJBQL("( ");
         ((Expr)expr).appendPostEJBQL(") ");
      } else {
         if (!_tokenSet_2.member(this.LA(1)) || !_tokenSet_3.member(this.LA(2))) {
            throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         expr = this.relationalExpr(allowAggregateValue);
         if (op != 0) {
            expr = new ExprNOT(op, (Expr)expr);
            ((Expr)expr).appendMainEJBQL(notToken);
         }
      }

      return (Expr)expr;
   }

   public final Expr relationalExpr(boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr e1 = null;
      Expr e2 = null;
      boolean negate = false;
      EJBQLToken ejbqlToken = null;
      String opText = null;

      try {
         expr = this.additiveExpr(allowAggregateValue);
         if (expr == null) {
            throw new Exception("An unexpected variable or operator was encountered in the EJB QL Query");
         } else {
            while(true) {
               while(true) {
                  switch (this.LA(1)) {
                     case 19:
                     case 31:
                     case 42:
                     case 43:
                     case 47:
                        switch (this.LA(1)) {
                           case 31:
                              this.match(31);
                              this.addToParserTokenList();
                              negate = true;
                           case 19:
                           case 42:
                           case 43:
                           case 47:
                              switch (this.LA(1)) {
                                 case 19:
                                    expr = this.inExpr((Expr)expr, negate);
                                    continue;
                                 case 42:
                                    expr = this.betweenExpr((Expr)expr, negate, allowAggregateValue);
                                    continue;
                                 case 43:
                                    expr = this.likeExpr((Expr)expr, negate);
                                    continue;
                                 case 47:
                                    expr = this.memberExpr((Expr)expr, negate);
                                    continue;
                                 default:
                                    throw new NoViableAltException(this.LT(1), this.getFilename());
                              }
                           default:
                              throw new NoViableAltException(this.LT(1), this.getFilename());
                        }
                     case 20:
                     case 21:
                     case 22:
                     case 23:
                     case 24:
                     case 25:
                     case 26:
                     case 27:
                     case 28:
                     case 29:
                     case 30:
                     case 32:
                     case 40:
                     case 41:
                     case 44:
                     case 45:
                     case 46:
                     default:
                        return (Expr)expr;
                     case 33:
                     case 34:
                     case 35:
                     case 36:
                     case 37:
                     case 38:
                        byte op;
                        switch (this.LA(1)) {
                           case 33:
                              this.match(33);
                              ejbqlToken = this.addToParserTokenList();
                              opText = ejbqlToken.getTokenText();
                              op = 10;
                              break;
                           case 34:
                              this.match(34);
                              ejbqlToken = this.addToParserTokenList();
                              opText = ejbqlToken.getTokenText();
                              op = 5;
                              break;
                           case 35:
                              this.match(35);
                              ejbqlToken = this.addToParserTokenList();
                              opText = ejbqlToken.getTokenText();
                              op = 6;
                              break;
                           case 36:
                              this.match(36);
                              ejbqlToken = this.addToParserTokenList();
                              opText = ejbqlToken.getTokenText();
                              op = 7;
                              break;
                           case 37:
                              this.match(37);
                              ejbqlToken = this.addToParserTokenList();
                              opText = ejbqlToken.getTokenText();
                              op = 8;
                              break;
                           case 38:
                              this.match(38);
                              ejbqlToken = this.addToParserTokenList();
                              opText = ejbqlToken.getTokenText();
                              op = 9;
                              break;
                           default:
                              throw new NoViableAltException(this.LT(1), this.getFilename());
                        }

                        e1 = this.additiveExpr(allowAggregateValue);
                        if (e1 == null) {
                           throw new Exception("An unexpected variable or operator was encountered in the EJB QL Query");
                        }

                        if (op == 10) {
                           expr = new ExprNOT_EQUAL(op, (Expr)expr, e1);
                        } else if (op == 5) {
                           expr = new ExprEQUAL(op, (Expr)expr, e1);
                        } else {
                           expr = new ExprSIMPLE_TWO_TERM(op, (Expr)expr, e1);
                        }

                        ((Expr)expr).appendMainEJBQL(opText);
                        break;
                     case 39:
                        this.match(39);
                        ejbqlToken = this.addToParserTokenList();
                        opText = this.getTokenText();
                        switch (this.LA(1)) {
                           case 31:
                              this.match(31);
                              ejbqlToken = this.addToParserTokenList();
                              opText = opText + " " + ejbqlToken.getTokenText();
                              negate = true;
                           case 40:
                           case 41:
                              switch (this.LA(1)) {
                                 case 40:
                                    this.match(40);
                                    ejbqlToken = this.addToParserTokenList();
                                    opText = opText + " " + ejbqlToken.getTokenText();
                                    if (negate) {
                                       expr = new ExprISNULL(4, (Expr)expr, false);
                                       ((Expr)expr).appendMainEJBQL(opText);
                                    } else {
                                       expr = new ExprISNULL(3, (Expr)expr, true);
                                       ((Expr)expr).appendMainEJBQL(opText);
                                    }
                                    continue;
                                 case 41:
                                    this.match(41);
                                    ejbqlToken = this.addToParserTokenList();
                                    opText = opText + " " + ejbqlToken.getTokenText();
                                    if (negate) {
                                       expr = new ExprISEMPTY(42, (Expr)expr, false);
                                       ((Expr)expr).appendMainEJBQL(opText);
                                    } else {
                                       expr = new ExprISEMPTY(41, (Expr)expr, true);
                                       ((Expr)expr).appendMainEJBQL(opText);
                                    }
                                    continue;
                                 default:
                                    throw new NoViableAltException(this.LT(1), this.getFilename());
                              }
                           default:
                              throw new NoViableAltException(this.LT(1), this.getFilename());
                        }
                  }
               }
            }
         }
      } catch (Exception var10) {
         this.exceptionBuff.append(var10.getMessage());
         return (Expr)expr;
      }
   }

   public final Expr additiveExpr(boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr e1 = null;
      String opString = "";
      expr = this.multiplicativeExpr(allowAggregateValue);

      while(this.LA(1) == 49 || this.LA(1) == 50) {
         byte op;
         switch (this.LA(1)) {
            case 49:
               this.match(49);
               this.addToParserTokenList();
               op = 22;
               opString = "+";
               break;
            case 50:
               this.match(50);
               this.addToParserTokenList();
               op = 21;
               opString = "-";
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         e1 = this.multiplicativeExpr(allowAggregateValue);
         expr = new ExprSIMPLE_ARITH(op, (Expr)expr, e1);
         ((Expr)expr).appendMainEJBQL(opString + " ");
      }

      return (Expr)expr;
   }

   public final Expr betweenExpr(Expr lhs, boolean negate, boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr lhs_clone = null;
      Expr e1 = null;
      Expr e2 = null;
      EJBQLToken ejbqlToken1 = null;
      EJBQLToken ejbqlToken2 = null;

      try {
         this.match(42);
         ejbqlToken1 = this.addToParserTokenList();
         e1 = this.additiveExpr(allowAggregateValue);
         this.match(30);
         ejbqlToken2 = this.addToParserTokenList();
         e2 = this.additiveExpr(allowAggregateValue);
         if (negate) {
            expr = new ExprNOT_BETWEEN(77, lhs, e1, e2);
            ((Expr)expr).appendMainEJBQL(ejbqlToken1.getTokenText());
         } else {
            expr = new ExprBETWEEN(12, lhs, e1, e2);
            ((Expr)expr).appendMainEJBQL(ejbqlToken1.getTokenText());
         }

         ((Expr)expr).appendPostEJBQL(ejbqlToken2.getTokenText());
      } catch (Exception var11) {
         this.exceptionBuff.append("Error in 'BETWEEN' expression. \n  Check the BETWEEN syntax:  expr1 [NOT] BETWEEN expr2 AND expr3 \n  Check that no EJB QL keywords are being used as arguments: expr1, expr2 or expr3. \n\n");
         this.appendCurrTokenIsKeywordMaybe();
      }

      return (Expr)expr;
   }

   public final Expr likeExpr(Expr lhs, boolean negate) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr rhs = null;
      Expr esc = null;
      EJBQLToken ejbqlToken = null;
      boolean like_branch = false;
      String likeText = "";

      try {
         this.match(43);
         ejbqlToken = this.addToParserTokenList();
         like_branch = true;
         rhs = this.like_value();
         esc = this.escapeExpr();
         expr = new ExprLIKE(11, lhs, rhs, esc, negate);
         if (negate) {
            expr.appendMainEJBQL("NOT ");
         }

         expr.appendMainEJBQL(ejbqlToken);
      } catch (Exception var10) {
         if (like_branch) {
            this.appendCurrTokenIsKeywordMaybe();
            this.exceptionBuff.append("Error in 'LIKE' expression. \nCheck the arguments to the 'LIKE' expression.  \n  The arguments are allowed to be: \n     String Literal. \n     Input Parameter. \nCheck that none of the arguments are EJB QL keywords. \n\n");
         }
      }

      return expr;
   }

   public final Expr inExpr(Expr lhs, boolean negate) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      EJBQLToken ejbqlToken = null;
      Vector list = null;
      String inText = null;
      String selectText = null;

      try {
         this.match(19);
         ejbqlToken = this.addToParserTokenList();
         inText = ejbqlToken.getTokenText();
         this.match(8);
         this.addToParserTokenList();
         switch (this.LA(1)) {
            case 4:
               this.match(4);
               ejbqlToken = this.addToParserTokenList();
               selectText = ejbqlToken.getTokenText();
               Expr expr = this.subqueryBody((Expr)null, selectText);
               expr = new ExprIN_SUBQUERY(13, lhs, expr, negate);
               if (negate) {
                  ((Expr)expr).appendMainEJBQL("NOT ");
               }

               ((Expr)expr).appendMainEJBQL(inText);
               break;
            case 49:
            case 50:
            case 53:
            case 54:
            case 57:
               list = this.in_rhs();
               expr = new ExprIN(13, lhs, list, negate);
               if (negate) {
                  ((Expr)expr).appendMainEJBQL("NOT ");
               }

               ((Expr)expr).appendMainEJBQL(inText);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         this.match(10);
         this.addToParserTokenList();
      } catch (Exception var9) {
         this.appendCurrTokenIsKeywordMaybe();
         this.exceptionBuff.append("Error in 'IN' expression. \n\n");
      }

      return (Expr)expr;
   }

   public final Expr memberExpr(Expr lhs, boolean negate) throws RecognitionException, TokenStreamException {
      Token z = null;
      Expr expr = null;
      ExprID rhs = null;
      EJBQLToken ejbqlToken = null;
      String memberText = null;
      String ofText = "";
      this.match(47);
      ejbqlToken = this.addToParserTokenList();
      memberText = ejbqlToken.getTokenText();
      switch (this.LA(1)) {
         case 48:
            this.match(48);
            ejbqlToken = this.addToParserTokenList();
            ofText = ejbqlToken.getTokenText();
         case 9:
            try {
               z = this.LT(1);
               this.match(9);
            } catch (Exception var10) {
               this.appendCurrTokenIsKeywordMaybe();
               this.exceptionBuff.append(" Error in [NOT] MEMBER OF expression. \n Check that the MEMBER OF target is a Collection Valued Path Expr \n  and that no EJB QL keywords are being used as: \n      arguments. \n\n");
            }

            ejbqlToken = this.addToParserTokenList();
            int type = BaseExpr.finderStringOrId(z.getText());
            if (type != 17) {
               this.reportError(" Error MEMBER [OF] expected an ID, but instead got: " + z.getText());
            }

            rhs = new ExprID(17, z.getText());
            this.lastIdValue = z.getText();
            rhs.appendMainEJBQL(ejbqlToken);
            if (negate) {
               expr = new ExprISMEMBER(63, lhs, rhs, false);
               expr.appendMainEJBQL("NOT " + memberText + ofText);
            } else {
               expr = new ExprISMEMBER(62, lhs, rhs, true);
               expr.appendMainEJBQL(memberText + ofText);
            }

            return expr;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }
   }

   public final Expr like_value() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      switch (this.LA(1)) {
         case 1:
         case 10:
         case 19:
         case 23:
         case 24:
         case 26:
         case 28:
         case 29:
         case 30:
         case 31:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 42:
         case 43:
         case 44:
         case 47:
         case 58:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
            expr = this.function();
            break;
         case 2:
         case 3:
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
         case 15:
         case 16:
         case 17:
         case 18:
         case 20:
         case 21:
         case 22:
         case 25:
         case 27:
         case 32:
         case 40:
         case 41:
         case 45:
         case 46:
         case 48:
         case 49:
         case 50:
         case 51:
         case 52:
         case 55:
         case 56:
         case 57:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 53:
            expr = this.variable();
            break;
         case 54:
            expr = this.string();
      }

      return expr;
   }

   public final Expr escapeExpr() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      EJBQLToken ejbqlToken = null;
      boolean esc_branch = false;

      try {
         switch (this.LA(1)) {
            case 1:
            case 10:
            case 19:
            case 23:
            case 24:
            case 26:
            case 28:
            case 29:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 42:
            case 43:
            case 47:
               expr = null;
               break;
            case 2:
            case 3:
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
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
            case 25:
            case 27:
            case 32:
            case 40:
            case 41:
            case 45:
            case 46:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 44:
               this.match(44);
               ejbqlToken = this.addToParserTokenList();
               esc_branch = true;
               Expr expr = this.like_value();
               expr = new ExprESCAPE(75, expr);
               expr.appendMainEJBQL(ejbqlToken);
         }
      } catch (Exception var5) {
         if (esc_branch) {
            this.appendCurrTokenIsKeywordMaybe();
            this.exceptionBuff.append("Error in 'ESCAPE' expression. \nCheck the arguments to the 'ESCAPE' expression.  \n  The arguments are allowed to be: \n     String Literal. \n     Input Parameter. \nCheck that none of the arguments are EJB QL keywords. \n\n");
         }
      }

      return expr;
   }

   public final Vector in_rhs() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr expr2 = null;
      new Vector();
      Vector v = this.in_list();
      return v;
   }

   public final Vector in_list() throws RecognitionException, TokenStreamException {
      Vector v = new Vector();
      Expr e = null;

      try {
         e = this.in_value();
         v.addElement(e);

         while(this.LA(1) == 6) {
            this.match(6);
            this.addToParserTokenList();
            e = this.in_value();
            v.addElement(e);
         }
      } catch (Exception var4) {
         this.appendCurrTokenIsKeywordMaybe();
         this.exceptionBuff.append(" Error in the IN expression. \n Check that the IN (   ) arguments are: \n   String Literals \n   Input Parameters \n  \n Check that none of the IN (  ) arguments are EJB QL keywords \n\n");
      }

      return v;
   }

   public final Expr number() throws RecognitionException, TokenStreamException {
      Token n = null;
      Expr expr = null;
      EJBQLToken ejbqlToken = null;
      n = this.LT(1);
      this.match(57);
      ejbqlToken = this.addToParserTokenList(false);
      String numberWithNoPad = ejbqlToken.getTokenText();
      int op = BaseExpr.numType(numberWithNoPad);
      if (op == 19) {
         expr = new ExprINTEGER(op, numberWithNoPad);
         ((Expr)expr).appendMainEJBQL(numberWithNoPad + " ");
      } else if (op == 20) {
         expr = new ExprFLOAT(op, numberWithNoPad);
         ((Expr)expr).appendMainEJBQL(numberWithNoPad + " ");
      } else {
         this.reportError(" Error expected expected INTEGER or FLOAT, instead got: " + BaseExpr.getTypeName(op));
      }

      return (Expr)expr;
   }

   public final Expr in_value() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      switch (this.LA(1)) {
         case 49:
         case 50:
         case 57:
            expr = this.numeric_literal();
            break;
         case 51:
         case 52:
         case 55:
         case 56:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 53:
            expr = this.variable();
            break;
         case 54:
            expr = this.string();
      }

      return expr;
   }

   public final Expr multiplicativeExpr(boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr e1 = null;
      EJBQLToken ejbqlToken = null;
      String opString = "";
      expr = this.prefixExpr(allowAggregateValue);

      while(this.LA(1) == 17 || this.LA(1) == 51) {
         byte op;
         switch (this.LA(1)) {
            case 17:
               this.match(17);
               ejbqlToken = this.addToParserTokenList();
               op = 23;
               break;
            case 51:
               this.match(51);
               ejbqlToken = this.addToParserTokenList();
               op = 24;
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         e1 = this.prefixExpr(allowAggregateValue);
         expr = new ExprSIMPLE_ARITH(op, (Expr)expr, e1);
         ((Expr)expr).appendMainEJBQL(ejbqlToken);
      }

      return (Expr)expr;
   }

   public final Expr prefixExpr(boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      if (this.LA(1) == 50 && _tokenSet_4.member(this.LA(2))) {
         this.match(50);
         this.addToParserTokenList();
         Expr expr = this.prefixExpr(allowAggregateValue);
         Expr sign = new ExprINTEGER(19, "-1");
         expr = new ExprTIMES(23, sign, expr);
         ((Expr)expr).appendMainEJBQL("-");
      } else if (this.LA(1) == 49 && _tokenSet_4.member(this.LA(2))) {
         this.match(49);
         this.addToParserTokenList();
         expr = this.prefixExpr(allowAggregateValue);
      } else {
         if (!_tokenSet_4.member(this.LA(1)) || !_tokenSet_3.member(this.LA(2))) {
            throw new NoViableAltException(this.LT(1), this.getFilename());
         }

         expr = this.primaryExpr(allowAggregateValue);
      }

      return (Expr)expr;
   }

   public final Expr primaryExpr(boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr expr2 = null;
      EJBQLToken ejbqlToken = null;
      String inText = null;
      switch (this.LA(1)) {
         case 1:
         case 6:
         case 9:
         case 10:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 17:
         case 19:
         case 23:
         case 24:
         case 26:
         case 28:
         case 29:
         case 30:
         case 31:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 42:
         case 43:
         case 47:
         case 49:
         case 50:
         case 51:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
            expr = this.value(allowAggregateValue);
            return expr;
         case 2:
         case 3:
         case 4:
         case 5:
         case 7:
         case 18:
         case 20:
         case 21:
         case 22:
         case 25:
         case 27:
         case 40:
         case 41:
         case 44:
         case 45:
         case 46:
         case 48:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 8:
         case 16:
         case 32:
         case 52:
            switch (this.LA(1)) {
               case 16:
                  this.match(16);
                  ejbqlToken = this.addToParserTokenList();
                  inText = ejbqlToken.getTokenText();
                  expr2 = new ExprSIMPLE_QUALIFIER(49, inText);
                  ((Expr)expr2).appendMainEJBQL(inText);
               case 8:
               case 32:
               case 52:
                  switch (this.LA(1)) {
                     case 52:
                        this.match(52);
                        ejbqlToken = this.addToParserTokenList();
                        inText = ejbqlToken.getTokenText();
                        expr2 = new ExprSIMPLE_QUALIFIER(64, inText);
                        ((Expr)expr2).appendMainEJBQL(inText);
                     case 8:
                     case 32:
                        switch (this.LA(1)) {
                           case 32:
                              this.match(32);
                              ejbqlToken = this.addToParserTokenList();
                              inText = ejbqlToken.getTokenText();
                              expr2 = new ExprEXISTS(65, false);
                              ((Expr)expr2).appendMainEJBQL(inText);
                           case 8:
                              this.match(8);
                              this.addToParserTokenList();
                              expr = this.conditionalExpr((Expr)expr2, allowAggregateValue);
                              this.match(10);
                              this.addToParserTokenList();
                              expr.prependPreEJBQL("( ");
                              expr.appendPostEJBQL(") ");
                              return expr;
                           default:
                              throw new NoViableAltException(this.LT(1), this.getFilename());
                        }
                     default:
                        throw new NoViableAltException(this.LT(1), this.getFilename());
                  }
               default:
                  throw new NoViableAltException(this.LT(1), this.getFilename());
            }
      }
   }

   public final Expr value(boolean allowAggregateValue) throws RecognitionException, TokenStreamException {
      Expr expr = null;
      Expr v1 = null;
      Expr v2 = null;
      switch (this.LA(1)) {
         case 1:
         case 6:
         case 10:
         case 17:
         case 19:
         case 23:
         case 24:
         case 26:
         case 28:
         case 29:
         case 30:
         case 31:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 42:
         case 43:
         case 47:
         case 49:
         case 50:
         case 51:
         case 58:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
         case 64:
         case 65:
         case 66:
            expr = this.function();
            break;
         case 2:
         case 3:
         case 4:
         case 5:
         case 7:
         case 8:
         case 16:
         case 18:
         case 20:
         case 21:
         case 22:
         case 25:
         case 27:
         case 32:
         case 40:
         case 41:
         case 44:
         case 45:
         case 46:
         case 48:
         case 52:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 9:
            expr = this.id();
            break;
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
            if (allowAggregateValue) {
               expr = this.aggregateTarget();
            }
            break;
         case 53:
            expr = this.variable();
            break;
         case 54:
            expr = this.string();
            break;
         case 55:
         case 56:
            expr = this.bool();
            break;
         case 57:
            expr = this.number();
      }

      return (Expr)expr;
   }

   public final Expr function() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      switch (this.LA(1)) {
         case 1:
         case 6:
         case 10:
         case 17:
         case 19:
         case 23:
         case 24:
         case 26:
         case 28:
         case 29:
         case 30:
         case 31:
         case 33:
         case 34:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 42:
         case 43:
         case 44:
         case 47:
         case 49:
         case 50:
         case 51:
         case 64:
         case 65:
         case 66:
            expr = this.arith_function();
            break;
         case 2:
         case 3:
         case 4:
         case 5:
         case 7:
         case 8:
         case 9:
         case 11:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 18:
         case 20:
         case 21:
         case 22:
         case 25:
         case 27:
         case 32:
         case 40:
         case 41:
         case 45:
         case 46:
         case 48:
         case 52:
         case 53:
         case 54:
         case 55:
         case 56:
         case 57:
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
         case 58:
         case 59:
         case 60:
         case 61:
         case 62:
         case 63:
            expr = this.string_function();
      }

      return expr;
   }

   public final Expr variable() throws RecognitionException, TokenStreamException {
      Token v = null;
      Expr expr = null;
      EJBQLToken ejbqlToken = null;
      v = this.LT(1);
      this.match(53);
      this.addToParserTokenList("?", false);
      ejbqlToken = this.addToParserTokenList();
      expr = new ExprVARIABLE(25, v.getText(), this.lastIdValue);
      expr.appendMainEJBQL("?");
      expr.appendMainEJBQL(ejbqlToken);
      return expr;
   }

   public final Expr bool() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      EJBQLToken ejbqlToken = null;
      switch (this.LA(1)) {
         case 55:
            this.match(55);
            ejbqlToken = this.addToParserTokenList();
            expr = new ExprBOOLEAN(14);
            expr.appendMainEJBQL(ejbqlToken);
            break;
         case 56:
            this.match(56);
            ejbqlToken = this.addToParserTokenList();
            expr = new ExprBOOLEAN(15);
            expr.appendMainEJBQL(ejbqlToken);
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return expr;
   }

   public final Expr numeric_literal() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      switch (this.LA(1)) {
         case 49:
            this.match(49);
            this.addToParserTokenList();
            expr = this.number();
            break;
         case 50:
            this.match(50);
            this.addToParserTokenList();
            Expr expr = this.number();
            Expr sign = new ExprINTEGER(19, "-1");
            expr = new ExprTIMES(23, sign, expr);
            ((Expr)expr).appendMainEJBQL("-");
            break;
         case 57:
            expr = this.number();
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return (Expr)expr;
   }

   public final Expr case_value() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      switch (this.LA(1)) {
         case 9:
            expr = this.id();
            break;
         case 53:
            expr = this.variable();
            break;
         case 54:
            expr = this.string();
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return expr;
   }

   public final Expr case_value_order_group_by() throws RecognitionException, TokenStreamException {
      Expr expr = null;
      switch (this.LA(1)) {
         case 9:
            expr = this.id();
            break;
         case 57:
            expr = this.number();
            break;
         default:
            throw new NoViableAltException(this.LT(1), this.getFilename());
      }

      return expr;
   }

   public final Expr string_function() throws RecognitionException, TokenStreamException {
      Expr expr2 = null;
      Expr expr3 = null;
      Expr expr = null;
      String branch = null;
      EJBQLToken ejbqlToken = null;

      try {
         Expr expr;
         switch (this.LA(1)) {
            case 58:
               this.match(58);
               ejbqlToken = this.addToParserTokenList();
               branch = "CONCAT";
               this.match(8);
               this.addToParserTokenList();
               expr = this.value(false);
               this.match(6);
               this.addToParserTokenList();
               expr2 = this.value(false);
               this.match(10);
               this.addToParserTokenList();
               expr = new ExprSTRING_FUNCTION(54, expr, expr2);
               ((Expr)expr).appendMainEJBQL(ejbqlToken);
               break;
            case 59:
               this.match(59);
               this.addToParserTokenList();
               branch = "SUBSTRING";
               this.match(8);
               ejbqlToken = this.addToParserTokenList();
               expr = this.value(false);
               this.match(6);
               this.addToParserTokenList();
               expr2 = this.additiveExpr(false);
               this.match(6);
               this.addToParserTokenList();
               expr3 = this.additiveExpr(false);
               this.match(10);
               this.addToParserTokenList();
               expr = new ExprSTRING_FUNCTION(55, expr, expr2, expr3);
               ((Expr)expr).appendMainEJBQL(ejbqlToken);
               break;
            case 60:
               this.match(60);
               ejbqlToken = this.addToParserTokenList();
               branch = "LENGTH";
               this.match(8);
               this.addToParserTokenList();
               expr = this.value(false);
               this.match(10);
               this.addToParserTokenList();
               expr = new ExprSTRING_FUNCTION(57, expr);
               ((Expr)expr).appendMainEJBQL(ejbqlToken);
               break;
            case 61:
               this.match(61);
               ejbqlToken = this.addToParserTokenList();
               branch = "LOCATE";
               this.match(8);
               this.addToParserTokenList();
               expr = this.value(false);
               this.match(6);
               this.addToParserTokenList();
               expr2 = this.value(false);
               switch (this.LA(1)) {
                  case 6:
                     this.match(6);
                     this.addToParserTokenList();
                     expr3 = this.additiveExpr(false);
                  case 10:
                     this.match(10);
                     expr = new ExprSTRING_FUNCTION(56, expr, expr2, expr3);
                     ((Expr)expr).appendMainEJBQL(ejbqlToken);
                     return (Expr)expr;
                  default:
                     throw new NoViableAltException(this.LT(1), this.getFilename());
               }
            case 62:
            case 63:
               expr = this.case_function(false);
               break;
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
         }
      } catch (Exception var7) {
         this.appendCurrTokenIsKeywordMaybe();
         if (branch.equals("CONCAT")) {
            this.exceptionBuff.append(" Error in CONCAT(String, String) function. \n");
         } else if (branch.equals("SUBSTRING")) {
            this.exceptionBuff.append(" Error in SUBSTRING(String, start, length) function. \n");
         } else if (branch.equals("LENGTH")) {
            this.exceptionBuff.append(" Error in LENGTH(String) function. \n");
         } else if (branch.equals("LOCATE")) {
            this.exceptionBuff.append(" Error in LOCATE(String, String, [,start]) function. \n");
         }

         this.exceptionBuff.append(" Check for proper syntax. \n Check that none of the arguments are EJB QL keywords. \n\n");
      }

      return (Expr)expr;
   }

   public final Expr arith_function() throws RecognitionException, TokenStreamException {
      Expr expr2 = null;
      Expr expr3 = null;
      Expr expr = null;
      String branch = null;
      EJBQLToken ejbqlToken = null;

      try {
         Expr expr;
         switch (this.LA(1)) {
            case 1:
            case 6:
            case 10:
            case 17:
            case 19:
            case 23:
            case 24:
            case 26:
            case 28:
            case 29:
            case 30:
            case 31:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 42:
            case 43:
            case 44:
            case 47:
            case 49:
            case 50:
            case 51:
               break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 18:
            case 20:
            case 21:
            case 22:
            case 25:
            case 27:
            case 32:
            case 40:
            case 41:
            case 45:
            case 46:
            case 48:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
            case 58:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            default:
               throw new NoViableAltException(this.LT(1), this.getFilename());
            case 64:
               this.match(64);
               ejbqlToken = this.addToParserTokenList();
               branch = "ABS";
               this.match(8);
               this.addToParserTokenList();
               expr = this.additiveExpr(false);
               this.match(10);
               this.addToParserTokenList();
               expr = new ExprARITH_FUNCTION(58, expr);
               expr.appendMainEJBQL(ejbqlToken);
               break;
            case 65:
               this.match(65);
               ejbqlToken = this.addToParserTokenList();
               branch = "SQRT";
               this.match(8);
               this.addToParserTokenList();
               expr = this.additiveExpr(false);
               this.match(10);
               this.addToParserTokenList();
               expr = new ExprARITH_FUNCTION(59, expr);
               expr.appendMainEJBQL(ejbqlToken);
               break;
            case 66:
               this.match(66);
               ejbqlToken = this.addToParserTokenList();
               branch = "MOD";
               this.match(8);
               this.addToParserTokenList();
               expr = this.additiveExpr(false);
               this.match(6);
               this.addToParserTokenList();
               expr2 = this.additiveExpr(false);
               this.match(10);
               this.addToParserTokenList();
               expr = new ExprARITH_FUNCTION(76, expr, expr2);
               expr.appendMainEJBQL(ejbqlToken);
         }
      } catch (Exception var7) {
         this.appendCurrTokenIsKeywordMaybe();
         if (branch.equals("ABS")) {
            this.exceptionBuff.append(" Error in ABS(number) function. \n");
         } else if (branch.equals("SQRT")) {
            this.exceptionBuff.append(" Error in SQRT(double) function. \n");
         } else if (branch.equals("MOD")) {
            this.exceptionBuff.append(" Error in MOD(int, int) function. \n");
         }

         this.exceptionBuff.append(" Check for proper syntax. \n Check that none of the arguments are EJB QL keywords. \n\n");
      }

      return expr;
   }

   private static final long[] mk_tokenSet_0() {
      long[] data = new long[]{360711170L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_1() {
      long[] data = new long[]{-4445460755465501118L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_2() {
      long[] data = new long[]{-407918989279486L, 7L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_3() {
      long[] data = new long[]{-105553258086574L, 7L, 0L, 0L};
      return data;
   }

   private static final long[] mk_tokenSet_4() {
      long[] data = new long[]{-407918989279422L, 7L, 0L, 0L};
      return data;
   }
}
