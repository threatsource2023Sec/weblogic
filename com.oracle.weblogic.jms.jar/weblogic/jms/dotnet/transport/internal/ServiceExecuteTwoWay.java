package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.Service;
import weblogic.jms.dotnet.transport.ServiceTwoWay;
import weblogic.jms.dotnet.transport.TransportError;

class ServiceExecuteTwoWay extends ServiceExecute {
   final ReceivedTwoWayImpl receivedTwoWay;

   ServiceExecuteTwoWay(ServiceWrapper sw, ReceivedTwoWayImpl rtw) {
      super(sw, ServiceExecute.State.INVOKETWOWAY);
      this.receivedTwoWay = rtw;
   }

   public void execute() {
      Service svc = this.wrapper.getService();

      try {
         ((ServiceTwoWay)svc).invoke(this.receivedTwoWay);
      } catch (Throwable var5) {
         Throwable t = var5;

         try {
            if (!this.receivedTwoWay.isAlreadySent()) {
               this.receivedTwoWay.send(new TransportError(t));
            }
         } catch (Throwable var4) {
         }
      }

   }

   public String toString() {
      return this.toString(this.receivedTwoWay);
   }
}
