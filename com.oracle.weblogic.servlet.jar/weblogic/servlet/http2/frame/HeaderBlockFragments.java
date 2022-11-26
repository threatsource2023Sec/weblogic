package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;

public class HeaderBlockFragments {
   private PriorityFrame priorityFrame;
   private boolean endStream;
   private int streamId;
   private ByteBuffer headerBlock;

   public PriorityFrame getPriorityFrame() {
      return this.priorityFrame;
   }

   public void setPriorityFrame(PriorityFrame priorityFrame) {
      this.priorityFrame = priorityFrame;
   }

   public boolean isEndStream() {
      return this.endStream;
   }

   public void setEndStream(boolean endStream) {
      this.endStream = endStream;
   }

   public ByteBuffer complete() {
      ByteBuffer result = this.headerBlock;
      this.headerBlock = null;
      result.flip();
      return result;
   }

   public int getStreamId() {
      return this.streamId;
   }

   public void setStreamId(int streamId) {
      this.streamId = streamId;
   }

   public void storeFragment(ByteBuffer fragment, int length, boolean isEndOfHeaders) {
      int limit;
      if (this.headerBlock == null) {
         limit = isEndOfHeaders ? length : length * 2;
         this.headerBlock = ByteBuffer.allocate(limit);
      }

      if (this.headerBlock.remaining() < length) {
         limit = isEndOfHeaders ? length : length * 2;
         ByteBuffer newBuffer = ByteBuffer.allocate(this.headerBlock.position() + limit);
         this.headerBlock.flip();
         newBuffer.put(this.headerBlock);
         this.headerBlock = newBuffer;
      }

      limit = fragment.limit();
      fragment.limit(fragment.position() + length);
      this.headerBlock.put(fragment);
      fragment.limit(limit);
   }
}
