package com.bea.core.repackaged.springframework.cache.interceptor;

@FunctionalInterface
public interface CacheOperationInvoker {
   Object invoke() throws ThrowableWrapper;

   public static class ThrowableWrapper extends RuntimeException {
      private final Throwable original;

      public ThrowableWrapper(Throwable original) {
         super(original.getMessage(), original);
         this.original = original;
      }

      public Throwable getOriginal() {
         return this.original;
      }
   }
}
