package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.dispatcher.Response;

public class DDMembershipResponse extends Response implements Externalizable {
   static final long serialVersionUID = -1329238397594646261L;
   private static final int EXTVERSIONDIABLO = 1;
   private static final int EXTVERSION1221 = 2;
   private static final int VERSION_MASK = 255;
   private static int _HAS_DD_MEMBER_INFORMATION = 65280;
   private static int _HAS_PARTITION_NAME = 65536;
   private DDMemberInformation[] ddMemberInformation;
   private String partitionName = null;

   public DDMembershipResponse(DDMemberInformation[] ddMemberInformation, String partitionName) {
      this.ddMemberInformation = ddMemberInformation;
      this.partitionName = partitionName;
   }

   public DDMemberInformation[] getDDMemberInformation() {
      return this.ddMemberInformation;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public DDMembershipResponse() {
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
      int numDDMembers = 0;
      if (this.ddMemberInformation != null && (numDDMembers = this.ddMemberInformation.length) != 0) {
         flags = version | _HAS_DD_MEMBER_INFORMATION;
      }

      if (version >= 2 && this.partitionName != null && this.partitionName.length() != 0 && !this.partitionName.equals("DOMAIN")) {
         flags |= _HAS_PARTITION_NAME;
      }

      out.writeInt(flags);
      super.writeExternal(out);
      if (numDDMembers != 0) {
         out.writeInt(numDDMembers);

         for(int i = 0; i < numDDMembers; ++i) {
            this.ddMemberInformation[i].writeExternal(out);
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
         if ((flags & _HAS_DD_MEMBER_INFORMATION) != 0) {
            int numDDMembers = in.readInt();
            this.ddMemberInformation = new DDMemberInformation[numDDMembers];

            for(int i = 0; i < numDDMembers; ++i) {
               DDMemberInformation ddmInfo = new DDMemberInformation();
               ddmInfo.readExternal(in);
               this.ddMemberInformation[i] = ddmInfo;
            }
         }

         if ((flags & _HAS_PARTITION_NAME) != 0) {
            this.partitionName = in.readUTF();
         }

      }
   }
}
