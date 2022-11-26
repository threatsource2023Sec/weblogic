package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprNOT extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprNOT(int type, Expr term1) {
      super(type, term1);
      this.debugClassName = "ExprNOT ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      this.term1.init(this.globalContext, this.queryTree);
   }

   public void calculate_method() throws ErrorCollectionException {
      this.term1.calculate();
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendMainEJBQLTokenToList(l);
      this.appendPreEJBQLTokensToList(l);
      this.term1.appendEJBQLTokens(l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      return "NOT ( " + this.term1.toSQL() + ") ";
   }
}
