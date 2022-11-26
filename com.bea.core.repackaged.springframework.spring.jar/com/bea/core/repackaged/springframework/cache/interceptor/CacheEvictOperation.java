package com.bea.core.repackaged.springframework.cache.interceptor;

public class CacheEvictOperation extends CacheOperation {
   private final boolean cacheWide;
   private final boolean beforeInvocation;

   public CacheEvictOperation(Builder b) {
      super(b);
      this.cacheWide = b.cacheWide;
      this.beforeInvocation = b.beforeInvocation;
   }

   public boolean isCacheWide() {
      return this.cacheWide;
   }

   public boolean isBeforeInvocation() {
      return this.beforeInvocation;
   }

   public static class Builder extends CacheOperation.Builder {
      private boolean cacheWide = false;
      private boolean beforeInvocation = false;

      public void setCacheWide(boolean cacheWide) {
         this.cacheWide = cacheWide;
      }

      public void setBeforeInvocation(boolean beforeInvocation) {
         this.beforeInvocation = beforeInvocation;
      }

      protected StringBuilder getOperationDescription() {
         StringBuilder sb = super.getOperationDescription();
         sb.append(",");
         sb.append(this.cacheWide);
         sb.append(",");
         sb.append(this.beforeInvocation);
         return sb;
      }

      public CacheEvictOperation build() {
         return new CacheEvictOperation(this);
      }
   }
}
