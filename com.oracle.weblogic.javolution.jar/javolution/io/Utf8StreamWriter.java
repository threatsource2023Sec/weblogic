package javolution.io;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import javolution.lang.Reusable;

public final class Utf8StreamWriter extends Writer implements Reusable {
   private OutputStream _outputStream;
   private final byte[] _bytes;
   private int _index;
   private char _highSurrogate;

   public Utf8StreamWriter() {
      this._bytes = new byte[2048];
   }

   public Utf8StreamWriter(int var1) {
      this._bytes = new byte[var1];
   }

   public Utf8StreamWriter setOutputStream(OutputStream var1) {
      if (this._outputStream != null) {
         throw new IllegalStateException("Writer not closed or reset");
      } else {
         this._outputStream = var1;
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
         this._bytes[this._index] = (byte)var1;
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }
      } else {
         this.write2(var1);
      }

   }

   private void write2(int var1) throws IOException {
      if ((var1 & -2048) == 0) {
         this._bytes[this._index] = (byte)(192 | var1 >> 6);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }
      } else if ((var1 & -65536) == 0) {
         this._bytes[this._index] = (byte)(224 | var1 >> 12);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 >> 6 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }
      } else if ((var1 & -14680064) == 0) {
         this._bytes[this._index] = (byte)(240 | var1 >> 18);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 >> 12 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 >> 6 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }
      } else if ((var1 & -201326592) == 0) {
         this._bytes[this._index] = (byte)(248 | var1 >> 24);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 >> 18 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 >> 12 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 >> 6 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }
      } else {
         if ((var1 & Integer.MIN_VALUE) != 0) {
            throw new CharConversionException("Illegal character U+" + Integer.toHexString(var1));
         }

         this._bytes[this._index] = (byte)(252 | var1 >> 30);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 >> 24 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 >> 18 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 >> 12 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 >> 6 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }

         this._bytes[this._index] = (byte)(128 | var1 & 63);
         if (++this._index >= this._bytes.length) {
            this.flushBuffer();
         }
      }

   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      int var4 = var2 + var3;
      int var5 = var2;

      while(var5 < var4) {
         char var6 = var1[var5++];
         if (var6 < 128) {
            this._bytes[this._index] = (byte)var6;
            if (++this._index >= this._bytes.length) {
               this.flushBuffer();
            }
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
            this._bytes[this._index] = (byte)var6;
            if (++this._index >= this._bytes.length) {
               this.flushBuffer();
            }
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
            this._bytes[this._index] = (byte)var4;
            if (++this._index >= this._bytes.length) {
               this.flushBuffer();
            }
         } else {
            this.write(var4);
         }
      }

   }

   public void flush() throws IOException {
      this.flushBuffer();
      this._outputStream.flush();
   }

   public void close() throws IOException {
      if (this._outputStream != null) {
         this.flushBuffer();
         this._outputStream.close();
         this.reset();
      }

   }

   private void flushBuffer() throws IOException {
      if (this._outputStream == null) {
         throw new IOException("Stream closed");
      } else {
         this._outputStream.write(this._bytes, 0, this._index);
         this._index = 0;
      }
   }

   public void reset() {
      this._highSurrogate = 0;
      this._index = 0;
      this._outputStream = null;
   }
}
