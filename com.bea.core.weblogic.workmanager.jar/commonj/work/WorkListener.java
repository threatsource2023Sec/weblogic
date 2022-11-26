package commonj.work;

import java.util.EventListener;

public interface WorkListener extends EventListener {
   void workAccepted(WorkEvent var1);

   void workRejected(WorkEvent var1);

   void workStarted(WorkEvent var1);

   void workCompleted(WorkEvent var1);
}
