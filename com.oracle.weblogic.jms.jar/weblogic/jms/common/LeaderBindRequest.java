package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;

public final class LeaderBindRequest extends Request implements Externalizable {
   static final long serialVersionUID = -5214437563807672399L;
   private static final byte EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private String serverName;
   private String jndiName;

   public LeaderBindRequest(String serverName, String jndiName) {
      super((JMSID)null, 16405);
      this.serverName = serverName;
      this.jndiName = jndiName;
   }

   public final String getServerName() {
      return this.serverName;
   }

   public int remoteSignature() {
      return 34;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return new LeaderBindResponse();
   }

   public final String getJNDIName() {
      return this.jndiName;
   }

   public String toString() {
      return "LeaderBindRequest(" + this.serverName + ":" + this.jndiName + ")";
   }

   public LeaderBindRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      out.writeInt(mask);
      super.writeExternal(out);
      out.writeUTF(this.serverName);
      out.writeUTF(this.jndiName);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         this.serverName = in.readUTF();
         this.jndiName = in.readUTF();
      }
   }
}
