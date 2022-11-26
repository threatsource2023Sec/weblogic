package weblogic.xml.xpath.common.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;

public abstract class StringExpression implements Expression {
   public final int getType() {
      return 4;
   }

   public final boolean evaluateAsBoolean(Context ctx) {
      return this.evaluateAsString(ctx).length() != 0;
   }

   public final double evaluateAsNumber(Context ctx) {
      return string2double(this.evaluateAsString(ctx));
   }

   public final List evaluateAsNodeset(Context ctx) {
      return null;
   }

   public void getSubsRequiringStringConversion(int type, Collection c) {
      if (type != 1) {
         List subs = new ArrayList();
         this.getSubExpressions(subs);

         for(int i = 0; i < subs.size(); ++i) {
            c.add(subs.get(i));
            ((Expression)subs.get(i)).getSubsRequiringStringConversion(4, c);
         }

      }
   }

   public abstract String evaluateAsString(Context var1);

   public static double string2double(String s) {
      try {
         return Double.parseDouble(s);
      } catch (NumberFormatException var2) {
         return Double.NaN;
      }
   }
}
