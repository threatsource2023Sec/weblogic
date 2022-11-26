package org.apache.taglibs.standard.lang.jstl;

public class IntegerLiteral extends Literal {
   public IntegerLiteral(String pToken) {
      super(getValueFromToken(pToken));
   }

   static Object getValueFromToken(String pToken) {
      return Long.valueOf(pToken);
   }

   public String getExpressionString() {
      return this.getValue().toString();
   }
}
