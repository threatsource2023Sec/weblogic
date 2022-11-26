package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.TransportError;

class ServiceExecutePeerGone extends ServiceExecute {
   final TransportError transportError;

   ServiceExecutePeerGone(ServiceWrapper sw, TransportError te) {
      super(sw, ServiceExecute.State.PEERGONE);
      this.transportError = te;
   }

   public void execute() {
      this.wrapper.getService().onPeerGone(this.transportError);
   }

   public String toString() {
      return this.toString(this.transportError);
   }
}
