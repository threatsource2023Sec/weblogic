package org.python.google.common.collect;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@Beta
@GwtCompatible
public final class EvictingQueue extends ForwardingQueue implements Serializable {
   private final Queue delegate;
   @VisibleForTesting
   final int maxSize;
   private static final long serialVersionUID = 0L;

   private EvictingQueue(int maxSize) {
      Preconditions.checkArgument(maxSize >= 0, "maxSize (%s) must >= 0", maxSize);
      this.delegate = new ArrayDeque(maxSize);
      this.maxSize = maxSize;
   }

   public static EvictingQueue create(int maxSize) {
      return new EvictingQueue(maxSize);
   }

   public int remainingCapacity() {
      return this.maxSize - this.size();
   }

   protected Queue delegate() {
      return this.delegate;
   }

   @CanIgnoreReturnValue
   public boolean offer(Object e) {
      return this.add(e);
   }

   @CanIgnoreReturnValue
   public boolean add(Object e) {
      Preconditions.checkNotNull(e);
      if (this.maxSize == 0) {
         return true;
      } else {
         if (this.size() == this.maxSize) {
            this.delegate.remove();
         }

         this.delegate.add(e);
         return true;
      }
   }

   @CanIgnoreReturnValue
   public boolean addAll(Collection collection) {
      int size = collection.size();
      if (size >= this.maxSize) {
         this.clear();
         return Iterables.addAll(this, Iterables.skip(collection, size - this.maxSize));
      } else {
         return this.standardAddAll(collection);
      }
   }

   public boolean contains(Object object) {
      return this.delegate().contains(Preconditions.checkNotNull(object));
   }

   @CanIgnoreReturnValue
   public boolean remove(Object object) {
      return this.delegate().remove(Preconditions.checkNotNull(object));
   }
}
