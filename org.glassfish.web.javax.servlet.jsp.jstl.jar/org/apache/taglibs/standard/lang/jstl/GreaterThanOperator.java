package org.apache.taglibs.standard.lang.jstl;

public class GreaterThanOperator extends RelationalOperator {
   public static final GreaterThanOperator SINGLETON = new GreaterThanOperator();

   public String getOperatorSymbol() {
      return ">";
   }

   public Object apply(Object pLeft, Object pRight, Object pContext, Logger pLogger) throws ELException {
      if (pLeft == pRight) {
         return Boolean.FALSE;
      } else {
         return pLeft != null && pRight != null ? super.apply(pLeft, pRight, pContext, pLogger) : Boolean.FALSE;
      }
   }

   public boolean apply(double pLeft, double pRight, Logger pLogger) {
      return pLeft > pRight;
   }

   public boolean apply(long pLeft, long pRight, Logger pLogger) {
      return pLeft > pRight;
   }

   public boolean apply(String pLeft, String pRight, Logger pLogger) {
      return pLeft.compareTo(pRight) > 0;
   }
}
