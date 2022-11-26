package org.python.core;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;

public abstract class AbstractArray implements Serializable {
   protected int capacity;
   protected int size;
   protected int modCountIncr;

   public AbstractArray(AbstractArray toCopy) {
      this.capacity = toCopy.capacity;
      this.size = toCopy.size;
   }

   public AbstractArray(int size) {
      this.size = size;
      this.capacity = size;
   }

   public AbstractArray(Class type) {
      this(type, 10);
   }

   public AbstractArray(Class type, int[] dimensions) {
      Object array = Array.newInstance(type, dimensions);
      this.capacity = dimensions[0];
      this.setArray(array);
   }

   public AbstractArray(Class type, int size) {
      Object array = Array.newInstance(type, size);
      this.capacity = Math.max(size, 10);
      this.setArray(array);
   }

   public void appendArray(Object ofArrayType) {
      this.replaceSubArray(ofArrayType, this.size);
   }

   public void clear() {
      this.modCountIncr = 0;
      if (this.size != 0) {
         this.modCountIncr = 1;
         this.clearRange(0, this.size);
         this.setSize(0);
      }

   }

   protected void clearRange(int start, int stop) {
      if (start < stop && start >= 0 && stop <= this.size) {
         this.clearRangeInternal(start, stop);
      } else if (start != stop || start < 0 || stop > this.size) {
         throw new ArrayIndexOutOfBoundsException("start and stop must follow: 0 <= start <= stop <= " + this.size + ", but found start= " + start + " and stop=" + stop);
      }
   }

   private void clearRangeInternal(int start, int stop) {
      Object base = this.getArray();
      Class arrayType = base.getClass().getComponentType();
      if (arrayType.isPrimitive()) {
         if (arrayType == Boolean.TYPE) {
            Arrays.fill((boolean[])((boolean[])base), start, stop, false);
         } else if (arrayType == Character.TYPE) {
            Arrays.fill((char[])((char[])base), start, stop, '\u0000');
         } else if (arrayType == Byte.TYPE) {
            Arrays.fill((byte[])((byte[])base), start, stop, (byte)0);
         } else if (arrayType == Short.TYPE) {
            Arrays.fill((short[])((short[])base), start, stop, (short)0);
         } else if (arrayType == Integer.TYPE) {
            Arrays.fill((int[])((int[])base), start, stop, 0);
         } else if (arrayType == Long.TYPE) {
            Arrays.fill((long[])((long[])base), start, stop, 0L);
         } else if (arrayType == Float.TYPE) {
            Arrays.fill((float[])((float[])base), start, stop, 0.0F);
         } else if (arrayType == Double.TYPE) {
            Arrays.fill((double[])((double[])base), start, stop, 0.0);
         }
      } else {
         Arrays.fill((Object[])((Object[])base), start, stop, (Object)null);
      }

   }

   public Object copyArray() {
      Object copy = this.createArray(this.size);
      System.arraycopy(this.getArray(), 0, copy, 0, this.size);
      return copy;
   }

   protected void ensureCapacity(int minCapacity) {
      this.modCountIncr = 0;
      if (minCapacity > this.capacity) {
         this.modCountIncr = 1;
         int newCapacity = this.capacity * 2 + 1;
         newCapacity = newCapacity < minCapacity ? minCapacity : newCapacity;
         this.setNewBase(newCapacity);
         this.capacity = newCapacity;
      }

   }

   protected int getAddIndex() {
      int index = this.size++;
      if (this.size > this.capacity) {
         this.ensureCapacity(this.size);
      }

      return index;
   }

   protected abstract Object getArray();

   protected boolean isEmpty() {
      return this.size == 0;
   }

   protected void makeInsertSpace(int index) {
      this.makeInsertSpace(index, 1);
   }

   protected void makeInsertSpace(int index, int length) {
      this.modCountIncr = 0;
      if (index >= 0 && index <= this.size) {
         int toCopy = this.size - index;
         this.size += length;
         if (this.size > this.capacity) {
            this.ensureCapacity(this.size);
         }

         if (index < this.size - 1) {
            this.modCountIncr = 1;
            Object array = this.getArray();
            System.arraycopy(array, index, array, index + length, toCopy);
         }

      } else {
         throw new ArrayIndexOutOfBoundsException("Index must be between 0 and " + this.size + ", but was " + index);
      }
   }

   public void remove(int index) {
      if (index >= 0 && index < this.size) {
         --this.size;
         if (index < this.size) {
            Object base = this.getArray();
            System.arraycopy(base, index + 1, base, index, this.size - index);
            this.clearRangeInternal(this.size, this.size);
         }

      } else if (this.size == 0) {
         throw new IllegalStateException("Cannot remove data from an empty array");
      } else {
         throw new IndexOutOfBoundsException("Index must be between 0 and " + (this.size - 1) + ", but was " + index);
      }
   }

