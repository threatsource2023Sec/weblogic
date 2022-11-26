package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.NumberExpression;

public final class RoundFunction extends NumberExpression {
   private Expression mArg;

   public RoundFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 1) {
         throw new XPathParsingException("the 'round' function requires a single argument.");
      } else {
         this.mArg = args[0];
      }
   }

   public double evaluateAsNumber(Context ctx) {
      return round(this.mArg.evaluateAsNumber(ctx));
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mArg);
      this.mArg.getSubExpressions(out);
   }

   static final double round(double val) {
      return !Double.isNaN(val) && !Double.isInfinite(val) ? (double)Math.round(val) : val;
   }
}
