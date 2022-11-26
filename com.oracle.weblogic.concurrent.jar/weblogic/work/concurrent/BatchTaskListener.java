package weblogic.work.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public interface BatchTaskListener {
   void taskSuccess(Object var1);

   void taskFailed(ExecutionException var1);

   void taskCanceled(CancellationException var1);
}
