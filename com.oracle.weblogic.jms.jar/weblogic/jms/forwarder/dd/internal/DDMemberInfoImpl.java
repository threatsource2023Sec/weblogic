package weblogic.jms.forwarder.dd.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.forwarder.dd.DDMemberInfo;
import weblogic.jms.forwarder.dd.DDMemberRuntimeInformation;

public class DDMemberInfoImpl implements DDMemberInfo {
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   private static int versionmask = 255;
   private static final long serialVersionUID = -3799642636905950867L;
   private static int _HAS_JMS_SERVER_CONFIG_NAME = 8;
   private String jmsServerInstanceName;
   private String jmsServerConfigName;
   private String ddMemberConfigName;
   private String destinationType;
   private transient DDMemberRuntimeInformation ddMemberRuntimeInformation;
   private transient DestinationImpl dImpl;

   public DDMemberInfoImpl() {
   }

   public DDMemberInfoImpl(String jmsServerInstanceName, String jmsServerConfigName, String ddMemberConfigName, String destinationType, DestinationImpl dImpl) {
      this(jmsServerInstanceName, jmsServerConfigName, ddMemberConfigName, destinationType, dImpl, (DDMemberRuntimeInformation)null);
   }

   public DDMemberInfoImpl(String jmsServerInstanceName, String jmsServerConfigName, String ddMemberConfigName, String destinationType, DestinationImpl dImpl, DDMemberRuntimeInformation ddMemberRuntimeInformation) {
      this.jmsServerInstanceName = jmsServerInstanceName;
      this.jmsServerConfigName = jmsServerConfigName;
      this.ddMemberConfigName = ddMemberConfigName;
      this.destinationType = destinationType;
      this.dImpl = dImpl;
      this.ddMemberRuntimeInformation = ddMemberRuntimeInformation;
   }

   public String toString() {
      return "<DDMemberInfoImpl> = { ddMemberConfigName=" + this.ddMemberConfigName + ", destinationType=" + this.destinationType + ", JmsServerInstanceName=" + this.jmsServerInstanceName + ", JmsServerConfigName=" + this.jmsServerConfigName + "}";
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof DDMemberInfoImpl)) {
         return false;
      } else {
         DDMemberInfoImpl ddMemberInfo;
         label56: {
            ddMemberInfo = (DDMemberInfoImpl)o;
            if (this.ddMemberConfigName != null) {
               if (this.ddMemberConfigName.equals(ddMemberInfo.ddMemberConfigName)) {
                  break label56;
               }
            } else if (ddMemberInfo.ddMemberConfigName == null) {
               break label56;
            }

            return false;
         }

         label49: {
            if (this.destinationType != null) {
               if (this.destinationType.equals(ddMemberInfo.destinationType)) {
                  break label49;
               }
            } else if (ddMemberInfo.destinationType == null) {
               break label49;
            }

            return false;
         }

         if (this.jmsServerInstanceName != null) {
            if (!this.jmsServerInstanceName.equals(ddMemberInfo.jmsServerInstanceName)) {
               return false;
            }
         } else if (ddMemberInfo.jmsServerInstanceName != null) {
            return false;
         }

         if (this.jmsServerConfigName != null) {
            if (!this.jmsServerConfigName.equals(ddMemberInfo.jmsServerConfigName)) {
               return false;
            }
         } else if (ddMemberInfo.jmsServerConfigName != null) {
            return false;
         }

         return true;
      }
   }

   public int hashCode() {
      int result = this.jmsServerInstanceName != null ? this.jmsServerInstanceName.hashCode() : 0;
      result = 29 * result + (this.jmsServerConfigName != null ? this.jmsServerConfigName.hashCode() : 0);
      result = 29 * result + (this.ddMemberConfigName != null ? this.ddMemberConfigName.hashCode() : 0);
      result = 29 * result + (this.destinationType != null ? this.destinationType.hashCode() : 0);
      return result;
   }

   public String getMemberName() {
      return this.jmsServerInstanceName + "/" + this.ddMemberConfigName;
   }

   public String getMemberNameForSort() {
      return this.dImpl.getMemberNameForSort();
   }

   public String getJMSServerInstanceName() {
      return this.jmsServerInstanceName;
   }

   public String getJMSServerConfigName() {
      return this.jmsServerConfigName;
   }

   public String getType() {
      return this.destinationType;
   }

   public String getDDMemberConfigName() {
      return this.ddMemberConfigName;
   }

   public DDMemberRuntimeInformation getDDMemberRuntimeInformation() {
      return this.ddMemberRuntimeInformation;
   }

   public String getDispatcherName() {
      return this.dImpl.getDispatcherName();
   }

   public boolean isOnPreferredServer() {
      return this.dImpl.isOnPreferredServer();
   }

   public boolean isPossiblyClusterTargeted() {
      return this.dImpl.isPossiblyClusterTargeted();
   }

   public DestinationImpl getDestination() {
      return this.dImpl;
   }

   public void setDestination(DestinationImpl destination) {
      this.dImpl = destination;
   }

   protected int getVersion(Object oo) throws IOException {
      if (oo instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)oo).getPeerInfo();
         int majorVer = pi.getMajor();
         if (majorVer < 9) {
            throw new IOException(JMSClientExceptionLogger.logIncompatibleVersion9Loggable((byte)1, (byte)1, (byte)1, (byte)1, pi.toString()).getMessage());
         }

         if (pi.compareTo(PeerInfo.VERSION_1212) < 0) {
            return 1;
         }
      }

      return 2;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = this.getVersion(out);
      int version = flags & versionmask;
      if (version >= 2 && this.jmsServerConfigName != null && this.jmsServerConfigName.length() != 0) {
         flags |= _HAS_JMS_SERVER_CONFIG_NAME;
      }

      out.writeInt(flags);
      out.writeUTF(this.jmsServerInstanceName);
      out.writeUTF(this.ddMemberConfigName);
      out.writeUTF(this.destinationType);
      if (version >= 2 && (flags & _HAS_JMS_SERVER_CONFIG_NAME) != 0) {
         out.writeUTF(this.jmsServerConfigName);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int vrsn = flags & versionmask;
      if (vrsn < 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 2);
      } else {
         this.jmsServerInstanceName = in.readUTF();
         this.ddMemberConfigName = in.readUTF();
         this.destinationType = in.readUTF();
         if ((flags & _HAS_JMS_SERVER_CONFIG_NAME) != 0) {
            this.jmsServerConfigName = in.readUTF();
         }

      }
   }
}
