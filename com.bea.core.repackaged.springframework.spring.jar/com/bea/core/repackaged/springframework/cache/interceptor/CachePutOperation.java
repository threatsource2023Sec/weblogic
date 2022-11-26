package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class CachePutOperation extends CacheOperation {
   @Nullable
   private final String unless;

   public CachePutOperation(Builder b) {
      super(b);
      this.unless = b.unless;
   }

   @Nullable
   public String getUnless() {
      return this.unless;
   }

   public static class Builder extends CacheOperation.Builder {
      @Nullable
      private String unless;

      public void setUnless(String unless) {
         this.unless = unless;
      }

      protected StringBuilder getOperationDescription() {
         StringBuilder sb = super.getOperationDescription();
         sb.append(" | unless='");
         sb.append(this.unless);
         sb.append("'");
         return sb;
      }

      public CachePutOperation build() {
         return new CachePutOperation(this);
      }
   }
}
