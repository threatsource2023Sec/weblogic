package org.apache.taglibs.standard.lang.jstl;

public class BooleanLiteral extends Literal {
   public static final BooleanLiteral TRUE = new BooleanLiteral("true");
   public static final BooleanLiteral FALSE = new BooleanLiteral("false");

   public BooleanLiteral(String pToken) {
      super(getValueFromToken(pToken));
   }

   static Object getValueFromToken(String pToken) {
      return "true".equals(pToken) ? Boolean.TRUE : Boolean.FALSE;
   }

   public String getExpressionString() {
      return this.getValue() == Boolean.TRUE ? "true" : "false";
   }
}
