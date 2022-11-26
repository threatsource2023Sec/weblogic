package weblogic.xml.process;

import java.util.Iterator;

public final class Functions {
   private static final boolean debug = false;
   private static final boolean verbose = false;

   public static String value(ProcessingContext pctx) {
      if (pctx.isText()) {
         return pctx.getValue();
      } else {
         StringBuffer val = new StringBuffer();
         Iterator ci = pctx.getChildNodes().iterator();

         while(ci.hasNext()) {
            ProcessingContext c = (ProcessingContext)ci.next();
            if (c.isText()) {
               val.append(c.getValue());
            }
         }

         return val.toString();
      }
   }

   public static String value(ProcessingContext pctx, String xpathExpr) throws ExpressionSyntaxException {
      xpathExpr = xpathExpr.trim();
      if (xpathExpr.charAt(0) == '@') {
         return attrValue(pctx, xpathExpr.substring(1));
      } else {
         throw new ExpressionSyntaxException("Unrecognized expression: " + xpathExpr);
      }
   }

   private static String attrValue(ProcessingContext pctx, String attrName) {
      return pctx.getAttribute(attrName);
   }
}
