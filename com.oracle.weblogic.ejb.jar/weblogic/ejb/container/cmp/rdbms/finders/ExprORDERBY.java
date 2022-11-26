package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public class ExprORDERBY extends BaseExpr implements Expr, ExpressionTypes {
   private boolean isResultSetFinder = false;
   private boolean hasIntegerArgument = false;
   private List selectList = null;
   private StringBuffer select_clause_buffer;
   private StringBuffer where_clause_buffer;

   protected ExprORDERBY(int type, Expr noop, Vector args) {
      super(type, noop, args);
      this.debugClassName = "ExprORDERBY ";
   }

   public void init_method() throws ErrorCollectionException {
      this.isResultSetFinder = this.globalContext.isResultSetFinder();
      this.selectList = this.queryTree.getSelectList();
      Enumeration e = this.terms.elements();

      while(e.hasMoreElements()) {
         Expr expr = (Expr)e.nextElement();

         try {
            expr.init(this.globalContext, this.queryTree);
         } catch (Exception var7) {
            expr.markExcAndAddCollectionException(var7);
            this.addCollectionException(var7);
         }

         if (expr.getTerm1().type() == 70 || expr.getTerm1().type() == 71) {
            expr = expr.getTerm1();
         }

         if (expr.getTerm1().type() == 19) {
            this.hasIntegerArgument = true;
            if (!this.isResultSetFinder) {
               int intArg = (int)expr.getTerm1().getIval();
               String stringArg = Integer.toString(intArg);
               Loggable l = EJBLogger.lognonResultSetFinderHasIntegerOrderByOrGroupByArgLoggable("ORDERBY", stringArg);
               Exception ex = new IllegalExpressionException(7, l.getMessageText());
               expr.getTerm1().markExcAndThrowCollectionException(ex);
            }
         }
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      this.where_clause_buffer = new StringBuffer();
      if (!this.isResultSetFinder) {
         this.select_clause_buffer = new StringBuffer();
      }

      Enumeration e = this.terms.elements();

      while(true) {
         Loggable l;
         while(e.hasMoreElements()) {
            Expr origExpr = (Expr)e.nextElement();
            Expr expr = origExpr;
            if (origExpr.getTerm1().type() == 70 || origExpr.getTerm1().type() == 71) {
               expr = origExpr.getTerm1();
            }

            if (expr.getTerm1().type() == 19) {
               int intArg = (int)expr.getTerm1().getIval();
               String stringArg = Integer.toString(intArg);
               if (this.selectList.size() < intArg) {
                  Loggable l = EJBLogger.logintegerOrderByOrGroupByArgExceedsSelectListSizeLoggable("ORDERBY", stringArg, this.selectList.size());
                  Exception ex = new IllegalExpressionException(7, l.getMessageText());
                  expr.getTerm1().markExcAndAddCollectionException(ex);
                  this.addCollectionException(ex);
                  continue;
               }

               this.where_clause_buffer.append(" ").append(origExpr.getTerm1().toSQL());
               this.hasIntegerArgument = true;
            } else {
               if (!(expr.getTerm1() instanceof ExprID)) {
                  throw new AssertionError("unexpected codepath");
               }

               IllegalExpressionException ex;
               if (!((ExprID)expr.getTerm1()).isPathExpressionEndingInCmpFieldWithNoSQLGen()) {
                  l = EJBLogger.logejbqlArgNotACmpFieldLoggable("ORDERBY", expr.getTerm1().getSval());
                  ex = new IllegalExpressionException(7, l.getMessageText());
                  expr.getTerm1().markExcAndAddCollectionException(ex);
                  this.addCollectionException(ex);
                  continue;
               }

               if (((ExprID)expr.getTerm1()).isPathExpressionEndingInBlobClobColumnWithSQLGen()) {
                  l = EJBLogger.logcannotSpecifyBlobClobInOrderbyLoggable(((ExprID)expr.getTerm1()).getEjbqlID());
                  ex = new IllegalExpressionException(7, l.getMessageText());
                  expr.getTerm1().markExcAndAddCollectionException(ex);
                  this.addCollectionException(ex);
                  continue;
               }

               String tableAndColumn = origExpr.getTerm1().toSQL();
               this.where_clause_buffer.append(tableAndColumn);
               if (!this.isResultSetFinder) {
                  this.select_clause_buffer.append(tableAndColumn);
               }
            }

            if (origExpr.getTerm2() != null) {
               if (origExpr.getTerm2().type() == 67) {
                  this.where_clause_buffer.append(" DESC ");
               } else if (origExpr.getTerm2().type() == 66) {
                  this.where_clause_buffer.append(" ASC ");
               }
            }

            if (e.hasMoreElements()) {
               this.where_clause_buffer.append(", ");
               if (!this.isResultSetFinder) {
                  this.select_clause_buffer.append(", ");
               }
            }
         }

         if (this.hasIntegerArgument) {
            Iterator it = this.selectList.iterator();

            while(it.hasNext()) {
               SelectNode sn = (SelectNode)it.next();
               if (sn.getSelectType() == 61) {
                  l = EJBLogger.logejbqlClauseNotAllowedInResultSetQueriesReturningBeansLoggable("ORDERBY");
                  this.markExcAndThrowCollectionException(new IllegalExpressionException(7, l.getMessageText()));
               }
            }
         }

         this.where_clause_buffer.append(" ");
         if (!this.isResultSetFinder) {
            this.select_clause_buffer.append(" ");
         }

         if (!this.isResultSetFinder) {
            this.globalContext.setOrderbyColBuf(this.select_clause_buffer.toString());
         }

         this.globalContext.setOrderbySql(" ORDER BY " + this.where_clause_buffer.toString() + " ");
         return;
      }
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      Enumeration en = this.terms.elements();

      while(en.hasMoreElements()) {
         Expr expr = (Expr)en.nextElement();
         expr.appendEJBQLTokens(l);
         if (en.hasMoreElements()) {
            this.appendNewEJBQLTokenToList(", ", l);
         }
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      return "";
   }
}
