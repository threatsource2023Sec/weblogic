package jsr166e;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

public class LongAdderTable implements Serializable {
   private static final long serialVersionUID = 7249369246863182397L;
   private final ConcurrentHashMapV8 map = new ConcurrentHashMapV8();
   private static final CreateAdder createAdder = new CreateAdder();

   public LongAdder install(Object key) {
      return (LongAdder)this.map.computeIfAbsent(key, createAdder);
   }

   public void add(Object key, long x) {
      ((LongAdder)this.map.computeIfAbsent(key, createAdder)).add(x);
   }

   public void increment(Object key) {
      this.add(key, 1L);
   }

   public void decrement(Object key) {
      this.add(key, -1L);
   }

   public long sum(Object key) {
      LongAdder a = (LongAdder)this.map.get(key);
      return a == null ? 0L : a.sum();
   }

   public void reset(Object key) {
      LongAdder a = (LongAdder)this.map.get(key);
      if (a != null) {
         a.reset();
      }

   }

   public long sumThenReset(Object key) {
      LongAdder a = (LongAdder)this.map.get(key);
      return a == null ? 0L : a.sumThenReset();
   }

   public long sumAll() {
      long sum = 0L;

      LongAdder a;
      for(Iterator i$ = this.map.values().iterator(); i$.hasNext(); sum += a.sum()) {
         a = (LongAdder)i$.next();
      }

      return sum;
   }

   public void resetAll() {
      Iterator i$ = this.map.values().iterator();

      while(i$.hasNext()) {
         LongAdder a = (LongAdder)i$.next();
         a.reset();
      }

   }

   public long sumThenResetAll() {
      long sum = 0L;

      LongAdder a;
      for(Iterator i$ = this.map.values().iterator(); i$.hasNext(); sum += a.sumThenReset()) {
         a = (LongAdder)i$.next();
      }

      return sum;
   }

   public void remove(Object key) {
      this.map.remove(key);
   }

   public void removeAll() {
      this.map.clear();
   }

   public Set keySet() {
      return this.map.keySet();
   }

   public Set entrySet() {
      return this.map.entrySet();
   }

   static final class CreateAdder implements ConcurrentHashMapV8.Fun {
      public LongAdder apply(Object unused) {
         return new LongAdder();
      }
   }
}
