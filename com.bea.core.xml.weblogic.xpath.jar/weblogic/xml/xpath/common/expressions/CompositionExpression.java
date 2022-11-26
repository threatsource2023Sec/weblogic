package weblogic.xml.xpath.common.expressions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;

public final class CompositionExpression extends NodesetExpression {
   private Expression mFilterExpr;
   private Expression mPath;

   public CompositionExpression(Expression filter, Expression path, Interrogator i) {
      super(i);
      this.mFilterExpr = filter;
      this.mPath = path;
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mFilterExpr);
      out.add(this.mPath);
      this.mFilterExpr.getSubExpressions(out);
      this.mPath.getSubExpressions(out);
   }

   public List evaluateAsNodeset(Context ctx) {
      Object originalNode = ctx.node;
      List out = new ArrayList();
      List list = this.mFilterExpr.evaluateAsNodeset(ctx);
      int i = 0;

      for(int iL = list.size(); i < iL; ++i) {
         ctx.node = list.get(i);
         out.addAll(this.mPath.evaluateAsNodeset(ctx));
      }

      ctx.node = originalNode;
      return out;
   }
}
