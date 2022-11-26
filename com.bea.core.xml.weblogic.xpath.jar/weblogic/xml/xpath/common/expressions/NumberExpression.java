package weblogic.xml.xpath.common.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;

public abstract class NumberExpression implements Expression {
   public final int getType() {
      return 3;
   }

   public final boolean evaluateAsBoolean(Context ctx) {
      double d = this.evaluateAsNumber(ctx);
      return d != 0.0 && !Double.isNaN(d);
   }

   public final String evaluateAsString(Context ctx) {
      double f = this.evaluateAsNumber(ctx);
      if (Double.isInfinite(f)) {
         return f > 0.0 ? "Infinity" : "-Infinity";
      } else if (Double.isNaN(f)) {
         return "NaN";
      } else {
         return (double)((int)f) == f ? Integer.toString((int)f) : Double.toString(f);
      }
   }

   public final List evaluateAsNodeset(Context ctx) {
      return null;
   }

   public void getSubsRequiringStringConversion(int type, Collection c) {
      if (type != 1) {
         List subs = new ArrayList();
         this.getSubExpressions(subs);

         for(int i = 0; i < subs.size(); ++i) {
            ((Expression)subs.get(i)).getSubsRequiringStringConversion(3, c);
         }

      }
   }

   public abstract double evaluateAsNumber(Context var1);
}
