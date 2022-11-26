package weblogic.xml.xpath.common.functions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.expressions.NodesetExpression;
import weblogic.xml.xpath.common.utils.EmptyList;

public final class IdFunction extends NodesetExpression implements InterrogatingFunction {
   private Expression mArg;

   public IdFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 1) {
         throw new XPathParsingException("the 'id' function requires a single argument.");
      } else {
         this.mArg = args[0];
      }
   }

   public void setInterrogator(Interrogator i) {
      super.mInterrogator = i;
   }

   public List evaluateAsNodeset(Context ctx) {
      List out = null;
      String idList = this.mArg.evaluateAsString(ctx);
      StringTokenizer t = new StringTokenizer(idList, " \t\n\r");

      while(t.hasMoreTokens()) {
         Object node = this.mInterrogator.getNodeById(ctx, t.nextToken());
         if (node != null) {
            if (out == null) {
               out = new ArrayList();
            }

            out.add(node);
         }
      }

      return (List)(out == null ? EmptyList.getInstance() : out);
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mArg);
      this.mArg.getSubExpressions(out);
   }
}
