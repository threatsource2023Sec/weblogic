package weblogic.jms.dotnet.transport.internal;

class ServiceExecuteShutdown extends ServiceExecute {
   ServiceExecuteShutdown(ServiceWrapper sw) {
      super(sw, ServiceExecute.State.SHUTDOWN);
   }

   public void execute() {
      this.wrapper.getService().onShutdown();
   }
}
