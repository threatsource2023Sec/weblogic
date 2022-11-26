package com.bea.common.security.internal.utils;

import java.io.Serializable;

public final class UnsyncStringBuffer implements Serializable {
   private char[] value;
   private int count;
   private boolean shared;
   static final long serialVersionUID = 3388685877147921107L;

   public UnsyncStringBuffer() {
      this(16);
   }

   public UnsyncStringBuffer(int length) {
      this.value = new char[length];
      this.shared = false;
   }

   public UnsyncStringBuffer(String str) {
      this(str.length() + 16);
      this.append(str);
   }

   public int length() {
      return this.count;
   }

   public int capacity() {
      return this.value.length;
   }

   private final void copyWhenShared() {
      if (this.shared) {
         char[] newValue = new char[this.value.length];
         System.arraycopy(this.value, 0, newValue, 0, this.count);
         this.value = newValue;
         this.shared = false;
      }

   }

   public void ensureCapacity(int minimumCapacity) {
      int maxCapacity = this.value.length;
      if (minimumCapacity > maxCapacity) {
         int newCapacity = (maxCapacity + 1) * 2;
         if (minimumCapacity > newCapacity) {
            newCapacity = minimumCapacity;
         }

         char[] newValue = new char[newCapacity];
         System.arraycopy(this.value, 0, newValue, 0, this.count);
         this.value = newValue;
         this.shared = false;
      }

   }

   public void setLength(int newLength) {
      if (newLength < 0) {
         throw new StringIndexOutOfBoundsException(newLength);
      } else {
         this.ensureCapacity(newLength);
         if (this.count < newLength) {
            this.copyWhenShared();

            while(this.count < newLength) {
               this.value[this.count] = 0;
               ++this.count;
            }
         }

         this.count = newLength;
      }
   }

   public char charAt(int index) {
      if (index >= 0 && index < this.count) {
         return this.value[index];
      } else {
         throw new StringIndexOutOfBoundsException(index);
      }
   }

   public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
      if (srcBegin >= 0 && srcBegin < this.count) {
         if (srcEnd >= 0 && srcEnd <= this.count) {
            if (srcBegin < srcEnd) {
               System.arraycopy(this.value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
            }

         } else {
            throw new StringIndexOutOfBoundsException(srcEnd);
         }
      } else {
         throw new StringIndexOutOfBoundsException(srcBegin);
      }
   }

   public void setCharAt(int index, char ch) {
      if (index >= 0 && index < this.count) {
         this.copyWhenShared();
         this.value[index] = ch;
      } else {
         throw new StringIndexOutOfBoundsException(index);
      }
   }

   public UnsyncStringBuffer append(Object obj) {
      return this.append(String.valueOf(obj));
   }

   public UnsyncStringBuffer append(String str) {
      if (str == null) {
         str = String.valueOf(str);
      }

      int len = str.length();
      this.ensureCapacity(this.count + len);
      this.copyWhenShared();
      str.getChars(0, len, this.value, this.count);
      this.count += len;
      return this;
   }

   public UnsyncStringBuffer append(char[] str) {
      int len = str.length;
      this.ensureCapacity(this.count + len);
      this.copyWhenShared();
      System.arraycopy(str, 0, this.value, this.count, len);
      this.count += len;
      return this;
   }

   public UnsyncStringBuffer append(char[] str, int offset, int len) {
      this.ensureCapacity(this.count + len);
      this.copyWhenShared();
      System.arraycopy(str, offset, this.value, this.count, len);
      this.count += len;
      return this;
   }

   public UnsyncStringBuffer append(boolean b) {
      return this.append(String.valueOf(b));
   }

   public UnsyncStringBuffer append(char c) {
      this.ensureCapacity(this.count + 1);
      this.copyWhenShared();
      this.value[this.count++] = c;
      return this;
   }

   public UnsyncStringBuffer append(int i) {
      return this.append(String.valueOf(i));
   }

   public UnsyncStringBuffer append(long l) {
      return this.append(String.valueOf(l));
   }

   public UnsyncStringBuffer append(float f) {
      return this.append(String.valueOf(f));
   }

   public UnsyncStringBuffer append(double d) {
      return this.append(String.valueOf(d));
   }

   public UnsyncStringBuffer insert(int offset, Object obj) {
      return this.insert(offset, String.valueOf(obj));
   }

   public UnsyncStringBuffer insert(int offset, String str) {
      if (offset >= 0 && offset <= this.count) {
         int len = str.length();
         this.ensureCapacity(this.count + len);
         this.copyWhenShared();
         System.arraycopy(this.value, offset, this.value, offset + len, this.count - offset);
         str.getChars(0, len, this.value, offset);
         this.count += len;
         return this;
      } else {
         throw new StringIndexOutOfBoundsException();
      }
   }

   public UnsyncStringBuffer insert(int offset, char[] str) {
      if (offset >= 0 && offset <= this.count) {
         int len = str.length;
         this.ensureCapacity(this.count + len);
         this.copyWhenShared();
         System.arraycopy(this.value, offset, this.value, offset + len, this.count - offset);
         System.arraycopy(str, 0, this.value, offset, len);
         this.count += len;
         return this;
      } else {
         throw new StringIndexOutOfBoundsException();
      }
   }

   public UnsyncStringBuffer insert(int offset, boolean b) {
      return this.insert(offset, String.valueOf(b));
   }

   public UnsyncStringBuffer insert(int offset, char c) {
      this.ensureCapacity(this.count + 1);
      this.copyWhenShared();
      System.arraycopy(this.value, offset, this.value, offset + 1, this.count - offset);
      this.value[offset] = c;
      ++this.count;
      return this;
   }

   public UnsyncStringBuffer insert(int offset, int i) {
      return this.insert(offset, String.valueOf(i));
   }

   public UnsyncStringBuffer insert(int offset, long l) {
      return this.insert(offset, String.valueOf(l));
   }

   public UnsyncStringBuffer insert(int offset, float f) {
      return this.insert(offset, String.valueOf(f));
   }

   public UnsyncStringBuffer insert(int offset, double d) {
      return this.insert(offset, String.valueOf(d));
   }

   public UnsyncStringBuffer reverse() {
      this.copyWhenShared();
      int n = this.count - 1;

      for(int j = n - 1 >> 1; j >= 0; --j) {
         char temp = this.value[j];
         this.value[j] = this.value[n - j];
         this.value[n - j] = temp;
      }

      return this;
   }

   public String toString() {
      this.shared = true;
      return new String(this.value, 0, this.count);
   }
}
