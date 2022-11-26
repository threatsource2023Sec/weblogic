package com.bea.core.repackaged.springframework.cache.interceptor;

import com.bea.core.repackaged.springframework.lang.Nullable;

public class CacheableOperation extends CacheOperation {
   @Nullable
   private final String unless;
   private final boolean sync;

   public CacheableOperation(Builder b) {
      super(b);
      this.unless = b.unless;
      this.sync = b.sync;
   }

   @Nullable
   public String getUnless() {
      return this.unless;
   }

   public boolean isSync() {
      return this.sync;
   }

   public static class Builder extends CacheOperation.Builder {
      @Nullable
      private String unless;
      private boolean sync;

      public void setUnless(String unless) {
         this.unless = unless;
      }

      public void setSync(boolean sync) {
         this.sync = sync;
      }

      protected StringBuilder getOperationDescription() {
         StringBuilder sb = super.getOperationDescription();
         sb.append(" | unless='");
         sb.append(this.unless);
         sb.append("'");
         sb.append(" | sync='");
         sb.append(this.sync);
         sb.append("'");
         return sb;
      }

      public CacheableOperation build() {
         return new CacheableOperation(this);
      }
   }
}
