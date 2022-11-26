package kodo.remote;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.openjpa.kernel.AbstractLockManager;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import serp.util.Numbers;

public class ClientLockManager extends AbstractLockManager {
   private ClientStoreManager _store = null;

   public void setContext(StoreContext ctx) {
      super.setContext(ctx);
      this._store = (ClientStoreManager)ctx.getBroker().getStoreManager().getInnermostDelegate();
   }

   public void serverLocked(OpenJPAStateManager sm, int level) {
      if (level == 0) {
         sm.setLock((Object)null);
      } else {
         sm.setLock(Numbers.valueOf(level));
      }

   }

   public int getLockLevel(OpenJPAStateManager sm) {
      Integer level = (Integer)sm.getLock();
      return level == null ? 0 : level;
   }

   public void release(OpenJPAStateManager sm) {
      sm.setLock((Object)null);
   }

   public void lock(OpenJPAStateManager sm, int level, int timeout, Object context) {
      if (!sm.isNew() && level > this.getLockLevel(sm)) {
         LockCommand cmd = new LockCommand(sm.getObjectId(), level, timeout);
         this._store.send(cmd);
         this.serverLocked(sm, cmd.getLockLevels()[0]);
      }
   }

   public void lockAll(Collection sms, int level, int timeout, Object context) {
      List lock = null;
      Iterator itr = sms.iterator();

      while(true) {
         OpenJPAStateManager sm;
         do {
            if (!itr.hasNext()) {
               if (lock == null) {
                  return;
               }

               Object[] oids = new Object[lock.size()];

               for(int i = 0; i < oids.length; ++i) {
                  oids[i] = ((OpenJPAStateManager)lock.get(i)).getObjectId();
               }

               LockCommand cmd = new LockCommand(oids, level, timeout);
               this._store.send(cmd);
               int[] locks = cmd.getLockLevels();

               for(int i = 0; i < locks.length; ++i) {
                  this.serverLocked((OpenJPAStateManager)lock.get(i), locks[i]);
               }

               return;
            }

            sm = (OpenJPAStateManager)itr.next();
         } while(sm.isNew() && !sm.isFlushed());

         if (level > this.getLockLevel(sm)) {
            if (lock == null) {
               lock = new ArrayList(sms.size());
            }

            lock.add(sm);
         }
      }
   }
}
