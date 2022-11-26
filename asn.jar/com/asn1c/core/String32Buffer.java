package com.asn1c.core;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class String32Buffer implements Serializable, Cloneable {
   private int[] value;
   private int count;

   public String32Buffer() {
      this(16);
   }

   public String32Buffer(int var1) {
      this.value = new int[var1];
      this.count = 0;
   }

   public String32Buffer(String32 var1) {
      this(var1.length() + 16);
      this.append(var1);
   }

   public String32Buffer(String16 var1) {
      this(var1.length() + 16);
      this.append(var1);
   }

   public String32Buffer(String var1) {
      this(var1.length() + 16);
      this.append(var1);
   }

   public String32Buffer(StringBuffer var1) {
      this(var1.length() + 16);
      this.append(var1);
   }

   public String32Buffer(String32Buffer var1) {
      this(var1.length() + 16);
      this.append(var1);
   }

   public String32Buffer append(Object var1) {
      return this.append(String32.valueOf(var1));
   }

   public String32Buffer append(String32 var1) {
      if (var1 == null) {
         var1 = String32.valueOf((Object)var1);
      }

      int var2 = var1.length();
      this.ensureCapacity(this.count + var2);
      var1.getChars(0, var2, this.value, this.count);
      this.count += var2;
      return this;
   }

   public String32Buffer append(String16 var1) {
      return this.append(new String32(var1));
   }

   public String32Buffer append(String var1) {
      return this.append(new String32(var1));
   }

   public String32Buffer append(StringBuffer var1) {
      return this.append(new String32(var1));
   }

   public String32Buffer append(String32Buffer var1) {
      return this.append(new String32(var1));
   }

   public String32Buffer append(int[] var1) {
      int var2 = var1.length;
      this.ensureCapacity(this.count + var2);
      System.arraycopy(var1, 0, this.value, this.count, var2);
      this.count += var2;
      return this;
   }

   public String32Buffer append(int[] var1, int var2, int var3) {
      this.ensureCapacity(this.count + var3);
      System.arraycopy(var1, var2, this.value, this.count, var3);
      this.count += var3;
      return this;
   }

   public String32Buffer append(boolean var1) {
      return this.append(String32.valueOf(var1));
   }

   public String32Buffer append(char var1) {
      return this.append(String32.valueOf(var1));
   }

   public String32Buffer append(int var1) {
      return this.append(String32.valueOf(var1));
   }

   public String32Buffer append(long var1) {
      return this.append(String32.valueOf(var1));
   }

   public String32Buffer append(float var1) {
      return this.append(String32.valueOf(var1));
   }

   public String32Buffer append(double var1) {
      return this.append(String32.valueOf(var1));
   }

   public String32Buffer appendChar(int var1) {
      this.ensureCapacity(this.count + 1);
      this.value[this.count++] = var1;
      return this;
   }

   public int capacity() {
      return this.value.length;
   }

   public int charAt(int var1) {
      if (var1 >= 0 && var1 < this.count) {
         return this.value[var1];
      } else {
         throw new StringIndexOutOfBoundsException(var1);
      }
   }

   public void ensureCapacity(int var1) {
      int var2 = this.value.length;
      if (var1 > var2) {
         int var3 = (var2 + 1) * 2;
         if (var1 > var3) {
            var3 = var1 + 16;
         }

         int[] var4 = new int[var3];
         System.arraycopy(this.value, 0, var4, 0, this.count);
         this.value = var4;
      }

   }

   public void shrinkCapacity() {
      int var1 = this.value.length;
      if (var1 > this.count) {
         int[] var2 = new int[this.count];
         System.arraycopy(this.value, 0, var2, 0, this.count);
         this.value = var2;
      }

   }

   public void getChars(int var1, int var2, int[] var3, int var4) {
      if (var1 < 0) {
         throw new StringIndexOutOfBoundsException(var1);
      } else if (var2 > this.count) {
         throw new StringIndexOutOfBoundsException(var2);
      } else {
         if (var1 < var2) {
            System.arraycopy(this.value, var1, var3, var4, var2 - var1);
         }

      }
   }

   public String32Buffer insert(int var1, Object var2) {
      return this.insert(var1, String32.valueOf(var2));
   }

   public String32Buffer insert(int var1, String32 var2) {
      if (var1 >= 0 && var1 <= this.count) {
         int var3 = var2.length();
         this.ensureCapacity(this.count + var3);
         System.arraycopy(this.value, var1, this.value, var1 + var3, this.count - var1);
         var2.getChars(0, var3, this.value, var1);
         this.count += var3;
         return this;
      } else {
         throw new StringIndexOutOfBoundsException();
      }
   }

   public String32Buffer insert(int var1, String16 var2) {
      return this.insert(var1, new String32(var2));
   }

   public String32Buffer insert(int var1, String var2) {
      return this.insert(var1, new String32(var2));
   }

   public String32Buffer insert(int var1, int[] var2) {
      if (var1 >= 0 && var1 <= this.count) {
         int var3 = var2.length;
         this.ensureCapacity(this.count + var3);
         System.arraycopy(this.value, var1, this.value, var1 + var3, this.count - var1);
         System.arraycopy(var2, 0, this.value, var1, var3);
         this.count += var3;
         return this;
      } else {
         throw new StringIndexOutOfBoundsException();
      }
   }

   public String32Buffer insert(int var1, boolean var2) {
      return this.insert(var1, String32.valueOf(var2));
   }

   public String32Buffer insert(int var1, char var2) {
      return this.insert(var1, String32.valueOf(var2));
   }

   public String32Buffer insert(int var1, int var2) {
      return this.insert(var1, String32.valueOf(var2));
   }

   public String32Buffer insert(int var1, long var2) {
      return this.insert(var1, String32.valueOf(var2));
   }

   public String32Buffer insert(int var1, float var2) {
      return this.insert(var1, String32.valueOf(var2));
   }

   public String32Buffer insert(int var1, double var2) {
      return this.insert(var1, String32.valueOf(var2));
   }

   public int length() {
      return this.count;
   }

   public String32Buffer reverse() {
      int var1 = this.count - 1;

      for(int var2 = var1 - 1 >> 1; var2 >= 0; --var2) {
         int var3 = this.value[var2];
         this.value[var2] = this.value[var1 - var2];
         this.value[var1 - var2] = var3;
      }

      return this;
   }

   public void setCharAt(int var1, int var2) {
      if (var1 >= 0 && var1 < this.count) {
         this.value[var1] = var2;
      } else {
         throw new StringIndexOutOfBoundsException(var1);
      }
   }

   public void setLength(int var1) {
      if (var1 < 0) {
         throw new StringIndexOutOfBoundsException(var1);
      } else {
         this.ensureCapacity(var1);
         if (this.count < var1) {
            while(this.count < var1) {
               this.value[this.count] = 0;
               ++this.count;
            }
         }

         this.count = var1;
      }
   }

   public Object clone() {
      String32Buffer var1;
      try {
         var1 = (String32Buffer)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      var1.value = (int[])this.value.clone();
      return var1;
   }

   public String toString() {
      char[] var1 = new char[this.count];

      for(int var2 = 0; var2 < this.count; ++var2) {
         var1[var2] = (char)this.value[var2];
      }

      return new String(var1);
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this);
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      this.shrinkCapacity();
      var1.defaultWriteObject();
   }
}
