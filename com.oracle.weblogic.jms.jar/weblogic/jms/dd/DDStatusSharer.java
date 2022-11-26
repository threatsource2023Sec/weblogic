package weblogic.jms.dd;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.AccessController;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.naming.NamingException;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSUtilities;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.internal.NamingNode;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DDStatusSharer implements DDStatusListener, Aggregatable, Externalizable {
   static final long serialVersionUID = -3705735684883464847L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private DDHandler ddHandler;
   private static int VERSION_FARALON_PS1 = 1;
   private static int VERSION_1033 = 2;
   private static int VERSION_1221 = 3;
   private static final int VERSION_SHIFT = 3;
   private static final int VERSION_MASK = 7;
   private static final int NO_APPLICATION_NAME = 8;
   private static final int NO_MODULE_NAME = 16;
   private static final int IS_ROUTING_PATHSERVICE = 32;
   private static final int NO_SAF_EXPORT_POLICY = 64;
   private static final int IS_ROUTING_HASH = 128;
   private static final int NO_JNDI_NAME = 256;
   private static final int IS_QUEUE = 512;
   private static final int IS_UOW_DESTINATION = 1024;
   private static final int HAS_REFERENCE_NAME = 2048;
   private static final int RESET_DELIVERY_COUNT = 4096;
   private static final int HAS_PATH_SERVICE_JNDI_NAME = 8192;
   private static final int IS_JMS_RESOURCE_DEFINITION = 16384;
   private List remoteMemberList;
   private boolean everHadMembers = false;
   private String serverName;

   public DDStatusSharer() {
   }

   public DDStatusSharer(DDHandler ddHandler) {
      this.ddHandler = ddHandler;
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      this.serverName = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
      if (ddHandler.getNumberOfMembers() != 0) {
         this.everHadMembers = true;
      }

      ddHandler.addStatusListener(this, 29);
   }

   public void statusChangeNotification(DDHandler ddHandler, int events) {
      if ((events & 256) == 0) {
         if (ddHandler.isLocal()) {
            if (!this.everHadMembers) {
               if (ddHandler.getNumberOfMembers() == 0) {
                  return;
               }

               this.everHadMembers = true;
            }

            String internalDistributedJNDIName = "weblogic.jms.internal.dd." + this.serverName + "." + ddHandler.getName();
            boolean unbind = true;

            try {
               if ((events & 16) != 0) {
                  PrivilegedActionUtilities.unbindAsSU(JMSService.getContextWithManagementException(true), internalDistributedJNDIName, kernelId);
               } else {
                  unbind = false;
                  PrivilegedActionUtilities.rebindAsSU(JMSService.getContextWithManagementException(true), internalDistributedJNDIName, this, kernelId);
               }
            } catch (ManagementException | NamingException var6) {
               if (JMSDebug.JMSCommon.isDebugEnabled()) {
                  JMSDebug.JMSCommon.debug("DDStatusSharer:statusChangeNotification(): Exception occurred on " + (unbind ? "unbind " : "rebind ") + internalDistributedJNDIName, var6);
               }
            }

         }
      }
   }

   public void onBind(NamingNode store, String name, Aggregatable newData) throws NamingException {
      DDStatusSharer newValue;
      if (newData == null) {
         newValue = this;
      } else {
         newValue = (DDStatusSharer)newData;
      }

      DDManager.remoteUpdate(newValue.ddHandler, newValue.remoteMemberList);
   }

   public void onRebind(NamingNode store, String name, Aggregatable other) throws NamingException {
      this.onBind(store, name, other);
   }

   public boolean onUnbind(NamingNode store, String name, Aggregatable other) throws NamingException {
      DDManager.remoteDeactivate(this.ddHandler.getName());
      return true;
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      DDHandler ddHandler = new DDHandler();
      int flags = in.readInt();
      int wireVersion = flags & 7;
      boolean requireForwardingPolicy;
      boolean permitReadPathServiceJndi;
      if (wireVersion == VERSION_1221) {
         requireForwardingPolicy = true;
         permitReadPathServiceJndi = true;
      } else if (wireVersion == VERSION_1033) {
         requireForwardingPolicy = true;
         permitReadPathServiceJndi = false;
      } else {
         if (wireVersion != VERSION_FARALON_PS1) {
            throw JMSUtilities.versionIOException(wireVersion, VERSION_FARALON_PS1, VERSION_1221);
         }

         requireForwardingPolicy = false;
         permitReadPathServiceJndi = false;
      }

      ddHandler.setIsQueue((flags & 512) != 0);
      ddHandler.setIsUOWDestinationWithoutEvent((flags & 1024) != 0);
      ddHandler.setName(in.readUTF());
      if ((flags & 8) == 0) {
         ddHandler.setApplicationName(in.readUTF());
      }

      if ((flags & 16) == 0) {
         ddHandler.setEARModuleName(in.readUTF());
      }

      if ((flags & 64) == 0) {
         ddHandler.setSAFExportPolicyWithoutEvent(in.readUTF());
      }

      if ((flags & 128) != 0) {
         ddHandler.setUnitOfOrderRoutingWithoutEvent("Hash".intern());
      } else if ((flags & 32) != 0) {
         ddHandler.setUnitOfOrderRoutingWithoutEvent("PathService".intern());
      }

      if ((flags & 256) == 0) {
         ddHandler.setJNDINameWithoutEvent(in.readUTF());
      }

      ddHandler.setLoadBalancingPolicyAsIntWithoutEvent(in.readInt());
      ddHandler.setForwardDelayWithoutEvent(in.readInt());
      int nMembers = in.readInt();
      this.remoteMemberList = new LinkedList();

      for(int i = 0; i < nMembers; ++i) {
         String member = in.readUTF();
         this.remoteMemberList.add(member);
      }

      if ((flags & 2048) != 0) {
         ddHandler.setReferenceName(in.readUTF());
      }

      ddHandler.setResetDeliveryCountOnForwardWithoutEvent((flags & 4096) != 0);
      if (requireForwardingPolicy) {
         ddHandler.setForwardingPolicy(in.readInt());
      }

      if (permitReadPathServiceJndi && (flags & 8192) != 0) {
         ddHandler.setPathServiceJndiNameWithoutEvent(in.readUTF());
      }

      ddHandler.setJMSResourceDefinition((flags & 16384) != 0);
      this.ddHandler = ddHandler;
   }

   private int versionFromPeerInfoable(PeerInfoable pi) {
      if (pi.getPeerInfo().compareTo(PeerInfo.VERSION_1033) < 0) {
         return VERSION_FARALON_PS1;
      } else {
         return pi.getPeerInfo().compareTo(PeerInfo.VERSION_1221) < 0 ? VERSION_1033 : VERSION_1221;
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int versionFlags;
      if (out instanceof PeerInfoable) {
         versionFlags = this.versionFromPeerInfoable((PeerInfoable)out);
      } else {
         versionFlags = VERSION_1221;
      }

      boolean requireForwardingPolicy;
      boolean permitWritePathServiceJndi;
      if (versionFlags == VERSION_FARALON_PS1) {
         requireForwardingPolicy = false;
         permitWritePathServiceJndi = false;
      } else if (versionFlags == VERSION_1033) {
         requireForwardingPolicy = true;
         permitWritePathServiceJndi = false;
      } else {
         requireForwardingPolicy = true;
         permitWritePathServiceJndi = true;
      }

      int flags = versionFlags;
      synchronized(this.ddHandler) {
         String uooRouting = this.ddHandler.getUnitOfOrderRouting();
         String pathSvcJndi = this.ddHandler.getPathServiceJndiName();
         if (uooRouting != null) {
            if (uooRouting.equals("Hash")) {
               flags |= 128;
            } else if (uooRouting.equals("PathService")) {
               flags |= 32;
            }
         }

         if (this.ddHandler.getApplicationName() == null) {
            flags |= 8;
         }

         if (this.ddHandler.getEARModuleName() == null) {
            flags |= 16;
         }

         if (this.ddHandler.getSAFExportPolicy() == null) {
            flags |= 64;
         }

         if (this.ddHandler.getJNDIName() == null) {
            flags |= 256;
         }

         if (this.ddHandler.isQueue()) {
            flags |= 512;
         }

         if (this.ddHandler.isUOWDestination()) {
            flags |= 1024;
         }

         if (this.ddHandler.getReferenceName() != null) {
            flags |= 2048;
         }

         if (this.ddHandler.getResetDeliveryCountOnForward()) {
            flags |= 4096;
         }

         if (permitWritePathServiceJndi && pathSvcJndi != null && pathSvcJndi.length() > 0) {
            flags |= 8192;
         }

         if (this.ddHandler.isJMSResourceDefinition()) {
            flags |= 16384;
         }

         out.writeInt(flags);
         out.writeUTF(this.ddHandler.getName());
         if ((flags & 8) == 0) {
            out.writeUTF(this.ddHandler.getApplicationName());
         }

         if ((flags & 16) == 0) {
            out.writeUTF(this.ddHandler.getEARModuleName());
         }

         if ((flags & 64) == 0) {
            out.writeUTF(this.ddHandler.getSAFExportPolicy());
         }

         if ((flags & 256) == 0) {
            out.writeUTF(this.ddHandler.getJNDIName());
         }

         out.writeInt(this.ddHandler.getLoadBalancingPolicyAsInt());
         out.writeInt(this.ddHandler.getForwardDelay());
         if (this.ddHandler.isActive()) {
            Iterator iter = this.ddHandler.memberCloneIterator();
            this.remoteMemberList = new LinkedList();

            while(iter.hasNext()) {
               DDMember member = (DDMember)iter.next();
               this.remoteMemberList.add(member.getName());
            }
         }

         out.writeInt(this.remoteMemberList != null ? this.remoteMemberList.size() : 0);
         if (this.remoteMemberList != null) {
            Iterator iter = this.remoteMemberList.listIterator();

            while(iter.hasNext()) {
               String memberName = (String)iter.next();
               out.writeUTF(memberName);
            }
         }

         if (this.ddHandler.isActive()) {
            this.remoteMemberList = null;
         }

         if ((flags & 2048) != 0) {
            out.writeUTF(this.ddHandler.getReferenceName());
         }

         if (requireForwardingPolicy) {
            out.writeInt(this.ddHandler.getForwardingPolicy());
         }

         if ((flags & 8192) != 0) {
            out.writeUTF(pathSvcJndi);
         }

      }
   }

   public boolean equals(Object o) {
      return o instanceof DDStatusSharer ? ((DDStatusSharer)o).ddHandler.getName().equals(this.ddHandler.getName()) : false;
   }

   public int hashCode() {
      return this.ddHandler.getName().hashCode();
   }

   public String toString() {
      return "DDStatusSharer: " + this.ddHandler.getName() + ", hash: " + this.hashCode();
   }
}
