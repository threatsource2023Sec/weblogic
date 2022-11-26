package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.jms.dispatcher.VoidResponse;
import weblogic.messaging.dispatcher.Response;

public final class FEConnectionSetClientIdRequest extends Request implements Externalizable {
   static final long serialVersionUID = -2181075251492512714L;
   private String clientId;
   private static final int EXTVERSION_PRE_1033 = 1;
   private static final int EXTVERSION_1033 = 2;
   private static final int EXTVERSION = 2;
   private static final int VERSION_MASK = 255;
   public static final int CONTINUE = 1;
   private int clientIdPolicy = 0;
   private transient Object myFocascia;

   public FEConnectionSetClientIdRequest(JMSID connectionId, String clientId, int clientIdPolicy) {
      super(connectionId, 1799);
      this.clientId = clientId;
      this.clientIdPolicy = clientIdPolicy;
   }

   public final String getClientId() {
      return this.clientId;
   }

   public final int getClientIdPolicy() {
      return this.clientIdPolicy;
   }

   public void setFocascia(Object myFocascia) {
      this.myFocascia = myFocascia;
   }

   public Object getFocascia() {
      return this.myFocascia;
   }

   public int remoteSignature() {
      return 18;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public FEConnectionSetClientIdRequest() {
   }

   private byte getVersion(Object o) throws IOException {
      if (o instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)o).getPeerInfo();
         if (pi.compareTo(PeerInfo.VERSION_1033) < 0) {
            return 1;
         }
      }

      return 2;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int version = this.getVersion(out);
      out.writeInt(version);
      super.writeExternal(out);
      out.writeUTF(this.clientId);
      if (version >= 2) {
         out.writeInt(this.clientIdPolicy);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1 && version != 2) {
         throw JMSUtilities.versionIOException(version, 1, 2);
      } else {
         super.readExternal(in);
         this.clientId = in.readUTF();
         if (version >= 2) {
            this.clientIdPolicy = in.readInt();
         }

      }
   }
}
