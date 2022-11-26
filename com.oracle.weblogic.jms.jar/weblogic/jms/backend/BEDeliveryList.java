package weblogic.jms.backend;

import weblogic.messaging.util.DeliveryList;
import weblogic.utils.concurrent.atomic.AtomicFactory;
import weblogic.utils.concurrent.atomic.AtomicLong;

public abstract class BEDeliveryList extends DeliveryList {
   private final AtomicLong nextSequenceNumber = AtomicFactory.createAtomicLong();
   protected BackEnd backEnd;

   protected BEDeliveryList(BackEnd backEnd) {
      this.nextSequenceNumber.set(1L);
      this.setBackEnd(backEnd);
   }

   void setBackEnd(BackEnd backEnd) {
      if (backEnd != null) {
         this.backEnd = backEnd;
         this.setWorkManager(backEnd.getAsyncPushWorkManager());
      }

   }

   BackEnd getBackEnd() {
      return this.backEnd;
   }

   protected void setSequenceNumber(long arg) {
      this.nextSequenceNumber.set(arg);
   }

   long getSequenceNumber() {
      return this.nextSequenceNumber.get();
   }

   long getNextSequenceNumber() {
      return this.nextSequenceNumber.getAndIncrement();
   }
}
