package weblogic.xml.xpath.common.expressions;

import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Interrogator;

public final class ConstantNodesetExpression extends NodesetExpression {
   private List mValue;

   public ConstantNodesetExpression(List value, Interrogator i) {
      super(i);
      this.mValue = value;
   }

   public List evaluateAsNodeset(Context ctx) {
      return this.mValue;
   }

   public void getSubExpressions(Collection out) {
   }
}
