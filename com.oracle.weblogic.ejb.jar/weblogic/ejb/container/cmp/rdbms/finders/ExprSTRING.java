package weblogic.ejb.container.cmp.rdbms.finders;

import java.util.List;
import weblogic.ejb.container.utils.ToStringUtils;
import weblogic.utils.ErrorCollectionException;

public final class ExprSTRING extends BaseExpr implements Expr, ExpressionTypes {
   protected ExprSTRING(int type, String value) {
      super(type, value);
      this.debugClassName = "ExprSTRING - " + value;
   }

   public void init_method() throws ErrorCollectionException {
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
      StringBuffer sb = new StringBuffer();
      sb.append("'");
      sb.append(ToStringUtils.escapedQuotesToString(this.getSval()));
      sb.append("'");
      return sb.toString();
   }
}
