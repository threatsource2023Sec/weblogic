package org.apache.taglibs.standard.lang.jstl;

public class PlusOperator extends ArithmeticOperator {
   public static final PlusOperator SINGLETON = new PlusOperator();

   public String getOperatorSymbol() {
      return "+";
   }

   public double apply(double pLeft, double pRight, Logger pLogger) {
      return pLeft + pRight;
   }

   public long apply(long pLeft, long pRight, Logger pLogger) {
      return pLeft + pRight;
   }
}
