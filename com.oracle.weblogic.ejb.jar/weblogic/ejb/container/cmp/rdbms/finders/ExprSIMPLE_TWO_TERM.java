package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public class ExprSIMPLE_TWO_TERM extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprSIMPLE_TWO_TERM(int type, Expr left, Expr right) {
      super(type, left, right);
      this.debugClassName = "ExprSIMPLE_TWO_TERM  ";
   }

   public void init_method() throws ErrorCollectionException {
      requireTerm(this, 1);
      requireTerm(this, 2);

      try {
         this.term1.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var3) {
         this.addCollectionException(var3);
      }

      try {
         this.term2.init(this.globalContext, this.queryTree);
      } catch (ErrorCollectionException var2) {
         this.addCollectionException(var2);
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

      this.throwCollectionException();
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      byte invertedOpType;
      String invertedOpString;
      switch (this.type()) {
         case 6:
            invertedOpType = 9;
            invertedOpString = ">= ";
            break;
         case 7:
            invertedOpType = 8;
            invertedOpString = "<= ";
            break;
         case 8:
            invertedOpType = 7;
            invertedOpString = "> ";
            break;
         case 9:
            invertedOpType = 6;
            invertedOpString = "< ";
            break;
         default:
            throw new AssertionError("unexpected operator type '" + this.type() + "' in ExprSIMPLE_TWO_TERM.invertForNOT().");
      }

      BaseExpr newExprSIMPLE_TWO_TERM = new ExprSIMPLE_TWO_TERM(invertedOpType, this.term1, this.term2);
      newExprSIMPLE_TWO_TERM.setPreEJBQLFrom(this);
      newExprSIMPLE_TWO_TERM.setMainEJBQL(invertedOpString);
      newExprSIMPLE_TWO_TERM.setPostEJBQLFrom(this);
      return newExprSIMPLE_TWO_TERM;
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
      this.appendSQLBuf(this.term1.toSQL());
      this.appendSQLBuf(this.getOpString());
      this.appendSQLBuf(this.term2.toSQL());
      return this.getSQLBuf().toString();
   }

   private String getOpString() throws ErrorCollectionException {
      return BaseExpr.getComparisonOpStringFromType(this.type());
   }
}
