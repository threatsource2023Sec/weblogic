package kodo.remote;

import org.apache.openjpa.kernel.Broker;

abstract class BrokerCommand extends KodoCommand {
   public BrokerCommand(byte code) {
      super(code);
   }

   protected void execute(KodoContextFactory context) throws Exception {
      this.execute((Broker)context.getContext(this.getClientId()));
   }

   protected abstract void execute(Broker var1) throws Exception;
}
