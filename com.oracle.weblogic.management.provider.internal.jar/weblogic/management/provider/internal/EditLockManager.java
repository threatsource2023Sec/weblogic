package weblogic.management.provider.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.AccessController;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementLogger;
import weblogic.management.internal.EditDirectoryManager;
import weblogic.management.provider.EditWaitTimedOutException;
import weblogic.security.SubjectUtils;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionServiceException;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrincipalAuthenticator;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;

public class EditLockManager {
   static final String NAME = "name";
   static final String PARTITION_NAME = "partition_name";
   static final String CREATOR = "creator";
   static final String OWNER = "owner";
   static final String ACQUIRED = "acquired";
   static final String EXPIRES = "expires";
   static final String EXCLUSIVE = "exclusive";
   static final String DESCRIPTION = "description";
   static final String MERGE_NEEDED = "merge_needed";
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static final AuthenticatedSubject kernelIdentity = obtainKernelIdentity();
   private final String sessionName;
   private final String partitionName;
   private final String description;
   private Object owner;
   private Object creator;
   private long lockAcquisitionTime;
   private long lockExpirationTime;
   private boolean lockExclusiveFlag;
   private boolean mergeNeeded;
   private final LinkedList waiters = new LinkedList();
   private final String fileName;
   private ClearOrEncryptedService encryptionService = null;

