package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.NumberExpression;

public final class CountFunction extends NumberExpression {
   private Expression mArg;

   public CountFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 1) {
         throw new XPathParsingException("the 'count' function requires a single argument.");
      } else {
         this.mArg = args[0];
      }
   }

   public double evaluateAsNumber(Context ctx) {
      return (double)this.mArg.evaluateAsNodeset(ctx).size();
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mArg);
      this.mArg.getSubExpressions(out);
   }
}
