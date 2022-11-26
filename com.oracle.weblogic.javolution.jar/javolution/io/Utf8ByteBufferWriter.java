package javolution.io;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;
import javolution.lang.Reusable;

public final class Utf8ByteBufferWriter extends Writer implements Reusable {
   private ByteBuffer _byteBuffer;
   private char _highSurrogate;

   public Utf8ByteBufferWriter setByteBuffer(ByteBuffer var1) {
      if (this._byteBuffer != null) {
         throw new IllegalStateException("Writer not closed or reset");
      } else {
         this._byteBuffer = var1;
         return this;
      }
   }

   public void write(char var1) throws IOException {
      if (var1 >= '\ud800' && var1 <= '\udfff') {
         if (var1 < '\udc00') {
            this._highSurrogate = var1;
         } else {
            int var2 = (this._highSurrogate - '\ud800' << 10) + (var1 - '\udc00') + 65536;
            this.write(var2);
         }
      } else {
         this.write((int)var1);
      }

   }

   public void write(int var1) throws IOException {
      if ((var1 & -128) == 0) {
         this._byteBuffer.put((byte)var1);
      } else {
         this.write2(var1);
      }

   }

   private void write2(int var1) throws IOException {
      if ((var1 & -2048) == 0) {
         this._byteBuffer.put((byte)(192 | var1 >> 6));
         this._byteBuffer.put((byte)(128 | var1 & 63));
      } else if ((var1 & -65536) == 0) {
         this._byteBuffer.put((byte)(224 | var1 >> 12));
         this._byteBuffer.put((byte)(128 | var1 >> 6 & 63));
         this._byteBuffer.put((byte)(128 | var1 & 63));
      } else if ((var1 & -14680064) == 0) {
         this._byteBuffer.put((byte)(240 | var1 >> 18));
         this._byteBuffer.put((byte)(128 | var1 >> 12 & 63));
         this._byteBuffer.put((byte)(128 | var1 >> 6 & 63));
         this._byteBuffer.put((byte)(128 | var1 & 63));
      } else if ((var1 & -201326592) == 0) {
         this._byteBuffer.put((byte)(248 | var1 >> 24));
         this._byteBuffer.put((byte)(128 | var1 >> 18 & 63));
         this._byteBuffer.put((byte)(128 | var1 >> 12 & 63));
         this._byteBuffer.put((byte)(128 | var1 >> 6 & 63));
         this._byteBuffer.put((byte)(128 | var1 & 63));
      } else {
         if ((var1 & Integer.MIN_VALUE) != 0) {
            throw new CharConversionException("Illegal character U+" + Integer.toHexString(var1));
         }

         this._byteBuffer.put((byte)(252 | var1 >> 30));
         this._byteBuffer.put((byte)(128 | var1 >> 24 & 63));
         this._byteBuffer.put((byte)(128 | var1 >> 18 & 63));
         this._byteBuffer.put((byte)(128 | var1 >> 12 & 63));
         this._byteBuffer.put((byte)(128 | var1 >> 6 & 63));
         this._byteBuffer.put((byte)(128 | var1 & 63));
      }

   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      int var4 = var2 + var3;
      int var5 = var2;

      while(var5 < var4) {
         char var6 = var1[var5++];
         if (var6 < 128) {
            this._byteBuffer.put((byte)var6);
         } else {
            this.write(var6);
         }
      }

   }

   public void write(String var1, int var2, int var3) throws IOException {
      int var4 = var2 + var3;
      int var5 = var2;

      while(var5 < var4) {
         char var6 = var1.charAt(var5++);
         if (var6 < 128) {
            this._byteBuffer.put((byte)var6);
         } else {
            this.write(var6);
         }
      }

   }

   public void write(CharSequence var1) throws IOException {
      int var2 = var1.length();
      int var3 = 0;

      while(var3 < var2) {
         char var4 = var1.charAt(var3++);
         if (var4 < 128) {
            this._byteBuffer.put((byte)var4);
         } else {
            this.write(var4);
         }
      }

   }

   public void flush() throws IOException {
      if (this._byteBuffer == null) {
         throw new IOException("Writer closed");
      }
   }

   public void close() throws IOException {
      if (this._byteBuffer != null) {
         this.reset();
      }

   }

   public void reset() {
      this._byteBuffer = null;
      this._highSurrogate = 0;
   }
}
