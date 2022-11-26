package org.python.google.common.collect;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;

@GwtCompatible(
   serializable = true,
   emulated = true
)
final class RegularImmutableMap extends ImmutableMap {
   private static final int ABSENT = -1;
   static final ImmutableMap EMPTY = new RegularImmutableMap((int[])null, new Object[0], 0);
   private final transient int[] hashTable;
   private final transient Object[] alternatingKeysAndValues;
   private final transient int size;
   private static final long serialVersionUID = 0L;

   static RegularImmutableMap create(int n, Object[] alternatingKeysAndValues) {
      if (n == 0) {
         return (RegularImmutableMap)EMPTY;
      } else if (n == 1) {
         CollectPreconditions.checkEntryNotNull(alternatingKeysAndValues[0], alternatingKeysAndValues[1]);
         return new RegularImmutableMap((int[])null, alternatingKeysAndValues, 1);
      } else {
         Preconditions.checkPositionIndex(n, alternatingKeysAndValues.length >> 1);
         int tableSize = ImmutableSet.chooseTableSize(n);
         int[] hashTable = createHashTable(alternatingKeysAndValues, n, tableSize, 0);
         return new RegularImmutableMap(hashTable, alternatingKeysAndValues, n);
      }
   }

   static int[] createHashTable(Object[] alternatingKeysAndValues, int n, int tableSize, int keyOffset) {
      if (n == 1) {
         CollectPreconditions.checkEntryNotNull(alternatingKeysAndValues[keyOffset], alternatingKeysAndValues[keyOffset ^ 1]);
         return null;
      } else {
         int mask = tableSize - 1;
         int[] hashTable = new int[tableSize];
         Arrays.fill(hashTable, -1);

         for(int i = 0; i < n; ++i) {
            Object key = alternatingKeysAndValues[2 * i + keyOffset];
            Object value = alternatingKeysAndValues[2 * i + (keyOffset ^ 1)];
            CollectPreconditions.checkEntryNotNull(key, value);
            int h = Hashing.smear(key.hashCode());

            while(true) {
               h &= mask;
               int previous = hashTable[h];
               if (previous == -1) {
                  hashTable[h] = 2 * i + keyOffset;
                  break;
               }

               if (alternatingKeysAndValues[previous].equals(key)) {
                  throw new IllegalArgumentException("Multiple entries with same key: " + key + "=" + value + " and " + alternatingKeysAndValues[previous] + "=" + alternatingKeysAndValues[previous ^ 1]);
               }

               ++h;
            }
         }

         return hashTable;
      }
   }

   private RegularImmutableMap(int[] hashTable, Object[] alternatingKeysAndValues, int size) {
      this.hashTable = hashTable;
      this.alternatingKeysAndValues = alternatingKeysAndValues;
      this.size = size;
   }

   public int size() {
      return this.size;
   }

   @Nullable
   public Object get(@Nullable Object key) {
      return get(this.hashTable, this.alternatingKeysAndValues, this.size, 0, key);
   }

   static Object get(@Nullable int[] hashTable, @Nullable Object[] alternatingKeysAndValues, int size, int keyOffset, @Nullable Object key) {
      if (key == null) {
         return null;
      } else if (size == 1) {
         return alternatingKeysAndValues[keyOffset].equals(key) ? alternatingKeysAndValues[keyOffset ^ 1] : null;
      } else if (hashTable == null) {
         return null;
      } else {
         int mask = hashTable.length - 1;
         int h = Hashing.smear(key.hashCode());

         while(true) {
            h &= mask;
            int index = hashTable[h];
            if (index == -1) {
               return null;
            }

            if (alternatingKeysAndValues[index].equals(key)) {
               return alternatingKeysAndValues[index ^ 1];
            }

            ++h;
         }
      }
   }

   ImmutableSet createEntrySet() {
      return new EntrySet(this, this.alternatingKeysAndValues, 0, this.size);
   }

   ImmutableSet createKeySet() {
      ImmutableList keyList = new KeysOrValuesAsList(this.alternatingKeysAndValues, 0, this.size);
      return new KeySet(this, keyList);
   }

   ImmutableCollection createValues() {
      return new KeysOrValuesAsList(this.alternatingKeysAndValues, 1, this.size);
   }

   boolean isPartialView() {
      return false;
   }

   static final class KeySet extends ImmutableSet {
      private final transient ImmutableMap map;
      private final transient ImmutableList list;

      KeySet(ImmutableMap map, ImmutableList list) {
         this.map = map;
         this.list = list;
      }

      public UnmodifiableIterator iterator() {
         return this.asList().iterator();
      }

      public ImmutableList asList() {
         return this.list;
      }

      public boolean contains(@Nullable Object object) {
         return this.map.get(object) != null;
      }

      boolean isPartialView() {
         return true;
      }

      public int size() {
         return this.map.size();
      }
   }

   static final class KeysOrValuesAsList extends ImmutableList {
      private final transient Object[] alternatingKeysAndValues;
      private final transient int offset;
      private final transient int size;

      KeysOrValuesAsList(Object[] alternatingKeysAndValues, int offset, int size) {
         this.alternatingKeysAndValues = alternatingKeysAndValues;
         this.offset = offset;
         this.size = size;
      }

      public Object get(int index) {
         Preconditions.checkElementIndex(index, this.size);
         return this.alternatingKeysAndValues[2 * index + this.offset];
      }

      boolean isPartialView() {
         return true;
      }

      public int size() {
         return this.size;
      }
   }

   static class EntrySet extends ImmutableSet {
      private final transient ImmutableMap map;
      private final transient Object[] alternatingKeysAndValues;
      private final transient int keyOffset;
      private final transient int size;

      EntrySet(ImmutableMap map, Object[] alternatingKeysAndValues, int keyOffset, int size) {
         this.map = map;
         this.alternatingKeysAndValues = alternatingKeysAndValues;
         this.keyOffset = keyOffset;
         this.size = size;
      }

      public UnmodifiableIterator iterator() {
         return this.asList().iterator();
      }

      ImmutableList createAsList() {
         return new ImmutableList() {
            public Map.Entry get(int index) {
               Preconditions.checkElementIndex(index, EntrySet.this.size);
               Object key = EntrySet.this.alternatingKeysAndValues[2 * index + EntrySet.this.keyOffset];
               Object value = EntrySet.this.alternatingKeysAndValues[2 * index + (EntrySet.this.keyOffset ^ 1)];
               return new AbstractMap.SimpleImmutableEntry(key, value);
            }

            public int size() {
               return EntrySet.this.size;
            }

            public boolean isPartialView() {
               return true;
            }
         };
      }

      public boolean contains(Object object) {
         if (!(object instanceof Map.Entry)) {
            return false;
         } else {
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            Object v = entry.getValue();
            return v != null && v.equals(this.map.get(k));
         }
      }

      boolean isPartialView() {
         return true;
      }

      public int size() {
         return this.size;
      }
   }
}
