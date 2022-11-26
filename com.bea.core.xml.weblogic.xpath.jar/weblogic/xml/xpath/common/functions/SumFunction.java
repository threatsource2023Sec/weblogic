package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.expressions.NumberExpression;
import weblogic.xml.xpath.common.expressions.StringExpression;

public final class SumFunction extends NumberExpression implements InterrogatingFunction {
   private Expression mArg;
   private Interrogator mInterrogator = null;

   public SumFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 1) {
         throw new XPathParsingException("the 'sum' function requires a single argument.");
      } else {
         this.mArg = args[0];
      }
   }

   public double evaluateAsNumber(Context ctx) {
      List nodeset = this.mArg.evaluateAsNodeset(ctx);
      double total = 0.0;

      for(int i = 0; i < nodeset.size(); ++i) {
         total += StringExpression.string2double(this.mInterrogator.getNodeStringValue(nodeset.get(i)));
      }

      return total;
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mArg);
      this.mArg.getSubExpressions(out);
   }

   public void getSubsRequiringStringConversion(int type, Collection c) {
      c.add(this.mArg);
      this.mArg.getSubsRequiringStringConversion(4, c);
   }

   public void setInterrogator(Interrogator i) {
      this.mInterrogator = i;
   }
}
