package org.apache.taglibs.standard.lang.jstl;

public abstract class BinaryOperator {
   public abstract String getOperatorSymbol();

   public abstract Object apply(Object var1, Object var2, Object var3, Logger var4) throws ELException;

   public boolean shouldEvaluate(Object pLeft) {
      return true;
   }

   public boolean shouldCoerceToBoolean() {
      return false;
   }
}
