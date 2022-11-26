package weblogic.xml.xpath.common.functions;

import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.Interrogator;
import weblogic.xml.xpath.common.expressions.BooleanExpression;

public final class LangFunction extends BooleanExpression implements InterrogatingFunction {
   private Expression mArg;
   private Interrogator mInterrogator = null;

   public LangFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 1) {
         throw new XPathParsingException("the 'lang' function requires a single argument.");
      } else {
         this.mArg = args[0];
      }
   }

   public boolean evaluateAsBoolean(Context ctx) {
      String lang = this.getLanguage(ctx);
      if (lang == null) {
         return false;
      } else {
         String arg = this.mArg.evaluateAsString(ctx);
         if (arg == null) {
            return false;
         } else if (arg.equalsIgnoreCase(lang)) {
            return true;
         } else {
            arg = arg.toLowerCase();
            lang = lang.toLowerCase();
            return lang.toLowerCase().startsWith(arg.toLowerCase() + "-");
         }
      }
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mArg);
      this.mArg.getSubExpressions(out);
   }

   public void setInterrogator(Interrogator i) {
      this.mInterrogator = i;
   }

   private final String getLanguage(Context ctx) {
      Object node = ctx.node;

      do {
         String langVal = this.mInterrogator.getAttributeValue(node, "xml", "lang");
         if (langVal != null) {
            return langVal;
         }

         node = this.mInterrogator.getParent(node);
      } while(node != null);

      return null;
   }
}
