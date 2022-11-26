package javolution.io;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javolution.lang.Reusable;

public final class Utf8StreamReader extends Reader implements Reusable {
   private InputStream _inputStream;
   private int _start;
   private int _end;
   private final byte[] _bytes;
   private int _code;
   private int _moreBytes;

   public Utf8StreamReader() {
      this._bytes = new byte[2048];
   }

   public Utf8StreamReader(int var1) {
      this._bytes = new byte[var1];
   }

   public Utf8StreamReader setInputStream(InputStream var1) {
      if (this._inputStream != null) {
         throw new IllegalStateException("Reader not closed or reset");
      } else {
         this._inputStream = var1;
         return this;
      }
   }

   public boolean ready() throws IOException {
      if (this._inputStream == null) {
         throw new IOException("Stream closed");
      } else {
         return this._end - this._start > 0 || this._inputStream.available() != 0;
      }
   }

   public void close() throws IOException {
      if (this._inputStream != null) {
         this._inputStream.close();
         this.reset();
      }

   }

   public int read() throws IOException {
      byte var1 = this._bytes[this._start];
      return var1 >= 0 && this._start++ < this._end ? var1 : this.read2();
   }

   private int read2() throws IOException {
      if (this._start < this._end) {
         byte var1 = this._bytes[this._start++];
         if (var1 >= 0 && this._moreBytes == 0) {
            return var1;
         } else if ((var1 & 192) == 128 && this._moreBytes != 0) {
            this._code = this._code << 6 | var1 & 63;
            return --this._moreBytes == 0 ? this._code : this.read2();
         } else if ((var1 & 224) == 192 && this._moreBytes == 0) {
            this._code = var1 & 31;
            this._moreBytes = 1;
            return this.read2();
         } else if ((var1 & 240) == 224 && this._moreBytes == 0) {
            this._code = var1 & 15;
            this._moreBytes = 2;
            return this.read2();
         } else if ((var1 & 248) == 240 && this._moreBytes == 0) {
            this._code = var1 & 7;
            this._moreBytes = 3;
            return this.read2();
         } else if ((var1 & 252) == 248 && this._moreBytes == 0) {
            this._code = var1 & 3;
            this._moreBytes = 4;
            return this.read2();
         } else if ((var1 & 254) == 252 && this._moreBytes == 0) {
            this._code = var1 & 1;
            this._moreBytes = 5;
            return this.read2();
         } else {
            throw new CharConversionException("Invalid UTF-8 Encoding");
         }
      } else if (this._inputStream == null) {
         throw new IOException("No input stream or stream closed");
      } else {
         this._start = 0;
         this._end = this._inputStream.read(this._bytes, 0, this._bytes.length);
         if (this._end > 0) {
            return this.read2();
         } else if (this._moreBytes == 0) {
            return -1;
         } else {
            throw new CharConversionException("Unexpected end of stream");
         }
      }
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if (this._inputStream == null) {
         throw new IOException("No input stream or stream closed");
      } else {
         if (this._start >= this._end) {
            this._start = 0;
            this._end = this._inputStream.read(this._bytes, 0, this._bytes.length);
            if (this._end <= 0) {
               return this._end;
            }
         }

         int var4 = var2 + var3;
         int var5 = var2;

         label51:
         do {
            while(var5 < var4) {
               byte var6 = this._bytes[this._start];
               if (var6 < 0 || ++this._start >= this._end) {
                  if (var6 >= 0) {
                     var1[var5++] = (char)var6;
                     return var5 - var2;
                  }

                  if (var5 >= var4 - 1) {
                     return var5 - var2;
                  }

                  int var7 = this.read2();
                  if (var7 < 65536) {
                     var1[var5++] = (char)var7;
                  } else {
                     if (var7 > 1114111) {
                        throw new CharConversionException("Cannot convert U+" + Integer.toHexString(var7) + " to char (code greater than U+10FFFF)");
                     }

                     var1[var5++] = (char)((var7 - 65536 >> 10) + '\ud800');
                     var1[var5++] = (char)((var7 - 65536 & 1023) + '\udc00');
                  }
                  continue label51;
               }

               var1[var5++] = (char)var6;
            }

            return var3;
         } while(this._start < this._end);

         return var5 - var2;
      }
   }

   public void read(Appendable var1) throws IOException {
      if (this._inputStream == null) {
         throw new IOException("No input stream or stream closed");
      } else {
         while(true) {
            if (this._start >= this._end) {
               this._start = 0;
               this._end = this._inputStream.read(this._bytes, 0, this._bytes.length);
               if (this._end <= 0) {
                  return;
               }
            }

            byte var2 = this._bytes[this._start];
            if (var2 >= 0) {
               var1.append((char)var2);
               ++this._start;
            } else {
               int var3 = this.read2();
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
      this._code = 0;
      this._end = 0;
      this._inputStream = null;
      this._moreBytes = 0;
      this._start = 0;
   }
}
