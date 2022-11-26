package weblogic.xml.xpath.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.common.expressions.NodesetExpression;
import weblogic.xml.xpath.parser.LocationPathModel;

public final class LocationPath extends NodesetExpression implements LocationPathModel {
   private Step[] mSteps = null;

   LocationPath(Step[] steps, Interrogator inter) {
      super(inter);
      if (steps == null) {
         throw new IllegalArgumentException("null steps");
      } else if (steps.length == 0) {
         throw new IllegalArgumentException("empty steps");
      } else {
         for(int i = 0; i < steps.length; ++i) {
            if (steps[i] == null) {
               throw new IllegalArgumentException("step " + (i + 1) + " of " + steps.length + " is null");
            }
         }

         this.mSteps = steps;
      }
   }

   public final List evaluateAsNodeset(Context ctx) {
      Object originalNode = ctx.node;
      List resultList = this.mSteps[0].getMatches(ctx);

      for(int i = 1; i < this.mSteps.length; ++i) {
         List iterList = resultList;
         resultList = new ArrayList();

         for(int j = 0; j < ((List)iterList).size(); ++j) {
            ctx.node = ((List)iterList).get(j);
            ((List)resultList).addAll(this.mSteps[i].getMatches(ctx));
         }
      }

      ctx.node = originalNode;
      return (List)resultList;
   }

   public void getSubExpressions(Collection out) {
      throw new IllegalStateException();
   }
}
