package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprNOOP extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprNOOP(int type) {
      super(type);
   }

   public void init_method() throws ErrorCollectionException {
   }

   public void calculate_method() throws ErrorCollectionException {
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
   }

   public String toSQL() {
      return "";
   }
}
