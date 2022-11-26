package weblogic.servlet.utils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class ChunkedGzipOutputStream extends FilterOutputStream {
   protected GZIPOutputStream gzipOutput = null;
   protected final OutputStream internalOutput = new InternalOutputStream();
   protected byte[] buff;
   protected long bytesWritten;

   public ChunkedGzipOutputStream(OutputStream out) {
      super(out);
   }

   public void write(int b) throws IOException {
      if (this.gzipOutput == null) {
         this.gzipOutput = new GZIPOutputStream(this.internalOutput);
      }

      this.gzipOutput.write(b);
   }

   public void write(byte[] b, int off, int len) throws IOException {
      if (this.gzipOutput == null) {
         this.gzipOutput = new GZIPOutputStream(this.internalOutput);
      }

      this.gzipOutput.write(b, off, len);
   }

   public void flush() throws IOException {
      if (this.gzipOutput != null) {
         this.gzipOutput.flush();
      }

   }

   public void finish() throws IOException {
      if (this.gzipOutput != null) {
         this.gzipOutput.finish();
         this.gzipOutput.close();
      }

   }

   public long getGzipedContentLength() {
      return this.bytesWritten;
   }

   protected class InternalOutputStream extends OutputStream {
      protected final int CHUNK_HEADER_SIZE = 6;
      protected final int CHUNK_TRAILER_SIZE = 2;
      protected final byte[] DIGITS = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};

      public void write(int b) throws IOException {
         byte[] buf = new byte[]{(byte)(b & 255)};
         this.write(buf, 0, 1);
      }

      public void write(byte[] b, int off, int len) throws IOException {
         int toWrite = 6 + len + 2;
         if (ChunkedGzipOutputStream.this.buff == null || ChunkedGzipOutputStream.this.buff.length < toWrite) {
            ChunkedGzipOutputStream.this.buff = new byte[toWrite];
         }

         this.writeChunkHeader(ChunkedGzipOutputStream.this.buff, len);
         System.arraycopy(b, off, ChunkedGzipOutputStream.this.buff, 6, len);
         ChunkedGzipOutputStream.this.buff[toWrite - 2] = 13;
         ChunkedGzipOutputStream.this.buff[toWrite - 1] = 10;
         ChunkedGzipOutputStream.this.out.write(ChunkedGzipOutputStream.this.buff, 0, toWrite);
         ChunkedGzipOutputStream.this.bytesWritten += (long)toWrite;
      }

      protected void writeChunkHeader(byte[] b, int i) {
         int pos = 4;

         do {
            --pos;
            b[pos] = this.DIGITS[i & 15];
            i >>>= 4;
         } while(i != 0);

         for(int j = 0; j < pos; ++j) {
            b[j] = 48;
         }

         b[4] = 13;
         b[5] = 10;
      }
   }
}
