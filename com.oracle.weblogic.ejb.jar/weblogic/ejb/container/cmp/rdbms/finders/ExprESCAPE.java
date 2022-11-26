package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public class ExprESCAPE extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprESCAPE(int type, Expr escapeVal) {
      super(type, escapeVal);
      this.debugClassName = "ExprESCAPE ";
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
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      return this.term1 != null ? this.term1.toSQL() : "";
   }
}
