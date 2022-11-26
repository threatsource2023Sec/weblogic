package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public class ExprIN_SUBQUERY extends BaseExpr implements Expr, ExpressionTypes {
   private static final int SUBQUERY_IN_ON_BEAN = 0;
   private static final int SUBQUERY_IN_ON_FIELD = 1;
   private int inType = 1;
   private final boolean notIn;
   private StringBuffer preCalcSQLBuf;

   protected ExprIN_SUBQUERY(int type, Expr lhs, Expr rhs, boolean notIn) {
      super(type, lhs, rhs);
      this.notIn = notIn;
      this.debugClassName = "ExprIN_SUBQUERY ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      requireTerm(this, 2);

      try {
         this.term1.init(this.globalContext, this.queryTree);
      } catch (Exception var3) {
         this.addCollectionException(var3);
      }

      try {
         this.term2.init(this.globalContext, this.queryTree);
      } catch (Exception var2) {
         this.addCollectionException(var2);
      }

      if (ExprEQUAL.isCalcEQonSubQuerySelectBean(this)) {
         this.inType = 0;
      } else {
         this.inType = 1;
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      this.preCalcSQLBuf = new StringBuffer();
      if (this.inType == 0) {
         ExprEQUAL.doCalcEQonSubQuerySelectBean(this.globalContext, this.queryTree, this, this.preCalcSQLBuf, !this.notIn, true);
      } else {
         try {
            if (this.term1 instanceof ExprID) {
               ((ExprID)this.term1).calcTableAndColumnForCmpField();
            } else {
               this.term1.calculate();
            }
         } catch (ErrorCollectionException var3) {
            this.addCollectionException(var3);
         }

         try {
            this.term2.calculate();
         } catch (ErrorCollectionException var2) {
            this.addCollectionException(var2);
         }

      }
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      ExprIN_SUBQUERY newExprIN_SUBQUERY;
      if (this.notIn) {
         newExprIN_SUBQUERY = new ExprIN_SUBQUERY(this.type, this.term1, this.term2, false);
         newExprIN_SUBQUERY.setPreEJBQLFrom(this);
         newExprIN_SUBQUERY.setMainEJBQL("IN  ");
         newExprIN_SUBQUERY.setPostEJBQLFrom(this);
         return newExprIN_SUBQUERY;
      } else {
         newExprIN_SUBQUERY = new ExprIN_SUBQUERY(this.type, this.term1, this.term2, true);
         newExprIN_SUBQUERY.setPreEJBQLFrom(this);
         newExprIN_SUBQUERY.setMainEJBQL("NOT IN ");
         newExprIN_SUBQUERY.setPostEJBQLFrom(this);
         return newExprIN_SUBQUERY;
      }
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendMainEJBQLTokenToList(l);
      this.appendNewEJBQLTokenToList("( ", l);
      if (this.term2 != null) {
         this.term2.appendEJBQLTokens(l);
      }

      this.appendNewEJBQLTokenToList(") ", l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      this.clearSQLBuf();
      if (this.inType == 0) {
         return this.preCalcSQLBuf.toString();
      } else {
         this.appendSQLBuf(this.term1.toSQL());
         if (this.notIn) {
            this.appendSQLBuf("NOT ");
         }

         this.appendSQLBuf("IN ");
         this.appendSQLBuf(this.term2.toSQL());
         return this.getSQLBuf().toString();
      }
   }
}
