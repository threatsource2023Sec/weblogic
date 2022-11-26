package oracle.jrockit.jfr;

import java.nio.ByteBuffer;

final class BufferHolder {
   private ByteBuffer buffer;

   public BufferHolder(ByteBuffer buffer) {
      this.buffer = buffer;
   }

   public ByteBuffer getBuffer() {
      return this.buffer;
   }

   public void setBuffer(ByteBuffer buffer) {
      this.buffer = buffer;
   }
}
