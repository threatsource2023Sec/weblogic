package weblogic.xml.xpath.common.expressions;

import java.util.List;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;

public final class InequalityExpression extends BinaryExpression {
   private int mEvaluationType = super.getEvaluationType();
   private Interrogator mInterrogator;

   public InequalityExpression(Expression left, Expression right, Interrogator i) {
      super(left, right);
      if (i == null) {
         throw new IllegalArgumentException("null interrogator");
      } else {
         this.mInterrogator = i;
      }
   }

   public final boolean evaluateAsBoolean(Context ctx) {
      String s2;
      switch (this.mEvaluationType) {
         case 1:
            List c1 = this.mLeft.evaluateAsNodeset(ctx);
            List c2 = this.mRight.evaluateAsNodeset(ctx);
            if (c1 != null && c2 != null) {
               String[] a1 = new String[c1.size()];
               String[] a2 = new String[c2.size()];

               for(int i1 = 0; i1 < a1.length; ++i1) {
                  String s1 = a1[i1] != null ? a1[i1] : (a1[i1] = this.mInterrogator.getNodeStringValue(c1.get(i1)));

                  for(int i2 = 0; i2 < a2.length; ++i2) {
                     s2 = a2[i2] != null ? a2[i2] : (a2[i2] = this.mInterrogator.getNodeStringValue(c2.get(i2)));
                     if (!s1.equals(s2)) {
                        return true;
                     }
                  }
               }

               return false;
            } else {
               return false;
            }
         case 2:
            List l = this.mLeft.evaluateAsNodeset(ctx);
            if (l == null) {
               return false;
            } else {
               double n = this.mRight.evaluateAsNumber(ctx);
               int i = 0;

               for(int iL = l.size(); i < iL; ++i) {
                  if (StringExpression.string2double(this.mInterrogator.getNodeStringValue(l.get(i))) != n) {
                     return true;
                  }
               }

               return false;
            }
         case 3:
            s2 = this.mRight.evaluateAsString(ctx);
            List nodeset = this.mLeft.evaluateAsNodeset(ctx);
            int i = 0;

            for(int iL = nodeset.size(); i < iL; ++i) {
               if (!s2.equals(this.mInterrogator.getNodeStringValue(nodeset.get(i)))) {
                  return true;
               }
            }

            return false;
         case 4:
         case 5:
            return this.mLeft.evaluateAsBoolean(ctx) != this.mRight.evaluateAsBoolean(ctx);
         case 6:
            return this.mLeft.evaluateAsNumber(ctx) != this.mRight.evaluateAsNumber(ctx);
         case 7:
            return !this.mLeft.evaluateAsString(ctx).equals(this.mRight.evaluateAsString(ctx));
         default:
            return false;
      }
   }

   public String toString() {
      return "(" + this.mLeft.toString() + " = " + this.mRight.toString() + ")";
   }
}
