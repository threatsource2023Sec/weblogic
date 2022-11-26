package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import java.util.List;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.expressions.StringExpression;

public final class NamespaceUriFunction extends StringExpression implements InterrogatingFunction {
   private Interrogator mInterrogator = null;
   private Expression mArg;

   public NamespaceUriFunction(Expression[] args) throws XPathParsingException {
      if (args.length > 1) {
         throw new XPathParsingException("the 'namspace-uri' function requires zero or one arguments.");
      } else {
         this.mArg = args.length == 1 ? args[0] : null;
      }
   }

   public String evaluateAsString(Context ctx) {
      if (this.mArg == null) {
         String uri = this.mInterrogator.getNamespaceURI(ctx.node);
         return uri == null ? "" : uri;
      } else {
         List nodeset = this.mArg.evaluateAsNodeset(ctx);
         if (nodeset != null && nodeset.size() != 0) {
            String uri = this.mInterrogator.getNamespaceURI(nodeset.get(0));
            return uri == null ? "" : uri;
         } else {
            return "";
         }
      }
   }

   public void getSubExpressions(Collection out) {
      if (this.mArg != null) {
         out.add(this.mArg);
         this.mArg.getSubExpressions(out);
      }

   }

   public void setInterrogator(Interrogator i) {
      this.mInterrogator = i;
   }

   public String toString() {
      return "namspace-uri(" + (this.mArg != null ? this.mArg.toString() : "") + ")";
   }
}
