package kodo.remote;

import org.apache.openjpa.kernel.Broker;

class RollbackCommand extends BrokerCommand {
   public RollbackCommand() {
      super((byte)14);
   }

   public void execute(Broker broker) {
      if (broker.isActive()) {
         broker.rollback();
      }

   }
}
