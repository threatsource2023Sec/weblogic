package weblogic.xml.xpath.common.functions;

import java.io.StringWriter;
import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.expressions.StringExpression;

public final class NormalizeSpaceFunction extends StringExpression implements InterrogatingFunction {
   private Interrogator mInterrogator = null;
   private Expression mArg;

   public NormalizeSpaceFunction(Expression[] args) throws XPathParsingException {
      if (args.length > 1) {
         throw new XPathParsingException("the 'normalize-space' function requires zero or one arguments.");
      } else {
         this.mArg = args.length == 1 ? args[0] : null;
      }
   }

   public String evaluateAsString(Context ctx) {
      String str = this.mArg == null ? this.mInterrogator.getNodeStringValue(ctx.node) : this.mArg.evaluateAsString(ctx);
      str = str.trim();
      StringWriter out = new StringWriter();
      boolean alreadySpacing = false;

      for(int i = 0; i < str.length(); ++i) {
         char c;
         switch (c = str.charAt(i)) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
               if (!alreadySpacing) {
                  out.write(32);
                  alreadySpacing = true;
               }
               break;
            default:
               alreadySpacing = false;
               out.write(c);
         }
      }

      return out.toString();
   }

   public void getSubExpressions(Collection out) {
      if (this.mArg != null) {
         out.add(this.mArg);
         this.mArg.getSubExpressions(out);
      }

   }

   public void getSubsRequiringStringConversion(int type, Collection c) {
      if (type != 1) {
         if (this.mArg == null) {
            c.add(CONTEXT_NODE_DUMMY);
         } else {
            this.mArg.getSubsRequiringStringConversion(4, c);
         }

      }
   }

   public void setInterrogator(Interrogator i) {
      this.mInterrogator = i;
   }
}
