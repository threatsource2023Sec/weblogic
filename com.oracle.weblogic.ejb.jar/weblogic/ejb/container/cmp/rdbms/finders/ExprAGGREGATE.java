package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public final class ExprAGGREGATE extends BaseExpr implements Expr, SingleExprIDHolder, ExpressionTypes {
   protected ExprAGGREGATE(int type, ExprID e1) {
      super(type, (Expr)e1);
   }

   protected ExprAGGREGATE(int type, ExprID e1, Expr e2) {
      super(type, e1, (Expr)e2);
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
   }

   public void calculate_method() throws ErrorCollectionException {
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void validate() throws ErrorCollectionException {
      if (this.type != 48 && !((ExprID)this.term1).isPathExpressionEndingInCmpFieldWithNoSQLGen()) {
         Loggable l = EJBLogger.logAggregateFunctionMustHaveCMPFieldArgLoggable(this.globalContext.getEjbName(), this.term1.getMainEJBQL());
         IllegalExpressionException ex = new IllegalExpressionException(7, l.getMessageText());
         this.term1.markExcAndAddCollectionException(ex);
      }

   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      this.appendNewEJBQLTokenToList("( ", l);
      if (this.term2 != null) {
         this.term2.appendEJBQLTokens(l);
      }

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

   public String getEjbqlID() throws ErrorCollectionException {
      if (this.term1.type() != 17) {
         Exception ex = new IllegalExpressionException(7, " Internal Error in ExprAGGREGATE.getEjbqlID(), term1 is NOT of type: ExpressionTypes.ID");
         this.markExcAndThrowCollectionException(ex);
      }

      return ((ExprID)this.term1).getEjbqlID();
   }
}
