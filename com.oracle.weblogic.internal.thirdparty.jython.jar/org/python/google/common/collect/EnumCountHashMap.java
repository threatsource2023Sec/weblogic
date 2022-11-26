package org.python.google.common.collect;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.common.base.Preconditions;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible(
   serializable = true,
   emulated = true
)
class EnumCountHashMap extends AbstractObjectCountMap {
   private final Class keyType;

   public static EnumCountHashMap create(Class type) {
      return new EnumCountHashMap(type);
   }

   EnumCountHashMap(Class keyType) {
      this.keyType = keyType;
      this.keys = keyType.getEnumConstants();
      if (this.keys == null) {
         throw new IllegalStateException("Expected Enum class type, but got " + keyType.getName());
      } else {
         this.values = new int[this.keys.length];
         Arrays.fill(this.values, 0, this.keys.length, -1);
      }
   }

   int firstIndex() {
      for(int i = 0; i < this.keys.length; ++i) {
         if (this.values[i] > 0) {
            return i;
         }
      }

      return -1;
   }

   int nextIndex(int index) {
      for(int i = index + 1; i < this.keys.length; ++i) {
         if (this.values[i] > 0) {
            return i;
         }
      }

      return -1;
   }

   Set createKeySet() {
      return new AbstractObjectCountMap.KeySetView() {
         private Object[] getFilteredKeyArray() {
            Object[] filteredKeys = new Object[EnumCountHashMap.this.size];
            int i = 0;

            for(int j = 0; i < EnumCountHashMap.this.keys.length; ++i) {
               if (EnumCountHashMap.this.values[i] != -1) {
                  filteredKeys[j++] = EnumCountHashMap.this.keys[i];
               }
            }

            return filteredKeys;
         }

         public Object[] toArray() {
            return this.getFilteredKeyArray();
         }

         public Object[] toArray(Object[] a) {
            return ObjectArrays.toArrayImpl(this.getFilteredKeyArray(), 0, EnumCountHashMap.this.size, a);
         }

         public Iterator iterator() {
            return new EnumIterator() {
               Enum getOutput(int entry) {
                  return (Enum)EnumCountHashMap.this.keys[entry];
               }
            };
         }
      };
   }

   Multiset.Entry getEntry(int index) {
      Preconditions.checkElementIndex(index, this.size);
      return new EnumMapEntry(index);
   }

   Set createEntrySet() {
      return new AbstractObjectCountMap.EntrySetView() {
         public Iterator iterator() {
            return new EnumIterator() {
               Multiset.Entry getOutput(int entry) {
                  return EnumCountHashMap.this.new EnumMapEntry(entry);
               }
            };
         }
      };
   }

   public void clear() {
      ++this.modCount;
      if (this.keys != null) {
         Arrays.fill(this.values, 0, this.values.length, -1);
         this.size = 0;
      }

   }

   private boolean isValidKey(Object key) {
      if (key == null) {
         return false;
      } else {
         Class keyClass = key.getClass();
         return keyClass == this.keyType || keyClass.getSuperclass() == this.keyType;
      }
   }

   public boolean containsKey(@Nullable Object key) {
      return this.isValidKey(key) && this.values[((Enum)key).ordinal()] != -1;
   }

   public int get(@Nullable Object key) {
      return this.containsKey(key) ? this.values[((Enum)key).ordinal()] : 0;
   }

   int indexOf(@Nullable Object key) {
      return !this.isValidKey(key) ? -1 : ((Enum)key).ordinal();
   }

   @CanIgnoreReturnValue
   int removeEntry(int entryIndex) {
      return this.remove(this.keys[entryIndex]);
   }

   @CanIgnoreReturnValue
   public int put(@Nullable Enum key, int value) {
      CollectPreconditions.checkPositive(value, "count");
      this.typeCheck(key);
      int index = key.ordinal();
      int oldValue = this.values[index];
      this.values[index] = value;
      ++this.modCount;
      if (oldValue == -1) {
         ++this.size;
         return 0;
      } else {
         return oldValue;
      }
   }

   @CanIgnoreReturnValue
   public int remove(@Nullable Object key) {
      if (!this.isValidKey(key)) {
         return 0;
      } else {
         int index = ((Enum)key).ordinal();
         int oldValue = this.values[index];
         if (oldValue == -1) {
            return 0;
         } else {
            this.values[index] = -1;
            --this.size;
            ++this.modCount;
            return oldValue;
         }
      }
   }

   private void typeCheck(Enum key) {
      Class keyClass = key.getClass();
      if (keyClass != this.keyType && keyClass.getSuperclass() != this.keyType) {
         throw new ClassCastException(keyClass + " != " + this.keyType);
      }
   }

   public int hashCode() {
      int h = 0;

      for(int i = 0; i < this.keys.length; ++i) {
         h += this.keys[i].hashCode() ^ this.values[i];
      }

      return h;
   }

   class EnumMapEntry extends AbstractObjectCountMap.MapEntry {
      EnumMapEntry(int index) {
         super(index);
      }

      public int getCount() {
         return EnumCountHashMap.this.values[this.lastKnownIndex] == -1 ? 0 : EnumCountHashMap.this.values[this.lastKnownIndex];
      }

      public int setCount(int count) {
         if (EnumCountHashMap.this.values[this.lastKnownIndex] == -1) {
            EnumCountHashMap.this.put((Enum)this.key, count);
            return 0;
         } else {
            int old = EnumCountHashMap.this.values[this.lastKnownIndex];
            EnumCountHashMap.this.values[this.lastKnownIndex] = count;
            return old == -1 ? 0 : old;
         }
      }
   }

   private abstract class EnumIterator extends AbstractObjectCountMap.Itr {
      int nextIndex;

      private EnumIterator() {
         super();
         this.nextIndex = -1;
      }

      public boolean hasNext() {
         while(this.index < EnumCountHashMap.this.values.length && EnumCountHashMap.this.values[this.index] <= 0) {
            ++this.index;
         }

         return this.index != EnumCountHashMap.this.values.length;
      }

      public Object next() {
         this.checkForConcurrentModification();
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            this.nextCalled = true;
            this.nextIndex = this.index;
            return this.getOutput(this.index++);
         }
      }

      public void remove() {
         this.checkForConcurrentModification();
         CollectPreconditions.checkRemove(this.nextCalled);
         ++this.expectedModCount;
         EnumCountHashMap.this.removeEntry(this.nextIndex);
         this.nextCalled = false;
         this.nextIndex = -1;
         --this.index;
      }

      // $FF: synthetic method
      EnumIterator(Object x1) {
         this();
      }
   }
}
