package weblogic.cache.store;

import commonj.work.WorkException;
import commonj.work.WorkManager;
import java.util.TimerTask;

public class StoreTimer extends TimerTask {
   private final StoreBuffer buffer;
   private final StoreWork work;
   private final WorkManager workManager;

   public StoreTimer(StoreBuffer buffer, StoreWork work, WorkManager workManager) {
      this.buffer = buffer;
      this.work = work;
      this.workManager = workManager;
   }

   public void run() {
      if (!this.buffer.isEmpty()) {
         try {
            this.workManager.schedule(this.work);
         } catch (IllegalArgumentException var2) {
            var2.printStackTrace();
         } catch (WorkException var3) {
            var3.printStackTrace();
         }

         this.cancel();
      }

   }
}
