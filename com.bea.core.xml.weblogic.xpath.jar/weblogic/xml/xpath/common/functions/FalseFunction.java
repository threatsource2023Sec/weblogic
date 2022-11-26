package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.expressions.BooleanExpression;

public final class FalseFunction extends BooleanExpression {
   public static final FalseFunction INSTANCE = new FalseFunction();

   public boolean evaluateAsBoolean(Context ctx) {
      return false;
   }

   public void getSubExpressions(Collection out) {
   }
}
