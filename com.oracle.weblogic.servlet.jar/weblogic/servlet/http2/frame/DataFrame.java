package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import weblogic.servlet.http2.ConnectionException;
import weblogic.servlet.http2.Flags;
import weblogic.servlet.http2.HTTP2Exception;
import weblogic.servlet.http2.MessageManager;

public class DataFrame extends Frame {
   private ByteBuffer data;
   private boolean endStream;
   private int padding;

   public DataFrame(int payloadSize, int flags, int streamId) {
      super(FrameType.DATA, payloadSize, flags, streamId);
      this.streamRequired = true;
   }

   public DataFrame(int payloadSize, int streamId, ByteBuffer data, boolean endStream, int padding) {
      this(payloadSize, 0, streamId);
      this.data = data;
      this.endStream = endStream;
      this.padding = padding;
   }

   public ByteBuffer getData() {
      return this.data;
   }

   public void setData(ByteBuffer data) {
      this.data = data;
   }

   public boolean isEndStream() {
      return this.endStream;
   }

   public void setEndStream(boolean endStream) {
      this.endStream = endStream;
   }

   public int paddingLength() {
      return this.padding;
   }

   public void setPadding(int padding) {
      this.padding = padding;
   }

   public int remaining() {
      return this.data.remaining();
   }

   public boolean parseBody(ByteBuffer buffer) throws HTTP2Exception {
      this.endStream = Flags.isEndStream(this.flags);
      int dataLength;
      if (Flags.hasPadding(this.flags)) {
         this.padding = buffer.get() & 255;
         if (this.padding >= this.payloadSize) {
            throw new ConnectionException(MessageManager.getMessage("http2Parser.processFrame.tooMuchPadding", this.streamId, this.padding, this.payloadSize), 1);
         }

         dataLength = this.payloadSize - (this.padding + 1);
      } else {
         dataLength = this.payloadSize;
      }

      int size = Math.min(buffer.remaining(), dataLength);
      int position = buffer.position();
      int limit = buffer.limit();
      buffer.limit(position + size);
      this.data = buffer.slice();
      buffer.limit(limit);
      buffer.position(position + size);
      return true;
   }

   public byte[] toBytes() {
      return this.generate(this.maxFrameSize);
   }

   private byte[] generate(int maxLength) {
      ByteBuffer buffer = null;
      if (this.streamId < 0) {
         throw new IllegalArgumentException("Invalid stream id: " + this.streamId);
      } else if (this.payloadSize <= maxLength && this.payloadSize <= this.maxFrameSize) {
         buffer = ByteBuffer.allocate(this.payloadSize + 9);
         this.generateSingleFrame(this.streamId, buffer, this.data, this.endStream);
         return buffer.array();
      } else {
         int length = Math.min(maxLength, this.payloadSize);
         int frames = length / this.maxFrameSize;
         if (frames * this.maxFrameSize != length) {
            ++frames;
         }

         buffer = ByteBuffer.allocate(this.payloadSize + frames * 9);
         int begin = this.data.position();
         int end = this.data.limit();

         for(int i = 1; i <= frames; ++i) {
            int limit = begin + Math.min(this.maxFrameSize * i, length);
            this.data.limit(limit);
            ByteBuffer slice = this.data.slice();
            this.data.position(limit);
            this.generateSingleFrame(this.streamId, buffer, slice, i == frames && this.endStream && limit == end);
         }

         this.data.limit(end);
         return buffer.array();
      }
   }

   private void generateSingleFrame(int streamId, ByteBuffer target, ByteBuffer data, boolean lastStream) {
      int length = data.remaining();
      int flags = 0;
      if (lastStream) {
         flags |= 1;
      }

      generateHeader(target, FrameType.DATA, length, flags, streamId);
      target.put(data);
   }
}
