package com.oracle.pitchfork.intercept;

public class InterceptorExclusion {
   private boolean excludeClassInterceptors;
   private boolean excludeDefaultInterceptors;

   public boolean isExcludeClassInterceptors() {
      return this.excludeClassInterceptors;
   }

   public void setExcludeClassInterceptors(boolean excludeClassInterceptors) {
      this.excludeClassInterceptors = excludeClassInterceptors;
   }

   public boolean isExcludeDefaultInterceptors() {
      return this.excludeDefaultInterceptors;
   }

   public void setExcludeDefaultInterceptors(boolean excludeDefaultInterceptors) {
      this.excludeDefaultInterceptors = excludeDefaultInterceptors;
   }
}
