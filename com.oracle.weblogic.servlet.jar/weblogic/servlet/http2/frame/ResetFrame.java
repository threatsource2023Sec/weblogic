package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import weblogic.servlet.http2.HTTP2Exception;

public class ResetFrame extends Frame {
   public static final int RESET_LENGTH = 4;
   private int error;

   public ResetFrame(int payloadSize, int flags, int streamId) {
      super(FrameType.RST_STREAM, payloadSize, flags, streamId);
      this.streamRequired = true;
   }

   public ResetFrame(int flags, int streamId) {
      this(4, flags, streamId);
   }

   public int getError() {
      return this.error;
   }

   public void setError(int error) {
      this.error = error;
   }

   public boolean parseBody(ByteBuffer buffer) throws HTTP2Exception {
      this.error = buffer.getInt();
      return true;
   }

   public byte[] toBytes() {
      ByteBuffer buffer = ByteBuffer.allocate(13);
      generateHeader(buffer, FrameType.RST_STREAM, 4, 0, this.streamId);
      buffer.putInt(this.error);
      return buffer.array();
   }
}
