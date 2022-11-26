package org.apache.taglibs.standard.lang.jstl;

public class UnaryMinusOperator extends UnaryOperator {
   public static final UnaryMinusOperator SINGLETON = new UnaryMinusOperator();

   public String getOperatorSymbol() {
      return "-";
   }

   public Object apply(Object pValue, Object pContext, Logger pLogger) throws ELException {
      if (pValue == null) {
         return PrimitiveObjects.getInteger(0);
      } else if (pValue instanceof String) {
         if (Coercions.isFloatingPointString(pValue)) {
            double dval = Coercions.coerceToPrimitiveNumber(pValue, Double.class, pLogger).doubleValue();
            return PrimitiveObjects.getDouble(-dval);
         } else {
            long lval = Coercions.coerceToPrimitiveNumber(pValue, Long.class, pLogger).longValue();
            return PrimitiveObjects.getLong(-lval);
         }
      } else if (pValue instanceof Byte) {
         return PrimitiveObjects.getByte((byte)(-(Byte)pValue));
      } else if (pValue instanceof Short) {
         return PrimitiveObjects.getShort((short)(-(Short)pValue));
      } else if (pValue instanceof Integer) {
         return PrimitiveObjects.getInteger(-(Integer)pValue);
      } else if (pValue instanceof Long) {
         return PrimitiveObjects.getLong(-(Long)pValue);
      } else if (pValue instanceof Float) {
         return PrimitiveObjects.getFloat(-(Float)pValue);
      } else if (pValue instanceof Double) {
         return PrimitiveObjects.getDouble(-(Double)pValue);
      } else {
         if (pLogger.isLoggingError()) {
            pLogger.logError(Constants.UNARY_OP_BAD_TYPE, (Object)this.getOperatorSymbol(), pValue.getClass().getName());
         }

         return PrimitiveObjects.getInteger(0);
      }
   }
}
