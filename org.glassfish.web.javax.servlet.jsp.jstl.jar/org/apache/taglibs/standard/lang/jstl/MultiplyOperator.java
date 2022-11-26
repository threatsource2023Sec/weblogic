package org.apache.taglibs.standard.lang.jstl;

public class MultiplyOperator extends ArithmeticOperator {
   public static final MultiplyOperator SINGLETON = new MultiplyOperator();

   public String getOperatorSymbol() {
      return "*";
   }

   public double apply(double pLeft, double pRight, Logger pLogger) {
      return pLeft * pRight;
   }

   public long apply(long pLeft, long pRight, Logger pLogger) {
      return pLeft * pRight;
   }
}
