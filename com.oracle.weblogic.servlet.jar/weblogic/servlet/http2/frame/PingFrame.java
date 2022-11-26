package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import java.util.Objects;
import weblogic.servlet.http2.Flags;
import weblogic.servlet.http2.HTTP2Exception;

public class PingFrame extends Frame {
   public static final int PING_LENGTH = 8;
   private byte[] payload;
   private boolean isAck;

   public PingFrame(int payloadSize, int flags, int streamId) {
      super(FrameType.PING, payloadSize, flags, streamId);
      this.payload = new byte[8];
      this.streamRequired = false;
   }

   public PingFrame(int flags, int streamId, byte[] payload, boolean isAck) {
      this(8, flags, streamId);
      this.payload = payload;
      this.isAck = isAck;
   }

   public byte[] getPayload() {
      return this.payload;
   }

   public long getPayloadAsLong() {
      return toLong(this.payload);
   }

   public void setPayload(byte[] payload) {
      this.payload = (byte[])Objects.requireNonNull(payload);
   }

   public void setPayload(long payload) {
      this.payload = (byte[])Objects.requireNonNull(toBytes(payload));
   }

   public boolean isAck() {
      return this.isAck;
   }

   public void setAck(boolean isAck) {
      this.isAck = isAck;
   }

   public boolean parseBody(ByteBuffer buffer) throws HTTP2Exception {
      this.isAck = Flags.isAck(this.flags);
      buffer.get(this.payload);
      return true;
   }

   public byte[] toBytes() {
      int flags = 0;
      if (this.isAck) {
         flags |= 1;
      }

      ByteBuffer buffer = ByteBuffer.allocate(17);
      generateHeader(buffer, FrameType.PING, 8, flags, 0);
      buffer.put(this.payload);
      return buffer.array();
   }

   private static byte[] toBytes(long value) {
      byte[] result = new byte[8];

      for(int i = result.length - 1; i >= 0; --i) {
         result[i] = (byte)((int)(value & 255L));
         value >>= 8;
      }

      return result;
   }

   private static long toLong(byte[] payload) {
      long result = 0L;

      for(int i = 0; i < 8; ++i) {
         result <<= 8;
         result |= (long)(payload[i] & 255);
      }

      return result;
   }
}
