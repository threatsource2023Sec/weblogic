package weblogic.servlet.internal.session;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.cluster.replication.QuerySessionRequestMessage;
import weblogic.cluster.replication.QuerySessionResponseMessage;
import weblogic.rmi.spi.HostID;
import weblogic.servlet.internal.ROIDLookupImpl;

public class HttpQuerySessionRequestMessage implements QuerySessionRequestMessage, Externalizable {
   private String sessionID;
   private String webServerName;

   public HttpQuerySessionRequestMessage() {
   }

   public HttpQuerySessionRequestMessage(String sessionID, String webServerName) {
      this.sessionID = sessionID;
      this.webServerName = webServerName;
   }

   public QuerySessionResponseMessage execute(HostID sender) {
      ROIDLookupImpl roidLookup = new ROIDLookupImpl();
      HostID[] hosts = roidLookup.getPrimarySecondaryHosts(this.webServerName, this.sessionID);
      return hosts != null ? new HttpQuerySessionResponseMessage(this.getID(), hosts[0], hosts[1]) : null;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.sessionID);
      out.writeUTF(this.webServerName);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.sessionID = in.readUTF();
      this.webServerName = in.readUTF();
   }

   public String getID() {
      return this.sessionID + "." + this.webServerName;
   }
}
