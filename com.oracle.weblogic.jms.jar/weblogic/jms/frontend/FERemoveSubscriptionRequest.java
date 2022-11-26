package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class FERemoveSubscriptionRequest extends Request implements Externalizable {
   static final long serialVersionUID = 4562660717591850604L;
   private String clientId;
   private String name;
   private int clientIdPolicy;
   private JMSServerId backEndId;
   private String destinationName;
   private DestinationImpl destination;
   private static final int EXTVERSION_PRE_1033 = 1;
   private static final int EXTVERSION_1033 = 2;
   private static final int EXTVERSION_1034 = 3;
   private static final int EXTVERSION = 3;
   private static final int VERSION_MASK = 255;
   private static final int HAS_JMSSERVER_ID = 256;
   private static final int HAS_DESTINATION_NAME = 512;
   private static final int HAS_DESTINATION = 1024;
   static final int START = 0;
   static final int WAIT = 1;

   public FERemoveSubscriptionRequest(String clientId, String name) {
      super((JMSID)null, 5377);
      this.clientIdPolicy = 0;
      this.clientId = clientId;
      this.name = name;
   }

   public FERemoveSubscriptionRequest(String clientId, String name, int clientIdPolicy, JMSServerId backEndId, String destinationName) {
      this(clientId, name);
      this.clientIdPolicy = clientIdPolicy;
      this.destinationName = destinationName;
      this.backEndId = backEndId;
   }

   public FERemoveSubscriptionRequest(String clientId, String name, int clientIdPolicy, DestinationImpl destination) {
      this(clientId, name);
      this.clientIdPolicy = clientIdPolicy;
      this.destination = destination;
      this.backEndId = destination.getBackEndId();
      this.destinationName = destination.getTopicName();
   }

   public String getClientId() {
      return this.clientId;
   }

   public String getName() {
      return this.name;
   }

   public String getDestinationName() {
      return this.destinationName;
   }

   public int getClientIdPolicy() {
      return this.clientIdPolicy;
   }

   public JMSServerId getBackEndId() {
      return this.backEndId;
   }

   void setBackEndId(JMSServerId newBackEndId) {
      this.backEndId = newBackEndId;
   }

   public DestinationImpl getDestination() {
      return this.destination;
   }

   void setDestination(DestinationImpl refreshedDestination) {
      this.destination = refreshedDestination;
      this.backEndId = refreshedDestination.getBackEndId();
      this.destinationName = refreshedDestination.getTopicName();
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FERemoveSubscriptionRequest() {
      this.clientIdPolicy = 0;
   }

   private byte getVersion(Object o) throws IOException {
      if (o instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)o).getPeerInfo();
         if (pi.compareTo(PeerInfo.VERSION_1033) < 0) {
            return 1;
         }

         if (pi.compareTo(PeerInfo.VERSION_1034) < 0) {
            return 2;
         }
      }

      return 3;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int version = this.getVersion(out) & 255;
      int mask = version;
      if (version >= 3) {
         if (this.destination != null) {
            mask = version | 1024;
         }
      } else if (version >= 2) {
         if (this.backEndId != null) {
            mask = version | 256;
         }

         if (this.destinationName != null) {
            mask |= 512;
         }
      }

      out.writeInt(mask);
      super.writeExternal(out);
      out.writeUTF(this.clientId == null ? "" : this.clientId);
      out.writeUTF(this.name);
      if (version >= 3) {
         out.writeInt(this.clientIdPolicy);
         if (this.destination != null) {
            this.destination.writeExternal(out);
         }
      } else if (version >= 2) {
         out.writeInt(this.clientIdPolicy);
         if (this.backEndId != null) {
            this.backEndId.writeExternal(out);
         }

         if (this.destinationName != null) {
            out.writeUTF(this.destinationName);
         }
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1 && version != 2 && version != 3) {
         throw JMSUtilities.versionIOException(version, 1, 3);
      } else {
         super.readExternal(in);
         this.clientId = in.readUTF();
         if (this.clientId.length() == 0) {
            this.clientId = null;
         }

         this.name = in.readUTF();
         if (version >= 3) {
            this.clientIdPolicy = in.readInt();
            if ((mask & 1024) != 0) {
               this.destination = new DestinationImpl();
               this.destination.readExternal(in);
               this.backEndId = this.destination.getBackEndId();
               this.destinationName = this.destination.getTopicName();
            }
         } else if (version >= 2) {
            this.clientIdPolicy = in.readInt();
            if ((mask & 256) != 0) {
               this.backEndId = new JMSServerId();
               this.backEndId.readExternal(in);
            }

            if ((mask & 512) != 0) {
               this.destinationName = in.readUTF();
            }
         }

      }
   }
}
