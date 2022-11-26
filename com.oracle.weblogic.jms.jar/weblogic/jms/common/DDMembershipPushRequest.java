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

public class DDMembershipPushRequest extends Request implements Externalizable {
   private static final long serialVersionUID = 7109883643317752320L;
   private static final int EXTVERSIONDIABLO = 1;
   private static final int EXTVERSION1221 = 2;
   private static final int VERSION_MASK = 255;
   private static final int _HAS_MEMBER_LIST = 256;
   private static int _HAS_PARTITION_NAME = 512;
   private String ddConfigName;
   private String ddJndiName;
   private DDMemberInformation[] memberList;
   private DispatcherWrapper dispatcherWrapper;
   private String partitionName = null;

   public DDMembershipPushRequest(String ddConfigName, String ddJndiName, DDMemberInformation[] memberList, DispatcherWrapper dispatcherWrapper, String partitionName) {
      super((JMSID)null, 18711);
      this.ddConfigName = ddConfigName;
      this.ddJndiName = ddJndiName;
      this.memberList = memberList;
      this.dispatcherWrapper = dispatcherWrapper;
      this.partitionName = partitionName;
      if (ddConfigName == null) {
         throw new Error(" Call BEA Support. DDMembershipPushRequest.ddConfigName = null");
      } else if (ddJndiName == null) {
         throw new Error(" Call BEA Support. DDMembershipPushRequest.ddJndiName = null");
      }
   }

   public DispatcherWrapper getDispatcherWrapper() {
      return this.dispatcherWrapper;
   }

   public String getDDConfigName() {
      return this.ddConfigName;
   }

   public String getDDJndiName() {
      return this.ddJndiName;
   }

   public DDMemberInformation[] getMemberList() {
      return this.memberList;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public int remoteSignature() {
      return 18;
   }

   public boolean isServerOneWay() {
      return true;
   }

   public boolean isServerToServer() {
      return false;
   }

   public Response createResponse() {
      return VoidResponse.THE_ONE;
   }

   public DDMembershipPushRequest() {
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
      int numMemberList = 0;
      if (this.memberList != null && (numMemberList = this.memberList.length) != 0) {
         flags = version | 256;
      }

      if (version >= 2 && this.partitionName != null && this.partitionName.length() != 0 && !this.partitionName.equals("DOMAIN")) {
         flags |= _HAS_PARTITION_NAME;
      }

      out.writeInt(flags);
      super.writeExternal(out);
      this.dispatcherWrapper.writeExternal(out);
      out.writeUTF(this.ddConfigName);
      out.writeUTF(this.ddJndiName);
      if (numMemberList != 0) {
         out.writeInt(numMemberList);

         for(int i = 0; i < numMemberList; ++i) {
            this.memberList[i].writeExternal(out);
         }
      }

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
         this.ddConfigName = in.readUTF();
         this.ddJndiName = in.readUTF();
         if ((flags & 256) != 0) {
            int numMemberList = in.readInt();
            this.memberList = new DDMemberInformation[numMemberList];

            for(int i = 0; i < numMemberList; ++i) {
               DDMemberInformation ddmInfo = new DDMemberInformation();
               ddmInfo.readExternal(in);
               this.memberList[i] = ddmInfo;
            }
         }

         if ((flags & _HAS_PARTITION_NAME) != 0) {
            this.partitionName = in.readUTF();
         }

      }
   }
}
