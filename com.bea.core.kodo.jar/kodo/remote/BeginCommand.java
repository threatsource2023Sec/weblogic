package kodo.remote;

import org.apache.openjpa.kernel.Broker;

class BeginCommand extends BrokerCommand {
   public BeginCommand() {
      super((byte)0);
   }

   public void execute(Broker broker) {
      broker.begin();
   }
}
