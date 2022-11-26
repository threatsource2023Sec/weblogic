package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import weblogic.servlet.http2.HTTP2Exception;

public class PushPromiseFrame extends Frame {
   private int padding;
   private int promisedStreamId;
   private ByteBuffer data;

   public PushPromiseFrame(int streamId, int promisedStreamId, ByteBuffer data) {
      super(FrameType.PUSH_PROMISE, data != null ? data.remaining() + 4 : 4, 0, streamId);
      this.streamRequired = true;
      this.promisedStreamId = promisedStreamId;
      this.data = data;
   }

   public int paddingLength() {
      return this.padding;
   }

   public void setPadding(int padding) {
      this.padding = padding;
   }

   public int getPromisedStreamId() {
      return this.promisedStreamId;
   }

   public void setPromisedStreamId(int promisedStreamId) {
      this.promisedStreamId = promisedStreamId;
   }

   public ByteBuffer getData() {
      return this.data.duplicate();
   }

   public boolean parseBody(ByteBuffer buffer) throws HTTP2Exception {
      return false;
   }

   public byte[] toBytes() {
      if (this.streamId < 0) {
         throw new IllegalArgumentException("Invalid stream id: " + this.streamId);
      } else if (this.promisedStreamId < 0) {
         throw new IllegalArgumentException("Invalid promised stream id: " + this.promisedStreamId);
      } else {
         int promisedStreamIdSpace = 4;
         int flags = 0;
         ByteBuffer buffer = null;
         if (this.data.remaining() > this.maxFrameSize) {
            int continuationframes;
            if ((this.data.remaining() + promisedStreamIdSpace) % this.maxFrameSize > 0) {
               continuationframes = (this.data.remaining() + promisedStreamIdSpace) / this.maxFrameSize;
            } else {
               continuationframes = (this.data.remaining() + promisedStreamIdSpace) / this.maxFrameSize - 1;
               continuationframes = continuationframes < 0 ? 0 : continuationframes;
            }

            int oldLimit = this.data.limit();
            int headBlockLength = this.maxFrameSize;
            buffer = ByteBuffer.allocate(9 + this.data.remaining() + promisedStreamIdSpace + continuationframes * 9);
            this.data.limit(headBlockLength - promisedStreamIdSpace);
            this.generateSinglePPFrame(headBlockLength + 4, flags, this.streamId, this.data.slice(), buffer);
            int position = headBlockLength - promisedStreamIdSpace;

            for(int limit = position + headBlockLength; limit < oldLimit; limit += headBlockLength) {
               this.data.position(position).limit(limit);
               generateHeader(buffer, FrameType.CONTINUATION, headBlockLength, 0, this.streamId);
               buffer.put(this.data.slice());
               position += headBlockLength;
            }

            this.data.position(position).limit(oldLimit);
            generateHeader(buffer, FrameType.CONTINUATION, this.data.remaining(), 4, this.streamId);
            buffer.put(this.data);
         } else {
            buffer = ByteBuffer.allocate(9 + this.payloadSize);
            flags |= 4;
            this.generateSinglePPFrame(this.payloadSize, flags, this.streamId, this.data, buffer);
         }

         return buffer.array();
      }
   }

   private void generateSinglePPFrame(int payloadSize, int flags, int streamId, ByteBuffer hpacked, ByteBuffer target) {
      generateHeader(target, FrameType.PUSH_PROMISE, payloadSize, flags, streamId);
      target.put((byte)((this.promisedStreamId & 2130706432) >> 24));
      target.put((byte)((this.promisedStreamId & 16711680) >> 16));
      target.put((byte)((this.promisedStreamId & '\uff00') >> 8));
      target.put((byte)(this.promisedStreamId & 255));
      target.put(hpacked);
   }

   public String toString() {
      return String.format("%s#%d/#%d", super.toString(), this.streamId, this.promisedStreamId);
   }
}
