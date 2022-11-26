package netscape.ldap.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;

public final class ByteBuf implements Serializable {
   static final long serialVersionUID = -786393456618166871L;
   private byte[] value;
   private int count;

   public ByteBuf() {
      this(16);
   }

   public ByteBuf(int var1) {
      this.value = new byte[var1];
   }

   public ByteBuf(String var1) {
      this(var1.length() + 16);
      this.append(var1);
   }

   public ByteBuf(byte[] var1, int var2, int var3) {
      this.value = new byte[var3];
      System.arraycopy(var1, var2, this.value, 0, var3);
      this.count = var3;
   }

   public int length() {
      return this.count;
   }

   public int capacity() {
      return this.value.length;
   }

   public void ensureCapacity(int var1) {
      int var2 = this.value.length;
      if (var1 > var2) {
         int var3 = (var2 + 1) * 2;
         if (var1 > var3) {
            var3 = var1;
         }

         byte[] var4 = new byte[var3];
         System.arraycopy(this.value, 0, var4, 0, this.count);
         this.value = var4;
      }

   }

   public void setLength(int var1) {
      if (var1 < 0) {
         throw new StringIndexOutOfBoundsException(var1);
      } else {
         if (this.count < var1) {
            this.ensureCapacity(var1);

            while(this.count < var1) {
               this.value[this.count] = 0;
               ++this.count;
            }
         }

         this.count = var1;
      }
   }

   public byte byteAt(int var1) {
      if (var1 >= 0 && var1 < this.count) {
         return this.value[var1];
      } else {
         throw new StringIndexOutOfBoundsException(var1);
      }
   }

   public void getBytes(int var1, int var2, byte[] var3, int var4) {
      if (var1 >= 0 && var1 < this.count) {
         if (var2 >= 0 && var2 <= this.count) {
            if (var1 < var2) {
               System.arraycopy(this.value, var1, var3, var4, var2 - var1);
            }

         } else {
            throw new StringIndexOutOfBoundsException(var2);
         }
      } else {
         throw new StringIndexOutOfBoundsException(var1);
      }
   }

   public void setByteAt(int var1, byte var2) {
      if (var1 >= 0 && var1 < this.count) {
         this.value[var1] = var2;
      } else {
         throw new StringIndexOutOfBoundsException(var1);
      }
   }

   public ByteBuf append(Object var1) {
      return this.append(String.valueOf(var1));
   }

   public ByteBuf append(String var1) {
      if (var1 == null) {
         var1 = String.valueOf(var1);
      }

      int var2 = var1.length();
      this.ensureCapacity(this.count + var2);

      for(int var3 = 0; var3 < var2; ++var3) {
         this.value[this.count++] = (byte)var1.charAt(var3);
      }

      return this;
   }

   public ByteBuf append(byte[] var1) {
      int var2 = var1.length;
      this.ensureCapacity(this.count + var2);
      System.arraycopy(var1, 0, this.value, this.count, var2);
      this.count += var2;
      return this;
   }

   public ByteBuf append(byte[] var1, int var2, int var3) {
      this.ensureCapacity(this.count + var3);
      System.arraycopy(var1, var2, this.value, this.count, var3);
      this.count += var3;
      return this;
   }

   public ByteBuf append(ByteBuf var1) {
      this.append(var1.toBytes(), 0, var1.length());
      return this;
   }

   public ByteBuf append(boolean var1) {
      return this.append(String.valueOf(var1));
   }

   public ByteBuf append(byte var1) {
      this.ensureCapacity(this.count + 1);
      this.value[this.count++] = var1;
      return this;
   }

   public ByteBuf append(int var1) {
      return this.append(String.valueOf(var1));
   }

   public ByteBuf append(long var1) {
      return this.append(String.valueOf(var1));
   }

   public ByteBuf append(float var1) {
      return this.append(String.valueOf(var1));
   }

   public ByteBuf append(double var1) {
      return this.append(String.valueOf(var1));
   }

   public String toString() {
      return new String(this.value, 0, this.count);
   }

   public byte[] toBytes() {
      byte[] var1 = new byte[this.count];
      System.arraycopy(this.value, 0, var1, 0, this.count);
      return var1;
   }

   public int read(InputStream var1, int var2) throws IOException {
      this.ensureCapacity(this.count + var2);
      int var3 = var1.read(this.value, this.count, var2);
      if (var3 > 0) {
         this.count += var3;
      }

      return var3;
   }

   public int read(RandomAccessFile var1, int var2) throws IOException {
      this.ensureCapacity(this.count + var2);
      int var3 = var1.read(this.value, this.count, var2);
      if (var3 > 0) {
         this.count += var3;
      }

      return var3;
   }

   public void write(OutputStream var1) throws IOException {
      var1.write(this.value, 0, this.count);
   }

   public void write(RandomAccessFile var1) throws IOException {
      var1.write(this.value, 0, this.count);
   }
}
