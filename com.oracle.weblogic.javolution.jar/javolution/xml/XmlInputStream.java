package javolution.xml;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javolution.lang.Reusable;

public class XmlInputStream extends InputStream implements Reusable {
   private final ObjectReader _objectReader;
   private final XmlReader _xmlReader;

   public XmlInputStream() {
      this(new ObjectReader());
   }

   public XmlInputStream(ObjectReader var1) {
      this._xmlReader = new XmlReader();
      this._objectReader = var1;
   }

   public XmlInputStream setInputStream(InputStream var1) {
      if (XmlInputStream.XmlReader.access$100(this._xmlReader) != null) {
         throw new IllegalStateException("Stream not closed or reset");
      } else {
         XmlInputStream.XmlReader.access$102(this._xmlReader, var1);
         return this;
      }
   }

   public Object readObject() throws IOException {
      Object var1;
      try {
         var1 = this._objectReader.read((Reader)this._xmlReader);
      } finally {
         this._xmlReader.resume();
      }

      return var1;
   }

   public void close() throws IOException {
      if (XmlInputStream.XmlReader.access$100(this._xmlReader) != null) {
         XmlInputStream.XmlReader.access$100(this._xmlReader).close();
         this.reset();
      }

   }

   public int read() throws IOException {
      if (XmlInputStream.XmlReader.access$200(this._xmlReader) < XmlInputStream.XmlReader.access$300(this._xmlReader)) {
         return XmlInputStream.XmlReader.access$400(this._xmlReader)[XmlInputStream.XmlReader.access$208(this._xmlReader)];
      } else {
         return this._xmlReader.fillBuffer() ? XmlInputStream.XmlReader.access$400(this._xmlReader)[XmlInputStream.XmlReader.access$208(this._xmlReader)] : -1;
      }
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = XmlInputStream.XmlReader.access$300(this._xmlReader) - XmlInputStream.XmlReader.access$200(this._xmlReader);
      if (var4 == 0) {
         return XmlInputStream.XmlReader.access$100(this._xmlReader).read(var1, var2, var3);
      } else {
         int var5 = var3 < var4 ? var3 : var4;
         System.arraycopy(XmlInputStream.XmlReader.access$400(this._xmlReader), XmlInputStream.XmlReader.access$200(this._xmlReader), var1, var2, var5);
         XmlInputStream.XmlReader.access$212(this._xmlReader, var5);
         return var5;
      }
   }

   public void reset() {
      this._objectReader.reset();
      this._xmlReader.reset();
   }

   private static final class XmlReader extends Reader implements Reusable {
      private InputStream _inputStream;
      private int _code;
      private int _moreBytes;
      private int _start;
      private int _end;
      private final byte[] _bytes;
      private boolean _isHalted;

      private XmlReader() {
         this._bytes = new byte[2048];
      }

      public void resume() {
         this._isHalted = false;
      }

      public boolean fillBuffer() throws IOException {
         if (this._inputStream == null) {
            throw new IOException("Stream closed");
         } else {
            this._start = 0;
            this._end = this._inputStream.read(this._bytes, 0, this._bytes.length);
            return this._end > 0;
         }
      }

      public int read(char[] var1, int var2, int var3) throws IOException {
         if (this._isHalted) {
            return -1;
         } else if (this._start >= this._end && !this.fillBuffer()) {
            return -1;
         } else {
            int var4 = var2 + var3;
            int var5 = var2;

            label55:
            do {
               while(var5 < var4) {
                  byte var6 = this._bytes[this._start];
                  if (var6 < 0 || ++this._start >= this._end) {
                     if (var6 >= 0) {
                        var1[var5++] = (char)var6;
                        return var5 - var2;
                     }

                     if (var6 == -2) {
                        ++this._start;
                        this._isHalted = true;
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
                     continue label55;
                  }

                  var1[var5++] = (char)var6;
               }

               return var3;
            } while(this._start < this._end);

            return var5 - var2;
         }
      }

      public void close() throws IOException {
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
            } else {
               throw new CharConversionException("Invalid UTF-8 Encoding");
            }
         } else if (this.fillBuffer()) {
            return this.read2();
         } else if (this._moreBytes == 0) {
            return -1;
         } else {
            throw new CharConversionException("Unexpected end of stream");
         }
      }

      public void reset() {
         this._code = 0;
         this._end = 0;
         this._inputStream = null;
         this._moreBytes = 0;
         this._start = 0;
      }

      XmlReader(Object var1) {
         this();
      }

      static InputStream access$100(XmlReader var0) {
         return var0._inputStream;
      }

      static InputStream access$102(XmlReader var0, InputStream var1) {
         return var0._inputStream = var1;
      }

      static int access$200(XmlReader var0) {
         return var0._start;
      }

      static int access$300(XmlReader var0) {
         return var0._end;
      }

      static byte[] access$400(XmlReader var0) {
         return var0._bytes;
      }

      static int access$208(XmlReader var0) {
         return var0._start++;
      }

      static int access$212(XmlReader var0, int var1) {
         return var0._start += var1;
      }
   }
}
