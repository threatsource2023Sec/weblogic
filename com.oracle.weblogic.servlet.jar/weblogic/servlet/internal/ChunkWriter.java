package weblogic.servlet.internal;

import java.io.IOException;
import java.io.Writer;

public final class ChunkWriter extends Writer {
   private boolean error = false;
   private ChunkOutputWrapper co;
   private char[] writeBuffer;
   private static final int writeBufferSize = 1024;

   public ChunkWriter(ChunkOutputWrapper co) {
      this.co = co;
   }

   public String getEncoding() {
      String enc = this.co.getEncoding();
      if (enc == null) {
         enc = "ISO-8859-1";
      }

      return enc;
   }

   public void write(int i) throws IOException {
      if (!this.error) {
         try {
            this.co.write(i);
         } catch (IOException var3) {
            this.error = true;
            throw var3;
         }
      }
   }

   public void write(char[] c, int off, int len) throws IOException {
      if (!this.error) {
         try {
            this.co.write(c, off, len);
         } catch (IOException var5) {
            this.error = true;
            throw var5;
         }
      }
   }

   public void write(String s) throws IOException {
      if (!this.error) {
         try {
            this.co.print(s);
         } catch (IOException var3) {
            this.error = true;
            throw var3;
         }
      }
   }

   public void write(String str, int off, int len) throws IOException {
      char[] cbuf;
      if (len <= 1024) {
         if (this.writeBuffer == null) {
            this.writeBuffer = new char[1024];
         }

         cbuf = this.writeBuffer;
      } else {
         cbuf = new char[len];
      }

      str.getChars(off, off + len, cbuf, 0);
      this.write((char[])cbuf, 0, len);
   }

   public void flush() throws IOException {
      if (!this.error) {
         try {
            this.co.flush();
         } catch (IOException var2) {
            this.error = true;
            throw var2;
         }
      }
   }

   public void close() throws IOException {
   }
}
