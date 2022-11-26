package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.rmi.spi.HostID;

public class MulticastSessionsStatusMessage extends MulticastSessionsStatus implements GroupMessage, Externalizable {
   public MulticastSessionsStatusMessage(int statusChangeNumber) {
      super(statusChangeNumber);
   }

   public MulticastSessionsStatusMessage() {
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void execute(HostID hostID) {
      MemberManager.theOne().handleMulticastSessionsStatusMessage(hostID, this);
   }
}
