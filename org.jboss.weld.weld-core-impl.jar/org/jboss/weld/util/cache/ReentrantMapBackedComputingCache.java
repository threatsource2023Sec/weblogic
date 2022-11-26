package org.jboss.weld.util.cache;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jboss.weld.util.LazyValueHolder;
import org.jboss.weld.util.ValueHolder;

class ReentrantMapBackedComputingCache implements ComputingCache, Iterable {
   private final ConcurrentMap map;
   private final Long maxSize;
   private final Function function;

   ReentrantMapBackedComputingCache(Function computingFunction, Long maxSize) {
      this(computingFunction, LazyValueHolder::forSupplier, maxSize);
   }

   ReentrantMapBackedComputingCache(Function computingFunction, Function valueHolderFunction, Long maxSize) {
      this.map = new ConcurrentHashMap();
      this.maxSize = maxSize;
      this.function = (key) -> {
         return (ValueHolder)valueHolderFunction.apply(() -> {
            return computingFunction.apply(key);
         });
      };
   }

   public Object getValue(Object key) {
      ValueHolder value = (ValueHolder)this.map.get(key);
      if (value == null) {
         value = (ValueHolder)this.function.apply(key);
         ValueHolder previous = (ValueHolder)this.map.putIfAbsent(key, value);
         if (previous != null) {
            value = previous;
         }

         if (this.maxSize != null && this.size() > this.maxSize) {
            this.clear();
         }
      }

      return value.get();
   }

   public Object getCastValue(Object key) {
      return this.getValue(key);
   }

   public Object getValueIfPresent(Object key) {
      ValueHolder value = (ValueHolder)this.map.get(key);
      return value == null ? null : value.getIfPresent();
   }

   public long size() {
      return (long)this.map.size();
   }

   public void clear() {
      this.map.clear();
   }

   public void invalidate(Object key) {
      this.map.remove(key);
   }

   public Iterable getAllPresentValues() {
      return this;
   }

   public String toString() {
      return this.map.toString();
   }

   public void forEachValue(Consumer consumer) {
      Iterator var2 = this.map.values().iterator();

      while(var2.hasNext()) {
         ValueHolder valueHolder = (ValueHolder)var2.next();
         Object value = valueHolder.getIfPresent();
         if (value != null) {
            consumer.accept(value);
         }
      }

   }

   public Iterator iterator() {
      return new Iterator() {
         private final Iterator delegate;
         private Object next;

         {
            this.delegate = ReentrantMapBackedComputingCache.this.map.values().iterator();
            this.next = this.findNext();
         }

         public boolean hasNext() {
            return this.next != null;
         }

         private Object findNext() {
            while(true) {
               if (this.delegate.hasNext()) {
                  Object next = ((ValueHolder)this.delegate.next()).getIfPresent();
                  if (next == null) {
                     continue;
                  }

                  return next;
               }

               return null;
            }
         }

         public Object next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            } else {
               Object current = this.next;
               this.next = this.findNext();
               return current;
            }
         }
      };
   }
}
