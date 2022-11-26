package weblogic.xml.xpath.common.functions;

import java.io.StringWriter;
import java.util.Collection;
import weblogic.xml.xpath.XPathParsingException;
import weblogic.xml.xpath.common.Context;
import weblogic.xml.xpath.common.Expression;
import weblogic.xml.xpath.common.expressions.StringExpression;

public final class TranslateFunction extends StringExpression {
   private Expression mSource;
   private Expression mFrom;
   private Expression mTo;

   public TranslateFunction(Expression[] args) throws XPathParsingException {
      if (args.length != 3) {
         throw new XPathParsingException("the 'translate' function requires three arguments.");
      } else {
         this.mSource = args[0];
         this.mFrom = args[1];
         this.mTo = args[2];
      }
   }

   public String evaluateAsString(Context ctx) {
      ctx.scratchMap.clear();
      String from = this.mFrom.evaluateAsString(ctx);
      String to = this.mTo.evaluateAsString(ctx);
      int toLength = to.length();

      int i;
      for(i = from.length() - 1; i >= toLength; --i) {
         ctx.scratchMap.put(new Character(from.charAt(i)), (Object)null);
      }

      while(i >= 0) {
         ctx.scratchMap.put(new Character(from.charAt(i)), new Character(to.charAt(i)));
         --i;
      }

      StringWriter out = new StringWriter();
      String source = this.mSource.evaluateAsString(ctx);
      int i = 0;

      for(int iL = source.length(); i < iL; ++i) {
         Character c = new Character(source.charAt(i));
         if (ctx.scratchMap.containsKey(c)) {
            c = (Character)ctx.scratchMap.get(c);
            if (c != null) {
               out.write(c);
            }
         } else {
            out.write(c);
         }
      }

      return out.toString();
   }

   public void getSubExpressions(Collection out) {
      out.add(this.mSource);
      this.mSource.getSubExpressions(out);
      out.add(this.mFrom);
      this.mFrom.getSubExpressions(out);
      out.add(this.mTo);
      this.mTo.getSubExpressions(out);
   }
}
