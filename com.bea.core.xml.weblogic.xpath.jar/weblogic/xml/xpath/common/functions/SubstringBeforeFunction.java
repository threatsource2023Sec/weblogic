package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.StringExpression;

public final class SubstringBeforeFunction extends StringExpression {
   private Expression mString;
   private Expression mSubstring;

   public SubstringBeforeFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 2) {
         throw new XPathParsingException("the 'substring-before' function requires two arguments.");
      } else {
         this.mString = args[0];
         this.mSubstring = args[1];
      }
   }

   public String evaluateAsString(Context ctx) {
      String str = this.mString.evaluateAsString(ctx);
      int i = str.indexOf(this.mSubstring.evaluateAsString(ctx));
      return i <= 0 ? "" : str.substring(0, i);
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mString);
      this.mString.getSubExpressions(out);
      out.add(this.mSubstring);
      this.mSubstring.getSubExpressions(out);
   }
}
