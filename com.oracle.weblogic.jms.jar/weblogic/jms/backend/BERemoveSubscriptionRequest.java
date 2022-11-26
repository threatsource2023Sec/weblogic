package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class BERemoveSubscriptionRequest extends Request implements Externalizable {
   static final long serialVersionUID = -2710563626585921758L;
   private String clientId;
   private String name;
   private int clientIdPolicy;
   private JMSServerId backEndId;
   private String destinationName;
   private static final int EXTVERSION_PRE_1033 = 1;
   private static final int EXTVERSION_1033 = 2;
   private static final int EXTVERSION = 2;
   private static final int VERSION_MASK = 255;
   private static final int HAS_DESTINATION_NAME = 256;

   public BERemoveSubscriptionRequest(JMSServerId backEndId, String destinationName, String clientId, int clientIdPolicy, String name) {
      super((JMSID)null, 14850);
      this.backEndId = backEndId;
      this.destinationName = destinationName;
      this.clientId = clientId;
      this.clientIdPolicy = clientIdPolicy;
      this.name = name;
   }

   public JMSServerId getBackEndId() {
      return this.backEndId;
   }

   public String getDestinationName() {
      return this.destinationName;
   }

   public String getClientId() {
      return this.clientId;
   }

   public int getClientIdPolicy() {
      return this.clientIdPolicy;
   }

   public String getName() {
      return this.name;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerToServer() {
      return true;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public BERemoveSubscriptionRequest() {
   }

   private byte getVersionForWrite(Object o) throws IOException {
      if (o instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)o).getPeerInfo();
         if (pi.compareTo(PeerInfo.VERSION_1221) < 0) {
            this.checkJMS2ForWriteToOldPeer(pi);
         }

         if (pi.compareTo(PeerInfo.VERSION_1033) < 0) {
            return 1;
         }
      } else {
         this.checkJMS2ForWriteToOldPeer((PeerInfo)null);
      }

      return 2;
   }

   private void checkJMS2ForWriteToOldPeer(PeerInfo pi) throws IOException {
      if (this.clientId == null) {
         throw new IOException("Unsupported operation to back-end server version " + (pi == null ? " < [" + PeerInfo.VERSION_1221 + "]: " : "[" + pi + "]: ") + " unsubscribe durable subscription with name " + this.name + " and null client id");
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int version = this.getVersionForWrite(out);
      int mask = version;
      if (version >= 2 && this.destinationName != null) {
         mask = version | 256;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      this.backEndId.writeExternal(out);
      out.writeUTF(this.clientId == null ? "" : this.clientId);
      out.writeUTF(this.name);
      if (version >= 2) {
         out.writeInt(this.clientIdPolicy);
         if (this.destinationName != null) {
            out.writeUTF(this.destinationName);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1 && version != 2) {
         throw JMSUtilities.versionIOException(version, 1, 2);
      } else {
         super.readExternal(in);
         this.backEndId = new JMSServerId();
         this.backEndId.readExternal(in);
         this.clientId = in.readUTF();
         if (this.clientId.length() == 0) {
            this.clientId = null;
         }

         this.name = in.readUTF();
         if (version >= 2) {
            this.clientIdPolicy = in.readInt();
            if ((mask & 256) != 0) {
               this.destinationName = in.readUTF();
            }
         }

      }
   }
}
