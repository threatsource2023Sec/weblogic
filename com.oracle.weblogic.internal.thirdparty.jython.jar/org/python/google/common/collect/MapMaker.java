package org.python.google.common.collect;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.common.base.Ascii;
import org.python.google.common.base.Equivalence;
import org.python.google.common.base.MoreObjects;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   emulated = true
)
public final class MapMaker {
   private static final int DEFAULT_INITIAL_CAPACITY = 16;
   private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
   static final int UNSET_INT = -1;
   boolean useCustomMap;
   int initialCapacity = -1;
   int concurrencyLevel = -1;
   MapMakerInternalMap.Strength keyStrength;
   MapMakerInternalMap.Strength valueStrength;
   Equivalence keyEquivalence;

   @CanIgnoreReturnValue
   @GwtIncompatible
   MapMaker keyEquivalence(Equivalence equivalence) {
      Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", (Object)this.keyEquivalence);
      this.keyEquivalence = (Equivalence)Preconditions.checkNotNull(equivalence);
      this.useCustomMap = true;
      return this;
   }

   Equivalence getKeyEquivalence() {
      return (Equivalence)MoreObjects.firstNonNull(this.keyEquivalence, this.getKeyStrength().defaultEquivalence());
   }

   @CanIgnoreReturnValue
   public MapMaker initialCapacity(int initialCapacity) {
      Preconditions.checkState(this.initialCapacity == -1, "initial capacity was already set to %s", this.initialCapacity);
      Preconditions.checkArgument(initialCapacity >= 0);
      this.initialCapacity = initialCapacity;
      return this;
   }

   int getInitialCapacity() {
      return this.initialCapacity == -1 ? 16 : this.initialCapacity;
   }

   @CanIgnoreReturnValue
   public MapMaker concurrencyLevel(int concurrencyLevel) {
      Preconditions.checkState(this.concurrencyLevel == -1, "concurrency level was already set to %s", this.concurrencyLevel);
      Preconditions.checkArgument(concurrencyLevel > 0);
      this.concurrencyLevel = concurrencyLevel;
      return this;
   }

   int getConcurrencyLevel() {
      return this.concurrencyLevel == -1 ? 4 : this.concurrencyLevel;
   }

   @CanIgnoreReturnValue
   @GwtIncompatible
   public MapMaker weakKeys() {
      return this.setKeyStrength(MapMakerInternalMap.Strength.WEAK);
   }

   MapMaker setKeyStrength(MapMakerInternalMap.Strength strength) {
      Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", (Object)this.keyStrength);
      this.keyStrength = (MapMakerInternalMap.Strength)Preconditions.checkNotNull(strength);
      if (strength != MapMakerInternalMap.Strength.STRONG) {
         this.useCustomMap = true;
      }

      return this;
   }

   MapMakerInternalMap.Strength getKeyStrength() {
      return (MapMakerInternalMap.Strength)MoreObjects.firstNonNull(this.keyStrength, MapMakerInternalMap.Strength.STRONG);
   }

   @CanIgnoreReturnValue
   @GwtIncompatible
   public MapMaker weakValues() {
      return this.setValueStrength(MapMakerInternalMap.Strength.WEAK);
   }

   MapMaker setValueStrength(MapMakerInternalMap.Strength strength) {
      Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", (Object)this.valueStrength);
      this.valueStrength = (MapMakerInternalMap.Strength)Preconditions.checkNotNull(strength);
      if (strength != MapMakerInternalMap.Strength.STRONG) {
         this.useCustomMap = true;
      }

      return this;
   }

   MapMakerInternalMap.Strength getValueStrength() {
      return (MapMakerInternalMap.Strength)MoreObjects.firstNonNull(this.valueStrength, MapMakerInternalMap.Strength.STRONG);
   }

   public ConcurrentMap makeMap() {
      return (ConcurrentMap)(!this.useCustomMap ? new ConcurrentHashMap(this.getInitialCapacity(), 0.75F, this.getConcurrencyLevel()) : MapMakerInternalMap.create(this));
   }

   public String toString() {
      MoreObjects.ToStringHelper s = MoreObjects.toStringHelper((Object)this);
      if (this.initialCapacity != -1) {
         s.add("initialCapacity", this.initialCapacity);
      }

      if (this.concurrencyLevel != -1) {
         s.add("concurrencyLevel", this.concurrencyLevel);
      }

      if (this.keyStrength != null) {
         s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
      }

      if (this.valueStrength != null) {
         s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
      }

      if (this.keyEquivalence != null) {
         s.addValue("keyEquivalence");
      }

      return s.toString();
   }

   static enum Dummy {
      VALUE;
   }
}
