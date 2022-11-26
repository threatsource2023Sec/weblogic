package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import weblogic.servlet.http2.HTTP2Exception;

public class WindowUpdateFrame extends Frame {
   public static final int WINDOW_UPDATE_LENGTH = 4;
   private int updateSize;

   public WindowUpdateFrame(int streamId) {
      super(FrameType.WINDOW_UPDATE, 4, 0, streamId);
      this.streamRequired = false;
   }

   public WindowUpdateFrame(int streamId, int updateSize) {
      this(streamId);
      this.updateSize = updateSize;
   }

   public int getUpdateSize() {
      return this.updateSize;
   }

   public boolean parseBody(ByteBuffer buffer) throws HTTP2Exception {
      this.updateSize = buffer.getInt() & Integer.MAX_VALUE;
      return true;
   }

   public byte[] toBytes() {
      ByteBuffer buffer = ByteBuffer.allocate(13);
      generateHeader(buffer, FrameType.WINDOW_UPDATE, 4, 0, this.streamId);
      buffer.putInt(this.updateSize);
      return buffer.array();
   }

   public String toString() {
      return String.format("%s#%d,delta=%d", super.toString(), this.streamId, this.updateSize);
   }
}
