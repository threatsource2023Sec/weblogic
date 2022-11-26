package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public class ExprEXISTS extends BaseExpr implements Expr, ExpressionTypes {
   private boolean notExists;

   protected ExprEXISTS(int type, boolean notExists) {
      super(type);
      this.notExists = false;
      this.notExists = notExists;
      this.debugClassName = "ExprEXISTS ";
   }

   protected ExprEXISTS(int type) {
      this(type, false);
   }

   public void init_method() throws ErrorCollectionException {
   }

   public void calculate_method() throws ErrorCollectionException {
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      ExprEXISTS newExprEXISTS;
      if (this.notExists) {
         newExprEXISTS = new ExprEXISTS(65, false);
         newExprEXISTS.setPreEJBQLFrom(this);
         newExprEXISTS.setMainEJBQL("EXISTS ");
         newExprEXISTS.setPostEJBQLFrom(this);
         return newExprEXISTS;
      } else {
         newExprEXISTS = new ExprEXISTS(65, true);
         newExprEXISTS.setPreEJBQLFrom(this);
         newExprEXISTS.setMainEJBQL("NOT EXISTS ");
         newExprEXISTS.setPostEJBQLFrom(this);
         return newExprEXISTS;
      }
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() throws ErrorCollectionException {
      this.clearSQLBuf();
      if (this.notExists) {
         this.appendSQLBuf("NOT ");
      }

      this.appendSQLBuf("EXISTS ");
      return this.getSQLBuf().toString();
   }
}
