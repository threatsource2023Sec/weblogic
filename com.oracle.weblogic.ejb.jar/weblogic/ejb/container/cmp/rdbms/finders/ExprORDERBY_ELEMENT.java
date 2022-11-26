package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.ejb.container.EJBLogger;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;

public class ExprORDERBY_ELEMENT extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprORDERBY_ELEMENT(int type, Expr value, ExprSIMPLE_QUALIFIER qualifier) {
      super(type, value, (Expr)qualifier);
      this.debugClassName = "ExprORDERBY_ELEMENT ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);

      try {
         this.term1.init(this.globalContext, this.queryTree);
         Expr exprToValidate = this.term1;
         if (this.term1.type() == 70 || this.term1.type() == 71) {
            exprToValidate = this.term1.getTerm1();
         }

         if (exprToValidate.type() != 17 && exprToValidate.type() != 19) {
            Loggable l = EJBLogger.logejbqlArgMustBeIDorINTLoggable("ORDERBY", BaseExpr.getTypeName(exprToValidate.type()));
            Exception e = new Exception(l.getMessageText());
            exprToValidate.markExcAndAddCollectionException(e);
            throw e;
         }
      } catch (Exception var5) {
         this.addCollectionException(var5);
      }

      if (this.term2 != null) {
         try {
            this.term2.init(this.globalContext, this.queryTree);
         } catch (Exception var4) {
            this.addCollectionException(var4);
         }
      }

   }

   public void calculate_method() throws ErrorCollectionException {
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      if (this.term2 != null) {
         this.term2.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      return "";
   }
}
