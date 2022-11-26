package com.bea.core.repackaged.springframework.util;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public abstract class StreamUtils {
   public static final int BUFFER_SIZE = 4096;
   private static final byte[] EMPTY_CONTENT = new byte[0];

   public static byte[] copyToByteArray(@Nullable InputStream in) throws IOException {
      if (in == null) {
         return new byte[0];
      } else {
         ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
         copy((InputStream)in, out);
         return out.toByteArray();
      }
   }

   public static String copyToString(@Nullable InputStream in, Charset charset) throws IOException {
      if (in == null) {
         return "";
      } else {
         StringBuilder out = new StringBuilder();
         InputStreamReader reader = new InputStreamReader(in, charset);
         char[] buffer = new char[4096];
         int bytesRead = true;

         int bytesRead;
         while((bytesRead = reader.read(buffer)) != -1) {
            out.append(buffer, 0, bytesRead);
         }

         return out.toString();
      }
   }

   public static void copy(byte[] in, OutputStream out) throws IOException {
      Assert.notNull(in, (String)"No input byte array specified");
      Assert.notNull(out, (String)"No OutputStream specified");
      out.write(in);
   }

   public static void copy(String in, Charset charset, OutputStream out) throws IOException {
      Assert.notNull(in, (String)"No input String specified");
      Assert.notNull(charset, (String)"No charset specified");
      Assert.notNull(out, (String)"No OutputStream specified");
      Writer writer = new OutputStreamWriter(out, charset);
      writer.write(in);
      writer.flush();
   }

   public static int copy(InputStream in, OutputStream out) throws IOException {
      Assert.notNull(in, (String)"No InputStream specified");
      Assert.notNull(out, (String)"No OutputStream specified");
      int byteCount = 0;
      byte[] buffer = new byte[4096];

      int bytesRead;
      for(int bytesRead = true; (bytesRead = in.read(buffer)) != -1; byteCount += bytesRead) {
         out.write(buffer, 0, bytesRead);
      }

      out.flush();
      return byteCount;
   }

   public static long copyRange(InputStream in, OutputStream out, long start, long end) throws IOException {
      Assert.notNull(in, (String)"No InputStream specified");
      Assert.notNull(out, (String)"No OutputStream specified");
      long skipped = in.skip(start);
      if (skipped < start) {
         throw new IOException("Skipped only " + skipped + " bytes out of " + start + " required");
      } else {
         long bytesToCopy = end - start + 1L;
         byte[] buffer = new byte[4096];

         while(bytesToCopy > 0L) {
            int bytesRead = in.read(buffer);
            if (bytesRead == -1) {
               break;
            }

            if ((long)bytesRead <= bytesToCopy) {
               out.write(buffer, 0, bytesRead);
               bytesToCopy -= (long)bytesRead;
            } else {
               out.write(buffer, 0, (int)bytesToCopy);
               bytesToCopy = 0L;
            }
         }

         return end - start + 1L - bytesToCopy;
      }
   }

   public static int drain(InputStream in) throws IOException {
      Assert.notNull(in, (String)"No InputStream specified");
      byte[] buffer = new byte[4096];
      int bytesRead = true;

      int byteCount;
      int bytesRead;
      for(byteCount = 0; (bytesRead = in.read(buffer)) != -1; byteCount += bytesRead) {
      }

      return byteCount;
   }

   public static InputStream emptyInput() {
      return new ByteArrayInputStream(EMPTY_CONTENT);
   }

   public static InputStream nonClosing(InputStream in) {
      Assert.notNull(in, (String)"No InputStream specified");
      return new NonClosingInputStream(in);
   }

   public static OutputStream nonClosing(OutputStream out) {
      Assert.notNull(out, (String)"No OutputStream specified");
      return new NonClosingOutputStream(out);
   }

   private static class NonClosingOutputStream extends FilterOutputStream {
      public NonClosingOutputStream(OutputStream out) {
         super(out);
      }

      public void write(byte[] b, int off, int let) throws IOException {
         this.out.write(b, off, let);
      }

      public void close() throws IOException {
      }
   }

   private static class NonClosingInputStream extends FilterInputStream {
      public NonClosingInputStream(InputStream in) {
         super(in);
      }

      public void close() throws IOException {
      }
   }
}
