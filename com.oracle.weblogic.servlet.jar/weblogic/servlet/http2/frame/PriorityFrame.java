package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;
import weblogic.servlet.http2.HTTP2Exception;

public class PriorityFrame extends Frame {
   public static final int PRIORITY_LENGTH = 5;
   private PriorityItems priorityItems;

   public PriorityFrame(int payloadSize, int flags, int streamId) {
      super(FrameType.PRIORITY, payloadSize, flags, streamId);
      this.streamRequired = true;
      this.priorityItems = new PriorityItems();
   }

   public PriorityFrame(int flags, int streamId, boolean exclusive, int parentStreamId, int weight) {
      this(5, flags, streamId);
      this.priorityItems.setExclusive(exclusive);
      this.priorityItems.setParentStreamId(parentStreamId);
      this.priorityItems.setWeight(weight);
   }

   public PriorityFrame(boolean exclusive, int parentStreamId, int weight) {
      this(0, 0, exclusive, parentStreamId, weight);
   }

   public PriorityItems getPriorityItems() {
      return this.priorityItems;
   }

   public boolean parseBody(ByteBuffer buffer) throws HTTP2Exception {
      this.priorityItems.setExclusive((buffer.get(buffer.position()) & 128) == 128);
      this.priorityItems.setParentStreamId(buffer.getInt() & Integer.MAX_VALUE);
      this.priorityItems.setWeight((buffer.get() & 255) + 1);
      return true;
   }

   public byte[] toBytes() {
      if (this.streamId < 0) {
         throw new IllegalArgumentException("Invalid stream id: " + this.streamId);
      } else {
         int parentStreamId = this.priorityItems.getParentStreamId();
         if (parentStreamId < 0) {
            throw new IllegalArgumentException("Invalid parent stream id: " + parentStreamId);
         } else if (parentStreamId == this.streamId) {
            throw new IllegalArgumentException("Stream " + this.streamId + " cannot depend on stream " + parentStreamId);
         } else {
            int weight = this.priorityItems.getWeight();
            if (weight >= 1 && weight <= 256) {
               ByteBuffer buffer = ByteBuffer.allocate(14);
               generateHeader(buffer, FrameType.PRIORITY, 5, 0, this.streamId);
               this.priorityItems.generateBody(buffer);
               return buffer.array();
            } else {
               throw new IllegalArgumentException("Invalid weight: " + weight);
            }
         }
      }
   }
}
