package weblogic.jms.dotnet.transport.internal;

class ServiceExecuteUnregister extends ServiceExecute {
   ServiceExecuteUnregister(ServiceWrapper sw) {
      super(sw, ServiceExecute.State.UNREGISTER);
   }

   public void execute() {
      this.wrapper.getService().onUnregister();
   }
}
