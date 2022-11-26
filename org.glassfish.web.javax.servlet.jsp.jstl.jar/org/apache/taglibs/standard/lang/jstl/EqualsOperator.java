package org.apache.taglibs.standard.lang.jstl;

public class EqualsOperator extends EqualityOperator {
   public static final EqualsOperator SINGLETON = new EqualsOperator();

   public String getOperatorSymbol() {
      return "==";
   }

   public boolean apply(boolean pAreEqual, Logger pLogger) {
      return pAreEqual;
   }
}
