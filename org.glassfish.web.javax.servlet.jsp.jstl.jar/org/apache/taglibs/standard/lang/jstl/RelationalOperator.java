package org.apache.taglibs.standard.lang.jstl;

public abstract class RelationalOperator extends BinaryOperator {
   public Object apply(Object pLeft, Object pRight, Object pContext, Logger pLogger) throws ELException {
      return Coercions.applyRelationalOperator(pLeft, pRight, this, pLogger);
   }

   public abstract boolean apply(double var1, double var3, Logger var5);

   public abstract boolean apply(long var1, long var3, Logger var5);

   public abstract boolean apply(String var1, String var2, Logger var3);
}
