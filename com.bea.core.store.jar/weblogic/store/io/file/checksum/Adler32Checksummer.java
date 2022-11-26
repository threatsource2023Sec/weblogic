package weblogic.store.io.file.checksum;

import java.nio.ByteBuffer;
import java.util.zip.Adler32;

public class Adler32Checksummer extends Adler32 implements Checksummer {
   private static final int MAX_ADLER_BYTES = 65536;
   private ByteBuffer adlerBuf;

   public Adler32Checksummer() {
      this.adlerBuf = ByteBuffer.allocate(65536);
   }

   public Adler32Checksummer(int bufferSize) {
      this.adlerBuf = ByteBuffer.allocate(bufferSize);
   }

   public void update(ByteBuffer buffer, int offset, int len) {
      assert offset >= 0 && len >= 0 && offset + len <= buffer.limit();

      if (buffer.hasArray()) {
         this.update(buffer.array(), buffer.arrayOffset() + offset, len);
      } else {
         int origPos = buffer.position();
         int origLimit = buffer.limit();

         try {
            buffer.position(offset);

            int chunkSize;
            for(int numBytesProcessed = 0; numBytesProcessed < len; numBytesProcessed += chunkSize) {
               chunkSize = Math.min(len - numBytesProcessed, this.adlerBuf.remaining());
               buffer.limit(buffer.position() + chunkSize);
               this.adlerBuf.put(buffer);
               if (!this.adlerBuf.hasRemaining()) {
                  this.adlerBuf.flip();
                  this.update(this.adlerBuf.array(), 0, this.adlerBuf.limit());
                  this.adlerBuf.clear();
               }
            }

            if (this.adlerBuf.position() > 0) {
               this.adlerBuf.flip();
               this.update(this.adlerBuf.array(), 0, this.adlerBuf.limit());
            }
         } finally {
            buffer.limit(origLimit);
            buffer.position(origPos);
            this.adlerBuf.clear();
         }

      }
   }

   public long calculateChecksum(ByteBuffer buffer) {
      this.reset();
      this.update(buffer, buffer.position(), buffer.remaining());
      return this.getValue();
   }
}
