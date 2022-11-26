package org.apache.taglibs.standard.lang.jstl;

public class NullLiteral extends Literal {
   public static final NullLiteral SINGLETON = new NullLiteral();

   public NullLiteral() {
      super((Object)null);
   }

   public String getExpressionString() {
      return "null";
   }
}
