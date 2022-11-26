package org.python.bouncycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ByteQueue {
   private static final int DEFAULT_CAPACITY = 1024;
   private byte[] databuf;
   private int skipped;
   private int available;
   private boolean readOnlyBuf;

   public static int nextTwoPow(int var0) {
      var0 |= var0 >> 1;
      var0 |= var0 >> 2;
      var0 |= var0 >> 4;
      var0 |= var0 >> 8;
      var0 |= var0 >> 16;
      return var0 + 1;
   }

   public ByteQueue() {
      this(1024);
   }

   public ByteQueue(int var1) {
      this.skipped = 0;
      this.available = 0;
      this.readOnlyBuf = false;
      this.databuf = var1 == 0 ? TlsUtils.EMPTY_BYTES : new byte[var1];
   }

   public ByteQueue(byte[] var1, int var2, int var3) {
      this.skipped = 0;
      this.available = 0;
      this.readOnlyBuf = false;
      this.databuf = var1;
      this.skipped = var2;
      this.available = var3;
      this.readOnlyBuf = true;
   }

   public void addData(byte[] var1, int var2, int var3) {
      if (this.readOnlyBuf) {
         throw new IllegalStateException("Cannot add data to read-only buffer");
      } else {
         if (this.skipped + this.available + var3 > this.databuf.length) {
            int var4 = nextTwoPow(this.available + var3);
            if (var4 > this.databuf.length) {
               byte[] var5 = new byte[var4];
               System.arraycopy(this.databuf, this.skipped, var5, 0, this.available);
               this.databuf = var5;
            } else {
               System.arraycopy(this.databuf, this.skipped, this.databuf, 0, this.available);
            }

            this.skipped = 0;
         }

         System.arraycopy(var1, var2, this.databuf, this.skipped + this.available, var3);
         this.available += var3;
      }
   }

   public int available() {
      return this.available;
   }

   public void copyTo(OutputStream var1, int var2) throws IOException {
      if (var2 > this.available) {
         throw new IllegalStateException("Cannot copy " + var2 + " bytes, only got " + this.available);
      } else {
         var1.write(this.databuf, this.skipped, var2);
      }
   }

   public void read(byte[] var1, int var2, int var3, int var4) {
      if (var1.length - var2 < var3) {
         throw new IllegalArgumentException("Buffer size of " + var1.length + " is too small for a read of " + var3 + " bytes");
      } else if (this.available - var4 < var3) {
         throw new IllegalStateException("Not enough data to read");
      } else {
         System.arraycopy(this.databuf, this.skipped + var4, var1, var2, var3);
      }
   }

   public ByteArrayInputStream readFrom(int var1) {
      if (var1 > this.available) {
         throw new IllegalStateException("Cannot read " + var1 + " bytes, only got " + this.available);
      } else {
         int var2 = this.skipped;
         this.available -= var1;
         this.skipped += var1;
         return new ByteArrayInputStream(this.databuf, var2, var1);
      }
   }

   public void removeData(int var1) {
      if (var1 > this.available) {
         throw new IllegalStateException("Cannot remove " + var1 + " bytes, only got " + this.available);
      } else {
         this.available -= var1;
         this.skipped += var1;
      }
   }

   public void removeData(byte[] var1, int var2, int var3, int var4) {
      this.read(var1, var2, var3, var4);
      this.removeData(var4 + var3);
   }

   public byte[] removeData(int var1, int var2) {
      byte[] var3 = new byte[var1];
      this.removeData(var3, 0, var1, var2);
      return var3;
   }

   public void shrink() {
      if (this.available == 0) {
         this.databuf = TlsUtils.EMPTY_BYTES;
         this.skipped = 0;
      } else {
         int var1 = nextTwoPow(this.available);
         if (var1 < this.databuf.length) {
            byte[] var2 = new byte[var1];
            System.arraycopy(this.databuf, this.skipped, var2, 0, this.available);
            this.databuf = var2;
            this.skipped = 0;
         }
      }

   }
}
