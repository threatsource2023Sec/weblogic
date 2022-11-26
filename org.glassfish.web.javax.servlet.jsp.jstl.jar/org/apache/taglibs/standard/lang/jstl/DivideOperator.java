package org.apache.taglibs.standard.lang.jstl;

public class DivideOperator extends BinaryOperator {
   public static final DivideOperator SINGLETON = new DivideOperator();

   public String getOperatorSymbol() {
      return "/";
   }

   public Object apply(Object pLeft, Object pRight, Object pContext, Logger pLogger) throws ELException {
      if (pLeft == null && pRight == null) {
         if (pLogger.isLoggingWarning()) {
            pLogger.logWarning(Constants.ARITH_OP_NULL, (Object)this.getOperatorSymbol());
         }

         return PrimitiveObjects.getInteger(0);
      } else {
         double left = Coercions.coerceToPrimitiveNumber(pLeft, Double.class, pLogger).doubleValue();
         double right = Coercions.coerceToPrimitiveNumber(pRight, Double.class, pLogger).doubleValue();

         try {
            return PrimitiveObjects.getDouble(left / right);
         } catch (Exception var10) {
            if (pLogger.isLoggingError()) {
               pLogger.logError(Constants.ARITH_ERROR, (Object)this.getOperatorSymbol(), "" + left, "" + right);
            }

            return PrimitiveObjects.getInteger(0);
         }
      }
   }
}
