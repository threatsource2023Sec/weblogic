package weblogic.xml.xpath.common.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;

public abstract class NodesetExpression implements Expression {
   protected Interrogator mInterrogator;

   protected NodesetExpression(Interrogator i) {
      if (i == null) {
         throw new IllegalArgumentException("null interrogator");
      } else {
         this.mInterrogator = i;
      }
   }

   protected NodesetExpression() {
   }

   public final int getType() {
      return 1;
   }

   public final boolean evaluateAsBoolean(Context ctx) {
      List l = this.evaluateAsNodeset(ctx);
      return l != null && l.size() > 0;
   }

   public final double evaluateAsNumber(Context ctx) {
      return StringExpression.string2double(this.evaluateAsString(ctx));
   }

   public final String evaluateAsString(Context ctx) {
      List l = this.evaluateAsNodeset(ctx);
      return l != null && l.size() >= 1 ? this.mInterrogator.getNodeStringValue(l.get(0)) : "";
   }

   public void getSubsRequiringStringConversion(int type, Collection c) {
      List subs = new ArrayList();
      this.getSubExpressions(subs);

      for(int i = 0; i < subs.size(); ++i) {
         ((Expression)subs.get(i)).getSubsRequiringStringConversion(1, c);
      }

   }
}
