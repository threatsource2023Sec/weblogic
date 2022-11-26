package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprWHERE extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprWHERE(int type, Expr e1) {
      super(type, e1);
      this.debugClassName = "ExprWHERE ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      this.term1.init(this.globalContext, this.queryTree);
   }

   public void calculate_method() throws ErrorCollectionException {
      this.term1.calculate();

      try {
         this.queryTree.checkOracleORJoin(this.toSQL());
      } catch (Exception var2) {
         this.markExcAndThrowCollectionException(var2);
      }

   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      return "WHERE ( " + this.term1.toSQL() + ") ";
   }
}
