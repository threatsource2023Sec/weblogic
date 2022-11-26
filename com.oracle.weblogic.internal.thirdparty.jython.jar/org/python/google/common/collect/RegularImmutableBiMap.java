package org.python.google.common.collect;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible(
   serializable = true,
   emulated = true
)
final class RegularImmutableBiMap extends ImmutableBiMap {
   static final RegularImmutableBiMap EMPTY = new RegularImmutableBiMap();
   private final transient int[] keyHashTable;
   private final transient Object[] alternatingKeysAndValues;
   private final transient int keyOffset;
   private final transient int size;
   private final transient RegularImmutableBiMap inverse;

   private RegularImmutableBiMap() {
      this.keyHashTable = null;
      this.alternatingKeysAndValues = new Object[0];
      this.keyOffset = 0;
      this.size = 0;
      this.inverse = this;
   }

   RegularImmutableBiMap(Object[] alternatingKeysAndValues, int size) {
      this.alternatingKeysAndValues = alternatingKeysAndValues;
      this.size = size;
      this.keyOffset = 0;
      int tableSize = size >= 2 ? ImmutableSet.chooseTableSize(size) : 0;
      this.keyHashTable = RegularImmutableMap.createHashTable(alternatingKeysAndValues, size, tableSize, 0);
      int[] valueHashTable = RegularImmutableMap.createHashTable(alternatingKeysAndValues, size, tableSize, 1);
      this.inverse = new RegularImmutableBiMap(valueHashTable, alternatingKeysAndValues, size, this);
   }

   private RegularImmutableBiMap(int[] valueHashTable, Object[] alternatingKeysAndValues, int size, RegularImmutableBiMap inverse) {
      this.keyHashTable = valueHashTable;
      this.alternatingKeysAndValues = alternatingKeysAndValues;
      this.keyOffset = 1;
      this.size = size;
      this.inverse = inverse;
   }

   public int size() {
      return this.size;
   }

   public ImmutableBiMap inverse() {
      return this.inverse;
   }

   public Object get(@Nullable Object key) {
      return RegularImmutableMap.get(this.keyHashTable, this.alternatingKeysAndValues, this.size, this.keyOffset, key);
   }

   ImmutableSet createEntrySet() {
      return new RegularImmutableMap.EntrySet(this, this.alternatingKeysAndValues, this.keyOffset, this.size);
   }

   ImmutableSet createKeySet() {
      ImmutableList keyList = new RegularImmutableMap.KeysOrValuesAsList(this.alternatingKeysAndValues, this.keyOffset, this.size);
      return new RegularImmutableMap.KeySet(this, keyList);
   }

   boolean isPartialView() {
      return false;
   }
}
