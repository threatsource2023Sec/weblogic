package org.apache.taglibs.standard.lang.jstl;

public class AndOperator extends BinaryOperator {
   public static final AndOperator SINGLETON = new AndOperator();

   public String getOperatorSymbol() {
      return "and";
   }

   public Object apply(Object pLeft, Object pRight, Object pContext, Logger pLogger) throws ELException {
      boolean left = Coercions.coerceToBoolean(pLeft, pLogger);
      boolean right = Coercions.coerceToBoolean(pRight, pLogger);
      return PrimitiveObjects.getBoolean(left && right);
   }

   public boolean shouldEvaluate(Object pLeft) {
      return pLeft instanceof Boolean && (Boolean)pLeft;
   }

   public boolean shouldCoerceToBoolean() {
      return true;
   }
}
