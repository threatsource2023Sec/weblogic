package org.glassfish.grizzly.utils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class ArraySet implements Set {
   private volatile Object[] array;
   private final Object sync;
   private final Class clazz;
   private final boolean replaceElementIfEquals;

   public ArraySet(Class clazz) {
      this(clazz, true);
   }

   public ArraySet(Class clazz, boolean replaceElementIfEquals) {
      this.sync = new Object();
      this.clazz = clazz;
      this.replaceElementIfEquals = replaceElementIfEquals;
   }

   public final boolean addAll(Object... elements) {
      if (elements != null && elements.length != 0) {
         synchronized(this.sync) {
            int startIdx = 0;
            if (this.array == null) {
               this.array = (Object[])((Object[])Array.newInstance(this.clazz, 1));
               this.array[0] = elements[0];
               startIdx = 1;
            }

            boolean result = false;

            for(int i = startIdx; i < elements.length; ++i) {
               Object element = elements[i];
               Object[] oldArray = this.array;
               this.array = ArrayUtils.addUnique(this.array, element, this.replaceElementIfEquals);
               result |= oldArray != this.array;
            }

            return result;
         }
      } else {
         return false;
      }
   }

   public boolean addAll(Collection collection) {
      if (collection.isEmpty()) {
         return false;
      } else {
         synchronized(this.sync) {
            boolean initArray = this.array == null;
            if (initArray) {
               this.array = (Object[])((Object[])Array.newInstance(this.clazz, 1));
            }

            boolean result = false;
            Iterator var5 = collection.iterator();

            while(var5.hasNext()) {
               Object element = var5.next();
               if (initArray) {
                  initArray = false;
                  this.array[0] = element;
               } else {
                  Object[] oldArray = this.array;
                  this.array = ArrayUtils.addUnique(this.array, element, this.replaceElementIfEquals);
                  result |= oldArray != this.array;
               }
            }

            return result;
         }
      }
   }

   public final boolean add(ArraySet source) {
      Object[] sourceArraySet = source.getArray();
      if (sourceArraySet == null) {
         return false;
      } else {
         synchronized(this.sync) {
            if (this.array == null) {
               this.array = Arrays.copyOf(sourceArraySet, sourceArraySet.length);
               return true;
            } else {
               boolean result = false;

               for(int i = 0; i < sourceArraySet.length; ++i) {
                  Object element = sourceArraySet[i];
                  Object[] oldArray = this.array;
                  this.array = ArrayUtils.addUnique(this.array, element, this.replaceElementIfEquals);
                  result |= oldArray != this.array;
               }

               return result;
            }
         }
      }
   }

   public final boolean removeAll(Object... elements) {
      if (elements.length == 0) {
         return false;
      } else {
         synchronized(this.sync) {
            if (this.array == null) {
               return false;
            } else {
               boolean result = false;
               Object[] var4 = elements;
               int var5 = elements.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Object element = var4[var6];
                  Object[] oldArray = this.array;
                  this.array = ArrayUtils.remove(this.array, element);
                  result |= oldArray != this.array;
               }

               return result;
            }
         }
      }
   }

   public final Object[] getArray() {
      return this.array;
   }

   public final Object[] getArrayCopy() {
      Object[] localArray = this.array;
      return localArray == null ? null : Arrays.copyOf(localArray, localArray.length);
   }

   public final Object[] obtainArrayCopy() {
      Object[] localArray = this.array;
      return localArray == null ? (Object[])((Object[])Array.newInstance(this.clazz, 0)) : Arrays.copyOf(localArray, localArray.length);
   }

   public void clear() {
      this.array = null;
   }

   public int size() {
      Object[] localArray = this.array;
      return localArray != null ? localArray.length : 0;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public boolean add(Object element) {
      if (element == null) {
         return false;
      } else {
         synchronized(this.sync) {
            if (this.array == null) {
               this.array = (Object[])((Object[])Array.newInstance(this.clazz, 1));
               this.array[0] = element;
               return true;
            } else {
               Object[] oldArray = this.array;
               this.array = ArrayUtils.addUnique(this.array, element, this.replaceElementIfEquals);
               return oldArray != this.array;
            }
         }
      }
   }

   public boolean contains(Object o) {
      Object[] localArray = this.array;
      if (localArray == null) {
         return false;
      } else {
         for(int i = 0; i < localArray.length; ++i) {
            if (localArray[i].equals(o)) {
               return true;
            }
         }

         return false;
      }
   }

   public Object[] toArray() {
      Object[] localArray = this.array;
      return Arrays.copyOf(localArray, localArray.length);
   }

   public Object[] toArray(Object[] a) {
      Object[] localArray = this.array;
      if (localArray == null) {
         return a;
      } else {
         int size = localArray.length;
         if (a.length < size) {
            return (Object[])Arrays.copyOf(localArray, size, a.getClass());
         } else {
            System.arraycopy(localArray, 0, a, 0, localArray.length);
            if (a.length > size) {
               a[size] = null;
            }

            return a;
         }
      }
   }

   public boolean remove(Object o) {
      return this.removeAll(o);
   }

   public boolean containsAll(Collection collection) {
      if (collection.isEmpty()) {
         return true;
      } else {
         Object[] localArray = this.array;
         Iterator var3 = collection.iterator();

         Object element;
         do {
            if (!var3.hasNext()) {
               return true;
            }

            element = var3.next();
         } while(ArrayUtils.indexOf(localArray, element) != -1);

         return false;
      }
   }

   public boolean retainAll(Collection collection) {
      Object[] localArray = this.array;
      if (localArray == null) {
         return false;
      } else {
         Object[] newArray = (Object[])((Object[])Array.newInstance(this.clazz, Math.min(localArray.length, collection.size())));
         int newSize = 0;

         for(int i = 0; i < localArray.length; ++i) {
            Object elem = localArray[i];
            if (collection.contains(elem)) {
               newArray[newSize++] = elem;
            }
         }

         if (newSize == localArray.length) {
            return false;
         } else {
            this.array = Arrays.copyOf(newArray, newSize);
            return true;
         }
      }
   }

   public boolean removeAll(Collection collection) {
      Object[] localArray = this.array;
      if (localArray == null) {
         return false;
      } else {
         Object[] newArray = (Object[])((Object[])Array.newInstance(this.clazz, localArray.length));
         int newSize = 0;

         for(int i = 0; i < localArray.length; ++i) {
            Object elem = localArray[i];
            if (!collection.contains(elem)) {
               newArray[newSize++] = elem;
            }
         }

         if (newSize == localArray.length) {
            return false;
         } else {
            this.array = Arrays.copyOf(newArray, newSize);
            return true;
         }
      }
   }

   public Iterator iterator() {
      return new Itr();
   }

   private class Itr implements Iterator {
      int cursor;
      Object lastRet;
      Object nextElem;

      public Itr() {
         this.advance();
      }

      public boolean hasNext() {
         return this.nextElem != null;
      }

      public Object next() {
         if (this.nextElem == null) {
            throw new NoSuchElementException();
         } else {
            this.lastRet = this.nextElem;
            this.advance();
            return this.lastRet;
         }
      }

      public void remove() {
         if (this.lastRet == null) {
            throw new IllegalStateException();
         } else {
            ArraySet.this.remove(this.lastRet);
            --this.cursor;
            this.lastRet = null;
         }
      }

      private void advance() {
         Object[] localArray = ArraySet.this.array;
         if (localArray != null && this.cursor < localArray.length) {
            this.nextElem = localArray[this.cursor++];
         } else {
            this.nextElem = null;
         }
      }
   }
}
