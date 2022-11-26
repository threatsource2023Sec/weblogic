package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.TransportExecutable;

abstract class ServiceExecute implements TransportExecutable {
   final State state;
   final ServiceWrapper wrapper;

   ServiceExecute(ServiceWrapper sw, State s) {
      this.wrapper = sw;
      this.state = s;
   }

   public abstract void execute();

   public String toString(Object info) {
      return "ServiceExecute:<" + this.state + "," + this.wrapper + "," + info + ">";
   }

   public String toString() {
      return this.toString("N/A");
   }

   static enum State {
      INVOKEONEWAY,
      INVOKETWOWAY,
      SHUTDOWN,
      UNREGISTER,
      PEERGONE,
      CANCEL;
   }
}