   EditLockManager(String partitionName, String sessionName, String description) {
      FileInputStream is = null;
      this.sessionName = sessionName;
      this.partitionName = partitionName;
      this.description = description == null ? "" : description;
      this.fileName = EditDirectoryManager.getEditLock(partitionName, sessionName);

      try {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Getting encryption service");
         }

         this.encryptionService = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService());
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Reading edit lock info from lock file");
         }

         Properties props = new Properties();
         is = new FileInputStream(this.getEditLockFilename());
         props.load(is);
         String encryptedUser = props.getProperty("owner");
         String user = this.decryptUser(encryptedUser);
         if (WLSPrincipals.isKernelUsername(user)) {
            this.owner = kernelIdentity;
         } else if (WLSPrincipals.isAnonymousUsername(user)) {
            this.owner = SubjectUtils.getAnonymousSubject();
         } else if (user != null) {
            PrincipalAuthenticator pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelIdentity, "weblogicDEFAULT", ServiceType.AUTHENTICATION);
            this.owner = pa.impersonateIdentity(user, (ContextHandler)null);
         }

         String encryptedCreator = props.getProperty("creator");
         String creatorStr = this.decryptUser(encryptedCreator);
         if (WLSPrincipals.isKernelUsername(creatorStr)) {
            this.creator = kernelIdentity;
         } else if (WLSPrincipals.isAnonymousUsername(creatorStr)) {
            this.creator = SubjectUtils.getAnonymousSubject();
         } else if (creatorStr != null) {
            PrincipalAuthenticator pa = (PrincipalAuthenticator)SecurityServiceManager.getSecurityService(kernelIdentity, "weblogicDEFAULT", ServiceType.AUTHENTICATION);
            this.creator = pa.impersonateIdentity(creatorStr, (ContextHandler)null);
         }

         String exp = props.getProperty("expires");
         if (exp != null) {
            this.lockExpirationTime = Long.parseLong(exp);
         }

         String acquired = props.getProperty("acquired");
         if (acquired != null) {
            this.lockAcquisitionTime = Long.parseLong(acquired);
         }

         String exclusive = props.getProperty("exclusive");
         if (exclusive != null) {
            this.lockExclusiveFlag = Boolean.valueOf(exclusive);
         }

         this.mergeNeeded = Boolean.parseBoolean(props.getProperty("merge_needed"));
      } catch (FileNotFoundException var23) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Edit lock file was not found, ignoring");
         }
      } catch (Exception var24) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Exception occurred reading edit lock file", var24);
         }

         ManagementLogger.logReadEditLockFileFailed(var24);
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (IOException var22) {
            }
         }

      }

      if (this.creator == null) {
         this.creator = SecurityServiceManager.getCurrentSubject(kernelIdentity);
      }

      if (!"default".equals(sessionName) || "DOMAIN".equals(partitionName)) {
         this.persistLock();
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Created edit lock manager " + this);
      }

   }

   public synchronized boolean getEditLock(Object newOwner, int waitTimeInMillis, int timeOutInMillis, boolean exclusiveLock) throws EditWaitTimedOutException {
      boolean canceledLock = false;
      Lock lockEntry = null;
      long expirationPeriod = -1L;
      long giveupTime = -1L;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Attempting to get edit lock for owner " + newOwner);
      }

      if (newOwner == null) {
         throw new IllegalArgumentException("owner can not be null");
      } else {
         if (waitTimeInMillis != -1) {
            giveupTime = System.currentTimeMillis() + (long)waitTimeInMillis;
         }

         if (timeOutInMillis != -1) {
            expirationPeriod = (long)timeOutInMillis;
         }

         if (this.creator == null) {
            this.setCreator(newOwner);
         }

         if (this.owner == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "No current owner - getting lock");
            }

            this.setOwner(newOwner);
            this.setExpirationTime(expirationPeriod);
            this.setExclusive(exclusiveLock);
            this.persistLock();
            return canceledLock;
         } else {
            while((!this.ownersEqual(newOwner, this.owner) || this.isLockExclusive() || exclusiveLock) && (lockEntry == null || !lockEntry.isRemoved())) {
               long currentTime = System.currentTimeMillis();
               if (this.lockExpirationTime > 0L && currentTime >= this.lockExpirationTime) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug(this.getDebugInfo() + "Getting lock - edit lock has expired at " + new Date(this.lockExpirationTime));
                  }

                  canceledLock = true;
                  this.clearOwner();
                  this.setOwner(newOwner);
                  if (lockEntry != null) {
                     this.waiters.remove(lockEntry);
                  }

                  this.setExpirationTime(expirationPeriod);
                  this.setExclusive(exclusiveLock);
                  this.persistLock();
                  return canceledLock;
               }

               if (giveupTime != -1L && currentTime >= giveupTime) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug(this.getDebugInfo() + "Giving up, give up time was " + new Date(giveupTime));
                  }

                  if (lockEntry != null) {
                     this.waiters.remove(lockEntry);
                  }

                  throw new EditWaitTimedOutException("Waited " + waitTimeInMillis + " milliseconds");
               }

               if (lockEntry == null) {
                  lockEntry = new Lock(newOwner, exclusiveLock, expirationPeriod);
                  this.waiters.add(lockEntry);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug(this.getDebugInfo() + "Adding owner to waiters list " + newOwner);
                  }
               }

               long waitTime = -1L;
               if (this.lockExpirationTime > 0L) {
                  waitTime = this.lockExpirationTime - currentTime;
               }

               if (giveupTime != -1L && (giveupTime < this.lockExpirationTime || this.lockExpirationTime <= 0L)) {
                  waitTime = giveupTime - currentTime;
               }

               try {
                  if (waitTime == -1L) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug(this.getDebugInfo() + "Waiting until notified");
                     }

                     this.wait();
                  } else {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug(this.getDebugInfo() + "Waiting for " + waitTime + " milliseconds");
                     }

                     this.wait(waitTime);
                  }
               } catch (InterruptedException var16) {
               }
            }

            if (lockEntry != null && !lockEntry.isRemoved()) {
               this.waiters.remove(lockEntry);
            }

            this.setExpirationTime(expirationPeriod);
            this.setExclusive(exclusiveLock);
            return canceledLock;
         }
      }
   }

   public synchronized void releaseEditLock(Object currOwner) {
      if (currOwner == null) {
         throw new IllegalArgumentException("owner can not be null");
      } else if (this.owner != null) {
         if (!this.ownersEqual(this.owner, currOwner)) {
            throw new IllegalStateException("not owner, owner is " + this.owner);
         } else {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(this.getDebugInfo() + "Release edit lock, owner is " + this.owner);
            }

            this.clearOwner();
            this.persistLock();
            if (!this.waiters.isEmpty()) {
               Lock lockEntry = (Lock)this.waiters.removeFirst();
               lockEntry.setRemoved(true);
               this.setOwner(lockEntry.getLockOwner());
               this.setExclusive(lockEntry.isExclusive());
               this.setExpirationTime(lockEntry.getExpirationTime());
               this.persistLock();
               this.notifyAll();
            }

         }
      }
   }

   public synchronized void cancelEditLock(Object newOwner) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Canceling edit lock, setting new owner " + newOwner);
      }

      if (newOwner == null) {
         throw new IllegalArgumentException("owner can not be null");
      } else {
         this.clearOwner();
         this.setOwner(newOwner);
         this.persistLock();
      }
   }

   synchronized Object getLockCreator() {
      return this.creator;
   }

   public synchronized Object getLockOwner() {
      return this.owner;
   }

   String getPartitionName() {
      return this.partitionName;
   }

   String getEditSessionName() {
      return this.sessionName;
   }

   String getEditSessionDescription() {
      return this.description;
   }

   public synchronized long getLockAcquisitionTime() {
      return this.lockAcquisitionTime;
   }

   public synchronized boolean isLockExclusive() {
      return this.lockExclusiveFlag;
   }

   public synchronized long getLockExpirationTime() {
      return this.lockExpirationTime;
   }

   public synchronized boolean isLockOwner(Object aOwner) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Checking lock owner for " + aOwner);
         debugLogger.debug(this.getDebugInfo() + "Current owner is " + this.owner);
      }

      return this.ownersEqual(this.owner, aOwner);
   }

   private void setCreator(Object newOwner) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Setting creator to " + newOwner);
      }

      this.creator = newOwner;
   }

   private void setOwner(Object newOwner) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Setting owner to " + newOwner);
      }

      this.owner = newOwner;
      this.lockAcquisitionTime = System.currentTimeMillis();
   }

   private void setExpirationTime(long expirationPeriod) {
      if (expirationPeriod == -1L) {
         this.lockExpirationTime = 0L;
      } else {
         this.lockExpirationTime = System.currentTimeMillis() + expirationPeriod;
      }

   }

   private void setExclusive(boolean exclusive) {
      this.lockExclusiveFlag = exclusive;
   }

   private void clearOwner() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Clearing owner and times");
      }

      this.owner = null;
      this.lockAcquisitionTime = 0L;
      this.lockExpirationTime = 0L;
      this.lockExclusiveFlag = false;
   }

   boolean isMergeNeeded() {
      return this.mergeNeeded;
   }

   void markMergeNeeded() {
      if (!this.mergeNeeded) {
         this.mergeNeeded = true;
         this.persistLock();
      }
   }

   void clearMergeNeeded() {
      if (this.mergeNeeded) {
         this.mergeNeeded = false;
         this.persistLock();
      }
   }

   private void persistLock() {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getDebugInfo() + "Persisting lock");
      }

      FileOutputStream os = null;

      try {
         Properties props = new Properties();
         if (this.creator != null) {
            if (this.creator instanceof AuthenticatedSubject) {
               props.setProperty("creator", this.encryptUser(SubjectUtils.getUsername((AuthenticatedSubject)this.creator)));
            } else {
               props.setProperty("creator", this.encryptUser(this.creator.toString()));
            }
         }

         if (this.owner != null) {
            if (this.owner instanceof AuthenticatedSubject) {
               props.setProperty("owner", this.encryptUser(SubjectUtils.getUsername((AuthenticatedSubject)this.owner)));
            } else {
               props.setProperty("owner", this.encryptUser(this.owner.toString()));
            }

            props.setProperty("acquired", "" + this.lockAcquisitionTime);
            props.setProperty("expires", "" + this.lockExpirationTime);
            props.setProperty("exclusive", "" + this.lockExclusiveFlag);
         }

         File lok = new File(this.getEditLockFilename());
         if (!lok.exists()) {
            File parent = lok.getParentFile();
            if (!parent.exists()) {
               parent.mkdirs();
            }
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug(this.getDebugInfo() + "Writing edit lock to " + lok);
         }

         props.setProperty("name", this.sessionName);
         props.setProperty("partition_name", this.partitionName);
         props.setProperty("description", this.description);
         if (this.mergeNeeded) {
            props.setProperty("merge_needed", String.valueOf(true));
         }

         os = new FileOutputStream(lok);
         props.store(os, "");
      } catch (Exception var13) {
         ManagementLogger.logWriteEditLockFileFailed(var13);
      } finally {
         if (os != null) {
            try {
               os.close();
            } catch (IOException var12) {
            }
         }

      }

   }

   private boolean ownersEqual(Object newOwner, Object oldOwner) {
      if (newOwner != null && oldOwner != null) {
         return newOwner instanceof AuthenticatedSubject && oldOwner instanceof AuthenticatedSubject ? SubjectUtils.getUsername((AuthenticatedSubject)newOwner).equals(SubjectUtils.getUsername((AuthenticatedSubject)oldOwner)) : newOwner.equals(oldOwner);
      } else {
         return newOwner == oldOwner;
      }
   }

   private String encryptUser(String user) {
      return this.encryptionService.encrypt(user);
   }

   private String decryptUser(String user) {
      if (user == null) {
         return user;
      } else {
         try {
            return this.encryptionService.decrypt(user);
         } catch (EncryptionServiceException var3) {
            ManagementLogger.logEditLockPropertyDecryptionFailure(this.getEditLockFilename(), "owner", user, var3.toString());
            return null;
         } catch (Exception var4) {
            ManagementLogger.logEditLockDecryptionFailure(this.getEditLockFilename(), var4.toString());
            return null;
         }
      }
   }

   private String getEditLockFilename() {
      return this.fileName;
   }

   private static AuthenticatedSubject obtainKernelIdentity() {
      AuthenticatedSubject s = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      return s;
   }

   private String getDebugInfo() {
      return "[EditLockManager partition:" + this.getPartitionName() + " name:" + this.getEditSessionName() + "] ";
   }

   private class Lock {
      private Object lockOwner;
      private boolean exclusive;
      private boolean removed;
      private long expirationTime;

      private Lock(Object owner, boolean isExclusive, long expirationTime) {
         this.lockOwner = owner;
         this.exclusive = isExclusive;
         this.expirationTime = expirationTime;
      }

      public Object getLockOwner() {
         return this.lockOwner;
      }

      public boolean isExclusive() {
         return this.exclusive;
      }

      public long getExpirationTime() {
         return this.expirationTime;
      }

      public void setRemoved(boolean isRemoved) {
         this.removed = isRemoved;
      }

      public boolean isRemoved() {
         return this.removed;
      }

      // $FF: synthetic method
      Lock(Object x1, boolean x2, long x3, Object x4) {
         this(x1, x2, x3);
      }
   }
}
