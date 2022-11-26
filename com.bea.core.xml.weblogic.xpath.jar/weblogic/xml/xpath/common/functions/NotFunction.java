package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.BooleanExpression;

public final class NotFunction extends BooleanExpression {
   private Expression mArg;

   public NotFunction(Expression arg) {
      this.mArg = arg;
   }

   public NotFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 1) {
         throw new XPathParsingException("the 'count' function requires a single argument.");
      } else {
         this.mArg = args[0];
      }
   }

   public boolean evaluateAsBoolean(Context ctx) {
      return !this.mArg.evaluateAsBoolean(ctx);
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mArg);
      this.mArg.getSubExpressions(out);
   }

   public String toString() {
      return "not(" + this.mArg + ")";
   }
}
