package com.bea.core.repackaged.jdt.internal.compiler.util;

public final class SimpleSet implements Cloneable {
   public Object[] values;
   public int elementSize;
   public int threshold;

   public SimpleSet() {
      this(13);
   }

   public SimpleSet(int size) {
      if (size < 3) {
         size = 3;
      }

      this.elementSize = 0;
      this.threshold = size + 1;
      this.values = new Object[2 * size + 1];
   }

   public Object add(Object object) {
      int length = this.values.length;
      int index = (object.hashCode() & Integer.MAX_VALUE) % length;

      Object current;
      while((current = this.values[index]) != null) {
         if (current.equals(object)) {
            return this.values[index] = object;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      this.values[index] = object;
      if (++this.elementSize > this.threshold) {
         this.rehash();
      }

      return object;
   }

   public Object addIfNotIncluded(Object object) {
      int length = this.values.length;
      int index = (object.hashCode() & Integer.MAX_VALUE) % length;

      Object current;
      while((current = this.values[index]) != null) {
         if (current.equals(object)) {
            return null;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      this.values[index] = object;
      if (++this.elementSize > this.threshold) {
         this.rehash();
      }

      return object;
   }

   public void asArray(Object[] copy) {
      if (this.elementSize != copy.length) {
         throw new IllegalArgumentException();
      } else {
         int index = this.elementSize;
         int i = 0;

         for(int l = this.values.length; i < l && index > 0; ++i) {
            if (this.values[i] != null) {
               --index;
               copy[index] = this.values[i];
            }
         }

      }
   }

   public void clear() {
      int i = this.values.length;

      while(true) {
         --i;
         if (i < 0) {
            this.elementSize = 0;
            return;
         }

         this.values[i] = null;
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SimpleSet result = (SimpleSet)super.clone();
      result.elementSize = this.elementSize;
      result.threshold = this.threshold;
      int length = this.values.length;
      result.values = new Object[length];
      System.arraycopy(this.values, 0, result.values, 0, length);
      return result;
   }

   public boolean includes(Object object) {
      int length = this.values.length;
      int index = (object.hashCode() & Integer.MAX_VALUE) % length;

      Object current;
      while((current = this.values[index]) != null) {
         if (current.equals(object)) {
            return true;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return false;
   }

   public Object remove(Object object) {
      int length = this.values.length;
      int index = (object.hashCode() & Integer.MAX_VALUE) % length;

      Object current;
      while((current = this.values[index]) != null) {
         if (current.equals(object)) {
            --this.elementSize;
            Object oldValue = this.values[index];
            this.values[index] = null;
            if (this.values[index + 1 == length ? 0 : index + 1] != null) {
               this.rehash();
            }

            return oldValue;
         }

         ++index;
         if (index == length) {
            index = 0;
         }
      }

      return null;
   }

   private void rehash() {
      SimpleSet newSet = new SimpleSet(this.elementSize * 2);
      int i = this.values.length;

      while(true) {
         --i;
         if (i < 0) {
            this.values = newSet.values;
            this.elementSize = newSet.elementSize;
            this.threshold = newSet.threshold;
            return;
         }

         Object current;
         if ((current = this.values[i]) != null) {
            newSet.add(current);
         }
      }
   }

   public String toString() {
      String s = "";
      int i = 0;

      for(int l = this.values.length; i < l; ++i) {
         Object object;
         if ((object = this.values[i]) != null) {
            s = s + object.toString() + "\n";
         }
      }

      return s;
   }
}
