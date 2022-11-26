package org.apache.openjpa.kernel;

import java.util.Collection;
import java.util.Iterator;
import org.apache.openjpa.lib.log.Log;

public abstract class AbstractLockManager implements LockManager {
   protected StoreContext ctx;
   protected Log log;

   public void setContext(StoreContext ctx) {
      this.ctx = ctx;
      this.log = ctx.getConfiguration().getLog("openjpa.Runtime");
   }

   public StoreContext getContext() {
      return this.ctx;
   }

   public void lockAll(Collection sms, int level, int timeout, Object context) {
      Iterator itr = sms.iterator();

      while(itr.hasNext()) {
         this.lock((OpenJPAStateManager)itr.next(), level, timeout, context);
      }

   }

   public void beginTransaction() {
   }

   public void endTransaction() {
   }

   public void close() {
   }
}
