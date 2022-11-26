package weblogic.xml.xpath.common.expressions;

import java.util.Collection;
import weblogic.xml.xpath.common.Context;

public final class LiteralStringExpression extends StringExpression {
   private String mValue;

   public LiteralStringExpression(String literal) {
      this.mValue = literal;
   }

   public String evaluateAsString(Context ctx) {
      return this.mValue;
   }

   public void getSubExpressions(Collection out) {
   }

   public String toString() {
      return "\"" + this.mValue + "\"";
   }
}
