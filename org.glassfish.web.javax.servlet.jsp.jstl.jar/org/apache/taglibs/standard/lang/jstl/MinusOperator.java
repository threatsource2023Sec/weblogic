package org.apache.taglibs.standard.lang.jstl;

public class MinusOperator extends ArithmeticOperator {
   public static final MinusOperator SINGLETON = new MinusOperator();

   public String getOperatorSymbol() {
      return "-";
   }

   public double apply(double pLeft, double pRight, Logger pLogger) {
      return pLeft - pRight;
   }

   public long apply(long pLeft, long pRight, Logger pLogger) {
      return pLeft - pRight;
   }
}
