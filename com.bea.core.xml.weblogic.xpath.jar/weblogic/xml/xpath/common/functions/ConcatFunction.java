package weblogic.xml.xpath.common.functions;

import java.io.StringWriter;
import java.util.Collection;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.StringExpression;

public final class ConcatFunction extends StringExpression {
   private Expression[] mStrings;

   public ConcatFunction(Expression[] strings) {
      for(int i = 0; i < strings.length; ++i) {
         if (strings[i] == null) {
            throw new IllegalArgumentException("" + i);
         }
      }

      this.mStrings = strings;
   }

   public String evaluateAsString(Context ctx) {
      StringBuffer out = new StringBuffer();

      for(int i = 0; i < this.mStrings.length; ++i) {
         out.append(this.mStrings[i].evaluateAsString(ctx));
      }

      return out.toString();
   }

   public void getSubExpressions(Collection out) {
      for(int i = 0; i < this.mStrings.length; ++i) {
         out.add(this.mStrings[i]);
         this.mStrings[i].getSubExpressions(out);
      }

   }

   public String toString() {
      StringWriter out = new StringWriter();
      out.write("concat(");

      for(int i = 0; i < this.mStrings.length; ++i) {
         out.write(this.mStrings[i].toString());
         if (i + 1 < this.mStrings.length) {
            out.write(",");
         }
      }

      out.write(")");
      return out.toString();
   }
}
