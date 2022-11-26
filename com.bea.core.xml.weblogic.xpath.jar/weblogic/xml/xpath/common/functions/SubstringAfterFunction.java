package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.StringExpression;

public final class SubstringAfterFunction extends StringExpression {
   private Expression mString;
   private Expression mSubstring;

   public SubstringAfterFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 2) {
         throw new XPathParsingException("the 'substring-after' function requires two arguments.");
      } else {
         this.mString = args[0];
         this.mSubstring = args[1];
      }
   }

   public String evaluateAsString(Context ctx) {
      String str = this.mString.evaluateAsString(ctx);
      String sub = this.mSubstring.evaluateAsString(ctx);
      int i = this.mString.evaluateAsString(ctx).indexOf(sub);
      int j = i + sub.length();
      return i != -1 && j < str.length() ? str.substring(j) : "";
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mString);
      this.mString.getSubExpressions(out);
      out.add(this.mSubstring);
      this.mSubstring.getSubExpressions(out);
   }
}
