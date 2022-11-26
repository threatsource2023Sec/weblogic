package com.bea.core.repackaged.springframework.transaction.interceptor;

import com.bea.core.repackaged.springframework.util.Assert;
import java.io.Serializable;

public class RollbackRuleAttribute implements Serializable {
   public static final RollbackRuleAttribute ROLLBACK_ON_RUNTIME_EXCEPTIONS = new RollbackRuleAttribute(RuntimeException.class);
   private final String exceptionName;

   public RollbackRuleAttribute(Class clazz) {
      Assert.notNull(clazz, (String)"'clazz' cannot be null");
      if (!Throwable.class.isAssignableFrom(clazz)) {
         throw new IllegalArgumentException("Cannot construct rollback rule from [" + clazz.getName() + "]: it's not a Throwable");
      } else {
         this.exceptionName = clazz.getName();
      }
   }

   public RollbackRuleAttribute(String exceptionName) {
      Assert.hasText(exceptionName, "'exceptionName' cannot be null or empty");
      this.exceptionName = exceptionName;
   }

   public String getExceptionName() {
      return this.exceptionName;
   }

   public int getDepth(Throwable ex) {
      return this.getDepth(ex.getClass(), 0);
   }

   private int getDepth(Class exceptionClass, int depth) {
      if (exceptionClass.getName().contains(this.exceptionName)) {
         return depth;
      } else {
         return exceptionClass == Throwable.class ? -1 : this.getDepth(exceptionClass.getSuperclass(), depth + 1);
      }
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof RollbackRuleAttribute)) {
         return false;
      } else {
         RollbackRuleAttribute rhs = (RollbackRuleAttribute)other;
         return this.exceptionName.equals(rhs.exceptionName);
      }
   }

   public int hashCode() {
      return this.exceptionName.hashCode();
   }

   public String toString() {
      return "RollbackRuleAttribute with pattern [" + this.exceptionName + "]";
   }
}
