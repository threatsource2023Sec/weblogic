package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.BooleanExpression;

public final class ContainsFunction extends BooleanExpression {
   private Expression mString;
   private Expression mSubstring;

   public ContainsFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 2) {
         throw new XPathParsingException("the 'contains' function requires two arguments.");
      } else {
         this.mString = args[0];
         this.mSubstring = args[1];
      }
   }

   public boolean evaluateAsBoolean(Context ctx) {
      return this.mString.evaluateAsString(ctx).indexOf(this.mSubstring.evaluateAsString(ctx)) != -1;
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mString);
      out.add(this.mSubstring);
      this.mString.getSubExpressions(out);
      this.mSubstring.getSubExpressions(out);
   }

   public String toString() {
      return "contains(" + this.mString + "," + this.mSubstring + ")";
   }
}
