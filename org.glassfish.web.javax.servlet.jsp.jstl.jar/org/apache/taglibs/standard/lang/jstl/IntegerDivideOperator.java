package org.apache.taglibs.standard.lang.jstl;

public class IntegerDivideOperator extends BinaryOperator {
   public static final IntegerDivideOperator SINGLETON = new IntegerDivideOperator();

   public String getOperatorSymbol() {
      return "idiv";
   }

   public Object apply(Object pLeft, Object pRight, Object pContext, Logger pLogger) throws ELException {
      if (pLeft == null && pRight == null) {
         if (pLogger.isLoggingWarning()) {
            pLogger.logWarning(Constants.ARITH_OP_NULL, (Object)this.getOperatorSymbol());
         }

         return PrimitiveObjects.getInteger(0);
      } else {
         long left = Coercions.coerceToPrimitiveNumber(pLeft, Long.class, pLogger).longValue();
         long right = Coercions.coerceToPrimitiveNumber(pRight, Long.class, pLogger).longValue();

         try {
            return PrimitiveObjects.getLong(left / right);
         } catch (Exception var10) {
            if (pLogger.isLoggingError()) {
               pLogger.logError(Constants.ARITH_ERROR, (Object)this.getOperatorSymbol(), "" + left, "" + right);
            }

            return PrimitiveObjects.getInteger(0);
         }
      }
   }
}
