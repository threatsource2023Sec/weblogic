package com.bea.core.repackaged.springframework.transaction.interceptor;

public class NoRollbackRuleAttribute extends RollbackRuleAttribute {
   public NoRollbackRuleAttribute(Class clazz) {
      super(clazz);
   }

   public NoRollbackRuleAttribute(String exceptionName) {
      super(exceptionName);
   }

   public String toString() {
      return "No" + super.toString();
   }
}
