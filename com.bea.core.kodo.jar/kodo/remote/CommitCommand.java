package kodo.remote;

import org.apache.openjpa.kernel.Broker;

class CommitCommand extends BrokerCommand {
   public CommitCommand() {
      super((byte)3);
   }

   public void execute(Broker broker) {
      broker.commit();
   }
}
