package weblogic.servlet.http2.frame;

import java.nio.ByteBuffer;

public class PriorityItems {
   private boolean exclusive;
   private int parentStreamId;
   private int weight;

   public PriorityItems() {
   }

   public PriorityItems(boolean exclusive, int parentStreamId, int weight) {
      this.exclusive = exclusive;
      this.parentStreamId = parentStreamId;
      this.weight = weight;
   }

   public boolean isExclusive() {
      return this.exclusive;
   }

   public void setExclusive(boolean exclusive) {
      this.exclusive = exclusive;
   }

   public int getParentStreamId() {
      return this.parentStreamId;
   }

   public void setParentStreamId(int parentStreamId) {
      this.parentStreamId = parentStreamId;
   }

   public int getWeight() {
      return this.weight;
   }

   public void setWeight(int weight) {
      this.weight = weight;
   }

   public void generateBody(ByteBuffer target) {
      if (this.isExclusive()) {
         target.putInt(this.getParentStreamId() | Integer.MIN_VALUE);
      } else {
         target.putInt(this.getParentStreamId());
      }

      target.put((byte)(this.getWeight() - 1));
   }
}
