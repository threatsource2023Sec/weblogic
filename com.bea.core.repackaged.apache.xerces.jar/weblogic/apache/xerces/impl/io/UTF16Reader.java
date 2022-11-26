package weblogic.apache.xerces.impl.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Locale;
import weblogic.apache.xerces.impl.msg.XMLMessageFormatter;
import weblogic.apache.xerces.util.MessageFormatter;

public final class UTF16Reader extends Reader {
   public static final int DEFAULT_BUFFER_SIZE = 4096;
   protected final InputStream fInputStream;
   protected final byte[] fBuffer;
   protected final boolean fIsBigEndian;
   private final MessageFormatter fFormatter;
   private final Locale fLocale;

   public UTF16Reader(InputStream var1, boolean var2) {
      this(var1, 4096, var2, new XMLMessageFormatter(), Locale.getDefault());
   }

   public UTF16Reader(InputStream var1, boolean var2, MessageFormatter var3, Locale var4) {
      this(var1, 4096, var2, var3, var4);
   }

   public UTF16Reader(InputStream var1, int var2, boolean var3, MessageFormatter var4, Locale var5) {
      this(var1, new byte[var2], var3, var4, var5);
   }

   public UTF16Reader(InputStream var1, byte[] var2, boolean var3, MessageFormatter var4, Locale var5) {
      this.fInputStream = var1;
      this.fBuffer = var2;
      this.fIsBigEndian = var3;
      this.fFormatter = var4;
      this.fLocale = var5;
   }

   public int read() throws IOException {
      int var1 = this.fInputStream.read();
      if (var1 == -1) {
         return -1;
      } else {
         int var2 = this.fInputStream.read();
         if (var2 == -1) {
            this.expectedTwoBytes();
         }

         return this.fIsBigEndian ? var1 << 8 | var2 : var2 << 8 | var1;
      }
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      int var4 = var3 << 1;
      if (var4 > this.fBuffer.length) {
         var4 = this.fBuffer.length;
      }

      int var5 = this.fInputStream.read(this.fBuffer, 0, var4);
      if (var5 == -1) {
         return -1;
      } else {
         int var6;
         if ((var5 & 1) != 0) {
            var6 = this.fInputStream.read();
            if (var6 == -1) {
               this.expectedTwoBytes();
            }

            this.fBuffer[var5++] = (byte)var6;
         }

         var6 = var5 >> 1;
         if (this.fIsBigEndian) {
            this.processBE(var1, var2, var6);
         } else {
            this.processLE(var1, var2, var6);
         }

         return var6;
      }
   }

   public long skip(long var1) throws IOException {
      long var3 = this.fInputStream.skip(var1 << 1);
      if ((var3 & 1L) != 0L) {
         int var5 = this.fInputStream.read();
         if (var5 == -1) {
            this.expectedTwoBytes();
         }

         ++var3;
      }

      return var3 >> 1;
   }

   public boolean ready() throws IOException {
      return false;
   }

   public boolean markSupported() {
      return false;
   }

   public void mark(int var1) throws IOException {
      throw new IOException(this.fFormatter.formatMessage(this.fLocale, "OperationNotSupported", new Object[]{"mark()", "UTF-16"}));
   }

   public void reset() throws IOException {
   }

   public void close() throws IOException {
      this.fInputStream.close();
   }

   private void processBE(char[] var1, int var2, int var3) {
      int var4 = 0;

      for(int var5 = 0; var5 < var3; ++var5) {
         int var6 = this.fBuffer[var4++] & 255;
         int var7 = this.fBuffer[var4++] & 255;
         var1[var2++] = (char)(var6 << 8 | var7);
      }

   }

   private void processLE(char[] var1, int var2, int var3) {
      int var4 = 0;

      for(int var5 = 0; var5 < var3; ++var5) {
         int var6 = this.fBuffer[var4++] & 255;
         int var7 = this.fBuffer[var4++] & 255;
         var1[var2++] = (char)(var7 << 8 | var6);
      }

   }

   private void expectedTwoBytes() throws MalformedByteSequenceException {
      throw new MalformedByteSequenceException(this.fFormatter, this.fLocale, "http://www.w3.org/TR/1998/REC-xml-19980210", "ExpectedByte", new Object[]{"2", "2"});
   }
}
