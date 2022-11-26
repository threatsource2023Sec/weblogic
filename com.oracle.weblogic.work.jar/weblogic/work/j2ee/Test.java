package weblogic.work.j2ee;

import commonj.work.Work;
import commonj.work.WorkEvent;
import commonj.work.WorkException;
import commonj.work.WorkItem;
import commonj.work.WorkListener;
import commonj.work.WorkManager;
import weblogic.kernel.Kernel;

public class Test implements WorkListener, Work {
   private WorkItem item;
   private boolean throwException;

   public Test() {
   }

   public Test(boolean flag) {
      this.throwException = flag;
   }

   public void workAccepted(WorkEvent event) {
      System.out.println("work accepted : " + event.getType());
   }

   public void workRejected(WorkEvent event) {
      System.out.println("work rejected : " + event.getType() + ", workitem : " + (this.item != null ? this.item.getStatus() : -1));
   }

   public void workStarted(WorkEvent event) {
      System.out.println("work started : " + event.getType() + ", workitem : " + (this.item != null ? this.item.getStatus() : -1));
   }

   public void workCompleted(WorkEvent event) {
      System.out.println("work completed : " + event.getType() + ", workitem : " + this.item.getStatus() + ", exception : " + event.getException());
   }

   public static void main(String[] args) {
      Test test = new Test();
      Test test1 = new Test(true);
      Kernel.ensureInitialized();
      WorkManager manager = J2EEWorkManager.getDefault();

      try {
         test.item = manager.schedule(test, test);
         test1.item = manager.schedule(test1, test1);
      } catch (WorkException var5) {
         var5.printStackTrace();
      } catch (IllegalArgumentException var6) {
         var6.printStackTrace();
      }

      while(test1.item.getStatus() != 4) {
      }

   }

   public void run() {
      try {
         System.out.println("started run. sleeping ...");
         Thread.sleep(10000L);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

      if (this.throwException) {
         throw new RuntimeException("testing testing");
      } else {
         System.out.println("completed run");
      }
   }

   public void release() {
   }

   public boolean isDaemon() {
      return false;
   }
}
