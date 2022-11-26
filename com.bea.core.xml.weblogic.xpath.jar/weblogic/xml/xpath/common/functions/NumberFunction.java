package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.expressions.NumberExpression;
import weblogic.xml.xpath.common.expressions.StringExpression;

public final class NumberFunction extends NumberExpression implements InterrogatingFunction {
   private Expression mArg;
   private Interrogator mInterrogator = null;

   public NumberFunction(Expression[] args) throws XPathParsingException {
      if (args.length > 1) {
         throw new XPathParsingException("the 'number' function requires zero or one arguments.");
      } else {
         this.mArg = args.length == 1 ? args[0] : null;
      }
   }

   public double evaluateAsNumber(Context ctx) {
      return this.mArg == null ? StringExpression.string2double(this.mInterrogator.getNodeStringValue(ctx.node)) : this.mArg.evaluateAsNumber(ctx);
   }

   public void getSubExpressions(Collection out) {
      if (this.mArg != null) {
         out.add(this.mArg);
         this.mArg.getSubExpressions(out);
      }

   }

   public void getSubsRequiringStringConversion(int type, Collection c) {
      if (type != 1) {
         if (this.mArg == null) {
            c.add(CONTEXT_NODE_DUMMY);
         } else {
            this.mArg.getSubsRequiringStringConversion(3, c);
         }

      }
   }

   public void setInterrogator(Interrogator i) {
      this.mInterrogator = i;
   }
}
