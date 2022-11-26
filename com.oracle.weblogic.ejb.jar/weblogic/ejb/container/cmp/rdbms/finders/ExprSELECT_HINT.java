package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprSELECT_HINT extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprSELECT_HINT(int type, ExprSTRING hint_text) {
      super(type, (Expr)hint_text);
      this.debugClassName = "ExprSELECT_HINT";
   }

   public void init_method() throws ErrorCollectionException {
   }

   public void calculate_method() throws ErrorCollectionException {
      this.globalContext.setSelectHint(this.term1.getSval());
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() {
      return "";
   }
}
