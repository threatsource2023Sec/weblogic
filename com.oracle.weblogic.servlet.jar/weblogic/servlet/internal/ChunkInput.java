package weblogic.servlet.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import weblogic.utils.Hex;

public final class ChunkInput {
   private static void enforceEOL(InputStream in) throws IOException {
      if (in.read() != 13) {
         throw new IOException("chunk data not ended with CR");
      } else if (in.read() != 10) {
         throw new IOException("chunk data not ended with LF");
      }
   }

   public static int readCTE(byte[] buf, InputStream in) throws IOException {
      int chunkSize = false;
      int offset = 0;
      int total = 0;

      int chunkSize;
      while((chunkSize = readChunkSize(in)) > 0) {
         int nread;
         do {
            nread = in.read(buf, offset, chunkSize);
            offset += nread;
            if (offset > buf.length) {
               throw new IOException("Max buffer exceeded");
            }

            chunkSize -= nread;
         } while(chunkSize > 0);

         total += nread;
         enforceEOL(in);
      }

      return total;
   }

   public static int readCTE(OutputStream out, InputStream in) throws IOException {
      return readCTE(out, in, false);
   }

   public static int readCTE(OutputStream out, InputStream in, boolean flushChunk) throws IOException {
      int total = 0;
      boolean needToWrite = true;

      int chunkSize;
      while((chunkSize = readChunkSize(in)) > 0) {
         if (readAndWriteChunk(chunkSize, out, in, needToWrite) == -1) {
            needToWrite = false;
         }

         total += chunkSize + 2;
         if (flushChunk) {
            try {
               out.flush();
            } catch (IOException var7) {
               needToWrite = false;
               throw new WriteClientIOException("Error in writing to client");
            }
         }
      }

      return total;
   }

   public static int readChunkSize(InputStream in) throws IOException {
      StringBuilder sb = new StringBuilder();
      int chunkSize = false;

      int b;
      while((b = in.read()) != -1 && (b != 13 || (b = in.read()) != 10)) {
         char c = (char)b;
         if (Hex.isHexChar(c)) {
            sb.append(c);
         }
      }

      int chunkSize = Integer.parseInt(sb.toString(), 16);
      if (chunkSize == 0) {
         enforceEOL(in);
      }

      return chunkSize;
   }

   /** @deprecated */
   @Deprecated
   public static int readAndWriteChunk(int size, OutputStream out, InputStream in) throws IOException {
      return readAndWriteChunk(size, out, in, true);
   }

   public static int readAndWriteChunk(int size, OutputStream out, InputStream in, boolean needToWrite) throws IOException {
      int len = size;
      int nread = false;

      int nread;
      for(byte[] buf = new byte[Math.min(size, 8192)]; len > 0; len -= nread) {
         nread = in.read(buf, 0, Math.min(buf.length, len));
         if (nread == -1) {
            throw new IOException("unexpected EOF, expected to read: " + size + " actually read: " + nread);
         }

         if (needToWrite) {
            try {
               out.write(buf, 0, nread);
            } catch (IOException var8) {
               needToWrite = false;
               throw new WriteClientIOException("Error in writing to client");
            }
         }
      }

      enforceEOL(in);
      if (!needToWrite) {
         return -1;
      } else {
         return size;
      }
   }
}
