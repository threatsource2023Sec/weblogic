package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.ejb20.cmp.rdbms.finders.EJBQLToken;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public abstract class BaseExpr implements Expr, ExpressionTypes {
   protected static final DebugLogger debugLogger;
   protected boolean encounteredException = false;
   protected ErrorCollectionException collectionException = null;
   private EJBQLToken mainEJBQLToken = null;
   private EJBQLToken preEJBQLToken = null;
   private EJBQLToken postEJBQLToken = null;
   protected QueryContext globalContext;
   protected QueryNode queryTree;
   protected int type;
   protected Expr term1 = null;
   protected Expr term2 = null;
   protected Expr term3 = null;
   protected Expr term4 = null;
   protected Expr term5 = null;
   protected Expr term6 = null;
   protected Vector terms = null;
   protected long ival;
   protected double fval;
   protected String sval;
   protected int termCount;
   protected int termVectorSize = 0;
   private int nextTerm = 1;
   private StringBuffer sqlStringBuffer;
   public String debugClassName = "BaseExpr";

   public BaseExpr(int type) {
      if (debugLogger.isDebugEnabled()) {
         debug(" Expr Constructor called for: '" + TYPE_NAMES[type] + "'");
      }

      this.type = type;
      this.termCount = 0;
   }

   protected BaseExpr(int type, Expr expr) {
      if (debugLogger.isDebugEnabled()) {
         debug(" Expr Constructor called for: '" + TYPE_NAMES[type] + "'");
      }

      this.type = type;
      this.termCount = 0;
      this.term1 = expr;
      if (expr != null) {
         ++this.termCount;
      }

   }

   protected BaseExpr(int type, Expr expr1, Expr expr2) {
      if (debugLogger.isDebugEnabled()) {
         debug(" Expr Constructor called for: '" + TYPE_NAMES[type] + "'");
      }

      this.type = type;
      this.termCount = 0;
      this.term1 = expr1;
      if (expr1 != null) {
         ++this.termCount;
      }

      this.term2 = expr2;
      if (expr2 != null) {
         ++this.termCount;
      }

   }

   protected BaseExpr(int type, Expr expr1, Expr expr2, Expr expr3) {
      if (debugLogger.isDebugEnabled()) {
         debug(" Expr Constructor called for: '" + TYPE_NAMES[type] + "'");
      }

      this.type = type;
      this.termCount = 0;
      this.term1 = expr1;
      if (expr1 != null) {
         ++this.termCount;
      }

      this.term2 = expr2;
      if (expr2 != null) {
         ++this.termCount;
      }

      this.term3 = expr3;
      if (expr3 != null) {
         ++this.termCount;
      }

   }

   protected BaseExpr(int type, Expr expr1, Expr expr2, Expr expr3, Expr expr4) {
      if (debugLogger.isDebugEnabled()) {
         debug(" Expr Constructor called for: '" + TYPE_NAMES[type] + "'");
      }

      this.type = type;
      this.termCount = 0;
      this.term1 = expr1;
      if (expr1 != null) {
         ++this.termCount;
      }

      this.term2 = expr2;
      if (expr2 != null) {
         ++this.termCount;
      }

      this.term3 = expr3;
      if (expr3 != null) {
         ++this.termCount;
      }

      this.term4 = expr4;
      if (expr4 != null) {
         ++this.termCount;
      }

   }

   protected BaseExpr(int type, Expr expr1, Expr expr2, Expr expr3, Expr expr4, Expr expr5) {
      if (debugLogger.isDebugEnabled()) {
         debug(" Expr Constructor called for: '" + TYPE_NAMES[type] + "'");
      }

      this.type = type;
      this.termCount = 0;
      this.term1 = expr1;
      if (expr1 != null) {
         ++this.termCount;
      }

      this.term2 = expr2;
      if (expr2 != null) {
         ++this.termCount;
      }

      this.term3 = expr3;
      if (expr3 != null) {
         ++this.termCount;
      }

      this.term4 = expr4;
      if (expr4 != null) {
         ++this.termCount;
      }

      this.term5 = expr5;
      if (expr5 != null) {
         ++this.termCount;
      }

   }

   protected BaseExpr(int type, Expr expr1, Expr expr2, Expr expr3, Expr expr4, Expr expr5, Expr expr6) {
      if (debugLogger.isDebugEnabled()) {
         debug(" Expr Constructor called for: '" + TYPE_NAMES[type] + "'");
      }

      this.type = type;
      this.termCount = 0;
      this.term1 = expr1;
      if (expr1 != null) {
         ++this.termCount;
      }

      this.term2 = expr2;
      if (expr2 != null) {
         ++this.termCount;
      }

      this.term3 = expr3;
      if (expr3 != null) {
         ++this.termCount;
      }

      this.term4 = expr4;
      if (expr4 != null) {
         ++this.termCount;
      }

      this.term5 = expr5;
      if (expr5 != null) {
         ++this.termCount;
      }

      this.term6 = expr6;
      if (expr6 != null) {
         ++this.termCount;
      }

   }

   protected BaseExpr(int type, Expr expr1, Vector terms) {
      if (debugLogger.isDebugEnabled()) {
         debug(" Expr Constructor called for: '" + TYPE_NAMES[type] + "'");
      }

      this.type = type;
      this.term1 = expr1;
      if (expr1 != null) {
         ++this.termCount;
      }

      this.terms = terms;
      this.termVectorSize = terms.size();
   }

   protected BaseExpr(int type, String lit) {
      if (debugLogger.isDebugEnabled()) {
         debug(" Expr Constructor called for: '" + TYPE_NAMES[type] + "'");
      }

      this.type = type;
      if (type == 18) {
         this.sval = lit.substring(1, lit.length() - 1);
      } else {
         this.sval = lit;
      }

      if (type == 19) {
         this.sval = lit;
         this.ival = Long.parseLong(lit);
      } else if (type == 20) {
         this.fval = Double.valueOf(lit);
         this.sval = lit;
      } else if (type == 25) {
         this.sval = lit;
         this.ival = (long)Integer.parseInt(lit);
      } else if (type == 53) {
         this.sval = lit;
      }

   }

   public final void init(QueryContext globalContext, QueryNode queryTree) throws ErrorCollectionException {
      this.globalContext = globalContext;
      this.queryTree = queryTree;
      this.init_method();
      this.throwCollectionException();
   }

   public final void calculate() throws ErrorCollectionException {
      this.calculate_method();
      this.throwCollectionException();
   }

   public abstract void init_method() throws ErrorCollectionException;

   public abstract void calculate_method() throws ErrorCollectionException;

   protected abstract Expr invertForNOT() throws ErrorCollectionException;

   public void addCollectionException(Exception e) {
      if (this.collectionException == null) {
         this.collectionException = new ErrorCollectionException(e);
      } else {
         this.collectionException.add(e);
      }
   }

   public void addCollectionExceptionAndThrow(Exception e) throws ErrorCollectionException {
      this.addCollectionException(e);
      throw this.collectionException;
   }

   public void throwCollectionException() throws ErrorCollectionException {
      if (this.collectionException != null) {
         throw this.collectionException;
      }
   }

   public void markExcAndAddCollectionException(Exception e) {
      this.encounteredException = true;
      this.getMainEJBQLToken().setHadException(true);
      this.addCollectionException(e);
   }

   public void markExcAndThrowCollectionException(Exception e) throws ErrorCollectionException {
      this.markExcAndAddCollectionException(e);
      this.throwCollectionException();
   }

   public String getMainEJBQL() {
      return this.mainEJBQLToken == null ? "" : this.getMainEJBQLToken().getTokenText();
   }

   public void setMainEJBQL(String s) {
      this.appendMainEJBQL(s);
   }

   private EJBQLToken getMainEJBQLToken() {
      if (this.mainEJBQLToken == null) {
         this.mainEJBQLToken = new EJBQLToken();
      }

      return this.mainEJBQLToken;
   }

   private EJBQLToken getPreEJBQLToken() {
      if (this.preEJBQLToken == null) {
         this.preEJBQLToken = new EJBQLToken();
      }

      return this.preEJBQLToken;
   }

   private EJBQLToken getPostEJBQLToken() {
      if (this.postEJBQLToken == null) {
         this.postEJBQLToken = new EJBQLToken();
      }

      return this.postEJBQLToken;
   }

   public void prependPreEJBQL(String s) {
      this.getPreEJBQLToken().prependTokenText(s);
   }

   public void appendMainEJBQL(String s) {
      if (s != null) {
         if (s.length() > 0) {
            this.getMainEJBQLToken().appendTokenText(s);
         }
      }
   }

   public void appendMainEJBQL(EJBQLToken t) {
      if (t != null) {
         String s = t.getTokenText();
         if (s != null) {
            if (s.length() > 0) {
               this.getMainEJBQLToken().appendTokenText(s);
            }
         }
      }
   }

   public void appendPostEJBQL(String s) {
      this.getPostEJBQLToken().appendTokenText(s);
   }

   public abstract void appendEJBQLTokens(List var1);

   protected void appendNewEJBQLTokenToList(String s, List l) {
      EJBQLToken t = new EJBQLToken(s);
      l.add(t);
   }

   protected void appendPreEJBQLTokensToList(List l) {
      if (this.preEJBQLToken != null) {
         l.add(this.getPreEJBQLToken());
      }
   }

   protected void appendMainEJBQLTokenToList(List l) {
      if (this.mainEJBQLToken != null) {
         l.add(this.getMainEJBQLToken());
      }
   }

   protected void appendPostEJBQLTokensToList(List l) {
      if (this.postEJBQLToken != null) {
         l.add(this.getPostEJBQLToken());
      }
   }

   protected void setPreEJBQLFrom(BaseExpr from) {
      this.preEJBQLToken = null;
      String text = from.getPreEJBQLToken().getTokenText();
      if (text != null && text.length() > 0) {
         this.prependPreEJBQL(text);
      }

   }

   protected void setPostEJBQLFrom(BaseExpr from) {
      this.postEJBQLToken = null;
      String text = from.getPostEJBQLToken().getTokenText();
      if (text != null && text.length() > 0) {
         this.appendPostEJBQL(text);
      }

   }

   public String printEJBQLTree() {
      List l = new ArrayList();
      this.appendEJBQLTokens(l);
      StringBuffer sb = new StringBuffer();
      Iterator it = l.iterator();

      while(it.hasNext()) {
         EJBQLToken token = (EJBQLToken)it.next();
         sb.append(token.getTokenText());
      }

      return sb.toString();
   }

   protected void clearSQLBuf() {
      if (this.sqlStringBuffer != null) {
         this.sqlStringBuffer.setLength(0);
      }

   }

   protected void appendSQLBuf(String sql) {
      if (this.sqlStringBuffer == null) {
         this.sqlStringBuffer = new StringBuffer();
      }

      this.sqlStringBuffer.append(sql);
   }

   public StringBuffer getSQLBuf() {
      if (this.sqlStringBuffer == null) {
         this.sqlStringBuffer = new StringBuffer();
      }

      return this.sqlStringBuffer;
   }

   public String toSQL() throws ErrorCollectionException {
      return "";
   }

   public boolean encounteredException() {
      return this.encounteredException;
   }

   public QueryContext getGlobalContext() {
      return this.globalContext;
   }

   public QueryNode getQueryTree() {
      return this.queryTree;
   }

   public int type() {
      return this.type;
   }

   public Expr getTerm1() {
      return this.term1;
   }

   public Expr getTerm2() {
      return this.term2;
   }

   public Expr getTerm3() {
      return this.term3;
   }

   public Expr getTerm4() {
      return this.term4;
   }

   public Expr getTerm5() {
      return this.term5;
   }

   public Expr getTerm6() {
      return this.term6;
   }

   public int numTerms() {
      return this.termCount;
   }

   public boolean hasMoreTerms() {
      return this.nextTerm <= this.termCount;
   }

   public Expr getNextTerm() {
      return this.getTerm(this.nextTerm++);
   }

   public Expr getTerm(int termNumber) {
      switch (termNumber) {
         case 1:
            return this.term1;
         case 2:
            return this.term2;
         case 3:
            return this.term3;
         case 4:
            return this.term4;
         case 5:
            return this.term5;
         case 6:
            return this.term6;
         default:
            return null;
      }
   }

   public int getCurrTermNumber() {
      return this.nextTerm - 1;
   }

   public void setNextTerm(int termNumber) {
      this.nextTerm = termNumber;
   }

   public void replaceTermAt(Expr expr, int termNumber) {
      switch (termNumber) {
         case 1:
            this.term1 = expr;
            break;
         case 2:
            this.term2 = expr;
            break;
         case 3:
            this.term3 = expr;
            break;
         case 4:
            this.term4 = expr;
            break;
         case 5:
            this.term5 = expr;
            break;
         case 6:
            this.term6 = expr;
      }

   }

   public void resetTermNumber() {
      this.nextTerm = 1;
   }

   public Vector getTermVector() {
      return this.terms;
   }

   public int termVectSize() {
      return this.termVectorSize;
   }

   public String getSval() {
      return this.sval;
   }

   public void setSval(String s) {
      this.sval = s;
   }

   public long getIval() {
      return this.ival;
   }

   public double getFval() {
      return this.fval;
   }

   public String getTypeName() {
      return getTypeName(this.type);
   }

   public static void addBlanksForStringLength(StringBuffer sb, String s) {
      if (s != null) {
         int len = s.length();
         if (len > 0) {
            sb.append(getBlankString(len));
         }
      }
   }

   public static String getBlankString(int len) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < len; ++i) {
         sb.append(" ");
      }

      return sb.toString();
   }

   public static int numType(String txt) {
      return txt.indexOf(46) == -1 && txt.indexOf(69) == -1 ? 19 : 20;
   }

   public static String getTypeName(int type) {
      return type > TYPE_NAMES.length ? "UNKNOWN TYPE" : TYPE_NAMES[type];
   }

   public static int finderStringOrId(String txt) {
      if (txt.startsWith("@@")) {
         return 40;
      } else {
         int gtIndex = txt.indexOf(">>");
         if (gtIndex == -1) {
            return 17;
         } else {
            return txt.indexOf("find", gtIndex) != -1 ? 35 : 17;
         }
      }
   }

   public static String getEjbNameFromFinderString(String txt) {
      int gtIndex = txt.indexOf(">>");
      return gtIndex == -1 ? "" : txt.substring(0, gtIndex);
   }

   public static String getSelectCast(String s) {
      if (s.length() < 3) {
         return "";
      } else {
         return 40 != finderStringOrId(s) ? "" : s.substring(2);
      }
   }

   public static Expr getExpressionFromTerms(Expr q, int typeOut) {
      q.resetTermNumber();

      Expr expr;
      do {
         if (!q.hasMoreTerms()) {
            return null;
         }

         expr = q.getNextTerm();
      } while(expr.type() != typeOut);

      return expr;
   }

   public static ExprID getExprIDFromSingleExprIDHolder(Expr expr) throws ErrorCollectionException {
      if (!(expr instanceof SingleExprIDHolder)) {
         IllegalExpressionException ex = new IllegalExpressionException(7, " Internal Error:  attempt to execute: SingleExprIDHolder.getExprID() on an Expr of Class: '" + expr.getClass().getName() + "' which does not implement SingleExprIDHolder");
         expr.markExcAndThrowCollectionException(ex);
      }

      return ((SingleExprIDHolder)expr).getExprID();
   }

   public static Expr getAggregateExpressionFromSubQuerySelect(Expr q) {
      if (q.type() != 43) {
         return null;
      } else {
         Expr expr = null;
         q.resetTermNumber();

         while(q.hasMoreTerms()) {
            expr = q.getNextTerm();
            if (expr.type() == 34) {
               break;
            }

            expr = null;
         }

         if (expr == null) {
            return null;
         } else if (expr.getTermVector() == null) {
            return null;
         } else {
            Enumeration e = expr.getTermVector().elements();

            do {
               if (!e.hasMoreElements()) {
                  return null;
               }

               expr = (Expr)e.nextElement();
            } while(expr.type() != 44 && expr.type() != 45 && expr.type() != 46 && expr.type() != 47 && expr.type() != 48);

            return expr;
         }
      }
   }

   public static String getComparisonOpStringFromType(int t) throws ErrorCollectionException {
      switch (t) {
         case 5:
            return "= ";
         case 6:
            return "< ";
         case 7:
            return "> ";
         case 8:
            return "<= ";
         case 9:
            return ">= ";
         case 10:
            return "<> ";
         default:
            throw new ErrorCollectionException("Internal Error, attempt to perform toSQL using an unknown operand type code: '" + t + "', '" + getTypeName(t) + "'.");
      }
   }

   public String toString() {
      String val = this.toVal();
      return TYPE_NAMES[this.type()] + ": " + (val == null ? this.term1 + "," + this.term2 : val);
   }

   private String toVal() {
      switch (this.type()) {
         case 17:
         case 18:
         case 25:
         case 40:
            return this.getSval();
         case 19:
            return this.getIval() + "";
         case 20:
            return this.getFval() + "";
         default:
            return null;
      }
   }

   public void dump() {
      StringBuffer sb = new StringBuffer();
      dump(this, 0, sb);
      System.out.println(sb.toString());
   }

   public String dumpString() {
      StringBuffer sb = new StringBuffer();
      dump(this, 0, sb);
      return sb.toString();
   }

   private static void dump(Expr q, int depth, StringBuffer sb) {
      if (q.type() != 72) {
         for(int i = 0; i < depth; ++i) {
            sb.append("| ");
         }

         sb.append(TYPE_NAMES[q.type()]);
         switch (q.type()) {
            case 16:
               sb.append("-- NULL").append("\n");
               break;
            case 17:
            case 25:
            case 35:
            case 40:
            case 53:
            case 61:
            case 62:
               sb.append(" -- " + q.getSval()).append("\n");
               break;
            case 18:
               sb.append(" -- \"" + q.getSval() + "\"").append("\n");
               break;
            case 19:
               sb.append(" -- " + q.getIval()).append("\n");
               break;
            case 20:
               sb.append(" -- " + q.getFval()).append("\n");
               break;
            default:
               sb.append("\n");
         }

         if (q.getTerm1() != null) {
            dump(q.getTerm1(), depth + 1, sb);
         }

         if (q.getTerm2() != null) {
            dump(q.getTerm2(), depth + 1, sb);
         }

         if (q.getTerm3() != null) {
            dump(q.getTerm3(), depth + 1, sb);
         }

         if (q.getTerm4() != null) {
            dump(q.getTerm4(), depth + 1, sb);
         }

         if (q.getTerm5() != null) {
            dump(q.getTerm5(), depth + 1, sb);
         }

         if (q.getTerm6() != null) {
            dump(q.getTerm6(), depth + 1, sb);
         }

         if (q.getTermVector() != null) {
            Enumeration ts = q.getTermVector().elements();

            while(ts.hasMoreElements()) {
               dump((Expr)ts.nextElement(), depth + 1, sb);
            }
         }

      }
   }

   public static Expr find(Expr q, int type) {
      if (debugLogger.isDebugEnabled()) {
         debug("Checking NODE: " + TYPE_NAMES[q.type()] + "  for type: " + TYPE_NAMES[type]);
      }

      if (q.type() == type) {
         return q;
      } else {
         Expr expr = null;
         if (q.getTerm1() != null) {
            expr = find(q.getTerm1(), type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.getTerm2() != null) {
            expr = find(q.getTerm2(), type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.getTerm3() != null) {
            expr = find(q.getTerm3(), type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.getTerm4() != null) {
            expr = find(q.getTerm4(), type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.getTerm5() != null) {
            expr = find(q.getTerm5(), type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.getTerm6() != null) {
            expr = find(q.getTerm6(), type);
            if (expr != null) {
               return expr;
            }
         }

         if (q.getTermVector() != null) {
            Enumeration ts = q.getTermVector().elements();

            while(ts.hasMoreElements()) {
               expr = find((Expr)ts.nextElement(), type);
               if (expr != null) {
                  return expr;
               }
            }
         }

         return null;
      }
   }

   public static String findIdForVariable(Expr q, int val) {
      if (q.type() == 5 && q.getTerm2() != null && q.getTerm2().type() == 25 && q.getTerm2().getIval() == (long)val && q.getTerm1() != null && q.getTerm1().type() == 17) {
         if (debugLogger.isDebugEnabled()) {
            debug(" VARIABLE NODE: " + q.getTerm2().getIval() + ".  returning ID: " + q.getTerm1().getSval());
         }

         return q.getTerm1().getSval();
      } else {
         String str = null;
         if (q.getTerm1() != null) {
            str = findIdForVariable(q.getTerm1(), val);
            if (str != null) {
               return str;
            }
         }

         if (q.getTerm2() != null) {
            str = findIdForVariable(q.getTerm2(), val);
            if (str != null) {
               return str;
            }
         }

         if (q.getTerm3() != null) {
            str = findIdForVariable(q.getTerm3(), val);
            if (str != null) {
               return str;
            }
         }

         if (q.getTerm4() != null) {
            str = findIdForVariable(q.getTerm4(), val);
            if (str != null) {
               return str;
            }
         }

         if (q.getTerm5() != null) {
            str = findIdForVariable(q.getTerm5(), val);
            if (str != null) {
               return str;
            }
         }

         if (q.getTerm6() != null) {
            str = findIdForVariable(q.getTerm6(), val);
            if (str != null) {
               return str;
            }
         }

         if (q.getTermVector() != null) {
            Enumeration ts = q.getTermVector().elements();

            while(ts.hasMoreElements()) {
               str = findIdForVariable((Expr)ts.nextElement(), val);
               if (str != null) {
                  return str;
               }
            }
         }

         return str;
      }
   }

   protected static void requireTerm(Expr q, int termNumber) throws ErrorCollectionException {
      Expr term = null;
      IllegalExpressionException ex;
      switch (termNumber) {
         case 1:
            term = q.getTerm1();
            break;
         case 2:
            term = q.getTerm2();
            break;
         case 3:
            term = q.getTerm3();
            break;
         default:
            ex = new IllegalExpressionException(7, ExpressionTypes.TYPE_NAMES[q.type()] + " error attempt to requireTerm on term number " + termNumber);
            q.markExcAndThrowCollectionException(ex);
      }

      if (term == null) {
         ex = new IllegalExpressionException(7, ExpressionTypes.TYPE_NAMES[q.type()] + " expression has term Number" + termNumber + " == null, this is required to be non-null");
         q.markExcAndThrowCollectionException(ex);
      }

   }

   protected static void verifyStringExpressionTerm(Expr q, int termNumber) throws ErrorCollectionException {
      Expr term = null;
      switch (termNumber) {
         case 1:
            term = q.getTerm1();
            break;
         case 2:
            term = q.getTerm2();
            break;
         case 3:
            term = q.getTerm3();
            break;
         default:
            IllegalExpressionException ex = new IllegalExpressionException(7, q.getTypeName() + " error attempt to verifyStringExpressionTerm on term number " + termNumber);
            q.markExcAndThrowCollectionException(ex);
      }

      try {
         verifyStringExpressionType(term);
      } catch (RDBMSException var5) {
         IllegalExpressionException ex = new IllegalExpressionException(7, q.getTypeName() + " function argument " + termNumber + " is of the wrong type: " + var5.toString());
         term.markExcAndThrowCollectionException(ex);
      }

   }

   private static void verifyStringExpressionType(Expr q) throws RDBMSException {
      if (q != null && q.type() != 17 && q.type() != 18 && q.type() != 25 && q.type() != 54 && q.type() != 55) {
         Loggable l = EJBLogger.logfinderInvalidStringExpressionLoggable();
         throw new RDBMSException(q.getTypeName() + "  " + l.getMessageText());
      }
   }

   protected void verifyArithmeticExpressionTerm(Expr q, int termNumber) throws ErrorCollectionException {
      Expr term = null;
      switch (termNumber) {
         case 1:
            term = q.getTerm1();
            break;
         case 2:
            term = q.getTerm2();
            break;
         case 3:
            term = q.getTerm3();
            break;
         default:
            IllegalExpressionException ex = new IllegalExpressionException(7, ExpressionTypes.TYPE_NAMES[q.type()] + " error attempt to verifyArithmeticExpressionTerm on term number " + termNumber);
            q.markExcAndThrowCollectionException(ex);
      }

      try {
         this.verifyArithmeticExpressionType(term);
      } catch (RDBMSException var6) {
         IllegalExpressionException ex = new IllegalExpressionException(7, ExpressionTypes.TYPE_NAMES[q.type()] + " function argument " + termNumber + " is of the wrong type: " + var6.toString());
         term.markExcAndThrowCollectionException(ex);
      }

   }

   private void verifyArithmeticExpressionType(Expr q) throws RDBMSException {
      if (q != null && q.type() != 17 && q.type() != 19 && q.type() != 20 && q.type() != 23 && q.type() != 24 && q.type() != 22 && q.type() != 21 && q.type() != 25 && q.type() != 57 && q.type() != 56 && q.type() != 58 && q.type() != 59 && q.type() != 76) {
         Loggable l = EJBLogger.logfinderInvalidArithExpressionLoggable();
         throw new RDBMSException(ExpressionTypes.TYPE_NAMES[q.type()] + "  " + l.getMessageText());
      }
   }

   private static void debug(String s) {
      debugLogger.debug("[BaseExpr] " + s);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
