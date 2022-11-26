package kodo.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.openjpa.kernel.Broker;

class ChangeSettingsCommand extends BrokerCommand {
   private BrokerFlags _flags;

   ChangeSettingsCommand() {
      super((byte)1);
      this._flags = null;
   }

   public ChangeSettingsCommand(BrokerFlags flags) {
      this();
      this._flags = flags;
   }

   public BrokerFlags getBrokerFlags() {
      return this._flags;
   }

   public void execute(Broker broker) {
      this._flags.sync(broker);
   }

   protected void read(ObjectInput in) throws Exception {
      this._flags = (BrokerFlags)in.readObject();
   }

   protected void write(ObjectOutput out) throws Exception {
      out.writeObject(this._flags);
   }
}
