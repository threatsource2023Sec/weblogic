package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.TransportExecutable;

class Order implements TransportExecutable {
   private ExecutableWrapper first;
   private ExecutableWrapper last;
   private final long ordering;
   private final ThreadPoolWrapper poolWrapper;

   private Order(ThreadPoolWrapper poolWrapper, long ordering) {
      this.poolWrapper = poolWrapper;
      this.ordering = ordering;
   }

   private synchronized void add(ExecutableWrapper ew) {
      if (this.first == null) {
         this.first = ew;
         this.last = ew;
      } else {
         this.last.next = ew;
         this.last = ew;
      }
   }

   private synchronized ExecutableWrapper remove() {
      if (this.first == null) {
         return null;
      } else {
         ExecutableWrapper hold = this.first;
         this.first = this.first.next;
         if (this.first == null) {
            this.last = null;
         }

         return hold;
      }
   }

   private synchronized boolean isEmpty() {
      return this.first == null;
   }

   static void schedule(ThreadPoolWrapper poolWrapper, TransportExecutable tte, long orderingID) {
      ExecutableWrapper ew = new ExecutableWrapper(tte);
      Order o;
      synchronized(poolWrapper.getLock()) {
         o = poolWrapper.getOrder(orderingID);
         if (o != null) {
            o.add(ew);
            return;
         }

         o = new Order(poolWrapper, orderingID);
         o.add(ew);
         poolWrapper.putOrder(orderingID, o);
      }

      poolWrapper.schedule(o);
   }

   public void execute() {
      for(int i = 0; i < 10; ++i) {
         ExecutableWrapper ew = this.remove();
         ew.getTask().execute();
         if (this.isEmpty()) {
            synchronized(this.poolWrapper.getLock()) {
               if (this.isEmpty()) {
                  this.poolWrapper.removeOrder(this.ordering);
                  return;
               }
            }
         }
      }

      this.poolWrapper.schedule(this);
   }
}
