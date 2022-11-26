package weblogic.jdbc.common.rac.internal;

import oracle.ucp.util.Task;
import oracle.ucp.util.TaskHandle;
import oracle.ucp.util.TaskManagerException;

public class WLTaskHandle implements TaskHandle {
   private WLTask wltask;

   public WLTaskHandle(WLTask wltask) {
      this.wltask = wltask;
   }

   public Object get(long timeout) throws TaskManagerException {
      try {
         return this.wltask.getResult(timeout);
      } catch (InterruptedException var4) {
         throw new TaskManagerException(var4);
      }
   }

   public Task getTask() {
      return this.wltask.getTask();
   }
}
