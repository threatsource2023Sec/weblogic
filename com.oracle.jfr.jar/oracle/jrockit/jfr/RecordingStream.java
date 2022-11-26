package oracle.jrockit.jfr;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.util.zip.GZIPOutputStream;

class RecordingStream {
   private final ReadableByteChannel input;
   private final ByteBuffer buffer = ByteBuffer.allocate(4096);
   private ByteArrayOutputStream byteOut = new ByteArrayOutputStream(32768);
   private GZIPOutputStream gzOut;
   private static final int MIN_SEND_SIZE = 4096;

   public RecordingStream(ReadableByteChannel input) throws IOException {
      this.gzOut = new GZIPOutputStream(this.byteOut);
      this.input = input;
   }

   public synchronized byte[] read() throws IOException {
      if (!this.isOpen()) {
         return null;
      } else {
         while(true) {
            int n = this.input.read(this.buffer);
            if (n < 0) {
               this.gzOut.flush();
               this.gzOut.close();
               break;
            }

            try {
               this.gzOut.write(this.buffer.array(), 0, n);
               this.gzOut.flush();
               if (this.byteOut.size() >= 4096) {
                  break;
               }
            } finally {
               this.buffer.clear();
            }
         }

         byte[] var17;
         label170: {
            try {
               if (this.byteOut.size() <= 0) {
                  break label170;
               }

               var17 = this.byteOut.toByteArray();
            } finally {
               this.byteOut.reset();
            }

            return var17;
         }

         this.gzOut.flush();
         this.gzOut.close();

         try {
            var17 = this.byteOut.toByteArray();
         } finally {
            this.close();
         }

         return var17;
      }
   }

   public synchronized boolean isOpen() {
      return this.input.isOpen();
   }

   public synchronized void close() throws IOException {
      this.input.close();
      if (this.gzOut != null) {
         this.gzOut.close();
         this.gzOut = null;
      }

      if (this.byteOut != null) {
         this.byteOut.close();
         this.byteOut = null;
      }

   }
}
