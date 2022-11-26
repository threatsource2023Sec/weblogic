package com.asn1c.core;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;

public class ObjectIdentifier implements ASN1Object, Serializable, Cloneable {
   protected long[] value;
   protected int size;

   public ObjectIdentifier() {
      this.size = 0;
      this.value = new long[4];
   }

   public ObjectIdentifier(long[] var1) {
      this.size = var1.length;
      this.value = new long[this.size];
      System.arraycopy(var1, 0, this.value, 0, this.size);
   }

   public ObjectIdentifier(long[] var1, int var2, int var3) {
      this.size = var3;
      this.value = new long[this.size];
      System.arraycopy(var1, var2, this.value, 0, this.size);
   }

   public ObjectIdentifier(ObjectIdentifier var1) {
      this(var1.value, 0, var1.size);
   }

   public long get(int var1) {
      if (var1 >= 0 && var1 < this.size) {
         return this.value[var1];
      } else {
         throw new IndexOutOfBoundsException(Integer.toString(var1));
      }
   }

   public void get(int var1, long[] var2, int var3, int var4) {
      System.arraycopy(this.value, var1, var2, var3, var4);
   }

   public void set(int var1, long var2) {
      if (var1 >= 0 && var1 <= this.size) {
         if (var1 == this.size) {
            this.setLength(var1 + 1);
         }

         this.value[var1] = var2;
      } else {
         throw new IndexOutOfBoundsException(Integer.toString(var1));
      }
   }

   public void set(long[] var1, int var2, int var3, int var4) {
      if (this.size < var3 + var4) {
         throw new IndexOutOfBoundsException(Integer.toString(var3 + var4 - 1));
      } else {
         System.arraycopy(var1, var2, this.value, var3, var4);
      }
   }

   public int length() {
      return this.size;
   }

   public void setLength(int var1) {
      if (var1 < 0) {
         throw new IndexOutOfBoundsException(Integer.toString(var1));
      } else {
         if (var1 != this.size) {
            if (var1 > this.value.length) {
               long[] var2 = new long[var1 + 4];
               System.arraycopy(this.value, 0, var2, 0, this.size);
               this.value = var2;
            }

            for(int var3 = this.size; var3 < var1; ++var3) {
               this.value[var3] = 0L;
            }

            this.size = var1;
         }

      }
   }

   public void setValue(long[] var1) {
      this.size = var1.length;
      if (this.size > this.value.length) {
         this.value = new long[this.size];
      }

      System.arraycopy(var1, 0, this.value, 0, this.size);
   }

   public void setValue(long[] var1, int var2, int var3) {
      this.size = var3;
      if (this.size > this.value.length) {
         this.value = new long[this.size];
      }

      System.arraycopy(var1, var2, this.value, 0, this.size);
   }

   public void setValue(ObjectIdentifier var1) {
      this.setValue(var1.value, 0, var1.size);
   }

   public int capacity() {
      return this.value.length;
   }

   public void ensureCapacity(int var1) {
      if (var1 > this.value.length) {
         long[] var2 = new long[var1];
         System.arraycopy(this.value, 0, var2, 0, this.size);
         this.value = var2;
      }

   }

   public void shrinkCapacity() {
      if (this.value.length > this.size) {
         long[] var1 = new long[this.size];
         System.arraycopy(this.value, 0, var1, 0, this.size);
         this.value = var1;
      }

   }

   public int compareTo(Object var1) {
      ObjectIdentifier var2 = (ObjectIdentifier)var1;
      int var3 = Math.min(this.size, var2.size);

      for(int var4 = 0; var4 < var3; ++var4) {
         if (this.value[var4] != var2.value[var4]) {
            return this.value[var4] < var2.value[var4] ? -1 : 1;
         }
      }

      if (this.size != var2.size) {
         return this.size < var2.size ? -1 : 1;
      } else {
         return 0;
      }
   }

   public boolean equals(Object var1) {
      return this.compareTo(var1) == 0;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      this.shrinkCapacity();
      var1.defaultWriteObject();
   }

   public Object clone() {
      ObjectIdentifier var1 = null;

      try {
         var1 = (ObjectIdentifier)super.clone();
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }

      var1.value = new long[this.size + 4];
      var1.size = this.size;
      System.arraycopy(this.value, 0, var1.value, 0, this.size);
      return var1;
   }

   public void print(PrintWriter var1, String var2, String var3, String var4, int var5) {
      var1.println(var2 + var3 + this.toString() + var4);
   }

   public long[] toLongArray() {
      long[] var1 = new long[this.size];
      System.arraycopy(this.value, 0, var1, 0, this.size);
      return var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer("{");

      for(int var2 = 0; var2 < this.size; ++var2) {
         var1.append(' ');
         var1.append(this.value[var2]);
      }

      var1.append(" }");
      return var1.toString();
   }

   public String16 toString16() {
      return new String16(this.toString());
   }

   public String32 toString32() {
      return new String32(this.toString());
   }
}
