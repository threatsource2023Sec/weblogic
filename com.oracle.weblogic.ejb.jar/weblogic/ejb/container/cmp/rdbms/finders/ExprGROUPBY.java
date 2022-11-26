package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public class ExprGROUPBY extends BaseExpr implements Expr, ExpressionTypes {
   private boolean isResultSetFinder = false;
   private List selectList = null;
   private StringBuffer where_clause_buffer;

   protected ExprGROUPBY(int type, ExprHAVING having, Vector args) {
      super(type, having, (Vector)args);
      this.debugClassName = "ExprGROUPBY ";
   }

   public void init_method() throws ErrorCollectionException {
      this.selectList = this.queryTree.getSelectList();
      Iterator it = this.selectList.iterator();

      Loggable l;
      while(it.hasNext()) {
         SelectNode sn = (SelectNode)it.next();
         if (sn.getSelectType() == 61) {
            l = EJBLogger.logejbqlClauseNotAllowedInResultSetQueriesReturningBeansLoggable("GROUP_BY");
            this.markExcAndThrowCollectionException(new Exception(l.getMessageText()));
         }
      }

      this.isResultSetFinder = this.globalContext.isResultSetFinder();
      it = this.terms.iterator();

      while(it.hasNext()) {
         Expr expr = (Expr)it.next();

         try {
            expr.init(this.globalContext, this.queryTree);
         } catch (Exception var7) {
            expr.markExcAndAddCollectionException(var7);
            this.addCollectionException(var7);
            continue;
         }

         if (expr.type() == 70 || expr.type() == 71) {
            expr = expr.getTerm1();
         }

         if (expr.type() == 19) {
            int intArg = (int)expr.getIval();
            String stringArg = Integer.toString(intArg);
            Loggable l;
            if (!this.isResultSetFinder) {
               l = EJBLogger.lognonResultSetFinderHasIntegerOrderByOrGroupByArgLoggable("GROUPBY", stringArg);
               this.markExcAndAddCollectionException(new IllegalExpressionException(7, l.getMessageText()));
            }

            if (this.selectList.size() < intArg) {
               l = EJBLogger.logintegerOrderByOrGroupByArgExceedsSelectListSizeLoggable("GROUPBY", stringArg, this.selectList.size());
               this.markExcAndAddCollectionException(new IllegalExpressionException(7, l.getMessageText()));
            }
         } else if (expr.type() == 17) {
            if (!((ExprID)expr).isPathExpressionEndingInCmpFieldWithNoSQLGen()) {
               l = EJBLogger.logejbqlArgNotACmpFieldLoggable("GROUP_BY", expr.getSval());
               this.markExcAndAddCollectionException(new IllegalExpressionException(7, l.getMessageText()));
            }
         } else {
            l = EJBLogger.logejbqlArgMustBeIDorINTLoggable("GROUP BY", expr.getTypeName());
            this.markExcAndAddCollectionException(new IllegalExpressionException(7, l.getMessageText()));
         }
      }

      if (this.term1 != null) {
         try {
            this.term1.init(this.globalContext, this.queryTree);
         } catch (ErrorCollectionException var6) {
            this.addCollectionException(var6);
         }
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      this.where_clause_buffer = new StringBuffer();
      Vector v = this.terms;
      if (v.size() >= 1) {
         this.where_clause_buffer.append(" GROUP BY ");
         Iterator it = v.iterator();

         while(it.hasNext()) {
            Expr expr = (Expr)it.next();

            try {
               expr.calculate();
               this.where_clause_buffer.append(expr.toSQL());
            } catch (ErrorCollectionException var6) {
               if (expr.type() == 70 || expr.type() == 71) {
                  expr = expr.getTerm1();
               }

               expr.markExcAndAddCollectionException(var6);
               this.addCollectionException(var6);
            }

            if (it.hasNext()) {
               this.where_clause_buffer.append(", ");
            }
         }

         if (this.term1 != null) {
            try {
               this.term1.calculate();
               this.where_clause_buffer.append(this.term1.toSQL()).append(" ");
            } catch (ErrorCollectionException var5) {
               this.addCollectionException(var5);
            }
         }

         this.throwCollectionException();
         if (this.queryTree.getQueryId() == 0) {
            this.globalContext.setGroupbySql(this.where_clause_buffer.toString());
         }

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

      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      return this.queryTree.getQueryId() == 0 ? "" : this.where_clause_buffer.toString();
   }
}
