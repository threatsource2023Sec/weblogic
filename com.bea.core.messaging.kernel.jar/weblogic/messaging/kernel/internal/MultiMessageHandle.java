package weblogic.messaging.kernel.internal;

import weblogic.messaging.Message;
import weblogic.messaging.kernel.SendOptions;

public class MultiMessageHandle extends MessageHandle {
   private int lowCurrent;
   private int lowPending;
   private boolean lowReceived;
   private int highCurrent;
   private int highPending;
   private boolean highReceived;

   public MultiMessageHandle() {
   }

   MultiMessageHandle(KernelImpl kernel, Message message, SendOptions options) {
      super(kernel, message, options);
   }

   public MultiMessageHandle(long id, Message message, SendOptions options) {
      super(id, message, options);
   }

   synchronized boolean incrementLowCurrent() {
      return ++this.lowCurrent == 1;
   }

   synchronized boolean decrementLowCurrent() {
      return --this.lowCurrent == 0;
   }

   synchronized boolean incrementLowPending() {
      return ++this.lowPending == 1;
   }

   synchronized boolean decrementLowPending() {
      return --this.lowPending == 0;
   }

   synchronized boolean incrementLowReceived() {
      if (!this.lowReceived) {
         this.lowReceived = true;
         return true;
      } else {
         return false;
      }
   }

   synchronized boolean incrementHighCurrent() {
      return ++this.highCurrent == 1;
   }

   synchronized boolean decrementHighCurrent() {
      return --this.highCurrent == 0;
   }

   synchronized boolean incrementHighPending() {
      return ++this.highPending == 1;
   }

   synchronized boolean decrementHighPending() {
      return --this.highPending == 0;
   }

   synchronized boolean incrementHighReceived() {
      if (!this.highReceived) {
         this.highReceived = true;
         return true;
      } else {
         return false;
      }
   }
}
