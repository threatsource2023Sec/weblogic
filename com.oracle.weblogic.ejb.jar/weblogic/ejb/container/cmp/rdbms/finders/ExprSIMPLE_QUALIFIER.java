package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprSIMPLE_QUALIFIER extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprSIMPLE_QUALIFIER(int type, String keyword) {
      super(type);
      this.debugClassName = "ExprSIMPLE_QUALIFIER - " + keyword;
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
}
