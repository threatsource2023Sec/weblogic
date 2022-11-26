package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.openjpa.kernel.Broker;

class CancelAllCommand extends BrokerCommand {
   private boolean _canceled = false;

   public CancelAllCommand() {
      super((byte)19);
   }

   public boolean canceled() {
      return this._canceled;
   }

   public void execute(Broker broker) {
      this._canceled = broker.cancelAll();
   }

   protected void read(ObjectInput in) throws Exception {
   }

   protected void write(ObjectOutput out) throws Exception {
   }

   protected void readResponse(ObjectInput in) throws Exception {
      this._canceled = in.readBoolean();
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
      out.writeBoolean(this._canceled);
   }
}
