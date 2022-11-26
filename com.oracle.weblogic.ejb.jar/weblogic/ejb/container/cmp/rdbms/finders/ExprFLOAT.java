package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprFLOAT extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprFLOAT(int type, String value) {
      super(type, value);
      this.debugClassName = "ExprFLOAT - " + value;
   }

   public void init_method() throws ErrorCollectionException {
   }

   public void calculate_method() throws ErrorCollectionException {
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() {
      return this.getSval() + " ";
   }
}
