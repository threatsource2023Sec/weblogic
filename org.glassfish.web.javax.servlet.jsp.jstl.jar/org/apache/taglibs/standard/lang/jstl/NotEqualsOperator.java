package org.apache.taglibs.standard.lang.jstl;

public class NotEqualsOperator extends EqualityOperator {
   public static final NotEqualsOperator SINGLETON = new NotEqualsOperator();

   public String getOperatorSymbol() {
      return "!=";
   }

   public boolean apply(boolean pAreEqual, Logger pLogger) {
      return !pAreEqual;
   }
}
