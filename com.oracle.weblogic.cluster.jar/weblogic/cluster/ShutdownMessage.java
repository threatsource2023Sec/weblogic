package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.rmi.spi.HostID;

public class ShutdownMessage implements GroupMessage, Externalizable {
   public void execute(HostID hostID) {
      MemberManager.theOne().shutdown(hostID);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
   }
}
