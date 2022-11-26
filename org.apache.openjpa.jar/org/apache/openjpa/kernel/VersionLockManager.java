package org.apache.openjpa.kernel;

import serp.util.Numbers;

public class VersionLockManager extends AbstractLockManager {
   private boolean _versionCheckOnReadLock = true;
   private boolean _versionUpdateOnWriteLock = true;

   public int getLockLevel(OpenJPAStateManager sm) {
      while(sm.getOwner() != null) {
         sm = sm.getOwner();
      }

      Number level = (Number)sm.getLock();
      return level == null ? 0 : level.intValue();
   }

   protected void setLockLevel(OpenJPAStateManager sm, int level) {
      sm.setLock(Numbers.valueOf(level));
   }

   public void release(OpenJPAStateManager sm) {
      sm.setLock((Object)null);
   }

   public void lock(OpenJPAStateManager sm, int level, int timeout, Object sdata) {
      if (level != 0) {
         while(sm.getOwner() != null) {
            sm = sm.getOwner();
         }

         int oldLevel = this.getLockLevel(sm);
         if (sm.isPersistent() && !sm.isNew() && level > oldLevel) {
            try {
               this.lockInternal(sm, level, timeout, sdata);
            } catch (RuntimeException var7) {
               this.setLockLevel(sm, oldLevel);
               throw var7;
            }
         }
      }
   }

   protected void lockInternal(OpenJPAStateManager sm, int level, int timeout, Object sdata) {
      this.setLockLevel(sm, level);
      if (level >= 20 && this._versionUpdateOnWriteLock) {
         this.getContext().transactional(sm.getManagedInstance(), true, (OpCallbacks)null);
      } else if (level >= 10 && this._versionCheckOnReadLock) {
         this.getContext().transactional(sm.getManagedInstance(), false, (OpCallbacks)null);
      }

   }

   public void setVersionCheckOnReadLock(boolean versionCheckOnReadLock) {
      this._versionCheckOnReadLock = versionCheckOnReadLock;
   }

   public boolean getVersionCheckOnReadLock() {
      return this._versionCheckOnReadLock;
   }

   public void setVersionUpdateOnWriteLock(boolean versionUpdateOnWriteLock) {
      this._versionUpdateOnWriteLock = versionUpdateOnWriteLock;
   }

   public boolean getVersionUpdateOnWriteLock() {
      return this._versionUpdateOnWriteLock;
   }
}
