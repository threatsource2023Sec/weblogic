package org.apache.taglibs.standard.lang.jstl;

public abstract class ArithmeticOperator extends BinaryOperator {
   public Object apply(Object pLeft, Object pRight, Object pContext, Logger pLogger) throws ELException {
      return Coercions.applyArithmeticOperator(pLeft, pRight, this, pLogger);
   }

   public abstract double apply(double var1, double var3, Logger var5);

   public abstract long apply(long var1, long var3, Logger var5);
}
