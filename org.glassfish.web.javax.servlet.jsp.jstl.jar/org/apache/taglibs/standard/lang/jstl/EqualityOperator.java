package org.apache.taglibs.standard.lang.jstl;

public abstract class EqualityOperator extends BinaryOperator {
   public Object apply(Object pLeft, Object pRight, Object pContext, Logger pLogger) throws ELException {
      return Coercions.applyEqualityOperator(pLeft, pRight, this, pLogger);
   }

   public abstract boolean apply(boolean var1, Logger var2);
}
