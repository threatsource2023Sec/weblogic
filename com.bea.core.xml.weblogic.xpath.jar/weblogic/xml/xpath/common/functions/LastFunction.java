package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.NumberExpression;

public final class LastFunction extends NumberExpression {
   public LastFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 0) {
         throw new XPathParsingException("the 'last' function takes no arguments.");
      }
   }

   public double evaluateAsNumber(Context ctx) {
      return (double)ctx.size;
   }

   public void getSubExpressions(Collection out) {
   }
}
