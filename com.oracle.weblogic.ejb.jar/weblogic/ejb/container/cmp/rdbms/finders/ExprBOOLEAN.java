package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.utils.ErrorCollectionException;

public final class ExprBOOLEAN extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprBOOLEAN(int type) {
      super(type);
      this.debugClassName = "ExprBOOLEAN - " + getTypeName(type);
   }

   public void init_method() throws ErrorCollectionException {
      if (this.type() != 14 && this.type() != 15) {
         this.markExcAndThrowCollectionException(new Exception("Internal Error,  Boolean type has been constructed with a value of other than { TRUE, FALSE }."));
      }

   }

   public void calculate_method() throws ErrorCollectionException {
   }

   protected Expr invertForNOT() throws ErrorCollectionException {
      return this;
   }

   public void appendEJBQLTokens(List l) {
      this.appendPreEJBQLTokensToList(l);
      this.appendMainEJBQLTokenToList(l);
      this.appendPostEJBQLTokensToList(l);
   }

   public String toSQL() {
      if (this.type() == 14) {
         return "TRUE ";
      } else {
         return this.type() == 15 ? "FALSE " : "";
      }
   }
}
