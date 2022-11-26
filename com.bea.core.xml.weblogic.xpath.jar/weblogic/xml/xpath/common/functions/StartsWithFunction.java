package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.BooleanExpression;

public final class StartsWithFunction extends BooleanExpression {
   private Expression mString;
   private Expression mSubstring;

   public StartsWithFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 2) {
         throw new XPathParsingException("the 'starts-with' function requires two arguments.");
      } else {
         this.mString = args[0];
         this.mSubstring = args[1];
      }
   }

   public boolean evaluateAsBoolean(Context ctx) {
      return this.mString.evaluateAsString(ctx).startsWith(this.mSubstring.evaluateAsString(ctx));
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mString);
      this.mString.getSubExpressions(out);
      out.add(this.mSubstring);
      this.mSubstring.getSubExpressions(out);
   }
}
