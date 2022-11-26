package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public class ExprSIMPLE_ARITH extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprSIMPLE_ARITH(int type, Expr left, Expr right) {
      super(type, left, right);
      this.debugClassName = "ExprSIMPLE_ARITH  ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      requireTerm(this, 2);

      try {
         this.term1.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var3) {
         this.addCollectionException(var3);
      }

      if (this.term1 instanceof ExprID) {
      }

      try {
         this.term2.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

      if (this.term2 instanceof ExprID) {
      }

   }

   public void calculate_method() throws ErrorCollectionException {
      try {
         if (this.term1 instanceof ExprID) {
            ((ExprID)this.term1).calcTableAndColumnForCmpField();
         } else {
            this.term1.calculate();
         }
      } catch (ErrorCollectionException var3) {
         this.addCollectionException(var3);
      }

      try {
         if (this.term2 instanceof ExprID) {
            ((ExprID)this.term2).calcTableAndColumnForCmpField();
         } else {
            this.term2.calculate();
         }
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
      }

   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      if (this.term1 != null) {
         this.term1.appendEJBQLTokens(l);
      }

      this.appendMainEJBQLTokenToList(l);
      if (this.term2 != null) {
         this.term2.appendEJBQLTokens(l);
      }

      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      this.clearSQLBuf();
      this.appendSQLBuf("( ");
      this.appendSQLBuf(this.term1.toSQL());
      this.appendSQLBuf(this.getOpString());
      this.appendSQLBuf(this.term2.toSQL());
      this.appendSQLBuf(") ");
      return this.getSQLBuf().toString();
   }

   private String getOpString() throws ErrorCollectionException {
      switch (this.type()) {
         case 21:
            return "- ";
         case 22:
            return "+ ";
         case 23:
            return "* ";
         case 24:
            return "/ ";
         default:
            Exception ex = new Exception("Internal Error in " + this.debugClassName + ", attempt to perform toSQL using an unknown operand type code: '" + this.type() + "'.");
            this.markExcAndThrowCollectionException(ex);
            return "";
      }
   }
}
