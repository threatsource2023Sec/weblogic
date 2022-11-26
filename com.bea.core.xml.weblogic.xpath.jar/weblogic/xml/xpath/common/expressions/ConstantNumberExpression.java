package weblogic.xml.xpath.common.expressions;

import java.util.Collection;
import weblogic.xml.xpath.common.Context;

public final class ConstantNumberExpression extends NumberExpression {
   private double mValue;

   public ConstantNumberExpression(double value) {
      this.mValue = value;
   }

   public ConstantNumberExpression(String value) {
      this.mValue = StringExpression.string2double(value);
   }

   public double evaluateAsNumber(Context ctx) {
      return this.mValue;
   }

   public void getSubExpressions(Collection out) {
   }

   public String toString() {
      return String.valueOf(this.mValue);
   }
}
