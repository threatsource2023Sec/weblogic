package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.TransportExecutable;
import weblogic.jms.dotnet.transport.TransportThreadPool;
import weblogic.utils.collections.NumericKeyHashMap;

class ThreadPoolWrapper {
   static final ThreadPoolWrapper DIRECT = new ThreadPoolWrapper(new DirectThreadPool());
   private final TransportThreadPool poolActual;
   private final OrderLock lock = new OrderLock();
   private NumericKeyHashMap orderings = new NumericKeyHashMap();

   ThreadPoolWrapper(TransportThreadPool t) {
      this.poolActual = t;
   }

   void schedule(TransportExecutable te) {
      this.poolActual.schedule(te);
   }

   void schedule(TransportExecutable tte, long orderingID) {
      if (orderingID == -1L) {
         this.poolActual.schedule(tte);
      } else {
         Order.schedule(this, tte, orderingID);
      }

   }

   OrderLock getLock() {
      return this.lock;
   }

   void removeOrder(long key) {
      this.orderings.remove(key);
   }

   Order getOrder(long key) {
      return (Order)this.orderings.get(key);
   }

   void putOrder(long key, Order o) {
      this.orderings.put(key, o);
   }
}
