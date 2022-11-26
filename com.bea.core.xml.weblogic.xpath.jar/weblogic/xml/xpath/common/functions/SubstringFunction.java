package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.StringExpression;

public final class SubstringFunction extends StringExpression {
   private Expression mString;
   private Expression mBegin;
   private Expression mLength;

   public SubstringFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 2 && args.length != 3) {
         throw new XPathParsingException("the 'substring' function requires two or three arguments.");
      } else {
         this.mString = args[0];
         this.mBegin = args[1];
         this.mLength = args.length == 3 ? args[2] : null;
      }
   }

   public String evaluateAsString(Context ctx) {
      String str = this.mString.evaluateAsString(ctx);
      double begin = RoundFunction.round(this.mBegin.evaluateAsNumber(ctx));
      if (Double.isNaN(begin)) {
         return "";
      } else if (Double.isInfinite(begin)) {
         return "";
      } else if (begin > (double)str.length()) {
         return "";
      } else if (this.mLength == null) {
         begin = begin <= 1.0 ? 0.0 : begin - 1.0;
         return str.substring((int)begin);
      } else {
         double length = RoundFunction.round(this.mLength.evaluateAsNumber(ctx));
         if (begin < 1.0) {
            length += begin - 1.0;
            begin = 1.0;
         }

         if (length <= 0.0) {
            return "";
         } else {
            --begin;
            return begin + length >= (double)str.length() ? str.substring((int)begin) : str.substring((int)begin, (int)(begin + length));
         }
      }
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mString);
      this.mString.getSubExpressions(out);
      out.add(this.mBegin);
      this.mBegin.getSubExpressions(out);
      if (this.mLength != null) {
         out.add(this.mLength);
         this.mLength.getSubExpressions(out);
      }

   }
}