   public void remove(int start, int stop) {
      if (start >= 0 && stop <= this.size && start <= stop) {
         Object base = this.getArray();
         int nRemove = stop - start;
         if (nRemove != 0) {
            System.arraycopy(base, stop, base, start, this.size - stop);
            this.size -= nRemove;
            this.clearRangeInternal(this.size, this.size + nRemove);
            this.setArray(base);
         }
      } else {
         throw new IndexOutOfBoundsException("start and stop must follow: 0 <= start <= stop <= " + this.size + ", but found start= " + start + " and stop=" + stop);
      }
   }

   public void replaceSubArray(Object array, int atIndex) {
      int arrayLen = Array.getLength(array);
      this.replaceSubArray(atIndex, Math.min(this.size, atIndex + arrayLen), array, 0, arrayLen);
   }

   public void replaceSubArray(int thisStart, int thisStop, Object srcArray, int srcStart, int srcStop) {
      this.modCountIncr = 0;
      if (!srcArray.getClass().isArray()) {
         throw new IllegalArgumentException("'array' must be an array type");
      } else {
         int replacedLen = thisStop - thisStart;
         if (thisStart >= 0 && replacedLen >= 0 && thisStop <= this.size) {
            int srcLen = Array.getLength(srcArray);
            int replacementLen = srcStop - srcStart;
            if (srcStart >= 0 && replacementLen >= 0 && srcStop <= srcLen) {
               int lengthChange = replacementLen - replacedLen;
               if (lengthChange < 0) {
                  this.remove(thisStop + lengthChange, thisStop);
               } else if (lengthChange > 0) {
                  this.makeInsertSpace(thisStop, lengthChange);
               }

               try {
                  this.modCountIncr = 1;
                  System.arraycopy(srcArray, srcStart, this.getArray(), thisStart, replacementLen);
               } catch (ArrayStoreException var11) {
                  throw new IllegalArgumentException("'ofArrayType' must be compatible with existing array type of " + this.getArray().getClass().getName() + "\tsee java.lang.Class.getName().");
               }
            } else {
               String message = null;
               if (srcStart < 0) {
                  message = "srcStart < 0 (srcStart = " + srcStart + ")";
               } else if (replacementLen < 0) {
                  message = "srcStart > srcStop (srcStart = " + srcStart + ", srcStop = " + srcStop + ")";
               } else {
                  if (srcStop <= srcLen) {
                     throw new InternalError("Incorrect validation logic");
                  }

                  message = "srcStop > srcArray length (srcStop = " + srcStop + ", srcArray length = " + srcLen + ")";
               }

               throw new IllegalArgumentException("start, stop and array must follow:\n\t0 <= start <= stop <= array length\nBut found\n\t" + message);
            }
         } else {
            String message = null;
            if (thisStart < 0) {
               message = "thisStart < 0 (thisStart = " + thisStart + ")";
            } else if (replacedLen < 0) {
               message = "thisStart > thistStop (thisStart = " + thisStart + ", thisStop = " + thisStop + ")";
            } else {
               if (thisStop <= this.size) {
                  throw new InternalError("Incorrect validation logic");
               }

               message = "thisStop > size (thisStop = " + thisStop + ", size = " + this.size + ")";
            }

            throw new ArrayIndexOutOfBoundsException(message);
         }
      }
   }

   protected abstract void setArray(Object var1);

   private void setNewBase(int newCapacity) {
      this.modCountIncr = 1;
      Object base = this.getArray();
      Object newBase = this.createArray(newCapacity);
      System.arraycopy(base, 0, newBase, 0, this.capacity > newCapacity ? newCapacity : this.capacity);
      this.setArray(newBase);
   }

   public void setSize(int count) {
      if (count > this.capacity) {
         this.ensureCapacity(count);
      } else if (count < this.size) {
         this.clearRange(count, this.size);
      }

      this.size = count;
   }

   public int getSize() {
      return this.size;
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("[");
      Object base = this.getArray();
      Class arrayType = base.getClass().getComponentType();
      int n = this.size - 1;
      if (arrayType.isPrimitive()) {
         for(int i = 0; i < n; ++i) {
            buf.append(Array.get(base, i)).append(", ");
         }

         if (n >= 0) {
            buf.append(Array.get(base, n));
         }
      } else {
         Object[] objects = (Object[])((Object[])base);

         for(int i = 0; i < n; ++i) {
            buf.append(objects[i]).append(", ");
         }

         if (n >= 0) {
            buf.append(objects[n]);
         }
      }

      buf.append("]");
      return buf.toString();
   }

   protected void trimToSize() {
      if (this.size < this.capacity) {
         this.setNewBase(this.size);
      }

   }

   public int getModCountIncr() {
      return this.modCountIncr;
   }

   protected abstract Object createArray(int var1);
}
