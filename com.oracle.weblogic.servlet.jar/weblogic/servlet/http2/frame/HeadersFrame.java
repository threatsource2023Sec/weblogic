package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import weblogic.servlet.http2.ConnectionException;
import weblogic.servlet.http2.Flags;
import weblogic.servlet.http2.HTTP2Exception;
import weblogic.servlet.http2.MessageManager;
import weblogic.servlet.http2.StreamException;

public class HeadersFrame extends Frame {
   private ByteBuffer data;
   private int paddingLength;
   private int dataLength;
   private PriorityItems priority;
   private boolean endStream;
   private boolean endOfHeader;

   public HeadersFrame(int payloadSize, int flags, int streamId) {
      super(FrameType.HEADERS, payloadSize, flags, streamId);
      this.streamRequired = true;
      this.endStream = Flags.isEndStream(flags);
      this.endOfHeader = Flags.isEndOfHeaders(flags);
   }

   public HeadersFrame(int payloadSize, int flags, int streamId, int paddingLength, PriorityItems priority, boolean endStream) {
      this(payloadSize, flags, streamId);
      this.paddingLength = paddingLength;
      this.priority = priority;
      this.endStream = endStream;
   }

   public HeadersFrame(int streamId, int paddingLength, PriorityItems priority, boolean endStream, ByteBuffer data) {
      this(data != null ? data.remaining() : 0, 0, streamId, paddingLength, priority, endStream);
      this.data = data;
   }

   public int paddingLength() {
      return this.paddingLength;
   }

   public void setPadding(int padding) {
      this.paddingLength = padding;
   }

   public PriorityItems getPriority() {
      return this.priority;
   }

   public void setPriority(PriorityItems priority) {
      this.priority = priority;
   }

   public ByteBuffer getData() {
      return this.data.duplicate();
   }

   public boolean isEndOfHeader() {
      return this.endOfHeader;
   }

   public boolean isEndStream() {
      return this.endStream;
   }

   public void setEndStream(boolean endStream) {
      this.endStream = endStream;
   }

   public boolean parseBody(ByteBuffer buffer) throws HTTP2Exception {
      int length = this.payloadSize;
      if (Flags.hasPadding(this.flags)) {
         this.paddingLength = buffer.get() & 255;
         if (this.paddingLength >= length) {
            throw new ConnectionException(MessageManager.getMessage("http2Parser.processFrame.tooMuchPadding", this.streamId, this.paddingLength, this.payloadSize), 1);
         }

         length = length - this.paddingLength - 1;
      }

      if (Flags.hasPriority(this.flags)) {
         boolean exclusive = (buffer.get(buffer.position()) & 128) == 128;
         int parentStreamId = buffer.getInt();
         parentStreamId &= Integer.MAX_VALUE;
         if (this.streamId == parentStreamId) {
            throw new StreamException(MessageManager.getMessage("http2Parser.processFramePriority.invalidParent", this.streamId), 1, this.streamId);
         }

         length -= 4;
         if (length < 1) {
            throw new ConnectionException(MessageManager.getMessage("http2Parser.processFrame.invalid_headers_frame", this.streamId, length), 1);
         }

         int weight = (buffer.get() & 255) + 1;
         --length;
         this.priority = new PriorityItems(exclusive, parentStreamId, weight);
      }

      this.dataLength = length;
      this.data = buffer;
      this.data.limit(this.data.position() + this.dataLength);
      return true;
   }

   public byte[] toBytes() {
      int flags = 0;
      if (this.priority != null) {
         flags = 32;
      }

      ByteBuffer buffer = null;
      if (this.data.remaining() > this.maxFrameSize) {
         int continuationframes;
         if (this.data.remaining() % this.maxFrameSize > 0) {
            continuationframes = this.data.remaining() / this.maxFrameSize;
         } else {
            continuationframes = this.data.remaining() / this.maxFrameSize - 1;
            continuationframes = continuationframes < 0 ? 0 : continuationframes;
         }

         int oldLimit = this.data.limit();
         int headBlockLength = this.maxFrameSize;
         buffer = ByteBuffer.allocate(9 + this.data.remaining() + (this.priority == null ? 0 : 5) + continuationframes * 9);
         this.data.limit(headBlockLength);
         this.generateSingleHeadersFrame(headBlockLength, flags, this.streamId, this.priority, this.endStream, this.data.slice(), buffer);
         int position = headBlockLength;

         for(int limit = headBlockLength + headBlockLength; limit < oldLimit; limit += headBlockLength) {
            this.data.position(position).limit(limit);
            generateHeader(buffer, FrameType.CONTINUATION, headBlockLength, 0, this.streamId);
            buffer.put(this.data.slice());
            position += headBlockLength;
         }

         this.data.position(position).limit(oldLimit);
         generateHeader(buffer, FrameType.CONTINUATION, this.data.remaining(), 4, this.streamId);
         buffer.put(this.data);
      } else {
         buffer = ByteBuffer.allocate(9 + this.data.remaining() + (this.priority == null ? 0 : 5));
         this.generateSingleHeadersFrame(this.data.remaining(), flags | 4, this.streamId, this.priority, this.endStream, this.data, buffer);
      }

      return buffer.array();
   }

   private void generateSingleHeadersFrame(int payloadSize, int flags, int streamId, PriorityItems priority, Boolean endStream, ByteBuffer hpacked, ByteBuffer target) {
      if (endStream) {
         flags |= 1;
      }

      if (priority != null) {
         payloadSize += 5;
      }

      generateHeader(target, FrameType.HEADERS, payloadSize, flags, streamId);
      if (priority != null) {
         priority.generateBody(target);
      }

      target.put(hpacked);
   }
}
