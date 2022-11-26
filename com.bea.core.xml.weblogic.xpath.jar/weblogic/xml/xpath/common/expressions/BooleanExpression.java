package weblogic.xml.xpath.common.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;

public abstract class BooleanExpression implements Expression {
   public final int getType() {
      return 2;
   }

   public final String evaluateAsString(Context ctx) {
      return this.evaluateAsBoolean(ctx) ? "true" : "false";
   }

   public final double evaluateAsNumber(Context ctx) {
      return this.evaluateAsBoolean(ctx) ? 1.0 : 0.0;
   }

   public final List evaluateAsNodeset(Context ctx) {
      return null;
   }

   public void getSubsRequiringStringConversion(int type, Collection c) {
      if (type != 1) {
         List subs = new ArrayList();
         this.getSubExpressions(subs);

         for(int i = 0; i < subs.size(); ++i) {
            Expression e = (Expression)subs.get(i);
            ((Expression)subs.get(i)).getSubsRequiringStringConversion(2, c);
         }

      }
   }

   public abstract boolean evaluateAsBoolean(Context var1);
}
