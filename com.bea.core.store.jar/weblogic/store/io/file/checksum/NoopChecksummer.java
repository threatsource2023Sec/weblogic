package weblogic.store.io.file.checksum;

import java.nio.ByteBuffer;

public class NoopChecksummer implements Checksummer {
   public void update(int b) {
   }

   public void update(byte[] b, int off, int len) {
   }

   public long getValue() {
      return 1L;
   }

   public void reset() {
   }

   public void update(ByteBuffer buffer, int offset, int len) {
      if (offset < 0 || len < 0 || len + offset > buffer.capacity()) {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   public long calculateChecksum(ByteBuffer buffer) {
      return 1L;
   }
}
