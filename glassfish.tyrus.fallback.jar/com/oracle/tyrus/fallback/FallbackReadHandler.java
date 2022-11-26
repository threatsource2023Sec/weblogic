package com.oracle.tyrus.fallback;

import com.oracle.tyrus.fallback.spi.ReadHandler;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

public class FallbackReadHandler implements ReadHandler {
   private static final Logger LOGGER = Logger.getLogger(FallbackReadHandler.class.getName());
   private final org.glassfish.tyrus.spi.ReadHandler rh;
   private ByteBuffer buf;

   FallbackReadHandler(org.glassfish.tyrus.spi.ReadHandler rh) {
      this.rh = rh;
   }

   public void handle(byte[] data, int offset, int length) {
      try {
         this.fillBuf(data, offset, length);
         this.rh.handle(this.buf);
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }
   }

   private void fillBuf(byte[] data, int offset, int len) throws IOException {
      if (len == 0) {
         throw new RuntimeException("No data available.");
      } else {
         if (this.buf == null) {
            LOGGER.finest("No Buffer. Allocating new one");
            this.buf = ByteBuffer.wrap(data, offset, len);
            this.buf.limit(len);
         } else {
            int limit = this.buf.limit();
            int capacity = this.buf.capacity();
            int remaining = this.buf.remaining();
            if (capacity - limit >= len) {
               LOGGER.finest("Remaining data need not be moved. New data is just appended");
               this.buf.mark();
               this.buf.position(limit);
               this.buf.limit(capacity);
               this.buf.put(data, offset, len);
               this.buf.limit(limit + len);
               this.buf.reset();
            } else if (remaining + len < capacity) {
               LOGGER.finest("Remaining data is moved to left. Then new data is appended");
               this.buf.compact();
               this.buf.put(data, offset, len);
               this.buf.flip();
            } else {
               LOGGER.finest("Remaining data + new > capacity. So allocate new one");
               byte[] array = new byte[remaining + len];
               this.buf.get(array, 0, remaining);
               System.arraycopy(data, offset, array, remaining, len);
               this.buf = ByteBuffer.wrap(array);
               this.buf.limit(remaining + len);
            }
         }

      }
   }
}
