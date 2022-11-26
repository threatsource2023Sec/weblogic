package weblogic.t3.srvr;

import java.security.AccessController;
import java.security.PrivilegedAction;
import weblogic.kernel.KernelStatus;
import weblogic.kernel.T3SrvrLogger;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.UnixMachineMBean;
import weblogic.management.provider.ManagementService;
import weblogic.platform.OperatingSystem;
import weblogic.platform.Unix;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;

public final class SetUIDRendezvous {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugCategory dbg = Debug.getCategory("weblogic.DebugSetUID");
   private static final SetUIDRendezvous singleton = new SetUIDRendezvous();
   private boolean canSwitchUsers;
   private final OperatingSystem os = OperatingSystem.getOS();
   private final String privilegedUser;
   private final String privilegedGroup;
   private final String unPrivilegedUser = getNonPrivUser();
   private final String unPrivilegedGroup = getNonPrivGroup();

   private SetUIDRendezvous() {
      String privUser = null;
      String privGroup = null;
      if ((this.unPrivilegedUser != null || this.unPrivilegedGroup != null) && this.os instanceof Unix && KernelStatus.isServer()) {
         privUser = this.os.getUser();
         privGroup = this.os.getGroup();
         if (privUser != null || privGroup != null) {
            this.canSwitchUsers = true;
         }
      }

      this.privilegedUser = privUser;
      this.privilegedGroup = privGroup;
   }

   public static synchronized void initialize() {
      if (dbg.isEnabled()) {
         T3SrvrLogger.logDebugSetUID("current user=" + singleton.privilegedUser + " current group=" + singleton.privilegedGroup + " target user=" + singleton.unPrivilegedUser + " target group=" + singleton.unPrivilegedGroup + " canSwithUsers=" + singleton.canSwitchUsers);
      }

      singleton.makeUnPrivileged();
   }

   public static synchronized void finish() {
      singleton.makePrivileged();
      singleton.makeUnPrivilegedFinal();
      if (dbg.isEnabled()) {
         T3SrvrLogger.logDebugSetUID("switching uid/gid done.");
      }

   }

   public static synchronized Throwable doPrivileged(PrivilegedAction action) {
      Throwable var1;
      try {
         singleton.makePrivileged();
         if (dbg.isEnabled()) {
            T3SrvrLogger.logDebugSetUID("Running action " + action);
         }

         var1 = (Throwable)action.run();
      } finally {
         if (dbg.isEnabled()) {
            T3SrvrLogger.logDebugSetUID("Done action " + action);
         }

         singleton.makeUnPrivileged();
      }

      return var1;
   }

   private void setUser(String user) {
      if (user != null && user.length() != 0) {
         try {
            this.os.setUser(user);
         } catch (IllegalArgumentException var3) {
            T3SrvrLogger.logCantSwitchToUser(user, var3);
            return;
         }

         T3SrvrLogger.logSwitchedToUser(user);
      }
   }

   private void setGroup(String group) {
      if (group != null && group.length() != 0) {
         try {
            this.os.setGroup(group);
         } catch (IllegalArgumentException var3) {
            T3SrvrLogger.logCantSwitchToGroup(group, var3);
            return;
         }

         T3SrvrLogger.logSwitchedToGroup(group);
      }
   }

   private void setEUser(String user) {
      if (user != null && user.length() != 0) {
         if (dbg.isEnabled()) {
            T3SrvrLogger.logDebugSetUID("Switching user to " + user);
         }

         try {
            this.os.setEffectiveUser(user);
         } catch (IllegalArgumentException var3) {
            T3SrvrLogger.logCantSwitchToUser(user, var3);
            return;
         }

         if (dbg.isEnabled()) {
            T3SrvrLogger.logDebugSetUID("Switched user to " + user);
         }

      }
   }

   private void setEGroup(String group) {
      if (group != null && group.length() != 0) {
         if (dbg.isEnabled()) {
            T3SrvrLogger.logDebugSetUID("Switching group to " + group);
         }

         try {
            this.os.setEffectiveGroup(group);
         } catch (IllegalArgumentException var3) {
            T3SrvrLogger.logCantSwitchToGroup(group, var3);
            return;
         }

         if (dbg.isEnabled()) {
            T3SrvrLogger.logDebugSetUID("Switched group to " + group);
         }

      }
   }

   private void makeUnPrivilegedFinal() {
      if (this.canSwitchUsers) {
         this.canSwitchUsers = false;
         FileOwnerFixer.fixFileOwnerships(this.unPrivilegedUser, this.unPrivilegedGroup);
         this.setGroup(this.unPrivilegedGroup);
         this.setUser(this.unPrivilegedUser);
         this.verifyReal(this.unPrivilegedGroup, this.unPrivilegedUser);
      } else if (this.unPrivilegedGroup != null && this.unPrivilegedUser != null && System.getProperty("os.name").toLowerCase().startsWith("mac")) {
         FileOwnerFixer.fixFileOwnerships(this.unPrivilegedUser, this.unPrivilegedGroup);
      }

   }

   private void makeUnPrivileged() {
      if (this.canSwitchUsers) {
         this.setEGroup(this.unPrivilegedGroup);
         this.setEUser(this.unPrivilegedUser);
         this.verifyEffective(this.unPrivilegedGroup, this.unPrivilegedUser);
      }
   }

   private void makePrivileged() {
      if (this.canSwitchUsers) {
         this.setEGroup(this.privilegedGroup);
         this.setEUser(this.privilegedUser);
         this.verifyEffective(this.privilegedGroup, this.privilegedUser);
      }
   }

   private void verifyEffective(String group, String user) {
      String euid;
      if (group != null) {
         euid = this.os.getEffectiveGroup();
         if (!group.equals(euid)) {
            throw new AssertionError(group + "!=" + euid);
         }
      }

      if (user != null) {
         euid = this.os.getEffectiveUser();
         if (!user.equals(euid)) {
            throw new AssertionError(user + " != " + euid);
         }
      }

   }

   private void verifyReal(String group, String user) {
      String uid;
      if (group != null) {
         uid = this.os.getGroup();
         if (!group.equals(uid)) {
            throw new AssertionError(group + "!=" + uid);
         }
      }

      if (user != null) {
         uid = this.os.getUser();
         if (!user.equals(uid)) {
            throw new AssertionError(user + " != " + uid);
         }
      }

   }

   private static String getNonPrivUser() {
      if (!KernelStatus.isServer()) {
         return null;
      } else {
         String nonPrivUser = null;
         ServerMBean serverMBean = ManagementService.getRuntimeAccess(kernelId).getServer();
         MachineMBean machineMBean = serverMBean.getMachine();
         if (machineMBean != null && machineMBean instanceof UnixMachineMBean) {
            UnixMachineMBean machine = (UnixMachineMBean)machineMBean;
            if (machine.isPostBindUIDEnabled()) {
               nonPrivUser = machine.getPostBindUID();
            }
         }

         return nonPrivUser;
      }
   }

   private static String getNonPrivGroup() {
      if (!KernelStatus.isServer()) {
         return null;
      } else {
         String nonPrivGroup = null;
         ServerMBean serverMBean = ManagementService.getRuntimeAccess(kernelId).getServer();
         MachineMBean machineMBean = serverMBean.getMachine();
         if (machineMBean != null && machineMBean instanceof UnixMachineMBean) {
            UnixMachineMBean machine = (UnixMachineMBean)machineMBean;
            if (machine.isPostBindGIDEnabled()) {
               nonPrivGroup = machine.getPostBindGID();
            }
         }

         return nonPrivGroup;
      }
   }
}
