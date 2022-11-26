package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import java.util.Arrays;
import weblogic.servlet.http2.HTTP2Exception;

public class GoAwayFrame extends Frame {
   private int lastStreamId;
   private int error;
   private byte[] debugData;

   public GoAwayFrame(int payloadSize, int flags, int streamId) {
      super(FrameType.GOAWAY, payloadSize, flags, streamId);
      this.streamRequired = false;
   }

   public GoAwayFrame(int flags, int streamId, int lastStreamId, int error, byte[] debugData) {
      this((debugData == null ? 0 : debugData.length) + 8, flags, streamId);
      this.lastStreamId = lastStreamId;
      this.error = error;
      this.debugData = debugData;
   }

   public int getLastStreamId() {
      return this.lastStreamId;
   }

   public void setLastStreamId(int lastStreamId) {
      this.lastStreamId = lastStreamId;
   }

   public int getError() {
      return this.error;
   }

   public void setError(int error) {
      this.error = error;
   }

   public byte[] getDebugData() {
      return this.debugData;
   }

   public void setDebugData(byte[] debugData) {
      this.debugData = debugData;
   }

   public boolean parseBody(ByteBuffer buffer) throws HTTP2Exception {
      this.lastStreamId = buffer.getInt() & Integer.MAX_VALUE;
      this.error = buffer.getInt();
      if (this.payloadSize > 8) {
         this.debugData = new byte[this.payloadSize - 8];
         buffer.get(this.debugData);
      }

      return true;
   }

   public byte[] toBytes() {
      if (this.lastStreamId < 0) {
         throw new IllegalArgumentException("Invalid last stream id: " + this.lastStreamId);
      } else {
         ByteBuffer buffer = null;
         if (this.payloadSize > this.maxFrameSize) {
            buffer = ByteBuffer.allocate(this.maxFrameSize + 9);
            this.debugData = Arrays.copyOfRange(this.debugData, 0, this.maxFrameSize - 8);
            generateHeader(buffer, FrameType.GOAWAY, this.maxFrameSize, 0, 0);
         } else {
            buffer = ByteBuffer.allocate(this.payloadSize + 9);
            generateHeader(buffer, FrameType.GOAWAY, this.payloadSize, 0, 0);
         }

         buffer.putInt(this.lastStreamId);
         buffer.putInt(this.error);
         if (this.debugData != null) {
            buffer.put(this.debugData);
         }

         return buffer.array();
      }
   }
}
