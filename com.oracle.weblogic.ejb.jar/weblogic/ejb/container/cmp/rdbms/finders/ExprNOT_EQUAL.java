package weblogic.ejb.container.cmp.rdbms.finders;

import weblogic.utils.ErrorCollectionException;

public final class ExprNOT_EQUAL extends ExprEQUAL implements Expr, ExpressionTypes {
   protected ExprNOT_EQUAL(int type, Expr left, Expr right) {
      super(type, left, right);
      this.isEqual = false;
      this.debugClassName = "ExprNOT_EQUAL ";
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      BaseExpr newExprEQUAL = new ExprEQUAL(5, this.term1, this.term2);
      newExprEQUAL.setPreEJBQLFrom(this);
      newExprEQUAL.setMainEJBQL("= ");
      newExprEQUAL.setPostEJBQLFrom(this);
      return newExprEQUAL;
   }
}
