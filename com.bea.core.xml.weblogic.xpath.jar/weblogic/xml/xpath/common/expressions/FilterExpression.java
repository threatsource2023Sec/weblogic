package weblogic.xml.xpath.common.expressions;

import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;

public final class FilterExpression extends NodesetExpression {
   private Expression mPrimary;
   private Expression[] mPredicates;

   public FilterExpression(Expression primary, Expression[] pred, Interrogator i) {
      super(i);
      if (primary == null) {
         throw new IllegalArgumentException("null primary");
      } else if (pred == null) {
         throw new IllegalArgumentException("null pred");
      } else {
         this.mPrimary = primary;
         this.mPredicates = pred;
      }
   }

   public final List evaluateAsNodeset(Context ctx) {
      List nodeset = this.mPrimary.evaluateAsNodeset(ctx);
      if (nodeset == null) {
         return null;
      } else {
         List holdNodeset = null;

         for(int p = 0; p < this.mPredicates.length; ++p) {
            ctx.scratchList.clear();
            ctx.size = nodeset.size();

            for(int n = 0; n < nodeset.size(); ++n) {
               ctx.position = n + 1;
               ctx.node = nodeset.get(n);
               if (this.mPredicates[p].getType() == 3) {
                  if ((double)(n + 1) == this.mPredicates[p].evaluateAsNumber(ctx)) {
                     ctx.scratchList.add(nodeset.get(n));
                  }
               } else if (this.mPredicates[p].evaluateAsBoolean(ctx)) {
                  ctx.scratchList.add(nodeset.get(n));
               }
            }

            holdNodeset = nodeset;
            nodeset = ctx.scratchList;
            ctx.scratchList = holdNodeset;
         }

         return nodeset;
      }
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mPrimary);
      this.mPrimary.getSubExpressions(out);

      for(int i = 0; i < this.mPredicates.length; ++i) {
         out.add(this.mPredicates[i]);
         this.mPredicates[i].getSubExpressions(out);
      }

   }
}
