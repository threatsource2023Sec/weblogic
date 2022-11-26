package weblogic.xml.util;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import weblogic.utils.io.ChunkedInputStream;
import weblogic.utils.io.ChunkedOutputStream;

public class CachedInputStream extends FilterInputStream {
   boolean isUserStream = true;
   boolean streamclose = false;
   ChunkedOutputStream output = new ChunkedOutputStream();

   public CachedInputStream(InputStream in) throws IOException {
      super(in);
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (this.isUserStream) {
         int i = super.read(b, off, len);
         if (i != -1) {
            this.output.write(b, off, i);
         }

         return i;
      } else {
         return super.read(b, off, len);
      }
   }

   public int read() throws IOException {
      if (this.isUserStream) {
         int i = super.read();
         if (i != -1) {
            this.output.write(i);
         }

         return i;
      } else {
         return super.read();
      }
   }

   public void reset() throws IOException {
      if (this.isUserStream) {
         this.readFullstream();
         this.output.flush();
         this.output.close();
         this.in = new ChunkedInputStream(this.output.getChunks(), 0);
         this.in.mark(0);
         this.output = null;
         this.isUserStream = false;
      } else {
         this.in.reset();
         this.in.mark(0);
      }

      this.streamclose = false;
   }

   private void readFullstream() throws IOException {
      try {
         if (this.streamclose) {
            return;
         }

         byte[] buf = new byte[512];

         while(true) {
            if (this.read(buf) != -1) {
               continue;
            }
         }
      } catch (EOFException var2) {
      }

   }

   public void close() throws IOException {
      if (this.isUserStream) {
         this.readFullstream();
         this.in.close();
      }

      this.streamclose = true;
   }

   public void closeAll() throws IOException {
      if (this.isUserStream) {
         this.close();
      }

      this.reset();
      this.in.close();
   }
}
