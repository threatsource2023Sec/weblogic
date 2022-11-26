package weblogic.work.concurrent.utils;

import java.util.concurrent.Future;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.concurrent.ManagedTaskListener;

public class EmptyManagedTaskListener implements ManagedTaskListener {
   public void taskAborted(Future arg0, ManagedExecutorService arg1, Object arg2, Throwable arg3) {
   }

   public void taskDone(Future arg0, ManagedExecutorService arg1, Object arg2, Throwable arg3) {
   }

   public void taskStarting(Future arg0, ManagedExecutorService arg1, Object arg2) {
   }

   public void taskSubmitted(Future arg0, ManagedExecutorService arg1, Object arg2) {
   }
}
