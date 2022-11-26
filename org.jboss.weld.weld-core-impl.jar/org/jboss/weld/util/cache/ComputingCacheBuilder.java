package org.jboss.weld.util.cache;

import java.util.function.Function;
import org.jboss.weld.util.WeakLazyValueHolder;

public final class ComputingCacheBuilder {
   private Long maxSize;
   private boolean weakValues;

   private ComputingCacheBuilder() {
   }

   public static ComputingCacheBuilder newBuilder() {
      return new ComputingCacheBuilder();
   }

   public ComputingCacheBuilder setMaxSize(long maxSize) {
      this.maxSize = maxSize;
      return this;
   }

   public ComputingCacheBuilder setWeakValues() {
      this.weakValues = true;
      return this;
   }

   public ComputingCache build(Function computingFunction) {
      return this.weakValues ? new ReentrantMapBackedComputingCache(computingFunction, WeakLazyValueHolder::forSupplier, this.maxSize) : new ReentrantMapBackedComputingCache(computingFunction, this.maxSize);
   }
}
