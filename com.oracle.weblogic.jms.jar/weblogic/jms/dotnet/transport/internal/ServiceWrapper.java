package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.Service;
import weblogic.jms.dotnet.transport.TransportError;

class ServiceWrapper {
   private final ThreadPoolWrapper threadPool;
   private final Service service;
   private final long serviceID;
   private boolean closed;

   ServiceWrapper(long sid, Service s, ThreadPoolWrapper ttp) {
      this.service = s;
      this.threadPool = ttp;
      this.serviceID = sid;
   }

   Service getService() {
      return this.service;
   }

   synchronized void invoke(ReceivedOneWayImpl row, long orderingID) {
      if (!this.closed) {
         this.threadPool.schedule(new ServiceExecuteOneWay(this, row), orderingID);
      }
   }

   synchronized void invoke(ReceivedTwoWayImpl rtw, long orderingID) {
      if (!this.closed) {
         this.threadPool.schedule(new ServiceExecuteTwoWay(this, rtw), orderingID);
      }
   }

   synchronized void shutdown(TransportError te) {
      if (!this.closed) {
         this.closed = true;
         if (te != null) {
            this.threadPool.schedule(new ServiceExecutePeerGone(this, te));
         } else {
            this.threadPool.schedule(new ServiceExecuteShutdown(this));
         }

      }
   }

   synchronized void unregister() {
      if (!this.closed) {
         this.closed = true;
         this.threadPool.schedule(new ServiceExecuteUnregister(this));
      }
   }

   public synchronized String toString() {
      return "ServiceWrapper:<" + this.serviceID + "," + this.service + ">";
   }
}
