package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.expressions.BooleanExpression;

public final class TrueFunction extends BooleanExpression {
   public static final TrueFunction INSTANCE = new TrueFunction();

   public boolean evaluateAsBoolean(Context ctx) {
      return true;
   }

   public void getSubExpressions(Collection out) {
   }
}
