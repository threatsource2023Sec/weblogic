package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.Response;
import weblogic.messaging.dispatcher.VoidResponse;

public class DDMembershipCancelRequest extends Request implements Externalizable {
   private static final long serialVersionUID = -5527130705835795695L;
   private static final int EXTVERSIONDIABLO = 1;
   private static final int EXTVERSION1221 = 2;
   private static final int VERSION_MASK = 255;
   private static int _HAS_PARTITION_NAME = 256;
   private String ddJndiName;
   private DispatcherWrapper dispatcherWrapper;
   private String partitionName = null;

   public DDMembershipCancelRequest(String ddJndiName, DispatcherWrapper dispatcherWrapper, String partitionName) {
      super((JMSID)null, 18967);
      this.ddJndiName = ddJndiName;
      this.dispatcherWrapper = dispatcherWrapper;
      this.partitionName = partitionName;
      if (ddJndiName == null) {
         throw new Error(" Call BEA Support. DDMembershipCancelRequest.ddJndiName = null");
      }
   }

   public DispatcherWrapper getDispatcherWrapper() {
      return this.dispatcherWrapper;
   }

   public String getDDJndiName() {
      return this.ddJndiName;
   }

   public String getPartitionName() {
      return this.partitionName;
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

   public DDMembershipCancelRequest() {
   }

   private int getVersion(Object o) throws IOException {
      if (o instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)o).getPeerInfo();
         if (pi.compareTo(PeerInfo.VERSION_DIABLO) < 0) {
            throw JMSUtilities.versionIOException(0, 1, 2);
         }

         if (pi.compareTo(PeerInfo.VERSION_1221) < 0) {
            return 1;
         }
      }

      return 2;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int version = this.getVersion(out);
      int flags = version;
      if (version >= 2 && this.partitionName != null && this.partitionName.length() != 0 && !this.partitionName.equals("DOMAIN")) {
         flags = version | _HAS_PARTITION_NAME;
      }

      out.writeInt(flags);
      super.writeExternal(out);
      this.dispatcherWrapper.writeExternal(out);
      out.writeUTF(this.ddJndiName);
      if ((flags & _HAS_PARTITION_NAME) != 0) {
         out.writeUTF(this.partitionName);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1 && version != 2) {
         throw JMSUtilities.versionIOException(version, 1, 2);
      } else {
         super.readExternal(in);
         this.dispatcherWrapper = new DispatcherWrapper();
         this.dispatcherWrapper.readExternal(in);
         this.ddJndiName = in.readUTF();
         if ((flags & _HAS_PARTITION_NAME) != 0) {
            this.partitionName = in.readUTF();
         }

      }
   }
}
