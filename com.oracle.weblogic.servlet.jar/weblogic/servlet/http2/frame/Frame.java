package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import weblogic.servlet.http2.HTTP2Connection;
import weblogic.servlet.http2.HTTP2Exception;

public abstract class Frame {
   public static final int HEADER_LENGTH = 9;
   public static final int DEFAULT_MAX_LENGTH = 16384;
   public static final int MAX_MAX_LENGTH = 16777215;
   protected final int payloadSize;
   protected final FrameType type;
   protected final int flags;
   protected final int streamId;
   protected boolean streamRequired;
   protected int maxFrameSize;

   public Frame(FrameType type, int payloadSize, int flags, int streamId) {
      this.type = type;
      this.payloadSize = payloadSize;
      this.flags = flags;
      this.streamId = streamId;
   }

   public boolean isStreamRequired() {
      return this.streamRequired;
   }

   public FrameType getType() {
      return this.type;
   }

   public int getPayloadSize() {
      return this.payloadSize;
   }

   public int getFlags() {
      return this.flags;
   }

   public int getStreamId() {
      return this.streamId;
   }

   public void setRemoteMaxFrameSize(int maxFrameSize) {
      this.maxFrameSize = maxFrameSize;
   }

   public abstract boolean parseBody(ByteBuffer var1) throws HTTP2Exception;

   public void send(HTTP2Connection conn) {
      conn.sendBytes(this.toBytes());
   }

   public abstract byte[] toBytes();

   protected static void generateHeader(ByteBuffer buffer, FrameType type, int payloadSize, int flags, int streamId) {
      byte[] header = buffer.array();
      int off = buffer.position();
      setFrameLength(header, off, payloadSize);
      header[off + 3] = (byte)type.getType();
      header[off + 4] = (byte)flags;
      setStreamId(header, 5, streamId);
      buffer.position(off + 9);
   }

   private static void setFrameLength(byte[] target, int firstByte, int value) {
      target[firstByte] = (byte)((value & 16711680) >> 16);
      target[firstByte + 1] = (byte)((value & '\uff00') >> 8);
      target[firstByte + 2] = (byte)(value & 255);
   }

   private static void setStreamId(byte[] target, int firstByte, int value) {
      target[firstByte] = (byte)((value & 2130706432) >> 24);
      target[firstByte + 1] = (byte)((value & 16711680) >> 16);
      target[firstByte + 2] = (byte)((value & '\uff00') >> 8);
      target[firstByte + 3] = (byte)(value & 255);
   }

   public static void generateAck(ByteBuffer target, FrameType type, int streamId, byte[] payload) {
      generateHeader(target, type, payload == null ? 0 : payload.length, 1, streamId);
      if (payload != null) {
         int position = target.position();
         target.put(payload);
         target.position(payload.length + position);
      }

   }

   public String toString() {
      return String.format("Frame object: " + super.toString() + " StreamId: " + this.streamId + " flags: " + this.flags + " payload size: " + this.payloadSize + " type: " + this.type);
   }
}
