package org.apache.taglibs.standard.lang.jstl;

public class NotOperator extends UnaryOperator {
   public static final NotOperator SINGLETON = new NotOperator();

   public String getOperatorSymbol() {
      return "not";
   }

   public Object apply(Object pValue, Object pContext, Logger pLogger) throws ELException {
      boolean val = Coercions.coerceToBoolean(pValue, pLogger);
      return PrimitiveObjects.getBoolean(!val);
   }
}
