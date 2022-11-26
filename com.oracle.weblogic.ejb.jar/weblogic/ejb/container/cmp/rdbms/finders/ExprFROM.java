package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;
import weblogic.utils.ErrorCollectionException;

public final class ExprFROM extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprFROM(int type, Expr noop, Vector fromElements, String ejbqlFROM) {
      super(type, noop, fromElements);
      this.debugClassName = "ExprFROM";
   }

   public void init_method() throws ErrorCollectionException {
      if (this.terms.size() <= 0) {
         IllegalExpressionException ex = new IllegalExpressionException(7, " Error: the FROM Clause of an EJB QL Query cannot be empty. ");
         this.markExcAndThrowCollectionException(ex);
      }

      Enumeration e = this.terms.elements();

      while(e.hasMoreElements()) {
         Expr expr = (Expr)e.nextElement();
         if (debugLogger.isDebugEnabled()) {
            debug(" init on '" + expr.getTypeName() + "'");
         }

         try {
            expr.init(this.globalContext, this.queryTree);
         } catch (ErrorCollectionException var4) {
            this.addCollectionException(var4);
         }
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      Enumeration e = this.terms.elements();

      while(e.hasMoreElements()) {
         Expr expr = (Expr)e.nextElement();

         try {
            expr.calculate();
         } catch (ErrorCollectionException var4) {
            this.addCollectionException(var4);
         }
      }

   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      Enumeration en = this.terms.elements();

      while(en.hasMoreElements()) {
         Expr expr = (Expr)en.nextElement();
         expr.appendEJBQLTokens(l);
         if (en.hasMoreElements()) {
            this.appendNewEJBQLTokenToList(", ", l);
         }
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() {
      return "";
   }

   private static void debug(String s) {
      debugLogger.debug("[ExprFROM] " + s);
   }
}
