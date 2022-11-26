package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.Service;
import weblogic.jms.dotnet.transport.ServiceOneWay;

class ServiceExecuteOneWay extends ServiceExecute {
   final ReceivedOneWayImpl receivedOneWay;

   ServiceExecuteOneWay(ServiceWrapper sw, ReceivedOneWayImpl row) {
      super(sw, ServiceExecute.State.INVOKEONEWAY);
      this.receivedOneWay = row;
   }

   public void execute() {
      Service svc = this.wrapper.getService();

      try {
         ((ServiceOneWay)svc).invoke(this.receivedOneWay);
      } catch (Throwable var3) {
      }

   }

   public String toString() {
      return this.toString(this.receivedOneWay);
   }
}
