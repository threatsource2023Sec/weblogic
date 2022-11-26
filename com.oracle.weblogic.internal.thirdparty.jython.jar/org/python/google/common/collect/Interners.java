package org.python.google.common.collect;

import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.annotations.VisibleForTesting;
import org.python.google.common.base.Equivalence;
import org.python.google.common.base.Function;
import org.python.google.common.base.Preconditions;

@Beta
@GwtIncompatible
public final class Interners {
   private Interners() {
   }

   public static InternerBuilder newBuilder() {
      return new InternerBuilder();
   }

   public static Interner newStrongInterner() {
      return newBuilder().strong().build();
   }

   @GwtIncompatible("java.lang.ref.WeakReference")
   public static Interner newWeakInterner() {
      return newBuilder().weak().build();
   }

   public static Function asFunction(Interner interner) {
      return new InternerFunction((Interner)Preconditions.checkNotNull(interner));
   }

   private static class InternerFunction implements Function {
      private final Interner interner;

      public InternerFunction(Interner interner) {
         this.interner = interner;
      }

      public Object apply(Object input) {
         return this.interner.intern(input);
      }

      public int hashCode() {
         return this.interner.hashCode();
      }

      public boolean equals(Object other) {
         if (other instanceof InternerFunction) {
            InternerFunction that = (InternerFunction)other;
            return this.interner.equals(that.interner);
         } else {
            return false;
         }
      }
   }

   @VisibleForTesting
   static final class InternerImpl implements Interner {
      @VisibleForTesting
      final MapMakerInternalMap map;

      private InternerImpl(MapMaker mapMaker) {
         this.map = MapMakerInternalMap.createWithDummyValues(mapMaker.keyEquivalence(Equivalence.equals()));
      }

      public Object intern(Object sample) {
         MapMaker.Dummy sneaky;
         do {
            MapMakerInternalMap.InternalEntry entry = this.map.getEntry(sample);
            if (entry != null) {
               Object canonical = entry.getKey();
               if (canonical != null) {
                  return canonical;
               }
            }

            sneaky = (MapMaker.Dummy)this.map.putIfAbsent(sample, MapMaker.Dummy.VALUE);
         } while(sneaky != null);

         return sample;
      }

      // $FF: synthetic method
      InternerImpl(MapMaker x0, Object x1) {
         this(x0);
      }
   }

   public static class InternerBuilder {
      private final MapMaker mapMaker;
      private boolean strong;

      private InternerBuilder() {
         this.mapMaker = new MapMaker();
         this.strong = true;
      }

      public InternerBuilder strong() {
         this.strong = true;
         return this;
      }

      @GwtIncompatible("java.lang.ref.WeakReference")
      public InternerBuilder weak() {
         this.strong = false;
         return this;
      }

      public InternerBuilder concurrencyLevel(int concurrencyLevel) {
         this.mapMaker.concurrencyLevel(concurrencyLevel);
         return this;
      }

      public Interner build() {
         if (!this.strong) {
            this.mapMaker.weakKeys();
         }

         return new InternerImpl(this.mapMaker);
      }

      // $FF: synthetic method
      InternerBuilder(Object x0) {
         this();
      }
   }
}
