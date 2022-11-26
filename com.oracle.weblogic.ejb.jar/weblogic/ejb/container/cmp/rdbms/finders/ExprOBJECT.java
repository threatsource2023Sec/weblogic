package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprOBJECT extends BaseExpr implements Expr, SingleExprIDHolder, ExpressionTypes {
   protected ExprOBJECT(int type, ExprID e1) {
      super(type, (Expr)e1);
      this.debugClassName = "ExprOBJECT - " + e1.getEjbqlID() + " ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      this.term1.init(this.globalContext, this.queryTree);
   }

   public void calculate_method() throws ErrorCollectionException {
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      this.appendNewEJBQLTokenToList("(", l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendNewEJBQLTokenToList(") ", l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() {
      return "";
   }

   public ExprID getExprID() {
      return (ExprID)this.term1;
   }

   public String getEjbqlID() {
      return ((ExprID)this.term1).getEjbqlID();
   }

   public String getDealiasedEjbqlID() {
      return ((ExprID)this.term1).getDealiasedEjbqlID();
   }
}
