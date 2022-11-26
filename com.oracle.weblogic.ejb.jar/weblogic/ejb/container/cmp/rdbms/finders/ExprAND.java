package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public class ExprAND extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprAND(int type, Expr left, Expr right) {
      super(type, left, right);
      this.debugClassName = "ExprAND  ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      requireTerm(this, 2);

      try {
         this.term1.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var3) {
         this.addCollectionException(var3);
      }

      try {
         this.term2.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      try {
         this.term1.calculate();
      } catch (ErrorCollectionException var3) {
         this.addCollectionException(var3);
      }

      try {
         this.term2.calculate();
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      BaseExpr newExprOR = new ExprOR(1, this.term1, this.term2);
      newExprOR.setPreEJBQLFrom(this);
      newExprOR.setMainEJBQL("OR ");
      newExprOR.setPostEJBQLFrom(this);
      return newExprOR;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendMainEJBQLTokenToList(l);
      if (this.term2 != null) {
         this.term2.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      this.clearSQLBuf();
      this.appendSQLBuf("( ");
      this.appendSQLBuf(this.term1.toSQL());
      this.appendSQLBuf(") ");
      this.appendSQLBuf("AND ");
      this.appendSQLBuf("( ");
      this.appendSQLBuf(this.term2.toSQL());
      this.appendSQLBuf(") ");
      return this.getSQLBuf().toString();
   }
}
