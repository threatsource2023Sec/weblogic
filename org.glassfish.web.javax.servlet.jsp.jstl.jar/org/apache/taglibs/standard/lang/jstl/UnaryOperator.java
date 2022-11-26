package org.apache.taglibs.standard.lang.jstl;

public abstract class UnaryOperator {
   public abstract String getOperatorSymbol();

   public abstract Object apply(Object var1, Object var2, Logger var3) throws ELException;
}
