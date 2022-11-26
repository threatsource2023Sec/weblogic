package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.TransportExecutable;

class ExecutableWrapper {
   ExecutableWrapper next;
   private final TransportExecutable task;

   ExecutableWrapper(TransportExecutable t) {
      this.task = t;
   }

   public TransportExecutable getTask() {
      return this.task;
   }
}
