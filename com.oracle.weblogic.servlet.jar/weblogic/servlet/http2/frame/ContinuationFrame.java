package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import java.util.List;
import weblogic.servlet.http2.Flags;
import weblogic.servlet.http2.HTTP2Exception;

public class ContinuationFrame extends Frame {
   private List headers;
   private ByteBuffer data;

   public ContinuationFrame(int payloadSize, int flags, int streamId) {
      super(FrameType.CONTINUATION, payloadSize, flags, streamId);
      this.streamRequired = true;
   }

   public boolean isEndOfHeader() {
      return Flags.isEndOfHeaders(this.flags);
   }

   public boolean parseBody(ByteBuffer buffer) throws HTTP2Exception {
      this.data = buffer;
      return true;
   }

   public ByteBuffer getData() {
      return this.data.duplicate();
   }

   public byte[] toBytes() {
      return null;
   }
}
