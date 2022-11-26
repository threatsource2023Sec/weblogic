package org.apache.taglibs.standard.lang.jstl;

public class FloatingPointLiteral extends Literal {
   public FloatingPointLiteral(String pToken) {
      super(getValueFromToken(pToken));
   }

   static Object getValueFromToken(String pToken) {
      return new Double(pToken);
   }

   public String getExpressionString() {
      return this.getValue().toString();
   }
}
