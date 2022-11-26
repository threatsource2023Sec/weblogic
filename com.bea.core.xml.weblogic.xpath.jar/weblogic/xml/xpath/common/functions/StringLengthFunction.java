package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.expressions.NumberExpression;

public final class StringLengthFunction extends NumberExpression implements InterrogatingFunction {
   private Interrogator mInterrogator = null;
   private Expression mArg;

   public StringLengthFunction(Expression[] args) throws XPathParsingException {
      if (args.length > 1) {
         throw new XPathParsingException("the 'string-length' function requires zero or one arguments.");
      } else {
         this.mArg = args.length == 1 ? args[0] : null;
      }
   }

   public double evaluateAsNumber(Context ctx) {
      return this.mArg == null ? (double)this.mInterrogator.getNodeStringValue(ctx.node).length() : (double)this.mArg.evaluateAsString(ctx).length();
   }

   public void getSubExpressions(Collection out) {
      if (this.mArg != null) {
         out.add(this.mArg);
         this.mArg.getSubExpressions(out);
      }

   }

   public void setInterrogator(Interrogator i) {
      this.mInterrogator = i;
   }
}
