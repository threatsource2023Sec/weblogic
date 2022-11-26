package weblogic.jms.dd;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSService;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSServerId;
import weblogic.jms.common.JMSUtilities;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.internal.NamingNode;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DDMemberStatusSharer implements MemberStatusListener, Aggregatable, Externalizable {
   static final long serialVersionUID = 3562221018441394798L;
   private DDMember member;
   private static final int VERSION_SHIFT = 3;
   private static final int VERSION_MASK = 7;
   private static final int EXTVERSION_920 = 1;
   private static final int EXTVERSION_DANTE = 2;
   private static final int EXTVERSION_CORDELL = 3;
   private static final int EXTVERSION_1212_SERIALIZE = 4;
   private static final int EXTVERSION_1221_UnWritten = 5;
   private static final int IS_PRODUCTION_PAUSED = 8;
   private static final int IS_CONSUMPTION_PAUSED = 16;
   private static final int IS_INSERTION_PAUSED = 32;
   private static final int IS_UP = 64;
   private static final int HAS_CONSUMERS = 128;
   private static final int IS_PERSISTENT = 256;
   private static final int HAS_GLOBAL_JNDI_NAME = 512;
   private static final int HAS_LOCAL_JNDI_NAME = 1024;
   private static final int HAS_MIGRATABLE_TARGET_NAME = 2048;
   private static final int HAS_DOMAIN_NAME = 4096;
   private static final int SECURITY_MASK = 24576;
   private static final int WANTS_UNSIGNED = 8192;
   private static final int WANTS_SIGNED = 16384;
   private static final int WANTS_SIGNEDFULL = 24576;
   private static final int WANTS_KERNELID = 0;
   private static final int HAS_MORE_FLAGS = 32768;
   private static final int HAS_STORE_NAME = 1;
   private static final int HAS_CONFIG_NAME = 2;
   private static final int HAS_MEMBER_TYPE_1212 = 4;
   private static final int HAS_PATH_SERVICE_JNDI_NAME = 8;
   private static final int HAS_MEMBER_TYPE_1221 = 16;
   private boolean init = false;
   private String internalDistributedJNDIName;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public DDMemberStatusSharer(DDMember member) {
      this.member = member;
      member.addStatusListener(this);
   }

   public DDMemberStatusSharer() {
   }

   private void init() {
      String serverName = ManagementService.getRuntimeAccess(kernelId).getServer().getName();
      this.internalDistributedJNDIName = "weblogic.jms.internal.ddmember." + this.member.getName() + "." + serverName;
      this.init = true;
   }

   public void doBind() {
      if (this.member.isLocal()) {
         if (!this.init) {
            this.init();
         }

         try {
            PrivilegedActionUtilities.rebindAsSU(JMSService.getContextWithManagementException(true), this.internalDistributedJNDIName, this, kernelId);
         } catch (ManagementException | NamingException var2) {
            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug("DDMemberStatusSharer:doBind(): Exception occurred on rebind " + this.internalDistributedJNDIName, var2);
            }
         }

      }
   }

   public void doUnbind() {
      if (!this.init) {
         this.init();
      }

      try {
         PrivilegedActionUtilities.unbindAsSU(JMSService.getContextWithManagementException(true), this.internalDistributedJNDIName, this, kernelId);
      } catch (ManagementException | NamingException var2) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("DDMemberStatusSharer:doUnbind(): Exception occurred on unbind " + this.internalDistributedJNDIName, var2);
         }
      }

   }

   public void memberStatusChange(DDMember member, int events) {
      if (!member.isDestinationUp()) {
         if ((events & 16) != 0) {
            member.removeStatusListener(this);
            this.doUnbind();
         }
      } else if ((events & 64) == 0) {
         this.doBind();
      }

   }

   public void onBind(NamingNode store, String name, Aggregatable newValue) throws NamingException {
      if (newValue == null) {
         newValue = this;
      }

      DDManager.memberUpdate(((DDMemberStatusSharer)newValue).member);
   }

   public void onRebind(NamingNode store, String name, Aggregatable other) throws NamingException {
      this.onBind(store, name, other);
   }

   private boolean checkForReplacement(NamingNode store, String name) {
      boolean replaced = false;

      try {
         Enumeration en = store.listBindings("", (Hashtable)null);

         while(en != null && en.hasMoreElements()) {
            NameClassPair nameClass = (NameClassPair)en.nextElement();
            if (!nameClass.getName().equals(name)) {
               Object dmss = store.lookupLink(nameClass.getName(), (Hashtable)null);
               if (dmss instanceof DDMemberStatusSharer) {
                  replaced = DDManager.memberUpdate(((DDMemberStatusSharer)dmss).member);
               }
            }
         }
      } catch (NamingException var7) {
         var7.printStackTrace();
      } catch (RemoteException var8) {
         var8.printStackTrace();
      } catch (ClassCastException var9) {
         var9.printStackTrace();
      }

      return replaced;
   }

   public boolean onUnbind(NamingNode store, String name, Aggregatable other) throws NamingException {
      if (this.checkForReplacement(store, name)) {
         return true;
      } else {
         DDHandler ddHandler = DDManager.findDDHandlerByMemberName(this.member.getName());
         if (ddHandler != null) {
            DDMember existingMember = ddHandler.findMemberByNameWithDDHandlerLock(this.member.getName());
            if (existingMember != null && !existingMember.isLocal()) {
               synchronized(existingMember) {
                  existingMember.setHasConsumers(false);
                  existingMember.setIsUp(false);
               }
            }
         } else {
            DDManager.removeDeferredMember(this.member.getName());
         }

         return true;
      }
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int version = this.getPeerInfoNetworkVersion(out);
      short flags = (short)this.get3BitWriteNetworkVersion(out);
      synchronized(this.member) {
         if (this.member.isProductionPaused()) {
            flags = (short)(flags | 8);
         }

         if (this.member.isConsumptionPaused()) {
            flags = (short)(flags | 16);
         }

         if (this.member.isInsertionPaused()) {
            flags = (short)(flags | 32);
         }

         if (this.member.isUp()) {
            flags = (short)(flags | 64);
         }

         if (this.member.hasConsumers()) {
            flags = (short)(flags | 128);
         }

         if (this.member.isPersistent()) {
            flags = (short)(flags | 256);
         }

         if (this.member.getMigratableTargetName() != null) {
            flags = (short)(flags | 2048);
         }

         if (version > 1 && this.member.getDomainName() != null) {
            flags = (short)(flags | 4096);
         }

         if (this.member.getGlobalJNDIName() != null) {
            flags = (short)(flags | 512);
         }

         if (this.member.getLocalJNDIName() != null) {
            flags = (short)(flags | 1024);
         }

         switch (this.member.getRemoteSecurityMode()) {
            case 11:
               flags = (short)(flags | 16384);
               break;
            case 12:
               flags = (short)(flags | 8192);
               break;
            case 13:
               flags = (short)(flags | 24576);
               break;
            case 14:
               flags = (short)(flags | 0);
               break;
            default:
               throw new AssertionError();
         }

         short extendedFlags = 0;
         String localJmsServerConfigName = this.member.getJMSServerConfigName();
         String localPathServiceJndiName = this.member.getPathServiceJndiName();
         if (version >= 3) {
            if (this.member.getPersistentStoreName() != null && this.member.getPersistentStoreName().length() != 0) {
               extendedFlags = (short)(extendedFlags | 1);
            }

            if (version >= 4) {
               if (localJmsServerConfigName != null && localJmsServerConfigName.length() != 0) {
                  extendedFlags = (short)(extendedFlags | 2);
               }

               if (version < 5) {
                  extendedFlags = (short)(extendedFlags | 4);
               } else {
                  extendedFlags = (short)(extendedFlags | 16);
                  if (localPathServiceJndiName != null && localPathServiceJndiName.length() != 0) {
                     extendedFlags = (short)(extendedFlags | 8);
                  }
               }
            }

            if (extendedFlags != 0) {
               flags = (short)(flags | '耀');
            }
         }

         out.writeShort(flags);
         out.writeInt(this.member.getWeight());
         out.writeUTF(this.member.getName());
         out.writeUTF(this.member.getWLSServerName());
         out.writeUTF(this.member.getJMSServerInstanceName());
         if ((flags & 2048) != 0) {
            out.writeUTF(this.member.getMigratableTargetName());
         }

         if ((flags & 4096) != 0) {
            out.writeUTF(this.member.getDomainName());
         }

         if ((flags & 512) != 0) {
            out.writeUTF(this.member.getGlobalJNDIName());
         }

         if ((flags & 1024) != 0) {
            out.writeUTF(this.member.getLocalJNDIName());
         }

         this.member.getBackEndId().writeExternal(out);
         this.member.getDestinationId().writeExternal(out);
         this.member.getDispatcherId().writeExternal(out);
         if (version >= 3) {
            if ((flags & '耀') != 0) {
               out.writeShort(extendedFlags);
            }

            if ((extendedFlags & 1) != 0) {
               out.writeUTF(this.member.getPersistentStoreName());
            }
         }

         if ((extendedFlags & 2) != 0) {
            out.writeUTF(localJmsServerConfigName);
         }

         int wireMemberType;
         boolean doWrite;
         if ((flags & 16) != 0) {
            doWrite = true;
            wireMemberType = this.member.getDeploymentMemberType();
         } else if ((flags & 4) != 0) {
            doWrite = true;
            wireMemberType = this.member.getMemberType1212();
         } else {
            doWrite = false;
            wireMemberType = -1;
         }

         if (doWrite) {
            out.writeInt(wireMemberType);
         }

         if ((extendedFlags & 8) != 0) {
            out.writeUTF(localPathServiceJndiName);
         }

      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      short flags = in.readShort();
      int versionFromMask = flags & 7;
      if (versionFromMask >= 1 && 4 >= versionFromMask) {
         this.getPeerInfoNetworkVersion(in);
         DDMember member = new DDMember();
         member.setIsProductionPausedWithoutEvent((flags & 8) != 0);
         member.setIsConsumptionPausedWithoutEvent((flags & 16) != 0);
         member.setIsInsertionPausedWithoutEvent((flags & 32) != 0);
         member.setHasConsumersWithoutEvent((flags & 128) != 0);
         member.setIsPersistent((flags & 256) != 0);
         int rMode = false;
         byte rMode;
         switch (flags & 24576) {
            case 0:
               rMode = 14;
               break;
            case 8192:
               rMode = 12;
               break;
            case 16384:
               rMode = 11;
               break;
            case 24576:
               rMode = 13;
               break;
            default:
               throw new AssertionError();
         }

         member.setRemoteSecurityMode(rMode);
         member.setWeightWithoutEvent(in.readInt());
         member.setName(in.readUTF());
         member.setWLSServerName(in.readUTF());
         member.setJMSServerInstanceName(in.readUTF());
         if ((flags & 2048) != 0) {
            member.setMigratableTargetName(in.readUTF());
         }

         if ((flags & 4096) != 0) {
            member.setDomainName(in.readUTF());
         }

         if ((flags & 512) != 0) {
            member.setGlobalJNDIName(in.readUTF());
         }

         if ((flags & 1024) != 0) {
            member.setLocalJNDIName(in.readUTF());
         }

         JMSServerId backEndId = new JMSServerId();
         backEndId.readExternal(in);
         member.setBackEndId(backEndId);
         JMSID destinationId = new JMSID();
         destinationId.readExternal(in);
         member.setDestinationId(destinationId);
         DispatcherId dispatcherId = new DispatcherId();
         dispatcherId.readExternal(in);
         member.setDispatcherId(dispatcherId);
         short extendedFlags = 0;
         if ((flags & '耀') != 0) {
            extendedFlags = in.readShort();
         }

         if ((extendedFlags & 1) != 0) {
            member.setPersistentStoreName(in.readUTF());
         }

         if ((extendedFlags & 2) != 0) {
            member.setJMSServerConfigName(in.readUTF());
         }

         if ((extendedFlags & 20) != 0) {
            member.setDeploymentMemberType(in.readInt());
         }

         if ((extendedFlags & 8) != 0) {
            member.setPathServiceJndiNameWithoutEvent(in.readUTF());
         }

         member.setIsUpWithoutEvent((flags & 64) != 0);
         this.member = member;
      } else {
         throw JMSUtilities.versionIOException(versionFromMask, 1, 4);
      }
   }

   public boolean equals(Object o) {
      return o instanceof DDMemberStatusSharer && ((DDMemberStatusSharer)o).member.getName().equals(this.member.getName());
   }

   public int hashCode() {
      return this.member.getName().hashCode();
   }

   public String toString() {
      return "DDMemberStatusSharer: " + this.member.getName() + ", hash: " + this.hashCode();
   }

   private int getPeerInfoNetworkVersion(Object o) throws IOException {
      return o instanceof PeerInfoable ? this.calculateVersion(((PeerInfoable)o).getPeerInfo()) : 5;
   }

   private int calculateVersion(PeerInfo pi) throws IOException {
      if (pi.compareTo(PeerInfo.VERSION_DIABLO) < 0) {
         throw JMSUtilities.versionIOException(0, 1, 4);
      } else if (pi.compareTo(PeerInfo.VERSION_920) <= 0) {
         return 1;
      } else if (pi.compareTo(PeerInfo.VERSION_1030) <= 0) {
         return 2;
      } else if (pi.compareTo(PeerInfo.VERSION_1212) < 0) {
         return 3;
      } else {
         return pi.compareTo(PeerInfo.VERSION_1221) < 0 ? 4 : 5;
      }
   }

   private int get3BitWriteNetworkVersion(Object o) throws IOException {
      if (o instanceof PeerInfoable) {
         int version = this.calculateVersion(((PeerInfoable)o).getPeerInfo());
         return version > 4 ? 4 : version;
      } else {
         return 4;
      }
   }
}
