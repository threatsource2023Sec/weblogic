package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.NumberExpression;

public final class PositionFunction extends NumberExpression {
   public PositionFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 0) {
         throw new XPathParsingException("the 'position' function takes no arguments.");
      }
   }

   public double evaluateAsNumber(Context ctx) {
      return (double)ctx.position;
   }

   public void getSubExpressions(Collection out) {
   }

   public String toString() {
      return "position()";
   }
}
