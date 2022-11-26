package javolution.io;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.Reader;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import javolution.lang.Reusable;

public final class Utf8ByteBufferReader extends Reader implements Reusable {
   private ByteBuffer _byteBuffer;
   private int _code;
   private int _moreBytes;

   public Utf8ByteBufferReader setByteBuffer(ByteBuffer var1) {
      if (this._byteBuffer != null) {
         throw new IllegalStateException("Reader not closed or reset");
      } else {
         this._byteBuffer = var1;
         return this;
      }
   }

   public boolean ready() throws IOException {
      if (this._byteBuffer != null) {
         return this._byteBuffer.hasRemaining();
      } else {
         throw new IOException("Reader closed");
      }
   }

   public void close() throws IOException {
      if (this._byteBuffer != null) {
         this.reset();
      }

   }

   public int read() throws IOException {
      if (this._byteBuffer != null) {
         if (this._byteBuffer.hasRemaining()) {
            byte var1 = this._byteBuffer.get();
            return var1 >= 0 ? var1 : this.read2(var1);
         } else {
            return -1;
         }
      } else {
         throw new IOException("Reader closed");
      }
   }

   private int read2(byte var1) throws IOException {
      try {
         if (var1 >= 0 && this._moreBytes == 0) {
            return var1;
         } else if ((var1 & 192) == 128 && this._moreBytes != 0) {
            this._code = this._code << 6 | var1 & 63;
            return --this._moreBytes == 0 ? this._code : this.read2(this._byteBuffer.get());
         } else if ((var1 & 224) == 192 && this._moreBytes == 0) {
            this._code = var1 & 31;
            this._moreBytes = 1;
            return this.read2(this._byteBuffer.get());
         } else if ((var1 & 240) == 224 && this._moreBytes == 0) {
            this._code = var1 & 15;
            this._moreBytes = 2;
            return this.read2(this._byteBuffer.get());
         } else if ((var1 & 248) == 240 && this._moreBytes == 0) {
            this._code = var1 & 7;
            this._moreBytes = 3;
            return this.read2(this._byteBuffer.get());
         } else if ((var1 & 252) == 248 && this._moreBytes == 0) {
            this._code = var1 & 3;
            this._moreBytes = 4;
            return this.read2(this._byteBuffer.get());
         } else if ((var1 & 254) == 252 && this._moreBytes == 0) {
            this._code = var1 & 1;
            this._moreBytes = 5;
            return this.read2(this._byteBuffer.get());
         } else {
            throw new CharConversionException("Invalid UTF-8 Encoding");
         }
      } catch (BufferUnderflowException var3) {
         throw new CharConversionException("Incomplete Sequence");
      }
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if (this._byteBuffer == null) {
         throw new IOException("Reader closed");
      } else {
         int var4 = var2 + var3;
         int var5 = this._byteBuffer.remaining();
         int var6 = var2;

         while(var6 < var4) {
            if (var5-- <= 0) {
               return var6 - var2;
            }

            byte var7 = this._byteBuffer.get();
            if (var7 >= 0) {
               var1[var6++] = (char)var7;
            } else {
               if (var6 >= var4 - 1) {
                  this._byteBuffer.position(this._byteBuffer.position() - 1);
                  ++var5;
                  return var6 - var2;
               }

               int var8 = this.read2(var7);
               var5 = this._byteBuffer.remaining();
               if (var8 < 65536) {
                  var1[var6++] = (char)var8;
               } else {
                  if (var8 > 1114111) {
                     throw new CharConversionException("Cannot convert U+" + Integer.toHexString(var8) + " to char (code greater than U+10FFFF)");
                  }

                  var1[var6++] = (char)((var8 - 65536 >> 10) + '\ud800');
                  var1[var6++] = (char)((var8 - 65536 & 1023) + '\udc00');
               }
            }
         }

         return var3;
      }
   }

   public void read(Appendable var1) throws IOException {
      if (this._byteBuffer == null) {
         throw new IOException("Reader closed");
      } else {
         while(this._byteBuffer.hasRemaining()) {
            byte var2 = this._byteBuffer.get();
            if (var2 >= 0) {
               var1.append((char)var2);
            } else {
               int var3 = this.read2(var2);
               if (var3 < 65536) {
                  var1.append((char)var3);
               } else {
                  if (var3 > 1114111) {
                     throw new CharConversionException("Cannot convert U+" + Integer.toHexString(var3) + " to char (code greater than U+10FFFF)");
                  }

                  var1.append((char)((var3 - 65536 >> 10) + '\ud800'));
                  var1.append((char)((var3 - 65536 & 1023) + '\udc00'));
               }
            }
         }

      }
   }

   public void reset() {
      this._byteBuffer = null;
      this._code = 0;
      this._moreBytes = 0;
   }
}
